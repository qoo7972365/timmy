/*     */ package com.sun.org.apache.xml.internal.serialize;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.dom.DOMMessageFormatter;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HTMLdtd
/*     */ {
/*     */   public static final String HTMLPublicId = "-//W3C//DTD HTML 4.01//EN";
/*     */   public static final String HTMLSystemId = "http://www.w3.org/TR/html4/strict.dtd";
/*     */   public static final String XHTMLPublicId = "-//W3C//DTD XHTML 1.0 Strict//EN";
/*     */   public static final String XHTMLSystemId = "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd";
/*     */   private static Map<Integer, String> _byChar;
/*     */   private static Map<String, Integer> _byName;
/*     */   private static final Map<String, String[]> _boolAttrs;
/*     */   
/*     */   public static boolean isEmptyTag(String tagName) {
/* 180 */     return isElement(tagName, 17);
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
/*     */   public static boolean isElementContent(String tagName) {
/* 194 */     return isElement(tagName, 2);
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
/*     */   public static boolean isPreserveSpace(String tagName) {
/* 208 */     return isElement(tagName, 4);
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
/*     */   public static boolean isOptionalClosing(String tagName) {
/* 222 */     return isElement(tagName, 8);
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
/*     */   public static boolean isOnlyOpening(String tagName) {
/* 235 */     return isElement(tagName, 1);
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
/*     */   public static boolean isClosing(String tagName, String openTag) {
/* 252 */     if (openTag.equalsIgnoreCase("HEAD")) {
/* 253 */       return !isElement(tagName, 32);
/*     */     }
/* 255 */     if (openTag.equalsIgnoreCase("P")) {
/* 256 */       return isElement(tagName, 64);
/*     */     }
/* 258 */     if (openTag.equalsIgnoreCase("DT") || openTag.equalsIgnoreCase("DD")) {
/* 259 */       return isElement(tagName, 128);
/*     */     }
/* 261 */     if (openTag.equalsIgnoreCase("LI") || openTag.equalsIgnoreCase("OPTION")) {
/* 262 */       return isElement(tagName, 256);
/*     */     }
/* 264 */     if (openTag.equalsIgnoreCase("THEAD") || openTag.equalsIgnoreCase("TFOOT") || openTag
/* 265 */       .equalsIgnoreCase("TBODY") || openTag.equalsIgnoreCase("TR") || openTag
/* 266 */       .equalsIgnoreCase("COLGROUP")) {
/* 267 */       return isElement(tagName, 512);
/*     */     }
/* 269 */     if (openTag.equalsIgnoreCase("TH") || openTag.equalsIgnoreCase("TD"))
/* 270 */       return isElement(tagName, 16384); 
/* 271 */     return false;
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
/*     */   public static boolean isURI(String tagName, String attrName) {
/* 286 */     return (attrName.equalsIgnoreCase("href") || attrName.equalsIgnoreCase("src"));
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
/*     */   public static boolean isBoolean(String tagName, String attrName) {
/* 302 */     String[] attrNames = _boolAttrs.get(tagName.toUpperCase(Locale.ENGLISH));
/* 303 */     if (attrNames == null)
/* 304 */       return false; 
/* 305 */     for (int i = 0; i < attrNames.length; i++) {
/* 306 */       if (attrNames[i].equalsIgnoreCase(attrName))
/* 307 */         return true; 
/* 308 */     }  return false;
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
/*     */   public static int charFromName(String name) {
/* 324 */     initialize();
/* 325 */     Object value = _byName.get(name);
/* 326 */     if (value != null && value instanceof Integer) {
/* 327 */       return ((Integer)value).intValue();
/*     */     }
/* 329 */     return -1;
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
/*     */   public static String fromChar(int value) {
/* 343 */     if (value > 65535) {
/* 344 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 348 */     initialize();
/* 349 */     String name = _byChar.get(Integer.valueOf(value));
/* 350 */     return name;
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
/*     */   private static void initialize() {
/* 362 */     InputStream is = null;
/* 363 */     BufferedReader reader = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 371 */     if (_byName != null)
/*     */       return; 
/*     */     try {
/* 374 */       _byName = new HashMap<>();
/* 375 */       _byChar = new HashMap<>();
/* 376 */       is = HTMLdtd.class.getResourceAsStream("HTMLEntities.res");
/* 377 */       if (is == null) {
/* 378 */         throw new RuntimeException(
/* 379 */             DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "ResourceNotFound", new Object[] { "HTMLEntities.res" }));
/*     */       }
/*     */ 
/*     */       
/* 383 */       reader = new BufferedReader(new InputStreamReader(is, "ASCII"));
/* 384 */       String line = reader.readLine();
/* 385 */       while (line != null) {
/* 386 */         if (line.length() == 0 || line.charAt(0) == '#') {
/* 387 */           line = reader.readLine();
/*     */           continue;
/*     */         } 
/* 390 */         int index = line.indexOf(' ');
/* 391 */         if (index > 1) {
/* 392 */           String name = line.substring(0, index);
/* 393 */           index++;
/* 394 */           if (index < line.length()) {
/* 395 */             String value = line.substring(index);
/* 396 */             index = value.indexOf(' ');
/* 397 */             if (index > 0)
/* 398 */               value = value.substring(0, index); 
/* 399 */             int code = Integer.parseInt(value);
/* 400 */             defineEntity(name, (char)code);
/*     */           } 
/*     */         } 
/* 403 */         line = reader.readLine();
/*     */       } 
/* 405 */       is.close();
/* 406 */     } catch (Exception except) {
/* 407 */       throw new RuntimeException(
/* 408 */           DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "ResourceNotLoaded", new Object[] {
/*     */               
/* 410 */               "HTMLEntities.res", except.toString() }));
/*     */     } finally {
/* 412 */       if (is != null) {
/*     */         try {
/* 414 */           is.close();
/* 415 */         } catch (Exception exception) {}
/*     */       }
/*     */     } 
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
/*     */   private static void defineEntity(String name, char value) {
/* 435 */     if (_byName.get(name) == null) {
/* 436 */       _byName.put(name, new Integer(value));
/* 437 */       _byChar.put(new Integer(value), name);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void defineElement(String name, int flags) {
/* 444 */     _elemDefs.put(name, Integer.valueOf(flags));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void defineBoolean(String tagName, String attrName) {
/* 450 */     defineBoolean(tagName, new String[] { attrName });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void defineBoolean(String tagName, String[] attrNames) {
/* 456 */     _boolAttrs.put(tagName, attrNames);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isElement(String name, int flag) {
/* 464 */     Integer flags = _elemDefs.get(name.toUpperCase(Locale.ENGLISH));
/* 465 */     if (flags == null) {
/* 466 */       return false;
/*     */     }
/* 468 */     return ((flags.intValue() & flag) == flag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 474 */   private static final Map<String, Integer> _elemDefs = new HashMap<>(); static {
/* 475 */     defineElement("ADDRESS", 64);
/* 476 */     defineElement("AREA", 17);
/* 477 */     defineElement("BASE", 49);
/* 478 */     defineElement("BASEFONT", 17);
/* 479 */     defineElement("BLOCKQUOTE", 64);
/* 480 */     defineElement("BODY", 8);
/* 481 */     defineElement("BR", 17);
/* 482 */     defineElement("COL", 17);
/* 483 */     defineElement("COLGROUP", 522);
/* 484 */     defineElement("DD", 137);
/* 485 */     defineElement("DIV", 64);
/* 486 */     defineElement("DL", 66);
/* 487 */     defineElement("DT", 137);
/* 488 */     defineElement("FIELDSET", 64);
/* 489 */     defineElement("FORM", 64);
/* 490 */     defineElement("FRAME", 25);
/* 491 */     defineElement("H1", 64);
/* 492 */     defineElement("H2", 64);
/* 493 */     defineElement("H3", 64);
/* 494 */     defineElement("H4", 64);
/* 495 */     defineElement("H5", 64);
/* 496 */     defineElement("H6", 64);
/* 497 */     defineElement("HEAD", 10);
/* 498 */     defineElement("HR", 81);
/* 499 */     defineElement("HTML", 10);
/* 500 */     defineElement("IMG", 17);
/* 501 */     defineElement("INPUT", 17);
/* 502 */     defineElement("ISINDEX", 49);
/* 503 */     defineElement("LI", 265);
/* 504 */     defineElement("LINK", 49);
/* 505 */     defineElement("MAP", 32);
/* 506 */     defineElement("META", 49);
/* 507 */     defineElement("OL", 66);
/* 508 */     defineElement("OPTGROUP", 2);
/* 509 */     defineElement("OPTION", 265);
/* 510 */     defineElement("P", 328);
/* 511 */     defineElement("PARAM", 17);
/* 512 */     defineElement("PRE", 68);
/* 513 */     defineElement("SCRIPT", 36);
/* 514 */     defineElement("NOSCRIPT", 36);
/* 515 */     defineElement("SELECT", 2);
/* 516 */     defineElement("STYLE", 36);
/* 517 */     defineElement("TABLE", 66);
/* 518 */     defineElement("TBODY", 522);
/* 519 */     defineElement("TD", 16392);
/* 520 */     defineElement("TEXTAREA", 4);
/* 521 */     defineElement("TFOOT", 522);
/* 522 */     defineElement("TH", 16392);
/* 523 */     defineElement("THEAD", 522);
/* 524 */     defineElement("TITLE", 32);
/* 525 */     defineElement("TR", 522);
/* 526 */     defineElement("UL", 66);
/*     */     
/* 528 */     _boolAttrs = (Map)new HashMap<>();
/* 529 */     defineBoolean("AREA", "href");
/* 530 */     defineBoolean("BUTTON", "disabled");
/* 531 */     defineBoolean("DIR", "compact");
/* 532 */     defineBoolean("DL", "compact");
/* 533 */     defineBoolean("FRAME", "noresize");
/* 534 */     defineBoolean("HR", "noshade");
/* 535 */     defineBoolean("IMAGE", "ismap");
/* 536 */     defineBoolean("INPUT", new String[] { "defaultchecked", "checked", "readonly", "disabled" });
/* 537 */     defineBoolean("LINK", "link");
/* 538 */     defineBoolean("MENU", "compact");
/* 539 */     defineBoolean("OBJECT", "declare");
/* 540 */     defineBoolean("OL", "compact");
/* 541 */     defineBoolean("OPTGROUP", "disabled");
/* 542 */     defineBoolean("OPTION", new String[] { "default-selected", "selected", "disabled" });
/* 543 */     defineBoolean("SCRIPT", "defer");
/* 544 */     defineBoolean("SELECT", new String[] { "multiple", "disabled" });
/* 545 */     defineBoolean("STYLE", "disabled");
/* 546 */     defineBoolean("TD", "nowrap");
/* 547 */     defineBoolean("TH", "nowrap");
/* 548 */     defineBoolean("TEXTAREA", new String[] { "disabled", "readonly" });
/* 549 */     defineBoolean("UL", "compact");
/*     */     
/* 551 */     initialize();
/*     */   }
/*     */   
/*     */   private static final String ENTITIES_RESOURCE = "HTMLEntities.res";
/*     */   private static final int ONLY_OPENING = 1;
/*     */   private static final int ELEM_CONTENT = 2;
/*     */   private static final int PRESERVE = 4;
/*     */   private static final int OPT_CLOSING = 8;
/*     */   private static final int EMPTY = 17;
/*     */   private static final int ALLOWED_HEAD = 32;
/*     */   private static final int CLOSE_P = 64;
/*     */   private static final int CLOSE_DD_DT = 128;
/*     */   private static final int CLOSE_SELF = 256;
/*     */   private static final int CLOSE_TABLE = 512;
/*     */   private static final int CLOSE_TH_TD = 16384;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/serialize/HTMLdtd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */