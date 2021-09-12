/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.bean.WriteTag;
/*      */ import org.apache.struts.taglib.html.ButtonTag;
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.MessagesTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.PasswordTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class ServiceDesk_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, javax.servlet.http.HttpSession session)
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
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, javax.servlet.http.HttpSession session)
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
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, javax.servlet.http.HttpSession session, HttpServletRequest request)
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
/*  896 */     for (java.util.Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
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
/*  910 */     for (java.util.Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
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
/* 1703 */           String attributeName = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
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
/* 1973 */     for (java.util.Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
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
/* 2144 */           String message = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2145 */           String image = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
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
/* 2180 */   private static java.util.Map<String, Long> _jspx_dependants = new HashMap(5);
/* 2181 */   static { _jspx_dependants.put("/jsp/includes/HelpDeskChooser.jspf", Long.valueOf(1473429417000L));
/* 2182 */     _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/* 2183 */     _jspx_dependants.put("/jsp/includes/ServiceDesk.jspf", Long.valueOf(1473429417000L));
/* 2184 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2185 */     _jspx_dependants.put("/jsp/includes/SDeskSettings.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonchange_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/* 2220 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2224 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonchange_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2252 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2256 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2257 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2258 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2259 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2260 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2261 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2262 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2263 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2264 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2265 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/* 2266 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody.release();
/* 2267 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2268 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2269 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2270 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/* 2271 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/* 2272 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/* 2273 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/* 2274 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonchange_005fnobody.release();
/* 2275 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2276 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2277 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/* 2278 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.release();
/* 2279 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2280 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.release();
/* 2281 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.release();
/* 2282 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2289 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/* 2292 */     JspWriter out = null;
/* 2293 */     Object page = this;
/* 2294 */     JspWriter _jspx_out = null;
/* 2295 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2299 */       response.setContentType("text/html;charset=UTF-8");
/* 2300 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2302 */       _jspx_page_context = pageContext;
/* 2303 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2304 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2305 */       session = pageContext.getSession();
/* 2306 */       out = pageContext.getOut();
/* 2307 */       _jspx_out = out;
/*      */       
/* 2309 */       out.write("<!DOCTYPE html>\n");
/* 2310 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2311 */       out.write(10);
/* 2312 */       out.write(10);
/*      */       
/* 2314 */       request.setAttribute("HelpKey", "ServiceDesk Settings");
/*      */       
/* 2316 */       out.write("\n\n\n\n\n\n\n\n");
/* 2317 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2319 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2320 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2321 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2323 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2325 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2327 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2329 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2330 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2331 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2332 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2335 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2336 */         String available = null;
/* 2337 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2338 */         out.write(10);
/*      */         
/* 2340 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2341 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2342 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2344 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2346 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2348 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2350 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2351 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2352 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2353 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2356 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2357 */           String unavailable = null;
/* 2358 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2359 */           out.write(10);
/*      */           
/* 2361 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2362 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2363 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2365 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2367 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2369 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2371 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2372 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2373 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2374 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2377 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2378 */             String unmanaged = null;
/* 2379 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2380 */             out.write(10);
/*      */             
/* 2382 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2383 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2384 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2386 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2388 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2390 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2392 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2393 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2394 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2395 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2398 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2399 */               String scheduled = null;
/* 2400 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2401 */               out.write(10);
/*      */               
/* 2403 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2404 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2405 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2407 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2409 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2411 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2413 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2414 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2415 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2416 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2419 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2420 */                 String critical = null;
/* 2421 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2422 */                 out.write(10);
/*      */                 
/* 2424 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2425 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2426 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2428 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2430 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2432 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2434 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2435 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2436 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2437 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2440 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2441 */                   String clear = null;
/* 2442 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2443 */                   out.write(10);
/*      */                   
/* 2445 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2446 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2447 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2449 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2451 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2453 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2455 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2456 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2457 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2458 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2461 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2462 */                     String warning = null;
/* 2463 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2464 */                     out.write(10);
/* 2465 */                     out.write(10);
/*      */                     
/* 2467 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2468 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2470 */                     out.write(10);
/* 2471 */                     out.write(10);
/* 2472 */                     out.write(10);
/* 2473 */                     out.write(10);
/* 2474 */                     out.write(10);
/* 2475 */                     out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 2476 */                     if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                       return;
/* 2478 */                     out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/* 2479 */                     out.write(10);
/* 2480 */                     out.write("\n<script>\nfunction fnFormSubmit1()\n{\n\tif(trimAll(document.AMActionForm.host.value)=='')\n\t{\n\t\talert(\"Please Enter Server Name\");\n\t\tdocument.AMActionForm.host.focus();\n\t\treturn false;\n\t}\n\telse if(trimAll(document.AMActionForm.port.value)=='')\n\t{\n\t\talert(\"Please Enter Port Number\");\n\t\tdocument.AMActionForm.port.focus();\n\t\treturn false;\n\t}\n\n   document.AMActionForm.submit();\n}\n\nfunction fnFormSubmit()\n{\n\n");
/* 2481 */                     if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2483 */                     out.write(10);
/* 2484 */                     out.write(10);
/*      */                     
/* 2486 */                     org.apache.struts.taglib.logic.NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (org.apache.struts.taglib.logic.NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(org.apache.struts.taglib.logic.NotPresentTag.class);
/* 2487 */                     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2488 */                     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */                     
/* 2490 */                     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2491 */                     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2492 */                     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                       for (;;) {
/* 2494 */                         out.write("\n\n\tif(trimAll(document.AMActionForm.host.value)=='')\n\t{\n\t\talert(\"");
/* 2495 */                         out.print(FormatUtil.getString("am.webclient.addonproduct.servicedesk.alert.name"));
/* 2496 */                         out.write("\");\n\t\tdocument.AMActionForm.host.focus();\n\t\treturn false;\n\t}\n\telse if(trimAll(document.AMActionForm.port.value)=='')\n\t{\n\t\talert(\"");
/* 2497 */                         out.print(FormatUtil.getString("am.webclient.addonproduct.servicedesk.alert.port"));
/* 2498 */                         out.write("\");\n\t\tdocument.AMActionForm.port.focus();\n\t\treturn false;\n\t}\n\telse if(isNaN(trimAll(document.AMActionForm.port.value)))\n\t{\n\t\talert(\"");
/* 2499 */                         out.print(FormatUtil.getString("am.webclient.addonproduct.servicedesk.alert.notnumeric.port"));
/* 2500 */                         out.write("\");\n\t\tdocument.AMActionForm.port.focus();\n\t\treturn false;\n\t}\n\t\n\tif(document.AMActionForm.ticketingType.value == \"restapi\")\n\t{\n\t\tif(trimAll(document.AMActionForm.restApiKey.value)=='')\n\t\t{\n\t\t\talert(\"");
/* 2501 */                         out.print(FormatUtil.getString("am.webclient.sdp.restapikey.required.message"));
/* 2502 */                         out.write("\");\n\t\t\tdocument.AMActionForm.restApiKey.focus();\n\t\t\treturn false;\n\t\t}\n\t\tfrmSelectAllIncludingEmpty(document.AMActionForm.excludeFirstLevelMonitorTypes);\n\t\tfrmSelectAllIncludingEmpty(document.AMActionForm.includeSecondLevelMonitorTypes);\n\t}\n\telse if(trimAll(document.AMActionForm.username.value)=='')\n\t{\n\t\talert(\"");
/* 2503 */                         out.print(FormatUtil.getString("webclient.login.username.required.message"));
/* 2504 */                         out.write("\");\n\t\tdocument.AMActionForm.username.focus();\n\t\treturn false;\n\t}\n\telse if(trimAll(document.AMActionForm.password.value)=='')\n\t{\n\t\talert(\"");
/* 2505 */                         out.print(FormatUtil.getString("webclient.login.password.required.message"));
/* 2506 */                         out.write("\");\n\t\tdocument.AMActionForm.password.focus();\n\t\treturn false;\n\t}\n\telse if(trimAll(document.AMActionForm.toaddress.value)=='')\n    {\n\t    alert(\"");
/* 2507 */                         out.print(FormatUtil.getString("am.webclient.addonproduct.servicedesk.alert.toaddress"));
/* 2508 */                         out.write("\");\n\t\tdocument.AMActionForm.toaddress.focus();\n\t\treturn false;\n\t}\n\telse if(trimAll(document.AMActionForm.fromaddress.value)=='')\n\t{\n\t\talert(\"");
/* 2509 */                         out.print(FormatUtil.getString("am.webclient.addonproduct.servicedesk.alert.fromaddress"));
/* 2510 */                         out.write("\");\n\t\tdocument.AMActionForm.fromaddress.focus();\n\t\treturn false;\n\t}\n\n\t\tdocument.AMActionForm.submit();\n");
/* 2511 */                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2512 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2516 */                     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2517 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */                     }
/*      */                     else {
/* 2520 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2521 */                       out.write("\n}\nfunction changeProduct(productName)\n    {\n    \tif(productName==\"ServiceDesk\")\n\t    {\n    \t\tlocation.href= \"/adminAction.do?method=showSdeskConfiguration\";\n\t    }\n\t    else\n\t    {\n\t    \tlocation.href= \"/adminAction.do?method=showServiceNowConfiguration\";\t    \t\t\n\t    }\n    }\n\n\n</script>\n");
/*      */                       
/* 2523 */                       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2524 */                       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2525 */                       _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2526 */                       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2527 */                       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                         for (;;) {
/* 2529 */                           out.write(10);
/*      */                           
/* 2531 */                           WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2532 */                           _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2533 */                           _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                           
/* 2535 */                           _jspx_th_c_005fwhen_005f0.setTest("${param.global!='true'}");
/* 2536 */                           int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2537 */                           if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                             for (;;) {
/* 2539 */                               out.write(10);
/*      */                               
/* 2541 */                               org.apache.struts.taglib.tiles.InsertTag _jspx_th_tiles_005finsert_005f0 = (org.apache.struts.taglib.tiles.InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(org.apache.struts.taglib.tiles.InsertTag.class);
/* 2542 */                               _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2543 */                               _jspx_th_tiles_005finsert_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                               
/* 2545 */                               _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2546 */                               int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2547 */                               if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                                 for (;;) {
/* 2549 */                                   out.write(10);
/* 2550 */                                   out.write(32);
/* 2551 */                                   if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                                     return;
/* 2553 */                                   out.write("\n   ");
/* 2554 */                                   if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                                     return;
/* 2556 */                                   out.write(10);
/* 2557 */                                   out.write(10);
/*      */                                   
/* 2559 */                                   PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2560 */                                   _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2561 */                                   _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                                   
/* 2563 */                                   _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*      */                                   
/* 2565 */                                   _jspx_th_tiles_005fput_005f2.setType("string");
/* 2566 */                                   int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2567 */                                   if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2568 */                                     if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2569 */                                       out = _jspx_page_context.pushBody();
/* 2570 */                                       _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2571 */                                       _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 2574 */                                       out.write("\n\n<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2575 */                                       out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 2576 */                                       out.write(" &gt;<span class=\"bcactive\"> ");
/* 2577 */                                       out.print(FormatUtil.getString("am.webclient.addonproduct.servicedesk.title"));
/* 2578 */                                       out.write("</span></td>\n  </tr>\n</table>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td width=\"100%\" valign=\"top\">\n");
/* 2579 */                                       out.write("<!-- $Id$ -->\n\n");
/*      */                                       
/* 2581 */                                       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 2582 */                                       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2583 */                                       _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                       
/* 2585 */                                       _jspx_th_html_005fform_005f0.setAction("/adminAction.do");
/*      */                                       
/* 2587 */                                       _jspx_th_html_005fform_005f0.setStyle("display:inline");
/* 2588 */                                       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2589 */                                       if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                         for (;;) {
/* 2591 */                                           out.write("\n<am:hiddenparam name=\"global\"/>\n<am:hiddenparam name=\"returnpath\"/>\n");
/* 2592 */                                           if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2594 */                                           out.write(10);
/* 2595 */                                           if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2597 */                                           out.write(10);
/* 2598 */                                           if (_jspx_meth_c_005fif_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2600 */                                           out.write("\n\n\n<script type=\"text/javascript\">\n\n jQuery(document).ready(function() //No I18N\n {\n\t ");
/* 2601 */                                           if (_jspx_meth_c_005fchoose_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2603 */                                           out.write("\n\tshowAdvnSettings1();\n\tshowAdvnSettings2();\n\t \n});\n \nfunction showLoginDiv()\n{\n\tjQuery(\"input:hidden[name=ticketingType]\").val(\"credential\");  //NO I18N \n\tjQuery(\"#SDPapiDiv1\").hide();  //NO I18N\n\tjQuery(\"#loadingg\").hide();  //NO I18N\n\tjQuery(\"#useRestApi a span.tabarrows\").hide();  //NO I18N\n\tjQuery(\"#advanced_api_settings\").hide();//NO I18N\n\tjQuery(\"#SDPEdition\").hide();//NO I18N\n\t\n\tjQuery(\"#SDPlogin\").show();  //NO I18N\n\tjQuery(\"#SDPPassWord\").show();  //NO I18N\n\tjQuery(\"#fromaddress\").show();  //NO I18N\n\tjQuery(\"#toaddress\").show();  //NO I18N\n\tjQuery(\"#useCredential a span.tabarrows\").show();  //NO I18N\n\t\n\t ");
/* 2604 */                                           if (_jspx_meth_c_005fif_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2606 */                                           out.write("\n\t\n}\n\nfunction showApiDiv()\n{\n\tjQuery(\"input:hidden[name=ticketingType]\").val(\"restapi\");  //NO I18N\t\n\tjQuery(\"#SDPlogin\").hide();  //NO I18N\n\tjQuery(\"#SDPPassWord\").hide();  //NO I18N\n\tjQuery(\"#loadingg\").hide();  //NO I18N\n\tjQuery(\"#fromaddress\").hide();  //NO I18N\n\tjQuery(\"#toaddress\").hide();  //NO I18N\n\tjQuery(\"#useCredential a span.tabarrows\").hide();  //NO I18N\n\t\n\tjQuery(\"#SDPapiDiv1\").show();  //NO I18N\n\tjQuery(\"#advanced_api_settings\").show();//NO I18N\n\tjQuery(\"#useRestApi a span.tabarrows\").show();  //NO I18N\n\tjQuery(\"#SDPEdition\").show();  //NO I18N\n\t\n\tif((jQuery(\"input:hidden[name=valuesCleared]\").attr(\"value\")) == \"true\")\n\t{\n\t\tjQuery(\"input:text[name=host]\").val(\"");
/* 2607 */                                           if (_jspx_meth_c_005fout_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2609 */                                           out.write("\");  //NO I18N\n\t\tjQuery(\"input:text[name=port]\").val(\"");
/* 2610 */                                           if (_jspx_meth_c_005fout_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2612 */                                           out.write("\");  //NO I18N\n\t\t//jQuery(\"input:text[name=restApiKey]\").val(\"");
/* 2613 */                                           if (_jspx_meth_c_005fout_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2615 */                                           out.write("\");  //NO I18N\n\t}\n\t\n\t\n}\n\nfunction handleMSPDesk() {\n   if(document.getElementsByName('mspDesk')[1].checked){\n    jQuery(\"#sub_settings1\").hide();//NO I18N\n\tjQuery('h5').hide();//NO I18N\n\tjQuery(\"#sub-info-ci\").hide();//NO I18N\n   }\n else {\n   jQuery(\"#sub_settings1\").show();//NO I18N\n   jQuery('h5').show();//NO I18N\n   showAdvnSettings1();\n  }\n\n}\nfunction showAdvnSettings() {\n if(document.getElementById(\"advanced_settings\").checked == 1)\n  {\n   jQuery(\"#settings_container\").show();//NO I18N\n  }\n else {\n   jQuery(\"#settings_container\").hide();//NO I18N\n  }\n\n}\nfunction showAdvnSettings1() {\n if(document.getElementById(\"sub_settings1\").checked == 0)\n  {\n   jQuery(\"#sub-info-ci\").hide();//NO I18N\n  }\n else {\n   jQuery(\"#sub-info-ci\").show();//NO I18N\n  }\n}\nfunction showAdvnSettings2() {\n if(document.getElementById(\"sub_settings2\").checked == 0)\n  {\n   jQuery(\"#sub-info-ticket\").hide();//NO I18N\n  }\n else {\n   jQuery(\"#sub-info-ticket\").show();//NO I18N\n  }\n}\n</script>\n\n<style type=\"text/css\">\n\n.ulstyleforcf \n{\nlist-style-type: none;\n");
/* 2616 */                                           out.write("margin: 0px;\npadding:0px;\n}\n\n.listyleforcf \n{\n\tfloat: left;\n\tposition: relative;\n\tpadding-left: 0px;\n\tpadding-right: 0px;\n\n}\n\n.ulanchor{\nbackground-image: url(/images/icon_customfield_sep.gif);\nbackground-repeat: no-repeat;\nbackground-position: right;\npadding-right: 20px;\npadding-left: 20px;\nfloat:left;\nline-height: 50px;\ntext-decoration: none;\ncolor: #595959;\nfont-size: 11px;\nfont-weight: normal;\n}\n\n.ulanchoractive{\nbackground-image: url(/images/icon_customfield_sep.gif);\nbackground-repeat: no-repeat;\nbackground-position: right;\npadding-right: 20px;\npadding-left: 20px;\nfloat:left;\nline-height: 50px;\ntext-decoration: none;\ncolor: #595959;\nfont-size: 11px;\nfont-weight: bold;\n}\n\n\n/* #SDPTabs .liselectedforcf a span.tabarrows \n{\n\tdisplay: block;\n} */\n\n#SDPTabs span.tabarrows {\ntext-decoration: underline;\nbackground-image: url(/images/icon_customfield_select.gif);\nbackground-repeat: no-repeat;\npadding-top: 10px;\ntop: 52px;\nposition: absolute;\nwidth: 100%;\nhorizontal-align: center;\ndisplay: none;\nleft: 40%;\nheight: 18px;\n");
/* 2617 */                                           out.write("width: 34px;\n}\n</style>\n\n\t\t");
/* 2618 */                                           out.write("<!-- $Id$ -->\n<am:hiddenparam name=\"global\"/>\n<table width=\"99%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\">\n\t<tr>\n\t\t<td class=\"bodytext label-align\" width=\"25%\" nowrap=\"\" >\n\t\t\t\t");
/* 2619 */                                           out.print(FormatUtil.getString("am.webclient.select.helpDesk.product"));
/* 2620 */                                           out.write("\n\t\t</td> \n\t\t<td colspan=\"2\" class=\"bodytext\" width=\"75%\">\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t<tr valign=\"top\">\n\t\t\t\t\t\t<td class=\"bodytext\">\n\t\t\t\t\t\t\t");
/* 2621 */                                           if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2623 */                                           out.print(FormatUtil.getString("ManageEngine ServiceDesk Plus"));
/* 2624 */                                           out.write("&nbsp;&nbsp;\n\t\t\t\t\t\t\t");
/* 2625 */                                           if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2627 */                                           out.print(FormatUtil.getString("ServiceNow"));
/* 2628 */                                           out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t</td>\n\t</tr> \n</table>\n");
/* 2629 */                                           out.write(10);
/*      */                                           
/* 2631 */                                           String showSdeskLogTicket9 = (String)request.getAttribute("showSdeskLogTicket8");
/* 2632 */                                           if (showSdeskLogTicket9 != null)
/*      */                                           {
/*      */ 
/* 2635 */                                             out.write("\n <input type=\"hidden\" name=\"checkforSdesk\" value=\"showSdeskLogTicketcheck\" >\n ");
/*      */                                           }
/*      */                                           
/* 2638 */                                           String configureSdesk3 = (String)request.getAttribute("configureSdesk2");
/* 2639 */                                           if (configureSdesk3 != null)
/*      */                                           {
/*      */ 
/* 2642 */                                             out.write("\n <input type=\"hidden\" name=\"toconfigureSdesk\" value=\"configureSdeskCheck\" >\n ");
/*      */                                           }
/*      */                                           
/* 2645 */                                           String message = (String)request.getAttribute("success");
/* 2646 */                                           if (message != null)
/*      */                                           {
/* 2648 */                                             out.write("\n\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"messagebox\">\n\t  <tr>\n\t\t<td width=\"5%\" align=\"center\" valign=\"top\" class=\"bodytext\">\n\t    \t<img src=\"/images/icon_message_success.gif\" width=\"25\" height=\"25\" vspace=\"5\"></td>\n\t\t    <td width=\"95%\" class=\"bodytext\"  > ");
/* 2649 */                                             out.print(message);
/* 2650 */                                             out.write("</td>\n\t  </tr>\n\t</table>\n");
/*      */                                           }
/* 2652 */                                           out.write("\n\n         ");
/*      */                                           
/* 2654 */                                           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2655 */                                           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2656 */                                           _jspx_th_c_005fif_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                                           
/* 2658 */                                           _jspx_th_c_005fif_005f2.setTest("${!empty showerror}");
/* 2659 */                                           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2660 */                                           if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                                             for (;;) {
/* 2662 */                                               out.write("\n         ");
/*      */                                               
/* 2664 */                                               MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 2665 */                                               _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 2666 */                                               _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_c_005fif_005f2);
/*      */                                               
/* 2668 */                                               _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                                               
/* 2670 */                                               _jspx_th_html_005fmessages_005f0.setMessage("false");
/* 2671 */                                               int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 2672 */                                               if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 2673 */                                                 String msg = null;
/* 2674 */                                                 if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 2675 */                                                   out = _jspx_page_context.pushBody();
/* 2676 */                                                   _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/* 2677 */                                                   _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                                                 }
/* 2679 */                                                 msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                                                 for (;;) {
/* 2681 */                                                   out.write("\n         ");
/* 2682 */                                                   if (_jspx_meth_c_005fif_005f3(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                                                     return;
/* 2684 */                                                   out.write("\n\n                  ");
/* 2685 */                                                   if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                                                     return;
/* 2687 */                                                   out.write("<br>\n\n               ");
/* 2688 */                                                   int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 2689 */                                                   msg = (String)_jspx_page_context.findAttribute("msg");
/* 2690 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 2693 */                                                 if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 2694 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 2697 */                                               if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 2698 */                                                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                                               }
/*      */                                               
/* 2701 */                                               this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/* 2702 */                                               out.write("\n               ");
/* 2703 */                                               if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                                 return;
/* 2705 */                                               out.write("\n             <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                          <tr>\n                            <td  height=\"10\" ><img src=\"../images/spacer.gif\" ></td>\n                           </tr>\n</table>\n        ");
/* 2706 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2707 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 2711 */                                           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2712 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                                           }
/*      */                                           
/* 2715 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2716 */                                           out.write("\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtborder\">\n <tr >\n <td width=\"4%\" class=\"tableheading-monitor-config \"><img src=\"/images/sdp.gif\" class=\"tableheading-add-icon\"></td>\n <td class=\"tableheading-monitor-config\">\n\n<div id=\"SDPTabs\">\n\t<ul class=\"ulstyleforcf\">\n\t\t<li class=\"listyleforcf\" id=\"useRestApi\">\n\t\t\t<a href=\"javascript:void(0)\" class=\"ulanchor\" onclick=\"showApiDiv()\">");
/* 2717 */                                           out.print(FormatUtil.getString("am.webclient.sdp.userestapi.text"));
/* 2718 */                                           out.write(" \n\t\t\t\t<span class=\"tabarrows\"></span>\n\t\t\t</a>\n\t\t</li>\n\t\t<li class=\"listyleforcf\" id=\"useCredential\">\n\t\t\t<a href=\"javascript:void(0)\" class=\"ulanchor\" onclick=\"showLoginDiv()\">");
/* 2719 */                                           out.print(FormatUtil.getString("am.webclient.sdp.usecredential.text"));
/* 2720 */                                           out.write(" \n\t\t\t\t<span class=\"tabarrows\"></span>\n\t\t\t</a>\n\t\t</li>\n\t</ul>\n</div>\n</td>\n </tr>\n</table>\n\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbborder\">\n<tr>\n<input type=\"hidden\" id=\"methodName\" name=\"method\" value=\"SdeskConfiguration\" >\n<td>\n <table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\" >\n <tr>\n  <td>\n  <!-- <table width=\"99%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" id=\"SDPlogin\"> -->\n  <table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n      <tr>\n\t     <td class=\"bodytextbold\" style=\"padding-top: 20px;\" colspan=\"2\">");
/* 2721 */                                           out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.serverdetail"));
/* 2722 */                                           out.write("</td>\n\t  </tr>\n     <tr id=\"SDPEdition\">\n\t<td width=\"25%\" height=\"28\" class=\"bodytext label-align\" >");
/* 2723 */                                           out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.servereditiondetail"));
/* 2724 */                                           out.write("<span class=\"mandatory\">*</span></td>");
/* 2725 */                                           out.write("\n\t  <td width=\"75%\" height=\"28\" colspan=\"2\" >");
/* 2726 */                                           if (_jspx_meth_html_005fradio_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2728 */                                           out.print(FormatUtil.getString("am.extprod.sdp"));
/* 2729 */                                           out.write("&nbsp;&nbsp;&nbsp;");
/* 2730 */                                           if (_jspx_meth_html_005fradio_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2732 */                                           out.write("<span class=\"bodytext\">");
/* 2733 */                                           out.print(FormatUtil.getString("am.extprod.mspdesk"));
/* 2734 */                                           out.write("</span>");
/* 2735 */                                           out.write("</td>\n    </tr>\n\t<tr>\n        <td width=\"25%\" class=\"bodytext label-align\">");
/* 2736 */                                           out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.servername"));
/* 2737 */                                           out.write("<span class=\"mandatory\">*</span></td>\n\t\t<td width=\"75%\" class=\"bodytext\">");
/* 2738 */                                           if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2740 */                                           out.write("</td>\n\t  </tr>\n\t <tr>\n\t     <td width=\"25%\" class=\"bodytext label-align\">");
/* 2741 */                                           out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.serverport"));
/* 2742 */                                           out.write("<span class=\"mandatory\">*</span></td>\n\t\t <td width=\"75%\" class=\"bodytext\">");
/* 2743 */                                           if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2745 */                                           out.write("</td>\n\t </tr>\n\t \n\t <tr id=\"SDPlogin\">\n\t    <td width=\"25%\" class=\"bodytext label-align\">");
/* 2746 */                                           out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.authlogin"));
/* 2747 */                                           out.write("<span class=\"mandatory\">*</span></td>\n\t\t <td width=\"75%\" class=\"bodytext\">");
/* 2748 */                                           if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2750 */                                           out.write("</td>\n\t </tr>\n\t <tr id=\"SDPPassWord\">\n\t     <td width=\"25%\" class=\"bodytext label-align\">");
/* 2751 */                                           out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.authpassword"));
/* 2752 */                                           out.write("<span class=\"mandatory\">*</span></td>\n\t\t  <td width=\"75%\" class=\"bodytext\">");
/* 2753 */                                           if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2755 */                                           out.write("</td>\n\t </tr>\n\t\n   \t<tr id=\"SDPapiDiv1\">\n\t\t<td width=\"25%\" class=\"bodytext label-align\">");
/* 2756 */                                           out.print(FormatUtil.getString("am.webclient.sdp.restapikey.text"));
/* 2757 */                                           out.write("<span class=\"mandatory\">*</span></td>\n\t\t<td width=\"75%\" class=\"bodytext\">");
/* 2758 */                                           if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2760 */                                           out.write("</td>\n \t</tr>\n\t \n\t  <tr>\n\t  <td height=\"28\" class=\"bodytext label-align\" >");
/* 2761 */                                           out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.Protocol"));
/* 2762 */                                           out.write("<span class=\"mandatory\">*</span></TD>\n\t  <td height=\"28\" colspan=\"2\" >");
/* 2763 */                                           if (_jspx_meth_html_005fradio_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2765 */                                           out.write("<span class=\"bodytext\">");
/* 2766 */                                           out.print(FormatUtil.getString("am.extprod.http"));
/* 2767 */                                           out.write("&nbsp;&nbsp;&nbsp;");
/* 2768 */                                           if (_jspx_meth_html_005fradio_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2770 */                                           out.print(FormatUtil.getString("am.extprod.https"));
/* 2771 */                                           out.write("</span>\n\t  </td>\n     </tr>\n\t\t <tr id=\"toaddress\">\n\t   <td width=\"25%\" class=\"bodytext label-align\">");
/* 2772 */                                           out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.toaddress"));
/* 2773 */                                           out.write("<span class=\"mandatory\">*</span></td>\n\t    <td width=\"75%\" class=\"bodytext\">");
/* 2774 */                                           if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2776 */                                           out.write("</td>\n\t </tr>\n\t <tr id=\"fromaddress\">\n\t   <td width=\"25%\" class=\"bodytext label-align\">");
/* 2777 */                                           out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.fromaddress"));
/* 2778 */                                           out.write("<span class=\"mandatory\">*</span></td>\n\t    <td  width=\"75%\" class=\"bodytext\">");
/* 2779 */                                           if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2781 */                                           out.write("</td>\n\t </tr>\n</table>\n\n");
/* 2782 */                                           out.write("<!--$Id$-->\n<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" id=\"advanced_api_settings\">\n   <tr>\n      <td>\n         <h3 class=\"head-settings\"><input type=\"checkbox\" name=\"advanced_settings\" onChange=\"javascript:showAdvnSettings(),handleMSPDesk()\" id=\"advanced_settings\" />");
/* 2783 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.advancedsettings"));
/* 2784 */                                           out.write("</h3>\n         <div id=\"settings_container\" style=\"display: none;\">\n\t\t \n          <h7 class=\"head-settings\">");
/* 2785 */                                           if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2787 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.ticket.enableticketing"));
/* 2788 */                                           out.write(" </h7>");
/* 2789 */                                           out.write("\n            <div class=\"sub-info\" id=\"sub-info-ticket\">\n            <fieldset class=\"fieldset_replace\">\n               <legend> ");
/* 2790 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.ticket.ticketsetting"));
/* 2791 */                                           out.write(" </legend>\n               <table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"ci-setting-container\">\n                  <tbody>\n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 2792 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.ticket.reopenpolicy"));
/* 2793 */                                           out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"input-align-td\">\n                           <tbody>\n                                 <tr>\n                                    <td>");
/* 2794 */                                           if (_jspx_meth_html_005fradio_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2796 */                                           out.write("</td>\t\t\t\t\t\t\t\t\n                                    <td>");
/* 2797 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.ticket.reopen.always"));
/* 2798 */                                           out.write("</td>\n                                 </tr>\n                                 <tr>\n                                    <td>");
/* 2799 */                                           if (_jspx_meth_html_005fradio_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2801 */                                           out.write("</td>\n                                    <td>");
/* 2802 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.ticket.reopen.period.1"));
/* 2803 */                                           out.write(32);
/* 2804 */                                           if (_jspx_meth_html_005ftext_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2806 */                                           out.write(32);
/* 2807 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.ticket.reopen.period.2"));
/* 2808 */                                           out.write("</td>\n                                 </tr>\n                                \n                                 <tr>\n                                    <td>");
/* 2809 */                                           if (_jspx_meth_html_005fradio_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2811 */                                           out.write("</td>\n                                    <td>");
/* 2812 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.ticket.createticket"));
/* 2813 */                                           out.write("</td>\n                                 </tr>\n                                 \n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     \n                     \n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 2814 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.ticket.autoclosepolicy"));
/* 2815 */                                           out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"input-align-td\">\n                           <tbody>\n                                 <tr>\n                                    <td>");
/* 2816 */                                           if (_jspx_meth_html_005fradio_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2818 */                                           out.write("</td>\t\t\t\t\t\t\t\t\n                                    <td>");
/* 2819 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.ticket.closeticket"));
/* 2820 */                                           out.write("</td>\n                                 </tr>\n                                 <tr>\n                                    <td>");
/* 2821 */                                           if (_jspx_meth_html_005fradio_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2823 */                                           out.write("</td>\n                                    <td>");
/* 2824 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.ticket.updatenotes"));
/* 2825 */                                           out.write("</td>\n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     \n                     \n                     \n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 2826 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.ticket.addnotes"));
/* 2827 */                                           out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 2828 */                                           if (_jspx_meth_html_005fradio_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2830 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 2831 */                                           out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 2832 */                                           if (_jspx_meth_html_005fradio_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2834 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 2835 */                                           out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     \n                     \n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 2836 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.ticket.dynamicticketing.action"));
/* 2837 */                                           out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 2838 */                                           if (_jspx_meth_html_005fradio_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2840 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 2841 */                                           out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 2842 */                                           if (_jspx_meth_html_005fradio_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2844 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 2845 */                                           out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 2846 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.ticket.dynamicticketing.form"));
/* 2847 */                                           out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 2848 */                                           if (_jspx_meth_html_005fradio_005f15(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2850 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 2851 */                                           out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 2852 */                                           if (_jspx_meth_html_005fradio_005f16(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2854 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 2855 */                                           out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     \n                       ");
/* 2856 */                                           out.write("\n                     \n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 2857 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.ticket.ticketdetailsfromalrm", new Object[] { FormatUtil.getString("product.name") }));
/* 2858 */                                           out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 2859 */                                           if (_jspx_meth_html_005fradio_005f17(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2861 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 2862 */                                           out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 2863 */                                           if (_jspx_meth_html_005fradio_005f18(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2865 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 2866 */                                           out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t ");
/*      */                                           
/* 2868 */                                           IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2869 */                                           _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2870 */                                           _jspx_th_c_005fif_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */                                           
/* 2872 */                                           _jspx_th_c_005fif_005f5.setTest("${showAllSettings=='true'}");
/* 2873 */                                           int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2874 */                                           if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                             for (;;) {
/* 2876 */                                               out.write("\n\t\t\t\t\t <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 2877 */                                               out.print(FormatUtil.getString("me.sdp.cmdb.ticket.readonly"));
/* 2878 */                                               out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 2879 */                                               if (_jspx_meth_html_005fradio_005f19(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                                                 return;
/* 2881 */                                               out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 2882 */                                               out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 2883 */                                               if (_jspx_meth_html_005fradio_005f20(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                                                 return;
/* 2885 */                                               out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 2886 */                                               out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t \n\t\t\t\t\t <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 2887 */                                               out.print(FormatUtil.getString("me.sdp.cmdb.update.ticket.status.update"));
/* 2888 */                                               out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 2889 */                                               if (_jspx_meth_html_005fradio_005f21(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                                                 return;
/* 2891 */                                               out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 2892 */                                               out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 2893 */                                               if (_jspx_meth_html_005fradio_005f22(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                                                 return;
/* 2895 */                                               out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 2896 */                                               out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t ");
/*      */                                               
/* 2898 */                                               IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2899 */                                               _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2900 */                                               _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fif_005f5);
/*      */                                               
/* 2902 */                                               _jspx_th_c_005fif_005f6.setTest("${isServiceNow ne 'true'}");
/* 2903 */                                               int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2904 */                                               if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                                 for (;;) {
/* 2906 */                                                   out.write("\n\t\t\t\t\t  <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 2907 */                                                   out.print(FormatUtil.getString("me.sdp.cmdb.reqTemplate.overwrite"));
/* 2908 */                                                   out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 2909 */                                                   if (_jspx_meth_html_005fradio_005f23(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                                     return;
/* 2911 */                                                   out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 2912 */                                                   out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 2913 */                                                   if (_jspx_meth_html_005fradio_005f24(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                                     return;
/* 2915 */                                                   out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 2916 */                                                   out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t ");
/* 2917 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2918 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 2922 */                                               if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2923 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                                               }
/*      */                                               
/* 2926 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2927 */                                               out.write("\n                    ");
/* 2928 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2929 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 2933 */                                           if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2934 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                           }
/*      */                                           
/* 2937 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2938 */                                           out.write("\n                 </tbody>\n               </table></fieldset>\n            </div>\n\t\t\t <h5 class=\"head-settings\">");
/* 2939 */                                           if (_jspx_meth_html_005fcheckbox_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2941 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.ci.cisync.enable"));
/* 2942 */                                           out.write(" </h5>");
/* 2943 */                                           out.write("\n         <div class=\"sub-info\" id=\"sub-info-ci\">\n            <fieldset class=\"fieldset_replace\">\n               <legend> ");
/* 2944 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.ci.cisettings"));
/* 2945 */                                           out.write(" </legend>\n               <table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"ci-setting-container\">\n                  <tbody>\n                     <tr>\n                        <td class=\"bodytext label-align\">");
/* 2946 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.ci.deleteci"));
/* 2947 */                                           out.write("</td>\n                        <td colspan=\"3\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 2948 */                                           if (_jspx_meth_html_005fradio_005f25(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2950 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 2951 */                                           out.write("</td>\n                                    <td width=\"75\">");
/* 2952 */                                           if (_jspx_meth_html_005fradio_005f26(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2954 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 2955 */                                           out.write("</td>\n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     \n                     \n                     <tr width=\"100%\">\n                     \t<td width=\"45%\" valign=\"top\" class=\"bodytext label-align\">");
/* 2956 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.ci.firstlevel.excludetype"));
/* 2957 */                                           out.write("</td>\n                        <td width=\"25%\">\n                        ");
/* 2958 */                                           if (_jspx_meth_html_005fselect_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2960 */                                           out.write("\t\t\t\t\n                        </td>\n                        <td width=\"5%\" align=\"center\" class=\"bodytext\">\n                           <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tr>\n                                 <td align=\"center\">");
/* 2961 */                                           if (_jspx_meth_html_005fbutton_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2963 */                                           out.write("</td>\n                              </tr>\n                              <tr>\n                                 <td  align=\"center\">");
/* 2964 */                                           if (_jspx_meth_html_005fbutton_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2966 */                                           out.write("</td>\n                             </tr>\n                              <tr>\n                                 <td  align=\"center\" valign=\"bottom\">");
/* 2967 */                                           if (_jspx_meth_html_005fbutton_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2969 */                                           out.write("</td>\n                              </tr>\n                              <tr>\n                                 <td  align=\"center\" valign=\"bottom\">");
/* 2970 */                                           if (_jspx_meth_html_005fbutton_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2972 */                                           out.write("</td>\n                              </tr>\n                           </table>\n                        </td>\n                        <td width=\"25%\">\t\t\t\t\t\t\t\n                           ");
/* 2973 */                                           if (_jspx_meth_html_005fselect_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 2975 */                                           out.write("\t\t\t\t\t\t\t\n                        </td>\n                     </tr>\n                     \n                     ");
/*      */                                           
/* 2977 */                                           IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2978 */                                           _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2979 */                                           _jspx_th_c_005fif_005f7.setParent(_jspx_th_html_005fform_005f0);
/*      */                                           
/* 2981 */                                           _jspx_th_c_005fif_005f7.setTest("${showAllSettings=='true'}");
/* 2982 */                                           int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2983 */                                           if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                             for (;;) {
/* 2985 */                                               out.write("\n                     <tr width=\"100%\">\n                     \t<td width=\"45%\" valign=\"top\" class=\"bodytext label-align\">");
/* 2986 */                                               out.print(FormatUtil.getString("me.sdp.cmdb.ci.secondlevel.includetype"));
/* 2987 */                                               out.write("</td>\n                        <td width=\"25%\">\t\t\t\t\t\n                           ");
/* 2988 */                                               if (_jspx_meth_html_005fselect_005f2(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                                 return;
/* 2990 */                                               out.write("\t\t\t\t\t\n                        </td>\n                        <td width=\"5%\" align=\"center\" class=\"bodytext\">\n                           <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tr>\n                                 <td align=\"center\">");
/* 2991 */                                               if (_jspx_meth_html_005fbutton_005f4(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                                 return;
/* 2993 */                                               out.write("</td>\n                              </tr>\n                              <tr>\n                                 <td  align=\"center\">");
/* 2994 */                                               if (_jspx_meth_html_005fbutton_005f5(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                                 return;
/* 2996 */                                               out.write("</td>\n                             </tr>\n                              <tr>\n                                 <td  align=\"center\" valign=\"bottom\">");
/* 2997 */                                               if (_jspx_meth_html_005fbutton_005f6(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                                 return;
/* 2999 */                                               out.write("</td>\n                              </tr>\n                              <tr>\n                                 <td  align=\"center\" valign=\"bottom\">");
/* 3000 */                                               if (_jspx_meth_html_005fbutton_005f7(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                                 return;
/* 3002 */                                               out.write("</td>\n                              </tr>\n                           </table>\n                        </td>\n                        <td width=\"25%\">\t\t\t\t\t\t\t\n                           ");
/* 3003 */                                               if (_jspx_meth_html_005fselect_005f3(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                                 return;
/* 3005 */                                               out.write("\t\t\t\t\t\t\t\t\n                        </td>\n                     </tr>\n                     ");
/* 3006 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3007 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3011 */                                           if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3012 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                           }
/*      */                                           
/* 3015 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3016 */                                           out.write("\n                     <tr>\n                     \t<td class=\"bodytext label-align\">");
/* 3017 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.ci.customattributes"));
/* 3018 */                                           out.write("</td>\n                        <td colspan=\"3\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3019 */                                           if (_jspx_meth_html_005fradio_005f27(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 3021 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3022 */                                           out.write("</td>\n                                    \n                                    <td width=\"75\">");
/* 3023 */                                           if (_jspx_meth_html_005fradio_005f28(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 3025 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3026 */                                           out.write("</td>\n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     <tr>\n                     \t<td class=\"bodytext label-align\">");
/* 3027 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.ci.rlmapfromsnapshot", new Object[] { FormatUtil.getString("product.name") }));
/* 3028 */                                           out.write("</td>\n                        <td colspan=\"3\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3029 */                                           if (_jspx_meth_html_005fradio_005f29(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 3031 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3032 */                                           out.write("</td>\n                                    \n                                    <td width=\"75\">");
/* 3033 */                                           if (_jspx_meth_html_005fradio_005f30(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                             return;
/* 3035 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3036 */                                           out.write("</td>\n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t ");
/*      */                                           
/* 3038 */                                           IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3039 */                                           _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3040 */                                           _jspx_th_c_005fif_005f8.setParent(_jspx_th_html_005fform_005f0);
/*      */                                           
/* 3042 */                                           _jspx_th_c_005fif_005f8.setTest("${showAllSettings=='true'}");
/* 3043 */                                           int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3044 */                                           if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                             for (;;) {
/* 3046 */                                               out.write("\n\t\t\t\t\t   <tr>\n                     \t<td class=\"bodytext label-align\">");
/* 3047 */                                               out.print(FormatUtil.getString("me.sdp.cmdb.ci.rlmap.withlist"));
/* 3048 */                                               out.write("</td>\n                        <td colspan=\"3\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3049 */                                               if (_jspx_meth_html_005fradio_005f31(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*      */                                                 return;
/* 3051 */                                               out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3052 */                                               out.write("</td>\n                                    \n                                    <td width=\"75\">");
/* 3053 */                                               if (_jspx_meth_html_005fradio_005f32(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*      */                                                 return;
/* 3055 */                                               out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3056 */                                               out.write("</td>\n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t ");
/* 3057 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3058 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3062 */                                           if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3063 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                                           }
/*      */                                           
/* 3066 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3067 */                                           out.write("\n                  </tbody>\n               </table></fieldset>\n            </div>\n            </div>\n      </td>\n\t </tr>\n</table>\n");
/* 3068 */                                           out.write("\n\n</td>\n</tr>\n<tr>\n<td>\n<table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" >\n <tr>\n    <td width=\"25%\" class=\"tablebottom\">&nbsp;</td>\n\t<td width=\"75%\" height=\"31\" class=\"tablebottom\" ><input name=\"Submit\" value=\"");
/* 3069 */                                           out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.submit"));
/* 3070 */                                           out.write("\" type=\"button\" class=\"buttons btn_highlt\" onclick=\"fnFormSubmit();\">\n\t<input type=\"reset\" align=\"center\" class=\"buttons btn_reset\" value=\"");
/* 3071 */                                           out.print(FormatUtil.getString("webclient.admin.adduser.clear"));
/* 3072 */                                           out.write("\">\n\t");
/*      */                                           
/* 3074 */                                           if ((request.getAttribute("showSdeskLogTicket8") != null) || (request.getAttribute("configureSdesk2") != null))
/*      */                                           {
/*      */ 
/* 3077 */                                             out.write("\n\t<input type=\"button\" align=\"center\" class=\"buttons btn_link\" value=\"");
/* 3078 */                                             out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.cancel"));
/* 3079 */                                             out.write("\" onclick=\"javascript:history.back();\">\n\t");
/*      */ 
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 3084 */                                             out.write("\n\t<input type=\"button\" align=\"center\" class=\"buttons btn_link\" value=\"");
/* 3085 */                                             out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.cancel"));
/* 3086 */                                             out.write("\" onclick=\"javascript:history.back();\">\n\t");
/*      */                                           }
/* 3088 */                                           out.write("\n\t</td>\n </tr>\n</table>\n</td>\n</tr>\n</table>\n</td>\n</tr>\n</table>\n ");
/* 3089 */                                           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 3090 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3094 */                                       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 3095 */                                         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                                       }
/*      */                                       
/* 3098 */                                       this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 3099 */                                       out.write(10);
/* 3100 */                                       out.write(10);
/* 3101 */                                       out.write("\n</td>\n</tr>\n\n<tr>\n<td width=\"100%\" valign=\"top\">\n");
/* 3102 */                                       org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admin.servicedesk.helpcard")), request.getCharacterEncoding()), out, false);
/* 3103 */                                       out.write("\n</td>\n</tr>\n</table>\n ");
/* 3104 */                                       int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 3105 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3108 */                                     if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 3109 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3112 */                                   if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3113 */                                     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                                   }
/*      */                                   
/* 3116 */                                   this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 3117 */                                   out.write("\n     ");
/* 3118 */                                   if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                                     return;
/* 3120 */                                   out.write(10);
/* 3121 */                                   out.write(32);
/* 3122 */                                   int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3123 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3127 */                               if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3128 */                                 this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0); return;
/*      */                               }
/*      */                               
/* 3131 */                               this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3132 */                               out.write(32);
/* 3133 */                               out.write(10);
/* 3134 */                               out.write(32);
/* 3135 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3136 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3140 */                           if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3141 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                           }
/*      */                           
/* 3144 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3145 */                           out.write(10);
/*      */                           
/* 3147 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3148 */                           _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 3149 */                           _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f0);
/* 3150 */                           int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 3151 */                           if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                             for (;;) {
/* 3153 */                               out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td width=\"100%\" valign=\"top\">\n\t\t");
/* 3154 */                               out.write("<!-- $Id$ -->\n\n");
/*      */                               
/* 3156 */                               FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 3157 */                               _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/* 3158 */                               _jspx_th_html_005fform_005f1.setParent(_jspx_th_c_005fotherwise_005f1);
/*      */                               
/* 3160 */                               _jspx_th_html_005fform_005f1.setAction("/adminAction.do");
/*      */                               
/* 3162 */                               _jspx_th_html_005fform_005f1.setStyle("display:inline");
/* 3163 */                               int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/* 3164 */                               if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */                                 for (;;) {
/* 3166 */                                   out.write("\n<am:hiddenparam name=\"global\"/>\n<am:hiddenparam name=\"returnpath\"/>\n");
/* 3167 */                                   if (_jspx_meth_html_005fhidden_005f2(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3169 */                                   out.write(10);
/* 3170 */                                   if (_jspx_meth_html_005fhidden_005f3(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3172 */                                   out.write(10);
/* 3173 */                                   if (_jspx_meth_c_005fif_005f9(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3175 */                                   out.write("\n\n\n<script type=\"text/javascript\">\n\n jQuery(document).ready(function() //No I18N\n {\n\t ");
/* 3176 */                                   if (_jspx_meth_c_005fchoose_005f2(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3178 */                                   out.write("\n\tshowAdvnSettings1();\n\tshowAdvnSettings2();\n\t \n});\n \nfunction showLoginDiv()\n{\n\tjQuery(\"input:hidden[name=ticketingType]\").val(\"credential\");  //NO I18N \n\tjQuery(\"#SDPapiDiv1\").hide();  //NO I18N\n\tjQuery(\"#loadingg\").hide();  //NO I18N\n\tjQuery(\"#useRestApi a span.tabarrows\").hide();  //NO I18N\n\tjQuery(\"#advanced_api_settings\").hide();//NO I18N\n\tjQuery(\"#SDPEdition\").hide();//NO I18N\n\t\n\tjQuery(\"#SDPlogin\").show();  //NO I18N\n\tjQuery(\"#SDPPassWord\").show();  //NO I18N\n\tjQuery(\"#fromaddress\").show();  //NO I18N\n\tjQuery(\"#toaddress\").show();  //NO I18N\n\tjQuery(\"#useCredential a span.tabarrows\").show();  //NO I18N\n\t\n\t ");
/* 3179 */                                   if (_jspx_meth_c_005fif_005f10(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3181 */                                   out.write("\n\t\n}\n\nfunction showApiDiv()\n{\n\tjQuery(\"input:hidden[name=ticketingType]\").val(\"restapi\");  //NO I18N\t\n\tjQuery(\"#SDPlogin\").hide();  //NO I18N\n\tjQuery(\"#SDPPassWord\").hide();  //NO I18N\n\tjQuery(\"#loadingg\").hide();  //NO I18N\n\tjQuery(\"#fromaddress\").hide();  //NO I18N\n\tjQuery(\"#toaddress\").hide();  //NO I18N\n\tjQuery(\"#useCredential a span.tabarrows\").hide();  //NO I18N\n\t\n\tjQuery(\"#SDPapiDiv1\").show();  //NO I18N\n\tjQuery(\"#advanced_api_settings\").show();//NO I18N\n\tjQuery(\"#useRestApi a span.tabarrows\").show();  //NO I18N\n\tjQuery(\"#SDPEdition\").show();  //NO I18N\n\t\n\tif((jQuery(\"input:hidden[name=valuesCleared]\").attr(\"value\")) == \"true\")\n\t{\n\t\tjQuery(\"input:text[name=host]\").val(\"");
/* 3182 */                                   if (_jspx_meth_c_005fout_005f4(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3184 */                                   out.write("\");  //NO I18N\n\t\tjQuery(\"input:text[name=port]\").val(\"");
/* 3185 */                                   if (_jspx_meth_c_005fout_005f5(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3187 */                                   out.write("\");  //NO I18N\n\t\t//jQuery(\"input:text[name=restApiKey]\").val(\"");
/* 3188 */                                   if (_jspx_meth_c_005fout_005f6(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3190 */                                   out.write("\");  //NO I18N\n\t}\n\t\n\t\n}\n\nfunction handleMSPDesk() {\n   if(document.getElementsByName('mspDesk')[1].checked){\n    jQuery(\"#sub_settings1\").hide();//NO I18N\n\tjQuery('h5').hide();//NO I18N\n\tjQuery(\"#sub-info-ci\").hide();//NO I18N\n   }\n else {\n   jQuery(\"#sub_settings1\").show();//NO I18N\n   jQuery('h5').show();//NO I18N\n   showAdvnSettings1();\n  }\n\n}\nfunction showAdvnSettings() {\n if(document.getElementById(\"advanced_settings\").checked == 1)\n  {\n   jQuery(\"#settings_container\").show();//NO I18N\n  }\n else {\n   jQuery(\"#settings_container\").hide();//NO I18N\n  }\n\n}\nfunction showAdvnSettings1() {\n if(document.getElementById(\"sub_settings1\").checked == 0)\n  {\n   jQuery(\"#sub-info-ci\").hide();//NO I18N\n  }\n else {\n   jQuery(\"#sub-info-ci\").show();//NO I18N\n  }\n}\nfunction showAdvnSettings2() {\n if(document.getElementById(\"sub_settings2\").checked == 0)\n  {\n   jQuery(\"#sub-info-ticket\").hide();//NO I18N\n  }\n else {\n   jQuery(\"#sub-info-ticket\").show();//NO I18N\n  }\n}\n</script>\n\n<style type=\"text/css\">\n\n.ulstyleforcf \n{\nlist-style-type: none;\n");
/* 3191 */                                   out.write("margin: 0px;\npadding:0px;\n}\n\n.listyleforcf \n{\n\tfloat: left;\n\tposition: relative;\n\tpadding-left: 0px;\n\tpadding-right: 0px;\n\n}\n\n.ulanchor{\nbackground-image: url(/images/icon_customfield_sep.gif);\nbackground-repeat: no-repeat;\nbackground-position: right;\npadding-right: 20px;\npadding-left: 20px;\nfloat:left;\nline-height: 50px;\ntext-decoration: none;\ncolor: #595959;\nfont-size: 11px;\nfont-weight: normal;\n}\n\n.ulanchoractive{\nbackground-image: url(/images/icon_customfield_sep.gif);\nbackground-repeat: no-repeat;\nbackground-position: right;\npadding-right: 20px;\npadding-left: 20px;\nfloat:left;\nline-height: 50px;\ntext-decoration: none;\ncolor: #595959;\nfont-size: 11px;\nfont-weight: bold;\n}\n\n\n/* #SDPTabs .liselectedforcf a span.tabarrows \n{\n\tdisplay: block;\n} */\n\n#SDPTabs span.tabarrows {\ntext-decoration: underline;\nbackground-image: url(/images/icon_customfield_select.gif);\nbackground-repeat: no-repeat;\npadding-top: 10px;\ntop: 52px;\nposition: absolute;\nwidth: 100%;\nhorizontal-align: center;\ndisplay: none;\nleft: 40%;\nheight: 18px;\n");
/* 3192 */                                   out.write("width: 34px;\n}\n</style>\n\n\t\t");
/* 3193 */                                   out.write("<!-- $Id$ -->\n<am:hiddenparam name=\"global\"/>\n<table width=\"99%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\">\n\t<tr>\n\t\t<td class=\"bodytext label-align\" width=\"25%\" nowrap=\"\" >\n\t\t\t\t");
/* 3194 */                                   out.print(FormatUtil.getString("am.webclient.select.helpDesk.product"));
/* 3195 */                                   out.write("\n\t\t</td> \n\t\t<td colspan=\"2\" class=\"bodytext\" width=\"75%\">\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t<tr valign=\"top\">\n\t\t\t\t\t\t<td class=\"bodytext\">\n\t\t\t\t\t\t\t");
/* 3196 */                                   if (_jspx_meth_html_005fradio_005f33(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3198 */                                   out.print(FormatUtil.getString("ManageEngine ServiceDesk Plus"));
/* 3199 */                                   out.write("&nbsp;&nbsp;\n\t\t\t\t\t\t\t");
/* 3200 */                                   if (_jspx_meth_html_005fradio_005f34(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3202 */                                   out.print(FormatUtil.getString("ServiceNow"));
/* 3203 */                                   out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t</td>\n\t</tr> \n</table>\n");
/* 3204 */                                   out.write(10);
/*      */                                   
/* 3206 */                                   String showSdeskLogTicket9 = (String)request.getAttribute("showSdeskLogTicket8");
/* 3207 */                                   if (showSdeskLogTicket9 != null)
/*      */                                   {
/*      */ 
/* 3210 */                                     out.write("\n <input type=\"hidden\" name=\"checkforSdesk\" value=\"showSdeskLogTicketcheck\" >\n ");
/*      */                                   }
/*      */                                   
/* 3213 */                                   String configureSdesk3 = (String)request.getAttribute("configureSdesk2");
/* 3214 */                                   if (configureSdesk3 != null)
/*      */                                   {
/*      */ 
/* 3217 */                                     out.write("\n <input type=\"hidden\" name=\"toconfigureSdesk\" value=\"configureSdeskCheck\" >\n ");
/*      */                                   }
/*      */                                   
/* 3220 */                                   String message = (String)request.getAttribute("success");
/* 3221 */                                   if (message != null)
/*      */                                   {
/* 3223 */                                     out.write("\n\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"messagebox\">\n\t  <tr>\n\t\t<td width=\"5%\" align=\"center\" valign=\"top\" class=\"bodytext\">\n\t    \t<img src=\"/images/icon_message_success.gif\" width=\"25\" height=\"25\" vspace=\"5\"></td>\n\t\t    <td width=\"95%\" class=\"bodytext\"  > ");
/* 3224 */                                     out.print(message);
/* 3225 */                                     out.write("</td>\n\t  </tr>\n\t</table>\n");
/*      */                                   }
/* 3227 */                                   out.write("\n\n         ");
/*      */                                   
/* 3229 */                                   IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3230 */                                   _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3231 */                                   _jspx_th_c_005fif_005f11.setParent(_jspx_th_html_005fform_005f1);
/*      */                                   
/* 3233 */                                   _jspx_th_c_005fif_005f11.setTest("${!empty showerror}");
/* 3234 */                                   int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3235 */                                   if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                                     for (;;) {
/* 3237 */                                       out.write("\n         ");
/*      */                                       
/* 3239 */                                       MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 3240 */                                       _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/* 3241 */                                       _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_c_005fif_005f11);
/*      */                                       
/* 3243 */                                       _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */                                       
/* 3245 */                                       _jspx_th_html_005fmessages_005f1.setMessage("false");
/* 3246 */                                       int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/* 3247 */                                       if (_jspx_eval_html_005fmessages_005f1 != 0) {
/* 3248 */                                         String msg = null;
/* 3249 */                                         if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 3250 */                                           out = _jspx_page_context.pushBody();
/* 3251 */                                           _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/* 3252 */                                           _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */                                         }
/* 3254 */                                         msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                                         for (;;) {
/* 3256 */                                           out.write("\n         ");
/* 3257 */                                           if (_jspx_meth_c_005fif_005f12(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*      */                                             return;
/* 3259 */                                           out.write("\n\n                  ");
/* 3260 */                                           if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*      */                                             return;
/* 3262 */                                           out.write("<br>\n\n               ");
/* 3263 */                                           int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/* 3264 */                                           msg = (String)_jspx_page_context.findAttribute("msg");
/* 3265 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3268 */                                         if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 3269 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3272 */                                       if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/* 3273 */                                         this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */                                       }
/*      */                                       
/* 3276 */                                       this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/* 3277 */                                       out.write("\n               ");
/* 3278 */                                       if (_jspx_meth_c_005fif_005f13(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                                         return;
/* 3280 */                                       out.write("\n             <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                          <tr>\n                            <td  height=\"10\" ><img src=\"../images/spacer.gif\" ></td>\n                           </tr>\n</table>\n        ");
/* 3281 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3282 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3286 */                                   if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3287 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                                   }
/*      */                                   
/* 3290 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3291 */                                   out.write("\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtborder\">\n <tr >\n <td width=\"4%\" class=\"tableheading-monitor-config \"><img src=\"/images/sdp.gif\" class=\"tableheading-add-icon\"></td>\n <td class=\"tableheading-monitor-config\">\n\n<div id=\"SDPTabs\">\n\t<ul class=\"ulstyleforcf\">\n\t\t<li class=\"listyleforcf\" id=\"useRestApi\">\n\t\t\t<a href=\"javascript:void(0)\" class=\"ulanchor\" onclick=\"showApiDiv()\">");
/* 3292 */                                   out.print(FormatUtil.getString("am.webclient.sdp.userestapi.text"));
/* 3293 */                                   out.write(" \n\t\t\t\t<span class=\"tabarrows\"></span>\n\t\t\t</a>\n\t\t</li>\n\t\t<li class=\"listyleforcf\" id=\"useCredential\">\n\t\t\t<a href=\"javascript:void(0)\" class=\"ulanchor\" onclick=\"showLoginDiv()\">");
/* 3294 */                                   out.print(FormatUtil.getString("am.webclient.sdp.usecredential.text"));
/* 3295 */                                   out.write(" \n\t\t\t\t<span class=\"tabarrows\"></span>\n\t\t\t</a>\n\t\t</li>\n\t</ul>\n</div>\n</td>\n </tr>\n</table>\n\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbborder\">\n<tr>\n<input type=\"hidden\" id=\"methodName\" name=\"method\" value=\"SdeskConfiguration\" >\n<td>\n <table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\" >\n <tr>\n  <td>\n  <!-- <table width=\"99%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" id=\"SDPlogin\"> -->\n  <table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n      <tr>\n\t     <td class=\"bodytextbold\" style=\"padding-top: 20px;\" colspan=\"2\">");
/* 3296 */                                   out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.serverdetail"));
/* 3297 */                                   out.write("</td>\n\t  </tr>\n     <tr id=\"SDPEdition\">\n\t<td width=\"25%\" height=\"28\" class=\"bodytext label-align\" >");
/* 3298 */                                   out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.servereditiondetail"));
/* 3299 */                                   out.write("<span class=\"mandatory\">*</span></td>");
/* 3300 */                                   out.write("\n\t  <td width=\"75%\" height=\"28\" colspan=\"2\" >");
/* 3301 */                                   if (_jspx_meth_html_005fradio_005f35(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3303 */                                   out.print(FormatUtil.getString("am.extprod.sdp"));
/* 3304 */                                   out.write("&nbsp;&nbsp;&nbsp;");
/* 3305 */                                   if (_jspx_meth_html_005fradio_005f36(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3307 */                                   out.write("<span class=\"bodytext\">");
/* 3308 */                                   out.print(FormatUtil.getString("am.extprod.mspdesk"));
/* 3309 */                                   out.write("</span>");
/* 3310 */                                   out.write("</td>\n    </tr>\n\t<tr>\n        <td width=\"25%\" class=\"bodytext label-align\">");
/* 3311 */                                   out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.servername"));
/* 3312 */                                   out.write("<span class=\"mandatory\">*</span></td>\n\t\t<td width=\"75%\" class=\"bodytext\">");
/* 3313 */                                   if (_jspx_meth_html_005ftext_005f7(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3315 */                                   out.write("</td>\n\t  </tr>\n\t <tr>\n\t     <td width=\"25%\" class=\"bodytext label-align\">");
/* 3316 */                                   out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.serverport"));
/* 3317 */                                   out.write("<span class=\"mandatory\">*</span></td>\n\t\t <td width=\"75%\" class=\"bodytext\">");
/* 3318 */                                   if (_jspx_meth_html_005ftext_005f8(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3320 */                                   out.write("</td>\n\t </tr>\n\t \n\t <tr id=\"SDPlogin\">\n\t    <td width=\"25%\" class=\"bodytext label-align\">");
/* 3321 */                                   out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.authlogin"));
/* 3322 */                                   out.write("<span class=\"mandatory\">*</span></td>\n\t\t <td width=\"75%\" class=\"bodytext\">");
/* 3323 */                                   if (_jspx_meth_html_005ftext_005f9(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3325 */                                   out.write("</td>\n\t </tr>\n\t <tr id=\"SDPPassWord\">\n\t     <td width=\"25%\" class=\"bodytext label-align\">");
/* 3326 */                                   out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.authpassword"));
/* 3327 */                                   out.write("<span class=\"mandatory\">*</span></td>\n\t\t  <td width=\"75%\" class=\"bodytext\">");
/* 3328 */                                   if (_jspx_meth_html_005fpassword_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3330 */                                   out.write("</td>\n\t </tr>\n\t\n   \t<tr id=\"SDPapiDiv1\">\n\t\t<td width=\"25%\" class=\"bodytext label-align\">");
/* 3331 */                                   out.print(FormatUtil.getString("am.webclient.sdp.restapikey.text"));
/* 3332 */                                   out.write("<span class=\"mandatory\">*</span></td>\n\t\t<td width=\"75%\" class=\"bodytext\">");
/* 3333 */                                   if (_jspx_meth_html_005ftext_005f10(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3335 */                                   out.write("</td>\n \t</tr>\n\t \n\t  <tr>\n\t  <td height=\"28\" class=\"bodytext label-align\" >");
/* 3336 */                                   out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.Protocol"));
/* 3337 */                                   out.write("<span class=\"mandatory\">*</span></TD>\n\t  <td height=\"28\" colspan=\"2\" >");
/* 3338 */                                   if (_jspx_meth_html_005fradio_005f37(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3340 */                                   out.write("<span class=\"bodytext\">");
/* 3341 */                                   out.print(FormatUtil.getString("am.extprod.http"));
/* 3342 */                                   out.write("&nbsp;&nbsp;&nbsp;");
/* 3343 */                                   if (_jspx_meth_html_005fradio_005f38(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3345 */                                   out.print(FormatUtil.getString("am.extprod.https"));
/* 3346 */                                   out.write("</span>\n\t  </td>\n     </tr>\n\t\t <tr id=\"toaddress\">\n\t   <td width=\"25%\" class=\"bodytext label-align\">");
/* 3347 */                                   out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.toaddress"));
/* 3348 */                                   out.write("<span class=\"mandatory\">*</span></td>\n\t    <td width=\"75%\" class=\"bodytext\">");
/* 3349 */                                   if (_jspx_meth_html_005ftext_005f11(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3351 */                                   out.write("</td>\n\t </tr>\n\t <tr id=\"fromaddress\">\n\t   <td width=\"25%\" class=\"bodytext label-align\">");
/* 3352 */                                   out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.fromaddress"));
/* 3353 */                                   out.write("<span class=\"mandatory\">*</span></td>\n\t    <td  width=\"75%\" class=\"bodytext\">");
/* 3354 */                                   if (_jspx_meth_html_005ftext_005f12(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3356 */                                   out.write("</td>\n\t </tr>\n</table>\n\n");
/* 3357 */                                   out.write("<!--$Id$-->\n<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" id=\"advanced_api_settings\">\n   <tr>\n      <td>\n         <h3 class=\"head-settings\"><input type=\"checkbox\" name=\"advanced_settings\" onChange=\"javascript:showAdvnSettings(),handleMSPDesk()\" id=\"advanced_settings\" />");
/* 3358 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.advancedsettings"));
/* 3359 */                                   out.write("</h3>\n         <div id=\"settings_container\" style=\"display: none;\">\n\t\t \n          <h7 class=\"head-settings\">");
/* 3360 */                                   if (_jspx_meth_html_005fcheckbox_005f2(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3362 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.ticket.enableticketing"));
/* 3363 */                                   out.write(" </h7>");
/* 3364 */                                   out.write("\n            <div class=\"sub-info\" id=\"sub-info-ticket\">\n            <fieldset class=\"fieldset_replace\">\n               <legend> ");
/* 3365 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.ticket.ticketsetting"));
/* 3366 */                                   out.write(" </legend>\n               <table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"ci-setting-container\">\n                  <tbody>\n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 3367 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.ticket.reopenpolicy"));
/* 3368 */                                   out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"input-align-td\">\n                           <tbody>\n                                 <tr>\n                                    <td>");
/* 3369 */                                   if (_jspx_meth_html_005fradio_005f39(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3371 */                                   out.write("</td>\t\t\t\t\t\t\t\t\n                                    <td>");
/* 3372 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.ticket.reopen.always"));
/* 3373 */                                   out.write("</td>\n                                 </tr>\n                                 <tr>\n                                    <td>");
/* 3374 */                                   if (_jspx_meth_html_005fradio_005f40(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3376 */                                   out.write("</td>\n                                    <td>");
/* 3377 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.ticket.reopen.period.1"));
/* 3378 */                                   out.write(32);
/* 3379 */                                   if (_jspx_meth_html_005ftext_005f13(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3381 */                                   out.write(32);
/* 3382 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.ticket.reopen.period.2"));
/* 3383 */                                   out.write("</td>\n                                 </tr>\n                                \n                                 <tr>\n                                    <td>");
/* 3384 */                                   if (_jspx_meth_html_005fradio_005f41(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3386 */                                   out.write("</td>\n                                    <td>");
/* 3387 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.ticket.createticket"));
/* 3388 */                                   out.write("</td>\n                                 </tr>\n                                 \n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     \n                     \n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 3389 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.ticket.autoclosepolicy"));
/* 3390 */                                   out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"input-align-td\">\n                           <tbody>\n                                 <tr>\n                                    <td>");
/* 3391 */                                   if (_jspx_meth_html_005fradio_005f42(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3393 */                                   out.write("</td>\t\t\t\t\t\t\t\t\n                                    <td>");
/* 3394 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.ticket.closeticket"));
/* 3395 */                                   out.write("</td>\n                                 </tr>\n                                 <tr>\n                                    <td>");
/* 3396 */                                   if (_jspx_meth_html_005fradio_005f43(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3398 */                                   out.write("</td>\n                                    <td>");
/* 3399 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.ticket.updatenotes"));
/* 3400 */                                   out.write("</td>\n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     \n                     \n                     \n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 3401 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.ticket.addnotes"));
/* 3402 */                                   out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3403 */                                   if (_jspx_meth_html_005fradio_005f44(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3405 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3406 */                                   out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 3407 */                                   if (_jspx_meth_html_005fradio_005f45(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3409 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3410 */                                   out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     \n                     \n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 3411 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.ticket.dynamicticketing.action"));
/* 3412 */                                   out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3413 */                                   if (_jspx_meth_html_005fradio_005f46(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3415 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3416 */                                   out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 3417 */                                   if (_jspx_meth_html_005fradio_005f47(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3419 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3420 */                                   out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 3421 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.ticket.dynamicticketing.form"));
/* 3422 */                                   out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3423 */                                   if (_jspx_meth_html_005fradio_005f48(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3425 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3426 */                                   out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 3427 */                                   if (_jspx_meth_html_005fradio_005f49(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3429 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3430 */                                   out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     \n                       ");
/* 3431 */                                   out.write("\n                     \n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 3432 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.ticket.ticketdetailsfromalrm", new Object[] { FormatUtil.getString("product.name") }));
/* 3433 */                                   out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3434 */                                   if (_jspx_meth_html_005fradio_005f50(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3436 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3437 */                                   out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 3438 */                                   if (_jspx_meth_html_005fradio_005f51(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3440 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3441 */                                   out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t ");
/*      */                                   
/* 3443 */                                   IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3444 */                                   _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3445 */                                   _jspx_th_c_005fif_005f14.setParent(_jspx_th_html_005fform_005f1);
/*      */                                   
/* 3447 */                                   _jspx_th_c_005fif_005f14.setTest("${showAllSettings=='true'}");
/* 3448 */                                   int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3449 */                                   if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                                     for (;;) {
/* 3451 */                                       out.write("\n\t\t\t\t\t <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 3452 */                                       out.print(FormatUtil.getString("me.sdp.cmdb.ticket.readonly"));
/* 3453 */                                       out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3454 */                                       if (_jspx_meth_html_005fradio_005f52(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                         return;
/* 3456 */                                       out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3457 */                                       out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 3458 */                                       if (_jspx_meth_html_005fradio_005f53(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                         return;
/* 3460 */                                       out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3461 */                                       out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t \n\t\t\t\t\t <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 3462 */                                       out.print(FormatUtil.getString("me.sdp.cmdb.update.ticket.status.update"));
/* 3463 */                                       out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3464 */                                       if (_jspx_meth_html_005fradio_005f54(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                         return;
/* 3466 */                                       out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3467 */                                       out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 3468 */                                       if (_jspx_meth_html_005fradio_005f55(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                         return;
/* 3470 */                                       out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3471 */                                       out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t ");
/*      */                                       
/* 3473 */                                       IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3474 */                                       _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 3475 */                                       _jspx_th_c_005fif_005f15.setParent(_jspx_th_c_005fif_005f14);
/*      */                                       
/* 3477 */                                       _jspx_th_c_005fif_005f15.setTest("${isServiceNow ne 'true'}");
/* 3478 */                                       int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 3479 */                                       if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                                         for (;;) {
/* 3481 */                                           out.write("\n\t\t\t\t\t  <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 3482 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.reqTemplate.overwrite"));
/* 3483 */                                           out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3484 */                                           if (_jspx_meth_html_005fradio_005f56(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                             return;
/* 3486 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3487 */                                           out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 3488 */                                           if (_jspx_meth_html_005fradio_005f57(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                             return;
/* 3490 */                                           out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3491 */                                           out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t ");
/* 3492 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 3493 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3497 */                                       if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 3498 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                                       }
/*      */                                       
/* 3501 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 3502 */                                       out.write("\n                    ");
/* 3503 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3504 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3508 */                                   if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3509 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                                   }
/*      */                                   
/* 3512 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3513 */                                   out.write("\n                 </tbody>\n               </table></fieldset>\n            </div>\n\t\t\t <h5 class=\"head-settings\">");
/* 3514 */                                   if (_jspx_meth_html_005fcheckbox_005f3(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3516 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.ci.cisync.enable"));
/* 3517 */                                   out.write(" </h5>");
/* 3518 */                                   out.write("\n         <div class=\"sub-info\" id=\"sub-info-ci\">\n            <fieldset class=\"fieldset_replace\">\n               <legend> ");
/* 3519 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.ci.cisettings"));
/* 3520 */                                   out.write(" </legend>\n               <table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"ci-setting-container\">\n                  <tbody>\n                     <tr>\n                        <td class=\"bodytext label-align\">");
/* 3521 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.ci.deleteci"));
/* 3522 */                                   out.write("</td>\n                        <td colspan=\"3\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3523 */                                   if (_jspx_meth_html_005fradio_005f58(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3525 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3526 */                                   out.write("</td>\n                                    <td width=\"75\">");
/* 3527 */                                   if (_jspx_meth_html_005fradio_005f59(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3529 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3530 */                                   out.write("</td>\n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     \n                     \n                     <tr width=\"100%\">\n                     \t<td width=\"45%\" valign=\"top\" class=\"bodytext label-align\">");
/* 3531 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.ci.firstlevel.excludetype"));
/* 3532 */                                   out.write("</td>\n                        <td width=\"25%\">\n                        ");
/* 3533 */                                   if (_jspx_meth_html_005fselect_005f4(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3535 */                                   out.write("\t\t\t\t\n                        </td>\n                        <td width=\"5%\" align=\"center\" class=\"bodytext\">\n                           <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tr>\n                                 <td align=\"center\">");
/* 3536 */                                   if (_jspx_meth_html_005fbutton_005f8(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3538 */                                   out.write("</td>\n                              </tr>\n                              <tr>\n                                 <td  align=\"center\">");
/* 3539 */                                   if (_jspx_meth_html_005fbutton_005f9(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3541 */                                   out.write("</td>\n                             </tr>\n                              <tr>\n                                 <td  align=\"center\" valign=\"bottom\">");
/* 3542 */                                   if (_jspx_meth_html_005fbutton_005f10(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3544 */                                   out.write("</td>\n                              </tr>\n                              <tr>\n                                 <td  align=\"center\" valign=\"bottom\">");
/* 3545 */                                   if (_jspx_meth_html_005fbutton_005f11(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3547 */                                   out.write("</td>\n                              </tr>\n                           </table>\n                        </td>\n                        <td width=\"25%\">\t\t\t\t\t\t\t\n                           ");
/* 3548 */                                   if (_jspx_meth_html_005fselect_005f5(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3550 */                                   out.write("\t\t\t\t\t\t\t\n                        </td>\n                     </tr>\n                     \n                     ");
/*      */                                   
/* 3552 */                                   IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3553 */                                   _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 3554 */                                   _jspx_th_c_005fif_005f16.setParent(_jspx_th_html_005fform_005f1);
/*      */                                   
/* 3556 */                                   _jspx_th_c_005fif_005f16.setTest("${showAllSettings=='true'}");
/* 3557 */                                   int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 3558 */                                   if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                                     for (;;) {
/* 3560 */                                       out.write("\n                     <tr width=\"100%\">\n                     \t<td width=\"45%\" valign=\"top\" class=\"bodytext label-align\">");
/* 3561 */                                       out.print(FormatUtil.getString("me.sdp.cmdb.ci.secondlevel.includetype"));
/* 3562 */                                       out.write("</td>\n                        <td width=\"25%\">\t\t\t\t\t\n                           ");
/* 3563 */                                       if (_jspx_meth_html_005fselect_005f6(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                                         return;
/* 3565 */                                       out.write("\t\t\t\t\t\n                        </td>\n                        <td width=\"5%\" align=\"center\" class=\"bodytext\">\n                           <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tr>\n                                 <td align=\"center\">");
/* 3566 */                                       if (_jspx_meth_html_005fbutton_005f12(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                                         return;
/* 3568 */                                       out.write("</td>\n                              </tr>\n                              <tr>\n                                 <td  align=\"center\">");
/* 3569 */                                       if (_jspx_meth_html_005fbutton_005f13(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                                         return;
/* 3571 */                                       out.write("</td>\n                             </tr>\n                              <tr>\n                                 <td  align=\"center\" valign=\"bottom\">");
/* 3572 */                                       if (_jspx_meth_html_005fbutton_005f14(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                                         return;
/* 3574 */                                       out.write("</td>\n                              </tr>\n                              <tr>\n                                 <td  align=\"center\" valign=\"bottom\">");
/* 3575 */                                       if (_jspx_meth_html_005fbutton_005f15(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                                         return;
/* 3577 */                                       out.write("</td>\n                              </tr>\n                           </table>\n                        </td>\n                        <td width=\"25%\">\t\t\t\t\t\t\t\n                           ");
/* 3578 */                                       if (_jspx_meth_html_005fselect_005f7(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                                         return;
/* 3580 */                                       out.write("\t\t\t\t\t\t\t\t\n                        </td>\n                     </tr>\n                     ");
/* 3581 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 3582 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3586 */                                   if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 3587 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                                   }
/*      */                                   
/* 3590 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 3591 */                                   out.write("\n                     <tr>\n                     \t<td class=\"bodytext label-align\">");
/* 3592 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.ci.customattributes"));
/* 3593 */                                   out.write("</td>\n                        <td colspan=\"3\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3594 */                                   if (_jspx_meth_html_005fradio_005f60(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3596 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3597 */                                   out.write("</td>\n                                    \n                                    <td width=\"75\">");
/* 3598 */                                   if (_jspx_meth_html_005fradio_005f61(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3600 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3601 */                                   out.write("</td>\n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     <tr>\n                     \t<td class=\"bodytext label-align\">");
/* 3602 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.ci.rlmapfromsnapshot", new Object[] { FormatUtil.getString("product.name") }));
/* 3603 */                                   out.write("</td>\n                        <td colspan=\"3\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3604 */                                   if (_jspx_meth_html_005fradio_005f62(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3606 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3607 */                                   out.write("</td>\n                                    \n                                    <td width=\"75\">");
/* 3608 */                                   if (_jspx_meth_html_005fradio_005f63(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 3610 */                                   out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3611 */                                   out.write("</td>\n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t ");
/*      */                                   
/* 3613 */                                   IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3614 */                                   _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 3615 */                                   _jspx_th_c_005fif_005f17.setParent(_jspx_th_html_005fform_005f1);
/*      */                                   
/* 3617 */                                   _jspx_th_c_005fif_005f17.setTest("${showAllSettings=='true'}");
/* 3618 */                                   int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 3619 */                                   if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                                     for (;;) {
/* 3621 */                                       out.write("\n\t\t\t\t\t   <tr>\n                     \t<td class=\"bodytext label-align\">");
/* 3622 */                                       out.print(FormatUtil.getString("me.sdp.cmdb.ci.rlmap.withlist"));
/* 3623 */                                       out.write("</td>\n                        <td colspan=\"3\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3624 */                                       if (_jspx_meth_html_005fradio_005f64(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                         return;
/* 3626 */                                       out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3627 */                                       out.write("</td>\n                                    \n                                    <td width=\"75\">");
/* 3628 */                                       if (_jspx_meth_html_005fradio_005f65(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                         return;
/* 3630 */                                       out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3631 */                                       out.write("</td>\n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t ");
/* 3632 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 3633 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3637 */                                   if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 3638 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */                                   }
/*      */                                   
/* 3641 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 3642 */                                   out.write("\n                  </tbody>\n               </table></fieldset>\n            </div>\n            </div>\n      </td>\n\t </tr>\n</table>\n");
/* 3643 */                                   out.write("\n\n</td>\n</tr>\n<tr>\n<td>\n<table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" >\n <tr>\n    <td width=\"25%\" class=\"tablebottom\">&nbsp;</td>\n\t<td width=\"75%\" height=\"31\" class=\"tablebottom\" ><input name=\"Submit\" value=\"");
/* 3644 */                                   out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.submit"));
/* 3645 */                                   out.write("\" type=\"button\" class=\"buttons btn_highlt\" onclick=\"fnFormSubmit();\">\n\t<input type=\"reset\" align=\"center\" class=\"buttons btn_reset\" value=\"");
/* 3646 */                                   out.print(FormatUtil.getString("webclient.admin.adduser.clear"));
/* 3647 */                                   out.write("\">\n\t");
/*      */                                   
/* 3649 */                                   if ((request.getAttribute("showSdeskLogTicket8") != null) || (request.getAttribute("configureSdesk2") != null))
/*      */                                   {
/*      */ 
/* 3652 */                                     out.write("\n\t<input type=\"button\" align=\"center\" class=\"buttons btn_link\" value=\"");
/* 3653 */                                     out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.cancel"));
/* 3654 */                                     out.write("\" onclick=\"javascript:history.back();\">\n\t");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3659 */                                     out.write("\n\t<input type=\"button\" align=\"center\" class=\"buttons btn_link\" value=\"");
/* 3660 */                                     out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.cancel"));
/* 3661 */                                     out.write("\" onclick=\"javascript:history.back();\">\n\t");
/*      */                                   }
/* 3663 */                                   out.write("\n\t</td>\n </tr>\n</table>\n</td>\n</tr>\n</table>\n</td>\n</tr>\n</table>\n ");
/* 3664 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/* 3665 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3669 */                               if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/* 3670 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*      */                               }
/*      */                               
/* 3673 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f1);
/* 3674 */                               out.write(10);
/* 3675 */                               out.write(10);
/* 3676 */                               out.write("\n</td>\n</tr>\n");
/* 3677 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 3678 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3682 */                           if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 3683 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                           }
/*      */                           
/* 3686 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3687 */                           out.write(10);
/* 3688 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3689 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3693 */                       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3694 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */                       }
/*      */                       else {
/* 3697 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3698 */                         out.write(10);
/*      */                       }
/* 3700 */                     } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3701 */         out = _jspx_out;
/* 3702 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3703 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3704 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3707 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3713 */     PageContext pageContext = _jspx_page_context;
/* 3714 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3716 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3717 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3718 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 3720 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3722 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3723 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3724 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3725 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3726 */       return true;
/*      */     }
/* 3728 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3729 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3734 */     PageContext pageContext = _jspx_page_context;
/* 3735 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3737 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3738 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3739 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 3741 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 3742 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3743 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 3745 */         out.write("\n alertUser();\n return;\n ");
/* 3746 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3747 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3751 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3752 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3753 */       return true;
/*      */     }
/* 3755 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3756 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3761 */     PageContext pageContext = _jspx_page_context;
/* 3762 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3764 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3765 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 3766 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3768 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 3770 */     _jspx_th_tiles_005fput_005f0.setValue("Add-on/Products Settings");
/* 3771 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 3772 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 3773 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3774 */       return true;
/*      */     }
/* 3776 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3777 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3782 */     PageContext pageContext = _jspx_page_context;
/* 3783 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3785 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3786 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3787 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3789 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3791 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 3792 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3793 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3794 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3795 */       return true;
/*      */     }
/* 3797 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3798 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3803 */     PageContext pageContext = _jspx_page_context;
/* 3804 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3806 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody.get(HiddenTag.class);
/* 3807 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 3808 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3810 */     _jspx_th_html_005fhidden_005f0.setName("ticketingType");
/*      */     
/* 3812 */     _jspx_th_html_005fhidden_005f0.setProperty("ticketingType");
/*      */     
/* 3814 */     _jspx_th_html_005fhidden_005f0.setValue("restapi");
/* 3815 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 3816 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 3817 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 3818 */       return true;
/*      */     }
/* 3820 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 3821 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3826 */     PageContext pageContext = _jspx_page_context;
/* 3827 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3829 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody.get(HiddenTag.class);
/* 3830 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 3831 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3833 */     _jspx_th_html_005fhidden_005f1.setName("valuesCleared");
/*      */     
/* 3835 */     _jspx_th_html_005fhidden_005f1.setProperty("valuesCleared");
/*      */     
/* 3837 */     _jspx_th_html_005fhidden_005f1.setValue("false");
/* 3838 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 3839 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 3840 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 3841 */       return true;
/*      */     }
/* 3843 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 3844 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3849 */     PageContext pageContext = _jspx_page_context;
/* 3850 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3852 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3853 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3854 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3856 */     _jspx_th_c_005fif_005f0.setTest("${param.admin!='true'}");
/* 3857 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3858 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3860 */         out.write(10);
/* 3861 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3862 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3866 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3867 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3868 */       return true;
/*      */     }
/* 3870 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3871 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3876 */     PageContext pageContext = _jspx_page_context;
/* 3877 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3879 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3880 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 3881 */     _jspx_th_c_005fchoose_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 3882 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 3883 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 3885 */         out.write(10);
/* 3886 */         out.write(9);
/* 3887 */         out.write(9);
/* 3888 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 3889 */           return true;
/* 3890 */         out.write(10);
/* 3891 */         out.write(9);
/* 3892 */         out.write(9);
/* 3893 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 3894 */           return true;
/* 3895 */         out.write(10);
/* 3896 */         out.write(9);
/* 3897 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 3898 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3902 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 3903 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 3904 */       return true;
/*      */     }
/* 3906 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 3907 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3912 */     PageContext pageContext = _jspx_page_context;
/* 3913 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3915 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3916 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 3917 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 3919 */     _jspx_th_c_005fwhen_005f1.setTest("${AMActionForm.ticketingType eq 'restapi'}");
/* 3920 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 3921 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 3923 */         out.write("\n\t\t\tshowApiDiv();\n\t\t\thandleMSPDesk();\n\t\t");
/* 3924 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 3925 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3929 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 3930 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 3931 */       return true;
/*      */     }
/* 3933 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 3934 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3939 */     PageContext pageContext = _jspx_page_context;
/* 3940 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3942 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3943 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3944 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 3945 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3946 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 3948 */         out.write("\n\t\t\tshowLoginDiv();\n\t\t");
/* 3949 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3950 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3954 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3955 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3956 */       return true;
/*      */     }
/* 3958 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3959 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3964 */     PageContext pageContext = _jspx_page_context;
/* 3965 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3967 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3968 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3969 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3971 */     _jspx_th_c_005fif_005f1.setTest("${AMActionForm.username eq ''}");
/* 3972 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3973 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3975 */         out.write("\n\t \t//$('input').attr('autocomplete','off');\n\t \tjQuery(\"input:text[name=host]\").val(\"\"); //NO I18N\n\t \tjQuery(\"input:text[name=port]\").val(\"\"); //NO I18N\n\t \tjQuery(\"input:text[name=username]\").val(\"\"); //NO I18N\n\t \tjQuery(\"input:password\").val(\"\");  //NO I18N\n\t \tjQuery(\"input:text[name=toaddress]\").val(\"\"); //NO I18N\n\t \tjQuery(\"input:text[name=fromaddress]\").val(\"\"); //NO I18N\n\t \tjQuery(\"input:hidden[name=valuesCleared]\").val(\"true\");  //NO I18N\n\t ");
/* 3976 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3977 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3981 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3982 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3983 */       return true;
/*      */     }
/* 3985 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3986 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3991 */     PageContext pageContext = _jspx_page_context;
/* 3992 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3994 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3995 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3996 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3998 */     _jspx_th_c_005fout_005f1.setValue("${AMActionForm.host}");
/* 3999 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4000 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4001 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4002 */       return true;
/*      */     }
/* 4004 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4005 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4010 */     PageContext pageContext = _jspx_page_context;
/* 4011 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4013 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4014 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4015 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4017 */     _jspx_th_c_005fout_005f2.setValue("${AMActionForm.port}");
/* 4018 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4019 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4020 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4021 */       return true;
/*      */     }
/* 4023 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4024 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4029 */     PageContext pageContext = _jspx_page_context;
/* 4030 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4032 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4033 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4034 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4036 */     _jspx_th_c_005fout_005f3.setValue("${AMActionForm.restApiKey}");
/* 4037 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4038 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4039 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4040 */       return true;
/*      */     }
/* 4042 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4043 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4048 */     PageContext pageContext = _jspx_page_context;
/* 4049 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4051 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 4052 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 4053 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4055 */     _jspx_th_html_005fradio_005f0.setProperty("helpDeskProduct");
/*      */     
/* 4057 */     _jspx_th_html_005fradio_005f0.setValue("SERVICEDESK");
/*      */     
/* 4059 */     _jspx_th_html_005fradio_005f0.setOnclick("javascript:changeProduct('ServiceDesk')");
/* 4060 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 4061 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 4062 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 4063 */       return true;
/*      */     }
/* 4065 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 4066 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4071 */     PageContext pageContext = _jspx_page_context;
/* 4072 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4074 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 4075 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 4076 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4078 */     _jspx_th_html_005fradio_005f1.setProperty("helpDeskProduct");
/*      */     
/* 4080 */     _jspx_th_html_005fradio_005f1.setValue("SERVICENOW");
/*      */     
/* 4082 */     _jspx_th_html_005fradio_005f1.setOnclick("javascript:changeProduct('ServiceNow')");
/* 4083 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 4084 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 4085 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 4086 */       return true;
/*      */     }
/* 4088 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 4089 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4094 */     PageContext pageContext = _jspx_page_context;
/* 4095 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4097 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4098 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 4099 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/* 4101 */     _jspx_th_c_005fif_005f3.setTest("${empty firstrow}");
/* 4102 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 4103 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 4105 */         out.write("\n\n<table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n                   <tr>\n                  <td width=\"5%\" align=\"center\">\n                  <img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\">\n                  </td>\n                  <td width=\"95%\" class=\"message\">\n\n               ");
/* 4106 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 4107 */           return true;
/* 4108 */         out.write("\n              ");
/* 4109 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 4110 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4114 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 4115 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4116 */       return true;
/*      */     }
/* 4118 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4119 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4124 */     PageContext pageContext = _jspx_page_context;
/* 4125 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4127 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4128 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4129 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4131 */     _jspx_th_c_005fset_005f0.setVar("firstrow");
/*      */     
/* 4133 */     _jspx_th_c_005fset_005f0.setValue("true");
/* 4134 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4135 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4136 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 4137 */       return true;
/*      */     }
/* 4139 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 4140 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4145 */     PageContext pageContext = _jspx_page_context;
/* 4146 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4148 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 4149 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 4150 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/* 4152 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*      */     
/* 4154 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 4155 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 4156 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 4157 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 4158 */       return true;
/*      */     }
/* 4160 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 4161 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4166 */     PageContext pageContext = _jspx_page_context;
/* 4167 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4169 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4170 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 4171 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4173 */     _jspx_th_c_005fif_005f4.setTest("${!empty firstrow}");
/* 4174 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 4175 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 4177 */         out.write("\n               </td>\n                   </tr>\n          </table>\n           ");
/* 4178 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 4179 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4183 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 4184 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 4185 */       return true;
/*      */     }
/* 4187 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 4188 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4193 */     PageContext pageContext = _jspx_page_context;
/* 4194 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4196 */     RadioTag _jspx_th_html_005fradio_005f2 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonchange_005fnobody.get(RadioTag.class);
/* 4197 */     _jspx_th_html_005fradio_005f2.setPageContext(_jspx_page_context);
/* 4198 */     _jspx_th_html_005fradio_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4200 */     _jspx_th_html_005fradio_005f2.setProperty("mspDesk");
/*      */     
/* 4202 */     _jspx_th_html_005fradio_005f2.setValue("false");
/*      */     
/* 4204 */     _jspx_th_html_005fradio_005f2.setOnchange("javascript:handleMSPDesk()");
/* 4205 */     int _jspx_eval_html_005fradio_005f2 = _jspx_th_html_005fradio_005f2.doStartTag();
/* 4206 */     if (_jspx_th_html_005fradio_005f2.doEndTag() == 5) {
/* 4207 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 4208 */       return true;
/*      */     }
/* 4210 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 4211 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4216 */     PageContext pageContext = _jspx_page_context;
/* 4217 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4219 */     RadioTag _jspx_th_html_005fradio_005f3 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonchange_005fnobody.get(RadioTag.class);
/* 4220 */     _jspx_th_html_005fradio_005f3.setPageContext(_jspx_page_context);
/* 4221 */     _jspx_th_html_005fradio_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4223 */     _jspx_th_html_005fradio_005f3.setProperty("mspDesk");
/*      */     
/* 4225 */     _jspx_th_html_005fradio_005f3.setValue("true");
/*      */     
/* 4227 */     _jspx_th_html_005fradio_005f3.setOnchange("javascript:handleMSPDesk()");
/* 4228 */     int _jspx_eval_html_005fradio_005f3 = _jspx_th_html_005fradio_005f3.doStartTag();
/* 4229 */     if (_jspx_th_html_005fradio_005f3.doEndTag() == 5) {
/* 4230 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 4231 */       return true;
/*      */     }
/* 4233 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 4234 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4239 */     PageContext pageContext = _jspx_page_context;
/* 4240 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4242 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 4243 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 4244 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4246 */     _jspx_th_html_005ftext_005f0.setProperty("host");
/*      */     
/* 4248 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default");
/* 4249 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 4250 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 4251 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4252 */       return true;
/*      */     }
/* 4254 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4255 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4260 */     PageContext pageContext = _jspx_page_context;
/* 4261 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4263 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 4264 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 4265 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4267 */     _jspx_th_html_005ftext_005f1.setProperty("port");
/*      */     
/* 4269 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext default");
/* 4270 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 4271 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 4272 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4273 */       return true;
/*      */     }
/* 4275 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4276 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4281 */     PageContext pageContext = _jspx_page_context;
/* 4282 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4284 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 4285 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 4286 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4288 */     _jspx_th_html_005ftext_005f2.setProperty("username");
/*      */     
/* 4290 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext default");
/* 4291 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 4292 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 4293 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 4294 */       return true;
/*      */     }
/* 4296 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 4297 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4302 */     PageContext pageContext = _jspx_page_context;
/* 4303 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4305 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.get(PasswordTag.class);
/* 4306 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 4307 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4309 */     _jspx_th_html_005fpassword_005f0.setProperty("password");
/*      */     
/* 4311 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext default");
/* 4312 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 4313 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 4314 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 4315 */       return true;
/*      */     }
/* 4317 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 4318 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4323 */     PageContext pageContext = _jspx_page_context;
/* 4324 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4326 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 4327 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 4328 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4330 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext default");
/*      */     
/* 4332 */     _jspx_th_html_005ftext_005f3.setProperty("restApiKey");
/* 4333 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 4334 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 4335 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 4336 */       return true;
/*      */     }
/* 4338 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 4339 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4344 */     PageContext pageContext = _jspx_page_context;
/* 4345 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4347 */     RadioTag _jspx_th_html_005fradio_005f4 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4348 */     _jspx_th_html_005fradio_005f4.setPageContext(_jspx_page_context);
/* 4349 */     _jspx_th_html_005fradio_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4351 */     _jspx_th_html_005fradio_005f4.setProperty("protocol");
/*      */     
/* 4353 */     _jspx_th_html_005fradio_005f4.setValue("http");
/* 4354 */     int _jspx_eval_html_005fradio_005f4 = _jspx_th_html_005fradio_005f4.doStartTag();
/* 4355 */     if (_jspx_th_html_005fradio_005f4.doEndTag() == 5) {
/* 4356 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 4357 */       return true;
/*      */     }
/* 4359 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 4360 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4365 */     PageContext pageContext = _jspx_page_context;
/* 4366 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4368 */     RadioTag _jspx_th_html_005fradio_005f5 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4369 */     _jspx_th_html_005fradio_005f5.setPageContext(_jspx_page_context);
/* 4370 */     _jspx_th_html_005fradio_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4372 */     _jspx_th_html_005fradio_005f5.setProperty("protocol");
/*      */     
/* 4374 */     _jspx_th_html_005fradio_005f5.setValue("https");
/* 4375 */     int _jspx_eval_html_005fradio_005f5 = _jspx_th_html_005fradio_005f5.doStartTag();
/* 4376 */     if (_jspx_th_html_005fradio_005f5.doEndTag() == 5) {
/* 4377 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f5);
/* 4378 */       return true;
/*      */     }
/* 4380 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f5);
/* 4381 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4386 */     PageContext pageContext = _jspx_page_context;
/* 4387 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4389 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 4390 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 4391 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4393 */     _jspx_th_html_005ftext_005f4.setProperty("toaddress");
/*      */     
/* 4395 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext label-align");
/* 4396 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 4397 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 4398 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 4399 */       return true;
/*      */     }
/* 4401 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 4402 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4407 */     PageContext pageContext = _jspx_page_context;
/* 4408 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4410 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 4411 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 4412 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4414 */     _jspx_th_html_005ftext_005f5.setProperty("fromaddress");
/*      */     
/* 4416 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext default");
/* 4417 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 4418 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 4419 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 4420 */       return true;
/*      */     }
/* 4422 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 4423 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4428 */     PageContext pageContext = _jspx_page_context;
/* 4429 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4431 */     CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.get(CheckboxTag.class);
/* 4432 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 4433 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4435 */     _jspx_th_html_005fcheckbox_005f0.setProperty("ticketingEnabled");
/*      */     
/* 4437 */     _jspx_th_html_005fcheckbox_005f0.setStyleId("sub_settings2");
/*      */     
/* 4439 */     _jspx_th_html_005fcheckbox_005f0.setOnchange("javascript:showAdvnSettings2()");
/*      */     
/* 4441 */     _jspx_th_html_005fcheckbox_005f0.setValue("true");
/* 4442 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 4443 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 4444 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 4445 */       return true;
/*      */     }
/* 4447 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 4448 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4453 */     PageContext pageContext = _jspx_page_context;
/* 4454 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4456 */     RadioTag _jspx_th_html_005fradio_005f6 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4457 */     _jspx_th_html_005fradio_005f6.setPageContext(_jspx_page_context);
/* 4458 */     _jspx_th_html_005fradio_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4460 */     _jspx_th_html_005fradio_005f6.setProperty("reOpenTicketPolicy");
/*      */     
/* 4462 */     _jspx_th_html_005fradio_005f6.setValue("REOPEN_TICKET");
/* 4463 */     int _jspx_eval_html_005fradio_005f6 = _jspx_th_html_005fradio_005f6.doStartTag();
/* 4464 */     if (_jspx_th_html_005fradio_005f6.doEndTag() == 5) {
/* 4465 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f6);
/* 4466 */       return true;
/*      */     }
/* 4468 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f6);
/* 4469 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4474 */     PageContext pageContext = _jspx_page_context;
/* 4475 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4477 */     RadioTag _jspx_th_html_005fradio_005f7 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4478 */     _jspx_th_html_005fradio_005f7.setPageContext(_jspx_page_context);
/* 4479 */     _jspx_th_html_005fradio_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4481 */     _jspx_th_html_005fradio_005f7.setProperty("reOpenTicketPolicy");
/*      */     
/* 4483 */     _jspx_th_html_005fradio_005f7.setValue("REOPEN_TICKET_CUSTOM_PERIOD");
/* 4484 */     int _jspx_eval_html_005fradio_005f7 = _jspx_th_html_005fradio_005f7.doStartTag();
/* 4485 */     if (_jspx_th_html_005fradio_005f7.doEndTag() == 5) {
/* 4486 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f7);
/* 4487 */       return true;
/*      */     }
/* 4489 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f7);
/* 4490 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4495 */     PageContext pageContext = _jspx_page_context;
/* 4496 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4498 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4499 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 4500 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4502 */     _jspx_th_html_005ftext_005f6.setProperty("reOpenPeriod");
/*      */     
/* 4504 */     _jspx_th_html_005ftext_005f6.setSize("3");
/*      */     
/* 4506 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext");
/* 4507 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 4508 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 4509 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 4510 */       return true;
/*      */     }
/* 4512 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 4513 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4518 */     PageContext pageContext = _jspx_page_context;
/* 4519 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4521 */     RadioTag _jspx_th_html_005fradio_005f8 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4522 */     _jspx_th_html_005fradio_005f8.setPageContext(_jspx_page_context);
/* 4523 */     _jspx_th_html_005fradio_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4525 */     _jspx_th_html_005fradio_005f8.setProperty("reOpenTicketPolicy");
/*      */     
/* 4527 */     _jspx_th_html_005fradio_005f8.setValue("NEW_TICKET");
/* 4528 */     int _jspx_eval_html_005fradio_005f8 = _jspx_th_html_005fradio_005f8.doStartTag();
/* 4529 */     if (_jspx_th_html_005fradio_005f8.doEndTag() == 5) {
/* 4530 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f8);
/* 4531 */       return true;
/*      */     }
/* 4533 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f8);
/* 4534 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4539 */     PageContext pageContext = _jspx_page_context;
/* 4540 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4542 */     RadioTag _jspx_th_html_005fradio_005f9 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4543 */     _jspx_th_html_005fradio_005f9.setPageContext(_jspx_page_context);
/* 4544 */     _jspx_th_html_005fradio_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4546 */     _jspx_th_html_005fradio_005f9.setProperty("closeTicketPolicy");
/*      */     
/* 4548 */     _jspx_th_html_005fradio_005f9.setValue("CLOSE_TICKET_UPDATE_NOTES");
/* 4549 */     int _jspx_eval_html_005fradio_005f9 = _jspx_th_html_005fradio_005f9.doStartTag();
/* 4550 */     if (_jspx_th_html_005fradio_005f9.doEndTag() == 5) {
/* 4551 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f9);
/* 4552 */       return true;
/*      */     }
/* 4554 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f9);
/* 4555 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4560 */     PageContext pageContext = _jspx_page_context;
/* 4561 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4563 */     RadioTag _jspx_th_html_005fradio_005f10 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4564 */     _jspx_th_html_005fradio_005f10.setPageContext(_jspx_page_context);
/* 4565 */     _jspx_th_html_005fradio_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4567 */     _jspx_th_html_005fradio_005f10.setProperty("closeTicketPolicy");
/*      */     
/* 4569 */     _jspx_th_html_005fradio_005f10.setValue("UPDATE_NOTES");
/* 4570 */     int _jspx_eval_html_005fradio_005f10 = _jspx_th_html_005fradio_005f10.doStartTag();
/* 4571 */     if (_jspx_th_html_005fradio_005f10.doEndTag() == 5) {
/* 4572 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f10);
/* 4573 */       return true;
/*      */     }
/* 4575 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f10);
/* 4576 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4581 */     PageContext pageContext = _jspx_page_context;
/* 4582 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4584 */     RadioTag _jspx_th_html_005fradio_005f11 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4585 */     _jspx_th_html_005fradio_005f11.setPageContext(_jspx_page_context);
/* 4586 */     _jspx_th_html_005fradio_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4588 */     _jspx_th_html_005fradio_005f11.setProperty("notesToBeAddedForTicket");
/*      */     
/* 4590 */     _jspx_th_html_005fradio_005f11.setValue("true");
/* 4591 */     int _jspx_eval_html_005fradio_005f11 = _jspx_th_html_005fradio_005f11.doStartTag();
/* 4592 */     if (_jspx_th_html_005fradio_005f11.doEndTag() == 5) {
/* 4593 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f11);
/* 4594 */       return true;
/*      */     }
/* 4596 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f11);
/* 4597 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4602 */     PageContext pageContext = _jspx_page_context;
/* 4603 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4605 */     RadioTag _jspx_th_html_005fradio_005f12 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4606 */     _jspx_th_html_005fradio_005f12.setPageContext(_jspx_page_context);
/* 4607 */     _jspx_th_html_005fradio_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4609 */     _jspx_th_html_005fradio_005f12.setProperty("notesToBeAddedForTicket");
/*      */     
/* 4611 */     _jspx_th_html_005fradio_005f12.setValue("false");
/* 4612 */     int _jspx_eval_html_005fradio_005f12 = _jspx_th_html_005fradio_005f12.doStartTag();
/* 4613 */     if (_jspx_th_html_005fradio_005f12.doEndTag() == 5) {
/* 4614 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f12);
/* 4615 */       return true;
/*      */     }
/* 4617 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f12);
/* 4618 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4623 */     PageContext pageContext = _jspx_page_context;
/* 4624 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4626 */     RadioTag _jspx_th_html_005fradio_005f13 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4627 */     _jspx_th_html_005fradio_005f13.setPageContext(_jspx_page_context);
/* 4628 */     _jspx_th_html_005fradio_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4630 */     _jspx_th_html_005fradio_005f13.setProperty("dynamicTicketingUsingAction");
/*      */     
/* 4632 */     _jspx_th_html_005fradio_005f13.setValue("true");
/* 4633 */     int _jspx_eval_html_005fradio_005f13 = _jspx_th_html_005fradio_005f13.doStartTag();
/* 4634 */     if (_jspx_th_html_005fradio_005f13.doEndTag() == 5) {
/* 4635 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f13);
/* 4636 */       return true;
/*      */     }
/* 4638 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f13);
/* 4639 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4644 */     PageContext pageContext = _jspx_page_context;
/* 4645 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4647 */     RadioTag _jspx_th_html_005fradio_005f14 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4648 */     _jspx_th_html_005fradio_005f14.setPageContext(_jspx_page_context);
/* 4649 */     _jspx_th_html_005fradio_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4651 */     _jspx_th_html_005fradio_005f14.setProperty("dynamicTicketingUsingAction");
/*      */     
/* 4653 */     _jspx_th_html_005fradio_005f14.setValue("false");
/* 4654 */     int _jspx_eval_html_005fradio_005f14 = _jspx_th_html_005fradio_005f14.doStartTag();
/* 4655 */     if (_jspx_th_html_005fradio_005f14.doEndTag() == 5) {
/* 4656 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f14);
/* 4657 */       return true;
/*      */     }
/* 4659 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f14);
/* 4660 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f15(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4665 */     PageContext pageContext = _jspx_page_context;
/* 4666 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4668 */     RadioTag _jspx_th_html_005fradio_005f15 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4669 */     _jspx_th_html_005fradio_005f15.setPageContext(_jspx_page_context);
/* 4670 */     _jspx_th_html_005fradio_005f15.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4672 */     _jspx_th_html_005fradio_005f15.setProperty("dynamicTicketingUsingForm");
/*      */     
/* 4674 */     _jspx_th_html_005fradio_005f15.setValue("true");
/* 4675 */     int _jspx_eval_html_005fradio_005f15 = _jspx_th_html_005fradio_005f15.doStartTag();
/* 4676 */     if (_jspx_th_html_005fradio_005f15.doEndTag() == 5) {
/* 4677 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f15);
/* 4678 */       return true;
/*      */     }
/* 4680 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f15);
/* 4681 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f16(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4686 */     PageContext pageContext = _jspx_page_context;
/* 4687 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4689 */     RadioTag _jspx_th_html_005fradio_005f16 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4690 */     _jspx_th_html_005fradio_005f16.setPageContext(_jspx_page_context);
/* 4691 */     _jspx_th_html_005fradio_005f16.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4693 */     _jspx_th_html_005fradio_005f16.setProperty("dynamicTicketingUsingForm");
/*      */     
/* 4695 */     _jspx_th_html_005fradio_005f16.setValue("false");
/* 4696 */     int _jspx_eval_html_005fradio_005f16 = _jspx_th_html_005fradio_005f16.doStartTag();
/* 4697 */     if (_jspx_th_html_005fradio_005f16.doEndTag() == 5) {
/* 4698 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f16);
/* 4699 */       return true;
/*      */     }
/* 4701 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f16);
/* 4702 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f17(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4707 */     PageContext pageContext = _jspx_page_context;
/* 4708 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4710 */     RadioTag _jspx_th_html_005fradio_005f17 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4711 */     _jspx_th_html_005fradio_005f17.setPageContext(_jspx_page_context);
/* 4712 */     _jspx_th_html_005fradio_005f17.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4714 */     _jspx_th_html_005fradio_005f17.setProperty("ticketLinkToBeShown");
/*      */     
/* 4716 */     _jspx_th_html_005fradio_005f17.setValue("true");
/* 4717 */     int _jspx_eval_html_005fradio_005f17 = _jspx_th_html_005fradio_005f17.doStartTag();
/* 4718 */     if (_jspx_th_html_005fradio_005f17.doEndTag() == 5) {
/* 4719 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f17);
/* 4720 */       return true;
/*      */     }
/* 4722 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f17);
/* 4723 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f18(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4728 */     PageContext pageContext = _jspx_page_context;
/* 4729 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4731 */     RadioTag _jspx_th_html_005fradio_005f18 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4732 */     _jspx_th_html_005fradio_005f18.setPageContext(_jspx_page_context);
/* 4733 */     _jspx_th_html_005fradio_005f18.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4735 */     _jspx_th_html_005fradio_005f18.setProperty("ticketLinkToBeShown");
/*      */     
/* 4737 */     _jspx_th_html_005fradio_005f18.setValue("false");
/* 4738 */     int _jspx_eval_html_005fradio_005f18 = _jspx_th_html_005fradio_005f18.doStartTag();
/* 4739 */     if (_jspx_th_html_005fradio_005f18.doEndTag() == 5) {
/* 4740 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f18);
/* 4741 */       return true;
/*      */     }
/* 4743 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f18);
/* 4744 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f19(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4749 */     PageContext pageContext = _jspx_page_context;
/* 4750 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4752 */     RadioTag _jspx_th_html_005fradio_005f19 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4753 */     _jspx_th_html_005fradio_005f19.setPageContext(_jspx_page_context);
/* 4754 */     _jspx_th_html_005fradio_005f19.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 4756 */     _jspx_th_html_005fradio_005f19.setProperty("readonlyTicket");
/*      */     
/* 4758 */     _jspx_th_html_005fradio_005f19.setValue("true");
/* 4759 */     int _jspx_eval_html_005fradio_005f19 = _jspx_th_html_005fradio_005f19.doStartTag();
/* 4760 */     if (_jspx_th_html_005fradio_005f19.doEndTag() == 5) {
/* 4761 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f19);
/* 4762 */       return true;
/*      */     }
/* 4764 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f19);
/* 4765 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f20(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4770 */     PageContext pageContext = _jspx_page_context;
/* 4771 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4773 */     RadioTag _jspx_th_html_005fradio_005f20 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4774 */     _jspx_th_html_005fradio_005f20.setPageContext(_jspx_page_context);
/* 4775 */     _jspx_th_html_005fradio_005f20.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 4777 */     _jspx_th_html_005fradio_005f20.setProperty("readonlyTicket");
/*      */     
/* 4779 */     _jspx_th_html_005fradio_005f20.setValue("false");
/* 4780 */     int _jspx_eval_html_005fradio_005f20 = _jspx_th_html_005fradio_005f20.doStartTag();
/* 4781 */     if (_jspx_th_html_005fradio_005f20.doEndTag() == 5) {
/* 4782 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f20);
/* 4783 */       return true;
/*      */     }
/* 4785 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f20);
/* 4786 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f21(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4791 */     PageContext pageContext = _jspx_page_context;
/* 4792 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4794 */     RadioTag _jspx_th_html_005fradio_005f21 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4795 */     _jspx_th_html_005fradio_005f21.setPageContext(_jspx_page_context);
/* 4796 */     _jspx_th_html_005fradio_005f21.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 4798 */     _jspx_th_html_005fradio_005f21.setProperty("updateTicketForchangeInStatusAlone");
/*      */     
/* 4800 */     _jspx_th_html_005fradio_005f21.setValue("true");
/* 4801 */     int _jspx_eval_html_005fradio_005f21 = _jspx_th_html_005fradio_005f21.doStartTag();
/* 4802 */     if (_jspx_th_html_005fradio_005f21.doEndTag() == 5) {
/* 4803 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f21);
/* 4804 */       return true;
/*      */     }
/* 4806 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f21);
/* 4807 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f22(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4812 */     PageContext pageContext = _jspx_page_context;
/* 4813 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4815 */     RadioTag _jspx_th_html_005fradio_005f22 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4816 */     _jspx_th_html_005fradio_005f22.setPageContext(_jspx_page_context);
/* 4817 */     _jspx_th_html_005fradio_005f22.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 4819 */     _jspx_th_html_005fradio_005f22.setProperty("updateTicketForchangeInStatusAlone");
/*      */     
/* 4821 */     _jspx_th_html_005fradio_005f22.setValue("false");
/* 4822 */     int _jspx_eval_html_005fradio_005f22 = _jspx_th_html_005fradio_005f22.doStartTag();
/* 4823 */     if (_jspx_th_html_005fradio_005f22.doEndTag() == 5) {
/* 4824 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f22);
/* 4825 */       return true;
/*      */     }
/* 4827 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f22);
/* 4828 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f23(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4833 */     PageContext pageContext = _jspx_page_context;
/* 4834 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4836 */     RadioTag _jspx_th_html_005fradio_005f23 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4837 */     _jspx_th_html_005fradio_005f23.setPageContext(_jspx_page_context);
/* 4838 */     _jspx_th_html_005fradio_005f23.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4840 */     _jspx_th_html_005fradio_005f23.setProperty("allowOverWriteOfReqTemplate");
/*      */     
/* 4842 */     _jspx_th_html_005fradio_005f23.setValue("true");
/* 4843 */     int _jspx_eval_html_005fradio_005f23 = _jspx_th_html_005fradio_005f23.doStartTag();
/* 4844 */     if (_jspx_th_html_005fradio_005f23.doEndTag() == 5) {
/* 4845 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f23);
/* 4846 */       return true;
/*      */     }
/* 4848 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f23);
/* 4849 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f24(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4854 */     PageContext pageContext = _jspx_page_context;
/* 4855 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4857 */     RadioTag _jspx_th_html_005fradio_005f24 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4858 */     _jspx_th_html_005fradio_005f24.setPageContext(_jspx_page_context);
/* 4859 */     _jspx_th_html_005fradio_005f24.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4861 */     _jspx_th_html_005fradio_005f24.setProperty("allowOverWriteOfReqTemplate");
/*      */     
/* 4863 */     _jspx_th_html_005fradio_005f24.setValue("false");
/* 4864 */     int _jspx_eval_html_005fradio_005f24 = _jspx_th_html_005fradio_005f24.doStartTag();
/* 4865 */     if (_jspx_th_html_005fradio_005f24.doEndTag() == 5) {
/* 4866 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f24);
/* 4867 */       return true;
/*      */     }
/* 4869 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f24);
/* 4870 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4875 */     PageContext pageContext = _jspx_page_context;
/* 4876 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4878 */     CheckboxTag _jspx_th_html_005fcheckbox_005f1 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.get(CheckboxTag.class);
/* 4879 */     _jspx_th_html_005fcheckbox_005f1.setPageContext(_jspx_page_context);
/* 4880 */     _jspx_th_html_005fcheckbox_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4882 */     _jspx_th_html_005fcheckbox_005f1.setProperty("cISyncEnabled");
/*      */     
/* 4884 */     _jspx_th_html_005fcheckbox_005f1.setStyleId("sub_settings1");
/*      */     
/* 4886 */     _jspx_th_html_005fcheckbox_005f1.setOnchange("javascript:showAdvnSettings1()");
/*      */     
/* 4888 */     _jspx_th_html_005fcheckbox_005f1.setValue("true");
/* 4889 */     int _jspx_eval_html_005fcheckbox_005f1 = _jspx_th_html_005fcheckbox_005f1.doStartTag();
/* 4890 */     if (_jspx_th_html_005fcheckbox_005f1.doEndTag() == 5) {
/* 4891 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 4892 */       return true;
/*      */     }
/* 4894 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 4895 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f25(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4900 */     PageContext pageContext = _jspx_page_context;
/* 4901 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4903 */     RadioTag _jspx_th_html_005fradio_005f25 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4904 */     _jspx_th_html_005fradio_005f25.setPageContext(_jspx_page_context);
/* 4905 */     _jspx_th_html_005fradio_005f25.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4907 */     _jspx_th_html_005fradio_005f25.setProperty("ciToBeDeleted");
/*      */     
/* 4909 */     _jspx_th_html_005fradio_005f25.setValue("true");
/* 4910 */     int _jspx_eval_html_005fradio_005f25 = _jspx_th_html_005fradio_005f25.doStartTag();
/* 4911 */     if (_jspx_th_html_005fradio_005f25.doEndTag() == 5) {
/* 4912 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f25);
/* 4913 */       return true;
/*      */     }
/* 4915 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f25);
/* 4916 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f26(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4921 */     PageContext pageContext = _jspx_page_context;
/* 4922 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4924 */     RadioTag _jspx_th_html_005fradio_005f26 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4925 */     _jspx_th_html_005fradio_005f26.setPageContext(_jspx_page_context);
/* 4926 */     _jspx_th_html_005fradio_005f26.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4928 */     _jspx_th_html_005fradio_005f26.setProperty("ciToBeDeleted");
/*      */     
/* 4930 */     _jspx_th_html_005fradio_005f26.setValue("false");
/* 4931 */     int _jspx_eval_html_005fradio_005f26 = _jspx_th_html_005fradio_005f26.doStartTag();
/* 4932 */     if (_jspx_th_html_005fradio_005f26.doEndTag() == 5) {
/* 4933 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f26);
/* 4934 */       return true;
/*      */     }
/* 4936 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f26);
/* 4937 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4942 */     PageContext pageContext = _jspx_page_context;
/* 4943 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4945 */     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 4946 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 4947 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4949 */     _jspx_th_html_005fselect_005f0.setProperty("toAdd");
/*      */     
/* 4951 */     _jspx_th_html_005fselect_005f0.setStyle("width:100%");
/*      */     
/* 4953 */     _jspx_th_html_005fselect_005f0.setMultiple("true");
/*      */     
/* 4955 */     _jspx_th_html_005fselect_005f0.setSize("5");
/* 4956 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 4957 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 4958 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 4959 */         out = _jspx_page_context.pushBody();
/* 4960 */         _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 4961 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4964 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 4965 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 4966 */           return true;
/* 4967 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 4968 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 4969 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4972 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 4973 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4976 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 4977 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f0);
/* 4978 */       return true;
/*      */     }
/* 4980 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f0);
/* 4981 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4986 */     PageContext pageContext = _jspx_page_context;
/* 4987 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4989 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.get(OptionsCollectionTag.class);
/* 4990 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 4991 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 4993 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("firstLevelMonitorTypesOptions");
/*      */     
/* 4995 */     _jspx_th_html_005foptionsCollection_005f0.setLabel("label");
/*      */     
/* 4997 */     _jspx_th_html_005foptionsCollection_005f0.setValue("value");
/* 4998 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 4999 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 5000 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 5001 */       return true;
/*      */     }
/* 5003 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 5004 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5009 */     PageContext pageContext = _jspx_page_context;
/* 5010 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5012 */     ButtonTag _jspx_th_html_005fbutton_005f0 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5013 */     _jspx_th_html_005fbutton_005f0.setPageContext(_jspx_page_context);
/* 5014 */     _jspx_th_html_005fbutton_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5016 */     _jspx_th_html_005fbutton_005f0.setStyleClass("buttons btn_small");
/*      */     
/* 5018 */     _jspx_th_html_005fbutton_005f0.setProperty("AddButton2");
/*      */     
/* 5020 */     _jspx_th_html_005fbutton_005f0.setOnclick("javascript:fnAddToRightCombo(document.AMActionForm.toAdd,document.AMActionForm.excludeFirstLevelMonitorTypes),fnRemoveFromRightCombo(document.AMActionForm.toAdd);");
/*      */     
/* 5022 */     _jspx_th_html_005fbutton_005f0.setValue("&nbsp;&gt;&nbsp;");
/* 5023 */     int _jspx_eval_html_005fbutton_005f0 = _jspx_th_html_005fbutton_005f0.doStartTag();
/* 5024 */     if (_jspx_th_html_005fbutton_005f0.doEndTag() == 5) {
/* 5025 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0);
/* 5026 */       return true;
/*      */     }
/* 5028 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0);
/* 5029 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5034 */     PageContext pageContext = _jspx_page_context;
/* 5035 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5037 */     ButtonTag _jspx_th_html_005fbutton_005f1 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5038 */     _jspx_th_html_005fbutton_005f1.setPageContext(_jspx_page_context);
/* 5039 */     _jspx_th_html_005fbutton_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5041 */     _jspx_th_html_005fbutton_005f1.setStyleClass("buttons btn_small");
/*      */     
/* 5043 */     _jspx_th_html_005fbutton_005f1.setProperty("AddButton2");
/*      */     
/* 5045 */     _jspx_th_html_005fbutton_005f1.setOnclick("javascript:fnAddAllFromCombo(document.AMActionForm.toAdd,document.AMActionForm.excludeFirstLevelMonitorTypes),fnRemoveAllFromCombo(document.AMActionForm.toAdd);");
/*      */     
/* 5047 */     _jspx_th_html_005fbutton_005f1.setValue("&gt;&gt;");
/* 5048 */     int _jspx_eval_html_005fbutton_005f1 = _jspx_th_html_005fbutton_005f1.doStartTag();
/* 5049 */     if (_jspx_th_html_005fbutton_005f1.doEndTag() == 5) {
/* 5050 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f1);
/* 5051 */       return true;
/*      */     }
/* 5053 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f1);
/* 5054 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5059 */     PageContext pageContext = _jspx_page_context;
/* 5060 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5062 */     ButtonTag _jspx_th_html_005fbutton_005f2 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5063 */     _jspx_th_html_005fbutton_005f2.setPageContext(_jspx_page_context);
/* 5064 */     _jspx_th_html_005fbutton_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5066 */     _jspx_th_html_005fbutton_005f2.setStyleClass("buttons btn_small");
/*      */     
/* 5068 */     _jspx_th_html_005fbutton_005f2.setProperty("AddButton2");
/*      */     
/* 5070 */     _jspx_th_html_005fbutton_005f2.setOnclick("javascript:fnAddAllFromCombo(document.AMActionForm.excludeFirstLevelMonitorTypes,document.AMActionForm.toAdd),fnRemoveAllFromCombo(document.AMActionForm.excludeFirstLevelMonitorTypes);");
/*      */     
/* 5072 */     _jspx_th_html_005fbutton_005f2.setValue("&lt;&lt;");
/* 5073 */     int _jspx_eval_html_005fbutton_005f2 = _jspx_th_html_005fbutton_005f2.doStartTag();
/* 5074 */     if (_jspx_th_html_005fbutton_005f2.doEndTag() == 5) {
/* 5075 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f2);
/* 5076 */       return true;
/*      */     }
/* 5078 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f2);
/* 5079 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5084 */     PageContext pageContext = _jspx_page_context;
/* 5085 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5087 */     ButtonTag _jspx_th_html_005fbutton_005f3 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5088 */     _jspx_th_html_005fbutton_005f3.setPageContext(_jspx_page_context);
/* 5089 */     _jspx_th_html_005fbutton_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5091 */     _jspx_th_html_005fbutton_005f3.setStyleClass("buttons btn_small");
/*      */     
/* 5093 */     _jspx_th_html_005fbutton_005f3.setProperty("AddButton2");
/*      */     
/* 5095 */     _jspx_th_html_005fbutton_005f3.setOnclick("javascript:fnAddToRightCombo(document.AMActionForm.excludeFirstLevelMonitorTypes,document.AMActionForm.toAdd),fnRemoveFromRightCombo(document.AMActionForm.excludeFirstLevelMonitorTypes);");
/*      */     
/* 5097 */     _jspx_th_html_005fbutton_005f3.setValue("&nbsp;&lt;&nbsp;");
/* 5098 */     int _jspx_eval_html_005fbutton_005f3 = _jspx_th_html_005fbutton_005f3.doStartTag();
/* 5099 */     if (_jspx_th_html_005fbutton_005f3.doEndTag() == 5) {
/* 5100 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f3);
/* 5101 */       return true;
/*      */     }
/* 5103 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f3);
/* 5104 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5109 */     PageContext pageContext = _jspx_page_context;
/* 5110 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5112 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 5113 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 5114 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5116 */     _jspx_th_html_005fselect_005f1.setProperty("excludeFirstLevelMonitorTypes");
/*      */     
/* 5118 */     _jspx_th_html_005fselect_005f1.setStyle("width:100%");
/*      */     
/* 5120 */     _jspx_th_html_005fselect_005f1.setMultiple("true");
/*      */     
/* 5122 */     _jspx_th_html_005fselect_005f1.setSize("5");
/* 5123 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 5124 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 5125 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 5126 */         out = _jspx_page_context.pushBody();
/* 5127 */         _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 5128 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5131 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5132 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/* 5133 */           return true;
/* 5134 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5135 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 5136 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5139 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 5140 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5143 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 5144 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f1);
/* 5145 */       return true;
/*      */     }
/* 5147 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f1);
/* 5148 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5153 */     PageContext pageContext = _jspx_page_context;
/* 5154 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5156 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.get(OptionsCollectionTag.class);
/* 5157 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 5158 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*      */     
/* 5160 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("excludeFirstLevelMonitorTypesOptions");
/*      */     
/* 5162 */     _jspx_th_html_005foptionsCollection_005f1.setLabel("label");
/*      */     
/* 5164 */     _jspx_th_html_005foptionsCollection_005f1.setValue("value");
/* 5165 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 5166 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 5167 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 5168 */       return true;
/*      */     }
/* 5170 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 5171 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5176 */     PageContext pageContext = _jspx_page_context;
/* 5177 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5179 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 5180 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 5181 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 5183 */     _jspx_th_html_005fselect_005f2.setProperty("toAddg");
/*      */     
/* 5185 */     _jspx_th_html_005fselect_005f2.setStyle("width:100%");
/*      */     
/* 5187 */     _jspx_th_html_005fselect_005f2.setMultiple("true");
/*      */     
/* 5189 */     _jspx_th_html_005fselect_005f2.setSize("5");
/* 5190 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 5191 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 5192 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 5193 */         out = _jspx_page_context.pushBody();
/* 5194 */         _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 5195 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5198 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5199 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/* 5200 */           return true;
/* 5201 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5202 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 5203 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5206 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 5207 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5210 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 5211 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f2);
/* 5212 */       return true;
/*      */     }
/* 5214 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f2);
/* 5215 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5220 */     PageContext pageContext = _jspx_page_context;
/* 5221 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5223 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.get(OptionsCollectionTag.class);
/* 5224 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 5225 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*      */     
/* 5227 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("secondLevelMonitorTypesOptions");
/*      */     
/* 5229 */     _jspx_th_html_005foptionsCollection_005f2.setLabel("label");
/*      */     
/* 5231 */     _jspx_th_html_005foptionsCollection_005f2.setValue("value");
/* 5232 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 5233 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 5234 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 5235 */       return true;
/*      */     }
/* 5237 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 5238 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f4(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5243 */     PageContext pageContext = _jspx_page_context;
/* 5244 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5246 */     ButtonTag _jspx_th_html_005fbutton_005f4 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5247 */     _jspx_th_html_005fbutton_005f4.setPageContext(_jspx_page_context);
/* 5248 */     _jspx_th_html_005fbutton_005f4.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 5250 */     _jspx_th_html_005fbutton_005f4.setStyleClass("buttons btn_small");
/*      */     
/* 5252 */     _jspx_th_html_005fbutton_005f4.setProperty("AddButton2");
/*      */     
/* 5254 */     _jspx_th_html_005fbutton_005f4.setOnclick("javascript:fnAddToRightCombo(document.AMActionForm.toAddg,document.AMActionForm.includeSecondLevelMonitorTypes),fnRemoveFromRightCombo(document.AMActionForm.toAddg);");
/*      */     
/* 5256 */     _jspx_th_html_005fbutton_005f4.setValue("&nbsp;&gt;&nbsp;");
/* 5257 */     int _jspx_eval_html_005fbutton_005f4 = _jspx_th_html_005fbutton_005f4.doStartTag();
/* 5258 */     if (_jspx_th_html_005fbutton_005f4.doEndTag() == 5) {
/* 5259 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f4);
/* 5260 */       return true;
/*      */     }
/* 5262 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f4);
/* 5263 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f5(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5268 */     PageContext pageContext = _jspx_page_context;
/* 5269 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5271 */     ButtonTag _jspx_th_html_005fbutton_005f5 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5272 */     _jspx_th_html_005fbutton_005f5.setPageContext(_jspx_page_context);
/* 5273 */     _jspx_th_html_005fbutton_005f5.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 5275 */     _jspx_th_html_005fbutton_005f5.setStyleClass("buttons btn_small");
/*      */     
/* 5277 */     _jspx_th_html_005fbutton_005f5.setProperty("AddButton2");
/*      */     
/* 5279 */     _jspx_th_html_005fbutton_005f5.setOnclick("javascript:fnAddAllFromCombo(document.AMActionForm.toAddg,document.AMActionForm.includeSecondLevelMonitorTypes),fnRemoveAllFromCombo(document.AMActionForm.toAddg);");
/*      */     
/* 5281 */     _jspx_th_html_005fbutton_005f5.setValue("&gt;&gt;");
/* 5282 */     int _jspx_eval_html_005fbutton_005f5 = _jspx_th_html_005fbutton_005f5.doStartTag();
/* 5283 */     if (_jspx_th_html_005fbutton_005f5.doEndTag() == 5) {
/* 5284 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f5);
/* 5285 */       return true;
/*      */     }
/* 5287 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f5);
/* 5288 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f6(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5293 */     PageContext pageContext = _jspx_page_context;
/* 5294 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5296 */     ButtonTag _jspx_th_html_005fbutton_005f6 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5297 */     _jspx_th_html_005fbutton_005f6.setPageContext(_jspx_page_context);
/* 5298 */     _jspx_th_html_005fbutton_005f6.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 5300 */     _jspx_th_html_005fbutton_005f6.setStyleClass("buttons btn_small");
/*      */     
/* 5302 */     _jspx_th_html_005fbutton_005f6.setProperty("AddButton2");
/*      */     
/* 5304 */     _jspx_th_html_005fbutton_005f6.setOnclick("javascript:fnAddAllFromCombo(document.AMActionForm.includeSecondLevelMonitorTypes,document.AMActionForm.toAddg),fnRemoveAllFromCombo(document.AMActionForm.includeSecondLevelMonitorTypes);");
/*      */     
/* 5306 */     _jspx_th_html_005fbutton_005f6.setValue("&lt;&lt;");
/* 5307 */     int _jspx_eval_html_005fbutton_005f6 = _jspx_th_html_005fbutton_005f6.doStartTag();
/* 5308 */     if (_jspx_th_html_005fbutton_005f6.doEndTag() == 5) {
/* 5309 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f6);
/* 5310 */       return true;
/*      */     }
/* 5312 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f6);
/* 5313 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f7(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5318 */     PageContext pageContext = _jspx_page_context;
/* 5319 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5321 */     ButtonTag _jspx_th_html_005fbutton_005f7 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5322 */     _jspx_th_html_005fbutton_005f7.setPageContext(_jspx_page_context);
/* 5323 */     _jspx_th_html_005fbutton_005f7.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 5325 */     _jspx_th_html_005fbutton_005f7.setStyleClass("buttons btn_small");
/*      */     
/* 5327 */     _jspx_th_html_005fbutton_005f7.setProperty("AddButton2");
/*      */     
/* 5329 */     _jspx_th_html_005fbutton_005f7.setOnclick("javascript:fnAddToRightCombo(document.AMActionForm.includeSecondLevelMonitorTypes,document.AMActionForm.toAddg),fnRemoveFromRightCombo(document.AMActionForm.includeSecondLevelMonitorTypes);");
/*      */     
/* 5331 */     _jspx_th_html_005fbutton_005f7.setValue("&nbsp;&lt;&nbsp;");
/* 5332 */     int _jspx_eval_html_005fbutton_005f7 = _jspx_th_html_005fbutton_005f7.doStartTag();
/* 5333 */     if (_jspx_th_html_005fbutton_005f7.doEndTag() == 5) {
/* 5334 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f7);
/* 5335 */       return true;
/*      */     }
/* 5337 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f7);
/* 5338 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f3(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5343 */     PageContext pageContext = _jspx_page_context;
/* 5344 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5346 */     SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 5347 */     _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 5348 */     _jspx_th_html_005fselect_005f3.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 5350 */     _jspx_th_html_005fselect_005f3.setProperty("includeSecondLevelMonitorTypes");
/*      */     
/* 5352 */     _jspx_th_html_005fselect_005f3.setStyle("width:100%");
/*      */     
/* 5354 */     _jspx_th_html_005fselect_005f3.setMultiple("true");
/*      */     
/* 5356 */     _jspx_th_html_005fselect_005f3.setSize("5");
/* 5357 */     int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 5358 */     if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 5359 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 5360 */         out = _jspx_page_context.pushBody();
/* 5361 */         _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 5362 */         _jspx_th_html_005fselect_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5365 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5366 */         if (_jspx_meth_html_005foptionsCollection_005f3(_jspx_th_html_005fselect_005f3, _jspx_page_context))
/* 5367 */           return true;
/* 5368 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5369 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 5370 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5373 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 5374 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5377 */     if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 5378 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f3);
/* 5379 */       return true;
/*      */     }
/* 5381 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f3);
/* 5382 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f3(JspTag _jspx_th_html_005fselect_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5387 */     PageContext pageContext = _jspx_page_context;
/* 5388 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5390 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f3 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.get(OptionsCollectionTag.class);
/* 5391 */     _jspx_th_html_005foptionsCollection_005f3.setPageContext(_jspx_page_context);
/* 5392 */     _jspx_th_html_005foptionsCollection_005f3.setParent((Tag)_jspx_th_html_005fselect_005f3);
/*      */     
/* 5394 */     _jspx_th_html_005foptionsCollection_005f3.setProperty("includeSecondLevelMonitorTypesOptions");
/*      */     
/* 5396 */     _jspx_th_html_005foptionsCollection_005f3.setLabel("label");
/*      */     
/* 5398 */     _jspx_th_html_005foptionsCollection_005f3.setValue("value");
/* 5399 */     int _jspx_eval_html_005foptionsCollection_005f3 = _jspx_th_html_005foptionsCollection_005f3.doStartTag();
/* 5400 */     if (_jspx_th_html_005foptionsCollection_005f3.doEndTag() == 5) {
/* 5401 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 5402 */       return true;
/*      */     }
/* 5404 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 5405 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f27(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5410 */     PageContext pageContext = _jspx_page_context;
/* 5411 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5413 */     RadioTag _jspx_th_html_005fradio_005f27 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5414 */     _jspx_th_html_005fradio_005f27.setPageContext(_jspx_page_context);
/* 5415 */     _jspx_th_html_005fradio_005f27.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5417 */     _jspx_th_html_005fradio_005f27.setProperty("ciAttributesToBeSynced");
/*      */     
/* 5419 */     _jspx_th_html_005fradio_005f27.setValue("true");
/* 5420 */     int _jspx_eval_html_005fradio_005f27 = _jspx_th_html_005fradio_005f27.doStartTag();
/* 5421 */     if (_jspx_th_html_005fradio_005f27.doEndTag() == 5) {
/* 5422 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f27);
/* 5423 */       return true;
/*      */     }
/* 5425 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f27);
/* 5426 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f28(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5431 */     PageContext pageContext = _jspx_page_context;
/* 5432 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5434 */     RadioTag _jspx_th_html_005fradio_005f28 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5435 */     _jspx_th_html_005fradio_005f28.setPageContext(_jspx_page_context);
/* 5436 */     _jspx_th_html_005fradio_005f28.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5438 */     _jspx_th_html_005fradio_005f28.setProperty("ciAttributesToBeSynced");
/*      */     
/* 5440 */     _jspx_th_html_005fradio_005f28.setValue("false");
/* 5441 */     int _jspx_eval_html_005fradio_005f28 = _jspx_th_html_005fradio_005f28.doStartTag();
/* 5442 */     if (_jspx_th_html_005fradio_005f28.doEndTag() == 5) {
/* 5443 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f28);
/* 5444 */       return true;
/*      */     }
/* 5446 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f28);
/* 5447 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f29(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5452 */     PageContext pageContext = _jspx_page_context;
/* 5453 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5455 */     RadioTag _jspx_th_html_005fradio_005f29 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5456 */     _jspx_th_html_005fradio_005f29.setPageContext(_jspx_page_context);
/* 5457 */     _jspx_th_html_005fradio_005f29.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5459 */     _jspx_th_html_005fradio_005f29.setProperty("ciLinksToBeShown");
/*      */     
/* 5461 */     _jspx_th_html_005fradio_005f29.setValue("true");
/* 5462 */     int _jspx_eval_html_005fradio_005f29 = _jspx_th_html_005fradio_005f29.doStartTag();
/* 5463 */     if (_jspx_th_html_005fradio_005f29.doEndTag() == 5) {
/* 5464 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f29);
/* 5465 */       return true;
/*      */     }
/* 5467 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f29);
/* 5468 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f30(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5473 */     PageContext pageContext = _jspx_page_context;
/* 5474 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5476 */     RadioTag _jspx_th_html_005fradio_005f30 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5477 */     _jspx_th_html_005fradio_005f30.setPageContext(_jspx_page_context);
/* 5478 */     _jspx_th_html_005fradio_005f30.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5480 */     _jspx_th_html_005fradio_005f30.setProperty("ciLinksToBeShown");
/*      */     
/* 5482 */     _jspx_th_html_005fradio_005f30.setValue("false");
/* 5483 */     int _jspx_eval_html_005fradio_005f30 = _jspx_th_html_005fradio_005f30.doStartTag();
/* 5484 */     if (_jspx_th_html_005fradio_005f30.doEndTag() == 5) {
/* 5485 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f30);
/* 5486 */       return true;
/*      */     }
/* 5488 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f30);
/* 5489 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f31(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5494 */     PageContext pageContext = _jspx_page_context;
/* 5495 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5497 */     RadioTag _jspx_th_html_005fradio_005f31 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5498 */     _jspx_th_html_005fradio_005f31.setPageContext(_jspx_page_context);
/* 5499 */     _jspx_th_html_005fradio_005f31.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 5501 */     _jspx_th_html_005fradio_005f31.setProperty("ciRlMapalongWithListView");
/*      */     
/* 5503 */     _jspx_th_html_005fradio_005f31.setValue("true");
/* 5504 */     int _jspx_eval_html_005fradio_005f31 = _jspx_th_html_005fradio_005f31.doStartTag();
/* 5505 */     if (_jspx_th_html_005fradio_005f31.doEndTag() == 5) {
/* 5506 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f31);
/* 5507 */       return true;
/*      */     }
/* 5509 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f31);
/* 5510 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f32(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5515 */     PageContext pageContext = _jspx_page_context;
/* 5516 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5518 */     RadioTag _jspx_th_html_005fradio_005f32 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5519 */     _jspx_th_html_005fradio_005f32.setPageContext(_jspx_page_context);
/* 5520 */     _jspx_th_html_005fradio_005f32.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 5522 */     _jspx_th_html_005fradio_005f32.setProperty("ciRlMapalongWithListView");
/*      */     
/* 5524 */     _jspx_th_html_005fradio_005f32.setValue("false");
/* 5525 */     int _jspx_eval_html_005fradio_005f32 = _jspx_th_html_005fradio_005f32.doStartTag();
/* 5526 */     if (_jspx_th_html_005fradio_005f32.doEndTag() == 5) {
/* 5527 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f32);
/* 5528 */       return true;
/*      */     }
/* 5530 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f32);
/* 5531 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5536 */     PageContext pageContext = _jspx_page_context;
/* 5537 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5539 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5540 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 5541 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5543 */     _jspx_th_tiles_005fput_005f3.setName("footer");
/*      */     
/* 5545 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/footer.jsp");
/* 5546 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 5547 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 5548 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 5549 */       return true;
/*      */     }
/* 5551 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 5552 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5557 */     PageContext pageContext = _jspx_page_context;
/* 5558 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5560 */     HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody.get(HiddenTag.class);
/* 5561 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/* 5562 */     _jspx_th_html_005fhidden_005f2.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5564 */     _jspx_th_html_005fhidden_005f2.setName("ticketingType");
/*      */     
/* 5566 */     _jspx_th_html_005fhidden_005f2.setProperty("ticketingType");
/*      */     
/* 5568 */     _jspx_th_html_005fhidden_005f2.setValue("restapi");
/* 5569 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/* 5570 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/* 5571 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 5572 */       return true;
/*      */     }
/* 5574 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 5575 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f3(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5580 */     PageContext pageContext = _jspx_page_context;
/* 5581 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5583 */     HiddenTag _jspx_th_html_005fhidden_005f3 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody.get(HiddenTag.class);
/* 5584 */     _jspx_th_html_005fhidden_005f3.setPageContext(_jspx_page_context);
/* 5585 */     _jspx_th_html_005fhidden_005f3.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5587 */     _jspx_th_html_005fhidden_005f3.setName("valuesCleared");
/*      */     
/* 5589 */     _jspx_th_html_005fhidden_005f3.setProperty("valuesCleared");
/*      */     
/* 5591 */     _jspx_th_html_005fhidden_005f3.setValue("false");
/* 5592 */     int _jspx_eval_html_005fhidden_005f3 = _jspx_th_html_005fhidden_005f3.doStartTag();
/* 5593 */     if (_jspx_th_html_005fhidden_005f3.doEndTag() == 5) {
/* 5594 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 5595 */       return true;
/*      */     }
/* 5597 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 5598 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5603 */     PageContext pageContext = _jspx_page_context;
/* 5604 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5606 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5607 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 5608 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5610 */     _jspx_th_c_005fif_005f9.setTest("${param.admin!='true'}");
/* 5611 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 5612 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 5614 */         out.write(10);
/* 5615 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 5616 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5620 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 5621 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 5622 */       return true;
/*      */     }
/* 5624 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 5625 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5630 */     PageContext pageContext = _jspx_page_context;
/* 5631 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5633 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5634 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 5635 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_html_005fform_005f1);
/* 5636 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 5637 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 5639 */         out.write(10);
/* 5640 */         out.write(9);
/* 5641 */         out.write(9);
/* 5642 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 5643 */           return true;
/* 5644 */         out.write(10);
/* 5645 */         out.write(9);
/* 5646 */         out.write(9);
/* 5647 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 5648 */           return true;
/* 5649 */         out.write(10);
/* 5650 */         out.write(9);
/* 5651 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 5652 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5656 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 5657 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 5658 */       return true;
/*      */     }
/* 5660 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 5661 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5666 */     PageContext pageContext = _jspx_page_context;
/* 5667 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5669 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5670 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 5671 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 5673 */     _jspx_th_c_005fwhen_005f2.setTest("${AMActionForm.ticketingType eq 'restapi'}");
/* 5674 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 5675 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 5677 */         out.write("\n\t\t\tshowApiDiv();\n\t\t\thandleMSPDesk();\n\t\t");
/* 5678 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 5679 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5683 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 5684 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 5685 */       return true;
/*      */     }
/* 5687 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 5688 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5693 */     PageContext pageContext = _jspx_page_context;
/* 5694 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5696 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5697 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 5698 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 5699 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 5700 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 5702 */         out.write("\n\t\t\tshowLoginDiv();\n\t\t");
/* 5703 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 5704 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5708 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 5709 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 5710 */       return true;
/*      */     }
/* 5712 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 5713 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5718 */     PageContext pageContext = _jspx_page_context;
/* 5719 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5721 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5722 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 5723 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5725 */     _jspx_th_c_005fif_005f10.setTest("${AMActionForm.username eq ''}");
/* 5726 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 5727 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 5729 */         out.write("\n\t \t//$('input').attr('autocomplete','off');\n\t \tjQuery(\"input:text[name=host]\").val(\"\"); //NO I18N\n\t \tjQuery(\"input:text[name=port]\").val(\"\"); //NO I18N\n\t \tjQuery(\"input:text[name=username]\").val(\"\"); //NO I18N\n\t \tjQuery(\"input:password\").val(\"\");  //NO I18N\n\t \tjQuery(\"input:text[name=toaddress]\").val(\"\"); //NO I18N\n\t \tjQuery(\"input:text[name=fromaddress]\").val(\"\"); //NO I18N\n\t \tjQuery(\"input:hidden[name=valuesCleared]\").val(\"true\");  //NO I18N\n\t ");
/* 5730 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 5731 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5735 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 5736 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 5737 */       return true;
/*      */     }
/* 5739 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 5740 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5745 */     PageContext pageContext = _jspx_page_context;
/* 5746 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5748 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5749 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 5750 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5752 */     _jspx_th_c_005fout_005f4.setValue("${AMActionForm.host}");
/* 5753 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 5754 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 5755 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5756 */       return true;
/*      */     }
/* 5758 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5759 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5764 */     PageContext pageContext = _jspx_page_context;
/* 5765 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5767 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5768 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 5769 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5771 */     _jspx_th_c_005fout_005f5.setValue("${AMActionForm.port}");
/* 5772 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 5773 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 5774 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5775 */       return true;
/*      */     }
/* 5777 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5778 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5783 */     PageContext pageContext = _jspx_page_context;
/* 5784 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5786 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5787 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 5788 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5790 */     _jspx_th_c_005fout_005f6.setValue("${AMActionForm.restApiKey}");
/* 5791 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 5792 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 5793 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5794 */       return true;
/*      */     }
/* 5796 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5797 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f33(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5802 */     PageContext pageContext = _jspx_page_context;
/* 5803 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5805 */     RadioTag _jspx_th_html_005fradio_005f33 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 5806 */     _jspx_th_html_005fradio_005f33.setPageContext(_jspx_page_context);
/* 5807 */     _jspx_th_html_005fradio_005f33.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5809 */     _jspx_th_html_005fradio_005f33.setProperty("helpDeskProduct");
/*      */     
/* 5811 */     _jspx_th_html_005fradio_005f33.setValue("SERVICEDESK");
/*      */     
/* 5813 */     _jspx_th_html_005fradio_005f33.setOnclick("javascript:changeProduct('ServiceDesk')");
/* 5814 */     int _jspx_eval_html_005fradio_005f33 = _jspx_th_html_005fradio_005f33.doStartTag();
/* 5815 */     if (_jspx_th_html_005fradio_005f33.doEndTag() == 5) {
/* 5816 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f33);
/* 5817 */       return true;
/*      */     }
/* 5819 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f33);
/* 5820 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f34(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5825 */     PageContext pageContext = _jspx_page_context;
/* 5826 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5828 */     RadioTag _jspx_th_html_005fradio_005f34 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 5829 */     _jspx_th_html_005fradio_005f34.setPageContext(_jspx_page_context);
/* 5830 */     _jspx_th_html_005fradio_005f34.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5832 */     _jspx_th_html_005fradio_005f34.setProperty("helpDeskProduct");
/*      */     
/* 5834 */     _jspx_th_html_005fradio_005f34.setValue("SERVICENOW");
/*      */     
/* 5836 */     _jspx_th_html_005fradio_005f34.setOnclick("javascript:changeProduct('ServiceNow')");
/* 5837 */     int _jspx_eval_html_005fradio_005f34 = _jspx_th_html_005fradio_005f34.doStartTag();
/* 5838 */     if (_jspx_th_html_005fradio_005f34.doEndTag() == 5) {
/* 5839 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f34);
/* 5840 */       return true;
/*      */     }
/* 5842 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f34);
/* 5843 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5848 */     PageContext pageContext = _jspx_page_context;
/* 5849 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5851 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5852 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 5853 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*      */     
/* 5855 */     _jspx_th_c_005fif_005f12.setTest("${empty firstrow}");
/* 5856 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 5857 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 5859 */         out.write("\n\n<table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n                   <tr>\n                  <td width=\"5%\" align=\"center\">\n                  <img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\">\n                  </td>\n                  <td width=\"95%\" class=\"message\">\n\n               ");
/* 5860 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f12, _jspx_page_context))
/* 5861 */           return true;
/* 5862 */         out.write("\n              ");
/* 5863 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 5864 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5868 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 5869 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 5870 */       return true;
/*      */     }
/* 5872 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 5873 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5878 */     PageContext pageContext = _jspx_page_context;
/* 5879 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5881 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 5882 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 5883 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 5885 */     _jspx_th_c_005fset_005f1.setVar("firstrow");
/*      */     
/* 5887 */     _jspx_th_c_005fset_005f1.setValue("true");
/* 5888 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 5889 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 5890 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 5891 */       return true;
/*      */     }
/* 5893 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 5894 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5899 */     PageContext pageContext = _jspx_page_context;
/* 5900 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5902 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 5903 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 5904 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*      */     
/* 5906 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*      */     
/* 5908 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 5909 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 5910 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 5911 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 5912 */       return true;
/*      */     }
/* 5914 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 5915 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5920 */     PageContext pageContext = _jspx_page_context;
/* 5921 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5923 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5924 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 5925 */     _jspx_th_c_005fif_005f13.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 5927 */     _jspx_th_c_005fif_005f13.setTest("${!empty firstrow}");
/* 5928 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 5929 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 5931 */         out.write("\n               </td>\n                   </tr>\n          </table>\n           ");
/* 5932 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 5933 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5937 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 5938 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 5939 */       return true;
/*      */     }
/* 5941 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 5942 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f35(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5947 */     PageContext pageContext = _jspx_page_context;
/* 5948 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5950 */     RadioTag _jspx_th_html_005fradio_005f35 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonchange_005fnobody.get(RadioTag.class);
/* 5951 */     _jspx_th_html_005fradio_005f35.setPageContext(_jspx_page_context);
/* 5952 */     _jspx_th_html_005fradio_005f35.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5954 */     _jspx_th_html_005fradio_005f35.setProperty("mspDesk");
/*      */     
/* 5956 */     _jspx_th_html_005fradio_005f35.setValue("false");
/*      */     
/* 5958 */     _jspx_th_html_005fradio_005f35.setOnchange("javascript:handleMSPDesk()");
/* 5959 */     int _jspx_eval_html_005fradio_005f35 = _jspx_th_html_005fradio_005f35.doStartTag();
/* 5960 */     if (_jspx_th_html_005fradio_005f35.doEndTag() == 5) {
/* 5961 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fradio_005f35);
/* 5962 */       return true;
/*      */     }
/* 5964 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fradio_005f35);
/* 5965 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f36(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5970 */     PageContext pageContext = _jspx_page_context;
/* 5971 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5973 */     RadioTag _jspx_th_html_005fradio_005f36 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonchange_005fnobody.get(RadioTag.class);
/* 5974 */     _jspx_th_html_005fradio_005f36.setPageContext(_jspx_page_context);
/* 5975 */     _jspx_th_html_005fradio_005f36.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5977 */     _jspx_th_html_005fradio_005f36.setProperty("mspDesk");
/*      */     
/* 5979 */     _jspx_th_html_005fradio_005f36.setValue("true");
/*      */     
/* 5981 */     _jspx_th_html_005fradio_005f36.setOnchange("javascript:handleMSPDesk()");
/* 5982 */     int _jspx_eval_html_005fradio_005f36 = _jspx_th_html_005fradio_005f36.doStartTag();
/* 5983 */     if (_jspx_th_html_005fradio_005f36.doEndTag() == 5) {
/* 5984 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fradio_005f36);
/* 5985 */       return true;
/*      */     }
/* 5987 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fradio_005f36);
/* 5988 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f7(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5993 */     PageContext pageContext = _jspx_page_context;
/* 5994 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5996 */     TextTag _jspx_th_html_005ftext_005f7 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 5997 */     _jspx_th_html_005ftext_005f7.setPageContext(_jspx_page_context);
/* 5998 */     _jspx_th_html_005ftext_005f7.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6000 */     _jspx_th_html_005ftext_005f7.setProperty("host");
/*      */     
/* 6002 */     _jspx_th_html_005ftext_005f7.setStyleClass("formtext default");
/* 6003 */     int _jspx_eval_html_005ftext_005f7 = _jspx_th_html_005ftext_005f7.doStartTag();
/* 6004 */     if (_jspx_th_html_005ftext_005f7.doEndTag() == 5) {
/* 6005 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 6006 */       return true;
/*      */     }
/* 6008 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 6009 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f8(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6014 */     PageContext pageContext = _jspx_page_context;
/* 6015 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6017 */     TextTag _jspx_th_html_005ftext_005f8 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6018 */     _jspx_th_html_005ftext_005f8.setPageContext(_jspx_page_context);
/* 6019 */     _jspx_th_html_005ftext_005f8.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6021 */     _jspx_th_html_005ftext_005f8.setProperty("port");
/*      */     
/* 6023 */     _jspx_th_html_005ftext_005f8.setStyleClass("formtext default");
/* 6024 */     int _jspx_eval_html_005ftext_005f8 = _jspx_th_html_005ftext_005f8.doStartTag();
/* 6025 */     if (_jspx_th_html_005ftext_005f8.doEndTag() == 5) {
/* 6026 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 6027 */       return true;
/*      */     }
/* 6029 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 6030 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f9(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6035 */     PageContext pageContext = _jspx_page_context;
/* 6036 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6038 */     TextTag _jspx_th_html_005ftext_005f9 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6039 */     _jspx_th_html_005ftext_005f9.setPageContext(_jspx_page_context);
/* 6040 */     _jspx_th_html_005ftext_005f9.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6042 */     _jspx_th_html_005ftext_005f9.setProperty("username");
/*      */     
/* 6044 */     _jspx_th_html_005ftext_005f9.setStyleClass("formtext default");
/* 6045 */     int _jspx_eval_html_005ftext_005f9 = _jspx_th_html_005ftext_005f9.doStartTag();
/* 6046 */     if (_jspx_th_html_005ftext_005f9.doEndTag() == 5) {
/* 6047 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/* 6048 */       return true;
/*      */     }
/* 6050 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/* 6051 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6056 */     PageContext pageContext = _jspx_page_context;
/* 6057 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6059 */     PasswordTag _jspx_th_html_005fpassword_005f1 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.get(PasswordTag.class);
/* 6060 */     _jspx_th_html_005fpassword_005f1.setPageContext(_jspx_page_context);
/* 6061 */     _jspx_th_html_005fpassword_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6063 */     _jspx_th_html_005fpassword_005f1.setProperty("password");
/*      */     
/* 6065 */     _jspx_th_html_005fpassword_005f1.setStyleClass("formtext default");
/* 6066 */     int _jspx_eval_html_005fpassword_005f1 = _jspx_th_html_005fpassword_005f1.doStartTag();
/* 6067 */     if (_jspx_th_html_005fpassword_005f1.doEndTag() == 5) {
/* 6068 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f1);
/* 6069 */       return true;
/*      */     }
/* 6071 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f1);
/* 6072 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f10(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6077 */     PageContext pageContext = _jspx_page_context;
/* 6078 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6080 */     TextTag _jspx_th_html_005ftext_005f10 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6081 */     _jspx_th_html_005ftext_005f10.setPageContext(_jspx_page_context);
/* 6082 */     _jspx_th_html_005ftext_005f10.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6084 */     _jspx_th_html_005ftext_005f10.setStyleClass("formtext default");
/*      */     
/* 6086 */     _jspx_th_html_005ftext_005f10.setProperty("restApiKey");
/* 6087 */     int _jspx_eval_html_005ftext_005f10 = _jspx_th_html_005ftext_005f10.doStartTag();
/* 6088 */     if (_jspx_th_html_005ftext_005f10.doEndTag() == 5) {
/* 6089 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f10);
/* 6090 */       return true;
/*      */     }
/* 6092 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f10);
/* 6093 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f37(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6098 */     PageContext pageContext = _jspx_page_context;
/* 6099 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6101 */     RadioTag _jspx_th_html_005fradio_005f37 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6102 */     _jspx_th_html_005fradio_005f37.setPageContext(_jspx_page_context);
/* 6103 */     _jspx_th_html_005fradio_005f37.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6105 */     _jspx_th_html_005fradio_005f37.setProperty("protocol");
/*      */     
/* 6107 */     _jspx_th_html_005fradio_005f37.setValue("http");
/* 6108 */     int _jspx_eval_html_005fradio_005f37 = _jspx_th_html_005fradio_005f37.doStartTag();
/* 6109 */     if (_jspx_th_html_005fradio_005f37.doEndTag() == 5) {
/* 6110 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f37);
/* 6111 */       return true;
/*      */     }
/* 6113 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f37);
/* 6114 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f38(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6119 */     PageContext pageContext = _jspx_page_context;
/* 6120 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6122 */     RadioTag _jspx_th_html_005fradio_005f38 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6123 */     _jspx_th_html_005fradio_005f38.setPageContext(_jspx_page_context);
/* 6124 */     _jspx_th_html_005fradio_005f38.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6126 */     _jspx_th_html_005fradio_005f38.setProperty("protocol");
/*      */     
/* 6128 */     _jspx_th_html_005fradio_005f38.setValue("https");
/* 6129 */     int _jspx_eval_html_005fradio_005f38 = _jspx_th_html_005fradio_005f38.doStartTag();
/* 6130 */     if (_jspx_th_html_005fradio_005f38.doEndTag() == 5) {
/* 6131 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f38);
/* 6132 */       return true;
/*      */     }
/* 6134 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f38);
/* 6135 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f11(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6140 */     PageContext pageContext = _jspx_page_context;
/* 6141 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6143 */     TextTag _jspx_th_html_005ftext_005f11 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6144 */     _jspx_th_html_005ftext_005f11.setPageContext(_jspx_page_context);
/* 6145 */     _jspx_th_html_005ftext_005f11.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6147 */     _jspx_th_html_005ftext_005f11.setProperty("toaddress");
/*      */     
/* 6149 */     _jspx_th_html_005ftext_005f11.setStyleClass("formtext label-align");
/* 6150 */     int _jspx_eval_html_005ftext_005f11 = _jspx_th_html_005ftext_005f11.doStartTag();
/* 6151 */     if (_jspx_th_html_005ftext_005f11.doEndTag() == 5) {
/* 6152 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f11);
/* 6153 */       return true;
/*      */     }
/* 6155 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f11);
/* 6156 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f12(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6161 */     PageContext pageContext = _jspx_page_context;
/* 6162 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6164 */     TextTag _jspx_th_html_005ftext_005f12 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6165 */     _jspx_th_html_005ftext_005f12.setPageContext(_jspx_page_context);
/* 6166 */     _jspx_th_html_005ftext_005f12.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6168 */     _jspx_th_html_005ftext_005f12.setProperty("fromaddress");
/*      */     
/* 6170 */     _jspx_th_html_005ftext_005f12.setStyleClass("formtext default");
/* 6171 */     int _jspx_eval_html_005ftext_005f12 = _jspx_th_html_005ftext_005f12.doStartTag();
/* 6172 */     if (_jspx_th_html_005ftext_005f12.doEndTag() == 5) {
/* 6173 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f12);
/* 6174 */       return true;
/*      */     }
/* 6176 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f12);
/* 6177 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f2(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6182 */     PageContext pageContext = _jspx_page_context;
/* 6183 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6185 */     CheckboxTag _jspx_th_html_005fcheckbox_005f2 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.get(CheckboxTag.class);
/* 6186 */     _jspx_th_html_005fcheckbox_005f2.setPageContext(_jspx_page_context);
/* 6187 */     _jspx_th_html_005fcheckbox_005f2.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6189 */     _jspx_th_html_005fcheckbox_005f2.setProperty("ticketingEnabled");
/*      */     
/* 6191 */     _jspx_th_html_005fcheckbox_005f2.setStyleId("sub_settings2");
/*      */     
/* 6193 */     _jspx_th_html_005fcheckbox_005f2.setOnchange("javascript:showAdvnSettings2()");
/*      */     
/* 6195 */     _jspx_th_html_005fcheckbox_005f2.setValue("true");
/* 6196 */     int _jspx_eval_html_005fcheckbox_005f2 = _jspx_th_html_005fcheckbox_005f2.doStartTag();
/* 6197 */     if (_jspx_th_html_005fcheckbox_005f2.doEndTag() == 5) {
/* 6198 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 6199 */       return true;
/*      */     }
/* 6201 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 6202 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f39(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6207 */     PageContext pageContext = _jspx_page_context;
/* 6208 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6210 */     RadioTag _jspx_th_html_005fradio_005f39 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6211 */     _jspx_th_html_005fradio_005f39.setPageContext(_jspx_page_context);
/* 6212 */     _jspx_th_html_005fradio_005f39.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6214 */     _jspx_th_html_005fradio_005f39.setProperty("reOpenTicketPolicy");
/*      */     
/* 6216 */     _jspx_th_html_005fradio_005f39.setValue("REOPEN_TICKET");
/* 6217 */     int _jspx_eval_html_005fradio_005f39 = _jspx_th_html_005fradio_005f39.doStartTag();
/* 6218 */     if (_jspx_th_html_005fradio_005f39.doEndTag() == 5) {
/* 6219 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f39);
/* 6220 */       return true;
/*      */     }
/* 6222 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f39);
/* 6223 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f40(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6228 */     PageContext pageContext = _jspx_page_context;
/* 6229 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6231 */     RadioTag _jspx_th_html_005fradio_005f40 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6232 */     _jspx_th_html_005fradio_005f40.setPageContext(_jspx_page_context);
/* 6233 */     _jspx_th_html_005fradio_005f40.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6235 */     _jspx_th_html_005fradio_005f40.setProperty("reOpenTicketPolicy");
/*      */     
/* 6237 */     _jspx_th_html_005fradio_005f40.setValue("REOPEN_TICKET_CUSTOM_PERIOD");
/* 6238 */     int _jspx_eval_html_005fradio_005f40 = _jspx_th_html_005fradio_005f40.doStartTag();
/* 6239 */     if (_jspx_th_html_005fradio_005f40.doEndTag() == 5) {
/* 6240 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f40);
/* 6241 */       return true;
/*      */     }
/* 6243 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f40);
/* 6244 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f13(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6249 */     PageContext pageContext = _jspx_page_context;
/* 6250 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6252 */     TextTag _jspx_th_html_005ftext_005f13 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6253 */     _jspx_th_html_005ftext_005f13.setPageContext(_jspx_page_context);
/* 6254 */     _jspx_th_html_005ftext_005f13.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6256 */     _jspx_th_html_005ftext_005f13.setProperty("reOpenPeriod");
/*      */     
/* 6258 */     _jspx_th_html_005ftext_005f13.setSize("3");
/*      */     
/* 6260 */     _jspx_th_html_005ftext_005f13.setStyleClass("formtext");
/* 6261 */     int _jspx_eval_html_005ftext_005f13 = _jspx_th_html_005ftext_005f13.doStartTag();
/* 6262 */     if (_jspx_th_html_005ftext_005f13.doEndTag() == 5) {
/* 6263 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f13);
/* 6264 */       return true;
/*      */     }
/* 6266 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f13);
/* 6267 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f41(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6272 */     PageContext pageContext = _jspx_page_context;
/* 6273 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6275 */     RadioTag _jspx_th_html_005fradio_005f41 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6276 */     _jspx_th_html_005fradio_005f41.setPageContext(_jspx_page_context);
/* 6277 */     _jspx_th_html_005fradio_005f41.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6279 */     _jspx_th_html_005fradio_005f41.setProperty("reOpenTicketPolicy");
/*      */     
/* 6281 */     _jspx_th_html_005fradio_005f41.setValue("NEW_TICKET");
/* 6282 */     int _jspx_eval_html_005fradio_005f41 = _jspx_th_html_005fradio_005f41.doStartTag();
/* 6283 */     if (_jspx_th_html_005fradio_005f41.doEndTag() == 5) {
/* 6284 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f41);
/* 6285 */       return true;
/*      */     }
/* 6287 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f41);
/* 6288 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f42(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6293 */     PageContext pageContext = _jspx_page_context;
/* 6294 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6296 */     RadioTag _jspx_th_html_005fradio_005f42 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6297 */     _jspx_th_html_005fradio_005f42.setPageContext(_jspx_page_context);
/* 6298 */     _jspx_th_html_005fradio_005f42.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6300 */     _jspx_th_html_005fradio_005f42.setProperty("closeTicketPolicy");
/*      */     
/* 6302 */     _jspx_th_html_005fradio_005f42.setValue("CLOSE_TICKET_UPDATE_NOTES");
/* 6303 */     int _jspx_eval_html_005fradio_005f42 = _jspx_th_html_005fradio_005f42.doStartTag();
/* 6304 */     if (_jspx_th_html_005fradio_005f42.doEndTag() == 5) {
/* 6305 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f42);
/* 6306 */       return true;
/*      */     }
/* 6308 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f42);
/* 6309 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f43(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6314 */     PageContext pageContext = _jspx_page_context;
/* 6315 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6317 */     RadioTag _jspx_th_html_005fradio_005f43 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6318 */     _jspx_th_html_005fradio_005f43.setPageContext(_jspx_page_context);
/* 6319 */     _jspx_th_html_005fradio_005f43.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6321 */     _jspx_th_html_005fradio_005f43.setProperty("closeTicketPolicy");
/*      */     
/* 6323 */     _jspx_th_html_005fradio_005f43.setValue("UPDATE_NOTES");
/* 6324 */     int _jspx_eval_html_005fradio_005f43 = _jspx_th_html_005fradio_005f43.doStartTag();
/* 6325 */     if (_jspx_th_html_005fradio_005f43.doEndTag() == 5) {
/* 6326 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f43);
/* 6327 */       return true;
/*      */     }
/* 6329 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f43);
/* 6330 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f44(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6335 */     PageContext pageContext = _jspx_page_context;
/* 6336 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6338 */     RadioTag _jspx_th_html_005fradio_005f44 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6339 */     _jspx_th_html_005fradio_005f44.setPageContext(_jspx_page_context);
/* 6340 */     _jspx_th_html_005fradio_005f44.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6342 */     _jspx_th_html_005fradio_005f44.setProperty("notesToBeAddedForTicket");
/*      */     
/* 6344 */     _jspx_th_html_005fradio_005f44.setValue("true");
/* 6345 */     int _jspx_eval_html_005fradio_005f44 = _jspx_th_html_005fradio_005f44.doStartTag();
/* 6346 */     if (_jspx_th_html_005fradio_005f44.doEndTag() == 5) {
/* 6347 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f44);
/* 6348 */       return true;
/*      */     }
/* 6350 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f44);
/* 6351 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f45(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6356 */     PageContext pageContext = _jspx_page_context;
/* 6357 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6359 */     RadioTag _jspx_th_html_005fradio_005f45 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6360 */     _jspx_th_html_005fradio_005f45.setPageContext(_jspx_page_context);
/* 6361 */     _jspx_th_html_005fradio_005f45.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6363 */     _jspx_th_html_005fradio_005f45.setProperty("notesToBeAddedForTicket");
/*      */     
/* 6365 */     _jspx_th_html_005fradio_005f45.setValue("false");
/* 6366 */     int _jspx_eval_html_005fradio_005f45 = _jspx_th_html_005fradio_005f45.doStartTag();
/* 6367 */     if (_jspx_th_html_005fradio_005f45.doEndTag() == 5) {
/* 6368 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f45);
/* 6369 */       return true;
/*      */     }
/* 6371 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f45);
/* 6372 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f46(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6377 */     PageContext pageContext = _jspx_page_context;
/* 6378 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6380 */     RadioTag _jspx_th_html_005fradio_005f46 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6381 */     _jspx_th_html_005fradio_005f46.setPageContext(_jspx_page_context);
/* 6382 */     _jspx_th_html_005fradio_005f46.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6384 */     _jspx_th_html_005fradio_005f46.setProperty("dynamicTicketingUsingAction");
/*      */     
/* 6386 */     _jspx_th_html_005fradio_005f46.setValue("true");
/* 6387 */     int _jspx_eval_html_005fradio_005f46 = _jspx_th_html_005fradio_005f46.doStartTag();
/* 6388 */     if (_jspx_th_html_005fradio_005f46.doEndTag() == 5) {
/* 6389 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f46);
/* 6390 */       return true;
/*      */     }
/* 6392 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f46);
/* 6393 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f47(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6398 */     PageContext pageContext = _jspx_page_context;
/* 6399 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6401 */     RadioTag _jspx_th_html_005fradio_005f47 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6402 */     _jspx_th_html_005fradio_005f47.setPageContext(_jspx_page_context);
/* 6403 */     _jspx_th_html_005fradio_005f47.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6405 */     _jspx_th_html_005fradio_005f47.setProperty("dynamicTicketingUsingAction");
/*      */     
/* 6407 */     _jspx_th_html_005fradio_005f47.setValue("false");
/* 6408 */     int _jspx_eval_html_005fradio_005f47 = _jspx_th_html_005fradio_005f47.doStartTag();
/* 6409 */     if (_jspx_th_html_005fradio_005f47.doEndTag() == 5) {
/* 6410 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f47);
/* 6411 */       return true;
/*      */     }
/* 6413 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f47);
/* 6414 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f48(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6419 */     PageContext pageContext = _jspx_page_context;
/* 6420 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6422 */     RadioTag _jspx_th_html_005fradio_005f48 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6423 */     _jspx_th_html_005fradio_005f48.setPageContext(_jspx_page_context);
/* 6424 */     _jspx_th_html_005fradio_005f48.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6426 */     _jspx_th_html_005fradio_005f48.setProperty("dynamicTicketingUsingForm");
/*      */     
/* 6428 */     _jspx_th_html_005fradio_005f48.setValue("true");
/* 6429 */     int _jspx_eval_html_005fradio_005f48 = _jspx_th_html_005fradio_005f48.doStartTag();
/* 6430 */     if (_jspx_th_html_005fradio_005f48.doEndTag() == 5) {
/* 6431 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f48);
/* 6432 */       return true;
/*      */     }
/* 6434 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f48);
/* 6435 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f49(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6440 */     PageContext pageContext = _jspx_page_context;
/* 6441 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6443 */     RadioTag _jspx_th_html_005fradio_005f49 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6444 */     _jspx_th_html_005fradio_005f49.setPageContext(_jspx_page_context);
/* 6445 */     _jspx_th_html_005fradio_005f49.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6447 */     _jspx_th_html_005fradio_005f49.setProperty("dynamicTicketingUsingForm");
/*      */     
/* 6449 */     _jspx_th_html_005fradio_005f49.setValue("false");
/* 6450 */     int _jspx_eval_html_005fradio_005f49 = _jspx_th_html_005fradio_005f49.doStartTag();
/* 6451 */     if (_jspx_th_html_005fradio_005f49.doEndTag() == 5) {
/* 6452 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f49);
/* 6453 */       return true;
/*      */     }
/* 6455 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f49);
/* 6456 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f50(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6461 */     PageContext pageContext = _jspx_page_context;
/* 6462 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6464 */     RadioTag _jspx_th_html_005fradio_005f50 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6465 */     _jspx_th_html_005fradio_005f50.setPageContext(_jspx_page_context);
/* 6466 */     _jspx_th_html_005fradio_005f50.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6468 */     _jspx_th_html_005fradio_005f50.setProperty("ticketLinkToBeShown");
/*      */     
/* 6470 */     _jspx_th_html_005fradio_005f50.setValue("true");
/* 6471 */     int _jspx_eval_html_005fradio_005f50 = _jspx_th_html_005fradio_005f50.doStartTag();
/* 6472 */     if (_jspx_th_html_005fradio_005f50.doEndTag() == 5) {
/* 6473 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f50);
/* 6474 */       return true;
/*      */     }
/* 6476 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f50);
/* 6477 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f51(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6482 */     PageContext pageContext = _jspx_page_context;
/* 6483 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6485 */     RadioTag _jspx_th_html_005fradio_005f51 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6486 */     _jspx_th_html_005fradio_005f51.setPageContext(_jspx_page_context);
/* 6487 */     _jspx_th_html_005fradio_005f51.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6489 */     _jspx_th_html_005fradio_005f51.setProperty("ticketLinkToBeShown");
/*      */     
/* 6491 */     _jspx_th_html_005fradio_005f51.setValue("false");
/* 6492 */     int _jspx_eval_html_005fradio_005f51 = _jspx_th_html_005fradio_005f51.doStartTag();
/* 6493 */     if (_jspx_th_html_005fradio_005f51.doEndTag() == 5) {
/* 6494 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f51);
/* 6495 */       return true;
/*      */     }
/* 6497 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f51);
/* 6498 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f52(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6503 */     PageContext pageContext = _jspx_page_context;
/* 6504 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6506 */     RadioTag _jspx_th_html_005fradio_005f52 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6507 */     _jspx_th_html_005fradio_005f52.setPageContext(_jspx_page_context);
/* 6508 */     _jspx_th_html_005fradio_005f52.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 6510 */     _jspx_th_html_005fradio_005f52.setProperty("readonlyTicket");
/*      */     
/* 6512 */     _jspx_th_html_005fradio_005f52.setValue("true");
/* 6513 */     int _jspx_eval_html_005fradio_005f52 = _jspx_th_html_005fradio_005f52.doStartTag();
/* 6514 */     if (_jspx_th_html_005fradio_005f52.doEndTag() == 5) {
/* 6515 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f52);
/* 6516 */       return true;
/*      */     }
/* 6518 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f52);
/* 6519 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f53(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6524 */     PageContext pageContext = _jspx_page_context;
/* 6525 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6527 */     RadioTag _jspx_th_html_005fradio_005f53 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6528 */     _jspx_th_html_005fradio_005f53.setPageContext(_jspx_page_context);
/* 6529 */     _jspx_th_html_005fradio_005f53.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 6531 */     _jspx_th_html_005fradio_005f53.setProperty("readonlyTicket");
/*      */     
/* 6533 */     _jspx_th_html_005fradio_005f53.setValue("false");
/* 6534 */     int _jspx_eval_html_005fradio_005f53 = _jspx_th_html_005fradio_005f53.doStartTag();
/* 6535 */     if (_jspx_th_html_005fradio_005f53.doEndTag() == 5) {
/* 6536 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f53);
/* 6537 */       return true;
/*      */     }
/* 6539 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f53);
/* 6540 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f54(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6545 */     PageContext pageContext = _jspx_page_context;
/* 6546 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6548 */     RadioTag _jspx_th_html_005fradio_005f54 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6549 */     _jspx_th_html_005fradio_005f54.setPageContext(_jspx_page_context);
/* 6550 */     _jspx_th_html_005fradio_005f54.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 6552 */     _jspx_th_html_005fradio_005f54.setProperty("updateTicketForchangeInStatusAlone");
/*      */     
/* 6554 */     _jspx_th_html_005fradio_005f54.setValue("true");
/* 6555 */     int _jspx_eval_html_005fradio_005f54 = _jspx_th_html_005fradio_005f54.doStartTag();
/* 6556 */     if (_jspx_th_html_005fradio_005f54.doEndTag() == 5) {
/* 6557 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f54);
/* 6558 */       return true;
/*      */     }
/* 6560 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f54);
/* 6561 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f55(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6566 */     PageContext pageContext = _jspx_page_context;
/* 6567 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6569 */     RadioTag _jspx_th_html_005fradio_005f55 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6570 */     _jspx_th_html_005fradio_005f55.setPageContext(_jspx_page_context);
/* 6571 */     _jspx_th_html_005fradio_005f55.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 6573 */     _jspx_th_html_005fradio_005f55.setProperty("updateTicketForchangeInStatusAlone");
/*      */     
/* 6575 */     _jspx_th_html_005fradio_005f55.setValue("false");
/* 6576 */     int _jspx_eval_html_005fradio_005f55 = _jspx_th_html_005fradio_005f55.doStartTag();
/* 6577 */     if (_jspx_th_html_005fradio_005f55.doEndTag() == 5) {
/* 6578 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f55);
/* 6579 */       return true;
/*      */     }
/* 6581 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f55);
/* 6582 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f56(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6587 */     PageContext pageContext = _jspx_page_context;
/* 6588 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6590 */     RadioTag _jspx_th_html_005fradio_005f56 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6591 */     _jspx_th_html_005fradio_005f56.setPageContext(_jspx_page_context);
/* 6592 */     _jspx_th_html_005fradio_005f56.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 6594 */     _jspx_th_html_005fradio_005f56.setProperty("allowOverWriteOfReqTemplate");
/*      */     
/* 6596 */     _jspx_th_html_005fradio_005f56.setValue("true");
/* 6597 */     int _jspx_eval_html_005fradio_005f56 = _jspx_th_html_005fradio_005f56.doStartTag();
/* 6598 */     if (_jspx_th_html_005fradio_005f56.doEndTag() == 5) {
/* 6599 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f56);
/* 6600 */       return true;
/*      */     }
/* 6602 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f56);
/* 6603 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f57(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6608 */     PageContext pageContext = _jspx_page_context;
/* 6609 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6611 */     RadioTag _jspx_th_html_005fradio_005f57 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6612 */     _jspx_th_html_005fradio_005f57.setPageContext(_jspx_page_context);
/* 6613 */     _jspx_th_html_005fradio_005f57.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 6615 */     _jspx_th_html_005fradio_005f57.setProperty("allowOverWriteOfReqTemplate");
/*      */     
/* 6617 */     _jspx_th_html_005fradio_005f57.setValue("false");
/* 6618 */     int _jspx_eval_html_005fradio_005f57 = _jspx_th_html_005fradio_005f57.doStartTag();
/* 6619 */     if (_jspx_th_html_005fradio_005f57.doEndTag() == 5) {
/* 6620 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f57);
/* 6621 */       return true;
/*      */     }
/* 6623 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f57);
/* 6624 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f3(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6629 */     PageContext pageContext = _jspx_page_context;
/* 6630 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6632 */     CheckboxTag _jspx_th_html_005fcheckbox_005f3 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.get(CheckboxTag.class);
/* 6633 */     _jspx_th_html_005fcheckbox_005f3.setPageContext(_jspx_page_context);
/* 6634 */     _jspx_th_html_005fcheckbox_005f3.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6636 */     _jspx_th_html_005fcheckbox_005f3.setProperty("cISyncEnabled");
/*      */     
/* 6638 */     _jspx_th_html_005fcheckbox_005f3.setStyleId("sub_settings1");
/*      */     
/* 6640 */     _jspx_th_html_005fcheckbox_005f3.setOnchange("javascript:showAdvnSettings1()");
/*      */     
/* 6642 */     _jspx_th_html_005fcheckbox_005f3.setValue("true");
/* 6643 */     int _jspx_eval_html_005fcheckbox_005f3 = _jspx_th_html_005fcheckbox_005f3.doStartTag();
/* 6644 */     if (_jspx_th_html_005fcheckbox_005f3.doEndTag() == 5) {
/* 6645 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/* 6646 */       return true;
/*      */     }
/* 6648 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/* 6649 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f58(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6654 */     PageContext pageContext = _jspx_page_context;
/* 6655 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6657 */     RadioTag _jspx_th_html_005fradio_005f58 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6658 */     _jspx_th_html_005fradio_005f58.setPageContext(_jspx_page_context);
/* 6659 */     _jspx_th_html_005fradio_005f58.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6661 */     _jspx_th_html_005fradio_005f58.setProperty("ciToBeDeleted");
/*      */     
/* 6663 */     _jspx_th_html_005fradio_005f58.setValue("true");
/* 6664 */     int _jspx_eval_html_005fradio_005f58 = _jspx_th_html_005fradio_005f58.doStartTag();
/* 6665 */     if (_jspx_th_html_005fradio_005f58.doEndTag() == 5) {
/* 6666 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f58);
/* 6667 */       return true;
/*      */     }
/* 6669 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f58);
/* 6670 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f59(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6675 */     PageContext pageContext = _jspx_page_context;
/* 6676 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6678 */     RadioTag _jspx_th_html_005fradio_005f59 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6679 */     _jspx_th_html_005fradio_005f59.setPageContext(_jspx_page_context);
/* 6680 */     _jspx_th_html_005fradio_005f59.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6682 */     _jspx_th_html_005fradio_005f59.setProperty("ciToBeDeleted");
/*      */     
/* 6684 */     _jspx_th_html_005fradio_005f59.setValue("false");
/* 6685 */     int _jspx_eval_html_005fradio_005f59 = _jspx_th_html_005fradio_005f59.doStartTag();
/* 6686 */     if (_jspx_th_html_005fradio_005f59.doEndTag() == 5) {
/* 6687 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f59);
/* 6688 */       return true;
/*      */     }
/* 6690 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f59);
/* 6691 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f4(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6696 */     PageContext pageContext = _jspx_page_context;
/* 6697 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6699 */     SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 6700 */     _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/* 6701 */     _jspx_th_html_005fselect_005f4.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6703 */     _jspx_th_html_005fselect_005f4.setProperty("toAdd");
/*      */     
/* 6705 */     _jspx_th_html_005fselect_005f4.setStyle("width:100%");
/*      */     
/* 6707 */     _jspx_th_html_005fselect_005f4.setMultiple("true");
/*      */     
/* 6709 */     _jspx_th_html_005fselect_005f4.setSize("5");
/* 6710 */     int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/* 6711 */     if (_jspx_eval_html_005fselect_005f4 != 0) {
/* 6712 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 6713 */         out = _jspx_page_context.pushBody();
/* 6714 */         _jspx_th_html_005fselect_005f4.setBodyContent((BodyContent)out);
/* 6715 */         _jspx_th_html_005fselect_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6718 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 6719 */         if (_jspx_meth_html_005foptionsCollection_005f4(_jspx_th_html_005fselect_005f4, _jspx_page_context))
/* 6720 */           return true;
/* 6721 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 6722 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/* 6723 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6726 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 6727 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6730 */     if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/* 6731 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f4);
/* 6732 */       return true;
/*      */     }
/* 6734 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f4);
/* 6735 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f4(JspTag _jspx_th_html_005fselect_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6740 */     PageContext pageContext = _jspx_page_context;
/* 6741 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6743 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f4 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.get(OptionsCollectionTag.class);
/* 6744 */     _jspx_th_html_005foptionsCollection_005f4.setPageContext(_jspx_page_context);
/* 6745 */     _jspx_th_html_005foptionsCollection_005f4.setParent((Tag)_jspx_th_html_005fselect_005f4);
/*      */     
/* 6747 */     _jspx_th_html_005foptionsCollection_005f4.setProperty("firstLevelMonitorTypesOptions");
/*      */     
/* 6749 */     _jspx_th_html_005foptionsCollection_005f4.setLabel("label");
/*      */     
/* 6751 */     _jspx_th_html_005foptionsCollection_005f4.setValue("value");
/* 6752 */     int _jspx_eval_html_005foptionsCollection_005f4 = _jspx_th_html_005foptionsCollection_005f4.doStartTag();
/* 6753 */     if (_jspx_th_html_005foptionsCollection_005f4.doEndTag() == 5) {
/* 6754 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f4);
/* 6755 */       return true;
/*      */     }
/* 6757 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f4);
/* 6758 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f8(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6763 */     PageContext pageContext = _jspx_page_context;
/* 6764 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6766 */     ButtonTag _jspx_th_html_005fbutton_005f8 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 6767 */     _jspx_th_html_005fbutton_005f8.setPageContext(_jspx_page_context);
/* 6768 */     _jspx_th_html_005fbutton_005f8.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6770 */     _jspx_th_html_005fbutton_005f8.setStyleClass("buttons btn_small");
/*      */     
/* 6772 */     _jspx_th_html_005fbutton_005f8.setProperty("AddButton2");
/*      */     
/* 6774 */     _jspx_th_html_005fbutton_005f8.setOnclick("javascript:fnAddToRightCombo(document.AMActionForm.toAdd,document.AMActionForm.excludeFirstLevelMonitorTypes),fnRemoveFromRightCombo(document.AMActionForm.toAdd);");
/*      */     
/* 6776 */     _jspx_th_html_005fbutton_005f8.setValue("&nbsp;&gt;&nbsp;");
/* 6777 */     int _jspx_eval_html_005fbutton_005f8 = _jspx_th_html_005fbutton_005f8.doStartTag();
/* 6778 */     if (_jspx_th_html_005fbutton_005f8.doEndTag() == 5) {
/* 6779 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f8);
/* 6780 */       return true;
/*      */     }
/* 6782 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f8);
/* 6783 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f9(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6788 */     PageContext pageContext = _jspx_page_context;
/* 6789 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6791 */     ButtonTag _jspx_th_html_005fbutton_005f9 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 6792 */     _jspx_th_html_005fbutton_005f9.setPageContext(_jspx_page_context);
/* 6793 */     _jspx_th_html_005fbutton_005f9.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6795 */     _jspx_th_html_005fbutton_005f9.setStyleClass("buttons btn_small");
/*      */     
/* 6797 */     _jspx_th_html_005fbutton_005f9.setProperty("AddButton2");
/*      */     
/* 6799 */     _jspx_th_html_005fbutton_005f9.setOnclick("javascript:fnAddAllFromCombo(document.AMActionForm.toAdd,document.AMActionForm.excludeFirstLevelMonitorTypes),fnRemoveAllFromCombo(document.AMActionForm.toAdd);");
/*      */     
/* 6801 */     _jspx_th_html_005fbutton_005f9.setValue("&gt;&gt;");
/* 6802 */     int _jspx_eval_html_005fbutton_005f9 = _jspx_th_html_005fbutton_005f9.doStartTag();
/* 6803 */     if (_jspx_th_html_005fbutton_005f9.doEndTag() == 5) {
/* 6804 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f9);
/* 6805 */       return true;
/*      */     }
/* 6807 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f9);
/* 6808 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f10(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6813 */     PageContext pageContext = _jspx_page_context;
/* 6814 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6816 */     ButtonTag _jspx_th_html_005fbutton_005f10 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 6817 */     _jspx_th_html_005fbutton_005f10.setPageContext(_jspx_page_context);
/* 6818 */     _jspx_th_html_005fbutton_005f10.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6820 */     _jspx_th_html_005fbutton_005f10.setStyleClass("buttons btn_small");
/*      */     
/* 6822 */     _jspx_th_html_005fbutton_005f10.setProperty("AddButton2");
/*      */     
/* 6824 */     _jspx_th_html_005fbutton_005f10.setOnclick("javascript:fnAddAllFromCombo(document.AMActionForm.excludeFirstLevelMonitorTypes,document.AMActionForm.toAdd),fnRemoveAllFromCombo(document.AMActionForm.excludeFirstLevelMonitorTypes);");
/*      */     
/* 6826 */     _jspx_th_html_005fbutton_005f10.setValue("&lt;&lt;");
/* 6827 */     int _jspx_eval_html_005fbutton_005f10 = _jspx_th_html_005fbutton_005f10.doStartTag();
/* 6828 */     if (_jspx_th_html_005fbutton_005f10.doEndTag() == 5) {
/* 6829 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f10);
/* 6830 */       return true;
/*      */     }
/* 6832 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f10);
/* 6833 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f11(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6838 */     PageContext pageContext = _jspx_page_context;
/* 6839 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6841 */     ButtonTag _jspx_th_html_005fbutton_005f11 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 6842 */     _jspx_th_html_005fbutton_005f11.setPageContext(_jspx_page_context);
/* 6843 */     _jspx_th_html_005fbutton_005f11.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6845 */     _jspx_th_html_005fbutton_005f11.setStyleClass("buttons btn_small");
/*      */     
/* 6847 */     _jspx_th_html_005fbutton_005f11.setProperty("AddButton2");
/*      */     
/* 6849 */     _jspx_th_html_005fbutton_005f11.setOnclick("javascript:fnAddToRightCombo(document.AMActionForm.excludeFirstLevelMonitorTypes,document.AMActionForm.toAdd),fnRemoveFromRightCombo(document.AMActionForm.excludeFirstLevelMonitorTypes);");
/*      */     
/* 6851 */     _jspx_th_html_005fbutton_005f11.setValue("&nbsp;&lt;&nbsp;");
/* 6852 */     int _jspx_eval_html_005fbutton_005f11 = _jspx_th_html_005fbutton_005f11.doStartTag();
/* 6853 */     if (_jspx_th_html_005fbutton_005f11.doEndTag() == 5) {
/* 6854 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f11);
/* 6855 */       return true;
/*      */     }
/* 6857 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f11);
/* 6858 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f5(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6863 */     PageContext pageContext = _jspx_page_context;
/* 6864 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6866 */     SelectTag _jspx_th_html_005fselect_005f5 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 6867 */     _jspx_th_html_005fselect_005f5.setPageContext(_jspx_page_context);
/* 6868 */     _jspx_th_html_005fselect_005f5.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6870 */     _jspx_th_html_005fselect_005f5.setProperty("excludeFirstLevelMonitorTypes");
/*      */     
/* 6872 */     _jspx_th_html_005fselect_005f5.setStyle("width:100%");
/*      */     
/* 6874 */     _jspx_th_html_005fselect_005f5.setMultiple("true");
/*      */     
/* 6876 */     _jspx_th_html_005fselect_005f5.setSize("5");
/* 6877 */     int _jspx_eval_html_005fselect_005f5 = _jspx_th_html_005fselect_005f5.doStartTag();
/* 6878 */     if (_jspx_eval_html_005fselect_005f5 != 0) {
/* 6879 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 6880 */         out = _jspx_page_context.pushBody();
/* 6881 */         _jspx_th_html_005fselect_005f5.setBodyContent((BodyContent)out);
/* 6882 */         _jspx_th_html_005fselect_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6885 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 6886 */         if (_jspx_meth_html_005foptionsCollection_005f5(_jspx_th_html_005fselect_005f5, _jspx_page_context))
/* 6887 */           return true;
/* 6888 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 6889 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f5.doAfterBody();
/* 6890 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6893 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 6894 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6897 */     if (_jspx_th_html_005fselect_005f5.doEndTag() == 5) {
/* 6898 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f5);
/* 6899 */       return true;
/*      */     }
/* 6901 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f5);
/* 6902 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f5(JspTag _jspx_th_html_005fselect_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6907 */     PageContext pageContext = _jspx_page_context;
/* 6908 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6910 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f5 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.get(OptionsCollectionTag.class);
/* 6911 */     _jspx_th_html_005foptionsCollection_005f5.setPageContext(_jspx_page_context);
/* 6912 */     _jspx_th_html_005foptionsCollection_005f5.setParent((Tag)_jspx_th_html_005fselect_005f5);
/*      */     
/* 6914 */     _jspx_th_html_005foptionsCollection_005f5.setProperty("excludeFirstLevelMonitorTypesOptions");
/*      */     
/* 6916 */     _jspx_th_html_005foptionsCollection_005f5.setLabel("label");
/*      */     
/* 6918 */     _jspx_th_html_005foptionsCollection_005f5.setValue("value");
/* 6919 */     int _jspx_eval_html_005foptionsCollection_005f5 = _jspx_th_html_005foptionsCollection_005f5.doStartTag();
/* 6920 */     if (_jspx_th_html_005foptionsCollection_005f5.doEndTag() == 5) {
/* 6921 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f5);
/* 6922 */       return true;
/*      */     }
/* 6924 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f5);
/* 6925 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f6(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6930 */     PageContext pageContext = _jspx_page_context;
/* 6931 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6933 */     SelectTag _jspx_th_html_005fselect_005f6 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 6934 */     _jspx_th_html_005fselect_005f6.setPageContext(_jspx_page_context);
/* 6935 */     _jspx_th_html_005fselect_005f6.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 6937 */     _jspx_th_html_005fselect_005f6.setProperty("toAddg");
/*      */     
/* 6939 */     _jspx_th_html_005fselect_005f6.setStyle("width:100%");
/*      */     
/* 6941 */     _jspx_th_html_005fselect_005f6.setMultiple("true");
/*      */     
/* 6943 */     _jspx_th_html_005fselect_005f6.setSize("5");
/* 6944 */     int _jspx_eval_html_005fselect_005f6 = _jspx_th_html_005fselect_005f6.doStartTag();
/* 6945 */     if (_jspx_eval_html_005fselect_005f6 != 0) {
/* 6946 */       if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 6947 */         out = _jspx_page_context.pushBody();
/* 6948 */         _jspx_th_html_005fselect_005f6.setBodyContent((BodyContent)out);
/* 6949 */         _jspx_th_html_005fselect_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6952 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 6953 */         if (_jspx_meth_html_005foptionsCollection_005f6(_jspx_th_html_005fselect_005f6, _jspx_page_context))
/* 6954 */           return true;
/* 6955 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 6956 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f6.doAfterBody();
/* 6957 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6960 */       if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 6961 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6964 */     if (_jspx_th_html_005fselect_005f6.doEndTag() == 5) {
/* 6965 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f6);
/* 6966 */       return true;
/*      */     }
/* 6968 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f6);
/* 6969 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f6(JspTag _jspx_th_html_005fselect_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6974 */     PageContext pageContext = _jspx_page_context;
/* 6975 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6977 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f6 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.get(OptionsCollectionTag.class);
/* 6978 */     _jspx_th_html_005foptionsCollection_005f6.setPageContext(_jspx_page_context);
/* 6979 */     _jspx_th_html_005foptionsCollection_005f6.setParent((Tag)_jspx_th_html_005fselect_005f6);
/*      */     
/* 6981 */     _jspx_th_html_005foptionsCollection_005f6.setProperty("secondLevelMonitorTypesOptions");
/*      */     
/* 6983 */     _jspx_th_html_005foptionsCollection_005f6.setLabel("label");
/*      */     
/* 6985 */     _jspx_th_html_005foptionsCollection_005f6.setValue("value");
/* 6986 */     int _jspx_eval_html_005foptionsCollection_005f6 = _jspx_th_html_005foptionsCollection_005f6.doStartTag();
/* 6987 */     if (_jspx_th_html_005foptionsCollection_005f6.doEndTag() == 5) {
/* 6988 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f6);
/* 6989 */       return true;
/*      */     }
/* 6991 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f6);
/* 6992 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f12(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6997 */     PageContext pageContext = _jspx_page_context;
/* 6998 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7000 */     ButtonTag _jspx_th_html_005fbutton_005f12 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 7001 */     _jspx_th_html_005fbutton_005f12.setPageContext(_jspx_page_context);
/* 7002 */     _jspx_th_html_005fbutton_005f12.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 7004 */     _jspx_th_html_005fbutton_005f12.setStyleClass("buttons btn_small");
/*      */     
/* 7006 */     _jspx_th_html_005fbutton_005f12.setProperty("AddButton2");
/*      */     
/* 7008 */     _jspx_th_html_005fbutton_005f12.setOnclick("javascript:fnAddToRightCombo(document.AMActionForm.toAddg,document.AMActionForm.includeSecondLevelMonitorTypes),fnRemoveFromRightCombo(document.AMActionForm.toAddg);");
/*      */     
/* 7010 */     _jspx_th_html_005fbutton_005f12.setValue("&nbsp;&gt;&nbsp;");
/* 7011 */     int _jspx_eval_html_005fbutton_005f12 = _jspx_th_html_005fbutton_005f12.doStartTag();
/* 7012 */     if (_jspx_th_html_005fbutton_005f12.doEndTag() == 5) {
/* 7013 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f12);
/* 7014 */       return true;
/*      */     }
/* 7016 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f12);
/* 7017 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f13(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7022 */     PageContext pageContext = _jspx_page_context;
/* 7023 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7025 */     ButtonTag _jspx_th_html_005fbutton_005f13 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 7026 */     _jspx_th_html_005fbutton_005f13.setPageContext(_jspx_page_context);
/* 7027 */     _jspx_th_html_005fbutton_005f13.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 7029 */     _jspx_th_html_005fbutton_005f13.setStyleClass("buttons btn_small");
/*      */     
/* 7031 */     _jspx_th_html_005fbutton_005f13.setProperty("AddButton2");
/*      */     
/* 7033 */     _jspx_th_html_005fbutton_005f13.setOnclick("javascript:fnAddAllFromCombo(document.AMActionForm.toAddg,document.AMActionForm.includeSecondLevelMonitorTypes),fnRemoveAllFromCombo(document.AMActionForm.toAddg);");
/*      */     
/* 7035 */     _jspx_th_html_005fbutton_005f13.setValue("&gt;&gt;");
/* 7036 */     int _jspx_eval_html_005fbutton_005f13 = _jspx_th_html_005fbutton_005f13.doStartTag();
/* 7037 */     if (_jspx_th_html_005fbutton_005f13.doEndTag() == 5) {
/* 7038 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f13);
/* 7039 */       return true;
/*      */     }
/* 7041 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f13);
/* 7042 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f14(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7047 */     PageContext pageContext = _jspx_page_context;
/* 7048 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7050 */     ButtonTag _jspx_th_html_005fbutton_005f14 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 7051 */     _jspx_th_html_005fbutton_005f14.setPageContext(_jspx_page_context);
/* 7052 */     _jspx_th_html_005fbutton_005f14.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 7054 */     _jspx_th_html_005fbutton_005f14.setStyleClass("buttons btn_small");
/*      */     
/* 7056 */     _jspx_th_html_005fbutton_005f14.setProperty("AddButton2");
/*      */     
/* 7058 */     _jspx_th_html_005fbutton_005f14.setOnclick("javascript:fnAddAllFromCombo(document.AMActionForm.includeSecondLevelMonitorTypes,document.AMActionForm.toAddg),fnRemoveAllFromCombo(document.AMActionForm.includeSecondLevelMonitorTypes);");
/*      */     
/* 7060 */     _jspx_th_html_005fbutton_005f14.setValue("&lt;&lt;");
/* 7061 */     int _jspx_eval_html_005fbutton_005f14 = _jspx_th_html_005fbutton_005f14.doStartTag();
/* 7062 */     if (_jspx_th_html_005fbutton_005f14.doEndTag() == 5) {
/* 7063 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f14);
/* 7064 */       return true;
/*      */     }
/* 7066 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f14);
/* 7067 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f15(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7072 */     PageContext pageContext = _jspx_page_context;
/* 7073 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7075 */     ButtonTag _jspx_th_html_005fbutton_005f15 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 7076 */     _jspx_th_html_005fbutton_005f15.setPageContext(_jspx_page_context);
/* 7077 */     _jspx_th_html_005fbutton_005f15.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 7079 */     _jspx_th_html_005fbutton_005f15.setStyleClass("buttons btn_small");
/*      */     
/* 7081 */     _jspx_th_html_005fbutton_005f15.setProperty("AddButton2");
/*      */     
/* 7083 */     _jspx_th_html_005fbutton_005f15.setOnclick("javascript:fnAddToRightCombo(document.AMActionForm.includeSecondLevelMonitorTypes,document.AMActionForm.toAddg),fnRemoveFromRightCombo(document.AMActionForm.includeSecondLevelMonitorTypes);");
/*      */     
/* 7085 */     _jspx_th_html_005fbutton_005f15.setValue("&nbsp;&lt;&nbsp;");
/* 7086 */     int _jspx_eval_html_005fbutton_005f15 = _jspx_th_html_005fbutton_005f15.doStartTag();
/* 7087 */     if (_jspx_th_html_005fbutton_005f15.doEndTag() == 5) {
/* 7088 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f15);
/* 7089 */       return true;
/*      */     }
/* 7091 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f15);
/* 7092 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f7(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7097 */     PageContext pageContext = _jspx_page_context;
/* 7098 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7100 */     SelectTag _jspx_th_html_005fselect_005f7 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 7101 */     _jspx_th_html_005fselect_005f7.setPageContext(_jspx_page_context);
/* 7102 */     _jspx_th_html_005fselect_005f7.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 7104 */     _jspx_th_html_005fselect_005f7.setProperty("includeSecondLevelMonitorTypes");
/*      */     
/* 7106 */     _jspx_th_html_005fselect_005f7.setStyle("width:100%");
/*      */     
/* 7108 */     _jspx_th_html_005fselect_005f7.setMultiple("true");
/*      */     
/* 7110 */     _jspx_th_html_005fselect_005f7.setSize("5");
/* 7111 */     int _jspx_eval_html_005fselect_005f7 = _jspx_th_html_005fselect_005f7.doStartTag();
/* 7112 */     if (_jspx_eval_html_005fselect_005f7 != 0) {
/* 7113 */       if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 7114 */         out = _jspx_page_context.pushBody();
/* 7115 */         _jspx_th_html_005fselect_005f7.setBodyContent((BodyContent)out);
/* 7116 */         _jspx_th_html_005fselect_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7119 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 7120 */         if (_jspx_meth_html_005foptionsCollection_005f7(_jspx_th_html_005fselect_005f7, _jspx_page_context))
/* 7121 */           return true;
/* 7122 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 7123 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f7.doAfterBody();
/* 7124 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7127 */       if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 7128 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7131 */     if (_jspx_th_html_005fselect_005f7.doEndTag() == 5) {
/* 7132 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f7);
/* 7133 */       return true;
/*      */     }
/* 7135 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f7);
/* 7136 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f7(JspTag _jspx_th_html_005fselect_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7141 */     PageContext pageContext = _jspx_page_context;
/* 7142 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7144 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f7 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.get(OptionsCollectionTag.class);
/* 7145 */     _jspx_th_html_005foptionsCollection_005f7.setPageContext(_jspx_page_context);
/* 7146 */     _jspx_th_html_005foptionsCollection_005f7.setParent((Tag)_jspx_th_html_005fselect_005f7);
/*      */     
/* 7148 */     _jspx_th_html_005foptionsCollection_005f7.setProperty("includeSecondLevelMonitorTypesOptions");
/*      */     
/* 7150 */     _jspx_th_html_005foptionsCollection_005f7.setLabel("label");
/*      */     
/* 7152 */     _jspx_th_html_005foptionsCollection_005f7.setValue("value");
/* 7153 */     int _jspx_eval_html_005foptionsCollection_005f7 = _jspx_th_html_005foptionsCollection_005f7.doStartTag();
/* 7154 */     if (_jspx_th_html_005foptionsCollection_005f7.doEndTag() == 5) {
/* 7155 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f7);
/* 7156 */       return true;
/*      */     }
/* 7158 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f7);
/* 7159 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f60(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7164 */     PageContext pageContext = _jspx_page_context;
/* 7165 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7167 */     RadioTag _jspx_th_html_005fradio_005f60 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 7168 */     _jspx_th_html_005fradio_005f60.setPageContext(_jspx_page_context);
/* 7169 */     _jspx_th_html_005fradio_005f60.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 7171 */     _jspx_th_html_005fradio_005f60.setProperty("ciAttributesToBeSynced");
/*      */     
/* 7173 */     _jspx_th_html_005fradio_005f60.setValue("true");
/* 7174 */     int _jspx_eval_html_005fradio_005f60 = _jspx_th_html_005fradio_005f60.doStartTag();
/* 7175 */     if (_jspx_th_html_005fradio_005f60.doEndTag() == 5) {
/* 7176 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f60);
/* 7177 */       return true;
/*      */     }
/* 7179 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f60);
/* 7180 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f61(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7185 */     PageContext pageContext = _jspx_page_context;
/* 7186 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7188 */     RadioTag _jspx_th_html_005fradio_005f61 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 7189 */     _jspx_th_html_005fradio_005f61.setPageContext(_jspx_page_context);
/* 7190 */     _jspx_th_html_005fradio_005f61.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 7192 */     _jspx_th_html_005fradio_005f61.setProperty("ciAttributesToBeSynced");
/*      */     
/* 7194 */     _jspx_th_html_005fradio_005f61.setValue("false");
/* 7195 */     int _jspx_eval_html_005fradio_005f61 = _jspx_th_html_005fradio_005f61.doStartTag();
/* 7196 */     if (_jspx_th_html_005fradio_005f61.doEndTag() == 5) {
/* 7197 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f61);
/* 7198 */       return true;
/*      */     }
/* 7200 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f61);
/* 7201 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f62(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7206 */     PageContext pageContext = _jspx_page_context;
/* 7207 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7209 */     RadioTag _jspx_th_html_005fradio_005f62 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 7210 */     _jspx_th_html_005fradio_005f62.setPageContext(_jspx_page_context);
/* 7211 */     _jspx_th_html_005fradio_005f62.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 7213 */     _jspx_th_html_005fradio_005f62.setProperty("ciLinksToBeShown");
/*      */     
/* 7215 */     _jspx_th_html_005fradio_005f62.setValue("true");
/* 7216 */     int _jspx_eval_html_005fradio_005f62 = _jspx_th_html_005fradio_005f62.doStartTag();
/* 7217 */     if (_jspx_th_html_005fradio_005f62.doEndTag() == 5) {
/* 7218 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f62);
/* 7219 */       return true;
/*      */     }
/* 7221 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f62);
/* 7222 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f63(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7227 */     PageContext pageContext = _jspx_page_context;
/* 7228 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7230 */     RadioTag _jspx_th_html_005fradio_005f63 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 7231 */     _jspx_th_html_005fradio_005f63.setPageContext(_jspx_page_context);
/* 7232 */     _jspx_th_html_005fradio_005f63.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 7234 */     _jspx_th_html_005fradio_005f63.setProperty("ciLinksToBeShown");
/*      */     
/* 7236 */     _jspx_th_html_005fradio_005f63.setValue("false");
/* 7237 */     int _jspx_eval_html_005fradio_005f63 = _jspx_th_html_005fradio_005f63.doStartTag();
/* 7238 */     if (_jspx_th_html_005fradio_005f63.doEndTag() == 5) {
/* 7239 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f63);
/* 7240 */       return true;
/*      */     }
/* 7242 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f63);
/* 7243 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f64(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7248 */     PageContext pageContext = _jspx_page_context;
/* 7249 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7251 */     RadioTag _jspx_th_html_005fradio_005f64 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 7252 */     _jspx_th_html_005fradio_005f64.setPageContext(_jspx_page_context);
/* 7253 */     _jspx_th_html_005fradio_005f64.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 7255 */     _jspx_th_html_005fradio_005f64.setProperty("ciRlMapalongWithListView");
/*      */     
/* 7257 */     _jspx_th_html_005fradio_005f64.setValue("true");
/* 7258 */     int _jspx_eval_html_005fradio_005f64 = _jspx_th_html_005fradio_005f64.doStartTag();
/* 7259 */     if (_jspx_th_html_005fradio_005f64.doEndTag() == 5) {
/* 7260 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f64);
/* 7261 */       return true;
/*      */     }
/* 7263 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f64);
/* 7264 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f65(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7269 */     PageContext pageContext = _jspx_page_context;
/* 7270 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7272 */     RadioTag _jspx_th_html_005fradio_005f65 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 7273 */     _jspx_th_html_005fradio_005f65.setPageContext(_jspx_page_context);
/* 7274 */     _jspx_th_html_005fradio_005f65.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 7276 */     _jspx_th_html_005fradio_005f65.setProperty("ciRlMapalongWithListView");
/*      */     
/* 7278 */     _jspx_th_html_005fradio_005f65.setValue("false");
/* 7279 */     int _jspx_eval_html_005fradio_005f65 = _jspx_th_html_005fradio_005f65.doStartTag();
/* 7280 */     if (_jspx_th_html_005fradio_005f65.doEndTag() == 5) {
/* 7281 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f65);
/* 7282 */       return true;
/*      */     }
/* 7284 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f65);
/* 7285 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ServiceDesk_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */