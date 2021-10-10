/*      */ package java.net;
/*      */ 
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TimeZone;
/*      */ import sun.misc.JavaNetHttpCookieAccess;
/*      */ import sun.misc.SharedSecrets;
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
/*      */ public final class HttpCookie
/*      */   implements Cloneable
/*      */ {
/*      */   private final String name;
/*      */   private String value;
/*      */   private String comment;
/*      */   private String commentURL;
/*      */   private boolean toDiscard;
/*      */   private String domain;
/*   70 */   private long maxAge = -1L;
/*      */   private String path;
/*      */   private String portlist;
/*      */   private boolean secure;
/*      */   private boolean httpOnly;
/*   75 */   private int version = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   private final String header;
/*      */ 
/*      */ 
/*      */   
/*      */   private final long whenCreated;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long MAX_AGE_UNSPECIFIED = -1L;
/*      */ 
/*      */ 
/*      */   
/*   91 */   private static final String[] COOKIE_DATE_FORMATS = new String[] { "EEE',' dd-MMM-yyyy HH:mm:ss 'GMT'", "EEE',' dd MMM yyyy HH:mm:ss 'GMT'", "EEE MMM dd yyyy HH:mm:ss 'GMT'Z", "EEE',' dd-MMM-yy HH:mm:ss 'GMT'", "EEE',' dd MMM yy HH:mm:ss 'GMT'", "EEE MMM dd yy HH:mm:ss 'GMT'Z" };
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
/*      */   private static final String SET_COOKIE = "set-cookie:";
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
/*      */   private static final String SET_COOKIE2 = "set-cookie2:";
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
/*      */   private static final String tspecials = ",; ";
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
/*      */   public HttpCookie(String paramString1, String paramString2) {
/*  139 */     this(paramString1, paramString2, null);
/*      */   }
/*      */   
/*      */   private HttpCookie(String paramString1, String paramString2, String paramString3) {
/*  143 */     paramString1 = paramString1.trim();
/*  144 */     if (paramString1.length() == 0 || !isToken(paramString1) || paramString1.charAt(0) == '$') {
/*  145 */       throw new IllegalArgumentException("Illegal cookie name");
/*      */     }
/*      */     
/*  148 */     this.name = paramString1;
/*  149 */     this.value = paramString2;
/*  150 */     this.toDiscard = false;
/*  151 */     this.secure = false;
/*      */     
/*  153 */     this.whenCreated = System.currentTimeMillis();
/*  154 */     this.portlist = null;
/*  155 */     this.header = paramString3;
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
/*      */   public static List<HttpCookie> parse(String paramString) {
/*  178 */     return parse(paramString, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static List<HttpCookie> parse(String paramString, boolean paramBoolean) {
/*  187 */     int i = guessCookieVersion(paramString);
/*      */ 
/*      */     
/*  190 */     if (startsWithIgnoreCase(paramString, "set-cookie2:")) {
/*  191 */       paramString = paramString.substring("set-cookie2:".length());
/*  192 */     } else if (startsWithIgnoreCase(paramString, "set-cookie:")) {
/*  193 */       paramString = paramString.substring("set-cookie:".length());
/*      */     } 
/*      */     
/*  196 */     ArrayList<HttpCookie> arrayList = new ArrayList();
/*      */ 
/*      */ 
/*      */     
/*  200 */     if (i == 0) {
/*      */       
/*  202 */       HttpCookie httpCookie = parseInternal(paramString, paramBoolean);
/*  203 */       httpCookie.setVersion(0);
/*  204 */       arrayList.add(httpCookie);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  209 */       List<String> list = splitMultiCookies(paramString);
/*  210 */       for (String str : list) {
/*  211 */         HttpCookie httpCookie = parseInternal(str, paramBoolean);
/*  212 */         httpCookie.setVersion(1);
/*  213 */         arrayList.add(httpCookie);
/*      */       } 
/*      */     } 
/*      */     
/*  217 */     return arrayList;
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
/*      */   public boolean hasExpired() {
/*  229 */     if (this.maxAge == 0L) return true;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  234 */     if (this.maxAge == -1L) return false;
/*      */     
/*  236 */     long l = (System.currentTimeMillis() - this.whenCreated) / 1000L;
/*  237 */     if (l > this.maxAge) {
/*  238 */       return true;
/*      */     }
/*  240 */     return false;
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
/*      */   public void setComment(String paramString) {
/*  254 */     this.comment = paramString;
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
/*      */   public String getComment() {
/*  266 */     return this.comment;
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
/*      */   public void setCommentURL(String paramString) {
/*  280 */     this.commentURL = paramString;
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
/*      */   public String getCommentURL() {
/*  293 */     return this.commentURL;
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
/*      */   public void setDiscard(boolean paramBoolean) {
/*  306 */     this.toDiscard = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getDiscard() {
/*  317 */     return this.toDiscard;
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
/*      */   public void setPortlist(String paramString) {
/*  331 */     this.portlist = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPortlist() {
/*  342 */     return this.portlist;
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
/*      */   public void setDomain(String paramString) {
/*  362 */     if (paramString != null) {
/*  363 */       this.domain = paramString.toLowerCase();
/*      */     } else {
/*  365 */       this.domain = paramString;
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
/*      */   public String getDomain() {
/*  377 */     return this.domain;
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
/*      */   public void setMaxAge(long paramLong) {
/*  400 */     this.maxAge = paramLong;
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
/*      */   public long getMaxAge() {
/*  412 */     return this.maxAge;
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
/*      */   public void setPath(String paramString) {
/*  434 */     this.path = paramString;
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
/*      */   public String getPath() {
/*  447 */     return this.path;
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
/*      */   public void setSecure(boolean paramBoolean) {
/*  464 */     this.secure = paramBoolean;
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
/*      */   public boolean getSecure() {
/*  478 */     return this.secure;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  488 */     return this.name;
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
/*      */   public void setValue(String paramString) {
/*  506 */     this.value = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getValue() {
/*  517 */     return this.value;
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
/*      */   public int getVersion() {
/*  532 */     return this.version;
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
/*      */   public void setVersion(int paramInt) {
/*  550 */     if (paramInt != 0 && paramInt != 1) {
/*  551 */       throw new IllegalArgumentException("cookie version should be 0 or 1");
/*      */     }
/*      */     
/*  554 */     this.version = paramInt;
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
/*      */   public boolean isHttpOnly() {
/*  567 */     return this.httpOnly;
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
/*      */   public void setHttpOnly(boolean paramBoolean) {
/*  582 */     this.httpOnly = paramBoolean;
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
/*      */   public static boolean domainMatches(String paramString1, String paramString2) {
/*  636 */     if (paramString1 == null || paramString2 == null) {
/*  637 */       return false;
/*      */     }
/*      */     
/*  640 */     boolean bool = ".local".equalsIgnoreCase(paramString1);
/*  641 */     int i = paramString1.indexOf('.');
/*  642 */     if (i == 0)
/*  643 */       i = paramString1.indexOf('.', 1); 
/*  644 */     if (!bool && (i == -1 || i == paramString1
/*      */       
/*  646 */       .length() - 1)) {
/*  647 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  651 */     int j = paramString2.indexOf('.');
/*  652 */     if (j == -1 && (bool || paramString1
/*      */       
/*  654 */       .equalsIgnoreCase(paramString2 + ".local"))) {
/*  655 */       return true;
/*      */     }
/*      */     
/*  658 */     int k = paramString1.length();
/*  659 */     int m = paramString2.length() - k;
/*  660 */     if (m == 0)
/*      */     {
/*  662 */       return paramString2.equalsIgnoreCase(paramString1);
/*      */     }
/*  664 */     if (m > 0) {
/*      */       
/*  666 */       String str1 = paramString2.substring(0, m);
/*  667 */       String str2 = paramString2.substring(m);
/*      */       
/*  669 */       return (str1.indexOf('.') == -1 && str2.equalsIgnoreCase(paramString1));
/*      */     } 
/*  671 */     if (m == -1)
/*      */     {
/*  673 */       return (paramString1.charAt(0) == '.' && paramString2
/*  674 */         .equalsIgnoreCase(paramString1.substring(1)));
/*      */     }
/*      */     
/*  677 */     return false;
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
/*      */   public String toString() {
/*  689 */     if (getVersion() > 0) {
/*  690 */       return toRFC2965HeaderString();
/*      */     }
/*  692 */     return toNetscapeHeaderString();
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
/*      */   public boolean equals(Object paramObject) {
/*  708 */     if (paramObject == this)
/*  709 */       return true; 
/*  710 */     if (!(paramObject instanceof HttpCookie))
/*  711 */       return false; 
/*  712 */     HttpCookie httpCookie = (HttpCookie)paramObject;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  718 */     return (equalsIgnoreCase(getName(), httpCookie.getName()) && 
/*  719 */       equalsIgnoreCase(getDomain(), httpCookie.getDomain()) && 
/*  720 */       Objects.equals(getPath(), httpCookie.getPath()));
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
/*      */   public int hashCode() {
/*  737 */     int i = this.name.toLowerCase().hashCode();
/*  738 */     byte b1 = (this.domain != null) ? this.domain.toLowerCase().hashCode() : 0;
/*  739 */     byte b2 = (this.path != null) ? this.path.hashCode() : 0;
/*      */     
/*  741 */     return i + b1 + b2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/*      */     try {
/*  752 */       return super.clone();
/*  753 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*  754 */       throw new RuntimeException(cloneNotSupportedException.getMessage());
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
/*      */ 
/*      */   
/*      */   private static boolean isToken(String paramString) {
/*  776 */     int i = paramString.length();
/*      */     
/*  778 */     for (byte b = 0; b < i; b++) {
/*  779 */       char c = paramString.charAt(b);
/*      */       
/*  781 */       if (c < ' ' || c >= '' || ",; ".indexOf(c) != -1)
/*  782 */         return false; 
/*      */     } 
/*  784 */     return true;
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
/*      */   private static HttpCookie parseInternal(String paramString, boolean paramBoolean) {
/*  801 */     HttpCookie httpCookie = null;
/*  802 */     String str = null;
/*      */     
/*  804 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, ";");
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  809 */       str = stringTokenizer.nextToken();
/*  810 */       int i = str.indexOf('=');
/*  811 */       if (i != -1) {
/*  812 */         String str1 = str.substring(0, i).trim();
/*  813 */         String str2 = str.substring(i + 1).trim();
/*  814 */         if (paramBoolean) {
/*      */           
/*  816 */           httpCookie = new HttpCookie(str1, stripOffSurroundingQuote(str2), paramString);
/*      */         }
/*      */         else {
/*      */           
/*  820 */           httpCookie = new HttpCookie(str1, stripOffSurroundingQuote(str2));
/*      */         } 
/*      */       } else {
/*  823 */         throw new IllegalArgumentException("Invalid cookie name-value pair");
/*      */       } 
/*  825 */     } catch (NoSuchElementException noSuchElementException) {
/*  826 */       throw new IllegalArgumentException("Empty cookie header string");
/*      */     } 
/*      */ 
/*      */     
/*  830 */     while (stringTokenizer.hasMoreTokens()) {
/*  831 */       String str1, str2; str = stringTokenizer.nextToken();
/*  832 */       int i = str.indexOf('=');
/*      */       
/*  834 */       if (i != -1) {
/*  835 */         str1 = str.substring(0, i).trim();
/*  836 */         str2 = str.substring(i + 1).trim();
/*      */       } else {
/*  838 */         str1 = str.trim();
/*  839 */         str2 = null;
/*      */       } 
/*      */ 
/*      */       
/*  843 */       assignAttribute(httpCookie, str1, str2);
/*      */     } 
/*      */     
/*  846 */     return httpCookie;
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
/*  858 */   static final Map<String, CookieAttributeAssignor> assignors = new HashMap<>();
/*      */   
/*      */   static {
/*  861 */     assignors.put("comment", new CookieAttributeAssignor()
/*      */         {
/*      */           public void assign(HttpCookie param1HttpCookie, String param1String1, String param1String2)
/*      */           {
/*  865 */             if (param1HttpCookie.getComment() == null)
/*  866 */               param1HttpCookie.setComment(param1String2); 
/*      */           }
/*      */         });
/*  869 */     assignors.put("commenturl", new CookieAttributeAssignor()
/*      */         {
/*      */           public void assign(HttpCookie param1HttpCookie, String param1String1, String param1String2)
/*      */           {
/*  873 */             if (param1HttpCookie.getCommentURL() == null)
/*  874 */               param1HttpCookie.setCommentURL(param1String2); 
/*      */           }
/*      */         });
/*  877 */     assignors.put("discard", new CookieAttributeAssignor()
/*      */         {
/*      */           public void assign(HttpCookie param1HttpCookie, String param1String1, String param1String2)
/*      */           {
/*  881 */             param1HttpCookie.setDiscard(true);
/*      */           }
/*      */         });
/*  884 */     assignors.put("domain", new CookieAttributeAssignor()
/*      */         {
/*      */           public void assign(HttpCookie param1HttpCookie, String param1String1, String param1String2)
/*      */           {
/*  888 */             if (param1HttpCookie.getDomain() == null)
/*  889 */               param1HttpCookie.setDomain(param1String2); 
/*      */           }
/*      */         });
/*  892 */     assignors.put("max-age", new CookieAttributeAssignor()
/*      */         {
/*      */           public void assign(HttpCookie param1HttpCookie, String param1String1, String param1String2)
/*      */           {
/*      */             try {
/*  897 */               long l = Long.parseLong(param1String2);
/*  898 */               if (param1HttpCookie.getMaxAge() == -1L)
/*  899 */                 param1HttpCookie.setMaxAge(l); 
/*  900 */             } catch (NumberFormatException numberFormatException) {
/*  901 */               throw new IllegalArgumentException("Illegal cookie max-age attribute");
/*      */             } 
/*      */           }
/*      */         });
/*      */     
/*  906 */     assignors.put("path", new CookieAttributeAssignor()
/*      */         {
/*      */           public void assign(HttpCookie param1HttpCookie, String param1String1, String param1String2)
/*      */           {
/*  910 */             if (param1HttpCookie.getPath() == null)
/*  911 */               param1HttpCookie.setPath(param1String2); 
/*      */           }
/*      */         });
/*  914 */     assignors.put("port", new CookieAttributeAssignor()
/*      */         {
/*      */           public void assign(HttpCookie param1HttpCookie, String param1String1, String param1String2)
/*      */           {
/*  918 */             if (param1HttpCookie.getPortlist() == null)
/*  919 */               param1HttpCookie.setPortlist((param1String2 == null) ? "" : param1String2); 
/*      */           }
/*      */         });
/*  922 */     assignors.put("secure", new CookieAttributeAssignor()
/*      */         {
/*      */           public void assign(HttpCookie param1HttpCookie, String param1String1, String param1String2)
/*      */           {
/*  926 */             param1HttpCookie.setSecure(true);
/*      */           }
/*      */         });
/*  929 */     assignors.put("httponly", new CookieAttributeAssignor()
/*      */         {
/*      */           public void assign(HttpCookie param1HttpCookie, String param1String1, String param1String2)
/*      */           {
/*  933 */             param1HttpCookie.setHttpOnly(true);
/*      */           }
/*      */         });
/*  936 */     assignors.put("version", new CookieAttributeAssignor()
/*      */         {
/*      */           public void assign(HttpCookie param1HttpCookie, String param1String1, String param1String2)
/*      */           {
/*      */             try {
/*  941 */               int i = Integer.parseInt(param1String2);
/*  942 */               param1HttpCookie.setVersion(i);
/*  943 */             } catch (NumberFormatException numberFormatException) {}
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  948 */     assignors.put("expires", new CookieAttributeAssignor()
/*      */         {
/*      */           public void assign(HttpCookie param1HttpCookie, String param1String1, String param1String2)
/*      */           {
/*  952 */             if (param1HttpCookie.getMaxAge() == -1L) {
/*  953 */               param1HttpCookie.setMaxAge(param1HttpCookie.expiryDate2DeltaSeconds(param1String2));
/*      */             }
/*      */           }
/*      */         });
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
/*  974 */     SharedSecrets.setJavaNetHttpCookieAccess(new JavaNetHttpCookieAccess()
/*      */         {
/*      */           public List<HttpCookie> parse(String param1String) {
/*  977 */             return HttpCookie.parse(param1String, true);
/*      */           }
/*      */           
/*      */           public String header(HttpCookie param1HttpCookie) {
/*  981 */             return param1HttpCookie.header;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String header() {
/*  992 */     return this.header;
/*      */   } private static void assignAttribute(HttpCookie paramHttpCookie, String paramString1, String paramString2) {
/*      */     paramString2 = stripOffSurroundingQuote(paramString2);
/*      */     CookieAttributeAssignor cookieAttributeAssignor = assignors.get(paramString1.toLowerCase());
/*      */     if (cookieAttributeAssignor != null)
/*      */       cookieAttributeAssignor.assign(paramHttpCookie, paramString1, paramString2); 
/*      */   }
/*      */   private String toNetscapeHeaderString() {
/* 1000 */     return getName() + "=" + getValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String toRFC2965HeaderString() {
/* 1008 */     StringBuilder stringBuilder = new StringBuilder();
/*      */     
/* 1010 */     stringBuilder.append(getName()).append("=\"").append(getValue()).append('"');
/* 1011 */     if (getPath() != null)
/* 1012 */       stringBuilder.append(";$Path=\"").append(getPath()).append('"'); 
/* 1013 */     if (getDomain() != null)
/* 1014 */       stringBuilder.append(";$Domain=\"").append(getDomain()).append('"'); 
/* 1015 */     if (getPortlist() != null) {
/* 1016 */       stringBuilder.append(";$Port=\"").append(getPortlist()).append('"');
/*      */     }
/* 1018 */     return stringBuilder.toString();
/*      */   }
/*      */   
/* 1021 */   static final TimeZone GMT = TimeZone.getTimeZone("GMT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long expiryDate2DeltaSeconds(String paramString) {
/* 1031 */     GregorianCalendar gregorianCalendar = new GregorianCalendar(GMT);
/* 1032 */     for (byte b = 0; b < COOKIE_DATE_FORMATS.length; b++) {
/* 1033 */       SimpleDateFormat simpleDateFormat = new SimpleDateFormat(COOKIE_DATE_FORMATS[b], Locale.US);
/*      */       
/* 1035 */       gregorianCalendar.set(1970, 0, 1, 0, 0, 0);
/* 1036 */       simpleDateFormat.setTimeZone(GMT);
/* 1037 */       simpleDateFormat.setLenient(false);
/* 1038 */       simpleDateFormat.set2DigitYearStart(gregorianCalendar.getTime());
/*      */       try {
/* 1040 */         gregorianCalendar.setTime(simpleDateFormat.parse(paramString));
/* 1041 */         if (!COOKIE_DATE_FORMATS[b].contains("yyyy")) {
/*      */ 
/*      */           
/* 1044 */           int i = gregorianCalendar.get(1);
/* 1045 */           i %= 100;
/* 1046 */           if (i < 70) {
/* 1047 */             i += 2000;
/*      */           } else {
/* 1049 */             i += 1900;
/*      */           } 
/* 1051 */           gregorianCalendar.set(1, i);
/*      */         } 
/* 1053 */         return (gregorianCalendar.getTimeInMillis() - this.whenCreated) / 1000L;
/* 1054 */       } catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */     
/* 1058 */     return 0L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int guessCookieVersion(String paramString) {
/* 1065 */     boolean bool = false;
/*      */     
/* 1067 */     paramString = paramString.toLowerCase();
/* 1068 */     if (paramString.indexOf("expires=") != -1) {
/*      */       
/* 1070 */       bool = false;
/* 1071 */     } else if (paramString.indexOf("version=") != -1) {
/*      */       
/* 1073 */       bool = true;
/* 1074 */     } else if (paramString.indexOf("max-age") != -1) {
/*      */       
/* 1076 */       bool = true;
/* 1077 */     } else if (startsWithIgnoreCase(paramString, "set-cookie2:")) {
/*      */       
/* 1079 */       bool = true;
/*      */     } 
/*      */     
/* 1082 */     return bool;
/*      */   }
/*      */   
/*      */   private static String stripOffSurroundingQuote(String paramString) {
/* 1086 */     if (paramString != null && paramString.length() > 2 && paramString
/* 1087 */       .charAt(0) == '"' && paramString.charAt(paramString.length() - 1) == '"') {
/* 1088 */       return paramString.substring(1, paramString.length() - 1);
/*      */     }
/* 1090 */     if (paramString != null && paramString.length() > 2 && paramString
/* 1091 */       .charAt(0) == '\'' && paramString.charAt(paramString.length() - 1) == '\'') {
/* 1092 */       return paramString.substring(1, paramString.length() - 1);
/*      */     }
/* 1094 */     return paramString;
/*      */   }
/*      */   
/*      */   private static boolean equalsIgnoreCase(String paramString1, String paramString2) {
/* 1098 */     if (paramString1 == paramString2) return true; 
/* 1099 */     if (paramString1 != null && paramString2 != null) {
/* 1100 */       return paramString1.equalsIgnoreCase(paramString2);
/*      */     }
/* 1102 */     return false;
/*      */   }
/*      */   
/*      */   private static boolean startsWithIgnoreCase(String paramString1, String paramString2) {
/* 1106 */     if (paramString1 == null || paramString2 == null) return false;
/*      */     
/* 1108 */     if (paramString1.length() >= paramString2.length() && paramString2
/* 1109 */       .equalsIgnoreCase(paramString1.substring(0, paramString2.length()))) {
/* 1110 */       return true;
/*      */     }
/*      */     
/* 1113 */     return false;
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
/*      */   private static List<String> splitMultiCookies(String paramString) {
/* 1128 */     ArrayList<String> arrayList = new ArrayList();
/* 1129 */     byte b1 = 0;
/*      */     
/*      */     int i;
/* 1132 */     for (byte b2 = 0; b2 < paramString.length(); b2++) {
/* 1133 */       char c = paramString.charAt(b2);
/* 1134 */       if (c == '"') b1++; 
/* 1135 */       if (c == ',' && b1 % 2 == 0) {
/*      */         
/* 1137 */         arrayList.add(paramString.substring(i, b2));
/* 1138 */         i = b2 + 1;
/*      */       } 
/*      */     } 
/*      */     
/* 1142 */     arrayList.add(paramString.substring(i));
/*      */     
/* 1144 */     return arrayList;
/*      */   }
/*      */   
/*      */   static interface CookieAttributeAssignor {
/*      */     void assign(HttpCookie param1HttpCookie, String param1String1, String param1String2);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/HttpCookie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */