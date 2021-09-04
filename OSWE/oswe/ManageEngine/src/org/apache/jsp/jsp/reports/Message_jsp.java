/*     */ package org.apache.jsp.jsp.reports;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.bean.WriteTag;
/*     */ import org.apache.struts.taglib.html.MessagesTag;
/*     */ import org.apache.struts.taglib.logic.MessagesPresentTag;
/*     */ 
/*     */ public final class Message_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  18 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  31 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  35 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  36 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  37 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  38 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  39 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  43 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*  44 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  45 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(javax.servlet.http.HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  52 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  55 */     JspWriter out = null;
/*  56 */     Object page = this;
/*  57 */     JspWriter _jspx_out = null;
/*  58 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  62 */       response.setContentType("text/html");
/*  63 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  65 */       _jspx_page_context = pageContext;
/*  66 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  67 */       ServletConfig config = pageContext.getServletConfig();
/*  68 */       session = pageContext.getSession();
/*  69 */       out = pageContext.getOut();
/*  70 */       _jspx_out = out;
/*     */       
/*  72 */       out.write(10);
/*  73 */       out.write("\n\n\n\n");
/*     */       
/*  75 */       MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/*  76 */       _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/*  77 */       _jspx_th_logic_005fmessagesPresent_005f0.setParent(null);
/*     */       
/*  79 */       _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/*  80 */       int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/*  81 */       if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*     */         for (;;) {
/*  83 */           out.write("\n<!--No Data available to generate report-->\n<table width=\"100%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messageboxfailure\">\n  <tr>\n\t<td width=\"6%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"23\" height=\"23\"> \n    </td>\n      \n    <td width=\"94%\" height=\"34\"  class=\"message\"> ");
/*     */           
/*  85 */           MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  86 */           _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/*  87 */           _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*     */           
/*  89 */           _jspx_th_html_005fmessages_005f0.setId("msg");
/*     */           
/*  91 */           _jspx_th_html_005fmessages_005f0.setMessage("true");
/*  92 */           int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/*  93 */           if (_jspx_eval_html_005fmessages_005f0 != 0) {
/*  94 */             String msg = null;
/*  95 */             if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  96 */               out = _jspx_page_context.pushBody();
/*  97 */               _jspx_th_html_005fmessages_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  98 */               _jspx_th_html_005fmessages_005f0.doInitBody();
/*     */             }
/* 100 */             msg = (String)_jspx_page_context.findAttribute("msg");
/*     */             for (;;) {
/* 102 */               out.write(" \n      ");
/* 103 */               if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*     */                 return;
/* 105 */               out.write(32);
/* 106 */               int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 107 */               msg = (String)_jspx_page_context.findAttribute("msg");
/* 108 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/* 111 */             if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 112 */               out = _jspx_page_context.popBody();
/*     */             }
/*     */           }
/* 115 */           if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 116 */             this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*     */           }
/*     */           
/* 119 */           this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/* 120 */           out.write(" </td>\n    </tr>\n</table>\n");
/* 121 */           int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 122 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 126 */       if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 127 */         this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*     */       }
/*     */       else {
/* 130 */         this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 131 */         out.write(10);
/* 132 */         out.write(10);
/*     */       }
/* 134 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 135 */         out = _jspx_out;
/* 136 */         if ((out != null) && (out.getBufferSize() != 0))
/* 137 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 138 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 141 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 147 */     PageContext pageContext = _jspx_page_context;
/* 148 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 150 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 151 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 152 */     _jspx_th_bean_005fwrite_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fmessages_005f0);
/*     */     
/* 154 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*     */     
/* 156 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 157 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 158 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 159 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 160 */       return true;
/*     */     }
/* 162 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 163 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\Message_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */