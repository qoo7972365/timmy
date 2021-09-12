/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*     */ import com.adventnet.appmanager.struts.beans.DependantMOUtil;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
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
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class gmap_005fseverity_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  31 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  42 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  46 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  48 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  52 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  59 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  62 */     JspWriter out = null;
/*  63 */     Object page = this;
/*  64 */     JspWriter _jspx_out = null;
/*  65 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  69 */       response.setContentType("text/html;charset=UTF-8");
/*  70 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  72 */       _jspx_page_context = pageContext;
/*  73 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  74 */       ServletConfig config = pageContext.getServletConfig();
/*  75 */       session = pageContext.getSession();
/*  76 */       out = pageContext.getOut();
/*  77 */       _jspx_out = out;
/*     */       
/*  79 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*  80 */       ManagedApplication mo = null;
/*  81 */       mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 1);
/*  82 */       if (mo == null) {
/*  83 */         mo = new ManagedApplication();
/*  84 */         _jspx_page_context.setAttribute("mo", mo, 1);
/*     */       }
/*  86 */       out.write(10);
/*     */       
/*     */ 
/*     */ 
/*  90 */       String gMapQuery = "";
/*     */       
/*  92 */       if (EnterpriseUtil.isIt360MSPEdition())
/*     */       {
/*  94 */         int custId = -1;
/*  95 */         Properties custProp = EnterpriseUtil.getCustProp(request);
/*  96 */         System.out.println("custProp###  : " + custProp);
/*  97 */         Enumeration e = custProp.propertyNames();
/*  98 */         while (e.hasMoreElements())
/*     */         {
/* 100 */           String key = (String)e.nextElement();
/* 101 */           custId = Integer.parseInt(custProp.getProperty(key));
/*     */         }
/* 103 */         gMapQuery = "AND AM_GMapCountryResourceRel.ID IN (select CHILDID from AM_ParentChildMapper where PARENTID=" + custId + ")";
/*     */       }
/*     */       
/* 106 */       System.out.println("gMapQuery###  : " + gMapQuery);
/*     */       
/* 108 */       String mgQuery = "";
/* 109 */       boolean isUserResourceEnabled = false;
/* 110 */       String loginUserid = null;
/*     */       
/* 112 */       if (ClientDBUtil.isPrivilegedUser(request)) {
/* 113 */         if (Constants.isUserResourceEnabled()) {
/* 114 */           isUserResourceEnabled = true;
/* 115 */           loginUserid = Constants.getLoginUserid(request);
/*     */         } else {
/* 117 */           Vector resids = ClientDBUtil.getAssociatedMonitorGroupsForOwner(request.getRemoteUser());
/* 118 */           mgQuery = " and " + DependantMOUtil.getCondition("AM_GMapCountryResourceRel.ID", resids) + " ";
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 126 */       String query = null;
/*     */       
/* 128 */       if (isUserResourceEnabled) {
/* 129 */         query = "select RESOURCENAME,NAME,XCOORD,YCOORD, COALESCE(Alert.MMESSAGE,'Health is Clear'),COALESCE(Alert.SEVERITY,'5') FROM AM_GMapCountryResourceRel JOIN AM_GMapCountryCoord ON AM_GMapCountryCoord.ID = AM_GMapCountryResourceRel.LOCATIONID " + gMapQuery + " JOIN AM_ManagedObject ON AM_ManagedObject.RESOURCEID = AM_GMapCountryResourceRel.ID join AM_USERRESOURCESTABLE on AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " LEFT OUTER JOIN Alert ON Alert.SOURCE=AM_GMapCountryResourceRel.ID AND Alert.CATEGORY='18' ORDER BY COALESCE(Alert.SEVERITY,'5') DESC";
/*     */       } else {
/* 131 */         query = "select RESOURCENAME,NAME,XCOORD,YCOORD, COALESCE(Alert.MMESSAGE,'Health is Clear'),COALESCE(Alert.SEVERITY,'5') FROM AM_GMapCountryResourceRel JOIN AM_GMapCountryCoord ON AM_GMapCountryCoord.ID = AM_GMapCountryResourceRel.LOCATIONID " + gMapQuery + mgQuery + " JOIN AM_ManagedObject ON AM_ManagedObject.RESOURCEID = AM_GMapCountryResourceRel.ID LEFT OUTER JOIN Alert ON Alert.SOURCE=AM_GMapCountryResourceRel.ID AND Alert.CATEGORY='18' ORDER BY COALESCE(Alert.SEVERITY,'5') DESC";
/*     */       }
/*     */       
/* 134 */       if (!Constants.subGroupsEnabled.equals("true"))
/*     */       {
/* 136 */         if (isUserResourceEnabled) {
/* 137 */           query = "SELECT RESOURCENAME,NAME,XCOORD,YCOORD,COALESCE(Alert.MMESSAGE, 'Health is Clear'),COALESCE(Alert.SEVERITY, '5') FROM  AM_GMapCountryResourceRel JOIN AM_GMapCountryCoord  on AM_GMapCountryCoord.ID  = AM_GMapCountryResourceRel.LOCATIONID " + gMapQuery + " JOIN AM_ManagedObject on AM_ManagedObject.RESOURCEID  = AM_GMapCountryResourceRel.ID join AM_USERRESOURCESTABLE on AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " LEFT OUTER JOIN Alert ON Alert.SOURCE  = AM_GMapCountryResourceRel.ID AND Alert.CATEGORY  = '18'  LEFT OUTER JOIN AM_HOLISTICAPPLICATION ON AM_HOLISTICAPPLICATION.HAID  = AM_ManagedObject.RESOURCEID WHERE AM_HOLISTICAPPLICATION.TYPE  = 0  ORDER BY COALESCE(Alert.SEVERITY, '5') DESC";
/*     */         } else {
/* 139 */           query = "SELECT RESOURCENAME,NAME,XCOORD,YCOORD,COALESCE(Alert.MMESSAGE, 'Health is Clear'),COALESCE(Alert.SEVERITY, '5') FROM  AM_GMapCountryResourceRel JOIN AM_GMapCountryCoord  on AM_GMapCountryCoord.ID  = AM_GMapCountryResourceRel.LOCATIONID " + gMapQuery + mgQuery + " JOIN AM_ManagedObject on AM_ManagedObject.RESOURCEID  = AM_GMapCountryResourceRel.ID LEFT OUTER JOIN Alert ON Alert.SOURCE  = AM_GMapCountryResourceRel.ID AND Alert.CATEGORY  = '18'  LEFT OUTER JOIN AM_HOLISTICAPPLICATION ON AM_HOLISTICAPPLICATION.HAID  = AM_ManagedObject.RESOURCEID WHERE AM_HOLISTICAPPLICATION.TYPE  = 0  ORDER BY COALESCE(Alert.SEVERITY, '5') DESC";
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 145 */       System.out.println("query###  : " + query);
/* 146 */       List list = mo.getRows(query);
/* 147 */       request.setAttribute("markers", list);
/*     */       
/*     */ 
/*     */ 
/* 151 */       out.write("\n<markers>\n\t");
/*     */       
/* 153 */       IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 154 */       _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 155 */       _jspx_th_logic_005fiterate_005f0.setParent(null);
/*     */       
/* 157 */       _jspx_th_logic_005fiterate_005f0.setName("markers");
/*     */       
/* 159 */       _jspx_th_logic_005fiterate_005f0.setId("row");
/*     */       
/* 161 */       _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*     */       
/* 163 */       _jspx_th_logic_005fiterate_005f0.setType("java.util.List");
/* 164 */       int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 165 */       if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 166 */         List row = null;
/* 167 */         Integer j = null;
/* 168 */         if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 169 */           out = _jspx_page_context.pushBody();
/* 170 */           _jspx_th_logic_005fiterate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 171 */           _jspx_th_logic_005fiterate_005f0.doInitBody();
/*     */         }
/* 173 */         row = (List)_jspx_page_context.findAttribute("row");
/* 174 */         j = (Integer)_jspx_page_context.findAttribute("j");
/*     */         for (;;) {
/* 176 */           out.write(" \t\t\n\t");
/*     */           
/* 178 */           String mesg = (String)row.get(4);
/* 179 */           mesg = mesg.replaceAll("<", "&lt;");
/* 180 */           mesg = mesg.replaceAll(">", "&gt;");
/*     */           
/* 182 */           String msg = (String)row.get(0);
/* 183 */           msg = msg.replaceAll("<", "&lt;");
/* 184 */           msg = msg.replaceAll(">", "&gt;");
/* 185 */           msg = msg.replaceAll("&", "&amp;");
/*     */           
/*     */ 
/* 188 */           out.write("\n\t\t<marker lat=\"");
/* 189 */           out.print(row.get(2));
/* 190 */           out.write("\" lng=\"");
/* 191 */           out.print(row.get(3));
/* 192 */           out.write("\" monitorname=\"");
/* 193 */           out.print(msg);
/* 194 */           out.write("\" icon=\"/images/marker");
/* 195 */           out.print(row.get(5));
/* 196 */           out.write(".png\" rca=\"");
/* 197 */           out.print(mesg);
/* 198 */           out.write("\" place=\"");
/* 199 */           out.print(com.adventnet.appmanager.util.FormatUtil.getString("Location"));
/* 200 */           out.write(32);
/* 201 */           out.write(58);
/* 202 */           out.write(32);
/* 203 */           out.print(row.get(1));
/* 204 */           out.write("\"/>\t\n \t");
/* 205 */           int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 206 */           row = (List)_jspx_page_context.findAttribute("row");
/* 207 */           j = (Integer)_jspx_page_context.findAttribute("j");
/* 208 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/* 211 */         if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 212 */           out = _jspx_page_context.popBody();
/*     */         }
/*     */       }
/* 215 */       if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 216 */         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*     */       }
/*     */       else {
/* 219 */         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 220 */         out.write("\n \t\n</markers>\n");
/*     */       }
/* 222 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 223 */         out = _jspx_out;
/* 224 */         if ((out != null) && (out.getBufferSize() != 0))
/* 225 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 226 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 229 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\gmap_005fseverity_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */