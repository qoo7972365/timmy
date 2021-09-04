/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
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
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class Systemoptions_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  28 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  39 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  43 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  44 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  45 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  49 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/*  56 */     HttpSession session = null;
/*     */     
/*     */ 
/*  59 */     JspWriter out = null;
/*  60 */     Object page = this;
/*  61 */     JspWriter _jspx_out = null;
/*  62 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  66 */       response.setContentType("text/html;charset=UTF-8");
/*  67 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  69 */       _jspx_page_context = pageContext;
/*  70 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  71 */       ServletConfig config = pageContext.getServletConfig();
/*  72 */       session = pageContext.getSession();
/*  73 */       out = pageContext.getOut();
/*  74 */       _jspx_out = out;
/*     */       
/*  76 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  77 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  79 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n<body>\n    <table width=\"100%\" border=\"0\" cellpadding=\"4\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  \n  <tr > \n    <td height=\"23\" colspan=\"2\"  class=\"tableheadingbborder\">");
/*  80 */       out.print(FormatUtil.getString("am.webclient.anomaly.systemoptions.text"));
/*  81 */       out.write("</td>\n  </tr>\n   <tr > \n    <td height=\"19\"  class=\"monitorinfoeven\">");
/*  82 */       out.print(FormatUtil.getString("am.webclient.anomaly.usethisoption.text"));
/*  83 */       out.write("</td>\n    <td  class=\"monitorinfoeven\">");
/*  84 */       out.print(FormatUtil.getString("am.webclient.anomaly.meaning.text"));
/*  85 */       out.write("</td>\n  </tr>\n");
/*     */       
/*     */ 
/*  88 */       Map data = com.adventnet.appmanager.util.CustomExpressionUtil.getAllSystemOptions();
/*  89 */       Collection c = data.keySet();
/*  90 */       Iterator itr = c.iterator();
/*  91 */       while (itr.hasNext())
/*     */       {
/*  93 */         String keys = itr.next().toString();
/*  94 */         String value = (String)data.get(keys);
/*     */         
/*     */ 
/*     */ 
/*  98 */         out.write("\n        \n\n \n  <tr > \n    <td width=\"25%\" height=\"19\"  class=\"yellowgrayborderbr\">");
/*  99 */         out.print("$" + keys);
/* 100 */         out.write(" </td>\n    <td width=\"75%\"  class=\"yellowgrayborder\">");
/* 101 */         out.print(FormatUtil.getString(value));
/* 102 */         out.write(" </td>\n  </tr>\n ");
/*     */       }
/* 104 */       out.write("\n \n</table>\n\n\n</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/* 106 */       if (!(t instanceof SkipPageException)) {
/* 107 */         out = _jspx_out;
/* 108 */         if ((out != null) && (out.getBufferSize() != 0))
/* 109 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 110 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 113 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 119 */     PageContext pageContext = _jspx_page_context;
/* 120 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 122 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 123 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 124 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 126 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 128 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 129 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 130 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 131 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 132 */       return true;
/*     */     }
/* 134 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 135 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Systemoptions_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */