/*      */ package org.apache.jsp.jsp.sap;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.tags.HiddenParam;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import java.sql.ResultSet;
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
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.PasswordTag;
/*      */ import org.apache.struts.taglib.html.ResetTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ 
/*      */ public final class newsapccms_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   50 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   53 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   54 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   55 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   62 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   67 */     ArrayList list = null;
/*   68 */     StringBuffer sbf = new StringBuffer();
/*   69 */     ManagedApplication mo = new ManagedApplication();
/*   70 */     if (distinct)
/*      */     {
/*   72 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   76 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   79 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   81 */       ArrayList row = (ArrayList)list.get(i);
/*   82 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   83 */       if (distinct) {
/*   84 */         sbf.append(row.get(0));
/*      */       } else
/*   86 */         sbf.append(row.get(1));
/*   87 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   90 */     return sbf.toString(); }
/*      */   
/*   92 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   95 */     if (severity == null)
/*      */     {
/*   97 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*   99 */     if (severity.equals("5"))
/*      */     {
/*  101 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  103 */     if (severity.equals("1"))
/*      */     {
/*  105 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  110 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  117 */     if (severity == null)
/*      */     {
/*  119 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  121 */     if (severity.equals("1"))
/*      */     {
/*  123 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  125 */     if (severity.equals("4"))
/*      */     {
/*  127 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  129 */     if (severity.equals("5"))
/*      */     {
/*  131 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  136 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  142 */     if (severity == null)
/*      */     {
/*  144 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  146 */     if (severity.equals("5"))
/*      */     {
/*  148 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  150 */     if (severity.equals("1"))
/*      */     {
/*  152 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  156 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  162 */     if (severity == null)
/*      */     {
/*  164 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  166 */     if (severity.equals("1"))
/*      */     {
/*  168 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  170 */     if (severity.equals("4"))
/*      */     {
/*  172 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  174 */     if (severity.equals("5"))
/*      */     {
/*  176 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  180 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  186 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  192 */     if (severity == 5)
/*      */     {
/*  194 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  196 */     if (severity == 1)
/*      */     {
/*  198 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  203 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  209 */     if (severity == null)
/*      */     {
/*  211 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  213 */     if (severity.equals("5"))
/*      */     {
/*  215 */       if (isAvailability) {
/*  216 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  219 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  222 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  224 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  226 */     if (severity.equals("1"))
/*      */     {
/*  228 */       if (isAvailability) {
/*  229 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  232 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  239 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  246 */     if (severity == null)
/*      */     {
/*  248 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  250 */     if (severity.equals("5"))
/*      */     {
/*  252 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  254 */     if (severity.equals("4"))
/*      */     {
/*  256 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  258 */     if (severity.equals("1"))
/*      */     {
/*  260 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  265 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  271 */     if (severity == null)
/*      */     {
/*  273 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  275 */     if (severity.equals("5"))
/*      */     {
/*  277 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  279 */     if (severity.equals("4"))
/*      */     {
/*  281 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  283 */     if (severity.equals("1"))
/*      */     {
/*  285 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  290 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  297 */     if (severity == null)
/*      */     {
/*  299 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  301 */     if (severity.equals("5"))
/*      */     {
/*  303 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  305 */     if (severity.equals("4"))
/*      */     {
/*  307 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  309 */     if (severity.equals("1"))
/*      */     {
/*  311 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  316 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  324 */     StringBuffer out = new StringBuffer();
/*  325 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  326 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  327 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  328 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  329 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  330 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  331 */     out.append("</tr>");
/*  332 */     out.append("</form></table>");
/*  333 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  340 */     if (val == null)
/*      */     {
/*  342 */       return "-";
/*      */     }
/*      */     
/*  345 */     String ret = FormatUtil.formatNumber(val);
/*  346 */     String troubleshootlink = OEMUtil.getOEMString("company.troubleshoot.link");
/*  347 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  350 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  354 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  362 */     StringBuffer out = new StringBuffer();
/*  363 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  364 */     out.append("<tr>");
/*  365 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  367 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  369 */     out.append("</tr>");
/*  370 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  374 */       if (j % 2 == 0)
/*      */       {
/*  376 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  380 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  383 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  385 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  388 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  392 */       out.append("</tr>");
/*      */     }
/*  394 */     out.append("</table>");
/*  395 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  396 */     out.append("<tr>");
/*  397 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  398 */     out.append("</tr>");
/*  399 */     out.append("</table>");
/*  400 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  406 */     StringBuffer out = new StringBuffer();
/*  407 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  408 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  409 */     out.append("<tr>");
/*  410 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  411 */     out.append("<tr>");
/*  412 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  413 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  414 */     out.append("</tr>");
/*  415 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  418 */       out.append("<tr>");
/*  419 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  420 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  421 */       out.append("</tr>");
/*      */     }
/*      */     
/*  424 */     out.append("</table>");
/*  425 */     out.append("</table>");
/*  426 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  431 */     if (severity.equals("0"))
/*      */     {
/*  433 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  437 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  444 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  457 */     StringBuffer out = new StringBuffer();
/*  458 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  459 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  461 */       out.append("<tr>");
/*  462 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  463 */       out.append("</tr>");
/*      */       
/*      */ 
/*  466 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  468 */         String borderclass = "";
/*      */         
/*      */ 
/*  471 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  473 */         out.append("<tr>");
/*      */         
/*  475 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  476 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  477 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  483 */     out.append("</table><br>");
/*  484 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  485 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  487 */       List sLinks = secondLevelOfLinks[0];
/*  488 */       List sText = secondLevelOfLinks[1];
/*  489 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  492 */         out.append("<tr>");
/*  493 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  494 */         out.append("</tr>");
/*  495 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  497 */           String borderclass = "";
/*      */           
/*      */ 
/*  500 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  502 */           out.append("<tr>");
/*      */           
/*  504 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  505 */           if (sLinks.get(i).toString().length() == 0) {
/*  506 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  509 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  511 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  515 */     out.append("</table>");
/*  516 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  523 */     StringBuffer out = new StringBuffer();
/*  524 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  525 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  527 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  529 */         out.append("<tr>");
/*  530 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  531 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  535 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  537 */           String borderclass = "";
/*      */           
/*      */ 
/*  540 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  542 */           out.append("<tr>");
/*      */           
/*  544 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  545 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  546 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  549 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  552 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  557 */     out.append("</table><br>");
/*  558 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  559 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  561 */       List sLinks = secondLevelOfLinks[0];
/*  562 */       List sText = secondLevelOfLinks[1];
/*  563 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  566 */         out.append("<tr>");
/*  567 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  568 */         out.append("</tr>");
/*  569 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  571 */           String borderclass = "";
/*      */           
/*      */ 
/*  574 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  576 */           out.append("<tr>");
/*      */           
/*  578 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  579 */           if (sLinks.get(i).toString().length() == 0) {
/*  580 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  583 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  585 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  589 */     out.append("</table>");
/*  590 */     return out.toString();
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
/*  603 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  606 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  609 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  612 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  615 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  618 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  621 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  624 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  632 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  637 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  642 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  647 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  652 */     if (val != null)
/*      */     {
/*  654 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  658 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  663 */     if (val == null) {
/*  664 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  668 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  673 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  679 */     if (val != null)
/*      */     {
/*  681 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  685 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  691 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  696 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  700 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  705 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  710 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  715 */     String hostaddress = "";
/*  716 */     String ip = request.getHeader("x-forwarded-for");
/*  717 */     if (ip == null)
/*  718 */       ip = request.getRemoteAddr();
/*  719 */     java.net.InetAddress add = null;
/*  720 */     if (ip.equals("127.0.0.1")) {
/*  721 */       add = java.net.InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  725 */       add = java.net.InetAddress.getByName(ip);
/*      */     }
/*  727 */     hostaddress = add.getHostName();
/*  728 */     if (hostaddress.indexOf('.') != -1) {
/*  729 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  730 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  734 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  739 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  745 */     if (severity == null)
/*      */     {
/*  747 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  749 */     if (severity.equals("5"))
/*      */     {
/*  751 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  753 */     if (severity.equals("1"))
/*      */     {
/*  755 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  760 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  765 */     ResultSet set = null;
/*  766 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  767 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  769 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  770 */       if (set.next()) { String str1;
/*  771 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  772 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  775 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  780 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  783 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  785 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  789 */     StringBuffer rca = new StringBuffer();
/*  790 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  791 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  794 */     int rcalength = key.length();
/*  795 */     String split = "6. ";
/*  796 */     int splitPresent = key.indexOf(split);
/*  797 */     String div1 = "";String div2 = "";
/*  798 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  800 */       if (rcalength > 180) {
/*  801 */         rca.append("<span class=\"rca-critical-text\">");
/*  802 */         getRCATrimmedText(key, rca);
/*  803 */         rca.append("</span>");
/*      */       } else {
/*  805 */         rca.append("<span class=\"rca-critical-text\">");
/*  806 */         rca.append(key);
/*  807 */         rca.append("</span>");
/*      */       }
/*  809 */       return rca.toString();
/*      */     }
/*  811 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  812 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  813 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  814 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  815 */     getRCATrimmedText(div1, rca);
/*  816 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  819 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  820 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  821 */     getRCATrimmedText(div2, rca);
/*  822 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  824 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  829 */     String[] st = msg.split("<br>");
/*  830 */     for (int i = 0; i < st.length; i++) {
/*  831 */       String s = st[i];
/*  832 */       if (s.length() > 180) {
/*  833 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  835 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  839 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  840 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  842 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  846 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  847 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  848 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  851 */       if (key == null) {
/*  852 */         return ret;
/*      */       }
/*      */       
/*  855 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  856 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  859 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  860 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  861 */       set = AMConnectionPool.executeQueryStmt(query);
/*  862 */       if (set.next())
/*      */       {
/*  864 */         String helpLink = set.getString("LINK");
/*  865 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  868 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  874 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  893 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  884 */         if (set != null) {
/*  885 */           AMConnectionPool.closeStatement(set);
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
/*  899 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  900 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  902 */       String entityStr = (String)keys.nextElement();
/*  903 */       String mmessage = temp.getProperty(entityStr);
/*  904 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  905 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  907 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  913 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
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
/*      */   private void debug(String debugMessage)
/*      */   {
/*  926 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  936 */     String des = new String();
/*  937 */     while (str.indexOf(find) != -1) {
/*  938 */       des = des + str.substring(0, str.indexOf(find));
/*  939 */       des = des + replace;
/*  940 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  942 */     des = des + str;
/*  943 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  950 */       if (alert == null)
/*      */       {
/*  952 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  954 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  956 */         return "&nbsp;";
/*      */       }
/*      */       
/*  959 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  961 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  964 */       int rcalength = test.length();
/*  965 */       if (rcalength < 300)
/*      */       {
/*  967 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  971 */       StringBuffer out = new StringBuffer();
/*  972 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  973 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  974 */       out.append("</div>");
/*  975 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  976 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  977 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  982 */       ex.printStackTrace();
/*      */     }
/*  984 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  990 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  995 */     ArrayList attribIDs = new ArrayList();
/*  996 */     ArrayList resIDs = new ArrayList();
/*  997 */     ArrayList entitylist = new ArrayList();
/*      */     
/*  999 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1001 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1003 */       String resourceid = "";
/* 1004 */       String resourceType = "";
/* 1005 */       if (type == 2) {
/* 1006 */         resourceid = (String)row.get(0);
/* 1007 */         resourceType = (String)row.get(3);
/*      */       }
/* 1009 */       else if (type == 3) {
/* 1010 */         resourceid = (String)row.get(0);
/* 1011 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1014 */         resourceid = (String)row.get(6);
/* 1015 */         resourceType = (String)row.get(7);
/*      */       }
/* 1017 */       resIDs.add(resourceid);
/* 1018 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1019 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1021 */       String healthentity = null;
/* 1022 */       String availentity = null;
/* 1023 */       if (healthid != null) {
/* 1024 */         healthentity = resourceid + "_" + healthid;
/* 1025 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1028 */       if (availid != null) {
/* 1029 */         availentity = resourceid + "_" + availid;
/* 1030 */         entitylist.add(availentity);
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
/* 1044 */     Properties alert = getStatus(entitylist);
/* 1045 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1050 */     int size = monitorList.size();
/*      */     
/* 1052 */     String[] severity = new String[size];
/*      */     
/* 1054 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1056 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1057 */       String resourceName1 = (String)row1.get(7);
/* 1058 */       String resourceid1 = (String)row1.get(6);
/* 1059 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1060 */       if (severity[j] == null)
/*      */       {
/* 1062 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1066 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1068 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1070 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1073 */         if (sev > 0) {
/* 1074 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1075 */           monitorList.set(k, monitorList.get(j));
/* 1076 */           monitorList.set(j, t);
/* 1077 */           String temp = severity[k];
/* 1078 */           severity[k] = severity[j];
/* 1079 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1085 */     int z = 0;
/* 1086 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1089 */       int i = 0;
/* 1090 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1093 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1097 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1101 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1103 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1106 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1110 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1113 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1114 */       String resourceName1 = (String)row1.get(7);
/* 1115 */       String resourceid1 = (String)row1.get(6);
/* 1116 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1117 */       if (hseverity[j] == null)
/*      */       {
/* 1119 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1124 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1126 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1129 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1132 */         if (hsev > 0) {
/* 1133 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1134 */           monitorList.set(k, monitorList.get(j));
/* 1135 */           monitorList.set(j, t);
/* 1136 */           String temp1 = hseverity[k];
/* 1137 */           hseverity[k] = hseverity[j];
/* 1138 */           hseverity[j] = temp1;
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
/* 1150 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1151 */     boolean forInventory = false;
/* 1152 */     String trdisplay = "none";
/* 1153 */     String plusstyle = "inline";
/* 1154 */     String minusstyle = "none";
/* 1155 */     String haidTopLevel = "";
/* 1156 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1158 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1160 */         haidTopLevel = request.getParameter("haid");
/* 1161 */         forInventory = true;
/* 1162 */         trdisplay = "table-row;";
/* 1163 */         plusstyle = "none";
/* 1164 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1171 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1174 */     ArrayList listtoreturn = new ArrayList();
/* 1175 */     StringBuffer toreturn = new StringBuffer();
/* 1176 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1177 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1178 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1180 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1182 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1183 */       String childresid = (String)singlerow.get(0);
/* 1184 */       String childresname = (String)singlerow.get(1);
/* 1185 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1186 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1187 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1188 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1189 */       String unmanagestatus = (String)singlerow.get(5);
/* 1190 */       String actionstatus = (String)singlerow.get(6);
/* 1191 */       String linkclass = "monitorgp-links";
/* 1192 */       String titleforres = childresname;
/* 1193 */       String titilechildresname = childresname;
/* 1194 */       String childimg = "/images/trcont.png";
/* 1195 */       String flag = "enable";
/* 1196 */       String dcstarted = (String)singlerow.get(8);
/* 1197 */       String configMonitor = "";
/* 1198 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1199 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1201 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1203 */       if (singlerow.get(7) != null)
/*      */       {
/* 1205 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1207 */       String haiGroupType = "0";
/* 1208 */       if ("HAI".equals(childtype))
/*      */       {
/* 1210 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1212 */       childimg = "/images/trend.png";
/* 1213 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1214 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1215 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1217 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1219 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1221 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1222 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1225 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1227 */         linkclass = "disabledtext";
/* 1228 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1230 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1231 */       String availmouseover = "";
/* 1232 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1234 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1236 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1237 */       String healthmouseover = "";
/* 1238 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1240 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1243 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1244 */       int spacing = 0;
/* 1245 */       if (level >= 1)
/*      */       {
/* 1247 */         spacing = 40 * level;
/*      */       }
/* 1249 */       if (childtype.equals("HAI"))
/*      */       {
/* 1251 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1252 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1253 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1255 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1256 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1257 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1258 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1259 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1260 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1261 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1262 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1263 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1264 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1265 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1267 */         if (!forInventory)
/*      */         {
/* 1269 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1272 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1274 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1276 */           actions = editlink + actions;
/*      */         }
/* 1278 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1280 */           actions = actions + associatelink;
/*      */         }
/* 1282 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1283 */         String arrowimg = "";
/* 1284 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1286 */           actions = "";
/* 1287 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1288 */           checkbox = "";
/* 1289 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1291 */         if (isIt360)
/*      */         {
/* 1293 */           actionimg = "";
/* 1294 */           actions = "";
/* 1295 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1296 */           checkbox = "";
/*      */         }
/*      */         
/* 1299 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1301 */           actions = "";
/*      */         }
/* 1303 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1305 */           checkbox = "";
/*      */         }
/*      */         
/* 1308 */         String resourcelink = "";
/*      */         
/* 1310 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1312 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1316 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1319 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1320 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1321 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1322 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1323 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1324 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1325 */         if (!isIt360)
/*      */         {
/* 1327 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1331 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1334 */         toreturn.append("</tr>");
/* 1335 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1337 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1338 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1342 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1343 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1346 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1350 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1352 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1353 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1354 */             toreturn.append(assocMessage);
/* 1355 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1356 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1357 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1358 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1364 */         String resourcelink = null;
/* 1365 */         boolean hideEditLink = false;
/* 1366 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1368 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1369 */           hideEditLink = true;
/* 1370 */           if (isIt360)
/*      */           {
/* 1372 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1376 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1378 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1380 */           hideEditLink = true;
/* 1381 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/* 1382 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1387 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1390 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1391 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1392 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1393 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1394 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1395 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1396 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1397 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1398 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1399 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1400 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1401 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1402 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1404 */         if (hideEditLink)
/*      */         {
/* 1406 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1408 */         if (!forInventory)
/*      */         {
/* 1410 */           removefromgroup = "";
/*      */         }
/* 1412 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1413 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1414 */           actions = actions + configcustomfields;
/*      */         }
/* 1416 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1418 */           actions = editlink + actions;
/*      */         }
/* 1420 */         String managedLink = "";
/* 1421 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1423 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1424 */           actions = "";
/* 1425 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1426 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1429 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1431 */           checkbox = "";
/*      */         }
/*      */         
/* 1434 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1436 */           actions = "";
/*      */         }
/* 1438 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1439 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1440 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1441 */         if (isIt360)
/*      */         {
/* 1443 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1447 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1449 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1450 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1451 */         if (!isIt360)
/*      */         {
/* 1453 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1457 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1459 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1462 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1469 */       StringBuilder toreturn = new StringBuilder();
/* 1470 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1471 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1472 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1473 */       String title = "";
/* 1474 */       message = EnterpriseUtil.decodeString(message);
/* 1475 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1476 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1477 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1479 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1481 */       else if ("5".equals(severity))
/*      */       {
/* 1483 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1487 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1489 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1490 */       toreturn.append(v);
/*      */       
/* 1492 */       toreturn.append(link);
/* 1493 */       if (severity == null)
/*      */       {
/* 1495 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1497 */       else if (severity.equals("5"))
/*      */       {
/* 1499 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1501 */       else if (severity.equals("4"))
/*      */       {
/* 1503 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1505 */       else if (severity.equals("1"))
/*      */       {
/* 1507 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1512 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1514 */       toreturn.append("</a>");
/* 1515 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1519 */       ex.printStackTrace();
/*      */     }
/* 1521 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1528 */       StringBuilder toreturn = new StringBuilder();
/* 1529 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1530 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1531 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1532 */       if (message == null)
/*      */       {
/* 1534 */         message = "";
/*      */       }
/*      */       
/* 1537 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1538 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1540 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1541 */       toreturn.append(v);
/*      */       
/* 1543 */       toreturn.append(link);
/*      */       
/* 1545 */       if (severity == null)
/*      */       {
/* 1547 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1549 */       else if (severity.equals("5"))
/*      */       {
/* 1551 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1553 */       else if (severity.equals("1"))
/*      */       {
/* 1555 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1560 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1562 */       toreturn.append("</a>");
/* 1563 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1569 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1572 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1573 */     if (invokeActions != null) {
/* 1574 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1575 */       while (iterator.hasNext()) {
/* 1576 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1577 */         if (actionmap.containsKey(actionid)) {
/* 1578 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1583 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1587 */     String actionLink = "";
/* 1588 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1589 */     String query = "";
/* 1590 */     ResultSet rs = null;
/* 1591 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1592 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1593 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1594 */       actionLink = "method=" + methodName;
/*      */     }
/* 1596 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1597 */       actionLink = methodName;
/*      */     }
/* 1599 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1600 */     Iterator itr = methodarglist.iterator();
/* 1601 */     boolean isfirstparam = true;
/* 1602 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1603 */     while (itr.hasNext()) {
/* 1604 */       HashMap argmap = (HashMap)itr.next();
/* 1605 */       String argtype = (String)argmap.get("TYPE");
/* 1606 */       String argname = (String)argmap.get("IDENTITY");
/* 1607 */       String paramname = (String)argmap.get("PARAMETER");
/* 1608 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1609 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1610 */         isfirstparam = false;
/* 1611 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1613 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1617 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1621 */         actionLink = actionLink + "&";
/*      */       }
/* 1623 */       String paramValue = null;
/* 1624 */       String tempargname = argname;
/* 1625 */       if (commonValues.getProperty(tempargname) != null) {
/* 1626 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1629 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1630 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1631 */           if (dbType.equals("mysql")) {
/* 1632 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1635 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1637 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1639 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1640 */             if (rs.next()) {
/* 1641 */               paramValue = rs.getString("VALUE");
/* 1642 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1646 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1650 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1653 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1658 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1659 */           paramValue = rowId;
/*      */         }
/* 1661 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1662 */           paramValue = managedObjectName;
/*      */         }
/* 1664 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1665 */           paramValue = resID;
/*      */         }
/* 1667 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1668 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1671 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1673 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1674 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1675 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1677 */     return actionLink;
/*      */   }
/*      */   
/* 1680 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1681 */     String dependentAttribute = null;
/* 1682 */     String align = "left";
/*      */     
/* 1684 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1685 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1686 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1687 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1688 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1689 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1690 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1691 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1692 */       align = "center";
/*      */     }
/*      */     
/* 1695 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1696 */     String actualdata = "";
/*      */     
/* 1698 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1699 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1700 */         actualdata = availValue;
/*      */       }
/* 1702 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1703 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1707 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1708 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1711 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1717 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1718 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1719 */       toreturn.append("<table>");
/* 1720 */       toreturn.append("<tr>");
/* 1721 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1722 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1723 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1724 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1725 */         String toolTip = "";
/* 1726 */         String hideClass = "";
/* 1727 */         String textStyle = "";
/* 1728 */         boolean isreferenced = true;
/* 1729 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1730 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1731 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1732 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1734 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1735 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1736 */           while (valueList.hasMoreTokens()) {
/* 1737 */             String dependentVal = valueList.nextToken();
/* 1738 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1739 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1740 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1742 */               toolTip = "";
/* 1743 */               hideClass = "";
/* 1744 */               isreferenced = false;
/* 1745 */               textStyle = "disabledtext";
/* 1746 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1750 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1751 */           toolTip = "";
/* 1752 */           hideClass = "";
/* 1753 */           isreferenced = false;
/* 1754 */           textStyle = "disabledtext";
/* 1755 */           if (dependentImageMap != null) {
/* 1756 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1757 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1760 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1764 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1765 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1766 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1767 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1768 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1769 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1771 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1772 */           if (isreferenced) {
/* 1773 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1777 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1778 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1779 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1780 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1781 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1782 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1784 */           toreturn.append("</span>");
/* 1785 */           toreturn.append("</a>");
/* 1786 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1789 */       toreturn.append("</tr>");
/* 1790 */       toreturn.append("</table>");
/* 1791 */       toreturn.append("</td>");
/*      */     } else {
/* 1793 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1796 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1800 */     String colTime = null;
/* 1801 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1802 */     if ((rows != null) && (rows.size() > 0)) {
/* 1803 */       Iterator<String> itr = rows.iterator();
/* 1804 */       String maxColQuery = "";
/* 1805 */       for (;;) { if (itr.hasNext()) {
/* 1806 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1807 */           ResultSet maxCol = null;
/*      */           try {
/* 1809 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1810 */             while (maxCol.next()) {
/* 1811 */               if (colTime == null) {
/* 1812 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1815 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1824 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1826 */               if (maxCol != null)
/* 1827 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1829 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1824 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1826 */               if (maxCol != null)
/* 1827 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1829 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1834 */     return colTime;
/*      */   }
/*      */   
/* 1837 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1838 */     tablename = null;
/* 1839 */     ResultSet rsTable = null;
/* 1840 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1842 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1843 */       while (rsTable.next()) {
/* 1844 */         tablename = rsTable.getString("DATATABLE");
/* 1845 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1846 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1859 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1850 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1853 */         if (rsTable != null)
/* 1854 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1856 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1862 */     String argsList = "";
/* 1863 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1865 */       if (showArgsMap.get(row) != null) {
/* 1866 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1867 */         if (showArgslist != null) {
/* 1868 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1869 */             if (argsList.trim().equals("")) {
/* 1870 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1873 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1880 */       e.printStackTrace();
/* 1881 */       return "";
/*      */     }
/* 1883 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1888 */     String argsList = "";
/* 1889 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1892 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1894 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1895 */         if (hideArgsList != null)
/*      */         {
/* 1897 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1899 */             if (argsList.trim().equals(""))
/*      */             {
/* 1901 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1905 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1913 */       ex.printStackTrace();
/*      */     }
/* 1915 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1919 */     StringBuilder toreturn = new StringBuilder();
/* 1920 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1927 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1928 */       Iterator itr = tActionList.iterator();
/* 1929 */       while (itr.hasNext()) {
/* 1930 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1931 */         String confirmmsg = "";
/* 1932 */         String link = "";
/* 1933 */         String isJSP = "NO";
/* 1934 */         HashMap tactionMap = (HashMap)itr.next();
/* 1935 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1936 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1937 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1938 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1939 */           (actionmap.containsKey(actionId))) {
/* 1940 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1941 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1942 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1943 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1944 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1946 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1952 */           if (isTableAction) {
/* 1953 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1956 */             tableName = "Link";
/* 1957 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1958 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1959 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1960 */             toreturn.append("</a></td>");
/*      */           }
/* 1962 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1963 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1964 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1965 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1971 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1977 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1979 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1980 */       Properties prop = (Properties)node.getUserObject();
/* 1981 */       String mgID = prop.getProperty("label");
/* 1982 */       String mgName = prop.getProperty("value");
/* 1983 */       String isParent = prop.getProperty("isParent");
/* 1984 */       int mgIDint = Integer.parseInt(mgID);
/* 1985 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1987 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1989 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1990 */       if (node.getChildCount() > 0)
/*      */       {
/* 1992 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1994 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1996 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1998 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2002 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2007 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2009 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2011 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2013 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2017 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2020 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2021 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2023 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2027 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2029 */       if (node.getChildCount() > 0)
/*      */       {
/* 2031 */         builder.append("<UL>");
/* 2032 */         printMGTree(node, builder);
/* 2033 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2038 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2039 */     StringBuffer toReturn = new StringBuffer();
/* 2040 */     String table = "-";
/*      */     try {
/* 2042 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2043 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2044 */       float total = 0.0F;
/* 2045 */       while (it.hasNext()) {
/* 2046 */         String attName = (String)it.next();
/* 2047 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2048 */         boolean roundOffData = false;
/* 2049 */         if ((data != null) && (!data.equals(""))) {
/* 2050 */           if (data.indexOf(",") != -1) {
/* 2051 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2054 */             float value = Float.parseFloat(data);
/* 2055 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2058 */             total += value;
/* 2059 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2062 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2067 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2068 */       while (attVsWidthList.hasNext()) {
/* 2069 */         String attName = (String)attVsWidthList.next();
/* 2070 */         String data = (String)attVsWidthProps.get(attName);
/* 2071 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2072 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2073 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2074 */         String className = (String)graphDetails.get("ClassName");
/* 2075 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2076 */         if (percentage < 1.0F)
/*      */         {
/* 2078 */           data = percentage + "";
/*      */         }
/* 2080 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2082 */       if (toReturn.length() > 0) {
/* 2083 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2087 */       e.printStackTrace();
/*      */     }
/* 2089 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2095 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2096 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2097 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2098 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2099 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2100 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2101 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2102 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2103 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2106 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2107 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2108 */       splitvalues[0] = multiplecondition.toString();
/* 2109 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2112 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2117 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2118 */     if (thresholdType != 3) {
/* 2119 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2120 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2121 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2122 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2123 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2124 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2126 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2127 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2128 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2129 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2130 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2131 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2133 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2134 */     if (updateSelected != null) {
/* 2135 */       updateSelected[0] = "selected";
/*      */     }
/* 2137 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2142 */       StringBuffer toreturn = new StringBuffer("");
/* 2143 */       if (commaSeparatedMsgId != null) {
/* 2144 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2145 */         int count = 0;
/* 2146 */         while (msgids.hasMoreTokens()) {
/* 2147 */           String id = msgids.nextToken();
/* 2148 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2149 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2150 */           count++;
/* 2151 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2152 */             if (toreturn.length() == 0) {
/* 2153 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2155 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2156 */             if (!image.trim().equals("")) {
/* 2157 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2159 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2160 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2163 */         if (toreturn.length() > 0) {
/* 2164 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2168 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2171 */       e.printStackTrace(); }
/* 2172 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2178 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2184 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/* 2185 */   static { _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/* 2186 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2187 */     _jspx_dependants.put("/jsp/includes/MonitorDiscoveryStatus.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonblur_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fstyle_005fsize_005freadonly_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2217 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonblur_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fstyle_005fsize_005freadonly_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2244 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2248 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2249 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2250 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2251 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2252 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2253 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2254 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2255 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2256 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/* 2257 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/* 2258 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2259 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2260 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2261 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonblur_005fnobody.release();
/* 2262 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/* 2263 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.release();
/* 2264 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2265 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2266 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2267 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fstyle_005fsize_005freadonly_005fproperty_005fnobody.release();
/* 2268 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.release();
/* 2269 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2276 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2279 */     JspWriter out = null;
/* 2280 */     Object page = this;
/* 2281 */     JspWriter _jspx_out = null;
/* 2282 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2286 */       response.setContentType("text/html;charset=UTF-8");
/* 2287 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2289 */       _jspx_page_context = pageContext;
/* 2290 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2291 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2292 */       session = pageContext.getSession();
/* 2293 */       out = pageContext.getOut();
/* 2294 */       _jspx_out = out;
/*      */       
/* 2296 */       out.write("<!DOCTYPE html>\n");
/* 2297 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\t\n");
/* 2298 */       out.write("\n\n\n\n\n\n\n\n\n\n");
/* 2299 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2301 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2302 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2304 */       out.write(10);
/* 2305 */       out.write(10);
/* 2306 */       out.write(10);
/* 2307 */       out.write(10);
/* 2308 */       out.write(10);
/* 2309 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 2310 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/* 2312 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/* 2313 */       out.write(10);
/* 2314 */       out.write("\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/diagnosepage.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/hostdiscoveryform.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/sap.js\"></SCRIPT>\n");
/* 2315 */       JspRuntimeLibrary.include(request, response, "/jsp/includes/monitorGroups.jsp", out, false);
/* 2316 */       out.write("\n\n<script>\nfunction loadSite()\n{\n\tdocument.AMActionForm.haid.options.length=0;\n\tvar formCustomerId = document.AMActionForm.organization.value;\n\tvar siteName;\n\tvar siteId;\n\tvar customerId;\n\tvar rowCount=0;\n\tdocument.AMActionForm.haid.options[rowCount++] = new Option('-Select Site-','-'); //No I18N\n\t");
/* 2317 */       if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
/*      */         return;
/* 2319 */       out.write("\n}\n\nfunction resetname(name)\n{\n\tif(name='groupname')\n\t{\n\t\tdocument.AMActionForm.groupname.value='';\n\t}\n\tif(name='subgroupname')\n\t{\n\t\tdocument.AMActionForm.subgroupname.value='';\n\t}\n}\nfunction createGroup()\n{\n\tif(document.AMActionForm.groupname.value=='')\n\t{\n\t\talert(\"");
/* 2320 */       out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/* 2321 */       out.write("\");\n\t\thideDiv('group');\n\t\tshowDiv('creategroup');\n\t\tdocument.AMActionForm.groupname.focus();\n\t\treturn false;\n\t}\n\telse\n\t{\n\t\thideDiv('creategroup');\n\t\tvar a=document.AMActionForm.groupname.value;\n\t\turl=\"/adminAction.do?method=createMonitorGroup&groupname=\"+encodeURIComponent(a);\n\t\thttp.open(\"GET\",url,true);\n\t\thttp.onreadystatechange = getActionTypes;\n\t\thttp.send(null);\n\t\tshowDiv('group');\n\t}\n\n}  \nfunction check()\n{\n\thideDiv(\"creategroup\");\n\thideDiv(\"createsubgroup\");\n\thideDiv(\"groupmessage\");\n\tvar flag='");
/* 2322 */       out.print(com.adventnet.appmanager.util.Constants.subGroupsEnabled);
/* 2323 */       out.write("';\n\tif(flag==\"true\")\n\t{\n\t\tif(trimAll(document.AMActionForm.haid.value) == '' || document.AMActionForm.haid.value=='-')\n\t\t{\n\t\t\thideDiv(\"subgroup\");\n\t\t\tshowDiv(\"group\");\n\t\t}\n\t\telse\n\t\t{\n\t\t\thideDiv(\"group\");\n\t\t\tshowDiv(\"subgroup\");\n\t\t}\n\t}\n\telse\n\t{\n\t\tshowDiv(\"group\");\n\t}\n}\nfunction createsubGroup()\n{\n\tif(trimAll(document.AMActionForm.haid.value) == '' || document.AMActionForm.haid.value=='-')\n\t{\n\t\talert(\"");
/* 2324 */       out.print(FormatUtil.getString("am.webclient.monitorsubgroup.monitoralert"));
/* 2325 */       out.write("\");\t\t\n\t\tdocument.AMActionForm.haid.focus();\n\t}\n\telse\n\t{\n\t\tif(document.AMActionForm.subgroupname.value=='')\n\t\t{\n\t\t\talert(\"");
/* 2326 */       out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/* 2327 */       out.write("\");\n\t\t\tdocument.AMActionForm.subgroupname.focus();\n\t\t\treturn false;\n\t\t}\n\t\telse\n\t\t{\n\t\t\thideDiv('createsubgroup');\n\t\t\tvar a=document.AMActionForm.subgroupname.value;\n\t\t\tvar haid=document.AMActionForm.haid.value;\n\t\t\turl=\"/adminAction.do?method=createSubGroup&haid=\"+haid+\"&subgroupname=\"+encodeURIComponent(a);\n\t\t\thttp.open(\"GET\",url,true);\n\t\t\thttp.onreadystatechange = getActionTypes;\n\t\t\thttp.send(null);\n\t\t}\n\t\tshowDiv('subgroup');\n    }\n\n}  \n\n function getActionTypes()\n{\n    if(http.readyState == 4)\n    {\n\t\tvar result = http.responseText;\n\t\tvar id=result;\n\t\tvar stringtokens=id.split(\",\");\n\t\tsid = stringtokens[2];\n\t\tsname=stringtokens[1];\n\t\tsname=decodeURIComponent(stringtokens[1]);\n\t\tsmessage=stringtokens[0];\n\t\tif (sname==null || sname=='undefined')\n\t\t{\n\t\t\tshowDiv(\"groupmessage\");\n\t\t\tdocument.getElementById(\"groupmessage\").innerHTML=smessage;\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.AMActionForm.haid.options[document.AMActionForm.haid.length] =new Option(sname,sid,false,true);\n\t\t\thideDiv(\"creategroup\");\n\t\t\thideDiv(\"createsubgroup\");\n");
/* 2328 */       out.write("\t\t\thideDiv(\"group\");\n\t\t\tshowDiv(\"subgroup\");\n\t\t\tshowDiv(\"groupmessage\");\n\t\t\tdocument.getElementById(\"groupmessage\").innerHTML=smessage;\n\t  \t}\n\t}\n}\n\nfunction formReload()\n{\n\tvar v = document.AMActionForm.type.value;\n    if((v==\"WTA:55555\")||(v==\".Net:9080\"))\n    {\n\t\tdocument.AMActionForm.action=\"/adminAction.do?method=reloadHostDiscoveryForm&type=\"+v+\"&hideFieldsForIT360=");
/* 2329 */       out.print(request.getParameter("hideFieldsForIT360"));
/* 2330 */       out.write("\";\n\t\tenableAll();\n\t\tdocument.AMActionForm.submit();\n    }\n    else\n    {\n\t\tdocument.AMActionForm.action=\"/adminAction.do?method=reloadHostDiscoveryForm&type=\"+v+\"&hideFieldsForIT360=");
/* 2331 */       out.print(request.getParameter("hideFieldsForIT360"));
/* 2332 */       out.write("\";\n\t\tenableAll();\n\t\tdocument.AMActionForm.submit();\n    }\n}\n\nfunction validateform()\n{\n    if(trimAll(document.AMActionForm.host.value) == '')\n\t{\n        alert(\"");
/* 2333 */       out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.name"));
/* 2334 */       out.write("\");\n\t\tdocument.AMActionForm.host.select();\n\t\treturn false;\n\t}\n\tdocument.AMActionForm.host.value=trimAll(document.AMActionForm.host.value);\n\tif(document.AMActionForm.usedRouterString.checked)\n\t{\n\t\tif(trimAll(document.AMActionForm.routerString.value) == '')\n\t\t{\n\t\t\talert(\"");
/* 2335 */       out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.routerString"));
/* 2336 */       out.write("\");\n\t\t\tdocument.AMActionForm.routerString.select();\n\t\t\treturn false;\n\t\t}\n\t}\n\tdocument.AMActionForm.routerString.value=trimAll(document.AMActionForm.routerString.value);\n\tif(trimAll(document.AMActionForm.logonClient.value) == '')\n\t{\n\t\talert(\"");
/* 2337 */       out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.logonclient"));
/* 2338 */       out.write("\");\n\t\tdocument.AMActionForm.logonClient.select();\n\t\treturn false;\n\t}\n\tif(trimAll(document.AMActionForm.systemNumber.value) == '')\n\t{\n\t\talert(\"");
/* 2339 */       out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.systemnumber"));
/* 2340 */       out.write("\");\n\t\tdocument.AMActionForm.systemNumber.select();\n\t\treturn false;\n\t}\n\tvar temp = trimAll(document.AMActionForm.pollInterval.value);\n\tif((temp == '') || !(isPositiveInteger(temp)) || (temp == '0'))\n\t{\n\t\talert(\"");
/* 2341 */       out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.polling"));
/* 2342 */       out.write("\");\n\t\tdocument.AMActionForm.pollInterval.select();\n\t\treturn;\n\t}\n\tif(trimAll(document.AMActionForm.username.value) == '')\n\t{\n\t\talert(\"");
/* 2343 */       out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.username"));
/* 2344 */       out.write("\");\n\t\tdocument.AMActionForm.username.select();\n\t\treturn false;\n\t}\n\tif(trimAll(document.AMActionForm.password.value) == '')\n\t{\n\t\talert(\"");
/* 2345 */       out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.password"));
/* 2346 */       out.write("\");\n\t\tdocument.AMActionForm.username.select();\n\t\treturn false;\n\t}\n\treturn true;\n}\n\nfunction fnFormSubmit()\n{\n\t");
/* 2347 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */         return;
/* 2349 */       out.write(10);
/* 2350 */       out.write(9);
/*      */       
/* 2352 */       FreeEditionDetails freeeditiondetails = FreeEditionDetails.getFreeEditionDetails();
/* 2353 */       String usertype = freeeditiondetails.getUserType();
/* 2354 */       if ((usertype != null) && (usertype.equals("F")) && (!FreeEditionDetails.getFreeEditionDetails().isSAPAllowed()))
/*      */       {
/*      */ 
/* 2357 */         out.write("\n\t\talert(\"");
/* 2358 */         out.print(FormatUtil.getString("am.webclient.sap.freeedition.notsupported"));
/* 2359 */         out.write("\");\n\t\treturn;\n\t");
/*      */       }
/* 2361 */       out.write("\n\tif(trimAll(document.AMActionForm.displayname.value) == '')\n\t{\n\t\talert(\"");
/* 2362 */       out.print(FormatUtil.getString("am.webclient.newscript.alert.displaynameempty"));
/* 2363 */       out.write("\");\n\t\tdocument.AMActionForm.displayname.select();\n\t\treturn false;\n\t}\n\tvar disp=document.AMActionForm.displayname.value;\n    if(displayNameHasQuotes(disp,'");
/* 2364 */       out.print(FormatUtil.getString("am.webclient.specialchar.alert.displayname"));
/* 2365 */       out.write("'))\n    {\n        document.AMActionForm.displayname.focus();\n        return ;\n    }\n\tif(!validateform())\n\t{\n\t\treturn;\n\t}\n\tif(trimAll(document.AMActionForm.version.value) == '')\n\t{\n\t\tif (\"");
/* 2366 */       out.print(EnterpriseUtil.isAdminServer());
/* 2367 */       out.write("\" == \"true\") {\n\t\t\talert(\"");
/* 2368 */       out.print(FormatUtil.getString("am.webclient.admin.add.monitor.sap.enter.ccms.set.alert.text"));
/* 2369 */       out.write("\");\n\t\t\treturn false;\t\t\t\n\t\t} else {\n\t\t\talert(\"");
/* 2370 */       out.print(FormatUtil.getString("am.webclient.ccms.newmonitor.ccmsalert"));
/* 2371 */       out.write("\");\n\t\t\treturn false;\t\t\t\n\t\t}\n\t}\n\tif(trimAll(document.AMActionForm.haid.value) != '' && document.AMActionForm.haid.value!=\"-\")\n\t{\n\t\tdocument.AMActionForm.addtoha.value=\"true\";\n\t}\n\n    ");
/*      */       
/* 2373 */       if (EnterpriseUtil.isAdminServer())
/*      */       {
/*      */ 
/* 2376 */         out.write("                                \n        if (document.AMActionForm.masSelectType[1].checked) {\n        \tvar selectedVal=document.AMActionForm.masGroupName.value;\n        \tif (selectedVal != null && selectedVal == \"-\") {\n                alert('");
/* 2377 */         out.print(FormatUtil.getString("am.webclient.admin.add.monitor.select.masgroup.text"));
/* 2378 */         out.write("');\n                document.AMActionForm.masGroupName.focus();\n                return;\n            }                                \t\n        } else if (document.AMActionForm.masSelectType[2].checked) {\n        \tselectedVal=document.AMActionForm.selectedServer.value;\n        \tif (selectedVal != null && selectedVal == \"-\") {\n                alert('");
/* 2379 */         out.print(FormatUtil.getString("am.webclient.admin.addmonitor.select.mas.text"));
/* 2380 */         out.write("');\n                document.AMActionForm.selectedServer.focus();\n                return;\n            }                                 \t\n        }\n    ");
/*      */       }
/*      */       
/*      */ 
/* 2384 */       out.write(" \n /***  IT360-1762 ISSUES CHANGES STARTS HERE ***/\n\t\n\t");
/* 2385 */       if (EnterpriseUtil.isIt360MSPEdition()) {
/* 2386 */         out.write("\n\t\n    if (document.AMActionForm.organization.value== \"-\")\n     {\n    \talert(\"");
/* 2387 */         out.print(FormatUtil.getString("it360.addnewmonitor.err.checkcustomer"));
/* 2388 */         out.write("\");\n    \treturn;\n     }\n\n    if (trimAll(document.AMActionForm.haid.value) == '' || document.AMActionForm.haid.value== \"-\")\n     {\n        alert(\"");
/* 2389 */         out.print(FormatUtil.getString("it360.addnewmonitor.err.checksite"));
/* 2390 */         out.write("\");\n        return;\n     }\n    ");
/*      */       }
/* 2392 */       out.write("\n    \n    ");
/*      */       
/* 2394 */       IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2395 */       _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2396 */       _jspx_th_c_005fif_005f3.setParent(null);
/*      */       
/* 2398 */       _jspx_th_c_005fif_005f3.setTest("${checkForMonitorGroup}");
/* 2399 */       int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2400 */       if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */         for (;;) {
/* 2402 */           out.write("\n\t\tvar haidValue = document.AMActionForm.haid.value\n\t\tif(haidValue == '-' || haidValue == ''){\n\t\talert(\"");
/* 2403 */           out.print(FormatUtil.getString("am.webclient.newmonitor.selectmg.text"));
/* 2404 */           out.write("\")\n\t\treturn;\n\t\t}\n\t");
/* 2405 */           int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2406 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2410 */       if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2411 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*      */       }
/*      */       else {
/* 2414 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2415 */         out.write("\n\n/***  IT360-1762 ISSUES CHANGES ENDS HERE ***/\t\n\n\tdocument.AMActionForm.submit();\n}\n\nfunction showServiceDetail()\n{\n\tvar hostNameToSend = trimAll(document.AMActionForm.host.value) ;\n\tif(hostNameToSend==\"\" || \"");
/* 2416 */         out.print(EnterpriseUtil.isAdminServer());
/* 2417 */         out.write("\" == \"true\")\n\t{\n\t\treturn;\n\t}\n\tif(http)\n\t{\n\t\tvar url = '/jsp/HostNameDiscovery.jsp?hostName='+escape(hostNameToSend);\n\t\thttp.open(\"GET\",url,true);\n\t\thttp.onreadystatechange = handleServiceMessage;\n\t\thttp.send(null);\n\t}\n\treturn false;\n}\n\nfunction handleServiceMessage()\n{\n\tif (http.readyState == 4)\n\t{\n\t\tvar result = http.responseText ;\n\t\tvar element=(document.getElementsByName(\"host\"))[0];\n\t\tvar temp=null;\n\t\tvar isPointerReq=false;\n\t\tvar red=\"#FF0000\";\n\t\tddrivetip(element,temp,result,isPointerReq,true,null,red);\n\t\tsetTimeout(\"hideDialog()\",6000);\n\t}\n}\n\nvar http1;\nfunction loadCCMS()\n{\n\tif(!validateform())\n\t{\n\t\treturn;\n\t}\n\tvar hostNameToTest = trimAll(document.AMActionForm.host.value);\n\tvar logonClient = trimAll(document.AMActionForm.logonClient.value);\n\tvar systemNumber = trimAll(document.AMActionForm.systemNumber.value);\n\tvar language = trimAll(document.AMActionForm.language.value);\n\tvar username = trimAll(document.AMActionForm.username.value);\n\tvar password = trimAll(document.AMActionForm.password.value);\n\tvar typeToSend = document.AMActionForm.type.value;\n");
/* 2418 */         out.write("\tvar url = '/jsp/HostServiceDiscovery.jsp?hostName='+escape(hostNameToTest)+'&portNumber='+escape(systemNumber)+'&typeToSend='+escape(typeToSend)+'&version='+escape(typeToSend)+'&logonClient='+escape(logonClient)+'&language='+escape(language)+'&username='+escape(username)+'&password='+escape(password);\n\thttp1=getHTTPObject();\n\thttp1.onreadystatechange = handleMessage;\n\thttp1.open(\"GET\",url,true);\n\thttp1.send(null);\n}\nfunction handleMessage()\n{\n\tif (http1.readyState == 4)\n\t{\n\t\tvar result = http1.responseText;\n\t\tif(trimAll(result)=='')\n\t\t{\n\t\t\tMM_openBrWindow('/jsp/sap/listccmsmonitorsets.jsp','CCMS','width=480,height=640,scrollbars=yes');\n\t\t}\n\t\tvar element=(document.getElementsByName(\"systemNumber\"))[0];\n\t\tvar temp=null;\n\t\tvar isPointerReq=false;\n\t\tvar red=\"#FF0000\";\n\t\tddrivetip(element,temp,result,isPointerReq,true,red,300);\n\t\tsetTimeout(\"hideDialog()\",60000);\n\t}\n}\nfunction hideDialog()\n{\n\tstartHideFade(\"dhtmltooltip\",0.04);\n}\n\n\n\t\n\nfunction myOnLoad(){\n\tif(");
/* 2419 */         out.print(EnterpriseUtil.isAdminServer());
/* 2420 */         out.write(") {\n\t\tvar radioObj = document.AMActionForm.masSelectType;\n\t\tif (radioObj !=null && radioObj != \"undefined\") {\n\t\t\tvar val='0';\n\t\t\tif (radioObj[1].checked) {\n\t\t\t\tval='1';\n\t\t\t} else if (radioObj[2].checked){\n\t\t\t\tval='2';\n\t\t\t}\n\t\t\tshowAsPerSelection(val);\n\t\t}\t\n\t}\n}\n</script>\n");
/*      */         
/* 2422 */         String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/* 2423 */         if (hideFieldsForIT360 == null)
/*      */         {
/* 2425 */           hideFieldsForIT360 = (String)request.getAttribute("hideFieldsForIT360");
/*      */         }
/*      */         
/* 2428 */         boolean hideFields = false;
/* 2429 */         String hideStyle = "";
/* 2430 */         if ((hideFieldsForIT360 != null) && (hideFieldsForIT360.equals("true")))
/*      */         {
/* 2432 */           hideFields = true;
/* 2433 */           hideStyle = "hideContent";
/*      */         }
/* 2435 */         boolean isDiscoveryComplete = false;
/* 2436 */         boolean isDiscoverySuccess = false;
/*      */         
/* 2438 */         request.setAttribute("HelpKey", "Configure SAP CCMS");
/*      */         
/* 2440 */         out.write(10);
/* 2441 */         out.write(10);
/*      */         
/* 2443 */         InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2444 */         _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2445 */         _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */         
/* 2447 */         _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2448 */         int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2449 */         if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */           for (;;) {
/* 2451 */             out.write(10);
/* 2452 */             if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */               return;
/* 2454 */             out.write(10);
/* 2455 */             if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */               return;
/* 2457 */             out.write(10);
/* 2458 */             out.write(10);
/*      */             
/* 2460 */             PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2461 */             _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2462 */             _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */             
/* 2464 */             _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*      */             
/* 2466 */             _jspx_th_tiles_005fput_005f2.setType("string");
/* 2467 */             int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2468 */             if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2469 */               if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2470 */                 out = _jspx_page_context.pushBody();
/* 2471 */                 _jspx_th_tiles_005fput_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2472 */                 _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */               }
/*      */               for (;;) {
/* 2475 */                 out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n\t\t<td width=\"70%\" valign=\"top\">\n");
/*      */                 
/* 2477 */                 FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/* 2478 */                 _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2479 */                 _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                 
/* 2481 */                 _jspx_th_html_005fform_005f0.setAction("/sap.do");
/* 2482 */                 int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2483 */                 if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                   for (;;) {
/* 2485 */                     out.write("\n<input type=\"hidden\" name=\"method\" value=\"createCCMSMonitors\">\n");
/* 2486 */                     if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 2488 */                     out.write(10);
/*      */                     
/* 2490 */                     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2491 */                     _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2492 */                     _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                     
/* 2494 */                     _jspx_th_logic_005fnotEmpty_005f0.setName("discoverystatus");
/* 2495 */                     int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2496 */                     if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                       for (;;) {
/* 2498 */                         out.write(10);
/* 2499 */                         out.write(9);
/* 2500 */                         out.write("<!--$Id$-->\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/diagnosepage.js\"></SCRIPT>\n");
/*      */                         
/* 2502 */                         NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2503 */                         _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 2504 */                         _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                         
/* 2506 */                         _jspx_th_logic_005fnotEmpty_005f1.setName("discoverystatus");
/* 2507 */                         int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 2508 */                         if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                           for (;;) {
/* 2510 */                             out.write(10);
/*      */                             
/*      */ 
/* 2513 */                             if ((request.getAttribute("type") == null) || ((!request.getAttribute("type").equals("Script Monitor")) && ((request.getAttribute("basetype") == null) || ((request.getAttribute("basetype") != null) && (!request.getAttribute("basetype").equals("Script Monitor")))) && (!request.getAttribute("type").equals("QENGINE")) && (!request.getAttribute("type").equals("Web Service")) && (!request.getAttribute("type").equals("file")) && (!request.getAttribute("type").equals("directory")) && (!request.getAttribute("type").equals("File System Monitor")) && (!request.getAttribute("type").equals("Ping Monitor")) && (!request.getAttribute("type").equals("SAP-CCMS"))))
/*      */                             {
/*      */ 
/* 2516 */                               out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n  <tr>\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2517 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 2518 */                               out.write("</span> </td>\n    <td width=\"7%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2519 */                               out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverport"));
/* 2520 */                               out.write("\n      </span> </td>\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2521 */                               out.print(FormatUtil.getString("am.webclient.oracle.Status"));
/* 2522 */                               out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2523 */                               out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 2524 */                               out.write("\n </span></td>\n  <tr>\n  ");
/*      */                               
/* 2526 */                               int failedNumber = 1;
/*      */                               
/* 2528 */                               out.write(10);
/*      */                               
/* 2530 */                               IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2531 */                               _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2532 */                               _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                               
/* 2534 */                               _jspx_th_logic_005fiterate_005f0.setName("discoverystatus");
/*      */                               
/* 2536 */                               _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                               
/* 2538 */                               _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*      */                               
/* 2540 */                               _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 2541 */                               int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2542 */                               if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2543 */                                 ArrayList row = null;
/* 2544 */                                 Integer i = null;
/* 2545 */                                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2546 */                                   out = _jspx_page_context.pushBody();
/* 2547 */                                   _jspx_th_logic_005fiterate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2548 */                                   _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                 }
/* 2550 */                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2551 */                                 i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                 for (;;) {
/* 2553 */                                   out.write("\n<tr>\n<td height=\"18\" class=\"bodytext\">");
/* 2554 */                                   out.print(row.get(0));
/* 2555 */                                   out.write("</td>\n<td height=\"18\" class=\"bodytext\">");
/* 2556 */                                   out.print(row.get(1));
/* 2557 */                                   out.write("</td>\n\n    <td height=\"18\" class=\"bodytext\">\n      ");
/*      */                                   
/* 2559 */                                   if ((row.get(2).equals("Success")) || (row.get(2).equals("Present")))
/*      */                                   {
/* 2561 */                                     request.setAttribute("isDiscoverySuccess", "true");
/*      */                                     
/* 2563 */                                     out.write("\n      <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 2564 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2565 */                                     out.write("\" align=\"absmiddle\">\n      ");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 2570 */                                     request.setAttribute("isDiscoverySuccess", "false");
/*      */                                     
/* 2572 */                                     out.write("\n      <img alt=\"");
/* 2573 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.notiniatedimagetip.text"));
/* 2574 */                                     out.write("\" src=\"/images/icon_monitor_failure.gif\" align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                                   }
/*      */                                   
/*      */ 
/* 2578 */                                   out.write("\n      <span class=\"bodytextbold\">");
/* 2579 */                                   out.print(FormatUtil.getString(String.valueOf(row.get(2))));
/* 2580 */                                   out.write("</span> </td>\n\n      ");
/*      */                                   
/* 2582 */                                   pageContext.setAttribute("ret_msg", String.valueOf(row.get(3)));
/*      */                                   
/* 2584 */                                   out.write("\n                     ");
/*      */                                   
/* 2586 */                                   IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2587 */                                   _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2588 */                                   _jspx_th_c_005fif_005f4.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                   
/* 2590 */                                   _jspx_th_c_005fif_005f4.setTest("${ret_msg=='Monitoring Initiated Succesfully'}");
/* 2591 */                                   int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2592 */                                   if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                     for (;;) {
/* 2594 */                                       out.write("\n                           <td height=\"18\" class=\"bodytext\">");
/* 2595 */                                       out.print(FormatUtil.getString("am.webclient.discovery.host.successfullyiniated.text"));
/* 2596 */                                       out.write("\n                                 ");
/* 2597 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2598 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2602 */                                   if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2603 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                   }
/*      */                                   
/* 2606 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2607 */                                   out.write("\n                                       ");
/*      */                                   
/* 2609 */                                   IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2610 */                                   _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2611 */                                   _jspx_th_c_005fif_005f5.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                   
/* 2613 */                                   _jspx_th_c_005fif_005f5.setTest("${ret_msg!='Monitoring Initiated Succesfully'}");
/* 2614 */                                   int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2615 */                                   if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                     for (;;) {
/* 2617 */                                       out.write("\n                                             <td height=\"18\" class=\"bodytext\">");
/* 2618 */                                       out.print(row.get(3));
/* 2619 */                                       out.write("\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                       
/* 2621 */                                       if ((request.getAttribute("type") != null) && (!request.getAttribute("type").equals("All")) && (!request.getAttribute("type").equals("TELNET")) && (!request.getAttribute("type").equals("SNMP")) && (!request.getAttribute("type").equals("SERVICE")) && (!request.getAttribute("type").equals("JMX1.2-MX4J-RMI")) && (!request.getAttribute("type").equals("JDK1.5")) && (!request.getAttribute("type").equals("RMI")) && (!request.getAttribute("type").equals("PHP")) && (!request.getAttribute("type").equals("MAIL")) && (!request.getAttribute("type").equals("ORACLEDB")) && (!request.getAttribute("type").equals("MYSQLDB")) && (!request.getAttribute("type").equals("MSSQLDB")) && (!request.getAttribute("type").equals("DB2")) && (!request.getAttribute("type").equals("WTA")) && (!request.getAttribute("type").equals("SAP")))
/*      */                                       {
/* 2623 */                                         if (((String)pageContext.getAttribute("ret_msg")).indexOf(FormatUtil.getString("am.webclient.discovery.host.monitoradded.text")) == -1)
/*      */                                         {
/* 2625 */                                           String fWhr = request.getParameter("hideFieldsForIT360");
/* 2626 */                                           if (fWhr == null)
/*      */                                           {
/* 2628 */                                             fWhr = (String)request.getAttribute("hideFieldsForIT360");
/*      */                                           }
/*      */                                           
/* 2631 */                                           if (((fWhr == null) || (!fWhr.equals("true"))) && 
/* 2632 */                                             (!request.getAttribute("type").equals("SYBASEDB")))
/*      */                                           {
/* 2634 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t &nbsp;&nbsp;&nbsp;&nbsp;<a class=\"bodytext\" href=\"javascript:void(0)\" onClick=\"getAllDetailsOfHost()\">");
/* 2635 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.diagnose.link"));
/* 2636 */                                             out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                           }
/*      */                                         } }
/* 2639 */                                       if ((request.getAttribute("showSupportMessage") != null) && (failedNumber == 1) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */                                       {
/* 2641 */                                         failedNumber++;
/*      */                                         
/*      */ 
/* 2644 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t<br>");
/* 2645 */                                         out.print(FormatUtil.getString("am.webclient.discovery.host.support.link", new String[] { OEMUtil.getOEMString("product.talkback.mailid"), OEMUtil.getOEMString("product.tollfree.number") }));
/* 2646 */                                         out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                       }
/* 2648 */                                       out.write("\n                                                   ");
/* 2649 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2650 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2654 */                                   if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2655 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                   }
/*      */                                   
/* 2658 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2659 */                                   out.write(10);
/* 2660 */                                   out.write(10);
/* 2661 */                                   out.write(10);
/*      */                                   
/*      */ 
/* 2664 */                                   if (row.size() > 4)
/*      */                                   {
/*      */ 
/* 2667 */                                     out.write("<br>\n");
/* 2668 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)row.get(4) }));
/* 2669 */                                     out.write(10);
/*      */                                   }
/*      */                                   
/*      */ 
/* 2673 */                                   out.write("\n</td>\n\n</tr>\n");
/* 2674 */                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2675 */                                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2676 */                                   i = (Integer)_jspx_page_context.findAttribute("i");
/* 2677 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 2680 */                                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2681 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 2684 */                               if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2685 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                               }
/*      */                               
/* 2688 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2689 */                               out.write("\n</table>\n");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/* 2694 */                               ArrayList al1 = (ArrayList)request.getAttribute("discoverystatus");
/*      */                               
/* 2696 */                               out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n<tr>\n");
/* 2697 */                               String mtype = (String)request.getAttribute("type");
/* 2698 */                               out.write(10);
/* 2699 */                               if (mtype.equals("File System Monitor")) {
/* 2700 */                                 out.write("\n  <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2701 */                                 out.print(FormatUtil.getString("File/Directory Name"));
/* 2702 */                                 out.write("</span> </td>\n");
/* 2703 */                               } else if ((((String)request.getAttribute("type")).equals("Ping Monitor")) || (((String)request.getAttribute("type")).equals("SAP-CCMS"))) {
/* 2704 */                                 out.write("\n\t<td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2705 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 2706 */                                 out.write("</span> </td>\n");
/*      */                               } else {
/* 2708 */                                 out.write("\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2709 */                                 out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 2710 */                                 out.write("</span> </td>\n");
/*      */                               }
/* 2712 */                               out.write("\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2713 */                               out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.status"));
/* 2714 */                               out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2715 */                               out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 2716 */                               out.write("</span></td>\n  </tr>\n  <tr>\n   <td height=\"18\" class=\"bodytext\">");
/* 2717 */                               out.print(al1.get(0));
/* 2718 */                               out.write("</td>\n   <td height=\"18\" class=\"bodytext\">\n   ");
/*      */                               
/* 2720 */                               if (al1.get(1).equals("Success"))
/*      */                               {
/* 2722 */                                 request.setAttribute("isDiscoverySuccess", "true");
/*      */                                 
/* 2724 */                                 out.write("\n   <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 2725 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2726 */                                 out.write("\" align=\"absmiddle\">\n    ");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/* 2731 */                                 request.setAttribute("isDiscoverySuccess", "false");
/*      */                                 
/*      */ 
/* 2734 */                                 out.write("\n      <img  src=\"/images/icon_monitor_failure.gif\"  align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                               }
/*      */                               
/*      */ 
/* 2738 */                               out.write("\n<span class=\"bodytextbold\">");
/* 2739 */                               out.print(FormatUtil.getString(String.valueOf(al1.get(1))));
/* 2740 */                               out.write("</span> </td>\n");
/*      */                               
/* 2742 */                               if (al1.get(1).equals("Success"))
/*      */                               {
/* 2744 */                                 boolean isAdminServer = EnterpriseUtil.isAdminServer();
/* 2745 */                                 if (isAdminServer) {
/* 2746 */                                   String masDisplayName = (String)al1.get(3);
/* 2747 */                                   String format = FormatUtil.getString("am.webclient.admin.add.monitor.successfully.configured.text", new String[] { masDisplayName, "", (String)al1.get(2) });
/*      */                                   
/* 2749 */                                   out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\">");
/* 2750 */                                   out.print(format);
/* 2751 */                                   out.write("</td>\n");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 2755 */                                   out.write("\t\t\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"> ");
/* 2756 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2757 */                                   out.write("<br> ");
/* 2758 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)al1.get(2) }));
/* 2759 */                                   out.write("</td>\n");
/*      */                                 }
/*      */                                 
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/* 2766 */                                 out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"><span class=\"bodytext\">");
/* 2767 */                                 out.print(al1.get(2));
/* 2768 */                                 out.write("</span></td>\n");
/*      */                               }
/*      */                               
/*      */ 
/* 2772 */                               out.write("\n  </tr>\n</table>\n\n");
/*      */                             }
/*      */                             
/*      */ 
/* 2776 */                             out.write(10);
/* 2777 */                             int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 2778 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2782 */                         if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 2783 */                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                         }
/*      */                         
/* 2786 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 2787 */                         out.write(10);
/* 2788 */                         out.write(10);
/* 2789 */                         out.write(9);
/*      */                         
/* 2791 */                         String discSucc = (String)request.getAttribute("isDiscoverySuccess");
/* 2792 */                         isDiscoveryComplete = true;
/* 2793 */                         if ((discSucc != null) && (discSucc.equals("true")))
/*      */                         {
/* 2795 */                           isDiscoverySuccess = true;
/*      */                         }
/*      */                         
/* 2798 */                         out.write("\n\t<br>\n");
/* 2799 */                         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2800 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2804 */                     if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2805 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                     }
/*      */                     
/* 2808 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2809 */                     out.write(10);
/*      */                     
/* 2811 */                     if ((!hideFields) || ((!isDiscoveryComplete) && (hideFields)))
/*      */                     {
/*      */ 
/* 2814 */                       out.write("\n<input type=\"hidden\" name=\"addtoha\" value=\"");
/* 2815 */                       out.print(request.getParameter("addtoha"));
/* 2816 */                       out.write(34);
/* 2817 */                       out.write(62);
/* 2818 */                       out.write(10);
/*      */                       
/* 2820 */                       IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2821 */                       _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2822 */                       _jspx_th_c_005fif_005f6.setParent(_jspx_th_html_005fform_005f0);
/*      */                       
/* 2824 */                       _jspx_th_c_005fif_005f6.setTest("${!empty  param.redirectto}");
/* 2825 */                       int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2826 */                       if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                         for (;;) {
/* 2828 */                           out.write("\n\t<input type=\"hidden\" name=\"redirectto\" value=\"");
/* 2829 */                           out.print(request.getParameter("redirectto"));
/* 2830 */                           out.write(34);
/* 2831 */                           out.write(62);
/* 2832 */                           out.write(10);
/* 2833 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2834 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2838 */                       if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2839 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                       }
/*      */                       
/* 2842 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2843 */                       out.write(10);
/* 2844 */                       JspRuntimeLibrary.include(request, response, "/jsp/newresourcetypes.jsp", out, false);
/* 2845 */                       out.write("\n<table width=\"99%\" BORDER=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"lrborder\">\n\t\t\t<tr>\n\t\t\t\t<td class=\"message\" colspan=\"2\"></td>\n\t\t\t</tr>\n\t\t\t<TR>\n\t\t\t\t<TD width=\"26%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 2846 */                       out.print(FormatUtil.getString("am.webclient.common.displayname.text"));
/* 2847 */                       out.write("<span class=\"mandatory\">*</span></label></TD>\n\t\t\t\t<TD height=\"28\">");
/* 2848 */                       if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2850 */                       out.write("</TD>\n\t\t\t</TR>\n\t\t\t<TR>\n\t\t\t\t<TD height=28 width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 2851 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.hostname"));
/* 2852 */                       out.write("<span class=\"mandatory\">*</span></label></TD>\n\t\t\t\t<TD width=\"75%\">\n\t\t\t\t<table width=\"75%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td>");
/* 2853 */                       if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2855 */                       out.write("</td>\n\t\t\t\t\t\t<td align=\"right\" width=\"25%\">\n\t\t\t\t\t\t");
/*      */                       
/* 2857 */                       if (!usertype.equals("F"))
/*      */                       {
/* 2859 */                         out.write("\n\t\t\t\t\t\t\t<div id=\"showndmessage\" style=\"DISPLAY: none\"> <span class=\"footer\"> ");
/* 2860 */                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.textmessage.host"));
/* 2861 */                         out.write("</span> </div>\n\t\t\t\t\t\t");
/*      */                       }
/*      */                       
/*      */ 
/* 2865 */                       out.write("\n\t  \t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n      \t\t\t</td>\n\t\t    </TR>\n\t\t    <TR>\n\t\t\t\t<TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/* 2866 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.connect.withrouterstring"));
/* 2867 */                       out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/* 2868 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.connect.withrouterstring"));
/* 2869 */                       out.write("</a></label></TD>\n\t\t\t\t<TD height=\"28\" colspan=\"2\" >  ");
/* 2870 */                       if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2872 */                       out.write("\n\t\t\t\t</TD>\n\t\t        </TR>\n\t\t        <TR id=\"routerString\" style=\"display:none\">\n\t\t\t\t<TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/* 2873 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.routerstring"));
/* 2874 */                       out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/* 2875 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.routerstring"));
/* 2876 */                       out.write("</a><span class=\"mandatory\">*</span></label></TD>\n\t\t\t\t<TD height=\"28\" colspan=\"2\" > ");
/* 2877 */                       if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2879 */                       out.write("\n\t\t\t\t</TD>\n\t\t        </TR>\n\t\t    <TR>\n\t\t\t\t<TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/* 2880 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.textmessage.logonclient"));
/* 2881 */                       out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/* 2882 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.logonclient"));
/* 2883 */                       out.write("</a><span class=\"mandatory\">*</span></label></TD>\n\t\t\t\t<TD height=\"28\" colspan=\"2\" >");
/* 2884 */                       if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2886 */                       out.write("</TD>\n\t\t\t</TR>\n\t\t    <TR>\n\t\t\t\t<TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/* 2887 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.textmessage.systemnumber"));
/* 2888 */                       out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/* 2889 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.systemnumber"));
/* 2890 */                       out.write("</a><span class=\"mandatory\">*</span></label></TD>\n\t\t\t\t<TD height=\"28\" colspan=\"2\" >");
/* 2891 */                       if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2893 */                       out.write("</TD>\n\t\t    </TR>\n\t\t    <TR>\n\t\t\t\t<TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/* 2894 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.textmessage.language"));
/* 2895 */                       out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/* 2896 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.language"));
/* 2897 */                       out.write("</a></label></TD>\n\t\t\t\t<TD height=\"28\" colspan=\"2\" >");
/* 2898 */                       if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2900 */                       out.write("</TD>\n\t\t    </TR>\n\t\t\t<TR>\n\t\t\t\t<TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 2901 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/* 2902 */                       out.write("<span class=\"mandatory\">*</span></label></TD>\n\t\t\t\t<TD height=\"28\" colspan=\"2\"><input type=\"text\" name=\"username\" class=\"formtext default\" autocomplete=\"off\" /><!--");
/* 2903 */                       if (_jspx_meth_html_005ftext_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2905 */                       out.write("--> </TD>\n\t\t\t</TR>\n\t\t\t<TR>\n\t\t\t\t<TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 2906 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/* 2907 */                       out.write("<span class=\"mandatory\">*</span>&nbsp;</label></TD>\n          \t\t<TD height=\"28\" colspan=\"2\"><input type=\"password\" name=\"password\" class=\"formtext default\" autocomplete=\"off\" /><!--");
/* 2908 */                       if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2910 */                       out.write("-->&nbsp;<span class=\"bodytext\"></span></TD>\n        \t</TR>\n        \t");
/*      */                       
/* 2912 */                       if (EnterpriseUtil.isAdminServer())
/*      */                       {
/* 2914 */                         out.write("\n\t\t\t<TR>\n\t\t\t\t<TD height=\"28\" class=\"bodytext label-align addmonitor-label\">");
/* 2915 */                         out.print(FormatUtil.getString("am.webclient.ccms.ccmsmonitorset"));
/* 2916 */                         out.write("<span class=\"mandatory\">*</span>&nbsp;</TD>\n          \t\t<TD height=\"28\" class=\"bodytext\" colspan=\"2\">");
/* 2917 */                         if (_jspx_meth_html_005ftext_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2919 */                         out.write("</TD>\n        \t</TR>\n        \t");
/*      */                       }
/*      */                       else
/*      */                       {
/* 2923 */                         out.write(" \t\n\t\t\t<TR>\n\t\t\t\t<TD height=\"28\" class=\"bodytext label-align addmonitor-label\">");
/* 2924 */                         out.print(FormatUtil.getString("am.webclient.ccms.ccmsmonitorset"));
/* 2925 */                         out.write("<span class=\"mandatory\">*</span>&nbsp;</TD>\n          \t\t<TD height=\"28\" class=\"bodytext\" colspan=\"2\">");
/* 2926 */                         if (_jspx_meth_html_005ftext_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2928 */                         out.write("&nbsp;&nbsp;");
/* 2929 */                         out.print(FormatUtil.getString("am.webclient.ccms.newmonitor.selectmonitorset"));
/* 2930 */                         out.write("</TD>\n        \t</TR>\n        \t");
/*      */                       }
/*      */                       
/*      */ 
/* 2934 */                       out.write("\t        \t\n\t\t\t<TR>\n\t\t\t\t<TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 2935 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.polling"));
/* 2936 */                       out.write("<span class=\"mandatory\">*</span></label></TD>\n\t\t\t\t<TD height=\"28\" colspan=\"2\"> ");
/* 2937 */                       if (_jspx_meth_html_005ftext_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2939 */                       out.write("<span class=\"footer\">&nbsp;&nbsp;");
/* 2940 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.minutes"));
/* 2941 */                       out.write("</span></TD>\n\t\t\t</TR>\t\t    \n</table>\n\n\n    ");
/*      */                       
/* 2943 */                       if (EnterpriseUtil.isAdminServer())
/*      */                       {
/*      */ 
/* 2946 */                         out.write("\n\t\t\t");
/* 2947 */                         JspRuntimeLibrary.include(request, response, "/jsp/includes/ShowGroupsDetails.jsp" + ("/jsp/includes/ShowGroupsDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("showDetailsOf", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("managedServerGroups", request.getCharacterEncoding()), out, false);
/* 2948 */                         out.write("\t\t\n\t");
/*      */                       }
/*      */                       
/*      */ 
/* 2952 */                       out.write(10);
/* 2953 */                       out.write(10);
/*      */                       
/* 2955 */                       IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2956 */                       _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2957 */                       _jspx_th_c_005fif_005f7.setParent(_jspx_th_html_005fform_005f0);
/*      */                       
/* 2959 */                       _jspx_th_c_005fif_005f7.setTest("${empty param.fromAssociate}");
/* 2960 */                       int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2961 */                       if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                         for (;;) {
/* 2963 */                           out.write(10);
/* 2964 */                           out.write(9);
/* 2965 */                           out.write(9);
/* 2966 */                           JspRuntimeLibrary.include(request, response, "/jsp/includes/ShowGroupsDetails.jsp" + ("/jsp/includes/ShowGroupsDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("showDetailsOf", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("monitorGroups", request.getCharacterEncoding()), out, false);
/* 2967 */                           out.write(10);
/* 2968 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2969 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2973 */                       if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2974 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                       }
/*      */                       
/* 2977 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2978 */                       out.write("\n        \t\n \n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\t<tr>\n\t\t<td width=\"25%\" class=\"tablebottom\">&nbsp;</td>\n\t\t<td align=\"left\" class=\"tablebottom\">\n\t\t<input type=\"button\" class=\"buttons btn_highlt\" value='");
/* 2979 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.addmonitor"));
/* 2980 */                       out.write("' onClick='javascript:fnFormSubmit();'>&nbsp;\n\t\t");
/*      */                       
/* 2982 */                       ResetTag _jspx_th_html_005freset_005f0 = (ResetTag)this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.get(ResetTag.class);
/* 2983 */                       _jspx_th_html_005freset_005f0.setPageContext(_jspx_page_context);
/* 2984 */                       _jspx_th_html_005freset_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                       
/* 2986 */                       _jspx_th_html_005freset_005f0.setStyleClass("buttons btn_reset");
/*      */                       
/* 2988 */                       _jspx_th_html_005freset_005f0.setValue(FormatUtil.getString("am.webclient.global.Reset.text"));
/* 2989 */                       int _jspx_eval_html_005freset_005f0 = _jspx_th_html_005freset_005f0.doStartTag();
/* 2990 */                       if (_jspx_th_html_005freset_005f0.doEndTag() == 5) {
/* 2991 */                         this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.reuse(_jspx_th_html_005freset_005f0); return;
/*      */                       }
/*      */                       
/* 2994 */                       this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.reuse(_jspx_th_html_005freset_005f0);
/* 2995 */                       out.write(" &nbsp;\n\t\t<input type=\"button\" value='");
/* 2996 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.cancel"));
/* 2997 */                       out.write("' onclick=\"history.back();\" class=\"buttons btn_back\" id=\"cancelButtonMod\"/>\n\t\t</td>\n\t</tr>\n</table>\n</td>\n<td width=\"30%\" valign=\"top\">\t\n");
/* 2998 */                       StringBuffer helpcardKey = new StringBuffer();
/* 2999 */                       helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.sap.helpcard", new String[] { OEMUtil.getOEMString("product.name"), OEMUtil.getOEMString("company.troubleshoot.link") }));
/*      */                       
/* 3001 */                       out.write(10);
/* 3002 */                       JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(helpcardKey.toString()), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()), out, false);
/* 3003 */                       out.write(" \n\t\n\n\n\n");
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/*      */ 
/* 3009 */                       out.write("\n<TABLE width=\"99%\" BORDER=\"0\" cellpadding=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\">\n  <tr>\n    <td align=\"center\">\n      <input name=\"closeButton\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 3010 */                       out.print(FormatUtil.getString("Close Window"));
/* 3011 */                       out.write("\" onclick=\"closePopUpWindow();\">\n      ");
/* 3012 */                       if (!isDiscoverySuccess) {
/* 3013 */                         out.write("\n              ");
/*      */                         
/* 3015 */                         ResetTag _jspx_th_html_005freset_005f1 = (ResetTag)this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.get(ResetTag.class);
/* 3016 */                         _jspx_th_html_005freset_005f1.setPageContext(_jspx_page_context);
/* 3017 */                         _jspx_th_html_005freset_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 3019 */                         _jspx_th_html_005freset_005f1.setStyleClass("buttons btn_back");
/*      */                         
/* 3021 */                         _jspx_th_html_005freset_005f1.setValue(FormatUtil.getString("am.webclient.goback.readd.txt"));
/*      */                         
/* 3023 */                         _jspx_th_html_005freset_005f1.setOnclick("javascript:history.back();");
/* 3024 */                         int _jspx_eval_html_005freset_005f1 = _jspx_th_html_005freset_005f1.doStartTag();
/* 3025 */                         if (_jspx_th_html_005freset_005f1.doEndTag() == 5) {
/* 3026 */                           this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f1); return;
/*      */                         }
/*      */                         
/* 3029 */                         this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f1);
/* 3030 */                         out.write("\n      ");
/*      */                       }
/* 3032 */                       out.write("\n      </td>\n      </tr>\n      </table>\n");
/*      */                     }
/*      */                     
/*      */ 
/* 3036 */                     out.write(10);
/* 3037 */                     int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 3038 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3042 */                 if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 3043 */                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                 }
/*      */                 
/* 3046 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 3047 */                 out.write("\n</td>\n</tr>\n</table>\n");
/* 3048 */                 int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 3049 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/* 3052 */               if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 3053 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/* 3056 */             if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3057 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */             }
/*      */             
/* 3060 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 3061 */             out.write(10);
/* 3062 */             if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */               return;
/* 3064 */             out.write(10);
/* 3065 */             int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3066 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 3070 */         if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3071 */           this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */         }
/*      */         else {
/* 3074 */           this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3075 */           out.write(10);
/* 3076 */           out.write(10);
/*      */           
/* 3078 */           if (hideFields)
/*      */           {
/*      */ 
/* 3081 */             out.write("\n\t<script>\n\t\t$(document.body).ready(function(){\n\t\tdocument.getElementById(\"cancelButtonMod\").onclick = null;\n\t\t$(\"#cancelButtonMod\").click(function(){ //No I18N\n\t\t\tclosePopUpWindow();\n\t\t});\n\t\t});\n\t</script>\n");
/*      */           }
/*      */           
/*      */ 
/* 3085 */           out.write("\n\n\n\n");
/*      */         }
/* 3087 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3088 */         out = _jspx_out;
/* 3089 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3090 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3091 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3094 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3100 */     PageContext pageContext = _jspx_page_context;
/* 3101 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3103 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3104 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3105 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 3107 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3109 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3110 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3111 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3112 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3113 */       return true;
/*      */     }
/* 3115 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3116 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3121 */     PageContext pageContext = _jspx_page_context;
/* 3122 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3124 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3125 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3126 */     _jspx_th_c_005fforEach_005f0.setParent(null);
/*      */     
/* 3128 */     _jspx_th_c_005fforEach_005f0.setItems("${dynamicSites}");
/*      */     
/* 3130 */     _jspx_th_c_005fforEach_005f0.setVar("a");
/*      */     
/* 3132 */     _jspx_th_c_005fforEach_005f0.setVarStatus("rowCounter");
/* 3133 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 3135 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3136 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 3138 */           out.write(10);
/* 3139 */           out.write(9);
/* 3140 */           out.write(9);
/* 3141 */           if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3142 */             return true;
/* 3143 */           out.write("\n\t\tif(formCustomerId == customerId)\n\t\t{\n\t\t\tdocument.AMActionForm.haid.options[rowCount++] = new Option(siteName,siteId);\n\t\t}\n\t");
/* 3144 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3145 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3149 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 3150 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3153 */         int tmp206_205 = 0; int[] tmp206_203 = _jspx_push_body_count_c_005fforEach_005f0; int tmp208_207 = tmp206_203[tmp206_205];tmp206_203[tmp206_205] = (tmp208_207 - 1); if (tmp208_207 <= 0) break;
/* 3154 */         out = _jspx_page_context.popBody(); }
/* 3155 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 3157 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 3158 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 3160 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3165 */     PageContext pageContext = _jspx_page_context;
/* 3166 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3168 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3169 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 3170 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3172 */     _jspx_th_c_005fforEach_005f1.setItems("${a}");
/*      */     
/* 3174 */     _jspx_th_c_005fforEach_005f1.setVar("b");
/*      */     
/* 3176 */     _jspx_th_c_005fforEach_005f1.setVarStatus("rowCounter1");
/* 3177 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 3179 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 3180 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 3182 */           out.write("\n\t\t\t");
/* 3183 */           boolean bool; if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3184 */             return true;
/* 3185 */           out.write("\n\t\t\t");
/* 3186 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3187 */             return true;
/* 3188 */           out.write("\n\t\t\t");
/* 3189 */           if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3190 */             return true;
/* 3191 */           out.write(10);
/* 3192 */           out.write(9);
/* 3193 */           out.write(9);
/* 3194 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 3195 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3199 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 3200 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3203 */         int tmp295_294 = 0; int[] tmp295_292 = _jspx_push_body_count_c_005fforEach_005f1; int tmp297_296 = tmp295_292[tmp295_294];tmp295_292[tmp295_294] = (tmp297_296 - 1); if (tmp297_296 <= 0) break;
/* 3204 */         out = _jspx_page_context.popBody(); }
/* 3205 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 3207 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 3208 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 3210 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3215 */     PageContext pageContext = _jspx_page_context;
/* 3216 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3218 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3219 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3220 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3222 */     _jspx_th_c_005fif_005f0.setTest("${rowCounter1.count == 1}");
/* 3223 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3224 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3226 */         out.write("\n\t\t\t\tsiteName = '");
/* 3227 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3228 */           return true;
/* 3229 */         out.write("';\n\t\t\t");
/* 3230 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3231 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3235 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3236 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3237 */       return true;
/*      */     }
/* 3239 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3240 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3245 */     PageContext pageContext = _jspx_page_context;
/* 3246 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3248 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3249 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3250 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3252 */     _jspx_th_c_005fout_005f1.setValue("${b}");
/* 3253 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3254 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3255 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3256 */       return true;
/*      */     }
/* 3258 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3259 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3264 */     PageContext pageContext = _jspx_page_context;
/* 3265 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3267 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3268 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3269 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3271 */     _jspx_th_c_005fif_005f1.setTest("${rowCounter1.count == 2}");
/* 3272 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3273 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3275 */         out.write("\n\t\t\t\tsiteId = '");
/* 3276 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3277 */           return true;
/* 3278 */         out.write("';\n\t\t\t");
/* 3279 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3280 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3284 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3285 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3286 */       return true;
/*      */     }
/* 3288 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3289 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3294 */     PageContext pageContext = _jspx_page_context;
/* 3295 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3297 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3298 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3299 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3301 */     _jspx_th_c_005fout_005f2.setValue("${b}");
/* 3302 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3303 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3304 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3305 */       return true;
/*      */     }
/* 3307 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3308 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3313 */     PageContext pageContext = _jspx_page_context;
/* 3314 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3316 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3317 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3318 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3320 */     _jspx_th_c_005fif_005f2.setTest("${rowCounter1.count == 3}");
/* 3321 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3322 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 3324 */         out.write("\n\t\t\t\tcustomerId = '");
/* 3325 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3326 */           return true;
/* 3327 */         out.write("';\n\t\t\t");
/* 3328 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3329 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3333 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3334 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3335 */       return true;
/*      */     }
/* 3337 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3338 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3343 */     PageContext pageContext = _jspx_page_context;
/* 3344 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3346 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3347 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3348 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3350 */     _jspx_th_c_005fout_005f3.setValue("${b}");
/* 3351 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3352 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3353 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3354 */       return true;
/*      */     }
/* 3356 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3357 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3362 */     PageContext pageContext = _jspx_page_context;
/* 3363 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3365 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3366 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3367 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 3369 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 3370 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3371 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 3373 */         out.write("\n\t\talertUser();\n\t\treturn false;\n\t");
/* 3374 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3375 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3379 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3380 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3381 */       return true;
/*      */     }
/* 3383 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3384 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3389 */     PageContext pageContext = _jspx_page_context;
/* 3390 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3392 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3393 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 3394 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3396 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 3398 */     _jspx_th_tiles_005fput_005f0.setValue("SAP CCMS");
/* 3399 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 3400 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 3401 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3402 */       return true;
/*      */     }
/* 3404 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3405 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3410 */     PageContext pageContext = _jspx_page_context;
/* 3411 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3413 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3414 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3415 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3417 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3419 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=1");
/* 3420 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3421 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3422 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3423 */       return true;
/*      */     }
/* 3425 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3426 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3431 */     PageContext pageContext = _jspx_page_context;
/* 3432 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3434 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 3435 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 3436 */     _jspx_th_am_005fhiddenparam_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3438 */     _jspx_th_am_005fhiddenparam_005f0.setName("fromAssociate");
/* 3439 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 3440 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 3441 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 3442 */       return true;
/*      */     }
/* 3444 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 3445 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3450 */     PageContext pageContext = _jspx_page_context;
/* 3451 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3453 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 3454 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 3455 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3457 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 3459 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default");
/* 3460 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 3461 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 3462 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3463 */       return true;
/*      */     }
/* 3465 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3466 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3471 */     PageContext pageContext = _jspx_page_context;
/* 3472 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3474 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonblur_005fnobody.get(TextTag.class);
/* 3475 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 3476 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3478 */     _jspx_th_html_005ftext_005f1.setProperty("host");
/*      */     
/* 3480 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext default");
/*      */     
/* 3482 */     _jspx_th_html_005ftext_005f1.setOnblur("javascript:showServiceDetail()");
/* 3483 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 3484 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 3485 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonblur_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3486 */       return true;
/*      */     }
/* 3488 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonblur_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3489 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3494 */     PageContext pageContext = _jspx_page_context;
/* 3495 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3497 */     CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 3498 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 3499 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3501 */     _jspx_th_html_005fcheckbox_005f0.setProperty("usedRouterString");
/*      */     
/* 3503 */     _jspx_th_html_005fcheckbox_005f0.setValue("true");
/*      */     
/* 3505 */     _jspx_th_html_005fcheckbox_005f0.setOnclick("javascript:showSAPRouterString(this);");
/* 3506 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 3507 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 3508 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 3509 */       return true;
/*      */     }
/* 3511 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 3512 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3517 */     PageContext pageContext = _jspx_page_context;
/* 3518 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3520 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 3521 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 3522 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3524 */     _jspx_th_html_005ftext_005f2.setProperty("routerString");
/*      */     
/* 3526 */     _jspx_th_html_005ftext_005f2.setValue("");
/*      */     
/* 3528 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext default");
/* 3529 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 3530 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 3531 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 3532 */       return true;
/*      */     }
/* 3534 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 3535 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3540 */     PageContext pageContext = _jspx_page_context;
/* 3541 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3543 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 3544 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 3545 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3547 */     _jspx_th_html_005ftext_005f3.setProperty("logonClient");
/*      */     
/* 3549 */     _jspx_th_html_005ftext_005f3.setValue("000");
/*      */     
/* 3551 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext default");
/* 3552 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 3553 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 3554 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 3555 */       return true;
/*      */     }
/* 3557 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 3558 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3563 */     PageContext pageContext = _jspx_page_context;
/* 3564 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3566 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 3567 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 3568 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3570 */     _jspx_th_html_005ftext_005f4.setProperty("systemNumber");
/*      */     
/* 3572 */     _jspx_th_html_005ftext_005f4.setValue("00");
/*      */     
/* 3574 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext default");
/* 3575 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 3576 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 3577 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 3578 */       return true;
/*      */     }
/* 3580 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 3581 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3586 */     PageContext pageContext = _jspx_page_context;
/* 3587 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3589 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 3590 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 3591 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3593 */     _jspx_th_html_005ftext_005f5.setProperty("language");
/*      */     
/* 3595 */     _jspx_th_html_005ftext_005f5.setValue("EN");
/*      */     
/* 3597 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext default");
/* 3598 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 3599 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 3600 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 3601 */       return true;
/*      */     }
/* 3603 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 3604 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3609 */     PageContext pageContext = _jspx_page_context;
/* 3610 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3612 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 3613 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 3614 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3616 */     _jspx_th_html_005ftext_005f6.setProperty("username");
/*      */     
/* 3618 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext");
/*      */     
/* 3620 */     _jspx_th_html_005ftext_005f6.setSize("15");
/* 3621 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 3622 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 3623 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 3624 */       return true;
/*      */     }
/* 3626 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 3627 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3632 */     PageContext pageContext = _jspx_page_context;
/* 3633 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3635 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 3636 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 3637 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3639 */     _jspx_th_html_005fpassword_005f0.setProperty("password");
/*      */     
/* 3641 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext");
/*      */     
/* 3643 */     _jspx_th_html_005fpassword_005f0.setSize("15");
/* 3644 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 3645 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 3646 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 3647 */       return true;
/*      */     }
/* 3649 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 3650 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3655 */     PageContext pageContext = _jspx_page_context;
/* 3656 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3658 */     TextTag _jspx_th_html_005ftext_005f7 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 3659 */     _jspx_th_html_005ftext_005f7.setPageContext(_jspx_page_context);
/* 3660 */     _jspx_th_html_005ftext_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3662 */     _jspx_th_html_005ftext_005f7.setProperty("version");
/*      */     
/* 3664 */     _jspx_th_html_005ftext_005f7.setStyleClass("formtext default");
/*      */     
/* 3666 */     _jspx_th_html_005ftext_005f7.setSize("25");
/*      */     
/* 3668 */     _jspx_th_html_005ftext_005f7.setValue("");
/* 3669 */     int _jspx_eval_html_005ftext_005f7 = _jspx_th_html_005ftext_005f7.doStartTag();
/* 3670 */     if (_jspx_th_html_005ftext_005f7.doEndTag() == 5) {
/* 3671 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 3672 */       return true;
/*      */     }
/* 3674 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 3675 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3680 */     PageContext pageContext = _jspx_page_context;
/* 3681 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3683 */     TextTag _jspx_th_html_005ftext_005f8 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fstyle_005fsize_005freadonly_005fproperty_005fnobody.get(TextTag.class);
/* 3684 */     _jspx_th_html_005ftext_005f8.setPageContext(_jspx_page_context);
/* 3685 */     _jspx_th_html_005ftext_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3687 */     _jspx_th_html_005ftext_005f8.setProperty("version");
/*      */     
/* 3689 */     _jspx_th_html_005ftext_005f8.setStyle("background-color: #CDC9C9;");
/*      */     
/* 3691 */     _jspx_th_html_005ftext_005f8.setStyleClass("formtext default");
/*      */     
/* 3693 */     _jspx_th_html_005ftext_005f8.setSize("25");
/*      */     
/* 3695 */     _jspx_th_html_005ftext_005f8.setReadonly(true);
/*      */     
/* 3697 */     _jspx_th_html_005ftext_005f8.setValue("");
/* 3698 */     int _jspx_eval_html_005ftext_005f8 = _jspx_th_html_005ftext_005f8.doStartTag();
/* 3699 */     if (_jspx_th_html_005ftext_005f8.doEndTag() == 5) {
/* 3700 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fstyle_005fsize_005freadonly_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 3701 */       return true;
/*      */     }
/* 3703 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fstyle_005fsize_005freadonly_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 3704 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3709 */     PageContext pageContext = _jspx_page_context;
/* 3710 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3712 */     TextTag _jspx_th_html_005ftext_005f9 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 3713 */     _jspx_th_html_005ftext_005f9.setPageContext(_jspx_page_context);
/* 3714 */     _jspx_th_html_005ftext_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3716 */     _jspx_th_html_005ftext_005f9.setProperty("pollInterval");
/*      */     
/* 3718 */     _jspx_th_html_005ftext_005f9.setStyleClass("formtext small");
/* 3719 */     int _jspx_eval_html_005ftext_005f9 = _jspx_th_html_005ftext_005f9.doStartTag();
/* 3720 */     if (_jspx_th_html_005ftext_005f9.doEndTag() == 5) {
/* 3721 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/* 3722 */       return true;
/*      */     }
/* 3724 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/* 3725 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3730 */     PageContext pageContext = _jspx_page_context;
/* 3731 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3733 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3734 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 3735 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3737 */     _jspx_th_tiles_005fput_005f3.setName("footer");
/*      */     
/* 3739 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/footer.jsp");
/* 3740 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 3741 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3742 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3743 */       return true;
/*      */     }
/* 3745 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3746 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\sap\newsapccms_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */