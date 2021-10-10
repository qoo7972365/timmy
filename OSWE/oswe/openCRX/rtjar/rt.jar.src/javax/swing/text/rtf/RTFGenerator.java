/*     */ package javax.swing.text.rtf;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Dictionary;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import javax.swing.text.AttributeSet;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.Element;
/*     */ import javax.swing.text.MutableAttributeSet;
/*     */ import javax.swing.text.Segment;
/*     */ import javax.swing.text.SimpleAttributeSet;
/*     */ import javax.swing.text.Style;
/*     */ import javax.swing.text.StyleConstants;
/*     */ import javax.swing.text.TabStop;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class RTFGenerator
/*     */ {
/*     */   Dictionary<Object, Integer> colorTable;
/*     */   int colorCount;
/*     */   Dictionary<String, Integer> fontTable;
/*     */   int fontCount;
/*     */   Dictionary<AttributeSet, Integer> styleTable;
/*     */   int styleCount;
/*     */   OutputStream outputStream;
/*     */   boolean afterKeyword;
/*     */   MutableAttributeSet outputAttributes;
/*     */   int unicodeCount;
/*     */   private Segment workingSegment;
/*     */   int[] outputConversion;
/*  79 */   public static final Color defaultRTFColor = Color.black;
/*     */ 
/*     */   
/*     */   public static final float defaultFontSize = 12.0F;
/*     */ 
/*     */   
/*     */   public static final String defaultFontFamily = "Helvetica";
/*     */ 
/*     */ 
/*     */   
/*     */   static class CharacterKeywordPair
/*     */   {
/*     */     public char character;
/*     */ 
/*     */     
/*     */     public String keyword;
/*     */   }
/*     */   
/*  97 */   private static final Object MagicToken = new Object();
/*     */   static {
/*  99 */     Dictionary<String, String> dictionary = RTFReader.textKeywords;
/* 100 */     Enumeration<String> enumeration = dictionary.keys();
/* 101 */     Vector<CharacterKeywordPair> vector = new Vector();
/* 102 */     while (enumeration.hasMoreElements()) {
/* 103 */       CharacterKeywordPair characterKeywordPair = new CharacterKeywordPair();
/* 104 */       characterKeywordPair.keyword = enumeration.nextElement();
/* 105 */       characterKeywordPair.character = ((String)dictionary.get(characterKeywordPair.keyword)).charAt(0);
/* 106 */       vector.addElement(characterKeywordPair);
/*     */     } 
/* 108 */     textKeywords = new CharacterKeywordPair[vector.size()];
/* 109 */     vector.copyInto((Object[])textKeywords);
/*     */   }
/*     */   protected static CharacterKeywordPair[] textKeywords;
/* 112 */   static final char[] hexdigits = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeDocument(Document paramDocument, OutputStream paramOutputStream) throws IOException {
/* 118 */     RTFGenerator rTFGenerator = new RTFGenerator(paramOutputStream);
/* 119 */     Element element = paramDocument.getDefaultRootElement();
/*     */     
/* 121 */     rTFGenerator.examineElement(element);
/* 122 */     rTFGenerator.writeRTFHeader();
/* 123 */     rTFGenerator.writeDocumentProperties(paramDocument);
/*     */ 
/*     */ 
/*     */     
/* 127 */     int i = element.getElementCount();
/* 128 */     for (byte b = 0; b < i; b++) {
/* 129 */       rTFGenerator.writeParagraphElement(element.getElement(b));
/*     */     }
/* 131 */     rTFGenerator.writeRTFTrailer();
/*     */   }
/*     */ 
/*     */   
/*     */   public RTFGenerator(OutputStream paramOutputStream) {
/* 136 */     this.colorTable = new Hashtable<>();
/* 137 */     this.colorTable.put(defaultRTFColor, Integer.valueOf(0));
/* 138 */     this.colorCount = 1;
/*     */     
/* 140 */     this.fontTable = new Hashtable<>();
/* 141 */     this.fontCount = 0;
/*     */     
/* 143 */     this.styleTable = new Hashtable<>();
/*     */     
/* 145 */     this.styleCount = 0;
/*     */     
/* 147 */     this.workingSegment = new Segment();
/*     */     
/* 149 */     this.outputStream = paramOutputStream;
/*     */     
/* 151 */     this.unicodeCount = 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void examineElement(Element paramElement) {
/* 156 */     AttributeSet attributeSet = paramElement.getAttributes();
/*     */ 
/*     */ 
/*     */     
/* 160 */     tallyStyles(attributeSet);
/*     */     
/* 162 */     if (attributeSet != null) {
/*     */ 
/*     */       
/* 165 */       Color color = StyleConstants.getForeground(attributeSet);
/* 166 */       if (color != null && this.colorTable
/* 167 */         .get(color) == null) {
/* 168 */         this.colorTable.put(color, new Integer(this.colorCount));
/* 169 */         this.colorCount++;
/*     */       } 
/*     */       
/* 172 */       Object object = attributeSet.getAttribute(StyleConstants.Background);
/* 173 */       if (object != null && this.colorTable
/* 174 */         .get(object) == null) {
/* 175 */         this.colorTable.put(object, new Integer(this.colorCount));
/* 176 */         this.colorCount++;
/*     */       } 
/*     */       
/* 179 */       String str = StyleConstants.getFontFamily(attributeSet);
/*     */       
/* 181 */       if (str == null) {
/* 182 */         str = "Helvetica";
/*     */       }
/* 184 */       if (str != null && this.fontTable
/* 185 */         .get(str) == null) {
/* 186 */         this.fontTable.put(str, new Integer(this.fontCount));
/* 187 */         this.fontCount++;
/*     */       } 
/*     */     } 
/*     */     
/* 191 */     int i = paramElement.getElementCount();
/* 192 */     for (byte b = 0; b < i; b++) {
/* 193 */       examineElement(paramElement.getElement(b));
/*     */     }
/*     */   }
/*     */   
/*     */   private void tallyStyles(AttributeSet paramAttributeSet) {
/* 198 */     while (paramAttributeSet != null) {
/* 199 */       if (paramAttributeSet instanceof Style) {
/* 200 */         Integer integer = this.styleTable.get(paramAttributeSet);
/* 201 */         if (integer == null) {
/* 202 */           this.styleCount++;
/* 203 */           integer = new Integer(this.styleCount);
/* 204 */           this.styleTable.put(paramAttributeSet, integer);
/*     */         } 
/*     */       } 
/* 207 */       paramAttributeSet = paramAttributeSet.getResolveParent();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Style findStyle(AttributeSet paramAttributeSet) {
/* 213 */     while (paramAttributeSet != null) {
/* 214 */       if (paramAttributeSet instanceof Style) {
/* 215 */         Object object = this.styleTable.get(paramAttributeSet);
/* 216 */         if (object != null)
/* 217 */           return (Style)paramAttributeSet; 
/*     */       } 
/* 219 */       paramAttributeSet = paramAttributeSet.getResolveParent();
/*     */     } 
/* 221 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private Integer findStyleNumber(AttributeSet paramAttributeSet, String paramString) {
/* 226 */     while (paramAttributeSet != null) {
/* 227 */       if (paramAttributeSet instanceof Style) {
/* 228 */         Integer integer = this.styleTable.get(paramAttributeSet);
/* 229 */         if (integer != null && (
/* 230 */           paramString == null || paramString
/* 231 */           .equals(paramAttributeSet.getAttribute("style:type")))) {
/* 232 */           return integer;
/*     */         }
/*     */       } 
/*     */       
/* 236 */       paramAttributeSet = paramAttributeSet.getResolveParent();
/*     */     } 
/* 238 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object attrDiff(MutableAttributeSet paramMutableAttributeSet, AttributeSet paramAttributeSet, Object paramObject1, Object paramObject2) {
/* 248 */     Object object1 = paramMutableAttributeSet.getAttribute(paramObject1);
/* 249 */     Object object2 = paramAttributeSet.getAttribute(paramObject1);
/*     */     
/* 251 */     if (object2 == object1)
/* 252 */       return null; 
/* 253 */     if (object2 == null) {
/* 254 */       paramMutableAttributeSet.removeAttribute(paramObject1);
/* 255 */       if (paramObject2 != null && !paramObject2.equals(object1)) {
/* 256 */         return paramObject2;
/*     */       }
/* 258 */       return null;
/*     */     } 
/* 260 */     if (object1 == null || 
/* 261 */       !equalArraysOK(object1, object2)) {
/* 262 */       paramMutableAttributeSet.addAttribute(paramObject1, object2);
/* 263 */       return object2;
/*     */     } 
/* 265 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean equalArraysOK(Object paramObject1, Object paramObject2) {
/* 271 */     if (paramObject1 == paramObject2)
/* 272 */       return true; 
/* 273 */     if (paramObject1 == null || paramObject2 == null)
/* 274 */       return false; 
/* 275 */     if (paramObject1.equals(paramObject2))
/* 276 */       return true; 
/* 277 */     if (!paramObject1.getClass().isArray() || !paramObject2.getClass().isArray())
/* 278 */       return false; 
/* 279 */     Object[] arrayOfObject1 = (Object[])paramObject1;
/* 280 */     Object[] arrayOfObject2 = (Object[])paramObject2;
/* 281 */     if (arrayOfObject1.length != arrayOfObject2.length) {
/* 282 */       return false;
/*     */     }
/*     */     
/* 285 */     int i = arrayOfObject1.length;
/* 286 */     for (byte b = 0; b < i; b++) {
/* 287 */       if (!equalArraysOK(arrayOfObject1[b], arrayOfObject2[b])) {
/* 288 */         return false;
/*     */       }
/*     */     } 
/* 291 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeLineBreak() throws IOException {
/* 298 */     writeRawString("\n");
/* 299 */     this.afterKeyword = false;
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
/*     */   public void writeRTFHeader() throws IOException {
/* 314 */     writeBegingroup();
/* 315 */     writeControlWord("rtf", 1);
/* 316 */     writeControlWord("ansi");
/* 317 */     this.outputConversion = outputConversionForName("ansi");
/* 318 */     writeLineBreak();
/*     */ 
/*     */     
/* 321 */     String[] arrayOfString = new String[this.fontCount];
/* 322 */     Enumeration<String> enumeration = this.fontTable.keys();
/*     */     
/* 324 */     while (enumeration.hasMoreElements()) {
/* 325 */       String str = enumeration.nextElement();
/* 326 */       Integer integer = this.fontTable.get(str);
/* 327 */       arrayOfString[integer.intValue()] = str;
/*     */     } 
/* 329 */     writeBegingroup();
/* 330 */     writeControlWord("fonttbl"); byte b;
/* 331 */     for (b = 0; b < this.fontCount; b++) {
/* 332 */       writeControlWord("f", b);
/* 333 */       writeControlWord("fnil");
/* 334 */       writeText(arrayOfString[b]);
/* 335 */       writeText(";");
/*     */     } 
/* 337 */     writeEndgroup();
/* 338 */     writeLineBreak();
/*     */ 
/*     */     
/* 341 */     if (this.colorCount > 1) {
/* 342 */       Color[] arrayOfColor = new Color[this.colorCount];
/* 343 */       Enumeration<Color> enumeration1 = this.colorTable.keys();
/*     */       
/* 345 */       while (enumeration1.hasMoreElements()) {
/* 346 */         Color color = enumeration1.nextElement();
/* 347 */         Integer integer = this.colorTable.get(color);
/* 348 */         arrayOfColor[integer.intValue()] = color;
/*     */       } 
/* 350 */       writeBegingroup();
/* 351 */       writeControlWord("colortbl");
/* 352 */       for (b = 0; b < this.colorCount; b++) {
/* 353 */         Color color = arrayOfColor[b];
/* 354 */         if (color != null) {
/* 355 */           writeControlWord("red", color.getRed());
/* 356 */           writeControlWord("green", color.getGreen());
/* 357 */           writeControlWord("blue", color.getBlue());
/*     */         } 
/* 359 */         writeRawString(";");
/*     */       } 
/* 361 */       writeEndgroup();
/* 362 */       writeLineBreak();
/*     */     } 
/*     */ 
/*     */     
/* 366 */     if (this.styleCount > 1) {
/* 367 */       writeBegingroup();
/* 368 */       writeControlWord("stylesheet");
/* 369 */       Enumeration<AttributeSet> enumeration1 = this.styleTable.keys();
/* 370 */       while (enumeration1.hasMoreElements()) {
/* 371 */         SimpleAttributeSet simpleAttributeSet; Style style1 = (Style)enumeration1.nextElement();
/* 372 */         int i = ((Integer)this.styleTable.get(style1)).intValue();
/* 373 */         writeBegingroup();
/* 374 */         String str = (String)style1.getAttribute("style:type");
/* 375 */         if (str == null)
/* 376 */           str = "paragraph"; 
/* 377 */         if (str.equals("character")) {
/* 378 */           writeControlWord("*");
/* 379 */           writeControlWord("cs", i);
/* 380 */         } else if (str.equals("section")) {
/* 381 */           writeControlWord("*");
/* 382 */           writeControlWord("ds", i);
/*     */         } else {
/* 384 */           writeControlWord("s", i);
/*     */         } 
/*     */         
/* 387 */         AttributeSet attributeSet = style1.getResolveParent();
/*     */         
/* 389 */         if (attributeSet == null) {
/* 390 */           simpleAttributeSet = new SimpleAttributeSet();
/*     */         } else {
/* 392 */           simpleAttributeSet = new SimpleAttributeSet(attributeSet);
/*     */         } 
/*     */         
/* 395 */         updateSectionAttributes(simpleAttributeSet, style1, false);
/* 396 */         updateParagraphAttributes(simpleAttributeSet, style1, false);
/* 397 */         updateCharacterAttributes(simpleAttributeSet, style1, false);
/*     */         
/* 399 */         attributeSet = style1.getResolveParent();
/* 400 */         if (attributeSet != null && attributeSet instanceof Style) {
/* 401 */           Integer integer = this.styleTable.get(attributeSet);
/* 402 */           if (integer != null) {
/* 403 */             writeControlWord("sbasedon", integer.intValue());
/*     */           }
/*     */         } 
/*     */         
/* 407 */         Style style2 = (Style)style1.getAttribute("style:nextStyle");
/* 408 */         if (style2 != null) {
/* 409 */           Integer integer = this.styleTable.get(style2);
/* 410 */           if (integer != null) {
/* 411 */             writeControlWord("snext", integer.intValue());
/*     */           }
/*     */         } 
/*     */         
/* 415 */         Boolean bool1 = (Boolean)style1.getAttribute("style:hidden");
/* 416 */         if (bool1 != null && bool1.booleanValue()) {
/* 417 */           writeControlWord("shidden");
/*     */         }
/* 419 */         Boolean bool2 = (Boolean)style1.getAttribute("style:additive");
/* 420 */         if (bool2 != null && bool2.booleanValue()) {
/* 421 */           writeControlWord("additive");
/*     */         }
/*     */         
/* 424 */         writeText(style1.getName());
/* 425 */         writeText(";");
/* 426 */         writeEndgroup();
/*     */       } 
/* 428 */       writeEndgroup();
/* 429 */       writeLineBreak();
/*     */     } 
/*     */     
/* 432 */     this.outputAttributes = new SimpleAttributeSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void writeDocumentProperties(Document paramDocument) throws IOException {
/* 440 */     boolean bool = false;
/*     */     
/* 442 */     for (byte b = 0; b < RTFAttributes.attributes.length; b++) {
/* 443 */       RTFAttribute rTFAttribute = RTFAttributes.attributes[b];
/* 444 */       if (rTFAttribute.domain() == 3) {
/*     */         
/* 446 */         Object object = paramDocument.getProperty(rTFAttribute.swingName());
/* 447 */         boolean bool1 = rTFAttribute.writeValue(object, this, false);
/* 448 */         if (bool1)
/* 449 */           bool = true; 
/*     */       } 
/*     */     } 
/* 452 */     if (bool) {
/* 453 */       writeLineBreak();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeRTFTrailer() throws IOException {
/* 459 */     writeEndgroup();
/* 460 */     writeLineBreak();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkNumericControlWord(MutableAttributeSet paramMutableAttributeSet, AttributeSet paramAttributeSet, Object paramObject, String paramString, float paramFloat1, float paramFloat2) throws IOException {
/*     */     Object object;
/* 472 */     if ((object = attrDiff(paramMutableAttributeSet, paramAttributeSet, paramObject, MagicToken)) != null) {
/*     */       float f;
/*     */       
/* 475 */       if (object == MagicToken) {
/* 476 */         f = paramFloat1;
/*     */       } else {
/* 478 */         f = ((Number)object).floatValue();
/* 479 */       }  writeControlWord(paramString, Math.round(f * paramFloat2));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkControlWord(MutableAttributeSet paramMutableAttributeSet, AttributeSet paramAttributeSet, RTFAttribute paramRTFAttribute) throws IOException {
/*     */     Object object;
/* 490 */     if ((object = attrDiff(paramMutableAttributeSet, paramAttributeSet, paramRTFAttribute
/* 491 */         .swingName(), MagicToken)) != null) {
/* 492 */       if (object == MagicToken)
/* 493 */         object = null; 
/* 494 */       paramRTFAttribute.writeValue(object, this, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkControlWords(MutableAttributeSet paramMutableAttributeSet, AttributeSet paramAttributeSet, RTFAttribute[] paramArrayOfRTFAttribute, int paramInt) throws IOException {
/* 505 */     int i = paramArrayOfRTFAttribute.length;
/* 506 */     for (byte b = 0; b < i; b++) {
/* 507 */       RTFAttribute rTFAttribute = paramArrayOfRTFAttribute[b];
/* 508 */       if (rTFAttribute.domain() == paramInt) {
/* 509 */         checkControlWord(paramMutableAttributeSet, paramAttributeSet, rTFAttribute);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void updateSectionAttributes(MutableAttributeSet paramMutableAttributeSet, AttributeSet paramAttributeSet, boolean paramBoolean) throws IOException {
/* 518 */     if (paramBoolean) {
/* 519 */       Object object = paramMutableAttributeSet.getAttribute("sectionStyle");
/* 520 */       Integer integer = findStyleNumber(paramAttributeSet, "section");
/* 521 */       if (object != integer) {
/* 522 */         if (object != null) {
/* 523 */           resetSectionAttributes(paramMutableAttributeSet);
/*     */         }
/* 525 */         if (integer != null) {
/* 526 */           writeControlWord("ds", integer.intValue());
/* 527 */           paramMutableAttributeSet.addAttribute("sectionStyle", integer);
/*     */         } else {
/* 529 */           paramMutableAttributeSet.removeAttribute("sectionStyle");
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 534 */     checkControlWords(paramMutableAttributeSet, paramAttributeSet, RTFAttributes.attributes, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void resetSectionAttributes(MutableAttributeSet paramMutableAttributeSet) throws IOException {
/* 541 */     writeControlWord("sectd");
/*     */ 
/*     */     
/* 544 */     int i = RTFAttributes.attributes.length;
/* 545 */     for (byte b = 0; b < i; b++) {
/* 546 */       RTFAttribute rTFAttribute = RTFAttributes.attributes[b];
/* 547 */       if (rTFAttribute.domain() == 2) {
/* 548 */         rTFAttribute.setDefault(paramMutableAttributeSet);
/*     */       }
/*     */     } 
/* 551 */     paramMutableAttributeSet.removeAttribute("sectionStyle");
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
/*     */   void updateParagraphAttributes(MutableAttributeSet paramMutableAttributeSet, AttributeSet paramAttributeSet, boolean paramBoolean) throws IOException {
/*     */     Object object;
/*     */     Integer integer;
/* 567 */     if (paramBoolean) {
/* 568 */       object = paramMutableAttributeSet.getAttribute("paragraphStyle");
/* 569 */       integer = findStyleNumber(paramAttributeSet, "paragraph");
/* 570 */       if (object != integer && 
/* 571 */         object != null) {
/* 572 */         resetParagraphAttributes(paramMutableAttributeSet);
/* 573 */         object = null;
/*     */       } 
/*     */     } else {
/*     */       
/* 577 */       object = null;
/* 578 */       integer = null;
/*     */     } 
/*     */     
/* 581 */     Object object1 = paramMutableAttributeSet.getAttribute("tabs");
/* 582 */     Object object2 = paramAttributeSet.getAttribute("tabs");
/* 583 */     if (object1 != object2 && 
/* 584 */       object1 != null) {
/* 585 */       resetParagraphAttributes(paramMutableAttributeSet);
/* 586 */       object1 = null;
/* 587 */       object = null;
/*     */     } 
/*     */ 
/*     */     
/* 591 */     if (object != integer && integer != null) {
/* 592 */       writeControlWord("s", ((Integer)integer).intValue());
/* 593 */       paramMutableAttributeSet.addAttribute("paragraphStyle", integer);
/*     */     } 
/*     */     
/* 596 */     checkControlWords(paramMutableAttributeSet, paramAttributeSet, RTFAttributes.attributes, 1);
/*     */ 
/*     */     
/* 599 */     if (object1 != object2 && object2 != null) {
/* 600 */       TabStop[] arrayOfTabStop = (TabStop[])object2;
/*     */       
/* 602 */       for (byte b = 0; b < arrayOfTabStop.length; b++) {
/* 603 */         TabStop tabStop = arrayOfTabStop[b];
/* 604 */         switch (tabStop.getAlignment()) {
/*     */ 
/*     */ 
/*     */           
/*     */           case 1:
/* 609 */             writeControlWord("tqr");
/*     */             break;
/*     */           case 2:
/* 612 */             writeControlWord("tqc");
/*     */             break;
/*     */           case 4:
/* 615 */             writeControlWord("tqdec");
/*     */             break;
/*     */         } 
/* 618 */         switch (tabStop.getLeader()) {
/*     */ 
/*     */           
/*     */           case 1:
/* 622 */             writeControlWord("tldot");
/*     */             break;
/*     */           case 2:
/* 625 */             writeControlWord("tlhyph");
/*     */             break;
/*     */           case 3:
/* 628 */             writeControlWord("tlul");
/*     */             break;
/*     */           case 4:
/* 631 */             writeControlWord("tlth");
/*     */             break;
/*     */           case 5:
/* 634 */             writeControlWord("tleq");
/*     */             break;
/*     */         } 
/* 637 */         int i = Math.round(20.0F * tabStop.getPosition());
/* 638 */         if (tabStop.getAlignment() == 5) {
/* 639 */           writeControlWord("tb", i);
/*     */         } else {
/* 641 */           writeControlWord("tx", i);
/*     */         } 
/*     */       } 
/* 644 */       paramMutableAttributeSet.addAttribute("tabs", arrayOfTabStop);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeParagraphElement(Element paramElement) throws IOException {
/* 651 */     updateParagraphAttributes(this.outputAttributes, paramElement.getAttributes(), true);
/*     */     
/* 653 */     int i = paramElement.getElementCount();
/* 654 */     for (byte b = 0; b < i; b++) {
/* 655 */       writeTextElement(paramElement.getElement(b));
/*     */     }
/*     */     
/* 658 */     writeControlWord("par");
/* 659 */     writeLineBreak();
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
/*     */   protected void resetParagraphAttributes(MutableAttributeSet paramMutableAttributeSet) throws IOException {
/* 686 */     writeControlWord("pard");
/*     */     
/* 688 */     paramMutableAttributeSet.addAttribute(StyleConstants.Alignment, Integer.valueOf(0));
/*     */ 
/*     */     
/* 691 */     int i = RTFAttributes.attributes.length;
/* 692 */     for (byte b = 0; b < i; b++) {
/* 693 */       RTFAttribute rTFAttribute = RTFAttributes.attributes[b];
/* 694 */       if (rTFAttribute.domain() == 1) {
/* 695 */         rTFAttribute.setDefault(paramMutableAttributeSet);
/*     */       }
/*     */     } 
/* 698 */     paramMutableAttributeSet.removeAttribute("paragraphStyle");
/* 699 */     paramMutableAttributeSet.removeAttribute("tabs");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void updateCharacterAttributes(MutableAttributeSet paramMutableAttributeSet, AttributeSet paramAttributeSet, boolean paramBoolean) throws IOException {
/* 709 */     if (paramBoolean) {
/* 710 */       Object object1 = paramMutableAttributeSet.getAttribute("characterStyle");
/* 711 */       Integer integer = findStyleNumber(paramAttributeSet, "character");
/*     */       
/* 713 */       if (object1 != integer) {
/* 714 */         if (object1 != null) {
/* 715 */           resetCharacterAttributes(paramMutableAttributeSet);
/*     */         }
/* 717 */         if (integer != null) {
/* 718 */           writeControlWord("cs", integer.intValue());
/* 719 */           paramMutableAttributeSet.addAttribute("characterStyle", integer);
/*     */         } else {
/* 721 */           paramMutableAttributeSet.removeAttribute("characterStyle");
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     Object object;
/* 726 */     if ((object = attrDiff(paramMutableAttributeSet, paramAttributeSet, StyleConstants.FontFamily, null)) != null) {
/*     */       
/* 728 */       Integer integer = this.fontTable.get(object);
/* 729 */       writeControlWord("f", integer.intValue());
/*     */     } 
/*     */     
/* 732 */     checkNumericControlWord(paramMutableAttributeSet, paramAttributeSet, StyleConstants.FontSize, "fs", 12.0F, 2.0F);
/*     */ 
/*     */ 
/*     */     
/* 736 */     checkControlWords(paramMutableAttributeSet, paramAttributeSet, RTFAttributes.attributes, 0);
/*     */ 
/*     */     
/* 739 */     checkNumericControlWord(paramMutableAttributeSet, paramAttributeSet, StyleConstants.LineSpacing, "sl", 0.0F, 20.0F);
/*     */ 
/*     */ 
/*     */     
/* 743 */     if ((object = attrDiff(paramMutableAttributeSet, paramAttributeSet, StyleConstants.Background, MagicToken)) != null) {
/*     */       int i;
/*     */       
/* 746 */       if (object == MagicToken) {
/* 747 */         i = 0;
/*     */       } else {
/* 749 */         i = ((Integer)this.colorTable.get(object)).intValue();
/* 750 */       }  writeControlWord("cb", i);
/*     */     } 
/*     */     
/* 753 */     if ((object = attrDiff(paramMutableAttributeSet, paramAttributeSet, StyleConstants.Foreground, null)) != null) {
/*     */       int i;
/*     */       
/* 756 */       if (object == MagicToken) {
/* 757 */         i = 0;
/*     */       } else {
/* 759 */         i = ((Integer)this.colorTable.get(object)).intValue();
/* 760 */       }  writeControlWord("cf", i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void resetCharacterAttributes(MutableAttributeSet paramMutableAttributeSet) throws IOException {
/* 767 */     writeControlWord("plain");
/*     */ 
/*     */     
/* 770 */     int i = RTFAttributes.attributes.length;
/* 771 */     for (byte b = 0; b < i; b++) {
/* 772 */       RTFAttribute rTFAttribute = RTFAttributes.attributes[b];
/* 773 */       if (rTFAttribute.domain() == 0) {
/* 774 */         rTFAttribute.setDefault(paramMutableAttributeSet);
/*     */       }
/*     */     } 
/* 777 */     StyleConstants.setFontFamily(paramMutableAttributeSet, "Helvetica");
/* 778 */     paramMutableAttributeSet.removeAttribute(StyleConstants.FontSize);
/* 779 */     paramMutableAttributeSet.removeAttribute(StyleConstants.Background);
/* 780 */     paramMutableAttributeSet.removeAttribute(StyleConstants.Foreground);
/* 781 */     paramMutableAttributeSet.removeAttribute(StyleConstants.LineSpacing);
/* 782 */     paramMutableAttributeSet.removeAttribute("characterStyle");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTextElement(Element paramElement) throws IOException {
/* 788 */     updateCharacterAttributes(this.outputAttributes, paramElement.getAttributes(), true);
/*     */     
/* 790 */     if (paramElement.isLeaf()) {
/*     */       try {
/* 792 */         paramElement.getDocument().getText(paramElement.getStartOffset(), paramElement
/* 793 */             .getEndOffset() - paramElement.getStartOffset(), this.workingSegment);
/*     */       }
/* 795 */       catch (BadLocationException badLocationException) {
/*     */         
/* 797 */         badLocationException.printStackTrace();
/* 798 */         throw new InternalError(badLocationException.getMessage());
/*     */       } 
/* 800 */       writeText(this.workingSegment);
/*     */     } else {
/* 802 */       int i = paramElement.getElementCount();
/* 803 */       for (byte b = 0; b < i; b++) {
/* 804 */         writeTextElement(paramElement.getElement(b));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeText(Segment paramSegment) throws IOException {
/* 814 */     int i = paramSegment.offset;
/* 815 */     int j = i + paramSegment.count;
/* 816 */     char[] arrayOfChar = paramSegment.array;
/* 817 */     for (; i < j; i++) {
/* 818 */       writeCharacter(arrayOfChar[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeText(String paramString) throws IOException {
/* 826 */     byte b = 0;
/* 827 */     int i = paramString.length();
/* 828 */     for (; b < i; b++) {
/* 829 */       writeCharacter(paramString.charAt(b));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeRawString(String paramString) throws IOException {
/* 835 */     int i = paramString.length();
/* 836 */     for (byte b = 0; b < i; b++) {
/* 837 */       this.outputStream.write(paramString.charAt(b));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeControlWord(String paramString) throws IOException {
/* 843 */     this.outputStream.write(92);
/* 844 */     writeRawString(paramString);
/* 845 */     this.afterKeyword = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeControlWord(String paramString, int paramInt) throws IOException {
/* 851 */     this.outputStream.write(92);
/* 852 */     writeRawString(paramString);
/* 853 */     writeRawString(String.valueOf(paramInt));
/* 854 */     this.afterKeyword = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeBegingroup() throws IOException {
/* 860 */     this.outputStream.write(123);
/* 861 */     this.afterKeyword = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEndgroup() throws IOException {
/* 867 */     this.outputStream.write(125);
/* 868 */     this.afterKeyword = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeCharacter(char paramChar) throws IOException {
/* 876 */     if (paramChar == ' ') {
/* 877 */       this.outputStream.write(92);
/* 878 */       this.outputStream.write(126);
/* 879 */       this.afterKeyword = false;
/*     */       
/*     */       return;
/*     */     } 
/* 883 */     if (paramChar == '\t') {
/* 884 */       writeControlWord("tab");
/*     */       
/*     */       return;
/*     */     } 
/* 888 */     if (paramChar == '\n' || paramChar == '\r') {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 893 */     int i = convertCharacter(this.outputConversion, paramChar);
/* 894 */     if (i == 0) {
/*     */ 
/*     */       
/* 897 */       for (byte b = 0; b < textKeywords.length; b++) {
/* 898 */         if ((textKeywords[b]).character == paramChar) {
/* 899 */           writeControlWord((textKeywords[b]).keyword);
/*     */ 
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 908 */       String str = approximationForUnicode(paramChar);
/* 909 */       if (str.length() != this.unicodeCount) {
/* 910 */         this.unicodeCount = str.length();
/* 911 */         writeControlWord("uc", this.unicodeCount);
/*     */       } 
/* 913 */       writeControlWord("u", paramChar);
/* 914 */       writeRawString(" ");
/* 915 */       writeRawString(str);
/* 916 */       this.afterKeyword = false;
/*     */       
/*     */       return;
/*     */     } 
/* 920 */     if (i > 127) {
/*     */       
/* 922 */       this.outputStream.write(92);
/* 923 */       this.outputStream.write(39);
/* 924 */       int j = (i & 0xF0) >>> 4;
/* 925 */       this.outputStream.write(hexdigits[j]);
/* 926 */       j = i & 0xF;
/* 927 */       this.outputStream.write(hexdigits[j]);
/* 928 */       this.afterKeyword = false;
/*     */       
/*     */       return;
/*     */     } 
/* 932 */     switch (i) {
/*     */       case 92:
/*     */       case 123:
/*     */       case 125:
/* 936 */         this.outputStream.write(92);
/* 937 */         this.afterKeyword = false;
/*     */         break;
/*     */     } 
/* 940 */     if (this.afterKeyword) {
/* 941 */       this.outputStream.write(32);
/* 942 */       this.afterKeyword = false;
/*     */     } 
/* 944 */     this.outputStream.write(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String approximationForUnicode(char paramChar) {
/* 953 */     return "?";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int[] outputConversionFromTranslationTable(char[] paramArrayOfchar) {
/* 964 */     int[] arrayOfInt = new int[2 * paramArrayOfchar.length];
/*     */ 
/*     */ 
/*     */     
/* 968 */     for (byte b = 0; b < paramArrayOfchar.length; b++) {
/* 969 */       arrayOfInt[b * 2] = paramArrayOfchar[b];
/* 970 */       arrayOfInt[b * 2 + 1] = b;
/*     */     } 
/*     */     
/* 973 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int[] outputConversionForName(String paramString) throws IOException {
/* 979 */     char[] arrayOfChar = (char[])RTFReader.getCharacterSet(paramString);
/* 980 */     return outputConversionFromTranslationTable(arrayOfChar);
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
/*     */   protected static int convertCharacter(int[] paramArrayOfint, char paramChar) {
/* 993 */     for (byte b = 0; b < paramArrayOfint.length; b += 2) {
/* 994 */       if (paramArrayOfint[b] == paramChar) {
/* 995 */         return paramArrayOfint[b + 1];
/*     */       }
/*     */     } 
/* 998 */     return 0;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/rtf/RTFGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */