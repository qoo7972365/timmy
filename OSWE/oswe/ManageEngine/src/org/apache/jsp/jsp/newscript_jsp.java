/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.tags.HiddenParam;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
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
/*      */ import java.util.Set;
/*      */ import java.util.SortedMap;
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
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.action.DynaActionForm;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.bean.MessageTag;
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.PasswordTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.ResetTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.html.TextareaTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class newscript_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  688 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
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
/*  834 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  835 */     getRCATrimmedText(div1, rca);
/*  836 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  839 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  840 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
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
/*  860 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
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
/*  946 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
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
/*  993 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
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
/* 1401 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
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
/* 1446 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
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
/* 1844 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1846 */               if (maxCol != null)
/* 1847 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1849 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1844 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
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
/* 2235 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2236 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/* 2237 */     _jspx_dependants.put("/jsp/includes/MonitorDiscoveryStatus.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonblur_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fdisabled_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fproperty_005fcols_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2287 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2291 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2292 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2293 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2294 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2295 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2296 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2297 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2298 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2299 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2300 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2301 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2302 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2303 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2304 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2305 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2306 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2307 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2308 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2309 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2310 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2311 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2312 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2313 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2314 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2315 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2316 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2317 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2318 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2319 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonblur_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2320 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2321 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2322 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2323 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2324 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2325 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2326 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2327 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2328 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2329 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fdisabled_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2330 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2331 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2332 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2333 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2334 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2338 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2339 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2340 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2341 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2342 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/* 2343 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2344 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2345 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2346 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction.release();
/* 2347 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2348 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2349 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/* 2350 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/* 2351 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2352 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2353 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2354 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2355 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.release();
/* 2356 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/* 2357 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2358 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/* 2359 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2360 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/* 2361 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.release();
/* 2362 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.release();
/* 2363 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2364 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.release();
/* 2365 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.release();
/* 2366 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonblur_005fnobody.release();
/* 2367 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2368 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.release();
/* 2369 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.release();
/* 2370 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.release();
/* 2371 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.release();
/* 2372 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.release();
/* 2373 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.release();
/* 2374 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/* 2375 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2376 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fdisabled_005fnobody.release();
/* 2377 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fproperty_005fcols_005fnobody.release();
/* 2378 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.release();
/* 2379 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2386 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2389 */     JspWriter out = null;
/* 2390 */     Object page = this;
/* 2391 */     JspWriter _jspx_out = null;
/* 2392 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2396 */       response.setContentType("text/html;charset=UTF-8");
/* 2397 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2399 */       _jspx_page_context = pageContext;
/* 2400 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2401 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2402 */       session = pageContext.getSession();
/* 2403 */       out = pageContext.getOut();
/* 2404 */       _jspx_out = out;
/*      */       
/* 2406 */       out.write("<!DOCTYPE html>\n");
/* 2407 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\t\n");
/* 2408 */       out.write(10);
/* 2409 */       out.write(10);
/*      */       
/* 2411 */       String monitortype = (String)request.getAttribute("type");
/*      */       
/* 2413 */       if ((monitortype.equals("file")) || (monitortype.equals("directory")) || (monitortype.equals("File System Monitor")))
/*      */       {
/* 2415 */         request.setAttribute("HelpKey", "Configuring File Monitor");
/*      */       }
/*      */       else {
/* 2418 */         request.setAttribute("HelpKey", "Configuring Script Monitor");
/*      */       }
/*      */       
/*      */ 
/* 2422 */       out.write("\n\n\n\n\n\n\n\n\n\n");
/* 2423 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2425 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2426 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2427 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2429 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2431 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2433 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2435 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2436 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2437 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2438 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2441 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2442 */         String available = null;
/* 2443 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2444 */         out.write(10);
/*      */         
/* 2446 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2447 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2448 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2450 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2452 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2454 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2456 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2457 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2458 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2459 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2462 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2463 */           String unavailable = null;
/* 2464 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2465 */           out.write(10);
/*      */           
/* 2467 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2468 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2469 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2471 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2473 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2475 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2477 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2478 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2479 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2480 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2483 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2484 */             String unmanaged = null;
/* 2485 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2486 */             out.write(10);
/*      */             
/* 2488 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2489 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2490 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2492 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2494 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2496 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2498 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2499 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2500 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2501 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2504 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2505 */               String scheduled = null;
/* 2506 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2507 */               out.write(10);
/*      */               
/* 2509 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2510 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2511 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2513 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2515 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2517 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2519 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2520 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2521 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2522 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2525 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2526 */                 String critical = null;
/* 2527 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2528 */                 out.write(10);
/*      */                 
/* 2530 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2531 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2532 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2534 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2536 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2538 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2540 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2541 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2542 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2543 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2546 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2547 */                   String clear = null;
/* 2548 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2549 */                   out.write(10);
/*      */                   
/* 2551 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2552 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2553 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2555 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2557 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2559 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2561 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2562 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2563 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2564 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2567 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2568 */                     String warning = null;
/* 2569 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2570 */                     out.write(10);
/* 2571 */                     out.write(10);
/*      */                     
/* 2573 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2574 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2576 */                     out.write(10);
/* 2577 */                     out.write(10);
/* 2578 */                     out.write(10);
/* 2579 */                     out.write(10);
/* 2580 */                     out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 2581 */                     out.write(10);
/* 2582 */                     out.write(10);
/* 2583 */                     out.write(10);
/* 2584 */                     ManagedApplication mo = null;
/* 2585 */                     mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 1);
/* 2586 */                     if (mo == null) {
/* 2587 */                       mo = new ManagedApplication();
/* 2588 */                       _jspx_page_context.setAttribute("mo", mo, 1);
/*      */                     }
/* 2590 */                     out.write(10);
/* 2591 */                     Hashtable applications = null;
/* 2592 */                     synchronized (application) {
/* 2593 */                       applications = (Hashtable)_jspx_page_context.getAttribute("applications", 4);
/* 2594 */                       if (applications == null) {
/* 2595 */                         applications = new Hashtable();
/* 2596 */                         _jspx_page_context.setAttribute("applications", applications, 4);
/*      */                       }
/*      */                     }
/* 2599 */                     out.write("\n\n\n\n");
/* 2600 */                     out.write("<!--$Id$-->\n\n\n\n");
/*      */                     
/*      */                     try
/*      */                     {
/* 2604 */                       boolean isprivilege = false;
/* 2605 */                       if (ClientDBUtil.isPrivilegedUser(request)) {
/* 2606 */                         isprivilege = true;
/*      */                       }
/* 2608 */                       request.setAttribute("checkForMonitorGroup", Boolean.valueOf(isprivilege));
/*      */                       
/*      */ 
/* 2611 */                       ArrayList dynamicSites = AlarmUtil.getSiteMonitorGroups();
/* 2612 */                       if (dynamicSites != null)
/*      */                       {
/* 2614 */                         request.setAttribute("dynamicSites", dynamicSites);
/*      */                       }
/*      */                       
/* 2617 */                       ArrayList mgList = new ArrayList();
/* 2618 */                       if (EnterpriseUtil.isIt360MSPEdition())
/*      */                       {
/* 2620 */                         AMActionForm form = (AMActionForm)request.getAttribute("AMActionForm");
/* 2621 */                         ArrayList orgs = AlarmUtil.getCustomerNames();
/*      */                         
/* 2623 */                         if (orgs != null)
/*      */                         {
/* 2625 */                           request.setAttribute("customers", orgs);
/*      */                         }
/*      */                         
/*      */ 
/* 2629 */                         if (form != null)
/*      */                         {
/* 2631 */                           String customerName = form.getOrganization();
/* 2632 */                           if (customerName != null)
/*      */                           {
/* 2634 */                             mgList = AlarmUtil.getSiteMonitorGroups(customerName);
/*      */                           }
/*      */                           
/*      */                         }
/*      */                         
/*      */ 
/*      */                       }
/* 2641 */                       else if (isprivilege)
/*      */                       {
/* 2643 */                         mgList = AlarmUtil.getConfiguredGroups(request, false, false, true);
/*      */                       }
/*      */                       else
/*      */                       {
/* 2647 */                         mgList = AlarmUtil.getConfiguredGroups(null, false, false, true);
/*      */                       }
/*      */                       
/* 2650 */                       if (mgList != null)
/*      */                       {
/* 2652 */                         request.setAttribute("applications", mgList);
/* 2653 */                         if (EnterpriseUtil.isAdminServer()) {
/* 2654 */                           ArrayList adminMGroups = getMGroupsCreatedInAdminServer(mgList);
/* 2655 */                           request.setAttribute("AllMonitorGroupsInAdminServer", mgList);
/* 2656 */                           request.setAttribute("MonitorGroupsCreatedInAdminServer", adminMGroups);
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 2662 */                       e.printStackTrace();
/*      */                     }
/*      */                     
/* 2665 */                     out.write(10);
/* 2666 */                     out.write(10);
/* 2667 */                     out.write(10);
/* 2668 */                     out.write(10);
/* 2669 */                     out.write(10);
/*      */                     
/* 2671 */                     Hashtable table_atts = new Hashtable();
/* 2672 */                     ArrayList tableids = new ArrayList();
/* 2673 */                     if (request.getAttribute("tableids") != null)
/*      */                     {
/* 2675 */                       table_atts = (Hashtable)request.getAttribute("table_atts");
/* 2676 */                       tableids = (ArrayList)request.getAttribute("tableids");
/*      */                     }
/*      */                     
/* 2679 */                     out.write(10);
/*      */                     
/* 2681 */                     String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/* 2682 */                     if (hideFieldsForIT360 == null)
/*      */                     {
/* 2684 */                       hideFieldsForIT360 = (String)request.getAttribute("hideFieldsForIT360");
/*      */                     }
/*      */                     
/* 2687 */                     boolean hideFields = false;
/* 2688 */                     String hideStyle = "";
/* 2689 */                     if ((hideFieldsForIT360 != null) && (hideFieldsForIT360.equals("true")))
/*      */                     {
/* 2691 */                       hideFields = true;
/* 2692 */                       hideStyle = "hideContent";
/*      */                     }
/*      */                     try
/*      */                     {
/* 2696 */                       String resourceid = request.getParameter("resourceid");
/* 2697 */                       String haid = request.getParameter("haid");
/* 2698 */                       String customType = request.getParameter("customType");
/* 2699 */                       int contentChkCount = 5;
/*      */                       try {
/* 2701 */                         contentChkCount = com.adventnet.appmanager.util.Constants.fContentChkCount;
/*      */                       } catch (Exception ex) {
/* 2703 */                         ex.printStackTrace();
/*      */                       }
/* 2705 */                       request.setAttribute("contentChkCount", Integer.valueOf(contentChkCount));
/* 2706 */                       org.apache.struts.util.TokenProcessor token = org.apache.struts.util.TokenProcessor.getInstance();
/* 2707 */                       token.saveToken(request);
/* 2708 */                       String wiz3 = request.getParameter("wiz");
/* 2709 */                       String name = null;
/*      */                       
/* 2711 */                       out.write("\n\n<script>\n\nfunction loadSite()\n{\n\tdocument.AMActionForm.haid.options.length=0;\n\tvar formCustomerId = document.AMActionForm.organization.value;\n\tvar siteName;\n\tvar siteId;\n\tvar customerId;\n\tvar rowCount=0;\n\tdocument.AMActionForm.haid.options[rowCount++] = new Option('-Select Site-','-'); //No I18N\n\t");
/* 2712 */                       if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
/*      */                         return;
/* 2714 */                       out.write("\n}\n\nfunction resetname(name)\n{\n\tif(name='groupname')\n\t{\n\t\tdocument.AMActionForm.groupname.value='';\n\t}\n\tif(name='subgroupname')\n\t{\n\t\tdocument.AMActionForm.subgroupname.value='';\n\t}\n}\nfunction createGroup()\n{\n\tif(document.AMActionForm.groupname.value=='')\n\t{\n\t\talert(\"");
/* 2715 */                       out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/* 2716 */                       out.write("\");\n\t\thideDiv('group');\n\t\tshowDiv('creategroup');\n\t\tdocument.AMActionForm.groupname.focus();\n\t\treturn false;\n\t}\n\telse\n\t{\n\t\thideDiv('creategroup');\n\t\tvar a=document.AMActionForm.groupname.value;\n\t\turl=\"/adminAction.do?method=createMonitorGroup&groupname=\"+encodeURIComponent(a);\n\t\thttp.open(\"GET\",url,true);\n\t\thttp.onreadystatechange = getActionTypes;\n\t\thttp.send(null);\n\t\tshowDiv('group');\n\t}\n\n}  \n\nfunction ContentCheck(){\n\n\tif(document.AMActionForm.contentChk.checked==true)\n\t {\n\n\t\t//showDiv('contentChk11');\n\t\t document.getElementById(\"contentChk11\").style.display='table-row';\n\t\t if(document.AMActionForm.fileCheckType[0]){\n\t\t\t    document.AMActionForm.fileCheckType[0].checked=true;\n\t\t}\n\t\t\n\t }\n\telse{\n\t\thideDiv('contentChk11');\n\t}\n}\n\nfunction RegexCheck(){\n\n\tif(document.AMActionForm.regexChk.checked==true)\n\t {\n\t\thideDiv('NormalChk');\n\t\tshowDiv('regexChk1');\n\t }\n\telse{\n\t\t showDiv('NormalChk');\n\t\thideDiv('regexChk1');\n\t}\n}\n\nfunction FileDirAgeCheck(){\n\tif(document.AMActionForm.fileDirAge.checked==true)\n\t {\n\n\t\t//showDiv('fileDirAge11');\n");
/* 2717 */                       out.write("\t\tdocument.getElementById(\"fileDirAge11\").style.display='table-row';\n\t\t\n\t }\n\telse{\n\t\thideDiv('fileDirAge11');\n\t}\n}\n\nfunction check()\n{\n\thideDiv(\"creategroup\");\n\thideDiv(\"createsubgroup\");\n\thideDiv(\"groupmessage\");\n\tvar flag='");
/* 2718 */                       out.print(com.adventnet.appmanager.util.Constants.subGroupsEnabled);
/* 2719 */                       out.write("';\n\tif(flag==\"true\")\n\t{\n\t\tif(trimAll(document.AMActionForm.haid.value) == '' || document.AMActionForm.haid.value=='-')\n\t\t{\n\t\t\thideDiv(\"subgroup\");\n\t\t\tshowDiv(\"group\");\n\t\t}\n\t\telse\n\t\t{\n\t\t\thideDiv(\"group\");\n\t\t\tshowDiv(\"subgroup\");\n\t\t}\n\t}\n\telse\n\t{\n\t\tshowDiv(\"group\");\n\t}\n}\nfunction createsubGroup()\n{\n\tif(trimAll(document.AMActionForm.haid.value) == '' || document.AMActionForm.haid.value=='-')\n\t{\n\t\talert(\"");
/* 2720 */                       out.print(FormatUtil.getString("am.webclient.monitorsubgroup.monitoralert"));
/* 2721 */                       out.write("\");\t\t\n\t\tdocument.AMActionForm.haid.focus();\n\t}\n\telse\n\t{\n\t\tif(document.AMActionForm.subgroupname.value=='')\n\t\t{\n\t\t\talert(\"");
/* 2722 */                       out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/* 2723 */                       out.write("\");\n\t\t\tdocument.AMActionForm.subgroupname.focus();\n\t\t\treturn false;\n\t\t}\n\t\telse\n\t\t{\n\t\t\thideDiv('createsubgroup');\n\t\t\tvar a=document.AMActionForm.subgroupname.value;\n\t\t\tvar haid=document.AMActionForm.haid.value;\n\t\t\turl=\"/adminAction.do?method=createSubGroup&haid=\"+haid+\"&subgroupname=\"+encodeURIComponent(a);\n\t\t\thttp.open(\"GET\",url,true);\n\t\t\thttp.onreadystatechange = getActionTypes;\n\t\t\thttp.send(null);\n\t\t}\n\t\tshowDiv('subgroup');\n    }\n\n}  \n\n function getActionTypes()\n{\n    if(http.readyState == 4)\n    {\n\t\tvar result = http.responseText;\n\t\tvar id=result;\n\t\tvar stringtokens=id.split(\",\");\n\t\tsid = stringtokens[2];\n\t\tsname=stringtokens[1];\n\t\tsname=decodeURIComponent(stringtokens[1]);\n\t\tsmessage=stringtokens[0];\n\t\tif (sname==null || sname=='undefined')\n\t\t{\n\t\t\tshowDiv(\"groupmessage\");\n\t\t\tdocument.getElementById(\"groupmessage\").innerHTML=smessage;\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.AMActionForm.haid.options[document.AMActionForm.haid.length] =new Option(sname,sid,false,true);\n\t\t\thideDiv(\"creategroup\");\n\t\t\thideDiv(\"createsubgroup\");\n");
/* 2724 */                       out.write("\t\t\thideDiv(\"group\");\n\t\t\tshowDiv(\"subgroup\");\n\t\t\tshowDiv(\"groupmessage\");\n\t\t\tdocument.getElementById(\"groupmessage\").innerHTML=smessage;\n\t  \t}\n\t}\n}\n\n\n/*function checkHost(){\n\nvar host = trimAll(document.AMActionForm.host.value);\nvar usrname = trimAll(document.AMActionForm.username.value);\nif(http){\n\n\n var url = '/updateScript.do?method=checkForServer&hostName='+host+'&username='+usrname;\n                http.open(\"GET\",url,true);\n                http.onreadystatechange = handleMessage;\n                http.send(null);\n        }\n        return false;\n\n\n\n}\n\nfunction handleMessage(){\n\n\tif(http.readyState == 4){\n\n\t\tvar result = http.responseText ;\n                var element=(document.getElementsByName(\"username\"))[0];\n                var temp=null;\n                var isPointerReq=false;\n                var red=\"#FF0000\";\n                ddrivetip(element,temp,result,isPointerReq,true,red,300);\n               setTimeout(\"hideDialog()\",1000);\n\n\n\n\n\t}\n\n}\n\nfunction hideDialog()\n{\n        startHideFade(\"dhtmltooltip\",0.04);\n");
/* 2725 */                       out.write("}*/\n\n\nfunction fnclear(){\nif(document.AMActionForm.filepath.value == '");
/* 2726 */                       out.print(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/* 2727 */                       out.write("'){\n\tdocument.AMActionForm.filepath.value=\"\";\n}\n}\n\n\nvar count=1;\nfunction showTables()\n{\n\t\n");
/*      */                       
/* 2729 */                       if ((customType == null) || (!customType.equals("true")))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2735 */                         out.write("\n  if(document.AMActionForm.tablespresent.checked)\n  {\n    javascript:showDiv(\"tableid\");\n    count=1;\n  }\n  else\n  {\n   javascript:hideDiv(\"tableid\");\n  }\n  \n  ");
/*      */                       }
/*      */                       
/*      */ 
/* 2739 */                       out.write("\n}\n\nfunction callremove(count)\n{\n\tif(count==1)\n\t{\n\t\tdocument.AMActionForm.tablespresent.checked=false;\n\t\tshowTables();\n\t\tcount=2;\n\t\treturn;\n\t}\n\telse\n\t{\n \t\tremoveFilterRow(count);\n\t}\n}\n\nfunction changeport()\n{\n\n\tvar mode=trimAll(document.AMActionForm.monitoringmode.value);\n\n\tif(mode==\"TELNET\")\n\t{\n\t\tjavascript:hideDiv('sshKeyAuth');\n\t\tjavascript:showDiv('passwordid');\n\t\tdocument.AMActionForm.port.value=\"23\";\n\t\tdocument.AMActionForm.sshkey.checked=false;\n\t\tjavascript:hideDiv(\"keyid\");\n\t\tjavascript:hideDiv(\"passphraseid\");\n\t\tjavascript:showDiv('wmiid');\n\t ");
/* 2740 */                       if ((((String)request.getAttribute("type")).equals("file")) || (((String)request.getAttribute("type")).equals("directory")) || (((String)request.getAttribute("type")).equals("File System Monitor"))) {
/* 2741 */                         out.write("\n                        if(document.AMActionForm.mtype[0].checked ==true)\n                //javascript:showDiv('content');\n                        \t document.getElementById(\"content\").style.display='table-row';\n                ");
/*      */                       }
/* 2743 */                       out.write("\n\ndocument.getElementById(\"uname\").innerHTML ='<label>");
/* 2744 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/* 2745 */                       out.write("<spa></labeln class=\"mandatory\">*</span>' ");
/* 2746 */                       out.write("\n\t}\n\telse if(mode==\"WMI\"){\n\t\tif(document.AMActionForm.mtype[1].checked == true){\n\t\t\t\t\t//javascript:hideDiv('content');\n\t\t\tdocument.getElementById(\"content\").style.display='none';\n\t\t\t document.getElementById(\"contentChk11\").style.display='none';\n\t\t\t//if(document.AMActionForm.serversite[1].checked == true){\n\t\t\t//alert('");
/* 2747 */                       out.print(FormatUtil.getString("am.webclient.rdir.alert"));
/* 2748 */                       out.write("');\n        \t//        javascript:hideDiv('newhost')\n          //      \tjavascript:hideDiv('remotescript');\n\t\t//\tif(document.AMActionForm.serversite[0]){document.AMActionForm.serversite[0].checked=true;}\n\t\t//\t}\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.getElementById(\"content\").style.display='table-row';\n\t\t}\n\t\tjavascript:hideDiv('wmiid');\n\t\tjavascript:showDiv('passwordid');\n\t\tjavascript:hideDiv('sshKeyAuth');\n\t\tjavascript:hideDiv(\"keyid\");\n\t\tjavascript:hideDiv(\"passphraseid\");\n\t\t//javascript:hideDiv('content');\t\t\n\t\tdocument.getElementById(\"uname\").innerHTML ='<a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,\\'");
/* 2749 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.message.filemonitor"));
/* 2750 */                       out.write("\\',false,true,\\'#000000\\',200,\\'lightyellow\\')\" onmouseout=\"hideddrivetip()\">");
/* 2751 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/* 2752 */                       out.write("</a><span class=\"mandatory\">*</span>'\n\n\t}\n\telse\n\t{\n\n\t\tjavascript:showDiv('wmiid');\n\t\tjavascript:showDiv('sshKeyAuth');\n\t\tjavascript:showDiv('passwordid');\n\t\t");
/* 2753 */                       if ((((String)request.getAttribute("type")).equals("file")) || (((String)request.getAttribute("type")).equals("directory")) || (((String)request.getAttribute("type")).equals("File System Monitor"))) {
/* 2754 */                         out.write("\n                                if(document.AMActionForm.mtype[0].checked ==true)\n                                //javascript:showDiv('content');\n                                \tdocument.getElementById(\"content\").style.display='table-row';\n                ");
/*      */                       }
/* 2756 */                       out.write("\n\n\t\tdocument.AMActionForm.port.value=\"22\";\n\t\tdocument.getElementById(\"uname\").innerHTML ='");
/* 2757 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/* 2758 */                       out.write("<span class=\"mandatory\">*</span>'\n\n\n\t}\n\tif($('input[name=isCredManager]:checked').val() =='true')\n\t  {\n\t\tswitchCredentialProfiles();\n\t  }\n}\n\nfunction showPrivateKey()\n{\n  if(document.AMActionForm.sshkey.checked)\n  {\n    javascript:hideDiv(\"passwordid\");\n    javascript:showDiv(\"keyid\");\n    javascript:showDiv(\"passphraseid\");\n  }\n  else\n  {\n   javascript:showDiv(\"passwordid\");\n   javascript:hideDiv(\"keyid\");\n   javascript:hideDiv(\"passphraseid\");\n  }\n}\n\nfunction validateAndSubmit(value)\n{\n\t\n\tif($('input[name=isCredManager]:checked').val()=='true' && document.AMActionForm.credentialID.value=='No Credentials'){\n\t\talert('");
/* 2759 */                       out.print(FormatUtil.getString("am.webclient.common.chooseCredential.text"));
/* 2760 */                       out.write("');\n\t\tdocument.AMActionForm.credentialID.focus();\n\t\treturn;\n\t}\n\t\nvar pollinterval=trimAll(document.AMActionForm.pollInterval.value);\nvar filepath=\"\";//No I18N\nif(document.AMActionForm.filepath){\n\tfilepath=trimAll(document.AMActionForm.filepath.value);\n}\n");
/* 2761 */                       if ((((String)request.getAttribute("type")).equals("file")) || (((String)request.getAttribute("type")).equals("directory")) || (((String)request.getAttribute("type")).equals("File System Monitor"))) {
/* 2762 */                         out.write("\n\t\t\tvar timeout=trimAll(document.AMActionForm.timeout.value);\n\t\t\tvar timeVal=trimAll(document.AMActionForm.timeval.value);\n\t\t\tvar searchcontent=trimAll(document.AMActionForm.ccontent.value);\n\t\t\t\n\t\t\tif(document.AMActionForm.displayname.value == ''){\n\t\t\talert('");
/* 2763 */                         out.print(FormatUtil.getString("am.webclient.common.validatename.text"));
/* 2764 */                         out.write("')\n\t\t\tdocument.AMActionForm.displayname.focus();\n\t\t\treturn;\n\t\t\t}\n\t\t\t var dispname=document.AMActionForm.displayname.value;\n\tif(dispname.indexOf(\"'\") != -1)\n        {\n            alert(\"");
/* 2765 */                         out.print(FormatUtil.getString("am.webclient.common.jsalertforsinglequote.text"));
/* 2766 */                         out.write("\");\n                document.AMActionForm.displayname.focus();\n                return;\n         }\n\t\t\tif(!document.AMActionForm.mtype[0].checked && !document.AMActionForm.mtype[1].checked){\n                                alert('");
/* 2767 */                         out.print(FormatUtil.getString("am.webclient.common.type.text1"));
/* 2768 */                         out.write("')\n                                return;\n                        }\n\n\t\t\tif(document.AMActionForm.filepath.value == ''){\n\t\t\talert('");
/* 2769 */                         out.print(FormatUtil.getString("am.webclient.path.text"));
/* 2770 */                         out.write("')\n\t\t\tdocument.AMActionForm.filepath.focus();\n\t\t\treturn;\n\t\t\t}\n\t\t\telse\n\t        {\n\t\t\t  if(filepath.indexOf(\"\\\\\")==-1 && filepath.indexOf(\"/\")==-1){\n\t\t\t\t  alert('");
/* 2771 */                         out.print(FormatUtil.getString("am.webclient.exact.path.text"));
/* 2772 */                         out.write("')\n\t\t\t\t  document.AMActionForm.filepath.focus();\n\t\t\t\t  return;\n\t\t\t  }\n\t\t\t  \n\t\t\t}\n/*\t\tif(document.AMActionForm.mtype[0].checked ==true){\n\t\t\tif(document.AMActionForm.filename.value == ''){\n\t\t\talert('");
/* 2773 */                         out.print(FormatUtil.getString("File Name cannot be empty"));
/* 2774 */                         out.write("')\n\t\t\tdocument.AMActionForm.filename.focus();\n\t\t\treturn;\n\t\t\t}\n\t\t}*/\n\t\t\tif(document.AMActionForm.mtype[0].checked ==true && document.AMActionForm.contentChk.checked == true){\n\t\t\t\tif(document.AMActionForm.ccontent.value == ''){\n\t\t\t\talert('");
/* 2775 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.content.text"));
/* 2776 */                         out.write("')\n\t\t\t\tdocument.AMActionForm.ccontent.focus();\n\t\t\t\treturn;\n\t\t\t\t}\n            }\n\t\t\n\t\t\tvar searchContArr=searchcontent.split(\",\");\n\t\t\tif(searchContArr.length > ");
/* 2777 */                         if (_jspx_meth_c_005fout_005f3(_jspx_page_context))
/*      */                           return;
/* 2779 */                         out.write("){\n\t\t\t\talert('");
/* 2780 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.contentCnt.text", new String[] { "" + contentChkCount }));
/* 2781 */                         out.write("')\n\t\t\t\tdocument.AMActionForm.ccontent.focus();\n\t\t\t\treturn;\n\t\t\t}\n\t\t\tif(document.AMActionForm.mtype[0].checked ==true && document.AMActionForm.contentChk.checked == true){\n\t\t\t\tif (document.AMActionForm.clearCondition[1].checked == true) {\n\t\t\t\t\tif(document.AMActionForm.clearConditionContent.value == '') {\n\t\t\t\t\t\t// empty\n\t\t\t\t\t\talert('");
/* 2782 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.content.text"));
/* 2783 */                         out.write("')\n\t\t\t\t\t\tdocument.AMActionForm.clearConditionContent.focus();\n\t\t\t\t\t\treturn\n\t\t\t\t\t} else {\n\t\t\t\t\t\tvar searchcontent=trimAll(document.AMActionForm.clearConditionContent.value);\n\t\t\t\t\t\tvar searchContArr=searchcontent.split(\",\");\n\t\t\t\t\t\tif(searchContArr.length > ");
/* 2784 */                         if (_jspx_meth_c_005fout_005f4(_jspx_page_context))
/*      */                           return;
/* 2786 */                         out.write("){\n\t\t\t\t\t\t\talert('");
/* 2787 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.contentCnt.text", new String[] { "" + contentChkCount }));
/* 2788 */                         out.write("')\n\t\t\t\t\t\t\tdocument.AMActionForm.clearConditionContent.focus();\n\t\t\t\t\t\t\treturn;\n\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t\t\n\t\t\t}\n\t\t\tif(timeVal == '' || timeVal =='0' ||!(isPositiveInteger(timeVal))){\n\t\t\t\tif(timeVal == ''){\n\t\t\t\t\talert('");
/* 2789 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.timevalEmpty.text"));
/* 2790 */                         out.write("')\n\t\t\t\t\tdocument.AMActionForm.timeval.focus();\n\t\t\t\t\treturn;\n\t\t\t\t}\n\t\t\t\telse{\n\t\t\t\t\talert('");
/* 2791 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.timeval.text"));
/* 2792 */                         out.write("')\n\t\t\t\t\tdocument.AMActionForm.timeval.focus();\n\t\t\t\t\treturn;\n\t\t\t\t}\n\t\t\t}\n\t\t\tif(pollinterval ==\"\" ||!(isPositiveInteger(pollinterval)) || pollinterval =='0' )\n                        {\n                                alert(\"");
/* 2793 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.pollingintervalzero.text"));
/* 2794 */                         out.write("\");\n                                document.AMActionForm.pollInterval.focus();\n                                return;\n                        }\n\n\t\t\tif(timeout==\"\")\n                        {\n                                alert(\"");
/* 2795 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.timeoutempty.text"));
/* 2796 */                         out.write("\");\n                                document.AMActionForm.timeout.focus();\n                                return;\n                        }\n            ");
/*      */                         
/* 2798 */                         if (EnterpriseUtil.isAdminServer())
/*      */                         {
/*      */ 
/* 2801 */                           out.write("                                \n                if (document.AMActionForm.masSelectType[1].checked) {\n                \tvar selectedVal=document.AMActionForm.masGroupName.value;\n                \tif (selectedVal != null && selectedVal == \"-\") {\n                        alert('");
/* 2802 */                           out.print(FormatUtil.getString("am.webclient.admin.add.monitor.select.masgroup.text"));
/* 2803 */                           out.write("');\n                        document.AMActionForm.masGroupName.focus();\n                        return;\n                    }                                \t\n                } else if (document.AMActionForm.masSelectType[2].checked) {\n                \tselectedVal=document.AMActionForm.selectedServer.value;\n                \tif (selectedVal != null && selectedVal == \"-\") {\n                        alert('");
/* 2804 */                           out.print(FormatUtil.getString("am.webclient.admin.addmonitor.select.mas.text"));
/* 2805 */                           out.write("');\n                        document.AMActionForm.selectedServer.focus();\n                        return;\n                    }                                 \t\n                }\n            ");
/*      */                         }
/*      */                         
/*      */ 
/* 2809 */                         out.write("  \t\t\t\n//\tdocument.AMActionForm.action=\"/updateScript.do?method=filemon&type=");
/* 2810 */                         out.print(request.getAttribute("type"));
/* 2811 */                         out.write("\";\n\n\t\t ");
/* 2812 */                         if (OEMUtil.isRemove("am.localscript.remove")) {
/* 2813 */                           out.write("\n\n                         if(document.AMActionForm.serversite && document.AMActionForm.serversite.checked == true)\n                         ");
/*      */                         } else {
/* 2815 */                           out.write("\n\n\t\t\tif(document.AMActionForm.serversite[1] && document.AMActionForm.serversite[1].checked == true)\n\t\t\t ");
/*      */                         }
/* 2817 */                         out.write("\n\t\t\t{\n\t\t\t\tif(document.AMActionForm.choosehost.value=='-1')\n\t\t\t\t{\n\t\t\t\t\tif(document.AMActionForm.host.value=='')\n\t\t\t\t\t{\n\t\t\t\t\t\talert('");
/* 2818 */                         out.print(FormatUtil.getString("am.webclient.hostnameemp.text"));
/* 2819 */                         out.write("');\n\t\t\t\t\t\tdocument.AMActionForm.host.focus();\n\t\t\t\t\t\treturn;\n\t\t\t\t\t}\n\t\t\t\t\tif(document.AMActionForm.username.value=='' && $('input[name=isCredManager]:checked').val() =='false')\n\t\t\t\t\t{\n\t\t\t\t\t\talert('");
/* 2820 */                         out.print(FormatUtil.getString("am.webclient.usernameemp.text"));
/* 2821 */                         out.write("');\n\t\t\t\t\t\tdocument.AMActionForm.username.focus();\n\t\t\t\t\t\treturn;\n\t\t\t\t\t}\n\t\t\t\t\tif(document.AMActionForm.sshkey.checked==true)\n\t\t\t\t\t{\n\t\t\t\t\t\tif(document.AMActionForm.description.value=='')\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\t alert('");
/* 2822 */                         out.print(FormatUtil.getString("am.webclient.sshkeyemp.text"));
/* 2823 */                         out.write("');\n\t\t\t\t\t\t\tdocument.AMActionForm.sshkey.focus();\n\t\t\t\t\t\t\treturn;\n\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t\t\telse if(document.AMActionForm.password.value=='' && $('input[name=isCredManager]:checked').val() =='false')\n\t\t\t\t\t{\n\t\t\t\t\t\tif(confirm('");
/* 2824 */                         out.print(FormatUtil.getString("password.empty.message"));
/* 2825 */                         out.write("'))\n\t\t\t\t\t\t{\n\t\t\t\t\t\t}\n\t\t\t\t\t\telse\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\tdocument.AMActionForm.password.focus();\n\t\t\t\t\t\t\treturn;\n\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t\t\tif(document.AMActionForm.port.value=='')\n\t\t\t\t\t{\n\t\t\t\t\t\talert('");
/* 2826 */                         out.print(FormatUtil.getString("am.webclient.portemp.text"));
/* 2827 */                         out.write("');\n\n\t\t\t\t\t\tdocument.AMActionForm.port.focus();\n\t\t\t\t\t\treturn;\n\t\t\t\t\t}\n\t\t\t\t\tif(document.AMActionForm.prompt.value=='')\n\t\t\t\t\t{\n\t\t\t\t\t\talert('");
/* 2828 */                         out.print(FormatUtil.getString("am.webclient.promptemp.text"));
/* 2829 */                         out.write("');\n\t\t\t\t\t\tdocument.AMActionForm.prompt.focus();\n\t\t\t\t\t\treturn;\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n\t\t\t\t\tif(document.AMActionForm.choosehost.value=='-2')\n\t\t\t\t\t{\n\t\t\t\t\t\talert('");
/* 2830 */                         out.print(FormatUtil.getString("am.webclient.script.choosehost"));
/* 2831 */                         out.write("');\n\t\t\t\t\t\treturn;\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t}\n");
/*      */                       }
/*      */                       else {
/* 2834 */                         out.write("\n\n\t\t\tif(document.AMActionForm.displayname.value == ''){\n\t\t\talert('");
/* 2835 */                         out.print(FormatUtil.getString("am.webclient.flashview.displayname.empty.text"));
/* 2836 */                         out.write("')\t//NO I18N\n\t\t\tdocument.AMActionForm.displayname.focus();\n\t\t\treturn;\n\t\t\t}\n\n\t\t\tif(pollinterval ==\"\" ||!(isPositiveInteger(pollinterval)) || pollinterval =='0' )\n                        {\n                                alert(\"");
/* 2837 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.pollingintervalzero.text"));
/* 2838 */                         out.write("\");\n                                document.AMActionForm.pollInterval.focus();\n                                return;\n                        }\n\n\t\t\tif(timeout==\"\")\n                        {\n                                alert(\"");
/* 2839 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.timeoutempty.text"));
/* 2840 */                         out.write("\");\n                                document.AMActionForm.timeout.focus();\n                                return;\n                        }\n//\tdocument.AMActionForm.action=\"/updateScript.do?method=filemon&type=");
/* 2841 */                         out.print(request.getAttribute("type"));
/* 2842 */                         out.write("\";\n\t\t ");
/* 2843 */                         if (OEMUtil.isRemove("am.localscript.remove")) {
/* 2844 */                           out.write("\n\n                         if(document.AMActionForm.serversite && document.AMActionForm.serversite.checked == true)\n                         ");
/*      */                         } else {
/* 2846 */                           out.write("\n\n\n\t\t\tif(document.AMActionForm.serversite[1] && document.AMActionForm.serversite[1].checked == true)\n\t\t\t ");
/*      */                         }
/* 2848 */                         out.write("\n\t\t\t{\n\t\t\t\tif(document.AMActionForm.choosehost.value=='-1')\n\t\t\t\t{\n\t\t\t\t\tif(document.AMActionForm.host.value=='')\n\t\t\t\t\t{\n\t\t\t\t\t\talert(\"Hostname cannot be empty if the new host is choosed\");\n\t\t\t\t\t\tdocument.AMActionForm.host.focus();\n\t\t\t\t\t\treturn;\n\t\t\t\t\t}\n\t\t\t\t\tif(document.AMActionForm.username.value=='' && $('input[name=isCredManager]:checked').val() =='false')\n\t\t\t\t\t{\n\t\t\t\t\t\talert(\"Username cannot be empty if the new host is choosed\");\n\t\t\t\t\t\tdocument.AMActionForm.username.focus();\n\t\t\t\t\t\treturn;\n\t\t\t\t\t}\n\t\t\t\t\tif(document.AMActionForm.sshkey.checked==true)\n\t\t\t\t\t{\n\t\t\t\t\t\tif(document.AMActionForm.description.value=='')\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\talert(\"Private Key cannot be empty if Key Based Authentication is choosed\");\n\t\t\t\t\t\t\tdocument.AMActionForm.sshkey.focus();\n\t\t\t\t\t\t\treturn;\n\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t\t\telse if(document.AMActionForm.password.value=='' && $('input[name=isCredManager]:checked').val() =='false')\n\t\t\t\t\t{\n\t\t\t\t\t\tif(confirm(\"Password is empty. Do you still want to continue?\"))\n\t\t\t\t\t\t{\n\t\t\t\t\t\t}\n\t\t\t\t\t\telse\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\tdocument.AMActionForm.password.focus();\n");
/* 2849 */                         out.write("\t\t\t\t\t\t\treturn;\n\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t\t\tif(document.AMActionForm.port.value=='')\n\t\t\t\t\t{\n\t\t\t\t\t\talert(\"Port cannot be empty if the new host is choosed\");\n\t\t\t\t\t\tdocument.AMActionForm.port.focus();\n\t\t\t\t\t\treturn;\n\t\t\t\t\t}\n\t\t\t\t\tif(document.AMActionForm.prompt.value=='' && $('input[name=isCredManager]:checked').val() =='false')\n\t\t\t\t\t{\n\t\t\t\t\t\talert(\"Prompt cannot be empty if the new host is choosed\");\n\t\t\t\t\t\tdocument.AMActionForm.prompt.focus();\n\t\t\t\t\t\treturn;\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n\t\t\t\t\tif(document.AMActionForm.choosehost.value=='-2')\n\t\t\t\t\t{\n\t\t\t\t\t\talert('");
/* 2850 */                         out.print(FormatUtil.getString("am.webclient.newscript.choosehost.text"));
/* 2851 */                         out.write("');\n\t\t\t\t\t\treturn;\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t}\n    var displayname=trimAll(document.AMActionForm.displayname.value);\n    //var exp_results=trimAll(document.AMActionForm.description.value);\n\n    var outputfile=trimAll(document.AMActionForm.outputfile.value);\n    var arguments=trimAll(document.AMActionForm.message.value);\n    var pollinterval=trimAll(document.AMActionForm.pollInterval.value);\n    var timeout=trimAll(document.AMActionForm.timeout.value);\n    var mode=\"\";\n    var string_att_table=\"\";\n    var numeric_att_table=\"\";\n    var primary_index=\"\";\n    var table_name=\"\";\n\n    ");
/*      */                         
/* 2853 */                         if ((!System.getProperty("os.name").startsWith("Windows")) && (!System.getProperty("os.name").startsWith("windows")))
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2860 */                           out.write("\n        mode=trimAll(document.AMActionForm.mode.value);\n        if(mode==\"\")\n        {\n\n        }\n        ");
/*      */                         }
/*      */                         
/*      */ 
/* 2864 */                         out.write("\n\n    if(displayname == \"\")\n    {\n        alert(\"");
/* 2865 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.displaynameempty.text"));
/* 2866 */                         out.write("\");\n        document.AMActionForm.displayname.focus();\n        return;\n    }\n    else\n    {\n        if(checkSpecialchar(document.AMActionForm.displayname.value)==false)\n        {\n        alert(\"");
/* 2867 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.displaynameillegal.text"));
/* 2868 */                         out.write("\");\n            document.AMActionForm.displayname.focus();\n            return false;\n        }\n    }\n    var serverpath=trimAll(document.AMActionForm.serverpath.value);\n    var exec_dir=trimAll(document.AMActionForm.workingdirectory.value);\n    if(serverpath==\"\")\n    {\n    alert(\"");
/* 2869 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.scriptempty.text"));
/* 2870 */                         out.write("\");\n        document.AMActionForm.serverpath.focus();\n        return;\n    }\n    else\n    {\n        if(document.AMActionForm.isCommand[0].checked==false && checkSpecialchar(document.AMActionForm.serverpath.value)==false)\n        {\n            alert(\"");
/* 2871 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.scriptnameillegal.text"));
/* 2872 */                         out.write("\");\n            document.AMActionForm.serverpath.focus();\n            return false;\n        }\n    }\n\n\n    if(arguments==\"\")\n    {\n    }\n    else\n    {\n        if(checkSpecialchar(document.AMActionForm.message.value)==false)\n        {\n        alert(\"");
/* 2873 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.argumentsillegal.text"));
/* 2874 */                         out.write("\");\n                document.AMActionForm.message.focus();\n                return false;\n        }\n    }\n\n    if(exec_dir==\"\")\n    {\n    alert(\"");
/* 2875 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.workingdirectoryempty.text"));
/* 2876 */                         out.write("\");\n        document.AMActionForm.workingdirectory.focus();\n        return;\n    }\n    else\n    {\n        if(checkSpecialchar(document.AMActionForm.workingdirectory.value)==false)\n        {\n        alert(\"");
/* 2877 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.workingdirectoryillegal.text"));
/* 2878 */                         out.write("\");\n            document.AMActionForm.workingdirectory.focus();\n            return false;\n        }\n    }\n    if(pollinterval ==\"\" ||!(isPositiveInteger(pollinterval)) || pollinterval =='0' )\n    {\n    alert(\"");
/* 2879 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.pollingintervalzero.text"));
/* 2880 */                         out.write("\");\n        document.AMActionForm.pollInterval.focus();\n        return;\n    }\n    ");
/*      */                         
/* 2882 */                         if (EnterpriseUtil.isAdminServer())
/*      */                         {
/*      */ 
/* 2885 */                           out.write("                                \n        if (document.AMActionForm.masSelectType[1].checked) {\n        \tvar selectedVal=document.AMActionForm.masGroupName.value;\n        \tif (selectedVal != null && selectedVal == \"-\") {\n                alert('");
/* 2886 */                           out.print(FormatUtil.getString("am.webclient.admin.add.monitor.select.masgroup.text"));
/* 2887 */                           out.write("');\n                document.AMActionForm.masGroupName.focus();\n                return;\n            }                                \t\n        } else if (document.AMActionForm.masSelectType[2].checked) {\n        \tselectedVal=document.AMActionForm.selectedServer.value;\n        \tif (selectedVal != null && selectedVal == \"-\") {\n                alert('");
/* 2888 */                           out.print(FormatUtil.getString("am.webclient.admin.addmonitor.select.mas.text"));
/* 2889 */                           out.write("');\n                document.AMActionForm.selectedServer.focus();\n                return;\n            }                                 \t\n        }\n    ");
/*      */                         }
/*      */                         
/*      */ 
/* 2893 */                         out.write("    \n    ");
/*      */                         
/* 2895 */                         if ((customType != null) && (customType.equals("true")))
/*      */                         {
/*      */ 
/*      */ 
/* 2899 */                           out.write("\n        if(outputfile==\"\" && document.AMActionForm.isFile.checked==true)\n        {\n            alert(\"");
/* 2900 */                           out.print(FormatUtil.getString("am.webclient.newscript.alert.outputfileempty.text"));
/* 2901 */                           out.write("\");\n            document.AMActionForm.outputfile.focus();\n            return;\n        }\n        ");
/*      */ 
/*      */                         }
/*      */                         else
/*      */                         {
/*      */ 
/* 2907 */                           out.write("\n    var string_att=trimAll(document.AMActionForm.string_att.value);\n    var numeric_att=trimAll(document.AMActionForm.numeric_att.value);\n    var delimiter=trimAll(document.AMActionForm.delimiter.value);\n    if(document.AMActionForm.opfile.checked==true)\n\t{\n    if(outputfile!=\"\" && string_att==\"\" && numeric_att==\"\")\n    {\n\tif(document.AMActionForm.tablespresent.checked)\n        {\n        }\n\telse\n\t{\n\t\talert(\"");
/* 2908 */                           out.print(FormatUtil.getString("am.webclient.newscript.alert.numericorstringempty.text"));
/* 2909 */                           out.write("\");\n\t\tif(string_att==\"\")\n\t\t{\n\t\tdocument.AMActionForm.string_att.focus();\n\t\t}\n\t\telse\n\t\t{\n\t\tdocument.AMActionForm.numeric_att.focus();\n\t\t}\n\t\treturn;\n\t}\n    }\n    if(outputfile==\"\" && (string_att!=\"\" || numeric_att!=\"\") && (document.AMActionForm.isFile.checked==true))\n    {\n    alert(\"");
/* 2910 */                           out.print(FormatUtil.getString("am.webclient.newscript.alert.outputfileempty.text"));
/* 2911 */                           out.write("\");\n        document.AMActionForm.outputfile.focus();\n        return;\n    }\n\t}\n\n    if(string_att==\"\")\n    {\n    }\n    else\n    {\n        if(checkSpecialchar(document.AMActionForm.string_att.value)==false)\n        {\n        alert(\"");
/* 2912 */                           out.print(FormatUtil.getString("am.webclient.newscript.alert.attributesillegal.text"));
/* 2913 */                           out.write("\");\n            document.AMActionForm.string_att.focus();\n            return false;\n        }\n    }\n\tif(numeric_att==\"\")\n    {\n    }\n    else\n    {\n        if(checkSpecialchar(document.AMActionForm.numeric_att.value)==false)\n        {\n        alert(\"");
/* 2914 */                           out.print(FormatUtil.getString("am.webclient.newscript.alert.attributesillegal.text"));
/* 2915 */                           out.write("\");\n            document.AMActionForm.numeric_att.focus();\n            return false;\n        }\n    }\n\n    if(document.AMActionForm.tablespresent.checked)\n    {\n\tfor(var i=1;i<=count;i++)\n\t{\n\t\tvar table_name=document.getElementById(\"tab\"+i).value;\n\t\tif(table_name==\"\")\n\t\t{\n\t\talert(\"");
/* 2916 */                           out.print(FormatUtil.getString("am.webclient.newscript.tablename.empty"));
/* 2917 */                           out.write("\");\n\t\treturn;\n\t\t}\n\t\tvar str_att_tab=document.getElementById(\"sa\"+i).value;\n\t\tvar num_att_tab=document.getElementById(\"na\"+i).value;\n\t\tif(str_att_tab==\"\" && num_att_tab==\"\")\n\t\t{\n\t\t\talert(\"");
/* 2918 */                           out.print(FormatUtil.getString("am.webclient.newscript.enterattributes.text"));
/* 2919 */                           out.write("\");\n\t\treturn;\n\t\t}\n\t\tvar str_att_tab=document.getElementById(\"pri_col_att\"+i).value;\n\t\tif(str_att_tab==\"\")\n\t\t{\n\t\t\talert(\"");
/* 2920 */                           out.print(FormatUtil.getString("am.webclient.newscript.uniquecolumn.empty"));
/* 2921 */                           out.write("\");\n\t\treturn;\n\t\t}\n\t}\n    }\n    ");
/*      */                         }
/*      */                         
/*      */ 
/* 2925 */                         out.write("\n    if(timeout==\"\")\n    {\n    alert(\"");
/* 2926 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.timeoutempty.text"));
/* 2927 */                         out.write("\");\n        document.AMActionForm.timeout.focus();\n        return;\n    }\n ");
/*      */                       }
/* 2929 */                       out.write("\n\n if(value==1)\n                        {\n\n                                ");
/* 2930 */                       if (wiz3 == null)
/*      */                       {
/*      */ 
/* 2933 */                         out.write("\n                                                if(trimAll(document.AMActionForm.haid.value) == '' || trimAll(document.AMActionForm.haid.value) == '-')\n                                                {\n                                                        // alert(\"Select the Monitor Group to associate this Monitor.\");\n                                                        // return;\n                                                }\n                                                else\n                                                {\n                                                        document.AMActionForm.addtoha.value=\"true\";\n                                                }\n                                        ");
/*      */                       }
/*      */                       
/*      */ 
/* 2937 */                       out.write("\n\n                        }\n\n                        else if(value==3)\n                        {\n                                document.AMActionForm.addtoha.value=\"true\";\n\n                                ");
/* 2938 */                       if ((((String)request.getAttribute("type")).equals("file")) || (((String)request.getAttribute("type")).equals("directory")) || (((String)request.getAttribute("type")).equals("File System Monitor"))) {
/* 2939 */                         out.write("\n\n                                document.AMActionForm.action='/updateScript.do?method=filemon&type=");
/* 2940 */                         out.print(request.getAttribute("type"));
/* 2941 */                         out.write("&value=3';\n\n                                 ");
/*      */                       } else {
/* 2943 */                         out.write("\n                                        document.AMActionForm.action=\"/updateScript.do?method=createscript&value=3\";\n                                ");
/*      */                       }
/* 2945 */                       out.write("\n\t}\n\n /***  IT360-1762 ISSUES CHANGES STARTS HERE ***/\n\n\t");
/* 2946 */                       if (EnterpriseUtil.isIt360MSPEdition()) {
/* 2947 */                         out.write("\n\n if (document.AMActionForm.organization.value== \"-\")\n  {\n \talert(\"");
/* 2948 */                         out.print(FormatUtil.getString("it360.addnewmonitor.err.checkcustomer"));
/* 2949 */                         out.write("\");\n \treturn;\n  }\n\n if (trimAll(document.AMActionForm.haid.value) == '' || document.AMActionForm.haid.value== \"-\")\n  {\n     alert(\"");
/* 2950 */                         out.print(FormatUtil.getString("it360.addnewmonitor.err.checksite"));
/* 2951 */                         out.write("\");\n     return;\n  }\n ");
/*      */                       }
/* 2953 */                       out.write("\n \n ");
/*      */                       
/* 2955 */                       IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2956 */                       _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2957 */                       _jspx_th_c_005fif_005f3.setParent(null);
/*      */                       
/* 2959 */                       _jspx_th_c_005fif_005f3.setTest("${checkForMonitorGroup}");
/* 2960 */                       int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2961 */                       if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                         for (;;) {
/* 2963 */                           out.write("\n\tvar haidValue = document.AMActionForm.haid.value\n\tif(haidValue == '-' || haidValue == ''){\n\talert(\"");
/* 2964 */                           out.print(FormatUtil.getString("am.webclient.newmonitor.selectmg.text"));
/* 2965 */                           out.write("\")\n\treturn\n\t}\n");
/* 2966 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2967 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2971 */                       if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2972 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                       }
/*      */                       
/* 2975 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2976 */                       out.write("\n\n/***  IT360-1762 ISSUES CHANGES ENDS HERE ***/\n\n    document.AMActionForm.table_row.value=count;\n    document.AMActionForm.submit();\n}\n\nfunction checkSpecialchar(value)\n{\n    var iChars =\"\\'\";\n    for (var i = 0; i < value.length; i++)\n    {\n        if (iChars.indexOf(value.charAt(i)) != -1)\n        {\n            return false;\n        }\n    }\n}\n\n\nfunction formReload()\n{\n    var v=document.AMActionForm.type.value;\n    //var portNo=v.substring(v.indexOf(\":\")+1,v.length);\n    //document.AMActionForm.method=\"post\";\n    document.AMActionForm.action=\"/adminAction.do?method=reloadHostDiscoveryForm&type=\"+v+\"&hideFieldsForIT360=");
/* 2977 */                       out.print(request.getParameter("hideFieldsForIT360"));
/* 2978 */                       out.write("\";\n    //enableAll();\n    document.AMActionForm.submit();\n}\n\n\nfunction deletehost()\n{\n\n        var monitortype=\"");
/* 2979 */                       out.print(monitortype);
/* 2980 */                       out.write("\";\n        if(document.AMActionForm.choosehost.value=='-2' || document.AMActionForm.choosehost.value=='-1')\n        {\n                alert('");
/* 2981 */                       out.print(FormatUtil.getString("am.webclient.wmi.validhost.alert.text"));
/* 2982 */                       out.write("');\n                return;\n        }\n        else if(confirm(\"");
/* 2983 */                       out.print(FormatUtil.getString("am.webclient.wmi.deletehost.confirm.text"));
/* 2984 */                       out.write("\"))\n        {\n\n        \t choosehostval=document.AMActionForm.choosehost.value;\n             var host=choosehostval.split(\",\");\n             if(host[0]!=2){\n           \t\t location.href=\"/WMIPerfCounters.do?method=deleteHost&host=\"+host[1]+\"&type=script&resType=\"+monitortype;//No I18N\n             }\n             else{\n                 alert('");
/* 2985 */                       out.print(FormatUtil.getString("am.webclient.wmi.deletehost.server.text"));
/* 2986 */                       out.write("');\n             }\n         }\n         else\n         {\n         return;\n         }\n}\n\n\nfunction myOnLoad()\n{\n\tif(");
/* 2987 */                       out.print(tableids.size());
/* 2988 */                       out.write(" > 0)\n  {\n  document.AMActionForm.tablespresent.checked=true;\n  }\n\n  if(document.AMActionForm.serversite[0]){\n    document.AMActionForm.serversite[0].checked=true;\n    }\n    manageremotescript();\n    changeport();\n    if($('input[name=isCredManager]:checked').val() =='true')\n\t  {\n\t\tdocument.getElementById(\"credentialFormDiv\").style.display=\"none\";\n\t\tswitchCredentialProfiles();\n\t  }\n    else{\n    \tdocument.getElementById(\"credentialDropDiv\").style.display=\"none\";\n    }\n");
/*      */                       
/* 2990 */                       if ((!((String)request.getAttribute("type")).equals("file")) && (!((String)request.getAttribute("type")).equals("directory")) && (!((String)request.getAttribute("type")).equals("File System Monitor"))) {
/* 2991 */                         out.write("\n    \t\tmanageopFile();\n    showTables();\n\n");
/*      */                       } else {
/* 2993 */                         out.write("\n\t\tif(document.AMActionForm.mtype[0]){\n\t\t\t  document.AMActionForm.mtype[0].checked=true;\n\t\t}\n\t\tmanagecontent();\n\t\tloadTextBox();\n\t\t loadTextBox1();\n\t\t showClearCondnTitle();\n\t");
/*      */                       }
/* 2995 */                       out.write("\n\n\n\tjavascript:hideDiv('view');\n\t\n\tif(!document.getElementById('HelpDetails')){\n\t\tdocument.getElementById('DiscoveryDetailsTD').style.width=\"100%\";\n\t\tdocument.getElementById('newResourceTypes').style.width=\"100%\";\n\t\tdocument.getElementById('DiscoveryDetails').style.width=\"100%\";\n\t\tdocument.getElementById('AssociateDiscoveryDetails').style.width=\"100%\";\n\t\tdocument.getElementById('SubmitDiscoveryDetails').style.width=\"100%\";\n\t\t\n\t}\n\t\n\tif(");
/* 2996 */                       out.print(EnterpriseUtil.isAdminServer());
/* 2997 */                       out.write(") {\n\t\tvar radioObj = document.AMActionForm.masSelectType;\n\t\tif (radioObj !=null && radioObj != \"undefined\") {\n\t\t\tvar val='0';\n\t\t\tif (radioObj[1].checked) {\n\t\t\t\tval='1';\n\t\t\t} else if (radioObj[2].checked){\n\t\t\t\tval='2';\n\t\t\t}\n\t\t\tshowAsPerSelection(val);\n\t\t}\t\n\t}\n\tcount=");
/* 2998 */                       out.print(tableids.size());
/* 2999 */                       out.write(";\n}\n\n\nfunction manageCommand() {\n\tif(document.AMActionForm.isCommand[0].checked == true) {\n\t\tdocument.AMActionForm.mode.disabled=true;\n\t} else {\n\t\tdocument.AMActionForm.mode.disabled=false;\n\t}\n}\n\nfunction manageremotescript()\n{\n    if(document.AMActionForm.serversite[0] && document.AMActionForm.serversite[0].checked == true)\n    {\n        javascript:hideDiv('remotescript');\n        javascript:hideDiv('newhost');\n\t\tjavascript:hideDiv('windowsmode');\n\t    \n    }\n    else\n    {\n    \t//javascript:showDiv('remotescript');\n    \tdocument.getElementById(\"remotescript\").style.display='table-row';\n\t    javascript:showDiv('windowsmode');\n\t");
/*      */                       
/* 3001 */                       if ((((String)request.getAttribute("type")).equals("file")) || (((String)request.getAttribute("type")).equals("directory")) || (((String)request.getAttribute("type")).equals("File System Monitor"))) {
/* 3002 */                         out.write("\n\t if(document.AMActionForm.mtype[0].checked == true)\n        ");
/* 3003 */                         if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows")))
/* 3004 */                           out.write("\n        //javascript:showDiv('content');\n           document.getElementById(\"content\").style.display='table-row';\n         ");
/*      */                       }
/* 3006 */                       out.write("\n\n        if(document.AMActionForm.choosehost.value=='-1')\n        {\n        \t // javascript:showDiv('newhost');\n        \tdocument.getElementById(\"newhost\").style.display='table-row';\n        }\n    }\n}\n\nfunction managecontent(){\n\t\n\tif(document.AMActionForm.fileDirAge.checked == true){\n\t\tFileDirAgeCheck();\n\t}\n\tif(document.AMActionForm.mtype[0].checked == true){\n\t\t\n\t\tdocument.getElementById(\"content\").style.display='table-row';\n\t\thideDiv('subDirCountChk');\n\t\t\n\t\tif(document.AMActionForm.contentChk.checked == true){\n\t\t\tContentCheck();\n\t\t}\t\n\t\tif(document.AMActionForm.regexChk.checked == true){\n\t\t\tRegexCheck();\n\t\t}\n\t\tdocument.getElementById(\"location\").innerHTML ='<label>");
/* 3007 */                       out.print(FormatUtil.getString("File Location"));
/* 3008 */                       out.write("</label>'\n\t\tdocument.getElementById(\"name\").innerHTML ='<label><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,\\'");
/* 3009 */                       out.print(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/* 3010 */                       out.write("\\',false,true,\\'#000000\\',200,\\'lightyellow\\')\" onmouseout=\"hideddrivetip()\">");
/* 3011 */                       out.print(FormatUtil.getString("File Name"));
/* 3012 */                       out.write("</a> <span class=\"mandatory\">*</span></label>'\n\t}\n\telse if(document.AMActionForm.mtype[1].checked == true){\n\t\tjavascript:hideDiv('content');\n\t\tjavascript:hideDiv('contentChk11');\n\t\tdocument.getElementById(\"subDirCountChk\").style.display='table-row';\n\t\tdocument.getElementById(\"location\").innerHTML ='<label>");
/* 3013 */                       out.print(FormatUtil.getString("Directory Location"));
/* 3014 */                       out.write("</label>'\n\t\tdocument.getElementById(\"name\").innerHTML = '<label><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,\\'");
/* 3015 */                       out.print(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/* 3016 */                       out.write("\\',false,true,\\'#000000\\',200,\\'lightyellow\\')\" onmouseout=\"hideddrivetip()\">");
/* 3017 */                       out.print(FormatUtil.getString("Directory Name"));
/* 3018 */                       out.write("</a> <span class=\"mandatory\">*</span></label>'\n\n\t}\n\telse{\n\tdocument.getElementById(\"location\").innerHTML ='");
/* 3019 */                       out.print(FormatUtil.getString("File/Directory Location"));
/* 3020 */                       out.write("'\ndocument.getElementById(\"name\").innerHTML = '<label><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,\\'");
/* 3021 */                       out.print(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/* 3022 */                       out.write("\\',false,true,\\'#000000\\',200,\\'lightyellow\\')\" onmouseout=\"hideddrivetip()\">");
/* 3023 */                       out.print(FormatUtil.getString("File/Directory Name"));
/* 3024 */                       out.write("</a> <span class=\"mandatory\">*</span></label>'\n\n\t}\n}\n\nfunction managenewHost()\n{\n  //if(document.AMActionForm.newserver.checked==false)\n  if(document.AMActionForm.choosehost.value=='-1')\n  {\n\t  // javascript:showDiv('newhost');\n\t  document.getElementById(\"newhost\").style.display='table-row';\n  }\n  else\n  {\n\t");
/* 3025 */                       if ((((String)request.getAttribute("type")).equals("file")) || (((String)request.getAttribute("type")).equals("directory")) || (((String)request.getAttribute("type")).equals("File System Monitor"))) {
/* 3026 */                         out.write("\n\n        javascript:hideDiv('newhost');\n\t\tchoosehostval=document.AMActionForm.choosehost.value;\n\t\tvar choosehost;\n\t\tif(choosehostval != ''){\n\t\t\tchoosehost=choosehostval.split(\",\");\n\t  \t\tURL='/jsp/formpages/Error.jsp?id='+choosehost[1];\n\t        http1=getHTTPObject();\n\t        http1.open(\"GET\", URL, true);\n\t        http1.onreadystatechange =handleResponse;\n\t        http1.send(null);\n\t\t}\n\t ");
/*      */                       } else {
/* 3028 */                         out.write("\n\n\t  javascript:hideDiv('newhost');\n\t  ");
/*      */                       }
/* 3030 */                       out.write("\n  }\n}\n\n\nfunction handleResponse()\n{\n\t\n        if(http1.readyState == 4)\n        {\n                var result = http1.responseText;\n\t\tdocument.AMActionForm.wmihost.value = trimAll(result);\n\t\tchoosehostval=document.AMActionForm.choosehost.value;\n \t\t var choosehost=choosehostval.split(\",\");\n\t\t if(choosehost[1] == document.AMActionForm.wmihost.value){\n\n\t\t\t\t\tif(document.AMActionForm.mtype[1].checked == true){\n\t\t\t\t\t\tjavascript:hideDiv('contentChk11');\n                                      //  alert('");
/* 3031 */                       out.print(FormatUtil.getString("am.webclient.rdir.alert"));
/* 3032 */                       out.write("');\n                                     //   javascript:hideDiv('remotescript');\n                                    //     if(document.AMActionForm.serversite[0]){\n                                     //   document.AMActionForm.serversite[0].checked=true;}\n                    }\n\t\t\t\t\t\t\n               }else{\n                   //javascript:showDiv('content');\n            \t   if(document.AMActionForm.mtype[0].checked == true){\n\t\t\t\t\tdocument.getElementById(\"content\").style.display='table-row';\n               \t\t}\n                }\n        }\n}\n\n\nfunction loadTextBox()\n{\n\t\n\tif(document.getElementsByName(\"selectRuleType\")[0].value==0)\n\t{\n\t\tdocument.getElementById(\"cntval11\").width=\"7%\";\n\t\tdocument.getElementById(\"selectValue\").style.display='block'; //No I18N\n\t}\n\telse if(document.getElementsByName(\"selectRuleType\")[0].value==1)\n\t{\n\t\tdocument.getElementById(\"cntval11\").width=\"1%\";\n\t\tdocument.getElementById(\"selectValue\").style.display='none'; //No I18N\n\t\t\n\t\t\n\t}\n}\n\nfunction loadTextBox1()\n{\n\t\n\tif(document.getElementsByName(\"clearConditionRuleType\")[0].value==0)\n");
/* 3033 */                       out.write("\t{\n\t\tdocument.getElementById(\"cntval22\").width=\"7%\";\n\t\tdocument.getElementById(\"selectValue2\").style.display='block'; //No I18N\n\t}\n\telse if(document.getElementsByName(\"clearConditionRuleType\")[0].value==1)\n\t{\n\t\tdocument.getElementById(\"cntval22\").width=\"1%\";\n\t\tdocument.getElementById(\"selectValue2\").style.display='none'; //No I18N\n\t\t\n\t\t\n\t}\n}\n\nfunction showClearCondnTitle() {\n\t\n\tif(document.getElementsByName(\"selectStatusType\")[0].value==0)\n\t{\n\t\tdocument.getElementById(\"ClearCondnUp\").style.display='block'; //No I18N\n\t\tdocument.getElementById(\"ClearCondnDown\").style.display='none'; //No I18N\n\t}\n\telse if(document.getElementsByName(\"selectStatusType\")[0].value==1)\n\t{\n\t\tdocument.getElementById(\"ClearCondnUp\").style.display='none'; //No I18N\n\t\tdocument.getElementById(\"ClearCondnDown\").style.display='block'; //No I18N\n\t}\n}\n\nfunction showHideCheckEmpty()\n{\n    if(document.AMActionForm.fileCheckType[0].checked==true)\n    {\n        document.getElementById(\"checkEmptyId\").style.display='table-row';\n    }\n    else\n    {\n");
/* 3034 */                       out.write("        document.getElementById(\"checkEmptyId\").style.display='none';\n    }\n}\n\nfunction manageopFile()\n{\n");
/*      */                       
/* 3036 */                       if ((customType == null) || (!customType.equals("true")))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3043 */                         out.write("\n  if(document.AMActionForm.opfile.checked==false)\n  {\n        javascript:hideDiv('opdetails');\n  }\n  else\n  {\n        javascript:showDiv('opdetails');\n  }\n  ");
/*      */                       }
/*      */                       
/*      */ 
/* 3047 */                       out.write("\n\tdocument.AMActionForm.outputfile.disabled=true;\n}\n\nfunction enableDisableFile() {\n\tif(document.AMActionForm.isFile.checked==true)\t{\n\t\tdocument.AMActionForm.outputfile.disabled=false;\n\t} else {\n\t\tdocument.AMActionForm.outputfile.disabled=true;\n\t}\n}\n\nfunction showCredentialProfiles()\n{\n\t\n  if($('input[name=isCredManager]:checked').val() =='true')\n  {\n\tdocument.getElementById(\"credentialDropDiv\").style.display=\"block\";\n\tdocument.getElementById(\"credentialFormDiv\").style.display=\"none\";\n\tswitchCredentialProfiles();\n  }\n  else\n  {\n       document.getElementById(\"credentialDropDiv\").style.display=\"none\";\n       document.getElementById(\"credentialDropRow\").height=\"1px\";\n       document.getElementById(\"credentialFormDiv\").style.display=\"block\";\n  }\n}\n\nfunction switchCredentialProfiles()\n{\t\n\t\n\tvar mode=trimAll(document.AMActionForm.monitoringmode.value);\n\tfor (var i = 0; i < document.AMActionForm.credentialID.length; i++) \n\t{ //Clear the 2nd menu\n\t\tdocument.AMActionForm.credentialID.options[i] = null;\n\t}\n    var i = 0;\n");
/* 3048 */                       out.write("    if(mode =='SSH')\n    {\n    \t");
/* 3049 */                       if (((HashMap)request.getAttribute("credentialHash") != null) && (((HashMap)request.getAttribute("credentialHash")).containsKey("SSH"))) {
/* 3050 */                         out.write("\n\t       ");
/* 3051 */                         if (_jspx_meth_c_005fforEach_005f2(_jspx_page_context))
/*      */                           return;
/* 3053 */                         out.write("\n\t  \t");
/*      */                       } else {
/* 3055 */                         out.write("\n\t\t\t  document.AMActionForm.credentialID.options[i] = new Option('No Credentials','No Credentials',0,0);  ");
/* 3056 */                         out.write(10);
/* 3057 */                         out.write(9);
/* 3058 */                         out.write(9);
/*      */                       }
/* 3060 */                       out.write("\n    }\n    else if(mode =='TELNET')\n    {\n      ");
/* 3061 */                       if (((HashMap)request.getAttribute("credentialHash") != null) && (((HashMap)request.getAttribute("credentialHash")).containsKey("TELNET"))) {
/* 3062 */                         out.write("\n\t      ");
/* 3063 */                         if (_jspx_meth_c_005fforEach_005f3(_jspx_page_context))
/*      */                           return;
/* 3065 */                         out.write("\n      ");
/*      */                       } else {
/* 3067 */                         out.write("\n\t\t  document.AMActionForm.credentialID.options[i] = new Option('No Credentials','No Credentials',0,0);  ");
/* 3068 */                         out.write("\n\t  ");
/*      */                       }
/* 3070 */                       out.write("\n    }\n    else\n    {\n      ");
/* 3071 */                       if (((HashMap)request.getAttribute("credentialHash") != null) && (((HashMap)request.getAttribute("credentialHash")).containsKey("WMI"))) {
/* 3072 */                         out.write("\n\t       ");
/* 3073 */                         if (_jspx_meth_c_005fforEach_005f4(_jspx_page_context))
/*      */                           return;
/* 3075 */                         out.write("\n      ");
/*      */                       } else {
/* 3077 */                         out.write("\n\t\t  document.AMActionForm.credentialID.options[i] = new Option('No Credentials','No Credentials',0,0);  ");
/* 3078 */                         out.write("\n\t  ");
/*      */                       }
/* 3080 */                       out.write("\n    }\n    \n}\n</script>\n");
/* 3081 */                       String type = (String)request.getAttribute("type");
/* 3082 */                       String heading = null;
/* 3083 */                       if (type.equals("File System Monitor")) {
/* 3084 */                         heading = "File / Directory Monitor";
/*      */                       } else {
/* 3086 */                         heading = "Script Monitor";
/*      */                       }
/* 3088 */                       out.write("\n\n<body onLoad=\"javascript:myOnLoad();\">\n    ");
/*      */                       
/* 3090 */                       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 3091 */                       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 3092 */                       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                       
/* 3094 */                       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 3095 */                       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 3096 */                       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                         for (;;) {
/* 3098 */                           out.write("\n    ");
/*      */                           
/* 3100 */                           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3101 */                           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 3102 */                           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 3104 */                           _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                           
/* 3106 */                           _jspx_th_tiles_005fput_005f0.setValue(heading);
/* 3107 */                           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 3108 */                           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 3109 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                           }
/*      */                           
/* 3112 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3113 */                           out.write("\n    ");
/* 3114 */                           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 3116 */                           out.write("\n   \n    ");
/*      */                           
/* 3118 */                           PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 3119 */                           _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 3120 */                           _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 3122 */                           _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*      */                           
/* 3124 */                           _jspx_th_tiles_005fput_005f2.setType("string");
/* 3125 */                           int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 3126 */                           if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 3127 */                             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 3128 */                               out = _jspx_page_context.pushBody();
/* 3129 */                               _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 3130 */                               _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3133 */                               out.write(10);
/* 3134 */                               out.write(9);
/*      */                               
/* 3136 */                               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction.get(FormTag.class);
/* 3137 */                               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 3138 */                               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 3140 */                               _jspx_th_html_005fform_005f0.setAction("/updateScript");
/*      */                               
/* 3142 */                               _jspx_th_html_005fform_005f0.setFocus("displayname");
/*      */                               
/* 3144 */                               _jspx_th_html_005fform_005f0.setEnctype("multipart/form-data");
/* 3145 */                               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 3146 */                               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                 for (;;) {
/* 3148 */                                   out.write("\n\t<input type=\"hidden\" name=\"addtoha\" value=\"");
/* 3149 */                                   out.print(request.getParameter("addtoha"));
/* 3150 */                                   out.write("\">\n\t<input type=\"hidden\" name=\"table_row\" value=1>\n        ");
/*      */                                   
/* 3152 */                                   if ((customType != null) && (customType.equals("true")))
/*      */                                   {
/*      */ 
/* 3155 */                                     out.write("\n        <input type=\"hidden\" name=\"customType\" value=\"true\">\n        <input type=\"hidden\" name=\"monitorType\" value=\"");
/* 3156 */                                     out.print(request.getAttribute("type"));
/* 3157 */                                     out.write("\">\n        ");
/*      */                                   }
/*      */                                   
/* 3160 */                                   boolean isDiscoveryComplete = false;
/* 3161 */                                   boolean isDiscoverySuccess = false;
/*      */                                   
/* 3163 */                                   out.write(10);
/* 3164 */                                   out.write(9);
/*      */                                   
/* 3166 */                                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3167 */                                   _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 3168 */                                   _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 3170 */                                   _jspx_th_logic_005fnotEmpty_005f0.setName("discoverystatus");
/* 3171 */                                   int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 3172 */                                   if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                                     for (;;) {
/* 3174 */                                       out.write(32);
/* 3175 */                                       out.write(32);
/* 3176 */                                       out.write("<!--$Id$-->\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/diagnosepage.js\"></SCRIPT>\n");
/*      */                                       
/* 3178 */                                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3179 */                                       _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 3180 */                                       _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                       
/* 3182 */                                       _jspx_th_logic_005fnotEmpty_005f1.setName("discoverystatus");
/* 3183 */                                       int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 3184 */                                       if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                                         for (;;) {
/* 3186 */                                           out.write(10);
/*      */                                           
/*      */ 
/* 3189 */                                           if ((request.getAttribute("type") == null) || ((!request.getAttribute("type").equals("Script Monitor")) && ((request.getAttribute("basetype") == null) || ((request.getAttribute("basetype") != null) && (!request.getAttribute("basetype").equals("Script Monitor")))) && (!request.getAttribute("type").equals("QENGINE")) && (!request.getAttribute("type").equals("Web Service")) && (!request.getAttribute("type").equals("file")) && (!request.getAttribute("type").equals("directory")) && (!request.getAttribute("type").equals("File System Monitor")) && (!request.getAttribute("type").equals("Ping Monitor")) && (!request.getAttribute("type").equals("SAP-CCMS"))))
/*      */                                           {
/*      */ 
/* 3192 */                                             out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n  <tr>\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3193 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 3194 */                                             out.write("</span> </td>\n    <td width=\"7%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3195 */                                             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverport"));
/* 3196 */                                             out.write("\n      </span> </td>\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3197 */                                             out.print(FormatUtil.getString("am.webclient.oracle.Status"));
/* 3198 */                                             out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3199 */                                             out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 3200 */                                             out.write("\n </span></td>\n  <tr>\n  ");
/*      */                                             
/* 3202 */                                             int failedNumber = 1;
/*      */                                             
/* 3204 */                                             out.write(10);
/*      */                                             
/* 3206 */                                             IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3207 */                                             _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 3208 */                                             _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                                             
/* 3210 */                                             _jspx_th_logic_005fiterate_005f0.setName("discoverystatus");
/*      */                                             
/* 3212 */                                             _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                                             
/* 3214 */                                             _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*      */                                             
/* 3216 */                                             _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 3217 */                                             int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 3218 */                                             if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 3219 */                                               ArrayList row = null;
/* 3220 */                                               Integer i = null;
/* 3221 */                                               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3222 */                                                 out = _jspx_page_context.pushBody();
/* 3223 */                                                 _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 3224 */                                                 _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                               }
/* 3226 */                                               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3227 */                                               i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                               for (;;) {
/* 3229 */                                                 out.write("\n<tr>\n<td height=\"18\" class=\"bodytext\">");
/* 3230 */                                                 out.print(row.get(0));
/* 3231 */                                                 out.write("</td>\n<td height=\"18\" class=\"bodytext\">");
/* 3232 */                                                 out.print(row.get(1));
/* 3233 */                                                 out.write("</td>\n\n    <td height=\"18\" class=\"bodytext\">\n      ");
/*      */                                                 
/* 3235 */                                                 if ((row.get(2).equals("Success")) || (row.get(2).equals("Present")))
/*      */                                                 {
/* 3237 */                                                   request.setAttribute("isDiscoverySuccess", "true");
/*      */                                                   
/* 3239 */                                                   out.write("\n      <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 3240 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 3241 */                                                   out.write("\" align=\"absmiddle\">\n      ");
/*      */ 
/*      */                                                 }
/*      */                                                 else
/*      */                                                 {
/* 3246 */                                                   request.setAttribute("isDiscoverySuccess", "false");
/*      */                                                   
/* 3248 */                                                   out.write("\n      <img alt=\"");
/* 3249 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.notiniatedimagetip.text"));
/* 3250 */                                                   out.write("\" src=\"/images/icon_monitor_failure.gif\" align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                                                 }
/*      */                                                 
/*      */ 
/* 3254 */                                                 out.write("\n      <span class=\"bodytextbold\">");
/* 3255 */                                                 out.print(FormatUtil.getString(String.valueOf(row.get(2))));
/* 3256 */                                                 out.write("</span> </td>\n\n      ");
/*      */                                                 
/* 3258 */                                                 pageContext.setAttribute("ret_msg", String.valueOf(row.get(3)));
/*      */                                                 
/* 3260 */                                                 out.write("\n                     ");
/*      */                                                 
/* 3262 */                                                 IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3263 */                                                 _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 3264 */                                                 _jspx_th_c_005fif_005f4.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                                 
/* 3266 */                                                 _jspx_th_c_005fif_005f4.setTest("${ret_msg=='Monitoring Initiated Succesfully'}");
/* 3267 */                                                 int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 3268 */                                                 if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                                   for (;;) {
/* 3270 */                                                     out.write("\n                           <td height=\"18\" class=\"bodytext\">");
/* 3271 */                                                     out.print(FormatUtil.getString("am.webclient.discovery.host.successfullyiniated.text"));
/* 3272 */                                                     out.write("\n                                 ");
/* 3273 */                                                     int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3274 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 3278 */                                                 if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3279 */                                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                                 }
/*      */                                                 
/* 3282 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3283 */                                                 out.write("\n                                       ");
/*      */                                                 
/* 3285 */                                                 IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3286 */                                                 _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3287 */                                                 _jspx_th_c_005fif_005f5.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                                 
/* 3289 */                                                 _jspx_th_c_005fif_005f5.setTest("${ret_msg!='Monitoring Initiated Succesfully'}");
/* 3290 */                                                 int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3291 */                                                 if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                                   for (;;) {
/* 3293 */                                                     out.write("\n                                             <td height=\"18\" class=\"bodytext\">");
/* 3294 */                                                     out.print(row.get(3));
/* 3295 */                                                     out.write("\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                                     
/* 3297 */                                                     if ((request.getAttribute("type") != null) && (!request.getAttribute("type").equals("All")) && (!request.getAttribute("type").equals("TELNET")) && (!request.getAttribute("type").equals("SNMP")) && (!request.getAttribute("type").equals("SERVICE")) && (!request.getAttribute("type").equals("JMX1.2-MX4J-RMI")) && (!request.getAttribute("type").equals("JDK1.5")) && (!request.getAttribute("type").equals("RMI")) && (!request.getAttribute("type").equals("PHP")) && (!request.getAttribute("type").equals("MAIL")) && (!request.getAttribute("type").equals("ORACLEDB")) && (!request.getAttribute("type").equals("MYSQLDB")) && (!request.getAttribute("type").equals("MSSQLDB")) && (!request.getAttribute("type").equals("DB2")) && (!request.getAttribute("type").equals("WTA")) && (!request.getAttribute("type").equals("SAP")))
/*      */                                                     {
/* 3299 */                                                       if (((String)pageContext.getAttribute("ret_msg")).indexOf(FormatUtil.getString("am.webclient.discovery.host.monitoradded.text")) == -1)
/*      */                                                       {
/* 3301 */                                                         String fWhr = request.getParameter("hideFieldsForIT360");
/* 3302 */                                                         if (fWhr == null)
/*      */                                                         {
/* 3304 */                                                           fWhr = (String)request.getAttribute("hideFieldsForIT360");
/*      */                                                         }
/*      */                                                         
/* 3307 */                                                         if (((fWhr == null) || (!fWhr.equals("true"))) && 
/* 3308 */                                                           (!request.getAttribute("type").equals("SYBASEDB")))
/*      */                                                         {
/* 3310 */                                                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t &nbsp;&nbsp;&nbsp;&nbsp;<a class=\"bodytext\" href=\"javascript:void(0)\" onClick=\"getAllDetailsOfHost()\">");
/* 3311 */                                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.diagnose.link"));
/* 3312 */                                                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                                         }
/*      */                                                       } }
/* 3315 */                                                     if ((request.getAttribute("showSupportMessage") != null) && (failedNumber == 1) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */                                                     {
/* 3317 */                                                       failedNumber++;
/*      */                                                       
/*      */ 
/* 3320 */                                                       out.write("\n\t\t\t\t\t\t\t\t\t\t<br>");
/* 3321 */                                                       out.print(FormatUtil.getString("am.webclient.discovery.host.support.link", new String[] { OEMUtil.getOEMString("product.talkback.mailid"), OEMUtil.getOEMString("product.tollfree.number") }));
/* 3322 */                                                       out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                                     }
/* 3324 */                                                     out.write("\n                                                   ");
/* 3325 */                                                     int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3326 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 3330 */                                                 if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3331 */                                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                                 }
/*      */                                                 
/* 3334 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3335 */                                                 out.write(10);
/* 3336 */                                                 out.write(10);
/* 3337 */                                                 out.write(10);
/*      */                                                 
/*      */ 
/* 3340 */                                                 if (row.size() > 4)
/*      */                                                 {
/*      */ 
/* 3343 */                                                   out.write("<br>\n");
/* 3344 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)row.get(4) }));
/* 3345 */                                                   out.write(10);
/*      */                                                 }
/*      */                                                 
/*      */ 
/* 3349 */                                                 out.write("\n</td>\n\n</tr>\n");
/* 3350 */                                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 3351 */                                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3352 */                                                 i = (Integer)_jspx_page_context.findAttribute("i");
/* 3353 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3356 */                                               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3357 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3360 */                                             if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 3361 */                                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                             }
/*      */                                             
/* 3364 */                                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 3365 */                                             out.write("\n</table>\n");
/*      */ 
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 3370 */                                             ArrayList al1 = (ArrayList)request.getAttribute("discoverystatus");
/*      */                                             
/* 3372 */                                             out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n<tr>\n");
/* 3373 */                                             String mtype = (String)request.getAttribute("type");
/* 3374 */                                             out.write(10);
/* 3375 */                                             if (mtype.equals("File System Monitor")) {
/* 3376 */                                               out.write("\n  <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3377 */                                               out.print(FormatUtil.getString("File/Directory Name"));
/* 3378 */                                               out.write("</span> </td>\n");
/* 3379 */                                             } else if ((((String)request.getAttribute("type")).equals("Ping Monitor")) || (((String)request.getAttribute("type")).equals("SAP-CCMS"))) {
/* 3380 */                                               out.write("\n\t<td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3381 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 3382 */                                               out.write("</span> </td>\n");
/*      */                                             } else {
/* 3384 */                                               out.write("\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3385 */                                               out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 3386 */                                               out.write("</span> </td>\n");
/*      */                                             }
/* 3388 */                                             out.write("\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3389 */                                             out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.status"));
/* 3390 */                                             out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3391 */                                             out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 3392 */                                             out.write("</span></td>\n  </tr>\n  <tr>\n   <td height=\"18\" class=\"bodytext\">");
/* 3393 */                                             out.print(al1.get(0));
/* 3394 */                                             out.write("</td>\n   <td height=\"18\" class=\"bodytext\">\n   ");
/*      */                                             
/* 3396 */                                             if (al1.get(1).equals("Success"))
/*      */                                             {
/* 3398 */                                               request.setAttribute("isDiscoverySuccess", "true");
/*      */                                               
/* 3400 */                                               out.write("\n   <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 3401 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 3402 */                                               out.write("\" align=\"absmiddle\">\n    ");
/*      */ 
/*      */                                             }
/*      */                                             else
/*      */                                             {
/* 3407 */                                               request.setAttribute("isDiscoverySuccess", "false");
/*      */                                               
/*      */ 
/* 3410 */                                               out.write("\n      <img  src=\"/images/icon_monitor_failure.gif\"  align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                                             }
/*      */                                             
/*      */ 
/* 3414 */                                             out.write("\n<span class=\"bodytextbold\">");
/* 3415 */                                             out.print(FormatUtil.getString(String.valueOf(al1.get(1))));
/* 3416 */                                             out.write("</span> </td>\n");
/*      */                                             
/* 3418 */                                             if (al1.get(1).equals("Success"))
/*      */                                             {
/* 3420 */                                               boolean isAdminServer = EnterpriseUtil.isAdminServer();
/* 3421 */                                               if (isAdminServer) {
/* 3422 */                                                 String masDisplayName = (String)al1.get(3);
/* 3423 */                                                 String format = FormatUtil.getString("am.webclient.admin.add.monitor.successfully.configured.text", new String[] { masDisplayName, "", (String)al1.get(2) });
/*      */                                                 
/* 3425 */                                                 out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\">");
/* 3426 */                                                 out.print(format);
/* 3427 */                                                 out.write("</td>\n");
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 3431 */                                                 out.write("\t\t\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"> ");
/* 3432 */                                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 3433 */                                                 out.write("<br> ");
/* 3434 */                                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)al1.get(2) }));
/* 3435 */                                                 out.write("</td>\n");
/*      */                                               }
/*      */                                               
/*      */ 
/*      */                                             }
/*      */                                             else
/*      */                                             {
/* 3442 */                                               out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"><span class=\"bodytext\">");
/* 3443 */                                               out.print(al1.get(2));
/* 3444 */                                               out.write("</span></td>\n");
/*      */                                             }
/*      */                                             
/*      */ 
/* 3448 */                                             out.write("\n  </tr>\n</table>\n\n");
/*      */                                           }
/*      */                                           
/*      */ 
/* 3452 */                                           out.write(10);
/* 3453 */                                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 3454 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3458 */                                       if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 3459 */                                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                                       }
/*      */                                       
/* 3462 */                                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3463 */                                       out.write(10);
/* 3464 */                                       out.write(10);
/* 3465 */                                       out.write(9);
/*      */                                       
/* 3467 */                                       String discSucc = (String)request.getAttribute("isDiscoverySuccess");
/* 3468 */                                       isDiscoveryComplete = true;
/* 3469 */                                       if ((discSucc != null) && (discSucc.equals("true")))
/*      */                                       {
/* 3471 */                                         isDiscoverySuccess = true;
/*      */                                       }
/*      */                                       
/* 3474 */                                       out.write(10);
/* 3475 */                                       out.write(9);
/* 3476 */                                       out.write(10);
/* 3477 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3478 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3482 */                                   if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3483 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                                   }
/*      */                                   
/* 3486 */                                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3487 */                                   out.write(10);
/* 3488 */                                   out.write(9);
/* 3489 */                                   if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 3491 */                                   out.write(10);
/* 3492 */                                   String mtype = (String)request.getAttribute("type");
/* 3493 */                                   out.write("\n<table width=\"95%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n\n");
/*      */                                   
/* 3495 */                                   if (((String)request.getAttribute("type") != null) && (((String)request.getAttribute("type")).equals("Script Monitor")))
/*      */                                   {
/*      */ 
/* 3498 */                                     out.write("\n\n\n\n\n  ");
/*      */                                   }
/*      */                                   
/* 3501 */                                   out.write("\n\n\n\n\n");
/* 3502 */                                   if ((mtype.equals("file")) || (mtype.equals("directory")) || (mtype.equals("File System Monitor"))) {
/* 3503 */                                     out.write("\n\n <input type=\"hidden\" name=\"method\" value=\"filemon\"/>\n");
/*      */                                   } else {
/* 3505 */                                     out.write("\n <input type=\"hidden\" name=\"method\" value=\"createscript\"/>\n");
/*      */                                   }
/* 3507 */                                   out.write("\n </table>\n");
/*      */                                   
/* 3509 */                                   if (OEMUtil.isOEM())
/*      */                                   {
/* 3511 */                                     AMActionForm frm = (AMActionForm)request.getAttribute("AMActionForm");
/* 3512 */                                     frm.setServersite("remote");
/*      */                                   }
/* 3514 */                                   if ((!hideFields) || ((!isDiscoveryComplete) && (hideFields)))
/*      */                                   {
/*      */ 
/* 3517 */                                     out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n\t\t<td id=\"DiscoveryDetailsTD\" valign=\"top\">\n");
/* 3518 */                                     out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link rel=\"stylesheet\" href=\"/images/chosen.css\" />\n");
/* 3519 */                                     String message = (String)request.getAttribute("typemessage");
/*      */                                     
/* 3521 */                                     ManagedApplication mo1 = new ManagedApplication();
/* 3522 */                                     Properties props = com.adventnet.appmanager.util.Constants.getValueForNewMonitor();
/* 3523 */                                     boolean isConfMonitor = false;
/* 3524 */                                     ConfMonitorConfiguration confMonConfig = ConfMonitorConfiguration.getInstance();
/* 3525 */                                     if (message != null)
/*      */                                     {
/* 3527 */                                       out.write("\n    <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\" >\n    <tr>\n      <td><img src=\"/images/icon_message_success.gif\"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"bodytext\">\n        ");
/* 3528 */                                       out.print(message);
/* 3529 */                                       out.write("</span> </td>\n    </tr>\n  </table>\n     ");
/*      */                                     }
/*      */                                     
/*      */ 
/* 3533 */                                     out.write("\n\n\n<table id=\"newResourceTypes\" width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\">\n  <tr>\n\t<td width=\"25%\" class=\"tableheading-monitor-config bodytext label-align addmonitor-label\">&nbsp;");
/* 3534 */                                     out.print(FormatUtil.getString("am.webclient.newresourcetypes.addmonitor.text"));
/* 3535 */                                     out.write("</td>\n    <td class=\"tableheading-monitor-config \" valign=\"middle\">\n");
/* 3536 */                                     if ("UrlSeq".equals(request.getParameter("type"))) {
/* 3537 */                                       DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 3538 */                                       if (frm != null) {
/* 3539 */                                         frm.set("type", "UrlSeq");
/*      */                                       }
/*      */                                     }
/*      */                                     
/* 3543 */                                     if ("UrlMonitor".equals(request.getParameter("type"))) {
/* 3544 */                                       DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 3545 */                                       if (frm != null) {
/* 3546 */                                         frm.set("type", "UrlMonitor");
/*      */                                       }
/*      */                                     }
/*      */                                     
/* 3550 */                                     if ("RBM".equals(request.getParameter("type"))) {
/* 3551 */                                       DynaActionForm frm = (DynaActionForm)request.getAttribute("RbmForm");
/* 3552 */                                       if (frm != null) {
/* 3553 */                                         frm.set("type", "RBM");
/*      */                                       }
/*      */                                     }
/*      */                                     
/*      */ 
/* 3558 */                                     out.write("\n\n    ");
/*      */                                     
/* 3560 */                                     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3561 */                                     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3562 */                                     _jspx_th_c_005fif_005f6.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 3564 */                                     _jspx_th_c_005fif_005f6.setTest("${empty param.wiz && empty param.fromAssociate}");
/* 3565 */                                     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3566 */                                     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                       for (;;) {
/* 3568 */                                         out.write("\n     ");
/*      */                                         
/* 3570 */                                         SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 3571 */                                         _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 3572 */                                         _jspx_th_html_005fselect_005f0.setParent(_jspx_th_c_005fif_005f6);
/*      */                                         
/* 3574 */                                         _jspx_th_html_005fselect_005f0.setProperty("type");
/*      */                                         
/* 3576 */                                         _jspx_th_html_005fselect_005f0.setStyle("background-color:#FDFEF2; font-size:13px;");
/*      */                                         
/* 3578 */                                         _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*      */                                         
/* 3580 */                                         _jspx_th_html_005fselect_005f0.setOnchange("javascript:formReload()");
/* 3581 */                                         int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 3582 */                                         if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 3583 */                                           if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3584 */                                             out = _jspx_page_context.pushBody();
/* 3585 */                                             _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 3586 */                                             _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 3589 */                                             out.write("\n\n      <!-- If you are changing any of the values in this select box then kindly update the corresponding strings in HostDiscoveryHandler.java also-->\n      ");
/*      */                                             
/* 3591 */                                             if ((!com.adventnet.appmanager.util.Constants.isMinimizedversion()) || (com.adventnet.appmanager.util.Constants.getUserType().equals("F")) || (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                                             {
/*      */ 
/*      */ 
/* 3595 */                                               out.write("\n\n\t <optgroup label=\"");
/* 3596 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 3597 */                                               out.write("\">\n                                        \n                                        ");
/*      */                                               
/* 3599 */                                               OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3600 */                                               _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 3601 */                                               _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3603 */                                               _jspx_th_html_005foption_005f0.setValue("AIX");
/* 3604 */                                               int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 3605 */                                               if (_jspx_eval_html_005foption_005f0 != 0) {
/* 3606 */                                                 if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3607 */                                                   out = _jspx_page_context.pushBody();
/* 3608 */                                                   _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 3609 */                                                   _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3612 */                                                   out.print(FormatUtil.getString("AIX"));
/* 3613 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 3614 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3617 */                                                 if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3618 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3621 */                                               if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 3622 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                               }
/*      */                                               
/* 3625 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 3626 */                                               out.write("\n                                        ");
/*      */                                               
/* 3628 */                                               OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3629 */                                               _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 3630 */                                               _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3632 */                                               _jspx_th_html_005foption_005f1.setValue("AS400");
/* 3633 */                                               int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 3634 */                                               if (_jspx_eval_html_005foption_005f1 != 0) {
/* 3635 */                                                 if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3636 */                                                   out = _jspx_page_context.pushBody();
/* 3637 */                                                   _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 3638 */                                                   _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3641 */                                                   out.print(FormatUtil.getString("AS400/iSeries"));
/* 3642 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 3643 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3646 */                                                 if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3647 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3650 */                                               if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 3651 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                               }
/*      */                                               
/* 3654 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 3655 */                                               out.write("\n                                        ");
/*      */                                               
/* 3657 */                                               OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3658 */                                               _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 3659 */                                               _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3661 */                                               _jspx_th_html_005foption_005f2.setValue("FreeBSD");
/* 3662 */                                               int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 3663 */                                               if (_jspx_eval_html_005foption_005f2 != 0) {
/* 3664 */                                                 if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3665 */                                                   out = _jspx_page_context.pushBody();
/* 3666 */                                                   _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 3667 */                                                   _jspx_th_html_005foption_005f2.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3670 */                                                   out.print(FormatUtil.getString("FreeBSD/OpenBSD"));
/* 3671 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 3672 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3675 */                                                 if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3676 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3679 */                                               if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 3680 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                               }
/*      */                                               
/* 3683 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 3684 */                                               out.write("\n                                        ");
/*      */                                               
/* 3686 */                                               OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3687 */                                               _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 3688 */                                               _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3690 */                                               _jspx_th_html_005foption_005f3.setValue("HP-UX");
/* 3691 */                                               int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 3692 */                                               if (_jspx_eval_html_005foption_005f3 != 0) {
/* 3693 */                                                 if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3694 */                                                   out = _jspx_page_context.pushBody();
/* 3695 */                                                   _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 3696 */                                                   _jspx_th_html_005foption_005f3.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3699 */                                                   out.print(FormatUtil.getString("HP-UX  / Tru64"));
/* 3700 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 3701 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3704 */                                                 if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3705 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3708 */                                               if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 3709 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                               }
/*      */                                               
/* 3712 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 3713 */                                               out.write("\n                                        ");
/*      */                                               
/* 3715 */                                               OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3716 */                                               _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 3717 */                                               _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3719 */                                               _jspx_th_html_005foption_005f4.setValue("Linux");
/* 3720 */                                               int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 3721 */                                               if (_jspx_eval_html_005foption_005f4 != 0) {
/* 3722 */                                                 if (_jspx_eval_html_005foption_005f4 != 1) {
/* 3723 */                                                   out = _jspx_page_context.pushBody();
/* 3724 */                                                   _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 3725 */                                                   _jspx_th_html_005foption_005f4.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3728 */                                                   out.print(FormatUtil.getString("Linux"));
/* 3729 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 3730 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3733 */                                                 if (_jspx_eval_html_005foption_005f4 != 1) {
/* 3734 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3737 */                                               if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 3738 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                               }
/*      */                                               
/* 3741 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 3742 */                                               out.write("\n                                        ");
/*      */                                               
/* 3744 */                                               OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3745 */                                               _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 3746 */                                               _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3748 */                                               _jspx_th_html_005foption_005f5.setValue("Mac OS");
/* 3749 */                                               int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 3750 */                                               if (_jspx_eval_html_005foption_005f5 != 0) {
/* 3751 */                                                 if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3752 */                                                   out = _jspx_page_context.pushBody();
/* 3753 */                                                   _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 3754 */                                                   _jspx_th_html_005foption_005f5.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3757 */                                                   out.print(FormatUtil.getString("Mac OS"));
/* 3758 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 3759 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3762 */                                                 if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3763 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3766 */                                               if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 3767 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                               }
/*      */                                               
/* 3770 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 3771 */                                               out.write("\n                                        ");
/*      */                                               
/* 3773 */                                               OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3774 */                                               _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 3775 */                                               _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3777 */                                               _jspx_th_html_005foption_005f6.setValue("Novell");
/* 3778 */                                               int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 3779 */                                               if (_jspx_eval_html_005foption_005f6 != 0) {
/* 3780 */                                                 if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3781 */                                                   out = _jspx_page_context.pushBody();
/* 3782 */                                                   _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 3783 */                                                   _jspx_th_html_005foption_005f6.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3786 */                                                   out.print(FormatUtil.getString("Novell"));
/* 3787 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 3788 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3791 */                                                 if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3792 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3795 */                                               if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 3796 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                               }
/*      */                                               
/* 3799 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 3800 */                                               out.write("\n                                        ");
/*      */                                               
/* 3802 */                                               OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3803 */                                               _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 3804 */                                               _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3806 */                                               _jspx_th_html_005foption_005f7.setValue("Sun Solaris");
/* 3807 */                                               int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 3808 */                                               if (_jspx_eval_html_005foption_005f7 != 0) {
/* 3809 */                                                 if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3810 */                                                   out = _jspx_page_context.pushBody();
/* 3811 */                                                   _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 3812 */                                                   _jspx_th_html_005foption_005f7.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3815 */                                                   out.print(FormatUtil.getString("Sun Solaris"));
/* 3816 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 3817 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3820 */                                                 if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3821 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3824 */                                               if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 3825 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                               }
/*      */                                               
/* 3828 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/* 3829 */                                               out.write("\n                                        ");
/*      */                                               
/* 3831 */                                               OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3832 */                                               _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 3833 */                                               _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3835 */                                               _jspx_th_html_005foption_005f8.setValue("Windows");
/* 3836 */                                               int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 3837 */                                               if (_jspx_eval_html_005foption_005f8 != 0) {
/* 3838 */                                                 if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3839 */                                                   out = _jspx_page_context.pushBody();
/* 3840 */                                                   _jspx_th_html_005foption_005f8.setBodyContent((BodyContent)out);
/* 3841 */                                                   _jspx_th_html_005foption_005f8.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3844 */                                                   out.print(FormatUtil.getString("Windows"));
/* 3845 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f8.doAfterBody();
/* 3846 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3849 */                                                 if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3850 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3853 */                                               if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 3854 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                                               }
/*      */                                               
/* 3857 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8);
/* 3858 */                                               out.write("\n                                        ");
/*      */                                               
/* 3860 */                                               OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3861 */                                               _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 3862 */                                               _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3864 */                                               _jspx_th_html_005foption_005f9.setValue("Windows Cluster");
/* 3865 */                                               int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 3866 */                                               if (_jspx_eval_html_005foption_005f9 != 0) {
/* 3867 */                                                 if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3868 */                                                   out = _jspx_page_context.pushBody();
/* 3869 */                                                   _jspx_th_html_005foption_005f9.setBodyContent((BodyContent)out);
/* 3870 */                                                   _jspx_th_html_005foption_005f9.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3873 */                                                   out.print(FormatUtil.getString("Windows Cluster"));
/* 3874 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f9.doAfterBody();
/* 3875 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3878 */                                                 if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3879 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3882 */                                               if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 3883 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                                               }
/*      */                                               
/* 3886 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9);
/* 3887 */                                               out.write("\n                                        \n\n  ");
/*      */                                               
/* 3889 */                                               ArrayList rows1 = mo1.getRows("select RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH from AM_ManagedResourceType,AM_MONITOR_TYPES where TYPEID=RESOURCETYPEID and RESOURCEGROUP='SYS' and AMCREATED='NO' ORDER BY UPPER(DISPLAYNAME)");
/* 3890 */                                               if ((rows1 != null) && (rows1.size() > 0))
/*      */                                               {
/* 3892 */                                                 for (int i = 0; i < rows1.size(); i++)
/*      */                                                 {
/* 3894 */                                                   ArrayList row = (ArrayList)rows1.get(i);
/* 3895 */                                                   String res = (String)row.get(0);
/* 3896 */                                                   String dname = (String)row.get(1);
/* 3897 */                                                   String values = props.getProperty(res);
/* 3898 */                                                   if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                                                   {
/*      */ 
/* 3901 */                                                     out.write("\n\t\t\t\t");
/*      */                                                     
/* 3903 */                                                     OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3904 */                                                     _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/* 3905 */                                                     _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                     
/* 3907 */                                                     _jspx_th_html_005foption_005f10.setValue(values);
/* 3908 */                                                     int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/* 3909 */                                                     if (_jspx_eval_html_005foption_005f10 != 0) {
/* 3910 */                                                       if (_jspx_eval_html_005foption_005f10 != 1) {
/* 3911 */                                                         out = _jspx_page_context.pushBody();
/* 3912 */                                                         _jspx_th_html_005foption_005f10.setBodyContent((BodyContent)out);
/* 3913 */                                                         _jspx_th_html_005foption_005f10.doInitBody();
/*      */                                                       }
/*      */                                                       for (;;) {
/* 3916 */                                                         out.write(32);
/* 3917 */                                                         out.print(FormatUtil.getString(dname));
/* 3918 */                                                         int evalDoAfterBody = _jspx_th_html_005foption_005f10.doAfterBody();
/* 3919 */                                                         if (evalDoAfterBody != 2)
/*      */                                                           break;
/*      */                                                       }
/* 3922 */                                                       if (_jspx_eval_html_005foption_005f10 != 1) {
/* 3923 */                                                         out = _jspx_page_context.popBody();
/*      */                                                       }
/*      */                                                     }
/* 3926 */                                                     if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/* 3927 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                                                     }
/*      */                                                     
/* 3930 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10);
/* 3931 */                                                     out.write("\n\t\t\t");
/*      */                                                   }
/*      */                                                 }
/*      */                                               }
/*      */                                               
/*      */ 
/* 3937 */                                               String[] categoryLink = { "APP", "ERP", "TM", "SYS", "DBS", "SER", "URL", "MS", "MOM", "CAM", "VIR", "CLD" };
/*      */                                               
/* 3939 */                                               String[] categoryTitle = { "am.webclient.monitorgroupsecond.category.appserver", "am.webclient.monitorgroupsecond.category.erp", "am.webclient.monitorgroupsecond.category.transaction", "am.webclient.monitorgroupsecond.category.servers", "am.webclient.monitorgroupsecond.category.dbserver", "am.webclient.monitorgroupsecond.category.services", "am.webclient.monitorgroupsecond.category.webservices.title", "am.webclient.monitorgroupsecond.category.mailserver", "Middleware/Portal", "am.webclient.monitorgroupsecond.category.custom", "am.webclient.monitorgroupsecond.category.virtualserver", "am.webclient.monitorgroupsecond.category.cloudapps" };
/*      */                                               
/*      */ 
/* 3942 */                                               if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD"))
/*      */                                               {
/*      */ 
/* 3945 */                                                 categoryLink = com.adventnet.appmanager.util.Constants.categoryLink;
/* 3946 */                                                 categoryTitle = com.adventnet.appmanager.util.Constants.categoryTitle;
/*      */                                               }
/* 3948 */                                               for (int c = 0; c < categoryLink.length; c++)
/*      */                                               {
/* 3950 */                                                 ArrayList unSupportedTypes = com.adventnet.appmanager.util.Constants.getUnSupportedAsList();
/* 3951 */                                                 if ((!categoryLink[c].equals("SYS")) && (!categoryLink[c].equals("NWD")) && (!categoryLink[c].equals("SAN")) && (!categoryLink[c].equals("EMO")) && (!categoryLink[c].equals("SCR")) && ((!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")) || (!unSupportedTypes.contains(categoryLink[c]))))
/*      */                                                 {
/*      */ 
/*      */ 
/* 3955 */                                                   StringBuffer queryBuf = new StringBuffer();
/* 3956 */                                                   queryBuf.append("SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='");
/* 3957 */                                                   queryBuf.append(categoryLink[c] + "'");
/* 3958 */                                                   queryBuf.append(" ");
/* 3959 */                                                   queryBuf.append("and RESOURCETYPE in");
/* 3960 */                                                   queryBuf.append(" ");
/* 3961 */                                                   queryBuf.append(com.adventnet.appmanager.util.Constants.resourceTypes);
/* 3962 */                                                   if (categoryLink[c].equals("APP"))
/*      */                                                   {
/* 3964 */                                                     queryBuf.append(" ");
/* 3965 */                                                     queryBuf.append("and DISPLAYNAME NOT IN ('WebLogic Clusters')");
/* 3966 */                                                     queryBuf.append(" ");
/*      */                                                   }
/* 3968 */                                                   else if (categoryLink[c].equals("SER"))
/*      */                                                   {
/* 3970 */                                                     queryBuf.append(" ");
/* 3971 */                                                     queryBuf.append("and RESOURCETYPE<>'RMI'");
/* 3972 */                                                     queryBuf.append(" ");
/*      */                                                   }
/* 3974 */                                                   else if (categoryLink[c].equals("CAM"))
/*      */                                                   {
/* 3976 */                                                     queryBuf.append(" ");
/* 3977 */                                                     queryBuf.append("and RESOURCETYPE != 'directory'");
/* 3978 */                                                     queryBuf.append(" ");
/*      */                                                   }
/* 3980 */                                                   queryBuf.append(" ");
/* 3981 */                                                   queryBuf.append("ORDER BY UPPER(DISPLAYNAME)");
/* 3982 */                                                   ArrayList rows = mo1.getRows(queryBuf.toString());
/* 3983 */                                                   if ((rows != null) && (rows.size() != 0))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/* 3988 */                                                     out.write("\n</optgroup>\t\t\t\t<optgroup label=\"");
/* 3989 */                                                     out.print(FormatUtil.getString(categoryTitle[c]));
/* 3990 */                                                     out.write(34);
/* 3991 */                                                     out.write(62);
/* 3992 */                                                     out.write(10);
/*      */                                                     
/*      */ 
/* 3995 */                                                     for (int i = 0; i < rows.size(); i++)
/*      */                                                     {
/* 3997 */                                                       ArrayList row = (ArrayList)rows.get(i);
/* 3998 */                                                       String res = (String)row.get(0);
/* 3999 */                                                       if (res.equals("file"))
/*      */                                                       {
/* 4001 */                                                         res = "File / Directory Monitor";
/*      */                                                       }
/* 4003 */                                                       String dname = (String)row.get(1);
/* 4004 */                                                       String values = props.getProperty(res);
/* 4005 */                                                       if ((!EnterpriseUtil.isCloudEdition()) || (!unSupportedTypes.contains(values)))
/*      */                                                       {
/*      */ 
/* 4008 */                                                         if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                                                         {
/*      */ 
/* 4011 */                                                           out.write("\n\t\t\t\t \t");
/*      */                                                           
/* 4013 */                                                           OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4014 */                                                           _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/* 4015 */                                                           _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                           
/* 4017 */                                                           _jspx_th_html_005foption_005f11.setValue(values);
/* 4018 */                                                           int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/* 4019 */                                                           if (_jspx_eval_html_005foption_005f11 != 0) {
/* 4020 */                                                             if (_jspx_eval_html_005foption_005f11 != 1) {
/* 4021 */                                                               out = _jspx_page_context.pushBody();
/* 4022 */                                                               _jspx_th_html_005foption_005f11.setBodyContent((BodyContent)out);
/* 4023 */                                                               _jspx_th_html_005foption_005f11.doInitBody();
/*      */                                                             }
/*      */                                                             for (;;) {
/* 4026 */                                                               out.write(32);
/* 4027 */                                                               out.print(FormatUtil.getString(dname));
/* 4028 */                                                               int evalDoAfterBody = _jspx_th_html_005foption_005f11.doAfterBody();
/* 4029 */                                                               if (evalDoAfterBody != 2)
/*      */                                                                 break;
/*      */                                                             }
/* 4032 */                                                             if (_jspx_eval_html_005foption_005f11 != 1) {
/* 4033 */                                                               out = _jspx_page_context.popBody();
/*      */                                                             }
/*      */                                                           }
/* 4036 */                                                           if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/* 4037 */                                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11); return;
/*      */                                                           }
/*      */                                                           
/* 4040 */                                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11);
/* 4041 */                                                           out.write("\n\t\t\t\t");
/*      */                                                         }
/*      */                                                       }
/*      */                                                     }
/*      */                                                     
/* 4046 */                                                     if (categoryLink[c].equals("VIR"))
/*      */                                                     {
/*      */ 
/* 4049 */                                                       out.write("\n\t\t\t\t\t");
/*      */                                                       
/* 4051 */                                                       OptionTag _jspx_th_html_005foption_005f12 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4052 */                                                       _jspx_th_html_005foption_005f12.setPageContext(_jspx_page_context);
/* 4053 */                                                       _jspx_th_html_005foption_005f12.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4055 */                                                       _jspx_th_html_005foption_005f12.setValue("VCenter");
/* 4056 */                                                       int _jspx_eval_html_005foption_005f12 = _jspx_th_html_005foption_005f12.doStartTag();
/* 4057 */                                                       if (_jspx_eval_html_005foption_005f12 != 0) {
/* 4058 */                                                         if (_jspx_eval_html_005foption_005f12 != 1) {
/* 4059 */                                                           out = _jspx_page_context.pushBody();
/* 4060 */                                                           _jspx_th_html_005foption_005f12.setBodyContent((BodyContent)out);
/* 4061 */                                                           _jspx_th_html_005foption_005f12.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4064 */                                                           out.write(32);
/* 4065 */                                                           out.print(FormatUtil.getString("am.webclient.addmonitor.vcenter.name"));
/* 4066 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f12.doAfterBody();
/* 4067 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4070 */                                                         if (_jspx_eval_html_005foption_005f12 != 1) {
/* 4071 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4074 */                                                       if (_jspx_th_html_005foption_005f12.doEndTag() == 5) {
/* 4075 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12); return;
/*      */                                                       }
/*      */                                                       
/* 4078 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12);
/* 4079 */                                                       out.write("\n\t\t\t\t");
/*      */                                                     }
/*      */                                                   }
/*      */                                                 } }
/* 4083 */                                               String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/* 4084 */                                               if (!usertype.equals("F"))
/*      */                                               {
/* 4086 */                                                 if (((!EnterpriseUtil.isIt360MSPEdition()) || (!DBUtil.isSharedProbe())) && (!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                                                 {
/* 4088 */                                                   out.write("\n    </optgroup> <optgroup label=\"");
/* 4089 */                                                   out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 4090 */                                                   out.write("\">\n     ");
/*      */                                                   
/* 4092 */                                                   OptionTag _jspx_th_html_005foption_005f13 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4093 */                                                   _jspx_th_html_005foption_005f13.setPageContext(_jspx_page_context);
/* 4094 */                                                   _jspx_th_html_005foption_005f13.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4096 */                                                   _jspx_th_html_005foption_005f13.setValue("All:1008");
/* 4097 */                                                   int _jspx_eval_html_005foption_005f13 = _jspx_th_html_005foption_005f13.doStartTag();
/* 4098 */                                                   if (_jspx_eval_html_005foption_005f13 != 0) {
/* 4099 */                                                     if (_jspx_eval_html_005foption_005f13 != 1) {
/* 4100 */                                                       out = _jspx_page_context.pushBody();
/* 4101 */                                                       _jspx_th_html_005foption_005f13.setBodyContent((BodyContent)out);
/* 4102 */                                                       _jspx_th_html_005foption_005f13.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4105 */                                                       out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 4106 */                                                       out.write(32);
/* 4107 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f13.doAfterBody();
/* 4108 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4111 */                                                     if (_jspx_eval_html_005foption_005f13 != 1) {
/* 4112 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4115 */                                                   if (_jspx_th_html_005foption_005f13.doEndTag() == 5) {
/* 4116 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13); return;
/*      */                                                   }
/*      */                                                   
/* 4119 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13);
/* 4120 */                                                   out.write("\n\n     ");
/*      */                                                 }
/*      */                                                 
/*      */                                               }
/*      */                                               
/*      */                                             }
/* 4126 */                                             else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("LAMP"))
/*      */                                             {
/*      */ 
/* 4129 */                                               out.write("\n\t\t\t </optgroup>  <optgroup label=\"");
/* 4130 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 4131 */                                               out.write("\">\n\t\t\t   ");
/*      */                                               
/* 4133 */                                               OptionTag _jspx_th_html_005foption_005f14 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4134 */                                               _jspx_th_html_005foption_005f14.setPageContext(_jspx_page_context);
/* 4135 */                                               _jspx_th_html_005foption_005f14.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4137 */                                               _jspx_th_html_005foption_005f14.setValue("SYSTEM:9999");
/* 4138 */                                               int _jspx_eval_html_005foption_005f14 = _jspx_th_html_005foption_005f14.doStartTag();
/* 4139 */                                               if (_jspx_eval_html_005foption_005f14 != 0) {
/* 4140 */                                                 if (_jspx_eval_html_005foption_005f14 != 1) {
/* 4141 */                                                   out = _jspx_page_context.pushBody();
/* 4142 */                                                   _jspx_th_html_005foption_005f14.setBodyContent((BodyContent)out);
/* 4143 */                                                   _jspx_th_html_005foption_005f14.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4146 */                                                   out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4147 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f14.doAfterBody();
/* 4148 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4151 */                                                 if (_jspx_eval_html_005foption_005f14 != 1) {
/* 4152 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4155 */                                               if (_jspx_th_html_005foption_005f14.doEndTag() == 5) {
/* 4156 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14); return;
/*      */                                               }
/*      */                                               
/* 4159 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14);
/* 4160 */                                               out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 4161 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 4162 */                                               out.write("\">\n\t\t\t   ");
/*      */                                               
/* 4164 */                                               OptionTag _jspx_th_html_005foption_005f15 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4165 */                                               _jspx_th_html_005foption_005f15.setPageContext(_jspx_page_context);
/* 4166 */                                               _jspx_th_html_005foption_005f15.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4168 */                                               _jspx_th_html_005foption_005f15.setValue("MYSQLDB:3306");
/* 4169 */                                               int _jspx_eval_html_005foption_005f15 = _jspx_th_html_005foption_005f15.doStartTag();
/* 4170 */                                               if (_jspx_eval_html_005foption_005f15 != 0) {
/* 4171 */                                                 if (_jspx_eval_html_005foption_005f15 != 1) {
/* 4172 */                                                   out = _jspx_page_context.pushBody();
/* 4173 */                                                   _jspx_th_html_005foption_005f15.setBodyContent((BodyContent)out);
/* 4174 */                                                   _jspx_th_html_005foption_005f15.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4177 */                                                   out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 4178 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f15.doAfterBody();
/* 4179 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4182 */                                                 if (_jspx_eval_html_005foption_005f15 != 1) {
/* 4183 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4186 */                                               if (_jspx_th_html_005foption_005f15.doEndTag() == 5) {
/* 4187 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15); return;
/*      */                                               }
/*      */                                               
/* 4190 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15);
/* 4191 */                                               out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 4192 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 4193 */                                               out.write("\">\n\t\t\t   ");
/*      */                                               
/* 4195 */                                               OptionTag _jspx_th_html_005foption_005f16 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4196 */                                               _jspx_th_html_005foption_005f16.setPageContext(_jspx_page_context);
/* 4197 */                                               _jspx_th_html_005foption_005f16.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4199 */                                               _jspx_th_html_005foption_005f16.setValue("JMX1.2-MX4J-RMI:1099");
/* 4200 */                                               int _jspx_eval_html_005foption_005f16 = _jspx_th_html_005foption_005f16.doStartTag();
/* 4201 */                                               if (_jspx_eval_html_005foption_005f16 != 0) {
/* 4202 */                                                 if (_jspx_eval_html_005foption_005f16 != 1) {
/* 4203 */                                                   out = _jspx_page_context.pushBody();
/* 4204 */                                                   _jspx_th_html_005foption_005f16.setBodyContent((BodyContent)out);
/* 4205 */                                                   _jspx_th_html_005foption_005f16.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4208 */                                                   out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 4209 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f16.doAfterBody();
/* 4210 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4213 */                                                 if (_jspx_eval_html_005foption_005f16 != 1) {
/* 4214 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4217 */                                               if (_jspx_th_html_005foption_005f16.doEndTag() == 5) {
/* 4218 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16); return;
/*      */                                               }
/*      */                                               
/* 4221 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16);
/* 4222 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 4224 */                                               OptionTag _jspx_th_html_005foption_005f17 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4225 */                                               _jspx_th_html_005foption_005f17.setPageContext(_jspx_page_context);
/* 4226 */                                               _jspx_th_html_005foption_005f17.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4228 */                                               _jspx_th_html_005foption_005f17.setValue("SERVICE:9090");
/* 4229 */                                               int _jspx_eval_html_005foption_005f17 = _jspx_th_html_005foption_005f17.doStartTag();
/* 4230 */                                               if (_jspx_eval_html_005foption_005f17 != 0) {
/* 4231 */                                                 if (_jspx_eval_html_005foption_005f17 != 1) {
/* 4232 */                                                   out = _jspx_page_context.pushBody();
/* 4233 */                                                   _jspx_th_html_005foption_005f17.setBodyContent((BodyContent)out);
/* 4234 */                                                   _jspx_th_html_005foption_005f17.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4237 */                                                   out.write(32);
/* 4238 */                                                   out.print(FormatUtil.getString("Service Monitoring"));
/* 4239 */                                                   out.write(32);
/* 4240 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f17.doAfterBody();
/* 4241 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4244 */                                                 if (_jspx_eval_html_005foption_005f17 != 1) {
/* 4245 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4248 */                                               if (_jspx_th_html_005foption_005f17.doEndTag() == 5) {
/* 4249 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17); return;
/*      */                                               }
/*      */                                               
/* 4252 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17);
/* 4253 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 4255 */                                               OptionTag _jspx_th_html_005foption_005f18 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4256 */                                               _jspx_th_html_005foption_005f18.setPageContext(_jspx_page_context);
/* 4257 */                                               _jspx_th_html_005foption_005f18.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4259 */                                               _jspx_th_html_005foption_005f18.setValue("RMI:1099");
/* 4260 */                                               int _jspx_eval_html_005foption_005f18 = _jspx_th_html_005foption_005f18.doStartTag();
/* 4261 */                                               if (_jspx_eval_html_005foption_005f18 != 0) {
/* 4262 */                                                 if (_jspx_eval_html_005foption_005f18 != 1) {
/* 4263 */                                                   out = _jspx_page_context.pushBody();
/* 4264 */                                                   _jspx_th_html_005foption_005f18.setBodyContent((BodyContent)out);
/* 4265 */                                                   _jspx_th_html_005foption_005f18.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4268 */                                                   out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 4269 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f18.doAfterBody();
/* 4270 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4273 */                                                 if (_jspx_eval_html_005foption_005f18 != 1) {
/* 4274 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4277 */                                               if (_jspx_th_html_005foption_005f18.doEndTag() == 5) {
/* 4278 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18); return;
/*      */                                               }
/*      */                                               
/* 4281 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18);
/* 4282 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 4284 */                                               OptionTag _jspx_th_html_005foption_005f19 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4285 */                                               _jspx_th_html_005foption_005f19.setPageContext(_jspx_page_context);
/* 4286 */                                               _jspx_th_html_005foption_005f19.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4288 */                                               _jspx_th_html_005foption_005f19.setValue("SNMP:161");
/* 4289 */                                               int _jspx_eval_html_005foption_005f19 = _jspx_th_html_005foption_005f19.doStartTag();
/* 4290 */                                               if (_jspx_eval_html_005foption_005f19 != 0) {
/* 4291 */                                                 if (_jspx_eval_html_005foption_005f19 != 1) {
/* 4292 */                                                   out = _jspx_page_context.pushBody();
/* 4293 */                                                   _jspx_th_html_005foption_005f19.setBodyContent((BodyContent)out);
/* 4294 */                                                   _jspx_th_html_005foption_005f19.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4297 */                                                   out.print(FormatUtil.getString("SNMP"));
/* 4298 */                                                   out.write(" (V1 or V2c)");
/* 4299 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f19.doAfterBody();
/* 4300 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4303 */                                                 if (_jspx_eval_html_005foption_005f19 != 1) {
/* 4304 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4307 */                                               if (_jspx_th_html_005foption_005f19.doEndTag() == 5) {
/* 4308 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19); return;
/*      */                                               }
/*      */                                               
/* 4311 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19);
/* 4312 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 4314 */                                               OptionTag _jspx_th_html_005foption_005f20 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4315 */                                               _jspx_th_html_005foption_005f20.setPageContext(_jspx_page_context);
/* 4316 */                                               _jspx_th_html_005foption_005f20.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4318 */                                               _jspx_th_html_005foption_005f20.setValue("TELNET:23");
/* 4319 */                                               int _jspx_eval_html_005foption_005f20 = _jspx_th_html_005foption_005f20.doStartTag();
/* 4320 */                                               if (_jspx_eval_html_005foption_005f20 != 0) {
/* 4321 */                                                 if (_jspx_eval_html_005foption_005f20 != 1) {
/* 4322 */                                                   out = _jspx_page_context.pushBody();
/* 4323 */                                                   _jspx_th_html_005foption_005f20.setBodyContent((BodyContent)out);
/* 4324 */                                                   _jspx_th_html_005foption_005f20.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4327 */                                                   out.print(FormatUtil.getString("Telnet"));
/* 4328 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f20.doAfterBody();
/* 4329 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4332 */                                                 if (_jspx_eval_html_005foption_005f20 != 1) {
/* 4333 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4336 */                                               if (_jspx_th_html_005foption_005f20.doEndTag() == 5) {
/* 4337 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20); return;
/*      */                                               }
/*      */                                               
/* 4340 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20);
/* 4341 */                                               out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 4342 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 4343 */                                               out.write("\">\n\t\t\t   ");
/*      */                                               
/* 4345 */                                               OptionTag _jspx_th_html_005foption_005f21 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4346 */                                               _jspx_th_html_005foption_005f21.setPageContext(_jspx_page_context);
/* 4347 */                                               _jspx_th_html_005foption_005f21.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4349 */                                               _jspx_th_html_005foption_005f21.setValue("APACHE:80");
/* 4350 */                                               int _jspx_eval_html_005foption_005f21 = _jspx_th_html_005foption_005f21.doStartTag();
/* 4351 */                                               if (_jspx_eval_html_005foption_005f21 != 0) {
/* 4352 */                                                 if (_jspx_eval_html_005foption_005f21 != 1) {
/* 4353 */                                                   out = _jspx_page_context.pushBody();
/* 4354 */                                                   _jspx_th_html_005foption_005f21.setBodyContent((BodyContent)out);
/* 4355 */                                                   _jspx_th_html_005foption_005f21.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4358 */                                                   out.write(32);
/* 4359 */                                                   out.print(FormatUtil.getString("Apache Server"));
/* 4360 */                                                   out.write(32);
/* 4361 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f21.doAfterBody();
/* 4362 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4365 */                                                 if (_jspx_eval_html_005foption_005f21 != 1) {
/* 4366 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4369 */                                               if (_jspx_th_html_005foption_005f21.doEndTag() == 5) {
/* 4370 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21); return;
/*      */                                               }
/*      */                                               
/* 4373 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21);
/* 4374 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 4376 */                                               OptionTag _jspx_th_html_005foption_005f22 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4377 */                                               _jspx_th_html_005foption_005f22.setPageContext(_jspx_page_context);
/* 4378 */                                               _jspx_th_html_005foption_005f22.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4380 */                                               _jspx_th_html_005foption_005f22.setValue("PHP:80");
/* 4381 */                                               int _jspx_eval_html_005foption_005f22 = _jspx_th_html_005foption_005f22.doStartTag();
/* 4382 */                                               if (_jspx_eval_html_005foption_005f22 != 0) {
/* 4383 */                                                 if (_jspx_eval_html_005foption_005f22 != 1) {
/* 4384 */                                                   out = _jspx_page_context.pushBody();
/* 4385 */                                                   _jspx_th_html_005foption_005f22.setBodyContent((BodyContent)out);
/* 4386 */                                                   _jspx_th_html_005foption_005f22.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4389 */                                                   out.print(FormatUtil.getString("PHP"));
/* 4390 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f22.doAfterBody();
/* 4391 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4394 */                                                 if (_jspx_eval_html_005foption_005f22 != 1) {
/* 4395 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4398 */                                               if (_jspx_th_html_005foption_005f22.doEndTag() == 5) {
/* 4399 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22); return;
/*      */                                               }
/*      */                                               
/* 4402 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22);
/* 4403 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 4405 */                                               OptionTag _jspx_th_html_005foption_005f23 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4406 */                                               _jspx_th_html_005foption_005f23.setPageContext(_jspx_page_context);
/* 4407 */                                               _jspx_th_html_005foption_005f23.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4409 */                                               _jspx_th_html_005foption_005f23.setValue("UrlMonitor");
/* 4410 */                                               int _jspx_eval_html_005foption_005f23 = _jspx_th_html_005foption_005f23.doStartTag();
/* 4411 */                                               if (_jspx_eval_html_005foption_005f23 != 0) {
/* 4412 */                                                 if (_jspx_eval_html_005foption_005f23 != 1) {
/* 4413 */                                                   out = _jspx_page_context.pushBody();
/* 4414 */                                                   _jspx_th_html_005foption_005f23.setBodyContent((BodyContent)out);
/* 4415 */                                                   _jspx_th_html_005foption_005f23.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4418 */                                                   out.print(FormatUtil.getString("HTTP-URLs"));
/* 4419 */                                                   out.write(32);
/* 4420 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f23.doAfterBody();
/* 4421 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4424 */                                                 if (_jspx_eval_html_005foption_005f23 != 1) {
/* 4425 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4428 */                                               if (_jspx_th_html_005foption_005f23.doEndTag() == 5) {
/* 4429 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23); return;
/*      */                                               }
/*      */                                               
/* 4432 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23);
/* 4433 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 4435 */                                               OptionTag _jspx_th_html_005foption_005f24 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4436 */                                               _jspx_th_html_005foption_005f24.setPageContext(_jspx_page_context);
/* 4437 */                                               _jspx_th_html_005foption_005f24.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4439 */                                               _jspx_th_html_005foption_005f24.setValue("UrlSeq");
/* 4440 */                                               int _jspx_eval_html_005foption_005f24 = _jspx_th_html_005foption_005f24.doStartTag();
/* 4441 */                                               if (_jspx_eval_html_005foption_005f24 != 0) {
/* 4442 */                                                 if (_jspx_eval_html_005foption_005f24 != 1) {
/* 4443 */                                                   out = _jspx_page_context.pushBody();
/* 4444 */                                                   _jspx_th_html_005foption_005f24.setBodyContent((BodyContent)out);
/* 4445 */                                                   _jspx_th_html_005foption_005f24.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4448 */                                                   out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 4449 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f24.doAfterBody();
/* 4450 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4453 */                                                 if (_jspx_eval_html_005foption_005f24 != 1) {
/* 4454 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4457 */                                               if (_jspx_th_html_005foption_005f24.doEndTag() == 5) {
/* 4458 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24); return;
/*      */                                               }
/*      */                                               
/* 4461 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24);
/* 4462 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 4464 */                                               OptionTag _jspx_th_html_005foption_005f25 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4465 */                                               _jspx_th_html_005foption_005f25.setPageContext(_jspx_page_context);
/* 4466 */                                               _jspx_th_html_005foption_005f25.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4468 */                                               _jspx_th_html_005foption_005f25.setValue("WEB:80");
/* 4469 */                                               int _jspx_eval_html_005foption_005f25 = _jspx_th_html_005foption_005f25.doStartTag();
/* 4470 */                                               if (_jspx_eval_html_005foption_005f25 != 0) {
/* 4471 */                                                 if (_jspx_eval_html_005foption_005f25 != 1) {
/* 4472 */                                                   out = _jspx_page_context.pushBody();
/* 4473 */                                                   _jspx_th_html_005foption_005f25.setBodyContent((BodyContent)out);
/* 4474 */                                                   _jspx_th_html_005foption_005f25.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4477 */                                                   out.write(32);
/* 4478 */                                                   out.print(FormatUtil.getString("Web Server"));
/* 4479 */                                                   out.write(32);
/* 4480 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f25.doAfterBody();
/* 4481 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4484 */                                                 if (_jspx_eval_html_005foption_005f25 != 1) {
/* 4485 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4488 */                                               if (_jspx_th_html_005foption_005f25.doEndTag() == 5) {
/* 4489 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25); return;
/*      */                                               }
/*      */                                               
/* 4492 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25);
/* 4493 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 4495 */                                               OptionTag _jspx_th_html_005foption_005f26 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4496 */                                               _jspx_th_html_005foption_005f26.setPageContext(_jspx_page_context);
/* 4497 */                                               _jspx_th_html_005foption_005f26.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4499 */                                               _jspx_th_html_005foption_005f26.setValue("Web Service");
/* 4500 */                                               int _jspx_eval_html_005foption_005f26 = _jspx_th_html_005foption_005f26.doStartTag();
/* 4501 */                                               if (_jspx_eval_html_005foption_005f26 != 0) {
/* 4502 */                                                 if (_jspx_eval_html_005foption_005f26 != 1) {
/* 4503 */                                                   out = _jspx_page_context.pushBody();
/* 4504 */                                                   _jspx_th_html_005foption_005f26.setBodyContent((BodyContent)out);
/* 4505 */                                                   _jspx_th_html_005foption_005f26.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4508 */                                                   out.write(32);
/* 4509 */                                                   out.print(FormatUtil.getString("Web Service"));
/* 4510 */                                                   out.write(32);
/* 4511 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f26.doAfterBody();
/* 4512 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4515 */                                                 if (_jspx_eval_html_005foption_005f26 != 1) {
/* 4516 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4519 */                                               if (_jspx_th_html_005foption_005f26.doEndTag() == 5) {
/* 4520 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26); return;
/*      */                                               }
/*      */                                               
/* 4523 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26);
/* 4524 */                                               out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 4525 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 4526 */                                               out.write("\">\n\t\t\t   ");
/*      */                                               
/* 4528 */                                               OptionTag _jspx_th_html_005foption_005f27 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4529 */                                               _jspx_th_html_005foption_005f27.setPageContext(_jspx_page_context);
/* 4530 */                                               _jspx_th_html_005foption_005f27.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4532 */                                               _jspx_th_html_005foption_005f27.setValue("MAIL:25");
/* 4533 */                                               int _jspx_eval_html_005foption_005f27 = _jspx_th_html_005foption_005f27.doStartTag();
/* 4534 */                                               if (_jspx_eval_html_005foption_005f27 != 0) {
/* 4535 */                                                 if (_jspx_eval_html_005foption_005f27 != 1) {
/* 4536 */                                                   out = _jspx_page_context.pushBody();
/* 4537 */                                                   _jspx_th_html_005foption_005f27.setBodyContent((BodyContent)out);
/* 4538 */                                                   _jspx_th_html_005foption_005f27.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4541 */                                                   out.print(FormatUtil.getString("Mail Server"));
/* 4542 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f27.doAfterBody();
/* 4543 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4546 */                                                 if (_jspx_eval_html_005foption_005f27 != 1) {
/* 4547 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4550 */                                               if (_jspx_th_html_005foption_005f27.doEndTag() == 5) {
/* 4551 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27); return;
/*      */                                               }
/*      */                                               
/* 4554 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27);
/* 4555 */                                               out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 4556 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 4557 */                                               out.write("\">\n\t\t\t   ");
/*      */                                               
/* 4559 */                                               OptionTag _jspx_th_html_005foption_005f28 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4560 */                                               _jspx_th_html_005foption_005f28.setPageContext(_jspx_page_context);
/* 4561 */                                               _jspx_th_html_005foption_005f28.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4563 */                                               _jspx_th_html_005foption_005f28.setValue("Custom-Application");
/* 4564 */                                               int _jspx_eval_html_005foption_005f28 = _jspx_th_html_005foption_005f28.doStartTag();
/* 4565 */                                               if (_jspx_eval_html_005foption_005f28 != 0) {
/* 4566 */                                                 if (_jspx_eval_html_005foption_005f28 != 1) {
/* 4567 */                                                   out = _jspx_page_context.pushBody();
/* 4568 */                                                   _jspx_th_html_005foption_005f28.setBodyContent((BodyContent)out);
/* 4569 */                                                   _jspx_th_html_005foption_005f28.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4572 */                                                   out.write(32);
/* 4573 */                                                   out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 4574 */                                                   out.write(32);
/* 4575 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f28.doAfterBody();
/* 4576 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4579 */                                                 if (_jspx_eval_html_005foption_005f28 != 1) {
/* 4580 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4583 */                                               if (_jspx_th_html_005foption_005f28.doEndTag() == 5) {
/* 4584 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28); return;
/*      */                                               }
/*      */                                               
/* 4587 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28);
/* 4588 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 4590 */                                               OptionTag _jspx_th_html_005foption_005f29 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4591 */                                               _jspx_th_html_005foption_005f29.setPageContext(_jspx_page_context);
/* 4592 */                                               _jspx_th_html_005foption_005f29.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4594 */                                               _jspx_th_html_005foption_005f29.setValue("Script Monitor");
/* 4595 */                                               int _jspx_eval_html_005foption_005f29 = _jspx_th_html_005foption_005f29.doStartTag();
/* 4596 */                                               if (_jspx_eval_html_005foption_005f29 != 0) {
/* 4597 */                                                 if (_jspx_eval_html_005foption_005f29 != 1) {
/* 4598 */                                                   out = _jspx_page_context.pushBody();
/* 4599 */                                                   _jspx_th_html_005foption_005f29.setBodyContent((BodyContent)out);
/* 4600 */                                                   _jspx_th_html_005foption_005f29.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4603 */                                                   out.print(FormatUtil.getString("Script Monitor"));
/* 4604 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f29.doAfterBody();
/* 4605 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4608 */                                                 if (_jspx_eval_html_005foption_005f29 != 1) {
/* 4609 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4612 */                                               if (_jspx_th_html_005foption_005f29.doEndTag() == 5) {
/* 4613 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29); return;
/*      */                                               }
/*      */                                               
/* 4616 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29);
/* 4617 */                                               out.write("\n\n    ");
/*      */ 
/*      */                                             }
/* 4620 */                                             else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("J2EE"))
/*      */                                             {
/*      */ 
/* 4623 */                                               out.write("\n        ");
/*      */                                               
/* 4625 */                                               OptionTag _jspx_th_html_005foption_005f30 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4626 */                                               _jspx_th_html_005foption_005f30.setPageContext(_jspx_page_context);
/* 4627 */                                               _jspx_th_html_005foption_005f30.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4629 */                                               _jspx_th_html_005foption_005f30.setValue("SYSTEM:9999");
/* 4630 */                                               int _jspx_eval_html_005foption_005f30 = _jspx_th_html_005foption_005f30.doStartTag();
/* 4631 */                                               if (_jspx_eval_html_005foption_005f30 != 0) {
/* 4632 */                                                 if (_jspx_eval_html_005foption_005f30 != 1) {
/* 4633 */                                                   out = _jspx_page_context.pushBody();
/* 4634 */                                                   _jspx_th_html_005foption_005f30.setBodyContent((BodyContent)out);
/* 4635 */                                                   _jspx_th_html_005foption_005f30.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4638 */                                                   out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4639 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f30.doAfterBody();
/* 4640 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4643 */                                                 if (_jspx_eval_html_005foption_005f30 != 1) {
/* 4644 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4647 */                                               if (_jspx_th_html_005foption_005f30.doEndTag() == 5) {
/* 4648 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30); return;
/*      */                                               }
/*      */                                               
/* 4651 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30);
/* 4652 */                                               out.write("\n       ");
/*      */                                               
/* 4654 */                                               OptionTag _jspx_th_html_005foption_005f31 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4655 */                                               _jspx_th_html_005foption_005f31.setPageContext(_jspx_page_context);
/* 4656 */                                               _jspx_th_html_005foption_005f31.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4658 */                                               _jspx_th_html_005foption_005f31.setValue("JBoss:8080");
/* 4659 */                                               int _jspx_eval_html_005foption_005f31 = _jspx_th_html_005foption_005f31.doStartTag();
/* 4660 */                                               if (_jspx_eval_html_005foption_005f31 != 0) {
/* 4661 */                                                 if (_jspx_eval_html_005foption_005f31 != 1) {
/* 4662 */                                                   out = _jspx_page_context.pushBody();
/* 4663 */                                                   _jspx_th_html_005foption_005f31.setBodyContent((BodyContent)out);
/* 4664 */                                                   _jspx_th_html_005foption_005f31.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4667 */                                                   out.write(32);
/* 4668 */                                                   out.print(FormatUtil.getString("JBoss Server"));
/* 4669 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f31.doAfterBody();
/* 4670 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4673 */                                                 if (_jspx_eval_html_005foption_005f31 != 1) {
/* 4674 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4677 */                                               if (_jspx_th_html_005foption_005f31.doEndTag() == 5) {
/* 4678 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31); return;
/*      */                                               }
/*      */                                               
/* 4681 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31);
/* 4682 */                                               out.write("\n      ");
/*      */                                               
/* 4684 */                                               OptionTag _jspx_th_html_005foption_005f32 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4685 */                                               _jspx_th_html_005foption_005f32.setPageContext(_jspx_page_context);
/* 4686 */                                               _jspx_th_html_005foption_005f32.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4688 */                                               _jspx_th_html_005foption_005f32.setValue("Tomcat:8080");
/* 4689 */                                               int _jspx_eval_html_005foption_005f32 = _jspx_th_html_005foption_005f32.doStartTag();
/* 4690 */                                               if (_jspx_eval_html_005foption_005f32 != 0) {
/* 4691 */                                                 if (_jspx_eval_html_005foption_005f32 != 1) {
/* 4692 */                                                   out = _jspx_page_context.pushBody();
/* 4693 */                                                   _jspx_th_html_005foption_005f32.setBodyContent((BodyContent)out);
/* 4694 */                                                   _jspx_th_html_005foption_005f32.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4697 */                                                   out.print(FormatUtil.getString("Tomcat Server"));
/* 4698 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f32.doAfterBody();
/* 4699 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4702 */                                                 if (_jspx_eval_html_005foption_005f32 != 1) {
/* 4703 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4706 */                                               if (_jspx_th_html_005foption_005f32.doEndTag() == 5) {
/* 4707 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32); return;
/*      */                                               }
/*      */                                               
/* 4710 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32);
/* 4711 */                                               out.write("\n       ");
/*      */                                               
/* 4713 */                                               OptionTag _jspx_th_html_005foption_005f33 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4714 */                                               _jspx_th_html_005foption_005f33.setPageContext(_jspx_page_context);
/* 4715 */                                               _jspx_th_html_005foption_005f33.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4717 */                                               _jspx_th_html_005foption_005f33.setValue("WEBLOGIC:7001");
/* 4718 */                                               int _jspx_eval_html_005foption_005f33 = _jspx_th_html_005foption_005f33.doStartTag();
/* 4719 */                                               if (_jspx_eval_html_005foption_005f33 != 0) {
/* 4720 */                                                 if (_jspx_eval_html_005foption_005f33 != 1) {
/* 4721 */                                                   out = _jspx_page_context.pushBody();
/* 4722 */                                                   _jspx_th_html_005foption_005f33.setBodyContent((BodyContent)out);
/* 4723 */                                                   _jspx_th_html_005foption_005f33.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4726 */                                                   out.write(32);
/* 4727 */                                                   out.print(FormatUtil.getString("WebLogic Server"));
/* 4728 */                                                   out.write(32);
/* 4729 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f33.doAfterBody();
/* 4730 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4733 */                                                 if (_jspx_eval_html_005foption_005f33 != 1) {
/* 4734 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4737 */                                               if (_jspx_th_html_005foption_005f33.doEndTag() == 5) {
/* 4738 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33); return;
/*      */                                               }
/*      */                                               
/* 4741 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33);
/* 4742 */                                               out.write("\n      ");
/*      */                                               
/* 4744 */                                               OptionTag _jspx_th_html_005foption_005f34 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4745 */                                               _jspx_th_html_005foption_005f34.setPageContext(_jspx_page_context);
/* 4746 */                                               _jspx_th_html_005foption_005f34.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4748 */                                               _jspx_th_html_005foption_005f34.setValue("WEBSPHERE:9080");
/* 4749 */                                               int _jspx_eval_html_005foption_005f34 = _jspx_th_html_005foption_005f34.doStartTag();
/* 4750 */                                               if (_jspx_eval_html_005foption_005f34 != 0) {
/* 4751 */                                                 if (_jspx_eval_html_005foption_005f34 != 1) {
/* 4752 */                                                   out = _jspx_page_context.pushBody();
/* 4753 */                                                   _jspx_th_html_005foption_005f34.setBodyContent((BodyContent)out);
/* 4754 */                                                   _jspx_th_html_005foption_005f34.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4757 */                                                   out.write(32);
/* 4758 */                                                   out.print(FormatUtil.getString("WebSphere Server"));
/* 4759 */                                                   out.write(32);
/* 4760 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f34.doAfterBody();
/* 4761 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4764 */                                                 if (_jspx_eval_html_005foption_005f34 != 1) {
/* 4765 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4768 */                                               if (_jspx_th_html_005foption_005f34.doEndTag() == 5) {
/* 4769 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34); return;
/*      */                                               }
/*      */                                               
/* 4772 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34);
/* 4773 */                                               out.write("\n      ");
/*      */                                               
/* 4775 */                                               OptionTag _jspx_th_html_005foption_005f35 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4776 */                                               _jspx_th_html_005foption_005f35.setPageContext(_jspx_page_context);
/* 4777 */                                               _jspx_th_html_005foption_005f35.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4779 */                                               _jspx_th_html_005foption_005f35.setValue("WTA:55555");
/* 4780 */                                               int _jspx_eval_html_005foption_005f35 = _jspx_th_html_005foption_005f35.doStartTag();
/* 4781 */                                               if (_jspx_eval_html_005foption_005f35 != 0) {
/* 4782 */                                                 if (_jspx_eval_html_005foption_005f35 != 1) {
/* 4783 */                                                   out = _jspx_page_context.pushBody();
/* 4784 */                                                   _jspx_th_html_005foption_005f35.setBodyContent((BodyContent)out);
/* 4785 */                                                   _jspx_th_html_005foption_005f35.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4788 */                                                   out.print(FormatUtil.getString("Web Transactions"));
/* 4789 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f35.doAfterBody();
/* 4790 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4793 */                                                 if (_jspx_eval_html_005foption_005f35 != 1) {
/* 4794 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4797 */                                               if (_jspx_th_html_005foption_005f35.doEndTag() == 5) {
/* 4798 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35); return;
/*      */                                               }
/*      */                                               
/* 4801 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35);
/* 4802 */                                               out.write("\n      ");
/*      */                                               
/* 4804 */                                               OptionTag _jspx_th_html_005foption_005f36 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4805 */                                               _jspx_th_html_005foption_005f36.setPageContext(_jspx_page_context);
/* 4806 */                                               _jspx_th_html_005foption_005f36.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4808 */                                               _jspx_th_html_005foption_005f36.setValue("MAIL:25");
/* 4809 */                                               int _jspx_eval_html_005foption_005f36 = _jspx_th_html_005foption_005f36.doStartTag();
/* 4810 */                                               if (_jspx_eval_html_005foption_005f36 != 0) {
/* 4811 */                                                 if (_jspx_eval_html_005foption_005f36 != 1) {
/* 4812 */                                                   out = _jspx_page_context.pushBody();
/* 4813 */                                                   _jspx_th_html_005foption_005f36.setBodyContent((BodyContent)out);
/* 4814 */                                                   _jspx_th_html_005foption_005f36.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4817 */                                                   out.print(FormatUtil.getString("Mail Server"));
/* 4818 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f36.doAfterBody();
/* 4819 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4822 */                                                 if (_jspx_eval_html_005foption_005f36 != 1) {
/* 4823 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4826 */                                               if (_jspx_th_html_005foption_005f36.doEndTag() == 5) {
/* 4827 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36); return;
/*      */                                               }
/*      */                                               
/* 4830 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36);
/* 4831 */                                               out.write("\n      ");
/*      */                                               
/* 4833 */                                               OptionTag _jspx_th_html_005foption_005f37 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4834 */                                               _jspx_th_html_005foption_005f37.setPageContext(_jspx_page_context);
/* 4835 */                                               _jspx_th_html_005foption_005f37.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4837 */                                               _jspx_th_html_005foption_005f37.setValue("JMX1.2-MX4J-RMI:1099");
/* 4838 */                                               int _jspx_eval_html_005foption_005f37 = _jspx_th_html_005foption_005f37.doStartTag();
/* 4839 */                                               if (_jspx_eval_html_005foption_005f37 != 0) {
/* 4840 */                                                 if (_jspx_eval_html_005foption_005f37 != 1) {
/* 4841 */                                                   out = _jspx_page_context.pushBody();
/* 4842 */                                                   _jspx_th_html_005foption_005f37.setBodyContent((BodyContent)out);
/* 4843 */                                                   _jspx_th_html_005foption_005f37.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4846 */                                                   out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 4847 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f37.doAfterBody();
/* 4848 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4851 */                                                 if (_jspx_eval_html_005foption_005f37 != 1) {
/* 4852 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4855 */                                               if (_jspx_th_html_005foption_005f37.doEndTag() == 5) {
/* 4856 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37); return;
/*      */                                               }
/*      */                                               
/* 4859 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37);
/* 4860 */                                               out.write("\n      ");
/*      */                                               
/* 4862 */                                               OptionTag _jspx_th_html_005foption_005f38 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4863 */                                               _jspx_th_html_005foption_005f38.setPageContext(_jspx_page_context);
/* 4864 */                                               _jspx_th_html_005foption_005f38.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4866 */                                               _jspx_th_html_005foption_005f38.setValue("SERVICE:9090");
/* 4867 */                                               int _jspx_eval_html_005foption_005f38 = _jspx_th_html_005foption_005f38.doStartTag();
/* 4868 */                                               if (_jspx_eval_html_005foption_005f38 != 0) {
/* 4869 */                                                 if (_jspx_eval_html_005foption_005f38 != 1) {
/* 4870 */                                                   out = _jspx_page_context.pushBody();
/* 4871 */                                                   _jspx_th_html_005foption_005f38.setBodyContent((BodyContent)out);
/* 4872 */                                                   _jspx_th_html_005foption_005f38.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4875 */                                                   out.write(32);
/* 4876 */                                                   out.print(FormatUtil.getString("Service Monitoring"));
/* 4877 */                                                   out.write(32);
/* 4878 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f38.doAfterBody();
/* 4879 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4882 */                                                 if (_jspx_eval_html_005foption_005f38 != 1) {
/* 4883 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4886 */                                               if (_jspx_th_html_005foption_005f38.doEndTag() == 5) {
/* 4887 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38); return;
/*      */                                               }
/*      */                                               
/* 4890 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38);
/* 4891 */                                               out.write("\n      ");
/*      */                                               
/* 4893 */                                               OptionTag _jspx_th_html_005foption_005f39 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4894 */                                               _jspx_th_html_005foption_005f39.setPageContext(_jspx_page_context);
/* 4895 */                                               _jspx_th_html_005foption_005f39.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4897 */                                               _jspx_th_html_005foption_005f39.setValue("RMI:1099");
/* 4898 */                                               int _jspx_eval_html_005foption_005f39 = _jspx_th_html_005foption_005f39.doStartTag();
/* 4899 */                                               if (_jspx_eval_html_005foption_005f39 != 0) {
/* 4900 */                                                 if (_jspx_eval_html_005foption_005f39 != 1) {
/* 4901 */                                                   out = _jspx_page_context.pushBody();
/* 4902 */                                                   _jspx_th_html_005foption_005f39.setBodyContent((BodyContent)out);
/* 4903 */                                                   _jspx_th_html_005foption_005f39.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4906 */                                                   out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 4907 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f39.doAfterBody();
/* 4908 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4911 */                                                 if (_jspx_eval_html_005foption_005f39 != 1) {
/* 4912 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4915 */                                               if (_jspx_th_html_005foption_005f39.doEndTag() == 5) {
/* 4916 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39); return;
/*      */                                               }
/*      */                                               
/* 4919 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39);
/* 4920 */                                               out.write("\n      ");
/*      */                                               
/* 4922 */                                               OptionTag _jspx_th_html_005foption_005f40 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4923 */                                               _jspx_th_html_005foption_005f40.setPageContext(_jspx_page_context);
/* 4924 */                                               _jspx_th_html_005foption_005f40.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4926 */                                               _jspx_th_html_005foption_005f40.setValue("SNMP:161");
/* 4927 */                                               int _jspx_eval_html_005foption_005f40 = _jspx_th_html_005foption_005f40.doStartTag();
/* 4928 */                                               if (_jspx_eval_html_005foption_005f40 != 0) {
/* 4929 */                                                 if (_jspx_eval_html_005foption_005f40 != 1) {
/* 4930 */                                                   out = _jspx_page_context.pushBody();
/* 4931 */                                                   _jspx_th_html_005foption_005f40.setBodyContent((BodyContent)out);
/* 4932 */                                                   _jspx_th_html_005foption_005f40.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4935 */                                                   out.print(FormatUtil.getString("SNMP"));
/* 4936 */                                                   out.write(" (V1 or V2c)");
/* 4937 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f40.doAfterBody();
/* 4938 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4941 */                                                 if (_jspx_eval_html_005foption_005f40 != 1) {
/* 4942 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4945 */                                               if (_jspx_th_html_005foption_005f40.doEndTag() == 5) {
/* 4946 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40); return;
/*      */                                               }
/*      */                                               
/* 4949 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40);
/* 4950 */                                               out.write("\n      ");
/*      */                                               
/* 4952 */                                               OptionTag _jspx_th_html_005foption_005f41 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4953 */                                               _jspx_th_html_005foption_005f41.setPageContext(_jspx_page_context);
/* 4954 */                                               _jspx_th_html_005foption_005f41.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4956 */                                               _jspx_th_html_005foption_005f41.setValue("Custom-Application");
/* 4957 */                                               int _jspx_eval_html_005foption_005f41 = _jspx_th_html_005foption_005f41.doStartTag();
/* 4958 */                                               if (_jspx_eval_html_005foption_005f41 != 0) {
/* 4959 */                                                 if (_jspx_eval_html_005foption_005f41 != 1) {
/* 4960 */                                                   out = _jspx_page_context.pushBody();
/* 4961 */                                                   _jspx_th_html_005foption_005f41.setBodyContent((BodyContent)out);
/* 4962 */                                                   _jspx_th_html_005foption_005f41.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4965 */                                                   out.write(32);
/* 4966 */                                                   out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 4967 */                                                   out.write(32);
/* 4968 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f41.doAfterBody();
/* 4969 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4972 */                                                 if (_jspx_eval_html_005foption_005f41 != 1) {
/* 4973 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4976 */                                               if (_jspx_th_html_005foption_005f41.doEndTag() == 5) {
/* 4977 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41); return;
/*      */                                               }
/*      */                                               
/* 4980 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41);
/* 4981 */                                               out.write("\n      ");
/*      */                                               
/* 4983 */                                               OptionTag _jspx_th_html_005foption_005f42 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4984 */                                               _jspx_th_html_005foption_005f42.setPageContext(_jspx_page_context);
/* 4985 */                                               _jspx_th_html_005foption_005f42.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4987 */                                               _jspx_th_html_005foption_005f42.setValue("Script Monitor");
/* 4988 */                                               int _jspx_eval_html_005foption_005f42 = _jspx_th_html_005foption_005f42.doStartTag();
/* 4989 */                                               if (_jspx_eval_html_005foption_005f42 != 0) {
/* 4990 */                                                 if (_jspx_eval_html_005foption_005f42 != 1) {
/* 4991 */                                                   out = _jspx_page_context.pushBody();
/* 4992 */                                                   _jspx_th_html_005foption_005f42.setBodyContent((BodyContent)out);
/* 4993 */                                                   _jspx_th_html_005foption_005f42.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4996 */                                                   out.print(FormatUtil.getString("Script Monitor"));
/* 4997 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f42.doAfterBody();
/* 4998 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5001 */                                                 if (_jspx_eval_html_005foption_005f42 != 1) {
/* 5002 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5005 */                                               if (_jspx_th_html_005foption_005f42.doEndTag() == 5) {
/* 5006 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42); return;
/*      */                                               }
/*      */                                               
/* 5009 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42);
/* 5010 */                                               out.write("\n       ");
/*      */ 
/*      */                                             }
/* 5013 */                                             else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("WINDOWS"))
/*      */                                             {
/*      */ 
/* 5016 */                                               out.write("\n        ");
/*      */                                               
/* 5018 */                                               OptionTag _jspx_th_html_005foption_005f43 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5019 */                                               _jspx_th_html_005foption_005f43.setPageContext(_jspx_page_context);
/* 5020 */                                               _jspx_th_html_005foption_005f43.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 5022 */                                               _jspx_th_html_005foption_005f43.setValue("SYSTEM:9999");
/* 5023 */                                               int _jspx_eval_html_005foption_005f43 = _jspx_th_html_005foption_005f43.doStartTag();
/* 5024 */                                               if (_jspx_eval_html_005foption_005f43 != 0) {
/* 5025 */                                                 if (_jspx_eval_html_005foption_005f43 != 1) {
/* 5026 */                                                   out = _jspx_page_context.pushBody();
/* 5027 */                                                   _jspx_th_html_005foption_005f43.setBodyContent((BodyContent)out);
/* 5028 */                                                   _jspx_th_html_005foption_005f43.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5031 */                                                   out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 5032 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f43.doAfterBody();
/* 5033 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5036 */                                                 if (_jspx_eval_html_005foption_005f43 != 1) {
/* 5037 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5040 */                                               if (_jspx_th_html_005foption_005f43.doEndTag() == 5) {
/* 5041 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43); return;
/*      */                                               }
/*      */                                               
/* 5044 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43);
/* 5045 */                                               out.write("\n       ");
/*      */                                               
/* 5047 */                                               OptionTag _jspx_th_html_005foption_005f44 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5048 */                                               _jspx_th_html_005foption_005f44.setPageContext(_jspx_page_context);
/* 5049 */                                               _jspx_th_html_005foption_005f44.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 5051 */                                               _jspx_th_html_005foption_005f44.setValue(".Net:9080");
/* 5052 */                                               int _jspx_eval_html_005foption_005f44 = _jspx_th_html_005foption_005f44.doStartTag();
/* 5053 */                                               if (_jspx_eval_html_005foption_005f44 != 0) {
/* 5054 */                                                 if (_jspx_eval_html_005foption_005f44 != 1) {
/* 5055 */                                                   out = _jspx_page_context.pushBody();
/* 5056 */                                                   _jspx_th_html_005foption_005f44.setBodyContent((BodyContent)out);
/* 5057 */                                                   _jspx_th_html_005foption_005f44.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5060 */                                                   out.print(FormatUtil.getString("Microsoft .NET"));
/* 5061 */                                                   out.write(32);
/* 5062 */                                                   out.write(32);
/* 5063 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f44.doAfterBody();
/* 5064 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5067 */                                                 if (_jspx_eval_html_005foption_005f44 != 1) {
/* 5068 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5071 */                                               if (_jspx_th_html_005foption_005f44.doEndTag() == 5) {
/* 5072 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44); return;
/*      */                                               }
/*      */                                               
/* 5075 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44);
/* 5076 */                                               out.write("\n      ");
/*      */                                               
/* 5078 */                                               OptionTag _jspx_th_html_005foption_005f45 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5079 */                                               _jspx_th_html_005foption_005f45.setPageContext(_jspx_page_context);
/* 5080 */                                               _jspx_th_html_005foption_005f45.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 5082 */                                               _jspx_th_html_005foption_005f45.setValue("MSSQLDB:1433");
/* 5083 */                                               int _jspx_eval_html_005foption_005f45 = _jspx_th_html_005foption_005f45.doStartTag();
/* 5084 */                                               if (_jspx_eval_html_005foption_005f45 != 0) {
/* 5085 */                                                 if (_jspx_eval_html_005foption_005f45 != 1) {
/* 5086 */                                                   out = _jspx_page_context.pushBody();
/* 5087 */                                                   _jspx_th_html_005foption_005f45.setBodyContent((BodyContent)out);
/* 5088 */                                                   _jspx_th_html_005foption_005f45.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5091 */                                                   out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 5092 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f45.doAfterBody();
/* 5093 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5096 */                                                 if (_jspx_eval_html_005foption_005f45 != 1) {
/* 5097 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5100 */                                               if (_jspx_th_html_005foption_005f45.doEndTag() == 5) {
/* 5101 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45); return;
/*      */                                               }
/*      */                                               
/* 5104 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45);
/* 5105 */                                               out.write("\n      ");
/*      */                                               
/* 5107 */                                               OptionTag _jspx_th_html_005foption_005f46 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5108 */                                               _jspx_th_html_005foption_005f46.setPageContext(_jspx_page_context);
/* 5109 */                                               _jspx_th_html_005foption_005f46.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 5111 */                                               _jspx_th_html_005foption_005f46.setValue("Exchange:25");
/* 5112 */                                               int _jspx_eval_html_005foption_005f46 = _jspx_th_html_005foption_005f46.doStartTag();
/* 5113 */                                               if (_jspx_eval_html_005foption_005f46 != 0) {
/* 5114 */                                                 if (_jspx_eval_html_005foption_005f46 != 1) {
/* 5115 */                                                   out = _jspx_page_context.pushBody();
/* 5116 */                                                   _jspx_th_html_005foption_005f46.setBodyContent((BodyContent)out);
/* 5117 */                                                   _jspx_th_html_005foption_005f46.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5120 */                                                   out.print(FormatUtil.getString("Exchange Server"));
/* 5121 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f46.doAfterBody();
/* 5122 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5125 */                                                 if (_jspx_eval_html_005foption_005f46 != 1) {
/* 5126 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5129 */                                               if (_jspx_th_html_005foption_005f46.doEndTag() == 5) {
/* 5130 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46); return;
/*      */                                               }
/*      */                                               
/* 5133 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46);
/* 5134 */                                               out.write("\n\t  ");
/*      */                                               
/* 5136 */                                               OptionTag _jspx_th_html_005foption_005f47 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5137 */                                               _jspx_th_html_005foption_005f47.setPageContext(_jspx_page_context);
/* 5138 */                                               _jspx_th_html_005foption_005f47.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 5140 */                                               _jspx_th_html_005foption_005f47.setValue("IIS:80");
/* 5141 */                                               int _jspx_eval_html_005foption_005f47 = _jspx_th_html_005foption_005f47.doStartTag();
/* 5142 */                                               if (_jspx_eval_html_005foption_005f47 != 0) {
/* 5143 */                                                 if (_jspx_eval_html_005foption_005f47 != 1) {
/* 5144 */                                                   out = _jspx_page_context.pushBody();
/* 5145 */                                                   _jspx_th_html_005foption_005f47.setBodyContent((BodyContent)out);
/* 5146 */                                                   _jspx_th_html_005foption_005f47.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5149 */                                                   out.write(32);
/* 5150 */                                                   out.print(FormatUtil.getString("IIS Server"));
/* 5151 */                                                   out.write(32);
/* 5152 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f47.doAfterBody();
/* 5153 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5156 */                                                 if (_jspx_eval_html_005foption_005f47 != 1) {
/* 5157 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5160 */                                               if (_jspx_th_html_005foption_005f47.doEndTag() == 5) {
/* 5161 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47); return;
/*      */                                               }
/*      */                                               
/* 5164 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47);
/* 5165 */                                               out.write("\n      ");
/*      */                                               
/* 5167 */                                               OptionTag _jspx_th_html_005foption_005f48 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5168 */                                               _jspx_th_html_005foption_005f48.setPageContext(_jspx_page_context);
/* 5169 */                                               _jspx_th_html_005foption_005f48.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 5171 */                                               _jspx_th_html_005foption_005f48.setValue("SERVICE:9090");
/* 5172 */                                               int _jspx_eval_html_005foption_005f48 = _jspx_th_html_005foption_005f48.doStartTag();
/* 5173 */                                               if (_jspx_eval_html_005foption_005f48 != 0) {
/* 5174 */                                                 if (_jspx_eval_html_005foption_005f48 != 1) {
/* 5175 */                                                   out = _jspx_page_context.pushBody();
/* 5176 */                                                   _jspx_th_html_005foption_005f48.setBodyContent((BodyContent)out);
/* 5177 */                                                   _jspx_th_html_005foption_005f48.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5180 */                                                   out.write(32);
/* 5181 */                                                   out.print(FormatUtil.getString("Service Monitoring"));
/* 5182 */                                                   out.write(32);
/* 5183 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f48.doAfterBody();
/* 5184 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5187 */                                                 if (_jspx_eval_html_005foption_005f48 != 1) {
/* 5188 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5191 */                                               if (_jspx_th_html_005foption_005f48.doEndTag() == 5) {
/* 5192 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48); return;
/*      */                                               }
/*      */                                               
/* 5195 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48);
/* 5196 */                                               out.write("\n\t  ");
/*      */                                               
/* 5198 */                                               OptionTag _jspx_th_html_005foption_005f49 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5199 */                                               _jspx_th_html_005foption_005f49.setPageContext(_jspx_page_context);
/* 5200 */                                               _jspx_th_html_005foption_005f49.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 5202 */                                               _jspx_th_html_005foption_005f49.setValue("SNMP:161");
/* 5203 */                                               int _jspx_eval_html_005foption_005f49 = _jspx_th_html_005foption_005f49.doStartTag();
/* 5204 */                                               if (_jspx_eval_html_005foption_005f49 != 0) {
/* 5205 */                                                 if (_jspx_eval_html_005foption_005f49 != 1) {
/* 5206 */                                                   out = _jspx_page_context.pushBody();
/* 5207 */                                                   _jspx_th_html_005foption_005f49.setBodyContent((BodyContent)out);
/* 5208 */                                                   _jspx_th_html_005foption_005f49.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5211 */                                                   out.print(FormatUtil.getString("SNMP"));
/* 5212 */                                                   out.write(" (V1 or V2c)");
/* 5213 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f49.doAfterBody();
/* 5214 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5217 */                                                 if (_jspx_eval_html_005foption_005f49 != 1) {
/* 5218 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5221 */                                               if (_jspx_th_html_005foption_005f49.doEndTag() == 5) {
/* 5222 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49); return;
/*      */                                               }
/*      */                                               
/* 5225 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49);
/* 5226 */                                               out.write("\n      ");
/*      */                                               
/* 5228 */                                               OptionTag _jspx_th_html_005foption_005f50 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5229 */                                               _jspx_th_html_005foption_005f50.setPageContext(_jspx_page_context);
/* 5230 */                                               _jspx_th_html_005foption_005f50.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 5232 */                                               _jspx_th_html_005foption_005f50.setValue("Script Monitor");
/* 5233 */                                               int _jspx_eval_html_005foption_005f50 = _jspx_th_html_005foption_005f50.doStartTag();
/* 5234 */                                               if (_jspx_eval_html_005foption_005f50 != 0) {
/* 5235 */                                                 if (_jspx_eval_html_005foption_005f50 != 1) {
/* 5236 */                                                   out = _jspx_page_context.pushBody();
/* 5237 */                                                   _jspx_th_html_005foption_005f50.setBodyContent((BodyContent)out);
/* 5238 */                                                   _jspx_th_html_005foption_005f50.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5241 */                                                   out.print(FormatUtil.getString("Script Monitor"));
/* 5242 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f50.doAfterBody();
/* 5243 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5246 */                                                 if (_jspx_eval_html_005foption_005f50 != 1) {
/* 5247 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5250 */                                               if (_jspx_th_html_005foption_005f50.doEndTag() == 5) {
/* 5251 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50); return;
/*      */                                               }
/*      */                                               
/* 5254 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50);
/* 5255 */                                               out.write(10);
/*      */ 
/*      */                                             }
/* 5258 */                                             else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("DATABASE"))
/*      */                                             {
/*      */ 
/* 5261 */                                               out.write("\n\t\t</optgroup>\t<optgroup label=\"");
/* 5262 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 5263 */                                               out.write("\">\n\t\t\t");
/*      */                                               
/* 5265 */                                               OptionTag _jspx_th_html_005foption_005f51 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5266 */                                               _jspx_th_html_005foption_005f51.setPageContext(_jspx_page_context);
/* 5267 */                                               _jspx_th_html_005foption_005f51.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 5269 */                                               _jspx_th_html_005foption_005f51.setValue("SYSTEM:9999");
/* 5270 */                                               int _jspx_eval_html_005foption_005f51 = _jspx_th_html_005foption_005f51.doStartTag();
/* 5271 */                                               if (_jspx_eval_html_005foption_005f51 != 0) {
/* 5272 */                                                 if (_jspx_eval_html_005foption_005f51 != 1) {
/* 5273 */                                                   out = _jspx_page_context.pushBody();
/* 5274 */                                                   _jspx_th_html_005foption_005f51.setBodyContent((BodyContent)out);
/* 5275 */                                                   _jspx_th_html_005foption_005f51.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5278 */                                                   out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 5279 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f51.doAfterBody();
/* 5280 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5283 */                                                 if (_jspx_eval_html_005foption_005f51 != 1) {
/* 5284 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5287 */                                               if (_jspx_th_html_005foption_005f51.doEndTag() == 5) {
/* 5288 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51); return;
/*      */                                               }
/*      */                                               
/* 5291 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51);
/* 5292 */                                               out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 5293 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 5294 */                                               out.write("\">\n\t\t\t");
/*      */                                               
/* 5296 */                                               OptionTag _jspx_th_html_005foption_005f52 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5297 */                                               _jspx_th_html_005foption_005f52.setPageContext(_jspx_page_context);
/* 5298 */                                               _jspx_th_html_005foption_005f52.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 5300 */                                               _jspx_th_html_005foption_005f52.setValue("DB2:50000");
/* 5301 */                                               int _jspx_eval_html_005foption_005f52 = _jspx_th_html_005foption_005f52.doStartTag();
/* 5302 */                                               if (_jspx_eval_html_005foption_005f52 != 0) {
/* 5303 */                                                 if (_jspx_eval_html_005foption_005f52 != 1) {
/* 5304 */                                                   out = _jspx_page_context.pushBody();
/* 5305 */                                                   _jspx_th_html_005foption_005f52.setBodyContent((BodyContent)out);
/* 5306 */                                                   _jspx_th_html_005foption_005f52.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5309 */                                                   out.print(FormatUtil.getString("am.webclient.db2.servertype"));
/* 5310 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f52.doAfterBody();
/* 5311 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5314 */                                                 if (_jspx_eval_html_005foption_005f52 != 1) {
/* 5315 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5318 */                                               if (_jspx_th_html_005foption_005f52.doEndTag() == 5) {
/* 5319 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52); return;
/*      */                                               }
/*      */                                               
/* 5322 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52);
/* 5323 */                                               out.write("\n\t\t\t");
/*      */                                               
/* 5325 */                                               OptionTag _jspx_th_html_005foption_005f53 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5326 */                                               _jspx_th_html_005foption_005f53.setPageContext(_jspx_page_context);
/* 5327 */                                               _jspx_th_html_005foption_005f53.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 5329 */                                               _jspx_th_html_005foption_005f53.setValue("MSSQLDB:1433");
/* 5330 */                                               int _jspx_eval_html_005foption_005f53 = _jspx_th_html_005foption_005f53.doStartTag();
/* 5331 */                                               if (_jspx_eval_html_005foption_005f53 != 0) {
/* 5332 */                                                 if (_jspx_eval_html_005foption_005f53 != 1) {
/* 5333 */                                                   out = _jspx_page_context.pushBody();
/* 5334 */                                                   _jspx_th_html_005foption_005f53.setBodyContent((BodyContent)out);
/* 5335 */                                                   _jspx_th_html_005foption_005f53.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5338 */                                                   out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 5339 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f53.doAfterBody();
/* 5340 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5343 */                                                 if (_jspx_eval_html_005foption_005f53 != 1) {
/* 5344 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5347 */                                               if (_jspx_th_html_005foption_005f53.doEndTag() == 5) {
/* 5348 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53); return;
/*      */                                               }
/*      */                                               
/* 5351 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53);
/* 5352 */                                               out.write("\n\t\t\t");
/*      */                                               
/* 5354 */                                               OptionTag _jspx_th_html_005foption_005f54 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5355 */                                               _jspx_th_html_005foption_005f54.setPageContext(_jspx_page_context);
/* 5356 */                                               _jspx_th_html_005foption_005f54.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 5358 */                                               _jspx_th_html_005foption_005f54.setValue("MYSQLDB:3306");
/* 5359 */                                               int _jspx_eval_html_005foption_005f54 = _jspx_th_html_005foption_005f54.doStartTag();
/* 5360 */                                               if (_jspx_eval_html_005foption_005f54 != 0) {
/* 5361 */                                                 if (_jspx_eval_html_005foption_005f54 != 1) {
/* 5362 */                                                   out = _jspx_page_context.pushBody();
/* 5363 */                                                   _jspx_th_html_005foption_005f54.setBodyContent((BodyContent)out);
/* 5364 */                                                   _jspx_th_html_005foption_005f54.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5367 */                                                   out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 5368 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f54.doAfterBody();
/* 5369 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5372 */                                                 if (_jspx_eval_html_005foption_005f54 != 1) {
/* 5373 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5376 */                                               if (_jspx_th_html_005foption_005f54.doEndTag() == 5) {
/* 5377 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54); return;
/*      */                                               }
/*      */                                               
/* 5380 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54);
/* 5381 */                                               out.write("\n\t\t\t");
/*      */                                               
/* 5383 */                                               OptionTag _jspx_th_html_005foption_005f55 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5384 */                                               _jspx_th_html_005foption_005f55.setPageContext(_jspx_page_context);
/* 5385 */                                               _jspx_th_html_005foption_005f55.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 5387 */                                               _jspx_th_html_005foption_005f55.setValue("ORACLEDB:1521");
/* 5388 */                                               int _jspx_eval_html_005foption_005f55 = _jspx_th_html_005foption_005f55.doStartTag();
/* 5389 */                                               if (_jspx_eval_html_005foption_005f55 != 0) {
/* 5390 */                                                 if (_jspx_eval_html_005foption_005f55 != 1) {
/* 5391 */                                                   out = _jspx_page_context.pushBody();
/* 5392 */                                                   _jspx_th_html_005foption_005f55.setBodyContent((BodyContent)out);
/* 5393 */                                                   _jspx_th_html_005foption_005f55.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5396 */                                                   out.print(FormatUtil.getString("am.webclient.oracle.servertype"));
/* 5397 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f55.doAfterBody();
/* 5398 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5401 */                                                 if (_jspx_eval_html_005foption_005f55 != 1) {
/* 5402 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5405 */                                               if (_jspx_th_html_005foption_005f55.doEndTag() == 5) {
/* 5406 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55); return;
/*      */                                               }
/*      */                                               
/* 5409 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55);
/* 5410 */                                               out.write("\n\t\t\t");
/*      */                                               
/* 5412 */                                               OptionTag _jspx_th_html_005foption_005f56 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5413 */                                               _jspx_th_html_005foption_005f56.setPageContext(_jspx_page_context);
/* 5414 */                                               _jspx_th_html_005foption_005f56.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 5416 */                                               _jspx_th_html_005foption_005f56.setValue("SYBASEDB:5000");
/* 5417 */                                               int _jspx_eval_html_005foption_005f56 = _jspx_th_html_005foption_005f56.doStartTag();
/* 5418 */                                               if (_jspx_eval_html_005foption_005f56 != 0) {
/* 5419 */                                                 if (_jspx_eval_html_005foption_005f56 != 1) {
/* 5420 */                                                   out = _jspx_page_context.pushBody();
/* 5421 */                                                   _jspx_th_html_005foption_005f56.setBodyContent((BodyContent)out);
/* 5422 */                                                   _jspx_th_html_005foption_005f56.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5425 */                                                   out.print(FormatUtil.getString("Sybase"));
/* 5426 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f56.doAfterBody();
/* 5427 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5430 */                                                 if (_jspx_eval_html_005foption_005f56 != 1) {
/* 5431 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5434 */                                               if (_jspx_th_html_005foption_005f56.doEndTag() == 5) {
/* 5435 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56); return;
/*      */                                               }
/*      */                                               
/* 5438 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56);
/* 5439 */                                               out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 5440 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 5441 */                                               out.write("\">\n\t\t\t");
/*      */                                               
/* 5443 */                                               OptionTag _jspx_th_html_005foption_005f57 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5444 */                                               _jspx_th_html_005foption_005f57.setPageContext(_jspx_page_context);
/* 5445 */                                               _jspx_th_html_005foption_005f57.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 5447 */                                               _jspx_th_html_005foption_005f57.setValue("SERVICE:9090");
/* 5448 */                                               int _jspx_eval_html_005foption_005f57 = _jspx_th_html_005foption_005f57.doStartTag();
/* 5449 */                                               if (_jspx_eval_html_005foption_005f57 != 0) {
/* 5450 */                                                 if (_jspx_eval_html_005foption_005f57 != 1) {
/* 5451 */                                                   out = _jspx_page_context.pushBody();
/* 5452 */                                                   _jspx_th_html_005foption_005f57.setBodyContent((BodyContent)out);
/* 5453 */                                                   _jspx_th_html_005foption_005f57.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5456 */                                                   out.write(32);
/* 5457 */                                                   out.print(FormatUtil.getString("Service Monitoring"));
/* 5458 */                                                   out.write(32);
/* 5459 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f57.doAfterBody();
/* 5460 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5463 */                                                 if (_jspx_eval_html_005foption_005f57 != 1) {
/* 5464 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5467 */                                               if (_jspx_th_html_005foption_005f57.doEndTag() == 5) {
/* 5468 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57); return;
/*      */                                               }
/*      */                                               
/* 5471 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57);
/* 5472 */                                               out.write("\n\t\t\t");
/*      */                                               
/* 5474 */                                               OptionTag _jspx_th_html_005foption_005f58 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5475 */                                               _jspx_th_html_005foption_005f58.setPageContext(_jspx_page_context);
/* 5476 */                                               _jspx_th_html_005foption_005f58.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 5478 */                                               _jspx_th_html_005foption_005f58.setValue("SNMP:161");
/* 5479 */                                               int _jspx_eval_html_005foption_005f58 = _jspx_th_html_005foption_005f58.doStartTag();
/* 5480 */                                               if (_jspx_eval_html_005foption_005f58 != 0) {
/* 5481 */                                                 if (_jspx_eval_html_005foption_005f58 != 1) {
/* 5482 */                                                   out = _jspx_page_context.pushBody();
/* 5483 */                                                   _jspx_th_html_005foption_005f58.setBodyContent((BodyContent)out);
/* 5484 */                                                   _jspx_th_html_005foption_005f58.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5487 */                                                   out.print(FormatUtil.getString("SNMP"));
/* 5488 */                                                   out.write(" (V1 or V2c)");
/* 5489 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f58.doAfterBody();
/* 5490 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5493 */                                                 if (_jspx_eval_html_005foption_005f58 != 1) {
/* 5494 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5497 */                                               if (_jspx_th_html_005foption_005f58.doEndTag() == 5) {
/* 5498 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58); return;
/*      */                                               }
/*      */                                               
/* 5501 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58);
/* 5502 */                                               out.write("</optgroup>");
/* 5503 */                                               out.write("\n\t\t\t<optgroup label=\"");
/* 5504 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 5505 */                                               out.write("\">\n\t\t\t");
/*      */                                               
/* 5507 */                                               OptionTag _jspx_th_html_005foption_005f59 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5508 */                                               _jspx_th_html_005foption_005f59.setPageContext(_jspx_page_context);
/* 5509 */                                               _jspx_th_html_005foption_005f59.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 5511 */                                               _jspx_th_html_005foption_005f59.setValue("UrlMonitor");
/* 5512 */                                               int _jspx_eval_html_005foption_005f59 = _jspx_th_html_005foption_005f59.doStartTag();
/* 5513 */                                               if (_jspx_eval_html_005foption_005f59 != 0) {
/* 5514 */                                                 if (_jspx_eval_html_005foption_005f59 != 1) {
/* 5515 */                                                   out = _jspx_page_context.pushBody();
/* 5516 */                                                   _jspx_th_html_005foption_005f59.setBodyContent((BodyContent)out);
/* 5517 */                                                   _jspx_th_html_005foption_005f59.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5520 */                                                   out.print(FormatUtil.getString("HTTP-URLs"));
/* 5521 */                                                   out.write(32);
/* 5522 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f59.doAfterBody();
/* 5523 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5526 */                                                 if (_jspx_eval_html_005foption_005f59 != 1) {
/* 5527 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5530 */                                               if (_jspx_th_html_005foption_005f59.doEndTag() == 5) {
/* 5531 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59); return;
/*      */                                               }
/*      */                                               
/* 5534 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59);
/* 5535 */                                               out.write("\n\t\t\t");
/*      */                                               
/* 5537 */                                               OptionTag _jspx_th_html_005foption_005f60 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5538 */                                               _jspx_th_html_005foption_005f60.setPageContext(_jspx_page_context);
/* 5539 */                                               _jspx_th_html_005foption_005f60.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 5541 */                                               _jspx_th_html_005foption_005f60.setValue("UrlSeq");
/* 5542 */                                               int _jspx_eval_html_005foption_005f60 = _jspx_th_html_005foption_005f60.doStartTag();
/* 5543 */                                               if (_jspx_eval_html_005foption_005f60 != 0) {
/* 5544 */                                                 if (_jspx_eval_html_005foption_005f60 != 1) {
/* 5545 */                                                   out = _jspx_page_context.pushBody();
/* 5546 */                                                   _jspx_th_html_005foption_005f60.setBodyContent((BodyContent)out);
/* 5547 */                                                   _jspx_th_html_005foption_005f60.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5550 */                                                   out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 5551 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f60.doAfterBody();
/* 5552 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5555 */                                                 if (_jspx_eval_html_005foption_005f60 != 1) {
/* 5556 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5559 */                                               if (_jspx_th_html_005foption_005f60.doEndTag() == 5) {
/* 5560 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60); return;
/*      */                                               }
/*      */                                               
/* 5563 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60);
/* 5564 */                                               out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 5565 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 5566 */                                               out.write("\">\n\t\t\t");
/*      */                                               
/* 5568 */                                               OptionTag _jspx_th_html_005foption_005f61 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5569 */                                               _jspx_th_html_005foption_005f61.setPageContext(_jspx_page_context);
/* 5570 */                                               _jspx_th_html_005foption_005f61.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 5572 */                                               _jspx_th_html_005foption_005f61.setValue("Script Monitor");
/* 5573 */                                               int _jspx_eval_html_005foption_005f61 = _jspx_th_html_005foption_005f61.doStartTag();
/* 5574 */                                               if (_jspx_eval_html_005foption_005f61 != 0) {
/* 5575 */                                                 if (_jspx_eval_html_005foption_005f61 != 1) {
/* 5576 */                                                   out = _jspx_page_context.pushBody();
/* 5577 */                                                   _jspx_th_html_005foption_005f61.setBodyContent((BodyContent)out);
/* 5578 */                                                   _jspx_th_html_005foption_005f61.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5581 */                                                   out.print(FormatUtil.getString("Script Monitor"));
/* 5582 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f61.doAfterBody();
/* 5583 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5586 */                                                 if (_jspx_eval_html_005foption_005f61 != 1) {
/* 5587 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5590 */                                               if (_jspx_th_html_005foption_005f61.doEndTag() == 5) {
/* 5591 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61); return;
/*      */                                               }
/*      */                                               
/* 5594 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61);
/* 5595 */                                               out.write(10);
/* 5596 */                                               out.write(10);
/*      */                                             }
/*      */                                             
/*      */ 
/* 5600 */                                             out.write("\n\n\n\n      ");
/* 5601 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 5602 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5605 */                                           if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 5606 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5609 */                                         if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 5610 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                         }
/*      */                                         
/* 5613 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 5614 */                                         out.write("\n                      \n      ");
/* 5615 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 5616 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5620 */                                     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 5621 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                                     }
/*      */                                     
/* 5624 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 5625 */                                     out.write("\n      ");
/* 5626 */                                     if (_jspx_meth_c_005fif_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5628 */                                     out.write("\n      </td>\n      \n      ");
/* 5629 */                                     if (request.getParameter("type") != null) {
/* 5630 */                                       isConfMonitor = confMonConfig.isConfMonitor(request.getParameter("type"));
/* 5631 */                                       String restype = request.getParameter("type");
/* 5632 */                                       if (restype.indexOf(":") != -1) {
/* 5633 */                                         restype = restype.substring(0, restype.indexOf(":"));
/*      */                                       }
/* 5635 */                                       if (((isConfMonitor) && (!restype.equals("QueryMonitor"))) || ((!restype.equals("JMX1.2-MX4J-RMI")) && (!restype.equals("Generic WMI")) && (!restype.equals("Script Monitor")) && (!restype.equals("Custom-Application")) && (!restype.equals("File System Monitor")) && (!restype.equals("QueryMonitor")) && (!restype.equals("SNMP")) && (!restype.equals("TELNET")) && (!restype.equals("Exchange")) && (!restype.equals("WTA")) && (!restype.equals("WEB")) && (!restype.equals("UrlSeq")) && (!restype.equals("PHP")) && (!restype.equals("IIS")) && (!restype.equals("APACHE")) && (!restype.equals("MAIL")) && (!restype.equals("All")) && (restype.indexOf("SAP") == -1))) {
/* 5636 */                                         out.write("\n      \t<td class=\"tableheading-monitor-config itadmin-hide\" align=\"right\">\n      \n      \t\t<div id=\"Conf-bulk-configuration\"> \n\t\t\t    \t\t<a href=\"javascript:void(0)\"  onclick=\"window.open('/BulkAddMonitors.do?method=showBulkImportForm&type=");
/* 5637 */                                         out.print(restype);
/* 5638 */                                         out.write("','windowName','toolbar=no,status=no,menubar=no,width=1000,height=500,scrollbars=yes')\" class=\"staticlinks\" >");
/* 5639 */                                         out.print(FormatUtil.getString("am.webclient.admin.bulkimport.label.text"));
/* 5640 */                                         out.write("</a>\n\t    \t</div><img src=\"/images/script-icon.gif\">\n   \t   </td>\n      \n      \t");
/*      */                                       }
/*      */                                     }
/* 5643 */                                     out.write("     \n      \n  </tr>\n</table>\n <script src=\"/template/chosen.jquery.min.js\" type=\"text/javascript\"></script><script type=\"text/javascript\"> $(\".formtext\").chosen();  </script>\n");
/* 5644 */                                     out.write("\n\n<table id=\"DiscoveryDetails\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"1\" class=\"lrborder\">\n  <tr>\n<input type=\"hidden\" name=\"wmihost\"/>\n<input type=\"hidden\" name=\"hideFieldsForIT360\" value=\"");
/* 5645 */                                     out.print(request.getParameter("hideFieldsForIT360"));
/* 5646 */                                     out.write("\">\n    <td height=\"20\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 5647 */                                     out.print(FormatUtil.getString("am.webclient.common.displayname.text"));
/* 5648 */                                     out.write("</a><span class=\"mandatory\">*</span></label></td>\n    <td  height=\"20\"  valign=\"bottom\">");
/* 5649 */                                     if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5651 */                                     out.write("</td>\n\n</tr>\n<tr>\n");
/* 5652 */                                     if ((mtype.equals("file")) || (mtype.equals("directory")) || (mtype.equals("File System Monitor"))) {
/* 5653 */                                       out.write("\n<td class=\"bodytext label-align addmonitor-label\"><label>");
/* 5654 */                                       out.print(FormatUtil.getString("Type"));
/* 5655 */                                       out.write("</label></td>\n<td class=\"bodytext\" width=\"75%\" valign=\"middle\">\n\t <table width=\"99%\" cellspacing=\"0\">\n\t\t <tr style=\"padding-top:5px\" colspan=4>\n\t\t\t  <td height=\"20\" width=\"2%\" class=\"bodytext\"> ");
/* 5656 */                                       if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 5658 */                                       out.write(" </td>\n\t\t\t  <td height=\"20\" width=\"14%\" class=\"bodytext\">");
/* 5659 */                                       out.print(FormatUtil.getString("File"));
/* 5660 */                                       out.write("</td>\n\t\t\t  <td height=\"20\" width=\"2%\" class=\"bodytext\">");
/* 5661 */                                       if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 5663 */                                       out.write("</td>\n\t\t\t  <td height=\"20\" width=\"81%\" class=\"bodytext\">");
/* 5664 */                                       out.print(FormatUtil.getString("Directory"));
/* 5665 */                                       out.write("</td>\n\t\t</tr>\n\t </table>\n</td>\n</tr>\n  <!--tr>\n\n    <td width=\"19%\" height=\"35\" class=\"bodytext\" >");
/* 5666 */                                       out.print(FormatUtil.getString("Path"));
/* 5667 */                                       out.write("<span class=\"mandatory\">*</span>\n    </td>\n<td height=\"35\" width=\"81%\" colspan=\"2\" class=\"bodytext\">");
/* 5668 */                                       if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 5670 */                                       out.write("\n      <span class=\"footer\">");
/* 5671 */                                       out.print(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/* 5672 */                                       out.write("</span></td>\n  </tr-->\n\n<tr>\n<td class=\"bodytext label-align addmonitor-label\" ><div id=\"location\">");
/* 5673 */                                       out.print(FormatUtil.getString("File/Directory Location"));
/* 5674 */                                       out.write("</div></td>\n\n");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 5678 */                                       out.write("\n\n  <tr>\n    <td class=\"bodytext label-align addmonitor-label\"><label>");
/* 5679 */                                       out.print(FormatUtil.getString("Script Location"));
/* 5680 */                                       out.write("</label></td>\n");
/*      */                                     }
/* 5682 */                                     out.write("\n     <td class=\"bodytext\" width=\"75%\" valign=\"middle\">\n\t     <table width=\"99%\" cellspacing=\"0\">\n\t\t\t <tr style=\"padding-top:5px\" colspan=4>\n\t\t\t\t  <td height=\"20\" width=\"2%\" class=\"bodytext\">\n\t\t\t\t  \t");
/* 5683 */                                     if (!OEMUtil.isRemove("am.localscript.remove")) {
/* 5684 */                                       out.write(" \n\t\t\t\t  \t\t");
/* 5685 */                                       if (_jspx_meth_html_005fradio_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 5687 */                                       out.write("</td>\n\t\t\t\t  <td height=\"20\" width=\"14%\" class=\"bodytext\">");
/* 5688 */                                       out.print(FormatUtil.getString("am.webclient.script.localserver"));
/* 5689 */                                       out.write("\n\t\t\t\t  \t");
/*      */                                     }
/* 5691 */                                     out.write("\n\t\t\t\t  </td>\n\t\t\t\t  <td height=\"20\" width=\"2%\" class=\"bodytext\">");
/* 5692 */                                     if (_jspx_meth_html_005fradio_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5694 */                                     out.write("</td>\n\t\t\t\t  <td height=\"20\" width=\"81%\" class=\"bodytext\">");
/* 5695 */                                     out.print(FormatUtil.getString("am.webclient.script.remoteserver"));
/* 5696 */                                     out.write("</td>\n\t\t\t</tr>\n\t\t </table>\n    </td>\n  </tr>\n  <tr id=\"remotescript\" style=\"display:table-row;\">\n    <td colspan=\"2\"> \n        <table border=\"0\" cellpadding=\"6\" width=\"99%\">\n          <tr>\n            <td class=\"bodytext label-align addmonitor-label\" width=\"25%\"><label>");
/* 5697 */                                     out.print(FormatUtil.getString("am.webclient.script.choosehost"));
/* 5698 */                                     out.write("</label></td>\n            <td width=\"81%\"> ");
/*      */                                     
/* 5700 */                                     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 5701 */                                     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 5702 */                                     _jspx_th_html_005fselect_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5704 */                                     _jspx_th_html_005fselect_005f1.setProperty("choosehost");
/*      */                                     
/* 5706 */                                     _jspx_th_html_005fselect_005f1.setOnchange("javascript:managenewHost()");
/*      */                                     
/* 5708 */                                     _jspx_th_html_005fselect_005f1.setStyleClass("formtext medium");
/* 5709 */                                     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 5710 */                                     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 5711 */                                       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 5712 */                                         out = _jspx_page_context.pushBody();
/* 5713 */                                         _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 5714 */                                         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 5717 */                                         out.write("\n            \t");
/*      */                                         
/* 5719 */                                         OptionTag _jspx_th_html_005foption_005f62 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 5720 */                                         _jspx_th_html_005foption_005f62.setPageContext(_jspx_page_context);
/* 5721 */                                         _jspx_th_html_005foption_005f62.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                         
/* 5723 */                                         _jspx_th_html_005foption_005f62.setValue("-1");
/*      */                                         
/* 5725 */                                         _jspx_th_html_005foption_005f62.setStyle("font-weight: bold;");
/* 5726 */                                         int _jspx_eval_html_005foption_005f62 = _jspx_th_html_005foption_005f62.doStartTag();
/* 5727 */                                         if (_jspx_eval_html_005foption_005f62 != 0) {
/* 5728 */                                           if (_jspx_eval_html_005foption_005f62 != 1) {
/* 5729 */                                             out = _jspx_page_context.pushBody();
/* 5730 */                                             _jspx_th_html_005foption_005f62.setBodyContent((BodyContent)out);
/* 5731 */                                             _jspx_th_html_005foption_005f62.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 5734 */                                             out.print(FormatUtil.getString("am.webclient.script.newhost"));
/* 5735 */                                             int evalDoAfterBody = _jspx_th_html_005foption_005f62.doAfterBody();
/* 5736 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5739 */                                           if (_jspx_eval_html_005foption_005f62 != 1) {
/* 5740 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5743 */                                         if (_jspx_th_html_005foption_005f62.doEndTag() == 5) {
/* 5744 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f62); return;
/*      */                                         }
/*      */                                         
/* 5747 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f62);
/* 5748 */                                         out.write("\n             \t");
/*      */                                         
/* 5750 */                                         OptionTag _jspx_th_html_005foption_005f63 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5751 */                                         _jspx_th_html_005foption_005f63.setPageContext(_jspx_page_context);
/* 5752 */                                         _jspx_th_html_005foption_005f63.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                         
/* 5754 */                                         _jspx_th_html_005foption_005f63.setValue("-2");
/* 5755 */                                         int _jspx_eval_html_005foption_005f63 = _jspx_th_html_005foption_005f63.doStartTag();
/* 5756 */                                         if (_jspx_eval_html_005foption_005f63 != 0) {
/* 5757 */                                           if (_jspx_eval_html_005foption_005f63 != 1) {
/* 5758 */                                             out = _jspx_page_context.pushBody();
/* 5759 */                                             _jspx_th_html_005foption_005f63.setBodyContent((BodyContent)out);
/* 5760 */                                             _jspx_th_html_005foption_005f63.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 5763 */                                             out.print(FormatUtil.getString("am.webclient.wmi.selecthost.text"));
/* 5764 */                                             int evalDoAfterBody = _jspx_th_html_005foption_005f63.doAfterBody();
/* 5765 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5768 */                                           if (_jspx_eval_html_005foption_005f63 != 1) {
/* 5769 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5772 */                                         if (_jspx_th_html_005foption_005f63.doEndTag() == 5) {
/* 5773 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f63); return;
/*      */                                         }
/*      */                                         
/* 5776 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f63);
/* 5777 */                                         out.write(" \n\n\n\n              ");
/*      */                                         
/* 5779 */                                         String hostquery = null;
/* 5780 */                                         ArrayList arr = new ArrayList();
/*      */                                         try
/*      */                                         {
/* 5783 */                                           String localhostaddr = InetAddress.getLocalHost().getHostName();
/* 5784 */                                           String privilegeCondition = "";
/* 5785 */                                           boolean isUserResourceEnabled = false;
/* 5786 */                                           String loginUserid = null;
/* 5787 */                                           if ((request.getAttribute("checkForMonitorGroup") != null) && (((Boolean)request.getAttribute("checkForMonitorGroup")).booleanValue())) {
/* 5788 */                                             if (ClientDBUtil.isUserResourceEnabled()) {
/* 5789 */                                               isUserResourceEnabled = true;
/* 5790 */                                               loginUserid = ClientDBUtil.getLoginUserid(request);
/*      */                                             } else {
/* 5792 */                                               privilegeCondition = " and " + ManagedApplication.getCondition("m.RESOURCEID", ClientDBUtil.getResourceIdentity(request.getRemoteUser()));
/*      */                                             }
/*      */                                           }
/*      */                                           
/*      */ 
/* 5797 */                                           if ((mtype.equals("file")) || (mtype.equals("directory")) || (mtype.equals("File System Monitor"))) {
/* 5798 */                                             if ((EnterpriseUtil.isAdminServer()) || (System.getProperty("os.name").startsWith("Windows"))) {
/* 5799 */                                               if (isUserResourceEnabled) {
/* 5800 */                                                 hostquery = "select m.RESOURCEID,m.RESOURCENAME,h.USERNAME,2 as flag from HostDetails h, AM_USERRESOURCESTABLE, AM_ManagedObject m,AM_HOSTINFO inf where AM_USERRESOURCESTABLE.RESOURCEID=m.RESOURCEID and AM_USERRESOURCESTABLE.USERID =" + loginUserid + " and h.RESOURCENAME=m.RESOURCENAME and h.COMPONENTNAME='HOST' and m.RESOURCEID=inf.RESOURCEID and inf.MODE in ('TELNET','SSH','SSH_KEY','WMI') and m.TYPE<>'VirtualMachine' and h.RESOURCENAME not like '" + localhostaddr + "%'  UNION select ID,HOSTNAME,USERNAME,1 as flag from AM_SCRIPTHOSTDETAILS where MODE in ('TELNET','SSH','SSH_KEY','WMI') order by RESOURCENAME";
/*      */                                               } else {
/* 5802 */                                                 hostquery = "select m.RESOURCEID,m.RESOURCENAME,h.USERNAME,2 as flag from HostDetails h,AM_ManagedObject m,AM_HOSTINFO inf where h.RESOURCENAME=m.RESOURCENAME and h.COMPONENTNAME='HOST' and m.RESOURCEID=inf.RESOURCEID and inf.MODE in ('TELNET','SSH','SSH_KEY','WMI') and m.TYPE<>'VirtualMachine' and h.RESOURCENAME not like '" + localhostaddr + "%' " + privilegeCondition + " UNION select ID,HOSTNAME,USERNAME,1 as flag from AM_SCRIPTHOSTDETAILS where MODE in ('TELNET','SSH','SSH_KEY','WMI') order by RESOURCENAME";
/*      */                                               }
/*      */                                               
/*      */                                             }
/* 5806 */                                             else if (isUserResourceEnabled) {
/* 5807 */                                               hostquery = "select m.RESOURCEID,m.RESOURCENAME,h.USERNAME,2 as flag from HostDetails h, AM_USERRESOURCESTABLE, AM_ManagedObject m,AM_HOSTINFO inf where AM_USERRESOURCESTABLE.RESOURCEID=m.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and h.RESOURCENAME=m.RESOURCENAME and h.COMPONENTNAME='HOST' and m.RESOURCEID=inf.RESOURCEID and inf.MODE in ('TELNET','SSH','SSH_KEY') and h.RESOURCENAME not like '" + localhostaddr + "%' and m.TYPE<>'VirtualMachine' UNION select ID,HOSTNAME,USERNAME,1 as flag from AM_SCRIPTHOSTDETAILS where MODE in ('TELNET','SSH','SSH_KEY') order by RESOURCENAME";
/*      */                                             } else {
/* 5809 */                                               hostquery = "select m.RESOURCEID,m.RESOURCENAME,h.USERNAME,2 as flag from HostDetails h,AM_ManagedObject m,AM_HOSTINFO inf where h.RESOURCENAME=m.RESOURCENAME and h.COMPONENTNAME='HOST' and m.RESOURCEID=inf.RESOURCEID and inf.MODE in ('TELNET','SSH','SSH_KEY') and h.RESOURCENAME not like '" + localhostaddr + "%' " + privilegeCondition + " and m.TYPE<>'VirtualMachine' UNION select ID,HOSTNAME,USERNAME,1 as flag from AM_SCRIPTHOSTDETAILS where MODE in ('TELNET','SSH','SSH_KEY') order by RESOURCENAME";
/*      */                                             }
/*      */                                             
/*      */ 
/*      */                                           }
/* 5814 */                                           else if (isUserResourceEnabled) {
/* 5815 */                                             hostquery = "select m.RESOURCEID,m.RESOURCENAME,h.USERNAME,2 as flag from HostDetails h, AM_USERRESOURCESTABLE, AM_ManagedObject m,AM_HOSTINFO inf where AM_USERRESOURCESTABLE.RESOURCEID=m.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and h.RESOURCENAME=m.RESOURCENAME and h.COMPONENTNAME='HOST' and m.RESOURCEID=inf.RESOURCEID and inf.MODE in ('TELNET','SSH','SSH_KEY') and h.RESOURCENAME not like '" + localhostaddr + "%' and m.TYPE<>'VirtualMachine' UNION select ID,HOSTNAME,USERNAME,1 as flag from AM_SCRIPTHOSTDETAILS where MODE in ('TELNET','SSH','SSH_KEY') order by RESOURCENAME";
/*      */                                           } else {
/* 5817 */                                             hostquery = "select m.RESOURCEID,m.RESOURCENAME,h.USERNAME,2 as flag from HostDetails h,AM_ManagedObject m,AM_HOSTINFO inf where h.RESOURCENAME=m.RESOURCENAME and h.COMPONENTNAME='HOST' and m.RESOURCEID=inf.RESOURCEID and inf.MODE in ('TELNET','SSH','SSH_KEY') and h.RESOURCENAME not like '" + localhostaddr + "%' " + privilegeCondition + " and m.TYPE<>'VirtualMachine' UNION select ID,HOSTNAME,USERNAME,1 as flag from AM_SCRIPTHOSTDETAILS where MODE in ('TELNET','SSH','SSH_KEY') order by RESOURCENAME";
/*      */                                           }
/*      */                                           
/*      */ 
/*      */ 
/* 5822 */                                           AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */                                           
/* 5824 */                                           SortedMap hostinfo = new java.util.TreeMap();
/* 5825 */                                           ArrayList arr1 = new ArrayList();
/* 5826 */                                           String optval = null;String hostIp = null;
/*      */                                           
/* 5828 */                                           ResultSet rs = AMConnectionPool.executeQueryStmt(hostquery);
/* 5829 */                                           while (rs.next())
/*      */                                           {
/*      */                                             try
/*      */                                             {
/* 5833 */                                               arr.add(rs.getString(2));
/*      */                                               try {
/* 5835 */                                                 hostIp = InetAddress.getByName(rs.getString(2)).getHostAddress();
/*      */ 
/*      */                                               }
/*      */                                               catch (Exception hostex)
/*      */                                               {
/* 5840 */                                                 hostex.printStackTrace();
/* 5841 */                                                 hostIp = rs.getString(2);
/*      */                                               }
/* 5843 */                                               arr1.add(rs.getString(1));
/* 5844 */                                               arr1.add(rs.getString(2));
/* 5845 */                                               arr1.add(rs.getString(3));
/* 5846 */                                               arr1.add(rs.getString(4));
/* 5847 */                                               hostinfo.put(rs.getString(2) + hostIp, arr1);
/* 5848 */                                               arr1 = new ArrayList();
/*      */                                             }
/*      */                                             catch (Exception ex)
/*      */                                             {
/* 5852 */                                               ex.printStackTrace();
/*      */                                             }
/*      */                                           }
/* 5855 */                                           Iterator it = hostinfo.keySet().iterator();
/*      */                                           
/* 5857 */                                           while (it.hasNext()) {
/* 5858 */                                             String key = (String)it.next();
/* 5859 */                                             ArrayList arrlist = (ArrayList)hostinfo.get(key);
/* 5860 */                                             optval = arrlist.get(3) + "," + arrlist.get(0);
/*      */                                             
/*      */ 
/* 5863 */                                             out.write("\n\t\t              ");
/*      */                                             
/* 5865 */                                             OptionTag _jspx_th_html_005foption_005f64 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5866 */                                             _jspx_th_html_005foption_005f64.setPageContext(_jspx_page_context);
/* 5867 */                                             _jspx_th_html_005foption_005f64.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                             
/* 5869 */                                             _jspx_th_html_005foption_005f64.setValue(optval);
/* 5870 */                                             int _jspx_eval_html_005foption_005f64 = _jspx_th_html_005foption_005f64.doStartTag();
/* 5871 */                                             if (_jspx_eval_html_005foption_005f64 != 0) {
/* 5872 */                                               if (_jspx_eval_html_005foption_005f64 != 1) {
/* 5873 */                                                 out = _jspx_page_context.pushBody();
/* 5874 */                                                 _jspx_th_html_005foption_005f64.setBodyContent((BodyContent)out);
/* 5875 */                                                 _jspx_th_html_005foption_005f64.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 5878 */                                                 out.print(arrlist.get(1));
/* 5879 */                                                 out.write(32);
/* 5880 */                                                 out.write(40);
/* 5881 */                                                 out.print(arrlist.get(2));
/* 5882 */                                                 out.write(41);
/* 5883 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f64.doAfterBody();
/* 5884 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5887 */                                               if (_jspx_eval_html_005foption_005f64 != 1) {
/* 5888 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5891 */                                             if (_jspx_th_html_005foption_005f64.doEndTag() == 5) {
/* 5892 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f64); return;
/*      */                                             }
/*      */                                             
/* 5895 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f64);
/* 5896 */                                             out.write("\n\t\t              ");
/*      */                                           }
/*      */                                           
/*      */ 
/* 5900 */                                           AMConnectionPool.closeStatement(rs);
/*      */                                         }
/*      */                                         catch (Exception exc)
/*      */                                         {
/* 5904 */                                           exc.printStackTrace();
/*      */                                         }
/*      */                                         
/* 5907 */                                         out.write("\n              ");
/* 5908 */                                         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 5909 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 5912 */                                       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 5913 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 5916 */                                     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 5917 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */                                     }
/*      */                                     
/* 5920 */                                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 5921 */                                     out.write("\n\n<a href=\"javascript:void(0)\" class=\"staticlinks tdindent\" onClick=\"deletehost()\">");
/* 5922 */                                     out.print(FormatUtil.getString("am.webclient.wmi.host.deletehost.link.text"));
/* 5923 */                                     out.write("</a></td>\n\n\n          </tr>\n </table></td></tr>\n  <tr id=\"newhost\" style=\"display:table-row;\">\n    <td colspan=\"2\">\n        <table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" class=\"bg-lite dottedline\">\n        <tr >\n            <td height=28 width=\"25%\"  class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,'");
/* 5924 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.textmessage.hostexample"));
/* 5925 */                                     out.write("',false,true,'#000000',200,'lightyellow')\" onMouseOut=\"hideddrivetip()\">");
/* 5926 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.hostname"));
/* 5927 */                                     out.write("</a><span class=\"mandatory\">*</span></label></td>\n            <td height=28 width=\"75%\"> ");
/* 5928 */                                     if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5930 */                                     out.write("</td>\n          </tr>\n          <tr >\n            <td height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 5931 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.mode"));
/* 5932 */                                     out.write("<span class=\"mandatory\">*</span></label></td>\n            <td class=\"bodytext\"> ");
/*      */                                     
/* 5934 */                                     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 5935 */                                     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 5936 */                                     _jspx_th_html_005fselect_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5938 */                                     _jspx_th_html_005fselect_005f2.setProperty("monitoringmode");
/*      */                                     
/* 5940 */                                     _jspx_th_html_005fselect_005f2.setStyleClass("formtext medium");
/*      */                                     
/* 5942 */                                     _jspx_th_html_005fselect_005f2.setOnchange("changeport()");
/* 5943 */                                     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 5944 */                                     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 5945 */                                       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 5946 */                                         out = _jspx_page_context.pushBody();
/* 5947 */                                         _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 5948 */                                         _jspx_th_html_005fselect_005f2.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 5951 */                                         out.write("\n              ");
/*      */                                         
/* 5953 */                                         OptionTag _jspx_th_html_005foption_005f65 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5954 */                                         _jspx_th_html_005foption_005f65.setPageContext(_jspx_page_context);
/* 5955 */                                         _jspx_th_html_005foption_005f65.setParent(_jspx_th_html_005fselect_005f2);
/*      */                                         
/* 5957 */                                         _jspx_th_html_005foption_005f65.setValue("TELNET");
/* 5958 */                                         int _jspx_eval_html_005foption_005f65 = _jspx_th_html_005foption_005f65.doStartTag();
/* 5959 */                                         if (_jspx_eval_html_005foption_005f65 != 0) {
/* 5960 */                                           if (_jspx_eval_html_005foption_005f65 != 1) {
/* 5961 */                                             out = _jspx_page_context.pushBody();
/* 5962 */                                             _jspx_th_html_005foption_005f65.setBodyContent((BodyContent)out);
/* 5963 */                                             _jspx_th_html_005foption_005f65.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 5966 */                                             out.write(32);
/* 5967 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.telnet"));
/* 5968 */                                             out.write("\n              ");
/* 5969 */                                             int evalDoAfterBody = _jspx_th_html_005foption_005f65.doAfterBody();
/* 5970 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5973 */                                           if (_jspx_eval_html_005foption_005f65 != 1) {
/* 5974 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5977 */                                         if (_jspx_th_html_005foption_005f65.doEndTag() == 5) {
/* 5978 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f65); return;
/*      */                                         }
/*      */                                         
/* 5981 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f65);
/* 5982 */                                         out.write(32);
/*      */                                         
/* 5984 */                                         OptionTag _jspx_th_html_005foption_005f66 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5985 */                                         _jspx_th_html_005foption_005f66.setPageContext(_jspx_page_context);
/* 5986 */                                         _jspx_th_html_005foption_005f66.setParent(_jspx_th_html_005fselect_005f2);
/*      */                                         
/* 5988 */                                         _jspx_th_html_005foption_005f66.setValue("SSH");
/* 5989 */                                         int _jspx_eval_html_005foption_005f66 = _jspx_th_html_005foption_005f66.doStartTag();
/* 5990 */                                         if (_jspx_eval_html_005foption_005f66 != 0) {
/* 5991 */                                           if (_jspx_eval_html_005foption_005f66 != 1) {
/* 5992 */                                             out = _jspx_page_context.pushBody();
/* 5993 */                                             _jspx_th_html_005foption_005f66.setBodyContent((BodyContent)out);
/* 5994 */                                             _jspx_th_html_005foption_005f66.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 5997 */                                             out.write(32);
/* 5998 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssh"));
/* 5999 */                                             out.write(32);
/* 6000 */                                             int evalDoAfterBody = _jspx_th_html_005foption_005f66.doAfterBody();
/* 6001 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 6004 */                                           if (_jspx_eval_html_005foption_005f66 != 1) {
/* 6005 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 6008 */                                         if (_jspx_th_html_005foption_005f66.doEndTag() == 5) {
/* 6009 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f66); return;
/*      */                                         }
/*      */                                         
/* 6012 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f66);
/* 6013 */                                         out.write(10);
/* 6014 */                                         out.write(9);
/* 6015 */                                         out.write(9);
/* 6016 */                                         if (((EnterpriseUtil.isAdminServer()) || (System.getProperty("os.name").startsWith("Windows"))) && ((mtype.equals("file")) || (mtype.equals("directory")) || (mtype.equals("File System Monitor")))) {
/* 6017 */                                           out.write("\n\n\t\t\t");
/*      */                                           
/* 6019 */                                           OptionTag _jspx_th_html_005foption_005f67 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 6020 */                                           _jspx_th_html_005foption_005f67.setPageContext(_jspx_page_context);
/* 6021 */                                           _jspx_th_html_005foption_005f67.setParent(_jspx_th_html_005fselect_005f2);
/*      */                                           
/* 6023 */                                           _jspx_th_html_005foption_005f67.setValue("WMI");
/* 6024 */                                           int _jspx_eval_html_005foption_005f67 = _jspx_th_html_005foption_005f67.doStartTag();
/* 6025 */                                           if (_jspx_eval_html_005foption_005f67 != 0) {
/* 6026 */                                             if (_jspx_eval_html_005foption_005f67 != 1) {
/* 6027 */                                               out = _jspx_page_context.pushBody();
/* 6028 */                                               _jspx_th_html_005foption_005f67.setBodyContent((BodyContent)out);
/* 6029 */                                               _jspx_th_html_005foption_005f67.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 6032 */                                               out.write(32);
/* 6033 */                                               out.print(FormatUtil.getString("WMI"));
/* 6034 */                                               int evalDoAfterBody = _jspx_th_html_005foption_005f67.doAfterBody();
/* 6035 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 6038 */                                             if (_jspx_eval_html_005foption_005f67 != 1) {
/* 6039 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 6042 */                                           if (_jspx_th_html_005foption_005f67.doEndTag() == 5) {
/* 6043 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f67); return;
/*      */                                           }
/*      */                                           
/* 6046 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f67);
/* 6047 */                                           out.write(10);
/* 6048 */                                           out.write(9);
/* 6049 */                                           out.write(9);
/*      */                                         }
/* 6051 */                                         out.write("\n               ");
/* 6052 */                                         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 6053 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 6056 */                                       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 6057 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 6060 */                                     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 6061 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2); return;
/*      */                                     }
/*      */                                     
/* 6064 */                                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2);
/* 6065 */                                     out.write("</td>\n          </tr>\n         \n          <tr  height=\"35\">\n\t\t  <td  class=\"bodytext label-align\" width=\"25%\">");
/* 6066 */                                     out.print(FormatUtil.getString("Credential Details"));
/* 6067 */                                     out.write("</a></div><span class=\"mandatory\">*</span></td>\n\t\t  <td class=\"bodytext\" valign=\"center\">\n\t\t\t\t");
/* 6068 */                                     if (_jspx_meth_html_005fradio_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 6070 */                                     out.write("<span class=\"bodytext\">");
/* 6071 */                                     out.print(FormatUtil.getString("Use below credential"));
/* 6072 */                                     out.write("</span>&nbsp;\n\t\t\t\t");
/* 6073 */                                     if (_jspx_meth_html_005fradio_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 6075 */                                     out.write("<span class=\"bodytext\">");
/* 6076 */                                     out.print(FormatUtil.getString("Select from credential list"));
/* 6077 */                                     out.write("</span>\n\t\t  </td>\n\t      </tr>\n\t       <tr height=\"1px\" id='credentialDropRow' >\n\t\t  <td colspan='2' width='100%'>\n\t\t  <div id='credentialDropDiv'>\n\t\t    <table width='100%' cellpadding=\"5\" cellspacing=\"0\" height=\"30px\">\n\t\t      <tr>\n\t\t       <td  class=\"bodytext label-align\" width=\"25%\">");
/* 6078 */                                     out.print(FormatUtil.getString("Credential Manager"));
/* 6079 */                                     out.write("</a></div><span class=\"mandatory\">*</span></td>\n\t\t       <td class=\"bodytext\" valign=\"center\">\n\t\t\t");
/* 6080 */                                     if (_jspx_meth_logic_005fnotEmpty_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 6082 */                                     out.write("\n\t\t\t");
/* 6083 */                                     if (_jspx_meth_logic_005fempty_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 6085 */                                     out.write("\n\t\t      </td>\n\t\t     </tr>\n\t\t    </table>\n\t\t  </div>\n\t\t  </td>\n\t      </tr>\n\t<tr >\n\t<td colspan=\"2\" >\n\t   <div id ='credentialFormDiv'>\n\t   <table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t   <td colspan=\"2\" >\n\t<div id=\"sshKeyAuth\" style=\"display:none\">\n\t<table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t<tr>\n\t<TD  width=\"25%\" class=\"bodytext label-align addmonitor-label\">&nbsp;</TD>\n\t<TD  width=\"75%\" class=\"footer\">");
/* 6086 */                                     if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 6088 */                                     out.write("\n    ");
/* 6089 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssh.privateKeyMessage"));
/* 6090 */                                     out.write("</TD>\n\t</tr>\n\t</table>\n\t</div>\n\t</td></tr>\n          <tr>\n            <td height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><div id=\"uname\">");
/* 6091 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/* 6092 */                                     out.print("testing");
/* 6093 */                                     out.write("<span class=\"mandatory\">*</span></td></div>\n         <!--   <td height=\"28\" width=\"27%\">");
/* 6094 */                                     if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 6096 */                                     out.write("&nbsp;&nbsp; -->\n            <td height=\"28\" width=\"75%\"><input type=\"text\" name=\"username\" class=\"formtext default\" autocomplete=\"off\" /><!--");
/* 6097 */                                     if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 6099 */                                     out.write("-->&nbsp;&nbsp;\n            </td>\n          </tr >\n\t<tr>\n\t<td colspan=\"2\">\n\t<div id=\"passwordid\" style=\"display:none\">\n\t<table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n          <tr>\n            <td width=\"25%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,'");
/* 6100 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.message.passwordleave"));
/* 6101 */                                     out.write("',false,true,'#000000',350,'lightyellow')\" onMouseOut=\"hideddrivetip()\">");
/* 6102 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/* 6103 */                                     out.write("</a><span class=\"mandatory\">*</span></label></td>\n            <td width=\"75%\" height=\"28\"> <input type=\"password\" name=\"password\" class=\"formtext default\" autocomplete=\"off\" /><!--");
/* 6104 */                                     if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 6106 */                                     out.write("-->\n             </td>\n          </tr>\n\t</table>\n\t</div>\n\t</td>\n\t</tr>\n\t<tr>\n\t<td colspan=\"2\">\n\t<div id=\"keyid\" style=\"display:none\">\n\t<table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t<tr>\n\t<TD  width=\"25%\" class=\"bodytext label-align addmonitor-label\">");
/* 6107 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssh.privateKey"));
/* 6108 */                                     out.write("<span class=\"mandatory\">*</span>\n\t</TD>\n\t<TD width=\"75%\">");
/* 6109 */                                     if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 6111 */                                     out.write(" </TD>\n\t</TR>\n\t</table>\n\t</div>\n\t</td>\n\t</tr>\n\t<tr>\n\t\t<td colspan=\"2\">\n\t\t<div id=\"passphraseid\" style=\"display:none\">\n\t\t<table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t  <tr>\n\t\t    <td width=\"25%\" height=\"28\" class=\"bodytext label-align addmonitor-label\">");
/* 6112 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.passphrase"));
/* 6113 */                                     out.write("</td>\n\t\t    <td width=\"75%\" height=\"28\"> <input type=\"password\" name=\"passphrase\" class=\"formtext normal\" size=\"15\" autocomplete=\"off\" />\n\t\t     </td>\n\t\t  </tr>\n\t\t</table>\n\t\t</div>\n\t\t</td>\n\t</tr>\n          <tr >\n            <td colspan=\"2\">\n\n            <div id=\"wmiid\" style=\"display:none\">\n\t    \t<table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t     <tr>\n\t     <script>\n\t      var host_dis_msg='");
/* 6114 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.message.commandprompt"));
/* 6115 */                                     out.write("';\n\t     </script>\n\n            <td height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,host_dis_msg,false,true,'#000000',450,'lightyellow')\" onMouseOut=\"hideddrivetip()\">");
/* 6116 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.commandprompt"));
/* 6117 */                                     out.write("</a></label></td>\n            <td height=\"28\" width=\"75%\">");
/* 6118 */                                     if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 6120 */                                     out.write("</td>\n          </tr>\n        </table>\n      </div></td>\n     </table>\n     </div></td>\n   </tr>\n   <tr>\n  <td height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 6121 */                                     out.print(FormatUtil.getString("am.webclient.script.port"));
/* 6122 */                                     out.write("</label></td>\n            <td height=\"28\" width=\"75%\">");
/* 6123 */                                     if (_jspx_meth_html_005ftext_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 6125 */                                     out.write("</td>\n\n\t     </tr>\n</table>\n</td></tr>\n");
/* 6126 */                                     if ((mtype.equals("file")) || (mtype.equals("directory")) || (mtype.equals("File System Monitor"))) {
/* 6127 */                                       out.write("\n <tr>\n\n    <td  height=\"35\" class=\"bodytext label-align addmonitor-label\"><div id=\"name\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,'");
/* 6128 */                                       out.print(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/* 6129 */                                       out.write("',false,true,'#000000',200,'lightyellow')\" onMouseOut=\"hideddrivetip()\">");
/* 6130 */                                       out.print(FormatUtil.getString("File/Directory Name"));
/* 6131 */                                       out.write("</a><span class=\"mandatory\">*</span></label></div>\n    </td>\n<td height=\"35\" width=\"81%\" colspan=\"2\" class=\"bodytext\">");
/*      */                                       
/* 6133 */                                       TextTag _jspx_th_html_005ftext_005f7 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(TextTag.class);
/* 6134 */                                       _jspx_th_html_005ftext_005f7.setPageContext(_jspx_page_context);
/* 6135 */                                       _jspx_th_html_005ftext_005f7.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 6137 */                                       _jspx_th_html_005ftext_005f7.setProperty("filepath");
/*      */                                       
/* 6139 */                                       _jspx_th_html_005ftext_005f7.setValue(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/*      */                                       
/* 6141 */                                       _jspx_th_html_005ftext_005f7.setStyleClass("formtext formtext-custom-filepath");
/*      */                                       
/* 6143 */                                       _jspx_th_html_005ftext_005f7.setOnclick("fnclear();");
/* 6144 */                                       int _jspx_eval_html_005ftext_005f7 = _jspx_th_html_005ftext_005f7.doStartTag();
/* 6145 */                                       if (_jspx_th_html_005ftext_005f7.doEndTag() == 5) {
/* 6146 */                                         this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005ftext_005f7); return;
/*      */                                       }
/*      */                                       
/* 6149 */                                       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 6150 */                                       out.write("\n      </td>\n  </tr>\n  <tr id=\"content\" style=\"display:none;\">\n  \t<td height=\"20\" width=\"25%\" class=\"bodytext label-align addmonitor-label\">&nbsp;</td>\n\t<td height=\"20\" width=\"75%\" class=\"bodytext\">");
/* 6151 */                                       if (_jspx_meth_html_005fcheckbox_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 6153 */                                       out.write("\n    ");
/* 6154 */                                       out.print(FormatUtil.getString("am.webclient.filedir.doconchk.text"));
/* 6155 */                                       out.write("</td>\n\t\t  \t  </tr>\n<tr id=\"contentChk11\" class=\"bg-lite\" style=\"display:none;\">\n\t<td colspan=\"2\" class=\"dottedline\">\n\t\t <table width=\"100%\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t \t<tr style=\"padding-top:5px\">\n\t\t    \t<td class=\"bodytext label-align addmonitor-label\" width=\"25%\" height=\"35\"><label>");
/* 6156 */                                       out.print(FormatUtil.getString("am.webclient.filedir.conParseType.text"));
/* 6157 */                                       out.write("</label></td>\n\t\t\t\t<td class=\"bodytext\" width=\"75%\" valign=\"middle\" height=\"35\">\n\t\t\t\t\t <table width=\"99%\" cellspacing=\"0\">\n\t\t  \t\t \t\t <tr style=\"padding-top:5px\" colspan=4>\n\t\t\t  \t\t \t\t <td height=\"20\" width=\"2%\" class=\"bodytext alignTdata\">  \n\t\t\t  \t\t \t\t \t");
/* 6158 */                                       if (_jspx_meth_html_005fradio_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 6160 */                                       out.write(" \n\t\t\t\t\t\t\t\t");
/* 6161 */                                       out.print(FormatUtil.getString("am.webclient.filedir.parseAppended.text"));
/* 6162 */                                       out.write(" &nbsp; &nbsp;\n\t\t\t\t\t\t\t\t");
/* 6163 */                                       if (_jspx_meth_html_005fradio_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 6165 */                                       out.write(" \n\t\t\t\t\t\t\t\t");
/* 6166 */                                       out.print(FormatUtil.getString("am.webclient.filedir.parseWhole.text"));
/* 6167 */                                       out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t  \t  \t\t</tr>\n\t  \t\t \t\t</table>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t\t\n\t\t    <tr style=\"padding-top:5px\">\n\t\t    \t<td  height=\"35\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 6168 */                                       out.print(FormatUtil.getString("am.webclient.filedir.conchk.text"));
/* 6169 */                                       out.write("&nbsp;<span class=\"mandatory\">*</span></label></td>\n\t\t \t\t<td  height=\"35\"  class=\"bodytext\">\n\t\t\t\t\t<table width=\"99%\" cellspacing=\"0\">\n\t\t\t\t\t\t<tr style=\"padding-top:5px\" colspan=2>\n\t\t\t\t\t\t\t<td height=\"20\" width=\"50%\" class=\"bodytext alignTdata\">\n\t\t\t\t\t\t\t\t");
/* 6170 */                                       if (_jspx_meth_html_005ftextarea_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 6172 */                                       out.write("\n\t\t\t\t\t\t\t\t");
/* 6173 */                                       if (_jspx_meth_html_005fcheckbox_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 6175 */                                       out.write("\n\t\t\t\t\t\t\t\t");
/* 6176 */                                       out.print(FormatUtil.getString("am.webclient.filedir.regexp.text"));
/* 6177 */                                       out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr id=\"checkEmptyId\" style=\"display: table-row;\">\n\t\t\t\t<td class=\"bodytext label-align addmonitor-label\" width=\"25%\">&nbsp;</td> \n\t\t\t\t<td colspan=\"4\">");
/* 6178 */                                       if (_jspx_meth_html_005fcheckbox_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 6180 */                                       out.print(FormatUtil.getString("am.webclient.filedir.donotCheckEmptyContent"));
/* 6181 */                                       out.write("</td>     \n\t\t\t</tr>\n\t\t\t<tr >\n\t\t    \t<td  height=\"35\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 6182 */                                       out.print(FormatUtil.getString("am.webclient.filedir.monstatus.text"));
/* 6183 */                                       out.write("</label></td>\n\t\t \t\t<td  height=\"35\" width=\"75%\" colspan=\"5\" class=\"bodytext alignTdata\">\n\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t\t\t\t\t<tr >\n\t\t\t\t\t\t\t<td width=\"10%\" class=\"alignTdata\">\n\t\t\t\t\t\t\t\t");
/*      */                                       
/* 6185 */                                       SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 6186 */                                       _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/* 6187 */                                       _jspx_th_html_005fselect_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 6189 */                                       _jspx_th_html_005fselect_005f4.setProperty("selectStatusType");
/*      */                                       
/* 6191 */                                       _jspx_th_html_005fselect_005f4.setStyleClass("formtext");
/*      */                                       
/* 6193 */                                       _jspx_th_html_005fselect_005f4.setOnchange("javascript:showClearCondnTitle()");
/* 6194 */                                       int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/* 6195 */                                       if (_jspx_eval_html_005fselect_005f4 != 0) {
/* 6196 */                                         if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 6197 */                                           out = _jspx_page_context.pushBody();
/* 6198 */                                           _jspx_th_html_005fselect_005f4.setBodyContent((BodyContent)out);
/* 6199 */                                           _jspx_th_html_005fselect_005f4.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 6202 */                                           out.write("\n\t\t\t\t\t\t\t\t\t  ");
/*      */                                           
/* 6204 */                                           OptionTag _jspx_th_html_005foption_005f68 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 6205 */                                           _jspx_th_html_005foption_005f68.setPageContext(_jspx_page_context);
/* 6206 */                                           _jspx_th_html_005foption_005f68.setParent(_jspx_th_html_005fselect_005f4);
/*      */                                           
/* 6208 */                                           _jspx_th_html_005foption_005f68.setKey(FormatUtil.getString("am.webclient.filedir.down.text"));
/*      */                                           
/* 6210 */                                           _jspx_th_html_005foption_005f68.setValue("0");
/* 6211 */                                           int _jspx_eval_html_005foption_005f68 = _jspx_th_html_005foption_005f68.doStartTag();
/* 6212 */                                           if (_jspx_th_html_005foption_005f68.doEndTag() == 5) {
/* 6213 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f68); return;
/*      */                                           }
/*      */                                           
/* 6216 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f68);
/* 6217 */                                           out.write("  \t \n     \t\t\t\t\t\t\t\t  ");
/*      */                                           
/* 6219 */                                           OptionTag _jspx_th_html_005foption_005f69 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 6220 */                                           _jspx_th_html_005foption_005f69.setPageContext(_jspx_page_context);
/* 6221 */                                           _jspx_th_html_005foption_005f69.setParent(_jspx_th_html_005fselect_005f4);
/*      */                                           
/* 6223 */                                           _jspx_th_html_005foption_005f69.setKey(FormatUtil.getString("am.webclient.filedir.up.text"));
/*      */                                           
/* 6225 */                                           _jspx_th_html_005foption_005f69.setValue("1");
/* 6226 */                                           int _jspx_eval_html_005foption_005f69 = _jspx_th_html_005foption_005f69.doStartTag();
/* 6227 */                                           if (_jspx_th_html_005foption_005f69.doEndTag() == 5) {
/* 6228 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f69); return;
/*      */                                           }
/*      */                                           
/* 6231 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f69);
/* 6232 */                                           out.write(" \n\t   \t\t\t\t\t\t\t\t    \t \n\t\t\t\t\t\t\t\t");
/* 6233 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/* 6234 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 6237 */                                         if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 6238 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 6241 */                                       if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/* 6242 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f4); return;
/*      */                                       }
/*      */                                       
/* 6245 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f4);
/* 6246 */                                       out.write("\n\t\t\t\t\t\t\t\t<div>\n\t\t\t\t\t\t\t\t<div id=\"regexChk1\"  style=\"display:none;\">\n\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t\t\t\t\t\t\t\t\t<tr >\n\t\t\t\t\t\t\t\t\t\t\t<td  height=\"20\" class=\"bodytext\" align=\"left\" >");
/* 6247 */                                       out.print(FormatUtil.getString("am.webclient.filedir.Regex.conmatch.text"));
/* 6248 */                                       out.write(".</td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t<div>\n\t\t\t\t\t\t\t\t<div id=\"NormalChk\" style=\"display: block;\">\n\t\t\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t\t\t\t\t\t\t\t\t<tr >\n\t\t\t\t\t\t\t\t\t\t\t<td width=\"3%\" height=\"20\" class=\"bodytext alignTdata\" align='left'>\n\t\t\t\t\t\t\t\t\t\t\t\t<span>");
/* 6249 */                                       out.print(FormatUtil.getString("am.webclient.filedir.if.text"));
/* 6250 */                                       out.write("</span>\n\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                       
/* 6252 */                                       SelectTag _jspx_th_html_005fselect_005f5 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 6253 */                                       _jspx_th_html_005fselect_005f5.setPageContext(_jspx_page_context);
/* 6254 */                                       _jspx_th_html_005fselect_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 6256 */                                       _jspx_th_html_005fselect_005f5.setProperty("selectRuleType");
/*      */                                       
/* 6258 */                                       _jspx_th_html_005fselect_005f5.setStyleClass("formtext");
/*      */                                       
/* 6260 */                                       _jspx_th_html_005fselect_005f5.setOnchange("javascript:loadTextBox()");
/* 6261 */                                       int _jspx_eval_html_005fselect_005f5 = _jspx_th_html_005fselect_005f5.doStartTag();
/* 6262 */                                       if (_jspx_eval_html_005fselect_005f5 != 0) {
/* 6263 */                                         if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 6264 */                                           out = _jspx_page_context.pushBody();
/* 6265 */                                           _jspx_th_html_005fselect_005f5.setBodyContent((BodyContent)out);
/* 6266 */                                           _jspx_th_html_005fselect_005f5.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 6269 */                                           out.write("  \t \n\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                           
/* 6271 */                                           OptionTag _jspx_th_html_005foption_005f70 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 6272 */                                           _jspx_th_html_005foption_005f70.setPageContext(_jspx_page_context);
/* 6273 */                                           _jspx_th_html_005foption_005f70.setParent(_jspx_th_html_005fselect_005f5);
/*      */                                           
/* 6275 */                                           _jspx_th_html_005foption_005f70.setKey(FormatUtil.getString("am.webclient.filedir.any.text"));
/*      */                                           
/* 6277 */                                           _jspx_th_html_005foption_005f70.setValue("0");
/* 6278 */                                           int _jspx_eval_html_005foption_005f70 = _jspx_th_html_005foption_005f70.doStartTag();
/* 6279 */                                           if (_jspx_th_html_005foption_005f70.doEndTag() == 5) {
/* 6280 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f70); return;
/*      */                                           }
/*      */                                           
/* 6283 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f70);
/* 6284 */                                           out.write(" \n\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                           
/* 6286 */                                           OptionTag _jspx_th_html_005foption_005f71 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 6287 */                                           _jspx_th_html_005foption_005f71.setPageContext(_jspx_page_context);
/* 6288 */                                           _jspx_th_html_005foption_005f71.setParent(_jspx_th_html_005fselect_005f5);
/*      */                                           
/* 6290 */                                           _jspx_th_html_005foption_005f71.setKey(FormatUtil.getString("am.webclient.filedir.all.text"));
/*      */                                           
/* 6292 */                                           _jspx_th_html_005foption_005f71.setValue("1");
/* 6293 */                                           int _jspx_eval_html_005foption_005f71 = _jspx_th_html_005foption_005f71.doStartTag();
/* 6294 */                                           if (_jspx_th_html_005foption_005f71.doEndTag() == 5) {
/* 6295 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f71); return;
/*      */                                           }
/*      */                                           
/* 6298 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f71);
/* 6299 */                                           out.write(" \n\t\t\t\t\t\t\t\t\t\t\t\t");
/* 6300 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f5.doAfterBody();
/* 6301 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 6304 */                                         if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 6305 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 6308 */                                       if (_jspx_th_html_005fselect_005f5.doEndTag() == 5) {
/* 6309 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f5); return;
/*      */                                       }
/*      */                                       
/* 6312 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f5);
/* 6313 */                                       out.write("\n\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t<div id=\"cntval11\" width=\"7%\" class=\"bodytext\" align='left'>\n\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"selectValue\" style=\"Display:none\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                       
/* 6315 */                                       SelectTag _jspx_th_html_005fselect_005f6 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 6316 */                                       _jspx_th_html_005fselect_005f6.setPageContext(_jspx_page_context);
/* 6317 */                                       _jspx_th_html_005fselect_005f6.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 6319 */                                       _jspx_th_html_005fselect_005f6.setProperty("countval");
/*      */                                       
/* 6321 */                                       _jspx_th_html_005fselect_005f6.setStyleClass("formtext");
/* 6322 */                                       int _jspx_eval_html_005fselect_005f6 = _jspx_th_html_005fselect_005f6.doStartTag();
/* 6323 */                                       if (_jspx_eval_html_005fselect_005f6 != 0) {
/* 6324 */                                         if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 6325 */                                           out = _jspx_page_context.pushBody();
/* 6326 */                                           _jspx_th_html_005fselect_005f6.setBodyContent((BodyContent)out);
/* 6327 */                                           _jspx_th_html_005fselect_005f6.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 6330 */                                           out.write("  \t \n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                           
/* 6332 */                                           for (int i = 1; i < contentChkCount; i++) {
/* 6333 */                                             String cval = "" + i;
/*      */                                             
/* 6335 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                             
/* 6337 */                                             OptionTag _jspx_th_html_005foption_005f72 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 6338 */                                             _jspx_th_html_005foption_005f72.setPageContext(_jspx_page_context);
/* 6339 */                                             _jspx_th_html_005foption_005f72.setParent(_jspx_th_html_005fselect_005f6);
/*      */                                             
/* 6341 */                                             _jspx_th_html_005foption_005f72.setKey(cval);
/*      */                                             
/* 6343 */                                             _jspx_th_html_005foption_005f72.setValue(cval);
/* 6344 */                                             int _jspx_eval_html_005foption_005f72 = _jspx_th_html_005foption_005f72.doStartTag();
/* 6345 */                                             if (_jspx_th_html_005foption_005f72.doEndTag() == 5) {
/* 6346 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f72); return;
/*      */                                             }
/*      */                                             
/* 6349 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f72);
/* 6350 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                           }
/* 6352 */                                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 6353 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f6.doAfterBody();
/* 6354 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 6357 */                                         if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 6358 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 6361 */                                       if (_jspx_th_html_005fselect_005f6.doEndTag() == 5) {
/* 6362 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f6); return;
/*      */                                       }
/*      */                                       
/* 6365 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f6);
/* 6366 */                                       out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t");
/* 6367 */                                       out.print(FormatUtil.getString("am.webclient.filedir.conmatch.text"));
/* 6368 */                                       out.write(".\n\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\n\t\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t\t\n\t\t\t<tr>\n\t\t    \t<td valign=\"top\" class=\"bodytext\" width=\"25%\" height=\"40\"></td>\n\t\t\t\t<td class=\"bodytext\" width=\"75%\" valign=\"top\" height=\"35\">\n\t\t\t\t<fieldset class=\"legendField\">\n  \t\t\t\t\t<legend id=\"ClearCondnUp\" style=\"display:''\">");
/* 6369 */                                       out.print(FormatUtil.getString("am.webclient.filedir.conmatch.clear.condn.up.text"));
/* 6370 */                                       out.write("</legend>\n  \t\t\t\t\t<legend id=\"ClearCondnDown\" style=\"display:none\">");
/* 6371 */                                       out.print(FormatUtil.getString("am.webclient.filedir.conmatch.clear.condn.down.text"));
/* 6372 */                                       out.write("</legend>\n\t\t\t\t\t\t<table width=\"99%\" cellpadding=\"0\">\n\t\t\t\t  \t\t \t<tr>\n\t\t\t\t\t  \t\t\t<td class=\"bodytext alignTdata\">\n\t\t\t\t\t\t  \t\t\t");
/* 6373 */                                       if (_jspx_meth_html_005fradio_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 6375 */                                       out.write("\n\t\t\t\t\t\t  \t\t \t");
/* 6376 */                                       out.print(FormatUtil.getString("am.webclient.filedir.conmatch.clear.not.matched.text"));
/* 6377 */                                       out.write("\n\t\t\t\t\t  \t\t \t</td>\n\t\t\t\t\t  \t\t</tr>\n\t\t\t\t\t  \t\t<tr>\n\t\t\t\t\t  \t\t\t<td height=\"40\" width=\"1%\" class=\"bodytext alignTdata\">\n\t\t\t\t\t  \t\t\t");
/* 6378 */                                       if (_jspx_meth_html_005fradio_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 6380 */                                       out.write("\n\t\t\t\t\t  \t\t \t\n\t\t\t\t\t  \t\t \t\n\t\t\t\t\t\t\t\t<div id=\"regexChk2\" style=\"display:none;\">\n\t\t\t\t\t\t\t\t\t<table width=\"50%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td height=\"20\" class=\"bodytext\" align=\"left\">");
/* 6381 */                                       out.print(FormatUtil.getString("am.webclient.filedir.Regex.conmatch.text"));
/* 6382 */                                       out.write(".</td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t<div>\n\t\t\t\t\t\t\t\t<div id=\"NormalChk2\" style=\"display: block;\">\n\t\t\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td width=\"3%\" height=\"20\" class=\"bodytext alignTdata\" align=\"left\">\n\t\t\t\t\t\t\t\t\t\t\t\t<span>");
/* 6383 */                                       out.print(FormatUtil.getString("am.webclient.filedir.if.text"));
/* 6384 */                                       out.write("</span>\n\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                       
/* 6386 */                                       SelectTag _jspx_th_html_005fselect_005f7 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 6387 */                                       _jspx_th_html_005fselect_005f7.setPageContext(_jspx_page_context);
/* 6388 */                                       _jspx_th_html_005fselect_005f7.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 6390 */                                       _jspx_th_html_005fselect_005f7.setProperty("clearConditionRuleType");
/*      */                                       
/* 6392 */                                       _jspx_th_html_005fselect_005f7.setOnchange("javascript:loadTextBox1()");
/*      */                                       
/* 6394 */                                       _jspx_th_html_005fselect_005f7.setStyleClass("formtext");
/* 6395 */                                       int _jspx_eval_html_005fselect_005f7 = _jspx_th_html_005fselect_005f7.doStartTag();
/* 6396 */                                       if (_jspx_eval_html_005fselect_005f7 != 0) {
/* 6397 */                                         if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 6398 */                                           out = _jspx_page_context.pushBody();
/* 6399 */                                           _jspx_th_html_005fselect_005f7.setBodyContent((BodyContent)out);
/* 6400 */                                           _jspx_th_html_005fselect_005f7.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 6403 */                                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                           
/* 6405 */                                           OptionTag _jspx_th_html_005foption_005f73 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 6406 */                                           _jspx_th_html_005foption_005f73.setPageContext(_jspx_page_context);
/* 6407 */                                           _jspx_th_html_005foption_005f73.setParent(_jspx_th_html_005fselect_005f7);
/*      */                                           
/* 6409 */                                           _jspx_th_html_005foption_005f73.setKey(FormatUtil.getString("am.webclient.filedir.any.text"));
/*      */                                           
/* 6411 */                                           _jspx_th_html_005foption_005f73.setValue("0");
/* 6412 */                                           int _jspx_eval_html_005foption_005f73 = _jspx_th_html_005foption_005f73.doStartTag();
/* 6413 */                                           if (_jspx_th_html_005foption_005f73.doEndTag() == 5) {
/* 6414 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f73); return;
/*      */                                           }
/*      */                                           
/* 6417 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f73);
/* 6418 */                                           out.write(" \n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                           
/* 6420 */                                           OptionTag _jspx_th_html_005foption_005f74 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 6421 */                                           _jspx_th_html_005foption_005f74.setPageContext(_jspx_page_context);
/* 6422 */                                           _jspx_th_html_005foption_005f74.setParent(_jspx_th_html_005fselect_005f7);
/*      */                                           
/* 6424 */                                           _jspx_th_html_005foption_005f74.setKey(FormatUtil.getString("am.webclient.filedir.all.text"));
/*      */                                           
/* 6426 */                                           _jspx_th_html_005foption_005f74.setValue("1");
/* 6427 */                                           int _jspx_eval_html_005foption_005f74 = _jspx_th_html_005foption_005f74.doStartTag();
/* 6428 */                                           if (_jspx_th_html_005foption_005f74.doEndTag() == 5) {
/* 6429 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f74); return;
/*      */                                           }
/*      */                                           
/* 6432 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f74);
/* 6433 */                                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t");
/* 6434 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f7.doAfterBody();
/* 6435 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 6438 */                                         if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 6439 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 6442 */                                       if (_jspx_th_html_005fselect_005f7.doEndTag() == 5) {
/* 6443 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f7); return;
/*      */                                       }
/*      */                                       
/* 6446 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f7);
/* 6447 */                                       out.write("\n\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t<div id=\"cntval22\" width=\"7%\" class=\"bodytext\" align=\"left\">\n\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"selectValue2\" style=\"display: block;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                       
/* 6449 */                                       SelectTag _jspx_th_html_005fselect_005f8 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 6450 */                                       _jspx_th_html_005fselect_005f8.setPageContext(_jspx_page_context);
/* 6451 */                                       _jspx_th_html_005fselect_005f8.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 6453 */                                       _jspx_th_html_005fselect_005f8.setProperty("clearConditionCountVal");
/*      */                                       
/* 6455 */                                       _jspx_th_html_005fselect_005f8.setStyleClass("formtext");
/* 6456 */                                       int _jspx_eval_html_005fselect_005f8 = _jspx_th_html_005fselect_005f8.doStartTag();
/* 6457 */                                       if (_jspx_eval_html_005fselect_005f8 != 0) {
/* 6458 */                                         if (_jspx_eval_html_005fselect_005f8 != 1) {
/* 6459 */                                           out = _jspx_page_context.pushBody();
/* 6460 */                                           _jspx_th_html_005fselect_005f8.setBodyContent((BodyContent)out);
/* 6461 */                                           _jspx_th_html_005fselect_005f8.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 6464 */                                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                           
/* 6466 */                                           for (int i = 1; i < contentChkCount; i++) {
/* 6467 */                                             String cval = "" + i;
/*      */                                             
/* 6469 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                             
/* 6471 */                                             OptionTag _jspx_th_html_005foption_005f75 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 6472 */                                             _jspx_th_html_005foption_005f75.setPageContext(_jspx_page_context);
/* 6473 */                                             _jspx_th_html_005foption_005f75.setParent(_jspx_th_html_005fselect_005f8);
/*      */                                             
/* 6475 */                                             _jspx_th_html_005foption_005f75.setKey(cval);
/*      */                                             
/* 6477 */                                             _jspx_th_html_005foption_005f75.setValue(cval);
/* 6478 */                                             int _jspx_eval_html_005foption_005f75 = _jspx_th_html_005foption_005f75.doStartTag();
/* 6479 */                                             if (_jspx_th_html_005foption_005f75.doEndTag() == 5) {
/* 6480 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f75); return;
/*      */                                             }
/*      */                                             
/* 6483 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f75);
/* 6484 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                           }
/* 6486 */                                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 6487 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f8.doAfterBody();
/* 6488 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 6491 */                                         if (_jspx_eval_html_005fselect_005f8 != 1) {
/* 6492 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 6495 */                                       if (_jspx_th_html_005fselect_005f8.doEndTag() == 5) {
/* 6496 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f8); return;
/*      */                                       }
/*      */                                       
/* 6499 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f8);
/* 6500 */                                       out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t");
/* 6501 */                                       if (_jspx_meth_html_005ftextarea_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 6503 */                                       out.write("<span class=\"mandatory\" style=\"vertical-align: top;\">*</span>\n\t\t\t\t\t\t\t\t\t\t\t");
/* 6504 */                                       out.print(FormatUtil.getString("am.webclient.filedir.conmatch.text"));
/* 6505 */                                       out.write(".\n\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t  \t\t\t</tr>\n\t\t  \t\t\t\t</table>\n\t  \t\t\t\t</fieldset>\n\t\t\t\t</td>\n\t\t\t</tr>\t\n\t\t\t\n\t\t\t\t\t\n\t\t </table>\n\t</td>\n</tr>\n<tr >\n  \t<td height=\"20\" width=\"25%\" class=\"bodytext label-align addmonitor-label\">&nbsp;</td>\n  \t<td height=\"20\" width=\"75%\" class=\"bodytext\">");
/* 6506 */                                       if (_jspx_meth_html_005fcheckbox_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 6508 */                                       out.write("\n    ");
/* 6509 */                                       out.print(FormatUtil.getString("am.webclient.fileDirAgeChk.text"));
/* 6510 */                                       out.write("</td>\n  </tr>\n  <tr id=\"fileDirAge11\" class=\"bg-lite\" style=\"display:none;\" >\n  \n  \t<td colspan=\"2\" class=\"dottedline\">\n\t\t\n\t\t <table width=\"100%\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t\t<tr>\n\t\t    \t<td  height=\"35\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 6511 */                                       out.print(FormatUtil.getString("am.webclient.filedir.monstatus.text"));
/* 6512 */                                       out.write("</label></td>\n\t\t \t\t<td  height=\"35\" width=\"75%\" colspan=\"6\" class=\"bodytext\">\n\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"5%\" height=\"20\" class=\"bodytext\">\n\t\t\t\t\t\t\t\t");
/*      */                                       
/* 6514 */                                       SelectTag _jspx_th_html_005fselect_005f9 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 6515 */                                       _jspx_th_html_005fselect_005f9.setPageContext(_jspx_page_context);
/* 6516 */                                       _jspx_th_html_005fselect_005f9.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 6518 */                                       _jspx_th_html_005fselect_005f9.setProperty("selectMonStatus");
/*      */                                       
/* 6520 */                                       _jspx_th_html_005fselect_005f9.setStyleClass("formtext");
/* 6521 */                                       int _jspx_eval_html_005fselect_005f9 = _jspx_th_html_005fselect_005f9.doStartTag();
/* 6522 */                                       if (_jspx_eval_html_005fselect_005f9 != 0) {
/* 6523 */                                         if (_jspx_eval_html_005fselect_005f9 != 1) {
/* 6524 */                                           out = _jspx_page_context.pushBody();
/* 6525 */                                           _jspx_th_html_005fselect_005f9.setBodyContent((BodyContent)out);
/* 6526 */                                           _jspx_th_html_005fselect_005f9.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 6529 */                                           out.write(" \n\t\t\t\t\t\t\t\t\t  ");
/*      */                                           
/* 6531 */                                           OptionTag _jspx_th_html_005foption_005f76 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 6532 */                                           _jspx_th_html_005foption_005f76.setPageContext(_jspx_page_context);
/* 6533 */                                           _jspx_th_html_005foption_005f76.setParent(_jspx_th_html_005fselect_005f9);
/*      */                                           
/* 6535 */                                           _jspx_th_html_005foption_005f76.setKey(FormatUtil.getString("am.webclient.filedir.down.text"));
/*      */                                           
/* 6537 */                                           _jspx_th_html_005foption_005f76.setValue("0");
/* 6538 */                                           int _jspx_eval_html_005foption_005f76 = _jspx_th_html_005foption_005f76.doStartTag();
/* 6539 */                                           if (_jspx_th_html_005foption_005f76.doEndTag() == 5) {
/* 6540 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f76); return;
/*      */                                           }
/*      */                                           
/* 6543 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f76);
/* 6544 */                                           out.write("  \t \t \n     \t\t\t\t\t\t\t\t  ");
/*      */                                           
/* 6546 */                                           OptionTag _jspx_th_html_005foption_005f77 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 6547 */                                           _jspx_th_html_005foption_005f77.setPageContext(_jspx_page_context);
/* 6548 */                                           _jspx_th_html_005foption_005f77.setParent(_jspx_th_html_005fselect_005f9);
/*      */                                           
/* 6550 */                                           _jspx_th_html_005foption_005f77.setKey(FormatUtil.getString("am.webclient.filedir.up.text"));
/*      */                                           
/* 6552 */                                           _jspx_th_html_005foption_005f77.setValue("1");
/* 6553 */                                           int _jspx_eval_html_005foption_005f77 = _jspx_th_html_005foption_005f77.doStartTag();
/* 6554 */                                           if (_jspx_th_html_005foption_005f77.doEndTag() == 5) {
/* 6555 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f77); return;
/*      */                                           }
/*      */                                           
/* 6558 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f77);
/* 6559 */                                           out.write(" \n\t   \t\t\t\t\t\t\t\t \n\t\t\t\t\t\t\t\t");
/* 6560 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f9.doAfterBody();
/* 6561 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 6564 */                                         if (_jspx_eval_html_005fselect_005f9 != 1) {
/* 6565 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 6568 */                                       if (_jspx_th_html_005fselect_005f9.doEndTag() == 5) {
/* 6569 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f9); return;
/*      */                                       }
/*      */                                       
/* 6572 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f9);
/* 6573 */                                       out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td width=\"20%\" height=\"20\" class=\"bodytext\" align='center'>");
/* 6574 */                                       out.print(FormatUtil.getString("am.webclient.fileDirAgeChk.if.text"));
/* 6575 */                                       out.write(" </td>\n\t\t\t\t\t\t\t<td width=\"18%\" height=\"20\" class=\"bodytext\" >\n\t\t\t\t\t\t\t\t");
/*      */                                       
/* 6577 */                                       SelectTag _jspx_th_html_005fselect_005f10 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 6578 */                                       _jspx_th_html_005fselect_005f10.setPageContext(_jspx_page_context);
/* 6579 */                                       _jspx_th_html_005fselect_005f10.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 6581 */                                       _jspx_th_html_005fselect_005f10.setProperty("selectChangeType");
/*      */                                       
/* 6583 */                                       _jspx_th_html_005fselect_005f10.setStyleClass("formtext");
/* 6584 */                                       int _jspx_eval_html_005fselect_005f10 = _jspx_th_html_005fselect_005f10.doStartTag();
/* 6585 */                                       if (_jspx_eval_html_005fselect_005f10 != 0) {
/* 6586 */                                         if (_jspx_eval_html_005fselect_005f10 != 1) {
/* 6587 */                                           out = _jspx_page_context.pushBody();
/* 6588 */                                           _jspx_th_html_005fselect_005f10.setBodyContent((BodyContent)out);
/* 6589 */                                           _jspx_th_html_005fselect_005f10.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 6592 */                                           out.write("  \t \n\t\t\t\t\t\t\t\t\t\t");
/*      */                                           
/* 6594 */                                           OptionTag _jspx_th_html_005foption_005f78 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 6595 */                                           _jspx_th_html_005foption_005f78.setPageContext(_jspx_page_context);
/* 6596 */                                           _jspx_th_html_005foption_005f78.setParent(_jspx_th_html_005fselect_005f10);
/*      */                                           
/* 6598 */                                           _jspx_th_html_005foption_005f78.setKey(FormatUtil.getString("am.webclient.fileDirAgeChk.NotModified.text"));
/*      */                                           
/* 6600 */                                           _jspx_th_html_005foption_005f78.setValue("0");
/* 6601 */                                           int _jspx_eval_html_005foption_005f78 = _jspx_th_html_005foption_005f78.doStartTag();
/* 6602 */                                           if (_jspx_th_html_005foption_005f78.doEndTag() == 5) {
/* 6603 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f78); return;
/*      */                                           }
/*      */                                           
/* 6606 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f78);
/* 6607 */                                           out.write(" \n       \t\t\t\t\t\t\t\t\t");
/*      */                                           
/* 6609 */                                           OptionTag _jspx_th_html_005foption_005f79 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 6610 */                                           _jspx_th_html_005foption_005f79.setPageContext(_jspx_page_context);
/* 6611 */                                           _jspx_th_html_005foption_005f79.setParent(_jspx_th_html_005fselect_005f10);
/*      */                                           
/* 6613 */                                           _jspx_th_html_005foption_005f79.setKey(FormatUtil.getString("am.webclient.fileDirAgeChk.Modified.text"));
/*      */                                           
/* 6615 */                                           _jspx_th_html_005foption_005f79.setValue("1");
/* 6616 */                                           int _jspx_eval_html_005foption_005f79 = _jspx_th_html_005foption_005f79.doStartTag();
/* 6617 */                                           if (_jspx_th_html_005foption_005f79.doEndTag() == 5) {
/* 6618 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f79); return;
/*      */                                           }
/*      */                                           
/* 6621 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f79);
/* 6622 */                                           out.write(" \n\t   \t\t\t\t\t\t\t");
/* 6623 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f10.doAfterBody();
/* 6624 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 6627 */                                         if (_jspx_eval_html_005fselect_005f10 != 1) {
/* 6628 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 6631 */                                       if (_jspx_th_html_005fselect_005f10.doEndTag() == 5) {
/* 6632 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f10); return;
/*      */                                       }
/*      */                                       
/* 6635 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f10);
/* 6636 */                                       out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td width=\"8%\" class=\"bodytext\" align='left'>\n\t\t\t\t\t\t\t\t");
/* 6637 */                                       out.print(FormatUtil.getString("am.webclient.fileDirAgeChk.within.text"));
/* 6638 */                                       out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td width=\"8%\" class=\"bodytext\" align='left' >");
/* 6639 */                                       if (_jspx_meth_html_005ftext_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 6641 */                                       out.write("</td>\n\t\t\t\t\t\t\t<td width=\"52%\" class=\"bodytext\" >\n\t\t\t\t\t\t\t\t");
/*      */                                       
/* 6643 */                                       SelectTag _jspx_th_html_005fselect_005f11 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 6644 */                                       _jspx_th_html_005fselect_005f11.setPageContext(_jspx_page_context);
/* 6645 */                                       _jspx_th_html_005fselect_005f11.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 6647 */                                       _jspx_th_html_005fselect_005f11.setProperty("timeUnit");
/*      */                                       
/* 6649 */                                       _jspx_th_html_005fselect_005f11.setStyleClass("formtext");
/* 6650 */                                       int _jspx_eval_html_005fselect_005f11 = _jspx_th_html_005fselect_005f11.doStartTag();
/* 6651 */                                       if (_jspx_eval_html_005fselect_005f11 != 0) {
/* 6652 */                                         if (_jspx_eval_html_005fselect_005f11 != 1) {
/* 6653 */                                           out = _jspx_page_context.pushBody();
/* 6654 */                                           _jspx_th_html_005fselect_005f11.setBodyContent((BodyContent)out);
/* 6655 */                                           _jspx_th_html_005fselect_005f11.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 6658 */                                           out.write("  \t \n\t\t\t\t\t\t\t\t\t\t");
/*      */                                           
/* 6660 */                                           OptionTag _jspx_th_html_005foption_005f80 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 6661 */                                           _jspx_th_html_005foption_005f80.setPageContext(_jspx_page_context);
/* 6662 */                                           _jspx_th_html_005foption_005f80.setParent(_jspx_th_html_005fselect_005f11);
/*      */                                           
/* 6664 */                                           _jspx_th_html_005foption_005f80.setKey(FormatUtil.getString("am.webclient.fileDirAgeChk.mins.text"));
/*      */                                           
/* 6666 */                                           _jspx_th_html_005foption_005f80.setValue("Minutes");
/* 6667 */                                           int _jspx_eval_html_005foption_005f80 = _jspx_th_html_005foption_005f80.doStartTag();
/* 6668 */                                           if (_jspx_th_html_005foption_005f80.doEndTag() == 5) {
/* 6669 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f80); return;
/*      */                                           }
/*      */                                           
/* 6672 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f80);
/* 6673 */                                           out.write(" \n       \t\t\t\t\t\t\t\t\t");
/*      */                                           
/* 6675 */                                           OptionTag _jspx_th_html_005foption_005f81 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 6676 */                                           _jspx_th_html_005foption_005f81.setPageContext(_jspx_page_context);
/* 6677 */                                           _jspx_th_html_005foption_005f81.setParent(_jspx_th_html_005fselect_005f11);
/*      */                                           
/* 6679 */                                           _jspx_th_html_005foption_005f81.setKey(FormatUtil.getString("am.webclient.fileDirAgeChk.hrs.text"));
/*      */                                           
/* 6681 */                                           _jspx_th_html_005foption_005f81.setValue("Hours");
/* 6682 */                                           int _jspx_eval_html_005foption_005f81 = _jspx_th_html_005foption_005f81.doStartTag();
/* 6683 */                                           if (_jspx_th_html_005foption_005f81.doEndTag() == 5) {
/* 6684 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f81); return;
/*      */                                           }
/*      */                                           
/* 6687 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f81);
/* 6688 */                                           out.write(" \n\t   \t\t\t\t\t\t\t");
/* 6689 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f11.doAfterBody();
/* 6690 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 6693 */                                         if (_jspx_eval_html_005fselect_005f11 != 1) {
/* 6694 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 6697 */                                       if (_jspx_th_html_005fselect_005f11.doEndTag() == 5) {
/* 6698 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f11); return;
/*      */                                       }
/*      */                                       
/* 6701 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f11);
/* 6702 */                                       out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t </table>\n\t\t\n\t</td>\n  </tr>\n   <tr id=\"subDirCountChk\" style=\"display:none;\">\n  \t<td height=\"20\" width=\"25%\" class=\"bodytext label-align addmonitor-label\">&nbsp;</td>\n  \t<td height=\"20\" width=\"75%\" class=\"bodytext\">");
/* 6703 */                                       if (_jspx_meth_html_005fcheckbox_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 6705 */                                       out.write("\n    ");
/* 6706 */                                       out.print(FormatUtil.getString("am.webclient.subDirCount.text"));
/* 6707 */                                       out.write("</td>\n  </tr>\n");
/*      */                                     } else {
/* 6709 */                                       out.write(10);
/* 6710 */                                       out.write(10);
/* 6711 */                                       out.write(32);
/* 6712 */                                       if ((!mtype.equals("file")) && (!mtype.equals("directory")) && (!mtype.equals("File System Monitor"))) {
/* 6713 */                                         out.write("\n\t<tr>\n\t    <td class=\"bodytext label-align addmonitor-label\"><label>");
/* 6714 */                                         out.print(FormatUtil.getString("Script Location"));
/* 6715 */                                         out.write("</label></td>\n\t     <td class=\"bodytext\" width=\"75%\" valign=\"middle\">\n\t\t     <table width=\"99%\" cellspacing=\"0\">\n\t\t\t\t <tr style=\"padding-top:5px\" colspan=4>\n\t\t\t\t\t  <td height=\"20\" width=\"2%\" class=\"bodytext\">\n\t\t\t\t\t\t");
/* 6716 */                                         if (_jspx_meth_html_005fradio_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 6718 */                                         out.write("\n\t\t\t\t\t  </td>\n\t\t\t\t\t  <td height=\"20\" width=\"14%\" class=\"bodytext\">");
/* 6719 */                                         out.print(FormatUtil.getString("am.webclient.script.command"));
/* 6720 */                                         out.write("</td>\n\t\t\t\t\t  <td height=\"20\" width=\"2%\" class=\"bodytext\">");
/* 6721 */                                         if (_jspx_meth_html_005fradio_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 6723 */                                         out.write("</td>\n\t\t\t\t\t  <td height=\"20\" width=\"81%\" class=\"bodytext\">");
/* 6724 */                                         out.print(FormatUtil.getString("am.webclient.script.script"));
/* 6725 */                                         out.write("</td>\n\t\t\t\t</tr>\n\t\t     </table>\n\t     </td>\n\t</tr>\n\t");
/*      */                                       }
/* 6727 */                                       out.write("\n\n  <tr>\n    <td  height=\"35\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,'");
/* 6728 */                                       out.print(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/* 6729 */                                       out.write("',false,true,'#000000',200,'lightyellow')\" onMouseOut=\"hideddrivetip()\">");
/* 6730 */                                       out.print(FormatUtil.getString("am.webclient.newscript.scripttomonitor.text"));
/* 6731 */                                       out.write("</a><span class=\"mandatory\">*</span></label>\n    </td>\n    <td height=\"35\" width=\"75%\" colspan=\"2\" class=\"bodytext\">");
/* 6732 */                                       if (_jspx_meth_html_005ftext_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 6734 */                                       out.write("\n\t<span class=\"footer\">");
/* 6735 */                                       out.print(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/* 6736 */                                       out.write("</span></td>\n  </tr>\n  <tr>\n    <td  height=\"35\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,'");
/* 6737 */                                       out.print(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/* 6738 */                                       out.write("',false,true,'#000000',200,'lightyellow')\" onMouseOut=\"hideddrivetip()\">");
/* 6739 */                                       out.print(FormatUtil.getString("am.webclient.newscript.scriptdirectory.text"));
/* 6740 */                                       out.write("</a><span class=\"mandatory\">*</span></label>\n    </td>\n    <td height=\"35\" width=\"75%\" colspan=\"2\" class=\"bodytext\">");
/* 6741 */                                       if (_jspx_meth_html_005ftext_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 6743 */                                       out.write("\n\t<span class=\"footer\">");
/* 6744 */                                       out.print(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/* 6745 */                                       out.write("</span>\n      </td>\n  </tr>\n ");
/* 6746 */                                       if ((!mtype.equals("file")) && (!mtype.equals("directory")) && (!mtype.equals("File System Monitor")))
/* 6747 */                                         if ((!System.getProperty("os.name").startsWith("windows")) && (!System.getProperty("os.name").startsWith("Windows"))) {
/* 6748 */                                           out.write("\n\t  <tr>\n\t    <td height=\"20\" class=\"bodytext\">");
/* 6749 */                                           out.print(FormatUtil.getString("am.webclient.newscript.enterthemode.text"));
/* 6750 */                                           out.write("<span class=\"mandatory\"></span></td>\n\t    <td height=\"20\"> ");
/* 6751 */                                           if (_jspx_meth_html_005ftext_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 6753 */                                           out.write("\n\t    </td>\n\t  </tr>\n  \t");
/*      */                                         } else {
/* 6755 */                                           out.write("\n\t<tr>\n\t\t<td colspan=\"2\">\n\t\t\t<div id=\"windowsmode\" style=\"display:block;\">\n\t\t\t\t<table border=\"0\" cellpadding=\"6\" width=\"99%\">\n\t\t\t\t\t<tr>\n\t    \t\t\t\t\t<td height=\"20\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 6756 */                                           out.print(FormatUtil.getString("am.webclient.newscript.enterthemode.text"));
/* 6757 */                                           out.write("<span class=\"mandatory\"></span></label></td>\n\t\t    \t\t\t\t<td height=\"20\"> ");
/* 6758 */                                           if (_jspx_meth_html_005ftext_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 6760 */                                           out.write("</td>\n\t\t\t\t  \t</tr>\n\t\t\t\t</table>\n\t\t\t</div>\n\t\t</td>\n\t</tr>\n\n\t");
/*      */                                         }
/* 6762 */                                       out.write("\n  \n  ");
/*      */                                       
/* 6764 */                                       if ((customType != null) && (customType.equals("true")))
/*      */                                       {
/*      */ 
/* 6767 */                                         out.write("\n      <tr class=\"yellowgrayborder\">\n      \t    <td class=\"bodytext label-align addmonitor-label\" width=\"25%\">&nbsp;</td>\n      \t     <td colspan=\"4\"><input type=\"checkbox\" name=\"isFile\" value=\"true\" onClick=\"javascript:enableDisableFile()\">\n      \t     ");
/* 6768 */                                         out.print(FormatUtil.getString("am.script.output.from.file"));
/* 6769 */                                         out.write("</td>\n          </tr>\n      <tr>\n      \n    <td  height=\"35\" class=\"bodytext label-align addmonitor-label\"><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,'");
/* 6770 */                                         out.print(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/* 6771 */                                         out.write("',false,true,'#000000',200,'lightyellow')\" onMouseOut=\"hideddrivetip()\">");
/* 6772 */                                         out.print(FormatUtil.getString("am.webclient.newscript.outputfile.text"));
/* 6773 */                                         out.write("</a><span class=\"mandatory\">*</span>\n    </td>\n    <td height=\"35\" width=\"75%\" colspan=\"2\" class=\"bodytext\">");
/* 6774 */                                         if (_jspx_meth_html_005ftext_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 6776 */                                         out.write("\n\t<span class=\"footer\">");
/* 6777 */                                         out.print(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/* 6778 */                                         out.write("</span>\n      </td>\n  </tr>\n      ");
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 6784 */                                         out.write("\n  <tr>\n    <td class=\"bodytext label-align addmonitor-label\" width=\"25%\">&nbsp;</td>\n     <td width=\"75%\">");
/* 6785 */                                         if (_jspx_meth_html_005fcheckbox_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 6787 */                                         out.write("\n      ");
/* 6788 */                                         out.print(FormatUtil.getString("am.webclient.newscript.outputsettings.text"));
/* 6789 */                                         out.write(" </td>\n  </tr>\n  <tr>\n    <td colspan=\"2\" class=\"cellpadd-none\">\n<div id=\"opdetails\" style=\"display:block;\">\n        <table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\"  class=\"bg-lite dottedline\">\n          <tr class=\"yellowgrayborder\">\n\t    <td class=\"bodytext label-align addmonitor-label\" width=\"25%\">&nbsp;</td>\n\t     <td colspan=\"4\"><input type=\"checkbox\" name=\"isFile\" value=\"true\" onClick=\"javascript:enableDisableFile()\">\n\t     ");
/* 6790 */                                         out.print(FormatUtil.getString("am.script.output.from.file"));
/* 6791 */                                         out.write("</td>\n          </tr>\n        \n          <tr class=\"yellowgrayborder\">\n            <td width=\"25%\" height=\"34\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,'");
/* 6792 */                                         out.print(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/* 6793 */                                         out.write("',false,true,'#000000',200,'lightyellow')\" onMouseOut=\"hideddrivetip()\">");
/* 6794 */                                         out.print(FormatUtil.getString("am.webclient.newscript.outputfile.text"));
/* 6795 */                                         out.write("</a><span class=\"mandatory\"></span></label></td>\n            <td height=\"34\" colspan=\"4\" class=\"bodytext\" width=\"75%\" valign=\"top\">");
/* 6796 */                                         if (_jspx_meth_html_005ftext_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 6798 */                                         out.write("\n\t\t\t<span class=\"footer\">");
/* 6799 */                                         out.print(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/* 6800 */                                         out.write("</span>\n            </td>\n          </tr>\n          <tr class=\"yellowgrayborder\">\n            <td width=\"25%\" height=\"29\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,'");
/* 6801 */                                         out.print(FormatUtil.getString("am.webclient.newscript.stringattributeshelp.text"));
/* 6802 */                                         out.write("',false,true,'#000000',300,'lightyellow')\" onMouseOut=\"hideddrivetip()\">");
/* 6803 */                                         out.print(FormatUtil.getString("String Attributes"));
/* 6804 */                                         out.write("</a></label></td>\n            <td height=\"29\"  width=\"35%\" class=\"bodytext\" valign=\"top\">\n              ");
/* 6805 */                                         if (_jspx_meth_html_005ftextarea_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 6807 */                                         out.write("</td>\n\t\t\t <td width=\"40%\" colspan=\"3\"><span class=\"footer\">");
/* 6808 */                                         out.print(FormatUtil.getString("am.webclient.newscript.stringattributeshelp.text"));
/* 6809 */                                         out.write("</span></td>\n          </tr>\n          <tr class=\"yellowgrayborder\">\n            <td width=\"25%\" height=\"35\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,'");
/* 6810 */                                         out.print(FormatUtil.getString("am.webclient.newscript.numericattributeshelp.text"));
/* 6811 */                                         out.write("',false,true,'#000000',200,'lightyellow')\" onMouseOut=\"hideddrivetip()\">");
/* 6812 */                                         out.print(FormatUtil.getString("Numeric Attributes"));
/* 6813 */                                         out.write("</a></label></td>\n            <td height=\"35\" width=\"35%\" class=\"bodytext\" valign=\"top\">\n              ");
/* 6814 */                                         if (_jspx_meth_html_005ftextarea_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 6816 */                                         out.write("</td>\n\t\t\t  <td width=\"40%\" colspan=\"3\"><span class=\"footer\">");
/* 6817 */                                         out.print(FormatUtil.getString("am.webclient.newscript.numericattributeshelp.text"));
/* 6818 */                                         out.write("</span></td>\n          </tr>\n          <tr class=\"yellowgrayborder\">\n            <td width=\"25%\" height=\"35\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 6819 */                                         out.print(FormatUtil.getString("Delimiter"));
/* 6820 */                                         out.write("<span class=\"mandatory\"></span></label>\n            </td>\n            <td height=\"35\" colspan=\"4\" width=\"75%\" class=\"bodytext\" valign=\"top\">");
/* 6821 */                                         if (_jspx_meth_html_005ftext_005f15(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 6823 */                                         out.write("\n            </td>\n          </tr>\n          <tr class=\"yellowgrayborder\">\n            <td class=\"bodytext\"><input type=\"checkbox\" name=\"tablespresent\" onclick=javascript:showTables()>");
/* 6824 */                                         out.print(FormatUtil.getString("am.webclient.newscript.tablesinoutput.text"));
/* 6825 */                                         out.write("</td>\n            <td class=\"bodytext\"><img src=\"../images/icon_quicknote_help.gif\" width=\"15\" height=\"11\"  border=\"0\" align=\"absmiddle\" ><a href=\"/help/managing-business-applications/script-monitor.html#example\" target=\"_blank\" class=\"selectedmenu\"> ");
/* 6826 */                                         out.print(FormatUtil.getString("am.webclient.hometab.availabilitycheck.helpmessage"));
/* 6827 */                                         out.write("</a></td>\n            <td class=\"bodytext\">&nbsp;</td>\n            <td class=\"bodytext\">&nbsp;</td>\n            <td class=\"bodytext\">&nbsp;</td>\n          </tr>\n          <tr class=\"yellowgrayborder\">\n            <td colspan=\"5\"> <div id=\"tableid\" style=\"display:block;\">\n                <table  width=\"100%\" border=\"0\" >\n                  <tbody id=\"filtertable\">\n                    <tr class=\"yellowgrayborder\">\n                      <td width=\"25%\" height=\"34\" class=\"bodytext\"  >");
/* 6828 */                                         out.print(FormatUtil.getString("Table Name"));
/* 6829 */                                         out.write("<span class=\"mandatory\"></span></td>\n                      <td width=\"20%\" height=\"34\" class=\"bodytext\">");
/* 6830 */                                         out.print(FormatUtil.getString("Numeric Attributes"));
/* 6831 */                                         out.write("</td>\n                      <td width=\"20%\" height=\"35\" class=\"bodytext\" >");
/* 6832 */                                         out.print(FormatUtil.getString("String Attributes"));
/* 6833 */                                         out.write("</td>\n                      <td width=\"19%\" height=\"35\" class=\"bodytext\" >");
/* 6834 */                                         out.print(FormatUtil.getString("Unique Column"));
/* 6835 */                                         out.write("\n                      </td>\n                      <td width=\"16%\" height=\"35\" class=\"bodytext\" >");
/* 6836 */                                         out.print(FormatUtil.getString("Column Delimiter"));
/* 6837 */                                         out.write("<span class=\"mandatory\"></span>\n                      </td>\n                    </tr>\n                    ");
/* 6838 */                                         if (tableids.size() <= 0)
/*      */                                         {
/* 6840 */                                           out.write("\n                    <tr class=\"yellowgrayborder\">\n                      <td height=\"35\" class=\"\" valign=\"top\" width=\"25%\"><input type=\"textbox\" name=\"table1\" id=\"tab1\" value=\"\" class=\"formtext medium\"></td>\n                      <td height=\"35\" width=\"20%\"  class=\"bodytext\" valign=\"top\"><textarea cols=\"15\" name=\"numericatt1\" id=\"na1\"  class=\"formtextarea medium\"></textarea></td>\n                      <td height=\"35\" width=\"20%\" class=\"bodytext\" valign=\"top\"><textarea cols=\"15\" name=\"stringatt1\" id=\"sa1\" class=\"formtextarea medium\"></textarea></td>\n                      <td width=\"19%\" class=\"bodytext\" valign=\"top\"><textarea cols=\"15\" name=\"pcatt1\" id=\"pri_col_att1\" class=\"formtextarea medium\"></textarea></td>\n                      <td height=\"35\" width=\"16%\" class=\"bodytext\" valign=\"top\"><input type=\"textbox\" size=\"2\" name=\"cdl1\" id=\"col_del1\" class=\"formtext vsmall\"></td>\n                    </tr>\n                     ");
/*      */                                         }
/*      */                                         
/* 6843 */                                         if (tableids.size() > 0)
/*      */                                         {
/* 6845 */                                           for (int i = 0; i < tableids.size(); i++)
/*      */                                           {
/* 6847 */                                             String tabname = "table" + (i + 1);
/* 6848 */                                             String tabid = "tab" + (i + 1);
/* 6849 */                                             String sattname = "stringatt" + (i + 1);
/* 6850 */                                             String sattid = "sa" + (i + 1);
/* 6851 */                                             String nattname = "numericatt" + (i + 1);
/* 6852 */                                             String nattid = "na" + (i + 1);
/* 6853 */                                             String pcolname = "pcatt" + (i + 1);
/* 6854 */                                             String pcolid = "pri_col_att" + (i + 1);
/* 6855 */                                             String delname = "cdl" + (i + 1);
/* 6856 */                                             String delid = "col_del" + (i + 1);
/* 6857 */                                             String filterrow = "filterrow" + (i + 1);
/*      */                                             
/* 6859 */                                             out.write("\n                                                    <tr class=\"whitegrayborder\" id=\"");
/* 6860 */                                             out.print(filterrow);
/* 6861 */                                             out.write("\" border=\"0\">\n                                                        <td height=\"35\" class=\"\" valign=\"top\" width=\"20%\"><input type=\"text\" name=\"");
/* 6862 */                                             out.print(tabname);
/* 6863 */                                             out.write("\" id=\"");
/* 6864 */                                             out.print(tabid);
/* 6865 */                                             out.write("\"  value=\"");
/* 6866 */                                             out.print(((Hashtable)table_atts.get(tableids.get(i))).get("name"));
/* 6867 */                                             out.write("\" class=\"formtext medium\"></td>\n                                                        <td height=\"35\" width=\"20%\"  class=\"bodytext\" valign=\"top\"><textarea cols=\"15\" name=\"");
/* 6868 */                                             out.print(nattname);
/* 6869 */                                             out.write("\" id=\"");
/* 6870 */                                             out.print(nattid);
/* 6871 */                                             out.write("\"  class=\"formtextarea medium\">");
/* 6872 */                                             out.print(((Hashtable)table_atts.get(tableids.get(i))).get("numerics").toString().trim());
/* 6873 */                                             out.write("</textarea></td>\n                                                        <td height=\"35\" width=\"20%\" class=\"bodytext\" valign=\"top\"><textarea cols=\"15\" name=\"");
/* 6874 */                                             out.print(sattname);
/* 6875 */                                             out.write("\" id=\"");
/* 6876 */                                             out.print(sattid);
/* 6877 */                                             out.write("\"  class=\"formtextarea medium\">");
/* 6878 */                                             out.print(((Hashtable)table_atts.get(tableids.get(i))).get("strings").toString().trim());
/* 6879 */                                             out.write("</textarea></td>\n                                                        <td height=\"35\" width=\"20%\" class=\"bodytext\" valign=\"top\"><textarea cols=\"15\" name=\"");
/* 6880 */                                             out.print(pcolname);
/* 6881 */                                             out.write("\" id=\"");
/* 6882 */                                             out.print(pcolid);
/* 6883 */                                             out.write("\" class=\"formtextarea medium\">");
/* 6884 */                                             out.print(((Hashtable)table_atts.get(tableids.get(i))).get("primary").toString().trim());
/* 6885 */                                             out.write("</textarea></td>\n                                                        <td height=\"35\" width=\"5%\" class=\"bodytext\" valign=\"top\"><input type=\"text\" name=\"");
/* 6886 */                                             out.print(delname);
/* 6887 */                                             out.write("\" id=\"");
/* 6888 */                                             out.print(delid);
/* 6889 */                                             out.write("\"  value=\"");
/* 6890 */                                             out.print(((Hashtable)table_atts.get(tableids.get(i))).get("delimiter").toString().trim());
/* 6891 */                                             out.write("\" class=\"formtext vsmall\"></td>\n\t\t\t\t\n                                                      </tr>\n                                                    ");
/*      */                                           }
/*      */                                         }
/*      */                                         
/*      */ 
/* 6896 */                                         out.write("\n\n\n                  </tbody>\n                </table>\n                <table>\n                  <tr>\n                    <td width=\"20%\"><a class=\"staticlinks\" href=\"javascript:addFilterRow(++count,'yellowgrayborder')\">");
/* 6897 */                                         out.print(FormatUtil.getString("am.webclient.maintenance.more"));
/* 6898 */                                         out.write("</a></td>\n\t\t\t\t\t<td width=\"20%\" colspan=4><a class=\"staticlinks\" href=\"javascript:callremove(count--)\">");
/* 6899 */                                         out.print(FormatUtil.getString("am.webclient.maintenance.fewer"));
/* 6900 */                                         out.write("</a></td>\n                  </tr>\n                </table>\n              </div></td>\n          </tr>\n        </table>\n\t<table>\n</table>\n      </div></td>\n  </tr>\n  ");
/*      */                                       }
/*      */                                       
/*      */ 
/* 6904 */                                       out.write("\n  <tr>\n  <script>\n  \tvar scriptArgSeg='");
/* 6905 */                                       out.print(FormatUtil.getString("am.webclient.newscript.argseg.text"));
/* 6906 */                                       out.write("';\n  </script>\n    <td height=\"35\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,scriptArgSeg,false,true,'#000000',200,'lightyellow');\" onMouseOut=\"hideddrivetip()\">");
/* 6907 */                                       out.print(FormatUtil.getString("am.webclient.newscript.arguments.text"));
/* 6908 */                                       out.write("</a></label></td>\n    <td height=\"35\" colspan=\"2\" class=\"bodytext\"> ");
/* 6909 */                                       if (_jspx_meth_html_005ftext_005f16(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 6911 */                                       out.write("\n      <span class=\"footer\">");
/* 6912 */                                       out.print(FormatUtil.getString("am.webclient.newscript.argseg.text"));
/* 6913 */                                       out.write("</span></td>\n    <!--value=\"appmanager,port,check\"-->\n  </tr>\n");
/*      */                                     }
/* 6915 */                                     out.write("\n  <tr>\n\n    <td height=\"26\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 6916 */                                     out.print(FormatUtil.getString("am.webclient.common.pollinginterval.text"));
/* 6917 */                                     out.write("\n      <span class=\"mandatory\">*</span></label></td>\n");
/* 6918 */                                     if ((mtype.equals("file")) || (mtype.equals("directory")) || (mtype.equals("File System Monitor"))) {
/* 6919 */                                       out.write("\n    <td height=\"26\" class=\"bodytext\"> ");
/* 6920 */                                       if (_jspx_meth_html_005ftext_005f17(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 6922 */                                       out.write(10);
/*      */                                     } else {
/* 6924 */                                       out.write("\n    <td height=\"26\" class=\"bodytext\"> ");
/* 6925 */                                       if (_jspx_meth_html_005ftext_005f18(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 6927 */                                       out.write(10);
/*      */                                     }
/* 6929 */                                     out.write("\n      ");
/* 6930 */                                     out.print(FormatUtil.getString("am.webclient.urlmonitor.unitofpoll.text"));
/* 6931 */                                     out.write("</td>\n  </tr>\n\n  <tr>\n\t\t");
/* 6932 */                                     String tout = null;
/* 6933 */                                     String toutval = null;
/* 6934 */                                     if ((mtype.equals("file")) || (mtype.equals("directory")) || (mtype.equals("File System Monitor"))) {
/* 6935 */                                       tout = "am.webclient.newfsm.insecondshelp.text";
/* 6936 */                                       toutval = "60";
/*      */                                     }
/*      */                                     else {
/* 6939 */                                       tout = "am.webclient.newscript.insecondshelp.text";
/* 6940 */                                       toutval = "30";
/*      */                                     }
/* 6942 */                                     out.write("\n    <td height=\"20\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,'");
/* 6943 */                                     out.print(FormatUtil.getString(tout));
/* 6944 */                                     out.write("',false,true,'#000000',400,'lightyellow')\" onMouseOut=\"hideddrivetip()\">");
/* 6945 */                                     out.print(FormatUtil.getString("am.webclient.newscript.tiemout.text"));
/* 6946 */                                     out.write("</a><span class=\"mandatory\">*</span></label></td>\n    <td height=\"20\" colspan=\"2\"> ");
/*      */                                     
/* 6948 */                                     TextTag _jspx_th_html_005ftext_005f19 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6949 */                                     _jspx_th_html_005ftext_005f19.setPageContext(_jspx_page_context);
/* 6950 */                                     _jspx_th_html_005ftext_005f19.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 6952 */                                     _jspx_th_html_005ftext_005f19.setProperty("timeout");
/*      */                                     
/* 6954 */                                     _jspx_th_html_005ftext_005f19.setValue(toutval);
/*      */                                     
/* 6956 */                                     _jspx_th_html_005ftext_005f19.setStyleClass("formtext small");
/* 6957 */                                     int _jspx_eval_html_005ftext_005f19 = _jspx_th_html_005ftext_005f19.doStartTag();
/* 6958 */                                     if (_jspx_th_html_005ftext_005f19.doEndTag() == 5) {
/* 6959 */                                       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f19); return;
/*      */                                     }
/*      */                                     
/* 6962 */                                     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f19);
/* 6963 */                                     out.write("<span class=\"bodytext\"> ");
/* 6964 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.second"));
/* 6965 */                                     out.write("</span>\n     </td>\n  </tr>\n</table>\n\n");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/*      */ 
/* 6971 */                                     out.write("\n<TABLE width=\"99%\" BORDER=\"0\" cellpadding=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\">\n  <tr>\n    <td align=\"center\">\n      <input name=\"closeButton\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 6972 */                                     out.print(FormatUtil.getString("Close Window"));
/* 6973 */                                     out.write("\" onClick=\"closePopUpWindow();\">\n      ");
/* 6974 */                                     if (!isDiscoverySuccess) {
/* 6975 */                                       out.write("\n              ");
/*      */                                       
/* 6977 */                                       ResetTag _jspx_th_html_005freset_005f0 = (ResetTag)this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.get(ResetTag.class);
/* 6978 */                                       _jspx_th_html_005freset_005f0.setPageContext(_jspx_page_context);
/* 6979 */                                       _jspx_th_html_005freset_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 6981 */                                       _jspx_th_html_005freset_005f0.setStyleClass("buttons btn_back");
/*      */                                       
/* 6983 */                                       _jspx_th_html_005freset_005f0.setValue(FormatUtil.getString("am.webclient.goback.readd.txt"));
/*      */                                       
/* 6985 */                                       _jspx_th_html_005freset_005f0.setOnclick("javascript:history.back();");
/* 6986 */                                       int _jspx_eval_html_005freset_005f0 = _jspx_th_html_005freset_005f0.doStartTag();
/* 6987 */                                       if (_jspx_th_html_005freset_005f0.doEndTag() == 5) {
/* 6988 */                                         this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f0); return;
/*      */                                       }
/*      */                                       
/* 6991 */                                       this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f0);
/* 6992 */                                       out.write("\n      ");
/*      */                                     }
/* 6994 */                                     out.write("\n      </td>\n      </tr>\n      </table>\n");
/*      */                                   }
/*      */                                   
/*      */ 
/* 6998 */                                   if (EnterpriseUtil.isAdminServer())
/*      */                                   {
/*      */ 
/* 7001 */                                     out.write(10);
/* 7002 */                                     out.write(9);
/* 7003 */                                     out.write(9);
/* 7004 */                                     JspRuntimeLibrary.include(request, response, "/jsp/includes/ShowGroupsDetails.jsp" + ("/jsp/includes/ShowGroupsDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("showDetailsOf", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("managedServerGroups", request.getCharacterEncoding()), out, false);
/* 7005 */                                     out.write(10);
/*      */                                   }
/*      */                                   
/* 7008 */                                   if (wiz3 == null)
/*      */                                   {
/*      */ 
/* 7011 */                                     out.write(10);
/* 7012 */                                     out.write(9);
/* 7013 */                                     out.write(9);
/* 7014 */                                     JspRuntimeLibrary.include(request, response, "/jsp/includes/ShowGroupsDetails.jsp" + ("/jsp/includes/ShowGroupsDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("showDetailsOf", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("monitorGroups", request.getCharacterEncoding()), out, false);
/* 7015 */                                     out.write(10);
/*      */                                     
/* 7017 */                                     if ((!hideFields) || ((!isDiscoveryComplete) && (hideFields)))
/*      */                                     {
/* 7019 */                                       String addmonitors = FormatUtil.getString("am.webclient.newscript.addmonitors.text");
/* 7020 */                                       String restoredefaults = FormatUtil.getString("am.webclient.global.Reset.text");
/* 7021 */                                       String cancel = FormatUtil.getString("am.webclient.common.cancel.text");
/*      */                                       
/* 7023 */                                       out.write("\n<table id=\"SubmitDiscoveryDetails\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n  <td width=\"25%\" class=\"tablebottom\"> </td>\n    <td align=\"left\" class=\"tablebottom\">\n      <input name=\"button1\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 7024 */                                       out.print(addmonitors);
/* 7025 */                                       out.write("\" onClick=\"validateAndSubmit(1);\">\n      &nbsp; ");
/*      */                                       
/* 7027 */                                       ResetTag _jspx_th_html_005freset_005f1 = (ResetTag)this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.get(ResetTag.class);
/* 7028 */                                       _jspx_th_html_005freset_005f1.setPageContext(_jspx_page_context);
/* 7029 */                                       _jspx_th_html_005freset_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 7031 */                                       _jspx_th_html_005freset_005f1.setStyleClass("buttons btn_reset");
/*      */                                       
/* 7033 */                                       _jspx_th_html_005freset_005f1.setValue(restoredefaults);
/* 7034 */                                       int _jspx_eval_html_005freset_005f1 = _jspx_th_html_005freset_005f1.doStartTag();
/* 7035 */                                       if (_jspx_th_html_005freset_005f1.doEndTag() == 5) {
/* 7036 */                                         this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.reuse(_jspx_th_html_005freset_005f1); return;
/*      */                                       }
/*      */                                       
/* 7039 */                                       this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.reuse(_jspx_th_html_005freset_005f1);
/* 7040 */                                       out.write(" &nbsp; <input type=\"button\" value=\"");
/* 7041 */                                       out.print(cancel);
/* 7042 */                                       out.write("\" onClick=\"history.back();\" class=\"buttons btn_link\" id=\"cancelButtonMod\"/>\n    </td>\n  </tr>\n\n</table>\n");
/*      */                                     }
/*      */                                     
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 7048 */                                     String back = FormatUtil.getString("webclient.common.backlink.text");
/* 7049 */                                     String create = FormatUtil.getString("am.webclient.hostdiscovery.create");
/* 7050 */                                     String create_add = FormatUtil.getString("am.webclient.hostdiscovery.button.createandaddmore");
/* 7051 */                                     String finish = FormatUtil.getString("am.webclient.hostdiscovery.button.finish");
/* 7052 */                                     String configure_alerts = FormatUtil.getString("am.webclient.hostdiscovery.button.configurealert");
/*      */                                     
/* 7054 */                                     out.write("\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n\t<tr>\n\t<td width=\"25%\" align=\"left\"  class=\"tablebottom\">&nbsp;</td>\n\t<td width=\"75%\" height=\"31\" align=\"left\"  class=\"tablebottom\">\n\t<input type=hidden name=\"haid\" value=\"");
/* 7055 */                                     out.print(haid);
/* 7056 */                                     out.write("\" />\n        <input type=\"button\" name=\"xx2\" value=\"");
/* 7057 */                                     out.print(back);
/* 7058 */                                     out.write("\" class=\"buttons btn_link\"  onClick=\"javascript:location.href='/showresource.do?method=associateMonitors&haid=");
/* 7059 */                                     if (_jspx_meth_c_005fout_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 7061 */                                     out.write("&wiz=true'\">\n        <input name=\"addcustomapp1\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 7062 */                                     out.print(create);
/* 7063 */                                     out.write("\" onClick=\"validateAndSubmit(3)\"/>\n\t<input name=\"addcustomapp\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 7064 */                                     out.print(create_add);
/* 7065 */                                     out.write("\" onClick=\"validateAndSubmit(1)\"/>\n\t");
/*      */                                     
/* 7067 */                                     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7068 */                                     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 7069 */                                     _jspx_th_c_005fif_005f8.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 7071 */                                     _jspx_th_c_005fif_005f8.setTest("${!empty associatedmonitors}");
/* 7072 */                                     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 7073 */                                     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                       for (;;) {
/* 7075 */                                         out.write("\n\t<input type=\"button\" name=\"xx\" value=\"");
/* 7076 */                                         out.print(configure_alerts);
/* 7077 */                                         out.write("\" class=\"buttons btn_highlt\"  onClick=\"javascript:location.href='/showActionProfiles.do?method=getHAProfiles&haid=");
/* 7078 */                                         if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*      */                                           return;
/* 7080 */                                         out.write("&wiz=true'\">\n\t");
/* 7081 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 7082 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 7086 */                                     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 7087 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                                     }
/*      */                                     
/* 7090 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 7091 */                                     out.write("\n\t<input type=\"button\" name=\"xx1\" value=\"");
/* 7092 */                                     out.print(finish);
/* 7093 */                                     out.write("\" class=\"buttons btn_highlt\"  onClick=\"javascript:location.href='/showapplication.do?method=showApplication&haid=");
/* 7094 */                                     if (_jspx_meth_c_005fout_005f16(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 7096 */                                     out.write("'\">\n\t</td>\n\t</tr>\n\t</table>\n\t<table class=\"lrbborder\" width=\"99%\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\">\n\t<!--<td width=\"28\" class=\"bodytext\"><b>Back : </b>It takes you to the second step to select monitor type.</td></tr>-->\n        <tr><td  class=\"bodytext\">");
/* 7097 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.createdesc"));
/* 7098 */                                     out.write("</td></tr>\n\t<tr><td  class=\"bodytext\">");
/* 7099 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.createandaddmoredesc"));
/* 7100 */                                     out.write("</td></tr>\n\t");
/*      */                                     
/* 7102 */                                     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7103 */                                     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 7104 */                                     _jspx_th_c_005fif_005f9.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 7106 */                                     _jspx_th_c_005fif_005f9.setTest("${!empty associatedmonitors}");
/* 7107 */                                     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 7108 */                                     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                       for (;;) {
/* 7110 */                                         out.write("\n\t<tr><td  class=\"bodytext\">");
/* 7111 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.configurealertdesc"));
/* 7112 */                                         out.write("</td></tr>\n\t");
/* 7113 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 7114 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 7118 */                                     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 7119 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                     }
/*      */                                     
/* 7122 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 7123 */                                     out.write("\n\t<tr><td  class=\"bodytext\">");
/* 7124 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.finishdesc"));
/* 7125 */                                     out.write("</td></tr>\n\t</tr>\n   </table>\n");
/*      */                                   }
/* 7127 */                                   out.write(10);
/* 7128 */                                   out.write(10);
/* 7129 */                                   out.write(10);
/* 7130 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 7131 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 7135 */                               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 7136 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                               }
/*      */                               
/* 7139 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 7140 */                               out.write("\n</td>\n<td width=\"30%\" valign=\"top\">\n");
/* 7141 */                               if ((((String)request.getAttribute("type")).equals("file")) || (((String)request.getAttribute("type")).equals("Script Monitor")) || (((String)request.getAttribute("type")).equals("directory")) || (((String)request.getAttribute("type")).equals("File System Monitor"))) {
/* 7142 */                                 String osName = System.getProperty("os.name");
/* 7143 */                                 StringBuffer helpcardKey = new StringBuffer();
/*      */                                 
/* 7145 */                                 if (((String)request.getAttribute("type")).equals("Script Monitor"))
/*      */                                 {
/* 7147 */                                   helpcardKey.append(FormatUtil.getString("am.webclient.scripttonmt.help") + ".<br>");
/* 7148 */                                   helpcardKey.append(FormatUtil.getString("am.webclient.scriptmonitor.text", new String[] { OEMUtil.getOEMString("product.name") }));
/*      */                                 } else {
/* 7150 */                                   helpcardKey.append("<ul>");
/* 7151 */                                   helpcardKey.append("<li>" + FormatUtil.getString("am.webclient.pathhelpcard.text") + "</li><br>");
/* 7152 */                                   helpcardKey.append("<li>" + FormatUtil.getString("am.webclient.datefile.text") + "</li><br>");
/* 7153 */                                   helpcardKey.append("<li>" + FormatUtil.getString("am.webclient.patternfile.text") + "</li><br>");
/* 7154 */                                   helpcardKey.append("<li>" + FormatUtil.getString("am.webclient.ccontent.text") + "</li><br>");
/* 7155 */                                   helpcardKey.append("<li>" + FormatUtil.getString("am.webclient.contenthelpcard.text") + "</li><br>");
/* 7156 */                                   helpcardKey.append("<li>" + FormatUtil.getString("am.webclient.readper.text") + "</li><br>");
/* 7157 */                                   helpcardKey.append("<li>" + FormatUtil.getString("am.webclient.subDirCount.helpcard.text") + "</li><br>");
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/* 7162 */                                   helpcardKey.append("</ul>");
/*      */                                 }
/*      */                                 
/*      */ 
/* 7166 */                                 out.write(10);
/* 7167 */                                 out.write(10);
/* 7168 */                                 JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(helpcardKey.toString()), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()), out, false);
/* 7169 */                                 out.write("\n\t\t\n\n\t\t\n\n\n");
/*      */                               }
/*      */                               
/*      */ 
/* 7173 */                               out.write("\n</td>\n        </tr>\n        </table>\n");
/* 7174 */                               int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 7175 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 7178 */                             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 7179 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 7182 */                           if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 7183 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                           }
/*      */                           
/* 7186 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 7187 */                           out.write(10);
/* 7188 */                           if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 7190 */                           out.write(10);
/* 7191 */                           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 7192 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 7196 */                       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 7197 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0); return;
/*      */                       }
/*      */                       
/* 7200 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 7201 */                       out.write("\n</body>\n");
/*      */                     }
/*      */                     catch (Exception ex)
/*      */                     {
/* 7205 */                       ex.printStackTrace();
/*      */                     }
/*      */                     
/* 7208 */                     out.write(10);
/* 7209 */                     out.write(10);
/*      */                     
/* 7211 */                     if (hideFields)
/*      */                     {
/*      */ 
/* 7214 */                       out.write("\n\t<script>\n\t\t$(document.body).ready(function(){\n\t\tdocument.getElementById(\"cancelButtonMod\").onclick = null;\n\t\t$(\"#cancelButtonMod\").click(function(){ //No I18N\n\t\t\tclosePopUpWindow();\n\t\t});\n\t\t});\n\t</script>\n");
/*      */                     }
/*      */                     
/*      */ 
/* 7218 */                     out.write(10);
/*      */                   }
/* 7220 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 7221 */         out = _jspx_out;
/* 7222 */         if ((out != null) && (out.getBufferSize() != 0))
/* 7223 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 7224 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 7227 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7233 */     PageContext pageContext = _jspx_page_context;
/* 7234 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7236 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 7237 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 7238 */     _jspx_th_c_005fforEach_005f0.setParent(null);
/*      */     
/* 7240 */     _jspx_th_c_005fforEach_005f0.setItems("${dynamicSites}");
/*      */     
/* 7242 */     _jspx_th_c_005fforEach_005f0.setVar("a");
/*      */     
/* 7244 */     _jspx_th_c_005fforEach_005f0.setVarStatus("rowCounter");
/* 7245 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 7247 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 7248 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 7250 */           out.write(10);
/* 7251 */           out.write(9);
/* 7252 */           out.write(9);
/* 7253 */           if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 7254 */             return true;
/* 7255 */           out.write("\n\t\tif(formCustomerId == customerId)\n\t\t{\n\t\t\tdocument.AMActionForm.haid.options[rowCount++] = new Option(siteName,siteId);\n\t\t}\n\t");
/* 7256 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 7257 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 7261 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 7262 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 7265 */         int tmp206_205 = 0; int[] tmp206_203 = _jspx_push_body_count_c_005fforEach_005f0; int tmp208_207 = tmp206_203[tmp206_205];tmp206_203[tmp206_205] = (tmp208_207 - 1); if (tmp208_207 <= 0) break;
/* 7266 */         out = _jspx_page_context.popBody(); }
/* 7267 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 7269 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 7270 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 7272 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7277 */     PageContext pageContext = _jspx_page_context;
/* 7278 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7280 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 7281 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 7282 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 7284 */     _jspx_th_c_005fforEach_005f1.setItems("${a}");
/*      */     
/* 7286 */     _jspx_th_c_005fforEach_005f1.setVar("b");
/*      */     
/* 7288 */     _jspx_th_c_005fforEach_005f1.setVarStatus("rowCounter1");
/* 7289 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 7291 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 7292 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 7294 */           out.write("\n\t\t\t");
/* 7295 */           boolean bool; if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7296 */             return true;
/* 7297 */           out.write("\n\t\t\t");
/* 7298 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7299 */             return true;
/* 7300 */           out.write("\n\t\t\t");
/* 7301 */           if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7302 */             return true;
/* 7303 */           out.write(10);
/* 7304 */           out.write(9);
/* 7305 */           out.write(9);
/* 7306 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 7307 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 7311 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 7312 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 7315 */         int tmp295_294 = 0; int[] tmp295_292 = _jspx_push_body_count_c_005fforEach_005f1; int tmp297_296 = tmp295_292[tmp295_294];tmp295_292[tmp295_294] = (tmp297_296 - 1); if (tmp297_296 <= 0) break;
/* 7316 */         out = _jspx_page_context.popBody(); }
/* 7317 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 7319 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 7320 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 7322 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7327 */     PageContext pageContext = _jspx_page_context;
/* 7328 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7330 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7331 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 7332 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 7334 */     _jspx_th_c_005fif_005f0.setTest("${rowCounter1.count == 1}");
/* 7335 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 7336 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 7338 */         out.write("\n\t\t\t\tsiteName = '");
/* 7339 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7340 */           return true;
/* 7341 */         out.write("';\n\t\t\t");
/* 7342 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 7343 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7347 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 7348 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 7349 */       return true;
/*      */     }
/* 7351 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 7352 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7357 */     PageContext pageContext = _jspx_page_context;
/* 7358 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7360 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7361 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 7362 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 7364 */     _jspx_th_c_005fout_005f0.setValue("${b}");
/* 7365 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 7366 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 7367 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 7368 */       return true;
/*      */     }
/* 7370 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 7371 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7376 */     PageContext pageContext = _jspx_page_context;
/* 7377 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7379 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7380 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 7381 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 7383 */     _jspx_th_c_005fif_005f1.setTest("${rowCounter1.count == 2}");
/* 7384 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 7385 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 7387 */         out.write("\n\t\t\t\tsiteId = '");
/* 7388 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7389 */           return true;
/* 7390 */         out.write("';\n\t\t\t");
/* 7391 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 7392 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7396 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 7397 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 7398 */       return true;
/*      */     }
/* 7400 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 7401 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7406 */     PageContext pageContext = _jspx_page_context;
/* 7407 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7409 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7410 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 7411 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 7413 */     _jspx_th_c_005fout_005f1.setValue("${b}");
/* 7414 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 7415 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 7416 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 7417 */       return true;
/*      */     }
/* 7419 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 7420 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7425 */     PageContext pageContext = _jspx_page_context;
/* 7426 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7428 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7429 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 7430 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 7432 */     _jspx_th_c_005fif_005f2.setTest("${rowCounter1.count == 3}");
/* 7433 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 7434 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 7436 */         out.write("\n\t\t\t\tcustomerId = '");
/* 7437 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7438 */           return true;
/* 7439 */         out.write("';\n\t\t\t");
/* 7440 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 7441 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7445 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 7446 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 7447 */       return true;
/*      */     }
/* 7449 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 7450 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7455 */     PageContext pageContext = _jspx_page_context;
/* 7456 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7458 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7459 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 7460 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 7462 */     _jspx_th_c_005fout_005f2.setValue("${b}");
/* 7463 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 7464 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 7465 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 7466 */       return true;
/*      */     }
/* 7468 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 7469 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7474 */     PageContext pageContext = _jspx_page_context;
/* 7475 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7477 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7478 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 7479 */     _jspx_th_c_005fout_005f3.setParent(null);
/*      */     
/* 7481 */     _jspx_th_c_005fout_005f3.setValue("${contentChkCount}");
/* 7482 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 7483 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 7484 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 7485 */       return true;
/*      */     }
/* 7487 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 7488 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7493 */     PageContext pageContext = _jspx_page_context;
/* 7494 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7496 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7497 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 7498 */     _jspx_th_c_005fout_005f4.setParent(null);
/*      */     
/* 7500 */     _jspx_th_c_005fout_005f4.setValue("${contentChkCount}");
/* 7501 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 7502 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 7503 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 7504 */       return true;
/*      */     }
/* 7506 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 7507 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7512 */     PageContext pageContext = _jspx_page_context;
/* 7513 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7515 */     ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 7516 */     _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/* 7517 */     _jspx_th_c_005fforEach_005f2.setParent(null);
/*      */     
/* 7519 */     _jspx_th_c_005fforEach_005f2.setItems("${credentialHash.SSH}");
/*      */     
/* 7521 */     _jspx_th_c_005fforEach_005f2.setVar("credential");
/* 7522 */     int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */     try {
/* 7524 */       int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/* 7525 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */         for (;;) {
/* 7527 */           out.write("\n\t\t\t\tdocument.AMActionForm.credentialID.options[i] = new Option('");
/* 7528 */           boolean bool; if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 7529 */             return true;
/* 7530 */           out.write(39);
/* 7531 */           out.write(44);
/* 7532 */           out.write(39);
/* 7533 */           if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 7534 */             return true;
/* 7535 */           out.write("',0,0);\n\t\t\t\ti++;\n\t  \t\t ");
/* 7536 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/* 7537 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 7541 */       if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/* 7542 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 7545 */         int tmp237_236 = 0; int[] tmp237_234 = _jspx_push_body_count_c_005fforEach_005f2; int tmp239_238 = tmp237_234[tmp237_236];tmp237_234[tmp237_236] = (tmp239_238 - 1); if (tmp239_238 <= 0) break;
/* 7546 */         out = _jspx_page_context.popBody(); }
/* 7547 */       _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */     } finally {
/* 7549 */       _jspx_th_c_005fforEach_005f2.doFinally();
/* 7550 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
/*      */     }
/* 7552 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 7557 */     PageContext pageContext = _jspx_page_context;
/* 7558 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7560 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7561 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 7562 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 7564 */     _jspx_th_c_005fout_005f5.setValue("${credential.value}");
/* 7565 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 7566 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 7567 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 7568 */       return true;
/*      */     }
/* 7570 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 7571 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 7576 */     PageContext pageContext = _jspx_page_context;
/* 7577 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7579 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7580 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 7581 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 7583 */     _jspx_th_c_005fout_005f6.setValue("${credential.key}");
/* 7584 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 7585 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 7586 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 7587 */       return true;
/*      */     }
/* 7589 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 7590 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7595 */     PageContext pageContext = _jspx_page_context;
/* 7596 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7598 */     ForEachTag _jspx_th_c_005fforEach_005f3 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 7599 */     _jspx_th_c_005fforEach_005f3.setPageContext(_jspx_page_context);
/* 7600 */     _jspx_th_c_005fforEach_005f3.setParent(null);
/*      */     
/* 7602 */     _jspx_th_c_005fforEach_005f3.setItems("${credentialHash.TELNET}");
/*      */     
/* 7604 */     _jspx_th_c_005fforEach_005f3.setVar("credential");
/* 7605 */     int[] _jspx_push_body_count_c_005fforEach_005f3 = { 0 };
/*      */     try {
/* 7607 */       int _jspx_eval_c_005fforEach_005f3 = _jspx_th_c_005fforEach_005f3.doStartTag();
/* 7608 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f3 != 0) {
/*      */         for (;;) {
/* 7610 */           out.write("\n\t\t\tdocument.AMActionForm.credentialID.options[i] = new Option('");
/* 7611 */           boolean bool; if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fforEach_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 7612 */             return true;
/* 7613 */           out.write(39);
/* 7614 */           out.write(44);
/* 7615 */           out.write(39);
/* 7616 */           if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 7617 */             return true;
/* 7618 */           out.write("',0,0);\n\t\t\ti++;\n\t      ");
/* 7619 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f3.doAfterBody();
/* 7620 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 7624 */       if (_jspx_th_c_005fforEach_005f3.doEndTag() == 5)
/* 7625 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 7628 */         int tmp237_236 = 0; int[] tmp237_234 = _jspx_push_body_count_c_005fforEach_005f3; int tmp239_238 = tmp237_234[tmp237_236];tmp237_234[tmp237_236] = (tmp239_238 - 1); if (tmp239_238 <= 0) break;
/* 7629 */         out = _jspx_page_context.popBody(); }
/* 7630 */       _jspx_th_c_005fforEach_005f3.doCatch(_jspx_exception);
/*      */     } finally {
/* 7632 */       _jspx_th_c_005fforEach_005f3.doFinally();
/* 7633 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f3);
/*      */     }
/* 7635 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fforEach_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 7640 */     PageContext pageContext = _jspx_page_context;
/* 7641 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7643 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7644 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 7645 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f3);
/*      */     
/* 7647 */     _jspx_th_c_005fout_005f7.setValue("${credential.value}");
/* 7648 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 7649 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 7650 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 7651 */       return true;
/*      */     }
/* 7653 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 7654 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 7659 */     PageContext pageContext = _jspx_page_context;
/* 7660 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7662 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7663 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 7664 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f3);
/*      */     
/* 7666 */     _jspx_th_c_005fout_005f8.setValue("${credential.key}");
/* 7667 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 7668 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 7669 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 7670 */       return true;
/*      */     }
/* 7672 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 7673 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7678 */     PageContext pageContext = _jspx_page_context;
/* 7679 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7681 */     ForEachTag _jspx_th_c_005fforEach_005f4 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 7682 */     _jspx_th_c_005fforEach_005f4.setPageContext(_jspx_page_context);
/* 7683 */     _jspx_th_c_005fforEach_005f4.setParent(null);
/*      */     
/* 7685 */     _jspx_th_c_005fforEach_005f4.setItems("${credentialHash.WMI}");
/*      */     
/* 7687 */     _jspx_th_c_005fforEach_005f4.setVar("credential");
/* 7688 */     int[] _jspx_push_body_count_c_005fforEach_005f4 = { 0 };
/*      */     try {
/* 7690 */       int _jspx_eval_c_005fforEach_005f4 = _jspx_th_c_005fforEach_005f4.doStartTag();
/* 7691 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f4 != 0) {
/*      */         for (;;) {
/* 7693 */           out.write("\n\t\t\tdocument.AMActionForm.credentialID.options[i] = new Option('");
/* 7694 */           boolean bool; if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fforEach_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/* 7695 */             return true;
/* 7696 */           out.write(39);
/* 7697 */           out.write(44);
/* 7698 */           out.write(39);
/* 7699 */           if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/* 7700 */             return true;
/* 7701 */           out.write("',0,0);\n\t\t\ti++;\n\t      ");
/* 7702 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f4.doAfterBody();
/* 7703 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 7707 */       if (_jspx_th_c_005fforEach_005f4.doEndTag() == 5)
/* 7708 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 7711 */         int tmp237_236 = 0; int[] tmp237_234 = _jspx_push_body_count_c_005fforEach_005f4; int tmp239_238 = tmp237_234[tmp237_236];tmp237_234[tmp237_236] = (tmp239_238 - 1); if (tmp239_238 <= 0) break;
/* 7712 */         out = _jspx_page_context.popBody(); }
/* 7713 */       _jspx_th_c_005fforEach_005f4.doCatch(_jspx_exception);
/*      */     } finally {
/* 7715 */       _jspx_th_c_005fforEach_005f4.doFinally();
/* 7716 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f4);
/*      */     }
/* 7718 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fforEach_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 7723 */     PageContext pageContext = _jspx_page_context;
/* 7724 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7726 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7727 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 7728 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f4);
/*      */     
/* 7730 */     _jspx_th_c_005fout_005f9.setValue("${credential.value}");
/* 7731 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 7732 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 7733 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 7734 */       return true;
/*      */     }
/* 7736 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 7737 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 7742 */     PageContext pageContext = _jspx_page_context;
/* 7743 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7745 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7746 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 7747 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f4);
/*      */     
/* 7749 */     _jspx_th_c_005fout_005f10.setValue("${credential.key}");
/* 7750 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 7751 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 7752 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 7753 */       return true;
/*      */     }
/* 7755 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 7756 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7761 */     PageContext pageContext = _jspx_page_context;
/* 7762 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7764 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 7765 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 7766 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 7768 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 7770 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 7771 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 7772 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 7773 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 7774 */       return true;
/*      */     }
/* 7776 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 7777 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7782 */     PageContext pageContext = _jspx_page_context;
/* 7783 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7785 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 7786 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 7787 */     _jspx_th_am_005fhiddenparam_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7789 */     _jspx_th_am_005fhiddenparam_005f0.setName("wiz");
/* 7790 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 7791 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 7792 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 7793 */       return true;
/*      */     }
/* 7795 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 7796 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7801 */     PageContext pageContext = _jspx_page_context;
/* 7802 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7804 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7805 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 7806 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7808 */     _jspx_th_c_005fif_005f7.setTest("${!empty param.wiz ||  !empty param.fromAssociate}");
/* 7809 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 7810 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 7812 */         out.write("\n      ");
/* 7813 */         if (_jspx_meth_c_005fchoose_005f0(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 7814 */           return true;
/* 7815 */         out.write("\n      ");
/* 7816 */         if (_jspx_meth_html_005fhidden_005f0(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 7817 */           return true;
/* 7818 */         out.write("\n      ");
/* 7819 */         if (_jspx_meth_html_005fhidden_005f1(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 7820 */           return true;
/* 7821 */         out.write("\n      ");
/* 7822 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 7823 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7827 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 7828 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 7829 */       return true;
/*      */     }
/* 7831 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 7832 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7837 */     PageContext pageContext = _jspx_page_context;
/* 7838 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7840 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 7841 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 7842 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_c_005fif_005f7);
/* 7843 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 7844 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 7846 */         out.write("\n        ");
/* 7847 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 7848 */           return true;
/* 7849 */         out.write("\n        ");
/* 7850 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 7851 */           return true;
/* 7852 */         out.write("\n\n        ");
/* 7853 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 7854 */           return true;
/* 7855 */         out.write("\n      ");
/* 7856 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 7857 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7861 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 7862 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 7863 */       return true;
/*      */     }
/* 7865 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 7866 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7871 */     PageContext pageContext = _jspx_page_context;
/* 7872 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7874 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 7875 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 7876 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 7878 */     _jspx_th_c_005fwhen_005f0.setTest("${param.type=='WTA'}");
/* 7879 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 7880 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 7882 */         out.write("\n          ");
/* 7883 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 7884 */           return true;
/* 7885 */         out.write("\n        ");
/* 7886 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 7887 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7891 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 7892 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 7893 */       return true;
/*      */     }
/* 7895 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 7896 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7901 */     PageContext pageContext = _jspx_page_context;
/* 7902 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7904 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7905 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 7906 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 7908 */     _jspx_th_c_005fout_005f11.setValue("Web Transaction Monitor");
/* 7909 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 7910 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 7911 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 7912 */       return true;
/*      */     }
/* 7914 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 7915 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7920 */     PageContext pageContext = _jspx_page_context;
/* 7921 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7923 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 7924 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 7925 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 7927 */     _jspx_th_c_005fwhen_005f1.setTest("${param.type=='.Net'}");
/* 7928 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 7929 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 7931 */         out.write("\n          ");
/* 7932 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 7933 */           return true;
/* 7934 */         out.write("\n        ");
/* 7935 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 7936 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7940 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 7941 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 7942 */       return true;
/*      */     }
/* 7944 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 7945 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7950 */     PageContext pageContext = _jspx_page_context;
/* 7951 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7953 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7954 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 7955 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 7957 */     _jspx_th_c_005fout_005f12.setValue("Tomcat Server");
/* 7958 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 7959 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 7960 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 7961 */       return true;
/*      */     }
/* 7963 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 7964 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7969 */     PageContext pageContext = _jspx_page_context;
/* 7970 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7972 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 7973 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 7974 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 7975 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 7976 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 7978 */         out.write("\n         ");
/* 7979 */         if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 7980 */           return true;
/* 7981 */         out.write("\n        ");
/* 7982 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 7983 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7987 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 7988 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 7989 */       return true;
/*      */     }
/* 7991 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 7992 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7997 */     PageContext pageContext = _jspx_page_context;
/* 7998 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8000 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 8001 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 8002 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 8004 */     _jspx_th_c_005fout_005f13.setValue("${param.type}");
/* 8005 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 8006 */     if (_jspx_eval_c_005fout_005f13 != 0) {
/* 8007 */       if (_jspx_eval_c_005fout_005f13 != 1) {
/* 8008 */         out = _jspx_page_context.pushBody();
/* 8009 */         _jspx_th_c_005fout_005f13.setBodyContent((BodyContent)out);
/* 8010 */         _jspx_th_c_005fout_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8013 */         out.write(45);
/* 8014 */         int evalDoAfterBody = _jspx_th_c_005fout_005f13.doAfterBody();
/* 8015 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8018 */       if (_jspx_eval_c_005fout_005f13 != 1) {
/* 8019 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8022 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 8023 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f13);
/* 8024 */       return true;
/*      */     }
/* 8026 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f13);
/* 8027 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8032 */     PageContext pageContext = _jspx_page_context;
/* 8033 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8035 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 8036 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 8037 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 8039 */     _jspx_th_html_005fhidden_005f0.setProperty("type");
/* 8040 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 8041 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 8042 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 8043 */       return true;
/*      */     }
/* 8045 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 8046 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8051 */     PageContext pageContext = _jspx_page_context;
/* 8052 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8054 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 8055 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 8056 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 8058 */     _jspx_th_html_005fhidden_005f1.setProperty("haid");
/* 8059 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 8060 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 8061 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 8062 */       return true;
/*      */     }
/* 8064 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 8065 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8070 */     PageContext pageContext = _jspx_page_context;
/* 8071 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8073 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 8074 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 8075 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8077 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 8079 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default");
/* 8080 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 8081 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 8082 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 8083 */       return true;
/*      */     }
/* 8085 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 8086 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8091 */     PageContext pageContext = _jspx_page_context;
/* 8092 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8094 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 8095 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 8096 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8098 */     _jspx_th_html_005fradio_005f0.setProperty("mtype");
/*      */     
/* 8100 */     _jspx_th_html_005fradio_005f0.setValue("file");
/*      */     
/* 8102 */     _jspx_th_html_005fradio_005f0.setOnclick("javascript:managecontent()");
/* 8103 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 8104 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 8105 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 8106 */       return true;
/*      */     }
/* 8108 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 8109 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8114 */     PageContext pageContext = _jspx_page_context;
/* 8115 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8117 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 8118 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 8119 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8121 */     _jspx_th_html_005fradio_005f1.setProperty("mtype");
/*      */     
/* 8123 */     _jspx_th_html_005fradio_005f1.setValue("directory");
/*      */     
/* 8125 */     _jspx_th_html_005fradio_005f1.setOnclick("javascript:managecontent()");
/* 8126 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 8127 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 8128 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 8129 */       return true;
/*      */     }
/* 8131 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 8132 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8137 */     PageContext pageContext = _jspx_page_context;
/* 8138 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8140 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 8141 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 8142 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8144 */     _jspx_th_html_005ftext_005f1.setSize("25");
/*      */     
/* 8146 */     _jspx_th_html_005ftext_005f1.setProperty("filepath");
/*      */     
/* 8148 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext\n");
/* 8149 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 8150 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 8151 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 8152 */       return true;
/*      */     }
/* 8154 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 8155 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8160 */     PageContext pageContext = _jspx_page_context;
/* 8161 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8163 */     RadioTag _jspx_th_html_005fradio_005f2 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 8164 */     _jspx_th_html_005fradio_005f2.setPageContext(_jspx_page_context);
/* 8165 */     _jspx_th_html_005fradio_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8167 */     _jspx_th_html_005fradio_005f2.setProperty("serversite");
/*      */     
/* 8169 */     _jspx_th_html_005fradio_005f2.setValue("local");
/*      */     
/* 8171 */     _jspx_th_html_005fradio_005f2.setOnclick("javascript:manageremotescript()");
/* 8172 */     int _jspx_eval_html_005fradio_005f2 = _jspx_th_html_005fradio_005f2.doStartTag();
/* 8173 */     if (_jspx_th_html_005fradio_005f2.doEndTag() == 5) {
/* 8174 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 8175 */       return true;
/*      */     }
/* 8177 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 8178 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8183 */     PageContext pageContext = _jspx_page_context;
/* 8184 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8186 */     RadioTag _jspx_th_html_005fradio_005f3 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 8187 */     _jspx_th_html_005fradio_005f3.setPageContext(_jspx_page_context);
/* 8188 */     _jspx_th_html_005fradio_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8190 */     _jspx_th_html_005fradio_005f3.setProperty("serversite");
/*      */     
/* 8192 */     _jspx_th_html_005fradio_005f3.setValue("remote");
/*      */     
/* 8194 */     _jspx_th_html_005fradio_005f3.setOnclick("javascript:manageremotescript()");
/* 8195 */     int _jspx_eval_html_005fradio_005f3 = _jspx_th_html_005fradio_005f3.doStartTag();
/* 8196 */     if (_jspx_th_html_005fradio_005f3.doEndTag() == 5) {
/* 8197 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 8198 */       return true;
/*      */     }
/* 8200 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 8201 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8206 */     PageContext pageContext = _jspx_page_context;
/* 8207 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8209 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 8210 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 8211 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8213 */     _jspx_th_html_005ftext_005f2.setProperty("host");
/*      */     
/* 8215 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext default");
/* 8216 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 8217 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 8218 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 8219 */       return true;
/*      */     }
/* 8221 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 8222 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8227 */     PageContext pageContext = _jspx_page_context;
/* 8228 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8230 */     RadioTag _jspx_th_html_005fradio_005f4 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 8231 */     _jspx_th_html_005fradio_005f4.setPageContext(_jspx_page_context);
/* 8232 */     _jspx_th_html_005fradio_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8234 */     _jspx_th_html_005fradio_005f4.setProperty("isCredManager");
/*      */     
/* 8236 */     _jspx_th_html_005fradio_005f4.setValue("false");
/*      */     
/* 8238 */     _jspx_th_html_005fradio_005f4.setOnclick("javascript:showCredentialProfiles();");
/* 8239 */     int _jspx_eval_html_005fradio_005f4 = _jspx_th_html_005fradio_005f4.doStartTag();
/* 8240 */     if (_jspx_th_html_005fradio_005f4.doEndTag() == 5) {
/* 8241 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 8242 */       return true;
/*      */     }
/* 8244 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 8245 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8250 */     PageContext pageContext = _jspx_page_context;
/* 8251 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8253 */     RadioTag _jspx_th_html_005fradio_005f5 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 8254 */     _jspx_th_html_005fradio_005f5.setPageContext(_jspx_page_context);
/* 8255 */     _jspx_th_html_005fradio_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8257 */     _jspx_th_html_005fradio_005f5.setProperty("isCredManager");
/*      */     
/* 8259 */     _jspx_th_html_005fradio_005f5.setValue("true");
/*      */     
/* 8261 */     _jspx_th_html_005fradio_005f5.setOnclick("javascript:showCredentialProfiles();");
/* 8262 */     int _jspx_eval_html_005fradio_005f5 = _jspx_th_html_005fradio_005f5.doStartTag();
/* 8263 */     if (_jspx_th_html_005fradio_005f5.doEndTag() == 5) {
/* 8264 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f5);
/* 8265 */       return true;
/*      */     }
/* 8267 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f5);
/* 8268 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8273 */     PageContext pageContext = _jspx_page_context;
/* 8274 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8276 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 8277 */     _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 8278 */     _jspx_th_logic_005fnotEmpty_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8280 */     _jspx_th_logic_005fnotEmpty_005f2.setName("credentialHash");
/* 8281 */     int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 8282 */     if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */       for (;;) {
/* 8284 */         out.write("\n\t\t\t   ");
/* 8285 */         if (_jspx_meth_html_005fselect_005f3(_jspx_th_logic_005fnotEmpty_005f2, _jspx_page_context))
/* 8286 */           return true;
/* 8287 */         out.write("\n\t\t\t");
/* 8288 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 8289 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8293 */     if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 8294 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 8295 */       return true;
/*      */     }
/* 8297 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 8298 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f3(JspTag _jspx_th_logic_005fnotEmpty_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8303 */     PageContext pageContext = _jspx_page_context;
/* 8304 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8306 */     SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 8307 */     _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 8308 */     _jspx_th_html_005fselect_005f3.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f2);
/*      */     
/* 8310 */     _jspx_th_html_005fselect_005f3.setStyle("width: 135px;");
/*      */     
/* 8312 */     _jspx_th_html_005fselect_005f3.setProperty("credentialID");
/*      */     
/* 8314 */     _jspx_th_html_005fselect_005f3.setStyleClass("formtextarea");
/* 8315 */     int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 8316 */     if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 8317 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 8318 */         out = _jspx_page_context.pushBody();
/* 8319 */         _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 8320 */         _jspx_th_html_005fselect_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8323 */         out.write("\t   ");
/* 8324 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 8325 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8328 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 8329 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8332 */     if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 8333 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f3);
/* 8334 */       return true;
/*      */     }
/* 8336 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f3);
/* 8337 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fempty_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8342 */     PageContext pageContext = _jspx_page_context;
/* 8343 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8345 */     EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 8346 */     _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 8347 */     _jspx_th_logic_005fempty_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8349 */     _jspx_th_logic_005fempty_005f0.setName("credentialHash");
/* 8350 */     int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 8351 */     if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */       for (;;) {
/* 8353 */         out.write("\n\t\t\t\t      <b><i>");
/* 8354 */         if (_jspx_meth_bean_005fmessage_005f0(_jspx_th_logic_005fempty_005f0, _jspx_page_context))
/* 8355 */           return true;
/* 8356 */         out.write("</i></b>\n\t\t\t");
/* 8357 */         int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 8358 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8362 */     if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 8363 */       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 8364 */       return true;
/*      */     }
/* 8366 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 8367 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fmessage_005f0(JspTag _jspx_th_logic_005fempty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8372 */     PageContext pageContext = _jspx_page_context;
/* 8373 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8375 */     MessageTag _jspx_th_bean_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 8376 */     _jspx_th_bean_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 8377 */     _jspx_th_bean_005fmessage_005f0.setParent((Tag)_jspx_th_logic_005fempty_005f0);
/*      */     
/* 8379 */     _jspx_th_bean_005fmessage_005f0.setKey("No Credentials");
/* 8380 */     int _jspx_eval_bean_005fmessage_005f0 = _jspx_th_bean_005fmessage_005f0.doStartTag();
/* 8381 */     if (_jspx_th_bean_005fmessage_005f0.doEndTag() == 5) {
/* 8382 */       this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
/* 8383 */       return true;
/*      */     }
/* 8385 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
/* 8386 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8391 */     PageContext pageContext = _jspx_page_context;
/* 8392 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8394 */     CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 8395 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 8396 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8398 */     _jspx_th_html_005fcheckbox_005f0.setProperty("sshkey");
/*      */     
/* 8400 */     _jspx_th_html_005fcheckbox_005f0.setOnclick("showPrivateKey()");
/* 8401 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 8402 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 8403 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 8404 */       return true;
/*      */     }
/* 8406 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 8407 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8412 */     PageContext pageContext = _jspx_page_context;
/* 8413 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8415 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonblur_005fnobody.get(TextTag.class);
/* 8416 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 8417 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8419 */     _jspx_th_html_005ftext_005f3.setProperty("username");
/*      */     
/* 8421 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/*      */     
/* 8423 */     _jspx_th_html_005ftext_005f3.setSize("15");
/*      */     
/* 8425 */     _jspx_th_html_005ftext_005f3.setOnblur("checkHost()");
/* 8426 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 8427 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 8428 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonblur_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 8429 */       return true;
/*      */     }
/* 8431 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonblur_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 8432 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8437 */     PageContext pageContext = _jspx_page_context;
/* 8438 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8440 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 8441 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 8442 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8444 */     _jspx_th_html_005ftext_005f4.setProperty("username");
/*      */     
/* 8446 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext");
/*      */     
/* 8448 */     _jspx_th_html_005ftext_005f4.setSize("15");
/* 8449 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 8450 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 8451 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 8452 */       return true;
/*      */     }
/* 8454 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 8455 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8460 */     PageContext pageContext = _jspx_page_context;
/* 8461 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8463 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 8464 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 8465 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8467 */     _jspx_th_html_005fpassword_005f0.setProperty("password");
/*      */     
/* 8469 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext");
/*      */     
/* 8471 */     _jspx_th_html_005fpassword_005f0.setSize("15");
/* 8472 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 8473 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 8474 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 8475 */       return true;
/*      */     }
/* 8477 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 8478 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8483 */     PageContext pageContext = _jspx_page_context;
/* 8484 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8486 */     TextareaTag _jspx_th_html_005ftextarea_005f0 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 8487 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 8488 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8490 */     _jspx_th_html_005ftextarea_005f0.setProperty("description");
/*      */     
/* 8492 */     _jspx_th_html_005ftextarea_005f0.setStyleClass("formtextarea xlarge");
/*      */     
/* 8494 */     _jspx_th_html_005ftextarea_005f0.setRows("4");
/*      */     
/* 8496 */     _jspx_th_html_005ftextarea_005f0.setCols("50");
/* 8497 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 8498 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 8499 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 8500 */       return true;
/*      */     }
/* 8502 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 8503 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8508 */     PageContext pageContext = _jspx_page_context;
/* 8509 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8511 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 8512 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 8513 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8515 */     _jspx_th_html_005ftext_005f5.setProperty("prompt");
/*      */     
/* 8517 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext msmall");
/*      */     
/* 8519 */     _jspx_th_html_005ftext_005f5.setValue("$");
/* 8520 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 8521 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 8522 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 8523 */       return true;
/*      */     }
/* 8525 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 8526 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8531 */     PageContext pageContext = _jspx_page_context;
/* 8532 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8534 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 8535 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 8536 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8538 */     _jspx_th_html_005ftext_005f6.setProperty("port");
/*      */     
/* 8540 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext small");
/*      */     
/* 8542 */     _jspx_th_html_005ftext_005f6.setValue("23");
/* 8543 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 8544 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 8545 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 8546 */       return true;
/*      */     }
/* 8548 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 8549 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8554 */     PageContext pageContext = _jspx_page_context;
/* 8555 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8557 */     CheckboxTag _jspx_th_html_005fcheckbox_005f1 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 8558 */     _jspx_th_html_005fcheckbox_005f1.setPageContext(_jspx_page_context);
/* 8559 */     _jspx_th_html_005fcheckbox_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8561 */     _jspx_th_html_005fcheckbox_005f1.setProperty("contentChk");
/*      */     
/* 8563 */     _jspx_th_html_005fcheckbox_005f1.setOnclick("javascript:ContentCheck()");
/* 8564 */     int _jspx_eval_html_005fcheckbox_005f1 = _jspx_th_html_005fcheckbox_005f1.doStartTag();
/* 8565 */     if (_jspx_th_html_005fcheckbox_005f1.doEndTag() == 5) {
/* 8566 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 8567 */       return true;
/*      */     }
/* 8569 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 8570 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8575 */     PageContext pageContext = _jspx_page_context;
/* 8576 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8578 */     RadioTag _jspx_th_html_005fradio_005f6 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 8579 */     _jspx_th_html_005fradio_005f6.setPageContext(_jspx_page_context);
/* 8580 */     _jspx_th_html_005fradio_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8582 */     _jspx_th_html_005fradio_005f6.setProperty("fileCheckType");
/*      */     
/* 8584 */     _jspx_th_html_005fradio_005f6.setValue("0");
/*      */     
/* 8586 */     _jspx_th_html_005fradio_005f6.setOnclick("javascript:showHideCheckEmpty()");
/* 8587 */     int _jspx_eval_html_005fradio_005f6 = _jspx_th_html_005fradio_005f6.doStartTag();
/* 8588 */     if (_jspx_th_html_005fradio_005f6.doEndTag() == 5) {
/* 8589 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f6);
/* 8590 */       return true;
/*      */     }
/* 8592 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f6);
/* 8593 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8598 */     PageContext pageContext = _jspx_page_context;
/* 8599 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8601 */     RadioTag _jspx_th_html_005fradio_005f7 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 8602 */     _jspx_th_html_005fradio_005f7.setPageContext(_jspx_page_context);
/* 8603 */     _jspx_th_html_005fradio_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8605 */     _jspx_th_html_005fradio_005f7.setProperty("fileCheckType");
/*      */     
/* 8607 */     _jspx_th_html_005fradio_005f7.setValue("1");
/*      */     
/* 8609 */     _jspx_th_html_005fradio_005f7.setOnclick("javascript:showHideCheckEmpty()");
/* 8610 */     int _jspx_eval_html_005fradio_005f7 = _jspx_th_html_005fradio_005f7.doStartTag();
/* 8611 */     if (_jspx_th_html_005fradio_005f7.doEndTag() == 5) {
/* 8612 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f7);
/* 8613 */       return true;
/*      */     }
/* 8615 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f7);
/* 8616 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8621 */     PageContext pageContext = _jspx_page_context;
/* 8622 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8624 */     TextareaTag _jspx_th_html_005ftextarea_005f1 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 8625 */     _jspx_th_html_005ftextarea_005f1.setPageContext(_jspx_page_context);
/* 8626 */     _jspx_th_html_005ftextarea_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8628 */     _jspx_th_html_005ftextarea_005f1.setRows("3");
/*      */     
/* 8630 */     _jspx_th_html_005ftextarea_005f1.setCols("50");
/*      */     
/* 8632 */     _jspx_th_html_005ftextarea_005f1.setProperty("ccontent");
/*      */     
/* 8634 */     _jspx_th_html_005ftextarea_005f1.setStyleClass("formtextarea xlarge");
/* 8635 */     int _jspx_eval_html_005ftextarea_005f1 = _jspx_th_html_005ftextarea_005f1.doStartTag();
/* 8636 */     if (_jspx_th_html_005ftextarea_005f1.doEndTag() == 5) {
/* 8637 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f1);
/* 8638 */       return true;
/*      */     }
/* 8640 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f1);
/* 8641 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8646 */     PageContext pageContext = _jspx_page_context;
/* 8647 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8649 */     CheckboxTag _jspx_th_html_005fcheckbox_005f2 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 8650 */     _jspx_th_html_005fcheckbox_005f2.setPageContext(_jspx_page_context);
/* 8651 */     _jspx_th_html_005fcheckbox_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8653 */     _jspx_th_html_005fcheckbox_005f2.setProperty("regexChk");
/*      */     
/* 8655 */     _jspx_th_html_005fcheckbox_005f2.setOnclick("javascript:RegexCheck()");
/* 8656 */     int _jspx_eval_html_005fcheckbox_005f2 = _jspx_th_html_005fcheckbox_005f2.doStartTag();
/* 8657 */     if (_jspx_th_html_005fcheckbox_005f2.doEndTag() == 5) {
/* 8658 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 8659 */       return true;
/*      */     }
/* 8661 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 8662 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8667 */     PageContext pageContext = _jspx_page_context;
/* 8668 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8670 */     CheckboxTag _jspx_th_html_005fcheckbox_005f3 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/* 8671 */     _jspx_th_html_005fcheckbox_005f3.setPageContext(_jspx_page_context);
/* 8672 */     _jspx_th_html_005fcheckbox_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8674 */     _jspx_th_html_005fcheckbox_005f3.setProperty("checkEmpty");
/* 8675 */     int _jspx_eval_html_005fcheckbox_005f3 = _jspx_th_html_005fcheckbox_005f3.doStartTag();
/* 8676 */     if (_jspx_th_html_005fcheckbox_005f3.doEndTag() == 5) {
/* 8677 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/* 8678 */       return true;
/*      */     }
/* 8680 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/* 8681 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8686 */     PageContext pageContext = _jspx_page_context;
/* 8687 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8689 */     RadioTag _jspx_th_html_005fradio_005f8 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 8690 */     _jspx_th_html_005fradio_005f8.setPageContext(_jspx_page_context);
/* 8691 */     _jspx_th_html_005fradio_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8693 */     _jspx_th_html_005fradio_005f8.setProperty("clearCondition");
/*      */     
/* 8695 */     _jspx_th_html_005fradio_005f8.setValue("0");
/* 8696 */     int _jspx_eval_html_005fradio_005f8 = _jspx_th_html_005fradio_005f8.doStartTag();
/* 8697 */     if (_jspx_th_html_005fradio_005f8.doEndTag() == 5) {
/* 8698 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f8);
/* 8699 */       return true;
/*      */     }
/* 8701 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f8);
/* 8702 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8707 */     PageContext pageContext = _jspx_page_context;
/* 8708 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8710 */     RadioTag _jspx_th_html_005fradio_005f9 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 8711 */     _jspx_th_html_005fradio_005f9.setPageContext(_jspx_page_context);
/* 8712 */     _jspx_th_html_005fradio_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8714 */     _jspx_th_html_005fradio_005f9.setProperty("clearCondition");
/*      */     
/* 8716 */     _jspx_th_html_005fradio_005f9.setValue("1");
/* 8717 */     int _jspx_eval_html_005fradio_005f9 = _jspx_th_html_005fradio_005f9.doStartTag();
/* 8718 */     if (_jspx_th_html_005fradio_005f9.doEndTag() == 5) {
/* 8719 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f9);
/* 8720 */       return true;
/*      */     }
/* 8722 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f9);
/* 8723 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8728 */     PageContext pageContext = _jspx_page_context;
/* 8729 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8731 */     TextareaTag _jspx_th_html_005ftextarea_005f2 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 8732 */     _jspx_th_html_005ftextarea_005f2.setPageContext(_jspx_page_context);
/* 8733 */     _jspx_th_html_005ftextarea_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8735 */     _jspx_th_html_005ftextarea_005f2.setProperty("clearConditionContent");
/*      */     
/* 8737 */     _jspx_th_html_005ftextarea_005f2.setCols("50");
/*      */     
/* 8739 */     _jspx_th_html_005ftextarea_005f2.setRows("3");
/*      */     
/* 8741 */     _jspx_th_html_005ftextarea_005f2.setStyleClass("formtextarea xlarge");
/* 8742 */     int _jspx_eval_html_005ftextarea_005f2 = _jspx_th_html_005ftextarea_005f2.doStartTag();
/* 8743 */     if (_jspx_th_html_005ftextarea_005f2.doEndTag() == 5) {
/* 8744 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f2);
/* 8745 */       return true;
/*      */     }
/* 8747 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f2);
/* 8748 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8753 */     PageContext pageContext = _jspx_page_context;
/* 8754 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8756 */     CheckboxTag _jspx_th_html_005fcheckbox_005f4 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 8757 */     _jspx_th_html_005fcheckbox_005f4.setPageContext(_jspx_page_context);
/* 8758 */     _jspx_th_html_005fcheckbox_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8760 */     _jspx_th_html_005fcheckbox_005f4.setProperty("fileDirAge");
/*      */     
/* 8762 */     _jspx_th_html_005fcheckbox_005f4.setOnclick("javascript:FileDirAgeCheck()");
/* 8763 */     int _jspx_eval_html_005fcheckbox_005f4 = _jspx_th_html_005fcheckbox_005f4.doStartTag();
/* 8764 */     if (_jspx_th_html_005fcheckbox_005f4.doEndTag() == 5) {
/* 8765 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f4);
/* 8766 */       return true;
/*      */     }
/* 8768 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f4);
/* 8769 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8774 */     PageContext pageContext = _jspx_page_context;
/* 8775 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8777 */     TextTag _jspx_th_html_005ftext_005f8 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 8778 */     _jspx_th_html_005ftext_005f8.setPageContext(_jspx_page_context);
/* 8779 */     _jspx_th_html_005ftext_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8781 */     _jspx_th_html_005ftext_005f8.setSize("2");
/*      */     
/* 8783 */     _jspx_th_html_005ftext_005f8.setProperty("timeval");
/*      */     
/* 8785 */     _jspx_th_html_005ftext_005f8.setValue("10");
/*      */     
/* 8787 */     _jspx_th_html_005ftext_005f8.setStyleClass("formtext");
/* 8788 */     int _jspx_eval_html_005ftext_005f8 = _jspx_th_html_005ftext_005f8.doStartTag();
/* 8789 */     if (_jspx_th_html_005ftext_005f8.doEndTag() == 5) {
/* 8790 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 8791 */       return true;
/*      */     }
/* 8793 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 8794 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8799 */     PageContext pageContext = _jspx_page_context;
/* 8800 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8802 */     CheckboxTag _jspx_th_html_005fcheckbox_005f5 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/* 8803 */     _jspx_th_html_005fcheckbox_005f5.setPageContext(_jspx_page_context);
/* 8804 */     _jspx_th_html_005fcheckbox_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8806 */     _jspx_th_html_005fcheckbox_005f5.setProperty("subDirCntChk");
/* 8807 */     int _jspx_eval_html_005fcheckbox_005f5 = _jspx_th_html_005fcheckbox_005f5.doStartTag();
/* 8808 */     if (_jspx_th_html_005fcheckbox_005f5.doEndTag() == 5) {
/* 8809 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f5);
/* 8810 */       return true;
/*      */     }
/* 8812 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f5);
/* 8813 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8818 */     PageContext pageContext = _jspx_page_context;
/* 8819 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8821 */     RadioTag _jspx_th_html_005fradio_005f10 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 8822 */     _jspx_th_html_005fradio_005f10.setPageContext(_jspx_page_context);
/* 8823 */     _jspx_th_html_005fradio_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8825 */     _jspx_th_html_005fradio_005f10.setProperty("isCommand");
/*      */     
/* 8827 */     _jspx_th_html_005fradio_005f10.setValue("true");
/*      */     
/* 8829 */     _jspx_th_html_005fradio_005f10.setOnclick("javascript:manageCommand()");
/* 8830 */     int _jspx_eval_html_005fradio_005f10 = _jspx_th_html_005fradio_005f10.doStartTag();
/* 8831 */     if (_jspx_th_html_005fradio_005f10.doEndTag() == 5) {
/* 8832 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f10);
/* 8833 */       return true;
/*      */     }
/* 8835 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f10);
/* 8836 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8841 */     PageContext pageContext = _jspx_page_context;
/* 8842 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8844 */     RadioTag _jspx_th_html_005fradio_005f11 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 8845 */     _jspx_th_html_005fradio_005f11.setPageContext(_jspx_page_context);
/* 8846 */     _jspx_th_html_005fradio_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8848 */     _jspx_th_html_005fradio_005f11.setProperty("isCommand");
/*      */     
/* 8850 */     _jspx_th_html_005fradio_005f11.setValue("false");
/*      */     
/* 8852 */     _jspx_th_html_005fradio_005f11.setOnclick("javascript:manageCommand()");
/* 8853 */     int _jspx_eval_html_005fradio_005f11 = _jspx_th_html_005fradio_005f11.doStartTag();
/* 8854 */     if (_jspx_th_html_005fradio_005f11.doEndTag() == 5) {
/* 8855 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f11);
/* 8856 */       return true;
/*      */     }
/* 8858 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f11);
/* 8859 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8864 */     PageContext pageContext = _jspx_page_context;
/* 8865 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8867 */     TextTag _jspx_th_html_005ftext_005f9 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 8868 */     _jspx_th_html_005ftext_005f9.setPageContext(_jspx_page_context);
/* 8869 */     _jspx_th_html_005ftext_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8871 */     _jspx_th_html_005ftext_005f9.setProperty("serverpath");
/*      */     
/* 8873 */     _jspx_th_html_005ftext_005f9.setStyleClass("formtext default");
/* 8874 */     int _jspx_eval_html_005ftext_005f9 = _jspx_th_html_005ftext_005f9.doStartTag();
/* 8875 */     if (_jspx_th_html_005ftext_005f9.doEndTag() == 5) {
/* 8876 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/* 8877 */       return true;
/*      */     }
/* 8879 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/* 8880 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8885 */     PageContext pageContext = _jspx_page_context;
/* 8886 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8888 */     TextTag _jspx_th_html_005ftext_005f10 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 8889 */     _jspx_th_html_005ftext_005f10.setPageContext(_jspx_page_context);
/* 8890 */     _jspx_th_html_005ftext_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8892 */     _jspx_th_html_005ftext_005f10.setProperty("workingdirectory");
/*      */     
/* 8894 */     _jspx_th_html_005ftext_005f10.setStyleClass("formtext default");
/* 8895 */     int _jspx_eval_html_005ftext_005f10 = _jspx_th_html_005ftext_005f10.doStartTag();
/* 8896 */     if (_jspx_th_html_005ftext_005f10.doEndTag() == 5) {
/* 8897 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f10);
/* 8898 */       return true;
/*      */     }
/* 8900 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f10);
/* 8901 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8906 */     PageContext pageContext = _jspx_page_context;
/* 8907 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8909 */     TextTag _jspx_th_html_005ftext_005f11 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 8910 */     _jspx_th_html_005ftext_005f11.setPageContext(_jspx_page_context);
/* 8911 */     _jspx_th_html_005ftext_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8913 */     _jspx_th_html_005ftext_005f11.setProperty("mode");
/*      */     
/* 8915 */     _jspx_th_html_005ftext_005f11.setValue("sh");
/*      */     
/* 8917 */     _jspx_th_html_005ftext_005f11.setStyleClass("formtext");
/* 8918 */     int _jspx_eval_html_005ftext_005f11 = _jspx_th_html_005ftext_005f11.doStartTag();
/* 8919 */     if (_jspx_th_html_005ftext_005f11.doEndTag() == 5) {
/* 8920 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f11);
/* 8921 */       return true;
/*      */     }
/* 8923 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f11);
/* 8924 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8929 */     PageContext pageContext = _jspx_page_context;
/* 8930 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8932 */     TextTag _jspx_th_html_005ftext_005f12 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 8933 */     _jspx_th_html_005ftext_005f12.setPageContext(_jspx_page_context);
/* 8934 */     _jspx_th_html_005ftext_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8936 */     _jspx_th_html_005ftext_005f12.setProperty("mode");
/*      */     
/* 8938 */     _jspx_th_html_005ftext_005f12.setValue("sh");
/*      */     
/* 8940 */     _jspx_th_html_005ftext_005f12.setStyleClass("formtext");
/* 8941 */     int _jspx_eval_html_005ftext_005f12 = _jspx_th_html_005ftext_005f12.doStartTag();
/* 8942 */     if (_jspx_th_html_005ftext_005f12.doEndTag() == 5) {
/* 8943 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f12);
/* 8944 */       return true;
/*      */     }
/* 8946 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f12);
/* 8947 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8952 */     PageContext pageContext = _jspx_page_context;
/* 8953 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8955 */     TextTag _jspx_th_html_005ftext_005f13 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 8956 */     _jspx_th_html_005ftext_005f13.setPageContext(_jspx_page_context);
/* 8957 */     _jspx_th_html_005ftext_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8959 */     _jspx_th_html_005ftext_005f13.setSize("25");
/*      */     
/* 8961 */     _jspx_th_html_005ftext_005f13.setProperty("outputfile");
/*      */     
/* 8963 */     _jspx_th_html_005ftext_005f13.setStyleClass("formtext default");
/* 8964 */     int _jspx_eval_html_005ftext_005f13 = _jspx_th_html_005ftext_005f13.doStartTag();
/* 8965 */     if (_jspx_th_html_005ftext_005f13.doEndTag() == 5) {
/* 8966 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f13);
/* 8967 */       return true;
/*      */     }
/* 8969 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f13);
/* 8970 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8975 */     PageContext pageContext = _jspx_page_context;
/* 8976 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8978 */     CheckboxTag _jspx_th_html_005fcheckbox_005f6 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 8979 */     _jspx_th_html_005fcheckbox_005f6.setPageContext(_jspx_page_context);
/* 8980 */     _jspx_th_html_005fcheckbox_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 8982 */     _jspx_th_html_005fcheckbox_005f6.setProperty("opfile");
/*      */     
/* 8984 */     _jspx_th_html_005fcheckbox_005f6.setOnclick("javascript:manageopFile()");
/* 8985 */     int _jspx_eval_html_005fcheckbox_005f6 = _jspx_th_html_005fcheckbox_005f6.doStartTag();
/* 8986 */     if (_jspx_th_html_005fcheckbox_005f6.doEndTag() == 5) {
/* 8987 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f6);
/* 8988 */       return true;
/*      */     }
/* 8990 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f6);
/* 8991 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8996 */     PageContext pageContext = _jspx_page_context;
/* 8997 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8999 */     TextTag _jspx_th_html_005ftext_005f14 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fdisabled_005fnobody.get(TextTag.class);
/* 9000 */     _jspx_th_html_005ftext_005f14.setPageContext(_jspx_page_context);
/* 9001 */     _jspx_th_html_005ftext_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 9003 */     _jspx_th_html_005ftext_005f14.setProperty("outputfile");
/*      */     
/* 9005 */     _jspx_th_html_005ftext_005f14.setDisabled(true);
/*      */     
/* 9007 */     _jspx_th_html_005ftext_005f14.setValue("");
/*      */     
/* 9009 */     _jspx_th_html_005ftext_005f14.setStyleClass("formtext default");
/* 9010 */     int _jspx_eval_html_005ftext_005f14 = _jspx_th_html_005ftext_005f14.doStartTag();
/* 9011 */     if (_jspx_th_html_005ftext_005f14.doEndTag() == 5) {
/* 9012 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fdisabled_005fnobody.reuse(_jspx_th_html_005ftext_005f14);
/* 9013 */       return true;
/*      */     }
/* 9015 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fdisabled_005fnobody.reuse(_jspx_th_html_005ftext_005f14);
/* 9016 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9021 */     PageContext pageContext = _jspx_page_context;
/* 9022 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9024 */     TextareaTag _jspx_th_html_005ftextarea_005f3 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 9025 */     _jspx_th_html_005ftextarea_005f3.setPageContext(_jspx_page_context);
/* 9026 */     _jspx_th_html_005ftextarea_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 9028 */     _jspx_th_html_005ftextarea_005f3.setCols("15");
/*      */     
/* 9030 */     _jspx_th_html_005ftextarea_005f3.setProperty("string_att");
/*      */     
/* 9032 */     _jspx_th_html_005ftextarea_005f3.setStyleClass("formtextarea default");
/* 9033 */     int _jspx_eval_html_005ftextarea_005f3 = _jspx_th_html_005ftextarea_005f3.doStartTag();
/* 9034 */     if (_jspx_th_html_005ftextarea_005f3.doEndTag() == 5) {
/* 9035 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f3);
/* 9036 */       return true;
/*      */     }
/* 9038 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f3);
/* 9039 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9044 */     PageContext pageContext = _jspx_page_context;
/* 9045 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9047 */     TextareaTag _jspx_th_html_005ftextarea_005f4 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 9048 */     _jspx_th_html_005ftextarea_005f4.setPageContext(_jspx_page_context);
/* 9049 */     _jspx_th_html_005ftextarea_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 9051 */     _jspx_th_html_005ftextarea_005f4.setCols("15");
/*      */     
/* 9053 */     _jspx_th_html_005ftextarea_005f4.setProperty("numeric_att");
/*      */     
/* 9055 */     _jspx_th_html_005ftextarea_005f4.setStyleClass("formtextarea default");
/* 9056 */     int _jspx_eval_html_005ftextarea_005f4 = _jspx_th_html_005ftextarea_005f4.doStartTag();
/* 9057 */     if (_jspx_th_html_005ftextarea_005f4.doEndTag() == 5) {
/* 9058 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f4);
/* 9059 */       return true;
/*      */     }
/* 9061 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f4);
/* 9062 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f15(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9067 */     PageContext pageContext = _jspx_page_context;
/* 9068 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9070 */     TextTag _jspx_th_html_005ftext_005f15 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 9071 */     _jspx_th_html_005ftext_005f15.setPageContext(_jspx_page_context);
/* 9072 */     _jspx_th_html_005ftext_005f15.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 9074 */     _jspx_th_html_005ftext_005f15.setSize("1");
/*      */     
/* 9076 */     _jspx_th_html_005ftext_005f15.setProperty("delimiter");
/*      */     
/* 9078 */     _jspx_th_html_005ftext_005f15.setStyleClass("formtext");
/* 9079 */     int _jspx_eval_html_005ftext_005f15 = _jspx_th_html_005ftext_005f15.doStartTag();
/* 9080 */     if (_jspx_th_html_005ftext_005f15.doEndTag() == 5) {
/* 9081 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f15);
/* 9082 */       return true;
/*      */     }
/* 9084 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f15);
/* 9085 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f16(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9090 */     PageContext pageContext = _jspx_page_context;
/* 9091 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9093 */     TextTag _jspx_th_html_005ftext_005f16 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 9094 */     _jspx_th_html_005ftext_005f16.setPageContext(_jspx_page_context);
/* 9095 */     _jspx_th_html_005ftext_005f16.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 9097 */     _jspx_th_html_005ftext_005f16.setProperty("message");
/*      */     
/* 9099 */     _jspx_th_html_005ftext_005f16.setValue("");
/*      */     
/* 9101 */     _jspx_th_html_005ftext_005f16.setStyleClass("formtext default");
/* 9102 */     int _jspx_eval_html_005ftext_005f16 = _jspx_th_html_005ftext_005f16.doStartTag();
/* 9103 */     if (_jspx_th_html_005ftext_005f16.doEndTag() == 5) {
/* 9104 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f16);
/* 9105 */       return true;
/*      */     }
/* 9107 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f16);
/* 9108 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f17(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9113 */     PageContext pageContext = _jspx_page_context;
/* 9114 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9116 */     TextTag _jspx_th_html_005ftext_005f17 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 9117 */     _jspx_th_html_005ftext_005f17.setPageContext(_jspx_page_context);
/* 9118 */     _jspx_th_html_005ftext_005f17.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 9120 */     _jspx_th_html_005ftext_005f17.setProperty("pollInterval");
/*      */     
/* 9122 */     _jspx_th_html_005ftext_005f17.setStyleClass("formtext small");
/*      */     
/* 9124 */     _jspx_th_html_005ftext_005f17.setValue("15");
/* 9125 */     int _jspx_eval_html_005ftext_005f17 = _jspx_th_html_005ftext_005f17.doStartTag();
/* 9126 */     if (_jspx_th_html_005ftext_005f17.doEndTag() == 5) {
/* 9127 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f17);
/* 9128 */       return true;
/*      */     }
/* 9130 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f17);
/* 9131 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f18(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9136 */     PageContext pageContext = _jspx_page_context;
/* 9137 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9139 */     TextTag _jspx_th_html_005ftext_005f18 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 9140 */     _jspx_th_html_005ftext_005f18.setPageContext(_jspx_page_context);
/* 9141 */     _jspx_th_html_005ftext_005f18.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 9143 */     _jspx_th_html_005ftext_005f18.setProperty("pollInterval");
/*      */     
/* 9145 */     _jspx_th_html_005ftext_005f18.setStyleClass("formtext small");
/* 9146 */     int _jspx_eval_html_005ftext_005f18 = _jspx_th_html_005ftext_005f18.doStartTag();
/* 9147 */     if (_jspx_th_html_005ftext_005f18.doEndTag() == 5) {
/* 9148 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f18);
/* 9149 */       return true;
/*      */     }
/* 9151 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f18);
/* 9152 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9157 */     PageContext pageContext = _jspx_page_context;
/* 9158 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9160 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9161 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 9162 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 9164 */     _jspx_th_c_005fout_005f14.setValue("${param.haid}");
/* 9165 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 9166 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 9167 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 9168 */       return true;
/*      */     }
/* 9170 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 9171 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9176 */     PageContext pageContext = _jspx_page_context;
/* 9177 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9179 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9180 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 9181 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 9183 */     _jspx_th_c_005fout_005f15.setValue("${param.haid}");
/* 9184 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 9185 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 9186 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 9187 */       return true;
/*      */     }
/* 9189 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 9190 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9195 */     PageContext pageContext = _jspx_page_context;
/* 9196 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9198 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9199 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 9200 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 9202 */     _jspx_th_c_005fout_005f16.setValue("${param.haid}");
/* 9203 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 9204 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 9205 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 9206 */       return true;
/*      */     }
/* 9208 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 9209 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9214 */     PageContext pageContext = _jspx_page_context;
/* 9215 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9217 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 9218 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 9219 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 9221 */     _jspx_th_tiles_005fput_005f3.setName("footer");
/*      */     
/* 9223 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/footer.jsp");
/* 9224 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 9225 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 9226 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 9227 */       return true;
/*      */     }
/* 9229 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 9230 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\newscript_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */