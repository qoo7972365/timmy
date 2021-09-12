/*      */ package org.apache.jsp.jsp.sap;
/*      */ 
/*      */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.Property;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.me.apm.cmdb.APMHelpDeskUtil.CIUrl;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
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
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.PasswordTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class CCMSDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
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
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
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
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session)
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
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
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
/*  677 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
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
/*  849 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
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
/* 1483 */       message = EnterpriseUtil.decodeString(message);
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
/* 1716 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
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
/* 1994 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
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
/* 2157 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2158 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
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
/* 2203 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2209 */   private static Map<String, Long> _jspx_dependants = new HashMap(9);
/* 2210 */   static { _jspx_dependants.put("/jsp/includes/SAPRightArea.jspf", Long.valueOf(1473429417000L));
/* 2211 */     _jspx_dependants.put("/jsp/MyField_div.jsp", Long.valueOf(1473429417000L));
/* 2212 */     _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2213 */     _jspx_dependants.put("/jsp/includes/cam_screen.jspf", Long.valueOf(1473429417000L));
/* 2214 */     _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2215 */     _jspx_dependants.put("/jsp/includes/TopBorder.jspf", Long.valueOf(1473429417000L));
/* 2216 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2217 */     _jspx_dependants.put("/jsp/MyField_trstrip.jsp", Long.valueOf(1473429417000L));
/* 2218 */     _jspx_dependants.put("/jsp/includes/BottomBorder.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2250 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2254 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2258 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2259 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2261 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2262 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2263 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2264 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2265 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2266 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2267 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2268 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2269 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2270 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2271 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2272 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2273 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2274 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2275 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2276 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2277 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2278 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2279 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2283 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2284 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2285 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2286 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2287 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2288 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2289 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2290 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2291 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2292 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/* 2293 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2294 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.release();
/* 2295 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2296 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2297 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2298 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2299 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.release();
/* 2300 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2301 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2302 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2303 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2304 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2305 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2306 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2313 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2316 */     JspWriter out = null;
/* 2317 */     Object page = this;
/* 2318 */     JspWriter _jspx_out = null;
/* 2319 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2323 */       response.setContentType("text/html;charset=UTF-8");
/* 2324 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2326 */       _jspx_page_context = pageContext;
/* 2327 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2328 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2329 */       session = pageContext.getSession();
/* 2330 */       out = pageContext.getOut();
/* 2331 */       _jspx_out = out;
/*      */       
/* 2333 */       out.write("<!DOCTYPE html>\n");
/* 2334 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n");
/*      */       
/* 2336 */       request.setAttribute("HelpKey", "Monitors SAP CCMS Server Details");
/*      */       
/* 2338 */       out.write("\n\n\n\n\n\n\n\n\n\n");
/* 2339 */       com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2340 */       wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2341 */       if (wlsGraph == null) {
/* 2342 */         wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2343 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2345 */       out.write(10);
/* 2346 */       out.write(10);
/*      */       
/* 2348 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2349 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2350 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2352 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2353 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2354 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2356 */           out.write(10);
/* 2357 */           if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2359 */           out.write(10);
/* 2360 */           if (_jspx_meth_c_005fchoose_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2362 */           out.write(10);
/* 2363 */           out.write(10);
/*      */           
/* 2365 */           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2366 */           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2367 */           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2369 */           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */           
/* 2371 */           _jspx_th_tiles_005fput_005f3.setType("string");
/* 2372 */           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2373 */           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2374 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2375 */               out = _jspx_page_context.pushBody();
/* 2376 */               _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2377 */               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2380 */               out.write("\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/sap.js\"></SCRIPT>\n\n<script>\nfunction submitForm(editform)\n{\n\t");
/* 2381 */               if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2383 */               out.write(10);
/* 2384 */               out.write(9);
/*      */               
/* 2386 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2387 */               _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2388 */               _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2390 */               _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2391 */               int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2392 */               if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                 for (;;) {
/* 2394 */                   out.write("\n        var dispname = trimAll(editform.displayname.value);\n\tif(dispname=='')\n\t{\n\t\talert('");
/* 2395 */                   out.print(FormatUtil.getString("am.webclient.common.validatename.text"));
/* 2396 */                   out.write("');\n\t\treturn;\n\t}\n\tpoll=trimAll(editform.pollInterval.value);\n\tif(poll == '' || !(isPositiveInteger(poll)) || poll =='0' )\n\t{\n\t\talert('");
/* 2397 */                   out.print(FormatUtil.getString("am.webclient.common.validpollinginterval.text"));
/* 2398 */                   out.write("');\n\t\treturn;\n\t}\n        var poll=trimAll(editform.username.value);\n\tif(poll == '')\n\t{\n\t\talert('");
/* 2399 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.username"));
/* 2400 */                   out.write("');\n\t\treturn;\n\t}\n        poll=trimAll(editform.password.value);\n\tif(poll == '')\n\t{\n\t\talert('");
/* 2401 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.password"));
/* 2402 */                   out.write("');\n\t\treturn;\n\t}\n\n\teditform.submit();\n\t");
/* 2403 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2404 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2408 */               if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2409 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */               }
/*      */               
/* 2412 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2413 */               out.write("\n}\n$(document).ready(function(){\n\tvar list=document.getElementsByName('usedRouterString')\t//NO I18N\n\tif(list[0].checked){\n\t\tjavascript:showRow(\"routerString\");\t//NO I18N\n\t}\n})\n\n</script>\n\n");
/* 2414 */               out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */               
/* 2416 */               DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2417 */               _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2418 */               _jspx_th_bean_005fdefine_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2420 */               _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */               
/* 2422 */               _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */               
/* 2424 */               _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */               
/* 2426 */               _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2427 */               int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2428 */               if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2429 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0); return;
/*      */               }
/*      */               
/* 2432 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2433 */               String available = null;
/* 2434 */               available = (String)_jspx_page_context.findAttribute("available");
/* 2435 */               out.write(10);
/*      */               
/* 2437 */               DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2438 */               _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2439 */               _jspx_th_bean_005fdefine_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2441 */               _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */               
/* 2443 */               _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */               
/* 2445 */               _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */               
/* 2447 */               _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2448 */               int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2449 */               if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2450 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1); return;
/*      */               }
/*      */               
/* 2453 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2454 */               String unavailable = null;
/* 2455 */               unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2456 */               out.write(10);
/*      */               
/* 2458 */               DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2459 */               _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2460 */               _jspx_th_bean_005fdefine_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2462 */               _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */               
/* 2464 */               _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */               
/* 2466 */               _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */               
/* 2468 */               _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2469 */               int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2470 */               if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2471 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2); return;
/*      */               }
/*      */               
/* 2474 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2475 */               String unmanaged = null;
/* 2476 */               unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2477 */               out.write(10);
/*      */               
/* 2479 */               DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2480 */               _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2481 */               _jspx_th_bean_005fdefine_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2483 */               _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */               
/* 2485 */               _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */               
/* 2487 */               _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */               
/* 2489 */               _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2490 */               int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2491 */               if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2492 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3); return;
/*      */               }
/*      */               
/* 2495 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2496 */               String scheduled = null;
/* 2497 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2498 */               out.write(10);
/*      */               
/* 2500 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2501 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2502 */               _jspx_th_bean_005fdefine_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2504 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2506 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2508 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2510 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2511 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2512 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2513 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4); return;
/*      */               }
/*      */               
/* 2516 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2517 */               String critical = null;
/* 2518 */               critical = (String)_jspx_page_context.findAttribute("critical");
/* 2519 */               out.write(10);
/*      */               
/* 2521 */               DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2522 */               _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2523 */               _jspx_th_bean_005fdefine_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2525 */               _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */               
/* 2527 */               _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */               
/* 2529 */               _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */               
/* 2531 */               _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2532 */               int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2533 */               if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2534 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5); return;
/*      */               }
/*      */               
/* 2537 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2538 */               String clear = null;
/* 2539 */               clear = (String)_jspx_page_context.findAttribute("clear");
/* 2540 */               out.write(10);
/*      */               
/* 2542 */               DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2543 */               _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2544 */               _jspx_th_bean_005fdefine_005f6.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2546 */               _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */               
/* 2548 */               _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */               
/* 2550 */               _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */               
/* 2552 */               _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2553 */               int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2554 */               if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2555 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6); return;
/*      */               }
/*      */               
/* 2558 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2559 */               String warning = null;
/* 2560 */               warning = (String)_jspx_page_context.findAttribute("warning");
/* 2561 */               out.write(10);
/* 2562 */               out.write(10);
/*      */               
/* 2564 */               String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2565 */               boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */               
/* 2567 */               out.write(10);
/* 2568 */               out.write(10);
/* 2569 */               out.write(10);
/* 2570 */               out.write(10);
/* 2571 */               out.write(10);
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2577 */               String resourceid = request.getParameter("resourceid");
/* 2578 */               String seven_days_text = FormatUtil.getString("am.webclient.common.sevendays.tooltip.text");
/* 2579 */               String thiry_days_text = FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text");
/* 2580 */               String xaxis_time = FormatUtil.getString("am.webclient.common.axisname.time.text");
/* 2581 */               String displayname = (String)request.getAttribute("displayname");
/* 2582 */               String encodeurl = URLEncoder.encode("/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid);
/* 2583 */               ArrayList resIDs = new ArrayList();
/* 2584 */               resIDs.add(resourceid);
/* 2585 */               ArrayList attribIDs = new ArrayList();
/* 2586 */               attribIDs.add("4000");
/* 2587 */               attribIDs.add("4001");
/* 2588 */               attribIDs.add("4002");
/* 2589 */               Properties alert = getStatus(resIDs, attribIDs);
/* 2590 */               wlsGraph.setParam(resourceid, "AVAILABILITY");
/* 2591 */               HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/* 2592 */               Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/*      */               
/* 2594 */               String haid = request.getParameter("haid");
/* 2595 */               String haName = null;
/* 2596 */               if (haid != null)
/*      */               {
/* 2598 */                 haName = (String)ht.get(haid);
/*      */               }
/*      */               
/* 2601 */               out.write("\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n\t");
/*      */               
/* 2603 */               ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2604 */               _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2605 */               _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/* 2606 */               int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2607 */               if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                 for (;;) {
/* 2609 */                   out.write(10);
/* 2610 */                   out.write(9);
/* 2611 */                   out.write(9);
/*      */                   
/* 2613 */                   WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2614 */                   _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2615 */                   _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                   
/* 2617 */                   _jspx_th_c_005fwhen_005f1.setTest("${!empty param.haid && param.haid!='null'}");
/* 2618 */                   int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2619 */                   if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                     for (;;) {
/* 2621 */                       out.write("\n\t\t\t<td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2622 */                       out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHomePage(request));
/* 2623 */                       out.write(" &gt; ");
/* 2624 */                       out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 2625 */                       out.write(" &gt; <span class=\"bcactive\">");
/* 2626 */                       out.print(request.getParameter("resourcename"));
/* 2627 */                       out.write(" </span></td>\n\t\t");
/* 2628 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2629 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2633 */                   if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2634 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                   }
/*      */                   
/* 2637 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2638 */                   out.write(10);
/* 2639 */                   out.write(9);
/* 2640 */                   out.write(9);
/*      */                   
/* 2642 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2643 */                   _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2644 */                   _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2645 */                   int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2646 */                   if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                     for (;;) {
/* 2648 */                       out.write("\n\t\t\t<td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2649 */                       out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorsPage());
/* 2650 */                       out.write(" &gt; ");
/* 2651 */                       out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorResourceTypes("SAP-CCMS"));
/* 2652 */                       out.write(" &gt; <span class=\"bcactive\">");
/* 2653 */                       out.print(request.getParameter("resourcename"));
/* 2654 */                       out.write(" </span></td>\n\t\t");
/* 2655 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2656 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2660 */                   if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2661 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                   }
/*      */                   
/* 2664 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2665 */                   out.write(10);
/* 2666 */                   out.write(9);
/* 2667 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2668 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2672 */               if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2673 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */               }
/*      */               
/* 2676 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2677 */               out.write("\n    </tr>\n\t<tr>\n\t\t<td height=\"2\" class=\"bcstrip\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"2\"></td>\n\t</tr>\n\t<tr>\n\t\t<td height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n</table>\n\n<div id=\"Reconfigure\" style=\"display:none\">\n<br>\n");
/*      */               
/* 2679 */               String type = request.getParameter("type");
/* 2680 */               String pollInterval = request.getParameter("pollInterval");
/* 2681 */               if (pollInterval == null)
/*      */               {
/* 2683 */                 pollInterval = "5";
/*      */               }
/*      */               
/* 2686 */               out.write(10);
/* 2687 */               out.write(10);
/*      */               
/* 2689 */               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 2690 */               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2691 */               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2693 */               _jspx_th_html_005fform_005f0.setAction("/sap");
/*      */               
/* 2695 */               _jspx_th_html_005fform_005f0.setMethod("get");
/*      */               
/* 2697 */               _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 2698 */               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2699 */               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                 for (;;) {
/* 2701 */                   out.write("\n\t<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 2702 */                   out.print(request.getParameter("name"));
/* 2703 */                   out.write("\">\n\t<input type=\"hidden\" name=\"type\" value=\"");
/* 2704 */                   out.print(type);
/* 2705 */                   out.write("\">\n\t<input type=\"hidden\" name=\"method\" value=\"editMonitor\">\n\t<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2706 */                   out.print(resourceid);
/* 2707 */                   out.write("\">\n\n\t<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\t\t<tr>\n\t\t\t<td height=\"28\" class=\"tableheading\">");
/* 2708 */                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2709 */                   out.write("</td>\n\t\t\t<td height=\"28\" class=\"tableheading\" align=\"right\" onClick=\"javascript:reconfigure()\"><span class=\"bodytextboldwhiteun\" ><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\">");
/* 2710 */                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 2711 */                   out.write("</span></td>\n\t\t</tr>\n\t</table>\n\t<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n\t\t<tr>\n\t\t\t<td height=\"28\" width=\"23%\" class=\"bodytext\" align=\"right\">");
/* 2712 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.displayname"));
/* 2713 */                   out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t<td height=\"28\" width=\"77%\">");
/* 2714 */                   if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2716 */                   out.write("</td>\n\t\t</tr>\n\t\t<TR>\n\t\t\t<TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\"  onmouseover=\"ddrivetip(this,event,'");
/* 2717 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.connect.withrouterstring"));
/* 2718 */                   out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/* 2719 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.connect.withrouterstring"));
/* 2720 */                   out.write("</a></label></TD>\n\t\t\t<TD height=\"28\" colspan=\"2\" >  ");
/* 2721 */                   if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2723 */                   out.write("\n\t\t\t</TD>\n\t\t</TR>\n\t\t<TR id=\"routerString\" style=\"display:none\">\n\t\t\t<TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\"  onmouseover=\"ddrivetip(this,event,'");
/* 2724 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.routerstring"));
/* 2725 */                   out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/* 2726 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.routerstring"));
/* 2727 */                   out.write("</a><span class=\"mandatory\">*</span></label></TD>\n\t\t\t<TD height=\"28\" colspan=\"2\" > ");
/* 2728 */                   if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2730 */                   out.write("\n\t\t\t</TD>\n\t\t</TR>\n\t\t<tr>\n\t\t\t<td height=\"28\" width=\"23%\" class=\"bodytext\" align=\"right\">");
/* 2731 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/* 2732 */                   out.write(" <span class=\"mandatory\">*</span></td>\n\t\t\t<td height=\"28\" width=\"77%\">");
/* 2733 */                   if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2735 */                   out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td height=\"28\" width=\"23%\" class=\"bodytext\" align=\"right\">");
/* 2736 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/* 2737 */                   out.write(" <span class=\"mandatory\">*</span></td>\n\t\t\t<td height=\"28\" width=\"77%\">");
/* 2738 */                   if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2740 */                   out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td height=\"28\" width=\"23%\" class=\"bodytext\" align=\"right\">");
/* 2741 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.pollinginterval"));
/* 2742 */                   out.write(" <span class=\"mandatory\">*</span></td>\n\t\t\t<td height=\"28\" width=\"77%\">");
/* 2743 */                   if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2745 */                   out.write("<span class=\"bodytext\">&nbsp;");
/* 2746 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.pollinginterval.units"));
/* 2747 */                   out.write("</span></td>\n\t\t</tr>\n\t</table>\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n\t\t<tr>\n\t\t\t<td width=\"40%\" class=\"tablebottom\">&nbsp;</td>\n\t\t\t<td width=\"60%\" class=\"tablebottom\"><input name=\"but1\" type=\"button\"  class=\"buttons\" value=\"");
/*      */                   
/* 2749 */                   IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2750 */                   _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2751 */                   _jspx_th_c_005fif_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 2753 */                   _jspx_th_c_005fif_005f0.setTest("${empty enabled}");
/* 2754 */                   int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2755 */                   if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                     for (;;) {
/* 2757 */                       out.print(FormatUtil.getString("am.webclient.common.update.text"));
/* 2758 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2759 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2763 */                   if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2764 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                   }
/*      */                   
/* 2767 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2768 */                   if (_jspx_meth_c_005fif_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2770 */                   out.write("\" onClick=\"javascript:submitForm(this.form)\"/>&nbsp; <input name=\"reset\" type=\"reset\" class=\"buttons\" value=\"");
/* 2771 */                   out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 2772 */                   out.write("\" onClick=\"javascript:reconfigure()\" /></td>\n\t\t</tr>\n\t</table>\n");
/* 2773 */                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2774 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2778 */               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2779 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */               }
/*      */               
/* 2782 */               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2783 */               out.write("\n<br>\n</div>\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n\t\t<td width=\"55%\" valign=\"top\">\n\t\t<table width=\"100%\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" class=\"lrtbdarkborder\">\n\t\t\t<tr>\n\t\t\t\t<td colspan=\"2\" align=\"left\" class=\"tableheadingbborder\">");
/* 2784 */               out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 2785 */               out.write("</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td width=\"30%\" class=\"monitorinfoodd\">");
/* 2786 */               out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2787 */               out.write("</td>\n\t\t\t\t<td width=\"70%\" class=\"monitorinfoodd\" title=\"");
/* 2788 */               out.print(displayname);
/* 2789 */               out.write(34);
/* 2790 */               out.write(62);
/* 2791 */               out.print(getTrimmedText(displayname, 40));
/* 2792 */               out.write("</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoeven\" valign=\"top\">");
/* 2793 */               out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2794 */               out.write("</td>\n\t\t\t\t<td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2795 */               out.print(resourceid);
/* 2796 */               out.write("&attributeid=4001')\">");
/* 2797 */               out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "4001")));
/* 2798 */               out.write("</a>\n\t\t\t\t");
/* 2799 */               out.print(getHideAndShowRCAMessage(alert.getProperty(resourceid + "#" + "4001" + "#" + "MESSAGE"), "4001", alert.getProperty(resourceid + "#" + "4001"), resourceid));
/* 2800 */               out.write("\n\t\t\t\t");
/* 2801 */               if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resourceid, "4001") != 0) {
/* 2802 */                 out.write("\n\t\t   \t\t<br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 2803 */                 out.print(resourceid + "_4001");
/* 2804 */                 out.write("&monitortype=SAP-CCMS')\">");
/* 2805 */                 out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 2806 */                 out.write("</a></span>\n           \t\t");
/*      */               }
/* 2808 */               out.write("\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2809 */               out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 2810 */               out.write(" </td>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2811 */               out.print(FormatUtil.getString("am.webclient.sap.server.type"));
/* 2812 */               out.write("</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2813 */               out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 2814 */               out.write("</td>\n\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2815 */               if (_jspx_meth_c_005fout_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2817 */               out.write("</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2818 */               out.print(FormatUtil.getString("am.webclient.ccms.monitorset"));
/* 2819 */               out.write("</td>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2820 */               if (_jspx_meth_c_005fout_005f1(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2822 */               out.write("</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2823 */               out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 2824 */               out.write("</td>\n\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2825 */               if (_jspx_meth_c_005fout_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2827 */               out.write("</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2828 */               out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 2829 */               out.write("</td>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2830 */               if (_jspx_meth_c_005fout_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2832 */               out.write("</td>\n\t\t\t</tr>\n\t\t\t  ");
/* 2833 */               out.write("<!--$Id$-->\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/customfield.js\"></SCRIPT>\n<script>\n $(document).ready(function(){\n\n\tvar customFieldsHash = document.location.hash;\n\n\tcustomFieldsHash = customFieldsHash.split(\"/\")\n\n\tif(customFieldsHash.length > 1)\t");
/* 2834 */               out.write("\n\t{\n\t\t");
/* 2835 */               if (_jspx_meth_c_005fif_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2837 */               out.write(10);
/* 2838 */               out.write(9);
/* 2839 */               out.write(9);
/* 2840 */               if (_jspx_meth_c_005fif_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2842 */               out.write("\n\t\tgetCustomFields('");
/* 2843 */               if (_jspx_meth_c_005fout_005f6(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2845 */               out.write("','noalarms',false,customFieldsHash[1],true)\t");
/* 2846 */               out.write("\n\t}\n\n});\n</script>\n");
/* 2847 */               if (_jspx_meth_c_005fif_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2849 */               out.write(10);
/* 2850 */               out.write(10);
/* 2851 */               if (_jspx_meth_c_005fif_005f5(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2853 */               out.write(10);
/* 2854 */               out.write(10);
/* 2855 */               if (_jspx_meth_c_005fset_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2857 */               out.write(10);
/* 2858 */               if (_jspx_meth_c_005fset_005f5(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2860 */               out.write(10);
/* 2861 */               out.write(10);
/* 2862 */               out.write(10);
/* 2863 */               if (_jspx_meth_c_005fif_005f6(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2865 */               out.write(10);
/* 2866 */               out.write(10);
/* 2867 */               out.write(10);
/* 2868 */               if (_jspx_meth_c_005fif_005f7(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2870 */               out.write("\n\n\n<tr>\n<td colspan=\"2\" class=\"");
/* 2871 */               if (_jspx_meth_c_005fout_005f13(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2873 */               out.write("\" align=\"right\" style=\"padding:2px;\">\n<input type=\"button\" value=\"");
/* 2874 */               if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2876 */               out.write("\" onclick=\"getCustomFields('");
/* 2877 */               if (_jspx_meth_c_005fout_005f14(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2879 */               out.write(39);
/* 2880 */               out.write(44);
/* 2881 */               out.write(39);
/* 2882 */               if (_jspx_meth_c_005fout_005f15(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2884 */               out.write("',false,'CustomFieldValues',false);\" class=\"buttons btn_custom\"/>");
/* 2885 */               out.write("\n</td>\n</tr>\n\n\n");
/* 2886 */               out.write("\n\t\t</table>\n\t\t\n\t\t<br>\n       \n    \t</td>\n    \t<td width=\"45%\" valign=\"top\" align=\"right\">\n        <table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n        \t<tr>\n          \t\t<td class=\"tableheadingbborder\">");
/* 2887 */               out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 2888 */               out.write(" <a name=\"Availability\" id=\"Availability\"></a>&nbsp;&nbsp;</td>\n        \t</tr>\n        \t<tr>\n          \t\t<td align=\"right\" >\n            \t<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n              \t\t<tr>\n                \t\t<td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 2889 */               if (_jspx_meth_c_005fout_005f16(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2891 */               out.write("&period=1&resourcename=");
/* 2892 */               if (_jspx_meth_c_005fout_005f17(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2894 */               out.write("')\"><img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 2895 */               out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2896 */               out.write("\"></td>\n                \t\t<td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 2897 */               if (_jspx_meth_c_005fout_005f18(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2899 */               out.write("&period=2&resourcename=");
/* 2900 */               if (_jspx_meth_c_005fout_005f19(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2902 */               out.write("')\"><img src=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 2903 */               out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 2904 */               out.write("\"></td>\n              \t\t</tr>\n            \t</table>\n          \t\t</td>\n        \t</tr>\n        \t<tr>\n          \t\t<td height=\"21\" align=\"center\" >\n          \t\t");
/*      */               
/* 2906 */               AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 2907 */               _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 2908 */               _jspx_th_awolf_005fpiechart_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2910 */               _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */               
/* 2912 */               _jspx_th_awolf_005fpiechart_005f0.setWidth("240");
/*      */               
/* 2914 */               _jspx_th_awolf_005fpiechart_005f0.setHeight("180");
/*      */               
/* 2916 */               _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */               
/* 2918 */               _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */               
/* 2920 */               _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */               
/* 2922 */               _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 2923 */               int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 2924 */               if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 2925 */                 if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 2926 */                   out = _jspx_page_context.pushBody();
/* 2927 */                   _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 2928 */                   _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2931 */                   out.write("\n            \t");
/*      */                   
/* 2933 */                   Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 2934 */                   _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 2935 */                   _jspx_th_awolf_005fmap_005f0.setParent(_jspx_th_awolf_005fpiechart_005f0);
/*      */                   
/* 2937 */                   _jspx_th_awolf_005fmap_005f0.setId("color");
/* 2938 */                   int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 2939 */                   if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 2940 */                     if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 2941 */                       out = _jspx_page_context.pushBody();
/* 2942 */                       _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 2943 */                       _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 2946 */                       out.write(32);
/*      */                       
/* 2948 */                       AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 2949 */                       _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 2950 */                       _jspx_th_awolf_005fparam_005f0.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                       
/* 2952 */                       _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */                       
/* 2954 */                       _jspx_th_awolf_005fparam_005f0.setValue(available);
/* 2955 */                       int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 2956 */                       if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 2957 */                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0); return;
/*      */                       }
/*      */                       
/* 2960 */                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 2961 */                       out.write("\n            \t");
/*      */                       
/* 2963 */                       AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 2964 */                       _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 2965 */                       _jspx_th_awolf_005fparam_005f1.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                       
/* 2967 */                       _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */                       
/* 2969 */                       _jspx_th_awolf_005fparam_005f1.setValue(unavailable);
/* 2970 */                       int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 2971 */                       if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 2972 */                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1); return;
/*      */                       }
/*      */                       
/* 2975 */                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 2976 */                       out.write(32);
/* 2977 */                       int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 2978 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 2981 */                     if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 2982 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 2985 */                   if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 2986 */                     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0); return;
/*      */                   }
/*      */                   
/* 2989 */                   this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 2990 */                   out.write("\n            \t");
/* 2991 */                   int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 2992 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2995 */                 if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 2996 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2999 */               if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 3000 */                 this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0); return;
/*      */               }
/*      */               
/* 3003 */               this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 3004 */               out.write("\n          \t\t</td>\n        \t</tr>\n        \t<tr>\n          \t\t<td>\n          \t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              \t\t<tbody>\n                \t<tr>\n                \t  <td width=\"32%\" height=\"35\"  class=\"yellowgrayborder\">&nbsp;");
/* 3005 */               out.print(FormatUtil.getString("am.webclient.tomacatdetail.currentstatus"));
/* 3006 */               out.write("<br></td>\n                \t  <td width=\"17%\" height=\"35\" class=\"yellowgrayborder\" align=\"left\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3007 */               out.print(resourceid);
/* 3008 */               out.write("&attributeid=4000')\">");
/* 3009 */               out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + "4000")));
/* 3010 */               out.write("</a></td>\n                \t  <td width=\"51%\" align=\"right\" class=\"yellowgrayborder\" ><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3011 */               out.print(resourceid);
/* 3012 */               out.write("&attributeIDs=4000,4001&attributeToSelect=4000&redirectto=");
/* 3013 */               out.print(encodeurl);
/* 3014 */               out.write("#Availability\" class=\"staticlinks\">");
/* 3015 */               out.print(ALERTCONFIG_TEXT);
/* 3016 */               out.write("</a>&nbsp;</td>\n                \t</tr>\n            \t\t</tbody>\n            \t</table>\n            \t</td>\n        \t</tr>\n      \t</table>\n    \t</td>\n  \t</tr>\n  \t<tr><td colspan=\"2\">\n  \t\t <table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" ><tr><td>");
/* 3017 */               out.write("<!--$Id$-->\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td>\n<div id=\"customfieldsfullListDiv\" style='overflow: auto; display:none; width: 100%;'>\n</div>\n<div id=\"customfieldsloadingdiv\" style='text-align:center;height:200px;width: 100%;display: none;'><img src='/images/LoadingTC.gif' style='margin-top:74px'/></div>\n</td></tr></table>\n");
/* 3018 */               out.write("</td></tr></table>\n  \t</td></tr>\n  \t<tr ><td style=\"padding-top: 15px;\">\n  \t ");
/* 3019 */               out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 3020 */               out.write("\n\t\t<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n\t\t\t<tr>\n\t\t\t\t<td class=\"bodytextbold\" width=\"60%\">\n\t\t\t\t");
/* 3021 */               out.print(FormatUtil.getString("Connection Time"));
/* 3022 */               out.write("\n\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3023 */               if (_jspx_meth_c_005fout_005f20(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 3025 */               out.write("&attributeid=4002&alertconfigurl=");
/* 3026 */               out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=4002&attributeToSelect=4002&redirectto=" + encodeurl));
/* 3027 */               out.write("')\">");
/* 3028 */               out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "4002")));
/* 3029 */               out.write("</a>\n            \t</td>\n            \t<td width=\"40%\" align=\"right\">\n            \t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3030 */               if (_jspx_meth_c_005fout_005f21(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 3032 */               out.write("&attributeid=4002&period=-7',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3033 */               out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3034 */               out.write("\"></a>\n      \t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3035 */               if (_jspx_meth_c_005fout_005f22(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 3037 */               out.write("&attributeid=4002&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3038 */               out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3039 */               out.write("\"></a>\n      \t\t\t</td>\n      \t\t</tr>\n      \t\t<tr>\n      \t\t\t<td colspan=\"0\" align=\"center\" class=\"textResponseTime\">\n        \t\t");
/*      */               
/* 3041 */               ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3042 */               _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 3043 */               _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/* 3044 */               int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 3045 */               if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                 for (;;) {
/* 3047 */                   out.write("\n\t\t\t\t\t");
/*      */                   
/* 3049 */                   WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3050 */                   _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 3051 */                   _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                   
/* 3053 */                   _jspx_th_c_005fwhen_005f2.setTest("${!empty connectiontime}");
/* 3054 */                   int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 3055 */                   if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                     for (;;) {
/* 3057 */                       out.write("\n\t\t\t\t\t\t");
/* 3058 */                       if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                         return;
/* 3060 */                       out.write(32);
/* 3061 */                       out.print(FormatUtil.getString("am.webclient.hostResource.ms"));
/* 3062 */                       out.write("\n\t\t\t\t\t");
/* 3063 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 3064 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3068 */                   if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 3069 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                   }
/*      */                   
/* 3072 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 3073 */                   out.write("\n\t\t\t\t\t");
/* 3074 */                   if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/*      */                     return;
/* 3076 */                   out.write("\n                ");
/* 3077 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 3078 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3082 */               if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 3083 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */               }
/*      */               
/* 3086 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 3087 */               out.write("\n      \t\t\t</td>\n      \t\t\t<td valign=bottom align=right>\n     \t\t\t");
/*      */               
/* 3089 */               if (!EnterpriseUtil.isAdminServer())
/*      */               {
/*      */ 
/* 3092 */                 out.write("\n\t\t\t\t\t<img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3093 */                 out.print(resourceid);
/* 3094 */                 out.write("&attributeIDs=4002&attributeToSelect=4002&redirectto=");
/* 3095 */                 out.print(encodeurl);
/* 3096 */                 out.write("\" class=\"staticlinks\">");
/* 3097 */                 out.print(ALERTCONFIG_TEXT);
/* 3098 */                 out.write("</a>&nbsp;\n\t\t\t\t");
/*      */               }
/* 3100 */               out.write("\n      \t\t\t</td>\n      \t\t</tr>\n        </table>\n\t\t");
/* 3101 */               out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 3102 */               out.write("</td></tr>\n</table>\n\n<br>\n");
/* 3103 */               out.write("<!--$Id$-->\n");
/* 3104 */               com.adventnet.appmanager.cam.beans.CAMGraphs camGraph = null;
/* 3105 */               camGraph = (com.adventnet.appmanager.cam.beans.CAMGraphs)_jspx_page_context.getAttribute("camGraph", 1);
/* 3106 */               if (camGraph == null) {
/* 3107 */                 camGraph = new com.adventnet.appmanager.cam.beans.CAMGraphs();
/* 3108 */                 _jspx_page_context.setAttribute("camGraph", camGraph, 1);
/*      */               }
/* 3110 */               out.write("\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/customfield.js\"></SCRIPT>\n\n");
/*      */               
/* 3112 */               long camStartTime = System.currentTimeMillis();
/*      */               
/* 3114 */               String camIDI = (String)request.getAttribute("camid");
/* 3115 */               String screenIDI = (String)request.getAttribute("screenid");
/* 3116 */               List screenInfoI = (List)request.getAttribute("screeninfo");
/* 3117 */               boolean perfAvailResourceScreen = false;
/* 3118 */               String resourceID = "";
/* 3119 */               String fromConfPage1 = request.getAttribute("fromConfPage") + "";
/* 3120 */               String haidI = request.getParameter("haid");
/* 3121 */               if ((haidI == null) || (haidI.trim().length() == 0)) {
/* 3122 */                 haidI = request.getParameter("haid");
/*      */               }
/*      */               
/*      */ 
/*      */ 
/* 3127 */               String isFromResourcePage = (String)request.getAttribute("isfromresourcepage");
/* 3128 */               if (isFromResourcePage == null) {
/* 3129 */                 isFromResourcePage = "NA";
/*      */               }
/*      */               
/*      */ 
/*      */ 
/* 3134 */               if ("true".equals(isFromResourcePage))
/*      */               {
/*      */ 
/*      */ 
/* 3138 */                 resourceID = (String)request.getAttribute("resourceid");
/* 3139 */                 if ((resourceID == null) || (resourceID.trim().length() == 0)) {
/* 3140 */                   resourceID = request.getParameter("resourceid");
/*      */                 }
/*      */                 
/* 3143 */                 camIDI = resourceID;
/* 3144 */                 perfAvailResourceScreen = true;
/*      */                 
/*      */ 
/* 3147 */                 request.setAttribute("screenInfo", screenInfoI);
/* 3148 */                 List tmpList = CAMDBUtil.getScreens(Integer.parseInt(camIDI));
/* 3149 */                 if (tmpList.size() == 0)
/*      */                 {
/* 3151 */                   CAMDBUtil.createDefaultScreenForResource(Integer.parseInt(camIDI));
/* 3152 */                   tmpList = CAMDBUtil.getScreens(Integer.parseInt(camIDI));
/* 3153 */                   screenInfoI = (List)tmpList.get(0);
/*      */                 }
/*      */                 else {
/* 3156 */                   screenInfoI = (List)tmpList.get(0);
/*      */                 }
/*      */                 
/* 3159 */                 screenIDI = (String)screenInfoI.get(0);
/* 3160 */                 request.setAttribute("screenid", screenIDI);
/*      */               }
/*      */               
/*      */ 
/* 3164 */               Map fromDB = CAMDBUtil.getCustomizedDataForScreenAdminActivity(Long.parseLong(screenIDI));
/* 3165 */               List screenConfigList = (List)fromDB.get("completedata");
/*      */               
/*      */ 
/* 3168 */               List reportsData = (List)fromDB.get("reportsdata");
/*      */               
/*      */ 
/* 3171 */               Map dcTimeMap = CAMDBUtil.getLatestCollectionTimes(Long.parseLong(screenIDI));
/* 3172 */               Map attribResourceMap = CAMDBUtil.getResourceNamesForAttributesInScreen(Integer.parseInt(screenIDI));
/*      */               
/* 3174 */               List screenAttribInfo = CAMDBUtil.getScreenAttributeInfo(Long.parseLong(screenIDI), Integer.parseInt((String)screenInfoI.get(3)), dcTimeMap);
/* 3175 */               boolean scalarAttribsPresent = screenAttribInfo.size() > 0;
/* 3176 */               List colScreenAttribInfo = CAMDBUtil.getScreenInfoForColumnarData(Long.parseLong(screenIDI));
/* 3177 */               boolean columnarAttribsPresent = colScreenAttribInfo.size() > 0;
/* 3178 */               boolean attribsPresent = (scalarAttribsPresent == true) || (columnarAttribsPresent == true);
/* 3179 */               String quickNote = "This page displays the attributes monitored from various resources as configured during design time. Placing the mouse over the value for table data will display the time when the data was collected. Time intervals will be different if the attributes are from different resources.";
/*      */               
/*      */ 
/*      */ 
/* 3183 */               if (request.getAttribute("quicknote") == null) {
/* 3184 */                 request.setAttribute("quicknote", quickNote);
/*      */               }
/* 3186 */               String configureLink = "/ShowCAM.do?method=configureScreen&screenid=" + screenIDI + "&camid=" + camIDI + "&haid=" + haidI + "&isfromresourcepage=" + isFromResourcePage;
/* 3187 */               if ((request.isUserInRole("ENTERPRISEADMIN")) && (com.adventnet.appmanager.util.Constants.isSsoEnabled()))
/*      */               {
/* 3189 */                 StringBuilder builder = new StringBuilder();
/* 3190 */                 builder.append("https:").append("//");
/* 3191 */                 builder.append(com.adventnet.appmanager.util.Constants.getAppHostName()).append(":");
/* 3192 */                 builder.append(com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.getsslport()).append(configureLink);
/* 3193 */                 configureLink = builder.toString();
/*      */               }
/* 3195 */               request.setAttribute("configurelink", configureLink);
/* 3196 */               String secondLevelLinkTitle; if (!perfAvailResourceScreen)
/*      */               {
/*      */ 
/* 3199 */                 List sLinks = new ArrayList();
/* 3200 */                 List sText = new ArrayList();
/*      */                 
/* 3202 */                 sLinks.add("Add ScreenXXXX");
/* 3203 */                 sText.add("/ShowCAM.do?method=addScreen&camid=" + camIDI + "&haid=" + haidI);
/*      */                 
/*      */ 
/*      */ 
/* 3207 */                 sLinks.add("Customize");
/* 3208 */                 sText.add(configureLink);
/*      */                 
/*      */ 
/* 3211 */                 sLinks.add("<a href=\"/CAMDeleteScreen.do?method=deleteScreen&screenid=" + screenIDI + "&camid=" + camIDI + "&haid=" + haidI + "\" onclick=\"return deleteScreen();\" class='staticlinks'>Delete Screen</a>");
/* 3212 */                 sText.add("");
/*      */                 
/*      */ 
/*      */ 
/* 3216 */                 List[] secondLevelOfLinks = { sText, sLinks };
/* 3217 */                 secondLevelLinkTitle = "Admin Activity";
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3226 */               String configureThresholdRedirectLink = "/ShowCAM.do?method=showScreen&haid=" + haidI + "&camid=" + camIDI + "&screenid=" + screenIDI;
/*      */               
/* 3228 */               if ("true".equals(isFromResourcePage)) {
/* 3229 */                 configureThresholdRedirectLink = "/showresource.do?resourceid=" + camIDI + "&method=showResourceForResourceID";
/*      */               }
/*      */               
/* 3232 */               configureThresholdRedirectLink = URLEncoder.encode(configureThresholdRedirectLink);
/*      */               
/*      */ 
/*      */ 
/* 3236 */               out.write("\n\n\n\n<script language=\"JavaScript1.2\">\n\nfunction showAttribGraph(attribID,mbean) {\n       var featurelist = \"toolbar=no,status=no,menubar=no,width=450,height=300,scrollbars=yes\";\n       var url = \"/ShowCAM.do?method=showSingleGraphScreen&attributeid=\" + attribID+\"&mbean=\" +mbean;\n       popUp = window.open(url,'popUp',featurelist);\n       return false;\n}\n\n</SCRIPT>\n<!--This script is used to show horizontal bar if customer attributes tables have more number of attributes in SNMP Devices.--> \n<script>\n    jQuery(document).ready(function(){\n        var windowWidth = jQuery(window).width();\n        windowWidth = windowWidth*(81/100);\n        jQuery('.horTableScroll').css({'width':windowWidth});//No I18N\n\n    });\n</script>\n\n<style>\n    .horTableScroll {\n        overflow-x:auto;\n    }    \n</style>\n<!--");
/*      */               
/* 3238 */               String camid = request.getParameter("camid");
/*      */               
/*      */ 
/* 3241 */               out.write("-->\n\n\n<script>\nfunction validateAndSubmit()\n{\n   if(trimAll(document.AMActionForm.camname.value)==\"\")\n   {\n        alert('");
/* 3242 */               out.print(FormatUtil.getString("am.webclient.cam.namealert"));
/* 3243 */               out.write("');\n        return;\n   }\n   document.AMActionForm.submit();\n}\n\n</script>\n\n");
/*      */               
/* 3245 */               List list = CAMDBUtil.getCAMDetails(camIDI);
/* 3246 */               String camName = (String)list.get(0);
/* 3247 */               String camDescription = (String)list.get(2);
/*      */               
/* 3249 */               out.write("\n\n\n\n");
/*      */               
/* 3251 */               if ("true".equals(request.getParameter("editPage")))
/*      */               {
/* 3253 */                 out.write("\n<div id=\"edit\" style=\"display:block\">\n");
/*      */               } else {
/* 3255 */                 out.write("\n<div id=\"edit\" style=\"display:none\">\n");
/*      */               }
/* 3257 */               out.write(10);
/* 3258 */               out.write(10);
/*      */               
/* 3260 */               FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 3261 */               _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/* 3262 */               _jspx_th_html_005fform_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3264 */               _jspx_th_html_005fform_005f1.setAction("/adminAction");
/*      */               
/* 3266 */               _jspx_th_html_005fform_005f1.setMethod("get");
/*      */               
/* 3268 */               _jspx_th_html_005fform_005f1.setStyle("display:inline;");
/* 3269 */               int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/* 3270 */               if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */                 for (;;) {
/* 3272 */                   out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\n<tr>\n<td height=\"28\"   class=\"tableheading\">");
/* 3273 */                   out.print(FormatUtil.getString("am.webclient.common.configurationdetails.text"));
/* 3274 */                   out.write("\n\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 3275 */                   out.print(request.getParameter("name"));
/* 3276 */                   out.write("\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 3277 */                   out.print(request.getParameter("haid"));
/* 3278 */                   out.write("\">\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 3279 */                   out.print(request.getParameter("type"));
/* 3280 */                   out.write("\">\n<input type=\"hidden\" name=\"type\" value=\"");
/* 3281 */                   out.print(request.getParameter("type"));
/* 3282 */                   out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"configureJMX\">\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 3283 */                   out.print(request.getParameter("resourceid"));
/* 3284 */                   out.write("\">\n<input type=\"hidden\" name=\"resourcename\" value=\"");
/* 3285 */                   out.print(request.getParameter("resourcename"));
/* 3286 */                   out.write("\">\n<input type=\"hidden\" name=\"moname\" value=\"");
/* 3287 */                   out.print(request.getParameter("moname"));
/* 3288 */                   out.write("\">\n\n</td>\n<td height=\"28\"   class=\"tableheading\" align=\"right\" onClick=\"javascript:hideDiv('edit')\">\n<span class=\"bodytextboldwhiteun\" ><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\">\n");
/* 3289 */                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 3290 */                   out.write("</span>\n</td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n<tr>\n    <td width=\"20%\" height=\"32\" valign=='top' class=\"bodytext\"> ");
/* 3291 */                   out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 3292 */                   out.write("\n</td>\n    <td width=\"80%\" class=\"bodytext\"> <textarea name=\"camname\" cols=\"38\" rows=\"1\" class=\"formtextarea\">");
/* 3293 */                   out.print(camName);
/* 3294 */                   out.write(" </textarea>\n</td>\n</tr>\n\n<tr>\n    <td valign='top'  class=\"bodytext\"> ");
/* 3295 */                   out.print(FormatUtil.getString("Description"));
/* 3296 */                   out.write("</td>\n    <td  class=\"bodytext\"> <textarea name=\"camdesc\" cols=\"38\" rows=\"3\" class=\"formtextarea\" >");
/* 3297 */                   out.print(camDescription);
/* 3298 */                   out.write("</textarea>\n    </td>\n  </tr>\n</table>\n");
/*      */                   
/* 3300 */                   String cancel = FormatUtil.getString("am.webclient.common.cancel.text");
/*      */                   
/* 3302 */                   out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"20%\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"80%\" class=\"tablebottom\" >\n<input name=\"addcustomapp\" type=\"button\" class=\"buttons btn_highlt\" \" value=\"");
/* 3303 */                   out.print(FormatUtil.getString("am.webclient.common.startmonitoring.text"));
/* 3304 */                   out.write("\" onClick=\"validateAndSubmit()\"/>\n      &nbsp; <input name=\"reset\" type=\"reset\" class=\"buttons btn_link\" value=\"");
/* 3305 */                   out.print(cancel);
/* 3306 */                   out.write("\" onClick=\"javascript:toggleDiv('edit')\"/>\n     </td>\n  </tr>\n</table>\n");
/* 3307 */                   int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/* 3308 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3312 */               if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/* 3313 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*      */               }
/*      */               
/* 3316 */               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f1);
/* 3317 */               out.write("\n</div>\n");
/*      */               
/* 3319 */               if (!attribsPresent)
/*      */               {
/*      */ 
/* 3322 */                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n    <td colspan=3  height=\"19\" class=\"tableheadingbborder\" >");
/* 3323 */                 out.print(camName);
/* 3324 */                 out.write("\n      ");
/* 3325 */                 if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 3326 */                   out.write(": <span class=\"topdesc\">");
/* 3327 */                   out.print(FormatUtil.getString(camDescription));
/* 3328 */                   out.write(" </span>");
/*      */                 }
/* 3330 */                 out.write("</td>\n     <td  height=\"19\" width=\"20%\" class=\"tableheadingbborder\">\n     ");
/*      */                 
/* 3332 */                 if ((attribsPresent) && (request.getAttribute("showrefreshnowoption") != null))
/*      */                 {
/*      */ 
/* 3335 */                   out.write("\n       <a class=\"bodytextboldwhiteun\" href=\"/deleteMO.do?method=fetchDataNowForResource&resourceid=");
/* 3336 */                   out.print(camIDI);
/* 3337 */                   out.write("&redirectto=");
/* 3338 */                   out.print(URLEncoder.encode("/showresource.do?resourceid=" + camIDI + "&method=showResourceForResourceID" + fromConfPage1));
/* 3339 */                   out.write(34);
/* 3340 */                   out.write(62);
/* 3341 */                   out.print(FormatUtil.getString("am.webclient.availabilityperf.fetchvalue"));
/* 3342 */                   out.write("</a>\n     ");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/* 3348 */                   out.write("\n     &nbsp;\n     ");
/*      */                 }
/*      */                 
/*      */ 
/* 3352 */                 out.write("\n\n     </td>\n</tr>\n\n<tr>\n    <td colspan=4  height=\"29\" ><span class=\"bodytext\">&nbsp;");
/* 3353 */                 out.print(FormatUtil.getString("am.webclient.cam.addcustomattributes.message"));
/*      */                 
/* 3355 */                 PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3356 */                 _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3357 */                 _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 3359 */                 _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 3360 */                 int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3361 */                 if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                   for (;;) {
/* 3363 */                     out.write(" <a class='staticlinks' href=\"");
/* 3364 */                     out.print(configureLink);
/* 3365 */                     out.write("\">\n      ");
/* 3366 */                     out.print(FormatUtil.getString("am.webclient.cam.addattributes.link"));
/* 3367 */                     out.write("</a>.");
/* 3368 */                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3369 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3373 */                 if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3374 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                 }
/*      */                 
/* 3377 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3378 */                 out.write("</span></td>\n</tr>\n</table>\n");
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/* 3383 */                 if (!scalarAttribsPresent) {
/* 3384 */                   out.write(10);
/* 3385 */                   out.write(10);
/*      */ 
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/*      */ 
/* 3393 */                   List row = null;
/* 3394 */                   int posOfAttribName = 2;
/* 3395 */                   int posOfDispType = 4;
/* 3396 */                   int posOfAttribValue = 7;
/* 3397 */                   int posOfResourceID = 6;
/* 3398 */                   int posOfAttribID = 0;
/* 3399 */                   int posOfAttribType = 3;
/* 3400 */                   String className = "whitegrayborder";
/* 3401 */                   String currentResourceName = null;
/* 3402 */                   String currentMBeanName = null;
/* 3403 */                   Map infoMap = CAMDBUtil.getMBeanBasedScreenData(Long.parseLong(screenIDI));
/* 3404 */                   Map idVsMBeanAndRes = (HashMap)infoMap.get("attrIdVsMBeanName");
/* 3405 */                   List ordListFromDB = (ArrayList)infoMap.get("attributeidsorderedlist");
/* 3406 */                   List orderedList = new ArrayList(screenAttribInfo.size());
/*      */                   
/*      */ 
/* 3409 */                   out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n    <td colspan=3  height=\"19\" class=\"tableheadingbborder\" >");
/* 3410 */                   out.print(camName);
/* 3411 */                   out.write("\n    ");
/* 3412 */                   if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 3413 */                     out.write("  : <span class=\"topdesc\">");
/* 3414 */                     out.print(FormatUtil.getString(camDescription));
/* 3415 */                     out.write(" </span> ");
/*      */                   }
/* 3417 */                   out.write("</td>\n\t<td width=\"30%\" nowrap class=\"tableheadingbborder\">\n\t");
/* 3418 */                   if ((attribsPresent) && (request.getAttribute("showrefreshnowoption") != null)) {
/* 3419 */                     out.write("\n       <a class=\"bodytextboldwhiteun\" href=\"/deleteMO.do?method=fetchDataNowForResource&resourceid=");
/* 3420 */                     out.print(camIDI);
/* 3421 */                     out.write("&redirectto=");
/* 3422 */                     out.print(URLEncoder.encode("/showresource.do?resourceid=" + camIDI + "&method=showResourceForResourceID" + fromConfPage1));
/* 3423 */                     out.write(34);
/* 3424 */                     out.write(62);
/* 3425 */                     out.print(FormatUtil.getString("am.webclient.availabilityperf.fetchvalue"));
/* 3426 */                     out.write("</a>\n       ");
/*      */                   } else {
/* 3428 */                     out.write("\n       <a class=\"staticlinks\" href=\"javascript:void(0);\" onclick=\"getCustomFields('");
/* 3429 */                     out.print(camIDI);
/* 3430 */                     out.write("','noalarms',false,'CustomFieldValues',false);\">");
/* 3431 */                     out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 3432 */                     out.write("</a>&nbsp;");
/* 3433 */                     out.write("\n       ");
/*      */                   }
/* 3435 */                   out.write("\n       </td>\n\n<tr>\n                <td width=\"36%\" class=\"columnheading\" > ");
/* 3436 */                   out.print(FormatUtil.getString("am.webclient.camscreen.attributename"));
/* 3437 */                   out.write("</td>\n            <td width=\"30%\" class=\"columnheading\" > ");
/* 3438 */                   out.print(FormatUtil.getString("am.webclient.camscreen.value"));
/* 3439 */                   out.write("</td>\n        <td width=\"20%\" class=\"columnheading\" > ");
/* 3440 */                   if ((request.getParameter("type") != null) && (request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 3441 */                     out.write(" &nbsp; ");
/*      */                   } else {
/* 3443 */                     out.write(32);
/* 3444 */                     out.print(FormatUtil.getString("am.webclient.camscreen.datacollectiontime"));
/* 3445 */                     out.write("</td> ");
/*      */                   }
/* 3447 */                   out.write("\n    <td width=\"9%\" class=\"columnheading\" >");
/* 3448 */                   out.print(FormatUtil.getString("am.webclient.camscreen.actions.text"));
/* 3449 */                   out.write("</td>\n</tr>\n");
/*      */                   
/* 3451 */                   Hashtable token = new Hashtable();
/*      */                   
/* 3453 */                   for (int i = 0; i < screenAttribInfo.size(); i++)
/*      */                   {
/* 3455 */                     row = (List)screenAttribInfo.get(i);
/* 3456 */                     if (i % 2 == 0) {
/* 3457 */                       className = "whitegrayborder";
/*      */                     } else {
/* 3459 */                       className = "yellowgrayborder";
/*      */                     }
/*      */                     
/* 3462 */                     boolean newResource = false;
/* 3463 */                     boolean newMBean = false;
/* 3464 */                     boolean addMBeanRow = false;
/* 3465 */                     String date = "Could not be obtained";
/* 3466 */                     String shortDate = "Could not be obtained";
/* 3467 */                     String longFormatDate = "Could not be obtained";
/* 3468 */                     String resourceName4Attrib = "";
/*      */                     try
/*      */                     {
/* 3471 */                       resourceName4Attrib = (String)attribResourceMap.get(row.get(posOfAttribID));
/* 3472 */                       String attribID = (String)row.get(posOfAttribID);
/* 3473 */                       String mBeanName = (String)idVsMBeanAndRes.get(attribID);
/* 3474 */                       if (currentMBeanName == null)
/*      */                       {
/* 3476 */                         currentMBeanName = mBeanName;
/* 3477 */                         newMBean = true;
/*      */                       }
/* 3479 */                       else if (!currentMBeanName.equals(mBeanName))
/*      */                       {
/* 3481 */                         currentMBeanName = mBeanName;
/* 3482 */                         newMBean = true;
/*      */                       }
/* 3484 */                       if (currentResourceName == null)
/*      */                       {
/* 3486 */                         currentResourceName = resourceName4Attrib;
/* 3487 */                         newResource = true;
/*      */                       }
/* 3489 */                       else if (!currentResourceName.equals(resourceName4Attrib))
/*      */                       {
/* 3491 */                         currentResourceName = resourceName4Attrib;
/* 3492 */                         newResource = true;
/*      */                       }
/* 3494 */                       addMBeanRow = (newMBean) || (newResource);
/* 3495 */                       date = String.valueOf(Long.parseLong((String)dcTimeMap.get(row.get(posOfAttribID))));
/* 3496 */                       shortDate = formatDT(date);
/* 3497 */                       longFormatDate = new Date(Long.parseLong(date)).toString();
/*      */                     }
/*      */                     catch (Exception e) {}
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3506 */                     String value = (String)row.get(posOfAttribValue);
/* 3507 */                     if (row.get(posOfAttribType).equals("0"))
/*      */                     {
/* 3509 */                       if (value.equals("-1"))
/*      */                       {
/* 3511 */                         value = FormatUtil.getString("am.webclient.cam.nodata");
/*      */                       }
/*      */                     }
/*      */                     
/*      */ 
/* 3516 */                     out.write(10);
/*      */                     
/* 3518 */                     if (addMBeanRow)
/*      */                     {
/* 3520 */                       if (((String)attribResourceMap.get(row.get(posOfAttribID) + "_RESTYPE")).equals("SNMP"))
/*      */                       {
/*      */ 
/* 3523 */                         out.write("\n<tr>\n       <td height=\"20\" class=\"secondchildnode\" colspan=\"4\"><span class=\"bodytextbold\"><span class=\"bodytext\">(");
/* 3524 */                         out.print(currentResourceName);
/* 3525 */                         out.write(")</span></span></td>\n</tr>\n\n");
/*      */ 
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/*      */ 
/* 3532 */                         out.write("\n\n<tr>\n<td height=\"20\"   class=\"secondchildnode\"  colspan=\"4\" onmouseover=\"ddrivetip(this,event,'");
/* 3533 */                         out.print(addBreakAt((currentMBeanName + " - " + currentResourceName).replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"), 80));
/* 3534 */                         out.write("',null,true,'#000000',300)\" onmouseout=\"hideddrivetip()\" <span class=\"bodytextbold\">");
/* 3535 */                         out.print(addBreakAt(currentMBeanName, 80));
/* 3536 */                         out.write(" <span class=\"availablity-arrow\">&raquo;&nbsp;</span> ");
/* 3537 */                         out.print(getTrimmedText(currentResourceName, 25));
/* 3538 */                         out.write("</span> </td> ");
/* 3539 */                         out.write("\n</tr>\n");
/*      */                       }
/*      */                     }
/*      */                     try
/*      */                     {
/* 3544 */                       StringTokenizer mbean = new StringTokenizer(currentResourceName, "_");
/* 3545 */                       int j = 0;
/* 3546 */                       while (mbean.hasMoreTokens()) {
/* 3547 */                         String t = mbean.nextToken();
/* 3548 */                         token.put(Integer.valueOf(j), t);
/* 3549 */                         j++;
/*      */                       }
/*      */                       
/*      */ 
/* 3553 */                       String attrbId = (String)row.get(posOfAttribID);
/* 3554 */                       String resType = (String)attribResourceMap.get(attrbId + "_RESTYPE");
/* 3555 */                       if ((resType != null) && (resType.equalsIgnoreCase("snmp"))) {
/* 3556 */                         String resourceId = (String)row.get(row.size() - 2);
/* 3557 */                         if ((resourceId != null) && (resourceId.length() > 0)) {
/* 3558 */                           List l = com.adventnet.appmanager.util.DBUtil.getRows("SELECT RESOURCENAME FROM AM_ManagedObject where RESOURCEID=" + resourceId);
/* 3559 */                           if ((l != null) && (l.size() == 1)) {
/* 3560 */                             j = 0;
/* 3561 */                             String actualResourceName = (String)((ArrayList)l.get(0)).get(0);
/* 3562 */                             mbean = new StringTokenizer(actualResourceName, "_");
/* 3563 */                             while (mbean.hasMoreTokens()) {
/* 3564 */                               String t = mbean.nextToken();
/* 3565 */                               token.put(Integer.valueOf(j), t);
/* 3566 */                               j++;
/*      */                             }
/*      */                           }
/*      */                         }
/*      */                       }
/*      */                     } catch (Exception e) {}
/* 3572 */                     String toshow = getTrimmedText(value, 30);
/* 3573 */                     request.setAttribute("toshow", toshow);
/* 3574 */                     request.setAttribute("fullvalue", value);
/* 3575 */                     int len = value.length();
/* 3576 */                     String tooltiptype = (String)row.get(posOfDispType);
/*      */                     
/* 3578 */                     if (tooltiptype.equals("1")) {
/* 3579 */                       tooltiptype = "Counter";
/* 3580 */                       if ((toshow.equals(" ")) || (value.equals(" ")))
/*      */                       {
/* 3582 */                         Map fromMap = new HashMap();
/* 3583 */                         fromMap = (HashMap)com.adventnet.appmanager.cam.CAMServerUtil.collectFirstData;
/* 3584 */                         if (fromMap != null) {
/* 3585 */                           List lst = new ArrayList();
/* 3586 */                           lst = (ArrayList)fromMap.get((String)row.get(posOfAttribID));
/* 3587 */                           if (lst != null) {
/* 3588 */                             request.setAttribute("toshow", lst.get(2));
/* 3589 */                             request.setAttribute("fullvalue", lst.get(3));
/*      */                           }
/*      */                         }
/*      */                       }
/*      */                     } else {
/* 3594 */                       tooltiptype = "Non-Counter";
/*      */                     }
/*      */                     
/* 3597 */                     out.write("\n\n<tr>\n\t<td class=\"");
/* 3598 */                     out.print(className);
/* 3599 */                     out.write("\" onmouseover=\"ddrivetip(this,event,'");
/* 3600 */                     out.print(FormatUtil.getString("am.webclient.snmp.tootipMsg", new String[] { (String)row.get(posOfAttribName), resourceName4Attrib, tooltiptype }));
/* 3601 */                     out.write("',null,true,'#000000','len')\" onmouseout=\"hideddrivetip()\"> <span class=\"bodytext\">");
/* 3602 */                     out.print(getTrimmedText((String)row.get(posOfAttribName), 25));
/* 3603 */                     out.write(" </span></td>\n\n");
/*      */                     
/* 3605 */                     if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 3606 */                       if (len >= 30)
/*      */                       {
/* 3608 */                         out.write(10);
/* 3609 */                         out.write(10);
/* 3610 */                         String breaktext = addBreakAt(value, 50);
/* 3611 */                         out.write("\n     <td class=\"");
/* 3612 */                         out.print(className);
/* 3613 */                         out.write("\" onmouseover=\"ddrivetip(this,event,'");
/* 3614 */                         out.print(value.replaceAll("\\n", " "));
/* 3615 */                         out.write("',null,true,'#000000','len')\" onmouseout=\"hideddrivetip()\" ><span class=\"bodytext\"> ");
/* 3616 */                         if (_jspx_meth_c_005fout_005f24(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                           return;
/* 3618 */                         out.write(" </span></td>\n\n");
/*      */                       }
/*      */                       else {
/* 3621 */                         out.write("\n\n<td class=\"");
/* 3622 */                         out.print(className);
/* 3623 */                         out.write("\" ><span class=\"bodytext\"> ");
/* 3624 */                         if (_jspx_meth_c_005fout_005f25(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                           return;
/* 3626 */                         out.write(" </span></td>\n\n");
/*      */                       }
/*      */                       
/* 3629 */                       out.write("\n\n        <td class=\"");
/* 3630 */                       out.print(className);
/* 3631 */                       out.write("\" title=\"Time : ");
/* 3632 */                       out.print(longFormatDate);
/* 3633 */                       out.write(" Resource : ");
/* 3634 */                       out.print(resourceName4Attrib);
/* 3635 */                       out.write("\"> <span class=\"bodytext\">");
/* 3636 */                       out.print(shortDate);
/* 3637 */                       out.write("</span></td>\n");
/*      */                     } else {
/* 3639 */                       out.write("\n<td colspan=2 class=\"");
/* 3640 */                       out.print(className);
/* 3641 */                       out.write("\"><span class=\"bodytext\">");
/* 3642 */                       out.print(addBreakAt(value, 55));
/* 3643 */                       out.write("</span></td>\n");
/*      */                     }
/* 3645 */                     out.write("\n        <td class=\"");
/* 3646 */                     out.print(className);
/* 3647 */                     out.write("\" >\n        ");
/* 3648 */                     if ((row.get(posOfAttribType).equals("0")) || (row.get(posOfAttribType).equals("1"))) {
/* 3649 */                       if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS")))
/*      */                       {
/* 3651 */                         out.write("\n\n<a  style=\"cursor: pointer;\" onClick=\"javascript:MM_openBrWindow('/jsp/attribute_edit.jsp?resourceid=");
/* 3652 */                         out.print(row.get(posOfResourceID));
/* 3653 */                         out.write("&attributeid=");
/* 3654 */                         out.print(row.get(posOfAttribID));
/* 3655 */                         out.write("&camid=");
/* 3656 */                         out.print(camIDI);
/* 3657 */                         out.write("&haid=");
/* 3658 */                         out.print(haidI);
/* 3659 */                         out.write("&screenid=");
/* 3660 */                         out.print(screenIDI);
/* 3661 */                         out.write("&resourcename=");
/* 3662 */                         out.print(currentResourceName);
/* 3663 */                         out.write("&hostname=");
/* 3664 */                         out.print(token.get(Integer.valueOf(0)));
/* 3665 */                         out.write("&resourcetype=");
/* 3666 */                         out.print(token.get(Integer.valueOf(1)));
/* 3667 */                         out.write("&portno=");
/* 3668 */                         out.print(token.get(Integer.valueOf(2)));
/* 3669 */                         out.write("&attributes=");
/* 3670 */                         out.print(URLEncoder.encode(currentMBeanName + "|" + (String)row.get(1) + "|" + row.get(posOfAttribType)));
/* 3671 */                         out.write("&displayname=");
/* 3672 */                         out.print((String)row.get(posOfAttribName));
/* 3673 */                         out.write("&isfromeditpage=");
/* 3674 */                         out.print("true");
/* 3675 */                         out.write("&resourceid=");
/* 3676 */                         out.print(row.get(posOfResourceID));
/* 3677 */                         out.write("&dispType=");
/* 3678 */                         out.print(row.get(posOfDispType));
/* 3679 */                         out.write("','Personalize','width=390,height=200,screenX=100,screenY=300,scrollbars=yes')\"><img src=\"/images/icon_edit.gif\"  border=\"0\" title='");
/* 3680 */                         out.print(FormatUtil.getString("jmxnotification.edit"));
/* 3681 */                         out.write("'></a>\n");
/*      */                       }
/* 3683 */                       out.write("\n\n    <A class='staticlinks' href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3684 */                       out.print(row.get(posOfResourceID));
/* 3685 */                       out.write("&attributeIDs=");
/* 3686 */                       out.print(row.get(posOfAttribID));
/* 3687 */                       out.write("&attributeToSelect=");
/* 3688 */                       out.print(row.get(posOfAttribID));
/* 3689 */                       out.write("&redirectto=");
/* 3690 */                       out.print(configureThresholdRedirectLink);
/* 3691 */                       out.write("'>\n    <img src=\"/images/icon_associateaction.gif\" title='");
/* 3692 */                       out.print(ALERTCONFIG_TEXT);
/* 3693 */                       out.write("' border=\"0\" ></A>\n\n    ");
/*      */                       
/* 3695 */                       if (row.get(posOfAttribType).equals("0"))
/*      */                       {
/*      */ 
/* 3698 */                         out.write("\n    <a style=\"cursor: pointer;\" onclick=\"showAttribGraph(");
/* 3699 */                         out.print(row.get(posOfAttribID));
/* 3700 */                         out.write(44);
/* 3701 */                         out.write(39);
/* 3702 */                         out.print(getTrimmedText(currentMBeanName, 50).replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"));
/* 3703 */                         out.write("')\" ><img src='/images/icon_linegraph.gif' title='");
/* 3704 */                         out.print(FormatUtil.getString("jmxnotification.showgraph"));
/* 3705 */                         out.write("' border='0' ></a>\n\n\n        ");
/*      */                       }
/*      */                       
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/* 3712 */                       out.write("\n\t\t&nbsp;\n\t");
/*      */                     }
/*      */                     
/*      */ 
/* 3716 */                     out.write("\n</td>\n\n</tr>\n");
/*      */                   }
/*      */                   
/*      */ 
/* 3720 */                   out.write("\n</tr>\n\n<tr>\n\t<td colspan=4  height='25' class=\"tablebottom\"><span class=\"bodytext\">");
/*      */                   
/* 3722 */                   PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3723 */                   _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3724 */                   _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/* 3726 */                   _jspx_th_logic_005fpresent_005f2.setRole("ADMIN");
/* 3727 */                   int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3728 */                   if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                     for (;;) {
/* 3730 */                       out.write("\n       <a href=\"");
/* 3731 */                       out.print(configureLink);
/* 3732 */                       out.write("\" class='staticlinks'>");
/* 3733 */                       out.print(FormatUtil.getString("am.webclient.cam.adddeleteattributes.text"));
/* 3734 */                       out.write("</a> ");
/* 3735 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3736 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3740 */                   if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3741 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                   }
/*      */                   
/* 3744 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3745 */                   out.write("</span></td>\n</tr>\n</table>\n");
/*      */                 }
/*      */                 
/*      */ 
/* 3749 */                 out.write("\n<br>\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 3750 */                 org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/MyField_div.jsp", out, false);
/* 3751 */                 out.write("</td></tr></table>\n");
/*      */                 
/* 3753 */                 if (columnarAttribsPresent)
/*      */                 {
/* 3755 */                   int k = 0;
/* 3756 */                   String titlePrefix = FormatUtil.getString("am.webclient.common.name.text");
/* 3757 */                   for (int i = 0; i < colScreenAttribInfo.size(); i++)
/*      */                   {
/* 3759 */                     out.write("\n\t<div class=\"horTableScroll\">\n\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"99%\" class=\"lrtbdarkborder\">\n\t");
/*      */                     
/* 3761 */                     List temp1 = (List)colScreenAttribInfo.get(i);
/* 3762 */                     if (temp1.size() > 0)
/*      */                     {
/* 3764 */                       Properties tmpProp = (Properties)((List)temp1.get(0)).get(0);
/* 3765 */                       String mbeanName = tmpProp.getProperty("mbeanname");
/* 3766 */                       List h = (List)tmpProp.get("columnnames");
/*      */                       
/* 3768 */                       out.write("\n\t\t<tr>\n\t\t<td height=\"24\" width=\"75%\" class=\"tableheadingbborder\" colspan=\"");
/* 3769 */                       out.print(h.size() + 1);
/* 3770 */                       out.write("\">\n\t\t");
/* 3771 */                       out.print(titlePrefix);
/* 3772 */                       out.write(32);
/* 3773 */                       out.write(58);
/* 3774 */                       out.write(32);
/* 3775 */                       out.print(getTrimmedText(mbeanName, 50));
/* 3776 */                       out.write("</td>\n\t\t</tr>\n\t\t");
/*      */                     }
/*      */                     
/* 3779 */                     int cnt = 0; for (int size = temp1.size(); cnt < size; cnt++)
/*      */                     {
/*      */ 
/* 3782 */                       out.write("\n\t\t<tr><td width=\"100%\" style=\"vertical-align: top;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t");
/*      */                       
/*      */ 
/* 3785 */                       List oneTableList = (List)temp1.get(cnt);
/* 3786 */                       Properties camprops = (Properties)oneTableList.get(0);
/* 3787 */                       List headers = (List)camprops.get("columnnames");
/* 3788 */                       List thresholdPossibleIDs = (List)camprops.get("thresholdpossibleattrids");
/* 3789 */                       if ("snmp table".equals(camprops.get("TableType"))) {
/* 3790 */                         titlePrefix = "SNMP Table Name";
/*      */                       } else {
/* 3792 */                         titlePrefix = FormatUtil.getString("am.webclient.camscreen.titleprefix");
/*      */                       }
/*      */                       
/*      */ 
/* 3796 */                       out.write("\n\t\t\t<tr >\n\t\t     ");
/*      */                       
/* 3798 */                       String attrs = "";
/* 3799 */                       for (int a = 0; a < thresholdPossibleIDs.size(); a++)
/*      */                       {
/* 3801 */                         attrs = attrs + (String)thresholdPossibleIDs.get(a) + ",";
/*      */                       }
/*      */                       
/*      */ 
/* 3805 */                       out.write("\n\t\t<td height=\"24\" width=\"75%\" class=\"secondchildnode\" colspan=\"");
/* 3806 */                       out.print(headers.size());
/* 3807 */                       out.write("\">\n\t\t");
/* 3808 */                       String temp = (String)camprops.get("attrName");
/* 3809 */                       out.write("\n\t\t<span class=\"bodytextbold\">");
/* 3810 */                       out.print(FormatUtil.getString("am.webclient.camscreen.attributegraphs.attribute.text"));
/* 3811 */                       out.write(32);
/* 3812 */                       out.write(58);
/* 3813 */                       out.write(32);
/* 3814 */                       out.print(getTrimmedText(temp, 50));
/* 3815 */                       out.write("</span></td>\n\n\t<td class=\"secondchildnode\" width=\"25%\">\n\n\t");
/*      */                       
/* 3817 */                       if (thresholdPossibleIDs.size() > 0)
/*      */                       {
/*      */ 
/*      */ 
/* 3821 */                         out.write("\n\n\t\t<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3822 */                         out.print(camprops.get("resourceid"));
/* 3823 */                         out.write("&attributeIDs=");
/* 3824 */                         out.print(attrs.substring(0, attrs.length() - 1));
/* 3825 */                         out.write("&attributeToSelect=");
/* 3826 */                         out.print(thresholdPossibleIDs.get(0));
/* 3827 */                         out.write("&redirectto=");
/* 3828 */                         out.print(configureThresholdRedirectLink);
/* 3829 */                         out.write("' class=\"staticlinks\">\n        <img src=\"/images/icon_associateaction.gif\" alt=\"Associate Action\" border=\"0\" align=\"absmiddle\" hspace=\"5\" >");
/* 3830 */                         out.print(ALERTCONFIG_TEXT);
/* 3831 */                         out.write("</a>\n        ");
/*      */                       }
/*      */                       
/*      */ 
/* 3835 */                       out.write("\n\t\t</td>\n\t\t</tr>\n\t        <tr>\n\t\t");
/*      */                       
/* 3837 */                       for (k = 0; k < headers.size(); k++)
/*      */                       {
/*      */ 
/* 3840 */                         out.write("\n\t\t\t\t<td class=\"columnheading\" align=left>\n\t\t\t\t");
/* 3841 */                         out.print(headers.get(k));
/* 3842 */                         out.write("\n\t\t\t\t</td>\n\t\t\t");
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/* 3847 */                       out.write("\n\t\t<td class=\"columnheading\" width=\"19%\">&nbsp;</td>\n\t        </tr>\n\t        ");
/*      */                       
/* 3849 */                       for (int j = 1; j < oneTableList.size(); j++)
/*      */                       {
/* 3851 */                         String bgclass = "class=\"whitegrayrightalign-reports\"";
/* 3852 */                         if (j % 2 != 0)
/*      */                         {
/* 3854 */                           bgclass = "class=\"whitegrayrightalign-reports\"";
/*      */                         }
/*      */                         
/* 3857 */                         out.write("\n\t        \t\t<tr>\n\t        \t\t");
/*      */                         
/* 3859 */                         for (int l = 0; l < headers.size(); l++)
/*      */                         {
/*      */ 
/* 3862 */                           out.write("\n\t\t\t\t\t<td height=\"28\"  ");
/* 3863 */                           out.print(bgclass);
/* 3864 */                           out.write(" align=\"left\" title=\"");
/* 3865 */                           out.print(formatDT((String)camprops.get("dctime")));
/* 3866 */                           out.write("\">\n\t\t\t\t\t\t<div  style=\"WORD-BREAK:BREAK-ALL; word-wrap: break-word; width:100px; white-space:-moz-pre-wrap; white-space: normal;\">");
/* 3867 */                           out.print(((List)oneTableList.get(j)).get(l));
/* 3868 */                           out.write("</div>\n\t\t\t\t\t</td>\n\t\t\t\t\t");
/*      */                         }
/*      */                         
/*      */ 
/* 3872 */                         out.write("\n\n\t\t\t<td ");
/* 3873 */                         out.print(bgclass);
/* 3874 */                         out.write(" width=\"19%\">&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t");
/*      */                       }
/*      */                       
/*      */ 
/* 3878 */                       out.write("\n\t</table></td></tr>\n\t");
/*      */                     }
/*      */                     
/*      */ 
/* 3882 */                     out.write("\n<tr>\n        <td colspan=");
/* 3883 */                     out.print(k + 1);
/* 3884 */                     out.write("  height='25' class=\"tablebottom\"><span class=\"bodytext\">");
/*      */                     
/* 3886 */                     PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3887 */                     _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3888 */                     _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                     
/* 3890 */                     _jspx_th_logic_005fpresent_005f3.setRole("ADMIN");
/* 3891 */                     int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3892 */                     if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                       for (;;) {
/* 3894 */                         out.write("\n       <a href=\"");
/* 3895 */                         out.print(configureLink);
/* 3896 */                         out.write("\" class='staticlinks'>");
/* 3897 */                         out.print(FormatUtil.getString("am.webclient.cam.adddeleteattributes.text"));
/* 3898 */                         out.write("</a> ");
/* 3899 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3900 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3904 */                     if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3905 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                     }
/*      */                     
/* 3908 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3909 */                     out.write("</span></td>\n</tr>\n\n\n</table><br></div>\n");
/*      */                   }
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/* 3915 */               out.write("\n<br><br>\n\n<!-- Added graphs to the Normal Screen -->\n<div id=\"status\" style='Display:none'>");
/* 3916 */               out.print(FormatUtil.getString("am.webclient.gengraph.text"));
/* 3917 */               out.write("&nbsp;&nbsp;<img src=\"../images/icon_cogwheel.gif\"></div>\n<div id=\"attributegraphs\"></div>\n\n\n\n<script>\nmyOnLoad();\nfunction myOnLoad()\n{\n/**\t");
/* 3918 */               if ((request.getParameter("type") != null) && (request.getParameter("type").equals("JBOSS-server"))) {
/* 3919 */                 out.write("\n\tmyOnJbossLoad();\n\t");
/*      */               }
/* 3921 */               out.write("\n\t**/\n\tattributegraphs();\n}\nvar http1;\nfunction attributegraphs()\n{\n        document.getElementById(\"status\").style.display='block';\n        URL='/jsp/cam_graphs.jsp?camIDI=");
/* 3922 */               out.print(camIDI);
/* 3923 */               out.write("&haidI=");
/* 3924 */               out.print(haidI);
/* 3925 */               out.write("&screenIDI=");
/* 3926 */               out.print(screenIDI);
/* 3927 */               out.write("&isfromresourcepage=");
/* 3928 */               out.print(isFromResourcePage);
/* 3929 */               out.write("';\n        http1=getHTTPObject();\n        http1.open(\"GET\", URL, true);\n        //http1.onreadystatechange = new Function('if(http1.readyState == 4 && http1.status == 200){document.getElementById(\"status\").innerHTML =\"&nbsp;\",document.getElementById(\"attributegraphs\").innerHTML = http1.responseText;}' );\n\thttp1.onreadystatechange =handleResponse1;\n        http1.send(null);\n}\n\nfunction handleResponse1()\n{\n        if(http1.readyState == 4)\n        {\n                var result = http1.responseText;\n\t\tdocument.getElementById(\"status\").innerHTML =\"&nbsp;\"\n                document.getElementById(\"attributegraphs\").innerHTML = result;\n                document.getElementById(\"attributegraphs\").style.display='block';\n        //      alert('Div similarmonitor display' + document.getElementById(\"multimonitors\").checked);\n        }\n}\n\n\nfunction subAddCustom(linkS) {\n\tlinkS.href = \"");
/* 3930 */               out.print(configureLink);
/* 3931 */               out.write("\";\n\treturn true;\n}\n\n$(document).ready(function(){\n\n\tvar customFieldsHash = document.location.hash;\n\n\tcustomFieldsHash = customFieldsHash.split(\"/\")\n\n\tif(customFieldsHash.length > 1)\t");
/* 3932 */               out.write("\n\t{\n\t\t");
/* 3933 */               if (_jspx_meth_c_005fif_005f8(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 3935 */               out.write(10);
/* 3936 */               out.write(9);
/* 3937 */               out.write(9);
/* 3938 */               if (_jspx_meth_c_005fif_005f9(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 3940 */               out.write("\n\t\tgetCustomFields('");
/* 3941 */               if (_jspx_meth_c_005fout_005f28(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 3943 */               out.write("','noalarms',false,customFieldsHash[1],true)\t");
/* 3944 */               out.write("\n\t}\n\n});\n</script>\n\n\n");
/* 3945 */               out.write(10);
/* 3946 */               out.write(10);
/* 3947 */               out.write(10);
/* 3948 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 3949 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3952 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3953 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3956 */           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3957 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */           }
/*      */           
/* 3960 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 3961 */           out.write(10);
/* 3962 */           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 3964 */           out.write(10);
/*      */           
/* 3966 */           PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 3967 */           _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 3968 */           _jspx_th_tiles_005fput_005f5.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 3970 */           _jspx_th_tiles_005fput_005f5.setName("ServerLeftArea");
/*      */           
/* 3972 */           _jspx_th_tiles_005fput_005f5.setType("string");
/* 3973 */           int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 3974 */           if (_jspx_eval_tiles_005fput_005f5 != 0) {
/* 3975 */             if (_jspx_eval_tiles_005fput_005f5 != 1) {
/* 3976 */               out = _jspx_page_context.pushBody();
/* 3977 */               _jspx_th_tiles_005fput_005f5.setBodyContent((BodyContent)out);
/* 3978 */               _jspx_th_tiles_005fput_005f5.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 3981 */               out.write(10);
/* 3982 */               out.write(9);
/* 3983 */               out.write("<!--$Id$-->\n\n<script>\nfunction reconfigure()\n{\n\ttoggleDiv('Reconfigure');\n\t//hideDiv('snapshot');\n}\n</script>\n");
/*      */               
/* 3985 */               String resourceid = request.getParameter("resourceid");
/* 3986 */               String configure_link = (String)request.getAttribute("configurelink");
/* 3987 */               String type = "SAP";
/* 3988 */               String Availability_attribIDs = "3702";
/* 3989 */               String Health_attribIDs = "3703";
/* 3990 */               if ((request.getAttribute("type") != null) && (((String)request.getAttribute("type")).equalsIgnoreCase("SAP-CCMS")))
/*      */               {
/* 3992 */                 type = "SAP-CCMS";
/* 3993 */                 Availability_attribIDs = "4000";
/* 3994 */                 Health_attribIDs = "4001";
/*      */               }
/*      */               
/* 3997 */               out.write("\n\n<script language=\"JavaScript\">\n\tfunction confirmDelete()\n \t {\n        var s = confirm(\"");
/* 3998 */               out.print(FormatUtil.getString("am.webclient.urlmonitor.jsalertformonitor.text"));
/* 3999 */               out.write("\");\n        if (s)\n        document.location.href=\"/deleteMO.do?method=deleteMO&type=");
/* 4000 */               out.print(type);
/* 4001 */               out.write("&select=");
/* 4002 */               out.print(resourceid);
/* 4003 */               out.write("\";\n\t }\n\t        function confirmManage()\n \t {\n  var s = confirm(\"");
/* 4004 */               out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 4005 */               out.write("\");\n  if (s)\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 4006 */               if (_jspx_meth_c_005fout_005f29(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 4008 */               out.write("\";\n\t }\n\n         function confirmUnManage()\n \t {\n        \t  var show_msg=\"false\";\n              var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 4009 */               out.print(request.getParameter("resourceid"));
/* 4010 */               out.write("; //No i18n\n              $.ajax({\n                type:'POST', //No i18n\n                url:url,\n                async:false,\n                success: function(data)\n                {\n                  show_msg=data\n                }\n              });\n              if(show_msg.indexOf(\"true\")>-1)\n              {\n                  alert(\"");
/* 4011 */               out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 4012 */               out.write("\");\n\t\t\t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 4013 */               if (_jspx_meth_c_005fout_005f30(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 4015 */               out.write("\";\n             }\n           else { \n        \n        var s = confirm(\"");
/* 4016 */               out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 4017 */               out.write("\");\n        if (s){\n      \tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 4018 */               if (_jspx_meth_c_005fout_005f31(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 4020 */               out.write("\"; //No I18N\n\t\t\t  }\n\t       } \n\t }\n</script>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td>\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"   class=\"leftmnutables\">\n\n  <tr>\n    <td class=\"leftlinksheading\">");
/* 4021 */               out.print(FormatUtil.getString("am.webclient.jboss.quicklinks.text"));
/* 4022 */               out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"leftlinkstd\" >\n  ");
/*      */               
/* 4024 */               ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4025 */               _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 4026 */               _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_tiles_005fput_005f5);
/* 4027 */               int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 4028 */               if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                 for (;;) {
/* 4030 */                   out.write(10);
/* 4031 */                   out.write(32);
/* 4032 */                   out.write(32);
/*      */                   
/* 4034 */                   WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4035 */                   _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 4036 */                   _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                   
/* 4038 */                   _jspx_th_c_005fwhen_005f3.setTest("${(param.method == 'showdetails' && param.all!='true') || param.method=='showCCMSDetails'}");
/* 4039 */                   int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 4040 */                   if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                     for (;;) {
/* 4042 */                       out.write("\n         ");
/* 4043 */                       out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 4044 */                       out.write(10);
/* 4045 */                       out.write(32);
/* 4046 */                       out.write(32);
/* 4047 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 4048 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4052 */                   if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 4053 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                   }
/*      */                   
/* 4056 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 4057 */                   out.write(10);
/* 4058 */                   out.write(32);
/* 4059 */                   out.write(32);
/*      */                   
/* 4061 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4062 */                   _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 4063 */                   _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 4064 */                   int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 4065 */                   if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                     for (;;) {
/* 4067 */                       out.write("\n\t<a href=\"/showresource.do?resourceid=");
/* 4068 */                       if (_jspx_meth_c_005fout_005f32(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/*      */                         return;
/* 4070 */                       out.write("&method=showResourceForResourceID");
/* 4071 */                       if (_jspx_meth_c_005fout_005f33(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/*      */                         return;
/* 4073 */                       out.write("\"\n      class=\"new-left-links\">");
/* 4074 */                       out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 4075 */                       out.write("</a>\n  ");
/* 4076 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 4077 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4081 */                   if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 4082 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                   }
/*      */                   
/* 4085 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 4086 */                   out.write(10);
/* 4087 */                   out.write(32);
/* 4088 */                   out.write(32);
/* 4089 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 4090 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4094 */               if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 4095 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */               }
/*      */               
/* 4098 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 4099 */               out.write("\n  </td>\n  </tr>\n  <!--Alert configuration should be enabled for Admin and Demo users alone-->\n  ");
/*      */               
/* 4101 */               IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4102 */               _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 4103 */               _jspx_th_c_005fif_005f10.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 4105 */               _jspx_th_c_005fif_005f10.setTest("${!empty ADMIN || !empty DEMO}");
/* 4106 */               int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 4107 */               if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                 for (;;) {
/* 4109 */                   out.write("\n   <tr>\n          <td width=\"88%\" class=\"leftlinkstd\">\n       \t");
/*      */                   
/* 4111 */                   ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4112 */                   _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 4113 */                   _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fif_005f10);
/* 4114 */                   int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 4115 */                   if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                     for (;;) {
/* 4117 */                       out.write("\n         ");
/*      */                       
/* 4119 */                       WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4120 */                       _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 4121 */                       _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                       
/* 4123 */                       _jspx_th_c_005fwhen_005f4.setTest("${param.all=='true'}");
/* 4124 */                       int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 4125 */                       if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                         for (;;) {
/* 4127 */                           out.write("\n                ");
/* 4128 */                           out.print(ALERTCONFIG_TEXT);
/* 4129 */                           out.write("\n         ");
/* 4130 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 4131 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4135 */                       if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 4136 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                       }
/*      */                       
/* 4139 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 4140 */                       out.write("\n  \t");
/*      */                       
/* 4142 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4143 */                       _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 4144 */                       _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 4145 */                       int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 4146 */                       if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                         for (;;) {
/* 4148 */                           out.write("\n          <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 4149 */                           if (_jspx_meth_c_005fout_005f34(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/*      */                             return;
/* 4151 */                           out.write("\"\n            class=\"new-left-links\">");
/* 4152 */                           out.print(ALERTCONFIG_TEXT);
/* 4153 */                           out.write("</a>\n              ");
/* 4154 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 4155 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4159 */                       if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 4160 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                       }
/*      */                       
/* 4163 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 4164 */                       out.write("\n\t      ");
/* 4165 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 4166 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4170 */                   if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 4171 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                   }
/*      */                   
/* 4174 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 4175 */                   out.write("\n            </td>\n        </tr>\n  ");
/* 4176 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 4177 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4181 */               if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 4182 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */               }
/*      */               
/* 4185 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 4186 */               out.write("\n <!-- Edit link should be enabled for Enterprise Edition when SSO is enabled -->\n  ");
/*      */               
/* 4188 */               PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4189 */               _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 4190 */               _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 4192 */               _jspx_th_logic_005fpresent_005f4.setRole("ENTERPRISEADMIN");
/* 4193 */               int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 4194 */               if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                 for (;;) {
/* 4196 */                   out.write("\n\t<tr>\n\t\t<td class=\"leftlinkstd\">\n\t\t<a target=\"mas_window\" href=\"/showresource.do?resourceid=");
/* 4197 */                   out.print(request.getParameter("resourceid"));
/* 4198 */                   out.write("&method=showdetails&type=");
/* 4199 */                   out.print(request.getParameter("type"));
/* 4200 */                   out.write("&moname=");
/* 4201 */                   out.print(request.getParameter("moname"));
/* 4202 */                   out.write("&viewType=showResourceTypes&aam_jump=true&editPage=true\" class=\"new-left-links\">");
/* 4203 */                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 4204 */                   out.write("</a>\n\t\t</td>\n\t</tr>\n  ");
/* 4205 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 4206 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4210 */               if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 4211 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */               }
/*      */               
/* 4214 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 4215 */               out.write("\n<!-- Edit link should be enabled for ADMIN and DEMO Users alone -->\n");
/*      */               
/* 4217 */               IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4218 */               _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 4219 */               _jspx_th_c_005fif_005f11.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 4221 */               _jspx_th_c_005fif_005f11.setTest("${!empty ADMIN || !empty DEMO}");
/* 4222 */               int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 4223 */               if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                 for (;;) {
/* 4225 */                   out.write("\n  <tr>\n    <td class=\"leftlinkstd\">\n ");
/*      */                   
/* 4227 */                   ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4228 */                   _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 4229 */                   _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_c_005fif_005f11);
/* 4230 */                   int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 4231 */                   if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                     for (;;) {
/* 4233 */                       out.write("\n    ");
/*      */                       
/* 4235 */                       WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4236 */                       _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 4237 */                       _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                       
/* 4239 */                       _jspx_th_c_005fwhen_005f5.setTest("${uri=='/reconfigure.do'}");
/* 4240 */                       int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 4241 */                       if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                         for (;;) {
/* 4243 */                           out.write("\n        ");
/* 4244 */                           out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 4245 */                           out.write(10);
/* 4246 */                           out.write(32);
/* 4247 */                           out.write(32);
/* 4248 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 4249 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4253 */                       if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 4254 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                       }
/*      */                       
/* 4257 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 4258 */                       out.write(10);
/* 4259 */                       out.write(32);
/* 4260 */                       out.write(32);
/*      */                       
/* 4262 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4263 */                       _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 4264 */                       _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/* 4265 */                       int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 4266 */                       if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                         for (;;) {
/* 4268 */                           out.write("\n    <a href=\"javascript:reconfigure()\"\n      class=\"new-left-links\">");
/* 4269 */                           out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 4270 */                           out.write("</a>\n  ");
/* 4271 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 4272 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4276 */                       if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 4277 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                       }
/*      */                       
/* 4280 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 4281 */                       out.write(10);
/* 4282 */                       out.write(32);
/* 4283 */                       out.write(32);
/* 4284 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 4285 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4289 */                   if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 4290 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                   }
/*      */                   
/* 4293 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 4294 */                   out.write("\n  </td>\n  </tr>\n");
/* 4295 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 4296 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4300 */               if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 4301 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */               }
/*      */               
/* 4304 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 4305 */               out.write(10);
/* 4306 */               out.write(10);
/*      */               
/* 4308 */               IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4309 */               _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 4310 */               _jspx_th_c_005fif_005f12.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 4312 */               _jspx_th_c_005fif_005f12.setTest("${!empty ADMIN || !empty DEMO}");
/* 4313 */               int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 4314 */               if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                 for (;;) {
/* 4316 */                   out.write(10);
/*      */                   
/* 4318 */                   NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4319 */                   _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 4320 */                   _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f12);
/*      */                   
/* 4322 */                   _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 4323 */                   int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 4324 */                   if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                     for (;;) {
/* 4326 */                       out.write("\n  <tr>\n    <td class=\"leftlinkstd\" >\n     <A href=\"javascript:confirmDelete();\" class=\"new-left-links\">");
/* 4327 */                       out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 4328 */                       out.write("</A></td>\n  \t</td>\n  </tr>\n");
/* 4329 */                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 4330 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4334 */                   if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 4335 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                   }
/*      */                   
/* 4338 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 4339 */                   out.write(10);
/*      */                   
/* 4341 */                   PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4342 */                   _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 4343 */                   _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fif_005f12);
/*      */                   
/* 4345 */                   _jspx_th_logic_005fpresent_005f5.setRole("DEMO");
/* 4346 */                   int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 4347 */                   if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                     for (;;) {
/* 4349 */                       out.write("\n\n<td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 4350 */                       out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 4351 */                       out.write("</a></td>\n\n");
/* 4352 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 4353 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4357 */                   if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 4358 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                   }
/*      */                   
/* 4361 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 4362 */                   out.write("\n\n  </tr>\n  ");
/* 4363 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 4364 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4368 */               if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 4369 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */               }
/*      */               
/* 4372 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 4373 */               out.write(10);
/*      */               
/* 4375 */               IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4376 */               _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 4377 */               _jspx_th_c_005fif_005f13.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 4379 */               _jspx_th_c_005fif_005f13.setTest("${!empty ADMIN || !empty DEMO || !empty OPERATOR}");
/* 4380 */               int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 4381 */               if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                 for (;;) {
/* 4383 */                   out.write("\n  <tr>\n  ");
/*      */                   
/* 4385 */                   if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                   {
/*      */ 
/* 4388 */                     out.write("\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\">");
/* 4389 */                     out.print(FormatUtil.getString("Manage"));
/* 4390 */                     out.write("</A></td>\n    ");
/*      */ 
/*      */                   }
/*      */                   else
/*      */                   {
/*      */ 
/* 4396 */                     out.write("\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\">");
/* 4397 */                     out.print(FormatUtil.getString("UnManage"));
/* 4398 */                     out.write("</A></td>\n    ");
/*      */                   }
/*      */                   
/*      */ 
/* 4402 */                   out.write("\n  </tr>\n  ");
/* 4403 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 4404 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4408 */               if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 4409 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */               }
/*      */               
/* 4412 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 4413 */               out.write("\n\n\n\n");
/*      */               
/* 4415 */               if ((configure_link != null) && (!configure_link.equals("null")))
/*      */               {
/*      */ 
/* 4418 */                 out.write("\n\n   ");
/*      */                 
/* 4420 */                 IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4421 */                 _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 4422 */                 _jspx_th_c_005fif_005f14.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                 
/* 4424 */                 _jspx_th_c_005fif_005f14.setTest("${!empty ADMIN || !empty DEMO}");
/* 4425 */                 int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 4426 */                 if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                   for (;;) {
/* 4428 */                     out.write("\n    <tr>\n    <td class=\"leftlinkstd\" >\n\n    \t<a href=\"");
/* 4429 */                     out.print(request.getAttribute("configurelink"));
/* 4430 */                     out.write("\" onclick=\"javascript:return subAddCustom(this);\" class=\"new-left-links\">\n    \t  ");
/* 4431 */                     out.print(FormatUtil.getString("am.webclient.common.addcustomattributes.text"));
/* 4432 */                     out.write("\n    \t</a>\n    </td>\n  </tr>\n   ");
/* 4433 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 4434 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 4438 */                 if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 4439 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                 }
/*      */                 
/* 4442 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 4443 */                 out.write("\n   ");
/*      */               }
/*      */               
/*      */ 
/* 4447 */               out.write("\n\n  ");
/*      */               
/* 4449 */               IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4450 */               _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 4451 */               _jspx_th_c_005fif_005f15.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 4453 */               _jspx_th_c_005fif_005f15.setTest("${!empty ADMIN  && type!='SAP-CCMS'}");
/* 4454 */               int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 4455 */               if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                 for (;;) {
/* 4457 */                   out.write("\n  \t   \t<tr>\n  \t       \t <td colspan=\"2\" class=\"leftlinkstd\">\n  \t       \t ");
/* 4458 */                   out.print(com.adventnet.appmanager.fault.FaultUtil.getAlertTemplateURL(request.getParameter("resourceid"), request.getParameter("name"), "SAP", "SAP Server"));
/* 4459 */                   out.write("\n  \t       \t </td>\n  \t      \t</tr>\n  ");
/* 4460 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 4461 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4465 */               if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 4466 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */               }
/*      */               
/* 4469 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4470 */               out.write("\n   ");
/*      */               
/* 4472 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4473 */               _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 4474 */               _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 4476 */               _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 4477 */               int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 4478 */               if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                 for (;;) {
/* 4480 */                   out.write("\n    ");
/*      */                   
/* 4482 */                   String resourceid_poll = request.getParameter("resourceid");
/* 4483 */                   String resourcetype_poll = request.getParameter("type");
/*      */                   
/* 4485 */                   out.write("\n      <tr>\n      <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n      <a href=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 4486 */                   out.print(resourceid_poll);
/* 4487 */                   out.write("&resourcetype=");
/* 4488 */                   out.print(resourcetype_poll);
/* 4489 */                   out.write("\" class=\"new-left-links\"> ");
/* 4490 */                   out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 4491 */                   out.write("</a></td>\n    </tr>\n    ");
/* 4492 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 4493 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4497 */               if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 4498 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */               }
/*      */               
/* 4501 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 4502 */               out.write("\n    ");
/*      */               
/* 4504 */               PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4505 */               _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 4506 */               _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 4508 */               _jspx_th_logic_005fpresent_005f6.setRole("DEMO");
/* 4509 */               int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 4510 */               if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                 for (;;) {
/* 4512 */                   out.write("\n          <tr>\n          <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n          <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 4513 */                   out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 4514 */                   out.write("</a></td>\n          </td>\n    ");
/* 4515 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 4516 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4520 */               if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 4521 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */               }
/*      */               
/* 4524 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 4525 */               out.write("\n    ");
/* 4526 */               out.write("<!-- $Id$-->\n\n\n  \n");
/*      */               
/* 4528 */               if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */               {
/* 4530 */                 Map<APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 4531 */                 String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                 
/* 4533 */                 String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 4534 */                 String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 4535 */                 if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                 {
/* 4537 */                   if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                   {
/*      */ 
/* 4540 */                     out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 4541 */                     out.print(ciInfoUrl);
/* 4542 */                     out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 4543 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 4544 */                     out.write("</a></td>");
/* 4545 */                     out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 4546 */                     out.print(ciRLUrl);
/* 4547 */                     out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 4548 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 4549 */                     out.write("</a></td>");
/* 4550 */                     out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                   }
/* 4554 */                   else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                   {
/*      */ 
/* 4557 */                     out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 4558 */                     out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 4559 */                     out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 4560 */                     out.print(ciInfoUrl);
/* 4561 */                     out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 4562 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 4563 */                     out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 4564 */                     out.print(ciRLUrl);
/* 4565 */                     out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 4566 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 4567 */                     out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                   }
/*      */                 }
/*      */               }
/*      */               
/* 4572 */               out.write("\n \n \n\n");
/* 4573 */               out.write("\n  </table>\n");
/*      */               
/*      */ 
/* 4576 */               ArrayList attribIDs = new ArrayList();
/* 4577 */               ArrayList resIDs = new ArrayList();
/* 4578 */               resIDs.add(resourceid);
/* 4579 */               attribIDs.add(Availability_attribIDs);
/* 4580 */               attribIDs.add(Health_attribIDs);
/* 4581 */               Properties alert = getStatus(resIDs, attribIDs);
/*      */               
/* 4583 */               out.write("\n</td>\n  </tr>\n  <tr>\n  <td>&nbsp;\n\n  </td>\n  </tr>\n  <tr>\n    <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 4584 */               out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 4585 */               out.write("</td>\n  </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4586 */               if (_jspx_meth_c_005fout_005f35(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 4588 */               out.write("&attributeid=");
/* 4589 */               out.print(Health_attribIDs);
/* 4590 */               out.write("')\" class=\"new-left-links\">");
/* 4591 */               out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 4592 */               out.write("</a></td>\n        <td><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4593 */               if (_jspx_meth_c_005fout_005f36(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 4595 */               out.write("&attributeid=");
/* 4596 */               out.print(Health_attribIDs);
/* 4597 */               out.write("')\">");
/* 4598 */               out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + Health_attribIDs)));
/* 4599 */               out.write("</a></td>\n   </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4600 */               if (_jspx_meth_c_005fout_005f37(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 4602 */               out.write("&attributeid=");
/* 4603 */               out.print(Availability_attribIDs);
/* 4604 */               out.write("')\" class=\"new-left-links\">");
/* 4605 */               out.print(FormatUtil.getString("Availability"));
/* 4606 */               out.write("</a></td>\n    <td width=\"51%\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4607 */               if (_jspx_meth_c_005fout_005f38(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 4609 */               out.write("&attributeid=");
/* 4610 */               out.print(Availability_attribIDs);
/* 4611 */               out.write("')\">\n    ");
/* 4612 */               out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + Availability_attribIDs)));
/* 4613 */               out.write("</a>\n </td>\n  </tr>\n</table></td>\n  </tr>\n    </tr>\n  <tr>\n  <td>&nbsp;\n\n  </td>\n  </tr>\n\n<br>\n");
/* 4614 */               out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */               
/*      */ 
/*      */ 
/* 4618 */               boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 4619 */               if (EnterpriseUtil.isIt360MSPEdition)
/*      */               {
/* 4621 */                 showAssociatedBSG = false;
/*      */                 
/*      */ 
/*      */ 
/* 4625 */                 CustomerManagementAPI.getInstance();Properties assBsgSiteProp = CustomerManagementAPI.getSiteProp(request);
/* 4626 */                 CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 4627 */                 String loginName = request.getUserPrincipal().getName();
/* 4628 */                 CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                 
/* 4630 */                 if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                 {
/* 4632 */                   showAssociatedBSG = true;
/*      */                 }
/*      */               }
/* 4635 */               String monitorType = request.getParameter("type");
/* 4636 */               ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 4637 */               boolean mon = conf1.isConfMonitor(monitorType);
/* 4638 */               if (showAssociatedBSG)
/*      */               {
/* 4640 */                 Hashtable associatedmgs = new Hashtable();
/* 4641 */                 String resId = request.getParameter("resourceid");
/* 4642 */                 request.setAttribute("associatedmgs", com.adventnet.appmanager.fault.FaultUtil.getAdminAssociatedMG(resId, request));
/* 4643 */                 if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                 {
/* 4645 */                   mon = false;
/*      */                 }
/*      */                 
/* 4648 */                 if (!mon)
/*      */                 {
/* 4650 */                   out.write(10);
/* 4651 */                   out.write(10);
/*      */                   
/* 4653 */                   IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4654 */                   _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 4655 */                   _jspx_th_c_005fif_005f16.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                   
/* 4657 */                   _jspx_th_c_005fif_005f16.setTest("${!empty associatedmgs}");
/* 4658 */                   int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 4659 */                   if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                     for (;;) {
/* 4661 */                       out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 4662 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 4663 */                       out.write("</td>\n        </tr>\n        ");
/*      */                       
/* 4665 */                       ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4666 */                       _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 4667 */                       _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f16);
/*      */                       
/* 4669 */                       _jspx_th_c_005fforEach_005f0.setVar("ha");
/*      */                       
/* 4671 */                       _jspx_th_c_005fforEach_005f0.setItems("${associatedmgs}");
/*      */                       
/* 4673 */                       _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 4674 */                       int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                       try {
/* 4676 */                         int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 4677 */                         if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                           for (;;) {
/* 4679 */                             out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 4680 */                             if (_jspx_meth_c_005fout_005f39(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4738 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 4739 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 4682 */                             out.write("&method=showApplication\" title=\"");
/* 4683 */                             if (_jspx_meth_c_005fout_005f40(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4738 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 4739 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 4685 */                             out.write("\"  class=\"new-left-links\">\n         ");
/* 4686 */                             if (_jspx_meth_c_005fset_005f10(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4738 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 4739 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 4688 */                             out.write("\n    \t");
/* 4689 */                             out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 4690 */                             out.write("\n         </a></td>\n        <td>");
/*      */                             
/* 4692 */                             PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4693 */                             _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 4694 */                             _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                             
/* 4696 */                             _jspx_th_logic_005fpresent_005f7.setRole("ADMIN");
/* 4697 */                             int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 4698 */                             if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                               for (;;) {
/* 4700 */                                 out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 4701 */                                 if (_jspx_meth_c_005fout_005f42(_jspx_th_logic_005fpresent_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4738 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 4739 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 4703 */                                 out.write(39);
/* 4704 */                                 out.write(44);
/* 4705 */                                 out.write(39);
/* 4706 */                                 out.print(resId);
/* 4707 */                                 out.write(39);
/* 4708 */                                 out.write(44);
/* 4709 */                                 out.write(39);
/* 4710 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 4711 */                                 out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 4712 */                                 out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 4713 */                                 out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 4714 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 4715 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4719 */                             if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 4720 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4738 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 4739 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 4723 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 4724 */                             out.write("</td>\n        </tr>\n\t");
/* 4725 */                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 4726 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 4730 */                         if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4738 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 4739 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/*      */                       }
/*      */                       catch (Throwable _jspx_exception)
/*      */                       {
/*      */                         for (;;)
/*      */                         {
/* 4734 */                           int tmp16823_16822 = 0; int[] tmp16823_16820 = _jspx_push_body_count_c_005fforEach_005f0; int tmp16825_16824 = tmp16823_16820[tmp16823_16822];tmp16823_16820[tmp16823_16822] = (tmp16825_16824 - 1); if (tmp16825_16824 <= 0) break;
/* 4735 */                           out = _jspx_page_context.popBody(); }
/* 4736 */                         _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                       } finally {
/* 4738 */                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 4739 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                       }
/* 4741 */                       out.write("\n      </table>\n ");
/* 4742 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 4743 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4747 */                   if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 4748 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                   }
/*      */                   
/* 4751 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 4752 */                   out.write(10);
/* 4753 */                   out.write(32);
/*      */                   
/* 4755 */                   IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4756 */                   _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 4757 */                   _jspx_th_c_005fif_005f17.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                   
/* 4759 */                   _jspx_th_c_005fif_005f17.setTest("${empty associatedmgs}");
/* 4760 */                   int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 4761 */                   if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                     for (;;) {
/* 4763 */                       out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 4764 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 4765 */                       out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 4766 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 4767 */                       out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 4768 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 4769 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4773 */                   if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 4774 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */                   }
/*      */                   
/* 4777 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 4778 */                   out.write(10);
/* 4779 */                   out.write(32);
/* 4780 */                   out.write(10);
/*      */ 
/*      */                 }
/* 4783 */                 else if (mon)
/*      */                 {
/*      */ 
/*      */ 
/* 4787 */                   out.write(10);
/*      */                   
/* 4789 */                   IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4790 */                   _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 4791 */                   _jspx_th_c_005fif_005f18.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                   
/* 4793 */                   _jspx_th_c_005fif_005f18.setTest("${!empty associatedmgs}");
/* 4794 */                   int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 4795 */                   if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */                     for (;;) {
/* 4797 */                       out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b>");
/* 4798 */                       if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_c_005fif_005f18, _jspx_page_context))
/*      */                         return;
/* 4800 */                       out.write("</b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                       
/* 4802 */                       ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4803 */                       _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 4804 */                       _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f18);
/*      */                       
/* 4806 */                       _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                       
/* 4808 */                       _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                       
/* 4810 */                       _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 4811 */                       int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                       try {
/* 4813 */                         int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 4814 */                         if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                           for (;;) {
/* 4816 */                             out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 4817 */                             if (_jspx_meth_c_005fout_005f43(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4878 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 4879 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 4819 */                             out.write("&method=showApplication\" title=\"");
/* 4820 */                             if (_jspx_meth_c_005fout_005f44(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4878 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 4879 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 4822 */                             out.write("\"  class=\"staticlinks\">\n         ");
/* 4823 */                             if (_jspx_meth_c_005fset_005f11(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4878 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 4879 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 4825 */                             out.write("\n    \t");
/* 4826 */                             out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 4827 */                             out.write("</a></span>\t\n\t\t ");
/*      */                             
/* 4829 */                             PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4830 */                             _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 4831 */                             _jspx_th_logic_005fpresent_005f8.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                             
/* 4833 */                             _jspx_th_logic_005fpresent_005f8.setRole("ADMIN");
/* 4834 */                             int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 4835 */                             if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                               for (;;) {
/* 4837 */                                 out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 4838 */                                 if (_jspx_meth_c_005fout_005f46(_jspx_th_logic_005fpresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4878 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 4879 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 4840 */                                 out.write(39);
/* 4841 */                                 out.write(44);
/* 4842 */                                 out.write(39);
/* 4843 */                                 out.print(resId);
/* 4844 */                                 out.write(39);
/* 4845 */                                 out.write(44);
/* 4846 */                                 out.write(39);
/* 4847 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 4848 */                                 out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 4849 */                                 out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 4850 */                                 out.write("\"  title=\"");
/* 4851 */                                 if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_logic_005fpresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4878 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 4879 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 4853 */                                 out.write("\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 4854 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 4855 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4859 */                             if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 4860 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4878 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 4879 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 4863 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 4864 */                             out.write("\n\n\t\t \t");
/* 4865 */                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 4866 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 4870 */                         if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4878 */                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 4879 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                         }
/*      */                       }
/*      */                       catch (Throwable _jspx_exception)
/*      */                       {
/*      */                         for (;;)
/*      */                         {
/* 4874 */                           int tmp17849_17848 = 0; int[] tmp17849_17846 = _jspx_push_body_count_c_005fforEach_005f1; int tmp17851_17850 = tmp17849_17846[tmp17849_17848];tmp17849_17846[tmp17849_17848] = (tmp17851_17850 - 1); if (tmp17851_17850 <= 0) break;
/* 4875 */                           out = _jspx_page_context.popBody(); }
/* 4876 */                         _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                       } finally {
/* 4878 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 4879 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                       }
/* 4881 */                       out.write("\n\t\n\t\t\t</td>\n\t ");
/* 4882 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 4883 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4887 */                   if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 4888 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */                   }
/*      */                   
/* 4891 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 4892 */                   out.write(10);
/* 4893 */                   out.write(32);
/* 4894 */                   if (_jspx_meth_c_005fif_005f19(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                     return;
/* 4896 */                   out.write(32);
/* 4897 */                   out.write(10);
/*      */                 }
/*      */                 
/*      */               }
/* 4901 */               else if (mon)
/*      */               {
/*      */ 
/* 4904 */                 out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 4905 */                 if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                   return;
/* 4907 */                 out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */               }
/*      */               
/*      */ 
/* 4911 */               out.write(9);
/* 4912 */               out.write(9);
/* 4913 */               out.write("\n\n</table>\n\n\n");
/* 4914 */               out.write(10);
/* 4915 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f5.doAfterBody();
/* 4916 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 4919 */             if (_jspx_eval_tiles_005fput_005f5 != 1) {
/* 4920 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 4923 */           if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 4924 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f5); return;
/*      */           }
/*      */           
/* 4927 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f5);
/* 4928 */           out.write(10);
/* 4929 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 4930 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4934 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 4935 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else
/* 4938 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */     } catch (Throwable t) {
/* 4940 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4941 */         out = _jspx_out;
/* 4942 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4943 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 4944 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4947 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4953 */     PageContext pageContext = _jspx_page_context;
/* 4954 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4956 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4957 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 4958 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4960 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 4962 */     _jspx_th_tiles_005fput_005f0.setValue("SAP - Snapshot");
/* 4963 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 4964 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 4965 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 4966 */       return true;
/*      */     }
/* 4968 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 4969 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4974 */     PageContext pageContext = _jspx_page_context;
/* 4975 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4977 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4978 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 4979 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/* 4980 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 4981 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 4983 */         out.write(10);
/* 4984 */         out.write(9);
/* 4985 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 4986 */           return true;
/* 4987 */         out.write(10);
/* 4988 */         out.write(9);
/* 4989 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 4990 */           return true;
/* 4991 */         out.write(10);
/* 4992 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 4993 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4997 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 4998 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 4999 */       return true;
/*      */     }
/* 5001 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 5002 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5007 */     PageContext pageContext = _jspx_page_context;
/* 5008 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5010 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5011 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 5012 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 5014 */     _jspx_th_c_005fwhen_005f0.setTest("${!empty param.haid && haid!='null'}");
/* 5015 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 5016 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 5018 */         out.write(10);
/* 5019 */         out.write(9);
/* 5020 */         out.write(9);
/* 5021 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 5022 */           return true;
/* 5023 */         out.write(10);
/* 5024 */         out.write(9);
/* 5025 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 5026 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5030 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 5031 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 5032 */       return true;
/*      */     }
/* 5034 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 5035 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5040 */     PageContext pageContext = _jspx_page_context;
/* 5041 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5043 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5044 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 5045 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5047 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 5049 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 5050 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 5051 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 5052 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5053 */       return true;
/*      */     }
/* 5055 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5056 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5061 */     PageContext pageContext = _jspx_page_context;
/* 5062 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5064 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5065 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 5066 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 5067 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 5068 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 5070 */         out.write(10);
/* 5071 */         out.write(9);
/* 5072 */         out.write(9);
/* 5073 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 5074 */           return true;
/* 5075 */         out.write(10);
/* 5076 */         out.write(9);
/* 5077 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 5078 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5082 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 5083 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 5084 */       return true;
/*      */     }
/* 5086 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 5087 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5092 */     PageContext pageContext = _jspx_page_context;
/* 5093 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5095 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5096 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 5097 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 5099 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 5101 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 5102 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 5103 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 5104 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 5105 */       return true;
/*      */     }
/* 5107 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 5108 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5113 */     PageContext pageContext = _jspx_page_context;
/* 5114 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5116 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5117 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 5118 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5120 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 5121 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 5122 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 5124 */         out.write("\n\talertUser();\n\treturn;\n\t");
/* 5125 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 5126 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5130 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 5131 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 5132 */       return true;
/*      */     }
/* 5134 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 5135 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5140 */     PageContext pageContext = _jspx_page_context;
/* 5141 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5143 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5144 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 5145 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5147 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 5149 */     _jspx_th_html_005ftext_005f0.setSize("25");
/*      */     
/* 5151 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/* 5152 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 5153 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 5154 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 5155 */       return true;
/*      */     }
/* 5157 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 5158 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5163 */     PageContext pageContext = _jspx_page_context;
/* 5164 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5166 */     CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 5167 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 5168 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5170 */     _jspx_th_html_005fcheckbox_005f0.setProperty("usedRouterString");
/*      */     
/* 5172 */     _jspx_th_html_005fcheckbox_005f0.setOnclick("javascript:showSAPRouterString(this);");
/* 5173 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 5174 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 5175 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 5176 */       return true;
/*      */     }
/* 5178 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 5179 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5184 */     PageContext pageContext = _jspx_page_context;
/* 5185 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5187 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 5188 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 5189 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5191 */     _jspx_th_html_005ftext_005f1.setProperty("routerString");
/*      */     
/* 5193 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/* 5194 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 5195 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 5196 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 5197 */       return true;
/*      */     }
/* 5199 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 5200 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5205 */     PageContext pageContext = _jspx_page_context;
/* 5206 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5208 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5209 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 5210 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5212 */     _jspx_th_html_005ftext_005f2.setProperty("username");
/*      */     
/* 5214 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/* 5216 */     _jspx_th_html_005ftext_005f2.setSize("15");
/* 5217 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 5218 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 5219 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 5220 */       return true;
/*      */     }
/* 5222 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 5223 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5228 */     PageContext pageContext = _jspx_page_context;
/* 5229 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5231 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 5232 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 5233 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5235 */     _jspx_th_html_005fpassword_005f0.setProperty("password");
/*      */     
/* 5237 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext");
/*      */     
/* 5239 */     _jspx_th_html_005fpassword_005f0.setSize("15");
/* 5240 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 5241 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 5242 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 5243 */       return true;
/*      */     }
/* 5245 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 5246 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5251 */     PageContext pageContext = _jspx_page_context;
/* 5252 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5254 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5255 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 5256 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5258 */     _jspx_th_html_005ftext_005f3.setProperty("pollInterval");
/*      */     
/* 5260 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/*      */     
/* 5262 */     _jspx_th_html_005ftext_005f3.setSize("15");
/* 5263 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 5264 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 5265 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 5266 */       return true;
/*      */     }
/* 5268 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 5269 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5274 */     PageContext pageContext = _jspx_page_context;
/* 5275 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5277 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5278 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 5279 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5281 */     _jspx_th_c_005fif_005f1.setTest("${!empty enabled}");
/* 5282 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 5283 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 5285 */         out.write("Edit Monitor");
/* 5286 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 5287 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5291 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 5292 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5293 */       return true;
/*      */     }
/* 5295 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5296 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5301 */     PageContext pageContext = _jspx_page_context;
/* 5302 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5304 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5305 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5306 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5308 */     _jspx_th_c_005fout_005f0.setValue("${hostname}");
/* 5309 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5310 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5311 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5312 */       return true;
/*      */     }
/* 5314 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5315 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5320 */     PageContext pageContext = _jspx_page_context;
/* 5321 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5323 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5324 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5325 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5327 */     _jspx_th_c_005fout_005f1.setValue("${monitorset}");
/* 5328 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5329 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5330 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5331 */       return true;
/*      */     }
/* 5333 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5334 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5339 */     PageContext pageContext = _jspx_page_context;
/* 5340 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5342 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5343 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5344 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5346 */     _jspx_th_c_005fout_005f2.setValue("${LASTDC}");
/* 5347 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5348 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5349 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5350 */       return true;
/*      */     }
/* 5352 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5353 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5358 */     PageContext pageContext = _jspx_page_context;
/* 5359 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5361 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5362 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5363 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5365 */     _jspx_th_c_005fout_005f3.setValue("${NEXTDC}");
/* 5366 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5367 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5368 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5369 */       return true;
/*      */     }
/* 5371 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5372 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5377 */     PageContext pageContext = _jspx_page_context;
/* 5378 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5380 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5381 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 5382 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5384 */     _jspx_th_c_005fif_005f2.setTest("${not empty param.haid}");
/* 5385 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 5386 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 5388 */         out.write(10);
/* 5389 */         out.write(9);
/* 5390 */         out.write(9);
/* 5391 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 5392 */           return true;
/* 5393 */         out.write(10);
/* 5394 */         out.write(9);
/* 5395 */         out.write(9);
/* 5396 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 5397 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5401 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 5402 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5403 */       return true;
/*      */     }
/* 5405 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5406 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5411 */     PageContext pageContext = _jspx_page_context;
/* 5412 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5414 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5415 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 5416 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 5418 */     _jspx_th_c_005fset_005f0.setVar("myfield_paramresid");
/*      */     
/* 5420 */     _jspx_th_c_005fset_005f0.setScope("page");
/* 5421 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 5422 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 5423 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5424 */         out = _jspx_page_context.pushBody();
/* 5425 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 5426 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5429 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 5430 */           return true;
/* 5431 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 5432 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5435 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5436 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5439 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 5440 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 5441 */       return true;
/*      */     }
/* 5443 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 5444 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5449 */     PageContext pageContext = _jspx_page_context;
/* 5450 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5452 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5453 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 5454 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 5456 */     _jspx_th_c_005fout_005f4.setValue("${param.haid}");
/* 5457 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 5458 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 5459 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5460 */       return true;
/*      */     }
/* 5462 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5463 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5468 */     PageContext pageContext = _jspx_page_context;
/* 5469 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5471 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5472 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 5473 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5475 */     _jspx_th_c_005fif_005f3.setTest("${not empty param.resourceid}");
/* 5476 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 5477 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 5479 */         out.write(10);
/* 5480 */         out.write(9);
/* 5481 */         out.write(9);
/* 5482 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 5483 */           return true;
/* 5484 */         out.write(10);
/* 5485 */         out.write(9);
/* 5486 */         out.write(9);
/* 5487 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 5488 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5492 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 5493 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5494 */       return true;
/*      */     }
/* 5496 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5497 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5502 */     PageContext pageContext = _jspx_page_context;
/* 5503 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5505 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5506 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 5507 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 5509 */     _jspx_th_c_005fset_005f1.setVar("myfield_paramresid");
/*      */     
/* 5511 */     _jspx_th_c_005fset_005f1.setScope("page");
/* 5512 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 5513 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 5514 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 5515 */         out = _jspx_page_context.pushBody();
/* 5516 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 5517 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5520 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fset_005f1, _jspx_page_context))
/* 5521 */           return true;
/* 5522 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 5523 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5526 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 5527 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5530 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 5531 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 5532 */       return true;
/*      */     }
/* 5534 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 5535 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5540 */     PageContext pageContext = _jspx_page_context;
/* 5541 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5543 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5544 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 5545 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 5547 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 5548 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 5549 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 5550 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5551 */       return true;
/*      */     }
/* 5553 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5554 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5559 */     PageContext pageContext = _jspx_page_context;
/* 5560 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5562 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5563 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 5564 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5566 */     _jspx_th_c_005fout_005f6.setValue("${myfield_paramresid}");
/* 5567 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 5568 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 5569 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5570 */       return true;
/*      */     }
/* 5572 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5573 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5578 */     PageContext pageContext = _jspx_page_context;
/* 5579 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5581 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5582 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 5583 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5585 */     _jspx_th_c_005fif_005f4.setTest("${not empty param.haid}");
/* 5586 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 5587 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 5589 */         out.write(10);
/* 5590 */         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 5591 */           return true;
/* 5592 */         out.write(10);
/* 5593 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 5594 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5598 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 5599 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 5600 */       return true;
/*      */     }
/* 5602 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 5603 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5608 */     PageContext pageContext = _jspx_page_context;
/* 5609 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5611 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5612 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 5613 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 5615 */     _jspx_th_c_005fset_005f2.setVar("myfield_resid");
/*      */     
/* 5617 */     _jspx_th_c_005fset_005f2.setScope("page");
/* 5618 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 5619 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 5620 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5621 */         out = _jspx_page_context.pushBody();
/* 5622 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 5623 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5626 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fset_005f2, _jspx_page_context))
/* 5627 */           return true;
/* 5628 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 5629 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5632 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5633 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5636 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 5637 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 5638 */       return true;
/*      */     }
/* 5640 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 5641 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5646 */     PageContext pageContext = _jspx_page_context;
/* 5647 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5649 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5650 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 5651 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 5653 */     _jspx_th_c_005fout_005f7.setValue("${param.haid}");
/* 5654 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 5655 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 5656 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5657 */       return true;
/*      */     }
/* 5659 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5660 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5665 */     PageContext pageContext = _jspx_page_context;
/* 5666 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5668 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5669 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 5670 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5672 */     _jspx_th_c_005fif_005f5.setTest("${not empty param.resourceid}");
/* 5673 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 5674 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 5676 */         out.write(10);
/* 5677 */         if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 5678 */           return true;
/* 5679 */         out.write(10);
/* 5680 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 5681 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5685 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 5686 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 5687 */       return true;
/*      */     }
/* 5689 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 5690 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5695 */     PageContext pageContext = _jspx_page_context;
/* 5696 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5698 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5699 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 5700 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 5702 */     _jspx_th_c_005fset_005f3.setVar("myfield_resid");
/*      */     
/* 5704 */     _jspx_th_c_005fset_005f3.setScope("page");
/* 5705 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 5706 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 5707 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 5708 */         out = _jspx_page_context.pushBody();
/* 5709 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 5710 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5713 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fset_005f3, _jspx_page_context))
/* 5714 */           return true;
/* 5715 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 5716 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5719 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 5720 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5723 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 5724 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 5725 */       return true;
/*      */     }
/* 5727 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 5728 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5733 */     PageContext pageContext = _jspx_page_context;
/* 5734 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5736 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5737 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 5738 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 5740 */     _jspx_th_c_005fout_005f8.setValue("${param.resourceid}");
/* 5741 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 5742 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 5743 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5744 */       return true;
/*      */     }
/* 5746 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5747 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5752 */     PageContext pageContext = _jspx_page_context;
/* 5753 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5755 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5756 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 5757 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5759 */     _jspx_th_c_005fset_005f4.setVar("trstripclass");
/*      */     
/* 5761 */     _jspx_th_c_005fset_005f4.setScope("page");
/* 5762 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 5763 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 5764 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 5765 */         out = _jspx_page_context.pushBody();
/* 5766 */         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 5767 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5770 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fset_005f4, _jspx_page_context))
/* 5771 */           return true;
/* 5772 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 5773 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5776 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 5777 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5780 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 5781 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f4);
/* 5782 */       return true;
/*      */     }
/* 5784 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f4);
/* 5785 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fset_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5790 */     PageContext pageContext = _jspx_page_context;
/* 5791 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5793 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5794 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 5795 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fset_005f4);
/*      */     
/* 5797 */     _jspx_th_c_005fout_005f9.setValue("");
/* 5798 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 5799 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 5800 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5801 */       return true;
/*      */     }
/* 5803 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5804 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5809 */     PageContext pageContext = _jspx_page_context;
/* 5810 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5812 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5813 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 5814 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5816 */     _jspx_th_c_005fset_005f5.setVar("myfield_entity");
/*      */     
/* 5818 */     _jspx_th_c_005fset_005f5.setScope("page");
/* 5819 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 5820 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/* 5821 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 5822 */         out = _jspx_page_context.pushBody();
/* 5823 */         _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/* 5824 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5827 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fset_005f5, _jspx_page_context))
/* 5828 */           return true;
/* 5829 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 5830 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5833 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 5834 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5837 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 5838 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 5839 */       return true;
/*      */     }
/* 5841 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 5842 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fset_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5847 */     PageContext pageContext = _jspx_page_context;
/* 5848 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5850 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5851 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 5852 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fset_005f5);
/*      */     
/* 5854 */     _jspx_th_c_005fout_005f10.setValue("noalarms");
/* 5855 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 5856 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 5857 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5858 */       return true;
/*      */     }
/* 5860 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5861 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5866 */     PageContext pageContext = _jspx_page_context;
/* 5867 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5869 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5870 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 5871 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5873 */     _jspx_th_c_005fif_005f6.setTest("${not empty param.entity}");
/* 5874 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 5875 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 5877 */         out.write(10);
/* 5878 */         if (_jspx_meth_c_005fset_005f6(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 5879 */           return true;
/* 5880 */         out.write(10);
/* 5881 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 5882 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5886 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 5887 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 5888 */       return true;
/*      */     }
/* 5890 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 5891 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5896 */     PageContext pageContext = _jspx_page_context;
/* 5897 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5899 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5900 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 5901 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5903 */     _jspx_th_c_005fset_005f6.setVar("myfield_entity");
/*      */     
/* 5905 */     _jspx_th_c_005fset_005f6.setScope("page");
/* 5906 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 5907 */     if (_jspx_eval_c_005fset_005f6 != 0) {
/* 5908 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 5909 */         out = _jspx_page_context.pushBody();
/* 5910 */         _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 5911 */         _jspx_th_c_005fset_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5914 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fset_005f6, _jspx_page_context))
/* 5915 */           return true;
/* 5916 */         int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 5917 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5920 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 5921 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5924 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 5925 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 5926 */       return true;
/*      */     }
/* 5928 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 5929 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fset_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5934 */     PageContext pageContext = _jspx_page_context;
/* 5935 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5937 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5938 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 5939 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fset_005f6);
/*      */     
/* 5941 */     _jspx_th_c_005fout_005f11.setValue("${param.entity}");
/* 5942 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 5943 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 5944 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5945 */       return true;
/*      */     }
/* 5947 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5948 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5953 */     PageContext pageContext = _jspx_page_context;
/* 5954 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5956 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5957 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 5958 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5960 */     _jspx_th_c_005fif_005f7.setTest("${not empty param.includeClass}");
/* 5961 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 5962 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 5964 */         out.write(10);
/* 5965 */         if (_jspx_meth_c_005fset_005f7(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 5966 */           return true;
/* 5967 */         out.write(10);
/* 5968 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 5969 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5973 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 5974 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5975 */       return true;
/*      */     }
/* 5977 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5978 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5983 */     PageContext pageContext = _jspx_page_context;
/* 5984 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5986 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5987 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 5988 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 5990 */     _jspx_th_c_005fset_005f7.setVar("trstripclass");
/*      */     
/* 5992 */     _jspx_th_c_005fset_005f7.setScope("page");
/* 5993 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 5994 */     if (_jspx_eval_c_005fset_005f7 != 0) {
/* 5995 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 5996 */         out = _jspx_page_context.pushBody();
/* 5997 */         _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 5998 */         _jspx_th_c_005fset_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6001 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fset_005f7, _jspx_page_context))
/* 6002 */           return true;
/* 6003 */         int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 6004 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6007 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 6008 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6011 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 6012 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 6013 */       return true;
/*      */     }
/* 6015 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 6016 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fset_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6021 */     PageContext pageContext = _jspx_page_context;
/* 6022 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6024 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6025 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 6026 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fset_005f7);
/*      */     
/* 6028 */     _jspx_th_c_005fout_005f12.setValue("${param.includeClass}");
/* 6029 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 6030 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 6031 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6032 */       return true;
/*      */     }
/* 6034 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6035 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6040 */     PageContext pageContext = _jspx_page_context;
/* 6041 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6043 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6044 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 6045 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6047 */     _jspx_th_c_005fout_005f13.setValue("${trstripclass}");
/* 6048 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 6049 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 6050 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6051 */       return true;
/*      */     }
/* 6053 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6054 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6059 */     PageContext pageContext = _jspx_page_context;
/* 6060 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6062 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6063 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 6064 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 6065 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 6066 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 6067 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 6068 */         out = _jspx_page_context.pushBody();
/* 6069 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 6070 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6073 */         out.write("am.myfield.customfield.text");
/* 6074 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 6075 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6078 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 6079 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6082 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 6083 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6084 */       return true;
/*      */     }
/* 6086 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6087 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6092 */     PageContext pageContext = _jspx_page_context;
/* 6093 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6095 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6096 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 6097 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6099 */     _jspx_th_c_005fout_005f14.setValue("${myfield_resid}");
/* 6100 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 6101 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 6102 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6103 */       return true;
/*      */     }
/* 6105 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6106 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6111 */     PageContext pageContext = _jspx_page_context;
/* 6112 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6114 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6115 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 6116 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6118 */     _jspx_th_c_005fout_005f15.setValue("${myfield_entity}");
/* 6119 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 6120 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 6121 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6122 */       return true;
/*      */     }
/* 6124 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6125 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6130 */     PageContext pageContext = _jspx_page_context;
/* 6131 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6133 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6134 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 6135 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6137 */     _jspx_th_c_005fout_005f16.setValue("${param.resourceid}");
/* 6138 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 6139 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 6140 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6141 */       return true;
/*      */     }
/* 6143 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6144 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6149 */     PageContext pageContext = _jspx_page_context;
/* 6150 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6152 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6153 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 6154 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6156 */     _jspx_th_c_005fout_005f17.setValue("${param.resourcename}");
/* 6157 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 6158 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 6159 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6160 */       return true;
/*      */     }
/* 6162 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6163 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6168 */     PageContext pageContext = _jspx_page_context;
/* 6169 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6171 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6172 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 6173 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6175 */     _jspx_th_c_005fout_005f18.setValue("${param.resourceid}");
/* 6176 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 6177 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 6178 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6179 */       return true;
/*      */     }
/* 6181 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6182 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6187 */     PageContext pageContext = _jspx_page_context;
/* 6188 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6190 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6191 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 6192 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6194 */     _jspx_th_c_005fout_005f19.setValue("${param.resourcename}");
/* 6195 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 6196 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 6197 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6198 */       return true;
/*      */     }
/* 6200 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6201 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6206 */     PageContext pageContext = _jspx_page_context;
/* 6207 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6209 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6210 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 6211 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6213 */     _jspx_th_c_005fout_005f20.setValue("${param.resourceid}");
/* 6214 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 6215 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 6216 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6217 */       return true;
/*      */     }
/* 6219 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6220 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6225 */     PageContext pageContext = _jspx_page_context;
/* 6226 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6228 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6229 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 6230 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6232 */     _jspx_th_c_005fout_005f21.setValue("${param.resourceid}");
/* 6233 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 6234 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 6235 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6236 */       return true;
/*      */     }
/* 6238 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6239 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6244 */     PageContext pageContext = _jspx_page_context;
/* 6245 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6247 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6248 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 6249 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6251 */     _jspx_th_c_005fout_005f22.setValue("${param.resourceid}");
/* 6252 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 6253 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 6254 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 6255 */       return true;
/*      */     }
/* 6257 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 6258 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6263 */     PageContext pageContext = _jspx_page_context;
/* 6264 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6266 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6267 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 6268 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 6270 */     _jspx_th_c_005fout_005f23.setValue("${connectiontime}");
/* 6271 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 6272 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 6273 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 6274 */       return true;
/*      */     }
/* 6276 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 6277 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6282 */     PageContext pageContext = _jspx_page_context;
/* 6283 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6285 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 6286 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 6287 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 6288 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 6289 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 6291 */         out.write("\n\t\t\t\t\t\t-\n\t\t\t\t\t");
/* 6292 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 6293 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6297 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 6298 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 6299 */       return true;
/*      */     }
/* 6301 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 6302 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6307 */     PageContext pageContext = _jspx_page_context;
/* 6308 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6310 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6311 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 6312 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6314 */     _jspx_th_c_005fout_005f24.setValue("${fullvalue}");
/* 6315 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 6316 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 6317 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 6318 */       return true;
/*      */     }
/* 6320 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 6321 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6326 */     PageContext pageContext = _jspx_page_context;
/* 6327 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6329 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6330 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 6331 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6333 */     _jspx_th_c_005fout_005f25.setValue("${fullvalue}");
/* 6334 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 6335 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 6336 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 6337 */       return true;
/*      */     }
/* 6339 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 6340 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6345 */     PageContext pageContext = _jspx_page_context;
/* 6346 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6348 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6349 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 6350 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6352 */     _jspx_th_c_005fif_005f8.setTest("${not empty param.haid}");
/* 6353 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 6354 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 6356 */         out.write(10);
/* 6357 */         out.write(9);
/* 6358 */         out.write(9);
/* 6359 */         if (_jspx_meth_c_005fset_005f8(_jspx_th_c_005fif_005f8, _jspx_page_context))
/* 6360 */           return true;
/* 6361 */         out.write(10);
/* 6362 */         out.write(9);
/* 6363 */         out.write(9);
/* 6364 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 6365 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6369 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 6370 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 6371 */       return true;
/*      */     }
/* 6373 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 6374 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f8(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6379 */     PageContext pageContext = _jspx_page_context;
/* 6380 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6382 */     SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6383 */     _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 6384 */     _jspx_th_c_005fset_005f8.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 6386 */     _jspx_th_c_005fset_005f8.setVar("myfield_paramresid");
/*      */     
/* 6388 */     _jspx_th_c_005fset_005f8.setScope("page");
/* 6389 */     int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 6390 */     if (_jspx_eval_c_005fset_005f8 != 0) {
/* 6391 */       if (_jspx_eval_c_005fset_005f8 != 1) {
/* 6392 */         out = _jspx_page_context.pushBody();
/* 6393 */         _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 6394 */         _jspx_th_c_005fset_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6397 */         if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fset_005f8, _jspx_page_context))
/* 6398 */           return true;
/* 6399 */         int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 6400 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6403 */       if (_jspx_eval_c_005fset_005f8 != 1) {
/* 6404 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6407 */     if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 6408 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f8);
/* 6409 */       return true;
/*      */     }
/* 6411 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f8);
/* 6412 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fset_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6417 */     PageContext pageContext = _jspx_page_context;
/* 6418 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6420 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6421 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 6422 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fset_005f8);
/*      */     
/* 6424 */     _jspx_th_c_005fout_005f26.setValue("${param.haid}");
/* 6425 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 6426 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 6427 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 6428 */       return true;
/*      */     }
/* 6430 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 6431 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6436 */     PageContext pageContext = _jspx_page_context;
/* 6437 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6439 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6440 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 6441 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6443 */     _jspx_th_c_005fif_005f9.setTest("${not empty param.resourceid}");
/* 6444 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 6445 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 6447 */         out.write(10);
/* 6448 */         out.write(9);
/* 6449 */         out.write(9);
/* 6450 */         if (_jspx_meth_c_005fset_005f9(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 6451 */           return true;
/* 6452 */         out.write(10);
/* 6453 */         out.write(9);
/* 6454 */         out.write(9);
/* 6455 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 6456 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6460 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 6461 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 6462 */       return true;
/*      */     }
/* 6464 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 6465 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f9(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6470 */     PageContext pageContext = _jspx_page_context;
/* 6471 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6473 */     SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6474 */     _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 6475 */     _jspx_th_c_005fset_005f9.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 6477 */     _jspx_th_c_005fset_005f9.setVar("myfield_paramresid");
/*      */     
/* 6479 */     _jspx_th_c_005fset_005f9.setScope("page");
/* 6480 */     int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 6481 */     if (_jspx_eval_c_005fset_005f9 != 0) {
/* 6482 */       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 6483 */         out = _jspx_page_context.pushBody();
/* 6484 */         _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 6485 */         _jspx_th_c_005fset_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6488 */         if (_jspx_meth_c_005fout_005f27(_jspx_th_c_005fset_005f9, _jspx_page_context))
/* 6489 */           return true;
/* 6490 */         int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 6491 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6494 */       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 6495 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6498 */     if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 6499 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f9);
/* 6500 */       return true;
/*      */     }
/* 6502 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f9);
/* 6503 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_c_005fset_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6508 */     PageContext pageContext = _jspx_page_context;
/* 6509 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6511 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6512 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 6513 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_c_005fset_005f9);
/*      */     
/* 6515 */     _jspx_th_c_005fout_005f27.setValue("${param.resourceid}");
/* 6516 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 6517 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 6518 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 6519 */       return true;
/*      */     }
/* 6521 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 6522 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6527 */     PageContext pageContext = _jspx_page_context;
/* 6528 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6530 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6531 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 6532 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6534 */     _jspx_th_c_005fout_005f28.setValue("${myfield_paramresid}");
/* 6535 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 6536 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 6537 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 6538 */       return true;
/*      */     }
/* 6540 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 6541 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6546 */     PageContext pageContext = _jspx_page_context;
/* 6547 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6549 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6550 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 6551 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6553 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 6555 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 6556 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 6557 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 6558 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 6559 */       return true;
/*      */     }
/* 6561 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 6562 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6567 */     PageContext pageContext = _jspx_page_context;
/* 6568 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6570 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6571 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 6572 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 6574 */     _jspx_th_c_005fout_005f29.setValue("${param.resourceid}");
/* 6575 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 6576 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 6577 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 6578 */       return true;
/*      */     }
/* 6580 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 6581 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6586 */     PageContext pageContext = _jspx_page_context;
/* 6587 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6589 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6590 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 6591 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 6593 */     _jspx_th_c_005fout_005f30.setValue("${param.resourceid}");
/* 6594 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 6595 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 6596 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 6597 */       return true;
/*      */     }
/* 6599 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 6600 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6605 */     PageContext pageContext = _jspx_page_context;
/* 6606 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6608 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6609 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 6610 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 6612 */     _jspx_th_c_005fout_005f31.setValue("${param.resourceid}");
/* 6613 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 6614 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 6615 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 6616 */       return true;
/*      */     }
/* 6618 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 6619 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6624 */     PageContext pageContext = _jspx_page_context;
/* 6625 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6627 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6628 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 6629 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 6631 */     _jspx_th_c_005fout_005f32.setValue("${param.resourceid}");
/* 6632 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 6633 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 6634 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 6635 */       return true;
/*      */     }
/* 6637 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 6638 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6643 */     PageContext pageContext = _jspx_page_context;
/* 6644 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6646 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6647 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 6648 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 6650 */     _jspx_th_c_005fout_005f33.setValue("${haid}");
/* 6651 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 6652 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 6653 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 6654 */       return true;
/*      */     }
/* 6656 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 6657 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6662 */     PageContext pageContext = _jspx_page_context;
/* 6663 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6665 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6666 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 6667 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/*      */     
/* 6669 */     _jspx_th_c_005fout_005f34.setValue("${param.resourceid}");
/* 6670 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 6671 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 6672 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 6673 */       return true;
/*      */     }
/* 6675 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 6676 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6681 */     PageContext pageContext = _jspx_page_context;
/* 6682 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6684 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6685 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 6686 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 6688 */     _jspx_th_c_005fout_005f35.setValue("${param.resourceid}");
/* 6689 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 6690 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 6691 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 6692 */       return true;
/*      */     }
/* 6694 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 6695 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6700 */     PageContext pageContext = _jspx_page_context;
/* 6701 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6703 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6704 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 6705 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 6707 */     _jspx_th_c_005fout_005f36.setValue("${param.resourceid}");
/* 6708 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 6709 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 6710 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 6711 */       return true;
/*      */     }
/* 6713 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 6714 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6719 */     PageContext pageContext = _jspx_page_context;
/* 6720 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6722 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6723 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 6724 */     _jspx_th_c_005fout_005f37.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 6726 */     _jspx_th_c_005fout_005f37.setValue("${param.resourceid}");
/* 6727 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 6728 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 6729 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 6730 */       return true;
/*      */     }
/* 6732 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 6733 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6738 */     PageContext pageContext = _jspx_page_context;
/* 6739 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6741 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6742 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 6743 */     _jspx_th_c_005fout_005f38.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 6745 */     _jspx_th_c_005fout_005f38.setValue("${param.resourceid}");
/* 6746 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 6747 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 6748 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 6749 */       return true;
/*      */     }
/* 6751 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 6752 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6757 */     PageContext pageContext = _jspx_page_context;
/* 6758 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6760 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6761 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 6762 */     _jspx_th_c_005fout_005f39.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6764 */     _jspx_th_c_005fout_005f39.setValue("${ha.key}");
/* 6765 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 6766 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 6767 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 6768 */       return true;
/*      */     }
/* 6770 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 6771 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f40(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6776 */     PageContext pageContext = _jspx_page_context;
/* 6777 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6779 */     OutTag _jspx_th_c_005fout_005f40 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6780 */     _jspx_th_c_005fout_005f40.setPageContext(_jspx_page_context);
/* 6781 */     _jspx_th_c_005fout_005f40.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6783 */     _jspx_th_c_005fout_005f40.setValue("${ha.value}");
/* 6784 */     int _jspx_eval_c_005fout_005f40 = _jspx_th_c_005fout_005f40.doStartTag();
/* 6785 */     if (_jspx_th_c_005fout_005f40.doEndTag() == 5) {
/* 6786 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 6787 */       return true;
/*      */     }
/* 6789 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 6790 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f10(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6795 */     PageContext pageContext = _jspx_page_context;
/* 6796 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6798 */     SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6799 */     _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 6800 */     _jspx_th_c_005fset_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6802 */     _jspx_th_c_005fset_005f10.setVar("monitorName");
/* 6803 */     int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 6804 */     if (_jspx_eval_c_005fset_005f10 != 0) {
/* 6805 */       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 6806 */         out = _jspx_page_context.pushBody();
/* 6807 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 6808 */         _jspx_th_c_005fset_005f10.setBodyContent((BodyContent)out);
/* 6809 */         _jspx_th_c_005fset_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6812 */         if (_jspx_meth_c_005fout_005f41(_jspx_th_c_005fset_005f10, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6813 */           return true;
/* 6814 */         int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 6815 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6818 */       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 6819 */         out = _jspx_page_context.popBody();
/* 6820 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 6823 */     if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 6824 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10);
/* 6825 */       return true;
/*      */     }
/* 6827 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10);
/* 6828 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f41(JspTag _jspx_th_c_005fset_005f10, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6833 */     PageContext pageContext = _jspx_page_context;
/* 6834 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6836 */     OutTag _jspx_th_c_005fout_005f41 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6837 */     _jspx_th_c_005fout_005f41.setPageContext(_jspx_page_context);
/* 6838 */     _jspx_th_c_005fout_005f41.setParent((Tag)_jspx_th_c_005fset_005f10);
/*      */     
/* 6840 */     _jspx_th_c_005fout_005f41.setValue("${ha.value}");
/* 6841 */     int _jspx_eval_c_005fout_005f41 = _jspx_th_c_005fout_005f41.doStartTag();
/* 6842 */     if (_jspx_th_c_005fout_005f41.doEndTag() == 5) {
/* 6843 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 6844 */       return true;
/*      */     }
/* 6846 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 6847 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f42(JspTag _jspx_th_logic_005fpresent_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6852 */     PageContext pageContext = _jspx_page_context;
/* 6853 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6855 */     OutTag _jspx_th_c_005fout_005f42 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6856 */     _jspx_th_c_005fout_005f42.setPageContext(_jspx_page_context);
/* 6857 */     _jspx_th_c_005fout_005f42.setParent((Tag)_jspx_th_logic_005fpresent_005f7);
/*      */     
/* 6859 */     _jspx_th_c_005fout_005f42.setValue("${ha.key}");
/* 6860 */     int _jspx_eval_c_005fout_005f42 = _jspx_th_c_005fout_005f42.doStartTag();
/* 6861 */     if (_jspx_th_c_005fout_005f42.doEndTag() == 5) {
/* 6862 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 6863 */       return true;
/*      */     }
/* 6865 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 6866 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_c_005fif_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6871 */     PageContext pageContext = _jspx_page_context;
/* 6872 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6874 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6875 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 6876 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_c_005fif_005f18);
/*      */     
/* 6878 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 6879 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 6880 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 6881 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 6882 */       return true;
/*      */     }
/* 6884 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 6885 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f43(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6890 */     PageContext pageContext = _jspx_page_context;
/* 6891 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6893 */     OutTag _jspx_th_c_005fout_005f43 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6894 */     _jspx_th_c_005fout_005f43.setPageContext(_jspx_page_context);
/* 6895 */     _jspx_th_c_005fout_005f43.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6897 */     _jspx_th_c_005fout_005f43.setValue("${ha.key}");
/* 6898 */     int _jspx_eval_c_005fout_005f43 = _jspx_th_c_005fout_005f43.doStartTag();
/* 6899 */     if (_jspx_th_c_005fout_005f43.doEndTag() == 5) {
/* 6900 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 6901 */       return true;
/*      */     }
/* 6903 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 6904 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f44(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6909 */     PageContext pageContext = _jspx_page_context;
/* 6910 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6912 */     OutTag _jspx_th_c_005fout_005f44 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6913 */     _jspx_th_c_005fout_005f44.setPageContext(_jspx_page_context);
/* 6914 */     _jspx_th_c_005fout_005f44.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6916 */     _jspx_th_c_005fout_005f44.setValue("${ha.value}");
/* 6917 */     int _jspx_eval_c_005fout_005f44 = _jspx_th_c_005fout_005f44.doStartTag();
/* 6918 */     if (_jspx_th_c_005fout_005f44.doEndTag() == 5) {
/* 6919 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 6920 */       return true;
/*      */     }
/* 6922 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 6923 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f11(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6928 */     PageContext pageContext = _jspx_page_context;
/* 6929 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6931 */     SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6932 */     _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 6933 */     _jspx_th_c_005fset_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6935 */     _jspx_th_c_005fset_005f11.setVar("monitorName");
/* 6936 */     int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 6937 */     if (_jspx_eval_c_005fset_005f11 != 0) {
/* 6938 */       if (_jspx_eval_c_005fset_005f11 != 1) {
/* 6939 */         out = _jspx_page_context.pushBody();
/* 6940 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 6941 */         _jspx_th_c_005fset_005f11.setBodyContent((BodyContent)out);
/* 6942 */         _jspx_th_c_005fset_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6945 */         if (_jspx_meth_c_005fout_005f45(_jspx_th_c_005fset_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6946 */           return true;
/* 6947 */         int evalDoAfterBody = _jspx_th_c_005fset_005f11.doAfterBody();
/* 6948 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6951 */       if (_jspx_eval_c_005fset_005f11 != 1) {
/* 6952 */         out = _jspx_page_context.popBody();
/* 6953 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 6956 */     if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 6957 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 6958 */       return true;
/*      */     }
/* 6960 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 6961 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f45(JspTag _jspx_th_c_005fset_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6966 */     PageContext pageContext = _jspx_page_context;
/* 6967 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6969 */     OutTag _jspx_th_c_005fout_005f45 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6970 */     _jspx_th_c_005fout_005f45.setPageContext(_jspx_page_context);
/* 6971 */     _jspx_th_c_005fout_005f45.setParent((Tag)_jspx_th_c_005fset_005f11);
/*      */     
/* 6973 */     _jspx_th_c_005fout_005f45.setValue("${ha.value}");
/* 6974 */     int _jspx_eval_c_005fout_005f45 = _jspx_th_c_005fout_005f45.doStartTag();
/* 6975 */     if (_jspx_th_c_005fout_005f45.doEndTag() == 5) {
/* 6976 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 6977 */       return true;
/*      */     }
/* 6979 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 6980 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f46(JspTag _jspx_th_logic_005fpresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6985 */     PageContext pageContext = _jspx_page_context;
/* 6986 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6988 */     OutTag _jspx_th_c_005fout_005f46 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6989 */     _jspx_th_c_005fout_005f46.setPageContext(_jspx_page_context);
/* 6990 */     _jspx_th_c_005fout_005f46.setParent((Tag)_jspx_th_logic_005fpresent_005f8);
/*      */     
/* 6992 */     _jspx_th_c_005fout_005f46.setValue("${ha.key}");
/* 6993 */     int _jspx_eval_c_005fout_005f46 = _jspx_th_c_005fout_005f46.doStartTag();
/* 6994 */     if (_jspx_th_c_005fout_005f46.doEndTag() == 5) {
/* 6995 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 6996 */       return true;
/*      */     }
/* 6998 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 6999 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_logic_005fpresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7004 */     PageContext pageContext = _jspx_page_context;
/* 7005 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7007 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7008 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 7009 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_logic_005fpresent_005f8);
/*      */     
/* 7011 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.quickremoval.monitorgroup.txt");
/* 7012 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 7013 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 7014 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 7015 */       return true;
/*      */     }
/* 7017 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 7018 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f19(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7023 */     PageContext pageContext = _jspx_page_context;
/* 7024 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7026 */     IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7027 */     _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 7028 */     _jspx_th_c_005fif_005f19.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 7030 */     _jspx_th_c_005fif_005f19.setTest("${empty associatedmgs}");
/* 7031 */     int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 7032 */     if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */       for (;;) {
/* 7034 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 7035 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f19, _jspx_page_context))
/* 7036 */           return true;
/* 7037 */         out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">");
/* 7038 */         if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_c_005fif_005f19, _jspx_page_context))
/* 7039 */           return true;
/* 7040 */         out.write("</td>\n\t ");
/* 7041 */         int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 7042 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7046 */     if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 7047 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 7048 */       return true;
/*      */     }
/* 7050 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 7051 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7056 */     PageContext pageContext = _jspx_page_context;
/* 7057 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7059 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7060 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 7061 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 7063 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 7064 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 7065 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 7066 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 7067 */       return true;
/*      */     }
/* 7069 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 7070 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7075 */     PageContext pageContext = _jspx_page_context;
/* 7076 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7078 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7079 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 7080 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 7082 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.urlmonitor.none.text");
/* 7083 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 7084 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 7085 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 7086 */       return true;
/*      */     }
/* 7088 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 7089 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7094 */     PageContext pageContext = _jspx_page_context;
/* 7095 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7097 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7098 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 7099 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 7101 */     _jspx_th_fmt_005fmessage_005f5.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 7102 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 7103 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 7104 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 7105 */       return true;
/*      */     }
/* 7107 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 7108 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\sap\CCMSDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */