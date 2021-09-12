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
/*     */ import java.util.Enumeration;
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
/*     */ public class AvgReportServlet
/*     */   extends BaseDBServlet
/*     */ {
/*     */   String pageQueryString;
/*     */   
/*     */   public synchronized void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/*  47 */     super.doGet(paramHttpServletRequest, paramHttpServletResponse);
/*     */     
/*  49 */     getPageQueryString();
/*     */     
/*     */ 
/*  52 */     String str1 = replace(this.tableHeader, "#PAGE_QUERY_STRING#", this.pageQueryString);
/*  53 */     this.out.println(str1);
/*     */     Connection localConnection;
/*     */     try
/*     */     {
/*  57 */       localConnection = BaseDBServlet.relapi.getConnection();
/*     */     } catch (Exception localException1) {
/*  59 */       return;
/*     */     }
/*  61 */     Statement localStatement = null;
/*  62 */     ResultSet localResultSet1 = null;
/*  63 */     ResultSet localResultSet2 = null;
/*  64 */     Vector localVector = new Vector();
/*     */     try {
/*  66 */       localStatement = localConnection.createStatement();
/*     */       
/*     */ 
/*     */ 
/*  70 */       String str2 = getTagString();
/*  71 */       this.sqlQuery += str2;
/*     */       
/*     */ 
/*     */ 
/*  75 */       if (this.table != null)
/*     */       {
/*  77 */         str3 = this.parameters.getProperty("tableDate");
/*  78 */         if (str3 != null) {
/*  79 */           if (str3.equals("TODAY")) {
/*  80 */             Date localDate = new Date();
/*  81 */             SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("M_d_yyyy");
/*     */             
/*  83 */             str3 = localSimpleDateFormat.format(localDate);
/*     */           }
/*     */           
/*  86 */           if (this.table.endsWith("%"))
/*  87 */             this.table = this.table.substring(0, this.table.length() - 1);
/*  88 */           this.table += str3;
/*     */         }
/*     */         
/*  91 */         int i = this.sqlQuery.indexOf("STATSDATA");
/*  92 */         if (i == -1) {
/*  93 */           System.err.println("AvgReportServlet: no STATSDATA found in query string." + this.sqlQuery);
/*     */         }
/*     */         else {
/*  96 */           int j = this.sqlQuery.indexOf(" ", i);
/*  97 */           this.sqlQuery = (this.sqlQuery.substring(0, i) + this.table + " " + this.sqlQuery.substring(j));
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 102 */       String str3 = this.parameters.getProperty("isMultiplePolledData");
/*     */       
/* 104 */       if (str3 == null)
/*     */       {
/* 106 */         this.sqlQuery = (this.sqlQuery + " " + getOrderByString());
/* 107 */         System.err.println("finalquery is :: " + this.sqlQuery);
/* 108 */         getResult(localConnection, localStatement, localResultSet1, this.sqlQuery);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 113 */         if (str3.equals("true"))
/*     */         {
/* 115 */           localObject1 = "select distinct INSTANCE from PolledData, " + this.table + " " + "where POLLID=ID " + str2;
/*     */           
/* 117 */           System.err.println(" instance Query is :: " + (String)localObject1);
/*     */           
/* 119 */           localResultSet2 = localStatement.executeQuery((String)localObject1);
/*     */           
/* 121 */           localVector = new Vector();
/*     */           
/* 123 */           while (localResultSet2.next())
/*     */           {
/* 125 */             localVector.addElement(localResultSet2.getString(1));
/*     */           }
/*     */           try
/*     */           {
/* 129 */             localResultSet2.close();
/*     */           }
/*     */           catch (Exception localException3) {}
/*     */         }
/*     */         else
/*     */         {
/* 135 */           localVector.addElement("-1");
/*     */         }
/*     */         
/* 138 */         for (Object localObject1 = localVector.elements(); ((Enumeration)localObject1).hasMoreElements();)
/*     */         {
/* 140 */           String str4 = (String)((Enumeration)localObject1).nextElement();
/* 141 */           String str5 = this.sqlQuery + "  AND INSTANCE like '" + str4 + "'  " + getOrderByString();
/*     */           
/* 143 */           System.err.println(" final Query is :: " + str5);
/*     */           
/* 145 */           getResult(localConnection, localStatement, localResultSet1, str5);
/*     */         }
/*     */       }
/* 148 */       localStatement.close();
/*     */     }
/*     */     catch (SQLException localSQLException) {
/* 151 */       System.err.println("Exception in executeQuery" + localSQLException + " for " + this.sqlQuery);
/*     */     }
/*     */     catch (Exception localException2)
/*     */     {
/* 155 */       System.err.println("Exception in generating reports" + localException2);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try {
/* 160 */         if (localResultSet1 != null)
/* 161 */           localResultSet1.close();
/* 162 */         if (localResultSet2 != null)
/* 163 */           localResultSet2.close();
/* 164 */         if (localStatement != null) {
/* 165 */           localStatement.close();
/*     */         }
/*     */       }
/*     */       catch (Exception localException4) {}
/*     */     }
/*     */     
/*     */ 
/* 172 */     this.out.flush();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   String getOrderByString()
/*     */   {
/* 181 */     String str1 = "";
/*     */     
/*     */ 
/* 184 */     String str2 = this.parameters.getProperty("SORT_COLUMN");
/* 185 */     if ((str2 != null) && (!str2.trim().equals("")) && (!str2.equalsIgnoreCase("all")))
/*     */     {
/* 187 */       str1 = str1 + " order by " + str2 + ", POLLID ";
/* 188 */       return str1;
/*     */     }
/*     */     
/*     */ 
/* 192 */     str2 = this.parameters.getProperty("SORT_COLUMN_DESC");
/* 193 */     if ((str2 != null) && (!str2.trim().equals("")) && (!str2.equalsIgnoreCase("all")))
/*     */     {
/* 195 */       str1 = str1 + " order by " + str2 + ", POLLID " + " desc";
/* 196 */       return str1;
/*     */     }
/*     */     
/* 199 */     str1 = str1 + " order by POLLID";
/* 200 */     return str1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void getPageQueryString()
/*     */   {
/* 207 */     StringBuffer localStringBuffer = new StringBuffer();
/* 208 */     String str = this.parameters.getProperty("table");
/* 209 */     if (str != null) {
/* 210 */       localStringBuffer.append("table=" + str);
/*     */       
/* 212 */       str = this.parameters.getProperty("tableDate");
/* 213 */       if (str != null) { localStringBuffer.append("&tableDate=" + str);
/*     */       }
/*     */     }
/* 216 */     this.pageQueryString = localStringBuffer.toString();
/*     */   }
/*     */   
/*     */ 
/*     */   void getResult(Connection paramConnection, Statement paramStatement, ResultSet paramResultSet, String paramString)
/*     */     throws Exception
/*     */   {
/* 223 */     int i = 0;
/* 224 */     paramResultSet = paramStatement.executeQuery(paramString);
/* 225 */     ResultSetMetaData localResultSetMetaData = paramResultSet.getMetaData();
/* 226 */     int j = localResultSetMetaData.getColumnCount();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 232 */     long l1 = -1L;
/* 233 */     String[] arrayOfString = new String[this.desiredCols.size()];
/* 234 */     long l2 = 0L;long l3 = 0L;long l4 = -1L;long l5 = 0L;
/* 235 */     int k = 0;
/* 236 */     String str = this.template;
/*     */     int m;
/* 238 */     while (paramResultSet.next())
/*     */     {
/*     */ 
/* 241 */       for (m = 0; m < this.desiredCols.size(); m++) {
/* 242 */         if (this.columnType.elementAt(m).equals("POLLID")) {
/* 243 */           l5 = paramResultSet.getLong(m + 1);
/*     */           
/* 245 */           if (l1 == -1L) l1 = l5;
/* 246 */           if (l5 != l1) {
/* 247 */             for (n = 0; n < this.desiredCols.size(); n++) {
/* 248 */               str = replace(str, "#" + this.desiredCols.elementAt(n) + "#", arrayOfString[n]);
/*     */             }
/* 250 */             l2 = l3 = k = 0;l4 = -1L;
/* 251 */             str = replace(str, "#PAGE_QUERY_STRING#", this.pageQueryString);
/*     */             
/* 253 */             this.out.println(str);
/* 254 */             this.out.flush();
/* 255 */             str = this.template;
/* 256 */             i++;
/*     */           }
/*     */           
/* 259 */           l1 = l5;
/*     */         }
/*     */       }
/*     */       
/* 263 */       k++;
/* 264 */       for (int n = 0; n < this.desiredCols.size(); n++)
/*     */       {
/* 266 */         Object localObject = paramResultSet.getObject(n + 1);
/* 267 */         arrayOfString[n] = localObject.toString();
/*     */         long l6;
/* 269 */         if (this.columnType.elementAt(n).equals("Counter-Avg")) {
/* 270 */           l6 = paramResultSet.getLong(n + 1);
/* 271 */           l2 += l6;
/* 272 */           arrayOfString[n] = String.valueOf(l2 / k);
/*     */         }
/*     */         
/* 275 */         if (this.columnType.elementAt(n).equals("Counter-Min")) {
/* 276 */           l6 = paramResultSet.getLong(n + 1);
/* 277 */           if ((l4 > l6) || (l4 == -1L)) l4 = l6;
/* 278 */           arrayOfString[n] = String.valueOf(l4);
/*     */         }
/*     */         
/* 281 */         if (this.columnType.elementAt(n).equals("Counter-Max")) {
/* 282 */           l6 = paramResultSet.getLong(n + 1);
/* 283 */           if (l3 < l6) l3 = l6;
/* 284 */           arrayOfString[n] = String.valueOf(l3);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 289 */       if (i == this.MAX_COUNT)
/*     */         break;
/*     */     }
/* 292 */     if (l1 != -1L) {
/* 293 */       for (m = 0; m < this.desiredCols.size(); m++) {
/* 294 */         str = replace(str, "#" + this.desiredCols.elementAt(m) + "#", arrayOfString[m]);
/*     */       }
/*     */       
/* 297 */       str = replace(str, "#PAGE_QUERY_STRING#", this.pageQueryString);
/*     */       
/* 299 */       if (i != this.MAX_COUNT) this.out.println(str);
/*     */     }
/*     */     try
/*     */     {
/* 303 */       paramResultSet.close();
/*     */     }
/*     */     catch (Exception localException) {}
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\AvgReportServlet.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */