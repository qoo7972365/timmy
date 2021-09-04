/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.sql.ResultSet;
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
/*     */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*     */ 
/*     */ public final class EditMGView_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  23 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  29 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/*  30 */   static { _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  39 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  43 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  44 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  45 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  49 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  56 */     javax.servlet.http.HttpSession session = null;
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
/*  76 */       out.write("<!--$Id$--> \n\n\n\n\n\n\n\n\n\n\n\n\n<html>\n");
/*  77 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/<c:out value = \"${selectedskin}\" default=\"${initParam.defaultSkin}\"/>/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  78 */       out.write(10);
/*  79 */       out.write("\n<title>");
/*  80 */       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*     */         return;
/*  82 */       out.write("</title>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n");
/*     */       
/*  84 */       String type = request.getParameter("type");
/*  85 */       String haid = request.getParameter("haid");
/*     */       
/*  87 */       out.write("\n<script>\n    function fnSelectAll(e,frm,checkboxstr)\n    {\n            ToggleAll(e,frm,checkboxstr)\n    }\n    function fnFormSubmit()\n    {\n        var name='selectedresource';\n        document.myform.action=\"/GlobalActions.do?method=updateAttributesinMGView&type=");
/*  88 */       out.print(type);
/*  89 */       out.write("\";\n        if(!checkforOneSelected(document.myform,name))\n\t{\n\t\tif(confirm('");
/*  90 */       out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.donotneedattributes"));
/*  91 */       out.write(" '))\n                {\n                }\n                else\n                {\n                    return;\n                }\n\t}\n        document.myform.submit();\n        window.opener.location.href=\"/showapplication.do?haid=");
/*  92 */       out.print(haid);
/*  93 */       out.write("&method=showApplication&selectM=detailview\";\n        window.close();\n    }\n</script>\n");
/*     */       
/*  95 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  96 */       ArrayList allats = new ArrayList();
/*  97 */       ArrayList allnames = new ArrayList();
/*  98 */       ArrayList ispresent = new ArrayList();
/*  99 */       ArrayList views = new ArrayList();
/*     */       try
/*     */       {
/* 102 */         String qr = "select AM_ATTRIBUTES.attributeid,DISPLAYNAME from AM_ATTRIBUTES,AM_ATTRIBUTES_EXT where RESOURCETYPE='" + type + "' AND AM_ATTRIBUTES_EXT.attributeid=AM_ATTRIBUTES.ATTRIBUTEID AND ATTRIBUTE_LEVEL=1 AND type <>1 and type <>2";
/* 103 */         ResultSet rs = AMConnectionPool.executeQueryStmt(qr);
/* 104 */         String temp = "(";
/* 105 */         while (rs.next())
/*     */         {
/* 107 */           allats.add(rs.getString(1));
/* 108 */           allnames.add(rs.getString(2));
/* 109 */           ispresent.add("0");
/* 110 */           temp = temp + rs.getString(1) + ",";
/*     */         }
/* 112 */         temp = temp.substring(0, temp.length() - 1);
/* 113 */         temp = temp + ")";
/* 114 */         qr = "select ATTRIBUTEID from AM_MGVIEW where ATTRIBUTEID IN" + temp;
/* 115 */         ResultSet rs1 = AMConnectionPool.executeQueryStmt(qr);
/* 116 */         while (rs1.next())
/*     */         {
/* 118 */           views.add(rs1.getString(1));
/*     */         }
/* 120 */         for (int i = 0; i < allats.size(); i++)
/*     */         {
/* 122 */           if (views.contains(allats.get(i)))
/*     */           {
/* 124 */             ispresent.remove(i);
/* 125 */             ispresent.add(i, "1");
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception exc)
/*     */       {
/* 131 */         exc.printStackTrace();
/*     */       }
/*     */       
/* 134 */       out.write("<body>\n<form name=myform action=\"/GlobalActions.do?method=addAttributesinMGView&type=");
/* 135 */       out.print(type);
/* 136 */       out.write("\">\n    <input type=\"hidden\" name=\"method\" value=\"updateAttributesinMGView\"/>\n    <input type=\"hidden\" name=\"type\" value=\"");
/* 137 */       out.print(type);
/* 138 */       out.write("\"/>\n    <table align=\"center\" valign=\"center\" width=\"60%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n        <tr>\n                    <td height=\"25\"  colspan=\"2\" class=\"tableheadingbborder\">");
/* 139 */       out.print(FormatUtil.getString("Available Attributes"));
/* 140 */       out.write("</td>\n                </tr>\n        <tr>\n            <td class=\"columnheading\"><input type=\"checkbox\" onClick=\"javascript:fnSelectAll(this,this.form,'selectedresource')\"></td>\n                    <td class=\"columnheading\">");
/* 141 */       out.print(FormatUtil.getString("webclient.monitorgroupusdetails.Name"));
/* 142 */       out.write("</td>\n        </tr>\n        ");
/*     */       
/* 144 */       for (int i = 0; i < allats.size(); i++)
/*     */       {
/* 146 */         String checked = "";
/* 147 */         if (ispresent.get(i).toString().equals("1"))
/*     */         {
/* 149 */           checked = "checked";
/*     */         }
/* 151 */         String val = (String)allats.get(i);
/* 152 */         if (type.equals("Linux"))
/*     */         {
/* 154 */           val = (String)allnames.get(i);
/*     */         }
/*     */         
/* 157 */         out.write("\n        <tr>\n            \n        <td class=\"common-skin\"><input type=\"checkbox\" name=\"selectedresource\" style=\"position:relative; left:3px;\" value=\"");
/* 158 */         out.print(val);
/* 159 */         out.write(34);
/* 160 */         out.write(32);
/* 161 */         out.print(checked);
/* 162 */         out.write("  /></td>\n            <td class=\"common-skin\">");
/* 163 */         out.print(FormatUtil.getString((String)allnames.get(i)));
/* 164 */         out.write("</td>\n        </tr>\n        ");
/*     */       }
/*     */       
/*     */ 
/* 168 */       out.write("\n        <tr>\n            <td colspan=\"1\" class=\"tablebottom\"><input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 169 */       out.print(FormatUtil.getString("Update"));
/* 170 */       out.write("\" onClick=\"javascript:fnFormSubmit()\"></td>\n            <td colspan=\"1\" class=\"tablebottom\"><input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 171 */       out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.cancel"));
/* 172 */       out.write("\" onClick=\"javascript:window.close()\"></td>\n        </tr>\n    </table>\n</form>\n\n</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/* 174 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 175 */         out = _jspx_out;
/* 176 */         if ((out != null) && (out.getBufferSize() != 0))
/* 177 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 178 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 181 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 187 */     PageContext pageContext = _jspx_page_context;
/* 188 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 190 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 191 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 192 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*     */     
/* 194 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.cam.editattribute.text");
/* 195 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 196 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 197 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 198 */       return true;
/*     */     }
/* 200 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 201 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\EditMGView_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */