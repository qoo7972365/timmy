/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
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
/*      */ import org.apache.struts.action.DynaActionForm;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.ButtonTag;
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.PasswordTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.html.TextareaTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class HostResourceConfig_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   47 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   50 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   51 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   52 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   59 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   64 */     ArrayList list = null;
/*   65 */     StringBuffer sbf = new StringBuffer();
/*   66 */     com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
/*   67 */     if (distinct)
/*      */     {
/*   69 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   73 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   76 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   78 */       ArrayList row = (ArrayList)list.get(i);
/*   79 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   80 */       if (distinct) {
/*   81 */         sbf.append(row.get(0));
/*      */       } else
/*   83 */         sbf.append(row.get(1));
/*   84 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   87 */     return sbf.toString(); }
/*      */   
/*   89 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   92 */     if (severity == null)
/*      */     {
/*   94 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*   96 */     if (severity.equals("5"))
/*      */     {
/*   98 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  100 */     if (severity.equals("1"))
/*      */     {
/*  102 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  107 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  114 */     if (severity == null)
/*      */     {
/*  116 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  118 */     if (severity.equals("1"))
/*      */     {
/*  120 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  122 */     if (severity.equals("4"))
/*      */     {
/*  124 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  126 */     if (severity.equals("5"))
/*      */     {
/*  128 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  133 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  139 */     if (severity == null)
/*      */     {
/*  141 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  143 */     if (severity.equals("5"))
/*      */     {
/*  145 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  147 */     if (severity.equals("1"))
/*      */     {
/*  149 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  153 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  159 */     if (severity == null)
/*      */     {
/*  161 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  163 */     if (severity.equals("1"))
/*      */     {
/*  165 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  167 */     if (severity.equals("4"))
/*      */     {
/*  169 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  171 */     if (severity.equals("5"))
/*      */     {
/*  173 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  177 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  183 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  189 */     if (severity == 5)
/*      */     {
/*  191 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  193 */     if (severity == 1)
/*      */     {
/*  195 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  200 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  206 */     if (severity == null)
/*      */     {
/*  208 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  210 */     if (severity.equals("5"))
/*      */     {
/*  212 */       if (isAvailability) {
/*  213 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  216 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  219 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  221 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  223 */     if (severity.equals("1"))
/*      */     {
/*  225 */       if (isAvailability) {
/*  226 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  229 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  236 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  243 */     if (severity == null)
/*      */     {
/*  245 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  247 */     if (severity.equals("5"))
/*      */     {
/*  249 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  251 */     if (severity.equals("4"))
/*      */     {
/*  253 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  255 */     if (severity.equals("1"))
/*      */     {
/*  257 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  262 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  268 */     if (severity == null)
/*      */     {
/*  270 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  272 */     if (severity.equals("5"))
/*      */     {
/*  274 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  276 */     if (severity.equals("4"))
/*      */     {
/*  278 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  280 */     if (severity.equals("1"))
/*      */     {
/*  282 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  287 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  294 */     if (severity == null)
/*      */     {
/*  296 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  298 */     if (severity.equals("5"))
/*      */     {
/*  300 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  302 */     if (severity.equals("4"))
/*      */     {
/*  304 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  306 */     if (severity.equals("1"))
/*      */     {
/*  308 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  313 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  321 */     StringBuffer out = new StringBuffer();
/*  322 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  323 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  324 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  325 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  326 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  327 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  328 */     out.append("</tr>");
/*  329 */     out.append("</form></table>");
/*  330 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  337 */     if (val == null)
/*      */     {
/*  339 */       return "-";
/*      */     }
/*      */     
/*  342 */     String ret = FormatUtil.formatNumber(val);
/*  343 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  344 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  347 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  351 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  359 */     StringBuffer out = new StringBuffer();
/*  360 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  361 */     out.append("<tr>");
/*  362 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  364 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  366 */     out.append("</tr>");
/*  367 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  371 */       if (j % 2 == 0)
/*      */       {
/*  373 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  377 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  380 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  382 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  385 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  389 */       out.append("</tr>");
/*      */     }
/*  391 */     out.append("</table>");
/*  392 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  393 */     out.append("<tr>");
/*  394 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  395 */     out.append("</tr>");
/*  396 */     out.append("</table>");
/*  397 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, java.util.Vector tableColumns)
/*      */   {
/*  403 */     StringBuffer out = new StringBuffer();
/*  404 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  405 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  406 */     out.append("<tr>");
/*  407 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  408 */     out.append("<tr>");
/*  409 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  410 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  411 */     out.append("</tr>");
/*  412 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  415 */       out.append("<tr>");
/*  416 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  417 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  418 */       out.append("</tr>");
/*      */     }
/*      */     
/*  421 */     out.append("</table>");
/*  422 */     out.append("</table>");
/*  423 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  428 */     if (severity.equals("0"))
/*      */     {
/*  430 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  434 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  441 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  454 */     StringBuffer out = new StringBuffer();
/*  455 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  456 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  458 */       out.append("<tr>");
/*  459 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  460 */       out.append("</tr>");
/*      */       
/*      */ 
/*  463 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  465 */         String borderclass = "";
/*      */         
/*      */ 
/*  468 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  470 */         out.append("<tr>");
/*      */         
/*  472 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  473 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  474 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  480 */     out.append("</table><br>");
/*  481 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  482 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  484 */       List sLinks = secondLevelOfLinks[0];
/*  485 */       List sText = secondLevelOfLinks[1];
/*  486 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  489 */         out.append("<tr>");
/*  490 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  491 */         out.append("</tr>");
/*  492 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  494 */           String borderclass = "";
/*      */           
/*      */ 
/*  497 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  499 */           out.append("<tr>");
/*      */           
/*  501 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  502 */           if (sLinks.get(i).toString().length() == 0) {
/*  503 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  506 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  508 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  512 */     out.append("</table>");
/*  513 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  520 */     StringBuffer out = new StringBuffer();
/*  521 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  522 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  524 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  526 */         out.append("<tr>");
/*  527 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  528 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  532 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  534 */           String borderclass = "";
/*      */           
/*      */ 
/*  537 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  539 */           out.append("<tr>");
/*      */           
/*  541 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  542 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  543 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  546 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  549 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  554 */     out.append("</table><br>");
/*  555 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  556 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  558 */       List sLinks = secondLevelOfLinks[0];
/*  559 */       List sText = secondLevelOfLinks[1];
/*  560 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  563 */         out.append("<tr>");
/*  564 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  565 */         out.append("</tr>");
/*  566 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  568 */           String borderclass = "";
/*      */           
/*      */ 
/*  571 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  573 */           out.append("<tr>");
/*      */           
/*  575 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  576 */           if (sLinks.get(i).toString().length() == 0) {
/*  577 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  580 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  582 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  586 */     out.append("</table>");
/*  587 */     return out.toString();
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
/*  600 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  603 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  606 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  609 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  612 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  615 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  618 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  621 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  629 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  634 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  639 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  644 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  649 */     if (val != null)
/*      */     {
/*  651 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  655 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  660 */     if (val == null) {
/*  661 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  665 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  670 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  676 */     if (val != null)
/*      */     {
/*  678 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  682 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  688 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  693 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  697 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  702 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  707 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  712 */     String hostaddress = "";
/*  713 */     String ip = request.getHeader("x-forwarded-for");
/*  714 */     if (ip == null)
/*  715 */       ip = request.getRemoteAddr();
/*  716 */     java.net.InetAddress add = null;
/*  717 */     if (ip.equals("127.0.0.1")) {
/*  718 */       add = java.net.InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  722 */       add = java.net.InetAddress.getByName(ip);
/*      */     }
/*  724 */     hostaddress = add.getHostName();
/*  725 */     if (hostaddress.indexOf('.') != -1) {
/*  726 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  727 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  731 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  736 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  742 */     if (severity == null)
/*      */     {
/*  744 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  746 */     if (severity.equals("5"))
/*      */     {
/*  748 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  750 */     if (severity.equals("1"))
/*      */     {
/*  752 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  757 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  762 */     ResultSet set = null;
/*  763 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  764 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  766 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  767 */       if (set.next()) { String str1;
/*  768 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  769 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  772 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  777 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  780 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  782 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  786 */     StringBuffer rca = new StringBuffer();
/*  787 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  788 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  791 */     int rcalength = key.length();
/*  792 */     String split = "6. ";
/*  793 */     int splitPresent = key.indexOf(split);
/*  794 */     String div1 = "";String div2 = "";
/*  795 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  797 */       if (rcalength > 180) {
/*  798 */         rca.append("<span class=\"rca-critical-text\">");
/*  799 */         getRCATrimmedText(key, rca);
/*  800 */         rca.append("</span>");
/*      */       } else {
/*  802 */         rca.append("<span class=\"rca-critical-text\">");
/*  803 */         rca.append(key);
/*  804 */         rca.append("</span>");
/*      */       }
/*  806 */       return rca.toString();
/*      */     }
/*  808 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  809 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  810 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  811 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  812 */     getRCATrimmedText(div1, rca);
/*  813 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  816 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  817 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  818 */     getRCATrimmedText(div2, rca);
/*  819 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  821 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  826 */     String[] st = msg.split("<br>");
/*  827 */     for (int i = 0; i < st.length; i++) {
/*  828 */       String s = st[i];
/*  829 */       if (s.length() > 180) {
/*  830 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  832 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  836 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  837 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  839 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  843 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  844 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  845 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  848 */       if (key == null) {
/*  849 */         return ret;
/*      */       }
/*      */       
/*  852 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  853 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  856 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  857 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  858 */       set = AMConnectionPool.executeQueryStmt(query);
/*  859 */       if (set.next())
/*      */       {
/*  861 */         String helpLink = set.getString("LINK");
/*  862 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  865 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  871 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  890 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  881 */         if (set != null) {
/*  882 */           AMConnectionPool.closeStatement(set);
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
/*  896 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  897 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  899 */       String entityStr = (String)keys.nextElement();
/*  900 */       String mmessage = temp.getProperty(entityStr);
/*  901 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  902 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  904 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  910 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  911 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  913 */       String entityStr = (String)keys.nextElement();
/*  914 */       String mmessage = temp.getProperty(entityStr);
/*  915 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  916 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  918 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  923 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  933 */     String des = new String();
/*  934 */     while (str.indexOf(find) != -1) {
/*  935 */       des = des + str.substring(0, str.indexOf(find));
/*  936 */       des = des + replace;
/*  937 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  939 */     des = des + str;
/*  940 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  947 */       if (alert == null)
/*      */       {
/*  949 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  951 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  953 */         return "&nbsp;";
/*      */       }
/*      */       
/*  956 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  958 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  961 */       int rcalength = test.length();
/*  962 */       if (rcalength < 300)
/*      */       {
/*  964 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  968 */       StringBuffer out = new StringBuffer();
/*  969 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  970 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  971 */       out.append("</div>");
/*  972 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  973 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  974 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  979 */       ex.printStackTrace();
/*      */     }
/*  981 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  987 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  992 */     ArrayList attribIDs = new ArrayList();
/*  993 */     ArrayList resIDs = new ArrayList();
/*  994 */     ArrayList entitylist = new ArrayList();
/*      */     
/*  996 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/*  998 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1000 */       String resourceid = "";
/* 1001 */       String resourceType = "";
/* 1002 */       if (type == 2) {
/* 1003 */         resourceid = (String)row.get(0);
/* 1004 */         resourceType = (String)row.get(3);
/*      */       }
/* 1006 */       else if (type == 3) {
/* 1007 */         resourceid = (String)row.get(0);
/* 1008 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1011 */         resourceid = (String)row.get(6);
/* 1012 */         resourceType = (String)row.get(7);
/*      */       }
/* 1014 */       resIDs.add(resourceid);
/* 1015 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1016 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1018 */       String healthentity = null;
/* 1019 */       String availentity = null;
/* 1020 */       if (healthid != null) {
/* 1021 */         healthentity = resourceid + "_" + healthid;
/* 1022 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1025 */       if (availid != null) {
/* 1026 */         availentity = resourceid + "_" + availid;
/* 1027 */         entitylist.add(availentity);
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
/* 1041 */     Properties alert = getStatus(entitylist);
/* 1042 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1047 */     int size = monitorList.size();
/*      */     
/* 1049 */     String[] severity = new String[size];
/*      */     
/* 1051 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1053 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1054 */       String resourceName1 = (String)row1.get(7);
/* 1055 */       String resourceid1 = (String)row1.get(6);
/* 1056 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1057 */       if (severity[j] == null)
/*      */       {
/* 1059 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1063 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1065 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1067 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1070 */         if (sev > 0) {
/* 1071 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1072 */           monitorList.set(k, monitorList.get(j));
/* 1073 */           monitorList.set(j, t);
/* 1074 */           String temp = severity[k];
/* 1075 */           severity[k] = severity[j];
/* 1076 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1082 */     int z = 0;
/* 1083 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1086 */       int i = 0;
/* 1087 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1090 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1094 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1098 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1100 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1103 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1107 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1110 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1111 */       String resourceName1 = (String)row1.get(7);
/* 1112 */       String resourceid1 = (String)row1.get(6);
/* 1113 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1114 */       if (hseverity[j] == null)
/*      */       {
/* 1116 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1121 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1123 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1126 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1129 */         if (hsev > 0) {
/* 1130 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1131 */           monitorList.set(k, monitorList.get(j));
/* 1132 */           monitorList.set(j, t);
/* 1133 */           String temp1 = hseverity[k];
/* 1134 */           hseverity[k] = hseverity[j];
/* 1135 */           hseverity[j] = temp1;
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
/* 1147 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1148 */     boolean forInventory = false;
/* 1149 */     String trdisplay = "none";
/* 1150 */     String plusstyle = "inline";
/* 1151 */     String minusstyle = "none";
/* 1152 */     String haidTopLevel = "";
/* 1153 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1155 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1157 */         haidTopLevel = request.getParameter("haid");
/* 1158 */         forInventory = true;
/* 1159 */         trdisplay = "table-row;";
/* 1160 */         plusstyle = "none";
/* 1161 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1168 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1171 */     ArrayList listtoreturn = new ArrayList();
/* 1172 */     StringBuffer toreturn = new StringBuffer();
/* 1173 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1174 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1175 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1177 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1179 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1180 */       String childresid = (String)singlerow.get(0);
/* 1181 */       String childresname = (String)singlerow.get(1);
/* 1182 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1183 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1184 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1185 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1186 */       String unmanagestatus = (String)singlerow.get(5);
/* 1187 */       String actionstatus = (String)singlerow.get(6);
/* 1188 */       String linkclass = "monitorgp-links";
/* 1189 */       String titleforres = childresname;
/* 1190 */       String titilechildresname = childresname;
/* 1191 */       String childimg = "/images/trcont.png";
/* 1192 */       String flag = "enable";
/* 1193 */       String dcstarted = (String)singlerow.get(8);
/* 1194 */       String configMonitor = "";
/* 1195 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1196 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1198 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1200 */       if (singlerow.get(7) != null)
/*      */       {
/* 1202 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1204 */       String haiGroupType = "0";
/* 1205 */       if ("HAI".equals(childtype))
/*      */       {
/* 1207 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1209 */       childimg = "/images/trend.png";
/* 1210 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1211 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1212 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1214 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1216 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1218 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1219 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1222 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1224 */         linkclass = "disabledtext";
/* 1225 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1227 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1228 */       String availmouseover = "";
/* 1229 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1231 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1233 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1234 */       String healthmouseover = "";
/* 1235 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1237 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1240 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1241 */       int spacing = 0;
/* 1242 */       if (level >= 1)
/*      */       {
/* 1244 */         spacing = 40 * level;
/*      */       }
/* 1246 */       if (childtype.equals("HAI"))
/*      */       {
/* 1248 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1249 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1250 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1252 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1253 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1254 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1255 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1256 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1257 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1258 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1259 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1260 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1261 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1262 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1264 */         if (!forInventory)
/*      */         {
/* 1266 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1269 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1271 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1273 */           actions = editlink + actions;
/*      */         }
/* 1275 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1277 */           actions = actions + associatelink;
/*      */         }
/* 1279 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1280 */         String arrowimg = "";
/* 1281 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1283 */           actions = "";
/* 1284 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1285 */           checkbox = "";
/* 1286 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1288 */         if (isIt360)
/*      */         {
/* 1290 */           actionimg = "";
/* 1291 */           actions = "";
/* 1292 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1293 */           checkbox = "";
/*      */         }
/*      */         
/* 1296 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1298 */           actions = "";
/*      */         }
/* 1300 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1302 */           checkbox = "";
/*      */         }
/*      */         
/* 1305 */         String resourcelink = "";
/*      */         
/* 1307 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1309 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1313 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1316 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1317 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1318 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1319 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1320 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1321 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1322 */         if (!isIt360)
/*      */         {
/* 1324 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1328 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1331 */         toreturn.append("</tr>");
/* 1332 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1334 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1335 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1339 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1340 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1343 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1347 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1349 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1350 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1351 */             toreturn.append(assocMessage);
/* 1352 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1353 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1354 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1355 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1361 */         String resourcelink = null;
/* 1362 */         boolean hideEditLink = false;
/* 1363 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1365 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1366 */           hideEditLink = true;
/* 1367 */           if (isIt360)
/*      */           {
/* 1369 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1373 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1375 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1377 */           hideEditLink = true;
/* 1378 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/* 1379 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1384 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1387 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1388 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1389 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1390 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1391 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1392 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1393 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1394 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1395 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1396 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1397 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1398 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1399 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1401 */         if (hideEditLink)
/*      */         {
/* 1403 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1405 */         if (!forInventory)
/*      */         {
/* 1407 */           removefromgroup = "";
/*      */         }
/* 1409 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1410 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1411 */           actions = actions + configcustomfields;
/*      */         }
/* 1413 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1415 */           actions = editlink + actions;
/*      */         }
/* 1417 */         String managedLink = "";
/* 1418 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1420 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1421 */           actions = "";
/* 1422 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1423 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1426 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1428 */           checkbox = "";
/*      */         }
/*      */         
/* 1431 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1433 */           actions = "";
/*      */         }
/* 1435 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1436 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1437 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1438 */         if (isIt360)
/*      */         {
/* 1440 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1444 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1446 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1447 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1448 */         if (!isIt360)
/*      */         {
/* 1450 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1454 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1456 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1459 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1466 */       StringBuilder toreturn = new StringBuilder();
/* 1467 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1468 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1469 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1470 */       String title = "";
/* 1471 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
/* 1472 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1473 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1474 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1476 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1478 */       else if ("5".equals(severity))
/*      */       {
/* 1480 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1484 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1486 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1487 */       toreturn.append(v);
/*      */       
/* 1489 */       toreturn.append(link);
/* 1490 */       if (severity == null)
/*      */       {
/* 1492 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1494 */       else if (severity.equals("5"))
/*      */       {
/* 1496 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1498 */       else if (severity.equals("4"))
/*      */       {
/* 1500 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1502 */       else if (severity.equals("1"))
/*      */       {
/* 1504 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1509 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1511 */       toreturn.append("</a>");
/* 1512 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1516 */       ex.printStackTrace();
/*      */     }
/* 1518 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1525 */       StringBuilder toreturn = new StringBuilder();
/* 1526 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1527 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1528 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1529 */       if (message == null)
/*      */       {
/* 1531 */         message = "";
/*      */       }
/*      */       
/* 1534 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1535 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1537 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1538 */       toreturn.append(v);
/*      */       
/* 1540 */       toreturn.append(link);
/*      */       
/* 1542 */       if (severity == null)
/*      */       {
/* 1544 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1546 */       else if (severity.equals("5"))
/*      */       {
/* 1548 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1550 */       else if (severity.equals("1"))
/*      */       {
/* 1552 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1557 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1559 */       toreturn.append("</a>");
/* 1560 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1566 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1569 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1570 */     if (invokeActions != null) {
/* 1571 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1572 */       while (iterator.hasNext()) {
/* 1573 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1574 */         if (actionmap.containsKey(actionid)) {
/* 1575 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1580 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1584 */     String actionLink = "";
/* 1585 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1586 */     String query = "";
/* 1587 */     ResultSet rs = null;
/* 1588 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1589 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1590 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1591 */       actionLink = "method=" + methodName;
/*      */     }
/* 1593 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1594 */       actionLink = methodName;
/*      */     }
/* 1596 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1597 */     Iterator itr = methodarglist.iterator();
/* 1598 */     boolean isfirstparam = true;
/* 1599 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1600 */     while (itr.hasNext()) {
/* 1601 */       HashMap argmap = (HashMap)itr.next();
/* 1602 */       String argtype = (String)argmap.get("TYPE");
/* 1603 */       String argname = (String)argmap.get("IDENTITY");
/* 1604 */       String paramname = (String)argmap.get("PARAMETER");
/* 1605 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1606 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1607 */         isfirstparam = false;
/* 1608 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1610 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1614 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1618 */         actionLink = actionLink + "&";
/*      */       }
/* 1620 */       String paramValue = null;
/* 1621 */       String tempargname = argname;
/* 1622 */       if (commonValues.getProperty(tempargname) != null) {
/* 1623 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1626 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1627 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1628 */           if (dbType.equals("mysql")) {
/* 1629 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1632 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1634 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1636 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1637 */             if (rs.next()) {
/* 1638 */               paramValue = rs.getString("VALUE");
/* 1639 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1643 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1647 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1650 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1655 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1656 */           paramValue = rowId;
/*      */         }
/* 1658 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1659 */           paramValue = managedObjectName;
/*      */         }
/* 1661 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1662 */           paramValue = resID;
/*      */         }
/* 1664 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1665 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1668 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1670 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1671 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1672 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1674 */     return actionLink;
/*      */   }
/*      */   
/* 1677 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1678 */     String dependentAttribute = null;
/* 1679 */     String align = "left";
/*      */     
/* 1681 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1682 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1683 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1684 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1685 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1686 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1687 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1688 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1689 */       align = "center";
/*      */     }
/*      */     
/* 1692 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1693 */     String actualdata = "";
/*      */     
/* 1695 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1696 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1697 */         actualdata = availValue;
/*      */       }
/* 1699 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1700 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1704 */           String attributeName = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1705 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1708 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1714 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1715 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1716 */       toreturn.append("<table>");
/* 1717 */       toreturn.append("<tr>");
/* 1718 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1719 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1720 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1721 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1722 */         String toolTip = "";
/* 1723 */         String hideClass = "";
/* 1724 */         String textStyle = "";
/* 1725 */         boolean isreferenced = true;
/* 1726 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1727 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1728 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1729 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1731 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1732 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1733 */           while (valueList.hasMoreTokens()) {
/* 1734 */             String dependentVal = valueList.nextToken();
/* 1735 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1736 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1737 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1739 */               toolTip = "";
/* 1740 */               hideClass = "";
/* 1741 */               isreferenced = false;
/* 1742 */               textStyle = "disabledtext";
/* 1743 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1747 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1748 */           toolTip = "";
/* 1749 */           hideClass = "";
/* 1750 */           isreferenced = false;
/* 1751 */           textStyle = "disabledtext";
/* 1752 */           if (dependentImageMap != null) {
/* 1753 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1754 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1757 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1761 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1762 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1763 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1764 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1765 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1766 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1768 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1769 */           if (isreferenced) {
/* 1770 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1774 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1775 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1776 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1777 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1778 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1779 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1781 */           toreturn.append("</span>");
/* 1782 */           toreturn.append("</a>");
/* 1783 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1786 */       toreturn.append("</tr>");
/* 1787 */       toreturn.append("</table>");
/* 1788 */       toreturn.append("</td>");
/*      */     } else {
/* 1790 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1793 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1797 */     String colTime = null;
/* 1798 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1799 */     if ((rows != null) && (rows.size() > 0)) {
/* 1800 */       Iterator<String> itr = rows.iterator();
/* 1801 */       String maxColQuery = "";
/* 1802 */       for (;;) { if (itr.hasNext()) {
/* 1803 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1804 */           ResultSet maxCol = null;
/*      */           try {
/* 1806 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1807 */             while (maxCol.next()) {
/* 1808 */               if (colTime == null) {
/* 1809 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1812 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1821 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1823 */               if (maxCol != null)
/* 1824 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1826 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1821 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1823 */               if (maxCol != null)
/* 1824 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1826 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1831 */     return colTime;
/*      */   }
/*      */   
/* 1834 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1835 */     tablename = null;
/* 1836 */     ResultSet rsTable = null;
/* 1837 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1839 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1840 */       while (rsTable.next()) {
/* 1841 */         tablename = rsTable.getString("DATATABLE");
/* 1842 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1843 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1856 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1847 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1850 */         if (rsTable != null)
/* 1851 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1853 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1859 */     String argsList = "";
/* 1860 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1862 */       if (showArgsMap.get(row) != null) {
/* 1863 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1864 */         if (showArgslist != null) {
/* 1865 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1866 */             if (argsList.trim().equals("")) {
/* 1867 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1870 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1877 */       e.printStackTrace();
/* 1878 */       return "";
/*      */     }
/* 1880 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1885 */     String argsList = "";
/* 1886 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1889 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1891 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1892 */         if (hideArgsList != null)
/*      */         {
/* 1894 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1896 */             if (argsList.trim().equals(""))
/*      */             {
/* 1898 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1902 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1910 */       ex.printStackTrace();
/*      */     }
/* 1912 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1916 */     StringBuilder toreturn = new StringBuilder();
/* 1917 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1924 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1925 */       Iterator itr = tActionList.iterator();
/* 1926 */       while (itr.hasNext()) {
/* 1927 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1928 */         String confirmmsg = "";
/* 1929 */         String link = "";
/* 1930 */         String isJSP = "NO";
/* 1931 */         HashMap tactionMap = (HashMap)itr.next();
/* 1932 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1933 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1934 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1935 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1936 */           (actionmap.containsKey(actionId))) {
/* 1937 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1938 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1939 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1940 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1941 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1943 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1949 */           if (isTableAction) {
/* 1950 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1953 */             tableName = "Link";
/* 1954 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1955 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1956 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1957 */             toreturn.append("</a></td>");
/*      */           }
/* 1959 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1960 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1961 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1962 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1968 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1974 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1976 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1977 */       Properties prop = (Properties)node.getUserObject();
/* 1978 */       String mgID = prop.getProperty("label");
/* 1979 */       String mgName = prop.getProperty("value");
/* 1980 */       String isParent = prop.getProperty("isParent");
/* 1981 */       int mgIDint = Integer.parseInt(mgID);
/* 1982 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
/*      */       {
/* 1984 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1986 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1987 */       if (node.getChildCount() > 0)
/*      */       {
/* 1989 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1991 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1993 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1995 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 1999 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2004 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2006 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2008 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2010 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2014 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2017 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2018 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2020 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2024 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2026 */       if (node.getChildCount() > 0)
/*      */       {
/* 2028 */         builder.append("<UL>");
/* 2029 */         printMGTree(node, builder);
/* 2030 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2035 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2036 */     StringBuffer toReturn = new StringBuffer();
/* 2037 */     String table = "-";
/*      */     try {
/* 2039 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2040 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2041 */       float total = 0.0F;
/* 2042 */       while (it.hasNext()) {
/* 2043 */         String attName = (String)it.next();
/* 2044 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2045 */         boolean roundOffData = false;
/* 2046 */         if ((data != null) && (!data.equals(""))) {
/* 2047 */           if (data.indexOf(",") != -1) {
/* 2048 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2051 */             float value = Float.parseFloat(data);
/* 2052 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2055 */             total += value;
/* 2056 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2059 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2064 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2065 */       while (attVsWidthList.hasNext()) {
/* 2066 */         String attName = (String)attVsWidthList.next();
/* 2067 */         String data = (String)attVsWidthProps.get(attName);
/* 2068 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2069 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2070 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2071 */         String className = (String)graphDetails.get("ClassName");
/* 2072 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2073 */         if (percentage < 1.0F)
/*      */         {
/* 2075 */           data = percentage + "";
/*      */         }
/* 2077 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2079 */       if (toReturn.length() > 0) {
/* 2080 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2084 */       e.printStackTrace();
/*      */     }
/* 2086 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2092 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2093 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2094 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2095 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2096 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2097 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2098 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2099 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2100 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2103 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2104 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2105 */       splitvalues[0] = multiplecondition.toString();
/* 2106 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2109 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public java.util.Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2114 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2115 */     if (thresholdType != 3) {
/* 2116 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2117 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2118 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2119 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2120 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2121 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2123 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2124 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2125 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2126 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2127 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2128 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2130 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2131 */     if (updateSelected != null) {
/* 2132 */       updateSelected[0] = "selected";
/*      */     }
/* 2134 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2139 */       StringBuffer toreturn = new StringBuffer("");
/* 2140 */       if (commaSeparatedMsgId != null) {
/* 2141 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2142 */         int count = 0;
/* 2143 */         while (msgids.hasMoreTokens()) {
/* 2144 */           String id = msgids.nextToken();
/* 2145 */           String message = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2146 */           String image = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2147 */           count++;
/* 2148 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2149 */             if (toreturn.length() == 0) {
/* 2150 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2152 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2153 */             if (!image.trim().equals("")) {
/* 2154 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2156 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2157 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2160 */         if (toreturn.length() > 0) {
/* 2161 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2165 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2168 */       e.printStackTrace(); }
/* 2169 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2176 */   String haid = null;
/* 2177 */   String appname = null;
/* 2178 */   String network = null;
/*      */   
/* 2180 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = javax.servlet.jsp.JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2186 */   private static java.util.Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2187 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fdisabled_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fdisabled_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/* 2217 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2221 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fdisabled_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fdisabled_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2244 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2248 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2249 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2250 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2251 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/* 2252 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2253 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/* 2254 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2255 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/* 2256 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2257 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.release();
/* 2258 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2259 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2260 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2261 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2262 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.release();
/* 2263 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fdisabled_005fnobody.release();
/* 2264 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2265 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fnobody.release();
/* 2266 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2267 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.release();
/* 2268 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2269 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fdisabled_005fnobody.release();
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
/* 2297 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2298 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2300 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2301 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2302 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2304 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2306 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2308 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2310 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2311 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2312 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2313 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2316 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2317 */         String available = null;
/* 2318 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2319 */         out.write(10);
/*      */         
/* 2321 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2322 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2323 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2325 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2327 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2329 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2331 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2332 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2333 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2334 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2337 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2338 */           String unavailable = null;
/* 2339 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2340 */           out.write(10);
/*      */           
/* 2342 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2343 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2344 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2346 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2348 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2350 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2352 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2353 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2354 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2355 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2358 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2359 */             String unmanaged = null;
/* 2360 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2361 */             out.write(10);
/*      */             
/* 2363 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2364 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2365 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2367 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2369 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2371 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2373 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2374 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2375 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2376 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2379 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2380 */               String scheduled = null;
/* 2381 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2382 */               out.write(10);
/*      */               
/* 2384 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2385 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2386 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2388 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2390 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2392 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2394 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2395 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2396 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2397 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2400 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2401 */                 String critical = null;
/* 2402 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2403 */                 out.write(10);
/*      */                 
/* 2405 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2406 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2407 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2409 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2411 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2413 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2415 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2416 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2417 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2418 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2421 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2422 */                   String clear = null;
/* 2423 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2424 */                   out.write(10);
/*      */                   
/* 2426 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2427 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2428 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2430 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2432 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2434 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2436 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2437 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2438 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2439 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2442 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2443 */                     String warning = null;
/* 2444 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2445 */                     out.write(10);
/* 2446 */                     out.write(10);
/*      */                     
/* 2448 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2449 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2451 */                     out.write(10);
/* 2452 */                     out.write(10);
/* 2453 */                     out.write(10);
/* 2454 */                     out.write("\n\n\n<html>\n<body marginheight=0 marginwidth=0 leftmargin=0 topmargin=0>\n<script>\n\nfunction snmpVersionSelect(value)\n{\n    $(\"#testCredentialResult\").hide(\"fast\"); //No I18n\n    if(value == 'v1v2')\n    {\n        $(\"#snmpV1V2\").show(\"slow\"); //No I18n\n        $(\"#snmpV3\").hide(\"fast\"); //No I18n\n        //$(\"#snmpV3\").fadeOut(\"fast\");\n        //$(\"#snmpV1V2\").fadeIn(\"fast\");\n        //document.getElementById('snmpV1V2').style.display=\"block\";\n        //document.getElementById('snmpV3').style.display=\"none\";\n    }\n    else\n    {\n        $(\"#snmpV1V2\").hide(\"fast\"); //No I18n\n        $(\"#snmpV3\").show(\"slow\"); //No I18n\n\tshowSecurityLevelProps();\n        //$(\"#snmpV1V2\").fadeOut(\"fast\");\n        //$(\"#snmpV3\").fadeIn(\"fast\");\n        //document.getElementById('snmpV1V2').style.display=\"none\";\n        //document.getElementById('snmpV3').style.display=\"block\";\n    }\n}\nfunction validateAndPerformTestCredential(value)\n{\n\n   \n    if(!isPositiveInteger(document.forms[1].port.value))\n    {\n        alert('");
/* 2455 */                     out.print(FormatUtil.getString("am.webclient.testCredential.positiveNumbers"));
/* 2456 */                     out.write("');\n        return false;\n    }\n    testCredential(value);\n}\n\nfunction closeMessage(idToClose)\n{\n    $(\"#\"+trimAll(idToClose)).hide(\"slow\"); //No I18n\n}\n\nfunction testCredential(value)\n{    \n    cacheid = (new Date()).getTime();\n    deviceToCheck = trimAll(\"");
/* 2457 */                     out.print((String)request.getAttribute("name"));
/* 2458 */                     out.write("\");\n    snmpPort = document.forms[1].port.value;\n    if(value == 'v1v2')\n    {\n        snmpCommunity = document.forms[1].snmpCommunityString.value;\n        snmpVersion = \"v1v2\"; //No I18n\n        dataString=\"&method=testSnmpCredential&snmpVersion=\"+value+\"&deviceToCheck=\"+deviceToCheck+\"&snmpCommunity=\"+snmpCommunity+\"&snmpPort=\"+snmpPort+\"&cacheid=\"+cacheid; //No I18n\n    }\n    if(value == 'v3')\n    {\n        snmpSecurityLevel = document.forms[1].snmpSecurityLevel.value;\n        snmpUserName = document.forms[1].snmpUserName.value;\n        snmpContextName = document.forms[1].snmpContextName.value;\n        snmpAuthProtocol = document.forms[1].snmpAuthProtocol.value;\n        snmpAuthPassword = document.forms[1].snmpAuthPassword.value;\n        snmpPrivPassword = document.forms[1].snmpPrivPassword.value;\n        dataString=\"&method=testSnmpCredential&snmpVersion=\"+value+\"&deviceToCheck=\"+deviceToCheck+\"&snmpSecurityLevel=\"+snmpSecurityLevel+\"&snmpPort=\"+snmpPort+\"&snmpUserName=\"+snmpUserName+\"&snmpContextName=\"+snmpContextName+\"&snmpAuthProtocol=\"+snmpAuthProtocol+\"&snmpAuthPassword=\"+snmpAuthPassword+\"&snmpPrivPassword=\"+snmpPrivPassword+\"&cacheid=\"+cacheid; //No I18n\n");
/* 2459 */                     out.write("    }\n        $(\"#testCredentialResult\").show(\"fast\"); //No I18n\n        //$(\"#testCredentialResult\").addClass(\"bodytext\");\n        $(\"#testCredentialResult\").css(\"color\",\"blue\"); //No I18n\n        $(\"#testCredentialResult\").html(\"<font size=2>Please wait ....</font>\") //No I18n\n        //$(\"#testCredentialResult\").append(\"<img src=\\\"/images/icon_cogwheel.gif\\\"/>\");\n        $(\"#testCredentialResult\").append(\"<img src=\\\"/images/LoadingTC.gif\\\"/>\"); //No I18n\n        $.ajax({\n        type: \"POST\", //No I18n\n        url: \"/testCredential.do\", // Action URL //No I18n\n        data: dataString,                                                        // Query String parameters\n        success: function(response)\n        {\n                               $(\"#testCredentialResult\").html(response);        // Set response into particular div ID .. //No I18n\n                              //callbackMethodCalling();\n        }\n});\n\n}\n\nfunction myOnLoad()\n{//this method - myOnLoad - gets called from BasicLayout.jsp\n\n\t");
/* 2460 */                     if ((com.adventnet.appmanager.util.OEMUtil.isOEM()) && (com.adventnet.appmanager.util.OEMUtil.isRemove("am.wmimonitors.remove"))) {
/* 2461 */                       out.write("\n            javascript:hideRow('eventlogstatus');\n\n       ");
/*      */                     }
/* 2463 */                     out.write(10);
/* 2464 */                     out.write(9);
/* 2465 */                     out.write(9);
/*      */                     
/* 2467 */                     if ((request.getParameter("processid") == null) && (request.getParameter("actiononServices") == null))
/*      */                     {
/*      */ 
/* 2470 */                       out.write("\n\t\tchangemode1();\n\t\t");
/*      */                     }
/*      */                     
/* 2473 */                     DynaActionForm form3 = (DynaActionForm)request.getAttribute("HostResourceForm");
/*      */                     
/* 2475 */                     if (((form3.get("mode") != null) && (((String)form3.get("mode")).indexOf("TELNET") != -1)) || (((String)form3.get("mode")).indexOf("SSH") != -1) || (((String)form3.get("mode")).indexOf("WMI") != -1))
/*      */                     {
/*      */ 
/* 2478 */                       out.write("\n\n\t\t//\tdocument.getElementById(\"username\").style.display = \"block\";\n\t\t//  document.getElementById(\"password\").style.display = \"block\";\n\t\t");
/*      */                     }
/*      */                     
/* 2481 */                     if ((form3.get("mode") != null) && (((String)form3.get("mode")).indexOf("SNMP") != -1))
/*      */                     {
/*      */ 
/* 2484 */                       out.write("\n                snmpVersionSelect(\"");
/* 2485 */                       out.print((String)request.getAttribute("snmpVersionValue"));
/* 2486 */                       out.write("\");\n               // alert('");
/* 2487 */                       out.print((String)request.getAttribute("port"));
/* 2488 */                       out.write("');\n                //$('input[name=\"port\"]').val('");
/* 2489 */                       out.print((String)request.getAttribute("port"));
/* 2490 */                       out.write("');\n\t\t//document.getElementById(\"snmpcommunity\").style.display = \"block\";\n\t\t");
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/* 2495 */                     out.write(10);
/* 2496 */                     out.write(9);
/* 2497 */                     out.write(9);
/* 2498 */                     if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2500 */                     out.write("\n\t\t//if((request.getParameter(\"actiononServices\")!=null))\n\t\t//{\n}\n\nfunction changemode1()\n{\n\n\n");
/*      */                     
/* 2502 */                     NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2503 */                     _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2504 */                     _jspx_th_logic_005fnotPresent_005f1.setParent(null);
/*      */                     
/* 2506 */                     _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO,OPERATOR");
/* 2507 */                     int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2508 */                     if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                       for (;;) {
/* 2510 */                         out.write("\n\tvar mode1=document.forms[1].os[document.forms[1].os.selectedIndex].value;\n\tvar mode2=document.forms[1].mode[document.forms[1].mode.selectedIndex].value;\n\tdocument.forms[1].mode.length=0;\n\tif(mode1 == 'Windows 2000' || mode1 == 'WindowsNT' || mode1 == 'Windows 2003' || mode1 == 'Windows XP' || mode1 == 'Windows Vista'|| mode1 == 'Windows 7' || mode1 == 'Windows 2008' || mode1 == 'Windows 8' || mode1 == 'Windows 10' || mode1 == 'Windows 2012')\n\t{\n\t\tvar option0 = new Option(\"SNMP\",\"SNMP\");\n\t\tdocument.forms[1].mode[0]=option0;\n\t\tif(mode2 == 'SNMP')\n\t\t{\n\t\tdocument.forms[1].mode[0].selected=true;\n\t\t//hideDiv(\"authinfo\");\n\t\tjavascript:hideRow('username');\n\t\tjavascript:hideRow('password');\n\t\t\thideDiv(\"prompt1\");\n\t\t\t//showDiv(\"snmpcommunity\");\n                        $(\"#snmpTableView\").show(\"slow\"); //No I18n                        \n\t\t\tshowDiv(\"port\");\n\t\t}\n\t\tif(document.forms[1].hostos.value == 'Windows')\n\t\t{\n\t\t\tvar option1 = new Option(\"WMI\",\"WMI\");\n\t\t\tdocument.forms[1].mode[1]=option1;\n\t\t\tif(mode2 == 'WMI')\n\t\t\t{\n\t\t\tdocument.forms[1].mode[1].selected=true;\n");
/* 2511 */                         out.write("\t\t\t//showDiv(\"authinfo\");\n\t\t\tjavascript:showRow('username');\n\t\t\tjavascript:showRow('password');\n\n\t\t\t//hideDiv(\"snmpcommunity\");\n                        $(\"#snmpTableView\").hide(\"fast\"); //No I18n\n\t\t\thideDiv(\"port\");\n\t\t\thideDiv(\"prompt1\");\n\t\t\t}\n\t\t}\n\t}\n\telse if(mode1 == 'Linux' || mode1 == 'SUN' || mode1 == 'FreeBSD' || mode1 == 'Mac OS')\n\t{\n\t\tvar option1 = new Option(\"Telnet\",\"TELNET\");\n\t\tdocument.forms[1].mode[0]=option1;\n\t\tvar option0 = new Option(\"SNMP\",\"SNMP\");\n\t\tdocument.forms[1].mode[1]=option0;\n\t\tvar option2 = new Option(\"SSH\",\"SSH\");\n\t\tdocument.forms[1].mode[2]=option2;\n\t\tif(mode2 == 'SNMP')\n\t\t{\n\t\tdocument.forms[1].mode[1].selected=true;\n\t\t//hideDiv(\"authinfo\");\n\t\tjavascript:hideRow('username');\n\t\tjavascript:hideRow('password');\n\n\t\thideDiv(\"prompt1\");\n\t\t//showDiv(\"snmpcommunity\");\n                $(\"#snmpTableView\").show(\"slow\"); //No I18n\n\t\t\tshowDiv(\"port\");\n\t\t}\n\t\tif(mode2 == 'TELNET')\n\t\t{\n\t\tdocument.forms[1].mode[0].selected=true;\n\t\t//showDiv(\"authinfo\");\n\t\tjavascript:showRow('username');\n\t\tjavascript:showRow('password');\n");
/* 2512 */                         out.write("\n\t\tshowDiv(\"prompt1\");\n\t\t//hideDiv(\"snmpcommunity\");\n                $(\"#snmpTableView\").hide(\"fast\"); //No I18n\n\t\t\tshowDiv(\"port\");\n\t\t}\n\t\tif(mode2 == 'SSH')\n\t\t{\n\t\tdocument.forms[1].mode[2].selected=true;\n\t\t//showDiv(\"authinfo\");\n\t\tjavascript:showRow('username');\n\t\tjavascript:showRow('password');\n\n\t\tshowDiv(\"prompt1\");\n\t\t//hideDiv(\"snmpcommunity\");\n                $(\"#snmpTableView\").hide(\"fast\"); //No I18n\n\t\t\tshowDiv(\"port\");\n\t\t}\n\t}\n\telse if (mode1 == 'AIX' || mode1 == 'HP-UX' || mode1 == 'HP-TRU64')\n\t{\n\t\tvar option1 = new Option(\"Telnet\",\"TELNET\");\n\t\tvar option2 = new Option(\"SSH\",\"SSH\");\n\t\tdocument.forms[1].mode[0]=option1;\n\t\tdocument.forms[1].mode[1]=option2;\n\t\tif(mode2 == 'TELNET')\n\t\t{\n\t\t\tdocument.forms[1].mode[0].selected=true;\n\t\t\t//showDiv(\"authinfo\");\n\t\t\tjavascript:showRow('username');\n\t\t\tjavascript:showRow('password');\n\n\t\t\t//hideDiv(\"snmpcommunity\");\n                        $(\"#snmpTableView\").hide(\"fast\"); //No I18n\n\t\t\tshowDiv(\"port\");\n\t\t\tshowDiv(\"prompt1\");\n\t\t}\n\t\tif(mode2 == 'SSH')\n\t\t{\n\t\t\tdocument.forms[1].mode[1].selected=true;\n");
/* 2513 */                         out.write("\t\t\t//showDiv(\"authinfo\");\n\t\t\tjavascript:showRow('username');\n\t\t\tjavascript:showRow('password');\n\n\t\t\t//hideDiv(\"snmpcommunity\");\n                        $(\"#snmpTableView\").hide(\"fast\"); //No I18n\n\t\t\tshowDiv(\"port\");\n\t\t\tshowDiv(\"prompt1\");\n\t\t}\n\t}\n\telse\n\t{\n\t\tvar option0 = new Option(\"SNMP\",\"SNMP\");\n\t\tdocument.forms[1].mode[0]=option0;                \n                port = ");
/* 2514 */                         out.print((String)request.getAttribute("port"));
/* 2515 */                         out.write(";\n\t\tdocument.forms[1].port.value=port;\n\t\tif(mode2 == 'SNMP')\n\t\t{\n\t\tdocument.forms[1].mode[0].selected=true;\n\t\t}\n\t\t//hideDiv(\"authinfo\");\n\t\tjavascript:hideRow('username');\n\t\tjavascript:hideRow('password');\n\n\t\thideDiv(\"prompt1\");\n\t\t//showDiv(\"snmpcommunity\");\n                $(\"#snmpTableView\").show(\"slow\"); //No I18n\n\t\t\tshowDiv(\"port\");\n\t}\n\t");
/* 2516 */                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 2517 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2521 */                     if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 2522 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*      */                     }
/*      */                     else {
/* 2525 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 2526 */                       out.write("\n}\n\n\n\nfunction changemode()\n{\n\tjavascript:hideRow('eventlogstatus');\n\tvar mode1=document.forms[1].os[document.forms[1].os.selectedIndex].value;\n\tdocument.forms[1].mode.length=0;\n\tif(mode1 == 'Windows 2000' || mode1 == 'WindowsNT' || mode1 == 'Windows 2003' || mode1 == 'Windows XP' || mode1 == 'Windows Vista' || mode1 == 'Windows 7' || mode1 == 'Windows 2008' || mode1 == 'Windows 8'|| mode1 == 'Windows 10' || mode1 == 'Windows 2012')\n\t{\n\t\tvar option0 = new Option(\"SNMP\",\"SNMP\");\n\t\tdocument.forms[1].mode[0]=option0;\n\t//\tdocument.forms[1].port.value=\"161\";\n\t\tif(document.forms[1].hostos.value == 'Windows')\n\t\t{\n\t\tvar option1 = new Option(\"WMI\",\"WMI\");\n\t\tdocument.forms[1].mode[1]=option1;\n\t\t\t//hideDiv(\"authinfo\");\n\t\t\thideRow(\"username\");\n\t\t\thideRow(\"password\");\n\t\t\thideDiv(\"prompt1\");\n\t\t\t//showDiv(\"snmpcommunity\");\n                        $(\"#snmpTableView\").show(\"slow\"); //No I18n\n\t\t\tshowDiv(\"port\");\n\t\t}\n\t\telse\n\t\t{\n\t\t\t//hideDiv(\"authinfo\");\n\t\t\thideRow(\"username\");\n\t\t\thideRow(\"password\");\n\t\t\thideDiv(\"prompt1\");\n\t\t\t//showDiv(\"snmpcommunity\");\n");
/* 2527 */                       out.write("                        $(\"#snmpTableView\").show(\"slow\"); //No I18n\n\t\t\tshowDiv(\"port\");\n\t\t}\n\t}\n\telse if(mode1 == 'Linux' || mode1 == 'SUN' || mode1 == 'FreeBSD' || mode1 == 'Mac OS')\n\t{\n\t\tvar option1 = new Option(\"Telnet\",\"TELNET\");\n\t\tdocument.forms[1].mode[0]=option1;\n\t\t//document.forms[1].port.value=\"23\";\n\t\tvar option0 = new Option(\"SNMP\",\"SNMP\");\n\t\tdocument.forms[1].mode[1]=option0;\n\t\tvar option2 = new Option(\"SSH\",\"SSH\");\n\t\tdocument.forms[1].mode[2]=option2;\n\t\t\t//showDiv(\"authinfo\");\n\t\t\tshowRow(\"username\");\n\t\t\tshowRow(\"password\");\n\t\t\t//hideDiv(\"snmpcommunity\");\n                        $(\"#snmpTableView\").hide(\"fast\"); //No I18n\n\t\t\tshowDiv(\"port\");\n\t\t\tshowDiv(\"prompt1\");\n\t}\n\telse if(mode1 == 'AIX' || mode1 == 'HP-UX' || mode1 == 'HP-TRU64')\n\t{\n\t\tvar option1 = new Option(\"Telnet\",\"TELNET\");\n\t\tdocument.forms[1].mode[0]=option1;\n\t\t//document.forms[1].port.value=\"23\";\n\t\tvar option2 = new Option(\"SSH\",\"SSH\");\n\t\tdocument.forms[1].mode[1]=option2;\n\t\t//showDiv(\"authinfo\");\n\t\t showRow(\"username\");\n\t\t showRow(\"password\");\n");
/* 2528 */                       out.write("\t\t //hideDiv(\"snmpcommunity\");\n                 $(\"#snmpTableView\").hide(\"fast\"); //No I18n\n\t\t showDiv(\"port\");\n\t\t showDiv(\"prompt1\");\n\t}\n\telse\n\t{\n\t\tvar option0 = new Option(\"SNMP\",\"SNMP\");\n\t\tdocument.forms[1].mode[0]=option0;\n\t\tdocument.forms[1].port.value=(");
/* 2529 */                       out.print((String)request.getAttribute("port"));
/* 2530 */                       out.write(");//\"161\";\n\t\t\t//hideDiv(\"authinfo\");\n\t\t\thideRow(\"username\");\n\t\t\thideRow(\"password\");\n\t\t\thideDiv(\"prompt1\");\n\t\t\t//showDiv(\"snmpcommunity\");\n                        $(\"#snmpTableView\").show(\"slow\"); //No I18n\n\t\t\tshowDiv(\"port\");\n\t}\n}\n\nfunction showSecurityLevelProps()\n{\n    if($('select[name=snmpSecurityLevel]').val() == 'NOAUTHNOPRIV')\n        {\n            $(\"#AuthPrivID\").hide(\"fast\"); //No I18n\n            $(\"#AuthNoPrivID\").hide(\"fast\"); //No I18n\n        }\n        if($('select[name=snmpSecurityLevel]').val() == 'AUTHNOPRIV')\n        {\n            $(\"#AuthPrivID\").hide(\"fast\"); //No I18n\n            $(\"#AuthNoPrivID\").show(\"slow\"); //No I18n\n        }\n        if($('select[name=snmpSecurityLevel]').val() == 'AUTHPRIV')\n        {\n            $(\"#AuthPrivID\").show(\"slow\"); //No I18n\n            $(\"#AuthNoPrivID\").show(\"slow\"); //No I18n\n        }\n     //alert($('select[name=snmpSecurityLevel]').val());\n}\nfunction changeport()\n{\n\tvar check='false';\n\tvar adminRange=");
/* 2531 */                       out.print(com.adventnet.appmanager.util.EnterpriseUtil.RANGE);
/* 2532 */                       out.write(";\n// \talert(adminRange);\n\n\t");
/* 2533 */                       if (_jspx_meth_logic_005fnotPresent_005f2(_jspx_page_context))
/*      */                         return;
/* 2535 */                       out.write(10);
/* 2536 */                       out.write(10);
/* 2537 */                       out.write(9);
/* 2538 */                       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                         return;
/* 2540 */                       out.write("\n\n//  \talert(check);\nif(check)\n{\n\tvar mode=document.forms[1].mode[document.forms[1].mode.selectedIndex].value;        \n\tjavascript:hideRow('eventlogstatus');\n\tif(mode == 'SNMP')\n\t{\n\tdocument.forms[1].port.value=");
/* 2541 */                       out.print((String)request.getAttribute("port"));
/* 2542 */                       out.write(";//\"161\";\n\t//document.forms[1].community.value=\"public\";\n\t//hideDiv(\"authinfo\");\n\tjavascript:hideRow('username');\n\tjavascript:hideRow('password');\n    javascript:hideRow(\"sshKeyAuth\");\n    javascript:hideRow(\"privateKey\");\n\thideDiv(\"prompt1\");\n\t//showDiv(\"snmpcommunity\");\n         $(\"#snmpTableView\").show(\"slow\"); //No I18n\n\tshowDiv(\"port\");\n\tdocument.forms[1].timeout.value=5;\n\t}\n\tif(mode == 'TELNET')\n\t{\n\tdocument.forms[1].port.value=\"23\";\n\t//showDiv(\"authinfo\");\n\tjavascript:showRow('username');\n\tjavascript:showRow('password');\n\tjavascript:hideRow(\"sshKeyAuth\");\n    javascript:hideRow(\"privateKey\");\n\tjavascript:hideRow(\"passphrase\");//No I18N\n\tdocument.forms[1].timeout.value=40;\n\t//hideDiv(\"snmpcommunity\");\n         $(\"#snmpTableView\").hide(\"fast\"); //No I18n\n\tshowDiv(\"prompt1\");\n\t\t\tshowDiv(\"port\");\n\t}\n\telse if (mode == 'SSH')\n\t{\n\tdocument.forms[1].port.value=\"22\";\n\t//showDiv(\"authinfo\");\n\tjavascript:showRow('username');\n\tif ('");
/* 2543 */                       out.print(request.getAttribute("keyBasedAuth"));
/* 2544 */                       out.write("' == \"true\") {\n\t\tjavascript:hideRow('password');//No I18N\n\t\tjavascript:showRow(\"sshKeyAuth\");//No I18N\n\t\tjavascript:showRow(\"privateKey\");\t//No I18N\n\t\tjavascript:showRow(\"passphrase\");//No I18N\n\t\tvar obj = document.getElementById(\"sshPKAuth\");//No I18N\n\t\tif (obj != null && !obj.checked) {\n\t\t\tjavascript:showRow('password');//No I18N\n\t\t\tjavascript:hideRow(\"privateKey\");//No I18N\t\n\t\t\tjavascript:hideRow(\"passphrase\");//No I18N\t\t\t\t\n\t\t}\t\t\t\n\t} else {\t\t\n    javascript:showRow('password');\n\t\tjavascript:showRow(\"sshKeyAuth\");//No I18N\n\tjavascript:hideRow(\"privateKey\");\n\t\tjavascript:hideRow(\"passphrase\");//No I18N\n\t}\n\t//hideDiv(\"snmpcommunity\");\n        $(\"#snmpTableView\").hide(\"fast\"); //No I18n\n\tshowDiv(\"prompt1\");\n\t\t\tshowDiv(\"port\");\n\t\t\tdocument.forms[1].timeout.value=40;\n\t}\n\telse if (mode == 'WMI')\n\t{\n\t//hideDiv(\"snmpcommunity\");\n        $(\"#snmpTableView\").hide(\"fast\"); //No I18n\n//\tdocument.forms[1].port.value=\"161\";\n\t//showDiv(\"authinfo\");\n\tjavascript:showRow('username');\n    javascript:showRow('password');\n    javascript:hideRow(\"sshKeyAuth\");\n");
/* 2545 */                       out.write("    javascript:hideRow(\"privateKey\");\n\thideDiv(\"prompt1\");\n\t\t\thideDiv(\"port\");\n\t\t\t//document.forms[1].eventlog_status.checked=true;\n\t\t\tjavascript:showRow('eventlogstatus');\n\t\t\tdocument.forms[1].timeout.value=300;\n\t}\n\telse\n\t{\n\t//document.forms[1].port.value=\"161\";\n\t//showDiv(\"snmpcommunity\");\n        $(\"#snmpTableView\").show(\"slow\"); //No I18n\n\t//hideDiv(\"authinfo\");\n\tshowDiv(\"port\");\n\tdocument.forms[1].timeout.value=5;\n\t}\n}\n\n}\n\nfunction showPrivateKey()\n{\n if(document.forms[1].sshPKAuth.checked)\n {\n  javascript:hideRow(\"password\");\n  javascript:showRow(\"privateKey\");\n  javascript:showRow(\"passphrase\"); //NO I18N\n }\n else\n {\n  javascript:showRow(\"password\");\n  javascript:hideRow(\"privateKey\");\n  javascript:hideRow(\"passphrase\"); //NO I18N\n }\n}\n\n\nfunction validateAndSubmit()\n{\n\n\t");
/* 2546 */                       if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */                         return;
/* 2548 */                       out.write(10);
/* 2549 */                       out.write(9);
/*      */                       
/* 2551 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2552 */                       _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 2553 */                       _jspx_th_logic_005fnotPresent_005f3.setParent(null);
/*      */                       
/* 2555 */                       _jspx_th_logic_005fnotPresent_005f3.setRole("DEMO");
/* 2556 */                       int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 2557 */                       if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                         for (;;) {
/* 2559 */                           out.write("\n\tif(trimAll(document.forms[1].os.value) == 'Node')\n\t{\n\t\talert(\"");
/* 2560 */                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.ostype"));
/* 2561 */                           out.write("\");\n\t\tdocument.forms[1].os.select();\n\t\treturn;\n\t}\n\tvar mode=document.forms[1].mode[document.forms[1].mode.selectedIndex].value;\n\tif(mode != 'SNMP' && trimAll(document.forms[1].username.value) == '')\n\t{                \n\t\talert(\"");
/* 2562 */                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.username"));
/* 2563 */                           out.write("\");\n\t\tdocument.forms[1].username.select();\n\t\treturn;\n\t}\n\tif(mode =='SSH')\n\t{\n\t\tif(document.forms[1].sshPKAuth.checked)\n\t\t{\n\t\t  var sshKey = trimAll(document.forms[1].description.value);\n\t\t  if(sshKey =='')\n\t\t  {\n\t\t\talert(\"");
/* 2564 */                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssh.privateKey.alert"));
/* 2565 */                           out.write("\");\n\t\t\tdocument.forms[1].description.select();\n\t\t\treturn;\n\t\t  }\n\t    }\n\t    else\n\t    {\n\t\t  var passWord = trimAll(document.forms[1].password.value);\n\t\t  if(passWord=='')\n\t\t  {\n\t\t\talert(\"");
/* 2566 */                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.password"));
/* 2567 */                           out.write("\");\n\t\t\tdocument.forms[1].password.select();\n\t\t\treturn;\n\t\t  }\n\t\t\t}\n\t\t}\n\n\tif(mode=='SNMP') \n        {                       \n                         var versionVal = trimAll($('[name=\"snmpVersionValue\"]').val());                         //No I18N\n                         if(versionVal != 'v3' && ($('[name=\"snmpCommunityString\"]').val()) == \"\")\n                         {                            \n                            alert(\"");
/* 2568 */                           out.print(FormatUtil.getString("am.webclient.host.jsalert.text"));
/* 2569 */                           out.write("\");\n                            document.forms[1].snmpCommunityString.select();\n                            return;\n                        }\n                        else if(versionVal == 'v3')//means v3\n                        {\n                            if( $('[name=\"snmpUserName\"]').val() == \"\")\n                            {\n                             alert(\"");
/* 2570 */                           out.print(FormatUtil.getString("am.webclient.testCredential.username"));
/* 2571 */                           out.write("\");\n                             document.forms[1].snmpUserName.select();\n                             return;\n                            }\n                        }\n\t}\n\n\tif(mode != 'SNMP' && mode !='SSH'  && trimAll(document.forms[1].password.value) == '')\n\t{\n\t\talert(\"");
/* 2572 */                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.password"));
/* 2573 */                           out.write("\");\n\t\tdocument.forms[1].password.select();\n\t\treturn;\n\t}\n\tif(mode != 'WMI')\n\t{\n\t\tvar port=trimAll(document.forms[1].port.value);\n\t\tif(port == '' || !(isPositiveInteger(port)) || port =='0' )\n\t\t{\n\t\t\talert(\"");
/* 2574 */                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.port"));
/* 2575 */                           out.write("\");\n\t\t\treturn;\n\t\t}\n\t}\n\tvar poll=trimAll(document.forms[1].pollinterval.value);\n\tif(poll == '' || !(isPositiveInteger(poll)) || poll =='0' )\n\t{\n\t\talert(\"");
/* 2576 */                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.polling"));
/* 2577 */                           out.write("\");\n\t\treturn;\n\t}\n\t\tif(trimAll(document.forms[1].displayname.value) == '' )\n\t{\n\t\talert(\"");
/* 2578 */                           out.print(FormatUtil.getString("am.webclient.newscript.alert.displaynameempty.text"));
/* 2579 */                           out.write("\");\n\t\treturn;\n\t}\n\tdocument.forms[1].submit();\n\t");
/* 2580 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 2581 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2585 */                       if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 2586 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/*      */                       }
/*      */                       else {
/* 2589 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 2590 */                         out.write("\n}\nfunction fnformsubmit1()\n{\n     ");
/* 2591 */                         if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 2592 */                           out.write("\n\t     location.href=\"/showresource.do?method=showResourceForResourceID&resourceid=\"+document.forms[1].msid.value+\"&datatype=2\";\n\t ");
/*      */                         } else {
/* 2594 */                           out.write("\n\t\t location.href=\"/showresource.do?method=showResourceForResourceID&resourceid=\"+document.forms[1].resourceid.value;\n     ");
/*      */                         }
/* 2596 */                         out.write("\t \t          \n}\n\nfunction fnformsubmit2()\n{\n   location.href=\"/showresource.do?method=showResourceForResourceID&resourceid=\"+document.forms[1].sqlmanid.value+\"&datatype=2\";\n}\n\nfunction validateAndSubmit1()\n{\n\tif(trimAll(document.forms[2].displayname.value) == '' )\n\t{\n\t\talert(\"");
/* 2597 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.displaynameempty.text"));
/* 2598 */                         out.write("\");\n\t\treturn;\n\t}\n\tif(trimAll(document.forms[2].processname.value) == '' )\n\t{\n\t\talert(\"");
/* 2599 */                         out.print(FormatUtil.getString("am.webclient.hostresourceconfig.processname.alert"));
/* 2600 */                         out.write("\");\n\t\treturn;\n\t}\n\tdocument.forms[2].submit();\n}\nfunction validateAndSubmit2()\n{\n\tif(trimAll(document.forms[1].displayname.value) == '' )\n\t{\n\t\talert(\"");
/* 2601 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.displaynameempty.text"));
/* 2602 */                         out.write("\");\n\t\treturn;\n\t}\n\tif(trimAll(document.forms[1].processname.value) == '' )\n\t{\n\t\talert(\"");
/* 2603 */                         out.print(FormatUtil.getString("am.webclient.hostresourceconfig.processname.alert"));
/* 2604 */                         out.write("\");\n\t\treturn;\n\t}\n\tdocument.forms[1].submit();\n}\n\nfunction validateAndSubmitService()\n{\nif(trimAll(document.forms[1].displayname.value) == '' )\n\t{\n\t\talert(\"");
/* 2605 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.displaynameempty.text"));
/* 2606 */                         out.write("\");\n\t\treturn;\n\t}\n\tif(trimAll(document.forms[1].processname.value) == '' )\n\t\t{\n\t\t\talert(\"");
/* 2607 */                         out.print(FormatUtil.getString("am.webclient.hostresourceconfig.servicename.alert"));
/* 2608 */                         out.write("\");\n\t\t\treturn;\n\t}\n\tdocument.forms[1].submit();\n}\n</script>\n");
/* 2609 */                         out.write(10);
/*      */                         
/* 2611 */                         this.haid = ((String)request.getAttribute("haid"));
/* 2612 */                         this.appname = ((String)request.getAttribute("appName"));
/* 2613 */                         this.network = ((String)request.getAttribute("network"));
/* 2614 */                         String resourcename = (String)request.getAttribute("name");
/* 2615 */                         String displayname = (String)request.getAttribute("displayname");
/*      */                         
/* 2617 */                         String tab = "1";
/* 2618 */                         String showprocessconfig = request.getParameter("addProcess");
/* 2619 */                         String editprocessconfig = request.getParameter("editProcess");
/* 2620 */                         String actiononServices = request.getParameter("actiononServices");
/* 2621 */                         boolean showconfig = true;
/* 2622 */                         if ((showprocessconfig != null) && (showprocessconfig.equals("true")))
/*      */                         {
/* 2624 */                           showconfig = false;
/*      */                         }
/* 2626 */                         if ((editprocessconfig != null) && (editprocessconfig.equals("true")))
/*      */                         {
/* 2628 */                           showconfig = false;
/*      */                         }
/* 2630 */                         if ((actiononServices != null) && (actiononServices.equals("Edit")))
/*      */                         {
/* 2632 */                           showconfig = false;
/*      */                         }
/* 2634 */                         String categorytype1 = com.adventnet.appmanager.util.Constants.getCategorytype();
/* 2635 */                         pageContext.setAttribute("category1", categorytype1);
/*      */                         
/*      */ 
/* 2638 */                         out.write("\n\n\n\n\n\n\n\n");
/*      */                         
/*      */ 
/* 2641 */                         DynaActionForm form = (DynaActionForm)request.getAttribute("HostResourceForm");
/* 2642 */                         String os = (String)form.get("os");
/* 2643 */                         boolean iswindows = true;
/*      */                         
/* 2645 */                         if (os != null)
/*      */                         {
/*      */ 
/* 2648 */                           if ((os.equals("Windows 2000")) || (os.equals("WindowsNT")) || (os.equals("Windows XP")) || (os.equals("Windows Vista")) || (os.equals("Windows 2003")) || (os.equals("Windows 2008")) || (os.equals("Windows 7")) || (os.equals("Windows 8")) || (os.equals("Windows 10")) || (os.equals("Windows 2012")))
/*      */                           {
/*      */ 
/* 2651 */                             iswindows = true;
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/* 2656 */                             iswindows = false;
/*      */                           }
/*      */                         }
/* 2659 */                         if (showconfig)
/*      */                         {
/*      */ 
/*      */ 
/* 2663 */                           out.write(10);
/* 2664 */                           out.write(10);
/*      */                           
/* 2666 */                           FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 2667 */                           _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2668 */                           _jspx_th_html_005fform_005f0.setParent(null);
/*      */                           
/* 2670 */                           _jspx_th_html_005fform_005f0.setAction("/HostResource");
/*      */                           
/* 2672 */                           _jspx_th_html_005fform_005f0.setStyle("display:inline");
/* 2673 */                           int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2674 */                           if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                             for (;;) {
/* 2676 */                               out.write("\n\n          <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n        <tr>\n\n    <td width=\"72%\" height=\"29\" class=\"tableheading\">");
/* 2677 */                               out.print(FormatUtil.getString("am.webclient.common.startmonitoring.text"));
/* 2678 */                               out.write("</td>\n    <td height=\"31\" class=\"tableheading\" align=\"right\" onClick=\"javascript:hideDiv('edit')\" ><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\">\n    \t\t<span class=\"bodytextboldwhiteun\" ><a href=\"javascript:hideDiv('edit')\" class=\"staticlinks\">");
/* 2679 */                               out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 2680 */                               out.write("</a></span>\n\t\t</td>\n            </tr>\n          </table>\n\n\n      <table width=\"99%\" border=0 cellpadding=5 cellspacing=0  valign=center class=\"lrborder\">\n           <tr>\n\t\t\t  <td class=\"bodytext label-align\" width=\"25%\">");
/* 2681 */                               out.print(FormatUtil.getString("am.webclient.common.displayname.text"));
/* 2682 */                               out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t  <td>\n\t\t\t  ");
/* 2683 */                               if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 2685 */                               out.write("\n\t\t\t  </td>\n\t\t\t  </tr>\n          <tr>\n          <tr>\n            <td class=\"bodytext label-align\" width=\"25%\">");
/* 2686 */                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.ostype"));
/* 2687 */                               out.write("\n\t\t\t<input type=\"hidden\" name=\"haid\" value=\"");
/* 2688 */                               out.print(request.getParameter("haid"));
/* 2689 */                               out.write("\"/>\n\t\t\t<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2690 */                               out.print((String)request.getAttribute("resourceid"));
/* 2691 */                               out.write("\"/>\n\t\t\t<input type=\"hidden\" name=\"configure\" value=\"true\"/>\n\t\t\t");
/* 2692 */                               if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 2694 */                               out.write("\n\t\t\t");
/* 2695 */                               if (_jspx_meth_c_005fif_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 2697 */                               out.write("\n\t\t\t<input type=\"hidden\" name=\"appName\" value=\"");
/* 2698 */                               out.print(request.getParameter("appName"));
/* 2699 */                               out.write("\"/>\n\t\t\t<input name=\"name\" type=\"hidden\" id=\"name\" value=\"");
/* 2700 */                               out.print(resourcename);
/* 2701 */                               out.write("\" size=\"15\"/>\n\t\t\t</td>\n            <td width=\"75%\">\n               ");
/*      */                               
/* 2703 */                               SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 2704 */                               _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 2705 */                               _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                               
/* 2707 */                               _jspx_th_html_005fselect_005f0.setProperty("os");
/*      */                               
/* 2709 */                               _jspx_th_html_005fselect_005f0.setStyleClass("formtextarea");
/*      */                               
/* 2711 */                               _jspx_th_html_005fselect_005f0.setOnchange("javascript:changemode()");
/* 2712 */                               int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 2713 */                               if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 2714 */                                 if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2715 */                                   out = _jspx_page_context.pushBody();
/* 2716 */                                   _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 2717 */                                   _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 2720 */                                   out.write("\n                               ");
/*      */                                   
/* 2722 */                                   if (categorytype1.equals("CLOUD"))
/*      */                                   {
/*      */ 
/* 2725 */                                     out.write("\n                                ");
/*      */                                     
/* 2727 */                                     OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2728 */                                     _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 2729 */                                     _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 2731 */                                     _jspx_th_html_005foption_005f0.setValue("Linux");
/* 2732 */                                     int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 2733 */                                     if (_jspx_eval_html_005foption_005f0 != 0) {
/* 2734 */                                       if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2735 */                                         out = _jspx_page_context.pushBody();
/* 2736 */                                         _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 2737 */                                         _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 2740 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.linux"));
/* 2741 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 2742 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 2745 */                                       if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2746 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 2749 */                                     if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 2750 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                     }
/*      */                                     
/* 2753 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 2754 */                                     out.write("\n                                ");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/*      */ 
/* 2760 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 2762 */                                     OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2763 */                                     _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 2764 */                                     _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 2766 */                                     _jspx_th_html_005foption_005f1.setValue("Node");
/* 2767 */                                     int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 2768 */                                     if (_jspx_eval_html_005foption_005f1 != 0) {
/* 2769 */                                       if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2770 */                                         out = _jspx_page_context.pushBody();
/* 2771 */                                         _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 2772 */                                         _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 2775 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.selectos"));
/* 2776 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 2777 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 2780 */                                       if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2781 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 2784 */                                     if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 2785 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                     }
/*      */                                     
/* 2788 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 2789 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 2791 */                                     OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2792 */                                     _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 2793 */                                     _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 2795 */                                     _jspx_th_html_005foption_005f2.setValue("AIX");
/* 2796 */                                     int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 2797 */                                     if (_jspx_eval_html_005foption_005f2 != 0) {
/* 2798 */                                       if (_jspx_eval_html_005foption_005f2 != 1) {
/* 2799 */                                         out = _jspx_page_context.pushBody();
/* 2800 */                                         _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 2801 */                                         _jspx_th_html_005foption_005f2.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 2804 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.aix"));
/* 2805 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 2806 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 2809 */                                       if (_jspx_eval_html_005foption_005f2 != 1) {
/* 2810 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 2813 */                                     if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 2814 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                     }
/*      */                                     
/* 2817 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 2818 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 2820 */                                     OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2821 */                                     _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 2822 */                                     _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 2824 */                                     _jspx_th_html_005foption_005f3.setValue("FreeBSD");
/* 2825 */                                     int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 2826 */                                     if (_jspx_eval_html_005foption_005f3 != 0) {
/* 2827 */                                       if (_jspx_eval_html_005foption_005f3 != 1) {
/* 2828 */                                         out = _jspx_page_context.pushBody();
/* 2829 */                                         _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 2830 */                                         _jspx_th_html_005foption_005f3.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 2833 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.freebsd"));
/* 2834 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 2835 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 2838 */                                       if (_jspx_eval_html_005foption_005f3 != 1) {
/* 2839 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 2842 */                                     if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 2843 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                     }
/*      */                                     
/* 2846 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 2847 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 2849 */                                     OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2850 */                                     _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 2851 */                                     _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 2853 */                                     _jspx_th_html_005foption_005f4.setValue("HP-UX");
/* 2854 */                                     int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 2855 */                                     if (_jspx_eval_html_005foption_005f4 != 0) {
/* 2856 */                                       if (_jspx_eval_html_005foption_005f4 != 1) {
/* 2857 */                                         out = _jspx_page_context.pushBody();
/* 2858 */                                         _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 2859 */                                         _jspx_th_html_005foption_005f4.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 2862 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.hpux"));
/* 2863 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 2864 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 2867 */                                       if (_jspx_eval_html_005foption_005f4 != 1) {
/* 2868 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 2871 */                                     if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 2872 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                     }
/*      */                                     
/* 2875 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 2876 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 2878 */                                     OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2879 */                                     _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 2880 */                                     _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 2882 */                                     _jspx_th_html_005foption_005f5.setValue("HP-TRU64");
/* 2883 */                                     int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 2884 */                                     if (_jspx_eval_html_005foption_005f5 != 0) {
/* 2885 */                                       if (_jspx_eval_html_005foption_005f5 != 1) {
/* 2886 */                                         out = _jspx_page_context.pushBody();
/* 2887 */                                         _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 2888 */                                         _jspx_th_html_005foption_005f5.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 2891 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.hptru64"));
/* 2892 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 2893 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 2896 */                                       if (_jspx_eval_html_005foption_005f5 != 1) {
/* 2897 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 2900 */                                     if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 2901 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                     }
/*      */                                     
/* 2904 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 2905 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 2907 */                                     OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2908 */                                     _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 2909 */                                     _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 2911 */                                     _jspx_th_html_005foption_005f6.setValue("Linux");
/* 2912 */                                     int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 2913 */                                     if (_jspx_eval_html_005foption_005f6 != 0) {
/* 2914 */                                       if (_jspx_eval_html_005foption_005f6 != 1) {
/* 2915 */                                         out = _jspx_page_context.pushBody();
/* 2916 */                                         _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 2917 */                                         _jspx_th_html_005foption_005f6.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 2920 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.linux"));
/* 2921 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 2922 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 2925 */                                       if (_jspx_eval_html_005foption_005f6 != 1) {
/* 2926 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 2929 */                                     if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 2930 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                     }
/*      */                                     
/* 2933 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 2934 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 2936 */                                     OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2937 */                                     _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 2938 */                                     _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 2940 */                                     _jspx_th_html_005foption_005f7.setValue("Novell");
/* 2941 */                                     int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 2942 */                                     if (_jspx_eval_html_005foption_005f7 != 0) {
/* 2943 */                                       if (_jspx_eval_html_005foption_005f7 != 1) {
/* 2944 */                                         out = _jspx_page_context.pushBody();
/* 2945 */                                         _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 2946 */                                         _jspx_th_html_005foption_005f7.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 2949 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.novell"));
/* 2950 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 2951 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 2954 */                                       if (_jspx_eval_html_005foption_005f7 != 1) {
/* 2955 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 2958 */                                     if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 2959 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                     }
/*      */                                     
/* 2962 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/* 2963 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 2965 */                                     OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2966 */                                     _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 2967 */                                     _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 2969 */                                     _jspx_th_html_005foption_005f8.setValue("Mac OS");
/* 2970 */                                     int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 2971 */                                     if (_jspx_eval_html_005foption_005f8 != 0) {
/* 2972 */                                       if (_jspx_eval_html_005foption_005f8 != 1) {
/* 2973 */                                         out = _jspx_page_context.pushBody();
/* 2974 */                                         _jspx_th_html_005foption_005f8.setBodyContent((BodyContent)out);
/* 2975 */                                         _jspx_th_html_005foption_005f8.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 2978 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.mac"));
/* 2979 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f8.doAfterBody();
/* 2980 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 2983 */                                       if (_jspx_eval_html_005foption_005f8 != 1) {
/* 2984 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 2987 */                                     if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 2988 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                                     }
/*      */                                     
/* 2991 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8);
/* 2992 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 2994 */                                     OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2995 */                                     _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 2996 */                                     _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 2998 */                                     _jspx_th_html_005foption_005f9.setValue("SUN");
/* 2999 */                                     int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 3000 */                                     if (_jspx_eval_html_005foption_005f9 != 0) {
/* 3001 */                                       if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3002 */                                         out = _jspx_page_context.pushBody();
/* 3003 */                                         _jspx_th_html_005foption_005f9.setBodyContent((BodyContent)out);
/* 3004 */                                         _jspx_th_html_005foption_005f9.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3007 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.sunsolaris"));
/* 3008 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f9.doAfterBody();
/* 3009 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3012 */                                       if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3013 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3016 */                                     if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 3017 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                                     }
/*      */                                     
/* 3020 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9);
/* 3021 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 3023 */                                     OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3024 */                                     _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/* 3025 */                                     _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 3027 */                                     _jspx_th_html_005foption_005f10.setValue("Windows 2000");
/* 3028 */                                     int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/* 3029 */                                     if (_jspx_eval_html_005foption_005f10 != 0) {
/* 3030 */                                       if (_jspx_eval_html_005foption_005f10 != 1) {
/* 3031 */                                         out = _jspx_page_context.pushBody();
/* 3032 */                                         _jspx_th_html_005foption_005f10.setBodyContent((BodyContent)out);
/* 3033 */                                         _jspx_th_html_005foption_005f10.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3036 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.win2000"));
/* 3037 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f10.doAfterBody();
/* 3038 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3041 */                                       if (_jspx_eval_html_005foption_005f10 != 1) {
/* 3042 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3045 */                                     if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/* 3046 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                                     }
/*      */                                     
/* 3049 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10);
/* 3050 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 3052 */                                     OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3053 */                                     _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/* 3054 */                                     _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 3056 */                                     _jspx_th_html_005foption_005f11.setValue("Windows 2003");
/* 3057 */                                     int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/* 3058 */                                     if (_jspx_eval_html_005foption_005f11 != 0) {
/* 3059 */                                       if (_jspx_eval_html_005foption_005f11 != 1) {
/* 3060 */                                         out = _jspx_page_context.pushBody();
/* 3061 */                                         _jspx_th_html_005foption_005f11.setBodyContent((BodyContent)out);
/* 3062 */                                         _jspx_th_html_005foption_005f11.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3065 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.win2003"));
/* 3066 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f11.doAfterBody();
/* 3067 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3070 */                                       if (_jspx_eval_html_005foption_005f11 != 1) {
/* 3071 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3074 */                                     if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/* 3075 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11); return;
/*      */                                     }
/*      */                                     
/* 3078 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11);
/* 3079 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 3081 */                                     OptionTag _jspx_th_html_005foption_005f12 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3082 */                                     _jspx_th_html_005foption_005f12.setPageContext(_jspx_page_context);
/* 3083 */                                     _jspx_th_html_005foption_005f12.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 3085 */                                     _jspx_th_html_005foption_005f12.setValue("Windows XP");
/* 3086 */                                     int _jspx_eval_html_005foption_005f12 = _jspx_th_html_005foption_005f12.doStartTag();
/* 3087 */                                     if (_jspx_eval_html_005foption_005f12 != 0) {
/* 3088 */                                       if (_jspx_eval_html_005foption_005f12 != 1) {
/* 3089 */                                         out = _jspx_page_context.pushBody();
/* 3090 */                                         _jspx_th_html_005foption_005f12.setBodyContent((BodyContent)out);
/* 3091 */                                         _jspx_th_html_005foption_005f12.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3094 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.winxp"));
/* 3095 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f12.doAfterBody();
/* 3096 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3099 */                                       if (_jspx_eval_html_005foption_005f12 != 1) {
/* 3100 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3103 */                                     if (_jspx_th_html_005foption_005f12.doEndTag() == 5) {
/* 3104 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12); return;
/*      */                                     }
/*      */                                     
/* 3107 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12);
/* 3108 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 3110 */                                     OptionTag _jspx_th_html_005foption_005f13 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3111 */                                     _jspx_th_html_005foption_005f13.setPageContext(_jspx_page_context);
/* 3112 */                                     _jspx_th_html_005foption_005f13.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 3114 */                                     _jspx_th_html_005foption_005f13.setValue("Windows Vista");
/* 3115 */                                     int _jspx_eval_html_005foption_005f13 = _jspx_th_html_005foption_005f13.doStartTag();
/* 3116 */                                     if (_jspx_eval_html_005foption_005f13 != 0) {
/* 3117 */                                       if (_jspx_eval_html_005foption_005f13 != 1) {
/* 3118 */                                         out = _jspx_page_context.pushBody();
/* 3119 */                                         _jspx_th_html_005foption_005f13.setBodyContent((BodyContent)out);
/* 3120 */                                         _jspx_th_html_005foption_005f13.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3123 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.winvista"));
/* 3124 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f13.doAfterBody();
/* 3125 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3128 */                                       if (_jspx_eval_html_005foption_005f13 != 1) {
/* 3129 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3132 */                                     if (_jspx_th_html_005foption_005f13.doEndTag() == 5) {
/* 3133 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13); return;
/*      */                                     }
/*      */                                     
/* 3136 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13);
/* 3137 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 3139 */                                     OptionTag _jspx_th_html_005foption_005f14 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3140 */                                     _jspx_th_html_005foption_005f14.setPageContext(_jspx_page_context);
/* 3141 */                                     _jspx_th_html_005foption_005f14.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 3143 */                                     _jspx_th_html_005foption_005f14.setValue("Windows 7");
/* 3144 */                                     int _jspx_eval_html_005foption_005f14 = _jspx_th_html_005foption_005f14.doStartTag();
/* 3145 */                                     if (_jspx_eval_html_005foption_005f14 != 0) {
/* 3146 */                                       if (_jspx_eval_html_005foption_005f14 != 1) {
/* 3147 */                                         out = _jspx_page_context.pushBody();
/* 3148 */                                         _jspx_th_html_005foption_005f14.setBodyContent((BodyContent)out);
/* 3149 */                                         _jspx_th_html_005foption_005f14.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3152 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.win7"));
/* 3153 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f14.doAfterBody();
/* 3154 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3157 */                                       if (_jspx_eval_html_005foption_005f14 != 1) {
/* 3158 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3161 */                                     if (_jspx_th_html_005foption_005f14.doEndTag() == 5) {
/* 3162 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14); return;
/*      */                                     }
/*      */                                     
/* 3165 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14);
/* 3166 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 3168 */                                     OptionTag _jspx_th_html_005foption_005f15 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3169 */                                     _jspx_th_html_005foption_005f15.setPageContext(_jspx_page_context);
/* 3170 */                                     _jspx_th_html_005foption_005f15.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 3172 */                                     _jspx_th_html_005foption_005f15.setValue("Windows 2008");
/* 3173 */                                     int _jspx_eval_html_005foption_005f15 = _jspx_th_html_005foption_005f15.doStartTag();
/* 3174 */                                     if (_jspx_eval_html_005foption_005f15 != 0) {
/* 3175 */                                       if (_jspx_eval_html_005foption_005f15 != 1) {
/* 3176 */                                         out = _jspx_page_context.pushBody();
/* 3177 */                                         _jspx_th_html_005foption_005f15.setBodyContent((BodyContent)out);
/* 3178 */                                         _jspx_th_html_005foption_005f15.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3181 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.winn2008"));
/* 3182 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f15.doAfterBody();
/* 3183 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3186 */                                       if (_jspx_eval_html_005foption_005f15 != 1) {
/* 3187 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3190 */                                     if (_jspx_th_html_005foption_005f15.doEndTag() == 5) {
/* 3191 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15); return;
/*      */                                     }
/*      */                                     
/* 3194 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15);
/* 3195 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 3197 */                                     OptionTag _jspx_th_html_005foption_005f16 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3198 */                                     _jspx_th_html_005foption_005f16.setPageContext(_jspx_page_context);
/* 3199 */                                     _jspx_th_html_005foption_005f16.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 3201 */                                     _jspx_th_html_005foption_005f16.setValue("WindowsNT");
/* 3202 */                                     int _jspx_eval_html_005foption_005f16 = _jspx_th_html_005foption_005f16.doStartTag();
/* 3203 */                                     if (_jspx_eval_html_005foption_005f16 != 0) {
/* 3204 */                                       if (_jspx_eval_html_005foption_005f16 != 1) {
/* 3205 */                                         out = _jspx_page_context.pushBody();
/* 3206 */                                         _jspx_th_html_005foption_005f16.setBodyContent((BodyContent)out);
/* 3207 */                                         _jspx_th_html_005foption_005f16.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3210 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.winnt"));
/* 3211 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f16.doAfterBody();
/* 3212 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3215 */                                       if (_jspx_eval_html_005foption_005f16 != 1) {
/* 3216 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3219 */                                     if (_jspx_th_html_005foption_005f16.doEndTag() == 5) {
/* 3220 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16); return;
/*      */                                     }
/*      */                                     
/* 3223 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16);
/* 3224 */                                     out.write("\n\t \t\t\t");
/*      */                                     
/* 3226 */                                     OptionTag _jspx_th_html_005foption_005f17 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3227 */                                     _jspx_th_html_005foption_005f17.setPageContext(_jspx_page_context);
/* 3228 */                                     _jspx_th_html_005foption_005f17.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 3230 */                                     _jspx_th_html_005foption_005f17.setValue("Windows 8");
/* 3231 */                                     int _jspx_eval_html_005foption_005f17 = _jspx_th_html_005foption_005f17.doStartTag();
/* 3232 */                                     if (_jspx_eval_html_005foption_005f17 != 0) {
/* 3233 */                                       if (_jspx_eval_html_005foption_005f17 != 1) {
/* 3234 */                                         out = _jspx_page_context.pushBody();
/* 3235 */                                         _jspx_th_html_005foption_005f17.setBodyContent((BodyContent)out);
/* 3236 */                                         _jspx_th_html_005foption_005f17.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3239 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.winn8"));
/* 3240 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f17.doAfterBody();
/* 3241 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3244 */                                       if (_jspx_eval_html_005foption_005f17 != 1) {
/* 3245 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3248 */                                     if (_jspx_th_html_005foption_005f17.doEndTag() == 5) {
/* 3249 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17); return;
/*      */                                     }
/*      */                                     
/* 3252 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17);
/* 3253 */                                     out.write("\n\t \t\t\t");
/*      */                                     
/* 3255 */                                     OptionTag _jspx_th_html_005foption_005f18 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3256 */                                     _jspx_th_html_005foption_005f18.setPageContext(_jspx_page_context);
/* 3257 */                                     _jspx_th_html_005foption_005f18.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 3259 */                                     _jspx_th_html_005foption_005f18.setValue("Windows 2012");
/* 3260 */                                     int _jspx_eval_html_005foption_005f18 = _jspx_th_html_005foption_005f18.doStartTag();
/* 3261 */                                     if (_jspx_eval_html_005foption_005f18 != 0) {
/* 3262 */                                       if (_jspx_eval_html_005foption_005f18 != 1) {
/* 3263 */                                         out = _jspx_page_context.pushBody();
/* 3264 */                                         _jspx_th_html_005foption_005f18.setBodyContent((BodyContent)out);
/* 3265 */                                         _jspx_th_html_005foption_005f18.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3268 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.winn2012"));
/* 3269 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f18.doAfterBody();
/* 3270 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3273 */                                       if (_jspx_eval_html_005foption_005f18 != 1) {
/* 3274 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3277 */                                     if (_jspx_th_html_005foption_005f18.doEndTag() == 5) {
/* 3278 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18); return;
/*      */                                     }
/*      */                                     
/* 3281 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18);
/* 3282 */                                     out.write("\t\t\t\t\n\t \t\t\t");
/*      */                                     
/* 3284 */                                     OptionTag _jspx_th_html_005foption_005f19 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3285 */                                     _jspx_th_html_005foption_005f19.setPageContext(_jspx_page_context);
/* 3286 */                                     _jspx_th_html_005foption_005f19.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 3288 */                                     _jspx_th_html_005foption_005f19.setValue("Windows 10");
/* 3289 */                                     int _jspx_eval_html_005foption_005f19 = _jspx_th_html_005foption_005f19.doStartTag();
/* 3290 */                                     if (_jspx_eval_html_005foption_005f19 != 0) {
/* 3291 */                                       if (_jspx_eval_html_005foption_005f19 != 1) {
/* 3292 */                                         out = _jspx_page_context.pushBody();
/* 3293 */                                         _jspx_th_html_005foption_005f19.setBodyContent((BodyContent)out);
/* 3294 */                                         _jspx_th_html_005foption_005f19.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3297 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.win10"));
/* 3298 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f19.doAfterBody();
/* 3299 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3302 */                                       if (_jspx_eval_html_005foption_005f19 != 1) {
/* 3303 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3306 */                                     if (_jspx_th_html_005foption_005f19.doEndTag() == 5) {
/* 3307 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19); return;
/*      */                                     }
/*      */                                     
/* 3310 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19);
/* 3311 */                                     out.write("\n\t\t\t\t");
/*      */                                   }
/*      */                                   
/*      */ 
/* 3315 */                                   out.write("\n\n              ");
/* 3316 */                                   int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 3317 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 3320 */                                 if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3321 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 3324 */                               if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 3325 */                                 this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                               }
/*      */                               
/* 3328 */                               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 3329 */                               out.write("</td>\n\n\t\t\t  </tr>\n\t\t\t  <tr>\n\t\t\t  <td class=\"bodytext label-align\">");
/* 3330 */                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.mode"));
/* 3331 */                               out.write("</td>\n\t\t\t  <td class=\"bodytext\">\n\t\t\t  ");
/*      */                               
/* 3333 */                               SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 3334 */                               _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 3335 */                               _jspx_th_html_005fselect_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                               
/* 3337 */                               _jspx_th_html_005fselect_005f1.setProperty("mode");
/*      */                               
/* 3339 */                               _jspx_th_html_005fselect_005f1.setStyleClass("formtextarea");
/*      */                               
/* 3341 */                               _jspx_th_html_005fselect_005f1.setOnchange("javascript:changeport()");
/* 3342 */                               int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 3343 */                               if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 3344 */                                 if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 3345 */                                   out = _jspx_page_context.pushBody();
/* 3346 */                                   _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 3347 */                                   _jspx_th_html_005fselect_005f1.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 3350 */                                   out.write("\n\t\t\t  ");
/*      */                                   
/* 3352 */                                   if ((!iswindows) && (!os.equals("Novell")))
/*      */                                   {
/*      */ 
/* 3355 */                                     out.write("\n\t\t\t\t\t  ");
/*      */                                     
/* 3357 */                                     OptionTag _jspx_th_html_005foption_005f20 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3358 */                                     _jspx_th_html_005foption_005f20.setPageContext(_jspx_page_context);
/* 3359 */                                     _jspx_th_html_005foption_005f20.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                     
/* 3361 */                                     _jspx_th_html_005foption_005f20.setValue("TELNET");
/* 3362 */                                     int _jspx_eval_html_005foption_005f20 = _jspx_th_html_005foption_005f20.doStartTag();
/* 3363 */                                     if (_jspx_eval_html_005foption_005f20 != 0) {
/* 3364 */                                       if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3365 */                                         out = _jspx_page_context.pushBody();
/* 3366 */                                         _jspx_th_html_005foption_005f20.setBodyContent((BodyContent)out);
/* 3367 */                                         _jspx_th_html_005foption_005f20.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3370 */                                         out.write(32);
/* 3371 */                                         out.write(32);
/* 3372 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.telnet"));
/* 3373 */                                         out.write("    ");
/* 3374 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f20.doAfterBody();
/* 3375 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3378 */                                       if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3379 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3382 */                                     if (_jspx_th_html_005foption_005f20.doEndTag() == 5) {
/* 3383 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20); return;
/*      */                                     }
/*      */                                     
/* 3386 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20);
/* 3387 */                                     out.write("\n\t\t\t\t\t  ");
/*      */                                     
/* 3389 */                                     OptionTag _jspx_th_html_005foption_005f21 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3390 */                                     _jspx_th_html_005foption_005f21.setPageContext(_jspx_page_context);
/* 3391 */                                     _jspx_th_html_005foption_005f21.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                     
/* 3393 */                                     _jspx_th_html_005foption_005f21.setValue("SSH");
/* 3394 */                                     int _jspx_eval_html_005foption_005f21 = _jspx_th_html_005foption_005f21.doStartTag();
/* 3395 */                                     if (_jspx_eval_html_005foption_005f21 != 0) {
/* 3396 */                                       if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3397 */                                         out = _jspx_page_context.pushBody();
/* 3398 */                                         _jspx_th_html_005foption_005f21.setBodyContent((BodyContent)out);
/* 3399 */                                         _jspx_th_html_005foption_005f21.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3402 */                                         out.write(32);
/* 3403 */                                         out.write(32);
/* 3404 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssh"));
/* 3405 */                                         out.write(32);
/* 3406 */                                         out.write(32);
/* 3407 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f21.doAfterBody();
/* 3408 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3411 */                                       if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3412 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3415 */                                     if (_jspx_th_html_005foption_005f21.doEndTag() == 5) {
/* 3416 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21); return;
/*      */                                     }
/*      */                                     
/* 3419 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21);
/* 3420 */                                     out.write("\n\t\t\t\t\t  ");
/*      */                                   }
/*      */                                   
/*      */ 
/* 3424 */                                   if ((!os.equals("AIX")) && (!os.equals("HP-UX")) && (!os.equals("HP-TRU64")))
/*      */                                   {
/* 3426 */                                     out.write(10);
/* 3427 */                                     out.write(10);
/* 3428 */                                     out.write(9);
/*      */                                     
/* 3430 */                                     OptionTag _jspx_th_html_005foption_005f22 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3431 */                                     _jspx_th_html_005foption_005f22.setPageContext(_jspx_page_context);
/* 3432 */                                     _jspx_th_html_005foption_005f22.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                     
/* 3434 */                                     _jspx_th_html_005foption_005f22.setValue("SNMP");
/* 3435 */                                     int _jspx_eval_html_005foption_005f22 = _jspx_th_html_005foption_005f22.doStartTag();
/* 3436 */                                     if (_jspx_eval_html_005foption_005f22 != 0) {
/* 3437 */                                       if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3438 */                                         out = _jspx_page_context.pushBody();
/* 3439 */                                         _jspx_th_html_005foption_005f22.setBodyContent((BodyContent)out);
/* 3440 */                                         _jspx_th_html_005foption_005f22.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3443 */                                         out.write(32);
/* 3444 */                                         out.write(32);
/* 3445 */                                         out.print(FormatUtil.getString("am.monitortab.tableview.SNMP.text"));
/* 3446 */                                         out.write(32);
/* 3447 */                                         out.write(32);
/* 3448 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f22.doAfterBody();
/* 3449 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3452 */                                       if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3453 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3456 */                                     if (_jspx_th_html_005foption_005f22.doEndTag() == 5) {
/* 3457 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22); return;
/*      */                                     }
/*      */                                     
/* 3460 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22);
/* 3461 */                                     out.write(10);
/* 3462 */                                     out.write(9);
/*      */                                   }
/*      */                                   
/* 3465 */                                   if (iswindows)
/*      */                                   {
/* 3467 */                                     if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.wmimonitors.remove"))
/*      */                                     {
/* 3469 */                                       out.write("\n\t\t\t");
/*      */                                       
/* 3471 */                                       OptionTag _jspx_th_html_005foption_005f23 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3472 */                                       _jspx_th_html_005foption_005f23.setPageContext(_jspx_page_context);
/* 3473 */                                       _jspx_th_html_005foption_005f23.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                       
/* 3475 */                                       _jspx_th_html_005foption_005f23.setValue("WMI");
/* 3476 */                                       int _jspx_eval_html_005foption_005f23 = _jspx_th_html_005foption_005f23.doStartTag();
/* 3477 */                                       if (_jspx_eval_html_005foption_005f23 != 0) {
/* 3478 */                                         if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3479 */                                           out = _jspx_page_context.pushBody();
/* 3480 */                                           _jspx_th_html_005foption_005f23.setBodyContent((BodyContent)out);
/* 3481 */                                           _jspx_th_html_005foption_005f23.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3484 */                                           out.write(32);
/* 3485 */                                           out.write(32);
/* 3486 */                                           out.print(FormatUtil.getString("am.webclient.hostresourceconfig.wmi"));
/* 3487 */                                           out.write(32);
/* 3488 */                                           out.write(32);
/* 3489 */                                           int evalDoAfterBody = _jspx_th_html_005foption_005f23.doAfterBody();
/* 3490 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3493 */                                         if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3494 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3497 */                                       if (_jspx_th_html_005foption_005f23.doEndTag() == 5) {
/* 3498 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23); return;
/*      */                                       }
/*      */                                       
/* 3501 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23);
/* 3502 */                                       out.write("\n\t\t\t");
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 3506 */                                   out.write(10);
/* 3507 */                                   out.write(9);
/* 3508 */                                   int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 3509 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 3512 */                                 if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 3513 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 3516 */                               if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 3517 */                                 this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */                               }
/*      */                               
/* 3520 */                               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 3521 */                               out.write("\n\t\t</td>\n\t\t</tr>\n                 <tr id=\"eventlogstatus\" style=\"display: table-row;\">\n    <td class=\"bodytext label-align\">");
/* 3522 */                               out.print(FormatUtil.getString("am.webclient.eventlogrules.enableevventlog.text"));
/* 3523 */                               out.write("</td>\n    <td height=\"28\" width=\"75%\">\n\n\t                ");
/* 3524 */                               if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 3526 */                               out.write("\n\n\t             </td>\n                </tr>\n\t\t<tr>\n\t\t<td colspan=2>\n\t\t<div id=\"port\" style=\"DISPLAY: none\">\n\t\t<table width=\"100%\" border=0 cellpadding=5 cellspacing=0  valign=center>\n\t\t<tr>\n\t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 3527 */                               out.print(FormatUtil.getString("Port Number"));
/* 3528 */                               out.write("</td>\n\n\t\t<td width=\"75%\">\n\t\t");
/* 3529 */                               if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 3531 */                               out.write("\n\t\t</td>\n\t\t</tr>\n\t\t</table>\n\t\t</div>\n        </td>\n\t\t</tr>\n\t\t\n\t\t  <tr id=\"sshKeyAuth\" style=\"display:none\">\n\t\t   <td width=\"100%\" colspan=\"2\">\n\t\t    <table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\">\n        <tr>\n\n          <TD height=\"28\" width=\"25%\" class=\"bodytext label-align\">");
/* 3532 */                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssh.privateKeyMessage"));
/* 3533 */                               out.write("</TD>\n\t\t\t\t<TD height=\"28\" width=\"75%\" class=\"bodytext\">\n\t\t\t\t");
/* 3534 */                               if (_jspx_meth_c_005fchoose_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 3536 */                               out.write("\t\t\t\t\t\t\n          </TD>\n\t\t    </tr>\n\t\t   </table>\n\t\t  </td>\n\t\t </tr>\n         <tr id=\"username\" style=\"display:none\">\n\t\t <td width=\"100%\" colspan=\"2\">\n\t\t <table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\">\n\t\t  <tr>\n\t\t   <TD height=\"28\" width=\"25%\" class=\"bodytext label-align\">");
/* 3537 */                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/* 3538 */                               out.write("<span class=\"mandatory\">*</span></TD>\n\t\t   <TD height=\"28\" width=\"75%\">");
/* 3539 */                               if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 3541 */                               out.write("&nbsp; <span class=\"bodytext\"></span> </TD>\n\t\t  </TR>\n\t\t </table>\n\t\t</td>\n\t\t</tr>\n        <tr id=\"password\" style=\"display:none\">\n\t\t <td width=\"100%\" colspan=\"2\">\n\t\t  <table width=\"100%\" cellspacing=\"5\" cellpadding=\"0\">\n\t\t   <tr>\n\t\t    <TD height=\"28\" width=\"25%\" class=\"bodytext label-align\">");
/* 3542 */                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/* 3543 */                               out.write("<span class=\"mandatory\">*</span></TD>\n\t\t    <TD height=\"28\" width=\"75%\">");
/* 3544 */                               if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 3546 */                               out.write("&nbsp; <span class=\"bodytext\"></span> </TD>\n\t\t   </TR>\n\t\t  </table>\n\t\t </td>\n\t\t </tr>\n\t\t <tr id=\"privateKey\" style=\"display:none\">\n\t\t  <td width=\"100%\" colspan=\"2\">\n\t\t    <table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\">\n\t\t     <tr>\n\t\t       <TD height=\"28\" width=\"25%\" class=\"bodytext label-align\">");
/* 3547 */                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssh.privateKey"));
/* 3548 */                               out.write("<span class=\"mandatory\">*</span></TD>\n\t\t       <TD height=\"28\" width=\"75%\">");
/* 3549 */                               if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 3551 */                               out.write("&nbsp;<span class=\"bodytext\"></span> </TD>\n\t\t     </TR>\n\t\t    </table>\n\t\t   </td>\n\t\t </tr>\n        <tr id=\"passphrase\" style=\"display:none\">\n\t\t <td width=\"100%\" colspan=\"2\">\n\t\t  <table width=\"100%\" cellspacing=\"0\" cellpadding=\"5\">\n\t\t   <tr>\n\t\t    <TD height=\"28\" width=\"25%\" class=\"bodytext label-align\">");
/* 3552 */                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.passphrase"));
/* 3553 */                               out.write("</TD>\n\t\t    <TD height=\"28\" width=\"75%\">");
/* 3554 */                               if (_jspx_meth_html_005fpassword_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 3556 */                               out.write("&nbsp; <span class=\"bodytext\"></span> </TD>\n\t\t   </TR>\n\t\t  </table>\n\t\t </td>\n\t\t </tr> \n\n\t\t\t<tr>\n\t\t\t <td colspan=\"2\">\n\t\t\t <div id=\"prompt1\" style=\"DISPLAY: none\">\n\n        <table cellpadding=\"5\" cellspacing=\"0\" width=\"100%\">\n          <tr>\n            <td width=\"25%\" class=\"bodytext label-align\">");
/* 3557 */                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.commandprompt"));
/* 3558 */                               out.write("</td>\n            <td width=\"20%\"> ");
/* 3559 */                               if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 3561 */                               out.write("\n            </td>\n            <td width=\"55%\"><span class=\"footer\">");
/* 3562 */                               out.print(FormatUtil.getString("am.webclient.hostresourceconfig.commandprompt.message"));
/* 3563 */                               out.write("</span></td>\n        </table>\n\t\t\t </div>\n\t\t\t </td>\n\t\t\t</tr>\n\t\t\t  <tr>\n\t\t\t  <td class=\"bodytext label-align\">");
/* 3564 */                               out.print(FormatUtil.getString("am.webclient.common.pollinginterval.text"));
/* 3565 */                               out.write("</td>\n\t\t\t  <td>\n\t\t\t  ");
/* 3566 */                               if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 3568 */                               out.write(" <span class=\"footer\">&nbsp;");
/* 3569 */                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.minutes"));
/* 3570 */                               out.write("</span>\n\t\t\t  </td>\n\t\t\t  </tr>\n\t\t\t  <tr>\n\t\t\t  <td class=\"bodytext label-align\">");
/* 3571 */                               out.print(FormatUtil.getString("Timeout"));
/* 3572 */                               out.write("</td>\n\t\t\t  <td>\n\t\t\t  ");
/* 3573 */                               if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 3575 */                               out.write(" <span class=\"footer\">");
/* 3576 */                               out.print(FormatUtil.getString("am.webclient.common.seconds.text"));
/* 3577 */                               out.write("</span>\n\t\t\t  </td>\n\t\t\t  </tr>\t\t\t  \n        </table>\n        <table width=\"99%\" border=\"0\" cellpadding=\"5\" class=\"lrborder\" id=\"snmpTableView\" style=\"DISPLAY: none\">\n\n<tr>\n                <td width=\"25%\" align=\"left\" class=\"tablebottom bodytextbold label-align\">\n                    ");
/* 3578 */                               out.print(FormatUtil.getString("webclient.topo.objectdetails.version"));
/* 3579 */                               out.write("\n                </td>\n                <td align=\"left\" class=\"tablebottom bodytextbold\">\n                    ");
/* 3580 */                               if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 3582 */                               if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 3584 */                               out.write(" &nbsp; &nbsp;\n                    ");
/* 3585 */                               if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 3587 */                               if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 3589 */                               out.write("\n                </td>\n        </tr>\n        <tr><td colspan=\"2\" align=\"left\"><span id=\"testCredentialResult\"></span></td></tr>\n         <tr>\n         <td colspan=\"2\" height=\"70\">\n         <div id=\"snmpV1V2\" style=\"display:none\">\n            <table width=\"100%\" >\n                <tr>\n                    <td width=\"25%\" class=\"bodytext label-align\"><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,'");
/* 3590 */                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.community.value"));
/* 3591 */                               out.write("',false,true,'#000000',130,'lightyellow')\" onMouseOut=\"hideddrivetip()\">");
/* 3592 */                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.community"));
/* 3593 */                               out.write("</a><span class=\"mandatory\">*</span>\n                    </td>\n                    <td height=\"28\" colspan=\"3\">");
/* 3594 */                               if (_jspx_meth_html_005fpassword_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 3596 */                               out.write("\n                    </td>\n                </tr>\n                <tr height=\"6\">\n                </tr>\n                <tr>\n                \t<td width=\"25%\"></td>\n                    <td align=\"left\" colspan=\"3\">\n                         <input name=\"testCredentialButton\" type=\"button\" class=\"buttons btn_test\" value=\"");
/* 3597 */                               out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.testCredential"));
/* 3598 */                               out.write("\" onClick=\"javascript:validateAndPerformTestCredential('v1v2');\">\n                    </td>\n                </tr>\n\n            </table>\n         </div>\n         <div id=\"snmpV3\" style=\"display:none\">\n            <table width=\"100%\">\n            <tr>\n                <td width=\"25%\" class=\"bodytext label-align\">");
/* 3599 */                               out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.securityLevel"));
/* 3600 */                               out.write("<span class=\"mandatory\">*</span></td>\n                <td width=\"75%\" colspan=\"3\">\n\t\t\t          ");
/*      */                               
/* 3602 */                               SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 3603 */                               _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 3604 */                               _jspx_th_html_005fselect_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                               
/* 3606 */                               _jspx_th_html_005fselect_005f2.setProperty("snmpSecurityLevel");
/*      */                               
/* 3608 */                               _jspx_th_html_005fselect_005f2.setStyleClass("formtextarea");
/*      */                               
/* 3610 */                               _jspx_th_html_005fselect_005f2.setOnchange("javascript:showSecurityLevelProps();");
/* 3611 */                               int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 3612 */                               if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 3613 */                                 if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 3614 */                                   out = _jspx_page_context.pushBody();
/* 3615 */                                   _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 3616 */                                   _jspx_th_html_005fselect_005f2.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 3619 */                                   out.write("\n\t\t\t            ");
/*      */                                   
/* 3621 */                                   OptionTag _jspx_th_html_005foption_005f24 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3622 */                                   _jspx_th_html_005foption_005f24.setPageContext(_jspx_page_context);
/* 3623 */                                   _jspx_th_html_005foption_005f24.setParent(_jspx_th_html_005fselect_005f2);
/*      */                                   
/* 3625 */                                   _jspx_th_html_005foption_005f24.setValue("NOAUTHNOPRIV");
/* 3626 */                                   int _jspx_eval_html_005foption_005f24 = _jspx_th_html_005foption_005f24.doStartTag();
/* 3627 */                                   if (_jspx_eval_html_005foption_005f24 != 0) {
/* 3628 */                                     if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3629 */                                       out = _jspx_page_context.pushBody();
/* 3630 */                                       _jspx_th_html_005foption_005f24.setBodyContent((BodyContent)out);
/* 3631 */                                       _jspx_th_html_005foption_005f24.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3634 */                                       out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.noAuthnoPriv"));
/* 3635 */                                       int evalDoAfterBody = _jspx_th_html_005foption_005f24.doAfterBody();
/* 3636 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3639 */                                     if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3640 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3643 */                                   if (_jspx_th_html_005foption_005f24.doEndTag() == 5) {
/* 3644 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24); return;
/*      */                                   }
/*      */                                   
/* 3647 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24);
/* 3648 */                                   out.write("\n\t\t\t            ");
/*      */                                   
/* 3650 */                                   OptionTag _jspx_th_html_005foption_005f25 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3651 */                                   _jspx_th_html_005foption_005f25.setPageContext(_jspx_page_context);
/* 3652 */                                   _jspx_th_html_005foption_005f25.setParent(_jspx_th_html_005fselect_005f2);
/*      */                                   
/* 3654 */                                   _jspx_th_html_005foption_005f25.setValue("AUTHNOPRIV");
/* 3655 */                                   int _jspx_eval_html_005foption_005f25 = _jspx_th_html_005foption_005f25.doStartTag();
/* 3656 */                                   if (_jspx_eval_html_005foption_005f25 != 0) {
/* 3657 */                                     if (_jspx_eval_html_005foption_005f25 != 1) {
/* 3658 */                                       out = _jspx_page_context.pushBody();
/* 3659 */                                       _jspx_th_html_005foption_005f25.setBodyContent((BodyContent)out);
/* 3660 */                                       _jspx_th_html_005foption_005f25.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3663 */                                       out.write(32);
/* 3664 */                                       out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.authNoPriv"));
/* 3665 */                                       int evalDoAfterBody = _jspx_th_html_005foption_005f25.doAfterBody();
/* 3666 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3669 */                                     if (_jspx_eval_html_005foption_005f25 != 1) {
/* 3670 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3673 */                                   if (_jspx_th_html_005foption_005f25.doEndTag() == 5) {
/* 3674 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25); return;
/*      */                                   }
/*      */                                   
/* 3677 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25);
/* 3678 */                                   out.write("\n\t\t\t            ");
/*      */                                   
/* 3680 */                                   OptionTag _jspx_th_html_005foption_005f26 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3681 */                                   _jspx_th_html_005foption_005f26.setPageContext(_jspx_page_context);
/* 3682 */                                   _jspx_th_html_005foption_005f26.setParent(_jspx_th_html_005fselect_005f2);
/*      */                                   
/* 3684 */                                   _jspx_th_html_005foption_005f26.setValue("AUTHPRIV");
/* 3685 */                                   int _jspx_eval_html_005foption_005f26 = _jspx_th_html_005foption_005f26.doStartTag();
/* 3686 */                                   if (_jspx_eval_html_005foption_005f26 != 0) {
/* 3687 */                                     if (_jspx_eval_html_005foption_005f26 != 1) {
/* 3688 */                                       out = _jspx_page_context.pushBody();
/* 3689 */                                       _jspx_th_html_005foption_005f26.setBodyContent((BodyContent)out);
/* 3690 */                                       _jspx_th_html_005foption_005f26.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3693 */                                       out.write(32);
/* 3694 */                                       out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.authPriv"));
/* 3695 */                                       int evalDoAfterBody = _jspx_th_html_005foption_005f26.doAfterBody();
/* 3696 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3699 */                                     if (_jspx_eval_html_005foption_005f26 != 1) {
/* 3700 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3703 */                                   if (_jspx_th_html_005foption_005f26.doEndTag() == 5) {
/* 3704 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26); return;
/*      */                                   }
/*      */                                   
/* 3707 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26);
/* 3708 */                                   out.write("\n\t\t\t          ");
/* 3709 */                                   int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 3710 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 3713 */                                 if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 3714 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 3717 */                               if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 3718 */                                 this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2); return;
/*      */                               }
/*      */                               
/* 3721 */                               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2);
/* 3722 */                               out.write("\n\t         </td>\n\n\t    </tr>\n            <TR>\n                <TD width=\"25%\" height=\"28\" class=\"bodytext label-align\">");
/* 3723 */                               out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.userName"));
/* 3724 */                               out.write("<span class=\"mandatory\">*</span>\n                </TD>\n                <TD width=\"15%\" height=\"28\" colspan=\"2\">");
/* 3725 */                               if (_jspx_meth_html_005ftext_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 3727 */                               out.write("\n                </TD>\n                <TD width=\"8%\" height=\"28\" class=\"bodytext\">");
/* 3728 */                               out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.contextName"));
/* 3729 */                               out.write("<span class=\"mandatory\">*</span>\n                </TD>\n                <TD width=\"56%\" height=\"28\" colspan=\"2\">");
/* 3730 */                               if (_jspx_meth_html_005ftext_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 3732 */                               out.write("\n                </TD>\n            </TR>\n             <TR id=\"AuthNoPrivID\" style=\"display:none\">\n                <td width=\"25%\" id=\"AuthNoPrivID3\" height=\"28\" class=\"bodytext label-align\">");
/* 3733 */                               out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.authPassword"));
/* 3734 */                               out.write("<span class=\"mandatory\">*</span>\n                </td>\n                <TD width=\"15%\" id=\"AuthNoPrivID4\" height=\"28\" colspan=\"2\">");
/* 3735 */                               if (_jspx_meth_html_005fpassword_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 3737 */                               out.write("\n                </TD>\n                <td width=\"8%\" id=\"AuthNoPrivID1\" height=\"28\" class=\"bodytext\">");
/* 3738 */                               out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.authProtocol"));
/* 3739 */                               out.write("<span class=\"mandatory\">*</span>\n                </td>\n                <TD width=\"56%\" id=\"AuthNoPrivID2\"> ");
/*      */                               
/* 3741 */                               SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 3742 */                               _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 3743 */                               _jspx_th_html_005fselect_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */                               
/* 3745 */                               _jspx_th_html_005fselect_005f3.setProperty("snmpAuthProtocol");
/*      */                               
/* 3747 */                               _jspx_th_html_005fselect_005f3.setStyleClass("formtextarea");
/*      */                               
/* 3749 */                               _jspx_th_html_005fselect_005f3.setOnchange("");
/* 3750 */                               int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 3751 */                               if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 3752 */                                 if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 3753 */                                   out = _jspx_page_context.pushBody();
/* 3754 */                                   _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 3755 */                                   _jspx_th_html_005fselect_005f3.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 3758 */                                   out.write("\n\t\t\t            ");
/*      */                                   
/* 3760 */                                   OptionTag _jspx_th_html_005foption_005f27 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3761 */                                   _jspx_th_html_005foption_005f27.setPageContext(_jspx_page_context);
/* 3762 */                                   _jspx_th_html_005foption_005f27.setParent(_jspx_th_html_005fselect_005f3);
/*      */                                   
/* 3764 */                                   _jspx_th_html_005foption_005f27.setValue("MD5");
/* 3765 */                                   int _jspx_eval_html_005foption_005f27 = _jspx_th_html_005foption_005f27.doStartTag();
/* 3766 */                                   if (_jspx_eval_html_005foption_005f27 != 0) {
/* 3767 */                                     if (_jspx_eval_html_005foption_005f27 != 1) {
/* 3768 */                                       out = _jspx_page_context.pushBody();
/* 3769 */                                       _jspx_th_html_005foption_005f27.setBodyContent((BodyContent)out);
/* 3770 */                                       _jspx_th_html_005foption_005f27.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3773 */                                       out.print(FormatUtil.getString("MD5"));
/* 3774 */                                       int evalDoAfterBody = _jspx_th_html_005foption_005f27.doAfterBody();
/* 3775 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3778 */                                     if (_jspx_eval_html_005foption_005f27 != 1) {
/* 3779 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3782 */                                   if (_jspx_th_html_005foption_005f27.doEndTag() == 5) {
/* 3783 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27); return;
/*      */                                   }
/*      */                                   
/* 3786 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27);
/* 3787 */                                   out.write("\n\t\t\t            ");
/*      */                                   
/* 3789 */                                   OptionTag _jspx_th_html_005foption_005f28 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3790 */                                   _jspx_th_html_005foption_005f28.setPageContext(_jspx_page_context);
/* 3791 */                                   _jspx_th_html_005foption_005f28.setParent(_jspx_th_html_005fselect_005f3);
/*      */                                   
/* 3793 */                                   _jspx_th_html_005foption_005f28.setValue("SHA");
/* 3794 */                                   int _jspx_eval_html_005foption_005f28 = _jspx_th_html_005foption_005f28.doStartTag();
/* 3795 */                                   if (_jspx_eval_html_005foption_005f28 != 0) {
/* 3796 */                                     if (_jspx_eval_html_005foption_005f28 != 1) {
/* 3797 */                                       out = _jspx_page_context.pushBody();
/* 3798 */                                       _jspx_th_html_005foption_005f28.setBodyContent((BodyContent)out);
/* 3799 */                                       _jspx_th_html_005foption_005f28.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3802 */                                       out.write(32);
/* 3803 */                                       out.print(FormatUtil.getString("SHA"));
/* 3804 */                                       int evalDoAfterBody = _jspx_th_html_005foption_005f28.doAfterBody();
/* 3805 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3808 */                                     if (_jspx_eval_html_005foption_005f28 != 1) {
/* 3809 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3812 */                                   if (_jspx_th_html_005foption_005f28.doEndTag() == 5) {
/* 3813 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28); return;
/*      */                                   }
/*      */                                   
/* 3816 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28);
/* 3817 */                                   out.write("\n\t\t\t          ");
/* 3818 */                                   int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 3819 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 3822 */                                 if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 3823 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 3826 */                               if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 3827 */                                 this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f3); return;
/*      */                               }
/*      */                               
/* 3830 */                               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f3);
/* 3831 */                               out.write("\n                </TD>\n\n            </TR>\n            <tr id=\"AuthPrivID\" style=\"display:none\">\n                 <td id=\"AuthPrivID1\" height=\"28\" class=\"bodytext label-align\">");
/* 3832 */                               out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.privPassword"));
/* 3833 */                               out.write("<span class=\"mandatory\">*</span>\n                </td>\n                <TD id=\"AuthPrivID2\" height=\"28\" colspan=\"3\">");
/* 3834 */                               if (_jspx_meth_html_005fpassword_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                 return;
/* 3836 */                               out.write("\n                </TD>\n            </tr>\n\n            <tr height=\"6\"><td colspan=\"4\"></td>\n                </tr>\n                <tr>\n                \t<td class=\"bodytext label-align\"></td>\n                    <td align=\"left\" colspan=\"3\">\n                        <input name=\"testCredentialButton\" type=\"button\" class=\"buttons btn_test\" value=\"");
/* 3837 */                               out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.testCredential"));
/* 3838 */                               out.write("\" onClick=\"javascript:validateAndPerformTestCredential('v3');\">\n                    </td>\n                </tr>\n\n            </table>\n         </div>\n</td>\n\n</tr>\n\n</table>\n\n\n\n      <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrbborder\">\n        <tr>\n\n    <td width=\"25%\" height=\"29\" class=\"tablebottom\">&nbsp;</td>\n\n    <td width=\"75%\" class=\"tablebottom\" > ");
/*      */                               
/* 3840 */                               ButtonTag _jspx_th_html_005fbutton_005f0 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 3841 */                               _jspx_th_html_005fbutton_005f0.setPageContext(_jspx_page_context);
/* 3842 */                               _jspx_th_html_005fbutton_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                               
/* 3844 */                               _jspx_th_html_005fbutton_005f0.setOnclick("return validateAndSubmit();");
/*      */                               
/* 3846 */                               _jspx_th_html_005fbutton_005f0.setStyleClass("buttons btn_highlt");
/*      */                               
/* 3848 */                               _jspx_th_html_005fbutton_005f0.setProperty("submitbutton3");
/*      */                               
/* 3850 */                               _jspx_th_html_005fbutton_005f0.setValue(FormatUtil.getString("am.webclient.hostdiscovery.qengine.button.update"));
/* 3851 */                               int _jspx_eval_html_005fbutton_005f0 = _jspx_th_html_005fbutton_005f0.doStartTag();
/* 3852 */                               if (_jspx_th_html_005fbutton_005f0.doEndTag() == 5) {
/* 3853 */                                 this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0); return;
/*      */                               }
/*      */                               
/* 3856 */                               this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0);
/* 3857 */                               out.write("\n    <input type=\"button\" Class=\"buttons btn_link\" value='");
/* 3858 */                               out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 3859 */                               out.write("' onClick=\"javascript:toggleDiv('edit')\">\n    </td>\n          </tr>\n        </table>\n    </center>\n");
/* 3860 */                               int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 3861 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3865 */                           if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 3866 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                           }
/*      */                           
/* 3869 */                           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 3870 */                           out.write("\n   ");
/*      */ 
/*      */                         }
/* 3873 */                         else if (request.getParameter("actiononServices") != null)
/*      */                         {
/* 3875 */                           String msid = request.getParameter("mid");
/*      */                           
/* 3877 */                           out.write("\n\n\t             ");
/*      */                           
/* 3879 */                           FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 3880 */                           _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/* 3881 */                           _jspx_th_html_005fform_005f1.setParent(null);
/*      */                           
/* 3883 */                           _jspx_th_html_005fform_005f1.setAction("/HostResource");
/*      */                           
/* 3885 */                           _jspx_th_html_005fform_005f1.setStyle("display:inline");
/* 3886 */                           int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/* 3887 */                           if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */                             for (;;) {
/* 3889 */                               out.write("\n\t                <input type=\"hidden\" name=\"haid\" value=\"");
/* 3890 */                               out.print(request.getParameter("haid"));
/* 3891 */                               out.write("\"/>\n\t\t\t<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 3892 */                               out.print((String)request.getAttribute("resourceid"));
/* 3893 */                               out.write("\"/>\n\t\t\t<input type=\"hidden\" size=\"15\" name=\"configured\" value=\"true\"/>\n\t\t\t<input type=\"hidden\" name=\"appName\" value=\"");
/* 3894 */                               out.print(request.getParameter("appName"));
/* 3895 */                               out.write("\"/>\n\t\t\t<input type=\"hidden\" name=\"serviceid\" value=\"");
/* 3896 */                               out.print(request.getParameter("serviceid"));
/* 3897 */                               out.write("\"/>\n\t\t\t<input type=\"hidden\" name=\"actiononServices\" value=\"EditSave\"/>\n\t\t\t<input type=\"hidden\" name=\"name\" value=\"");
/* 3898 */                               out.print(resourcename);
/* 3899 */                               out.write("\"/>\n\t\t\t<input type=\"hidden\" name=\"msid\" value=\"");
/* 3900 */                               out.print(msid);
/* 3901 */                               out.write("\"/>\n\n\t             <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\t\t\t<tr>\n\t\t\t<td width=\"50%\" height=\"29\" class=\"tableheading\">&nbsp;");
/* 3902 */                               out.print(FormatUtil.getString("am.webclient.hostresourceconfig.editservice"));
/* 3903 */                               out.write("</td>\n\t\t\t<td width=\"50%\" height=\"29\" class=\"tableheading\">&nbsp;\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n\t\t\t<tr>\n\t\t\t<td width=\"25%\" class=\"bodytext label-align\">\n\t\t\t");
/* 3904 */                               out.print(FormatUtil.getString("am.webclient.newaction.displayname"));
/* 3905 */                               out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t<td width=\"75%\" align=\"left\">\n\t\t\t");
/* 3906 */                               if (_jspx_meth_html_005ftext_005f8(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                 return;
/* 3908 */                               out.write("\n\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td width=\"25%\" class=\"bodytext label-align\">\n\t\t\t ");
/* 3909 */                               out.print(FormatUtil.getString("am.webclient.hostResource.servers.servicename"));
/* 3910 */                               out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t<td width=\"75%\" align=\"left\">\n\t\t\t");
/* 3911 */                               if (_jspx_meth_html_005ftext_005f9(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                 return;
/* 3913 */                               out.write("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n\t\t\t</td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrbborder\">\n\t\t\t<tr>\n\t\t\t<td width=\"25%\" height=\"29\" class=\"Tablebottom\">&nbsp;</td>\n\t\t\t<td width=\"75%\" class=\"Tablebottom\" >\n\t\t\t");
/*      */                               
/* 3915 */                               ButtonTag _jspx_th_html_005fbutton_005f1 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 3916 */                               _jspx_th_html_005fbutton_005f1.setPageContext(_jspx_page_context);
/* 3917 */                               _jspx_th_html_005fbutton_005f1.setParent(_jspx_th_html_005fform_005f1);
/*      */                               
/* 3919 */                               _jspx_th_html_005fbutton_005f1.setOnclick("return validateAndSubmitService();");
/*      */                               
/* 3921 */                               _jspx_th_html_005fbutton_005f1.setStyleClass("buttons btn_highlt");
/*      */                               
/* 3923 */                               _jspx_th_html_005fbutton_005f1.setProperty("submitbutton4");
/*      */                               
/* 3925 */                               _jspx_th_html_005fbutton_005f1.setValue(FormatUtil.getString("am.webclient.common.update.text"));
/* 3926 */                               int _jspx_eval_html_005fbutton_005f1 = _jspx_th_html_005fbutton_005f1.doStartTag();
/* 3927 */                               if (_jspx_th_html_005fbutton_005f1.doEndTag() == 5) {
/* 3928 */                                 this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f1); return;
/*      */                               }
/*      */                               
/* 3931 */                               this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f1);
/* 3932 */                               out.write("\n\t\t\t");
/*      */                               
/* 3934 */                               ButtonTag _jspx_th_html_005fbutton_005f2 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 3935 */                               _jspx_th_html_005fbutton_005f2.setPageContext(_jspx_page_context);
/* 3936 */                               _jspx_th_html_005fbutton_005f2.setParent(_jspx_th_html_005fform_005f1);
/*      */                               
/* 3938 */                               _jspx_th_html_005fbutton_005f2.setOnclick("return fnformsubmit1();");
/*      */                               
/* 3940 */                               _jspx_th_html_005fbutton_005f2.setStyleClass("buttons btn_link");
/*      */                               
/* 3942 */                               _jspx_th_html_005fbutton_005f2.setProperty("submitbutton5");
/*      */                               
/* 3944 */                               _jspx_th_html_005fbutton_005f2.setValue(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 3945 */                               int _jspx_eval_html_005fbutton_005f2 = _jspx_th_html_005fbutton_005f2.doStartTag();
/* 3946 */                               if (_jspx_th_html_005fbutton_005f2.doEndTag() == 5) {
/* 3947 */                                 this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f2); return;
/*      */                               }
/*      */                               
/* 3950 */                               this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f2);
/* 3951 */                               out.write("</td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t");
/* 3952 */                               int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/* 3953 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3957 */                           if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/* 3958 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*      */                           }
/*      */                           
/* 3961 */                           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f1);
/* 3962 */                           out.write(10);
/* 3963 */                           out.write(9);
/*      */ 
/*      */                         }
/*      */                         else
/*      */                         {
/*      */ 
/* 3969 */                           out.write("\n\t\t\t");
/*      */                           
/* 3971 */                           FormTag _jspx_th_html_005fform_005f2 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 3972 */                           _jspx_th_html_005fform_005f2.setPageContext(_jspx_page_context);
/* 3973 */                           _jspx_th_html_005fform_005f2.setParent(null);
/*      */                           
/* 3975 */                           _jspx_th_html_005fform_005f2.setAction("/HostResource");
/*      */                           
/* 3977 */                           _jspx_th_html_005fform_005f2.setStyle("display:inline");
/* 3978 */                           int _jspx_eval_html_005fform_005f2 = _jspx_th_html_005fform_005f2.doStartTag();
/* 3979 */                           if (_jspx_eval_html_005fform_005f2 != 0) {
/*      */                             for (;;) {
/* 3981 */                               out.write("\n\t\t\t<input name=\"addProcess\" type=\"hidden\" value=\"true\" size=\"15\"/>\n\t\t\t");
/*      */                               
/* 3983 */                               IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3984 */                               _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3985 */                               _jspx_th_c_005fif_005f1.setParent(_jspx_th_html_005fform_005f2);
/*      */                               
/* 3987 */                               _jspx_th_c_005fif_005f1.setTest("${!empty param.editProcess}");
/* 3988 */                               int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3989 */                               if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                 for (;;) {
/* 3991 */                                   out.write("\n\t\t\t<input  type=\"hidden\" name=\"edit\" value=\"true\" size=\"15\"/>\n\t\t\t<input name=\"processid\" type=\"hidden\"  value=\"");
/* 3992 */                                   out.print(request.getParameter("processid"));
/* 3993 */                                   out.write("\" size=\"15\"/>\n\t\t\t");
/* 3994 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3995 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3999 */                               if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 4000 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                               }
/*      */                               
/* 4003 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4004 */                               out.write("\n\t\t\t<input type=\"hidden\" name=\"haid\" value=\"");
/* 4005 */                               out.print(request.getParameter("haid"));
/* 4006 */                               out.write("\"/>\n\t\t\t<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 4007 */                               out.print((String)request.getAttribute("resourceid"));
/* 4008 */                               out.write("\"/>\n\t\t\t<input type=\"hidden\" size=\"15\" name=\"configured\" value=\"true\"/>\n\t\t\t<input type=\"hidden\" name=\"appName\" value=\"");
/* 4009 */                               out.print(request.getParameter("appName"));
/* 4010 */                               out.write("\"/>\n\t\t\t<input type=\"hidden\" name=\"sqlmanid\" value=\"");
/* 4011 */                               out.print(request.getParameter("sqlmanid"));
/* 4012 */                               out.write("\"/>\n\n\n\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\t\t\t<tr>\n\t\t\t");
/*      */                               
/* 4014 */                               IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4015 */                               _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 4016 */                               _jspx_th_c_005fif_005f2.setParent(_jspx_th_html_005fform_005f2);
/*      */                               
/* 4018 */                               _jspx_th_c_005fif_005f2.setTest("${!empty param.editProcess}");
/* 4019 */                               int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 4020 */                               if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                                 for (;;) {
/* 4022 */                                   out.write("\n\t\t\t<td width=\"50%\" height=\"29\" class=\"tableheading\">&nbsp;");
/* 4023 */                                   out.print(FormatUtil.getString("am.webclient.hostresourceconfig.editprocess"));
/* 4024 */                                   out.write("</td>\n\t\t\t<td width=\"50%\" height=\"29\" class=\"tableheading\">&nbsp;\n\t\t\t");
/* 4025 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 4026 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4030 */                               if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 4031 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                               }
/*      */                               
/* 4034 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 4035 */                               out.write("\n\t\t\t");
/*      */                               
/* 4037 */                               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4038 */                               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 4039 */                               _jspx_th_c_005fif_005f3.setParent(_jspx_th_html_005fform_005f2);
/*      */                               
/* 4041 */                               _jspx_th_c_005fif_005f3.setTest("${empty param.editProcess}");
/* 4042 */                               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 4043 */                               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                 for (;;) {
/* 4045 */                                   out.write("\n\t\t\t<td width=\"50%\" height=\"29\" class=\"tableheading\">&nbsp;");
/* 4046 */                                   out.print(FormatUtil.getString("am.webclient.hostresourceconfig.addprocess"));
/* 4047 */                                   out.write("</td>\n\t\t\t<td width=\"50%\" height=\"29\" class=\"tableheading\"  onClick=\"javascript:hideDiv('addprocess')\" align=\"right\">\n\t\t\t<span class=\"bodytextboldwhiteun\" ><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\">Hide</span>\n\t\t\t</td>\n\t\t\t");
/* 4048 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 4049 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4053 */                               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 4054 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                               }
/*      */                               
/* 4057 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4058 */                               out.write("\n\t\t\t</td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t<table width=\"99%\" border=0 cellpadding=5 cellspacing=0  valign=center class=\"lrborder\">\n\t\t\t<tr>\n\t\t\t<td width=\"25%\" class=\"bodytext label-align\"> <input type=\"hidden\" name=\"name\" value=\"");
/* 4059 */                               out.print(resourcename);
/* 4060 */                               out.write("\" size=\"15\"/>\n\t\t\t ");
/* 4061 */                               out.print(FormatUtil.getString("am.webclient.newaction.displayname"));
/* 4062 */                               out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t<td width=\"75%\">\n\t\t\t");
/* 4063 */                               if (_jspx_meth_c_005fif_005f4(_jspx_th_html_005fform_005f2, _jspx_page_context))
/*      */                                 return;
/* 4065 */                               out.write("\n\t\t\t");
/* 4066 */                               if (_jspx_meth_c_005fif_005f5(_jspx_th_html_005fform_005f2, _jspx_page_context))
/*      */                                 return;
/* 4068 */                               out.write("\n\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td width=\"25%\" class=\"bodytext label-align\">\n\t\t\t ");
/* 4069 */                               out.print(FormatUtil.getString("am.webclient.hostresourceconfig.processname"));
/* 4070 */                               out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t<td width=\"75%\">\n\t\t\t");
/* 4071 */                               if (_jspx_meth_c_005fchoose_005f1(_jspx_th_html_005fform_005f2, _jspx_page_context))
/*      */                                 return;
/* 4073 */                               out.write("\n\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td width=\"25%\" class=\"bodytext label-align\">\n\t\t\t ");
/* 4074 */                               out.print(FormatUtil.getString("am.webclient.hostresourceconfig.commandandarg"));
/* 4075 */                               out.write(" </td>\n\t\t\t<td width=\"75%\">\n\t\t\t");
/* 4076 */                               if (_jspx_meth_c_005fchoose_005f2(_jspx_th_html_005fform_005f2, _jspx_page_context))
/*      */                                 return;
/* 4078 */                               out.write("\t\t\n\t\t\t</td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrbborder\">\n\t\t\t<tr>\n\t\t\t<td width=\"25%\" height=\"29\" class=\"tablebottom\">&nbsp;</td>\n\t\t\t<td width=\"75%\" class=\"tablebottom\" >\n\t\t\t");
/*      */                               
/* 4080 */                               IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4081 */                               _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 4082 */                               _jspx_th_c_005fif_005f6.setParent(_jspx_th_html_005fform_005f2);
/*      */                               
/* 4084 */                               _jspx_th_c_005fif_005f6.setTest("${!empty param.editProcess}");
/* 4085 */                               int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 4086 */                               if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                 for (;;) {
/* 4088 */                                   out.write("\n\t\t\t");
/*      */                                   
/* 4090 */                                   ButtonTag _jspx_th_html_005fbutton_005f3 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 4091 */                                   _jspx_th_html_005fbutton_005f3.setPageContext(_jspx_page_context);
/* 4092 */                                   _jspx_th_html_005fbutton_005f3.setParent(_jspx_th_c_005fif_005f6);
/*      */                                   
/* 4094 */                                   _jspx_th_html_005fbutton_005f3.setOnclick("return validateAndSubmit2();");
/*      */                                   
/* 4096 */                                   _jspx_th_html_005fbutton_005f3.setStyleClass("buttons btn_highlt");
/*      */                                   
/* 4098 */                                   _jspx_th_html_005fbutton_005f3.setProperty("submitbutton4");
/*      */                                   
/* 4100 */                                   _jspx_th_html_005fbutton_005f3.setValue(FormatUtil.getString("am.webclient.common.update.text"));
/* 4101 */                                   int _jspx_eval_html_005fbutton_005f3 = _jspx_th_html_005fbutton_005f3.doStartTag();
/* 4102 */                                   if (_jspx_th_html_005fbutton_005f3.doEndTag() == 5) {
/* 4103 */                                     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f3); return;
/*      */                                   }
/*      */                                   
/* 4106 */                                   this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f3);
/* 4107 */                                   out.write("\n\t\t\t");
/* 4108 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 4109 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4113 */                               if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 4114 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                               }
/*      */                               
/* 4117 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 4118 */                               out.write("\n\t\t\t");
/*      */                               
/* 4120 */                               IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4121 */                               _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 4122 */                               _jspx_th_c_005fif_005f7.setParent(_jspx_th_html_005fform_005f2);
/*      */                               
/* 4124 */                               _jspx_th_c_005fif_005f7.setTest("${empty param.editProcess}");
/* 4125 */                               int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 4126 */                               if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                 for (;;) {
/* 4128 */                                   out.write("\n\t\t\t");
/*      */                                   
/* 4130 */                                   ButtonTag _jspx_th_html_005fbutton_005f4 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 4131 */                                   _jspx_th_html_005fbutton_005f4.setPageContext(_jspx_page_context);
/* 4132 */                                   _jspx_th_html_005fbutton_005f4.setParent(_jspx_th_c_005fif_005f7);
/*      */                                   
/* 4134 */                                   _jspx_th_html_005fbutton_005f4.setOnclick("return validateAndSubmit1();");
/*      */                                   
/* 4136 */                                   _jspx_th_html_005fbutton_005f4.setStyleClass("buttons btn_highlt");
/*      */                                   
/* 4138 */                                   _jspx_th_html_005fbutton_005f4.setProperty("submitbutton3");
/*      */                                   
/* 4140 */                                   _jspx_th_html_005fbutton_005f4.setValue(FormatUtil.getString("am.webclient.hostresourceconfig.addprocess"));
/* 4141 */                                   int _jspx_eval_html_005fbutton_005f4 = _jspx_th_html_005fbutton_005f4.doStartTag();
/* 4142 */                                   if (_jspx_th_html_005fbutton_005f4.doEndTag() == 5) {
/* 4143 */                                     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f4); return;
/*      */                                   }
/*      */                                   
/* 4146 */                                   this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f4);
/* 4147 */                                   out.write("\n\t\t\t");
/* 4148 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 4149 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4153 */                               if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 4154 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                               }
/*      */                               
/* 4157 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 4158 */                               out.write("\n\t\t\t");
/*      */                               
/* 4160 */                               IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4161 */                               _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 4162 */                               _jspx_th_c_005fif_005f8.setParent(_jspx_th_html_005fform_005f2);
/*      */                               
/* 4164 */                               _jspx_th_c_005fif_005f8.setTest("${empty param.editProcess}");
/* 4165 */                               int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 4166 */                               if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                 for (;;) {
/* 4168 */                                   out.write("\n\t\t\t<input type=\"reset\" class=\"buttons btn_link\" value='");
/* 4169 */                                   out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 4170 */                                   out.write("' onClick=\"javascript:toggleDiv('addprocess')\"></td>\n\t\t\t");
/* 4171 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 4172 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4176 */                               if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 4177 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                               }
/*      */                               
/* 4180 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 4181 */                               out.write("\n\t\t\t");
/*      */                               
/* 4183 */                               IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4184 */                               _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 4185 */                               _jspx_th_c_005fif_005f9.setParent(_jspx_th_html_005fform_005f2);
/*      */                               
/* 4187 */                               _jspx_th_c_005fif_005f9.setTest("${!empty param.editProcess}");
/* 4188 */                               int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 4189 */                               if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                 for (;;) {
/* 4191 */                                   out.write("\n\n\n\t\t\t");
/*      */                                   
/* 4193 */                                   if (com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                                   {
/*      */ 
/* 4196 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 4198 */                                     ButtonTag _jspx_th_html_005fbutton_005f5 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 4199 */                                     _jspx_th_html_005fbutton_005f5.setPageContext(_jspx_page_context);
/* 4200 */                                     _jspx_th_html_005fbutton_005f5.setParent(_jspx_th_c_005fif_005f9);
/*      */                                     
/* 4202 */                                     _jspx_th_html_005fbutton_005f5.setOnclick("return fnformsubmit2();");
/*      */                                     
/* 4204 */                                     _jspx_th_html_005fbutton_005f5.setStyleClass("buttons btn_link");
/*      */                                     
/* 4206 */                                     _jspx_th_html_005fbutton_005f5.setProperty("submitbutton5");
/*      */                                     
/* 4208 */                                     _jspx_th_html_005fbutton_005f5.setValue(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 4209 */                                     int _jspx_eval_html_005fbutton_005f5 = _jspx_th_html_005fbutton_005f5.doStartTag();
/* 4210 */                                     if (_jspx_th_html_005fbutton_005f5.doEndTag() == 5) {
/* 4211 */                                       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f5); return;
/*      */                                     }
/*      */                                     
/* 4214 */                                     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f5);
/* 4215 */                                     out.write("</td>\n\t\t\t");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/*      */ 
/* 4221 */                                     out.write("\n\t\t\t\t ");
/*      */                                     
/* 4223 */                                     ButtonTag _jspx_th_html_005fbutton_005f6 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 4224 */                                     _jspx_th_html_005fbutton_005f6.setPageContext(_jspx_page_context);
/* 4225 */                                     _jspx_th_html_005fbutton_005f6.setParent(_jspx_th_c_005fif_005f9);
/*      */                                     
/* 4227 */                                     _jspx_th_html_005fbutton_005f6.setOnclick("return fnformsubmit1();");
/*      */                                     
/* 4229 */                                     _jspx_th_html_005fbutton_005f6.setStyleClass("buttons btn_link");
/*      */                                     
/* 4231 */                                     _jspx_th_html_005fbutton_005f6.setProperty("submitbutton5");
/*      */                                     
/* 4233 */                                     _jspx_th_html_005fbutton_005f6.setValue(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 4234 */                                     int _jspx_eval_html_005fbutton_005f6 = _jspx_th_html_005fbutton_005f6.doStartTag();
/* 4235 */                                     if (_jspx_th_html_005fbutton_005f6.doEndTag() == 5) {
/* 4236 */                                       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f6); return;
/*      */                                     }
/*      */                                     
/* 4239 */                                     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f6);
/* 4240 */                                     out.write("</td>\n\t\t\t");
/*      */                                   }
/*      */                                   
/*      */ 
/* 4244 */                                   out.write("\n\n\n\t\t\t");
/* 4245 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 4246 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4250 */                               if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 4251 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                               }
/*      */                               
/* 4254 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 4255 */                               out.write("\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t");
/* 4256 */                               int evalDoAfterBody = _jspx_th_html_005fform_005f2.doAfterBody();
/* 4257 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4261 */                           if (_jspx_th_html_005fform_005f2.doEndTag() == 5) {
/* 4262 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f2); return;
/*      */                           }
/*      */                           
/* 4265 */                           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f2);
/* 4266 */                           out.write(10);
/* 4267 */                           out.write(10);
/* 4268 */                           out.write(9);
/*      */                         }
/*      */                         
/*      */ 
/* 4272 */                         out.write(10);
/* 4273 */                         out.write(10);
/* 4274 */                         out.write(10);
/*      */                       }
/* 4276 */                     } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4277 */         out = _jspx_out;
/* 4278 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4279 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 4280 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4283 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4289 */     PageContext pageContext = _jspx_page_context;
/* 4290 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4292 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4293 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 4294 */     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */     
/* 4296 */     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO,OPERATOR,USERS");
/* 4297 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 4298 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */       for (;;) {
/* 4300 */         out.write("\n\t\tchangeport();\n\t\t");
/* 4301 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 4302 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4306 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 4307 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 4308 */       return true;
/*      */     }
/* 4310 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 4311 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4316 */     PageContext pageContext = _jspx_page_context;
/* 4317 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4319 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4320 */     _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 4321 */     _jspx_th_logic_005fnotPresent_005f2.setParent(null);
/*      */     
/* 4323 */     _jspx_th_logic_005fnotPresent_005f2.setRole("ENTERPRISEADMIN");
/* 4324 */     int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 4325 */     if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */       for (;;) {
/* 4327 */         out.write("\n\t\tcheck='true';\t//NO I18N\n\t");
/* 4328 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 4329 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4333 */     if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 4334 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 4335 */       return true;
/*      */     }
/* 4337 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 4338 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4343 */     PageContext pageContext = _jspx_page_context;
/* 4344 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4346 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4347 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 4348 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 4350 */     _jspx_th_logic_005fpresent_005f0.setRole("ENTERPRISEADMIN");
/* 4351 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 4352 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 4354 */         out.write("\n\t\tif(document.forms[1].resourceid.value<adminRange)\n\t\t{\n\t\t\tcheck='true';\t//NO I18N\n\t\t}\n\t");
/* 4355 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 4356 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4360 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 4361 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4362 */       return true;
/*      */     }
/* 4364 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4365 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4370 */     PageContext pageContext = _jspx_page_context;
/* 4371 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4373 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4374 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 4375 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 4377 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 4378 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 4379 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 4381 */         out.write("\n\talertUser();\n\treturn;\n\t");
/* 4382 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 4383 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4387 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 4388 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 4389 */       return true;
/*      */     }
/* 4391 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 4392 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4397 */     PageContext pageContext = _jspx_page_context;
/* 4398 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4400 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4401 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 4402 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4404 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 4406 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/*      */     
/* 4408 */     _jspx_th_html_005ftext_005f0.setSize("35");
/* 4409 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 4410 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 4411 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4412 */       return true;
/*      */     }
/* 4414 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4415 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4420 */     PageContext pageContext = _jspx_page_context;
/* 4421 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4423 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 4424 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 4425 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4427 */     _jspx_th_html_005fhidden_005f0.setProperty("hostos");
/* 4428 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 4429 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 4430 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 4431 */       return true;
/*      */     }
/* 4433 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 4434 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4439 */     PageContext pageContext = _jspx_page_context;
/* 4440 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4442 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4443 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 4444 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4446 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.reconfigure}");
/* 4447 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 4448 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 4450 */         out.write("\n\t\t\t<input type=\"hidden\" size=\"15\" name=\"configured\" value=\"true\"/>\n\t\t\t");
/* 4451 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 4452 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4456 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 4457 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4458 */       return true;
/*      */     }
/* 4460 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4461 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4466 */     PageContext pageContext = _jspx_page_context;
/* 4467 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4469 */     CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.get(CheckboxTag.class);
/* 4470 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 4471 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4473 */     _jspx_th_html_005fcheckbox_005f0.setProperty("eventlog_status");
/*      */     
/* 4475 */     _jspx_th_html_005fcheckbox_005f0.setValue("true");
/* 4476 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 4477 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 4478 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 4479 */       return true;
/*      */     }
/* 4481 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 4482 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4487 */     PageContext pageContext = _jspx_page_context;
/* 4488 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4490 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4491 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 4492 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4494 */     _jspx_th_html_005ftext_005f1.setProperty("port");
/*      */     
/* 4496 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/*      */     
/* 4498 */     _jspx_th_html_005ftext_005f1.setSize("7");
/* 4499 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 4500 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 4501 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4502 */       return true;
/*      */     }
/* 4504 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4505 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4510 */     PageContext pageContext = _jspx_page_context;
/* 4511 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4513 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4514 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 4515 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 4516 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 4517 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 4519 */         out.write("\n\t\t\t\t\t");
/* 4520 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 4521 */           return true;
/* 4522 */         out.write("\n\t\t\t\t\t");
/* 4523 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 4524 */           return true;
/* 4525 */         out.write("\n\t\t\t\t");
/* 4526 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 4527 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4531 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 4532 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 4533 */       return true;
/*      */     }
/* 4535 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 4536 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4541 */     PageContext pageContext = _jspx_page_context;
/* 4542 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4544 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4545 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 4546 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 4548 */     _jspx_th_c_005fwhen_005f0.setTest("${keyBasedAuth eq true}");
/* 4549 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 4550 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 4552 */         out.write("\n\t\t\t\t\t\t<input name=\"sshPKAuth\" id=\"sshPKAuth\" onClick=\"showPrivateKey()\" type=\"checkbox\" checked>\n\t\t\t\t\t");
/* 4553 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 4554 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4558 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 4559 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 4560 */       return true;
/*      */     }
/* 4562 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 4563 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4568 */     PageContext pageContext = _jspx_page_context;
/* 4569 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4571 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4572 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 4573 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 4574 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 4575 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 4577 */         out.write("\n\t\t\t\t\t\t<input name=\"sshPKAuth\" id=\"sshPKAuth\" onClick=\"showPrivateKey()\" type=\"checkbox\">\n\t\t\t\t\t");
/* 4578 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 4579 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4583 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 4584 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 4585 */       return true;
/*      */     }
/* 4587 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 4588 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4593 */     PageContext pageContext = _jspx_page_context;
/* 4594 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4596 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4597 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 4598 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4600 */     _jspx_th_html_005ftext_005f2.setProperty("username");
/*      */     
/* 4602 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/* 4604 */     _jspx_th_html_005ftext_005f2.setSize("15");
/* 4605 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 4606 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 4607 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 4608 */       return true;
/*      */     }
/* 4610 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 4611 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4616 */     PageContext pageContext = _jspx_page_context;
/* 4617 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4619 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 4620 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 4621 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4623 */     _jspx_th_html_005fpassword_005f0.setProperty("password");
/*      */     
/* 4625 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext");
/*      */     
/* 4627 */     _jspx_th_html_005fpassword_005f0.setSize("15");
/*      */     
/* 4629 */     _jspx_th_html_005fpassword_005f0.setValue("");
/* 4630 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 4631 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 4632 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 4633 */       return true;
/*      */     }
/* 4635 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 4636 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4641 */     PageContext pageContext = _jspx_page_context;
/* 4642 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4644 */     TextareaTag _jspx_th_html_005ftextarea_005f0 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 4645 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 4646 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4648 */     _jspx_th_html_005ftextarea_005f0.setProperty("description");
/*      */     
/* 4650 */     _jspx_th_html_005ftextarea_005f0.setStyleClass("formtextarea");
/*      */     
/* 4652 */     _jspx_th_html_005ftextarea_005f0.setRows("4");
/*      */     
/* 4654 */     _jspx_th_html_005ftextarea_005f0.setCols("50");
/* 4655 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 4656 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 4657 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 4658 */       return true;
/*      */     }
/* 4660 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 4661 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4666 */     PageContext pageContext = _jspx_page_context;
/* 4667 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4669 */     PasswordTag _jspx_th_html_005fpassword_005f1 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 4670 */     _jspx_th_html_005fpassword_005f1.setPageContext(_jspx_page_context);
/* 4671 */     _jspx_th_html_005fpassword_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4673 */     _jspx_th_html_005fpassword_005f1.setProperty("passphrase");
/*      */     
/* 4675 */     _jspx_th_html_005fpassword_005f1.setStyleClass("formtext");
/*      */     
/* 4677 */     _jspx_th_html_005fpassword_005f1.setSize("15");
/*      */     
/* 4679 */     _jspx_th_html_005fpassword_005f1.setValue("");
/* 4680 */     int _jspx_eval_html_005fpassword_005f1 = _jspx_th_html_005fpassword_005f1.doStartTag();
/* 4681 */     if (_jspx_th_html_005fpassword_005f1.doEndTag() == 5) {
/* 4682 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f1);
/* 4683 */       return true;
/*      */     }
/* 4685 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f1);
/* 4686 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4691 */     PageContext pageContext = _jspx_page_context;
/* 4692 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4694 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4695 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 4696 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4698 */     _jspx_th_html_005ftext_005f3.setProperty("prompt");
/*      */     
/* 4700 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/*      */     
/* 4702 */     _jspx_th_html_005ftext_005f3.setSize("15");
/* 4703 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 4704 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 4705 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 4706 */       return true;
/*      */     }
/* 4708 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 4709 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4714 */     PageContext pageContext = _jspx_page_context;
/* 4715 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4717 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4718 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 4719 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4721 */     _jspx_th_html_005ftext_005f4.setProperty("pollinterval");
/*      */     
/* 4723 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext small");
/*      */     
/* 4725 */     _jspx_th_html_005ftext_005f4.setSize("15");
/* 4726 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 4727 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 4728 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 4729 */       return true;
/*      */     }
/* 4731 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 4732 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4737 */     PageContext pageContext = _jspx_page_context;
/* 4738 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4740 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4741 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 4742 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4744 */     _jspx_th_html_005ftext_005f5.setProperty("timeout");
/*      */     
/* 4746 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext small");
/*      */     
/* 4748 */     _jspx_th_html_005ftext_005f5.setSize("15");
/* 4749 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 4750 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 4751 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 4752 */       return true;
/*      */     }
/* 4754 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 4755 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4760 */     PageContext pageContext = _jspx_page_context;
/* 4761 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4763 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fdisabled_005fnobody.get(RadioTag.class);
/* 4764 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 4765 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4767 */     _jspx_th_html_005fradio_005f0.setProperty("snmpVersionValue");
/*      */     
/* 4769 */     _jspx_th_html_005fradio_005f0.setValue("v1v2");
/*      */     
/* 4771 */     _jspx_th_html_005fradio_005f0.setDisabled(false);
/*      */     
/* 4773 */     _jspx_th_html_005fradio_005f0.setOnclick("javascript:snmpVersionSelect('v1v2')");
/*      */     
/* 4775 */     _jspx_th_html_005fradio_005f0.setStyle("position:relative; top:3px;");
/* 4776 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 4777 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 4778 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fdisabled_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 4779 */       return true;
/*      */     }
/* 4781 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fdisabled_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 4782 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4787 */     PageContext pageContext = _jspx_page_context;
/* 4788 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4790 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4791 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 4792 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4794 */     _jspx_th_fmt_005fmessage_005f0.setKey("V1/V2");
/* 4795 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 4796 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 4797 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4798 */       return true;
/*      */     }
/* 4800 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4801 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4806 */     PageContext pageContext = _jspx_page_context;
/* 4807 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4809 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 4810 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 4811 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4813 */     _jspx_th_html_005fradio_005f1.setProperty("snmpVersionValue");
/*      */     
/* 4815 */     _jspx_th_html_005fradio_005f1.setValue("v3");
/*      */     
/* 4817 */     _jspx_th_html_005fradio_005f1.setOnclick("javascript:snmpVersionSelect('v3')");
/*      */     
/* 4819 */     _jspx_th_html_005fradio_005f1.setStyle("position:relative; top:3px;");
/* 4820 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 4821 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 4822 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 4823 */       return true;
/*      */     }
/* 4825 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 4826 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4831 */     PageContext pageContext = _jspx_page_context;
/* 4832 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4834 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4835 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 4836 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4838 */     _jspx_th_fmt_005fmessage_005f1.setKey("V3");
/* 4839 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 4840 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 4841 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4842 */       return true;
/*      */     }
/* 4844 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4845 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4850 */     PageContext pageContext = _jspx_page_context;
/* 4851 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4853 */     PasswordTag _jspx_th_html_005fpassword_005f2 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 4854 */     _jspx_th_html_005fpassword_005f2.setPageContext(_jspx_page_context);
/* 4855 */     _jspx_th_html_005fpassword_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4857 */     _jspx_th_html_005fpassword_005f2.setProperty("snmpCommunityString");
/*      */     
/* 4859 */     _jspx_th_html_005fpassword_005f2.setStyleClass("formtext");
/*      */     
/* 4861 */     _jspx_th_html_005fpassword_005f2.setSize("15");
/* 4862 */     int _jspx_eval_html_005fpassword_005f2 = _jspx_th_html_005fpassword_005f2.doStartTag();
/* 4863 */     if (_jspx_th_html_005fpassword_005f2.doEndTag() == 5) {
/* 4864 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f2);
/* 4865 */       return true;
/*      */     }
/* 4867 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f2);
/* 4868 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4873 */     PageContext pageContext = _jspx_page_context;
/* 4874 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4876 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4877 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 4878 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4880 */     _jspx_th_html_005ftext_005f6.setProperty("snmpUserName");
/*      */     
/* 4882 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext");
/*      */     
/* 4884 */     _jspx_th_html_005ftext_005f6.setSize("15");
/* 4885 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 4886 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 4887 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 4888 */       return true;
/*      */     }
/* 4890 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 4891 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4896 */     PageContext pageContext = _jspx_page_context;
/* 4897 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4899 */     TextTag _jspx_th_html_005ftext_005f7 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4900 */     _jspx_th_html_005ftext_005f7.setPageContext(_jspx_page_context);
/* 4901 */     _jspx_th_html_005ftext_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4903 */     _jspx_th_html_005ftext_005f7.setProperty("snmpContextName");
/*      */     
/* 4905 */     _jspx_th_html_005ftext_005f7.setStyleClass("formtext");
/*      */     
/* 4907 */     _jspx_th_html_005ftext_005f7.setSize("15");
/* 4908 */     int _jspx_eval_html_005ftext_005f7 = _jspx_th_html_005ftext_005f7.doStartTag();
/* 4909 */     if (_jspx_th_html_005ftext_005f7.doEndTag() == 5) {
/* 4910 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 4911 */       return true;
/*      */     }
/* 4913 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 4914 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4919 */     PageContext pageContext = _jspx_page_context;
/* 4920 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4922 */     PasswordTag _jspx_th_html_005fpassword_005f3 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 4923 */     _jspx_th_html_005fpassword_005f3.setPageContext(_jspx_page_context);
/* 4924 */     _jspx_th_html_005fpassword_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4926 */     _jspx_th_html_005fpassword_005f3.setProperty("snmpAuthPassword");
/*      */     
/* 4928 */     _jspx_th_html_005fpassword_005f3.setStyleClass("formtext");
/*      */     
/* 4930 */     _jspx_th_html_005fpassword_005f3.setSize("15");
/* 4931 */     int _jspx_eval_html_005fpassword_005f3 = _jspx_th_html_005fpassword_005f3.doStartTag();
/* 4932 */     if (_jspx_th_html_005fpassword_005f3.doEndTag() == 5) {
/* 4933 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f3);
/* 4934 */       return true;
/*      */     }
/* 4936 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f3);
/* 4937 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4942 */     PageContext pageContext = _jspx_page_context;
/* 4943 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4945 */     PasswordTag _jspx_th_html_005fpassword_005f4 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 4946 */     _jspx_th_html_005fpassword_005f4.setPageContext(_jspx_page_context);
/* 4947 */     _jspx_th_html_005fpassword_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4949 */     _jspx_th_html_005fpassword_005f4.setProperty("snmpPrivPassword");
/*      */     
/* 4951 */     _jspx_th_html_005fpassword_005f4.setStyleClass("formtext");
/*      */     
/* 4953 */     _jspx_th_html_005fpassword_005f4.setSize("15");
/* 4954 */     int _jspx_eval_html_005fpassword_005f4 = _jspx_th_html_005fpassword_005f4.doStartTag();
/* 4955 */     if (_jspx_th_html_005fpassword_005f4.doEndTag() == 5) {
/* 4956 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f4);
/* 4957 */       return true;
/*      */     }
/* 4959 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f4);
/* 4960 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f8(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4965 */     PageContext pageContext = _jspx_page_context;
/* 4966 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4968 */     TextTag _jspx_th_html_005ftext_005f8 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4969 */     _jspx_th_html_005ftext_005f8.setPageContext(_jspx_page_context);
/* 4970 */     _jspx_th_html_005ftext_005f8.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4972 */     _jspx_th_html_005ftext_005f8.setProperty("displayname");
/*      */     
/* 4974 */     _jspx_th_html_005ftext_005f8.setStyleClass("formtext");
/*      */     
/* 4976 */     _jspx_th_html_005ftext_005f8.setSize("30");
/* 4977 */     int _jspx_eval_html_005ftext_005f8 = _jspx_th_html_005ftext_005f8.doStartTag();
/* 4978 */     if (_jspx_th_html_005ftext_005f8.doEndTag() == 5) {
/* 4979 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 4980 */       return true;
/*      */     }
/* 4982 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 4983 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f9(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4988 */     PageContext pageContext = _jspx_page_context;
/* 4989 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4991 */     TextTag _jspx_th_html_005ftext_005f9 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4992 */     _jspx_th_html_005ftext_005f9.setPageContext(_jspx_page_context);
/* 4993 */     _jspx_th_html_005ftext_005f9.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4995 */     _jspx_th_html_005ftext_005f9.setProperty("processname");
/*      */     
/* 4997 */     _jspx_th_html_005ftext_005f9.setStyleClass("formtext");
/*      */     
/* 4999 */     _jspx_th_html_005ftext_005f9.setSize("30");
/* 5000 */     int _jspx_eval_html_005ftext_005f9 = _jspx_th_html_005ftext_005f9.doStartTag();
/* 5001 */     if (_jspx_th_html_005ftext_005f9.doEndTag() == 5) {
/* 5002 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/* 5003 */       return true;
/*      */     }
/* 5005 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/* 5006 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_html_005fform_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5011 */     PageContext pageContext = _jspx_page_context;
/* 5012 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5014 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5015 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 5016 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_html_005fform_005f2);
/*      */     
/* 5018 */     _jspx_th_c_005fif_005f4.setTest("${!empty param.editProcess}");
/* 5019 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 5020 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 5022 */         out.write("\n\t\t\t");
/* 5023 */         if (_jspx_meth_html_005ftext_005f10(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 5024 */           return true;
/* 5025 */         out.write("\n\t\t\t");
/* 5026 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 5027 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5031 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 5032 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 5033 */       return true;
/*      */     }
/* 5035 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 5036 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f10(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5041 */     PageContext pageContext = _jspx_page_context;
/* 5042 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5044 */     TextTag _jspx_th_html_005ftext_005f10 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5045 */     _jspx_th_html_005ftext_005f10.setPageContext(_jspx_page_context);
/* 5046 */     _jspx_th_html_005ftext_005f10.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 5048 */     _jspx_th_html_005ftext_005f10.setProperty("displayname");
/*      */     
/* 5050 */     _jspx_th_html_005ftext_005f10.setStyleClass("formtext");
/*      */     
/* 5052 */     _jspx_th_html_005ftext_005f10.setSize("30");
/* 5053 */     int _jspx_eval_html_005ftext_005f10 = _jspx_th_html_005ftext_005f10.doStartTag();
/* 5054 */     if (_jspx_th_html_005ftext_005f10.doEndTag() == 5) {
/* 5055 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f10);
/* 5056 */       return true;
/*      */     }
/* 5058 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f10);
/* 5059 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_html_005fform_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5064 */     PageContext pageContext = _jspx_page_context;
/* 5065 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5067 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5068 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 5069 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_html_005fform_005f2);
/*      */     
/* 5071 */     _jspx_th_c_005fif_005f5.setTest("${empty param.editProcess}");
/* 5072 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 5073 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 5075 */         out.write("\n\t\t\t");
/* 5076 */         if (_jspx_meth_html_005ftext_005f11(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 5077 */           return true;
/* 5078 */         out.write("\n\t\t\t");
/* 5079 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 5080 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5084 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 5085 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 5086 */       return true;
/*      */     }
/* 5088 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 5089 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f11(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5094 */     PageContext pageContext = _jspx_page_context;
/* 5095 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5097 */     TextTag _jspx_th_html_005ftext_005f11 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5098 */     _jspx_th_html_005ftext_005f11.setPageContext(_jspx_page_context);
/* 5099 */     _jspx_th_html_005ftext_005f11.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 5101 */     _jspx_th_html_005ftext_005f11.setProperty("displayname");
/*      */     
/* 5103 */     _jspx_th_html_005ftext_005f11.setStyleClass("formtext");
/*      */     
/* 5105 */     _jspx_th_html_005ftext_005f11.setSize("30");
/*      */     
/* 5107 */     _jspx_th_html_005ftext_005f11.setValue("");
/* 5108 */     int _jspx_eval_html_005ftext_005f11 = _jspx_th_html_005ftext_005f11.doStartTag();
/* 5109 */     if (_jspx_th_html_005ftext_005f11.doEndTag() == 5) {
/* 5110 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f11);
/* 5111 */       return true;
/*      */     }
/* 5113 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f11);
/* 5114 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(JspTag _jspx_th_html_005fform_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5119 */     PageContext pageContext = _jspx_page_context;
/* 5120 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5122 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5123 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 5124 */     _jspx_th_c_005fchoose_005f1.setParent((Tag)_jspx_th_html_005fform_005f2);
/* 5125 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 5126 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 5128 */         out.write("\n\t\t\t");
/* 5129 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 5130 */           return true;
/* 5131 */         out.write("\n\t\t\t");
/* 5132 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 5133 */           return true;
/* 5134 */         out.write("\n\t\t\t");
/* 5135 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 5136 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5140 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 5141 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 5142 */       return true;
/*      */     }
/* 5144 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 5145 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5150 */     PageContext pageContext = _jspx_page_context;
/* 5151 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5153 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5154 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 5155 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 5157 */     _jspx_th_c_005fwhen_005f1.setTest("${! empty param.disableEdit && param.disableEdit==true}");
/* 5158 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 5159 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 5161 */         out.write("\t\t\t\n\t\t\t");
/* 5162 */         if (_jspx_meth_html_005ftext_005f12(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 5163 */           return true;
/* 5164 */         out.write("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n\t\t\t");
/* 5165 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 5166 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5170 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 5171 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 5172 */       return true;
/*      */     }
/* 5174 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 5175 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f12(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5180 */     PageContext pageContext = _jspx_page_context;
/* 5181 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5183 */     TextTag _jspx_th_html_005ftext_005f12 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fdisabled_005fnobody.get(TextTag.class);
/* 5184 */     _jspx_th_html_005ftext_005f12.setPageContext(_jspx_page_context);
/* 5185 */     _jspx_th_html_005ftext_005f12.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 5187 */     _jspx_th_html_005ftext_005f12.setProperty("processname");
/*      */     
/* 5189 */     _jspx_th_html_005ftext_005f12.setStyleClass("formtext");
/*      */     
/* 5191 */     _jspx_th_html_005ftext_005f12.setSize("30");
/*      */     
/* 5193 */     _jspx_th_html_005ftext_005f12.setDisabled(true);
/* 5194 */     int _jspx_eval_html_005ftext_005f12 = _jspx_th_html_005ftext_005f12.doStartTag();
/* 5195 */     if (_jspx_th_html_005ftext_005f12.doEndTag() == 5) {
/* 5196 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fdisabled_005fnobody.reuse(_jspx_th_html_005ftext_005f12);
/* 5197 */       return true;
/*      */     }
/* 5199 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fdisabled_005fnobody.reuse(_jspx_th_html_005ftext_005f12);
/* 5200 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5205 */     PageContext pageContext = _jspx_page_context;
/* 5206 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5208 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5209 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 5210 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 5211 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 5212 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 5214 */         out.write("\n\t\t\t");
/* 5215 */         if (_jspx_meth_html_005ftext_005f13(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/* 5216 */           return true;
/* 5217 */         out.write("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n\t\t\t");
/* 5218 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 5219 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5223 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 5224 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 5225 */       return true;
/*      */     }
/* 5227 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 5228 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f13(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5233 */     PageContext pageContext = _jspx_page_context;
/* 5234 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5236 */     TextTag _jspx_th_html_005ftext_005f13 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5237 */     _jspx_th_html_005ftext_005f13.setPageContext(_jspx_page_context);
/* 5238 */     _jspx_th_html_005ftext_005f13.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 5240 */     _jspx_th_html_005ftext_005f13.setProperty("processname");
/*      */     
/* 5242 */     _jspx_th_html_005ftext_005f13.setStyleClass("formtext");
/*      */     
/* 5244 */     _jspx_th_html_005ftext_005f13.setSize("30");
/* 5245 */     int _jspx_eval_html_005ftext_005f13 = _jspx_th_html_005ftext_005f13.doStartTag();
/* 5246 */     if (_jspx_th_html_005ftext_005f13.doEndTag() == 5) {
/* 5247 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f13);
/* 5248 */       return true;
/*      */     }
/* 5250 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f13);
/* 5251 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_html_005fform_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5256 */     PageContext pageContext = _jspx_page_context;
/* 5257 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5259 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5260 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 5261 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_html_005fform_005f2);
/* 5262 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 5263 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 5265 */         out.write("\n\t\t\t");
/* 5266 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 5267 */           return true;
/* 5268 */         out.write("\n\t\t\t");
/* 5269 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 5270 */           return true;
/* 5271 */         out.write("\n\t\t\t");
/* 5272 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 5273 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5277 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 5278 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 5279 */       return true;
/*      */     }
/* 5281 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 5282 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5287 */     PageContext pageContext = _jspx_page_context;
/* 5288 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5290 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5291 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 5292 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 5294 */     _jspx_th_c_005fwhen_005f2.setTest("${! empty param.disableEdit && param.disableEdit==true}");
/* 5295 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 5296 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 5298 */         out.write("\t\n\t\t\t");
/* 5299 */         if (_jspx_meth_html_005ftext_005f14(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/* 5300 */           return true;
/* 5301 */         out.write("\n\t\t\t");
/* 5302 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 5303 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5307 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 5308 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 5309 */       return true;
/*      */     }
/* 5311 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 5312 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f14(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5317 */     PageContext pageContext = _jspx_page_context;
/* 5318 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5320 */     TextTag _jspx_th_html_005ftext_005f14 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fdisabled_005fnobody.get(TextTag.class);
/* 5321 */     _jspx_th_html_005ftext_005f14.setPageContext(_jspx_page_context);
/* 5322 */     _jspx_th_html_005ftext_005f14.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 5324 */     _jspx_th_html_005ftext_005f14.setProperty("command");
/*      */     
/* 5326 */     _jspx_th_html_005ftext_005f14.setStyleClass("formtext");
/*      */     
/* 5328 */     _jspx_th_html_005ftext_005f14.setSize("60");
/*      */     
/* 5330 */     _jspx_th_html_005ftext_005f14.setDisabled(true);
/* 5331 */     int _jspx_eval_html_005ftext_005f14 = _jspx_th_html_005ftext_005f14.doStartTag();
/* 5332 */     if (_jspx_th_html_005ftext_005f14.doEndTag() == 5) {
/* 5333 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fdisabled_005fnobody.reuse(_jspx_th_html_005ftext_005f14);
/* 5334 */       return true;
/*      */     }
/* 5336 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fdisabled_005fnobody.reuse(_jspx_th_html_005ftext_005f14);
/* 5337 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5342 */     PageContext pageContext = _jspx_page_context;
/* 5343 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5345 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5346 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 5347 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 5348 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 5349 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 5351 */         out.write("\n\t\t\t");
/* 5352 */         if (_jspx_meth_html_005ftext_005f15(_jspx_th_c_005fotherwise_005f2, _jspx_page_context))
/* 5353 */           return true;
/* 5354 */         out.write("\n\t\t\t");
/* 5355 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 5356 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5360 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 5361 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 5362 */       return true;
/*      */     }
/* 5364 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 5365 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f15(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5370 */     PageContext pageContext = _jspx_page_context;
/* 5371 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5373 */     TextTag _jspx_th_html_005ftext_005f15 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5374 */     _jspx_th_html_005ftext_005f15.setPageContext(_jspx_page_context);
/* 5375 */     _jspx_th_html_005ftext_005f15.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 5377 */     _jspx_th_html_005ftext_005f15.setProperty("command");
/*      */     
/* 5379 */     _jspx_th_html_005ftext_005f15.setStyleClass("formtext");
/*      */     
/* 5381 */     _jspx_th_html_005ftext_005f15.setSize("60");
/* 5382 */     int _jspx_eval_html_005ftext_005f15 = _jspx_th_html_005ftext_005f15.doStartTag();
/* 5383 */     if (_jspx_th_html_005ftext_005f15.doEndTag() == 5) {
/* 5384 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f15);
/* 5385 */       return true;
/*      */     }
/* 5387 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f15);
/* 5388 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\HostResourceConfig_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */