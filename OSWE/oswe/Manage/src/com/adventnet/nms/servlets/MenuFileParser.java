/*     */ package com.adventnet.nms.servlets;
/*     */ 
/*     */ import com.adventnet.nms.fe.utils.NmsMenuFileParser;
/*     */ import com.adventnet.nms.util.PureUtils;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class MenuFileParser extends javax.servlet.http.HttpServlet
/*     */ {
/*     */   public String getServletInfo()
/*     */   {
/*  25 */     return "This servlet returns an Element to construct a tree";
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
/*     */   public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/*  40 */     doGet(paramHttpServletRequest, paramHttpServletResponse);
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
/*     */   public synchronized void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/*  54 */     PrintWriter localPrintWriter = null;
/*  55 */     String str1 = paramHttpServletRequest.getParameter("userName");
/*  56 */     String str2 = paramHttpServletRequest.getParameter("menuFilename");
/*  57 */     String str3 = paramHttpServletRequest.getParameter("menuType");
/*  58 */     String str4 = paramHttpServletRequest.getParameter("client");
/*  59 */     String str5 = paramHttpServletRequest.getParameter("ACTION-ON-NO-PRIVILEGE");
/*  60 */     String str6 = paramHttpServletRequest.getParameter("checkForFile");
/*     */     try {
/*  62 */       int i = 1;
/*  63 */       if ((str6 != null) && (str6.equals("TRUE")))
/*  64 */         i = 0;
/*  65 */       paramHttpServletResponse.setStatus(200);
/*  66 */       paramHttpServletResponse.setContentType("text/html");
/*  67 */       NmsMenuFileParser localNmsMenuFileParser = new NmsMenuFileParser();
/*  68 */       if (str2 == null)
/*  69 */         str2 = "null";
/*  70 */       if (str1 == null)
/*  71 */         str1 = "null";
/*  72 */       String str7 = getFilePath(str2, str1, str3);
/*  73 */       Element localElement = null;
/*  74 */       localPrintWriter = paramHttpServletResponse.getWriter();
/*  75 */       File localFile = new File(str7);
/*  76 */       if (i != 0)
/*     */       {
/*  78 */         if (localFile.exists())
/*     */         {
/*  80 */           localElement = localNmsMenuFileParser.getRootElement(str1, str2, str3, str5, str4);
/*  81 */           if (localElement != null)
/*     */           {
/*  83 */             localPrintWriter.println("Parse Successful");
/*  84 */             String str8 = localElement.toString();
/*  85 */             if ((str8 != null) && (str8.indexOf("null") == -1))
/*     */             {
/*  87 */               localPrintWriter.println(str8);
/*     */             }
/*     */             else
/*     */             {
/*  91 */               StringBuffer localStringBuffer = new StringBuffer();
/*  92 */               String str9 = getElementString(localElement, localStringBuffer).toString();
/*  93 */               localPrintWriter.println(str9);
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/*  98 */             localPrintWriter.println("Parse Failed");
/*  99 */             System.err.println(" Root node is null in MenuFileParser for " + str2);
/*     */           }
/*     */           
/*     */         }
/*     */         else
/*     */         {
/* 105 */           localPrintWriter.println("File Not Found");
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */       }
/* 112 */       else if (localFile.exists())
/*     */       {
/* 114 */         localPrintWriter.println("File Found");
/*     */       }
/*     */       else {
/* 117 */         localPrintWriter.println("File Not Found");
/*     */       }
/*     */       
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 123 */       localIOException.printStackTrace();
/* 124 */       throw localIOException;
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/* 128 */       localException.printStackTrace();
/*     */     }
/*     */     
/* 131 */     localPrintWriter.flush();
/* 132 */     localPrintWriter.close();
/*     */   }
/*     */   
/*     */   private String getFilePath(String paramString1, String paramString2, String paramString3) throws IOException {
/* 136 */     String str = null;
/* 137 */     File localFile = null;
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
/* 157 */     if (paramString3 == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 163 */       str = PureUtils.usersDir + "users" + File.separator + paramString2 + File.separator + paramString1 + ".xml";
/*     */       
/*     */       try
/*     */       {
/* 167 */         localFile = new File(str);
/* 168 */         if (!localFile.exists())
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 175 */           str = PureUtils.rootDir + "html" + File.separator + "defaultsToAllUsers" + File.separator + paramString1 + ".xml";
/*     */         }
/*     */       }
/*     */       catch (Exception localException1)
/*     */       {
/* 180 */         localException1.printStackTrace();
/*     */       }
/* 182 */       return str;
/*     */     }
/* 184 */     if (paramString3.equals("map"))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 190 */       str = PureUtils.usersDir + "users" + File.separator + paramString2 + File.separator + "mapmenus" + File.separator + paramString1 + ".xml";
/*     */       
/*     */       try
/*     */       {
/* 194 */         localFile = new File(str);
/* 195 */         if (!localFile.exists())
/*     */         {
/* 197 */           str = PureUtils.usersDir + "mapdata" + File.separator + "menus" + File.separator + paramString1 + ".xml";
/*     */         }
/*     */       }
/*     */       catch (Exception localException2) {
/* 201 */         localException2.printStackTrace();
/*     */       }
/* 203 */       return str;
/*     */     }
/*     */     
/* 206 */     if (paramString3.equals("list"))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 212 */       str = PureUtils.usersDir + "users" + File.separator + paramString2 + File.separator + "listmenus" + File.separator + paramString1 + ".xml";
/*     */       
/*     */       try
/*     */       {
/* 216 */         localFile = new File(str);
/* 217 */         if (!localFile.exists())
/*     */         {
/* 219 */           str = PureUtils.usersDir + "listmenus" + File.separator + paramString1 + ".xml";
/*     */         }
/*     */       }
/*     */       catch (Exception localException3)
/*     */       {
/* 224 */         localException3.printStackTrace();
/*     */       }
/* 226 */       return str;
/*     */     }
/*     */     
/* 229 */     if (paramString3.equals("policy"))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 235 */       str = PureUtils.usersDir + "users" + File.separator + paramString2 + File.separator + "policymenus" + File.separator + paramString1 + ".xml";
/*     */       
/*     */       try
/*     */       {
/* 239 */         localFile = new File(str);
/* 240 */         return str;
/*     */       }
/*     */       catch (Exception localException4)
/*     */       {
/* 244 */         localException4.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/* 248 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private StringBuffer getElementString(Element paramElement, StringBuffer paramStringBuffer)
/*     */   {
/* 257 */     if (paramElement == null)
/*     */     {
/* 259 */       return paramStringBuffer;
/*     */     }
/* 261 */     String str1 = paramElement.getNodeName();
/* 262 */     paramStringBuffer.append("<");
/* 263 */     paramStringBuffer.append(str1);
/* 264 */     NamedNodeMap localNamedNodeMap = paramElement.getAttributes();
/* 265 */     Object localObject1; Object localObject3; Object localObject4; for (int i = 0; i < localNamedNodeMap.getLength(); i++)
/*     */     {
/* 267 */       localObject1 = localNamedNodeMap.item(i);
/* 268 */       paramStringBuffer.append(" ");
/* 269 */       paramStringBuffer.append(((Node)localObject1).getNodeName());
/* 270 */       paramStringBuffer.append(" ");
/* 271 */       paramStringBuffer.append("=");
/* 272 */       paramStringBuffer.append(" ");
/* 273 */       paramStringBuffer.append("\"");
/* 274 */       Object localObject2 = ((Node)localObject1).getNodeValue();
/* 275 */       localObject3 = new java.util.Properties();
/* 276 */       ((Hashtable)localObject3).put("<", "&lt;");
/* 277 */       ((Hashtable)localObject3).put(">", "&gt;");
/* 278 */       ((Hashtable)localObject3).put("&", "&amp;");
/* 279 */       ((Hashtable)localObject3).put("'", "&apos;");
/* 280 */       ((Hashtable)localObject3).put("\"", "&quot;");
/* 281 */       localObject4 = ((Hashtable)localObject3).keys();
/* 282 */       int k = 0;
/* 283 */       String str2 = "";
/* 284 */       while (((Enumeration)localObject4).hasMoreElements())
/*     */       {
/* 286 */         String str3 = (String)((Enumeration)localObject4).nextElement();
/* 287 */         if (((String)localObject2).indexOf(str3) != -1)
/*     */         {
/* 289 */           String str4 = (String)((Hashtable)localObject3).get(str3);
/*     */           
/* 291 */           StringTokenizer localStringTokenizer = new StringTokenizer((String)localObject2, str3, true);
/* 292 */           while (localStringTokenizer.hasMoreTokens())
/*     */           {
/* 294 */             String str5 = localStringTokenizer.nextToken();
/* 295 */             if (str5.equals(str3))
/*     */             {
/* 297 */               str2 = str2 + str4;
/*     */             }
/*     */             else
/*     */             {
/* 301 */               str2 = str2 + str5;
/*     */             }
/*     */           }
/* 304 */           localObject2 = str2;
/*     */         }
/*     */       }
/*     */       
/* 308 */       paramStringBuffer.append((String)localObject2);
/*     */       
/* 310 */       paramStringBuffer.append("\"");
/* 311 */       paramStringBuffer.append(" ");
/*     */     }
/*     */     
/* 314 */     if (!paramElement.hasChildNodes()) {
/* 315 */       paramStringBuffer.append("/>");
/*     */     }
/*     */     else
/*     */     {
/* 319 */       paramStringBuffer.append(">");
/* 320 */       localObject1 = paramElement.getChildNodes();
/* 321 */       for (int j = 0; j < ((NodeList)localObject1).getLength(); j++)
/*     */       {
/* 323 */         localObject3 = ((NodeList)localObject1).item(j);
/* 324 */         if (((Node)localObject3).getNodeType() != 1)
/*     */         {
/* 326 */           ((Node)localObject3).getParentNode().removeChild((Node)localObject3);
/* 327 */           j--;
/*     */         }
/*     */         else {
/* 330 */           localObject4 = (Element)((NodeList)localObject1).item(j);
/* 331 */           if (localObject4 != null)
/*     */           {
/*     */ 
/*     */ 
/* 335 */             getElementString((Element)localObject4, paramStringBuffer); }
/*     */         }
/*     */       }
/* 338 */       paramStringBuffer.append(" ");
/* 339 */       paramStringBuffer.append("</");
/* 340 */       paramStringBuffer.append(str1);
/* 341 */       paramStringBuffer.append(">");
/*     */     }
/*     */     
/*     */ 
/* 345 */     return paramStringBuffer;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\MenuFileParser.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */