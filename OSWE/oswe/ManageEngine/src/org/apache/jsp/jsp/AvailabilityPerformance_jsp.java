/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.data.support.DialChartSupport;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.DialChart;
/*      */ import com.adventnet.awolf.tags.Property;
/*      */ import com.adventnet.awolf.tags.TimeChart;
/*      */ import java.net.URLEncoder;
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
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class AvailabilityPerformance_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   59 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   62 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   63 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   64 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   71 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   76 */     ArrayList list = null;
/*   77 */     StringBuffer sbf = new StringBuffer();
/*   78 */     ManagedApplication mo = new ManagedApplication();
/*   79 */     if (distinct)
/*      */     {
/*   81 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   85 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   88 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   90 */       ArrayList row = (ArrayList)list.get(i);
/*   91 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   92 */       if (distinct) {
/*   93 */         sbf.append(row.get(0));
/*      */       } else
/*   95 */         sbf.append(row.get(1));
/*   96 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   99 */     return sbf.toString(); }
/*      */   
/*  101 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  104 */     if (severity == null)
/*      */     {
/*  106 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  108 */     if (severity.equals("5"))
/*      */     {
/*  110 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  112 */     if (severity.equals("1"))
/*      */     {
/*  114 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  119 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  126 */     if (severity == null)
/*      */     {
/*  128 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  130 */     if (severity.equals("1"))
/*      */     {
/*  132 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  134 */     if (severity.equals("4"))
/*      */     {
/*  136 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  138 */     if (severity.equals("5"))
/*      */     {
/*  140 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  145 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  151 */     if (severity == null)
/*      */     {
/*  153 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  155 */     if (severity.equals("5"))
/*      */     {
/*  157 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  159 */     if (severity.equals("1"))
/*      */     {
/*  161 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  165 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  171 */     if (severity == null)
/*      */     {
/*  173 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  175 */     if (severity.equals("1"))
/*      */     {
/*  177 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  179 */     if (severity.equals("4"))
/*      */     {
/*  181 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  183 */     if (severity.equals("5"))
/*      */     {
/*  185 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  189 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  195 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  201 */     if (severity == 5)
/*      */     {
/*  203 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  205 */     if (severity == 1)
/*      */     {
/*  207 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  212 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  218 */     if (severity == null)
/*      */     {
/*  220 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  222 */     if (severity.equals("5"))
/*      */     {
/*  224 */       if (isAvailability) {
/*  225 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  228 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  231 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  233 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  235 */     if (severity.equals("1"))
/*      */     {
/*  237 */       if (isAvailability) {
/*  238 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  241 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  248 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  255 */     if (severity == null)
/*      */     {
/*  257 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  259 */     if (severity.equals("5"))
/*      */     {
/*  261 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  263 */     if (severity.equals("4"))
/*      */     {
/*  265 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  267 */     if (severity.equals("1"))
/*      */     {
/*  269 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  274 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  280 */     if (severity == null)
/*      */     {
/*  282 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  284 */     if (severity.equals("5"))
/*      */     {
/*  286 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  288 */     if (severity.equals("4"))
/*      */     {
/*  290 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  292 */     if (severity.equals("1"))
/*      */     {
/*  294 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  299 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  306 */     if (severity == null)
/*      */     {
/*  308 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  310 */     if (severity.equals("5"))
/*      */     {
/*  312 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  314 */     if (severity.equals("4"))
/*      */     {
/*  316 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  318 */     if (severity.equals("1"))
/*      */     {
/*  320 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  325 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  333 */     StringBuffer out = new StringBuffer();
/*  334 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  335 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  336 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  337 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  338 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  339 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  340 */     out.append("</tr>");
/*  341 */     out.append("</form></table>");
/*  342 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  349 */     if (val == null)
/*      */     {
/*  351 */       return "-";
/*      */     }
/*      */     
/*  354 */     String ret = FormatUtil.formatNumber(val);
/*  355 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  356 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  359 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  363 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  371 */     StringBuffer out = new StringBuffer();
/*  372 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  373 */     out.append("<tr>");
/*  374 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  376 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  378 */     out.append("</tr>");
/*  379 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  383 */       if (j % 2 == 0)
/*      */       {
/*  385 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  389 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  392 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  394 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  397 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  401 */       out.append("</tr>");
/*      */     }
/*  403 */     out.append("</table>");
/*  404 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  405 */     out.append("<tr>");
/*  406 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  407 */     out.append("</tr>");
/*  408 */     out.append("</table>");
/*  409 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, java.util.Vector tableColumns)
/*      */   {
/*  415 */     StringBuffer out = new StringBuffer();
/*  416 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  417 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  418 */     out.append("<tr>");
/*  419 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  420 */     out.append("<tr>");
/*  421 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  422 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  423 */     out.append("</tr>");
/*  424 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  427 */       out.append("<tr>");
/*  428 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  429 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  430 */       out.append("</tr>");
/*      */     }
/*      */     
/*  433 */     out.append("</table>");
/*  434 */     out.append("</table>");
/*  435 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  440 */     if (severity.equals("0"))
/*      */     {
/*  442 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  446 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, javax.servlet.http.HttpSession session)
/*      */   {
/*  453 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  466 */     StringBuffer out = new StringBuffer();
/*  467 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  468 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  470 */       out.append("<tr>");
/*  471 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  472 */       out.append("</tr>");
/*      */       
/*      */ 
/*  475 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  477 */         String borderclass = "";
/*      */         
/*      */ 
/*  480 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  482 */         out.append("<tr>");
/*      */         
/*  484 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  485 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  486 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  492 */     out.append("</table><br>");
/*  493 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  494 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  496 */       List sLinks = secondLevelOfLinks[0];
/*  497 */       List sText = secondLevelOfLinks[1];
/*  498 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  501 */         out.append("<tr>");
/*  502 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  503 */         out.append("</tr>");
/*  504 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  506 */           String borderclass = "";
/*      */           
/*      */ 
/*  509 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  511 */           out.append("<tr>");
/*      */           
/*  513 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  514 */           if (sLinks.get(i).toString().length() == 0) {
/*  515 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  518 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  520 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  524 */     out.append("</table>");
/*  525 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, javax.servlet.http.HttpSession session, HttpServletRequest request)
/*      */   {
/*  532 */     StringBuffer out = new StringBuffer();
/*  533 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  534 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  536 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  538 */         out.append("<tr>");
/*  539 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  540 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  544 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  546 */           String borderclass = "";
/*      */           
/*      */ 
/*  549 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  551 */           out.append("<tr>");
/*      */           
/*  553 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  554 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  555 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  558 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  561 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  566 */     out.append("</table><br>");
/*  567 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  568 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  570 */       List sLinks = secondLevelOfLinks[0];
/*  571 */       List sText = secondLevelOfLinks[1];
/*  572 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  575 */         out.append("<tr>");
/*  576 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  577 */         out.append("</tr>");
/*  578 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  580 */           String borderclass = "";
/*      */           
/*      */ 
/*  583 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  585 */           out.append("<tr>");
/*      */           
/*  587 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  588 */           if (sLinks.get(i).toString().length() == 0) {
/*  589 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  592 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  594 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  598 */     out.append("</table>");
/*  599 */     return out.toString();
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
/*  612 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  615 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  618 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  621 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  624 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  627 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  630 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  633 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  641 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  646 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  651 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  656 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  661 */     if (val != null)
/*      */     {
/*  663 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  667 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  672 */     if (val == null) {
/*  673 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  677 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  682 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  688 */     if (val != null)
/*      */     {
/*  690 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  694 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  700 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  705 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  709 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  714 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  719 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  724 */     String hostaddress = "";
/*  725 */     String ip = request.getHeader("x-forwarded-for");
/*  726 */     if (ip == null)
/*  727 */       ip = request.getRemoteAddr();
/*  728 */     java.net.InetAddress add = null;
/*  729 */     if (ip.equals("127.0.0.1")) {
/*  730 */       add = java.net.InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  734 */       add = java.net.InetAddress.getByName(ip);
/*      */     }
/*  736 */     hostaddress = add.getHostName();
/*  737 */     if (hostaddress.indexOf('.') != -1) {
/*  738 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  739 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  743 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  748 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  754 */     if (severity == null)
/*      */     {
/*  756 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  758 */     if (severity.equals("5"))
/*      */     {
/*  760 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  762 */     if (severity.equals("1"))
/*      */     {
/*  764 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  769 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  774 */     ResultSet set = null;
/*  775 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  776 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  778 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  779 */       if (set.next()) { String str1;
/*  780 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  781 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  784 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  789 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  792 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  794 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  798 */     StringBuffer rca = new StringBuffer();
/*  799 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  800 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  803 */     int rcalength = key.length();
/*  804 */     String split = "6. ";
/*  805 */     int splitPresent = key.indexOf(split);
/*  806 */     String div1 = "";String div2 = "";
/*  807 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  809 */       if (rcalength > 180) {
/*  810 */         rca.append("<span class=\"rca-critical-text\">");
/*  811 */         getRCATrimmedText(key, rca);
/*  812 */         rca.append("</span>");
/*      */       } else {
/*  814 */         rca.append("<span class=\"rca-critical-text\">");
/*  815 */         rca.append(key);
/*  816 */         rca.append("</span>");
/*      */       }
/*  818 */       return rca.toString();
/*      */     }
/*  820 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  821 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  822 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  823 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  824 */     getRCATrimmedText(div1, rca);
/*  825 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  828 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  829 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  830 */     getRCATrimmedText(div2, rca);
/*  831 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  833 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  838 */     String[] st = msg.split("<br>");
/*  839 */     for (int i = 0; i < st.length; i++) {
/*  840 */       String s = st[i];
/*  841 */       if (s.length() > 180) {
/*  842 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  844 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  848 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  849 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  851 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  855 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  856 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  857 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  860 */       if (key == null) {
/*  861 */         return ret;
/*      */       }
/*      */       
/*  864 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  865 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  868 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  869 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  870 */       set = AMConnectionPool.executeQueryStmt(query);
/*  871 */       if (set.next())
/*      */       {
/*  873 */         String helpLink = set.getString("LINK");
/*  874 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  877 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  883 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  902 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  893 */         if (set != null) {
/*  894 */           AMConnectionPool.closeStatement(set);
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
/*  908 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  909 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  911 */       String entityStr = (String)keys.nextElement();
/*  912 */       String mmessage = temp.getProperty(entityStr);
/*  913 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  914 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  916 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  922 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  923 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  925 */       String entityStr = (String)keys.nextElement();
/*  926 */       String mmessage = temp.getProperty(entityStr);
/*  927 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  928 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  930 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  935 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  945 */     String des = new String();
/*  946 */     while (str.indexOf(find) != -1) {
/*  947 */       des = des + str.substring(0, str.indexOf(find));
/*  948 */       des = des + replace;
/*  949 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  951 */     des = des + str;
/*  952 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  959 */       if (alert == null)
/*      */       {
/*  961 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  963 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  965 */         return "&nbsp;";
/*      */       }
/*      */       
/*  968 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  970 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  973 */       int rcalength = test.length();
/*  974 */       if (rcalength < 300)
/*      */       {
/*  976 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  980 */       StringBuffer out = new StringBuffer();
/*  981 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  982 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  983 */       out.append("</div>");
/*  984 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  985 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  986 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  991 */       ex.printStackTrace();
/*      */     }
/*  993 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  999 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1004 */     ArrayList attribIDs = new ArrayList();
/* 1005 */     ArrayList resIDs = new ArrayList();
/* 1006 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1008 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1010 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1012 */       String resourceid = "";
/* 1013 */       String resourceType = "";
/* 1014 */       if (type == 2) {
/* 1015 */         resourceid = (String)row.get(0);
/* 1016 */         resourceType = (String)row.get(3);
/*      */       }
/* 1018 */       else if (type == 3) {
/* 1019 */         resourceid = (String)row.get(0);
/* 1020 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1023 */         resourceid = (String)row.get(6);
/* 1024 */         resourceType = (String)row.get(7);
/*      */       }
/* 1026 */       resIDs.add(resourceid);
/* 1027 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1028 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1030 */       String healthentity = null;
/* 1031 */       String availentity = null;
/* 1032 */       if (healthid != null) {
/* 1033 */         healthentity = resourceid + "_" + healthid;
/* 1034 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1037 */       if (availid != null) {
/* 1038 */         availentity = resourceid + "_" + availid;
/* 1039 */         entitylist.add(availentity);
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
/* 1053 */     Properties alert = getStatus(entitylist);
/* 1054 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1059 */     int size = monitorList.size();
/*      */     
/* 1061 */     String[] severity = new String[size];
/*      */     
/* 1063 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1065 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1066 */       String resourceName1 = (String)row1.get(7);
/* 1067 */       String resourceid1 = (String)row1.get(6);
/* 1068 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1069 */       if (severity[j] == null)
/*      */       {
/* 1071 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1075 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1077 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1079 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1082 */         if (sev > 0) {
/* 1083 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1084 */           monitorList.set(k, monitorList.get(j));
/* 1085 */           monitorList.set(j, t);
/* 1086 */           String temp = severity[k];
/* 1087 */           severity[k] = severity[j];
/* 1088 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1094 */     int z = 0;
/* 1095 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1098 */       int i = 0;
/* 1099 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1102 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1106 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1110 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1112 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1115 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1119 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1122 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1123 */       String resourceName1 = (String)row1.get(7);
/* 1124 */       String resourceid1 = (String)row1.get(6);
/* 1125 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1126 */       if (hseverity[j] == null)
/*      */       {
/* 1128 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1133 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1135 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1138 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1141 */         if (hsev > 0) {
/* 1142 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1143 */           monitorList.set(k, monitorList.get(j));
/* 1144 */           monitorList.set(j, t);
/* 1145 */           String temp1 = hseverity[k];
/* 1146 */           hseverity[k] = hseverity[j];
/* 1147 */           hseverity[j] = temp1;
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
/* 1159 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1160 */     boolean forInventory = false;
/* 1161 */     String trdisplay = "none";
/* 1162 */     String plusstyle = "inline";
/* 1163 */     String minusstyle = "none";
/* 1164 */     String haidTopLevel = "";
/* 1165 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1167 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1169 */         haidTopLevel = request.getParameter("haid");
/* 1170 */         forInventory = true;
/* 1171 */         trdisplay = "table-row;";
/* 1172 */         plusstyle = "none";
/* 1173 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1180 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1183 */     ArrayList listtoreturn = new ArrayList();
/* 1184 */     StringBuffer toreturn = new StringBuffer();
/* 1185 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1186 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1187 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1189 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1191 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1192 */       String childresid = (String)singlerow.get(0);
/* 1193 */       String childresname = (String)singlerow.get(1);
/* 1194 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1195 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1196 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1197 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1198 */       String unmanagestatus = (String)singlerow.get(5);
/* 1199 */       String actionstatus = (String)singlerow.get(6);
/* 1200 */       String linkclass = "monitorgp-links";
/* 1201 */       String titleforres = childresname;
/* 1202 */       String titilechildresname = childresname;
/* 1203 */       String childimg = "/images/trcont.png";
/* 1204 */       String flag = "enable";
/* 1205 */       String dcstarted = (String)singlerow.get(8);
/* 1206 */       String configMonitor = "";
/* 1207 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1208 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1210 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1212 */       if (singlerow.get(7) != null)
/*      */       {
/* 1214 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1216 */       String haiGroupType = "0";
/* 1217 */       if ("HAI".equals(childtype))
/*      */       {
/* 1219 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1221 */       childimg = "/images/trend.png";
/* 1222 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1223 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1224 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1226 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1228 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1230 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1231 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1234 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1236 */         linkclass = "disabledtext";
/* 1237 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1239 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1240 */       String availmouseover = "";
/* 1241 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1243 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1245 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1246 */       String healthmouseover = "";
/* 1247 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1249 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1252 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1253 */       int spacing = 0;
/* 1254 */       if (level >= 1)
/*      */       {
/* 1256 */         spacing = 40 * level;
/*      */       }
/* 1258 */       if (childtype.equals("HAI"))
/*      */       {
/* 1260 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1261 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1262 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1264 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1265 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1266 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1267 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1268 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1269 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1270 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1271 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1272 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1273 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1274 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1276 */         if (!forInventory)
/*      */         {
/* 1278 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1281 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1283 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1285 */           actions = editlink + actions;
/*      */         }
/* 1287 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1289 */           actions = actions + associatelink;
/*      */         }
/* 1291 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1292 */         String arrowimg = "";
/* 1293 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1295 */           actions = "";
/* 1296 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1297 */           checkbox = "";
/* 1298 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1300 */         if (isIt360)
/*      */         {
/* 1302 */           actionimg = "";
/* 1303 */           actions = "";
/* 1304 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1305 */           checkbox = "";
/*      */         }
/*      */         
/* 1308 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1310 */           actions = "";
/*      */         }
/* 1312 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1314 */           checkbox = "";
/*      */         }
/*      */         
/* 1317 */         String resourcelink = "";
/*      */         
/* 1319 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1321 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1325 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1328 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1329 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1330 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1331 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1332 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1333 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1334 */         if (!isIt360)
/*      */         {
/* 1336 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1340 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1343 */         toreturn.append("</tr>");
/* 1344 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1346 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1347 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1351 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1352 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1355 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1359 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1361 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1362 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1363 */             toreturn.append(assocMessage);
/* 1364 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1365 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1366 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1367 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1373 */         String resourcelink = null;
/* 1374 */         boolean hideEditLink = false;
/* 1375 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1377 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1378 */           hideEditLink = true;
/* 1379 */           if (isIt360)
/*      */           {
/* 1381 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1385 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1387 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1389 */           hideEditLink = true;
/* 1390 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1391 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1396 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1399 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1400 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1401 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1402 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1403 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1404 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1405 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1406 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1407 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1408 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1409 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1410 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1411 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1413 */         if (hideEditLink)
/*      */         {
/* 1415 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1417 */         if (!forInventory)
/*      */         {
/* 1419 */           removefromgroup = "";
/*      */         }
/* 1421 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1422 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1423 */           actions = actions + configcustomfields;
/*      */         }
/* 1425 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1427 */           actions = editlink + actions;
/*      */         }
/* 1429 */         String managedLink = "";
/* 1430 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1432 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1433 */           actions = "";
/* 1434 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1435 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1438 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1440 */           checkbox = "";
/*      */         }
/*      */         
/* 1443 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1445 */           actions = "";
/*      */         }
/* 1447 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1448 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1449 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1450 */         if (isIt360)
/*      */         {
/* 1452 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1456 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1458 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1459 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1460 */         if (!isIt360)
/*      */         {
/* 1462 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1466 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1468 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1471 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1478 */       StringBuilder toreturn = new StringBuilder();
/* 1479 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1480 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1481 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1482 */       String title = "";
/* 1483 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
/* 1484 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1485 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1486 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1488 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1490 */       else if ("5".equals(severity))
/*      */       {
/* 1492 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1496 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1498 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1499 */       toreturn.append(v);
/*      */       
/* 1501 */       toreturn.append(link);
/* 1502 */       if (severity == null)
/*      */       {
/* 1504 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1506 */       else if (severity.equals("5"))
/*      */       {
/* 1508 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1510 */       else if (severity.equals("4"))
/*      */       {
/* 1512 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1514 */       else if (severity.equals("1"))
/*      */       {
/* 1516 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1521 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1523 */       toreturn.append("</a>");
/* 1524 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1528 */       ex.printStackTrace();
/*      */     }
/* 1530 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1537 */       StringBuilder toreturn = new StringBuilder();
/* 1538 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1539 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1540 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1541 */       if (message == null)
/*      */       {
/* 1543 */         message = "";
/*      */       }
/*      */       
/* 1546 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1547 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1549 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1550 */       toreturn.append(v);
/*      */       
/* 1552 */       toreturn.append(link);
/*      */       
/* 1554 */       if (severity == null)
/*      */       {
/* 1556 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1558 */       else if (severity.equals("5"))
/*      */       {
/* 1560 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1562 */       else if (severity.equals("1"))
/*      */       {
/* 1564 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1569 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1571 */       toreturn.append("</a>");
/* 1572 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1578 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1581 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1582 */     if (invokeActions != null) {
/* 1583 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1584 */       while (iterator.hasNext()) {
/* 1585 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1586 */         if (actionmap.containsKey(actionid)) {
/* 1587 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1592 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1596 */     String actionLink = "";
/* 1597 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1598 */     String query = "";
/* 1599 */     ResultSet rs = null;
/* 1600 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1601 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1602 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1603 */       actionLink = "method=" + methodName;
/*      */     }
/* 1605 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1606 */       actionLink = methodName;
/*      */     }
/* 1608 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1609 */     Iterator itr = methodarglist.iterator();
/* 1610 */     boolean isfirstparam = true;
/* 1611 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1612 */     while (itr.hasNext()) {
/* 1613 */       HashMap argmap = (HashMap)itr.next();
/* 1614 */       String argtype = (String)argmap.get("TYPE");
/* 1615 */       String argname = (String)argmap.get("IDENTITY");
/* 1616 */       String paramname = (String)argmap.get("PARAMETER");
/* 1617 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1618 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1619 */         isfirstparam = false;
/* 1620 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1622 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1626 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1630 */         actionLink = actionLink + "&";
/*      */       }
/* 1632 */       String paramValue = null;
/* 1633 */       String tempargname = argname;
/* 1634 */       if (commonValues.getProperty(tempargname) != null) {
/* 1635 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1638 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1639 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1640 */           if (dbType.equals("mysql")) {
/* 1641 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1644 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1646 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1648 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1649 */             if (rs.next()) {
/* 1650 */               paramValue = rs.getString("VALUE");
/* 1651 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1655 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1659 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1662 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1667 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1668 */           paramValue = rowId;
/*      */         }
/* 1670 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1671 */           paramValue = managedObjectName;
/*      */         }
/* 1673 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1674 */           paramValue = resID;
/*      */         }
/* 1676 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1677 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1680 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1682 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1683 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1684 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1686 */     return actionLink;
/*      */   }
/*      */   
/* 1689 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1690 */     String dependentAttribute = null;
/* 1691 */     String align = "left";
/*      */     
/* 1693 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1694 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1695 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1696 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1697 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1698 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1699 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1700 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1701 */       align = "center";
/*      */     }
/*      */     
/* 1704 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1705 */     String actualdata = "";
/*      */     
/* 1707 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1708 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1709 */         actualdata = availValue;
/*      */       }
/* 1711 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1712 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1716 */           String attributeName = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1717 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1720 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1726 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1727 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1728 */       toreturn.append("<table>");
/* 1729 */       toreturn.append("<tr>");
/* 1730 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1731 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1732 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1733 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1734 */         String toolTip = "";
/* 1735 */         String hideClass = "";
/* 1736 */         String textStyle = "";
/* 1737 */         boolean isreferenced = true;
/* 1738 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1739 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1740 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1741 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1743 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1744 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1745 */           while (valueList.hasMoreTokens()) {
/* 1746 */             String dependentVal = valueList.nextToken();
/* 1747 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1748 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1749 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1751 */               toolTip = "";
/* 1752 */               hideClass = "";
/* 1753 */               isreferenced = false;
/* 1754 */               textStyle = "disabledtext";
/* 1755 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1759 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1760 */           toolTip = "";
/* 1761 */           hideClass = "";
/* 1762 */           isreferenced = false;
/* 1763 */           textStyle = "disabledtext";
/* 1764 */           if (dependentImageMap != null) {
/* 1765 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1766 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1769 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1773 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1774 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1775 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1776 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1777 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1778 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1780 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1781 */           if (isreferenced) {
/* 1782 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1786 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1787 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1788 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1789 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1790 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1791 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1793 */           toreturn.append("</span>");
/* 1794 */           toreturn.append("</a>");
/* 1795 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1798 */       toreturn.append("</tr>");
/* 1799 */       toreturn.append("</table>");
/* 1800 */       toreturn.append("</td>");
/*      */     } else {
/* 1802 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1805 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1809 */     String colTime = null;
/* 1810 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1811 */     if ((rows != null) && (rows.size() > 0)) {
/* 1812 */       Iterator<String> itr = rows.iterator();
/* 1813 */       String maxColQuery = "";
/* 1814 */       for (;;) { if (itr.hasNext()) {
/* 1815 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1816 */           ResultSet maxCol = null;
/*      */           try {
/* 1818 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1819 */             while (maxCol.next()) {
/* 1820 */               if (colTime == null) {
/* 1821 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1824 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1833 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1835 */               if (maxCol != null)
/* 1836 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1838 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1833 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1835 */               if (maxCol != null)
/* 1836 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1838 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1843 */     return colTime;
/*      */   }
/*      */   
/* 1846 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1847 */     tablename = null;
/* 1848 */     ResultSet rsTable = null;
/* 1849 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1851 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1852 */       while (rsTable.next()) {
/* 1853 */         tablename = rsTable.getString("DATATABLE");
/* 1854 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1855 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1868 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1859 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1862 */         if (rsTable != null)
/* 1863 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1865 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1871 */     String argsList = "";
/* 1872 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1874 */       if (showArgsMap.get(row) != null) {
/* 1875 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1876 */         if (showArgslist != null) {
/* 1877 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1878 */             if (argsList.trim().equals("")) {
/* 1879 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1882 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1889 */       e.printStackTrace();
/* 1890 */       return "";
/*      */     }
/* 1892 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1897 */     String argsList = "";
/* 1898 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1901 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1903 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1904 */         if (hideArgsList != null)
/*      */         {
/* 1906 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1908 */             if (argsList.trim().equals(""))
/*      */             {
/* 1910 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1914 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1922 */       ex.printStackTrace();
/*      */     }
/* 1924 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1928 */     StringBuilder toreturn = new StringBuilder();
/* 1929 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1936 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1937 */       Iterator itr = tActionList.iterator();
/* 1938 */       while (itr.hasNext()) {
/* 1939 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1940 */         String confirmmsg = "";
/* 1941 */         String link = "";
/* 1942 */         String isJSP = "NO";
/* 1943 */         HashMap tactionMap = (HashMap)itr.next();
/* 1944 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1945 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1946 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1947 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1948 */           (actionmap.containsKey(actionId))) {
/* 1949 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1950 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1951 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1952 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1953 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1955 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1961 */           if (isTableAction) {
/* 1962 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1965 */             tableName = "Link";
/* 1966 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1967 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1968 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1969 */             toreturn.append("</a></td>");
/*      */           }
/* 1971 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1972 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1973 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1974 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1980 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1986 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1988 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1989 */       Properties prop = (Properties)node.getUserObject();
/* 1990 */       String mgID = prop.getProperty("label");
/* 1991 */       String mgName = prop.getProperty("value");
/* 1992 */       String isParent = prop.getProperty("isParent");
/* 1993 */       int mgIDint = Integer.parseInt(mgID);
/* 1994 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
/*      */       {
/* 1996 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1998 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1999 */       if (node.getChildCount() > 0)
/*      */       {
/* 2001 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2003 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2005 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2007 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2011 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2016 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2018 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2020 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2022 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2026 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2029 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2030 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2032 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2036 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2038 */       if (node.getChildCount() > 0)
/*      */       {
/* 2040 */         builder.append("<UL>");
/* 2041 */         printMGTree(node, builder);
/* 2042 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2047 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2048 */     StringBuffer toReturn = new StringBuffer();
/* 2049 */     String table = "-";
/*      */     try {
/* 2051 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2052 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2053 */       float total = 0.0F;
/* 2054 */       while (it.hasNext()) {
/* 2055 */         String attName = (String)it.next();
/* 2056 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2057 */         boolean roundOffData = false;
/* 2058 */         if ((data != null) && (!data.equals(""))) {
/* 2059 */           if (data.indexOf(",") != -1) {
/* 2060 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2063 */             float value = Float.parseFloat(data);
/* 2064 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2067 */             total += value;
/* 2068 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2071 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2076 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2077 */       while (attVsWidthList.hasNext()) {
/* 2078 */         String attName = (String)attVsWidthList.next();
/* 2079 */         String data = (String)attVsWidthProps.get(attName);
/* 2080 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2081 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2082 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2083 */         String className = (String)graphDetails.get("ClassName");
/* 2084 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2085 */         if (percentage < 1.0F)
/*      */         {
/* 2087 */           data = percentage + "";
/*      */         }
/* 2089 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2091 */       if (toReturn.length() > 0) {
/* 2092 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2096 */       e.printStackTrace();
/*      */     }
/* 2098 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2104 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2105 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2106 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2107 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2108 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2109 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2110 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2111 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2112 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2115 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2116 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2117 */       splitvalues[0] = multiplecondition.toString();
/* 2118 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2121 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2126 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2127 */     if (thresholdType != 3) {
/* 2128 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2129 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2130 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2131 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2132 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2133 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2135 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2136 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2137 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2138 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2139 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2140 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2142 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2143 */     if (updateSelected != null) {
/* 2144 */       updateSelected[0] = "selected";
/*      */     }
/* 2146 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2151 */       StringBuffer toreturn = new StringBuffer("");
/* 2152 */       if (commaSeparatedMsgId != null) {
/* 2153 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2154 */         int count = 0;
/* 2155 */         while (msgids.hasMoreTokens()) {
/* 2156 */           String id = msgids.nextToken();
/* 2157 */           String message = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2158 */           String image = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2159 */           count++;
/* 2160 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2161 */             if (toreturn.length() == 0) {
/* 2162 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2164 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2165 */             if (!image.trim().equals("")) {
/* 2166 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2168 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2169 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2172 */         if (toreturn.length() > 0) {
/* 2173 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2177 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2180 */       e.printStackTrace(); }
/* 2181 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String addBreakAt(String str, int len)
/*      */   {
/* 2190 */     if (len == 0) return str;
/* 2191 */     String temp = str;
/* 2192 */     StringBuffer ret = new StringBuffer("");
/* 2193 */     while (temp.length() > len)
/*      */     {
/* 2195 */       ret.append(temp.substring(0, len));
/* 2196 */       ret.append("<br>");
/* 2197 */       temp = temp.substring(len);
/*      */     }
/* 2199 */     ret.append(temp);
/* 2200 */     return ret.toString();
/*      */   }
/*      */   
/* 2203 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = javax.servlet.jsp.JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2209 */   private static Map<String, Long> _jspx_dependants = new HashMap(10);
/* 2210 */   static { _jspx_dependants.put("/jsp/includes/ApacheStats.jspf", Long.valueOf(1473429417000L));
/* 2211 */     _jspx_dependants.put("/jsp/includes/jmxnotificationlisteners_list.jspf", Long.valueOf(1473429417000L));
/* 2212 */     _jspx_dependants.put("/jsp/includes/mop_actions.jspf", Long.valueOf(1473429417000L));
/* 2213 */     _jspx_dependants.put("/jsp/includes/ManagedServerInfo.jspf", Long.valueOf(1473429417000L));
/* 2214 */     _jspx_dependants.put("/jsp/includes/cam_screen.jspf", Long.valueOf(1473429417000L));
/* 2215 */     _jspx_dependants.put("/jsp/includes/TopBorder.jspf", Long.valueOf(1473429417000L));
/* 2216 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2217 */     _jspx_dependants.put("/jsp/includes/BottomBorder.jspf", Long.valueOf(1473429417000L));
/* 2218 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/* 2219 */     _jspx_dependants.put("/jsp/includes/HostPerformance.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2259 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2263 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2264 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2265 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2266 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2267 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2268 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2269 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2270 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2271 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2272 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2273 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2274 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2275 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2276 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2277 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2278 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2279 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2280 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2281 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2282 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2283 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2284 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2285 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2286 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2287 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2288 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2289 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2290 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2291 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2292 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2293 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2294 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2295 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2296 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2300 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2301 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2302 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2303 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2304 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2305 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2306 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2307 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2308 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2309 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2310 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2311 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2312 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.release();
/* 2313 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2314 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2315 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2316 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2317 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2318 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.release();
/* 2319 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2320 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/* 2321 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.release();
/* 2322 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.release();
/* 2323 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.release();
/* 2324 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid.release();
/* 2325 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2326 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/* 2327 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.release();
/* 2328 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2329 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/* 2330 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.release();
/* 2331 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.release();
/*      */   }
/*      */   
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException, javax.servlet.ServletException {
/*      */     ;
/*      */     ;
/*      */     ;
/* 2338 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/* 2341 */     JspWriter out = null;
/* 2342 */     Object page = this;
/* 2343 */     JspWriter _jspx_out = null;
/* 2344 */     PageContext _jspx_page_context = null;
/*      */     
/* 2346 */     Object _jspx_acolumn_1 = null;
/* 2347 */     Integer _jspx_i_1 = null;
/*      */     try
/*      */     {
/* 2350 */       response.setContentType("text/html;charset=UTF-8");
/* 2351 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2353 */       _jspx_page_context = pageContext;
/* 2354 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2355 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2356 */       session = pageContext.getSession();
/* 2357 */       out = pageContext.getOut();
/* 2358 */       _jspx_out = out;
/*      */       
/* 2360 */       out.write("<!DOCTYPE html>\n");
/* 2361 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n\n");
/*      */       
/* 2363 */       request.setAttribute("HelpKey", "Monitors Service Details");
/*      */       
/* 2365 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2366 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2368 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2369 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2370 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2372 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2374 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2376 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2378 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2379 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2380 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2381 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2384 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2385 */         String available = null;
/* 2386 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2387 */         out.write(10);
/*      */         
/* 2389 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2390 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2391 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2393 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2395 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2397 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2399 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2400 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2401 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2402 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2405 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2406 */           String unavailable = null;
/* 2407 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2408 */           out.write(10);
/*      */           
/* 2410 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2411 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2412 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2414 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2416 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2418 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2420 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2421 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2422 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2423 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2426 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2427 */             String unmanaged = null;
/* 2428 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2429 */             out.write(10);
/*      */             
/* 2431 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2432 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2433 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2435 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2437 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2439 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2441 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2442 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2443 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2444 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2447 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2448 */               String scheduled = null;
/* 2449 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2450 */               out.write(10);
/*      */               
/* 2452 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2453 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2454 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2456 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2458 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2460 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2462 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2463 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2464 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2465 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2468 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2469 */                 String critical = null;
/* 2470 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2471 */                 out.write(10);
/*      */                 
/* 2473 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2474 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2475 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2477 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2479 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2481 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2483 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2484 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2485 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2486 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2489 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2490 */                   String clear = null;
/* 2491 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2492 */                   out.write(10);
/*      */                   
/* 2494 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2495 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2496 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2498 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2500 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2502 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2504 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2505 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2506 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2507 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2510 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2511 */                     String warning = null;
/* 2512 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2513 */                     out.write(10);
/* 2514 */                     out.write(10);
/*      */                     
/* 2516 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2517 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2519 */                     out.write(10);
/* 2520 */                     out.write(10);
/* 2521 */                     out.write(10);
/* 2522 */                     out.write(10);
/* 2523 */                     out.write(10);
/* 2524 */                     GetWLSGraph wlsGraph = null;
/* 2525 */                     wlsGraph = (GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2526 */                     if (wlsGraph == null) {
/* 2527 */                       wlsGraph = new GetWLSGraph();
/* 2528 */                       _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */                     }
/* 2530 */                     out.write(10);
/* 2531 */                     Hashtable motypedisplaynames = null;
/* 2532 */                     synchronized (application) {
/* 2533 */                       motypedisplaynames = (Hashtable)_jspx_page_context.getAttribute("motypedisplaynames", 4);
/* 2534 */                       if (motypedisplaynames == null) {
/* 2535 */                         motypedisplaynames = new Hashtable();
/* 2536 */                         _jspx_page_context.setAttribute("motypedisplaynames", motypedisplaynames, 4);
/*      */                       }
/*      */                     }
/* 2539 */                     out.write(10);
/* 2540 */                     Hashtable availabilitykeys = null;
/* 2541 */                     synchronized (application) {
/* 2542 */                       availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2543 */                       if (availabilitykeys == null) {
/* 2544 */                         availabilitykeys = new Hashtable();
/* 2545 */                         _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */                       }
/*      */                     }
/* 2548 */                     out.write(10);
/* 2549 */                     Hashtable healthkeys = null;
/* 2550 */                     synchronized (application) {
/* 2551 */                       healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2552 */                       if (healthkeys == null) {
/* 2553 */                         healthkeys = new Hashtable();
/* 2554 */                         _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */                       }
/*      */                     }
/* 2557 */                     out.write(10);
/*      */                     
/* 2559 */                     String yaxis_restime = FormatUtil.getString("am.webclient.common.responsetime.text") + " " + FormatUtil.getString("am.webclient.common.units.ms.text");
/* 2560 */                     String xaxis_time = FormatUtil.getString("am.webclient.common.axisname.time.text");
/* 2561 */                     String yaxis_busyser = FormatUtil.getString("am.webclient.apache.busyservers");
/* 2562 */                     String yaxis_bytessec = FormatUtil.getString("am.webclient.apache.bytespersecond");
/* 2563 */                     String seven_days_text_1 = FormatUtil.getString("am.webclient.common.sevendays.tooltip.text");
/* 2564 */                     String thiry_days_text_1 = FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text");
/* 2565 */                     String yaxis_pagefaults = FormatUtil.getString("am.webclient.php.pagefaults.text");
/* 2566 */                     String resourceName = request.getParameter("resourcename");
/* 2567 */                     String resID = request.getParameter("resourceid");
/* 2568 */                     request.setAttribute("resourceid", resID);
/* 2569 */                     ArrayList attribIDs = new ArrayList();
/* 2570 */                     ArrayList resIDs = new ArrayList();
/* 2571 */                     resIDs.add(resID);
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/* 2576 */                     String applicationName = request.getParameter("name");
/* 2577 */                     String haid = request.getParameter("haid");
/* 2578 */                     String moname = request.getParameter("moname");
/*      */                     
/* 2580 */                     AMConnectionPool pool = AMConnectionPool.getInstance();
/* 2581 */                     ResultSet rs = null;
/* 2582 */                     String resourcetype = request.getParameter("type");
/* 2583 */                     String responsetimeid = "402";
/* 2584 */                     if (!resourcetype.equals("UrlMonitor"))
/*      */                     {
/* 2586 */                       ManagedApplication mo = new ManagedApplication();
/* 2587 */                       ArrayList rows = mo.getRows("select ATTRIBUTEID,RESOURCETYPE,ATTRIBUTE from AM_ATTRIBUTES where RESOURCETYPE='" + resourcetype + "' and LOWER(ATTRIBUTE)='responsetime'");
/* 2588 */                       if (rows.size() > 0)
/*      */                       {
/* 2590 */                         rows = (ArrayList)rows.get(0);
/* 2591 */                         responsetimeid = (String)rows.get(0);
/* 2592 */                         attribIDs.add(responsetimeid);
/*      */                       }
/*      */                     }
/* 2595 */                     if ((resourcetype.equals("RMI")) || (resourcetype.equals("SNMP")) || (resourcetype.equals("JMX1.2-MX4J-RMI")) || (resourcetype.equals("TELNET"))) {
/* 2596 */                       request.setAttribute("isfromresourcepage", "true");
/*      */                     }
/* 2598 */                     attribIDs.add("1400");
/* 2599 */                     attribIDs.add("1401");
/* 2600 */                     attribIDs.add("2000");
/* 2601 */                     attribIDs.add("2001");
/* 2602 */                     attribIDs.add("2300");
/* 2603 */                     attribIDs.add("2301");
/* 2604 */                     attribIDs.add("2302");
/* 2605 */                     attribIDs.add("2303");
/* 2606 */                     attribIDs.add("2304");
/* 2607 */                     attribIDs.add("2305");
/* 2608 */                     attribIDs.add("400");
/* 2609 */                     attribIDs.add("401");
/* 2610 */                     attribIDs.add("900");
/* 2611 */                     attribIDs.add("901");
/* 2612 */                     attribIDs.add("1900");
/* 2613 */                     attribIDs.add("1901");
/* 2614 */                     attribIDs.add(String.valueOf(1850));
/* 2615 */                     attribIDs.add(String.valueOf(1851));
/* 2616 */                     attribIDs.add(String.valueOf(1860));
/* 2617 */                     attribIDs.add(String.valueOf(1861));
/* 2618 */                     attribIDs.add(String.valueOf(1750));
/* 2619 */                     attribIDs.add(String.valueOf(1751));
/* 2620 */                     attribIDs.add(String.valueOf(161));
/* 2621 */                     attribIDs.add(String.valueOf(162));
/* 2622 */                     attribIDs.add(String.valueOf(163));
/*      */                     
/* 2624 */                     Properties alert = getStatus(resIDs, attribIDs);
/* 2625 */                     HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/* 2626 */                     String encodeurl = URLEncoder.encode("/showresource.do?type=" + resourcetype + "&moname=" + moname + "&method=showdetails&resourcename=" + resourceName + "&resourceid=" + resID + "&haid=" + haid);
/* 2627 */                     String dispname = (String)motypedisplaynames.get(request.getParameter("type")) + " Details";
/* 2628 */                     HashMap hm = null;
/* 2629 */                     if (resourcetype.equals("PHP"))
/*      */                     {
/* 2631 */                       hm = (HashMap)request.getAttribute("phpdata");
/*      */                     }
/*      */                     
/* 2634 */                     out.write(10);
/* 2635 */                     out.write(10);
/* 2636 */                     out.write(9);
/* 2637 */                     if (_jspx_meth_c_005fcatch_005f0(_jspx_page_context))
/*      */                       return;
/* 2639 */                     out.write(10);
/*      */                     
/* 2641 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2642 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2643 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2645 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2646 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2647 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2649 */                         out.write(32);
/*      */                         
/* 2651 */                         PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2652 */                         _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2653 */                         _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2655 */                         _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                         
/* 2657 */                         _jspx_th_tiles_005fput_005f0.setValue(dispname);
/* 2658 */                         int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2659 */                         if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2660 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                         }
/*      */                         
/* 2663 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2664 */                         out.write(10);
/* 2665 */                         if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2667 */                         out.write(10);
/* 2668 */                         if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2670 */                         out.write(10);
/* 2671 */                         if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2673 */                         out.write(10);
/*      */                         
/* 2675 */                         PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2676 */                         _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 2677 */                         _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2679 */                         _jspx_th_tiles_005fput_005f4.setName("UserArea");
/*      */                         
/* 2681 */                         _jspx_th_tiles_005fput_005f4.setType("string");
/* 2682 */                         int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 2683 */                         if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 2684 */                           if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 2685 */                             out = _jspx_page_context.pushBody();
/* 2686 */                             _jspx_th_tiles_005fput_005f4.setBodyContent((BodyContent)out);
/* 2687 */                             _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2690 */                             out.write(10);
/*      */                             
/*      */ 
/* 2693 */                             String tipDB = FormatUtil.getString("am.webclient.availabilityperf.tooltip");
/* 2694 */                             String tipCurStatus = FormatUtil.getString("am.webclient.availabilityperf.curstatus.tooltip");
/* 2695 */                             String tipCurValue = FormatUtil.getString("am.webclient.availabilityperf.curvalue.tooltip");
/* 2696 */                             String tipCurThr = FormatUtil.getString("am.webclient.availabilityperf.curthres.tooltip");
/*      */                             
/* 2698 */                             out.write("\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n\t");
/*      */                             
/* 2700 */                             Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2701 */                             String aid = request.getParameter("haid");
/* 2702 */                             String haName = null;
/* 2703 */                             if (aid != null)
/*      */                             {
/* 2705 */                               haName = (String)ht.get(aid);
/*      */                             }
/*      */                             
/* 2708 */                             out.write(10);
/* 2709 */                             out.write(9);
/*      */                             
/* 2711 */                             IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2712 */                             _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2713 */                             _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 2715 */                             _jspx_th_c_005fif_005f2.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2716 */                             int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2717 */                             if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                               for (;;) {
/* 2719 */                                 out.write("\n      <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2720 */                                 out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHomePage(request));
/* 2721 */                                 out.write(" &gt; ");
/* 2722 */                                 out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 2723 */                                 out.write(" &gt; <span class=\"bcactive\"> ");
/* 2724 */                                 out.print(resourceName);
/* 2725 */                                 out.write(" </span></td>\n\t");
/* 2726 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2727 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2731 */                             if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2732 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                             }
/*      */                             
/* 2735 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2736 */                             out.write(10);
/* 2737 */                             out.write(9);
/*      */                             
/* 2739 */                             IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2740 */                             _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2741 */                             _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 2743 */                             _jspx_th_c_005fif_005f3.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 2744 */                             int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2745 */                             if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                               for (;;) {
/* 2747 */                                 out.write("\n      <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2748 */                                 out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorsPage());
/* 2749 */                                 out.write(" &gt; ");
/* 2750 */                                 out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorResourceTypes(request.getParameter("type")));
/* 2751 */                                 out.write(" &gt; <span class=\"bcactive\"> ");
/* 2752 */                                 out.print(resourceName);
/* 2753 */                                 out.write("</span></td>\n\t");
/* 2754 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2755 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2759 */                             if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2760 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                             }
/*      */                             
/* 2763 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2764 */                             out.write("\n    </tr>\n\t<tr>\n\t\t<td  height=\"2\" class=\"bcstrip\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"2\"></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n</table>\n");
/*      */                             
/* 2766 */                             IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2767 */                             _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2768 */                             _jspx_th_c_005fif_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 2770 */                             _jspx_th_c_005fif_005f4.setTest("${DotNetAgentAvailable == 'true'}");
/* 2771 */                             int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2772 */                             if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                               for (;;) {
/* 2774 */                                 out.write(10);
/*      */                                 
/* 2776 */                                 request.setAttribute("hideLeftArea", "true");
/*      */                                 
/* 2778 */                                 out.write("\n<script type=\"text/javascript\">\n\nfunction showHide(tab,resourceId, parentId, insightParam)\n{\n\t//alert(\"insightParam=\"+insightParam);\n\tif(tab==\"OverviewTab\")\n\t{\n\t\tdocument.getElementById(\"customreplink-left\").className = \"tbSelected_Left\";\n\t\tdocument.getElementById(\"customreplink\").className = \"tbSelected_Middle\";\n\t\tdocument.getElementById(\"customreplink-right\").className = \"tbSelected_Right\";\n\t\tdocument.getElementById(\"insightOverviewreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightOverviewreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightOverviewreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightTransactionreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightTransactionreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightTransactionreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightDBreplink-left\").className = \"tbUnSelected_Left\";\n");
/* 2779 */                                 out.write("\t\tdocument.getElementById(\"insightDBreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightDBreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightTracereplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightTracereplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightTracereplink-right\").className = \"tbUnSelected_Right\";\n\t\tjavascript:showDiv('OverviewTab');\n\t\tjavascript:hideDiv('insight_OverviewTab');\n\t\tjavascript:hideDiv('insight_transactionTab');\n\t   \tjavascript:hideDiv('insight_databaseTab');\n\t\tjavascript:hideDiv('insight_tracesTab');\n\t\tif(typeof insightParam == 'undefined')\n\t\t{\n\t\t\tvar url = document.location.href;\n\t\t\tvar pos = url.indexOf(\"#\");\n\t\t\tvar finalUrl = url.substring(0, pos);\n\t\t\tlocation.href = finalUrl;\n\t\t\t\n\t\t}\n\t}\n\telse if(tab==\"insight_OverviewTab\")\n\t{\n\t\tdocument.getElementById(\"customreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"customreplink\").className = \"tbUnSelected_Middle\";\n");
/* 2780 */                                 out.write("\t\tdocument.getElementById(\"customreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightOverviewreplink-left\").className = \"tbSelected_Left\";\n\t\tdocument.getElementById(\"insightOverviewreplink\").className = \"tbSelected_Middle\";\n\t\tdocument.getElementById(\"insightOverviewreplink-right\").className = \"tbSelected_Right\";\n\t\tdocument.getElementById(\"insightTransactionreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightTransactionreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightTransactionreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightDBreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightDBreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightDBreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightTracereplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightTracereplink\").className = \"tbUnSelected_Middle\";\n");
/* 2781 */                                 out.write("\t\tdocument.getElementById(\"insightTracereplink-right\").className = \"tbUnSelected_Right\";\n\t\tjavascript:hideDiv('OverviewTab');\n\t\tjavascript:showDiv('insight_OverviewTab');\n\t\tjavascript:hideDiv('insight_transactionTab');\n\t   \tjavascript:hideDiv('insight_databaseTab');\n\t\tjavascript:hideDiv('insight_tracesTab');\n\t}\n\telse if(tab==\"insight_transactionTab\")\n\t{\n\t\tdocument.getElementById(\"customreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"customreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"customreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightOverviewreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightOverviewreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightOverviewreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightTransactionreplink-left\").className = \"tbSelected_Left\";\n\t\tdocument.getElementById(\"insightTransactionreplink\").className = \"tbSelected_Middle\";\n");
/* 2782 */                                 out.write("\t\tdocument.getElementById(\"insightTransactionreplink-right\").className = \"tbSelected_Right\";\n\t\tdocument.getElementById(\"insightDBreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightDBreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightDBreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightTracereplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightTracereplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightTracereplink-right\").className = \"tbUnSelected_Right\";\n\t\t//document.location.hash = insightParam;\n\t\tjavascript:hideDiv('OverviewTab');\n\t\tjavascript:hideDiv('insight_OverviewTab');\n\t\tjavascript:showDiv('insight_transactionTab');\n\t   \tjavascript:hideDiv('insight_databaseTab');\n\t\tjavascript:hideDiv('insight_tracesTab');\n\t}\n\telse if(tab==\"insight_databaseTab\")\n\t{\n\t\tdocument.getElementById(\"customreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"customreplink\").className = \"tbUnSelected_Middle\";\n");
/* 2783 */                                 out.write("\t\tdocument.getElementById(\"customreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightOverviewreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightOverviewreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightOverviewreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightTransactionreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightTransactionreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightTransactionreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightDBreplink-left\").className = \"tbSelected_Left\";\n\t\tdocument.getElementById(\"insightDBreplink\").className = \"tbSelected_Middle\";\n\t\tdocument.getElementById(\"insightDBreplink-right\").className = \"tbSelected_Right\";\n\t\tdocument.getElementById(\"insightTracereplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightTracereplink\").className = \"tbUnSelected_Middle\";\n");
/* 2784 */                                 out.write("\t\tdocument.getElementById(\"insightTracereplink-right\").className = \"tbUnSelected_Right\";\n\t\tjavascript:hideDiv('OverviewTab');\n\t\tjavascript:hideDiv('insight_OverviewTab');\n\t\tjavascript:hideDiv('insight_transactionTab');\n\t   \tjavascript:showDiv('insight_databaseTab');\n\t\tjavascript:hideDiv('insight_tracesTab');\n\t}\n\telse if(tab==\"insight_tracesTab\")\n\t{\n\t\tdocument.getElementById(\"customreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"customreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"customreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightOverviewreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightOverviewreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightOverviewreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightTransactionreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightTransactionreplink\").className = \"tbUnSelected_Middle\";\n");
/* 2785 */                                 out.write("\t\tdocument.getElementById(\"insightTransactionreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightDBreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"insightDBreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"insightDBreplink-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"insightTracereplink-left\").className = \"tbSelected_Left\";\n\t\tdocument.getElementById(\"insightTracereplink\").className = \"tbSelected_Middle\";\n\t\tdocument.getElementById(\"insightTracereplink-right\").className = \"tbSelected_Right\";\n\t\tjavascript:hideDiv('OverviewTab');\n\t\tjavascript:hideDiv('insight_OverviewTab');\n\t\tjavascript:hideDiv('insight_transactionTab');\n\t   \tjavascript:hideDiv('insight_databaseTab');\n\t\tjavascript:showDiv('insight_tracesTab');\n\t}\n\tif(insightParam != null && insightParam != '')\n\t{\n\t\tdocument.location.hash = insightParam;\n\t}\n}\n\nfunction showDiv(divname)\n{\n\tvar na = divname.split(\"$\");\n\tfor(i=0;i<na.length;i++)\n\t{\n\t\tvar oDiv = document.getElementById(na[i]);\n");
/* 2786 */                                 out.write("\t\tif(oDiv)\n\t\t{\n\t\t\toDiv.style.display = \"block\";\n\t\t}\n\t}\n}\n\nfunction hideDiv(divname)\n{\n\tvar na = divname.split(\"$\");\n\tfor(i=0;i<na.length;i++)\n\t{\n\t\tvar oDiv = document.getElementById(na[i]);\n\t\tif(oDiv)\n\t\t{\n\t\t\toDiv.style.display = \"none\";\n\t\t}\n\t}\n} \n</script>\n<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\">\n\t\t\t<tr class=\"tabBtmLine\">\n   \t\t \t\t<td>\n           \t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" id=\"InnerTab\">\n                 \t\t<tr>\n                   \t\t\t<td width=\"17\"></td>\n\t\t    \t\t\t<td>\n\t\t    \t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\">\n                         \t\t\t<tr>\n                           \t\t\t\t<td id=\"customreplink-left\" class=\"tbSelected_Left\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n                           \t\t\t\t<td id=\"customreplink\" class=\"tbSelected_Middle\">\n\t\t\t\t   \t\t\t\t\t\t\t<a href=\"javascript:showHide('OverviewTab')\">&nbsp;<span class=\"tabLink\">");
/* 2787 */                                 out.print(FormatUtil.getString("Overview"));
/* 2788 */                                 out.write("</span></a>\n                           \t\t\t\t</td>\n                           \t\t\t\t<td id=\"customreplink-right\" class=\"tbSelected_Right\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n                         \t\t\t</tr>\n                     \t\t\t\t</table>\n                   \t\t\t</td>\n\t\t\t\t\t<td>\n                   \t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\">\n                         \t\t\t<tr>\n                           \t\t\t\t<td id=\"insightOverviewreplink-left\" class=\"tbUnSelected_Left\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n                           \t\t\t\t<td id=\"insightOverviewreplink\" class=\"tbUnSelected_Middle\">\n\t\t\t\t   \t\t\t\t<a href=\"javascript:showHide('insight_OverviewTab','");
/* 2789 */                                 if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                   return;
/* 2791 */                                 out.write("', '");
/* 2792 */                                 if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                   return;
/* 2794 */                                 out.write("','I/O/");
/* 2795 */                                 if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                   return;
/* 2797 */                                 out.write("/TW=H')\">&nbsp;<span class=\"tabLink\">");
/* 2798 */                                 out.print(FormatUtil.getString("am.webclient.dotnet.apminsight.apdexOverview"));
/* 2799 */                                 out.write("</span></a></td>\n                           \t\t\t\t<td id=\"insightOverviewreplink-right\" class=\"tbUnSelected_Right\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n                         \t\t\t</tr>\n                     \t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td>\n                   \t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\">\n                         \t\t\t<tr>\n                           \t\t\t\t<td id=\"insightTransactionreplink-left\" class=\"tbUnSelected_Left\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n                           \t\t\t\t<td id=\"insightTransactionreplink\" class=\"tbUnSelected_Middle\">\n\t\t\t\t   \t\t\t\t<a href=\"javascript:showHide('insight_transactionTab','");
/* 2800 */                                 if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                   return;
/* 2802 */                                 out.write("', '");
/* 2803 */                                 if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                   return;
/* 2805 */                                 out.write("','I/WT-G-RT/");
/* 2806 */                                 if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                   return;
/* 2808 */                                 out.write("/TW=H')\">&nbsp;<span class=\"tabLink\">");
/* 2809 */                                 out.print(FormatUtil.getString("am.webclient.dotnet.apminsight.webtransaction"));
/* 2810 */                                 out.write("</span></a></td>\n                           \t\t\t\t<td id=\"insightTransactionreplink-right\" class=\"tbUnSelected_Right\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n                         \t\t\t</tr>\n                     \t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td>\n                   \t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\">\n                         \t\t\t<tr>\n                           \t\t\t\t<td id=\"insightDBreplink-left\" class=\"tbUnSelected_Left\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n                           \t\t\t\t<td id=\"insightDBreplink\" class=\"tbUnSelected_Middle\">\n\t\t\t\t   \t\t\t\t<a href=\"javascript:showHide('insight_databaseTab','");
/* 2811 */                                 if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                   return;
/* 2813 */                                 out.write("', '");
/* 2814 */                                 if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                   return;
/* 2816 */                                 out.write("','I/DB-G-RT/");
/* 2817 */                                 if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                   return;
/* 2819 */                                 out.write("/TW=H')\">&nbsp;<span class=\"tabLink\">");
/* 2820 */                                 out.print(FormatUtil.getString("am.webclient.dotnet.apminsight.database"));
/* 2821 */                                 out.write("</span></a></td>\n                           \t\t\t\t<td id=\"insightDBreplink-right\" class=\"tbUnSelected_Right\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n                         \t\t\t</tr>\n                     \t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td>\n                   \t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\">\n                         \t\t\t<tr>\n                           \t\t\t\t<td id=\"insightTracereplink-left\" class=\"tbUnSelected_Left\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n                           \t\t\t\t<td id=\"insightTracereplink\" class=\"tbUnSelected_Middle\">\n\t\t\t\t   \t\t\t\t<a href=\"javascript:showHide('insight_tracesTab','");
/* 2822 */                                 if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                   return;
/* 2824 */                                 out.write("', '");
/* 2825 */                                 if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                   return;
/* 2827 */                                 out.write("','I/TR/");
/* 2828 */                                 if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                   return;
/* 2830 */                                 out.write("/TW=H')\">&nbsp;<span class=\"tabLink\">");
/* 2831 */                                 out.print(FormatUtil.getString("am.webclient.dotnet.apminsight.traces"));
/* 2832 */                                 out.write("</span></a></td>\n                           \t\t\t\t<td id=\"insightTracereplink-right\" class=\"tbUnSelected_Right\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n                         \t\t\t</tr>\n                     \t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t  \t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</table>\n\t\t<br>\n\t\t<div id=\"OverviewTab\">\n\t\t");
/* 2833 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2834 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2838 */                             if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2839 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                             }
/*      */                             
/* 2842 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2843 */                             out.write("\n<div id=\"edit\" style=\"DISPLAY: none\">\n");
/* 2844 */                             if (_jspx_meth_c_005fchoose_005f0(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 2846 */                             out.write("\n<br>\n</div>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n  <tr>\n    <td width=\"573\" height=\"123\" valign=\"top\"> <table width=\"96%\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" class=\"lrtbdarkborder\">\n        <tr>\n          <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 2847 */                             out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 2848 */                             out.write(" </td>\n        </tr>\n        <tr>\n          <td width=\"32%\" class=\"monitorinfoodd\">");
/* 2849 */                             out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2850 */                             out.write("</td>\n          <td width=\"68%\" class=\"monitorinfoodd\"> ");
/* 2851 */                             out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 2852 */                             out.write("\n          </td>\n        </tr>\n\t\t");
/* 2853 */                             out.write("<!--$Id$-->\n");
/*      */                             
/* 2855 */                             String hostName = "localhost";
/*      */                             try {
/* 2857 */                               hostName = java.net.InetAddress.getLocalHost().getHostName();
/*      */                             } catch (Exception ex) {
/* 2859 */                               ex.printStackTrace();
/*      */                             }
/* 2861 */                             String portNumber = System.getProperty("webserver.port");
/* 2862 */                             String styleClass = "monitorinfoodd";
/* 2863 */                             if ((request.getAttribute("amcreated") != null) && (((String)request.getAttribute("amcreated")).equals("YES"))) {
/* 2864 */                               styleClass = "whitegrayborder-conf-mon";
/*      */                             }
/*      */                             
/* 2867 */                             out.write(10);
/*      */                             
/* 2869 */                             PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2870 */                             _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2871 */                             _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 2873 */                             _jspx_th_logic_005fpresent_005f0.setRole("ENTERPRISEADMIN");
/* 2874 */                             int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2875 */                             if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                               for (;;) {
/* 2877 */                                 out.write("\n<tr>\n  <td width=\"30%\" class=\"");
/* 2878 */                                 out.print(styleClass);
/* 2879 */                                 out.write(34);
/* 2880 */                                 out.write(62);
/* 2881 */                                 out.print(FormatUtil.getString("am.webclient.managedserver.name"));
/* 2882 */                                 out.write(" </td>\n  <td width=\"70%\" class=\"");
/* 2883 */                                 out.print(styleClass);
/* 2884 */                                 out.write(34);
/* 2885 */                                 out.write(62);
/* 2886 */                                 out.print(hostName);
/* 2887 */                                 out.write(95);
/* 2888 */                                 out.print(portNumber);
/* 2889 */                                 out.write("</td>\n</tr>\n");
/* 2890 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2891 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2895 */                             if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2896 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                             }
/*      */                             
/* 2899 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2900 */                             out.write(10);
/* 2901 */                             out.write("\n        <tr>\n          <td class=\"monitorinfoeven\" valign=\"top\">");
/* 2902 */                             out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2903 */                             out.write("</td>\n          <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2904 */                             out.print(resID);
/* 2905 */                             out.write("&attributeid=");
/* 2906 */                             out.print((String)systeminfo.get("HEALTHID"));
/* 2907 */                             out.write("')\">");
/* 2908 */                             out.print(getSeverityImageForHealth(alert.getProperty(resID + "#" + (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID"))));
/* 2909 */                             out.write("</a>\n\t\t  ");
/* 2910 */                             out.print(getHideAndShowRCAMessage(alert.getProperty(resID + "#" + (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID") + "#" + "MESSAGE"), (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID"), alert.getProperty(resID + "#" + (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID")), resID));
/* 2911 */                             out.write("\n\t\t  ");
/* 2912 */                             if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resID, (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID")) != 0) {
/* 2913 */                               out.write("\n\t\t  <br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 2914 */                               out.print(resID + "_" + (String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID"));
/* 2915 */                               out.write("&monitortype=");
/* 2916 */                               out.print(resourcetype);
/* 2917 */                               out.write("')\">");
/* 2918 */                               out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 2919 */                               out.write("</a></span>\n          ");
/*      */                             }
/* 2921 */                             out.write("\n\t\t  </td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 2922 */                             out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 2923 */                             out.write("</td>\n\t\t  ");
/*      */                             
/* 2925 */                             String TYPE1 = null;
/* 2926 */                             TYPE1 = (String)systeminfo.get("TYPE");
/* 2927 */                             if ((TYPE1 != null) && (TYPE1.equals("TELNET")))
/*      */                             {
/* 2929 */                               TYPE1 = "Telnet";
/*      */ 
/*      */                             }
/* 2932 */                             else if ((TYPE1 != null) && (TYPE1.equals("JMX1.2-MX4J-RMI")))
/*      */                             {
/* 2934 */                               TYPE1 = "JMX Applications";
/*      */                             }
/* 2936 */                             TYPE1 = FormatUtil.getString(TYPE1);
/*      */                             
/* 2938 */                             out.write("\n\t\t     <td class=\"monitorinfoodd\">");
/* 2939 */                             out.print(TYPE1);
/* 2940 */                             out.write("</td>\n\n        </tr>\n\n\t<tr>\n\t<td class=\"monitorinfoodd\">");
/* 2941 */                             out.print(FormatUtil.getString("am.webclient.common.port.text"));
/* 2942 */                             out.write("</td>\n\t<td class=\"monitorinfoodd\">");
/*      */                             
/* 2944 */                             OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 2945 */                             _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 2946 */                             _jspx_th_c_005fout_005f12.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 2948 */                             _jspx_th_c_005fout_005f12.setValue("${param.smtpPort}");
/* 2949 */                             int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 2950 */                             if (_jspx_eval_c_005fout_005f12 != 0) {
/* 2951 */                               if (_jspx_eval_c_005fout_005f12 != 1) {
/* 2952 */                                 out = _jspx_page_context.pushBody();
/* 2953 */                                 _jspx_th_c_005fout_005f12.setBodyContent((BodyContent)out);
/* 2954 */                                 _jspx_th_c_005fout_005f12.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 2957 */                                 out.print((String)systeminfo.get("PORTNO"));
/* 2958 */                                 int evalDoAfterBody = _jspx_th_c_005fout_005f12.doAfterBody();
/* 2959 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 2962 */                               if (_jspx_eval_c_005fout_005f12 != 1) {
/* 2963 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 2966 */                             if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 2967 */                               this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f12); return;
/*      */                             }
/*      */                             
/* 2970 */                             this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f12);
/* 2971 */                             out.write("\n\t</td>\n\t</tr>\n\n\t");
/*      */                             
/* 2973 */                             if (resourcetype.equals("PHP"))
/*      */                             {
/*      */ 
/* 2976 */                               out.write("\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 2977 */                               out.print(FormatUtil.getString("am.webclient.php.version.text"));
/* 2978 */                               out.write("</td>\n          <td class=\"monitorinfoodd\">");
/* 2979 */                               out.print(hm.get("version"));
/* 2980 */                               out.write("</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 2981 */                               out.print(FormatUtil.getString("am.webclient.php.interface.text"));
/* 2982 */                               out.write("</td>\n          <td class=\"monitorinfoeven\">");
/* 2983 */                               out.print(hm.get("relation"));
/* 2984 */                               out.write("</td>\n        </tr>\n        ");
/*      */                             }
/*      */                             
/*      */ 
/* 2988 */                             out.write("\n        ");
/*      */                             
/* 2990 */                             EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2991 */                             _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2992 */                             _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 2994 */                             _jspx_th_logic_005fempty_005f0.setName("systeminfo");
/* 2995 */                             int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2996 */                             if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                               for (;;) {
/* 2998 */                                 out.write("\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 2999 */                                 out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 3000 */                                 out.write("</td>\n          <td class=\"monitorinfoeven\">-&nbsp;</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3001 */                                 out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 3002 */                                 out.write("</td>\n          <td class=\"monitorinfoodd\">-</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3003 */                                 out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 3004 */                                 out.write("</td>\n          <td class=\"monitorinfoeven\">-</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3005 */                                 out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 3006 */                                 out.write("</td>\n          <td class=\"monitorinfoodd\">-</td>\n        </tr>\n        ");
/* 3007 */                                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 3008 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3012 */                             if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 3013 */                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                             }
/*      */                             
/* 3016 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 3017 */                             out.write(32);
/*      */                             
/* 3019 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3020 */                             _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 3021 */                             _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3023 */                             _jspx_th_logic_005fnotEmpty_005f0.setName("systeminfo");
/* 3024 */                             int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 3025 */                             if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                               for (;;) {
/* 3027 */                                 out.write("\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3028 */                                 out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 3029 */                                 out.write("</td>\n\t\t   ");
/*      */                                 
/* 3031 */                                 if (systeminfo.get("host_resid") != null)
/*      */                                 {
/* 3033 */                                   out.write("\n\t\t    <td class=\"monitorinfoeven\"><a href=\"showresource.do?resourceid=");
/* 3034 */                                   out.print(systeminfo.get("host_resid"));
/* 3035 */                                   out.write("&method=showResourceForResourceID\" class=\"staticlinks\" title=\"");
/* 3036 */                                   out.print(systeminfo.get("HOSTNAME"));
/* 3037 */                                   out.write(34);
/* 3038 */                                   out.write(32);
/* 3039 */                                   out.write(62);
/* 3040 */                                   out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 3041 */                                   out.write("&nbsp;(");
/* 3042 */                                   out.print(systeminfo.get("HOSTIP"));
/* 3043 */                                   out.write(")</a></td>\n\t\t\t");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3048 */                                   out.write("\n             <td class=\"monitorinfoeven\" title=\"");
/* 3049 */                                   out.print(systeminfo.get("HOSTNAME"));
/* 3050 */                                   out.write(34);
/* 3051 */                                   out.write(32);
/* 3052 */                                   out.write(62);
/* 3053 */                                   out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 3054 */                                   out.write("&nbsp;(");
/* 3055 */                                   out.print(systeminfo.get("HOSTIP"));
/* 3056 */                                   out.write(")</td>\n\t\t\t");
/*      */                                 }
/* 3058 */                                 out.write("\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3059 */                                 out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 3060 */                                 out.write("</td>\n          <td class=\"monitorinfoodd\">");
/* 3061 */                                 out.print(FormatUtil.getString((String)systeminfo.get("HOSTOS")));
/* 3062 */                                 out.write("</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3063 */                                 out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 3064 */                                 out.write("</td>\n          <td class=\"monitorinfoodd\">");
/* 3065 */                                 out.print(formatDT((Long)systeminfo.get("LASTDC")));
/* 3066 */                                 out.write("</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3067 */                                 out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 3068 */                                 out.write("</td>\n          <td class=\"monitorinfoeven\">");
/* 3069 */                                 out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 3070 */                                 out.write("</td>\n        </tr>\n        ");
/* 3071 */                                 org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MyField_trstrip.jsp", out, false);
/* 3072 */                                 out.write("\n        ");
/* 3073 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3074 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3078 */                             if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3079 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                             }
/*      */                             
/* 3082 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3083 */                             out.write("\n\n    </table></td>\n    <td width=\"355\" valign=\"top\"><table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n        <tr>\n          <td height=\"28\" colspan=\"3\" class=\"tableheadingbborder\">");
/* 3084 */                             out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 3085 */                             out.write("</td>\n        </tr>\n        <tr>\n          <td colspan=\"3\"> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"right\" >\n              <tr>\n                <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3086 */                             if (_jspx_meth_c_005fout_005f13(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3088 */                             out.write("&period=1&resourcename=");
/* 3089 */                             if (_jspx_meth_c_005fout_005f14(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3091 */                             out.write("')\">\n                  <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3092 */                             out.print(seven_days_text_1);
/* 3093 */                             out.write("\"></a></td>\n                <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3094 */                             if (_jspx_meth_c_005fout_005f15(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3096 */                             out.write("&period=2&resourcename=");
/* 3097 */                             if (_jspx_meth_c_005fout_005f16(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3099 */                             out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3100 */                             out.print(thiry_days_text_1);
/* 3101 */                             out.write("\"></a></td>\n              </tr>\n            </table></td>\n          ");
/*      */                             
/* 3103 */                             wlsGraph.setParam(resID, "AVAILABILITY");
/*      */                             
/* 3105 */                             out.write("\n        </tr>\n        <tr align=\"center\">\n          <td height=\"28\" colspan=\"3\"> ");
/* 3106 */                             if (_jspx_meth_awolf_005fpiechart_005f0(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3108 */                             out.write("</td>\n        </tr>\n\t\t<tr>\n\t\t<td width=\"39%\" height=\"25\" class=\"yellowgrayborder\" colspan=\"2\" title=\"");
/* 3109 */                             out.print(tipCurStatus);
/* 3110 */                             out.write(34);
/* 3111 */                             out.write(62);
/* 3112 */                             out.print(FormatUtil.getString("am.webclient.common.currentstatus.text"));
/* 3113 */                             out.write(" : <a style=\"position:relative; top:2px;\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3114 */                             out.print(resID);
/* 3115 */                             out.write("&attributeid=");
/* 3116 */                             out.print(availabilitykeys.get(resourcetype));
/* 3117 */                             out.write("&alertconfigurl=");
/* 3118 */                             out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "&attributeIDs=" + availabilitykeys.get(resourcetype) + "&attributeToSelect=" + availabilitykeys.get(resourcetype) + "&redirectto=" + encodeurl));
/* 3119 */                             out.write("')\">\n\t\t");
/* 3120 */                             out.print(getSeverityImageForAvailability(alert.getProperty(resID + "#" + availabilitykeys.get(resourcetype))));
/* 3121 */                             out.write("\n\t\t</a> </td>\n\n\t\t<td width=\"51%\" align=\"right\" class=\"yellowgrayborder\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3122 */                             out.print(resID);
/* 3123 */                             out.write("&attributeIDs=");
/* 3124 */                             out.print(availabilitykeys.get(resourcetype));
/* 3125 */                             out.write(44);
/* 3126 */                             out.print(healthkeys.get(resourcetype));
/* 3127 */                             out.write("&attributeToSelect=");
/* 3128 */                             out.print(availabilitykeys.get(resourcetype));
/* 3129 */                             out.write("&redirectto=");
/* 3130 */                             out.print(encodeurl);
/* 3131 */                             out.write("\" class=\"links\">");
/* 3132 */                             out.print(ALERTCONFIG_TEXT);
/* 3133 */                             out.write("</a>&nbsp;\n\t\t</td>\n\t\t</tr>\n      </table></td>\n  </tr>\n</table>\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 3134 */                             org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MyField_div.jsp", out, false);
/* 3135 */                             out.write("</td></tr></table>\n<br>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbtborder\">\n  <tr>\n    <td height=\"26\" class=\"tableheading\">");
/* 3136 */                             out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 3137 */                             out.write(32);
/* 3138 */                             out.write(45);
/* 3139 */                             out.write(32);
/* 3140 */                             out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3141 */                             out.write("</td>\n  </tr>\n</table>\n");
/*      */                             
/* 3143 */                             wlsGraph.setParam(resID, "RESPONSETIME");
/*      */                             
/*      */ 
/* 3146 */                             out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n  <tr>\n    <td width=\"405\" height=\"127\" valign=\"top\"> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"70%\">\n        ");
/*      */                             
/* 3148 */                             if ((responsetimeid.equals("1902")) || (responsetimeid.equals("1402")) || (responsetimeid.equals("1852")) || (responsetimeid.equals("1862")) || (responsetimeid.equals("1752")) || (responsetimeid.equals("2002")) || (responsetimeid.equals("2302")) || (responsetimeid.equals("161")))
/*      */                             {
/* 3150 */                               out.write("\n        <tr>\n          <td width=\"90%\" height=\"35\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3151 */                               if (_jspx_meth_c_005fout_005f17(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3153 */                               out.write("&attributeid=");
/* 3154 */                               out.print(responsetimeid);
/* 3155 */                               out.write("&period=-7',740,550)\">\n            <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3156 */                               out.print(seven_days_text_1);
/* 3157 */                               out.write("\"></a></td>\n          <td width=\"10%\" height=\"35\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3158 */                               if (_jspx_meth_c_005fout_005f18(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3160 */                               out.write("&attributeid=");
/* 3161 */                               out.print(responsetimeid);
/* 3162 */                               out.write("&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3163 */                               out.print(thiry_days_text_1);
/* 3164 */                               out.write("\"></a></td>\n        </tr>\n        ");
/*      */                             }
/* 3166 */                             out.write("\n        <tr>\n          <td colspan=\"2\"> ");
/*      */                             
/* 3168 */                             TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 3169 */                             _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 3170 */                             _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3172 */                             _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("wlsGraph");
/*      */                             
/* 3174 */                             _jspx_th_awolf_005ftimechart_005f0.setWidth("320");
/*      */                             
/* 3176 */                             _jspx_th_awolf_005ftimechart_005f0.setHeight("170");
/*      */                             
/* 3178 */                             _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*      */                             
/* 3180 */                             _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(xaxis_time);
/*      */                             
/* 3182 */                             _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(yaxis_restime);
/*      */                             
/* 3184 */                             _jspx_th_awolf_005ftimechart_005f0.setDateFormat("HH:mm");
/* 3185 */                             int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 3186 */                             if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 3187 */                               if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3188 */                                 out = _jspx_page_context.pushBody();
/* 3189 */                                 _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 3190 */                                 _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3193 */                                 out.write("\n            ");
/* 3194 */                                 int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 3195 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3198 */                               if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3199 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3202 */                             if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 3203 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                             }
/*      */                             
/* 3206 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 3207 */                             out.write(" </td>\n        </tr>\n      </table>\n    </td>\n    <td width=\"562\" valign=\"top\"> <br> <br>\n              ");
/*      */                             
/*      */ 
/* 3210 */                             long maxtime = wlsGraph.getLastDataCollectedTime();
/* 3211 */                             long curvalue = 0L;
/* 3212 */                             int health = -1;
/* 3213 */                             String dataquery = "select RESPONSETIME,HEALTH from AM_ManagedObjectData where AM_ManagedObjectData.RESID=" + resID + " and COLLECTIONTIME=" + maxtime;
/* 3214 */                             rs = AMConnectionPool.executeQueryStmt(dataquery);
/* 3215 */                             if (rs.next())
/*      */                             {
/* 3217 */                               curvalue = rs.getLong(1);
/* 3218 */                               health = rs.getInt(2);
/*      */                             }
/* 3220 */                             rs.close();
/*      */                             
/* 3222 */                             out.write("\n      <table align=\"left\" width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n        <tr>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3223 */                             if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3225 */                             out.write("</span></td>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3226 */                             if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3228 */                             out.write("</span></td>\n          <td class=\"columnheadingnotop\" colspan=\"2\"><span class=\"bodytextbold\">\n          ");
/*      */                             
/* 3230 */                             if (curvalue != -1L)
/*      */                             {
/* 3232 */                               out.write("\n          ");
/* 3233 */                               if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3235 */                               out.write("</span>\n          ");
/*      */                             } else {
/* 3237 */                               out.println("&nbsp;");
/*      */                             }
/* 3239 */                             out.write("\n          </td>\n        </tr>\n        <tr>\n\n          <td  height=\"25\" class=\"whitegrayborder\" title=\"");
/* 3240 */                             out.print(tipCurValue);
/* 3241 */                             out.write(34);
/* 3242 */                             out.write(62);
/* 3243 */                             out.print(FormatUtil.getString("am.webclient.common.current.text"));
/* 3244 */                             out.write(32);
/* 3245 */                             out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 3246 */                             out.write("</td>\n          ");
/*      */                             
/* 3248 */                             if (curvalue != -1L)
/*      */                             {
/*      */ 
/* 3251 */                               out.write("\n          <td  height=\"25\" class=\"whitegrayborder\">");
/* 3252 */                               out.print(formatNumber(curvalue));
/* 3253 */                               out.write(32);
/* 3254 */                               out.print(FormatUtil.getString("ms"));
/* 3255 */                               out.write("</td>\n\n            ");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/*      */ 
/* 3261 */                               out.write("\n          <td  height=\"25\" class=\"whitegrayborder\" colspan=\"3\" >");
/* 3262 */                               out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 3263 */                               out.write(" </td>\n            ");
/*      */                             }
/*      */                             
/* 3266 */                             out.write(10);
/* 3267 */                             out.write(9);
/* 3268 */                             out.write(32);
/*      */                             
/* 3270 */                             if (curvalue != -1L)
/*      */                             {
/*      */ 
/* 3273 */                               out.write("\n          <Td width=\"16%\" class=\"whitegrayborder\">  &nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3274 */                               out.print(resID);
/* 3275 */                               out.write("&attributeid=");
/* 3276 */                               out.print(responsetimeid);
/* 3277 */                               out.write("&alertconfigurl=");
/* 3278 */                               out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs" + responsetimeid + "attributeToSelect=" + responsetimeid + "&redirectto=" + encodeurl));
/* 3279 */                               out.write("')\">");
/* 3280 */                               out.print(getSeverityImage(alert.getProperty(resID + "#" + responsetimeid)));
/* 3281 */                               out.write(" </a>\n          </td>\n          ");
/*      */                             }
/*      */                             
/*      */ 
/* 3285 */                             out.write("\n        </tr>\n        <tr>\n          <td  colspan=\"4\" height=\"35\" class=\"yellowgrayborder\" align=\"right\" ><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\" title=\"\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3286 */                             out.print(resID);
/* 3287 */                             out.write("&attributeIDs=");
/* 3288 */                             out.print(responsetimeid);
/* 3289 */                             out.write("&attributeToSelect=");
/* 3290 */                             out.print(responsetimeid);
/* 3291 */                             out.write("&redirectto=");
/* 3292 */                             out.print(encodeurl);
/* 3293 */                             out.write("\" class=\"staticlinks\">");
/* 3294 */                             out.print(ALERTCONFIG_TEXT);
/* 3295 */                             out.write("</a>&nbsp;</td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td height=\"26\" class=\"tablebottom\">&nbsp;</td>\n  </tr>\n</table>\n\n");
/* 3296 */                             if (resourcetype.equals("PHP"))
/*      */                             {
/* 3298 */                               wlsGraph.setParam(resID, "PHPPAGEFAULT");
/* 3299 */                               if ((!String.valueOf(hm.get("os_name")).startsWith("win")) || (!String.valueOf(hm.get("os_name")).startsWith("WIN")))
/*      */                               {
/* 3301 */                                 responsetimeid = "2303";
/*      */                                 
/*      */ 
/* 3304 */                                 out.write("\n<br>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"26\" class=\"tableheading\">");
/* 3305 */                                 out.print(FormatUtil.getString("am.webclient.php.pagefaults.text"));
/* 3306 */                                 out.write(32);
/* 3307 */                                 out.write(45);
/* 3308 */                                 out.write(32);
/* 3309 */                                 out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3310 */                                 out.write("</td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n<tr>\n    <td width=\"405\" height=\"127\" valign=\"top\"> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"70%\">\n        <tr>\n          <td width=\"90%\" height=\"35\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3311 */                                 if (_jspx_meth_c_005fout_005f19(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3313 */                                 out.write("&attributeid=2303&period=-7',740,550)\">\n            <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3314 */                                 out.print(seven_days_text_1);
/* 3315 */                                 out.write("\"></a></td>\n          <td width=\"10%\" height=\"35\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3316 */                                 if (_jspx_meth_c_005fout_005f20(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3318 */                                 out.write("&attributeid=2303&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3319 */                                 out.print(thiry_days_text_1);
/* 3320 */                                 out.write("\"></a></td>\n        </tr>\n        <tr>\n          <td colspan=\"2\"> ");
/*      */                                 
/* 3322 */                                 TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 3323 */                                 _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 3324 */                                 _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 3326 */                                 _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("wlsGraph");
/*      */                                 
/* 3328 */                                 _jspx_th_awolf_005ftimechart_005f1.setWidth("320");
/*      */                                 
/* 3330 */                                 _jspx_th_awolf_005ftimechart_005f1.setHeight("170");
/*      */                                 
/* 3332 */                                 _jspx_th_awolf_005ftimechart_005f1.setLegend("false");
/*      */                                 
/* 3334 */                                 _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(xaxis_time);
/*      */                                 
/* 3336 */                                 _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(yaxis_pagefaults);
/*      */                                 
/* 3338 */                                 _jspx_th_awolf_005ftimechart_005f1.setDateFormat("HH:mm");
/* 3339 */                                 int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 3340 */                                 if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 3341 */                                   if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3342 */                                     out = _jspx_page_context.pushBody();
/* 3343 */                                     _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 3344 */                                     _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3347 */                                     out.write("\n            ");
/* 3348 */                                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 3349 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3352 */                                   if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3353 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3356 */                                 if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 3357 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                                 }
/*      */                                 
/* 3360 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 3361 */                                 out.write(" </td>\n        </tr>\n      </table>\n    </td>\n    <td width=\"562\" valign=\"top\"> <br> <br>\n    <table align=\"left\" width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n        <tr>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3362 */                                 if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3364 */                                 out.write("</span></td>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3365 */                                 if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3367 */                                 out.write("</span></td>\n          <td class=\"columnheadingnotop\" colspan=\"2\"><span class=\"bodytextbold\">");
/* 3368 */                                 if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3370 */                                 out.write("</span></td>\n        </tr>\n        <tr>\n\n        <td  height=\"25\" class=\"whitegrayborder\" title=\"");
/* 3371 */                                 out.print(tipCurValue);
/* 3372 */                                 out.write(34);
/* 3373 */                                 out.write(62);
/* 3374 */                                 out.print(FormatUtil.getString("am.webclient.php.currentpagefault.text"));
/* 3375 */                                 out.write("</td>\n\n          ");
/*      */                                 
/* 3377 */                                 if ((hm.get("pagefaults") != null) && (!hm.get("pagefaults").equals("-1")))
/*      */                                 {
/*      */ 
/* 3380 */                                   out.write("\n          <td  height=\"25\" class=\"whitegrayborder\">");
/* 3381 */                                   out.print(hm.get("pagefaults"));
/* 3382 */                                   out.write(" </td>\n\n            ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 3388 */                                   out.write("\n          <td  height=\"25\" class=\"whitegrayborder\" colspan=\"3\" >");
/* 3389 */                                   out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 3390 */                                   out.write(" </td>\n            ");
/*      */                                 }
/*      */                                 
/* 3393 */                                 out.write(10);
/* 3394 */                                 out.write(9);
/* 3395 */                                 out.write(32);
/*      */                                 
/* 3397 */                                 if ((hm.get("pagefaults") != null) && (!hm.get("pagefaults").equals("-1")))
/*      */                                 {
/*      */ 
/* 3400 */                                   out.write("\n          <Td width=\"16%\" class=\"whitegrayborder\">  &nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3401 */                                   out.print(resID);
/* 3402 */                                   out.write("&attributeid=");
/* 3403 */                                   out.print(responsetimeid);
/* 3404 */                                   out.write("&alertconfigurl=");
/* 3405 */                                   out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs" + responsetimeid + "attributeToSelect=" + responsetimeid + "&redirectto=" + encodeurl));
/* 3406 */                                   out.write("')\">");
/* 3407 */                                   out.print(getSeverityImage(alert.getProperty(resID + "#" + responsetimeid)));
/* 3408 */                                   out.write(" </a>\n          </td>\n          ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3412 */                                 out.write("\n        </tr>\n        <tr>\n\t\t  <td  height=\"25\" class=\"whitegrayborder\">");
/* 3413 */                                 out.print(FormatUtil.getString("am.webclient.php.swaps.text"));
/* 3414 */                                 out.write("</td>\n\t\t  ");
/* 3415 */                                 if ((hm.get("swaps") != null) && (!hm.get("swaps").equals("-1")))
/*      */                                 {
/* 3417 */                                   out.write("\n\t\t  <td height=\"25\" class=\"whitegrayborder\">");
/* 3418 */                                   out.print(hm.get("swaps"));
/* 3419 */                                   out.write(" </td>\n\t\t  ");
/*      */                                 }
/*      */                                 else {
/* 3422 */                                   out.write("\n\t\t  <td  height=\"25\" class=\"whitegrayborder\" colspan=\"3\" >");
/* 3423 */                                   out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 3424 */                                   out.write(" </td>\n\t\t  ");
/*      */                                 }
/* 3426 */                                 out.write("\n\t\t  ");
/* 3427 */                                 if ((hm.get("swaps") != null) && (!hm.get("swaps").equals("-1")))
/*      */                                 {
/* 3429 */                                   out.write("\n\t\t  <Td width=\"16%\" class=\"whitegrayborder\">  &nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3430 */                                   out.print(resID);
/* 3431 */                                   out.write("&attributeid=2304&alertconfigurl=");
/* 3432 */                                   out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs=2304&attributeToSelect=2304&redirectto=" + encodeurl));
/* 3433 */                                   out.write("')\">");
/* 3434 */                                   out.print(getSeverityImage(alert.getProperty(resID + "#" + 2304)));
/* 3435 */                                   out.write(" </a>\n\t\t  </td>\n\t\t  ");
/*      */                                 }
/* 3437 */                                 out.write("\n\t\t</tr>\n\t\t<tr>\n\t\t  <td  height=\"25\" class=\"whitegrayborder\">");
/* 3438 */                                 out.print(FormatUtil.getString("am.webclient.php.usertime.text"));
/* 3439 */                                 out.write("</td>\n\t\t  ");
/* 3440 */                                 if ((hm.get("usertime") != null) && (!hm.get("usertime").equals("-1")))
/*      */                                 {
/* 3442 */                                   out.write("\n\t\t  <td height=\"25\" class=\"whitegrayborder\">");
/* 3443 */                                   out.print(hm.get("usertime"));
/* 3444 */                                   out.write(" </td>\n\t\t  ");
/*      */                                 }
/*      */                                 else {
/* 3447 */                                   out.write("\n\t\t  <td  height=\"25\" class=\"whitegrayborder\" colspan=\"3\" >");
/* 3448 */                                   out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 3449 */                                   out.write(" </td>\n\t\t  ");
/*      */                                 }
/* 3451 */                                 out.write("\n\t\t  ");
/* 3452 */                                 if ((hm.get("usertime") != null) && (!hm.get("usertime").equals("-1")))
/*      */                                 {
/* 3454 */                                   out.write("\n\t\t  <Td width=\"16%\" class=\"whitegrayborder\">  &nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3455 */                                   out.print(resID);
/* 3456 */                                   out.write("&attributeid=2305&alertconfigurl=");
/* 3457 */                                   out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs=2305&attributeToSelect=2305&redirectto=" + encodeurl));
/* 3458 */                                   out.write("')\">");
/* 3459 */                                   out.print(getSeverityImage(alert.getProperty(resID + "#" + 2305)));
/* 3460 */                                   out.write(" </a>\n\t\t  </td>\n\t\t  ");
/*      */                                 }
/* 3462 */                                 out.write("\n\t\t</tr>\n        <tr>\n          <td  colspan=\"4\" height=\"35\" class=\"yellowgrayborder\" align=\"right\" ><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\" width=\"20\" height=\"20\" style=\"width: 13px; height: 13px;\" title=\"\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3463 */                                 out.print(resID);
/* 3464 */                                 out.write("&attributeIDs=");
/* 3465 */                                 out.print(responsetimeid);
/* 3466 */                                 out.write(",2304,2305&attributeToSelect=");
/* 3467 */                                 out.print(responsetimeid);
/* 3468 */                                 out.write("&redirectto=");
/* 3469 */                                 out.print(encodeurl);
/* 3470 */                                 out.write("\" class=\"staticlinks\">");
/* 3471 */                                 out.print(ALERTCONFIG_TEXT);
/* 3472 */                                 out.write("</a>&nbsp;</td>\n        </tr>\n    </table>\n          </td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td height=\"26\" class=\"tablebottom\">&nbsp;</td>\n  </tr>\n</table>\n");
/*      */                               }
/*      */                               
/* 3475 */                               if ((request.getAttribute("apache_stats") != null) && (!request.getAttribute("apache_stats").equals("null")))
/*      */                               {
/* 3477 */                                 ArrayList apache_stats = (ArrayList)request.getAttribute("apache_stats");
/* 3478 */                                 String bytespersec = FormatUtil.getString("am.webclient.nodata.text");
/* 3479 */                                 String busyservers = FormatUtil.getString("am.webclient.nodata.text");
/* 3480 */                                 String idleservers = FormatUtil.getString("No_Data_Available");
/* 3481 */                                 String busyserver_id = "2306";
/* 3482 */                                 String idleserver_id = "2307";
/* 3483 */                                 String bytespersec_id = "2308";
/* 3484 */                                 if (!String.valueOf(apache_stats.get(0)).equals("2"))
/*      */                                 {
/* 3486 */                                   busyservers = (String)apache_stats.get(1);
/* 3487 */                                   idleservers = (String)apache_stats.get(2);
/* 3488 */                                   if (!String.valueOf(apache_stats.get(3)).equals("-1"))
/*      */                                   {
/* 3490 */                                     bytespersec = (String)apache_stats.get(3);
/*      */                                   }
/*      */                                 }
/* 3493 */                                 String apache_id = (String)request.getAttribute("apache_id");
/*      */                                 
/* 3495 */                                 out.write("\n    <br>\n");
/* 3496 */                                 out.write("<!--$Id$-->\n");
/*      */                                 
/* 3498 */                                 ArrayList resID1s = new ArrayList();
/* 3499 */                                 resID1s.add(resID);
/* 3500 */                                 attribIDs.add("" + busyserver_id);
/* 3501 */                                 attribIDs.add("" + idleserver_id);
/* 3502 */                                 attribIDs.add("" + bytespersec_id);
/* 3503 */                                 Properties alert1 = getStatus(resID1s, attribIDs);
/* 3504 */                                 wlsGraph.setParam(apache_id, "BUSYSERVERS");
/* 3505 */                                 tipCurValue = FormatUtil.getString("am.webclient.apache.tooltip.busyservers");
/* 3506 */                                 String busyServerSttributeId = "" + busyserver_id;
/* 3507 */                                 String bytedPerSecId = "" + bytespersec_id;
/*      */                                 
/* 3509 */                                 out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"26\" width=\"50%\" align=\"left\" class=\"tableheading\">");
/* 3510 */                                 out.print(FormatUtil.getString("am.webclient.apache.busyservers"));
/* 3511 */                                 out.write(32);
/* 3512 */                                 out.write(45);
/* 3513 */                                 out.write(32);
/* 3514 */                                 out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3515 */                                 out.write("</td>\n    <td height=\"26\" width=\"50%\" align=\"left\" class=\"tableheading\">");
/* 3516 */                                 out.print(FormatUtil.getString("am.webclient.apache.bytestransferred"));
/* 3517 */                                 out.write(32);
/* 3518 */                                 out.write(45);
/* 3519 */                                 out.write(32);
/* 3520 */                                 out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3521 */                                 out.write("</td>\n  </tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n<tr>\n<td width=\"49%\" height=\"25\">\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"rborder\">\n <td width=\"300\" height=\"127\" colspan=\"2\"> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"50%\">\n\n\n        <tr>\n          <td colspan=\"2\" > ");
/*      */                                 
/* 3523 */                                 TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 3524 */                                 _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 3525 */                                 _jspx_th_awolf_005ftimechart_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 3527 */                                 _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("wlsGraph");
/*      */                                 
/* 3529 */                                 _jspx_th_awolf_005ftimechart_005f2.setWidth("300");
/*      */                                 
/* 3531 */                                 _jspx_th_awolf_005ftimechart_005f2.setHeight("170");
/*      */                                 
/* 3533 */                                 _jspx_th_awolf_005ftimechart_005f2.setLegend("false");
/*      */                                 
/* 3535 */                                 _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(xaxis_time);
/*      */                                 
/* 3537 */                                 _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel(yaxis_busyser);
/*      */                                 
/* 3539 */                                 _jspx_th_awolf_005ftimechart_005f2.setDateFormat("HH:mm");
/* 3540 */                                 int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 3541 */                                 if (_jspx_eval_awolf_005ftimechart_005f2 != 0) {
/* 3542 */                                   if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3543 */                                     out = _jspx_page_context.pushBody();
/* 3544 */                                     _jspx_th_awolf_005ftimechart_005f2.setBodyContent((BodyContent)out);
/* 3545 */                                     _jspx_th_awolf_005ftimechart_005f2.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3548 */                                     out.write("\n            ");
/* 3549 */                                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f2.doAfterBody();
/* 3550 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3553 */                                   if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3554 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3557 */                                 if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 3558 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2); return;
/*      */                                 }
/*      */                                 
/* 3561 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 3562 */                                 out.write(" </td>\n        </tr>\n      </table></td>\n</tr>\n<tr>\n        <td align=left valign=\"top\">\n\n      <table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3563 */                                 if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3565 */                                 out.write("</span></td>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3566 */                                 if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3568 */                                 out.write("</span></td>\n          <td class=\"columnheadingnotop\" colspan=\"2\"><span class=\"bodytextbold\">");
/* 3569 */                                 if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3571 */                                 out.write("</span></td>\n        </tr>\n        <tr>\n          <td  height=\"25\" class=\"whitegrayborder\" title=\"");
/* 3572 */                                 out.print(tipCurValue);
/* 3573 */                                 out.write(34);
/* 3574 */                                 out.write(62);
/* 3575 */                                 out.print(FormatUtil.getString("am.webclient.apache.busyservers"));
/* 3576 */                                 out.write("</td>\n          <td  height=\"25\" class=\"whitegrayborder\">");
/* 3577 */                                 out.print(formatNumber(busyservers));
/* 3578 */                                 out.write("</td>\n          <td width=\"16%\" align=\"center\" class=\"whitegrayborder\">  &nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3579 */                                 out.print(resID);
/* 3580 */                                 out.write("&attributeid=");
/* 3581 */                                 out.print(busyserver_id);
/* 3582 */                                 out.write("&alertconfigurl=");
/* 3583 */                                 out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs" + busyserver_id + "attributeToSelect=" + busyserver_id + "&redirectto=" + encodeurl));
/* 3584 */                                 out.write("')\">");
/* 3585 */                                 out.print(getSeverityImage(alert1.getProperty(resID + "#" + busyserver_id)));
/* 3586 */                                 out.write(" </a></td>\n        </tr>\n        ");
/*      */                                 
/* 3588 */                                 tipCurValue = FormatUtil.getString("am.webclient.apache.tooltip.idleserver");
/*      */                                 
/* 3590 */                                 out.write("\n        <tr>\n          <td  height=\"25\" class=\"yellowgrayborder\" title=\"");
/* 3591 */                                 out.print(tipCurValue);
/* 3592 */                                 out.write(34);
/* 3593 */                                 out.write(62);
/* 3594 */                                 out.print(FormatUtil.getString("am.webclient.apache.idleservers"));
/* 3595 */                                 out.write("</td>\n          <td  height=\"25\" class=\"yellowgrayborder\">");
/* 3596 */                                 out.print(formatNumber(idleservers));
/* 3597 */                                 out.write("</td>\n          <td width=\"16%\"  align=\"center\" class=\"yellowgrayborder\">  &nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3598 */                                 out.print(resID);
/* 3599 */                                 out.write("&attributeid=");
/* 3600 */                                 out.print(idleserver_id);
/* 3601 */                                 out.write("&alertconfigurl=");
/* 3602 */                                 out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs=" + idleserver_id + "attributeToSelect=" + idleserver_id + "&redirectto=" + encodeurl));
/* 3603 */                                 out.write("')\">");
/* 3604 */                                 out.print(getSeverityImage(alert1.getProperty(resID + "#" + idleserver_id)));
/* 3605 */                                 out.write(" </a></td>\n        </tr>\n        <tr>\n        <td  colspan=\"4\" height=\"35\" class=\"whitegrayborder\" align=\"right\" ><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\" width=\"20\" height=\"20\" style=\"width: 13px; height: 13px;\" title=\"\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3606 */                                 out.print(resID);
/* 3607 */                                 out.write("&attributeIDs=");
/* 3608 */                                 out.print(busyserver_id);
/* 3609 */                                 out.write(44);
/* 3610 */                                 out.print(idleserver_id);
/* 3611 */                                 out.write("&attributeToSelect=");
/* 3612 */                                 out.print(busyserver_id);
/* 3613 */                                 out.write("&redirectto=");
/* 3614 */                                 out.print(encodeurl);
/* 3615 */                                 out.write("\" class=\"staticlinks\">");
/* 3616 */                                 out.print(ALERTCONFIG_TEXT);
/* 3617 */                                 out.write("</a>&nbsp;</td>\n        </tr>\n      </table>\n     </td>\n</tr>\n</table>\n</td>\n\n");
/*      */                                 
/* 3619 */                                 wlsGraph.setParam(apache_id, "BYTESPERSEC");
/* 3620 */                                 tipCurValue = FormatUtil.getString("am.webclient.apache.tooltip.nobtransferedpersecond");
/*      */                                 
/* 3622 */                                 out.write("\n\n<td width=\"50%\" height=\"25\" >\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n<tr>\n <td width=\"300\" height=\"127\" colspan=\"2\"> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"50%\">\n\n        <tr>\n          <td colspan=\"2\" > ");
/*      */                                 
/* 3624 */                                 TimeChart _jspx_th_awolf_005ftimechart_005f3 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 3625 */                                 _jspx_th_awolf_005ftimechart_005f3.setPageContext(_jspx_page_context);
/* 3626 */                                 _jspx_th_awolf_005ftimechart_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 3628 */                                 _jspx_th_awolf_005ftimechart_005f3.setDataSetProducer("wlsGraph");
/*      */                                 
/* 3630 */                                 _jspx_th_awolf_005ftimechart_005f3.setWidth("300");
/*      */                                 
/* 3632 */                                 _jspx_th_awolf_005ftimechart_005f3.setHeight("170");
/*      */                                 
/* 3634 */                                 _jspx_th_awolf_005ftimechart_005f3.setLegend("false");
/*      */                                 
/* 3636 */                                 _jspx_th_awolf_005ftimechart_005f3.setXaxisLabel(xaxis_time);
/*      */                                 
/* 3638 */                                 _jspx_th_awolf_005ftimechart_005f3.setYaxisLabel(yaxis_bytessec);
/*      */                                 
/* 3640 */                                 _jspx_th_awolf_005ftimechart_005f3.setDateFormat("HH:mm");
/* 3641 */                                 int _jspx_eval_awolf_005ftimechart_005f3 = _jspx_th_awolf_005ftimechart_005f3.doStartTag();
/* 3642 */                                 if (_jspx_eval_awolf_005ftimechart_005f3 != 0) {
/* 3643 */                                   if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 3644 */                                     out = _jspx_page_context.pushBody();
/* 3645 */                                     _jspx_th_awolf_005ftimechart_005f3.setBodyContent((BodyContent)out);
/* 3646 */                                     _jspx_th_awolf_005ftimechart_005f3.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3649 */                                     out.write("\n            ");
/* 3650 */                                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f3.doAfterBody();
/* 3651 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3654 */                                   if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 3655 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3658 */                                 if (_jspx_th_awolf_005ftimechart_005f3.doEndTag() == 5) {
/* 3659 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3); return;
/*      */                                 }
/*      */                                 
/* 3662 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3);
/* 3663 */                                 out.write(" </td>\n        </tr>\n      </table></td>\n</tr>\n<tr>\n        <td  align=left valign=\"top\">\n\n      <table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3664 */                                 if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3666 */                                 out.write("</span></td>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3667 */                                 if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3669 */                                 out.write("</span></td>\n          <td class=\"columnheadingnotop\" colspan=\"2\"><span class=\"bodytextbold\">");
/* 3670 */                                 if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3672 */                                 out.write("</span></td>\n\n        </tr>\n        <tr>\n          <td  height=\"25\" class=\"whitegrayborder\" title=\"");
/* 3673 */                                 out.print(tipCurValue);
/* 3674 */                                 out.write(34);
/* 3675 */                                 out.write(62);
/* 3676 */                                 out.print(FormatUtil.getString("am.webclient.apache.bytespersec"));
/* 3677 */                                 out.write("</td>\n          <td  height=\"25\" class=\"whitegrayborder\">");
/* 3678 */                                 out.print(formatNumber(bytespersec));
/* 3679 */                                 out.write("</td>\n          <td width=\"16%\" align=\"center\" class=\"whitegrayborder\">  &nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3680 */                                 out.print(resID);
/* 3681 */                                 out.write("&attributeid=");
/* 3682 */                                 out.print(bytespersec_id);
/* 3683 */                                 out.write("&alertconfigurl=");
/* 3684 */                                 out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resID + "attributeIDs" + bytespersec_id + "attributeToSelect=" + bytespersec_id + "&redirectto=" + encodeurl));
/* 3685 */                                 out.write("')\">");
/* 3686 */                                 out.print(getSeverityImage(alert1.getProperty(resID + "#" + bytespersec_id)));
/* 3687 */                                 out.write(" </a></td>\n\n        </tr>\n        <tr>\n          <td  height=\"25\" class=\"yellowgrayborder\"></td>\n          <td  height=\"25\" class=\"yellowgrayborder\"></td>\n          <td width=\"16%\"class=\"yellowgrayborder\"> </td-->\n        </tr>\n        <tr>\n        <td  colspan=\"4\" height=\"35\" class=\"whitegrayborder\" align=\"right\" ><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\" width=\"20\" height=\"20\" style=\"width: 13px; height: 13px;\" title=\"\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3688 */                                 out.print(resID);
/* 3689 */                                 out.write("&attributeIDs=");
/* 3690 */                                 out.print(bytespersec_id);
/* 3691 */                                 out.write("&attributeToSelect=");
/* 3692 */                                 out.print(bytespersec_id);
/* 3693 */                                 out.write("&redirectto=");
/* 3694 */                                 out.print(encodeurl);
/* 3695 */                                 out.write("\" class=\"staticlinks\">");
/* 3696 */                                 out.print(ALERTCONFIG_TEXT);
/* 3697 */                                 out.write("</a>&nbsp;</td>\n        </tr>\n      </table></td>\n</tr>\n</table>\n</td>\n</tr>\n\n</table>\n\n\n\n");
/*      */                                 
/* 3699 */                                 if (apache_id != resID)
/*      */                                 {
/*      */ 
/* 3702 */                                   out.write("\n<br>\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"99%\" class=\"lrtbdarkborder\">\n<tr>\n    <td class=\"yellowgrayborder\"> &nbsp;&nbsp;");
/* 3703 */                                   out.print(FormatUtil.getString("am.webclient.apache.latest.values"));
/* 3704 */                                   out.write("\n<a href=\"/help/monitors/services-monitoring.html#php\" target=\"_blank\" class=\"selectedmenu\"><img src=\"/images/icon_quicknote_help.gif\"\nhspace=\"5\" vspace=\"5\" border=\"0\" align=\"absmiddle\">");
/* 3705 */                                   out.print(FormatUtil.getString("am.webclient.contexthelplink.text"));
/* 3706 */                                   out.write(" >></a> </td>\n</tr>\n</table>\n\n<br>\n");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3710 */                                 out.write(10);
/* 3711 */                                 out.write("\n    ");
/*      */                               }
/*      */                             }
/*      */                             
/*      */ 
/*      */ 
/* 3717 */                             out.write(10);
/* 3718 */                             out.write(10);
/* 3719 */                             if ((resourcetype.equals("RMI")) || (resourcetype.equals("SNMP")) || (resourcetype.equals("JMX1.2-MX4J-RMI"))) {
/* 3720 */                               out.write("\n<br>\n   ");
/* 3721 */                               out.write("<!--$Id$-->\n");
/* 3722 */                               com.adventnet.appmanager.cam.beans.CAMGraphs camGraph = null;
/* 3723 */                               camGraph = (com.adventnet.appmanager.cam.beans.CAMGraphs)_jspx_page_context.getAttribute("camGraph", 1);
/* 3724 */                               if (camGraph == null) {
/* 3725 */                                 camGraph = new com.adventnet.appmanager.cam.beans.CAMGraphs();
/* 3726 */                                 _jspx_page_context.setAttribute("camGraph", camGraph, 1);
/*      */                               }
/* 3728 */                               out.write("\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/customfield.js\"></SCRIPT>\n\n");
/*      */                               
/* 3730 */                               long camStartTime = System.currentTimeMillis();
/*      */                               
/* 3732 */                               String camIDI = (String)request.getAttribute("camid");
/* 3733 */                               String screenIDI = (String)request.getAttribute("screenid");
/* 3734 */                               List screenInfoI = (List)request.getAttribute("screeninfo");
/* 3735 */                               boolean perfAvailResourceScreen = false;
/* 3736 */                               String resourceID = "";
/* 3737 */                               String fromConfPage1 = request.getAttribute("fromConfPage") + "";
/* 3738 */                               String haidI = request.getParameter("haid");
/* 3739 */                               if ((haidI == null) || (haidI.trim().length() == 0)) {
/* 3740 */                                 haidI = request.getParameter("haid");
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/* 3745 */                               String isFromResourcePage = (String)request.getAttribute("isfromresourcepage");
/* 3746 */                               if (isFromResourcePage == null) {
/* 3747 */                                 isFromResourcePage = "NA";
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/* 3752 */                               if ("true".equals(isFromResourcePage))
/*      */                               {
/*      */ 
/*      */ 
/* 3756 */                                 resourceID = (String)request.getAttribute("resourceid");
/* 3757 */                                 if ((resourceID == null) || (resourceID.trim().length() == 0)) {
/* 3758 */                                   resourceID = request.getParameter("resourceid");
/*      */                                 }
/*      */                                 
/* 3761 */                                 camIDI = resourceID;
/* 3762 */                                 perfAvailResourceScreen = true;
/*      */                                 
/*      */ 
/* 3765 */                                 request.setAttribute("screenInfo", screenInfoI);
/* 3766 */                                 List tmpList = CAMDBUtil.getScreens(Integer.parseInt(camIDI));
/* 3767 */                                 if (tmpList.size() == 0)
/*      */                                 {
/* 3769 */                                   CAMDBUtil.createDefaultScreenForResource(Integer.parseInt(camIDI));
/* 3770 */                                   tmpList = CAMDBUtil.getScreens(Integer.parseInt(camIDI));
/* 3771 */                                   screenInfoI = (List)tmpList.get(0);
/*      */                                 }
/*      */                                 else {
/* 3774 */                                   screenInfoI = (List)tmpList.get(0);
/*      */                                 }
/*      */                                 
/* 3777 */                                 screenIDI = (String)screenInfoI.get(0);
/* 3778 */                                 request.setAttribute("screenid", screenIDI);
/*      */                               }
/*      */                               
/*      */ 
/* 3782 */                               Map fromDB = CAMDBUtil.getCustomizedDataForScreenAdminActivity(Long.parseLong(screenIDI));
/* 3783 */                               List screenConfigList = (List)fromDB.get("completedata");
/*      */                               
/*      */ 
/* 3786 */                               List reportsData = (List)fromDB.get("reportsdata");
/*      */                               
/*      */ 
/* 3789 */                               Map dcTimeMap = CAMDBUtil.getLatestCollectionTimes(Long.parseLong(screenIDI));
/* 3790 */                               Map attribResourceMap = CAMDBUtil.getResourceNamesForAttributesInScreen(Integer.parseInt(screenIDI));
/*      */                               
/* 3792 */                               List screenAttribInfo = CAMDBUtil.getScreenAttributeInfo(Long.parseLong(screenIDI), Integer.parseInt((String)screenInfoI.get(3)), dcTimeMap);
/* 3793 */                               boolean scalarAttribsPresent = screenAttribInfo.size() > 0;
/* 3794 */                               List colScreenAttribInfo = CAMDBUtil.getScreenInfoForColumnarData(Long.parseLong(screenIDI));
/* 3795 */                               boolean columnarAttribsPresent = colScreenAttribInfo.size() > 0;
/* 3796 */                               boolean attribsPresent = (scalarAttribsPresent == true) || (columnarAttribsPresent == true);
/* 3797 */                               String quickNote = "This page displays the attributes monitored from various resources as configured during design time. Placing the mouse over the value for table data will display the time when the data was collected. Time intervals will be different if the attributes are from different resources.";
/*      */                               
/*      */ 
/*      */ 
/* 3801 */                               if (request.getAttribute("quicknote") == null) {
/* 3802 */                                 request.setAttribute("quicknote", quickNote);
/*      */                               }
/* 3804 */                               String configureLink = "/ShowCAM.do?method=configureScreen&screenid=" + screenIDI + "&camid=" + camIDI + "&haid=" + haidI + "&isfromresourcepage=" + isFromResourcePage;
/* 3805 */                               if ((request.isUserInRole("ENTERPRISEADMIN")) && (com.adventnet.appmanager.util.Constants.isSsoEnabled()))
/*      */                               {
/* 3807 */                                 StringBuilder builder = new StringBuilder();
/* 3808 */                                 builder.append("https:").append("//");
/* 3809 */                                 builder.append(com.adventnet.appmanager.util.Constants.getAppHostName()).append(":");
/* 3810 */                                 builder.append(com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.getsslport()).append(configureLink);
/* 3811 */                                 configureLink = builder.toString();
/*      */                               }
/* 3813 */                               request.setAttribute("configurelink", configureLink);
/* 3814 */                               String secondLevelLinkTitle; if (!perfAvailResourceScreen)
/*      */                               {
/*      */ 
/* 3817 */                                 List sLinks = new ArrayList();
/* 3818 */                                 List sText = new ArrayList();
/*      */                                 
/* 3820 */                                 sLinks.add("Add ScreenXXXX");
/* 3821 */                                 sText.add("/ShowCAM.do?method=addScreen&camid=" + camIDI + "&haid=" + haidI);
/*      */                                 
/*      */ 
/*      */ 
/* 3825 */                                 sLinks.add("Customize");
/* 3826 */                                 sText.add(configureLink);
/*      */                                 
/*      */ 
/* 3829 */                                 sLinks.add("<a href=\"/CAMDeleteScreen.do?method=deleteScreen&screenid=" + screenIDI + "&camid=" + camIDI + "&haid=" + haidI + "\" onclick=\"return deleteScreen();\" class='staticlinks'>Delete Screen</a>");
/* 3830 */                                 sText.add("");
/*      */                                 
/*      */ 
/*      */ 
/* 3834 */                                 List[] secondLevelOfLinks = { sText, sLinks };
/* 3835 */                                 secondLevelLinkTitle = "Admin Activity";
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3844 */                               String configureThresholdRedirectLink = "/ShowCAM.do?method=showScreen&haid=" + haidI + "&camid=" + camIDI + "&screenid=" + screenIDI;
/*      */                               
/* 3846 */                               if ("true".equals(isFromResourcePage)) {
/* 3847 */                                 configureThresholdRedirectLink = "/showresource.do?resourceid=" + camIDI + "&method=showResourceForResourceID";
/*      */                               }
/*      */                               
/* 3850 */                               configureThresholdRedirectLink = URLEncoder.encode(configureThresholdRedirectLink);
/*      */                               
/*      */ 
/*      */ 
/* 3854 */                               out.write("\n\n\n\n<script language=\"JavaScript1.2\">\n\nfunction showAttribGraph(attribID,mbean) {\n       var featurelist = \"toolbar=no,status=no,menubar=no,width=450,height=300,scrollbars=yes\";\n       var url = \"/ShowCAM.do?method=showSingleGraphScreen&attributeid=\" + attribID+\"&mbean=\" +mbean;\n       popUp = window.open(url,'popUp',featurelist);\n       return false;\n}\n\n</SCRIPT>\n<!--This script is used to show horizontal bar if customer attributes tables have more number of attributes in SNMP Devices.--> \n<script>\n    jQuery(document).ready(function(){\n        var windowWidth = jQuery(window).width();\n        windowWidth = windowWidth*(81/100);\n        jQuery('.horTableScroll').css({'width':windowWidth});//No I18N\n\n    });\n</script>\n\n<style>\n    .horTableScroll {\n        overflow-x:auto;\n    }    \n</style>\n<!--");
/*      */                               
/* 3856 */                               String camid = request.getParameter("camid");
/*      */                               
/*      */ 
/* 3859 */                               out.write("-->\n\n\n<script>\nfunction validateAndSubmit()\n{\n   if(trimAll(document.AMActionForm.camname.value)==\"\")\n   {\n        alert('");
/* 3860 */                               out.print(FormatUtil.getString("am.webclient.cam.namealert"));
/* 3861 */                               out.write("');\n        return;\n   }\n   document.AMActionForm.submit();\n}\n\n</script>\n\n");
/*      */                               
/* 3863 */                               List list = CAMDBUtil.getCAMDetails(camIDI);
/* 3864 */                               String camName = (String)list.get(0);
/* 3865 */                               String camDescription = (String)list.get(2);
/*      */                               
/* 3867 */                               out.write("\n\n\n\n");
/*      */                               
/* 3869 */                               if ("true".equals(request.getParameter("editPage")))
/*      */                               {
/* 3871 */                                 out.write("\n<div id=\"edit\" style=\"display:block\">\n");
/*      */                               } else {
/* 3873 */                                 out.write("\n<div id=\"edit\" style=\"display:none\">\n");
/*      */                               }
/* 3875 */                               out.write(10);
/* 3876 */                               out.write(10);
/*      */                               
/* 3878 */                               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 3879 */                               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 3880 */                               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 3882 */                               _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*      */                               
/* 3884 */                               _jspx_th_html_005fform_005f0.setMethod("get");
/*      */                               
/* 3886 */                               _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 3887 */                               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 3888 */                               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                 for (;;) {
/* 3890 */                                   out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\n<tr>\n<td height=\"28\"   class=\"tableheading\">");
/* 3891 */                                   out.print(FormatUtil.getString("am.webclient.common.configurationdetails.text"));
/* 3892 */                                   out.write("\n\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 3893 */                                   out.print(request.getParameter("name"));
/* 3894 */                                   out.write("\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 3895 */                                   out.print(request.getParameter("haid"));
/* 3896 */                                   out.write("\">\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 3897 */                                   out.print(request.getParameter("type"));
/* 3898 */                                   out.write("\">\n<input type=\"hidden\" name=\"type\" value=\"");
/* 3899 */                                   out.print(request.getParameter("type"));
/* 3900 */                                   out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"configureJMX\">\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 3901 */                                   out.print(request.getParameter("resourceid"));
/* 3902 */                                   out.write("\">\n<input type=\"hidden\" name=\"resourcename\" value=\"");
/* 3903 */                                   out.print(request.getParameter("resourcename"));
/* 3904 */                                   out.write("\">\n<input type=\"hidden\" name=\"moname\" value=\"");
/* 3905 */                                   out.print(request.getParameter("moname"));
/* 3906 */                                   out.write("\">\n\n</td>\n<td height=\"28\"   class=\"tableheading\" align=\"right\" onClick=\"javascript:hideDiv('edit')\">\n<span class=\"bodytextboldwhiteun\" ><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\">\n");
/* 3907 */                                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 3908 */                                   out.write("</span>\n</td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n<tr>\n    <td width=\"20%\" height=\"32\" valign=='top' class=\"bodytext\"> ");
/* 3909 */                                   out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 3910 */                                   out.write("\n</td>\n    <td width=\"80%\" class=\"bodytext\"> <textarea name=\"camname\" cols=\"38\" rows=\"1\" class=\"formtextarea\">");
/* 3911 */                                   out.print(camName);
/* 3912 */                                   out.write(" </textarea>\n</td>\n</tr>\n\n<tr>\n    <td valign='top'  class=\"bodytext\"> ");
/* 3913 */                                   out.print(FormatUtil.getString("Description"));
/* 3914 */                                   out.write("</td>\n    <td  class=\"bodytext\"> <textarea name=\"camdesc\" cols=\"38\" rows=\"3\" class=\"formtextarea\" >");
/* 3915 */                                   out.print(camDescription);
/* 3916 */                                   out.write("</textarea>\n    </td>\n  </tr>\n</table>\n");
/*      */                                   
/* 3918 */                                   String cancel = FormatUtil.getString("am.webclient.common.cancel.text");
/*      */                                   
/* 3920 */                                   out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"20%\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"80%\" class=\"tablebottom\" >\n<input name=\"addcustomapp\" type=\"button\" class=\"buttons btn_highlt\" \" value=\"");
/* 3921 */                                   out.print(FormatUtil.getString("am.webclient.common.startmonitoring.text"));
/* 3922 */                                   out.write("\" onClick=\"validateAndSubmit()\"/>\n      &nbsp; <input name=\"reset\" type=\"reset\" class=\"buttons btn_link\" value=\"");
/* 3923 */                                   out.print(cancel);
/* 3924 */                                   out.write("\" onClick=\"javascript:toggleDiv('edit')\"/>\n     </td>\n  </tr>\n</table>\n");
/* 3925 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 3926 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3930 */                               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 3931 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                               }
/*      */                               
/* 3934 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 3935 */                               out.write("\n</div>\n");
/*      */                               int m;
/* 3937 */                               if (!attribsPresent)
/*      */                               {
/*      */ 
/* 3940 */                                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n    <td colspan=3  height=\"19\" class=\"tableheadingbborder\" >");
/* 3941 */                                 out.print(camName);
/* 3942 */                                 out.write("\n      ");
/* 3943 */                                 if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 3944 */                                   out.write(": <span class=\"topdesc\">");
/* 3945 */                                   out.print(FormatUtil.getString(camDescription));
/* 3946 */                                   out.write(" </span>");
/*      */                                 }
/* 3948 */                                 out.write("</td>\n     <td  height=\"19\" width=\"20%\" class=\"tableheadingbborder\">\n     ");
/*      */                                 
/* 3950 */                                 if ((attribsPresent) && (request.getAttribute("showrefreshnowoption") != null))
/*      */                                 {
/*      */ 
/* 3953 */                                   out.write("\n       <a class=\"bodytextboldwhiteun\" href=\"/deleteMO.do?method=fetchDataNowForResource&resourceid=");
/* 3954 */                                   out.print(camIDI);
/* 3955 */                                   out.write("&redirectto=");
/* 3956 */                                   out.print(URLEncoder.encode("/showresource.do?resourceid=" + camIDI + "&method=showResourceForResourceID" + fromConfPage1));
/* 3957 */                                   out.write(34);
/* 3958 */                                   out.write(62);
/* 3959 */                                   out.print(FormatUtil.getString("am.webclient.availabilityperf.fetchvalue"));
/* 3960 */                                   out.write("</a>\n     ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 3966 */                                   out.write("\n     &nbsp;\n     ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3970 */                                 out.write("\n\n     </td>\n</tr>\n\n<tr>\n    <td colspan=4  height=\"29\" ><span class=\"bodytext\">&nbsp;");
/* 3971 */                                 out.print(FormatUtil.getString("am.webclient.cam.addcustomattributes.message"));
/*      */                                 
/* 3973 */                                 PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3974 */                                 _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3975 */                                 _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 3977 */                                 _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 3978 */                                 int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3979 */                                 if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                   for (;;) {
/* 3981 */                                     out.write(" <a class='staticlinks' href=\"");
/* 3982 */                                     out.print(configureLink);
/* 3983 */                                     out.write("\">\n      ");
/* 3984 */                                     out.print(FormatUtil.getString("am.webclient.cam.addattributes.link"));
/* 3985 */                                     out.write("</a>.");
/* 3986 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3987 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3991 */                                 if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3992 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                                 }
/*      */                                 
/* 3995 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3996 */                                 out.write("</span></td>\n</tr>\n</table>\n");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/* 4001 */                                 if (!scalarAttribsPresent) {
/* 4002 */                                   out.write(10);
/* 4003 */                                   out.write(10);
/*      */ 
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/*      */ 
/* 4011 */                                   List row = null;
/* 4012 */                                   int posOfAttribName = 2;
/* 4013 */                                   int posOfDispType = 4;
/* 4014 */                                   int posOfAttribValue = 7;
/* 4015 */                                   int posOfResourceID = 6;
/* 4016 */                                   int posOfAttribID = 0;
/* 4017 */                                   int posOfAttribType = 3;
/* 4018 */                                   String className = "whitegrayborder";
/* 4019 */                                   String currentResourceName = null;
/* 4020 */                                   String currentMBeanName = null;
/* 4021 */                                   Map infoMap = CAMDBUtil.getMBeanBasedScreenData(Long.parseLong(screenIDI));
/* 4022 */                                   Map idVsMBeanAndRes = (HashMap)infoMap.get("attrIdVsMBeanName");
/* 4023 */                                   List ordListFromDB = (ArrayList)infoMap.get("attributeidsorderedlist");
/* 4024 */                                   List orderedList = new ArrayList(screenAttribInfo.size());
/*      */                                   
/*      */ 
/* 4027 */                                   out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n    <td colspan=3  height=\"19\" class=\"tableheadingbborder\" >");
/* 4028 */                                   out.print(camName);
/* 4029 */                                   out.write("\n    ");
/* 4030 */                                   if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 4031 */                                     out.write("  : <span class=\"topdesc\">");
/* 4032 */                                     out.print(FormatUtil.getString(camDescription));
/* 4033 */                                     out.write(" </span> ");
/*      */                                   }
/* 4035 */                                   out.write("</td>\n\t<td width=\"30%\" nowrap class=\"tableheadingbborder\">\n\t");
/* 4036 */                                   if ((attribsPresent) && (request.getAttribute("showrefreshnowoption") != null)) {
/* 4037 */                                     out.write("\n       <a class=\"bodytextboldwhiteun\" href=\"/deleteMO.do?method=fetchDataNowForResource&resourceid=");
/* 4038 */                                     out.print(camIDI);
/* 4039 */                                     out.write("&redirectto=");
/* 4040 */                                     out.print(URLEncoder.encode("/showresource.do?resourceid=" + camIDI + "&method=showResourceForResourceID" + fromConfPage1));
/* 4041 */                                     out.write(34);
/* 4042 */                                     out.write(62);
/* 4043 */                                     out.print(FormatUtil.getString("am.webclient.availabilityperf.fetchvalue"));
/* 4044 */                                     out.write("</a>\n       ");
/*      */                                   } else {
/* 4046 */                                     out.write("\n       <a class=\"staticlinks\" href=\"javascript:void(0);\" onclick=\"getCustomFields('");
/* 4047 */                                     out.print(camIDI);
/* 4048 */                                     out.write("','noalarms',false,'CustomFieldValues',false);\">");
/* 4049 */                                     out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 4050 */                                     out.write("</a>&nbsp;");
/* 4051 */                                     out.write("\n       ");
/*      */                                   }
/* 4053 */                                   out.write("\n       </td>\n\n<tr>\n                <td width=\"36%\" class=\"columnheading\" > ");
/* 4054 */                                   out.print(FormatUtil.getString("am.webclient.camscreen.attributename"));
/* 4055 */                                   out.write("</td>\n            <td width=\"30%\" class=\"columnheading\" > ");
/* 4056 */                                   out.print(FormatUtil.getString("am.webclient.camscreen.value"));
/* 4057 */                                   out.write("</td>\n        <td width=\"20%\" class=\"columnheading\" > ");
/* 4058 */                                   if ((request.getParameter("type") != null) && (request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 4059 */                                     out.write(" &nbsp; ");
/*      */                                   } else {
/* 4061 */                                     out.write(32);
/* 4062 */                                     out.print(FormatUtil.getString("am.webclient.camscreen.datacollectiontime"));
/* 4063 */                                     out.write("</td> ");
/*      */                                   }
/* 4065 */                                   out.write("\n    <td width=\"9%\" class=\"columnheading\" >");
/* 4066 */                                   out.print(FormatUtil.getString("am.webclient.camscreen.actions.text"));
/* 4067 */                                   out.write("</td>\n</tr>\n");
/*      */                                   
/* 4069 */                                   Hashtable token = new Hashtable();
/*      */                                   int n;
/* 4071 */                                   for (int i = 0; i < screenAttribInfo.size(); n++)
/*      */                                   {
/* 4073 */                                     row = (List)screenAttribInfo.get(i);
/* 4074 */                                     if (i % 2 == 0) {
/* 4075 */                                       className = "whitegrayborder";
/*      */                                     } else {
/* 4077 */                                       className = "yellowgrayborder";
/*      */                                     }
/*      */                                     
/* 4080 */                                     boolean newResource = false;
/* 4081 */                                     boolean newMBean = false;
/* 4082 */                                     boolean addMBeanRow = false;
/* 4083 */                                     String date = "Could not be obtained";
/* 4084 */                                     String shortDate = "Could not be obtained";
/* 4085 */                                     String longFormatDate = "Could not be obtained";
/* 4086 */                                     String resourceName4Attrib = "";
/*      */                                     try
/*      */                                     {
/* 4089 */                                       resourceName4Attrib = (String)attribResourceMap.get(row.get(posOfAttribID));
/* 4090 */                                       String attribID = (String)row.get(posOfAttribID);
/* 4091 */                                       String mBeanName = (String)idVsMBeanAndRes.get(attribID);
/* 4092 */                                       if (currentMBeanName == null)
/*      */                                       {
/* 4094 */                                         currentMBeanName = mBeanName;
/* 4095 */                                         newMBean = true;
/*      */                                       }
/* 4097 */                                       else if (!currentMBeanName.equals(mBeanName))
/*      */                                       {
/* 4099 */                                         currentMBeanName = mBeanName;
/* 4100 */                                         newMBean = true;
/*      */                                       }
/* 4102 */                                       if (currentResourceName == null)
/*      */                                       {
/* 4104 */                                         currentResourceName = resourceName4Attrib;
/* 4105 */                                         newResource = true;
/*      */                                       }
/* 4107 */                                       else if (!currentResourceName.equals(resourceName4Attrib))
/*      */                                       {
/* 4109 */                                         currentResourceName = resourceName4Attrib;
/* 4110 */                                         newResource = true;
/*      */                                       }
/* 4112 */                                       addMBeanRow = (newMBean) || (newResource);
/* 4113 */                                       date = String.valueOf(Long.parseLong((String)dcTimeMap.get(row.get(posOfAttribID))));
/* 4114 */                                       shortDate = formatDT(date);
/* 4115 */                                       longFormatDate = new java.util.Date(Long.parseLong(date)).toString();
/*      */                                     }
/*      */                                     catch (Exception e) {}
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4124 */                                     String value = (String)row.get(posOfAttribValue);
/* 4125 */                                     if (row.get(posOfAttribType).equals("0"))
/*      */                                     {
/* 4127 */                                       if (value.equals("-1"))
/*      */                                       {
/* 4129 */                                         value = FormatUtil.getString("am.webclient.cam.nodata");
/*      */                                       }
/*      */                                     }
/*      */                                     
/*      */ 
/* 4134 */                                     out.write(10);
/*      */                                     
/* 4136 */                                     if (addMBeanRow)
/*      */                                     {
/* 4138 */                                       if (((String)attribResourceMap.get(row.get(posOfAttribID) + "_RESTYPE")).equals("SNMP"))
/*      */                                       {
/*      */ 
/* 4141 */                                         out.write("\n<tr>\n       <td height=\"20\" class=\"secondchildnode\" colspan=\"4\"><span class=\"bodytextbold\"><span class=\"bodytext\">(");
/* 4142 */                                         out.print(currentResourceName);
/* 4143 */                                         out.write(")</span></span></td>\n</tr>\n\n");
/*      */ 
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 4150 */                                         out.write("\n\n<tr>\n<td height=\"20\"   class=\"secondchildnode\"  colspan=\"4\" onmouseover=\"ddrivetip(this,event,'");
/* 4151 */                                         out.print(addBreakAt((currentMBeanName + " - " + currentResourceName).replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"), 80));
/* 4152 */                                         out.write("',null,true,'#000000',300)\" onmouseout=\"hideddrivetip()\" <span class=\"bodytextbold\">");
/* 4153 */                                         out.print(addBreakAt(currentMBeanName, 80));
/* 4154 */                                         out.write(" <span class=\"availablity-arrow\">&raquo;&nbsp;</span> ");
/* 4155 */                                         out.print(getTrimmedText(currentResourceName, 25));
/* 4156 */                                         out.write("</span> </td> ");
/* 4157 */                                         out.write("\n</tr>\n");
/*      */                                       }
/*      */                                     }
/*      */                                     try
/*      */                                     {
/* 4162 */                                       StringTokenizer mbean = new StringTokenizer(currentResourceName, "_");
/* 4163 */                                       int j = 0;
/* 4164 */                                       int i1; while (mbean.hasMoreTokens()) {
/* 4165 */                                         String t = mbean.nextToken();
/* 4166 */                                         token.put(Integer.valueOf(j), t);
/* 4167 */                                         i1++;
/*      */                                       }
/*      */                                       
/*      */ 
/* 4171 */                                       String attrbId = (String)row.get(posOfAttribID);
/* 4172 */                                       String resType = (String)attribResourceMap.get(attrbId + "_RESTYPE");
/* 4173 */                                       if ((resType != null) && (resType.equalsIgnoreCase("snmp"))) {
/* 4174 */                                         String resourceId = (String)row.get(row.size() - 2);
/* 4175 */                                         if ((resourceId != null) && (resourceId.length() > 0)) {
/* 4176 */                                           List l = com.adventnet.appmanager.util.DBUtil.getRows("SELECT RESOURCENAME FROM AM_ManagedObject where RESOURCEID=" + resourceId);
/* 4177 */                                           if ((l != null) && (l.size() == 1)) {
/* 4178 */                                             j = 0;
/* 4179 */                                             String actualResourceName = (String)((ArrayList)l.get(0)).get(0);
/* 4180 */                                             mbean = new StringTokenizer(actualResourceName, "_");
/* 4181 */                                             while (mbean.hasMoreTokens()) {
/* 4182 */                                               String t = mbean.nextToken();
/* 4183 */                                               token.put(Integer.valueOf(j), t);
/* 4184 */                                               i1++;
/*      */                                             }
/*      */                                           }
/*      */                                         }
/*      */                                       }
/*      */                                     } catch (Exception e) {}
/* 4190 */                                     String toshow = getTrimmedText(value, 30);
/* 4191 */                                     request.setAttribute("toshow", toshow);
/* 4192 */                                     request.setAttribute("fullvalue", value);
/* 4193 */                                     int len = value.length();
/* 4194 */                                     String tooltiptype = (String)row.get(posOfDispType);
/*      */                                     
/* 4196 */                                     if (tooltiptype.equals("1")) {
/* 4197 */                                       tooltiptype = "Counter";
/* 4198 */                                       if ((toshow.equals(" ")) || (value.equals(" ")))
/*      */                                       {
/* 4200 */                                         Map fromMap = new HashMap();
/* 4201 */                                         fromMap = (HashMap)com.adventnet.appmanager.cam.CAMServerUtil.collectFirstData;
/* 4202 */                                         if (fromMap != null) {
/* 4203 */                                           List lst = new ArrayList();
/* 4204 */                                           lst = (ArrayList)fromMap.get((String)row.get(posOfAttribID));
/* 4205 */                                           if (lst != null) {
/* 4206 */                                             request.setAttribute("toshow", lst.get(2));
/* 4207 */                                             request.setAttribute("fullvalue", lst.get(3));
/*      */                                           }
/*      */                                         }
/*      */                                       }
/*      */                                     } else {
/* 4212 */                                       tooltiptype = "Non-Counter";
/*      */                                     }
/*      */                                     
/* 4215 */                                     out.write("\n\n<tr>\n\t<td class=\"");
/* 4216 */                                     out.print(className);
/* 4217 */                                     out.write("\" onmouseover=\"ddrivetip(this,event,'");
/* 4218 */                                     out.print(FormatUtil.getString("am.webclient.snmp.tootipMsg", new String[] { (String)row.get(posOfAttribName), resourceName4Attrib, tooltiptype }));
/* 4219 */                                     out.write("',null,true,'#000000','len')\" onmouseout=\"hideddrivetip()\"> <span class=\"bodytext\">");
/* 4220 */                                     out.print(getTrimmedText((String)row.get(posOfAttribName), 25));
/* 4221 */                                     out.write(" </span></td>\n\n");
/*      */                                     
/* 4223 */                                     if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 4224 */                                       if (len >= 30)
/*      */                                       {
/* 4226 */                                         out.write(10);
/* 4227 */                                         out.write(10);
/* 4228 */                                         String breaktext = addBreakAt(value, 50);
/* 4229 */                                         out.write("\n     <td class=\"");
/* 4230 */                                         out.print(className);
/* 4231 */                                         out.write("\" onmouseover=\"ddrivetip(this,event,'");
/* 4232 */                                         out.print(value.replaceAll("\\n", " "));
/* 4233 */                                         out.write("',null,true,'#000000','len')\" onmouseout=\"hideddrivetip()\" ><span class=\"bodytext\"> ");
/* 4234 */                                         if (_jspx_meth_c_005fout_005f21(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                           return;
/* 4236 */                                         out.write(" </span></td>\n\n");
/*      */                                       }
/*      */                                       else {
/* 4239 */                                         out.write("\n\n<td class=\"");
/* 4240 */                                         out.print(className);
/* 4241 */                                         out.write("\" ><span class=\"bodytext\"> ");
/* 4242 */                                         if (_jspx_meth_c_005fout_005f22(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                           return;
/* 4244 */                                         out.write(" </span></td>\n\n");
/*      */                                       }
/*      */                                       
/* 4247 */                                       out.write("\n\n        <td class=\"");
/* 4248 */                                       out.print(className);
/* 4249 */                                       out.write("\" title=\"Time : ");
/* 4250 */                                       out.print(longFormatDate);
/* 4251 */                                       out.write(" Resource : ");
/* 4252 */                                       out.print(resourceName4Attrib);
/* 4253 */                                       out.write("\"> <span class=\"bodytext\">");
/* 4254 */                                       out.print(shortDate);
/* 4255 */                                       out.write("</span></td>\n");
/*      */                                     } else {
/* 4257 */                                       out.write("\n<td colspan=2 class=\"");
/* 4258 */                                       out.print(className);
/* 4259 */                                       out.write("\"><span class=\"bodytext\">");
/* 4260 */                                       out.print(addBreakAt(value, 55));
/* 4261 */                                       out.write("</span></td>\n");
/*      */                                     }
/* 4263 */                                     out.write("\n        <td class=\"");
/* 4264 */                                     out.print(className);
/* 4265 */                                     out.write("\" >\n        ");
/* 4266 */                                     if ((row.get(posOfAttribType).equals("0")) || (row.get(posOfAttribType).equals("1"))) {
/* 4267 */                                       if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS")))
/*      */                                       {
/* 4269 */                                         out.write("\n\n<a  style=\"cursor: pointer;\" onClick=\"javascript:MM_openBrWindow('/jsp/attribute_edit.jsp?resourceid=");
/* 4270 */                                         out.print(row.get(posOfResourceID));
/* 4271 */                                         out.write("&attributeid=");
/* 4272 */                                         out.print(row.get(posOfAttribID));
/* 4273 */                                         out.write("&camid=");
/* 4274 */                                         out.print(camIDI);
/* 4275 */                                         out.write("&haid=");
/* 4276 */                                         out.print(haidI);
/* 4277 */                                         out.write("&screenid=");
/* 4278 */                                         out.print(screenIDI);
/* 4279 */                                         out.write("&resourcename=");
/* 4280 */                                         out.print(currentResourceName);
/* 4281 */                                         out.write("&hostname=");
/* 4282 */                                         out.print(token.get(Integer.valueOf(0)));
/* 4283 */                                         out.write("&resourcetype=");
/* 4284 */                                         out.print(token.get(Integer.valueOf(1)));
/* 4285 */                                         out.write("&portno=");
/* 4286 */                                         out.print(token.get(Integer.valueOf(2)));
/* 4287 */                                         out.write("&attributes=");
/* 4288 */                                         out.print(URLEncoder.encode(currentMBeanName + "|" + (String)row.get(1) + "|" + row.get(posOfAttribType)));
/* 4289 */                                         out.write("&displayname=");
/* 4290 */                                         out.print((String)row.get(posOfAttribName));
/* 4291 */                                         out.write("&isfromeditpage=");
/* 4292 */                                         out.print("true");
/* 4293 */                                         out.write("&resourceid=");
/* 4294 */                                         out.print(row.get(posOfResourceID));
/* 4295 */                                         out.write("&dispType=");
/* 4296 */                                         out.print(row.get(posOfDispType));
/* 4297 */                                         out.write("','Personalize','width=390,height=200,screenX=100,screenY=300,scrollbars=yes')\"><img src=\"/images/icon_edit.gif\"  border=\"0\" title='");
/* 4298 */                                         out.print(FormatUtil.getString("jmxnotification.edit"));
/* 4299 */                                         out.write("'></a>\n");
/*      */                                       }
/* 4301 */                                       out.write("\n\n    <A class='staticlinks' href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4302 */                                       out.print(row.get(posOfResourceID));
/* 4303 */                                       out.write("&attributeIDs=");
/* 4304 */                                       out.print(row.get(posOfAttribID));
/* 4305 */                                       out.write("&attributeToSelect=");
/* 4306 */                                       out.print(row.get(posOfAttribID));
/* 4307 */                                       out.write("&redirectto=");
/* 4308 */                                       out.print(configureThresholdRedirectLink);
/* 4309 */                                       out.write("'>\n    <img src=\"/images/icon_associateaction.gif\" title='");
/* 4310 */                                       out.print(ALERTCONFIG_TEXT);
/* 4311 */                                       out.write("' border=\"0\" ></A>\n\n    ");
/*      */                                       
/* 4313 */                                       if (row.get(posOfAttribType).equals("0"))
/*      */                                       {
/*      */ 
/* 4316 */                                         out.write("\n    <a style=\"cursor: pointer;\" onclick=\"showAttribGraph(");
/* 4317 */                                         out.print(row.get(posOfAttribID));
/* 4318 */                                         out.write(44);
/* 4319 */                                         out.write(39);
/* 4320 */                                         out.print(getTrimmedText(currentMBeanName, 50).replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"));
/* 4321 */                                         out.write("')\" ><img src='/images/icon_linegraph.gif' title='");
/* 4322 */                                         out.print(FormatUtil.getString("jmxnotification.showgraph"));
/* 4323 */                                         out.write("' border='0' ></a>\n\n\n        ");
/*      */                                       }
/*      */                                       
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 4330 */                                       out.write("\n\t\t&nbsp;\n\t");
/*      */                                     }
/*      */                                     
/*      */ 
/* 4334 */                                     out.write("\n</td>\n\n</tr>\n");
/*      */                                   }
/*      */                                   
/*      */ 
/* 4338 */                                   out.write("\n</tr>\n\n<tr>\n\t<td colspan=4  height='25' class=\"tablebottom\"><span class=\"bodytext\">");
/*      */                                   
/* 4340 */                                   PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4341 */                                   _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 4342 */                                   _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                   
/* 4344 */                                   _jspx_th_logic_005fpresent_005f2.setRole("ADMIN");
/* 4345 */                                   int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 4346 */                                   if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                     for (;;) {
/* 4348 */                                       out.write("\n       <a href=\"");
/* 4349 */                                       out.print(configureLink);
/* 4350 */                                       out.write("\" class='staticlinks'>");
/* 4351 */                                       out.print(FormatUtil.getString("am.webclient.cam.adddeleteattributes.text"));
/* 4352 */                                       out.write("</a> ");
/* 4353 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 4354 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4358 */                                   if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 4359 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                                   }
/*      */                                   
/* 4362 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 4363 */                                   out.write("</span></td>\n</tr>\n</table>\n");
/*      */                                 }
/*      */                                 
/*      */ 
/* 4367 */                                 out.write("\n<br>\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 4368 */                                 org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/MyField_div.jsp", out, false);
/* 4369 */                                 out.write("</td></tr></table>\n");
/*      */                                 
/* 4371 */                                 if (columnarAttribsPresent)
/*      */                                 {
/* 4373 */                                   int k = 0;
/* 4374 */                                   String titlePrefix = FormatUtil.getString("am.webclient.common.name.text");
/* 4375 */                                   for (int i = 0; i < colScreenAttribInfo.size(); ???++)
/*      */                                   {
/* 4377 */                                     out.write("\n\t<div class=\"horTableScroll\">\n\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"99%\" class=\"lrtbdarkborder\">\n\t");
/*      */                                     
/* 4379 */                                     List temp1 = (List)colScreenAttribInfo.get(i);
/* 4380 */                                     if (temp1.size() > 0)
/*      */                                     {
/* 4382 */                                       Properties tmpProp = (Properties)((List)temp1.get(0)).get(0);
/* 4383 */                                       String mbeanName = tmpProp.getProperty("mbeanname");
/* 4384 */                                       List h = (List)tmpProp.get("columnnames");
/*      */                                       
/* 4386 */                                       out.write("\n\t\t<tr>\n\t\t<td height=\"24\" width=\"75%\" class=\"tableheadingbborder\" colspan=\"");
/* 4387 */                                       out.print(h.size() + 1);
/* 4388 */                                       out.write("\">\n\t\t");
/* 4389 */                                       out.print(titlePrefix);
/* 4390 */                                       out.write(32);
/* 4391 */                                       out.write(58);
/* 4392 */                                       out.write(32);
/* 4393 */                                       out.print(getTrimmedText(mbeanName, 50));
/* 4394 */                                       out.write("</td>\n\t\t</tr>\n\t\t");
/*      */                                     }
/*      */                                     
/* 4397 */                                     int cnt = 0; for (int size = temp1.size(); cnt < size; ???++)
/*      */                                     {
/*      */ 
/* 4400 */                                       out.write("\n\t\t<tr><td width=\"100%\" style=\"vertical-align: top;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t");
/*      */                                       
/*      */ 
/* 4403 */                                       List oneTableList = (List)temp1.get(cnt);
/* 4404 */                                       Properties camprops = (Properties)oneTableList.get(0);
/* 4405 */                                       List headers = (List)camprops.get("columnnames");
/* 4406 */                                       List thresholdPossibleIDs = (List)camprops.get("thresholdpossibleattrids");
/* 4407 */                                       if ("snmp table".equals(camprops.get("TableType"))) {
/* 4408 */                                         titlePrefix = "SNMP Table Name";
/*      */                                       } else {
/* 4410 */                                         titlePrefix = FormatUtil.getString("am.webclient.camscreen.titleprefix");
/*      */                                       }
/*      */                                       
/*      */ 
/* 4414 */                                       out.write("\n\t\t\t<tr >\n\t\t     ");
/*      */                                       
/* 4416 */                                       String attrs = "";
/* 4417 */                                       int i; for (int a = 0; a < thresholdPossibleIDs.size(); i++)
/*      */                                       {
/* 4419 */                                         attrs = attrs + (String)thresholdPossibleIDs.get(a) + ",";
/*      */                                       }
/*      */                                       
/*      */ 
/* 4423 */                                       out.write("\n\t\t<td height=\"24\" width=\"75%\" class=\"secondchildnode\" colspan=\"");
/* 4424 */                                       out.print(headers.size());
/* 4425 */                                       out.write("\">\n\t\t");
/* 4426 */                                       String temp = (String)camprops.get("attrName");
/* 4427 */                                       out.write("\n\t\t<span class=\"bodytextbold\">");
/* 4428 */                                       out.print(FormatUtil.getString("am.webclient.camscreen.attributegraphs.attribute.text"));
/* 4429 */                                       out.write(32);
/* 4430 */                                       out.write(58);
/* 4431 */                                       out.write(32);
/* 4432 */                                       out.print(getTrimmedText(temp, 50));
/* 4433 */                                       out.write("</span></td>\n\n\t<td class=\"secondchildnode\" width=\"25%\">\n\n\t");
/*      */                                       
/* 4435 */                                       if (thresholdPossibleIDs.size() > 0)
/*      */                                       {
/*      */ 
/*      */ 
/* 4439 */                                         out.write("\n\n\t\t<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4440 */                                         out.print(camprops.get("resourceid"));
/* 4441 */                                         out.write("&attributeIDs=");
/* 4442 */                                         out.print(attrs.substring(0, attrs.length() - 1));
/* 4443 */                                         out.write("&attributeToSelect=");
/* 4444 */                                         out.print(thresholdPossibleIDs.get(0));
/* 4445 */                                         out.write("&redirectto=");
/* 4446 */                                         out.print(configureThresholdRedirectLink);
/* 4447 */                                         out.write("' class=\"staticlinks\">\n        <img src=\"/images/icon_associateaction.gif\" alt=\"Associate Action\" border=\"0\" align=\"absmiddle\" hspace=\"5\" >");
/* 4448 */                                         out.print(ALERTCONFIG_TEXT);
/* 4449 */                                         out.write("</a>\n        ");
/*      */                                       }
/*      */                                       
/*      */ 
/* 4453 */                                       out.write("\n\t\t</td>\n\t\t</tr>\n\t        <tr>\n\t\t");
/*      */                                       
/* 4455 */                                       for (k = 0; k < headers.size(); ???++)
/*      */                                       {
/*      */ 
/* 4458 */                                         out.write("\n\t\t\t\t<td class=\"columnheading\" align=left>\n\t\t\t\t");
/* 4459 */                                         out.print(headers.get(k));
/* 4460 */                                         out.write("\n\t\t\t\t</td>\n\t\t\t");
/*      */                                       }
/*      */                                       
/*      */ 
/*      */ 
/* 4465 */                                       out.write("\n\t\t<td class=\"columnheading\" width=\"19%\">&nbsp;</td>\n\t        </tr>\n\t        ");
/*      */                                       int k;
/* 4467 */                                       for (int j = 1; j < oneTableList.size(); k++)
/*      */                                       {
/* 4469 */                                         String bgclass = "class=\"whitegrayrightalign-reports\"";
/* 4470 */                                         if (j % 2 != 0)
/*      */                                         {
/* 4472 */                                           bgclass = "class=\"whitegrayrightalign-reports\"";
/*      */                                         }
/*      */                                         
/* 4475 */                                         out.write("\n\t        \t\t<tr>\n\t        \t\t");
/*      */                                         
/* 4477 */                                         for (int l = 0; l < headers.size(); m++)
/*      */                                         {
/*      */ 
/* 4480 */                                           out.write("\n\t\t\t\t\t<td height=\"28\"  ");
/* 4481 */                                           out.print(bgclass);
/* 4482 */                                           out.write(" align=\"left\" title=\"");
/* 4483 */                                           out.print(formatDT((String)camprops.get("dctime")));
/* 4484 */                                           out.write("\">\n\t\t\t\t\t\t<div  style=\"WORD-BREAK:BREAK-ALL; word-wrap: break-word; width:100px; white-space:-moz-pre-wrap; white-space: normal;\">");
/* 4485 */                                           out.print(((List)oneTableList.get(j)).get(l));
/* 4486 */                                           out.write("</div>\n\t\t\t\t\t</td>\n\t\t\t\t\t");
/*      */                                         }
/*      */                                         
/*      */ 
/* 4490 */                                         out.write("\n\n\t\t\t<td ");
/* 4491 */                                         out.print(bgclass);
/* 4492 */                                         out.write(" width=\"19%\">&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t");
/*      */                                       }
/*      */                                       
/*      */ 
/* 4496 */                                       out.write("\n\t</table></td></tr>\n\t");
/*      */                                     }
/*      */                                     
/*      */ 
/* 4500 */                                     out.write("\n<tr>\n        <td colspan=");
/* 4501 */                                     out.print(k + 1);
/* 4502 */                                     out.write("  height='25' class=\"tablebottom\"><span class=\"bodytext\">");
/*      */                                     
/* 4504 */                                     PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4505 */                                     _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 4506 */                                     _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                     
/* 4508 */                                     _jspx_th_logic_005fpresent_005f3.setRole("ADMIN");
/* 4509 */                                     int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 4510 */                                     if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                                       for (;;) {
/* 4512 */                                         out.write("\n       <a href=\"");
/* 4513 */                                         out.print(configureLink);
/* 4514 */                                         out.write("\" class='staticlinks'>");
/* 4515 */                                         out.print(FormatUtil.getString("am.webclient.cam.adddeleteattributes.text"));
/* 4516 */                                         out.write("</a> ");
/* 4517 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 4518 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4522 */                                     if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 4523 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                                     }
/*      */                                     
/* 4526 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 4527 */                                     out.write("</span></td>\n</tr>\n\n\n</table><br></div>\n");
/*      */                                   }
/*      */                                 }
/*      */                               }
/*      */                               
/*      */ 
/* 4533 */                               out.write("\n<br><br>\n\n<!-- Added graphs to the Normal Screen -->\n<div id=\"status\" style='Display:none'>");
/* 4534 */                               out.print(FormatUtil.getString("am.webclient.gengraph.text"));
/* 4535 */                               out.write("&nbsp;&nbsp;<img src=\"../images/icon_cogwheel.gif\"></div>\n<div id=\"attributegraphs\"></div>\n\n\n\n<script>\nmyOnLoad();\nfunction myOnLoad()\n{\n/**\t");
/* 4536 */                               if ((request.getParameter("type") != null) && (request.getParameter("type").equals("JBOSS-server"))) {
/* 4537 */                                 out.write("\n\tmyOnJbossLoad();\n\t");
/*      */                               }
/* 4539 */                               out.write("\n\t**/\n\tattributegraphs();\n}\nvar http1;\nfunction attributegraphs()\n{\n        document.getElementById(\"status\").style.display='block';\n        URL='/jsp/cam_graphs.jsp?camIDI=");
/* 4540 */                               out.print(camIDI);
/* 4541 */                               out.write("&haidI=");
/* 4542 */                               out.print(haidI);
/* 4543 */                               out.write("&screenIDI=");
/* 4544 */                               out.print(screenIDI);
/* 4545 */                               out.write("&isfromresourcepage=");
/* 4546 */                               out.print(isFromResourcePage);
/* 4547 */                               out.write("';\n        http1=getHTTPObject();\n        http1.open(\"GET\", URL, true);\n        //http1.onreadystatechange = new Function('if(http1.readyState == 4 && http1.status == 200){document.getElementById(\"status\").innerHTML =\"&nbsp;\",document.getElementById(\"attributegraphs\").innerHTML = http1.responseText;}' );\n\thttp1.onreadystatechange =handleResponse1;\n        http1.send(null);\n}\n\nfunction handleResponse1()\n{\n        if(http1.readyState == 4)\n        {\n                var result = http1.responseText;\n\t\tdocument.getElementById(\"status\").innerHTML =\"&nbsp;\"\n                document.getElementById(\"attributegraphs\").innerHTML = result;\n                document.getElementById(\"attributegraphs\").style.display='block';\n        //      alert('Div similarmonitor display' + document.getElementById(\"multimonitors\").checked);\n        }\n}\n\n\nfunction subAddCustom(linkS) {\n\tlinkS.href = \"");
/* 4548 */                               out.print(configureLink);
/* 4549 */                               out.write("\";\n\treturn true;\n}\n\n$(document).ready(function(){\n\n\tvar customFieldsHash = document.location.hash;\n\n\tcustomFieldsHash = customFieldsHash.split(\"/\")\n\n\tif(customFieldsHash.length > 1)\t");
/* 4550 */                               out.write("\n\t{\n\t\t");
/* 4551 */                               if (_jspx_meth_c_005fif_005f5(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 4553 */                               out.write(10);
/* 4554 */                               out.write(9);
/* 4555 */                               out.write(9);
/* 4556 */                               if (_jspx_meth_c_005fif_005f6(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 4558 */                               out.write("\n\t\tgetCustomFields('");
/* 4559 */                               if (_jspx_meth_c_005fout_005f25(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 4561 */                               out.write("','noalarms',false,customFieldsHash[1],true)\t");
/* 4562 */                               out.write("\n\t}\n\n});\n</script>\n\n\n");
/* 4563 */                               out.write(10);
/* 4564 */                               out.write(10);
/* 4565 */                               out.write("\n   <br>\n   ");
/*      */                               
/* 4567 */                               if (!resourcetype.equals("SNMP"))
/*      */                               {
/* 4569 */                                 String mopRedirectString = encodeurl;
/* 4570 */                                 String linkForMopAction = "/MBeanOperationAction.do?method=showInitialScreen&resourceid=" + resID + "&redirectto=" + mopRedirectString;
/* 4571 */                                 ArrayList mopActions = com.adventnet.appmanager.fault.FaultUtil.getMBeanOperationActionsForResourceID(resID, request.getRemoteUser(), request.isUserInRole("OPERATOR"));
/* 4572 */                                 if (mopActions.size() > 0)
/*      */                                 {
/* 4574 */                                   request.setAttribute("executeMopActions", mopActions);
/*      */                                 }
/*      */                                 
/* 4577 */                                 out.write("\n   ");
/* 4578 */                                 out.write("<!--$Id$-->\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n\n\n\n\n<script>\nfunction deleteMopSelections()\n{\n\tvar sel = false;\n \tfor(i=0;i<document.formab.elements.length;i++)\n \t{\n \t\tif(document.formab.elements[i].type==\"checkbox\")\n \t               {\n \t                        var name = document.formab.elements[i].name;\n \t                        if(name==\"execmopcheckbox\")\n \t                        {\n \t                        \tvar value = document.formab.elements[i].value;\n \t                        \tsel=document.formab.elements[i].checked;\n \t                        \tif(sel)\n \t                        \t{\n \t                        \t\tbreak;\n \t                        \t}\n \t                        }\n \t                 }\n         }\n \tif(!sel)\n \t{\n \t     alert('");
/* 4579 */                                 out.print(FormatUtil.getString("am.webclient.viewaction.alertmbeandelete"));
/* 4580 */                                 out.write("');\n \t}\n \telse if(confirm('");
/* 4581 */                                 out.print(FormatUtil.getString("am.webclient.viewaction.alertdeleteconfirm"));
/* 4582 */                                 out.write("'))\n\t{\n\t    document.formab.action=\"/adminAction.do?method=deleteMBeanOperationAction\";\n\t    document.formab.method=\"Post\"\n\t    document.formab.submit();\n\t}\n}\nfunction deleteThreadDump(url,id)\n{\n      \tif(!confirm(\"");
/* 4583 */                                 out.print(FormatUtil.getString("am.javaruntime.confirm.delete.text"));
/* 4584 */                                 out.write("\"))\n      \t{\n       \t\treturn;//No I18N\n      \t}\n\tvar url =\"/CAMUpdateScreenAttributes.do?method=deletethreadURL&url=\"+url; //NO I18N\n\thttp=getHTTPObject(); //NO I18N\n\thttp.onreadystatechange = handleResponse3;//NO I18N\n\thttp.open(\"GET\",url,true);\n\thttp.send(null); //NO I18N\n}\nfunction getThreadDumpData(rid,tabval)\n{\n\tif(document.getElementById('exturl').style.display=='block')\n\t{\n\t    var showall = document.getElementById('more'); //NO I18N\n\t    showall.innerHTML=\"More...\"; //NO I18N\n\t    toggleDiv('exturl'); //NO I18N\n\t    return;\n\t}\n\tvar date = new Date(); //NO I18N\n\tvar url ='/CAMUpdateScreenAttributes.do?resourceid='+rid+'&method=getThreadDump'; //NO I18N\n\thttp=getHTTPObject(); //NO I18N\n\thttp.onreadystatechange = handleResponse2 //NO I18N\n\thttp.open(\"POST\", url, true); //NO I18N\n\thttp.send(null); //NO I18N\n\n}\nfunction handleResponse2() \n{\n\t if(http.readyState == 4 && http.status == 200)\n\t {\n\t\tvar result = http.responseText;\n\t\tdocument.getElementById('exturl').innerHTML = result; //NO I18N\n\t\tvar showall = document.getElementById('more'); //NO I18N\n");
/* 4585 */                                 out.write("\t\tshowall.innerHTML=\"Hide...\"; //NO I18N\n\t\ttoggleDiv('exturl'); //NO I18N\n\t }\n}\nfunction handleResponse3() {\n\tif (http.readyState == 4) {\n\t\tvar result = http.responseText;\n\t\tvar ele = document.getElementById('groupContent');\n\t\tif(ele)\n\t\t{\n\t\t\tele.innerHTML = result;\n\t\t\tconfBodyLoad();\n\t\t}\n\t}\n\t\n}\n</script>\n<form name=\"formab\">\n<input type=\"hidden\" name=\"redirectto\" value=\"");
/* 4586 */                                 out.print(java.net.URLDecoder.decode(mopRedirectString));
/* 4587 */                                 out.write("\">\n<br>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t\t    <tr>\n\t\t\t\t          <td width=\"100%\" height=\"31\" class=\"tableheading\" >");
/* 4588 */                                 out.print(FormatUtil.getString("am.webclient.common.mbeanoperations.text"));
/* 4589 */                                 out.write(" :</td>\n\t\t\t\t        </tr>\n\t\t\t\t      </table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\n\n            ");
/*      */                                 
/* 4591 */                                 PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.get(PresentTag.class);
/* 4592 */                                 _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 4593 */                                 _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 4595 */                                 _jspx_th_logic_005fpresent_005f4.setName("executeMopActions");
/* 4596 */                                 int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 4597 */                                 if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                   for (;;) {
/* 4599 */                                     out.write("\n            <tr>\n              <td> <SCRIPT LANGUAGE=\"javascript\" SRC=\"../webclient/common/js/windowFunctions.js\"></SCRIPT>\n              </td>\n            </tr>\n            <tr>\n              <td>\n\n\n \t\t<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">\n\t\t<tr>\n\t\t<td width=\"2%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">\n\t\t<input type=\"checkbox\" name=\"headercheckbox\"  onClick=\"javascript:ToggleAll(this,document.formab,'execmopcheckbox')\">\n\t\t</td>\n\n\t\t<td width=\"12%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 4600 */                                     out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 4601 */                                     out.write("</td>\n\t\t<td width=\"21%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 4602 */                                     out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 4603 */                                     out.write("</td>\n\t\t<td width=\"27%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 4604 */                                     out.print(FormatUtil.getString("am.reporttab.mbeanname.text"));
/* 4605 */                                     out.write("</td>\n\t\t<td width=\"23%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 4606 */                                     out.print(FormatUtil.getString("am.webclient.viewaction.operation"));
/* 4607 */                                     out.write(" </td>\n\t\t<!--td width=\"23%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 4608 */                                     out.print(FormatUtil.getString("am.webclient.viewaction.argscount"));
/* 4609 */                                     out.write(" </td-->\n\t\t<td width=\"23%\" height=\"28\" valign=\"center\" class=\"columnheadingnotop\">");
/* 4610 */                                     out.print(FormatUtil.getString("am.webclient.viewaction.targetemail.text"));
/* 4611 */                                     out.write("</td>\n\t\t<td width=\"11%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 4612 */                                     out.print(FormatUtil.getString("am.webclient.threshold.editview"));
/* 4613 */                                     out.write("</td>\n\t\t<td width=\"6%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\"> ");
/* 4614 */                                     out.print(FormatUtil.getString("am.webclient.viewaction.execute"));
/* 4615 */                                     out.write("</td>\n\t\t<td width=\"6%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\"> ");
/* 4616 */                                     out.print(FormatUtil.getString("am.webclient.viewaction.manualexecution"));
/* 4617 */                                     out.write("</td>\n\t\t</tr>\n\t\t");
/*      */                                     
/* 4619 */                                     IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.get(IterateTag.class);
/* 4620 */                                     _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 4621 */                                     _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fpresent_005f4);
/*      */                                     
/* 4623 */                                     _jspx_th_logic_005fiterate_005f0.setName("executeMopActions");
/*      */                                     
/* 4625 */                                     _jspx_th_logic_005fiterate_005f0.setScope("request");
/*      */                                     
/* 4627 */                                     _jspx_th_logic_005fiterate_005f0.setId("moprow");
/*      */                                     
/* 4629 */                                     _jspx_th_logic_005fiterate_005f0.setIndexId("mopm");
/* 4630 */                                     int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 4631 */                                     if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 4632 */                                       Object moprow = null;
/* 4633 */                                       Integer mopm = null;
/* 4634 */                                       if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 4635 */                                         out = _jspx_page_context.pushBody();
/* 4636 */                                         _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 4637 */                                         _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                       }
/* 4639 */                                       moprow = _jspx_page_context.findAttribute("moprow");
/* 4640 */                                       mopm = (Integer)_jspx_page_context.findAttribute("mopm");
/*      */                                       for (;;) {
/* 4642 */                                         out.write(10);
/* 4643 */                                         out.write(9);
/* 4644 */                                         out.write(9);
/*      */                                         
/* 4646 */                                         String bgclass = "whitegrayborder";
/* 4647 */                                         if (mopm.intValue() % 2 != 0)
/*      */                                         {
/* 4649 */                                           bgclass = "yellowgrayborder";
/*      */                                         }
/*      */                                         
/* 4652 */                                         out.write("\n\t\t<tr>\n\n\t\t<td class=\"");
/* 4653 */                                         out.print(bgclass);
/* 4654 */                                         out.write("\">\n\t\t<input type=\"checkbox\" name=\"execmopcheckbox\" value=\"");
/* 4655 */                                         out.print(((ArrayList)moprow).get(0));
/* 4656 */                                         out.write("\">\n\t\t</td>\n\t\t<td height=\"22\" class=\"");
/* 4657 */                                         out.print(bgclass);
/* 4658 */                                         out.write("\"><!--a href=\"#\" class=\"resourcename\" onClick=\"MM_openBrWindow('/showActionProfiles.do?method=getActionDetails&actionid=");
/* 4659 */                                         out.print(((ArrayList)moprow).get(0));
/* 4660 */                                         out.write("','','resizable=yes,width=450,height=185')\"-->\n\t\t");
/* 4661 */                                         out.print(getTrimmedText((String)((ArrayList)moprow).get(1), 25));
/* 4662 */                                         out.write("</a>\n\t\t</td>\n\t\t");
/*      */                                         
/* 4664 */                                         boolean hasArgs = false;
/*      */                                         
/* 4666 */                                         out.write("\n\t\t\t");
/*      */                                         
/* 4668 */                                         IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid.get(IterateTag.class);
/* 4669 */                                         _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 4670 */                                         _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                         
/* 4672 */                                         _jspx_th_logic_005fiterate_005f1.setName("moprow");
/*      */                                         
/* 4674 */                                         _jspx_th_logic_005fiterate_005f1.setId("acolumn");
/*      */                                         
/* 4676 */                                         _jspx_th_logic_005fiterate_005f1.setIndexId("i");
/*      */                                         
/* 4678 */                                         _jspx_th_logic_005fiterate_005f1.setOffset("2");
/* 4679 */                                         int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 4680 */                                         if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 4681 */                                           Object acolumn = null;
/* 4682 */                                           Integer i = null;
/* 4683 */                                           if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 4684 */                                             out = _jspx_page_context.pushBody();
/* 4685 */                                             _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 4686 */                                             _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                           }
/* 4688 */                                           acolumn = _jspx_page_context.findAttribute("acolumn");
/* 4689 */                                           i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                           for (;;) {
/* 4691 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 4693 */                                             if (i.intValue() == 6)
/*      */                                             {
/* 4695 */                                               if (Integer.parseInt((String)acolumn) > 0)
/*      */                                               {
/* 4697 */                                                 hasArgs = true;
/*      */                                               }
/*      */                                               
/*      */                                             }
/*      */                                             else
/*      */                                             {
/* 4703 */                                               out.write("\n\t\t\t<td height='22' class='");
/* 4704 */                                               out.print(bgclass);
/* 4705 */                                               out.write("' title='");
/* 4706 */                                               out.print((String)acolumn);
/* 4707 */                                               out.write("'>\n\n\t\t\t");
/* 4708 */                                               out.print(getTrimmedText((String)acolumn, 25));
/* 4709 */                                               out.write("\n\t\t\t</td>\n\t\t\t");
/*      */                                             }
/*      */                                             
/*      */ 
/* 4713 */                                             out.write("\n\t\t\t");
/* 4714 */                                             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 4715 */                                             acolumn = _jspx_page_context.findAttribute("acolumn");
/* 4716 */                                             i = (Integer)_jspx_page_context.findAttribute("i");
/* 4717 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4720 */                                           if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 4721 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4724 */                                         if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 4725 */                                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                                         }
/*      */                                         
/* 4728 */                                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 4729 */                                         out.write("\n\t\t<td class=\"");
/* 4730 */                                         out.print(bgclass);
/* 4731 */                                         out.write("\"><a href=\"/adminAction.do?method=showMBeanOperationAction&actionID=");
/* 4732 */                                         out.print(((ArrayList)moprow).get(0));
/* 4733 */                                         out.write("&haid=");
/* 4734 */                                         out.print(request.getParameter("haid"));
/* 4735 */                                         out.write("&redirectto=");
/* 4736 */                                         out.print(mopRedirectString);
/* 4737 */                                         out.write("\"><img src=\"/images/icon_edit.gif\"  border=\"0\"></a></td>\n\t\t<td width=\"4%\"height=\"28\" align=\"center\"  class=\"");
/* 4738 */                                         out.print(bgclass);
/* 4739 */                                         out.write("\">\n\t\t");
/* 4740 */                                         if (_jspx_meth_logic_005fpresent_005f5(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                                           return;
/* 4742 */                                         out.write(10);
/* 4743 */                                         out.write(9);
/* 4744 */                                         out.write(9);
/*      */                                         
/* 4746 */                                         NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4747 */                                         _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 4748 */                                         _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                         
/* 4750 */                                         _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO,ENTERPRISEADMIN");
/* 4751 */                                         int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 4752 */                                         if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                           for (;;) {
/* 4754 */                                             out.write("\n \t\t\t<a href=\"/GlobalActions.do?method=testAction&actionID=");
/* 4755 */                                             out.print(((ArrayList)moprow).get(0));
/* 4756 */                                             out.write("&haid=");
/* 4757 */                                             out.print(request.getParameter("haid") + "&redirectto=" + mopRedirectString);
/* 4758 */                                             out.write("\"><img src=\"/images/icon_executeaction.gif\"  border=\"0\"></a></td>\n \t\t\t<td class=\"");
/* 4759 */                                             out.print(bgclass);
/* 4760 */                                             out.write("\">\n \t\t\t");
/*      */                                             
/* 4762 */                                             if (hasArgs)
/*      */                                             {
/*      */ 
/* 4765 */                                               out.write("\n \t\t\t<a href=\"javascript:void(0)\" onclick=\"javascript:MM_openBrWindow('/MBeanOperationAction.do?method=executeMBeanOperationActionWithUserIntervention&actionID=");
/* 4766 */                                               out.print(((ArrayList)moprow).get(0));
/* 4767 */                                               out.write("&haid=");
/* 4768 */                                               out.print(request.getParameter("haid"));
/* 4769 */                                               out.write("','','width=580,height=385')\"><img src=\"/images/icon_execute_user.gif\"  border=\"0\" title='");
/* 4770 */                                               out.print(FormatUtil.getString("am.webclient.viewaction.mbeantitle"));
/* 4771 */                                               out.write("'></a>\n \t\t\t");
/*      */ 
/*      */                                             }
/*      */                                             else
/*      */                                             {
/*      */ 
/* 4777 */                                               out.write("\n \t\t\t");
/* 4778 */                                               out.print(FormatUtil.getString("am.webclient.viewaction.na"));
/* 4779 */                                               out.write("\n \t\t\t");
/*      */                                             }
/*      */                                             
/*      */ 
/* 4783 */                                             out.write("\n \t\t\t</td>\n\t\t");
/* 4784 */                                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 4785 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 4789 */                                         if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 4790 */                                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                                         }
/*      */                                         
/* 4793 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 4794 */                                         out.write("\n\t\t</tr>\n\t\t");
/* 4795 */                                         int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 4796 */                                         moprow = _jspx_page_context.findAttribute("moprow");
/* 4797 */                                         mopm = (Integer)_jspx_page_context.findAttribute("mopm");
/* 4798 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 4801 */                                       if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 4802 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 4805 */                                     if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 4806 */                                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                     }
/*      */                                     
/* 4809 */                                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 4810 */                                     out.write("\n\t\t</table>\n\t  \t\t\t\t<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"Tablebottom\">\n\t  \t\t\t\t<tr>\n\t  \t\t\t\t<td width=\"72%\" height=\"26\" class=\"bodytext\">\n\t  \t\t\t\t");
/*      */                                     
/* 4812 */                                     AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 4813 */                                     _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 4814 */                                     _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_logic_005fpresent_005f4);
/*      */                                     
/* 4816 */                                     _jspx_th_am_005fadminlink_005f0.setHref("javascript:deleteMopSelections(this.form);");
/*      */                                     
/* 4818 */                                     _jspx_th_am_005fadminlink_005f0.setEnableClass("staticlinks");
/* 4819 */                                     int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 4820 */                                     if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 4821 */                                       if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 4822 */                                         out = _jspx_page_context.pushBody();
/* 4823 */                                         _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 4824 */                                         _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 4827 */                                         out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 4828 */                                         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 4829 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 4832 */                                       if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 4833 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 4836 */                                     if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 4837 */                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                                     }
/*      */                                     
/* 4840 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 4841 */                                     out.write("\n\t  \t\t\t\t|\n\t  \t\t\t\t");
/*      */                                     
/* 4843 */                                     AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 4844 */                                     _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 4845 */                                     _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_logic_005fpresent_005f4);
/*      */                                     
/* 4847 */                                     _jspx_th_am_005fadminlink_005f1.setHref(linkForMopAction);
/*      */                                     
/* 4849 */                                     _jspx_th_am_005fadminlink_005f1.setEnableClass("staticlinks");
/* 4850 */                                     int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 4851 */                                     if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 4852 */                                       if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 4853 */                                         out = _jspx_page_context.pushBody();
/* 4854 */                                         _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/* 4855 */                                         _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 4858 */                                         out.write("\n\t  \t\t\t\t");
/* 4859 */                                         out.print(FormatUtil.getString("am.webclient.threshold.addnew"));
/* 4860 */                                         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 4861 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 4864 */                                       if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 4865 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 4868 */                                     if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 4869 */                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                                     }
/*      */                                     
/* 4872 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 4873 */                                     out.write("</td>\n\t  \t\t\t\t</tr>\n\t  \t\t\t\t</table>\n\n              </td>\n            </tr>\n            ");
/* 4874 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 4875 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4879 */                                 if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 4880 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                                 }
/*      */                                 
/* 4883 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f4);
/* 4884 */                                 out.write("\n            ");
/*      */                                 
/* 4886 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.get(NotPresentTag.class);
/* 4887 */                                 _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 4888 */                                 _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 4890 */                                 _jspx_th_logic_005fnotPresent_005f1.setName("executeMopActions");
/* 4891 */                                 int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 4892 */                                 if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                                   for (;;) {
/* 4894 */                                     out.write("\n            <tr>\n            <td>\n           \t\t<table>\n           \t\t<tr>\n\n<td class=\"bodytext\" height=\"29\" valign=\"center\">&nbsp;");
/* 4895 */                                     out.print(FormatUtil.getString("am.webclient.viewaction.noactionscreated"));
/* 4896 */                                     out.write(32);
/*      */                                     
/* 4898 */                                     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4899 */                                     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 4900 */                                     _jspx_th_c_005fif_005f7.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                                     
/* 4902 */                                     _jspx_th_c_005fif_005f7.setTest("${!empty ADMIN}");
/* 4903 */                                     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 4904 */                                     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                       for (;;) {
/* 4906 */                                         out.write(10);
/* 4907 */                                         out.write(32);
/* 4908 */                                         out.write(32);
/* 4909 */                                         out.print(FormatUtil.getString("am.webclient.viewaction.clickto"));
/* 4910 */                                         out.write(" <a href=\"");
/* 4911 */                                         out.print(linkForMopAction);
/* 4912 */                                         out.write("\" class=\"resourcename\">\n  ");
/* 4913 */                                         out.print(FormatUtil.getString("am.webclient.threshold.creatembean"));
/* 4914 */                                         out.write("</a>");
/* 4915 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 4916 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4920 */                                     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 4921 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                     }
/*      */                                     
/* 4924 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 4925 */                                     out.write("</td>\n           \t\t</tr>\n               \t</table>\n           </td>\n           </tr>\n           ");
/* 4926 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 4927 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4931 */                                 if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 4932 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                                 }
/*      */                                 
/* 4935 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 4936 */                                 out.write("\n          \t</table>\n\n");
/*      */                                 
/* 4938 */                                 HashMap threadProps11 = (HashMap)request.getAttribute("threadProps");
/*      */                                 try {
/* 4940 */                                   if (threadProps11 != null) {
/* 4941 */                                     ArrayList threaddumphistory = (ArrayList)threadProps11.get("threadurls");
/* 4942 */                                     int rowCount = ((Integer)threadProps11.get("ROW_COUNT")).intValue();
/* 4943 */                                     String resourceid11 = "" + request.getParameter("resourceid");
/*      */                                     
/* 4945 */                                     out.write(10);
/*      */                                     
/* 4947 */                                     PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4948 */                                     _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 4949 */                                     _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                     
/* 4951 */                                     _jspx_th_logic_005fpresent_005f6.setRole("ADMIN");
/* 4952 */                                     int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 4953 */                                     if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                                       for (;;) {
/* 4955 */                                         out.write("\n <br>\n     <input class=\"buttons\" value=\"");
/* 4956 */                                         out.print(FormatUtil.getString("am.webclient.jdk15.threadinfo.text"));
/* 4957 */                                         out.write("\" type=\"button\" onClick=\"javascript:MM_openBrWindow('/JavaRuntime.do?method=getThreadInfo&resourceid=");
/* 4958 */                                         out.print(resourceid11);
/* 4959 */                                         out.write("','ThreadInfo','scrollbars=yes,resizable=yes')\"> \n <br>\n");
/* 4960 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 4961 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4965 */                                     if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 4966 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                                     }
/*      */                                     
/* 4969 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 4970 */                                     out.write("\n\n<br>\n<table width=\"99%\"   border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n<td colspan=\"2\" height=\"31\" class=\"tableheading\">");
/* 4971 */                                     out.print(FormatUtil.getString("am.javaruntime.threaddumphistory"));
/* 4972 */                                     out.write("</td>\n</tr> \n");
/*      */                                     
/* 4974 */                                     if (threaddumphistory.size() > 0)
/*      */                                     {
/*      */ 
/* 4977 */                                       out.write("\n\t<tr>\n\t<td width=\"80%\" class=\"columnheadingb\">");
/* 4978 */                                       out.print(FormatUtil.getString("am.webclient.historydata.date.text"));
/* 4979 */                                       out.write(32);
/* 4980 */                                       out.write(38);
/* 4981 */                                       out.write(32);
/* 4982 */                                       out.print(FormatUtil.getString("am.webclient.historydata.time.text"));
/* 4983 */                                       out.write("</td>\n");
/* 4984 */                                       if ((request.isUserInRole("ADMIN")) && (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))
/*      */                                       {
/*      */ 
/* 4987 */                                         out.write("\n\t<td width=\"20%%\" class=\"columnheadingb\">");
/* 4988 */                                         out.print(FormatUtil.getString("am.webclient.rbm.delete.text"));
/* 4989 */                                         out.write("</td>\n");
/*      */                                       }
/*      */                                       
/*      */ 
/* 4993 */                                       out.write("\n</tr>\n\t\n");
/*      */                                       
/* 4995 */                                       boolean extra = false;
/* 4996 */                                       for (int k = 0; k < threaddumphistory.size(); m++)
/*      */                                       {
/* 4998 */                                         Properties url = (Properties)threaddumphistory.get(k);
/*      */                                         
/* 5000 */                                         out.write("\n\t<tr>\n\t<td style=\"padding-left:26px\" class=\"whitegrayborderbr\" title=\"");
/* 5001 */                                         out.print(url.getProperty("URL"));
/* 5002 */                                         out.write("\">\n\t<a class=\"staticlinks-blue\" href=\"javascript:void(0);\" onclick=\"javascript:MM_openBrWindow('");
/* 5003 */                                         out.print(url.getProperty("URL"));
/* 5004 */                                         out.write("','ThreadInfo','scrollbars=yes,resizable=yes')\">");
/* 5005 */                                         out.print(url.getProperty("DSPNAME"));
/* 5006 */                                         out.write("</a>\n\t</td>\n");
/* 5007 */                                         if ((request.isUserInRole("ADMIN")) && (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))
/*      */                                         {
/*      */ 
/* 5010 */                                           out.write("\t\n\t<td class=\"whitegrayborderbr\">\n\t<a title=\"Delete Thread Dump File\"  class=\"staticlinks\" href=\"javascript:void(0);\" onclick=\"javascript:deleteThreadDump('");
/* 5011 */                                           out.print(url.getProperty("ABSURL"));
/* 5012 */                                           out.write(39);
/* 5013 */                                           out.write(44);
/* 5014 */                                           out.write(39);
/* 5015 */                                           out.print(resourceid11);
/* 5016 */                                           out.write("');return false;\">\n\t<img hspace=\"5\" border=\"0\" src=\"/images/deleteWidget.gif\"/>\n\t</a>\n\t</td>\n");
/*      */                                         }
/*      */                                         
/*      */ 
/* 5020 */                                         out.write("\n\t</tr>\n");
/*      */                                       }
/*      */                                       
/*      */ 
/* 5024 */                                       out.write("\t \n\t<tr>\n\t<td colspan=\"2\">\n\t<div id=\"exturl\" style=\"display:none\">\n\n\t</div>\n\t</td>\n\t</tr>\n");
/*      */                                       
/* 5026 */                                       if (rowCount > 5)
/*      */                                       {
/*      */ 
/* 5029 */                                         out.write("\n\t<tr>\n\t<td class=\"columnheadingb\" colspan=\"2\" align=\"right\"><a class=\"bodytext-nounderline\" href=\"javascript:void(0)\" id=\"more\" onclick=\"javascript:getThreadDumpData('");
/* 5030 */                                         out.print(resourceid11);
/* 5031 */                                         out.write("');\" >More...</a></td>");
/* 5032 */                                         out.write("\n\t</tr>\n");
/*      */                                       }
/*      */                                       
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 5039 */                                       out.write("\n\t<tr>\n\t<td  colspan=\"2\" class=\"whitegrayborderbr\" align=\"center\">");
/* 5040 */                                       out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 5041 */                                       out.write("</td>\n\t</tr>\n");
/*      */                                     }
/*      */                                     
/*      */ 
/* 5045 */                                     out.write("\n</table>\n");
/*      */                                   }
/*      */                                 } catch (Exception ex) {
/* 5048 */                                   ex.printStackTrace();
/*      */                                 }
/* 5050 */                                 out.write("\n</form>\n");
/* 5051 */                                 out.write("\n   ");
/*      */                               }
/*      */                               
/* 5054 */                               if (resourcetype.equals("JMX1.2-MX4J-RMI"))
/*      */                               {
/*      */ 
/* 5057 */                                 out.write("\n     <br>\n      ");
/* 5058 */                                 out.write("<!--$Id$-->\n\n<script>\nfunction fnSelectAll(e,name)\n{\n    ToggleAll(e,document.formac,name)\n}\n\nfunction deleteJMXNotifSelections()\n{\n\t");
/* 5059 */                                 if (_jspx_meth_logic_005fpresent_005f7(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 5061 */                                 out.write(10);
/* 5062 */                                 out.write(9);
/*      */                                 
/* 5064 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 5065 */                                 _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 5066 */                                 _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 5068 */                                 _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 5069 */                                 int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 5070 */                                 if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                                   for (;;) {
/* 5072 */                                     out.write(" \n\tif(!checkforOneSelected(document.formac,\"checkbox\"))\n\t{\n\t\talert('");
/* 5073 */                                     out.print(FormatUtil.getString("jmxnotification.alert.selectone"));
/* 5074 */                                     out.write("');\n\t\treturn;\n\t}\n\tif(confirm('");
/* 5075 */                                     out.print(FormatUtil.getString("jmxnotification.alert.delete"));
/* 5076 */                                     out.write("'))\n    {\n\t\tdocument.formac.action=\"/adminAction.do?method=deleteJMXNotificationListener\";\n\t\tdocument.formac.method=\"Post\"\n\t\tdocument.formac.submit();\n\t}\n\t");
/* 5077 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 5078 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 5082 */                                 if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 5083 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                                 }
/*      */                                 
/* 5086 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 5087 */                                 out.write("\n}\n</script>\n\n<form name=\"formac\">\n");
/*      */                                 
/* 5089 */                                 ManagedApplication mo = new ManagedApplication();
/* 5090 */                                 String checkquery = "select ID,NAME,AM_ManagedObject.DISPLAYNAME,OBJECTNAME,STATUS,SEVERITY,(case when AM_JMXNOTIFICATION_PROFILES.SEVERITY='1' then 'Critical' when AM_JMXNOTIFICATION_PROFILES.SEVERITY='4' then 'Warning' else 'Clear' end)  as SEVERITY,(case when AM_JMXNOTIFICATION_PROFILES.STATUS='1' then 'Enabled' else 'Disabled' end)  as STATUS,AM_JMXNOTIFICATION_PROFILES.RESOURCEID from AM_JMXNOTIFICATION_PROFILES,AM_ManagedObject where AM_JMXNOTIFICATION_PROFILES.MBEANRESOURCEID=AM_ManagedObject.RESOURCEID and AM_JMXNOTIFICATION_PROFILES.MBEANRESOURCEID=" + resID;
/* 5091 */                                 ArrayList list1 = mo.getPropertiesList(checkquery);
/* 5092 */                                 request.setAttribute("list1", list1);
/* 5093 */                                 String bgcolor = "";
/*      */                                 
/* 5095 */                                 out.write("\n<a name=\"notif_list\"></a>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr> \n    <td colspan=\"8\" height=\"29\" class=\"tableheading\" >");
/* 5096 */                                 out.print(FormatUtil.getString("jmxnotification.name"));
/* 5097 */                                 out.write("</td>\n  </tr>\n  ");
/*      */                                 
/* 5099 */                                 ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5100 */                                 _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 5101 */                                 _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/* 5102 */                                 int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 5103 */                                 if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                                   for (;;) {
/* 5105 */                                     out.write(10);
/* 5106 */                                     out.write(32);
/* 5107 */                                     out.write(32);
/*      */                                     
/* 5109 */                                     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5110 */                                     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 5111 */                                     _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                     
/* 5113 */                                     _jspx_th_c_005fwhen_005f1.setTest("${!empty list1}");
/* 5114 */                                     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 5115 */                                     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                       for (;;) {
/* 5117 */                                         out.write("\n   <tr>\n    <td width=\"5%\" height=\"28\" valign=\"center\"  class=\"columnheading\">");
/* 5118 */                                         if (_jspx_meth_c_005fchoose_005f2(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/*      */                                           return;
/* 5120 */                                         out.write("</td>\n      <td class=\"columnheading\" align=\"center\">");
/* 5121 */                                         out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 5122 */                                         out.write("</td>\n      <td class=\"columnheading\" align=\"center\">");
/* 5123 */                                         out.print(FormatUtil.getString("jmxnotification.monitor"));
/* 5124 */                                         out.write("</td>\n      <td class=\"columnheading\" align=\"center\">");
/* 5125 */                                         out.print(FormatUtil.getString("am.webclient.common.mbean"));
/* 5126 */                                         out.write("</td>\n      <td class=\"columnheading\" align=\"center\">");
/* 5127 */                                         out.print(FormatUtil.getString("jmxnotification.severity"));
/* 5128 */                                         out.write("</td>\n      <td class=\"columnheading\" align=\"center\">");
/* 5129 */                                         out.print(FormatUtil.getString("jmxnotification.status"));
/* 5130 */                                         out.write("</td>\n      <td class=\"columnheading\" align=\"center\">");
/* 5131 */                                         out.print(FormatUtil.getString("jmxnotification.edit"));
/* 5132 */                                         out.write("</td>\n  </tr>\n  ");
/*      */                                         
/* 5134 */                                         ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 5135 */                                         _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 5136 */                                         _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fwhen_005f1);
/*      */                                         
/* 5138 */                                         _jspx_th_c_005fforEach_005f0.setVar("props");
/*      */                                         
/* 5140 */                                         _jspx_th_c_005fforEach_005f0.setItems("${list1}");
/*      */                                         
/* 5142 */                                         _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 5143 */                                         int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                                         try {
/* 5145 */                                           int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 5146 */                                           if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                             for (;;) {
/* 5148 */                                               out.write("\n\t  ");
/*      */                                               
/* 5150 */                                               IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5151 */                                               _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 5152 */                                               _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                               
/* 5154 */                                               _jspx_th_c_005fif_005f8.setTest("${status.count %2 == 1}");
/* 5155 */                                               int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 5156 */                                               if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                                 for (;;) {
/* 5158 */                                                   out.write("\n\t  ");
/* 5159 */                                                   bgcolor = "whitegrayborder";
/* 5160 */                                                   out.write("\n\t  ");
/* 5161 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 5162 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 5166 */                                               if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 5167 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/*      */                                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5246 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 5247 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 5170 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 5171 */                                               out.write("\n\t  ");
/*      */                                               
/* 5173 */                                               IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5174 */                                               _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 5175 */                                               _jspx_th_c_005fif_005f9.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                               
/* 5177 */                                               _jspx_th_c_005fif_005f9.setTest("${status.count %2 == 0}");
/* 5178 */                                               int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 5179 */                                               if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                                 for (;;) {
/* 5181 */                                                   out.write(" \n\t  ");
/* 5182 */                                                   bgcolor = "yellowgrayborder";
/* 5183 */                                                   out.write("\n\t  ");
/* 5184 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 5185 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 5189 */                                               if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 5190 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/*      */                                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5246 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 5247 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 5193 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 5194 */                                               out.write(" \t \t\n\t  <tr>\n\t\t<td class=\"");
/* 5195 */                                               out.print(bgcolor);
/* 5196 */                                               out.write("\">\n\t\t<input type=\"checkbox\" name=\"checkbox\" value='");
/* 5197 */                                               if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5246 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 5247 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 5199 */                                               out.write("'>\n\t\t</td>  \t\t\n\t\t<td class=\"");
/* 5200 */                                               out.print(bgcolor);
/* 5201 */                                               out.write("\" >\n\t\t  ");
/* 5202 */                                               if (_jspx_meth_c_005fchoose_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5246 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 5247 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 5204 */                                               out.write("\n\t\t</td>\n\t\t<td class=\"");
/* 5205 */                                               out.print(bgcolor);
/* 5206 */                                               out.write("\" align=\"center\">\n\t\t  ");
/* 5207 */                                               if (_jspx_meth_c_005fchoose_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 5246 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 5247 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 5209 */                                               out.write("\n\t\t</td>\n\t\t<td class=\"");
/* 5210 */                                               out.print(bgcolor);
/* 5211 */                                               out.write("\" align=\"center\">\n\t\t  ");
/* 5212 */                                               if (_jspx_meth_c_005fchoose_005f5(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 5246 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 5247 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 5214 */                                               out.write("\n\t\t</td>\n\t\t<td class=\"");
/* 5215 */                                               out.print(bgcolor);
/* 5216 */                                               out.write("\" align=\"center\">\n\t\t  ");
/* 5217 */                                               if (_jspx_meth_c_005fchoose_005f6(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 5246 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 5247 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 5219 */                                               out.write("\n\t\t</td>\n\t\t<td class=\"");
/* 5220 */                                               out.print(bgcolor);
/* 5221 */                                               out.write("\" align=\"center\">\n\t\t   ");
/* 5222 */                                               if (_jspx_meth_c_005fchoose_005f7(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 5246 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 5247 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 5224 */                                               out.write("\t\t\t\n\t\t</td>\n\t\t<td class=\"");
/* 5225 */                                               out.print(bgcolor);
/* 5226 */                                               out.write("\" align=\"center\">\n\t\t   <a href='/adminAction.do?method=editJMXNotificationListener&notificationid=");
/* 5227 */                                               if (_jspx_meth_c_005fout_005f32(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 5246 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 5247 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 5229 */                                               out.write("&edit=true&resourceid=");
/* 5230 */                                               if (_jspx_meth_c_005fout_005f33(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 5246 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 5247 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 5232 */                                               out.write("'><img src=\"/images/icon_edit.gif\"  border=\"0\"></a>    \n\t\t</td>\t\n\t  </tr>\n  ");
/* 5233 */                                               int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 5234 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 5238 */                                           if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5246 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 5247 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/*      */                                         }
/*      */                                         catch (Throwable _jspx_exception)
/*      */                                         {
/*      */                                           for (;;)
/*      */                                           {
/* 5242 */                                             int tmp21130_21129 = 0; int[] tmp21130_21127 = _jspx_push_body_count_c_005fforEach_005f0; int tmp21132_21131 = tmp21130_21127[tmp21130_21129];tmp21130_21127[tmp21130_21129] = (tmp21132_21131 - 1); if (tmp21132_21131 <= 0) break;
/* 5243 */                                             out = _jspx_page_context.popBody(); }
/* 5244 */                                           _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                                         } finally {
/* 5246 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 5247 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                                         }
/* 5249 */                                         out.write("\n  <tr>\n    <tr class=\"Tablebottom\">\n  \t<td height=\"29\" colspan=\"8\" class=\"bodytext\">&nbsp;&nbsp;<a href=\"javascript:deleteJMXNotifSelections();\" class=\"staticlinks\">");
/* 5250 */                                         out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 5251 */                                         out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;\n  \t<a href=\"/MBeanOperationAction.do?method=showDomainsForJMXNotifications&actionresourceid=");
/* 5252 */                                         out.print(resID);
/* 5253 */                                         out.write("\" class=\"staticlinks\">");
/* 5254 */                                         out.print(FormatUtil.getString("jmxnotification.addnew"));
/* 5255 */                                         out.write("</a>&nbsp;&nbsp;</td>\t\n    </tr>\n  </tr>\n  ");
/* 5256 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 5257 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5261 */                                     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 5262 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                                     }
/*      */                                     
/* 5265 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 5266 */                                     out.write(10);
/* 5267 */                                     out.write(32);
/* 5268 */                                     out.write(32);
/*      */                                     
/* 5270 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5271 */                                     _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 5272 */                                     _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f1);
/* 5273 */                                     int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 5274 */                                     if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                                       for (;;) {
/* 5276 */                                         out.write("\n\t  <tr>\n\t\t<td colspan=\"8\" height=\"32\" class=\"bodytext\" align=\"left\">&nbsp;&nbsp;");
/* 5277 */                                         out.print(FormatUtil.getString("jmxnotification.nonotification"));
/* 5278 */                                         out.write(32);
/*      */                                         
/* 5280 */                                         PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5281 */                                         _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 5282 */                                         _jspx_th_logic_005fpresent_005f8.setParent(_jspx_th_c_005fotherwise_005f7);
/*      */                                         
/* 5284 */                                         _jspx_th_logic_005fpresent_005f8.setRole("ADMIN");
/* 5285 */                                         int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 5286 */                                         if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                                           for (;;) {
/* 5288 */                                             out.print(FormatUtil.getString("jmxnotification.clickhere"));
/* 5289 */                                             out.write("<a href=\"/MBeanOperationAction.do?method=showDomainsForJMXNotifications&actionresourceid=");
/* 5290 */                                             out.print(resID);
/* 5291 */                                             out.write("\" class=\"staticlinks\"> ");
/* 5292 */                                             out.print(FormatUtil.getString("jmxnotification.createnew"));
/* 5293 */                                             out.write("</a>");
/* 5294 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 5295 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 5299 */                                         if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 5300 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8); return;
/*      */                                         }
/*      */                                         
/* 5303 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 5304 */                                         out.write("</td>\n\t  </tr>\n  ");
/* 5305 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 5306 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5310 */                                     if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 5311 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                                     }
/*      */                                     
/* 5314 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 5315 */                                     out.write(10);
/* 5316 */                                     out.write(32);
/* 5317 */                                     out.write(32);
/* 5318 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 5319 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 5323 */                                 if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 5324 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                                 }
/*      */                                 
/* 5327 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 5328 */                                 out.write("\n</table>\n</form>\n\n");
/* 5329 */                                 out.write("\n     ");
/*      */                               }
/*      */                             }
/*      */                             
/* 5333 */                             if ((resourcetype.equals("IIS-server")) && (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.wmimonitors.remove")))
/*      */                             {
/* 5335 */                               String hostresourceid = (String)request.getAttribute("hostresourceid");
/* 5336 */                               String mode = null;
/* 5337 */                               if (hostresourceid == null)
/*      */                               {
/* 5339 */                                 String query1 = "select InetService.TARGETNAME,test.RESOURCEID ,AM_HOSTINFO.MODE from AM_ManagedObject left outer join InetService on InetService.NAME=AM_ManagedObject.RESOURCENAME left outer join CollectData on CollectData.TARGETADDRESS=InetService.TARGETADDRESS and CollectData.COMPONENTNAME='HOST' left outer join AM_ManagedObject test on test.RESOURCENAME=InetService.TARGETNAME left outer join AM_HOSTINFO on AM_HOSTINFO.RESOURCEID=test.RESOURCEID where AM_ManagedObject.RESOURCEID='" + resID + "'";
/* 5340 */                                 rs = AMConnectionPool.executeQueryStmt(query1);
/* 5341 */                                 if (rs.next())
/*      */                                 {
/* 5343 */                                   hostresourceid = rs.getString(2);
/* 5344 */                                   mode = rs.getString(3);
/*      */                                 }
/* 5346 */                                 rs.close();
/*      */                               }
/* 5348 */                               boolean isDataAvailable = false;
/* 5349 */                               String query = "select AM_ManagedObject.DISPLAYNAME from AM_ManagedObject  join AM_PARENTCHILDMAPPER  on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where  AM_PARENTCHILDMAPPER.PARENTID='" + resID + "' AND AM_ManagedObject.TYPE IN ('IIS-Website', 'IIS-ApplicationPool')";
/* 5350 */                               rs = AMConnectionPool.executeQueryStmt(query);
/* 5351 */                               if (rs.next())
/*      */                               {
/* 5353 */                                 isDataAvailable = true;
/*      */                               }
/* 5355 */                               rs.close();
/* 5356 */                               if ((!isDataAvailable) && ((mode == null) || (mode.equals("null"))))
/*      */                               {
/*      */ 
/*      */ 
/* 5360 */                                 out.write("\n<BR>\n<table border=\"0\" width=\"99%\" cellspacing=\"0\" cellpadding=\"0\">\n<tr>\n<td align=\"left\">\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n<tr>\n\t<td  height=\"31\" class=\"tableheading\">");
/* 5361 */                                 out.print(FormatUtil.getString("am.webclient.iis.websiteandpool.heading.text"));
/* 5362 */                                 out.write("</td>\n</tr>\n</table>\n<table align=\"left\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"lrbborder\">\n<tr>\n\t\t<td align=\"center\" width=\"50%\" height=\"39\" class=\"bodytext\">");
/* 5363 */                                 out.print(FormatUtil.getString("am.webclient.iis.wmimode.siteAndPool.alert.text", new String[] { "/showresource.do?resourceid=" + hostresourceid + "&method=showResourceForResourceID&editPage=true" }));
/* 5364 */                                 out.write("</td>\n\t</tr>\n</table>\n</td>\n</tr>\n</table>\n");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 5370 */                                 out.write("\n<br>\n<table border=\"0\" width=\"99%\" cellspacing=\"0\" cellpadding=\"0\">\n<tr>\n<td align=\"left\">\n");
/* 5371 */                                 org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "IISWebsiteStats.jsp", out, false);
/* 5372 */                                 out.write("\n</td>\n</tr>\n</table>\n<!-- Application Pool List-->\n");
/*      */                                 
/* 5374 */                                 ArrayList attrList = new ArrayList();
/* 5375 */                                 attrList.add("2016");
/* 5376 */                                 attrList.add("2017");
/* 5377 */                                 ArrayList poolList = new ArrayList();
/* 5378 */                                 ArrayList<Properties> appPoolList = (ArrayList)request.getAttribute("ApplicationPoolList");
/* 5379 */                                 for (Properties prop : appPoolList)
/*      */                                 {
/* 5381 */                                   poolList.add(prop.getProperty("CHILDID"));
/*      */                                 }
/* 5383 */                                 Properties poolAlertStatusProp = getStatus(poolList, attrList);
/* 5384 */                                 String params = "";
/* 5385 */                                 Enumeration enuma1 = request.getParameterNames();
/* 5386 */                                 while (enuma1.hasMoreElements())
/*      */                                 {
/* 5388 */                                   String paramName = (String)enuma1.nextElement();
/* 5389 */                                   if ((!paramName.equals("redirectto")) && (!paramName.equals("websiteidtoDelete")) && 
/*      */                                   
/*      */ 
/*      */ 
/* 5393 */                                     (!paramName.equals("websiteid")) && (!paramName.equals("websitename")))
/*      */                                   {
/*      */ 
/*      */ 
/* 5397 */                                     if (params.length() != 0)
/*      */                                     {
/* 5399 */                                       params = params + "&";
/*      */                                     }
/*      */                                     
/* 5402 */                                     params = params + paramName + "=" + request.getParameter(paramName);
/*      */                                   } }
/* 5404 */                                 String IISmonitorpage = "/showresource.do?" + params;
/*      */                                 
/* 5406 */                                 out.write("\n<BR>\n<table border=\"0\" width=\"99%\" cellspacing=\"0\" cellpadding=\"0\">\n<tr>\n<td align=\"left\">\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n<tr>\n\t<td  height=\"31\" class=\"tableheading\">");
/* 5407 */                                 out.print(FormatUtil.getString("am.webclient.iis.applicationpool.text"));
/* 5408 */                                 out.write("</td>\n</tr>\n</table>\n");
/*      */                                 
/* 5410 */                                 ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5411 */                                 _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 5412 */                                 _jspx_th_c_005fchoose_005f8.setParent(_jspx_th_tiles_005fput_005f4);
/* 5413 */                                 int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 5414 */                                 if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */                                   for (;;) {
/* 5416 */                                     out.write(10);
/*      */                                     
/* 5418 */                                     WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5419 */                                     _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 5420 */                                     _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */                                     
/* 5422 */                                     _jspx_th_c_005fwhen_005f8.setTest("${!empty ApplicationPoolList}");
/* 5423 */                                     int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 5424 */                                     if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                                       for (;;) {
/* 5426 */                                         out.write("\n<table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbborder\">\n<tr>\n\t<td class=\"columnheadingnotop\" width=\"25%\">");
/* 5427 */                                         out.print(FormatUtil.getString("am.webclient.iis.applicationpoolname.text"));
/* 5428 */                                         out.write("</td>\n\t<td class=\"columnheadingnotop\" width=\"15%\" align=\"center\">");
/* 5429 */                                         out.print(FormatUtil.getString("Availability"));
/* 5430 */                                         out.write("</td>\n\t<td class=\"columnheadingnotop\" width=\"15%\" align=\"center\">");
/* 5431 */                                         out.print(FormatUtil.getString("Health"));
/* 5432 */                                         out.write(" </td>\n\t<td class=\"columnheadingnotop\" nowrap>");
/* 5433 */                                         out.print(FormatUtil.getString("am.webclient.iis.appPool.cpuUsage"));
/* 5434 */                                         out.write(" </td>\n\t<td class=\"columnheadingnotop\" nowrap>");
/* 5435 */                                         out.print(FormatUtil.getString("am.webclient.iis.appPool.memUsage"));
/* 5436 */                                         out.write(" </td>\n\t<td class=\"columnheadingnotop\" nowrap>");
/* 5437 */                                         out.print(FormatUtil.getString("am.webclient.iis.appPool.processCnt"));
/* 5438 */                                         out.write(" </td>\n\t");
/*      */                                         
/* 5440 */                                         PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5441 */                                         _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 5442 */                                         _jspx_th_logic_005fpresent_005f9.setParent(_jspx_th_c_005fwhen_005f8);
/*      */                                         
/* 5444 */                                         _jspx_th_logic_005fpresent_005f9.setRole("ADMIN,ENTERPRISEADMIN");
/* 5445 */                                         int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 5446 */                                         if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                                           for (;;) {
/* 5448 */                                             out.write("\n\t<td width=\"15%\" class=\"columnheadingnotop\">");
/* 5449 */                                             out.print(FormatUtil.getString("am.webclient.camscreen.actions.text"));
/* 5450 */                                             out.write(" </td>\n\t");
/* 5451 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 5452 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 5456 */                                         if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 5457 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9); return;
/*      */                                         }
/*      */                                         
/* 5460 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 5461 */                                         out.write("\n</tr>\n");
/*      */                                         
/* 5463 */                                         ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 5464 */                                         _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 5465 */                                         _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fwhen_005f8);
/*      */                                         
/* 5467 */                                         _jspx_th_c_005fforEach_005f1.setVar("row");
/*      */                                         
/* 5469 */                                         _jspx_th_c_005fforEach_005f1.setItems("${ApplicationPoolList}");
/*      */                                         
/* 5471 */                                         _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 5472 */                                         int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                                         try {
/* 5474 */                                           int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 5475 */                                           if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                                             for (;;) {
/* 5477 */                                               out.write(10);
/* 5478 */                                               if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 5480 */                                               out.write(10);
/* 5481 */                                               if (_jspx_meth_c_005fchoose_005f9(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 5483 */                                               out.write(10);
/*      */                                               
/* 5485 */                                               String disableLink = "";
/* 5486 */                                               String websitestyleclass = "";
/*      */                                               
/* 5488 */                                               out.write(10);
/*      */                                               
/* 5490 */                                               ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5491 */                                               _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 5492 */                                               _jspx_th_c_005fchoose_005f10.setParent(_jspx_th_c_005fforEach_005f1);
/* 5493 */                                               int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 5494 */                                               if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */                                                 for (;;) {
/* 5496 */                                                   out.write(10);
/* 5497 */                                                   out.write(9);
/*      */                                                   
/* 5499 */                                                   WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5500 */                                                   _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 5501 */                                                   _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/*      */                                                   
/* 5503 */                                                   _jspx_th_c_005fwhen_005f10.setTest("${row.DCSTARTED == '7'}");
/* 5504 */                                                   int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 5505 */                                                   if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                                                     for (;;) {
/* 5507 */                                                       out.write(10);
/* 5508 */                                                       out.write(9);
/*      */                                                       
/* 5510 */                                                       disableLink = "<a href=\"javascript:void(0)\" onClick=\"changeWebsiteStatus('enable','" + pageContext.getAttribute("poolResourceID") + "','" + resID + "','applicationpool')\" ><img src=\"/images/cross.gif\" align=\"absmiddle\" title=\"" + FormatUtil.getString("am.webclient.schedulereport.showwschedule.disable.text") + "\" id=\"1\" border=\"0\">";
/* 5511 */                                                       websitestyleclass = "disabledtext";
/*      */                                                       
/* 5513 */                                                       out.write(10);
/* 5514 */                                                       out.write(9);
/* 5515 */                                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 5516 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/*      */                                                   }
/* 5520 */                                                   if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 5521 */                                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/*      */                                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                                   }
/* 5524 */                                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 5525 */                                                   out.write(10);
/* 5526 */                                                   out.write(9);
/*      */                                                   
/* 5528 */                                                   OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5529 */                                                   _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 5530 */                                                   _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f10);
/* 5531 */                                                   int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 5532 */                                                   if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */                                                     for (;;) {
/* 5534 */                                                       out.write(10);
/* 5535 */                                                       out.write(9);
/*      */                                                       
/* 5537 */                                                       disableLink = "<a href=\"javascript:void(0)\" onClick=\"changeWebsiteStatus('disable','" + pageContext.getAttribute("poolResourceID") + "','" + resID + "','applicationpool')\" ><img src=\"/images/icon_tickmark.gif\" align=\"absmiddle\" title=\"" + FormatUtil.getString("am.webclient.schedulereport.showwschedule.enable.text") + "\" id=\"1\" border=\"0\"></a>";
/*      */                                                       
/* 5539 */                                                       out.write(10);
/* 5540 */                                                       out.write(9);
/* 5541 */                                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 5542 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/*      */                                                   }
/* 5546 */                                                   if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 5547 */                                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/*      */                                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                                   }
/* 5550 */                                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 5551 */                                                   out.write(10);
/* 5552 */                                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 5553 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 5557 */                                               if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 5558 */                                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/*      */                                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 5561 */                                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 5562 */                                               out.write("\n<tr>\n\t<td class=\"");
/* 5563 */                                               if (_jspx_meth_c_005fout_005f34(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 5565 */                                               out.write(32);
/* 5566 */                                               out.print(websitestyleclass);
/* 5567 */                                               out.write(34);
/* 5568 */                                               out.write(62);
/* 5569 */                                               if (_jspx_meth_c_005fout_005f35(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 5571 */                                               out.write("</td>\n\t<td class=\"");
/* 5572 */                                               if (_jspx_meth_c_005fout_005f36(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 5574 */                                               out.write("\" align=\"center\"><a href=\"javascript:void(0)\"  onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5575 */                                               if (_jspx_meth_c_005fout_005f37(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 5577 */                                               out.write("&attributeid=2017')\">");
/* 5578 */                                               out.print(getSeverityImageForAvailability(poolAlertStatusProp.getProperty(pageContext.getAttribute("poolResourceID") + "#" + "2017")));
/* 5579 */                                               out.write("</a></td>\n\t<td class=\"");
/* 5580 */                                               if (_jspx_meth_c_005fout_005f38(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 5582 */                                               out.write("\" align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5583 */                                               if (_jspx_meth_c_005fout_005f39(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 5585 */                                               out.write("&attributeid=2016')\">");
/* 5586 */                                               out.print(getSeverityImageForHealth(poolAlertStatusProp.getProperty(pageContext.getAttribute("poolResourceID") + "#" + 2016)));
/* 5587 */                                               out.write("</a></td>\n\t<td class=\"");
/* 5588 */                                               if (_jspx_meth_c_005fout_005f40(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 5590 */                                               out.write("\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/showHistoryData.do?method=getData&resourceid=");
/* 5591 */                                               if (_jspx_meth_c_005fout_005f41(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 5593 */                                               out.write("&attributeid=2018&period=20&businessPeriod=oni&sid=')\">");
/* 5594 */                                               if (_jspx_meth_c_005fout_005f42(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 5596 */                                               out.write("</a></td>\n\t<td class=\"");
/* 5597 */                                               if (_jspx_meth_c_005fout_005f43(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 5599 */                                               out.write("\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/showHistoryData.do?method=getData&resourceid=");
/* 5600 */                                               if (_jspx_meth_c_005fout_005f44(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 5602 */                                               out.write("&attributeid=2019&period=20&businessPeriod=oni&sid=')\">");
/* 5603 */                                               if (_jspx_meth_c_005fout_005f45(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 5605 */                                               out.write("</a></td>\n\t<td class=\"");
/* 5606 */                                               if (_jspx_meth_c_005fout_005f46(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 5608 */                                               out.write("\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/showHistoryData.do?method=getData&resourceid=");
/* 5609 */                                               if (_jspx_meth_c_005fout_005f47(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 5611 */                                               out.write("&attributeid=2020&period=20&businessPeriod=oni&sid=')\">");
/* 5612 */                                               if (_jspx_meth_c_005fout_005f48(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 5614 */                                               out.write("</a></td>\n\t");
/*      */                                               
/* 5616 */                                               PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5617 */                                               _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 5618 */                                               _jspx_th_logic_005fpresent_005f10.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                                               
/* 5620 */                                               _jspx_th_logic_005fpresent_005f10.setRole("ADMIN,ENTERPRISEADMIN");
/* 5621 */                                               int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 5622 */                                               if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */                                                 for (;;) {
/* 5624 */                                                   out.write("\n    <td class=\"");
/* 5625 */                                                   if (_jspx_meth_c_005fout_005f49(_jspx_th_logic_005fpresent_005f10, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                                   }
/* 5627 */                                                   out.write("\">&nbsp; <a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5628 */                                                   if (_jspx_meth_c_005fout_005f50(_jspx_th_logic_005fpresent_005f10, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                                   }
/* 5630 */                                                   out.write("&attributeIDs=2016,2017,2018,2019,2020&attributeToSelect=2016&redirectto=");
/* 5631 */                                                   out.print(URLEncoder.encode(IISmonitorpage));
/* 5632 */                                                   out.write("'><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" border=\"0\"></a>\n            &nbsp;");
/* 5633 */                                                   out.print(disableLink);
/* 5634 */                                                   out.write("\n             </td>\n     ");
/* 5635 */                                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 5636 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 5640 */                                               if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 5641 */                                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/*      */                                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 5644 */                                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 5645 */                                               out.write("\n     </tr>\n     ");
/* 5646 */                                               int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 5647 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 5651 */                                           if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5659 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/*      */                                         }
/*      */                                         catch (Throwable _jspx_exception)
/*      */                                         {
/*      */                                           for (;;)
/*      */                                           {
/* 5655 */                                             int tmp24546_24545 = 0; int[] tmp24546_24543 = _jspx_push_body_count_c_005fforEach_005f1; int tmp24548_24547 = tmp24546_24543[tmp24546_24545];tmp24546_24543[tmp24546_24545] = (tmp24548_24547 - 1); if (tmp24548_24547 <= 0) break;
/* 5656 */                                             out = _jspx_page_context.popBody(); }
/* 5657 */                                           _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                                         } finally {
/* 5659 */                                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 5660 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                         }
/* 5662 */                                         out.write("\n</table>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\"><tr><td class=\"tableheading\" align=\"right\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"MM_openBrWindow('../jsp/RemoveSites.jsp?resourceid=");
/* 5663 */                                         out.print(resID);
/* 5664 */                                         out.write("&type=IIS-ApplicationPool','Personalize','width=800,height=350,screenX=200,screenY=250,scrollbars=yes')\">");
/* 5665 */                                         out.print(FormatUtil.getString("am.webclient.hostResource.servers.apppooldeleted"));
/* 5666 */                                         out.write("</a></tr></td></table>\n");
/* 5667 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 5668 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5672 */                                     if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 5673 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                                     }
/*      */                                     
/* 5676 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 5677 */                                     out.write(10);
/*      */                                     
/* 5679 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5680 */                                     _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 5681 */                                     _jspx_th_c_005fotherwise_005f10.setParent(_jspx_th_c_005fchoose_005f8);
/* 5682 */                                     int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 5683 */                                     if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */                                       for (;;) {
/* 5685 */                                         out.write(10);
/*      */                                         
/* 5687 */                                         hostresourceid = (String)request.getAttribute("hostresourceid");
/*      */                                         
/* 5689 */                                         out.write("\n<table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n\t             <tr>\n\t\t\t<td width=\"50%\" height=\"39\" class=\"bodytext\" align=\"center\">");
/* 5690 */                                         out.print(FormatUtil.getString("am.webclient.iis.pool.wmimode.alert.text", new String[] { "apm.manageengine.com/IIS-Server-monitor-does-not-list-Application-Pools.html" }));
/* 5691 */                                         out.write("</td>\n\t            </tr>\n\n  </table>\n  ");
/* 5692 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 5693 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5697 */                                     if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 5698 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10); return;
/*      */                                     }
/*      */                                     
/* 5701 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 5702 */                                     out.write(10);
/* 5703 */                                     out.write(32);
/* 5704 */                                     out.write(32);
/* 5705 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 5706 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 5710 */                                 if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 5711 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */                                 }
/*      */                                 
/* 5714 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 5715 */                                 out.write("\n</td>\n</tr>\n</table>\n");
/*      */                               }
/*      */                             }
/* 5718 */                             out.write("\n<br>\n");
/* 5719 */                             out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n");
/* 5720 */                             DialChartSupport dialGraph = null;
/* 5721 */                             dialGraph = (DialChartSupport)_jspx_page_context.getAttribute("dialGraph", 1);
/* 5722 */                             if (dialGraph == null) {
/* 5723 */                               dialGraph = new DialChartSupport();
/* 5724 */                               _jspx_page_context.setAttribute("dialGraph", dialGraph, 1);
/*      */                             }
/* 5726 */                             out.write(10);
/*      */                             
/*      */                             try
/*      */                             {
/* 5730 */                               String hostos = (String)systeminfo.get("HOSTOS");
/* 5731 */                               String hostname = (String)systeminfo.get("HOSTNAME");
/* 5732 */                               String hostid = (String)systeminfo.get("host_resid");
/* 5733 */                               boolean isConf = false;
/* 5734 */                               if ((systeminfo.get("isConf") != null) && (((String)systeminfo.get("isConf")).equals("true"))) {
/* 5735 */                                 isConf = true;
/*      */                               }
/* 5737 */                               com.adventnet.appmanager.db.RepairTables rt = new com.adventnet.appmanager.db.RepairTables();
/* 5738 */                               Properties property = new Properties();
/* 5739 */                               if ((hostos != null) && (!hostos.equalsIgnoreCase("unknown")) && (!hostos.equalsIgnoreCase("node")))
/*      */                               {
/* 5741 */                                 property = com.adventnet.appmanager.db.RepairTables.getValuesForHost(hostname, hostos);
/* 5742 */                                 if ((property != null) && (property.size() > 0))
/*      */                                 {
/* 5744 */                                   String cpuid = property.getProperty("cpuid");
/* 5745 */                                   String memid = property.getProperty("memid");
/* 5746 */                                   String diskid = property.getProperty("diskid");
/* 5747 */                                   String cpuvalue = property.getProperty("CPU Utilization");
/* 5748 */                                   String memvalue = property.getProperty("Memory Utilization");
/* 5749 */                                   String memurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + memid + "&period=0')";
/* 5750 */                                   String cpuurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + cpuid + "&period=0')";
/* 5751 */                                   String diskvalue = property.getProperty("Disk Utilization");
/* 5752 */                                   String diskurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + diskid + "&period=0')";
/*      */                                   
/* 5754 */                                   if (!isConf) {
/* 5755 */                                     out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"26\" class=\"tableheading\">");
/* 5756 */                                     out.print(FormatUtil.getString("am.webclient.serversnapshot.heading"));
/* 5757 */                                     out.write(45);
/* 5758 */                                     if (systeminfo.get("host_resid") != null) {
/* 5759 */                                       out.write("<a href=\"showresource.do?resourceid=");
/* 5760 */                                       out.print(hostid);
/* 5761 */                                       out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 5762 */                                       out.print(hostname);
/* 5763 */                                       out.write("</a>");
/* 5764 */                                     } else { out.println(hostname); }
/* 5765 */                                     out.write("</td>\t");
/* 5766 */                                     out.write("\n  </tr>\n</table>\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n    <td width=\"30%\" valign=\"top\">\n    ");
/* 5767 */                                     out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5768 */                                     out.write("\n    <table  cellspacing=\"0\" cellpadding=\"3\" border=\"0\" width=\"99%\">\n\n        <tr>\n         ");
/*      */                                     
/*      */ 
/* 5771 */                                     if (cpuvalue != null)
/*      */                                     {
/*      */ 
/* 5774 */                                       dialGraph.setValue(Long.parseLong(cpuvalue));
/* 5775 */                                       out.write("\n         <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5776 */                                       out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 5777 */                                       out.write(45);
/* 5778 */                                       out.print(cpuvalue);
/* 5779 */                                       out.write(" %'>\n\n");
/*      */                                       
/* 5781 */                                       DialChart _jspx_th_awolf_005fdialchart_005f0 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5782 */                                       _jspx_th_awolf_005fdialchart_005f0.setPageContext(_jspx_page_context);
/* 5783 */                                       _jspx_th_awolf_005fdialchart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                       
/* 5785 */                                       _jspx_th_awolf_005fdialchart_005f0.setDataSetProducer("dialGraph");
/*      */                                       
/* 5787 */                                       _jspx_th_awolf_005fdialchart_005f0.setWidth("150");
/*      */                                       
/* 5789 */                                       _jspx_th_awolf_005fdialchart_005f0.setHeight("148");
/*      */                                       
/* 5791 */                                       _jspx_th_awolf_005fdialchart_005f0.setLegend("false");
/*      */                                       
/* 5793 */                                       _jspx_th_awolf_005fdialchart_005f0.setXaxisLabel("");
/*      */                                       
/* 5795 */                                       _jspx_th_awolf_005fdialchart_005f0.setYaxisLabel("");
/*      */                                       
/* 5797 */                                       _jspx_th_awolf_005fdialchart_005f0.setDateFormat("HH:mm");
/*      */                                       
/* 5799 */                                       _jspx_th_awolf_005fdialchart_005f0.setLink(cpuurl);
/*      */                                       
/* 5801 */                                       _jspx_th_awolf_005fdialchart_005f0.setResourceId(hostid);
/*      */                                       
/* 5803 */                                       _jspx_th_awolf_005fdialchart_005f0.setAttributeId(cpuid);
/* 5804 */                                       int _jspx_eval_awolf_005fdialchart_005f0 = _jspx_th_awolf_005fdialchart_005f0.doStartTag();
/* 5805 */                                       if (_jspx_eval_awolf_005fdialchart_005f0 != 0) {
/* 5806 */                                         if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 5807 */                                           out = _jspx_page_context.pushBody();
/* 5808 */                                           _jspx_th_awolf_005fdialchart_005f0.setBodyContent((BodyContent)out);
/* 5809 */                                           _jspx_th_awolf_005fdialchart_005f0.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 5812 */                                           out.write(10);
/* 5813 */                                           int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f0.doAfterBody();
/* 5814 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 5817 */                                         if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 5818 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 5821 */                                       if (_jspx_th_awolf_005fdialchart_005f0.doEndTag() == 5) {
/* 5822 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0); return;
/*      */                                       }
/*      */                                       
/* 5825 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0);
/* 5826 */                                       out.write("\n         </td>\n            ");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 5830 */                                       out.write("\n\n\t<tr>\n\t\t<td><img src=\"../images/spacer.gif\" height=\"30\" ></td></tr>\n\n\n<tr>  \t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5831 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5832 */                                       out.write(32);
/* 5833 */                                       out.write(62);
/* 5834 */                                       out.write(10);
/* 5835 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5836 */                                       out.write("</td></tr>\n \t\t<!--img src='../images/nodata.gif'-->\n<tr>\t\t<td><img src=\"../images/spacer.gif\" height=\"30\"></td></tr>\n\n\n  ");
/*      */                                     }
/* 5838 */                                     out.write("\n      </tr>\n       <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 5839 */                                     if (cpuvalue != null)
/*      */                                     {
/* 5841 */                                       out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5842 */                                       out.print(hostid);
/* 5843 */                                       out.write("&attributeid=");
/* 5844 */                                       out.print(cpuid);
/* 5845 */                                       out.write("&period=-7')\" class='bodytextbold'>");
/* 5846 */                                       out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 5847 */                                       out.write(32);
/* 5848 */                                       out.write(45);
/* 5849 */                                       out.write(32);
/* 5850 */                                       out.print(cpuvalue);
/* 5851 */                                       out.write("</a> %\n");
/*      */                                     }
/* 5853 */                                     out.write("\n  </td>\n       </tr>\n       </table>");
/* 5854 */                                     out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5855 */                                     out.write("</td>\n      <td width=\"30%\"> ");
/* 5856 */                                     out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5857 */                                     out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n             <tr>\n");
/*      */                                     
/* 5859 */                                     if (memvalue != null)
/*      */                                     {
/*      */ 
/* 5862 */                                       dialGraph.setValue(Long.parseLong(memvalue));
/* 5863 */                                       out.write("\n            <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5864 */                                       out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5865 */                                       out.write(45);
/* 5866 */                                       out.print(memvalue);
/* 5867 */                                       out.write(" %' >\n\n");
/*      */                                       
/* 5869 */                                       DialChart _jspx_th_awolf_005fdialchart_005f1 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5870 */                                       _jspx_th_awolf_005fdialchart_005f1.setPageContext(_jspx_page_context);
/* 5871 */                                       _jspx_th_awolf_005fdialchart_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                       
/* 5873 */                                       _jspx_th_awolf_005fdialchart_005f1.setDataSetProducer("dialGraph");
/*      */                                       
/* 5875 */                                       _jspx_th_awolf_005fdialchart_005f1.setWidth("150");
/*      */                                       
/* 5877 */                                       _jspx_th_awolf_005fdialchart_005f1.setHeight("148");
/*      */                                       
/* 5879 */                                       _jspx_th_awolf_005fdialchart_005f1.setLegend("false");
/*      */                                       
/* 5881 */                                       _jspx_th_awolf_005fdialchart_005f1.setXaxisLabel("");
/*      */                                       
/* 5883 */                                       _jspx_th_awolf_005fdialchart_005f1.setYaxisLabel("");
/*      */                                       
/* 5885 */                                       _jspx_th_awolf_005fdialchart_005f1.setDateFormat("HH:mm");
/*      */                                       
/* 5887 */                                       _jspx_th_awolf_005fdialchart_005f1.setLink(memurl);
/*      */                                       
/* 5889 */                                       _jspx_th_awolf_005fdialchart_005f1.setResourceId(hostid);
/*      */                                       
/* 5891 */                                       _jspx_th_awolf_005fdialchart_005f1.setAttributeId(memid);
/* 5892 */                                       int _jspx_eval_awolf_005fdialchart_005f1 = _jspx_th_awolf_005fdialchart_005f1.doStartTag();
/* 5893 */                                       if (_jspx_eval_awolf_005fdialchart_005f1 != 0) {
/* 5894 */                                         if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 5895 */                                           out = _jspx_page_context.pushBody();
/* 5896 */                                           _jspx_th_awolf_005fdialchart_005f1.setBodyContent((BodyContent)out);
/* 5897 */                                           _jspx_th_awolf_005fdialchart_005f1.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 5900 */                                           out.write(32);
/* 5901 */                                           int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f1.doAfterBody();
/* 5902 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 5905 */                                         if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 5906 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 5909 */                                       if (_jspx_th_awolf_005fdialchart_005f1.doEndTag() == 5) {
/* 5910 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1); return;
/*      */                                       }
/*      */                                       
/* 5913 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1);
/* 5914 */                                       out.write(32);
/* 5915 */                                       out.write("\n            </td>\n            ");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 5919 */                                       out.write("\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n<tr>    <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5920 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5921 */                                       out.write(" >\n\n");
/* 5922 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5923 */                                       out.write("</td></tr>\n<!--img src='../images/nodata.gif'-->\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                     }
/* 5925 */                                     out.write("\n  </tr>\n   <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 5926 */                                     if (memvalue != null)
/*      */                                     {
/* 5928 */                                       out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5929 */                                       out.print(hostid);
/* 5930 */                                       out.write("&attributeid=");
/* 5931 */                                       out.print(memid);
/* 5932 */                                       out.write("&period=-7')\" class='bodytextbold'>");
/* 5933 */                                       out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5934 */                                       out.write(45);
/* 5935 */                                       out.print(memvalue);
/* 5936 */                                       out.write("</a> %\n  ");
/*      */                                     }
/* 5938 */                                     out.write("\n  </td>\n       </tr>\n    </table>");
/* 5939 */                                     out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5940 */                                     out.write("</td>\n      <td width=\"30%\">");
/* 5941 */                                     out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5942 */                                     out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n       <tr>\n  ");
/*      */                                     
/*      */ 
/* 5945 */                                     if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                     {
/*      */ 
/*      */ 
/* 5949 */                                       dialGraph.setValue(Long.parseLong(diskvalue));
/* 5950 */                                       out.write("\n\n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5951 */                                       out.print(FormatUtil.getString("am.reporttab.shortname.disk.text"));
/* 5952 */                                       out.write(45);
/* 5953 */                                       out.print(diskvalue);
/* 5954 */                                       out.write("%' >\n");
/*      */                                       
/* 5956 */                                       DialChart _jspx_th_awolf_005fdialchart_005f2 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5957 */                                       _jspx_th_awolf_005fdialchart_005f2.setPageContext(_jspx_page_context);
/* 5958 */                                       _jspx_th_awolf_005fdialchart_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                       
/* 5960 */                                       _jspx_th_awolf_005fdialchart_005f2.setDataSetProducer("dialGraph");
/*      */                                       
/* 5962 */                                       _jspx_th_awolf_005fdialchart_005f2.setWidth("150");
/*      */                                       
/* 5964 */                                       _jspx_th_awolf_005fdialchart_005f2.setHeight("148");
/*      */                                       
/* 5966 */                                       _jspx_th_awolf_005fdialchart_005f2.setLegend("false");
/*      */                                       
/* 5968 */                                       _jspx_th_awolf_005fdialchart_005f2.setXaxisLabel("");
/*      */                                       
/* 5970 */                                       _jspx_th_awolf_005fdialchart_005f2.setYaxisLabel("");
/*      */                                       
/* 5972 */                                       _jspx_th_awolf_005fdialchart_005f2.setDateFormat("HH:mm");
/*      */                                       
/* 5974 */                                       _jspx_th_awolf_005fdialchart_005f2.setLink(diskurl);
/*      */                                       
/* 5976 */                                       _jspx_th_awolf_005fdialchart_005f2.setResourceId(hostid);
/*      */                                       
/* 5978 */                                       _jspx_th_awolf_005fdialchart_005f2.setAttributeId(diskid);
/* 5979 */                                       int _jspx_eval_awolf_005fdialchart_005f2 = _jspx_th_awolf_005fdialchart_005f2.doStartTag();
/* 5980 */                                       if (_jspx_eval_awolf_005fdialchart_005f2 != 0) {
/* 5981 */                                         if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 5982 */                                           out = _jspx_page_context.pushBody();
/* 5983 */                                           _jspx_th_awolf_005fdialchart_005f2.setBodyContent((BodyContent)out);
/* 5984 */                                           _jspx_th_awolf_005fdialchart_005f2.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 5987 */                                           out.write(32);
/* 5988 */                                           int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f2.doAfterBody();
/* 5989 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 5992 */                                         if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 5993 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 5996 */                                       if (_jspx_th_awolf_005fdialchart_005f2.doEndTag() == 5) {
/* 5997 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2); return;
/*      */                                       }
/*      */                                       
/* 6000 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2);
/* 6001 */                                       out.write(32);
/* 6002 */                                       out.write(32);
/* 6003 */                                       out.write("\n    </td>\n            ");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 6007 */                                       out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 6008 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6009 */                                       out.write(32);
/* 6010 */                                       out.write(62);
/* 6011 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6012 */                                       out.write("\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                     }
/* 6014 */                                     out.write("\n  </tr>\n  <tr>\n\n\n\n  <td align='center'  class='bodytextbold'>\n");
/* 6015 */                                     if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                     {
/* 6017 */                                       out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 6018 */                                       out.print(hostid);
/* 6019 */                                       out.write("&attributeid=");
/* 6020 */                                       out.print(diskid);
/* 6021 */                                       out.write("&period=-7')\" class='bodytextbold'>");
/* 6022 */                                       out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 6023 */                                       out.write(45);
/* 6024 */                                       out.print(diskvalue);
/* 6025 */                                       out.write("</a> %\n     ");
/*      */                                     }
/* 6027 */                                     out.write("\n  </td>\n  </tr>\n</table>");
/* 6028 */                                     out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 6029 */                                     out.write("</td></tr></table>\n\n");
/*      */                                   } else {
/* 6031 */                                     out.write("\n\n\t<table cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-table\" width=\"100%\" onMouseOver=\"ShowPicture('configureIcons_ifany',1,'hostresource')\" onMouseOut=\"ShowPicture('configureIcons_ifany',0,'hostresource')\">\n\t<tr><td class=\"conf-mon-heading\" align=\"left\" colspan=\"3\">");
/* 6032 */                                     out.print(FormatUtil.getString("am.webclient.serversnapshot.allCaps.heading"));
/* 6033 */                                     out.write("&nbsp;-&nbsp;<a href=\"showresource.do?resourceid=");
/* 6034 */                                     out.print(systeminfo.get("host_resid"));
/* 6035 */                                     out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 6036 */                                     out.print(hostname);
/* 6037 */                                     out.write("</a></td></tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"><div id=\"configureIcons_ifany\"></div></td></tr>\n\t<tr>\n");
/* 6038 */                                     if (cpuvalue != null)
/*      */                                     {
/*      */ 
/* 6041 */                                       dialGraph.setValue(Long.parseLong(cpuvalue));
/* 6042 */                                       out.write("\n         <td align=\"center\" valign=\"center\">\n\t\t\t");
/*      */                                       
/* 6044 */                                       DialChart _jspx_th_awolf_005fdialchart_005f3 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 6045 */                                       _jspx_th_awolf_005fdialchart_005f3.setPageContext(_jspx_page_context);
/* 6046 */                                       _jspx_th_awolf_005fdialchart_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                       
/* 6048 */                                       _jspx_th_awolf_005fdialchart_005f3.setDataSetProducer("dialGraph");
/*      */                                       
/* 6050 */                                       _jspx_th_awolf_005fdialchart_005f3.setWidth("150");
/*      */                                       
/* 6052 */                                       _jspx_th_awolf_005fdialchart_005f3.setHeight("148");
/*      */                                       
/* 6054 */                                       _jspx_th_awolf_005fdialchart_005f3.setLegend("false");
/*      */                                       
/* 6056 */                                       _jspx_th_awolf_005fdialchart_005f3.setXaxisLabel("");
/*      */                                       
/* 6058 */                                       _jspx_th_awolf_005fdialchart_005f3.setYaxisLabel("");
/*      */                                       
/* 6060 */                                       _jspx_th_awolf_005fdialchart_005f3.setDateFormat("HH:mm");
/*      */                                       
/* 6062 */                                       _jspx_th_awolf_005fdialchart_005f3.setLink(cpuurl);
/*      */                                       
/* 6064 */                                       _jspx_th_awolf_005fdialchart_005f3.setResourceId(hostid);
/*      */                                       
/* 6066 */                                       _jspx_th_awolf_005fdialchart_005f3.setAttributeId(cpuid);
/* 6067 */                                       int _jspx_eval_awolf_005fdialchart_005f3 = _jspx_th_awolf_005fdialchart_005f3.doStartTag();
/* 6068 */                                       if (_jspx_th_awolf_005fdialchart_005f3.doEndTag() == 5) {
/* 6069 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3); return;
/*      */                                       }
/*      */                                       
/* 6072 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3);
/* 6073 */                                       out.write("\n         </td>\n     ");
/*      */                                     }
/*      */                                     else {
/* 6076 */                                       out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 6077 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6078 */                                       out.write(39);
/* 6079 */                                       out.write(32);
/* 6080 */                                       out.write(62);
/* 6081 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6082 */                                       out.write("\n \t\t</td>\n\t\t");
/*      */                                     }
/* 6084 */                                     if (memvalue != null) {
/* 6085 */                                       dialGraph.setValue(Long.parseLong(memvalue));
/* 6086 */                                       out.write("\n            <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                       
/* 6088 */                                       DialChart _jspx_th_awolf_005fdialchart_005f4 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 6089 */                                       _jspx_th_awolf_005fdialchart_005f4.setPageContext(_jspx_page_context);
/* 6090 */                                       _jspx_th_awolf_005fdialchart_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                       
/* 6092 */                                       _jspx_th_awolf_005fdialchart_005f4.setDataSetProducer("dialGraph");
/*      */                                       
/* 6094 */                                       _jspx_th_awolf_005fdialchart_005f4.setWidth("150");
/*      */                                       
/* 6096 */                                       _jspx_th_awolf_005fdialchart_005f4.setHeight("148");
/*      */                                       
/* 6098 */                                       _jspx_th_awolf_005fdialchart_005f4.setLegend("false");
/*      */                                       
/* 6100 */                                       _jspx_th_awolf_005fdialchart_005f4.setXaxisLabel("");
/*      */                                       
/* 6102 */                                       _jspx_th_awolf_005fdialchart_005f4.setYaxisLabel("");
/*      */                                       
/* 6104 */                                       _jspx_th_awolf_005fdialchart_005f4.setDateFormat("HH:mm");
/*      */                                       
/* 6106 */                                       _jspx_th_awolf_005fdialchart_005f4.setLink(memurl);
/*      */                                       
/* 6108 */                                       _jspx_th_awolf_005fdialchart_005f4.setResourceId(hostid);
/*      */                                       
/* 6110 */                                       _jspx_th_awolf_005fdialchart_005f4.setAttributeId(memid);
/* 6111 */                                       int _jspx_eval_awolf_005fdialchart_005f4 = _jspx_th_awolf_005fdialchart_005f4.doStartTag();
/* 6112 */                                       if (_jspx_th_awolf_005fdialchart_005f4.doEndTag() == 5) {
/* 6113 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4); return;
/*      */                                       }
/*      */                                       
/* 6116 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4);
/* 6117 */                                       out.write("\n            </td>\n         ");
/*      */                                     }
/*      */                                     else {
/* 6120 */                                       out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 6121 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6122 */                                       out.write(39);
/* 6123 */                                       out.write(32);
/* 6124 */                                       out.write(62);
/* 6125 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6126 */                                       out.write("\n \t\t</td>\n\t\t");
/*      */                                     }
/* 6128 */                                     if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 6129 */                                       dialGraph.setValue(Long.parseLong(diskvalue));
/* 6130 */                                       out.write("\n             <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                       
/* 6132 */                                       DialChart _jspx_th_awolf_005fdialchart_005f5 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 6133 */                                       _jspx_th_awolf_005fdialchart_005f5.setPageContext(_jspx_page_context);
/* 6134 */                                       _jspx_th_awolf_005fdialchart_005f5.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                       
/* 6136 */                                       _jspx_th_awolf_005fdialchart_005f5.setDataSetProducer("dialGraph");
/*      */                                       
/* 6138 */                                       _jspx_th_awolf_005fdialchart_005f5.setWidth("150");
/*      */                                       
/* 6140 */                                       _jspx_th_awolf_005fdialchart_005f5.setHeight("148");
/*      */                                       
/* 6142 */                                       _jspx_th_awolf_005fdialchart_005f5.setLegend("false");
/*      */                                       
/* 6144 */                                       _jspx_th_awolf_005fdialchart_005f5.setXaxisLabel("");
/*      */                                       
/* 6146 */                                       _jspx_th_awolf_005fdialchart_005f5.setYaxisLabel("");
/*      */                                       
/* 6148 */                                       _jspx_th_awolf_005fdialchart_005f5.setDateFormat("HH:mm");
/*      */                                       
/* 6150 */                                       _jspx_th_awolf_005fdialchart_005f5.setLink(diskurl);
/*      */                                       
/* 6152 */                                       _jspx_th_awolf_005fdialchart_005f5.setResourceId(hostid);
/*      */                                       
/* 6154 */                                       _jspx_th_awolf_005fdialchart_005f5.setAttributeId(diskid);
/* 6155 */                                       int _jspx_eval_awolf_005fdialchart_005f5 = _jspx_th_awolf_005fdialchart_005f5.doStartTag();
/* 6156 */                                       if (_jspx_th_awolf_005fdialchart_005f5.doEndTag() == 5) {
/* 6157 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5); return;
/*      */                                       }
/*      */                                       
/* 6160 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5);
/* 6161 */                                       out.write(32);
/* 6162 */                                       out.write("\n\t          </td>\n\t  ");
/*      */                                     }
/*      */                                     else {
/* 6165 */                                       out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 6166 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6167 */                                       out.write(39);
/* 6168 */                                       out.write(32);
/* 6169 */                                       out.write(62);
/* 6170 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6171 */                                       out.write("\n \t\t</td>\n\t\t");
/*      */                                     }
/* 6173 */                                     out.write("\n         \t</tr>\n\t<tr id=\"showLinks_hostresource\">\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 6174 */                                     out.print(hostid);
/* 6175 */                                     out.write("&attributeid=");
/* 6176 */                                     out.print(cpuid);
/* 6177 */                                     out.write("&period=-7')\" class='tooltip'>");
/* 6178 */                                     out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 6179 */                                     out.write(32);
/* 6180 */                                     out.write(45);
/* 6181 */                                     out.write(32);
/* 6182 */                                     if (cpuvalue != null) {
/* 6183 */                                       out.print(cpuvalue);
/*      */                                     }
/* 6185 */                                     out.write(" %</a>\n\t\t</span>\n\t\t</td>\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 6186 */                                     out.print(hostid);
/* 6187 */                                     out.write("&attributeid=");
/* 6188 */                                     out.print(memid);
/* 6189 */                                     out.write("&period=-7')\" class='tooltip'>");
/* 6190 */                                     out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 6191 */                                     out.write(45);
/* 6192 */                                     if (memvalue != null) {
/* 6193 */                                       out.print(memvalue);
/*      */                                     }
/* 6195 */                                     out.write(" %</a>\n  \t\t</span>\n\t\t</td>\n\t\t<td align=\"center\">\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 6196 */                                     out.print(hostid);
/* 6197 */                                     out.write("&attributeid=");
/* 6198 */                                     out.print(diskid);
/* 6199 */                                     out.write("&period=-7')\" class='tooltip'>");
/* 6200 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 6201 */                                     out.write(45);
/* 6202 */                                     if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 6203 */                                       out.print(diskvalue);
/*      */                                     }
/* 6205 */                                     out.write(" %</a>\n     \t</span>\n\t\t</td>\n\t</tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"></td></tr>\n</table>\n         \t\n");
/*      */                                   }
/* 6207 */                                   out.write(10);
/* 6208 */                                   out.write(10);
/*      */                                 }
/*      */                                 
/*      */                               }
/*      */                             }
/*      */                             catch (Exception e)
/*      */                             {
/* 6215 */                               e.printStackTrace();
/*      */                             }
/* 6217 */                             out.write(10);
/* 6218 */                             out.write(10);
/*      */                             
/* 6220 */                             IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6221 */                             _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 6222 */                             _jspx_th_c_005fif_005f10.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 6224 */                             _jspx_th_c_005fif_005f10.setTest("${DotNetAgentAvailable == true}");
/* 6225 */                             int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 6226 */                             if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                               for (;;) {
/* 6228 */                                 out.write("\n</div>\n");
/*      */                                 
/* 6230 */                                 String tablist = "overview, webtransaction, database, traces";
/*      */                                 
/* 6232 */                                 out.write(10);
/* 6233 */                                 out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 6234 */                                 out.write("\n<link href=\"/apminsight/style/apminsight-styles.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script src='/apminsight/js/includeJS.js' type='text/javascript'></script>\n<script>\n\tincludeAllScripts('');\n\tvar _moduleName = \"/apminsight\";//No I18N\n</script>\n<div id=\"alertsTabLists\" style=\"display: none;\">");
/* 6235 */                                 out.print(tablist);
/* 6236 */                                 out.write("</div>\n<div id=\"TabContent\" style=\"width:99%\">\n<div id=\"insight_OverviewTab\" style=\"display:none;padding:5px;\"></div>\n<div id=\"insight_transactionTab\" style=\"display:none;padding:1px;\"></div>\n<div id=\"insight_databaseTab\" style=\"display:none;padding:1px;\"></div>\n<div id=\"insight_tracesTab\" style=\"display:none;padding:1px;\"></div>\n<div class=\"load-msg\" style=\"padding:0em 0em;\"><img src=\"/apminsight/images/pageloading.gif\" /></div>\n</div>\n");
/* 6237 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 6238 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 6242 */                             if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 6243 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                             }
/*      */                             
/* 6246 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 6247 */                             out.write(10);
/* 6248 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 6249 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 6252 */                           if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 6253 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 6256 */                         if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 6257 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */                         }
/*      */                         
/* 6260 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 6261 */                         out.write(10);
/* 6262 */                         if (_jspx_meth_tiles_005fput_005f5(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 6264 */                         out.write(32);
/* 6265 */                         out.write(10);
/* 6266 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 6267 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 6271 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 6272 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 6275 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 6276 */                       out.write(10);
/* 6277 */                       if (_jspx_meth_c_005fif_005f11(_jspx_page_context))
/*      */                         return;
/* 6279 */                       out.write("\n</body>\n</html>\n");
/*      */                     }
/* 6281 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 6282 */         out = _jspx_out;
/* 6283 */         if ((out != null) && (out.getBufferSize() != 0))
/* 6284 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 6285 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 6288 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6294 */     PageContext pageContext = _jspx_page_context;
/* 6295 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6297 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 6298 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 6299 */     _jspx_th_c_005fcatch_005f0.setParent(null);
/*      */     
/* 6301 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 6302 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 6304 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 6305 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 6307 */           out.write("\n\t    ");
/* 6308 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 6309 */             return true;
/* 6310 */           out.write(10);
/* 6311 */           out.write(9);
/* 6312 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 6313 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 6317 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 6318 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 6321 */         int tmp184_183 = 0; int[] tmp184_181 = _jspx_push_body_count_c_005fcatch_005f0; int tmp186_185 = tmp184_181[tmp184_183];tmp184_181[tmp184_183] = (tmp186_185 - 1); if (tmp186_185 <= 0) break;
/* 6322 */         out = _jspx_page_context.popBody(); }
/* 6323 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 6325 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 6326 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 6328 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 6333 */     PageContext pageContext = _jspx_page_context;
/* 6334 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6336 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 6337 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 6338 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 6340 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 6342 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 6343 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 6344 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 6345 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 6346 */       return true;
/*      */     }
/* 6348 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 6349 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6354 */     PageContext pageContext = _jspx_page_context;
/* 6355 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6357 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6358 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 6359 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6361 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 6362 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 6363 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 6365 */         out.write(10);
/* 6366 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 6367 */           return true;
/* 6368 */         out.write(10);
/* 6369 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 6370 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6374 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 6375 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 6376 */       return true;
/*      */     }
/* 6378 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 6379 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6384 */     PageContext pageContext = _jspx_page_context;
/* 6385 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6387 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6388 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 6389 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 6391 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 6393 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 6394 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 6395 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 6396 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 6397 */       return true;
/*      */     }
/* 6399 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 6400 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6405 */     PageContext pageContext = _jspx_page_context;
/* 6406 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6408 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6409 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 6410 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6412 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 6413 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 6414 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 6416 */         out.write(10);
/* 6417 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 6418 */           return true;
/* 6419 */         out.write(10);
/* 6420 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 6421 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6425 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 6426 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 6427 */       return true;
/*      */     }
/* 6429 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 6430 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6435 */     PageContext pageContext = _jspx_page_context;
/* 6436 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6438 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6439 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 6440 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 6442 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 6444 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 6445 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 6446 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 6447 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 6448 */       return true;
/*      */     }
/* 6450 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 6451 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6456 */     PageContext pageContext = _jspx_page_context;
/* 6457 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6459 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6460 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 6461 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6463 */     _jspx_th_tiles_005fput_005f3.setName("ServerLeftArea");
/*      */     
/* 6465 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/AvailabilityPerformanceLinks.jsp");
/* 6466 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 6467 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 6468 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 6469 */       return true;
/*      */     }
/* 6471 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 6472 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6477 */     PageContext pageContext = _jspx_page_context;
/* 6478 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6480 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6481 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 6482 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6484 */     _jspx_th_c_005fout_005f0.setValue("${insightResourceID}");
/* 6485 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 6486 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 6487 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 6488 */       return true;
/*      */     }
/* 6490 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 6491 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6496 */     PageContext pageContext = _jspx_page_context;
/* 6497 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6499 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6500 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 6501 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6503 */     _jspx_th_c_005fout_005f1.setValue("${insightApplicationID}");
/* 6504 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 6505 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 6506 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6507 */       return true;
/*      */     }
/* 6509 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6510 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6515 */     PageContext pageContext = _jspx_page_context;
/* 6516 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6518 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6519 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 6520 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6522 */     _jspx_th_c_005fout_005f2.setValue("${insightResourceID}");
/* 6523 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 6524 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 6525 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6526 */       return true;
/*      */     }
/* 6528 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6529 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6534 */     PageContext pageContext = _jspx_page_context;
/* 6535 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6537 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6538 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 6539 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6541 */     _jspx_th_c_005fout_005f3.setValue("${insightResourceID}");
/* 6542 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 6543 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 6544 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6545 */       return true;
/*      */     }
/* 6547 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6548 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6553 */     PageContext pageContext = _jspx_page_context;
/* 6554 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6556 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6557 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 6558 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6560 */     _jspx_th_c_005fout_005f4.setValue("${insightApplicationID}");
/* 6561 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 6562 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 6563 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6564 */       return true;
/*      */     }
/* 6566 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6567 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6572 */     PageContext pageContext = _jspx_page_context;
/* 6573 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6575 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6576 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 6577 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6579 */     _jspx_th_c_005fout_005f5.setValue("${insightResourceID}");
/* 6580 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 6581 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 6582 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6583 */       return true;
/*      */     }
/* 6585 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6586 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6591 */     PageContext pageContext = _jspx_page_context;
/* 6592 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6594 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6595 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 6596 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6598 */     _jspx_th_c_005fout_005f6.setValue("${insightResourceID}");
/* 6599 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 6600 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 6601 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6602 */       return true;
/*      */     }
/* 6604 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6605 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6610 */     PageContext pageContext = _jspx_page_context;
/* 6611 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6613 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6614 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 6615 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6617 */     _jspx_th_c_005fout_005f7.setValue("${insightApplicationID}");
/* 6618 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 6619 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 6620 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 6621 */       return true;
/*      */     }
/* 6623 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 6624 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6629 */     PageContext pageContext = _jspx_page_context;
/* 6630 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6632 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6633 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 6634 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6636 */     _jspx_th_c_005fout_005f8.setValue("${insightResourceID}");
/* 6637 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 6638 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 6639 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6640 */       return true;
/*      */     }
/* 6642 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6643 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6648 */     PageContext pageContext = _jspx_page_context;
/* 6649 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6651 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6652 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 6653 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6655 */     _jspx_th_c_005fout_005f9.setValue("${insightResourceID}");
/* 6656 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 6657 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 6658 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6659 */       return true;
/*      */     }
/* 6661 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6662 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6667 */     PageContext pageContext = _jspx_page_context;
/* 6668 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6670 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6671 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 6672 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6674 */     _jspx_th_c_005fout_005f10.setValue("${insightApplicationID}");
/* 6675 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 6676 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 6677 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6678 */       return true;
/*      */     }
/* 6680 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6681 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6686 */     PageContext pageContext = _jspx_page_context;
/* 6687 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6689 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6690 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 6691 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6693 */     _jspx_th_c_005fout_005f11.setValue("${insightResourceID}");
/* 6694 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 6695 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 6696 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6697 */       return true;
/*      */     }
/* 6699 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6700 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6705 */     PageContext pageContext = _jspx_page_context;
/* 6706 */     JspWriter out = _jspx_page_context.getOut();
/* 6707 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 6708 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 6710 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 6711 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 6712 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 6713 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 6714 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 6716 */         out.write(10);
/* 6717 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 6718 */           return true;
/* 6719 */         out.write(10);
/* 6720 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 6721 */           return true;
/* 6722 */         out.write(10);
/* 6723 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 6724 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6728 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 6729 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 6730 */       return true;
/*      */     }
/* 6732 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 6733 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6738 */     PageContext pageContext = _jspx_page_context;
/* 6739 */     JspWriter out = _jspx_page_context.getOut();
/* 6740 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 6741 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 6743 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 6744 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 6745 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 6747 */     _jspx_th_c_005fwhen_005f0.setTest("${param.type=='WEB-server' || param.type=='MAIL-server' || param.type=='IIS-server' || param.type=='PHP' ||\nparam.type=='TELNET'}");
/* 6748 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 6749 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 6751 */         out.write(10);
/* 6752 */         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/ConfigureMailNWeb.jsp", out, false);
/* 6753 */         out.write(10);
/* 6754 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 6755 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6759 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 6760 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 6761 */       return true;
/*      */     }
/* 6763 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 6764 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6769 */     PageContext pageContext = _jspx_page_context;
/* 6770 */     JspWriter out = _jspx_page_context.getOut();
/* 6771 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 6772 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 6774 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 6775 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 6776 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 6777 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 6778 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 6780 */         out.write(10);
/* 6781 */         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/configure_resource.jsp", out, false);
/* 6782 */         out.write(10);
/* 6783 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 6784 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6788 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 6789 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 6790 */       return true;
/*      */     }
/* 6792 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 6793 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6798 */     PageContext pageContext = _jspx_page_context;
/* 6799 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6801 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6802 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 6803 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6805 */     _jspx_th_c_005fout_005f13.setValue("${param.resourceid}");
/* 6806 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 6807 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 6808 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6809 */       return true;
/*      */     }
/* 6811 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6812 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6817 */     PageContext pageContext = _jspx_page_context;
/* 6818 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6820 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6821 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 6822 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6824 */     _jspx_th_c_005fout_005f14.setValue("${param.resourcename}");
/* 6825 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 6826 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 6827 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6828 */       return true;
/*      */     }
/* 6830 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6831 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6836 */     PageContext pageContext = _jspx_page_context;
/* 6837 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6839 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6840 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 6841 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6843 */     _jspx_th_c_005fout_005f15.setValue("${param.resourceid}");
/* 6844 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 6845 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 6846 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6847 */       return true;
/*      */     }
/* 6849 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6850 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6855 */     PageContext pageContext = _jspx_page_context;
/* 6856 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6858 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6859 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 6860 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6862 */     _jspx_th_c_005fout_005f16.setValue("${param.resourcename}");
/* 6863 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 6864 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 6865 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6866 */       return true;
/*      */     }
/* 6868 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6869 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fpiechart_005f0(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6874 */     PageContext pageContext = _jspx_page_context;
/* 6875 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6877 */     AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 6878 */     _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 6879 */     _jspx_th_awolf_005fpiechart_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6881 */     _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */     
/* 6883 */     _jspx_th_awolf_005fpiechart_005f0.setWidth("300");
/*      */     
/* 6885 */     _jspx_th_awolf_005fpiechart_005f0.setHeight("180");
/*      */     
/* 6887 */     _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */     
/* 6889 */     _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */     
/* 6891 */     _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */     
/* 6893 */     _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 6894 */     int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 6895 */     if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 6896 */       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 6897 */         out = _jspx_page_context.pushBody();
/* 6898 */         _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 6899 */         _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6902 */         out.write("\n            ");
/* 6903 */         if (_jspx_meth_awolf_005fmap_005f0(_jspx_th_awolf_005fpiechart_005f0, _jspx_page_context))
/* 6904 */           return true;
/* 6905 */         out.write(32);
/* 6906 */         int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 6907 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6910 */       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 6911 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6914 */     if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 6915 */       this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 6916 */       return true;
/*      */     }
/* 6918 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 6919 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f0(JspTag _jspx_th_awolf_005fpiechart_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6924 */     PageContext pageContext = _jspx_page_context;
/* 6925 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6927 */     Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 6928 */     _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 6929 */     _jspx_th_awolf_005fmap_005f0.setParent((Tag)_jspx_th_awolf_005fpiechart_005f0);
/*      */     
/* 6931 */     _jspx_th_awolf_005fmap_005f0.setId("color");
/* 6932 */     int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 6933 */     if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 6934 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 6935 */         out = _jspx_page_context.pushBody();
/* 6936 */         _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 6937 */         _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6940 */         out.write(32);
/* 6941 */         if (_jspx_meth_awolf_005fparam_005f0(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 6942 */           return true;
/* 6943 */         out.write(32);
/* 6944 */         if (_jspx_meth_awolf_005fparam_005f1(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 6945 */           return true;
/* 6946 */         out.write("\n            ");
/* 6947 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 6948 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6951 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 6952 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6955 */     if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 6956 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 6957 */       return true;
/*      */     }
/* 6959 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 6960 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f0(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6965 */     PageContext pageContext = _jspx_page_context;
/* 6966 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6968 */     AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 6969 */     _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 6970 */     _jspx_th_awolf_005fparam_005f0.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 6972 */     _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */     
/* 6974 */     _jspx_th_awolf_005fparam_005f0.setValue("#29FF29");
/* 6975 */     int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 6976 */     if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 6977 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 6978 */       return true;
/*      */     }
/* 6980 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 6981 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f1(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6986 */     PageContext pageContext = _jspx_page_context;
/* 6987 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6989 */     AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 6990 */     _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 6991 */     _jspx_th_awolf_005fparam_005f1.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 6993 */     _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */     
/* 6995 */     _jspx_th_awolf_005fparam_005f1.setValue("#FF0000");
/* 6996 */     int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 6997 */     if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 6998 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 6999 */       return true;
/*      */     }
/* 7001 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 7002 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7007 */     PageContext pageContext = _jspx_page_context;
/* 7008 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7010 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7011 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 7012 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7014 */     _jspx_th_c_005fout_005f17.setValue("${param.resourceid}");
/* 7015 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 7016 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 7017 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 7018 */       return true;
/*      */     }
/* 7020 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 7021 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7026 */     PageContext pageContext = _jspx_page_context;
/* 7027 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7029 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7030 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 7031 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7033 */     _jspx_th_c_005fout_005f18.setValue("${param.resourceid}");
/* 7034 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 7035 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 7036 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 7037 */       return true;
/*      */     }
/* 7039 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 7040 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7045 */     PageContext pageContext = _jspx_page_context;
/* 7046 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7048 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7049 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 7050 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7051 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 7052 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 7053 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 7054 */         out = _jspx_page_context.pushBody();
/* 7055 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 7056 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7059 */         out.write("table.heading.attribute");
/* 7060 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 7061 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7064 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 7065 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7068 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 7069 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 7070 */       return true;
/*      */     }
/* 7072 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 7073 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7078 */     PageContext pageContext = _jspx_page_context;
/* 7079 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7081 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7082 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 7083 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7084 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 7085 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 7086 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 7087 */         out = _jspx_page_context.pushBody();
/* 7088 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 7089 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7092 */         out.write("table.heading.value");
/* 7093 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 7094 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7097 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 7098 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7101 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 7102 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 7103 */       return true;
/*      */     }
/* 7105 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 7106 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7111 */     PageContext pageContext = _jspx_page_context;
/* 7112 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7114 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7115 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 7116 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7117 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 7118 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 7119 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 7120 */         out = _jspx_page_context.pushBody();
/* 7121 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 7122 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7125 */         out.write("table.heading.status");
/* 7126 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 7127 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7130 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 7131 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7134 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 7135 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 7136 */       return true;
/*      */     }
/* 7138 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 7139 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7144 */     PageContext pageContext = _jspx_page_context;
/* 7145 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7147 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7148 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 7149 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7151 */     _jspx_th_c_005fout_005f19.setValue("${param.resourceid}");
/* 7152 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 7153 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 7154 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 7155 */       return true;
/*      */     }
/* 7157 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 7158 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7163 */     PageContext pageContext = _jspx_page_context;
/* 7164 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7166 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7167 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 7168 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7170 */     _jspx_th_c_005fout_005f20.setValue("${param.resourceid}");
/* 7171 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 7172 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 7173 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 7174 */       return true;
/*      */     }
/* 7176 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 7177 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7182 */     PageContext pageContext = _jspx_page_context;
/* 7183 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7185 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7186 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 7187 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7188 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 7189 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 7190 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 7191 */         out = _jspx_page_context.pushBody();
/* 7192 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 7193 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7196 */         out.write("table.heading.attribute");
/* 7197 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 7198 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7201 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 7202 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7205 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 7206 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 7207 */       return true;
/*      */     }
/* 7209 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 7210 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7215 */     PageContext pageContext = _jspx_page_context;
/* 7216 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7218 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7219 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 7220 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7221 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 7222 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 7223 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 7224 */         out = _jspx_page_context.pushBody();
/* 7225 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 7226 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7229 */         out.write("table.heading.value");
/* 7230 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 7231 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7234 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 7235 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7238 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 7239 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 7240 */       return true;
/*      */     }
/* 7242 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 7243 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7248 */     PageContext pageContext = _jspx_page_context;
/* 7249 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7251 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7252 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 7253 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7254 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 7255 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 7256 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 7257 */         out = _jspx_page_context.pushBody();
/* 7258 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 7259 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7262 */         out.write("table.heading.status");
/* 7263 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 7264 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7267 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 7268 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7271 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 7272 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 7273 */       return true;
/*      */     }
/* 7275 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 7276 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7281 */     PageContext pageContext = _jspx_page_context;
/* 7282 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7284 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7285 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 7286 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7287 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 7288 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 7289 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 7290 */         out = _jspx_page_context.pushBody();
/* 7291 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 7292 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7295 */         out.write("table.heading.attribute");
/* 7296 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 7297 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7300 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 7301 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7304 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 7305 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 7306 */       return true;
/*      */     }
/* 7308 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 7309 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7314 */     PageContext pageContext = _jspx_page_context;
/* 7315 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7317 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7318 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 7319 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7320 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 7321 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 7322 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 7323 */         out = _jspx_page_context.pushBody();
/* 7324 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 7325 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7328 */         out.write("table.heading.value");
/* 7329 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 7330 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7333 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 7334 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7337 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 7338 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 7339 */       return true;
/*      */     }
/* 7341 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 7342 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7347 */     PageContext pageContext = _jspx_page_context;
/* 7348 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7350 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7351 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 7352 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7353 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 7354 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 7355 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 7356 */         out = _jspx_page_context.pushBody();
/* 7357 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 7358 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7361 */         out.write("table.heading.status");
/* 7362 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 7363 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7366 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 7367 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7370 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 7371 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 7372 */       return true;
/*      */     }
/* 7374 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 7375 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7380 */     PageContext pageContext = _jspx_page_context;
/* 7381 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7383 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7384 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 7385 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7386 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 7387 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 7388 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 7389 */         out = _jspx_page_context.pushBody();
/* 7390 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 7391 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7394 */         out.write("table.heading.attribute");
/* 7395 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 7396 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7399 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 7400 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7403 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 7404 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 7405 */       return true;
/*      */     }
/* 7407 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 7408 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7413 */     PageContext pageContext = _jspx_page_context;
/* 7414 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7416 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7417 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 7418 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7419 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 7420 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 7421 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 7422 */         out = _jspx_page_context.pushBody();
/* 7423 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 7424 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7427 */         out.write("table.heading.value");
/* 7428 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 7429 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7432 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 7433 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7436 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 7437 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 7438 */       return true;
/*      */     }
/* 7440 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 7441 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7446 */     PageContext pageContext = _jspx_page_context;
/* 7447 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7449 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7450 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 7451 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 7452 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 7453 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 7454 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 7455 */         out = _jspx_page_context.pushBody();
/* 7456 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 7457 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7460 */         out.write("table.heading.status");
/* 7461 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 7462 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7465 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 7466 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7469 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 7470 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 7471 */       return true;
/*      */     }
/* 7473 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 7474 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7479 */     PageContext pageContext = _jspx_page_context;
/* 7480 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7482 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7483 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 7484 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7486 */     _jspx_th_c_005fout_005f21.setValue("${fullvalue}");
/* 7487 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 7488 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 7489 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 7490 */       return true;
/*      */     }
/* 7492 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 7493 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7498 */     PageContext pageContext = _jspx_page_context;
/* 7499 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7501 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7502 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 7503 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7505 */     _jspx_th_c_005fout_005f22.setValue("${fullvalue}");
/* 7506 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 7507 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 7508 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 7509 */       return true;
/*      */     }
/* 7511 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 7512 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7517 */     PageContext pageContext = _jspx_page_context;
/* 7518 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7520 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7521 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 7522 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7524 */     _jspx_th_c_005fif_005f5.setTest("${not empty param.haid}");
/* 7525 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 7526 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 7528 */         out.write(10);
/* 7529 */         out.write(9);
/* 7530 */         out.write(9);
/* 7531 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 7532 */           return true;
/* 7533 */         out.write(10);
/* 7534 */         out.write(9);
/* 7535 */         out.write(9);
/* 7536 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 7537 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7541 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 7542 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 7543 */       return true;
/*      */     }
/* 7545 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 7546 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7551 */     PageContext pageContext = _jspx_page_context;
/* 7552 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7554 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 7555 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 7556 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 7558 */     _jspx_th_c_005fset_005f0.setVar("myfield_paramresid");
/*      */     
/* 7560 */     _jspx_th_c_005fset_005f0.setScope("page");
/* 7561 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 7562 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 7563 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 7564 */         out = _jspx_page_context.pushBody();
/* 7565 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 7566 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7569 */         if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 7570 */           return true;
/* 7571 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 7572 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7575 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 7576 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7579 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 7580 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 7581 */       return true;
/*      */     }
/* 7583 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 7584 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7589 */     PageContext pageContext = _jspx_page_context;
/* 7590 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7592 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7593 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 7594 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 7596 */     _jspx_th_c_005fout_005f23.setValue("${param.haid}");
/* 7597 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 7598 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 7599 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 7600 */       return true;
/*      */     }
/* 7602 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 7603 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7608 */     PageContext pageContext = _jspx_page_context;
/* 7609 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7611 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7612 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 7613 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7615 */     _jspx_th_c_005fif_005f6.setTest("${not empty param.resourceid}");
/* 7616 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 7617 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 7619 */         out.write(10);
/* 7620 */         out.write(9);
/* 7621 */         out.write(9);
/* 7622 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 7623 */           return true;
/* 7624 */         out.write(10);
/* 7625 */         out.write(9);
/* 7626 */         out.write(9);
/* 7627 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 7628 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7632 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 7633 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 7634 */       return true;
/*      */     }
/* 7636 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 7637 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7642 */     PageContext pageContext = _jspx_page_context;
/* 7643 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7645 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 7646 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 7647 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 7649 */     _jspx_th_c_005fset_005f1.setVar("myfield_paramresid");
/*      */     
/* 7651 */     _jspx_th_c_005fset_005f1.setScope("page");
/* 7652 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 7653 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 7654 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 7655 */         out = _jspx_page_context.pushBody();
/* 7656 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 7657 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7660 */         if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fset_005f1, _jspx_page_context))
/* 7661 */           return true;
/* 7662 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 7663 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7666 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 7667 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7670 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 7671 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 7672 */       return true;
/*      */     }
/* 7674 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 7675 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7680 */     PageContext pageContext = _jspx_page_context;
/* 7681 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7683 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7684 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 7685 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 7687 */     _jspx_th_c_005fout_005f24.setValue("${param.resourceid}");
/* 7688 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 7689 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 7690 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 7691 */       return true;
/*      */     }
/* 7693 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 7694 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7699 */     PageContext pageContext = _jspx_page_context;
/* 7700 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7702 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7703 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 7704 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7706 */     _jspx_th_c_005fout_005f25.setValue("${myfield_paramresid}");
/* 7707 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 7708 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 7709 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 7710 */       return true;
/*      */     }
/* 7712 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 7713 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f5(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7718 */     PageContext pageContext = _jspx_page_context;
/* 7719 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7721 */     PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 7722 */     _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 7723 */     _jspx_th_logic_005fpresent_005f5.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 7725 */     _jspx_th_logic_005fpresent_005f5.setRole("DEMO");
/* 7726 */     int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 7727 */     if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */       for (;;) {
/* 7729 */         out.write("\n\t\t<a href=\"javascript:alertUser()\"><img src=\"/images/icon_executeaction.gif\"  border=\"0\"></a></td>\n\t\t");
/* 7730 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 7731 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7735 */     if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 7736 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 7737 */       return true;
/*      */     }
/* 7739 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 7740 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f7(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7745 */     PageContext pageContext = _jspx_page_context;
/* 7746 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7748 */     PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 7749 */     _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 7750 */     _jspx_th_logic_005fpresent_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 7752 */     _jspx_th_logic_005fpresent_005f7.setRole("DEMO");
/* 7753 */     int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 7754 */     if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */       for (;;) {
/* 7756 */         out.write(" \n\talertUser();\n\treturn;\n\t");
/* 7757 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 7758 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7762 */     if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 7763 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 7764 */       return true;
/*      */     }
/* 7766 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 7767 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7772 */     PageContext pageContext = _jspx_page_context;
/* 7773 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7775 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 7776 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 7777 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/* 7778 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 7779 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 7781 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 7782 */           return true;
/* 7783 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 7784 */           return true;
/* 7785 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 7786 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7790 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 7791 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 7792 */       return true;
/*      */     }
/* 7794 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 7795 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7800 */     PageContext pageContext = _jspx_page_context;
/* 7801 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7803 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 7804 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 7805 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 7807 */     _jspx_th_c_005fwhen_005f2.setTest("${!empty list1}");
/* 7808 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 7809 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 7811 */         out.write("<input type=\"checkbox\" name=\"headercheckbox\"  onClick=\"javascript:fnSelectAll(this,'checkbox');\">");
/* 7812 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 7813 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7817 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 7818 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 7819 */       return true;
/*      */     }
/* 7821 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 7822 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7827 */     PageContext pageContext = _jspx_page_context;
/* 7828 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7830 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 7831 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 7832 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 7833 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 7834 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 7836 */         out.write("&nbsp;");
/* 7837 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 7838 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7842 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 7843 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 7844 */       return true;
/*      */     }
/* 7846 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 7847 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7852 */     PageContext pageContext = _jspx_page_context;
/* 7853 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7855 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7856 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 7857 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 7859 */     _jspx_th_c_005fout_005f26.setValue("${props.ID}");
/* 7860 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 7861 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 7862 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 7863 */       return true;
/*      */     }
/* 7865 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 7866 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7871 */     PageContext pageContext = _jspx_page_context;
/* 7872 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7874 */     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 7875 */     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 7876 */     _jspx_th_c_005fchoose_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/* 7877 */     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 7878 */     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */       for (;;) {
/* 7880 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 7881 */           return true;
/* 7882 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 7883 */           return true;
/* 7884 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 7885 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7889 */     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 7890 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 7891 */       return true;
/*      */     }
/* 7893 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 7894 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7899 */     PageContext pageContext = _jspx_page_context;
/* 7900 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7902 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 7903 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 7904 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 7906 */     _jspx_th_c_005fwhen_005f3.setTest("${!empty props.NAME}");
/* 7907 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 7908 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 7910 */         if (_jspx_meth_c_005fout_005f27(_jspx_th_c_005fwhen_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 7911 */           return true;
/* 7912 */         out.write("</a>");
/* 7913 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 7914 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7918 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 7919 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 7920 */       return true;
/*      */     }
/* 7922 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 7923 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7928 */     PageContext pageContext = _jspx_page_context;
/* 7929 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7931 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7932 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 7933 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 7935 */     _jspx_th_c_005fout_005f27.setValue("${props.NAME}");
/* 7936 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 7937 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 7938 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 7939 */       return true;
/*      */     }
/* 7941 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 7942 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7947 */     PageContext pageContext = _jspx_page_context;
/* 7948 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7950 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 7951 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 7952 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 7953 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 7954 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 7956 */         out.write(45);
/* 7957 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 7958 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7962 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 7963 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 7964 */       return true;
/*      */     }
/* 7966 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 7967 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7972 */     PageContext pageContext = _jspx_page_context;
/* 7973 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7975 */     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 7976 */     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 7977 */     _jspx_th_c_005fchoose_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/* 7978 */     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 7979 */     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */       for (;;) {
/* 7981 */         if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 7982 */           return true;
/* 7983 */         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 7984 */           return true;
/* 7985 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 7986 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7990 */     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 7991 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 7992 */       return true;
/*      */     }
/* 7994 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 7995 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f4(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 8000 */     PageContext pageContext = _jspx_page_context;
/* 8001 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8003 */     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 8004 */     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 8005 */     _jspx_th_c_005fwhen_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 8007 */     _jspx_th_c_005fwhen_005f4.setTest("${!empty props.DISPLAYNAME}");
/* 8008 */     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 8009 */     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */       for (;;) {
/* 8011 */         if (_jspx_meth_c_005fout_005f28(_jspx_th_c_005fwhen_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 8012 */           return true;
/* 8013 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 8014 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8018 */     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 8019 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 8020 */       return true;
/*      */     }
/* 8022 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 8023 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 8028 */     PageContext pageContext = _jspx_page_context;
/* 8029 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8031 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8032 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 8033 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 8035 */     _jspx_th_c_005fout_005f28.setValue("${props.DISPLAYNAME}");
/* 8036 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 8037 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 8038 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 8039 */       return true;
/*      */     }
/* 8041 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 8042 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 8047 */     PageContext pageContext = _jspx_page_context;
/* 8048 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8050 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 8051 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 8052 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/* 8053 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 8054 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 8056 */         out.write(45);
/* 8057 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 8058 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8062 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 8063 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 8064 */       return true;
/*      */     }
/* 8066 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 8067 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f5(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 8072 */     PageContext pageContext = _jspx_page_context;
/* 8073 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8075 */     ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 8076 */     _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 8077 */     _jspx_th_c_005fchoose_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/* 8078 */     int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 8079 */     if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */       for (;;) {
/* 8081 */         if (_jspx_meth_c_005fwhen_005f5(_jspx_th_c_005fchoose_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 8082 */           return true;
/* 8083 */         if (_jspx_meth_c_005fotherwise_005f4(_jspx_th_c_005fchoose_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 8084 */           return true;
/* 8085 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 8086 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8090 */     if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 8091 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 8092 */       return true;
/*      */     }
/* 8094 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 8095 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f5(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 8100 */     PageContext pageContext = _jspx_page_context;
/* 8101 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8103 */     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 8104 */     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 8105 */     _jspx_th_c_005fwhen_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/*      */     
/* 8107 */     _jspx_th_c_005fwhen_005f5.setTest("${!empty props.OBJECTNAME}");
/* 8108 */     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 8109 */     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */       for (;;) {
/* 8111 */         if (_jspx_meth_c_005fout_005f29(_jspx_th_c_005fwhen_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 8112 */           return true;
/* 8113 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 8114 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8118 */     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 8119 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 8120 */       return true;
/*      */     }
/* 8122 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 8123 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 8128 */     PageContext pageContext = _jspx_page_context;
/* 8129 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8131 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8132 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 8133 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 8135 */     _jspx_th_c_005fout_005f29.setValue("${props.OBJECTNAME}");
/* 8136 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 8137 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 8138 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 8139 */       return true;
/*      */     }
/* 8141 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 8142 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f4(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 8147 */     PageContext pageContext = _jspx_page_context;
/* 8148 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8150 */     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 8151 */     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 8152 */     _jspx_th_c_005fotherwise_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/* 8153 */     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 8154 */     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */       for (;;) {
/* 8156 */         out.write(45);
/* 8157 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 8158 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8162 */     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 8163 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 8164 */       return true;
/*      */     }
/* 8166 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 8167 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f6(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 8172 */     PageContext pageContext = _jspx_page_context;
/* 8173 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8175 */     ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 8176 */     _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 8177 */     _jspx_th_c_005fchoose_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/* 8178 */     int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 8179 */     if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */       for (;;) {
/* 8181 */         if (_jspx_meth_c_005fwhen_005f6(_jspx_th_c_005fchoose_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 8182 */           return true;
/* 8183 */         if (_jspx_meth_c_005fotherwise_005f5(_jspx_th_c_005fchoose_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 8184 */           return true;
/* 8185 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 8186 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8190 */     if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 8191 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 8192 */       return true;
/*      */     }
/* 8194 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 8195 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f6(JspTag _jspx_th_c_005fchoose_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 8200 */     PageContext pageContext = _jspx_page_context;
/* 8201 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8203 */     WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 8204 */     _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 8205 */     _jspx_th_c_005fwhen_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f6);
/*      */     
/* 8207 */     _jspx_th_c_005fwhen_005f6.setTest("${!empty props.SEVERITY}");
/* 8208 */     int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 8209 */     if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */       for (;;) {
/* 8211 */         if (_jspx_meth_c_005fout_005f30(_jspx_th_c_005fwhen_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 8212 */           return true;
/* 8213 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 8214 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8218 */     if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 8219 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 8220 */       return true;
/*      */     }
/* 8222 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 8223 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 8228 */     PageContext pageContext = _jspx_page_context;
/* 8229 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8231 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8232 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 8233 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/*      */     
/* 8235 */     _jspx_th_c_005fout_005f30.setValue("${props.SEVERITY}");
/* 8236 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 8237 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 8238 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 8239 */       return true;
/*      */     }
/* 8241 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 8242 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f5(JspTag _jspx_th_c_005fchoose_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 8247 */     PageContext pageContext = _jspx_page_context;
/* 8248 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8250 */     OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 8251 */     _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 8252 */     _jspx_th_c_005fotherwise_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f6);
/* 8253 */     int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 8254 */     if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */       for (;;) {
/* 8256 */         out.write(45);
/* 8257 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 8258 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8262 */     if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 8263 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 8264 */       return true;
/*      */     }
/* 8266 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 8267 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f7(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 8272 */     PageContext pageContext = _jspx_page_context;
/* 8273 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8275 */     ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 8276 */     _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 8277 */     _jspx_th_c_005fchoose_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/* 8278 */     int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 8279 */     if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */       for (;;) {
/* 8281 */         if (_jspx_meth_c_005fwhen_005f7(_jspx_th_c_005fchoose_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 8282 */           return true;
/* 8283 */         if (_jspx_meth_c_005fotherwise_005f6(_jspx_th_c_005fchoose_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 8284 */           return true;
/* 8285 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 8286 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8290 */     if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 8291 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 8292 */       return true;
/*      */     }
/* 8294 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 8295 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f7(JspTag _jspx_th_c_005fchoose_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 8300 */     PageContext pageContext = _jspx_page_context;
/* 8301 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8303 */     WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 8304 */     _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 8305 */     _jspx_th_c_005fwhen_005f7.setParent((Tag)_jspx_th_c_005fchoose_005f7);
/*      */     
/* 8307 */     _jspx_th_c_005fwhen_005f7.setTest("${!empty props.STATUS}");
/* 8308 */     int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 8309 */     if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */       for (;;) {
/* 8311 */         if (_jspx_meth_c_005fout_005f31(_jspx_th_c_005fwhen_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 8312 */           return true;
/* 8313 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 8314 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8318 */     if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 8319 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 8320 */       return true;
/*      */     }
/* 8322 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 8323 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_c_005fwhen_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 8328 */     PageContext pageContext = _jspx_page_context;
/* 8329 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8331 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8332 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 8333 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_c_005fwhen_005f7);
/*      */     
/* 8335 */     _jspx_th_c_005fout_005f31.setValue("${props.STATUS}");
/* 8336 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 8337 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 8338 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 8339 */       return true;
/*      */     }
/* 8341 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 8342 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f6(JspTag _jspx_th_c_005fchoose_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 8347 */     PageContext pageContext = _jspx_page_context;
/* 8348 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8350 */     OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 8351 */     _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 8352 */     _jspx_th_c_005fotherwise_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f7);
/* 8353 */     int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 8354 */     if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */       for (;;) {
/* 8356 */         out.write(45);
/* 8357 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 8358 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8362 */     if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 8363 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 8364 */       return true;
/*      */     }
/* 8366 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 8367 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 8372 */     PageContext pageContext = _jspx_page_context;
/* 8373 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8375 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8376 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 8377 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 8379 */     _jspx_th_c_005fout_005f32.setValue("${props.ID}");
/* 8380 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 8381 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 8382 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 8383 */       return true;
/*      */     }
/* 8385 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 8386 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 8391 */     PageContext pageContext = _jspx_page_context;
/* 8392 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8394 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8395 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 8396 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 8398 */     _jspx_th_c_005fout_005f33.setValue("${props.RESOURCEID}");
/* 8399 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 8400 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 8401 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 8402 */       return true;
/*      */     }
/* 8404 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 8405 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8410 */     PageContext pageContext = _jspx_page_context;
/* 8411 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8413 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 8414 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 8415 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 8417 */     _jspx_th_c_005fset_005f2.setVar("poolResourceID");
/*      */     
/* 8419 */     _jspx_th_c_005fset_005f2.setValue("${row.CHILDID}");
/* 8420 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 8421 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 8422 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 8423 */       return true;
/*      */     }
/* 8425 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 8426 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f9(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8431 */     PageContext pageContext = _jspx_page_context;
/* 8432 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8434 */     ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 8435 */     _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 8436 */     _jspx_th_c_005fchoose_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/* 8437 */     int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 8438 */     if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */       for (;;) {
/* 8440 */         out.write(10);
/* 8441 */         out.write(9);
/* 8442 */         if (_jspx_meth_c_005fwhen_005f9(_jspx_th_c_005fchoose_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 8443 */           return true;
/* 8444 */         out.write(10);
/* 8445 */         out.write(9);
/* 8446 */         if (_jspx_meth_c_005fotherwise_005f8(_jspx_th_c_005fchoose_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 8447 */           return true;
/* 8448 */         out.write(10);
/* 8449 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 8450 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8454 */     if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 8455 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 8456 */       return true;
/*      */     }
/* 8458 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 8459 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f9(JspTag _jspx_th_c_005fchoose_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8464 */     PageContext pageContext = _jspx_page_context;
/* 8465 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8467 */     WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 8468 */     _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 8469 */     _jspx_th_c_005fwhen_005f9.setParent((Tag)_jspx_th_c_005fchoose_005f9);
/*      */     
/* 8471 */     _jspx_th_c_005fwhen_005f9.setTest("${status.count%2==0}");
/* 8472 */     int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 8473 */     if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */       for (;;) {
/* 8475 */         out.write(10);
/* 8476 */         out.write(9);
/* 8477 */         out.write(9);
/* 8478 */         if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fwhen_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 8479 */           return true;
/* 8480 */         out.write(10);
/* 8481 */         out.write(9);
/* 8482 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 8483 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8487 */     if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 8488 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 8489 */       return true;
/*      */     }
/* 8491 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 8492 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fwhen_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8497 */     PageContext pageContext = _jspx_page_context;
/* 8498 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8500 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 8501 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 8502 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f9);
/*      */     
/* 8504 */     _jspx_th_c_005fset_005f3.setVar("bgClass");
/*      */     
/* 8506 */     _jspx_th_c_005fset_005f3.setValue("whitegrayborder");
/* 8507 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 8508 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 8509 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f3);
/* 8510 */       return true;
/*      */     }
/* 8512 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f3);
/* 8513 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f8(JspTag _jspx_th_c_005fchoose_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8518 */     PageContext pageContext = _jspx_page_context;
/* 8519 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8521 */     OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 8522 */     _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 8523 */     _jspx_th_c_005fotherwise_005f8.setParent((Tag)_jspx_th_c_005fchoose_005f9);
/* 8524 */     int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 8525 */     if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */       for (;;) {
/* 8527 */         out.write(10);
/* 8528 */         out.write(9);
/* 8529 */         out.write(9);
/* 8530 */         if (_jspx_meth_c_005fset_005f4(_jspx_th_c_005fotherwise_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 8531 */           return true;
/* 8532 */         out.write(10);
/* 8533 */         out.write(9);
/* 8534 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 8535 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8539 */     if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 8540 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 8541 */       return true;
/*      */     }
/* 8543 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 8544 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_c_005fotherwise_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8549 */     PageContext pageContext = _jspx_page_context;
/* 8550 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8552 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 8553 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 8554 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f8);
/*      */     
/* 8556 */     _jspx_th_c_005fset_005f4.setVar("bgClass");
/*      */     
/* 8558 */     _jspx_th_c_005fset_005f4.setValue("yellowgrayborder");
/* 8559 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 8560 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 8561 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f4);
/* 8562 */       return true;
/*      */     }
/* 8564 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f4);
/* 8565 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8570 */     PageContext pageContext = _jspx_page_context;
/* 8571 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8573 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8574 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 8575 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 8577 */     _jspx_th_c_005fout_005f34.setValue("${bgClass}");
/* 8578 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 8579 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 8580 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 8581 */       return true;
/*      */     }
/* 8583 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 8584 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8589 */     PageContext pageContext = _jspx_page_context;
/* 8590 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8592 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8593 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 8594 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 8596 */     _jspx_th_c_005fout_005f35.setValue("${row.DISPLAYNAME}");
/* 8597 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 8598 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 8599 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 8600 */       return true;
/*      */     }
/* 8602 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 8603 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8608 */     PageContext pageContext = _jspx_page_context;
/* 8609 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8611 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8612 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 8613 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 8615 */     _jspx_th_c_005fout_005f36.setValue("${bgClass}");
/* 8616 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 8617 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 8618 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 8619 */       return true;
/*      */     }
/* 8621 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 8622 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8627 */     PageContext pageContext = _jspx_page_context;
/* 8628 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8630 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8631 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 8632 */     _jspx_th_c_005fout_005f37.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 8634 */     _jspx_th_c_005fout_005f37.setValue("${row.CHILDID}");
/* 8635 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 8636 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 8637 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 8638 */       return true;
/*      */     }
/* 8640 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 8641 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8646 */     PageContext pageContext = _jspx_page_context;
/* 8647 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8649 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8650 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 8651 */     _jspx_th_c_005fout_005f38.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 8653 */     _jspx_th_c_005fout_005f38.setValue("${bgClass}");
/* 8654 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 8655 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 8656 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 8657 */       return true;
/*      */     }
/* 8659 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 8660 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8665 */     PageContext pageContext = _jspx_page_context;
/* 8666 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8668 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8669 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 8670 */     _jspx_th_c_005fout_005f39.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 8672 */     _jspx_th_c_005fout_005f39.setValue("${row.CHILDID}");
/* 8673 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 8674 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 8675 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 8676 */       return true;
/*      */     }
/* 8678 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 8679 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f40(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8684 */     PageContext pageContext = _jspx_page_context;
/* 8685 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8687 */     OutTag _jspx_th_c_005fout_005f40 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8688 */     _jspx_th_c_005fout_005f40.setPageContext(_jspx_page_context);
/* 8689 */     _jspx_th_c_005fout_005f40.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 8691 */     _jspx_th_c_005fout_005f40.setValue("${bgClass}");
/* 8692 */     int _jspx_eval_c_005fout_005f40 = _jspx_th_c_005fout_005f40.doStartTag();
/* 8693 */     if (_jspx_th_c_005fout_005f40.doEndTag() == 5) {
/* 8694 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 8695 */       return true;
/*      */     }
/* 8697 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 8698 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f41(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8703 */     PageContext pageContext = _jspx_page_context;
/* 8704 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8706 */     OutTag _jspx_th_c_005fout_005f41 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8707 */     _jspx_th_c_005fout_005f41.setPageContext(_jspx_page_context);
/* 8708 */     _jspx_th_c_005fout_005f41.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 8710 */     _jspx_th_c_005fout_005f41.setValue("${row.CHILDID}");
/* 8711 */     int _jspx_eval_c_005fout_005f41 = _jspx_th_c_005fout_005f41.doStartTag();
/* 8712 */     if (_jspx_th_c_005fout_005f41.doEndTag() == 5) {
/* 8713 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 8714 */       return true;
/*      */     }
/* 8716 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 8717 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f42(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8722 */     PageContext pageContext = _jspx_page_context;
/* 8723 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8725 */     OutTag _jspx_th_c_005fout_005f42 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8726 */     _jspx_th_c_005fout_005f42.setPageContext(_jspx_page_context);
/* 8727 */     _jspx_th_c_005fout_005f42.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 8729 */     _jspx_th_c_005fout_005f42.setValue("${row.CPUUSAGE}");
/* 8730 */     int _jspx_eval_c_005fout_005f42 = _jspx_th_c_005fout_005f42.doStartTag();
/* 8731 */     if (_jspx_th_c_005fout_005f42.doEndTag() == 5) {
/* 8732 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 8733 */       return true;
/*      */     }
/* 8735 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 8736 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f43(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8741 */     PageContext pageContext = _jspx_page_context;
/* 8742 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8744 */     OutTag _jspx_th_c_005fout_005f43 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8745 */     _jspx_th_c_005fout_005f43.setPageContext(_jspx_page_context);
/* 8746 */     _jspx_th_c_005fout_005f43.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 8748 */     _jspx_th_c_005fout_005f43.setValue("${bgClass}");
/* 8749 */     int _jspx_eval_c_005fout_005f43 = _jspx_th_c_005fout_005f43.doStartTag();
/* 8750 */     if (_jspx_th_c_005fout_005f43.doEndTag() == 5) {
/* 8751 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 8752 */       return true;
/*      */     }
/* 8754 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 8755 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f44(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8760 */     PageContext pageContext = _jspx_page_context;
/* 8761 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8763 */     OutTag _jspx_th_c_005fout_005f44 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8764 */     _jspx_th_c_005fout_005f44.setPageContext(_jspx_page_context);
/* 8765 */     _jspx_th_c_005fout_005f44.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 8767 */     _jspx_th_c_005fout_005f44.setValue("${row.CHILDID}");
/* 8768 */     int _jspx_eval_c_005fout_005f44 = _jspx_th_c_005fout_005f44.doStartTag();
/* 8769 */     if (_jspx_th_c_005fout_005f44.doEndTag() == 5) {
/* 8770 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 8771 */       return true;
/*      */     }
/* 8773 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 8774 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f45(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8779 */     PageContext pageContext = _jspx_page_context;
/* 8780 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8782 */     OutTag _jspx_th_c_005fout_005f45 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8783 */     _jspx_th_c_005fout_005f45.setPageContext(_jspx_page_context);
/* 8784 */     _jspx_th_c_005fout_005f45.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 8786 */     _jspx_th_c_005fout_005f45.setValue("${row.MEMORYUSAGE}");
/* 8787 */     int _jspx_eval_c_005fout_005f45 = _jspx_th_c_005fout_005f45.doStartTag();
/* 8788 */     if (_jspx_th_c_005fout_005f45.doEndTag() == 5) {
/* 8789 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 8790 */       return true;
/*      */     }
/* 8792 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 8793 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f46(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8798 */     PageContext pageContext = _jspx_page_context;
/* 8799 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8801 */     OutTag _jspx_th_c_005fout_005f46 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8802 */     _jspx_th_c_005fout_005f46.setPageContext(_jspx_page_context);
/* 8803 */     _jspx_th_c_005fout_005f46.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 8805 */     _jspx_th_c_005fout_005f46.setValue("${bgClass}");
/* 8806 */     int _jspx_eval_c_005fout_005f46 = _jspx_th_c_005fout_005f46.doStartTag();
/* 8807 */     if (_jspx_th_c_005fout_005f46.doEndTag() == 5) {
/* 8808 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 8809 */       return true;
/*      */     }
/* 8811 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 8812 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f47(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8817 */     PageContext pageContext = _jspx_page_context;
/* 8818 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8820 */     OutTag _jspx_th_c_005fout_005f47 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8821 */     _jspx_th_c_005fout_005f47.setPageContext(_jspx_page_context);
/* 8822 */     _jspx_th_c_005fout_005f47.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 8824 */     _jspx_th_c_005fout_005f47.setValue("${row.CHILDID}");
/* 8825 */     int _jspx_eval_c_005fout_005f47 = _jspx_th_c_005fout_005f47.doStartTag();
/* 8826 */     if (_jspx_th_c_005fout_005f47.doEndTag() == 5) {
/* 8827 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 8828 */       return true;
/*      */     }
/* 8830 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 8831 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f48(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8836 */     PageContext pageContext = _jspx_page_context;
/* 8837 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8839 */     OutTag _jspx_th_c_005fout_005f48 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8840 */     _jspx_th_c_005fout_005f48.setPageContext(_jspx_page_context);
/* 8841 */     _jspx_th_c_005fout_005f48.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 8843 */     _jspx_th_c_005fout_005f48.setValue("${row.PROCESSCOUNT}");
/* 8844 */     int _jspx_eval_c_005fout_005f48 = _jspx_th_c_005fout_005f48.doStartTag();
/* 8845 */     if (_jspx_th_c_005fout_005f48.doEndTag() == 5) {
/* 8846 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 8847 */       return true;
/*      */     }
/* 8849 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 8850 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f49(JspTag _jspx_th_logic_005fpresent_005f10, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8855 */     PageContext pageContext = _jspx_page_context;
/* 8856 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8858 */     OutTag _jspx_th_c_005fout_005f49 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8859 */     _jspx_th_c_005fout_005f49.setPageContext(_jspx_page_context);
/* 8860 */     _jspx_th_c_005fout_005f49.setParent((Tag)_jspx_th_logic_005fpresent_005f10);
/*      */     
/* 8862 */     _jspx_th_c_005fout_005f49.setValue("${bgClass}");
/* 8863 */     int _jspx_eval_c_005fout_005f49 = _jspx_th_c_005fout_005f49.doStartTag();
/* 8864 */     if (_jspx_th_c_005fout_005f49.doEndTag() == 5) {
/* 8865 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 8866 */       return true;
/*      */     }
/* 8868 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 8869 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f50(JspTag _jspx_th_logic_005fpresent_005f10, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8874 */     PageContext pageContext = _jspx_page_context;
/* 8875 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8877 */     OutTag _jspx_th_c_005fout_005f50 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8878 */     _jspx_th_c_005fout_005f50.setPageContext(_jspx_page_context);
/* 8879 */     _jspx_th_c_005fout_005f50.setParent((Tag)_jspx_th_logic_005fpresent_005f10);
/*      */     
/* 8881 */     _jspx_th_c_005fout_005f50.setValue("${row.CHILDID}");
/* 8882 */     int _jspx_eval_c_005fout_005f50 = _jspx_th_c_005fout_005f50.doStartTag();
/* 8883 */     if (_jspx_th_c_005fout_005f50.doEndTag() == 5) {
/* 8884 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f50);
/* 8885 */       return true;
/*      */     }
/* 8887 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f50);
/* 8888 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f5(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8893 */     PageContext pageContext = _jspx_page_context;
/* 8894 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8896 */     PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 8897 */     _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 8898 */     _jspx_th_tiles_005fput_005f5.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 8900 */     _jspx_th_tiles_005fput_005f5.setName("footer");
/*      */     
/* 8902 */     _jspx_th_tiles_005fput_005f5.setValue("/jsp/footer.jsp");
/* 8903 */     int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 8904 */     if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 8905 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 8906 */       return true;
/*      */     }
/* 8908 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 8909 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8914 */     PageContext pageContext = _jspx_page_context;
/* 8915 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8917 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 8918 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 8919 */     _jspx_th_c_005fif_005f11.setParent(null);
/*      */     
/* 8921 */     _jspx_th_c_005fif_005f11.setTest("${DotNetAgentAvailable == 'true'}");
/* 8922 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 8923 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 8925 */         out.write("\n<script type=\"text/javascript\">\n$(document).ajaxComplete(function(event,request, settings) {\n\t\tif(settings.url.indexOf(\"myFields.do\") != -1)\n\t\t{\n\t\t\treturn;\n\t\t}\n\t\tif(settings.url.indexOf(\"showOverview\") != -1)\n\t\t{\n\t\t\tshowHide('insight_OverviewTab','");
/* 8926 */         if (_jspx_meth_c_005fout_005f51(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 8927 */           return true;
/* 8928 */         out.write("', '");
/* 8929 */         if (_jspx_meth_c_005fout_005f52(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 8930 */           return true;
/* 8931 */         out.write("');//No I18N\n\t\t}\n\t\telse if(settings.url.indexOf(\"showTransactions\") != -1)\n\t\t{\n\t\t\tshowHide('insight_transactionTab','");
/* 8932 */         if (_jspx_meth_c_005fout_005f53(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 8933 */           return true;
/* 8934 */         out.write("', '");
/* 8935 */         if (_jspx_meth_c_005fout_005f54(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 8936 */           return true;
/* 8937 */         out.write("');//No I18N\n\t\t}\n\t\telse if(settings.url.indexOf(\"showDBOperations\") != -1)\n\t\t{\n\t\t\tshowHide('insight_databaseTab','");
/* 8938 */         if (_jspx_meth_c_005fout_005f55(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 8939 */           return true;
/* 8940 */         out.write("', '");
/* 8941 */         if (_jspx_meth_c_005fout_005f56(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 8942 */           return true;
/* 8943 */         out.write("');//No I18N\n\t\t}\n\t\telse if(settings.url.indexOf(\"traces.do\") != -1)\n\t\t{\n\t\t\tshowHide('insight_tracesTab','");
/* 8944 */         if (_jspx_meth_c_005fout_005f57(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 8945 */           return true;
/* 8946 */         out.write("', '");
/* 8947 */         if (_jspx_meth_c_005fout_005f58(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 8948 */           return true;
/* 8949 */         out.write("');//No I18N\n\t\t}\n\t\telse\n\t\t{\n\t\t\tshowHide('OverviewTab');//No I18N\n\t\t}\n\t\t$('table').remove('.SelectedAppBg');//No I18N\n });\n</script>\n");
/* 8950 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 8951 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8955 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 8956 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 8957 */       return true;
/*      */     }
/* 8959 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 8960 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f51(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8965 */     PageContext pageContext = _jspx_page_context;
/* 8966 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8968 */     OutTag _jspx_th_c_005fout_005f51 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8969 */     _jspx_th_c_005fout_005f51.setPageContext(_jspx_page_context);
/* 8970 */     _jspx_th_c_005fout_005f51.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 8972 */     _jspx_th_c_005fout_005f51.setValue("${insightResourceID}");
/* 8973 */     int _jspx_eval_c_005fout_005f51 = _jspx_th_c_005fout_005f51.doStartTag();
/* 8974 */     if (_jspx_th_c_005fout_005f51.doEndTag() == 5) {
/* 8975 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 8976 */       return true;
/*      */     }
/* 8978 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 8979 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f52(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8984 */     PageContext pageContext = _jspx_page_context;
/* 8985 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8987 */     OutTag _jspx_th_c_005fout_005f52 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8988 */     _jspx_th_c_005fout_005f52.setPageContext(_jspx_page_context);
/* 8989 */     _jspx_th_c_005fout_005f52.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 8991 */     _jspx_th_c_005fout_005f52.setValue("${insightApplicationID}");
/* 8992 */     int _jspx_eval_c_005fout_005f52 = _jspx_th_c_005fout_005f52.doStartTag();
/* 8993 */     if (_jspx_th_c_005fout_005f52.doEndTag() == 5) {
/* 8994 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 8995 */       return true;
/*      */     }
/* 8997 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 8998 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f53(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9003 */     PageContext pageContext = _jspx_page_context;
/* 9004 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9006 */     OutTag _jspx_th_c_005fout_005f53 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9007 */     _jspx_th_c_005fout_005f53.setPageContext(_jspx_page_context);
/* 9008 */     _jspx_th_c_005fout_005f53.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 9010 */     _jspx_th_c_005fout_005f53.setValue("${insightResourceID}");
/* 9011 */     int _jspx_eval_c_005fout_005f53 = _jspx_th_c_005fout_005f53.doStartTag();
/* 9012 */     if (_jspx_th_c_005fout_005f53.doEndTag() == 5) {
/* 9013 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f53);
/* 9014 */       return true;
/*      */     }
/* 9016 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f53);
/* 9017 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f54(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9022 */     PageContext pageContext = _jspx_page_context;
/* 9023 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9025 */     OutTag _jspx_th_c_005fout_005f54 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9026 */     _jspx_th_c_005fout_005f54.setPageContext(_jspx_page_context);
/* 9027 */     _jspx_th_c_005fout_005f54.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 9029 */     _jspx_th_c_005fout_005f54.setValue("${insightApplicationID}");
/* 9030 */     int _jspx_eval_c_005fout_005f54 = _jspx_th_c_005fout_005f54.doStartTag();
/* 9031 */     if (_jspx_th_c_005fout_005f54.doEndTag() == 5) {
/* 9032 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f54);
/* 9033 */       return true;
/*      */     }
/* 9035 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f54);
/* 9036 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f55(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9041 */     PageContext pageContext = _jspx_page_context;
/* 9042 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9044 */     OutTag _jspx_th_c_005fout_005f55 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9045 */     _jspx_th_c_005fout_005f55.setPageContext(_jspx_page_context);
/* 9046 */     _jspx_th_c_005fout_005f55.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 9048 */     _jspx_th_c_005fout_005f55.setValue("${insightResourceID}");
/* 9049 */     int _jspx_eval_c_005fout_005f55 = _jspx_th_c_005fout_005f55.doStartTag();
/* 9050 */     if (_jspx_th_c_005fout_005f55.doEndTag() == 5) {
/* 9051 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f55);
/* 9052 */       return true;
/*      */     }
/* 9054 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f55);
/* 9055 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f56(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9060 */     PageContext pageContext = _jspx_page_context;
/* 9061 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9063 */     OutTag _jspx_th_c_005fout_005f56 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9064 */     _jspx_th_c_005fout_005f56.setPageContext(_jspx_page_context);
/* 9065 */     _jspx_th_c_005fout_005f56.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 9067 */     _jspx_th_c_005fout_005f56.setValue("${insightApplicationID}");
/* 9068 */     int _jspx_eval_c_005fout_005f56 = _jspx_th_c_005fout_005f56.doStartTag();
/* 9069 */     if (_jspx_th_c_005fout_005f56.doEndTag() == 5) {
/* 9070 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f56);
/* 9071 */       return true;
/*      */     }
/* 9073 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f56);
/* 9074 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f57(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9079 */     PageContext pageContext = _jspx_page_context;
/* 9080 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9082 */     OutTag _jspx_th_c_005fout_005f57 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9083 */     _jspx_th_c_005fout_005f57.setPageContext(_jspx_page_context);
/* 9084 */     _jspx_th_c_005fout_005f57.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 9086 */     _jspx_th_c_005fout_005f57.setValue("${insightResourceID}");
/* 9087 */     int _jspx_eval_c_005fout_005f57 = _jspx_th_c_005fout_005f57.doStartTag();
/* 9088 */     if (_jspx_th_c_005fout_005f57.doEndTag() == 5) {
/* 9089 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f57);
/* 9090 */       return true;
/*      */     }
/* 9092 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f57);
/* 9093 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f58(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9098 */     PageContext pageContext = _jspx_page_context;
/* 9099 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9101 */     OutTag _jspx_th_c_005fout_005f58 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9102 */     _jspx_th_c_005fout_005f58.setPageContext(_jspx_page_context);
/* 9103 */     _jspx_th_c_005fout_005f58.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 9105 */     _jspx_th_c_005fout_005f58.setValue("${insightApplicationID}");
/* 9106 */     int _jspx_eval_c_005fout_005f58 = _jspx_th_c_005fout_005f58.doStartTag();
/* 9107 */     if (_jspx_th_c_005fout_005f58.doEndTag() == 5) {
/* 9108 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f58);
/* 9109 */       return true;
/*      */     }
/* 9111 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f58);
/* 9112 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AvailabilityPerformance_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */