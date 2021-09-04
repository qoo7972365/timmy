/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.SummaryBean;
/*      */ import com.adventnet.appmanager.cam.beans.CAMGraphs;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.Property;
/*      */ import com.adventnet.awolf.tags.TimeChart;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.me.apm.cmdb.APMHelpDeskUtil.CIUrl;
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
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class ScriptMonitor_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   64 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   67 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   68 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   69 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   76 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   81 */     ArrayList list = null;
/*   82 */     StringBuffer sbf = new StringBuffer();
/*   83 */     ManagedApplication mo = new ManagedApplication();
/*   84 */     if (distinct)
/*      */     {
/*   86 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   90 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   93 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   95 */       ArrayList row = (ArrayList)list.get(i);
/*   96 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   97 */       if (distinct) {
/*   98 */         sbf.append(row.get(0));
/*      */       } else
/*  100 */         sbf.append(row.get(1));
/*  101 */       sbf.append("</option>");
/*      */     }
/*      */     
/*  104 */     return sbf.toString(); }
/*      */   
/*  106 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  109 */     if (severity == null)
/*      */     {
/*  111 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  113 */     if (severity.equals("5"))
/*      */     {
/*  115 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  117 */     if (severity.equals("1"))
/*      */     {
/*  119 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  124 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  131 */     if (severity == null)
/*      */     {
/*  133 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  135 */     if (severity.equals("1"))
/*      */     {
/*  137 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  139 */     if (severity.equals("4"))
/*      */     {
/*  141 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  143 */     if (severity.equals("5"))
/*      */     {
/*  145 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  150 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  156 */     if (severity == null)
/*      */     {
/*  158 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  160 */     if (severity.equals("5"))
/*      */     {
/*  162 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  164 */     if (severity.equals("1"))
/*      */     {
/*  166 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  170 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  176 */     if (severity == null)
/*      */     {
/*  178 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  180 */     if (severity.equals("1"))
/*      */     {
/*  182 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  184 */     if (severity.equals("4"))
/*      */     {
/*  186 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  188 */     if (severity.equals("5"))
/*      */     {
/*  190 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  194 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  200 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  206 */     if (severity == 5)
/*      */     {
/*  208 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  210 */     if (severity == 1)
/*      */     {
/*  212 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  217 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  223 */     if (severity == null)
/*      */     {
/*  225 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  227 */     if (severity.equals("5"))
/*      */     {
/*  229 */       if (isAvailability) {
/*  230 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  233 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  236 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  238 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  240 */     if (severity.equals("1"))
/*      */     {
/*  242 */       if (isAvailability) {
/*  243 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  246 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  253 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  260 */     if (severity == null)
/*      */     {
/*  262 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  264 */     if (severity.equals("5"))
/*      */     {
/*  266 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  268 */     if (severity.equals("4"))
/*      */     {
/*  270 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  272 */     if (severity.equals("1"))
/*      */     {
/*  274 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  279 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  285 */     if (severity == null)
/*      */     {
/*  287 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  289 */     if (severity.equals("5"))
/*      */     {
/*  291 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  293 */     if (severity.equals("4"))
/*      */     {
/*  295 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  297 */     if (severity.equals("1"))
/*      */     {
/*  299 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  304 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  311 */     if (severity == null)
/*      */     {
/*  313 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  315 */     if (severity.equals("5"))
/*      */     {
/*  317 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  319 */     if (severity.equals("4"))
/*      */     {
/*  321 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  323 */     if (severity.equals("1"))
/*      */     {
/*  325 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  330 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  338 */     StringBuffer out = new StringBuffer();
/*  339 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  340 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  341 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  342 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  343 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  344 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  345 */     out.append("</tr>");
/*  346 */     out.append("</form></table>");
/*  347 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  354 */     if (val == null)
/*      */     {
/*  356 */       return "-";
/*      */     }
/*      */     
/*  359 */     String ret = FormatUtil.formatNumber(val);
/*  360 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  361 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  364 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  368 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  376 */     StringBuffer out = new StringBuffer();
/*  377 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  378 */     out.append("<tr>");
/*  379 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  381 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  383 */     out.append("</tr>");
/*  384 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  388 */       if (j % 2 == 0)
/*      */       {
/*  390 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  394 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  397 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  399 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  402 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  406 */       out.append("</tr>");
/*      */     }
/*  408 */     out.append("</table>");
/*  409 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  410 */     out.append("<tr>");
/*  411 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  412 */     out.append("</tr>");
/*  413 */     out.append("</table>");
/*  414 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  420 */     StringBuffer out = new StringBuffer();
/*  421 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  422 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  423 */     out.append("<tr>");
/*  424 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  425 */     out.append("<tr>");
/*  426 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  427 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  428 */     out.append("</tr>");
/*  429 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  432 */       out.append("<tr>");
/*  433 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  434 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  435 */       out.append("</tr>");
/*      */     }
/*      */     
/*  438 */     out.append("</table>");
/*  439 */     out.append("</table>");
/*  440 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  445 */     if (severity.equals("0"))
/*      */     {
/*  447 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  451 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  458 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  471 */     StringBuffer out = new StringBuffer();
/*  472 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  473 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  475 */       out.append("<tr>");
/*  476 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  477 */       out.append("</tr>");
/*      */       
/*      */ 
/*  480 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  482 */         String borderclass = "";
/*      */         
/*      */ 
/*  485 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  487 */         out.append("<tr>");
/*      */         
/*  489 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  490 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  491 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  497 */     out.append("</table><br>");
/*  498 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  499 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  501 */       List sLinks = secondLevelOfLinks[0];
/*  502 */       List sText = secondLevelOfLinks[1];
/*  503 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  506 */         out.append("<tr>");
/*  507 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  508 */         out.append("</tr>");
/*  509 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  511 */           String borderclass = "";
/*      */           
/*      */ 
/*  514 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  516 */           out.append("<tr>");
/*      */           
/*  518 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  519 */           if (sLinks.get(i).toString().length() == 0) {
/*  520 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  523 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  525 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  529 */     out.append("</table>");
/*  530 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  537 */     StringBuffer out = new StringBuffer();
/*  538 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  539 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  541 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  543 */         out.append("<tr>");
/*  544 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  545 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  549 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  551 */           String borderclass = "";
/*      */           
/*      */ 
/*  554 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  556 */           out.append("<tr>");
/*      */           
/*  558 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  559 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  560 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  563 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  566 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  571 */     out.append("</table><br>");
/*  572 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  573 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  575 */       List sLinks = secondLevelOfLinks[0];
/*  576 */       List sText = secondLevelOfLinks[1];
/*  577 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  580 */         out.append("<tr>");
/*  581 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  582 */         out.append("</tr>");
/*  583 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  585 */           String borderclass = "";
/*      */           
/*      */ 
/*  588 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  590 */           out.append("<tr>");
/*      */           
/*  592 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  593 */           if (sLinks.get(i).toString().length() == 0) {
/*  594 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  597 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  599 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  603 */     out.append("</table>");
/*  604 */     return out.toString();
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
/*  617 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  620 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  623 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  626 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  629 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  632 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  635 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  638 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  646 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  651 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  656 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  661 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  666 */     if (val != null)
/*      */     {
/*  668 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  672 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  677 */     if (val == null) {
/*  678 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  682 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  687 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  693 */     if (val != null)
/*      */     {
/*  695 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  699 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  705 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  710 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  714 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  719 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  724 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  729 */     String hostaddress = "";
/*  730 */     String ip = request.getHeader("x-forwarded-for");
/*  731 */     if (ip == null)
/*  732 */       ip = request.getRemoteAddr();
/*  733 */     InetAddress add = null;
/*  734 */     if (ip.equals("127.0.0.1")) {
/*  735 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  739 */       add = InetAddress.getByName(ip);
/*      */     }
/*  741 */     hostaddress = add.getHostName();
/*  742 */     if (hostaddress.indexOf('.') != -1) {
/*  743 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  744 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  748 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  753 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  759 */     if (severity == null)
/*      */     {
/*  761 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  763 */     if (severity.equals("5"))
/*      */     {
/*  765 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  767 */     if (severity.equals("1"))
/*      */     {
/*  769 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  774 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  779 */     ResultSet set = null;
/*  780 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  781 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  783 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  784 */       if (set.next()) { String str1;
/*  785 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  786 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  789 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  794 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  797 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  799 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  803 */     StringBuffer rca = new StringBuffer();
/*  804 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  805 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  808 */     int rcalength = key.length();
/*  809 */     String split = "6. ";
/*  810 */     int splitPresent = key.indexOf(split);
/*  811 */     String div1 = "";String div2 = "";
/*  812 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  814 */       if (rcalength > 180) {
/*  815 */         rca.append("<span class=\"rca-critical-text\">");
/*  816 */         getRCATrimmedText(key, rca);
/*  817 */         rca.append("</span>");
/*      */       } else {
/*  819 */         rca.append("<span class=\"rca-critical-text\">");
/*  820 */         rca.append(key);
/*  821 */         rca.append("</span>");
/*      */       }
/*  823 */       return rca.toString();
/*      */     }
/*  825 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  826 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  827 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  828 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  829 */     getRCATrimmedText(div1, rca);
/*  830 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  833 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  834 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  835 */     getRCATrimmedText(div2, rca);
/*  836 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  838 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  843 */     String[] st = msg.split("<br>");
/*  844 */     for (int i = 0; i < st.length; i++) {
/*  845 */       String s = st[i];
/*  846 */       if (s.length() > 180) {
/*  847 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  849 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  853 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  854 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  856 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  860 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  861 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  862 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  865 */       if (key == null) {
/*  866 */         return ret;
/*      */       }
/*      */       
/*  869 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  870 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  873 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  874 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  875 */       set = AMConnectionPool.executeQueryStmt(query);
/*  876 */       if (set.next())
/*      */       {
/*  878 */         String helpLink = set.getString("LINK");
/*  879 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  882 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  888 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  907 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  898 */         if (set != null) {
/*  899 */           AMConnectionPool.closeStatement(set);
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
/*  913 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  914 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  916 */       String entityStr = (String)keys.nextElement();
/*  917 */       String mmessage = temp.getProperty(entityStr);
/*  918 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  919 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  921 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  927 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  928 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  930 */       String entityStr = (String)keys.nextElement();
/*  931 */       String mmessage = temp.getProperty(entityStr);
/*  932 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  933 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  935 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  940 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  950 */     String des = new String();
/*  951 */     while (str.indexOf(find) != -1) {
/*  952 */       des = des + str.substring(0, str.indexOf(find));
/*  953 */       des = des + replace;
/*  954 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  956 */     des = des + str;
/*  957 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  964 */       if (alert == null)
/*      */       {
/*  966 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  968 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  970 */         return "&nbsp;";
/*      */       }
/*      */       
/*  973 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  975 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  978 */       int rcalength = test.length();
/*  979 */       if (rcalength < 300)
/*      */       {
/*  981 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  985 */       StringBuffer out = new StringBuffer();
/*  986 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  987 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  988 */       out.append("</div>");
/*  989 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  990 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  991 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  996 */       ex.printStackTrace();
/*      */     }
/*  998 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1004 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1009 */     ArrayList attribIDs = new ArrayList();
/* 1010 */     ArrayList resIDs = new ArrayList();
/* 1011 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1013 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1015 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1017 */       String resourceid = "";
/* 1018 */       String resourceType = "";
/* 1019 */       if (type == 2) {
/* 1020 */         resourceid = (String)row.get(0);
/* 1021 */         resourceType = (String)row.get(3);
/*      */       }
/* 1023 */       else if (type == 3) {
/* 1024 */         resourceid = (String)row.get(0);
/* 1025 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1028 */         resourceid = (String)row.get(6);
/* 1029 */         resourceType = (String)row.get(7);
/*      */       }
/* 1031 */       resIDs.add(resourceid);
/* 1032 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1033 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1035 */       String healthentity = null;
/* 1036 */       String availentity = null;
/* 1037 */       if (healthid != null) {
/* 1038 */         healthentity = resourceid + "_" + healthid;
/* 1039 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1042 */       if (availid != null) {
/* 1043 */         availentity = resourceid + "_" + availid;
/* 1044 */         entitylist.add(availentity);
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
/* 1058 */     Properties alert = getStatus(entitylist);
/* 1059 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1064 */     int size = monitorList.size();
/*      */     
/* 1066 */     String[] severity = new String[size];
/*      */     
/* 1068 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1070 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1071 */       String resourceName1 = (String)row1.get(7);
/* 1072 */       String resourceid1 = (String)row1.get(6);
/* 1073 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1074 */       if (severity[j] == null)
/*      */       {
/* 1076 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1080 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1082 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1084 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1087 */         if (sev > 0) {
/* 1088 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1089 */           monitorList.set(k, monitorList.get(j));
/* 1090 */           monitorList.set(j, t);
/* 1091 */           String temp = severity[k];
/* 1092 */           severity[k] = severity[j];
/* 1093 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1099 */     int z = 0;
/* 1100 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1103 */       int i = 0;
/* 1104 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1107 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1111 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1115 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1117 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1120 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1124 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1127 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1128 */       String resourceName1 = (String)row1.get(7);
/* 1129 */       String resourceid1 = (String)row1.get(6);
/* 1130 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1131 */       if (hseverity[j] == null)
/*      */       {
/* 1133 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1138 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1140 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1143 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1146 */         if (hsev > 0) {
/* 1147 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1148 */           monitorList.set(k, monitorList.get(j));
/* 1149 */           monitorList.set(j, t);
/* 1150 */           String temp1 = hseverity[k];
/* 1151 */           hseverity[k] = hseverity[j];
/* 1152 */           hseverity[j] = temp1;
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
/* 1164 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1165 */     boolean forInventory = false;
/* 1166 */     String trdisplay = "none";
/* 1167 */     String plusstyle = "inline";
/* 1168 */     String minusstyle = "none";
/* 1169 */     String haidTopLevel = "";
/* 1170 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1172 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1174 */         haidTopLevel = request.getParameter("haid");
/* 1175 */         forInventory = true;
/* 1176 */         trdisplay = "table-row;";
/* 1177 */         plusstyle = "none";
/* 1178 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1185 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1188 */     ArrayList listtoreturn = new ArrayList();
/* 1189 */     StringBuffer toreturn = new StringBuffer();
/* 1190 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1191 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1192 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1194 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1196 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1197 */       String childresid = (String)singlerow.get(0);
/* 1198 */       String childresname = (String)singlerow.get(1);
/* 1199 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1200 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1201 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1202 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1203 */       String unmanagestatus = (String)singlerow.get(5);
/* 1204 */       String actionstatus = (String)singlerow.get(6);
/* 1205 */       String linkclass = "monitorgp-links";
/* 1206 */       String titleforres = childresname;
/* 1207 */       String titilechildresname = childresname;
/* 1208 */       String childimg = "/images/trcont.png";
/* 1209 */       String flag = "enable";
/* 1210 */       String dcstarted = (String)singlerow.get(8);
/* 1211 */       String configMonitor = "";
/* 1212 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1213 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1215 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1217 */       if (singlerow.get(7) != null)
/*      */       {
/* 1219 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1221 */       String haiGroupType = "0";
/* 1222 */       if ("HAI".equals(childtype))
/*      */       {
/* 1224 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1226 */       childimg = "/images/trend.png";
/* 1227 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1228 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1229 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1231 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1233 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1235 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1236 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1239 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1241 */         linkclass = "disabledtext";
/* 1242 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1244 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1245 */       String availmouseover = "";
/* 1246 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1248 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1250 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1251 */       String healthmouseover = "";
/* 1252 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1254 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1257 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1258 */       int spacing = 0;
/* 1259 */       if (level >= 1)
/*      */       {
/* 1261 */         spacing = 40 * level;
/*      */       }
/* 1263 */       if (childtype.equals("HAI"))
/*      */       {
/* 1265 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1266 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1267 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1269 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1270 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1271 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1272 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1273 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1274 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1275 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1276 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1277 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1278 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1279 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1281 */         if (!forInventory)
/*      */         {
/* 1283 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1286 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1288 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1290 */           actions = editlink + actions;
/*      */         }
/* 1292 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1294 */           actions = actions + associatelink;
/*      */         }
/* 1296 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1297 */         String arrowimg = "";
/* 1298 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1300 */           actions = "";
/* 1301 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1302 */           checkbox = "";
/* 1303 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1305 */         if (isIt360)
/*      */         {
/* 1307 */           actionimg = "";
/* 1308 */           actions = "";
/* 1309 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1310 */           checkbox = "";
/*      */         }
/*      */         
/* 1313 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1315 */           actions = "";
/*      */         }
/* 1317 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1319 */           checkbox = "";
/*      */         }
/*      */         
/* 1322 */         String resourcelink = "";
/*      */         
/* 1324 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1326 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1330 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1333 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1334 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1335 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1336 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1337 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1338 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1339 */         if (!isIt360)
/*      */         {
/* 1341 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1345 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1348 */         toreturn.append("</tr>");
/* 1349 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1351 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1352 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1356 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1357 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1360 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1364 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1366 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1367 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1368 */             toreturn.append(assocMessage);
/* 1369 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1370 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1371 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1372 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1378 */         String resourcelink = null;
/* 1379 */         boolean hideEditLink = false;
/* 1380 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1382 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1383 */           hideEditLink = true;
/* 1384 */           if (isIt360)
/*      */           {
/* 1386 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1390 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1392 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1394 */           hideEditLink = true;
/* 1395 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1396 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1401 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1404 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1405 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1406 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1407 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1408 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1409 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1410 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1411 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1412 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1413 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1414 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1415 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1416 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1418 */         if (hideEditLink)
/*      */         {
/* 1420 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1422 */         if (!forInventory)
/*      */         {
/* 1424 */           removefromgroup = "";
/*      */         }
/* 1426 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1427 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1428 */           actions = actions + configcustomfields;
/*      */         }
/* 1430 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1432 */           actions = editlink + actions;
/*      */         }
/* 1434 */         String managedLink = "";
/* 1435 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1437 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1438 */           actions = "";
/* 1439 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1440 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1443 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1445 */           checkbox = "";
/*      */         }
/*      */         
/* 1448 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1450 */           actions = "";
/*      */         }
/* 1452 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1453 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1454 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1455 */         if (isIt360)
/*      */         {
/* 1457 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1461 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1463 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1464 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1465 */         if (!isIt360)
/*      */         {
/* 1467 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1471 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1473 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1476 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1483 */       StringBuilder toreturn = new StringBuilder();
/* 1484 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1485 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1486 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1487 */       String title = "";
/* 1488 */       message = EnterpriseUtil.decodeString(message);
/* 1489 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1490 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1491 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1493 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1495 */       else if ("5".equals(severity))
/*      */       {
/* 1497 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1501 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1503 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1504 */       toreturn.append(v);
/*      */       
/* 1506 */       toreturn.append(link);
/* 1507 */       if (severity == null)
/*      */       {
/* 1509 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1511 */       else if (severity.equals("5"))
/*      */       {
/* 1513 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1515 */       else if (severity.equals("4"))
/*      */       {
/* 1517 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1519 */       else if (severity.equals("1"))
/*      */       {
/* 1521 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1526 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1528 */       toreturn.append("</a>");
/* 1529 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1533 */       ex.printStackTrace();
/*      */     }
/* 1535 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1542 */       StringBuilder toreturn = new StringBuilder();
/* 1543 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1544 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1545 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1546 */       if (message == null)
/*      */       {
/* 1548 */         message = "";
/*      */       }
/*      */       
/* 1551 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1552 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1554 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1555 */       toreturn.append(v);
/*      */       
/* 1557 */       toreturn.append(link);
/*      */       
/* 1559 */       if (severity == null)
/*      */       {
/* 1561 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1563 */       else if (severity.equals("5"))
/*      */       {
/* 1565 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1567 */       else if (severity.equals("1"))
/*      */       {
/* 1569 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1574 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1576 */       toreturn.append("</a>");
/* 1577 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1583 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1586 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1587 */     if (invokeActions != null) {
/* 1588 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1589 */       while (iterator.hasNext()) {
/* 1590 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1591 */         if (actionmap.containsKey(actionid)) {
/* 1592 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1597 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1601 */     String actionLink = "";
/* 1602 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1603 */     String query = "";
/* 1604 */     ResultSet rs = null;
/* 1605 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1606 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1607 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1608 */       actionLink = "method=" + methodName;
/*      */     }
/* 1610 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1611 */       actionLink = methodName;
/*      */     }
/* 1613 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1614 */     Iterator itr = methodarglist.iterator();
/* 1615 */     boolean isfirstparam = true;
/* 1616 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1617 */     while (itr.hasNext()) {
/* 1618 */       HashMap argmap = (HashMap)itr.next();
/* 1619 */       String argtype = (String)argmap.get("TYPE");
/* 1620 */       String argname = (String)argmap.get("IDENTITY");
/* 1621 */       String paramname = (String)argmap.get("PARAMETER");
/* 1622 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1623 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1624 */         isfirstparam = false;
/* 1625 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1627 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1631 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1635 */         actionLink = actionLink + "&";
/*      */       }
/* 1637 */       String paramValue = null;
/* 1638 */       String tempargname = argname;
/* 1639 */       if (commonValues.getProperty(tempargname) != null) {
/* 1640 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1643 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1644 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1645 */           if (dbType.equals("mysql")) {
/* 1646 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1649 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1651 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1653 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1654 */             if (rs.next()) {
/* 1655 */               paramValue = rs.getString("VALUE");
/* 1656 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1660 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1664 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1667 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1672 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1673 */           paramValue = rowId;
/*      */         }
/* 1675 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1676 */           paramValue = managedObjectName;
/*      */         }
/* 1678 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1679 */           paramValue = resID;
/*      */         }
/* 1681 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1682 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1685 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1687 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1688 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1689 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1691 */     return actionLink;
/*      */   }
/*      */   
/* 1694 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1695 */     String dependentAttribute = null;
/* 1696 */     String align = "left";
/*      */     
/* 1698 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1699 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1700 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1701 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1702 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1703 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1704 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1705 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1706 */       align = "center";
/*      */     }
/*      */     
/* 1709 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1710 */     String actualdata = "";
/*      */     
/* 1712 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1713 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1714 */         actualdata = availValue;
/*      */       }
/* 1716 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1717 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1721 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1722 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1725 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1731 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1732 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1733 */       toreturn.append("<table>");
/* 1734 */       toreturn.append("<tr>");
/* 1735 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1736 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1737 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1738 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1739 */         String toolTip = "";
/* 1740 */         String hideClass = "";
/* 1741 */         String textStyle = "";
/* 1742 */         boolean isreferenced = true;
/* 1743 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1744 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1745 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1746 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1748 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1749 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1750 */           while (valueList.hasMoreTokens()) {
/* 1751 */             String dependentVal = valueList.nextToken();
/* 1752 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1753 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1754 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1756 */               toolTip = "";
/* 1757 */               hideClass = "";
/* 1758 */               isreferenced = false;
/* 1759 */               textStyle = "disabledtext";
/* 1760 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1764 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1765 */           toolTip = "";
/* 1766 */           hideClass = "";
/* 1767 */           isreferenced = false;
/* 1768 */           textStyle = "disabledtext";
/* 1769 */           if (dependentImageMap != null) {
/* 1770 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1771 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1774 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1778 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1779 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1780 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1781 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1782 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1783 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1785 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1786 */           if (isreferenced) {
/* 1787 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1791 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1792 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1793 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1794 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1795 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1796 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1798 */           toreturn.append("</span>");
/* 1799 */           toreturn.append("</a>");
/* 1800 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1803 */       toreturn.append("</tr>");
/* 1804 */       toreturn.append("</table>");
/* 1805 */       toreturn.append("</td>");
/*      */     } else {
/* 1807 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1810 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1814 */     String colTime = null;
/* 1815 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1816 */     if ((rows != null) && (rows.size() > 0)) {
/* 1817 */       Iterator<String> itr = rows.iterator();
/* 1818 */       String maxColQuery = "";
/* 1819 */       for (;;) { if (itr.hasNext()) {
/* 1820 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1821 */           ResultSet maxCol = null;
/*      */           try {
/* 1823 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1824 */             while (maxCol.next()) {
/* 1825 */               if (colTime == null) {
/* 1826 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1829 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1838 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1840 */               if (maxCol != null)
/* 1841 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1843 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1838 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1840 */               if (maxCol != null)
/* 1841 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1843 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1848 */     return colTime;
/*      */   }
/*      */   
/* 1851 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1852 */     tablename = null;
/* 1853 */     ResultSet rsTable = null;
/* 1854 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1856 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1857 */       while (rsTable.next()) {
/* 1858 */         tablename = rsTable.getString("DATATABLE");
/* 1859 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1860 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1873 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1864 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1867 */         if (rsTable != null)
/* 1868 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1870 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1876 */     String argsList = "";
/* 1877 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1879 */       if (showArgsMap.get(row) != null) {
/* 1880 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1881 */         if (showArgslist != null) {
/* 1882 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1883 */             if (argsList.trim().equals("")) {
/* 1884 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1887 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1894 */       e.printStackTrace();
/* 1895 */       return "";
/*      */     }
/* 1897 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1902 */     String argsList = "";
/* 1903 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1906 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1908 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1909 */         if (hideArgsList != null)
/*      */         {
/* 1911 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1913 */             if (argsList.trim().equals(""))
/*      */             {
/* 1915 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1919 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1927 */       ex.printStackTrace();
/*      */     }
/* 1929 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1933 */     StringBuilder toreturn = new StringBuilder();
/* 1934 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1941 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1942 */       Iterator itr = tActionList.iterator();
/* 1943 */       while (itr.hasNext()) {
/* 1944 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1945 */         String confirmmsg = "";
/* 1946 */         String link = "";
/* 1947 */         String isJSP = "NO";
/* 1948 */         HashMap tactionMap = (HashMap)itr.next();
/* 1949 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1950 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1951 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1952 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1953 */           (actionmap.containsKey(actionId))) {
/* 1954 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1955 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1956 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1957 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1958 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1960 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1966 */           if (isTableAction) {
/* 1967 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1970 */             tableName = "Link";
/* 1971 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1972 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1973 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1974 */             toreturn.append("</a></td>");
/*      */           }
/* 1976 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1977 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1978 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1979 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1985 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1991 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1993 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1994 */       Properties prop = (Properties)node.getUserObject();
/* 1995 */       String mgID = prop.getProperty("label");
/* 1996 */       String mgName = prop.getProperty("value");
/* 1997 */       String isParent = prop.getProperty("isParent");
/* 1998 */       int mgIDint = Integer.parseInt(mgID);
/* 1999 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 2001 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 2003 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 2004 */       if (node.getChildCount() > 0)
/*      */       {
/* 2006 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2008 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2010 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2012 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2016 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2021 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2023 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2025 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2027 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2031 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2034 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2035 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2037 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2041 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2043 */       if (node.getChildCount() > 0)
/*      */       {
/* 2045 */         builder.append("<UL>");
/* 2046 */         printMGTree(node, builder);
/* 2047 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2052 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2053 */     StringBuffer toReturn = new StringBuffer();
/* 2054 */     String table = "-";
/*      */     try {
/* 2056 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2057 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2058 */       float total = 0.0F;
/* 2059 */       while (it.hasNext()) {
/* 2060 */         String attName = (String)it.next();
/* 2061 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2062 */         boolean roundOffData = false;
/* 2063 */         if ((data != null) && (!data.equals(""))) {
/* 2064 */           if (data.indexOf(",") != -1) {
/* 2065 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2068 */             float value = Float.parseFloat(data);
/* 2069 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2072 */             total += value;
/* 2073 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2076 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2081 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2082 */       while (attVsWidthList.hasNext()) {
/* 2083 */         String attName = (String)attVsWidthList.next();
/* 2084 */         String data = (String)attVsWidthProps.get(attName);
/* 2085 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2086 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2087 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2088 */         String className = (String)graphDetails.get("ClassName");
/* 2089 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2090 */         if (percentage < 1.0F)
/*      */         {
/* 2092 */           data = percentage + "";
/*      */         }
/* 2094 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2096 */       if (toReturn.length() > 0) {
/* 2097 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2101 */       e.printStackTrace();
/*      */     }
/* 2103 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2109 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2110 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2111 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2112 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2113 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2114 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2115 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2116 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2117 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2120 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2121 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2122 */       splitvalues[0] = multiplecondition.toString();
/* 2123 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2126 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2131 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2132 */     if (thresholdType != 3) {
/* 2133 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2134 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2135 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2136 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2137 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2138 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2140 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2141 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2142 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2143 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2144 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2145 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2147 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2148 */     if (updateSelected != null) {
/* 2149 */       updateSelected[0] = "selected";
/*      */     }
/* 2151 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2156 */       StringBuffer toreturn = new StringBuffer("");
/* 2157 */       if (commaSeparatedMsgId != null) {
/* 2158 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2159 */         int count = 0;
/* 2160 */         while (msgids.hasMoreTokens()) {
/* 2161 */           String id = msgids.nextToken();
/* 2162 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2163 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2164 */           count++;
/* 2165 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2166 */             if (toreturn.length() == 0) {
/* 2167 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2169 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2170 */             if (!image.trim().equals("")) {
/* 2171 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2173 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2174 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2177 */         if (toreturn.length() > 0) {
/* 2178 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2182 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2185 */       e.printStackTrace(); }
/* 2186 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2192 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2198 */   private static Map<String, Long> _jspx_dependants = new HashMap(7);
/* 2199 */   static { _jspx_dependants.put("/jsp/includes/ScriptMonitor.jspf", Long.valueOf(1473429417000L));
/* 2200 */     _jspx_dependants.put("/jsp/includes/ScriptLeftPage.jspf", Long.valueOf(1473429417000L));
/* 2201 */     _jspx_dependants.put("/jsp/includes/ManagedServerInfo.jspf", Long.valueOf(1473429417000L));
/* 2202 */     _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2203 */     _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2204 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2205 */     _jspx_dependants.put("/jsp/includes/ScriptTableDetails.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2233 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2237 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2258 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2262 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2263 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2264 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2265 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2266 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2267 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2268 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2269 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/* 2270 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2271 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2272 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2273 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2274 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2275 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2276 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2277 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2278 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2279 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2280 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2281 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.release();
/*      */   }
/*      */   
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException, javax.servlet.ServletException {
/*      */     ;
/*      */     ;
/*      */     ;
/* 2288 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2291 */     JspWriter out = null;
/* 2292 */     Object page = this;
/* 2293 */     JspWriter _jspx_out = null;
/* 2294 */     PageContext _jspx_page_context = null;
/*      */     
/* 2296 */     String _jspx_colnames_1 = null;
/* 2297 */     Integer _jspx_k_1 = null;
/* 2298 */     String _jspx_rowid_1 = null;
/* 2299 */     Integer _jspx_i_1 = null;
/* 2300 */     String _jspx_colvalues_2 = null;
/* 2301 */     Integer _jspx_k_2 = null;
/*      */     try
/*      */     {
/* 2304 */       response.setContentType("text/html;charset=UTF-8");
/* 2305 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2307 */       _jspx_page_context = pageContext;
/* 2308 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2309 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2310 */       session = pageContext.getSession();
/* 2311 */       out = pageContext.getOut();
/* 2312 */       _jspx_out = out;
/*      */       
/* 2314 */       out.write("<!DOCTYPE html>\n");
/* 2315 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n");
/*      */       
/* 2317 */       request.setAttribute("HelpKey", "Script Monitor Details");
/*      */       
/* 2319 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2320 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2322 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2323 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2324 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2326 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2328 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2330 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2332 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2333 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2334 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2335 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2338 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2339 */         String available = null;
/* 2340 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2341 */         out.write(10);
/*      */         
/* 2343 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2344 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2345 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2347 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2349 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2351 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2353 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2354 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2355 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2356 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2359 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2360 */           String unavailable = null;
/* 2361 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2362 */           out.write(10);
/*      */           
/* 2364 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2365 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2366 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2368 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2370 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2372 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2374 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2375 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2376 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2377 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2380 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2381 */             String unmanaged = null;
/* 2382 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2383 */             out.write(10);
/*      */             
/* 2385 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2386 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2387 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2389 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2391 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2393 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2395 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2396 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2397 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2398 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2401 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2402 */               String scheduled = null;
/* 2403 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2404 */               out.write(10);
/*      */               
/* 2406 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2407 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2408 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2410 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2412 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2414 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2416 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2417 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2418 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2419 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2422 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2423 */                 String critical = null;
/* 2424 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2425 */                 out.write(10);
/*      */                 
/* 2427 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2428 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2429 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2431 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2433 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2435 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2437 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2438 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2439 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2440 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2443 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2444 */                   String clear = null;
/* 2445 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2446 */                   out.write(10);
/*      */                   
/* 2448 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2449 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2450 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2452 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2454 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2456 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2458 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2459 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2460 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2461 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2464 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2465 */                     String warning = null;
/* 2466 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2467 */                     out.write(10);
/* 2468 */                     out.write(10);
/*      */                     
/* 2470 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2471 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2473 */                     out.write(10);
/* 2474 */                     out.write(10);
/* 2475 */                     out.write(10);
/* 2476 */                     out.write("\n\n\n\n\n\n\n\n\n\n");
/* 2477 */                     GetWLSGraph wlsGraph = null;
/* 2478 */                     wlsGraph = (GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2479 */                     if (wlsGraph == null) {
/* 2480 */                       wlsGraph = new GetWLSGraph();
/* 2481 */                       _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */                     }
/* 2483 */                     out.write(10);
/* 2484 */                     CAMGraphs camGraph = null;
/* 2485 */                     camGraph = (CAMGraphs)_jspx_page_context.getAttribute("camGraph", 1);
/* 2486 */                     if (camGraph == null) {
/* 2487 */                       camGraph = new CAMGraphs();
/* 2488 */                       _jspx_page_context.setAttribute("camGraph", camGraph, 1);
/*      */                     }
/* 2490 */                     out.write(10);
/* 2491 */                     SummaryBean sumgraph = null;
/* 2492 */                     sumgraph = (SummaryBean)_jspx_page_context.getAttribute("sumgraph", 2);
/* 2493 */                     if (sumgraph == null) {
/* 2494 */                       sumgraph = new SummaryBean();
/* 2495 */                       _jspx_page_context.setAttribute("sumgraph", sumgraph, 2);
/*      */                     }
/* 2497 */                     out.write(10);
/* 2498 */                     Hashtable motypedisplaynames = null;
/* 2499 */                     synchronized (application) {
/* 2500 */                       motypedisplaynames = (Hashtable)_jspx_page_context.getAttribute("motypedisplaynames", 4);
/* 2501 */                       if (motypedisplaynames == null) {
/* 2502 */                         motypedisplaynames = new Hashtable();
/* 2503 */                         _jspx_page_context.setAttribute("motypedisplaynames", motypedisplaynames, 4);
/*      */                       }
/*      */                     }
/* 2506 */                     out.write(10);
/* 2507 */                     Hashtable availabilitykeys = null;
/* 2508 */                     synchronized (application) {
/* 2509 */                       availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2510 */                       if (availabilitykeys == null) {
/* 2511 */                         availabilitykeys = new Hashtable();
/* 2512 */                         _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */                       }
/*      */                     }
/* 2515 */                     out.write(10);
/* 2516 */                     Hashtable healthkeys = null;
/* 2517 */                     synchronized (application) {
/* 2518 */                       healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2519 */                       if (healthkeys == null) {
/* 2520 */                         healthkeys = new Hashtable();
/* 2521 */                         _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */                       }
/*      */                     }
/* 2524 */                     out.write(10);
/*      */                     
/* 2526 */                     String resID = request.getParameter("resourceid");
/*      */                     
/* 2528 */                     out.write("\n<script>\n\n\nfunction enableReports()\n{\n\t");
/*      */                     
/* 2530 */                     if (((request.getAttribute("numeric_size") != null) && (((Integer)request.getAttribute("numeric_size")).intValue() > 0)) || ((request.getAttribute("isTableHasNumeric") != null) && (request.getAttribute("isTableHasNumeric").equals("true"))))
/*      */                     {
/*      */ 
/* 2533 */                       out.write("\n  toggleDiv('reports');\n\t\t");
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/*      */ 
/* 2539 */                       out.write("\n\talert('");
/* 2540 */                       out.print(FormatUtil.getString("am.webclient.script.reportsnoattributes.text"));
/* 2541 */                       out.write("');\n\t\t");
/*      */                     }
/*      */                     
/*      */ 
/* 2545 */                     out.write("\n}\n\nfunction enableSelections(temp)\n{\n\tvar sel = false;\n\tfor(i=0;i<document.form2.elements.length;i++)\n\t{\n\t\tif(document.form2.elements[i].type==\"checkbox\")\n\t               {\n\t                        var name = document.form2.elements[i].name;\n\t                        if(name==\"checkbox\")\n\t                        {\n\t                        \tvar value = document.form2.elements[i].value;\n\t                        \tsel=document.form2.elements[i].checked;\n\t                        \tif(sel)\n\t                        \t{\n\t                        \t\tbreak;\n\t                        \t}\n\t                        }\n\t                 }\n               }\n               if(!sel)\n               {\n                  alert(\"");
/* 2546 */                     out.print(FormatUtil.getString("am.webclient.script.alert.selectattributes"));
/* 2547 */                     out.write("\");\n               }\nelse\n{\n    if(temp==1)\n\tdocument.form2.action=\"/updateScript.do?method=enableReports&resourceid=");
/* 2548 */                     if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                       return;
/* 2550 */                     out.write("\";\n    else if(temp==2)\n    {\n        if(confirm('");
/* 2551 */                     out.print(FormatUtil.getString("am.webclient.qengine.jsalertfordisablereports.text"));
/* 2552 */                     out.write("'))\n            document.form2.action=\"/updateScript.do?method=disableReports&resourceid=");
/* 2553 */                     if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*      */                       return;
/* 2555 */                     out.write("\";\n        else\n            return;\n    }\n\n\tdocument.form2.method=\"Post\"\n\tdocument.form2.submit();\n}\n\n}\n\nfunction fnSelectAll(e)\n{\n\tToggleAll(e,document.form2,\"checkbox\")\n}\n\n\nfunction showInstanceGraph(resid,insid)\n{\n\tMM_openBrWindow('/jsp/wsmigraph.jsp?resourceid='+resid+'&instanceid='+insid,'ExecutionTimeStatistic','width=800,height=500,top=200,left=200,scrollbars=yes,resizable=yes');\n}\n\n\n\n</script>\n\n");
/*      */                     
/* 2557 */                     String dispname = (String)motypedisplaynames.get(request.getParameter("type")) + " Details";
/* 2558 */                     String resourceName = request.getParameter("resourcename");
/*      */                     
/* 2560 */                     String resourceid = resID;
/* 2561 */                     request.setAttribute("resourceid", resID);
/* 2562 */                     ArrayList attribIDs = new ArrayList();
/* 2563 */                     String haid = request.getParameter("haid");
/* 2564 */                     String moname = request.getParameter("moname");
/* 2565 */                     ArrayList resIDs = new ArrayList();
/* 2566 */                     resIDs.add(resID);
/* 2567 */                     Properties ess_atts = (Properties)request.getAttribute("ess_atts");
/* 2568 */                     attribIDs.add(ess_atts.getProperty("Availability"));
/* 2569 */                     attribIDs.add(ess_atts.getProperty("Health"));
/* 2570 */                     attribIDs.add(ess_atts.getProperty("ResponseTime"));
/* 2571 */                     String resourcetype = request.getParameter("type");
/* 2572 */                     HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/* 2573 */                     String monitortype = FormatUtil.getString(request.getParameter("typedisplayname"));
/* 2574 */                     if ((monitortype == null) || (monitortype.equals("")))
/*      */                     {
/* 2576 */                       monitortype = FormatUtil.getString("am.webclient.monitorgroupsecond.category.script");
/*      */                     }
/* 2578 */                     String encodeurl = URLEncoder.encode("/showresource.do?type=" + resourcetype + "&moname=" + moname + "&method=showdetails&resourcename=" + resourceName + "&resourceid=" + resID + "&haid=" + haid);
/* 2579 */                     String tipCurStatus = FormatUtil.getString("am.webclient.script.tooltip");
/* 2580 */                     HashMap ht_numeric = (HashMap)request.getAttribute("numeric_data");
/* 2581 */                     HashMap ht_string = (HashMap)request.getAttribute("string_data");
/* 2582 */                     HashMap displayname_attributeid = (HashMap)request.getAttribute("display_attributeid");
/* 2583 */                     int attributeid1 = -1;
/* 2584 */                     ArrayList numeric = (ArrayList)request.getAttribute("numeric");
/* 2585 */                     ArrayList all_attributes = (ArrayList)request.getAttribute("attributes");
/* 2586 */                     if (all_attributes != null)
/*      */                     {
/* 2588 */                       for (int i = 0; i < all_attributes.size(); i++)
/*      */                       {
/* 2590 */                         if (String.valueOf(displayname_attributeid.get(all_attributes.get(i))) != null)
/*      */                         {
/*      */                           try
/*      */                           {
/* 2594 */                             attributeid1 = Integer.parseInt(String.valueOf(displayname_attributeid.get(all_attributes.get(i))));
/* 2595 */                             attribIDs.add("" + attributeid1);
/*      */                           }
/*      */                           catch (Exception exc) {}
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/* 2604 */                     int num_data = ((Integer)request.getAttribute("numeric_size")).intValue();
/* 2605 */                     int str_data = ((Integer)request.getAttribute("string_size")).intValue();
/* 2606 */                     ArrayList table_details_temp = (ArrayList)request.getAttribute("tabledetails");
/* 2607 */                     if ((table_details_temp != null) && (table_details_temp.size() > 0))
/*      */                     {
/* 2609 */                       ArrayList tableids_temp = (ArrayList)table_details_temp.get(0);
/* 2610 */                       Hashtable table_health_temp = (Hashtable)table_details_temp.get(5);
/* 2611 */                       Hashtable table_resids_temp = (Hashtable)table_details_temp.get(1);
/* 2612 */                       for (int i = 0; i < tableids_temp.size(); i++)
/*      */                       {
/* 2614 */                         ArrayList rowids_temp = (ArrayList)table_resids_temp.get(tableids_temp.get(i));
/* 2615 */                         attribIDs.add("" + table_health_temp.get(tableids_temp.get(i)));
/* 2616 */                         for (int j = 0; j < rowids_temp.size(); j++)
/*      */                         {
/* 2618 */                           resIDs.add("" + rowids_temp.get(j));
/*      */                         }
/*      */                       }
/*      */                     }
/* 2622 */                     Properties alert = getStatus(resIDs, attribIDs);
/* 2623 */                     String seven_days_text = FormatUtil.getString("am.webclient.common.sevendays.tooltip.text");
/* 2624 */                     String thiry_days_text = FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text");
/*      */                     
/* 2626 */                     out.write(10);
/* 2627 */                     out.write(10);
/*      */                     
/* 2629 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2630 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2631 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2633 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2634 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2635 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2637 */                         out.write(32);
/*      */                         
/* 2639 */                         PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2640 */                         _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2641 */                         _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2643 */                         _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                         
/* 2645 */                         _jspx_th_tiles_005fput_005f0.setValue(dispname);
/* 2646 */                         int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2647 */                         if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2648 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                         }
/*      */                         
/* 2651 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2652 */                         out.write(10);
/* 2653 */                         if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2655 */                         out.write(10);
/* 2656 */                         if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2658 */                         out.write(10);
/* 2659 */                         out.write(10);
/*      */                         
/*      */ 
/*      */ 
/*      */ 
/* 2664 */                         out.write(10);
/*      */                         
/* 2666 */                         PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2667 */                         _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2668 */                         _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2670 */                         _jspx_th_tiles_005fput_005f3.setName("ServerLeftArea");
/*      */                         
/* 2672 */                         _jspx_th_tiles_005fput_005f3.setType("string");
/* 2673 */                         int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2674 */                         if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2675 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2676 */                             out = _jspx_page_context.pushBody();
/* 2677 */                             _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2678 */                             _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2681 */                             out.write(10);
/* 2682 */                             out.write("<!--$Id$-->\n\n\n\n");
/*      */                             
/* 2684 */                             int healthid = 2201;
/* 2685 */                             int availabilityid = 2200;
/* 2686 */                             String haid1 = "";
/* 2687 */                             String head = "Script Monitor";
/* 2688 */                             String mon_type = null;
/* 2689 */                             String rtype = request.getParameter("type");
/* 2690 */                             head = rtype;
/* 2691 */                             if (rtype.equals("File System Monitor"))
/*      */                             {
/* 2693 */                               head = "File / Directory Monitor";
/*      */                             }
/*      */                             
/* 2696 */                             String typedisplayname = head;
/* 2697 */                             if (request.getParameter("typedisplayname") != null)
/*      */                             {
/* 2699 */                               typedisplayname = request.getParameter("typedisplayname");
/*      */                             }
/* 2701 */                             else if (request.getAttribute("typedisplayname") != null)
/*      */                             {
/* 2703 */                               typedisplayname = (String)request.getAttribute("typedisplayname");
/*      */                             }
/*      */                             
/* 2706 */                             int baseid = -1;
/*      */                             try
/*      */                             {
/* 2709 */                               if (request.getAttribute("baseid") != null) {
/* 2710 */                                 baseid = Integer.parseInt((String)request.getAttribute("baseid"));
/*      */                               }
/*      */                             }
/*      */                             catch (Exception e2)
/*      */                             {
/* 2715 */                               e2.printStackTrace();
/*      */                             }
/* 2717 */                             String appendParams = "&baseid=" + baseid;
/* 2718 */                             if (baseid != -1)
/*      */                             {
/* 2720 */                               appendParams = appendParams + "&customType=true&basetype=Script Monitor";
/*      */                             }
/* 2722 */                             if ((rtype.equals("file")) || (rtype.equals("directory")) || (rtype.equals("File System Monitor")))
/*      */                             {
/* 2724 */                               rtype = "File System Monitor";
/* 2725 */                               head = "File / Directory Monitor";
/* 2726 */                               typedisplayname = "File / Directory Monitor";
/* 2727 */                             } else if (rtype.equals("Ping Monitor"))
/*      */                             {
/* 2729 */                               head = "Ping Monitor";
/* 2730 */                               rtype = "Ping Monitor";
/* 2731 */                               typedisplayname = "Ping Monitor";
/* 2732 */                               healthid = 9001;
/* 2733 */                               availabilityid = 9000;
/*      */                             }
/*      */                             else
/*      */                             {
/* 2737 */                               availabilityid = Integer.parseInt(ess_atts.getProperty("Availability"));
/* 2738 */                               healthid = Integer.parseInt(ess_atts.getProperty("Health"));
/*      */                             }
/*      */                             
/*      */ 
/* 2742 */                             if (request.getParameter("mtype") != null) {
/* 2743 */                               mon_type = request.getParameter("mtype");
/*      */                             }
/*      */                             else {
/* 2746 */                               mon_type = request.getParameter("type");
/*      */                             }
/*      */                             
/* 2749 */                             if (request.getParameter("type").equals("QENGINE"))
/*      */                             {
/* 2751 */                               healthid = 4301;
/* 2752 */                               availabilityid = 4300;
/*      */                             }
/* 2754 */                             else if (mon_type.equals("file")) {
/* 2755 */                               head = "File System Monitor";
/*      */                               
/* 2757 */                               healthid = 6009;
/* 2758 */                               availabilityid = 6008;
/*      */                             }
/* 2760 */                             else if (mon_type.equals("directory"))
/*      */                             {
/* 2762 */                               head = "File System Monitor";
/* 2763 */                               healthid = 6001;
/* 2764 */                               availabilityid = 6000;
/*      */ 
/*      */                             }
/* 2767 */                             else if (request.getParameter("type").equals("Script Monitor"))
/*      */                             {
/* 2769 */                               healthid = 2201;
/* 2770 */                               availabilityid = 2200;
/*      */                             }
/* 2772 */                             else if (mon_type.equals("Ping Monitor"))
/*      */                             {
/* 2774 */                               healthid = 9001;
/* 2775 */                               availabilityid = 9000;
/*      */                             }
/*      */                             else
/*      */                             {
/* 2779 */                               availabilityid = Integer.parseInt(ess_atts.getProperty("Availability"));
/* 2780 */                               healthid = Integer.parseInt(ess_atts.getProperty("Health"));
/*      */                             }
/*      */                             
/*      */ 
/* 2784 */                             out.write("\n\n<script language=\"JavaScript\">\n");
/*      */                             
/* 2786 */                             if (request.getParameter("editPage") != null)
/*      */                             {
/* 2788 */                               out.write(10);
/*      */                             }
/*      */                             
/* 2791 */                             out.write("\n\n\n\tfunction confirmDelete()\n \t {\n  var s = confirm(\"");
/* 2792 */                             out.print(FormatUtil.getString("am.webclient.urlmonitor.jsalertformonitor.text"));
/* 2793 */                             out.write("\");\n  if (s)\n         document.location.href=\"/deleteMO.do?method=deleteMO&type=");
/* 2794 */                             out.print(head);
/* 2795 */                             out.write("&baseid=");
/* 2796 */                             out.print(baseid);
/* 2797 */                             out.write("&select=");
/* 2798 */                             if (_jspx_meth_c_005fout_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2800 */                             out.write("\";\n\t }\n        function confirmManage()\n \t {\n  var s = confirm(\"");
/* 2801 */                             out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 2802 */                             out.write("\");\n  if (s){\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 2803 */                             if (_jspx_meth_c_005fout_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2805 */                             out.write("&type=");
/* 2806 */                             out.print(rtype);
/* 2807 */                             out.write("&moname=");
/* 2808 */                             out.print(moname);
/* 2809 */                             out.write("&resourcename=");
/* 2810 */                             out.print(resourceName);
/* 2811 */                             out.write("\";\n\n}\n\t }\n\n         function confirmUnManage()\n \t {\n        \t  var show_msg=\"false\";\n              var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 2812 */                             out.print(request.getParameter("resourceid"));
/* 2813 */                             out.write("; //No i18n\n              $.ajax({\n                type:'POST', //No i18n\n                url:url,\n                async:false,\n                success: function(data)\n                {\n                  show_msg=data\n                }\n              });\n              if(show_msg.indexOf(\"true\")>-1)\n              {\n                  alert(\"");
/* 2814 */                             out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 2815 */                             out.write("\");\n\t\t\t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2816 */                             if (_jspx_meth_c_005fout_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2818 */                             out.write("&type=");
/* 2819 */                             out.print(rtype);
/* 2820 */                             out.write("&moname=");
/* 2821 */                             out.print(moname);
/* 2822 */                             out.write("&resourcename=");
/* 2823 */                             out.print(resourceName);
/* 2824 */                             out.write("\";\n             }\n           else { \n        \t   \tvar s = confirm(\"");
/* 2825 */                             out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 2826 */                             out.write("\");\n       \t\t if (s){\n   \t\t \t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2827 */                             if (_jspx_meth_c_005fout_005f5(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2829 */                             out.write("&type=");
/* 2830 */                             out.print(rtype);
/* 2831 */                             out.write("&moname=");
/* 2832 */                             out.print(moname);
/* 2833 */                             out.write("&resourcename=");
/* 2834 */                             out.print(resourceName);
/* 2835 */                             out.write("\";\n\t\t      }\n\t      }\n       }\n  </script>\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td width=\"90%\" height=\"21\" class=\"leftlinksheading\">\n    ");
/* 2836 */                             out.print(FormatUtil.getString(typedisplayname));
/* 2837 */                             out.write("\n      </td>\n  </tr>\n      ");
/* 2838 */                             if (_jspx_meth_c_005fif_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2840 */                             out.write("\n      ");
/* 2841 */                             if (_jspx_meth_c_005fif_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2843 */                             out.write(10);
/* 2844 */                             out.write(9);
/* 2845 */                             out.write(9);
/* 2846 */                             if (_jspx_meth_c_005fif_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2848 */                             out.write(10);
/* 2849 */                             out.write("\n\n\n\n");
/*      */                             
/* 2851 */                             IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2852 */                             _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2853 */                             _jspx_th_c_005fif_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2855 */                             _jspx_th_c_005fif_005f5.setTest("${!empty param.haid && empty invalidhaid}");
/* 2856 */                             int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2857 */                             if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                               for (;;) {
/* 2859 */                                 out.write(10);
/*      */                                 
/* 2861 */                                 haid1 = "&haid=" + request.getParameter("haid");
/*      */                                 
/* 2863 */                                 out.write(10);
/* 2864 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2865 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2869 */                             if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2870 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                             }
/*      */                             
/* 2873 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2874 */                             out.write("\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\">\n    ");
/*      */                             
/* 2876 */                             IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2877 */                             _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2878 */                             _jspx_th_c_005fif_005f6.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2880 */                             _jspx_th_c_005fif_005f6.setTest("${param.method=='editScript'}");
/* 2881 */                             int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2882 */                             if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                               for (;;) {
/* 2884 */                                 out.write("\n    <a href=\"/showresource.do?resourceid=");
/* 2885 */                                 if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                   return;
/* 2887 */                                 out.write("&method=showResourceForResourceID");
/* 2888 */                                 out.print(haid1);
/* 2889 */                                 out.write("\"  class=\"new-left-links\">\n    ");
/* 2890 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2891 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2895 */                             if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2896 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                             }
/*      */                             
/* 2899 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2900 */                             out.write("\n    ");
/* 2901 */                             out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 2902 */                             out.write("\n    ");
/* 2903 */                             if (_jspx_meth_c_005fif_005f7(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2905 */                             out.write("\n     </td>\n  </tr>\n");
/* 2906 */                             out.write(10);
/* 2907 */                             out.write(32);
/* 2908 */                             out.write(32);
/*      */                             
/* 2910 */                             IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2911 */                             _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2912 */                             _jspx_th_c_005fif_005f8.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2914 */                             _jspx_th_c_005fif_005f8.setTest("${!empty ADMIN || !empty DEMO }");
/* 2915 */                             int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2916 */                             if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                               for (;;) {
/* 2918 */                                 out.write("\n\n\n\t<tr>\n  ");
/*      */                                 
/* 2920 */                                 PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2921 */                                 _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2922 */                                 _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fif_005f8);
/*      */                                 
/* 2924 */                                 _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2925 */                                 int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2926 */                                 if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                   for (;;) {
/* 2928 */                                     out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 2929 */                                     out.print(ALERTCONFIG_TEXT);
/* 2930 */                                     out.write("</a> </td>\n  ");
/* 2931 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2932 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2936 */                                 if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2937 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                                 }
/*      */                                 
/* 2940 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2941 */                                 out.write(10);
/* 2942 */                                 out.write(9);
/*      */                                 
/* 2944 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2945 */                                 _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2946 */                                 _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fif_005f8);
/*      */                                 
/* 2948 */                                 _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2949 */                                 int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2950 */                                 if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                   for (;;) {
/* 2952 */                                     out.write("\n\n    <td height=\"21\" class=\"leftlinkstd\">\n    ");
/*      */                                     
/* 2954 */                                     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2955 */                                     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 2956 */                                     _jspx_th_c_005fif_005f9.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                     
/* 2958 */                                     _jspx_th_c_005fif_005f9.setTest("${param.method=='editScript'}");
/* 2959 */                                     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 2960 */                                     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                       for (;;) {
/* 2962 */                                         out.write("\n    <a href=\"/showresource.do?resourceid=");
/* 2963 */                                         if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fif_005f9, _jspx_page_context))
/*      */                                           return;
/* 2965 */                                         out.write("&method=showResourceForResourceID&alert=true");
/* 2966 */                                         out.print(haid1);
/* 2967 */                                         out.write("\"  class=\"new-left-links\">\n    ");
/* 2968 */                                         out.print(ALERTCONFIG_TEXT);
/* 2969 */                                         out.write("\n    </a>\n    ");
/* 2970 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 2971 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2975 */                                     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 2976 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                     }
/*      */                                     
/* 2979 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2980 */                                     out.write("\n    ");
/*      */                                     
/* 2982 */                                     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2983 */                                     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2984 */                                     _jspx_th_c_005fif_005f10.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                     
/* 2986 */                                     _jspx_th_c_005fif_005f10.setTest("${param.method!='editScript'}");
/* 2987 */                                     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2988 */                                     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                       for (;;) {
/* 2990 */                                         out.write("\n    <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 2991 */                                         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                           return;
/* 2993 */                                         out.write("&mtype=");
/* 2994 */                                         out.print(mon_type);
/* 2995 */                                         out.write("\" class=\"new-left-links\">\n    ");
/* 2996 */                                         out.print(ALERTCONFIG_TEXT);
/* 2997 */                                         out.write("\n    </a>\n    ");
/* 2998 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2999 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3003 */                                     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3004 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                                     }
/*      */                                     
/* 3007 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3008 */                                     out.write("\n    </td>\n");
/* 3009 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 3010 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3014 */                                 if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 3015 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                                 }
/*      */                                 
/* 3018 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 3019 */                                 out.write("\n  </tr>\n  ");
/* 3020 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3021 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3025 */                             if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3026 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                             }
/*      */                             
/* 3029 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3030 */                             out.write(10);
/*      */                             
/* 3032 */                             IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3033 */                             _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3034 */                             _jspx_th_c_005fif_005f11.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3036 */                             _jspx_th_c_005fif_005f11.setTest("${param.type=='Script Monitor'}");
/* 3037 */                             int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3038 */                             if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                               for (;;) {
/* 3040 */                                 out.write(10);
/* 3041 */                                 out.write(32);
/* 3042 */                                 out.write(32);
/*      */                                 
/* 3044 */                                 IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3045 */                                 _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3046 */                                 _jspx_th_c_005fif_005f12.setParent(_jspx_th_c_005fif_005f11);
/*      */                                 
/* 3048 */                                 _jspx_th_c_005fif_005f12.setTest("${!empty ADMIN || !empty DEMO }");
/* 3049 */                                 int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3050 */                                 if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                                   for (;;) {
/* 3052 */                                     out.write(10);
/* 3053 */                                     out.write(32);
/* 3054 */                                     out.write(32);
/*      */                                     
/* 3056 */                                     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3057 */                                     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3058 */                                     _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fif_005f12);
/*      */                                     
/* 3060 */                                     _jspx_th_c_005fif_005f13.setTest("${param.method=='editScript'}");
/* 3061 */                                     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3062 */                                     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                                       for (;;) {
/* 3064 */                                         out.write("\n\t <tr>\n  ");
/*      */                                         
/* 3066 */                                         PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3067 */                                         _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3068 */                                         _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fif_005f13);
/*      */                                         
/* 3070 */                                         _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 3071 */                                         int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3072 */                                         if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                           for (;;) {
/* 3074 */                                             out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3075 */                                             out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 3076 */                                             out.write("</a> </td>\n  ");
/* 3077 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3078 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3082 */                                         if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3083 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                                         }
/*      */                                         
/* 3086 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3087 */                                         out.write("\n        ");
/*      */                                         
/* 3089 */                                         NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3090 */                                         _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 3091 */                                         _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f13);
/*      */                                         
/* 3093 */                                         _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 3094 */                                         int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 3095 */                                         if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                                           for (;;) {
/* 3097 */                                             out.write("\n\n\n    <td height=\"21\" class=\"leftlinkstd\">\n\n    <a href=\"/showresource.do?resourceid=");
/* 3098 */                                             if (_jspx_meth_c_005fout_005f12(_jspx_th_logic_005fnotPresent_005f1, _jspx_page_context))
/*      */                                               return;
/* 3100 */                                             out.write("&method=showResourceForResourceID&reports=true");
/* 3101 */                                             out.print(haid1);
/* 3102 */                                             out.write("\"  class=\"new-left-links\">\n    ");
/* 3103 */                                             out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 3104 */                                             out.write("\n    </a>\n     </td>\n");
/* 3105 */                                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 3106 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3110 */                                         if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 3111 */                                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                                         }
/*      */                                         
/* 3114 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 3115 */                                         out.write("\n  </tr>\n  ");
/* 3116 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3117 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3121 */                                     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3122 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                                     }
/*      */                                     
/* 3125 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3126 */                                     out.write(10);
/* 3127 */                                     out.write(32);
/* 3128 */                                     out.write(32);
/* 3129 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3130 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3134 */                                 if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3135 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                                 }
/*      */                                 
/* 3138 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3139 */                                 out.write("\n\n  ");
/*      */                                 
/* 3141 */                                 IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3142 */                                 _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3143 */                                 _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f11);
/*      */                                 
/* 3145 */                                 _jspx_th_c_005fif_005f14.setTest("${!empty ADMIN || !empty DEMO }");
/* 3146 */                                 int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3147 */                                 if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                                   for (;;) {
/* 3149 */                                     out.write(10);
/* 3150 */                                     out.write(32);
/* 3151 */                                     out.write(32);
/*      */                                     
/* 3153 */                                     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3154 */                                     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 3155 */                                     _jspx_th_c_005fif_005f15.setParent(_jspx_th_c_005fif_005f14);
/*      */                                     
/* 3157 */                                     _jspx_th_c_005fif_005f15.setTest("${param.method!='editScript'}");
/* 3158 */                                     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 3159 */                                     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                                       for (;;) {
/* 3161 */                                         out.write("\n  <tr>\n");
/*      */                                         
/* 3163 */                                         PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3164 */                                         _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3165 */                                         _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fif_005f15);
/*      */                                         
/* 3167 */                                         _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 3168 */                                         int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3169 */                                         if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                           for (;;) {
/* 3171 */                                             out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3172 */                                             out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 3173 */                                             out.write("</a> </td>\n  ");
/* 3174 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3175 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3179 */                                         if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3180 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                                         }
/*      */                                         
/* 3183 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3184 */                                         out.write("\n        ");
/*      */                                         
/* 3186 */                                         NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3187 */                                         _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 3188 */                                         _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_c_005fif_005f15);
/*      */                                         
/* 3190 */                                         _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 3191 */                                         int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 3192 */                                         if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                                           for (;;) {
/* 3194 */                                             out.write("\n\n    <td class=\"leftlinkstd\"><a href=\"javascript:enableReports()\" class=\"new-left-links\">");
/* 3195 */                                             out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 3196 */                                             out.write("</a></td>\n   </td>\n");
/* 3197 */                                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3198 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3202 */                                         if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 3203 */                                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                                         }
/*      */                                         
/* 3206 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 3207 */                                         out.write("\n  </tr>\n  ");
/* 3208 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 3209 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3213 */                                     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 3214 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                                     }
/*      */                                     
/* 3217 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 3218 */                                     out.write(10);
/* 3219 */                                     out.write(32);
/* 3220 */                                     out.write(32);
/* 3221 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3222 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3226 */                                 if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3227 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                                 }
/*      */                                 
/* 3230 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3231 */                                 out.write(10);
/* 3232 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3233 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3237 */                             if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3238 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                             }
/*      */                             
/* 3241 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3242 */                             out.write(10);
/* 3243 */                             out.write(10);
/*      */                             
/* 3245 */                             PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3246 */                             _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3247 */                             _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3249 */                             _jspx_th_logic_005fpresent_005f3.setRole("ENTERPRISEADMIN");
/* 3250 */                             int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3251 */                             if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                               for (;;) {
/* 3253 */                                 out.write("\n  \n");
/*      */                                 
/* 3255 */                                 PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3256 */                                 _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3257 */                                 _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */                                 
/* 3259 */                                 _jspx_th_logic_005fpresent_005f4.setRole("DEMO");
/* 3260 */                                 int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3261 */                                 if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                   for (;;) {
/* 3263 */                                     out.write("\n<tr>\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3264 */                                     out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3265 */                                     out.write("</a> </td> \n</tr>\n");
/* 3266 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3267 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3271 */                                 if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3272 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                                 }
/*      */                                 
/* 3275 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3276 */                                 out.write(10);
/* 3277 */                                 out.write(10);
/*      */                                 
/* 3279 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3280 */                                 _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 3281 */                                 _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */                                 
/* 3283 */                                 _jspx_th_logic_005fnotPresent_005f3.setRole("DEMO");
/* 3284 */                                 int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 3285 */                                 if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                                   for (;;) {
/* 3287 */                                     out.write(10);
/* 3288 */                                     if (com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.isSsoEnabled()) {
/* 3289 */                                       out.write("\n\t<tr>\n   <td height=\"21\" class=\"leftlinkstd\">\n   ");
/*      */                                       
/* 3291 */                                       String temphaid = request.getParameter("haid");
/*      */                                       try
/*      */                                       {
/* 3294 */                                         Integer.parseInt(temphaid);
/*      */                                         
/* 3296 */                                         if (rtype.equals("Ping Monitor")) {
/* 3297 */                                           out.write("\n <a target=\"mas_window\" href=\"/updateScript.do?haid=");
/* 3298 */                                           if (_jspx_meth_c_005fout_005f13(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3300 */                                           out.write("&method=editScript");
/* 3301 */                                           out.print(appendParams);
/* 3302 */                                           out.write("&resourceid=");
/* 3303 */                                           if (_jspx_meth_c_005fout_005f14(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3305 */                                           out.write("&aam_jump=true&type=");
/* 3306 */                                           out.print(request.getParameter("type"));
/* 3307 */                                           if (_jspx_meth_c_005fout_005f15(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3309 */                                           out.write("&resourcename=");
/* 3310 */                                           out.print(resourceName);
/* 3311 */                                           out.write("&mtype=");
/* 3312 */                                           out.print(mon_type);
/* 3313 */                                           out.write("\"  class=\"new-left-links\">\n\n");
/*      */                                         } else {
/* 3315 */                                           out.write("\n    <a target=\"mas_window\" href=\"/updateScript.do?haid=");
/* 3316 */                                           if (_jspx_meth_c_005fout_005f16(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3318 */                                           out.write("&method=editScript");
/* 3319 */                                           out.print(appendParams);
/* 3320 */                                           out.write("&resourceid=");
/* 3321 */                                           if (_jspx_meth_c_005fout_005f17(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3323 */                                           out.write("&aam_jump=true&type=");
/* 3324 */                                           out.print(request.getParameter("type"));
/* 3325 */                                           if (_jspx_meth_c_005fout_005f18(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3327 */                                           out.write("&resourcename=");
/* 3328 */                                           out.print(resourceName);
/* 3329 */                                           out.write("&mtype=");
/* 3330 */                                           out.print(mon_type);
/* 3331 */                                           out.write("\"  class=\"new-left-links\">\n   ");
/*      */                                         }
/*      */                                         
/*      */                                       }
/*      */                                       catch (NumberFormatException ne)
/*      */                                       {
/* 3337 */                                         if (rtype.equals("Ping Monitor")) {
/* 3338 */                                           out.write("\n<a target=\"mas_window\" href=\"/showresource.do?resourceid=");
/* 3339 */                                           out.print(request.getParameter("resourceid"));
/* 3340 */                                           out.write("&type=");
/* 3341 */                                           out.print(request.getParameter("type"));
/* 3342 */                                           out.write("&moname=");
/* 3343 */                                           out.print(moname);
/* 3344 */                                           out.write("&method=showdetails&resourcename=");
/* 3345 */                                           out.print(request.getParameter("resourcename"));
/* 3346 */                                           out.write("&aam_jump=true&editPage=true\" class=\"new-left-links\">\n\n");
/*      */                                         } else {
/* 3348 */                                           out.write("\n\n    <a target=\"mas_window\" href=\"/updateScript.do?method=editScript");
/* 3349 */                                           out.print(appendParams);
/* 3350 */                                           out.write("&resourceid=");
/* 3351 */                                           if (_jspx_meth_c_005fout_005f19(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3353 */                                           out.write("&aam_jump=true&type=");
/* 3354 */                                           out.print(request.getParameter("type"));
/* 3355 */                                           if (_jspx_meth_c_005fout_005f20(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                             return;
/* 3357 */                                           out.write("&resourcename=");
/* 3358 */                                           out.print(resourceName);
/* 3359 */                                           out.write("&mtype=");
/* 3360 */                                           out.print(mon_type);
/* 3361 */                                           out.write("\"  class=\"new-left-links\">\n   ");
/*      */                                         }
/*      */                                       }
/*      */                                       
/* 3365 */                                       out.write(10);
/* 3366 */                                       out.write(32);
/* 3367 */                                       out.write(32);
/* 3368 */                                       out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3369 */                                       out.write("\n   </td> </tr>\n   ");
/*      */                                     }
/* 3371 */                                     out.write(10);
/* 3372 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 3373 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3377 */                                 if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 3378 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                                 }
/*      */                                 
/* 3381 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 3382 */                                 out.write(10);
/* 3383 */                                 out.write(32);
/* 3384 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3385 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3389 */                             if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3390 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                             }
/*      */                             
/* 3393 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3394 */                             out.write(10);
/* 3395 */                             out.write(32);
/* 3396 */                             out.write(10);
/*      */                             
/* 3398 */                             IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3399 */                             _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 3400 */                             _jspx_th_c_005fif_005f16.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3402 */                             _jspx_th_c_005fif_005f16.setTest("${(!empty ADMIN || !empty DEMO) }");
/* 3403 */                             int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 3404 */                             if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                               for (;;) {
/* 3406 */                                 out.write("\n  <tr>\n");
/*      */                                 
/* 3408 */                                 PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3409 */                                 _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3410 */                                 _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fif_005f16);
/*      */                                 
/* 3412 */                                 _jspx_th_logic_005fpresent_005f5.setRole("DEMO");
/* 3413 */                                 int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3414 */                                 if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                   for (;;) {
/* 3416 */                                     out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3417 */                                     out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3418 */                                     out.write("</a> </td>\n  ");
/* 3419 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3420 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3424 */                                 if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3425 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                                 }
/*      */                                 
/* 3428 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3429 */                                 out.write("\n        ");
/*      */                                 
/* 3431 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3432 */                                 _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/* 3433 */                                 _jspx_th_logic_005fnotPresent_005f4.setParent(_jspx_th_c_005fif_005f16);
/*      */                                 
/* 3435 */                                 _jspx_th_logic_005fnotPresent_005f4.setRole("DEMO");
/* 3436 */                                 int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/* 3437 */                                 if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */                                   for (;;) {
/* 3439 */                                     out.write("\n\n\n    <td height=\"21\" class=\"leftlinkstd\">\n    ");
/*      */                                     
/* 3441 */                                     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3442 */                                     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 3443 */                                     _jspx_th_c_005fif_005f17.setParent(_jspx_th_logic_005fnotPresent_005f4);
/*      */                                     
/* 3445 */                                     _jspx_th_c_005fif_005f17.setTest("${param.actionmethod!='editScript' }");
/* 3446 */                                     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 3447 */                                     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                                       for (;;) {
/* 3449 */                                         out.write("\n    ");
/*      */                                         
/* 3451 */                                         String temphaid = request.getParameter("haid");
/*      */                                         try
/*      */                                         {
/* 3454 */                                           Integer.parseInt(temphaid);
/*      */                                           
/* 3456 */                                           if (rtype.equals("Ping Monitor")) {
/* 3457 */                                             out.write("\n<a href=\"javascript:toggleDiv('edit')\" class=\"new-left-links\">\n");
/*      */                                           } else {
/* 3459 */                                             out.write("\n\n<a href=\"/updateScript.do?haid=");
/* 3460 */                                             if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                               return;
/* 3462 */                                             out.write("&method=editScript");
/* 3463 */                                             out.print(appendParams);
/* 3464 */                                             out.write("&resourceid=");
/* 3465 */                                             if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                               return;
/* 3467 */                                             out.write("&type=");
/* 3468 */                                             out.print(rtype);
/* 3469 */                                             if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                               return;
/* 3471 */                                             out.write("&resourcename=");
/* 3472 */                                             out.print(resourceName);
/* 3473 */                                             out.write("&mtype=");
/* 3474 */                                             out.print(mon_type);
/* 3475 */                                             out.write("\"  class=\"new-left-links\">\n    \t");
/*      */                                           }
/*      */                                           
/*      */                                         }
/*      */                                         catch (NumberFormatException ne)
/*      */                                         {
/* 3481 */                                           if (rtype.equals("Ping Monitor")) {
/* 3482 */                                             out.write("\n<a href=\"javascript:toggleDiv('edit')\" class=\"new-left-links\">\n");
/*      */                                           } else {
/* 3484 */                                             out.write("\n\n\n<a href=\"/updateScript.do?method=editScript");
/* 3485 */                                             out.print(appendParams);
/* 3486 */                                             out.write("&resourceid=");
/* 3487 */                                             if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                               return;
/* 3489 */                                             out.write("&type=");
/* 3490 */                                             out.print(rtype);
/* 3491 */                                             if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                               return;
/* 3493 */                                             out.write("&resourcename=");
/* 3494 */                                             out.print(resourceName);
/* 3495 */                                             out.write("&mtype=");
/* 3496 */                                             out.print(mon_type);
/* 3497 */                                             out.write("\"  class=\"new-left-links\">\n    \t");
/*      */                                           }
/*      */                                         }
/*      */                                         
/* 3501 */                                         out.write(10);
/* 3502 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 3503 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3507 */                                     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 3508 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */                                     }
/*      */                                     
/* 3511 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 3512 */                                     out.write("\n\n    ");
/* 3513 */                                     out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3514 */                                     out.write("\n    ");
/* 3515 */                                     if (_jspx_meth_c_005fif_005f18(_jspx_th_logic_005fnotPresent_005f4, _jspx_page_context))
/*      */                                       return;
/* 3517 */                                     out.write("</td>\n");
/* 3518 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 3519 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3523 */                                 if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 3524 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4); return;
/*      */                                 }
/*      */                                 
/* 3527 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 3528 */                                 out.write("\n  </tr>\n  ");
/* 3529 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 3530 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3534 */                             if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 3535 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                             }
/*      */                             
/* 3538 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 3539 */                             out.write("\n\n  ");
/*      */                             
/* 3541 */                             IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3542 */                             _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 3543 */                             _jspx_th_c_005fif_005f19.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3545 */                             _jspx_th_c_005fif_005f19.setTest("${!empty ADMIN || !empty DEMO }");
/* 3546 */                             int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 3547 */                             if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                               for (;;) {
/* 3549 */                                 out.write(10);
/*      */                                 
/* 3551 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f5 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3552 */                                 _jspx_th_logic_005fnotPresent_005f5.setPageContext(_jspx_page_context);
/* 3553 */                                 _jspx_th_logic_005fnotPresent_005f5.setParent(_jspx_th_c_005fif_005f19);
/*      */                                 
/* 3555 */                                 _jspx_th_logic_005fnotPresent_005f5.setRole("DEMO");
/* 3556 */                                 int _jspx_eval_logic_005fnotPresent_005f5 = _jspx_th_logic_005fnotPresent_005f5.doStartTag();
/* 3557 */                                 if (_jspx_eval_logic_005fnotPresent_005f5 != 0) {
/*      */                                   for (;;) {
/* 3559 */                                     out.write("\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\"><a href=\"javascript:confirmDelete();\" class=\"new-left-links\">");
/* 3560 */                                     out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3561 */                                     out.write("</a></td>\n  </tr>\n");
/* 3562 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f5.doAfterBody();
/* 3563 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3567 */                                 if (_jspx_th_logic_005fnotPresent_005f5.doEndTag() == 5) {
/* 3568 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5); return;
/*      */                                 }
/*      */                                 
/* 3571 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5);
/* 3572 */                                 out.write(10);
/*      */                                 
/* 3574 */                                 PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3575 */                                 _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 3576 */                                 _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fif_005f19);
/*      */                                 
/* 3578 */                                 _jspx_th_logic_005fpresent_005f6.setRole("DEMO");
/* 3579 */                                 int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 3580 */                                 if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                                   for (;;) {
/* 3582 */                                     out.write("\n\n<td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 3583 */                                     out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3584 */                                     out.write("</a></td>\n\n");
/* 3585 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 3586 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3590 */                                 if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 3591 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                                 }
/*      */                                 
/* 3594 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 3595 */                                 out.write("\n\n  ");
/* 3596 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 3597 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3601 */                             if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 3602 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */                             }
/*      */                             
/* 3605 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 3606 */                             out.write(10);
/* 3607 */                             out.write(32);
/* 3608 */                             out.write(32);
/*      */                             
/* 3610 */                             IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3611 */                             _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 3612 */                             _jspx_th_c_005fif_005f20.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3614 */                             _jspx_th_c_005fif_005f20.setTest("${!empty ADMIN || !empty DEMO || !empty OPERATOR}");
/* 3615 */                             int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 3616 */                             if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */                               for (;;) {
/* 3618 */                                 out.write("\n\n  <tr>\n\n  ");
/*      */                                 
/* 3620 */                                 if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                                 {
/*      */ 
/* 3623 */                                   out.write(10);
/* 3624 */                                   out.write(9);
/*      */                                   
/* 3626 */                                   PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3627 */                                   _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 3628 */                                   _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_c_005fif_005f20);
/*      */                                   
/* 3630 */                                   _jspx_th_logic_005fpresent_005f7.setRole("DEMO");
/* 3631 */                                   int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 3632 */                                   if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                                     for (;;) {
/* 3634 */                                       out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3635 */                                       out.print(FormatUtil.getString("Manage"));
/* 3636 */                                       out.write("</a> </td>\n  ");
/* 3637 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 3638 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3642 */                                   if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 3643 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                                   }
/*      */                                   
/* 3646 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 3647 */                                   out.write("\n        ");
/*      */                                   
/* 3649 */                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f6 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3650 */                                   _jspx_th_logic_005fnotPresent_005f6.setPageContext(_jspx_page_context);
/* 3651 */                                   _jspx_th_logic_005fnotPresent_005f6.setParent(_jspx_th_c_005fif_005f20);
/*      */                                   
/* 3653 */                                   _jspx_th_logic_005fnotPresent_005f6.setRole("DEMO");
/* 3654 */                                   int _jspx_eval_logic_005fnotPresent_005f6 = _jspx_th_logic_005fnotPresent_005f6.doStartTag();
/* 3655 */                                   if (_jspx_eval_logic_005fnotPresent_005f6 != 0) {
/*      */                                     for (;;) {
/* 3657 */                                       out.write("\n\n\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\">");
/* 3658 */                                       out.print(FormatUtil.getString("Manage"));
/* 3659 */                                       out.write("</A></td>\n");
/* 3660 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f6.doAfterBody();
/* 3661 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3665 */                                   if (_jspx_th_logic_005fnotPresent_005f6.doEndTag() == 5) {
/* 3666 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6); return;
/*      */                                   }
/*      */                                   
/* 3669 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6);
/* 3670 */                                   out.write("\n    ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 3676 */                                   out.write(10);
/*      */                                   
/* 3678 */                                   PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3679 */                                   _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 3680 */                                   _jspx_th_logic_005fpresent_005f8.setParent(_jspx_th_c_005fif_005f20);
/*      */                                   
/* 3682 */                                   _jspx_th_logic_005fpresent_005f8.setRole("DEMO");
/* 3683 */                                   int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 3684 */                                   if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                                     for (;;) {
/* 3686 */                                       out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3687 */                                       out.print(FormatUtil.getString("UnManage"));
/* 3688 */                                       out.write("</a> </td>\n  ");
/* 3689 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 3690 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3694 */                                   if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 3695 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8); return;
/*      */                                   }
/*      */                                   
/* 3698 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 3699 */                                   out.write("\n        ");
/*      */                                   
/* 3701 */                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f7 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3702 */                                   _jspx_th_logic_005fnotPresent_005f7.setPageContext(_jspx_page_context);
/* 3703 */                                   _jspx_th_logic_005fnotPresent_005f7.setParent(_jspx_th_c_005fif_005f20);
/*      */                                   
/* 3705 */                                   _jspx_th_logic_005fnotPresent_005f7.setRole("DEMO");
/* 3706 */                                   int _jspx_eval_logic_005fnotPresent_005f7 = _jspx_th_logic_005fnotPresent_005f7.doStartTag();
/* 3707 */                                   if (_jspx_eval_logic_005fnotPresent_005f7 != 0) {
/*      */                                     for (;;) {
/* 3709 */                                       out.write("\n\n\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\">");
/* 3710 */                                       out.print(FormatUtil.getString("UnManage"));
/* 3711 */                                       out.write("</A></td>\n");
/* 3712 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f7.doAfterBody();
/* 3713 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3717 */                                   if (_jspx_th_logic_005fnotPresent_005f7.doEndTag() == 5) {
/* 3718 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7); return;
/*      */                                   }
/*      */                                   
/* 3721 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7);
/* 3722 */                                   out.write("\n    ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3726 */                                 out.write("\n  </tr>\n  ");
/* 3727 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 3728 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3732 */                             if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 3733 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20); return;
/*      */                             }
/*      */                             
/* 3736 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 3737 */                             out.write("\n   ");
/*      */                             
/* 3739 */                             NotPresentTag _jspx_th_logic_005fnotPresent_005f8 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3740 */                             _jspx_th_logic_005fnotPresent_005f8.setPageContext(_jspx_page_context);
/* 3741 */                             _jspx_th_logic_005fnotPresent_005f8.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3743 */                             _jspx_th_logic_005fnotPresent_005f8.setRole("DEMO");
/* 3744 */                             int _jspx_eval_logic_005fnotPresent_005f8 = _jspx_th_logic_005fnotPresent_005f8.doStartTag();
/* 3745 */                             if (_jspx_eval_logic_005fnotPresent_005f8 != 0) {
/*      */                               for (;;) {
/* 3747 */                                 out.write("\n    ");
/*      */                                 
/* 3749 */                                 String resourceid_poll = request.getParameter("resourceid");
/* 3750 */                                 String resourcetype_poll = request.getParameter("type");
/*      */                                 
/* 3752 */                                 out.write("\n      <tr>\n      <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n      <a href=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 3753 */                                 out.print(resourceid_poll);
/* 3754 */                                 out.write("&baseid=");
/* 3755 */                                 out.print(baseid);
/* 3756 */                                 out.write("&resourcetype=");
/* 3757 */                                 out.print(resourcetype_poll);
/* 3758 */                                 out.write("\" class=\"new-left-links\"> ");
/* 3759 */                                 out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3760 */                                 out.write("</a></td>\n    </tr>\n    ");
/* 3761 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f8.doAfterBody();
/* 3762 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3766 */                             if (_jspx_th_logic_005fnotPresent_005f8.doEndTag() == 5) {
/* 3767 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8); return;
/*      */                             }
/*      */                             
/* 3770 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8);
/* 3771 */                             out.write("\n    ");
/*      */                             
/* 3773 */                             PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3774 */                             _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 3775 */                             _jspx_th_logic_005fpresent_005f9.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3777 */                             _jspx_th_logic_005fpresent_005f9.setRole("DEMO");
/* 3778 */                             int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 3779 */                             if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                               for (;;) {
/* 3781 */                                 out.write("\n          <tr>\n          <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n          <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 3782 */                                 out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3783 */                                 out.write("</a></td>\n          </td>\n    ");
/* 3784 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 3785 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3789 */                             if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 3790 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9); return;
/*      */                             }
/*      */                             
/* 3793 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 3794 */                             out.write("\n    ");
/* 3795 */                             out.write("<!-- $Id$-->\n\n\n  \n");
/*      */                             
/* 3797 */                             if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */                             {
/* 3799 */                               Map<APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 3800 */                               String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                               
/* 3802 */                               String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 3803 */                               String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 3804 */                               if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                               {
/* 3806 */                                 if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                                 {
/*      */ 
/* 3809 */                                   out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3810 */                                   out.print(ciInfoUrl);
/* 3811 */                                   out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3812 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3813 */                                   out.write("</a></td>");
/* 3814 */                                   out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3815 */                                   out.print(ciRLUrl);
/* 3816 */                                   out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3817 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3818 */                                   out.write("</a></td>");
/* 3819 */                                   out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                                 }
/* 3823 */                                 else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                                 {
/*      */ 
/* 3826 */                                   out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 3827 */                                   out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 3828 */                                   out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3829 */                                   out.print(ciInfoUrl);
/* 3830 */                                   out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 3831 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3832 */                                   out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3833 */                                   out.print(ciRLUrl);
/* 3834 */                                   out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 3835 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3836 */                                   out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                                 }
/*      */                               }
/*      */                             }
/*      */                             
/* 3841 */                             out.write("\n \n \n\n");
/* 3842 */                             out.write("\n</table>\n\n <br>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3843 */                             out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 3844 */                             out.write("</td>\n  </tr>\n  <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3845 */                             if (_jspx_meth_c_005fout_005f26(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3847 */                             out.write("&attributeid=");
/* 3848 */                             out.print(healthid);
/* 3849 */                             out.write("')\"\n      class=\"new-left-links\">");
/* 3850 */                             out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3851 */                             out.write("</a> </td>\n    <td><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3852 */                             if (_jspx_meth_c_005fout_005f27(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3854 */                             out.write("&attributeid=");
/* 3855 */                             out.print(healthid);
/* 3856 */                             out.write("')\">");
/* 3857 */                             out.print(getSeverityImageForHealth(alert.getProperty(request.getParameter("resourceid") + "#" + healthid)));
/* 3858 */                             out.write("</a></td>\n  </tr>\n  <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3859 */                             if (_jspx_meth_c_005fout_005f28(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3861 */                             out.write("&attributeid=");
/* 3862 */                             out.print(availabilityid);
/* 3863 */                             out.write("')\"\n      class=\"new-left-links\">");
/* 3864 */                             out.print(FormatUtil.getString("Availability"));
/* 3865 */                             out.write("</a> </td>\n    <td><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3866 */                             if (_jspx_meth_c_005fout_005f29(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3868 */                             out.write("&attributeid=");
/* 3869 */                             out.print(availabilityid);
/* 3870 */                             out.write("')\">");
/* 3871 */                             out.print(getSeverityImageForAvailability(alert.getProperty(request.getParameter("resourceid") + "#" + availabilityid)));
/* 3872 */                             out.write("</a></td>\n  </tr>\n</table>\n\n<br>\n\n<br>\n");
/* 3873 */                             out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */                             
/*      */ 
/*      */ 
/* 3877 */                             boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 3878 */                             if (EnterpriseUtil.isIt360MSPEdition)
/*      */                             {
/* 3880 */                               showAssociatedBSG = false;
/*      */                               
/*      */ 
/*      */ 
/* 3884 */                               CustomerManagementAPI.getInstance();Properties assBsgSiteProp = CustomerManagementAPI.getSiteProp(request);
/* 3885 */                               CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 3886 */                               String loginName = request.getUserPrincipal().getName();
/* 3887 */                               CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                               
/* 3889 */                               if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                               {
/* 3891 */                                 showAssociatedBSG = true;
/*      */                               }
/*      */                             }
/* 3894 */                             String monitorType = request.getParameter("type");
/* 3895 */                             ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 3896 */                             boolean mon = conf1.isConfMonitor(monitorType);
/* 3897 */                             if (showAssociatedBSG)
/*      */                             {
/* 3899 */                               Hashtable associatedmgs = new Hashtable();
/* 3900 */                               String resId = request.getParameter("resourceid");
/* 3901 */                               request.setAttribute("associatedmgs", FaultUtil.getAdminAssociatedMG(resId, request));
/* 3902 */                               if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                               {
/* 3904 */                                 mon = false;
/*      */                               }
/*      */                               
/* 3907 */                               if (!mon)
/*      */                               {
/* 3909 */                                 out.write(10);
/* 3910 */                                 out.write(10);
/*      */                                 
/* 3912 */                                 IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3913 */                                 _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 3914 */                                 _jspx_th_c_005fif_005f21.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 3916 */                                 _jspx_th_c_005fif_005f21.setTest("${!empty associatedmgs}");
/* 3917 */                                 int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 3918 */                                 if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */                                   for (;;) {
/* 3920 */                                     out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3921 */                                     out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3922 */                                     out.write("</td>\n        </tr>\n        ");
/*      */                                     
/* 3924 */                                     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3925 */                                     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3926 */                                     _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f21);
/*      */                                     
/* 3928 */                                     _jspx_th_c_005fforEach_005f0.setVar("ha");
/*      */                                     
/* 3930 */                                     _jspx_th_c_005fforEach_005f0.setItems("${associatedmgs}");
/*      */                                     
/* 3932 */                                     _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 3933 */                                     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                                     try {
/* 3935 */                                       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3936 */                                       if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                         for (;;) {
/* 3938 */                                           out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 3939 */                                           if (_jspx_meth_c_005fout_005f30(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3997 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3998 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3941 */                                           out.write("&method=showApplication\" title=\"");
/* 3942 */                                           if (_jspx_meth_c_005fout_005f31(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3997 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3998 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3944 */                                           out.write("\"  class=\"new-left-links\">\n         ");
/* 3945 */                                           if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3997 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3998 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3947 */                                           out.write("\n    \t");
/* 3948 */                                           out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3949 */                                           out.write("\n         </a></td>\n        <td>");
/*      */                                           
/* 3951 */                                           PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3952 */                                           _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 3953 */                                           _jspx_th_logic_005fpresent_005f10.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                           
/* 3955 */                                           _jspx_th_logic_005fpresent_005f10.setRole("ADMIN");
/* 3956 */                                           int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 3957 */                                           if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */                                             for (;;) {
/* 3959 */                                               out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 3960 */                                               if (_jspx_meth_c_005fout_005f33(_jspx_th_logic_005fpresent_005f10, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3997 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 3998 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 3962 */                                               out.write(39);
/* 3963 */                                               out.write(44);
/* 3964 */                                               out.write(39);
/* 3965 */                                               out.print(resId);
/* 3966 */                                               out.write(39);
/* 3967 */                                               out.write(44);
/* 3968 */                                               out.write(39);
/* 3969 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3970 */                                               out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 3971 */                                               out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3972 */                                               out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 3973 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 3974 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3978 */                                           if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 3979 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/*      */                                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3997 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3998 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3982 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 3983 */                                           out.write("</td>\n        </tr>\n\t");
/* 3984 */                                           int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3985 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3989 */                                       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3997 */                                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 3998 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Throwable _jspx_exception)
/*      */                                     {
/*      */                                       for (;;)
/*      */                                       {
/* 3993 */                                         int tmp11101_11100 = 0; int[] tmp11101_11098 = _jspx_push_body_count_c_005fforEach_005f0; int tmp11103_11102 = tmp11101_11098[tmp11101_11100];tmp11101_11098[tmp11101_11100] = (tmp11103_11102 - 1); if (tmp11103_11102 <= 0) break;
/* 3994 */                                         out = _jspx_page_context.popBody(); }
/* 3995 */                                       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                                     } finally {
/* 3997 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 3998 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                                     }
/* 4000 */                                     out.write("\n      </table>\n ");
/* 4001 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 4002 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4006 */                                 if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 4007 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21); return;
/*      */                                 }
/*      */                                 
/* 4010 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 4011 */                                 out.write(10);
/* 4012 */                                 out.write(32);
/*      */                                 
/* 4014 */                                 IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4015 */                                 _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/* 4016 */                                 _jspx_th_c_005fif_005f22.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 4018 */                                 _jspx_th_c_005fif_005f22.setTest("${empty associatedmgs}");
/* 4019 */                                 int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/* 4020 */                                 if (_jspx_eval_c_005fif_005f22 != 0) {
/*      */                                   for (;;) {
/* 4022 */                                     out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 4023 */                                     out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 4024 */                                     out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 4025 */                                     out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 4026 */                                     out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 4027 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/* 4028 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4032 */                                 if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/* 4033 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22); return;
/*      */                                 }
/*      */                                 
/* 4036 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 4037 */                                 out.write(10);
/* 4038 */                                 out.write(32);
/* 4039 */                                 out.write(10);
/*      */ 
/*      */                               }
/* 4042 */                               else if (mon)
/*      */                               {
/*      */ 
/*      */ 
/* 4046 */                                 out.write(10);
/*      */                                 
/* 4048 */                                 IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4049 */                                 _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/* 4050 */                                 _jspx_th_c_005fif_005f23.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 4052 */                                 _jspx_th_c_005fif_005f23.setTest("${!empty associatedmgs}");
/* 4053 */                                 int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/* 4054 */                                 if (_jspx_eval_c_005fif_005f23 != 0) {
/*      */                                   for (;;) {
/* 4056 */                                     out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b>");
/* 4057 */                                     if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f23, _jspx_page_context))
/*      */                                       return;
/* 4059 */                                     out.write("</b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                                     
/* 4061 */                                     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4062 */                                     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 4063 */                                     _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f23);
/*      */                                     
/* 4065 */                                     _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                                     
/* 4067 */                                     _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                                     
/* 4069 */                                     _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 4070 */                                     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                                     try {
/* 4072 */                                       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 4073 */                                       if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                                         for (;;) {
/* 4075 */                                           out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 4076 */                                           if (_jspx_meth_c_005fout_005f34(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4137 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 4138 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 4078 */                                           out.write("&method=showApplication\" title=\"");
/* 4079 */                                           if (_jspx_meth_c_005fout_005f35(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4137 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 4138 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 4081 */                                           out.write("\"  class=\"staticlinks\">\n         ");
/* 4082 */                                           if (_jspx_meth_c_005fset_005f4(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4137 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 4138 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 4084 */                                           out.write("\n    \t");
/* 4085 */                                           out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 4086 */                                           out.write("</a></span>\t\n\t\t ");
/*      */                                           
/* 4088 */                                           PresentTag _jspx_th_logic_005fpresent_005f11 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4089 */                                           _jspx_th_logic_005fpresent_005f11.setPageContext(_jspx_page_context);
/* 4090 */                                           _jspx_th_logic_005fpresent_005f11.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                                           
/* 4092 */                                           _jspx_th_logic_005fpresent_005f11.setRole("ADMIN");
/* 4093 */                                           int _jspx_eval_logic_005fpresent_005f11 = _jspx_th_logic_005fpresent_005f11.doStartTag();
/* 4094 */                                           if (_jspx_eval_logic_005fpresent_005f11 != 0) {
/*      */                                             for (;;) {
/* 4096 */                                               out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 4097 */                                               if (_jspx_meth_c_005fout_005f37(_jspx_th_logic_005fpresent_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4137 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 4138 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 4099 */                                               out.write(39);
/* 4100 */                                               out.write(44);
/* 4101 */                                               out.write(39);
/* 4102 */                                               out.print(resId);
/* 4103 */                                               out.write(39);
/* 4104 */                                               out.write(44);
/* 4105 */                                               out.write(39);
/* 4106 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 4107 */                                               out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 4108 */                                               out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 4109 */                                               out.write("\"  title=\"");
/* 4110 */                                               if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fpresent_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4137 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 4138 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 4112 */                                               out.write("\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 4113 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f11.doAfterBody();
/* 4114 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 4118 */                                           if (_jspx_th_logic_005fpresent_005f11.doEndTag() == 5) {
/* 4119 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/*      */                                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4137 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 4138 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 4122 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/* 4123 */                                           out.write("\n\n\t\t \t");
/* 4124 */                                           int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 4125 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4129 */                                       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4137 */                                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 4138 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Throwable _jspx_exception)
/*      */                                     {
/*      */                                       for (;;)
/*      */                                       {
/* 4133 */                                         int tmp12127_12126 = 0; int[] tmp12127_12124 = _jspx_push_body_count_c_005fforEach_005f1; int tmp12129_12128 = tmp12127_12124[tmp12127_12126];tmp12127_12124[tmp12127_12126] = (tmp12129_12128 - 1); if (tmp12129_12128 <= 0) break;
/* 4134 */                                         out = _jspx_page_context.popBody(); }
/* 4135 */                                       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                                     } finally {
/* 4137 */                                       _jspx_th_c_005fforEach_005f1.doFinally();
/* 4138 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                     }
/* 4140 */                                     out.write("\n\t\n\t\t\t</td>\n\t ");
/* 4141 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/* 4142 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4146 */                                 if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/* 4147 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23); return;
/*      */                                 }
/*      */                                 
/* 4150 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 4151 */                                 out.write(10);
/* 4152 */                                 out.write(32);
/* 4153 */                                 if (_jspx_meth_c_005fif_005f24(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                   return;
/* 4155 */                                 out.write(32);
/* 4156 */                                 out.write(10);
/*      */                               }
/*      */                               
/*      */                             }
/* 4160 */                             else if (mon)
/*      */                             {
/*      */ 
/* 4163 */                               out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 4164 */                               if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                 return;
/* 4166 */                               out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */                             }
/*      */                             
/*      */ 
/* 4170 */                             out.write(9);
/* 4171 */                             out.write(9);
/* 4172 */                             out.write(10);
/*      */                             
/* 4174 */                             ArrayList menupos = new ArrayList(5);
/* 4175 */                             menupos.add("350");
/* 4176 */                             pageContext.setAttribute("menupos", menupos);
/*      */                             
/* 4178 */                             out.write(10);
/* 4179 */                             out.write(10);
/* 4180 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 4181 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4184 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 4185 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4188 */                         if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 4189 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                         }
/*      */                         
/* 4192 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 4193 */                         out.write(10);
/*      */                         
/* 4195 */                         PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 4196 */                         _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 4197 */                         _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 4199 */                         _jspx_th_tiles_005fput_005f4.setName("UserArea");
/*      */                         
/* 4201 */                         _jspx_th_tiles_005fput_005f4.setType("string");
/* 4202 */                         int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 4203 */                         if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 4204 */                           if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 4205 */                             out = _jspx_page_context.pushBody();
/* 4206 */                             _jspx_th_tiles_005fput_005f4.setBodyContent((BodyContent)out);
/* 4207 */                             _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 4210 */                             out.write("\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n\t");
/*      */                             
/* 4212 */                             Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 4213 */                             String aid = request.getParameter("haid");
/* 4214 */                             String haName = null;
/* 4215 */                             if (aid != null)
/*      */                             {
/* 4217 */                               haName = (String)ht.get(aid);
/*      */                             }
/*      */                             
/* 4220 */                             out.write(10);
/* 4221 */                             out.write(9);
/*      */                             
/* 4223 */                             IfTag _jspx_th_c_005fif_005f25 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4224 */                             _jspx_th_c_005fif_005f25.setPageContext(_jspx_page_context);
/* 4225 */                             _jspx_th_c_005fif_005f25.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4227 */                             _jspx_th_c_005fif_005f25.setTest("${!empty param.haid && param.haid!='null' && (empty invalidhaid)}");
/* 4228 */                             int _jspx_eval_c_005fif_005f25 = _jspx_th_c_005fif_005f25.doStartTag();
/* 4229 */                             if (_jspx_eval_c_005fif_005f25 != 0) {
/*      */                               for (;;) {
/* 4231 */                                 out.write("\n        <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 4232 */                                 out.print(BreadcrumbUtil.getHomePage(request));
/* 4233 */                                 out.write(" &gt; ");
/* 4234 */                                 out.print(BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 4235 */                                 out.write(" &gt; <span class=\"bcactive\"> ");
/* 4236 */                                 out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 4237 */                                 out.write(" </span></td>\n\t");
/* 4238 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f25.doAfterBody();
/* 4239 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4243 */                             if (_jspx_th_c_005fif_005f25.doEndTag() == 5) {
/* 4244 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25); return;
/*      */                             }
/*      */                             
/* 4247 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/* 4248 */                             out.write(10);
/* 4249 */                             out.write(9);
/*      */                             
/* 4251 */                             IfTag _jspx_th_c_005fif_005f26 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4252 */                             _jspx_th_c_005fif_005f26.setPageContext(_jspx_page_context);
/* 4253 */                             _jspx_th_c_005fif_005f26.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4255 */                             _jspx_th_c_005fif_005f26.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid) || param.haid=='null'}");
/* 4256 */                             int _jspx_eval_c_005fif_005f26 = _jspx_th_c_005fif_005f26.doStartTag();
/* 4257 */                             if (_jspx_eval_c_005fif_005f26 != 0) {
/*      */                               for (;;) {
/* 4259 */                                 out.write("\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 4260 */                                 out.print(BreadcrumbUtil.getMonitorsPage());
/* 4261 */                                 out.write(" &gt; ");
/* 4262 */                                 out.print(BreadcrumbUtil.getMonitorResourceTypes(request.getParameter("type")));
/* 4263 */                                 out.write(" &gt; <span class=\"bcactive\"> ");
/* 4264 */                                 out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 4265 */                                 out.write("</span></td>\n\t");
/* 4266 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f26.doAfterBody();
/* 4267 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4271 */                             if (_jspx_th_c_005fif_005f26.doEndTag() == 5) {
/* 4272 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26); return;
/*      */                             }
/*      */                             
/* 4275 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26);
/* 4276 */                             out.write("\n    </tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n  <tr>\n  <td width=\"55%\" height=\"123\" valign=\"top\"> <table width=\"96%\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" class=\"lrtbdarkborder\">\n  <tr>\n          <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 4277 */                             out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 4278 */                             out.write(" </td>\n        </tr>\n        <tr>\n          <td width=\"32%\" class=\"monitorinfoodd\">");
/* 4279 */                             out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 4280 */                             out.write(" </td>\n          <td width=\"68%\" class=\"monitorinfoodd\"> ");
/* 4281 */                             out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 4282 */                             out.write("\n          </td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 4283 */                             out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 4284 */                             out.write(" </td>\n          <td class=\"monitorinfoodd\">");
/* 4285 */                             out.print(monitortype);
/* 4286 */                             out.write("</td>\n        </tr>\n        ");
/* 4287 */                             out.write("<!--$Id$-->\n");
/*      */                             
/* 4289 */                             String hostName = "localhost";
/*      */                             try {
/* 4291 */                               hostName = InetAddress.getLocalHost().getHostName();
/*      */                             } catch (Exception ex) {
/* 4293 */                               ex.printStackTrace();
/*      */                             }
/* 4295 */                             String portNumber = System.getProperty("webserver.port");
/* 4296 */                             String styleClass = "monitorinfoodd";
/* 4297 */                             if ((request.getAttribute("amcreated") != null) && (((String)request.getAttribute("amcreated")).equals("YES"))) {
/* 4298 */                               styleClass = "whitegrayborder-conf-mon";
/*      */                             }
/*      */                             
/* 4301 */                             out.write(10);
/*      */                             
/* 4303 */                             PresentTag _jspx_th_logic_005fpresent_005f12 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4304 */                             _jspx_th_logic_005fpresent_005f12.setPageContext(_jspx_page_context);
/* 4305 */                             _jspx_th_logic_005fpresent_005f12.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4307 */                             _jspx_th_logic_005fpresent_005f12.setRole("ENTERPRISEADMIN");
/* 4308 */                             int _jspx_eval_logic_005fpresent_005f12 = _jspx_th_logic_005fpresent_005f12.doStartTag();
/* 4309 */                             if (_jspx_eval_logic_005fpresent_005f12 != 0) {
/*      */                               for (;;) {
/* 4311 */                                 out.write("\n<tr>\n  <td width=\"30%\" class=\"");
/* 4312 */                                 out.print(styleClass);
/* 4313 */                                 out.write(34);
/* 4314 */                                 out.write(62);
/* 4315 */                                 out.print(FormatUtil.getString("am.webclient.managedserver.name"));
/* 4316 */                                 out.write(" </td>\n  <td width=\"70%\" class=\"");
/* 4317 */                                 out.print(styleClass);
/* 4318 */                                 out.write(34);
/* 4319 */                                 out.write(62);
/* 4320 */                                 out.print(hostName);
/* 4321 */                                 out.write(95);
/* 4322 */                                 out.print(portNumber);
/* 4323 */                                 out.write("</td>\n</tr>\n");
/* 4324 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f12.doAfterBody();
/* 4325 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4329 */                             if (_jspx_th_logic_005fpresent_005f12.doEndTag() == 5) {
/* 4330 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12); return;
/*      */                             }
/*      */                             
/* 4333 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12);
/* 4334 */                             out.write(10);
/* 4335 */                             out.write("\n        <tr>\n          <td class=\"monitorinfoeven\" valign=\"top\">");
/* 4336 */                             out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 4337 */                             out.write("</td>\n          <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4338 */                             out.print(resID);
/* 4339 */                             out.write("&attributeid=");
/* 4340 */                             out.print((String)systeminfo.get("HEALTHID"));
/* 4341 */                             out.write("')\">");
/* 4342 */                             out.print(getSeverityImageForHealth(alert.getProperty(resID + "#" + (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID"))));
/* 4343 */                             out.write("</a>\n\t\t  ");
/* 4344 */                             out.print(getHideAndShowRCAMessage(alert.getProperty(resID + "#" + (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID") + "#" + "MESSAGE"), (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID"), alert.getProperty(resID + "#" + (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID")), resID));
/* 4345 */                             out.write("\n\t\t  ");
/* 4346 */                             if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resID, (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID")) != 0) {
/* 4347 */                               out.write("\n\t\t  <br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 4348 */                               out.print(resID + "_" + (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID"));
/* 4349 */                               out.write("&monitortype=");
/* 4350 */                               out.print(resourcetype);
/* 4351 */                               out.write("')\">");
/* 4352 */                               out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 4353 */                               out.write("</a></span>\n          ");
/*      */                             }
/* 4355 */                             out.write("\n\t</td>\n        </tr>\n\t\t<tr>\n                    <td class=\"monitorinfoodd\" valign=\"top\">");
/* 4356 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.scriptname.text"));
/* 4357 */                             out.write("</td>\n\t\t<td class=\"monitorinfoodd\">");
/* 4358 */                             out.print(request.getAttribute("scriptname"));
/* 4359 */                             out.write("</td>\n\n\t\t</tr>\n\t\t");
/*      */                             
/* 4361 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 4362 */                             _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 4363 */                             _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4365 */                             _jspx_th_logic_005fnotEmpty_005f0.setName("hostname");
/* 4366 */                             int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 4367 */                             if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                               for (;;) {
/* 4369 */                                 out.write("\n                    <td class=\"monitorinfoodd\" valign=\"top\">");
/* 4370 */                                 out.print(FormatUtil.getString("am.webclient.script.remoteserver"));
/* 4371 */                                 out.write("</td>\n\t\t        <td class=\"monitorinfoodd\">");
/* 4372 */                                 if (_jspx_meth_c_005fout_005f38(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*      */                                   return;
/* 4374 */                                 out.write("</td>\n\t\t");
/* 4375 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 4376 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4380 */                             if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 4381 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                             }
/*      */                             
/* 4384 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 4385 */                             out.write("\n        ");
/*      */                             
/* 4387 */                             EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 4388 */                             _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 4389 */                             _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4391 */                             _jspx_th_logic_005fempty_005f0.setName("systeminfo");
/* 4392 */                             int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 4393 */                             if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                               for (;;) {
/* 4395 */                                 out.write("\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 4396 */                                 out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 4397 */                                 out.write("</td>\n          <td class=\"monitorinfoeven\">-</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 4398 */                                 out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 4399 */                                 out.write("</td>\n          <td class=\"monitorinfoodd\">-</td>\n        </tr>\n        ");
/* 4400 */                                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 4401 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4405 */                             if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 4406 */                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                             }
/*      */                             
/* 4409 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 4410 */                             out.write("\n        ");
/*      */                             
/* 4412 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 4413 */                             _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 4414 */                             _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4416 */                             _jspx_th_logic_005fnotEmpty_005f1.setName("systeminfo");
/* 4417 */                             int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 4418 */                             if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                               for (;;) {
/* 4420 */                                 out.write("\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 4421 */                                 out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 4422 */                                 out.write("</td>\n          <td class=\"monitorinfoeven\">");
/* 4423 */                                 out.print(formatDT((Long)systeminfo.get("LASTDC")));
/* 4424 */                                 out.write("</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 4425 */                                 out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 4426 */                                 out.write("</td>\n          <td class=\"monitorinfoodd\">");
/* 4427 */                                 out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 4428 */                                 out.write("</td>\n        </tr>\n        ");
/* 4429 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 4430 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4434 */                             if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 4435 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                             }
/*      */                             
/* 4438 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 4439 */                             out.write("\n        ");
/* 4440 */                             org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MyField_trstrip.jsp", out, false);
/* 4441 */                             out.write("\n    </table>\n  </td>\n  <td width=\"43%\" valign=\"top\"><table align=left width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n        <tr>\n          <td height=\"28\" colspan=\"3\" class=\"tableheadingbborder\">");
/* 4442 */                             out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 4443 */                             out.write("</td>\n        </tr>\n        <tr>\n          <td colspan=\"3\"> <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n              <tr>\n                <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 4444 */                             if (_jspx_meth_c_005fout_005f39(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4446 */                             out.write("&period=1&resourcename=");
/* 4447 */                             if (_jspx_meth_c_005fout_005f40(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4449 */                             out.write("')\">\n                  <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4450 */                             out.print(seven_days_text);
/* 4451 */                             out.write("\"></a></td>\n                <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 4452 */                             if (_jspx_meth_c_005fout_005f41(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4454 */                             out.write("&period=2&resourcename=");
/* 4455 */                             if (_jspx_meth_c_005fout_005f42(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4457 */                             out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4458 */                             out.print(thiry_days_text);
/* 4459 */                             out.write("\"></a></td>\n              </tr>\n            </table></td>\n          ");
/*      */                             
/* 4461 */                             wlsGraph.setParam(resID, "AVAILABILITY");
/*      */                             
/* 4463 */                             out.write("\n        </tr>\n        <tr align=\"center\">\n          <td height=\"28\" colspan=\"3\" class=\"whitegrayborder\"> ");
/* 4464 */                             if (_jspx_meth_awolf_005fpiechart_005f0(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4466 */                             out.write("</td>\n        </tr>\n\t\t<tr>\n\t\t<td width=\"49%\" class=\"yellowgrayborder\"  title=\"");
/* 4467 */                             out.print(tipCurStatus);
/* 4468 */                             out.write(34);
/* 4469 */                             out.write(62);
/* 4470 */                             out.print(FormatUtil.getString("am.webclient.common.currentstatus.text"));
/* 4471 */                             out.write(" :\n\t\t<a style=\"position:relative; top:2px;\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4472 */                             out.print(resID);
/* 4473 */                             out.write("&attributeid=");
/* 4474 */                             out.print(availabilitykeys.get(resourcetype));
/* 4475 */                             out.write("&alertconfigurl=");
/* 4476 */                             out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "&attributeIDs=" + availabilitykeys.get(resourcetype) + "&attributeToSelect=" + availabilitykeys.get(resourcetype) + "&redirectto=" + encodeurl));
/* 4477 */                             out.write("')\">\n\t\t");
/* 4478 */                             out.print(getSeverityImageForAvailability(alert.getProperty(resID + "#" + ess_atts.getProperty("Availability"))));
/* 4479 */                             out.write("\n\t\t</a></td>\n\t\t<td width=\"51%\" align=\"right\" class=\"yellowgrayborder\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4480 */                             out.print(resID);
/* 4481 */                             out.write("&attributeIDs=");
/* 4482 */                             out.print(availabilitykeys.get(resourcetype));
/* 4483 */                             out.write(44);
/* 4484 */                             out.print(healthkeys.get(resourcetype));
/* 4485 */                             out.write("&attributeToSelect=");
/* 4486 */                             out.print(availabilitykeys.get(resourcetype));
/* 4487 */                             out.write("&redirectto=");
/* 4488 */                             out.print(encodeurl);
/* 4489 */                             out.write("\" class=\"links\">");
/* 4490 */                             out.print(ALERTCONFIG_TEXT);
/* 4491 */                             out.write("</a>&nbsp;\n\t\t</td>\n\t\t</tr>\n      </table></td>\n  </tr>\n</table>\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 4492 */                             org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MyField_div.jsp", out, false);
/* 4493 */                             out.write("</td></tr></table>\n");
/* 4494 */                             out.write("<!--$Id$-->\n\n\n");
/*      */                             
/* 4496 */                             String baseid = "-1";
/* 4497 */                             if (request.getAttribute("baseid") != null)
/*      */                             {
/* 4499 */                               baseid = (String)request.getAttribute("baseid");
/*      */                             }
/*      */                             
/* 4502 */                             out.write("\n<Script>\n\nfunction fnSelectAllform(e,frm,name)\n{\n\tToggleAll(e,frm,name)\n}\n\nfunction deleteTablesAlert(resid,tableid,formid,frm)\n{\n\tdocument.getElementById(formid).resourceid.value=resid;\n\tdocument.getElementById(formid).tableid.value=tableid;\n\tif(confirm(\"");
/* 4503 */                             out.print(FormatUtil.getString("am.webclient.scripttable.delete.alert"));
/* 4504 */                             out.write("\"))\n\t{\n\tdocument.getElementById(formid).action=\"/updateScript.do?method=deleteTableByUser&resourceid=\"+resid+\"&tableid=\"+tableid;\n\tdocument.getElementById(formid).submit();\n\t}\n\telse\n\t{\n\t\treturn;\n\t}\n}\n\nfunction deleteRows(e,frm,objname,frmname,formid)\n{\n\tvar resid=\"\";\n\tvar count=0;\n\tif(!checkforOneSelected(frmname,objname))\n\t{\n\t  alert(\"");
/* 4505 */                             out.print(FormatUtil.getString("am.webclient.scriptrow.selectone.delete"));
/* 4506 */                             out.write("\");\n\t  return;\n\t}\n\telse\n\t{\n\t\tfor(var i=0;i<document.getElementsByName(objname).length;i++)\n\t\t{\n\t\t\tif(document.getElementsByName(objname)[i].checked==true)\n\t\t\t{\n\t\t\t\tvar temp=document.getElementsByName(objname)[i].value;\n\t\t\t\tvar test=temp.split(\",\");\n\t\t\t\tvar res=test[0];\n\t\t\t\tif(resid!=\"\")\n\t\t\t\t{\n\t\t\t\tresid=resid+\",\"+res;\n\t\t\t\tcount++;\n\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n\t\t\t\tresid=res;\n\t\t\t\t}\n\t\t\t\t\n\t\t\t}\n\t\t\t\n\t\t}\n\t}\n\tif(confirm(\"");
/* 4507 */                             out.print(FormatUtil.getString("am.webclient.scriptrow.delete.confirm"));
/* 4508 */                             out.write("\"))\n\t{\n\tdocument.getElementById(formid).action=\"/updateScript.do?method=deleteRowByUser&resourceid=\"+resid;\n\tdocument.getElementById(formid).method.value='deleteRowByUser';\n\tdocument.getElementById(formid).resourceid.value=resid;\n\tdocument.getElementById(formid).tableid.value=");
/* 4509 */                             out.print(resID);
/* 4510 */                             out.write(";\n\tdocument.getElementById(formid).submit();\n\t}\n\telse\n\t{\n\t\treturn;\n\t}\n}\n\nfunction showGraphForthis(attid,rowid,attribute)\n{\n    var count=1;\n    MM_openBrWindow('/jsp/PopUp_Graph.jsp?baseid=");
/* 4511 */                             out.print(baseid);
/* 4512 */                             out.write("&restype=");
/* 4513 */                             out.print(resourcetype);
/* 4514 */                             out.write("&resids='+rowid+'&attids='+attid+'&listsize='+count+'&attName='+attribute,'ExecutionTimeStatistic','width=800,height=500,top=200,left=200,scrollbars=yes,resizable=yes');\n}\n\nfunction showGraph(e,frm,objname,formid)\n{\n\tvar resid=\"\";\n\tvar attid=\"\";\n\tvar count=0;\n\tif(!checkforOneSelected(document.forms[frm],objname))\n\t{\n\t  alert(\"");
/* 4515 */                             out.print(FormatUtil.getString("am.comparereport.jsalert.text"));
/* 4516 */                             out.write("\");\n\t  return;\n\t}\n\telse\n\t{\n\tfor(var i=0;i<document.getElementsByName(objname).length;i++)\n\t\t{\n\t\t\tif(document.getElementsByName(objname)[i].checked==true)\n\t\t\t{\n\t\t\t\tvar temp=document.getElementsByName(objname)[i].value;\n\t\t\t\tvar test=temp.split(\",\");\n\t\t\t\tvar res=test[0];\n\t\t\t\tif(resid!=\"\")\n\t\t\t\t{\n\t\t\t\tresid=resid+\",\"+res;\n\t\t\t\tcount++;\n\t\t\t\t//attid=attid+\",\"+att;\n\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n\t\t\t\tresid=res;\n\t\t\t//\tattid=att;\n\t\t\t\t}\n\t\t\t\t\n\t\t\t}\n\t\t\t\n\t}\nattid=document.getElementById(formid).attList[document.getElementById(formid).attList.selectedIndex].value;\nMM_openBrWindow('/jsp/PopUp_Graph.jsp?baseid=");
/* 4517 */                             out.print(baseid);
/* 4518 */                             out.write("&restype=");
/* 4519 */                             out.print(resourcetype);
/* 4520 */                             out.write("&resids='+resid+'&attids='+attid+'&listsize='+count,'ExecutionTimeStatistic','width=800,height=500,top=200,left=200,scrollbars=yes,resizable=yes');\n\t\n        }\n}\n\n</Script>\n");
/*      */                             
/* 4522 */                             ArrayList table_details = (ArrayList)request.getAttribute("tabledetails");
/* 4523 */                             ArrayList tableids = new ArrayList();
/* 4524 */                             pageContext.setAttribute("table_ids", tableids);
/* 4525 */                             Hashtable table_resids = new Hashtable();
/* 4526 */                             Hashtable tabid_tabname = new Hashtable();
/* 4527 */                             Hashtable attid_details = new Hashtable();
/* 4528 */                             Hashtable table_data = new Hashtable();
/* 4529 */                             Hashtable table_health = new Hashtable();
/* 4530 */                             Hashtable table_avail = new Hashtable();
/* 4531 */                             Hashtable table_avail_data = new Hashtable();
/* 4532 */                             Hashtable table_rowsDisabled = new Hashtable();
/*      */                             
/* 4534 */                             if ((table_details != null) && (table_details.size() > 0))
/*      */                             {
/* 4536 */                               tableids = (ArrayList)table_details.get(0);
/* 4537 */                               pageContext.setAttribute("table_ids", tableids);
/* 4538 */                               table_resids = (Hashtable)table_details.get(1);
/* 4539 */                               tabid_tabname = (Hashtable)table_details.get(2);
/* 4540 */                               attid_details = (Hashtable)table_details.get(3);
/* 4541 */                               table_data = (Hashtable)table_details.get(4);
/* 4542 */                               table_health = (Hashtable)table_details.get(5);
/* 4543 */                               table_avail = (Hashtable)table_details.get(6);
/* 4544 */                               table_avail_data = (Hashtable)table_details.get(7);
/* 4545 */                               table_rowsDisabled = (Hashtable)table_details.get(8);
/*      */                             }
/* 4547 */                             int id1 = 0;
/*      */                             
/* 4549 */                             long curvalue = -1L;
/* 4550 */                             String temp = "-1";
/* 4551 */                             if (request.getAttribute("responsetime") != null)
/*      */                             {
/* 4553 */                               temp = (String)request.getAttribute("responsetime");
/*      */                             }
/* 4555 */                             String responsetimeid = ess_atts.getProperty("ResponseTime");
/* 4556 */                             String tipCurValue = "The time taken to get a response from this service.";
/* 4557 */                             wlsGraph.setParam(resID, "RESPONSETIME");
/* 4558 */                             HashMap hm_enable = (HashMap)request.getAttribute("hm_reports");
/* 4559 */                             ArrayList al = (ArrayList)request.getAttribute("enable");
/* 4560 */                             String xaxis_time = FormatUtil.getString("am.webclient.common.axisname.time.text");
/* 4561 */                             String yaxis_restime = FormatUtil.getString("am.webclient.common.responsetime.text") + " " + FormatUtil.getString("am.webclient.common.units.ms.text");
/*      */                             
/*      */ 
/* 4564 */                             out.write("\n\n<table width=\"100%\">\n<tr>\n<td>\n\n<div id=\"reports\" style=\"DISPLAY: none\">\n<br>\n<form name=\"form2\">\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n<tr> \n<td width=\"72%\" height=\"31\" class=\"tableheading\" > ");
/* 4565 */                             out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 4566 */                             out.write("</td>\n\n</tr>\n</table>\n<table align=\"left\" width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbborder\">\n<tr>\n<td>\n<table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n<tr>\n<td width=\"10%\" height=\"28\" valign=\"center\"  class=\"columnheading\">\n<input type=\"checkbox\" name=\"headercheckbox\"  onClick=\"javascript:fnSelectAll(this)\"></td>\n<td width=\"40%\" class=\"columnheading\"><span class=\"bodytextbold\">");
/* 4567 */                             if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4569 */                             out.write("</span></td>\n<td width=\"50%\" height=\"28%\" valign=\"center\" class=\"columnheading\"><span class=\"bodytextbold\">");
/* 4570 */                             if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4572 */                             out.write("</span></td>\n</tr>\n\n");
/*      */                             
/* 4574 */                             String reports_enabled = FormatUtil.getString("am.webclient.script.reportsenabled");
/* 4575 */                             String reports_disabled = FormatUtil.getString("am.webclient.script.reportsdisabled");
/* 4576 */                             int bgclasschanger = 0;
/*      */                             
/* 4578 */                             out.write(10);
/* 4579 */                             out.write(10);
/*      */                             
/* 4581 */                             IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 4582 */                             _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 4583 */                             _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4585 */                             _jspx_th_logic_005fiterate_005f0.setName("enable");
/*      */                             
/* 4587 */                             _jspx_th_logic_005fiterate_005f0.setId("attribute");
/*      */                             
/* 4589 */                             _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                             
/* 4591 */                             _jspx_th_logic_005fiterate_005f0.setType("java.lang.String");
/* 4592 */                             int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 4593 */                             if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 4594 */                               String attribute = null;
/* 4595 */                               Integer j = null;
/* 4596 */                               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 4597 */                                 out = _jspx_page_context.pushBody();
/* 4598 */                                 _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 4599 */                                 _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                               }
/* 4601 */                               attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 4602 */                               j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                               for (;;) {
/* 4604 */                                 out.write("\n<tr>\n\n");
/*      */                                 
/* 4606 */                                 String bgclass = "whitegrayborder";
/* 4607 */                                 if (j.intValue() % 2 != 0)
/*      */                                 {
/* 4609 */                                   bgclass = "yellowgrayborder";
/*      */                                 }
/* 4611 */                                 bgclasschanger++;
/*      */                                 
/* 4613 */                                 out.write("\n\n<td width=\"10%\" height=\"22\"  class=\"");
/* 4614 */                                 out.print(bgclass);
/* 4615 */                                 out.write("\">\n<input type=\"checkbox\" name=\"checkbox\" value=\"");
/* 4616 */                                 out.print(displayname_attributeid.get(attribute));
/* 4617 */                                 out.write("\"/></td>\n<td width=\"40%\"  class=\"");
/* 4618 */                                 out.print(bgclass);
/* 4619 */                                 out.write("\" ><span class=\"bodytext\">");
/* 4620 */                                 out.print(attribute);
/* 4621 */                                 out.write("</span></td>\n");
/*      */                                 
/* 4623 */                                 if (hm_enable.get(attribute).equals("enabled"))
/*      */                                 {
/*      */ 
/* 4626 */                                   out.write("\n<td width=\"50%\" class=\"");
/* 4627 */                                   out.print(bgclass);
/* 4628 */                                   out.write("\"><img alt=\"Reports Enabled\" title=\"");
/* 4629 */                                   out.print(reports_enabled);
/* 4630 */                                   out.write("\" src=\"../images/cam_report_enabled.gif\" border=\"0\"></td>\n");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 4636 */                                   out.write("\n<td width=\"50%\" class=\"");
/* 4637 */                                   out.print(bgclass);
/* 4638 */                                   out.write("\" ><img alt=\"Reports Disabled\" title=\"");
/* 4639 */                                   out.print(reports_disabled);
/* 4640 */                                   out.write("\" src=\"../images/cam_report_disabled.gif\" border=\"0\"></td>\n\n");
/*      */                                 }
/*      */                                 
/*      */ 
/* 4644 */                                 out.write("\n</tr>\n");
/* 4645 */                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 4646 */                                 attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 4647 */                                 j = (Integer)_jspx_page_context.findAttribute("j");
/* 4648 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 4651 */                               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 4652 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 4655 */                             if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 4656 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                             }
/*      */                             
/* 4659 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 4660 */                             out.write(10);
/*      */                             
/* 4662 */                             IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 4663 */                             _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 4664 */                             _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4666 */                             _jspx_th_logic_005fiterate_005f1.setName("table_ids");
/*      */                             
/* 4668 */                             _jspx_th_logic_005fiterate_005f1.setId("attribute");
/*      */                             
/* 4670 */                             _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/*      */                             
/* 4672 */                             _jspx_th_logic_005fiterate_005f1.setType("java.lang.String");
/* 4673 */                             int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 4674 */                             if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 4675 */                               String attribute = null;
/* 4676 */                               Integer j = null;
/* 4677 */                               if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 4678 */                                 out = _jspx_page_context.pushBody();
/* 4679 */                                 _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 4680 */                                 _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                               }
/* 4682 */                               attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 4683 */                               j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                               for (;;) {
/* 4685 */                                 out.write(10);
/*      */                                 
/* 4687 */                                 ArrayList attids = (ArrayList)((Hashtable)table_data.get(attribute)).get("column");
/* 4688 */                                 pageContext.setAttribute("attids", attids);
/*      */                                 
/* 4690 */                                 out.write(10);
/*      */                                 
/* 4692 */                                 IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 4693 */                                 _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/* 4694 */                                 _jspx_th_logic_005fiterate_005f2.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                 
/* 4696 */                                 _jspx_th_logic_005fiterate_005f2.setName("attids");
/*      */                                 
/* 4698 */                                 _jspx_th_logic_005fiterate_005f2.setId("colnames");
/*      */                                 
/* 4700 */                                 _jspx_th_logic_005fiterate_005f2.setIndexId("k");
/*      */                                 
/* 4702 */                                 _jspx_th_logic_005fiterate_005f2.setType("java.lang.String");
/* 4703 */                                 int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/* 4704 */                                 if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/* 4705 */                                   String colnames = null;
/* 4706 */                                   Integer k = null;
/* 4707 */                                   if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 4708 */                                     out = _jspx_page_context.pushBody();
/* 4709 */                                     _jspx_th_logic_005fiterate_005f2.setBodyContent((BodyContent)out);
/* 4710 */                                     _jspx_th_logic_005fiterate_005f2.doInitBody();
/*      */                                   }
/* 4712 */                                   colnames = (String)_jspx_page_context.findAttribute("colnames");
/* 4713 */                                   k = (Integer)_jspx_page_context.findAttribute("k");
/*      */                                   for (;;) {
/* 4715 */                                     out.write(10);
/*      */                                     
/* 4717 */                                     String bgclass = "whitegrayborder";
/* 4718 */                                     if ((attid_details.get(colnames) != null) && (((ArrayList)attid_details.get(colnames)).get(1).equals("0")))
/*      */                                     {
/*      */ 
/* 4721 */                                       if (bgclasschanger % 2 != 0)
/*      */                                       {
/* 4723 */                                         bgclass = "yellowgrayborder";
/*      */                                       }
/* 4725 */                                       bgclasschanger++;
/*      */                                       
/* 4727 */                                       out.write("\n<tr>\n<td width=\"10%\" height=\"22\"  class=\"");
/* 4728 */                                       out.print(bgclass);
/* 4729 */                                       out.write("\">\n<input type=\"checkbox\" name=\"checkbox\" value=\"");
/* 4730 */                                       out.print(colnames);
/* 4731 */                                       out.write("\"/></td>\n<td width=\"40%\"  class=\"");
/* 4732 */                                       out.print(bgclass);
/* 4733 */                                       out.write("\" ><span class=\"bodytext\">");
/* 4734 */                                       out.print(tabid_tabname.get(tableids.get(j.intValue())));
/* 4735 */                                       out.write(58);
/* 4736 */                                       out.print(((ArrayList)attid_details.get(colnames)).get(0));
/* 4737 */                                       out.write("</span></td>\n");
/*      */                                       
/* 4739 */                                       if (((ArrayList)attid_details.get(colnames)).get(2).equals("0"))
/*      */                                       {
/*      */ 
/* 4742 */                                         out.write("\n<td width=\"50%\" class=\"");
/* 4743 */                                         out.print(bgclass);
/* 4744 */                                         out.write("\" ><img alt=\"Reports Disabled\" title=\"");
/* 4745 */                                         out.print(reports_disabled);
/* 4746 */                                         out.write("\" src=\"../images/cam_report_disabled.gif\" border=\"0\"></td>\n");
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 4752 */                                         out.write("\n<td width=\"50%\" class=\"");
/* 4753 */                                         out.print(bgclass);
/* 4754 */                                         out.write("\"><img alt=\"Reports Enabled\" title=\"");
/* 4755 */                                         out.print(reports_enabled);
/* 4756 */                                         out.write("\" src=\"../images/cam_report_enabled.gif\" border=\"0\"></td>\n");
/*      */                                       }
/*      */                                       
/*      */ 
/* 4760 */                                       out.write("\n</tr>\n");
/*      */                                     }
/*      */                                     
/*      */ 
/* 4764 */                                     out.write(10);
/* 4765 */                                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/* 4766 */                                     colnames = (String)_jspx_page_context.findAttribute("colnames");
/* 4767 */                                     k = (Integer)_jspx_page_context.findAttribute("k");
/* 4768 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 4771 */                                   if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 4772 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 4775 */                                 if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/* 4776 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2); return;
/*      */                                 }
/*      */                                 
/* 4779 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/* 4780 */                                 out.write(10);
/* 4781 */                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 4782 */                                 attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 4783 */                                 j = (Integer)_jspx_page_context.findAttribute("j");
/* 4784 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 4787 */                               if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 4788 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 4791 */                             if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 4792 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                             }
/*      */                             
/* 4795 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 4796 */                             out.write("\n</table>\n</td>\n</tr>\n<tr>\n<td>\n<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"tablebottom\" >\n<tr>\n<td width=\"5%\" colspan=2 heigth=\"26\" class=\"bodytext\"><a href=\"javascript:enableSelections(1);\" class=\"links\">");
/* 4797 */                             out.print(FormatUtil.getString("am.webclient.script.enablereports"));
/* 4798 */                             out.write("</a> | <a href=\"javascript:enableSelections(2);\" class=\"links\">");
/* 4799 */                             out.print(FormatUtil.getString("am.webclient.script.disablereports"));
/* 4800 */                             out.write("</a></td>\n\n</tr>\n</table>\n</td>\n</tr>\n</table>\n</form>\n<br>\n</div>\n</td>\n</tr>\n\n\n<tr>\n<td>\n\n\n");
/* 4801 */                             if (_jspx_meth_c_005fif_005f27(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4803 */                             out.write(10);
/* 4804 */                             out.write(10);
/*      */                             
/* 4806 */                             String class1 = null;
/* 4807 */                             String class2 = null;
/* 4808 */                             if ((request.getParameter("fromhost") != null) && (request.getParameter("fromhost").equals("true")))
/*      */                             {
/* 4810 */                               class1 = "lrbborder";
/* 4811 */                               class2 = "tablebottom";
/*      */                             }
/*      */                             else
/*      */                             {
/* 4815 */                               class1 = "lrtbdarkborder";
/* 4816 */                               class2 = "tableheading";
/*      */                             }
/*      */                             
/*      */ 
/* 4820 */                             out.write("\n</tr>\n</td>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"");
/* 4821 */                             out.print(class1);
/* 4822 */                             out.write("\">\n  <tr> \n    <td height=\"26\" class=\"tableheading\">");
/* 4823 */                             out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 4824 */                             out.write(32);
/* 4825 */                             out.write(45);
/* 4826 */                             out.write(32);
/* 4827 */                             out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 4828 */                             out.write("</td>\n  </tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n  <tr> \n    <td width=\"50%\" height=\"127\" valign=\"top\"> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\">\n        <tr> \n          <td width=\"90%\" height=\"35\" align=\"right\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4829 */                             out.print(resID);
/* 4830 */                             out.write("&attributeid=");
/* 4831 */                             out.print(responsetimeid);
/* 4832 */                             out.write("&period=-7',740,550)\"> \n            <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 4833 */                             out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4834 */                             out.write("\"></a></td>\n          <td width=\"10%\" height=\"35\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4835 */                             out.print(resID);
/* 4836 */                             out.write("&attributeid=");
/* 4837 */                             out.print(responsetimeid);
/* 4838 */                             out.write("&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4839 */                             out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 4840 */                             out.write("\"></a></td>\n        </tr>\n        <tr> \n          <td colspan=\"2\"> ");
/*      */                             
/* 4842 */                             TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 4843 */                             _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 4844 */                             _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4846 */                             _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("wlsGraph");
/*      */                             
/* 4848 */                             _jspx_th_awolf_005ftimechart_005f0.setWidth("320");
/*      */                             
/* 4850 */                             _jspx_th_awolf_005ftimechart_005f0.setHeight("170");
/*      */                             
/* 4852 */                             _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*      */                             
/* 4854 */                             _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(xaxis_time);
/*      */                             
/* 4856 */                             _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(yaxis_restime);
/*      */                             
/* 4858 */                             _jspx_th_awolf_005ftimechart_005f0.setDateFormat("HH:mm");
/* 4859 */                             int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 4860 */                             if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 4861 */                               if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 4862 */                                 out = _jspx_page_context.pushBody();
/* 4863 */                                 _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 4864 */                                 _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 4867 */                                 out.write(" \n            ");
/* 4868 */                                 int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 4869 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 4872 */                               if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 4873 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 4876 */                             if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 4877 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                             }
/*      */                             
/* 4880 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 4881 */                             out.write(" </td>\n        </tr>\n      </table></td>\n    <td width=\"50%\" valign=\"top\"> <br> <br> \n\n      <table align=\"left\" width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"lrbborder\">\n\n\t  \n        <tr> \n          <td class=\"columnheadingtb\"><span class=\"bodytextbold\">");
/* 4882 */                             if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4884 */                             out.write("</span></td>");
/* 4885 */                             out.write(" \n          <td class=\"columnheadingtb\"><span class=\"bodytextbold\">");
/* 4886 */                             if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4888 */                             out.write("</span></td>");
/* 4889 */                             out.write(" \n          <td class=\"columnheadingtb\" colspan=\"2\"><span class=\"bodytextbold\">\n          ");
/*      */                             
/* 4891 */                             if (!temp.equals("-1"))
/*      */                             {
/* 4893 */                               out.write("\n          ");
/* 4894 */                               if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 4896 */                               out.write("</span>\n          ");
/*      */                             } else {
/* 4898 */                               out.println("&nbsp;");
/*      */                             }
/* 4900 */                             out.write("\n          </td>\n        </tr>\n        <tr> \n\n          <td  height=\"25\" class=\"whitegrayborder\" title=\"");
/* 4901 */                             out.print(tipCurValue);
/* 4902 */                             out.write(34);
/* 4903 */                             out.write(62);
/* 4904 */                             out.print(FormatUtil.getString("am.webclient.common.current.text"));
/* 4905 */                             out.write(32);
/* 4906 */                             out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 4907 */                             out.write("</td>\n          ");
/*      */                             
/* 4909 */                             if (!temp.equals("-1"))
/*      */                             {
/*      */ 
/* 4912 */                               out.write("\n          <td  height=\"25\" class=\"whitegrayborder\">");
/* 4913 */                               out.print(temp);
/* 4914 */                               out.write(32);
/* 4915 */                               out.print(FormatUtil.getString("ms"));
/* 4916 */                               out.write("</td>\n             \n            ");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/*      */ 
/* 4922 */                               out.write("\n          <td  height=\"25\" class=\"whitegrayborder\" colspan=\"3\" >");
/* 4923 */                               out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 4924 */                               out.write("</td>\n            ");
/*      */                             }
/*      */                             
/* 4927 */                             out.write(10);
/* 4928 */                             out.write(9);
/* 4929 */                             out.write(32);
/*      */                             
/* 4931 */                             if (!temp.equals("-1"))
/*      */                             {
/*      */ 
/* 4934 */                               out.write("\n          <Td width=\"16%\" class=\"whitegrayborder\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4935 */                               out.print(resID);
/* 4936 */                               out.write("&attributeid=");
/* 4937 */                               out.print(responsetimeid);
/* 4938 */                               out.write("&alertconfigurl=");
/* 4939 */                               out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs" + responsetimeid + "attributeToSelect=" + responsetimeid + "&redirectto=" + encodeurl));
/* 4940 */                               out.write("')\">");
/* 4941 */                               out.print(getSeverityImage(alert.getProperty(resID + "#" + responsetimeid)));
/* 4942 */                               out.write(" </a>\n          </td>\n          ");
/*      */                             }
/*      */                             
/*      */ 
/* 4946 */                             out.write("\n        </tr>\n        <tr> \n          <td  colspan=\"4\" height=\"35\" class=\"whitegrayborder\" align=\"right\" ><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4947 */                             out.print(resID);
/* 4948 */                             out.write("&attributeIDs=");
/* 4949 */                             out.print(responsetimeid);
/* 4950 */                             out.write("&attributeToSelect=");
/* 4951 */                             out.print(responsetimeid);
/* 4952 */                             out.write("&redirectto=");
/* 4953 */                             out.print(encodeurl);
/* 4954 */                             out.write("\" class=\"staticlinks\">");
/* 4955 */                             out.print(ALERTCONFIG_TEXT);
/* 4956 */                             out.write("</a></td>\n        </tr>\n      </table></td>\n  </tr>\n</table>    \n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr> \n    <td height=\"26\" class=\"tablebottom\">&nbsp;</td>\n  </tr>\n</table>\n\n\n\n\n");
/*      */                             
/* 4958 */                             int localattributes = 0;
/* 4959 */                             int widthOfTopMostTable = num_data == 1 ? 49 : 99;
/* 4960 */                             int secondLevelTableWidth = num_data == 1 ? 99 : 49;
/* 4961 */                             int widthofinnertables = num_data == 1 ? 100 : 98;
/*      */                             
/* 4963 */                             out.write("\n\n<br>\n<!--c:if test=\"${string_attributes!=0}\"-->\n\n");
/*      */                             
/* 4965 */                             if (str_data > 0)
/*      */                             {
/* 4967 */                               out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" ><tr><td>\n<table WIDTH=\"99%\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" class=lrbtborder>\n<tr>\n<td>\n<table align=\"center\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n\t  <tr>\n\t  <td colspan=\"5\" class=\"tableheadingbborder\">");
/* 4968 */                               out.print(FormatUtil.getString("am.webclient.script.stringattheading"));
/* 4969 */                               out.write("</td>\n\t  </tr>\n        <tr>\n            <td width=\"50%\" class=\"columnheading\"><span class=\"bodytextbold\">");
/* 4970 */                               if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 4972 */                               out.write("</span></td>");
/* 4973 */                               out.write("\n            <td width=\"15%\" class=\"columnheading\"><span class=\"bodytextbold\">");
/* 4974 */                               if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 4976 */                               out.write("</span></td>");
/* 4977 */                               out.write("\n          <td width=\"15%\" class=\"columnheading\" align=\"center\"><span class=\"bodytextbold\">");
/* 4978 */                               if (_jspx_meth_fmt_005fmessage_005f12(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 4980 */                               out.write("</span></td>");
/* 4981 */                               out.write("\n          <td width=\"20%\" class=\"columnheading\" colspan=\"2\" align=\"center\"><span class=\"bodytextbold\">");
/* 4982 */                               out.print(ALERTCONFIG_TEXT);
/* 4983 */                               out.write("</span></td>");
/* 4984 */                               out.write("\n        </tr>\n</table>\n        <tr>\n        <td>\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" >\n");
/*      */                               
/* 4986 */                               IterateTag _jspx_th_logic_005fiterate_005f3 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 4987 */                               _jspx_th_logic_005fiterate_005f3.setPageContext(_jspx_page_context);
/* 4988 */                               _jspx_th_logic_005fiterate_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 4990 */                               _jspx_th_logic_005fiterate_005f3.setName("non_numeric");
/*      */                               
/* 4992 */                               _jspx_th_logic_005fiterate_005f3.setId("attribute");
/*      */                               
/* 4994 */                               _jspx_th_logic_005fiterate_005f3.setIndexId("i");
/*      */                               
/* 4996 */                               _jspx_th_logic_005fiterate_005f3.setType("java.lang.String");
/* 4997 */                               int _jspx_eval_logic_005fiterate_005f3 = _jspx_th_logic_005fiterate_005f3.doStartTag();
/* 4998 */                               if (_jspx_eval_logic_005fiterate_005f3 != 0) {
/* 4999 */                                 String attribute = null;
/* 5000 */                                 Integer i = null;
/* 5001 */                                 if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 5002 */                                   out = _jspx_page_context.pushBody();
/* 5003 */                                   _jspx_th_logic_005fiterate_005f3.setBodyContent((BodyContent)out);
/* 5004 */                                   _jspx_th_logic_005fiterate_005f3.doInitBody();
/*      */                                 }
/* 5006 */                                 attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 5007 */                                 i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                 for (;;) {
/* 5009 */                                   out.write(10);
/*      */                                   
/* 5011 */                                   if (displayname_attributeid.get(attribute) != null)
/*      */                                   {
/* 5013 */                                     int attributeid = Integer.parseInt(String.valueOf(displayname_attributeid.get(attribute)));
/*      */                                     
/* 5015 */                                     out.write(10);
/*      */                                     
/* 5017 */                                     int j = i.intValue();
/* 5018 */                                     String border = "whitegrayborder";
/* 5019 */                                     if (j % 2 == 0) {
/* 5020 */                                       border = "whitegrayborder";
/*      */                                     } else {
/* 5022 */                                       border = "yellowgrayborder";
/*      */                                     }
/* 5024 */                                     String value = FormatUtil.getString("am.webclient.nodata.text");
/* 5025 */                                     if ((ht_string.get(attribute) != null) && (!String.valueOf(ht_string.get(attribute)).equalsIgnoreCase("null"))) {
/* 5026 */                                       value = (String)ht_string.get(attribute);
/*      */                                     }
/* 5028 */                                     out.write("\n         <tr>\n          <td  height=\"25\" colspan=\"0\" width=\"50%\" class=\"");
/* 5029 */                                     out.print(border);
/* 5030 */                                     out.write("\" align=\"left\" >");
/* 5031 */                                     out.print(attribute);
/* 5032 */                                     out.write(" </td>\n          <td  height=\"25\" colspan=\"2\" width=\"15%\" align=\"left\" class=\"");
/* 5033 */                                     out.print(border);
/* 5034 */                                     out.write(34);
/* 5035 */                                     out.write(32);
/* 5036 */                                     out.write(62);
/* 5037 */                                     out.print(value);
/* 5038 */                                     out.write(" </td>\n          <td  width=\"15%\" colspan=\"2\" align=\"center\" class=\"");
/* 5039 */                                     out.print(border);
/* 5040 */                                     out.write("\" >  <a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5041 */                                     out.print(resID);
/* 5042 */                                     out.write("&attributeid=");
/* 5043 */                                     out.print(attributeid);
/* 5044 */                                     out.write("&alertconfigurl=");
/* 5045 */                                     out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs" + attributeid + "attributeToSelect=" + attributeid + "&redirectto=" + encodeurl));
/* 5046 */                                     out.write("')\">");
/* 5047 */                                     out.print(getSeverityImage(alert.getProperty(resID + "#" + attributeid)));
/* 5048 */                                     out.write(" </a></td>\n          <td  width=\"20%\" colspan=\"2\" height=\"35\" align=\"center\" class=\"");
/* 5049 */                                     out.print(border);
/* 5050 */                                     out.write("\" align=\"center\" ><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5051 */                                     out.print(resID);
/* 5052 */                                     out.write("&attributeIDs=");
/* 5053 */                                     out.print(attributeid);
/* 5054 */                                     out.write("&attributeToSelect=");
/* 5055 */                                     out.print(attributeid);
/* 5056 */                                     out.write("&redirectto=");
/* 5057 */                                     out.print(encodeurl);
/* 5058 */                                     out.write("\" class=\"staticlinks\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\" width=\"20\" height=\"20\" style=\"width: 10px; height: 13px;\" border=\"0\" title=\"\"></a></td>\n        </tr>\n        ");
/*      */                                   }
/* 5060 */                                   out.write(10);
/* 5061 */                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f3.doAfterBody();
/* 5062 */                                   attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 5063 */                                   i = (Integer)_jspx_page_context.findAttribute("i");
/* 5064 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 5067 */                                 if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 5068 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 5071 */                               if (_jspx_th_logic_005fiterate_005f3.doEndTag() == 5) {
/* 5072 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3); return;
/*      */                               }
/*      */                               
/* 5075 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3);
/* 5076 */                               out.write("\n</table>\n</td>\n</tr>\n</td>\n</tr>\n</table>\n</td>\n</tr>\n</table>\n");
/*      */                             }
/* 5078 */                             out.write("\n\n<br>\n");
/*      */                             
/* 5080 */                             if (num_data > 0)
/*      */                             {
/* 5082 */                               out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" ><tr><td>\n<table WIDTH=\"99%\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" class=lrtborder>\n<tr>\n<td>\n<table align=\"center\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n\t  <tr>\n\t  <td colspan=\"5\" class=\"tableheading\">");
/* 5083 */                               out.print(FormatUtil.getString("Numeric Attributes Details"));
/* 5084 */                               out.write("</td>\n\t  </tr>\n        <tr>\n          <td width=\"50%\" class=\"columnheading\"><span class=\"bodytextbold\">");
/* 5085 */                               if (_jspx_meth_fmt_005fmessage_005f13(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 5087 */                               out.write("</span></td>");
/* 5088 */                               out.write("\n          <td width=\"15%\" class=\"columnheading\"><span class=\"bodytextbold\">");
/* 5089 */                               if (_jspx_meth_fmt_005fmessage_005f14(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 5091 */                               out.write("</span></td>");
/* 5092 */                               out.write("\n          <td width=\"15%\" class=\"columnheading\" align=\"center\"><span class=\"bodytextbold\">");
/* 5093 */                               if (_jspx_meth_fmt_005fmessage_005f15(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 5095 */                               out.write("</span></td>");
/* 5096 */                               out.write("\n          <td width=\"20%\" class=\"columnheading\" colspan=\"2\" align=\"center\"><span class=\"bodytextbold\">");
/* 5097 */                               out.print(ALERTCONFIG_TEXT);
/* 5098 */                               out.write("</span></td>");
/* 5099 */                               out.write("\n        </tr>\n</table>\n        <tr>\n        <td>\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" >\n");
/*      */                               
/* 5101 */                               IterateTag _jspx_th_logic_005fiterate_005f4 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5102 */                               _jspx_th_logic_005fiterate_005f4.setPageContext(_jspx_page_context);
/* 5103 */                               _jspx_th_logic_005fiterate_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 5105 */                               _jspx_th_logic_005fiterate_005f4.setName("numeric");
/*      */                               
/* 5107 */                               _jspx_th_logic_005fiterate_005f4.setId("attribute");
/*      */                               
/* 5109 */                               _jspx_th_logic_005fiterate_005f4.setIndexId("i");
/*      */                               
/* 5111 */                               _jspx_th_logic_005fiterate_005f4.setType("java.lang.String");
/* 5112 */                               int _jspx_eval_logic_005fiterate_005f4 = _jspx_th_logic_005fiterate_005f4.doStartTag();
/* 5113 */                               if (_jspx_eval_logic_005fiterate_005f4 != 0) {
/* 5114 */                                 String attribute = null;
/* 5115 */                                 Integer i = null;
/* 5116 */                                 if (_jspx_eval_logic_005fiterate_005f4 != 1) {
/* 5117 */                                   out = _jspx_page_context.pushBody();
/* 5118 */                                   _jspx_th_logic_005fiterate_005f4.setBodyContent((BodyContent)out);
/* 5119 */                                   _jspx_th_logic_005fiterate_005f4.doInitBody();
/*      */                                 }
/* 5121 */                                 attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 5122 */                                 i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                 for (;;) {
/* 5124 */                                   out.write(10);
/*      */                                   
/* 5126 */                                   if (displayname_attributeid.get(attribute) != null)
/*      */                                   {
/* 5128 */                                     int attributeid = Integer.parseInt(String.valueOf(displayname_attributeid.get(attribute)));
/*      */                                     
/* 5130 */                                     out.write(10);
/*      */                                     
/* 5132 */                                     int j = i.intValue();
/* 5133 */                                     String border = "whitegrayborder";
/* 5134 */                                     if (j % 2 == 0) {
/* 5135 */                                       border = "whitegrayborder";
/*      */                                     } else {
/* 5137 */                                       border = "yellowgrayborder";
/*      */                                     }
/* 5139 */                                     String value = FormatUtil.getString("am.webclient.nodata.text");
/* 5140 */                                     if ((ht_numeric.get(attribute) != null) && (!String.valueOf(ht_numeric.get(attribute)).equalsIgnoreCase("null"))) {
/* 5141 */                                       value = (String)ht_numeric.get(attribute);
/*      */                                     }
/* 5143 */                                     out.write("\n         <tr>\n          <td  height=\"25\" colspan=\"0\" width=\"50%\" class=\"");
/* 5144 */                                     out.print(border);
/* 5145 */                                     out.write("\" align=\"left\" >");
/* 5146 */                                     out.print(attribute);
/* 5147 */                                     out.write(" </td>\n          <td  height=\"25\" colspan=\"2\" width=\"15%\" align=\"left\" class=\"");
/* 5148 */                                     out.print(border);
/* 5149 */                                     out.write(34);
/* 5150 */                                     out.write(32);
/* 5151 */                                     out.write(62);
/* 5152 */                                     out.print(value);
/* 5153 */                                     out.write(" </td>\n          <td  width=\"15%\" colspan=\"2\" align=\"center\" class=\"");
/* 5154 */                                     out.print(border);
/* 5155 */                                     out.write("\" >  <a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5156 */                                     out.print(resID);
/* 5157 */                                     out.write("&attributeid=");
/* 5158 */                                     out.print(attributeid);
/* 5159 */                                     out.write("&alertconfigurl=");
/* 5160 */                                     out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs" + attributeid + "attributeToSelect=" + attributeid + "&redirectto=" + encodeurl));
/* 5161 */                                     out.write("')\">");
/* 5162 */                                     out.print(getSeverityImage(alert.getProperty(resID + "#" + attributeid)));
/* 5163 */                                     out.write(" </a></td>\n          <td  width=\"20%\" colspan=\"2\" height=\"35\" align=\"center\" class=\"");
/* 5164 */                                     out.print(border);
/* 5165 */                                     out.write("\" align=\"center\" ><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5166 */                                     out.print(resID);
/* 5167 */                                     out.write("&attributeIDs=");
/* 5168 */                                     out.print(attributeid);
/* 5169 */                                     out.write("&attributeToSelect=");
/* 5170 */                                     out.print(attributeid);
/* 5171 */                                     out.write("&redirectto=");
/* 5172 */                                     out.print(encodeurl);
/* 5173 */                                     out.write("\" class=\"staticlinks\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\" width=\"20\" height=\"20\" style=\"width: 10px; height: 13px;\" border=\"0\" title=\"\"></a></td>\n        </tr>\n        ");
/*      */                                   }
/* 5175 */                                   out.write(10);
/* 5176 */                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f4.doAfterBody();
/* 5177 */                                   attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 5178 */                                   i = (Integer)_jspx_page_context.findAttribute("i");
/* 5179 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 5182 */                                 if (_jspx_eval_logic_005fiterate_005f4 != 1) {
/* 5183 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 5186 */                               if (_jspx_th_logic_005fiterate_005f4.doEndTag() == 5) {
/* 5187 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f4); return;
/*      */                               }
/*      */                               
/* 5190 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f4);
/* 5191 */                               out.write("\n</table>\n</td>\n</tr>\n</td>\n</tr>\n</table>\n</td>\n</tr>\n</table>\n");
/*      */                             }
/* 5193 */                             out.write("\n\n<br>\n");
/*      */                             
/* 5195 */                             if (num_data > 0)
/*      */                             {
/* 5197 */                               out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" ><tr><td>\n<table WIDTH=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\n<tr>\n");
/*      */                               
/* 5199 */                               IterateTag _jspx_th_logic_005fiterate_005f5 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5200 */                               _jspx_th_logic_005fiterate_005f5.setPageContext(_jspx_page_context);
/* 5201 */                               _jspx_th_logic_005fiterate_005f5.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 5203 */                               _jspx_th_logic_005fiterate_005f5.setName("numeric");
/*      */                               
/* 5205 */                               _jspx_th_logic_005fiterate_005f5.setId("attribute");
/*      */                               
/* 5207 */                               _jspx_th_logic_005fiterate_005f5.setIndexId("i");
/*      */                               
/* 5209 */                               _jspx_th_logic_005fiterate_005f5.setType("java.lang.String");
/* 5210 */                               int _jspx_eval_logic_005fiterate_005f5 = _jspx_th_logic_005fiterate_005f5.doStartTag();
/* 5211 */                               if (_jspx_eval_logic_005fiterate_005f5 != 0) {
/* 5212 */                                 String attribute = null;
/* 5213 */                                 Integer i = null;
/* 5214 */                                 if (_jspx_eval_logic_005fiterate_005f5 != 1) {
/* 5215 */                                   out = _jspx_page_context.pushBody();
/* 5216 */                                   _jspx_th_logic_005fiterate_005f5.setBodyContent((BodyContent)out);
/* 5217 */                                   _jspx_th_logic_005fiterate_005f5.doInitBody();
/*      */                                 }
/* 5219 */                                 attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 5220 */                                 i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                 for (;;) {
/* 5222 */                                   out.write(10);
/* 5223 */                                   out.write(10);
/*      */                                   
/* 5225 */                                   boolean isLeftBox = localattributes % 2 == 0;
/* 5226 */                                   boolean isLastBox = localattributes == num_data - 1;
/* 5227 */                                   ???++;
/* 5228 */                                   int attributeid = Integer.parseInt(String.valueOf(displayname_attributeid.get(attribute)));
/*      */                                   
/* 5230 */                                   if ((resourcetype != null) && (!resourcetype.equals("Script Monitor")))
/*      */                                   {
/* 5232 */                                     camGraph.setParam(attributeid, resourcetype, attribute, Integer.parseInt(resID), true, baseid);
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 5236 */                                     camGraph.setParam(attributeid, resourcetype, attribute, Integer.parseInt(resID), false);
/*      */                                   }
/*      */                                   
/* 5239 */                                   String value = FormatUtil.getString("am.webclient.nodata.text");
/* 5240 */                                   if ((ht_numeric.get(attribute) != null) && (!String.valueOf(ht_numeric.get(attribute)).equalsIgnoreCase("null")))
/*      */                                   {
/* 5242 */                                     value = String.valueOf(ht_numeric.get(attribute));
/*      */                                   }
/*      */                                   
/*      */ 
/* 5246 */                                   out.write("\n\n<td  width=\"");
/* 5247 */                                   out.print(secondLevelTableWidth);
/* 5248 */                                   out.write("%\" align=\"left\" >\n  \t<table WIDTH=\"");
/* 5249 */                                   out.print(widthofinnertables);
/* 5250 */                                   out.write("%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"grayfullborder\">\n          <tr> \n            <td height=\"26\" class=\"tableheading\">");
/* 5251 */                                   out.print(attribute);
/* 5252 */                                   out.write(32);
/* 5253 */                                   out.write(45);
/* 5254 */                                   out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 5255 */                                   out.write("</td>   \n          </tr>\n          <tr>\n <td width=\"100%\" height=\"127\" valign=\"top\" colspan=2> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"50%\">\n ");
/*      */                                   
/* 5257 */                                   if (hm_enable.get(attribute).equals("enabled"))
/*      */                                   {
/*      */ 
/* 5260 */                                     out.write("\n        <tr> \n          <td width=\"90%\" height=\"35\" align=\"right\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5261 */                                     out.print(resID);
/* 5262 */                                     out.write("&attributeid=");
/* 5263 */                                     out.print(attributeid);
/* 5264 */                                     out.write("&period=-7',740,550)\"> \n            <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"Click to view last seven days data\"></a></td>\n          <td width=\"10%\" height=\"35\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5265 */                                     out.print(resID);
/* 5266 */                                     out.write("&attributeid=");
/* 5267 */                                     out.print(attributeid);
/* 5268 */                                     out.write("&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"Click to view last thirty days data\"></a></td>\n        </tr>\n");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/*      */ 
/* 5274 */                                     out.write("\n        <tr> \n          <td width=\"90%\" height=\"35\" align=\"right\"></td>\n          <td width=\"10%\" height=\"35\"></td>\n        </tr>\n    ");
/*      */                                   }
/*      */                                   
/*      */ 
/*      */ 
/* 5279 */                                   out.write("\n        <tr>\n          <td colspan=\"2\"> ");
/*      */                                   
/* 5281 */                                   TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 5282 */                                   _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 5283 */                                   _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_logic_005fiterate_005f5);
/*      */                                   
/* 5285 */                                   _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("camGraph");
/*      */                                   
/* 5287 */                                   _jspx_th_awolf_005ftimechart_005f1.setWidth("300");
/*      */                                   
/* 5289 */                                   _jspx_th_awolf_005ftimechart_005f1.setHeight("170");
/*      */                                   
/* 5291 */                                   _jspx_th_awolf_005ftimechart_005f1.setLegend("false");
/*      */                                   
/* 5293 */                                   _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(FormatUtil.getString("am.webclient.historydata.time.text"));
/*      */                                   
/* 5295 */                                   _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(attribute);
/*      */                                   
/* 5297 */                                   _jspx_th_awolf_005ftimechart_005f1.setDateFormat("HH:mm");
/* 5298 */                                   int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 5299 */                                   if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 5300 */                                     if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 5301 */                                       out = _jspx_page_context.pushBody();
/* 5302 */                                       _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 5303 */                                       _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 5306 */                                       out.write("\n            ");
/* 5307 */                                       int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 5308 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 5311 */                                     if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 5312 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 5315 */                                   if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 5316 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                                   }
/*      */                                   
/* 5319 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 5320 */                                   out.write(" </td>\n        </tr>\n      </table></td>\n</tr>\n<!--tr>\n<td>\n<table align=\"left\" width=\"100%\" border=0>\n</tr>\n<td colspan=\"4\" height=\"35\" class=\"whitegrayborder\" align=\"right\"><a href=\"/updateScript.do?method=enableReports&resourceid=");
/* 5321 */                                   out.print(resID);
/* 5322 */                                   out.write("&attributeid=");
/* 5323 */                                   out.print(attributeid);
/* 5324 */                                   out.write("\" class=\"staticlinks\">Enable Reports</a>\n</td>\n</tr>\n</table>\n</td>\n</tr-->\n          \n          <tr>\n          <td  align=left colspan=2 valign=\"top\">\n      <table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"4\" >\n        <tr>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 5325 */                                   if (_jspx_meth_fmt_005fmessage_005f16(_jspx_th_logic_005fiterate_005f5, _jspx_page_context))
/*      */                                     return;
/* 5327 */                                   out.write("</span></td>\n          <td class=\"columnheadingnotop\" align=\"center\"><span class=\"bodytextbold\">");
/* 5328 */                                   if (_jspx_meth_fmt_005fmessage_005f17(_jspx_th_logic_005fiterate_005f5, _jspx_page_context))
/*      */                                     return;
/* 5330 */                                   out.write("</span></td>\n          <td class=\"columnheadingnotop\" colspan=\"2\" align=\"center\"><span class=\"bodytextbold\">");
/* 5331 */                                   out.print(ALERTCONFIG_TEXT);
/* 5332 */                                   out.write("</span></td>\n        </tr>\n\n        <tr>\n          <td  height=\"25\" class=\"bodytext\">");
/* 5333 */                                   out.print(value);
/* 5334 */                                   out.write("</td>\n          <td width=\"16%\" align=\"center\"> <a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5335 */                                   out.print(resID);
/* 5336 */                                   out.write("&attributeid=");
/* 5337 */                                   out.print(attributeid);
/* 5338 */                                   out.write("&alertconfigurl=");
/* 5339 */                                   out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs" + attributeid + "attributeToSelect=" + attributeid + "&redirectto=" + encodeurl));
/* 5340 */                                   out.write("')\">");
/* 5341 */                                   out.print(getSeverityImage(alert.getProperty(resID + "#" + attributeid)));
/* 5342 */                                   out.write(" </a></td>\n          <td  colspan=\"4\" height=\"35\"  align=\"center\" ><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5343 */                                   out.print(resID);
/* 5344 */                                   out.write("&attributeIDs=");
/* 5345 */                                   out.print(attributeid);
/* 5346 */                                   out.write("&attributeToSelect=");
/* 5347 */                                   out.print(attributeid);
/* 5348 */                                   out.write("&redirectto=");
/* 5349 */                                   out.print(encodeurl);
/* 5350 */                                   out.write("\" class=\"staticlinks\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\" width=\"20\" height=\"20\" border=\"0\" style=\"width: 10px; height: 13px;\" title=\"\"></a></td>\n        </tr>\n      </table>\n          </td>\n          </tr>\n        </table>\n        \n       </td>\n\n");
/*      */                                   
/* 5352 */                                   if ((isLastBox) && (isLeftBox)) {
/* 5353 */                                     out.println("<tr><td>&nbsp;</td></tr>");
/*      */                                   }
/*      */                                   
/* 5356 */                                   if (!isLeftBox) {
/* 5357 */                                     out.println("<tr><td>&nbsp;</td></tr>");
/*      */                                   }
/*      */                                   
/* 5360 */                                   out.write("\t\n\n\n");
/* 5361 */                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f5.doAfterBody();
/* 5362 */                                   attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 5363 */                                   i = (Integer)_jspx_page_context.findAttribute("i");
/* 5364 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 5367 */                                 if (_jspx_eval_logic_005fiterate_005f5 != 1) {
/* 5368 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 5371 */                               if (_jspx_th_logic_005fiterate_005f5.doEndTag() == 5) {
/* 5372 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f5); return;
/*      */                               }
/*      */                               
/* 5375 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f5);
/* 5376 */                               out.write("\n</table>\n</td>\n</tr>\n</table>\n");
/*      */                             }
/* 5378 */                             out.write(10);
/* 5379 */                             out.write(10);
/* 5380 */                             out.write("<!--$Id$-->\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n");
/* 5381 */                             String mon_health = ess_atts.getProperty("Health");
/* 5382 */                             out.write("\n<script>\n  \t     /**\n  \t      * This Script is for disabling the Rows ... DC will not happen for these rows unlike Delete\n  \t      */\n  \t     function disableRows(e,frm,objname,frmname,formid,tableid)\n  \t     {\n  \t         var resid=\"\";\n  \t         var count=0;\n  \t         if(!checkforOneSelected(frmname,objname))\n  \t         {\n  \t           alert(\"");
/* 5383 */                             out.print(FormatUtil.getString("am.webclient.scriptrow.selectone.disable"));
/* 5384 */                             out.write("\");\n  \t           return;\n  \t         }\n  \t         else\n  \t         {\n  \t                 for(var i=0;i<document.getElementsByName(objname).length;i++)\n  \t                 {\n  \t                         if(document.getElementsByName(objname)[i].checked==true)\n  \t                         {\n  \t                                 var temp=document.getElementsByName(objname)[i].value;\n  \t                                 var test=temp.split(\",\");\n  \t                                 var res=test[0];\n  \t                                 if(resid!=\"\")\n  \t                                 {\n  \t                                 resid=resid+\",\"+res;\n  \t                                 count++;\n  \t                                 }\n  \t                                 else\n  \t                                 {\n  \t                                 resid=res;\n  \t                                 }\n  \t \n  \t                         }\n  \t \n  \t                 }\n  \t         }\n  \t         if(confirm(\"");
/* 5385 */                             out.print(FormatUtil.getString("am.webclient.scriptrow.disable.confirm"));
/* 5386 */                             out.write("\"))\n  \t         {\n                 document.getElementById(formid).action=\"/updateScript.do?method=disableRowByUser&resourceid=\"+resid+\"&tableid=\"+tableid+\"&scriptid=");
/* 5387 */                             out.print(resID);
/* 5388 */                             out.write("\";\n  \t         document.getElementById(formid).method.value='disableRowByUser';\n  \t         document.getElementById(formid).resourceid.value=resid;\n  \t         document.getElementById(formid).tableid.value=tableid;\n  \t         document.getElementById(formid).submit();\n  \t         }\n  \t         else\n  \t         {\n  \t                 return;\n  \t         }\n  \t     }\n  \t \n  \t     function enableRows(e,frm,objname,frmname,formid,tableid,table_health)\n  \t     {\n  \t         var resid=\"\";\n  \t         var count=0;\n  \t         if(!checkforOneSelected(frmname,objname))\n  \t         {\n  \t           alert(\"");
/* 5389 */                             out.print(FormatUtil.getString("am.webclient.scriptrow.selectone.disable"));
/* 5390 */                             out.write("\");\n  \t           return;\n  \t         }\n  \t         else\n  \t         {\n  \t                 for(var i=0;i<document.getElementsByName(objname).length;i++)\n  \t                 {\n  \t                         if(document.getElementsByName(objname)[i].checked==true)\n  \t                         {\n  \t                                 var temp=document.getElementsByName(objname)[i].value;\n  \t                                 var test=temp.split(\",\");\n  \t                                 var res=test[0];\n  \t                                 if(resid!=\"\")\n  \t                                 {\n  \t                                 resid=resid+\",\"+res;\n  \t                                 count++;\n  \t                                 }\n  \t                                 else\n  \t                                 {\n  \t                                 resid=res;\n  \t                                 }\n  \t \n  \t                         }\n  \t \n  \t                 }\n  \t         }\n  \t         if(confirm(\"");
/* 5391 */                             out.print(FormatUtil.getString("am.webclient.scriptrow.enable.confirm"));
/* 5392 */                             out.write("\"))\n  \t         {\n  \t         document.getElementById(formid).action=\"/updateScript.do?method=enableRowByUser&resourceid=\"+resid+\"&tableid=\"+tableid+\"&scriptid=");
/* 5393 */                             out.print(resID);
/* 5394 */                             out.write("&table_health=\"+table_health+\"&mon_health=");
/* 5395 */                             out.print(mon_health);
/* 5396 */                             out.write("\";\n  \t         document.getElementById(formid).method.value='enableRowByUser';\n  \t         document.getElementById(formid).resourceid.value=resid;\n  \t         document.getElementById(formid).tableid.value=tableid;\n  \t         document.getElementById(formid).submit();\n  \t         }\n  \t         else\n  \t         {\n  \t                 return;\n  \t         }\n  \t     }\n  \t \n  \t </script>\n<br>\n<table width=\"100%\" border=\"0\" style=\"table-layout:fixed\">\n<tr>\n<td>\n");
/*      */                             
/* 5398 */                             IterateTag _jspx_th_logic_005fiterate_005f6 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5399 */                             _jspx_th_logic_005fiterate_005f6.setPageContext(_jspx_page_context);
/* 5400 */                             _jspx_th_logic_005fiterate_005f6.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 5402 */                             _jspx_th_logic_005fiterate_005f6.setName("table_ids");
/*      */                             
/* 5404 */                             _jspx_th_logic_005fiterate_005f6.setId("attribute");
/*      */                             
/* 5406 */                             _jspx_th_logic_005fiterate_005f6.setIndexId("j");
/*      */                             
/* 5408 */                             _jspx_th_logic_005fiterate_005f6.setType("java.lang.String");
/* 5409 */                             int _jspx_eval_logic_005fiterate_005f6 = _jspx_th_logic_005fiterate_005f6.doStartTag();
/* 5410 */                             if (_jspx_eval_logic_005fiterate_005f6 != 0) {
/* 5411 */                               String attribute = null;
/* 5412 */                               Integer j = null;
/* 5413 */                               if (_jspx_eval_logic_005fiterate_005f6 != 1) {
/* 5414 */                                 out = _jspx_page_context.pushBody();
/* 5415 */                                 _jspx_th_logic_005fiterate_005f6.setBodyContent((BodyContent)out);
/* 5416 */                                 _jspx_th_logic_005fiterate_005f6.doInitBody();
/*      */                               }
/* 5418 */                               attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 5419 */                               j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                               for (;;) {
/* 5421 */                                 out.write(10);
/* 5422 */                                 out.write(10);
/*      */                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5449 */                                 boolean isQMExTimeTable = false;
/* 5450 */                                 ArrayList rowsDisabled = (ArrayList)table_rowsDisabled.get(attribute);
/* 5451 */                                 ArrayList attids = (ArrayList)((Hashtable)table_data.get(attribute)).get("column");
/* 5452 */                                 pageContext.setAttribute("attids", attids);
/* 5453 */                                 id1++;
/* 5454 */                                 int num_count = 0;
/* 5455 */                                 Properties num_mapper = new Properties();
/* 5456 */                                 for (int i = 0; i < attids.size(); ???++)
/*      */                                 {
/* 5458 */                                   if ((attid_details.get(attids.get(i)) != null) && (((ArrayList)attid_details.get(attids.get(i))).get(1).equals("0")))
/*      */                                   {
/* 5460 */                                     ???++;
/* 5461 */                                     num_mapper.setProperty((String)attids.get(i), "true");
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 5465 */                                     num_mapper.setProperty((String)attids.get(i), "false");
/*      */                                   }
/*      */                                 }
/* 5468 */                                 Hashtable data = (Hashtable)((Hashtable)table_data.get(attribute)).get("data");
/*      */                                 
/* 5470 */                                 ArrayList rowids = (ArrayList)table_resids.get(tableids.get(j.intValue()));
/* 5471 */                                 pageContext.setAttribute("rowids", rowids);
/* 5472 */                                 int col_count = attids.size() + 3;
/* 5473 */                                 if (num_count == 0) {}
/*      */                                 
/*      */ 
/*      */ 
/* 5477 */                                 float width = 98 / (attids.size() + 2);
/* 5478 */                                 String attids_commas = "";
/* 5479 */                                 String att_to_select = "";
/* 5480 */                                 if (attids.size() > 0)
/*      */                                 {
/* 5482 */                                   att_to_select = att_to_select + attids.get(0);
/*      */                                 }
/* 5484 */                                 String rowids_asString = "";
/* 5485 */                                 String fname = "forms" + j + "_" + resID;
/* 5486 */                                 String formid = "fid" + j + "_" + resID;
/* 5487 */                                 String checkname = "resrows" + j;
/* 5488 */                                 String checkid = "checkID" + j;
/* 5489 */                                 String colavaid = (String)table_avail.get(attribute);
/* 5490 */                                 if (colavaid == null)
/*      */                                 {
/* 5492 */                                   colavaid = "";
/*      */                                 }
/* 5494 */                                 String table_healthid = (String)table_health.get(attribute);
/* 5495 */                                 boolean datapresent = false;
/* 5496 */                                 if (table_avail_data.get(attribute) != null)
/* 5497 */                                   datapresent = table_avail_data.get(attribute).toString().equals("YES");
/* 5498 */                                 boolean setHeight = (rowids != null) && (rowids.size() > 1);
/* 5499 */                                 String heightCss = setHeight ? "" : "style=height:50px;white-space:nowrap;";
/* 5500 */                                 String overflowCss = setHeight ? "overflow:auto;" : "overflow-x:auto;";
/*      */                                 
/* 5502 */                                 out.write("\n<form name=\"");
/* 5503 */                                 out.print(fname);
/* 5504 */                                 out.write("\" id=\"");
/* 5505 */                                 out.print(formid);
/* 5506 */                                 out.write("\" method=post>\n<input type=\"hidden\" name=\"method\" value=\"deleteTableByUser\"/>\n<input type=\"hidden\" name=\"resourceid\" value=\"\"/>\n<input type=\"hidden\" name=\"tableid\" value=\"\"/>\n<input type=\"hidden\" name=\"sqlmanid\" value=\"\"/>\n<input type=\"hidden\" name=\"baseid\" id=\"baseid\" value=\"");
/* 5507 */                                 if (_jspx_meth_c_005fout_005f43(_jspx_th_logic_005fiterate_005f6, _jspx_page_context))
/*      */                                   return;
/* 5509 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"type\" value=\"");
/* 5510 */                                 out.print(resourcetype);
/* 5511 */                                 out.write("\" />\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbtborder\" >\n<tr> \n            <td height=\"26\"  class=\"tableheading\" colspan=\"");
/* 5512 */                                 out.print(col_count - 3);
/* 5513 */                                 out.write("\"> <!--%=FormatUtil.getString(\"am.monitortab.table.text\")%--> ");
/* 5514 */                                 out.print(FormatUtil.getString((String)tabid_tabname.get(tableids.get(j.intValue()))));
/* 5515 */                                 out.write("</td>   \n            ");
/*      */                                 
/* 5517 */                                 String tabName = (String)tabid_tabname.get(tableids.get(j.intValue()));
/*      */                                 
/*      */ 
/* 5520 */                                 if ((resourcetype != null) && (resourcetype.equals("Script Monitor")) && (!request.isUserInRole("ENTERPRISEADMIN")))
/*      */                                 {
/*      */ 
/* 5523 */                                   out.write("\n<td height=\"26\"  width=\"15%\" class=\"tableheading\" align=\"right\" colspan=\"1\"><a class=\"bodytextboldwhiteun\" href=\"javascript:deleteTablesAlert('");
/* 5524 */                                   out.print(resID);
/* 5525 */                                   out.write(39);
/* 5526 */                                   out.write(44);
/* 5527 */                                   out.write(39);
/* 5528 */                                   out.print(tableids.get(j.intValue()));
/* 5529 */                                   out.write(39);
/* 5530 */                                   out.write(44);
/* 5531 */                                   out.write(39);
/* 5532 */                                   out.print(formid);
/* 5533 */                                   out.write("',this.form)\" style=\"margin-right:10px;\">");
/* 5534 */                                   out.print(FormatUtil.getString("am.script.deletetable.text"));
/* 5535 */                                   out.write("</a></td> \n            ");
/*      */                                 }
/* 5537 */                                 else if ((resourcetype != null) && (resourcetype.equals("QueryMonitor")) && (!tabName.equals("Execution Time")))
/*      */                                 {
/*      */ 
/* 5540 */                                   out.write("\n\n<td height=\"26\" colspan=\"2\"  width=\"25%\" class=\"tableheading\" align=\"right\" colspan=\"1\"><a class=\"bodytextboldwhiteun\" href=\"javascript:MM_openBrWindow('/jsp/queryEdit.jsp?tableId=");
/* 5541 */                                   out.print(tableids.get(j.intValue()));
/* 5542 */                                   out.write("&resourceid=");
/* 5543 */                                   out.print(resID);
/* 5544 */                                   out.write("','Personalize','width=390,height=300,screenX=100,screenY=300,scrollbars=yes')\" style=\"margin-right:10px;\">");
/* 5545 */                                   out.print(FormatUtil.getString("Primary Keys"));
/* 5546 */                                   out.write("</a></td>\n\n\n\n");
/*      */ 
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/*      */ 
/* 5554 */                                   if ((resourcetype != null) && (resourcetype.equals("QueryMonitor")) && (tabName.equals("Execution Time"))) {
/* 5555 */                                     isQMExTimeTable = true;
/*      */                                   }
/* 5557 */                                   out.write("\n                <td height=\"26\"  width=\"15%\" class=\"tableheading\" align=\"right\" colspan=\"1\"></td> \n                ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 5561 */                                 out.write("\n</tr>\n</table>\n<div id=\"");
/* 5562 */                                 out.print(id1);
/* 5563 */                                 out.write("\" style=\"width:99%;");
/* 5564 */                                 out.print(overflowCss);
/* 5565 */                                 out.write("\">\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\" id=\"scriptTableOutput");
/* 5566 */                                 out.print(id1);
/* 5567 */                                 out.write("\">\n<tr>\n\n");
/*      */                                 
/* 5569 */                                 if (num_count >= 0)
/*      */                                 {
/*      */ 
/* 5572 */                                   out.write("\n<td height=\"26\" align=\"center\" width=\"2%\" class=\"columnheading\"><input type=\"checkbox\"  name=\"headercheckbox\" onclick=\"javascript:fnSelectAllform(this,this.form,'");
/* 5573 */                                   out.print(checkname);
/* 5574 */                                   out.write("')\" /> </td>\n");
/*      */                                 }
/*      */                                 
/* 5577 */                                 boolean toshow = true;
/*      */                                 
/* 5579 */                                 out.write(10);
/*      */                                 
/* 5581 */                                 IterateTag _jspx_th_logic_005fiterate_005f7 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5582 */                                 _jspx_th_logic_005fiterate_005f7.setPageContext(_jspx_page_context);
/* 5583 */                                 _jspx_th_logic_005fiterate_005f7.setParent(_jspx_th_logic_005fiterate_005f6);
/*      */                                 
/* 5585 */                                 _jspx_th_logic_005fiterate_005f7.setName("attids");
/*      */                                 
/* 5587 */                                 _jspx_th_logic_005fiterate_005f7.setId("colnames");
/*      */                                 
/* 5589 */                                 _jspx_th_logic_005fiterate_005f7.setIndexId("k");
/*      */                                 
/* 5591 */                                 _jspx_th_logic_005fiterate_005f7.setType("java.lang.String");
/* 5592 */                                 int _jspx_eval_logic_005fiterate_005f7 = _jspx_th_logic_005fiterate_005f7.doStartTag();
/* 5593 */                                 if (_jspx_eval_logic_005fiterate_005f7 != 0) {
/* 5594 */                                   String colnames = null;
/* 5595 */                                   Integer k = null;
/* 5596 */                                   if (_jspx_eval_logic_005fiterate_005f7 != 1) {
/* 5597 */                                     out = _jspx_page_context.pushBody();
/* 5598 */                                     _jspx_th_logic_005fiterate_005f7.setBodyContent((BodyContent)out);
/* 5599 */                                     _jspx_th_logic_005fiterate_005f7.doInitBody();
/*      */                                   }
/* 5601 */                                   colnames = (String)_jspx_page_context.findAttribute("colnames");
/* 5602 */                                   k = (Integer)_jspx_page_context.findAttribute("k");
/*      */                                   for (;;) {
/* 5604 */                                     out.write(10);
/* 5605 */                                     out.write(9);
/*      */                                     
/*      */ 
/* 5608 */                                     String col_name = (String)((ArrayList)attid_details.get(colnames)).get(0);
/* 5609 */                                     if ((col_name.equals("Availability")) && (!datapresent))
/*      */                                     {
/* 5611 */                                       toshow = false;
/*      */                                     }
/*      */                                     else {
/* 5614 */                                       attids_commas = attids_commas + colnames + ",";
/* 5615 */                                       toshow = true;
/*      */                                     }
/* 5617 */                                     if (toshow)
/*      */                                     {
/* 5619 */                                       if (col_name.equals("Execution Time")) {
/* 5620 */                                         col_name = col_name + " (ms) ";
/*      */                                       }
/*      */                                       
/* 5623 */                                       out.write("\n            <td height=\"26\" align=\"left\" width=\"25%\"  class=\"columnheading\"><a id=\"scriptTableOutput");
/* 5624 */                                       out.print(id1);
/* 5625 */                                       out.write("_header");
/* 5626 */                                       out.print(k);
/* 5627 */                                       out.write("\" href=\"#\" style=\"text-decoration: none; color: rgb(0, 0, 0);\" onclick=\"ts_resortTable(this,'scriptTableOutput");
/* 5628 */                                       out.print(id1);
/* 5629 */                                       out.write("',0);return false;\"><span class=\"bodytextbold\">");
/* 5630 */                                       out.print(FormatUtil.getString(col_name));
/* 5631 */                                       out.write("</span><span class=\"sortarrow\">&nbsp;</span></a></td>\n            ");
/*      */                                     }
/*      */                                     
/*      */ 
/* 5635 */                                     out.write(10);
/* 5636 */                                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f7.doAfterBody();
/* 5637 */                                     colnames = (String)_jspx_page_context.findAttribute("colnames");
/* 5638 */                                     k = (Integer)_jspx_page_context.findAttribute("k");
/* 5639 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 5642 */                                   if (_jspx_eval_logic_005fiterate_005f7 != 1) {
/* 5643 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 5646 */                                 if (_jspx_th_logic_005fiterate_005f7.doEndTag() == 5) {
/* 5647 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f7); return;
/*      */                                 }
/*      */                                 
/* 5650 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f7);
/* 5651 */                                 out.write(10);
/*      */                                 
/* 5653 */                                 if ((attids_commas != null) && (attids_commas.length() > 0))
/*      */                                 {
/* 5655 */                                   attids_commas = attids_commas.substring(0, attids_commas.length() - 1);
/*      */                                 }
/* 5657 */                                 attids_commas = attids_commas + "," + table_healthid;
/* 5658 */                                 String colsp = "";
/* 5659 */                                 if (!toshow)
/*      */                                 {
/* 5661 */                                   colsp = "2";
/*      */                                 }
/*      */                                 
/* 5664 */                                 out.write("\n\n<td height=\"26\" align=\"center\" width=\"20%\"  class=\"columnheading\"><a id=\"scriptTableOutput");
/* 5665 */                                 out.print(id1);
/* 5666 */                                 out.write("_header");
/* 5667 */                                 out.print(attids.size());
/* 5668 */                                 out.write("\" href=\"#\" style=\"text-decoration: none; color: rgb(0, 0, 0);\" onclick=\"ts_resortTable(this,'scriptTableOutput");
/* 5669 */                                 out.print(id1);
/* 5670 */                                 out.write("',0);return false;\"><span class=\"bodytextbold\">");
/* 5671 */                                 out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 5672 */                                 out.write("</span><span class=\"sortarrow\">&nbsp;</span></a></td> \n\n<td height=\"26\" align=\"center\" colspan=\"");
/* 5673 */                                 out.print(colsp);
/* 5674 */                                 out.write("\" width=\"20%\" class=\"columnheading\" nowrap=\"nowrap\"><font style=\"margin-right:10px;\">");
/* 5675 */                                 out.print(ALERTCONFIG_TEXT);
/* 5676 */                                 out.write("</font></td> \n\n</tr>\n\n");
/* 5677 */                                 if ((rowids != null) && (rowids.size() > 0)) {
/* 5678 */                                   out.write(10);
/* 5679 */                                   out.write(10);
/*      */                                   
/* 5681 */                                   IterateTag _jspx_th_logic_005fiterate_005f8 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5682 */                                   _jspx_th_logic_005fiterate_005f8.setPageContext(_jspx_page_context);
/* 5683 */                                   _jspx_th_logic_005fiterate_005f8.setParent(_jspx_th_logic_005fiterate_005f6);
/*      */                                   
/* 5685 */                                   _jspx_th_logic_005fiterate_005f8.setName("rowids");
/*      */                                   
/* 5687 */                                   _jspx_th_logic_005fiterate_005f8.setId("rowid");
/*      */                                   
/* 5689 */                                   _jspx_th_logic_005fiterate_005f8.setIndexId("i");
/*      */                                   
/* 5691 */                                   _jspx_th_logic_005fiterate_005f8.setType("java.lang.String");
/* 5692 */                                   int _jspx_eval_logic_005fiterate_005f8 = _jspx_th_logic_005fiterate_005f8.doStartTag();
/* 5693 */                                   if (_jspx_eval_logic_005fiterate_005f8 != 0) {
/* 5694 */                                     String rowid = null;
/* 5695 */                                     Integer i = null;
/* 5696 */                                     if (_jspx_eval_logic_005fiterate_005f8 != 1) {
/* 5697 */                                       out = _jspx_page_context.pushBody();
/* 5698 */                                       _jspx_th_logic_005fiterate_005f8.setBodyContent((BodyContent)out);
/* 5699 */                                       _jspx_th_logic_005fiterate_005f8.doInitBody();
/*      */                                     }
/* 5701 */                                     rowid = (String)_jspx_page_context.findAttribute("rowid");
/* 5702 */                                     i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                     for (;;) {
/* 5704 */                                       out.write(10);
/* 5705 */                                       out.write(10);
/*      */                                       
/* 5707 */                                       boolean isdisable = false;
/* 5708 */                                       if (rowsDisabled.contains(rowid)) {
/* 5709 */                                         isdisable = true;
/*      */                                       }
/* 5711 */                                       rowids_asString = rowids_asString + rowid + ",";
/* 5712 */                                       int temp_val = i.intValue();
/* 5713 */                                       String bgclass = "whitegrayrightalign-reports-normal";
/* 5714 */                                       String textclass = "";
/* 5715 */                                       if (isdisable) {
/* 5716 */                                         textclass = "class=\"disabledtext\"";
/*      */                                       }
/* 5718 */                                       if (temp_val % 2 == 0)
/*      */                                       {
/* 5720 */                                         bgclass = "whitegrayrightalign-reports-normal";
/*      */                                       }
/*      */                                       try {
/* 5723 */                                         if (((Hashtable)data.get(rowid) != null) || (isdisable)) {
/* 5724 */                                           out.write("\n<tr>\n");
/*      */                                           
/* 5726 */                                           if (num_count >= 0)
/*      */                                           {
/*      */ 
/* 5729 */                                             out.write("\n<td width=\"5%\" align=\"center\" class=\"");
/* 5730 */                                             out.print(bgclass);
/* 5731 */                                             out.write(34);
/* 5732 */                                             out.write(32);
/* 5733 */                                             out.print(heightCss);
/* 5734 */                                             out.write(" ><input type=\"checkbox\" name=\"");
/* 5735 */                                             out.print(checkname);
/* 5736 */                                             out.write("\" id=\"");
/* 5737 */                                             out.print(checkid);
/* 5738 */                                             out.write("\" value=\"");
/* 5739 */                                             out.print(rowid);
/* 5740 */                                             out.write("\"/> </td>\n");
/*      */                                           }
/*      */                                           
/*      */ 
/* 5744 */                                           out.write(10);
/*      */                                           
/* 5746 */                                           IterateTag _jspx_th_logic_005fiterate_005f9 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5747 */                                           _jspx_th_logic_005fiterate_005f9.setPageContext(_jspx_page_context);
/* 5748 */                                           _jspx_th_logic_005fiterate_005f9.setParent(_jspx_th_logic_005fiterate_005f8);
/*      */                                           
/* 5750 */                                           _jspx_th_logic_005fiterate_005f9.setName("attids");
/*      */                                           
/* 5752 */                                           _jspx_th_logic_005fiterate_005f9.setId("colvalues");
/*      */                                           
/* 5754 */                                           _jspx_th_logic_005fiterate_005f9.setIndexId("k");
/*      */                                           
/* 5756 */                                           _jspx_th_logic_005fiterate_005f9.setType("java.lang.String");
/* 5757 */                                           int _jspx_eval_logic_005fiterate_005f9 = _jspx_th_logic_005fiterate_005f9.doStartTag();
/* 5758 */                                           if (_jspx_eval_logic_005fiterate_005f9 != 0) {
/* 5759 */                                             String colvalues = null;
/* 5760 */                                             Integer k = null;
/* 5761 */                                             if (_jspx_eval_logic_005fiterate_005f9 != 1) {
/* 5762 */                                               out = _jspx_page_context.pushBody();
/* 5763 */                                               _jspx_th_logic_005fiterate_005f9.setBodyContent((BodyContent)out);
/* 5764 */                                               _jspx_th_logic_005fiterate_005f9.doInitBody();
/*      */                                             }
/* 5766 */                                             colvalues = (String)_jspx_page_context.findAttribute("colvalues");
/* 5767 */                                             k = (Integer)_jspx_page_context.findAttribute("k");
/*      */                                             for (;;) {
/* 5769 */                                               out.write("\n    ");
/*      */                                               
/* 5771 */                                               toshow = true;
/* 5772 */                                               if ((colavaid.equals(colvalues)) && (!datapresent))
/*      */                                               {
/* 5774 */                                                 toshow = false;
/*      */                                               }
/* 5776 */                                               if (!colavaid.equals(colvalues))
/*      */                                               {
/*      */ 
/* 5779 */                                                 out.write("\n    \n<td width=\"26%\" align=\"left\" class=\"");
/* 5780 */                                                 out.print(bgclass);
/* 5781 */                                                 out.write(34);
/* 5782 */                                                 out.write(32);
/* 5783 */                                                 out.print(heightCss);
/* 5784 */                                                 out.write(62);
/* 5785 */                                                 out.write(10);
/*      */                                                 
/* 5787 */                                                 String data1 = "";
/*      */                                                 try {
/* 5789 */                                                   data1 = (String)((Hashtable)data.get(rowid)).get(colvalues);
/* 5790 */                                                   data1 = StrUtil.findReplace(data1, "<", "&lt;");
/* 5791 */                                                   data1 = StrUtil.findReplace(data1, ">", "&gt;");
/*      */                                                 }
/*      */                                                 catch (Exception ex) {
/* 5794 */                                                   ex.printStackTrace();
/*      */                                                 }
/* 5796 */                                                 if ((data1 == null) || (data1.equals("")))
/*      */                                                 {
/* 5798 */                                                   data1 = "NA";
/*      */                                                 }
/*      */                                                 
/* 5801 */                                                 if ((num_mapper.getProperty(colvalues) != null) && (num_mapper.getProperty(colvalues).equals("true")))
/*      */                                                 {
/*      */ 
/*      */ 
/* 5805 */                                                   out.write("\n<a href=\"javascript:showGraphForthis('");
/* 5806 */                                                   out.print(colvalues);
/* 5807 */                                                   out.write(39);
/* 5808 */                                                   out.write(44);
/* 5809 */                                                   out.write(39);
/* 5810 */                                                   out.print(rowid);
/* 5811 */                                                   out.write(39);
/* 5812 */                                                   out.write(44);
/* 5813 */                                                   out.write(39);
/* 5814 */                                                   out.print(((ArrayList)attid_details.get(colvalues)).get(0));
/* 5815 */                                                   out.write(39);
/* 5816 */                                                   out.write(44);
/* 5817 */                                                   out.write(39);
/* 5818 */                                                   out.print(isQMExTimeTable);
/* 5819 */                                                   out.write("')\" class=\"staticlinks\">\n<span ");
/* 5820 */                                                   out.print(textclass);
/* 5821 */                                                   out.write(62);
/* 5822 */                                                   out.write(10);
/* 5823 */                                                   out.print(data1);
/* 5824 */                                                   out.write("</span></a>\n");
/*      */ 
/*      */                                                 }
/*      */                                                 else
/*      */                                                 {
/* 5829 */                                                   out.write("\n<span ");
/* 5830 */                                                   out.print(textclass);
/* 5831 */                                                   out.write(62);
/* 5832 */                                                   out.write(10);
/* 5833 */                                                   out.print(data1);
/* 5834 */                                                   out.write("</span></a>\n");
/*      */                                                 }
/*      */                                                 
/*      */ 
/* 5838 */                                                 out.write("\n </td>\n ");
/*      */ 
/*      */                                               }
/* 5841 */                                               else if (datapresent)
/*      */                                               {
/*      */ 
/* 5844 */                                                 out.write("\n <td width=\"15%\" align=\"center\" class=\"");
/* 5845 */                                                 out.print(bgclass);
/* 5846 */                                                 out.write(34);
/* 5847 */                                                 out.write(32);
/* 5848 */                                                 out.print(heightCss);
/* 5849 */                                                 out.write(" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5850 */                                                 out.print(rowid);
/* 5851 */                                                 out.write("&attributeid=");
/* 5852 */                                                 out.print(colvalues);
/* 5853 */                                                 out.write("&alertconfigurl=");
/* 5854 */                                                 out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + rowid + "&attributeIDs=" + availabilitykeys.get(resourcetype) + "&attributeToSelect=" + availabilitykeys.get(resourcetype) + "&redirectto=" + encodeurl));
/* 5855 */                                                 out.write("')\"> \n            ");
/* 5856 */                                                 out.print(getSeverityImageForAvailability(alert.getProperty(rowid + "#" + colvalues)));
/* 5857 */                                                 out.write(" \n            </a>\n </td>\n ");
/*      */                                               }
/*      */                                               
/*      */ 
/* 5861 */                                               out.write(10);
/* 5862 */                                               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f9.doAfterBody();
/* 5863 */                                               colvalues = (String)_jspx_page_context.findAttribute("colvalues");
/* 5864 */                                               k = (Integer)_jspx_page_context.findAttribute("k");
/* 5865 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 5868 */                                             if (_jspx_eval_logic_005fiterate_005f9 != 1) {
/* 5869 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 5872 */                                           if (_jspx_th_logic_005fiterate_005f9.doEndTag() == 5) {
/* 5873 */                                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f9); return;
/*      */                                           }
/*      */                                           
/* 5876 */                                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f9);
/* 5877 */                                           out.write("\n<td width=\"20%\"  align=\"center\" height=\"25\" align=\"left\" class=\"");
/* 5878 */                                           out.print(bgclass);
/* 5879 */                                           out.write(34);
/* 5880 */                                           out.write(32);
/* 5881 */                                           out.print(heightCss);
/* 5882 */                                           out.write(" ><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5883 */                                           out.print(rowid);
/* 5884 */                                           out.write("&attributeid=");
/* 5885 */                                           out.print(table_health.get(attribute));
/* 5886 */                                           out.write("&alertconfigurl=");
/* 5887 */                                           out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + rowid + "&attributeIDs=" + availabilitykeys.get(resourcetype) + "&attributeToSelect=" + availabilitykeys.get(resourcetype) + "&redirectto=" + encodeurl));
/* 5888 */                                           out.write("')\"> \n            ");
/* 5889 */                                           out.print(getSeverityImageForHealth(alert.getProperty(rowid + "#" + table_health.get(attribute))));
/* 5890 */                                           out.write("<span style=\"display:none\">");
/* 5891 */                                           out.print(alert.getProperty(rowid + "#" + table_health.get(attribute)));
/* 5892 */                                           out.write("</span> \n            </a></td>\n<td width=\"20%\" colspan=\"2\" align=\"center\" class=\"");
/* 5893 */                                           out.print(bgclass);
/* 5894 */                                           out.write(34);
/* 5895 */                                           out.write(32);
/* 5896 */                                           out.print(heightCss);
/* 5897 */                                           out.write(" ><a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5898 */                                           out.print(rowid);
/* 5899 */                                           out.write("&attributeIDs=");
/* 5900 */                                           out.print(attids_commas);
/* 5901 */                                           out.write("&attributeToSelect=");
/* 5902 */                                           out.print(att_to_select);
/* 5903 */                                           out.write("&redirectto=");
/* 5904 */                                           out.print(encodeurl);
/* 5905 */                                           out.write("' class=\"staticlinks\"><img src=\"../images/icon_associateaction.gif\" hspace=\"4\" border=\"0\" align=\"absmiddle\"></a></td>\n</tr>\n");
/*      */                                         }
/*      */                                       }
/*      */                                       catch (Exception ex) {
/* 5909 */                                         ex.printStackTrace();
/*      */                                       }
/*      */                                       
/* 5912 */                                       out.write(10);
/* 5913 */                                       int evalDoAfterBody = _jspx_th_logic_005fiterate_005f8.doAfterBody();
/* 5914 */                                       rowid = (String)_jspx_page_context.findAttribute("rowid");
/* 5915 */                                       i = (Integer)_jspx_page_context.findAttribute("i");
/* 5916 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 5919 */                                     if (_jspx_eval_logic_005fiterate_005f8 != 1) {
/* 5920 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 5923 */                                   if (_jspx_th_logic_005fiterate_005f8.doEndTag() == 5) {
/* 5924 */                                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f8); return;
/*      */                                   }
/*      */                                   
/* 5927 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f8);
/* 5928 */                                   out.write(10);
/*      */                                 } else {
/* 5930 */                                   out.write("\n\n<tr>\n<td class=\"whitegrayrightalign-reports-normal\" colspan=\"");
/* 5931 */                                   out.print(col_count - 3);
/* 5932 */                                   out.write("\" align=\"center\" style=\"line-height: 45px;border: none;\">\n");
/* 5933 */                                   out.print(FormatUtil.getString("No_Data_Available"));
/* 5934 */                                   out.write("\n</td>\n</tr>\n\n");
/*      */                                 }
/* 5936 */                                 out.write("\n</table>\n</div>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n<tr > \n<td colspan=\"3\" width=\"90%\" height=\"31\" align=\"left\" class=\"tablebottom\">\n<!-- scripttable disable starts-->\n<a class=\"staticlinks\" href=\"javascript:disableRows(this,'");
/* 5937 */                                 out.print(fname);
/* 5938 */                                 out.write(39);
/* 5939 */                                 out.write(44);
/* 5940 */                                 out.write(39);
/* 5941 */                                 out.print(checkname);
/* 5942 */                                 out.write(39);
/* 5943 */                                 out.write(44);
/* 5944 */                                 out.print(fname);
/* 5945 */                                 out.write(44);
/* 5946 */                                 out.write(39);
/* 5947 */                                 out.print(formid);
/* 5948 */                                 out.write(39);
/* 5949 */                                 out.write(44);
/* 5950 */                                 out.write(39);
/* 5951 */                                 out.print(attribute);
/* 5952 */                                 out.write("')\">");
/* 5953 */                                 out.print(FormatUtil.getString("Disable"));
/* 5954 */                                 out.write("</a>&nbsp;<span class=\"bodytext\">|&nbsp;<a class=\"staticlinks\" href=\"javascript:enableRows(this,'");
/* 5955 */                                 out.print(fname);
/* 5956 */                                 out.write(39);
/* 5957 */                                 out.write(44);
/* 5958 */                                 out.write(39);
/* 5959 */                                 out.print(checkname);
/* 5960 */                                 out.write(39);
/* 5961 */                                 out.write(44);
/* 5962 */                                 out.print(fname);
/* 5963 */                                 out.write(44);
/* 5964 */                                 out.write(39);
/* 5965 */                                 out.print(formid);
/* 5966 */                                 out.write(39);
/* 5967 */                                 out.write(44);
/* 5968 */                                 out.write(39);
/* 5969 */                                 out.print(attribute);
/* 5970 */                                 out.write(39);
/* 5971 */                                 out.write(44);
/* 5972 */                                 out.write(39);
/* 5973 */                                 out.print(table_healthid);
/* 5974 */                                 out.write("')\">");
/* 5975 */                                 out.print(FormatUtil.getString("Enable"));
/* 5976 */                                 out.write("</a>&nbsp;<span class=\"bodytext\">|&nbsp;<a name=\"");
/* 5977 */                                 out.print(attribute);
/* 5978 */                                 out.write("\" class=\"staticlinks\" href=\"#");
/* 5979 */                                 out.print(attribute);
/* 5980 */                                 out.write("\" onClick=\"javascript:deleteRows(this,'");
/* 5981 */                                 out.print(fname);
/* 5982 */                                 out.write(39);
/* 5983 */                                 out.write(44);
/* 5984 */                                 out.write(39);
/* 5985 */                                 out.print(checkname);
/* 5986 */                                 out.write(39);
/* 5987 */                                 out.write(44);
/* 5988 */                                 out.print(fname);
/* 5989 */                                 out.write(44);
/* 5990 */                                 out.write(39);
/* 5991 */                                 out.print(formid);
/* 5992 */                                 out.write("')\">");
/* 5993 */                                 out.print(FormatUtil.getString("am.webclient.cam.delete.link"));
/* 5994 */                                 out.write("</a>&nbsp;<span class=\"bodytext\">|&nbsp;\n<!-- scripttable disable ends-->\n");
/*      */                                 
/* 5996 */                                 if (num_count > 0)
/*      */                                 {
/*      */ 
/* 5999 */                                   out.write("\n<a href=\"javascript:showGraph(this,'");
/* 6000 */                                   out.print(fname);
/* 6001 */                                   out.write(39);
/* 6002 */                                   out.write(44);
/* 6003 */                                   out.write(39);
/* 6004 */                                   out.print(checkname);
/* 6005 */                                   out.write(39);
/* 6006 */                                   out.write(44);
/* 6007 */                                   out.write(39);
/* 6008 */                                   out.print(formid);
/* 6009 */                                   out.write(39);
/* 6010 */                                   out.write(44);
/* 6011 */                                   out.write(39);
/* 6012 */                                   out.print(isQMExTimeTable);
/* 6013 */                                   out.write("')\" class=\"staticlinks\">");
/* 6014 */                                   out.print(FormatUtil.getString("am.webclient.report.compare.text"));
/* 6015 */                                   out.write("</a> ");
/*      */                                   
/* 6017 */                                   if (num_count > 0)
/*      */                                   {
/*      */ 
/* 6020 */                                     out.write("\n    <select name=\"attList\" class=\"formtext\" onchange=\"javascript:showGraph(this,'");
/* 6021 */                                     out.print(fname);
/* 6022 */                                     out.write(39);
/* 6023 */                                     out.write(44);
/* 6024 */                                     out.write(39);
/* 6025 */                                     out.print(checkname);
/* 6026 */                                     out.write(39);
/* 6027 */                                     out.write(44);
/* 6028 */                                     out.write(39);
/* 6029 */                                     out.print(formid);
/* 6030 */                                     out.write(39);
/* 6031 */                                     out.write(44);
/* 6032 */                                     out.write(39);
/* 6033 */                                     out.print(isQMExTimeTable);
/* 6034 */                                     out.write("')\">\n    ");
/*      */                                     
/* 6036 */                                     IterateTag _jspx_th_logic_005fiterate_005f10 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 6037 */                                     _jspx_th_logic_005fiterate_005f10.setPageContext(_jspx_page_context);
/* 6038 */                                     _jspx_th_logic_005fiterate_005f10.setParent(_jspx_th_logic_005fiterate_005f6);
/*      */                                     
/* 6040 */                                     _jspx_th_logic_005fiterate_005f10.setName("attids");
/*      */                                     
/* 6042 */                                     _jspx_th_logic_005fiterate_005f10.setId("colnames");
/*      */                                     
/* 6044 */                                     _jspx_th_logic_005fiterate_005f10.setIndexId("k");
/*      */                                     
/* 6046 */                                     _jspx_th_logic_005fiterate_005f10.setType("java.lang.String");
/* 6047 */                                     int _jspx_eval_logic_005fiterate_005f10 = _jspx_th_logic_005fiterate_005f10.doStartTag();
/* 6048 */                                     if (_jspx_eval_logic_005fiterate_005f10 != 0) {
/* 6049 */                                       String colnames = null;
/* 6050 */                                       Integer k = null;
/* 6051 */                                       if (_jspx_eval_logic_005fiterate_005f10 != 1) {
/* 6052 */                                         out = _jspx_page_context.pushBody();
/* 6053 */                                         _jspx_th_logic_005fiterate_005f10.setBodyContent((BodyContent)out);
/* 6054 */                                         _jspx_th_logic_005fiterate_005f10.doInitBody();
/*      */                                       }
/* 6056 */                                       colnames = (String)_jspx_page_context.findAttribute("colnames");
/* 6057 */                                       k = (Integer)_jspx_page_context.findAttribute("k");
/*      */                                       for (;;) {
/* 6059 */                                         out.write(10);
/* 6060 */                                         out.write(9);
/*      */                                         
/* 6062 */                                         if ((attid_details.get(colnames) != null) && (((ArrayList)attid_details.get(colnames)).get(1).equals("0")))
/*      */                                         {
/*      */ 
/* 6065 */                                           out.write("\n    \n    <option value=\"");
/* 6066 */                                           out.print(colnames);
/* 6067 */                                           out.write(34);
/* 6068 */                                           out.write(62);
/* 6069 */                                           out.print(FormatUtil.getString((String)((ArrayList)attid_details.get(colnames)).get(0)));
/* 6070 */                                           out.write("</option>\n");
/*      */                                         }
/*      */                                         
/*      */ 
/* 6074 */                                         out.write("\n    ");
/* 6075 */                                         int evalDoAfterBody = _jspx_th_logic_005fiterate_005f10.doAfterBody();
/* 6076 */                                         colnames = (String)_jspx_page_context.findAttribute("colnames");
/* 6077 */                                         k = (Integer)_jspx_page_context.findAttribute("k");
/* 6078 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 6081 */                                       if (_jspx_eval_logic_005fiterate_005f10 != 1) {
/* 6082 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 6085 */                                     if (_jspx_th_logic_005fiterate_005f10.doEndTag() == 5) {
/* 6086 */                                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f10); return;
/*      */                                     }
/*      */                                     
/* 6089 */                                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f10);
/* 6090 */                                     out.write("\n</select>\n");
/*      */                                   }
/*      */                                   
/*      */ 
/* 6094 */                                   out.write(32);
/* 6095 */                                   out.write(10);
/*      */                                 }
/*      */                                 
/*      */ 
/* 6099 */                                 out.write("\n</span> </td> \n    <td colspan=");
/* 6100 */                                 out.print(col_count - 1);
/* 6101 */                                 out.write(" width=\"10%\" height=\"31\" align=\"right\" class=\"tablebottom\"><a href=\"#top\" class=\"staticlinks\" style=\"margin-right:10px;\">");
/* 6102 */                                 out.print(FormatUtil.getString("am.webclient.common.top.text"));
/* 6103 */                                 out.write("</a></td>\n  </tr>\n</table>\n<br>\n");
/*      */                                 
/* 6105 */                                 if ((rowids_asString != null) && (rowids_asString.length() > 0))
/*      */                                 {
/* 6107 */                                   rowids_asString = rowids_asString.substring(0, rowids_asString.length() - 1);
/*      */                                 }
/*      */                                 
/* 6110 */                                 out.write("\n</form>\n<br>\n");
/* 6111 */                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f6.doAfterBody();
/* 6112 */                                 attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 6113 */                                 j = (Integer)_jspx_page_context.findAttribute("j");
/* 6114 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 6117 */                               if (_jspx_eval_logic_005fiterate_005f6 != 1) {
/* 6118 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 6121 */                             if (_jspx_th_logic_005fiterate_005f6.doEndTag() == 5) {
/* 6122 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f6); return;
/*      */                             }
/*      */                             
/* 6125 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f6);
/* 6126 */                             out.write("\n</td>\n</tr>\n\n</table>\n");
/* 6127 */                             out.write(10);
/* 6128 */                             out.write(10);
/* 6129 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 6130 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 6133 */                           if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 6134 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 6137 */                         if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 6138 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */                         }
/*      */                         
/* 6141 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 6142 */                         out.write(10);
/* 6143 */                         if (_jspx_meth_tiles_005fput_005f5(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 6145 */                         out.write(32);
/* 6146 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 6147 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 6151 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 6152 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 6155 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 6156 */                       out.write(10);
/*      */                     }
/* 6158 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 6159 */         out = _jspx_out;
/* 6160 */         if ((out != null) && (out.getBufferSize() != 0))
/* 6161 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 6162 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 6165 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6171 */     PageContext pageContext = _jspx_page_context;
/* 6172 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6174 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6175 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 6176 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 6178 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 6179 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 6180 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 6181 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 6182 */       return true;
/*      */     }
/* 6184 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 6185 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6190 */     PageContext pageContext = _jspx_page_context;
/* 6191 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6193 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6194 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 6195 */     _jspx_th_c_005fout_005f1.setParent(null);
/*      */     
/* 6197 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 6198 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 6199 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 6200 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6201 */       return true;
/*      */     }
/* 6203 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6204 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6209 */     PageContext pageContext = _jspx_page_context;
/* 6210 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6212 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6213 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 6214 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6216 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 6217 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 6218 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 6220 */         out.write(10);
/* 6221 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 6222 */           return true;
/* 6223 */         out.write(10);
/* 6224 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 6225 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6229 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 6230 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 6231 */       return true;
/*      */     }
/* 6233 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 6234 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6239 */     PageContext pageContext = _jspx_page_context;
/* 6240 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6242 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6243 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 6244 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 6246 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 6248 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 6249 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 6250 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 6251 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 6252 */       return true;
/*      */     }
/* 6254 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 6255 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6260 */     PageContext pageContext = _jspx_page_context;
/* 6261 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6263 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6264 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 6265 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6267 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 6268 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 6269 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 6271 */         out.write(10);
/* 6272 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 6273 */           return true;
/* 6274 */         out.write(10);
/* 6275 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 6276 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6280 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 6281 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 6282 */       return true;
/*      */     }
/* 6284 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 6285 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6290 */     PageContext pageContext = _jspx_page_context;
/* 6291 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6293 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6294 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 6295 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 6297 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 6299 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 6300 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 6301 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 6302 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 6303 */       return true;
/*      */     }
/* 6305 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 6306 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6311 */     PageContext pageContext = _jspx_page_context;
/* 6312 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6314 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6315 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 6316 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6318 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 6319 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 6320 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 6321 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6322 */       return true;
/*      */     }
/* 6324 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6325 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6330 */     PageContext pageContext = _jspx_page_context;
/* 6331 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6333 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6334 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 6335 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6337 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 6338 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 6339 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 6340 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6341 */       return true;
/*      */     }
/* 6343 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6344 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6349 */     PageContext pageContext = _jspx_page_context;
/* 6350 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6352 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6353 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 6354 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6356 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 6357 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 6358 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 6359 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6360 */       return true;
/*      */     }
/* 6362 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6363 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6368 */     PageContext pageContext = _jspx_page_context;
/* 6369 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6371 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6372 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 6373 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6375 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 6376 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 6377 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 6378 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6379 */       return true;
/*      */     }
/* 6381 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6382 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6387 */     PageContext pageContext = _jspx_page_context;
/* 6388 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6390 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6391 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 6392 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6394 */     _jspx_th_c_005fif_005f2.setTest("${!empty param.parentid}");
/* 6395 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 6396 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 6398 */         out.write("\n      ");
/* 6399 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 6400 */           return true;
/* 6401 */         out.write("\n      ");
/* 6402 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 6403 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6407 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 6408 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 6409 */       return true;
/*      */     }
/* 6411 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 6412 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6417 */     PageContext pageContext = _jspx_page_context;
/* 6418 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6420 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6421 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 6422 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6424 */     _jspx_th_c_005fset_005f0.setVar("parentids");
/* 6425 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 6426 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 6427 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 6428 */         out = _jspx_page_context.pushBody();
/* 6429 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 6430 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6433 */         out.write("\n      &parentname=");
/* 6434 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 6435 */           return true;
/* 6436 */         out.write("&parentid=");
/* 6437 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 6438 */           return true;
/* 6439 */         out.write("\n      ");
/* 6440 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 6441 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6444 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 6445 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6448 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 6449 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 6450 */       return true;
/*      */     }
/* 6452 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 6453 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6458 */     PageContext pageContext = _jspx_page_context;
/* 6459 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6461 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6462 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 6463 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 6465 */     _jspx_th_c_005fout_005f6.setValue("${param.parentname}");
/* 6466 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 6467 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 6468 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6469 */       return true;
/*      */     }
/* 6471 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6472 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6477 */     PageContext pageContext = _jspx_page_context;
/* 6478 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6480 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6481 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 6482 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 6484 */     _jspx_th_c_005fout_005f7.setValue("${param.parentid}");
/* 6485 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 6486 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 6487 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 6488 */       return true;
/*      */     }
/* 6490 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 6491 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6496 */     PageContext pageContext = _jspx_page_context;
/* 6497 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6499 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6500 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 6501 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6503 */     _jspx_th_c_005fif_005f3.setTest("${empty param.parentid}");
/* 6504 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 6505 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 6507 */         out.write("\n            ");
/* 6508 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 6509 */           return true;
/* 6510 */         out.write("\n      ");
/* 6511 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 6512 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6516 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 6517 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 6518 */       return true;
/*      */     }
/* 6520 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 6521 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6526 */     PageContext pageContext = _jspx_page_context;
/* 6527 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6529 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 6530 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 6531 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 6533 */     _jspx_th_c_005fset_005f1.setVar("parentids");
/*      */     
/* 6535 */     _jspx_th_c_005fset_005f1.setValue("");
/* 6536 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 6537 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 6538 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 6539 */       return true;
/*      */     }
/* 6541 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 6542 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6547 */     PageContext pageContext = _jspx_page_context;
/* 6548 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6550 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6551 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 6552 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6554 */     _jspx_th_c_005fif_005f4.setTest("${!empty param.haid}");
/* 6555 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 6556 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 6558 */         out.write(10);
/* 6559 */         out.write(9);
/* 6560 */         out.write(9);
/* 6561 */         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 6562 */           return true;
/* 6563 */         out.write(10);
/* 6564 */         out.write(9);
/* 6565 */         out.write(9);
/* 6566 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 6567 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6571 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 6572 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 6573 */       return true;
/*      */     }
/* 6575 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 6576 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6581 */     PageContext pageContext = _jspx_page_context;
/* 6582 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6584 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6585 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 6586 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6588 */     _jspx_th_c_005fset_005f2.setVar("haid");
/* 6589 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 6590 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 6591 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 6592 */         out = _jspx_page_context.pushBody();
/* 6593 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 6594 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6597 */         out.write("\n\t\t&haid=");
/* 6598 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fset_005f2, _jspx_page_context))
/* 6599 */           return true;
/* 6600 */         out.write(10);
/* 6601 */         out.write(9);
/* 6602 */         out.write(9);
/* 6603 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 6604 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6607 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 6608 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6611 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 6612 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 6613 */       return true;
/*      */     }
/* 6615 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 6616 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6621 */     PageContext pageContext = _jspx_page_context;
/* 6622 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6624 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6625 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 6626 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 6628 */     _jspx_th_c_005fout_005f8.setValue("${param.haid}");
/* 6629 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 6630 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 6631 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6632 */       return true;
/*      */     }
/* 6634 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6635 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6640 */     PageContext pageContext = _jspx_page_context;
/* 6641 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6643 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6644 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 6645 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6647 */     _jspx_th_c_005fout_005f9.setValue("${param.resourceid}");
/* 6648 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 6649 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 6650 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6651 */       return true;
/*      */     }
/* 6653 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6654 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6659 */     PageContext pageContext = _jspx_page_context;
/* 6660 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6662 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6663 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 6664 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6666 */     _jspx_th_c_005fif_005f7.setTest("${param.method=='editScript'}");
/* 6667 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 6668 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 6670 */         out.write("\n    </a>\n    ");
/* 6671 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 6672 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6676 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 6677 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 6678 */       return true;
/*      */     }
/* 6680 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 6681 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6686 */     PageContext pageContext = _jspx_page_context;
/* 6687 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6689 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6690 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 6691 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 6693 */     _jspx_th_c_005fout_005f10.setValue("${param.resourceid}");
/* 6694 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 6695 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 6696 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6697 */       return true;
/*      */     }
/* 6699 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6700 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6705 */     PageContext pageContext = _jspx_page_context;
/* 6706 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6708 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6709 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 6710 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 6712 */     _jspx_th_c_005fout_005f11.setValue("${param.resourceid}");
/* 6713 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 6714 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 6715 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6716 */       return true;
/*      */     }
/* 6718 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6719 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_logic_005fnotPresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6724 */     PageContext pageContext = _jspx_page_context;
/* 6725 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6727 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6728 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 6729 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_logic_005fnotPresent_005f1);
/*      */     
/* 6731 */     _jspx_th_c_005fout_005f12.setValue("${param.resourceid}");
/* 6732 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 6733 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 6734 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6735 */       return true;
/*      */     }
/* 6737 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6738 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6743 */     PageContext pageContext = _jspx_page_context;
/* 6744 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6746 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6747 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 6748 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 6750 */     _jspx_th_c_005fout_005f13.setValue("${param.haid}");
/* 6751 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 6752 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 6753 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6754 */       return true;
/*      */     }
/* 6756 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6757 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6762 */     PageContext pageContext = _jspx_page_context;
/* 6763 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6765 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6766 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 6767 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 6769 */     _jspx_th_c_005fout_005f14.setValue("${param.resourceid}");
/* 6770 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 6771 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 6772 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6773 */       return true;
/*      */     }
/* 6775 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6776 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6781 */     PageContext pageContext = _jspx_page_context;
/* 6782 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6784 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6785 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 6786 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 6788 */     _jspx_th_c_005fout_005f15.setValue("${parentids}");
/* 6789 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 6790 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 6791 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6792 */       return true;
/*      */     }
/* 6794 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6795 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6800 */     PageContext pageContext = _jspx_page_context;
/* 6801 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6803 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6804 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 6805 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 6807 */     _jspx_th_c_005fout_005f16.setValue("${param.haid}");
/* 6808 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 6809 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 6810 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6811 */       return true;
/*      */     }
/* 6813 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6814 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6819 */     PageContext pageContext = _jspx_page_context;
/* 6820 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6822 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6823 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 6824 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 6826 */     _jspx_th_c_005fout_005f17.setValue("${param.resourceid}");
/* 6827 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 6828 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 6829 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6830 */       return true;
/*      */     }
/* 6832 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6833 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6838 */     PageContext pageContext = _jspx_page_context;
/* 6839 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6841 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6842 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 6843 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 6845 */     _jspx_th_c_005fout_005f18.setValue("${parentids}");
/* 6846 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 6847 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 6848 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6849 */       return true;
/*      */     }
/* 6851 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6852 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6857 */     PageContext pageContext = _jspx_page_context;
/* 6858 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6860 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6861 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 6862 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 6864 */     _jspx_th_c_005fout_005f19.setValue("${param.resourceid}");
/* 6865 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 6866 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 6867 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6868 */       return true;
/*      */     }
/* 6870 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6871 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6876 */     PageContext pageContext = _jspx_page_context;
/* 6877 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6879 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6880 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 6881 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 6883 */     _jspx_th_c_005fout_005f20.setValue("${parentids}");
/* 6884 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 6885 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 6886 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6887 */       return true;
/*      */     }
/* 6889 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6890 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6895 */     PageContext pageContext = _jspx_page_context;
/* 6896 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6898 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6899 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 6900 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 6902 */     _jspx_th_c_005fout_005f21.setValue("${param.haid}");
/* 6903 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 6904 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 6905 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6906 */       return true;
/*      */     }
/* 6908 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6909 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6914 */     PageContext pageContext = _jspx_page_context;
/* 6915 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6917 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6918 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 6919 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 6921 */     _jspx_th_c_005fout_005f22.setValue("${param.resourceid}");
/* 6922 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 6923 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 6924 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 6925 */       return true;
/*      */     }
/* 6927 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 6928 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6933 */     PageContext pageContext = _jspx_page_context;
/* 6934 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6936 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6937 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 6938 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 6940 */     _jspx_th_c_005fout_005f23.setValue("${parentids}");
/* 6941 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 6942 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 6943 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 6944 */       return true;
/*      */     }
/* 6946 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 6947 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6952 */     PageContext pageContext = _jspx_page_context;
/* 6953 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6955 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6956 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 6957 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 6959 */     _jspx_th_c_005fout_005f24.setValue("${param.resourceid}");
/* 6960 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 6961 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 6962 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 6963 */       return true;
/*      */     }
/* 6965 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 6966 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6971 */     PageContext pageContext = _jspx_page_context;
/* 6972 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6974 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6975 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 6976 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 6978 */     _jspx_th_c_005fout_005f25.setValue("${parentids}");
/* 6979 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 6980 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 6981 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 6982 */       return true;
/*      */     }
/* 6984 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 6985 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f18(JspTag _jspx_th_logic_005fnotPresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6990 */     PageContext pageContext = _jspx_page_context;
/* 6991 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6993 */     IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6994 */     _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 6995 */     _jspx_th_c_005fif_005f18.setParent((Tag)_jspx_th_logic_005fnotPresent_005f4);
/*      */     
/* 6997 */     _jspx_th_c_005fif_005f18.setTest("${param.actionmethod!='editScript'}");
/* 6998 */     int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 6999 */     if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */       for (;;) {
/* 7001 */         out.write("\n    </a>\n    ");
/* 7002 */         int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 7003 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7007 */     if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 7008 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 7009 */       return true;
/*      */     }
/* 7011 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 7012 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7017 */     PageContext pageContext = _jspx_page_context;
/* 7018 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7020 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7021 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 7022 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 7024 */     _jspx_th_c_005fout_005f26.setValue("${param.resourceid}");
/* 7025 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 7026 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 7027 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 7028 */       return true;
/*      */     }
/* 7030 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 7031 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7036 */     PageContext pageContext = _jspx_page_context;
/* 7037 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7039 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7040 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 7041 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 7043 */     _jspx_th_c_005fout_005f27.setValue("${param.resourceid}");
/* 7044 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 7045 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 7046 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 7047 */       return true;
/*      */     }
/* 7049 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 7050 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7055 */     PageContext pageContext = _jspx_page_context;
/* 7056 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7058 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7059 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 7060 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 7062 */     _jspx_th_c_005fout_005f28.setValue("${param.resourceid}");
/* 7063 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 7064 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 7065 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 7066 */       return true;
/*      */     }
/* 7068 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 7069 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7074 */     PageContext pageContext = _jspx_page_context;
/* 7075 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7077 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7078 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 7079 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 7081 */     _jspx_th_c_005fout_005f29.setValue("${param.resourceid}");
/* 7082 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 7083 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 7084 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 7085 */       return true;
/*      */     }
/* 7087 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 7088 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7093 */     PageContext pageContext = _jspx_page_context;
/* 7094 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7096 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7097 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 7098 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 7100 */     _jspx_th_c_005fout_005f30.setValue("${ha.key}");
/* 7101 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 7102 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 7103 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 7104 */       return true;
/*      */     }
/* 7106 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 7107 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7112 */     PageContext pageContext = _jspx_page_context;
/* 7113 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7115 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7116 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 7117 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 7119 */     _jspx_th_c_005fout_005f31.setValue("${ha.value}");
/* 7120 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 7121 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 7122 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 7123 */       return true;
/*      */     }
/* 7125 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 7126 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7131 */     PageContext pageContext = _jspx_page_context;
/* 7132 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7134 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 7135 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 7136 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 7138 */     _jspx_th_c_005fset_005f3.setVar("monitorName");
/* 7139 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 7140 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 7141 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 7142 */         out = _jspx_page_context.pushBody();
/* 7143 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 7144 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 7145 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7148 */         if (_jspx_meth_c_005fout_005f32(_jspx_th_c_005fset_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 7149 */           return true;
/* 7150 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 7151 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7154 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 7155 */         out = _jspx_page_context.popBody();
/* 7156 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 7159 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 7160 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 7161 */       return true;
/*      */     }
/* 7163 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 7164 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7169 */     PageContext pageContext = _jspx_page_context;
/* 7170 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7172 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7173 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 7174 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 7176 */     _jspx_th_c_005fout_005f32.setValue("${ha.value}");
/* 7177 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 7178 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 7179 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 7180 */       return true;
/*      */     }
/* 7182 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 7183 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_logic_005fpresent_005f10, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7188 */     PageContext pageContext = _jspx_page_context;
/* 7189 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7191 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7192 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 7193 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_logic_005fpresent_005f10);
/*      */     
/* 7195 */     _jspx_th_c_005fout_005f33.setValue("${ha.key}");
/* 7196 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 7197 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 7198 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 7199 */       return true;
/*      */     }
/* 7201 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 7202 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f23, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7207 */     PageContext pageContext = _jspx_page_context;
/* 7208 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7210 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7211 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 7212 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f23);
/*      */     
/* 7214 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 7215 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 7216 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 7217 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 7218 */       return true;
/*      */     }
/* 7220 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 7221 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7226 */     PageContext pageContext = _jspx_page_context;
/* 7227 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7229 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7230 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 7231 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 7233 */     _jspx_th_c_005fout_005f34.setValue("${ha.key}");
/* 7234 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 7235 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 7236 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 7237 */       return true;
/*      */     }
/* 7239 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 7240 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7245 */     PageContext pageContext = _jspx_page_context;
/* 7246 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7248 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7249 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 7250 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 7252 */     _jspx_th_c_005fout_005f35.setValue("${ha.value}");
/* 7253 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 7254 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 7255 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 7256 */       return true;
/*      */     }
/* 7258 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 7259 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7264 */     PageContext pageContext = _jspx_page_context;
/* 7265 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7267 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 7268 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 7269 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 7271 */     _jspx_th_c_005fset_005f4.setVar("monitorName");
/* 7272 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 7273 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 7274 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 7275 */         out = _jspx_page_context.pushBody();
/* 7276 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 7277 */         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 7278 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7281 */         if (_jspx_meth_c_005fout_005f36(_jspx_th_c_005fset_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7282 */           return true;
/* 7283 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 7284 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7287 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 7288 */         out = _jspx_page_context.popBody();
/* 7289 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 7292 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 7293 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 7294 */       return true;
/*      */     }
/* 7296 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 7297 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_c_005fset_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7302 */     PageContext pageContext = _jspx_page_context;
/* 7303 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7305 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7306 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 7307 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_c_005fset_005f4);
/*      */     
/* 7309 */     _jspx_th_c_005fout_005f36.setValue("${ha.value}");
/* 7310 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 7311 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 7312 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 7313 */       return true;
/*      */     }
/* 7315 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 7316 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(JspTag _jspx_th_logic_005fpresent_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7321 */     PageContext pageContext = _jspx_page_context;
/* 7322 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7324 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7325 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 7326 */     _jspx_th_c_005fout_005f37.setParent((Tag)_jspx_th_logic_005fpresent_005f11);
/*      */     
/* 7328 */     _jspx_th_c_005fout_005f37.setValue("${ha.key}");
/* 7329 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 7330 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 7331 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 7332 */       return true;
/*      */     }
/* 7334 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 7335 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fpresent_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7340 */     PageContext pageContext = _jspx_page_context;
/* 7341 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7343 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7344 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 7345 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f11);
/*      */     
/* 7347 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.quickremoval.monitorgroup.txt");
/* 7348 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 7349 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 7350 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 7351 */       return true;
/*      */     }
/* 7353 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 7354 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f24(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7359 */     PageContext pageContext = _jspx_page_context;
/* 7360 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7362 */     IfTag _jspx_th_c_005fif_005f24 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7363 */     _jspx_th_c_005fif_005f24.setPageContext(_jspx_page_context);
/* 7364 */     _jspx_th_c_005fif_005f24.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 7366 */     _jspx_th_c_005fif_005f24.setTest("${empty associatedmgs}");
/* 7367 */     int _jspx_eval_c_005fif_005f24 = _jspx_th_c_005fif_005f24.doStartTag();
/* 7368 */     if (_jspx_eval_c_005fif_005f24 != 0) {
/*      */       for (;;) {
/* 7370 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 7371 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f24, _jspx_page_context))
/* 7372 */           return true;
/* 7373 */         out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">");
/* 7374 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f24, _jspx_page_context))
/* 7375 */           return true;
/* 7376 */         out.write("</td>\n\t ");
/* 7377 */         int evalDoAfterBody = _jspx_th_c_005fif_005f24.doAfterBody();
/* 7378 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7382 */     if (_jspx_th_c_005fif_005f24.doEndTag() == 5) {
/* 7383 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 7384 */       return true;
/*      */     }
/* 7386 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 7387 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f24, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7392 */     PageContext pageContext = _jspx_page_context;
/* 7393 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7395 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7396 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 7397 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f24);
/*      */     
/* 7399 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 7400 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 7401 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 7402 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 7403 */       return true;
/*      */     }
/* 7405 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 7406 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f24, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7411 */     PageContext pageContext = _jspx_page_context;
/* 7412 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7414 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7415 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 7416 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f24);
/*      */     
/* 7418 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.urlmonitor.none.text");
/* 7419 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 7420 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 7421 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 7422 */       return true;
/*      */     }
/* 7424 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 7425 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7430 */     PageContext pageContext = _jspx_page_context;
/* 7431 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7433 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7434 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 7435 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 7437 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 7438 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 7439 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 7440 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 7441 */       return true;
/*      */     }
/* 7443 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 7444 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7449 */     PageContext pageContext = _jspx_page_context;
/* 7450 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7452 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7453 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 7454 */     _jspx_th_c_005fout_005f38.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 7456 */     _jspx_th_c_005fout_005f38.setValue("${hostname}");
/* 7457 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 7458 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 7459 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 7460 */       return true;
/*      */     }
/* 7462 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 7463 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7468 */     PageContext pageContext = _jspx_page_context;
/* 7469 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7471 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7472 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 7473 */     _jspx_th_c_005fout_005f39.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7475 */     _jspx_th_c_005fout_005f39.setValue("${param.resourceid}");
/* 7476 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 7477 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 7478 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 7479 */       return true;
/*      */     }
/* 7481 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 7482 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f40(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7487 */     PageContext pageContext = _jspx_page_context;
/* 7488 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7490 */     OutTag _jspx_th_c_005fout_005f40 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7491 */     _jspx_th_c_005fout_005f40.setPageContext(_jspx_page_context);
/* 7492 */     _jspx_th_c_005fout_005f40.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7494 */     _jspx_th_c_005fout_005f40.setValue("${param.resourcename}");
/* 7495 */     int _jspx_eval_c_005fout_005f40 = _jspx_th_c_005fout_005f40.doStartTag();
/* 7496 */     if (_jspx_th_c_005fout_005f40.doEndTag() == 5) {
/* 7497 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 7498 */       return true;
/*      */     }
/* 7500 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 7501 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f41(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7506 */     PageContext pageContext = _jspx_page_context;
/* 7507 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7509 */     OutTag _jspx_th_c_005fout_005f41 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7510 */     _jspx_th_c_005fout_005f41.setPageContext(_jspx_page_context);
/* 7511 */     _jspx_th_c_005fout_005f41.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7513 */     _jspx_th_c_005fout_005f41.setValue("${param.resourceid}");
/* 7514 */     int _jspx_eval_c_005fout_005f41 = _jspx_th_c_005fout_005f41.doStartTag();
/* 7515 */     if (_jspx_th_c_005fout_005f41.doEndTag() == 5) {
/* 7516 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 7517 */       return true;
/*      */     }
/* 7519 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 7520 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f42(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7525 */     PageContext pageContext = _jspx_page_context;
/* 7526 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7528 */     OutTag _jspx_th_c_005fout_005f42 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7529 */     _jspx_th_c_005fout_005f42.setPageContext(_jspx_page_context);
/* 7530 */     _jspx_th_c_005fout_005f42.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7532 */     _jspx_th_c_005fout_005f42.setValue("${param.resourcename}");
/* 7533 */     int _jspx_eval_c_005fout_005f42 = _jspx_th_c_005fout_005f42.doStartTag();
/* 7534 */     if (_jspx_th_c_005fout_005f42.doEndTag() == 5) {
/* 7535 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 7536 */       return true;
/*      */     }
/* 7538 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 7539 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fpiechart_005f0(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7544 */     PageContext pageContext = _jspx_page_context;
/* 7545 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7547 */     AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 7548 */     _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 7549 */     _jspx_th_awolf_005fpiechart_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7551 */     _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */     
/* 7553 */     _jspx_th_awolf_005fpiechart_005f0.setWidth("300");
/*      */     
/* 7555 */     _jspx_th_awolf_005fpiechart_005f0.setHeight("180");
/*      */     
/* 7557 */     _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */     
/* 7559 */     _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */     
/* 7561 */     _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */     
/* 7563 */     _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 7564 */     int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 7565 */     if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 7566 */       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 7567 */         out = _jspx_page_context.pushBody();
/* 7568 */         _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 7569 */         _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7572 */         out.write("\n            ");
/* 7573 */         if (_jspx_meth_awolf_005fmap_005f0(_jspx_th_awolf_005fpiechart_005f0, _jspx_page_context))
/* 7574 */           return true;
/* 7575 */         out.write(32);
/* 7576 */         int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 7577 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7580 */       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 7581 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7584 */     if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 7585 */       this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 7586 */       return true;
/*      */     }
/* 7588 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 7589 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f0(JspTag _jspx_th_awolf_005fpiechart_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7594 */     PageContext pageContext = _jspx_page_context;
/* 7595 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7597 */     Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 7598 */     _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 7599 */     _jspx_th_awolf_005fmap_005f0.setParent((Tag)_jspx_th_awolf_005fpiechart_005f0);
/*      */     
/* 7601 */     _jspx_th_awolf_005fmap_005f0.setId("color");
/* 7602 */     int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 7603 */     if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 7604 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 7605 */         out = _jspx_page_context.pushBody();
/* 7606 */         _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 7607 */         _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7610 */         out.write(32);
/* 7611 */         if (_jspx_meth_awolf_005fparam_005f0(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 7612 */           return true;
/* 7613 */         out.write(32);
/* 7614 */         if (_jspx_meth_awolf_005fparam_005f1(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 7615 */           return true;
/* 7616 */         out.write("\n            ");
/* 7617 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 7618 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7621 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 7622 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7625 */     if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 7626 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 7627 */       return true;
/*      */     }
/* 7629 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 7630 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f0(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7635 */     PageContext pageContext = _jspx_page_context;
/* 7636 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7638 */     AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 7639 */     _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 7640 */     _jspx_th_awolf_005fparam_005f0.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 7642 */     _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */     
/* 7644 */     _jspx_th_awolf_005fparam_005f0.setValue("#29FF29");
/* 7645 */     int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 7646 */     if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 7647 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 7648 */       return true;
/*      */     }
/* 7650 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 7651 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f1(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7656 */     PageContext pageContext = _jspx_page_context;
/* 7657 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7659 */     AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 7660 */     _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 7661 */     _jspx_th_awolf_005fparam_005f1.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 7663 */     _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */     
/* 7665 */     _jspx_th_awolf_005fparam_005f1.setValue("#FF0000");
/* 7666 */     int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 7667 */     if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 7668 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 7669 */       return true;
/*      */     }
/* 7671 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 7672 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7677 */     PageContext pageContext = _jspx_page_context;
/* 7678 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7680 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7681 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 7682 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7683 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 7684 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 7685 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 7686 */         out = _jspx_page_context.pushBody();
/* 7687 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 7688 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7691 */         out.write("Attribute");
/* 7692 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 7693 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7696 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 7697 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7700 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 7701 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 7702 */       return true;
/*      */     }
/* 7704 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 7705 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7710 */     PageContext pageContext = _jspx_page_context;
/* 7711 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7713 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7714 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 7715 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7716 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 7717 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 7718 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 7719 */         out = _jspx_page_context.pushBody();
/* 7720 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 7721 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7724 */         out.write("Status");
/* 7725 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 7726 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7729 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 7730 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7733 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 7734 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 7735 */       return true;
/*      */     }
/* 7737 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 7738 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f27(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7743 */     PageContext pageContext = _jspx_page_context;
/* 7744 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7746 */     IfTag _jspx_th_c_005fif_005f27 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7747 */     _jspx_th_c_005fif_005f27.setPageContext(_jspx_page_context);
/* 7748 */     _jspx_th_c_005fif_005f27.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7750 */     _jspx_th_c_005fif_005f27.setTest("${!empty param.reports}");
/* 7751 */     int _jspx_eval_c_005fif_005f27 = _jspx_th_c_005fif_005f27.doStartTag();
/* 7752 */     if (_jspx_eval_c_005fif_005f27 != 0) {
/*      */       for (;;) {
/* 7754 */         out.write("\n<script>\njavascript:showDiv('reports');\n</script>\n");
/* 7755 */         int evalDoAfterBody = _jspx_th_c_005fif_005f27.doAfterBody();
/* 7756 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7760 */     if (_jspx_th_c_005fif_005f27.doEndTag() == 5) {
/* 7761 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 7762 */       return true;
/*      */     }
/* 7764 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 7765 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7770 */     PageContext pageContext = _jspx_page_context;
/* 7771 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7773 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7774 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 7775 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7776 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 7777 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 7778 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 7779 */         out = _jspx_page_context.pushBody();
/* 7780 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 7781 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7784 */         out.write("table.heading.attribute");
/* 7785 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 7786 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7789 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 7790 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7793 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 7794 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 7795 */       return true;
/*      */     }
/* 7797 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 7798 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7803 */     PageContext pageContext = _jspx_page_context;
/* 7804 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7806 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7807 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 7808 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7809 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 7810 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 7811 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 7812 */         out = _jspx_page_context.pushBody();
/* 7813 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 7814 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7817 */         out.write("table.heading.value");
/* 7818 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 7819 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7822 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 7823 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7826 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 7827 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 7828 */       return true;
/*      */     }
/* 7830 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 7831 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7836 */     PageContext pageContext = _jspx_page_context;
/* 7837 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7839 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7840 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 7841 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7842 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 7843 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 7844 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 7845 */         out = _jspx_page_context.pushBody();
/* 7846 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 7847 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7850 */         out.write("table.heading.status");
/* 7851 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 7852 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7855 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 7856 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7859 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 7860 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 7861 */       return true;
/*      */     }
/* 7863 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 7864 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7869 */     PageContext pageContext = _jspx_page_context;
/* 7870 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7872 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7873 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 7874 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7875 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 7876 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 7877 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 7878 */         out = _jspx_page_context.pushBody();
/* 7879 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 7880 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7883 */         out.write("Name");
/* 7884 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 7885 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7888 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 7889 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7892 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 7893 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 7894 */       return true;
/*      */     }
/* 7896 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 7897 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7902 */     PageContext pageContext = _jspx_page_context;
/* 7903 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7905 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7906 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 7907 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7908 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 7909 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 7910 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 7911 */         out = _jspx_page_context.pushBody();
/* 7912 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 7913 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7916 */         out.write("table.heading.value");
/* 7917 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 7918 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7921 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 7922 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7925 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 7926 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 7927 */       return true;
/*      */     }
/* 7929 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 7930 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7935 */     PageContext pageContext = _jspx_page_context;
/* 7936 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7938 */     MessageTag _jspx_th_fmt_005fmessage_005f12 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7939 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 7940 */     _jspx_th_fmt_005fmessage_005f12.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7941 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 7942 */     if (_jspx_eval_fmt_005fmessage_005f12 != 0) {
/* 7943 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 7944 */         out = _jspx_page_context.pushBody();
/* 7945 */         _jspx_th_fmt_005fmessage_005f12.setBodyContent((BodyContent)out);
/* 7946 */         _jspx_th_fmt_005fmessage_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7949 */         out.write("table.heading.status");
/* 7950 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f12.doAfterBody();
/* 7951 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7954 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 7955 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7958 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 7959 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 7960 */       return true;
/*      */     }
/* 7962 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 7963 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f13(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7968 */     PageContext pageContext = _jspx_page_context;
/* 7969 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7971 */     MessageTag _jspx_th_fmt_005fmessage_005f13 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7972 */     _jspx_th_fmt_005fmessage_005f13.setPageContext(_jspx_page_context);
/* 7973 */     _jspx_th_fmt_005fmessage_005f13.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7974 */     int _jspx_eval_fmt_005fmessage_005f13 = _jspx_th_fmt_005fmessage_005f13.doStartTag();
/* 7975 */     if (_jspx_eval_fmt_005fmessage_005f13 != 0) {
/* 7976 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 7977 */         out = _jspx_page_context.pushBody();
/* 7978 */         _jspx_th_fmt_005fmessage_005f13.setBodyContent((BodyContent)out);
/* 7979 */         _jspx_th_fmt_005fmessage_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7982 */         out.write("Name");
/* 7983 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f13.doAfterBody();
/* 7984 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7987 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 7988 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7991 */     if (_jspx_th_fmt_005fmessage_005f13.doEndTag() == 5) {
/* 7992 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 7993 */       return true;
/*      */     }
/* 7995 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 7996 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f14(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8001 */     PageContext pageContext = _jspx_page_context;
/* 8002 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8004 */     MessageTag _jspx_th_fmt_005fmessage_005f14 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 8005 */     _jspx_th_fmt_005fmessage_005f14.setPageContext(_jspx_page_context);
/* 8006 */     _jspx_th_fmt_005fmessage_005f14.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 8007 */     int _jspx_eval_fmt_005fmessage_005f14 = _jspx_th_fmt_005fmessage_005f14.doStartTag();
/* 8008 */     if (_jspx_eval_fmt_005fmessage_005f14 != 0) {
/* 8009 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 8010 */         out = _jspx_page_context.pushBody();
/* 8011 */         _jspx_th_fmt_005fmessage_005f14.setBodyContent((BodyContent)out);
/* 8012 */         _jspx_th_fmt_005fmessage_005f14.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8015 */         out.write("table.heading.value");
/* 8016 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f14.doAfterBody();
/* 8017 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8020 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 8021 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8024 */     if (_jspx_th_fmt_005fmessage_005f14.doEndTag() == 5) {
/* 8025 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 8026 */       return true;
/*      */     }
/* 8028 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 8029 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f15(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8034 */     PageContext pageContext = _jspx_page_context;
/* 8035 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8037 */     MessageTag _jspx_th_fmt_005fmessage_005f15 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 8038 */     _jspx_th_fmt_005fmessage_005f15.setPageContext(_jspx_page_context);
/* 8039 */     _jspx_th_fmt_005fmessage_005f15.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 8040 */     int _jspx_eval_fmt_005fmessage_005f15 = _jspx_th_fmt_005fmessage_005f15.doStartTag();
/* 8041 */     if (_jspx_eval_fmt_005fmessage_005f15 != 0) {
/* 8042 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 8043 */         out = _jspx_page_context.pushBody();
/* 8044 */         _jspx_th_fmt_005fmessage_005f15.setBodyContent((BodyContent)out);
/* 8045 */         _jspx_th_fmt_005fmessage_005f15.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8048 */         out.write("table.heading.status");
/* 8049 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f15.doAfterBody();
/* 8050 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8053 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 8054 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8057 */     if (_jspx_th_fmt_005fmessage_005f15.doEndTag() == 5) {
/* 8058 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 8059 */       return true;
/*      */     }
/* 8061 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 8062 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f16(JspTag _jspx_th_logic_005fiterate_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8067 */     PageContext pageContext = _jspx_page_context;
/* 8068 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8070 */     MessageTag _jspx_th_fmt_005fmessage_005f16 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 8071 */     _jspx_th_fmt_005fmessage_005f16.setPageContext(_jspx_page_context);
/* 8072 */     _jspx_th_fmt_005fmessage_005f16.setParent((Tag)_jspx_th_logic_005fiterate_005f5);
/* 8073 */     int _jspx_eval_fmt_005fmessage_005f16 = _jspx_th_fmt_005fmessage_005f16.doStartTag();
/* 8074 */     if (_jspx_eval_fmt_005fmessage_005f16 != 0) {
/* 8075 */       if (_jspx_eval_fmt_005fmessage_005f16 != 1) {
/* 8076 */         out = _jspx_page_context.pushBody();
/* 8077 */         _jspx_th_fmt_005fmessage_005f16.setBodyContent((BodyContent)out);
/* 8078 */         _jspx_th_fmt_005fmessage_005f16.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8081 */         out.write("table.heading.value");
/* 8082 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f16.doAfterBody();
/* 8083 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8086 */       if (_jspx_eval_fmt_005fmessage_005f16 != 1) {
/* 8087 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8090 */     if (_jspx_th_fmt_005fmessage_005f16.doEndTag() == 5) {
/* 8091 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 8092 */       return true;
/*      */     }
/* 8094 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 8095 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f17(JspTag _jspx_th_logic_005fiterate_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8100 */     PageContext pageContext = _jspx_page_context;
/* 8101 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8103 */     MessageTag _jspx_th_fmt_005fmessage_005f17 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 8104 */     _jspx_th_fmt_005fmessage_005f17.setPageContext(_jspx_page_context);
/* 8105 */     _jspx_th_fmt_005fmessage_005f17.setParent((Tag)_jspx_th_logic_005fiterate_005f5);
/* 8106 */     int _jspx_eval_fmt_005fmessage_005f17 = _jspx_th_fmt_005fmessage_005f17.doStartTag();
/* 8107 */     if (_jspx_eval_fmt_005fmessage_005f17 != 0) {
/* 8108 */       if (_jspx_eval_fmt_005fmessage_005f17 != 1) {
/* 8109 */         out = _jspx_page_context.pushBody();
/* 8110 */         _jspx_th_fmt_005fmessage_005f17.setBodyContent((BodyContent)out);
/* 8111 */         _jspx_th_fmt_005fmessage_005f17.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8114 */         out.write("table.heading.status");
/* 8115 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f17.doAfterBody();
/* 8116 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8119 */       if (_jspx_eval_fmt_005fmessage_005f17 != 1) {
/* 8120 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8123 */     if (_jspx_th_fmt_005fmessage_005f17.doEndTag() == 5) {
/* 8124 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 8125 */       return true;
/*      */     }
/* 8127 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 8128 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f43(JspTag _jspx_th_logic_005fiterate_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8133 */     PageContext pageContext = _jspx_page_context;
/* 8134 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8136 */     OutTag _jspx_th_c_005fout_005f43 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8137 */     _jspx_th_c_005fout_005f43.setPageContext(_jspx_page_context);
/* 8138 */     _jspx_th_c_005fout_005f43.setParent((Tag)_jspx_th_logic_005fiterate_005f6);
/*      */     
/* 8140 */     _jspx_th_c_005fout_005f43.setValue("${baseid}");
/* 8141 */     int _jspx_eval_c_005fout_005f43 = _jspx_th_c_005fout_005f43.doStartTag();
/* 8142 */     if (_jspx_th_c_005fout_005f43.doEndTag() == 5) {
/* 8143 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 8144 */       return true;
/*      */     }
/* 8146 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 8147 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f5(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8152 */     PageContext pageContext = _jspx_page_context;
/* 8153 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8155 */     PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 8156 */     _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 8157 */     _jspx_th_tiles_005fput_005f5.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 8159 */     _jspx_th_tiles_005fput_005f5.setName("footer");
/*      */     
/* 8161 */     _jspx_th_tiles_005fput_005f5.setValue("/jsp/footer.jsp");
/* 8162 */     int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 8163 */     if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 8164 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 8165 */       return true;
/*      */     }
/* 8167 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 8168 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ScriptMonitor_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */