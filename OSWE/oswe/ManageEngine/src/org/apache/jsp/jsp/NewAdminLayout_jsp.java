/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.manageengine.appmanager.plugin.PluginUtil;
/*     */ import com.manageengine.appmanager.plugin.RequestUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
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
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.bean.WriteTag;
/*     */ import org.apache.struts.taglib.html.MessagesTag;
/*     */ import org.apache.struts.taglib.logic.MessagesPresentTag;
/*     */ import org.apache.struts.taglib.tiles.InsertTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NewAdminLayout_jsp
/*     */   extends HttpJspBase
/*     */   implements JspSourceDependent
/*     */ {
/*  44 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  50 */   private static Map<String, Long> _jspx_dependants = new HashMap(4);
/*  51 */   static { _jspx_dependants.put("/jsp/includes/jsInclude.jspf", Long.valueOf(1473429417000L));
/*  52 */     _jspx_dependants.put("/jsp/includes/commonIncludes.jspf", Long.valueOf(1473429417000L));
/*  53 */     _jspx_dependants.put("/jsp/includes/PluginIncludes.jspf", Long.valueOf(1473429417000L));
/*  54 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*     */   }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  67 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  71 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  72 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  73 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  74 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  75 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  76 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  77 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  81 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  82 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  83 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*  84 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*  85 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/*  92 */     HttpSession session = null;
/*     */     
/*     */ 
/*  95 */     JspWriter out = null;
/*  96 */     Object page = this;
/*  97 */     JspWriter _jspx_out = null;
/*  98 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/* 102 */       response.setContentType("text/html;charset=UTF-8");
/* 103 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/* 105 */       _jspx_page_context = pageContext;
/* 106 */       ServletContext application = pageContext.getServletContext();
/* 107 */       ServletConfig config = pageContext.getServletConfig();
/* 108 */       session = pageContext.getSession();
/* 109 */       out = pageContext.getOut();
/* 110 */       _jspx_out = out;
/*     */       
/* 112 */       out.write("<!DOCTYPE html>\n");
/* 113 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 114 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 115 */       out.write(10);
/* 116 */       out.write(10);
/* 117 */       out.write(10);
/* 118 */       Properties applications = null;
/* 119 */       synchronized (application) {
/* 120 */         applications = (Properties)_jspx_page_context.getAttribute("applications", 4);
/* 121 */         if (applications == null) {
/* 122 */           applications = new Properties();
/* 123 */           _jspx_page_context.setAttribute("applications", applications, 4);
/*     */         }
/*     */       }
/* 126 */       out.write("   \n\n<html>\n\t<head>\n\n\t\t");
/* 127 */       out.write(10);
/* 128 */       out.write(9);
/* 129 */       out.write(9);
/* 130 */       out.write("<!-- $Id$ -->\n\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\"/>\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n\n<!-- JS include via file -->\n");
/* 131 */       out.write("\n<!-- Scripts getting included in a common place. TODO: Need to merge the script files. This is not applicable for SQL Manager-->\n<script type=\"text/javascript\" src=\"/template/appmanager.js\"></script>\n<script type=\"text/javascript\" src=\"/template/validation.js\"></script>\n<script type=\"text/javascript\" src=\"/template/dnd.js\"></script>\n<script type=\"text/javascript\" src=\"/template/TabDrag.js\"></script>\n<script type=\"text/javascript\" src=\"/template/dropdown.js\"></script>\n<script type=\"text/javascript\" src=\"/template/Dialog.js\"></script>\n<script type=\"text/javascript\" src=\"/template/Utils.js\"></script>\n<script type=\"text/javascript\" src=\"/template/jquery-1.11.0.min.js\"></script>\n<script type=\"text/javascript\" src=\"/template/jquery-migrate-1.2.1.min.js\"></script>\n<script type=\"text/javascript\" src=\"/template/jquery-ui.min.js\" ></script>\n");
/* 132 */       out.write("\n<link href=\"/images/");
/* 133 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/* 135 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n\t\t\n\t\n\t</head>\n\t<body onLoad=\"doInitStuffOnBodyLoad()\">\n\t\t<div id=\"containerDiv\">\n\t\t");
/*     */       
/* 137 */       MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 138 */       _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 139 */       _jspx_th_html_005fmessages_005f0.setParent(null);
/*     */       
/* 141 */       _jspx_th_html_005fmessages_005f0.setId("msg");
/*     */       
/* 143 */       _jspx_th_html_005fmessages_005f0.setMessage("false");
/* 144 */       int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 145 */       if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 146 */         String msg = null;
/* 147 */         if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 148 */           out = _jspx_page_context.pushBody();
/* 149 */           _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/* 150 */           _jspx_th_html_005fmessages_005f0.doInitBody();
/*     */         }
/* 152 */         msg = (String)_jspx_page_context.findAttribute("msg");
/*     */         for (;;) {
/* 154 */           out.write("\n\t\t\t<div class=\"msg-table-width\">\n\t\t\t\t<span class=\"padd-rt-15\"><img\n\t\t\t\t\tsrc=\"../images/icon_message_failure.gif\" class=\"align-middle\"\n\t\t\t\t\talt=\"Icon\" width=\"25\" height=\"25\">\n\t\t\t\t</span>\n\t\t\t\t");
/* 155 */           if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*     */             return;
/* 157 */           out.write("\n\t\t\t</div>\n\t\t");
/* 158 */           int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 159 */           msg = (String)_jspx_page_context.findAttribute("msg");
/* 160 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/* 163 */         if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 164 */           out = _jspx_page_context.popBody();
/*     */         }
/*     */       }
/* 167 */       if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 168 */         this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*     */       }
/*     */       else {
/* 171 */         this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/* 172 */         out.write("\n\n\t\t");
/*     */         
/* 174 */         MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 175 */         _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 176 */         _jspx_th_logic_005fmessagesPresent_005f0.setParent(null);
/*     */         
/* 178 */         _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 179 */         int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 180 */         if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*     */           for (;;) {
/* 182 */             out.write("\n\t\t\t<div class=\"msg-table-width\">\n\t\t\t\t<span class=\"padd-rt-15\"><img\n\t\t\t\t\tsrc=\"../images/icon_message_success.gif\" class=\"align-middle\"\n\t\t\t\t\talt=\"Icon\" width=\"25\" height=\"25\">\n\t\t\t\t</span>\n\t\t\t\t");
/*     */             
/* 184 */             MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 185 */             _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/* 186 */             _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*     */             
/* 188 */             _jspx_th_html_005fmessages_005f1.setId("msg");
/*     */             
/* 190 */             _jspx_th_html_005fmessages_005f1.setMessage("true");
/* 191 */             int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/* 192 */             if (_jspx_eval_html_005fmessages_005f1 != 0) {
/* 193 */               String msg = null;
/* 194 */               if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 195 */                 out = _jspx_page_context.pushBody();
/* 196 */                 _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/* 197 */                 _jspx_th_html_005fmessages_005f1.doInitBody();
/*     */               }
/* 199 */               msg = (String)_jspx_page_context.findAttribute("msg");
/*     */               for (;;) {
/* 201 */                 out.write("\n\t\t\t\t\t");
/* 202 */                 if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*     */                   return;
/* 204 */                 out.write("\n\t\t\t\t");
/* 205 */                 int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/* 206 */                 msg = (String)_jspx_page_context.findAttribute("msg");
/* 207 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/* 210 */               if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 211 */                 out = _jspx_page_context.popBody();
/*     */               }
/*     */             }
/* 214 */             if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/* 215 */               this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*     */             }
/*     */             
/* 218 */             this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/* 219 */             out.write("\n\t\t\t</div>\n\t\t");
/* 220 */             int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 221 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 225 */         if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 226 */           this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*     */         }
/*     */         else {
/* 229 */           this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 230 */           out.write("\n\n\n\t\t<div>\n\t\t\t\t");
/* 231 */           if (_jspx_meth_tiles_005finsert_005f0(_jspx_page_context))
/*     */             return;
/* 233 */           out.write("\n\t\t\t</div>\n\t\t\t<div>\n\t\t\t\t");
/* 234 */           if (_jspx_meth_tiles_005finsert_005f1(_jspx_page_context))
/*     */             return;
/* 236 */           out.write("\n\t\t\t</div>\n\t\t\t<div>\n\t\t\t\t");
/* 237 */           if (_jspx_meth_tiles_005finsert_005f2(_jspx_page_context))
/*     */             return;
/* 239 */           out.write("\n\t\t\t</div>\n\t\t</div>\n\n<script type=\"text/javascript\">\n\ttry\n\t{\n\t  form = document.forms[0];\n\t  if(form && form.action.indexOf(\"Search.do\")<0)  //NO I18N\n\t  {\n\t    $('body').prepend('<form id=\"mySearch\" action=\"/Search.do\" style=\"display:none;\"></form>');   //No I18N\n\t  }\n\t}\n\tcatch(err){\n\n\t}\n</script>\n\t");
/* 240 */           out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 241 */           out.write(10);
/* 242 */           out.write(9);
/* 243 */           out.write(9);
/* 244 */           out.write("<!-- $Id$ -->\n\n<!-- JS include via file -->\n<script src='");
/* 245 */           out.print(request.getContextPath());
/* 246 */           out.write("/template/includeAllScript.js' type='text/javascript'></script>\n<script>\n\tincludeAllScripts('");
/* 247 */           out.print(request.getContextPath());
/* 248 */           out.write("'); //No I18N\n</script>\n");
/* 249 */           if (Constants.isIt360) {
/* 250 */             out.write("<script src='");
/* 251 */             out.print(request.getContextPath());
/* 252 */             out.write("/it360/js/it360general.js' type='text/javascript'></script>");
/*     */           }
/* 254 */           out.write("\n\t\t\n\t\t<script src=\"/template/chosen.jquery.min.js\" type=\"text/javascript\"></script>\n\t\t");
/* 255 */           out.write(10);
/* 256 */           out.write(9);
/* 257 */           out.write(9);
/* 258 */           out.write(10);
/* 259 */           out.write(10);
/* 260 */           out.write(10);
/*     */           
/* 262 */           if (PluginUtil.isPlugin())
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/* 267 */             out.write("\n<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"/template/jquery.ba-postmessage.min.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"/template/IframeResize-child.js\"></SCRIPT>\n<script type=\"text/javascript\">\n\tparentOrigin='");
/* 268 */             out.print(RequestUtil.getURL("", request.getServerName()));
/* 269 */             out.write("';\t//NO I18N\n\t$(document).ready(function(){\n\t\tif(window!=top){\n\t\t\t$('body').attr('style','margin:0px !important');\t//NO I18N \n\t\t\t$('#userAreaContainerDiv').css({'width':'100%','box-shadow':'none','border':'0px','padding':'0px','margin':'5px 0px'}); //NO I18N \n\t\t\t$('.basicLayoutNoLeftWidth').css({'width':'0px'});\t//NO I18N \n\t\t}\n\t});\n</script>\n");
/*     */           }
/*     */           
/*     */ 
/* 273 */           out.write("\n\t\t\n\t</body>\n</html>\n\n");
/*     */         }
/* 275 */       } } catch (Throwable t) { if (!(t instanceof SkipPageException)) {
/* 276 */         out = _jspx_out;
/* 277 */         if ((out != null) && (out.getBufferSize() != 0))
/* 278 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 279 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 282 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 288 */     PageContext pageContext = _jspx_page_context;
/* 289 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 291 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 292 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 293 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 295 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 297 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 298 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 299 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 300 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 301 */       return true;
/*     */     }
/* 303 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 304 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 309 */     PageContext pageContext = _jspx_page_context;
/* 310 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 312 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 313 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 314 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*     */     
/* 316 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*     */     
/* 318 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 319 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 320 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 321 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 322 */       return true;
/*     */     }
/* 324 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 325 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 330 */     PageContext pageContext = _jspx_page_context;
/* 331 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 333 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 334 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 335 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*     */     
/* 337 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*     */     
/* 339 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 340 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 341 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 342 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 343 */       return true;
/*     */     }
/* 345 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 346 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005finsert_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 351 */     PageContext pageContext = _jspx_page_context;
/* 352 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 354 */     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 355 */     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 356 */     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*     */     
/* 358 */     _jspx_th_tiles_005finsert_005f0.setAttribute("UserArea");
/* 359 */     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 360 */     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 361 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 362 */       return true;
/*     */     }
/* 364 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 365 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005finsert_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 370 */     PageContext pageContext = _jspx_page_context;
/* 371 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 373 */     InsertTag _jspx_th_tiles_005finsert_005f1 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 374 */     _jspx_th_tiles_005finsert_005f1.setPageContext(_jspx_page_context);
/* 375 */     _jspx_th_tiles_005finsert_005f1.setParent(null);
/*     */     
/* 377 */     _jspx_th_tiles_005finsert_005f1.setAttribute("HelpContent");
/* 378 */     int _jspx_eval_tiles_005finsert_005f1 = _jspx_th_tiles_005finsert_005f1.doStartTag();
/* 379 */     if (_jspx_th_tiles_005finsert_005f1.doEndTag() == 5) {
/* 380 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 381 */       return true;
/*     */     }
/* 383 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 384 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005finsert_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 389 */     PageContext pageContext = _jspx_page_context;
/* 390 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 392 */     InsertTag _jspx_th_tiles_005finsert_005f2 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 393 */     _jspx_th_tiles_005finsert_005f2.setPageContext(_jspx_page_context);
/* 394 */     _jspx_th_tiles_005finsert_005f2.setParent(null);
/*     */     
/* 396 */     _jspx_th_tiles_005finsert_005f2.setAttribute("Footer");
/* 397 */     int _jspx_eval_tiles_005finsert_005f2 = _jspx_th_tiles_005finsert_005f2.doStartTag();
/* 398 */     if (_jspx_th_tiles_005finsert_005f2.doEndTag() == 5) {
/* 399 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 400 */       return true;
/*     */     }
/* 402 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 403 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\NewAdminLayout_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */