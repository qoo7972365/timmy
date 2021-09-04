/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.util.AMRegexUtil;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.ExtProdUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.nms.util.NmsUtil;
/*      */ import com.adventnet.snmp.mibs.MibNode;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.SimpleDateFormat;
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
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.SkipPageException;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class cam_005faddsnmpobjects_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   68 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   71 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   72 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   73 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   80 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   85 */     ArrayList list = null;
/*   86 */     StringBuffer sbf = new StringBuffer();
/*   87 */     ManagedApplication mo = new ManagedApplication();
/*   88 */     if (distinct)
/*      */     {
/*   90 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   94 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   97 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   99 */       ArrayList row = (ArrayList)list.get(i);
/*  100 */       sbf.append("<option value='" + row.get(0) + "'>");
/*  101 */       if (distinct) {
/*  102 */         sbf.append(row.get(0));
/*      */       } else
/*  104 */         sbf.append(row.get(1));
/*  105 */       sbf.append("</option>");
/*      */     }
/*      */     
/*  108 */     return sbf.toString(); }
/*      */   
/*  110 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  113 */     if (severity == null)
/*      */     {
/*  115 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  117 */     if (severity.equals("5"))
/*      */     {
/*  119 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  121 */     if (severity.equals("1"))
/*      */     {
/*  123 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  128 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  135 */     if (severity == null)
/*      */     {
/*  137 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  139 */     if (severity.equals("1"))
/*      */     {
/*  141 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  143 */     if (severity.equals("4"))
/*      */     {
/*  145 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  147 */     if (severity.equals("5"))
/*      */     {
/*  149 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  154 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  160 */     if (severity == null)
/*      */     {
/*  162 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  164 */     if (severity.equals("5"))
/*      */     {
/*  166 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  168 */     if (severity.equals("1"))
/*      */     {
/*  170 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  174 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  180 */     if (severity == null)
/*      */     {
/*  182 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  184 */     if (severity.equals("1"))
/*      */     {
/*  186 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  188 */     if (severity.equals("4"))
/*      */     {
/*  190 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  192 */     if (severity.equals("5"))
/*      */     {
/*  194 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  198 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  204 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  210 */     if (severity == 5)
/*      */     {
/*  212 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  214 */     if (severity == 1)
/*      */     {
/*  216 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  221 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  227 */     if (severity == null)
/*      */     {
/*  229 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  231 */     if (severity.equals("5"))
/*      */     {
/*  233 */       if (isAvailability) {
/*  234 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  237 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  240 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  242 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  244 */     if (severity.equals("1"))
/*      */     {
/*  246 */       if (isAvailability) {
/*  247 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  250 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  257 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  264 */     if (severity == null)
/*      */     {
/*  266 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  268 */     if (severity.equals("5"))
/*      */     {
/*  270 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  272 */     if (severity.equals("4"))
/*      */     {
/*  274 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  276 */     if (severity.equals("1"))
/*      */     {
/*  278 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  283 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  289 */     if (severity == null)
/*      */     {
/*  291 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  293 */     if (severity.equals("5"))
/*      */     {
/*  295 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  297 */     if (severity.equals("4"))
/*      */     {
/*  299 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  301 */     if (severity.equals("1"))
/*      */     {
/*  303 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  308 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  315 */     if (severity == null)
/*      */     {
/*  317 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  319 */     if (severity.equals("5"))
/*      */     {
/*  321 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  323 */     if (severity.equals("4"))
/*      */     {
/*  325 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  327 */     if (severity.equals("1"))
/*      */     {
/*  329 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  334 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  342 */     StringBuffer out = new StringBuffer();
/*  343 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  344 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  345 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  346 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  347 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  348 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  349 */     out.append("</tr>");
/*  350 */     out.append("</form></table>");
/*  351 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  358 */     if (val == null)
/*      */     {
/*  360 */       return "-";
/*      */     }
/*      */     
/*  363 */     String ret = FormatUtil.formatNumber(val);
/*  364 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  365 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  368 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  372 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  380 */     StringBuffer out = new StringBuffer();
/*  381 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  382 */     out.append("<tr>");
/*  383 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  385 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  387 */     out.append("</tr>");
/*  388 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  392 */       if (j % 2 == 0)
/*      */       {
/*  394 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  398 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  401 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  403 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  406 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  410 */       out.append("</tr>");
/*      */     }
/*  412 */     out.append("</table>");
/*  413 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  414 */     out.append("<tr>");
/*  415 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  416 */     out.append("</tr>");
/*  417 */     out.append("</table>");
/*  418 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  424 */     StringBuffer out = new StringBuffer();
/*  425 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  426 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  427 */     out.append("<tr>");
/*  428 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  429 */     out.append("<tr>");
/*  430 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  431 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  432 */     out.append("</tr>");
/*  433 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  436 */       out.append("<tr>");
/*  437 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  438 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  439 */       out.append("</tr>");
/*      */     }
/*      */     
/*  442 */     out.append("</table>");
/*  443 */     out.append("</table>");
/*  444 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  449 */     if (severity.equals("0"))
/*      */     {
/*  451 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  455 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  462 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  475 */     StringBuffer out = new StringBuffer();
/*  476 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  477 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  479 */       out.append("<tr>");
/*  480 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  481 */       out.append("</tr>");
/*      */       
/*      */ 
/*  484 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  486 */         String borderclass = "";
/*      */         
/*      */ 
/*  489 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  491 */         out.append("<tr>");
/*      */         
/*  493 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  494 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  495 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  501 */     out.append("</table><br>");
/*  502 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  503 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  505 */       List sLinks = secondLevelOfLinks[0];
/*  506 */       List sText = secondLevelOfLinks[1];
/*  507 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  510 */         out.append("<tr>");
/*  511 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  512 */         out.append("</tr>");
/*  513 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  515 */           String borderclass = "";
/*      */           
/*      */ 
/*  518 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  520 */           out.append("<tr>");
/*      */           
/*  522 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  523 */           if (sLinks.get(i).toString().length() == 0) {
/*  524 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  527 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  529 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  533 */     out.append("</table>");
/*  534 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  541 */     StringBuffer out = new StringBuffer();
/*  542 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  543 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  545 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  547 */         out.append("<tr>");
/*  548 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  549 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  553 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  555 */           String borderclass = "";
/*      */           
/*      */ 
/*  558 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  560 */           out.append("<tr>");
/*      */           
/*  562 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  563 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  564 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  567 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  570 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  575 */     out.append("</table><br>");
/*  576 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  577 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  579 */       List sLinks = secondLevelOfLinks[0];
/*  580 */       List sText = secondLevelOfLinks[1];
/*  581 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  584 */         out.append("<tr>");
/*  585 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  586 */         out.append("</tr>");
/*  587 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  589 */           String borderclass = "";
/*      */           
/*      */ 
/*  592 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  594 */           out.append("<tr>");
/*      */           
/*  596 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  597 */           if (sLinks.get(i).toString().length() == 0) {
/*  598 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  601 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  603 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  607 */     out.append("</table>");
/*  608 */     return out.toString();
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
/*  621 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  624 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  627 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  630 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  633 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  636 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  639 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  642 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  650 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  655 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  660 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  665 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  670 */     if (val != null)
/*      */     {
/*  672 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  676 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  681 */     if (val == null) {
/*  682 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  686 */       val = new SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  691 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  697 */     if (val != null)
/*      */     {
/*  699 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  703 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  709 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  714 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  718 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  723 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  728 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  733 */     String hostaddress = "";
/*  734 */     String ip = request.getHeader("x-forwarded-for");
/*  735 */     if (ip == null)
/*  736 */       ip = request.getRemoteAddr();
/*  737 */     InetAddress add = null;
/*  738 */     if (ip.equals("127.0.0.1")) {
/*  739 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  743 */       add = InetAddress.getByName(ip);
/*      */     }
/*  745 */     hostaddress = add.getHostName();
/*  746 */     if (hostaddress.indexOf('.') != -1) {
/*  747 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  748 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  752 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  757 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  763 */     if (severity == null)
/*      */     {
/*  765 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  767 */     if (severity.equals("5"))
/*      */     {
/*  769 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  771 */     if (severity.equals("1"))
/*      */     {
/*  773 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  778 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  783 */     ResultSet set = null;
/*  784 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  785 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  787 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  788 */       if (set.next()) { String str1;
/*  789 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  790 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  793 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  798 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  801 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  803 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  807 */     StringBuffer rca = new StringBuffer();
/*  808 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  809 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  812 */     int rcalength = key.length();
/*  813 */     String split = "6. ";
/*  814 */     int splitPresent = key.indexOf(split);
/*  815 */     String div1 = "";String div2 = "";
/*  816 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  818 */       if (rcalength > 180) {
/*  819 */         rca.append("<span class=\"rca-critical-text\">");
/*  820 */         getRCATrimmedText(key, rca);
/*  821 */         rca.append("</span>");
/*      */       } else {
/*  823 */         rca.append("<span class=\"rca-critical-text\">");
/*  824 */         rca.append(key);
/*  825 */         rca.append("</span>");
/*      */       }
/*  827 */       return rca.toString();
/*      */     }
/*  829 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  830 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  831 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  832 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  833 */     getRCATrimmedText(div1, rca);
/*  834 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  837 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  838 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  839 */     getRCATrimmedText(div2, rca);
/*  840 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  842 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  847 */     String[] st = msg.split("<br>");
/*  848 */     for (int i = 0; i < st.length; i++) {
/*  849 */       String s = st[i];
/*  850 */       if (s.length() > 180) {
/*  851 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  853 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  857 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  858 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  860 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  864 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  865 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  866 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  869 */       if (key == null) {
/*  870 */         return ret;
/*      */       }
/*      */       
/*  873 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  874 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  877 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  878 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  879 */       set = AMConnectionPool.executeQueryStmt(query);
/*  880 */       if (set.next())
/*      */       {
/*  882 */         String helpLink = set.getString("LINK");
/*  883 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  886 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  892 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  911 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  902 */         if (set != null) {
/*  903 */           AMConnectionPool.closeStatement(set);
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
/*  917 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  918 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  920 */       String entityStr = (String)keys.nextElement();
/*  921 */       String mmessage = temp.getProperty(entityStr);
/*  922 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  923 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  925 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  931 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  932 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  934 */       String entityStr = (String)keys.nextElement();
/*  935 */       String mmessage = temp.getProperty(entityStr);
/*  936 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  937 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  939 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  944 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  954 */     String des = new String();
/*  955 */     while (str.indexOf(find) != -1) {
/*  956 */       des = des + str.substring(0, str.indexOf(find));
/*  957 */       des = des + replace;
/*  958 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  960 */     des = des + str;
/*  961 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  968 */       if (alert == null)
/*      */       {
/*  970 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  972 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  974 */         return "&nbsp;";
/*      */       }
/*      */       
/*  977 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  979 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  982 */       int rcalength = test.length();
/*  983 */       if (rcalength < 300)
/*      */       {
/*  985 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  989 */       StringBuffer out = new StringBuffer();
/*  990 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  991 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  992 */       out.append("</div>");
/*  993 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  994 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  995 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1000 */       ex.printStackTrace();
/*      */     }
/* 1002 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1008 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1013 */     ArrayList attribIDs = new ArrayList();
/* 1014 */     ArrayList resIDs = new ArrayList();
/* 1015 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1017 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1019 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1021 */       String resourceid = "";
/* 1022 */       String resourceType = "";
/* 1023 */       if (type == 2) {
/* 1024 */         resourceid = (String)row.get(0);
/* 1025 */         resourceType = (String)row.get(3);
/*      */       }
/* 1027 */       else if (type == 3) {
/* 1028 */         resourceid = (String)row.get(0);
/* 1029 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1032 */         resourceid = (String)row.get(6);
/* 1033 */         resourceType = (String)row.get(7);
/*      */       }
/* 1035 */       resIDs.add(resourceid);
/* 1036 */       String healthid = AMAttributesCache.getHealthId(resourceType);
/* 1037 */       String availid = AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1039 */       String healthentity = null;
/* 1040 */       String availentity = null;
/* 1041 */       if (healthid != null) {
/* 1042 */         healthentity = resourceid + "_" + healthid;
/* 1043 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1046 */       if (availid != null) {
/* 1047 */         availentity = resourceid + "_" + availid;
/* 1048 */         entitylist.add(availentity);
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
/* 1062 */     Properties alert = getStatus(entitylist);
/* 1063 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1068 */     int size = monitorList.size();
/*      */     
/* 1070 */     String[] severity = new String[size];
/*      */     
/* 1072 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1074 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1075 */       String resourceName1 = (String)row1.get(7);
/* 1076 */       String resourceid1 = (String)row1.get(6);
/* 1077 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1078 */       if (severity[j] == null)
/*      */       {
/* 1080 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1084 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1086 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1088 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1091 */         if (sev > 0) {
/* 1092 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1093 */           monitorList.set(k, monitorList.get(j));
/* 1094 */           monitorList.set(j, t);
/* 1095 */           String temp = severity[k];
/* 1096 */           severity[k] = severity[j];
/* 1097 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1103 */     int z = 0;
/* 1104 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1107 */       int i = 0;
/* 1108 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1111 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1115 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1119 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1121 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1124 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1128 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1131 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1132 */       String resourceName1 = (String)row1.get(7);
/* 1133 */       String resourceid1 = (String)row1.get(6);
/* 1134 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1135 */       if (hseverity[j] == null)
/*      */       {
/* 1137 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1142 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1144 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1147 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1150 */         if (hsev > 0) {
/* 1151 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1152 */           monitorList.set(k, monitorList.get(j));
/* 1153 */           monitorList.set(j, t);
/* 1154 */           String temp1 = hseverity[k];
/* 1155 */           hseverity[k] = hseverity[j];
/* 1156 */           hseverity[j] = temp1;
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
/* 1168 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1169 */     boolean forInventory = false;
/* 1170 */     String trdisplay = "none";
/* 1171 */     String plusstyle = "inline";
/* 1172 */     String minusstyle = "none";
/* 1173 */     String haidTopLevel = "";
/* 1174 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1176 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1178 */         haidTopLevel = request.getParameter("haid");
/* 1179 */         forInventory = true;
/* 1180 */         trdisplay = "table-row;";
/* 1181 */         plusstyle = "none";
/* 1182 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1189 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1192 */     ArrayList listtoreturn = new ArrayList();
/* 1193 */     StringBuffer toreturn = new StringBuffer();
/* 1194 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1195 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1196 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1198 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1200 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1201 */       String childresid = (String)singlerow.get(0);
/* 1202 */       String childresname = (String)singlerow.get(1);
/* 1203 */       childresname = ExtProdUtil.decodeString(childresname);
/* 1204 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1205 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1206 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1207 */       String unmanagestatus = (String)singlerow.get(5);
/* 1208 */       String actionstatus = (String)singlerow.get(6);
/* 1209 */       String linkclass = "monitorgp-links";
/* 1210 */       String titleforres = childresname;
/* 1211 */       String titilechildresname = childresname;
/* 1212 */       String childimg = "/images/trcont.png";
/* 1213 */       String flag = "enable";
/* 1214 */       String dcstarted = (String)singlerow.get(8);
/* 1215 */       String configMonitor = "";
/* 1216 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1217 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1219 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1221 */       if (singlerow.get(7) != null)
/*      */       {
/* 1223 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1225 */       String haiGroupType = "0";
/* 1226 */       if ("HAI".equals(childtype))
/*      */       {
/* 1228 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1230 */       childimg = "/images/trend.png";
/* 1231 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1232 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1233 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1235 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1237 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1239 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1240 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1243 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1245 */         linkclass = "disabledtext";
/* 1246 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1248 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1249 */       String availmouseover = "";
/* 1250 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1252 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1254 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1255 */       String healthmouseover = "";
/* 1256 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1258 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1261 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1262 */       int spacing = 0;
/* 1263 */       if (level >= 1)
/*      */       {
/* 1265 */         spacing = 40 * level;
/*      */       }
/* 1267 */       if (childtype.equals("HAI"))
/*      */       {
/* 1269 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1270 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1271 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1273 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1274 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1275 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1276 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1277 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1278 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1279 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1280 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1281 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1282 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1283 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1285 */         if (!forInventory)
/*      */         {
/* 1287 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1290 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1292 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1294 */           actions = editlink + actions;
/*      */         }
/* 1296 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1298 */           actions = actions + associatelink;
/*      */         }
/* 1300 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1301 */         String arrowimg = "";
/* 1302 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1304 */           actions = "";
/* 1305 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1306 */           checkbox = "";
/* 1307 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1309 */         if (isIt360)
/*      */         {
/* 1311 */           actionimg = "";
/* 1312 */           actions = "";
/* 1313 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1314 */           checkbox = "";
/*      */         }
/*      */         
/* 1317 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1319 */           actions = "";
/*      */         }
/* 1321 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1323 */           checkbox = "";
/*      */         }
/*      */         
/* 1326 */         String resourcelink = "";
/*      */         
/* 1328 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1330 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1334 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1337 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1338 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1339 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1340 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1341 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1342 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1343 */         if (!isIt360)
/*      */         {
/* 1345 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1349 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1352 */         toreturn.append("</tr>");
/* 1353 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1355 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1356 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1360 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1361 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1364 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1368 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1370 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1371 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1372 */             toreturn.append(assocMessage);
/* 1373 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1374 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1375 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1376 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1382 */         String resourcelink = null;
/* 1383 */         boolean hideEditLink = false;
/* 1384 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1386 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1387 */           hideEditLink = true;
/* 1388 */           if (isIt360)
/*      */           {
/* 1390 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1394 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1396 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1398 */           hideEditLink = true;
/* 1399 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1400 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1405 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1408 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1409 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1410 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1411 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1412 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1413 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1414 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1415 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1416 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1417 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1418 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1419 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1420 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1422 */         if (hideEditLink)
/*      */         {
/* 1424 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1426 */         if (!forInventory)
/*      */         {
/* 1428 */           removefromgroup = "";
/*      */         }
/* 1430 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1431 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1432 */           actions = actions + configcustomfields;
/*      */         }
/* 1434 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1436 */           actions = editlink + actions;
/*      */         }
/* 1438 */         String managedLink = "";
/* 1439 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1441 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1442 */           actions = "";
/* 1443 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1444 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1447 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1449 */           checkbox = "";
/*      */         }
/*      */         
/* 1452 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1454 */           actions = "";
/*      */         }
/* 1456 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1457 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1458 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1459 */         if (isIt360)
/*      */         {
/* 1461 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1465 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1467 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1468 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1469 */         if (!isIt360)
/*      */         {
/* 1471 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1475 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1477 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1480 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1487 */       StringBuilder toreturn = new StringBuilder();
/* 1488 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1489 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1490 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1491 */       String title = "";
/* 1492 */       message = EnterpriseUtil.decodeString(message);
/* 1493 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1494 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1495 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1497 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1499 */       else if ("5".equals(severity))
/*      */       {
/* 1501 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1505 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1507 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1508 */       toreturn.append(v);
/*      */       
/* 1510 */       toreturn.append(link);
/* 1511 */       if (severity == null)
/*      */       {
/* 1513 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1515 */       else if (severity.equals("5"))
/*      */       {
/* 1517 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1519 */       else if (severity.equals("4"))
/*      */       {
/* 1521 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1523 */       else if (severity.equals("1"))
/*      */       {
/* 1525 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1530 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1532 */       toreturn.append("</a>");
/* 1533 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1537 */       ex.printStackTrace();
/*      */     }
/* 1539 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1546 */       StringBuilder toreturn = new StringBuilder();
/* 1547 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1548 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1549 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1550 */       if (message == null)
/*      */       {
/* 1552 */         message = "";
/*      */       }
/*      */       
/* 1555 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1556 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1558 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1559 */       toreturn.append(v);
/*      */       
/* 1561 */       toreturn.append(link);
/*      */       
/* 1563 */       if (severity == null)
/*      */       {
/* 1565 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1567 */       else if (severity.equals("5"))
/*      */       {
/* 1569 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1571 */       else if (severity.equals("1"))
/*      */       {
/* 1573 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1578 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1580 */       toreturn.append("</a>");
/* 1581 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1587 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1590 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1591 */     if (invokeActions != null) {
/* 1592 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1593 */       while (iterator.hasNext()) {
/* 1594 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1595 */         if (actionmap.containsKey(actionid)) {
/* 1596 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1601 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1605 */     String actionLink = "";
/* 1606 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1607 */     String query = "";
/* 1608 */     ResultSet rs = null;
/* 1609 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1610 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1611 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1612 */       actionLink = "method=" + methodName;
/*      */     }
/* 1614 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1615 */       actionLink = methodName;
/*      */     }
/* 1617 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1618 */     Iterator itr = methodarglist.iterator();
/* 1619 */     boolean isfirstparam = true;
/* 1620 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1621 */     while (itr.hasNext()) {
/* 1622 */       HashMap argmap = (HashMap)itr.next();
/* 1623 */       String argtype = (String)argmap.get("TYPE");
/* 1624 */       String argname = (String)argmap.get("IDENTITY");
/* 1625 */       String paramname = (String)argmap.get("PARAMETER");
/* 1626 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1627 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1628 */         isfirstparam = false;
/* 1629 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1631 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1635 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1639 */         actionLink = actionLink + "&";
/*      */       }
/* 1641 */       String paramValue = null;
/* 1642 */       String tempargname = argname;
/* 1643 */       if (commonValues.getProperty(tempargname) != null) {
/* 1644 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1647 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1648 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1649 */           if (dbType.equals("mysql")) {
/* 1650 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1653 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1655 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1657 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1658 */             if (rs.next()) {
/* 1659 */               paramValue = rs.getString("VALUE");
/* 1660 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1664 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1668 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1671 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1676 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1677 */           paramValue = rowId;
/*      */         }
/* 1679 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1680 */           paramValue = managedObjectName;
/*      */         }
/* 1682 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1683 */           paramValue = resID;
/*      */         }
/* 1685 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1686 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1689 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1691 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1692 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1693 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1695 */     return actionLink;
/*      */   }
/*      */   
/* 1698 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1699 */     String dependentAttribute = null;
/* 1700 */     String align = "left";
/*      */     
/* 1702 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1703 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1704 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1705 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1706 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1707 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1708 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1709 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1710 */       align = "center";
/*      */     }
/*      */     
/* 1713 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1714 */     String actualdata = "";
/*      */     
/* 1716 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1717 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1718 */         actualdata = availValue;
/*      */       }
/* 1720 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1721 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1725 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1726 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1729 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1735 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1736 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1737 */       toreturn.append("<table>");
/* 1738 */       toreturn.append("<tr>");
/* 1739 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1740 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1741 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1742 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1743 */         String toolTip = "";
/* 1744 */         String hideClass = "";
/* 1745 */         String textStyle = "";
/* 1746 */         boolean isreferenced = true;
/* 1747 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1748 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1749 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1750 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1752 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1753 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1754 */           while (valueList.hasMoreTokens()) {
/* 1755 */             String dependentVal = valueList.nextToken();
/* 1756 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1757 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1758 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1760 */               toolTip = "";
/* 1761 */               hideClass = "";
/* 1762 */               isreferenced = false;
/* 1763 */               textStyle = "disabledtext";
/* 1764 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1768 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1769 */           toolTip = "";
/* 1770 */           hideClass = "";
/* 1771 */           isreferenced = false;
/* 1772 */           textStyle = "disabledtext";
/* 1773 */           if (dependentImageMap != null) {
/* 1774 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1775 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1778 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1782 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1783 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1784 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1785 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1786 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1787 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1789 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1790 */           if (isreferenced) {
/* 1791 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1795 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1796 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1797 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1798 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1799 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1800 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1802 */           toreturn.append("</span>");
/* 1803 */           toreturn.append("</a>");
/* 1804 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1807 */       toreturn.append("</tr>");
/* 1808 */       toreturn.append("</table>");
/* 1809 */       toreturn.append("</td>");
/*      */     } else {
/* 1811 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1814 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1818 */     String colTime = null;
/* 1819 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1820 */     if ((rows != null) && (rows.size() > 0)) {
/* 1821 */       Iterator<String> itr = rows.iterator();
/* 1822 */       String maxColQuery = "";
/* 1823 */       for (;;) { if (itr.hasNext()) {
/* 1824 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1825 */           ResultSet maxCol = null;
/*      */           try {
/* 1827 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1828 */             while (maxCol.next()) {
/* 1829 */               if (colTime == null) {
/* 1830 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1833 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1842 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1844 */               if (maxCol != null)
/* 1845 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1847 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1842 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1844 */               if (maxCol != null)
/* 1845 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1847 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1852 */     return colTime;
/*      */   }
/*      */   
/* 1855 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1856 */     tablename = null;
/* 1857 */     ResultSet rsTable = null;
/* 1858 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1860 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1861 */       while (rsTable.next()) {
/* 1862 */         tablename = rsTable.getString("DATATABLE");
/* 1863 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1864 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1877 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1868 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1871 */         if (rsTable != null)
/* 1872 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1874 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1880 */     String argsList = "";
/* 1881 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1883 */       if (showArgsMap.get(row) != null) {
/* 1884 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1885 */         if (showArgslist != null) {
/* 1886 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1887 */             if (argsList.trim().equals("")) {
/* 1888 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1891 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1898 */       e.printStackTrace();
/* 1899 */       return "";
/*      */     }
/* 1901 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1906 */     String argsList = "";
/* 1907 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1910 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1912 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1913 */         if (hideArgsList != null)
/*      */         {
/* 1915 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1917 */             if (argsList.trim().equals(""))
/*      */             {
/* 1919 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1923 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1931 */       ex.printStackTrace();
/*      */     }
/* 1933 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1937 */     StringBuilder toreturn = new StringBuilder();
/* 1938 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1945 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1946 */       Iterator itr = tActionList.iterator();
/* 1947 */       while (itr.hasNext()) {
/* 1948 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1949 */         String confirmmsg = "";
/* 1950 */         String link = "";
/* 1951 */         String isJSP = "NO";
/* 1952 */         HashMap tactionMap = (HashMap)itr.next();
/* 1953 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1954 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1955 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1956 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1957 */           (actionmap.containsKey(actionId))) {
/* 1958 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1959 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1960 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1961 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1962 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1964 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1970 */           if (isTableAction) {
/* 1971 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1974 */             tableName = "Link";
/* 1975 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1976 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1977 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1978 */             toreturn.append("</a></td>");
/*      */           }
/* 1980 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1981 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1982 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1983 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1989 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1995 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1997 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1998 */       Properties prop = (Properties)node.getUserObject();
/* 1999 */       String mgID = prop.getProperty("label");
/* 2000 */       String mgName = prop.getProperty("value");
/* 2001 */       String isParent = prop.getProperty("isParent");
/* 2002 */       int mgIDint = Integer.parseInt(mgID);
/* 2003 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 2005 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 2007 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 2008 */       if (node.getChildCount() > 0)
/*      */       {
/* 2010 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2012 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2014 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2016 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2020 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2025 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2027 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2029 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2031 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2035 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2038 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2039 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2041 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2045 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2047 */       if (node.getChildCount() > 0)
/*      */       {
/* 2049 */         builder.append("<UL>");
/* 2050 */         printMGTree(node, builder);
/* 2051 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2056 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2057 */     StringBuffer toReturn = new StringBuffer();
/* 2058 */     String table = "-";
/*      */     try {
/* 2060 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
/* 2061 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2062 */       float total = 0.0F;
/* 2063 */       while (it.hasNext()) {
/* 2064 */         String attName = (String)it.next();
/* 2065 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2066 */         boolean roundOffData = false;
/* 2067 */         if ((data != null) && (!data.equals(""))) {
/* 2068 */           if (data.indexOf(",") != -1) {
/* 2069 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2072 */             float value = Float.parseFloat(data);
/* 2073 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2076 */             total += value;
/* 2077 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2080 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2085 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2086 */       while (attVsWidthList.hasNext()) {
/* 2087 */         String attName = (String)attVsWidthList.next();
/* 2088 */         String data = (String)attVsWidthProps.get(attName);
/* 2089 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2090 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2091 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2092 */         String className = (String)graphDetails.get("ClassName");
/* 2093 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2094 */         if (percentage < 1.0F)
/*      */         {
/* 2096 */           data = percentage + "";
/*      */         }
/* 2098 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2100 */       if (toReturn.length() > 0) {
/* 2101 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2105 */       e.printStackTrace();
/*      */     }
/* 2107 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2113 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2114 */     List<String> criticalThresholdValues = AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2115 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2116 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2117 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2118 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2119 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2120 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2121 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2124 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2125 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2126 */       splitvalues[0] = multiplecondition.toString();
/* 2127 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2130 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2135 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2136 */     if (thresholdType != 3) {
/* 2137 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2138 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2139 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2140 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2141 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2142 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2144 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2145 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2146 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2147 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2148 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2149 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2151 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2152 */     if (updateSelected != null) {
/* 2153 */       updateSelected[0] = "selected";
/*      */     }
/* 2155 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2160 */       StringBuffer toreturn = new StringBuffer("");
/* 2161 */       if (commaSeparatedMsgId != null) {
/* 2162 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2163 */         int count = 0;
/* 2164 */         while (msgids.hasMoreTokens()) {
/* 2165 */           String id = msgids.nextToken();
/* 2166 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2167 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2168 */           count++;
/* 2169 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2170 */             if (toreturn.length() == 0) {
/* 2171 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2173 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2174 */             if (!image.trim().equals("")) {
/* 2175 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2177 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2178 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2181 */         if (toreturn.length() > 0) {
/* 2182 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2186 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2189 */       e.printStackTrace(); }
/* 2190 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2198 */   String selectedMib = "RFC1213-MIB";
/*      */   String prevselectedMib;
/*      */   String userName;
/* 2201 */   Hashtable getLoadedMib = null;
/* 2202 */   MibNode mibnode = null;
/* 2203 */   String compiledFile = "false";
/* 2204 */   String OverWrite = "true";
/* 2205 */   File mibdir = new File(NmsUtil.MIBDIR);
/*      */   
/*      */ 
/*      */ 
/* 2209 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2215 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2216 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
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
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2232 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2236 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2245 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2249 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2250 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2251 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2252 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2253 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2254 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2255 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2256 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2263 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2266 */     JspWriter out = null;
/* 2267 */     Object page = this;
/* 2268 */     JspWriter _jspx_out = null;
/* 2269 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2273 */       response.setContentType("text/html;charset=UTF-8");
/* 2274 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2276 */       _jspx_page_context = pageContext;
/* 2277 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2278 */       ServletConfig config = pageContext.getServletConfig();
/* 2279 */       session = pageContext.getSession();
/* 2280 */       out = pageContext.getOut();
/* 2281 */       _jspx_out = out;
/*      */       
/* 2283 */       out.write("<!DOCTYPE html>\n");
/* 2284 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2285 */       out.write(10);
/* 2286 */       out.write(10);
/*      */       
/* 2288 */       request.setAttribute("HelpKey", "Adding SNMP OID Attributes");
/*      */       
/* 2290 */       out.write("\n\n\n\n\n\n\n\n \n\n\n \n\n\n\n\n\n");
/* 2291 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2293 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2294 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2295 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2297 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2299 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2301 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2303 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2304 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2305 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2306 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2309 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2310 */         String available = null;
/* 2311 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2312 */         out.write(10);
/*      */         
/* 2314 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2315 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2316 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2318 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2320 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2322 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2324 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2325 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2326 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2327 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2330 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2331 */           String unavailable = null;
/* 2332 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2333 */           out.write(10);
/*      */           
/* 2335 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2336 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2337 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2339 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2341 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2343 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2345 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2346 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2347 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2348 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2351 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2352 */             String unmanaged = null;
/* 2353 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2354 */             out.write(10);
/*      */             
/* 2356 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2357 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2358 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2360 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2362 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2364 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2366 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2367 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2368 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2369 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2372 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2373 */               String scheduled = null;
/* 2374 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2375 */               out.write(10);
/*      */               
/* 2377 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2378 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2379 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2381 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2383 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2385 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2387 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2388 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2389 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2390 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2393 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2394 */                 String critical = null;
/* 2395 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2396 */                 out.write(10);
/*      */                 
/* 2398 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2399 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2400 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2402 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2404 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2406 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2408 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2409 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2410 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2411 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2414 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2415 */                   String clear = null;
/* 2416 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2417 */                   out.write(10);
/*      */                   
/* 2419 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2420 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2421 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2423 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2425 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2427 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2429 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2430 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2431 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2432 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2435 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2436 */                     String warning = null;
/* 2437 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2438 */                     out.write(10);
/* 2439 */                     out.write(10);
/*      */                     
/* 2441 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2442 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2444 */                     out.write(10);
/* 2445 */                     out.write(10);
/* 2446 */                     out.write(10);
/* 2447 */                     out.write("\n \n\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n\n");
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2453 */                     String camID = (String)request.getAttribute("camid");
/* 2454 */                     String haid = (String)request.getAttribute("haid");
/* 2455 */                     String screenID = (String)request.getAttribute("screenid");
/* 2456 */                     List screenInfo = CAMDBUtil.getScreenInfo(Long.parseLong(screenID));
/* 2457 */                     String hostName = (String)request.getAttribute("hostname");
/* 2458 */                     String portNumber = (String)request.getAttribute("portnumber");
/* 2459 */                     String resourceType = (String)request.getAttribute("resourcetype");
/* 2460 */                     String resourceID = (String)request.getAttribute("resourceid");
/*      */                     
/* 2462 */                     String quickNote = "Numeric attributes can be plotted as a graph.";
/* 2463 */                     String topDesc = "SNMP OID exposed in the monitor can be added to screen here.";
/* 2464 */                     request.setAttribute("quicknote", quickNote);
/*      */                     
/* 2466 */                     request.setAttribute("needCustomizeScreen", "hint for cam_leftlinks.jsp");
/* 2467 */                     request.setAttribute("needDeleteScreen", "hint for cam_leftlinks.jsp");
/*      */                     
/*      */ 
/* 2470 */                     String camname = "";
/* 2471 */                     Map resourceInfoFromResourcePage = null;
/*      */                     
/* 2473 */                     if ("true".equals((String)request.getAttribute("isfromresourcepage"))) {
/* 2474 */                       resourceInfoFromResourcePage = CAMDBUtil.getAMMOInfo(Integer.parseInt(camID));
/*      */                     } else {
/* 2476 */                       List list = CAMDBUtil.getCAMDetails(camID);
/* 2477 */                       camname = (String)list.get(0);
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/* 2482 */                     out.write(10);
/* 2483 */                     out.write(10);
/*      */                     
/* 2485 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2486 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2487 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2489 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2490 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2491 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2493 */                         out.write(10);
/* 2494 */                         if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2496 */                         out.write(10);
/* 2497 */                         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2499 */                         out.write(10);
/* 2500 */                         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2502 */                         out.write(10);
/* 2503 */                         out.write(10);
/*      */                         
/* 2505 */                         PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2506 */                         _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2507 */                         _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2509 */                         _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                         
/* 2511 */                         _jspx_th_tiles_005fput_005f3.setType("string");
/* 2512 */                         int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2513 */                         if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2514 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2515 */                             out = _jspx_page_context.pushBody();
/* 2516 */                             _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2517 */                             _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2520 */                             out.write("\n  <script>\n\n function setVal()\n{   var val;\n    var count=0;\n    var sep;\n    var disname=new Array();\n    var oidname=new Array();\n      var scalar= new Array(document.getElementsByName(\"scalar\"));\n     \n      for(var i=0; i<document.getElementsByName(\"scalar\").length ;i++)\n     { \n        \n\tif(document.getElementById(\"scalar\"+i).checked)\n       {\n        val=document.getElementById(\"scalar\"+i).value;\n        sep=val.indexOf(\"$\");\n\n        disname[count]=val.substr(0,sep);\n        \n        oidname[count]=val.substr(sep+1);\n\n      count++;\n      }\n     }\n     \n     for(var j=0; j<document.getElementsByName(\"table\").length;j++)\n     {  \n\tif(document.getElementById(\"table\"+j).checked)\n        {val=document.getElementById(\"table\"+j).value;\n         sep=val.indexOf(\"$\");\n         var col=val.substr(0,sep);\n         for(var k=0;k<document.getElementsByName(col).length;k++)\n         {\n           val=document.getElementById(col+k).value;\n         \n           sep=val.indexOf(\"$\");\n           disname[count]=val.substr(0,sep);\n");
/* 2521 */                             out.write("        \n            oidname[count]=val.substr(sep+1);\n   \n        \n          count++;\n          }\n        \n        }\n     else\n        {val=document.getElementById(\"table\"+j).value;\n         sep=val.indexOf(\"$\");\n         var col=val.substr(0,sep);\n         for(var k=0;k<document.getElementsByName(col).length;k++)\n          {if(document.getElementById(col+k).checked)\n            \n          {\n           val=document.getElementById(col+k).value;\n         \n           sep=val.indexOf(\"$\");\n           disname[count]=val.substr(0,sep);\n        \n            oidname[count]=val.substr(sep+1);\n   \n        \n            count++;\n          }\n         }\n        }\n       \n     }\n     \n      \n\n    \n     document.CAMSNMPAgentDetailsForm.oiddisplayname.value=disname;\n     document.CAMSNMPAgentDetailsForm.snmpoid.value=oidname;\n     document.CAMSNMPAgentDetailsForm.mibname.value=document.getElementById(\"selectedMib\").value;\n    \n     return count;\n}\n\n function validateAndSubmit()\n  {  \n     \n     var count=setVal();\n      \n\tif(count!=0)\n");
/* 2522 */                             out.write("   {\n    \n    document.CAMSNMPAgentDetailsForm.submit();\n   }\n  else \n  {\n\t  alert('");
/* 2523 */                             out.print(FormatUtil.getString("am.webclient.cam.snmpattrib.alert"));
/* 2524 */                             out.write("');\n\n  }\n}\n  \n  function reloadForm()\n  {\n     document.CAMSNMPAgentDetailsForm.method.value='reloadCAMSNMPObjectsForm';\n     document.CAMSNMPAgentDetailsForm.submit();\n  }\n  \n  function cancelfn() \n  {\n  \t");
/*      */                             
/* 2526 */                             IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2527 */                             _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2528 */                             _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2530 */                             _jspx_th_c_005fif_005f0.setTest("${('true' == isfromresourcepage)}");
/* 2531 */                             int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2532 */                             if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                               for (;;) {
/* 2534 */                                 out.write("    \n\t    var url = \"/showresource.do?resourceid=");
/* 2535 */                                 out.print(camID);
/* 2536 */                                 out.write("&method=showResourceForResourceID\";\n\t    document.CAMSNMPAgentDetailsForm.action = url;\n\t    document.CAMSNMPAgentDetailsForm.submit();\n\t");
/* 2537 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2538 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2542 */                             if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2543 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                             }
/*      */                             
/* 2546 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2547 */                             out.write(10);
/* 2548 */                             out.write(10);
/* 2549 */                             out.write(9);
/* 2550 */                             if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2552 */                             out.write("\n  }\n  \n function validateAndSubmitOID()\n  {\n\tvar oid = document.getElementById(\"oidval\").value;\n\tvar disp = document.getElementById(\"dispname\").value;\n\tif (oid.length == 0){\n    \talert(\"Input OID to add\");\n\t}\n\telse if(disp.length == 0)\n\t{\n\talert(\"Input Display Name for OID\");\n\t}\n\telse\n\t{\n\tdocument.CAMSNMPAgentDetailsForm.oiddisplayname.value=disp;\n     \tdocument.CAMSNMPAgentDetailsForm.snmpoid.value=oid;\n\tdocument.CAMSNMPAgentDetailsForm.mibname.value=\"nill\";\n     \tdocument.CAMSNMPAgentDetailsForm.submit();\n\t}\n   }\n  \n  </script>\n  \n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr> \n    ");
/*      */                             
/* 2554 */                             Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2555 */                             String aid = request.getParameter("haid");
/* 2556 */                             String haName = null;
/* 2557 */                             if (aid != null)
/*      */                             {
/* 2559 */                               haName = (String)ht.get(aid);
/*      */                             }
/*      */                             
/* 2562 */                             out.write("\n    ");
/* 2563 */                             if (_jspx_meth_c_005fcatch_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2565 */                             out.write(" \n    ");
/*      */                             
/* 2567 */                             IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2568 */                             _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2569 */                             _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2571 */                             _jspx_th_c_005fif_005f2.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2572 */                             int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2573 */                             if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                               for (;;) {
/* 2575 */                                 out.write(" \n    <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2576 */                                 out.print(BreadcrumbUtil.getHomePage(request));
/* 2577 */                                 out.write(" \n      &gt; ");
/* 2578 */                                 out.print(BreadcrumbUtil.getHAPage(aid, haName));
/* 2579 */                                 out.write(" &gt; ");
/* 2580 */                                 out.print(BreadcrumbUtil.getCAMDetailsPage(camID, aid, camname));
/* 2581 */                                 out.write(" \n      &gt; <span class=\"bcactive\"> Custom Attributes </span></td>\n    ");
/* 2582 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2583 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2587 */                             if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2588 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                             }
/*      */                             
/* 2591 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2592 */                             out.write(" \n    \n    ");
/*      */                             
/* 2594 */                             IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2595 */                             _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2596 */                             _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2598 */                             _jspx_th_c_005fif_005f3.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 2599 */                             int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2600 */                             if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                               for (;;) {
/* 2602 */                                 out.write("\t\n    <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2603 */                                 out.print(BreadcrumbUtil.getMonitorsPage());
/* 2604 */                                 out.write(" \n      &gt; \n      \n");
/*      */                                 
/* 2606 */                                 IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2607 */                                 _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2608 */                                 _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fif_005f3);
/*      */                                 
/* 2610 */                                 _jspx_th_c_005fif_005f4.setTest("${('true'!=isfromresourcepage)}");
/* 2611 */                                 int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2612 */                                 if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                   for (;;) {
/* 2614 */                                     out.write("      \n      ");
/* 2615 */                                     out.print(BreadcrumbUtil.getMonitorResourceTypes("Custom-Application"));
/* 2616 */                                     out.write(" \n      &gt; ");
/* 2617 */                                     out.print(BreadcrumbUtil.getCAMDetailsPage(camID, aid, camname));
/* 2618 */                                     out.write(32);
/* 2619 */                                     out.write(10);
/* 2620 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2621 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2625 */                                 if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2626 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                 }
/*      */                                 
/* 2629 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2630 */                                 out.write("      \n \n \n");
/*      */                                 
/* 2632 */                                 IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2633 */                                 _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2634 */                                 _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fif_005f3);
/*      */                                 
/* 2636 */                                 _jspx_th_c_005fif_005f5.setTest("${('true'==isfromresourcepage)}");
/* 2637 */                                 int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2638 */                                 if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                   for (;;) {
/* 2640 */                                     out.write(10);
/* 2641 */                                     out.write(9);
/* 2642 */                                     out.print(BreadcrumbUtil.getMonitorResourceTypes((String)resourceInfoFromResourcePage.get("TYPE")));
/* 2643 */                                     out.write(" \n\t&gt;\n\t\n\t");
/* 2644 */                                     out.print(BreadcrumbUtil.getServerDetailsPage(camID, (String)resourceInfoFromResourcePage.get("DISPLAYNAME")));
/* 2645 */                                     out.write(10);
/* 2646 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2647 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2651 */                                 if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2652 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                 }
/*      */                                 
/* 2655 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2656 */                                 out.write("\n\t&gt; <span class=\"bcactive\"> \n      ");
/* 2657 */                                 out.print(FormatUtil.getString("am.webclient.cam.addattributes.link"));
/* 2658 */                                 out.write("</span> \n      </td>\n    ");
/* 2659 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2660 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2664 */                             if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2665 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                             }
/*      */                             
/* 2668 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2669 */                             out.write(" </tr>\n  \n</table>\n  \n <script>\nfunction validateSubmit()\n{\n   if(trimAll(document.AMActionForm.camname.value)==\"\")\n   {\n        alert('");
/* 2670 */                             out.print(FormatUtil.getString("am.webclient.cam.namealert"));
/* 2671 */                             out.write("');\n        return;\n   }\n   document.AMActionForm.submit();\n}\n\n</script>\n\n");
/*      */                             
/* 2673 */                             List list = CAMDBUtil.getCAMDetails(camID);
/* 2674 */                             String camName = (String)list.get(0);
/* 2675 */                             String camDescription = (String)list.get(2);
/*      */                             
/* 2677 */                             out.write("\n\n\n\n<div id=\"edit\" style=\"DISPLAY: none\">\n\n");
/*      */                             
/* 2679 */                             FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 2680 */                             _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2681 */                             _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2683 */                             _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*      */                             
/* 2685 */                             _jspx_th_html_005fform_005f0.setMethod("get");
/*      */                             
/* 2687 */                             _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 2688 */                             int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2689 */                             if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                               for (;;) {
/* 2691 */                                 out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\n<tr>\n<td height=\"28\"   class=\"tableheading\">");
/* 2692 */                                 out.print(FormatUtil.getString("am.webclient.common.configurationdetails.text"));
/* 2693 */                                 out.write("\n\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 2694 */                                 out.print(request.getParameter("name"));
/* 2695 */                                 out.write("\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 2696 */                                 out.print(request.getParameter("haid"));
/* 2697 */                                 out.write("\">\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 2698 */                                 out.print(request.getParameter("type"));
/* 2699 */                                 out.write("\">\n<input type=\"hidden\" name=\"type\" value=\"");
/* 2700 */                                 out.print(request.getParameter("type"));
/* 2701 */                                 out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"configureJMX\">\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2702 */                                 out.print(request.getParameter("resourceid"));
/* 2703 */                                 out.write("\">\n<input type=\"hidden\" name=\"resourcename\" value=\"");
/* 2704 */                                 out.print(request.getParameter("resourcename"));
/* 2705 */                                 out.write("\">\n<input type=\"hidden\" name=\"moname\" value=\"");
/* 2706 */                                 out.print(request.getParameter("moname"));
/* 2707 */                                 out.write("\">\n</td>\n<td height=\"28\"   class=\"tableheading\" align=\"right\" onClick=\"javascript:hideDiv('edit')\">\n<span class=\"bodytextboldwhiteun\" ><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\">\n");
/* 2708 */                                 out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 2709 */                                 out.write("</span>\n</td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n<tr>\n    <td width=\"20%\" height=\"32\" valign=='top' class=\"bodytext\"> ");
/* 2710 */                                 out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2711 */                                 out.write("\n</td>\n    <td width=\"80%\" class=\"bodytext\"> <textarea name=\"camname\" cols=\"38\" rows=\"1\" class=\"formtextarea\">");
/* 2712 */                                 out.print(camName);
/* 2713 */                                 out.write(" </textarea>\n</td>\n</tr>\n\n<tr>\n    <td valign='top'  class=\"bodytext\"> ");
/* 2714 */                                 out.print(FormatUtil.getString("Description"));
/* 2715 */                                 out.write("</td>\n    <td  class=\"bodytext\"> <textarea name=\"camdesc\" cols=\"38\" rows=\"3\" class=\"formtextarea\" >");
/* 2716 */                                 out.print(camDescription);
/* 2717 */                                 out.write("</textarea>\n    </td>\n  </tr>\n</table>\n\n");
/*      */                                 
/* 2719 */                                 String cancel = FormatUtil.getString("am.webclient.common.cancel.text");
/*      */                                 
/* 2721 */                                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"20%\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"80%\" class=\"tablebottom\" >\n<input name=\"addcustomapp\" type=\"button\" class=\"buttons btn_highlt\" \" value=\"");
/* 2722 */                                 out.print(FormatUtil.getString("am.webclient.common.startmonitoring.text"));
/* 2723 */                                 out.write("\" onClick=\"validateSubmit()\"/>\n      &nbsp; <input name=\"reset\" type=\"reset\" class=\"buttons btn_reset\" value=\"");
/* 2724 */                                 out.print(cancel);
/* 2725 */                                 out.write("\" onClick=\"javascript:toggleDiv('edit')\"/>\n     </td>\n  </tr>\n</table>\n");
/* 2726 */                                 int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2727 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2731 */                             if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2732 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                             }
/*      */                             
/* 2735 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2736 */                             out.write("\n</div>\n \n  \n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>   \n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/mibbrowser.js\"></SCRIPT>\n <script>\n\nfunction myOnLoad()\n{\n\tif(document.location.hash && document.location.hash.trim() != \"\" && document.location.hash.indexOf('#selmib')!=-1) {\n\t\tselmib=document.location.hash.split(\"=\")[1];\n\t\tajaxFunction(selmib);\n\t} else {\t\n  ajaxFunction('RFC1213-MIB');\n\t}\n  showHide('mibbrowser');\n} \n\t\n function openAddMibWin() {\t\n \n\t  var win=window.open('/jsp/MibUpload.jsp','','resizable=yes,scrollbars=yes,width=760,height=300');\n\t  win.focus();\n\t \n  }\n  \n  \n\nvar xmlHttp;\nfunction ajaxFunction(selmib)\n{\n\twindow.location.hash=\"#selmib=\"+selmib; // NO I18N\n\tif(document.location.hash && document.location.hash.trim() != \"\" && document.location.hash.indexOf('#selmib')!=-1) {\n\t\tselmib=document.location.hash.split(\"=\")[1];\n\t}\t\n  try\n    {\n    // Firefox, Opera 8.0+, Safari\n    xmlHttp=new XMLHttpRequest();\n    }\n  catch (e)\n    {\n    // Internet Explorer\n    try\n");
/* 2737 */                             out.write("      {\n      xmlHttp=new ActiveXObject(\"Msxml2.XMLHTTP\");\n      }\n    catch (e)\n      {\n      try\n        {\n        xmlHttp=new ActiveXObject(\"Microsoft.XMLHTTP\");\n        }\n      catch (e)\n        {\n        alert(\"Your browser does not support AJAX!\");\n        return false;\n        }\n      }\n    }\n  \n    xmlHttp.onreadystatechange=AjaxTest;\n    URL=\"/jsp/Mibinterdiv.jsp?selmib=\"+selmib;\n    xmlHttp.open(\"GET\",URL,true);\n    xmlHttp.send(null);\n}\n\nfunction AjaxTest()\n{\n\tif(xmlHttp.readyState==4)\n\t{\n\t\tdocument.getElementById(\"listcontainer\").innerHTML=xmlHttp.responseText;\n\t}\n}\n \nfunction checkUncheckAll(table, coloumn)\n{  \n    if(!table.checked)\n        {mstrchk(table);\n         }\n\t \n         if(coloumn.length==null){\n          coloumn.checked = table.checked;\n           \n          }\n\t for (i = 0; i < coloumn.length; i++)\n\t\t{ \n\t\t\tcoloumn[i].checked = table.checked;\n\t\t}\n\t \n         \n}\nfunction checkAll(master)\n{ var sep;\n  var val; \n\nfor (i = 0; i < document.getElementsByName(\"scalar\").length; i++)\n\t\t{\n\t\t\tdocument.getElementById(\"scalar\"+i).checked = master.checked;\n");
/* 2738 */                             out.write("\t\t}\n\tfor (i = 0; i < document.getElementsByName(\"table\").length; i++)\n\t\t{\n\t\t\tdocument.getElementById(\"table\"+i).checked = master.checked;\n                        \n                        val=document.getElementById(\"table\"+i).value;\n                         sep=val.indexOf(\"$\");\n                         var col=val.substr(0,sep);\n                         for(var k=0;k<document.getElementsByName(col).length;k++)\n                          { document.getElementById(col+k).checked = master.checked\n                          \n                          }\n\t\t} \n}\nfunction mstrchk(node)\n{\n  \n  if(node.name==\"scalar\"){\n   document.getElementById(\"master\").checked=node.checked;\n   }\n  else if(node.name==\"table\"){\n   document.getElementById(\"master\").checked=node.checked;\n   }\n  else\n   {document.getElementById(\"master\").checked=node.checked;\n    var tab=node.name\n    for (i = 0; i < document.getElementsByName(\"table\").length; i++)\n\t\t{\n\t\t\t val=document.getElementById(\"table\"+i).value;\n                         sep=val.indexOf(\"$\");\n");
/* 2739 */                             out.write("                         var col=val.substr(0,sep);\n                         if(col==tab)\n                         {\n                             document.getElementById(\"table\"+i).checked=node.checked;\n                         }\n\n\t\t} \n   }\n}\nfunction showHide(tab)\n{\n\tif(tab==\"mibbrowser\")\n\t{\n\t\tdocument.getElementById(\"performancelink\").className = \"profile\";\n\t\tdocument.getElementById(\"diskiolink\").className = \"permissions\";\n\t\tjavascript:showDiv('mibdiv');\n\t\tjavascript:hideDiv('oiddiv');\n\t}\n\telse if(tab==\"addoid\")\n\t{\n\t\tdocument.getElementById(\"performancelink\").className = \"permissions\";\n\t\tdocument.getElementById(\"diskiolink\").className = \"profile\";\n\t\tjavascript:showDiv('oiddiv');\n\t\tjavascript:hideDiv('mibdiv');\n\t} \n\n}\n</script>\n\n");
/*      */                             
/* 2741 */                             FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 2742 */                             _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/* 2743 */                             _jspx_th_html_005fform_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2745 */                             _jspx_th_html_005fform_005f1.setAction("/CAMSNMPObjects");
/*      */                             
/* 2747 */                             _jspx_th_html_005fform_005f1.setMethod("post");
/*      */                             
/* 2749 */                             _jspx_th_html_005fform_005f1.setStyle("display:inline");
/* 2750 */                             int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/* 2751 */                             if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */                               for (;;) {
/* 2753 */                                 out.write(" \n\n\n\n<input type=\"hidden\" name=\"camid\" value=\"");
/* 2754 */                                 out.print(camID);
/* 2755 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 2756 */                                 out.print(haid);
/* 2757 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"screenid\" value=\"");
/* 2758 */                                 out.print(screenID);
/* 2759 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"hostname\" value=\"");
/* 2760 */                                 out.print(hostName);
/* 2761 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"portnumber\" value=\"");
/* 2762 */                                 out.print(portNumber);
/* 2763 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 2764 */                                 out.print(resourceType);
/* 2765 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2766 */                                 out.print(resourceID);
/* 2767 */                                 out.write("\"/>\n<input type=hidden name=\"method\" value=\"addSNMPObjects\"/>\n<input type=\"hidden\" name=\"mibname\" value=\"");
/* 2768 */                                 out.print(this.selectedMib);
/* 2769 */                                 out.write("\" />\n<input type=\"hidden\" name=\"isfromresourcepage\" value=\"");
/* 2770 */                                 out.print(request.getAttribute("isfromresourcepage"));
/* 2771 */                                 out.write("\"/>  \n<input type=\"hidden\" name=\"oiddisplayname\" />\n<input type=\"hidden\" name=\"snmpoid\"  />\n\n  <table width=\"100%\">\n   <tr>\n     <td width=\"14%\" align=\"center\" valign=\"top\" class=\"profile\" id=\"performancelink\"><a href=\"javascript:showHide('mibbrowser')\" class=\"staticlinks\">");
/* 2772 */                                 out.print(FormatUtil.getString("am.webclient.cam.snmp.mibbrowser.head"));
/* 2773 */                                 out.write("</a></td>\n     <td width=\"8%\" align=\"center\" valign=\"top\" Class=\"permissions\" id=\"diskiolink\"><a href=\"javascript:showHide('addoid')\" class=\"staticlinks\">");
/* 2774 */                                 out.print(FormatUtil.getString("am.webclient.cam.snmp.mibbrowser.oidtab"));
/* 2775 */                                 out.write("</a></td>\n     <td width=\"70%\">&nbsp;</td>\n   </tr>\n </table>\n <div id=\"mibdiv\" style=\"display:none\">\n\n<table  width=\"99%\" valign=\"top\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\"  bgcolor='FFFFFF'>\n  <tr>\n  <td  width=\"99%\" class=\"tableheading\" colspan=\"2\"><span>");
/* 2776 */                                 out.print(FormatUtil.getString("am.webclient.cam.snmp.mibbrowser.head"));
/* 2777 */                                 out.write("</span> </td>\n  </tr>\n  <tr> \n    <td> \n   <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr class=\"tablebottom\"> \n   <td class=\"columnheading\">\n    <span>");
/* 2778 */                                 out.print(FormatUtil.getString("am.webclient.cam.snmp.mibbrowser"));
/* 2779 */                                 out.write("\n\t&nbsp;\n");
/* 2780 */                                 out.write("\n<SELECT  onchange=\"ajaxFunction(this.value)\" NAME=\"selectedMib\" id=\"selectedMib\" >\n");
/*      */                                 
/*      */ 
/* 2783 */                                 if (!this.mibdir.isDirectory())
/*      */                                 {
/* 2785 */                                   out.println(MessageFormat.format(NmsUtil.GetString("{0} is not a directory"), new String[] { "" + NmsUtil.MIBDIR }));
/*      */                                 }
/* 2787 */                                 String[] str = this.mibdir.list();
/* 2788 */                                 for (int i = 0; i < str.length; i++) {
/* 2789 */                                   if (this.selectedMib != null)
/*      */                                   {
/*      */ 
/* 2792 */                                     if (str[i].equalsIgnoreCase("RFC1213-MIB"))
/*      */                                     {
/*      */ 
/* 2795 */                                       out.write(" \n\n\t\t\t\t<OPTION SELECTED value=\"");
/* 2796 */                                       out.print(str[i]);
/* 2797 */                                       out.write(34);
/* 2798 */                                       out.write(62);
/* 2799 */                                       out.print(str[i]);
/* 2800 */                                       out.write("</OPTION> \n\n");
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/*      */ 
/* 2806 */                                       out.write(" \n\n\t\t\t\t<OPTION value=\"");
/* 2807 */                                       out.print(str[i]);
/* 2808 */                                       out.write(34);
/* 2809 */                                       out.write(62);
/* 2810 */                                       out.print(str[i]);
/* 2811 */                                       out.write("</OPTION> \n\n");
/*      */                                     }
/*      */                                     
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/*      */ 
/* 2819 */                                     out.write(" \n\n\t\t<OPTION SELECTED value=\"");
/* 2820 */                                     out.print(str[i]);
/* 2821 */                                     out.write(34);
/* 2822 */                                     out.write(62);
/* 2823 */                                     out.print(str[i]);
/* 2824 */                                     out.write("</OPTION> \n");
/*      */                                   }
/*      */                                 }
/*      */                                 
/*      */ 
/* 2829 */                                 out.write(" \n      </SELECT>\n</span>\n</td>\n<td class=\"columnheading\">\n\n<span align =\"right\">\n\n\n <input name=\"addmibbtn\" type=\"button\" align=\"right\" class='buttons btn_highlt' value='");
/* 2830 */                                 out.print(FormatUtil.getString("am.webclient.cam.snmp.addmib.button"));
/* 2831 */                                 out.write("' onClick=\"javascript:openAddMibWin()\"/>\n\n </span>\n</td>\n</tr>\n\n\n</table>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n<tr>\n<td>\n     </td>\n</tr>\n\n  <tr colspan=2 class=\"tablebottom\" width=\"100%\"> \n    <td>\n    </td>\n    <td align=\"center\"> \n      <input name=\"submissionbutton\" type=\"button\" class='buttons btn_highlt' value=\"");
/* 2832 */                                 out.print(FormatUtil.getString("am.webclient.cam.showmbeans.addattributes"));
/* 2833 */                                 out.write("\" onClick=\"validateAndSubmit()\"/>\n      &nbsp; \n      <input name=\"cnbutton\" type=\"button\" class='buttons btn_link' onClick=\"javascript:cancelfn();\" value=\"");
/* 2834 */                                 out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 2835 */                                 out.write("\" /> \n    </td>\n     <td>\n     </td>\n  </tr>\n</table>\n\n<div id=\"listcontainer\"> \n\n </div>\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n<tr>\n<td>\n     </td>\n</tr>\n\n  <tr colspan=2 class=\"tablebottom\" width=\"100%\"> \n    <td>\n    </td>\n    <td align=\"center\"> \n      <input name=\"submissionbutton\" type=\"button\" class='buttons btn_highlt' value=\"");
/* 2836 */                                 out.print(FormatUtil.getString("am.webclient.cam.showmbeans.addattributes"));
/* 2837 */                                 out.write("\" onClick=\"validateAndSubmit()\"/>\n      &nbsp; \n      <input name=\"cnbutton\" type=\"button\" class='buttons btn_link' onClick=\"javascript:cancelfn();\" value=\"");
/* 2838 */                                 out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 2839 */                                 out.write("\" /> \n    </td>\n     <td>\n     </td>\n  </tr>\n</table>\n</td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr> \n    <td >&nbsp; </td>\n  </tr>\n</table>\n\n<table width=\"99%\" valign=\"center\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr> \n  \n  <td colspan=\"6\" height=\"31\" class=\"tableheading\"> ");
/* 2840 */                                 out.print(FormatUtil.getString("am.webclient.cam.snmp.monitordetails"));
/* 2841 */                                 out.write("</td>\n  </tr>\n\n  <tr> \n    <td width=\"15%\" class=\"whitegrayborder\">");
/* 2842 */                                 out.print(FormatUtil.getString("am.webclient.cam.snmp.monitorname"));
/* 2843 */                                 out.write(" :</td>\n    <td title =\"");
/* 2844 */                                 out.print(hostName);
/* 2845 */                                 out.write("\" width=\"36%\" class=\"whitegrayborder\"> ");
/* 2846 */                                 out.print(getTrimmedText(hostName, 33));
/* 2847 */                                 out.write("</td>\n    <td width=\"6%\" class=\"whitegrayborder\"> ");
/* 2848 */                                 out.print(FormatUtil.getString("am.webclient.cam.snmp.port"));
/* 2849 */                                 out.write(" :</td>\n    <td width=\"10%\" class=\"whitegrayborder\"> ");
/* 2850 */                                 out.print(portNumber);
/* 2851 */                                 out.write("</td>\n    <td width=\"15%\" class=\"whitegrayborder\">");
/* 2852 */                                 out.print(FormatUtil.getString("am.webclient.cam.snmp.monitortype"));
/* 2853 */                                 out.write(" :</td>\n    <td width=\"16%\" class=\"whitegrayborder\">");
/* 2854 */                                 out.print(resourceType);
/* 2855 */                                 out.write("</td>\n  </tr>\n</table>\n</div>\n<div id=\"oiddiv\" style=\"display:none\">\n\t\t<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n \t\t<tr> \n    \t\t<td colspan=\"2\" class=\"tableheadingbborder\">");
/* 2856 */                                 out.print(FormatUtil.getString("am.webclient.cam.snmp.mibbrowser.oidhead"));
/* 2857 */                                 out.write("</td>\n \t\t </tr><tr>\n\t\t<tr>\n\t\t<td>&nbsp;\n\t\t</td>\n\t\t</tr>\n\t\t<td class=\"bodytext\" width=\"20%\">&nbsp;\n\t\t");
/* 2858 */                                 out.print(FormatUtil.getString("am.webclient.cam.snmp.mibbrowser.oid"));
/* 2859 */                                 out.write(" </td><td><input type=\"text\" id=\"oidval\">\n\t\t</td>\n\t\t</tr> \n\t\t<tr>\n\t\t<td>&nbsp;\n\t\t</td><td>&nbsp;</td>\n\t\t</tr>\n\t\t<tr> \n\t\t<td class=\"bodytext\" width=\"20%\">&nbsp;\n\t\t");
/* 2860 */                                 out.print(FormatUtil.getString("am.webclient.cam.snmp.mibbrowser.oiddisplay"));
/* 2861 */                                 out.write("</td><td>  <input type=\"text\" id=\"dispname\">\n\t\t</td>\n\t\t</tr>\n\t\t<tr>\n\t\t<td>&nbsp;\n\t\t</td>\n\t\t<td>&nbsp;</td>\n\t\t</tr>\n\t\t<tr  class=\"tablebottom\" width=\"100%\">\n\t\t<td align=\"center\" colspan=\"2\" > \n      <input name=\"submissionbutton2\" type=\"button\" class='buttons btn_highlt' value=\"");
/* 2862 */                                 out.print(FormatUtil.getString("am.webclient.cam.snmp.mibbrowser.addoid"));
/* 2863 */                                 out.write("\" onClick=\"validateAndSubmitOID()\"/>\n\t\t &nbsp; \n      <input name=\"cnbutton\" type=\"button\" class='buttons btn_link' onClick=\"javascript:cancelfn();\" value=\"");
/* 2864 */                                 out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 2865 */                                 out.write("\" /> \n\t\t</td>\n\t\t</tr>\n\t\t</table>\n</div>\n");
/* 2866 */                                 int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/* 2867 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2871 */                             if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/* 2872 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*      */                             }
/*      */                             
/* 2875 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f1);
/* 2876 */                             out.write(10);
/* 2877 */                             out.write(10);
/* 2878 */                             out.write(32);
/* 2879 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 2880 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2883 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2884 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2887 */                         if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 2888 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                         }
/*      */                         
/* 2891 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 2892 */                         out.write(32);
/* 2893 */                         out.write(10);
/* 2894 */                         if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2896 */                         out.write(10);
/* 2897 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 2898 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2902 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 2903 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 2906 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2907 */                       out.write("\n</body>\n");
/*      */                     }
/* 2909 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof SkipPageException)) {
/* 2910 */         out = _jspx_out;
/* 2911 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2912 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 2913 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2916 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2922 */     PageContext pageContext = _jspx_page_context;
/* 2923 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2925 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2926 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2927 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2929 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 2931 */     _jspx_th_tiles_005fput_005f0.setValue("Add SNMP Objects from Agent");
/* 2932 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2933 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2934 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2935 */       return true;
/*      */     }
/* 2937 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2938 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2943 */     PageContext pageContext = _jspx_page_context;
/* 2944 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2946 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2947 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 2948 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2950 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 2952 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 2953 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 2954 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 2955 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2956 */       return true;
/*      */     }
/* 2958 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2959 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2964 */     PageContext pageContext = _jspx_page_context;
/* 2965 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2967 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2968 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2969 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2971 */     _jspx_th_tiles_005fput_005f2.setName("ServerLeftArea");
/*      */     
/* 2973 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/cam_leftlinksarea.jsp");
/* 2974 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2975 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 2976 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 2977 */       return true;
/*      */     }
/* 2979 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 2980 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2985 */     PageContext pageContext = _jspx_page_context;
/* 2986 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2988 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2989 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2990 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2992 */     _jspx_th_c_005fif_005f1.setTest("${('true' != isfromresourcepage)}");
/* 2993 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2994 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 2996 */         out.write("    \n\t    javascript:history.back();    \n\t");
/* 2997 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2998 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3002 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3003 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3004 */       return true;
/*      */     }
/* 3006 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3007 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3012 */     PageContext pageContext = _jspx_page_context;
/* 3013 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3015 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 3016 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 3017 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3019 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 3020 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 3022 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 3023 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 3025 */           out.write(32);
/* 3026 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 3027 */             return true;
/* 3028 */           out.write(" \n    ");
/* 3029 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 3030 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3034 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 3035 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3038 */         int tmp184_183 = 0; int[] tmp184_181 = _jspx_push_body_count_c_005fcatch_005f0; int tmp186_185 = tmp184_181[tmp184_183];tmp184_181[tmp184_183] = (tmp186_185 - 1); if (tmp186_185 <= 0) break;
/* 3039 */         out = _jspx_page_context.popBody(); }
/* 3040 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 3042 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 3043 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 3045 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 3050 */     PageContext pageContext = _jspx_page_context;
/* 3051 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3053 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 3054 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 3055 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 3057 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 3059 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 3060 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 3061 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 3062 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3063 */       return true;
/*      */     }
/* 3065 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3066 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3071 */     PageContext pageContext = _jspx_page_context;
/* 3072 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3074 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3075 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 3076 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3078 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 3080 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 3081 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 3082 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 3083 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3084 */       return true;
/*      */     }
/* 3086 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3087 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\cam_005faddsnmpobjects_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */