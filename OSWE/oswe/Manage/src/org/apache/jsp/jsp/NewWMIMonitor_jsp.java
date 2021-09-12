/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
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
/*      */ import org.apache.struts.action.DynaActionForm;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.PasswordTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.html.TextareaTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
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
/*      */ public final class NewWMIMonitor_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   70 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   73 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   74 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   75 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   82 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   87 */     ArrayList list = null;
/*   88 */     StringBuffer sbf = new StringBuffer();
/*   89 */     ManagedApplication mo = new ManagedApplication();
/*   90 */     if (distinct)
/*      */     {
/*   92 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   96 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   99 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*  101 */       ArrayList row = (ArrayList)list.get(i);
/*  102 */       sbf.append("<option value='" + row.get(0) + "'>");
/*  103 */       if (distinct) {
/*  104 */         sbf.append(row.get(0));
/*      */       } else
/*  106 */         sbf.append(row.get(1));
/*  107 */       sbf.append("</option>");
/*      */     }
/*      */     
/*  110 */     return sbf.toString(); }
/*      */   
/*  112 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  115 */     if (severity == null)
/*      */     {
/*  117 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  119 */     if (severity.equals("5"))
/*      */     {
/*  121 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  123 */     if (severity.equals("1"))
/*      */     {
/*  125 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  130 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  137 */     if (severity == null)
/*      */     {
/*  139 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  141 */     if (severity.equals("1"))
/*      */     {
/*  143 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  145 */     if (severity.equals("4"))
/*      */     {
/*  147 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  149 */     if (severity.equals("5"))
/*      */     {
/*  151 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  156 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  162 */     if (severity == null)
/*      */     {
/*  164 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  166 */     if (severity.equals("5"))
/*      */     {
/*  168 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  170 */     if (severity.equals("1"))
/*      */     {
/*  172 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  176 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  182 */     if (severity == null)
/*      */     {
/*  184 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  186 */     if (severity.equals("1"))
/*      */     {
/*  188 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  190 */     if (severity.equals("4"))
/*      */     {
/*  192 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  194 */     if (severity.equals("5"))
/*      */     {
/*  196 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  200 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  206 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  212 */     if (severity == 5)
/*      */     {
/*  214 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  216 */     if (severity == 1)
/*      */     {
/*  218 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  223 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  229 */     if (severity == null)
/*      */     {
/*  231 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  233 */     if (severity.equals("5"))
/*      */     {
/*  235 */       if (isAvailability) {
/*  236 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  239 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  242 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  244 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  246 */     if (severity.equals("1"))
/*      */     {
/*  248 */       if (isAvailability) {
/*  249 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  252 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  259 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  266 */     if (severity == null)
/*      */     {
/*  268 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  270 */     if (severity.equals("5"))
/*      */     {
/*  272 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  274 */     if (severity.equals("4"))
/*      */     {
/*  276 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  278 */     if (severity.equals("1"))
/*      */     {
/*  280 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  285 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  291 */     if (severity == null)
/*      */     {
/*  293 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  295 */     if (severity.equals("5"))
/*      */     {
/*  297 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  299 */     if (severity.equals("4"))
/*      */     {
/*  301 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  303 */     if (severity.equals("1"))
/*      */     {
/*  305 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  310 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  317 */     if (severity == null)
/*      */     {
/*  319 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  321 */     if (severity.equals("5"))
/*      */     {
/*  323 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  325 */     if (severity.equals("4"))
/*      */     {
/*  327 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  329 */     if (severity.equals("1"))
/*      */     {
/*  331 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  336 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  344 */     StringBuffer out = new StringBuffer();
/*  345 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  346 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  347 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  348 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  349 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  350 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  351 */     out.append("</tr>");
/*  352 */     out.append("</form></table>");
/*  353 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  360 */     if (val == null)
/*      */     {
/*  362 */       return "-";
/*      */     }
/*      */     
/*  365 */     String ret = FormatUtil.formatNumber(val);
/*  366 */     String troubleshootlink = OEMUtil.getOEMString("company.troubleshoot.link");
/*  367 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  370 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  374 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  382 */     StringBuffer out = new StringBuffer();
/*  383 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  384 */     out.append("<tr>");
/*  385 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  387 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  389 */     out.append("</tr>");
/*  390 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  394 */       if (j % 2 == 0)
/*      */       {
/*  396 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  400 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  403 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  405 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  408 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  412 */       out.append("</tr>");
/*      */     }
/*  414 */     out.append("</table>");
/*  415 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  416 */     out.append("<tr>");
/*  417 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  418 */     out.append("</tr>");
/*  419 */     out.append("</table>");
/*  420 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  426 */     StringBuffer out = new StringBuffer();
/*  427 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  428 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  429 */     out.append("<tr>");
/*  430 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  431 */     out.append("<tr>");
/*  432 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  433 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  434 */     out.append("</tr>");
/*  435 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  438 */       out.append("<tr>");
/*  439 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  440 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  441 */       out.append("</tr>");
/*      */     }
/*      */     
/*  444 */     out.append("</table>");
/*  445 */     out.append("</table>");
/*  446 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  451 */     if (severity.equals("0"))
/*      */     {
/*  453 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  457 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  464 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  477 */     StringBuffer out = new StringBuffer();
/*  478 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  479 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  481 */       out.append("<tr>");
/*  482 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  483 */       out.append("</tr>");
/*      */       
/*      */ 
/*  486 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  488 */         String borderclass = "";
/*      */         
/*      */ 
/*  491 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  493 */         out.append("<tr>");
/*      */         
/*  495 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  496 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  497 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  503 */     out.append("</table><br>");
/*  504 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  505 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  507 */       List sLinks = secondLevelOfLinks[0];
/*  508 */       List sText = secondLevelOfLinks[1];
/*  509 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  512 */         out.append("<tr>");
/*  513 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  514 */         out.append("</tr>");
/*  515 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  517 */           String borderclass = "";
/*      */           
/*      */ 
/*  520 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  522 */           out.append("<tr>");
/*      */           
/*  524 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  525 */           if (sLinks.get(i).toString().length() == 0) {
/*  526 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  529 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  531 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  535 */     out.append("</table>");
/*  536 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  543 */     StringBuffer out = new StringBuffer();
/*  544 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  545 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  547 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  549 */         out.append("<tr>");
/*  550 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  551 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  555 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  557 */           String borderclass = "";
/*      */           
/*      */ 
/*  560 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  562 */           out.append("<tr>");
/*      */           
/*  564 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  565 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  566 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  569 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  572 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  577 */     out.append("</table><br>");
/*  578 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  579 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  581 */       List sLinks = secondLevelOfLinks[0];
/*  582 */       List sText = secondLevelOfLinks[1];
/*  583 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  586 */         out.append("<tr>");
/*  587 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  588 */         out.append("</tr>");
/*  589 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  591 */           String borderclass = "";
/*      */           
/*      */ 
/*  594 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  596 */           out.append("<tr>");
/*      */           
/*  598 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  599 */           if (sLinks.get(i).toString().length() == 0) {
/*  600 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  603 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  605 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  609 */     out.append("</table>");
/*  610 */     return out.toString();
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
/*  623 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  626 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  629 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  632 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  635 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  638 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  641 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  644 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  652 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  657 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  662 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  667 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  672 */     if (val != null)
/*      */     {
/*  674 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  678 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  683 */     if (val == null) {
/*  684 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  688 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  693 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  699 */     if (val != null)
/*      */     {
/*  701 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  705 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  711 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  716 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  720 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  725 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  730 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  735 */     String hostaddress = "";
/*  736 */     String ip = request.getHeader("x-forwarded-for");
/*  737 */     if (ip == null)
/*  738 */       ip = request.getRemoteAddr();
/*  739 */     InetAddress add = null;
/*  740 */     if (ip.equals("127.0.0.1")) {
/*  741 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  745 */       add = InetAddress.getByName(ip);
/*      */     }
/*  747 */     hostaddress = add.getHostName();
/*  748 */     if (hostaddress.indexOf('.') != -1) {
/*  749 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  750 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  754 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  759 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  765 */     if (severity == null)
/*      */     {
/*  767 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  769 */     if (severity.equals("5"))
/*      */     {
/*  771 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  773 */     if (severity.equals("1"))
/*      */     {
/*  775 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  780 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  785 */     ResultSet set = null;
/*  786 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  787 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  789 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  790 */       if (set.next()) { String str1;
/*  791 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  792 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  795 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  800 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  803 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  805 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  809 */     StringBuffer rca = new StringBuffer();
/*  810 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  811 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  814 */     int rcalength = key.length();
/*  815 */     String split = "6. ";
/*  816 */     int splitPresent = key.indexOf(split);
/*  817 */     String div1 = "";String div2 = "";
/*  818 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  820 */       if (rcalength > 180) {
/*  821 */         rca.append("<span class=\"rca-critical-text\">");
/*  822 */         getRCATrimmedText(key, rca);
/*  823 */         rca.append("</span>");
/*      */       } else {
/*  825 */         rca.append("<span class=\"rca-critical-text\">");
/*  826 */         rca.append(key);
/*  827 */         rca.append("</span>");
/*      */       }
/*  829 */       return rca.toString();
/*      */     }
/*  831 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  832 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  833 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  834 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  835 */     getRCATrimmedText(div1, rca);
/*  836 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  839 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  840 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  841 */     getRCATrimmedText(div2, rca);
/*  842 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  844 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  849 */     String[] st = msg.split("<br>");
/*  850 */     for (int i = 0; i < st.length; i++) {
/*  851 */       String s = st[i];
/*  852 */       if (s.length() > 180) {
/*  853 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  855 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  859 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  860 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  862 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  866 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  867 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  868 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  871 */       if (key == null) {
/*  872 */         return ret;
/*      */       }
/*      */       
/*  875 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  876 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  879 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  880 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  881 */       set = AMConnectionPool.executeQueryStmt(query);
/*  882 */       if (set.next())
/*      */       {
/*  884 */         String helpLink = set.getString("LINK");
/*  885 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  888 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  894 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  913 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  904 */         if (set != null) {
/*  905 */           AMConnectionPool.closeStatement(set);
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
/*  919 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
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
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  933 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  934 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  936 */       String entityStr = (String)keys.nextElement();
/*  937 */       String mmessage = temp.getProperty(entityStr);
/*  938 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  939 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  941 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  946 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  956 */     String des = new String();
/*  957 */     while (str.indexOf(find) != -1) {
/*  958 */       des = des + str.substring(0, str.indexOf(find));
/*  959 */       des = des + replace;
/*  960 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  962 */     des = des + str;
/*  963 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  970 */       if (alert == null)
/*      */       {
/*  972 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  974 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  976 */         return "&nbsp;";
/*      */       }
/*      */       
/*  979 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  981 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  984 */       int rcalength = test.length();
/*  985 */       if (rcalength < 300)
/*      */       {
/*  987 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  991 */       StringBuffer out = new StringBuffer();
/*  992 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  993 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  994 */       out.append("</div>");
/*  995 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  996 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  997 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1002 */       ex.printStackTrace();
/*      */     }
/* 1004 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1010 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1015 */     ArrayList attribIDs = new ArrayList();
/* 1016 */     ArrayList resIDs = new ArrayList();
/* 1017 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1019 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1021 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1023 */       String resourceid = "";
/* 1024 */       String resourceType = "";
/* 1025 */       if (type == 2) {
/* 1026 */         resourceid = (String)row.get(0);
/* 1027 */         resourceType = (String)row.get(3);
/*      */       }
/* 1029 */       else if (type == 3) {
/* 1030 */         resourceid = (String)row.get(0);
/* 1031 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1034 */         resourceid = (String)row.get(6);
/* 1035 */         resourceType = (String)row.get(7);
/*      */       }
/* 1037 */       resIDs.add(resourceid);
/* 1038 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1039 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1041 */       String healthentity = null;
/* 1042 */       String availentity = null;
/* 1043 */       if (healthid != null) {
/* 1044 */         healthentity = resourceid + "_" + healthid;
/* 1045 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1048 */       if (availid != null) {
/* 1049 */         availentity = resourceid + "_" + availid;
/* 1050 */         entitylist.add(availentity);
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
/* 1064 */     Properties alert = getStatus(entitylist);
/* 1065 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1070 */     int size = monitorList.size();
/*      */     
/* 1072 */     String[] severity = new String[size];
/*      */     
/* 1074 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1076 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1077 */       String resourceName1 = (String)row1.get(7);
/* 1078 */       String resourceid1 = (String)row1.get(6);
/* 1079 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1080 */       if (severity[j] == null)
/*      */       {
/* 1082 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1086 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1088 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1090 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1093 */         if (sev > 0) {
/* 1094 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1095 */           monitorList.set(k, monitorList.get(j));
/* 1096 */           monitorList.set(j, t);
/* 1097 */           String temp = severity[k];
/* 1098 */           severity[k] = severity[j];
/* 1099 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1105 */     int z = 0;
/* 1106 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1109 */       int i = 0;
/* 1110 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1113 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1117 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1121 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1123 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1126 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1130 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1133 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1134 */       String resourceName1 = (String)row1.get(7);
/* 1135 */       String resourceid1 = (String)row1.get(6);
/* 1136 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1137 */       if (hseverity[j] == null)
/*      */       {
/* 1139 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1144 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1146 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1149 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1152 */         if (hsev > 0) {
/* 1153 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1154 */           monitorList.set(k, monitorList.get(j));
/* 1155 */           monitorList.set(j, t);
/* 1156 */           String temp1 = hseverity[k];
/* 1157 */           hseverity[k] = hseverity[j];
/* 1158 */           hseverity[j] = temp1;
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
/* 1170 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1171 */     boolean forInventory = false;
/* 1172 */     String trdisplay = "none";
/* 1173 */     String plusstyle = "inline";
/* 1174 */     String minusstyle = "none";
/* 1175 */     String haidTopLevel = "";
/* 1176 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1178 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1180 */         haidTopLevel = request.getParameter("haid");
/* 1181 */         forInventory = true;
/* 1182 */         trdisplay = "table-row;";
/* 1183 */         plusstyle = "none";
/* 1184 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1191 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1194 */     ArrayList listtoreturn = new ArrayList();
/* 1195 */     StringBuffer toreturn = new StringBuffer();
/* 1196 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1197 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1198 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1200 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1202 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1203 */       String childresid = (String)singlerow.get(0);
/* 1204 */       String childresname = (String)singlerow.get(1);
/* 1205 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1206 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1207 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1208 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1209 */       String unmanagestatus = (String)singlerow.get(5);
/* 1210 */       String actionstatus = (String)singlerow.get(6);
/* 1211 */       String linkclass = "monitorgp-links";
/* 1212 */       String titleforres = childresname;
/* 1213 */       String titilechildresname = childresname;
/* 1214 */       String childimg = "/images/trcont.png";
/* 1215 */       String flag = "enable";
/* 1216 */       String dcstarted = (String)singlerow.get(8);
/* 1217 */       String configMonitor = "";
/* 1218 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1219 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1221 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1223 */       if (singlerow.get(7) != null)
/*      */       {
/* 1225 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1227 */       String haiGroupType = "0";
/* 1228 */       if ("HAI".equals(childtype))
/*      */       {
/* 1230 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1232 */       childimg = "/images/trend.png";
/* 1233 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1234 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1235 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1237 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1239 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1241 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1242 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1245 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1247 */         linkclass = "disabledtext";
/* 1248 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1250 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1251 */       String availmouseover = "";
/* 1252 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1254 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1256 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1257 */       String healthmouseover = "";
/* 1258 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1260 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1263 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1264 */       int spacing = 0;
/* 1265 */       if (level >= 1)
/*      */       {
/* 1267 */         spacing = 40 * level;
/*      */       }
/* 1269 */       if (childtype.equals("HAI"))
/*      */       {
/* 1271 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1272 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1273 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1275 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1276 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1277 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1278 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1279 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1280 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1281 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1282 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1283 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1284 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1285 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1287 */         if (!forInventory)
/*      */         {
/* 1289 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1292 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1294 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1296 */           actions = editlink + actions;
/*      */         }
/* 1298 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1300 */           actions = actions + associatelink;
/*      */         }
/* 1302 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1303 */         String arrowimg = "";
/* 1304 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1306 */           actions = "";
/* 1307 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1308 */           checkbox = "";
/* 1309 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1311 */         if (isIt360)
/*      */         {
/* 1313 */           actionimg = "";
/* 1314 */           actions = "";
/* 1315 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1316 */           checkbox = "";
/*      */         }
/*      */         
/* 1319 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1321 */           actions = "";
/*      */         }
/* 1323 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1325 */           checkbox = "";
/*      */         }
/*      */         
/* 1328 */         String resourcelink = "";
/*      */         
/* 1330 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1332 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1336 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1339 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1340 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1341 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1342 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1343 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1344 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1345 */         if (!isIt360)
/*      */         {
/* 1347 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1351 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1354 */         toreturn.append("</tr>");
/* 1355 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1357 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1358 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1362 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1363 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1366 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1370 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1372 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1373 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1374 */             toreturn.append(assocMessage);
/* 1375 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1376 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1377 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1378 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1384 */         String resourcelink = null;
/* 1385 */         boolean hideEditLink = false;
/* 1386 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1388 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1389 */           hideEditLink = true;
/* 1390 */           if (isIt360)
/*      */           {
/* 1392 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1396 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1398 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1400 */           hideEditLink = true;
/* 1401 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1402 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1407 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1410 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1411 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1412 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1413 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1414 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1415 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1416 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1417 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1418 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1419 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1420 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1421 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1422 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1424 */         if (hideEditLink)
/*      */         {
/* 1426 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1428 */         if (!forInventory)
/*      */         {
/* 1430 */           removefromgroup = "";
/*      */         }
/* 1432 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1433 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1434 */           actions = actions + configcustomfields;
/*      */         }
/* 1436 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1438 */           actions = editlink + actions;
/*      */         }
/* 1440 */         String managedLink = "";
/* 1441 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1443 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1444 */           actions = "";
/* 1445 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1446 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1449 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1451 */           checkbox = "";
/*      */         }
/*      */         
/* 1454 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1456 */           actions = "";
/*      */         }
/* 1458 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1459 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1460 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1461 */         if (isIt360)
/*      */         {
/* 1463 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1467 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1469 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1470 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1471 */         if (!isIt360)
/*      */         {
/* 1473 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1477 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1479 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1482 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1489 */       StringBuilder toreturn = new StringBuilder();
/* 1490 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1491 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1492 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1493 */       String title = "";
/* 1494 */       message = EnterpriseUtil.decodeString(message);
/* 1495 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1496 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1497 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1499 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1501 */       else if ("5".equals(severity))
/*      */       {
/* 1503 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1507 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1509 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1510 */       toreturn.append(v);
/*      */       
/* 1512 */       toreturn.append(link);
/* 1513 */       if (severity == null)
/*      */       {
/* 1515 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1517 */       else if (severity.equals("5"))
/*      */       {
/* 1519 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1521 */       else if (severity.equals("4"))
/*      */       {
/* 1523 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1525 */       else if (severity.equals("1"))
/*      */       {
/* 1527 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1532 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1534 */       toreturn.append("</a>");
/* 1535 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1539 */       ex.printStackTrace();
/*      */     }
/* 1541 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1548 */       StringBuilder toreturn = new StringBuilder();
/* 1549 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1550 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1551 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1552 */       if (message == null)
/*      */       {
/* 1554 */         message = "";
/*      */       }
/*      */       
/* 1557 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1558 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1560 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1561 */       toreturn.append(v);
/*      */       
/* 1563 */       toreturn.append(link);
/*      */       
/* 1565 */       if (severity == null)
/*      */       {
/* 1567 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1569 */       else if (severity.equals("5"))
/*      */       {
/* 1571 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1573 */       else if (severity.equals("1"))
/*      */       {
/* 1575 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1580 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1582 */       toreturn.append("</a>");
/* 1583 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1589 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1592 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1593 */     if (invokeActions != null) {
/* 1594 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1595 */       while (iterator.hasNext()) {
/* 1596 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1597 */         if (actionmap.containsKey(actionid)) {
/* 1598 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1603 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1607 */     String actionLink = "";
/* 1608 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1609 */     String query = "";
/* 1610 */     ResultSet rs = null;
/* 1611 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1612 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1613 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1614 */       actionLink = "method=" + methodName;
/*      */     }
/* 1616 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1617 */       actionLink = methodName;
/*      */     }
/* 1619 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1620 */     Iterator itr = methodarglist.iterator();
/* 1621 */     boolean isfirstparam = true;
/* 1622 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1623 */     while (itr.hasNext()) {
/* 1624 */       HashMap argmap = (HashMap)itr.next();
/* 1625 */       String argtype = (String)argmap.get("TYPE");
/* 1626 */       String argname = (String)argmap.get("IDENTITY");
/* 1627 */       String paramname = (String)argmap.get("PARAMETER");
/* 1628 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1629 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1630 */         isfirstparam = false;
/* 1631 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1633 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1637 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1641 */         actionLink = actionLink + "&";
/*      */       }
/* 1643 */       String paramValue = null;
/* 1644 */       String tempargname = argname;
/* 1645 */       if (commonValues.getProperty(tempargname) != null) {
/* 1646 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1649 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1650 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1651 */           if (dbType.equals("mysql")) {
/* 1652 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1655 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1657 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1659 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1660 */             if (rs.next()) {
/* 1661 */               paramValue = rs.getString("VALUE");
/* 1662 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1666 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1670 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1673 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1678 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1679 */           paramValue = rowId;
/*      */         }
/* 1681 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1682 */           paramValue = managedObjectName;
/*      */         }
/* 1684 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1685 */           paramValue = resID;
/*      */         }
/* 1687 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1688 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1691 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1693 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1694 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1695 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1697 */     return actionLink;
/*      */   }
/*      */   
/* 1700 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1701 */     String dependentAttribute = null;
/* 1702 */     String align = "left";
/*      */     
/* 1704 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1705 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1706 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1707 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1708 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1709 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1710 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1711 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1712 */       align = "center";
/*      */     }
/*      */     
/* 1715 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1716 */     String actualdata = "";
/*      */     
/* 1718 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1719 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1720 */         actualdata = availValue;
/*      */       }
/* 1722 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1723 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1727 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1728 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1731 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1737 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1738 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1739 */       toreturn.append("<table>");
/* 1740 */       toreturn.append("<tr>");
/* 1741 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1742 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1743 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1744 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1745 */         String toolTip = "";
/* 1746 */         String hideClass = "";
/* 1747 */         String textStyle = "";
/* 1748 */         boolean isreferenced = true;
/* 1749 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1750 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1751 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1752 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1754 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1755 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1756 */           while (valueList.hasMoreTokens()) {
/* 1757 */             String dependentVal = valueList.nextToken();
/* 1758 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1759 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1760 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1762 */               toolTip = "";
/* 1763 */               hideClass = "";
/* 1764 */               isreferenced = false;
/* 1765 */               textStyle = "disabledtext";
/* 1766 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1770 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1771 */           toolTip = "";
/* 1772 */           hideClass = "";
/* 1773 */           isreferenced = false;
/* 1774 */           textStyle = "disabledtext";
/* 1775 */           if (dependentImageMap != null) {
/* 1776 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1777 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1780 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1784 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1785 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1786 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1787 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1788 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1789 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1791 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1792 */           if (isreferenced) {
/* 1793 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1797 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1798 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1799 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1800 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1801 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1802 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1804 */           toreturn.append("</span>");
/* 1805 */           toreturn.append("</a>");
/* 1806 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1809 */       toreturn.append("</tr>");
/* 1810 */       toreturn.append("</table>");
/* 1811 */       toreturn.append("</td>");
/*      */     } else {
/* 1813 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1816 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1820 */     String colTime = null;
/* 1821 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1822 */     if ((rows != null) && (rows.size() > 0)) {
/* 1823 */       Iterator<String> itr = rows.iterator();
/* 1824 */       String maxColQuery = "";
/* 1825 */       for (;;) { if (itr.hasNext()) {
/* 1826 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1827 */           ResultSet maxCol = null;
/*      */           try {
/* 1829 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1830 */             while (maxCol.next()) {
/* 1831 */               if (colTime == null) {
/* 1832 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1835 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1844 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1846 */               if (maxCol != null)
/* 1847 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1849 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1844 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1846 */               if (maxCol != null)
/* 1847 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1849 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1854 */     return colTime;
/*      */   }
/*      */   
/* 1857 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1858 */     tablename = null;
/* 1859 */     ResultSet rsTable = null;
/* 1860 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1862 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1863 */       while (rsTable.next()) {
/* 1864 */         tablename = rsTable.getString("DATATABLE");
/* 1865 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1866 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1879 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1870 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1873 */         if (rsTable != null)
/* 1874 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1876 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1882 */     String argsList = "";
/* 1883 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1885 */       if (showArgsMap.get(row) != null) {
/* 1886 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1887 */         if (showArgslist != null) {
/* 1888 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1889 */             if (argsList.trim().equals("")) {
/* 1890 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1893 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1900 */       e.printStackTrace();
/* 1901 */       return "";
/*      */     }
/* 1903 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1908 */     String argsList = "";
/* 1909 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1912 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1914 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1915 */         if (hideArgsList != null)
/*      */         {
/* 1917 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1919 */             if (argsList.trim().equals(""))
/*      */             {
/* 1921 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1925 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1933 */       ex.printStackTrace();
/*      */     }
/* 1935 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1939 */     StringBuilder toreturn = new StringBuilder();
/* 1940 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1947 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1948 */       Iterator itr = tActionList.iterator();
/* 1949 */       while (itr.hasNext()) {
/* 1950 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1951 */         String confirmmsg = "";
/* 1952 */         String link = "";
/* 1953 */         String isJSP = "NO";
/* 1954 */         HashMap tactionMap = (HashMap)itr.next();
/* 1955 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1956 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1957 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1958 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1959 */           (actionmap.containsKey(actionId))) {
/* 1960 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1961 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1962 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1963 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1964 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1966 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1972 */           if (isTableAction) {
/* 1973 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1976 */             tableName = "Link";
/* 1977 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1978 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1979 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1980 */             toreturn.append("</a></td>");
/*      */           }
/* 1982 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1983 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1984 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1985 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1991 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1997 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1999 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 2000 */       Properties prop = (Properties)node.getUserObject();
/* 2001 */       String mgID = prop.getProperty("label");
/* 2002 */       String mgName = prop.getProperty("value");
/* 2003 */       String isParent = prop.getProperty("isParent");
/* 2004 */       int mgIDint = Integer.parseInt(mgID);
/* 2005 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 2007 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 2009 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 2010 */       if (node.getChildCount() > 0)
/*      */       {
/* 2012 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2014 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2016 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2018 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2022 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2027 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2029 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2031 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2033 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2037 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2040 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2041 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2043 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2047 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2049 */       if (node.getChildCount() > 0)
/*      */       {
/* 2051 */         builder.append("<UL>");
/* 2052 */         printMGTree(node, builder);
/* 2053 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2058 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2059 */     StringBuffer toReturn = new StringBuffer();
/* 2060 */     String table = "-";
/*      */     try {
/* 2062 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2063 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2064 */       float total = 0.0F;
/* 2065 */       while (it.hasNext()) {
/* 2066 */         String attName = (String)it.next();
/* 2067 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2068 */         boolean roundOffData = false;
/* 2069 */         if ((data != null) && (!data.equals(""))) {
/* 2070 */           if (data.indexOf(",") != -1) {
/* 2071 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2074 */             float value = Float.parseFloat(data);
/* 2075 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2078 */             total += value;
/* 2079 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2082 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2087 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2088 */       while (attVsWidthList.hasNext()) {
/* 2089 */         String attName = (String)attVsWidthList.next();
/* 2090 */         String data = (String)attVsWidthProps.get(attName);
/* 2091 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2092 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2093 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2094 */         String className = (String)graphDetails.get("ClassName");
/* 2095 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2096 */         if (percentage < 1.0F)
/*      */         {
/* 2098 */           data = percentage + "";
/*      */         }
/* 2100 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2102 */       if (toReturn.length() > 0) {
/* 2103 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2107 */       e.printStackTrace();
/*      */     }
/* 2109 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2115 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2116 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2117 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2118 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2119 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2120 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2121 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2122 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2123 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2126 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2127 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2128 */       splitvalues[0] = multiplecondition.toString();
/* 2129 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2132 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2137 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2138 */     if (thresholdType != 3) {
/* 2139 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2140 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2141 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2142 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2143 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2144 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2146 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2147 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2148 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2149 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2150 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2151 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2153 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2154 */     if (updateSelected != null) {
/* 2155 */       updateSelected[0] = "selected";
/*      */     }
/* 2157 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2162 */       StringBuffer toreturn = new StringBuffer("");
/* 2163 */       if (commaSeparatedMsgId != null) {
/* 2164 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2165 */         int count = 0;
/* 2166 */         while (msgids.hasMoreTokens()) {
/* 2167 */           String id = msgids.nextToken();
/* 2168 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2169 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2170 */           count++;
/* 2171 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2172 */             if (toreturn.length() == 0) {
/* 2173 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2175 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2176 */             if (!image.trim().equals("")) {
/* 2177 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2179 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2180 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2183 */         if (toreturn.length() > 0) {
/* 2184 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2188 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2191 */       e.printStackTrace(); }
/* 2192 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList getMGroupsCreatedInAdminServer(ArrayList aListOfAllMonitorGroups)
/*      */   {
/* 2201 */     ArrayList aListAdminMonitorGrps = new ArrayList();
/*      */     try {
/* 2203 */       for (int i = 0; i < aListOfAllMonitorGroups.size(); i++) {
/* 2204 */         ArrayList innerList = (ArrayList)aListOfAllMonitorGroups.get(i);
/* 2205 */         if ((innerList != null) && (innerList.size() >= 2))
/*      */         {
/*      */           try
/*      */           {
/* 2209 */             String strMgId = (String)innerList.get(1);
/* 2210 */             int mgId = Integer.parseInt(strMgId);
/* 2211 */             if (mgId < 10000000) {
/* 2212 */               aListAdminMonitorGrps.add(innerList);
/*      */             }
/* 2214 */             String grpCreatedMasName = CommDBUtil.getManagedServerNameWithPort(strMgId);
/* 2215 */             innerList.add(grpCreatedMasName);
/*      */           }
/*      */           catch (Exception ex1) {}
/*      */         }
/*      */       }
/*      */     } catch (Exception ex2) {
/* 2221 */       ex2.printStackTrace();
/*      */     }
/* 2223 */     return aListAdminMonitorGrps;
/*      */   }
/*      */   
/* 2226 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2232 */   private static Map<String, Long> _jspx_dependants = new HashMap(5);
/* 2233 */   static { _jspx_dependants.put("/jsp/includes/monitorGroups.jsp", Long.valueOf(1473429417000L));
/* 2234 */     _jspx_dependants.put("/jsp/includes/newresourcetypes.jspf", Long.valueOf(1473429417000L));
/* 2235 */     _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/* 2236 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2237 */     _jspx_dependants.put("/jsp/includes/MonitorDiscoveryStatus.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2272 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2276 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2277 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2278 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2279 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2280 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2281 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2282 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2283 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2284 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2285 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2286 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2287 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2288 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2289 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2290 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2291 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2292 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2293 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2294 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2295 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2296 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2297 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2298 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2299 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2300 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2301 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2302 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2303 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2304 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2308 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2309 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2310 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2311 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2312 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2313 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/* 2314 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2315 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2316 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2317 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/* 2318 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2319 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2320 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2321 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2322 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2323 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.release();
/* 2324 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/* 2325 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fmaxlength_005fnobody.release();
/* 2326 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols.release();
/* 2327 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2328 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/* 2329 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.release();
/* 2330 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2331 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2332 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2333 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2334 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2341 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2344 */     JspWriter out = null;
/* 2345 */     Object page = this;
/* 2346 */     JspWriter _jspx_out = null;
/* 2347 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2351 */       response.setContentType("text/html;charset=UTF-8");
/* 2352 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2354 */       _jspx_page_context = pageContext;
/* 2355 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2356 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2357 */       session = pageContext.getSession();
/* 2358 */       out = pageContext.getOut();
/* 2359 */       _jspx_out = out;
/*      */       
/* 2361 */       out.write("<!DOCTYPE html>\n");
/* 2362 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2363 */       out.write(10);
/*      */       
/* 2365 */       request.setAttribute("HelpKey", "New WMI Monitor");
/*      */       
/* 2367 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2368 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2370 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2371 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2372 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2374 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2376 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2378 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2380 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2381 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2382 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2383 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2386 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2387 */         String available = null;
/* 2388 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2389 */         out.write(10);
/*      */         
/* 2391 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2392 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2393 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2395 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2397 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2399 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2401 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2402 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2403 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2404 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2407 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2408 */           String unavailable = null;
/* 2409 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2410 */           out.write(10);
/*      */           
/* 2412 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2413 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2414 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2416 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2418 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2420 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2422 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2423 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2424 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2425 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2428 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2429 */             String unmanaged = null;
/* 2430 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2431 */             out.write(10);
/*      */             
/* 2433 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2434 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2435 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2437 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2439 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2441 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2443 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2444 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2445 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2446 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2449 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2450 */               String scheduled = null;
/* 2451 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2452 */               out.write(10);
/*      */               
/* 2454 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2455 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2456 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2458 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2460 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2462 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2464 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2465 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2466 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2467 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2470 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2471 */                 String critical = null;
/* 2472 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2473 */                 out.write(10);
/*      */                 
/* 2475 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2476 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2477 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2479 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2481 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2483 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2485 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2486 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2487 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2488 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2491 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2492 */                   String clear = null;
/* 2493 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2494 */                   out.write(10);
/*      */                   
/* 2496 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2497 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2498 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2500 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2502 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2504 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2506 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2507 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2508 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2509 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2512 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2513 */                     String warning = null;
/* 2514 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2515 */                     out.write(10);
/* 2516 */                     out.write(10);
/*      */                     
/* 2518 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2519 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2521 */                     out.write(10);
/* 2522 */                     out.write(10);
/* 2523 */                     out.write(10);
/* 2524 */                     out.write(10);
/* 2525 */                     out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 2526 */                     if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                       return;
/* 2528 */                     out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/* 2529 */                     out.write(10);
/* 2530 */                     out.write(10);
/* 2531 */                     ManagedApplication mo = null;
/* 2532 */                     mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 1);
/* 2533 */                     if (mo == null) {
/* 2534 */                       mo = new ManagedApplication();
/* 2535 */                       _jspx_page_context.setAttribute("mo", mo, 1);
/*      */                     }
/* 2537 */                     out.write(10);
/* 2538 */                     out.write(10);
/* 2539 */                     out.write(10);
/* 2540 */                     out.write("<!--$Id$-->\n\n\n\n");
/*      */                     
/*      */                     try
/*      */                     {
/* 2544 */                       boolean isprivilege = false;
/* 2545 */                       if (com.adventnet.appmanager.struts.beans.ClientDBUtil.isPrivilegedUser(request)) {
/* 2546 */                         isprivilege = true;
/*      */                       }
/* 2548 */                       request.setAttribute("checkForMonitorGroup", Boolean.valueOf(isprivilege));
/*      */                       
/*      */ 
/* 2551 */                       ArrayList dynamicSites = AlarmUtil.getSiteMonitorGroups();
/* 2552 */                       if (dynamicSites != null)
/*      */                       {
/* 2554 */                         request.setAttribute("dynamicSites", dynamicSites);
/*      */                       }
/*      */                       
/* 2557 */                       ArrayList mgList = new ArrayList();
/* 2558 */                       if (EnterpriseUtil.isIt360MSPEdition())
/*      */                       {
/* 2560 */                         AMActionForm form = (AMActionForm)request.getAttribute("AMActionForm");
/* 2561 */                         ArrayList orgs = AlarmUtil.getCustomerNames();
/*      */                         
/* 2563 */                         if (orgs != null)
/*      */                         {
/* 2565 */                           request.setAttribute("customers", orgs);
/*      */                         }
/*      */                         
/*      */ 
/* 2569 */                         if (form != null)
/*      */                         {
/* 2571 */                           String customerName = form.getOrganization();
/* 2572 */                           if (customerName != null)
/*      */                           {
/* 2574 */                             mgList = AlarmUtil.getSiteMonitorGroups(customerName);
/*      */                           }
/*      */                           
/*      */                         }
/*      */                         
/*      */ 
/*      */                       }
/* 2581 */                       else if (isprivilege)
/*      */                       {
/* 2583 */                         mgList = AlarmUtil.getConfiguredGroups(request, false, false, true);
/*      */                       }
/*      */                       else
/*      */                       {
/* 2587 */                         mgList = AlarmUtil.getConfiguredGroups(null, false, false, true);
/*      */                       }
/*      */                       
/* 2590 */                       if (mgList != null)
/*      */                       {
/* 2592 */                         request.setAttribute("applications", mgList);
/* 2593 */                         if (EnterpriseUtil.isAdminServer()) {
/* 2594 */                           ArrayList adminMGroups = getMGroupsCreatedInAdminServer(mgList);
/* 2595 */                           request.setAttribute("AllMonitorGroupsInAdminServer", mgList);
/* 2596 */                           request.setAttribute("MonitorGroupsCreatedInAdminServer", adminMGroups);
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 2602 */                       e.printStackTrace();
/*      */                     }
/*      */                     
/* 2605 */                     out.write(10);
/* 2606 */                     out.write(10);
/* 2607 */                     out.write(10);
/*      */                     
/*      */ 
/*      */ 
/* 2611 */                     String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/* 2612 */                     String title = "Generic WMI";
/* 2613 */                     if (request.getParameter("type") != null) {
/* 2614 */                       title = request.getParameter("type");
/*      */                     }
/* 2616 */                     if (hideFieldsForIT360 == null)
/*      */                     {
/* 2618 */                       hideFieldsForIT360 = (String)request.getAttribute("hideFieldsForIT360");
/*      */                     }
/* 2620 */                     String isDiscComplete = request.getParameter("isDiscoveryComplete");
/* 2621 */                     boolean isDiscoveryComplete = false;
/* 2622 */                     if ((isDiscComplete != null) && (isDiscComplete.equals("true")))
/*      */                     {
/* 2624 */                       isDiscoveryComplete = true;
/*      */                     }
/* 2626 */                     String resourceid = request.getParameter("resourceid");
/* 2627 */                     String haid = request.getParameter("haid");
/*      */                     
/* 2629 */                     out.write(10);
/*      */                     
/* 2631 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2632 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2633 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2635 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2636 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2637 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2639 */                         out.write(32);
/* 2640 */                         if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2642 */                         out.write(10);
/* 2643 */                         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2645 */                         out.write(" \n\n\n");
/*      */                         
/* 2647 */                         PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2648 */                         _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2649 */                         _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2651 */                         _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*      */                         
/* 2653 */                         _jspx_th_tiles_005fput_005f2.setType("string");
/* 2654 */                         int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2655 */                         if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2656 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2657 */                             out = _jspx_page_context.pushBody();
/* 2658 */                             _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2659 */                             _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2662 */                             out.write(32);
/*      */                             
/* 2664 */                             FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/* 2665 */                             _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2666 */                             _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                             
/* 2668 */                             _jspx_th_html_005fform_005f0.setAction("/WMIPerfCounters.do");
/* 2669 */                             int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2670 */                             if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                               for (;;) {
/* 2672 */                                 out.write(10);
/*      */                                 
/* 2674 */                                 NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2675 */                                 _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2676 */                                 _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 2678 */                                 _jspx_th_logic_005fnotEmpty_005f0.setName("discoverystatus");
/* 2679 */                                 int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2680 */                                 if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                                   for (;;) {
/* 2682 */                                     out.write(32);
/* 2683 */                                     out.write("<!--$Id$-->\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/diagnosepage.js\"></SCRIPT>\n");
/*      */                                     
/* 2685 */                                     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2686 */                                     _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 2687 */                                     _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                     
/* 2689 */                                     _jspx_th_logic_005fnotEmpty_005f1.setName("discoverystatus");
/* 2690 */                                     int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 2691 */                                     if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                                       for (;;) {
/* 2693 */                                         out.write(10);
/*      */                                         
/*      */ 
/* 2696 */                                         if ((request.getAttribute("type") == null) || ((!request.getAttribute("type").equals("Script Monitor")) && ((request.getAttribute("basetype") == null) || ((request.getAttribute("basetype") != null) && (!request.getAttribute("basetype").equals("Script Monitor")))) && (!request.getAttribute("type").equals("QENGINE")) && (!request.getAttribute("type").equals("Web Service")) && (!request.getAttribute("type").equals("file")) && (!request.getAttribute("type").equals("directory")) && (!request.getAttribute("type").equals("File System Monitor")) && (!request.getAttribute("type").equals("Ping Monitor")) && (!request.getAttribute("type").equals("SAP-CCMS"))))
/*      */                                         {
/*      */ 
/* 2699 */                                           out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n  <tr>\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2700 */                                           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 2701 */                                           out.write("</span> </td>\n    <td width=\"7%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2702 */                                           out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverport"));
/* 2703 */                                           out.write("\n      </span> </td>\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2704 */                                           out.print(FormatUtil.getString("am.webclient.oracle.Status"));
/* 2705 */                                           out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2706 */                                           out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 2707 */                                           out.write("\n </span></td>\n  <tr>\n  ");
/*      */                                           
/* 2709 */                                           int failedNumber = 1;
/*      */                                           
/* 2711 */                                           out.write(10);
/*      */                                           
/* 2713 */                                           IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2714 */                                           _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2715 */                                           _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                                           
/* 2717 */                                           _jspx_th_logic_005fiterate_005f0.setName("discoverystatus");
/*      */                                           
/* 2719 */                                           _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                                           
/* 2721 */                                           _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*      */                                           
/* 2723 */                                           _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 2724 */                                           int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2725 */                                           if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2726 */                                             ArrayList row = null;
/* 2727 */                                             Integer i = null;
/* 2728 */                                             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2729 */                                               out = _jspx_page_context.pushBody();
/* 2730 */                                               _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2731 */                                               _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                             }
/* 2733 */                                             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2734 */                                             i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                             for (;;) {
/* 2736 */                                               out.write("\n<tr>\n<td height=\"18\" class=\"bodytext\">");
/* 2737 */                                               out.print(row.get(0));
/* 2738 */                                               out.write("</td>\n<td height=\"18\" class=\"bodytext\">");
/* 2739 */                                               out.print(row.get(1));
/* 2740 */                                               out.write("</td>\n\n    <td height=\"18\" class=\"bodytext\">\n      ");
/*      */                                               
/* 2742 */                                               if ((row.get(2).equals("Success")) || (row.get(2).equals("Present")))
/*      */                                               {
/* 2744 */                                                 request.setAttribute("isDiscoverySuccess", "true");
/*      */                                                 
/* 2746 */                                                 out.write("\n      <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 2747 */                                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2748 */                                                 out.write("\" align=\"absmiddle\">\n      ");
/*      */ 
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 2753 */                                                 request.setAttribute("isDiscoverySuccess", "false");
/*      */                                                 
/* 2755 */                                                 out.write("\n      <img alt=\"");
/* 2756 */                                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.notiniatedimagetip.text"));
/* 2757 */                                                 out.write("\" src=\"/images/icon_monitor_failure.gif\" align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                                               }
/*      */                                               
/*      */ 
/* 2761 */                                               out.write("\n      <span class=\"bodytextbold\">");
/* 2762 */                                               out.print(FormatUtil.getString(String.valueOf(row.get(2))));
/* 2763 */                                               out.write("</span> </td>\n\n      ");
/*      */                                               
/* 2765 */                                               pageContext.setAttribute("ret_msg", String.valueOf(row.get(3)));
/*      */                                               
/* 2767 */                                               out.write("\n                     ");
/*      */                                               
/* 2769 */                                               IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2770 */                                               _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2771 */                                               _jspx_th_c_005fif_005f0.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                               
/* 2773 */                                               _jspx_th_c_005fif_005f0.setTest("${ret_msg=='Monitoring Initiated Succesfully'}");
/* 2774 */                                               int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2775 */                                               if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                                                 for (;;) {
/* 2777 */                                                   out.write("\n                           <td height=\"18\" class=\"bodytext\">");
/* 2778 */                                                   out.print(FormatUtil.getString("am.webclient.discovery.host.successfullyiniated.text"));
/* 2779 */                                                   out.write("\n                                 ");
/* 2780 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2781 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 2785 */                                               if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2786 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                                               }
/*      */                                               
/* 2789 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2790 */                                               out.write("\n                                       ");
/*      */                                               
/* 2792 */                                               IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2793 */                                               _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2794 */                                               _jspx_th_c_005fif_005f1.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                               
/* 2796 */                                               _jspx_th_c_005fif_005f1.setTest("${ret_msg!='Monitoring Initiated Succesfully'}");
/* 2797 */                                               int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2798 */                                               if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                                 for (;;) {
/* 2800 */                                                   out.write("\n                                             <td height=\"18\" class=\"bodytext\">");
/* 2801 */                                                   out.print(row.get(3));
/* 2802 */                                                   out.write("\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                                   
/* 2804 */                                                   if ((request.getAttribute("type") != null) && (!request.getAttribute("type").equals("All")) && (!request.getAttribute("type").equals("TELNET")) && (!request.getAttribute("type").equals("SNMP")) && (!request.getAttribute("type").equals("SERVICE")) && (!request.getAttribute("type").equals("JMX1.2-MX4J-RMI")) && (!request.getAttribute("type").equals("JDK1.5")) && (!request.getAttribute("type").equals("RMI")) && (!request.getAttribute("type").equals("PHP")) && (!request.getAttribute("type").equals("MAIL")) && (!request.getAttribute("type").equals("ORACLEDB")) && (!request.getAttribute("type").equals("MYSQLDB")) && (!request.getAttribute("type").equals("MSSQLDB")) && (!request.getAttribute("type").equals("DB2")) && (!request.getAttribute("type").equals("WTA")) && (!request.getAttribute("type").equals("SAP")))
/*      */                                                   {
/* 2806 */                                                     if (((String)pageContext.getAttribute("ret_msg")).indexOf(FormatUtil.getString("am.webclient.discovery.host.monitoradded.text")) == -1)
/*      */                                                     {
/* 2808 */                                                       String fWhr = request.getParameter("hideFieldsForIT360");
/* 2809 */                                                       if (fWhr == null)
/*      */                                                       {
/* 2811 */                                                         fWhr = (String)request.getAttribute("hideFieldsForIT360");
/*      */                                                       }
/*      */                                                       
/* 2814 */                                                       if (((fWhr == null) || (!fWhr.equals("true"))) && 
/* 2815 */                                                         (!request.getAttribute("type").equals("SYBASEDB")))
/*      */                                                       {
/* 2817 */                                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t &nbsp;&nbsp;&nbsp;&nbsp;<a class=\"bodytext\" href=\"javascript:void(0)\" onClick=\"getAllDetailsOfHost()\">");
/* 2818 */                                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.diagnose.link"));
/* 2819 */                                                         out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                                       }
/*      */                                                     } }
/* 2822 */                                                   if ((request.getAttribute("showSupportMessage") != null) && (failedNumber == 1) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */                                                   {
/* 2824 */                                                     failedNumber++;
/*      */                                                     
/*      */ 
/* 2827 */                                                     out.write("\n\t\t\t\t\t\t\t\t\t\t<br>");
/* 2828 */                                                     out.print(FormatUtil.getString("am.webclient.discovery.host.support.link", new String[] { OEMUtil.getOEMString("product.talkback.mailid"), OEMUtil.getOEMString("product.tollfree.number") }));
/* 2829 */                                                     out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                                   }
/* 2831 */                                                   out.write("\n                                                   ");
/* 2832 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2833 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 2837 */                                               if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2838 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                                               }
/*      */                                               
/* 2841 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2842 */                                               out.write(10);
/* 2843 */                                               out.write(10);
/* 2844 */                                               out.write(10);
/*      */                                               
/*      */ 
/* 2847 */                                               if (row.size() > 4)
/*      */                                               {
/*      */ 
/* 2850 */                                                 out.write("<br>\n");
/* 2851 */                                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)row.get(4) }));
/* 2852 */                                                 out.write(10);
/*      */                                               }
/*      */                                               
/*      */ 
/* 2856 */                                               out.write("\n</td>\n\n</tr>\n");
/* 2857 */                                               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2858 */                                               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2859 */                                               i = (Integer)_jspx_page_context.findAttribute("i");
/* 2860 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 2863 */                                             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2864 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 2867 */                                           if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2868 */                                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                           }
/*      */                                           
/* 2871 */                                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2872 */                                           out.write("\n</table>\n");
/*      */ 
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 2877 */                                           ArrayList al1 = (ArrayList)request.getAttribute("discoverystatus");
/*      */                                           
/* 2879 */                                           out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n<tr>\n");
/* 2880 */                                           String mtype = (String)request.getAttribute("type");
/* 2881 */                                           out.write(10);
/* 2882 */                                           if (mtype.equals("File System Monitor")) {
/* 2883 */                                             out.write("\n  <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2884 */                                             out.print(FormatUtil.getString("File/Directory Name"));
/* 2885 */                                             out.write("</span> </td>\n");
/* 2886 */                                           } else if ((((String)request.getAttribute("type")).equals("Ping Monitor")) || (((String)request.getAttribute("type")).equals("SAP-CCMS"))) {
/* 2887 */                                             out.write("\n\t<td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2888 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 2889 */                                             out.write("</span> </td>\n");
/*      */                                           } else {
/* 2891 */                                             out.write("\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2892 */                                             out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 2893 */                                             out.write("</span> </td>\n");
/*      */                                           }
/* 2895 */                                           out.write("\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2896 */                                           out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.status"));
/* 2897 */                                           out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2898 */                                           out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 2899 */                                           out.write("</span></td>\n  </tr>\n  <tr>\n   <td height=\"18\" class=\"bodytext\">");
/* 2900 */                                           out.print(al1.get(0));
/* 2901 */                                           out.write("</td>\n   <td height=\"18\" class=\"bodytext\">\n   ");
/*      */                                           
/* 2903 */                                           if (al1.get(1).equals("Success"))
/*      */                                           {
/* 2905 */                                             request.setAttribute("isDiscoverySuccess", "true");
/*      */                                             
/* 2907 */                                             out.write("\n   <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 2908 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2909 */                                             out.write("\" align=\"absmiddle\">\n    ");
/*      */ 
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 2914 */                                             request.setAttribute("isDiscoverySuccess", "false");
/*      */                                             
/*      */ 
/* 2917 */                                             out.write("\n      <img  src=\"/images/icon_monitor_failure.gif\"  align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                                           }
/*      */                                           
/*      */ 
/* 2921 */                                           out.write("\n<span class=\"bodytextbold\">");
/* 2922 */                                           out.print(FormatUtil.getString(String.valueOf(al1.get(1))));
/* 2923 */                                           out.write("</span> </td>\n");
/*      */                                           
/* 2925 */                                           if (al1.get(1).equals("Success"))
/*      */                                           {
/* 2927 */                                             boolean isAdminServer = EnterpriseUtil.isAdminServer();
/* 2928 */                                             if (isAdminServer) {
/* 2929 */                                               String masDisplayName = (String)al1.get(3);
/* 2930 */                                               String format = FormatUtil.getString("am.webclient.admin.add.monitor.successfully.configured.text", new String[] { masDisplayName, "", (String)al1.get(2) });
/*      */                                               
/* 2932 */                                               out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\">");
/* 2933 */                                               out.print(format);
/* 2934 */                                               out.write("</td>\n");
/*      */                                             }
/*      */                                             else
/*      */                                             {
/* 2938 */                                               out.write("\t\t\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"> ");
/* 2939 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2940 */                                               out.write("<br> ");
/* 2941 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)al1.get(2) }));
/* 2942 */                                               out.write("</td>\n");
/*      */                                             }
/*      */                                             
/*      */ 
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 2949 */                                             out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"><span class=\"bodytext\">");
/* 2950 */                                             out.print(al1.get(2));
/* 2951 */                                             out.write("</span></td>\n");
/*      */                                           }
/*      */                                           
/*      */ 
/* 2955 */                                           out.write("\n  </tr>\n</table>\n\n");
/*      */                                         }
/*      */                                         
/*      */ 
/* 2959 */                                         out.write(10);
/* 2960 */                                         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 2961 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2965 */                                     if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 2966 */                                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                                     }
/*      */                                     
/* 2969 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 2970 */                                     out.write(10);
/* 2971 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2972 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2976 */                                 if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2977 */                                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                                 }
/*      */                                 
/* 2980 */                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2981 */                                 out.write(10);
/* 2982 */                                 out.write(10);
/*      */                                 
/* 2984 */                                 if (!isDiscoveryComplete)
/*      */                                 {
/*      */ 
/* 2987 */                                   out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n\t\t<td width=\"65%\" valign=\"top\">\n");
/* 2988 */                                   out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link rel=\"stylesheet\" href=\"/images/chosen.css\" />\n");
/* 2989 */                                   String message = (String)request.getAttribute("typemessage");
/*      */                                   
/* 2991 */                                   ManagedApplication mo1 = new ManagedApplication();
/* 2992 */                                   Properties props = com.adventnet.appmanager.util.Constants.getValueForNewMonitor();
/* 2993 */                                   boolean isConfMonitor = false;
/* 2994 */                                   ConfMonitorConfiguration confMonConfig = ConfMonitorConfiguration.getInstance();
/* 2995 */                                   if (message != null)
/*      */                                   {
/* 2997 */                                     out.write("\n    <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\" >\n    <tr>\n      <td><img src=\"/images/icon_message_success.gif\"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"bodytext\">\n        ");
/* 2998 */                                     out.print(message);
/* 2999 */                                     out.write("</span> </td>\n    </tr>\n  </table>\n     ");
/*      */                                   }
/*      */                                   
/*      */ 
/* 3003 */                                   out.write("\n\n\n<table id=\"newResourceTypes\" width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\">\n  <tr>\n\t<td width=\"25%\" class=\"tableheading-monitor-config bodytext label-align addmonitor-label\">&nbsp;");
/* 3004 */                                   out.print(FormatUtil.getString("am.webclient.newresourcetypes.addmonitor.text"));
/* 3005 */                                   out.write("</td>\n    <td class=\"tableheading-monitor-config \" valign=\"middle\">\n");
/* 3006 */                                   if ("UrlSeq".equals(request.getParameter("type"))) {
/* 3007 */                                     DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 3008 */                                     if (frm != null) {
/* 3009 */                                       frm.set("type", "UrlSeq");
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 3013 */                                   if ("UrlMonitor".equals(request.getParameter("type"))) {
/* 3014 */                                     DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 3015 */                                     if (frm != null) {
/* 3016 */                                       frm.set("type", "UrlMonitor");
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 3020 */                                   if ("RBM".equals(request.getParameter("type"))) {
/* 3021 */                                     DynaActionForm frm = (DynaActionForm)request.getAttribute("RbmForm");
/* 3022 */                                     if (frm != null) {
/* 3023 */                                       frm.set("type", "RBM");
/*      */                                     }
/*      */                                   }
/*      */                                   
/*      */ 
/* 3028 */                                   out.write("\n\n    ");
/*      */                                   
/* 3030 */                                   IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3031 */                                   _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3032 */                                   _jspx_th_c_005fif_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 3034 */                                   _jspx_th_c_005fif_005f2.setTest("${empty param.wiz && empty param.fromAssociate}");
/* 3035 */                                   int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3036 */                                   if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                                     for (;;) {
/* 3038 */                                       out.write("\n     ");
/*      */                                       
/* 3040 */                                       SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 3041 */                                       _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 3042 */                                       _jspx_th_html_005fselect_005f0.setParent(_jspx_th_c_005fif_005f2);
/*      */                                       
/* 3044 */                                       _jspx_th_html_005fselect_005f0.setProperty("type");
/*      */                                       
/* 3046 */                                       _jspx_th_html_005fselect_005f0.setStyle("background-color:#FDFEF2; font-size:13px;");
/*      */                                       
/* 3048 */                                       _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*      */                                       
/* 3050 */                                       _jspx_th_html_005fselect_005f0.setOnchange("javascript:formReload()");
/* 3051 */                                       int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 3052 */                                       if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 3053 */                                         if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3054 */                                           out = _jspx_page_context.pushBody();
/* 3055 */                                           _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 3056 */                                           _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3059 */                                           out.write("\n\n      <!-- If you are changing any of the values in this select box then kindly update the corresponding strings in HostDiscoveryHandler.java also-->\n      ");
/*      */                                           
/* 3061 */                                           if ((!com.adventnet.appmanager.util.Constants.isMinimizedversion()) || (com.adventnet.appmanager.util.Constants.getUserType().equals("F")) || (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                                           {
/*      */ 
/*      */ 
/* 3065 */                                             out.write("\n\n\t <optgroup label=\"");
/* 3066 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 3067 */                                             out.write("\">\n                                        \n                                        ");
/*      */                                             
/* 3069 */                                             OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3070 */                                             _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 3071 */                                             _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3073 */                                             _jspx_th_html_005foption_005f0.setValue("AIX");
/* 3074 */                                             int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 3075 */                                             if (_jspx_eval_html_005foption_005f0 != 0) {
/* 3076 */                                               if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3077 */                                                 out = _jspx_page_context.pushBody();
/* 3078 */                                                 _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 3079 */                                                 _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3082 */                                                 out.print(FormatUtil.getString("AIX"));
/* 3083 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 3084 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3087 */                                               if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3088 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3091 */                                             if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 3092 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                             }
/*      */                                             
/* 3095 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 3096 */                                             out.write("\n                                        ");
/*      */                                             
/* 3098 */                                             OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3099 */                                             _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 3100 */                                             _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3102 */                                             _jspx_th_html_005foption_005f1.setValue("AS400");
/* 3103 */                                             int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 3104 */                                             if (_jspx_eval_html_005foption_005f1 != 0) {
/* 3105 */                                               if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3106 */                                                 out = _jspx_page_context.pushBody();
/* 3107 */                                                 _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 3108 */                                                 _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3111 */                                                 out.print(FormatUtil.getString("AS400/iSeries"));
/* 3112 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 3113 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3116 */                                               if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3117 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3120 */                                             if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 3121 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                             }
/*      */                                             
/* 3124 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 3125 */                                             out.write("\n                                        ");
/*      */                                             
/* 3127 */                                             OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3128 */                                             _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 3129 */                                             _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3131 */                                             _jspx_th_html_005foption_005f2.setValue("FreeBSD");
/* 3132 */                                             int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 3133 */                                             if (_jspx_eval_html_005foption_005f2 != 0) {
/* 3134 */                                               if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3135 */                                                 out = _jspx_page_context.pushBody();
/* 3136 */                                                 _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 3137 */                                                 _jspx_th_html_005foption_005f2.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3140 */                                                 out.print(FormatUtil.getString("FreeBSD/OpenBSD"));
/* 3141 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 3142 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3145 */                                               if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3146 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3149 */                                             if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 3150 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                             }
/*      */                                             
/* 3153 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 3154 */                                             out.write("\n                                        ");
/*      */                                             
/* 3156 */                                             OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3157 */                                             _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 3158 */                                             _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3160 */                                             _jspx_th_html_005foption_005f3.setValue("HP-UX");
/* 3161 */                                             int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 3162 */                                             if (_jspx_eval_html_005foption_005f3 != 0) {
/* 3163 */                                               if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3164 */                                                 out = _jspx_page_context.pushBody();
/* 3165 */                                                 _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 3166 */                                                 _jspx_th_html_005foption_005f3.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3169 */                                                 out.print(FormatUtil.getString("HP-UX  / Tru64"));
/* 3170 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 3171 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3174 */                                               if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3175 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3178 */                                             if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 3179 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                             }
/*      */                                             
/* 3182 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 3183 */                                             out.write("\n                                        ");
/*      */                                             
/* 3185 */                                             OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3186 */                                             _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 3187 */                                             _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3189 */                                             _jspx_th_html_005foption_005f4.setValue("Linux");
/* 3190 */                                             int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 3191 */                                             if (_jspx_eval_html_005foption_005f4 != 0) {
/* 3192 */                                               if (_jspx_eval_html_005foption_005f4 != 1) {
/* 3193 */                                                 out = _jspx_page_context.pushBody();
/* 3194 */                                                 _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 3195 */                                                 _jspx_th_html_005foption_005f4.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3198 */                                                 out.print(FormatUtil.getString("Linux"));
/* 3199 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 3200 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3203 */                                               if (_jspx_eval_html_005foption_005f4 != 1) {
/* 3204 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3207 */                                             if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 3208 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                             }
/*      */                                             
/* 3211 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 3212 */                                             out.write("\n                                        ");
/*      */                                             
/* 3214 */                                             OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3215 */                                             _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 3216 */                                             _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3218 */                                             _jspx_th_html_005foption_005f5.setValue("Mac OS");
/* 3219 */                                             int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 3220 */                                             if (_jspx_eval_html_005foption_005f5 != 0) {
/* 3221 */                                               if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3222 */                                                 out = _jspx_page_context.pushBody();
/* 3223 */                                                 _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 3224 */                                                 _jspx_th_html_005foption_005f5.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3227 */                                                 out.print(FormatUtil.getString("Mac OS"));
/* 3228 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 3229 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3232 */                                               if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3233 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3236 */                                             if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 3237 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                             }
/*      */                                             
/* 3240 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 3241 */                                             out.write("\n                                        ");
/*      */                                             
/* 3243 */                                             OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3244 */                                             _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 3245 */                                             _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3247 */                                             _jspx_th_html_005foption_005f6.setValue("Novell");
/* 3248 */                                             int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 3249 */                                             if (_jspx_eval_html_005foption_005f6 != 0) {
/* 3250 */                                               if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3251 */                                                 out = _jspx_page_context.pushBody();
/* 3252 */                                                 _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 3253 */                                                 _jspx_th_html_005foption_005f6.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3256 */                                                 out.print(FormatUtil.getString("Novell"));
/* 3257 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 3258 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3261 */                                               if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3262 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3265 */                                             if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 3266 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                             }
/*      */                                             
/* 3269 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 3270 */                                             out.write("\n                                        ");
/*      */                                             
/* 3272 */                                             OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3273 */                                             _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 3274 */                                             _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3276 */                                             _jspx_th_html_005foption_005f7.setValue("Sun Solaris");
/* 3277 */                                             int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 3278 */                                             if (_jspx_eval_html_005foption_005f7 != 0) {
/* 3279 */                                               if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3280 */                                                 out = _jspx_page_context.pushBody();
/* 3281 */                                                 _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 3282 */                                                 _jspx_th_html_005foption_005f7.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3285 */                                                 out.print(FormatUtil.getString("Sun Solaris"));
/* 3286 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 3287 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3290 */                                               if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3291 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3294 */                                             if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 3295 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                             }
/*      */                                             
/* 3298 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/* 3299 */                                             out.write("\n                                        ");
/*      */                                             
/* 3301 */                                             OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3302 */                                             _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 3303 */                                             _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3305 */                                             _jspx_th_html_005foption_005f8.setValue("Windows");
/* 3306 */                                             int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 3307 */                                             if (_jspx_eval_html_005foption_005f8 != 0) {
/* 3308 */                                               if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3309 */                                                 out = _jspx_page_context.pushBody();
/* 3310 */                                                 _jspx_th_html_005foption_005f8.setBodyContent((BodyContent)out);
/* 3311 */                                                 _jspx_th_html_005foption_005f8.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3314 */                                                 out.print(FormatUtil.getString("Windows"));
/* 3315 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f8.doAfterBody();
/* 3316 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3319 */                                               if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3320 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3323 */                                             if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 3324 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                                             }
/*      */                                             
/* 3327 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8);
/* 3328 */                                             out.write("\n                                        ");
/*      */                                             
/* 3330 */                                             OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3331 */                                             _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 3332 */                                             _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3334 */                                             _jspx_th_html_005foption_005f9.setValue("Windows Cluster");
/* 3335 */                                             int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 3336 */                                             if (_jspx_eval_html_005foption_005f9 != 0) {
/* 3337 */                                               if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3338 */                                                 out = _jspx_page_context.pushBody();
/* 3339 */                                                 _jspx_th_html_005foption_005f9.setBodyContent((BodyContent)out);
/* 3340 */                                                 _jspx_th_html_005foption_005f9.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3343 */                                                 out.print(FormatUtil.getString("Windows Cluster"));
/* 3344 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f9.doAfterBody();
/* 3345 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3348 */                                               if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3349 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3352 */                                             if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 3353 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                                             }
/*      */                                             
/* 3356 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9);
/* 3357 */                                             out.write("\n                                        \n\n  ");
/*      */                                             
/* 3359 */                                             ArrayList rows1 = mo1.getRows("select RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH from AM_ManagedResourceType,AM_MONITOR_TYPES where TYPEID=RESOURCETYPEID and RESOURCEGROUP='SYS' and AMCREATED='NO' ORDER BY UPPER(DISPLAYNAME)");
/* 3360 */                                             if ((rows1 != null) && (rows1.size() > 0))
/*      */                                             {
/* 3362 */                                               for (int i = 0; i < rows1.size(); i++)
/*      */                                               {
/* 3364 */                                                 ArrayList row = (ArrayList)rows1.get(i);
/* 3365 */                                                 String res = (String)row.get(0);
/* 3366 */                                                 String dname = (String)row.get(1);
/* 3367 */                                                 String values = props.getProperty(res);
/* 3368 */                                                 if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                                                 {
/*      */ 
/* 3371 */                                                   out.write("\n\t\t\t\t");
/*      */                                                   
/* 3373 */                                                   OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3374 */                                                   _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/* 3375 */                                                   _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3377 */                                                   _jspx_th_html_005foption_005f10.setValue(values);
/* 3378 */                                                   int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/* 3379 */                                                   if (_jspx_eval_html_005foption_005f10 != 0) {
/* 3380 */                                                     if (_jspx_eval_html_005foption_005f10 != 1) {
/* 3381 */                                                       out = _jspx_page_context.pushBody();
/* 3382 */                                                       _jspx_th_html_005foption_005f10.setBodyContent((BodyContent)out);
/* 3383 */                                                       _jspx_th_html_005foption_005f10.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3386 */                                                       out.write(32);
/* 3387 */                                                       out.print(FormatUtil.getString(dname));
/* 3388 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f10.doAfterBody();
/* 3389 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3392 */                                                     if (_jspx_eval_html_005foption_005f10 != 1) {
/* 3393 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3396 */                                                   if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/* 3397 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                                                   }
/*      */                                                   
/* 3400 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10);
/* 3401 */                                                   out.write("\n\t\t\t");
/*      */                                                 }
/*      */                                               }
/*      */                                             }
/*      */                                             
/*      */ 
/* 3407 */                                             String[] categoryLink = { "APP", "ERP", "TM", "SYS", "DBS", "SER", "URL", "MS", "MOM", "CAM", "VIR", "CLD" };
/*      */                                             
/* 3409 */                                             String[] categoryTitle = { "am.webclient.monitorgroupsecond.category.appserver", "am.webclient.monitorgroupsecond.category.erp", "am.webclient.monitorgroupsecond.category.transaction", "am.webclient.monitorgroupsecond.category.servers", "am.webclient.monitorgroupsecond.category.dbserver", "am.webclient.monitorgroupsecond.category.services", "am.webclient.monitorgroupsecond.category.webservices.title", "am.webclient.monitorgroupsecond.category.mailserver", "Middleware/Portal", "am.webclient.monitorgroupsecond.category.custom", "am.webclient.monitorgroupsecond.category.virtualserver", "am.webclient.monitorgroupsecond.category.cloudapps" };
/*      */                                             
/*      */ 
/* 3412 */                                             if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD"))
/*      */                                             {
/*      */ 
/* 3415 */                                               categoryLink = com.adventnet.appmanager.util.Constants.categoryLink;
/* 3416 */                                               categoryTitle = com.adventnet.appmanager.util.Constants.categoryTitle;
/*      */                                             }
/* 3418 */                                             for (int c = 0; c < categoryLink.length; c++)
/*      */                                             {
/* 3420 */                                               ArrayList unSupportedTypes = com.adventnet.appmanager.util.Constants.getUnSupportedAsList();
/* 3421 */                                               if ((!categoryLink[c].equals("SYS")) && (!categoryLink[c].equals("NWD")) && (!categoryLink[c].equals("SAN")) && (!categoryLink[c].equals("EMO")) && (!categoryLink[c].equals("SCR")) && ((!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")) || (!unSupportedTypes.contains(categoryLink[c]))))
/*      */                                               {
/*      */ 
/*      */ 
/* 3425 */                                                 StringBuffer queryBuf = new StringBuffer();
/* 3426 */                                                 queryBuf.append("SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='");
/* 3427 */                                                 queryBuf.append(categoryLink[c] + "'");
/* 3428 */                                                 queryBuf.append(" ");
/* 3429 */                                                 queryBuf.append("and RESOURCETYPE in");
/* 3430 */                                                 queryBuf.append(" ");
/* 3431 */                                                 queryBuf.append(com.adventnet.appmanager.util.Constants.resourceTypes);
/* 3432 */                                                 if (categoryLink[c].equals("APP"))
/*      */                                                 {
/* 3434 */                                                   queryBuf.append(" ");
/* 3435 */                                                   queryBuf.append("and DISPLAYNAME NOT IN ('WebLogic Clusters')");
/* 3436 */                                                   queryBuf.append(" ");
/*      */                                                 }
/* 3438 */                                                 else if (categoryLink[c].equals("SER"))
/*      */                                                 {
/* 3440 */                                                   queryBuf.append(" ");
/* 3441 */                                                   queryBuf.append("and RESOURCETYPE<>'RMI'");
/* 3442 */                                                   queryBuf.append(" ");
/*      */                                                 }
/* 3444 */                                                 else if (categoryLink[c].equals("CAM"))
/*      */                                                 {
/* 3446 */                                                   queryBuf.append(" ");
/* 3447 */                                                   queryBuf.append("and RESOURCETYPE != 'directory'");
/* 3448 */                                                   queryBuf.append(" ");
/*      */                                                 }
/* 3450 */                                                 queryBuf.append(" ");
/* 3451 */                                                 queryBuf.append("ORDER BY UPPER(DISPLAYNAME)");
/* 3452 */                                                 ArrayList rows = mo1.getRows(queryBuf.toString());
/* 3453 */                                                 if ((rows != null) && (rows.size() != 0))
/*      */                                                 {
/*      */ 
/*      */ 
/*      */ 
/* 3458 */                                                   out.write("\n</optgroup>\t\t\t\t<optgroup label=\"");
/* 3459 */                                                   out.print(FormatUtil.getString(categoryTitle[c]));
/* 3460 */                                                   out.write(34);
/* 3461 */                                                   out.write(62);
/* 3462 */                                                   out.write(10);
/*      */                                                   
/*      */ 
/* 3465 */                                                   for (int i = 0; i < rows.size(); i++)
/*      */                                                   {
/* 3467 */                                                     ArrayList row = (ArrayList)rows.get(i);
/* 3468 */                                                     String res = (String)row.get(0);
/* 3469 */                                                     if (res.equals("file"))
/*      */                                                     {
/* 3471 */                                                       res = "File / Directory Monitor";
/*      */                                                     }
/* 3473 */                                                     String dname = (String)row.get(1);
/* 3474 */                                                     String values = props.getProperty(res);
/* 3475 */                                                     if ((!EnterpriseUtil.isCloudEdition()) || (!unSupportedTypes.contains(values)))
/*      */                                                     {
/*      */ 
/* 3478 */                                                       if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                                                       {
/*      */ 
/* 3481 */                                                         out.write("\n\t\t\t\t \t");
/*      */                                                         
/* 3483 */                                                         OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3484 */                                                         _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/* 3485 */                                                         _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                         
/* 3487 */                                                         _jspx_th_html_005foption_005f11.setValue(values);
/* 3488 */                                                         int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/* 3489 */                                                         if (_jspx_eval_html_005foption_005f11 != 0) {
/* 3490 */                                                           if (_jspx_eval_html_005foption_005f11 != 1) {
/* 3491 */                                                             out = _jspx_page_context.pushBody();
/* 3492 */                                                             _jspx_th_html_005foption_005f11.setBodyContent((BodyContent)out);
/* 3493 */                                                             _jspx_th_html_005foption_005f11.doInitBody();
/*      */                                                           }
/*      */                                                           for (;;) {
/* 3496 */                                                             out.write(32);
/* 3497 */                                                             out.print(FormatUtil.getString(dname));
/* 3498 */                                                             int evalDoAfterBody = _jspx_th_html_005foption_005f11.doAfterBody();
/* 3499 */                                                             if (evalDoAfterBody != 2)
/*      */                                                               break;
/*      */                                                           }
/* 3502 */                                                           if (_jspx_eval_html_005foption_005f11 != 1) {
/* 3503 */                                                             out = _jspx_page_context.popBody();
/*      */                                                           }
/*      */                                                         }
/* 3506 */                                                         if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/* 3507 */                                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11); return;
/*      */                                                         }
/*      */                                                         
/* 3510 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11);
/* 3511 */                                                         out.write("\n\t\t\t\t");
/*      */                                                       }
/*      */                                                     }
/*      */                                                   }
/*      */                                                   
/* 3516 */                                                   if (categoryLink[c].equals("VIR"))
/*      */                                                   {
/*      */ 
/* 3519 */                                                     out.write("\n\t\t\t\t\t");
/*      */                                                     
/* 3521 */                                                     OptionTag _jspx_th_html_005foption_005f12 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3522 */                                                     _jspx_th_html_005foption_005f12.setPageContext(_jspx_page_context);
/* 3523 */                                                     _jspx_th_html_005foption_005f12.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                     
/* 3525 */                                                     _jspx_th_html_005foption_005f12.setValue("VCenter");
/* 3526 */                                                     int _jspx_eval_html_005foption_005f12 = _jspx_th_html_005foption_005f12.doStartTag();
/* 3527 */                                                     if (_jspx_eval_html_005foption_005f12 != 0) {
/* 3528 */                                                       if (_jspx_eval_html_005foption_005f12 != 1) {
/* 3529 */                                                         out = _jspx_page_context.pushBody();
/* 3530 */                                                         _jspx_th_html_005foption_005f12.setBodyContent((BodyContent)out);
/* 3531 */                                                         _jspx_th_html_005foption_005f12.doInitBody();
/*      */                                                       }
/*      */                                                       for (;;) {
/* 3534 */                                                         out.write(32);
/* 3535 */                                                         out.print(FormatUtil.getString("am.webclient.addmonitor.vcenter.name"));
/* 3536 */                                                         int evalDoAfterBody = _jspx_th_html_005foption_005f12.doAfterBody();
/* 3537 */                                                         if (evalDoAfterBody != 2)
/*      */                                                           break;
/*      */                                                       }
/* 3540 */                                                       if (_jspx_eval_html_005foption_005f12 != 1) {
/* 3541 */                                                         out = _jspx_page_context.popBody();
/*      */                                                       }
/*      */                                                     }
/* 3544 */                                                     if (_jspx_th_html_005foption_005f12.doEndTag() == 5) {
/* 3545 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12); return;
/*      */                                                     }
/*      */                                                     
/* 3548 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12);
/* 3549 */                                                     out.write("\n\t\t\t\t");
/*      */                                                   }
/*      */                                                 }
/*      */                                               } }
/* 3553 */                                             String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/* 3554 */                                             if (!usertype.equals("F"))
/*      */                                             {
/* 3556 */                                               if (((!EnterpriseUtil.isIt360MSPEdition()) || (!DBUtil.isSharedProbe())) && (!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                                               {
/* 3558 */                                                 out.write("\n    </optgroup> <optgroup label=\"");
/* 3559 */                                                 out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 3560 */                                                 out.write("\">\n     ");
/*      */                                                 
/* 3562 */                                                 OptionTag _jspx_th_html_005foption_005f13 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3563 */                                                 _jspx_th_html_005foption_005f13.setPageContext(_jspx_page_context);
/* 3564 */                                                 _jspx_th_html_005foption_005f13.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                 
/* 3566 */                                                 _jspx_th_html_005foption_005f13.setValue("All:1008");
/* 3567 */                                                 int _jspx_eval_html_005foption_005f13 = _jspx_th_html_005foption_005f13.doStartTag();
/* 3568 */                                                 if (_jspx_eval_html_005foption_005f13 != 0) {
/* 3569 */                                                   if (_jspx_eval_html_005foption_005f13 != 1) {
/* 3570 */                                                     out = _jspx_page_context.pushBody();
/* 3571 */                                                     _jspx_th_html_005foption_005f13.setBodyContent((BodyContent)out);
/* 3572 */                                                     _jspx_th_html_005foption_005f13.doInitBody();
/*      */                                                   }
/*      */                                                   for (;;) {
/* 3575 */                                                     out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 3576 */                                                     out.write(32);
/* 3577 */                                                     int evalDoAfterBody = _jspx_th_html_005foption_005f13.doAfterBody();
/* 3578 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/* 3581 */                                                   if (_jspx_eval_html_005foption_005f13 != 1) {
/* 3582 */                                                     out = _jspx_page_context.popBody();
/*      */                                                   }
/*      */                                                 }
/* 3585 */                                                 if (_jspx_th_html_005foption_005f13.doEndTag() == 5) {
/* 3586 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13); return;
/*      */                                                 }
/*      */                                                 
/* 3589 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13);
/* 3590 */                                                 out.write("\n\n     ");
/*      */                                               }
/*      */                                               
/*      */                                             }
/*      */                                             
/*      */                                           }
/* 3596 */                                           else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("LAMP"))
/*      */                                           {
/*      */ 
/* 3599 */                                             out.write("\n\t\t\t </optgroup>  <optgroup label=\"");
/* 3600 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 3601 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 3603 */                                             OptionTag _jspx_th_html_005foption_005f14 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3604 */                                             _jspx_th_html_005foption_005f14.setPageContext(_jspx_page_context);
/* 3605 */                                             _jspx_th_html_005foption_005f14.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3607 */                                             _jspx_th_html_005foption_005f14.setValue("SYSTEM:9999");
/* 3608 */                                             int _jspx_eval_html_005foption_005f14 = _jspx_th_html_005foption_005f14.doStartTag();
/* 3609 */                                             if (_jspx_eval_html_005foption_005f14 != 0) {
/* 3610 */                                               if (_jspx_eval_html_005foption_005f14 != 1) {
/* 3611 */                                                 out = _jspx_page_context.pushBody();
/* 3612 */                                                 _jspx_th_html_005foption_005f14.setBodyContent((BodyContent)out);
/* 3613 */                                                 _jspx_th_html_005foption_005f14.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3616 */                                                 out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 3617 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f14.doAfterBody();
/* 3618 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3621 */                                               if (_jspx_eval_html_005foption_005f14 != 1) {
/* 3622 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3625 */                                             if (_jspx_th_html_005foption_005f14.doEndTag() == 5) {
/* 3626 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14); return;
/*      */                                             }
/*      */                                             
/* 3629 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14);
/* 3630 */                                             out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3631 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 3632 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 3634 */                                             OptionTag _jspx_th_html_005foption_005f15 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3635 */                                             _jspx_th_html_005foption_005f15.setPageContext(_jspx_page_context);
/* 3636 */                                             _jspx_th_html_005foption_005f15.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3638 */                                             _jspx_th_html_005foption_005f15.setValue("MYSQLDB:3306");
/* 3639 */                                             int _jspx_eval_html_005foption_005f15 = _jspx_th_html_005foption_005f15.doStartTag();
/* 3640 */                                             if (_jspx_eval_html_005foption_005f15 != 0) {
/* 3641 */                                               if (_jspx_eval_html_005foption_005f15 != 1) {
/* 3642 */                                                 out = _jspx_page_context.pushBody();
/* 3643 */                                                 _jspx_th_html_005foption_005f15.setBodyContent((BodyContent)out);
/* 3644 */                                                 _jspx_th_html_005foption_005f15.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3647 */                                                 out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 3648 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f15.doAfterBody();
/* 3649 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3652 */                                               if (_jspx_eval_html_005foption_005f15 != 1) {
/* 3653 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3656 */                                             if (_jspx_th_html_005foption_005f15.doEndTag() == 5) {
/* 3657 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15); return;
/*      */                                             }
/*      */                                             
/* 3660 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15);
/* 3661 */                                             out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3662 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 3663 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 3665 */                                             OptionTag _jspx_th_html_005foption_005f16 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3666 */                                             _jspx_th_html_005foption_005f16.setPageContext(_jspx_page_context);
/* 3667 */                                             _jspx_th_html_005foption_005f16.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3669 */                                             _jspx_th_html_005foption_005f16.setValue("JMX1.2-MX4J-RMI:1099");
/* 3670 */                                             int _jspx_eval_html_005foption_005f16 = _jspx_th_html_005foption_005f16.doStartTag();
/* 3671 */                                             if (_jspx_eval_html_005foption_005f16 != 0) {
/* 3672 */                                               if (_jspx_eval_html_005foption_005f16 != 1) {
/* 3673 */                                                 out = _jspx_page_context.pushBody();
/* 3674 */                                                 _jspx_th_html_005foption_005f16.setBodyContent((BodyContent)out);
/* 3675 */                                                 _jspx_th_html_005foption_005f16.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3678 */                                                 out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 3679 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f16.doAfterBody();
/* 3680 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3683 */                                               if (_jspx_eval_html_005foption_005f16 != 1) {
/* 3684 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3687 */                                             if (_jspx_th_html_005foption_005f16.doEndTag() == 5) {
/* 3688 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16); return;
/*      */                                             }
/*      */                                             
/* 3691 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16);
/* 3692 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3694 */                                             OptionTag _jspx_th_html_005foption_005f17 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3695 */                                             _jspx_th_html_005foption_005f17.setPageContext(_jspx_page_context);
/* 3696 */                                             _jspx_th_html_005foption_005f17.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3698 */                                             _jspx_th_html_005foption_005f17.setValue("SERVICE:9090");
/* 3699 */                                             int _jspx_eval_html_005foption_005f17 = _jspx_th_html_005foption_005f17.doStartTag();
/* 3700 */                                             if (_jspx_eval_html_005foption_005f17 != 0) {
/* 3701 */                                               if (_jspx_eval_html_005foption_005f17 != 1) {
/* 3702 */                                                 out = _jspx_page_context.pushBody();
/* 3703 */                                                 _jspx_th_html_005foption_005f17.setBodyContent((BodyContent)out);
/* 3704 */                                                 _jspx_th_html_005foption_005f17.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3707 */                                                 out.write(32);
/* 3708 */                                                 out.print(FormatUtil.getString("Service Monitoring"));
/* 3709 */                                                 out.write(32);
/* 3710 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f17.doAfterBody();
/* 3711 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3714 */                                               if (_jspx_eval_html_005foption_005f17 != 1) {
/* 3715 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3718 */                                             if (_jspx_th_html_005foption_005f17.doEndTag() == 5) {
/* 3719 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17); return;
/*      */                                             }
/*      */                                             
/* 3722 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17);
/* 3723 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3725 */                                             OptionTag _jspx_th_html_005foption_005f18 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3726 */                                             _jspx_th_html_005foption_005f18.setPageContext(_jspx_page_context);
/* 3727 */                                             _jspx_th_html_005foption_005f18.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3729 */                                             _jspx_th_html_005foption_005f18.setValue("RMI:1099");
/* 3730 */                                             int _jspx_eval_html_005foption_005f18 = _jspx_th_html_005foption_005f18.doStartTag();
/* 3731 */                                             if (_jspx_eval_html_005foption_005f18 != 0) {
/* 3732 */                                               if (_jspx_eval_html_005foption_005f18 != 1) {
/* 3733 */                                                 out = _jspx_page_context.pushBody();
/* 3734 */                                                 _jspx_th_html_005foption_005f18.setBodyContent((BodyContent)out);
/* 3735 */                                                 _jspx_th_html_005foption_005f18.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3738 */                                                 out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 3739 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f18.doAfterBody();
/* 3740 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3743 */                                               if (_jspx_eval_html_005foption_005f18 != 1) {
/* 3744 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3747 */                                             if (_jspx_th_html_005foption_005f18.doEndTag() == 5) {
/* 3748 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18); return;
/*      */                                             }
/*      */                                             
/* 3751 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18);
/* 3752 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3754 */                                             OptionTag _jspx_th_html_005foption_005f19 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3755 */                                             _jspx_th_html_005foption_005f19.setPageContext(_jspx_page_context);
/* 3756 */                                             _jspx_th_html_005foption_005f19.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3758 */                                             _jspx_th_html_005foption_005f19.setValue("SNMP:161");
/* 3759 */                                             int _jspx_eval_html_005foption_005f19 = _jspx_th_html_005foption_005f19.doStartTag();
/* 3760 */                                             if (_jspx_eval_html_005foption_005f19 != 0) {
/* 3761 */                                               if (_jspx_eval_html_005foption_005f19 != 1) {
/* 3762 */                                                 out = _jspx_page_context.pushBody();
/* 3763 */                                                 _jspx_th_html_005foption_005f19.setBodyContent((BodyContent)out);
/* 3764 */                                                 _jspx_th_html_005foption_005f19.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3767 */                                                 out.print(FormatUtil.getString("SNMP"));
/* 3768 */                                                 out.write(" (V1 or V2c)");
/* 3769 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f19.doAfterBody();
/* 3770 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3773 */                                               if (_jspx_eval_html_005foption_005f19 != 1) {
/* 3774 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3777 */                                             if (_jspx_th_html_005foption_005f19.doEndTag() == 5) {
/* 3778 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19); return;
/*      */                                             }
/*      */                                             
/* 3781 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19);
/* 3782 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3784 */                                             OptionTag _jspx_th_html_005foption_005f20 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3785 */                                             _jspx_th_html_005foption_005f20.setPageContext(_jspx_page_context);
/* 3786 */                                             _jspx_th_html_005foption_005f20.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3788 */                                             _jspx_th_html_005foption_005f20.setValue("TELNET:23");
/* 3789 */                                             int _jspx_eval_html_005foption_005f20 = _jspx_th_html_005foption_005f20.doStartTag();
/* 3790 */                                             if (_jspx_eval_html_005foption_005f20 != 0) {
/* 3791 */                                               if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3792 */                                                 out = _jspx_page_context.pushBody();
/* 3793 */                                                 _jspx_th_html_005foption_005f20.setBodyContent((BodyContent)out);
/* 3794 */                                                 _jspx_th_html_005foption_005f20.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3797 */                                                 out.print(FormatUtil.getString("Telnet"));
/* 3798 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f20.doAfterBody();
/* 3799 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3802 */                                               if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3803 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3806 */                                             if (_jspx_th_html_005foption_005f20.doEndTag() == 5) {
/* 3807 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20); return;
/*      */                                             }
/*      */                                             
/* 3810 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20);
/* 3811 */                                             out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3812 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 3813 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 3815 */                                             OptionTag _jspx_th_html_005foption_005f21 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3816 */                                             _jspx_th_html_005foption_005f21.setPageContext(_jspx_page_context);
/* 3817 */                                             _jspx_th_html_005foption_005f21.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3819 */                                             _jspx_th_html_005foption_005f21.setValue("APACHE:80");
/* 3820 */                                             int _jspx_eval_html_005foption_005f21 = _jspx_th_html_005foption_005f21.doStartTag();
/* 3821 */                                             if (_jspx_eval_html_005foption_005f21 != 0) {
/* 3822 */                                               if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3823 */                                                 out = _jspx_page_context.pushBody();
/* 3824 */                                                 _jspx_th_html_005foption_005f21.setBodyContent((BodyContent)out);
/* 3825 */                                                 _jspx_th_html_005foption_005f21.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3828 */                                                 out.write(32);
/* 3829 */                                                 out.print(FormatUtil.getString("Apache Server"));
/* 3830 */                                                 out.write(32);
/* 3831 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f21.doAfterBody();
/* 3832 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3835 */                                               if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3836 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3839 */                                             if (_jspx_th_html_005foption_005f21.doEndTag() == 5) {
/* 3840 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21); return;
/*      */                                             }
/*      */                                             
/* 3843 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21);
/* 3844 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3846 */                                             OptionTag _jspx_th_html_005foption_005f22 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3847 */                                             _jspx_th_html_005foption_005f22.setPageContext(_jspx_page_context);
/* 3848 */                                             _jspx_th_html_005foption_005f22.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3850 */                                             _jspx_th_html_005foption_005f22.setValue("PHP:80");
/* 3851 */                                             int _jspx_eval_html_005foption_005f22 = _jspx_th_html_005foption_005f22.doStartTag();
/* 3852 */                                             if (_jspx_eval_html_005foption_005f22 != 0) {
/* 3853 */                                               if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3854 */                                                 out = _jspx_page_context.pushBody();
/* 3855 */                                                 _jspx_th_html_005foption_005f22.setBodyContent((BodyContent)out);
/* 3856 */                                                 _jspx_th_html_005foption_005f22.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3859 */                                                 out.print(FormatUtil.getString("PHP"));
/* 3860 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f22.doAfterBody();
/* 3861 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3864 */                                               if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3865 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3868 */                                             if (_jspx_th_html_005foption_005f22.doEndTag() == 5) {
/* 3869 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22); return;
/*      */                                             }
/*      */                                             
/* 3872 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22);
/* 3873 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3875 */                                             OptionTag _jspx_th_html_005foption_005f23 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3876 */                                             _jspx_th_html_005foption_005f23.setPageContext(_jspx_page_context);
/* 3877 */                                             _jspx_th_html_005foption_005f23.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3879 */                                             _jspx_th_html_005foption_005f23.setValue("UrlMonitor");
/* 3880 */                                             int _jspx_eval_html_005foption_005f23 = _jspx_th_html_005foption_005f23.doStartTag();
/* 3881 */                                             if (_jspx_eval_html_005foption_005f23 != 0) {
/* 3882 */                                               if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3883 */                                                 out = _jspx_page_context.pushBody();
/* 3884 */                                                 _jspx_th_html_005foption_005f23.setBodyContent((BodyContent)out);
/* 3885 */                                                 _jspx_th_html_005foption_005f23.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3888 */                                                 out.print(FormatUtil.getString("HTTP-URLs"));
/* 3889 */                                                 out.write(32);
/* 3890 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f23.doAfterBody();
/* 3891 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3894 */                                               if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3895 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3898 */                                             if (_jspx_th_html_005foption_005f23.doEndTag() == 5) {
/* 3899 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23); return;
/*      */                                             }
/*      */                                             
/* 3902 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23);
/* 3903 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3905 */                                             OptionTag _jspx_th_html_005foption_005f24 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3906 */                                             _jspx_th_html_005foption_005f24.setPageContext(_jspx_page_context);
/* 3907 */                                             _jspx_th_html_005foption_005f24.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3909 */                                             _jspx_th_html_005foption_005f24.setValue("UrlSeq");
/* 3910 */                                             int _jspx_eval_html_005foption_005f24 = _jspx_th_html_005foption_005f24.doStartTag();
/* 3911 */                                             if (_jspx_eval_html_005foption_005f24 != 0) {
/* 3912 */                                               if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3913 */                                                 out = _jspx_page_context.pushBody();
/* 3914 */                                                 _jspx_th_html_005foption_005f24.setBodyContent((BodyContent)out);
/* 3915 */                                                 _jspx_th_html_005foption_005f24.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3918 */                                                 out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 3919 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f24.doAfterBody();
/* 3920 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3923 */                                               if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3924 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3927 */                                             if (_jspx_th_html_005foption_005f24.doEndTag() == 5) {
/* 3928 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24); return;
/*      */                                             }
/*      */                                             
/* 3931 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24);
/* 3932 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3934 */                                             OptionTag _jspx_th_html_005foption_005f25 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3935 */                                             _jspx_th_html_005foption_005f25.setPageContext(_jspx_page_context);
/* 3936 */                                             _jspx_th_html_005foption_005f25.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3938 */                                             _jspx_th_html_005foption_005f25.setValue("WEB:80");
/* 3939 */                                             int _jspx_eval_html_005foption_005f25 = _jspx_th_html_005foption_005f25.doStartTag();
/* 3940 */                                             if (_jspx_eval_html_005foption_005f25 != 0) {
/* 3941 */                                               if (_jspx_eval_html_005foption_005f25 != 1) {
/* 3942 */                                                 out = _jspx_page_context.pushBody();
/* 3943 */                                                 _jspx_th_html_005foption_005f25.setBodyContent((BodyContent)out);
/* 3944 */                                                 _jspx_th_html_005foption_005f25.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3947 */                                                 out.write(32);
/* 3948 */                                                 out.print(FormatUtil.getString("Web Server"));
/* 3949 */                                                 out.write(32);
/* 3950 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f25.doAfterBody();
/* 3951 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3954 */                                               if (_jspx_eval_html_005foption_005f25 != 1) {
/* 3955 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3958 */                                             if (_jspx_th_html_005foption_005f25.doEndTag() == 5) {
/* 3959 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25); return;
/*      */                                             }
/*      */                                             
/* 3962 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25);
/* 3963 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3965 */                                             OptionTag _jspx_th_html_005foption_005f26 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3966 */                                             _jspx_th_html_005foption_005f26.setPageContext(_jspx_page_context);
/* 3967 */                                             _jspx_th_html_005foption_005f26.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3969 */                                             _jspx_th_html_005foption_005f26.setValue("Web Service");
/* 3970 */                                             int _jspx_eval_html_005foption_005f26 = _jspx_th_html_005foption_005f26.doStartTag();
/* 3971 */                                             if (_jspx_eval_html_005foption_005f26 != 0) {
/* 3972 */                                               if (_jspx_eval_html_005foption_005f26 != 1) {
/* 3973 */                                                 out = _jspx_page_context.pushBody();
/* 3974 */                                                 _jspx_th_html_005foption_005f26.setBodyContent((BodyContent)out);
/* 3975 */                                                 _jspx_th_html_005foption_005f26.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3978 */                                                 out.write(32);
/* 3979 */                                                 out.print(FormatUtil.getString("Web Service"));
/* 3980 */                                                 out.write(32);
/* 3981 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f26.doAfterBody();
/* 3982 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3985 */                                               if (_jspx_eval_html_005foption_005f26 != 1) {
/* 3986 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3989 */                                             if (_jspx_th_html_005foption_005f26.doEndTag() == 5) {
/* 3990 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26); return;
/*      */                                             }
/*      */                                             
/* 3993 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26);
/* 3994 */                                             out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3995 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 3996 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 3998 */                                             OptionTag _jspx_th_html_005foption_005f27 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3999 */                                             _jspx_th_html_005foption_005f27.setPageContext(_jspx_page_context);
/* 4000 */                                             _jspx_th_html_005foption_005f27.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4002 */                                             _jspx_th_html_005foption_005f27.setValue("MAIL:25");
/* 4003 */                                             int _jspx_eval_html_005foption_005f27 = _jspx_th_html_005foption_005f27.doStartTag();
/* 4004 */                                             if (_jspx_eval_html_005foption_005f27 != 0) {
/* 4005 */                                               if (_jspx_eval_html_005foption_005f27 != 1) {
/* 4006 */                                                 out = _jspx_page_context.pushBody();
/* 4007 */                                                 _jspx_th_html_005foption_005f27.setBodyContent((BodyContent)out);
/* 4008 */                                                 _jspx_th_html_005foption_005f27.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4011 */                                                 out.print(FormatUtil.getString("Mail Server"));
/* 4012 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f27.doAfterBody();
/* 4013 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4016 */                                               if (_jspx_eval_html_005foption_005f27 != 1) {
/* 4017 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4020 */                                             if (_jspx_th_html_005foption_005f27.doEndTag() == 5) {
/* 4021 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27); return;
/*      */                                             }
/*      */                                             
/* 4024 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27);
/* 4025 */                                             out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 4026 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 4027 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 4029 */                                             OptionTag _jspx_th_html_005foption_005f28 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4030 */                                             _jspx_th_html_005foption_005f28.setPageContext(_jspx_page_context);
/* 4031 */                                             _jspx_th_html_005foption_005f28.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4033 */                                             _jspx_th_html_005foption_005f28.setValue("Custom-Application");
/* 4034 */                                             int _jspx_eval_html_005foption_005f28 = _jspx_th_html_005foption_005f28.doStartTag();
/* 4035 */                                             if (_jspx_eval_html_005foption_005f28 != 0) {
/* 4036 */                                               if (_jspx_eval_html_005foption_005f28 != 1) {
/* 4037 */                                                 out = _jspx_page_context.pushBody();
/* 4038 */                                                 _jspx_th_html_005foption_005f28.setBodyContent((BodyContent)out);
/* 4039 */                                                 _jspx_th_html_005foption_005f28.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4042 */                                                 out.write(32);
/* 4043 */                                                 out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 4044 */                                                 out.write(32);
/* 4045 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f28.doAfterBody();
/* 4046 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4049 */                                               if (_jspx_eval_html_005foption_005f28 != 1) {
/* 4050 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4053 */                                             if (_jspx_th_html_005foption_005f28.doEndTag() == 5) {
/* 4054 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28); return;
/*      */                                             }
/*      */                                             
/* 4057 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28);
/* 4058 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 4060 */                                             OptionTag _jspx_th_html_005foption_005f29 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4061 */                                             _jspx_th_html_005foption_005f29.setPageContext(_jspx_page_context);
/* 4062 */                                             _jspx_th_html_005foption_005f29.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4064 */                                             _jspx_th_html_005foption_005f29.setValue("Script Monitor");
/* 4065 */                                             int _jspx_eval_html_005foption_005f29 = _jspx_th_html_005foption_005f29.doStartTag();
/* 4066 */                                             if (_jspx_eval_html_005foption_005f29 != 0) {
/* 4067 */                                               if (_jspx_eval_html_005foption_005f29 != 1) {
/* 4068 */                                                 out = _jspx_page_context.pushBody();
/* 4069 */                                                 _jspx_th_html_005foption_005f29.setBodyContent((BodyContent)out);
/* 4070 */                                                 _jspx_th_html_005foption_005f29.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4073 */                                                 out.print(FormatUtil.getString("Script Monitor"));
/* 4074 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f29.doAfterBody();
/* 4075 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4078 */                                               if (_jspx_eval_html_005foption_005f29 != 1) {
/* 4079 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4082 */                                             if (_jspx_th_html_005foption_005f29.doEndTag() == 5) {
/* 4083 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29); return;
/*      */                                             }
/*      */                                             
/* 4086 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29);
/* 4087 */                                             out.write("\n\n    ");
/*      */ 
/*      */                                           }
/* 4090 */                                           else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("J2EE"))
/*      */                                           {
/*      */ 
/* 4093 */                                             out.write("\n        ");
/*      */                                             
/* 4095 */                                             OptionTag _jspx_th_html_005foption_005f30 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4096 */                                             _jspx_th_html_005foption_005f30.setPageContext(_jspx_page_context);
/* 4097 */                                             _jspx_th_html_005foption_005f30.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4099 */                                             _jspx_th_html_005foption_005f30.setValue("SYSTEM:9999");
/* 4100 */                                             int _jspx_eval_html_005foption_005f30 = _jspx_th_html_005foption_005f30.doStartTag();
/* 4101 */                                             if (_jspx_eval_html_005foption_005f30 != 0) {
/* 4102 */                                               if (_jspx_eval_html_005foption_005f30 != 1) {
/* 4103 */                                                 out = _jspx_page_context.pushBody();
/* 4104 */                                                 _jspx_th_html_005foption_005f30.setBodyContent((BodyContent)out);
/* 4105 */                                                 _jspx_th_html_005foption_005f30.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4108 */                                                 out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4109 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f30.doAfterBody();
/* 4110 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4113 */                                               if (_jspx_eval_html_005foption_005f30 != 1) {
/* 4114 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4117 */                                             if (_jspx_th_html_005foption_005f30.doEndTag() == 5) {
/* 4118 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30); return;
/*      */                                             }
/*      */                                             
/* 4121 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30);
/* 4122 */                                             out.write("\n       ");
/*      */                                             
/* 4124 */                                             OptionTag _jspx_th_html_005foption_005f31 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4125 */                                             _jspx_th_html_005foption_005f31.setPageContext(_jspx_page_context);
/* 4126 */                                             _jspx_th_html_005foption_005f31.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4128 */                                             _jspx_th_html_005foption_005f31.setValue("JBoss:8080");
/* 4129 */                                             int _jspx_eval_html_005foption_005f31 = _jspx_th_html_005foption_005f31.doStartTag();
/* 4130 */                                             if (_jspx_eval_html_005foption_005f31 != 0) {
/* 4131 */                                               if (_jspx_eval_html_005foption_005f31 != 1) {
/* 4132 */                                                 out = _jspx_page_context.pushBody();
/* 4133 */                                                 _jspx_th_html_005foption_005f31.setBodyContent((BodyContent)out);
/* 4134 */                                                 _jspx_th_html_005foption_005f31.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4137 */                                                 out.write(32);
/* 4138 */                                                 out.print(FormatUtil.getString("JBoss Server"));
/* 4139 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f31.doAfterBody();
/* 4140 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4143 */                                               if (_jspx_eval_html_005foption_005f31 != 1) {
/* 4144 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4147 */                                             if (_jspx_th_html_005foption_005f31.doEndTag() == 5) {
/* 4148 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31); return;
/*      */                                             }
/*      */                                             
/* 4151 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31);
/* 4152 */                                             out.write("\n      ");
/*      */                                             
/* 4154 */                                             OptionTag _jspx_th_html_005foption_005f32 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4155 */                                             _jspx_th_html_005foption_005f32.setPageContext(_jspx_page_context);
/* 4156 */                                             _jspx_th_html_005foption_005f32.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4158 */                                             _jspx_th_html_005foption_005f32.setValue("Tomcat:8080");
/* 4159 */                                             int _jspx_eval_html_005foption_005f32 = _jspx_th_html_005foption_005f32.doStartTag();
/* 4160 */                                             if (_jspx_eval_html_005foption_005f32 != 0) {
/* 4161 */                                               if (_jspx_eval_html_005foption_005f32 != 1) {
/* 4162 */                                                 out = _jspx_page_context.pushBody();
/* 4163 */                                                 _jspx_th_html_005foption_005f32.setBodyContent((BodyContent)out);
/* 4164 */                                                 _jspx_th_html_005foption_005f32.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4167 */                                                 out.print(FormatUtil.getString("Tomcat Server"));
/* 4168 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f32.doAfterBody();
/* 4169 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4172 */                                               if (_jspx_eval_html_005foption_005f32 != 1) {
/* 4173 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4176 */                                             if (_jspx_th_html_005foption_005f32.doEndTag() == 5) {
/* 4177 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32); return;
/*      */                                             }
/*      */                                             
/* 4180 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32);
/* 4181 */                                             out.write("\n       ");
/*      */                                             
/* 4183 */                                             OptionTag _jspx_th_html_005foption_005f33 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4184 */                                             _jspx_th_html_005foption_005f33.setPageContext(_jspx_page_context);
/* 4185 */                                             _jspx_th_html_005foption_005f33.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4187 */                                             _jspx_th_html_005foption_005f33.setValue("WEBLOGIC:7001");
/* 4188 */                                             int _jspx_eval_html_005foption_005f33 = _jspx_th_html_005foption_005f33.doStartTag();
/* 4189 */                                             if (_jspx_eval_html_005foption_005f33 != 0) {
/* 4190 */                                               if (_jspx_eval_html_005foption_005f33 != 1) {
/* 4191 */                                                 out = _jspx_page_context.pushBody();
/* 4192 */                                                 _jspx_th_html_005foption_005f33.setBodyContent((BodyContent)out);
/* 4193 */                                                 _jspx_th_html_005foption_005f33.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4196 */                                                 out.write(32);
/* 4197 */                                                 out.print(FormatUtil.getString("WebLogic Server"));
/* 4198 */                                                 out.write(32);
/* 4199 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f33.doAfterBody();
/* 4200 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4203 */                                               if (_jspx_eval_html_005foption_005f33 != 1) {
/* 4204 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4207 */                                             if (_jspx_th_html_005foption_005f33.doEndTag() == 5) {
/* 4208 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33); return;
/*      */                                             }
/*      */                                             
/* 4211 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33);
/* 4212 */                                             out.write("\n      ");
/*      */                                             
/* 4214 */                                             OptionTag _jspx_th_html_005foption_005f34 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4215 */                                             _jspx_th_html_005foption_005f34.setPageContext(_jspx_page_context);
/* 4216 */                                             _jspx_th_html_005foption_005f34.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4218 */                                             _jspx_th_html_005foption_005f34.setValue("WEBSPHERE:9080");
/* 4219 */                                             int _jspx_eval_html_005foption_005f34 = _jspx_th_html_005foption_005f34.doStartTag();
/* 4220 */                                             if (_jspx_eval_html_005foption_005f34 != 0) {
/* 4221 */                                               if (_jspx_eval_html_005foption_005f34 != 1) {
/* 4222 */                                                 out = _jspx_page_context.pushBody();
/* 4223 */                                                 _jspx_th_html_005foption_005f34.setBodyContent((BodyContent)out);
/* 4224 */                                                 _jspx_th_html_005foption_005f34.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4227 */                                                 out.write(32);
/* 4228 */                                                 out.print(FormatUtil.getString("WebSphere Server"));
/* 4229 */                                                 out.write(32);
/* 4230 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f34.doAfterBody();
/* 4231 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4234 */                                               if (_jspx_eval_html_005foption_005f34 != 1) {
/* 4235 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4238 */                                             if (_jspx_th_html_005foption_005f34.doEndTag() == 5) {
/* 4239 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34); return;
/*      */                                             }
/*      */                                             
/* 4242 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34);
/* 4243 */                                             out.write("\n      ");
/*      */                                             
/* 4245 */                                             OptionTag _jspx_th_html_005foption_005f35 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4246 */                                             _jspx_th_html_005foption_005f35.setPageContext(_jspx_page_context);
/* 4247 */                                             _jspx_th_html_005foption_005f35.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4249 */                                             _jspx_th_html_005foption_005f35.setValue("WTA:55555");
/* 4250 */                                             int _jspx_eval_html_005foption_005f35 = _jspx_th_html_005foption_005f35.doStartTag();
/* 4251 */                                             if (_jspx_eval_html_005foption_005f35 != 0) {
/* 4252 */                                               if (_jspx_eval_html_005foption_005f35 != 1) {
/* 4253 */                                                 out = _jspx_page_context.pushBody();
/* 4254 */                                                 _jspx_th_html_005foption_005f35.setBodyContent((BodyContent)out);
/* 4255 */                                                 _jspx_th_html_005foption_005f35.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4258 */                                                 out.print(FormatUtil.getString("Web Transactions"));
/* 4259 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f35.doAfterBody();
/* 4260 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4263 */                                               if (_jspx_eval_html_005foption_005f35 != 1) {
/* 4264 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4267 */                                             if (_jspx_th_html_005foption_005f35.doEndTag() == 5) {
/* 4268 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35); return;
/*      */                                             }
/*      */                                             
/* 4271 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35);
/* 4272 */                                             out.write("\n      ");
/*      */                                             
/* 4274 */                                             OptionTag _jspx_th_html_005foption_005f36 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4275 */                                             _jspx_th_html_005foption_005f36.setPageContext(_jspx_page_context);
/* 4276 */                                             _jspx_th_html_005foption_005f36.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4278 */                                             _jspx_th_html_005foption_005f36.setValue("MAIL:25");
/* 4279 */                                             int _jspx_eval_html_005foption_005f36 = _jspx_th_html_005foption_005f36.doStartTag();
/* 4280 */                                             if (_jspx_eval_html_005foption_005f36 != 0) {
/* 4281 */                                               if (_jspx_eval_html_005foption_005f36 != 1) {
/* 4282 */                                                 out = _jspx_page_context.pushBody();
/* 4283 */                                                 _jspx_th_html_005foption_005f36.setBodyContent((BodyContent)out);
/* 4284 */                                                 _jspx_th_html_005foption_005f36.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4287 */                                                 out.print(FormatUtil.getString("Mail Server"));
/* 4288 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f36.doAfterBody();
/* 4289 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4292 */                                               if (_jspx_eval_html_005foption_005f36 != 1) {
/* 4293 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4296 */                                             if (_jspx_th_html_005foption_005f36.doEndTag() == 5) {
/* 4297 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36); return;
/*      */                                             }
/*      */                                             
/* 4300 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36);
/* 4301 */                                             out.write("\n      ");
/*      */                                             
/* 4303 */                                             OptionTag _jspx_th_html_005foption_005f37 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4304 */                                             _jspx_th_html_005foption_005f37.setPageContext(_jspx_page_context);
/* 4305 */                                             _jspx_th_html_005foption_005f37.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4307 */                                             _jspx_th_html_005foption_005f37.setValue("JMX1.2-MX4J-RMI:1099");
/* 4308 */                                             int _jspx_eval_html_005foption_005f37 = _jspx_th_html_005foption_005f37.doStartTag();
/* 4309 */                                             if (_jspx_eval_html_005foption_005f37 != 0) {
/* 4310 */                                               if (_jspx_eval_html_005foption_005f37 != 1) {
/* 4311 */                                                 out = _jspx_page_context.pushBody();
/* 4312 */                                                 _jspx_th_html_005foption_005f37.setBodyContent((BodyContent)out);
/* 4313 */                                                 _jspx_th_html_005foption_005f37.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4316 */                                                 out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 4317 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f37.doAfterBody();
/* 4318 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4321 */                                               if (_jspx_eval_html_005foption_005f37 != 1) {
/* 4322 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4325 */                                             if (_jspx_th_html_005foption_005f37.doEndTag() == 5) {
/* 4326 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37); return;
/*      */                                             }
/*      */                                             
/* 4329 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37);
/* 4330 */                                             out.write("\n      ");
/*      */                                             
/* 4332 */                                             OptionTag _jspx_th_html_005foption_005f38 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4333 */                                             _jspx_th_html_005foption_005f38.setPageContext(_jspx_page_context);
/* 4334 */                                             _jspx_th_html_005foption_005f38.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4336 */                                             _jspx_th_html_005foption_005f38.setValue("SERVICE:9090");
/* 4337 */                                             int _jspx_eval_html_005foption_005f38 = _jspx_th_html_005foption_005f38.doStartTag();
/* 4338 */                                             if (_jspx_eval_html_005foption_005f38 != 0) {
/* 4339 */                                               if (_jspx_eval_html_005foption_005f38 != 1) {
/* 4340 */                                                 out = _jspx_page_context.pushBody();
/* 4341 */                                                 _jspx_th_html_005foption_005f38.setBodyContent((BodyContent)out);
/* 4342 */                                                 _jspx_th_html_005foption_005f38.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4345 */                                                 out.write(32);
/* 4346 */                                                 out.print(FormatUtil.getString("Service Monitoring"));
/* 4347 */                                                 out.write(32);
/* 4348 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f38.doAfterBody();
/* 4349 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4352 */                                               if (_jspx_eval_html_005foption_005f38 != 1) {
/* 4353 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4356 */                                             if (_jspx_th_html_005foption_005f38.doEndTag() == 5) {
/* 4357 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38); return;
/*      */                                             }
/*      */                                             
/* 4360 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38);
/* 4361 */                                             out.write("\n      ");
/*      */                                             
/* 4363 */                                             OptionTag _jspx_th_html_005foption_005f39 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4364 */                                             _jspx_th_html_005foption_005f39.setPageContext(_jspx_page_context);
/* 4365 */                                             _jspx_th_html_005foption_005f39.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4367 */                                             _jspx_th_html_005foption_005f39.setValue("RMI:1099");
/* 4368 */                                             int _jspx_eval_html_005foption_005f39 = _jspx_th_html_005foption_005f39.doStartTag();
/* 4369 */                                             if (_jspx_eval_html_005foption_005f39 != 0) {
/* 4370 */                                               if (_jspx_eval_html_005foption_005f39 != 1) {
/* 4371 */                                                 out = _jspx_page_context.pushBody();
/* 4372 */                                                 _jspx_th_html_005foption_005f39.setBodyContent((BodyContent)out);
/* 4373 */                                                 _jspx_th_html_005foption_005f39.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4376 */                                                 out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 4377 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f39.doAfterBody();
/* 4378 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4381 */                                               if (_jspx_eval_html_005foption_005f39 != 1) {
/* 4382 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4385 */                                             if (_jspx_th_html_005foption_005f39.doEndTag() == 5) {
/* 4386 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39); return;
/*      */                                             }
/*      */                                             
/* 4389 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39);
/* 4390 */                                             out.write("\n      ");
/*      */                                             
/* 4392 */                                             OptionTag _jspx_th_html_005foption_005f40 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4393 */                                             _jspx_th_html_005foption_005f40.setPageContext(_jspx_page_context);
/* 4394 */                                             _jspx_th_html_005foption_005f40.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4396 */                                             _jspx_th_html_005foption_005f40.setValue("SNMP:161");
/* 4397 */                                             int _jspx_eval_html_005foption_005f40 = _jspx_th_html_005foption_005f40.doStartTag();
/* 4398 */                                             if (_jspx_eval_html_005foption_005f40 != 0) {
/* 4399 */                                               if (_jspx_eval_html_005foption_005f40 != 1) {
/* 4400 */                                                 out = _jspx_page_context.pushBody();
/* 4401 */                                                 _jspx_th_html_005foption_005f40.setBodyContent((BodyContent)out);
/* 4402 */                                                 _jspx_th_html_005foption_005f40.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4405 */                                                 out.print(FormatUtil.getString("SNMP"));
/* 4406 */                                                 out.write(" (V1 or V2c)");
/* 4407 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f40.doAfterBody();
/* 4408 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4411 */                                               if (_jspx_eval_html_005foption_005f40 != 1) {
/* 4412 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4415 */                                             if (_jspx_th_html_005foption_005f40.doEndTag() == 5) {
/* 4416 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40); return;
/*      */                                             }
/*      */                                             
/* 4419 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40);
/* 4420 */                                             out.write("\n      ");
/*      */                                             
/* 4422 */                                             OptionTag _jspx_th_html_005foption_005f41 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4423 */                                             _jspx_th_html_005foption_005f41.setPageContext(_jspx_page_context);
/* 4424 */                                             _jspx_th_html_005foption_005f41.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4426 */                                             _jspx_th_html_005foption_005f41.setValue("Custom-Application");
/* 4427 */                                             int _jspx_eval_html_005foption_005f41 = _jspx_th_html_005foption_005f41.doStartTag();
/* 4428 */                                             if (_jspx_eval_html_005foption_005f41 != 0) {
/* 4429 */                                               if (_jspx_eval_html_005foption_005f41 != 1) {
/* 4430 */                                                 out = _jspx_page_context.pushBody();
/* 4431 */                                                 _jspx_th_html_005foption_005f41.setBodyContent((BodyContent)out);
/* 4432 */                                                 _jspx_th_html_005foption_005f41.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4435 */                                                 out.write(32);
/* 4436 */                                                 out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 4437 */                                                 out.write(32);
/* 4438 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f41.doAfterBody();
/* 4439 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4442 */                                               if (_jspx_eval_html_005foption_005f41 != 1) {
/* 4443 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4446 */                                             if (_jspx_th_html_005foption_005f41.doEndTag() == 5) {
/* 4447 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41); return;
/*      */                                             }
/*      */                                             
/* 4450 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41);
/* 4451 */                                             out.write("\n      ");
/*      */                                             
/* 4453 */                                             OptionTag _jspx_th_html_005foption_005f42 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4454 */                                             _jspx_th_html_005foption_005f42.setPageContext(_jspx_page_context);
/* 4455 */                                             _jspx_th_html_005foption_005f42.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4457 */                                             _jspx_th_html_005foption_005f42.setValue("Script Monitor");
/* 4458 */                                             int _jspx_eval_html_005foption_005f42 = _jspx_th_html_005foption_005f42.doStartTag();
/* 4459 */                                             if (_jspx_eval_html_005foption_005f42 != 0) {
/* 4460 */                                               if (_jspx_eval_html_005foption_005f42 != 1) {
/* 4461 */                                                 out = _jspx_page_context.pushBody();
/* 4462 */                                                 _jspx_th_html_005foption_005f42.setBodyContent((BodyContent)out);
/* 4463 */                                                 _jspx_th_html_005foption_005f42.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4466 */                                                 out.print(FormatUtil.getString("Script Monitor"));
/* 4467 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f42.doAfterBody();
/* 4468 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4471 */                                               if (_jspx_eval_html_005foption_005f42 != 1) {
/* 4472 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4475 */                                             if (_jspx_th_html_005foption_005f42.doEndTag() == 5) {
/* 4476 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42); return;
/*      */                                             }
/*      */                                             
/* 4479 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42);
/* 4480 */                                             out.write("\n       ");
/*      */ 
/*      */                                           }
/* 4483 */                                           else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("WINDOWS"))
/*      */                                           {
/*      */ 
/* 4486 */                                             out.write("\n        ");
/*      */                                             
/* 4488 */                                             OptionTag _jspx_th_html_005foption_005f43 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4489 */                                             _jspx_th_html_005foption_005f43.setPageContext(_jspx_page_context);
/* 4490 */                                             _jspx_th_html_005foption_005f43.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4492 */                                             _jspx_th_html_005foption_005f43.setValue("SYSTEM:9999");
/* 4493 */                                             int _jspx_eval_html_005foption_005f43 = _jspx_th_html_005foption_005f43.doStartTag();
/* 4494 */                                             if (_jspx_eval_html_005foption_005f43 != 0) {
/* 4495 */                                               if (_jspx_eval_html_005foption_005f43 != 1) {
/* 4496 */                                                 out = _jspx_page_context.pushBody();
/* 4497 */                                                 _jspx_th_html_005foption_005f43.setBodyContent((BodyContent)out);
/* 4498 */                                                 _jspx_th_html_005foption_005f43.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4501 */                                                 out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4502 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f43.doAfterBody();
/* 4503 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4506 */                                               if (_jspx_eval_html_005foption_005f43 != 1) {
/* 4507 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4510 */                                             if (_jspx_th_html_005foption_005f43.doEndTag() == 5) {
/* 4511 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43); return;
/*      */                                             }
/*      */                                             
/* 4514 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43);
/* 4515 */                                             out.write("\n       ");
/*      */                                             
/* 4517 */                                             OptionTag _jspx_th_html_005foption_005f44 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4518 */                                             _jspx_th_html_005foption_005f44.setPageContext(_jspx_page_context);
/* 4519 */                                             _jspx_th_html_005foption_005f44.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4521 */                                             _jspx_th_html_005foption_005f44.setValue(".Net:9080");
/* 4522 */                                             int _jspx_eval_html_005foption_005f44 = _jspx_th_html_005foption_005f44.doStartTag();
/* 4523 */                                             if (_jspx_eval_html_005foption_005f44 != 0) {
/* 4524 */                                               if (_jspx_eval_html_005foption_005f44 != 1) {
/* 4525 */                                                 out = _jspx_page_context.pushBody();
/* 4526 */                                                 _jspx_th_html_005foption_005f44.setBodyContent((BodyContent)out);
/* 4527 */                                                 _jspx_th_html_005foption_005f44.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4530 */                                                 out.print(FormatUtil.getString("Microsoft .NET"));
/* 4531 */                                                 out.write(32);
/* 4532 */                                                 out.write(32);
/* 4533 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f44.doAfterBody();
/* 4534 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4537 */                                               if (_jspx_eval_html_005foption_005f44 != 1) {
/* 4538 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4541 */                                             if (_jspx_th_html_005foption_005f44.doEndTag() == 5) {
/* 4542 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44); return;
/*      */                                             }
/*      */                                             
/* 4545 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44);
/* 4546 */                                             out.write("\n      ");
/*      */                                             
/* 4548 */                                             OptionTag _jspx_th_html_005foption_005f45 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4549 */                                             _jspx_th_html_005foption_005f45.setPageContext(_jspx_page_context);
/* 4550 */                                             _jspx_th_html_005foption_005f45.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4552 */                                             _jspx_th_html_005foption_005f45.setValue("MSSQLDB:1433");
/* 4553 */                                             int _jspx_eval_html_005foption_005f45 = _jspx_th_html_005foption_005f45.doStartTag();
/* 4554 */                                             if (_jspx_eval_html_005foption_005f45 != 0) {
/* 4555 */                                               if (_jspx_eval_html_005foption_005f45 != 1) {
/* 4556 */                                                 out = _jspx_page_context.pushBody();
/* 4557 */                                                 _jspx_th_html_005foption_005f45.setBodyContent((BodyContent)out);
/* 4558 */                                                 _jspx_th_html_005foption_005f45.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4561 */                                                 out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 4562 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f45.doAfterBody();
/* 4563 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4566 */                                               if (_jspx_eval_html_005foption_005f45 != 1) {
/* 4567 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4570 */                                             if (_jspx_th_html_005foption_005f45.doEndTag() == 5) {
/* 4571 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45); return;
/*      */                                             }
/*      */                                             
/* 4574 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45);
/* 4575 */                                             out.write("\n      ");
/*      */                                             
/* 4577 */                                             OptionTag _jspx_th_html_005foption_005f46 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4578 */                                             _jspx_th_html_005foption_005f46.setPageContext(_jspx_page_context);
/* 4579 */                                             _jspx_th_html_005foption_005f46.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4581 */                                             _jspx_th_html_005foption_005f46.setValue("Exchange:25");
/* 4582 */                                             int _jspx_eval_html_005foption_005f46 = _jspx_th_html_005foption_005f46.doStartTag();
/* 4583 */                                             if (_jspx_eval_html_005foption_005f46 != 0) {
/* 4584 */                                               if (_jspx_eval_html_005foption_005f46 != 1) {
/* 4585 */                                                 out = _jspx_page_context.pushBody();
/* 4586 */                                                 _jspx_th_html_005foption_005f46.setBodyContent((BodyContent)out);
/* 4587 */                                                 _jspx_th_html_005foption_005f46.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4590 */                                                 out.print(FormatUtil.getString("Exchange Server"));
/* 4591 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f46.doAfterBody();
/* 4592 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4595 */                                               if (_jspx_eval_html_005foption_005f46 != 1) {
/* 4596 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4599 */                                             if (_jspx_th_html_005foption_005f46.doEndTag() == 5) {
/* 4600 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46); return;
/*      */                                             }
/*      */                                             
/* 4603 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46);
/* 4604 */                                             out.write("\n\t  ");
/*      */                                             
/* 4606 */                                             OptionTag _jspx_th_html_005foption_005f47 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4607 */                                             _jspx_th_html_005foption_005f47.setPageContext(_jspx_page_context);
/* 4608 */                                             _jspx_th_html_005foption_005f47.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4610 */                                             _jspx_th_html_005foption_005f47.setValue("IIS:80");
/* 4611 */                                             int _jspx_eval_html_005foption_005f47 = _jspx_th_html_005foption_005f47.doStartTag();
/* 4612 */                                             if (_jspx_eval_html_005foption_005f47 != 0) {
/* 4613 */                                               if (_jspx_eval_html_005foption_005f47 != 1) {
/* 4614 */                                                 out = _jspx_page_context.pushBody();
/* 4615 */                                                 _jspx_th_html_005foption_005f47.setBodyContent((BodyContent)out);
/* 4616 */                                                 _jspx_th_html_005foption_005f47.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4619 */                                                 out.write(32);
/* 4620 */                                                 out.print(FormatUtil.getString("IIS Server"));
/* 4621 */                                                 out.write(32);
/* 4622 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f47.doAfterBody();
/* 4623 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4626 */                                               if (_jspx_eval_html_005foption_005f47 != 1) {
/* 4627 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4630 */                                             if (_jspx_th_html_005foption_005f47.doEndTag() == 5) {
/* 4631 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47); return;
/*      */                                             }
/*      */                                             
/* 4634 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47);
/* 4635 */                                             out.write("\n      ");
/*      */                                             
/* 4637 */                                             OptionTag _jspx_th_html_005foption_005f48 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4638 */                                             _jspx_th_html_005foption_005f48.setPageContext(_jspx_page_context);
/* 4639 */                                             _jspx_th_html_005foption_005f48.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4641 */                                             _jspx_th_html_005foption_005f48.setValue("SERVICE:9090");
/* 4642 */                                             int _jspx_eval_html_005foption_005f48 = _jspx_th_html_005foption_005f48.doStartTag();
/* 4643 */                                             if (_jspx_eval_html_005foption_005f48 != 0) {
/* 4644 */                                               if (_jspx_eval_html_005foption_005f48 != 1) {
/* 4645 */                                                 out = _jspx_page_context.pushBody();
/* 4646 */                                                 _jspx_th_html_005foption_005f48.setBodyContent((BodyContent)out);
/* 4647 */                                                 _jspx_th_html_005foption_005f48.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4650 */                                                 out.write(32);
/* 4651 */                                                 out.print(FormatUtil.getString("Service Monitoring"));
/* 4652 */                                                 out.write(32);
/* 4653 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f48.doAfterBody();
/* 4654 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4657 */                                               if (_jspx_eval_html_005foption_005f48 != 1) {
/* 4658 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4661 */                                             if (_jspx_th_html_005foption_005f48.doEndTag() == 5) {
/* 4662 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48); return;
/*      */                                             }
/*      */                                             
/* 4665 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48);
/* 4666 */                                             out.write("\n\t  ");
/*      */                                             
/* 4668 */                                             OptionTag _jspx_th_html_005foption_005f49 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4669 */                                             _jspx_th_html_005foption_005f49.setPageContext(_jspx_page_context);
/* 4670 */                                             _jspx_th_html_005foption_005f49.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4672 */                                             _jspx_th_html_005foption_005f49.setValue("SNMP:161");
/* 4673 */                                             int _jspx_eval_html_005foption_005f49 = _jspx_th_html_005foption_005f49.doStartTag();
/* 4674 */                                             if (_jspx_eval_html_005foption_005f49 != 0) {
/* 4675 */                                               if (_jspx_eval_html_005foption_005f49 != 1) {
/* 4676 */                                                 out = _jspx_page_context.pushBody();
/* 4677 */                                                 _jspx_th_html_005foption_005f49.setBodyContent((BodyContent)out);
/* 4678 */                                                 _jspx_th_html_005foption_005f49.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4681 */                                                 out.print(FormatUtil.getString("SNMP"));
/* 4682 */                                                 out.write(" (V1 or V2c)");
/* 4683 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f49.doAfterBody();
/* 4684 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4687 */                                               if (_jspx_eval_html_005foption_005f49 != 1) {
/* 4688 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4691 */                                             if (_jspx_th_html_005foption_005f49.doEndTag() == 5) {
/* 4692 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49); return;
/*      */                                             }
/*      */                                             
/* 4695 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49);
/* 4696 */                                             out.write("\n      ");
/*      */                                             
/* 4698 */                                             OptionTag _jspx_th_html_005foption_005f50 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4699 */                                             _jspx_th_html_005foption_005f50.setPageContext(_jspx_page_context);
/* 4700 */                                             _jspx_th_html_005foption_005f50.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4702 */                                             _jspx_th_html_005foption_005f50.setValue("Script Monitor");
/* 4703 */                                             int _jspx_eval_html_005foption_005f50 = _jspx_th_html_005foption_005f50.doStartTag();
/* 4704 */                                             if (_jspx_eval_html_005foption_005f50 != 0) {
/* 4705 */                                               if (_jspx_eval_html_005foption_005f50 != 1) {
/* 4706 */                                                 out = _jspx_page_context.pushBody();
/* 4707 */                                                 _jspx_th_html_005foption_005f50.setBodyContent((BodyContent)out);
/* 4708 */                                                 _jspx_th_html_005foption_005f50.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4711 */                                                 out.print(FormatUtil.getString("Script Monitor"));
/* 4712 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f50.doAfterBody();
/* 4713 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4716 */                                               if (_jspx_eval_html_005foption_005f50 != 1) {
/* 4717 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4720 */                                             if (_jspx_th_html_005foption_005f50.doEndTag() == 5) {
/* 4721 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50); return;
/*      */                                             }
/*      */                                             
/* 4724 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50);
/* 4725 */                                             out.write(10);
/*      */ 
/*      */                                           }
/* 4728 */                                           else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("DATABASE"))
/*      */                                           {
/*      */ 
/* 4731 */                                             out.write("\n\t\t</optgroup>\t<optgroup label=\"");
/* 4732 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 4733 */                                             out.write("\">\n\t\t\t");
/*      */                                             
/* 4735 */                                             OptionTag _jspx_th_html_005foption_005f51 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4736 */                                             _jspx_th_html_005foption_005f51.setPageContext(_jspx_page_context);
/* 4737 */                                             _jspx_th_html_005foption_005f51.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4739 */                                             _jspx_th_html_005foption_005f51.setValue("SYSTEM:9999");
/* 4740 */                                             int _jspx_eval_html_005foption_005f51 = _jspx_th_html_005foption_005f51.doStartTag();
/* 4741 */                                             if (_jspx_eval_html_005foption_005f51 != 0) {
/* 4742 */                                               if (_jspx_eval_html_005foption_005f51 != 1) {
/* 4743 */                                                 out = _jspx_page_context.pushBody();
/* 4744 */                                                 _jspx_th_html_005foption_005f51.setBodyContent((BodyContent)out);
/* 4745 */                                                 _jspx_th_html_005foption_005f51.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4748 */                                                 out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4749 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f51.doAfterBody();
/* 4750 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4753 */                                               if (_jspx_eval_html_005foption_005f51 != 1) {
/* 4754 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4757 */                                             if (_jspx_th_html_005foption_005f51.doEndTag() == 5) {
/* 4758 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51); return;
/*      */                                             }
/*      */                                             
/* 4761 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51);
/* 4762 */                                             out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4763 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 4764 */                                             out.write("\">\n\t\t\t");
/*      */                                             
/* 4766 */                                             OptionTag _jspx_th_html_005foption_005f52 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4767 */                                             _jspx_th_html_005foption_005f52.setPageContext(_jspx_page_context);
/* 4768 */                                             _jspx_th_html_005foption_005f52.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4770 */                                             _jspx_th_html_005foption_005f52.setValue("DB2:50000");
/* 4771 */                                             int _jspx_eval_html_005foption_005f52 = _jspx_th_html_005foption_005f52.doStartTag();
/* 4772 */                                             if (_jspx_eval_html_005foption_005f52 != 0) {
/* 4773 */                                               if (_jspx_eval_html_005foption_005f52 != 1) {
/* 4774 */                                                 out = _jspx_page_context.pushBody();
/* 4775 */                                                 _jspx_th_html_005foption_005f52.setBodyContent((BodyContent)out);
/* 4776 */                                                 _jspx_th_html_005foption_005f52.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4779 */                                                 out.print(FormatUtil.getString("am.webclient.db2.servertype"));
/* 4780 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f52.doAfterBody();
/* 4781 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4784 */                                               if (_jspx_eval_html_005foption_005f52 != 1) {
/* 4785 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4788 */                                             if (_jspx_th_html_005foption_005f52.doEndTag() == 5) {
/* 4789 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52); return;
/*      */                                             }
/*      */                                             
/* 4792 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52);
/* 4793 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 4795 */                                             OptionTag _jspx_th_html_005foption_005f53 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4796 */                                             _jspx_th_html_005foption_005f53.setPageContext(_jspx_page_context);
/* 4797 */                                             _jspx_th_html_005foption_005f53.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4799 */                                             _jspx_th_html_005foption_005f53.setValue("MSSQLDB:1433");
/* 4800 */                                             int _jspx_eval_html_005foption_005f53 = _jspx_th_html_005foption_005f53.doStartTag();
/* 4801 */                                             if (_jspx_eval_html_005foption_005f53 != 0) {
/* 4802 */                                               if (_jspx_eval_html_005foption_005f53 != 1) {
/* 4803 */                                                 out = _jspx_page_context.pushBody();
/* 4804 */                                                 _jspx_th_html_005foption_005f53.setBodyContent((BodyContent)out);
/* 4805 */                                                 _jspx_th_html_005foption_005f53.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4808 */                                                 out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 4809 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f53.doAfterBody();
/* 4810 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4813 */                                               if (_jspx_eval_html_005foption_005f53 != 1) {
/* 4814 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4817 */                                             if (_jspx_th_html_005foption_005f53.doEndTag() == 5) {
/* 4818 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53); return;
/*      */                                             }
/*      */                                             
/* 4821 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53);
/* 4822 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 4824 */                                             OptionTag _jspx_th_html_005foption_005f54 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4825 */                                             _jspx_th_html_005foption_005f54.setPageContext(_jspx_page_context);
/* 4826 */                                             _jspx_th_html_005foption_005f54.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4828 */                                             _jspx_th_html_005foption_005f54.setValue("MYSQLDB:3306");
/* 4829 */                                             int _jspx_eval_html_005foption_005f54 = _jspx_th_html_005foption_005f54.doStartTag();
/* 4830 */                                             if (_jspx_eval_html_005foption_005f54 != 0) {
/* 4831 */                                               if (_jspx_eval_html_005foption_005f54 != 1) {
/* 4832 */                                                 out = _jspx_page_context.pushBody();
/* 4833 */                                                 _jspx_th_html_005foption_005f54.setBodyContent((BodyContent)out);
/* 4834 */                                                 _jspx_th_html_005foption_005f54.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4837 */                                                 out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 4838 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f54.doAfterBody();
/* 4839 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4842 */                                               if (_jspx_eval_html_005foption_005f54 != 1) {
/* 4843 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4846 */                                             if (_jspx_th_html_005foption_005f54.doEndTag() == 5) {
/* 4847 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54); return;
/*      */                                             }
/*      */                                             
/* 4850 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54);
/* 4851 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 4853 */                                             OptionTag _jspx_th_html_005foption_005f55 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4854 */                                             _jspx_th_html_005foption_005f55.setPageContext(_jspx_page_context);
/* 4855 */                                             _jspx_th_html_005foption_005f55.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4857 */                                             _jspx_th_html_005foption_005f55.setValue("ORACLEDB:1521");
/* 4858 */                                             int _jspx_eval_html_005foption_005f55 = _jspx_th_html_005foption_005f55.doStartTag();
/* 4859 */                                             if (_jspx_eval_html_005foption_005f55 != 0) {
/* 4860 */                                               if (_jspx_eval_html_005foption_005f55 != 1) {
/* 4861 */                                                 out = _jspx_page_context.pushBody();
/* 4862 */                                                 _jspx_th_html_005foption_005f55.setBodyContent((BodyContent)out);
/* 4863 */                                                 _jspx_th_html_005foption_005f55.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4866 */                                                 out.print(FormatUtil.getString("am.webclient.oracle.servertype"));
/* 4867 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f55.doAfterBody();
/* 4868 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4871 */                                               if (_jspx_eval_html_005foption_005f55 != 1) {
/* 4872 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4875 */                                             if (_jspx_th_html_005foption_005f55.doEndTag() == 5) {
/* 4876 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55); return;
/*      */                                             }
/*      */                                             
/* 4879 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55);
/* 4880 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 4882 */                                             OptionTag _jspx_th_html_005foption_005f56 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4883 */                                             _jspx_th_html_005foption_005f56.setPageContext(_jspx_page_context);
/* 4884 */                                             _jspx_th_html_005foption_005f56.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4886 */                                             _jspx_th_html_005foption_005f56.setValue("SYBASEDB:5000");
/* 4887 */                                             int _jspx_eval_html_005foption_005f56 = _jspx_th_html_005foption_005f56.doStartTag();
/* 4888 */                                             if (_jspx_eval_html_005foption_005f56 != 0) {
/* 4889 */                                               if (_jspx_eval_html_005foption_005f56 != 1) {
/* 4890 */                                                 out = _jspx_page_context.pushBody();
/* 4891 */                                                 _jspx_th_html_005foption_005f56.setBodyContent((BodyContent)out);
/* 4892 */                                                 _jspx_th_html_005foption_005f56.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4895 */                                                 out.print(FormatUtil.getString("Sybase"));
/* 4896 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f56.doAfterBody();
/* 4897 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4900 */                                               if (_jspx_eval_html_005foption_005f56 != 1) {
/* 4901 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4904 */                                             if (_jspx_th_html_005foption_005f56.doEndTag() == 5) {
/* 4905 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56); return;
/*      */                                             }
/*      */                                             
/* 4908 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56);
/* 4909 */                                             out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4910 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 4911 */                                             out.write("\">\n\t\t\t");
/*      */                                             
/* 4913 */                                             OptionTag _jspx_th_html_005foption_005f57 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4914 */                                             _jspx_th_html_005foption_005f57.setPageContext(_jspx_page_context);
/* 4915 */                                             _jspx_th_html_005foption_005f57.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4917 */                                             _jspx_th_html_005foption_005f57.setValue("SERVICE:9090");
/* 4918 */                                             int _jspx_eval_html_005foption_005f57 = _jspx_th_html_005foption_005f57.doStartTag();
/* 4919 */                                             if (_jspx_eval_html_005foption_005f57 != 0) {
/* 4920 */                                               if (_jspx_eval_html_005foption_005f57 != 1) {
/* 4921 */                                                 out = _jspx_page_context.pushBody();
/* 4922 */                                                 _jspx_th_html_005foption_005f57.setBodyContent((BodyContent)out);
/* 4923 */                                                 _jspx_th_html_005foption_005f57.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4926 */                                                 out.write(32);
/* 4927 */                                                 out.print(FormatUtil.getString("Service Monitoring"));
/* 4928 */                                                 out.write(32);
/* 4929 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f57.doAfterBody();
/* 4930 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4933 */                                               if (_jspx_eval_html_005foption_005f57 != 1) {
/* 4934 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4937 */                                             if (_jspx_th_html_005foption_005f57.doEndTag() == 5) {
/* 4938 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57); return;
/*      */                                             }
/*      */                                             
/* 4941 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57);
/* 4942 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 4944 */                                             OptionTag _jspx_th_html_005foption_005f58 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4945 */                                             _jspx_th_html_005foption_005f58.setPageContext(_jspx_page_context);
/* 4946 */                                             _jspx_th_html_005foption_005f58.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4948 */                                             _jspx_th_html_005foption_005f58.setValue("SNMP:161");
/* 4949 */                                             int _jspx_eval_html_005foption_005f58 = _jspx_th_html_005foption_005f58.doStartTag();
/* 4950 */                                             if (_jspx_eval_html_005foption_005f58 != 0) {
/* 4951 */                                               if (_jspx_eval_html_005foption_005f58 != 1) {
/* 4952 */                                                 out = _jspx_page_context.pushBody();
/* 4953 */                                                 _jspx_th_html_005foption_005f58.setBodyContent((BodyContent)out);
/* 4954 */                                                 _jspx_th_html_005foption_005f58.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4957 */                                                 out.print(FormatUtil.getString("SNMP"));
/* 4958 */                                                 out.write(" (V1 or V2c)");
/* 4959 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f58.doAfterBody();
/* 4960 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4963 */                                               if (_jspx_eval_html_005foption_005f58 != 1) {
/* 4964 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4967 */                                             if (_jspx_th_html_005foption_005f58.doEndTag() == 5) {
/* 4968 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58); return;
/*      */                                             }
/*      */                                             
/* 4971 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58);
/* 4972 */                                             out.write("</optgroup>");
/* 4973 */                                             out.write("\n\t\t\t<optgroup label=\"");
/* 4974 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 4975 */                                             out.write("\">\n\t\t\t");
/*      */                                             
/* 4977 */                                             OptionTag _jspx_th_html_005foption_005f59 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4978 */                                             _jspx_th_html_005foption_005f59.setPageContext(_jspx_page_context);
/* 4979 */                                             _jspx_th_html_005foption_005f59.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4981 */                                             _jspx_th_html_005foption_005f59.setValue("UrlMonitor");
/* 4982 */                                             int _jspx_eval_html_005foption_005f59 = _jspx_th_html_005foption_005f59.doStartTag();
/* 4983 */                                             if (_jspx_eval_html_005foption_005f59 != 0) {
/* 4984 */                                               if (_jspx_eval_html_005foption_005f59 != 1) {
/* 4985 */                                                 out = _jspx_page_context.pushBody();
/* 4986 */                                                 _jspx_th_html_005foption_005f59.setBodyContent((BodyContent)out);
/* 4987 */                                                 _jspx_th_html_005foption_005f59.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4990 */                                                 out.print(FormatUtil.getString("HTTP-URLs"));
/* 4991 */                                                 out.write(32);
/* 4992 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f59.doAfterBody();
/* 4993 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4996 */                                               if (_jspx_eval_html_005foption_005f59 != 1) {
/* 4997 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5000 */                                             if (_jspx_th_html_005foption_005f59.doEndTag() == 5) {
/* 5001 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59); return;
/*      */                                             }
/*      */                                             
/* 5004 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59);
/* 5005 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 5007 */                                             OptionTag _jspx_th_html_005foption_005f60 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5008 */                                             _jspx_th_html_005foption_005f60.setPageContext(_jspx_page_context);
/* 5009 */                                             _jspx_th_html_005foption_005f60.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 5011 */                                             _jspx_th_html_005foption_005f60.setValue("UrlSeq");
/* 5012 */                                             int _jspx_eval_html_005foption_005f60 = _jspx_th_html_005foption_005f60.doStartTag();
/* 5013 */                                             if (_jspx_eval_html_005foption_005f60 != 0) {
/* 5014 */                                               if (_jspx_eval_html_005foption_005f60 != 1) {
/* 5015 */                                                 out = _jspx_page_context.pushBody();
/* 5016 */                                                 _jspx_th_html_005foption_005f60.setBodyContent((BodyContent)out);
/* 5017 */                                                 _jspx_th_html_005foption_005f60.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 5020 */                                                 out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 5021 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f60.doAfterBody();
/* 5022 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5025 */                                               if (_jspx_eval_html_005foption_005f60 != 1) {
/* 5026 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5029 */                                             if (_jspx_th_html_005foption_005f60.doEndTag() == 5) {
/* 5030 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60); return;
/*      */                                             }
/*      */                                             
/* 5033 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60);
/* 5034 */                                             out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 5035 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 5036 */                                             out.write("\">\n\t\t\t");
/*      */                                             
/* 5038 */                                             OptionTag _jspx_th_html_005foption_005f61 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5039 */                                             _jspx_th_html_005foption_005f61.setPageContext(_jspx_page_context);
/* 5040 */                                             _jspx_th_html_005foption_005f61.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 5042 */                                             _jspx_th_html_005foption_005f61.setValue("Script Monitor");
/* 5043 */                                             int _jspx_eval_html_005foption_005f61 = _jspx_th_html_005foption_005f61.doStartTag();
/* 5044 */                                             if (_jspx_eval_html_005foption_005f61 != 0) {
/* 5045 */                                               if (_jspx_eval_html_005foption_005f61 != 1) {
/* 5046 */                                                 out = _jspx_page_context.pushBody();
/* 5047 */                                                 _jspx_th_html_005foption_005f61.setBodyContent((BodyContent)out);
/* 5048 */                                                 _jspx_th_html_005foption_005f61.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 5051 */                                                 out.print(FormatUtil.getString("Script Monitor"));
/* 5052 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f61.doAfterBody();
/* 5053 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5056 */                                               if (_jspx_eval_html_005foption_005f61 != 1) {
/* 5057 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5060 */                                             if (_jspx_th_html_005foption_005f61.doEndTag() == 5) {
/* 5061 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61); return;
/*      */                                             }
/*      */                                             
/* 5064 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61);
/* 5065 */                                             out.write(10);
/* 5066 */                                             out.write(10);
/*      */                                           }
/*      */                                           
/*      */ 
/* 5070 */                                           out.write("\n\n\n\n      ");
/* 5071 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 5072 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 5075 */                                         if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 5076 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 5079 */                                       if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 5080 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                       }
/*      */                                       
/* 5083 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 5084 */                                       out.write("\n                      \n      ");
/* 5085 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 5086 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 5090 */                                   if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 5091 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                                   }
/*      */                                   
/* 5094 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5095 */                                   out.write("\n      ");
/* 5096 */                                   if (_jspx_meth_c_005fif_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5098 */                                   out.write("\n      </td>\n      \n      ");
/* 5099 */                                   if (request.getParameter("type") != null) {
/* 5100 */                                     isConfMonitor = confMonConfig.isConfMonitor(request.getParameter("type"));
/* 5101 */                                     String restype = request.getParameter("type");
/* 5102 */                                     if (restype.indexOf(":") != -1) {
/* 5103 */                                       restype = restype.substring(0, restype.indexOf(":"));
/*      */                                     }
/* 5105 */                                     if (((isConfMonitor) && (!restype.equals("QueryMonitor"))) || ((!restype.equals("JMX1.2-MX4J-RMI")) && (!restype.equals("Generic WMI")) && (!restype.equals("Script Monitor")) && (!restype.equals("Custom-Application")) && (!restype.equals("File System Monitor")) && (!restype.equals("QueryMonitor")) && (!restype.equals("SNMP")) && (!restype.equals("TELNET")) && (!restype.equals("Exchange")) && (!restype.equals("WTA")) && (!restype.equals("WEB")) && (!restype.equals("UrlSeq")) && (!restype.equals("PHP")) && (!restype.equals("IIS")) && (!restype.equals("APACHE")) && (!restype.equals("MAIL")) && (!restype.equals("All")) && (restype.indexOf("SAP") == -1))) {
/* 5106 */                                       out.write("\n      \t<td class=\"tableheading-monitor-config itadmin-hide\" align=\"right\">\n      \n      \t\t<div id=\"Conf-bulk-configuration\"> \n\t\t\t    \t\t<a href=\"javascript:void(0)\"  onclick=\"window.open('/BulkAddMonitors.do?method=showBulkImportForm&type=");
/* 5107 */                                       out.print(restype);
/* 5108 */                                       out.write("','windowName','toolbar=no,status=no,menubar=no,width=1000,height=500,scrollbars=yes')\" class=\"staticlinks\" >");
/* 5109 */                                       out.print(FormatUtil.getString("am.webclient.admin.bulkimport.label.text"));
/* 5110 */                                       out.write("</a>\n\t    \t</div><img src=\"/images/script-icon.gif\">\n   \t   </td>\n      \n      \t");
/*      */                                     }
/*      */                                   }
/* 5113 */                                   out.write("     \n      \n  </tr>\n</table>\n <script src=\"/template/chosen.jquery.min.js\" type=\"text/javascript\"></script><script type=\"text/javascript\"> $(\".formtext\").chosen();  </script>\n");
/* 5114 */                                   out.write("\n\n<input type=\"hidden\" name=\"method\"/>\n        <input type=\"hidden\" name=\"addtoha\" value=\"true\">\n        <input type=\"hidden\" name=\"hideFieldsForIT360\" value=\"");
/* 5115 */                                   out.print(request.getParameter("hideFieldsForIT360"));
/* 5116 */                                   out.write("\">\n<table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" bgcolor=\"FFFFFF\" class=\"lrborder\">\n  <tr>\n    <td width=\"20%\" height=\"32\" valign='top' class=\"bodytext label-align addmonitor-label\"><label>");
/* 5117 */                                   out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 5118 */                                   out.write("<span class=\"mandatory\">*</span></label></td>\n    <td width=\"80%\" class=\"bodytext\"> ");
/* 5119 */                                   if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5121 */                                   out.write("\n    </td>\n  </tr>\n  <tr>\n      <td valign='top'  class=\"bodytext label-align addmonitor-label\"><label>");
/* 5122 */                                   out.print(FormatUtil.getString("am.webclient.wmi.descritption.text"));
/* 5123 */                                   out.write("</label></td>\n      <td  class=\"bodytext\"> ");
/*      */                                   
/* 5125 */                                   TextareaTag _jspx_th_html_005ftextarea_005f0 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols.get(TextareaTag.class);
/* 5126 */                                   _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 5127 */                                   _jspx_th_html_005ftextarea_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 5129 */                                   _jspx_th_html_005ftextarea_005f0.setProperty("description");
/*      */                                   
/* 5131 */                                   _jspx_th_html_005ftextarea_005f0.setCols("27");
/*      */                                   
/* 5133 */                                   _jspx_th_html_005ftextarea_005f0.setRows("3");
/*      */                                   
/* 5135 */                                   _jspx_th_html_005ftextarea_005f0.setStyleClass("formtextarea xlarge");
/* 5136 */                                   int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 5137 */                                   if (_jspx_eval_html_005ftextarea_005f0 != 0) {
/* 5138 */                                     if (_jspx_eval_html_005ftextarea_005f0 != 1) {
/* 5139 */                                       out = _jspx_page_context.pushBody();
/* 5140 */                                       _jspx_th_html_005ftextarea_005f0.setBodyContent((BodyContent)out);
/* 5141 */                                       _jspx_th_html_005ftextarea_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 5144 */                                       out.print(request.getAttribute("customapplicationdescription") != null ? request.getAttribute("customapplicationdescription") : "CRM Application");
/* 5145 */                                       int evalDoAfterBody = _jspx_th_html_005ftextarea_005f0.doAfterBody();
/* 5146 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 5149 */                                     if (_jspx_eval_html_005ftextarea_005f0 != 1) {
/* 5150 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 5153 */                                   if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 5154 */                                     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols.reuse(_jspx_th_html_005ftextarea_005f0); return;
/*      */                                   }
/*      */                                   
/* 5157 */                                   this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols.reuse(_jspx_th_html_005ftextarea_005f0);
/* 5158 */                                   out.write("\n      </td>\n  </tr>\n  <tr>\n        <td height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 5159 */                                   out.print(FormatUtil.getString("am.webclient.common.pollinginterval.text"));
/* 5160 */                                   out.write("<span class=\"mandatory\">*</span></label></td>\n        <td height=\"28\" colspan=\"2\" > ");
/* 5161 */                                   if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5163 */                                   out.write("<span class=\"bodytext\">&nbsp;&nbsp;&nbsp;");
/* 5164 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.minutes"));
/* 5165 */                                   out.write("</span></td>\n  </tr>\n  <tr>\n\n              <td class=\"bodytext label-align addmonitor-label\" width=\"25%\"><label>");
/* 5166 */                                   out.print(FormatUtil.getString("am.webclient.wmi.choosehost"));
/* 5167 */                                   out.write("</label></td>\n              <td width=\"75%\" class=\"bodytext\"> ");
/*      */                                   
/* 5169 */                                   SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 5170 */                                   _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 5171 */                                   _jspx_th_html_005fselect_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 5173 */                                   _jspx_th_html_005fselect_005f1.setProperty("choosehost");
/*      */                                   
/* 5175 */                                   _jspx_th_html_005fselect_005f1.setOnchange("javascript:managenewHost()");
/*      */                                   
/* 5177 */                                   _jspx_th_html_005fselect_005f1.setStyleClass("formtext default");
/* 5178 */                                   int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 5179 */                                   if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 5180 */                                     if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 5181 */                                       out = _jspx_page_context.pushBody();
/* 5182 */                                       _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 5183 */                                       _jspx_th_html_005fselect_005f1.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 5186 */                                       out.write("\n                ");
/*      */                                       
/* 5188 */                                       OptionTag _jspx_th_html_005foption_005f62 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5189 */                                       _jspx_th_html_005foption_005f62.setPageContext(_jspx_page_context);
/* 5190 */                                       _jspx_th_html_005foption_005f62.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                       
/* 5192 */                                       _jspx_th_html_005foption_005f62.setValue("-2");
/* 5193 */                                       int _jspx_eval_html_005foption_005f62 = _jspx_th_html_005foption_005f62.doStartTag();
/* 5194 */                                       if (_jspx_eval_html_005foption_005f62 != 0) {
/* 5195 */                                         if (_jspx_eval_html_005foption_005f62 != 1) {
/* 5196 */                                           out = _jspx_page_context.pushBody();
/* 5197 */                                           _jspx_th_html_005foption_005f62.setBodyContent((BodyContent)out);
/* 5198 */                                           _jspx_th_html_005foption_005f62.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 5201 */                                           out.print(FormatUtil.getString("am.webclient.wmi.selecthost.text"));
/* 5202 */                                           int evalDoAfterBody = _jspx_th_html_005foption_005f62.doAfterBody();
/* 5203 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 5206 */                                         if (_jspx_eval_html_005foption_005f62 != 1) {
/* 5207 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 5210 */                                       if (_jspx_th_html_005foption_005f62.doEndTag() == 5) {
/* 5211 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f62); return;
/*      */                                       }
/*      */                                       
/* 5214 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f62);
/* 5215 */                                       out.write(32);
/*      */                                       
/* 5217 */                                       OptionTag _jspx_th_html_005foption_005f63 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 5218 */                                       _jspx_th_html_005foption_005f63.setPageContext(_jspx_page_context);
/* 5219 */                                       _jspx_th_html_005foption_005f63.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                       
/* 5221 */                                       _jspx_th_html_005foption_005f63.setValue("-1");
/*      */                                       
/* 5223 */                                       _jspx_th_html_005foption_005f63.setStyle("font-weight: bold;");
/* 5224 */                                       int _jspx_eval_html_005foption_005f63 = _jspx_th_html_005foption_005f63.doStartTag();
/* 5225 */                                       if (_jspx_eval_html_005foption_005f63 != 0) {
/* 5226 */                                         if (_jspx_eval_html_005foption_005f63 != 1) {
/* 5227 */                                           out = _jspx_page_context.pushBody();
/* 5228 */                                           _jspx_th_html_005foption_005f63.setBodyContent((BodyContent)out);
/* 5229 */                                           _jspx_th_html_005foption_005f63.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 5232 */                                           out.print(FormatUtil.getString("am.webclient.wmi.newhost"));
/* 5233 */                                           int evalDoAfterBody = _jspx_th_html_005foption_005f63.doAfterBody();
/* 5234 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 5237 */                                         if (_jspx_eval_html_005foption_005f63 != 1) {
/* 5238 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 5241 */                                       if (_jspx_th_html_005foption_005f63.doEndTag() == 5) {
/* 5242 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f63); return;
/*      */                                       }
/*      */                                       
/* 5245 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f63);
/* 5246 */                                       out.write("\n                ");
/*      */                                       
/* 5248 */                                       String hostquery = "select ID,HOSTNAME from AM_SCRIPTHOSTDETAILS where MODE='WMI'";
/* 5249 */                                       AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */                                       try
/*      */                                       {
/* 5252 */                                         ResultSet rs = AMConnectionPool.executeQueryStmt(hostquery);
/* 5253 */                                         while (rs.next())
/*      */                                         {
/*      */ 
/* 5256 */                                           out.write("\n                ");
/*      */                                           
/* 5258 */                                           OptionTag _jspx_th_html_005foption_005f64 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5259 */                                           _jspx_th_html_005foption_005f64.setPageContext(_jspx_page_context);
/* 5260 */                                           _jspx_th_html_005foption_005f64.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                           
/* 5262 */                                           _jspx_th_html_005foption_005f64.setValue(rs.getString(1));
/* 5263 */                                           int _jspx_eval_html_005foption_005f64 = _jspx_th_html_005foption_005f64.doStartTag();
/* 5264 */                                           if (_jspx_eval_html_005foption_005f64 != 0) {
/* 5265 */                                             if (_jspx_eval_html_005foption_005f64 != 1) {
/* 5266 */                                               out = _jspx_page_context.pushBody();
/* 5267 */                                               _jspx_th_html_005foption_005f64.setBodyContent((BodyContent)out);
/* 5268 */                                               _jspx_th_html_005foption_005f64.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 5271 */                                               out.print(rs.getString(2));
/* 5272 */                                               out.write("\n                ");
/* 5273 */                                               int evalDoAfterBody = _jspx_th_html_005foption_005f64.doAfterBody();
/* 5274 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 5277 */                                             if (_jspx_eval_html_005foption_005f64 != 1) {
/* 5278 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 5281 */                                           if (_jspx_th_html_005foption_005f64.doEndTag() == 5) {
/* 5282 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f64); return;
/*      */                                           }
/*      */                                           
/* 5285 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f64);
/* 5286 */                                           out.write("\n                ");
/*      */                                         }
/*      */                                         
/* 5289 */                                         rs.close();
/*      */                                       }
/*      */                                       catch (Exception exc) {}
/*      */                                       
/*      */ 
/*      */ 
/*      */ 
/* 5296 */                                       out.write("\n                ");
/* 5297 */                                       int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 5298 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 5301 */                                     if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 5302 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 5305 */                                   if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 5306 */                                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */                                   }
/*      */                                   
/* 5309 */                                   this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 5310 */                                   out.write(" &nbsp; &nbsp;&nbsp;<a href=\"javascript:void(0)\" class=\"staticlinks\" onclick=\"deletehost()\">");
/* 5311 */                                   out.print(FormatUtil.getString("am.webclient.wmi.host.deletehost.link.text"));
/* 5312 */                                   out.write("</a></td>\n            </tr>\n\n<tr>\n  <td colspan=\"2\"><div id=\"newhost\" style=\"display:none;\">\n          <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"bg-lite dottedline\">\n            <tr class=\"yellowgrayborder\">\n              <td height=28 width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 5313 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.hostname"));
/* 5314 */                                   out.write("<span class=\"mandatory\">*</span></label></td>\n              <td height=28 width=\"75%\"> ");
/* 5315 */                                   if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5317 */                                   out.write("</td>\n            </tr>\n            <tr class=\"yellowgrayborder\">\n              <td height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 5318 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/* 5319 */                                   out.write("<span class=\"mandatory\">*</span></label></td>\n              <td height=\"28\" width=\"75%\"><input type=\"text\" name=\"username\" class=\"formtext default\" autocomplete=\"off\" /><!--");
/* 5320 */                                   if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5322 */                                   out.write("-->&nbsp;&nbsp;\n              </td>\n            </tr >\n            <tr class=\"yellowgrayborder\">\n              <td width=\"25%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 5323 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/* 5324 */                                   out.write("<span class=\"mandatory\">*</span></label></td>\n              <td width=\"75%\" height=\"28\"> <input type=\"password\" name=\"password\" class=\"formtext default\" autocomplete=\"off\" /><!--");
/* 5325 */                                   if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5327 */                                   out.write("-->\n               </td>\n            </tr>\n            </table>\n        </div></td>\n  </tr>\n  <!-- new line -->\n</table>\n\n\n\t\t<TABLE width=\"99%\" BORDER=\"0\" cellpadding=\"6\" CELLSPACING=\"0\" class=\"lrborder \">\n");
/*      */                                   
/* 5329 */                                   if (EnterpriseUtil.isAdminServer())
/*      */                                   {
/*      */ 
/* 5332 */                                     out.write(10);
/* 5333 */                                     out.write(9);
/* 5334 */                                     out.write(9);
/* 5335 */                                     JspRuntimeLibrary.include(request, response, "/jsp/includes/ShowGroupsDetails.jsp" + ("/jsp/includes/ShowGroupsDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("showDetailsOf", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("managedServerGroups", request.getCharacterEncoding()), out, false);
/* 5336 */                                     out.write(9);
/* 5337 */                                     out.write(9);
/* 5338 */                                     out.write(10);
/*      */                                   }
/*      */                                   
/*      */ 
/* 5342 */                                   out.write(10);
/* 5343 */                                   out.write(9);
/* 5344 */                                   JspRuntimeLibrary.include(request, response, "/jsp/includes/ShowGroupsDetails.jsp" + ("/jsp/includes/ShowGroupsDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("showDetailsOf", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("monitorGroups", request.getCharacterEncoding()), out, false);
/* 5345 */                                   out.write(10);
/* 5346 */                                   out.write(10);
/*      */                                   
/* 5348 */                                   IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5349 */                                   _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 5350 */                                   _jspx_th_c_005fif_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 5352 */                                   _jspx_th_c_005fif_005f4.setTest("${empty param.wiz}");
/* 5353 */                                   int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 5354 */                                   if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                     for (;;) {
/* 5356 */                                       out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"25%\" align=\"left\"  class=\"tablebottom\">&nbsp;</td>\n    <td height=\"31\" align=\"left\"  class=\"tablebottom\">\n      <input name=\"addwmiperf\" type=\"button\" class=\"buttons btn_highlt\" value='");
/* 5357 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.addmonitor"));
/* 5358 */                                       out.write("' onclick=\"validateAndSubmit()\"/>\n      &nbsp;&nbsp;<input type=\"button\" value=\"");
/* 5359 */                                       out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 5360 */                                       out.write("\" class='buttons btn_link' onclick=\"history.back();\" />\n    </td>\n  </tr>\n</table>\n");
/* 5361 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 5362 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 5366 */                                   if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 5367 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                   }
/*      */                                   
/* 5370 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 5371 */                                   out.write("\n</td>\n<td width=\"30%\" valign=\"top\">\n");
/* 5372 */                                   if (title.equals("Generic WMI")) {
/* 5373 */                                     StringBuffer helpcardKey = new StringBuffer();
/*      */                                     
/* 5375 */                                     helpcardKey.append(FormatUtil.getString("am.GenericWMI.helpcard.text"));
/* 5376 */                                     out.write(10);
/* 5377 */                                     JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(helpcardKey.toString()), request.getCharacterEncoding()), out, false);
/* 5378 */                                     out.write("\t  \n");
/*      */                                   }
/* 5380 */                                   out.write(10);
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 5386 */                                   out.write("\n<br><br>\n<table width=\"98%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" class=\"grayfullborder\">\n<tr>\n    <td height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 5387 */                                   out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.status"));
/* 5388 */                                   out.write("</span></td>\n    </tr>\n    <tr>\n    <td class=\"bodytext\">\n    <img border=\"0\" align=\"absmiddle\" alt=\"Monitoring Initiated successfully\" src=\"/images/icon_monitor_success.gif\"/>&nbsp;&nbsp;\n\t");
/* 5389 */                                   out.print(FormatUtil.getString("am.webclient.discovery.host.successfullyiniated.text"));
/* 5390 */                                   out.write(46);
/* 5391 */                                   out.write(32);
/* 5392 */                                   out.write(32);
/* 5393 */                                   out.print(FormatUtil.getString("am.webclient.wmi.associate.attrib.txt"));
/* 5394 */                                   out.write("\n    </td>\n    </tr>\n    </table>\n<br><br>\n<TABLE width=\"99%\" BORDER=\"0\" cellpadding=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\">\n  <tr>\n    <td align=\"center\">\n      <input name=\"closeButton\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 5395 */                                   out.print(FormatUtil.getString("Close Window"));
/* 5396 */                                   out.write("\" onclick=\"closePopUpWindow();\">\n      </td>\n      </tr>\n      </table>\n");
/*      */                                 }
/*      */                                 
/*      */ 
/* 5400 */                                 out.write("\n\n\n\n");
/* 5401 */                                 int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 5402 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 5406 */                             if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 5407 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                             }
/*      */                             
/* 5410 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 5411 */                             out.write("\n</td>\n        </tr>\n        </table>\n\n\n\n<script>\n\nfunction loadSite()\n{\n\tdocument.AMActionForm.haid.options.length=0;\n\tvar formCustomerId = document.AMActionForm.organization.value;\n\tvar siteName;\n\tvar siteId;\n\tvar customerId;\n\tvar rowCount=0;\n\tdocument.AMActionForm.haid.options[rowCount++] = new Option('-Select Site-','-'); //No I18N\n\t");
/* 5412 */                             if (_jspx_meth_c_005fforEach_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                               return;
/* 5414 */                             out.write("\n}\n\n\nfunction resetname(name)\n{\n\tif(name='groupname')\n\t{\n\t\tdocument.AMActionForm.groupname.value='';\n\t}\n\tif(name='subgroupname')\n\t{\n\t\tdocument.AMActionForm.subgroupname.value='';\n\t}\n}\nfunction createGroup()\n{\n\tif(document.AMActionForm.groupname.value=='')\n\t{\n\t\talert(\"");
/* 5415 */                             out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/* 5416 */                             out.write("\");\n\t\thideDiv('group');\n\t\tshowDiv('creategroup');\n\t\tdocument.AMActionForm.groupname.focus();\n\t\treturn false;\n\t}\n\telse\n\t{\n\t\thideDiv('creategroup');\n\t\tvar a=document.AMActionForm.groupname.value;\n\t\turl=\"/adminAction.do?method=createMonitorGroup&groupname=\"+encodeURIComponent(a);\t//NO I18N\n\t\thttp.open(\"GET\",url,true);\n\t\thttp.onreadystatechange = getActionTypes;\n\t\thttp.send(null);\n\t\tshowDiv('group');\n\t}\n\n}  \nfunction check()\n{\n\thideDiv(\"creategroup\");\n\thideDiv(\"createsubgroup\");\n\thideDiv(\"groupmessage\");\n\tvar flag='");
/* 5417 */                             out.print(com.adventnet.appmanager.util.Constants.subGroupsEnabled);
/* 5418 */                             out.write("';\n\tif(flag==\"true\")\n\t{\n\t\tif(trimAll(document.AMActionForm.haid.value) == '' || document.AMActionForm.haid.value=='-')\n\t\t{\n\t\t\thideDiv(\"subgroup\");\n\t\t\tshowDiv(\"group\");\n\t\t}\n\t\telse\n\t\t{\n\t\t\thideDiv(\"group\");\n\t\t\tshowDiv(\"subgroup\");\n\t\t}\n\t}\n\telse\n\t{\n\t\tshowDiv(\"group\");\n\t}\n}\nfunction createsubGroup()\n{\n\tif(trimAll(document.AMActionForm.haid.value) == '' || document.AMActionForm.haid.value=='-')\n\t{\n\t\talert(\"");
/* 5419 */                             out.print(FormatUtil.getString("am.webclient.monitorsubgroup.monitoralert"));
/* 5420 */                             out.write("\");\t\t\n\t\tdocument.AMActionForm.haid.focus();\n\t}\n\telse\n\t{\n\t\tif(document.AMActionForm.subgroupname.value=='')\n\t\t{\n\t\t\talert(\"");
/* 5421 */                             out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/* 5422 */                             out.write("\");\n\t\t\tdocument.AMActionForm.subgroupname.focus();\n\t\t\treturn false;\n\t\t}\n\t\telse\n\t\t{\n\t\t\thideDiv('createsubgroup');\n\t\t\tvar a=document.AMActionForm.subgroupname.value;\n\t\t\tvar haid=document.AMActionForm.haid.value;\n\t\t\turl=\"/adminAction.do?method=createSubGroup&haid=\"+haid+\"&subgroupname=\"+encodeURIComponent(a);\t//NO I18N\n\t\t\thttp.open(\"GET\",url,true);\n\t\t\thttp.onreadystatechange = getActionTypes;\n\t\t\thttp.send(null);\n\t\t}\n\t\tshowDiv('subgroup');\n    }\n\n}  \n\n function getActionTypes()\n{\n    if(http.readyState == 4)\n    {\n\t\tvar result = http.responseText;\n\t\tvar id=result;\n\t\tvar stringtokens=id.split(\",\");\n\t\tsid = stringtokens[2];\n\t\tsname=stringtokens[1];\n\t\tsname=decodeURIComponent(stringtokens[1]);\n\t\tsmessage=stringtokens[0];\n\t\tif (sname==null || sname=='undefined')\n\t\t{\n\t\t\tshowDiv(\"groupmessage\");\n\t\t\tdocument.getElementById(\"groupmessage\").innerHTML=smessage;\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.AMActionForm.haid.options[document.AMActionForm.haid.length] =new Option(sname,sid,false,true);\n\t\t\thideDiv(\"creategroup\");\n\t\t\thideDiv(\"createsubgroup\");\n");
/* 5423 */                             out.write("\t\t\thideDiv(\"group\");\n\t\t\tshowDiv(\"subgroup\");\n\t\t\tshowDiv(\"groupmessage\");\n\t\t\tdocument.getElementById(\"groupmessage\").innerHTML=smessage;\n\t  \t}\n\t}\n}\n\n\n\n\nif(document.AMActionForm.choosehost.value=='-1')\n{\n javascript:showDiv('newhost');\n}\nelse\n{\n javascript:hideDiv('newhost');\n}\n\nfunction cancel() {\n\n\n");
/* 5424 */                             if (_jspx_meth_c_005fif_005f8(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                               return;
/* 5426 */                             out.write("\n\n}\n\nfunction validateAndShowAlerts()\n{\n\tdocument.AMActionForm.configurealerts.value='true';\n\tvalidateAndSubmit();\n}\n\nfunction validateAndSubmit()\n{\n");
/* 5427 */                             if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                               return;
/* 5429 */                             out.write(10);
/*      */                             
/* 5431 */                             NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 5432 */                             _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 5433 */                             _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                             
/* 5435 */                             _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 5436 */                             int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 5437 */                             if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                               for (;;) {
/* 5439 */                                 out.write("\n\n   if(trimAll(document.AMActionForm.displayname.value)==\"\")\n   {\n   \talert('");
/* 5440 */                                 out.print(FormatUtil.getString("am.webclient.wmi.emptymonitorname.text"));
/* 5441 */                                 out.write("');\n   \treturn;\n   }\n    var dispname=document.AMActionForm.displayname.value;\n\tif(dispname.indexOf(\"'\") != -1)\n        {\n            alert(\"");
/* 5442 */                                 out.print(FormatUtil.getString("am.webclient.common.jsalertforsinglequote.text"));
/* 5443 */                                 out.write("\");\n                document.AMActionForm.displayname.focus();\n                return;\n         }\n    var temp = trimAll(document.AMActionForm.pollInterval.value);\n   if((temp == '') || !(isPositiveInteger(temp)) || (temp == '0'))\n   {\n\talert(\"");
/* 5444 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.polling"));
/* 5445 */                                 out.write("\");\n\tdocument.AMActionForm.pollInterval.select();\n\treturn;\n   }\n   document.AMActionForm.method.value='createWMIPerfmonitor'\n    if(document.AMActionForm.choosehost.value=='-1')\n           {\n               if(document.AMActionForm.host.value=='')\n               {\n                 alert(\"");
/* 5446 */                                 out.print(FormatUtil.getString("am.webclient.wmi.emptyhost.alert.text"));
/* 5447 */                                 out.write("\");\n                 document.AMActionForm.host.focus();\n                 return;\n               }\n               if(document.AMActionForm.username.value=='')\n               {\n                 alert(\"");
/* 5448 */                                 out.print(FormatUtil.getString("am.webclient.wmi.usernameempty.alert.text"));
/* 5449 */                                 out.write("\");\n                 document.AMActionForm.username.focus();\n                 return;\n               }\n               if(document.AMActionForm.password.value=='')\n               {\n                 alert(\"");
/* 5450 */                                 out.print(FormatUtil.getString("am.webclient.wmi.passwordempty.alert.text"));
/* 5451 */                                 out.write("\");\n                 document.AMActionForm.password.focus();\n                 return;\n               }\n             }\n          else\n   \t{\n   \t\tif(document.AMActionForm.choosehost.value=='-2')\n   \t\t{\n   \t\t\talert('");
/* 5452 */                                 out.print(FormatUtil.getString("am.webclient.wmi.selecthost.alert.text"));
/* 5453 */                                 out.write("');\n   \t\t\treturn;\n   \t        }\n\t\t}\n   ");
/*      */                                 
/* 5455 */                                 if (EnterpriseUtil.isAdminServer())
/*      */                                 {
/*      */ 
/* 5458 */                                   out.write("                                \n       if (document.AMActionForm.masSelectType[1].checked) {\n       \tvar selectedVal=document.AMActionForm.masGroupName.value;\n       \tif (selectedVal != null && selectedVal == \"-\") {\n               alert('");
/* 5459 */                                   out.print(FormatUtil.getString("am.webclient.admin.add.monitor.select.masgroup.text"));
/* 5460 */                                   out.write("');\n               document.AMActionForm.masGroupName.focus();\n               return;\n           }                                \t\n       } else if (document.AMActionForm.masSelectType[2].checked) {\n       \tselectedVal=document.AMActionForm.selectedServer.value;\n       \tif (selectedVal != null && selectedVal == \"-\") {\n               alert('");
/* 5461 */                                   out.print(FormatUtil.getString("am.webclient.admin.addmonitor.select.mas.text"));
/* 5462 */                                   out.write("');\n               document.AMActionForm.selectedServer.focus();\n               return;\n           }                                 \t\n       }\n   ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 5466 */                                 out.write("   \n\n\n\t\tif(trimAll(document.AMActionForm.haid.value) != '' && document.AMActionForm.haid.value != '-'){\ndocument.AMActionForm.addtoha.value=\"true\";\n}else{\ndocument.AMActionForm.addtoha.value=\"false\";\n}\n\n/***  IT360-1762 ISSUES CHANGES STARTS HERE ***/\n\n");
/* 5467 */                                 if (EnterpriseUtil.isIt360MSPEdition()) {
/* 5468 */                                   out.write("\n\nif (document.AMActionForm.organization.value== \"-\")\n {\n\talert(\"");
/* 5469 */                                   out.print(FormatUtil.getString("it360.addnewmonitor.err.checkcustomer"));
/* 5470 */                                   out.write("\");\n\treturn;\n }\n\nif (trimAll(document.AMActionForm.haid.value) == '' || document.AMActionForm.haid.value== \"-\")\n {\n    alert(\"");
/* 5471 */                                   out.print(FormatUtil.getString("it360.addnewmonitor.err.checksite"));
/* 5472 */                                   out.write("\");\n    return;\n }\n");
/*      */                                 }
/* 5474 */                                 out.write(10);
/* 5475 */                                 out.write(10);
/*      */                                 
/* 5477 */                                 IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5478 */                                 _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 5479 */                                 _jspx_th_c_005fif_005f9.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                 
/* 5481 */                                 _jspx_th_c_005fif_005f9.setTest("${checkForMonitorGroup}");
/* 5482 */                                 int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 5483 */                                 if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                   for (;;) {
/* 5485 */                                     out.write("\nvar haidValue = document.AMActionForm.haid.value\nif(haidValue == '-' || haidValue == ''){\nalert(\"");
/* 5486 */                                     out.print(FormatUtil.getString("am.webclient.newmonitor.selectmg.text"));
/* 5487 */                                     out.write("\")\nreturn\n}\n");
/* 5488 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 5489 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 5493 */                                 if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 5494 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                 }
/*      */                                 
/* 5497 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 5498 */                                 out.write("\n\n/***  IT360-1762 ISSUES CHANGES ENDS HERE ***/\n   document.AMActionForm.submit();\n   ");
/* 5499 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 5500 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 5504 */                             if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 5505 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                             }
/*      */                             
/* 5508 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 5509 */                             out.write("\n}\n\nfunction formReload()\n{\n    var v=document.AMActionForm.type.value;\n    //var portNo=v.substring(v.indexOf(\":\")+1,v.length);\n    //document.AMActionForm.method=\"post\";\n    document.AMActionForm.action=\"/adminAction.do?method=reloadHostDiscoveryForm&type=\"+v+\"&hideFieldsForIT360=");
/* 5510 */                             out.print(request.getParameter("hideFieldsForIT360"));
/* 5511 */                             out.write("\";\n    //enableAll();\n    document.AMActionForm.submit();\n}\n\nfunction managenewHost()\n{\n  if(document.AMActionForm.choosehost.value=='-1')\n  {\n        javascript:showDiv('newhost');\n  }\n  else\n  {\n        javascript:hideDiv('newhost');\n  }\n}\n\nfunction deletehost()\n{\n\n        if(document.AMActionForm.choosehost.value=='-2' || document.AMActionForm.choosehost.value=='-1')\n\t{\n\t\talert('");
/* 5512 */                             out.print(FormatUtil.getString("am.webclient.wmi.validhost.alert.text"));
/* 5513 */                             out.write("');\n\t\treturn;\n\t}\n\telse if(confirm(\"");
/* 5514 */                             out.print(FormatUtil.getString("am.webclient.wmi.deletehost.confirm.text"));
/* 5515 */                             out.write("\"))\n\t{\n\n\thost=document.AMActionForm.choosehost.value;\n          location.href=\"/WMIPerfCounters.do?method=deleteHost&host=\"+host;\n         }\n         else\n         {\n         return;\n         }\n}\n\n\n\t\n\nfunction myOnLoad(){\n\tif(");
/* 5516 */                             out.print(EnterpriseUtil.isAdminServer());
/* 5517 */                             out.write(") {\n\t\tvar radioObj = document.AMActionForm.masSelectType;\n\t\tif (radioObj !=null && radioObj != \"undefined\") {\n\t\t\tvar val='0';\n\t\t\tif (radioObj[1].checked) {\n\t\t\t\tval='1';\n\t\t\t} else if (radioObj[2].checked){\n\t\t\t\tval='2';\n\t\t\t}\n\t\t\tshowAsPerSelection(val);\n\t\t}\t\n\t}\n}\n</script>\n\n<!--  Your area ends here -->\n");
/* 5518 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 5519 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 5522 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 5523 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 5526 */                         if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 5527 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                         }
/*      */                         
/* 5530 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 5531 */                         out.write(10);
/* 5532 */                         if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 5534 */                         out.write(32);
/* 5535 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 5536 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 5540 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 5541 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 5544 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 5545 */                       out.write(10);
/*      */                     }
/* 5547 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 5548 */         out = _jspx_out;
/* 5549 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5550 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 5551 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5554 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5560 */     PageContext pageContext = _jspx_page_context;
/* 5561 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5563 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 5564 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5565 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 5567 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 5569 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 5570 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5571 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5572 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5573 */       return true;
/*      */     }
/* 5575 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5576 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5581 */     PageContext pageContext = _jspx_page_context;
/* 5582 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5584 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5585 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 5586 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5588 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 5590 */     _jspx_th_tiles_005fput_005f0.setValue("Create New Custom Monitor");
/* 5591 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 5592 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 5593 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 5594 */       return true;
/*      */     }
/* 5596 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 5597 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5602 */     PageContext pageContext = _jspx_page_context;
/* 5603 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5605 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5606 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 5607 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5609 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 5611 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 5612 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 5613 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 5614 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5615 */       return true;
/*      */     }
/* 5617 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5618 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5623 */     PageContext pageContext = _jspx_page_context;
/* 5624 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5626 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5627 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 5628 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5630 */     _jspx_th_c_005fif_005f3.setTest("${!empty param.wiz ||  !empty param.fromAssociate}");
/* 5631 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 5632 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 5634 */         out.write("\n      ");
/* 5635 */         if (_jspx_meth_c_005fchoose_005f0(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 5636 */           return true;
/* 5637 */         out.write("\n      ");
/* 5638 */         if (_jspx_meth_html_005fhidden_005f0(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 5639 */           return true;
/* 5640 */         out.write("\n      ");
/* 5641 */         if (_jspx_meth_html_005fhidden_005f1(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 5642 */           return true;
/* 5643 */         out.write("\n      ");
/* 5644 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 5645 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5649 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 5650 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5651 */       return true;
/*      */     }
/* 5653 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5654 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5659 */     PageContext pageContext = _jspx_page_context;
/* 5660 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5662 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5663 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 5664 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_c_005fif_005f3);
/* 5665 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 5666 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 5668 */         out.write("\n        ");
/* 5669 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 5670 */           return true;
/* 5671 */         out.write("\n        ");
/* 5672 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 5673 */           return true;
/* 5674 */         out.write("\n\n        ");
/* 5675 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 5676 */           return true;
/* 5677 */         out.write("\n      ");
/* 5678 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 5679 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5683 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 5684 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 5685 */       return true;
/*      */     }
/* 5687 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 5688 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5693 */     PageContext pageContext = _jspx_page_context;
/* 5694 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5696 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5697 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 5698 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 5700 */     _jspx_th_c_005fwhen_005f0.setTest("${param.type=='WTA'}");
/* 5701 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 5702 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 5704 */         out.write("\n          ");
/* 5705 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 5706 */           return true;
/* 5707 */         out.write("\n        ");
/* 5708 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 5709 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5713 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 5714 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 5715 */       return true;
/*      */     }
/* 5717 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 5718 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5723 */     PageContext pageContext = _jspx_page_context;
/* 5724 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5726 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5727 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5728 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5730 */     _jspx_th_c_005fout_005f1.setValue("Web Transaction Monitor");
/* 5731 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5732 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5733 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5734 */       return true;
/*      */     }
/* 5736 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5737 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5742 */     PageContext pageContext = _jspx_page_context;
/* 5743 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5745 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5746 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 5747 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 5749 */     _jspx_th_c_005fwhen_005f1.setTest("${param.type=='.Net'}");
/* 5750 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 5751 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 5753 */         out.write("\n          ");
/* 5754 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 5755 */           return true;
/* 5756 */         out.write("\n        ");
/* 5757 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 5758 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5762 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 5763 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 5764 */       return true;
/*      */     }
/* 5766 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 5767 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5772 */     PageContext pageContext = _jspx_page_context;
/* 5773 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5775 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5776 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5777 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 5779 */     _jspx_th_c_005fout_005f2.setValue("Tomcat Server");
/* 5780 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5781 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5782 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5783 */       return true;
/*      */     }
/* 5785 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5786 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5791 */     PageContext pageContext = _jspx_page_context;
/* 5792 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5794 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5795 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 5796 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 5797 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 5798 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 5800 */         out.write("\n         ");
/* 5801 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 5802 */           return true;
/* 5803 */         out.write("\n        ");
/* 5804 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 5805 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5809 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 5810 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 5811 */       return true;
/*      */     }
/* 5813 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 5814 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5819 */     PageContext pageContext = _jspx_page_context;
/* 5820 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5822 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 5823 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5824 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 5826 */     _jspx_th_c_005fout_005f3.setValue("${param.type}");
/* 5827 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5828 */     if (_jspx_eval_c_005fout_005f3 != 0) {
/* 5829 */       if (_jspx_eval_c_005fout_005f3 != 1) {
/* 5830 */         out = _jspx_page_context.pushBody();
/* 5831 */         _jspx_th_c_005fout_005f3.setBodyContent((BodyContent)out);
/* 5832 */         _jspx_th_c_005fout_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5835 */         out.write(45);
/* 5836 */         int evalDoAfterBody = _jspx_th_c_005fout_005f3.doAfterBody();
/* 5837 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5840 */       if (_jspx_eval_c_005fout_005f3 != 1) {
/* 5841 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5844 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5845 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f3);
/* 5846 */       return true;
/*      */     }
/* 5848 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f3);
/* 5849 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5854 */     PageContext pageContext = _jspx_page_context;
/* 5855 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5857 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 5858 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 5859 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 5861 */     _jspx_th_html_005fhidden_005f0.setProperty("type");
/* 5862 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 5863 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 5864 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 5865 */       return true;
/*      */     }
/* 5867 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 5868 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5873 */     PageContext pageContext = _jspx_page_context;
/* 5874 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5876 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 5877 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 5878 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 5880 */     _jspx_th_html_005fhidden_005f1.setProperty("haid");
/* 5881 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 5882 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 5883 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 5884 */       return true;
/*      */     }
/* 5886 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 5887 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5892 */     PageContext pageContext = _jspx_page_context;
/* 5893 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5895 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 5896 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 5897 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5899 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 5901 */     _jspx_th_html_005ftext_005f0.setMaxlength("255");
/*      */     
/* 5903 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default");
/* 5904 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 5905 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 5906 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 5907 */       return true;
/*      */     }
/* 5909 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 5910 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5915 */     PageContext pageContext = _jspx_page_context;
/* 5916 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5918 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 5919 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 5920 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5922 */     _jspx_th_html_005ftext_005f1.setProperty("pollInterval");
/*      */     
/* 5924 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext small");
/* 5925 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 5926 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 5927 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 5928 */       return true;
/*      */     }
/* 5930 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 5931 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5936 */     PageContext pageContext = _jspx_page_context;
/* 5937 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5939 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 5940 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 5941 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5943 */     _jspx_th_html_005ftext_005f2.setProperty("host");
/*      */     
/* 5945 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext default");
/* 5946 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 5947 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 5948 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 5949 */       return true;
/*      */     }
/* 5951 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 5952 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5957 */     PageContext pageContext = _jspx_page_context;
/* 5958 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5960 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5961 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 5962 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5964 */     _jspx_th_html_005ftext_005f3.setProperty("username");
/*      */     
/* 5966 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/*      */     
/* 5968 */     _jspx_th_html_005ftext_005f3.setSize("15");
/* 5969 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 5970 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 5971 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 5972 */       return true;
/*      */     }
/* 5974 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 5975 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5980 */     PageContext pageContext = _jspx_page_context;
/* 5981 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5983 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 5984 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 5985 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5987 */     _jspx_th_html_005fpassword_005f0.setProperty("password");
/*      */     
/* 5989 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext");
/*      */     
/* 5991 */     _jspx_th_html_005fpassword_005f0.setSize("15");
/* 5992 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 5993 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 5994 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 5995 */       return true;
/*      */     }
/* 5997 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 5998 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6003 */     PageContext pageContext = _jspx_page_context;
/* 6004 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6006 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 6007 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 6008 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 6010 */     _jspx_th_c_005fforEach_005f0.setItems("${dynamicSites}");
/*      */     
/* 6012 */     _jspx_th_c_005fforEach_005f0.setVar("a");
/*      */     
/* 6014 */     _jspx_th_c_005fforEach_005f0.setVarStatus("rowCounter");
/* 6015 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 6017 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 6018 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 6020 */           out.write(10);
/* 6021 */           out.write(9);
/* 6022 */           out.write(9);
/* 6023 */           if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6024 */             return true;
/* 6025 */           out.write("\n\t\tif(formCustomerId == customerId)\n\t\t{\n\t\t\tdocument.AMActionForm.haid.options[rowCount++] = new Option(siteName,siteId);\n\t\t}\n\t");
/* 6026 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 6027 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 6031 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 6032 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 6035 */         int tmp214_213 = 0; int[] tmp214_211 = _jspx_push_body_count_c_005fforEach_005f0; int tmp216_215 = tmp214_211[tmp214_213];tmp214_211[tmp214_213] = (tmp216_215 - 1); if (tmp216_215 <= 0) break;
/* 6036 */         out = _jspx_page_context.popBody(); }
/* 6037 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 6039 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 6040 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 6042 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6047 */     PageContext pageContext = _jspx_page_context;
/* 6048 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6050 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 6051 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 6052 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6054 */     _jspx_th_c_005fforEach_005f1.setItems("${a}");
/*      */     
/* 6056 */     _jspx_th_c_005fforEach_005f1.setVar("b");
/*      */     
/* 6058 */     _jspx_th_c_005fforEach_005f1.setVarStatus("rowCounter1");
/* 6059 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 6061 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 6062 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 6064 */           out.write("\n\t\t\t");
/* 6065 */           boolean bool; if (_jspx_meth_c_005fif_005f5(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6066 */             return true;
/* 6067 */           out.write("\n\t\t\t");
/* 6068 */           if (_jspx_meth_c_005fif_005f6(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6069 */             return true;
/* 6070 */           out.write("\n\t\t\t");
/* 6071 */           if (_jspx_meth_c_005fif_005f7(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6072 */             return true;
/* 6073 */           out.write(10);
/* 6074 */           out.write(9);
/* 6075 */           out.write(9);
/* 6076 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 6077 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 6081 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 6082 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 6085 */         int tmp295_294 = 0; int[] tmp295_292 = _jspx_push_body_count_c_005fforEach_005f1; int tmp297_296 = tmp295_292[tmp295_294];tmp295_292[tmp295_294] = (tmp297_296 - 1); if (tmp297_296 <= 0) break;
/* 6086 */         out = _jspx_page_context.popBody(); }
/* 6087 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 6089 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 6090 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 6092 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6097 */     PageContext pageContext = _jspx_page_context;
/* 6098 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6100 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6101 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 6102 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6104 */     _jspx_th_c_005fif_005f5.setTest("${rowCounter1.count == 1}");
/* 6105 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 6106 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 6108 */         out.write("\n\t\t\t\tsiteName = '");
/* 6109 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6110 */           return true;
/* 6111 */         out.write("'; //No I18N\n\t\t\t");
/* 6112 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 6113 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6117 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 6118 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 6119 */       return true;
/*      */     }
/* 6121 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 6122 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6127 */     PageContext pageContext = _jspx_page_context;
/* 6128 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6130 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6131 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 6132 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 6134 */     _jspx_th_c_005fout_005f4.setValue("${b}");
/* 6135 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 6136 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 6137 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6138 */       return true;
/*      */     }
/* 6140 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6141 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6146 */     PageContext pageContext = _jspx_page_context;
/* 6147 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6149 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6150 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 6151 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6153 */     _jspx_th_c_005fif_005f6.setTest("${rowCounter1.count == 2}");
/* 6154 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 6155 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 6157 */         out.write("\n\t\t\t\tsiteId = '");
/* 6158 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6159 */           return true;
/* 6160 */         out.write("'; //No I18N\n\t\t\t");
/* 6161 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 6162 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6166 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 6167 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 6168 */       return true;
/*      */     }
/* 6170 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 6171 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6176 */     PageContext pageContext = _jspx_page_context;
/* 6177 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6179 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6180 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 6181 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6183 */     _jspx_th_c_005fout_005f5.setValue("${b}");
/* 6184 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 6185 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 6186 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6187 */       return true;
/*      */     }
/* 6189 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6190 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6195 */     PageContext pageContext = _jspx_page_context;
/* 6196 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6198 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6199 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 6200 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6202 */     _jspx_th_c_005fif_005f7.setTest("${rowCounter1.count == 3}");
/* 6203 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 6204 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 6206 */         out.write("\n\t\t\t\tcustomerId = '");
/* 6207 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6208 */           return true;
/* 6209 */         out.write("'; //No I18N\n\t\t\t");
/* 6210 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 6211 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6215 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 6216 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 6217 */       return true;
/*      */     }
/* 6219 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 6220 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6225 */     PageContext pageContext = _jspx_page_context;
/* 6226 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6228 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6229 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 6230 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 6232 */     _jspx_th_c_005fout_005f6.setValue("${b}");
/* 6233 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 6234 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 6235 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6236 */       return true;
/*      */     }
/* 6238 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6239 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6244 */     PageContext pageContext = _jspx_page_context;
/* 6245 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6247 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6248 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 6249 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 6251 */     _jspx_th_c_005fif_005f8.setTest("${empty haid}");
/* 6252 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 6253 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 6255 */         out.write("\n      history.back();\n");
/* 6256 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 6257 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6261 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 6262 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 6263 */       return true;
/*      */     }
/* 6265 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 6266 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6271 */     PageContext pageContext = _jspx_page_context;
/* 6272 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6274 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6275 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 6276 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 6278 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 6279 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 6280 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 6282 */         out.write("\nalertUser();\n");
/* 6283 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 6284 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6288 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 6289 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 6290 */       return true;
/*      */     }
/* 6292 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 6293 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6298 */     PageContext pageContext = _jspx_page_context;
/* 6299 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6301 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6302 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 6303 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6305 */     _jspx_th_tiles_005fput_005f3.setName("footer");
/*      */     
/* 6307 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/footer.jsp");
/* 6308 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 6309 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 6310 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 6311 */       return true;
/*      */     }
/* 6313 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 6314 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\NewWMIMonitor_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */