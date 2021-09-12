/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.client.wsm.WSMGraph;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.tags.TimeChart;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class wsmigraph_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  22 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
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
/*  39 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  40 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  41 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  45 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  46 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  53 */     HttpSession session = null;
/*     */     
/*     */ 
/*  56 */     JspWriter out = null;
/*  57 */     Object page = this;
/*  58 */     JspWriter _jspx_out = null;
/*  59 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  63 */       response.setContentType("text/html");
/*  64 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  66 */       _jspx_page_context = pageContext;
/*  67 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  68 */       ServletConfig config = pageContext.getServletConfig();
/*  69 */       session = pageContext.getSession();
/*  70 */       out = pageContext.getOut();
/*  71 */       _jspx_out = out;
/*     */       
/*  73 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n  \n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n\n");
/*  74 */       WSMGraph wsmGraph = null;
/*  75 */       wsmGraph = (WSMGraph)_jspx_page_context.getAttribute("wsmGraph", 2);
/*  76 */       if (wsmGraph == null) {
/*  77 */         wsmGraph = new WSMGraph();
/*  78 */         _jspx_page_context.setAttribute("wsmGraph", wsmGraph, 2);
/*     */       }
/*  80 */       out.write(10);
/*     */       
/*  82 */       String resid = request.getParameter("resourceid");
/*  83 */       String insid = request.getParameter("instanceid");
/*     */       
/*  85 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (Integer.parseInt(resid) > com.adventnet.appmanager.server.framework.comm.Constants.RANGE))
/*     */       {
/*  87 */         String selectedskin = "Grey";
/*  88 */         selectedskin = (String)session.getAttribute("selectedskin");
/*  89 */         Map jobInfo = com.adventnet.appmanager.server.framework.comm.CommDBUtil.getDistributedServer(Integer.parseInt(resid));
/*  90 */         String hostName = (String)jobInfo.get("HOST");
/*  91 */         String port = (String)jobInfo.get("PORT");
/*  92 */         String sslPort = (String)jobInfo.get("SSLPORT");
/*     */         
/*  94 */         out.write("\n<script>\n\tlocation.href = 'http://");
/*  95 */         out.print(hostName);
/*  96 */         out.write(58);
/*  97 */         out.print(port);
/*  98 */         out.write("/jsp/wsmigraph.jsp?resourceid=");
/*  99 */         out.print(resid);
/* 100 */         out.write("&instanceid=");
/* 101 */         out.print(insid);
/* 102 */         out.write("&selectedskin=");
/* 103 */         out.print(selectedskin);
/* 104 */         out.write("';\n  </script>\n  ");
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 109 */         out.write("\n<!--Graph starts here -->\n");
/*     */         
/* 111 */         wsmGraph.setParameter(resid, insid, "methodexecution");
/*     */         
/* 113 */         out.write(10);
/*     */         
/* 115 */         String skin = "";
/* 116 */         if (request.getParameter("selectedskin") != null)
/*     */         {
/* 118 */           skin = request.getParameter("selectedskin");
/* 119 */           if (!skin.equals(""))
/*     */           {
/* 121 */             request.setAttribute("selectedskin", skin);
/*     */           }
/*     */         }
/*     */         
/* 125 */         out.write("\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 126 */         if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */           return;
/* 128 */         out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<table width=\"100%\" class=\"lrtbdarkborder\" cellspacing=\"0\" cellpadding=\"5\">\n<tr class=\"tableheadingbborder\">\n<td>");
/* 129 */         out.print(FormatUtil.getString("am.webclient.wsm.operationexecutiontime.text"));
/* 130 */         out.write(32);
/* 131 */         out.write(45);
/* 132 */         out.write(32);
/* 133 */         out.print(wsmGraph.getMethodName(insid));
/* 134 */         out.write("</td>\n</tr>\n<tr>\n<td>\n");
/*     */         
/* 136 */         TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 137 */         _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 138 */         _jspx_th_awolf_005ftimechart_005f0.setParent(null);
/*     */         
/* 140 */         _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("wsmGraph");
/*     */         
/* 142 */         _jspx_th_awolf_005ftimechart_005f0.setWidth("550");
/*     */         
/* 144 */         _jspx_th_awolf_005ftimechart_005f0.setHeight("200");
/*     */         
/* 146 */         _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*     */         
/* 148 */         _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.recent5alerts.columnheader.time"));
/*     */         
/* 150 */         _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.wsm.executiontime.text"));
/* 151 */         int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 152 */         if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 153 */           if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 154 */             out = _jspx_page_context.pushBody();
/* 155 */             _jspx_th_awolf_005ftimechart_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 156 */             _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*     */           }
/*     */           for (;;) {
/* 159 */             out.write(32);
/* 160 */             out.write(10);
/* 161 */             int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 162 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/* 165 */           if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 166 */             out = _jspx_page_context.popBody();
/*     */           }
/*     */         }
/* 169 */         if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 170 */           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*     */         }
/*     */         
/* 173 */         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 174 */         out.write(" \n</td>\n</tr>\n</table>\n<br>\n<table width=\"100%\" border=\"0\">\n<tr>\n<td align=\"center\">\n<input type=\"button\" class=\"buttons\" value=\"");
/* 175 */         out.print(FormatUtil.getString("webclient.common.printview.button.close"));
/* 176 */         out.write("\" onclick=\"window.close()\">\n</td>\n</table>\n");
/*     */       }
/*     */       
/*     */ 
/* 180 */       out.write(10);
/* 181 */       response.setContentType("text/html;charset=UTF-8");
/* 182 */       out.write(10);
/*     */     } catch (Throwable t) {
/* 184 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 185 */         out = _jspx_out;
/* 186 */         if ((out != null) && (out.getBufferSize() != 0))
/* 187 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 188 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 191 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 197 */     PageContext pageContext = _jspx_page_context;
/* 198 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 200 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 201 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 202 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 204 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 206 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 207 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 208 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 209 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 210 */       return true;
/*     */     }
/* 212 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 213 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\wsmigraph_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */