/*     */ package com.adventnet.nms.servlets;
/*     */ 
/*     */ import com.adventnet.management.log.LogUser;
/*     */ import com.adventnet.nms.jsp.GetImages;
/*     */ import com.adventnet.nms.store.relational.RelationalAPI;
/*     */ import com.adventnet.nms.util.NmsLogMgr;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import com.adventnet.nms.util.PureUtils;
/*     */ import java.awt.Image;
/*     */ import java.io.File;
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
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ 
/*     */ public class DBGraph extends BaseDBServlet
/*     */ {
/*  35 */   boolean average = false;
/*     */   
/*  37 */   String plotType = "PlotLine";
/*     */   
/*  39 */   String interval = "all";
/*     */   
/*  41 */   String graphTitle = "DB Graph"; String title = null;
/*  42 */   String queryString = "";
/*  43 */   Hashtable plotColsTable = null;
/*  44 */   private Image img = null;
/*  45 */   private boolean dataAvailable = false;
/*     */   
/*     */ 
/*     */   public synchronized void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/*  51 */     super.doGet(paramHttpServletRequest, paramHttpServletResponse);
/*  52 */     GetImages localGetImages = new GetImages();
/*  53 */     this.plotType = this.parameters.getProperty("PLOT_TYPE");
/*  54 */     String str1 = this.parameters.getProperty("TIME_AVERAGE");
/*  55 */     if ((str1 != null) && (str1.equals("true"))) { this.average = true;
/*     */     }
/*  57 */     String str2 = this.parameters.getProperty("PLOT_VARS");
/*  58 */     if (str2 == null) {
/*  59 */       errorPage("No columns to plot: ", paramHttpServletRequest, paramHttpServletResponse);
/*     */     }
/*     */     else {
/*  62 */       this.plotColsTable = new Hashtable();
/*  63 */       StringTokenizer localStringTokenizer = new StringTokenizer(str2);
/*  64 */       while (localStringTokenizer.hasMoreTokens()) {
/*  65 */         this.plotColsTable.put(localStringTokenizer.nextToken(), new Vector());
/*     */       }
/*     */     }
/*  68 */     int i = this.plotColsTable.size();
/*     */     
/*  70 */     String str3 = this.parameters.getProperty("tableColumns");
/*  71 */     if (str3 != null) {
/*  72 */       this.desiredCols = new Vector();
/*  73 */       localObject1 = new StringTokenizer(str3);
/*  74 */       while (((StringTokenizer)localObject1).hasMoreTokens()) { this.desiredCols.addElement(((StringTokenizer)localObject1).nextToken());
/*     */       }
/*     */     }
/*  77 */     str3 = this.parameters.getProperty("COLUMN-TYPE");
/*  78 */     this.columnType = new Vector();
/*  79 */     if (str3 != null) {
/*  80 */       localObject1 = new StringTokenizer(str3);
/*  81 */       while (((StringTokenizer)localObject1).hasMoreTokens()) { this.columnType.addElement(((StringTokenizer)localObject1).nextToken());
/*     */       }
/*     */     }
/*  84 */     Object localObject1 = BaseDBServlet.relapi.getConnection();
/*  85 */     Statement localStatement = null;
/*  86 */     ResultSet localResultSet = null;
/*     */     try
/*     */     {
/*  89 */       localStatement = ((Connection)localObject1).createStatement();
/*  90 */       int j = 0;
/*     */       
/*  92 */       if (this.table != null)
/*     */       {
/*  94 */         str4 = this.parameters.getProperty("tableDate");
/*  95 */         if (str4 != null) {
/*  96 */           if (str4.equals("TODAY")) {
/*  97 */             Date localDate = new Date();
/*  98 */             SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("M_d_yyyy");
/*     */             
/* 100 */             str4 = localSimpleDateFormat.format(localDate);
/*     */           }
/* 102 */           this.table += str4;
/*     */         }
/* 104 */         int k = this.sqlQuery.indexOf("STATSDATA");
/* 105 */         if (k == -1) {
/* 106 */           System.err.println("AvgReportServlet: no STATSDATA found in query string." + this.sqlQuery);
/*     */         } else {
/* 108 */           m = this.sqlQuery.indexOf(" ", k);
/* 109 */           this.sqlQuery = (this.sqlQuery.substring(0, k) + this.table + " " + this.sqlQuery.substring(m));
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 114 */       String str4 = getTagString();
/* 115 */       this.sqlQuery += str4;
/* 116 */       this.sqlQuery = (this.sqlQuery + " " + getOrderByString());
/*     */       
/* 118 */       System.err.println("finalquery is :: " + this.sqlQuery);
/*     */       
/* 120 */       localResultSet = localStatement.executeQuery(this.sqlQuery);
/* 121 */       ResultSetMetaData localResultSetMetaData = localResultSet.getMetaData();
/* 122 */       int m = localResultSetMetaData.getColumnCount();
/* 123 */       long[] arrayOfLong1 = new long[this.desiredCols.size()];
/* 124 */       for (int n = 0; n < this.desiredCols.size(); n++) arrayOfLong1[n] = -1L;
/* 125 */       long l1 = 300000L;
/* 126 */       while (localResultSet.next()) {
/* 127 */         for (int i1 = 0; i1 < this.desiredCols.size(); i1++)
/*     */         {
/* 129 */           if (this.plotColsTable.get(this.desiredCols.elementAt(i1)) != null) {
/*     */             long l2;
/*     */             Vector localVector2;
/* 132 */             if (this.columnType.elementAt(i1).equals("Counter")) {
/* 133 */               l2 = localResultSet.getLong(i1 + 1);
/*     */               
/* 135 */               localVector2 = (Vector)this.plotColsTable.get(this.desiredCols.elementAt(i1));
/*     */               
/* 137 */               if (localVector2 != null) { localVector2.addElement(new Long(l2));
/*     */               }
/*     */             }
/*     */             
/* 141 */             if (this.columnType.elementAt(i1).equals("Integer")) {
/* 142 */               l2 = localResultSet.getLong(i1 + 1);
/* 143 */               localVector2 = (Vector)this.plotColsTable.get(this.desiredCols.elementAt(i1));
/*     */               
/* 145 */               if (localVector2 != null) { localVector2.addElement(new Long(l2));
/*     */               }
/*     */             }
/* 148 */             if (this.columnType.elementAt(i1).equals("Time")) {
/* 149 */               l2 = localResultSet.getLong(i1 + 1);
/* 150 */               if (arrayOfLong1[i1] != -1L) l1 = l2 - arrayOfLong1[i1];
/* 151 */               localVector2 = (Vector)this.plotColsTable.get("TTIME");
/* 152 */               if (localVector2 != null) localVector2.addElement(new Long(l2));
/* 153 */               arrayOfLong1[i1] = l2;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 158 */         j++;
/* 159 */         if (j == this.MAX_COUNT) break;
/*     */       }
/* 161 */       if (j > 0) this.dataAvailable = true;
/* 162 */       localResultSet.close();
/* 163 */       localStatement.close();
/*     */       
/*     */ 
/* 166 */       Vector localVector1 = (Vector)this.plotColsTable.get("TTIME");
/* 167 */       if (localVector1 == null) errorPage("DB Graph: Time Column absent.", paramHttpServletRequest, paramHttpServletResponse);
/* 168 */       long[] arrayOfLong2 = new long[localVector1.size()];
/* 169 */       long[] arrayOfLong3 = new long[localVector1.size()];
/* 170 */       for (int i2 = 0; i2 < localVector1.size(); i2++)
/*     */       {
/* 172 */         arrayOfLong2[i2] = ((Long)localVector1.elementAt(i2)).longValue();
/*     */       }
/*     */       
/* 175 */       this.plotColsTable.remove("TTIME");
/*     */       
/* 177 */       this.title = this.graphTitle;
/*     */       
/* 179 */       int i3 = 0;
/* 180 */       long[][] arrayOfLong = new long[this.plotColsTable.size()][];
/* 181 */       int i4; for (Enumeration localEnumeration = this.plotColsTable.keys(); localEnumeration.hasMoreElements(); 
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 188 */           i4 < localVector1.size())
/*     */       {
/* 183 */         localObject2 = (String)localEnumeration.nextElement();
/* 184 */         this.title = (this.title + "  Line" + (i3 + 1) + ": " + (String)localObject2);
/* 185 */         localVector1 = (Vector)this.plotColsTable.get(localObject2);
/* 186 */         arrayOfLong[i3] = new long[localVector1.size()];
/*     */         
/* 188 */         i4 = 0; continue;
/*     */         
/*     */ 
/* 191 */         arrayOfLong3[i3] = ((Long)localVector1.elementAt(i4)).longValue();
/* 192 */         i3++;i4++;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 197 */       if (this.plotType.equals("PlotLine"))
/*     */       {
/* 199 */         this.img = localGetImages.getLineGraphImage(true, arrayOfLong2, arrayOfLong3, this.title);
/*     */       }
/*     */       else
/*     */       {
/* 203 */         this.img = localGetImages.getBarGraphImage(true, arrayOfLong2, arrayOfLong3, this.title);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 208 */       Object localObject2 = new Date();
/* 209 */       String str5 = "";
/* 210 */       if (paramHttpServletRequest.getQueryString() != null) {
/* 211 */         this.queryString = paramHttpServletRequest.getQueryString();
/*     */       }
/* 213 */       HttpSession localHttpSession = paramHttpServletRequest.getSession(false);
/* 214 */       String str6 = null;
/* 215 */       if (localHttpSession != null) {
/* 216 */         str6 = (String)localHttpSession.getAttribute("userName");
/*     */       }
/* 218 */       if (str6 == null) {
/* 219 */         str6 = paramHttpServletRequest.getParameter("userName");
/*     */       }
/* 221 */       if (str6 == null) {
/* 222 */         return;
/*     */       }
/*     */       
/* 225 */       StringBuffer localStringBuffer = new StringBuffer();
/* 226 */       localStringBuffer.append(PureUtils.usersDir + "users/");
/* 227 */       localStringBuffer.append(str6);
/* 228 */       localStringBuffer.append("/");
/* 229 */       localStringBuffer.append(String.valueOf(((Date)localObject2).getTime()));
/* 230 */       localStringBuffer.append(".jpg");
/* 231 */       str5 = localStringBuffer.toString();
/*     */       
/*     */ 
/* 234 */       Filt localFilt = new Filt();
/* 235 */       File localFile = new File(PureUtils.usersDir + "users/" + str6);
/* 236 */       File[] arrayOfFile = localFile.listFiles(localFilt);
/* 237 */       for (int i5 = 0; i5 < arrayOfFile.length; i5++) {
/* 238 */         arrayOfFile[i5].delete();
/*     */       }
/*     */       try
/*     */       {
/* 242 */         if (this.dataAvailable)
/*     */         {
/* 244 */           localGetImages.encodeImage(str5, this.img);
/*     */         }
/*     */         
/*     */ 
/*     */       }
/*     */       catch (Exception localException1)
/*     */       {
/* 251 */         NmsLogMgr.MISCERR.fail("Exception in PollGraphs " + localException1.getMessage(), localException1);
/* 252 */         this.out.println("Exception has occured in PollGraphs. Please refer logs for more details.");
/*     */         
/* 254 */         return;
/*     */       }
/*     */       
/* 257 */       this.out.println("<HTML><HEAD>");
/* 258 */       this.out.println("<TITLE>");
/* 259 */       this.out.println(NmsUtil.GetString("AdventNet"));
/* 260 */       this.out.println(" ");
/* 261 */       this.out.print(NmsUtil.GetString("WebNMS"));
/* 262 */       this.out.println(" Graphs</TITLE>");
/* 263 */       this.out.println("<LINK REL=STYLESHEET TYPE=\"text/css\" HREF=\"../template/nmshtmlui.css\">");
/* 264 */       this.out.println("<meta http-equiv=\"pragma\" content=\"no-cache\">");
/* 265 */       this.out.println("</HEAD><BODY>");
/*     */       
/* 267 */       this.out.println("<CENTER><TABLE BORDER=\"0\" CELLPADDING=\"0\" CELLSPACING=\"0\">");
/*     */       
/* 269 */       this.out.println("<TR><TD><FONT ID=\"cap\"><CENTER>" + this.title + "</CENTER></FONT></TD></TR></TABLE>");
/*     */       
/*     */ 
/*     */ 
/* 273 */       if (this.dataAvailable)
/*     */       {
/* 275 */         if (str5.indexOf(PureUtils.rootDir) != -1) {
/* 276 */           str5 = str5.substring(PureUtils.rootDir.length());
/*     */         }
/* 278 */         this.out.println("<IMG SRC=\"../" + str5 + " \" BORDER=\"0\" >");
/*     */       }
/*     */       else
/*     */       {
/* 282 */         this.out.println("<H3><CENTER>No data Available for the time interval " + this.interval + "<BR>for <FONT ID=\"cap\"><CENTER>" + this.title + "</CENTER></FONT></CENTER></H3>");
/*     */       }
/*     */       
/* 285 */       this.out.flush();
/*     */ 
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 290 */       System.err.println("Exception in executeQuery" + localSQLException + " for " + this.sqlQuery);
/* 291 */       errorPage("DB Graph could not get data: " + localSQLException, paramHttpServletRequest, paramHttpServletResponse);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try {
/* 296 */         if (localResultSet != null)
/* 297 */           localResultSet.close();
/* 298 */         if (localStatement != null) {
/* 299 */           localStatement.close();
/*     */         }
/*     */       }
/*     */       catch (Exception localException2) {}
/*     */     }
/*     */   }
/*     */   
/*     */   class Filt implements java.io.FilenameFilter
/*     */   {
/*     */     Filt() {}
/*     */     
/*     */     public boolean accept(File paramFile, String paramString)
/*     */     {
/* 312 */       if (paramString.endsWith(NmsUtil.getImageType())) {
/* 313 */         return true;
/*     */       }
/* 315 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\DBGraph.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */