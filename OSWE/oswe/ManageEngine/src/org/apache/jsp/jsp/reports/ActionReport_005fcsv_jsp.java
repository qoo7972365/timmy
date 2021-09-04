/*     */ package org.apache.jsp.jsp.reports;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspApplicationContext;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.logic.IterateTag;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.FormatDateTag;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class ActionReport_005fcsv_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  24 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  36 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  40 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  41 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  42 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  43 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  47 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.release();
/*  48 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.release();
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
/*  75 */       out.write(10);
/*  76 */       out.write(32);
/*  77 */       out.write(10);
/*  78 */       response.setContentType("text/html;charset=" + com.adventnet.appmanager.util.Constants.getCharSet());response.setHeader("Content-Disposition", "attachment;filename=ActionDetails_" + new java.sql.Date(System.currentTimeMillis()) + ".csv");ArrayList actionConfig = (ArrayList)request.getAttribute("actionConfig");
/*  79 */       out.write(32);
/*  80 */       out.write(10);
/*  81 */       out.print(FormatUtil.getString("am.webclient.heading.action.associatedattributes.text.popup.heading"));
/*  82 */       out.write(32);
/*  83 */       out.write(58);
/*  84 */       out.write(32);
/*  85 */       out.print(FormatUtil.getString((String)request.getAttribute("actionName")));
/*  86 */       request.setAttribute("currTime", new java.util.Date(System.currentTimeMillis()));
/*  87 */       out.write(10);
/*  88 */       out.write(34);
/*  89 */       out.print(FormatUtil.getString("am.webclient.managermail.schedulemail.reportgenerated.text"));
/*  90 */       out.write(32);
/*  91 */       out.write(58);
/*  92 */       out.write(32);
/*  93 */       if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_page_context))
/*     */         return;
/*  95 */       out.write(34);
/*  96 */       out.write(10);
/*  97 */       out.print(FormatUtil.getString("am.mypage.monitorname.text"));
/*  98 */       out.write(44);
/*  99 */       out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 100 */       out.write(44);
/* 101 */       out.print(FormatUtil.getString("am.reporttab.attributereport.name.text"));
/* 102 */       out.write(44);
/* 103 */       out.print(FormatUtil.getString("webclient.fault.alarm.severity"));
/* 104 */       out.write(10);
/*     */       
/* 106 */       IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.get(IterateTag.class);
/* 107 */       _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 108 */       _jspx_th_logic_005fiterate_005f0.setParent(null);
/*     */       
/* 110 */       _jspx_th_logic_005fiterate_005f0.setName("actionConfig");
/*     */       
/* 112 */       _jspx_th_logic_005fiterate_005f0.setScope("request");
/*     */       
/* 114 */       _jspx_th_logic_005fiterate_005f0.setId("row");
/* 115 */       int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 116 */       if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 117 */         Object row = null;
/* 118 */         if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 119 */           out = _jspx_page_context.pushBody();
/* 120 */           _jspx_th_logic_005fiterate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 121 */           _jspx_th_logic_005fiterate_005f0.doInitBody();
/*     */         }
/* 123 */         row = _jspx_page_context.findAttribute("row");
/*     */         for (;;) {
/* 125 */           out.write("\n     ");
/* 126 */           String severity = (String)((ArrayList)row).get(3);String dspname = FormatUtil.getString((String)((ArrayList)row).get(4)); if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (Integer.parseInt((String)((ArrayList)row).get(1)) > com.adventnet.appmanager.server.framework.comm.Constants.RANGE)) dspname = dspname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort((String)((ArrayList)row).get(1));
/* 127 */           out.write(32);
/* 128 */           out.print(dspname);
/* 129 */           out.write(44);
/* 130 */           if (((ArrayList)row).get(7) != null) {
/* 131 */             out.print(FormatUtil.getString((String)((ArrayList)row).get(7)));
/*     */           } else {
/* 133 */             out.print(FormatUtil.getString((String)((ArrayList)row).get(5)));
/*     */           }
/* 135 */           out.write(44);
/* 136 */           out.print(FormatUtil.getString((String)((ArrayList)row).get(6)));
/* 137 */           out.write(44);
/* 138 */           if (((String)((ArrayList)row).get(6)).equalsIgnoreCase("Availability")) {
/* 139 */             out.write(32);
/* 140 */             if (severity == null) {
/* 141 */               out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown"));
/* 142 */             } else if (severity.equals("5")) {
/* 143 */               out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up"));
/* 144 */             } else if (severity.equals("1")) {
/* 145 */               out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down"));
/*     */             }
/* 147 */             out.write(32);
/*     */           } else {
/* 149 */             if (severity == null) {
/* 150 */               out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown"));
/* 151 */             } else if (severity.equals("1")) {
/* 152 */               out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical"));
/* 153 */             } else if (severity.equals("4")) {
/* 154 */               out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning"));
/* 155 */             } else if (severity.equals("5")) {
/* 156 */               out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear"));
/*     */             }
/* 158 */             out.write(32);
/*     */           }
/* 160 */           out.write("  \n  ");
/* 161 */           int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 162 */           row = _jspx_page_context.findAttribute("row");
/* 163 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/* 166 */         if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 167 */           out = _jspx_page_context.popBody();
/*     */         }
/*     */       }
/* 170 */       if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 171 */         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*     */       }
/*     */       else
/* 174 */         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*     */     } catch (Throwable t) {
/* 176 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 177 */         out = _jspx_out;
/* 178 */         if ((out != null) && (out.getBufferSize() != 0))
/* 179 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 180 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 183 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fformatDate_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 189 */     PageContext pageContext = _jspx_page_context;
/* 190 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 192 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 193 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/* 194 */     _jspx_th_fmt_005fformatDate_005f0.setParent(null);
/*     */     
/* 196 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${currTime}");
/*     */     
/* 198 */     _jspx_th_fmt_005fformatDate_005f0.setType("both");
/* 199 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/* 200 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/* 201 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 202 */       return true;
/*     */     }
/* 204 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 205 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\ActionReport_005fcsv_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */