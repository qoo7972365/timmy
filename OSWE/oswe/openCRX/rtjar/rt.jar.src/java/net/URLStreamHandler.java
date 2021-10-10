/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.net.util.IPAddressUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class URLStreamHandler
/*     */ {
/*     */   protected abstract URLConnection openConnection(URL paramURL) throws IOException;
/*     */   
/*     */   protected URLConnection openConnection(URL paramURL, Proxy paramProxy) throws IOException {
/*  96 */     throw new UnsupportedOperationException("Method not implemented.");
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
/*     */   protected void parseURL(URL paramURL, String paramString, int paramInt1, int paramInt2) {
/* 126 */     String str1 = paramURL.getProtocol();
/* 127 */     String str2 = paramURL.getAuthority();
/* 128 */     String str3 = paramURL.getUserInfo();
/* 129 */     String str4 = paramURL.getHost();
/* 130 */     int i = paramURL.getPort();
/* 131 */     String str5 = paramURL.getPath();
/* 132 */     String str6 = paramURL.getQuery();
/*     */ 
/*     */     
/* 135 */     String str7 = paramURL.getRef();
/*     */     
/* 137 */     boolean bool1 = false;
/* 138 */     boolean bool2 = false;
/*     */ 
/*     */ 
/*     */     
/* 142 */     if (paramInt1 < paramInt2) {
/* 143 */       int k = paramString.indexOf('?');
/* 144 */       bool2 = (k == paramInt1) ? true : false;
/* 145 */       if (k != -1 && k < paramInt2) {
/* 146 */         str6 = paramString.substring(k + 1, paramInt2);
/* 147 */         if (paramInt2 > k)
/* 148 */           paramInt2 = k; 
/* 149 */         paramString = paramString.substring(0, k);
/*     */       } 
/*     */     } 
/*     */     
/* 153 */     int j = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     boolean bool3 = (paramInt1 <= paramInt2 - 4 && paramString.charAt(paramInt1) == '/' && paramString.charAt(paramInt1 + 1) == '/' && paramString.charAt(paramInt1 + 2) == '/' && paramString.charAt(paramInt1 + 3) == '/') ? true : false;
/* 160 */     if (!bool3 && paramInt1 <= paramInt2 - 2 && paramString.charAt(paramInt1) == '/' && paramString
/* 161 */       .charAt(paramInt1 + 1) == '/') {
/* 162 */       paramInt1 += 2;
/* 163 */       j = paramString.indexOf('/', paramInt1);
/* 164 */       if (j < 0 || j > paramInt2) {
/* 165 */         j = paramString.indexOf('?', paramInt1);
/* 166 */         if (j < 0 || j > paramInt2) {
/* 167 */           j = paramInt2;
/*     */         }
/*     */       } 
/* 170 */       str4 = str2 = paramString.substring(paramInt1, j);
/*     */       
/* 172 */       int k = str2.indexOf('@');
/* 173 */       if (k != -1) {
/* 174 */         if (k != str2.lastIndexOf('@')) {
/*     */           
/* 176 */           str3 = null;
/* 177 */           str4 = null;
/*     */         } else {
/* 179 */           str3 = str2.substring(0, k);
/* 180 */           str4 = str2.substring(k + 1);
/*     */         } 
/*     */       } else {
/* 183 */         str3 = null;
/*     */       } 
/* 185 */       if (str4 != null) {
/*     */ 
/*     */         
/* 188 */         if (str4.length() > 0 && str4.charAt(0) == '[') {
/* 189 */           if ((k = str4.indexOf(']')) > 2) {
/*     */             
/* 191 */             String str = str4;
/* 192 */             str4 = str.substring(0, k + 1);
/*     */             
/* 194 */             if (!IPAddressUtil.isIPv6LiteralAddress(str4.substring(1, k))) {
/* 195 */               throw new IllegalArgumentException("Invalid host: " + str4);
/*     */             }
/*     */ 
/*     */             
/* 199 */             i = -1;
/* 200 */             if (str.length() > k + 1) {
/* 201 */               if (str.charAt(k + 1) == ':') {
/* 202 */                 k++;
/*     */                 
/* 204 */                 if (str.length() > k + 1) {
/* 205 */                   i = Integer.parseInt(str.substring(k + 1));
/*     */                 }
/*     */               } else {
/* 208 */                 throw new IllegalArgumentException("Invalid authority field: " + str2);
/*     */               } 
/*     */             }
/*     */           } else {
/*     */             
/* 213 */             throw new IllegalArgumentException("Invalid authority field: " + str2);
/*     */           } 
/*     */         } else {
/*     */           
/* 217 */           k = str4.indexOf(':');
/* 218 */           i = -1;
/* 219 */           if (k >= 0) {
/*     */             
/* 221 */             if (str4.length() > k + 1) {
/* 222 */               i = Integer.parseInt(str4.substring(k + 1));
/*     */             }
/* 224 */             str4 = str4.substring(0, k);
/*     */           } 
/*     */         } 
/*     */       } else {
/* 228 */         str4 = "";
/*     */       } 
/* 230 */       if (i < -1) {
/* 231 */         throw new IllegalArgumentException("Invalid port number :" + i);
/*     */       }
/* 233 */       paramInt1 = j;
/*     */ 
/*     */       
/* 236 */       if (str2 != null && str2.length() > 0) {
/* 237 */         str5 = "";
/*     */       }
/*     */     } 
/* 240 */     if (str4 == null) {
/* 241 */       str4 = "";
/*     */     }
/*     */ 
/*     */     
/* 245 */     if (paramInt1 < paramInt2) {
/* 246 */       if (paramString.charAt(paramInt1) == '/') {
/* 247 */         str5 = paramString.substring(paramInt1, paramInt2);
/* 248 */       } else if (str5 != null && str5.length() > 0) {
/* 249 */         bool1 = true;
/* 250 */         int k = str5.lastIndexOf('/');
/* 251 */         String str = "";
/* 252 */         if (k == -1 && str2 != null) {
/* 253 */           str = "/";
/*     */         }
/* 255 */         str5 = str5.substring(0, k + 1) + str + paramString.substring(paramInt1, paramInt2);
/*     */       } else {
/*     */         
/* 258 */         String str = (str2 != null) ? "/" : "";
/* 259 */         str5 = str + paramString.substring(paramInt1, paramInt2);
/*     */       } 
/* 261 */     } else if (bool2 && str5 != null) {
/* 262 */       int k = str5.lastIndexOf('/');
/* 263 */       if (k < 0)
/* 264 */         k = 0; 
/* 265 */       str5 = str5.substring(0, k) + "/";
/*     */     } 
/* 267 */     if (str5 == null) {
/* 268 */       str5 = "";
/*     */     }
/* 270 */     if (bool1) {
/*     */       
/* 272 */       while ((j = str5.indexOf("/./")) >= 0) {
/* 273 */         str5 = str5.substring(0, j) + str5.substring(j + 2);
/*     */       }
/*     */       
/* 276 */       j = 0;
/* 277 */       while ((j = str5.indexOf("/../", j)) >= 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 284 */         if (j > 0 && (paramInt2 = str5.lastIndexOf('/', j - 1)) >= 0 && str5
/* 285 */           .indexOf("/../", paramInt2) != 0) {
/* 286 */           str5 = str5.substring(0, paramInt2) + str5.substring(j + 3);
/* 287 */           j = 0; continue;
/*     */         } 
/* 289 */         j += 3;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 294 */       j = str5.indexOf("/..");
/* 295 */       while (str5.endsWith("/..") && (paramInt2 = str5.lastIndexOf('/', j - 1)) >= 0) {
/* 296 */         str5 = str5.substring(0, paramInt2 + 1);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 302 */       if (str5.startsWith("./") && str5.length() > 2) {
/* 303 */         str5 = str5.substring(2);
/*     */       }
/*     */       
/* 306 */       if (str5.endsWith("/.")) {
/* 307 */         str5 = str5.substring(0, str5.length() - 1);
/*     */       }
/*     */     } 
/* 310 */     setURL(paramURL, str1, str4, i, str2, str3, str5, str6, str7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getDefaultPort() {
/* 320 */     return -1;
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
/*     */   protected boolean equals(URL paramURL1, URL paramURL2) {
/* 336 */     String str1 = paramURL1.getRef();
/* 337 */     String str2 = paramURL2.getRef();
/* 338 */     return ((str1 == str2 || (str1 != null && str1.equals(str2))) && 
/* 339 */       sameFile(paramURL1, paramURL2));
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
/*     */   protected int hashCode(URL paramURL) {
/* 351 */     int i = 0;
/*     */ 
/*     */     
/* 354 */     String str1 = paramURL.getProtocol();
/* 355 */     if (str1 != null) {
/* 356 */       i += str1.hashCode();
/*     */     }
/*     */     
/* 359 */     InetAddress inetAddress = getHostAddress(paramURL);
/* 360 */     if (inetAddress != null) {
/* 361 */       i += inetAddress.hashCode();
/*     */     } else {
/* 363 */       String str = paramURL.getHost();
/* 364 */       if (str != null) {
/* 365 */         i += str.toLowerCase().hashCode();
/*     */       }
/*     */     } 
/*     */     
/* 369 */     String str2 = paramURL.getFile();
/* 370 */     if (str2 != null) {
/* 371 */       i += str2.hashCode();
/*     */     }
/*     */     
/* 374 */     if (paramURL.getPort() == -1) {
/* 375 */       i += getDefaultPort();
/*     */     } else {
/* 377 */       i += paramURL.getPort();
/*     */     } 
/*     */     
/* 380 */     String str3 = paramURL.getRef();
/* 381 */     if (str3 != null) {
/* 382 */       i += str3.hashCode();
/*     */     }
/* 384 */     return i;
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
/*     */   protected boolean sameFile(URL paramURL1, URL paramURL2) {
/* 400 */     if (paramURL1.getProtocol() != paramURL2.getProtocol() && (paramURL1
/* 401 */       .getProtocol() == null || 
/* 402 */       !paramURL1.getProtocol().equalsIgnoreCase(paramURL2.getProtocol()))) {
/* 403 */       return false;
/*     */     }
/*     */     
/* 406 */     if (paramURL1.getFile() != paramURL2.getFile() && (paramURL1
/* 407 */       .getFile() == null || !paramURL1.getFile().equals(paramURL2.getFile()))) {
/* 408 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 412 */     int i = (paramURL1.getPort() != -1) ? paramURL1.getPort() : paramURL1.handler.getDefaultPort();
/* 413 */     int j = (paramURL2.getPort() != -1) ? paramURL2.getPort() : paramURL2.handler.getDefaultPort();
/* 414 */     if (i != j) {
/* 415 */       return false;
/*     */     }
/*     */     
/* 418 */     if (!hostsEqual(paramURL1, paramURL2)) {
/* 419 */       return false;
/*     */     }
/* 421 */     return true;
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
/*     */   protected synchronized InetAddress getHostAddress(URL paramURL) {
/* 434 */     if (paramURL.hostAddress != null) {
/* 435 */       return paramURL.hostAddress;
/*     */     }
/* 437 */     String str = paramURL.getHost();
/* 438 */     if (str == null || str.equals("")) {
/* 439 */       return null;
/*     */     }
/*     */     try {
/* 442 */       paramURL.hostAddress = InetAddress.getByName(str);
/* 443 */     } catch (UnknownHostException unknownHostException) {
/* 444 */       return null;
/* 445 */     } catch (SecurityException securityException) {
/* 446 */       return null;
/*     */     } 
/*     */     
/* 449 */     return paramURL.hostAddress;
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
/*     */   protected boolean hostsEqual(URL paramURL1, URL paramURL2) {
/* 461 */     InetAddress inetAddress1 = getHostAddress(paramURL1);
/* 462 */     InetAddress inetAddress2 = getHostAddress(paramURL2);
/*     */     
/* 464 */     if (inetAddress1 != null && inetAddress2 != null) {
/* 465 */       return inetAddress1.equals(inetAddress2);
/*     */     }
/* 467 */     if (paramURL1.getHost() != null && paramURL2.getHost() != null) {
/* 468 */       return paramURL1.getHost().equalsIgnoreCase(paramURL2.getHost());
/*     */     }
/* 470 */     return (paramURL1.getHost() == null && paramURL2.getHost() == null);
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
/*     */   protected String toExternalForm(URL paramURL) {
/* 483 */     int i = paramURL.getProtocol().length() + 1;
/* 484 */     if (paramURL.getAuthority() != null && paramURL.getAuthority().length() > 0)
/* 485 */       i += 2 + paramURL.getAuthority().length(); 
/* 486 */     if (paramURL.getPath() != null) {
/* 487 */       i += paramURL.getPath().length();
/*     */     }
/* 489 */     if (paramURL.getQuery() != null) {
/* 490 */       i += 1 + paramURL.getQuery().length();
/*     */     }
/* 492 */     if (paramURL.getRef() != null) {
/* 493 */       i += 1 + paramURL.getRef().length();
/*     */     }
/* 495 */     StringBuffer stringBuffer = new StringBuffer(i);
/* 496 */     stringBuffer.append(paramURL.getProtocol());
/* 497 */     stringBuffer.append(":");
/* 498 */     if (paramURL.getAuthority() != null && paramURL.getAuthority().length() > 0) {
/* 499 */       stringBuffer.append("//");
/* 500 */       stringBuffer.append(paramURL.getAuthority());
/*     */     } 
/* 502 */     if (paramURL.getPath() != null) {
/* 503 */       stringBuffer.append(paramURL.getPath());
/*     */     }
/* 505 */     if (paramURL.getQuery() != null) {
/* 506 */       stringBuffer.append('?');
/* 507 */       stringBuffer.append(paramURL.getQuery());
/*     */     } 
/* 509 */     if (paramURL.getRef() != null) {
/* 510 */       stringBuffer.append("#");
/* 511 */       stringBuffer.append(paramURL.getRef());
/*     */     } 
/* 513 */     return stringBuffer.toString();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setURL(URL paramURL, String paramString1, String paramString2, int paramInt, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) {
/* 538 */     if (this != paramURL.handler) {
/* 539 */       throw new SecurityException("handler for url different from this handler");
/*     */     }
/* 541 */     if (paramString2 != null && paramURL.isBuiltinStreamHandler(this)) {
/* 542 */       String str = IPAddressUtil.checkHostString(paramString2);
/* 543 */       if (str != null) throw new IllegalArgumentException(str);
/*     */     
/*     */     } 
/* 546 */     paramURL.set(paramURL.getProtocol(), paramString2, paramInt, paramString3, paramString4, paramString5, paramString6, paramString7);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void setURL(URL paramURL, String paramString1, String paramString2, int paramInt, String paramString3, String paramString4) {
/* 572 */     String str1 = null;
/* 573 */     String str2 = null;
/* 574 */     if (paramString2 != null && paramString2.length() != 0) {
/* 575 */       str1 = (paramInt == -1) ? paramString2 : (paramString2 + ":" + paramInt);
/* 576 */       int i = paramString2.lastIndexOf('@');
/* 577 */       if (i != -1) {
/* 578 */         str2 = paramString2.substring(0, i);
/* 579 */         paramString2 = paramString2.substring(i + 1);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 586 */     String str3 = null;
/* 587 */     String str4 = null;
/* 588 */     if (paramString3 != null) {
/* 589 */       int i = paramString3.lastIndexOf('?');
/* 590 */       if (i != -1) {
/* 591 */         str4 = paramString3.substring(i + 1);
/* 592 */         str3 = paramString3.substring(0, i);
/*     */       } else {
/* 594 */         str3 = paramString3;
/*     */       } 
/* 596 */     }  setURL(paramURL, paramString1, paramString2, paramInt, str1, str2, str3, str4, paramString4);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/URLStreamHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */