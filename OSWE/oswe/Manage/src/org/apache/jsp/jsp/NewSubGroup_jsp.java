/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.struts.beans.GroupComponent;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.action.DynaActionForm;
/*     */ import org.apache.struts.taglib.html.FormTag;
/*     */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*     */ import org.apache.struts.taglib.html.SelectTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class NewSubGroup_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  28 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  44 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  48 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  55 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  59 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  60 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*  61 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.release();
/*  62 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.release();
/*  63 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*  64 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  71 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  74 */     JspWriter out = null;
/*  75 */     Object page = this;
/*  76 */     JspWriter _jspx_out = null;
/*  77 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  81 */       response.setContentType("text/html");
/*  82 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  84 */       _jspx_page_context = pageContext;
/*  85 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  86 */       ServletConfig config = pageContext.getServletConfig();
/*  87 */       session = pageContext.getSession();
/*  88 */       out = pageContext.getOut();
/*  89 */       _jspx_out = out;
/*     */       
/*  91 */       out.write("<!--$Id$-->\n\n\n\n");
/*  92 */       ManagedApplication mo1 = null;
/*  93 */       mo1 = (ManagedApplication)_jspx_page_context.getAttribute("mo1", 1);
/*  94 */       if (mo1 == null) {
/*  95 */         mo1 = new ManagedApplication();
/*  96 */         _jspx_page_context.setAttribute("mo1", mo1, 1);
/*     */       }
/*  98 */       out.write("\n\n\n\n\n\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  99 */       out.write("\n<link href=\"/images/");
/* 100 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/* 102 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<script>\nfunction validateAndSubmit(frm)\n{\n\n              if((frm.name.value).trim() =='')\n               {\n                 alert(\"");
/* 103 */       out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.subgroupname.alert.text"));
/* 104 */       out.write("\");\n                 frm.name.focus();\n                 return;\n               }\n\n              frmSelectAllIncludingEmpty(frm.selectedowners_list);\n              //alert(document.applicationform.method.value)\n              frm.submit();\n }\n function addLocation()\n  {\n\n  \tvar url = \"/jsp/GMap_showlocations.jsp\";\n  \tvar win=window.open(url,'','resizable=yes,scrollbars=yes,width=600,height=600');\n    \twin.focus();\n  \treturn false;\n  }\n\n</script>\n\n");
/*     */       
/* 106 */       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/* 107 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 108 */       _jspx_th_html_005fform_005f0.setParent(null);
/*     */       
/* 110 */       _jspx_th_html_005fform_005f0.setAction("/manageApplications.do?method=AddSubGroup");
/* 111 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 112 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*     */         for (;;) {
/* 114 */           out.write("\n\t\t<input type=\"hidden\" name=\"haid\" value=\"");
/* 115 */           out.print(request.getParameter("haid"));
/* 116 */           out.write("\">\n\t\t");
/*     */           
/* 118 */           DynaActionForm form = (DynaActionForm)request.getAttribute("applicationform");
/*     */           
/* 120 */           form.set("name", (String)((DynaActionForm)pageContext.findAttribute("applicationform")).get("name"));
/* 121 */           form.set("owner", request.getRemoteUser());
/*     */           
/* 123 */           ArrayList rows = null;
/*     */           
/* 125 */           if (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())
/*     */           {
/* 127 */             rows = mo1.getPropertiesList("select AM_UserPasswordTable.USERNAME,USERID from AM_UserPasswordTable,AM_UserGroupTable where AM_UserPasswordTable.USERNAME = AM_UserGroupTable.USERNAME and  AM_UserPasswordTable.USERNAME not in ('reportadmin','systemadmin_enterprise','" + OEMUtil.getOEMString("am.hiddenuser.username") + "','" + OEMUtil.getOEMString("am.hiddenuser1.username") + "') group by AM_UserPasswordTable.USERNAME,USERID");
/*     */           }
/*     */           else
/*     */           {
/* 131 */             rows = mo1.getPropertiesList("select AM_UserPasswordTable.USERNAME,USERID from AM_UserPasswordTable,AM_UserGroupTable where AM_UserPasswordTable.USERNAME = AM_UserGroupTable.USERNAME and AM_UserPasswordTable.USERNAME not in ('reportadmin','" + OEMUtil.getOEMString("am.hiddenuser.username") + "','" + OEMUtil.getOEMString("am.hiddenuser1.username") + "') group by AM_UserPasswordTable.USERNAME,USERID");
/*     */           }
/* 133 */           form.set("allowners", rows);
/*     */           
/*     */ 
/* 136 */           out.write("\n\t<input type=\"hidden\" name=\"method\" value=\"AddSubGroup\">\n\t<table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\t  <tr>\n\t    <td width=\"72%\" class=\"tableheadingbborder\" height=\"26\" colspan='2'>");
/* 137 */           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.new.subgroup.text"));
/* 138 */           out.write("</td>\n\t  </tr>\n\t  <tr>\n\t    <td width=\"22%\" height=\"35\" class=\"bodytext label-align\">");
/* 139 */           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.subgroup.name.text"));
/* 140 */           out.write("<span class=\"mandatory\">*</span></td>\n\t    <td width=\"78%\" height=\"35\" class=\"bodytext\"> <input type=\"text\" name=\"name\" class=\"formtext\" maxlength=\"40\" size=\"40\" value=\"\"/>\n\t    </td>\n\t  </tr>\n\t  \n\t  <tr>\n\t    <td width=\"22%\" height=\"35\" class=\"bodytext label-align\" valign=\"top\">");
/* 141 */           out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.description"));
/* 142 */           out.write(" </td>\n\t    <td width=\"78%\" height=\"35\"> <textarea name=\"description\"  rows=\"5\" cols=\"80\" class=\"formtextarea\"  ></textarea></td>\n\t  </tr>\n\t\t");
/*     */           
/* 144 */           if ((!com.adventnet.appmanager.util.DBUtil.isDelegatedAdmin(request.getRemoteUser())) && ((!com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.isSsoEnabled()) || (!com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer)))
/*     */           {
/* 146 */             out.write("\n\t   <tr>\n\t  \t      <td colspan=\"2\" class=\"bodytext\">\n\t  \t      <table width=\"70%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t  \t          <tr>\n\t  \t            <td><div id=\"advancedDiv\" style=\"display:block\">\n\t  \t                <table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t  \t                  <tr>\n\t  \t                    <td width=\"31%\" class=\"bodytext label-align\">");
/* 147 */             out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.owner"));
/* 148 */             out.write("</td>\n\t  \t                    <td width=\"69%\"><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t  \t                        <tr>\n\t  \t                          <td align=\"center\" class=\"bodytextbold\">");
/* 149 */             out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.availableusers"));
/* 150 */             out.write("</td>\n\t  \t                          <td></td>\n\t  \t                          <td align=\"center\" class=\"bodytextbold\">");
/* 151 */             out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.owners"));
/* 152 */             out.write("</td>\n\t  \t                        </tr>\n\t  \t                        <tr>\n\t  \t                          <td align=\"center\" width=\"46%\"> ");
/* 153 */             if (_jspx_meth_html_005fselect_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */               return;
/* 155 */             out.write("\n\t  \t                          </td>\n\t  \t                          <td width=\"8%\" align=\"center\" class=\"bodytext\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">\n\t  \t                              <tr>\n\t  \t                                <td><input name=\"AddButton2\" type=\"button\" class=\"buttons btn_small\" onClick=\"fnAddToRightCombo(this.form.allowners_list,this.form.selectedowners_list);fnRemoveFromRightCombo(this.form.allowners_list);\" value=\"&nbsp;&gt;&nbsp;\"></td>\n\t  \t                              </tr>\n\t  \t                              <tr>\n\t  \t                                <td><img src=\"../images/spacer.gif\" height=\"6\" width=\"5\"></td>\n\t  \t                              </tr>\n\n\t  \t                              <tr>\n\t  \t                                <td><input name=\"RemoveButton2\" type=\"button\" class=\"buttons\" onClick=\"fnAddToRightCombo(this.form.selectedowners_list,this.form.allowners_list);fnRemoveFromRightCombo(this.form.selectedowners_list);\" value=\"&nbsp;&lt;&nbsp;\"></td>\n\t  \t                              </tr>\n");
/* 156 */             out.write("\t  \t                            </table></td>\n\t  \t                          <td align=\"center\" width=\"46%\"> ");
/* 157 */             if (_jspx_meth_html_005fselect_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */               return;
/* 159 */             out.write(" </td>\n\t  \t                        </tr>\n\t  \t                      </table></td>\n\t  \t                  </tr>\n\t  \t                   </table>\n\t  \t              </div></td>\n\t  \t          </tr>\n\t  \t        </table></td>\n  </tr>\n  \t\t");
/*     */           }
/* 161 */           out.write("\n <tr>\n                    <td width=\"22%\" height=\"35\" class=\"bodytext label-align\">");
/* 162 */           if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 163 */             out.print(FormatUtil.getString("am.webclient.mg.type"));
/*     */           }
/* 165 */           out.write("</td>\n                    <td width=\"78%\" height=\"35\">\n\t\t\t\t\n\t\t");
/*     */           
/* 167 */           String grouptype = (String)request.getAttribute("grouptype");
/* 168 */           String[] childTypes = GroupComponent.getSupportedChildGroups(grouptype);
/* 169 */           if (childTypes == null)
/*     */           {
/* 171 */             request.setAttribute("nosubgroups", Boolean.valueOf(true));
/* 172 */             childTypes = GroupComponent.getSupportedChildGroups("1");
/*     */           }
/* 174 */           ArrayList availTypes = new ArrayList();
/* 175 */           for (int i = 0; (childTypes != null) && (i < childTypes.length); i++)
/*     */           {
/* 177 */             String eachType = childTypes[i];
/*     */             
/* 179 */             if ((!"2".equals(eachType)) && (!"3".equals(eachType)) && (!"4".equals(eachType)) && (!"1011".equals(eachType)))
/*     */             {
/*     */ 
/* 182 */               String childlabel = GroupComponent.getSubGroupLabel(eachType);
/* 183 */               Properties dataProps = new Properties();
/* 184 */               dataProps.setProperty("label", childlabel);
/* 185 */               dataProps.setProperty("value", eachType);
/* 186 */               availTypes.add(dataProps);
/*     */             } }
/* 188 */           String childGroupType = GroupComponent.getDefaultClusterType(grouptype);
/* 189 */           String childGroupName = GroupComponent.getSubGroupLabel(childGroupType);
/* 190 */           request.setAttribute("subGroupName", childGroupName);
/* 191 */           form.set("grouptype", childGroupType);
/* 192 */           request.setAttribute("childTypes", availTypes);
/* 193 */           form.set("availableMGTypes", availTypes);
/*     */           
/*     */ 
/* 196 */           out.write("\n\t\t\t");
/* 197 */           if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 198 */             out.write("\n\t\t\t");
/* 199 */             if (_jspx_meth_html_005fselect_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */               return;
/* 201 */             out.write("\n\t\t\t");
/*     */           } else {
/* 203 */             out.write("\n\t\t\t<input type=\"hidden\" name=\"grouptype\" value=\"1004\">\n\t\t\t");
/*     */           }
/* 205 */           out.write("\n\t\t    </td>\n</tr>\n\n\t  <tr>\n \t\t    <td width=\"22%\" height=\"35\" class=\"bodytext label-align\">");
/* 206 */           out.print(FormatUtil.getString("am.webclient.gmap.createmonitorgroup.text"));
/* 207 */           out.write("</td>\n \t\t    <td width=\"78%\" height=\"35\">\n \t\t    ");
/* 208 */           ArrayList gmapcountries = (ArrayList)com.adventnet.appmanager.util.DBUtil.getGMapCountries();
/* 209 */           out.write("\n\n \t\t    <SELECT NAME=\"locationid\">\n \t\t    \t<OPTION VALUE=\"\" SELECTED >");
/* 210 */           out.print(FormatUtil.getString("am.webclient.gmap.createmonitorgroup.noneselected.text"));
/* 211 */           out.write("\n \t\t\t    ");
/* 212 */           if (gmapcountries.size() != 0)
/*     */           {
/* 214 */             for (int k = 0; k < gmapcountries.size(); k++)
/*     */             {
/* 216 */               Properties Countries = (Properties)gmapcountries.get(k);
/*     */               
/* 218 */               out.write("\n \t\t\t\t\t    <OPTION VALUE=\"");
/* 219 */               out.print(Countries.getProperty("value"));
/* 220 */               out.write(34);
/* 221 */               out.write(62);
/* 222 */               out.print(Countries.getProperty("label"));
/* 223 */               out.write("\n \t\t\t    \t");
/*     */             }
/*     */           }
/* 226 */           out.write("\n\n \t\t\t \t</SELECT>\n \t\t     &nbsp;&nbsp;&nbsp;&nbsp;<smaller><a href=\"#\" onclick=\"javascript:return addLocation();\" class=\"staticlinks\">");
/* 227 */           out.print(FormatUtil.getString("am.webclient.gmap.createmonitorgroup.link"));
/* 228 */           out.write("</a></smaller>\n \t\t    </td>\n \t\t  </tr>\n\t  <tr>\n\t    <td width=\"20%\" align=\"center\" class=\"tablebottom\">&nbsp;</td>\n\t    <td width=\"80%\" height=\"26\" class=\"tablebottom\"> &nbsp;\n\t      <input name=\"Submit2\" type=\"button\" class=\"buttons\" onClick=\"javascript:validateAndSubmit(this.form);\" value=\"");
/* 229 */           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.createsubgroup.text"));
/* 230 */           out.write("\" title=\"");
/* 231 */           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/* 232 */           out.write("\">\n\t      &nbsp;<input type=\"button\" value=\"");
/* 233 */           out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 234 */           out.write("\" class='buttons btn_link' onClick=\"toggleDiv('subgroup');\" />\n\n\t    </td>\n\t  </tr>\n\t</table>\n ");
/* 235 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 236 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 240 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 241 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/*     */       }
/*     */       else {
/* 244 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 245 */         out.write(10);
/*     */       }
/* 247 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 248 */         out = _jspx_out;
/* 249 */         if ((out != null) && (out.getBufferSize() != 0))
/* 250 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 251 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 254 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 260 */     PageContext pageContext = _jspx_page_context;
/* 261 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 263 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 264 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 265 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 267 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 269 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 270 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 271 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 272 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 273 */       return true;
/*     */     }
/* 275 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 276 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 281 */     PageContext pageContext = _jspx_page_context;
/* 282 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 284 */     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 285 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 286 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 288 */     _jspx_th_html_005fselect_005f0.setProperty("allowners_list");
/*     */     
/* 290 */     _jspx_th_html_005fselect_005f0.setStyle("width:100%");
/*     */     
/* 292 */     _jspx_th_html_005fselect_005f0.setMultiple("true");
/*     */     
/* 294 */     _jspx_th_html_005fselect_005f0.setSize("5");
/* 295 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 296 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 297 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 298 */         out = _jspx_page_context.pushBody();
/* 299 */         _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 300 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 303 */         out.write("\n\t  \t                            ");
/* 304 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 305 */           return true;
/* 306 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 307 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 310 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 311 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 314 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 315 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f0);
/* 316 */       return true;
/*     */     }
/* 318 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f0);
/* 319 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 324 */     PageContext pageContext = _jspx_page_context;
/* 325 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 327 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.get(OptionsCollectionTag.class);
/* 328 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 329 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*     */     
/* 331 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("allowners");
/*     */     
/* 333 */     _jspx_th_html_005foptionsCollection_005f0.setLabel("USERNAME");
/*     */     
/* 335 */     _jspx_th_html_005foptionsCollection_005f0.setValue("USERID");
/* 336 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 337 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 338 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 339 */       return true;
/*     */     }
/* 341 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 342 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 347 */     PageContext pageContext = _jspx_page_context;
/* 348 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 350 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 351 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 352 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 354 */     _jspx_th_html_005fselect_005f1.setProperty("selectedowners_list");
/*     */     
/* 356 */     _jspx_th_html_005fselect_005f1.setStyle("width:100%");
/*     */     
/* 358 */     _jspx_th_html_005fselect_005f1.setMultiple("true");
/*     */     
/* 360 */     _jspx_th_html_005fselect_005f1.setSize("5");
/* 361 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 362 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 363 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 364 */         out = _jspx_page_context.pushBody();
/* 365 */         _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 366 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 369 */         out.write("\n\t  \t                            ");
/* 370 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/* 371 */           return true;
/* 372 */         out.write("\n\t  \t                            ");
/* 373 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 374 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 377 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 378 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 381 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 382 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f1);
/* 383 */       return true;
/*     */     }
/* 385 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f1);
/* 386 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 391 */     PageContext pageContext = _jspx_page_context;
/* 392 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 394 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 395 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 396 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*     */     
/* 398 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("selectedowners");
/* 399 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 400 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 401 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 402 */       return true;
/*     */     }
/* 404 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 405 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 410 */     PageContext pageContext = _jspx_page_context;
/* 411 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 413 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.get(SelectTag.class);
/* 414 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 415 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 417 */     _jspx_th_html_005fselect_005f2.setProperty("grouptype");
/* 418 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 419 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 420 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 421 */         out = _jspx_page_context.pushBody();
/* 422 */         _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 423 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 426 */         out.write("\n\t\t\t");
/* 427 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/* 428 */           return true;
/* 429 */         out.write("\n\t\t\t");
/* 430 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 431 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 434 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 435 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 438 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 439 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 440 */       return true;
/*     */     }
/* 442 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 443 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 448 */     PageContext pageContext = _jspx_page_context;
/* 449 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 451 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 452 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 453 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*     */     
/* 455 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("availableMGTypes");
/* 456 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 457 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 458 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 459 */       return true;
/*     */     }
/* 461 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 462 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\NewSubGroup_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */