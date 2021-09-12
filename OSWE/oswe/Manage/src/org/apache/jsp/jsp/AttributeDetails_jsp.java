/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.bean.SummaryBean;
/*     */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*     */ import com.adventnet.appmanager.struts.beans.HistoryDataGraphUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.ReportDataUtilities;
/*     */ import com.adventnet.awolf.tags.TimeChart;
/*     */ import java.io.IOException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.html.FormTag;
/*     */ import org.apache.struts.taglib.html.TextTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ 
/*     */ public final class AttributeDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  36 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyle_005fsize_005freadonly_005fproperty_005fonchange_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  53 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  57 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  58 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  59 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  60 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  61 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyle_005fsize_005freadonly_005fproperty_005fonchange_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  62 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  63 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  64 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  65 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  69 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  70 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*  71 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  72 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  73 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyle_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.release();
/*  74 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  75 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  82 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  85 */     JspWriter out = null;
/*  86 */     Object page = this;
/*  87 */     JspWriter _jspx_out = null;
/*  88 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  92 */       response.setContentType("text/html;charset=UTF-8");
/*  93 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  95 */       _jspx_page_context = pageContext;
/*  96 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  97 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  98 */       session = pageContext.getSession();
/*  99 */       out = pageContext.getOut();
/* 100 */       _jspx_out = out;
/*     */       
/* 102 */       out.write("<!--$Id$-->\n");
/* 103 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 104 */       HistoryDataGraphUtil graph = null;
/* 105 */       graph = (HistoryDataGraphUtil)_jspx_page_context.getAttribute("graph", 1);
/* 106 */       if (graph == null) {
/* 107 */         graph = new HistoryDataGraphUtil();
/* 108 */         _jspx_page_context.setAttribute("graph", graph, 1);
/*     */       }
/* 110 */       out.write("\n\n<SCRIPT src=\"/template/calendar.js\" LANGUAGE=\"JavaScript1.2\"></SCRIPT>\n<SCRIPT src=\"/template/calendar-en.js\" LANGUAGE=\"JavaScript1.2\"></SCRIPT>\n<SCRIPT src=\"/template/calendar-setup.js\" LANGUAGE=\"JavaScript1.2\"></SCRIPT>\n<link href=\"../images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"../images/");
/* 111 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/* 113 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/calendar-win2k-1.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n<SCRIPT src=\"/template/listview.js\" type=text/javascript></SCRIPT>\n\n   ");
/* 114 */       if (!FreeEditionDetails.getFreeEditionDetails().isAnomalyAllowed()) {
/* 115 */         out.write("\n        <table class=\"messageboxfailure\" width=\"100%\" cellspacing=\"2\" cellpadding=\"2\" border=\"0\">\n                   <tr>\n\t    \t\t      <td width=\"6%\" align=\"center\">\n<img height=\"23\" width=\"23\" alt=\"Icon\" src=\"../images/icon_message_failure.gif\"/>\n</td>\n\t                   <td class=\"message\" height=\"34\" width=\"94%\">\n                                ");
/* 116 */         String link22 = "<a style=\"font-size: 10px;\" href=\"mailto:" + FormatUtil.getString("product.talkback.mailid") + "\" class=\"new-login-email-link\"><b>" + FormatUtil.getString("product.talkback.mailid") + "</b></a> ";
/* 117 */         FreeEditionDetails.getFreeEditionDetails(); if (FreeEditionDetails.anomalyMessage != null) {
/* 118 */           out.write("\n                        ");
/* 119 */           FreeEditionDetails.getFreeEditionDetails();out.print(FreeEditionDetails.anomalyMessage);
/* 120 */           out.write("\n                        ");
/*     */         } else {
/* 122 */           out.write("\n                        ");
/* 123 */           out.print(FormatUtil.getString("am.webclient.anomaly.noanomalyaddon.text", new String[] { link22 }));
/* 124 */           out.write("\n                        ");
/*     */         }
/* 126 */         out.write("\n                </td>\n                </tr>\n                </table>\n       ");
/*     */       } else {
/* 128 */         out.write(10);
/*     */         try {
/* 130 */           String CG = request.getParameter("customgraph");
/* 131 */           String ALERTID = request.getParameter("alertid");
/* 132 */           String RESTYPES = request.getParameter("resType");
/* 133 */           String cst = request.getParameter("cst");
/*     */           
/* 135 */           String rd = "";
/* 136 */           String ad = "";
/* 137 */           if ((ALERTID != null) && (ALERTID.indexOf("_") != -1)) {
/* 138 */             String[] tp = ALERTID.split("_");
/* 139 */             rd = tp[0];
/* 140 */             ad = tp[1];
/*     */           }
/*     */           
/*     */ 
/* 144 */           out.write("\n<html>\n\n<script>\n\n        var elemArr = new Array();\n        var criticalelem = new Array();\n        var clearelem = new Array();\n\n\n function getmonitortypes(httpo,dname,cname, g2)\n{\n\n\n if(httpo.readyState == 4 && httpo.status == 200)\n {\n\n     hideDiv(cname);\n     var f2=g2+\"1\";//no i18n\n\tif(document.getElementById(dname)!= null){\n    document.getElementById(dname).innerHTML=httpo.responseText;\n    if(httpo.responseText.indexOf(\"FAILED\")>0){\n       //document.getElementById(g2).src=\"../images/anamoly-red.gif\";\n       //document.getElementById(g2).innerHTML=\"<a href=\\\"#\"+f2+\"\\\"><img src=\\\"../images/anamoly-red.gif\\\"  border=\\\"0\\\"></a>\";\n       criticalelem[criticalelem.length]=g2;\n    }else{\n        clearelem[clearelem.length]=g2;\n    }\n\n    document.getElementById(dname).style.display='block';}\n    sendReq();\n }\n}\n    </script>\n");
/*     */           
/* 146 */           Calendar cal = new java.util.GregorianCalendar();
/* 147 */           Date STTime = new Date(System.currentTimeMillis());
/* 148 */           cal.setTime(STTime);
/* 149 */           int year1 = cal.get(1);
/* 150 */           int month1 = cal.get(2) + 1;
/* 151 */           int monthday1 = cal.get(5);
/* 152 */           int hour1 = cal.get(11);
/* 153 */           int minute1 = cal.get(12);
/* 154 */           int second1 = cal.get(13);
/*     */           
/* 156 */           String Stype = "clear";
/* 157 */           Stype = request.getParameter("severity");
/* 158 */           String Stime = request.getParameter("showtime");
/* 159 */           String falerts = request.getParameter("fromAlerts");
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 165 */           out.write("\n<script>\n /**\n     * get\n     */\n    function getData(b,f,g,h,st,ed,par,sec) {\n        var d =document.forms[0].sttime.value;\n\n       var d2=new Date();\n       var url2;\n       if(\"false\"==par){\n           url2=\"/showAttributes.do?method=showArchivedDataValues&toshow=\"+encodeURIComponent(b)+\"&time=\"+d+\"&resid=\"+g+\"&attid=\"+f+\"&hour=\"+h+\"&sttime=\"+st+\"&edtime=\"+ed+\"&isValue=\"+par+\"&isSecondlevel=\"+sec;//no i18n\n       }else{\n           url2=\"/showAttributes.do?method=showArchivedDataValues&toshow=\"+encodeURIComponent(b)+\"&time=\"+d+\"&resid=\"+g+\"&attid=\"+f+\"&hour=\"+h+\"&sttime=\"+st+\"&edtime=\"+ed+\"&isValue=true&anomalyid=\"+par+\"&isSecondlevel=\"+sec;//no i18n\n       }\n\n       var niceurls=url2+'&sid='+d2;\n        if(f.indexOf('@')>0){\n           var t1=f.split('@');\n           f=t1[0];\n       }\n       var udata=g+'_'+f;\n       var window2=window.open(niceurls,udata,'resizable=yes,scrollbars=yes,width=760,height=460');\n\n       window2.focus();\n\n       //fnOpenNewWindow(\"/jsp/AttributeDetailsDiv.jsp?graphkey=\"+encodeURIComponent(a)+\"&toshow=\"+encodeURIComponent(b)+\"&monitorname=\"+encodeURIComponent(c)+\"&time=\"+d+\"&Adname=\"+e);\n");
/* 166 */           out.write("\n       /* var url=\"/jsp/AttributeDetailsDiv.jsp?graphkey=\"+encodeURIComponent(a)+\"&toshow=\"+encodeURIComponent(b);\n\n\n        http.open(\"GET\",url,true);\n        http.onreadystatechange = getAttributeData;\n        http.send(null);*/\n    }\n     function getRawData(a,b,c,e,f,sec) {\n       var d =document.forms[0].sttime.value;\n\n       var d2=new Date();\n       var url2=\"/showAttributes.do?method=showRawDataValues&isSecondlevel=\"+sec+\"&isValue=\"+f+\"&attid=\"+encodeURIComponent(a)+\"&resid=\"+encodeURIComponent(b)+\"&monitorname=\"+encodeURIComponent(c)+\"&time=\"+d+\"&Adname=\"+encodeURIComponent(e);//no i18n\n       var niceurls=url2+'&sid='+d2;\n       if(a.indexOf('@')>0){\n           var t1=a.split('@');\n           a=t1[0];\n       }\n       var udata=b+'_'+a;\n\n       var window2=window.open(niceurls,udata,'resizable=yes,scrollbars=yes,width=760,height=460');\n       //fnOpenNewWindow(\"/showAttributes.do?method=showRawDataValues&attid=\"+encodeURIComponent(a)+\"&resid=\"+encodeURIComponent(b)+\"&monitorname=\"+encodeURIComponent(c)+\"&time=\"+d+\"&Adname=\"+e);\n");
/* 167 */           out.write("       window2.focus();\n\n       /* var url=\"/jsp/AttributeDetailsDiv.jsp?graphkey=\"+encodeURIComponent(a)+\"&toshow=\"+encodeURIComponent(b);\n\n\n        http.open(\"GET\",url,true);\n        http.onreadystatechange = getAttributeData;\n        http.send(null);*/\n    }\n    /**\n     * Comment\n     */\n    /*function getAttributeData() {\n        if(http.readyState == 4)\n {\n\n   var result = http.responseText;\n\n\n    var clientSize = findClientDim();\n\tvar clientWidth = clientSize.width;\n\tvar clientHeight = clientSize.height;\n\n\tvar docSize = findDocDim();\n\tvar docWidth = docSize.width;\n\tvar docHeight = docSize.height;\n\n\tdocument.getElementById(\"mask\").style.left = document.getElementById(\"mask\").style.top = \"0px\";\n\tdocument.getElementById(\"mask\").style.width = docSize.width + \"px\";\n\tdocument.getElementById(\"mask\").style.height = docSize.height + \"px\";\n\tdocument.getElementById(\"mask\").style.display = \"\";\n\n\tdocument.getElementById(\"popup\").style.left = ((clientWidth / 2) - (document.getElementById(\"popup\").offsetWidth / 2)) + \"px\";\n");
/* 168 */           out.write("\tdocument.getElementById(\"popup\").style.top = ((clientHeight / 2) - (document.getElementById(\"popup\").offsetHeight / 2)) + \"px\";\ndocument.getElementById(\"popup\").innerHTML=result;\n    //document.getElementById(\"popup\").style.display='block';\n    }\n    }*/\n\n        /**\n         * Comment\n         */\n        /**\n                  * Comment\n                  */\n                 function changeImage() {\n                     showDiv('changeGraph');\n                     showDiv('attributedetail');\n\n\n                 }\n\n/**\n                     * this method is used to get the new alert timings\n                     */\n                    function getAlertTimeValues() {\n\n\n                       document.forms[0].alerttime.value=document.forms[0].alerttimings.value;\n                         if(document.forms[0].criticalmonitors[0].checked){\n\n                            document.forms[0].severityType.value='critical';\n                        }else{\n                            document.forms[0].severityType.value='clear';\n");
/* 169 */           out.write("                        }\n                       document.forms[0].method.value=\"showAlertTimes\";\n\n                        //document.forms[0].action=\"/showAttributes.do?method=showAlertTimes\";\n\n                        document.forms[0].submit();\n\n\n                    }\n                    /**\n * Comment\n */\nfunction sendReq() {\n    //alert(elemArr);\n    //alert(clearelem);\n    //alert(criticalelem);\n    //alert(document.forms[0].criticalmonitors[0].checked);\n    if(document.forms[0].criticalmonitors[0].checked){\n        for(var x=0;x<clearelem.length;x++){\n            var y=clearelem[x];\n\n            var z=\"Att_\"+y;\n            //var f=\"img_\"+y;\n\n             //document.getElementById(f).style.display='none';\n            hideDiv(z);\n        }\n    }else{\n        for(var x=0;x<elemArr.length;x++){\n            var y=elemArr[x];\n\n            var z=\"Att_\"+y;\n\n             //var f=\"img_\"+y;\n\n             //document.getElementById(f).style.display='block';\n            showDiv(z);\n        }\n    }\n}\n  function fnCustomGraph(){\n");
/* 170 */           out.write("      //alert(f.monitors.value);\n      //alert(f.attributes.value);\n      //alert(document.forms[0]);\n\n      if(document.forms[0].dropdownmonitors.value=='--select--'){\n          alert(\"");
/* 171 */           out.print(FormatUtil.getString("am.anomaly.dropdown.jsalert.selectmonitor.text"));
/* 172 */           out.write("\");\n          return false;\n      }\n       else if(document.forms[0].dropdownattributes.value=='--select--'){\n          alert(\"");
/* 173 */           out.print(FormatUtil.getString("am.anomaly.dropdown.jsalert.selectattribute.text"));
/* 174 */           out.write("\");\n          return false;\n      }\n\n\n\n      document.forms[0].customshowtime.value=document.forms[0].thisstart.value;\n      document.forms[0].aid.value=document.forms[0].dropdownattributes.value;\n      document.forms[0].rtype.value=document.forms[0].dropdownmonitors.value;\n      document.forms[0].customgraph.value='true';\n      if(document.forms[0].criticalmonitors[0].checked){\n\n           document.forms[0].severityType.value='critical';\n      }else{\n           document.forms[0].severityType.value='clear';\n      }\n      //alert(f.aid.value);\n      //alert(f.rtype.value);\n\n      document.forms[0].method.value=\"showCustomGraph\";\n\n      //document.forms[0].action=\"/showAttributes.do?method=showCustomGraph\";\n      document.forms[0].submit();\n\n\n  }\n\n function fnSetLastStartTime(t){\n     document.forms[0].thisstart.value=t;\n }\n\n    </script>\n\n\n  <body style=\"margin:0px; padding:0px;\" onLoad=\"javascript:myOnLoad()\">\n\n");
/*     */           
/*     */           try
/*     */           {
/* 178 */             long[] timestamps = ReportDataUtilities.getTimeStampForQuery();
/*     */             
/* 180 */             String customtime = request.getParameter("customtime");
/* 181 */             String fromAlerts = request.getParameter("fromAlerts");
/* 182 */             String alertid = request.getParameter("haid");
/* 183 */             String alerttime = request.getParameter("time");
/* 184 */             String graphattributeid = null;
/* 185 */             String graphmonitorid = null;
/* 186 */             String alarmstarttime = null;
/*     */             
/*     */ 
/*     */ 
/* 190 */             String haid = request.getParameter("haid");
/* 191 */             String oldhaid = haid;
/* 192 */             String oldalertid = null;
/* 193 */             if (haid.indexOf("_") != -1)
/*     */             {
/* 195 */               String[] temp = haid.split("_");
/* 196 */               haid = temp[0];
/*     */             }
/*     */             
/*     */ 
/* 200 */             Map DataTableMap = new java.util.HashMap();
/* 201 */             Map RMAP = ReportDataUtilities.getResourceMapping(haid);
/*     */             
/* 203 */             Map U1 = (Map)RMAP.get("alltypes");
/* 204 */             Map U2 = (Map)RMAP.get("dropdown");
/*     */             
/* 206 */             List x2 = (List)U1.get("ResourceIds");
/*     */             
/* 208 */             List dropdownlist = (List)U2.get("ResourceIds");
/* 209 */             Map typeDisplayName = ReportDataUtilities.getDisplayNameForType(x2);
/*     */             
/*     */ 
/*     */ 
/* 213 */             RMAP.remove("alltypes");
/* 214 */             RMAP.remove("dropdown");
/*     */             
/*     */ 
/* 217 */             long alertStartTime = 0L;
/* 218 */             Map urldataMap = null;
/* 219 */             List allAlertTimes = new ArrayList();
/* 220 */             String ADNAME = FormatUtil.getString("am.webclient.db2.graph.responsetimeinms");
/* 221 */             if (("true".equals(CG)) && ("true".equals(fromAlerts)))
/*     */             {
/* 223 */               alerttime = request.getParameter("showtime");
/*     */               
/*     */ 
/* 226 */               alertStartTime = Long.parseLong(alerttime);
/*     */               
/*     */ 
/*     */ 
/*     */ 
/* 231 */               timestamps = ReportDataUtilities.getTimeStampForQuery(alertStartTime);
/*     */               
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 237 */               oldalertid = request.getParameter("oldalertid");
/* 238 */               alertid = request.getParameter("alertid");
/* 239 */               urldataMap = ReportDataUtilities.getCustomGraph(alertid, System.currentTimeMillis() - 10800000L, System.currentTimeMillis(), oldalertid);
/* 240 */               ADNAME = (String)urldataMap.get("AttDname");
/* 241 */               urldataMap.remove("AttDname");
/* 242 */               allAlertTimes = (List)urldataMap.get("alerttimes");
/* 243 */               urldataMap.remove("alerttimes");
/*     */             }
/*     */             else {
/* 246 */               if ("true".equals(fromAlerts)) {
/* 247 */                 oldalertid = alertid;
/*     */                 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 255 */                 timestamps = ReportDataUtilities.getTimeStampForQuery(Long.parseLong(alerttime));
/*     */                 
/*     */ 
/*     */ 
/*     */ 
/* 260 */                 urldataMap = ReportDataUtilities.getUrlResponseTimeValues(alertid, System.currentTimeMillis() - 10800000L, System.currentTimeMillis(), true, RMAP, Long.parseLong(alerttime));
/* 261 */                 allAlertTimes = (List)urldataMap.get("alerttimes");
/* 262 */                 SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm ", java.util.Locale.getDefault());
/* 263 */                 alarmstarttime = simpledateformat.format(new Date(Long.parseLong(alerttime)));
/*     */               }
/*     */               else
/*     */               {
/* 267 */                 urldataMap = ReportDataUtilities.getUrlData(RMAP, System.currentTimeMillis() - 10800000L, System.currentTimeMillis());
/* 268 */                 alarmstarttime = "";
/*     */               }
/*     */               
/* 271 */               ADNAME = (String)urldataMap.get("attributedisplayname");
/* 272 */               graphattributeid = (String)urldataMap.get("attributeidforgraph");
/* 273 */               graphmonitorid = (String)urldataMap.get("monitoridforgraph");
/*     */               
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 279 */               urldataMap.remove("attributedisplayname");
/* 280 */               urldataMap.remove("attributeidforgraph");
/* 281 */               urldataMap.remove("monitoridforgraph");
/* 282 */               urldataMap.remove("alerttimes");
/*     */             }
/* 284 */             long starttime = 0L;
/* 285 */             long endtime = 0L;
/* 286 */             if ("true".equals(customtime)) {
/* 287 */               starttime = Long.parseLong((String)request.getAttribute("sttime"));
/* 288 */               endtime = Long.parseLong((String)request.getAttribute("edtime"));
/*     */               
/* 290 */               timestamps = ReportDataUtilities.getTimeStampForQuery(starttime, endtime);
/*     */             }
/*     */             
/*     */ 
/*     */ 
/* 295 */             int graphColspan = 0;
/* 296 */             if ((allAlertTimes != null) && (allAlertTimes.size() <= 0)) {
/* 297 */               graphColspan = 2;
/*     */             }
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 308 */             SummaryBean sbean = new SummaryBean();
/* 309 */             sbean.setUrldata(true);
/* 310 */             sbean.setUrlvalues(urldataMap);
/* 311 */             request.setAttribute("urldatagraph", sbean);
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 332 */             out.write("\n\n<script>\n\n       function getAttributes()\n {\n\t\n\n var a=document.forms[0].dropdownmonitors.value;\n\n\n\n  var url=\"/showAttributes.do?method=sendAttributeDetails&resourcetype=\"+a+\"&todaytime=\"+");
/* 333 */             out.print(System.currentTimeMillis());
/* 334 */             out.write(";\n\n    http.open(\"GET\",url,true);\n    http.onreadystatechange = getAttributeTypes;\n    http.send(null);\n\n  }\n\n\n   function getAttributeTypes()\n{\n\n\n if(http.readyState == 4)\n {\n\n   var result = http.responseText;\n\n    document.getElementById(\"attributedetail\").innerHTML=result;\n    document.getElementById(\"attributedetail\").style.display='block';\n    ");
/* 335 */             if ("true".equals(CG)) {
/* 336 */               out.write("\n\n             var len1=document.forms[0].dropdownattributes.length;\n\n                         for(var i=0;i<len1;i++){\n                             if(document.forms[0].dropdownattributes.options[i].value=='");
/* 337 */               out.print(ad);
/* 338 */               out.write("'){\n                             document.forms[0].dropdownattributes.options[i].selected=true;\n                             }\n                         }\n                         ");
/*     */             } else {
/* 340 */               out.write("\n\n                      var len1=document.forms[0].dropdownattributes.length;\n                       for(var i=0;i<len1;i++){\n                             if(document.forms[0].dropdownattributes.options[i].value=='");
/* 341 */               out.print(graphattributeid);
/* 342 */               out.write("'){\n                             document.forms[0].dropdownattributes.options[i].selected=true;\n                             }\n                         }\n\n\n                     ");
/*     */             }
/* 344 */             out.write("\n\n    }\n\n }\n /**\n                  * Comment\n                  */\n                 function myOnLoad() {\n                toggle_it('pr1');\n\n\n                     ");
/* 345 */             if ((Stime != null) && (falerts == null)) {
/* 346 */               out.write("\n                            toggle_it('pr1');\n                        showDiv('changeGraph');\n\n                        showDiv('attributedetail');\n\n                     ");
/* 347 */             } else if ("true".equals(CG)) {
/* 348 */               out.write("\n                         toggle_it('pr1');\n\n                         var len=document.forms[0].dropdownmonitors.length;\n\n                         for(var i=0;i<len;i++){\n                             if(document.forms[0].dropdownmonitors.options[i].value=='");
/* 349 */               out.print(RESTYPES);
/* 350 */               out.write("'){\n                             document.forms[0].dropdownmonitors.options[i].selected=true;\n                             }\n                         }\n                         document.forms[0].thisstart.value='");
/* 351 */               out.print(cst);
/* 352 */               out.write("';\n                         getAttributes();\n\n\n\n\n                          toggle_it('pr1');\n\n\n                           showDiv('changeGraph');\n                            showDiv('attributedetail');\n                         ");
/*     */             }
/*     */             
/* 355 */             out.write("\n\n\n                     ");
/* 356 */             if ("critical".equals(Stype)) {
/* 357 */               out.write("\n\n                         document.forms[0].criticalmonitors[0].checked=true;\n                     ");
/*     */             } else {
/* 359 */               out.write("\n\n                         document.forms[0].criticalmonitors[1].checked=true;\n                     ");
/*     */             }
/* 361 */             out.write("\n\n                 }\n\n\n    </script>\n\n\n\n\n  ");
/*     */             
/* 363 */             FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/* 364 */             _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 365 */             _jspx_th_html_005fform_005f0.setParent(null);
/*     */             
/* 367 */             _jspx_th_html_005fform_005f0.setAction("/showAttributes.do");
/* 368 */             int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 369 */             if (_jspx_eval_html_005fform_005f0 != 0) {
/*     */               for (;;) {
/* 371 */                 out.write("\n  <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n\n      <tr>\n          <td class=\"anamoly-bg-tile\">\n\n              <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n                  <tr>\n                      <td width=\"50%\"> &nbsp; <span class=\"anomaly\">");
/* 372 */                 out.print(FormatUtil.getString("am.anomaly.text.anomaly"));
/* 373 */                 out.write("</span> <span class=\"dashboard\">");
/* 374 */                 out.print(FormatUtil.getString("am.anomaly.text.dashboard"));
/* 375 */                 out.write("</span> <img src=\"../images/icon_anomaly_search.gif\"  style=\"position:relative; top:8px;\"  /></td>\n                       <td width=\"30%\">\n                           <table>\n                               <tr valign=\"top\">\n                                   <td class=\"bodytext\" width=\"1%\" ><input type=\"radio\" name=\"criticalmonitors\" value=\"critical\" onClick=\"javascript:sendReq()\"></td><td class=\"bodytext\" width=\"45%\"  style=\"white-space:nowrap\" valign=\"middle\">");
/* 376 */                 out.print(FormatUtil.getString("am.anomaly.dropdown.radiobutton.showcritical.text"));
/* 377 */                 out.write("</td>\n\n                               <td class=\"bodytext\" width=\"1%\" ><input type=\"radio\" name=\"criticalmonitors\" value=\"all\" onClick=\"javascript:sendReq()\"></td><td class=\"bodytext\" width=\"65%\"  style=\"white-space:nowrap\" valign=\"middle\">");
/* 378 */                 out.print(FormatUtil.getString("am.anomaly.dropdown.radiobutton.showall.text"));
/* 379 */                 out.write("\n                               </td>\n                                </tr>\n                           </table>\n                       </td>\n                       <td width=\"10%\">  ");
/* 380 */                 if ((allAlertTimes != null) && (allAlertTimes.size() > 0)) {
/* 381 */                   out.write("\n\n            <select name=\"alerttimings\" onChange=\"javascript:getAlertTimeValues()\">\n                <option value='--select--'>");
/* 382 */                   out.print(FormatUtil.getString("am.webclient.anomalydashboard.dropdowm.showalerttime.text"));
/* 383 */                   out.write("</option>\n                ");
/* 384 */                   for (int x = 0; x < allAlertTimes.size(); x++) {
/* 385 */                     Properties p1 = (Properties)allAlertTimes.get(x);
/*     */                     
/* 387 */                     out.write("\n\n                <option value='");
/* 388 */                     out.print(p1.getProperty("value"));
/* 389 */                     out.write(39);
/* 390 */                     out.write(62);
/* 391 */                     out.print(p1.getProperty("label"));
/* 392 */                     out.write("</option>\n\n                ");
/*     */                   }
/* 394 */                   out.write("\n            </select>\n\n         ");
/*     */                 }
/* 396 */                 out.write("</td>\n                      <!-- <td width=\"10%\" align=\"right\"> <img src=\"../images/anamoly-settings.gif\" width=\"85\" height=\"20\" /> &nbsp; </td>-->\n                  </tr>\n\n              </table>\n          </td>\n\n      </tr>\n  </table>\n\n\n\n\n<script language=\"javascript\">\nfunction toggle_it(itemID){\n\nif ((document.getElementById(itemID).style.display == 'none'))\n{\n   document.getElementById(itemID).style.display = 'inline';\n   showDiv('changeGraph');\n   showDiv('attributedetail');\n} else {\n\ndocument.getElementById(itemID).style.display = 'none';\n}\n}\n</script>\n\n\n\n\n      <input type=\"hidden\" name=\"method\">\n          <input type=\"hidden\" name=\"sttime\" value=\"");
/* 397 */                 out.print(timestamps[3]);
/* 398 */                 out.write("\">\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"margin-left:10px;\">\n\n         <input type=\"hidden\" name=\"EID\"  value=\"");
/* 399 */                 out.print(alertid);
/* 400 */                 out.write("\">\n         <input type=\"hidden\" name=\"alerttime\" value=\"null\">\n         <input type=\"hidden\" name=\"aid\"  value=\"null\">\n         <input type=\"hidden\" name=\"rtype\"  value=\"null\">\n         <input type=\"hidden\" name=\"customgraph\"  value=\"false\">\n         <input type=\"hidden\" name=\"customshowtime\"  value=\"null\">\n         <input type=\"hidden\" name=\"oldhaid\"  value=\"");
/* 401 */                 out.print(oldhaid);
/* 402 */                 out.write("\">\n         <input type=\"hidden\" name=\"severityType\"  value=\"clear\">\n         <input type=\"hidden\" name=\"oldalertid\"  value=\"");
/* 403 */                 out.print(oldalertid);
/* 404 */                 out.write("\">\n\n<tr>\n       <td width=\"45%\">\n\n\n         <table cellpadding=\"0\" cellspacing=\"0\"  border=\"0\" style=\"margin-top:10px;\">\n    <tr>\n\n    <td></td>\n\n\n    <td  width=\"100%\" >\n\n        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  align=\"left\" >\n        <tr>\n         <td  width=\"99%\" class=\"anamoly-font\">\n\n\n\n           <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  align=\"left\" >\n              <tr>\n\n\t\t<td width=\"100%\" class=\"anamoly-font\">\n\n\n\n\n\t\t<table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"margin-left:15px;\">\n\t\t<tr>\n\n\t\t<td width=\"15%\">\n\n\t\t<div style=\"float:left;\" class=\"anamoly-second-font\">\n\t\t<img src=\"../images/icon-anamoly-responsetime.gif\"/>  ");
/* 405 */                 out.print(FormatUtil.getString("am.anomaly.heading.basemeterics.text"));
/* 406 */                 out.write("\n\t\t</div>\n\t\t</td>\n\n\n\n\n\n\t\t<td width=\"85%\">\n\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" align=\"left\">\n\n\n\t\t<tr>\n\t\t<td  align=\"center\">\n\t\t<div id=\"pr1\"  style=\"position:relative;  display:none; float:left; right:180px; top:25px; width:100%;   \">\n\t\t<table  cellpadding=\"1\" cellspacing=\"1\"  align=\"center\" border=\"0\">\n\n\n\t\t<tr>\n\t\t<td align=\"right\">\n\t\t<div id=\"changeGraph\">\n\n\n\t\t<select style=\"height:20px; font-size:11px;\" name=\"dropdownmonitors\" onChange=\"javascript:getAttributes()\">\n\t\t<option style=\"height:20px; font-size:11px;\" value='--select--'>---");
/* 407 */                 out.print(FormatUtil.getString("am.anomaly.dropdown.selectmonitor.text"));
/* 408 */                 out.write("---</option>\n\t\t");
/* 409 */                 for (int x = 0; x < dropdownlist.size(); x++) {
/* 410 */                   Properties p1 = (Properties)dropdownlist.get(x);
/*     */                   
/* 412 */                   out.write("\n\n\t\t<option style=\"height:20px; font-size:11px;\" value='");
/* 413 */                   out.print(p1.getProperty("value"));
/* 414 */                   out.write(39);
/* 415 */                   out.write(62);
/* 416 */                   out.print(p1.getProperty("label"));
/* 417 */                   out.write("</option>\n\n\t\t");
/*     */                 }
/* 419 */                 out.write("\n\n\t\t</select>\n\n\t\t</div></td>\n\n\t\t<td style=\"height:20px; font-size:11px;\"><div id=\"attributedetail\">\n\t\t<select name=\"dropdownattributes\" style=\"height:20px;font-size:11px; border:1px inset gray;\">\n\t\t<option  style=\"height:20px;font-size:11px; border:1px inset gray;\" value='--select--'>---");
/* 420 */                 out.print(FormatUtil.getString("am.anomaly.dropdown.selectattribute.text"));
/* 421 */                 out.write("---</option>\n\t\t</select>\n\t\t</div></td>\n\n\n\n\t\t<td  align=\"left\"  class=\"bodytext\">  ");
/* 422 */                 if (_jspx_meth_c_005fchoose_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */                   return;
/* 424 */                 out.write(" <a href=\"#\" ><IMG src=\"../images/calendar-button.gif\" border=\"0\" align=\"top\" vspace=\"2\" id=startTrigger title=\"");
/* 425 */                 out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/* 426 */                 out.write("\"></a>\n\t\t<SCRIPT type=text/javascript>\n\t\tCalendar.setup({\n\t\tinputField     :    \"start\",     // id of the input field\n\t\tifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n\t\tshowsTime\t   :    true,\n\t\tbutton         :    \"startTrigger\",  // trigger for the calendar (button ID)\n\t\ttimeFormat     :    \"24\",\n\t\talign          :    \"Bl\",           // alignment (defaults to \"Bl\")\n\t\tsingleClick    :    true\n\t\t});\n\t\t</SCRIPT>\n\n\t\t</td>\n\n\t\t<td><input style=\"height:20px;\"  type=\"button\" class=\"buttons btn_highlt\" name=\"show\" value=\"");
/* 427 */                 out.print(FormatUtil.getString("Go"));
/* 428 */                 out.write("\" onClick=\"javascript:fnCustomGraph()\">  </td>\n\n\n\n\n\t\t</tr>\n\t\t</table>\n\t\t</div>\n\n\t\t</td>\n\t\t</tr>\n\t\t</table>\n\t\t</td>\n\n\t\t</tr>\n\t\t</table>\n\t\t</td>\n\n            </tr>\n            </table>\n        </td>\n\n       </tr>\n\n\n         </table>\n\n    </td>\n    <td></td>\n\n    </tr>\n\n     <tr>\n     <td width=\"17\"></td>\n\n   <td width=\"99%\" >\n       <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"96%\" style=\"margin-left:15px;\" class=\"lrbtborder\">\n\n           <tr>\n            <td class=\"anamoly-blue-rig-corn \"><img src=\"../images/spacer.gif\" width=\"13\" height=\"25\"></td>\n            <td class=\"anamoly-mid-tile\"  width=\"100%\"></td>\n            <td class=\"anamoly-bgtab-right\" ><img src=\"../images/spacer.gif\" width=\"19\" height=\"25\"></td>\n\n        </tr>\n\n        <tr>\n            <td  width=\"17\" class=\"anamoly-bgtab-left-tile\"><img src=\"../images/spacer.gif\" width=\"17\"></td>\n            <td height=\"170\" align=\"center\" style=\"background-color:#FFFFFF;\" class=\"bodytext\">\n\t    ");
/*     */                 
/* 430 */                 TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 431 */                 _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 432 */                 _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_html_005fform_005f0);
/*     */                 
/* 434 */                 _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("urldatagraph");
/*     */                 
/* 436 */                 _jspx_th_awolf_005ftimechart_005f0.setWidth("800");
/*     */                 
/* 438 */                 _jspx_th_awolf_005ftimechart_005f0.setHeight("200");
/*     */                 
/* 440 */                 _jspx_th_awolf_005ftimechart_005f0.setLegend("true");
/*     */                 
/* 442 */                 _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*     */                 
/* 444 */                 _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString(ADNAME));
/* 445 */                 int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 446 */                 if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 447 */                   if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 448 */                     out = _jspx_page_context.pushBody();
/* 449 */                     _jspx_th_awolf_005ftimechart_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 450 */                     _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*     */                   }
/*     */                   for (;;) {
/* 453 */                     out.write("\n\t    ");
/* 454 */                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 455 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/* 458 */                   if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 459 */                     out = _jspx_page_context.popBody();
/*     */                   }
/*     */                 }
/* 462 */                 if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 463 */                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*     */                 }
/*     */                 
/* 466 */                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 467 */                 out.write("</td>\n           <td class=\"anamoly-bgtab-right-tile\"><img src=\"../images/spacer.gif\" width=\"18\"></td>\n\n        </tr>\n        <tr>\n\t<td class=\"anamoly-bgtab-left-btm\"><img src=\"../images/spacer.gif\" width=\"17\" height=\"12\"></td>\n\t<td class=\"anamoly-mid-tile-btm\"  width=\"98%\"></td>\n\t<td class=\"anamoly-bgtab-right-btm\" ><img src=\"../images/spacer.gif\" width=\"19\" height=\"12\"></td>\n\t</tr>\n       </table>\n\n   </td>\n\n\n\n\n<td  width=\"19\"></td>\n </tr>\n\n\n</table>\n\n\n       </td>\n   </tr>\n</table>\n");
/* 468 */                 int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 469 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 473 */             if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 474 */               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*     */             }
/*     */             
/* 477 */             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 478 */             out.write("\n\n\n\n\n<script>\n\n\n   var len2=document.forms[0].dropdownmonitors.length;\n\n                         for(var i=0;i<len2;i++){\n\n                             if(document.forms[0].dropdownmonitors.options[i].value=='");
/* 479 */             out.print(graphmonitorid);
/* 480 */             out.write("'){\n                             document.forms[0].dropdownmonitors.options[i].selected=true;\n                             }\n                         }\n\n\ngetAttributes();\n\n\n         document.forms[0].thisstart.value='");
/* 481 */             out.print(alarmstarttime);
/* 482 */             out.write("';\n\n\n</script>\n\n<br>\n\n\n\n\n");
/*     */             
/*     */ 
/*     */ 
/* 486 */             Collection keyValues = RMAP.keySet();
/* 487 */             Iterator ite = keyValues.iterator();
/* 488 */             int divid = 1;
/*     */             
/* 490 */             if ("true".equals(CG)) {
/* 491 */               fromAlerts = "true";
/*     */             }
/* 493 */             while (ite.hasNext()) {
/* 494 */               String k1 = ite.next().toString();
/*     */               
/*     */ 
/* 497 */               String divname = "Att_" + k1;
/* 498 */               String cogdivname = "load_" + divid;
/* 499 */               List tempList = new ArrayList();
/* 500 */               Map resourceMaps = (Map)RMAP.get(k1);
/* 501 */               if ("Att_Mac OS".equals(divname)) {
/* 502 */                 divname = "Att_Mac_OS";
/*     */               }
/* 504 */               if ("Att_LDAP Server".equals(divname)) {
/* 505 */                 divname = "Att_LDAP_Server";
/*     */               }
/* 507 */               if ("Att_Web Service".equals(divname)) {
/* 508 */                 divname = "Att_Web_Service";
/*     */               }
/* 510 */               if ("Att_Generic WMI".equals(divname)) {
/* 511 */                 divname = "Att_Generic_WMI";
/*     */               }
/* 513 */               if ("Att_Ping Monitor".equals(divname)) {
/* 514 */                 divname = "Att_Ping_Monitor";
/*     */               }
/* 516 */               if ("Att_Script Monitor".equals(divname)) {
/* 517 */                 divname = "Att_Script_Monitor";
/*     */               }
/* 519 */               if ("Att_SUN PC".equals(divname)) {
/* 520 */                 divname = "Att_SUN_PC";
/*     */               }
/* 522 */               if ("Att_Web Service".equals(divname)) {
/* 523 */                 divname = "Att_Web_Service";
/*     */               }
/* 525 */               if ("Att_VMWare ESX/ESXi".equals(divname)) {
/* 526 */                 divname = "VMWare_ESX_ESXi";
/*     */               }
/*     */               
/*     */ 
/*     */ 
/* 531 */               out.write("\n\n\n\n\n\n\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  >\n   <tr>\n   <td>\n       <div id=");
/* 532 */               out.print(cogdivname);
/* 533 */               out.write(" style=\"display:block;\" >\n\t  <img border=\"0\" src=\"/images/icon_cogwheel.gif\"  >&nbsp;");
/* 534 */               out.print(FormatUtil.getString("am.webclient.schedulereport.showwschedule.loadingmessage.text"));
/* 535 */               out.write("...\n\t  </div>\n       <div id='");
/* 536 */               out.print(divname);
/* 537 */               out.write("' style=\"display:block;\" >\n\n\t  </div><br>\n\n   </td>\n   </tr>\n</table>\n\n    <script>\n\n        var tempArray=0;\n\n  ");
/* 538 */               for (int i = 0; i < timestamps.length; i++) {
/* 539 */                 out.write("\n  tempArray=tempArray+\"@\"+");
/* 540 */                 out.print(timestamps[i]);
/* 541 */                 out.write(59);
/* 542 */                 out.write(10);
/* 543 */                 out.write(10);
/*     */               }
/* 545 */               out.write("\n\n        </script>\n\n\n\n\n\n\n\n\n<script>\n\n    var http");
/* 546 */               out.print(divid);
/* 547 */               out.write(";\n\n    function callAjaxPage");
/* 548 */               out.print(divid);
/* 549 */               out.write("(k2,id,dname,cname,sttime,ettime,custom,isAlerts)\n {\n\n \t//document.getElementById('msg').innerHTML = id;\n        //alert(\"the key is ===>\"+k2);\n        //showDiv(\"attributeloading\");\n  var url;\n  showDiv(cname);\n  elemArr[");
/* 550 */               out.print(divid - 1);
/* 551 */               out.write("]=k2;\n\n    url=\"/jsp/AjaxAttributeDetails.jsp?k1=\"+k2+\"&haid=\"+id+\"&polledtime=\"+sttime+\"&temparray=\"+ettime+\"&iscustom=\"+custom+\"&alerts=\"+isAlerts+\"&currenttime=\"+");
/* 552 */               out.print(System.currentTimeMillis());
/* 553 */               out.write(";\n\n    http");
/* 554 */               out.print(divid);
/* 555 */               out.write("=getHTTPObject();\n\n\n\n     http");
/* 556 */               out.print(divid);
/* 557 */               out.write(".open(\"GET\",url,true);\n\n   http");
/* 558 */               out.print(divid);
/* 559 */               out.write(".onreadystatechange = new Function('getmonitortypes(http");
/* 560 */               out.print(divid);
/* 561 */               out.write(",\\''+dname+'\\',\\''+cname+'\\',\\''+k2+'\\');');\n     http");
/* 562 */               out.print(divid);
/* 563 */               out.write(".send(null);\n\n\n\n\n\n  }\n\n  if(");
/* 564 */               out.print(divid);
/* 565 */               out.write("<=10){\n\n  setTimeout('500');\n  }else{\n      setTimeout('1000');\n  }\n\n\n    callAjaxPage");
/* 566 */               out.print(divid);
/* 567 */               out.write(40);
/* 568 */               out.write(39);
/* 569 */               out.print(k1);
/* 570 */               out.write(39);
/* 571 */               out.write(44);
/* 572 */               out.write(39);
/* 573 */               out.print(haid);
/* 574 */               out.write(39);
/* 575 */               out.write(44);
/* 576 */               out.write(39);
/* 577 */               out.print(divname);
/* 578 */               out.write(39);
/* 579 */               out.write(44);
/* 580 */               out.write(39);
/* 581 */               out.print(cogdivname);
/* 582 */               out.write(39);
/* 583 */               out.write(44);
/* 584 */               out.print(timestamps[1]);
/* 585 */               out.write(",tempArray,");
/* 586 */               out.print(customtime);
/* 587 */               out.write(44);
/* 588 */               out.print(fromAlerts);
/* 589 */               out.write(");\n</script>\n");
/* 590 */               divid++;
/*     */             }
/* 592 */           } catch (Exception ex) { ex.printStackTrace();
/*     */           }
/* 594 */           out.write("\n</body>\n</html>\n");
/*     */         } catch (Exception e) {
/* 596 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/* 600 */       out.write(10);
/* 601 */       out.write(10);
/* 602 */       out.write(10);
/*     */     } catch (Throwable t) {
/* 604 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 605 */         out = _jspx_out;
/* 606 */         if ((out != null) && (out.getBufferSize() != 0))
/* 607 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 608 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 611 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 617 */     PageContext pageContext = _jspx_page_context;
/* 618 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 620 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 621 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 622 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 624 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 626 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 627 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 628 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 629 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 630 */       return true;
/*     */     }
/* 632 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 633 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 638 */     PageContext pageContext = _jspx_page_context;
/* 639 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 641 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 642 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 643 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 644 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 645 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */       for (;;) {
/* 647 */         out.write(32);
/* 648 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 649 */           return true;
/* 650 */         out.write(32);
/* 651 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 652 */           return true;
/* 653 */         out.write(32);
/* 654 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 655 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 659 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 660 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 661 */       return true;
/*     */     }
/* 663 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 664 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 669 */     PageContext pageContext = _jspx_page_context;
/* 670 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 672 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 673 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 674 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*     */     
/* 676 */     _jspx_th_c_005fwhen_005f0.setTest("${requestScope.ReportForm.thisstart != ''}");
/* 677 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 678 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */       for (;;) {
/* 680 */         out.write(10);
/* 681 */         out.write(9);
/* 682 */         out.write(9);
/* 683 */         if (_jspx_meth_html_005ftext_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 684 */           return true;
/* 685 */         out.write(10);
/* 686 */         out.write(9);
/* 687 */         out.write(9);
/* 688 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 689 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 693 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 694 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 695 */       return true;
/*     */     }
/* 697 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 698 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 703 */     PageContext pageContext = _jspx_page_context;
/* 704 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 706 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyle_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 707 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 708 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*     */     
/* 710 */     _jspx_th_html_005ftext_005f0.setSize("15");
/*     */     
/* 712 */     _jspx_th_html_005ftext_005f0.setProperty("thisstart");
/*     */     
/* 714 */     _jspx_th_html_005ftext_005f0.setStyleId("start");
/*     */     
/* 716 */     _jspx_th_html_005ftext_005f0.setReadonly(true);
/*     */     
/* 718 */     _jspx_th_html_005ftext_005f0.setOnchange("javascript:fnSetLastStartTime(this.value)");
/*     */     
/* 720 */     _jspx_th_html_005ftext_005f0.setStyle("height:20px;");
/* 721 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 722 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 723 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyle_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 724 */       return true;
/*     */     }
/* 726 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyle_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 727 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 732 */     PageContext pageContext = _jspx_page_context;
/* 733 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 735 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 736 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 737 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 738 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 739 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*     */       for (;;) {
/* 741 */         out.write(32);
/* 742 */         if (_jspx_meth_html_005ftext_005f1(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 743 */           return true;
/* 744 */         out.write(10);
/* 745 */         out.write(9);
/* 746 */         out.write(9);
/* 747 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 748 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 752 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 753 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 754 */       return true;
/*     */     }
/* 756 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 757 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 762 */     PageContext pageContext = _jspx_page_context;
/* 763 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 765 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyle_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 766 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 767 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*     */     
/* 769 */     _jspx_th_html_005ftext_005f1.setSize("15");
/*     */     
/* 771 */     _jspx_th_html_005ftext_005f1.setProperty("thisstart");
/*     */     
/* 773 */     _jspx_th_html_005ftext_005f1.setStyleId("start");
/*     */     
/* 775 */     _jspx_th_html_005ftext_005f1.setReadonly(true);
/*     */     
/* 777 */     _jspx_th_html_005ftext_005f1.setOnchange("javascript:fnSetLastStartTime(this.value)");
/*     */     
/* 779 */     _jspx_th_html_005ftext_005f1.setStyle("height:20px;");
/* 780 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 781 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 782 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyle_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 783 */       return true;
/*     */     }
/* 785 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyle_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 786 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AttributeDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */