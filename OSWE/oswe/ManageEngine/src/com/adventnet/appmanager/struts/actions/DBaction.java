/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import java.io.File;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class DBaction extends org.apache.struts.actions.DispatchAction
/*     */ {
/*  24 */   public static Logger logger = Logger.getLogger(DBaction.class.getName());
/*     */   
/*     */ 
/*     */ 
/*     */   public ActionForward dbdetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  30 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  31 */     ResultSet rs = null;
/*     */     
/*     */ 
/*  34 */     String DBType = System.getProperty("am.dbserver.type");
/*     */     
/*     */ 
/*     */ 
/*  38 */     Hashtable ht = new Hashtable();
/*     */     try
/*     */     {
/*  41 */       if (DBQueryUtil.isPgsql())
/*     */       {
/*  43 */         String sdquery = " SELECT relname as tablename, reltuples as rows,(pg_total_relation_size(cast(relname as varchar)) - pg_relation_size(cast(relname as varchar))) as indexlength FROM pg_class  JOIN pg_namespace ON relnamespace = pg_namespace.oid WHERE relkind = 'r' AND pg_namespace.nspname = 'public' ";
/*  44 */         rs = AMConnectionPool.executeQueryStmt(sdquery);
/*  45 */         while (rs.next()) {
/*  46 */           String user = rs.getString("TABLENAME");
/*  47 */           String c1 = rs.getString("ROWS");
/*  48 */           String c3 = rs.getString("INDEXLENGTH");
/*  49 */           String c4 = "";
/*     */           
/*  51 */           ArrayList a1 = new ArrayList();
/*  52 */           a1.add(c1);
/*  53 */           a1.add(c3);
/*  54 */           a1.add(c4);
/*  55 */           ht.put(user, a1);
/*     */         }
/*     */       }
/*  58 */       else if (DBQueryUtil.isMssql())
/*     */       {
/*  60 */         String mssqlquery = "SELECT t.name AS table_name,i.rows FROM sys.tables AS t INNER JOIN sys.sysindexes AS i ON t.object_id = i.id AND i.indid < 2";
/*  61 */         rs = AMConnectionPool.executeQueryStmt(mssqlquery);
/*  62 */         while (rs.next()) {
/*  63 */           String tablename = rs.getString(1);
/*  64 */           String rowindex = rs.getString(2);
/*     */           
/*  66 */           ArrayList SqlList = new ArrayList();
/*  67 */           SqlList.add(tablename);
/*  68 */           SqlList.add(rowindex);
/*  69 */           ht.put(tablename, SqlList);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*  74 */         String sdquery = " show table status ";
/*     */         
/*  76 */         rs = AMConnectionPool.executeQueryStmt(sdquery);
/*  77 */         while (rs.next()) {
/*  78 */           String user = rs.getString("Name");
/*  79 */           String c1 = rs.getString("Rows");
/*     */           
/*  81 */           String c3 = rs.getString("Index_length");
/*  82 */           String c4 = rs.getString("Comment");
/*     */           
/*  84 */           ArrayList a1 = new ArrayList();
/*  85 */           a1.add(c1);
/*     */           
/*  87 */           a1.add(c3);
/*  88 */           a1.add(c4);
/*  89 */           ht.put(user, a1);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  95 */       request.setAttribute("temp1", ht);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 110 */       return new ActionForward("/jsp/table.jsp");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  99 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try {
/* 104 */         if (rs != null) {
/* 105 */           rs.close();
/*     */         }
/*     */       }
/*     */       catch (Exception e) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward DBstatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 117 */     return new ActionForward("/jsp/DB.jsp");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward querydetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 126 */     ResultSet rs = null;
/* 127 */     Statement stmt = null;
/*     */     try
/*     */     {
/* 130 */       String date = request.getParameter("date");
/* 131 */       String product = request.getParameter("product");
/*     */       
/*     */ 
/* 134 */       AMActionForm rf = (AMActionForm)form;
/* 135 */       if (date != null) {
/* 136 */         request.setAttribute("product", product);
/* 137 */         return new ActionForward("/jsp/query.jsp");
/*     */       }
/*     */       
/*     */ 
/* 141 */       String Textarea = rf.getDescription();
/* 142 */       if ((Textarea != null) || (!Textarea.trim().equals("")))
/*     */       {
/*     */ 
/* 145 */         Textarea = Textarea.replaceAll("&quot;", "\"");
/* 146 */         Textarea = Textarea.replaceAll("&#39;", "'");
/* 147 */         Textarea = Textarea.replaceAll("&#96;", "`");
/* 148 */         Textarea = Textarea.replaceAll("&lt;", "<");
/* 149 */         Textarea = Textarea.replaceAll("&gt;", ">");
/*     */         
/*     */ 
/* 152 */         int noOfQueries = 0;
/* 153 */         StringTokenizer tokens = new StringTokenizer(Textarea, "\n");
/* 154 */         Textarea = "";
/* 155 */         while (tokens.hasMoreTokens())
/*     */         {
/* 157 */           String tok = tokens.nextToken();
/* 158 */           tok = tok.trim();
/* 159 */           if ((tok.length() != 0) && (tok.indexOf("#") != 0))
/*     */           {
/*     */ 
/* 162 */             Textarea = tok;
/* 163 */             noOfQueries++;
/*     */           } }
/* 165 */         com.adventnet.appmanager.logging.AMLog.audit("DBAction Query Details Host=" + request.getRemoteHost() + "   Port=" + request.getRemotePort() + "    Query=" + Textarea);
/* 166 */         if (noOfQueries > 1)
/*     */         {
/* 168 */           throw new Exception("Cannot execute more than one query at a time. Please use '#' to comment query.");
/*     */         }
/* 170 */         if (Textarea.length() > 0)
/*     */         {
/* 172 */           request.setAttribute("result_query", "" + Textarea);
/*     */         }
/*     */         
/*     */ 
/* 176 */         logger.log(Level.INFO, "##$$##product name ##$$## " + product);
/* 177 */         String confPath = "";
/*     */         
/* 179 */         logger.log(Level.INFO, "user directory " + System.getProperty("user.dir"));
/* 180 */         String home = System.getProperty("user.dir") + File.separator + ".." + File.separator + ".." + File.separator;
/*     */         
/* 182 */         if (product == null)
/*     */         {
/* 184 */           product = "apm";
/*     */         }
/* 186 */         if (product.equals("console"))
/*     */         {
/* 188 */           confPath = home + File.separator + "conf" + File.separator + "database_params.conf";
/*     */         }
/* 190 */         else if (product.equals("networks"))
/*     */         {
/* 192 */           confPath = home + File.separator + "conf" + File.separator + "OpManager" + File.separator + "opm-remote" + File.separator + "database_params.conf";
/*     */ 
/*     */         }
/* 195 */         else if (product.equals("netflow"))
/*     */         {
/* 197 */           confPath = home + File.separator + "conf" + File.separator + "NetflowAnalyzer" + File.separator + "nfa-remote" + File.separator + "database_params.conf";
/*     */         }
/* 199 */         else if (product.equals("servicedesk"))
/*     */         {
/* 201 */           confPath = home + File.separator + "conf" + File.separator + "ServiceDesk" + File.separator + "sdp-remote" + File.separator + "database_params.conf";
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 206 */           confPath = home + File.separator + "applications" + File.separator + "working" + File.separator + "conf" + File.separator + "database_params.conf";
/*     */         }
/* 208 */         AMConnectionPool pool = null;
/* 209 */         Connection IT360con = null;
/*     */         
/*     */ 
/* 212 */         if (product.equals("apm")) {
/* 213 */           pool = AMConnectionPool.getInstance();
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 218 */           IT360con = com.manageengine.it360.util.IT360DBToolUtil.getInstance().getConnection(confPath, product);
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 223 */         String Textarea1 = Textarea.toLowerCase();
/*     */         
/* 225 */         if ((Textarea1.startsWith("insert")) || (Textarea1.startsWith("delete")) || (Textarea1.startsWith("update")))
/*     */         {
/*     */           try
/*     */           {
/* 229 */             int k = 0;
/* 230 */             if (product.equals("apm"))
/*     */             {
/*     */ 
/* 233 */               k = AMConnectionPool.executeUpdateStmt(Textarea);
/*     */             }
/*     */             else
/*     */             {
/* 237 */               stmt = IT360con.createStatement();
/* 238 */               k = stmt.executeUpdate(Textarea);
/*     */             }
/* 240 */             request.setAttribute("update1", "" + k);
/* 241 */             return new ActionForward("/jsp/dump.jsp");
/*     */ 
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/*     */ 
/* 247 */             e.printStackTrace();
/* 248 */             request.setAttribute("update2", "" + e);
/*     */             try
/*     */             {
/* 251 */               if (stmt != null)
/*     */               {
/* 253 */                 stmt.close();
/*     */               }
/*     */             }
/*     */             catch (Exception e1)
/*     */             {
/* 258 */               e1.printStackTrace();
/*     */             }
/*     */             
/*     */           }
/*     */           finally
/*     */           {
/*     */             try
/*     */             {
/* 266 */               if (stmt != null)
/*     */               {
/* 268 */                 stmt.close();
/*     */               }
/*     */             }
/*     */             catch (Exception e1)
/*     */             {
/* 273 */               e1.printStackTrace();
/* 274 */               request.setAttribute("update2", "" + e1);
/*     */             }
/*     */             
/*     */           }
/*     */           
/*     */ 
/*     */         }
/* 281 */         else if (Textarea1.startsWith("use"))
/*     */         {
/*     */ 
/* 284 */           String errorinuse = "You cannot execute use command here";
/* 285 */           request.setAttribute("update3", "" + errorinuse);
/*     */         }
/*     */         else
/*     */         {
/* 289 */           if (product.equals("apm"))
/*     */           {
/*     */ 
/* 292 */             rs = AMConnectionPool.executeQueryStmt(Textarea);
/*     */           }
/*     */           else
/*     */           {
/*     */             try
/*     */             {
/*     */ 
/* 299 */               rs = IT360con.createStatement().executeQuery(Textarea);
/*     */             }
/*     */             catch (Exception e1)
/*     */             {
/* 303 */               e1.printStackTrace();
/* 304 */               request.setAttribute("update3", "" + e1);
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 309 */           request.setAttribute("temp2", rs);
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 320 */         rf.setCommand(Textarea);
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 326 */       e.printStackTrace();
/* 327 */       request.setAttribute("update3", "" + e);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 335 */     return new ActionForward("/jsp/dump.jsp");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward threaddump(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 345 */     return new ActionForward("/jsp/threaddump.jsp");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void deletAllAmazonMonitors()
/*     */   {
/* 354 */     ResultSet resIdRs = null;
/* 355 */     ArrayList<String> resIdList = null;
/*     */     try
/*     */     {
/* 358 */       resIdList = new ArrayList();
/* 359 */       resIdRs = AMConnectionPool.executeQueryStmt("select RESOURCEID from AM_ManagedObject where type='EC2Instance'");
/* 360 */       while (resIdRs.next())
/*     */       {
/* 362 */         resIdList.add(resIdRs.getString("RESOURCEID"));
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 367 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 371 */       AMConnectionPool.closeStatement(resIdRs);
/*     */     }
/* 373 */     com.adventnet.appmanager.logging.AMLog.debug("Going to deletAllAmazonMonitors : " + resIdList);
/* 374 */     if (!resIdList.isEmpty())
/*     */     {
/* 376 */       com.adventnet.appmanager.server.framework.datacollection.DataCollectionDBUtil.deleteMonitor((String[])resIdList.toArray(new String[0]), new java.util.Properties(), "EC2Instance");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 385 */     if (com.adventnet.appmanager.util.AppManagerUtil.isArgsTablePurged())
/*     */     {
/* 387 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 393 */       AMConnectionPool.executeUpdateStmt("delete from AM_ARGS_" + com.adventnet.appmanager.util.DBUtil.getBaseId("EC2Instance"));
/*     */       
/* 395 */       AMConnectionPool.executeUpdateStmt("INSERT INTO AM_GLOBALCONFIG values('am.clean.amazonargstables','true')");
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 399 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\DBaction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */