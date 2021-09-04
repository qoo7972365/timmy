/*     */ package com.adventnet.nms.servlets;
/*     */ 
/*     */ import com.adventnet.management.log.LogUser;
/*     */ import com.adventnet.nms.jsp.MibBrowserMain;
/*     */ import com.adventnet.nms.util.NmsLogMgr;
/*     */ import com.adventnet.snmp.beans.SnmpTarget;
/*     */ import com.adventnet.snmp.mibs.MibModule;
/*     */ import com.adventnet.snmp.mibs.MibNode;
/*     */ import com.adventnet.snmp.mibs.MibOperations;
/*     */ import com.adventnet.snmp.snmp2.SnmpOID;
/*     */ import com.adventnet.snmp.snmp2.SnmpVarBind;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MibTableServlet
/*     */   extends AuthenticationServlet
/*     */ {
/*     */   Properties parameters;
/*     */   Hashtable paramTable;
/*     */   
/*     */   public void init(ServletConfig paramServletConfig)
/*     */     throws ServletException
/*     */   {
/*  42 */     super.init(paramServletConfig);
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
/*  60 */     doGet(paramHttpServletRequest, paramHttpServletResponse);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/*  69 */     this.parameters = new Properties();
/*     */     
/*     */ 
/*  72 */     PrintWriter localPrintWriter = new PrintWriter(paramHttpServletResponse.getOutputStream());
/*  73 */     this.paramTable = ((Hashtable)paramHttpServletRequest.getAttribute("parameters"));
/*     */     
/*  75 */     Enumeration localEnumeration1 = paramHttpServletRequest.getParameterNames();
/*  76 */     while (localEnumeration1.hasMoreElements())
/*     */     {
/*  78 */       localObject1 = (String)localEnumeration1.nextElement();
/*  79 */       str1 = paramHttpServletRequest.getParameter((String)localObject1);
/*     */       
/*  81 */       if (str1 == null) { str1 = "-";
/*     */       }
/*  83 */       this.parameters.put(localObject1, str1);
/*     */     }
/*     */     
/*  86 */     if (this.paramTable != null)
/*     */     {
/*  88 */       localObject1 = this.paramTable.keys();
/*  89 */       while (((Enumeration)localObject1).hasMoreElements())
/*     */       {
/*  91 */         str1 = (String)((Enumeration)localObject1).nextElement();
/*  92 */         localObject2 = (String)this.paramTable.get(str1);
/*     */         
/*  94 */         if (localObject2 == null) localObject2 = "-";
/*  95 */         this.parameters.put(str1, localObject2);
/*     */       }
/*     */     }
/*  98 */     Object localObject1 = this.parameters.getProperty("tableHeader");
/*     */     
/* 100 */     String str1 = this.parameters.getProperty("template");
/*     */     
/* 102 */     if (!initSnmp(this.parameters, localPrintWriter)) return;
/*     */     Enumeration localEnumeration2;
/* 104 */     if (str1 == null)
/*     */     {
/* 106 */       localObject2 = new StringBuffer();
/* 107 */       ((StringBuffer)localObject2).append("<TR BGCOLOR=#add8e6>");
/* 108 */       for (localEnumeration2 = this.desiredCols.elements(); localEnumeration2.hasMoreElements();)
/* 109 */         ((StringBuffer)localObject2).append("<TD>#" + localEnumeration2.nextElement() + "#</TD>");
/* 110 */       ((StringBuffer)localObject2).append("</TR>");
/* 111 */       str1 = ((StringBuffer)localObject2).toString();
/*     */     }
/*     */     
/* 114 */     if (localObject1 == null)
/*     */     {
/* 116 */       localObject2 = new StringBuffer();
/* 117 */       ((StringBuffer)localObject2).append("<TABLE WIDTH=300 BORDER=2 CELLPADDING=0 CELLSPACING=0><TR BGCOLOR=#4169e1>");
/* 118 */       for (localEnumeration2 = this.desiredCols.elements(); localEnumeration2.hasMoreElements();)
/* 119 */         ((StringBuffer)localObject2).append("<TD>" + localEnumeration2.nextElement() + "</TD>");
/* 120 */       ((StringBuffer)localObject2).append("</TR>");
/* 121 */       localObject1 = ((StringBuffer)localObject2).toString();
/*     */     }
/*     */     
/*     */ 
/* 125 */     localPrintWriter.println((String)localObject1);
/*     */     
/*     */ 
/* 128 */     Object localObject2 = this.target.getSnmpOIDList()[0];
/* 129 */     int i = 0;
/* 130 */     while (i++ < 10000)
/*     */     {
/*     */ 
/* 133 */       SnmpVarBind[] arrayOfSnmpVarBind = this.target.snmpGetNextVariableBindings();
/* 134 */       if ((arrayOfSnmpVarBind == null) || 
/* 135 */         (!SnmpTarget.isInSubTree((SnmpOID)localObject2, this.target.getSnmpOIDList()[0]))) {
/*     */         break;
/*     */       }
/* 138 */       String str2 = str1;
/* 139 */       for (int j = 0; j < arrayOfSnmpVarBind.length; j++)
/*     */       {
/*     */ 
/* 142 */         str2 = replace(str2, "#" + this.desiredCols.elementAt(j) + "#", this.mibOps.toString(arrayOfSnmpVarBind[j].getVariable(), arrayOfSnmpVarBind[j].getObjectID()));
/*     */       }
/*     */       
/*     */ 
/* 146 */       localPrintWriter.println(str2);
/* 147 */       localPrintWriter.flush();
/*     */     }
/*     */     
/* 150 */     localPrintWriter.println("</TABLE>");
/* 151 */     localPrintWriter.flush();
/*     */     
/* 153 */     if (i == 1)
/*     */     {
/*     */ 
/* 156 */       localPrintWriter.println("<p>");
/* 157 */       localPrintWriter.println("Request failed, timed out or no available data. \n" + this.target.getErrorString());
/*     */       
/* 159 */       localPrintWriter.flush();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void sendErrorMessage(String paramString, PrintWriter paramPrintWriter)
/*     */   {
/*     */     try
/*     */     {
/* 169 */       paramPrintWriter.println(paramString);
/* 170 */       paramPrintWriter.println("</TD>");
/* 171 */       paramPrintWriter.println("</TABLE>");
/* 172 */       paramPrintWriter.flush();
/* 173 */       paramPrintWriter.close();
/* 174 */       return;
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/* 178 */       NmsLogMgr.MISCERR.fail("Exception in MibTableServlet : " + localException, localException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   boolean getBoolean(String paramString)
/*     */   {
/* 186 */     if ((paramString != null) && (paramString.equalsIgnoreCase("true"))) {
/* 187 */       return true;
/*     */     }
/* 189 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 194 */   SnmpTarget target = new SnmpTarget();
/*     */   
/*     */   MibOperations mibOps;
/*     */   static MibModule mod;
/* 198 */   Vector desiredCols = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean initSnmp(Properties paramProperties, PrintWriter paramPrintWriter)
/*     */   {
/* 205 */     String str1 = paramProperties.getProperty("version");
/* 206 */     if (str1 != null)
/*     */     {
/*     */ 
/* 209 */       if (str1.equals("v2")) {
/* 210 */         this.target.setSnmpVersion(1);
/* 211 */       } else if (str1.equals("v1")) {
/* 212 */         this.target.setSnmpVersion(0);
/*     */       }
/*     */       else {
/* 215 */         System.out.println("Invalid Version Number in MibtableServlet");
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 221 */     String str2 = paramProperties.getProperty("HOST");
/* 222 */     if (str2 == null)
/*     */     {
/* 224 */       sendErrorMessage("Parameter HOST missing.", paramPrintWriter);
/* 225 */       return false;
/*     */     }
/*     */     
/* 228 */     this.target.setTargetHost(str2);
/*     */     
/*     */ 
/* 231 */     String str3 = paramProperties.getProperty("COMMUNITY");
/* 232 */     if (str3 != null) {
/* 233 */       this.target.setCommunity(str3);
/*     */     }
/*     */     
/* 236 */     String str4 = paramProperties.getProperty("PORT");
/* 237 */     String str5 = paramProperties.getProperty("RETRIES");
/* 238 */     String str6 = paramProperties.getProperty("TIMEOUT");
/*     */     
/*     */     try
/*     */     {
/* 242 */       if (str4 != null)
/* 243 */         this.target.setTargetPort(Integer.parseInt(str4));
/* 244 */       if (str5 != null)
/* 245 */         this.target.setRetries(Integer.parseInt(str5));
/* 246 */       if (str6 != null) {
/* 247 */         this.target.setTimeout(Integer.parseInt(str6));
/*     */       }
/*     */     }
/*     */     catch (NumberFormatException localNumberFormatException) {
/* 251 */       NmsLogMgr.MISCERR.fail("Invalid Integer Argument in MibTableServlet " + localNumberFormatException, localNumberFormatException);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 256 */     String str7 = this.parameters.getProperty("mibsToLoad");
/* 257 */     String str8 = this.parameters.getProperty("keyFortable");
/*     */     
/*     */ 
/*     */ 
/* 261 */     if (str7 != null) {
/*     */       try {
/* 263 */         mod = MibBrowserMain.loadMib(str7, str8);
/*     */       } catch (Exception localException) {
/* 265 */         NmsLogMgr.MISCERR.fail("MibTableServlet error loading mibs: " + str7 + " : " + localException, localException);
/*     */       }
/*     */     }
/*     */     
/* 269 */     this.desiredCols = null;
/* 270 */     String str9 = this.parameters.getProperty("mibColumns");
/* 271 */     if (str9 != null)
/*     */     {
/* 273 */       this.desiredCols = new Vector();
/* 274 */       localObject1 = new StringTokenizer(str9);
/* 275 */       while (((StringTokenizer)localObject1).hasMoreTokens()) this.desiredCols.addElement(((StringTokenizer)localObject1).nextToken());
/*     */     }
/* 277 */     Object localObject1 = this.parameters.getProperty("mibTableName");
/* 278 */     if (localObject1 == null)
/*     */     {
/* 280 */       sendErrorMessage("Parameter mibTableName missing.", paramPrintWriter);
/* 281 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 285 */     this.mibOps = MibBrowserMain.getMibOperations();
/* 286 */     SnmpOID localSnmpOID = this.mibOps.getSnmpOID((String)localObject1);
/*     */     
/* 288 */     if (localSnmpOID == null)
/*     */     {
/*     */ 
/* 291 */       this.mibOps = this.target.getMibOperations();
/* 292 */       localSnmpOID = this.mibOps.getSnmpOID((String)localObject1);
/*     */     }
/*     */     
/* 295 */     MibNode localMibNode1 = this.mibOps.getMibNode(localSnmpOID);
/*     */     
/* 297 */     if (localMibNode1 == null)
/*     */     {
/*     */ 
/* 300 */       this.mibOps = this.target.getMibOperations();
/* 301 */       localSnmpOID = this.mibOps.getSnmpOID((String)localObject1);
/* 302 */       localMibNode1 = this.mibOps.getMibNode(localSnmpOID);
/*     */     }
/*     */     
/*     */ 
/* 306 */     if (localMibNode1 == null)
/*     */     {
/*     */ 
/* 309 */       sendErrorMessage("Cannot find MIB node for table.  Correct MIB must be loaded: " + (String)localObject1, paramPrintWriter);
/* 310 */       return false;
/*     */     }
/*     */     
/* 313 */     Vector localVector = localMibNode1.getTableItems();
/* 314 */     if ((localVector == null) || (localVector.size() == 0))
/*     */     {
/*     */ 
/* 317 */       localObject2 = localMibNode1.getParent();
/* 318 */       localVector = ((MibNode)localObject2).getTableItems();
/* 319 */       if ((localVector == null) || (localVector.size() == 0))
/*     */       {
/* 321 */         sendErrorMessage("Not a table.  No columns: " + (String)localObject1, paramPrintWriter);
/* 322 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 326 */     if (this.desiredCols != null)
/*     */     {
/* 328 */       for (localObject2 = this.desiredCols.elements(); ((Enumeration)localObject2).hasMoreElements();)
/* 329 */         if (!localVector.contains(((Enumeration)localObject2).nextElement()))
/*     */         {
/* 331 */           sendErrorMessage("Improper mibColumns values: " + str9, paramPrintWriter);
/* 332 */           return false;
/*     */         }
/* 334 */       localVector = this.desiredCols;
/*     */     } else {
/* 336 */       this.desiredCols = localVector;
/*     */     }
/*     */     
/* 339 */     while (localVector.size() > 0)
/*     */     {
/* 341 */       localObject2 = this.mibOps.getSnmpOID((String)localVector.elementAt(0));
/* 342 */       MibNode localMibNode2 = this.mibOps.getMibNode((SnmpOID)localObject2);
/* 343 */       if ((localMibNode2.getAccess() == 43690) || (localMibNode2.getAccess() == 43706)) {
/*     */         break;
/*     */       }
/*     */       
/* 347 */       localVector.removeElementAt(0);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 353 */     Object localObject2 = new SnmpOID[localVector.size()];
/* 354 */     for (int i = 0; i < localObject2.length; i++) {
/* 355 */       localObject2[i] = this.mibOps.getSnmpOID((String)localVector.elementAt(i));
/*     */     }
/* 357 */     this.target.setSnmpOIDList((SnmpOID[])localObject2);
/*     */     
/* 359 */     return true;
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
/* 373 */       localStringBuffer.append(paramString3.replace(' ', '+')); else {
/* 374 */       localStringBuffer.append(paramString3);
/*     */     }
/* 376 */     if (i + paramString2.length() < paramString1.length())
/* 377 */       localStringBuffer.append(paramString1.substring(i + paramString2.length()));
/* 378 */     if (paramString1.indexOf(paramString2) != -1)
/* 379 */       return replace(localStringBuffer.toString(), paramString2, paramString3);
/* 380 */     return localStringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\MibTableServlet.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */