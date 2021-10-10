/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.StreamTokenizer;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.Security;
/*     */ import java.security.URIParameter;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import javax.security.auth.AuthPermission;
/*     */ import javax.security.auth.login.AppConfigurationEntry;
/*     */ import javax.security.auth.login.Configuration;
/*     */ import javax.security.auth.login.ConfigurationSpi;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.PropertyExpander;
/*     */ import sun.security.util.ResourcesMgr;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ConfigFile
/*     */   extends Configuration
/*     */ {
/* 102 */   private final Spi spi = new Spi();
/*     */ 
/*     */ 
/*     */   
/*     */   public AppConfigurationEntry[] getAppConfigurationEntry(String paramString) {
/* 107 */     return this.spi.engineGetAppConfigurationEntry(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void refresh() {
/* 112 */     this.spi.engineRefresh();
/*     */   }
/*     */   
/*     */   public static final class Spi
/*     */     extends ConfigurationSpi
/*     */   {
/*     */     private URL url;
/*     */     private boolean expandProp = true;
/*     */     private Map<String, List<AppConfigurationEntry>> configuration;
/*     */     private int linenum;
/*     */     private StreamTokenizer st;
/*     */     private int lookahead;
/* 124 */     private static Debug debugConfig = Debug.getInstance("configfile");
/* 125 */     private static Debug debugParser = Debug.getInstance("configparser");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Spi() {
/*     */       try {
/* 135 */         init();
/* 136 */       } catch (IOException iOException) {
/* 137 */         throw new SecurityException(iOException);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Spi(URI param1URI) {
/*     */       try {
/* 153 */         this.url = param1URI.toURL();
/* 154 */         init();
/* 155 */       } catch (IOException iOException) {
/* 156 */         throw new SecurityException(iOException);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Spi(final Configuration.Parameters params) throws IOException {
/*     */       try {
/* 169 */         AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
/*     */               public Void run() throws IOException {
/* 171 */                 if (params == null) {
/* 172 */                   ConfigFile.Spi.this.init();
/*     */                 } else {
/* 174 */                   if (!(params instanceof URIParameter)) {
/* 175 */                     throw new IllegalArgumentException("Unrecognized parameter: " + params);
/*     */                   }
/*     */                   
/* 178 */                   URIParameter uRIParameter = (URIParameter)params;
/* 179 */                   ConfigFile.Spi.this.url = uRIParameter.getURI().toURL();
/* 180 */                   ConfigFile.Spi.this.init();
/*     */                 } 
/* 182 */                 return null;
/*     */               }
/*     */             });
/* 185 */       } catch (PrivilegedActionException privilegedActionException) {
/* 186 */         throw (IOException)privilegedActionException.getException();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void init() throws IOException {
/* 203 */       boolean bool = false;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 208 */       String str1 = Security.getProperty("policy.expandProperties");
/* 209 */       if (str1 == null) {
/* 210 */         str1 = System.getProperty("policy.expandProperties");
/*     */       }
/* 212 */       if ("false".equals(str1)) {
/* 213 */         this.expandProp = false;
/*     */       }
/*     */ 
/*     */       
/* 217 */       HashMap<Object, Object> hashMap = new HashMap<>();
/*     */       
/* 219 */       if (this.url != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 224 */         if (debugConfig != null) {
/* 225 */           debugConfig.println("reading " + this.url);
/*     */         }
/* 227 */         init(this.url, (Map)hashMap);
/* 228 */         this.configuration = (Map)hashMap;
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */       
/* 236 */       String str2 = Security.getProperty("policy.allowSystemProperty");
/*     */       
/* 238 */       if ("true".equalsIgnoreCase(str2)) {
/*     */         
/* 240 */         String str = System.getProperty("java.security.auth.login.config");
/* 241 */         if (str != null) {
/* 242 */           boolean bool1 = false;
/* 243 */           if (str.startsWith("=")) {
/* 244 */             bool1 = true;
/* 245 */             str = str.substring(1);
/*     */           } 
/*     */           try {
/* 248 */             str = PropertyExpander.expand(str);
/* 249 */           } catch (sun.security.util.PropertyExpander.ExpandException expandException) {
/* 250 */             throw ioException("Unable.to.properly.expand.config", new Object[] { str });
/*     */           } 
/*     */ 
/*     */           
/* 254 */           URL uRL = null;
/*     */           try {
/* 256 */             uRL = new URL(str);
/* 257 */           } catch (MalformedURLException malformedURLException) {
/* 258 */             File file = new File(str);
/* 259 */             if (file.exists()) {
/* 260 */               uRL = file.toURI().toURL();
/*     */             } else {
/* 262 */               throw ioException("extra.config.No.such.file.or.directory.", new Object[] { str });
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 268 */           if (debugConfig != null) {
/* 269 */             debugConfig.println("reading " + uRL);
/*     */           }
/* 271 */           init(uRL, (Map)hashMap);
/* 272 */           bool = true;
/* 273 */           if (bool1) {
/* 274 */             if (debugConfig != null) {
/* 275 */               debugConfig.println("overriding other policies!");
/*     */             }
/* 277 */             this.configuration = (Map)hashMap;
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/* 283 */       byte b = 1;
/*     */       
/*     */       String str3;
/* 286 */       while ((str3 = Security.getProperty("login.config.url." + b)) != null) {
/*     */         
/*     */         try {
/* 289 */           str3 = PropertyExpander.expand(str3).replace(File.separatorChar, '/');
/* 290 */           if (debugConfig != null) {
/* 291 */             debugConfig.println("\tReading config: " + str3);
/*     */           }
/* 293 */           init(new URL(str3), (Map)hashMap);
/* 294 */           bool = true;
/* 295 */         } catch (sun.security.util.PropertyExpander.ExpandException expandException) {
/* 296 */           throw ioException("Unable.to.properly.expand.config", new Object[] { str3 });
/*     */         } 
/*     */         
/* 299 */         b++;
/*     */       } 
/*     */       
/* 302 */       if (!bool && b == 1 && str3 == null) {
/*     */ 
/*     */         
/* 305 */         if (debugConfig != null) {
/* 306 */           debugConfig.println("\tReading Policy from ~/.java.login.config");
/*     */         }
/*     */         
/* 309 */         str3 = System.getProperty("user.home");
/* 310 */         String str = str3 + File.separatorChar + ".java.login.config";
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 315 */         if ((new File(str)).exists()) {
/* 316 */           init((new File(str)).toURI().toURL(), (Map)hashMap);
/*     */         }
/*     */       } 
/*     */       
/* 320 */       this.configuration = (Map)hashMap;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void init(URL param1URL, Map<String, List<AppConfigurationEntry>> param1Map) throws IOException {
/* 327 */       try (InputStreamReader null = new InputStreamReader(
/* 328 */             getInputStream(param1URL), "UTF-8")) {
/* 329 */         readConfig(inputStreamReader, param1Map);
/* 330 */       } catch (FileNotFoundException fileNotFoundException) {
/* 331 */         if (debugConfig != null) {
/* 332 */           debugConfig.println(fileNotFoundException.toString());
/*     */         }
/* 334 */         throw new IOException(
/* 335 */             ResourcesMgr.getString("Configuration.Error.No.such.file.or.directory", "sun.security.util.AuthResources"));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AppConfigurationEntry[] engineGetAppConfigurationEntry(String param1String) {
/* 354 */       List list = null;
/* 355 */       synchronized (this.configuration) {
/* 356 */         list = this.configuration.get(param1String);
/*     */       } 
/*     */       
/* 359 */       if (list == null || list.size() == 0) {
/* 360 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 364 */       AppConfigurationEntry[] arrayOfAppConfigurationEntry = new AppConfigurationEntry[list.size()];
/* 365 */       Iterator<AppConfigurationEntry> iterator = list.iterator();
/* 366 */       for (byte b = 0; iterator.hasNext(); b++) {
/* 367 */         AppConfigurationEntry appConfigurationEntry = iterator.next();
/* 368 */         arrayOfAppConfigurationEntry[b] = new AppConfigurationEntry(appConfigurationEntry.getLoginModuleName(), appConfigurationEntry
/* 369 */             .getControlFlag(), appConfigurationEntry
/* 370 */             .getOptions());
/*     */       } 
/* 372 */       return arrayOfAppConfigurationEntry;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized void engineRefresh() {
/* 385 */       SecurityManager securityManager = System.getSecurityManager();
/* 386 */       if (securityManager != null) {
/* 387 */         securityManager.checkPermission(new AuthPermission("refreshLoginConfiguration"));
/*     */       }
/*     */ 
/*     */       
/* 391 */       AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */             public Void run() {
/*     */               try {
/* 394 */                 ConfigFile.Spi.this.init();
/* 395 */               } catch (IOException iOException) {
/* 396 */                 throw new SecurityException(iOException.getLocalizedMessage(), iOException);
/*     */               } 
/*     */               
/* 399 */               return null;
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void readConfig(Reader param1Reader, Map<String, List<AppConfigurationEntry>> param1Map) throws IOException {
/* 408 */       this.linenum = 1;
/*     */       
/* 410 */       if (!(param1Reader instanceof BufferedReader)) {
/* 411 */         param1Reader = new BufferedReader(param1Reader);
/*     */       }
/*     */       
/* 414 */       this.st = new StreamTokenizer(param1Reader);
/* 415 */       this.st.quoteChar(34);
/* 416 */       this.st.wordChars(36, 36);
/* 417 */       this.st.wordChars(95, 95);
/* 418 */       this.st.wordChars(45, 45);
/* 419 */       this.st.wordChars(42, 42);
/* 420 */       this.st.lowerCaseMode(false);
/* 421 */       this.st.slashSlashComments(true);
/* 422 */       this.st.slashStarComments(true);
/* 423 */       this.st.eolIsSignificant(true);
/*     */       
/* 425 */       this.lookahead = nextToken();
/* 426 */       while (this.lookahead != -1) {
/* 427 */         parseLoginEntry(param1Map);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void parseLoginEntry(Map<String, List<AppConfigurationEntry>> param1Map) throws IOException {
/* 435 */       LinkedList<AppConfigurationEntry> linkedList = new LinkedList();
/*     */ 
/*     */       
/* 438 */       String str = this.st.sval;
/* 439 */       this.lookahead = nextToken();
/*     */       
/* 441 */       if (debugParser != null) {
/* 442 */         debugParser.println("\tReading next config entry: " + str);
/*     */       }
/*     */       
/* 445 */       match("{");
/*     */ 
/*     */       
/* 448 */       while (!peek("}")) {
/*     */         AppConfigurationEntry.LoginModuleControlFlag loginModuleControlFlag;
/* 450 */         String str1 = match("module class name");
/*     */ 
/*     */ 
/*     */         
/* 454 */         String str2 = match("controlFlag").toUpperCase(Locale.ENGLISH);
/* 455 */         switch (str2) {
/*     */           case "REQUIRED":
/* 457 */             loginModuleControlFlag = AppConfigurationEntry.LoginModuleControlFlag.REQUIRED;
/*     */             break;
/*     */           case "REQUISITE":
/* 460 */             loginModuleControlFlag = AppConfigurationEntry.LoginModuleControlFlag.REQUISITE;
/*     */             break;
/*     */           case "SUFFICIENT":
/* 463 */             loginModuleControlFlag = AppConfigurationEntry.LoginModuleControlFlag.SUFFICIENT;
/*     */             break;
/*     */           case "OPTIONAL":
/* 466 */             loginModuleControlFlag = AppConfigurationEntry.LoginModuleControlFlag.OPTIONAL;
/*     */             break;
/*     */           default:
/* 469 */             throw ioException("Configuration.Error.Invalid.control.flag.flag", new Object[] { str2 });
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 475 */         HashMap<Object, Object> hashMap = new HashMap<>();
/* 476 */         while (!peek(";")) {
/* 477 */           String str3 = match("option key");
/* 478 */           match("=");
/*     */           try {
/* 480 */             hashMap.put(str3, expand(match("option value")));
/* 481 */           } catch (sun.security.util.PropertyExpander.ExpandException expandException) {
/* 482 */             throw new IOException(expandException.getLocalizedMessage());
/*     */           } 
/*     */         } 
/*     */         
/* 486 */         this.lookahead = nextToken();
/*     */ 
/*     */         
/* 489 */         if (debugParser != null) {
/* 490 */           debugParser.println("\t\t" + str1 + ", " + str2);
/* 491 */           for (String str3 : hashMap.keySet()) {
/* 492 */             debugParser.println("\t\t\t" + str3 + "=" + (String)hashMap
/* 493 */                 .get(str3));
/*     */           }
/*     */         } 
/* 496 */         linkedList.add(new AppConfigurationEntry(str1, loginModuleControlFlag, (Map)hashMap));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 501 */       match("}");
/* 502 */       match(";");
/*     */ 
/*     */       
/* 505 */       if (param1Map.containsKey(str)) {
/* 506 */         throw ioException("Configuration.Error.Can.not.specify.multiple.entries.for.appName", new Object[] { str });
/*     */       }
/*     */ 
/*     */       
/* 510 */       param1Map.put(str, linkedList);
/*     */     }
/*     */ 
/*     */     
/*     */     private String match(String param1String) throws IOException {
/* 515 */       String str = null;
/*     */       
/* 517 */       switch (this.lookahead) {
/*     */         case -1:
/* 519 */           throw ioException("Configuration.Error.expected.expect.read.end.of.file.", new Object[] { param1String });
/*     */ 
/*     */ 
/*     */         
/*     */         case -3:
/*     */         case 34:
/* 525 */           if (param1String.equalsIgnoreCase("module class name") || param1String
/* 526 */             .equalsIgnoreCase("controlFlag") || param1String
/* 527 */             .equalsIgnoreCase("option key") || param1String
/* 528 */             .equalsIgnoreCase("option value")) {
/* 529 */             str = this.st.sval;
/* 530 */             this.lookahead = nextToken();
/*     */           } else {
/* 532 */             throw ioException("Configuration.Error.Line.line.expected.expect.found.value.", new Object[] { new Integer(this.linenum), param1String, this.st.sval });
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 583 */           return str;case 123: if (param1String.equalsIgnoreCase("{")) { this.lookahead = nextToken(); } else { throw ioException("Configuration.Error.Line.line.expected.expect.", new Object[] { new Integer(this.linenum), param1String, this.st.sval }); }  return str;case 59: if (param1String.equalsIgnoreCase(";")) { this.lookahead = nextToken(); } else { throw ioException("Configuration.Error.Line.line.expected.expect.", new Object[] { new Integer(this.linenum), param1String, this.st.sval }); }  return str;case 125: if (param1String.equalsIgnoreCase("}")) { this.lookahead = nextToken(); } else { throw ioException("Configuration.Error.Line.line.expected.expect.", new Object[] { new Integer(this.linenum), param1String, this.st.sval }); }  return str;case 61: if (param1String.equalsIgnoreCase("=")) { this.lookahead = nextToken(); } else { throw ioException("Configuration.Error.Line.line.expected.expect.", new Object[] { new Integer(this.linenum), param1String, this.st.sval }); }  return str;
/*     */       } 
/*     */       throw ioException("Configuration.Error.Line.line.expected.expect.found.value.", new Object[] { new Integer(this.linenum), param1String, this.st.sval });
/*     */     } private boolean peek(String param1String) {
/* 587 */       switch (this.lookahead) {
/*     */         case 44:
/* 589 */           return param1String.equalsIgnoreCase(",");
/*     */         case 59:
/* 591 */           return param1String.equalsIgnoreCase(";");
/*     */         case 123:
/* 593 */           return param1String.equalsIgnoreCase("{");
/*     */         case 125:
/* 595 */           return param1String.equalsIgnoreCase("}");
/*     */       } 
/* 597 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     private int nextToken() throws IOException {
/*     */       int i;
/* 603 */       while ((i = this.st.nextToken()) == 10) {
/* 604 */         this.linenum++;
/*     */       }
/* 606 */       return i;
/*     */     }
/*     */     
/*     */     private InputStream getInputStream(URL param1URL) throws IOException {
/* 610 */       if ("file".equalsIgnoreCase(param1URL.getProtocol())) {
/*     */         
/*     */         try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 627 */           return param1URL.openStream();
/* 628 */         } catch (Exception exception) {
/* 629 */           String str = param1URL.getPath();
/* 630 */           if (param1URL.getHost().length() > 0) {
/* 631 */             str = "//" + param1URL.getHost() + str;
/*     */           }
/* 633 */           if (debugConfig != null) {
/* 634 */             debugConfig.println("cannot read " + param1URL + ", try " + str);
/*     */           }
/*     */           
/* 637 */           return new FileInputStream(str);
/*     */         } 
/*     */       }
/* 640 */       return param1URL.openStream();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String expand(String param1String) throws PropertyExpander.ExpandException, IOException {
/* 647 */       if (param1String.isEmpty()) {
/* 648 */         return param1String;
/*     */       }
/*     */       
/* 651 */       if (!this.expandProp) {
/* 652 */         return param1String;
/*     */       }
/* 654 */       String str = PropertyExpander.expand(param1String);
/* 655 */       if (str == null || str.length() == 0) {
/* 656 */         throw ioException("Configuration.Error.Line.line.system.property.value.expanded.to.empty.value", new Object[] { new Integer(this.linenum), param1String });
/*     */       }
/*     */ 
/*     */       
/* 660 */       return str;
/*     */     }
/*     */ 
/*     */     
/*     */     private IOException ioException(String param1String, Object... param1VarArgs) {
/* 665 */       MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString(param1String, "sun.security.util.AuthResources"));
/* 666 */       return new IOException(messageFormat.format(param1VarArgs));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/ConfigFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */