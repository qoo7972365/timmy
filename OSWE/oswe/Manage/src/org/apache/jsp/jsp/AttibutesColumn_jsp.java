/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.ReportUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ 
/*     */ public final class AttibutesColumn_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  23 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  32 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  36 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  37 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  47 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  50 */     JspWriter out = null;
/*  51 */     Object page = this;
/*  52 */     JspWriter _jspx_out = null;
/*  53 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  57 */       response.setContentType("text/html");
/*  58 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  60 */       _jspx_page_context = pageContext;
/*  61 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  62 */       ServletConfig config = pageContext.getServletConfig();
/*  63 */       session = pageContext.getSession();
/*  64 */       out = pageContext.getOut();
/*  65 */       _jspx_out = out;
/*     */       
/*  67 */       out.write("\n\n\n\n\n\n\n");
/*     */       
/*     */ 
/*  70 */       ManagedApplication mo = new ManagedApplication();
/*  71 */       AMConnectionPool ME_cp = AMConnectionPool.getInstance();
/*     */       
/*  73 */       ResultSet scriptResourceSet = null;
/*  74 */       String defaultAttrQuery = "";
/*  75 */       String defaultAttrID = "";
/*  76 */       String defaultAttrName = "";
/*  77 */       String tempList1 = "";
/*  78 */       ResultSet defaultAttrSet = null;
/*     */       
/*  80 */       String resourceType = request.getParameter("resourceType");
/*  81 */       System.out.println("ajaxresourceType" + resourceType);
/*  82 */       String scriptResourceID = "";
/*  83 */       String scriptResourceName = "";
/*  84 */       String tempList = "";
/*     */       
/*  86 */       if (resourceType != "")
/*     */       {
/*     */ 
/*     */ 
/*  90 */         if (!resourceType.equals("Script Monitor"))
/*     */         {
/*     */           try
/*     */           {
/*     */ 
/*  95 */             defaultAttrQuery = "select ATTRIBUTEID, DISPLAYNAME from AM_ATTRIBUTES where RESOURCETYPE='" + resourceType + "' and DISPLAYNAME in('Availability','Health');";
/*  96 */             defaultAttrSet = AMConnectionPool.executeQueryStmt(defaultAttrQuery);
/*  97 */             while (defaultAttrSet.next())
/*     */             {
/*  99 */               defaultAttrID = defaultAttrSet.getString("ATTRIBUTEID");
/* 100 */               defaultAttrName = defaultAttrSet.getString("DISPLAYNAME");
/* 101 */               tempList = tempList + defaultAttrID + "#" + defaultAttrName + ",";
/*     */             }
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 106 */             e.printStackTrace();
/*     */             
/*     */ 
/*     */ 
/*     */ 
/* 111 */             AMConnectionPool.closeStatement(defaultAttrSet); return; } finally { AMConnectionPool.closeStatement(defaultAttrSet);
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 116 */         if (resourceType.equals("Script Monitor"))
/*     */         {
/*     */           try
/*     */           {
/* 120 */             String scriptResourceQuery = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where TYPE='Script Monitor' order by DISPLAYNAME";
/* 121 */             scriptResourceSet = AMConnectionPool.executeQueryStmt(scriptResourceQuery);
/* 122 */             while (scriptResourceSet.next())
/*     */             {
/* 124 */               scriptResourceID = scriptResourceSet.getString("RESOURCEID");
/* 125 */               scriptResourceName = scriptResourceSet.getString("DISPLAYNAME");
/* 126 */               tempList = tempList + scriptResourceID + "#" + scriptResourceName + ",";
/*     */             }
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 131 */             e.printStackTrace();
/*     */             
/*     */ 
/*     */ 
/*     */ 
/* 136 */             AMConnectionPool.closeStatement(scriptResourceSet); return; } finally { AMConnectionPool.closeStatement(scriptResourceSet);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 141 */           List attList = ReportUtil.getAttributesForResourcetype(resourceType);
/*     */           
/* 143 */           String healthIDQuery = "";
/* 144 */           String healthID = "";
/* 145 */           ResultSet healthIDSet = null;
/*     */           try
/*     */           {
/* 148 */             healthIDQuery = "select ATTRIBUTEID from AM_ATTRIBUTES where RESOURCETYPE='" + resourceType + "' and ATTRIBUTE in ('Health')";
/* 149 */             healthIDSet = AMConnectionPool.executeQueryStmt(healthIDQuery);
/*     */             
/* 151 */             while (healthIDSet.next())
/*     */             {
/* 153 */               healthID = healthIDSet.getString("ATTRIBUTEID");
/*     */             }
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 158 */             e.printStackTrace();
/*     */             
/*     */ 
/*     */ 
/*     */ 
/* 163 */             AMConnectionPool.closeStatement(healthIDSet); return; } finally { AMConnectionPool.closeStatement(healthIDSet);
/*     */           }
/*     */           
/* 166 */           ArrayList childAttrList = ReportUtil.getSecondLevelAttribute(healthID, resourceType, true);
/* 167 */           int childCount = childAttrList.size();
/* 168 */           for (int k = 0; k < childCount; k++)
/*     */           {
/*     */ 
/* 171 */             String temp = (String)childAttrList.get(k);
/* 172 */             if (!attList.contains(temp))
/*     */             {
/* 174 */               attList.add(temp);
/*     */             }
/* 176 */             else if (attList.contains(temp))
/*     */             {
/* 178 */               int index = attList.indexOf(temp);
/* 179 */               attList.remove(index);
/* 180 */               attList.add(temp);
/*     */             }
/*     */           }
/*     */           
/* 184 */           int attributeCount = attList.size();
/* 185 */           for (int j = 0; j < attributeCount; j++)
/*     */           {
/* 187 */             String res = (String)attList.get(j);
/* 188 */             tempList = tempList + res + ",";
/*     */           }
/*     */         }
/* 191 */         tempList = tempList.substring(0, tempList.length() - 1);
/* 192 */         tempList1 = tempList.trim();
/* 193 */         System.out.println("tempList>>>>>>>>>" + tempList1);
/*     */       }
/* 195 */       response.setContentType("text/html");
/* 196 */       response.setHeader("Cache-Control", "no-cache");
/* 197 */       response.getWriter().write(tempList1);
/*     */       
/* 199 */       out.write(10);
/* 200 */       out.write(10);
/*     */     } catch (Throwable t) {
/* 202 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 203 */         out = _jspx_out;
/* 204 */         if ((out != null) && (out.getBufferSize() != 0))
/* 205 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 206 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 209 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AttibutesColumn_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */