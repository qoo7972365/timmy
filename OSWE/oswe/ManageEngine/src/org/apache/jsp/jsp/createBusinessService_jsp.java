/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ 
/*     */ public final class createBusinessService_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  19 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  25 */   private static Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*  26 */   static { _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  38 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  42 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  44 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  45 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  47 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  51 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  52 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  53 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  54 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  61 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  64 */     JspWriter out = null;
/*  65 */     Object page = this;
/*  66 */     JspWriter _jspx_out = null;
/*  67 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  71 */       response.setContentType("text/html");
/*  72 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  74 */       _jspx_page_context = pageContext;
/*  75 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  76 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  77 */       session = pageContext.getSession();
/*  78 */       out = pageContext.getOut();
/*  79 */       _jspx_out = out;
/*     */       
/*  81 */       out.write("<!--$Id$-->\n\t<!DOCTYPE html>\n\t\n\t\n\t");
/*  82 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  83 */       out.write("\n\t\n\t<html>\n\t   <head>\n\t\t  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n\t\t  <title>");
/*  84 */       out.print(FormatUtil.getString("create.business.service"));
/*  85 */       out.write("</title>\n\t\t <link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\"/>\n\t\t  <script type='text/javascript' src='../template/jit.js'></script>\n\t\t  <script type='text/javascript' src='../template/createBusinessService.js'></script>\n\t\t  <script type='text/javascript' src='../template/graphUtil.js'></script>\n\t\t<script type=\"text/javascript\" src=\"/template/chosen.jquery.min.js\"></script>\n\t\t<link href=\"/images/chosen.css\" rel=\"stylesheet\" type=\"text/css\">\n\t\t  <script>\n\t\t\t var startingPoints = ");
/*  86 */       out.print(request.getAttribute("startingpoints"));
/*  87 */       out.write(";\t\n\t\t\tvar fromDiscovery = ");
/*  88 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  90 */       out.write("+\"\";\n\t\t\t availableOptionsAsString = \"<ul>\";\n\t\t\t for(var i in startingPoints){\n\t\t\t\tavailableOptionsAsString += getEachMonDivAsListOption(\"sp_\"+i, i,  startingPoints[i].objDisplayName,  startingPoints[i].typeDisplayName, startingPoints[i].imagepath);\n\t\t\t }\n\t\t\t availableOptionsAsString += \"</ul>\";\n\t\t\tfunction getEachMonDivAsListOption(divId, resourceId,  displayName, resourcetype, imagepath){\n\t\t\t\treturn \"<li id=\\\"\"+divId+\"\\\"><label><input type=\\\"checkbox\\\" value=\\\"\"+resourceId+\"\\\">\" + displayName + \"</label><span>\"+resourcetype+\"<img src=\\\"\"+imagepath+\"\\\"></img></span></li>\";\n\t\t\t}\n\n\t\t\tfunction addDetailsToGraph(type){\n\t\t\t\t/* type 1- Associate Monitor Group\n\t\t\t\t\t2 - Add custom Subgroup  3- add dependent group */\n\t\t\t\tvar subGroupName=$('#subGroupName').val();\t\t\t\t\n\t\t\t\tif(!validateForSubGroups(subGroupName, type)){\n\t\t\t\t\t$('.addCreate').animate({\n\t\t\t\t   scrollTop: $('#subGroupName').offset().top-500\n\t\t\t\t\t},{\n\t\t\t\t  complete : function(){ $('#subGroupName').focus();}\n\t\t\t\t\t}, 500);\n\t\t\t\t\treturn ;\n\t\t\t\t}\n\t\t\t\tif(type === '1'){\n");
/*  91 */       out.write("\t\t\t\t\tassociateMonitorToGraph(editNodeParentId, false);\n\t\t\t\t}else if(type === '2'){\n\t\t\t\t\taddCustomSubgroupToGraph(editNodeParentId, SUBGROUPTYPE, subGroupName, false, \"");
/*  92 */       out.print(FormatUtil.getString("am.webclient.reports.excel.subgroup.text"));
/*  93 */       out.write("\");//No I18N\n\t\t\t\t}else if(type === '3'){\n\t\t\t\t\taddDependentGroup(editNodeParentId);\n\t\t\t\t}else{\n\t\t\t\t\taddPredefinedSubgroup(editNodeParentId, type, subGroupName);//No I18N\n\t\t\t\t}\t\n\t\t\t}\n\n\t\t  </script>\n\t   </head>\n\t   <body>\n\t\t  <!-- Discovery Details -->\n\t\t  <div class=\"boxCon\" id=\"pageHolder\">\n\t\t\t <div class=\"boxHead\">");
/*  94 */       out.print(FormatUtil.getString("create.business.service"));
/*  95 */       out.write("</div>\n\t\t\t <div class=\"conInner createBusiness\">\n\t\t\t\t<div class=\"form-group\">\n\t\t\t\t   <label for=\"inputName\" class=\"control-label\">");
/*  96 */       out.print(FormatUtil.getString("business.service.name"));
/*  97 */       out.write("</label>\n\t\t\t\t   <div class=\"inputBlock\">\n\t\t\t\t\t  <input type=\"text\" class=\"form-control\" id=\"serviceName\" placeholder=\"Name\" onFocus=\"checkServiceName()\">\n\t\t\t\t\t  <div id=\"bsNameErrordisplay\"></div>\n\t\t\t\t   </div>\n\t\t\t\t</div>\n\t\t\t\t");
/*     */       
/*  99 */       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 100 */       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 101 */       _jspx_th_c_005fchoose_005f0.setParent(null);
/* 102 */       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 103 */       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */         for (;;) {
/* 105 */           out.write("\n\t\t\t\t");
/*     */           
/* 107 */           WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 108 */           _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 109 */           _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*     */           
/* 111 */           _jspx_th_c_005fwhen_005f0.setTest("${isADDMAddOnEnabled=='true'}");
/* 112 */           int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 113 */           if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */             for (;;) {
/* 115 */               out.write("\n\t\t\t\t<div class=\"form-group\">\n\t\t\t\t   <div class=\"checkbox offset\">\n\t\t\t\t\t  <label>\n\t\t\t\t\t  <input type=\"checkbox\" name=\"Option1\" id=\"useADDM\" value=\"option3\">\n\t\t\t\t\t  ");
/* 116 */               out.print(FormatUtil.getString("use.application.dependency.mapping"));
/* 117 */               out.write(" </label>\t\n\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t   </div>\n\t\t\t\t</div>\t\t\t\t\n\t\t\t\t");
/* 118 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 119 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 123 */           if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 124 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*     */           }
/*     */           
/* 127 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 128 */           out.write("\t\t\t\t\t\n\t\t\t\t");
/*     */           
/* 130 */           OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 131 */           _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 132 */           _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 133 */           int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 134 */           if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*     */             for (;;) {
/* 136 */               out.write("\n\t\t\t\t<div class=\"form-group\">\n\t\t\t\t   <div class=\"checkbox offset conDisable\">\n\t\t\t\t\t  <label>\n\t\t\t\t\t  <input type=\"checkbox\" disabled name=\"Option1\" id=\"useADDM\" value=\"option3\">\n\t\t\t\t\t  ");
/* 137 */               out.print(FormatUtil.getString("use.application.dependency.mapping"));
/* 138 */               out.write("</label>\n\t\t\t\t   </div><img class=\"addOnDetails\" src=\"/images/icon_addon.gif\" title=\"ADDM Add On\" align=\"top\">");
/* 139 */               if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*     */                 return;
/* 141 */               out.write("\n\t\t\t\t</div>\n\t\t\t\t");
/* 142 */               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 143 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 147 */           if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 148 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*     */           }
/*     */           
/* 151 */           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 152 */           out.write("\n\t\t\t\t");
/* 153 */           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 154 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 158 */       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 159 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*     */       }
/*     */       else {
/* 162 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 163 */         out.write("\n\t\n\t\t\t\t\n\t\t\t\t<!-- Select Servers -->\n\t\t\t\t<div id=\"ADDMdiv\">\n\t\t\t\t <div class=\"form-group  clearfix\">\n\t\t\t\t\t<label for=\"inputName\" class=\"control-label\">");
/* 164 */         out.print(FormatUtil.getString("am.webclient.common.filterby.text"));
/* 165 */         out.write("</label>\n\t\t\t\t\t<div class=\"inputBlock\">\n\t\t\t\t\t\t <span>\n\t\t\t\t\t\t<select  id='searchMonitorInStartPage' class=' chzn-select' > <option value='webServers' selected='true'>");
/* 166 */         out.print(FormatUtil.getString("am.webclient.reports.reportlist.urls.text"));
/* 167 */         out.write("</option><option value='monitorsWithConnection'>");
/* 168 */         out.print(FormatUtil.getString("am.discovery.monitorsWithConnection"));
/* 169 */         out.write("</option><option value='allMonitors'>");
/* 170 */         out.print(FormatUtil.getString("am.webclient.maintenance.All"));
/* 171 */         out.write("</option></select>\n\t\t\t\t\t\t </span>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</div>\n\t\t\t\t\n\t\t\t\t  <div class=\"checkbox offset monitorOuterCon\">\n\t\t\t\t   <div class=\"webServerBox monitorCon\">\n\t\t\t\t\t  <h2>\n\t\t\t\t\t\t<span>\n\t\t\t\t\t\t <input type=\"checkbox\" name=\"Option2\" id=\"selectAllToBeSelected\" value=\"option1\" onclick=\"javascript:selectAll('selectAllToBeSelected','toBeSelectedList')\">\n\t\t\t\t\t\t <label for=\"selectAllToBeSelected\">");
/* 172 */         out.print(FormatUtil.getString("am.webclient.configurealert.availablemonitors"));
/* 173 */         out.write("</label>\n\t\t\t\t\t\t</span>\n\t\t\t\t\t\t<div class=\"searchMonitors\">\n\t\t\t\t\t\t <input type=\"text\" class=\"form-control\" id=\"searchToBeSelected\" placeholder='");
/* 174 */         out.print(FormatUtil.getString("am.mqseries.event.search"));
/* 175 */         out.write("' onkeyup=\"searchOnDivList('searchToBeSelected','toBeSelectedList')\">\n\t\t\t\t\t\t</div>\n\t\t\t\t\t  </h2>\n\t\t\t\t\t  <div class=\"monitorBox\" id=\"toBeSelectedList\">\n\t\t\t\t\t  </div>\n\t\t\t\t   </div>\n\t\t\t\t  \n\t\t\t\t   \n\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t\t<div class=\"form-group\">\n\t\t\t\t   <div class=\"inputBlock inputBlockOffset\">\n\t\t\t\t\t  <button class=\"btn\" id=\"createBusinessService\" onClick=\"proceedToGraph()\">");
/* 176 */         out.print(FormatUtil.getString("preoceed.business.service.creation"));
/* 177 */         out.write("</button>\n\t\t\t\t\t  <button class=\"btn\" onClick=\"cancelBSCreation()\">");
/* 178 */         out.print(FormatUtil.getString("webclient.fault.alarm.customview.button.cancel"));
/* 179 */         out.write("</button>\n\t\t\t\t   </div>\n\t\t\t\t</div>\n\t\t\t\t\n\t\t\t\t</div>\n\t\t\t </div>\n\t\t  <!-- Bussiness Service Graph is loaded here-->\n\t\t  <div class=\"hidden\" id=\"pageHolderForGraph\"></div>\n\t\t  <!-- Error Message Box -->\n\t<div class=\"infoBoxCon closeAnim\" style=\"display: block;\">\n\t<div class=\"infoBox msgError\">\n\n\t<p><i class=\"iconSprite\"></i><span id=\"message\"></span></p>\n\t<div class=\"btnBlock\"><a href=\"#\" class=\"btn btnMboxOk\">OK</a></div> ");
/* 180 */         out.write("\n\t</div>\n\t</div>\n\t\t  <script>\n\t\t  \tfunction showAlertForInvalidName(){\n\t\t\t\talert('");
/* 181 */         out.print(FormatUtil.getString("enter.valid.business.service.name"));
/* 182 */         out.write("');\n\t\t  \t}\n\t\t\tfunction showAlertAskingToChooseMonitors()\n\t\t\t{\n\t\t\t\talert('");
/* 183 */         out.print(FormatUtil.getString("am.discovery.selectOneMonitor"));
/* 184 */         out.write("');\n\t\t\t}\n\t\t  </script>\n\t   </body>\n\t</html>\n");
/*     */       }
/* 186 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 187 */         out = _jspx_out;
/* 188 */         if ((out != null) && (out.getBufferSize() != 0))
/* 189 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 190 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 193 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 199 */     PageContext pageContext = _jspx_page_context;
/* 200 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 202 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 203 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 204 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 206 */     _jspx_th_c_005fout_005f0.setValue("${param.fromDiscovery}");
/* 207 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 208 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 209 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 210 */       return true;
/*     */     }
/* 212 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 213 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 218 */     PageContext pageContext = _jspx_page_context;
/* 219 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 221 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 222 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 223 */     _jspx_th_c_005fout_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fotherwise_005f0);
/*     */     
/* 225 */     _jspx_th_c_005fout_005f1.setValue("${addmMessage}");
/* 226 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 227 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 228 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 229 */       return true;
/*     */     }
/* 231 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 232 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\createBusinessService_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */