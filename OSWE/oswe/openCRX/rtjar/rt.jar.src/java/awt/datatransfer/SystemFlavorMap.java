/*      */ package java.awt.datatransfer;
/*      */ 
/*      */ import java.awt.Toolkit;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.datatransfer.DataTransferer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class SystemFlavorMap
/*      */   implements FlavorMap, FlavorTable
/*      */ {
/*   69 */   private static String JavaMIME = "JAVA_DATAFLAVOR:";
/*      */   
/*   71 */   private static final Object FLAVOR_MAP_KEY = new Object();
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String keyValueSeparators = "=: \t\r\n\f";
/*      */ 
/*      */   
/*      */   private static final String strictKeyValueSeparators = "=:";
/*      */ 
/*      */   
/*      */   private static final String whiteSpaceChars = " \t\r\n\f";
/*      */ 
/*      */   
/*   84 */   private static final String[] UNICODE_TEXT_CLASSES = new String[] { "java.io.Reader", "java.lang.String", "java.nio.CharBuffer", "\"[C\"" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   92 */   private static final String[] ENCODED_TEXT_CLASSES = new String[] { "java.io.InputStream", "java.nio.ByteBuffer", "\"[B\"" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String TEXT_PLAIN_BASE_TYPE = "text/plain";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String HTML_TEXT_BASE_TYPE = "text/html";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  111 */   private final Map<String, LinkedHashSet<DataFlavor>> nativeToFlavor = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<String, LinkedHashSet<DataFlavor>> getNativeToFlavor() {
/*  121 */     if (!this.isMapInitialized) {
/*  122 */       initSystemFlavorMap();
/*      */     }
/*  124 */     return this.nativeToFlavor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  132 */   private final Map<DataFlavor, LinkedHashSet<String>> flavorToNative = (Map<DataFlavor, LinkedHashSet<String>>)new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized Map<DataFlavor, LinkedHashSet<String>> getFlavorToNative() {
/*  142 */     if (!this.isMapInitialized) {
/*  143 */       initSystemFlavorMap();
/*      */     }
/*  145 */     return this.flavorToNative;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  153 */   private Map<String, LinkedHashSet<String>> textTypeToNative = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isMapInitialized = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized Map<String, LinkedHashSet<String>> getTextTypeToNative() {
/*  168 */     if (!this.isMapInitialized) {
/*  169 */       initSystemFlavorMap();
/*      */       
/*  171 */       this.textTypeToNative = Collections.unmodifiableMap(this.textTypeToNative);
/*      */     } 
/*  173 */     return this.textTypeToNative;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  180 */   private final SoftCache<DataFlavor, String> nativesForFlavorCache = new SoftCache<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  186 */   private final SoftCache<String, DataFlavor> flavorsForNativeCache = new SoftCache<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  194 */   private Set<Object> disabledMappingGenerationKeys = new HashSet();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FlavorMap getDefaultFlavorMap() {
/*  200 */     AppContext appContext = AppContext.getAppContext();
/*  201 */     FlavorMap flavorMap = (FlavorMap)appContext.get(FLAVOR_MAP_KEY);
/*  202 */     if (flavorMap == null) {
/*  203 */       flavorMap = new SystemFlavorMap();
/*  204 */       appContext.put(FLAVOR_MAP_KEY, flavorMap);
/*      */     } 
/*  206 */     return flavorMap;
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
/*      */   private void initSystemFlavorMap() {
/*  218 */     if (this.isMapInitialized) {
/*      */       return;
/*      */     }
/*      */     
/*  222 */     this.isMapInitialized = true;
/*      */     
/*  224 */     BufferedReader bufferedReader1 = AccessController.<BufferedReader>doPrivileged(new PrivilegedAction<BufferedReader>()
/*      */         {
/*      */           public BufferedReader run()
/*      */           {
/*  228 */             String str = System.getProperty("java.home") + File.separator + "lib" + File.separator + "flavormap.properties";
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             try {
/*  234 */               return new BufferedReader(new InputStreamReader((new File(str))
/*      */                     
/*  236 */                     .toURI().toURL().openStream(), "ISO-8859-1"));
/*  237 */             } catch (MalformedURLException malformedURLException) {
/*  238 */               System.err.println("MalformedURLException:" + malformedURLException + " while loading default flavormap.properties file:" + str);
/*  239 */             } catch (IOException iOException) {
/*  240 */               System.err.println("IOException:" + iOException + " while loading default flavormap.properties file:" + str);
/*      */             } 
/*  242 */             return null;
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  247 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*      */         {
/*      */           public String run() {
/*  250 */             return Toolkit.getProperty("AWT.DnD.flavorMapFileURL", null);
/*      */           }
/*      */         });
/*      */     
/*  254 */     if (bufferedReader1 != null) {
/*      */       try {
/*  256 */         parseAndStoreReader(bufferedReader1);
/*  257 */       } catch (IOException iOException) {
/*  258 */         System.err.println("IOException:" + iOException + " while parsing default flavormap.properties file");
/*      */       } 
/*      */     }
/*      */     
/*  262 */     BufferedReader bufferedReader2 = null;
/*  263 */     if (str != null) {
/*      */       try {
/*  265 */         bufferedReader2 = new BufferedReader(new InputStreamReader((new URL(str)).openStream(), "ISO-8859-1"));
/*  266 */       } catch (MalformedURLException malformedURLException) {
/*  267 */         System.err.println("MalformedURLException:" + malformedURLException + " while reading AWT.DnD.flavorMapFileURL:" + str);
/*  268 */       } catch (IOException iOException) {
/*  269 */         System.err.println("IOException:" + iOException + " while reading AWT.DnD.flavorMapFileURL:" + str);
/*  270 */       } catch (SecurityException securityException) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  275 */     if (bufferedReader2 != null) {
/*      */       try {
/*  277 */         parseAndStoreReader(bufferedReader2);
/*  278 */       } catch (IOException iOException) {
/*  279 */         System.err.println("IOException:" + iOException + " while parsing AWT.DnD.flavorMapFileURL");
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parseAndStoreReader(BufferedReader paramBufferedReader) throws IOException {
/*      */     while (true) {
/*  290 */       String str = paramBufferedReader.readLine();
/*  291 */       if (str == null) {
/*      */         return;
/*      */       }
/*      */       
/*  295 */       if (str.length() > 0) {
/*      */         
/*  297 */         char c = str.charAt(0);
/*  298 */         if (c != '#' && c != '!') {
/*  299 */           DataFlavor dataFlavor; while (continueLine(str)) {
/*  300 */             String str3 = paramBufferedReader.readLine();
/*  301 */             if (str3 == null) {
/*  302 */               str3 = "";
/*      */             }
/*      */             
/*  305 */             String str4 = str.substring(0, str.length() - 1);
/*      */             
/*  307 */             byte b = 0;
/*  308 */             for (; b < str3.length() && 
/*  309 */               " \t\r\n\f"
/*  310 */               .indexOf(str3.charAt(b)) != -1; b++);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  315 */             str3 = str3.substring(b, str3
/*  316 */                 .length());
/*  317 */             str = str4 + str3;
/*      */           } 
/*      */ 
/*      */           
/*  321 */           int i = str.length();
/*  322 */           byte b1 = 0;
/*  323 */           for (; b1 < i && 
/*  324 */             " \t\r\n\f"
/*  325 */             .indexOf(str.charAt(b1)) != -1; b1++);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  331 */           if (b1 == i) {
/*      */             continue;
/*      */           }
/*      */ 
/*      */           
/*  336 */           byte b2 = b1;
/*  337 */           for (; b2 < i; b2++) {
/*  338 */             char c1 = str.charAt(b2);
/*  339 */             if (c1 == '\\') {
/*  340 */               b2++;
/*  341 */             } else if ("=: \t\r\n\f"
/*  342 */               .indexOf(c1) != -1) {
/*      */               break;
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*  348 */           byte b3 = b2;
/*  349 */           for (; b3 < i && 
/*  350 */             " \t\r\n\f"
/*  351 */             .indexOf(str.charAt(b3)) != -1; b3++);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  357 */           if (b3 < i && 
/*  358 */             "=:"
/*  359 */             .indexOf(str.charAt(b3)) != -1) {
/*  360 */             b3++;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  365 */           while (b3 < i && 
/*  366 */             " \t\r\n\f"
/*  367 */             .indexOf(str.charAt(b3)) != -1)
/*      */           {
/*      */             
/*  370 */             b3++;
/*      */           }
/*      */           
/*  373 */           String str1 = str.substring(b1, b2);
/*      */           
/*  375 */           String str2 = (b2 < i) ? str.substring(b3, i) : "";
/*      */ 
/*      */ 
/*      */           
/*  379 */           str1 = loadConvert(str1);
/*  380 */           str2 = loadConvert(str2);
/*      */           
/*      */           try {
/*  383 */             MimeType mimeType = new MimeType(str2);
/*  384 */             if ("text".equals(mimeType.getPrimaryType())) {
/*  385 */               String str3 = mimeType.getParameter("charset");
/*      */               
/*  387 */               if (DataTransferer.doesSubtypeSupportCharset(mimeType.getSubType(), str3)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  394 */                 DataTransferer dataTransferer = DataTransferer.getInstance();
/*  395 */                 if (dataTransferer != null) {
/*  396 */                   dataTransferer
/*  397 */                     .registerTextFlavorProperties(str1, str3, mimeType
/*  398 */                       .getParameter("eoln"), mimeType
/*  399 */                       .getParameter("terminators"));
/*      */                 }
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  407 */               mimeType.removeParameter("charset");
/*  408 */               mimeType.removeParameter("class");
/*  409 */               mimeType.removeParameter("eoln");
/*  410 */               mimeType.removeParameter("terminators");
/*  411 */               str2 = mimeType.toString();
/*      */             } 
/*  413 */           } catch (MimeTypeParseException mimeTypeParseException) {
/*  414 */             mimeTypeParseException.printStackTrace();
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/*      */           try {
/*  420 */             dataFlavor = new DataFlavor(str2);
/*  421 */           } catch (Exception exception) {
/*      */             try {
/*  423 */               dataFlavor = new DataFlavor(str2, null);
/*  424 */             } catch (Exception exception1) {
/*  425 */               exception1.printStackTrace();
/*      */               
/*      */               continue;
/*      */             } 
/*      */           } 
/*  430 */           LinkedHashSet<DataFlavor> linkedHashSet = new LinkedHashSet();
/*  431 */           linkedHashSet.add(dataFlavor);
/*      */           
/*  433 */           if ("text".equals(dataFlavor.getPrimaryType())) {
/*  434 */             linkedHashSet.addAll(convertMimeTypeToDataFlavors(str2));
/*  435 */             store(dataFlavor.mimeType.getBaseType(), str1, getTextTypeToNative());
/*      */           } 
/*      */           
/*  438 */           for (DataFlavor dataFlavor1 : linkedHashSet) {
/*  439 */             store(dataFlavor1, str1, getFlavorToNative());
/*  440 */             store(str1, dataFlavor1, getNativeToFlavor());
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean continueLine(String paramString) {
/*  451 */     byte b = 0;
/*  452 */     int i = paramString.length() - 1;
/*  453 */     while (i >= 0 && paramString.charAt(i--) == '\\') {
/*  454 */       b++;
/*      */     }
/*  456 */     return (b % 2 == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String loadConvert(String paramString) {
/*  464 */     int i = paramString.length();
/*  465 */     StringBuilder stringBuilder = new StringBuilder(i);
/*      */     
/*  467 */     for (byte b = 0; b < i; ) {
/*  468 */       char c = paramString.charAt(b++);
/*  469 */       if (c == '\\') {
/*  470 */         c = paramString.charAt(b++);
/*  471 */         if (c == 'u') {
/*      */           
/*  473 */           int j = 0;
/*  474 */           for (byte b1 = 0; b1 < 4; b1++) {
/*  475 */             c = paramString.charAt(b++);
/*  476 */             switch (c) { case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7':
/*      */               case '8':
/*      */               case '9':
/*  479 */                 j = (j << 4) + c - 48; break;
/*      */               case 'a': case 'b': case 'c':
/*      */               case 'd':
/*      */               case 'e':
/*      */               case 'f':
/*  484 */                 j = (j << 4) + 10 + c - 97; break;
/*      */               case 'A': case 'B': case 'C':
/*      */               case 'D':
/*      */               case 'E':
/*      */               case 'F':
/*  489 */                 j = (j << 4) + 10 + c - 65;
/*      */                 break;
/*      */               
/*      */               default:
/*  493 */                 throw new IllegalArgumentException("Malformed \\uxxxx encoding."); }
/*      */ 
/*      */ 
/*      */           
/*      */           } 
/*  498 */           stringBuilder.append((char)j); continue;
/*      */         } 
/*  500 */         if (c == 't') {
/*  501 */           c = '\t';
/*  502 */         } else if (c == 'r') {
/*  503 */           c = '\r';
/*  504 */         } else if (c == 'n') {
/*  505 */           c = '\n';
/*  506 */         } else if (c == 'f') {
/*  507 */           c = '\f';
/*      */         } 
/*  509 */         stringBuilder.append(c);
/*      */         continue;
/*      */       } 
/*  512 */       stringBuilder.append(c);
/*      */     } 
/*      */     
/*  515 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <H, L> void store(H paramH, L paramL, Map<H, LinkedHashSet<L>> paramMap) {
/*  525 */     LinkedHashSet<L> linkedHashSet = paramMap.get(paramH);
/*  526 */     if (linkedHashSet == null) {
/*  527 */       linkedHashSet = new LinkedHashSet(1);
/*  528 */       paramMap.put(paramH, linkedHashSet);
/*      */     } 
/*  530 */     if (!linkedHashSet.contains(paramL)) {
/*  531 */       linkedHashSet.add(paramL);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LinkedHashSet<DataFlavor> nativeToFlavorLookup(String paramString) {
/*  542 */     LinkedHashSet<? extends DataFlavor> linkedHashSet = getNativeToFlavor().get(paramString);
/*      */ 
/*      */     
/*  545 */     if (paramString != null && !this.disabledMappingGenerationKeys.contains(paramString)) {
/*  546 */       DataTransferer dataTransferer = DataTransferer.getInstance();
/*  547 */       if (dataTransferer != null) {
/*      */         
/*  549 */         LinkedHashSet<DataFlavor> linkedHashSet1 = dataTransferer.getPlatformMappingsForNative(paramString);
/*  550 */         if (!linkedHashSet1.isEmpty()) {
/*  551 */           if (linkedHashSet != null)
/*      */           {
/*      */ 
/*      */ 
/*      */             
/*  556 */             linkedHashSet1.addAll(linkedHashSet);
/*      */           }
/*  558 */           linkedHashSet = linkedHashSet1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  563 */     if (linkedHashSet == null && isJavaMIMEType(paramString)) {
/*  564 */       String str = decodeJavaMIMEType(paramString);
/*  565 */       DataFlavor dataFlavor = null;
/*      */       
/*      */       try {
/*  568 */         dataFlavor = new DataFlavor(str);
/*  569 */       } catch (Exception exception) {
/*  570 */         System.err.println("Exception \"" + exception.getClass().getName() + ": " + exception
/*  571 */             .getMessage() + "\"while constructing DataFlavor for: " + str);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  576 */       if (dataFlavor != null) {
/*  577 */         linkedHashSet = new LinkedHashSet<>(1);
/*  578 */         getNativeToFlavor().put(paramString, linkedHashSet);
/*  579 */         linkedHashSet.add(dataFlavor);
/*  580 */         this.flavorsForNativeCache.remove(paramString);
/*      */         
/*  582 */         LinkedHashSet<String> linkedHashSet1 = getFlavorToNative().get(dataFlavor);
/*  583 */         if (linkedHashSet1 == null) {
/*  584 */           linkedHashSet1 = new LinkedHashSet(1);
/*  585 */           getFlavorToNative().put(dataFlavor, linkedHashSet1);
/*      */         } 
/*  587 */         linkedHashSet1.add(paramString);
/*  588 */         this.nativesForFlavorCache.remove(dataFlavor);
/*      */       } 
/*      */     } 
/*      */     
/*  592 */     return (linkedHashSet != null) ? (LinkedHashSet)linkedHashSet : new LinkedHashSet<>(0);
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
/*      */   private LinkedHashSet<String> flavorToNativeLookup(DataFlavor paramDataFlavor, boolean paramBoolean) {
/*  606 */     LinkedHashSet<? extends String> linkedHashSet = getFlavorToNative().get(paramDataFlavor);
/*      */     
/*  608 */     if (paramDataFlavor != null && !this.disabledMappingGenerationKeys.contains(paramDataFlavor)) {
/*  609 */       DataTransferer dataTransferer = DataTransferer.getInstance();
/*  610 */       if (dataTransferer != null) {
/*      */         
/*  612 */         LinkedHashSet<String> linkedHashSet1 = dataTransferer.getPlatformMappingsForFlavor(paramDataFlavor);
/*  613 */         if (!linkedHashSet1.isEmpty()) {
/*  614 */           if (linkedHashSet != null)
/*      */           {
/*      */ 
/*      */ 
/*      */             
/*  619 */             linkedHashSet1.addAll(linkedHashSet);
/*      */           }
/*  621 */           linkedHashSet = linkedHashSet1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  626 */     if (linkedHashSet == null) {
/*  627 */       if (paramBoolean) {
/*  628 */         String str = encodeDataFlavor(paramDataFlavor);
/*  629 */         linkedHashSet = new LinkedHashSet<>(1);
/*  630 */         getFlavorToNative().put(paramDataFlavor, linkedHashSet);
/*  631 */         linkedHashSet.add(str);
/*      */         
/*  633 */         LinkedHashSet<DataFlavor> linkedHashSet1 = getNativeToFlavor().get(str);
/*  634 */         if (linkedHashSet1 == null) {
/*  635 */           linkedHashSet1 = new LinkedHashSet(1);
/*  636 */           getNativeToFlavor().put(str, linkedHashSet1);
/*      */         } 
/*  638 */         linkedHashSet1.add(paramDataFlavor);
/*      */         
/*  640 */         this.nativesForFlavorCache.remove(paramDataFlavor);
/*  641 */         this.flavorsForNativeCache.remove(str);
/*      */       } else {
/*  643 */         linkedHashSet = new LinkedHashSet<>(0);
/*      */       } 
/*      */     }
/*      */     
/*  647 */     return new LinkedHashSet<>(linkedHashSet);
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
/*      */   public synchronized List<String> getNativesForFlavor(DataFlavor paramDataFlavor) {
/*  677 */     LinkedHashSet<String> linkedHashSet = this.nativesForFlavorCache.check(paramDataFlavor);
/*  678 */     if (linkedHashSet != null) {
/*  679 */       return new ArrayList<>(linkedHashSet);
/*      */     }
/*      */     
/*  682 */     if (paramDataFlavor == null) {
/*  683 */       linkedHashSet = new LinkedHashSet<>(getNativeToFlavor().keySet());
/*  684 */     } else if (this.disabledMappingGenerationKeys.contains(paramDataFlavor)) {
/*      */ 
/*      */       
/*  687 */       linkedHashSet = flavorToNativeLookup(paramDataFlavor, false);
/*  688 */     } else if (DataTransferer.isFlavorCharsetTextType(paramDataFlavor)) {
/*  689 */       linkedHashSet = new LinkedHashSet<>(0);
/*      */ 
/*      */ 
/*      */       
/*  693 */       if ("text".equals(paramDataFlavor.getPrimaryType())) {
/*      */         
/*  695 */         LinkedHashSet<? extends String> linkedHashSet2 = getTextTypeToNative().get(paramDataFlavor.mimeType.getBaseType());
/*  696 */         if (linkedHashSet2 != null) {
/*  697 */           linkedHashSet.addAll(linkedHashSet2);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  703 */       LinkedHashSet<? extends String> linkedHashSet1 = getTextTypeToNative().get("text/plain");
/*  704 */       if (linkedHashSet1 != null) {
/*  705 */         linkedHashSet.addAll(linkedHashSet1);
/*      */       }
/*      */       
/*  708 */       if (linkedHashSet.isEmpty()) {
/*  709 */         linkedHashSet = flavorToNativeLookup(paramDataFlavor, true);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  714 */         linkedHashSet.addAll(flavorToNativeLookup(paramDataFlavor, false));
/*      */       } 
/*  716 */     } else if (DataTransferer.isFlavorNoncharsetTextType(paramDataFlavor)) {
/*  717 */       linkedHashSet = getTextTypeToNative().get(paramDataFlavor.mimeType.getBaseType());
/*      */       
/*  719 */       if (linkedHashSet == null || linkedHashSet.isEmpty()) {
/*  720 */         linkedHashSet = flavorToNativeLookup(paramDataFlavor, true);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  725 */         linkedHashSet.addAll(flavorToNativeLookup(paramDataFlavor, false));
/*      */       } 
/*      */     } else {
/*  728 */       linkedHashSet = flavorToNativeLookup(paramDataFlavor, true);
/*      */     } 
/*      */     
/*  731 */     this.nativesForFlavorCache.put(paramDataFlavor, linkedHashSet);
/*      */     
/*  733 */     return new ArrayList<>(linkedHashSet);
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
/*      */   public synchronized List<DataFlavor> getFlavorsForNative(String paramString) {
/*  769 */     LinkedHashSet<DataFlavor> linkedHashSet = this.flavorsForNativeCache.check(paramString);
/*  770 */     if (linkedHashSet != null) {
/*  771 */       return new ArrayList<>(linkedHashSet);
/*      */     }
/*  773 */     linkedHashSet = new LinkedHashSet<>();
/*      */ 
/*      */     
/*  776 */     if (paramString == null) {
/*  777 */       for (String str : getNativesForFlavor(null)) {
/*  778 */         linkedHashSet.addAll(getFlavorsForNative(str));
/*      */       }
/*      */     } else {
/*  781 */       LinkedHashSet<DataFlavor> linkedHashSet1 = nativeToFlavorLookup(paramString);
/*  782 */       if (this.disabledMappingGenerationKeys.contains(paramString)) {
/*  783 */         return new ArrayList<>(linkedHashSet1);
/*      */       }
/*      */ 
/*      */       
/*  787 */       LinkedHashSet<DataFlavor> linkedHashSet2 = nativeToFlavorLookup(paramString);
/*      */       
/*  789 */       for (DataFlavor dataFlavor : linkedHashSet2) {
/*  790 */         linkedHashSet.add(dataFlavor);
/*  791 */         if ("text".equals(dataFlavor.getPrimaryType())) {
/*  792 */           String str = dataFlavor.mimeType.getBaseType();
/*  793 */           linkedHashSet.addAll(convertMimeTypeToDataFlavors(str));
/*      */         } 
/*      */       } 
/*      */     } 
/*  797 */     this.flavorsForNativeCache.put(paramString, linkedHashSet);
/*  798 */     return new ArrayList<>(linkedHashSet);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Set<DataFlavor> convertMimeTypeToDataFlavors(String paramString) {
/*  804 */     LinkedHashSet<DataFlavor> linkedHashSet = new LinkedHashSet();
/*      */     
/*  806 */     String str = null;
/*      */     
/*      */     try {
/*  809 */       MimeType mimeType = new MimeType(paramString);
/*  810 */       str = mimeType.getSubType();
/*  811 */     } catch (MimeTypeParseException mimeTypeParseException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  816 */     if (DataTransferer.doesSubtypeSupportCharset(str, null)) {
/*  817 */       if ("text/plain".equals(paramString))
/*      */       {
/*  819 */         linkedHashSet.add(DataFlavor.stringFlavor);
/*      */       }
/*      */       
/*  822 */       for (String str1 : UNICODE_TEXT_CLASSES) {
/*  823 */         String str2 = paramString + ";charset=Unicode;class=" + str1;
/*      */ 
/*      */ 
/*      */         
/*  827 */         LinkedHashSet<String> linkedHashSet1 = handleHtmlMimeTypes(paramString, str2);
/*  828 */         for (String str3 : linkedHashSet1) {
/*  829 */           DataFlavor dataFlavor = null;
/*      */           try {
/*  831 */             dataFlavor = new DataFlavor(str3);
/*  832 */           } catch (ClassNotFoundException classNotFoundException) {}
/*      */           
/*  834 */           linkedHashSet.add(dataFlavor);
/*      */         } 
/*      */       } 
/*      */       
/*  838 */       for (String str1 : DataTransferer.standardEncodings()) {
/*      */         
/*  840 */         for (String str2 : ENCODED_TEXT_CLASSES) {
/*  841 */           String str3 = paramString + ";charset=" + str1 + ";class=" + str2;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  846 */           LinkedHashSet<String> linkedHashSet1 = handleHtmlMimeTypes(paramString, str3);
/*      */           
/*  848 */           for (String str4 : linkedHashSet1) {
/*      */             
/*  850 */             DataFlavor dataFlavor = null;
/*      */             
/*      */             try {
/*  853 */               dataFlavor = new DataFlavor(str4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  859 */               if (dataFlavor.equals(DataFlavor.plainTextFlavor)) {
/*  860 */                 dataFlavor = DataFlavor.plainTextFlavor;
/*      */               }
/*  862 */             } catch (ClassNotFoundException classNotFoundException) {}
/*      */ 
/*      */             
/*  865 */             linkedHashSet.add(dataFlavor);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  870 */       if ("text/plain".equals(paramString))
/*      */       {
/*  872 */         linkedHashSet.add(DataFlavor.plainTextFlavor);
/*      */       
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  878 */       for (String str1 : ENCODED_TEXT_CLASSES) {
/*  879 */         DataFlavor dataFlavor = null;
/*      */         try {
/*  881 */           dataFlavor = new DataFlavor(paramString + ";class=" + str1);
/*      */         }
/*  883 */         catch (ClassNotFoundException classNotFoundException) {}
/*      */         
/*  885 */         linkedHashSet.add(dataFlavor);
/*      */       } 
/*      */     } 
/*  888 */     return linkedHashSet;
/*      */   }
/*      */   
/*  891 */   private static final String[] htmlDocumntTypes = new String[] { "all", "selection", "fragment" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static LinkedHashSet<String> handleHtmlMimeTypes(String paramString1, String paramString2) {
/*  897 */     LinkedHashSet<String> linkedHashSet = new LinkedHashSet();
/*      */     
/*  899 */     if ("text/html".equals(paramString1)) {
/*  900 */       for (String str : htmlDocumntTypes) {
/*  901 */         linkedHashSet.add(paramString2 + ";document=" + str);
/*      */       }
/*      */     } else {
/*  904 */       linkedHashSet.add(paramString2);
/*      */     } 
/*      */     
/*  907 */     return linkedHashSet;
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
/*      */   public synchronized Map<DataFlavor, String> getNativesForFlavors(DataFlavor[] paramArrayOfDataFlavor) {
/*  938 */     if (paramArrayOfDataFlavor == null) {
/*  939 */       List<DataFlavor> list = getFlavorsForNative(null);
/*  940 */       paramArrayOfDataFlavor = new DataFlavor[list.size()];
/*  941 */       list.toArray(paramArrayOfDataFlavor);
/*      */     } 
/*      */     
/*  944 */     HashMap<Object, Object> hashMap = new HashMap<>(paramArrayOfDataFlavor.length, 1.0F);
/*  945 */     for (DataFlavor dataFlavor : paramArrayOfDataFlavor) {
/*  946 */       List<String> list = getNativesForFlavor(dataFlavor);
/*  947 */       String str = list.isEmpty() ? null : list.get(0);
/*  948 */       hashMap.put(dataFlavor, str);
/*      */     } 
/*      */     
/*  951 */     return (Map)hashMap;
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
/*      */   public synchronized Map<String, DataFlavor> getFlavorsForNatives(String[] paramArrayOfString) {
/*  982 */     if (paramArrayOfString == null) {
/*  983 */       List<String> list = getNativesForFlavor(null);
/*  984 */       paramArrayOfString = new String[list.size()];
/*  985 */       list.toArray(paramArrayOfString);
/*      */     } 
/*      */     
/*  988 */     HashMap<Object, Object> hashMap = new HashMap<>(paramArrayOfString.length, 1.0F);
/*  989 */     for (String str : paramArrayOfString) {
/*  990 */       List<DataFlavor> list = getFlavorsForNative(str);
/*  991 */       DataFlavor dataFlavor = list.isEmpty() ? null : list.get(0);
/*  992 */       hashMap.put(str, dataFlavor);
/*      */     } 
/*  994 */     return (Map)hashMap;
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
/*      */   public synchronized void addUnencodedNativeForFlavor(DataFlavor paramDataFlavor, String paramString) {
/* 1019 */     Objects.requireNonNull(paramString, "Null native not permitted");
/* 1020 */     Objects.requireNonNull(paramDataFlavor, "Null flavor not permitted");
/*      */     
/* 1022 */     LinkedHashSet<String> linkedHashSet = getFlavorToNative().get(paramDataFlavor);
/* 1023 */     if (linkedHashSet == null) {
/* 1024 */       linkedHashSet = new LinkedHashSet(1);
/* 1025 */       getFlavorToNative().put(paramDataFlavor, linkedHashSet);
/*      */     } 
/* 1027 */     linkedHashSet.add(paramString);
/* 1028 */     this.nativesForFlavorCache.remove(paramDataFlavor);
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
/*      */   public synchronized void setNativesForFlavor(DataFlavor paramDataFlavor, String[] paramArrayOfString) {
/* 1061 */     Objects.requireNonNull(paramArrayOfString, "Null natives not permitted");
/* 1062 */     Objects.requireNonNull(paramDataFlavor, "Null flavors not permitted");
/*      */     
/* 1064 */     getFlavorToNative().remove(paramDataFlavor);
/* 1065 */     for (String str : paramArrayOfString) {
/* 1066 */       addUnencodedNativeForFlavor(paramDataFlavor, str);
/*      */     }
/* 1068 */     this.disabledMappingGenerationKeys.add(paramDataFlavor);
/* 1069 */     this.nativesForFlavorCache.remove(paramDataFlavor);
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
/*      */   public synchronized void addFlavorForUnencodedNative(String paramString, DataFlavor paramDataFlavor) {
/* 1092 */     Objects.requireNonNull(paramString, "Null native not permitted");
/* 1093 */     Objects.requireNonNull(paramDataFlavor, "Null flavor not permitted");
/*      */     
/* 1095 */     LinkedHashSet<DataFlavor> linkedHashSet = getNativeToFlavor().get(paramString);
/* 1096 */     if (linkedHashSet == null) {
/* 1097 */       linkedHashSet = new LinkedHashSet(1);
/* 1098 */       getNativeToFlavor().put(paramString, linkedHashSet);
/*      */     } 
/* 1100 */     linkedHashSet.add(paramDataFlavor);
/* 1101 */     this.flavorsForNativeCache.remove(paramString);
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
/*      */   public synchronized void setFlavorsForNative(String paramString, DataFlavor[] paramArrayOfDataFlavor) {
/* 1133 */     Objects.requireNonNull(paramString, "Null native not permitted");
/* 1134 */     Objects.requireNonNull(paramArrayOfDataFlavor, "Null flavors not permitted");
/*      */     
/* 1136 */     getNativeToFlavor().remove(paramString);
/* 1137 */     for (DataFlavor dataFlavor : paramArrayOfDataFlavor) {
/* 1138 */       addFlavorForUnencodedNative(paramString, dataFlavor);
/*      */     }
/* 1140 */     this.disabledMappingGenerationKeys.add(paramString);
/* 1141 */     this.flavorsForNativeCache.remove(paramString);
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
/*      */   public static String encodeJavaMIMEType(String paramString) {
/* 1164 */     return (paramString != null) ? (JavaMIME + paramString) : null;
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
/*      */   public static String encodeDataFlavor(DataFlavor paramDataFlavor) {
/* 1193 */     return (paramDataFlavor != null) ? 
/* 1194 */       encodeJavaMIMEType(paramDataFlavor.getMimeType()) : null;
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
/*      */   public static boolean isJavaMIMEType(String paramString) {
/* 1207 */     return (paramString != null && paramString.startsWith(JavaMIME, 0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String decodeJavaMIMEType(String paramString) {
/* 1218 */     return isJavaMIMEType(paramString) ? paramString
/* 1219 */       .substring(JavaMIME.length(), paramString.length()).trim() : null;
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
/*      */   public static DataFlavor decodeDataFlavor(String paramString) throws ClassNotFoundException {
/* 1234 */     String str = decodeJavaMIMEType(paramString);
/* 1235 */     return (str != null) ? new DataFlavor(str) : null;
/*      */   }
/*      */   
/*      */   private static final class SoftCache<K, V> {
/*      */     Map<K, SoftReference<LinkedHashSet<V>>> cache;
/*      */     
/*      */     private SoftCache() {}
/*      */     
/*      */     public void put(K param1K, LinkedHashSet<V> param1LinkedHashSet) {
/* 1244 */       if (this.cache == null) {
/* 1245 */         this.cache = new HashMap<>(1);
/*      */       }
/* 1247 */       this.cache.put(param1K, new SoftReference<>(param1LinkedHashSet));
/*      */     }
/*      */     
/*      */     public void remove(K param1K) {
/* 1251 */       if (this.cache == null)
/* 1252 */         return;  this.cache.remove(null);
/* 1253 */       this.cache.remove(param1K);
/*      */     }
/*      */     
/*      */     public LinkedHashSet<V> check(K param1K) {
/* 1257 */       if (this.cache == null) return null; 
/* 1258 */       SoftReference<LinkedHashSet<V>> softReference = this.cache.get(param1K);
/* 1259 */       if (softReference != null) {
/* 1260 */         return softReference.get();
/*      */       }
/* 1262 */       return null;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/datatransfer/SystemFlavorMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */