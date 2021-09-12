/*     */ package org.apache.jsp.jsp.reports;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.FormatDateTag;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class HASnapshot_005fcsv_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  23 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  34 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  38 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  39 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  40 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  44 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.release();
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
/*  61 */       response.setContentType("text/html");
/*  62 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  64 */       _jspx_page_context = pageContext;
/*  65 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  66 */       ServletConfig config = pageContext.getServletConfig();
/*  67 */       session = pageContext.getSession();
/*  68 */       out = pageContext.getOut();
/*  69 */       _jspx_out = out;
/*     */       
/*  71 */       out.write(10);
/*  72 */       out.write(32);
/*  73 */       out.write(10);
/*     */       
/*  75 */       response.setContentType("text/html;charset=" + com.adventnet.appmanager.util.Constants.getCharSet());
/*  76 */       response.setHeader("Content-Disposition", "attachment;filename=Availability_Health_Snapshot_Report_" + new java.sql.Date(System.currentTimeMillis()) + ".csv");
/*     */       
/*  78 */       out.write(32);
/*  79 */       out.write(10);
/*  80 */       request.setAttribute("currTime", new java.util.Date(System.currentTimeMillis()));
/*  81 */       out.write(10);
/*  82 */       String withhost = (String)request.getAttribute("withhostname");
/*  83 */       boolean isHost = false;
/*  84 */       if ("true".equals(withhost)) {
/*  85 */         isHost = true;
/*     */       }
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
/*  97 */       out.print(FormatUtil.getString("Availability and Health Snapshot Report"));
/*  98 */       out.write(10);
/*  99 */       out.write(10);
/* 100 */       out.print(FormatUtil.getString("webclient.fault.details.properties.source"));
/* 101 */       out.write(44);
/* 102 */       out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.columnheader.availability"));
/* 103 */       out.write(44);
/* 104 */       out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.health"));
/* 105 */       out.write(44);
/* 106 */       out.write(32);
/* 107 */       if (isHost) {
/* 108 */         out.print(FormatUtil.getString("am.webclient.reports.functionalhostname.text"));
/* 109 */         out.write(44);
/*     */       }
/* 111 */       out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 112 */       out.write("    \n\n");
/* 113 */       ArrayList data = (ArrayList)request.getAttribute("data");
/*     */       
/* 115 */       int size = data.size();
/* 116 */       for (int i = 0; i < size; i++)
/*     */       {
/* 118 */         ArrayList a1 = (ArrayList)data.get(i);
/* 119 */         int a1Size = a1.size();
/* 120 */         String name = a1.get(1).toString();
/*     */         
/* 122 */         String avail = "-";
/* 123 */         String Aimg = "";
/* 124 */         String Himg = "";
/* 125 */         String text = " ";
/*     */         
/* 127 */         if (a1Size > 3)
/*     */         {
/* 129 */           avail = a1.get(3).toString();
/* 130 */           String[] t1 = avail.split("#");
/*     */           
/*     */ 
/* 133 */           if (t1.length > 1)
/* 134 */             Aimg = t1[0];
/* 135 */           String health = a1.get(4).toString();
/* 136 */           String[] t2 = health.split("#");
/*     */           
/* 138 */           if (t2.length > 1) {
/* 139 */             Himg = t2[0];
/*     */           }
/* 141 */           text = a1.get(5).toString();
/* 142 */           text = FormatUtil.removeBr(text);
/*     */         }
/*     */         
/* 145 */         if (isHost) {
/* 146 */           ArrayList allMonitors = (ArrayList)a1.get(6);
/* 147 */           for (int j = 0; j < allMonitors.size(); j++) {
/* 148 */             Properties rows = (Properties)allMonitors.get(j);
/* 149 */             String moname = rows.getProperty("moname");
/* 150 */             String momessage = rows.getProperty("momessage");
/* 151 */             if (j == 0) {
/* 152 */               out.print(name);
/* 153 */               out.write(44);
/* 154 */               out.print(Aimg);
/* 155 */               out.write(44);
/* 156 */               out.print(Himg);
/* 157 */               out.write(44);
/* 158 */               out.print(moname);
/* 159 */               out.write(44);
/* 160 */               out.print(momessage);
/* 161 */               out.write(10);
/*     */             }
/*     */             else {
/* 164 */               out.write(44);
/* 165 */               out.write(44);
/* 166 */               out.write(44);
/* 167 */               out.print(moname);
/* 168 */               out.write(44);
/* 169 */               out.print(momessage);
/* 170 */               out.write(10);
/*     */             }
/*     */           }
/* 173 */         } else { out.print(name);
/* 174 */           out.write(44);
/* 175 */           out.print(Aimg);
/* 176 */           out.write(44);
/* 177 */           out.print(Himg);
/* 178 */           out.write(44);
/* 179 */           out.print(text);
/* 180 */           out.write(10);
/*     */         }
/*     */       }
/* 183 */       out.write(10);
/* 184 */       out.write(10);
/* 185 */       out.write(10);
/*     */     } catch (Throwable t) {
/* 187 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 188 */         out = _jspx_out;
/* 189 */         if ((out != null) && (out.getBufferSize() != 0))
/* 190 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 191 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 194 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fformatDate_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 200 */     PageContext pageContext = _jspx_page_context;
/* 201 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 203 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 204 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/* 205 */     _jspx_th_fmt_005fformatDate_005f0.setParent(null);
/*     */     
/* 207 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${currTime}");
/*     */     
/* 209 */     _jspx_th_fmt_005fformatDate_005f0.setType("both");
/* 210 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/* 211 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/* 212 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 213 */       return true;
/*     */     }
/* 215 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 216 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\HASnapshot_005fcsv_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */