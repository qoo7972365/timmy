/*     */ package javax.management.remote;
/*     */ 
/*     */ import com.sun.jmx.remote.util.ClassLogger;
/*     */ import com.sun.jmx.remote.util.EnvHelp;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.net.InetAddress;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.BitSet;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class JMXServiceURL
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8173364409860779292L;
/*     */   private static final String INVALID_INSTANCE_MSG = "Trying to deserialize an invalid instance of JMXServiceURL";
/*     */   
/*     */   public JMXServiceURL(String paramString) throws MalformedURLException {
/* 142 */     int i1, i2, i = paramString.length();
/*     */ 
/*     */ 
/*     */     
/* 146 */     for (byte b = 0; b < i; b++) {
/* 147 */       char c = paramString.charAt(b);
/* 148 */       if (c < ' ' || c >= '') {
/* 149 */         throw new MalformedURLException("Service URL contains non-ASCII character 0x" + 
/*     */             
/* 151 */             Integer.toHexString(c));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 157 */     int j = "service:jmx:".length();
/* 158 */     if (!paramString.regionMatches(true, 0, "service:jmx:", 0, j))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 163 */       throw new MalformedURLException("Service URL must start with service:jmx:");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 168 */     int k = j;
/* 169 */     int m = indexOf(paramString, ':', k);
/* 170 */     this
/* 171 */       .protocol = paramString.substring(k, m).toLowerCase();
/*     */     
/* 173 */     if (!paramString.regionMatches(m, "://", 0, 3)) {
/* 174 */       throw new MalformedURLException("Missing \"://\" after protocol name");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 179 */     int n = m + 3;
/*     */     
/* 181 */     if (n < i && paramString
/* 182 */       .charAt(n) == '[') {
/* 183 */       i1 = paramString.indexOf(']', n) + 1;
/* 184 */       if (i1 == 0)
/* 185 */         throw new MalformedURLException("Bad host name: [ without ]"); 
/* 186 */       this.host = paramString.substring(n + 1, i1 - 1);
/* 187 */       if (!isNumericIPv6Address(this.host)) {
/* 188 */         throw new MalformedURLException("Address inside [...] must be numeric IPv6 address");
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 193 */       i1 = indexOfFirstNotInSet(paramString, hostNameBitSet, n);
/* 194 */       this.host = paramString.substring(n, i1);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 199 */     if (i1 < i && paramString.charAt(i1) == ':') {
/* 200 */       if (this.host.length() == 0) {
/* 201 */         throw new MalformedURLException("Cannot give port number without host name");
/*     */       }
/*     */       
/* 204 */       int i4 = i1 + 1;
/*     */       
/* 206 */       i2 = indexOfFirstNotInSet(paramString, numericBitSet, i4);
/* 207 */       String str = paramString.substring(i4, i2);
/*     */       try {
/* 209 */         this.port = Integer.parseInt(str);
/* 210 */       } catch (NumberFormatException numberFormatException) {
/* 211 */         throw new MalformedURLException("Bad port number: \"" + str + "\": " + numberFormatException);
/*     */       } 
/*     */     } else {
/*     */       
/* 215 */       i2 = i1;
/* 216 */       this.port = 0;
/*     */     } 
/*     */ 
/*     */     
/* 220 */     int i3 = i2;
/* 221 */     if (i3 < i) {
/* 222 */       this.urlPath = paramString.substring(i3);
/*     */     } else {
/* 224 */       this.urlPath = "";
/*     */     } 
/* 226 */     validate();
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
/*     */   public JMXServiceURL(String paramString1, String paramString2, int paramInt) throws MalformedURLException {
/* 253 */     this(paramString1, paramString2, paramInt, null);
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
/*     */   public JMXServiceURL(String paramString1, String paramString2, int paramInt, String paramString3) throws MalformedURLException {
/* 281 */     if (paramString1 == null) {
/* 282 */       paramString1 = "jmxmp";
/*     */     }
/* 284 */     if (paramString2 == null) {
/*     */       InetAddress inetAddress;
/*     */       try {
/* 287 */         inetAddress = InetAddress.getLocalHost();
/* 288 */       } catch (UnknownHostException unknownHostException) {
/* 289 */         throw new MalformedURLException("Local host name unknown: " + unknownHostException);
/*     */       } 
/*     */ 
/*     */       
/* 293 */       paramString2 = inetAddress.getHostName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 303 */         validateHost(paramString2, paramInt);
/* 304 */       } catch (MalformedURLException malformedURLException) {
/* 305 */         if (logger.fineOn()) {
/* 306 */           logger.fine("JMXServiceURL", "Replacing illegal local host name " + paramString2 + " with numeric IP address (see RFC 1034)", malformedURLException);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 311 */         paramString2 = inetAddress.getHostAddress();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 317 */     if (paramString2.startsWith("[")) {
/* 318 */       if (!paramString2.endsWith("]")) {
/* 319 */         throw new MalformedURLException("Host starts with [ but does not end with ]");
/*     */       }
/*     */       
/* 322 */       paramString2 = paramString2.substring(1, paramString2.length() - 1);
/* 323 */       if (!isNumericIPv6Address(paramString2)) {
/* 324 */         throw new MalformedURLException("Address inside [...] must be numeric IPv6 address");
/*     */       }
/*     */       
/* 327 */       if (paramString2.startsWith("[")) {
/* 328 */         throw new MalformedURLException("More than one [[...]]");
/*     */       }
/*     */     } 
/* 331 */     this.protocol = paramString1.toLowerCase();
/* 332 */     this.host = paramString2;
/* 333 */     this.port = paramInt;
/*     */     
/* 335 */     if (paramString3 == null)
/* 336 */       paramString3 = ""; 
/* 337 */     this.urlPath = paramString3;
/*     */     
/* 339 */     validate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 345 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 346 */     String str1 = (String)getField.get("host", (Object)null);
/* 347 */     int i = getField.get("port", -1);
/* 348 */     String str2 = (String)getField.get("protocol", (Object)null);
/* 349 */     String str3 = (String)getField.get("urlPath", (Object)null);
/*     */     
/* 351 */     if (str2 == null || str3 == null || str1 == null) {
/* 352 */       StringBuilder stringBuilder = (new StringBuilder("Trying to deserialize an invalid instance of JMXServiceURL")).append('[');
/* 353 */       boolean bool = true;
/* 354 */       if (str2 == null) {
/* 355 */         stringBuilder.append("protocol=null");
/* 356 */         bool = false;
/*     */       } 
/* 358 */       if (str1 == null) {
/* 359 */         stringBuilder.append(bool ? "" : ",").append("host=null");
/* 360 */         bool = false;
/*     */       } 
/* 362 */       if (str3 == null) {
/* 363 */         stringBuilder.append(bool ? "" : ",").append("urlPath=null");
/*     */       }
/* 365 */       stringBuilder.append(']');
/* 366 */       throw new InvalidObjectException(stringBuilder.toString());
/*     */     } 
/*     */     
/* 369 */     if (str1.contains("[") || str1.contains("]")) {
/* 370 */       throw new InvalidObjectException("Invalid host name: " + str1);
/*     */     }
/*     */     
/*     */     try {
/* 374 */       validate(str2, str1, i, str3);
/* 375 */       this.protocol = str2;
/* 376 */       this.host = str1;
/* 377 */       this.port = i;
/* 378 */       this.urlPath = str3;
/* 379 */     } catch (MalformedURLException malformedURLException) {
/* 380 */       throw new InvalidObjectException("Trying to deserialize an invalid instance of JMXServiceURL: " + malformedURLException
/* 381 */           .getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void validate(String paramString1, String paramString2, int paramInt, String paramString3) throws MalformedURLException {
/* 389 */     int i = indexOfFirstNotInSet(paramString1, protocolBitSet, 0);
/* 390 */     if (i == 0 || i < paramString1.length() || 
/* 391 */       !alphaBitSet.get(paramString1.charAt(0))) {
/* 392 */       throw new MalformedURLException("Missing or invalid protocol name: \"" + paramString1 + "\"");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 397 */     validateHost(paramString2, paramInt);
/*     */ 
/*     */     
/* 400 */     if (paramInt < 0) {
/* 401 */       throw new MalformedURLException("Bad port: " + paramInt);
/*     */     }
/*     */     
/* 404 */     if (paramString3.length() > 0 && 
/* 405 */       !paramString3.startsWith("/") && !paramString3.startsWith(";")) {
/* 406 */       throw new MalformedURLException("Bad URL path: " + paramString3);
/*     */     }
/*     */   }
/*     */   
/*     */   private void validate() throws MalformedURLException {
/* 411 */     validate(this.protocol, this.host, this.port, this.urlPath);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void validateHost(String paramString, int paramInt) throws MalformedURLException {
/* 417 */     if (paramString.length() == 0) {
/* 418 */       if (paramInt != 0) {
/* 419 */         throw new MalformedURLException("Cannot give port number without host name");
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 425 */     if (isNumericIPv6Address(paramString)) {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 433 */         InetAddress.getByName(paramString);
/* 434 */       } catch (Exception exception) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 439 */         MalformedURLException malformedURLException = new MalformedURLException("Bad IPv6 address: " + paramString);
/*     */         
/* 441 */         EnvHelp.initCause(malformedURLException, exception);
/* 442 */         throw malformedURLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 465 */       int i = paramString.length();
/* 466 */       byte b1 = 46;
/* 467 */       boolean bool = false;
/* 468 */       char c = Character.MIN_VALUE;
/*     */ 
/*     */       
/* 471 */       for (byte b2 = 0; b2 < i; b2++) {
/* 472 */         char c1 = paramString.charAt(b2);
/* 473 */         boolean bool1 = alphaNumericBitSet.get(c1);
/* 474 */         if (b1 == 46)
/* 475 */           c = c1; 
/* 476 */         if (bool1) {
/* 477 */           b1 = 97;
/* 478 */         } else if (c1 == '-') {
/* 479 */           if (b1 == 46)
/*     */             break; 
/* 481 */           b1 = 45;
/* 482 */         } else if (c1 == '.') {
/* 483 */           bool = true;
/* 484 */           if (b1 != 97)
/*     */             break; 
/* 486 */           b1 = 46;
/*     */         } else {
/* 488 */           b1 = 46;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       try {
/* 494 */         if (b1 != 97)
/* 495 */           throw randomException; 
/* 496 */         if (bool && !alphaBitSet.get(c)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 504 */           StringTokenizer stringTokenizer = new StringTokenizer(paramString, ".", true);
/* 505 */           for (byte b = 0; b < 4; b++) {
/* 506 */             String str = stringTokenizer.nextToken();
/* 507 */             int j = Integer.parseInt(str);
/* 508 */             if (j < 0 || j > 255)
/* 509 */               throw randomException; 
/* 510 */             if (b < 3 && !stringTokenizer.nextToken().equals("."))
/* 511 */               throw randomException; 
/*     */           } 
/* 513 */           if (stringTokenizer.hasMoreTokens())
/* 514 */             throw randomException; 
/*     */         } 
/* 516 */       } catch (Exception exception) {
/* 517 */         throw new MalformedURLException("Bad host: \"" + paramString + "\"");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/* 522 */   private static final Exception randomException = new Exception();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getProtocol() {
/* 531 */     return this.protocol;
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
/*     */   public String getHost() {
/* 551 */     return this.host;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPort() {
/* 561 */     return this.port;
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
/*     */   public String getURLPath() {
/* 573 */     return this.urlPath;
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
/*     */   public String toString() {
/* 596 */     if (this.toString != null)
/* 597 */       return this.toString; 
/* 598 */     StringBuilder stringBuilder = new StringBuilder("service:jmx:");
/* 599 */     stringBuilder.append(getProtocol()).append("://");
/* 600 */     String str = getHost();
/* 601 */     if (isNumericIPv6Address(str)) {
/* 602 */       stringBuilder.append('[').append(str).append(']');
/*     */     } else {
/* 604 */       stringBuilder.append(str);
/* 605 */     }  int i = getPort();
/* 606 */     if (i != 0)
/* 607 */       stringBuilder.append(':').append(i); 
/* 608 */     stringBuilder.append(getURLPath());
/* 609 */     this.toString = stringBuilder.toString();
/* 610 */     return this.toString;
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
/*     */   public boolean equals(Object paramObject) {
/* 628 */     if (!(paramObject instanceof JMXServiceURL))
/* 629 */       return false; 
/* 630 */     JMXServiceURL jMXServiceURL = (JMXServiceURL)paramObject;
/* 631 */     return (jMXServiceURL
/* 632 */       .getProtocol().equalsIgnoreCase(getProtocol()) && jMXServiceURL
/* 633 */       .getHost().equalsIgnoreCase(getHost()) && jMXServiceURL
/* 634 */       .getPort() == getPort() && jMXServiceURL
/* 635 */       .getURLPath().equals(getURLPath()));
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 639 */     return toString().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isNumericIPv6Address(String paramString) {
/* 647 */     return (paramString.indexOf(':') >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int indexOf(String paramString, char paramChar, int paramInt) {
/* 652 */     int i = paramString.indexOf(paramChar, paramInt);
/* 653 */     if (i < 0) {
/* 654 */       return paramString.length();
/*     */     }
/* 656 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int indexOfFirstNotInSet(String paramString, BitSet paramBitSet, int paramInt) {
/* 661 */     int i = paramString.length();
/* 662 */     int j = paramInt;
/*     */     
/* 664 */     while (j < i) {
/*     */       
/* 666 */       char c = paramString.charAt(j);
/* 667 */       if (c >= '')
/*     */         break; 
/* 669 */       if (!paramBitSet.get(c))
/*     */         break; 
/* 671 */       j++;
/*     */     } 
/* 673 */     return j;
/*     */   }
/*     */   
/* 676 */   private static final BitSet alphaBitSet = new BitSet(128);
/* 677 */   private static final BitSet numericBitSet = new BitSet(128);
/* 678 */   private static final BitSet alphaNumericBitSet = new BitSet(128);
/* 679 */   private static final BitSet protocolBitSet = new BitSet(128);
/* 680 */   private static final BitSet hostNameBitSet = new BitSet(128);
/*     */   private String protocol;
/*     */   private String host;
/*     */   
/*     */   static {
/*     */     char c;
/* 686 */     for (c = '0'; c <= '9'; c = (char)(c + 1)) {
/* 687 */       numericBitSet.set(c);
/*     */     }
/* 689 */     for (c = 'A'; c <= 'Z'; c = (char)(c + 1))
/* 690 */       alphaBitSet.set(c); 
/* 691 */     for (c = 'a'; c <= 'z'; c = (char)(c + 1)) {
/* 692 */       alphaBitSet.set(c);
/*     */     }
/* 694 */     alphaNumericBitSet.or(alphaBitSet);
/* 695 */     alphaNumericBitSet.or(numericBitSet);
/*     */     
/* 697 */     protocolBitSet.or(alphaNumericBitSet);
/* 698 */     protocolBitSet.set(43);
/* 699 */     protocolBitSet.set(45);
/*     */     
/* 701 */     hostNameBitSet.or(alphaNumericBitSet);
/* 702 */     hostNameBitSet.set(45);
/* 703 */     hostNameBitSet.set(46);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int port;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String urlPath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient String toString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 731 */   private static final ClassLogger logger = new ClassLogger("javax.management.remote.misc", "JMXServiceURL");
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/remote/JMXServiceURL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */