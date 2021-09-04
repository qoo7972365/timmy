/*     */ package org.apache.jsp.webclient.common.jsp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ 
/*     */ public final class home_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  18 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  32 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  36 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  37 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  38 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  39 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  40 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  41 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  45 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  46 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  47 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  48 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  55 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  58 */     JspWriter out = null;
/*  59 */     Object page = this;
/*  60 */     JspWriter _jspx_out = null;
/*  61 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  65 */       response.setContentType("text/html");
/*  66 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  68 */       _jspx_page_context = pageContext;
/*  69 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  70 */       ServletConfig config = pageContext.getServletConfig();
/*  71 */       session = pageContext.getSession();
/*  72 */       out = pageContext.getOut();
/*  73 */       _jspx_out = out;
/*     */       
/*  75 */       out.write("<!DOCTYPE html>\n");
/*  76 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  77 */       out.write("\n\n<!-- This comment is for Instant Gratification to work applications.do -->\n\n");
/*     */       
/*  79 */       pageContext.setAttribute("it360HomePage", Boolean.valueOf(com.adventnet.appmanager.util.Constants.isIt360));
/*  80 */       String showPreLogin = request.getParameter("showPreLogin") != null ? request.getParameter("showPreLogin") : "false";
/*  81 */       pageContext.setAttribute("showPreLogin", showPreLogin);
/*     */       
/*     */ 
/*  84 */       String isMobile = "false";
/*  85 */       String ua = request.getHeader("User-Agent").toLowerCase();
/*  86 */       if ((ua.matches(".*(android.+mobile|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|symbian|treo|up\\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino).*")) || (ua.substring(0, 4).matches("1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|e\\-|e\\/|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(di|rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|xda(\\-|2|g)|yas\\-|your|zeto|zte\\-")))
/*     */       {
/*  88 */         isMobile = "true";
/*     */       }
/*  90 */       pageContext.setAttribute("mobileHomePage", isMobile);
/*  91 */       request.getSession().setAttribute("mobile", isMobile);
/*     */       
/*  93 */       out.write("\n\n<html>\n<head>\n\n<script>\n\n  var showPreLogin = \"\";\n  ");
/*  94 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*     */         return;
/*  96 */       out.write(10);
/*  97 */       out.write(32);
/*  98 */       out.write(32);
/*  99 */       if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*     */         return;
/* 101 */       out.write("\n\n  !window.navigator.onLine?\"false\":'");
/* 102 */       out.print(showPreLogin);
/* 103 */       out.write("';\n  ");
/*     */       
/* 105 */       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 106 */       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 107 */       _jspx_th_c_005fchoose_005f0.setParent(null);
/* 108 */       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 109 */       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */         for (;;) {
/* 111 */           out.write("  \n    ");
/*     */           
/* 113 */           WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 114 */           _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 115 */           _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*     */           
/* 117 */           _jspx_th_c_005fwhen_005f0.setTest("${mobileHomePage=='true'}");
/* 118 */           int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 119 */           if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */             for (;;) {
/* 121 */               out.write("\n      location.href='");
/* 122 */               out.print(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
/* 123 */               out.write("'+\"/mobile/HomeDetails.do?method=showHomePage\"; //No I18N\n    ");
/* 124 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 125 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 129 */           if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 130 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*     */           }
/*     */           
/* 133 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 134 */           out.write("\n    ");
/*     */           
/* 136 */           WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 137 */           _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 138 */           _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f0);
/*     */           
/* 140 */           _jspx_th_c_005fwhen_005f1.setTest("${it360HomePage=='true'}");
/* 141 */           int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 142 */           if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*     */             for (;;) {
/* 144 */               out.write("\n      location.href='");
/* 145 */               out.print(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
/* 146 */               out.write("'+\"/it360/it360Home.do\"; //No I18N\n    ");
/* 147 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 148 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 152 */           if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 153 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*     */           }
/*     */           
/* 156 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 157 */           out.write("\n    ");
/*     */           
/* 159 */           WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 160 */           _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 161 */           _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f0);
/*     */           
/* 163 */           _jspx_th_c_005fwhen_005f2.setTest("${applicationScope.globalconfig.showgettingstarted=='true'}");
/* 164 */           int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 165 */           if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*     */             for (;;) {
/* 167 */               out.write("\n      location.href='");
/* 168 */               out.print(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
/* 169 */               out.write("'+\"/index.do\"+showPreLogin;\n    ");
/* 170 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 171 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 175 */           if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 176 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*     */           }
/*     */           
/* 179 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 180 */           out.write("\n    ");
/*     */           
/* 182 */           OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 183 */           _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 184 */           _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 185 */           int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 186 */           if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*     */             for (;;) {
/* 188 */               out.write("\n      location.href='");
/* 189 */               out.print(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
/* 190 */               out.write("'+\"/MyPage.do?method=viewDashBoard&toredirect=true\"+showPreLogin;\n    ");
/* 191 */               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 192 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 196 */           if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 197 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*     */           }
/*     */           
/* 200 */           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 201 */           out.write(10);
/* 202 */           out.write(32);
/* 203 */           out.write(32);
/* 204 */           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 205 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 209 */       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 210 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*     */       }
/*     */       else {
/* 213 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 214 */         out.write("\n</script>\n");
/*     */       }
/* 216 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 217 */         out = _jspx_out;
/* 218 */         if ((out != null) && (out.getBufferSize() != 0))
/* 219 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 220 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 223 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 229 */     PageContext pageContext = _jspx_page_context;
/* 230 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 232 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 233 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 234 */     _jspx_th_c_005fif_005f0.setParent(null);
/*     */     
/* 236 */     _jspx_th_c_005fif_005f0.setTest("${!empty showPreLogin && showPreLogin=='true'}");
/* 237 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 238 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */       for (;;) {
/* 240 */         out.write("\n    showPreLogin = \"&showPreLogin=true\";\t//NO I18N \n  ");
/* 241 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 242 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 246 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 247 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 248 */       return true;
/*     */     }
/* 250 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 251 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 256 */     PageContext pageContext = _jspx_page_context;
/* 257 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 259 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 260 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 261 */     _jspx_th_c_005fif_005f1.setParent(null);
/*     */     
/* 263 */     _jspx_th_c_005fif_005f1.setTest("${applicationScope.globalconfig.showgettingstarted=='true'}");
/* 264 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 265 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */       for (;;) {
/* 267 */         out.write("\n  \tshowPreLogin = showPreLogin.length>0?\"?\"+showPreLogin:showPreLogin;\n  ");
/* 268 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 269 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 273 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 274 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 275 */       return true;
/*     */     }
/* 277 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 278 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\webclient\common\jsp\home_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */