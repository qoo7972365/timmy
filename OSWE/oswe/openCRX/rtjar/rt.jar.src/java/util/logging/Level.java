/*     */ package java.util.logging;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.ResourceBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Level
/*     */   implements Serializable
/*     */ {
/*     */   private static final String defaultBundle = "sun.util.logging.resources.logging";
/*     */   private final String name;
/*     */   private final int value;
/*     */   private final String resourceBundleName;
/*     */   private transient String localizedLevelName;
/*     */   private transient Locale cachedLocale;
/*  92 */   public static final Level OFF = new Level("OFF", 2147483647, "sun.util.logging.resources.logging");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public static final Level SEVERE = new Level("SEVERE", 1000, "sun.util.logging.resources.logging");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   public static final Level WARNING = new Level("WARNING", 900, "sun.util.logging.resources.logging");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   public static final Level INFO = new Level("INFO", 800, "sun.util.logging.resources.logging");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   public static final Level CONFIG = new Level("CONFIG", 700, "sun.util.logging.resources.logging");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   public static final Level FINE = new Level("FINE", 500, "sun.util.logging.resources.logging");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public static final Level FINER = new Level("FINER", 400, "sun.util.logging.resources.logging");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 171 */   public static final Level FINEST = new Level("FINEST", 300, "sun.util.logging.resources.logging");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 177 */   public static final Level ALL = new Level("ALL", -2147483648, "sun.util.logging.resources.logging");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -8176160795706313070L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Level(String paramString, int paramInt) {
/* 192 */     this(paramString, paramInt, null);
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
/*     */   protected Level(String paramString1, int paramInt, String paramString2) {
/* 207 */     this(paramString1, paramInt, paramString2, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Level(String paramString1, int paramInt, String paramString2, boolean paramBoolean) {
/* 213 */     if (paramString1 == null) {
/* 214 */       throw new NullPointerException();
/*     */     }
/* 216 */     this.name = paramString1;
/* 217 */     this.value = paramInt;
/* 218 */     this.resourceBundleName = paramString2;
/* 219 */     this.localizedLevelName = (paramString2 == null) ? paramString1 : null;
/* 220 */     this.cachedLocale = null;
/* 221 */     if (paramBoolean) {
/* 222 */       KnownLevel.add(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getResourceBundleName() {
/* 233 */     return this.resourceBundleName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 242 */     return this.name;
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
/*     */   public String getLocalizedName() {
/* 255 */     return getLocalizedLevelName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final String getLevelName() {
/* 261 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String computeLocalizedLevelName(Locale paramLocale) {
/* 267 */     if (!"sun.util.logging.resources.logging".equals(this.resourceBundleName)) {
/* 268 */       return ResourceBundle.getBundle(this.resourceBundleName, paramLocale, 
/* 269 */           ClassLoader.getSystemClassLoader()).getString(this.name);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 274 */     ResourceBundle resourceBundle = ResourceBundle.getBundle("sun.util.logging.resources.logging", paramLocale);
/* 275 */     String str = resourceBundle.getString(this.name);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 280 */     Locale locale1 = resourceBundle.getLocale();
/*     */ 
/*     */     
/* 283 */     Locale locale2 = (Locale.ROOT.equals(locale1) || this.name.equals(str.toUpperCase(Locale.ROOT))) ? Locale.ROOT : locale1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 290 */     return Locale.ROOT.equals(locale2) ? this.name : str.toUpperCase(locale2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final String getCachedLocalizedLevelName() {
/* 297 */     if (this.localizedLevelName != null && 
/* 298 */       this.cachedLocale != null && 
/* 299 */       this.cachedLocale.equals(Locale.getDefault()))
/*     */     {
/*     */       
/* 302 */       return this.localizedLevelName;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 307 */     if (this.resourceBundleName == null)
/*     */     {
/* 309 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 315 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final synchronized String getLocalizedLevelName() {
/* 321 */     String str = getCachedLocalizedLevelName();
/* 322 */     if (str != null) {
/* 323 */       return str;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 328 */     Locale locale = Locale.getDefault();
/*     */     try {
/* 330 */       this.localizedLevelName = computeLocalizedLevelName(locale);
/* 331 */     } catch (Exception exception) {
/* 332 */       this.localizedLevelName = this.name;
/*     */     } 
/* 334 */     this.cachedLocale = locale;
/* 335 */     return this.localizedLevelName;
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
/*     */   static Level findLevel(String paramString) {
/* 350 */     if (paramString == null) {
/* 351 */       throw new NullPointerException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 357 */     KnownLevel knownLevel = KnownLevel.findByName(paramString);
/* 358 */     if (knownLevel != null) {
/* 359 */       return knownLevel.mirroredLevel;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 366 */       int i = Integer.parseInt(paramString);
/* 367 */       knownLevel = KnownLevel.findByValue(i);
/* 368 */       if (knownLevel == null) {
/*     */         
/* 370 */         Level level = new Level(paramString, i);
/* 371 */         knownLevel = KnownLevel.findByValue(i);
/*     */       } 
/* 373 */       return knownLevel.mirroredLevel;
/* 374 */     } catch (NumberFormatException numberFormatException) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 379 */       knownLevel = KnownLevel.findByLocalizedLevelName(paramString);
/* 380 */       if (knownLevel != null) {
/* 381 */         return knownLevel.mirroredLevel;
/*     */       }
/*     */       
/* 384 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String toString() {
/* 394 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int intValue() {
/* 404 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 412 */     KnownLevel knownLevel = KnownLevel.matches(this);
/* 413 */     if (knownLevel != null) {
/* 414 */       return knownLevel.levelObject;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 419 */     return new Level(this.name, this.value, this.resourceBundleName);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized Level parse(String paramString) throws IllegalArgumentException {
/* 452 */     paramString.length();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 457 */     KnownLevel knownLevel = KnownLevel.findByName(paramString);
/* 458 */     if (knownLevel != null) {
/* 459 */       return knownLevel.levelObject;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 466 */       int i = Integer.parseInt(paramString);
/* 467 */       knownLevel = KnownLevel.findByValue(i);
/* 468 */       if (knownLevel == null) {
/*     */         
/* 470 */         Level level = new Level(paramString, i);
/* 471 */         knownLevel = KnownLevel.findByValue(i);
/*     */       } 
/* 473 */       return knownLevel.levelObject;
/* 474 */     } catch (NumberFormatException numberFormatException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 482 */       knownLevel = KnownLevel.findByLocalizedLevelName(paramString);
/* 483 */       if (knownLevel != null) {
/* 484 */         return knownLevel.levelObject;
/*     */       }
/*     */ 
/*     */       
/* 488 */       throw new IllegalArgumentException("Bad level \"" + paramString + "\"");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*     */     try {
/* 498 */       Level level = (Level)paramObject;
/* 499 */       return (level.value == this.value);
/* 500 */     } catch (Exception exception) {
/* 501 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 511 */     return this.value;
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
/*     */   static final class KnownLevel
/*     */   {
/* 535 */     private static Map<String, List<KnownLevel>> nameToLevels = new HashMap<>();
/* 536 */     private static Map<Integer, List<KnownLevel>> intToLevels = new HashMap<>();
/*     */     final Level levelObject;
/*     */     
/*     */     KnownLevel(Level param1Level) {
/* 540 */       this.levelObject = param1Level;
/* 541 */       if (param1Level.getClass() == Level.class) {
/* 542 */         this.mirroredLevel = param1Level;
/*     */       } else {
/*     */         
/* 545 */         this.mirroredLevel = new Level(param1Level.name, param1Level.value, param1Level.resourceBundleName, false);
/*     */       } 
/*     */     }
/*     */     
/*     */     final Level mirroredLevel;
/*     */     
/*     */     static synchronized void add(Level param1Level) {
/* 552 */       KnownLevel knownLevel = new KnownLevel(param1Level);
/* 553 */       List<KnownLevel> list = nameToLevels.get(param1Level.name);
/* 554 */       if (list == null) {
/* 555 */         list = new ArrayList();
/* 556 */         nameToLevels.put(param1Level.name, list);
/*     */       } 
/* 558 */       list.add(knownLevel);
/*     */       
/* 560 */       list = intToLevels.get(Integer.valueOf(param1Level.value));
/* 561 */       if (list == null) {
/* 562 */         list = new ArrayList<>();
/* 563 */         intToLevels.put(Integer.valueOf(param1Level.value), list);
/*     */       } 
/* 565 */       list.add(knownLevel);
/*     */     }
/*     */ 
/*     */     
/*     */     static synchronized KnownLevel findByName(String param1String) {
/* 570 */       List<KnownLevel> list = nameToLevels.get(param1String);
/* 571 */       if (list != null) {
/* 572 */         return list.get(0);
/*     */       }
/* 574 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     static synchronized KnownLevel findByValue(int param1Int) {
/* 579 */       List<KnownLevel> list = intToLevels.get(Integer.valueOf(param1Int));
/* 580 */       if (list != null) {
/* 581 */         return list.get(0);
/*     */       }
/* 583 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static synchronized KnownLevel findByLocalizedLevelName(String param1String) {
/* 592 */       for (List<KnownLevel> list : nameToLevels.values()) {
/* 593 */         for (KnownLevel knownLevel : list) {
/* 594 */           String str = knownLevel.levelObject.getLocalizedLevelName();
/* 595 */           if (param1String.equals(str)) {
/* 596 */             return knownLevel;
/*     */           }
/*     */         } 
/*     */       } 
/* 600 */       return null;
/*     */     }
/*     */     
/*     */     static synchronized KnownLevel matches(Level param1Level) {
/* 604 */       List list = nameToLevels.get(param1Level.name);
/* 605 */       if (list != null) {
/* 606 */         for (KnownLevel knownLevel : list) {
/* 607 */           Level level = knownLevel.mirroredLevel;
/* 608 */           Class<?> clazz = knownLevel.levelObject.getClass();
/* 609 */           if (param1Level.value == level.value && (param1Level
/* 610 */             .resourceBundleName == level.resourceBundleName || (param1Level
/* 611 */             .resourceBundleName != null && param1Level
/* 612 */             .resourceBundleName.equals(level.resourceBundleName))) && 
/* 613 */             clazz == param1Level.getClass()) {
/* 614 */             return knownLevel;
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 619 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/logging/Level.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */