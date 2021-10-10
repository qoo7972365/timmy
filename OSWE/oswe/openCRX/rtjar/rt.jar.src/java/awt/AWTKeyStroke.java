/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import sun.awt.AppContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AWTKeyStroke
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -6430539691155161871L;
/*     */   private static Map<String, Integer> modifierKeywords;
/*     */   private static VKCollection vks;
/*  79 */   private static Object APP_CONTEXT_CACHE_KEY = new Object();
/*     */   
/*  81 */   private static AWTKeyStroke APP_CONTEXT_KEYSTROKE_KEY = new AWTKeyStroke();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<AWTKeyStroke> getAWTKeyStrokeClass() {
/*  89 */     Class<AWTKeyStroke> clazz = (Class)AppContext.getAppContext().get(AWTKeyStroke.class);
/*  90 */     if (clazz == null) {
/*  91 */       clazz = AWTKeyStroke.class;
/*  92 */       AppContext.getAppContext().put(AWTKeyStroke.class, AWTKeyStroke.class);
/*     */     } 
/*  94 */     return clazz;
/*     */   }
/*     */   
/*  97 */   private char keyChar = Character.MAX_VALUE;
/*  98 */   private int keyCode = 0;
/*     */   
/*     */   private int modifiers;
/*     */   private boolean onKeyRelease;
/*     */   
/*     */   static {
/* 104 */     Toolkit.loadLibraries();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AWTKeyStroke(char paramChar, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 155 */     this.keyChar = paramChar;
/* 156 */     this.keyCode = paramInt1;
/* 157 */     this.modifiers = paramInt2;
/* 158 */     this.onKeyRelease = paramBoolean;
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
/*     */   protected static void registerSubclass(Class<?> paramClass) {
/* 181 */     if (paramClass == null) {
/* 182 */       throw new IllegalArgumentException("subclass cannot be null");
/*     */     }
/* 184 */     synchronized (AWTKeyStroke.class) {
/* 185 */       Class clazz = (Class)AppContext.getAppContext().get(AWTKeyStroke.class);
/* 186 */       if (clazz != null && clazz.equals(paramClass)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 191 */     if (!AWTKeyStroke.class.isAssignableFrom(paramClass)) {
/* 192 */       throw new ClassCastException("subclass is not derived from AWTKeyStroke");
/*     */     }
/*     */     
/* 195 */     Constructor<AWTKeyStroke> constructor = getCtor(paramClass);
/*     */     
/* 197 */     String str = "subclass could not be instantiated";
/*     */     
/* 199 */     if (constructor == null) {
/* 200 */       throw new IllegalArgumentException(str);
/*     */     }
/*     */     try {
/* 203 */       AWTKeyStroke aWTKeyStroke = constructor.newInstance((Object[])null);
/* 204 */       if (aWTKeyStroke == null) {
/* 205 */         throw new IllegalArgumentException(str);
/*     */       }
/* 207 */     } catch (NoSuchMethodError noSuchMethodError) {
/* 208 */       throw new IllegalArgumentException(str);
/* 209 */     } catch (ExceptionInInitializerError exceptionInInitializerError) {
/* 210 */       throw new IllegalArgumentException(str);
/* 211 */     } catch (InstantiationException instantiationException) {
/* 212 */       throw new IllegalArgumentException(str);
/* 213 */     } catch (IllegalAccessException illegalAccessException) {
/* 214 */       throw new IllegalArgumentException(str);
/* 215 */     } catch (InvocationTargetException invocationTargetException) {
/* 216 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 219 */     synchronized (AWTKeyStroke.class) {
/* 220 */       AppContext.getAppContext().put(AWTKeyStroke.class, paramClass);
/* 221 */       AppContext.getAppContext().remove(APP_CONTEXT_CACHE_KEY);
/* 222 */       AppContext.getAppContext().remove(APP_CONTEXT_KEYSTROKE_KEY);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Constructor getCtor(final Class clazz) {
/* 232 */     return AccessController.<Constructor>doPrivileged(new PrivilegedAction<Constructor>() {
/*     */           public Constructor run() {
/*     */             
/* 235 */             try { Constructor constructor = clazz.getDeclaredConstructor((Class[])null);
/* 236 */               if (constructor != null) {
/* 237 */                 constructor.setAccessible(true);
/*     */               }
/* 239 */               return constructor; }
/* 240 */             catch (SecurityException securityException) {  }
/* 241 */             catch (NoSuchMethodException noSuchMethodException) {}
/*     */             
/* 243 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized AWTKeyStroke getCachedStroke(char paramChar, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 252 */     Map<Object, Object> map = (Map)AppContext.getAppContext().get(APP_CONTEXT_CACHE_KEY);
/* 253 */     AWTKeyStroke aWTKeyStroke1 = (AWTKeyStroke)AppContext.getAppContext().get(APP_CONTEXT_KEYSTROKE_KEY);
/*     */     
/* 255 */     if (map == null) {
/* 256 */       map = new HashMap<>();
/* 257 */       AppContext.getAppContext().put(APP_CONTEXT_CACHE_KEY, map);
/*     */     } 
/*     */     
/* 260 */     if (aWTKeyStroke1 == null) {
/*     */       try {
/* 262 */         Class<AWTKeyStroke> clazz = getAWTKeyStrokeClass();
/* 263 */         aWTKeyStroke1 = getCtor(clazz).newInstance((Object[])null);
/* 264 */         AppContext.getAppContext().put(APP_CONTEXT_KEYSTROKE_KEY, aWTKeyStroke1);
/* 265 */       } catch (InstantiationException instantiationException) {
/*     */         assert false;
/* 267 */       } catch (IllegalAccessException illegalAccessException) {
/*     */         assert false;
/* 269 */       } catch (InvocationTargetException invocationTargetException) {
/*     */         assert false;
/*     */       } 
/*     */     }
/* 273 */     aWTKeyStroke1.keyChar = paramChar;
/* 274 */     aWTKeyStroke1.keyCode = paramInt1;
/* 275 */     aWTKeyStroke1.modifiers = mapNewModifiers(mapOldModifiers(paramInt2));
/* 276 */     aWTKeyStroke1.onKeyRelease = paramBoolean;
/*     */     
/* 278 */     AWTKeyStroke aWTKeyStroke2 = (AWTKeyStroke)map.get(aWTKeyStroke1);
/* 279 */     if (aWTKeyStroke2 == null) {
/* 280 */       aWTKeyStroke2 = aWTKeyStroke1;
/* 281 */       map.put(aWTKeyStroke2, aWTKeyStroke2);
/* 282 */       AppContext.getAppContext().remove(APP_CONTEXT_KEYSTROKE_KEY);
/*     */     } 
/* 284 */     return aWTKeyStroke2;
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
/*     */   public static AWTKeyStroke getAWTKeyStroke(char paramChar) {
/* 296 */     return getCachedStroke(paramChar, 0, 0, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AWTKeyStroke getAWTKeyStroke(Character paramCharacter, int paramInt) {
/* 338 */     if (paramCharacter == null) {
/* 339 */       throw new IllegalArgumentException("keyChar cannot be null");
/*     */     }
/* 341 */     return getCachedStroke(paramCharacter.charValue(), 0, paramInt, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AWTKeyStroke getAWTKeyStroke(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 391 */     return getCachedStroke('￿', paramInt1, paramInt2, paramBoolean);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AWTKeyStroke getAWTKeyStroke(int paramInt1, int paramInt2) {
/* 435 */     return getCachedStroke('￿', paramInt1, paramInt2, false);
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
/*     */   public static AWTKeyStroke getAWTKeyStrokeForEvent(KeyEvent paramKeyEvent) {
/* 454 */     int i = paramKeyEvent.getID();
/* 455 */     switch (i) {
/*     */       case 401:
/*     */       case 402:
/* 458 */         return getCachedStroke('￿', paramKeyEvent
/* 459 */             .getKeyCode(), paramKeyEvent
/* 460 */             .getModifiers(), (i == 402));
/*     */       
/*     */       case 400:
/* 463 */         return getCachedStroke(paramKeyEvent.getKeyChar(), 0, paramKeyEvent
/*     */             
/* 465 */             .getModifiers(), false);
/*     */     } 
/*     */ 
/*     */     
/* 469 */     return null;
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
/*     */   public static AWTKeyStroke getAWTKeyStroke(String paramString) {
/* 501 */     if (paramString == null) {
/* 502 */       throw new IllegalArgumentException("String cannot be null");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 507 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, " ");
/*     */     
/* 509 */     int i = 0;
/* 510 */     boolean bool1 = false;
/* 511 */     boolean bool2 = false;
/* 512 */     boolean bool3 = false;
/*     */     
/* 514 */     synchronized (AWTKeyStroke.class) {
/* 515 */       if (modifierKeywords == null) {
/* 516 */         HashMap<Object, Object> hashMap = new HashMap<>(8, 1.0F);
/* 517 */         hashMap.put("shift", 
/* 518 */             Integer.valueOf(65));
/*     */         
/* 520 */         hashMap.put("control", 
/* 521 */             Integer.valueOf(130));
/*     */         
/* 523 */         hashMap.put("ctrl", 
/* 524 */             Integer.valueOf(130));
/*     */         
/* 526 */         hashMap.put("meta", 
/* 527 */             Integer.valueOf(260));
/*     */         
/* 529 */         hashMap.put("alt", 
/* 530 */             Integer.valueOf(520));
/*     */         
/* 532 */         hashMap.put("altGraph", 
/* 533 */             Integer.valueOf(8224));
/*     */         
/* 535 */         hashMap.put("button1", 
/* 536 */             Integer.valueOf(1024));
/* 537 */         hashMap.put("button2", 
/* 538 */             Integer.valueOf(2048));
/* 539 */         hashMap.put("button3", 
/* 540 */             Integer.valueOf(4096));
/*     */         
/* 542 */         modifierKeywords = Collections.synchronizedMap(hashMap);
/*     */       } 
/*     */     } 
/*     */     
/* 546 */     int j = stringTokenizer.countTokens();
/*     */     
/* 548 */     for (byte b = 1; b <= j; b++) {
/* 549 */       String str = stringTokenizer.nextToken();
/*     */       
/* 551 */       if (bool2) {
/* 552 */         if (str.length() != 1 || b != j) {
/* 553 */           throw new IllegalArgumentException("String formatted incorrectly");
/*     */         }
/* 555 */         return getCachedStroke(str.charAt(0), 0, i, false);
/*     */       } 
/*     */ 
/*     */       
/* 559 */       if (bool3 || bool1 || b == j) {
/* 560 */         if (b != j) {
/* 561 */           throw new IllegalArgumentException("String formatted incorrectly");
/*     */         }
/*     */         
/* 564 */         String str1 = "VK_" + str;
/* 565 */         int k = getVKValue(str1);
/*     */         
/* 567 */         return getCachedStroke('￿', k, i, bool1);
/*     */       } 
/*     */ 
/*     */       
/* 571 */       if (str.equals("released")) {
/* 572 */         bool1 = true;
/*     */       
/*     */       }
/* 575 */       else if (str.equals("pressed")) {
/* 576 */         bool3 = true;
/*     */       
/*     */       }
/* 579 */       else if (str.equals("typed")) {
/* 580 */         bool2 = true;
/*     */       }
/*     */       else {
/*     */         
/* 584 */         Integer integer = modifierKeywords.get(str);
/* 585 */         if (integer != null) {
/* 586 */           i |= integer.intValue();
/*     */         } else {
/* 588 */           throw new IllegalArgumentException("String formatted incorrectly");
/*     */         } 
/*     */       } 
/*     */     } 
/* 592 */     throw new IllegalArgumentException("String formatted incorrectly");
/*     */   }
/*     */   
/*     */   private static VKCollection getVKCollection() {
/* 596 */     if (vks == null) {
/* 597 */       vks = new VKCollection();
/*     */     }
/* 599 */     return vks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getVKValue(String paramString) {
/* 608 */     VKCollection vKCollection = getVKCollection();
/*     */     
/* 610 */     Integer integer = vKCollection.findCode(paramString);
/*     */     
/* 612 */     if (integer == null) {
/* 613 */       int i = 0;
/*     */ 
/*     */       
/*     */       try {
/* 617 */         i = KeyEvent.class.getField(paramString).getInt(KeyEvent.class);
/* 618 */       } catch (NoSuchFieldException noSuchFieldException) {
/* 619 */         throw new IllegalArgumentException("String formatted incorrectly");
/* 620 */       } catch (IllegalAccessException illegalAccessException) {
/* 621 */         throw new IllegalArgumentException("String formatted incorrectly");
/*     */       } 
/* 623 */       integer = Integer.valueOf(i);
/* 624 */       vKCollection.put(paramString, integer);
/*     */     } 
/* 626 */     return integer.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final char getKeyChar() {
/* 637 */     return this.keyChar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getKeyCode() {
/* 648 */     return this.keyCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getModifiers() {
/* 658 */     return this.modifiers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isOnKeyRelease() {
/* 669 */     return this.onKeyRelease;
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
/*     */   public final int getKeyEventType() {
/* 682 */     if (this.keyCode == 0) {
/* 683 */       return 400;
/*     */     }
/* 685 */     return this.onKeyRelease ? 402 : 401;
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
/*     */   public int hashCode() {
/* 698 */     return (this.keyChar + 1) * 2 * (this.keyCode + 1) * (this.modifiers + 1) + (this.onKeyRelease ? 1 : 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean equals(Object paramObject) {
/* 709 */     if (paramObject instanceof AWTKeyStroke) {
/* 710 */       AWTKeyStroke aWTKeyStroke = (AWTKeyStroke)paramObject;
/* 711 */       return (aWTKeyStroke.keyChar == this.keyChar && aWTKeyStroke.keyCode == this.keyCode && aWTKeyStroke.onKeyRelease == this.onKeyRelease && aWTKeyStroke.modifiers == this.modifiers);
/*     */     } 
/*     */ 
/*     */     
/* 715 */     return false;
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
/*     */   public String toString() {
/* 728 */     if (this.keyCode == 0) {
/* 729 */       return getModifiersText(this.modifiers) + "typed " + this.keyChar;
/*     */     }
/* 731 */     return getModifiersText(this.modifiers) + (this.onKeyRelease ? "released" : "pressed") + " " + 
/*     */       
/* 733 */       getVKText(this.keyCode);
/*     */   }
/*     */ 
/*     */   
/*     */   static String getModifiersText(int paramInt) {
/* 738 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/* 740 */     if ((paramInt & 0x40) != 0) {
/* 741 */       stringBuilder.append("shift ");
/*     */     }
/* 743 */     if ((paramInt & 0x80) != 0) {
/* 744 */       stringBuilder.append("ctrl ");
/*     */     }
/* 746 */     if ((paramInt & 0x100) != 0) {
/* 747 */       stringBuilder.append("meta ");
/*     */     }
/* 749 */     if ((paramInt & 0x200) != 0) {
/* 750 */       stringBuilder.append("alt ");
/*     */     }
/* 752 */     if ((paramInt & 0x2000) != 0) {
/* 753 */       stringBuilder.append("altGraph ");
/*     */     }
/* 755 */     if ((paramInt & 0x400) != 0) {
/* 756 */       stringBuilder.append("button1 ");
/*     */     }
/* 758 */     if ((paramInt & 0x800) != 0) {
/* 759 */       stringBuilder.append("button2 ");
/*     */     }
/* 761 */     if ((paramInt & 0x1000) != 0) {
/* 762 */       stringBuilder.append("button3 ");
/*     */     }
/*     */     
/* 765 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   static String getVKText(int paramInt) {
/* 769 */     VKCollection vKCollection = getVKCollection();
/* 770 */     Integer integer = Integer.valueOf(paramInt);
/* 771 */     String str = vKCollection.findName(integer);
/* 772 */     if (str != null) {
/* 773 */       return str.substring(3);
/*     */     }
/* 775 */     byte b1 = 25;
/*     */ 
/*     */     
/* 778 */     Field[] arrayOfField = KeyEvent.class.getDeclaredFields();
/* 779 */     for (byte b2 = 0; b2 < arrayOfField.length; b2++) {
/*     */       try {
/* 781 */         if (arrayOfField[b2].getModifiers() == b1 && arrayOfField[b2]
/* 782 */           .getType() == int.class && arrayOfField[b2]
/* 783 */           .getName().startsWith("VK_") && arrayOfField[b2]
/* 784 */           .getInt(KeyEvent.class) == paramInt) {
/*     */           
/* 786 */           str = arrayOfField[b2].getName();
/* 787 */           vKCollection.put(str, integer);
/* 788 */           return str.substring(3);
/*     */         } 
/* 790 */       } catch (IllegalAccessException illegalAccessException) {
/*     */         assert false;
/*     */       } 
/*     */     } 
/* 794 */     return "UNKNOWN";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object readResolve() throws ObjectStreamException {
/* 804 */     synchronized (AWTKeyStroke.class) {
/* 805 */       if (getClass().equals(getAWTKeyStrokeClass())) {
/* 806 */         return getCachedStroke(this.keyChar, this.keyCode, this.modifiers, this.onKeyRelease);
/*     */       }
/*     */     } 
/* 809 */     return this;
/*     */   }
/*     */   
/*     */   private static int mapOldModifiers(int paramInt) {
/* 813 */     if ((paramInt & 0x1) != 0) {
/* 814 */       paramInt |= 0x40;
/*     */     }
/* 816 */     if ((paramInt & 0x8) != 0) {
/* 817 */       paramInt |= 0x200;
/*     */     }
/* 819 */     if ((paramInt & 0x20) != 0) {
/* 820 */       paramInt |= 0x2000;
/*     */     }
/* 822 */     if ((paramInt & 0x2) != 0) {
/* 823 */       paramInt |= 0x80;
/*     */     }
/* 825 */     if ((paramInt & 0x4) != 0) {
/* 826 */       paramInt |= 0x100;
/*     */     }
/*     */     
/* 829 */     paramInt &= 0x3FC0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 838 */     return paramInt;
/*     */   }
/*     */   
/*     */   private static int mapNewModifiers(int paramInt) {
/* 842 */     if ((paramInt & 0x40) != 0) {
/* 843 */       paramInt |= 0x1;
/*     */     }
/* 845 */     if ((paramInt & 0x200) != 0) {
/* 846 */       paramInt |= 0x8;
/*     */     }
/* 848 */     if ((paramInt & 0x2000) != 0) {
/* 849 */       paramInt |= 0x20;
/*     */     }
/* 851 */     if ((paramInt & 0x80) != 0) {
/* 852 */       paramInt |= 0x2;
/*     */     }
/* 854 */     if ((paramInt & 0x100) != 0) {
/* 855 */       paramInt |= 0x4;
/*     */     }
/*     */     
/* 858 */     return paramInt;
/*     */   }
/*     */   
/*     */   protected AWTKeyStroke() {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/AWTKeyStroke.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */