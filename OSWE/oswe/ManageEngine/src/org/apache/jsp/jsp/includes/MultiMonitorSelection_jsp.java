/*     */ package org.apache.jsp.jsp.includes;
/*     */ 
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspApplicationContext;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.SkipPageException;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class MultiMonitorSelection_jsp
/*     */   extends HttpJspBase
/*     */   implements JspSourceDependent
/*     */ {
/*  35 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  49 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  53 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  55 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  56 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  57 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  58 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  62 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  63 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  64 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  65 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/*  72 */     HttpSession session = null;
/*     */     
/*     */ 
/*  75 */     JspWriter out = null;
/*  76 */     Object page = this;
/*  77 */     JspWriter _jspx_out = null;
/*  78 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  82 */       response.setContentType("text/html;charset=UTF-8");
/*  83 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  85 */       _jspx_page_context = pageContext;
/*  86 */       ServletContext application = pageContext.getServletContext();
/*  87 */       ServletConfig config = pageContext.getServletConfig();
/*  88 */       session = pageContext.getSession();
/*  89 */       out = pageContext.getOut();
/*  90 */       _jspx_out = out;
/*     */       
/*  92 */       out.write("<!-- $Id$ -->\n");
/*  93 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"> </SCRIPT>\n\n");
/*     */       
/*  95 */       String resType = request.getParameter("resType");
/*  96 */       String monType = request.getParameter("monType");
/*  97 */       String resourceid = request.getParameter("resId");
/*  98 */       String attributeID = request.getParameter("attrId");
/*  99 */       String groupTemplate = request.getParameter("groupTemplate");
/* 100 */       String mgName = null;
/* 101 */       if ("true".equals(groupTemplate)) {
/* 102 */         mgName = Constants.getMGName(request.getParameter("haid"));
/*     */       }
/*     */       
/* 105 */       out.write("\n\n<script>\nfunction showSites()\n{\n      document.getElementById(\"showCustomerSites\").style.display=\"block\";\n          document.getElementById(\"multimonid\").checked=false;\n}\n\nfunction showSimilarMonitors(checkboxObj,resourcetype,resourceid,attributeID,isCustSiteContext)\n{\nif(isCustSiteContext)\n{\ndocument.getElementById(\"multimonid1\").checked=false;\ndocument.getElementById(\"showCustomerSites\").style.display=\"none\";\n}       \nthresholdId=-1;\n                if(document.getElementById(\"thresholdlistid\") != null)\n            thresholdId=document.getElementById(\"thresholdlistid\").value;\n                if(!checkboxObj.checked)\n                {\n                        hideDiv('similarmonitors');\n            hideDiv('processingmonitors');\n                }\n                else\n                {\n                        if(document.form3.tabname.value=='anomaly'){\n\n                            showApplySelectedForAnomaly(document.form3.anomid.value,resourcetype,resourceid,attributeID) ;//no i18n\n                        }else{\n");
/* 106 */       out.write("                            showApplySelectedOnChange(thresholdId,resourcetype,resourceid,attributeID) ;\n                        }\n                }\n}\n</script>\n\n<html>\n\n");
/* 107 */       if ((!EnterpriseUtil.isIt360MSPEdition()) || (request.getParameter("admin") == null))
/*     */       {
/* 109 */         if ((DBUtil.hasGlobalConfigValue("showApplyToSimilarMonitors")) && (DBUtil.getGlobalConfigValueasBoolean("showApplyToSimilarMonitors")))
/*     */         {
/*     */ 
/* 112 */           out.write("   \n        ");
/*     */           
/* 114 */           IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 115 */           _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 116 */           _jspx_th_c_005fif_005f0.setParent(null);
/*     */           
/* 118 */           _jspx_th_c_005fif_005f0.setTest("${param.groupTemplate != \"true\" && param.resourceid != \"0\"}");
/* 119 */           int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 120 */           if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */             for (;;) {
/* 122 */               out.write("\n         <table border=\"0\" cellpadding=\"5\" cellspacing=\"0\" width=\"100%\">\n            <tr>\n                <td class=\"bodytext label-align bodytextbold\" width=\"20%\">&nbsp;</td>\n                <td class=\"bodytext\">\n                    <input style=\"padding-left:0px;\" id=\"multimonid\" type=\"checkbox\" name=\"multimonitors\" value=\"applyselected\" onclick=\"javascript:showSimilarMonitors(this,'");
/* 123 */               out.print(resType);
/* 124 */               out.write(39);
/* 125 */               out.write(44);
/* 126 */               out.print(resourceid);
/* 127 */               out.write(44);
/* 128 */               out.print(attributeID);
/* 129 */               out.write(",false);\">\n                    ");
/* 130 */               out.print(FormatUtil.getString("am.webclient.configurealert.selectmonitors"));
/* 131 */               out.write("\n                </td>\n            </tr>\n         </table>\n        ");
/* 132 */               int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 133 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 137 */           if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 138 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*     */           }
/*     */           
/* 141 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 142 */           out.write("\n    ");
/*     */         }
/*     */         
/*     */ 
/* 146 */         out.write("\n        ");
/*     */         
/* 148 */         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 149 */         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 150 */         _jspx_th_c_005fif_005f1.setParent(null);
/*     */         
/* 152 */         _jspx_th_c_005fif_005f1.setTest("${param.resourceid == \"0\"}");
/* 153 */         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 154 */         if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */           for (;;) {
/* 156 */             out.write("\n            \n                        ");
/*     */             
/* 158 */             ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 159 */             _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 160 */             _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_c_005fif_005f1);
/* 161 */             int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 162 */             if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */               for (;;) {
/* 164 */                 out.write("\n                            ");
/*     */                 
/* 166 */                 WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 167 */                 _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 168 */                 _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*     */                 
/* 170 */                 _jspx_th_c_005fwhen_005f0.setTest("${param.groupTemplate == \"true\"}");
/* 171 */                 int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 172 */                 if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */                   for (;;) {
/* 174 */                     out.write("\n                            <table border=\"0\" cellpadding=\"0\" cellspacing=\"5\" cellpadding=\"5\" width=\"100%\">\n                                <tr>\n                                    <td width=\"20%\">&nbsp;</td>\n                                    <td class=\"bodytext\">\n                                        <input style=\"padding-left:0px;\" id=\"multimonidtemplateThreshold\" type=\"checkbox\" name=\"overrideConfig\" value=\"overrideThresholdConfig\">\n                                        ");
/* 175 */                     out.print(FormatUtil.getString("am.webclient.configurealert.selectmonitors.group.text", new String[] { mgName }));
/* 176 */                     out.write("\n                                    </td>\n                                </tr>\n                            </table>\n                            ");
/* 177 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 178 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/*     */                 }
/* 182 */                 if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 183 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*     */                 }
/*     */                 
/* 186 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 187 */                 out.write("\n                            ");
/*     */                 
/* 189 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 190 */                 _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 191 */                 _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 192 */                 int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 193 */                 if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*     */                   for (;;) {
/* 195 */                     out.write("\n                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"5\" cellpadding=\"5\" width=\"100%\">\n                                <tr>\n                                    <td width=\"20%\">&nbsp;</td>\n                                    <td class=\"bodytext\">\n                                        <input style=\"padding-left:0px;\" id=\"multimonidtemplateThreshold\" type=\"checkbox\" name=\"overrideConfig\" value=\"overrideThresholdConfig\">\n                                        ");
/* 196 */                     out.print(FormatUtil.getString("am.webclient.configurealert.selectmonitors.monitortype.text", new String[] { monType }));
/* 197 */                     out.write("\n                                    </td>\n                                </tr>\n                            </table>\n                            ");
/* 198 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 199 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/*     */                 }
/* 203 */                 if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 204 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*     */                 }
/*     */                 
/* 207 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 208 */                 out.write("\n                        ");
/* 209 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 210 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 214 */             if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 215 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*     */             }
/*     */             
/* 218 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 219 */             out.write("\n                    \n        ");
/* 220 */             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 221 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 225 */         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 226 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*     */         }
/*     */         
/* 229 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 230 */         out.write("\n        <table border=\"0\" cellpadding=\"5\" cellspacing=\"0\" width=\"100%\">\n            <tr style=\"display:none;\">\n                <td class=\"bodytext label-align bodytextbold\" width=\"20%\">&nbsp;</td>\n                <td class=\"bodytext\">\n                    <input style=\"padding-left:0px;\" id=\"multimonidAnomaly\" type=\"checkbox\" name=\"multimonitors\" value=\"applyselected\" onclick=\"javascript:showSimilarMonitors(this,'");
/* 231 */         out.print(resType);
/* 232 */         out.write(39);
/* 233 */         out.write(44);
/* 234 */         out.print(resourceid);
/* 235 */         out.write(44);
/* 236 */         out.print(attributeID);
/* 237 */         out.write(",false);\">\n                    ");
/* 238 */         out.print(FormatUtil.getString("am.webclient.configurealert.selectmonitors"));
/* 239 */         out.write("\n                </td>\n            </tr>\n        </table>\n");
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 244 */         out.write("\n\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"5\" cellpadding=\"5\" width=\"100%\">\n                <tr>\n                <td width=\"10px\">\n                <input style=\"padding-left:0px;\" id=\"multimonid\"  type=\"radio\" name=\"multimonitors\" value=\"applyselected\" checked onclick=\"javascript:showSimilarMonitors(this,'");
/* 245 */         out.print(resType);
/* 246 */         out.write(39);
/* 247 */         out.write(44);
/* 248 */         out.print(resourceid);
/* 249 */         out.write(44);
/* 250 */         out.print(attributeID);
/* 251 */         out.write(",true);\">\n                </td>\n        <!--script> alert(document.getElementById(\"multimonid\").checked) </script-->\n                <td class=\"bodytext\">");
/* 252 */         out.print(FormatUtil.getString("am.webclient.configurealert.selectmonitors"));
/* 253 */         out.write("</td> ");
/* 254 */         out.write("\n\n<td width=\"10px\">\n                <input style=\"padding-left:0px;\" id=\"multimonid1\"  type=\"radio\" name=\"multimonitors\" value=\"applyselected\" onclick=\"javascript:showSites();\">\n                </td>\n        <!--script> alert(document.getElementById(\"multimonid\").checked) </script-->\n                <td class=\"bodytext\">");
/* 255 */         out.print(FormatUtil.getString("am.webclient.configurealert.select.acrosssite"));
/* 256 */         out.write("</td> ");
/* 257 */         out.write("\n\n                </tr>\n                </table>\n\n\n");
/*     */       }
/*     */       
/*     */ 
/* 261 */       out.write("\n\n\n\n\n</html>\n");
/*     */     } catch (Throwable t) {
/* 263 */       if (!(t instanceof SkipPageException)) {
/* 264 */         out = _jspx_out;
/* 265 */         if ((out != null) && (out.getBufferSize() != 0))
/* 266 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 267 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 270 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\includes\MultiMonitorSelection_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */