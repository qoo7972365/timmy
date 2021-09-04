/*     */ package com.adventnet.nms.servlets;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class NetMonitorPluginServlet extends javax.servlet.http.HttpServlet
/*     */ {
/*  15 */   static Dimension dim = new Dimension(500, 350);
/*     */   
/*     */   public String getServletInfo()
/*     */   {
/*  19 */     return "This is the NetMonitorPlugin Servlet";
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
/*     */   public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws javax.servlet.ServletException, IOException
/*     */   {
/*  36 */     doGet(paramHttpServletRequest, paramHttpServletResponse);
/*     */   }
/*     */   
/*     */   public synchronized void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws javax.servlet.ServletException, IOException
/*     */   {
/*  42 */     Hashtable localHashtable = new Hashtable();
/*     */     
/*  44 */     Enumeration localEnumeration1 = paramHttpServletRequest.getParameterNames();
/*  45 */     String str1; while (localEnumeration1.hasMoreElements())
/*     */     {
/*  47 */       localObject1 = (String)localEnumeration1.nextElement();
/*  48 */       str1 = paramHttpServletRequest.getParameter((String)localObject1);
/*     */       
/*  50 */       if (str1 == null) { str1 = "-";
/*     */       }
/*  52 */       localHashtable.put(localObject1, str1);
/*     */     }
/*     */     
/*  55 */     this.props = new Properties();
/*  56 */     this.app_width = "800";
/*  57 */     this.app_height = "600";
/*  58 */     this.pre_applet = new StringBuffer();
/*  59 */     this.post_applet = new StringBuffer();
/*     */     
/*  61 */     log("called");
/*  62 */     paramHttpServletResponse.setStatus(200);
/*  63 */     paramHttpServletResponse.setContentType("text/html");
/*     */     
/*  65 */     Object localObject1 = paramHttpServletResponse.getWriter();
/*     */     
/*  67 */     if (localHashtable == null) { ((PrintWriter)localObject1).println(" Error getting Parameters.");
/*     */     }
/*     */     else
/*     */     {
/*  71 */       str1 = (String)localHashtable.get("nms_name");
/*     */       
/*  73 */       getAppletTags("NetMonitor" + System.getProperty("file.separator") + str1 + ".html");
/*     */       
/*  75 */       processLinks(this.pre_applet, paramHttpServletRequest);
/*  76 */       processLinks(this.post_applet, paramHttpServletRequest);
/*     */       
/*  78 */       ((PrintWriter)localObject1).println(this.pre_applet.toString());
/*     */       
/*  80 */       if (this.hasApplet)
/*     */       {
/*  82 */         if (!this.hasObjectTag)
/*     */         {
/*  84 */           ((PrintWriter)localObject1).println("<OBJECT classid=\"clsid:8AD9C840-044E-11D1-B3E9-00805F499D93\"");
/*  85 */           ((PrintWriter)localObject1).println(" width=" + this.app_width + " height=" + this.app_height);
/*  86 */           ((PrintWriter)localObject1).println("codebase=\"http://java.sun.com/products/plugin/1.1/ jinstall-11-win32.cab \">");
/*     */         }
/*  88 */         ((PrintWriter)localObject1).println("<param name=SAS_PORT_DIR value=\"../html/\">");
/*     */         
/*     */         String str2;
/*  91 */         for (Enumeration localEnumeration2 = localHashtable.keys(); localEnumeration2.hasMoreElements();)
/*     */         {
/*  93 */           localObject2 = (String)localEnumeration2.nextElement();
/*  94 */           localObject3 = (String)localHashtable.get(localObject2);
/*  95 */           if ((((String)localObject2).startsWith("\"")) && (((String)localObject2).length() > 1) && (((String)localObject2).endsWith("\"")))
/*     */           {
/*     */ 
/*     */ 
/*  99 */             str2 = ((String)localObject2).substring(1, ((String)localObject2).length() - 1);
/* 100 */             if (this.props.get(str2) != null) { this.props.remove(str2);
/*     */             }
/*     */             
/*     */ 
/*     */           }
/* 105 */           else if (this.props.get("\"" + (String)localObject2 + "\"") != null) {
/* 106 */             this.props.remove("\"" + (String)localObject2 + "\"");
/*     */           }
/* 108 */           this.props.put(localObject2, localObject3);
/*     */         }
/* 110 */         for (Object localObject2 = this.props.keys(); ((Enumeration)localObject2).hasMoreElements();)
/*     */         {
/* 112 */           localObject3 = (String)((Enumeration)localObject2).nextElement();
/* 113 */           str2 = (String)this.props.get(localObject3);
/* 114 */           ((PrintWriter)localObject1).println("<param name=" + (String)localObject3 + " value=" + str2 + ">");
/*     */         }
/* 116 */         ((PrintWriter)localObject1).println("<COMMENT><EMBED");
/* 117 */         ((PrintWriter)localObject1).println(" width=" + this.app_width + " height=" + this.app_height);
/* 118 */         ((PrintWriter)localObject1).println(" SAS_PORT_DIR=\"../html/\" ");
/* 119 */         for (Object localObject3 = this.props.keys(); ((Enumeration)localObject3).hasMoreElements();)
/*     */         {
/* 121 */           str2 = (String)((Enumeration)localObject3).nextElement();
/* 122 */           String str3 = (String)this.props.get(str2);
/* 123 */           if (str2.startsWith("\"")) str2 = str2.substring(1, str2.length() - 1);
/* 124 */           ((PrintWriter)localObject1).println(" " + str2 + "=" + str3);
/*     */         }
/* 126 */         ((PrintWriter)localObject1).print(">");
/*     */       }
/*     */       
/* 129 */       ((PrintWriter)localObject1).println(this.post_applet.toString());
/*     */     }
/*     */     
/* 132 */     ((PrintWriter)localObject1).flush();
/*     */   }
/*     */   
/* 135 */   Properties props = new Properties();
/* 136 */   String app_width = String.valueOf(dim.width);
/* 137 */   String app_height = String.valueOf(dim.height);
/*     */   
/*     */   String archivename;
/*     */   
/*     */   void setParameter(String paramString1, String paramString2)
/*     */   {
/* 143 */     if ((paramString1 == null) || (paramString2 == null)) { return;
/*     */     }
/* 145 */     if (paramString1.toLowerCase().equals("height")) { this.app_height = paramString2;
/* 146 */     } else if (paramString1.toLowerCase().equals("width")) { this.app_width = paramString2;
/*     */     }
/*     */     else {
/* 149 */       if (!paramString1.startsWith("\""))
/*     */       {
/* 151 */         paramString1 = "\"" + paramString1 + "\"";
/*     */       }
/* 153 */       this.props.put(paramString1, paramString2);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void getAppletTags(String paramString)
/*     */   {
/* 161 */     StringBuffer localStringBuffer = new StringBuffer();
/* 162 */     String str = null;
/* 163 */     this.filename = paramString;
/*     */     
/*     */     try
/*     */     {
/* 167 */       java.io.InputStream localInputStream = com.adventnet.nms.util.CommonUtil.openFile(new java.io.File(paramString));
/* 168 */       java.io.BufferedReader localBufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(localInputStream));
/* 169 */       while ((str = localBufferedReader.readLine()) != null) localStringBuffer.append(str + "\n");
/* 170 */       load(localStringBuffer.toString());
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 174 */       System.err.println("Reading applet file: " + paramString + ": " + localIOException);
/*     */     }
/*     */   }
/*     */   
/* 178 */   StringBuffer pre_applet = new StringBuffer();
/* 179 */   StringBuffer post_applet = new StringBuffer();
/*     */   
/* 181 */   boolean hasApplet = false;
/* 182 */   boolean hasObjectTag = false;
/*     */   
/*     */   void load(String paramString)
/*     */     throws IOException
/*     */   {
/* 187 */     StringBuffer localStringBuffer = this.pre_applet;
/* 188 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "<>=\r\n \t", true);
/*     */     
/* 190 */     int i = 0;
/* 191 */     while (localStringTokenizer.hasMoreTokens())
/*     */     {
/* 193 */       String str = localStringTokenizer.nextToken();
/* 194 */       if (i != 0)
/*     */       {
/* 196 */         if ((str.toLowerCase().trim().equals("param")) || (str.toLowerCase().trim().equals("embed")))
/*     */         {
/* 198 */           this.hasApplet = true;
/* 199 */           if (str.equalsIgnoreCase("param")) this.hasObjectTag = true;
/* 200 */           getApplet(localStringTokenizer, str);
/* 201 */           localStringBuffer = this.post_applet;
/* 202 */           if (localStringTokenizer.hasMoreTokens())
/*     */           {
/* 204 */             str = localStringTokenizer.nextToken();
/*     */           } else
/* 206 */             str = "";
/*     */         } else {
/* 208 */           localStringBuffer.append("<");
/*     */         } }
/* 210 */       if (str.equals("<"))
/*     */       {
/* 212 */         if (i == 0) { i = 1;
/*     */         }
/*     */         else {
/* 215 */           i = 0;
/* 216 */           localStringBuffer.append("<");
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 221 */         i = 0;
/* 222 */         localStringBuffer.append(str);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void getApplet(StringTokenizer paramStringTokenizer)
/*     */     throws IOException
/*     */   {
/* 230 */     String str1 = nextToken(paramStringTokenizer);
/* 231 */     String str2; String str3; while (!str1.equals(">"))
/*     */     {
/* 233 */       str2 = str1;str3 = param(paramStringTokenizer);
/* 234 */       setParameter(str2, str3);
/* 235 */       str1 = nextToken(paramStringTokenizer);
/*     */     }
/* 237 */     str1 = nextToken(paramStringTokenizer);
/*     */     
/* 239 */     while (!str1.trim().toLowerCase().equals("/applet"))
/*     */     {
/* 241 */       if (str1.trim().toLowerCase().equals("param"))
/*     */       {
/* 243 */         str2 = null;str3 = null;
/* 244 */         while (paramStringTokenizer.hasMoreTokens())
/*     */         {
/* 246 */           str1 = nextToken(paramStringTokenizer);
/* 247 */           if (str1.trim().toLowerCase().equals("name")) { str2 = param(paramStringTokenizer);
/* 248 */           } else { if (!str1.trim().toLowerCase().equals("value")) break; str3 = param(paramStringTokenizer);
/*     */           }
/*     */         }
/* 251 */         setParameter(str2, str3);
/*     */       }
/* 253 */       str1 = nextToken(paramStringTokenizer);
/*     */     }
/*     */     
/* 256 */     while (!str1.equals(">"))
/*     */     {
/* 258 */       str1 = nextToken(paramStringTokenizer); }
/*     */   }
/*     */   
/*     */   void getApplet(StringTokenizer paramStringTokenizer, String paramString) throws IOException {
/*     */     Object localObject1;
/*     */     Object localObject2;
/*     */     String str;
/* 265 */     if (paramString.equalsIgnoreCase("embed"))
/*     */     {
/* 267 */       localObject1 = nextToken(paramStringTokenizer);
/* 268 */       while (!((String)localObject1).equals(">"))
/*     */       {
/* 270 */         localObject2 = localObject1;str = param(paramStringTokenizer);
/* 271 */         setParameter((String)localObject2, str);
/* 272 */         localObject1 = nextToken(paramStringTokenizer);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 277 */       localObject1 = null;localObject2 = null;
/* 278 */       while (paramStringTokenizer.hasMoreTokens())
/*     */       {
/* 280 */         str = nextToken(paramStringTokenizer);
/* 281 */         if (str.trim().toLowerCase().equals("name")) { localObject1 = param(paramStringTokenizer);
/* 282 */         } else { if (!str.trim().toLowerCase().equals("value")) break; localObject2 = param(paramStringTokenizer);
/*     */         }
/*     */       }
/* 285 */       setParameter((String)localObject1, (String)localObject2);
/* 286 */       str = nextToken(paramStringTokenizer);
/* 287 */       while ((!str.trim().toLowerCase().equals("embed")) && (!str.trim().toLowerCase().equals("/object")))
/*     */       {
/* 289 */         if (str.trim().toLowerCase().equals("param"))
/*     */         {
/* 291 */           localObject1 = null;localObject2 = null;
/* 292 */           while (paramStringTokenizer.hasMoreTokens())
/*     */           {
/* 294 */             str = nextToken(paramStringTokenizer);
/* 295 */             if (str.trim().toLowerCase().equals("name")) { localObject1 = param(paramStringTokenizer);
/* 296 */             } else { if (!str.trim().toLowerCase().equals("value")) break; localObject2 = param(paramStringTokenizer);
/*     */             }
/*     */           }
/* 299 */           setParameter((String)localObject1, (String)localObject2);
/*     */         }
/* 301 */         str = nextToken(paramStringTokenizer);
/*     */       }
/*     */       
/* 304 */       if (str.equalsIgnoreCase("embed"))
/*     */       {
/* 306 */         str = nextToken(paramStringTokenizer);
/* 307 */         while (!str.equals(">"))
/*     */         {
/* 309 */           localObject1 = str;localObject2 = param(paramStringTokenizer);
/* 310 */           setParameter((String)localObject1, (String)localObject2);
/* 311 */           str = nextToken(paramStringTokenizer);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void processLinks(StringBuffer paramStringBuffer, HttpServletRequest paramHttpServletRequest)
/*     */   {
/* 320 */     String str1 = paramHttpServletRequest.getQueryString();
/* 321 */     if ((str1 == null) || (str1.trim().equals(""))) return;
/* 322 */     String str2 = paramStringBuffer.toString();
/* 323 */     str2 = com.adventnet.nms.util.GenericUtility.replace(str2, "$NMS_ARGS", "?" + str1);
/* 324 */     paramStringBuffer.setLength(0);
/* 325 */     paramStringBuffer.append(str2);
/*     */   }
/*     */   
/*     */   String param(StringTokenizer paramStringTokenizer)
/*     */     throws IOException
/*     */   {
/* 331 */     String str1 = nextToken(paramStringTokenizer);
/* 332 */     String str2 = "";
/* 333 */     if (!str1.equals("=")) error("Invalid parameter", paramStringTokenizer);
/* 334 */     str1 = nextToken(paramStringTokenizer);
/* 335 */     while ((!str1.equals(" ")) && (!str1.equals("\t")) && (!str1.equals("\n")) && (!str1.equals("\r")) && (!str1.equals("<")) && (!str1.equals(">")))
/*     */     {
/* 337 */       str2 = str2 + str1;
/* 338 */       str1 = myNextToken(paramStringTokenizer);
/*     */     }
/*     */     
/* 341 */     return str2;
/*     */   }
/*     */   
/*     */   String nextToken(StringTokenizer paramStringTokenizer) throws IOException
/*     */   {
/* 346 */     if (!paramStringTokenizer.hasMoreTokens()) error("Invalid data", paramStringTokenizer);
/* 347 */     String str = paramStringTokenizer.nextToken();
/* 348 */     if (str.trim().equals("")) return nextToken(paramStringTokenizer);
/* 349 */     return str;
/*     */   }
/*     */   
/*     */   String myNextToken(StringTokenizer paramStringTokenizer)
/*     */     throws IOException
/*     */   {
/* 355 */     if (!paramStringTokenizer.hasMoreTokens()) error("Invalid data", paramStringTokenizer);
/* 356 */     String str = paramStringTokenizer.nextToken();
/* 357 */     return str;
/*     */   }
/*     */   
/* 360 */   String filename = "";
/*     */   
/*     */   String error(String paramString, StringTokenizer paramStringTokenizer) throws IOException
/*     */   {
/* 364 */     throw new IOException("Error reading HTML: " + paramString + "\n" + "File: " + this.filename);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\NetMonitorPluginServlet.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */