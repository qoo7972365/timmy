/*      */ package org.apache.jsp.jsp.amazon;
/*      */ 
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Properties;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.WriteTag;
/*      */ import org.apache.struts.taglib.html.MessagesTag;
/*      */ import org.apache.struts.taglib.logic.EqualTag;
/*      */ import org.apache.struts.taglib.logic.MessagesPresentTag;
/*      */ import org.apache.struts.taglib.logic.NotEqualTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class ManageEC2Instances_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   32 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */   private static java.util.Map<String, Long> _jspx_dependants;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   57 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   61 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   62 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   63 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   64 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   65 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   66 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   67 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   68 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   69 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   70 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   71 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   72 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   73 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   74 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   75 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   76 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   77 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   81 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*   82 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*   83 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*   84 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   85 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   86 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*   87 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   88 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.release();
/*   89 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.release();
/*   90 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname.release();
/*   91 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.release();
/*   92 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*   93 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*   94 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/*   95 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  102 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  105 */     JspWriter out = null;
/*  106 */     Object page = this;
/*  107 */     JspWriter _jspx_out = null;
/*  108 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  112 */       response.setContentType("text/html;charset=UTF-8");
/*  113 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  115 */       _jspx_page_context = pageContext;
/*  116 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  117 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  118 */       session = pageContext.getSession();
/*  119 */       out = pageContext.getOut();
/*  120 */       _jspx_out = out;
/*      */       
/*  122 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link\n\thref=\"/images/");
/*  123 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  125 */       out.write("/style.css\"\n\trel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*      */       try
/*      */       {
/*  128 */         Hashtable<String, String> names_ids = new Hashtable();
/*  129 */         Enumeration<String> resourceIds = null;
/*  130 */         Hashtable<String, String> systemLog = new Hashtable();
/*  131 */         ArrayList<String> systemLogKeys = new ArrayList(java.util.Arrays.asList(new String[] { "Instance ID", "Time Stamp", "Output" }));
/*  132 */         String resIds = "";
/*  133 */         String action = "";
/*  134 */         String title = "";
/*  135 */         String toManage = "";
/*  136 */         String selectedText = "";
/*  137 */         String alertText = "";
/*  138 */         String confirmText = "";
/*      */         
/*      */ 
/*  141 */         if (request.getParameter("resIds") != null)
/*      */         {
/*  143 */           resIds = request.getParameter("resIds");
/*      */         }
/*  145 */         if (request.getAttribute("action") != null)
/*      */         {
/*  147 */           action = (String)request.getAttribute("action");
/*      */           
/*  149 */           if ((request.getAttribute("resourceType") != null) && (((String)request.getAttribute("resourceType")).equals("Docker Container"))) {
/*  150 */             title = (String)request.getAttribute("title");
/*  151 */             selectedText = (String)request.getAttribute("selectedText");
/*  152 */             confirmText = (String)request.getAttribute("confirmText");
/*      */           }
/*  154 */           else if ("true".equals(request.getParameter("wmiMonitors")))
/*      */           {
/*  156 */             title = FormatUtil.getString("am.webclient.amazon." + action.toLowerCase() + ".title.text");
/*  157 */             selectedText = FormatUtil.getString("am.webclient.amazon.ec2instances." + action.toLowerCase() + ".selected.text");
/*  158 */             alertText = "Are you sure want to start the Service?";
/*  159 */             confirmText = FormatUtil.getString("am.webclient.amazon.ec2instanceactions." + action.toLowerCase() + ".confirm.text");
/*      */ 
/*      */           }
/*  162 */           else if (action.equalsIgnoreCase("System Log"))
/*      */           {
/*  164 */             title = FormatUtil.getString("am.webclient.amazon.systemlog.title.text");
/*      */           }
/*  166 */           else if ((action.equalsIgnoreCase("enable")) || (action.equalsIgnoreCase("disable")))
/*      */           {
/*  168 */             title = FormatUtil.getString("am.webclient.amazon." + action.toLowerCase() + "cloudwatch.title.text");
/*  169 */             selectedText = FormatUtil.getString("am.webclient.amazon." + action.toLowerCase() + "cloudwatch.selected.text");
/*  170 */             alertText = FormatUtil.getString("am.webclient.amazon.ec2instanceactions." + action.toLowerCase() + "cloudwatch.alert.text");
/*  171 */             confirmText = FormatUtil.getString("am.webclient.amazon." + action.toLowerCase() + "cloudwatch.confirm.text");
/*      */           }
/*      */           else
/*      */           {
/*  175 */             title = FormatUtil.getString("am.webclient.amazon." + action.toLowerCase() + ".title.text");
/*  176 */             selectedText = FormatUtil.getString("am.webclient.amazon.ec2instances." + action.toLowerCase() + ".selected.text");
/*  177 */             alertText = FormatUtil.getString("am.webclient.amazon.ec2instanceactions." + action.toLowerCase() + ".alert.text");
/*  178 */             confirmText = FormatUtil.getString("am.webclient.amazon.ec2instanceactions." + action.toLowerCase() + ".confirm.text");
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  184 */         if (request.getAttribute("nameids") != null)
/*      */         {
/*  186 */           names_ids = (Hashtable)request.getAttribute("nameids");
/*  187 */           resourceIds = names_ids.keys();
/*      */         }
/*  189 */         if (request.getAttribute("systemLog") != null)
/*      */         {
/*  191 */           systemLog = (Hashtable)request.getAttribute("systemLog");
/*  192 */           systemLog.put("Output", ((String)systemLog.get("Output")).replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
/*      */         }
/*      */         
/*  195 */         out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../../template/appmanager.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\nfunction submitForm(ids,action)   \n{\n\tvar divObj = document.getElementById('prompt');\n\tvar divObjj = document.getElementById( 'processing' );\n\t divObj.innerHTML = divObjj.innerHTML;\n\n\t ");
/*  196 */         if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */           return;
/*  198 */         out.write(10);
/*  199 */         out.write(9);
/*      */         
/*  201 */         NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  202 */         _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/*  203 */         _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */         
/*  205 */         _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/*  206 */         int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/*  207 */         if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */           for (;;) {
/*  209 */             out.write(10);
/*  210 */             out.write(9);
/*  211 */             out.write(9);
/*      */             
/*  213 */             ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  214 */             _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  215 */             _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*  216 */             int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  217 */             if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */               for (;;) {
/*  219 */                 out.write("\n\t\t\t");
/*  220 */                 if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/*      */                   return;
/*  222 */                 out.write("\n\t\t\t");
/*      */                 
/*  224 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  225 */                 _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  226 */                 _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*  227 */                 int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  228 */                 if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                   for (;;) {
/*  230 */                     out.write("\n\t\t\t\t");
/*      */                     
/*  232 */                     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.get(PresentTag.class);
/*  233 */                     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/*  234 */                     _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fotherwise_005f0);
/*      */                     
/*  236 */                     _jspx_th_logic_005fpresent_005f1.setName("resourceType");
/*  237 */                     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/*  238 */                     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                       for (;;) {
/*  240 */                         out.write("\n\t\t\t\t");
/*      */                         
/*  242 */                         EqualTag _jspx_th_logic_005fequal_005f0 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.get(EqualTag.class);
/*  243 */                         _jspx_th_logic_005fequal_005f0.setPageContext(_jspx_page_context);
/*  244 */                         _jspx_th_logic_005fequal_005f0.setParent(_jspx_th_logic_005fpresent_005f1);
/*      */                         
/*  246 */                         _jspx_th_logic_005fequal_005f0.setName("resourceType");
/*      */                         
/*  248 */                         _jspx_th_logic_005fequal_005f0.setValue("Docker Container");
/*  249 */                         int _jspx_eval_logic_005fequal_005f0 = _jspx_th_logic_005fequal_005f0.doStartTag();
/*  250 */                         if (_jspx_eval_logic_005fequal_005f0 != 0) {
/*      */                           for (;;) {
/*  252 */                             out.write("\n\t\t\t\t\tdocument.AMActionForm.action=\"/confActions.do?method=dockerContainerActions&action=\"+action+\"&resIds=\"+ids+\"&parentId=\"+");
/*  253 */                             out.print((String)request.getAttribute("parentId"));
/*  254 */                             out.write(";//No I18N\n\t\t\t\t");
/*  255 */                             int evalDoAfterBody = _jspx_th_logic_005fequal_005f0.doAfterBody();
/*  256 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  260 */                         if (_jspx_th_logic_005fequal_005f0.doEndTag() == 5) {
/*  261 */                           this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f0); return;
/*      */                         }
/*      */                         
/*  264 */                         this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/*  265 */                         out.write("\n\t\t\t\t");
/*  266 */                         if (_jspx_meth_logic_005fnotEqual_005f0(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/*      */                           return;
/*  268 */                         out.write("\n\t\t\t\t\n\t\t\t\t");
/*  269 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/*  270 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  274 */                     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/*  275 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                     }
/*      */                     
/*  278 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f1);
/*  279 */                     out.write("\n\t\t\t\t");
/*  280 */                     if (_jspx_meth_logic_005fnotPresent_005f1(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                       return;
/*  282 */                     out.write("\n\t\t\t");
/*  283 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  284 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  288 */                 if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  289 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                 }
/*      */                 
/*  292 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  293 */                 out.write(10);
/*  294 */                 out.write(9);
/*  295 */                 out.write(9);
/*  296 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  297 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  301 */             if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  302 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */             }
/*      */             
/*  305 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  306 */             out.write("\n\t\tdocument.AMActionForm.submit();\n\t\t");
/*  307 */             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/*  308 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  312 */         if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/*  313 */           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */         }
/*      */         else {
/*  316 */           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*  317 */           out.write("\n\t}\n\t\t\nfunction setFocusProperTextField()  \n{\n\tvar pos = document.AMActionForm.elements.length;\n    if(pos > 0)  \n    { \n\t\tif(document.AMActionForm.elements.length >=2)  \n\t\t{\n        \tpos = 1;\n        }\n        else   \n        {\n        \treturn; \n        }\n\t\tfor(i=0;i<document.AMActionForm.elements.length;i++)   \n\t\t{\n\t\t\tif(document.AMActionForm.elements[i].type =='text')   \n\t\t\t{               \n\t\t\t\ttry    \n\t\t\t\t{\n\t\t\t\t\tdocument.AMActionForm.elements[i].focus(); \n\t\t\t\t\tbreak;\n\t\t\t\t}\n\t\t\t\tcatch (e) {}                    \n           \t}\n       \t}       \n   \t}\n}\nfunction doInitStuffOnBodyLoad()  \n{\n\n    \tsetFocusProperTextField();\n    \tif (window.myOnLoad)   {\n\t\tmyOnLoad();\n    \t}\n}\n</script>\n");
/*      */           
/*      */ 
/*      */ 
/*  321 */           out.write("\n<br>\n<title>");
/*  322 */           out.print(title);
/*  323 */           out.write("\n</title>\n<body onLoad=\"javascript:doInitStuffOnBodyLoad()\"></body>\n<form action=\"/manageEC2Instances.do\" method=\"post\" name=\"AMActionForm\"\tstyle=\"display: inline\">\n\n\n");
/*      */           
/*  325 */           MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  326 */           _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/*  327 */           _jspx_th_html_005fmessages_005f0.setParent(null);
/*      */           
/*  329 */           _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */           
/*  331 */           _jspx_th_html_005fmessages_005f0.setMessage("false");
/*  332 */           int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/*  333 */           if (_jspx_eval_html_005fmessages_005f0 != 0) {
/*  334 */             String msg = null;
/*  335 */             if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  336 */               out = _jspx_page_context.pushBody();
/*  337 */               _jspx_th_html_005fmessages_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  338 */               _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */             }
/*  340 */             msg = (String)_jspx_page_context.findAttribute("msg");
/*      */             for (;;) {
/*  342 */               out.write("\n\t<table width=\"95%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messageboxfailure\" align=\"center\">\n\t\t<tr>\n\t\t\t<td width=\"5%\" align=\"center\"><img\n\t\t\t\tsrc=\"../../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\"\n\t\t\t\theight=\"25\"></td>\n\t\t\t<td width=\"95%\" height=\"28\" class=\"message\">");
/*  343 */               if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                 return;
/*  345 */               out.write("</td>\n\t\t</tr>\n\t</table>\n\t<br>\n");
/*  346 */               int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/*  347 */               msg = (String)_jspx_page_context.findAttribute("msg");
/*  348 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  351 */             if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  352 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  355 */           if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/*  356 */             this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*      */           }
/*      */           else {
/*  359 */             this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*  360 */             out.write("\n\n\n<div id=\"prompt\" name=\"prompt\">\n<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"95%\" align=\"center\">\n\t<tr>\n\t<td>\n");
/*  361 */             if (("true".equals(request.getParameter("wmiMonitors"))) && (request.getAttribute("actionresult") != null))
/*      */             {
/*      */ 
/*  364 */               out.write("\n\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\"\n\t\t\talign=\"center\">\n\t\t\t<tr>\n\t\t\t\t<td width=\"72%\" height=\"31\" colspan=\"2\" class=\"tableheadingtrans\">Service ");
/*  365 */               out.print(request.getParameter("action"));
/*  366 */               out.write(32);
/*  367 */               out.write("\n\t\t\t\t</td>\n\t\t\t<tr>\n\t\t\t\t");
/*  368 */               if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
/*      */                 return;
/*  370 */               out.write("\n\t</table>\n\t\n");
/*      */             }
/*      */             
/*  373 */             if ((resIds != null) && (resIds.trim() != "") && (resourceIds != null))
/*      */             {
/*      */ 
/*  376 */               out.write("\n\n<table style=\"top-padding: 20px;\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"lrtborder\" width=\"100%\" align=\"center\">\n\t<tr align=\"left\">\n\t\t<td class=\"tableheading\">");
/*  377 */               out.print(title);
/*  378 */               out.write("</td>\n\t</tr>\n</table>\n<table style=\"top-padding: 20px;\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"lrtborder\" width=\"100%\" align=\"center\">\n\t");
/*      */               
/*  380 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.get(NotPresentTag.class);
/*  381 */               _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/*  382 */               _jspx_th_logic_005fnotPresent_005f2.setParent(null);
/*      */               
/*  384 */               _jspx_th_logic_005fnotPresent_005f2.setName("resourceType");
/*  385 */               int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/*  386 */               if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                 for (;;) {
/*  388 */                   out.write("\n\t<tr>\n\t\t<td><b><span class=\"bodytextbold\" style=\"color:red;\"><p>");
/*  389 */                   out.print(alertText);
/*  390 */                   out.write("</p></span></b></td>\n\t</tr>\n\t");
/*  391 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/*  392 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  396 */               if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/*  397 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */               }
/*      */               
/*  400 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f2);
/*  401 */               out.write("\n\t<tr>\n\t\t<td class=\"lightbg\">&nbsp;<b><span class=\"bodytextbold\">");
/*  402 */               out.print(selectedText);
/*  403 */               out.write("</span></b></td>\n\t</tr>\n\t<tr>\n\t\t<td><img src=\"../../images/spacer.gif\" height=\"5\" width=\"5\">\n\t\t</td>\n\t</tr>\n\t<tr align=\"left\">\n\t\t<td>\n\t\t<!-- Display the VMs in list format. -->\n\t\t");
/*      */               
/*  405 */               PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.get(PresentTag.class);
/*  406 */               _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/*  407 */               _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */               
/*  409 */               _jspx_th_logic_005fpresent_005f2.setName("resourceType");
/*  410 */               int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/*  411 */               if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                 for (;;) {
/*  413 */                   out.write("\n\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\"\n\t\t\talign=\"center\">\n\n\t\t\t");
/*      */                   
/*  415 */                   for (int i = 0; resourceIds.hasMoreElements(); i++)
/*      */                   {
/*  417 */                     String resid = (String)resourceIds.nextElement();
/*  418 */                     String displayname = (String)names_ids.get(resid);
/*  419 */                     int len = displayname.length();
/*  420 */                     String bgclass = "whitegrayborder";
/*  421 */                     if (i % 2 != 0)
/*      */                     {
/*  423 */                       bgclass = "yellowgrayborder";
/*      */                     }
/*      */                     else
/*      */                     {
/*  427 */                       bgclass = "whitegrayborder";
/*      */                     }
/*      */                     
/*      */ 
/*  431 */                     out.write("\n\n\t\t\t<tr>\n\t\t\t\t<td class=");
/*  432 */                     out.print(bgclass);
/*  433 */                     out.write(" height=\"30\" align=\"left\">&#09;&#09;&#09;\n\t\t\t\t<li>");
/*  434 */                     out.print(displayname);
/*  435 */                     out.write("</li>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t\t");
/*      */                   }
/*  437 */                   out.write("\n\t\t</table>\n\t\t");
/*  438 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/*  439 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  443 */               if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/*  444 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */               }
/*      */               
/*  447 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f2);
/*  448 */               out.write("\n\t\t\n\t\t<!-- Display the amazon instances in tabular format. -->\n\t\t");
/*      */               
/*  450 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.get(NotPresentTag.class);
/*  451 */               _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/*  452 */               _jspx_th_logic_005fnotPresent_005f3.setParent(null);
/*      */               
/*  454 */               _jspx_th_logic_005fnotPresent_005f3.setName("resourceType");
/*  455 */               int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/*  456 */               if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                 for (;;) {
/*  458 */                   out.write("\n\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\"\n\t\t\talign=\"center\">\n\t\t\t<tr><td width=\"50%\" align=\"left\" class=\"tableheadingbborder\">");
/*  459 */                   out.print(FormatUtil.getString("am.amazon.ec2instanceaction.messageinstanceid.text"));
/*  460 */                   out.write("</td>\n\t\t\t\t\t\t\t\t\t<td width=\"50%\" align=\"left\" class=\"tableheadingbborder\">");
/*  461 */                   out.print(FormatUtil.getString("am.amazon.ec2instanceaction.messageinstancename.text"));
/*  462 */                   out.write("</td>\n\t\t\t</tr>\n\t\t\t");
/*      */                   
/*  464 */                   for (int i = 0; resourceIds.hasMoreElements(); i++)
/*      */                   {
/*  466 */                     String resid = (String)resourceIds.nextElement();
/*  467 */                     String displayname = (String)names_ids.get(resid);
/*  468 */                     ArrayList<String> instanceName = (ArrayList)request.getAttribute("instanceDisplayName");
/*  469 */                     int len = displayname.length();
/*  470 */                     String bgclass = "whitegrayborder";
/*  471 */                     if (i % 2 != 0)
/*      */                     {
/*  473 */                       bgclass = "yellowgrayborder";
/*      */                     }
/*      */                     else
/*      */                     {
/*  477 */                       bgclass = "whitegrayborder";
/*      */                     }
/*      */                     
/*      */ 
/*  481 */                     out.write("\n\n\t\t\t<tr>\n\t\t\t\t<td class=");
/*  482 */                     out.print(bgclass);
/*  483 */                     out.write(" height=\"30\" align=\"left\">&#09;&#09;&#09;\n\t\t\t\t");
/*  484 */                     out.print(displayname);
/*  485 */                     out.write("\n\t\t\t\t</td>\n\t\t\t\t<td class=");
/*  486 */                     out.print(bgclass);
/*  487 */                     out.write(" height=\"50\" align=\"left\" width=\"50%\">&#09;&#09;&#09;\n\t\t\t\t");
/*  488 */                     out.print((String)instanceName.get(i));
/*  489 */                     out.write("\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t\t");
/*      */                   }
/*  491 */                   out.write("\n\t\t</table>\t\n\t\t");
/*  492 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/*  493 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  497 */               if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/*  498 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */               }
/*      */               
/*  501 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f3);
/*  502 */               out.write("\n\t\t<!-- End of displaying the amazon instances in tabular format. -->\n\t</td>\n\t</tr>\n\t\t<tr>\n\t\t\t<td><img src=\"../../images/spacer.gif\" height=\"5\" width=\"5\">\n\t\t\t</td>\n\t\t</tr>\n</table>\n<table width=\"100%\" border=\"0\" class=\"lrtbdarkborder\" cellpadding=\"0\"\n\tcellspacing=\"0\" align=\"center\">\n\t<tr>\n\t\t<td height=\"35\" align=\"center\" class=\"tablebottom\">\n\t\t<input name=\"GoBack\" type=\"button\" class=\"buttons btn_link\"\tvalue='");
/*  503 */               out.print(FormatUtil.getString("am.webclient.amazon.cloudwatch.cancel.text"));
/*  504 */               out.write("' onClick=\"opener.location.reload(true);self.close();\">&nbsp;&nbsp;\n\t\t<input name=\"Submit\" type=\"button\" class=\"buttons btn_highlt\"\tvalue='");
/*  505 */               out.print(confirmText);
/*  506 */               out.write("' onClick=\"javascript:submitForm('");
/*  507 */               out.print(resIds);
/*  508 */               out.write(39);
/*  509 */               out.write(44);
/*  510 */               out.write(39);
/*  511 */               out.print(action);
/*  512 */               out.write("');\">\n\t\t\n\t\t</td>\n\t</tr>\n</table>\n");
/*      */             }
/*  514 */             out.write(10);
/*  515 */             out.write(10);
/*      */             
/*  517 */             if ((systemLog != null) && (action.equalsIgnoreCase("system Log")))
/*      */             {
/*      */ 
/*  520 */               out.write("\n<table style=\"top-padding: 20px;\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"lrtborder\" width=\"100%\" align=\"center\">\n\t<tr>\n\t\t<td><img src=\"../../images/spacer.gif\" height=\"5\" width=\"5\">\n\t\t</td>\n\t</tr>\n\t<tr align=\"left\">\n\t\t<td>\n\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" align=\"center\">\n\t\t\t");
/*      */               
/*  522 */               for (int i = 0; i < systemLogKeys.size(); i++)
/*      */               {
/*  524 */                 String key = (String)systemLogKeys.get(i);
/*  525 */                 String value = (String)systemLog.get(key);
/*  526 */                 String bgclass = "whitegrayborder-conf-mon-gray";
/*  527 */                 if (i % 2 != 0)
/*      */                 {
/*  529 */                   bgclass = "whitegrayborder-conf-mon-gray";
/*      */                 }
/*      */                 else
/*      */                 {
/*  533 */                   bgclass = "whitegrayborder-conf-mon";
/*      */                 }
/*      */                 
/*  536 */                 out.write("\n\t\t\t<tr valign=\"top\">\n\t\t\t\t<td class=");
/*  537 */                 out.print(bgclass);
/*  538 */                 out.write(" width =\"20%\" height=\"30\" align=\"left\">&#09;&#09;&#09;\n\t\t\t\t");
/*  539 */                 out.print(FormatUtil.getString(key));
/*  540 */                 out.write("\n\t\t\t\t</td>\n\t\t\t\t<td class=");
/*  541 */                 out.print(bgclass);
/*  542 */                 out.write(" width =\"80%\" height=\"30\" align=\"left\">&#09;&#09;&#09;\n\t\t\t\t");
/*  543 */                 out.print(value);
/*  544 */                 out.write("\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t\t");
/*      */               }
/*  546 */               out.write("\n\t\t</table>\n\t\t</td>\n\t\t</tr>\n\t\t<tr>\n\t\t<td class=\"conf-mon-btm-mid-tile \"></td>\n\t\t</tr>\n</table>\n");
/*      */             }
/*  548 */             out.write("\n</td>\n</tr>\n</table>\n</div>\n<br>\n\n");
/*      */             
/*  550 */             MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/*  551 */             _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/*  552 */             _jspx_th_logic_005fmessagesPresent_005f0.setParent(null);
/*      */             
/*  554 */             _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/*  555 */             int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/*  556 */             if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */               for (;;) {
/*  558 */                 out.write(10);
/*  559 */                 out.write(10);
/*  560 */                 out.write(9);
/*  561 */                 if (request.getAttribute("ec2InstanceChangeList") != null) {
/*  562 */                   out.write("\n\t<!-- Display amazon action results. -->\n\t\t<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"conf-mon-inner-content-bg\" align=\"center\">\n\t\t\t<tr>\n\t\t\t\t<td width=\"30%\" align=\"left\" class=\"tableheadingbborder\">");
/*  563 */                   out.print(FormatUtil.getString("am.amazon.ec2instanceaction.messageinstanceid.text"));
/*  564 */                   out.write("</td>\n\t\t\t\t<td width=\"30%\" align=\"left\" class=\"tableheadingbborder\">");
/*  565 */                   out.print(FormatUtil.getString("am.amazon.ec2instanceaction.messageinstancename.text"));
/*  566 */                   out.write("</td>\n\t\t\t\t<td width=\"35%\" align=\"left\" class=\"tableheadingbborder\">");
/*  567 */                   out.print(FormatUtil.getString("am.amazon.ec2instanceaction.successmessage.text"));
/*  568 */                   out.write("</td></tr>\n\t\t\t\t");
/*      */                   
/*  570 */                   java.util.List<Properties> list = (java.util.List)request.getAttribute("ec2InstanceChangeList");
/*  571 */                   ArrayList<String> instanceName = (ArrayList)request.getAttribute("displayNameforInstance");
/*  572 */                   int lenghtforinst = 0;
/*  573 */                   for (Properties instance : list)
/*      */                   {
/*  575 */                     out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"30%\" align=\"left\" class=\"whitegrayborder\">");
/*  576 */                     out.print(instance.getProperty("InstanceId"));
/*  577 */                     out.write("</td>\n\t\t\t\t\t<td width=\"30%\" align=\"left\" class=\"whitegrayborder\">");
/*  578 */                     out.print(instance.getProperty("InstanceName"));
/*  579 */                     out.write("</td>\t\t\t\t\n\t\t\t\t    <td width=\"35%\" align=\"left\" class=\"whitegrayborder\">");
/*  580 */                     out.print(instance.getProperty("Status"));
/*  581 */                     out.write("</td>\n\t\t\t\t</tr>");
/*      */                   }
/*  583 */                   out.write("\n\t\n\t\t</table>\n\t\t<!-- End of displaying amazon action results. -->\n\t");
/*      */                 }
/*  585 */                 else if (request.getAttribute("vmsResultMap") != null)
/*      */                 {
/*      */ 
/*  588 */                   out.write("\n\t\t<table style=\"top-padding: 20px;\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"lrtbdarkborder\" width=\"80%\" align=\"center\">\n\t\t<tr>\n\t\t\t<td class=\"tableheadingbborder\">");
/*  589 */                   out.print(FormatUtil.getString("am.webclient.esx.tab.overview.table.vms"));
/*  590 */                   out.write("</td>\n\t\t\t<td class=\"tableheadingbborder\">");
/*  591 */                   out.print(FormatUtil.getString("am.webclient.hostresource.services.result.text"));
/*  592 */                   out.write("</td>\n\t\t\t<td class=\"tableheadingbborder\">");
/*  593 */                   out.print(FormatUtil.getString("webclient.topo.addnoderesult.message"));
/*  594 */                   out.write("</td>\n\t\t</tr>\n\t\t");
/*      */                   
/*  596 */                   HashMap<String, HashMap<String, String>> vmActionResults = (HashMap)request.getAttribute("vmsResultMap");
/*  597 */                   for (String resourceId : vmActionResults.keySet())
/*      */                   {
/*  599 */                     HashMap<String, String> result = (HashMap)vmActionResults.get(resourceId);
/*      */                     
/*  601 */                     out.write("\n\t\t\t<tr>\n\t\t\t\t<td class=\"message\">");
/*  602 */                     out.print((String)result.get("Name"));
/*  603 */                     out.write("</td>\n\t\t\t\t<td class=\"message\" align=\"left\">\n\t\t\t\t  \t");
/*  604 */                     if (Boolean.parseBoolean((String)result.get("Status"))) {
/*  605 */                       out.write("\n\t\t\t\t  \t<img src=\"../../images/widget/icon_clear.gif\" height=\"10\" width=\"10\">\n\t\t\t\t  \t");
/*      */                     } else {
/*  607 */                       out.write("\n\t\t\t\t  \t<img src=\"../../images/widget/icon_critical.gif\" height=\"10\" width=\"10\">\n\t\t\t\t  \t");
/*      */                     }
/*  609 */                     out.write("\n\t\t\t  \t</td>\n\t\t\t  \t<td class=\"message\">");
/*  610 */                     out.print((String)result.get("message"));
/*  611 */                     out.write("</td>\n\t\t  \t</tr>\n\t\t");
/*      */                   }
/*      */                   
/*      */ 
/*  615 */                   out.write(10);
/*  616 */                   out.write(9);
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*  621 */                   out.write("\n\t<!-- Display VM action results. -->\n\t\t<table style=\"top-padding: 20px;\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"lrtbdarkborder\" width=\"80%\" align=\"center\">\n\t\t<tr>\n\t\t\t<td class=\"tableheadingbborder\">");
/*  622 */                   out.print(FormatUtil.getString("am.webclient.esx.tab.overview.table.vms"));
/*  623 */                   out.write("</td>\n\t\t\t<td class=\"tableheadingbborder\">");
/*  624 */                   out.print(FormatUtil.getString("am.webclient.hostresource.services.result.text"));
/*  625 */                   out.write("</td>\n\t\t\t<td class=\"tableheadingbborder\">");
/*  626 */                   out.print(FormatUtil.getString("webclient.topo.addnoderesult.message"));
/*  627 */                   out.write("</td>\n\t\t</tr>\n\t\t\t\t");
/*  628 */                   HashMap vmActionResults = (HashMap)request.getAttribute("vmsResults");
/*      */                   
/*  630 */                   if ((vmActionResults != null) && (vmActionResults.size() > 0)) {
/*  631 */                     Iterator itr = vmActionResults.keySet().iterator();
/*  632 */                     while (itr.hasNext()) {
/*  633 */                       Object vmName = itr.next();
/*      */                       
/*  635 */                       out.write("\n\t\t\t<tr>\n\t\t\t\t<td class=\"message\">");
/*  636 */                       out.print(vmName);
/*  637 */                       out.write("</td>\n\t\t\t\t<td class=\"message\" align=\"left\">");
/*  638 */                       String[] a = (String[])vmActionResults.get(vmName);
/*  639 */                       out.write("\n\t\t\t\t  \t");
/*  640 */                       if (a[0].equals(com.adventnet.appmanager.vserver.VMWareUtil.VM_CHANGEACTION_SUCCESS)) {
/*  641 */                         out.write("\n\t\t\t\t  \t<img src=\"../../images/widget/icon_clear.gif\" height=\"10\" width=\"10\">\n\t\t\t\t  \t");
/*      */                       } else {
/*  643 */                         out.write("\n\t\t\t\t  \t<img src=\"../../images/widget/icon_critical.gif\" height=\"10\" width=\"10\">\n\t\t\t\t  \t");
/*      */                       }
/*  645 */                       out.write("\n\t\t\t  \t</td>\n\t\t\t  \t<td class=\"message\">");
/*  646 */                       out.print(a[1]);
/*  647 */                       out.write("</td>\n\t\t  \t</tr>\n\t\t\t\t  ");
/*      */                     }
/*      */                   }
/*  650 */                   out.write("\n\t\t</table>\n\t\t<br><br>\n\t<!-- End of displaying VM action results. -->\n\t");
/*      */                 }
/*  652 */                 out.write("\n\t<br>\n");
/*  653 */                 int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/*  654 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  658 */             if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/*  659 */               this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */             }
/*      */             else {
/*  662 */               this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*  663 */               out.write(32);
/*  664 */               out.write(10);
/*  665 */               out.write(10);
/*      */               
/*  667 */               if ((resIds == null) || (resIds.trim() == "") || (resourceIds == null))
/*      */               {
/*      */ 
/*  670 */                 out.write("\n<table width=\"95%\" border=\"0\"  cellpadding=\"0\"\n\tcellspacing=\"0\" align=\"center\">\n\t<tr>\n\t\t<td height=\"35\" align=\"center\" >\n\t\t\t<input name=\"GoBack\" type=\"button\" class=\"buttons\"\talign=\"center\" value='");
/*  671 */                 out.print(FormatUtil.getString("am.webclient.amazon.close.text"));
/*  672 */                 out.write("' onClick=\"self.close();opener.location.reload(true);\">&nbsp;&nbsp;\n\t\t</td>\n\t</tr>\n</table>\n");
/*      */               }
/*  674 */               out.write("\n\n<div id=\"processing\" style=\"visibility:hidden\">\n\t<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t\t<tr>\n\t\t\t<td><img src=\"../../images/spacer.gif\" height=\"5\" width=\"20\">\n\t\t\t<td class=\"message\" valign=\"centre\">&nbsp;<img src=\"../../images/loading.gif\" height=\"50\" width=\"50\"></td>\n\t\t\t<td class=\"message\" valign=\"centre\">&nbsp;");
/*  675 */               out.print(request.getAttribute("actionStateToDisplay"));
/*  676 */               out.write("</td>\n\t\t</tr>\n\t</table>\n</div>\n</form>\n\n");
/*      */             }
/*      */           }
/*      */         }
/*      */       } catch (Exception e) {
/*  681 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     catch (Throwable t) {
/*  685 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  686 */         out = _jspx_out;
/*  687 */         if ((out != null) && (out.getBufferSize() != 0))
/*  688 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/*  689 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/*  692 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  698 */     PageContext pageContext = _jspx_page_context;
/*  699 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  701 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/*  702 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  703 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/*  705 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/*  707 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/*  708 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  709 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/*  710 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  711 */       return true;
/*      */     }
/*  713 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  714 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  719 */     PageContext pageContext = _jspx_page_context;
/*  720 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  722 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  723 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/*  724 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/*  726 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/*  727 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/*  728 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/*  730 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/*  731 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/*  732 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  736 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/*  737 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*  738 */       return true;
/*      */     }
/*  740 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*  741 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  746 */     PageContext pageContext = _jspx_page_context;
/*  747 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  749 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  750 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  751 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/*  753 */     _jspx_th_c_005fwhen_005f0.setTest("${param.wmiMonitors=='true'}");
/*  754 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  755 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/*  757 */         out.write("\n\t\t\t//alert(\"/manageEC2Instances.do?method=wmiServiceAction&action=\"+action+\"&resIds=\"+ids+\"&hostname=");
/*  758 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*  759 */           return true;
/*  760 */         out.write("&resourceid=");
/*  761 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*  762 */           return true;
/*  763 */         out.write("&typeid=");
/*  764 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*  765 */           return true;
/*  766 */         out.write("&tablename=");
/*  767 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*  768 */           return true;
/*  769 */         out.write("&montype=");
/*  770 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*  771 */           return true;
/*  772 */         out.write("\");\n\t\t\tdocument.AMActionForm.action=\"/manageEC2Instances.do?method=wmiServiceAction&action=\"+action+\"&resIds=\"+ids+\"&hostname=");
/*  773 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*  774 */           return true;
/*  775 */         out.write("&resourceid=");
/*  776 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*  777 */           return true;
/*  778 */         out.write("&typeid=");
/*  779 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*  780 */           return true;
/*  781 */         out.write("&tablename=");
/*  782 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*  783 */           return true;
/*  784 */         out.write("&montype=");
/*  785 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*  786 */           return true;
/*  787 */         out.write("\";//No I18N\n\t\t\t");
/*  788 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  789 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  793 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  794 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  795 */       return true;
/*      */     }
/*  797 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  798 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  803 */     PageContext pageContext = _jspx_page_context;
/*  804 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  806 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  807 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/*  808 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/*  810 */     _jspx_th_c_005fout_005f1.setValue("${param.hostname}");
/*  811 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/*  812 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/*  813 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/*  814 */       return true;
/*      */     }
/*  816 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/*  817 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  822 */     PageContext pageContext = _jspx_page_context;
/*  823 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  825 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  826 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/*  827 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/*  829 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/*  830 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/*  831 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/*  832 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/*  833 */       return true;
/*      */     }
/*  835 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/*  836 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  841 */     PageContext pageContext = _jspx_page_context;
/*  842 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  844 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  845 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/*  846 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/*  848 */     _jspx_th_c_005fout_005f3.setValue("${param.typeid}");
/*  849 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/*  850 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/*  851 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/*  852 */       return true;
/*      */     }
/*  854 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/*  855 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  860 */     PageContext pageContext = _jspx_page_context;
/*  861 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  863 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  864 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/*  865 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/*  867 */     _jspx_th_c_005fout_005f4.setValue("${param.tablename}");
/*  868 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/*  869 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/*  870 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/*  871 */       return true;
/*      */     }
/*  873 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/*  874 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  879 */     PageContext pageContext = _jspx_page_context;
/*  880 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  882 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  883 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/*  884 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/*  886 */     _jspx_th_c_005fout_005f5.setValue("${param.montype}");
/*  887 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/*  888 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/*  889 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/*  890 */       return true;
/*      */     }
/*  892 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/*  893 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  898 */     PageContext pageContext = _jspx_page_context;
/*  899 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  901 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  902 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/*  903 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/*  905 */     _jspx_th_c_005fout_005f6.setValue("${param.hostname}");
/*  906 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/*  907 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/*  908 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/*  909 */       return true;
/*      */     }
/*  911 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/*  912 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  917 */     PageContext pageContext = _jspx_page_context;
/*  918 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  920 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  921 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/*  922 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/*  924 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/*  925 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/*  926 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/*  927 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/*  928 */       return true;
/*      */     }
/*  930 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/*  931 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  936 */     PageContext pageContext = _jspx_page_context;
/*  937 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  939 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  940 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/*  941 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/*  943 */     _jspx_th_c_005fout_005f8.setValue("${param.typeid}");
/*  944 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/*  945 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/*  946 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/*  947 */       return true;
/*      */     }
/*  949 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/*  950 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  955 */     PageContext pageContext = _jspx_page_context;
/*  956 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  958 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  959 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/*  960 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/*  962 */     _jspx_th_c_005fout_005f9.setValue("${param.TableId}");
/*  963 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/*  964 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/*  965 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/*  966 */       return true;
/*      */     }
/*  968 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/*  969 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  974 */     PageContext pageContext = _jspx_page_context;
/*  975 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  977 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  978 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/*  979 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/*  981 */     _jspx_th_c_005fout_005f10.setValue("${param.montype}");
/*  982 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/*  983 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/*  984 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/*  985 */       return true;
/*      */     }
/*  987 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/*  988 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEqual_005f0(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  993 */     PageContext pageContext = _jspx_page_context;
/*  994 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  996 */     NotEqualTag _jspx_th_logic_005fnotEqual_005f0 = (NotEqualTag)this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname.get(NotEqualTag.class);
/*  997 */     _jspx_th_logic_005fnotEqual_005f0.setPageContext(_jspx_page_context);
/*  998 */     _jspx_th_logic_005fnotEqual_005f0.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 1000 */     _jspx_th_logic_005fnotEqual_005f0.setName("resourceType");
/*      */     
/* 1002 */     _jspx_th_logic_005fnotEqual_005f0.setValue("Docker Container");
/* 1003 */     int _jspx_eval_logic_005fnotEqual_005f0 = _jspx_th_logic_005fnotEqual_005f0.doStartTag();
/* 1004 */     if (_jspx_eval_logic_005fnotEqual_005f0 != 0) {
/*      */       for (;;) {
/* 1006 */         out.write("\n\t\t\t\t\tdocument.AMActionForm.action=\"/manageVMInstances.do?method=manageInstances&action=\"+action+\"&resIds=\"+ids;//No I18N\n\t\t\t\t");
/* 1007 */         int evalDoAfterBody = _jspx_th_logic_005fnotEqual_005f0.doAfterBody();
/* 1008 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1012 */     if (_jspx_th_logic_005fnotEqual_005f0.doEndTag() == 5) {
/* 1013 */       this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fnotEqual_005f0);
/* 1014 */       return true;
/*      */     }
/* 1016 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fnotEqual_005f0);
/* 1017 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f1(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1022 */     PageContext pageContext = _jspx_page_context;
/* 1023 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1025 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.get(NotPresentTag.class);
/* 1026 */     _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 1027 */     _jspx_th_logic_005fnotPresent_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 1029 */     _jspx_th_logic_005fnotPresent_005f1.setName("resourceType");
/* 1030 */     int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 1031 */     if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */       for (;;) {
/* 1033 */         out.write(" \n\t\t\t\tdocument.AMActionForm.action=\"/manageEC2Instances.do?method=manageInstances&action=\"+action+\"&resIds=\"+ids;//No I18N\n\t\t\t\t");
/* 1034 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 1035 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1039 */     if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 1040 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 1041 */       return true;
/*      */     }
/* 1043 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 1044 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1049 */     PageContext pageContext = _jspx_page_context;
/* 1050 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1052 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 1053 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 1054 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/* 1056 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*      */     
/* 1058 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 1059 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 1060 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 1061 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 1062 */       return true;
/*      */     }
/* 1064 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 1065 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1070 */     PageContext pageContext = _jspx_page_context;
/* 1071 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1073 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1074 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 1075 */     _jspx_th_c_005fforEach_005f0.setParent(null);
/*      */     
/* 1077 */     _jspx_th_c_005fforEach_005f0.setVar("service");
/*      */     
/* 1079 */     _jspx_th_c_005fforEach_005f0.setItems("${actionresult}");
/* 1080 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 1082 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 1083 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 1085 */           out.write("\n\t\t\t\t<td class=\"yellowgrayborder\" height=\"30\" align=\"left\">\n\t\t\t\t");
/* 1086 */           boolean bool; if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1087 */             return true;
/* 1088 */           out.write("\n\t\t\t\t</td>\n\t\t\t\t<td class=\"yellowgrayborder\" height=\"30\" align=\"left\">\n\t\t\t\t");
/* 1089 */           if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1090 */             return true;
/* 1091 */           out.write(" \n\t\t\t\t</td>\n\t\t\t\t</tr>\t\t\n\t\t\t\t");
/* 1092 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 1093 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1097 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 1098 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1101 */         int tmp226_225 = 0; int[] tmp226_223 = _jspx_push_body_count_c_005fforEach_005f0; int tmp228_227 = tmp226_223[tmp226_225];tmp226_223[tmp226_225] = (tmp228_227 - 1); if (tmp228_227 <= 0) break;
/* 1102 */         out = _jspx_page_context.popBody(); }
/* 1103 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 1105 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 1106 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 1108 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1113 */     PageContext pageContext = _jspx_page_context;
/* 1114 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1116 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1117 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 1118 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1120 */     _jspx_th_c_005fout_005f11.setValue("${service.key}");
/* 1121 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 1122 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 1123 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 1124 */       return true;
/*      */     }
/* 1126 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 1127 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1132 */     PageContext pageContext = _jspx_page_context;
/* 1133 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1135 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1136 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 1137 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1139 */     _jspx_th_c_005fout_005f12.setValue("${service.value}");
/* 1140 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 1141 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 1142 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 1143 */       return true;
/*      */     }
/* 1145 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 1146 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\amazon\ManageEC2Instances_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */