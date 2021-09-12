/*     */ import com.adventnet.adaptors.html.HtmlAdaptor;
/*     */ import com.adventnet.nms.jsp.JspUtility;
/*     */ import com.adventnet.ssiparser.DynamicHtml;
/*     */ import com.adventnet.ssiparser.HtmlMain;
/*     */ import com.adventnet.ssiparser.HtmlParser;
/*     */ import com.adventnet.ssiparser.ParseException;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.management.ObjectName;
/*     */ import javax.servlet.GenericServlet;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Jmx
/*     */   extends HttpServlet
/*     */ {
/*     */   private HtmlMain main;
/*     */   private HtmlParser parser;
/*     */   private DynamicHtml dynamic;
/*     */   private boolean dynamicHtmlGen;
/*     */   private boolean getFromPost;
/*     */   private boolean newRowFlag;
/*     */   private boolean deleteRowFlag;
/*     */   private boolean nextRowFlag;
/*     */   private boolean prevRowFlag;
/*     */   
/*     */   public void init(ServletConfig paramServletConfig)
/*     */     throws ServletException
/*     */   {
/*  65 */     super.init(paramServletConfig);
/*     */     
/*  67 */     this.getFromPost = false;
/*  68 */     this.newRowFlag = false;
/*  69 */     this.dynamicHtmlGen = true;
/*     */     
/*  71 */     this.main = new HtmlMain();
/*  72 */     this.parser = this.main.getHtmlParser();
/*  73 */     this.dynamic = this.main.getDynamicHtml();
/*  74 */     this.dynamic.setLogoImageName(HtmlAdaptor.getParentDirectory() + "/ssi/logo.png");
/*  75 */     initCustomHtmlParameters();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initCustomHtmlParameters()
/*     */   {
/*  87 */     HtmlParser.setDirectoryName(HtmlAdaptor.getParentDirectory() + "/ssi/");
/*  88 */     HtmlParser.setIndexFileName("custom_index.html");
/*  89 */     this.parser.setServerName("JMX Agent for AGENT-SAMPLE-MIB");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initDynamicHtmlParameters()
/*     */   {
/*  99 */     this.dynamic.setAuthor("JMX Agent");
/* 100 */     this.dynamic.setIndexFileName("dynamic_index.html");
/* 101 */     this.dynamic.setHeaderSize(2);
/* 102 */     this.dynamic.setLogoImageName(HtmlAdaptor.getParentDirectory() + "/ssi/logo.png");
/* 103 */     this.dynamic.setLogoSize(72, 234);
/* 104 */     this.dynamic.setFooterMsg("<BR>&nbsp;Copyright (c) 1999-2000 AdventNet, Inc. All Rights Reserved. \n");
/* 105 */     this.dynamic.setBackGroundColor("#FFF0F0");
/* 106 */     this.dynamic.setBackGroundImage(null);
/* 107 */     this.dynamic.setBackTagLocation(DynamicHtml.TOP_RIGHT);
/*     */   }
/*     */   
/* 110 */   private Object searchAttrbValue = null;
/* 111 */   private boolean forSet = false;
/* 112 */   private boolean addRow = false;
/* 113 */   private boolean deleteRow = false;
/* 114 */   private boolean modifyRow = false;
/* 115 */   private String searchAttrb = null;
/* 116 */   private String searchAttrbName = null;
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
/* 131 */     paramHttpServletRequest.getSession(true);
/* 132 */     HttpSession localHttpSession = paramHttpServletRequest.getSession(true);
/* 133 */     String str1 = localHttpSession.getId();
/* 134 */     String str2 = null;
/*     */     try
/*     */     {
/* 137 */       str2 = "text/html;charset=" + JspUtility.getContentType((String)localHttpSession.getAttribute("Language"), (String)localHttpSession.getAttribute("Country"));
/*     */     }
/*     */     catch (Exception localException1)
/*     */     {
/* 141 */       str2 = "text/html";
/*     */     }
/*     */     
/* 144 */     paramHttpServletResponse.setContentType(str2);
/*     */     
/*     */ 
/*     */ 
/* 148 */     String str3 = paramHttpServletRequest.getParameter("arrayType");
/*     */     Enumeration localEnumeration;
/*     */     Object localObject1;
/* 151 */     Object localObject2; Object localObject3; if (str3 != null)
/*     */     {
/* 153 */       this.forSet = true;
/* 154 */       if (paramHttpServletRequest.getParameter("add") != null)
/*     */       {
/* 156 */         this.getFromPost = true;
/* 157 */         this.newRowFlag = true;
/* 158 */         this.addRow = true;
/*     */       }
/*     */       
/* 161 */       if (paramHttpServletRequest.getParameter("delete") != null)
/*     */       {
/* 163 */         this.getFromPost = true;
/* 164 */         this.newRowFlag = true;
/* 165 */         this.deleteRow = true;
/*     */       }
/*     */       
/* 168 */       if (paramHttpServletRequest.getParameter("modifyButton") != null)
/*     */       {
/* 170 */         this.modifyRow = true;
/*     */       }
/*     */       
/* 173 */       int i = 0;
/* 174 */       localEnumeration = paramHttpServletRequest.getParameterNames();
/* 175 */       localObject1 = null;
/* 176 */       localObject2 = new StringBuffer();
/* 177 */       localObject3 = null;
/* 178 */       String str4 = null;
/* 179 */       if (paramHttpServletRequest.getParameter("attrbName") != null) {
/*     */         try
/*     */         {
/* 182 */           localObject1 = new ObjectName(paramHttpServletRequest.getParameter("attrbName"));
/*     */         }
/*     */         catch (Exception localException2) {}
/*     */       }
/*     */       
/*     */ 
/* 188 */       while (localEnumeration.hasMoreElements())
/*     */       {
/* 190 */         localObject4 = localEnumeration.nextElement();
/* 191 */         localObject5 = localObject4.toString();
/*     */         
/*     */ 
/* 194 */         if (((String)localObject5).indexOf(",") > 0)
/*     */         {
/* 196 */           i += 1;
/* 197 */           StringTokenizer localStringTokenizer = new StringTokenizer((String)localObject5, ",");
/* 198 */           str4 = localStringTokenizer.nextToken(",");
/* 199 */           localObject3 = str4;
/*     */         }
/*     */       }
/*     */       
/* 203 */       Object localObject4 = new String[i];
/* 204 */       Object localObject5 = null;
/*     */       
/*     */ 
/* 207 */       for (int k = 0; k < i; k++)
/*     */       {
/* 209 */         localObject4[k] = paramHttpServletRequest.getParameter(str4 + "," + k);
/*     */       }
/*     */       
/* 212 */       this.searchAttrbValue = localObject4;
/*     */       
/*     */ 
/* 215 */       if (paramHttpServletRequest.getParameter("delete") != null)
/*     */       {
/* 217 */         if (!paramHttpServletRequest.getParameter("delete").equals("Delete"))
/*     */         {
/* 219 */           localObject5 = new String[i - 1];
/* 220 */           int m = 0;
/* 221 */           for (int n = 0; n < localObject4.length; n++)
/*     */           {
/*     */ 
/* 224 */             if (!localObject4[n].equals(paramHttpServletRequest.getParameter("delete")))
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/* 229 */               localObject5[m] = localObject4[n];
/* 230 */               m++;
/*     */             }
/*     */           }
/* 233 */           this.searchAttrbValue = localObject5;
/* 234 */           this.deleteRow = false;
/* 235 */           this.newRowFlag = false;
/* 236 */           this.forSet = true;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 241 */       if (paramHttpServletRequest.getParameter("modifyButton") != null)
/*     */       {
/* 243 */         if (paramHttpServletRequest.getParameter("modifyButton").equals("Modify") == true)
/*     */         {
/* 245 */           this.searchAttrbValue = localObject4;
/* 246 */           this.searchAttrb = paramHttpServletRequest.getParameter("arrayList");
/* 247 */           this.newRowFlag = false;
/* 248 */           this.forSet = true;
/*     */         }
/*     */       }
/*     */       
/* 252 */       doGet(paramHttpServletRequest, paramHttpServletResponse);
/*     */ 
/*     */     }
/* 255 */     else if (str3 == null)
/*     */     {
/*     */ 
/* 258 */       Hashtable localHashtable = new Hashtable();
/*     */       
/* 260 */       localEnumeration = paramHttpServletRequest.getParameterNames();
/*     */       
/* 262 */       while (localEnumeration.hasMoreElements())
/*     */       {
/* 264 */         localObject1 = localEnumeration.nextElement();
/* 265 */         localObject2 = paramHttpServletRequest.getParameter(localObject1.toString());
/*     */         
/* 267 */         localHashtable.put(localObject1, localObject2);
/*     */       }
/*     */       
/* 270 */       int j = this.main.setAttributeValue(localHashtable);
/* 271 */       if (j == HtmlMain.NEW_ROW)
/*     */       {
/* 273 */         this.newRowFlag = true;
/*     */       }
/*     */       
/* 276 */       if (j == HtmlMain.NEXT_ROW) {
/* 277 */         this.newRowFlag = true;
/* 278 */         this.nextRowFlag = true;
/*     */       }
/* 280 */       if (j == HtmlMain.PREV_ROW) {
/* 281 */         this.newRowFlag = true;
/* 282 */         this.prevRowFlag = true;
/*     */       }
/*     */       
/* 285 */       if (j == HtmlMain.DELETE_ROW)
/*     */       {
/* 287 */         this.newRowFlag = true;
/* 288 */         this.deleteRowFlag = true;
/*     */       }
/*     */       else {
/* 291 */         if (j == HtmlMain.POST_ERROR)
/*     */         {
/* 293 */           localObject2 = paramHttpServletResponse.getWriter();
/*     */           
/*     */ 
/* 296 */           localObject3 = this.main.createErrorHtmlPage(HtmlMain.FORBIDDEN);
/*     */           
/* 298 */           ((PrintWriter)localObject2).println((String)localObject3);
/* 299 */           ((PrintWriter)localObject2).flush();
/* 300 */           ((PrintWriter)localObject2).close();
/* 301 */           return;
/*     */         }
/* 303 */         if (j == HtmlMain.DO_OPER)
/*     */         {
/* 305 */           localObject2 = paramHttpServletResponse.getWriter();
/*     */           
/* 307 */           localObject3 = this.main.page;
/*     */           
/* 309 */           ((PrintWriter)localObject2).println((String)localObject3);
/* 310 */           ((PrintWriter)localObject2).flush();
/* 311 */           ((PrintWriter)localObject2).close();
/* 312 */           return;
/*     */         }
/*     */       }
/* 315 */       this.getFromPost = true;
/* 316 */       doGet(paramHttpServletRequest, paramHttpServletResponse);
/*     */     }
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
/*     */   public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/* 333 */     String str1 = null;
/* 334 */     paramHttpServletRequest.getSession(true);
/*     */     
/* 336 */     HttpSession localHttpSession = paramHttpServletRequest.getSession(true);
/* 337 */     String str2 = localHttpSession.getId();
/*     */     
/*     */ 
/* 340 */     String str3 = null;
/*     */     
/*     */     try
/*     */     {
/* 344 */       str3 = "text/html;charset=" + JspUtility.getContentType((String)localHttpSession.getAttribute("Language"), (String)localHttpSession.getAttribute("Country"));
/*     */     }
/*     */     catch (Exception localException1)
/*     */     {
/* 348 */       str3 = "text/html";
/*     */     }
/* 350 */     paramHttpServletResponse.setContentType(str3);
/*     */     
/*     */ 
/* 353 */     this.main.setClientInfo(str2);
/*     */     
/* 355 */     PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
/* 356 */     Object localObject = paramHttpServletRequest.getParameter("fname");
/*     */     
/* 358 */     if (localObject == null)
/*     */     {
/*     */       try
/*     */       {
/* 362 */         localPrintWriter.println(this.parser.parse("ssi" + File.separator + "index.html"));
/*     */       }
/*     */       catch (ParseException localParseException1)
/*     */       {
/* 366 */         localParseException1.printStackTrace();
/*     */         
/* 368 */         str1 = this.main.createErrorHtmlPage(HtmlMain.NOT_FOUND);
/* 369 */         localPrintWriter.println(str1);
/* 370 */         localPrintWriter.flush();
/* 371 */         localPrintWriter.close();
/* 372 */         return;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 377 */       this.dynamicHtmlGen = this.main.getMode(str2);
/*     */       
/* 379 */       String str4 = paramHttpServletRequest.getParameter("timeValue");
/*     */       
/* 381 */       if (str4 != null) {
/*     */         try
/*     */         {
/* 384 */           int i = Integer.parseInt(str4);
/* 385 */           this.main.setRefreshTime(str2, i);
/*     */         }
/*     */         catch (NumberFormatException localNumberFormatException) {
/* 388 */           localNumberFormatException.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/*     */       String str8;
/* 393 */       if (((String)localObject).equals("admin.html"))
/*     */       {
/* 395 */         str5 = paramHttpServletRequest.getParameter("domain");
/* 396 */         String str6 = paramHttpServletRequest.getParameter("keys");
/* 397 */         str8 = paramHttpServletRequest.getParameter("className");
/* 398 */         String str9 = paramHttpServletRequest.getParameter("classLoader");
/* 399 */         String str10 = paramHttpServletRequest.getParameter("commandType");
/* 400 */         String str11 = paramHttpServletRequest.getParameter("cancel");
/* 401 */         String str12 = paramHttpServletRequest.getParameter("xmlName");
/*     */         
/*     */ 
/* 404 */         if (str11 != null)
/*     */         {
/* 406 */           if (this.dynamicHtmlGen) {
/* 407 */             localObject = this.dynamic.getIndexFileName();
/*     */           } else {
/* 409 */             localObject = this.parser.getIndexFileName();
/*     */           }
/*     */           
/*     */         }
/* 413 */         else if ((str5 != null) && (str6 != null))
/*     */         {
/* 415 */           if (str10.equals("Register")) {
/* 416 */             this.main.registerMBean(str5, str6, str8, str9, str12);
/*     */           } else {
/* 418 */             this.main.unregisterMBean(str5, str6);
/*     */           }
/* 420 */           if (this.dynamicHtmlGen) {
/* 421 */             localObject = this.dynamic.getIndexFileName();
/*     */           } else {
/* 423 */             localObject = this.parser.getIndexFileName();
/*     */           }
/*     */         }
/*     */         
/* 427 */         if ((!this.dynamicHtmlGen) || (((String)localObject).equals("admin.html")))
/*     */         {
/*     */           try
/*     */           {
/* 431 */             str1 = this.parser.parse(this.parser.getDirectoryName() + File.separator + (String)localObject);
/* 432 */             localPrintWriter.println(str1);
/*     */           }
/*     */           catch (ParseException localParseException3)
/*     */           {
/* 436 */             localPrintWriter.println("Error 2 : Unable to parse the file.");
/*     */           }
/*     */           
/* 439 */           localPrintWriter.flush();
/* 440 */           localPrintWriter.close();
/* 441 */           return;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 448 */       if ((this.dynamicHtmlGen) && (((String)localObject).equals("filter.html")))
/*     */       {
/* 450 */         str5 = paramHttpServletRequest.getParameter("filter");
/*     */         
/* 452 */         if (str5 != null)
/*     */         {
/* 454 */           this.dynamicHtmlGen = true;
/* 455 */           this.main.setMode(str2, this.dynamicHtmlGen);
/* 456 */           this.main.setFilter(str2, str5);
/* 457 */           str1 = this.dynamic.createDynamicIndexFile(str5, null);
/*     */           
/* 459 */           localPrintWriter.println(str1);
/* 460 */           localPrintWriter.flush();
/* 461 */           localPrintWriter.close();
/* 462 */           return;
/*     */         }
/*     */       }
/* 465 */       if ((this.dynamicHtmlGen) && (((String)localObject).equals("notif.html")))
/*     */       {
/* 467 */         this.dynamicHtmlGen = true;
/* 468 */         str1 = this.dynamic.createNotificationTable();
/*     */         
/* 470 */         localPrintWriter.println(str1);
/* 471 */         localPrintWriter.flush();
/* 472 */         localPrintWriter.close();
/* 473 */         return;
/*     */       }
/*     */       
/* 476 */       String str5 = null;
/* 477 */       str5 = paramHttpServletRequest.getParameter("attrbName");
/*     */       
/* 479 */       if (this.getFromPost)
/*     */       {
/* 481 */         this.getFromPost = false;
/*     */         
/*     */ 
/* 484 */         if (this.newRowFlag)
/*     */         {
/*     */ 
/* 487 */           if (this.addRow)
/*     */           {
/* 489 */             this.searchAttrbName = paramHttpServletRequest.getParameter("arrayList");
/* 490 */             str1 = this.main.createNewRow(str5, (String)localObject, paramHttpServletRequest.getParameter("arrayList"), "addRow");
/* 491 */             this.addRow = false;
/*     */ 
/*     */ 
/*     */           }
/* 495 */           else if (this.deleteRow)
/*     */           {
/* 497 */             this.searchAttrbName = paramHttpServletRequest.getParameter("arrayList");
/* 498 */             str1 = this.main.createNewRow(str5, (String)localObject, paramHttpServletRequest.getParameter("arrayList"), "deleteRow");
/* 499 */             this.deleteRow = false;
/*     */ 
/*     */           }
/* 502 */           else if (this.deleteRowFlag)
/*     */           {
/* 504 */             str1 = this.main.createNewRow(str5, (String)localObject, null, "deleteRowTable");
/* 505 */             this.deleteRowFlag = false;
/*     */ 
/*     */ 
/*     */           }
/* 509 */           else if (this.nextRowFlag)
/*     */           {
/*     */             try {
/* 512 */               this.main.setStartIndex(Integer.parseInt(paramHttpServletRequest.getParameter("sIndex")));
/* 513 */               this.main.setEndIndex(Integer.parseInt(paramHttpServletRequest.getParameter("eIndex")));
/*     */             } catch (Exception localException2) {
/* 515 */               localException2.printStackTrace();
/*     */             }
/* 517 */             str1 = this.main.createHtmlPage(str5, null, null, null);
/* 518 */             this.nextRowFlag = false;
/*     */           }
/* 520 */           else if (this.prevRowFlag)
/*     */           {
/*     */             try {
/* 523 */               this.main.setStartIndex(Integer.parseInt(paramHttpServletRequest.getParameter("sIndex")));
/* 524 */               this.main.setEndIndex(Integer.parseInt(paramHttpServletRequest.getParameter("eIndex")));
/*     */             } catch (Exception localException3) {
/* 526 */               localException3.printStackTrace();
/*     */             }
/* 528 */             str1 = this.main.createHtmlPage(str5, null, null, null);
/* 529 */             this.prevRowFlag = false;
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/*     */ 
/* 535 */             str1 = this.main.createNewRow(str5, (String)localObject, null, "");
/*     */           }
/*     */           
/* 538 */           this.newRowFlag = false;
/*     */           
/* 540 */           if (str1 != null) {
/* 541 */             localPrintWriter.println(str1);
/*     */           }
/* 543 */           localPrintWriter.flush();
/* 544 */           localPrintWriter.close();
/* 545 */           return;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 551 */       if ((((String)localObject).equals(this.dynamic.getIndexFileName())) && (str5 == null))
/*     */       {
/* 553 */         this.dynamicHtmlGen = true;
/* 554 */         this.main.setMode(str2, this.dynamicHtmlGen);
/* 555 */         str1 = this.dynamic.createDynamicIndexFile(this.main.getFilter(), null);
/*     */         
/* 557 */         localPrintWriter.println(str1);
/* 558 */         localPrintWriter.flush();
/* 559 */         localPrintWriter.close();
/* 560 */         return;
/*     */       }
/* 562 */       if (((String)localObject).equals(this.parser.getIndexFileName()))
/*     */       {
/* 564 */         this.dynamicHtmlGen = false;
/* 565 */         this.main.setMode(str2, this.dynamicHtmlGen);
/*     */       }
/*     */       String str7;
/* 568 */       if (this.dynamicHtmlGen)
/*     */       {
/* 570 */         str7 = paramHttpServletRequest.getParameter("attrbArrayList");
/*     */         
/* 572 */         if (paramHttpServletRequest.getParameter("attrbName") != null)
/*     */         {
/* 574 */           localObject = paramHttpServletRequest.getParameter("attrbName");
/* 575 */           if (this.forSet == true)
/*     */           {
/*     */ 
/* 578 */             if (this.modifyRow)
/*     */             {
/*     */ 
/* 581 */               str1 = this.main.createHtmlPage((String)localObject, this.searchAttrb, this.searchAttrbValue, this.searchAttrbName);
/*     */             }
/*     */             else
/*     */             {
/* 585 */               str1 = this.main.createHtmlPage((String)localObject, null, this.searchAttrbValue, this.searchAttrbName);
/*     */             }
/*     */             
/* 588 */             str1 = this.main.createHtmlPage((String)localObject, null, null, null);
/*     */             
/* 590 */             this.forSet = false;
/* 591 */             this.searchAttrbName = null;
/* 592 */             this.searchAttrb = null;
/*     */           }
/*     */           else
/*     */           {
/* 596 */             str1 = this.main.createHtmlPage((String)localObject, null, null, null);
/*     */           }
/*     */         }
/* 599 */         else if (paramHttpServletRequest.getParameter("attrbArrayList") != null)
/*     */         {
/*     */ 
/* 602 */           str1 = this.main.createHtmlPage((String)localObject, str7, null, null);
/*     */         }
/*     */         else
/*     */         {
/* 606 */           str1 = this.main.createHtmlPage((String)localObject, null, null, null);
/*     */         }
/*     */         
/* 609 */         if (str1 != null) {
/* 610 */           localPrintWriter.println(str1);
/*     */         }
/* 612 */         localPrintWriter.flush();
/* 613 */         localPrintWriter.close();
/* 614 */         return;
/*     */       }
/*     */       
/*     */ 
/*     */       try
/*     */       {
/* 620 */         str7 = paramHttpServletRequest.getParameter("shtml");
/*     */         
/* 622 */         if (str7 != null) {
/* 623 */           localObject = str7;
/*     */         }
/* 625 */         str8 = this.parser.parse(this.parser.getDirectoryName() + File.separator + (String)localObject);
/* 626 */         localPrintWriter.println(str8);
/*     */       }
/*     */       catch (ParseException localParseException2)
/*     */       {
/* 630 */         str1 = this.main.createErrorHtmlPage(HtmlMain.NOT_FOUND);
/* 631 */         localPrintWriter.println(str1);
/*     */       }
/*     */     }
/*     */     
/* 635 */     localPrintWriter.flush();
/* 636 */     localPrintWriter.close();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getServletInfo()
/*     */   {
/* 648 */     return "Jmx Servlet";
/*     */   }
/*     */   
/*     */   public void log(String paramString) {}
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\Jmx.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */