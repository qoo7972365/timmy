/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ 
/*     */ public final class ResourceDisplay_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*     */   public String getStringForm(Vector v)
/*     */   {
/*  24 */     StringBuffer sbf = new StringBuffer();
/*  25 */     for (int i = 0; i < v.size(); i++)
/*     */     {
/*  27 */       sbf.append(v.get(i));
/*  28 */       if (i != v.size() - 1)
/*  29 */         sbf.append(',');
/*     */     }
/*  31 */     return sbf.toString();
/*     */   }
/*     */   
/*     */   public String getAvailableOptions(ManagedApplication mo)
/*     */   {
/*  36 */     ArrayList list = mo.getRows("select ID , RESOURCENAME , APPNAME from Applications");
/*  37 */     StringBuffer sbf = new StringBuffer();
/*  38 */     for (int i = 0; i < list.size(); i++)
/*     */     {
/*  40 */       ArrayList row = (ArrayList)list.get(i);
/*  41 */       sbf.append("<option value='" + row.get(0) + "'>");
/*  42 */       sbf.append(row.get(1) + ":" + row.get(2));
/*  43 */       sbf.append("</option>");
/*     */     }
/*  45 */     return sbf.toString();
/*     */   }
/*     */   
/*     */ 
/*  49 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  58 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  62 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  63 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  73 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  76 */     JspWriter out = null;
/*  77 */     Object page = this;
/*  78 */     JspWriter _jspx_out = null;
/*  79 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  83 */       response.setContentType("text/html");
/*  84 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  86 */       _jspx_page_context = pageContext;
/*  87 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  88 */       ServletConfig config = pageContext.getServletConfig();
/*  89 */       session = pageContext.getSession();
/*  90 */       out = pageContext.getOut();
/*  91 */       _jspx_out = out;
/*     */       
/*  93 */       out.write(10);
/*     */       
/*  95 */       String defaultappname = request.getParameter("appname");
/*  96 */       String monitortype = request.getParameter("monitortype");
/*     */       
/*     */ 
/*  99 */       out.write(10);
/* 100 */       ManagedApplication mo = null;
/* 101 */       synchronized (application) {
/* 102 */         mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 4);
/* 103 */         if (mo == null) {
/* 104 */           mo = new ManagedApplication();
/* 105 */           _jspx_page_context.setAttribute("mo", mo, 4);
/*     */         }
/*     */       }
/* 108 */       out.write(" \n\n<body topmargin=\"0\">\n<table width=\"603\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr> \n    <td width=\"25\" height=\"29\">&nbsp;</td>\n    <td colspan=\"2\" class=\"whitegrayborderbig\"><span class=\"bodytextbold11\">Select \n      the Resources to be grouped as Application</span></td>\n  </tr>\n  ");
/*     */       
/* 110 */       if (monitortype.equals("network"))
/*     */       {
/*     */ 
/* 113 */         out.write("\n  <tr>\n    <td height=\"18\">&nbsp;</td>\n    <td class=\"bodytext\">&nbsp;</td>\n    <td>&nbsp;</td>\n  </tr>\n  <tr> \n    <td height=\"20\"><img src=\"/images/spacer.gif\" width=\"25\" height=\"21\"></td>\n    <td width=\"251\" height=\"30\" class=\"bodytext\">Resource Type </td>\n    <td width=\"364\" height=\"30\"> \n      <select name=\"resourcetype\" class=\"formtext\" id=\"select2\" onchange=\"javascript:populateAvailableResource()\">\n        ");
/*     */         
/* 115 */         Hashtable table = mo.getDistinctManagedObjects();
/* 116 */         StringBuffer defaultoptions = new StringBuffer("");
/* 117 */         if (defaultappname != null)
/*     */         {
/*     */ 
/* 120 */           ArrayList list = mo.getRows("select RESOURCENAME from ManagedApplicationResources where Name ='" + defaultappname + "'");
/* 121 */           for (int i = 0; i < list.size(); i++)
/*     */           {
/* 123 */             String opt = (String)((ArrayList)list.get(i)).get(0);
/* 124 */             defaultoptions.append("<option value=" + opt + ">");
/* 125 */             defaultoptions.append(opt);
/* 126 */             defaultoptions.append("</option>");
/*     */           }
/*     */         }
/*     */         
/* 130 */         Collection col = table.values();
/* 131 */         Vector all = new Vector();
/* 132 */         Iterator iterator = col.iterator();
/* 133 */         while (iterator.hasNext())
/*     */         {
/* 135 */           all.addAll((Vector)iterator.next());
/*     */         }
/*     */         
/* 138 */         out.write("\n        <option value='[");
/* 139 */         out.print(getStringForm(all));
/* 140 */         out.write("]' selected>Show All Resources</option>\n        ");
/*     */         
/* 142 */         Enumeration types = table.keys();
/* 143 */         while (types.hasMoreElements())
/*     */         {
/* 145 */           String type = (String)types.nextElement();
/* 146 */           Vector v = (Vector)table.get(type);
/*     */           
/* 148 */           out.write("\n        <option value ='");
/* 149 */           out.print("[" + getStringForm(v) + "]");
/* 150 */           out.write(39);
/* 151 */           out.write(62);
/* 152 */           out.print(type);
/* 153 */           out.write("</option>\n        ");
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 158 */         out.write("\n      </select></td>\n  </tr>\n  <tr> \n    <td>&nbsp;</td>\n    <td colspan=\"2\"><table width=\"562\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr> \n          <td width=\"241\" height=\"30\"><span class=\"bodytext\">Available Resources \n            </span></td>\n          <td width=\"63\" height=\"30\">&nbsp;</td>\n          <td width=\"306\" height=\"30\"><span class=\"bodytext\">Selected Resources \n            </span></td>\n        </tr>\n        <tr> \n          <td><select name=\"availableresource\" size=\"10\" multiple class=\"formtextarea\" id=\"select3\">\n              <option selected>--------------Available Resources--------------</option>\n            </select></td>\n          <td width=\"63\" align=\"center\"> <input name=\"Button2\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnSelectResource()\" value=\"&gt;&gt;\"> \n            <br> <br> <input name=\"Button\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnClearOption(document.form1.selectedresource)\" value=\"&lt;&lt;\"> \n          </td>\n          <td><select name=\"selectedresource\" size=\"10\" multiple class=\"formtextarea\" id=\"select4\" >\n");
/* 159 */         out.write("              <option>--------------Selected Resources--------------</option>\n              <!-- ");
/* 160 */         out.print(defaultoptions);
/* 161 */         out.write(" -->\n            </select></td>\n        </tr>\n      </table></td>\n  </tr>\n  <script>\npopulateAvailableResource();\n</script>\n  ");
/*     */ 
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/*     */ 
/* 169 */         out.write("\n  <tr> \n    <td height=\"29\">&nbsp;</td>\n    <td colspan=\"2\"><span class=\"bodytext\">Select the</span> <span class=\"bodytextbold11\"> \n      J2EE Application(s) to be grouped as Application</span></td>\n  </tr>\n  <tr> \n    <td height=\"29\">&nbsp;</td>\n    <td colspan=\"2\"><span class=\"bodytext\"> <table width=\"566\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr> \n          <td width=\"241\" height=\"30\"><span class=\"bodytext\">Available Applications \n            </span></td>\n          <td width=\"63\" height=\"30\">&nbsp;</td>\n          <td width=\"306\" height=\"30\"><span class=\"bodytext\">Selected Applications \n            </span></td>\n        </tr>\n        <tr> \n          <td><select name=\"availableapplications\" size=\"10\" multiple class=\"formtextarea\" id=\"select3\">\n              <option selected>--------------Available Applications--------------</option>\n              ");
/* 170 */         out.print(getAvailableOptions(mo));
/* 171 */         out.write(" </select></td>\n          <td width=\"63\" align=\"center\"> <input name=\"Button2\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnMove(document.form1.availableapplications,document.form1.selectedapplications)\" value=\"&gt;&gt;\"> \n            <br> <br> <input name=\"Button\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnClearOption(document.form1.selectedapplications)\" value=\"&lt;&lt;\"> \n          </td>\n          <td><select name=\"selectedapplications\" size=\"10\" multiple class=\"formtextarea\" id=\"select4\" >\n              <option>--------------Selected Applications--------------</option>\n            </select></td>\n        </tr>\n      </table></td>\n  </tr>\n  ");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 176 */       out.write("\n</table>\n");
/* 177 */       out.write(10);
/*     */     } catch (Throwable t) {
/* 179 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 180 */         out = _jspx_out;
/* 181 */         if ((out != null) && (out.getBufferSize() != 0))
/* 182 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 183 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 186 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ResourceDisplay_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */