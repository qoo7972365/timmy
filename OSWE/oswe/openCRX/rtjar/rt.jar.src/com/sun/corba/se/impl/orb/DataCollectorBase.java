/*     */ package com.sun.corba.se.impl.orb;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.GetPropertyAction;
/*     */ import com.sun.corba.se.spi.orb.DataCollector;
/*     */ import com.sun.corba.se.spi.orb.PropertyParser;
/*     */ import java.applet.Applet;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
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
/*     */ public abstract class DataCollectorBase
/*     */   implements DataCollector
/*     */ {
/*     */   private PropertyParser parser;
/*     */   private Set propertyNames;
/*     */   private Set propertyPrefixes;
/*     */   private Set URLPropertyNames;
/*     */   protected String localHostName;
/*     */   protected String configurationHostName;
/*     */   private boolean setParserCalled;
/*     */   private Properties originalProps;
/*     */   private Properties resultProps;
/*     */   
/*     */   public DataCollectorBase(Properties paramProperties, String paramString1, String paramString2) {
/*  72 */     this.URLPropertyNames = new HashSet();
/*  73 */     this.URLPropertyNames.add("org.omg.CORBA.ORBInitialServices");
/*     */     
/*  75 */     this.propertyNames = new HashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     this.propertyNames.add("org.omg.CORBA.ORBInitRef");
/*     */     
/*  82 */     this.propertyPrefixes = new HashSet();
/*     */     
/*  84 */     this.originalProps = paramProperties;
/*  85 */     this.localHostName = paramString1;
/*  86 */     this.configurationHostName = paramString2;
/*  87 */     this.setParserCalled = false;
/*  88 */     this.resultProps = new Properties();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean initialHostIsLocal() {
/*  97 */     checkSetParserCalled();
/*  98 */     return this.localHostName.equals(this.resultProps.getProperty("org.omg.CORBA.ORBInitialHost"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParser(PropertyParser paramPropertyParser) {
/* 104 */     Iterator<ParserAction> iterator = paramPropertyParser.iterator();
/* 105 */     while (iterator.hasNext()) {
/* 106 */       ParserAction parserAction = iterator.next();
/* 107 */       if (parserAction.isPrefix()) {
/* 108 */         this.propertyPrefixes.add(parserAction.getPropertyName()); continue;
/*     */       } 
/* 110 */       this.propertyNames.add(parserAction.getPropertyName());
/*     */     } 
/*     */     
/* 113 */     collect();
/* 114 */     this.setParserCalled = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Properties getProperties() {
/* 119 */     checkSetParserCalled();
/* 120 */     return this.resultProps;
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
/*     */   protected void checkPropertyDefaults() {
/* 143 */     String str1 = this.resultProps.getProperty("org.omg.CORBA.ORBInitialHost");
/*     */     
/* 145 */     if (str1 == null || str1.equals("")) {
/* 146 */       setProperty("org.omg.CORBA.ORBInitialHost", this.configurationHostName);
/*     */     }
/*     */ 
/*     */     
/* 150 */     String str2 = this.resultProps.getProperty("com.sun.CORBA.ORBServerHost");
/*     */     
/* 152 */     if (str2 == null || str2
/* 153 */       .equals("") || str2
/* 154 */       .equals("0.0.0.0") || str2
/* 155 */       .equals("::") || str2
/* 156 */       .toLowerCase().equals("::ffff:0.0.0.0")) {
/*     */       
/* 158 */       setProperty("com.sun.CORBA.ORBServerHost", this.localHostName);
/*     */       
/* 160 */       setProperty("com.sun.CORBA.INTERNAL USE ONLY: listen on all interfaces", "com.sun.CORBA.INTERNAL USE ONLY: listen on all interfaces");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void findPropertiesFromArgs(String[] paramArrayOfString) {
/* 167 */     if (paramArrayOfString == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 176 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 177 */       String str2 = null;
/* 178 */       String str1 = null;
/*     */       
/* 180 */       if (paramArrayOfString[b] != null && paramArrayOfString[b].startsWith("-ORB")) {
/* 181 */         String str = paramArrayOfString[b].substring(1);
/* 182 */         str1 = findMatchingPropertyName(this.propertyNames, str);
/*     */         
/* 184 */         if (str1 != null && 
/* 185 */           b + 1 < paramArrayOfString.length && paramArrayOfString[b + 1] != null) {
/* 186 */           str2 = paramArrayOfString[++b];
/*     */         }
/*     */       } 
/*     */       
/* 190 */       if (str2 != null) {
/* 191 */         setProperty(str1, str2);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void findPropertiesFromApplet(final Applet app) {
/* 200 */     if (app == null) {
/*     */       return;
/*     */     }
/* 203 */     PropertyCallback propertyCallback1 = new PropertyCallback() {
/*     */         public String get(String param1String) {
/* 205 */           return app.getParameter(param1String);
/*     */         }
/*     */       };
/*     */     
/* 209 */     findPropertiesByName(this.propertyNames.iterator(), propertyCallback1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 217 */     PropertyCallback propertyCallback2 = new PropertyCallback() {
/*     */         public String get(String param1String) {
/* 219 */           String str = DataCollectorBase.this.resultProps.getProperty(param1String);
/* 220 */           if (str == null) {
/* 221 */             return null;
/*     */           }
/*     */           try {
/* 224 */             URL uRL = new URL(app.getDocumentBase(), str);
/* 225 */             return uRL.toExternalForm();
/* 226 */           } catch (MalformedURLException malformedURLException) {
/*     */ 
/*     */             
/* 229 */             return str;
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 234 */     findPropertiesByName(this.URLPropertyNames.iterator(), propertyCallback2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void doProperties(final Properties props) {
/* 240 */     PropertyCallback propertyCallback = new PropertyCallback() {
/*     */         public String get(String param1String) {
/* 242 */           return props.getProperty(param1String);
/*     */         }
/*     */       };
/*     */     
/* 246 */     findPropertiesByName(this.propertyNames.iterator(), propertyCallback);
/*     */     
/* 248 */     findPropertiesByPrefix(this.propertyPrefixes, 
/* 249 */         makeIterator(props.propertyNames()), propertyCallback);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void findPropertiesFromFile() {
/* 254 */     Properties properties = getFileProperties();
/* 255 */     if (properties == null) {
/*     */       return;
/*     */     }
/* 258 */     doProperties(properties);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void findPropertiesFromProperties() {
/* 263 */     if (this.originalProps == null) {
/*     */       return;
/*     */     }
/* 266 */     doProperties(this.originalProps);
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
/*     */   protected void findPropertiesFromSystem() {
/* 278 */     Set set1 = getCORBAPrefixes(this.propertyNames);
/* 279 */     Set set2 = getCORBAPrefixes(this.propertyPrefixes);
/*     */     
/* 281 */     PropertyCallback propertyCallback = new PropertyCallback() {
/*     */         public String get(String param1String) {
/* 283 */           return DataCollectorBase.getSystemProperty(param1String);
/*     */         }
/*     */       };
/*     */     
/* 287 */     findPropertiesByName(set1.iterator(), propertyCallback);
/*     */     
/* 289 */     findPropertiesByPrefix(set2, 
/* 290 */         getSystemPropertyNames(), propertyCallback);
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
/*     */   private void setProperty(String paramString1, String paramString2) {
/* 302 */     if (paramString1.equals("org.omg.CORBA.ORBInitRef")) {
/*     */       
/* 304 */       StringTokenizer stringTokenizer = new StringTokenizer(paramString2, "=");
/* 305 */       if (stringTokenizer.countTokens() != 2) {
/* 306 */         throw new IllegalArgumentException();
/*     */       }
/* 308 */       String str1 = stringTokenizer.nextToken();
/* 309 */       String str2 = stringTokenizer.nextToken();
/*     */       
/* 311 */       this.resultProps.setProperty(paramString1 + "." + str1, str2);
/*     */     } else {
/* 313 */       this.resultProps.setProperty(paramString1, paramString2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkSetParserCalled() {
/* 319 */     if (!this.setParserCalled) {
/* 320 */       throw new IllegalStateException("setParser not called.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void findPropertiesByPrefix(Set paramSet, Iterator<String> paramIterator, PropertyCallback paramPropertyCallback) {
/* 329 */     while (paramIterator.hasNext()) {
/* 330 */       String str = paramIterator.next();
/* 331 */       Iterator<String> iterator = paramSet.iterator();
/* 332 */       while (iterator.hasNext()) {
/* 333 */         String str1 = iterator.next();
/* 334 */         if (str.startsWith(str1)) {
/* 335 */           String str2 = paramPropertyCallback.get(str);
/*     */ 
/*     */ 
/*     */           
/* 339 */           setProperty(str, str2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void findPropertiesByName(Iterator<String> paramIterator, PropertyCallback paramPropertyCallback) {
/* 351 */     while (paramIterator.hasNext()) {
/* 352 */       String str1 = paramIterator.next();
/* 353 */       String str2 = paramPropertyCallback.get(str1);
/* 354 */       if (str2 != null) {
/* 355 */         setProperty(str1, str2);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getSystemProperty(String paramString) {
/* 361 */     return AccessController.<String>doPrivileged((PrivilegedAction<String>)new GetPropertyAction(paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String findMatchingPropertyName(Set paramSet, String paramString) {
/* 370 */     Iterator<String> iterator = paramSet.iterator();
/* 371 */     while (iterator.hasNext()) {
/* 372 */       String str = iterator.next();
/* 373 */       if (str.endsWith(paramString)) {
/* 374 */         return str;
/*     */       }
/*     */     } 
/* 377 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Iterator makeIterator(final Enumeration enumeration) {
/* 382 */     return new Iterator() {
/* 383 */         public boolean hasNext() { return enumeration.hasMoreElements(); }
/* 384 */         public Object next() { return enumeration.nextElement(); } public void remove() {
/* 385 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Iterator getSystemPropertyNames() {
/* 394 */     Enumeration enumeration = AccessController.<Enumeration>doPrivileged(new PrivilegedAction<Enumeration>()
/*     */         {
/*     */           public Object run() {
/* 397 */             return System.getProperties().propertyNames();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 402 */     return makeIterator(enumeration);
/*     */   }
/*     */ 
/*     */   
/*     */   private void getPropertiesFromFile(Properties paramProperties, String paramString) {
/*     */     try {
/* 408 */       File file = new File(paramString);
/* 409 */       if (!file.exists()) {
/*     */         return;
/*     */       }
/* 412 */       FileInputStream fileInputStream = new FileInputStream(file);
/*     */       
/*     */       try {
/* 415 */         paramProperties.load(fileInputStream);
/*     */       } finally {
/* 417 */         fileInputStream.close();
/*     */       } 
/* 419 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Properties getFileProperties() {
/* 428 */     Properties properties1 = new Properties();
/*     */     
/* 430 */     String str1 = getSystemProperty("java.home");
/* 431 */     String str2 = str1 + File.separator + "lib" + File.separator + "orb.properties";
/*     */ 
/*     */     
/* 434 */     getPropertiesFromFile(properties1, str2);
/*     */     
/* 436 */     Properties properties2 = new Properties(properties1);
/*     */     
/* 438 */     String str3 = getSystemProperty("user.home");
/* 439 */     str2 = str3 + File.separator + "orb.properties";
/*     */     
/* 441 */     getPropertiesFromFile(properties2, str2);
/* 442 */     return properties2;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasCORBAPrefix(String paramString) {
/* 447 */     return (paramString.startsWith("org.omg.") || paramString
/* 448 */       .startsWith("com.sun.CORBA.") || paramString
/* 449 */       .startsWith("com.sun.corba.") || paramString
/* 450 */       .startsWith("com.sun.corba.se."));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Set getCORBAPrefixes(Set paramSet) {
/* 457 */     HashSet<String> hashSet = new HashSet();
/* 458 */     Iterator<String> iterator = paramSet.iterator();
/* 459 */     while (iterator.hasNext()) {
/* 460 */       String str = iterator.next();
/* 461 */       if (hasCORBAPrefix(str)) {
/* 462 */         hashSet.add(str);
/*     */       }
/*     */     } 
/* 465 */     return hashSet;
/*     */   }
/*     */   
/*     */   public abstract boolean isApplet();
/*     */   
/*     */   protected abstract void collect();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orb/DataCollectorBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */