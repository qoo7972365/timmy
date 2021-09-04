/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
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
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class UpdateResourceMonitorGroupList_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  24 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  33 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  37 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  38 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  48 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  51 */     JspWriter out = null;
/*  52 */     Object page = this;
/*  53 */     JspWriter _jspx_out = null;
/*  54 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  58 */       response.setContentType("text/html");
/*  59 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  61 */       _jspx_page_context = pageContext;
/*  62 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  63 */       ServletConfig config = pageContext.getServletConfig();
/*  64 */       session = pageContext.getSession();
/*  65 */       out = pageContext.getOut();
/*  66 */       _jspx_out = out;
/*     */       
/*  68 */       out.write("\n\n\n\n\n\n\n\n\n\n");
/*     */       
/*  70 */       AMConnectionPool ME_cp = AMConnectionPool.getInstance();
/*  71 */       ManagedApplication mo = new ManagedApplication();
/*  72 */       ArrayList monitorList = mo.getRows("select RESOURCENAME,DISPLAYNAME,-1,OWNER,CREATIONDATE,MODIFIEDDATE,AM_HOLISTICAPPLICATION.HAID from AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and AM_HOLISTICAPPLICATION.TYPE=0  order by RESOURCENAME");
/*  73 */       String stateView = "viewedit";
/*     */       
/*  75 */       String viewName = request.getParameter("newviewname");
/*     */       
/*  77 */       if ((viewName == null) || (viewName.trim().equals("")))
/*     */       {
/*  79 */         viewName = request.getParameter("viewName");
/*  80 */         if ((viewName == null) || (viewName.trim().equals("")))
/*     */         {
/*  82 */           viewName = "DefaultView";
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  88 */       String isCheckView = request.getParameter("isCreateView");
/*     */       
/*  90 */       if (isCheckView.equals("true"))
/*     */       {
/*  92 */         if (viewName != null)
/*     */         {
/*     */           try
/*     */           {
/*     */ 
/*  97 */             String viewExistQuery = "Select VIEWNAME from AM_STANDALONE_VIEWDETAILS where VIEWNAME='" + viewName + "'";
/*  98 */             ArrayList viewnameList = mo.getRows(viewExistQuery);
/*  99 */             int size = viewnameList.size();
/* 100 */             if (size == 0)
/*     */             {
/* 102 */               System.out.println("updatesize" + size);
/* 103 */               int incrViewID = DBQueryUtil.getIncrementedID("VIEWID", "AM_STANDALONE_VIEWDETAILS");
/* 104 */               String query = "insert into AM_STANDALONE_VIEWDETAILS values(" + incrViewID + ",'" + viewName + "')";
/* 105 */               System.out.println("query" + query);
/* 106 */               mo.executeUpdateStmt(query);
/* 107 */               stateView = "viewcreate";
/*     */             }
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 112 */             e.printStackTrace(); return;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 117 */       int viewID = com.adventnet.appmanager.util.DBUtil.getViewID(viewName);
/*     */       
/*     */ 
/*     */ 
/* 121 */       String ME_del_query = "delete from AM_STANDALONE_VIEW_MONITORGROUP_ASSOCIATION where VIEWID=" + viewID + "";
/* 122 */       System.out.println("ME_del_query" + ME_del_query);
/*     */       try
/*     */       {
/* 125 */         AMConnectionPool.executeUpdateStmt(ME_del_query);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 129 */         e.printStackTrace(); return;
/*     */       }
/*     */       
/* 132 */       int view_avail = -1;int monitorID = 0;
/* 133 */       int listCount = monitorList.size();
/* 134 */       int mo_list_count = monitorList.size();
/* 135 */       for (int i = 0; i < mo_list_count; i++)
/*     */       {
/* 137 */         String temp_chk = request.getParameter("ch_index" + i);
/* 138 */         if (temp_chk != null)
/*     */         {
/* 140 */           for (int j = 0; j < listCount; j++)
/*     */           {
/* 142 */             ArrayList tempView = (ArrayList)monitorList.get(j);
/* 143 */             String tempView1 = (String)tempView.get(6);
/* 144 */             if (temp_chk.equals(tempView1))
/*     */             {
/* 146 */               monitorID = Integer.parseInt((String)tempView.get(6));
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 151 */           String ME_query = "insert into AM_STANDALONE_VIEW_MONITORGROUP_ASSOCIATION (VIEWID,MONITORGROUPID) values (" + viewID + "," + monitorID + ")";
/* 152 */           System.out.println("ME_query" + ME_query);
/*     */           try
/*     */           {
/* 155 */             AMConnectionPool.executeUpdateStmt(ME_query);
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 159 */             e.printStackTrace(); return;
/*     */           }
/*     */           
/*     */ 
/* 163 */           view_avail = 1;
/*     */         }
/*     */       }
/*     */       
/* 167 */       if (view_avail == -1)
/*     */       {
/*     */ 
/* 170 */         String ME_query = "insert into AM_STANDALONE_VIEW_MONITORGROUP_ASSOCIATION (VIEWID,MONITORGROUPID) values (" + viewID + ",-1)";
/* 171 */         System.out.println("ME_query" + ME_query);
/*     */         try
/*     */         {
/* 174 */           AMConnectionPool.executeUpdateStmt(ME_query);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 178 */           e.printStackTrace(); return;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 183 */       out.write("\n\n<script>\nwindow.location=\"/jsp/ConfigureViews.jsp?selectedTab=divMonitorGroup&isCreateView=false&state=");
/* 184 */       out.print(stateView);
/* 185 */       out.write("&viewname=");
/* 186 */       out.print(viewName);
/* 187 */       out.write("\";\n</script>\n");
/*     */       
/*     */ 
/*     */ 
/* 191 */       out.write(10);
/*     */     } catch (Throwable t) {
/* 193 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 194 */         out = _jspx_out;
/* 195 */         if ((out != null) && (out.getBufferSize() != 0))
/* 196 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 197 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 200 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\UpdateResourceMonitorGroupList_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */