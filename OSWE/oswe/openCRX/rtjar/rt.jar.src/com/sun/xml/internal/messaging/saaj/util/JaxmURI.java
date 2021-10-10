/*      */ package com.sun.xml.internal.messaging.saaj.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.Serializable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JaxmURI
/*      */   implements Serializable
/*      */ {
/*      */   private static final String RESERVED_CHARACTERS = ";/?:@&=+$,";
/*      */   private static final String MARK_CHARACTERS = "-_.!~*'() ";
/*      */   private static final String SCHEME_CHARACTERS = "+-.";
/*      */   private static final String USERINFO_CHARACTERS = ";:&=+$,";
/*      */   
/*      */   public static class MalformedURIException
/*      */     extends IOException
/*      */   {
/*      */     public MalformedURIException() {}
/*      */     
/*      */     public MalformedURIException(String p_msg) {
/*   92 */       super(p_msg);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  111 */   private String m_scheme = null;
/*      */ 
/*      */   
/*  114 */   private String m_userinfo = null;
/*      */ 
/*      */   
/*  117 */   private String m_host = null;
/*      */ 
/*      */   
/*  120 */   private int m_port = -1;
/*      */ 
/*      */   
/*  123 */   private String m_path = null;
/*      */ 
/*      */ 
/*      */   
/*  127 */   private String m_queryString = null;
/*      */ 
/*      */   
/*  130 */   private String m_fragment = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean DEBUG = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JaxmURI() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JaxmURI(JaxmURI p_other) {
/*  147 */     initialize(p_other);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JaxmURI(String p_uriSpec) throws MalformedURIException {
/*  166 */     this((JaxmURI)null, p_uriSpec);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JaxmURI(JaxmURI p_base, String p_uriSpec) throws MalformedURIException {
/*  182 */     initialize(p_base, p_uriSpec);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JaxmURI(String p_scheme, String p_schemeSpecificPart) throws MalformedURIException {
/*  199 */     if (p_scheme == null || p_scheme.trim().length() == 0) {
/*  200 */       throw new MalformedURIException("Cannot construct URI with null/empty scheme!");
/*      */     }
/*      */     
/*  203 */     if (p_schemeSpecificPart == null || p_schemeSpecificPart
/*  204 */       .trim().length() == 0) {
/*  205 */       throw new MalformedURIException("Cannot construct URI with null/empty scheme-specific part!");
/*      */     }
/*      */     
/*  208 */     setScheme(p_scheme);
/*  209 */     setPath(p_schemeSpecificPart);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JaxmURI(String p_scheme, String p_host, String p_path, String p_queryString, String p_fragment) throws MalformedURIException {
/*  236 */     this(p_scheme, null, p_host, -1, p_path, p_queryString, p_fragment);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JaxmURI(String p_scheme, String p_userinfo, String p_host, int p_port, String p_path, String p_queryString, String p_fragment) throws MalformedURIException {
/*  268 */     if (p_scheme == null || p_scheme.trim().length() == 0) {
/*  269 */       throw new MalformedURIException("Scheme is required!");
/*      */     }
/*      */     
/*  272 */     if (p_host == null) {
/*  273 */       if (p_userinfo != null) {
/*  274 */         throw new MalformedURIException("Userinfo may not be specified if host is not specified!");
/*      */       }
/*      */       
/*  277 */       if (p_port != -1) {
/*  278 */         throw new MalformedURIException("Port may not be specified if host is not specified!");
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  283 */     if (p_path != null) {
/*  284 */       if (p_path.indexOf('?') != -1 && p_queryString != null) {
/*  285 */         throw new MalformedURIException("Query string cannot be specified in path and query string!");
/*      */       }
/*      */ 
/*      */       
/*  289 */       if (p_path.indexOf('#') != -1 && p_fragment != null) {
/*  290 */         throw new MalformedURIException("Fragment cannot be specified in both the path and fragment!");
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  295 */     setScheme(p_scheme);
/*  296 */     setHost(p_host);
/*  297 */     setPort(p_port);
/*  298 */     setUserinfo(p_userinfo);
/*  299 */     setPath(p_path);
/*  300 */     setQueryString(p_queryString);
/*  301 */     setFragment(p_fragment);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initialize(JaxmURI p_other) {
/*  310 */     this.m_scheme = p_other.getScheme();
/*  311 */     this.m_userinfo = p_other.getUserinfo();
/*  312 */     this.m_host = p_other.getHost();
/*  313 */     this.m_port = p_other.getPort();
/*  314 */     this.m_path = p_other.getPath();
/*  315 */     this.m_queryString = p_other.getQueryString();
/*  316 */     this.m_fragment = p_other.getFragment();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initialize(JaxmURI p_base, String p_uriSpec) throws MalformedURIException {
/*  337 */     if (p_base == null && (p_uriSpec == null || p_uriSpec
/*  338 */       .trim().length() == 0)) {
/*  339 */       throw new MalformedURIException("Cannot initialize URI with empty parameters.");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  344 */     if (p_uriSpec == null || p_uriSpec.trim().length() == 0) {
/*  345 */       initialize(p_base);
/*      */       
/*      */       return;
/*      */     } 
/*  349 */     String uriSpec = p_uriSpec.trim();
/*  350 */     int uriSpecLen = uriSpec.length();
/*  351 */     int index = 0;
/*      */ 
/*      */ 
/*      */     
/*  355 */     int colonIdx = uriSpec.indexOf(':');
/*  356 */     int slashIdx = uriSpec.indexOf('/');
/*  357 */     if (colonIdx < 2 || (colonIdx > slashIdx && slashIdx != -1)) {
/*  358 */       int fragmentIdx = uriSpec.indexOf('#');
/*      */       
/*  360 */       if (p_base == null && fragmentIdx != 0) {
/*  361 */         throw new MalformedURIException("No scheme found in URI.");
/*      */       }
/*      */     } else {
/*      */       
/*  365 */       initializeScheme(uriSpec);
/*  366 */       index = this.m_scheme.length() + 1;
/*      */     } 
/*      */ 
/*      */     
/*  370 */     if (index + 1 < uriSpecLen && uriSpec
/*  371 */       .substring(index).startsWith("//")) {
/*  372 */       index += 2;
/*  373 */       int startPos = index;
/*      */ 
/*      */       
/*  376 */       char testChar = Character.MIN_VALUE;
/*  377 */       while (index < uriSpecLen) {
/*  378 */         testChar = uriSpec.charAt(index);
/*  379 */         if (testChar == '/' || testChar == '?' || testChar == '#') {
/*      */           break;
/*      */         }
/*  382 */         index++;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  387 */       if (index > startPos) {
/*  388 */         initializeAuthority(uriSpec.substring(startPos, index));
/*      */       } else {
/*      */         
/*  391 */         this.m_host = "";
/*      */       } 
/*      */     } 
/*      */     
/*  395 */     initializePath(uriSpec.substring(index));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  402 */     if (p_base != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  411 */       if (this.m_path.length() == 0 && this.m_scheme == null && this.m_host == null) {
/*      */         
/*  413 */         this.m_scheme = p_base.getScheme();
/*  414 */         this.m_userinfo = p_base.getUserinfo();
/*  415 */         this.m_host = p_base.getHost();
/*  416 */         this.m_port = p_base.getPort();
/*  417 */         this.m_path = p_base.getPath();
/*      */         
/*  419 */         if (this.m_queryString == null) {
/*  420 */           this.m_queryString = p_base.getQueryString();
/*      */         }
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  427 */       if (this.m_scheme == null) {
/*  428 */         this.m_scheme = p_base.getScheme();
/*      */       } else {
/*      */         return;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  436 */       if (this.m_host == null) {
/*  437 */         this.m_userinfo = p_base.getUserinfo();
/*  438 */         this.m_host = p_base.getHost();
/*  439 */         this.m_port = p_base.getPort();
/*      */       } else {
/*      */         return;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  446 */       if (this.m_path.length() > 0 && this.m_path
/*  447 */         .startsWith("/")) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  453 */       String path = "";
/*  454 */       String basePath = p_base.getPath();
/*      */ 
/*      */       
/*  457 */       if (basePath != null) {
/*  458 */         int lastSlash = basePath.lastIndexOf('/');
/*  459 */         if (lastSlash != -1) {
/*  460 */           path = basePath.substring(0, lastSlash + 1);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  465 */       path = path.concat(this.m_path);
/*      */ 
/*      */       
/*  468 */       index = -1;
/*  469 */       while ((index = path.indexOf("/./")) != -1) {
/*  470 */         path = path.substring(0, index + 1).concat(path.substring(index + 3));
/*      */       }
/*      */ 
/*      */       
/*  474 */       if (path.endsWith("/.")) {
/*  475 */         path = path.substring(0, path.length() - 1);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  480 */       index = 1;
/*  481 */       int segIndex = -1;
/*  482 */       String tempString = null;
/*      */       
/*  484 */       while ((index = path.indexOf("/../", index)) > 0) {
/*  485 */         tempString = path.substring(0, path.indexOf("/../"));
/*  486 */         segIndex = tempString.lastIndexOf('/');
/*  487 */         if (segIndex != -1) {
/*  488 */           if (!tempString.substring(segIndex++).equals("..")) {
/*  489 */             path = path.substring(0, segIndex).concat(path.substring(index + 4));
/*      */             continue;
/*      */           } 
/*  492 */           index += 4;
/*      */           continue;
/*      */         } 
/*  495 */         index += 4;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  500 */       if (path.endsWith("/..")) {
/*  501 */         tempString = path.substring(0, path.length() - 3);
/*  502 */         segIndex = tempString.lastIndexOf('/');
/*  503 */         if (segIndex != -1) {
/*  504 */           path = path.substring(0, segIndex + 1);
/*      */         }
/*      */       } 
/*  507 */       this.m_path = path;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initializeScheme(String p_uriSpec) throws MalformedURIException {
/*  521 */     int uriSpecLen = p_uriSpec.length();
/*  522 */     int index = 0;
/*  523 */     String scheme = null;
/*  524 */     char testChar = Character.MIN_VALUE;
/*      */     
/*  526 */     while (index < uriSpecLen) {
/*  527 */       testChar = p_uriSpec.charAt(index);
/*  528 */       if (testChar == ':' || testChar == '/' || testChar == '?' || testChar == '#') {
/*      */         break;
/*      */       }
/*      */       
/*  532 */       index++;
/*      */     } 
/*  534 */     scheme = p_uriSpec.substring(0, index);
/*      */     
/*  536 */     if (scheme.length() == 0) {
/*  537 */       throw new MalformedURIException("No scheme found in URI.");
/*      */     }
/*      */     
/*  540 */     setScheme(scheme);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initializeAuthority(String p_uriSpec) throws MalformedURIException {
/*  554 */     int index = 0;
/*  555 */     int start = 0;
/*  556 */     int end = p_uriSpec.length();
/*  557 */     char testChar = Character.MIN_VALUE;
/*  558 */     String userinfo = null;
/*      */ 
/*      */     
/*  561 */     if (p_uriSpec.indexOf('@', start) != -1) {
/*  562 */       while (index < end) {
/*  563 */         testChar = p_uriSpec.charAt(index);
/*  564 */         if (testChar == '@') {
/*      */           break;
/*      */         }
/*  567 */         index++;
/*      */       } 
/*  569 */       userinfo = p_uriSpec.substring(start, index);
/*  570 */       index++;
/*      */     } 
/*      */ 
/*      */     
/*  574 */     String host = null;
/*  575 */     start = index;
/*  576 */     while (index < end) {
/*  577 */       testChar = p_uriSpec.charAt(index);
/*  578 */       if (testChar == ':') {
/*      */         break;
/*      */       }
/*  581 */       index++;
/*      */     } 
/*  583 */     host = p_uriSpec.substring(start, index);
/*  584 */     int port = -1;
/*  585 */     if (host.length() > 0)
/*      */     {
/*  587 */       if (testChar == ':') {
/*      */         
/*  589 */         start = ++index;
/*  590 */         while (index < end) {
/*  591 */           index++;
/*      */         }
/*  593 */         String portStr = p_uriSpec.substring(start, index);
/*  594 */         if (portStr.length() > 0) {
/*  595 */           for (int i = 0; i < portStr.length(); i++) {
/*  596 */             if (!isDigit(portStr.charAt(i))) {
/*  597 */               throw new MalformedURIException(portStr + " is invalid. Port should only contain digits!");
/*      */             }
/*      */           } 
/*      */ 
/*      */           
/*      */           try {
/*  603 */             port = Integer.parseInt(portStr);
/*      */           }
/*  605 */           catch (NumberFormatException numberFormatException) {}
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  611 */     setHost(host);
/*  612 */     setPort(port);
/*  613 */     setUserinfo(userinfo);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initializePath(String p_uriSpec) throws MalformedURIException {
/*  625 */     if (p_uriSpec == null) {
/*  626 */       throw new MalformedURIException("Cannot initialize path from null string!");
/*      */     }
/*      */ 
/*      */     
/*  630 */     int index = 0;
/*  631 */     int start = 0;
/*  632 */     int end = p_uriSpec.length();
/*  633 */     char testChar = Character.MIN_VALUE;
/*      */ 
/*      */     
/*  636 */     while (index < end) {
/*  637 */       testChar = p_uriSpec.charAt(index);
/*  638 */       if (testChar == '?' || testChar == '#') {
/*      */         break;
/*      */       }
/*      */       
/*  642 */       if (testChar == '%') {
/*  643 */         if (index + 2 >= end || 
/*  644 */           !isHex(p_uriSpec.charAt(index + 1)) || 
/*  645 */           !isHex(p_uriSpec.charAt(index + 2))) {
/*  646 */           throw new MalformedURIException("Path contains invalid escape sequence!");
/*      */         
/*      */         }
/*      */       }
/*  650 */       else if (!isReservedCharacter(testChar) && 
/*  651 */         !isUnreservedCharacter(testChar)) {
/*  652 */         throw new MalformedURIException("Path contains invalid character: " + testChar);
/*      */       } 
/*      */       
/*  655 */       index++;
/*      */     } 
/*  657 */     this.m_path = p_uriSpec.substring(start, index);
/*      */ 
/*      */     
/*  660 */     if (testChar == '?') {
/*      */       
/*  662 */       start = ++index;
/*  663 */       while (index < end) {
/*  664 */         testChar = p_uriSpec.charAt(index);
/*  665 */         if (testChar == '#') {
/*      */           break;
/*      */         }
/*  668 */         if (testChar == '%') {
/*  669 */           if (index + 2 >= end || 
/*  670 */             !isHex(p_uriSpec.charAt(index + 1)) || 
/*  671 */             !isHex(p_uriSpec.charAt(index + 2))) {
/*  672 */             throw new MalformedURIException("Query string contains invalid escape sequence!");
/*      */           
/*      */           }
/*      */         }
/*  676 */         else if (!isReservedCharacter(testChar) && 
/*  677 */           !isUnreservedCharacter(testChar)) {
/*  678 */           throw new MalformedURIException("Query string contains invalid character:" + testChar);
/*      */         } 
/*      */         
/*  681 */         index++;
/*      */       } 
/*  683 */       this.m_queryString = p_uriSpec.substring(start, index);
/*      */     } 
/*      */ 
/*      */     
/*  687 */     if (testChar == '#') {
/*      */       
/*  689 */       start = ++index;
/*  690 */       while (index < end) {
/*  691 */         testChar = p_uriSpec.charAt(index);
/*      */         
/*  693 */         if (testChar == '%') {
/*  694 */           if (index + 2 >= end || 
/*  695 */             !isHex(p_uriSpec.charAt(index + 1)) || 
/*  696 */             !isHex(p_uriSpec.charAt(index + 2))) {
/*  697 */             throw new MalformedURIException("Fragment contains invalid escape sequence!");
/*      */           
/*      */           }
/*      */         }
/*  701 */         else if (!isReservedCharacter(testChar) && 
/*  702 */           !isUnreservedCharacter(testChar)) {
/*  703 */           throw new MalformedURIException("Fragment contains invalid character:" + testChar);
/*      */         } 
/*      */         
/*  706 */         index++;
/*      */       } 
/*  708 */       this.m_fragment = p_uriSpec.substring(start, index);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getScheme() {
/*  718 */     return this.m_scheme;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSchemeSpecificPart() {
/*  728 */     StringBuffer schemespec = new StringBuffer();
/*      */     
/*  730 */     if (this.m_userinfo != null || this.m_host != null || this.m_port != -1) {
/*  731 */       schemespec.append("//");
/*      */     }
/*      */     
/*  734 */     if (this.m_userinfo != null) {
/*  735 */       schemespec.append(this.m_userinfo);
/*  736 */       schemespec.append('@');
/*      */     } 
/*      */     
/*  739 */     if (this.m_host != null) {
/*  740 */       schemespec.append(this.m_host);
/*      */     }
/*      */     
/*  743 */     if (this.m_port != -1) {
/*  744 */       schemespec.append(':');
/*  745 */       schemespec.append(this.m_port);
/*      */     } 
/*      */     
/*  748 */     if (this.m_path != null) {
/*  749 */       schemespec.append(this.m_path);
/*      */     }
/*      */     
/*  752 */     if (this.m_queryString != null) {
/*  753 */       schemespec.append('?');
/*  754 */       schemespec.append(this.m_queryString);
/*      */     } 
/*      */     
/*  757 */     if (this.m_fragment != null) {
/*  758 */       schemespec.append('#');
/*  759 */       schemespec.append(this.m_fragment);
/*      */     } 
/*      */     
/*  762 */     return schemespec.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUserinfo() {
/*  771 */     return this.m_userinfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getHost() {
/*  780 */     return this.m_host;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPort() {
/*  789 */     return this.m_port;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPath(boolean p_includeQueryString, boolean p_includeFragment) {
/*  808 */     StringBuffer pathString = new StringBuffer(this.m_path);
/*      */     
/*  810 */     if (p_includeQueryString && this.m_queryString != null) {
/*  811 */       pathString.append('?');
/*  812 */       pathString.append(this.m_queryString);
/*      */     } 
/*      */     
/*  815 */     if (p_includeFragment && this.m_fragment != null) {
/*  816 */       pathString.append('#');
/*  817 */       pathString.append(this.m_fragment);
/*      */     } 
/*  819 */     return pathString.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPath() {
/*  829 */     return this.m_path;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getQueryString() {
/*  840 */     return this.m_queryString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFragment() {
/*  851 */     return this.m_fragment;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setScheme(String p_scheme) throws MalformedURIException {
/*  864 */     if (p_scheme == null) {
/*  865 */       throw new MalformedURIException("Cannot set scheme from null string!");
/*      */     }
/*      */     
/*  868 */     if (!isConformantSchemeName(p_scheme)) {
/*  869 */       throw new MalformedURIException("The scheme is not conformant.");
/*      */     }
/*      */     
/*  872 */     this.m_scheme = p_scheme.toLowerCase();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUserinfo(String p_userinfo) throws MalformedURIException {
/*  885 */     if (p_userinfo == null) {
/*  886 */       this.m_userinfo = null;
/*      */     } else {
/*      */       
/*  889 */       if (this.m_host == null) {
/*  890 */         throw new MalformedURIException("Userinfo cannot be set when host is null!");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  896 */       int index = 0;
/*  897 */       int end = p_userinfo.length();
/*  898 */       char testChar = Character.MIN_VALUE;
/*  899 */       while (index < end) {
/*  900 */         testChar = p_userinfo.charAt(index);
/*  901 */         if (testChar == '%') {
/*  902 */           if (index + 2 >= end || 
/*  903 */             !isHex(p_userinfo.charAt(index + 1)) || 
/*  904 */             !isHex(p_userinfo.charAt(index + 2))) {
/*  905 */             throw new MalformedURIException("Userinfo contains invalid escape sequence!");
/*      */           
/*      */           }
/*      */         }
/*  909 */         else if (!isUnreservedCharacter(testChar) && ";:&=+$,"
/*  910 */           .indexOf(testChar) == -1) {
/*  911 */           throw new MalformedURIException("Userinfo contains invalid character:" + testChar);
/*      */         } 
/*      */         
/*  914 */         index++;
/*      */       } 
/*      */     } 
/*  917 */     this.m_userinfo = p_userinfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHost(String p_host) throws MalformedURIException {
/*  930 */     if (p_host == null || p_host.trim().length() == 0) {
/*  931 */       this.m_host = p_host;
/*  932 */       this.m_userinfo = null;
/*  933 */       this.m_port = -1;
/*      */     }
/*  935 */     else if (!isWellFormedAddress(p_host)) {
/*  936 */       throw new MalformedURIException("Host is not a well formed address!");
/*      */     } 
/*  938 */     this.m_host = p_host;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPort(int p_port) throws MalformedURIException {
/*  953 */     if (p_port >= 0 && p_port <= 65535) {
/*  954 */       if (this.m_host == null) {
/*  955 */         throw new MalformedURIException("Port cannot be set when host is null!");
/*      */       
/*      */       }
/*      */     }
/*  959 */     else if (p_port != -1) {
/*  960 */       throw new MalformedURIException("Invalid port number!");
/*      */     } 
/*  962 */     this.m_port = p_port;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPath(String p_path) throws MalformedURIException {
/*  980 */     if (p_path == null) {
/*  981 */       this.m_path = null;
/*  982 */       this.m_queryString = null;
/*  983 */       this.m_fragment = null;
/*      */     } else {
/*      */       
/*  986 */       initializePath(p_path);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void appendPath(String p_addToPath) throws MalformedURIException {
/* 1005 */     if (p_addToPath == null || p_addToPath.trim().length() == 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1009 */     if (!isURIString(p_addToPath)) {
/* 1010 */       throw new MalformedURIException("Path contains invalid character!");
/*      */     }
/*      */ 
/*      */     
/* 1014 */     if (this.m_path == null || this.m_path.trim().length() == 0) {
/* 1015 */       if (p_addToPath.startsWith("/")) {
/* 1016 */         this.m_path = p_addToPath;
/*      */       } else {
/*      */         
/* 1019 */         this.m_path = "/" + p_addToPath;
/*      */       }
/*      */     
/* 1022 */     } else if (this.m_path.endsWith("/")) {
/* 1023 */       if (p_addToPath.startsWith("/")) {
/* 1024 */         this.m_path = this.m_path.concat(p_addToPath.substring(1));
/*      */       } else {
/*      */         
/* 1027 */         this.m_path = this.m_path.concat(p_addToPath);
/*      */       }
/*      */     
/*      */     }
/* 1031 */     else if (p_addToPath.startsWith("/")) {
/* 1032 */       this.m_path = this.m_path.concat(p_addToPath);
/*      */     } else {
/*      */       
/* 1035 */       this.m_path = this.m_path.concat("/" + p_addToPath);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setQueryString(String p_queryString) throws MalformedURIException {
/* 1052 */     if (p_queryString == null) {
/* 1053 */       this.m_queryString = null;
/*      */     } else {
/* 1055 */       if (!isGenericURI()) {
/* 1056 */         throw new MalformedURIException("Query string can only be set for a generic URI!");
/*      */       }
/*      */       
/* 1059 */       if (getPath() == null) {
/* 1060 */         throw new MalformedURIException("Query string cannot be set when path is null!");
/*      */       }
/*      */       
/* 1063 */       if (!isURIString(p_queryString)) {
/* 1064 */         throw new MalformedURIException("Query string contains invalid character!");
/*      */       }
/*      */ 
/*      */       
/* 1068 */       this.m_queryString = p_queryString;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFragment(String p_fragment) throws MalformedURIException {
/* 1084 */     if (p_fragment == null) {
/* 1085 */       this.m_fragment = null;
/*      */     } else {
/* 1087 */       if (!isGenericURI()) {
/* 1088 */         throw new MalformedURIException("Fragment can only be set for a generic URI!");
/*      */       }
/*      */       
/* 1091 */       if (getPath() == null) {
/* 1092 */         throw new MalformedURIException("Fragment cannot be set when path is null!");
/*      */       }
/*      */       
/* 1095 */       if (!isURIString(p_fragment)) {
/* 1096 */         throw new MalformedURIException("Fragment contains invalid character!");
/*      */       }
/*      */ 
/*      */       
/* 1100 */       this.m_fragment = p_fragment;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object p_test) {
/* 1113 */     if (p_test instanceof JaxmURI) {
/* 1114 */       JaxmURI testURI = (JaxmURI)p_test;
/* 1115 */       if (((this.m_scheme == null && testURI.m_scheme == null) || (this.m_scheme != null && testURI.m_scheme != null && this.m_scheme
/*      */         
/* 1117 */         .equals(testURI.m_scheme))) && ((this.m_userinfo == null && testURI.m_userinfo == null) || (this.m_userinfo != null && testURI.m_userinfo != null && this.m_userinfo
/*      */ 
/*      */         
/* 1120 */         .equals(testURI.m_userinfo))) && ((this.m_host == null && testURI.m_host == null) || (this.m_host != null && testURI.m_host != null && this.m_host
/*      */ 
/*      */         
/* 1123 */         .equals(testURI.m_host))) && this.m_port == testURI.m_port && ((this.m_path == null && testURI.m_path == null) || (this.m_path != null && testURI.m_path != null && this.m_path
/*      */ 
/*      */ 
/*      */         
/* 1127 */         .equals(testURI.m_path))) && ((this.m_queryString == null && testURI.m_queryString == null) || (this.m_queryString != null && testURI.m_queryString != null && this.m_queryString
/*      */ 
/*      */         
/* 1130 */         .equals(testURI.m_queryString))) && ((this.m_fragment == null && testURI.m_fragment == null) || (this.m_fragment != null && testURI.m_fragment != null && this.m_fragment
/*      */ 
/*      */         
/* 1133 */         .equals(testURI.m_fragment)))) {
/* 1134 */         return true;
/*      */       }
/*      */     } 
/* 1137 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1142 */     return 153214;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1151 */     StringBuffer uriSpecString = new StringBuffer();
/*      */     
/* 1153 */     if (this.m_scheme != null) {
/* 1154 */       uriSpecString.append(this.m_scheme);
/* 1155 */       uriSpecString.append(':');
/*      */     } 
/* 1157 */     uriSpecString.append(getSchemeSpecificPart());
/* 1158 */     return uriSpecString.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isGenericURI() {
/* 1171 */     return (this.m_host != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isConformantSchemeName(String p_scheme) {
/* 1182 */     if (p_scheme == null || p_scheme.trim().length() == 0) {
/* 1183 */       return false;
/*      */     }
/*      */     
/* 1186 */     if (!isAlpha(p_scheme.charAt(0))) {
/* 1187 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1191 */     for (int i = 1; i < p_scheme.length(); i++) {
/* 1192 */       char testChar = p_scheme.charAt(i);
/* 1193 */       if (!isAlphanum(testChar) && "+-."
/* 1194 */         .indexOf(testChar) == -1) {
/* 1195 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1199 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isWellFormedAddress(String p_address) {
/* 1214 */     if (p_address == null) {
/* 1215 */       return false;
/*      */     }
/*      */     
/* 1218 */     String address = p_address.trim();
/* 1219 */     int addrLength = address.length();
/* 1220 */     if (addrLength == 0 || addrLength > 255) {
/* 1221 */       return false;
/*      */     }
/*      */     
/* 1224 */     if (address.startsWith(".") || address.startsWith("-")) {
/* 1225 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1231 */     int index = address.lastIndexOf('.');
/* 1232 */     if (address.endsWith(".")) {
/* 1233 */       index = address.substring(0, index).lastIndexOf('.');
/*      */     }
/*      */     
/* 1236 */     if (index + 1 < addrLength && isDigit(p_address.charAt(index + 1))) {
/*      */       
/* 1238 */       int numDots = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1243 */       for (int i = 0; i < addrLength; i++) {
/* 1244 */         char testChar = address.charAt(i);
/* 1245 */         if (testChar == '.') {
/* 1246 */           if (!isDigit(address.charAt(i - 1)) || (i + 1 < addrLength && 
/* 1247 */             !isDigit(address.charAt(i + 1)))) {
/* 1248 */             return false;
/*      */           }
/* 1250 */           numDots++;
/*      */         }
/* 1252 */         else if (!isDigit(testChar)) {
/* 1253 */           return false;
/*      */         } 
/*      */       } 
/* 1256 */       if (numDots != 3) {
/* 1257 */         return false;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1265 */       for (int i = 0; i < addrLength; i++) {
/* 1266 */         char testChar = address.charAt(i);
/* 1267 */         if (testChar == '.') {
/* 1268 */           if (!isAlphanum(address.charAt(i - 1))) {
/* 1269 */             return false;
/*      */           }
/* 1271 */           if (i + 1 < addrLength && !isAlphanum(address.charAt(i + 1))) {
/* 1272 */             return false;
/*      */           }
/*      */         }
/* 1275 */         else if (!isAlphanum(testChar) && testChar != '-') {
/* 1276 */           return false;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1280 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isDigit(char p_char) {
/* 1290 */     return (p_char >= '0' && p_char <= '9');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isHex(char p_char) {
/* 1300 */     return (isDigit(p_char) || (p_char >= 'a' && p_char <= 'f') || (p_char >= 'A' && p_char <= 'F'));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAlpha(char p_char) {
/* 1311 */     return ((p_char >= 'a' && p_char <= 'z') || (p_char >= 'A' && p_char <= 'Z'));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAlphanum(char p_char) {
/* 1321 */     return (isAlpha(p_char) || isDigit(p_char));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isReservedCharacter(char p_char) {
/* 1331 */     return (";/?:@&=+$,".indexOf(p_char) != -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isUnreservedCharacter(char p_char) {
/* 1340 */     return (isAlphanum(p_char) || "-_.!~*'() "
/* 1341 */       .indexOf(p_char) != -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isURIString(String p_uric) {
/* 1352 */     if (p_uric == null) {
/* 1353 */       return false;
/*      */     }
/* 1355 */     int end = p_uric.length();
/* 1356 */     char testChar = Character.MIN_VALUE;
/* 1357 */     for (int i = 0; i < end; i++) {
/* 1358 */       testChar = p_uric.charAt(i);
/* 1359 */       if (testChar == '%') {
/* 1360 */         if (i + 2 >= end || 
/* 1361 */           !isHex(p_uric.charAt(i + 1)) || 
/* 1362 */           !isHex(p_uric.charAt(i + 2))) {
/* 1363 */           return false;
/*      */         }
/*      */         
/* 1366 */         i += 2;
/*      */ 
/*      */       
/*      */       }
/* 1370 */       else if (!isReservedCharacter(testChar) && 
/* 1371 */         !isUnreservedCharacter(testChar)) {
/*      */ 
/*      */ 
/*      */         
/* 1375 */         return false;
/*      */       } 
/*      */     } 
/* 1378 */     return true;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/util/JaxmURI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */