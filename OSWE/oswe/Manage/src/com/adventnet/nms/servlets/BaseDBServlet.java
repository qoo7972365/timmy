/*     */ package com.adventnet.nms.servlets;
/*     */ 
/*     */ import com.adventnet.nms.commonfe.GenericFEAPIImpl;
/*     */ import com.adventnet.nms.store.relational.RelationalAPI;
/*     */ import com.adventnet.nms.util.GenericUtility;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import com.adventnet.nms.util.PureServerUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URLEncoder;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.GenericServlet;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ 
/*     */ public class BaseDBServlet
/*     */   extends AuthenticationServlet
/*     */ {
/*     */   Properties parameters;
/*     */   Hashtable paramTable;
/*  33 */   Vector desiredCols = null;
/*     */   
/*  35 */   Vector columnType = null;
/*     */   
/*     */ 
/*  38 */   int MAX_COUNT = 1000;
/*     */   
/*     */ 
/*  41 */   String tableHeader = null;
/*     */   
/*  43 */   String template = null;
/*     */   
/*  45 */   String sqlQuery = null;
/*     */   
/*  47 */   String table = null;
/*     */   
/*     */ 
/*     */ 
/*  51 */   static RelationalAPI relapi = null;
/*     */   
/*     */ 
/*  54 */   String url = "jdbc:odbc:WebNmsDB";
/*     */   
/*     */ 
/*  57 */   String userName = "admin";
/*     */   
/*     */ 
/*  60 */   String password = "public";
/*     */   
/*     */ 
/*  63 */   String driverName = "sun.jdbc.odbc.JdbcOdbcDriver";
/*     */   
/*  65 */   PrintWriter out = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void init(ServletConfig paramServletConfig)
/*     */     throws ServletException
/*     */   {
/*  78 */     super.init(paramServletConfig);
/*     */   }
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
/*     */   public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/*  96 */     doGet(paramHttpServletRequest, paramHttpServletResponse);
/*     */   }
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
/*     */   public synchronized void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/* 113 */     this.paramTable = ((Hashtable)paramHttpServletRequest.getAttribute("parameters"));
/* 114 */     this.out = paramHttpServletResponse.getWriter();
/* 115 */     getParameters(paramHttpServletRequest);
/* 116 */     setMaxCount();
/*     */     
/* 118 */     String str1 = this.parameters.getProperty("tableColumns");
/* 119 */     Object localObject; if (str1 != null) {
/* 120 */       this.desiredCols = new Vector();
/* 121 */       localObject = new StringTokenizer(str1);
/* 122 */       while (((StringTokenizer)localObject).hasMoreTokens()) this.desiredCols.addElement(((StringTokenizer)localObject).nextToken());
/* 123 */     } else { errorPage("Table columns unspecified. ", paramHttpServletRequest, paramHttpServletResponse);
/*     */     }
/* 125 */     str1 = this.parameters.getProperty("COLUMN-TYPE");
/* 126 */     this.columnType = new Vector();
/* 127 */     if (str1 != null) {
/* 128 */       localObject = new StringTokenizer(str1);
/* 129 */       while (((StringTokenizer)localObject).hasMoreTokens()) this.columnType.addElement(((StringTokenizer)localObject).nextToken());
/* 130 */     } else { errorPage("Table column types unspecified. ", paramHttpServletRequest, paramHttpServletResponse);
/*     */     }
/* 132 */     this.tableHeader = this.parameters.getProperty("tableHeader");
/* 133 */     this.template = this.parameters.getProperty("template");
/* 134 */     this.table = this.parameters.getProperty("table");
/*     */     
/* 136 */     this.sqlQuery = this.parameters.getProperty("SQL-QUERY");
/*     */     
/* 138 */     if (relapi == null) relapi = NmsUtil.relapi;
/* 139 */     if (relapi == null) {
/* 140 */       try { System.err.println("DB Servlet: NmsUtil.relapi null. Use direct");
/* 141 */         localObject = PureServerUtils.url;
/* 142 */         String str2 = PureServerUtils.userName;
/* 143 */         String str3 = PureServerUtils.password;
/* 144 */         String str4 = PureServerUtils.driverName;
/* 145 */         RelationalAPI localRelationalAPI = new RelationalAPI((String)localObject, str2, str3, str4, true);
/*     */ 
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/* 150 */         errorPage("Cannot connect to SQL database: ", paramHttpServletRequest, paramHttpServletResponse);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   String getOrderByString()
/*     */   {
/* 160 */     String str1 = "";
/*     */     
/*     */ 
/* 163 */     String str2 = this.parameters.getProperty("SORT_COLUMN");
/* 164 */     if ((str2 != null) && (!str2.trim().equals("")) && (!str2.equalsIgnoreCase("all")))
/*     */     {
/* 166 */       str1 = str1 + " order by " + str2;
/*     */     }
/*     */     else
/*     */     {
/* 170 */       str2 = this.parameters.getProperty("SORT_COLUMN_DESC");
/* 171 */       if ((str2 != null) && (!str2.trim().equals("")) && (!str2.equalsIgnoreCase("all")))
/*     */       {
/* 173 */         str1 = str1 + " order by " + str2 + " desc";
/*     */       }
/*     */     }
/*     */     
/* 177 */     return str1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   String getTagString()
/*     */   {
/* 184 */     Properties localProperties = new Properties();
/* 185 */     String str1 = "";
/* 186 */     String str2 = "";
/* 187 */     String str3 = "";
/*     */     
/*     */ 
/* 190 */     String str5 = this.parameters.getProperty("time1");
/* 191 */     String str6 = this.parameters.getProperty("time2");
/* 192 */     if ((str5 != null) && (!str5.trim().equals("")))
/*     */       try
/*     */       {
/* 195 */         Date localDate1 = GenericUtility.parseDate(str5);
/* 196 */         str2 = String.valueOf(GenericUtility.getParsedTime(localDate1));
/*     */       } catch (Exception localException1) {
/* 198 */         GenericFEAPIImpl.log(NmsUtil.GetString("Invalid Date Value") + " " + str5 + " - " + NmsUtil.GetString("Use the Pattern") + NmsUtil.GetString(GenericUtility.NmsFormatter.format(new Date())));
/*     */       } else {
/* 200 */       str2 = "";
/*     */     }
/* 202 */     if ((str6 != null) && (!str6.trim().equals("")))
/*     */       try
/*     */       {
/* 205 */         Date localDate2 = GenericUtility.parseDate(str6);
/* 206 */         str3 = String.valueOf(GenericUtility.getParsedTime(localDate2));
/*     */       } catch (Exception localException2) {
/* 208 */         GenericFEAPIImpl.log(NmsUtil.GetString("Invalid Date Value") + " " + str6 + " - " + NmsUtil.GetString("Use the Pattern") + NmsUtil.GetString(GenericUtility.NmsFormatter.format(new Date())));
/*     */       } else {
/* 210 */       str3 = "";
/*     */     }
/* 212 */     if ((!str2.equals("")) || (!str3.equals(""))) {
/* 213 */       str1 = str1 + returnSQLfortime("rowcreatetime", str2, str3);
/*     */     }
/* 215 */     for (int i = 0; i < this.desiredCols.size(); i++) {
/* 216 */       String str4 = this.parameters.getProperty((String)this.desiredCols.elementAt(i));
/* 217 */       if ((str4 != null) && (!str4.trim().equals("")) && (!str4.equalsIgnoreCase("all"))) {
/* 218 */         str1 = str1 + checkTypeAndReturnSQL((String)this.desiredCols.elementAt(i), str4, (String)this.columnType.elementAt(i));
/*     */       }
/*     */     }
/*     */     
/* 222 */     return str1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String returnSQLfortime(String paramString1, String paramString2, String paramString3)
/*     */   {
/* 230 */     String str = " AND (" + paramString1 + " BETWEEN " + paramString2 + " AND " + paramString3 + ")";
/*     */     
/* 232 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   String checkTypeAndReturnSQL(String paramString1, String paramString2, String paramString3)
/*     */   {
/* 239 */     if ((paramString3.equals("Counter")) || (paramString3.equals("Integer")))
/*     */     {
/* 241 */       return SQLforrange(paramString1, paramString2);
/*     */     }
/* 243 */     if (paramString3.equals("String"))
/*     */     {
/* 245 */       return returnSQL(paramString1, paramString2);
/*     */     }
/* 247 */     return "";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String returnSQL(String paramString1, String paramString2)
/*     */   {
/* 254 */     String str = "";
/* 255 */     if ((paramString2 != null) || (!paramString2.trim().equals("")))
/* 256 */       str = "AND (" + paramString1 + " LIKE '%" + paramString2 + "%')";
/* 257 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String SQLforrange(String paramString1, String paramString2)
/*     */   {
/* 264 */     String str1 = "";
/* 265 */     StringTokenizer localStringTokenizer1 = new StringTokenizer(paramString2.trim(), ",");
/*     */     
/* 267 */     while (localStringTokenizer1.hasMoreTokens())
/*     */     {
/* 269 */       String str2 = localStringTokenizer1.nextToken();
/* 270 */       int i = -1;
/* 271 */       StringTokenizer localStringTokenizer2 = new StringTokenizer(str2, "-");
/* 272 */       if (localStringTokenizer2.countTokens() != 2) {
/* 273 */         str1 = str1 + " AND (" + paramString1 + " > " + localStringTokenizer2.nextToken().trim() + " )";
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 278 */         String str3 = localStringTokenizer2.nextToken().trim();
/* 279 */         String str4 = localStringTokenizer2.nextToken().trim();
/*     */         int j;
/*     */         int k;
/*     */         try {
/* 283 */           j = Integer.parseInt(str3);
/* 284 */           k = Integer.parseInt(str4);
/*     */         }
/*     */         catch (NumberFormatException localNumberFormatException)
/*     */         {
/*     */           continue;
/*     */         }
/* 290 */         str1 = str1 + " AND (" + paramString1 + " BETWEEN " + j + " AND " + k + ")";
/*     */       }
/*     */     }
/* 293 */     return str1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void getParameters(HttpServletRequest paramHttpServletRequest)
/*     */   {
/* 302 */     this.parameters = new Properties();
/*     */     
/*     */ 
/*     */ 
/* 306 */     Enumeration localEnumeration = paramHttpServletRequest.getParameterNames();
/* 307 */     Object localObject; String str1; while (localEnumeration.hasMoreElements())
/*     */     {
/* 309 */       localObject = (String)localEnumeration.nextElement();
/* 310 */       str1 = paramHttpServletRequest.getParameter((String)localObject);
/*     */       
/* 312 */       if (str1 == null) { str1 = "-";
/*     */       }
/* 314 */       this.parameters.put(localObject, str1);
/*     */     }
/* 316 */     if (this.paramTable != null)
/*     */     {
/* 318 */       localObject = this.paramTable.keys();
/* 319 */       while (((Enumeration)localObject).hasMoreElements())
/*     */       {
/* 321 */         str1 = (String)((Enumeration)localObject).nextElement();
/* 322 */         String str2 = (String)this.paramTable.get(str1);
/*     */         
/* 324 */         if (str2 == null) { str2 = "-";
/*     */         }
/* 326 */         this.parameters.put(str1, str2);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void setMaxCount()
/*     */   {
/* 336 */     String str = this.parameters.getProperty("MAX_COUNT");
/* 337 */     if (str != null) {
/* 338 */       try { this.MAX_COUNT = Integer.parseInt(str);
/*     */       } catch (Exception localException) {
/* 340 */         System.err.println("RmonTableServlet MAX_COUNT invalid in ssi: " + str + ": " + localException);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void errorPage(String paramString, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */   {
/* 351 */     this.out.println(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   public String propValue(String paramString, Properties paramProperties)
/*     */   {
/* 357 */     String str = paramProperties.getProperty(paramString);
/* 358 */     if (str == null) return "";
/* 359 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */   String replace(String paramString1, String paramString2, String paramString3)
/*     */   {
/* 365 */     if ((paramString1 == null) || (paramString1.indexOf(paramString2) == -1))
/* 366 */       return paramString1;
/* 367 */     int i = paramString1.indexOf(paramString2);
/* 368 */     String str = paramString1.substring(0, i);
/* 369 */     StringBuffer localStringBuffer = new StringBuffer(str);
/*     */     
/*     */ 
/* 372 */     if (str.lastIndexOf("<A") > str.lastIndexOf(">"))
/*     */     {
/* 374 */       if (paramString2.indexOf("OID") != -1)
/*     */       {
/* 376 */         localStringBuffer.append(URLEncoder.encode(paramString3));
/*     */       }
/*     */       else
/*     */       {
/* 380 */         localStringBuffer.append(paramString3.replace(' ', '+'));
/*     */       }
/*     */     } else {
/* 383 */       localStringBuffer.append(paramString3);
/*     */     }
/* 385 */     if (i + paramString2.length() < paramString1.length())
/* 386 */       localStringBuffer.append(paramString1.substring(i + paramString2.length()));
/* 387 */     if (paramString1.indexOf(paramString2) != -1)
/* 388 */       return replace(localStringBuffer.toString(), paramString2, paramString3);
/* 389 */     return localStringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\BaseDBServlet.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */