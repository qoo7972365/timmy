/*     */ package com.adventnet.nms.servlets;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class NetMonitorServlet extends javax.servlet.http.HttpServlet
/*     */ {
/*  15 */   static Dimension dim = new Dimension(500, 350);
/*     */   
/*     */   public String getServletInfo()
/*     */   {
/*  19 */     return "This servlet lists all the details of a Topology database object";
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
/*  42 */     paramHttpServletResponse.setStatus(200);
/*  43 */     paramHttpServletResponse.setContentType("text/html");
/*     */     
/*  45 */     Hashtable localHashtable = new Hashtable();
/*     */     
/*  47 */     Enumeration localEnumeration1 = paramHttpServletRequest.getParameterNames();
/*  48 */     String str1; while (localEnumeration1.hasMoreElements())
/*     */     {
/*  50 */       localObject1 = (String)localEnumeration1.nextElement();
/*  51 */       str1 = paramHttpServletRequest.getParameter((String)localObject1);
/*     */       
/*  53 */       if (str1 == null) { str1 = "-";
/*     */       }
/*  55 */       localHashtable.put(localObject1, str1);
/*     */     }
/*     */     
/*  58 */     this.props = new java.util.Properties();
/*  59 */     this.app_width = "800";
/*  60 */     this.app_height = "600";
/*  61 */     this.pre_applet = new StringBuffer();
/*  62 */     this.post_applet = new StringBuffer();
/*     */     
/*  64 */     paramHttpServletResponse.setStatus(200);
/*  65 */     paramHttpServletResponse.setContentType("text/html");
/*  66 */     Object localObject1 = paramHttpServletResponse.getWriter();
/*     */     
/*  68 */     if (localHashtable == null) { ((PrintWriter)localObject1).println(" Error getting Parameters.");
/*     */     }
/*     */     else {
/*  71 */       str1 = (String)localHashtable.get("nms_name");
/*     */       
/*  73 */       this.archivename = ("/NetMonitor/build/" + str1 + ".zip");
/*     */       
/*  75 */       getAppletTags("NetMonitor/" + str1 + ".html");
/*     */       
/*  77 */       processLinks(this.pre_applet, paramHttpServletRequest);
/*  78 */       processLinks(this.post_applet, paramHttpServletRequest);
/*     */       
/*  80 */       ((PrintWriter)localObject1).println(this.pre_applet.toString());
/*     */       
/*  82 */       if (this.hasApplet)
/*     */       {
/*  84 */         ((PrintWriter)localObject1).println("<applet  ARCHIVE=" + this.archivename);
/*  85 */         ((PrintWriter)localObject1).println("code=" + str1 + ".class");
/*  86 */         ((PrintWriter)localObject1).println(" width=" + this.app_width + " height=" + this.app_height + ">");
/*  87 */         ((PrintWriter)localObject1).println("<param name=SAS_PORT_DIR value=\"..\">");
/*     */         String str2;
/*     */         String str3;
/*  90 */         for (Enumeration localEnumeration2 = localHashtable.keys(); localEnumeration2.hasMoreElements();)
/*     */         {
/*  92 */           localObject2 = (String)localEnumeration2.nextElement();
/*  93 */           str2 = (String)localHashtable.get(localObject2);
/*  94 */           if ((((String)localObject2).startsWith("\"")) && (((String)localObject2).length() > 1) && (((String)localObject2).endsWith("\"")))
/*     */           {
/*     */ 
/*     */ 
/*  98 */             str3 = ((String)localObject2).substring(1, ((String)localObject2).length() - 1);
/*  99 */             if (this.props.get(str3) != null) { this.props.remove(str3);
/*     */             }
/*     */             
/*     */ 
/*     */           }
/* 104 */           else if (this.props.get("\"" + (String)localObject2 + "\"") != null) {
/* 105 */             this.props.remove("\"" + (String)localObject2 + "\"");
/*     */           }
/* 107 */           this.props.put(localObject2, str2);
/*     */         }
/*     */         
/* 110 */         for (Object localObject2 = this.props.keys(); ((Enumeration)localObject2).hasMoreElements();)
/*     */         {
/* 112 */           str2 = (String)((Enumeration)localObject2).nextElement();
/* 113 */           str3 = (String)this.props.get(str2);
/* 114 */           ((PrintWriter)localObject1).println("<param name=" + str2 + " value=" + str3 + ">");
/*     */         }
/*     */         
/* 117 */         ((PrintWriter)localObject1).println("</applet>");
/*     */       }
/*     */       
/* 120 */       ((PrintWriter)localObject1).println(this.post_applet.toString());
/*     */     }
/*     */     
/* 123 */     ((PrintWriter)localObject1).flush();
/*     */   }
/*     */   
/* 126 */   java.util.Properties props = new java.util.Properties();
/* 127 */   String app_width = String.valueOf(dim.width);
/* 128 */   String app_height = String.valueOf(dim.height);
/*     */   String archivename;
/*     */   
/*     */   void setParameter(String paramString1, String paramString2)
/*     */   {
/* 133 */     if ((paramString1 == null) || (paramString2 == null)) { return;
/*     */     }
/* 135 */     if (paramString1.toLowerCase().equals("height")) { this.app_height = paramString2;
/* 136 */     } else if (paramString1.toLowerCase().equals("width")) this.app_width = paramString2; else {
/* 137 */       this.props.put(paramString1, paramString2);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void getAppletTags(String paramString)
/*     */   {
/* 145 */     StringBuffer localStringBuffer = new StringBuffer();
/* 146 */     String str = null;
/* 147 */     this.filename = paramString;
/*     */     
/*     */     try
/*     */     {
/* 151 */       java.io.InputStream localInputStream = com.adventnet.nms.util.CommonUtil.openFile(new java.io.File(paramString));
/* 152 */       java.io.BufferedReader localBufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(localInputStream));
/* 153 */       while ((str = localBufferedReader.readLine()) != null) localStringBuffer.append(str + "\n");
/* 154 */       load(localStringBuffer.toString());
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 158 */       System.err.println("Reading applet file: " + paramString + ": " + localIOException);
/*     */     }
/*     */   }
/*     */   
/* 162 */   StringBuffer pre_applet = new StringBuffer();
/* 163 */   StringBuffer post_applet = new StringBuffer();
/*     */   
/* 165 */   boolean hasApplet = false;
/*     */   
/*     */ 
/*     */   void load(String paramString)
/*     */     throws IOException
/*     */   {
/* 171 */     StringBuffer localStringBuffer = this.pre_applet;
/* 172 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "<>=\r\n \t", true);
/*     */     
/* 174 */     int i = 0;
/* 175 */     while (localStringTokenizer.hasMoreTokens())
/*     */     {
/* 177 */       String str = localStringTokenizer.nextToken();
/* 178 */       if (i != 0)
/*     */       {
/* 180 */         if (str.toLowerCase().trim().equals("applet"))
/*     */         {
/* 182 */           this.hasApplet = true;
/* 183 */           getApplet(localStringTokenizer);
/* 184 */           localStringBuffer = this.post_applet;
/* 185 */           if (localStringTokenizer.hasMoreTokens()) str = localStringTokenizer.nextToken(); else
/* 186 */             str = "";
/*     */         } else {
/* 188 */           localStringBuffer.append("<");
/*     */         } }
/* 190 */       if (str.equals("<"))
/*     */       {
/* 192 */         if (i == 0) { i = 1;
/* 193 */         } else { i = 0;localStringBuffer.append("<");
/*     */         }
/*     */       }
/*     */       else {
/* 197 */         i = 0;
/* 198 */         localStringBuffer.append(str);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void getApplet(StringTokenizer paramStringTokenizer)
/*     */     throws IOException
/*     */   {
/* 206 */     String str1 = nextToken(paramStringTokenizer);
/* 207 */     String str2; String str3; while (!str1.equals(">"))
/*     */     {
/* 209 */       str2 = str1;str3 = param(paramStringTokenizer);
/* 210 */       setParameter(str2, str3);
/* 211 */       str1 = nextToken(paramStringTokenizer);
/*     */     }
/* 213 */     str1 = nextToken(paramStringTokenizer);
/*     */     
/* 215 */     while (!str1.trim().toLowerCase().equals("/applet"))
/*     */     {
/* 217 */       if (str1.trim().toLowerCase().equals("param"))
/*     */       {
/* 219 */         str2 = null;str3 = null;
/* 220 */         while (paramStringTokenizer.hasMoreTokens())
/*     */         {
/* 222 */           str1 = nextToken(paramStringTokenizer);
/* 223 */           if (str1.trim().toLowerCase().equals("name")) { str2 = param(paramStringTokenizer);
/* 224 */           } else { if (!str1.trim().toLowerCase().equals("value")) break; str3 = param(paramStringTokenizer);
/*     */           }
/*     */         }
/* 227 */         setParameter(str2, str3);
/*     */       }
/* 229 */       str1 = nextToken(paramStringTokenizer);
/*     */     }
/*     */     
/* 232 */     while (!str1.equals(">"))
/*     */     {
/* 234 */       str1 = nextToken(paramStringTokenizer);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void processLinks(StringBuffer paramStringBuffer, HttpServletRequest paramHttpServletRequest)
/*     */   {
/* 241 */     String str1 = paramHttpServletRequest.getQueryString();
/* 242 */     if ((str1 == null) || (str1.trim().equals(""))) return;
/* 243 */     String str2 = paramStringBuffer.toString();
/* 244 */     str2 = com.adventnet.nms.util.GenericUtility.replace(str2, "$NMS_ARGS", "?" + str1);
/* 245 */     paramStringBuffer.setLength(0);
/* 246 */     paramStringBuffer.append(str2);
/*     */   }
/*     */   
/*     */   String param(StringTokenizer paramStringTokenizer)
/*     */     throws IOException
/*     */   {
/* 252 */     String str = nextToken(paramStringTokenizer);
/* 253 */     if (!str.equals("=")) error("Invalid parameter", paramStringTokenizer);
/* 254 */     str = nextToken(paramStringTokenizer).trim();
/* 255 */     return str;
/*     */   }
/*     */   
/*     */   String nextToken(StringTokenizer paramStringTokenizer) throws IOException
/*     */   {
/* 260 */     if (!paramStringTokenizer.hasMoreTokens()) error("Invalid data", paramStringTokenizer);
/* 261 */     String str = paramStringTokenizer.nextToken();
/* 262 */     if (str.trim().equals("")) return nextToken(paramStringTokenizer);
/* 263 */     return str;
/*     */   }
/*     */   
/* 266 */   String filename = "";
/*     */   
/*     */   String error(String paramString, StringTokenizer paramStringTokenizer) throws IOException
/*     */   {
/* 270 */     throw new IOException("Error reading HTML: " + paramString + "\n" + "File: " + this.filename);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\NetMonitorServlet.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */