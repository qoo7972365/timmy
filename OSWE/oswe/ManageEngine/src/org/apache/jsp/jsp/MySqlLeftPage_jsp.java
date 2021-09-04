/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.me.apm.cmdb.APMHelpDeskUtil.CIUrl;
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
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class MySqlLeftPage_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   58 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   61 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   62 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   63 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   70 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   75 */     ArrayList list = null;
/*   76 */     StringBuffer sbf = new StringBuffer();
/*   77 */     ManagedApplication mo = new ManagedApplication();
/*   78 */     if (distinct)
/*      */     {
/*   80 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   84 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   87 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   89 */       ArrayList row = (ArrayList)list.get(i);
/*   90 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   91 */       if (distinct) {
/*   92 */         sbf.append(row.get(0));
/*      */       } else
/*   94 */         sbf.append(row.get(1));
/*   95 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   98 */     return sbf.toString(); }
/*      */   
/*  100 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  103 */     if (severity == null)
/*      */     {
/*  105 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  107 */     if (severity.equals("5"))
/*      */     {
/*  109 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  111 */     if (severity.equals("1"))
/*      */     {
/*  113 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  118 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  125 */     if (severity == null)
/*      */     {
/*  127 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  129 */     if (severity.equals("1"))
/*      */     {
/*  131 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  133 */     if (severity.equals("4"))
/*      */     {
/*  135 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  137 */     if (severity.equals("5"))
/*      */     {
/*  139 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  144 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  150 */     if (severity == null)
/*      */     {
/*  152 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  154 */     if (severity.equals("5"))
/*      */     {
/*  156 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  158 */     if (severity.equals("1"))
/*      */     {
/*  160 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  164 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  170 */     if (severity == null)
/*      */     {
/*  172 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  174 */     if (severity.equals("1"))
/*      */     {
/*  176 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  178 */     if (severity.equals("4"))
/*      */     {
/*  180 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  182 */     if (severity.equals("5"))
/*      */     {
/*  184 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  188 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  194 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  200 */     if (severity == 5)
/*      */     {
/*  202 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  204 */     if (severity == 1)
/*      */     {
/*  206 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  211 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  217 */     if (severity == null)
/*      */     {
/*  219 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  221 */     if (severity.equals("5"))
/*      */     {
/*  223 */       if (isAvailability) {
/*  224 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  227 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  230 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  232 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  234 */     if (severity.equals("1"))
/*      */     {
/*  236 */       if (isAvailability) {
/*  237 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  240 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  247 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  254 */     if (severity == null)
/*      */     {
/*  256 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  258 */     if (severity.equals("5"))
/*      */     {
/*  260 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  262 */     if (severity.equals("4"))
/*      */     {
/*  264 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  266 */     if (severity.equals("1"))
/*      */     {
/*  268 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  273 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  279 */     if (severity == null)
/*      */     {
/*  281 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  283 */     if (severity.equals("5"))
/*      */     {
/*  285 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  287 */     if (severity.equals("4"))
/*      */     {
/*  289 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  291 */     if (severity.equals("1"))
/*      */     {
/*  293 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  298 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  305 */     if (severity == null)
/*      */     {
/*  307 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  309 */     if (severity.equals("5"))
/*      */     {
/*  311 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  313 */     if (severity.equals("4"))
/*      */     {
/*  315 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  317 */     if (severity.equals("1"))
/*      */     {
/*  319 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  324 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  332 */     StringBuffer out = new StringBuffer();
/*  333 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  334 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  335 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  336 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  337 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  338 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  339 */     out.append("</tr>");
/*  340 */     out.append("</form></table>");
/*  341 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  348 */     if (val == null)
/*      */     {
/*  350 */       return "-";
/*      */     }
/*      */     
/*  353 */     String ret = FormatUtil.formatNumber(val);
/*  354 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  355 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  358 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  362 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  370 */     StringBuffer out = new StringBuffer();
/*  371 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  372 */     out.append("<tr>");
/*  373 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  375 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  377 */     out.append("</tr>");
/*  378 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  382 */       if (j % 2 == 0)
/*      */       {
/*  384 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  388 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  391 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  393 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  396 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  400 */       out.append("</tr>");
/*      */     }
/*  402 */     out.append("</table>");
/*  403 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  404 */     out.append("<tr>");
/*  405 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  406 */     out.append("</tr>");
/*  407 */     out.append("</table>");
/*  408 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  414 */     StringBuffer out = new StringBuffer();
/*  415 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  416 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  417 */     out.append("<tr>");
/*  418 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  419 */     out.append("<tr>");
/*  420 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  421 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  422 */     out.append("</tr>");
/*  423 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  426 */       out.append("<tr>");
/*  427 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  428 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  429 */       out.append("</tr>");
/*      */     }
/*      */     
/*  432 */     out.append("</table>");
/*  433 */     out.append("</table>");
/*  434 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  439 */     if (severity.equals("0"))
/*      */     {
/*  441 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  445 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  452 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  465 */     StringBuffer out = new StringBuffer();
/*  466 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  467 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  469 */       out.append("<tr>");
/*  470 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  471 */       out.append("</tr>");
/*      */       
/*      */ 
/*  474 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  476 */         String borderclass = "";
/*      */         
/*      */ 
/*  479 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  481 */         out.append("<tr>");
/*      */         
/*  483 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  484 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  485 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  491 */     out.append("</table><br>");
/*  492 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  493 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  495 */       List sLinks = secondLevelOfLinks[0];
/*  496 */       List sText = secondLevelOfLinks[1];
/*  497 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  500 */         out.append("<tr>");
/*  501 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  502 */         out.append("</tr>");
/*  503 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  505 */           String borderclass = "";
/*      */           
/*      */ 
/*  508 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  510 */           out.append("<tr>");
/*      */           
/*  512 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  513 */           if (sLinks.get(i).toString().length() == 0) {
/*  514 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  517 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  519 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  523 */     out.append("</table>");
/*  524 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  531 */     StringBuffer out = new StringBuffer();
/*  532 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  533 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  535 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  537 */         out.append("<tr>");
/*  538 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  539 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  543 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  545 */           String borderclass = "";
/*      */           
/*      */ 
/*  548 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  550 */           out.append("<tr>");
/*      */           
/*  552 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  553 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  554 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  557 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  560 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  565 */     out.append("</table><br>");
/*  566 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  567 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  569 */       List sLinks = secondLevelOfLinks[0];
/*  570 */       List sText = secondLevelOfLinks[1];
/*  571 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  574 */         out.append("<tr>");
/*  575 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  576 */         out.append("</tr>");
/*  577 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  579 */           String borderclass = "";
/*      */           
/*      */ 
/*  582 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  584 */           out.append("<tr>");
/*      */           
/*  586 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  587 */           if (sLinks.get(i).toString().length() == 0) {
/*  588 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  591 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  593 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  597 */     out.append("</table>");
/*  598 */     return out.toString();
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
/*  611 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  614 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  617 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  620 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  623 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  626 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  629 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  632 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  640 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  645 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  650 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  655 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  660 */     if (val != null)
/*      */     {
/*  662 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  666 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  671 */     if (val == null) {
/*  672 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  676 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  681 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  687 */     if (val != null)
/*      */     {
/*  689 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  693 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  699 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  704 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  708 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  713 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  718 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  723 */     String hostaddress = "";
/*  724 */     String ip = request.getHeader("x-forwarded-for");
/*  725 */     if (ip == null)
/*  726 */       ip = request.getRemoteAddr();
/*  727 */     InetAddress add = null;
/*  728 */     if (ip.equals("127.0.0.1")) {
/*  729 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  733 */       add = InetAddress.getByName(ip);
/*      */     }
/*  735 */     hostaddress = add.getHostName();
/*  736 */     if (hostaddress.indexOf('.') != -1) {
/*  737 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  738 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  742 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  747 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  753 */     if (severity == null)
/*      */     {
/*  755 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  757 */     if (severity.equals("5"))
/*      */     {
/*  759 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  761 */     if (severity.equals("1"))
/*      */     {
/*  763 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  768 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  773 */     ResultSet set = null;
/*  774 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  775 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  777 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  778 */       if (set.next()) { String str1;
/*  779 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  780 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  783 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  788 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  791 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  793 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  797 */     StringBuffer rca = new StringBuffer();
/*  798 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  799 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  802 */     int rcalength = key.length();
/*  803 */     String split = "6. ";
/*  804 */     int splitPresent = key.indexOf(split);
/*  805 */     String div1 = "";String div2 = "";
/*  806 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  808 */       if (rcalength > 180) {
/*  809 */         rca.append("<span class=\"rca-critical-text\">");
/*  810 */         getRCATrimmedText(key, rca);
/*  811 */         rca.append("</span>");
/*      */       } else {
/*  813 */         rca.append("<span class=\"rca-critical-text\">");
/*  814 */         rca.append(key);
/*  815 */         rca.append("</span>");
/*      */       }
/*  817 */       return rca.toString();
/*      */     }
/*  819 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  820 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  821 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  822 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  823 */     getRCATrimmedText(div1, rca);
/*  824 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  827 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  828 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  829 */     getRCATrimmedText(div2, rca);
/*  830 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  832 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  837 */     String[] st = msg.split("<br>");
/*  838 */     for (int i = 0; i < st.length; i++) {
/*  839 */       String s = st[i];
/*  840 */       if (s.length() > 180) {
/*  841 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  843 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  847 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  848 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  850 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  854 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  855 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  856 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  859 */       if (key == null) {
/*  860 */         return ret;
/*      */       }
/*      */       
/*  863 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  864 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  867 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  868 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  869 */       set = AMConnectionPool.executeQueryStmt(query);
/*  870 */       if (set.next())
/*      */       {
/*  872 */         String helpLink = set.getString("LINK");
/*  873 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  876 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  882 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  901 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  892 */         if (set != null) {
/*  893 */           AMConnectionPool.closeStatement(set);
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
/*  907 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  908 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  910 */       String entityStr = (String)keys.nextElement();
/*  911 */       String mmessage = temp.getProperty(entityStr);
/*  912 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  913 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  915 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  921 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  922 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  924 */       String entityStr = (String)keys.nextElement();
/*  925 */       String mmessage = temp.getProperty(entityStr);
/*  926 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  927 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  929 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  934 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  944 */     String des = new String();
/*  945 */     while (str.indexOf(find) != -1) {
/*  946 */       des = des + str.substring(0, str.indexOf(find));
/*  947 */       des = des + replace;
/*  948 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  950 */     des = des + str;
/*  951 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  958 */       if (alert == null)
/*      */       {
/*  960 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  962 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  964 */         return "&nbsp;";
/*      */       }
/*      */       
/*  967 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  969 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  972 */       int rcalength = test.length();
/*  973 */       if (rcalength < 300)
/*      */       {
/*  975 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  979 */       StringBuffer out = new StringBuffer();
/*  980 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  981 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  982 */       out.append("</div>");
/*  983 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  984 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  985 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  990 */       ex.printStackTrace();
/*      */     }
/*  992 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  998 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1003 */     ArrayList attribIDs = new ArrayList();
/* 1004 */     ArrayList resIDs = new ArrayList();
/* 1005 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1007 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1009 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1011 */       String resourceid = "";
/* 1012 */       String resourceType = "";
/* 1013 */       if (type == 2) {
/* 1014 */         resourceid = (String)row.get(0);
/* 1015 */         resourceType = (String)row.get(3);
/*      */       }
/* 1017 */       else if (type == 3) {
/* 1018 */         resourceid = (String)row.get(0);
/* 1019 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1022 */         resourceid = (String)row.get(6);
/* 1023 */         resourceType = (String)row.get(7);
/*      */       }
/* 1025 */       resIDs.add(resourceid);
/* 1026 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1027 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1029 */       String healthentity = null;
/* 1030 */       String availentity = null;
/* 1031 */       if (healthid != null) {
/* 1032 */         healthentity = resourceid + "_" + healthid;
/* 1033 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1036 */       if (availid != null) {
/* 1037 */         availentity = resourceid + "_" + availid;
/* 1038 */         entitylist.add(availentity);
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
/* 1052 */     Properties alert = getStatus(entitylist);
/* 1053 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1058 */     int size = monitorList.size();
/*      */     
/* 1060 */     String[] severity = new String[size];
/*      */     
/* 1062 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1064 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1065 */       String resourceName1 = (String)row1.get(7);
/* 1066 */       String resourceid1 = (String)row1.get(6);
/* 1067 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1068 */       if (severity[j] == null)
/*      */       {
/* 1070 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1074 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1076 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1078 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1081 */         if (sev > 0) {
/* 1082 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1083 */           monitorList.set(k, monitorList.get(j));
/* 1084 */           monitorList.set(j, t);
/* 1085 */           String temp = severity[k];
/* 1086 */           severity[k] = severity[j];
/* 1087 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1093 */     int z = 0;
/* 1094 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1097 */       int i = 0;
/* 1098 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1101 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1105 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1109 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1111 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1114 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1118 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1121 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1122 */       String resourceName1 = (String)row1.get(7);
/* 1123 */       String resourceid1 = (String)row1.get(6);
/* 1124 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1125 */       if (hseverity[j] == null)
/*      */       {
/* 1127 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1132 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1134 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1137 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1140 */         if (hsev > 0) {
/* 1141 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1142 */           monitorList.set(k, monitorList.get(j));
/* 1143 */           monitorList.set(j, t);
/* 1144 */           String temp1 = hseverity[k];
/* 1145 */           hseverity[k] = hseverity[j];
/* 1146 */           hseverity[j] = temp1;
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
/* 1158 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1159 */     boolean forInventory = false;
/* 1160 */     String trdisplay = "none";
/* 1161 */     String plusstyle = "inline";
/* 1162 */     String minusstyle = "none";
/* 1163 */     String haidTopLevel = "";
/* 1164 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1166 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1168 */         haidTopLevel = request.getParameter("haid");
/* 1169 */         forInventory = true;
/* 1170 */         trdisplay = "table-row;";
/* 1171 */         plusstyle = "none";
/* 1172 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1179 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1182 */     ArrayList listtoreturn = new ArrayList();
/* 1183 */     StringBuffer toreturn = new StringBuffer();
/* 1184 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1185 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1186 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1188 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1190 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1191 */       String childresid = (String)singlerow.get(0);
/* 1192 */       String childresname = (String)singlerow.get(1);
/* 1193 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1194 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1195 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1196 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1197 */       String unmanagestatus = (String)singlerow.get(5);
/* 1198 */       String actionstatus = (String)singlerow.get(6);
/* 1199 */       String linkclass = "monitorgp-links";
/* 1200 */       String titleforres = childresname;
/* 1201 */       String titilechildresname = childresname;
/* 1202 */       String childimg = "/images/trcont.png";
/* 1203 */       String flag = "enable";
/* 1204 */       String dcstarted = (String)singlerow.get(8);
/* 1205 */       String configMonitor = "";
/* 1206 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1207 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1209 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1211 */       if (singlerow.get(7) != null)
/*      */       {
/* 1213 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1215 */       String haiGroupType = "0";
/* 1216 */       if ("HAI".equals(childtype))
/*      */       {
/* 1218 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1220 */       childimg = "/images/trend.png";
/* 1221 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1222 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1223 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1225 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1227 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1229 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1230 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1233 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1235 */         linkclass = "disabledtext";
/* 1236 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1238 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1239 */       String availmouseover = "";
/* 1240 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1242 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1244 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1245 */       String healthmouseover = "";
/* 1246 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1248 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1251 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1252 */       int spacing = 0;
/* 1253 */       if (level >= 1)
/*      */       {
/* 1255 */         spacing = 40 * level;
/*      */       }
/* 1257 */       if (childtype.equals("HAI"))
/*      */       {
/* 1259 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1260 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1261 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1263 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1264 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1265 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1266 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1267 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1268 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1269 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1270 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1271 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1272 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1273 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1275 */         if (!forInventory)
/*      */         {
/* 1277 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1280 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1282 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1284 */           actions = editlink + actions;
/*      */         }
/* 1286 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1288 */           actions = actions + associatelink;
/*      */         }
/* 1290 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1291 */         String arrowimg = "";
/* 1292 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1294 */           actions = "";
/* 1295 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1296 */           checkbox = "";
/* 1297 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1299 */         if (isIt360)
/*      */         {
/* 1301 */           actionimg = "";
/* 1302 */           actions = "";
/* 1303 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1304 */           checkbox = "";
/*      */         }
/*      */         
/* 1307 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1309 */           actions = "";
/*      */         }
/* 1311 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1313 */           checkbox = "";
/*      */         }
/*      */         
/* 1316 */         String resourcelink = "";
/*      */         
/* 1318 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1320 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1324 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1327 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1328 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1329 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1330 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1331 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1332 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1333 */         if (!isIt360)
/*      */         {
/* 1335 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1339 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1342 */         toreturn.append("</tr>");
/* 1343 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1345 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1346 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1350 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1351 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1354 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1358 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1360 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1361 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1362 */             toreturn.append(assocMessage);
/* 1363 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1364 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1365 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1366 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1372 */         String resourcelink = null;
/* 1373 */         boolean hideEditLink = false;
/* 1374 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1376 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1377 */           hideEditLink = true;
/* 1378 */           if (isIt360)
/*      */           {
/* 1380 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1384 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1386 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1388 */           hideEditLink = true;
/* 1389 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1390 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1395 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1398 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1399 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1400 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1401 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1402 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1403 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1404 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1405 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1406 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1407 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1408 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1409 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1410 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1412 */         if (hideEditLink)
/*      */         {
/* 1414 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1416 */         if (!forInventory)
/*      */         {
/* 1418 */           removefromgroup = "";
/*      */         }
/* 1420 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1421 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1422 */           actions = actions + configcustomfields;
/*      */         }
/* 1424 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1426 */           actions = editlink + actions;
/*      */         }
/* 1428 */         String managedLink = "";
/* 1429 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1431 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1432 */           actions = "";
/* 1433 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1434 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1437 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1439 */           checkbox = "";
/*      */         }
/*      */         
/* 1442 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1444 */           actions = "";
/*      */         }
/* 1446 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1447 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1448 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1449 */         if (isIt360)
/*      */         {
/* 1451 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1455 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1457 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1458 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1459 */         if (!isIt360)
/*      */         {
/* 1461 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1465 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1467 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1470 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1477 */       StringBuilder toreturn = new StringBuilder();
/* 1478 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1479 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1480 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1481 */       String title = "";
/* 1482 */       message = EnterpriseUtil.decodeString(message);
/* 1483 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1484 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1485 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1487 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1489 */       else if ("5".equals(severity))
/*      */       {
/* 1491 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1495 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1497 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1498 */       toreturn.append(v);
/*      */       
/* 1500 */       toreturn.append(link);
/* 1501 */       if (severity == null)
/*      */       {
/* 1503 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1505 */       else if (severity.equals("5"))
/*      */       {
/* 1507 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1509 */       else if (severity.equals("4"))
/*      */       {
/* 1511 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1513 */       else if (severity.equals("1"))
/*      */       {
/* 1515 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1520 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1522 */       toreturn.append("</a>");
/* 1523 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1527 */       ex.printStackTrace();
/*      */     }
/* 1529 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1536 */       StringBuilder toreturn = new StringBuilder();
/* 1537 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1538 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1539 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1540 */       if (message == null)
/*      */       {
/* 1542 */         message = "";
/*      */       }
/*      */       
/* 1545 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1546 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1548 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1549 */       toreturn.append(v);
/*      */       
/* 1551 */       toreturn.append(link);
/*      */       
/* 1553 */       if (severity == null)
/*      */       {
/* 1555 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1557 */       else if (severity.equals("5"))
/*      */       {
/* 1559 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1561 */       else if (severity.equals("1"))
/*      */       {
/* 1563 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1568 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1570 */       toreturn.append("</a>");
/* 1571 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1577 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1580 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1581 */     if (invokeActions != null) {
/* 1582 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1583 */       while (iterator.hasNext()) {
/* 1584 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1585 */         if (actionmap.containsKey(actionid)) {
/* 1586 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1591 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1595 */     String actionLink = "";
/* 1596 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1597 */     String query = "";
/* 1598 */     ResultSet rs = null;
/* 1599 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1600 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1601 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1602 */       actionLink = "method=" + methodName;
/*      */     }
/* 1604 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1605 */       actionLink = methodName;
/*      */     }
/* 1607 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1608 */     Iterator itr = methodarglist.iterator();
/* 1609 */     boolean isfirstparam = true;
/* 1610 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1611 */     while (itr.hasNext()) {
/* 1612 */       HashMap argmap = (HashMap)itr.next();
/* 1613 */       String argtype = (String)argmap.get("TYPE");
/* 1614 */       String argname = (String)argmap.get("IDENTITY");
/* 1615 */       String paramname = (String)argmap.get("PARAMETER");
/* 1616 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1617 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1618 */         isfirstparam = false;
/* 1619 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1621 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1625 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1629 */         actionLink = actionLink + "&";
/*      */       }
/* 1631 */       String paramValue = null;
/* 1632 */       String tempargname = argname;
/* 1633 */       if (commonValues.getProperty(tempargname) != null) {
/* 1634 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1637 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1638 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1639 */           if (dbType.equals("mysql")) {
/* 1640 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1643 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1645 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1647 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1648 */             if (rs.next()) {
/* 1649 */               paramValue = rs.getString("VALUE");
/* 1650 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1654 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1658 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1661 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1666 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1667 */           paramValue = rowId;
/*      */         }
/* 1669 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1670 */           paramValue = managedObjectName;
/*      */         }
/* 1672 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1673 */           paramValue = resID;
/*      */         }
/* 1675 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1676 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1679 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1681 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1682 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1683 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1685 */     return actionLink;
/*      */   }
/*      */   
/* 1688 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1689 */     String dependentAttribute = null;
/* 1690 */     String align = "left";
/*      */     
/* 1692 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1693 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1694 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1695 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1696 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1697 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1698 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1699 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1700 */       align = "center";
/*      */     }
/*      */     
/* 1703 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1704 */     String actualdata = "";
/*      */     
/* 1706 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1707 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1708 */         actualdata = availValue;
/*      */       }
/* 1710 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1711 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1715 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1716 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1719 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1725 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1726 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1727 */       toreturn.append("<table>");
/* 1728 */       toreturn.append("<tr>");
/* 1729 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1730 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1731 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1732 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1733 */         String toolTip = "";
/* 1734 */         String hideClass = "";
/* 1735 */         String textStyle = "";
/* 1736 */         boolean isreferenced = true;
/* 1737 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1738 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1739 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1740 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1742 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1743 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1744 */           while (valueList.hasMoreTokens()) {
/* 1745 */             String dependentVal = valueList.nextToken();
/* 1746 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1747 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1748 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1750 */               toolTip = "";
/* 1751 */               hideClass = "";
/* 1752 */               isreferenced = false;
/* 1753 */               textStyle = "disabledtext";
/* 1754 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1758 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1759 */           toolTip = "";
/* 1760 */           hideClass = "";
/* 1761 */           isreferenced = false;
/* 1762 */           textStyle = "disabledtext";
/* 1763 */           if (dependentImageMap != null) {
/* 1764 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1765 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1768 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1772 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1773 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1774 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1775 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1776 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1777 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1779 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1780 */           if (isreferenced) {
/* 1781 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1785 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1786 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1787 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1788 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1789 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1790 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1792 */           toreturn.append("</span>");
/* 1793 */           toreturn.append("</a>");
/* 1794 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1797 */       toreturn.append("</tr>");
/* 1798 */       toreturn.append("</table>");
/* 1799 */       toreturn.append("</td>");
/*      */     } else {
/* 1801 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1804 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1808 */     String colTime = null;
/* 1809 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1810 */     if ((rows != null) && (rows.size() > 0)) {
/* 1811 */       Iterator<String> itr = rows.iterator();
/* 1812 */       String maxColQuery = "";
/* 1813 */       for (;;) { if (itr.hasNext()) {
/* 1814 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1815 */           ResultSet maxCol = null;
/*      */           try {
/* 1817 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1818 */             while (maxCol.next()) {
/* 1819 */               if (colTime == null) {
/* 1820 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1823 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1832 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1834 */               if (maxCol != null)
/* 1835 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1837 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1832 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1834 */               if (maxCol != null)
/* 1835 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1837 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1842 */     return colTime;
/*      */   }
/*      */   
/* 1845 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1846 */     tablename = null;
/* 1847 */     ResultSet rsTable = null;
/* 1848 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1850 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1851 */       while (rsTable.next()) {
/* 1852 */         tablename = rsTable.getString("DATATABLE");
/* 1853 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1854 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1867 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1858 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1861 */         if (rsTable != null)
/* 1862 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1864 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1870 */     String argsList = "";
/* 1871 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1873 */       if (showArgsMap.get(row) != null) {
/* 1874 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1875 */         if (showArgslist != null) {
/* 1876 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1877 */             if (argsList.trim().equals("")) {
/* 1878 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1881 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1888 */       e.printStackTrace();
/* 1889 */       return "";
/*      */     }
/* 1891 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1896 */     String argsList = "";
/* 1897 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1900 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1902 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1903 */         if (hideArgsList != null)
/*      */         {
/* 1905 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1907 */             if (argsList.trim().equals(""))
/*      */             {
/* 1909 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1913 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1921 */       ex.printStackTrace();
/*      */     }
/* 1923 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1927 */     StringBuilder toreturn = new StringBuilder();
/* 1928 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1935 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1936 */       Iterator itr = tActionList.iterator();
/* 1937 */       while (itr.hasNext()) {
/* 1938 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1939 */         String confirmmsg = "";
/* 1940 */         String link = "";
/* 1941 */         String isJSP = "NO";
/* 1942 */         HashMap tactionMap = (HashMap)itr.next();
/* 1943 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1944 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1945 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1946 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1947 */           (actionmap.containsKey(actionId))) {
/* 1948 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1949 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1950 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1951 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1952 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1954 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1960 */           if (isTableAction) {
/* 1961 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1964 */             tableName = "Link";
/* 1965 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1966 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1967 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1968 */             toreturn.append("</a></td>");
/*      */           }
/* 1970 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1971 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1972 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1973 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1979 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1985 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1987 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1988 */       Properties prop = (Properties)node.getUserObject();
/* 1989 */       String mgID = prop.getProperty("label");
/* 1990 */       String mgName = prop.getProperty("value");
/* 1991 */       String isParent = prop.getProperty("isParent");
/* 1992 */       int mgIDint = Integer.parseInt(mgID);
/* 1993 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1995 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1997 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1998 */       if (node.getChildCount() > 0)
/*      */       {
/* 2000 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2002 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2004 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2006 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2010 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2015 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2017 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2019 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2021 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2025 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2028 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2029 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2031 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2035 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2037 */       if (node.getChildCount() > 0)
/*      */       {
/* 2039 */         builder.append("<UL>");
/* 2040 */         printMGTree(node, builder);
/* 2041 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2046 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2047 */     StringBuffer toReturn = new StringBuffer();
/* 2048 */     String table = "-";
/*      */     try {
/* 2050 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
/* 2051 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2052 */       float total = 0.0F;
/* 2053 */       while (it.hasNext()) {
/* 2054 */         String attName = (String)it.next();
/* 2055 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2056 */         boolean roundOffData = false;
/* 2057 */         if ((data != null) && (!data.equals(""))) {
/* 2058 */           if (data.indexOf(",") != -1) {
/* 2059 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2062 */             float value = Float.parseFloat(data);
/* 2063 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2066 */             total += value;
/* 2067 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2070 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2075 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2076 */       while (attVsWidthList.hasNext()) {
/* 2077 */         String attName = (String)attVsWidthList.next();
/* 2078 */         String data = (String)attVsWidthProps.get(attName);
/* 2079 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2080 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2081 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2082 */         String className = (String)graphDetails.get("ClassName");
/* 2083 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2084 */         if (percentage < 1.0F)
/*      */         {
/* 2086 */           data = percentage + "";
/*      */         }
/* 2088 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2090 */       if (toReturn.length() > 0) {
/* 2091 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2095 */       e.printStackTrace();
/*      */     }
/* 2097 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2103 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2104 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2105 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2106 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2107 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2108 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2109 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2110 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2111 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2114 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2115 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2116 */       splitvalues[0] = multiplecondition.toString();
/* 2117 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2120 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2125 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2126 */     if (thresholdType != 3) {
/* 2127 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2128 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2129 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2130 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2131 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2132 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2134 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2135 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2136 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2137 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2138 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2139 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2141 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2142 */     if (updateSelected != null) {
/* 2143 */       updateSelected[0] = "selected";
/*      */     }
/* 2145 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2150 */       StringBuffer toreturn = new StringBuffer("");
/* 2151 */       if (commaSeparatedMsgId != null) {
/* 2152 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2153 */         int count = 0;
/* 2154 */         while (msgids.hasMoreTokens()) {
/* 2155 */           String id = msgids.nextToken();
/* 2156 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2157 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2158 */           count++;
/* 2159 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2160 */             if (toreturn.length() == 0) {
/* 2161 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2163 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2164 */             if (!image.trim().equals("")) {
/* 2165 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2167 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2168 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2171 */         if (toreturn.length() > 0) {
/* 2172 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2176 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2179 */       e.printStackTrace(); }
/* 2180 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2186 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2192 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/* 2193 */   static { _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2194 */     _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2195 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2214 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2218 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2230 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2234 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2241 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2242 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2244 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2251 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2254 */     JspWriter out = null;
/* 2255 */     Object page = this;
/* 2256 */     JspWriter _jspx_out = null;
/* 2257 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2261 */       response.setContentType("text/html;charset=UTF-8");
/* 2262 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2264 */       _jspx_page_context = pageContext;
/* 2265 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2266 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2267 */       session = pageContext.getSession();
/* 2268 */       out = pageContext.getOut();
/* 2269 */       _jspx_out = out;
/*      */       
/* 2271 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2272 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2274 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2275 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2276 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2278 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2280 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2282 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2284 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2285 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2286 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2287 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2290 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2291 */         String available = null;
/* 2292 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2293 */         out.write(10);
/*      */         
/* 2295 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2296 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2297 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2299 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2301 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2303 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2305 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2306 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2307 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2308 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2311 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2312 */           String unavailable = null;
/* 2313 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2314 */           out.write(10);
/*      */           
/* 2316 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2317 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2318 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2320 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2322 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2324 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2326 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2327 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2328 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2329 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2332 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2333 */             String unmanaged = null;
/* 2334 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2335 */             out.write(10);
/*      */             
/* 2337 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2338 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2339 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2341 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2343 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2345 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2347 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2348 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2349 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2350 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2353 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2354 */               String scheduled = null;
/* 2355 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2356 */               out.write(10);
/*      */               
/* 2358 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2359 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2360 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2362 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2364 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2366 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2368 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2369 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2370 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2371 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2374 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2375 */                 String critical = null;
/* 2376 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2377 */                 out.write(10);
/*      */                 
/* 2379 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2380 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2381 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2383 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2385 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2387 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2389 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2390 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2391 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2392 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2395 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2396 */                   String clear = null;
/* 2397 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2398 */                   out.write(10);
/*      */                   
/* 2400 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2401 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2402 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2404 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2406 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2408 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2410 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2411 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2412 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2413 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2416 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2417 */                     String warning = null;
/* 2418 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2419 */                     out.write(10);
/* 2420 */                     out.write(10);
/*      */                     
/* 2422 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2423 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2425 */                     out.write(10);
/* 2426 */                     out.write(10);
/* 2427 */                     out.write(10);
/* 2428 */                     out.write("\n\n\n<script>\n");
/*      */                     
/* 2430 */                     if (request.getParameter("editPage") != null)
/*      */                     {
/* 2432 */                       out.write("\n toggleDiv('edit');\n");
/*      */                     }
/*      */                     
/* 2435 */                     out.write("\n</script>\n\n\n");
/*      */                     
/* 2437 */                     String haid = null;
/* 2438 */                     String appname = null;
/* 2439 */                     String network = null;
/* 2440 */                     haid = (String)request.getAttribute("haid");
/* 2441 */                     appname = (String)request.getAttribute("appName");
/* 2442 */                     String resourcename = (String)request.getAttribute("name");
/* 2443 */                     String resourceid = request.getParameter("resourceid");
/* 2444 */                     String configured = (String)request.getAttribute("configured");
/* 2445 */                     String displayname = null;
/* 2446 */                     if (request.getParameter("configure") != null)
/*      */                     {
/* 2448 */                       displayname = (String)request.getAttribute("displayname");
/* 2449 */                       if (displayname == null)
/*      */                       {
/* 2451 */                         displayname = request.getParameter("resourcename");
/*      */                       }
/*      */                     }
/*      */                     else
/*      */                     {
/* 2456 */                       displayname = request.getParameter("resourcename");
/*      */                     }
/* 2458 */                     ArrayList attribIDs = new ArrayList();
/* 2459 */                     ArrayList resIDs = new ArrayList();
/* 2460 */                     attribIDs.add("115");
/* 2461 */                     attribIDs.add("116");
/* 2462 */                     resIDs.add(resourceid);
/* 2463 */                     Properties alert = getStatus(resIDs, attribIDs);
/* 2464 */                     String healthStatus = alert.getProperty(resourceid + "#" + "116");
/* 2465 */                     String avaStatus = alert.getProperty(resourceid + "#" + "115");
/* 2466 */                     if (configured.equals("false"))
/*      */                     {
/* 2468 */                       out.write("\n<br>\n");
/* 2469 */                       out.write("\n<table width=\"95%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\tclass=\"leftmnutables\">\n\t<tr>\n\t\t<td class=\"leftlinksheading\"><img\n\t\t\tsrc=\"/images/");
/* 2470 */                       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                         return;
/* 2472 */                       out.write("/img_quicknote.gif\"\n\t\t\twidth=\"140\" height=\"30\"></td>\n\t</tr>\n\t<tr>\n\t\t<td class=\"quicknote\">");
/* 2473 */                       out.print(FormatUtil.getString("am.webclient.mysqlleftpage.quicknote.text"));
/* 2474 */                       out.write("\n\t\t</td>\n\t</tr>\n</table>\n\n");
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/* 2479 */                       out.write("\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\n\tclass=\"leftmnutables\">\n\t<tr>\n\t\t<td height=\"21\" class=\"leftlinksheading\">");
/* 2480 */                       out.print(FormatUtil.getString("am.webclient.mysql.servertype"));
/* 2481 */                       out.write("\n\t\t</td>\n\t</tr>\n\t<tr>\n\t\t<td width=\"87%\" class=\"leftlinkstd\">");
/*      */                       
/* 2483 */                       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2484 */                       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2485 */                       _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2486 */                       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2487 */                       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                         for (;;) {
/* 2489 */                           out.write("\n\t\t\t");
/*      */                           
/* 2491 */                           WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2492 */                           _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2493 */                           _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                           
/* 2495 */                           _jspx_th_c_005fwhen_005f0.setTest("${param.all!='true'}");
/* 2496 */                           int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2497 */                           if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                             for (;;) {
/* 2499 */                               out.write("\n\t\t\t\t");
/* 2500 */                               out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 2501 */                               out.write("\n\t\t\t");
/* 2502 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2503 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2507 */                           if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2508 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                           }
/*      */                           
/* 2511 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2512 */                           out.write("\n\t\t\t");
/*      */                           
/* 2514 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2515 */                           _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2516 */                           _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2517 */                           int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2518 */                           if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                             for (;;) {
/* 2520 */                               out.write("\n\t\t\t\t<a\n\t\t\t\t\thref=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2521 */                               if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                                 return;
/* 2523 */                               out.write("&haid=");
/* 2524 */                               out.print(haid);
/* 2525 */                               out.write("\"\n\t\t\t\t\tclass=\"new-left-links\">");
/* 2526 */                               out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 2527 */                               out.write("</a>\n\t\t\t");
/* 2528 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2529 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2533 */                           if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2534 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                           }
/*      */                           
/* 2537 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2538 */                           out.write(10);
/* 2539 */                           out.write(9);
/* 2540 */                           out.write(9);
/* 2541 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2542 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2546 */                       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2547 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                       }
/*      */                       
/* 2550 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2551 */                       out.write("</td>\n\t</tr>\n\t");
/*      */                       
/* 2553 */                       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2554 */                       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2555 */                       _jspx_th_c_005fif_005f0.setParent(null);
/*      */                       
/* 2557 */                       _jspx_th_c_005fif_005f0.setTest("${!empty ADMIN || !empty DEMO}");
/* 2558 */                       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2559 */                       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                         for (;;) {
/* 2561 */                           out.write(10);
/* 2562 */                           out.write(9);
/* 2563 */                           out.write(9);
/*      */                           
/* 2565 */                           IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2566 */                           _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2567 */                           _jspx_th_c_005fif_005f1.setParent(_jspx_th_c_005fif_005f0);
/*      */                           
/* 2569 */                           _jspx_th_c_005fif_005f1.setTest("${showdata=='2'}");
/* 2570 */                           int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2571 */                           if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                             for (;;) {
/* 2573 */                               out.write("\n\t\t\t<tr>\n\t\t\t\t<td width=\"87%\" class=\"leftlinkstd\">");
/*      */                               
/* 2575 */                               ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2576 */                               _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2577 */                               _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fif_005f1);
/* 2578 */                               int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2579 */                               if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                                 for (;;) {
/* 2581 */                                   out.write("\n\t\t\t\t\t");
/*      */                                   
/* 2583 */                                   WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2584 */                                   _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2585 */                                   _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                   
/* 2587 */                                   _jspx_th_c_005fwhen_005f1.setTest("${! empty param.configured && details !='Availability' }");
/* 2588 */                                   int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2589 */                                   if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                     for (;;) {
/* 2591 */                                       out.write("\n\t\t\t\t\t\t<a\n\t\t\t\t\t\t\thref=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2592 */                                       if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/*      */                                         return;
/* 2594 */                                       out.write("&haid=");
/* 2595 */                                       out.print(haid);
/* 2596 */                                       out.write("&alert=true\"\n\t\t\t\t\t\t\tclass=\"new-left-links\">");
/* 2597 */                                       out.print(ALERTCONFIG_TEXT);
/* 2598 */                                       out.write("</a>\n\t\t\t\t\t");
/* 2599 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2600 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2604 */                                   if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2605 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                                   }
/*      */                                   
/* 2608 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2609 */                                   out.write("\n\t\t\t\t\t");
/*      */                                   
/* 2611 */                                   WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2612 */                                   _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2613 */                                   _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                   
/* 2615 */                                   _jspx_th_c_005fwhen_005f2.setTest("${(param.all=='true')}");
/* 2616 */                                   int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2617 */                                   if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                     for (;;) {
/* 2619 */                                       out.write("\n\t\t\t\t\t\t");
/* 2620 */                                       out.print(ALERTCONFIG_TEXT);
/* 2621 */                                       out.write("\n\t\t\t\t\t");
/* 2622 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2623 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2627 */                                   if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2628 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                   }
/*      */                                   
/* 2631 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2632 */                                   out.write("\n\t\t\t\t\t");
/*      */                                   
/* 2634 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2635 */                                   _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2636 */                                   _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2637 */                                   int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2638 */                                   if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                     for (;;) {
/* 2640 */                                       out.write("\n\t\t\t\t\t\t<a\n\t\t\t\t\t\t\thref=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 2641 */                                       if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                                         return;
/* 2643 */                                       out.write("\"\n\t\t\t\t\t\t\tclass=\"new-left-links\">");
/* 2644 */                                       out.print(ALERTCONFIG_TEXT);
/* 2645 */                                       out.write("</a>\n\t\t\t\t\t");
/* 2646 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2647 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2651 */                                   if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2652 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                                   }
/*      */                                   
/* 2655 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2656 */                                   out.write("\n\t\t\t\t");
/* 2657 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2658 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2662 */                               if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2663 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                               }
/*      */                               
/* 2666 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2667 */                               out.write("</td>\n\t\t\t</tr>\n\t\t");
/* 2668 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2669 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2673 */                           if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2674 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                           }
/*      */                           
/* 2677 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2678 */                           out.write(10);
/* 2679 */                           out.write(9);
/* 2680 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2681 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2685 */                       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2686 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                       }
/*      */                       
/* 2689 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2690 */                       out.write(10);
/* 2691 */                       out.write(9);
/*      */                       
/* 2693 */                       if ((EnterpriseUtil.isAdminServer()) && (Integer.parseInt(request.getParameter("resourceid")) < EnterpriseUtil.RANGE) && (!request.isUserInRole("OPERATOR")))
/*      */                       {
/*      */ 
/* 2696 */                         out.write("\n\t<tr>\n\t\t<td width=\"87%\" class=\"leftlinkstd\"><a\n\t\t\thref=\"javascript:toggleDiv('edit')\" class=\"new-left-links\">");
/* 2697 */                         out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2698 */                         out.write("</a>\n\t\t</td>\n\t</tr>\n\t");
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/* 2703 */                         out.write(10);
/* 2704 */                         out.write(9);
/*      */                         
/* 2706 */                         PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2707 */                         _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2708 */                         _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */                         
/* 2710 */                         _jspx_th_logic_005fpresent_005f0.setRole("ENTERPRISEADMIN");
/* 2711 */                         int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2712 */                         if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                           for (;;) {
/* 2714 */                             out.write("\n\t\t<tr>\n\t\t\t<td class=\"leftlinkstd\"><a target=\"mas_window\"\n\t\t\t\thref=\"/showresource.do?resourceid=");
/* 2715 */                             out.print(request.getParameter("resourceid"));
/* 2716 */                             out.write("&type=");
/* 2717 */                             out.print(request.getParameter("type"));
/* 2718 */                             out.write("&moname=");
/* 2719 */                             out.print(request.getParameter("moname"));
/* 2720 */                             out.write("&method=showdetails&resourcename=");
/* 2721 */                             out.print(request.getParameter("resourcename"));
/* 2722 */                             out.write("&aam_jump=true&editPage=true\"\n\t\t\t\tclass=\"new-left-links\"> ");
/* 2723 */                             out.print(FormatUtil.getString("am.webclient.dotnet.edit"));
/* 2724 */                             out.write("</a>\n\t\t\t</td>\n\t\t</tr>\n\t");
/* 2725 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2726 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2730 */                         if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2731 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                         }
/*      */                         
/* 2734 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2735 */                         out.write(10);
/* 2736 */                         out.write(9);
/*      */                       }
/* 2738 */                       out.write(10);
/* 2739 */                       out.write(9);
/*      */                       
/* 2741 */                       IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2742 */                       _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2743 */                       _jspx_th_c_005fif_005f2.setParent(null);
/*      */                       
/* 2745 */                       _jspx_th_c_005fif_005f2.setTest("${!empty ADMIN || !empty DEMO}");
/* 2746 */                       int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2747 */                       if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                         for (;;) {
/* 2749 */                           out.write("\n\t\t<tr>\n\t\t\t<td width=\"87%\" class=\"leftlinkstd\"><a\n\t\t\t\thref=\"/manageConfMons.do?haid=");
/* 2750 */                           if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                             return;
/* 2752 */                           out.write("&method=editPreConfMonitor&resourceid=");
/* 2753 */                           if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                             return;
/* 2755 */                           out.write("&type=MYSQLDB:3306&resourcename=");
/* 2756 */                           out.print(request.getParameter("moname"));
/* 2757 */                           out.write("&displayName=");
/* 2758 */                           out.print(request.getParameter("resourcename"));
/* 2759 */                           out.write("&\" class=\"new-left-links\">");
/* 2760 */                           out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2761 */                           out.write("</a>\n\t\t\t</td>\n\t\t</tr>\n\t");
/* 2762 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2763 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2767 */                       if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2768 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                       }
/*      */                       
/* 2771 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2772 */                       out.write("\n\t<script language=\"JavaScript\">\n function confirmDelete()\n  {\n   ");
/* 2773 */                       if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */                         return;
/* 2775 */                       out.write(10);
/* 2776 */                       out.write(9);
/*      */                       
/* 2778 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2779 */                       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2780 */                       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */                       
/* 2782 */                       _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2783 */                       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2784 */                       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                         for (;;) {
/* 2786 */                           out.write("\n  var s = confirm('");
/* 2787 */                           out.print(FormatUtil.getString("am.webclient.common.confirm.delete.text"));
/* 2788 */                           out.write("')\n  if (s)\n \tdocument.location.href=\"/deleteMO.do?method=deleteMO&type=MYSQL-DB-server&select=");
/* 2789 */                           out.print(resourceid);
/* 2790 */                           out.write("\";\n \t ");
/* 2791 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2792 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2796 */                       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2797 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                       }
/*      */                       
/* 2800 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2801 */                       out.write("\n }\n        function confirmManage()\n \t {\n  var s = confirm(\"");
/* 2802 */                       out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 2803 */                       out.write("\");\n  if (s)\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 2804 */                       if (_jspx_meth_c_005fout_005f6(_jspx_page_context))
/*      */                         return;
/* 2806 */                       out.write("\";\n\t }\n\n         function confirmUnManage()\n \t {\n        \t ");
/* 2807 */                       if (_jspx_meth_logic_005fpresent_005f2(_jspx_page_context))
/*      */                         return;
/* 2809 */                       out.write("\n\t\t  var show_msg=\"false\";\n      var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 2810 */                       out.print(request.getParameter("resourceid"));
/* 2811 */                       out.write("; //No i18n\n      $.ajax({\n        type:'POST', //No i18n\n        url:url,\n        async:false,\n        success: function(data)\n        {\n          show_msg=data\n        }\n      });\n      if(show_msg.indexOf(\"true\")>-1)\n      {\n          alert(\"");
/* 2812 */                       out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 2813 */                       out.write("\");\n\t\t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2814 */                       if (_jspx_meth_c_005fout_005f7(_jspx_page_context))
/*      */                         return;
/* 2816 */                       out.write("\";\n       }\n       else { \n\t\t    var s = confirm(\"");
/* 2817 */                       out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 2818 */                       out.write("\");\n    \t\tif (s){\n  \t   \t\t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2819 */                       if (_jspx_meth_c_005fout_005f8(_jspx_page_context))
/*      */                         return;
/* 2821 */                       out.write("\"; //No I18N\n  \t\t\t\t\t}\n  \t\t\t }  \n\t }\n  </script>\n\t");
/*      */                       
/* 2823 */                       IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2824 */                       _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2825 */                       _jspx_th_c_005fif_005f3.setParent(null);
/*      */                       
/* 2827 */                       _jspx_th_c_005fif_005f3.setTest("${!empty ADMIN || !empty DEMO}");
/* 2828 */                       int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2829 */                       if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                         for (;;) {
/* 2831 */                           out.write("\n\t\t<tr>\n\t\t\t<td class=\"leftlinkstd\"><A href=\"javascript:confirmDelete();\"\n\t\t\t\tclass=\"new-left-links\">");
/* 2832 */                           out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 2833 */                           out.write("</A></td>\n\t\t</tr>\n\t");
/* 2834 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2835 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2839 */                       if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2840 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                       }
/*      */                       
/* 2843 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2844 */                       out.write(10);
/* 2845 */                       out.write(10);
/* 2846 */                       out.write(9);
/*      */                       
/* 2848 */                       IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2849 */                       _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2850 */                       _jspx_th_c_005fif_005f4.setParent(null);
/*      */                       
/* 2852 */                       _jspx_th_c_005fif_005f4.setTest("${!empty ADMIN || !empty DEMO}");
/* 2853 */                       int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2854 */                       if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                         for (;;) {
/* 2856 */                           out.write("\n\t\t<tr>\n\t\t\t");
/*      */                           
/* 2858 */                           PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2859 */                           _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 2860 */                           _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fif_005f4);
/*      */                           
/* 2862 */                           _jspx_th_logic_005fpresent_005f3.setRole("DEMO");
/* 2863 */                           int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 2864 */                           if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                             for (;;) {
/* 2866 */                               out.write("\n\t\t\t\t<td class=\"leftlinkstd\"><a href=\"javascript:alertUser()\"\n\t\t\t\t\tclass=\"new-left-links\">");
/* 2867 */                               out.print(FormatUtil.getString("am.webclient.hostleftpage.associatescripts"));
/* 2868 */                               out.write("\n\t\t\t\t</a></td>\n\t\t\t");
/* 2869 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 2870 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2874 */                           if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 2875 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                           }
/*      */                           
/* 2878 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 2879 */                           out.write("\n\t\t\t");
/*      */                           
/* 2881 */                           NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2882 */                           _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2883 */                           _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f4);
/*      */                           
/* 2885 */                           _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 2886 */                           int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2887 */                           if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                             for (;;) {
/* 2889 */                               out.write("\n\t\t\t\t<td class=\"leftlinkstd\"><a\n\t\t\t\t\thref='/showresource.do?type=Script Monitor&method=getAssociateMonitors&hostid=");
/* 2890 */                               out.print(resourceid);
/* 2891 */                               out.write("'\n\t\t\t\t\tclass=\"new-left-links\">");
/* 2892 */                               out.print(FormatUtil.getString("am.webclient.hostleftpage.associatescripts"));
/* 2893 */                               out.write("\n\t\t\t\t</a></td>\n\t\t\t");
/* 2894 */                               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 2895 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2899 */                           if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 2900 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                           }
/*      */                           
/* 2903 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 2904 */                           out.write("\n\t\t</tr>\n\t");
/* 2905 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2906 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2910 */                       if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2911 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                       }
/*      */                       
/* 2914 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2915 */                       out.write("\n\n\n\n\t");
/*      */                       
/* 2917 */                       IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2918 */                       _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2919 */                       _jspx_th_c_005fif_005f5.setParent(null);
/*      */                       
/* 2921 */                       _jspx_th_c_005fif_005f5.setTest("${!empty ADMIN || !empty DEMO || !empty OPERATOR}");
/* 2922 */                       int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2923 */                       if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                         for (;;) {
/* 2925 */                           out.write("\n\t\t<tr>\n\t\t\t");
/*      */                           
/* 2927 */                           if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                           {
/*      */ 
/* 2930 */                             out.write("\n\t\t\t<td class=\"leftlinkstd\"><A href=\"javascript:confirmManage();\"\n\t\t\t\tclass=\"new-left-links\">");
/* 2931 */                             out.print(FormatUtil.getString("Manage"));
/* 2932 */                             out.write("</A></td>\n\t\t\t");
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/*      */ 
/* 2938 */                             out.write("\n\t\t\t<td class=\"leftlinkstd\"><A href=\"javascript:confirmUnManage();\"\n\t\t\t\tclass=\"new-left-links\">");
/* 2939 */                             out.print(FormatUtil.getString("UnManage"));
/* 2940 */                             out.write("</A></td>\n\t\t\t");
/*      */                           }
/*      */                           
/*      */ 
/* 2944 */                           out.write("\n\t\t</tr>\n\t");
/* 2945 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2946 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2950 */                       if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2951 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                       }
/*      */                       
/* 2954 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2955 */                       out.write(10);
/* 2956 */                       out.write(9);
/*      */                       
/* 2958 */                       IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2959 */                       _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2960 */                       _jspx_th_c_005fif_005f6.setParent(null);
/*      */                       
/* 2962 */                       _jspx_th_c_005fif_005f6.setTest("${!empty ADMIN || !empty DEMO}");
/* 2963 */                       int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2964 */                       if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                         for (;;) {
/* 2966 */                           out.write("\n\t\t<tr>\n\t\t\t<td colspan=\"2\" class=\"leftlinkstd\">");
/* 2967 */                           out.print(FaultUtil.getAlertTemplateURL(resourceid, displayname, "MYSQL-DB-server", "MySQL Database Server"));
/* 2968 */                           out.write("\n\t\t\t</td>\n\t\t</tr>\n\t");
/* 2969 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2970 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2974 */                       if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2975 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                       }
/*      */                       
/* 2978 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2979 */                       out.write(10);
/* 2980 */                       out.write(9);
/*      */                       
/* 2982 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2983 */                       _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 2984 */                       _jspx_th_logic_005fnotPresent_005f2.setParent(null);
/*      */                       
/* 2986 */                       _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 2987 */                       int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 2988 */                       if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                         for (;;) {
/* 2990 */                           out.write(10);
/* 2991 */                           out.write(9);
/* 2992 */                           out.write(9);
/*      */                           
/* 2994 */                           String resourceid_poll = request.getParameter("resourceid");
/* 2995 */                           String resourcetype_poll = request.getParameter("type");
/*      */                           
/* 2997 */                           out.write("\n\t\t<tr>\n\t\t\t<td width=\"49%\" height=\"21\" class=\"leftlinkstd\"><a\n\t\t\t\thref=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 2998 */                           out.print(resourceid_poll);
/* 2999 */                           out.write("&resourcetype=");
/* 3000 */                           out.print(resourcetype_poll);
/* 3001 */                           out.write("\"\n\t\t\t\tclass=\"new-left-links\"> ");
/* 3002 */                           out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3003 */                           out.write("</a></td>\n\t\t</tr>\n\n\t");
/* 3004 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3005 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3009 */                       if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 3010 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                       }
/*      */                       
/* 3013 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 3014 */                       out.write(10);
/* 3015 */                       out.write(9);
/*      */                       
/* 3017 */                       PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3018 */                       _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3019 */                       _jspx_th_logic_005fpresent_005f4.setParent(null);
/*      */                       
/* 3021 */                       _jspx_th_logic_005fpresent_005f4.setRole("DEMO");
/* 3022 */                       int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3023 */                       if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                         for (;;) {
/* 3025 */                           out.write("\n\t\t<tr>\n\t\t\t<td width=\"49%\" height=\"21\" class=\"leftlinkstd\"><a\n\t\t\t\thref=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 3026 */                           out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3027 */                           out.write("</a></td>\n\t\t\t</td>\n\t");
/* 3028 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3029 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3033 */                       if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3034 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                       }
/*      */                       
/* 3037 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3038 */                       out.write(10);
/* 3039 */                       out.write(9);
/* 3040 */                       out.write("<!-- $Id$-->\n\n\n  \n");
/*      */                       
/* 3042 */                       if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */                       {
/* 3044 */                         Map<APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 3045 */                         String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                         
/* 3047 */                         String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 3048 */                         String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 3049 */                         if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                         {
/* 3051 */                           if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                           {
/*      */ 
/* 3054 */                             out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3055 */                             out.print(ciInfoUrl);
/* 3056 */                             out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3057 */                             out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3058 */                             out.write("</a></td>");
/* 3059 */                             out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3060 */                             out.print(ciRLUrl);
/* 3061 */                             out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3062 */                             out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3063 */                             out.write("</a></td>");
/* 3064 */                             out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                           }
/* 3068 */                           else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                           {
/*      */ 
/* 3071 */                             out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 3072 */                             out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 3073 */                             out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3074 */                             out.print(ciInfoUrl);
/* 3075 */                             out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 3076 */                             out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3077 */                             out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3078 */                             out.print(ciRLUrl);
/* 3079 */                             out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 3080 */                             out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3081 */                             out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                           }
/*      */                         }
/*      */                       }
/*      */                       
/* 3086 */                       out.write("\n \n \n\n");
/* 3087 */                       out.write("\n</table>\n<br>\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\n\tclass=\"leftmnutables\">\n\t<tr>\n\t\t<td height=\"21\" class=\"leftlinksheading\">");
/* 3088 */                       out.print(FormatUtil.getString("am.webclient.mysql.processlist.show"));
/* 3089 */                       out.write("\n\t\t</td>\n\t</tr>\n\t<tr>\n\t\t<td width=\"87%\" class=\"leftlinkstd\"><a class=\"new-left-links\"\n\t\t\thref=\"javascript:MM_openBrWindow('/MySql.do?method=triggerprocesslist&resourceid=");
/* 3090 */                       out.print(resourceid);
/* 3091 */                       out.write("','MySql_Process_List','scrollbars=yes,resizable=yes')\">");
/* 3092 */                       out.print(FormatUtil.getString("am.webclient.mysql.processlist.view"));
/* 3093 */                       out.write("</a>\n\t\t</td>\n\t</tr>\n</table>\n<br>\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\tclass=\"leftmnutables\">\n\t<tr>\n\t\t<td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3094 */                       out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 3095 */                       out.write("</td>\n\t</tr>\n\t<tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n\t\t<td width=\"49%\"><a\thref=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3096 */                       out.print(request.getParameter("resourceid"));
/* 3097 */                       out.write("&attributeid=116')\"\tclass=\"new-left-links\"> ");
/* 3098 */                       out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3099 */                       out.write(" </a></td>\n\t\t<td width=\"51%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3100 */                       out.print(request.getParameter("resourceid"));
/* 3101 */                       out.write("&attributeid=116')\"\t>");
/* 3102 */                       out.print(getSeverityImageForHealth(healthStatus));
/* 3103 */                       out.write("</a></td>\n\t</tr>\n\t<tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n\t\t<td width=\"49%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3104 */                       out.print(request.getParameter("resourceid"));
/* 3105 */                       out.write("&attributeid=115')\" class=\"new-left-links\">");
/* 3106 */                       out.print(FormatUtil.getString("am.webclient.common.availability.text"));
/* 3107 */                       out.write("</a></td>\n\t\t<td width=\"51%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3108 */                       out.print(request.getParameter("resourceid"));
/* 3109 */                       out.write("&attributeid=115')\">");
/* 3110 */                       out.print(getSeverityImageForAvailability(avaStatus));
/* 3111 */                       out.write("</a></td>\n\t</tr>\n</table>\n\n\n");
/*      */                       
/* 3113 */                       ArrayList menupos = new ArrayList(5);
/* 3114 */                       if (request.isUserInRole("OPERATOR")) {
/* 3115 */                         menupos.add("179");
/* 3116 */                         menupos.add("200");
/* 3117 */                         menupos.add("222");
/* 3118 */                         menupos.add("242");
/* 3119 */                         menupos.add("158");
/*      */                       } else {
/* 3121 */                         menupos.add("361");
/*      */                       }
/* 3123 */                       pageContext.setAttribute("menupos", menupos);
/*      */                       
/* 3125 */                       out.write(10);
/* 3126 */                       out.write(10);
/*      */                     }
/*      */                     
/*      */ 
/* 3130 */                     out.write("\n\n<br>\n");
/* 3131 */                     out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */                     
/*      */ 
/*      */ 
/* 3135 */                     boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 3136 */                     if (EnterpriseUtil.isIt360MSPEdition)
/*      */                     {
/* 3138 */                       showAssociatedBSG = false;
/*      */                       
/*      */ 
/*      */ 
/* 3142 */                       CustomerManagementAPI.getInstance();Properties assBsgSiteProp = CustomerManagementAPI.getSiteProp(request);
/* 3143 */                       CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 3144 */                       String loginName = request.getUserPrincipal().getName();
/* 3145 */                       CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                       
/* 3147 */                       if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                       {
/* 3149 */                         showAssociatedBSG = true;
/*      */                       }
/*      */                     }
/* 3152 */                     String monitorType = request.getParameter("type");
/* 3153 */                     ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 3154 */                     boolean mon = conf1.isConfMonitor(monitorType);
/* 3155 */                     if (showAssociatedBSG)
/*      */                     {
/* 3157 */                       Hashtable associatedmgs = new Hashtable();
/* 3158 */                       String resId = request.getParameter("resourceid");
/* 3159 */                       request.setAttribute("associatedmgs", FaultUtil.getAdminAssociatedMG(resId, request));
/* 3160 */                       if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                       {
/* 3162 */                         mon = false;
/*      */                       }
/*      */                       
/* 3165 */                       if (!mon)
/*      */                       {
/* 3167 */                         out.write(10);
/* 3168 */                         out.write(10);
/*      */                         
/* 3170 */                         IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3171 */                         _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3172 */                         _jspx_th_c_005fif_005f7.setParent(null);
/*      */                         
/* 3174 */                         _jspx_th_c_005fif_005f7.setTest("${!empty associatedmgs}");
/* 3175 */                         int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3176 */                         if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                           for (;;) {
/* 3178 */                             out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3179 */                             out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3180 */                             out.write("</td>\n        </tr>\n        ");
/*      */                             
/* 3182 */                             ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3183 */                             _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3184 */                             _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f7);
/*      */                             
/* 3186 */                             _jspx_th_c_005fforEach_005f0.setVar("ha");
/*      */                             
/* 3188 */                             _jspx_th_c_005fforEach_005f0.setItems("${associatedmgs}");
/*      */                             
/* 3190 */                             _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 3191 */                             int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                             try {
/* 3193 */                               int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3194 */                               if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                 for (;;) {
/* 3196 */                                   out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 3197 */                                   if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3255 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 3256 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/* 3199 */                                   out.write("&method=showApplication\" title=\"");
/* 3200 */                                   if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3255 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 3256 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/* 3202 */                                   out.write("\"  class=\"new-left-links\">\n         ");
/* 3203 */                                   if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3255 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 3256 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/* 3205 */                                   out.write("\n    \t");
/* 3206 */                                   out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3207 */                                   out.write("\n         </a></td>\n        <td>");
/*      */                                   
/* 3209 */                                   PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3210 */                                   _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3211 */                                   _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                   
/* 3213 */                                   _jspx_th_logic_005fpresent_005f5.setRole("ADMIN");
/* 3214 */                                   int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3215 */                                   if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                     for (;;) {
/* 3217 */                                       out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 3218 */                                       if (_jspx_meth_c_005fout_005f12(_jspx_th_logic_005fpresent_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3255 */                                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 3256 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                       }
/* 3220 */                                       out.write(39);
/* 3221 */                                       out.write(44);
/* 3222 */                                       out.write(39);
/* 3223 */                                       out.print(resId);
/* 3224 */                                       out.write(39);
/* 3225 */                                       out.write(44);
/* 3226 */                                       out.write(39);
/* 3227 */                                       out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3228 */                                       out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 3229 */                                       out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3230 */                                       out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 3231 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3232 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3236 */                                   if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3237 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3255 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 3256 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/* 3240 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3241 */                                   out.write("</td>\n        </tr>\n\t");
/* 3242 */                                   int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3243 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3247 */                               if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3255 */                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 3256 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                               }
/*      */                             }
/*      */                             catch (Throwable _jspx_exception)
/*      */                             {
/*      */                               for (;;)
/*      */                               {
/* 3251 */                                 int tmp6496_6495 = 0; int[] tmp6496_6493 = _jspx_push_body_count_c_005fforEach_005f0; int tmp6498_6497 = tmp6496_6493[tmp6496_6495];tmp6496_6493[tmp6496_6495] = (tmp6498_6497 - 1); if (tmp6498_6497 <= 0) break;
/* 3252 */                                 out = _jspx_page_context.popBody(); }
/* 3253 */                               _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                             } finally {
/* 3255 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3256 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                             }
/* 3258 */                             out.write("\n      </table>\n ");
/* 3259 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3260 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3264 */                         if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3265 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                         }
/*      */                         
/* 3268 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3269 */                         out.write(10);
/* 3270 */                         out.write(32);
/*      */                         
/* 3272 */                         IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3273 */                         _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3274 */                         _jspx_th_c_005fif_005f8.setParent(null);
/*      */                         
/* 3276 */                         _jspx_th_c_005fif_005f8.setTest("${empty associatedmgs}");
/* 3277 */                         int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3278 */                         if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                           for (;;) {
/* 3280 */                             out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 3281 */                             out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3282 */                             out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 3283 */                             out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 3284 */                             out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 3285 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3286 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3290 */                         if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3291 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                         }
/*      */                         
/* 3294 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3295 */                         out.write(10);
/* 3296 */                         out.write(32);
/* 3297 */                         out.write(10);
/*      */ 
/*      */                       }
/* 3300 */                       else if (mon)
/*      */                       {
/*      */ 
/*      */ 
/* 3304 */                         out.write(10);
/*      */                         
/* 3306 */                         IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3307 */                         _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3308 */                         _jspx_th_c_005fif_005f9.setParent(null);
/*      */                         
/* 3310 */                         _jspx_th_c_005fif_005f9.setTest("${!empty associatedmgs}");
/* 3311 */                         int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3312 */                         if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                           for (;;) {
/* 3314 */                             out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b><fmt:message key=\"am.webclient.urlmonitor.associatedgroups.text\"/></b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                             
/* 3316 */                             ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3317 */                             _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 3318 */                             _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f9);
/*      */                             
/* 3320 */                             _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                             
/* 3322 */                             _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                             
/* 3324 */                             _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 3325 */                             int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                             try {
/* 3327 */                               int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 3328 */                               if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                                 for (;;) {
/* 3330 */                                   out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 3331 */                                   if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3389 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 3390 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                   }
/* 3333 */                                   out.write("&method=showApplication\" title=\"");
/* 3334 */                                   if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3389 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 3390 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                   }
/* 3336 */                                   out.write("\"  class=\"staticlinks\">\n         ");
/* 3337 */                                   if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3389 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 3390 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                   }
/* 3339 */                                   out.write("\n    \t");
/* 3340 */                                   out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3341 */                                   out.write("</a></span>\t\n\t\t ");
/*      */                                   
/* 3343 */                                   PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3344 */                                   _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 3345 */                                   _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                                   
/* 3347 */                                   _jspx_th_logic_005fpresent_005f6.setRole("ADMIN");
/* 3348 */                                   int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 3349 */                                   if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                                     for (;;) {
/* 3351 */                                       out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 3352 */                                       if (_jspx_meth_c_005fout_005f16(_jspx_th_logic_005fpresent_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3389 */                                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 3390 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                       }
/* 3354 */                                       out.write(39);
/* 3355 */                                       out.write(44);
/* 3356 */                                       out.write(39);
/* 3357 */                                       out.print(resId);
/* 3358 */                                       out.write(39);
/* 3359 */                                       out.write(44);
/* 3360 */                                       out.write(39);
/* 3361 */                                       out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3362 */                                       out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 3363 */                                       out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3364 */                                       out.write("\"  title=\"<fmt:message key=\"am.webclient.quickremoval.monitorgroup.txt\" />\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 3365 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 3366 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3370 */                                   if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 3371 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3389 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 3390 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                   }
/* 3374 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 3375 */                                   out.write("\n\n\t\t \t");
/* 3376 */                                   int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 3377 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3381 */                               if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3389 */                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 3390 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                               }
/*      */                             }
/*      */                             catch (Throwable _jspx_exception)
/*      */                             {
/*      */                               for (;;)
/*      */                               {
/* 3385 */                                 int tmp7448_7447 = 0; int[] tmp7448_7445 = _jspx_push_body_count_c_005fforEach_005f1; int tmp7450_7449 = tmp7448_7445[tmp7448_7447];tmp7448_7445[tmp7448_7447] = (tmp7450_7449 - 1); if (tmp7450_7449 <= 0) break;
/* 3386 */                                 out = _jspx_page_context.popBody(); }
/* 3387 */                               _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                             } finally {
/* 3389 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3390 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                             }
/* 3392 */                             out.write("\n\t\n\t\t\t</td>\n\t ");
/* 3393 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3394 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3398 */                         if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3399 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                         }
/*      */                         
/* 3402 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3403 */                         out.write(10);
/* 3404 */                         out.write(32);
/* 3405 */                         if (_jspx_meth_c_005fif_005f10(_jspx_page_context))
/*      */                           return;
/* 3407 */                         out.write(32);
/* 3408 */                         out.write(10);
/*      */                       }
/*      */                       
/*      */                     }
/* 3412 */                     else if (mon)
/*      */                     {
/*      */ 
/* 3415 */                       out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b><fmt:message key=\"am.webclient.urlmonitor.associatedgroups.text\"/></b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */                     }
/*      */                     
/*      */ 
/* 3419 */                     out.write(9);
/* 3420 */                     out.write(9);
/* 3421 */                     out.write(10);
/*      */                   }
/* 3423 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3424 */         out = _jspx_out;
/* 3425 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3426 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3427 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3430 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3436 */     PageContext pageContext = _jspx_page_context;
/* 3437 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3439 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3440 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3441 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 3443 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3445 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3446 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3447 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3448 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3449 */       return true;
/*      */     }
/* 3451 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3452 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3457 */     PageContext pageContext = _jspx_page_context;
/* 3458 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3460 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3461 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3462 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 3464 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 3465 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3466 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3467 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3468 */       return true;
/*      */     }
/* 3470 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3471 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3476 */     PageContext pageContext = _jspx_page_context;
/* 3477 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3479 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3480 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3481 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 3483 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 3484 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3485 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3486 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3487 */       return true;
/*      */     }
/* 3489 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3490 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3495 */     PageContext pageContext = _jspx_page_context;
/* 3496 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3498 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3499 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3500 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 3502 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 3503 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3504 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3505 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3506 */       return true;
/*      */     }
/* 3508 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3509 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3514 */     PageContext pageContext = _jspx_page_context;
/* 3515 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3517 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3518 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3519 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3521 */     _jspx_th_c_005fout_005f4.setValue("${param.haid}");
/* 3522 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3523 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3524 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3525 */       return true;
/*      */     }
/* 3527 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3528 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3533 */     PageContext pageContext = _jspx_page_context;
/* 3534 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3536 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3537 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3538 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3540 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 3541 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3542 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3543 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3544 */       return true;
/*      */     }
/* 3546 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3547 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3552 */     PageContext pageContext = _jspx_page_context;
/* 3553 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3555 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3556 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3557 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 3559 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 3560 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3561 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 3563 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 3564 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3565 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3569 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3570 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3571 */       return true;
/*      */     }
/* 3573 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3574 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3579 */     PageContext pageContext = _jspx_page_context;
/* 3580 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3582 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3583 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3584 */     _jspx_th_c_005fout_005f6.setParent(null);
/*      */     
/* 3586 */     _jspx_th_c_005fout_005f6.setValue("${param.resourceid}");
/* 3587 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3588 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3589 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3590 */       return true;
/*      */     }
/* 3592 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3593 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3598 */     PageContext pageContext = _jspx_page_context;
/* 3599 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3601 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3602 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3603 */     _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */     
/* 3605 */     _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 3606 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3607 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 3609 */         out.write("\n\t\t\t alertUser();\n\t\t \treturn;\n\t\t");
/* 3610 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3611 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3615 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3616 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3617 */       return true;
/*      */     }
/* 3619 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3620 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3625 */     PageContext pageContext = _jspx_page_context;
/* 3626 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3628 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3629 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 3630 */     _jspx_th_c_005fout_005f7.setParent(null);
/*      */     
/* 3632 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/* 3633 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 3634 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 3635 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3636 */       return true;
/*      */     }
/* 3638 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3639 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3644 */     PageContext pageContext = _jspx_page_context;
/* 3645 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3647 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3648 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 3649 */     _jspx_th_c_005fout_005f8.setParent(null);
/*      */     
/* 3651 */     _jspx_th_c_005fout_005f8.setValue("${param.resourceid}");
/* 3652 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 3653 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 3654 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3655 */       return true;
/*      */     }
/* 3657 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3658 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3663 */     PageContext pageContext = _jspx_page_context;
/* 3664 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3666 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3667 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 3668 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3670 */     _jspx_th_c_005fout_005f9.setValue("${ha.key}");
/* 3671 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 3672 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 3673 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3674 */       return true;
/*      */     }
/* 3676 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3677 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3682 */     PageContext pageContext = _jspx_page_context;
/* 3683 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3685 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3686 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 3687 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3689 */     _jspx_th_c_005fout_005f10.setValue("${ha.value}");
/* 3690 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 3691 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 3692 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3693 */       return true;
/*      */     }
/* 3695 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3696 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3701 */     PageContext pageContext = _jspx_page_context;
/* 3702 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3704 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3705 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3706 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3708 */     _jspx_th_c_005fset_005f0.setVar("monitorName");
/* 3709 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3710 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 3711 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3712 */         out = _jspx_page_context.pushBody();
/* 3713 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 3714 */         _jspx_th_c_005fset_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 3715 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3718 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fset_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3719 */           return true;
/* 3720 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 3721 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3724 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3725 */         out = _jspx_page_context.popBody();
/* 3726 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 3729 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3730 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 3731 */       return true;
/*      */     }
/* 3733 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 3734 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3739 */     PageContext pageContext = _jspx_page_context;
/* 3740 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3742 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3743 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 3744 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 3746 */     _jspx_th_c_005fout_005f11.setValue("${ha.value}");
/* 3747 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 3748 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 3749 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3750 */       return true;
/*      */     }
/* 3752 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3753 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_logic_005fpresent_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3758 */     PageContext pageContext = _jspx_page_context;
/* 3759 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3761 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3762 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 3763 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_logic_005fpresent_005f5);
/*      */     
/* 3765 */     _jspx_th_c_005fout_005f12.setValue("${ha.key}");
/* 3766 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 3767 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 3768 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 3769 */       return true;
/*      */     }
/* 3771 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 3772 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3777 */     PageContext pageContext = _jspx_page_context;
/* 3778 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3780 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3781 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 3782 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3784 */     _jspx_th_c_005fout_005f13.setValue("${ha.key}");
/* 3785 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 3786 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 3787 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 3788 */       return true;
/*      */     }
/* 3790 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 3791 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3796 */     PageContext pageContext = _jspx_page_context;
/* 3797 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3799 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3800 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 3801 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3803 */     _jspx_th_c_005fout_005f14.setValue("${ha.value}");
/* 3804 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 3805 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 3806 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 3807 */       return true;
/*      */     }
/* 3809 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 3810 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3815 */     PageContext pageContext = _jspx_page_context;
/* 3816 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3818 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3819 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 3820 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3822 */     _jspx_th_c_005fset_005f1.setVar("monitorName");
/* 3823 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 3824 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 3825 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3826 */         out = _jspx_page_context.pushBody();
/* 3827 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 3828 */         _jspx_th_c_005fset_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 3829 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3832 */         if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fset_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3833 */           return true;
/* 3834 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 3835 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3838 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3839 */         out = _jspx_page_context.popBody();
/* 3840 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 3843 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 3844 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 3845 */       return true;
/*      */     }
/* 3847 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 3848 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3853 */     PageContext pageContext = _jspx_page_context;
/* 3854 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3856 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3857 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 3858 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 3860 */     _jspx_th_c_005fout_005f15.setValue("${ha.value}");
/* 3861 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 3862 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 3863 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 3864 */       return true;
/*      */     }
/* 3866 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 3867 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_logic_005fpresent_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3872 */     PageContext pageContext = _jspx_page_context;
/* 3873 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3875 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3876 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 3877 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_logic_005fpresent_005f6);
/*      */     
/* 3879 */     _jspx_th_c_005fout_005f16.setValue("${ha.key}");
/* 3880 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 3881 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 3882 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 3883 */       return true;
/*      */     }
/* 3885 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 3886 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3891 */     PageContext pageContext = _jspx_page_context;
/* 3892 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3894 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3895 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3896 */     _jspx_th_c_005fif_005f10.setParent(null);
/*      */     
/* 3898 */     _jspx_th_c_005fif_005f10.setTest("${empty associatedmgs}");
/* 3899 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3900 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 3902 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b><fmt:message key=\"am.webclient.urlmonitor.associatedgroups.text\"/></b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"><fmt:message key=\"am.webclient.urlmonitor.none.text\"/></td>\n\t ");
/* 3903 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3904 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3908 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3909 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3910 */       return true;
/*      */     }
/* 3912 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3913 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MySqlLeftPage_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */