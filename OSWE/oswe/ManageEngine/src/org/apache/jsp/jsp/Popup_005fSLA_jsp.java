/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.struts.actions.BussinessAction;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
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
/*     */ public final class Popup_005fSLA_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  23 */   private static final JspFactory _jspxFactory = ;
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
/*  34 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  38 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  39 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  40 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  44 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  51 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  54 */     JspWriter out = null;
/*  55 */     Object page = this;
/*  56 */     JspWriter _jspx_out = null;
/*  57 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  61 */       response.setContentType("text/html;charset=UTF-8");
/*  62 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  64 */       _jspx_page_context = pageContext;
/*  65 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  66 */       ServletConfig config = pageContext.getServletConfig();
/*  67 */       session = pageContext.getSession();
/*  68 */       out = pageContext.getOut();
/*  69 */       _jspx_out = out;
/*     */       
/*  71 */       out.write("<!--$Id$-->\n\n<html>\n<head>\n\n\n\n\n\n\n\n\n\n\n\n<title>");
/*  72 */       out.print(FormatUtil.getString("am.webclient.manager.settingstab.heading.text"));
/*  73 */       out.write("</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n\n<link href=\"/images/");
/*  74 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  76 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n\n</head>\n\n<body>\n");
/*     */       
/*  78 */       String sid = request.getParameter("sid");
/*     */       
/*  80 */       Hashtable sladata = new Hashtable();
/*  81 */       ArrayList slaid = new ArrayList();
/*  82 */       BussinessAction bm = new BussinessAction();
/*  83 */       sladata = bm.popupForSla(sid);
/*     */       
/*     */ 
/*  86 */       Properties slaprop = new Properties();
/*     */       
/*  88 */       slaprop = (Properties)sladata.get(sid);
/*     */       
/*  90 */       String sname = slaprop.getProperty("sname");
/*     */       
/*  92 */       String appop = slaprop.getProperty("appop");
/*  93 */       String appval = slaprop.getProperty("appval");
/*  94 */       String sysop = slaprop.getProperty("sysop");
/*  95 */       String sysval = slaprop.getProperty("sysval");
/*  96 */       String tickop = slaprop.getProperty("tickop");
/*  97 */       String tickval = slaprop.getProperty("tickval");
/*  98 */       String type = slaprop.getProperty("type");
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 104 */       out.write("\n        \n<table width=\"100%\" border=\"0\" cellpadding=\"4\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  \n  <tr > \n    <td height=\"23\" colspan=\"2\"  class=\"tableheadingbborder\">");
/* 105 */       out.print(FormatUtil.getString("am.webclient.manager.settingstab.heading.text"));
/* 106 */       out.write("</td>\n  </tr>\n  <tr > \n    <td height=\"19\"  class=\"whitegrayborderbr\">");
/* 107 */       out.print(FormatUtil.getString("am.webclient.manager.popupsla.slatype.text"));
/* 108 */       out.write(" :</td>\n    <td  class=\"whitegrayborder\">");
/* 109 */       out.print(FormatUtil.getString(type));
/* 110 */       out.write("</td>\n  </tr>\n  <tr > \n    <td width=\"25%\" height=\"19\"  class=\"yellowgrayborderbr\">");
/* 111 */       out.print(FormatUtil.getString("am.webclient.manager.slatab.slaname.text"));
/* 112 */       out.write(" :</td>\n    <td width=\"75%\"  class=\"yellowgrayborder\">");
/* 113 */       out.print(FormatUtil.getString(sname));
/* 114 */       out.write(" </td>\n  </tr>\n  <tr > \n    <td height=\"19\"  class=\"whitegrayborderbr\">");
/* 115 */       out.print(FormatUtil.getString("am.webclient.common.availability.text"));
/* 116 */       out.write(" :</td>\n    <td  class=\"whitegrayborder\">");
/* 117 */       out.print(FormatUtil.getString(appop));
/* 118 */       out.write("&nbsp; ");
/* 119 */       out.print(appval);
/* 120 */       out.write(" </td>\n  </tr>\n  <tr > \n    <td height=\"19\"  class=\"yellowgrayborderbr\"> ");
/* 121 */       out.print(FormatUtil.getString("am.webclient.manager.ttslatab.yaxis.text"));
/* 122 */       out.write(" :</td>\n    <td height=\"19\"  class=\"yellowgrayborder\">");
/* 123 */       out.print(FormatUtil.getString(tickop));
/* 124 */       out.write("&nbsp; ");
/* 125 */       out.print(tickval);
/* 126 */       out.write("  </td>\n  </tr>\n  \n \n</table>\n\n\n</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/* 128 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 129 */         out = _jspx_out;
/* 130 */         if ((out != null) && (out.getBufferSize() != 0))
/* 131 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 132 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 135 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 141 */     PageContext pageContext = _jspx_page_context;
/* 142 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 144 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 145 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 146 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 148 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 150 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 151 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 152 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 153 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 154 */       return true;
/*     */     }
/* 156 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 157 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Popup_005fSLA_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */