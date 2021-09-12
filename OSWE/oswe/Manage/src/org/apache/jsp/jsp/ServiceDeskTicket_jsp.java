/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
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
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.html.TextareaTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class ServiceDeskTicket_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/* 1470 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
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
/* 1981 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
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
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
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
/* 2180 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2181 */   static { _jspx_dependants.put("/jsp/includes/AdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/* 2182 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyle_005frows_005fproperty_005fcols_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2209 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2213 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyle_005frows_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2233 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2237 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2238 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2239 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2240 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2242 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2244 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2245 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2247 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/* 2248 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2249 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty.release();
/* 2250 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/* 2251 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody.release();
/* 2252 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyle_005frows_005fproperty_005fcols_005fnobody.release();
/* 2253 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/* 2254 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.release();
/* 2255 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2262 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2265 */     JspWriter out = null;
/* 2266 */     Object page = this;
/* 2267 */     JspWriter _jspx_out = null;
/* 2268 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2272 */       response.setContentType("text/html;charset=UTF-8");
/* 2273 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2275 */       _jspx_page_context = pageContext;
/* 2276 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2277 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2278 */       session = pageContext.getSession();
/* 2279 */       out = pageContext.getOut();
/* 2280 */       _jspx_out = out;
/*      */       
/* 2282 */       out.write("<!DOCTYPE html>\n");
/* 2283 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2284 */       out.write(10);
/*      */       
/* 2286 */       request.setAttribute("HelpKey", "ServiceDesk Settings");
/*      */       
/* 2288 */       out.write("\n\n\n\n\n\n\n\n");
/* 2289 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2291 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2292 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2293 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2295 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2297 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2299 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2301 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2302 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2303 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2304 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2307 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2308 */         String available = null;
/* 2309 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2310 */         out.write(10);
/*      */         
/* 2312 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2313 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2314 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2316 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2318 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2320 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2322 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2323 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2324 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2325 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2328 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2329 */           String unavailable = null;
/* 2330 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2331 */           out.write(10);
/*      */           
/* 2333 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2334 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2335 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2337 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2339 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2341 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2343 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2344 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2345 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2346 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2349 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2350 */             String unmanaged = null;
/* 2351 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2352 */             out.write(10);
/*      */             
/* 2354 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2355 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2356 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2358 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2360 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2362 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2364 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2365 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2366 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2367 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2370 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2371 */               String scheduled = null;
/* 2372 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2373 */               out.write(10);
/*      */               
/* 2375 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2376 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2377 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2379 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2381 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2383 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2385 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2386 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2387 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2388 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2391 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2392 */                 String critical = null;
/* 2393 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2394 */                 out.write(10);
/*      */                 
/* 2396 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2397 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2398 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2400 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2402 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2404 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2406 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2407 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2408 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2409 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2412 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2413 */                   String clear = null;
/* 2414 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2415 */                   out.write(10);
/*      */                   
/* 2417 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2418 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2419 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2421 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2423 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2425 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2427 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2428 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2429 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2430 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2433 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2434 */                     String warning = null;
/* 2435 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2436 */                     out.write(10);
/* 2437 */                     out.write(10);
/*      */                     
/* 2439 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2440 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2442 */                     out.write(10);
/* 2443 */                     out.write(10);
/* 2444 */                     out.write(10);
/* 2445 */                     out.write(10);
/* 2446 */                     out.write(10);
/*      */                     
/* 2448 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2449 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2450 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2452 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayout.jsp");
/* 2453 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2454 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2456 */                         out.write(10);
/* 2457 */                         if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2459 */                         out.write(10);
/* 2460 */                         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2462 */                         out.write(10);
/*      */                         
/* 2464 */                         PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2465 */                         _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2466 */                         _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2468 */                         _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */                         
/* 2470 */                         _jspx_th_tiles_005fput_005f2.setType("string");
/* 2471 */                         int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2472 */                         if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2473 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2474 */                             out = _jspx_page_context.pushBody();
/* 2475 */                             _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2476 */                             _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2479 */                             out.write("\n\n<script language=\"JavaScript1.2\">\nfunction fnFormSubmit()\n{\n    if(trimAll(document.AMActionForm.displayname.value)=='')\n\t{\n\t\talert(\"Please Enter Profile Name\");\n\t\tdocument.AMActionForm.displayname.focus();\n\t\treturn false;\n\t}\n\tdocument.AMActionForm.submit();\n}\n\nfunction addTitle(x)\n{\n if(x=='0')\n {\n\tvar titleAlarm=document.AMActionForm.titleList.selectedIndex;\n\tif(titleAlarm!=0)\n\t {\n       document.AMActionForm.subject.value=document.AMActionForm.subject.value+\"  \"+document.AMActionForm.titleList.value;\n\t } \n }\n else\n {\n   var descAlarm=document.AMActionForm.descList.selectedIndex;\n   if(descAlarm!=0)\n   {\n     document.AMActionForm.message.value=document.AMActionForm.message.value+\"\\n\"+document.AMActionForm.descList.value; \n   }\n }\n}\n\n</script>\n\n");
/* 2480 */                             out.write("<!--$Id$-->\n\n\n\n\n\n");
/*      */                             
/*      */ 
/* 2483 */                             String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */                             
/* 2485 */                             out.write("\n<script language=\"JavaScript\" type=\"text/JavaScript\">\t\nfunction Call()\n{\n alert(\"");
/* 2486 */                             out.print(FormatUtil.getString("wizard.disabled"));
/* 2487 */                             out.write("\");\n}\n</script>\n    \n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr> \n    <td height=\"21\"  class=\"leftlinksheading\">");
/* 2488 */                             out.print(FormatUtil.getString("am.webclient.admin.heading"));
/* 2489 */                             out.write("</td>\n  </tr>\n  \n ");
/*      */                             
/* 2491 */                             if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                             {
/*      */ 
/* 2494 */                               out.write("  \n  <tr>\n\n  ");
/*      */                               
/* 2496 */                               if (request.getParameter("wiz") != null)
/*      */                               {
/* 2498 */                                 out.write("\n\t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\" title=\"");
/* 2499 */                                 out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 2500 */                                 out.write("\" class='disabledlink'>");
/* 2501 */                                 out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 2502 */                                 out.write("</a></td>\n  ");
/*      */                               }
/*      */                               else
/*      */                               {
/* 2506 */                                 out.write("\n\t<td class=\"leftlinkstd\" >\n");
/*      */                                 
/* 2508 */                                 ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2509 */                                 _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2510 */                                 _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/* 2511 */                                 int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2512 */                                 if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                                   for (;;) {
/* 2514 */                                     out.write(10);
/*      */                                     
/* 2516 */                                     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2517 */                                     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2518 */                                     _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                                     
/* 2520 */                                     _jspx_th_c_005fwhen_005f0.setTest("${uri !='/jsp/CreateApplication.jsp' && uri !='/admin/createapplication.do' && uri!='/admin/createapplicationwiz.do'}");
/* 2521 */                                     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2522 */                                     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                                       for (;;) {
/* 2524 */                                         out.write("    \n            <a href=\"/admin/createapplication.do?method=createapp&grouptype=1\" class=\"new-left-links\" access=\"110\">\n              ");
/* 2525 */                                         out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 2526 */                                         out.write("\n    </a>\n ");
/* 2527 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2528 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2532 */                                     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2533 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                                     }
/*      */                                     
/* 2536 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2537 */                                     out.write(10);
/* 2538 */                                     out.write(32);
/*      */                                     
/* 2540 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2541 */                                     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2542 */                                     _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2543 */                                     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2544 */                                     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                       for (;;) {
/* 2546 */                                         out.write(10);
/* 2547 */                                         out.write(9);
/* 2548 */                                         out.write(32);
/* 2549 */                                         out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 2550 */                                         out.write(10);
/* 2551 */                                         out.write(32);
/* 2552 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2553 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2557 */                                     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2558 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                     }
/*      */                                     
/* 2561 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2562 */                                     out.write(10);
/* 2563 */                                     out.write(32);
/* 2564 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2565 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2569 */                                 if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2570 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                                 }
/*      */                                 
/* 2573 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2574 */                                 out.write("\n    </td>\n\t");
/*      */                               }
/* 2576 */                               out.write("\n</tr>  \n        <tr>\n    \n   ");
/*      */                               
/* 2578 */                               if (request.getParameter("wiz") != null)
/*      */                               {
/* 2580 */                                 out.write("\n    \t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\"title=\"");
/* 2581 */                                 out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 2582 */                                 out.write("\" class='disabledlink'>");
/* 2583 */                                 out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 2584 */                                 out.write("</a></td>\n   ");
/*      */                               }
/*      */                               else
/*      */                               {
/* 2588 */                                 out.write("\n    <td class=\"leftlinkstd\">\n    \n");
/*      */                                 
/* 2590 */                                 ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2591 */                                 _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2592 */                                 _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/* 2593 */                                 int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2594 */                                 if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                                   for (;;) {
/* 2596 */                                     out.write(10);
/*      */                                     
/* 2598 */                                     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2599 */                                     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2600 */                                     _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                     
/* 2602 */                                     _jspx_th_c_005fwhen_005f1.setTest("${param.method =='showMonitorTemplates' || param.method == 'reloadHostDiscoveryForm'}");
/* 2603 */                                     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2604 */                                     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                       for (;;) {
/* 2606 */                                         out.write("\n   ");
/* 2607 */                                         out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 2608 */                                         out.write(10);
/* 2609 */                                         out.write(32);
/* 2610 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2611 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2615 */                                     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2616 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                                     }
/*      */                                     
/* 2619 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2620 */                                     out.write(10);
/* 2621 */                                     out.write(32);
/*      */                                     
/* 2623 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2624 */                                     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2625 */                                     _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2626 */                                     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2627 */                                     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                       for (;;) {
/* 2629 */                                         out.write(10);
/* 2630 */                                         String link = "/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999";
/* 2631 */                                         out.write("\n\t \n <a href=\"");
/* 2632 */                                         out.print(link);
/* 2633 */                                         out.write("\" class=\"new-left-links\">\n               ");
/* 2634 */                                         out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 2635 */                                         out.write("\n    </a>    \n ");
/* 2636 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2637 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2641 */                                     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2642 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                                     }
/*      */                                     
/* 2645 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2646 */                                     out.write(10);
/* 2647 */                                     out.write(32);
/* 2648 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2649 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2653 */                                 if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2654 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                                 }
/*      */                                 
/* 2657 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2658 */                                 out.write("\n</td>\n");
/*      */                               }
/* 2660 */                               out.write("\n</tr>\n\n ");
/*      */                             }
/*      */                             
/*      */ 
/* 2664 */                             out.write("\n \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                             
/* 2666 */                             ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2667 */                             _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2668 */                             _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/* 2669 */                             int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2670 */                             if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                               for (;;) {
/* 2672 */                                 out.write(10);
/*      */                                 
/* 2674 */                                 WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2675 */                                 _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2676 */                                 _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                 
/* 2678 */                                 _jspx_th_c_005fwhen_005f2.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Actionalert'}");
/* 2679 */                                 int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2680 */                                 if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                   for (;;) {
/* 2682 */                                     out.write("\n    \n       ");
/* 2683 */                                     out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 2684 */                                     out.write(10);
/* 2685 */                                     out.write(32);
/* 2686 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2687 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2691 */                                 if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2692 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                 }
/*      */                                 
/* 2695 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2696 */                                 out.write(10);
/* 2697 */                                 out.write(32);
/*      */                                 
/* 2699 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2700 */                                 _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2701 */                                 _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 2702 */                                 int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2703 */                                 if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                                   for (;;) {
/* 2705 */                                     out.write("\n       <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Actionalert\" class=\"new-left-links\">\n ");
/* 2706 */                                     out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 2707 */                                     out.write("\n    </a>\n ");
/* 2708 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2709 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2713 */                                 if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2714 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                                 }
/*      */                                 
/* 2717 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2718 */                                 out.write(10);
/* 2719 */                                 out.write(32);
/* 2720 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2721 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2725 */                             if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2726 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                             }
/*      */                             
/* 2729 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2730 */                             out.write("\n    </td>\n</tr>   \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                             
/* 2732 */                             ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2733 */                             _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2734 */                             _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_tiles_005fput_005f2);
/* 2735 */                             int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2736 */                             if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                               for (;;) {
/* 2738 */                                 out.write(10);
/*      */                                 
/* 2740 */                                 WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2741 */                                 _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2742 */                                 _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                                 
/* 2744 */                                 _jspx_th_c_005fwhen_005f3.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Availablity'}");
/* 2745 */                                 int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2746 */                                 if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                   for (;;) {
/* 2748 */                                     out.write("\n    \n       ");
/* 2749 */                                     out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 2750 */                                     out.write(10);
/* 2751 */                                     out.write(32);
/* 2752 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2753 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2757 */                                 if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2758 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                                 }
/*      */                                 
/* 2761 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2762 */                                 out.write(10);
/* 2763 */                                 out.write(32);
/*      */                                 
/* 2765 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2766 */                                 _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 2767 */                                 _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 2768 */                                 int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 2769 */                                 if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                                   for (;;) {
/* 2771 */                                     out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Availablity\" class=\"new-left-links\">\n\t ");
/* 2772 */                                     out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 2773 */                                     out.write("\n\t </a>\n ");
/* 2774 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 2775 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2779 */                                 if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 2780 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                                 }
/*      */                                 
/* 2783 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2784 */                                 out.write(10);
/* 2785 */                                 out.write(32);
/* 2786 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2787 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2791 */                             if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2792 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                             }
/*      */                             
/* 2795 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2796 */                             out.write("\n    </td>\n</tr>  \n\n  ");
/*      */                             
/* 2798 */                             if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                             {
/*      */ 
/* 2801 */                               out.write(32);
/* 2802 */                               out.write(32);
/* 2803 */                               out.write(10);
/*      */                               
/* 2805 */                               ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2806 */                               _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 2807 */                               _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_tiles_005fput_005f2);
/* 2808 */                               int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 2809 */                               if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                                 for (;;) {
/* 2811 */                                   out.write(10);
/*      */                                   
/* 2813 */                                   WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2814 */                                   _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 2815 */                                   _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                                   
/* 2817 */                                   _jspx_th_c_005fwhen_005f4.setTest("${param.method !='showNetworkDiscoveryForm'}");
/* 2818 */                                   int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 2819 */                                   if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                                     for (;;) {
/* 2821 */                                       out.write("\n<tr>\n    ");
/* 2822 */                                       if (!request.isUserInRole("OPERATOR")) {
/* 2823 */                                         out.write("\n    <td class=\"leftlinkstd\" >    \n        <a href=\"/jsp/DiscoveryProfiles.jsp?showlink=network\" class=\"new-left-links\">\n           ");
/* 2824 */                                         out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 2825 */                                         out.write("\n    </a>\n        </td>\n     ");
/*      */                                       } else {
/* 2827 */                                         out.write("\n\t<td class=\"leftlinkstd\" > <a href=\"javascript:void(0)\" class=\"disabledlink\">\n\t ");
/* 2828 */                                         out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 2829 */                                         out.write("\n\t</a>\n\t </td>\n\t");
/*      */                                       }
/* 2831 */                                       out.write("\n    </tr>\n ");
/* 2832 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 2833 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2837 */                                   if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 2838 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                                   }
/*      */                                   
/* 2841 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 2842 */                                   out.write(10);
/* 2843 */                                   out.write(32);
/*      */                                   
/* 2845 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2846 */                                   _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 2847 */                                   _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 2848 */                                   int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 2849 */                                   if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                                     for (;;) {
/* 2851 */                                       out.write("\n \t<td class=\"leftlinkstd\" > \n\t ");
/* 2852 */                                       out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 2853 */                                       out.write("\n\t </td>\n ");
/* 2854 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 2855 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2859 */                                   if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 2860 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                                   }
/*      */                                   
/* 2863 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2864 */                                   out.write(10);
/* 2865 */                                   out.write(32);
/* 2866 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 2867 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2871 */                               if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 2872 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                               }
/*      */                               
/* 2875 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2876 */                               out.write("\n \n  ");
/*      */                             }
/*      */                             
/*      */ 
/* 2880 */                             out.write("  \n \n ");
/*      */                             
/* 2882 */                             if (!usertype.equals("F"))
/*      */                             {
/* 2884 */                               out.write("\n \n  <tr>   \n     <td class=\"leftlinkstd\" >\n\t");
/*      */                               
/* 2886 */                               ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2887 */                               _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 2888 */                               _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_tiles_005fput_005f2);
/* 2889 */                               int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 2890 */                               if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                                 for (;;) {
/* 2892 */                                   out.write(10);
/* 2893 */                                   out.write(9);
/*      */                                   
/* 2895 */                                   WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2896 */                                   _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2897 */                                   _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                                   
/* 2899 */                                   _jspx_th_c_005fwhen_005f5.setTest("${param.method !='maintenanceTaskListView'}");
/* 2900 */                                   int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 2901 */                                   if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                                     for (;;) {
/* 2903 */                                       out.write("     \n        \t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 2904 */                                       out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 2905 */                                       out.write("</a>\n  \t");
/* 2906 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 2907 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2911 */                                   if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 2912 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                                   }
/*      */                                   
/* 2915 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2916 */                                   out.write("\n  \t");
/*      */                                   
/* 2918 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2919 */                                   _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 2920 */                                   _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/* 2921 */                                   int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 2922 */                                   if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                                     for (;;) {
/* 2924 */                                       out.write("\n \t\t");
/* 2925 */                                       out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 2926 */                                       out.write("\n  \t");
/* 2927 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 2928 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2932 */                                   if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 2933 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                                   }
/*      */                                   
/* 2936 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 2937 */                                   out.write("\n  \t");
/* 2938 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 2939 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2943 */                               if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 2944 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                               }
/*      */                               
/* 2947 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 2948 */                               out.write("\n     </td>\n </tr>   \n \n ");
/*      */                               
/* 2950 */                               if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                               {
/*      */ 
/* 2953 */                                 out.write(32);
/* 2954 */                                 out.write(32);
/* 2955 */                                 out.write(10);
/*      */                                 
/* 2957 */                                 IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2958 */                                 _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2959 */                                 _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                 
/* 2961 */                                 _jspx_th_c_005fif_005f0.setTest("${category!='LAMP'}");
/* 2962 */                                 int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2963 */                                 if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                                   for (;;) {
/* 2965 */                                     out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n \t");
/*      */                                     
/* 2967 */                                     ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2968 */                                     _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 2969 */                                     _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_c_005fif_005f0);
/* 2970 */                                     int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 2971 */                                     if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                                       for (;;) {
/* 2973 */                                         out.write(10);
/* 2974 */                                         out.write(32);
/* 2975 */                                         out.write(9);
/*      */                                         
/* 2977 */                                         WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2978 */                                         _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 2979 */                                         _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                                         
/* 2981 */                                         _jspx_th_c_005fwhen_005f6.setTest("${param.method !='listTrapListener'}");
/* 2982 */                                         int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 2983 */                                         if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                                           for (;;) {
/* 2985 */                                             out.write("     \n         \t<a href=\"/adminAction.do?method=listTrapListener\" class=\"new-left-links\">");
/* 2986 */                                             out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 2987 */                                             out.write("</a>\n   \t");
/* 2988 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 2989 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 2993 */                                         if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 2994 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                                         }
/*      */                                         
/* 2997 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 2998 */                                         out.write("\n   \t");
/*      */                                         
/* 3000 */                                         OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3001 */                                         _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 3002 */                                         _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/* 3003 */                                         int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 3004 */                                         if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                                           for (;;) {
/* 3006 */                                             out.write("\n  \t\t");
/* 3007 */                                             out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 3008 */                                             out.write(" \n   \t");
/* 3009 */                                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 3010 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3014 */                                         if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 3015 */                                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                                         }
/*      */                                         
/* 3018 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 3019 */                                         out.write("\n   \t");
/* 3020 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 3021 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3025 */                                     if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 3026 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                                     }
/*      */                                     
/* 3029 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 3030 */                                     out.write("\n      </td>\n  </tr>   \n");
/* 3031 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3032 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3036 */                                 if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3037 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                                 }
/*      */                                 
/* 3040 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3041 */                                 out.write(10);
/* 3042 */                                 out.write(32);
/*      */                               }
/*      */                               
/*      */ 
/* 3046 */                               out.write("  \n\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                               
/* 3048 */                               ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3049 */                               _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 3050 */                               _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_tiles_005fput_005f2);
/* 3051 */                               int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 3052 */                               if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */                                 for (;;) {
/* 3054 */                                   out.write(10);
/*      */                                   
/* 3056 */                                   WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3057 */                                   _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 3058 */                                   _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                                   
/* 3060 */                                   _jspx_th_c_005fwhen_005f7.setTest("${param.method =='showScheduleReports'}");
/* 3061 */                                   int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 3062 */                                   if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                                     for (;;) {
/* 3064 */                                       out.write("\n       ");
/* 3065 */                                       out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3066 */                                       out.write(10);
/* 3067 */                                       out.write(32);
/* 3068 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 3069 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3073 */                                   if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 3074 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                                   }
/*      */                                   
/* 3077 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 3078 */                                   out.write(10);
/* 3079 */                                   out.write(32);
/*      */                                   
/* 3081 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3082 */                                   _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 3083 */                                   _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/* 3084 */                                   int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 3085 */                                   if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                                     for (;;) {
/* 3087 */                                       out.write("\n     <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n\t");
/* 3088 */                                       out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3089 */                                       out.write("\n\t </a>\n ");
/* 3090 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 3091 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3095 */                                   if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 3096 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                                   }
/*      */                                   
/* 3099 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 3100 */                                   out.write(10);
/* 3101 */                                   out.write(32);
/* 3102 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 3103 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3107 */                               if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 3108 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */                               }
/*      */                               
/* 3111 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 3112 */                               out.write("\n    </td>\n</tr> \n");
/*      */                             } else {
/* 3114 */                               out.write("\n <tr>   \n     <td class=\"leftlinkstd\">\n\t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 3115 */                               out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 3116 */                               out.write("</a>\n     </td>\n </tr>   \n");
/*      */                               
/* 3118 */                               IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3119 */                               _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3120 */                               _jspx_th_c_005fif_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 3122 */                               _jspx_th_c_005fif_005f1.setTest("${category!='LAMP'}");
/* 3123 */                               int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3124 */                               if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                 for (;;) {
/* 3126 */                                   out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/* 3127 */                                   out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 3128 */                                   out.write("</a>\n\t  </td>\n  </tr>   \n");
/* 3129 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3130 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3134 */                               if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3135 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                               }
/*      */                               
/* 3138 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3139 */                               out.write("\n\n<tr>\n    <td class=\"leftlinkstd\" >\n\t <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n        ");
/* 3140 */                               out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3141 */                               out.write("\n         </a>\n\n    </td>\n</tr> \n");
/*      */                             }
/* 3143 */                             out.write("\n <tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                             
/* 3145 */                             ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3146 */                             _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 3147 */                             _jspx_th_c_005fchoose_005f8.setParent(_jspx_th_tiles_005fput_005f2);
/* 3148 */                             int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 3149 */                             if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */                               for (;;) {
/* 3151 */                                 out.write(10);
/*      */                                 
/* 3153 */                                 WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3154 */                                 _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 3155 */                                 _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */                                 
/* 3157 */                                 _jspx_th_c_005fwhen_005f8.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/* 3158 */                                 int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 3159 */                                 if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                                   for (;;) {
/* 3161 */                                     out.write("\n        ");
/* 3162 */                                     out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 3163 */                                     out.write(10);
/* 3164 */                                     out.write(32);
/* 3165 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 3166 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3170 */                                 if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 3171 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                                 }
/*      */                                 
/* 3174 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 3175 */                                 out.write(10);
/* 3176 */                                 out.write(32);
/*      */                                 
/* 3178 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3179 */                                 _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 3180 */                                 _jspx_th_c_005fotherwise_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/* 3181 */                                 int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 3182 */                                 if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */                                   for (;;) {
/* 3184 */                                     out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general\" class=\"new-left-links\">\n\t ");
/* 3185 */                                     out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 3186 */                                     out.write("\n\t </a>\n ");
/* 3187 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 3188 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3192 */                                 if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 3193 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8); return;
/*      */                                 }
/*      */                                 
/* 3196 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 3197 */                                 out.write(10);
/* 3198 */                                 out.write(32);
/* 3199 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 3200 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3204 */                             if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 3205 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */                             }
/*      */                             
/* 3208 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 3209 */                             out.write("\n    </td>\n</tr>   \n\n<tr>\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                             
/* 3211 */                             ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3212 */                             _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 3213 */                             _jspx_th_c_005fchoose_005f9.setParent(_jspx_th_tiles_005fput_005f2);
/* 3214 */                             int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 3215 */                             if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */                               for (;;) {
/* 3217 */                                 out.write(10);
/*      */                                 
/* 3219 */                                 WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3220 */                                 _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 3221 */                                 _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/*      */                                 
/* 3223 */                                 _jspx_th_c_005fwhen_005f9.setTest("${param.method!='showMailServerConfiguration'}");
/* 3224 */                                 int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 3225 */                                 if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */                                   for (;;) {
/* 3227 */                                     out.write("    \n    <a href=\"/adminAction.do?method=showMailServerConfiguration\" class=\"new-left-links\">\n    ");
/* 3228 */                                     out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 3229 */                                     out.write("\n    </a>    \n ");
/* 3230 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 3231 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3235 */                                 if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 3236 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */                                 }
/*      */                                 
/* 3239 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 3240 */                                 out.write(10);
/* 3241 */                                 out.write(32);
/*      */                                 
/* 3243 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3244 */                                 _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 3245 */                                 _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/* 3246 */                                 int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 3247 */                                 if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */                                   for (;;) {
/* 3249 */                                     out.write(10);
/* 3250 */                                     out.write(9);
/* 3251 */                                     out.write(32);
/* 3252 */                                     out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 3253 */                                     out.write(10);
/* 3254 */                                     out.write(32);
/* 3255 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 3256 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3260 */                                 if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 3261 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9); return;
/*      */                                 }
/*      */                                 
/* 3264 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 3265 */                                 out.write(10);
/* 3266 */                                 out.write(32);
/* 3267 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 3268 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3272 */                             if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 3273 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */                             }
/*      */                             
/* 3276 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 3277 */                             out.write("\n    </td>\n</tr>\n\n\n");
/* 3278 */                             if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/* 3279 */                               out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */                               
/* 3281 */                               ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3282 */                               _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 3283 */                               _jspx_th_c_005fchoose_005f10.setParent(_jspx_th_tiles_005fput_005f2);
/* 3284 */                               int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 3285 */                               if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */                                 for (;;) {
/* 3287 */                                   out.write(10);
/*      */                                   
/* 3289 */                                   WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3290 */                                   _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 3291 */                                   _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/*      */                                   
/* 3293 */                                   _jspx_th_c_005fwhen_005f10.setTest("${param.method!='SMSServerConfiguration'}");
/* 3294 */                                   int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 3295 */                                   if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                                     for (;;) {
/* 3297 */                                       out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/* 3298 */                                       out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 3299 */                                       out.write("\n    </a>\n ");
/* 3300 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 3301 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3305 */                                   if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 3306 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                                   }
/*      */                                   
/* 3309 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 3310 */                                   out.write(10);
/* 3311 */                                   out.write(32);
/*      */                                   
/* 3313 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3314 */                                   _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 3315 */                                   _jspx_th_c_005fotherwise_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/* 3316 */                                   int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 3317 */                                   if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */                                     for (;;) {
/* 3319 */                                       out.write("\n         ");
/* 3320 */                                       out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 3321 */                                       out.write(10);
/* 3322 */                                       out.write(32);
/* 3323 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 3324 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3328 */                                   if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 3329 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10); return;
/*      */                                   }
/*      */                                   
/* 3332 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 3333 */                                   out.write(10);
/* 3334 */                                   out.write(32);
/* 3335 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 3336 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3340 */                               if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 3341 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10); return;
/*      */                               }
/*      */                               
/* 3344 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 3345 */                               out.write("\n    </td>\n</tr>\n");
/*      */                             }
/* 3347 */                             out.write("\n\n\n ");
/*      */                             
/* 3349 */                             if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                             {
/*      */ 
/* 3352 */                               out.write("  \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                               
/* 3354 */                               ChooseTag _jspx_th_c_005fchoose_005f11 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3355 */                               _jspx_th_c_005fchoose_005f11.setPageContext(_jspx_page_context);
/* 3356 */                               _jspx_th_c_005fchoose_005f11.setParent(_jspx_th_tiles_005fput_005f2);
/* 3357 */                               int _jspx_eval_c_005fchoose_005f11 = _jspx_th_c_005fchoose_005f11.doStartTag();
/* 3358 */                               if (_jspx_eval_c_005fchoose_005f11 != 0) {
/*      */                                 for (;;) {
/* 3360 */                                   out.write(10);
/*      */                                   
/* 3362 */                                   WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3363 */                                   _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 3364 */                                   _jspx_th_c_005fwhen_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/*      */                                   
/* 3366 */                                   _jspx_th_c_005fwhen_005f11.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/* 3367 */                                   int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 3368 */                                   if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */                                     for (;;) {
/* 3370 */                                       out.write("    \n    <a href=\"/jsp/ProxyConfiguration.jsp\" class=\"new-left-links\">\n    ");
/* 3371 */                                       out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 3372 */                                       out.write("\n    </a>\n ");
/* 3373 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 3374 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3378 */                                   if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 3379 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11); return;
/*      */                                   }
/*      */                                   
/* 3382 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 3383 */                                   out.write(10);
/* 3384 */                                   out.write(32);
/*      */                                   
/* 3386 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f11 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3387 */                                   _jspx_th_c_005fotherwise_005f11.setPageContext(_jspx_page_context);
/* 3388 */                                   _jspx_th_c_005fotherwise_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/* 3389 */                                   int _jspx_eval_c_005fotherwise_005f11 = _jspx_th_c_005fotherwise_005f11.doStartTag();
/* 3390 */                                   if (_jspx_eval_c_005fotherwise_005f11 != 0) {
/*      */                                     for (;;) {
/* 3392 */                                       out.write(10);
/* 3393 */                                       out.write(9);
/* 3394 */                                       out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 3395 */                                       out.write(10);
/* 3396 */                                       out.write(32);
/* 3397 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f11.doAfterBody();
/* 3398 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3402 */                                   if (_jspx_th_c_005fotherwise_005f11.doEndTag() == 5) {
/* 3403 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11); return;
/*      */                                   }
/*      */                                   
/* 3406 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11);
/* 3407 */                                   out.write(10);
/* 3408 */                                   out.write(32);
/* 3409 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f11.doAfterBody();
/* 3410 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3414 */                               if (_jspx_th_c_005fchoose_005f11.doEndTag() == 5) {
/* 3415 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11); return;
/*      */                               }
/*      */                               
/* 3418 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11);
/* 3419 */                               out.write("\n    </td>\n</tr>\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                               
/* 3421 */                               ChooseTag _jspx_th_c_005fchoose_005f12 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3422 */                               _jspx_th_c_005fchoose_005f12.setPageContext(_jspx_page_context);
/* 3423 */                               _jspx_th_c_005fchoose_005f12.setParent(_jspx_th_tiles_005fput_005f2);
/* 3424 */                               int _jspx_eval_c_005fchoose_005f12 = _jspx_th_c_005fchoose_005f12.doStartTag();
/* 3425 */                               if (_jspx_eval_c_005fchoose_005f12 != 0) {
/*      */                                 for (;;) {
/* 3427 */                                   out.write(10);
/*      */                                   
/* 3429 */                                   WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3430 */                                   _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 3431 */                                   _jspx_th_c_005fwhen_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/*      */                                   
/* 3433 */                                   _jspx_th_c_005fwhen_005f12.setTest("${uri !='/Upload.do'}");
/* 3434 */                                   int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 3435 */                                   if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */                                     for (;;) {
/* 3437 */                                       out.write("   \n        ");
/*      */                                       
/* 3439 */                                       AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3440 */                                       _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 3441 */                                       _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_c_005fwhen_005f12);
/*      */                                       
/* 3443 */                                       _jspx_th_am_005fadminlink_005f0.setHref("/Upload.do");
/*      */                                       
/* 3445 */                                       _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/* 3446 */                                       int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 3447 */                                       if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 3448 */                                         if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 3449 */                                           out = _jspx_page_context.pushBody();
/* 3450 */                                           _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 3451 */                                           _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3454 */                                           out.write("\n           ");
/* 3455 */                                           out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 3456 */                                           out.write("\n            ");
/* 3457 */                                           int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 3458 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3461 */                                         if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 3462 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3465 */                                       if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 3466 */                                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                                       }
/*      */                                       
/* 3469 */                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 3470 */                                       out.write(10);
/* 3471 */                                       out.write(10);
/* 3472 */                                       out.write(32);
/* 3473 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 3474 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3478 */                                   if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 3479 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12); return;
/*      */                                   }
/*      */                                   
/* 3482 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 3483 */                                   out.write(10);
/* 3484 */                                   out.write(32);
/*      */                                   
/* 3486 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f12 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3487 */                                   _jspx_th_c_005fotherwise_005f12.setPageContext(_jspx_page_context);
/* 3488 */                                   _jspx_th_c_005fotherwise_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/* 3489 */                                   int _jspx_eval_c_005fotherwise_005f12 = _jspx_th_c_005fotherwise_005f12.doStartTag();
/* 3490 */                                   if (_jspx_eval_c_005fotherwise_005f12 != 0) {
/*      */                                     for (;;) {
/* 3492 */                                       out.write(10);
/* 3493 */                                       out.write(9);
/* 3494 */                                       out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 3495 */                                       out.write(10);
/* 3496 */                                       out.write(32);
/* 3497 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f12.doAfterBody();
/* 3498 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3502 */                                   if (_jspx_th_c_005fotherwise_005f12.doEndTag() == 5) {
/* 3503 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12); return;
/*      */                                   }
/*      */                                   
/* 3506 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12);
/* 3507 */                                   out.write(10);
/* 3508 */                                   out.write(32);
/* 3509 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f12.doAfterBody();
/* 3510 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3514 */                               if (_jspx_th_c_005fchoose_005f12.doEndTag() == 5) {
/* 3515 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12); return;
/*      */                               }
/*      */                               
/* 3518 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12);
/* 3519 */                               out.write("\n    </td>\n</tr>\n \n ");
/*      */                             }
/*      */                             
/*      */ 
/* 3523 */                             out.write("  \n \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                             
/* 3525 */                             ChooseTag _jspx_th_c_005fchoose_005f13 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3526 */                             _jspx_th_c_005fchoose_005f13.setPageContext(_jspx_page_context);
/* 3527 */                             _jspx_th_c_005fchoose_005f13.setParent(_jspx_th_tiles_005fput_005f2);
/* 3528 */                             int _jspx_eval_c_005fchoose_005f13 = _jspx_th_c_005fchoose_005f13.doStartTag();
/* 3529 */                             if (_jspx_eval_c_005fchoose_005f13 != 0) {
/*      */                               for (;;) {
/* 3531 */                                 out.write(10);
/*      */                                 
/* 3533 */                                 WhenTag _jspx_th_c_005fwhen_005f13 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3534 */                                 _jspx_th_c_005fwhen_005f13.setPageContext(_jspx_page_context);
/* 3535 */                                 _jspx_th_c_005fwhen_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/*      */                                 
/* 3537 */                                 _jspx_th_c_005fwhen_005f13.setTest("${uri !='/admin/userconfiguration.do'}");
/* 3538 */                                 int _jspx_eval_c_005fwhen_005f13 = _jspx_th_c_005fwhen_005f13.doStartTag();
/* 3539 */                                 if (_jspx_eval_c_005fwhen_005f13 != 0) {
/*      */                                   for (;;) {
/* 3541 */                                     out.write("\n    \n        ");
/*      */                                     
/* 3543 */                                     AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3544 */                                     _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 3545 */                                     _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_c_005fwhen_005f13);
/*      */                                     
/* 3547 */                                     _jspx_th_am_005fadminlink_005f1.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                                     
/* 3549 */                                     _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/* 3550 */                                     int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 3551 */                                     if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 3552 */                                       if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 3553 */                                         out = _jspx_page_context.pushBody();
/* 3554 */                                         _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/* 3555 */                                         _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3558 */                                         out.write("\n       ");
/* 3559 */                                         out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 3560 */                                         out.write("\n        ");
/* 3561 */                                         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 3562 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3565 */                                       if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 3566 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3569 */                                     if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 3570 */                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                                     }
/*      */                                     
/* 3573 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 3574 */                                     out.write(10);
/* 3575 */                                     out.write(10);
/* 3576 */                                     out.write(32);
/* 3577 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f13.doAfterBody();
/* 3578 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3582 */                                 if (_jspx_th_c_005fwhen_005f13.doEndTag() == 5) {
/* 3583 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13); return;
/*      */                                 }
/*      */                                 
/* 3586 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 3587 */                                 out.write(10);
/* 3588 */                                 out.write(32);
/*      */                                 
/* 3590 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f13 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3591 */                                 _jspx_th_c_005fotherwise_005f13.setPageContext(_jspx_page_context);
/* 3592 */                                 _jspx_th_c_005fotherwise_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/* 3593 */                                 int _jspx_eval_c_005fotherwise_005f13 = _jspx_th_c_005fotherwise_005f13.doStartTag();
/* 3594 */                                 if (_jspx_eval_c_005fotherwise_005f13 != 0) {
/*      */                                   for (;;) {
/* 3596 */                                     out.write(10);
/* 3597 */                                     out.write(9);
/* 3598 */                                     out.write(32);
/* 3599 */                                     out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 3600 */                                     out.write(10);
/* 3601 */                                     out.write(32);
/* 3602 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f13.doAfterBody();
/* 3603 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3607 */                                 if (_jspx_th_c_005fotherwise_005f13.doEndTag() == 5) {
/* 3608 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13); return;
/*      */                                 }
/*      */                                 
/* 3611 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13);
/* 3612 */                                 out.write(10);
/* 3613 */                                 out.write(32);
/* 3614 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f13.doAfterBody();
/* 3615 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3619 */                             if (_jspx_th_c_005fchoose_005f13.doEndTag() == 5) {
/* 3620 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13); return;
/*      */                             }
/*      */                             
/* 3623 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13);
/* 3624 */                             out.write("\n    </td>\n</tr>\n   \n\n ");
/* 3625 */                             if (!com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 3626 */                               out.write("\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                               
/* 3628 */                               ChooseTag _jspx_th_c_005fchoose_005f14 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3629 */                               _jspx_th_c_005fchoose_005f14.setPageContext(_jspx_page_context);
/* 3630 */                               _jspx_th_c_005fchoose_005f14.setParent(_jspx_th_tiles_005fput_005f2);
/* 3631 */                               int _jspx_eval_c_005fchoose_005f14 = _jspx_th_c_005fchoose_005f14.doStartTag();
/* 3632 */                               if (_jspx_eval_c_005fchoose_005f14 != 0) {
/*      */                                 for (;;) {
/* 3634 */                                   out.write("\n   ");
/*      */                                   
/* 3636 */                                   WhenTag _jspx_th_c_005fwhen_005f14 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3637 */                                   _jspx_th_c_005fwhen_005f14.setPageContext(_jspx_page_context);
/* 3638 */                                   _jspx_th_c_005fwhen_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/*      */                                   
/* 3640 */                                   _jspx_th_c_005fwhen_005f14.setTest("${param.method!='showExtDeviceConfigurations'}");
/* 3641 */                                   int _jspx_eval_c_005fwhen_005f14 = _jspx_th_c_005fwhen_005f14.doStartTag();
/* 3642 */                                   if (_jspx_eval_c_005fwhen_005f14 != 0) {
/*      */                                     for (;;) {
/* 3644 */                                       out.write("\n    ");
/*      */                                       
/* 3646 */                                       AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3647 */                                       _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/* 3648 */                                       _jspx_th_am_005fadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f14);
/*      */                                       
/* 3650 */                                       _jspx_th_am_005fadminlink_005f2.setHref("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */                                       
/* 3652 */                                       _jspx_th_am_005fadminlink_005f2.setEnableClass("new-left-links");
/* 3653 */                                       int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/* 3654 */                                       if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/* 3655 */                                         if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 3656 */                                           out = _jspx_page_context.pushBody();
/* 3657 */                                           _jspx_th_am_005fadminlink_005f2.setBodyContent((BodyContent)out);
/* 3658 */                                           _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3661 */                                           out.write(10);
/* 3662 */                                           out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 3663 */                                           out.write("\n    ");
/* 3664 */                                           int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/* 3665 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3668 */                                         if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 3669 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3672 */                                       if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/* 3673 */                                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2); return;
/*      */                                       }
/*      */                                       
/* 3676 */                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/* 3677 */                                       out.write("\n   ");
/* 3678 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f14.doAfterBody();
/* 3679 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3683 */                                   if (_jspx_th_c_005fwhen_005f14.doEndTag() == 5) {
/* 3684 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14); return;
/*      */                                   }
/*      */                                   
/* 3687 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 3688 */                                   out.write("\n   ");
/*      */                                   
/* 3690 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f14 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3691 */                                   _jspx_th_c_005fotherwise_005f14.setPageContext(_jspx_page_context);
/* 3692 */                                   _jspx_th_c_005fotherwise_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/* 3693 */                                   int _jspx_eval_c_005fotherwise_005f14 = _jspx_th_c_005fotherwise_005f14.doStartTag();
/* 3694 */                                   if (_jspx_eval_c_005fotherwise_005f14 != 0) {
/*      */                                     for (;;) {
/* 3696 */                                       out.write(10);
/* 3697 */                                       out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 3698 */                                       out.write("\n   ");
/* 3699 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f14.doAfterBody();
/* 3700 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3704 */                                   if (_jspx_th_c_005fotherwise_005f14.doEndTag() == 5) {
/* 3705 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14); return;
/*      */                                   }
/*      */                                   
/* 3708 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14);
/* 3709 */                                   out.write(10);
/* 3710 */                                   out.write(32);
/* 3711 */                                   out.write(32);
/* 3712 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f14.doAfterBody();
/* 3713 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3717 */                               if (_jspx_th_c_005fchoose_005f14.doEndTag() == 5) {
/* 3718 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14); return;
/*      */                               }
/*      */                               
/* 3721 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14);
/* 3722 */                               out.write("\n </td>\n</tr>\n  ");
/*      */                             }
/* 3724 */                             out.write("\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                             
/* 3726 */                             ChooseTag _jspx_th_c_005fchoose_005f15 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3727 */                             _jspx_th_c_005fchoose_005f15.setPageContext(_jspx_page_context);
/* 3728 */                             _jspx_th_c_005fchoose_005f15.setParent(_jspx_th_tiles_005fput_005f2);
/* 3729 */                             int _jspx_eval_c_005fchoose_005f15 = _jspx_th_c_005fchoose_005f15.doStartTag();
/* 3730 */                             if (_jspx_eval_c_005fchoose_005f15 != 0) {
/*      */                               for (;;) {
/* 3732 */                                 out.write("\n   ");
/*      */                                 
/* 3734 */                                 WhenTag _jspx_th_c_005fwhen_005f15 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3735 */                                 _jspx_th_c_005fwhen_005f15.setPageContext(_jspx_page_context);
/* 3736 */                                 _jspx_th_c_005fwhen_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/*      */                                 
/* 3738 */                                 _jspx_th_c_005fwhen_005f15.setTest("${param.method!='showDataCleanUp'}");
/* 3739 */                                 int _jspx_eval_c_005fwhen_005f15 = _jspx_th_c_005fwhen_005f15.doStartTag();
/* 3740 */                                 if (_jspx_eval_c_005fwhen_005f15 != 0) {
/*      */                                   for (;;) {
/* 3742 */                                     out.write("\n    <a href=\"/adminAction.do?method=showDataCleanUp\" class=\"new-left-links\">\n");
/* 3743 */                                     out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 3744 */                                     out.write("\n    </a>\n   ");
/* 3745 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f15.doAfterBody();
/* 3746 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3750 */                                 if (_jspx_th_c_005fwhen_005f15.doEndTag() == 5) {
/* 3751 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15); return;
/*      */                                 }
/*      */                                 
/* 3754 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15);
/* 3755 */                                 out.write("\n   ");
/*      */                                 
/* 3757 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f15 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3758 */                                 _jspx_th_c_005fotherwise_005f15.setPageContext(_jspx_page_context);
/* 3759 */                                 _jspx_th_c_005fotherwise_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/* 3760 */                                 int _jspx_eval_c_005fotherwise_005f15 = _jspx_th_c_005fotherwise_005f15.doStartTag();
/* 3761 */                                 if (_jspx_eval_c_005fotherwise_005f15 != 0) {
/*      */                                   for (;;) {
/* 3763 */                                     out.write(10);
/* 3764 */                                     out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 3765 */                                     out.write("\n   ");
/* 3766 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f15.doAfterBody();
/* 3767 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3771 */                                 if (_jspx_th_c_005fotherwise_005f15.doEndTag() == 5) {
/* 3772 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15); return;
/*      */                                 }
/*      */                                 
/* 3775 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15);
/* 3776 */                                 out.write(10);
/* 3777 */                                 out.write(32);
/* 3778 */                                 out.write(32);
/* 3779 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f15.doAfterBody();
/* 3780 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3784 */                             if (_jspx_th_c_005fchoose_005f15.doEndTag() == 5) {
/* 3785 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15); return;
/*      */                             }
/*      */                             
/* 3788 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15);
/* 3789 */                             out.write("\n </td>\n</tr>\n\n</table>\n\n");
/* 3790 */                             out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\"><tr> \n    <td width=\"80%\" class=\"leftlinksquicknote\">");
/* 3791 */                             out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 3792 */                             out.write("</td>\n    <td width=\"20%\"  align=\"right\" class=\"leftlinksheading\"><img src=\"../images/");
/* 3793 */                             if (_jspx_meth_c_005fout_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                               return;
/* 3795 */                             out.write("/img_quicknote.gif\" hspace=\"5\"></td>\n  </tr>\n\t<tr>\n\t   <td colspan=\"2\" class=\"quicknote\"> If the proxy server requires a name and password to access a URL over the Internet, enter the user name and password here.</td>\n\t</tr>\n</table>\n");
/* 3796 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 3797 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3800 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 3801 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3804 */                         if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3805 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                         }
/*      */                         
/* 3808 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 3809 */                         out.write(10);
/*      */                         
/* 3811 */                         PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 3812 */                         _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 3813 */                         _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 3815 */                         _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                         
/* 3817 */                         _jspx_th_tiles_005fput_005f3.setType("string");
/* 3818 */                         int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 3819 */                         if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 3820 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3821 */                             out = _jspx_page_context.pushBody();
/* 3822 */                             _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 3823 */                             _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 3826 */                             out.write("\n<table width=\"99%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n      <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 3827 */                             out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 3828 */                             out.write(" &gt;<span class=\"bcactive\"> Notification Profile</span></td>\n  </tr>\n  <tr>\n      <td  height=\"2\" class=\"bcstrip\"><img src=\"/images/spacer.gif\" width=\"10\" height=\"2\"></td>\n  </tr>\n   <tr>\n      <td  height=\"2\"><img src=\"/images/spacer.gif\" width=\"10\" height=\"9\"></td>\n   </tr>\n</table>\n");
/* 3829 */                             if (_jspx_meth_html_005fform_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 3831 */                             out.write(32);
/* 3832 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 3833 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3836 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3837 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3840 */                         if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3841 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                         }
/*      */                         
/* 3844 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 3845 */                         out.write(32);
/* 3846 */                         if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 3848 */                         out.write(10);
/* 3849 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3850 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3854 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3855 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 3858 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3859 */                       out.write(10);
/* 3860 */                       out.write(10);
/*      */                     }
/* 3862 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3863 */         out = _jspx_out;
/* 3864 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3865 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3866 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3869 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3875 */     PageContext pageContext = _jspx_page_context;
/* 3876 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3878 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3879 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 3880 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3882 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 3884 */     _jspx_th_tiles_005fput_005f0.setValue("LogTicket Settings");
/* 3885 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 3886 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 3887 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3888 */       return true;
/*      */     }
/* 3890 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3891 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3896 */     PageContext pageContext = _jspx_page_context;
/* 3897 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3899 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3900 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3901 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3903 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3905 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=3");
/* 3906 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3907 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3908 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3909 */       return true;
/*      */     }
/* 3911 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3912 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3917 */     PageContext pageContext = _jspx_page_context;
/* 3918 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3920 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3921 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3922 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 3924 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3926 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3927 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3928 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3929 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3930 */       return true;
/*      */     }
/* 3932 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3933 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fform_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3938 */     PageContext pageContext = _jspx_page_context;
/* 3939 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3941 */     FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 3942 */     _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 3943 */     _jspx_th_html_005fform_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3945 */     _jspx_th_html_005fform_005f0.setAction("/adminAction.do");
/*      */     
/* 3947 */     _jspx_th_html_005fform_005f0.setStyle("display:inline");
/* 3948 */     int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 3949 */     if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */       for (;;) {
/* 3951 */         out.write("\n<input type=\"hidden\" name=\"method\" value=\"SdeskLogTicketConfig\" >\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n <tr >\n   <td width=\"72%\" height=\"31\" class=\"tableheading\" >Configure Profile Details</td>\n </tr>\n</table> \n<table width=\"99%\" border=\"0\" cellspacing=\"8\" cellpadding=\"0\" class=\"lrborder\" >\n <tr>\n  <td><table width=\"99%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\">\n   <tr>\n   <td width=\"38%\" class=\"bodytext\">Profile Name</td>\n   <td width=\"60%\" class=\"bodytext\">");
/* 3952 */         if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/* 3953 */           return true;
/* 3954 */         out.write("</td>\n   </tr>\n   <tr width=\"99%\" bgcolor=\"#efefef\">\n  \t\t<td colspan=\"2\" class=\"bodytext\"><b>Ticket Details</b></td>\n\t</tr>\n\t<tr>\n\t <td width=\"38%\" class=\"bodytext\">Tag the generated tickets with the following details</td>\n\t</tr>\t \n     <tr>\n\t     <td width=\"25%\" class=\"bodytext\">Category</td>\n\t\t  <td align=\"left\" width=\"75%\">\n\t\t   ");
/* 3955 */         if (_jspx_meth_html_005fselect_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/* 3956 */           return true;
/* 3957 */         out.write("\n\t\t</td>\n\t  </tr>\n\t <tr>\n\t     <td width=\"35%\" class=\"bodytext\">Priority</td>\n\t\t  <td align=\"left\" width=\"75%\">\n\t\t    ");
/* 3958 */         if (_jspx_meth_html_005fselect_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/* 3959 */           return true;
/* 3960 */         out.write("\n\t\t  </td>\n\t </tr>\n\t <tr>\n\t <td width=\"35%\" class=\"bodytext\">Technician</td>\n\t  <td align=\"left\" width=\"75%\">\n\t   ");
/* 3961 */         if (_jspx_meth_html_005fselect_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/* 3962 */           return true;
/* 3963 */         out.write("\n\t  </td>\n\t </tr>\t \n\t <tr bgcolor=\"#efefef\">\n\t     <td colspan=\"2\" class=\"bodytext\"><b>Ticket Contents</b>\n\t     </td>\n\t </tr>\n\t <tr>\n\t    <td width=\"55%\" class=\"bodytext\">Title</td>\n\t </tr>\t\n\t <tr bgcolor=\"#efefef\">\n\t <td width=\"55%\">");
/* 3964 */         if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/* 3965 */           return true;
/* 3966 */         out.write("</td>\n\t <td align=\"left\" width=\"75%\">\n\t   <select name=\"titleList\" Class=\"formtext\" onchange=\"javascript:addTitle('0')\">\n\t    <option value=\"alarm\">Select alarm Variable</option>\n\t    <option value=\"$HOSTNAME\">Host Name of the alarm</option>\n\t    <option value=\"$PORT\">Port Number of the alarm</option>\n\t    <option value=\"$MONITORNAME\">Monitor Name of the alarm</option>\n\t    <option value=\"$ATTRIBUTE\">Attributes of the alarm</option>\n\t\t<option value=\"$SEVERITY\">Severity of the alarm</option>\t\t\t   \n\t    <option value=\"$SEVERITYASNUMBER\">Severity number of the alarm</option>\n\t\t<option value=\"$MONITORTYPE\">Monitor type of the alarm</option>\n\t\t<option value=\"$HOSTIP\">Host IP Address of the alarm</option>\n\t    <option value=\"$OBJECTNAME\">Object Name of the alarm</option>\n\t\t<option value=\"$RCAMESSAGE\">RCA Message of the alarm</option>\n\t   </select>\n      </td>\n \t </tr>\t\n\t <tr>\n\t     <td width=\"35%\" class=\"bodytext\">Description</td>\n\t </tr>\t \n\t <tr bgcolor=\"#efefef\">\n\t <td width=\"65%\">");
/* 3967 */         if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/* 3968 */           return true;
/* 3969 */         out.write("</td>\n\t <td align=\"left\" width=\"75%\">\n\t  <select name=\"descList\" Class=\"formtext\" onchange=\"javascript:addTitle('1')\">\n\t   <option value=\"alarm1\">Select alarm Variable</option>\n\t   <option value=\"$HOSTNAME\">Host Name of the alarm</option>\n\t   <option value=\"$PORT\">Port Number of the alarm</option>\n\t   <option value=\"$MONITORNAME\">Monitor Name of the alarm</option>\n\t   <option value=\"$ATTRIBUTE\">Attributes of the alarm</option>\n\t   <option value=\"$SEVERITY\">Severity of the alarm</option>\n\t   <option value=\"$SEVERITYASNUMBER\">Severity number of the alarm</option>\n\t   <option value=\"$MONITORTYPE\">Monitor type of the alarm</option>\n\t   <option value=\"$HOSTIP\">Host IP Address of the alarm</option>\n\t   <option value=\"$OBJECTNAME\">Object Name of the alarm</option>\n\t   <option value=\"$RCAMESSAGE\">RCA Message of the alarm</option>\n\t  </select>\n\t </td>\n\t </tr>\n\t  <tr>\n\t   <td class=\"bodytext\">Mail Format</td>\n\t    <td colspan=\"2\" class=\"bodytext\">");
/* 3970 */         if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/* 3971 */           return true;
/* 3972 */         out.write("Plain Text&nbsp;&nbsp;&nbsp;&nbsp;");
/* 3973 */         if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/* 3974 */           return true;
/* 3975 */         out.write("HTML&nbsp;&nbsp;&nbsp;&nbsp;");
/* 3976 */         if (_jspx_meth_html_005fradio_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/* 3977 */           return true;
/* 3978 */         out.write("Both</td>\n\t  </tr>\n\t  <tr>\n\t   <td class=\"bodytext\">Append Alert Message</td>\n\t   <td colspan=\"2\" class=\"bodytext\">");
/* 3979 */         if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/* 3980 */           return true;
/* 3981 */         out.write("</td>\n\t  </tr>\n</table></td>\n</tr>\n</table>\n\t\t\t\t\t\t  \n\t \t\t  \n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n <tr>\n    <td width=\"12%\" class=\"tablebottom\">&nbsp;</td>\n\t<td width=\"48%\" height=\"31\" class=\"tablebottom\" ><input name=\"Submit\" value=\"Submit\" type=\"button\" class=\"buttons\" onClick=\"fnFormSubmit();\">\n\t<input type=\"button\" align=\"center\" class=\"buttons\" value=\"Cancel\" onClick=\"javascript:history.back();\"></td>\n\t      <!-- &nbsp;");
/* 3982 */         if (_jspx_meth_c_005fif_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/* 3983 */           return true;
/* 3984 */         out.write("\n      &nbsp; <input type=\"button\" class=\"buttons\" value=\"Cancel\" onClick=\"javascript:history.back();\"></td>\n       ");
/* 3985 */         if (_jspx_meth_c_005fif_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/* 3986 */           return true;
/* 3987 */         out.write("-->\n </tr>\n</table>\n");
/* 3988 */         int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 3989 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3993 */     if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 3994 */       this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 3995 */       return true;
/*      */     }
/* 3997 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 3998 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4003 */     PageContext pageContext = _jspx_page_context;
/* 4004 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4006 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 4007 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 4008 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4010 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 4012 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/* 4013 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 4014 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 4015 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4016 */       return true;
/*      */     }
/* 4018 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4019 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4024 */     PageContext pageContext = _jspx_page_context;
/* 4025 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4027 */     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty.get(SelectTag.class);
/* 4028 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 4029 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4031 */     _jspx_th_html_005fselect_005f0.setProperty("category");
/*      */     
/* 4033 */     _jspx_th_html_005fselect_005f0.setStyle("width:100%");
/*      */     
/* 4035 */     _jspx_th_html_005fselect_005f0.setSize("1");
/* 4036 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 4037 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 4038 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 4039 */         out = _jspx_page_context.pushBody();
/* 4040 */         _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 4041 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4044 */         out.write("\n\t\t   ");
/* 4045 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 4046 */           return true;
/* 4047 */         out.write("\n\t\t   ");
/* 4048 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 4049 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4052 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 4053 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4056 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 4057 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f0);
/* 4058 */       return true;
/*      */     }
/* 4060 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f0);
/* 4061 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4066 */     PageContext pageContext = _jspx_page_context;
/* 4067 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4069 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4070 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 4071 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 4073 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("toAdd");
/* 4074 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 4075 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 4076 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 4077 */       return true;
/*      */     }
/* 4079 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 4080 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4085 */     PageContext pageContext = _jspx_page_context;
/* 4086 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4088 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty.get(SelectTag.class);
/* 4089 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 4090 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4092 */     _jspx_th_html_005fselect_005f1.setProperty("priority");
/*      */     
/* 4094 */     _jspx_th_html_005fselect_005f1.setStyle("width:100%");
/*      */     
/* 4096 */     _jspx_th_html_005fselect_005f1.setSize("1");
/* 4097 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 4098 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 4099 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 4100 */         out = _jspx_page_context.pushBody();
/* 4101 */         _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 4102 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4105 */         out.write("\n\t\t    ");
/* 4106 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/* 4107 */           return true;
/* 4108 */         out.write("\n\t\t    ");
/* 4109 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 4110 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4113 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 4114 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4117 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 4118 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 4119 */       return true;
/*      */     }
/* 4121 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 4122 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4127 */     PageContext pageContext = _jspx_page_context;
/* 4128 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4130 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4131 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 4132 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*      */     
/* 4134 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("present");
/* 4135 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 4136 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 4137 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 4138 */       return true;
/*      */     }
/* 4140 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 4141 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4146 */     PageContext pageContext = _jspx_page_context;
/* 4147 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4149 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty.get(SelectTag.class);
/* 4150 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 4151 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4153 */     _jspx_th_html_005fselect_005f2.setProperty("technician");
/*      */     
/* 4155 */     _jspx_th_html_005fselect_005f2.setStyle("width:100%");
/*      */     
/* 4157 */     _jspx_th_html_005fselect_005f2.setSize("1");
/* 4158 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 4159 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 4160 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 4161 */         out = _jspx_page_context.pushBody();
/* 4162 */         _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 4163 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4166 */         out.write("\n\t   ");
/* 4167 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/* 4168 */           return true;
/* 4169 */         out.write("\n\t   ");
/* 4170 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 4171 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4174 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 4175 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4178 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 4179 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 4180 */       return true;
/*      */     }
/* 4182 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 4183 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4188 */     PageContext pageContext = _jspx_page_context;
/* 4189 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4191 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4192 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 4193 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*      */     
/* 4195 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("applications");
/* 4196 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 4197 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 4198 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 4199 */       return true;
/*      */     }
/* 4201 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 4202 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4207 */     PageContext pageContext = _jspx_page_context;
/* 4208 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4210 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody.get(TextTag.class);
/* 4211 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 4212 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4214 */     _jspx_th_html_005ftext_005f1.setProperty("subject");
/*      */     
/* 4216 */     _jspx_th_html_005ftext_005f1.setStyle("width:90%");
/*      */     
/* 4218 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/* 4219 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 4220 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 4221 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4222 */       return true;
/*      */     }
/* 4224 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4225 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4230 */     PageContext pageContext = _jspx_page_context;
/* 4231 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4233 */     TextareaTag _jspx_th_html_005ftextarea_005f0 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyle_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 4234 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 4235 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4237 */     _jspx_th_html_005ftextarea_005f0.setProperty("message");
/*      */     
/* 4239 */     _jspx_th_html_005ftextarea_005f0.setRows("8");
/*      */     
/* 4241 */     _jspx_th_html_005ftextarea_005f0.setCols("30");
/*      */     
/* 4243 */     _jspx_th_html_005ftextarea_005f0.setStyle("width:90%");
/* 4244 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 4245 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 4246 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyle_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 4247 */       return true;
/*      */     }
/* 4249 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyle_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 4250 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4255 */     PageContext pageContext = _jspx_page_context;
/* 4256 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4258 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4259 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 4260 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4262 */     _jspx_th_html_005fradio_005f0.setProperty("mailFormat");
/*      */     
/* 4264 */     _jspx_th_html_005fradio_005f0.setValue("1");
/* 4265 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 4266 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 4267 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 4268 */       return true;
/*      */     }
/* 4270 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 4271 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4276 */     PageContext pageContext = _jspx_page_context;
/* 4277 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4279 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4280 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 4281 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4283 */     _jspx_th_html_005fradio_005f1.setProperty("mailFormat");
/*      */     
/* 4285 */     _jspx_th_html_005fradio_005f1.setValue("2");
/* 4286 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 4287 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 4288 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 4289 */       return true;
/*      */     }
/* 4291 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 4292 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4297 */     PageContext pageContext = _jspx_page_context;
/* 4298 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4300 */     RadioTag _jspx_th_html_005fradio_005f2 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4301 */     _jspx_th_html_005fradio_005f2.setPageContext(_jspx_page_context);
/* 4302 */     _jspx_th_html_005fradio_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4304 */     _jspx_th_html_005fradio_005f2.setProperty("mailFormat");
/*      */     
/* 4306 */     _jspx_th_html_005fradio_005f2.setValue("0");
/* 4307 */     int _jspx_eval_html_005fradio_005f2 = _jspx_th_html_005fradio_005f2.doStartTag();
/* 4308 */     if (_jspx_th_html_005fradio_005f2.doEndTag() == 5) {
/* 4309 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 4310 */       return true;
/*      */     }
/* 4312 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 4313 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4318 */     PageContext pageContext = _jspx_page_context;
/* 4319 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4321 */     CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.get(CheckboxTag.class);
/* 4322 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 4323 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4325 */     _jspx_th_html_005fcheckbox_005f0.setProperty("appendMessage");
/*      */     
/* 4327 */     _jspx_th_html_005fcheckbox_005f0.setValue("1");
/* 4328 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 4329 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 4330 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 4331 */       return true;
/*      */     }
/* 4333 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 4334 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4339 */     PageContext pageContext = _jspx_page_context;
/* 4340 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4342 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4343 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 4344 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4346 */     _jspx_th_c_005fif_005f2.setTest("${param.actionmethod=='createUrlMonitor'}");
/* 4347 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 4348 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 4350 */         out.write("\n    <input type=\"button\" name=\"skip\" value=\"Skip\" onClick=\"javascript:location.href='/showresource.do?resourceid=");
/* 4351 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 4352 */           return true;
/* 4353 */         out.write("&method=showResourceForResourceID'\">\n    ");
/* 4354 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 4355 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4359 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 4360 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 4361 */       return true;
/*      */     }
/* 4363 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 4364 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4369 */     PageContext pageContext = _jspx_page_context;
/* 4370 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4372 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4373 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4374 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4376 */     _jspx_th_c_005fout_005f1.setValue("${urlid}");
/* 4377 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4378 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4379 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4380 */       return true;
/*      */     }
/* 4382 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4383 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4388 */     PageContext pageContext = _jspx_page_context;
/* 4389 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4391 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4392 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 4393 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4395 */     _jspx_th_c_005fif_005f3.setTest("${!empty param.haid}");
/* 4396 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 4397 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 4399 */         out.write("\n       <input type=\"hidden\" name=\"haid\" value='");
/* 4400 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 4401 */           return true;
/* 4402 */         out.write("'>\n       ");
/* 4403 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 4404 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4408 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 4409 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4410 */       return true;
/*      */     }
/* 4412 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4413 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4418 */     PageContext pageContext = _jspx_page_context;
/* 4419 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4421 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4422 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4423 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4425 */     _jspx_th_c_005fout_005f2.setValue("${param.haid}");
/* 4426 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4427 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4428 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4429 */       return true;
/*      */     }
/* 4431 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4432 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4437 */     PageContext pageContext = _jspx_page_context;
/* 4438 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4440 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4441 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 4442 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4444 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 4446 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 4447 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 4448 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 4449 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 4450 */       return true;
/*      */     }
/* 4452 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 4453 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ServiceDeskTicket_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */