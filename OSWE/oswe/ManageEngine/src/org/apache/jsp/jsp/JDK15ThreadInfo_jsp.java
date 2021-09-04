/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
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
/*     */ public final class JDK15ThreadInfo_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  22 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  28 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/*  29 */   static { _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  38 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  42 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  44 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  48 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
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
/*  65 */       response.setContentType("text/html;charset=UTF-8");
/*  66 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  68 */       _jspx_page_context = pageContext;
/*  69 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  70 */       ServletConfig config = pageContext.getServletConfig();
/*  71 */       session = pageContext.getSession();
/*  72 */       out = pageContext.getOut();
/*  73 */       _jspx_out = out;
/*     */       
/*  75 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n");
/*  76 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  77 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  79 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  80 */       out.write(10);
/*  81 */       out.write("\n<style type=\"text/css\">\n\n.critical{\n\tborder-bottom-width: 1px;\n\tborder-bottom-style: solid;\n\tborder-bottom-color: #DEDEDE;\n\theight: 20px;\n\tpadding-left: 3px;\n\tfont-family: Arial, Helvetica, sans-serif;\n\tfont-size: 11px;\n\tbackground:red;\n}\n.red-table-head{background-color:#ff6e6e; color:#fff; font-family:verdana, arial; font-size:12px; font-weight:bold; border:0px solid #ff0000; line-height:22px; } \n.red-table-td-white{background-color:#fff;  font-weight:normal; color:#000; font-size:11px; font-family:verdana, arial; padding:3px;} \n.red-table-td-pink{background-color:#fff2f2; font-weight:normal; color:#000; font-size:11px; font-family:verdana, arial; padding:3px; }\n.yellowgrayborder {background: #f6f6f6; height: 25px !important;}\n.whitegrayborder {background: #fff; height: 25px !important;}\n</style>\n\n<script>\nvar newDisplay; \nif(document.all) \n{\nnewDisplay ='block'; //No I18N\n}\nelse \n{\nnewDisplay ='table-row'; //No I18N\n}  \n\nfunction showTrace(name)\n{\nvar row =document.getElementById(name); \nvar disp = row.style.display; \n");
/*  82 */       out.write(" \nif(disp=='table-row'|| disp=='block')\n{\nrow.style.display = 'none'; //No I18N\n}else{\nrow.style.display = newDisplay;\n}\n}\n \nfunction showAll(name)\n{\nvar showall = document.getElementById('showall_'+name);\nvar rows = document.getElementById(name).rows;\nif(rows[2].style.display == 'none')\n{\nshowall.innerHTML ='&#45;';showall.title =\"");
/*  83 */       out.print(FormatUtil.getString("am.webclient.jdk15.thread.collapse.text"));
/*  84 */       out.write("\"; \n //No I18N\nfor(var i=1;i<rows.length;i++){if(i%2 == 0)\n{\nrows[i].style.display=newDisplay;\n}\n}\n}else{\nshowall.innerHTML = '&#43;'; //No I18N\nshowall.title =\"");
/*  85 */       out.print(FormatUtil.getString("am.webclient.jdk15.thread.expand.text"));
/*  86 */       out.write("\";\ncollapseAll(name);\n}\n}\n \nfunction collapseAll(name)\n{\nvar rows =document.getElementById(name).rows;\nfor(var i=1;i<rows.length;i++){\nif(i%2 == 0)\n{\nrows[i].style.display='none'; //No I18N\n}\n}\n}\n \n</script> \n\n");
/*     */       
/*  88 */       String resourceid = request.getParameter("resourceid");
/*  89 */       DecimalFormat df = new DecimalFormat("#.##");
/*     */       
/*  91 */       out.write("\n</meta>\n");
/*     */       
/*  93 */       String error = (String)request.getAttribute("error");
/*  94 */       if (error != null)
/*     */       {
/*     */ 
/*  97 */         out.write("\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr><td>\n<span class=\"bodytext\">");
/*  98 */         out.print(error);
/*  99 */         out.write("</span>\n</td></tr>\n</table>\n");
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 104 */         String resname = (String)request.getAttribute("displayname");
/* 105 */         boolean first = true;
/* 106 */         long key = 0L;
/* 107 */         if (request.getAttribute("key") != null)
/*     */         {
/* 109 */           key = ((Long)request.getAttribute("key")).longValue();
/*     */         }
/* 111 */         if (request.getAttribute("first") != null)
/*     */         {
/* 113 */           first = ((Boolean)request.getAttribute("first")).booleanValue();
/*     */         }
/*     */         
/*     */ 
/* 117 */         out.write("\n\n\n\n");
/*     */         
/* 119 */         if (first)
/*     */         {
/* 121 */           out.write("\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n\t<tr>\n\t\t<td  class=\"msg-status-tp-left-corn\"></td>\n\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t<td  class=\"msg-status-tp-right-corn\"></td>\n\t</tr>\n     <tr>\n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" border=\"0\">\n\t\t<tr>\n\t\t<td width=\"3%\" class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"icon\" height=\"20\" width=\"20\"></td>\n                <td width=\"98%\" class=\"msg-table-width\">&nbsp;");
/* 122 */           out.print(FormatUtil.getString("calculatethreadscpu"));
/* 123 */           out.write("</td>\n\t\t</tr>\n\t\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n     </tr>\n\n<tr>\n<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n<td class=\"msg-status-btm-right-corn\" >&nbsp;</td>\n</tr>\n</table>\n");
/*     */         }
/* 125 */         out.write("\n\n\n\n\n\n<b><span class=\"bodytext bodytextbold\">");
/* 126 */         out.print(FormatUtil.getString("am.webclient.javaruntime.threaddump.title", new String[] { resname }));
/* 127 */         out.write("</span></b>   ");
/* 128 */         out.write("\n<br><br>\n");
/*     */         
/* 130 */         ArrayList threadinfo = (ArrayList)request.getAttribute("threadinfo");
/* 131 */         String sclass = "yellowgrayborder";
/* 132 */         int s = threadinfo.size();
/* 133 */         HashMap overallinfo = (HashMap)threadinfo.remove(s - 1);
/* 134 */         long totalthreads = ((Long)overallinfo.get("totalthreads")).longValue();
/* 135 */         long running = ((Long)overallinfo.get("running")).longValue();
/* 136 */         long blocked = ((Long)overallinfo.get("blocked")).longValue();
/* 137 */         long waiting = ((Long)overallinfo.get("waiting")).longValue();
/* 138 */         long timedwaiting = ((Long)overallinfo.get("timedwaiting")).longValue();
/* 139 */         long usertime = ((Long)overallinfo.get("usertime")).longValue();
/* 140 */         long cputime = ((Long)overallinfo.get("cputime")).longValue();
/*     */         
/* 142 */         out.write("\n\n\n<table width=\"200px\" id=\"header\" class=\"lrtbdarkborder\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\">\n<tr class=\"yellowgrayborder\"><td><span class=\"bodytext\">");
/* 143 */         out.print(FormatUtil.getString("am.webclient.jdk15.totalthreads.text"));
/* 144 */         out.write("</span></td><td align=\"right\"><span class=\"bodytext\">");
/* 145 */         out.print(totalthreads);
/* 146 */         out.write("</span></td></tr>\n<tr class=\"whitegrayborder\"><td><span class=\"bodytext\">");
/* 147 */         out.print(FormatUtil.getString("am.webclient.jdk15.runnable.text"));
/* 148 */         out.write("</span></td><td align=\"right\"><span class=\"bodytext\">");
/* 149 */         out.print(running);
/* 150 */         out.write("</span></td></tr>\n<tr class=\"yellowgrayborder\"><td><span class=\"bodytext\">");
/* 151 */         out.print(FormatUtil.getString("am.webclient.jdk15.blocked.text"));
/* 152 */         out.write("</span></td><td align=\"right\"><span class=\"bodytext\">");
/* 153 */         out.print(blocked);
/* 154 */         out.write("</span></td>\n<tr class=\"whitegrayborder\"><td><span class=\"bodytext\">");
/* 155 */         out.print(FormatUtil.getString("am.webclient.jdk15.waiting.text"));
/* 156 */         out.write("</span></td><td align=\"right\"><span class=\"bodytext\">");
/* 157 */         out.print(waiting);
/* 158 */         out.write("</span></td></tr>\n<tr class=\"yellowgrayborder\"><td><span class=\"bodytext\">");
/* 159 */         out.print(FormatUtil.getString("am.webclient.jdk15.timedwaiting.text"));
/* 160 */         out.write("</span></td><td align=\"right\"><span class=\"bodytext\">");
/* 161 */         out.print(timedwaiting);
/* 162 */         out.write("</span></td></tr>\n</table>\n<br>\n\n <table id=\"threadtable_ext\" border=\"0\" class=\"lrbtborder\" width=\"100%\" cellpadding=\"2\" cellspacing=\"1\">\n<tr>\n<td width=\"10%\" class=\"columnheadingb\">");
/* 163 */         out.print(FormatUtil.getString("am.webclient.jdk15.thread.id.text"));
/* 164 */         out.write("</td>\n<td width=\"17%\" class=\"columnheadingb\">");
/* 165 */         out.print(FormatUtil.getString("am.webclient.jdk15.thread.name.text"));
/* 166 */         out.write("</td>\n<td width=\"10%\" class=\"columnheadingb\">");
/* 167 */         out.print(FormatUtil.getString("am.webclient.jdk15.thread.state.text"));
/* 168 */         out.write("</td>\n<td width=\"10%\" class=\"columnheadingb\">");
/* 169 */         out.print(FormatUtil.getString("am.webclient.jdk15.thread.usertimeper.text"));
/* 170 */         out.write("</td>\n<td width=\"10%\" class=\"columnheadingb\">");
/* 171 */         out.print(FormatUtil.getString("am.webclient.jdk15.thread.usertime.text"));
/* 172 */         out.write("</td>\n");
/*     */         
/* 174 */         if (!first)
/*     */         {
/* 176 */           out.write("\n<td width=\"10%\" class=\"columnheadingb\">");
/* 177 */           out.print(FormatUtil.getString("User%"));
/* 178 */           out.write("</td>\n");
/*     */         }
/* 180 */         out.write("\n<td width=\"10%\" class=\"columnheadingb\">");
/* 181 */         out.print(FormatUtil.getString("am.webclient.jdk15.thread.cputimeper.text"));
/* 182 */         out.write("</td>\n<td width=\"10%\" class=\"columnheadingb\">");
/* 183 */         out.print(FormatUtil.getString("am.webclient.jdk15.thread.cputime.text"));
/* 184 */         out.write("</td>\n");
/*     */         
/* 186 */         if (!first)
/*     */         {
/* 188 */           out.write("\n<td width=\"10%\" class=\"columnheadingb\">");
/* 189 */           out.print(FormatUtil.getString("CPU%"));
/* 190 */           out.write("</td>\n");
/*     */         }
/* 192 */         out.write("\n\n<td width=\"10%\" class=\"columnheadingb\">");
/* 193 */         out.print(FormatUtil.getString("am.webclient.jdk15.thread.blocked.text"));
/* 194 */         out.write("</td>\n<td width=\"10%\" class=\"columnheadingb\">");
/* 195 */         out.print(FormatUtil.getString("am.webclient.jdk15.thread.waited.text"));
/* 196 */         out.write("</td>\n<td width=\"3%\" class=\"columnheadingb\" align=\"center\" ><a style=\"text-decoration:none;\" href=\"javascript:showAll('threadtable_ext');\" title=\"");
/* 197 */         out.print(FormatUtil.getString("am.webclient.jdk15.thread.expand.text"));
/* 198 */         out.write("\"><big id=\"showall_threadtable_ext\" >&#43;</big></a></td> ");
/* 199 */         out.write("\n</tr>\n");
/*     */         
/* 201 */         int i = 0; for (int size = threadinfo.size(); i < size; i++) {
/* 202 */           HashMap data = (HashMap)threadinfo.get(i);
/* 203 */           sclass = "yellowgrayborder";
/* 204 */           if (i % 2 == 0) {
/* 205 */             sclass = "whitegrayborder";
/*     */           }
/*     */           
/* 208 */           String deadlocked = (String)data.get("deadlocked");
/* 209 */           if (!deadlocked.equals("true"))
/*     */           {
/*     */ 
/* 212 */             out.write("\t\n\t<tr class=\"");
/* 213 */             out.print(sclass);
/* 214 */             out.write("\" style=\"cursor: pointer;\" onclick=\"javascript:showTrace('");
/* 215 */             out.print(data.get("threadid"));
/* 216 */             out.write("');return false;\">\n\t<td>");
/* 217 */             out.print(data.get("threadid"));
/* 218 */             out.write("</td>\n\t<td title=\"");
/* 219 */             out.print(data.get("name"));
/* 220 */             out.write(34);
/* 221 */             out.write(62);
/* 222 */             out.print(FormatUtil.getTrimmedText((String)data.get("name"), 60));
/* 223 */             out.write("</td>\n\t<td>");
/* 224 */             out.print(data.get("state"));
/* 225 */             out.write("</td>\n\t<td align=\"right\">");
/* 226 */             out.print(((Long)data.get("usertime")).longValue() * 100L / usertime);
/* 227 */             out.write("</td>\n\t<td align=\"right\">");
/* 228 */             out.print(data.get("usertime"));
/* 229 */             out.write("</td>\n");
/*     */             
/* 231 */             if (!first)
/*     */             {
/* 233 */               out.write("\n\t<td align=\"right\">");
/* 234 */               out.print(df.format(data.get("user%")));
/* 235 */               out.write("</td>\t\n");
/*     */             }
/* 237 */             out.write("\n\t<td align=\"right\">");
/* 238 */             out.print(((Long)data.get("cputime")).longValue() * 100L / cputime);
/* 239 */             out.write("</td>\n\t<td align=\"right\">");
/* 240 */             out.print(data.get("cputime"));
/* 241 */             out.write("</td>\n");
/*     */             
/* 243 */             if (!first)
/*     */             {
/* 245 */               out.write("\n\t<td align=\"right\">");
/* 246 */               out.print(df.format(data.get("cpu%")));
/* 247 */               out.write("</td>\t\n");
/*     */             }
/* 249 */             out.write("\t\n\t<td align=\"right\">");
/* 250 */             out.print(data.get("blocked"));
/* 251 */             out.write("</td>\n\t<td align=\"right\">");
/* 252 */             out.print(data.get("waited"));
/* 253 */             out.write("</td>\n\t<td nowrap><a href=\"#\" style=\"color:black\">");
/* 254 */             out.print(FormatUtil.getString("am.webclient.jdk15.viewtrace.text"));
/* 255 */             out.write("</a></td>\n\t</tr>\n\t<tr name=\"trace\" class=\"");
/* 256 */             out.print(sclass);
/* 257 */             out.write("\" style=\"display:none\" id=\"");
/* 258 */             out.print(data.get("threadid"));
/* 259 */             out.write(34);
/* 260 */             out.write(62);
/* 261 */             out.write(10);
/*     */             
/* 263 */             if (!first)
/*     */             {
/* 265 */               out.write("\n<td colspan=\"12\" style=\"padding-left:5px\">\n");
/*     */             } else {
/* 267 */               out.write("\n<td colspan=\"10\" style=\"padding-left:5px\">\n");
/*     */             }
/* 269 */             out.write("\n\t \n\t");
/* 270 */             ArrayList trace = (ArrayList)data.get("trace");
/* 271 */             int j = 0; for (int size1 = trace.size(); j < size1; j++)
/*     */             {
/* 273 */               out.write(10);
/* 274 */               out.write(9);
/* 275 */               out.print(trace.get(j));
/* 276 */               out.write("<br>\n\t");
/*     */             }
/*     */             
/*     */ 
/* 280 */             out.write("\n\t</td></tr>\n");
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 285 */         out.write("\n</table>\n\n\n\n<script type=\"text/JavaScript\">\n if(");
/* 286 */         out.print(first);
/* 287 */         out.write("){\n window.open ('/JavaRuntime.do?method=getThreadInfo&resourceid=");
/* 288 */         out.print(resourceid);
/* 289 */         out.write("&first=true&key=");
/* 290 */         out.print(key);
/* 291 */         out.write("','ThreadInfo','scrollbars=yes,resizable=yes');\n }\n</script>\n");
/*     */       }
/* 293 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 294 */         out = _jspx_out;
/* 295 */         if ((out != null) && (out.getBufferSize() != 0))
/* 296 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 297 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 300 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 306 */     PageContext pageContext = _jspx_page_context;
/* 307 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 309 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 310 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 311 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 313 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 315 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 316 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 317 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 318 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 319 */       return true;
/*     */     }
/* 321 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 322 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\JDK15ThreadInfo_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */