/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
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
/*     */ public final class wsmarguments_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*     */   private String handleBean(String argid, ArrayList fields, int level)
/*     */   {
/*  23 */     StringBuffer buf = new StringBuffer("");
/*  24 */     if ((fields != null) && (!fields.equals("null")))
/*     */     {
/*  26 */       int i = 0; for (int size = fields.size(); i < size; i++)
/*     */       {
/*  28 */         HashMap field = (HashMap)fields.get(i);
/*  29 */         String propid = (String)field.get("propid");
/*  30 */         String propname = (String)field.get("propname");
/*  31 */         String propclass = (String)field.get("propclass");
/*  32 */         int type = Integer.parseInt((String)field.get("proptype"));
/*  33 */         int isarray = Integer.parseInt((String)field.get("isarray"));
/*  34 */         ArrayList value = (ArrayList)field.get("value");
/*  35 */         if (type == 1)
/*     */         {
/*  37 */           String isarraystr = "";
/*  38 */           if (isarray == 1) {
/*  39 */             isarraystr = "[]";
/*     */           }
/*  41 */           buf.append("<div style=\"padding-left:" + level * 24 + "px\">");
/*  42 */           buf.append("<img align=\"middle\" src=\"/images/trend.png\" border=\"0\">");
/*  43 */           buf.append(FormatUtil.getString("am.webclient.wsm.argname.text") + " :<b> " + propname + "</b>  " + FormatUtil.getString("am.webclient.wsm.argtype.text") + " : " + propclass + isarraystr + "</div>");
/*  44 */           String val = "";
/*  45 */           int k = 0; for (int size1 = value.size(); k < size1; k++)
/*     */           {
/*  47 */             if (k != size1 - 1) {
/*  48 */               val = val + value.get(k) + ",";
/*     */             }
/*     */             else {
/*  51 */               val = val + value.get(k);
/*     */             }
/*     */           }
/*  54 */           buf.append("<div style=\"padding-left:" + (level + 1) * 24 + "px\">");
/*  55 */           buf.append("<img align=\"middle\" src=\"/images/trend.png\" border=\"0\">");
/*  56 */           buf.append("<input class=\"formtext\" type=\"text\" size=\"25\" name=\"bean-" + argid + "-" + propid + "\" value=\"" + val + "\">");
/*  57 */           buf.append("</div>");
/*     */         }
/*  59 */         else if (type == 2)
/*     */         {
/*  61 */           buf.append("<div style=\"padding-left:" + (level + 1) * 24 + "px\">" + FormatUtil.getString("am.webclient.wsm.argname.text") + " :<b> " + propname + "</b> " + FormatUtil.getString("am.webclient.wsm.argtype.text") + " : " + propclass + "</div>");
/*  62 */           String s = handleBean(argid, value, level + 2);
/*  63 */           buf.append(s);
/*     */         }
/*     */       }
/*     */     }
/*  67 */     return buf.toString();
/*     */   }
/*     */   
/*  70 */   private static final JspFactory _jspxFactory = ;
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
/*  81 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  85 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  86 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  87 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  91 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  98 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/* 101 */     JspWriter out = null;
/* 102 */     Object page = this;
/* 103 */     JspWriter _jspx_out = null;
/* 104 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/* 108 */       response.setContentType("text/html");
/* 109 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/* 111 */       _jspx_page_context = pageContext;
/* 112 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 113 */       ServletConfig config = pageContext.getServletConfig();
/* 114 */       session = pageContext.getSession();
/* 115 */       out = pageContext.getOut();
/* 116 */       _jspx_out = out;
/*     */       
/* 118 */       out.write("<!--$Id$-->\n\n<head>\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 119 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/* 121 */       out.write("\n/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<title>");
/* 122 */       out.print(FormatUtil.getString("am.webclient.wsm.editargumenttitle.text"));
/* 123 */       out.write("</title>\n</head>\n<script>\nfunction submit()\n{\n\tdocument.form.arguments.submit();\n}\n</script>\n");
/*     */       
/* 125 */       ArrayList args = (ArrayList)request.getAttribute("argumentdetails");
/*     */       
/* 127 */       out.write(10);
/*     */       
/* 129 */       if (args.size() == 0) {
/* 130 */         out.println("The method has no arguments");
/*     */       }
/*     */       
/* 133 */       out.write("\n<div class=\"lrtbdarkborder\">\n  <div class=\"tableheadingbborder\" style=\"position:relative\">");
/* 134 */       out.print(FormatUtil.getString("am.webclient.wsm.operationdetails.text"));
/* 135 */       out.write("</div>\n<span class=\"bodytext\">\n<form name=\"arguments\" method=\"post\" action=\"/wsm.do\" style=\"display:inline\">\n<input type=\"hidden\" name=\"method\" value=\"saveargs\">\n<input type=\"hidden\" name=\"oid\" value=\"");
/* 136 */       out.print(request.getAttribute("oid"));
/* 137 */       out.write("\">\n<input type=\"hidden\" name=\"insid\" value=\"");
/* 138 */       out.print(request.getAttribute("insid"));
/* 139 */       out.write("\">\n<br>\n<div>&nbsp;");
/* 140 */       out.print(FormatUtil.getString("am.webclient.wsm.operdisplayname.text"));
/* 141 */       out.write("&nbsp;&nbsp;&nbsp;<input type=\"text\" size=\"30\" name=\"displayname\" class=\"formtext\" value=\"");
/* 142 */       out.print(request.getAttribute("displayname"));
/* 143 */       out.write("\"></div>\n<br>\n    <div class=\"yellowgrayborder\" style=\"position:relative\">");
/* 144 */       out.print(FormatUtil.getString("am.webclient.wsm.arguementdetails.text"));
/* 145 */       out.write("</div>\n");
/*     */       
/* 147 */       int i = 0; for (int size = args.size(); i < size; i++)
/*     */       {
/* 149 */         HashMap arg = (HashMap)args.get(i);
/* 150 */         String argid = (String)arg.get("argid");
/* 151 */         String argname = (String)arg.get("name");
/* 152 */         String argclass = (String)arg.get("argclass");
/* 153 */         int type = Integer.parseInt((String)arg.get("type"));
/* 154 */         int isarray = Integer.parseInt((String)arg.get("isarray"));
/* 155 */         ArrayList value = (ArrayList)arg.get("value");
/* 156 */         int level = 2;
/*     */         
/* 158 */         if (type == 1)
/*     */         {
/* 160 */           String isarraystr = "";
/* 161 */           if (isarray == 1) {
/* 162 */             isarraystr = "[]";
/*     */           }
/* 164 */           out.println("<br><div>&nbsp;" + FormatUtil.getString("am.webclient.wsm.argname.text") + " : <b>" + argname + "</b>  " + FormatUtil.getString("am.webclient.wsm.argtype.text") + " : " + argclass + isarraystr + "</div>");
/* 165 */           String val = "";
/* 166 */           int k = 0; for (int size1 = value.size(); k < size1; k++)
/*     */           {
/* 168 */             if (k != size1 - 1) {
/* 169 */               val = val + value.get(k) + ",";
/*     */             }
/*     */             else {
/* 172 */               val = val + value.get(k);
/*     */             }
/*     */           }
/* 175 */           out.print("<div style=\"padding-left:" + level * 24 + "px\">");
/* 176 */           out.println("<img src=\"/images/trend.png\" border=\"0\" align=\"absmiddle\">");
/* 177 */           out.println("<input class=\"formtext\" name=\"simple-" + argid + "\" type=\"text\" size=\"25\" value=\"" + val + "\">");
/* 178 */           out.println("</div>");
/*     */         }
/* 180 */         else if (type == 2)
/*     */         {
/* 182 */           out.println("<div>&nbsp;" + FormatUtil.getString("am.webclient.wsm.argname.text") + " : <b>" + argname + "</b> " + FormatUtil.getString("am.webclient.wsm.argtype.text") + " : <b>" + argclass + "</b></div>");
/* 183 */           String s = handleBean(argid, value, level);
/* 184 */           out.println(s);
/*     */         }
/*     */       }
/*     */       
/* 188 */       out.write("\n<br>\n    <div style=\"position:relative;text-align:center\" class=\"tablebottom\">\n      <input class=\"buttons\" type=\"button\" value=\"");
/* 189 */       out.print(FormatUtil.getString("am.webclient.hostdiscovery.qengine.button.update"));
/* 190 */       out.write("\" onclick=\"submit()\">\n      &nbsp;&nbsp;&nbsp;\n      <input type=\"button\" class=\"buttons\" value=\"");
/* 191 */       out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 192 */       out.write("\" onclick=\"window.close();\">\n    </div>\n</form>\n</span>\n</div>\n");
/* 193 */       out.write(10);
/* 194 */       response.setContentType("text/html;charset=UTF-8");
/*     */     } catch (Throwable t) {
/* 196 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 197 */         out = _jspx_out;
/* 198 */         if ((out != null) && (out.getBufferSize() != 0))
/* 199 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 200 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 203 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 209 */     PageContext pageContext = _jspx_page_context;
/* 210 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 212 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 213 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 214 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 216 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 218 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 219 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 220 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 221 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 222 */       return true;
/*     */     }
/* 224 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 225 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\wsmarguments_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */