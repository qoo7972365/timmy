/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.html.TextareaTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ImportTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParamTag;
/*      */ 
/*      */ public final class CreateProcessTemplate_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   46 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   49 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   50 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   51 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   58 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   63 */     ArrayList list = null;
/*   64 */     StringBuffer sbf = new StringBuffer();
/*   65 */     com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
/*   66 */     if (distinct)
/*      */     {
/*   68 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   72 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   75 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   77 */       ArrayList row = (ArrayList)list.get(i);
/*   78 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   79 */       if (distinct) {
/*   80 */         sbf.append(row.get(0));
/*      */       } else
/*   82 */         sbf.append(row.get(1));
/*   83 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   86 */     return sbf.toString(); }
/*      */   
/*   88 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   91 */     if (severity == null)
/*      */     {
/*   93 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*   95 */     if (severity.equals("5"))
/*      */     {
/*   97 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*   99 */     if (severity.equals("1"))
/*      */     {
/*  101 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  106 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  113 */     if (severity == null)
/*      */     {
/*  115 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  117 */     if (severity.equals("1"))
/*      */     {
/*  119 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  121 */     if (severity.equals("4"))
/*      */     {
/*  123 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  125 */     if (severity.equals("5"))
/*      */     {
/*  127 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  132 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  138 */     if (severity == null)
/*      */     {
/*  140 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  142 */     if (severity.equals("5"))
/*      */     {
/*  144 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  146 */     if (severity.equals("1"))
/*      */     {
/*  148 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  152 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  158 */     if (severity == null)
/*      */     {
/*  160 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  162 */     if (severity.equals("1"))
/*      */     {
/*  164 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  166 */     if (severity.equals("4"))
/*      */     {
/*  168 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  170 */     if (severity.equals("5"))
/*      */     {
/*  172 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  176 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  182 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  188 */     if (severity == 5)
/*      */     {
/*  190 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  192 */     if (severity == 1)
/*      */     {
/*  194 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  199 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  205 */     if (severity == null)
/*      */     {
/*  207 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  209 */     if (severity.equals("5"))
/*      */     {
/*  211 */       if (isAvailability) {
/*  212 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  215 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  218 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  220 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  222 */     if (severity.equals("1"))
/*      */     {
/*  224 */       if (isAvailability) {
/*  225 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  228 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  235 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  242 */     if (severity == null)
/*      */     {
/*  244 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  246 */     if (severity.equals("5"))
/*      */     {
/*  248 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  250 */     if (severity.equals("4"))
/*      */     {
/*  252 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  254 */     if (severity.equals("1"))
/*      */     {
/*  256 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  261 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  267 */     if (severity == null)
/*      */     {
/*  269 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  271 */     if (severity.equals("5"))
/*      */     {
/*  273 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  275 */     if (severity.equals("4"))
/*      */     {
/*  277 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  279 */     if (severity.equals("1"))
/*      */     {
/*  281 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  286 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  293 */     if (severity == null)
/*      */     {
/*  295 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  297 */     if (severity.equals("5"))
/*      */     {
/*  299 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  301 */     if (severity.equals("4"))
/*      */     {
/*  303 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  305 */     if (severity.equals("1"))
/*      */     {
/*  307 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  312 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  320 */     StringBuffer out = new StringBuffer();
/*  321 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  322 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  323 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  324 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  325 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  326 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  327 */     out.append("</tr>");
/*  328 */     out.append("</form></table>");
/*  329 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  336 */     if (val == null)
/*      */     {
/*  338 */       return "-";
/*      */     }
/*      */     
/*  341 */     String ret = FormatUtil.formatNumber(val);
/*  342 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  343 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  346 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  350 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  358 */     StringBuffer out = new StringBuffer();
/*  359 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  360 */     out.append("<tr>");
/*  361 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  363 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  365 */     out.append("</tr>");
/*  366 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  370 */       if (j % 2 == 0)
/*      */       {
/*  372 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  376 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  379 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  381 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  384 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  388 */       out.append("</tr>");
/*      */     }
/*  390 */     out.append("</table>");
/*  391 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  392 */     out.append("<tr>");
/*  393 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  394 */     out.append("</tr>");
/*  395 */     out.append("</table>");
/*  396 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, java.util.Vector tableColumns)
/*      */   {
/*  402 */     StringBuffer out = new StringBuffer();
/*  403 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  404 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  405 */     out.append("<tr>");
/*  406 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  407 */     out.append("<tr>");
/*  408 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  409 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  410 */     out.append("</tr>");
/*  411 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  414 */       out.append("<tr>");
/*  415 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  416 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  417 */       out.append("</tr>");
/*      */     }
/*      */     
/*  420 */     out.append("</table>");
/*  421 */     out.append("</table>");
/*  422 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  427 */     if (severity.equals("0"))
/*      */     {
/*  429 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  433 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  440 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  453 */     StringBuffer out = new StringBuffer();
/*  454 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  455 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  457 */       out.append("<tr>");
/*  458 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  459 */       out.append("</tr>");
/*      */       
/*      */ 
/*  462 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  464 */         String borderclass = "";
/*      */         
/*      */ 
/*  467 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  469 */         out.append("<tr>");
/*      */         
/*  471 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  472 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  473 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  479 */     out.append("</table><br>");
/*  480 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  481 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  483 */       List sLinks = secondLevelOfLinks[0];
/*  484 */       List sText = secondLevelOfLinks[1];
/*  485 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  488 */         out.append("<tr>");
/*  489 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  490 */         out.append("</tr>");
/*  491 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  493 */           String borderclass = "";
/*      */           
/*      */ 
/*  496 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  498 */           out.append("<tr>");
/*      */           
/*  500 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  501 */           if (sLinks.get(i).toString().length() == 0) {
/*  502 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  505 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  507 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  511 */     out.append("</table>");
/*  512 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  519 */     StringBuffer out = new StringBuffer();
/*  520 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  521 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  523 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  525 */         out.append("<tr>");
/*  526 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  527 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  531 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  533 */           String borderclass = "";
/*      */           
/*      */ 
/*  536 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  538 */           out.append("<tr>");
/*      */           
/*  540 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  541 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  542 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  545 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  548 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  553 */     out.append("</table><br>");
/*  554 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  555 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  557 */       List sLinks = secondLevelOfLinks[0];
/*  558 */       List sText = secondLevelOfLinks[1];
/*  559 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  562 */         out.append("<tr>");
/*  563 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  564 */         out.append("</tr>");
/*  565 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  567 */           String borderclass = "";
/*      */           
/*      */ 
/*  570 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  572 */           out.append("<tr>");
/*      */           
/*  574 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  575 */           if (sLinks.get(i).toString().length() == 0) {
/*  576 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  579 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  581 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  585 */     out.append("</table>");
/*  586 */     return out.toString();
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
/*  599 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  602 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  605 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  608 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  611 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  614 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  617 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  620 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  628 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  633 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  638 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  643 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  648 */     if (val != null)
/*      */     {
/*  650 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  654 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  659 */     if (val == null) {
/*  660 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  664 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  669 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  675 */     if (val != null)
/*      */     {
/*  677 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  681 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  687 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  692 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  696 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  701 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  706 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  711 */     String hostaddress = "";
/*  712 */     String ip = request.getHeader("x-forwarded-for");
/*  713 */     if (ip == null)
/*  714 */       ip = request.getRemoteAddr();
/*  715 */     java.net.InetAddress add = null;
/*  716 */     if (ip.equals("127.0.0.1")) {
/*  717 */       add = java.net.InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  721 */       add = java.net.InetAddress.getByName(ip);
/*      */     }
/*  723 */     hostaddress = add.getHostName();
/*  724 */     if (hostaddress.indexOf('.') != -1) {
/*  725 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  726 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  730 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  735 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  741 */     if (severity == null)
/*      */     {
/*  743 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  745 */     if (severity.equals("5"))
/*      */     {
/*  747 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  749 */     if (severity.equals("1"))
/*      */     {
/*  751 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  756 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  761 */     ResultSet set = null;
/*  762 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  763 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  765 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  766 */       if (set.next()) { String str1;
/*  767 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  768 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  771 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  776 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  779 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  781 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  785 */     StringBuffer rca = new StringBuffer();
/*  786 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  787 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  790 */     int rcalength = key.length();
/*  791 */     String split = "6. ";
/*  792 */     int splitPresent = key.indexOf(split);
/*  793 */     String div1 = "";String div2 = "";
/*  794 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  796 */       if (rcalength > 180) {
/*  797 */         rca.append("<span class=\"rca-critical-text\">");
/*  798 */         getRCATrimmedText(key, rca);
/*  799 */         rca.append("</span>");
/*      */       } else {
/*  801 */         rca.append("<span class=\"rca-critical-text\">");
/*  802 */         rca.append(key);
/*  803 */         rca.append("</span>");
/*      */       }
/*  805 */       return rca.toString();
/*      */     }
/*  807 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  808 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  809 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  810 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  811 */     getRCATrimmedText(div1, rca);
/*  812 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  815 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  816 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  817 */     getRCATrimmedText(div2, rca);
/*  818 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  820 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  825 */     String[] st = msg.split("<br>");
/*  826 */     for (int i = 0; i < st.length; i++) {
/*  827 */       String s = st[i];
/*  828 */       if (s.length() > 180) {
/*  829 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  831 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  835 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  836 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  838 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  842 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  843 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  844 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  847 */       if (key == null) {
/*  848 */         return ret;
/*      */       }
/*      */       
/*  851 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  852 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  855 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  856 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  857 */       set = AMConnectionPool.executeQueryStmt(query);
/*  858 */       if (set.next())
/*      */       {
/*  860 */         String helpLink = set.getString("LINK");
/*  861 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  864 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  870 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  889 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  880 */         if (set != null) {
/*  881 */           AMConnectionPool.closeStatement(set);
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
/*  895 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  896 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  898 */       String entityStr = (String)keys.nextElement();
/*  899 */       String mmessage = temp.getProperty(entityStr);
/*  900 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  901 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  903 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  909 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  910 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  912 */       String entityStr = (String)keys.nextElement();
/*  913 */       String mmessage = temp.getProperty(entityStr);
/*  914 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  915 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  917 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  922 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  932 */     String des = new String();
/*  933 */     while (str.indexOf(find) != -1) {
/*  934 */       des = des + str.substring(0, str.indexOf(find));
/*  935 */       des = des + replace;
/*  936 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  938 */     des = des + str;
/*  939 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  946 */       if (alert == null)
/*      */       {
/*  948 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  950 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  952 */         return "&nbsp;";
/*      */       }
/*      */       
/*  955 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  957 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  960 */       int rcalength = test.length();
/*  961 */       if (rcalength < 300)
/*      */       {
/*  963 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  967 */       StringBuffer out = new StringBuffer();
/*  968 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  969 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  970 */       out.append("</div>");
/*  971 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  972 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  973 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  978 */       ex.printStackTrace();
/*      */     }
/*  980 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  986 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  991 */     ArrayList attribIDs = new ArrayList();
/*  992 */     ArrayList resIDs = new ArrayList();
/*  993 */     ArrayList entitylist = new ArrayList();
/*      */     
/*  995 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/*  997 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/*  999 */       String resourceid = "";
/* 1000 */       String resourceType = "";
/* 1001 */       if (type == 2) {
/* 1002 */         resourceid = (String)row.get(0);
/* 1003 */         resourceType = (String)row.get(3);
/*      */       }
/* 1005 */       else if (type == 3) {
/* 1006 */         resourceid = (String)row.get(0);
/* 1007 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1010 */         resourceid = (String)row.get(6);
/* 1011 */         resourceType = (String)row.get(7);
/*      */       }
/* 1013 */       resIDs.add(resourceid);
/* 1014 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1015 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1017 */       String healthentity = null;
/* 1018 */       String availentity = null;
/* 1019 */       if (healthid != null) {
/* 1020 */         healthentity = resourceid + "_" + healthid;
/* 1021 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1024 */       if (availid != null) {
/* 1025 */         availentity = resourceid + "_" + availid;
/* 1026 */         entitylist.add(availentity);
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
/* 1040 */     Properties alert = getStatus(entitylist);
/* 1041 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1046 */     int size = monitorList.size();
/*      */     
/* 1048 */     String[] severity = new String[size];
/*      */     
/* 1050 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1052 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1053 */       String resourceName1 = (String)row1.get(7);
/* 1054 */       String resourceid1 = (String)row1.get(6);
/* 1055 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1056 */       if (severity[j] == null)
/*      */       {
/* 1058 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1062 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1064 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1066 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1069 */         if (sev > 0) {
/* 1070 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1071 */           monitorList.set(k, monitorList.get(j));
/* 1072 */           monitorList.set(j, t);
/* 1073 */           String temp = severity[k];
/* 1074 */           severity[k] = severity[j];
/* 1075 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1081 */     int z = 0;
/* 1082 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1085 */       int i = 0;
/* 1086 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1089 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1093 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1097 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1099 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1102 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1106 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1109 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1110 */       String resourceName1 = (String)row1.get(7);
/* 1111 */       String resourceid1 = (String)row1.get(6);
/* 1112 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1113 */       if (hseverity[j] == null)
/*      */       {
/* 1115 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1120 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1122 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1125 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1128 */         if (hsev > 0) {
/* 1129 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1130 */           monitorList.set(k, monitorList.get(j));
/* 1131 */           monitorList.set(j, t);
/* 1132 */           String temp1 = hseverity[k];
/* 1133 */           hseverity[k] = hseverity[j];
/* 1134 */           hseverity[j] = temp1;
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
/* 1146 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1147 */     boolean forInventory = false;
/* 1148 */     String trdisplay = "none";
/* 1149 */     String plusstyle = "inline";
/* 1150 */     String minusstyle = "none";
/* 1151 */     String haidTopLevel = "";
/* 1152 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1154 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1156 */         haidTopLevel = request.getParameter("haid");
/* 1157 */         forInventory = true;
/* 1158 */         trdisplay = "table-row;";
/* 1159 */         plusstyle = "none";
/* 1160 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1167 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1170 */     ArrayList listtoreturn = new ArrayList();
/* 1171 */     StringBuffer toreturn = new StringBuffer();
/* 1172 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1173 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1174 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1176 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1178 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1179 */       String childresid = (String)singlerow.get(0);
/* 1180 */       String childresname = (String)singlerow.get(1);
/* 1181 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1182 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1183 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1184 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1185 */       String unmanagestatus = (String)singlerow.get(5);
/* 1186 */       String actionstatus = (String)singlerow.get(6);
/* 1187 */       String linkclass = "monitorgp-links";
/* 1188 */       String titleforres = childresname;
/* 1189 */       String titilechildresname = childresname;
/* 1190 */       String childimg = "/images/trcont.png";
/* 1191 */       String flag = "enable";
/* 1192 */       String dcstarted = (String)singlerow.get(8);
/* 1193 */       String configMonitor = "";
/* 1194 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1195 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1197 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1199 */       if (singlerow.get(7) != null)
/*      */       {
/* 1201 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1203 */       String haiGroupType = "0";
/* 1204 */       if ("HAI".equals(childtype))
/*      */       {
/* 1206 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1208 */       childimg = "/images/trend.png";
/* 1209 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1210 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1211 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1213 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1215 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1217 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1218 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1221 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1223 */         linkclass = "disabledtext";
/* 1224 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1226 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1227 */       String availmouseover = "";
/* 1228 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1230 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1232 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1233 */       String healthmouseover = "";
/* 1234 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1236 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1239 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1240 */       int spacing = 0;
/* 1241 */       if (level >= 1)
/*      */       {
/* 1243 */         spacing = 40 * level;
/*      */       }
/* 1245 */       if (childtype.equals("HAI"))
/*      */       {
/* 1247 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1248 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1249 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1251 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1252 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1253 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1254 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1255 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1256 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1257 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1258 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1259 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1260 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1261 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1263 */         if (!forInventory)
/*      */         {
/* 1265 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1268 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1270 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1272 */           actions = editlink + actions;
/*      */         }
/* 1274 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1276 */           actions = actions + associatelink;
/*      */         }
/* 1278 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1279 */         String arrowimg = "";
/* 1280 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1282 */           actions = "";
/* 1283 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1284 */           checkbox = "";
/* 1285 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1287 */         if (isIt360)
/*      */         {
/* 1289 */           actionimg = "";
/* 1290 */           actions = "";
/* 1291 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1292 */           checkbox = "";
/*      */         }
/*      */         
/* 1295 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1297 */           actions = "";
/*      */         }
/* 1299 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1301 */           checkbox = "";
/*      */         }
/*      */         
/* 1304 */         String resourcelink = "";
/*      */         
/* 1306 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1308 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1312 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1315 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1316 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1317 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1318 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1319 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1320 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1321 */         if (!isIt360)
/*      */         {
/* 1323 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1327 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1330 */         toreturn.append("</tr>");
/* 1331 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1333 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1334 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1338 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1339 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1342 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1346 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1348 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1349 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1350 */             toreturn.append(assocMessage);
/* 1351 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1352 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1353 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1354 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1360 */         String resourcelink = null;
/* 1361 */         boolean hideEditLink = false;
/* 1362 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1364 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1365 */           hideEditLink = true;
/* 1366 */           if (isIt360)
/*      */           {
/* 1368 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1372 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1374 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1376 */           hideEditLink = true;
/* 1377 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/* 1378 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1383 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1386 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1387 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1388 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1389 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1390 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1391 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1392 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1393 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1394 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1395 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1396 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1397 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1398 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1400 */         if (hideEditLink)
/*      */         {
/* 1402 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1404 */         if (!forInventory)
/*      */         {
/* 1406 */           removefromgroup = "";
/*      */         }
/* 1408 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1409 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1410 */           actions = actions + configcustomfields;
/*      */         }
/* 1412 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1414 */           actions = editlink + actions;
/*      */         }
/* 1416 */         String managedLink = "";
/* 1417 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1419 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1420 */           actions = "";
/* 1421 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1422 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1425 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1427 */           checkbox = "";
/*      */         }
/*      */         
/* 1430 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1432 */           actions = "";
/*      */         }
/* 1434 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1435 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1436 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1437 */         if (isIt360)
/*      */         {
/* 1439 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1443 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1445 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1446 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1447 */         if (!isIt360)
/*      */         {
/* 1449 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1453 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1455 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1458 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1465 */       StringBuilder toreturn = new StringBuilder();
/* 1466 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1467 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1468 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1469 */       String title = "";
/* 1470 */       message = EnterpriseUtil.decodeString(message);
/* 1471 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1472 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1473 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1475 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1477 */       else if ("5".equals(severity))
/*      */       {
/* 1479 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1483 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1485 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1486 */       toreturn.append(v);
/*      */       
/* 1488 */       toreturn.append(link);
/* 1489 */       if (severity == null)
/*      */       {
/* 1491 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1493 */       else if (severity.equals("5"))
/*      */       {
/* 1495 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1497 */       else if (severity.equals("4"))
/*      */       {
/* 1499 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1501 */       else if (severity.equals("1"))
/*      */       {
/* 1503 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1508 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1510 */       toreturn.append("</a>");
/* 1511 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1515 */       ex.printStackTrace();
/*      */     }
/* 1517 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1524 */       StringBuilder toreturn = new StringBuilder();
/* 1525 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1526 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1527 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1528 */       if (message == null)
/*      */       {
/* 1530 */         message = "";
/*      */       }
/*      */       
/* 1533 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1534 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1536 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1537 */       toreturn.append(v);
/*      */       
/* 1539 */       toreturn.append(link);
/*      */       
/* 1541 */       if (severity == null)
/*      */       {
/* 1543 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1545 */       else if (severity.equals("5"))
/*      */       {
/* 1547 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1549 */       else if (severity.equals("1"))
/*      */       {
/* 1551 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1556 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1558 */       toreturn.append("</a>");
/* 1559 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1565 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1568 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1569 */     if (invokeActions != null) {
/* 1570 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1571 */       while (iterator.hasNext()) {
/* 1572 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1573 */         if (actionmap.containsKey(actionid)) {
/* 1574 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1579 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1583 */     String actionLink = "";
/* 1584 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1585 */     String query = "";
/* 1586 */     ResultSet rs = null;
/* 1587 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1588 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1589 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1590 */       actionLink = "method=" + methodName;
/*      */     }
/* 1592 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1593 */       actionLink = methodName;
/*      */     }
/* 1595 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1596 */     Iterator itr = methodarglist.iterator();
/* 1597 */     boolean isfirstparam = true;
/* 1598 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1599 */     while (itr.hasNext()) {
/* 1600 */       HashMap argmap = (HashMap)itr.next();
/* 1601 */       String argtype = (String)argmap.get("TYPE");
/* 1602 */       String argname = (String)argmap.get("IDENTITY");
/* 1603 */       String paramname = (String)argmap.get("PARAMETER");
/* 1604 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1605 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1606 */         isfirstparam = false;
/* 1607 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1609 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1613 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1617 */         actionLink = actionLink + "&";
/*      */       }
/* 1619 */       String paramValue = null;
/* 1620 */       String tempargname = argname;
/* 1621 */       if (commonValues.getProperty(tempargname) != null) {
/* 1622 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1625 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1626 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1627 */           if (dbType.equals("mysql")) {
/* 1628 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1631 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1633 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1635 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1636 */             if (rs.next()) {
/* 1637 */               paramValue = rs.getString("VALUE");
/* 1638 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1642 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1646 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1649 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1654 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1655 */           paramValue = rowId;
/*      */         }
/* 1657 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1658 */           paramValue = managedObjectName;
/*      */         }
/* 1660 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1661 */           paramValue = resID;
/*      */         }
/* 1663 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1664 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1667 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1669 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1670 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1671 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1673 */     return actionLink;
/*      */   }
/*      */   
/* 1676 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1677 */     String dependentAttribute = null;
/* 1678 */     String align = "left";
/*      */     
/* 1680 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1681 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1682 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1683 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1684 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1685 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1686 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1687 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1688 */       align = "center";
/*      */     }
/*      */     
/* 1691 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1692 */     String actualdata = "";
/*      */     
/* 1694 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1695 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1696 */         actualdata = availValue;
/*      */       }
/* 1698 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1699 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1703 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1704 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1707 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1713 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1714 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1715 */       toreturn.append("<table>");
/* 1716 */       toreturn.append("<tr>");
/* 1717 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1718 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1719 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1720 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1721 */         String toolTip = "";
/* 1722 */         String hideClass = "";
/* 1723 */         String textStyle = "";
/* 1724 */         boolean isreferenced = true;
/* 1725 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1726 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1727 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1728 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1730 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1731 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1732 */           while (valueList.hasMoreTokens()) {
/* 1733 */             String dependentVal = valueList.nextToken();
/* 1734 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1735 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1736 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1738 */               toolTip = "";
/* 1739 */               hideClass = "";
/* 1740 */               isreferenced = false;
/* 1741 */               textStyle = "disabledtext";
/* 1742 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1746 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1747 */           toolTip = "";
/* 1748 */           hideClass = "";
/* 1749 */           isreferenced = false;
/* 1750 */           textStyle = "disabledtext";
/* 1751 */           if (dependentImageMap != null) {
/* 1752 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1753 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1756 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1760 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1761 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1762 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1763 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1764 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1765 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1767 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1768 */           if (isreferenced) {
/* 1769 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1773 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1774 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1775 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1776 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1777 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1778 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1780 */           toreturn.append("</span>");
/* 1781 */           toreturn.append("</a>");
/* 1782 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1785 */       toreturn.append("</tr>");
/* 1786 */       toreturn.append("</table>");
/* 1787 */       toreturn.append("</td>");
/*      */     } else {
/* 1789 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1792 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1796 */     String colTime = null;
/* 1797 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1798 */     if ((rows != null) && (rows.size() > 0)) {
/* 1799 */       Iterator<String> itr = rows.iterator();
/* 1800 */       String maxColQuery = "";
/* 1801 */       for (;;) { if (itr.hasNext()) {
/* 1802 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1803 */           ResultSet maxCol = null;
/*      */           try {
/* 1805 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1806 */             while (maxCol.next()) {
/* 1807 */               if (colTime == null) {
/* 1808 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1811 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1820 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1822 */               if (maxCol != null)
/* 1823 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1825 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1820 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1822 */               if (maxCol != null)
/* 1823 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1825 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1830 */     return colTime;
/*      */   }
/*      */   
/* 1833 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1834 */     tablename = null;
/* 1835 */     ResultSet rsTable = null;
/* 1836 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1838 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1839 */       while (rsTable.next()) {
/* 1840 */         tablename = rsTable.getString("DATATABLE");
/* 1841 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1842 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1855 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1846 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1849 */         if (rsTable != null)
/* 1850 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1852 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1858 */     String argsList = "";
/* 1859 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1861 */       if (showArgsMap.get(row) != null) {
/* 1862 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1863 */         if (showArgslist != null) {
/* 1864 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1865 */             if (argsList.trim().equals("")) {
/* 1866 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1869 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1876 */       e.printStackTrace();
/* 1877 */       return "";
/*      */     }
/* 1879 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1884 */     String argsList = "";
/* 1885 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1888 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1890 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1891 */         if (hideArgsList != null)
/*      */         {
/* 1893 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1895 */             if (argsList.trim().equals(""))
/*      */             {
/* 1897 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1901 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1909 */       ex.printStackTrace();
/*      */     }
/* 1911 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1915 */     StringBuilder toreturn = new StringBuilder();
/* 1916 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1923 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1924 */       Iterator itr = tActionList.iterator();
/* 1925 */       while (itr.hasNext()) {
/* 1926 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1927 */         String confirmmsg = "";
/* 1928 */         String link = "";
/* 1929 */         String isJSP = "NO";
/* 1930 */         HashMap tactionMap = (HashMap)itr.next();
/* 1931 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1932 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1933 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1934 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1935 */           (actionmap.containsKey(actionId))) {
/* 1936 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1937 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1938 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1939 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1940 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1942 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1948 */           if (isTableAction) {
/* 1949 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1952 */             tableName = "Link";
/* 1953 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1954 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1955 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1956 */             toreturn.append("</a></td>");
/*      */           }
/* 1958 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1959 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1960 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1961 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1967 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1973 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1975 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1976 */       Properties prop = (Properties)node.getUserObject();
/* 1977 */       String mgID = prop.getProperty("label");
/* 1978 */       String mgName = prop.getProperty("value");
/* 1979 */       String isParent = prop.getProperty("isParent");
/* 1980 */       int mgIDint = Integer.parseInt(mgID);
/* 1981 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1983 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1985 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1986 */       if (node.getChildCount() > 0)
/*      */       {
/* 1988 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1990 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1992 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1994 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 1998 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2003 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2005 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2007 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2009 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2013 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2016 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2017 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2019 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2023 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2025 */       if (node.getChildCount() > 0)
/*      */       {
/* 2027 */         builder.append("<UL>");
/* 2028 */         printMGTree(node, builder);
/* 2029 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2034 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2035 */     StringBuffer toReturn = new StringBuffer();
/* 2036 */     String table = "-";
/*      */     try {
/* 2038 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2039 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2040 */       float total = 0.0F;
/* 2041 */       while (it.hasNext()) {
/* 2042 */         String attName = (String)it.next();
/* 2043 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2044 */         boolean roundOffData = false;
/* 2045 */         if ((data != null) && (!data.equals(""))) {
/* 2046 */           if (data.indexOf(",") != -1) {
/* 2047 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2050 */             float value = Float.parseFloat(data);
/* 2051 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2054 */             total += value;
/* 2055 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2058 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2063 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2064 */       while (attVsWidthList.hasNext()) {
/* 2065 */         String attName = (String)attVsWidthList.next();
/* 2066 */         String data = (String)attVsWidthProps.get(attName);
/* 2067 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2068 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2069 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2070 */         String className = (String)graphDetails.get("ClassName");
/* 2071 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2072 */         if (percentage < 1.0F)
/*      */         {
/* 2074 */           data = percentage + "";
/*      */         }
/* 2076 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2078 */       if (toReturn.length() > 0) {
/* 2079 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2083 */       e.printStackTrace();
/*      */     }
/* 2085 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2091 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2092 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2093 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2094 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2095 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2096 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2097 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2098 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2099 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2102 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2103 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2104 */       splitvalues[0] = multiplecondition.toString();
/* 2105 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2108 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public java.util.Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2113 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2114 */     if (thresholdType != 3) {
/* 2115 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2116 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2117 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2118 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2119 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2120 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2122 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2123 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2124 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2125 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2126 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2127 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2129 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2130 */     if (updateSelected != null) {
/* 2131 */       updateSelected[0] = "selected";
/*      */     }
/* 2133 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2138 */       StringBuffer toreturn = new StringBuffer("");
/* 2139 */       if (commaSeparatedMsgId != null) {
/* 2140 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2141 */         int count = 0;
/* 2142 */         while (msgids.hasMoreTokens()) {
/* 2143 */           String id = msgids.nextToken();
/* 2144 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2145 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2146 */           count++;
/* 2147 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2148 */             if (toreturn.length() == 0) {
/* 2149 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2151 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2152 */             if (!image.trim().equals("")) {
/* 2153 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2155 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2156 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2159 */         if (toreturn.length() > 0) {
/* 2160 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2164 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2167 */       e.printStackTrace(); }
/* 2168 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2174 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = javax.servlet.jsp.JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2180 */   private static java.util.Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2181 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparam;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/* 2208 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2212 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005ffmt_005fparam = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2232 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2240 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2241 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2242 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2243 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.release();
/* 2244 */     this._005fjspx_005ftagPool_005ffmt_005fparam.release();
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2246 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2247 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody.release();
/* 2248 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/* 2249 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2250 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/* 2251 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.release();
/* 2252 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2253 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2254 */     this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2261 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2264 */     JspWriter out = null;
/* 2265 */     Object page = this;
/* 2266 */     JspWriter _jspx_out = null;
/* 2267 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2271 */       response.setContentType("text/html;charset=UTF-8");
/* 2272 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2274 */       _jspx_page_context = pageContext;
/* 2275 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2276 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2277 */       session = pageContext.getSession();
/* 2278 */       out = pageContext.getOut();
/* 2279 */       _jspx_out = out;
/*      */       
/* 2281 */       out.write("<!DOCTYPE html>\n");
/* 2282 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2283 */       out.write("\n\n\n\n\n\n\n\n\n\n");
/* 2284 */       if (_jspx_meth_c_005fchoose_005f0(_jspx_page_context))
/*      */         return;
/* 2286 */       out.write(10);
/* 2287 */       out.write(10);
/* 2288 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2290 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2291 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2292 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2294 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2296 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2298 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2300 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2301 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2302 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2303 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2306 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2307 */         String available = null;
/* 2308 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2309 */         out.write(10);
/*      */         
/* 2311 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2312 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2313 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2315 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2317 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2319 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2321 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2322 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2323 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2324 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2327 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2328 */           String unavailable = null;
/* 2329 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2330 */           out.write(10);
/*      */           
/* 2332 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2333 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2334 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2336 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2338 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2340 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2342 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2343 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2344 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2345 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2348 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2349 */             String unmanaged = null;
/* 2350 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2351 */             out.write(10);
/*      */             
/* 2353 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2354 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2355 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2357 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2359 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2361 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2363 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2364 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2365 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2366 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2369 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2370 */               String scheduled = null;
/* 2371 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2372 */               out.write(10);
/*      */               
/* 2374 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2375 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2376 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2378 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2380 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2382 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2384 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2385 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2386 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2387 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2390 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2391 */                 String critical = null;
/* 2392 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2393 */                 out.write(10);
/*      */                 
/* 2395 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2396 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2397 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2399 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2401 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2403 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2405 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2406 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2407 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2408 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2411 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2412 */                   String clear = null;
/* 2413 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2414 */                   out.write(10);
/*      */                   
/* 2416 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2417 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2418 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2420 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2422 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2424 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2426 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2427 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2428 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2429 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2432 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2433 */                     String warning = null;
/* 2434 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2435 */                     out.write(10);
/* 2436 */                     out.write(10);
/*      */                     
/* 2438 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2439 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2441 */                     out.write(10);
/* 2442 */                     out.write(10);
/* 2443 */                     out.write(10);
/* 2444 */                     out.write("\n\n\n<script>\nfunction submitTemplate()\n{\n\t");
/* 2445 */                     if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2447 */                     out.write("\n\tif(isValidationSuccess()){\n\t\t\t\tvar selectedradio = jQuery('input[name=\"monitorchooser\"]:checked').val()\t\t// NO I18N\n\t\t\t\tif(selectedradio == 'monitorlist'){\n\t\t\t\t\tservertypeindex=document.AMProcessTemplateForm.servertypelist.selectedIndex\n\t\t\t\t\tif(servertypeindex == -1){\n\t\t\t\t\t\talert('");
/* 2448 */                     if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */                       return;
/* 2450 */                     out.write("'); ");
/* 2451 */                     out.write("\n\t\t\t\t\t\treturn;\n\t\t\t\t\t}\n\t\t\t\t\tvar servertype=document.AMProcessTemplateForm.servertypelist.options[servertypeindex].value;\n\t\t\t\t\tif(document.getElementById(servertype+\"_selected\").length>0){\n\t\t\t\t\t\tfrmSelectAllIncludingEmpty(document.getElementById(servertype+\"_selected\"));\n\t\t\t\t\t}else{\n\t\t\t\t\t\talert('");
/* 2452 */                     if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*      */                       return;
/* 2454 */                     out.write("'); ");
/* 2455 */                     out.write("\n\t\t\t\t\t\treturn;\n\t\t\t\t\t}\n\t\t\t\t}else if(selectedradio== 'monitorgroup'){ ");
/* 2456 */                     out.write("\n\t\t\t\t\tvar mgchecked=false;\n\t\t\t\t\tif(document.AMProcessTemplateForm.select.checked){\n\t\t\t\t\t\tmgchecked=true;\n\t\t\t\t\t}\n\t\t\t\t\tfor(i=0;i<document.AMProcessTemplateForm.select.length;i++) {\n                        \t\t\tif(document.AMProcessTemplateForm.select[i].checked==true){\n\t\t\t                                mgchecked=true;\n\t\t\t\t\t\t\tbreak;\n                        \t\t\t}\n                                       \t}\n\t\t\t                if(!mgchecked){\n\t\t\t                        alert(\"");
/* 2457 */                     if (_jspx_meth_fmt_005fmessage_005f2(_jspx_page_context))
/*      */                       return;
/* 2459 */                     out.write("\"); ");
/* 2460 */                     out.write("\n\t\t                      \t\treturn;\n                \t\t\t}\n\t\t\t\t}else if(selectedradio = 'monitortype'){ ");
/* 2461 */                     out.write("\n\t\t\t\t\tvar typechecked=false;\n\t\t\t\t\tif(typeof document.AMProcessTemplateForm.servertypecheckbox.length == 'undefined')\n\t\t\t\t\t{\n\t\t\t\t\t\tif(document.AMProcessTemplateForm.servertypecheckbox.checked == true)\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\ttypechecked = true;\n\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t\t\telse\n\t\t\t\t\t{\n\t\t\t\t\t\tfor(i=0;i<document.AMProcessTemplateForm.servertypecheckbox.length;i++) {\n                                                \tif(document.AMProcessTemplateForm.servertypecheckbox[i].checked==true){\n                                                        \ttypechecked=true;\n                                                        \tbreak;\n                                                \t}\n\t\t\t\t\t\t}\n                                        }\n                                        if(!typechecked){\n                                                alert(\"");
/* 2462 */                     if (_jspx_meth_fmt_005fmessage_005f3(_jspx_page_context))
/*      */                       return;
/* 2464 */                     out.write("\"); ");
/* 2465 */                     out.write("\n                                                return;\n                                        }\n\t\t\t\t}\n\t\t\t\t\n\t\t//document.AMProcessTemplateForm.processcheckbox[0].checked=true;\n\t\t//fnSelectAll(document.AMProcessTemplateForm.processcheckbox[0]);\n\t\tdocument.AMProcessTemplateForm.submit();\n\t}\n\n}\n\nfunction isValidationSuccess()\n{\n\t// Check valid Template Name\n\t\tif(!document.AMProcessTemplateForm.templateName.value) {\n\t\t\talert(\"");
/* 2466 */                     if (_jspx_meth_fmt_005fmessage_005f4(_jspx_page_context))
/*      */                       return;
/* 2468 */                     out.write("\"); ");
/* 2469 */                     out.write("\n\t\t\treturn false;\n\t\t}\n\t// Check any Process is added\n\t\tif(! document.AMProcessTemplateForm.processcheckbox){\n\t\t\talert('");
/* 2470 */                     if (_jspx_meth_fmt_005fmessage_005f5(_jspx_page_context))
/*      */                       return;
/* 2472 */                     out.write("'); ");
/* 2473 */                     out.write("\n\t\t\treturn false;\n\t\t}else {\n\t\t\tvar checked=false;\n\t\t\tvar processcheckboxvar=document.AMProcessTemplateForm.processcheckbox;\n\t\t\tfor(var j=0;j<processcheckboxvar.length;j++){\n\t\t\t\tif(processcheckboxvar[j].checked){\n\t\t\t\t\tchecked=true;\n\t\t\t\t\tbreak;\n\t\t\t\t}\n\t\t\t\t//checked=true;\n\t\t\t}\n\t\t\tif(!checked){\n\t\t\talert('");
/* 2474 */                     if (_jspx_meth_fmt_005fmessage_005f6(_jspx_page_context))
/*      */                       return;
/* 2476 */                     out.write("'); ");
/* 2477 */                     out.write("\n\t\t\treturn false;\n\t\t\t}\n\t\t}\n\n\t\treturn true;\n\t// Check any monitor is associated to template\n}\n\nfunction insertRow(processdispname,processname,processcmd,bgcolor,ispnameregex,iscmdregex,type)\n{\n\t\tvar table=document.getElementById(\"processtableid\"); ");
/* 2478 */                     out.write("\n        var rowcount=table.rows.length;\n        var bgcolor=\"whitegrayrightalign\"; ");
/* 2479 */                     out.write("\n\t\tif(rowcount==2){\n\t\t\tshowDiv(\"showrow\");\n\t\t\tshowDiv(\"adddeleteprocess\");\n\t\t}\n\t\tif(rowcount%2 == 0){\n           bgcolor=\"whitegrayrightalign\"; ");
/* 2480 */                     out.write("\n        }\n        var pnmaeregexid='0';\n        var pcmdregexid='0';\n\n        if(ispnameregex=='true')\n\t\t{\n        \tpnmaeregexid='1';\t\n        }\n\n        if(iscmdregex=='true')\n        {\n        \tpcmdregexid='1';\n        }\n\n        var row = table.insertRow(rowcount);\n        row.className=bgcolor;\n        var checkcell = row.insertCell(0);\n        checkcell.width=\"4%\";\n\n        // Create a checkbox\n        var element1 = document.createElement(\"input\");\n        element1.id=\"processcheckbox\";\n        element1.name=\"processcheckbox\";\n        element1.type = \"checkbox\";        \n        element1.value=rowcount;\n        element1.onclick=function() {javascript:controlAllServSelCheckbox()}       \n        checkcell.className=bgcolor;\n        checkcell.appendChild(element1);\n        element1.checked=true;\n       \n        // Set process display name as hidden value to handle at serverside\n        var pnamehidden=document.createElement(\"input\");\n        pnamehidden.type=\"hidden\";\n        pnamehidden.name=\"pdispname_\"+rowcount;\n");
/* 2481 */                     out.write("\t\tpnamehidden.id=\"pdispname_\"+rowcount;\n        pnamehidden.value=processdispname;\n        document.forms[\"AMProcessTemplateForm\"].appendChild(pnamehidden);   \n\n        // Set process name as hidden value to handle at serverside\n        var pnamehidden=document.createElement(\"input\");\n        pnamehidden.type=\"hidden\";\n        pnamehidden.name=\"pname_\"+rowcount;\n\t\tpnamehidden.id=\"pname_\"+rowcount;\n        pnamehidden.value=processname;\n        document.forms[\"AMProcessTemplateForm\"].appendChild(pnamehidden);\n\n        // Set process name as hidden value to handle at serverside\n        var pnamehidden=document.createElement(\"input\");\n        pnamehidden.type=\"hidden\";\n        pnamehidden.name=\"pcmd_\"+rowcount;\n\t\tpnamehidden.id=\"pcmd_\"+rowcount;\n        pnamehidden.value=processcmd;\n        document.forms[\"AMProcessTemplateForm\"].appendChild(pnamehidden);\n\n        var pnamehidden=document.createElement(\"input\");\n        pnamehidden.type=\"hidden\";\n        pnamehidden.name=\"pnameregex_\"+rowcount;\n\t\tpnamehidden.id=\"pnameregex_\"+rowcount;\n");
/* 2482 */                     out.write("        pnamehidden.value=pnmaeregexid;\n        document.forms[\"AMProcessTemplateForm\"].appendChild(pnamehidden);\n\n        var pnamehidden=document.createElement(\"input\");\n        pnamehidden.type=\"hidden\";\n        pnamehidden.name=\"pcmdregex_\"+rowcount;\n\t\tpnamehidden.id=\"pcmdregex_\"+rowcount;\n        pnamehidden.value=pcmdregexid;\n        document.forms[\"AMProcessTemplateForm\"].appendChild(pnamehidden);\n\n        if (type == 0) {\n\t        //Create a cell for process display name\n\t        var pdispnamecell = row.insertCell(1);\n\t        pdispnamecell.className=bgcolor;\n\t        pdispnamecell.id=\"pdispnamecell_\"+rowcount;\n\t        pdispnamecell.innerHTML=processdispname;\n\t        pdispnamecell.width=\"21%\";\n\t        \n\t \t\t//Create a cell for processname\n\t        var pnamecell = row.insertCell(2);\n\t        pnamecell.className=bgcolor;\n\t\t\tpnamecell.id=\"pnamecell_\"+rowcount;\n\t        pnamecell.innerHTML=processname;\n\t\t\tpnamecell.width=\"21%\";\n\n\t        //Create a cell for processcmd\n\t        var pcmdcell = row.insertCell(3);\n");
/* 2483 */                     out.write("\t        pcmdcell.className=bgcolor + \" apm-breakword\";\n\t\t\tpcmdcell.id=\"pcmdcell_\"+rowcount;\n\t\t\tpcmdcell.width=\"50%\";\n\t\t\tif(processcmd){\n\t        \tpcmdcell.innerHTML=processcmd;\n\t\t\t}else{\n\t        \tpcmdcell.innerHTML=\"&nbsp;\";\n\t\t\t}\n\n\t\t\t// Create a cell for edit process/service\n\t        var peditcell=row.insertCell(4);\n\t        peditcell.className=bgcolor;\n\t        peditcell.width=\"4%\";\n\t        peditcell.align=\"center\"\n\t        peditcell.innerHTML='<a href=\"javascript:popupEditProcess('+rowcount+',false)\"><img src=\"/images/icon_edit.gif\" border=\"0\"></a>';\t        \n        } else {\n        \t//Create a cell for processname\n            var pnamecell = row.insertCell(1);\n            pnamecell.className=bgcolor;\n    \t\tpnamecell.id=\"pnamecell_\"+rowcount;\n            pnamecell.innerHTML=processname;\n    \t\tpnamecell.width=\"40%\";\n\n            //Create a cell for processcmd\n            var pcmdcell = row.insertCell(2);\n            pcmdcell.className=bgcolor + \" apm-breakword\";\n    \t\tpcmdcell.id=\"pcmdcell_\"+rowcount;\n    \t\tpcmdcell.width=\"50%\";\n");
/* 2484 */                     out.write("    \t\tif(processcmd){\n            \tpcmdcell.innerHTML=processcmd;\n    \t\t}else{\n            \tpcmdcell.innerHTML=\"&nbsp;\";\n    \t\t}\n\n    \t\t// Create a cell for edit process/service\n            var peditcell=row.insertCell(3);\n            peditcell.className=bgcolor;\n            peditcell.width=\"6%\";\n            peditcell.align=\"center\"\n            peditcell.innerHTML='<a href=\"javascript:popupEditProcess('+rowcount+',false)\"><img src=\"/images/icon_edit.gif\" border=\"0\"></a>';        \t\n        }\n}\n\nfunction controlAllServSelCheckbox()\n{\n\tvar uncheck = false;\n\tvar service_check_box = document.AMProcessTemplateForm.processcheckbox;\n\tif (service_check_box != null) {\n\t\tvar len = service_check_box.length;\n\t\tfor (var i=1;i<len;i++) {\n\t\t\tif (service_check_box[i].checked == false) {\n\t\t\t\tuncheck = true;\n\t\t\t\tbreak;\n\t\t\t}\n\t\t}\n\t}\n\tvar allServSelBox = document.AMProcessTemplateForm.processcheckbox;\n\tif (allServSelBox != null) {\n\t\tallServSelBox[0].checked=!uncheck;\n\t}\t\n}\n\nfunction editProcess(processid,modifieddispname,modifiedname,modifiedcmd,pnameregexmod,cmdregexmod,type)\n");
/* 2485 */                     out.write("{\n\tvar pnmaeregexid='0';\n\tvar pcmdregexid='0';\n\n\tif(pnameregexmod=='true')\n\t{\n\t\tpnmaeregexid='1';\t\n\t}\n\tif(cmdregexmod=='true')\n\t{\n\t\tpcmdregexid='1';\n\t}\n\t\n   \tdocument.getElementById(\"pnamecell_\"+processid).innerHTML=modifiedname;\n\tdocument.getElementById(\"pcmdcell_\"+processid).innerHTML=modifiedcmd;\n\tif (type == 0) {\n\t\tdocument.getElementById(\"pdispnamecell_\"+processid).innerHTML=modifieddispname;\n\t}\n    \n\t// Create a hidden element for modified process name\n    if(document.getElementById(\"pname_\"+processid)){\n\t\tdocument.getElementById(\"pname_\"+processid).value=modifiedname;\n\t\tdocument.getElementById(\"pcmd_\"+processid).value=modifiedcmd;\n\t\tdocument.getElementById(\"pnameregex_\"+processid).value=pnmaeregexid;\n\t\tdocument.getElementById(\"pcmdregex_\"+processid).value=pcmdregexid;\n\t\tdocument.getElementById(\"pdispname_\"+processid).value=modifieddispname;\n\t} \n    else \n    {\n \t\tvar pdispnamehidden=document.createElement(\"input\");\n \t\tpdispnamehidden.type=\"hidden\";\n \t\tpdispnamehidden.name=\"pdispnameedit_\"+processid;\n \t\tpdispnamehidden.id=\"pdispnameedit_\"+processid;\n");
/* 2486 */                     out.write(" \t\tpdispnamehidden.value=modifieddispname;\n        document.forms[\"AMProcessTemplateForm\"].appendChild(pdispnamehidden);\n        \n\t\tvar pnamehidden=document.createElement(\"input\");\n        pnamehidden.type=\"hidden\";\n        pnamehidden.name=\"pnameedit_\"+processid;\n        pnamehidden.id=\"pnameedit_\"+processid;\n        pnamehidden.value=modifiedname;\n        document.forms[\"AMProcessTemplateForm\"].appendChild(pnamehidden);\n        \n        //Create a hidden element for modified process arg\n        var pcmdhidden=document.createElement(\"input\");\n        pcmdhidden.type=\"hidden\";\n        pcmdhidden.name=\"pcmdedit_\"+processid;\n        pcmdhidden.id=\"pcmdedit_\"+processid;\n        pcmdhidden.value=modifiedcmd;\n        document.forms[\"AMProcessTemplateForm\"].appendChild(pcmdhidden);\n\n\n        var pnamehidden=document.createElement(\"input\");\n\t\tpnamehidden.type=\"hidden\";\n\t\tpnamehidden.name=\"pnameregexedit_\"+processid;\n\t\tpnamehidden.id=\"pnameregexedit_\"+processid;\n\t\tpnamehidden.value=pnmaeregexid;\n\t\tdocument.forms[\"AMProcessTemplateForm\"].appendChild(pnamehidden);\n");
/* 2487 */                     out.write("\n\t\tvar pnamehidden=document.createElement(\"input\");\n\t\tpnamehidden.type=\"hidden\";\n\t\tpnamehidden.name=\"pcmdregexedit_\"+processid;\n\t\tpnamehidden.id=\"pcmdregexedit_\"+processid;\n\t\tpnamehidden.value=pcmdregexid;\n\t\tdocument.forms[\"AMProcessTemplateForm\"].appendChild(pnamehidden);\n\t}\n}\n\nfunction deleteProcess()\n{\n\t ");
/* 2488 */                     if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */                       return;
/* 2490 */                     out.write("\n        ");
/* 2491 */                     if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2493 */                     out.write("\n\n}\n\nfunction removeFormElements (id) {\n\t// removing form elements\n\tif (document.getElementById(\"pdispname_\"+id)!= null) {\n\t\tdocument.getElementById(\"pdispname_\"+id).remove();\n\t}\n\t\n\tif (document.getElementById(\"pname_\"+id)!= null) {\n\t\tdocument.getElementById(\"pname_\"+id).remove();\n\t}\n\t\n\tif (document.getElementById(\"pcmd_\"+id)!= null) {\n\t\tdocument.getElementById(\"pcmd_\"+id).remove();\n\t}\n\t\n\tif (document.getElementById(\"pnameregex_\"+id)!= null) {\n\t\tdocument.getElementById(\"pnameregex_\"+id).remove();\n\t}\n\t\n\tif (document.getElementById(\"pcmdregex_\"+id)!= null) {\n\t\tdocument.getElementById(\"pcmdregex_\"+id).remove();\n\t}\n}\n\nfunction fnSelectAll(e)\n{\n        ToggleAll(e,document.AMProcessTemplateForm,\"processcheckbox\") ; ");
/* 2494 */                     out.write("\n}\n\nfunction cancelTemplate()\n{\n\tvar url='/ProcessTemplates.do?method=showAllTemplates&templatetype=");
/* 2495 */                     if (_jspx_meth_c_005fout_005f4(_jspx_page_context))
/*      */                       return;
/* 2497 */                     out.write(39);
/* 2498 */                     out.write(59);
/* 2499 */                     out.write(32);
/* 2500 */                     out.write("\n\tdocument.AMProcessTemplateForm.action=url;\n\tdocument.AMProcessTemplateForm.submit();\n}\n</script>\n\n\n\n<SCRIPT src=\"template/calendar.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-en.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-setup.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/listview.js\" type=text/javascript></SCRIPT>\n\n<script>\n\nfunction popupAddProcessPage()\n{\n\tfnOpenNewWindowWithHeightWidthPlacement('/jsp/TemplateProcessChooser.jsp?templatetype=");
/* 2501 */                     if (_jspx_meth_c_005fout_005f5(_jspx_page_context))
/*      */                       return;
/* 2503 */                     out.write("&templatetypestr=");
/* 2504 */                     if (_jspx_meth_c_005fout_005f6(_jspx_page_context))
/*      */                       return;
/* 2506 */                     out.write("',1250,650,250,150); ");
/* 2507 */                     out.write("\n\t<!-- fnOpenNewWindow('../HostResource.do?addtotemplate=true&templatetype=");
/* 2508 */                     if (_jspx_meth_c_005fout_005f7(_jspx_page_context))
/*      */                       return;
/* 2510 */                     out.write("&templatetypestr=");
/* 2511 */                     if (_jspx_meth_c_005fout_005f8(_jspx_page_context))
/*      */                       return;
/* 2513 */                     out.write("'); --> ");
/* 2514 */                     out.write("\n\n}\n\nfunction popupEditProcess(processid,alreadyConfigured)\n{\n\t");
/* 2515 */                     if (_jspx_meth_logic_005fpresent_005f2(_jspx_page_context))
/*      */                       return;
/* 2517 */                     out.write("\n    var params=\"&editprocess=true&processid=\"+processid; ");
/* 2518 */                     out.write("\n    if(!alreadyConfigured){\n\t\tparams=params+\"&processdispname=\"+document.getElementById(\"pdispname_\"+processid).value+\"&processname=\"+document.getElementById(\"pname_\"+processid).value+\"&processarg=\"+encodeURIComponent(document.getElementById(\"pcmd_\"+processid).value); ");
/* 2519 */                     out.write("\n\t\tfnOpenNewWindowWithHeightWidthPlacement('/ProcessTemplates.do?method=editProcess&templatetype=");
/* 2520 */                     if (_jspx_meth_c_005fout_005f9(_jspx_page_context))
/*      */                       return;
/* 2522 */                     out.write("&templatetypestr=");
/* 2523 */                     if (_jspx_meth_c_005fout_005f10(_jspx_page_context))
/*      */                       return;
/* 2525 */                     out.write("'+params,1250,550,200,100); ");
/* 2526 */                     out.write("\n\t}else{\n\t\tfnOpenNewWindowWithHeightWidthPlacement('/ProcessTemplates.do?method=editProcess&templatetype=");
/* 2527 */                     if (_jspx_meth_c_005fout_005f11(_jspx_page_context))
/*      */                       return;
/* 2529 */                     out.write("&templatetypestr=");
/* 2530 */                     if (_jspx_meth_c_005fout_005f12(_jspx_page_context))
/*      */                       return;
/* 2532 */                     out.write("'+params,1250,550,200,100); ");
/* 2533 */                     out.write("\n\t}\n}\n\n</script>\n");
/*      */                     
/* 2535 */                     org.apache.struts.util.TokenProcessor token = org.apache.struts.util.TokenProcessor.getInstance();
/* 2536 */                     token.saveToken(request);
/*      */                     
/* 2538 */                     out.write(10);
/* 2539 */                     if (_jspx_meth_c_005fchoose_005f1(_jspx_page_context))
/*      */                       return;
/* 2541 */                     out.write(10);
/* 2542 */                     out.write(10);
/*      */                     
/* 2544 */                     FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 2545 */                     _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2546 */                     _jspx_th_html_005fform_005f0.setParent(null);
/*      */                     
/* 2548 */                     _jspx_th_html_005fform_005f0.setMethod("post");
/*      */                     
/* 2550 */                     _jspx_th_html_005fform_005f0.setAction("/ProcessTemplates");
/*      */                     
/* 2552 */                     _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 2553 */                     int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2554 */                     if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                       for (;;) {
/* 2556 */                         out.write("\n<input type=hidden name='method' value=\"associateProcessTemplate\">\n<input type=hidden id='templatetype' name='templatetype' value='");
/* 2557 */                         if (_jspx_meth_c_005fout_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2559 */                         out.write("'>\n<input type=hidden name='selectedservertype' value='");
/* 2560 */                         if (_jspx_meth_c_005fout_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2562 */                         out.write("'>\n<table width=\"99%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"itadmin-hide\">\n        <tr>\n\t");
/*      */                         
/* 2564 */                         ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2565 */                         _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2566 */                         _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_html_005fform_005f0);
/* 2567 */                         int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2568 */                         if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                           for (;;) {
/* 2570 */                             out.write("\n                ");
/*      */                             
/* 2572 */                             WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2573 */                             _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2574 */                             _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                             
/* 2576 */                             _jspx_th_c_005fwhen_005f2.setTest("${! empty param.edit && param.edit=='true' }");
/* 2577 */                             int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2578 */                             if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                               for (;;) {
/* 2580 */                                 out.write("\n\t\t<input type=hidden name='edit' value=\"true\">\n\t\t<input type=hidden name='templateid' value='");
/* 2581 */                                 if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                                   return;
/* 2583 */                                 out.write("'>\n         ");
/* 2584 */                                 if (EnterpriseUtil.isAdminServer())
/*      */                                 {
/*      */ 
/* 2587 */                                   out.write("\n          <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2588 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getEnterpriseAdminPage(request));
/* 2589 */                                   out.write(" &gt;  <a href='/ProcessTemplates.do?method=showAllTemplates&templatetype=");
/* 2590 */                                   if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                                     return;
/* 2592 */                                   out.write("' class=\"bcinactive\">\n\t ");
/* 2593 */                                   if (_jspx_meth_c_005fchoose_005f3(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                                     return;
/* 2595 */                                   out.write("\n        </a> &gt; <span class=\"bcactive\">\n\t");
/* 2596 */                                   if (_jspx_meth_fmt_005fmessage_005f17(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                                     return;
/* 2598 */                                   out.write("\n         ");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 2602 */                                   out.write("\n                 <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2603 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 2604 */                                   out.write(" &gt;  <a href='/ProcessTemplates.do?method=showAllTemplates&templatetype=");
/* 2605 */                                   if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                                     return;
/* 2607 */                                   out.write("' class=\"bcinactive\">\n\t\t ");
/* 2608 */                                   if (_jspx_meth_c_005fchoose_005f4(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                                     return;
/* 2610 */                                   out.write("\n\t</a> &gt <span class=\"bcactive\"> ");
/* 2611 */                                   out.write(10);
/* 2612 */                                   out.write(9);
/* 2613 */                                   if (_jspx_meth_fmt_005fmessage_005f20(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                                     return;
/* 2615 */                                   out.write("\n\t</span></td> ");
/* 2616 */                                   out.write("\n         ");
/*      */                                 }
/* 2618 */                                 out.write("\n\n\t\t");
/* 2619 */                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2620 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2624 */                             if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2625 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                             }
/*      */                             
/* 2628 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2629 */                             out.write(10);
/* 2630 */                             out.write(9);
/* 2631 */                             out.write(9);
/*      */                             
/* 2633 */                             OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2634 */                             _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 2635 */                             _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f2);
/* 2636 */                             int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 2637 */                             if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                               for (;;) {
/* 2639 */                                 out.write("\n         ");
/*      */                                 
/* 2641 */                                 if (EnterpriseUtil.isAdminServer())
/*      */                                 {
/*      */ 
/* 2644 */                                   out.write("\n          <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2645 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getEnterpriseAdminPage(request));
/* 2646 */                                   out.write(" &gt; <a href='/ProcessTemplates.do?method=showAllTemplates&templatetype=");
/* 2647 */                                   if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/*      */                                     return;
/* 2649 */                                   out.write("' class=\"bcinactive\">\n\t ");
/* 2650 */                                   if (_jspx_meth_c_005fchoose_005f5(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/*      */                                     return;
/* 2652 */                                   out.write("\n\t\t</a> &gt <span class=\"bcactive\"> ");
/* 2653 */                                   out.write(10);
/* 2654 */                                   out.write(9);
/* 2655 */                                   out.write(9);
/* 2656 */                                   if (_jspx_meth_fmt_005fmessage_005f23(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/*      */                                     return;
/* 2658 */                                   out.write("\n\t\t</span></td> ");
/* 2659 */                                   out.write("\n         ");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 2663 */                                   out.write("\n                 <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2664 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 2665 */                                   out.write(" &gt; <a href='/ProcessTemplates.do?method=showAllTemplates&templatetype=");
/* 2666 */                                   if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/*      */                                     return;
/* 2668 */                                   out.write("' class=\"bcinactive\">\n\t\t ");
/* 2669 */                                   if (_jspx_meth_c_005fchoose_005f6(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/*      */                                     return;
/* 2671 */                                   out.write("\n\t\t</a> &gt <span class=\"bcactive\"> ");
/* 2672 */                                   out.write(10);
/* 2673 */                                   out.write(9);
/* 2674 */                                   out.write(9);
/* 2675 */                                   if (_jspx_meth_fmt_005fmessage_005f26(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/*      */                                     return;
/* 2677 */                                   out.write("\n\t\t</span></td> ");
/* 2678 */                                   out.write("\n         ");
/*      */                                 }
/* 2680 */                                 out.write(10);
/* 2681 */                                 out.write(9);
/* 2682 */                                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 2683 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2687 */                             if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 2688 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                             }
/*      */                             
/* 2691 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2692 */                             out.write(10);
/* 2693 */                             out.write(9);
/* 2694 */                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2695 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2699 */                         if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2700 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                         }
/*      */                         
/* 2703 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2704 */                         out.write("\n        </tr>\n</table>\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n<tr>\n\n<td>\n\n\n<div id=\"two\">\n\n <fieldset style=\"padding-bottom:10px;\">\n\t\t\t\t\t\t\t\t\t\t<legend>");
/* 2705 */                         if (_jspx_meth_fmt_005fmessage_005f27(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2707 */                         out.write(" </legend> ");
/* 2708 */                         out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\"   >\n\n<tr><td colspan=\"2\" style=\"height:5px;\" ></td></tr>\n\n<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\" width=\"25%\" align=\"right\">");
/* 2709 */                         out.print(FormatUtil.getString("am.webclient.processtemplate.templatename"));
/* 2710 */                         out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"left\" >\n");
/* 2711 */                         if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2713 */                         out.write("\n</td>\n</tr>\n\n\n<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\" class=\"bodytext\" align=\"right\">");
/* 2714 */                         out.print(FormatUtil.getString("am.webclient.maintenance.description"));
/* 2715 */                         out.write("</td>\n<td align=\"left\">");
/* 2716 */                         if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2718 */                         out.write("\n\n</tr>\n<tr style=\"display:none\">\n<td></td>\n<td valign=\"top\" class=\"bodytext\" align=\"right\">");
/* 2719 */                         out.print(FormatUtil.getString("am.webclient.process.regex"));
/* 2720 */                         out.write("</td>\n<td align=\"left\">\n<input type=\"radio\" id=\"onceday\" name=\"matchinterval\" value=\"0\" ");
/* 2721 */                         if (_jspx_meth_c_005fif_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2723 */                         out.write(" > &nbsp;");
/* 2724 */                         out.print(FormatUtil.getString("am.webclient.template.onceday"));
/* 2725 */                         out.write("\n<input type=\"radio\" id=\"everypolls\" name=\"matchinterval\" value=\"1\" ");
/* 2726 */                         if (_jspx_meth_c_005fif_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2728 */                         out.write(" >&nbsp;");
/* 2729 */                         out.print(FormatUtil.getString("am.webclient.template.everypolls"));
/* 2730 */                         out.write("\n</td>\n</tr>\n\n<tr><td colspan=\"2\" style=\"height:10px;\"></td></tr>\n\t  </table>\n\n </fieldset>\n</div>\n</td>\n\n<td width=\"35%\" valign=\"top\"><p>  <a class=\"process-tooltip\" href=\"#\"><span class=\"process-custom process-warning\"><img src=\"/images/arrow-help.gif\" alt=\"Warning\" class=\"process-tooltip-img \"  width=\"15\" height=\"27\"/>\n<em> ");
/* 2731 */                         if (_jspx_meth_c_005fchoose_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2733 */                         out.write("\n</em> \n");
/* 2734 */                         if (_jspx_meth_fmt_005fmessage_005f30(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2736 */                         out.write("</span></a> </p>");
/* 2737 */                         out.write("\n\n</td>\n</tr>\n\n</table>\n\n\n\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n<tr>\n\n<td>\n\n\n<div id=\"two\">\n\n<fieldset >\n");
/* 2738 */                         if (_jspx_meth_c_005fif_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2740 */                         out.write(10);
/* 2741 */                         if (_jspx_meth_c_005fif_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2743 */                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\n<td align=\"right\">\n<div id=\"adddeleteprocess\" style=\"display:none\">\n<a href='javascript:popupAddProcessPage()' class='staticlinks'>");
/* 2744 */                         if (_jspx_meth_fmt_005fmessage_005f33(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2746 */                         out.write("</a>&nbsp;&nbsp;&iota;&nbsp;&nbsp;<a href='javascript:deleteProcess()' class='staticlinks'>");
/* 2747 */                         if (_jspx_meth_fmt_005fmessage_005f34(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2749 */                         out.write("</a>");
/* 2750 */                         out.write("\n</div>\n</td>\n");
/* 2751 */                         if (_jspx_meth_c_005fif_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2753 */                         out.write("\n<td width=\"1%\"></td>\n</tr>\n</table>\n\n\n\n<table width=\"100%\" id=\"processtableid\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" >\n<tr><td style=\"height:10px;\"></td></tr>\n");
/* 2754 */                         if (_jspx_meth_c_005fif_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2756 */                         out.write(10);
/* 2757 */                         out.write(10);
/* 2758 */                         if (_jspx_meth_c_005fif_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2760 */                         out.write("\n\n\n\n");
/* 2761 */                         if (_jspx_meth_c_005fif_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2763 */                         out.write("\n\t<table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\"  id=\"noprocessdiv\" style=\"display:none\" align=\"center\">\n\t<tr>\n\t<td width=\"300\">&nbsp;</td>\n    \t<td  width=\"50%\" style=\"height:10px;\" class=\"bodytext\" align=\"center\" >\n<img src=\"/images/icon_message_success_sml.gif\"/ style=\"position:relative; top:5px;\">\t");
/* 2764 */                         if (_jspx_meth_fmt_005fmessage_005f40(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2766 */                         out.write(32);
/* 2767 */                         out.write("<a href='javascript:popupAddProcessPage()' class='staticlinks'>");
/* 2768 */                         if (_jspx_meth_fmt_005fmessage_005f41(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2770 */                         out.write("</a>");
/* 2771 */                         out.write("\n\t</td>\n\t<td  width=\"25%\">&nbsp;</td>\n\t</tr>\n\n\t</table>\n\n\n");
/* 2772 */                         if (_jspx_meth_c_005fif_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2774 */                         out.write("\n<table width=\"100%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" >\n<tr>\n\t<td colspan=\"6\" height=\"29\"  align=\"right\"></td>\n        <td height=\"29\" colspan=\"2\" class=\"bodytext\">\n</td>\n</tr>\n</table>\n\n\n</fieldset ></div>\n</td>\n\n<td width=\"35%\" valign=\"top\"><p>  <a class=\"process-tooltip\" href=\"#\"><span class=\"process-custom process-warning\"><img src=\"/images/arrow-help.gif\" alt=\"Warning\" class=\"process-tooltip-img \"  width=\"15\" height=\"27\"/>\n<em>");
/* 2775 */                         if (_jspx_meth_fmt_005fmessage_005f42(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2777 */                         out.write("</em> ");
/* 2778 */                         if (_jspx_meth_fmt_005fmessage_005f43(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2780 */                         out.write("</span></a> </p>");
/* 2781 */                         out.write("\n</td>\n</tr>\n\n</table>\n\n\n\n\n\n\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n<tr>\n\n<td>\n\n\n<div id=\"two\">\n\n <fieldset style=\"paddign-bottom:0px; border-bottom:none;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<legend>");
/* 2782 */                         out.print(FormatUtil.getString("am.webclient.configurealert.configurealertsforattributes"));
/* 2783 */                         out.write("</legend>\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<!--");
/*      */                         
/* 2785 */                         IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2786 */                         _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 2787 */                         _jspx_th_c_005fif_005f11.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 2789 */                         _jspx_th_c_005fif_005f11.setTest("${templatetype == PROCESSTEMPLATE}");
/* 2790 */                         int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 2791 */                         if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                           for (;;) {
/* 2793 */                             out.write("\n    <span class=\"new-report-heading\" style=\"line-height:30px; padding-left:3px; \"><img src=\"/images/process-step3.png\" width=\"27\" height=\"27\" style=\"position:relative; top:8px; left:5px;\"> &nbsp;  ");
/* 2794 */                             out.print(FormatUtil.getString("am.webclient.configurealert.configurealertsforattributes"));
/* 2795 */                             out.write("</span>\n");
/* 2796 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 2797 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2801 */                         if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 2802 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                         }
/*      */                         
/* 2805 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2806 */                         out.write(10);
/*      */                         
/* 2808 */                         IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2809 */                         _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 2810 */                         _jspx_th_c_005fif_005f12.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 2812 */                         _jspx_th_c_005fif_005f12.setTest("${templatetype == SERVICETEMPLATE}");
/* 2813 */                         int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 2814 */                         if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                           for (;;) {
/* 2816 */                             out.write("\n<span class=\"new-report-heading\" style=\"line-height:30px; padding-left:3px; \"><img src=\"/images/process-step3.png\" width=\"27\" height=\"27\" style=\"position:relative; top:8px; left:5px;\"> &nbsp;  ");
/* 2817 */                             out.print(FormatUtil.getString("am.webclient.configurealert.configurealertsforattribute"));
/* 2818 */                             out.write("</span>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 2819 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 2820 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2824 */                         if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 2825 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                         }
/*      */                         
/* 2828 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 2829 */                         out.write("-->\n    <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\" style=\"padding-bottom:0px;\">\n\n<tr><td style=\"height:10px;\"></td></tr>\n\t<tr>\n\t<td>");
/* 2830 */                         if (_jspx_meth_c_005fimport_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2832 */                         out.write("</td>\n\t</tr>\n\n\n</table>\n\n\n  </fieldset></div>\n</td>\n\n<td width=\"35%\"><p>  <a class=\"process-tooltip\" href=\"#\"><span class=\"process-custom process-warning\"><img src=\"/images/arrow-help.gif\" alt=\"Warning\" class=\"process-tooltip-img \"  width=\"15\" height=\"27\"/><em>");
/* 2833 */                         if (_jspx_meth_fmt_005fmessage_005f44(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2835 */                         out.write("</em>\n");
/* 2836 */                         if (_jspx_meth_c_005fchoose_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2838 */                         out.write(" \n</span></a> </p>");
/* 2839 */                         out.write("\n</td>\n</tr>\n\n</table>\n\n\n\n\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n<tr>\n\n<td>\n\n<div id=\"two\">\n\n\n <fieldset >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<legend>");
/* 2840 */                         out.print(FormatUtil.getString("am.webclient.processtemplate.associatemonitors"));
/* 2841 */                         out.write("</legend>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<!--<span class=\"new-report-heading\" style=\"line-height:30px; padding-left:3px;\"><img src=\"/images/process-step4.png\" width=\"27\" height=\"27\" style=\"position:relative; top:8px; left:5px;\">  &nbsp;  ");
/* 2842 */                         out.print(FormatUtil.getString("am.webclient.processtemplate.associatemonitors"));
/* 2843 */                         out.write("</span>-->\n\n    <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\" >\n\n<tr><td style=\"height:10px;\"></td></tr>\n\t<tr>\n\t<td>");
/* 2844 */                         if (_jspx_meth_c_005fimport_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2846 */                         out.write("</td>\n\t</tr>\n\n\n</table>\n\n\n  </fieldset></div>\n  </td>\n\n  <td width=\"35%\"><p>  <a class=\"process-tooltip\" href=\"#\"><span class=\"process-custom process-warning\"><img src=\"/images/arrow-help.gif\" alt=\"Warning\" class=\"process-tooltip-img \"  width=\"15\" height=\"27\"/><em>");
/* 2847 */                         if (_jspx_meth_fmt_005fmessage_005f47(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2849 */                         out.write("</em> \n");
/* 2850 */                         if (_jspx_meth_fmt_005fmessage_005f48(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2852 */                         out.write("</span></a> </p>");
/* 2853 */                         out.write("\n  </td>\n  </tr>\n\n  </table>\n\n\n\n\n\n\n\n<br>\n\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n<tr >\n        <td height=\"29\"  class=\"bodytext\" align=\"center\">\n\t");
/*      */                         
/* 2855 */                         ChooseTag _jspx_th_c_005fchoose_005f12 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2856 */                         _jspx_th_c_005fchoose_005f12.setPageContext(_jspx_page_context);
/* 2857 */                         _jspx_th_c_005fchoose_005f12.setParent(_jspx_th_html_005fform_005f0);
/* 2858 */                         int _jspx_eval_c_005fchoose_005f12 = _jspx_th_c_005fchoose_005f12.doStartTag();
/* 2859 */                         if (_jspx_eval_c_005fchoose_005f12 != 0) {
/*      */                           for (;;) {
/* 2861 */                             out.write(10);
/* 2862 */                             out.write(9);
/*      */                             
/* 2864 */                             WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2865 */                             _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 2866 */                             _jspx_th_c_005fwhen_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/*      */                             
/* 2868 */                             _jspx_th_c_005fwhen_005f12.setTest("${! empty param.edit && param.edit=='true' }");
/* 2869 */                             int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 2870 */                             if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */                               for (;;) {
/* 2872 */                                 out.write("\n<input name=\"save\" type=\"button\" class=\"buttons btn_highlt\" onclick=\"javascript:submitTemplate();\" value=\"");
/* 2873 */                                 out.print(FormatUtil.getString("am.webclient.processtemplate.savetemplate"));
/* 2874 */                                 out.write("\">\n\t");
/* 2875 */                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 2876 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2880 */                             if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 2881 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12); return;
/*      */                             }
/*      */                             
/* 2884 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/*      */                             
/* 2886 */                             OtherwiseTag _jspx_th_c_005fotherwise_005f12 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2887 */                             _jspx_th_c_005fotherwise_005f12.setPageContext(_jspx_page_context);
/* 2888 */                             _jspx_th_c_005fotherwise_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/* 2889 */                             int _jspx_eval_c_005fotherwise_005f12 = _jspx_th_c_005fotherwise_005f12.doStartTag();
/* 2890 */                             if (_jspx_eval_c_005fotherwise_005f12 != 0) {
/*      */                               for (;;) {
/* 2892 */                                 out.write("\n\t<input name=\"save\" type=\"button\" class=\"buttons btn_highlt\" onclick=\"javascript:submitTemplate();\" value=\"");
/* 2893 */                                 out.print(FormatUtil.getString("am.webclient.processtemplate.maketemplate"));
/* 2894 */                                 out.write("\">\n\t");
/* 2895 */                                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f12.doAfterBody();
/* 2896 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2900 */                             if (_jspx_th_c_005fotherwise_005f12.doEndTag() == 5) {
/* 2901 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12); return;
/*      */                             }
/*      */                             
/* 2904 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12);
/* 2905 */                             out.write(10);
/* 2906 */                             out.write(9);
/* 2907 */                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f12.doAfterBody();
/* 2908 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2912 */                         if (_jspx_th_c_005fchoose_005f12.doEndTag() == 5) {
/* 2913 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12); return;
/*      */                         }
/*      */                         
/* 2916 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12);
/* 2917 */                         out.write("\n\t<input name=\"save\" type=\"button\" class=\"buttons btn_link\" onclick=\"javascript:cancelTemplate();\" value=\"");
/* 2918 */                         out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.cancel"));
/* 2919 */                         out.write("\">\n</td>\n<td width=\"35%\"></td>\n</tr>\n</table>\n\n</div>\n\n</td>\n\n");
/* 2920 */                         int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2921 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2925 */                     if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2926 */                       this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */                     }
/*      */                     else {
/* 2929 */                       this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2930 */                       out.write(10);
/* 2931 */                       out.write(10);
/*      */                     }
/* 2933 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2934 */         out = _jspx_out;
/* 2935 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2936 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2937 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2940 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2946 */     PageContext pageContext = _jspx_page_context;
/* 2947 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2949 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2950 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2951 */     _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2952 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2953 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 2955 */         out.write(10);
/* 2956 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 2957 */           return true;
/* 2958 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 2959 */           return true;
/* 2960 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2961 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2965 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2966 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2967 */       return true;
/*      */     }
/* 2969 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2970 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2975 */     PageContext pageContext = _jspx_page_context;
/* 2976 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2978 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2979 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2980 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 2982 */     _jspx_th_c_005fwhen_005f0.setTest("${templatetype==PROCESSTEMPLATE}");
/* 2983 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2984 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 2986 */         out.write("\n        ");
/* 2987 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 2988 */           return true;
/* 2989 */         out.write(10);
/* 2990 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2991 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2995 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2996 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2997 */       return true;
/*      */     }
/* 2999 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3000 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3005 */     PageContext pageContext = _jspx_page_context;
/* 3006 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3008 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 3009 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3010 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 3012 */     _jspx_th_c_005fset_005f0.setVar("HelpKey");
/*      */     
/* 3014 */     _jspx_th_c_005fset_005f0.setScope("request");
/*      */     
/* 3016 */     _jspx_th_c_005fset_005f0.setValue("Process Templates");
/* 3017 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3018 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3019 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 3020 */       return true;
/*      */     }
/* 3022 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 3023 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3028 */     PageContext pageContext = _jspx_page_context;
/* 3029 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3031 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3032 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3033 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 3034 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3035 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 3037 */         out.write("\n        ");
/* 3038 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 3039 */           return true;
/* 3040 */         out.write(10);
/* 3041 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3042 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3046 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3047 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3048 */       return true;
/*      */     }
/* 3050 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3051 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3056 */     PageContext pageContext = _jspx_page_context;
/* 3057 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3059 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 3060 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 3061 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 3063 */     _jspx_th_c_005fset_005f1.setVar("HelpKey");
/*      */     
/* 3065 */     _jspx_th_c_005fset_005f1.setScope("request");
/*      */     
/* 3067 */     _jspx_th_c_005fset_005f1.setValue("Service Templates");
/* 3068 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 3069 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 3070 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 3071 */       return true;
/*      */     }
/* 3073 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 3074 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3079 */     PageContext pageContext = _jspx_page_context;
/* 3080 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3082 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3083 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3084 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 3086 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 3087 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3088 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 3090 */         out.write("\n\talertUser();\n\treturn false;\n");
/* 3091 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3092 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3096 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3097 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3098 */       return true;
/*      */     }
/* 3100 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3101 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3106 */     PageContext pageContext = _jspx_page_context;
/* 3107 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3109 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3110 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 3111 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/* 3112 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 3113 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 3114 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3115 */         out = _jspx_page_context.pushBody();
/* 3116 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 3117 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3120 */         out.write("am.webclient.processtemplate.choosemonitor");
/* 3121 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 3122 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3125 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3126 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3129 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 3130 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3131 */       return true;
/*      */     }
/* 3133 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3134 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3139 */     PageContext pageContext = _jspx_page_context;
/* 3140 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3142 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3143 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 3144 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/* 3145 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 3146 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 3147 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 3148 */         out = _jspx_page_context.pushBody();
/* 3149 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 3150 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3153 */         out.write("am.webclient.processtemplate.choosemonitor");
/* 3154 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 3155 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3158 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 3159 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3162 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 3163 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3164 */       return true;
/*      */     }
/* 3166 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3167 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3172 */     PageContext pageContext = _jspx_page_context;
/* 3173 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3175 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3176 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 3177 */     _jspx_th_fmt_005fmessage_005f2.setParent(null);
/* 3178 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 3179 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 3180 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 3181 */         out = _jspx_page_context.pushBody();
/* 3182 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 3183 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3186 */         out.write("am.webclient.template.mgcheckfailtxt");
/* 3187 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 3188 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3191 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 3192 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3195 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 3196 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3197 */       return true;
/*      */     }
/* 3199 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3200 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3205 */     PageContext pageContext = _jspx_page_context;
/* 3206 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3208 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3209 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 3210 */     _jspx_th_fmt_005fmessage_005f3.setParent(null);
/* 3211 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 3212 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 3213 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 3214 */         out = _jspx_page_context.pushBody();
/* 3215 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 3216 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3219 */         out.write("am.webclient.processtemplate.chooseservertype");
/* 3220 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 3221 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3224 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 3225 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3228 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 3229 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3230 */       return true;
/*      */     }
/* 3232 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3233 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3238 */     PageContext pageContext = _jspx_page_context;
/* 3239 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3241 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3242 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 3243 */     _jspx_th_fmt_005fmessage_005f4.setParent(null);
/* 3244 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 3245 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 3246 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3247 */         out = _jspx_page_context.pushBody();
/* 3248 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 3249 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3252 */         out.write("am.webclient.processtemplate.entername");
/* 3253 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 3254 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3257 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3258 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3261 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 3262 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3263 */       return true;
/*      */     }
/* 3265 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3266 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3271 */     PageContext pageContext = _jspx_page_context;
/* 3272 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3274 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 3275 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 3276 */     _jspx_th_fmt_005fmessage_005f5.setParent(null);
/*      */     
/* 3278 */     _jspx_th_fmt_005fmessage_005f5.setKey("am.webclient.processtemplate.addprocess");
/* 3279 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 3280 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 3281 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 3282 */         out = _jspx_page_context.pushBody();
/* 3283 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 3284 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3287 */         if (_jspx_meth_fmt_005fparam_005f0(_jspx_th_fmt_005fmessage_005f5, _jspx_page_context))
/* 3288 */           return true;
/* 3289 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 3290 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3293 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 3294 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3297 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 3298 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3299 */       return true;
/*      */     }
/* 3301 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3302 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f0(JspTag _jspx_th_fmt_005fmessage_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3307 */     PageContext pageContext = _jspx_page_context;
/* 3308 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3310 */     ParamTag _jspx_th_fmt_005fparam_005f0 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 3311 */     _jspx_th_fmt_005fparam_005f0.setPageContext(_jspx_page_context);
/* 3312 */     _jspx_th_fmt_005fparam_005f0.setParent((Tag)_jspx_th_fmt_005fmessage_005f5);
/* 3313 */     int _jspx_eval_fmt_005fparam_005f0 = _jspx_th_fmt_005fparam_005f0.doStartTag();
/* 3314 */     if (_jspx_eval_fmt_005fparam_005f0 != 0) {
/* 3315 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/* 3316 */         out = _jspx_page_context.pushBody();
/* 3317 */         _jspx_th_fmt_005fparam_005f0.setBodyContent((BodyContent)out);
/* 3318 */         _jspx_th_fmt_005fparam_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3321 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_fmt_005fparam_005f0, _jspx_page_context))
/* 3322 */           return true;
/* 3323 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f0.doAfterBody();
/* 3324 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3327 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/* 3328 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3331 */     if (_jspx_th_fmt_005fparam_005f0.doEndTag() == 5) {
/* 3332 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/* 3333 */       return true;
/*      */     }
/* 3335 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/* 3336 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_fmt_005fparam_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3341 */     PageContext pageContext = _jspx_page_context;
/* 3342 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3344 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3345 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3346 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_fmt_005fparam_005f0);
/*      */     
/* 3348 */     _jspx_th_c_005fout_005f0.setValue("${templatetypestr}");
/* 3349 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3350 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3351 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3352 */       return true;
/*      */     }
/* 3354 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3355 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3360 */     PageContext pageContext = _jspx_page_context;
/* 3361 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3363 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 3364 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 3365 */     _jspx_th_fmt_005fmessage_005f6.setParent(null);
/*      */     
/* 3367 */     _jspx_th_fmt_005fmessage_005f6.setKey("am.webclient.processtemplate.selectprocess");
/* 3368 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 3369 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 3370 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 3371 */         out = _jspx_page_context.pushBody();
/* 3372 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 3373 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3376 */         if (_jspx_meth_fmt_005fparam_005f1(_jspx_th_fmt_005fmessage_005f6, _jspx_page_context))
/* 3377 */           return true;
/* 3378 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 3379 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3382 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 3383 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3386 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 3387 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3388 */       return true;
/*      */     }
/* 3390 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3391 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f1(JspTag _jspx_th_fmt_005fmessage_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3396 */     PageContext pageContext = _jspx_page_context;
/* 3397 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3399 */     ParamTag _jspx_th_fmt_005fparam_005f1 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 3400 */     _jspx_th_fmt_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3401 */     _jspx_th_fmt_005fparam_005f1.setParent((Tag)_jspx_th_fmt_005fmessage_005f6);
/* 3402 */     int _jspx_eval_fmt_005fparam_005f1 = _jspx_th_fmt_005fparam_005f1.doStartTag();
/* 3403 */     if (_jspx_eval_fmt_005fparam_005f1 != 0) {
/* 3404 */       if (_jspx_eval_fmt_005fparam_005f1 != 1) {
/* 3405 */         out = _jspx_page_context.pushBody();
/* 3406 */         _jspx_th_fmt_005fparam_005f1.setBodyContent((BodyContent)out);
/* 3407 */         _jspx_th_fmt_005fparam_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3410 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_fmt_005fparam_005f1, _jspx_page_context))
/* 3411 */           return true;
/* 3412 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f1.doAfterBody();
/* 3413 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3416 */       if (_jspx_eval_fmt_005fparam_005f1 != 1) {
/* 3417 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3420 */     if (_jspx_th_fmt_005fparam_005f1.doEndTag() == 5) {
/* 3421 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f1);
/* 3422 */       return true;
/*      */     }
/* 3424 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f1);
/* 3425 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_fmt_005fparam_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3430 */     PageContext pageContext = _jspx_page_context;
/* 3431 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3433 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3434 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3435 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_fmt_005fparam_005f1);
/*      */     
/* 3437 */     _jspx_th_c_005fout_005f1.setValue("${templatetypestr}");
/* 3438 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3439 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3440 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3441 */       return true;
/*      */     }
/* 3443 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3444 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3449 */     PageContext pageContext = _jspx_page_context;
/* 3450 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3452 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3453 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3454 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 3456 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 3457 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3458 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 3460 */         out.write("\n\t alertUser();\n        return;\n        ");
/* 3461 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3462 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3466 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3467 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3468 */       return true;
/*      */     }
/* 3470 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3471 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3476 */     PageContext pageContext = _jspx_page_context;
/* 3477 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3479 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3480 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 3481 */     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */     
/* 3483 */     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 3484 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 3485 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */       for (;;) {
/* 3487 */         out.write("\n        if(!checkforOneSelected(document.forms.AMProcessTemplateForm,\"processcheckbox\"))\n        {\n                alert('");
/* 3488 */         if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 3489 */           return true;
/* 3490 */         out.write("');  ");
/* 3491 */         out.write("\n                return;\n        }\n        if(confirm('");
/* 3492 */         if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 3493 */           return true;
/* 3494 */         out.write("'))\n        {\n\t       var processtable = document.getElementById(\"processtableid\");\n               var prrowCount = processtable.rows.length;\n               for(var i=0; i<prrowCount; i++) {\n                    var row = processtable.rows[i];\n                    var chkbox = row.cells[0].childNodes[0];\n\t\t\tif(row.cells[0].childNodes.length > 1){\n\t\t\t\tchkbox=row.cells[0].childNodes[1];\n\t\t\t}\n                    if(chkbox && true == chkbox.checked) {\n                         processtable.deleteRow(i);\n\t\t\t \t\t\t removeFormElements(chkbox.value);\n                         prrowCount--;\n                         i--;\n                    }\n\t\t}\n\t\tif(processtable.rows.length <=2 ){\n\t\t\thideDiv(\"showrow\");\n\t\t\thideDiv(\"adddeleteprocess\");\n\t\t\tshowDiv(\"noprocessdiv\");\n\t\t}\n\t}\n        ");
/* 3495 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 3496 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3500 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 3501 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 3502 */       return true;
/*      */     }
/* 3504 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 3505 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3510 */     PageContext pageContext = _jspx_page_context;
/* 3511 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3513 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 3514 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 3515 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/*      */     
/* 3517 */     _jspx_th_fmt_005fmessage_005f7.setKey("am.webclient.processtemplate.selectdeletetprocess.msg");
/* 3518 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 3519 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 3520 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 3521 */         out = _jspx_page_context.pushBody();
/* 3522 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 3523 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3526 */         out.write(32);
/* 3527 */         if (_jspx_meth_fmt_005fparam_005f2(_jspx_th_fmt_005fmessage_005f7, _jspx_page_context))
/* 3528 */           return true;
/* 3529 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 3530 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3533 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 3534 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3537 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 3538 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3539 */       return true;
/*      */     }
/* 3541 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3542 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f2(JspTag _jspx_th_fmt_005fmessage_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3547 */     PageContext pageContext = _jspx_page_context;
/* 3548 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3550 */     ParamTag _jspx_th_fmt_005fparam_005f2 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 3551 */     _jspx_th_fmt_005fparam_005f2.setPageContext(_jspx_page_context);
/* 3552 */     _jspx_th_fmt_005fparam_005f2.setParent((Tag)_jspx_th_fmt_005fmessage_005f7);
/* 3553 */     int _jspx_eval_fmt_005fparam_005f2 = _jspx_th_fmt_005fparam_005f2.doStartTag();
/* 3554 */     if (_jspx_eval_fmt_005fparam_005f2 != 0) {
/* 3555 */       if (_jspx_eval_fmt_005fparam_005f2 != 1) {
/* 3556 */         out = _jspx_page_context.pushBody();
/* 3557 */         _jspx_th_fmt_005fparam_005f2.setBodyContent((BodyContent)out);
/* 3558 */         _jspx_th_fmt_005fparam_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3561 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_fmt_005fparam_005f2, _jspx_page_context))
/* 3562 */           return true;
/* 3563 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f2.doAfterBody();
/* 3564 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3567 */       if (_jspx_eval_fmt_005fparam_005f2 != 1) {
/* 3568 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3571 */     if (_jspx_th_fmt_005fparam_005f2.doEndTag() == 5) {
/* 3572 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f2);
/* 3573 */       return true;
/*      */     }
/* 3575 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f2);
/* 3576 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_fmt_005fparam_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3581 */     PageContext pageContext = _jspx_page_context;
/* 3582 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3584 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3585 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3586 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_fmt_005fparam_005f2);
/*      */     
/* 3588 */     _jspx_th_c_005fout_005f2.setValue("${templatetypestr}");
/* 3589 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3590 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3591 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3592 */       return true;
/*      */     }
/* 3594 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3595 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3600 */     PageContext pageContext = _jspx_page_context;
/* 3601 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3603 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 3604 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 3605 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/*      */     
/* 3607 */     _jspx_th_fmt_005fmessage_005f8.setKey("am.webclient.processtemplate.deletetprocessconfirmmsg");
/* 3608 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 3609 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 3610 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 3611 */         out = _jspx_page_context.pushBody();
/* 3612 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 3613 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3616 */         if (_jspx_meth_fmt_005fparam_005f3(_jspx_th_fmt_005fmessage_005f8, _jspx_page_context))
/* 3617 */           return true;
/* 3618 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 3619 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3622 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 3623 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3626 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 3627 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3628 */       return true;
/*      */     }
/* 3630 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3631 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f3(JspTag _jspx_th_fmt_005fmessage_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3636 */     PageContext pageContext = _jspx_page_context;
/* 3637 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3639 */     ParamTag _jspx_th_fmt_005fparam_005f3 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 3640 */     _jspx_th_fmt_005fparam_005f3.setPageContext(_jspx_page_context);
/* 3641 */     _jspx_th_fmt_005fparam_005f3.setParent((Tag)_jspx_th_fmt_005fmessage_005f8);
/* 3642 */     int _jspx_eval_fmt_005fparam_005f3 = _jspx_th_fmt_005fparam_005f3.doStartTag();
/* 3643 */     if (_jspx_eval_fmt_005fparam_005f3 != 0) {
/* 3644 */       if (_jspx_eval_fmt_005fparam_005f3 != 1) {
/* 3645 */         out = _jspx_page_context.pushBody();
/* 3646 */         _jspx_th_fmt_005fparam_005f3.setBodyContent((BodyContent)out);
/* 3647 */         _jspx_th_fmt_005fparam_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3650 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_fmt_005fparam_005f3, _jspx_page_context))
/* 3651 */           return true;
/* 3652 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f3.doAfterBody();
/* 3653 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3656 */       if (_jspx_eval_fmt_005fparam_005f3 != 1) {
/* 3657 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3660 */     if (_jspx_th_fmt_005fparam_005f3.doEndTag() == 5) {
/* 3661 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f3);
/* 3662 */       return true;
/*      */     }
/* 3664 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f3);
/* 3665 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_fmt_005fparam_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3670 */     PageContext pageContext = _jspx_page_context;
/* 3671 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3673 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3674 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3675 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_fmt_005fparam_005f3);
/*      */     
/* 3677 */     _jspx_th_c_005fout_005f3.setValue("${templatetypestr}");
/* 3678 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3679 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3680 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3681 */       return true;
/*      */     }
/* 3683 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3684 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3689 */     PageContext pageContext = _jspx_page_context;
/* 3690 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3692 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3693 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3694 */     _jspx_th_c_005fout_005f4.setParent(null);
/*      */     
/* 3696 */     _jspx_th_c_005fout_005f4.setValue("${templatetype}");
/* 3697 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3698 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3699 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3700 */       return true;
/*      */     }
/* 3702 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3703 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3708 */     PageContext pageContext = _jspx_page_context;
/* 3709 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3711 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3712 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3713 */     _jspx_th_c_005fout_005f5.setParent(null);
/*      */     
/* 3715 */     _jspx_th_c_005fout_005f5.setValue("${templatetype}");
/* 3716 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3717 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3718 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3719 */       return true;
/*      */     }
/* 3721 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3722 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3727 */     PageContext pageContext = _jspx_page_context;
/* 3728 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3730 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3731 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3732 */     _jspx_th_c_005fout_005f6.setParent(null);
/*      */     
/* 3734 */     _jspx_th_c_005fout_005f6.setValue("${templatetypestr}");
/* 3735 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3736 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3737 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3738 */       return true;
/*      */     }
/* 3740 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3741 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3746 */     PageContext pageContext = _jspx_page_context;
/* 3747 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3749 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3750 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 3751 */     _jspx_th_c_005fout_005f7.setParent(null);
/*      */     
/* 3753 */     _jspx_th_c_005fout_005f7.setValue("${templatetype}");
/* 3754 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 3755 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 3756 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3757 */       return true;
/*      */     }
/* 3759 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3760 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3765 */     PageContext pageContext = _jspx_page_context;
/* 3766 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3768 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3769 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 3770 */     _jspx_th_c_005fout_005f8.setParent(null);
/*      */     
/* 3772 */     _jspx_th_c_005fout_005f8.setValue("${templatetypestr}");
/* 3773 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 3774 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 3775 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3776 */       return true;
/*      */     }
/* 3778 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3779 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3784 */     PageContext pageContext = _jspx_page_context;
/* 3785 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3787 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3788 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3789 */     _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */     
/* 3791 */     _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 3792 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3793 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 3795 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 3796 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3797 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3801 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3802 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3803 */       return true;
/*      */     }
/* 3805 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3806 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3811 */     PageContext pageContext = _jspx_page_context;
/* 3812 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3814 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3815 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 3816 */     _jspx_th_c_005fout_005f9.setParent(null);
/*      */     
/* 3818 */     _jspx_th_c_005fout_005f9.setValue("${templatetype}");
/* 3819 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 3820 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 3821 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3822 */       return true;
/*      */     }
/* 3824 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3825 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3830 */     PageContext pageContext = _jspx_page_context;
/* 3831 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3833 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3834 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 3835 */     _jspx_th_c_005fout_005f10.setParent(null);
/*      */     
/* 3837 */     _jspx_th_c_005fout_005f10.setValue("${templatetypestr}");
/* 3838 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 3839 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 3840 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3841 */       return true;
/*      */     }
/* 3843 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3844 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3849 */     PageContext pageContext = _jspx_page_context;
/* 3850 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3852 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3853 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 3854 */     _jspx_th_c_005fout_005f11.setParent(null);
/*      */     
/* 3856 */     _jspx_th_c_005fout_005f11.setValue("${templatetype}");
/* 3857 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 3858 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 3859 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3860 */       return true;
/*      */     }
/* 3862 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3863 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3868 */     PageContext pageContext = _jspx_page_context;
/* 3869 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3871 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3872 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 3873 */     _jspx_th_c_005fout_005f12.setParent(null);
/*      */     
/* 3875 */     _jspx_th_c_005fout_005f12.setValue("${templatetypestr}");
/* 3876 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 3877 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 3878 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 3879 */       return true;
/*      */     }
/* 3881 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 3882 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3887 */     PageContext pageContext = _jspx_page_context;
/* 3888 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3890 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3891 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 3892 */     _jspx_th_c_005fchoose_005f1.setParent(null);
/* 3893 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 3894 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 3896 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 3897 */           return true;
/* 3898 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 3899 */           return true;
/* 3900 */         out.write(10);
/* 3901 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 3902 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3906 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 3907 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 3908 */       return true;
/*      */     }
/* 3910 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 3911 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3916 */     PageContext pageContext = _jspx_page_context;
/* 3917 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3919 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3920 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 3921 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 3923 */     _jspx_th_c_005fwhen_005f1.setTest("${templatetype==PROCESSTEMPLATE}");
/* 3924 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 3925 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 3927 */         out.write(10);
/* 3928 */         if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 3929 */           return true;
/* 3930 */         out.write(10);
/* 3931 */         if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 3932 */           return true;
/* 3933 */         out.write(10);
/* 3934 */         if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 3935 */           return true;
/* 3936 */         out.write(10);
/* 3937 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 3938 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3942 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 3943 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 3944 */       return true;
/*      */     }
/* 3946 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 3947 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3952 */     PageContext pageContext = _jspx_page_context;
/* 3953 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3955 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody.get(MessageTag.class);
/* 3956 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 3957 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 3959 */     _jspx_th_fmt_005fmessage_005f9.setKey("am.webclient.processtemplate.upcaseprocesses");
/*      */     
/* 3961 */     _jspx_th_fmt_005fmessage_005f9.setVar("upcasepsmsg");
/* 3962 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 3963 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 3964 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3965 */       return true;
/*      */     }
/* 3967 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3968 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3973 */     PageContext pageContext = _jspx_page_context;
/* 3974 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3976 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody.get(MessageTag.class);
/* 3977 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 3978 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 3980 */     _jspx_th_fmt_005fmessage_005f10.setKey("am.webclient.processtemplate.lowercaseprocesses");
/*      */     
/* 3982 */     _jspx_th_fmt_005fmessage_005f10.setVar("lowercasemultipsmsg");
/* 3983 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 3984 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 3985 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 3986 */       return true;
/*      */     }
/* 3988 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 3989 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3994 */     PageContext pageContext = _jspx_page_context;
/* 3995 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3997 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody.get(MessageTag.class);
/* 3998 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 3999 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 4001 */     _jspx_th_fmt_005fmessage_005f11.setKey("am.webclient.processtemplate.lowercaseprocess");
/*      */     
/* 4003 */     _jspx_th_fmt_005fmessage_005f11.setVar("lowercasepsmsg");
/* 4004 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 4005 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 4006 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 4007 */       return true;
/*      */     }
/* 4009 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 4010 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4015 */     PageContext pageContext = _jspx_page_context;
/* 4016 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4018 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4019 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 4020 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 4021 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 4022 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 4024 */         out.write(10);
/* 4025 */         if (_jspx_meth_fmt_005fmessage_005f12(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/* 4026 */           return true;
/* 4027 */         out.write(10);
/* 4028 */         if (_jspx_meth_fmt_005fmessage_005f13(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/* 4029 */           return true;
/* 4030 */         out.write(10);
/* 4031 */         if (_jspx_meth_fmt_005fmessage_005f14(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/* 4032 */           return true;
/* 4033 */         out.write(10);
/* 4034 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 4035 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4039 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 4040 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 4041 */       return true;
/*      */     }
/* 4043 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 4044 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4049 */     PageContext pageContext = _jspx_page_context;
/* 4050 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4052 */     MessageTag _jspx_th_fmt_005fmessage_005f12 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody.get(MessageTag.class);
/* 4053 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 4054 */     _jspx_th_fmt_005fmessage_005f12.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 4056 */     _jspx_th_fmt_005fmessage_005f12.setKey("am.webclient.servicetemplate.upcaseservices");
/*      */     
/* 4058 */     _jspx_th_fmt_005fmessage_005f12.setVar("upcasepsmsg");
/* 4059 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 4060 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 4061 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 4062 */       return true;
/*      */     }
/* 4064 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 4065 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f13(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4070 */     PageContext pageContext = _jspx_page_context;
/* 4071 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4073 */     MessageTag _jspx_th_fmt_005fmessage_005f13 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody.get(MessageTag.class);
/* 4074 */     _jspx_th_fmt_005fmessage_005f13.setPageContext(_jspx_page_context);
/* 4075 */     _jspx_th_fmt_005fmessage_005f13.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 4077 */     _jspx_th_fmt_005fmessage_005f13.setKey("am.webclient.servicetemplate.lowercaseservices");
/*      */     
/* 4079 */     _jspx_th_fmt_005fmessage_005f13.setVar("lowercasemultipsmsg");
/* 4080 */     int _jspx_eval_fmt_005fmessage_005f13 = _jspx_th_fmt_005fmessage_005f13.doStartTag();
/* 4081 */     if (_jspx_th_fmt_005fmessage_005f13.doEndTag() == 5) {
/* 4082 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 4083 */       return true;
/*      */     }
/* 4085 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 4086 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f14(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4091 */     PageContext pageContext = _jspx_page_context;
/* 4092 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4094 */     MessageTag _jspx_th_fmt_005fmessage_005f14 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody.get(MessageTag.class);
/* 4095 */     _jspx_th_fmt_005fmessage_005f14.setPageContext(_jspx_page_context);
/* 4096 */     _jspx_th_fmt_005fmessage_005f14.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 4098 */     _jspx_th_fmt_005fmessage_005f14.setKey("am.webclient.servicetemplate.lowercaseservice");
/*      */     
/* 4100 */     _jspx_th_fmt_005fmessage_005f14.setVar("lowercasepsmsg");
/* 4101 */     int _jspx_eval_fmt_005fmessage_005f14 = _jspx_th_fmt_005fmessage_005f14.doStartTag();
/* 4102 */     if (_jspx_th_fmt_005fmessage_005f14.doEndTag() == 5) {
/* 4103 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 4104 */       return true;
/*      */     }
/* 4106 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 4107 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4112 */     PageContext pageContext = _jspx_page_context;
/* 4113 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4115 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4116 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 4117 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4119 */     _jspx_th_c_005fout_005f13.setValue("${templatetype}");
/* 4120 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 4121 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 4122 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4123 */       return true;
/*      */     }
/* 4125 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4126 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4131 */     PageContext pageContext = _jspx_page_context;
/* 4132 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4134 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4135 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 4136 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4138 */     _jspx_th_c_005fout_005f14.setValue("${selectedservertype}");
/* 4139 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 4140 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 4141 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4142 */       return true;
/*      */     }
/* 4144 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4145 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4150 */     PageContext pageContext = _jspx_page_context;
/* 4151 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4153 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4154 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 4155 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 4157 */     _jspx_th_c_005fout_005f15.setValue("${param.templateid}");
/* 4158 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 4159 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 4160 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4161 */       return true;
/*      */     }
/* 4163 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4164 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4169 */     PageContext pageContext = _jspx_page_context;
/* 4170 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4172 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4173 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 4174 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 4176 */     _jspx_th_c_005fout_005f16.setValue("${templatetype}");
/* 4177 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 4178 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 4179 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4180 */       return true;
/*      */     }
/* 4182 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4183 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f3(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4188 */     PageContext pageContext = _jspx_page_context;
/* 4189 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4191 */     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4192 */     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 4193 */     _jspx_th_c_005fchoose_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/* 4194 */     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 4195 */     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */       for (;;) {
/* 4197 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 4198 */           return true;
/* 4199 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 4200 */           return true;
/* 4201 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 4202 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4206 */     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 4207 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 4208 */       return true;
/*      */     }
/* 4210 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 4211 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4216 */     PageContext pageContext = _jspx_page_context;
/* 4217 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4219 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4220 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 4221 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 4223 */     _jspx_th_c_005fwhen_005f3.setTest("${templatetype==PROCESSTEMPLATE}");
/* 4224 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 4225 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 4227 */         out.write("\n                ");
/* 4228 */         if (_jspx_meth_fmt_005fmessage_005f15(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/* 4229 */           return true;
/* 4230 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 4231 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4235 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 4236 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 4237 */       return true;
/*      */     }
/* 4239 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 4240 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f15(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4245 */     PageContext pageContext = _jspx_page_context;
/* 4246 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4248 */     MessageTag _jspx_th_fmt_005fmessage_005f15 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 4249 */     _jspx_th_fmt_005fmessage_005f15.setPageContext(_jspx_page_context);
/* 4250 */     _jspx_th_fmt_005fmessage_005f15.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 4252 */     _jspx_th_fmt_005fmessage_005f15.setKey("am.webclient.admin.showtemplate.link");
/* 4253 */     int _jspx_eval_fmt_005fmessage_005f15 = _jspx_th_fmt_005fmessage_005f15.doStartTag();
/* 4254 */     if (_jspx_eval_fmt_005fmessage_005f15 != 0) {
/* 4255 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 4256 */         out = _jspx_page_context.pushBody();
/* 4257 */         _jspx_th_fmt_005fmessage_005f15.setBodyContent((BodyContent)out);
/* 4258 */         _jspx_th_fmt_005fmessage_005f15.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4261 */         out.write(32);
/* 4262 */         if (_jspx_meth_fmt_005fparam_005f4(_jspx_th_fmt_005fmessage_005f15, _jspx_page_context))
/* 4263 */           return true;
/* 4264 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f15.doAfterBody();
/* 4265 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4268 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 4269 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4272 */     if (_jspx_th_fmt_005fmessage_005f15.doEndTag() == 5) {
/* 4273 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 4274 */       return true;
/*      */     }
/* 4276 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 4277 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f4(JspTag _jspx_th_fmt_005fmessage_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4282 */     PageContext pageContext = _jspx_page_context;
/* 4283 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4285 */     ParamTag _jspx_th_fmt_005fparam_005f4 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 4286 */     _jspx_th_fmt_005fparam_005f4.setPageContext(_jspx_page_context);
/* 4287 */     _jspx_th_fmt_005fparam_005f4.setParent((Tag)_jspx_th_fmt_005fmessage_005f15);
/* 4288 */     int _jspx_eval_fmt_005fparam_005f4 = _jspx_th_fmt_005fparam_005f4.doStartTag();
/* 4289 */     if (_jspx_eval_fmt_005fparam_005f4 != 0) {
/* 4290 */       if (_jspx_eval_fmt_005fparam_005f4 != 1) {
/* 4291 */         out = _jspx_page_context.pushBody();
/* 4292 */         _jspx_th_fmt_005fparam_005f4.setBodyContent((BodyContent)out);
/* 4293 */         _jspx_th_fmt_005fparam_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4296 */         if (_jspx_meth_c_005fout_005f17(_jspx_th_fmt_005fparam_005f4, _jspx_page_context))
/* 4297 */           return true;
/* 4298 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f4.doAfterBody();
/* 4299 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4302 */       if (_jspx_eval_fmt_005fparam_005f4 != 1) {
/* 4303 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4306 */     if (_jspx_th_fmt_005fparam_005f4.doEndTag() == 5) {
/* 4307 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f4);
/* 4308 */       return true;
/*      */     }
/* 4310 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f4);
/* 4311 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_fmt_005fparam_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4316 */     PageContext pageContext = _jspx_page_context;
/* 4317 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4319 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4320 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 4321 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_fmt_005fparam_005f4);
/*      */     
/* 4323 */     _jspx_th_c_005fout_005f17.setValue("${templatetypestr}");
/* 4324 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 4325 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 4326 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4327 */       return true;
/*      */     }
/* 4329 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4330 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4335 */     PageContext pageContext = _jspx_page_context;
/* 4336 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4338 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4339 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 4340 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 4341 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 4342 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 4344 */         if (_jspx_meth_fmt_005fmessage_005f16(_jspx_th_c_005fotherwise_005f2, _jspx_page_context))
/* 4345 */           return true;
/* 4346 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 4347 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4351 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 4352 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 4353 */       return true;
/*      */     }
/* 4355 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 4356 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f16(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4361 */     PageContext pageContext = _jspx_page_context;
/* 4362 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4364 */     MessageTag _jspx_th_fmt_005fmessage_005f16 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4365 */     _jspx_th_fmt_005fmessage_005f16.setPageContext(_jspx_page_context);
/* 4366 */     _jspx_th_fmt_005fmessage_005f16.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 4368 */     _jspx_th_fmt_005fmessage_005f16.setKey("am.webclient.admin.showservicetemplate.link");
/* 4369 */     int _jspx_eval_fmt_005fmessage_005f16 = _jspx_th_fmt_005fmessage_005f16.doStartTag();
/* 4370 */     if (_jspx_th_fmt_005fmessage_005f16.doEndTag() == 5) {
/* 4371 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 4372 */       return true;
/*      */     }
/* 4374 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 4375 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f17(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4380 */     PageContext pageContext = _jspx_page_context;
/* 4381 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4383 */     MessageTag _jspx_th_fmt_005fmessage_005f17 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4384 */     _jspx_th_fmt_005fmessage_005f17.setPageContext(_jspx_page_context);
/* 4385 */     _jspx_th_fmt_005fmessage_005f17.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 4387 */     _jspx_th_fmt_005fmessage_005f17.setKey("am.webclient.processtemplate.edit");
/* 4388 */     int _jspx_eval_fmt_005fmessage_005f17 = _jspx_th_fmt_005fmessage_005f17.doStartTag();
/* 4389 */     if (_jspx_th_fmt_005fmessage_005f17.doEndTag() == 5) {
/* 4390 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 4391 */       return true;
/*      */     }
/* 4393 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 4394 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4399 */     PageContext pageContext = _jspx_page_context;
/* 4400 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4402 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4403 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 4404 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 4406 */     _jspx_th_c_005fout_005f18.setValue("${templatetype}");
/* 4407 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 4408 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 4409 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4410 */       return true;
/*      */     }
/* 4412 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4413 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f4(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4418 */     PageContext pageContext = _jspx_page_context;
/* 4419 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4421 */     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4422 */     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 4423 */     _jspx_th_c_005fchoose_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/* 4424 */     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 4425 */     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */       for (;;) {
/* 4427 */         if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 4428 */           return true;
/* 4429 */         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 4430 */           return true;
/* 4431 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 4432 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4436 */     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 4437 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 4438 */       return true;
/*      */     }
/* 4440 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 4441 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f4(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4446 */     PageContext pageContext = _jspx_page_context;
/* 4447 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4449 */     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4450 */     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 4451 */     _jspx_th_c_005fwhen_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 4453 */     _jspx_th_c_005fwhen_005f4.setTest("${templatetype==PROCESSTEMPLATE}");
/* 4454 */     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 4455 */     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */       for (;;) {
/* 4457 */         out.write("\n                ");
/* 4458 */         if (_jspx_meth_fmt_005fmessage_005f18(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/* 4459 */           return true;
/* 4460 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 4461 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4465 */     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 4466 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 4467 */       return true;
/*      */     }
/* 4469 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 4470 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f18(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4475 */     PageContext pageContext = _jspx_page_context;
/* 4476 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4478 */     MessageTag _jspx_th_fmt_005fmessage_005f18 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 4479 */     _jspx_th_fmt_005fmessage_005f18.setPageContext(_jspx_page_context);
/* 4480 */     _jspx_th_fmt_005fmessage_005f18.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 4482 */     _jspx_th_fmt_005fmessage_005f18.setKey("am.webclient.admin.showtemplate.link");
/* 4483 */     int _jspx_eval_fmt_005fmessage_005f18 = _jspx_th_fmt_005fmessage_005f18.doStartTag();
/* 4484 */     if (_jspx_eval_fmt_005fmessage_005f18 != 0) {
/* 4485 */       if (_jspx_eval_fmt_005fmessage_005f18 != 1) {
/* 4486 */         out = _jspx_page_context.pushBody();
/* 4487 */         _jspx_th_fmt_005fmessage_005f18.setBodyContent((BodyContent)out);
/* 4488 */         _jspx_th_fmt_005fmessage_005f18.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4491 */         out.write(32);
/* 4492 */         if (_jspx_meth_fmt_005fparam_005f5(_jspx_th_fmt_005fmessage_005f18, _jspx_page_context))
/* 4493 */           return true;
/* 4494 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f18.doAfterBody();
/* 4495 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4498 */       if (_jspx_eval_fmt_005fmessage_005f18 != 1) {
/* 4499 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4502 */     if (_jspx_th_fmt_005fmessage_005f18.doEndTag() == 5) {
/* 4503 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f18);
/* 4504 */       return true;
/*      */     }
/* 4506 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f18);
/* 4507 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f5(JspTag _jspx_th_fmt_005fmessage_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4512 */     PageContext pageContext = _jspx_page_context;
/* 4513 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4515 */     ParamTag _jspx_th_fmt_005fparam_005f5 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 4516 */     _jspx_th_fmt_005fparam_005f5.setPageContext(_jspx_page_context);
/* 4517 */     _jspx_th_fmt_005fparam_005f5.setParent((Tag)_jspx_th_fmt_005fmessage_005f18);
/* 4518 */     int _jspx_eval_fmt_005fparam_005f5 = _jspx_th_fmt_005fparam_005f5.doStartTag();
/* 4519 */     if (_jspx_eval_fmt_005fparam_005f5 != 0) {
/* 4520 */       if (_jspx_eval_fmt_005fparam_005f5 != 1) {
/* 4521 */         out = _jspx_page_context.pushBody();
/* 4522 */         _jspx_th_fmt_005fparam_005f5.setBodyContent((BodyContent)out);
/* 4523 */         _jspx_th_fmt_005fparam_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4526 */         if (_jspx_meth_c_005fout_005f19(_jspx_th_fmt_005fparam_005f5, _jspx_page_context))
/* 4527 */           return true;
/* 4528 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f5.doAfterBody();
/* 4529 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4532 */       if (_jspx_eval_fmt_005fparam_005f5 != 1) {
/* 4533 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4536 */     if (_jspx_th_fmt_005fparam_005f5.doEndTag() == 5) {
/* 4537 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f5);
/* 4538 */       return true;
/*      */     }
/* 4540 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f5);
/* 4541 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_fmt_005fparam_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4546 */     PageContext pageContext = _jspx_page_context;
/* 4547 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4549 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4550 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 4551 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_fmt_005fparam_005f5);
/*      */     
/* 4553 */     _jspx_th_c_005fout_005f19.setValue("${templatetypestr}");
/* 4554 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 4555 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 4556 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 4557 */       return true;
/*      */     }
/* 4559 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 4560 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4565 */     PageContext pageContext = _jspx_page_context;
/* 4566 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4568 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4569 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 4570 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/* 4571 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 4572 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 4574 */         if (_jspx_meth_fmt_005fmessage_005f19(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/* 4575 */           return true;
/* 4576 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 4577 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4581 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 4582 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 4583 */       return true;
/*      */     }
/* 4585 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 4586 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f19(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4591 */     PageContext pageContext = _jspx_page_context;
/* 4592 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4594 */     MessageTag _jspx_th_fmt_005fmessage_005f19 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4595 */     _jspx_th_fmt_005fmessage_005f19.setPageContext(_jspx_page_context);
/* 4596 */     _jspx_th_fmt_005fmessage_005f19.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 4598 */     _jspx_th_fmt_005fmessage_005f19.setKey("am.webclient.admin.showservicetemplate.link");
/* 4599 */     int _jspx_eval_fmt_005fmessage_005f19 = _jspx_th_fmt_005fmessage_005f19.doStartTag();
/* 4600 */     if (_jspx_th_fmt_005fmessage_005f19.doEndTag() == 5) {
/* 4601 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f19);
/* 4602 */       return true;
/*      */     }
/* 4604 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f19);
/* 4605 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f20(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4610 */     PageContext pageContext = _jspx_page_context;
/* 4611 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4613 */     MessageTag _jspx_th_fmt_005fmessage_005f20 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4614 */     _jspx_th_fmt_005fmessage_005f20.setPageContext(_jspx_page_context);
/* 4615 */     _jspx_th_fmt_005fmessage_005f20.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 4617 */     _jspx_th_fmt_005fmessage_005f20.setKey("am.webclient.processtemplate.edit");
/* 4618 */     int _jspx_eval_fmt_005fmessage_005f20 = _jspx_th_fmt_005fmessage_005f20.doStartTag();
/* 4619 */     if (_jspx_th_fmt_005fmessage_005f20.doEndTag() == 5) {
/* 4620 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f20);
/* 4621 */       return true;
/*      */     }
/* 4623 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f20);
/* 4624 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4629 */     PageContext pageContext = _jspx_page_context;
/* 4630 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4632 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4633 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 4634 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/*      */     
/* 4636 */     _jspx_th_c_005fout_005f20.setValue("${templatetype}");
/* 4637 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 4638 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 4639 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 4640 */       return true;
/*      */     }
/* 4642 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 4643 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f5(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4648 */     PageContext pageContext = _jspx_page_context;
/* 4649 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4651 */     ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4652 */     _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 4653 */     _jspx_th_c_005fchoose_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/* 4654 */     int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 4655 */     if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */       for (;;) {
/* 4657 */         if (_jspx_meth_c_005fwhen_005f5(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 4658 */           return true;
/* 4659 */         if (_jspx_meth_c_005fotherwise_005f5(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 4660 */           return true;
/* 4661 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 4662 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4666 */     if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 4667 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 4668 */       return true;
/*      */     }
/* 4670 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 4671 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f5(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4676 */     PageContext pageContext = _jspx_page_context;
/* 4677 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4679 */     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4680 */     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 4681 */     _jspx_th_c_005fwhen_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/*      */     
/* 4683 */     _jspx_th_c_005fwhen_005f5.setTest("${templatetype==PROCESSTEMPLATE}");
/* 4684 */     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 4685 */     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */       for (;;) {
/* 4687 */         out.write("\n                ");
/* 4688 */         if (_jspx_meth_fmt_005fmessage_005f21(_jspx_th_c_005fwhen_005f5, _jspx_page_context))
/* 4689 */           return true;
/* 4690 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 4691 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4695 */     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 4696 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 4697 */       return true;
/*      */     }
/* 4699 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 4700 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f21(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4705 */     PageContext pageContext = _jspx_page_context;
/* 4706 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4708 */     MessageTag _jspx_th_fmt_005fmessage_005f21 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 4709 */     _jspx_th_fmt_005fmessage_005f21.setPageContext(_jspx_page_context);
/* 4710 */     _jspx_th_fmt_005fmessage_005f21.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 4712 */     _jspx_th_fmt_005fmessage_005f21.setKey("am.webclient.admin.showtemplate.link");
/* 4713 */     int _jspx_eval_fmt_005fmessage_005f21 = _jspx_th_fmt_005fmessage_005f21.doStartTag();
/* 4714 */     if (_jspx_eval_fmt_005fmessage_005f21 != 0) {
/* 4715 */       if (_jspx_eval_fmt_005fmessage_005f21 != 1) {
/* 4716 */         out = _jspx_page_context.pushBody();
/* 4717 */         _jspx_th_fmt_005fmessage_005f21.setBodyContent((BodyContent)out);
/* 4718 */         _jspx_th_fmt_005fmessage_005f21.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4721 */         out.write(32);
/* 4722 */         if (_jspx_meth_fmt_005fparam_005f6(_jspx_th_fmt_005fmessage_005f21, _jspx_page_context))
/* 4723 */           return true;
/* 4724 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f21.doAfterBody();
/* 4725 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4728 */       if (_jspx_eval_fmt_005fmessage_005f21 != 1) {
/* 4729 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4732 */     if (_jspx_th_fmt_005fmessage_005f21.doEndTag() == 5) {
/* 4733 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f21);
/* 4734 */       return true;
/*      */     }
/* 4736 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f21);
/* 4737 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f6(JspTag _jspx_th_fmt_005fmessage_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4742 */     PageContext pageContext = _jspx_page_context;
/* 4743 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4745 */     ParamTag _jspx_th_fmt_005fparam_005f6 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 4746 */     _jspx_th_fmt_005fparam_005f6.setPageContext(_jspx_page_context);
/* 4747 */     _jspx_th_fmt_005fparam_005f6.setParent((Tag)_jspx_th_fmt_005fmessage_005f21);
/* 4748 */     int _jspx_eval_fmt_005fparam_005f6 = _jspx_th_fmt_005fparam_005f6.doStartTag();
/* 4749 */     if (_jspx_eval_fmt_005fparam_005f6 != 0) {
/* 4750 */       if (_jspx_eval_fmt_005fparam_005f6 != 1) {
/* 4751 */         out = _jspx_page_context.pushBody();
/* 4752 */         _jspx_th_fmt_005fparam_005f6.setBodyContent((BodyContent)out);
/* 4753 */         _jspx_th_fmt_005fparam_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4756 */         if (_jspx_meth_c_005fout_005f21(_jspx_th_fmt_005fparam_005f6, _jspx_page_context))
/* 4757 */           return true;
/* 4758 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f6.doAfterBody();
/* 4759 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4762 */       if (_jspx_eval_fmt_005fparam_005f6 != 1) {
/* 4763 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4766 */     if (_jspx_th_fmt_005fparam_005f6.doEndTag() == 5) {
/* 4767 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f6);
/* 4768 */       return true;
/*      */     }
/* 4770 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f6);
/* 4771 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_fmt_005fparam_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4776 */     PageContext pageContext = _jspx_page_context;
/* 4777 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4779 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4780 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 4781 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_fmt_005fparam_005f6);
/*      */     
/* 4783 */     _jspx_th_c_005fout_005f21.setValue("${templatetypestr}");
/* 4784 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 4785 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 4786 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 4787 */       return true;
/*      */     }
/* 4789 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 4790 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f5(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4795 */     PageContext pageContext = _jspx_page_context;
/* 4796 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4798 */     OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4799 */     _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 4800 */     _jspx_th_c_005fotherwise_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/* 4801 */     int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 4802 */     if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */       for (;;) {
/* 4804 */         if (_jspx_meth_fmt_005fmessage_005f22(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/* 4805 */           return true;
/* 4806 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 4807 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4811 */     if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 4812 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 4813 */       return true;
/*      */     }
/* 4815 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 4816 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f22(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4821 */     PageContext pageContext = _jspx_page_context;
/* 4822 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4824 */     MessageTag _jspx_th_fmt_005fmessage_005f22 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4825 */     _jspx_th_fmt_005fmessage_005f22.setPageContext(_jspx_page_context);
/* 4826 */     _jspx_th_fmt_005fmessage_005f22.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 4828 */     _jspx_th_fmt_005fmessage_005f22.setKey("am.webclient.admin.showservicetemplate.link");
/* 4829 */     int _jspx_eval_fmt_005fmessage_005f22 = _jspx_th_fmt_005fmessage_005f22.doStartTag();
/* 4830 */     if (_jspx_th_fmt_005fmessage_005f22.doEndTag() == 5) {
/* 4831 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f22);
/* 4832 */       return true;
/*      */     }
/* 4834 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f22);
/* 4835 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f23(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4840 */     PageContext pageContext = _jspx_page_context;
/* 4841 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4843 */     MessageTag _jspx_th_fmt_005fmessage_005f23 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4844 */     _jspx_th_fmt_005fmessage_005f23.setPageContext(_jspx_page_context);
/* 4845 */     _jspx_th_fmt_005fmessage_005f23.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/*      */     
/* 4847 */     _jspx_th_fmt_005fmessage_005f23.setKey("am.webclient.processtemplate.new");
/* 4848 */     int _jspx_eval_fmt_005fmessage_005f23 = _jspx_th_fmt_005fmessage_005f23.doStartTag();
/* 4849 */     if (_jspx_th_fmt_005fmessage_005f23.doEndTag() == 5) {
/* 4850 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f23);
/* 4851 */       return true;
/*      */     }
/* 4853 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f23);
/* 4854 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4859 */     PageContext pageContext = _jspx_page_context;
/* 4860 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4862 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4863 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 4864 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/*      */     
/* 4866 */     _jspx_th_c_005fout_005f22.setValue("${templatetype}");
/* 4867 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 4868 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 4869 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 4870 */       return true;
/*      */     }
/* 4872 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 4873 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f6(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4878 */     PageContext pageContext = _jspx_page_context;
/* 4879 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4881 */     ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4882 */     _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 4883 */     _jspx_th_c_005fchoose_005f6.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/* 4884 */     int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 4885 */     if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */       for (;;) {
/* 4887 */         if (_jspx_meth_c_005fwhen_005f6(_jspx_th_c_005fchoose_005f6, _jspx_page_context))
/* 4888 */           return true;
/* 4889 */         if (_jspx_meth_c_005fotherwise_005f6(_jspx_th_c_005fchoose_005f6, _jspx_page_context))
/* 4890 */           return true;
/* 4891 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 4892 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4896 */     if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 4897 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 4898 */       return true;
/*      */     }
/* 4900 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 4901 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f6(JspTag _jspx_th_c_005fchoose_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4906 */     PageContext pageContext = _jspx_page_context;
/* 4907 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4909 */     WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4910 */     _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 4911 */     _jspx_th_c_005fwhen_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f6);
/*      */     
/* 4913 */     _jspx_th_c_005fwhen_005f6.setTest("${templatetype==PROCESSTEMPLATE}");
/* 4914 */     int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 4915 */     if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */       for (;;) {
/* 4917 */         out.write("\n                ");
/* 4918 */         if (_jspx_meth_fmt_005fmessage_005f24(_jspx_th_c_005fwhen_005f6, _jspx_page_context))
/* 4919 */           return true;
/* 4920 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 4921 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4925 */     if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 4926 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 4927 */       return true;
/*      */     }
/* 4929 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 4930 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f24(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4935 */     PageContext pageContext = _jspx_page_context;
/* 4936 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4938 */     MessageTag _jspx_th_fmt_005fmessage_005f24 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 4939 */     _jspx_th_fmt_005fmessage_005f24.setPageContext(_jspx_page_context);
/* 4940 */     _jspx_th_fmt_005fmessage_005f24.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/*      */     
/* 4942 */     _jspx_th_fmt_005fmessage_005f24.setKey("am.webclient.admin.showtemplate.link");
/* 4943 */     int _jspx_eval_fmt_005fmessage_005f24 = _jspx_th_fmt_005fmessage_005f24.doStartTag();
/* 4944 */     if (_jspx_eval_fmt_005fmessage_005f24 != 0) {
/* 4945 */       if (_jspx_eval_fmt_005fmessage_005f24 != 1) {
/* 4946 */         out = _jspx_page_context.pushBody();
/* 4947 */         _jspx_th_fmt_005fmessage_005f24.setBodyContent((BodyContent)out);
/* 4948 */         _jspx_th_fmt_005fmessage_005f24.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4951 */         out.write(32);
/* 4952 */         if (_jspx_meth_fmt_005fparam_005f7(_jspx_th_fmt_005fmessage_005f24, _jspx_page_context))
/* 4953 */           return true;
/* 4954 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f24.doAfterBody();
/* 4955 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4958 */       if (_jspx_eval_fmt_005fmessage_005f24 != 1) {
/* 4959 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4962 */     if (_jspx_th_fmt_005fmessage_005f24.doEndTag() == 5) {
/* 4963 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f24);
/* 4964 */       return true;
/*      */     }
/* 4966 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f24);
/* 4967 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f7(JspTag _jspx_th_fmt_005fmessage_005f24, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4972 */     PageContext pageContext = _jspx_page_context;
/* 4973 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4975 */     ParamTag _jspx_th_fmt_005fparam_005f7 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 4976 */     _jspx_th_fmt_005fparam_005f7.setPageContext(_jspx_page_context);
/* 4977 */     _jspx_th_fmt_005fparam_005f7.setParent((Tag)_jspx_th_fmt_005fmessage_005f24);
/* 4978 */     int _jspx_eval_fmt_005fparam_005f7 = _jspx_th_fmt_005fparam_005f7.doStartTag();
/* 4979 */     if (_jspx_eval_fmt_005fparam_005f7 != 0) {
/* 4980 */       if (_jspx_eval_fmt_005fparam_005f7 != 1) {
/* 4981 */         out = _jspx_page_context.pushBody();
/* 4982 */         _jspx_th_fmt_005fparam_005f7.setBodyContent((BodyContent)out);
/* 4983 */         _jspx_th_fmt_005fparam_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4986 */         if (_jspx_meth_c_005fout_005f23(_jspx_th_fmt_005fparam_005f7, _jspx_page_context))
/* 4987 */           return true;
/* 4988 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f7.doAfterBody();
/* 4989 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4992 */       if (_jspx_eval_fmt_005fparam_005f7 != 1) {
/* 4993 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4996 */     if (_jspx_th_fmt_005fparam_005f7.doEndTag() == 5) {
/* 4997 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f7);
/* 4998 */       return true;
/*      */     }
/* 5000 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f7);
/* 5001 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_fmt_005fparam_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5006 */     PageContext pageContext = _jspx_page_context;
/* 5007 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5009 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5010 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 5011 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_fmt_005fparam_005f7);
/*      */     
/* 5013 */     _jspx_th_c_005fout_005f23.setValue("${templatetypestr}");
/* 5014 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 5015 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 5016 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5017 */       return true;
/*      */     }
/* 5019 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5020 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f6(JspTag _jspx_th_c_005fchoose_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5025 */     PageContext pageContext = _jspx_page_context;
/* 5026 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5028 */     OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5029 */     _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 5030 */     _jspx_th_c_005fotherwise_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f6);
/* 5031 */     int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 5032 */     if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */       for (;;) {
/* 5034 */         if (_jspx_meth_fmt_005fmessage_005f25(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/* 5035 */           return true;
/* 5036 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 5037 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5041 */     if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 5042 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 5043 */       return true;
/*      */     }
/* 5045 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 5046 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f25(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5051 */     PageContext pageContext = _jspx_page_context;
/* 5052 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5054 */     MessageTag _jspx_th_fmt_005fmessage_005f25 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5055 */     _jspx_th_fmt_005fmessage_005f25.setPageContext(_jspx_page_context);
/* 5056 */     _jspx_th_fmt_005fmessage_005f25.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 5058 */     _jspx_th_fmt_005fmessage_005f25.setKey("am.webclient.admin.showservicetemplate.link");
/* 5059 */     int _jspx_eval_fmt_005fmessage_005f25 = _jspx_th_fmt_005fmessage_005f25.doStartTag();
/* 5060 */     if (_jspx_th_fmt_005fmessage_005f25.doEndTag() == 5) {
/* 5061 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f25);
/* 5062 */       return true;
/*      */     }
/* 5064 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f25);
/* 5065 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f26(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5070 */     PageContext pageContext = _jspx_page_context;
/* 5071 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5073 */     MessageTag _jspx_th_fmt_005fmessage_005f26 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5074 */     _jspx_th_fmt_005fmessage_005f26.setPageContext(_jspx_page_context);
/* 5075 */     _jspx_th_fmt_005fmessage_005f26.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/*      */     
/* 5077 */     _jspx_th_fmt_005fmessage_005f26.setKey("am.webclient.processtemplate.new");
/* 5078 */     int _jspx_eval_fmt_005fmessage_005f26 = _jspx_th_fmt_005fmessage_005f26.doStartTag();
/* 5079 */     if (_jspx_th_fmt_005fmessage_005f26.doEndTag() == 5) {
/* 5080 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f26);
/* 5081 */       return true;
/*      */     }
/* 5083 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f26);
/* 5084 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f27(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5089 */     PageContext pageContext = _jspx_page_context;
/* 5090 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5092 */     MessageTag _jspx_th_fmt_005fmessage_005f27 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 5093 */     _jspx_th_fmt_005fmessage_005f27.setPageContext(_jspx_page_context);
/* 5094 */     _jspx_th_fmt_005fmessage_005f27.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5096 */     _jspx_th_fmt_005fmessage_005f27.setKey("am.webclient.admin.addprocesstemplate.text");
/* 5097 */     int _jspx_eval_fmt_005fmessage_005f27 = _jspx_th_fmt_005fmessage_005f27.doStartTag();
/* 5098 */     if (_jspx_eval_fmt_005fmessage_005f27 != 0) {
/* 5099 */       if (_jspx_eval_fmt_005fmessage_005f27 != 1) {
/* 5100 */         out = _jspx_page_context.pushBody();
/* 5101 */         _jspx_th_fmt_005fmessage_005f27.setBodyContent((BodyContent)out);
/* 5102 */         _jspx_th_fmt_005fmessage_005f27.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5105 */         if (_jspx_meth_fmt_005fparam_005f8(_jspx_th_fmt_005fmessage_005f27, _jspx_page_context))
/* 5106 */           return true;
/* 5107 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f27.doAfterBody();
/* 5108 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5111 */       if (_jspx_eval_fmt_005fmessage_005f27 != 1) {
/* 5112 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5115 */     if (_jspx_th_fmt_005fmessage_005f27.doEndTag() == 5) {
/* 5116 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f27);
/* 5117 */       return true;
/*      */     }
/* 5119 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f27);
/* 5120 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f8(JspTag _jspx_th_fmt_005fmessage_005f27, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5125 */     PageContext pageContext = _jspx_page_context;
/* 5126 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5128 */     ParamTag _jspx_th_fmt_005fparam_005f8 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 5129 */     _jspx_th_fmt_005fparam_005f8.setPageContext(_jspx_page_context);
/* 5130 */     _jspx_th_fmt_005fparam_005f8.setParent((Tag)_jspx_th_fmt_005fmessage_005f27);
/* 5131 */     int _jspx_eval_fmt_005fparam_005f8 = _jspx_th_fmt_005fparam_005f8.doStartTag();
/* 5132 */     if (_jspx_eval_fmt_005fparam_005f8 != 0) {
/* 5133 */       if (_jspx_eval_fmt_005fparam_005f8 != 1) {
/* 5134 */         out = _jspx_page_context.pushBody();
/* 5135 */         _jspx_th_fmt_005fparam_005f8.setBodyContent((BodyContent)out);
/* 5136 */         _jspx_th_fmt_005fparam_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5139 */         if (_jspx_meth_c_005fout_005f24(_jspx_th_fmt_005fparam_005f8, _jspx_page_context))
/* 5140 */           return true;
/* 5141 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f8.doAfterBody();
/* 5142 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5145 */       if (_jspx_eval_fmt_005fparam_005f8 != 1) {
/* 5146 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5149 */     if (_jspx_th_fmt_005fparam_005f8.doEndTag() == 5) {
/* 5150 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f8);
/* 5151 */       return true;
/*      */     }
/* 5153 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f8);
/* 5154 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_fmt_005fparam_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5159 */     PageContext pageContext = _jspx_page_context;
/* 5160 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5162 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5163 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 5164 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_fmt_005fparam_005f8);
/*      */     
/* 5166 */     _jspx_th_c_005fout_005f24.setValue("${templatetypestr}");
/* 5167 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 5168 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 5169 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5170 */       return true;
/*      */     }
/* 5172 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5173 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5178 */     PageContext pageContext = _jspx_page_context;
/* 5179 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5181 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 5182 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 5183 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5185 */     _jspx_th_html_005ftext_005f0.setProperty("templateName");
/*      */     
/* 5187 */     _jspx_th_html_005ftext_005f0.setSize("41");
/*      */     
/* 5189 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext large");
/*      */     
/* 5191 */     _jspx_th_html_005ftext_005f0.setMaxlength("50");
/* 5192 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 5193 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 5194 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 5195 */       return true;
/*      */     }
/* 5197 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 5198 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5203 */     PageContext pageContext = _jspx_page_context;
/* 5204 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5206 */     TextareaTag _jspx_th_html_005ftextarea_005f0 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 5207 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 5208 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5210 */     _jspx_th_html_005ftextarea_005f0.setProperty("templateDescription");
/*      */     
/* 5212 */     _jspx_th_html_005ftextarea_005f0.setStyleClass("formtextarea large");
/*      */     
/* 5214 */     _jspx_th_html_005ftextarea_005f0.setRows("3");
/*      */     
/* 5216 */     _jspx_th_html_005ftextarea_005f0.setCols("39");
/* 5217 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 5218 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 5219 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 5220 */       return true;
/*      */     }
/* 5222 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 5223 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5228 */     PageContext pageContext = _jspx_page_context;
/* 5229 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5231 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5232 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 5233 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5235 */     _jspx_th_c_005fif_005f0.setTest("${matchinterval eq '0'}");
/* 5236 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 5237 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 5239 */         out.write("checked");
/* 5240 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 5241 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5245 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 5246 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5247 */       return true;
/*      */     }
/* 5249 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5250 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5255 */     PageContext pageContext = _jspx_page_context;
/* 5256 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5258 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5259 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 5260 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5262 */     _jspx_th_c_005fif_005f1.setTest("${matchinterval eq '1'}");
/* 5263 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 5264 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 5266 */         out.write("checked");
/* 5267 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 5268 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5272 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 5273 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5274 */       return true;
/*      */     }
/* 5276 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5277 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5282 */     PageContext pageContext = _jspx_page_context;
/* 5283 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5285 */     ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5286 */     _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 5287 */     _jspx_th_c_005fchoose_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 5288 */     int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 5289 */     if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */       for (;;) {
/* 5291 */         if (_jspx_meth_c_005fwhen_005f7(_jspx_th_c_005fchoose_005f7, _jspx_page_context))
/* 5292 */           return true;
/* 5293 */         if (_jspx_meth_c_005fotherwise_005f7(_jspx_th_c_005fchoose_005f7, _jspx_page_context))
/* 5294 */           return true;
/* 5295 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 5296 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5300 */     if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 5301 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 5302 */       return true;
/*      */     }
/* 5304 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 5305 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f7(JspTag _jspx_th_c_005fchoose_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5310 */     PageContext pageContext = _jspx_page_context;
/* 5311 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5313 */     WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5314 */     _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 5315 */     _jspx_th_c_005fwhen_005f7.setParent((Tag)_jspx_th_c_005fchoose_005f7);
/*      */     
/* 5317 */     _jspx_th_c_005fwhen_005f7.setTest("${templatetype==PROCESSTEMPLATE}");
/* 5318 */     int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 5319 */     if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */       for (;;) {
/* 5321 */         out.write("\n                ");
/* 5322 */         if (_jspx_meth_fmt_005fmessage_005f28(_jspx_th_c_005fwhen_005f7, _jspx_page_context))
/* 5323 */           return true;
/* 5324 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 5325 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5329 */     if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 5330 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 5331 */       return true;
/*      */     }
/* 5333 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 5334 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f28(JspTag _jspx_th_c_005fwhen_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5339 */     PageContext pageContext = _jspx_page_context;
/* 5340 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5342 */     MessageTag _jspx_th_fmt_005fmessage_005f28 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 5343 */     _jspx_th_fmt_005fmessage_005f28.setPageContext(_jspx_page_context);
/* 5344 */     _jspx_th_fmt_005fmessage_005f28.setParent((Tag)_jspx_th_c_005fwhen_005f7);
/*      */     
/* 5346 */     _jspx_th_fmt_005fmessage_005f28.setKey("am.webclient.admin.showtemplate.link");
/* 5347 */     int _jspx_eval_fmt_005fmessage_005f28 = _jspx_th_fmt_005fmessage_005f28.doStartTag();
/* 5348 */     if (_jspx_eval_fmt_005fmessage_005f28 != 0) {
/* 5349 */       if (_jspx_eval_fmt_005fmessage_005f28 != 1) {
/* 5350 */         out = _jspx_page_context.pushBody();
/* 5351 */         _jspx_th_fmt_005fmessage_005f28.setBodyContent((BodyContent)out);
/* 5352 */         _jspx_th_fmt_005fmessage_005f28.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5355 */         out.write(32);
/* 5356 */         if (_jspx_meth_fmt_005fparam_005f9(_jspx_th_fmt_005fmessage_005f28, _jspx_page_context))
/* 5357 */           return true;
/* 5358 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f28.doAfterBody();
/* 5359 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5362 */       if (_jspx_eval_fmt_005fmessage_005f28 != 1) {
/* 5363 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5366 */     if (_jspx_th_fmt_005fmessage_005f28.doEndTag() == 5) {
/* 5367 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f28);
/* 5368 */       return true;
/*      */     }
/* 5370 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f28);
/* 5371 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f9(JspTag _jspx_th_fmt_005fmessage_005f28, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5376 */     PageContext pageContext = _jspx_page_context;
/* 5377 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5379 */     ParamTag _jspx_th_fmt_005fparam_005f9 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 5380 */     _jspx_th_fmt_005fparam_005f9.setPageContext(_jspx_page_context);
/* 5381 */     _jspx_th_fmt_005fparam_005f9.setParent((Tag)_jspx_th_fmt_005fmessage_005f28);
/* 5382 */     int _jspx_eval_fmt_005fparam_005f9 = _jspx_th_fmt_005fparam_005f9.doStartTag();
/* 5383 */     if (_jspx_eval_fmt_005fparam_005f9 != 0) {
/* 5384 */       if (_jspx_eval_fmt_005fparam_005f9 != 1) {
/* 5385 */         out = _jspx_page_context.pushBody();
/* 5386 */         _jspx_th_fmt_005fparam_005f9.setBodyContent((BodyContent)out);
/* 5387 */         _jspx_th_fmt_005fparam_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5390 */         if (_jspx_meth_c_005fout_005f25(_jspx_th_fmt_005fparam_005f9, _jspx_page_context))
/* 5391 */           return true;
/* 5392 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f9.doAfterBody();
/* 5393 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5396 */       if (_jspx_eval_fmt_005fparam_005f9 != 1) {
/* 5397 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5400 */     if (_jspx_th_fmt_005fparam_005f9.doEndTag() == 5) {
/* 5401 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f9);
/* 5402 */       return true;
/*      */     }
/* 5404 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f9);
/* 5405 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_fmt_005fparam_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5410 */     PageContext pageContext = _jspx_page_context;
/* 5411 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5413 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5414 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 5415 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_fmt_005fparam_005f9);
/*      */     
/* 5417 */     _jspx_th_c_005fout_005f25.setValue("${templatetypestr}");
/* 5418 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 5419 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 5420 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5421 */       return true;
/*      */     }
/* 5423 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5424 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f7(JspTag _jspx_th_c_005fchoose_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5429 */     PageContext pageContext = _jspx_page_context;
/* 5430 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5432 */     OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5433 */     _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 5434 */     _jspx_th_c_005fotherwise_005f7.setParent((Tag)_jspx_th_c_005fchoose_005f7);
/* 5435 */     int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 5436 */     if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */       for (;;) {
/* 5438 */         if (_jspx_meth_fmt_005fmessage_005f29(_jspx_th_c_005fotherwise_005f7, _jspx_page_context))
/* 5439 */           return true;
/* 5440 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 5441 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5445 */     if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 5446 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 5447 */       return true;
/*      */     }
/* 5449 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 5450 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f29(JspTag _jspx_th_c_005fotherwise_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5455 */     PageContext pageContext = _jspx_page_context;
/* 5456 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5458 */     MessageTag _jspx_th_fmt_005fmessage_005f29 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5459 */     _jspx_th_fmt_005fmessage_005f29.setPageContext(_jspx_page_context);
/* 5460 */     _jspx_th_fmt_005fmessage_005f29.setParent((Tag)_jspx_th_c_005fotherwise_005f7);
/*      */     
/* 5462 */     _jspx_th_fmt_005fmessage_005f29.setKey("am.webclient.admin.showservicetemplate.link");
/* 5463 */     int _jspx_eval_fmt_005fmessage_005f29 = _jspx_th_fmt_005fmessage_005f29.doStartTag();
/* 5464 */     if (_jspx_th_fmt_005fmessage_005f29.doEndTag() == 5) {
/* 5465 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f29);
/* 5466 */       return true;
/*      */     }
/* 5468 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f29);
/* 5469 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f30(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5474 */     PageContext pageContext = _jspx_page_context;
/* 5475 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5477 */     MessageTag _jspx_th_fmt_005fmessage_005f30 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 5478 */     _jspx_th_fmt_005fmessage_005f30.setPageContext(_jspx_page_context);
/* 5479 */     _jspx_th_fmt_005fmessage_005f30.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5481 */     _jspx_th_fmt_005fmessage_005f30.setKey("am.webclient.processtemplate.defnhelpcard");
/* 5482 */     int _jspx_eval_fmt_005fmessage_005f30 = _jspx_th_fmt_005fmessage_005f30.doStartTag();
/* 5483 */     if (_jspx_eval_fmt_005fmessage_005f30 != 0) {
/* 5484 */       if (_jspx_eval_fmt_005fmessage_005f30 != 1) {
/* 5485 */         out = _jspx_page_context.pushBody();
/* 5486 */         _jspx_th_fmt_005fmessage_005f30.setBodyContent((BodyContent)out);
/* 5487 */         _jspx_th_fmt_005fmessage_005f30.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5490 */         if (_jspx_meth_fmt_005fparam_005f10(_jspx_th_fmt_005fmessage_005f30, _jspx_page_context))
/* 5491 */           return true;
/* 5492 */         out.write(32);
/* 5493 */         if (_jspx_meth_fmt_005fparam_005f11(_jspx_th_fmt_005fmessage_005f30, _jspx_page_context))
/* 5494 */           return true;
/* 5495 */         out.write(10);
/* 5496 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f30.doAfterBody();
/* 5497 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5500 */       if (_jspx_eval_fmt_005fmessage_005f30 != 1) {
/* 5501 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5504 */     if (_jspx_th_fmt_005fmessage_005f30.doEndTag() == 5) {
/* 5505 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f30);
/* 5506 */       return true;
/*      */     }
/* 5508 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f30);
/* 5509 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f10(JspTag _jspx_th_fmt_005fmessage_005f30, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5514 */     PageContext pageContext = _jspx_page_context;
/* 5515 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5517 */     ParamTag _jspx_th_fmt_005fparam_005f10 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 5518 */     _jspx_th_fmt_005fparam_005f10.setPageContext(_jspx_page_context);
/* 5519 */     _jspx_th_fmt_005fparam_005f10.setParent((Tag)_jspx_th_fmt_005fmessage_005f30);
/* 5520 */     int _jspx_eval_fmt_005fparam_005f10 = _jspx_th_fmt_005fparam_005f10.doStartTag();
/* 5521 */     if (_jspx_eval_fmt_005fparam_005f10 != 0) {
/* 5522 */       if (_jspx_eval_fmt_005fparam_005f10 != 1) {
/* 5523 */         out = _jspx_page_context.pushBody();
/* 5524 */         _jspx_th_fmt_005fparam_005f10.setBodyContent((BodyContent)out);
/* 5525 */         _jspx_th_fmt_005fparam_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5528 */         if (_jspx_meth_c_005fout_005f26(_jspx_th_fmt_005fparam_005f10, _jspx_page_context))
/* 5529 */           return true;
/* 5530 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f10.doAfterBody();
/* 5531 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5534 */       if (_jspx_eval_fmt_005fparam_005f10 != 1) {
/* 5535 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5538 */     if (_jspx_th_fmt_005fparam_005f10.doEndTag() == 5) {
/* 5539 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f10);
/* 5540 */       return true;
/*      */     }
/* 5542 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f10);
/* 5543 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_fmt_005fparam_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5548 */     PageContext pageContext = _jspx_page_context;
/* 5549 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5551 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5552 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 5553 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_fmt_005fparam_005f10);
/*      */     
/* 5555 */     _jspx_th_c_005fout_005f26.setValue("${lowercasepsmsg}");
/* 5556 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 5557 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 5558 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5559 */       return true;
/*      */     }
/* 5561 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5562 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f11(JspTag _jspx_th_fmt_005fmessage_005f30, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5567 */     PageContext pageContext = _jspx_page_context;
/* 5568 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5570 */     ParamTag _jspx_th_fmt_005fparam_005f11 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 5571 */     _jspx_th_fmt_005fparam_005f11.setPageContext(_jspx_page_context);
/* 5572 */     _jspx_th_fmt_005fparam_005f11.setParent((Tag)_jspx_th_fmt_005fmessage_005f30);
/* 5573 */     int _jspx_eval_fmt_005fparam_005f11 = _jspx_th_fmt_005fparam_005f11.doStartTag();
/* 5574 */     if (_jspx_eval_fmt_005fparam_005f11 != 0) {
/* 5575 */       if (_jspx_eval_fmt_005fparam_005f11 != 1) {
/* 5576 */         out = _jspx_page_context.pushBody();
/* 5577 */         _jspx_th_fmt_005fparam_005f11.setBodyContent((BodyContent)out);
/* 5578 */         _jspx_th_fmt_005fparam_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5581 */         if (_jspx_meth_c_005fout_005f27(_jspx_th_fmt_005fparam_005f11, _jspx_page_context))
/* 5582 */           return true;
/* 5583 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f11.doAfterBody();
/* 5584 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5587 */       if (_jspx_eval_fmt_005fparam_005f11 != 1) {
/* 5588 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5591 */     if (_jspx_th_fmt_005fparam_005f11.doEndTag() == 5) {
/* 5592 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f11);
/* 5593 */       return true;
/*      */     }
/* 5595 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f11);
/* 5596 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_fmt_005fparam_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5601 */     PageContext pageContext = _jspx_page_context;
/* 5602 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5604 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5605 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 5606 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_fmt_005fparam_005f11);
/*      */     
/* 5608 */     _jspx_th_c_005fout_005f27.setValue("${lowercasemultipsmsg}");
/* 5609 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 5610 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 5611 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5612 */       return true;
/*      */     }
/* 5614 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5615 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5620 */     PageContext pageContext = _jspx_page_context;
/* 5621 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5623 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5624 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 5625 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5627 */     _jspx_th_c_005fif_005f2.setTest("${templatetype == PROCESSTEMPLATE}");
/* 5628 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 5629 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 5631 */         out.write(" \n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<legend>");
/* 5632 */         if (_jspx_meth_fmt_005fmessage_005f31(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 5633 */           return true;
/* 5634 */         out.write(" </legend>\n");
/* 5635 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 5636 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5640 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 5641 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5642 */       return true;
/*      */     }
/* 5644 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5645 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f31(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5650 */     PageContext pageContext = _jspx_page_context;
/* 5651 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5653 */     MessageTag _jspx_th_fmt_005fmessage_005f31 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5654 */     _jspx_th_fmt_005fmessage_005f31.setPageContext(_jspx_page_context);
/* 5655 */     _jspx_th_fmt_005fmessage_005f31.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 5657 */     _jspx_th_fmt_005fmessage_005f31.setKey("am.webclient.processtemplate.addedprocess");
/* 5658 */     int _jspx_eval_fmt_005fmessage_005f31 = _jspx_th_fmt_005fmessage_005f31.doStartTag();
/* 5659 */     if (_jspx_th_fmt_005fmessage_005f31.doEndTag() == 5) {
/* 5660 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f31);
/* 5661 */       return true;
/*      */     }
/* 5663 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f31);
/* 5664 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5669 */     PageContext pageContext = _jspx_page_context;
/* 5670 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5672 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5673 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 5674 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5676 */     _jspx_th_c_005fif_005f3.setTest("${templatetype == SERVICETEMPLATE}");
/* 5677 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 5678 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 5680 */         out.write(" \n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<legend>");
/* 5681 */         if (_jspx_meth_fmt_005fmessage_005f32(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 5682 */           return true;
/* 5683 */         out.write("</legend>\n");
/* 5684 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 5685 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5689 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 5690 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5691 */       return true;
/*      */     }
/* 5693 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5694 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f32(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5699 */     PageContext pageContext = _jspx_page_context;
/* 5700 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5702 */     MessageTag _jspx_th_fmt_005fmessage_005f32 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5703 */     _jspx_th_fmt_005fmessage_005f32.setPageContext(_jspx_page_context);
/* 5704 */     _jspx_th_fmt_005fmessage_005f32.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 5706 */     _jspx_th_fmt_005fmessage_005f32.setKey("am.webclient.processtemplate.addedservice");
/* 5707 */     int _jspx_eval_fmt_005fmessage_005f32 = _jspx_th_fmt_005fmessage_005f32.doStartTag();
/* 5708 */     if (_jspx_th_fmt_005fmessage_005f32.doEndTag() == 5) {
/* 5709 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f32);
/* 5710 */       return true;
/*      */     }
/* 5712 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f32);
/* 5713 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f33(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5718 */     PageContext pageContext = _jspx_page_context;
/* 5719 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5721 */     MessageTag _jspx_th_fmt_005fmessage_005f33 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 5722 */     _jspx_th_fmt_005fmessage_005f33.setPageContext(_jspx_page_context);
/* 5723 */     _jspx_th_fmt_005fmessage_005f33.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5725 */     _jspx_th_fmt_005fmessage_005f33.setKey("am.webclient.processtemplate.addprocess");
/* 5726 */     int _jspx_eval_fmt_005fmessage_005f33 = _jspx_th_fmt_005fmessage_005f33.doStartTag();
/* 5727 */     if (_jspx_eval_fmt_005fmessage_005f33 != 0) {
/* 5728 */       if (_jspx_eval_fmt_005fmessage_005f33 != 1) {
/* 5729 */         out = _jspx_page_context.pushBody();
/* 5730 */         _jspx_th_fmt_005fmessage_005f33.setBodyContent((BodyContent)out);
/* 5731 */         _jspx_th_fmt_005fmessage_005f33.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5734 */         if (_jspx_meth_fmt_005fparam_005f12(_jspx_th_fmt_005fmessage_005f33, _jspx_page_context))
/* 5735 */           return true;
/* 5736 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f33.doAfterBody();
/* 5737 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5740 */       if (_jspx_eval_fmt_005fmessage_005f33 != 1) {
/* 5741 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5744 */     if (_jspx_th_fmt_005fmessage_005f33.doEndTag() == 5) {
/* 5745 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f33);
/* 5746 */       return true;
/*      */     }
/* 5748 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f33);
/* 5749 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f12(JspTag _jspx_th_fmt_005fmessage_005f33, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5754 */     PageContext pageContext = _jspx_page_context;
/* 5755 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5757 */     ParamTag _jspx_th_fmt_005fparam_005f12 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 5758 */     _jspx_th_fmt_005fparam_005f12.setPageContext(_jspx_page_context);
/* 5759 */     _jspx_th_fmt_005fparam_005f12.setParent((Tag)_jspx_th_fmt_005fmessage_005f33);
/* 5760 */     int _jspx_eval_fmt_005fparam_005f12 = _jspx_th_fmt_005fparam_005f12.doStartTag();
/* 5761 */     if (_jspx_eval_fmt_005fparam_005f12 != 0) {
/* 5762 */       if (_jspx_eval_fmt_005fparam_005f12 != 1) {
/* 5763 */         out = _jspx_page_context.pushBody();
/* 5764 */         _jspx_th_fmt_005fparam_005f12.setBodyContent((BodyContent)out);
/* 5765 */         _jspx_th_fmt_005fparam_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5768 */         if (_jspx_meth_c_005fout_005f28(_jspx_th_fmt_005fparam_005f12, _jspx_page_context))
/* 5769 */           return true;
/* 5770 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f12.doAfterBody();
/* 5771 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5774 */       if (_jspx_eval_fmt_005fparam_005f12 != 1) {
/* 5775 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5778 */     if (_jspx_th_fmt_005fparam_005f12.doEndTag() == 5) {
/* 5779 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f12);
/* 5780 */       return true;
/*      */     }
/* 5782 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f12);
/* 5783 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_fmt_005fparam_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5788 */     PageContext pageContext = _jspx_page_context;
/* 5789 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5791 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5792 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 5793 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_fmt_005fparam_005f12);
/*      */     
/* 5795 */     _jspx_th_c_005fout_005f28.setValue("${templatetypestr}");
/* 5796 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 5797 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 5798 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5799 */       return true;
/*      */     }
/* 5801 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5802 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f34(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5807 */     PageContext pageContext = _jspx_page_context;
/* 5808 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5810 */     MessageTag _jspx_th_fmt_005fmessage_005f34 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 5811 */     _jspx_th_fmt_005fmessage_005f34.setPageContext(_jspx_page_context);
/* 5812 */     _jspx_th_fmt_005fmessage_005f34.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5814 */     _jspx_th_fmt_005fmessage_005f34.setKey("am.webclient.processtemplate.deleteprocess");
/* 5815 */     int _jspx_eval_fmt_005fmessage_005f34 = _jspx_th_fmt_005fmessage_005f34.doStartTag();
/* 5816 */     if (_jspx_eval_fmt_005fmessage_005f34 != 0) {
/* 5817 */       if (_jspx_eval_fmt_005fmessage_005f34 != 1) {
/* 5818 */         out = _jspx_page_context.pushBody();
/* 5819 */         _jspx_th_fmt_005fmessage_005f34.setBodyContent((BodyContent)out);
/* 5820 */         _jspx_th_fmt_005fmessage_005f34.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5823 */         if (_jspx_meth_fmt_005fparam_005f13(_jspx_th_fmt_005fmessage_005f34, _jspx_page_context))
/* 5824 */           return true;
/* 5825 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f34.doAfterBody();
/* 5826 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5829 */       if (_jspx_eval_fmt_005fmessage_005f34 != 1) {
/* 5830 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5833 */     if (_jspx_th_fmt_005fmessage_005f34.doEndTag() == 5) {
/* 5834 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f34);
/* 5835 */       return true;
/*      */     }
/* 5837 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f34);
/* 5838 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f13(JspTag _jspx_th_fmt_005fmessage_005f34, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5843 */     PageContext pageContext = _jspx_page_context;
/* 5844 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5846 */     ParamTag _jspx_th_fmt_005fparam_005f13 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 5847 */     _jspx_th_fmt_005fparam_005f13.setPageContext(_jspx_page_context);
/* 5848 */     _jspx_th_fmt_005fparam_005f13.setParent((Tag)_jspx_th_fmt_005fmessage_005f34);
/* 5849 */     int _jspx_eval_fmt_005fparam_005f13 = _jspx_th_fmt_005fparam_005f13.doStartTag();
/* 5850 */     if (_jspx_eval_fmt_005fparam_005f13 != 0) {
/* 5851 */       if (_jspx_eval_fmt_005fparam_005f13 != 1) {
/* 5852 */         out = _jspx_page_context.pushBody();
/* 5853 */         _jspx_th_fmt_005fparam_005f13.setBodyContent((BodyContent)out);
/* 5854 */         _jspx_th_fmt_005fparam_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5857 */         if (_jspx_meth_c_005fout_005f29(_jspx_th_fmt_005fparam_005f13, _jspx_page_context))
/* 5858 */           return true;
/* 5859 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f13.doAfterBody();
/* 5860 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5863 */       if (_jspx_eval_fmt_005fparam_005f13 != 1) {
/* 5864 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5867 */     if (_jspx_th_fmt_005fparam_005f13.doEndTag() == 5) {
/* 5868 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f13);
/* 5869 */       return true;
/*      */     }
/* 5871 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f13);
/* 5872 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_fmt_005fparam_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5877 */     PageContext pageContext = _jspx_page_context;
/* 5878 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5880 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5881 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 5882 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_fmt_005fparam_005f13);
/*      */     
/* 5884 */     _jspx_th_c_005fout_005f29.setValue("${templatetypestr}");
/* 5885 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 5886 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 5887 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5888 */       return true;
/*      */     }
/* 5890 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5891 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5896 */     PageContext pageContext = _jspx_page_context;
/* 5897 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5899 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5900 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 5901 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5903 */     _jspx_th_c_005fif_005f4.setTest("${not empty cacheprocessid}");
/* 5904 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 5905 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 5907 */         out.write("\n\t<script> showDiv(\"adddeleteprocess\")</script>\n");
/* 5908 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 5909 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5913 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 5914 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 5915 */       return true;
/*      */     }
/* 5917 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 5918 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5923 */     PageContext pageContext = _jspx_page_context;
/* 5924 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5926 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5927 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 5928 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5930 */     _jspx_th_c_005fif_005f5.setTest("${templatetype == PROCESSTEMPLATE}");
/* 5931 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 5932 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 5934 */         out.write("\n<tr>\n<td colspan=\"5\"><div id=\"showrow\" style=\"display:none\">\n<table width=\"100%\" id=\"processtablehead\" border=\"0\" cellspacing=\"0\" >\n<tr class=\"whitegrayrightalign\" width=\"100%\">\n\t<td style=\"height:28px;\" width=\"4%\"  class=\"bodytextbold whitegrayrightalign\" ><input type=\"checkbox\" id=\"processcheckbox\" name=\"processcheckbox\"  value='' checked onclick=\"javascript:fnSelectAll(this);\"/></td>\n    <td style=\"height:10px;\" width=\"21%\" class=\"bodytextbold whitegrayrightalign\" >");
/* 5935 */         if (_jspx_meth_fmt_005fmessage_005f35(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 5936 */           return true;
/* 5937 */         out.write("</td> ");
/* 5938 */         out.write("\n    <td style=\"height:10px;\" width=\"21%\" class=\"bodytextbold whitegrayrightalign\" >");
/* 5939 */         if (_jspx_meth_fmt_005fmessage_005f36(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 5940 */           return true;
/* 5941 */         out.write("</td> ");
/* 5942 */         out.write("\n    <td style=\"height:10px;\" width=\"50%\" class=\"bodytextbold whitegrayrightalign\" >");
/* 5943 */         if (_jspx_meth_fmt_005fmessage_005f37(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 5944 */           return true;
/* 5945 */         out.write("</td> ");
/* 5946 */         out.write("\n    <td style=\"height:10px;\" width=\"4%\" class=\"bodytextbold whitegrayrightalign\">&nbsp;</td>\n</tr>\n</table>\n</div>\n</td>\n</tr>\n");
/* 5947 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 5948 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5952 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 5953 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 5954 */       return true;
/*      */     }
/* 5956 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 5957 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f35(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5962 */     PageContext pageContext = _jspx_page_context;
/* 5963 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5965 */     MessageTag _jspx_th_fmt_005fmessage_005f35 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5966 */     _jspx_th_fmt_005fmessage_005f35.setPageContext(_jspx_page_context);
/* 5967 */     _jspx_th_fmt_005fmessage_005f35.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 5969 */     _jspx_th_fmt_005fmessage_005f35.setKey("am.webclient.common.displayname.text");
/* 5970 */     int _jspx_eval_fmt_005fmessage_005f35 = _jspx_th_fmt_005fmessage_005f35.doStartTag();
/* 5971 */     if (_jspx_th_fmt_005fmessage_005f35.doEndTag() == 5) {
/* 5972 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f35);
/* 5973 */       return true;
/*      */     }
/* 5975 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f35);
/* 5976 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f36(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5981 */     PageContext pageContext = _jspx_page_context;
/* 5982 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5984 */     MessageTag _jspx_th_fmt_005fmessage_005f36 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 5985 */     _jspx_th_fmt_005fmessage_005f36.setPageContext(_jspx_page_context);
/* 5986 */     _jspx_th_fmt_005fmessage_005f36.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 5988 */     _jspx_th_fmt_005fmessage_005f36.setKey("am.webclient.processtemplate.processname");
/* 5989 */     int _jspx_eval_fmt_005fmessage_005f36 = _jspx_th_fmt_005fmessage_005f36.doStartTag();
/* 5990 */     if (_jspx_eval_fmt_005fmessage_005f36 != 0) {
/* 5991 */       if (_jspx_eval_fmt_005fmessage_005f36 != 1) {
/* 5992 */         out = _jspx_page_context.pushBody();
/* 5993 */         _jspx_th_fmt_005fmessage_005f36.setBodyContent((BodyContent)out);
/* 5994 */         _jspx_th_fmt_005fmessage_005f36.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5997 */         out.write(32);
/* 5998 */         if (_jspx_meth_fmt_005fparam_005f14(_jspx_th_fmt_005fmessage_005f36, _jspx_page_context))
/* 5999 */           return true;
/* 6000 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f36.doAfterBody();
/* 6001 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6004 */       if (_jspx_eval_fmt_005fmessage_005f36 != 1) {
/* 6005 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6008 */     if (_jspx_th_fmt_005fmessage_005f36.doEndTag() == 5) {
/* 6009 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f36);
/* 6010 */       return true;
/*      */     }
/* 6012 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f36);
/* 6013 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f14(JspTag _jspx_th_fmt_005fmessage_005f36, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6018 */     PageContext pageContext = _jspx_page_context;
/* 6019 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6021 */     ParamTag _jspx_th_fmt_005fparam_005f14 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 6022 */     _jspx_th_fmt_005fparam_005f14.setPageContext(_jspx_page_context);
/* 6023 */     _jspx_th_fmt_005fparam_005f14.setParent((Tag)_jspx_th_fmt_005fmessage_005f36);
/* 6024 */     int _jspx_eval_fmt_005fparam_005f14 = _jspx_th_fmt_005fparam_005f14.doStartTag();
/* 6025 */     if (_jspx_eval_fmt_005fparam_005f14 != 0) {
/* 6026 */       if (_jspx_eval_fmt_005fparam_005f14 != 1) {
/* 6027 */         out = _jspx_page_context.pushBody();
/* 6028 */         _jspx_th_fmt_005fparam_005f14.setBodyContent((BodyContent)out);
/* 6029 */         _jspx_th_fmt_005fparam_005f14.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6032 */         if (_jspx_meth_c_005fout_005f30(_jspx_th_fmt_005fparam_005f14, _jspx_page_context))
/* 6033 */           return true;
/* 6034 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f14.doAfterBody();
/* 6035 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6038 */       if (_jspx_eval_fmt_005fparam_005f14 != 1) {
/* 6039 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6042 */     if (_jspx_th_fmt_005fparam_005f14.doEndTag() == 5) {
/* 6043 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f14);
/* 6044 */       return true;
/*      */     }
/* 6046 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f14);
/* 6047 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_fmt_005fparam_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6052 */     PageContext pageContext = _jspx_page_context;
/* 6053 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6055 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6056 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 6057 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_fmt_005fparam_005f14);
/*      */     
/* 6059 */     _jspx_th_c_005fout_005f30.setValue("${templatetypestr}");
/* 6060 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 6061 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 6062 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 6063 */       return true;
/*      */     }
/* 6065 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 6066 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f37(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6071 */     PageContext pageContext = _jspx_page_context;
/* 6072 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6074 */     MessageTag _jspx_th_fmt_005fmessage_005f37 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6075 */     _jspx_th_fmt_005fmessage_005f37.setPageContext(_jspx_page_context);
/* 6076 */     _jspx_th_fmt_005fmessage_005f37.setParent((Tag)_jspx_th_c_005fif_005f5);
/* 6077 */     int _jspx_eval_fmt_005fmessage_005f37 = _jspx_th_fmt_005fmessage_005f37.doStartTag();
/* 6078 */     if (_jspx_eval_fmt_005fmessage_005f37 != 0) {
/* 6079 */       if (_jspx_eval_fmt_005fmessage_005f37 != 1) {
/* 6080 */         out = _jspx_page_context.pushBody();
/* 6081 */         _jspx_th_fmt_005fmessage_005f37.setBodyContent((BodyContent)out);
/* 6082 */         _jspx_th_fmt_005fmessage_005f37.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6085 */         out.write("am.webclient.processtemplate.processcmd");
/* 6086 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f37.doAfterBody();
/* 6087 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6090 */       if (_jspx_eval_fmt_005fmessage_005f37 != 1) {
/* 6091 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6094 */     if (_jspx_th_fmt_005fmessage_005f37.doEndTag() == 5) {
/* 6095 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f37);
/* 6096 */       return true;
/*      */     }
/* 6098 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f37);
/* 6099 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6104 */     PageContext pageContext = _jspx_page_context;
/* 6105 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6107 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6108 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 6109 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6111 */     _jspx_th_c_005fif_005f6.setTest("${templatetype == SERVICETEMPLATE}");
/* 6112 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 6113 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 6115 */         out.write("\n<tr>\n<td colspan=\"4\"><div id=\"showrow\" style=\"display:none\">\n<table width=\"100%\" id=\"processtablehead\" border=\"0\" cellspacing=\"0\" >\n<tr class=\"whitegrayrightalign\" width=\"100%\">\n\t<td width=\"4%\" class=\"bodytextbold whitegrayrightalign\"><input type=\"checkbox\" name=\"processcheckbox\"  checked value='' onclick=\"javascript:fnSelectAll(this);\"/></td>\n    <td width=\"40%\" style=\"height:10px;\" class=\"bodytextbold whitegrayrightalign\" >");
/* 6116 */         if (_jspx_meth_fmt_005fmessage_005f38(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 6117 */           return true;
/* 6118 */         out.write("</td> ");
/* 6119 */         out.write("\n    <td width=\"50%\" style=\"height:10px;\" class=\"bodytextbold whitegrayrightalign\" >");
/* 6120 */         if (_jspx_meth_fmt_005fmessage_005f39(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 6121 */           return true;
/* 6122 */         out.write("</td> ");
/* 6123 */         out.write("\n    <td width=\"6%\" style=\"height:10px;\" class=\"bodytextbold whitegrayrightalign\">&nbsp;</td>\n</tr>\n</table>\n</div>\n</td>\n</tr>\n");
/* 6124 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 6125 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6129 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 6130 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 6131 */       return true;
/*      */     }
/* 6133 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 6134 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f38(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6139 */     PageContext pageContext = _jspx_page_context;
/* 6140 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6142 */     MessageTag _jspx_th_fmt_005fmessage_005f38 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6143 */     _jspx_th_fmt_005fmessage_005f38.setPageContext(_jspx_page_context);
/* 6144 */     _jspx_th_fmt_005fmessage_005f38.setParent((Tag)_jspx_th_c_005fif_005f6);
/* 6145 */     int _jspx_eval_fmt_005fmessage_005f38 = _jspx_th_fmt_005fmessage_005f38.doStartTag();
/* 6146 */     if (_jspx_eval_fmt_005fmessage_005f38 != 0) {
/* 6147 */       if (_jspx_eval_fmt_005fmessage_005f38 != 1) {
/* 6148 */         out = _jspx_page_context.pushBody();
/* 6149 */         _jspx_th_fmt_005fmessage_005f38.setBodyContent((BodyContent)out);
/* 6150 */         _jspx_th_fmt_005fmessage_005f38.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6153 */         out.write("am.webclient.templatetype.servicedname");
/* 6154 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f38.doAfterBody();
/* 6155 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6158 */       if (_jspx_eval_fmt_005fmessage_005f38 != 1) {
/* 6159 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6162 */     if (_jspx_th_fmt_005fmessage_005f38.doEndTag() == 5) {
/* 6163 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f38);
/* 6164 */       return true;
/*      */     }
/* 6166 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f38);
/* 6167 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f39(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6172 */     PageContext pageContext = _jspx_page_context;
/* 6173 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6175 */     MessageTag _jspx_th_fmt_005fmessage_005f39 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 6176 */     _jspx_th_fmt_005fmessage_005f39.setPageContext(_jspx_page_context);
/* 6177 */     _jspx_th_fmt_005fmessage_005f39.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6179 */     _jspx_th_fmt_005fmessage_005f39.setKey("am.webclient.processtemplate.processname");
/* 6180 */     int _jspx_eval_fmt_005fmessage_005f39 = _jspx_th_fmt_005fmessage_005f39.doStartTag();
/* 6181 */     if (_jspx_eval_fmt_005fmessage_005f39 != 0) {
/* 6182 */       if (_jspx_eval_fmt_005fmessage_005f39 != 1) {
/* 6183 */         out = _jspx_page_context.pushBody();
/* 6184 */         _jspx_th_fmt_005fmessage_005f39.setBodyContent((BodyContent)out);
/* 6185 */         _jspx_th_fmt_005fmessage_005f39.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6188 */         out.write(32);
/* 6189 */         if (_jspx_meth_fmt_005fparam_005f15(_jspx_th_fmt_005fmessage_005f39, _jspx_page_context))
/* 6190 */           return true;
/* 6191 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f39.doAfterBody();
/* 6192 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6195 */       if (_jspx_eval_fmt_005fmessage_005f39 != 1) {
/* 6196 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6199 */     if (_jspx_th_fmt_005fmessage_005f39.doEndTag() == 5) {
/* 6200 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f39);
/* 6201 */       return true;
/*      */     }
/* 6203 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f39);
/* 6204 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f15(JspTag _jspx_th_fmt_005fmessage_005f39, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6209 */     PageContext pageContext = _jspx_page_context;
/* 6210 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6212 */     ParamTag _jspx_th_fmt_005fparam_005f15 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 6213 */     _jspx_th_fmt_005fparam_005f15.setPageContext(_jspx_page_context);
/* 6214 */     _jspx_th_fmt_005fparam_005f15.setParent((Tag)_jspx_th_fmt_005fmessage_005f39);
/* 6215 */     int _jspx_eval_fmt_005fparam_005f15 = _jspx_th_fmt_005fparam_005f15.doStartTag();
/* 6216 */     if (_jspx_eval_fmt_005fparam_005f15 != 0) {
/* 6217 */       if (_jspx_eval_fmt_005fparam_005f15 != 1) {
/* 6218 */         out = _jspx_page_context.pushBody();
/* 6219 */         _jspx_th_fmt_005fparam_005f15.setBodyContent((BodyContent)out);
/* 6220 */         _jspx_th_fmt_005fparam_005f15.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6223 */         if (_jspx_meth_c_005fout_005f31(_jspx_th_fmt_005fparam_005f15, _jspx_page_context))
/* 6224 */           return true;
/* 6225 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f15.doAfterBody();
/* 6226 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6229 */       if (_jspx_eval_fmt_005fparam_005f15 != 1) {
/* 6230 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6233 */     if (_jspx_th_fmt_005fparam_005f15.doEndTag() == 5) {
/* 6234 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f15);
/* 6235 */       return true;
/*      */     }
/* 6237 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f15);
/* 6238 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_fmt_005fparam_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6243 */     PageContext pageContext = _jspx_page_context;
/* 6244 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6246 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6247 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 6248 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_fmt_005fparam_005f15);
/*      */     
/* 6250 */     _jspx_th_c_005fout_005f31.setValue("${templatetypestr}");
/* 6251 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 6252 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 6253 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 6254 */       return true;
/*      */     }
/* 6256 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 6257 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6262 */     PageContext pageContext = _jspx_page_context;
/* 6263 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6265 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6266 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 6267 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6269 */     _jspx_th_c_005fif_005f7.setTest("${not empty cacheprocessid}");
/* 6270 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 6271 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 6273 */         out.write("\n<script>showDiv(\"showrow\")</script>\n");
/* 6274 */         if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 6275 */           return true;
/* 6276 */         out.write("\n</table>\n");
/* 6277 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 6278 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6282 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 6283 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 6284 */       return true;
/*      */     }
/* 6286 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 6287 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6292 */     PageContext pageContext = _jspx_page_context;
/* 6293 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6295 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 6296 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 6297 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 6299 */     _jspx_th_c_005fforEach_005f0.setItems("${cacheprocessid}");
/*      */     
/* 6301 */     _jspx_th_c_005fforEach_005f0.setVar("processidrow");
/*      */     
/* 6303 */     _jspx_th_c_005fforEach_005f0.setVarStatus("rowstatus");
/* 6304 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 6306 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 6307 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 6309 */           out.write("\n<tr>\n");
/* 6310 */           boolean bool; if (_jspx_meth_c_005fchoose_005f8(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6311 */             return true;
/* 6312 */           out.write(10);
/* 6313 */           out.write(32);
/* 6314 */           out.write(9);
/* 6315 */           if (_jspx_meth_c_005fif_005f8(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6316 */             return true;
/* 6317 */           out.write("\n    ");
/* 6318 */           if (_jspx_meth_c_005fif_005f9(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6319 */             return true;
/* 6320 */           out.write("\n</tr>\n");
/* 6321 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 6322 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 6326 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 6327 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 6330 */         int tmp294_293 = 0; int[] tmp294_291 = _jspx_push_body_count_c_005fforEach_005f0; int tmp296_295 = tmp294_291[tmp294_293];tmp294_291[tmp294_293] = (tmp296_295 - 1); if (tmp296_295 <= 0) break;
/* 6331 */         out = _jspx_page_context.popBody(); }
/* 6332 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 6334 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 6335 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 6337 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f8(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6342 */     PageContext pageContext = _jspx_page_context;
/* 6343 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6345 */     ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 6346 */     _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 6347 */     _jspx_th_c_005fchoose_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/* 6348 */     int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 6349 */     if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */       for (;;) {
/* 6351 */         out.write("\n\n        ");
/* 6352 */         if (_jspx_meth_c_005fwhen_005f8(_jspx_th_c_005fchoose_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6353 */           return true;
/* 6354 */         if (_jspx_meth_c_005fotherwise_005f8(_jspx_th_c_005fchoose_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6355 */           return true;
/* 6356 */         out.write(10);
/* 6357 */         out.write(32);
/* 6358 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 6359 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6363 */     if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 6364 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 6365 */       return true;
/*      */     }
/* 6367 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 6368 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f8(JspTag _jspx_th_c_005fchoose_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6373 */     PageContext pageContext = _jspx_page_context;
/* 6374 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6376 */     WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 6377 */     _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 6378 */     _jspx_th_c_005fwhen_005f8.setParent((Tag)_jspx_th_c_005fchoose_005f8);
/*      */     
/* 6380 */     _jspx_th_c_005fwhen_005f8.setTest("${rowstatus.count%2 == 0}");
/* 6381 */     int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 6382 */     if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */       for (;;) {
/* 6384 */         out.write("\n        ");
/* 6385 */         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fwhen_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6386 */           return true;
/* 6387 */         out.write("\n        ");
/* 6388 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 6389 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6393 */     if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 6394 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 6395 */       return true;
/*      */     }
/* 6397 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 6398 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fwhen_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6403 */     PageContext pageContext = _jspx_page_context;
/* 6404 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6406 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 6407 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 6408 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f8);
/*      */     
/* 6410 */     _jspx_th_c_005fset_005f2.setVar("bgcolor");
/*      */     
/* 6412 */     _jspx_th_c_005fset_005f2.setScope("request");
/*      */     
/* 6414 */     _jspx_th_c_005fset_005f2.setValue("whitegrayrightalign");
/* 6415 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 6416 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 6417 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 6418 */       return true;
/*      */     }
/* 6420 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 6421 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f8(JspTag _jspx_th_c_005fchoose_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6426 */     PageContext pageContext = _jspx_page_context;
/* 6427 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6429 */     OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 6430 */     _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 6431 */     _jspx_th_c_005fotherwise_005f8.setParent((Tag)_jspx_th_c_005fchoose_005f8);
/* 6432 */     int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 6433 */     if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */       for (;;) {
/* 6435 */         out.write("\n        ");
/* 6436 */         if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fotherwise_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6437 */           return true;
/* 6438 */         out.write("\n        ");
/* 6439 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 6440 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6444 */     if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 6445 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 6446 */       return true;
/*      */     }
/* 6448 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 6449 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fotherwise_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6454 */     PageContext pageContext = _jspx_page_context;
/* 6455 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6457 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 6458 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 6459 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f8);
/*      */     
/* 6461 */     _jspx_th_c_005fset_005f3.setVar("bgcolor");
/*      */     
/* 6463 */     _jspx_th_c_005fset_005f3.setScope("request");
/*      */     
/* 6465 */     _jspx_th_c_005fset_005f3.setValue("whitegrayrightalign");
/* 6466 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 6467 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 6468 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f3);
/* 6469 */       return true;
/*      */     }
/* 6471 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f3);
/* 6472 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6477 */     PageContext pageContext = _jspx_page_context;
/* 6478 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6480 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6481 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 6482 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6484 */     _jspx_th_c_005fif_005f8.setTest("${templatetype == PROCESSTEMPLATE}");
/* 6485 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 6486 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 6488 */         out.write("\n\t<td width=\"4%\" style=\"height:28px;\"  class='");
/* 6489 */         if (_jspx_meth_c_005fout_005f32(_jspx_th_c_005fif_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6490 */           return true;
/* 6491 */         out.write("'><input type=\"checkbox\" id=\"processcheckbox\" name=\"processcheckbox\"  value='");
/* 6492 */         if (_jspx_meth_c_005fout_005f33(_jspx_th_c_005fif_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6493 */           return true;
/* 6494 */         out.write("' checked='checked' onclick=\"javascript:controlAllServSelCheckbox()\"/></td>\n    <td width=\"21%\" id=\"pdispnamecell_");
/* 6495 */         if (_jspx_meth_c_005fout_005f34(_jspx_th_c_005fif_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6496 */           return true;
/* 6497 */         out.write("\" class='");
/* 6498 */         if (_jspx_meth_c_005fout_005f35(_jspx_th_c_005fif_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6499 */           return true;
/* 6500 */         out.write(39);
/* 6501 */         out.write(32);
/* 6502 */         out.write(62);
/* 6503 */         if (_jspx_meth_c_005fout_005f36(_jspx_th_c_005fif_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6504 */           return true;
/* 6505 */         out.write("</td>\n    <td width=\"21%\" id=\"pnamecell_");
/* 6506 */         if (_jspx_meth_c_005fout_005f37(_jspx_th_c_005fif_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6507 */           return true;
/* 6508 */         out.write("\" class='");
/* 6509 */         if (_jspx_meth_c_005fout_005f38(_jspx_th_c_005fif_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6510 */           return true;
/* 6511 */         out.write(39);
/* 6512 */         out.write(32);
/* 6513 */         out.write(62);
/* 6514 */         if (_jspx_meth_c_005fout_005f39(_jspx_th_c_005fif_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6515 */           return true;
/* 6516 */         out.write("</td>\n    <td  width=\"50%\" id=\"pcmdcell_");
/* 6517 */         if (_jspx_meth_c_005fout_005f40(_jspx_th_c_005fif_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6518 */           return true;
/* 6519 */         out.write("\" class='");
/* 6520 */         if (_jspx_meth_c_005fout_005f41(_jspx_th_c_005fif_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6521 */           return true;
/* 6522 */         out.write(" apm-breakword' >");
/* 6523 */         if (_jspx_meth_c_005fchoose_005f9(_jspx_th_c_005fif_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6524 */           return true;
/* 6525 */         out.write("</td>\n   \t<td width=\"4%\" class='");
/* 6526 */         if (_jspx_meth_c_005fout_005f43(_jspx_th_c_005fif_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6527 */           return true;
/* 6528 */         out.write("' align=\"center\"> <a href='javascript:popupEditProcess(");
/* 6529 */         if (_jspx_meth_c_005fout_005f44(_jspx_th_c_005fif_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6530 */           return true;
/* 6531 */         out.write(",true)'><img src=\"/images/icon_edit.gif\"  border=\"0\"></a></td>\n    ");
/* 6532 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 6533 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6537 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 6538 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 6539 */       return true;
/*      */     }
/* 6541 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 6542 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6547 */     PageContext pageContext = _jspx_page_context;
/* 6548 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6550 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6551 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 6552 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 6554 */     _jspx_th_c_005fout_005f32.setValue("${bgcolor}");
/* 6555 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 6556 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 6557 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 6558 */       return true;
/*      */     }
/* 6560 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 6561 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6566 */     PageContext pageContext = _jspx_page_context;
/* 6567 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6569 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6570 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 6571 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 6573 */     _jspx_th_c_005fout_005f33.setValue("${processidrow.key}");
/* 6574 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 6575 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 6576 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 6577 */       return true;
/*      */     }
/* 6579 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 6580 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6585 */     PageContext pageContext = _jspx_page_context;
/* 6586 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6588 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6589 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 6590 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 6592 */     _jspx_th_c_005fout_005f34.setValue("${processidrow.key}");
/* 6593 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 6594 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 6595 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 6596 */       return true;
/*      */     }
/* 6598 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 6599 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6604 */     PageContext pageContext = _jspx_page_context;
/* 6605 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6607 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6608 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 6609 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 6611 */     _jspx_th_c_005fout_005f35.setValue("${bgcolor}");
/* 6612 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 6613 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 6614 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 6615 */       return true;
/*      */     }
/* 6617 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 6618 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6623 */     PageContext pageContext = _jspx_page_context;
/* 6624 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6626 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6627 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 6628 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 6630 */     _jspx_th_c_005fout_005f36.setValue("${processidrow.value[5]}");
/* 6631 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 6632 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 6633 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 6634 */       return true;
/*      */     }
/* 6636 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 6637 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6642 */     PageContext pageContext = _jspx_page_context;
/* 6643 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6645 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6646 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 6647 */     _jspx_th_c_005fout_005f37.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 6649 */     _jspx_th_c_005fout_005f37.setValue("${processidrow.key}");
/* 6650 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 6651 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 6652 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 6653 */       return true;
/*      */     }
/* 6655 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 6656 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6661 */     PageContext pageContext = _jspx_page_context;
/* 6662 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6664 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6665 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 6666 */     _jspx_th_c_005fout_005f38.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 6668 */     _jspx_th_c_005fout_005f38.setValue("${bgcolor}");
/* 6669 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 6670 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 6671 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 6672 */       return true;
/*      */     }
/* 6674 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 6675 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6680 */     PageContext pageContext = _jspx_page_context;
/* 6681 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6683 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6684 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 6685 */     _jspx_th_c_005fout_005f39.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 6687 */     _jspx_th_c_005fout_005f39.setValue("${processidrow.value[0]}");
/* 6688 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 6689 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 6690 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 6691 */       return true;
/*      */     }
/* 6693 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 6694 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f40(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6699 */     PageContext pageContext = _jspx_page_context;
/* 6700 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6702 */     OutTag _jspx_th_c_005fout_005f40 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6703 */     _jspx_th_c_005fout_005f40.setPageContext(_jspx_page_context);
/* 6704 */     _jspx_th_c_005fout_005f40.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 6706 */     _jspx_th_c_005fout_005f40.setValue("${processidrow.key}");
/* 6707 */     int _jspx_eval_c_005fout_005f40 = _jspx_th_c_005fout_005f40.doStartTag();
/* 6708 */     if (_jspx_th_c_005fout_005f40.doEndTag() == 5) {
/* 6709 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 6710 */       return true;
/*      */     }
/* 6712 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 6713 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f41(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6718 */     PageContext pageContext = _jspx_page_context;
/* 6719 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6721 */     OutTag _jspx_th_c_005fout_005f41 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6722 */     _jspx_th_c_005fout_005f41.setPageContext(_jspx_page_context);
/* 6723 */     _jspx_th_c_005fout_005f41.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 6725 */     _jspx_th_c_005fout_005f41.setValue("${bgcolor}");
/* 6726 */     int _jspx_eval_c_005fout_005f41 = _jspx_th_c_005fout_005f41.doStartTag();
/* 6727 */     if (_jspx_th_c_005fout_005f41.doEndTag() == 5) {
/* 6728 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 6729 */       return true;
/*      */     }
/* 6731 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 6732 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f9(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6737 */     PageContext pageContext = _jspx_page_context;
/* 6738 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6740 */     ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 6741 */     _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 6742 */     _jspx_th_c_005fchoose_005f9.setParent((Tag)_jspx_th_c_005fif_005f8);
/* 6743 */     int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 6744 */     if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */       for (;;) {
/* 6746 */         if (_jspx_meth_c_005fwhen_005f9(_jspx_th_c_005fchoose_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6747 */           return true;
/* 6748 */         if (_jspx_meth_c_005fotherwise_005f9(_jspx_th_c_005fchoose_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6749 */           return true;
/* 6750 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 6751 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6755 */     if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 6756 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 6757 */       return true;
/*      */     }
/* 6759 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 6760 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f9(JspTag _jspx_th_c_005fchoose_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6765 */     PageContext pageContext = _jspx_page_context;
/* 6766 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6768 */     WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 6769 */     _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 6770 */     _jspx_th_c_005fwhen_005f9.setParent((Tag)_jspx_th_c_005fchoose_005f9);
/*      */     
/* 6772 */     _jspx_th_c_005fwhen_005f9.setTest("${empty processidrow.value[1]}");
/* 6773 */     int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 6774 */     if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */       for (;;) {
/* 6776 */         out.write("&nbsp;");
/* 6777 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 6778 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6782 */     if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 6783 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 6784 */       return true;
/*      */     }
/* 6786 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 6787 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f9(JspTag _jspx_th_c_005fchoose_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6792 */     PageContext pageContext = _jspx_page_context;
/* 6793 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6795 */     OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 6796 */     _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 6797 */     _jspx_th_c_005fotherwise_005f9.setParent((Tag)_jspx_th_c_005fchoose_005f9);
/* 6798 */     int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 6799 */     if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */       for (;;) {
/* 6801 */         if (_jspx_meth_c_005fout_005f42(_jspx_th_c_005fotherwise_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6802 */           return true;
/* 6803 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 6804 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6808 */     if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 6809 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 6810 */       return true;
/*      */     }
/* 6812 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 6813 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f42(JspTag _jspx_th_c_005fotherwise_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6818 */     PageContext pageContext = _jspx_page_context;
/* 6819 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6821 */     OutTag _jspx_th_c_005fout_005f42 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6822 */     _jspx_th_c_005fout_005f42.setPageContext(_jspx_page_context);
/* 6823 */     _jspx_th_c_005fout_005f42.setParent((Tag)_jspx_th_c_005fotherwise_005f9);
/*      */     
/* 6825 */     _jspx_th_c_005fout_005f42.setValue("${processidrow.value[1]}");
/* 6826 */     int _jspx_eval_c_005fout_005f42 = _jspx_th_c_005fout_005f42.doStartTag();
/* 6827 */     if (_jspx_th_c_005fout_005f42.doEndTag() == 5) {
/* 6828 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 6829 */       return true;
/*      */     }
/* 6831 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 6832 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f43(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6837 */     PageContext pageContext = _jspx_page_context;
/* 6838 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6840 */     OutTag _jspx_th_c_005fout_005f43 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6841 */     _jspx_th_c_005fout_005f43.setPageContext(_jspx_page_context);
/* 6842 */     _jspx_th_c_005fout_005f43.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 6844 */     _jspx_th_c_005fout_005f43.setValue("${bgcolor}");
/* 6845 */     int _jspx_eval_c_005fout_005f43 = _jspx_th_c_005fout_005f43.doStartTag();
/* 6846 */     if (_jspx_th_c_005fout_005f43.doEndTag() == 5) {
/* 6847 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 6848 */       return true;
/*      */     }
/* 6850 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 6851 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f44(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6856 */     PageContext pageContext = _jspx_page_context;
/* 6857 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6859 */     OutTag _jspx_th_c_005fout_005f44 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6860 */     _jspx_th_c_005fout_005f44.setPageContext(_jspx_page_context);
/* 6861 */     _jspx_th_c_005fout_005f44.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 6863 */     _jspx_th_c_005fout_005f44.setValue("${processidrow.key}");
/* 6864 */     int _jspx_eval_c_005fout_005f44 = _jspx_th_c_005fout_005f44.doStartTag();
/* 6865 */     if (_jspx_th_c_005fout_005f44.doEndTag() == 5) {
/* 6866 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 6867 */       return true;
/*      */     }
/* 6869 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 6870 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6875 */     PageContext pageContext = _jspx_page_context;
/* 6876 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6878 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6879 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 6880 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6882 */     _jspx_th_c_005fif_005f9.setTest("${templatetype == SERVICETEMPLATE}");
/* 6883 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 6884 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 6886 */         out.write("\n\t<td width=\"4%\" style=\"height:28px;\"  class='");
/* 6887 */         if (_jspx_meth_c_005fout_005f45(_jspx_th_c_005fif_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6888 */           return true;
/* 6889 */         out.write("'><input type=\"checkbox\" id=\"processcheckbox\" name=\"processcheckbox\"  value='");
/* 6890 */         if (_jspx_meth_c_005fout_005f46(_jspx_th_c_005fif_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6891 */           return true;
/* 6892 */         out.write("' checked='checked' onclick=\"javascript:controlAllServSelCheckbox()\"/></td>\n    <td width=\"40%\" id=\"pnamecell_");
/* 6893 */         if (_jspx_meth_c_005fout_005f47(_jspx_th_c_005fif_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6894 */           return true;
/* 6895 */         out.write("\" class='");
/* 6896 */         if (_jspx_meth_c_005fout_005f48(_jspx_th_c_005fif_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6897 */           return true;
/* 6898 */         out.write(39);
/* 6899 */         out.write(32);
/* 6900 */         out.write(62);
/* 6901 */         if (_jspx_meth_c_005fout_005f49(_jspx_th_c_005fif_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6902 */           return true;
/* 6903 */         out.write("</td>\n    <td  width=\"50%\" id=\"pcmdcell_");
/* 6904 */         if (_jspx_meth_c_005fout_005f50(_jspx_th_c_005fif_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6905 */           return true;
/* 6906 */         out.write("\" class='");
/* 6907 */         if (_jspx_meth_c_005fout_005f51(_jspx_th_c_005fif_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6908 */           return true;
/* 6909 */         out.write(" apm-breakword' >");
/* 6910 */         if (_jspx_meth_c_005fchoose_005f10(_jspx_th_c_005fif_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6911 */           return true;
/* 6912 */         out.write("</td>\n\t<td width=\"6%\" class='");
/* 6913 */         if (_jspx_meth_c_005fout_005f53(_jspx_th_c_005fif_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6914 */           return true;
/* 6915 */         out.write("' align=\"center\"> <a href='javascript:popupEditProcess(");
/* 6916 */         if (_jspx_meth_c_005fout_005f54(_jspx_th_c_005fif_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6917 */           return true;
/* 6918 */         out.write(",true)'><img src=\"/images/icon_edit.gif\"  border=\"0\"></a></td>\n\t");
/* 6919 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 6920 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6924 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 6925 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 6926 */       return true;
/*      */     }
/* 6928 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 6929 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f45(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6934 */     PageContext pageContext = _jspx_page_context;
/* 6935 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6937 */     OutTag _jspx_th_c_005fout_005f45 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6938 */     _jspx_th_c_005fout_005f45.setPageContext(_jspx_page_context);
/* 6939 */     _jspx_th_c_005fout_005f45.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 6941 */     _jspx_th_c_005fout_005f45.setValue("${bgcolor}");
/* 6942 */     int _jspx_eval_c_005fout_005f45 = _jspx_th_c_005fout_005f45.doStartTag();
/* 6943 */     if (_jspx_th_c_005fout_005f45.doEndTag() == 5) {
/* 6944 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 6945 */       return true;
/*      */     }
/* 6947 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 6948 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f46(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6953 */     PageContext pageContext = _jspx_page_context;
/* 6954 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6956 */     OutTag _jspx_th_c_005fout_005f46 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6957 */     _jspx_th_c_005fout_005f46.setPageContext(_jspx_page_context);
/* 6958 */     _jspx_th_c_005fout_005f46.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 6960 */     _jspx_th_c_005fout_005f46.setValue("${processidrow.key}");
/* 6961 */     int _jspx_eval_c_005fout_005f46 = _jspx_th_c_005fout_005f46.doStartTag();
/* 6962 */     if (_jspx_th_c_005fout_005f46.doEndTag() == 5) {
/* 6963 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 6964 */       return true;
/*      */     }
/* 6966 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 6967 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f47(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6972 */     PageContext pageContext = _jspx_page_context;
/* 6973 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6975 */     OutTag _jspx_th_c_005fout_005f47 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6976 */     _jspx_th_c_005fout_005f47.setPageContext(_jspx_page_context);
/* 6977 */     _jspx_th_c_005fout_005f47.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 6979 */     _jspx_th_c_005fout_005f47.setValue("${processidrow.key}");
/* 6980 */     int _jspx_eval_c_005fout_005f47 = _jspx_th_c_005fout_005f47.doStartTag();
/* 6981 */     if (_jspx_th_c_005fout_005f47.doEndTag() == 5) {
/* 6982 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 6983 */       return true;
/*      */     }
/* 6985 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 6986 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f48(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6991 */     PageContext pageContext = _jspx_page_context;
/* 6992 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6994 */     OutTag _jspx_th_c_005fout_005f48 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6995 */     _jspx_th_c_005fout_005f48.setPageContext(_jspx_page_context);
/* 6996 */     _jspx_th_c_005fout_005f48.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 6998 */     _jspx_th_c_005fout_005f48.setValue("${bgcolor}");
/* 6999 */     int _jspx_eval_c_005fout_005f48 = _jspx_th_c_005fout_005f48.doStartTag();
/* 7000 */     if (_jspx_th_c_005fout_005f48.doEndTag() == 5) {
/* 7001 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 7002 */       return true;
/*      */     }
/* 7004 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 7005 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f49(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7010 */     PageContext pageContext = _jspx_page_context;
/* 7011 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7013 */     OutTag _jspx_th_c_005fout_005f49 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7014 */     _jspx_th_c_005fout_005f49.setPageContext(_jspx_page_context);
/* 7015 */     _jspx_th_c_005fout_005f49.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 7017 */     _jspx_th_c_005fout_005f49.setValue("${processidrow.value[0]}");
/* 7018 */     int _jspx_eval_c_005fout_005f49 = _jspx_th_c_005fout_005f49.doStartTag();
/* 7019 */     if (_jspx_th_c_005fout_005f49.doEndTag() == 5) {
/* 7020 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 7021 */       return true;
/*      */     }
/* 7023 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 7024 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f50(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7029 */     PageContext pageContext = _jspx_page_context;
/* 7030 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7032 */     OutTag _jspx_th_c_005fout_005f50 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7033 */     _jspx_th_c_005fout_005f50.setPageContext(_jspx_page_context);
/* 7034 */     _jspx_th_c_005fout_005f50.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 7036 */     _jspx_th_c_005fout_005f50.setValue("${processidrow.key}");
/* 7037 */     int _jspx_eval_c_005fout_005f50 = _jspx_th_c_005fout_005f50.doStartTag();
/* 7038 */     if (_jspx_th_c_005fout_005f50.doEndTag() == 5) {
/* 7039 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f50);
/* 7040 */       return true;
/*      */     }
/* 7042 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f50);
/* 7043 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f51(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7048 */     PageContext pageContext = _jspx_page_context;
/* 7049 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7051 */     OutTag _jspx_th_c_005fout_005f51 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7052 */     _jspx_th_c_005fout_005f51.setPageContext(_jspx_page_context);
/* 7053 */     _jspx_th_c_005fout_005f51.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 7055 */     _jspx_th_c_005fout_005f51.setValue("${bgcolor}");
/* 7056 */     int _jspx_eval_c_005fout_005f51 = _jspx_th_c_005fout_005f51.doStartTag();
/* 7057 */     if (_jspx_th_c_005fout_005f51.doEndTag() == 5) {
/* 7058 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 7059 */       return true;
/*      */     }
/* 7061 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 7062 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f10(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7067 */     PageContext pageContext = _jspx_page_context;
/* 7068 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7070 */     ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 7071 */     _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 7072 */     _jspx_th_c_005fchoose_005f10.setParent((Tag)_jspx_th_c_005fif_005f9);
/* 7073 */     int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 7074 */     if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */       for (;;) {
/* 7076 */         if (_jspx_meth_c_005fwhen_005f10(_jspx_th_c_005fchoose_005f10, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 7077 */           return true;
/* 7078 */         if (_jspx_meth_c_005fotherwise_005f10(_jspx_th_c_005fchoose_005f10, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 7079 */           return true;
/* 7080 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 7081 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7085 */     if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 7086 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 7087 */       return true;
/*      */     }
/* 7089 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 7090 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f10(JspTag _jspx_th_c_005fchoose_005f10, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7095 */     PageContext pageContext = _jspx_page_context;
/* 7096 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7098 */     WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 7099 */     _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 7100 */     _jspx_th_c_005fwhen_005f10.setParent((Tag)_jspx_th_c_005fchoose_005f10);
/*      */     
/* 7102 */     _jspx_th_c_005fwhen_005f10.setTest("${empty processidrow.value[1]}");
/* 7103 */     int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 7104 */     if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */       for (;;) {
/* 7106 */         out.write("&nbsp;");
/* 7107 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 7108 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7112 */     if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 7113 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 7114 */       return true;
/*      */     }
/* 7116 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 7117 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f10(JspTag _jspx_th_c_005fchoose_005f10, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7122 */     PageContext pageContext = _jspx_page_context;
/* 7123 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7125 */     OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 7126 */     _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 7127 */     _jspx_th_c_005fotherwise_005f10.setParent((Tag)_jspx_th_c_005fchoose_005f10);
/* 7128 */     int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 7129 */     if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */       for (;;) {
/* 7131 */         if (_jspx_meth_c_005fout_005f52(_jspx_th_c_005fotherwise_005f10, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 7132 */           return true;
/* 7133 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 7134 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7138 */     if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 7139 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 7140 */       return true;
/*      */     }
/* 7142 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 7143 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f52(JspTag _jspx_th_c_005fotherwise_005f10, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7148 */     PageContext pageContext = _jspx_page_context;
/* 7149 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7151 */     OutTag _jspx_th_c_005fout_005f52 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7152 */     _jspx_th_c_005fout_005f52.setPageContext(_jspx_page_context);
/* 7153 */     _jspx_th_c_005fout_005f52.setParent((Tag)_jspx_th_c_005fotherwise_005f10);
/*      */     
/* 7155 */     _jspx_th_c_005fout_005f52.setValue("${processidrow.value[1]}");
/* 7156 */     int _jspx_eval_c_005fout_005f52 = _jspx_th_c_005fout_005f52.doStartTag();
/* 7157 */     if (_jspx_th_c_005fout_005f52.doEndTag() == 5) {
/* 7158 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 7159 */       return true;
/*      */     }
/* 7161 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 7162 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f53(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7167 */     PageContext pageContext = _jspx_page_context;
/* 7168 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7170 */     OutTag _jspx_th_c_005fout_005f53 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7171 */     _jspx_th_c_005fout_005f53.setPageContext(_jspx_page_context);
/* 7172 */     _jspx_th_c_005fout_005f53.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 7174 */     _jspx_th_c_005fout_005f53.setValue("${bgcolor}");
/* 7175 */     int _jspx_eval_c_005fout_005f53 = _jspx_th_c_005fout_005f53.doStartTag();
/* 7176 */     if (_jspx_th_c_005fout_005f53.doEndTag() == 5) {
/* 7177 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f53);
/* 7178 */       return true;
/*      */     }
/* 7180 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f53);
/* 7181 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f54(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7186 */     PageContext pageContext = _jspx_page_context;
/* 7187 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7189 */     OutTag _jspx_th_c_005fout_005f54 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7190 */     _jspx_th_c_005fout_005f54.setPageContext(_jspx_page_context);
/* 7191 */     _jspx_th_c_005fout_005f54.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 7193 */     _jspx_th_c_005fout_005f54.setValue("${processidrow.key}");
/* 7194 */     int _jspx_eval_c_005fout_005f54 = _jspx_th_c_005fout_005f54.doStartTag();
/* 7195 */     if (_jspx_th_c_005fout_005f54.doEndTag() == 5) {
/* 7196 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f54);
/* 7197 */       return true;
/*      */     }
/* 7199 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f54);
/* 7200 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f40(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7205 */     PageContext pageContext = _jspx_page_context;
/* 7206 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7208 */     MessageTag _jspx_th_fmt_005fmessage_005f40 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 7209 */     _jspx_th_fmt_005fmessage_005f40.setPageContext(_jspx_page_context);
/* 7210 */     _jspx_th_fmt_005fmessage_005f40.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7212 */     _jspx_th_fmt_005fmessage_005f40.setKey("am.webclient.processtemplate.noprocessmsg");
/* 7213 */     int _jspx_eval_fmt_005fmessage_005f40 = _jspx_th_fmt_005fmessage_005f40.doStartTag();
/* 7214 */     if (_jspx_eval_fmt_005fmessage_005f40 != 0) {
/* 7215 */       if (_jspx_eval_fmt_005fmessage_005f40 != 1) {
/* 7216 */         out = _jspx_page_context.pushBody();
/* 7217 */         _jspx_th_fmt_005fmessage_005f40.setBodyContent((BodyContent)out);
/* 7218 */         _jspx_th_fmt_005fmessage_005f40.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7221 */         if (_jspx_meth_fmt_005fparam_005f16(_jspx_th_fmt_005fmessage_005f40, _jspx_page_context))
/* 7222 */           return true;
/* 7223 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f40.doAfterBody();
/* 7224 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7227 */       if (_jspx_eval_fmt_005fmessage_005f40 != 1) {
/* 7228 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7231 */     if (_jspx_th_fmt_005fmessage_005f40.doEndTag() == 5) {
/* 7232 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f40);
/* 7233 */       return true;
/*      */     }
/* 7235 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f40);
/* 7236 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f16(JspTag _jspx_th_fmt_005fmessage_005f40, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7241 */     PageContext pageContext = _jspx_page_context;
/* 7242 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7244 */     ParamTag _jspx_th_fmt_005fparam_005f16 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 7245 */     _jspx_th_fmt_005fparam_005f16.setPageContext(_jspx_page_context);
/* 7246 */     _jspx_th_fmt_005fparam_005f16.setParent((Tag)_jspx_th_fmt_005fmessage_005f40);
/* 7247 */     int _jspx_eval_fmt_005fparam_005f16 = _jspx_th_fmt_005fparam_005f16.doStartTag();
/* 7248 */     if (_jspx_eval_fmt_005fparam_005f16 != 0) {
/* 7249 */       if (_jspx_eval_fmt_005fparam_005f16 != 1) {
/* 7250 */         out = _jspx_page_context.pushBody();
/* 7251 */         _jspx_th_fmt_005fparam_005f16.setBodyContent((BodyContent)out);
/* 7252 */         _jspx_th_fmt_005fparam_005f16.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7255 */         if (_jspx_meth_c_005fout_005f55(_jspx_th_fmt_005fparam_005f16, _jspx_page_context))
/* 7256 */           return true;
/* 7257 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f16.doAfterBody();
/* 7258 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7261 */       if (_jspx_eval_fmt_005fparam_005f16 != 1) {
/* 7262 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7265 */     if (_jspx_th_fmt_005fparam_005f16.doEndTag() == 5) {
/* 7266 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f16);
/* 7267 */       return true;
/*      */     }
/* 7269 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f16);
/* 7270 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f55(JspTag _jspx_th_fmt_005fparam_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7275 */     PageContext pageContext = _jspx_page_context;
/* 7276 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7278 */     OutTag _jspx_th_c_005fout_005f55 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7279 */     _jspx_th_c_005fout_005f55.setPageContext(_jspx_page_context);
/* 7280 */     _jspx_th_c_005fout_005f55.setParent((Tag)_jspx_th_fmt_005fparam_005f16);
/*      */     
/* 7282 */     _jspx_th_c_005fout_005f55.setValue("${templatetypestr}");
/* 7283 */     int _jspx_eval_c_005fout_005f55 = _jspx_th_c_005fout_005f55.doStartTag();
/* 7284 */     if (_jspx_th_c_005fout_005f55.doEndTag() == 5) {
/* 7285 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f55);
/* 7286 */       return true;
/*      */     }
/* 7288 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f55);
/* 7289 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f41(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7294 */     PageContext pageContext = _jspx_page_context;
/* 7295 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7297 */     MessageTag _jspx_th_fmt_005fmessage_005f41 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 7298 */     _jspx_th_fmt_005fmessage_005f41.setPageContext(_jspx_page_context);
/* 7299 */     _jspx_th_fmt_005fmessage_005f41.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7301 */     _jspx_th_fmt_005fmessage_005f41.setKey("am.webclient.processtemplate.addprocess");
/* 7302 */     int _jspx_eval_fmt_005fmessage_005f41 = _jspx_th_fmt_005fmessage_005f41.doStartTag();
/* 7303 */     if (_jspx_eval_fmt_005fmessage_005f41 != 0) {
/* 7304 */       if (_jspx_eval_fmt_005fmessage_005f41 != 1) {
/* 7305 */         out = _jspx_page_context.pushBody();
/* 7306 */         _jspx_th_fmt_005fmessage_005f41.setBodyContent((BodyContent)out);
/* 7307 */         _jspx_th_fmt_005fmessage_005f41.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7310 */         if (_jspx_meth_fmt_005fparam_005f17(_jspx_th_fmt_005fmessage_005f41, _jspx_page_context))
/* 7311 */           return true;
/* 7312 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f41.doAfterBody();
/* 7313 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7316 */       if (_jspx_eval_fmt_005fmessage_005f41 != 1) {
/* 7317 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7320 */     if (_jspx_th_fmt_005fmessage_005f41.doEndTag() == 5) {
/* 7321 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f41);
/* 7322 */       return true;
/*      */     }
/* 7324 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f41);
/* 7325 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f17(JspTag _jspx_th_fmt_005fmessage_005f41, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7330 */     PageContext pageContext = _jspx_page_context;
/* 7331 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7333 */     ParamTag _jspx_th_fmt_005fparam_005f17 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 7334 */     _jspx_th_fmt_005fparam_005f17.setPageContext(_jspx_page_context);
/* 7335 */     _jspx_th_fmt_005fparam_005f17.setParent((Tag)_jspx_th_fmt_005fmessage_005f41);
/* 7336 */     int _jspx_eval_fmt_005fparam_005f17 = _jspx_th_fmt_005fparam_005f17.doStartTag();
/* 7337 */     if (_jspx_eval_fmt_005fparam_005f17 != 0) {
/* 7338 */       if (_jspx_eval_fmt_005fparam_005f17 != 1) {
/* 7339 */         out = _jspx_page_context.pushBody();
/* 7340 */         _jspx_th_fmt_005fparam_005f17.setBodyContent((BodyContent)out);
/* 7341 */         _jspx_th_fmt_005fparam_005f17.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7344 */         if (_jspx_meth_c_005fout_005f56(_jspx_th_fmt_005fparam_005f17, _jspx_page_context))
/* 7345 */           return true;
/* 7346 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f17.doAfterBody();
/* 7347 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7350 */       if (_jspx_eval_fmt_005fparam_005f17 != 1) {
/* 7351 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7354 */     if (_jspx_th_fmt_005fparam_005f17.doEndTag() == 5) {
/* 7355 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f17);
/* 7356 */       return true;
/*      */     }
/* 7358 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f17);
/* 7359 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f56(JspTag _jspx_th_fmt_005fparam_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7364 */     PageContext pageContext = _jspx_page_context;
/* 7365 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7367 */     OutTag _jspx_th_c_005fout_005f56 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7368 */     _jspx_th_c_005fout_005f56.setPageContext(_jspx_page_context);
/* 7369 */     _jspx_th_c_005fout_005f56.setParent((Tag)_jspx_th_fmt_005fparam_005f17);
/*      */     
/* 7371 */     _jspx_th_c_005fout_005f56.setValue("${templatetypestr}");
/* 7372 */     int _jspx_eval_c_005fout_005f56 = _jspx_th_c_005fout_005f56.doStartTag();
/* 7373 */     if (_jspx_th_c_005fout_005f56.doEndTag() == 5) {
/* 7374 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f56);
/* 7375 */       return true;
/*      */     }
/* 7377 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f56);
/* 7378 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7383 */     PageContext pageContext = _jspx_page_context;
/* 7384 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7386 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7387 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 7388 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7390 */     _jspx_th_c_005fif_005f10.setTest("${empty cacheprocessid}");
/* 7391 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 7392 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 7394 */         out.write("\n<script>showDiv(\"noprocessdiv\")</script>\n");
/* 7395 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 7396 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7400 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 7401 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 7402 */       return true;
/*      */     }
/* 7404 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 7405 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f42(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7410 */     PageContext pageContext = _jspx_page_context;
/* 7411 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7413 */     MessageTag _jspx_th_fmt_005fmessage_005f42 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 7414 */     _jspx_th_fmt_005fmessage_005f42.setPageContext(_jspx_page_context);
/* 7415 */     _jspx_th_fmt_005fmessage_005f42.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7417 */     _jspx_th_fmt_005fmessage_005f42.setKey("am.webclient.processtemplate.processconftxt");
/* 7418 */     int _jspx_eval_fmt_005fmessage_005f42 = _jspx_th_fmt_005fmessage_005f42.doStartTag();
/* 7419 */     if (_jspx_eval_fmt_005fmessage_005f42 != 0) {
/* 7420 */       if (_jspx_eval_fmt_005fmessage_005f42 != 1) {
/* 7421 */         out = _jspx_page_context.pushBody();
/* 7422 */         _jspx_th_fmt_005fmessage_005f42.setBodyContent((BodyContent)out);
/* 7423 */         _jspx_th_fmt_005fmessage_005f42.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7426 */         if (_jspx_meth_fmt_005fparam_005f18(_jspx_th_fmt_005fmessage_005f42, _jspx_page_context))
/* 7427 */           return true;
/* 7428 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f42.doAfterBody();
/* 7429 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7432 */       if (_jspx_eval_fmt_005fmessage_005f42 != 1) {
/* 7433 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7436 */     if (_jspx_th_fmt_005fmessage_005f42.doEndTag() == 5) {
/* 7437 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f42);
/* 7438 */       return true;
/*      */     }
/* 7440 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f42);
/* 7441 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f18(JspTag _jspx_th_fmt_005fmessage_005f42, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7446 */     PageContext pageContext = _jspx_page_context;
/* 7447 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7449 */     ParamTag _jspx_th_fmt_005fparam_005f18 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 7450 */     _jspx_th_fmt_005fparam_005f18.setPageContext(_jspx_page_context);
/* 7451 */     _jspx_th_fmt_005fparam_005f18.setParent((Tag)_jspx_th_fmt_005fmessage_005f42);
/* 7452 */     int _jspx_eval_fmt_005fparam_005f18 = _jspx_th_fmt_005fparam_005f18.doStartTag();
/* 7453 */     if (_jspx_eval_fmt_005fparam_005f18 != 0) {
/* 7454 */       if (_jspx_eval_fmt_005fparam_005f18 != 1) {
/* 7455 */         out = _jspx_page_context.pushBody();
/* 7456 */         _jspx_th_fmt_005fparam_005f18.setBodyContent((BodyContent)out);
/* 7457 */         _jspx_th_fmt_005fparam_005f18.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7460 */         if (_jspx_meth_c_005fout_005f57(_jspx_th_fmt_005fparam_005f18, _jspx_page_context))
/* 7461 */           return true;
/* 7462 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f18.doAfterBody();
/* 7463 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7466 */       if (_jspx_eval_fmt_005fparam_005f18 != 1) {
/* 7467 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7470 */     if (_jspx_th_fmt_005fparam_005f18.doEndTag() == 5) {
/* 7471 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f18);
/* 7472 */       return true;
/*      */     }
/* 7474 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f18);
/* 7475 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f57(JspTag _jspx_th_fmt_005fparam_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7480 */     PageContext pageContext = _jspx_page_context;
/* 7481 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7483 */     OutTag _jspx_th_c_005fout_005f57 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7484 */     _jspx_th_c_005fout_005f57.setPageContext(_jspx_page_context);
/* 7485 */     _jspx_th_c_005fout_005f57.setParent((Tag)_jspx_th_fmt_005fparam_005f18);
/*      */     
/* 7487 */     _jspx_th_c_005fout_005f57.setValue("${upcasepsmsg}");
/* 7488 */     int _jspx_eval_c_005fout_005f57 = _jspx_th_c_005fout_005f57.doStartTag();
/* 7489 */     if (_jspx_th_c_005fout_005f57.doEndTag() == 5) {
/* 7490 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f57);
/* 7491 */       return true;
/*      */     }
/* 7493 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f57);
/* 7494 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f43(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7499 */     PageContext pageContext = _jspx_page_context;
/* 7500 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7502 */     MessageTag _jspx_th_fmt_005fmessage_005f43 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 7503 */     _jspx_th_fmt_005fmessage_005f43.setPageContext(_jspx_page_context);
/* 7504 */     _jspx_th_fmt_005fmessage_005f43.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7506 */     _jspx_th_fmt_005fmessage_005f43.setKey("am.webclient.processtemplate.processconfigurationhelpcard");
/* 7507 */     int _jspx_eval_fmt_005fmessage_005f43 = _jspx_th_fmt_005fmessage_005f43.doStartTag();
/* 7508 */     if (_jspx_eval_fmt_005fmessage_005f43 != 0) {
/* 7509 */       if (_jspx_eval_fmt_005fmessage_005f43 != 1) {
/* 7510 */         out = _jspx_page_context.pushBody();
/* 7511 */         _jspx_th_fmt_005fmessage_005f43.setBodyContent((BodyContent)out);
/* 7512 */         _jspx_th_fmt_005fmessage_005f43.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7515 */         if (_jspx_meth_fmt_005fparam_005f19(_jspx_th_fmt_005fmessage_005f43, _jspx_page_context))
/* 7516 */           return true;
/* 7517 */         out.write(32);
/* 7518 */         if (_jspx_meth_fmt_005fparam_005f20(_jspx_th_fmt_005fmessage_005f43, _jspx_page_context))
/* 7519 */           return true;
/* 7520 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f43.doAfterBody();
/* 7521 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7524 */       if (_jspx_eval_fmt_005fmessage_005f43 != 1) {
/* 7525 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7528 */     if (_jspx_th_fmt_005fmessage_005f43.doEndTag() == 5) {
/* 7529 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f43);
/* 7530 */       return true;
/*      */     }
/* 7532 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f43);
/* 7533 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f19(JspTag _jspx_th_fmt_005fmessage_005f43, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7538 */     PageContext pageContext = _jspx_page_context;
/* 7539 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7541 */     ParamTag _jspx_th_fmt_005fparam_005f19 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 7542 */     _jspx_th_fmt_005fparam_005f19.setPageContext(_jspx_page_context);
/* 7543 */     _jspx_th_fmt_005fparam_005f19.setParent((Tag)_jspx_th_fmt_005fmessage_005f43);
/* 7544 */     int _jspx_eval_fmt_005fparam_005f19 = _jspx_th_fmt_005fparam_005f19.doStartTag();
/* 7545 */     if (_jspx_eval_fmt_005fparam_005f19 != 0) {
/* 7546 */       if (_jspx_eval_fmt_005fparam_005f19 != 1) {
/* 7547 */         out = _jspx_page_context.pushBody();
/* 7548 */         _jspx_th_fmt_005fparam_005f19.setBodyContent((BodyContent)out);
/* 7549 */         _jspx_th_fmt_005fparam_005f19.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7552 */         if (_jspx_meth_c_005fout_005f58(_jspx_th_fmt_005fparam_005f19, _jspx_page_context))
/* 7553 */           return true;
/* 7554 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f19.doAfterBody();
/* 7555 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7558 */       if (_jspx_eval_fmt_005fparam_005f19 != 1) {
/* 7559 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7562 */     if (_jspx_th_fmt_005fparam_005f19.doEndTag() == 5) {
/* 7563 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f19);
/* 7564 */       return true;
/*      */     }
/* 7566 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f19);
/* 7567 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f58(JspTag _jspx_th_fmt_005fparam_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7572 */     PageContext pageContext = _jspx_page_context;
/* 7573 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7575 */     OutTag _jspx_th_c_005fout_005f58 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7576 */     _jspx_th_c_005fout_005f58.setPageContext(_jspx_page_context);
/* 7577 */     _jspx_th_c_005fout_005f58.setParent((Tag)_jspx_th_fmt_005fparam_005f19);
/*      */     
/* 7579 */     _jspx_th_c_005fout_005f58.setValue("${lowercasemultipsmsg}");
/* 7580 */     int _jspx_eval_c_005fout_005f58 = _jspx_th_c_005fout_005f58.doStartTag();
/* 7581 */     if (_jspx_th_c_005fout_005f58.doEndTag() == 5) {
/* 7582 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f58);
/* 7583 */       return true;
/*      */     }
/* 7585 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f58);
/* 7586 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f20(JspTag _jspx_th_fmt_005fmessage_005f43, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7591 */     PageContext pageContext = _jspx_page_context;
/* 7592 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7594 */     ParamTag _jspx_th_fmt_005fparam_005f20 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 7595 */     _jspx_th_fmt_005fparam_005f20.setPageContext(_jspx_page_context);
/* 7596 */     _jspx_th_fmt_005fparam_005f20.setParent((Tag)_jspx_th_fmt_005fmessage_005f43);
/* 7597 */     int _jspx_eval_fmt_005fparam_005f20 = _jspx_th_fmt_005fparam_005f20.doStartTag();
/* 7598 */     if (_jspx_eval_fmt_005fparam_005f20 != 0) {
/* 7599 */       if (_jspx_eval_fmt_005fparam_005f20 != 1) {
/* 7600 */         out = _jspx_page_context.pushBody();
/* 7601 */         _jspx_th_fmt_005fparam_005f20.setBodyContent((BodyContent)out);
/* 7602 */         _jspx_th_fmt_005fparam_005f20.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7605 */         if (_jspx_meth_c_005fout_005f59(_jspx_th_fmt_005fparam_005f20, _jspx_page_context))
/* 7606 */           return true;
/* 7607 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f20.doAfterBody();
/* 7608 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7611 */       if (_jspx_eval_fmt_005fparam_005f20 != 1) {
/* 7612 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7615 */     if (_jspx_th_fmt_005fparam_005f20.doEndTag() == 5) {
/* 7616 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f20);
/* 7617 */       return true;
/*      */     }
/* 7619 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f20);
/* 7620 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f59(JspTag _jspx_th_fmt_005fparam_005f20, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7625 */     PageContext pageContext = _jspx_page_context;
/* 7626 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7628 */     OutTag _jspx_th_c_005fout_005f59 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7629 */     _jspx_th_c_005fout_005f59.setPageContext(_jspx_page_context);
/* 7630 */     _jspx_th_c_005fout_005f59.setParent((Tag)_jspx_th_fmt_005fparam_005f20);
/*      */     
/* 7632 */     _jspx_th_c_005fout_005f59.setValue("${lowercasepsmsg}");
/* 7633 */     int _jspx_eval_c_005fout_005f59 = _jspx_th_c_005fout_005f59.doStartTag();
/* 7634 */     if (_jspx_th_c_005fout_005f59.doEndTag() == 5) {
/* 7635 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f59);
/* 7636 */       return true;
/*      */     }
/* 7638 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f59);
/* 7639 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fimport_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7644 */     PageContext pageContext = _jspx_page_context;
/* 7645 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7647 */     ImportTag _jspx_th_c_005fimport_005f0 = (ImportTag)this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody.get(ImportTag.class);
/* 7648 */     _jspx_th_c_005fimport_005f0.setPageContext(_jspx_page_context);
/* 7649 */     _jspx_th_c_005fimport_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7651 */     _jspx_th_c_005fimport_005f0.setUrl("/jsp/TemplateThresholdList.jsp");
/* 7652 */     int[] _jspx_push_body_count_c_005fimport_005f0 = { 0 };
/*      */     try {
/* 7654 */       int _jspx_eval_c_005fimport_005f0 = _jspx_th_c_005fimport_005f0.doStartTag();
/* 7655 */       if (_jspx_th_c_005fimport_005f0.doEndTag() == 5)
/* 7656 */         return true;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 7659 */         int tmp113_112 = 0; int[] tmp113_110 = _jspx_push_body_count_c_005fimport_005f0; int tmp115_114 = tmp113_110[tmp113_112];tmp113_110[tmp113_112] = (tmp115_114 - 1); if (tmp115_114 <= 0) break;
/* 7660 */         out = _jspx_page_context.popBody(); }
/* 7661 */       _jspx_th_c_005fimport_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 7663 */       _jspx_th_c_005fimport_005f0.doFinally();
/* 7664 */       this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody.reuse(_jspx_th_c_005fimport_005f0);
/*      */     }
/* 7666 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f44(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7671 */     PageContext pageContext = _jspx_page_context;
/* 7672 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7674 */     MessageTag _jspx_th_fmt_005fmessage_005f44 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7675 */     _jspx_th_fmt_005fmessage_005f44.setPageContext(_jspx_page_context);
/* 7676 */     _jspx_th_fmt_005fmessage_005f44.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7678 */     _jspx_th_fmt_005fmessage_005f44.setKey("am.webclient.configurealert.configurealertsforattribute");
/* 7679 */     int _jspx_eval_fmt_005fmessage_005f44 = _jspx_th_fmt_005fmessage_005f44.doStartTag();
/* 7680 */     if (_jspx_th_fmt_005fmessage_005f44.doEndTag() == 5) {
/* 7681 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f44);
/* 7682 */       return true;
/*      */     }
/* 7684 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f44);
/* 7685 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7690 */     PageContext pageContext = _jspx_page_context;
/* 7691 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7693 */     ChooseTag _jspx_th_c_005fchoose_005f11 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 7694 */     _jspx_th_c_005fchoose_005f11.setPageContext(_jspx_page_context);
/* 7695 */     _jspx_th_c_005fchoose_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 7696 */     int _jspx_eval_c_005fchoose_005f11 = _jspx_th_c_005fchoose_005f11.doStartTag();
/* 7697 */     if (_jspx_eval_c_005fchoose_005f11 != 0) {
/*      */       for (;;) {
/* 7699 */         if (_jspx_meth_c_005fwhen_005f11(_jspx_th_c_005fchoose_005f11, _jspx_page_context))
/* 7700 */           return true;
/* 7701 */         if (_jspx_meth_c_005fotherwise_005f11(_jspx_th_c_005fchoose_005f11, _jspx_page_context))
/* 7702 */           return true;
/* 7703 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f11.doAfterBody();
/* 7704 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7708 */     if (_jspx_th_c_005fchoose_005f11.doEndTag() == 5) {
/* 7709 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11);
/* 7710 */       return true;
/*      */     }
/* 7712 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11);
/* 7713 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f11(JspTag _jspx_th_c_005fchoose_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7718 */     PageContext pageContext = _jspx_page_context;
/* 7719 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7721 */     WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 7722 */     _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 7723 */     _jspx_th_c_005fwhen_005f11.setParent((Tag)_jspx_th_c_005fchoose_005f11);
/*      */     
/* 7725 */     _jspx_th_c_005fwhen_005f11.setTest("${templatetype == PROCESSTEMPLATE}");
/* 7726 */     int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 7727 */     if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */       for (;;) {
/* 7729 */         if (_jspx_meth_fmt_005fmessage_005f45(_jspx_th_c_005fwhen_005f11, _jspx_page_context))
/* 7730 */           return true;
/* 7731 */         out.write(10);
/* 7732 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 7733 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7737 */     if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 7738 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 7739 */       return true;
/*      */     }
/* 7741 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 7742 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f45(JspTag _jspx_th_c_005fwhen_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7747 */     PageContext pageContext = _jspx_page_context;
/* 7748 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7750 */     MessageTag _jspx_th_fmt_005fmessage_005f45 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7751 */     _jspx_th_fmt_005fmessage_005f45.setPageContext(_jspx_page_context);
/* 7752 */     _jspx_th_fmt_005fmessage_005f45.setParent((Tag)_jspx_th_c_005fwhen_005f11);
/*      */     
/* 7754 */     _jspx_th_fmt_005fmessage_005f45.setKey("am.webclient.processtemplate.thresholdconfhelpcard");
/* 7755 */     int _jspx_eval_fmt_005fmessage_005f45 = _jspx_th_fmt_005fmessage_005f45.doStartTag();
/* 7756 */     if (_jspx_th_fmt_005fmessage_005f45.doEndTag() == 5) {
/* 7757 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f45);
/* 7758 */       return true;
/*      */     }
/* 7760 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f45);
/* 7761 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f11(JspTag _jspx_th_c_005fchoose_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7766 */     PageContext pageContext = _jspx_page_context;
/* 7767 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7769 */     OtherwiseTag _jspx_th_c_005fotherwise_005f11 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 7770 */     _jspx_th_c_005fotherwise_005f11.setPageContext(_jspx_page_context);
/* 7771 */     _jspx_th_c_005fotherwise_005f11.setParent((Tag)_jspx_th_c_005fchoose_005f11);
/* 7772 */     int _jspx_eval_c_005fotherwise_005f11 = _jspx_th_c_005fotherwise_005f11.doStartTag();
/* 7773 */     if (_jspx_eval_c_005fotherwise_005f11 != 0) {
/*      */       for (;;) {
/* 7775 */         if (_jspx_meth_fmt_005fmessage_005f46(_jspx_th_c_005fotherwise_005f11, _jspx_page_context))
/* 7776 */           return true;
/* 7777 */         out.write(10);
/* 7778 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f11.doAfterBody();
/* 7779 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7783 */     if (_jspx_th_c_005fotherwise_005f11.doEndTag() == 5) {
/* 7784 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11);
/* 7785 */       return true;
/*      */     }
/* 7787 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11);
/* 7788 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f46(JspTag _jspx_th_c_005fotherwise_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7793 */     PageContext pageContext = _jspx_page_context;
/* 7794 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7796 */     MessageTag _jspx_th_fmt_005fmessage_005f46 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7797 */     _jspx_th_fmt_005fmessage_005f46.setPageContext(_jspx_page_context);
/* 7798 */     _jspx_th_fmt_005fmessage_005f46.setParent((Tag)_jspx_th_c_005fotherwise_005f11);
/*      */     
/* 7800 */     _jspx_th_fmt_005fmessage_005f46.setKey("am.webclient.servicetemplate.thresholdconfhelpcard");
/* 7801 */     int _jspx_eval_fmt_005fmessage_005f46 = _jspx_th_fmt_005fmessage_005f46.doStartTag();
/* 7802 */     if (_jspx_th_fmt_005fmessage_005f46.doEndTag() == 5) {
/* 7803 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f46);
/* 7804 */       return true;
/*      */     }
/* 7806 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f46);
/* 7807 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fimport_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7812 */     PageContext pageContext = _jspx_page_context;
/* 7813 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7815 */     ImportTag _jspx_th_c_005fimport_005f1 = (ImportTag)this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody.get(ImportTag.class);
/* 7816 */     _jspx_th_c_005fimport_005f1.setPageContext(_jspx_page_context);
/* 7817 */     _jspx_th_c_005fimport_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7819 */     _jspx_th_c_005fimport_005f1.setUrl("/jsp/MonitorGroupsTypesChooser.jsp");
/* 7820 */     int[] _jspx_push_body_count_c_005fimport_005f1 = { 0 };
/*      */     try {
/* 7822 */       int _jspx_eval_c_005fimport_005f1 = _jspx_th_c_005fimport_005f1.doStartTag();
/* 7823 */       if (_jspx_th_c_005fimport_005f1.doEndTag() == 5)
/* 7824 */         return true;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 7827 */         int tmp113_112 = 0; int[] tmp113_110 = _jspx_push_body_count_c_005fimport_005f1; int tmp115_114 = tmp113_110[tmp113_112];tmp113_110[tmp113_112] = (tmp115_114 - 1); if (tmp115_114 <= 0) break;
/* 7828 */         out = _jspx_page_context.popBody(); }
/* 7829 */       _jspx_th_c_005fimport_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 7831 */       _jspx_th_c_005fimport_005f1.doFinally();
/* 7832 */       this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody.reuse(_jspx_th_c_005fimport_005f1);
/*      */     }
/* 7834 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f47(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7839 */     PageContext pageContext = _jspx_page_context;
/* 7840 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7842 */     MessageTag _jspx_th_fmt_005fmessage_005f47 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7843 */     _jspx_th_fmt_005fmessage_005f47.setPageContext(_jspx_page_context);
/* 7844 */     _jspx_th_fmt_005fmessage_005f47.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7846 */     _jspx_th_fmt_005fmessage_005f47.setKey("am.webclient.processtemplate.associatemonitors");
/* 7847 */     int _jspx_eval_fmt_005fmessage_005f47 = _jspx_th_fmt_005fmessage_005f47.doStartTag();
/* 7848 */     if (_jspx_th_fmt_005fmessage_005f47.doEndTag() == 5) {
/* 7849 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f47);
/* 7850 */       return true;
/*      */     }
/* 7852 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f47);
/* 7853 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f48(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7858 */     PageContext pageContext = _jspx_page_context;
/* 7859 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7861 */     MessageTag _jspx_th_fmt_005fmessage_005f48 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 7862 */     _jspx_th_fmt_005fmessage_005f48.setPageContext(_jspx_page_context);
/* 7863 */     _jspx_th_fmt_005fmessage_005f48.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7865 */     _jspx_th_fmt_005fmessage_005f48.setKey("am.webclient.processtemplate.monitorassnhelpcard");
/* 7866 */     int _jspx_eval_fmt_005fmessage_005f48 = _jspx_th_fmt_005fmessage_005f48.doStartTag();
/* 7867 */     if (_jspx_eval_fmt_005fmessage_005f48 != 0) {
/* 7868 */       if (_jspx_eval_fmt_005fmessage_005f48 != 1) {
/* 7869 */         out = _jspx_page_context.pushBody();
/* 7870 */         _jspx_th_fmt_005fmessage_005f48.setBodyContent((BodyContent)out);
/* 7871 */         _jspx_th_fmt_005fmessage_005f48.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7874 */         if (_jspx_meth_fmt_005fparam_005f21(_jspx_th_fmt_005fmessage_005f48, _jspx_page_context))
/* 7875 */           return true;
/* 7876 */         out.write(10);
/* 7877 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f48.doAfterBody();
/* 7878 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7881 */       if (_jspx_eval_fmt_005fmessage_005f48 != 1) {
/* 7882 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7885 */     if (_jspx_th_fmt_005fmessage_005f48.doEndTag() == 5) {
/* 7886 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f48);
/* 7887 */       return true;
/*      */     }
/* 7889 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f48);
/* 7890 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f21(JspTag _jspx_th_fmt_005fmessage_005f48, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7895 */     PageContext pageContext = _jspx_page_context;
/* 7896 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7898 */     ParamTag _jspx_th_fmt_005fparam_005f21 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 7899 */     _jspx_th_fmt_005fparam_005f21.setPageContext(_jspx_page_context);
/* 7900 */     _jspx_th_fmt_005fparam_005f21.setParent((Tag)_jspx_th_fmt_005fmessage_005f48);
/* 7901 */     int _jspx_eval_fmt_005fparam_005f21 = _jspx_th_fmt_005fparam_005f21.doStartTag();
/* 7902 */     if (_jspx_eval_fmt_005fparam_005f21 != 0) {
/* 7903 */       if (_jspx_eval_fmt_005fparam_005f21 != 1) {
/* 7904 */         out = _jspx_page_context.pushBody();
/* 7905 */         _jspx_th_fmt_005fparam_005f21.setBodyContent((BodyContent)out);
/* 7906 */         _jspx_th_fmt_005fparam_005f21.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7909 */         if (_jspx_meth_c_005fout_005f60(_jspx_th_fmt_005fparam_005f21, _jspx_page_context))
/* 7910 */           return true;
/* 7911 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f21.doAfterBody();
/* 7912 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7915 */       if (_jspx_eval_fmt_005fparam_005f21 != 1) {
/* 7916 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7919 */     if (_jspx_th_fmt_005fparam_005f21.doEndTag() == 5) {
/* 7920 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f21);
/* 7921 */       return true;
/*      */     }
/* 7923 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f21);
/* 7924 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f60(JspTag _jspx_th_fmt_005fparam_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7929 */     PageContext pageContext = _jspx_page_context;
/* 7930 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7932 */     OutTag _jspx_th_c_005fout_005f60 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7933 */     _jspx_th_c_005fout_005f60.setPageContext(_jspx_page_context);
/* 7934 */     _jspx_th_c_005fout_005f60.setParent((Tag)_jspx_th_fmt_005fparam_005f21);
/*      */     
/* 7936 */     _jspx_th_c_005fout_005f60.setValue("${lowercasemultipsmsg}");
/* 7937 */     int _jspx_eval_c_005fout_005f60 = _jspx_th_c_005fout_005f60.doStartTag();
/* 7938 */     if (_jspx_th_c_005fout_005f60.doEndTag() == 5) {
/* 7939 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f60);
/* 7940 */       return true;
/*      */     }
/* 7942 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f60);
/* 7943 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\CreateProcessTemplate_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */