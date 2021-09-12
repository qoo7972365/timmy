/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class FaultTemplateOptionsResult_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  20 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  31 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  35 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  36 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  37 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  41 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  48 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  51 */     JspWriter out = null;
/*  52 */     Object page = this;
/*  53 */     JspWriter _jspx_out = null;
/*  54 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  58 */       response.setContentType("text/html;charset=UTF-8");
/*  59 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  61 */       _jspx_page_context = pageContext;
/*  62 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  63 */       ServletConfig config = pageContext.getServletConfig();
/*  64 */       session = pageContext.getSession();
/*  65 */       out = pageContext.getOut();
/*  66 */       _jspx_out = out;
/*     */       
/*  68 */       out.write("\n\n\n\n<html>\n<head>\n\n<link href=\"/images/");
/*  69 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  71 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n</head>\n<body leftmargin=\"5\" topmargin=\"5\" marginwidth=\"5\" marginheight=\"5\">\n\n");
/*     */       
/*  73 */       Object res = request.getAttribute("resultresids");
/*  74 */       ArrayList list = new ArrayList();
/*  75 */       String close = FormatUtil.getString("am.webclient.common.close.text");
/*  76 */       if (res != null)
/*     */       {
/*  78 */         list = (ArrayList)res;
/*     */       }
/*  80 */       boolean resultPresent = list.size() > 0;
/*     */       
/*  82 */       out.write("\n\n    <table align=\"center\" width=\"100%\" cellpadding=\"1\" cellspacing=\"0\"  border=\"0\" class=\"lrtborder\">\n          <tr>\n\t        <td width=\"100%\"height=\"28\" valign=\"center\"  class=\"tableheading\">\n\t        ");
/*  83 */       out.print(FormatUtil.getString("am.webclient.faulttemplate.alertconfigresult.text"));
/*  84 */       out.write("</td>\n                </tr>\n\t\t</table>\n\t\t\n\t\t\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n");
/*     */       
/*  86 */       if (resultPresent)
/*     */       {
/*     */ 
/*  89 */         out.write("\n<tr>\n<td>\n<span class=\"bodytextbold\"> &nbsp;&nbsp;");
/*  90 */         out.print(FormatUtil.getString("am.webclient.faulttemplate.configsuccessto.text"));
/*  91 */         out.write("</span>\n</td>\n</tr>\n");
/*     */         
/*  93 */         for (int i = 0; i < list.size(); i++)
/*     */         {
/*     */ 
/*  96 */           String bgclass = "yellowgrayborder";
/*  97 */           if (i % 2 != 0)
/*     */           {
/*  99 */             bgclass = "whitegrayborder";
/*     */           }
/*     */           
/* 102 */           out.write("\n<tr>\n\t<td height=\"22\" align=\"top\" class=\"");
/* 103 */           out.print(bgclass);
/* 104 */           out.write("\">&nbsp;");
/* 105 */           out.print(i + 1);
/* 106 */           out.write(".&nbsp;");
/* 107 */           out.print(list.get(i));
/* 108 */           out.write("</td>\n</tr>\n\n");
/*     */         }
/*     */         
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 115 */         out.write("\n<tr>\n<td>\n");
/* 116 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 117 */           out.write("\n<span class=\"bodytext\"> <br>&nbsp;&nbsp; No monitors matched this particular Monitor Type. <br><br></span>\n");
/*     */         } else {
/* 119 */           out.write("\n<span class=\"bodytext\"> <br>&nbsp;&nbsp;");
/* 120 */           out.print(FormatUtil.getString("am.configuration.template.success"));
/* 121 */           out.write("<br><br></span>\n\n");
/*     */         }
/* 123 */         out.write("\n\n</td>\n</tr>\n  \n");
/*     */       }
/*     */       
/*     */ 
/* 127 */       out.write("\n</table>\n\t<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\" >\n\t<tr class=\"tablebottom\">\n            <td align=\"center\" width=\"72%\" height=\"26\" ><input type=\"button\" class=\"buttons\" onClick=\"javascript:window.close();\" name=\"closebutton\" value=\"");
/* 128 */       out.print(close);
/* 129 */       out.write("\"/></td>\n          </tr>\n\t</table> \n</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/* 131 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 132 */         out = _jspx_out;
/* 133 */         if ((out != null) && (out.getBufferSize() != 0))
/* 134 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 135 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 138 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 144 */     PageContext pageContext = _jspx_page_context;
/* 145 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 147 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 148 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 149 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 151 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 153 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 154 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 155 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 156 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 157 */       return true;
/*     */     }
/* 159 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 160 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\FaultTemplateOptionsResult_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */