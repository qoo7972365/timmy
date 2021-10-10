/*      */ package sun.security.provider;
/*      */ 
/*      */ import java.io.BufferedReader;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.File;
/*      */ import java.io.FileReader;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.Reader;
/*      */ import java.io.StreamTokenizer;
/*      */ import java.io.Writer;
/*      */ import java.security.GeneralSecurityException;
/*      */ import java.security.Principal;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TreeMap;
/*      */ import java.util.Vector;
/*      */ import javax.security.auth.x500.X500Principal;
/*      */ import sun.net.www.ParseUtil;
/*      */ import sun.security.util.Debug;
/*      */ import sun.security.util.PropertyExpander;
/*      */ import sun.security.util.ResourcesMgr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PolicyParser
/*      */ {
/*      */   private static final String EXTDIRS_PROPERTY = "java.ext.dirs";
/*      */   private static final String OLD_EXTDIRS_EXPANSION = "${java.ext.dirs}";
/*      */   static final String EXTDIRS_EXPANSION = "${{java.ext.dirs}}";
/*      */   private Vector<GrantEntry> grantEntries;
/*      */   private Map<String, DomainEntry> domainEntries;
/*   98 */   private static final Debug debug = Debug.getInstance("parser", "\t[Policy Parser]");
/*      */   
/*      */   private StreamTokenizer st;
/*      */   private int lookahead;
/*      */   private boolean expandProp = false;
/*  103 */   private String keyStoreUrlString = null;
/*  104 */   private String keyStoreType = null;
/*  105 */   private String keyStoreProvider = null;
/*  106 */   private String storePassURL = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private String expand(String paramString) throws PropertyExpander.ExpandException {
/*  111 */     return expand(paramString, false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String expand(String paramString, boolean paramBoolean) throws PropertyExpander.ExpandException {
/*  117 */     if (!this.expandProp) {
/*  118 */       return paramString;
/*      */     }
/*  120 */     return PropertyExpander.expand(paramString, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PolicyParser() {
/*  129 */     this.grantEntries = new Vector<>();
/*      */   }
/*      */ 
/*      */   
/*      */   public PolicyParser(boolean paramBoolean) {
/*  134 */     this();
/*  135 */     this.expandProp = paramBoolean;
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
/*      */   public void read(Reader paramReader) throws ParsingException, IOException {
/*  154 */     if (!(paramReader instanceof BufferedReader)) {
/*  155 */       paramReader = new BufferedReader(paramReader);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  165 */     this.st = new StreamTokenizer(paramReader);
/*      */     
/*  167 */     this.st.resetSyntax();
/*  168 */     this.st.wordChars(97, 122);
/*  169 */     this.st.wordChars(65, 90);
/*  170 */     this.st.wordChars(46, 46);
/*  171 */     this.st.wordChars(48, 57);
/*  172 */     this.st.wordChars(95, 95);
/*  173 */     this.st.wordChars(36, 36);
/*  174 */     this.st.wordChars(160, 255);
/*  175 */     this.st.whitespaceChars(0, 32);
/*  176 */     this.st.commentChar(47);
/*  177 */     this.st.quoteChar(39);
/*  178 */     this.st.quoteChar(34);
/*  179 */     this.st.lowerCaseMode(false);
/*  180 */     this.st.ordinaryChar(47);
/*  181 */     this.st.slashSlashComments(true);
/*  182 */     this.st.slashStarComments(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  193 */     this.lookahead = this.st.nextToken();
/*  194 */     GrantEntry grantEntry = null;
/*  195 */     while (this.lookahead != -1) {
/*  196 */       if (peek("grant")) {
/*  197 */         grantEntry = parseGrantEntry();
/*      */         
/*  199 */         if (grantEntry != null)
/*  200 */           add(grantEntry); 
/*  201 */       } else if (peek("keystore") && this.keyStoreUrlString == null) {
/*      */ 
/*      */         
/*  204 */         parseKeyStoreEntry();
/*  205 */       } else if (peek("keystorePasswordURL") && this.storePassURL == null) {
/*      */ 
/*      */         
/*  208 */         parseStorePassURL();
/*  209 */       } else if (grantEntry == null && this.keyStoreUrlString == null && this.storePassURL == null && 
/*  210 */         peek("domain")) {
/*  211 */         if (this.domainEntries == null) {
/*  212 */           this.domainEntries = new TreeMap<>();
/*      */         }
/*  214 */         DomainEntry domainEntry = parseDomainEntry();
/*  215 */         if (domainEntry != null) {
/*  216 */           String str = domainEntry.getName();
/*  217 */           if (!this.domainEntries.containsKey(str)) {
/*  218 */             this.domainEntries.put(str, domainEntry);
/*      */           } else {
/*      */             
/*  221 */             MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("duplicate.keystore.domain.name"));
/*      */             
/*  223 */             Object[] arrayOfObject = { str };
/*  224 */             throw new ParsingException(messageFormat.format(arrayOfObject));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  230 */       match(";");
/*      */     } 
/*      */     
/*  233 */     if (this.keyStoreUrlString == null && this.storePassURL != null) {
/*  234 */       throw new ParsingException(
/*  235 */           ResourcesMgr.getString("keystorePasswordURL.can.not.be.specified.without.also.specifying.keystore"));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void add(GrantEntry paramGrantEntry) {
/*  241 */     this.grantEntries.addElement(paramGrantEntry);
/*      */   }
/*      */ 
/*      */   
/*      */   public void replace(GrantEntry paramGrantEntry1, GrantEntry paramGrantEntry2) {
/*  246 */     this.grantEntries.setElementAt(paramGrantEntry2, this.grantEntries.indexOf(paramGrantEntry1));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(GrantEntry paramGrantEntry) {
/*  251 */     return this.grantEntries.removeElement(paramGrantEntry);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKeyStoreUrl() {
/*      */     try {
/*  260 */       if (this.keyStoreUrlString != null && this.keyStoreUrlString.length() != 0) {
/*  261 */         return expand(this.keyStoreUrlString, true)
/*  262 */           .replace(File.separatorChar, '/');
/*      */       }
/*  264 */     } catch (sun.security.util.PropertyExpander.ExpandException expandException) {
/*  265 */       if (debug != null) {
/*  266 */         debug.println(expandException.toString());
/*      */       }
/*  268 */       return null;
/*      */     } 
/*  270 */     return null;
/*      */   }
/*      */   
/*      */   public void setKeyStoreUrl(String paramString) {
/*  274 */     this.keyStoreUrlString = paramString;
/*      */   }
/*      */   
/*      */   public String getKeyStoreType() {
/*  278 */     return this.keyStoreType;
/*      */   }
/*      */   
/*      */   public void setKeyStoreType(String paramString) {
/*  282 */     this.keyStoreType = paramString;
/*      */   }
/*      */   
/*      */   public String getKeyStoreProvider() {
/*  286 */     return this.keyStoreProvider;
/*      */   }
/*      */   
/*      */   public void setKeyStoreProvider(String paramString) {
/*  290 */     this.keyStoreProvider = paramString;
/*      */   }
/*      */   
/*      */   public String getStorePassURL() {
/*      */     try {
/*  295 */       if (this.storePassURL != null && this.storePassURL.length() != 0) {
/*  296 */         return expand(this.storePassURL, true)
/*  297 */           .replace(File.separatorChar, '/');
/*      */       }
/*  299 */     } catch (sun.security.util.PropertyExpander.ExpandException expandException) {
/*  300 */       if (debug != null) {
/*  301 */         debug.println(expandException.toString());
/*      */       }
/*  303 */       return null;
/*      */     } 
/*  305 */     return null;
/*      */   }
/*      */   
/*      */   public void setStorePassURL(String paramString) {
/*  309 */     this.storePassURL = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Enumeration<GrantEntry> grantElements() {
/*  319 */     return this.grantEntries.elements();
/*      */   }
/*      */   
/*      */   public Collection<DomainEntry> getDomainEntries() {
/*  323 */     return this.domainEntries.values();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void write(Writer paramWriter) {
/*  332 */     PrintWriter printWriter = new PrintWriter(new BufferedWriter(paramWriter));
/*      */     
/*  334 */     Enumeration<GrantEntry> enumeration = grantElements();
/*      */     
/*  336 */     printWriter.println("/* AUTOMATICALLY GENERATED ON " + new Date() + "*/");
/*      */     
/*  338 */     printWriter.println("/* DO NOT EDIT */");
/*  339 */     printWriter.println();
/*      */ 
/*      */ 
/*      */     
/*  343 */     if (this.keyStoreUrlString != null) {
/*  344 */       writeKeyStoreEntry(printWriter);
/*      */     }
/*  346 */     if (this.storePassURL != null) {
/*  347 */       writeStorePassURL(printWriter);
/*      */     }
/*      */ 
/*      */     
/*  351 */     while (enumeration.hasMoreElements()) {
/*  352 */       GrantEntry grantEntry = enumeration.nextElement();
/*  353 */       grantEntry.write(printWriter);
/*  354 */       printWriter.println();
/*      */     } 
/*  356 */     printWriter.flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parseKeyStoreEntry() throws ParsingException, IOException {
/*  363 */     match("keystore");
/*  364 */     this.keyStoreUrlString = match("quoted string");
/*      */ 
/*      */     
/*  367 */     if (!peek(",")) {
/*      */       return;
/*      */     }
/*  370 */     match(",");
/*      */     
/*  372 */     if (peek("\"")) {
/*  373 */       this.keyStoreType = match("quoted string");
/*      */     } else {
/*  375 */       throw new ParsingException(this.st.lineno(), 
/*  376 */           ResourcesMgr.getString("expected.keystore.type"));
/*      */     } 
/*      */ 
/*      */     
/*  380 */     if (!peek(",")) {
/*      */       return;
/*      */     }
/*  383 */     match(",");
/*      */     
/*  385 */     if (peek("\"")) {
/*  386 */       this.keyStoreProvider = match("quoted string");
/*      */     } else {
/*  388 */       throw new ParsingException(this.st.lineno(), 
/*  389 */           ResourcesMgr.getString("expected.keystore.provider"));
/*      */     } 
/*      */   }
/*      */   
/*      */   private void parseStorePassURL() throws ParsingException, IOException {
/*  394 */     match("keyStorePasswordURL");
/*  395 */     this.storePassURL = match("quoted string");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeKeyStoreEntry(PrintWriter paramPrintWriter) {
/*  402 */     paramPrintWriter.print("keystore \"");
/*  403 */     paramPrintWriter.print(this.keyStoreUrlString);
/*  404 */     paramPrintWriter.print('"');
/*  405 */     if (this.keyStoreType != null && this.keyStoreType.length() > 0)
/*  406 */       paramPrintWriter.print(", \"" + this.keyStoreType + "\""); 
/*  407 */     if (this.keyStoreProvider != null && this.keyStoreProvider.length() > 0)
/*  408 */       paramPrintWriter.print(", \"" + this.keyStoreProvider + "\""); 
/*  409 */     paramPrintWriter.println(";");
/*  410 */     paramPrintWriter.println();
/*      */   }
/*      */   
/*      */   private void writeStorePassURL(PrintWriter paramPrintWriter) {
/*  414 */     paramPrintWriter.print("keystorePasswordURL \"");
/*  415 */     paramPrintWriter.print(this.storePassURL);
/*  416 */     paramPrintWriter.print('"');
/*  417 */     paramPrintWriter.println(";");
/*  418 */     paramPrintWriter.println();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private GrantEntry parseGrantEntry() throws ParsingException, IOException {
/*  427 */     GrantEntry grantEntry = new GrantEntry();
/*  428 */     LinkedList<PrincipalEntry> linkedList = null;
/*  429 */     boolean bool = false;
/*      */     
/*  431 */     match("grant");
/*      */     
/*  433 */     while (!peek("{")) {
/*      */       
/*  435 */       if (peekAndMatch("Codebase")) {
/*  436 */         if (grantEntry.codeBase != null)
/*  437 */           throw new ParsingException(this.st
/*  438 */               .lineno(), 
/*      */               
/*  440 */               ResourcesMgr.getString("multiple.Codebase.expressions")); 
/*  441 */         grantEntry.codeBase = match("quoted string");
/*  442 */         peekAndMatch(","); continue;
/*  443 */       }  if (peekAndMatch("SignedBy")) {
/*  444 */         if (grantEntry.signedBy != null) {
/*  445 */           throw new ParsingException(this.st
/*  446 */               .lineno(), 
/*  447 */               ResourcesMgr.getString("multiple.SignedBy.expressions"));
/*      */         }
/*  449 */         grantEntry.signedBy = match("quoted string");
/*      */ 
/*      */         
/*  452 */         StringTokenizer stringTokenizer = new StringTokenizer(grantEntry.signedBy, ",", true);
/*      */         
/*  454 */         byte b1 = 0;
/*  455 */         byte b2 = 0;
/*  456 */         while (stringTokenizer.hasMoreTokens()) {
/*  457 */           String str = stringTokenizer.nextToken().trim();
/*  458 */           if (str.equals(",")) {
/*  459 */             b2++; continue;
/*  460 */           }  if (str.length() > 0)
/*  461 */             b1++; 
/*      */         } 
/*  463 */         if (b1 <= b2) {
/*  464 */           throw new ParsingException(this.st
/*  465 */               .lineno(), 
/*  466 */               ResourcesMgr.getString("SignedBy.has.empty.alias"));
/*      */         }
/*      */         
/*  469 */         peekAndMatch(","); continue;
/*  470 */       }  if (peekAndMatch("Principal")) {
/*  471 */         String str1, str2; if (linkedList == null) {
/*  472 */           linkedList = new LinkedList();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  478 */         if (peek("\"")) {
/*      */ 
/*      */           
/*  481 */           str1 = "PolicyParser.REPLACE_NAME";
/*  482 */           str2 = match("principal type");
/*      */         } else {
/*      */           
/*  485 */           if (peek("*")) {
/*  486 */             match("*");
/*  487 */             str1 = "WILDCARD_PRINCIPAL_CLASS";
/*      */           } else {
/*  489 */             str1 = match("principal type");
/*      */           } 
/*      */ 
/*      */           
/*  493 */           if (peek("*")) {
/*  494 */             match("*");
/*  495 */             str2 = "WILDCARD_PRINCIPAL_NAME";
/*      */           } else {
/*  497 */             str2 = match("quoted string");
/*      */           } 
/*      */ 
/*      */           
/*  501 */           if (str1.equals("WILDCARD_PRINCIPAL_CLASS") && 
/*  502 */             !str2.equals("WILDCARD_PRINCIPAL_NAME")) {
/*  503 */             if (debug != null) {
/*  504 */               debug.println("disallowing principal that has WILDCARD class but no WILDCARD name");
/*      */             }
/*      */             
/*  507 */             throw new ParsingException(this.st
/*  508 */                 .lineno(), 
/*      */                 
/*  510 */                 ResourcesMgr.getString("can.not.specify.Principal.with.a.wildcard.class.without.a.wildcard.name"));
/*      */           } 
/*      */         } 
/*      */         
/*      */         try {
/*  515 */           str2 = expand(str2);
/*      */           
/*  517 */           if (str1
/*  518 */             .equals("javax.security.auth.x500.X500Principal") && 
/*  519 */             !str2.equals("WILDCARD_PRINCIPAL_NAME")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  526 */             X500Principal x500Principal = new X500Principal((new X500Principal(str2)).toString());
/*  527 */             str2 = x500Principal.getName();
/*      */           } 
/*      */           
/*  530 */           linkedList
/*  531 */             .add(new PrincipalEntry(str1, str2));
/*  532 */         } catch (sun.security.util.PropertyExpander.ExpandException expandException) {
/*      */ 
/*      */ 
/*      */           
/*  536 */           if (debug != null) {
/*  537 */             debug.println("principal name expansion failed: " + str2);
/*      */           }
/*      */           
/*  540 */           bool = true;
/*      */         } 
/*  542 */         peekAndMatch(",");
/*      */         continue;
/*      */       } 
/*  545 */       throw new ParsingException(this.st.lineno(), 
/*  546 */           ResourcesMgr.getString("expected.codeBase.or.SignedBy.or.Principal"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  551 */     if (linkedList != null) grantEntry.principals = linkedList; 
/*  552 */     match("{");
/*      */     
/*  554 */     while (!peek("}")) {
/*  555 */       if (peek("Permission")) {
/*      */         try {
/*  557 */           PermissionEntry permissionEntry = parsePermissionEntry();
/*  558 */           grantEntry.add(permissionEntry);
/*  559 */         } catch (sun.security.util.PropertyExpander.ExpandException expandException) {
/*      */           
/*  561 */           if (debug != null) {
/*  562 */             debug.println(expandException.toString());
/*      */           }
/*  564 */           skipEntry();
/*      */         } 
/*  566 */         match(";"); continue;
/*      */       } 
/*  568 */       throw new ParsingException(this.st
/*  569 */           .lineno(), 
/*  570 */           ResourcesMgr.getString("expected.permission.entry"));
/*      */     } 
/*      */ 
/*      */     
/*  574 */     match("}");
/*      */     
/*      */     try {
/*  577 */       if (grantEntry.signedBy != null) grantEntry.signedBy = expand(grantEntry.signedBy); 
/*  578 */       if (grantEntry.codeBase != null) {
/*      */ 
/*      */         
/*  581 */         if (grantEntry.codeBase.equals("${java.ext.dirs}")) {
/*  582 */           grantEntry.codeBase = "${{java.ext.dirs}}";
/*      */         }
/*      */         int i;
/*  585 */         if ((i = grantEntry.codeBase.indexOf("${{java.ext.dirs}}")) < 0) {
/*  586 */           grantEntry
/*  587 */             .codeBase = expand(grantEntry.codeBase, true).replace(File.separatorChar, '/');
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  592 */           String[] arrayOfString = parseExtDirs(grantEntry.codeBase, i);
/*  593 */           if (arrayOfString != null && arrayOfString.length > 0) {
/*  594 */             for (byte b = 0; b < arrayOfString.length; b++) {
/*  595 */               GrantEntry grantEntry1 = (GrantEntry)grantEntry.clone();
/*  596 */               grantEntry1.codeBase = arrayOfString[b];
/*  597 */               add(grantEntry1);
/*      */               
/*  599 */               if (debug != null) {
/*  600 */                 debug.println("creating policy entry for expanded java.ext.dirs path:\n\t\t" + arrayOfString[b]);
/*      */               }
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*  606 */           bool = true;
/*      */         } 
/*      */       } 
/*  609 */     } catch (sun.security.util.PropertyExpander.ExpandException expandException) {
/*  610 */       if (debug != null) {
/*  611 */         debug.println(expandException.toString());
/*      */       }
/*  613 */       return null;
/*      */     } 
/*      */     
/*  616 */     return (bool == true) ? null : grantEntry;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PermissionEntry parsePermissionEntry() throws ParsingException, IOException, PropertyExpander.ExpandException {
/*  625 */     PermissionEntry permissionEntry = new PermissionEntry();
/*      */ 
/*      */     
/*  628 */     match("Permission");
/*  629 */     permissionEntry.permission = match("permission type");
/*      */     
/*  631 */     if (peek("\""))
/*      */     {
/*  633 */       permissionEntry.name = expand(match("quoted string"));
/*      */     }
/*      */     
/*  636 */     if (!peek(",")) {
/*  637 */       return permissionEntry;
/*      */     }
/*  639 */     match(",");
/*      */     
/*  641 */     if (peek("\"")) {
/*  642 */       permissionEntry.action = expand(match("quoted string"));
/*  643 */       if (!peek(",")) {
/*  644 */         return permissionEntry;
/*      */       }
/*  646 */       match(",");
/*      */     } 
/*      */     
/*  649 */     if (peekAndMatch("SignedBy")) {
/*  650 */       permissionEntry.signedBy = expand(match("quoted string"));
/*      */     }
/*  652 */     return permissionEntry;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DomainEntry parseDomainEntry() throws ParsingException, IOException {
/*      */     Map<String, String> map;
/*  661 */     boolean bool = false;
/*      */     
/*  663 */     String str = null;
/*  664 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*      */     
/*  666 */     match("domain");
/*  667 */     str = match("domain name");
/*      */     
/*  669 */     while (!peek("{"))
/*      */     {
/*  671 */       map = parseProperties("{");
/*      */     }
/*  673 */     match("{");
/*  674 */     DomainEntry domainEntry = new DomainEntry(str, map);
/*      */     
/*  676 */     while (!peek("}")) {
/*      */       
/*  678 */       match("keystore");
/*  679 */       str = match("keystore name");
/*      */       
/*  681 */       if (!peek("}")) {
/*  682 */         map = parseProperties(";");
/*      */       }
/*  684 */       match(";");
/*  685 */       domainEntry.add(new KeyStoreEntry(str, map));
/*      */     } 
/*  687 */     match("}");
/*      */     
/*  689 */     return (bool == true) ? null : domainEntry;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<String, String> parseProperties(String paramString) throws ParsingException, IOException {
/*  698 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*      */ 
/*      */     
/*  701 */     while (!peek(paramString)) {
/*  702 */       String str2, str1 = match("property name");
/*  703 */       match("=");
/*      */       
/*      */       try {
/*  706 */         str2 = expand(match("quoted string"));
/*  707 */       } catch (sun.security.util.PropertyExpander.ExpandException expandException) {
/*  708 */         throw new IOException(expandException.getLocalizedMessage());
/*      */       } 
/*  710 */       hashMap.put(str1.toLowerCase(Locale.ENGLISH), str2);
/*      */     } 
/*      */     
/*  713 */     return (Map)hashMap;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static String[] parseExtDirs(String paramString, int paramInt) {
/*  719 */     String str1 = System.getProperty("java.ext.dirs");
/*  720 */     String str2 = (paramInt > 0) ? paramString.substring(0, paramInt) : "file:";
/*  721 */     int i = paramInt + "${{java.ext.dirs}}".length();
/*  722 */     String str3 = (i < paramString.length()) ? paramString.substring(i) : (String)null;
/*      */ 
/*      */     
/*  725 */     String[] arrayOfString = null;
/*      */     
/*  727 */     if (str1 != null) {
/*  728 */       StringTokenizer stringTokenizer = new StringTokenizer(str1, File.pathSeparator);
/*      */       
/*  730 */       int j = stringTokenizer.countTokens();
/*  731 */       arrayOfString = new String[j];
/*  732 */       for (byte b = 0; b < j; b++) {
/*  733 */         File file = new File(stringTokenizer.nextToken());
/*  734 */         arrayOfString[b] = 
/*  735 */           ParseUtil.encodePath(file.getAbsolutePath());
/*      */         
/*  737 */         if (!arrayOfString[b].startsWith("/")) {
/*  738 */           arrayOfString[b] = "/" + arrayOfString[b];
/*      */         }
/*      */ 
/*      */         
/*  742 */         String str = (str3 == null) ? (arrayOfString[b].endsWith("/") ? "*" : "/*") : str3;
/*      */ 
/*      */         
/*  745 */         arrayOfString[b] = str2 + arrayOfString[b] + str;
/*      */       } 
/*      */     } 
/*  748 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean peekAndMatch(String paramString) throws ParsingException, IOException {
/*  754 */     if (peek(paramString)) {
/*  755 */       match(paramString);
/*  756 */       return true;
/*      */     } 
/*  758 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean peek(String paramString) {
/*  763 */     boolean bool = false;
/*      */     
/*  765 */     switch (this.lookahead) {
/*      */       
/*      */       case -3:
/*  768 */         if (paramString.equalsIgnoreCase(this.st.sval))
/*  769 */           bool = true; 
/*      */         break;
/*      */       case 44:
/*  772 */         if (paramString.equalsIgnoreCase(","))
/*  773 */           bool = true; 
/*      */         break;
/*      */       case 123:
/*  776 */         if (paramString.equalsIgnoreCase("{"))
/*  777 */           bool = true; 
/*      */         break;
/*      */       case 125:
/*  780 */         if (paramString.equalsIgnoreCase("}"))
/*  781 */           bool = true; 
/*      */         break;
/*      */       case 34:
/*  784 */         if (paramString.equalsIgnoreCase("\""))
/*  785 */           bool = true; 
/*      */         break;
/*      */       case 42:
/*  788 */         if (paramString.equalsIgnoreCase("*"))
/*  789 */           bool = true; 
/*      */         break;
/*      */       case 59:
/*  792 */         if (paramString.equalsIgnoreCase(";")) {
/*  793 */           bool = true;
/*      */         }
/*      */         break;
/*      */     } 
/*      */     
/*  798 */     return bool;
/*      */   }
/*      */   
/*      */   private String match(String paramString) throws ParsingException, IOException {
/*      */     MessageFormat messageFormat;
/*      */     Object[] arrayOfObject;
/*  804 */     String str = null;
/*      */     
/*  806 */     switch (this.lookahead) {
/*      */       case -2:
/*  808 */         throw new ParsingException(this.st.lineno(), paramString, 
/*  809 */             ResourcesMgr.getString("number.") + 
/*  810 */             String.valueOf(this.st.nval));
/*      */ 
/*      */       
/*      */       case -1:
/*  814 */         messageFormat = new MessageFormat(ResourcesMgr.getString("expected.expect.read.end.of.file."));
/*  815 */         arrayOfObject = new Object[] { paramString };
/*  816 */         throw new ParsingException(messageFormat.format(arrayOfObject));
/*      */       case -3:
/*  818 */         if (paramString.equalsIgnoreCase(this.st.sval)) {
/*  819 */           this.lookahead = this.st.nextToken();
/*  820 */         } else if (paramString.equalsIgnoreCase("permission type")) {
/*  821 */           str = this.st.sval;
/*  822 */           this.lookahead = this.st.nextToken();
/*  823 */         } else if (paramString.equalsIgnoreCase("principal type")) {
/*  824 */           str = this.st.sval;
/*  825 */           this.lookahead = this.st.nextToken();
/*  826 */         } else if (paramString.equalsIgnoreCase("domain name") || paramString
/*  827 */           .equalsIgnoreCase("keystore name") || paramString
/*  828 */           .equalsIgnoreCase("property name")) {
/*  829 */           str = this.st.sval;
/*  830 */           this.lookahead = this.st.nextToken();
/*      */         } else {
/*  832 */           throw new ParsingException(this.st.lineno(), paramString, this.st.sval);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  890 */         return str;case 34: if (paramString.equalsIgnoreCase("quoted string")) { str = this.st.sval; this.lookahead = this.st.nextToken(); } else if (paramString.equalsIgnoreCase("permission type")) { str = this.st.sval; this.lookahead = this.st.nextToken(); } else if (paramString.equalsIgnoreCase("principal type")) { str = this.st.sval; this.lookahead = this.st.nextToken(); } else { throw new ParsingException(this.st.lineno(), paramString, this.st.sval); }  return str;case 44: if (paramString.equalsIgnoreCase(",")) { this.lookahead = this.st.nextToken(); } else { throw new ParsingException(this.st.lineno(), paramString, ","); }  return str;case 123: if (paramString.equalsIgnoreCase("{")) { this.lookahead = this.st.nextToken(); } else { throw new ParsingException(this.st.lineno(), paramString, "{"); }  return str;case 125: if (paramString.equalsIgnoreCase("}")) { this.lookahead = this.st.nextToken(); } else { throw new ParsingException(this.st.lineno(), paramString, "}"); }  return str;case 59: if (paramString.equalsIgnoreCase(";")) { this.lookahead = this.st.nextToken(); } else { throw new ParsingException(this.st.lineno(), paramString, ";"); }  return str;case 42: if (paramString.equalsIgnoreCase("*")) { this.lookahead = this.st.nextToken(); } else { throw new ParsingException(this.st.lineno(), paramString, "*"); }  return str;case 61: if (paramString.equalsIgnoreCase("=")) { this.lookahead = this.st.nextToken(); } else { throw new ParsingException(this.st.lineno(), paramString, "="); }  return str;
/*      */     } 
/*      */     throw new ParsingException(this.st.lineno(), paramString, new String(new char[] { (char)this.lookahead }));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void skipEntry() throws ParsingException, IOException {
/*  898 */     while (this.lookahead != 59) {
/*  899 */       switch (this.lookahead) {
/*      */         case -2:
/*  901 */           throw new ParsingException(this.st.lineno(), ";", 
/*  902 */               ResourcesMgr.getString("number.") + 
/*  903 */               String.valueOf(this.st.nval));
/*      */         case -1:
/*  905 */           throw new ParsingException(
/*  906 */               ResourcesMgr.getString("expected.read.end.of.file."));
/*      */       } 
/*  908 */       this.lookahead = this.st.nextToken();
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
/*      */   public static class GrantEntry
/*      */   {
/*      */     public String signedBy;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String codeBase;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LinkedList<PolicyParser.PrincipalEntry> principals;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Vector<PolicyParser.PermissionEntry> permissionEntries;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public GrantEntry() {
/*  951 */       this.principals = new LinkedList<>();
/*  952 */       this.permissionEntries = new Vector<>();
/*      */     }
/*      */     
/*      */     public GrantEntry(String param1String1, String param1String2) {
/*  956 */       this.codeBase = param1String2;
/*  957 */       this.signedBy = param1String1;
/*  958 */       this.principals = new LinkedList<>();
/*  959 */       this.permissionEntries = new Vector<>();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(PolicyParser.PermissionEntry param1PermissionEntry) {
/*  964 */       this.permissionEntries.addElement(param1PermissionEntry);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(PolicyParser.PrincipalEntry param1PrincipalEntry) {
/*  969 */       return this.principals.remove(param1PrincipalEntry);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(PolicyParser.PermissionEntry param1PermissionEntry) {
/*  974 */       return this.permissionEntries.removeElement(param1PermissionEntry);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(PolicyParser.PrincipalEntry param1PrincipalEntry) {
/*  979 */       return this.principals.contains(param1PrincipalEntry);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(PolicyParser.PermissionEntry param1PermissionEntry) {
/*  984 */       return this.permissionEntries.contains(param1PermissionEntry);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Enumeration<PolicyParser.PermissionEntry> permissionElements() {
/*  991 */       return this.permissionEntries.elements();
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(PrintWriter param1PrintWriter) {
/*  996 */       param1PrintWriter.print("grant");
/*  997 */       if (this.signedBy != null) {
/*  998 */         param1PrintWriter.print(" signedBy \"");
/*  999 */         param1PrintWriter.print(this.signedBy);
/* 1000 */         param1PrintWriter.print('"');
/* 1001 */         if (this.codeBase != null)
/* 1002 */           param1PrintWriter.print(", "); 
/*      */       } 
/* 1004 */       if (this.codeBase != null) {
/* 1005 */         param1PrintWriter.print(" codeBase \"");
/* 1006 */         param1PrintWriter.print(this.codeBase);
/* 1007 */         param1PrintWriter.print('"');
/* 1008 */         if (this.principals != null && this.principals.size() > 0)
/* 1009 */           param1PrintWriter.print(",\n"); 
/*      */       } 
/* 1011 */       if (this.principals != null && this.principals.size() > 0) {
/* 1012 */         Iterator<PolicyParser.PrincipalEntry> iterator = this.principals.iterator();
/* 1013 */         while (iterator.hasNext()) {
/* 1014 */           param1PrintWriter.print("      ");
/* 1015 */           PolicyParser.PrincipalEntry principalEntry = iterator.next();
/* 1016 */           principalEntry.write(param1PrintWriter);
/* 1017 */           if (iterator.hasNext())
/* 1018 */             param1PrintWriter.print(",\n"); 
/*      */         } 
/*      */       } 
/* 1021 */       param1PrintWriter.println(" {");
/* 1022 */       Enumeration<PolicyParser.PermissionEntry> enumeration = this.permissionEntries.elements();
/* 1023 */       while (enumeration.hasMoreElements()) {
/* 1024 */         PolicyParser.PermissionEntry permissionEntry = enumeration.nextElement();
/* 1025 */         param1PrintWriter.write("  ");
/* 1026 */         permissionEntry.write(param1PrintWriter);
/*      */       } 
/* 1028 */       param1PrintWriter.println("};");
/*      */     }
/*      */     
/*      */     public Object clone() {
/* 1032 */       GrantEntry grantEntry = new GrantEntry();
/* 1033 */       grantEntry.codeBase = this.codeBase;
/* 1034 */       grantEntry.signedBy = this.signedBy;
/* 1035 */       grantEntry.principals = new LinkedList<>(this.principals);
/* 1036 */       grantEntry.permissionEntries = new Vector<>(this.permissionEntries);
/*      */       
/* 1038 */       return grantEntry;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class PrincipalEntry
/*      */     implements Principal
/*      */   {
/*      */     public static final String WILDCARD_CLASS = "WILDCARD_PRINCIPAL_CLASS";
/*      */ 
/*      */     
/*      */     public static final String WILDCARD_NAME = "WILDCARD_PRINCIPAL_NAME";
/*      */ 
/*      */     
/*      */     public static final String REPLACE_NAME = "PolicyParser.REPLACE_NAME";
/*      */ 
/*      */     
/*      */     String principalClass;
/*      */ 
/*      */     
/*      */     String principalName;
/*      */ 
/*      */     
/*      */     public PrincipalEntry(String param1String1, String param1String2) {
/* 1063 */       if (param1String1 == null || param1String2 == null) {
/* 1064 */         throw new NullPointerException(ResourcesMgr.getString("null.principalClass.or.principalName"));
/*      */       }
/* 1066 */       this.principalClass = param1String1;
/* 1067 */       this.principalName = param1String2;
/*      */     }
/*      */     
/*      */     boolean isWildcardName() {
/* 1071 */       return this.principalName.equals("WILDCARD_PRINCIPAL_NAME");
/*      */     }
/*      */     
/*      */     boolean isWildcardClass() {
/* 1075 */       return this.principalClass.equals("WILDCARD_PRINCIPAL_CLASS");
/*      */     }
/*      */     
/*      */     boolean isReplaceName() {
/* 1079 */       return this.principalClass.equals("PolicyParser.REPLACE_NAME");
/*      */     }
/*      */     
/*      */     public String getPrincipalClass() {
/* 1083 */       return this.principalClass;
/*      */     }
/*      */     
/*      */     public String getPrincipalName() {
/* 1087 */       return this.principalName;
/*      */     }
/*      */     
/*      */     public String getDisplayClass() {
/* 1091 */       if (isWildcardClass())
/* 1092 */         return "*"; 
/* 1093 */       if (isReplaceName()) {
/* 1094 */         return "";
/*      */       }
/* 1096 */       return this.principalClass;
/*      */     }
/*      */     
/*      */     public String getDisplayName() {
/* 1100 */       return getDisplayName(false);
/*      */     }
/*      */     
/*      */     public String getDisplayName(boolean param1Boolean) {
/* 1104 */       if (isWildcardName()) {
/* 1105 */         return "*";
/*      */       }
/*      */       
/* 1108 */       if (param1Boolean) return "\"" + this.principalName + "\""; 
/* 1109 */       return this.principalName;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String getName() {
/* 1115 */       return this.principalName;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1120 */       if (!isReplaceName()) {
/* 1121 */         return getDisplayClass() + "/" + getDisplayName();
/*      */       }
/* 1123 */       return getDisplayName();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 1137 */       if (this == param1Object) {
/* 1138 */         return true;
/*      */       }
/* 1140 */       if (!(param1Object instanceof PrincipalEntry)) {
/* 1141 */         return false;
/*      */       }
/* 1143 */       PrincipalEntry principalEntry = (PrincipalEntry)param1Object;
/* 1144 */       return (this.principalClass.equals(principalEntry.principalClass) && this.principalName
/* 1145 */         .equals(principalEntry.principalName));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1155 */       return this.principalClass.hashCode();
/*      */     }
/*      */     
/*      */     public void write(PrintWriter param1PrintWriter) {
/* 1159 */       param1PrintWriter.print("principal " + getDisplayClass() + " " + 
/* 1160 */           getDisplayName(true));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class PermissionEntry
/*      */   {
/*      */     public String permission;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String name;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String action;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String signedBy;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PermissionEntry() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PermissionEntry(String param1String1, String param1String2, String param1String3) {
/* 1199 */       this.permission = param1String1;
/* 1200 */       this.name = param1String2;
/* 1201 */       this.action = param1String3;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1210 */       int i = this.permission.hashCode();
/* 1211 */       if (this.name != null) i ^= this.name.hashCode(); 
/* 1212 */       if (this.action != null) i ^= this.action.hashCode(); 
/* 1213 */       return i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 1218 */       if (param1Object == this) {
/* 1219 */         return true;
/*      */       }
/* 1221 */       if (!(param1Object instanceof PermissionEntry)) {
/* 1222 */         return false;
/*      */       }
/* 1224 */       PermissionEntry permissionEntry = (PermissionEntry)param1Object;
/*      */       
/* 1226 */       if (this.permission == null)
/* 1227 */       { if (permissionEntry.permission != null) return false;
/*      */          }
/* 1229 */       else if (!this.permission.equals(permissionEntry.permission)) { return false; }
/*      */ 
/*      */       
/* 1232 */       if (this.name == null)
/* 1233 */       { if (permissionEntry.name != null) return false;
/*      */          }
/* 1235 */       else if (!this.name.equals(permissionEntry.name)) { return false; }
/*      */ 
/*      */       
/* 1238 */       if (this.action == null)
/* 1239 */       { if (permissionEntry.action != null) return false;
/*      */          }
/* 1241 */       else if (!this.action.equals(permissionEntry.action)) { return false; }
/*      */ 
/*      */       
/* 1244 */       if (this.signedBy == null)
/* 1245 */       { if (permissionEntry.signedBy != null) return false;
/*      */          }
/* 1247 */       else if (!this.signedBy.equals(permissionEntry.signedBy)) { return false; }
/*      */ 
/*      */ 
/*      */       
/* 1251 */       return true;
/*      */     }
/*      */     
/*      */     public void write(PrintWriter param1PrintWriter) {
/* 1255 */       param1PrintWriter.print("permission ");
/* 1256 */       param1PrintWriter.print(this.permission);
/* 1257 */       if (this.name != null) {
/* 1258 */         param1PrintWriter.print(" \"");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1266 */         param1PrintWriter.print(this.name.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\\"", "\\\\\\\""));
/* 1267 */         param1PrintWriter.print('"');
/*      */       } 
/* 1269 */       if (this.action != null) {
/* 1270 */         param1PrintWriter.print(", \"");
/* 1271 */         param1PrintWriter.print(this.action);
/* 1272 */         param1PrintWriter.print('"');
/*      */       } 
/* 1274 */       if (this.signedBy != null) {
/* 1275 */         param1PrintWriter.print(", signedBy \"");
/* 1276 */         param1PrintWriter.print(this.signedBy);
/* 1277 */         param1PrintWriter.print('"');
/*      */       } 
/* 1279 */       param1PrintWriter.println(";");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class DomainEntry
/*      */   {
/*      */     private final String name;
/*      */     
/*      */     private final Map<String, String> properties;
/*      */     
/*      */     private final Map<String, PolicyParser.KeyStoreEntry> entries;
/*      */     
/*      */     DomainEntry(String param1String, Map<String, String> param1Map) {
/* 1293 */       this.name = param1String;
/* 1294 */       this.properties = param1Map;
/* 1295 */       this.entries = new HashMap<>();
/*      */     }
/*      */     
/*      */     String getName() {
/* 1299 */       return this.name;
/*      */     }
/*      */     
/*      */     Map<String, String> getProperties() {
/* 1303 */       return this.properties;
/*      */     }
/*      */     
/*      */     Collection<PolicyParser.KeyStoreEntry> getEntries() {
/* 1307 */       return this.entries.values();
/*      */     }
/*      */     
/*      */     void add(PolicyParser.KeyStoreEntry param1KeyStoreEntry) throws PolicyParser.ParsingException {
/* 1311 */       String str = param1KeyStoreEntry.getName();
/* 1312 */       if (!this.entries.containsKey(str)) {
/* 1313 */         this.entries.put(str, param1KeyStoreEntry);
/*      */       } else {
/* 1315 */         MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("duplicate.keystore.name"));
/*      */         
/* 1317 */         Object[] arrayOfObject = { str };
/* 1318 */         throw new PolicyParser.ParsingException(messageFormat.format(arrayOfObject));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1325 */       StringBuilder stringBuilder = (new StringBuilder("\ndomain ")).append(this.name);
/*      */       
/* 1327 */       if (this.properties != null)
/*      */       {
/* 1329 */         for (Map.Entry<String, String> entry : this.properties.entrySet()) {
/* 1330 */           stringBuilder.append("\n        ").append((String)entry.getKey()).append('=')
/* 1331 */             .append((String)entry.getValue());
/*      */         }
/*      */       }
/* 1334 */       stringBuilder.append(" {\n");
/*      */       
/* 1336 */       if (this.entries != null) {
/* 1337 */         for (PolicyParser.KeyStoreEntry keyStoreEntry : this.entries.values()) {
/* 1338 */           stringBuilder.append(keyStoreEntry).append("\n");
/*      */         }
/*      */       }
/* 1341 */       stringBuilder.append("}");
/*      */       
/* 1343 */       return stringBuilder.toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class KeyStoreEntry
/*      */   {
/*      */     private final String name;
/*      */     
/*      */     private final Map<String, String> properties;
/*      */ 
/*      */     
/*      */     KeyStoreEntry(String param1String, Map<String, String> param1Map) {
/* 1357 */       this.name = param1String;
/* 1358 */       this.properties = param1Map;
/*      */     }
/*      */     
/*      */     String getName() {
/* 1362 */       return this.name;
/*      */     }
/*      */     
/*      */     Map<String, String> getProperties() {
/* 1366 */       return this.properties;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1371 */       StringBuilder stringBuilder = (new StringBuilder("\n    keystore ")).append(this.name);
/* 1372 */       if (this.properties != null)
/*      */       {
/* 1374 */         for (Map.Entry<String, String> entry : this.properties.entrySet()) {
/* 1375 */           stringBuilder.append("\n        ").append((String)entry.getKey()).append('=')
/* 1376 */             .append((String)entry.getValue());
/*      */         }
/*      */       }
/* 1379 */       stringBuilder.append(";");
/*      */       
/* 1381 */       return stringBuilder.toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class ParsingException
/*      */     extends GeneralSecurityException
/*      */   {
/*      */     private static final long serialVersionUID = -4330692689482574072L;
/*      */ 
/*      */ 
/*      */     
/*      */     private String i18nMessage;
/*      */ 
/*      */ 
/*      */     
/*      */     public ParsingException(String param1String) {
/* 1400 */       super(param1String);
/* 1401 */       this.i18nMessage = param1String;
/*      */     }
/*      */     
/*      */     public ParsingException(int param1Int, String param1String) {
/* 1405 */       super("line " + param1Int + ": " + param1String);
/*      */       
/* 1407 */       MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("line.number.msg"));
/* 1408 */       Object[] arrayOfObject = { new Integer(param1Int), param1String };
/* 1409 */       this.i18nMessage = messageFormat.format(arrayOfObject);
/*      */     }
/*      */     
/*      */     public ParsingException(int param1Int, String param1String1, String param1String2) {
/* 1413 */       super("line " + param1Int + ": expected [" + param1String1 + "], found [" + param1String2 + "]");
/*      */ 
/*      */       
/* 1416 */       MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("line.number.expected.expect.found.actual."));
/* 1417 */       Object[] arrayOfObject = { new Integer(param1Int), param1String1, param1String2 };
/* 1418 */       this.i18nMessage = messageFormat.format(arrayOfObject);
/*      */     }
/*      */ 
/*      */     
/*      */     public String getLocalizedMessage() {
/* 1423 */       return this.i18nMessage;
/*      */     }
/*      */   }
/*      */   
/*      */   public static void main(String[] paramArrayOfString) throws Exception {
/* 1428 */     try(FileReader null = new FileReader(paramArrayOfString[0]); 
/* 1429 */         FileWriter null = new FileWriter(paramArrayOfString[1])) {
/* 1430 */       PolicyParser policyParser = new PolicyParser(true);
/* 1431 */       policyParser.read(fileReader);
/* 1432 */       policyParser.write(fileWriter);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/PolicyParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */