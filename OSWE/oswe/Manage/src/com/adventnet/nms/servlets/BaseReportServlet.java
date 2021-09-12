/*     */ package com.adventnet.nms.servlets;
/*     */ 
/*     */ import com.adventnet.nms.store.relational.RelationalAPI;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BaseReportServlet
/*     */   extends BaseDBServlet
/*     */ {
/*     */   String pageQueryString;
/*     */   Connection con;
/*     */   
/*     */   public synchronized void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/*  50 */     super.doGet(paramHttpServletRequest, paramHttpServletResponse);
/*     */     
/*  52 */     getPageQueryString();
/*     */     
/*     */ 
/*  55 */     String str1 = replace(this.tableHeader, "#PAGE_QUERY_STRING#", this.pageQueryString);
/*  56 */     this.out.println(str1);
/*     */     
/*     */     try
/*     */     {
/*  60 */       this.con = BaseDBServlet.relapi.getConnection(); } catch (Exception localException1) { return; }
/*  61 */     Statement localStatement = null;
/*  62 */     ResultSet localResultSet = null;
/*     */     try {
/*  64 */       localStatement = this.con.createStatement();
/*     */       
/*  66 */       int i = 0;
/*     */       
/*  68 */       String str2 = getTagString();
/*  69 */       this.sqlQuery += str2;
/*     */       
/*     */ 
/*  72 */       this.sqlQuery = (this.sqlQuery + " " + getOrderByString());
/*     */       
/*  74 */       if (this.table != null)
/*     */       {
/*  76 */         localObject1 = this.parameters.getProperty("tableDate");
/*  77 */         if (localObject1 != null) {
/*  78 */           if (((String)localObject1).equals("TODAY")) {
/*  79 */             Date localDate1 = new Date();
/*  80 */             SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("M_d_yyyy");
/*     */             
/*  82 */             localObject1 = localSimpleDateFormat1.format(localDate1);
/*     */           }
/*  84 */           this.table += (String)localObject1;
/*     */         }
/*  86 */         j = this.sqlQuery.indexOf("STATSDATA");
/*  87 */         if (j == -1) {
/*  88 */           System.err.println("BaseReportServlet: no STATSDATA found in query string." + this.sqlQuery);
/*     */         } else {
/*  90 */           int k = this.sqlQuery.indexOf(" ", j);
/*  91 */           this.sqlQuery = (this.sqlQuery.substring(0, j) + this.table + " " + this.sqlQuery.substring(k));
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*  96 */       System.err.println("finalquery is :: " + this.sqlQuery);
/*     */       
/*  98 */       localResultSet = localStatement.executeQuery(this.sqlQuery);
/*  99 */       Object localObject1 = localResultSet.getMetaData();
/* 100 */       int j = ((ResultSetMetaData)localObject1).getColumnCount();
/*     */       
/* 102 */       while (localResultSet.next()) {
/* 103 */         String str3 = this.template;
/* 104 */         for (int m = 0; m < this.desiredCols.size(); m++) {
/* 105 */           Object localObject2 = localResultSet.getObject(m + 1);
/* 106 */           String str4 = localObject2.toString();
/*     */           
/* 108 */           if (this.columnType.elementAt(m).equals("Time")) {
/* 109 */             long l = localResultSet.getLong(m + 1);
/*     */             
/* 111 */             Date localDate2 = new Date(l);
/* 112 */             SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
/*     */             
/* 114 */             str4 = localSimpleDateFormat2.format(localDate2);
/*     */           }
/*     */           
/*     */ 
/* 118 */           str3 = replace(str3, "#" + this.desiredCols.elementAt(m) + "#", str4);
/*     */         }
/* 120 */         i++;
/* 121 */         if (i == this.MAX_COUNT) break;
/* 122 */         str3 = replace(str3, "#PAGE_QUERY_STRING#", this.pageQueryString);
/* 123 */         this.out.println(str3);
/* 124 */         this.out.flush();
/*     */       }
/* 126 */       localResultSet.close();
/* 127 */       localStatement.close();
/*     */     }
/*     */     catch (SQLException localSQLException) {
/* 130 */       System.err.println("Exception in executeQuery" + localSQLException + " for " + this.sqlQuery);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try {
/* 135 */         if (localResultSet != null)
/* 136 */           localResultSet.close();
/* 137 */         if (localStatement != null) {
/* 138 */           localStatement.close();
/*     */         }
/*     */       }
/*     */       catch (Exception localException2) {}
/*     */     }
/* 143 */     this.out.flush();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void getPageQueryString()
/*     */   {
/* 150 */     StringBuffer localStringBuffer = new StringBuffer();
/* 151 */     String str = this.parameters.getProperty("table");
/* 152 */     if (str != null) {
/* 153 */       localStringBuffer.append("table=" + str);
/*     */       
/* 155 */       str = this.parameters.getProperty("tableDate");
/* 156 */       if (str != null) { localStringBuffer.append("&tableDate=" + str);
/*     */       }
/*     */     }
/* 159 */     str = this.parameters.getProperty("AGENT");
/* 160 */     if (str != null) { localStringBuffer.append("&AGENT=" + str);
/*     */     }
/*     */     
/* 163 */     str = this.parameters.getProperty("NAME");
/* 164 */     if (str != null) { localStringBuffer.append("&NAME=" + str);
/*     */     }
/*     */     
/* 167 */     str = this.parameters.getProperty("OID");
/* 168 */     if (str != null) { localStringBuffer.append("&OID=" + str);
/*     */     }
/*     */     
/* 171 */     str = this.parameters.getProperty("VAL");
/* 172 */     if (str != null) { localStringBuffer.append("&VAL=" + str);
/*     */     }
/* 174 */     this.pageQueryString = localStringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\BaseReportServlet.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */