/*      */ package javax.swing.text.rtf;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.StreamTokenizer;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Dictionary;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Vector;
/*      */ import javax.swing.text.AttributeSet;
/*      */ import javax.swing.text.BadLocationException;
/*      */ import javax.swing.text.MutableAttributeSet;
/*      */ import javax.swing.text.SimpleAttributeSet;
/*      */ import javax.swing.text.Style;
/*      */ import javax.swing.text.StyleConstants;
/*      */ import javax.swing.text.StyledDocument;
/*      */ import javax.swing.text.TabStop;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class RTFReader
/*      */   extends RTFParser
/*      */ {
/*      */   StyledDocument target;
/*      */   Dictionary<Object, Object> parserState;
/*      */   Destination rtfDestination;
/*      */   MutableAttributeSet documentAttributes;
/*      */   Dictionary<Integer, String> fontTable;
/*      */   Color[] colorTable;
/*      */   Style[] characterStyles;
/*      */   Style[] paragraphStyles;
/*      */   Style[] sectionStyles;
/*      */   int rtfversion;
/*      */   boolean ignoreGroupIfUnknownKeyword;
/*      */   int skippingCharacters;
/*   91 */   private static Dictionary<String, RTFAttribute> straightforwardAttributes = RTFAttributes.attributesByKeyword();
/*      */ 
/*      */ 
/*      */   
/*      */   private MockAttributeSet mockery;
/*      */ 
/*      */ 
/*      */   
/*   99 */   static Dictionary<String, String> textKeywords = null;
/*      */   static {
/*  101 */     textKeywords = new Hashtable<>();
/*  102 */     textKeywords.put("\\", "\\");
/*  103 */     textKeywords.put("{", "{");
/*  104 */     textKeywords.put("}", "}");
/*  105 */     textKeywords.put(" ", " ");
/*  106 */     textKeywords.put("~", " ");
/*  107 */     textKeywords.put("_", "‑");
/*  108 */     textKeywords.put("bullet", "•");
/*  109 */     textKeywords.put("emdash", "—");
/*  110 */     textKeywords.put("emspace", " ");
/*  111 */     textKeywords.put("endash", "–");
/*  112 */     textKeywords.put("enspace", " ");
/*  113 */     textKeywords.put("ldblquote", "“");
/*  114 */     textKeywords.put("lquote", "‘");
/*  115 */     textKeywords.put("ltrmark", "‎");
/*  116 */     textKeywords.put("rdblquote", "”");
/*  117 */     textKeywords.put("rquote", "’");
/*  118 */     textKeywords.put("rtlmark", "‏");
/*  119 */     textKeywords.put("tab", "\t");
/*  120 */     textKeywords.put("zwj", "‍");
/*  121 */     textKeywords.put("zwnj", "‌");
/*      */ 
/*      */ 
/*      */     
/*  125 */     textKeywords.put("-", "‧");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  133 */     useNeXTForAnsi = false;
/*      */     
/*  135 */     characterSets = (Dictionary)new Hashtable<>();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final String TabAlignmentKey = "tab_alignment";
/*      */   
/*      */   static final String TabLeaderKey = "tab_leader";
/*      */   
/*      */   static Dictionary<String, char[]> characterSets;
/*      */   
/*      */   static boolean useNeXTForAnsi;
/*      */ 
/*      */   
/*      */   public RTFReader(StyledDocument paramStyledDocument) {
/*  150 */     this.target = paramStyledDocument;
/*  151 */     this.parserState = new Hashtable<>();
/*  152 */     this.fontTable = new Hashtable<>();
/*      */     
/*  154 */     this.rtfversion = -1;
/*      */     
/*  156 */     this.mockery = new MockAttributeSet();
/*  157 */     this.documentAttributes = new SimpleAttributeSet();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleBinaryBlob(byte[] paramArrayOfbyte) {
/*  167 */     if (this.skippingCharacters > 0) {
/*      */       
/*  169 */       this.skippingCharacters--;
/*      */       return;
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
/*      */   public void handleText(String paramString) {
/*  182 */     if (this.skippingCharacters > 0) {
/*  183 */       if (this.skippingCharacters >= paramString.length()) {
/*  184 */         this.skippingCharacters -= paramString.length();
/*      */         return;
/*      */       } 
/*  187 */       paramString = paramString.substring(this.skippingCharacters);
/*  188 */       this.skippingCharacters = 0;
/*      */     } 
/*      */ 
/*      */     
/*  192 */     if (this.rtfDestination != null) {
/*  193 */       this.rtfDestination.handleText(paramString);
/*      */       
/*      */       return;
/*      */     } 
/*  197 */     warning("Text with no destination. oops.");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   Color defaultColor() {
/*  203 */     return Color.black;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void begingroup() {
/*  213 */     if (this.skippingCharacters > 0)
/*      */     {
/*  215 */       this.skippingCharacters = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  220 */     Object object = this.parserState.get("_savedState");
/*  221 */     if (object != null)
/*  222 */       this.parserState.remove("_savedState"); 
/*  223 */     Dictionary<String, Object> dictionary = (Dictionary)((Hashtable)this.parserState).clone();
/*  224 */     if (object != null)
/*  225 */       dictionary.put("_savedState", object); 
/*  226 */     this.parserState.put("_savedState", dictionary);
/*      */     
/*  228 */     if (this.rtfDestination != null) {
/*  229 */       this.rtfDestination.begingroup();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endgroup() {
/*  240 */     if (this.skippingCharacters > 0)
/*      */     {
/*  242 */       this.skippingCharacters = 0;
/*      */     }
/*      */     
/*  245 */     Dictionary<Object, Object> dictionary1 = (Dictionary)this.parserState.get("_savedState");
/*  246 */     Destination destination = (Destination)dictionary1.get("dst");
/*  247 */     if (destination != this.rtfDestination) {
/*  248 */       this.rtfDestination.close();
/*  249 */       this.rtfDestination = destination;
/*      */     } 
/*  251 */     Dictionary<Object, Object> dictionary2 = this.parserState;
/*  252 */     this.parserState = dictionary1;
/*  253 */     if (this.rtfDestination != null) {
/*  254 */       this.rtfDestination.endgroup(dictionary2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setRTFDestination(Destination paramDestination) {
/*  261 */     Dictionary dictionary = (Dictionary)this.parserState.get("_savedState");
/*  262 */     if (dictionary != null && 
/*  263 */       this.rtfDestination != dictionary.get("dst")) {
/*  264 */       warning("Warning, RTF destination overridden, invalid RTF.");
/*  265 */       this.rtfDestination.close();
/*      */     } 
/*      */     
/*  268 */     this.rtfDestination = paramDestination;
/*  269 */     this.parserState.put("dst", this.rtfDestination);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() throws IOException {
/*  280 */     Enumeration<?> enumeration = this.documentAttributes.getAttributeNames();
/*  281 */     while (enumeration.hasMoreElements()) {
/*  282 */       Object object = enumeration.nextElement();
/*  283 */       this.target.putProperty(object, this.documentAttributes
/*  284 */           .getAttribute(object));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  289 */     warning("RTF filter done.");
/*      */     
/*  291 */     super.close();
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
/*      */   public boolean handleKeyword(String paramString) {
/*  305 */     boolean bool = this.ignoreGroupIfUnknownKeyword;
/*      */     
/*  307 */     if (this.skippingCharacters > 0) {
/*  308 */       this.skippingCharacters--;
/*  309 */       return true;
/*      */     } 
/*      */     
/*  312 */     this.ignoreGroupIfUnknownKeyword = false;
/*      */     String str;
/*  314 */     if ((str = textKeywords.get(paramString)) != null) {
/*  315 */       handleText(str);
/*  316 */       return true;
/*      */     } 
/*      */     
/*  319 */     if (paramString.equals("fonttbl")) {
/*  320 */       setRTFDestination(new FonttblDestination());
/*  321 */       return true;
/*      */     } 
/*      */     
/*  324 */     if (paramString.equals("colortbl")) {
/*  325 */       setRTFDestination(new ColortblDestination());
/*  326 */       return true;
/*      */     } 
/*      */     
/*  329 */     if (paramString.equals("stylesheet")) {
/*  330 */       setRTFDestination(new StylesheetDestination());
/*  331 */       return true;
/*      */     } 
/*      */     
/*  334 */     if (paramString.equals("info")) {
/*  335 */       setRTFDestination(new InfoDestination());
/*  336 */       return false;
/*      */     } 
/*      */     
/*  339 */     if (paramString.equals("mac")) {
/*  340 */       setCharacterSet("mac");
/*  341 */       return true;
/*      */     } 
/*      */     
/*  344 */     if (paramString.equals("ansi")) {
/*  345 */       if (useNeXTForAnsi) {
/*  346 */         setCharacterSet("NeXT");
/*      */       } else {
/*  348 */         setCharacterSet("ansi");
/*  349 */       }  return true;
/*      */     } 
/*      */     
/*  352 */     if (paramString.equals("next")) {
/*  353 */       setCharacterSet("NeXT");
/*  354 */       return true;
/*      */     } 
/*      */     
/*  357 */     if (paramString.equals("pc")) {
/*  358 */       setCharacterSet("cpg437");
/*  359 */       return true;
/*      */     } 
/*      */     
/*  362 */     if (paramString.equals("pca")) {
/*  363 */       setCharacterSet("cpg850");
/*  364 */       return true;
/*      */     } 
/*      */     
/*  367 */     if (paramString.equals("*")) {
/*  368 */       this.ignoreGroupIfUnknownKeyword = true;
/*  369 */       return true;
/*      */     } 
/*      */     
/*  372 */     if (this.rtfDestination != null && 
/*  373 */       this.rtfDestination.handleKeyword(paramString)) {
/*  374 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  380 */     if (paramString.equals("aftncn") || paramString
/*  381 */       .equals("aftnsep") || paramString
/*  382 */       .equals("aftnsepc") || paramString
/*  383 */       .equals("annotation") || paramString
/*  384 */       .equals("atnauthor") || paramString
/*  385 */       .equals("atnicn") || paramString
/*  386 */       .equals("atnid") || paramString
/*  387 */       .equals("atnref") || paramString
/*  388 */       .equals("atntime") || paramString
/*  389 */       .equals("atrfend") || paramString
/*  390 */       .equals("atrfstart") || paramString
/*  391 */       .equals("bkmkend") || paramString
/*  392 */       .equals("bkmkstart") || paramString
/*  393 */       .equals("datafield") || paramString
/*  394 */       .equals("do") || paramString
/*  395 */       .equals("dptxbxtext") || paramString
/*  396 */       .equals("falt") || paramString
/*  397 */       .equals("field") || paramString
/*  398 */       .equals("file") || paramString
/*  399 */       .equals("filetbl") || paramString
/*  400 */       .equals("fname") || paramString
/*  401 */       .equals("fontemb") || paramString
/*  402 */       .equals("fontfile") || paramString
/*  403 */       .equals("footer") || paramString
/*  404 */       .equals("footerf") || paramString
/*  405 */       .equals("footerl") || paramString
/*  406 */       .equals("footerr") || paramString
/*  407 */       .equals("footnote") || paramString
/*  408 */       .equals("ftncn") || paramString
/*  409 */       .equals("ftnsep") || paramString
/*  410 */       .equals("ftnsepc") || paramString
/*  411 */       .equals("header") || paramString
/*  412 */       .equals("headerf") || paramString
/*  413 */       .equals("headerl") || paramString
/*  414 */       .equals("headerr") || paramString
/*  415 */       .equals("keycode") || paramString
/*  416 */       .equals("nextfile") || paramString
/*  417 */       .equals("object") || paramString
/*  418 */       .equals("pict") || paramString
/*  419 */       .equals("pn") || paramString
/*  420 */       .equals("pnseclvl") || paramString
/*  421 */       .equals("pntxtb") || paramString
/*  422 */       .equals("pntxta") || paramString
/*  423 */       .equals("revtbl") || paramString
/*  424 */       .equals("rxe") || paramString
/*  425 */       .equals("tc") || paramString
/*  426 */       .equals("template") || paramString
/*  427 */       .equals("txe") || paramString
/*  428 */       .equals("xe")) {
/*  429 */       bool = true;
/*      */     }
/*      */     
/*  432 */     if (bool) {
/*  433 */       setRTFDestination(new DiscardingDestination());
/*      */     }
/*      */     
/*  436 */     return false;
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
/*      */   public boolean handleKeyword(String paramString, int paramInt) {
/*  450 */     boolean bool = this.ignoreGroupIfUnknownKeyword;
/*      */     
/*  452 */     if (this.skippingCharacters > 0) {
/*  453 */       this.skippingCharacters--;
/*  454 */       return true;
/*      */     } 
/*      */     
/*  457 */     this.ignoreGroupIfUnknownKeyword = false;
/*      */     
/*  459 */     if (paramString.equals("uc")) {
/*      */       
/*  461 */       this.parserState.put("UnicodeSkip", Integer.valueOf(paramInt));
/*  462 */       return true;
/*      */     } 
/*  464 */     if (paramString.equals("u")) {
/*  465 */       if (paramInt < 0)
/*  466 */         paramInt += 65536; 
/*  467 */       handleText((char)paramInt);
/*  468 */       Number number = (Number)this.parserState.get("UnicodeSkip");
/*  469 */       if (number != null) {
/*  470 */         this.skippingCharacters = number.intValue();
/*      */       } else {
/*  472 */         this.skippingCharacters = 1;
/*      */       } 
/*  474 */       return true;
/*      */     } 
/*      */     
/*  477 */     if (paramString.equals("rtf")) {
/*  478 */       this.rtfversion = paramInt;
/*  479 */       setRTFDestination(new DocumentDestination());
/*  480 */       return true;
/*      */     } 
/*      */     
/*  483 */     if (paramString.startsWith("NeXT") || paramString
/*  484 */       .equals("private")) {
/*  485 */       bool = true;
/*      */     }
/*  487 */     if (this.rtfDestination != null && 
/*  488 */       this.rtfDestination.handleKeyword(paramString, paramInt)) {
/*  489 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  494 */     if (bool) {
/*  495 */       setRTFDestination(new DiscardingDestination());
/*      */     }
/*      */     
/*  498 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setTargetAttribute(String paramString, Object paramObject) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCharacterSet(String paramString) {
/*      */     char[] arrayOfChar;
/*      */     try {
/*  517 */       arrayOfChar = (char[])getCharacterSet(paramString);
/*  518 */     } catch (Exception exception) {
/*  519 */       warning("Exception loading RTF character set \"" + paramString + "\": " + exception);
/*  520 */       arrayOfChar = null;
/*      */     } 
/*      */     
/*  523 */     if (arrayOfChar != null) {
/*  524 */       this.translationTable = arrayOfChar;
/*      */     } else {
/*  526 */       warning("Unknown RTF character set \"" + paramString + "\"");
/*  527 */       if (!paramString.equals("ansi")) {
/*      */         try {
/*  529 */           this.translationTable = (char[])getCharacterSet("ansi");
/*  530 */         } catch (IOException iOException) {
/*  531 */           throw new InternalError("RTFReader: Unable to find character set resources (" + iOException + ")", iOException);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  536 */     setTargetAttribute("rtfCharacterSet", paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void defineCharacterSet(String paramString, char[] paramArrayOfchar) {
/*  544 */     if (paramArrayOfchar.length < 256)
/*  545 */       throw new IllegalArgumentException("Translation table must have 256 entries."); 
/*  546 */     characterSets.put(paramString, paramArrayOfchar);
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
/*      */   public static Object getCharacterSet(final String name) throws IOException {
/*  559 */     char[] arrayOfChar = characterSets.get(name);
/*  560 */     if (arrayOfChar == null) {
/*  561 */       InputStream inputStream = AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>()
/*      */           {
/*      */             public InputStream run() {
/*  564 */               return RTFReader.class.getResourceAsStream("charsets/" + name + ".txt");
/*      */             }
/*      */           });
/*  567 */       arrayOfChar = readCharset(inputStream);
/*  568 */       defineCharacterSet(name, arrayOfChar);
/*      */     } 
/*  570 */     return arrayOfChar;
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
/*      */   static char[] readCharset(InputStream paramInputStream) throws IOException {
/*  582 */     char[] arrayOfChar = new char[256];
/*      */     
/*  584 */     StreamTokenizer streamTokenizer = new StreamTokenizer(new BufferedReader(new InputStreamReader(paramInputStream, "ISO-8859-1")));
/*      */ 
/*      */     
/*  587 */     streamTokenizer.eolIsSignificant(false);
/*  588 */     streamTokenizer.commentChar(35);
/*  589 */     streamTokenizer.slashSlashComments(true);
/*  590 */     streamTokenizer.slashStarComments(true);
/*      */     
/*  592 */     byte b = 0;
/*  593 */     while (b < 'Ā') {
/*      */       int i;
/*      */       try {
/*  596 */         i = streamTokenizer.nextToken();
/*  597 */       } catch (Exception exception) {
/*  598 */         throw new IOException("Unable to read from character set file (" + exception + ")");
/*      */       } 
/*  600 */       if (i != -2)
/*      */       {
/*  602 */         throw new IOException("Unexpected token in character set file");
/*      */       }
/*      */       
/*  605 */       arrayOfChar[b] = (char)(int)streamTokenizer.nval;
/*  606 */       b++;
/*      */     } 
/*      */     
/*  609 */     return arrayOfChar;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static char[] readCharset(URL paramURL) throws IOException {
/*  615 */     return readCharset(paramURL.openStream());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static interface Destination
/*      */   {
/*      */     void handleBinaryBlob(byte[] param1ArrayOfbyte);
/*      */ 
/*      */     
/*      */     void handleText(String param1String);
/*      */ 
/*      */     
/*      */     boolean handleKeyword(String param1String);
/*      */ 
/*      */     
/*      */     boolean handleKeyword(String param1String, int param1Int);
/*      */ 
/*      */     
/*      */     void begingroup();
/*      */ 
/*      */     
/*      */     void endgroup(Dictionary param1Dictionary);
/*      */ 
/*      */     
/*      */     void close();
/*      */   }
/*      */ 
/*      */   
/*      */   class DiscardingDestination
/*      */     implements Destination
/*      */   {
/*      */     public void handleBinaryBlob(byte[] param1ArrayOfbyte) {}
/*      */ 
/*      */     
/*      */     public void handleText(String param1String) {}
/*      */ 
/*      */     
/*      */     public boolean handleKeyword(String param1String) {
/*  654 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean handleKeyword(String param1String, int param1Int) {
/*  660 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void begingroup() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void endgroup(Dictionary param1Dictionary) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void close() {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class FonttblDestination
/*      */     implements Destination
/*      */   {
/*      */     int nextFontNumber;
/*      */ 
/*      */     
/*  685 */     Integer fontNumberKey = null;
/*      */     
/*      */     String nextFontFamily;
/*      */     
/*      */     public void handleBinaryBlob(byte[] param1ArrayOfbyte) {}
/*      */     
/*      */     public void handleText(String param1String) {
/*      */       String str;
/*  693 */       int i = param1String.indexOf(';');
/*      */ 
/*      */       
/*  696 */       if (i > -1) {
/*  697 */         str = param1String.substring(0, i);
/*      */       } else {
/*  699 */         str = param1String;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  704 */       if (this.nextFontNumber == -1 && this.fontNumberKey != null) {
/*      */ 
/*      */         
/*  707 */         str = (String)RTFReader.this.fontTable.get(this.fontNumberKey) + str;
/*      */       } else {
/*  709 */         this.fontNumberKey = Integer.valueOf(this.nextFontNumber);
/*      */       } 
/*  711 */       RTFReader.this.fontTable.put(this.fontNumberKey, str);
/*      */       
/*  713 */       this.nextFontNumber = -1;
/*  714 */       this.nextFontFamily = null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean handleKeyword(String param1String) {
/*  719 */       if (param1String.charAt(0) == 'f') {
/*  720 */         this.nextFontFamily = param1String.substring(1);
/*  721 */         return true;
/*      */       } 
/*      */       
/*  724 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean handleKeyword(String param1String, int param1Int) {
/*  729 */       if (param1String.equals("f")) {
/*  730 */         this.nextFontNumber = param1Int;
/*  731 */         return true;
/*      */       } 
/*      */       
/*  734 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public void begingroup() {}
/*      */ 
/*      */     
/*      */     public void endgroup(Dictionary param1Dictionary) {}
/*      */ 
/*      */     
/*      */     public void close() {
/*  745 */       Enumeration<Integer> enumeration = RTFReader.this.fontTable.keys();
/*  746 */       RTFReader.this.warning("Done reading font table.");
/*  747 */       while (enumeration.hasMoreElements()) {
/*  748 */         Integer integer = enumeration.nextElement();
/*  749 */         RTFReader.this.warning("Number " + integer + ": " + (String)RTFReader.this.fontTable.get(integer));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class ColortblDestination
/*      */     implements Destination
/*      */   {
/*  763 */     int red = 0;
/*  764 */     int green = 0;
/*  765 */     int blue = 0;
/*  766 */     Vector<Color> proTemTable = new Vector<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void handleText(String param1String) {
/*  773 */       for (byte b = 0; b < param1String.length(); b++) {
/*  774 */         if (param1String.charAt(b) == ';') {
/*      */           
/*  776 */           Color color = new Color(this.red, this.green, this.blue);
/*  777 */           this.proTemTable.addElement(color);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void close() {
/*  784 */       int i = this.proTemTable.size();
/*  785 */       RTFReader.this.warning("Done reading color table, " + i + " entries.");
/*  786 */       RTFReader.this.colorTable = new Color[i];
/*  787 */       this.proTemTable.copyInto((Object[])RTFReader.this.colorTable);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean handleKeyword(String param1String, int param1Int) {
/*  792 */       if (param1String.equals("red")) {
/*  793 */         this.red = param1Int;
/*  794 */       } else if (param1String.equals("green")) {
/*  795 */         this.green = param1Int;
/*  796 */       } else if (param1String.equals("blue")) {
/*  797 */         this.blue = param1Int;
/*      */       } else {
/*  799 */         return false;
/*      */       } 
/*  801 */       return true;
/*      */     }
/*      */     
/*      */     public boolean handleKeyword(String param1String) {
/*  805 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public void begingroup() {}
/*      */ 
/*      */     
/*      */     public void endgroup(Dictionary param1Dictionary) {}
/*      */ 
/*      */     
/*      */     public void handleBinaryBlob(byte[] param1ArrayOfbyte) {}
/*      */   }
/*      */   
/*      */   class StylesheetDestination
/*      */     extends DiscardingDestination
/*      */     implements Destination
/*      */   {
/*      */     Dictionary<Integer, StyleDefiningDestination> definedStyles;
/*      */     
/*      */     public StylesheetDestination() {
/*  825 */       this.definedStyles = new Hashtable<>();
/*      */     }
/*      */ 
/*      */     
/*      */     public void begingroup() {
/*  830 */       RTFReader.this.setRTFDestination(new StyleDefiningDestination());
/*      */     }
/*      */ 
/*      */     
/*      */     public void close() {
/*  835 */       Vector vector1 = new Vector();
/*  836 */       Vector<Style> vector = new Vector();
/*  837 */       Vector vector2 = new Vector();
/*  838 */       Enumeration<StyleDefiningDestination> enumeration = this.definedStyles.elements();
/*  839 */       while (enumeration.hasMoreElements()) {
/*      */         Vector<Style> vector3;
/*      */         
/*  842 */         StyleDefiningDestination styleDefiningDestination = enumeration.nextElement();
/*  843 */         Style style = styleDefiningDestination.realize();
/*  844 */         RTFReader.this.warning("Style " + styleDefiningDestination.number + " (" + styleDefiningDestination.styleName + "): " + style);
/*  845 */         String str = (String)style.getAttribute("style:type");
/*      */         
/*  847 */         if (str.equals("section")) {
/*  848 */           vector3 = vector2;
/*  849 */         } else if (str.equals("character")) {
/*  850 */           vector3 = vector1;
/*      */         } else {
/*  852 */           vector3 = vector;
/*      */         } 
/*  854 */         if (vector3.size() <= styleDefiningDestination.number)
/*  855 */           vector3.setSize(styleDefiningDestination.number + 1); 
/*  856 */         vector3.setElementAt(style, styleDefiningDestination.number);
/*      */       } 
/*  858 */       if (!vector1.isEmpty()) {
/*  859 */         Style[] arrayOfStyle = new Style[vector1.size()];
/*  860 */         vector1.copyInto((Object[])arrayOfStyle);
/*  861 */         RTFReader.this.characterStyles = arrayOfStyle;
/*      */       } 
/*  863 */       if (!vector.isEmpty()) {
/*  864 */         Style[] arrayOfStyle = new Style[vector.size()];
/*  865 */         vector.copyInto((Object[])arrayOfStyle);
/*  866 */         RTFReader.this.paragraphStyles = arrayOfStyle;
/*      */       } 
/*  868 */       if (!vector2.isEmpty()) {
/*  869 */         Style[] arrayOfStyle = new Style[vector2.size()];
/*  870 */         vector2.copyInto((Object[])arrayOfStyle);
/*  871 */         RTFReader.this.sectionStyles = arrayOfStyle;
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     class StyleDefiningDestination
/*      */       extends RTFReader.AttributeTrackingDestination
/*      */       implements RTFReader.Destination
/*      */     {
/*  899 */       final int STYLENUMBER_NONE = 222;
/*      */       
/*      */       boolean additive;
/*      */       
/*      */       boolean characterStyle;
/*      */       boolean sectionStyle;
/*      */       public String styleName;
/*      */       public int number;
/*      */       int basedOn;
/*      */       int nextStyle;
/*      */       boolean hidden;
/*      */       Style realizedStyle;
/*      */       
/*      */       public StyleDefiningDestination() {
/*  913 */         this.additive = false;
/*  914 */         this.characterStyle = false;
/*  915 */         this.sectionStyle = false;
/*  916 */         this.styleName = null;
/*  917 */         this.number = 0;
/*  918 */         this.basedOn = 222;
/*  919 */         this.nextStyle = 222;
/*  920 */         this.hidden = false;
/*      */       }
/*      */ 
/*      */       
/*      */       public void handleText(String param2String) {
/*  925 */         if (this.styleName != null) {
/*  926 */           this.styleName += param2String;
/*      */         } else {
/*  928 */           this.styleName = param2String;
/*      */         } 
/*      */       }
/*      */       public void close() {
/*  932 */         boolean bool = (this.styleName == null) ? false : this.styleName.indexOf(';');
/*  933 */         if (bool)
/*  934 */           this.styleName = this.styleName.substring(0, bool); 
/*  935 */         RTFReader.StylesheetDestination.this.definedStyles.put(Integer.valueOf(this.number), this);
/*  936 */         super.close();
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean handleKeyword(String param2String) {
/*  941 */         if (param2String.equals("additive")) {
/*  942 */           this.additive = true;
/*  943 */           return true;
/*      */         } 
/*  945 */         if (param2String.equals("shidden")) {
/*  946 */           this.hidden = true;
/*  947 */           return true;
/*      */         } 
/*  949 */         return super.handleKeyword(param2String);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean handleKeyword(String param2String, int param2Int) {
/*  954 */         if (param2String.equals("s")) {
/*  955 */           this.characterStyle = false;
/*  956 */           this.sectionStyle = false;
/*  957 */           this.number = param2Int;
/*  958 */         } else if (param2String.equals("cs")) {
/*  959 */           this.characterStyle = true;
/*  960 */           this.sectionStyle = false;
/*  961 */           this.number = param2Int;
/*  962 */         } else if (param2String.equals("ds")) {
/*  963 */           this.characterStyle = false;
/*  964 */           this.sectionStyle = true;
/*  965 */           this.number = param2Int;
/*  966 */         } else if (param2String.equals("sbasedon")) {
/*  967 */           this.basedOn = param2Int;
/*  968 */         } else if (param2String.equals("snext")) {
/*  969 */           this.nextStyle = param2Int;
/*      */         } else {
/*  971 */           return super.handleKeyword(param2String, param2Int);
/*      */         } 
/*  973 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public Style realize() {
/*  978 */         Style style1 = null;
/*  979 */         Style style2 = null;
/*      */         
/*  981 */         if (this.realizedStyle != null) {
/*  982 */           return this.realizedStyle;
/*      */         }
/*  984 */         if (this.basedOn != 222) {
/*      */           
/*  986 */           StyleDefiningDestination styleDefiningDestination = RTFReader.StylesheetDestination.this.definedStyles.get(Integer.valueOf(this.basedOn));
/*  987 */           if (styleDefiningDestination != null && styleDefiningDestination != this) {
/*  988 */             style1 = styleDefiningDestination.realize();
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  995 */         this.realizedStyle = RTFReader.this.target.addStyle(this.styleName, style1);
/*      */         
/*  997 */         if (this.characterStyle) {
/*  998 */           this.realizedStyle.addAttributes(currentTextAttributes());
/*  999 */           this.realizedStyle.addAttribute("style:type", "character");
/*      */         }
/* 1001 */         else if (this.sectionStyle) {
/* 1002 */           this.realizedStyle.addAttributes(currentSectionAttributes());
/* 1003 */           this.realizedStyle.addAttribute("style:type", "section");
/*      */         } else {
/*      */           
/* 1006 */           this.realizedStyle.addAttributes(currentParagraphAttributes());
/* 1007 */           this.realizedStyle.addAttribute("style:type", "paragraph");
/*      */         } 
/*      */ 
/*      */         
/* 1011 */         if (this.nextStyle != 222) {
/*      */           
/* 1013 */           StyleDefiningDestination styleDefiningDestination = RTFReader.StylesheetDestination.this.definedStyles.get(Integer.valueOf(this.nextStyle));
/* 1014 */           if (styleDefiningDestination != null) {
/* 1015 */             style2 = styleDefiningDestination.realize();
/*      */           }
/*      */         } 
/*      */         
/* 1019 */         if (style2 != null)
/* 1020 */           this.realizedStyle.addAttribute("style:nextStyle", style2); 
/* 1021 */         this.realizedStyle.addAttribute("style:additive", 
/* 1022 */             Boolean.valueOf(this.additive));
/* 1023 */         this.realizedStyle.addAttribute("style:hidden", 
/* 1024 */             Boolean.valueOf(this.hidden));
/*      */         
/* 1026 */         return this.realizedStyle;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class InfoDestination
/*      */     extends DiscardingDestination
/*      */     implements Destination {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   abstract class AttributeTrackingDestination
/*      */     implements Destination
/*      */   {
/*      */     MutableAttributeSet characterAttributes;
/*      */ 
/*      */ 
/*      */     
/*      */     MutableAttributeSet paragraphAttributes;
/*      */ 
/*      */ 
/*      */     
/*      */     MutableAttributeSet sectionAttributes;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AttributeTrackingDestination() {
/* 1058 */       this.characterAttributes = rootCharacterAttributes();
/* 1059 */       RTFReader.this.parserState.put("chr", this.characterAttributes);
/* 1060 */       this.paragraphAttributes = rootParagraphAttributes();
/* 1061 */       RTFReader.this.parserState.put("pgf", this.paragraphAttributes);
/* 1062 */       this.sectionAttributes = rootSectionAttributes();
/* 1063 */       RTFReader.this.parserState.put("sec", this.sectionAttributes);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract void handleText(String param1String);
/*      */ 
/*      */ 
/*      */     
/*      */     public void handleBinaryBlob(byte[] param1ArrayOfbyte) {
/* 1073 */       RTFReader.this.warning("Unexpected binary data in RTF file.");
/*      */     }
/*      */ 
/*      */     
/*      */     public void begingroup() {
/* 1078 */       MutableAttributeSet mutableAttributeSet1 = currentTextAttributes();
/* 1079 */       MutableAttributeSet mutableAttributeSet2 = currentParagraphAttributes();
/* 1080 */       AttributeSet attributeSet = currentSectionAttributes();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1088 */       this.characterAttributes = new SimpleAttributeSet();
/* 1089 */       this.characterAttributes.addAttributes(mutableAttributeSet1);
/* 1090 */       RTFReader.this.parserState.put("chr", this.characterAttributes);
/*      */       
/* 1092 */       this.paragraphAttributes = new SimpleAttributeSet();
/* 1093 */       this.paragraphAttributes.addAttributes(mutableAttributeSet2);
/* 1094 */       RTFReader.this.parserState.put("pgf", this.paragraphAttributes);
/*      */       
/* 1096 */       this.sectionAttributes = new SimpleAttributeSet();
/* 1097 */       this.sectionAttributes.addAttributes(attributeSet);
/* 1098 */       RTFReader.this.parserState.put("sec", this.sectionAttributes);
/*      */     }
/*      */ 
/*      */     
/*      */     public void endgroup(Dictionary param1Dictionary) {
/* 1103 */       this.characterAttributes = (MutableAttributeSet)RTFReader.this.parserState.get("chr");
/* 1104 */       this.paragraphAttributes = (MutableAttributeSet)RTFReader.this.parserState.get("pgf");
/* 1105 */       this.sectionAttributes = (MutableAttributeSet)RTFReader.this.parserState.get("sec");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void close() {}
/*      */ 
/*      */     
/*      */     public boolean handleKeyword(String param1String) {
/* 1114 */       if (param1String.equals("ulnone")) {
/* 1115 */         return handleKeyword("ul", 0);
/*      */       }
/*      */ 
/*      */       
/* 1119 */       RTFAttribute rTFAttribute = (RTFAttribute)RTFReader.straightforwardAttributes.get(param1String);
/* 1120 */       if (rTFAttribute != null) {
/*      */         boolean bool;
/*      */         
/* 1123 */         switch (rTFAttribute.domain()) {
/*      */           case 0:
/* 1125 */             bool = rTFAttribute.set(this.characterAttributes);
/*      */             break;
/*      */           case 1:
/* 1128 */             bool = rTFAttribute.set(this.paragraphAttributes);
/*      */             break;
/*      */           case 2:
/* 1131 */             bool = rTFAttribute.set(this.sectionAttributes);
/*      */             break;
/*      */           case 4:
/* 1134 */             RTFReader.this.mockery.backing = RTFReader.this.parserState;
/* 1135 */             bool = rTFAttribute.set(RTFReader.this.mockery);
/* 1136 */             RTFReader.this.mockery.backing = null;
/*      */             break;
/*      */           case 3:
/* 1139 */             bool = rTFAttribute.set(RTFReader.this.documentAttributes);
/*      */             break;
/*      */           
/*      */           default:
/* 1143 */             bool = false;
/*      */             break;
/*      */         } 
/* 1146 */         if (bool) {
/* 1147 */           return true;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1152 */       if (param1String.equals("plain")) {
/* 1153 */         resetCharacterAttributes();
/* 1154 */         return true;
/*      */       } 
/*      */       
/* 1157 */       if (param1String.equals("pard")) {
/* 1158 */         resetParagraphAttributes();
/* 1159 */         return true;
/*      */       } 
/*      */       
/* 1162 */       if (param1String.equals("sectd")) {
/* 1163 */         resetSectionAttributes();
/* 1164 */         return true;
/*      */       } 
/*      */       
/* 1167 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean handleKeyword(String param1String, int param1Int) {
/* 1172 */       boolean bool = (param1Int != 0) ? true : false;
/*      */       
/* 1174 */       if (param1String.equals("fc")) {
/* 1175 */         param1String = "cf";
/*      */       }
/* 1177 */       if (param1String.equals("f")) {
/* 1178 */         RTFReader.this.parserState.put(param1String, Integer.valueOf(param1Int));
/* 1179 */         return true;
/*      */       } 
/* 1181 */       if (param1String.equals("cf")) {
/* 1182 */         RTFReader.this.parserState.put(param1String, Integer.valueOf(param1Int));
/* 1183 */         return true;
/*      */       } 
/*      */ 
/*      */       
/* 1187 */       RTFAttribute rTFAttribute = (RTFAttribute)RTFReader.straightforwardAttributes.get(param1String);
/* 1188 */       if (rTFAttribute != null) {
/*      */         boolean bool1;
/*      */         
/* 1191 */         switch (rTFAttribute.domain()) {
/*      */           case 0:
/* 1193 */             bool1 = rTFAttribute.set(this.characterAttributes, param1Int);
/*      */             break;
/*      */           case 1:
/* 1196 */             bool1 = rTFAttribute.set(this.paragraphAttributes, param1Int);
/*      */             break;
/*      */           case 2:
/* 1199 */             bool1 = rTFAttribute.set(this.sectionAttributes, param1Int);
/*      */             break;
/*      */           case 4:
/* 1202 */             RTFReader.this.mockery.backing = RTFReader.this.parserState;
/* 1203 */             bool1 = rTFAttribute.set(RTFReader.this.mockery, param1Int);
/* 1204 */             RTFReader.this.mockery.backing = null;
/*      */             break;
/*      */           case 3:
/* 1207 */             bool1 = rTFAttribute.set(RTFReader.this.documentAttributes, param1Int);
/*      */             break;
/*      */           
/*      */           default:
/* 1211 */             bool1 = false;
/*      */             break;
/*      */         } 
/* 1214 */         if (bool1) {
/* 1215 */           return true;
/*      */         }
/*      */       } 
/*      */       
/* 1219 */       if (param1String.equals("fs")) {
/* 1220 */         StyleConstants.setFontSize(this.characterAttributes, param1Int / 2);
/* 1221 */         return true;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1226 */       if (param1String.equals("sl")) {
/* 1227 */         if (param1Int == 1000) {
/* 1228 */           this.characterAttributes.removeAttribute(StyleConstants.LineSpacing);
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 1234 */           StyleConstants.setLineSpacing(this.characterAttributes, param1Int / 20.0F);
/*      */         } 
/*      */         
/* 1237 */         return true;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1242 */       if (param1String.equals("tx") || param1String.equals("tb")) {
/* 1243 */         Integer integer; float f = param1Int / 20.0F;
/*      */ 
/*      */ 
/*      */         
/* 1247 */         int i = 0;
/* 1248 */         Number number = (Number)RTFReader.this.parserState.get("tab_alignment");
/* 1249 */         if (number != null)
/* 1250 */           i = number.intValue(); 
/* 1251 */         int j = 0;
/* 1252 */         number = (Number)RTFReader.this.parserState.get("tab_leader");
/* 1253 */         if (number != null)
/* 1254 */           j = number.intValue(); 
/* 1255 */         if (param1String.equals("tb")) {
/* 1256 */           i = 5;
/*      */         }
/* 1258 */         RTFReader.this.parserState.remove("tab_alignment");
/* 1259 */         RTFReader.this.parserState.remove("tab_leader");
/*      */         
/* 1261 */         TabStop tabStop = new TabStop(f, i, j);
/*      */ 
/*      */ 
/*      */         
/* 1265 */         Dictionary<Object, Object> dictionary = (Dictionary)RTFReader.this.parserState.get("_tabs");
/* 1266 */         if (dictionary == null) {
/* 1267 */           dictionary = new Hashtable<>();
/* 1268 */           RTFReader.this.parserState.put("_tabs", dictionary);
/* 1269 */           integer = Integer.valueOf(1);
/*      */         } else {
/* 1271 */           integer = (Integer)dictionary.get("stop count");
/* 1272 */           integer = Integer.valueOf(1 + integer.intValue());
/*      */         } 
/* 1274 */         dictionary.put(integer, tabStop);
/* 1275 */         dictionary.put("stop count", integer);
/* 1276 */         RTFReader.this.parserState.remove("_tabs_immutable");
/*      */         
/* 1278 */         return true;
/*      */       } 
/*      */       
/* 1281 */       if (param1String.equals("s") && RTFReader.this.paragraphStyles != null) {
/*      */         
/* 1283 */         RTFReader.this.parserState.put("paragraphStyle", RTFReader.this.paragraphStyles[param1Int]);
/* 1284 */         return true;
/*      */       } 
/*      */       
/* 1287 */       if (param1String.equals("cs") && RTFReader.this.characterStyles != null) {
/*      */         
/* 1289 */         RTFReader.this.parserState.put("characterStyle", RTFReader.this.characterStyles[param1Int]);
/* 1290 */         return true;
/*      */       } 
/*      */       
/* 1293 */       if (param1String.equals("ds") && RTFReader.this.sectionStyles != null) {
/*      */         
/* 1295 */         RTFReader.this.parserState.put("sectionStyle", RTFReader.this.sectionStyles[param1Int]);
/* 1296 */         return true;
/*      */       } 
/*      */       
/* 1299 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected MutableAttributeSet rootCharacterAttributes() {
/* 1306 */       SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
/*      */ 
/*      */ 
/*      */       
/* 1310 */       StyleConstants.setItalic(simpleAttributeSet, false);
/* 1311 */       StyleConstants.setBold(simpleAttributeSet, false);
/* 1312 */       StyleConstants.setUnderline(simpleAttributeSet, false);
/* 1313 */       StyleConstants.setForeground(simpleAttributeSet, RTFReader.this.defaultColor());
/*      */       
/* 1315 */       return simpleAttributeSet;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected MutableAttributeSet rootParagraphAttributes() {
/* 1322 */       SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
/*      */       
/* 1324 */       StyleConstants.setLeftIndent(simpleAttributeSet, 0.0F);
/* 1325 */       StyleConstants.setRightIndent(simpleAttributeSet, 0.0F);
/* 1326 */       StyleConstants.setFirstLineIndent(simpleAttributeSet, 0.0F);
/*      */ 
/*      */       
/* 1329 */       simpleAttributeSet.setResolveParent(RTFReader.this.target.getStyle("default"));
/*      */       
/* 1331 */       return simpleAttributeSet;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected MutableAttributeSet rootSectionAttributes() {
/* 1338 */       return new SimpleAttributeSet();
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
/*      */     MutableAttributeSet currentTextAttributes() {
/*      */       String str;
/* 1351 */       SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet(this.characterAttributes);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1360 */       Integer integer = (Integer)RTFReader.this.parserState.get("f");
/*      */ 
/*      */       
/* 1363 */       if (integer != null) {
/* 1364 */         str = RTFReader.this.fontTable.get(integer);
/*      */       } else {
/* 1366 */         str = null;
/* 1367 */       }  if (str != null) {
/* 1368 */         StyleConstants.setFontFamily(simpleAttributeSet, str);
/*      */       } else {
/* 1370 */         simpleAttributeSet.removeAttribute(StyleConstants.FontFamily);
/*      */       } 
/* 1372 */       if (RTFReader.this.colorTable != null) {
/* 1373 */         Integer integer1 = (Integer)RTFReader.this.parserState.get("cf");
/* 1374 */         if (integer1 != null) {
/* 1375 */           Color color = RTFReader.this.colorTable[integer1.intValue()];
/* 1376 */           StyleConstants.setForeground(simpleAttributeSet, color);
/*      */         } else {
/*      */           
/* 1379 */           simpleAttributeSet.removeAttribute(StyleConstants.Foreground);
/*      */         } 
/*      */       } 
/*      */       
/* 1383 */       if (RTFReader.this.colorTable != null) {
/* 1384 */         Integer integer1 = (Integer)RTFReader.this.parserState.get("cb");
/* 1385 */         if (integer1 != null) {
/* 1386 */           Color color = RTFReader.this.colorTable[integer1.intValue()];
/* 1387 */           simpleAttributeSet.addAttribute(StyleConstants.Background, color);
/*      */         }
/*      */         else {
/*      */           
/* 1391 */           simpleAttributeSet.removeAttribute(StyleConstants.Background);
/*      */         } 
/*      */       } 
/*      */       
/* 1395 */       Style style = (Style)RTFReader.this.parserState.get("characterStyle");
/* 1396 */       if (style != null) {
/* 1397 */         simpleAttributeSet.setResolveParent(style);
/*      */       }
/*      */ 
/*      */       
/* 1401 */       return simpleAttributeSet;
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
/*      */     MutableAttributeSet currentParagraphAttributes() {
/* 1414 */       SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet(this.paragraphAttributes);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1421 */       TabStop[] arrayOfTabStop = (TabStop[])RTFReader.this.parserState.get("_tabs_immutable");
/* 1422 */       if (arrayOfTabStop == null) {
/* 1423 */         Dictionary dictionary = (Dictionary)RTFReader.this.parserState.get("_tabs");
/* 1424 */         if (dictionary != null) {
/* 1425 */           int i = ((Integer)dictionary.get("stop count")).intValue();
/* 1426 */           arrayOfTabStop = new TabStop[i];
/* 1427 */           for (byte b = 1; b <= i; b++)
/* 1428 */             arrayOfTabStop[b - 1] = (TabStop)dictionary.get(Integer.valueOf(b)); 
/* 1429 */           RTFReader.this.parserState.put("_tabs_immutable", arrayOfTabStop);
/*      */         } 
/*      */       } 
/* 1432 */       if (arrayOfTabStop != null) {
/* 1433 */         simpleAttributeSet.addAttribute("tabs", arrayOfTabStop);
/*      */       }
/* 1435 */       Style style = (Style)RTFReader.this.parserState.get("paragraphStyle");
/* 1436 */       if (style != null) {
/* 1437 */         simpleAttributeSet.setResolveParent(style);
/*      */       }
/* 1439 */       return simpleAttributeSet;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AttributeSet currentSectionAttributes() {
/* 1450 */       SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet(this.sectionAttributes);
/*      */       
/* 1452 */       Style style = (Style)RTFReader.this.parserState.get("sectionStyle");
/* 1453 */       if (style != null) {
/* 1454 */         simpleAttributeSet.setResolveParent(style);
/*      */       }
/* 1456 */       return simpleAttributeSet;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void resetCharacterAttributes() {
/* 1464 */       handleKeyword("f", 0);
/* 1465 */       handleKeyword("cf", 0);
/*      */       
/* 1467 */       handleKeyword("fs", 24);
/*      */       
/* 1469 */       Enumeration<RTFAttribute> enumeration = RTFReader.straightforwardAttributes.elements();
/* 1470 */       while (enumeration.hasMoreElements()) {
/* 1471 */         RTFAttribute rTFAttribute = enumeration.nextElement();
/* 1472 */         if (rTFAttribute.domain() == 0) {
/* 1473 */           rTFAttribute.setDefault(this.characterAttributes);
/*      */         }
/*      */       } 
/* 1476 */       handleKeyword("sl", 1000);
/*      */       
/* 1478 */       RTFReader.this.parserState.remove("characterStyle");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void resetParagraphAttributes() {
/* 1486 */       RTFReader.this.parserState.remove("_tabs");
/* 1487 */       RTFReader.this.parserState.remove("_tabs_immutable");
/* 1488 */       RTFReader.this.parserState.remove("paragraphStyle");
/*      */       
/* 1490 */       StyleConstants.setAlignment(this.paragraphAttributes, 0);
/*      */ 
/*      */       
/* 1493 */       Enumeration<RTFAttribute> enumeration = RTFReader.straightforwardAttributes.elements();
/* 1494 */       while (enumeration.hasMoreElements()) {
/* 1495 */         RTFAttribute rTFAttribute = enumeration.nextElement();
/* 1496 */         if (rTFAttribute.domain() == 1) {
/* 1497 */           rTFAttribute.setDefault(this.characterAttributes);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void resetSectionAttributes() {
/* 1506 */       Enumeration<RTFAttribute> enumeration = RTFReader.straightforwardAttributes.elements();
/* 1507 */       while (enumeration.hasMoreElements()) {
/* 1508 */         RTFAttribute rTFAttribute = enumeration.nextElement();
/* 1509 */         if (rTFAttribute.domain() == 2) {
/* 1510 */           rTFAttribute.setDefault(this.characterAttributes);
/*      */         }
/*      */       } 
/* 1513 */       RTFReader.this.parserState.remove("sectionStyle");
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
/*      */   abstract class TextHandlingDestination
/*      */     extends AttributeTrackingDestination
/*      */     implements Destination
/*      */   {
/*      */     boolean inParagraph;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TextHandlingDestination() {
/* 1537 */       this.inParagraph = false;
/*      */     }
/*      */ 
/*      */     
/*      */     public void handleText(String param1String) {
/* 1542 */       if (!this.inParagraph) {
/* 1543 */         beginParagraph();
/*      */       }
/* 1545 */       deliverText(param1String, currentTextAttributes());
/*      */     }
/*      */ 
/*      */     
/*      */     abstract void deliverText(String param1String, AttributeSet param1AttributeSet);
/*      */     
/*      */     public void close() {
/* 1552 */       if (this.inParagraph) {
/* 1553 */         endParagraph();
/*      */       }
/* 1555 */       super.close();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean handleKeyword(String param1String) {
/* 1560 */       if (param1String.equals("\r") || param1String.equals("\n")) {
/* 1561 */         param1String = "par";
/*      */       }
/*      */       
/* 1564 */       if (param1String.equals("par")) {
/*      */         
/* 1566 */         endParagraph();
/* 1567 */         return true;
/*      */       } 
/*      */       
/* 1570 */       if (param1String.equals("sect")) {
/*      */         
/* 1572 */         endSection();
/* 1573 */         return true;
/*      */       } 
/*      */       
/* 1576 */       return super.handleKeyword(param1String);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void beginParagraph() {
/* 1581 */       this.inParagraph = true;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void endParagraph() {
/* 1586 */       MutableAttributeSet mutableAttributeSet1 = currentParagraphAttributes();
/* 1587 */       MutableAttributeSet mutableAttributeSet2 = currentTextAttributes();
/* 1588 */       finishParagraph(mutableAttributeSet1, mutableAttributeSet2);
/* 1589 */       this.inParagraph = false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     abstract void finishParagraph(AttributeSet param1AttributeSet1, AttributeSet param1AttributeSet2);
/*      */ 
/*      */ 
/*      */     
/*      */     abstract void endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class DocumentDestination
/*      */     extends TextHandlingDestination
/*      */     implements Destination
/*      */   {
/*      */     public void deliverText(String param1String, AttributeSet param1AttributeSet) {
/*      */       try {
/* 1609 */         RTFReader.this.target.insertString(RTFReader.this.target.getLength(), param1String, 
/*      */             
/* 1611 */             currentTextAttributes());
/* 1612 */       } catch (BadLocationException badLocationException) {
/*      */ 
/*      */         
/* 1615 */         throw new InternalError(badLocationException.getMessage(), badLocationException);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void finishParagraph(AttributeSet param1AttributeSet1, AttributeSet param1AttributeSet2) {
/* 1622 */       int i = RTFReader.this.target.getLength();
/*      */       try {
/* 1624 */         RTFReader.this.target.insertString(i, "\n", param1AttributeSet2);
/* 1625 */         RTFReader.this.target.setParagraphAttributes(i, 1, param1AttributeSet1, true);
/* 1626 */       } catch (BadLocationException badLocationException) {
/*      */ 
/*      */         
/* 1629 */         throw new InternalError(badLocationException.getMessage(), badLocationException);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void endSection() {}
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/rtf/RTFReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */