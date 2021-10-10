/*      */ package sun.awt.X11;
/*      */ 
/*      */ import java.util.Hashtable;
/*      */ import sun.misc.Unsafe;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XKeysym
/*      */ {
/*      */   static class Keysym2JavaKeycode
/*      */   {
/*      */     int jkeycode;
/*      */     int keyLocation;
/*      */     
/*      */     int getJavaKeycode() {
/*   53 */       return this.jkeycode;
/*      */     }
/*      */     int getKeyLocation() {
/*   56 */       return this.keyLocation;
/*      */     }
/*      */     Keysym2JavaKeycode(int param1Int1, int param1Int2) {
/*   59 */       this.jkeycode = param1Int1;
/*   60 */       this.keyLocation = param1Int2;
/*      */     }
/*      */   }
/*   63 */   private static Unsafe unsafe = XlibWrapper.unsafe;
/*   64 */   static Hashtable<Long, Keysym2JavaKeycode> keysym2JavaKeycodeHash = new Hashtable<>();
/*   65 */   static Hashtable<Long, Character> keysym2UCSHash = new Hashtable<>();
/*   66 */   static Hashtable<Long, Long> uppercaseHash = new Hashtable<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   71 */   static Hashtable<Integer, Long> javaKeycode2KeysymHash = new Hashtable<>();
/*   72 */   static long keysym_lowercase = unsafe.allocateMemory(Native.getLongSize());
/*   73 */   static long keysym_uppercase = unsafe.allocateMemory(Native.getLongSize());
/*   74 */   static Keysym2JavaKeycode kanaLock = new Keysym2JavaKeycode(262, 1);
/*      */   
/*   76 */   private static PlatformLogger keyEventLog = PlatformLogger.getLogger("sun.awt.X11.key.XKeysym");
/*      */ 
/*      */   
/*      */   public static char convertKeysym(long paramLong, int paramInt) {
/*   80 */     if ((paramLong >= 32L && paramLong <= 126L) || (paramLong >= 160L && paramLong <= 255L)) {
/*      */       
/*   82 */       if ((paramInt & 0x4) != 0 && ((
/*   83 */         paramLong >= 65L && paramLong <= 93L) || paramLong == 95L || (paramLong >= 97L && paramLong <= 122L)))
/*      */       {
/*   85 */         paramLong &= 0x1FL;
/*      */       }
/*      */       
/*   88 */       return (char)(int)paramLong;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*   93 */     if ((paramLong & 0xFFFFFFFFFF000000L) == 16777216L) {
/*   94 */       return (char)(int)(paramLong & 0xFFFFFFL);
/*      */     }
/*   96 */     Character character = keysym2UCSHash.get(Long.valueOf(paramLong));
/*   97 */     return (character == null) ? Character.MIN_VALUE : character.charValue();
/*      */   }
/*      */   static long xkeycode2keysym_noxkb(XKeyEvent paramXKeyEvent, int paramInt) {
/*  100 */     XToolkit.awtLock();
/*      */     try {
/*  102 */       return XlibWrapper.XKeycodeToKeysym(paramXKeyEvent.get_display(), paramXKeyEvent.get_keycode(), paramInt);
/*      */     } finally {
/*  104 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   static long xkeycode2keysym_xkb(XKeyEvent paramXKeyEvent, int paramInt) {
/*  108 */     XToolkit.awtLock();
/*      */     try {
/*  110 */       int i = paramXKeyEvent.get_state();
/*  111 */       if (paramInt == 0 && (i & 0x1) != 0)
/*      */       {
/*      */ 
/*      */         
/*  115 */         i ^= 0x1;
/*      */       }
/*  117 */       long l = XToolkit.getXKBKbdDesc();
/*  118 */       if (l != 0L) {
/*  119 */         XlibWrapper.XkbTranslateKeyCode(l, paramXKeyEvent.get_keycode(), i, XlibWrapper.iarg1, XlibWrapper.larg3);
/*      */       }
/*      */       else {
/*      */         
/*  123 */         keyEventLog.fine("Thread race: Toolkit shutdown before the end of a key event processing.");
/*  124 */         return 0L;
/*      */       } 
/*      */       
/*  127 */       return Native.getLong(XlibWrapper.larg3);
/*      */     } finally {
/*  129 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   static long xkeycode2keysym(XKeyEvent paramXKeyEvent, int paramInt) {
/*  133 */     XToolkit.awtLock();
/*      */     try {
/*  135 */       if (XToolkit.canUseXKBCalls()) {
/*  136 */         return xkeycode2keysym_xkb(paramXKeyEvent, paramInt);
/*      */       }
/*  138 */       return xkeycode2keysym_noxkb(paramXKeyEvent, paramInt);
/*      */     } finally {
/*      */       
/*  141 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   static long xkeycode2primary_keysym(XKeyEvent paramXKeyEvent) {
/*  145 */     return xkeycode2keysym(paramXKeyEvent, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isKPEvent(XKeyEvent paramXKeyEvent) {
/*  152 */     boolean bool = (XToolkit.isXsunKPBehavior() && !XToolkit.isXKBenabled()) ? true : true;
/*      */ 
/*      */ 
/*      */     
/*  156 */     XToolkit.awtLock();
/*      */     try {
/*  158 */       return (XlibWrapper.IsKeypadKey(
/*  159 */           XlibWrapper.XKeycodeToKeysym(paramXKeyEvent.get_display(), paramXKeyEvent.get_keycode(), bool)) || 
/*  160 */         XlibWrapper.IsKeypadKey(
/*  161 */           XlibWrapper.XKeycodeToKeysym(paramXKeyEvent.get_display(), paramXKeyEvent.get_keycode(), 0)));
/*      */     } finally {
/*  163 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getUppercaseAlphabetic(long paramLong) {
/*  171 */     long l1 = -1L;
/*  172 */     long l2 = -1L;
/*  173 */     Long long_ = uppercaseHash.get(Long.valueOf(paramLong));
/*  174 */     if (long_ != null) {
/*  175 */       return long_.longValue();
/*      */     }
/*  177 */     XToolkit.awtLock();
/*      */     try {
/*  179 */       XlibWrapper.XConvertCase(paramLong, keysym_lowercase, keysym_uppercase);
/*  180 */       l1 = Native.getLong(keysym_lowercase);
/*  181 */       l2 = Native.getLong(keysym_uppercase);
/*  182 */       if (l1 == l2)
/*      */       {
/*  184 */         l2 = -1L;
/*      */       }
/*  186 */       uppercaseHash.put(Long.valueOf(paramLong), Long.valueOf(l2));
/*      */     } finally {
/*  188 */       XToolkit.awtUnlock();
/*      */     } 
/*  190 */     return l2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long getKeypadKeysym(XKeyEvent paramXKeyEvent) {
/*  197 */     byte b = 0;
/*  198 */     long l = 0L;
/*  199 */     if (XToolkit.isXsunKPBehavior() && 
/*  200 */       !XToolkit.isXKBenabled()) {
/*  201 */       if ((paramXKeyEvent.get_state() & 0x1) != 0) {
/*  202 */         b = 3;
/*  203 */         l = xkeycode2keysym(paramXKeyEvent, b);
/*      */       } else {
/*  205 */         b = 2;
/*  206 */         l = xkeycode2keysym(paramXKeyEvent, b);
/*      */       }
/*      */     
/*  209 */     } else if ((paramXKeyEvent.get_state() & 0x1) != 0 || ((paramXKeyEvent
/*  210 */       .get_state() & 0x2) != 0 && XToolkit.modLockIsShiftLock != 0)) {
/*      */       
/*  212 */       b = 0;
/*  213 */       l = xkeycode2keysym(paramXKeyEvent, b);
/*      */     } else {
/*  215 */       b = 1;
/*  216 */       l = xkeycode2keysym(paramXKeyEvent, b);
/*      */     } 
/*      */     
/*  219 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Keysym2JavaKeycode getJavaKeycode(long paramLong) {
/*  227 */     if (paramLong == 65406L) {
/*      */       
/*  229 */       if (XToolkit.isKanaKeyboard()) {
/*  230 */         return kanaLock;
/*      */       }
/*  232 */     } else if (paramLong == 65480L) {
/*      */       
/*  234 */       if (XToolkit.isSunKeyboard()) {
/*  235 */         paramLong = 65385L;
/*      */       }
/*  237 */     } else if (paramLong == 65481L) {
/*      */       
/*  239 */       if (XToolkit.isSunKeyboard()) {
/*  240 */         paramLong = 65382L;
/*      */       }
/*      */     } 
/*      */     
/*  244 */     return keysym2JavaKeycodeHash.get(Long.valueOf(paramLong));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Keysym2JavaKeycode getJavaKeycode(XKeyEvent paramXKeyEvent) {
/*  253 */     long l = 0L;
/*  254 */     boolean bool = false;
/*  255 */     if ((paramXKeyEvent.get_state() & XToolkit.numLockMask) != 0 && 
/*  256 */       isKPEvent(paramXKeyEvent)) {
/*  257 */       l = getKeypadKeysym(paramXKeyEvent);
/*      */     } else {
/*      */       
/*  260 */       bool = false;
/*  261 */       l = xkeycode2keysym(paramXKeyEvent, bool);
/*      */     } 
/*      */     
/*  264 */     return getJavaKeycode(l);
/*      */   }
/*      */   
/*      */   static int getJavaKeycodeOnly(XKeyEvent paramXKeyEvent) {
/*  268 */     Keysym2JavaKeycode keysym2JavaKeycode = getJavaKeycode(paramXKeyEvent);
/*  269 */     return (keysym2JavaKeycode == null) ? 0 : keysym2JavaKeycode.getJavaKeycode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int getLegacyJavaKeycodeOnly(XKeyEvent paramXKeyEvent) {
/*  279 */     long l = 0L;
/*  280 */     boolean bool = false;
/*  281 */     if ((paramXKeyEvent.get_state() & XToolkit.numLockMask) != 0 && 
/*  282 */       isKPEvent(paramXKeyEvent)) {
/*  283 */       l = getKeypadKeysym(paramXKeyEvent);
/*      */     } else {
/*      */       
/*  286 */       bool = false;
/*  287 */       l = xkeycode2keysym_noxkb(paramXKeyEvent, bool);
/*      */     } 
/*  289 */     Keysym2JavaKeycode keysym2JavaKeycode = getJavaKeycode(l);
/*  290 */     return (keysym2JavaKeycode == null) ? 0 : keysym2JavaKeycode.getJavaKeycode();
/*      */   }
/*      */   static long javaKeycode2Keysym(int paramInt) {
/*  293 */     Long long_ = javaKeycode2KeysymHash.get(Integer.valueOf(paramInt));
/*  294 */     return (long_ == null) ? 0L : long_.longValue();
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
/*      */   static long getKeysym(XKeyEvent paramXKeyEvent) {
/*  307 */     long l1 = 0L;
/*  308 */     long l2 = 0L;
/*  309 */     boolean bool1 = false;
/*  310 */     boolean bool2 = false;
/*  311 */     if ((paramXKeyEvent.get_state() & XToolkit.numLockMask) != 0 && 
/*  312 */       isKPEvent(paramXKeyEvent)) {
/*  313 */       l1 = getKeypadKeysym(paramXKeyEvent);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  323 */       if ((paramXKeyEvent.get_state() & 0x1) == 0) {
/*  324 */         if ((paramXKeyEvent.get_state() & 0x2) == 0) {
/*  325 */           bool1 = false;
/*  326 */           bool2 = false;
/*  327 */         } else if ((paramXKeyEvent.get_state() & 0x2) != 0 && XToolkit.modLockIsShiftLock == 0) {
/*      */           
/*  329 */           bool1 = false;
/*  330 */           bool2 = true;
/*  331 */         } else if ((paramXKeyEvent.get_state() & 0x2) != 0 && XToolkit.modLockIsShiftLock != 0) {
/*      */           
/*  333 */           bool1 = true;
/*  334 */           bool2 = false;
/*      */         }
/*      */       
/*  337 */       } else if ((paramXKeyEvent.get_state() & 0x2) != 0 && XToolkit.modLockIsShiftLock == 0) {
/*      */         
/*  339 */         bool1 = true;
/*  340 */         bool2 = true;
/*      */       } else {
/*  342 */         bool1 = true;
/*  343 */         bool2 = false;
/*      */       } 
/*      */       
/*  346 */       l1 = xkeycode2keysym(paramXKeyEvent, bool1);
/*  347 */       if (bool2 && (l2 = getUppercaseAlphabetic(l1)) != -1L) {
/*  348 */         l1 = l2;
/*      */       }
/*      */     } 
/*  351 */     return l1;
/*      */   }
/*      */   
/*      */   static {
/*  355 */     keysym2UCSHash.put(Long.valueOf(65288L), Character.valueOf('\b'));
/*  356 */     keysym2UCSHash.put(Long.valueOf(65289L), Character.valueOf('\t'));
/*  357 */     keysym2UCSHash.put(Long.valueOf(65290L), Character.valueOf('\n'));
/*  358 */     keysym2UCSHash.put(Long.valueOf(65291L), Character.valueOf('\013'));
/*  359 */     keysym2UCSHash.put(Long.valueOf(65293L), Character.valueOf('\n'));
/*  360 */     keysym2UCSHash.put(Long.valueOf(65307L), Character.valueOf('\033'));
/*  361 */     keysym2UCSHash.put(Long.valueOf(65535L), Character.valueOf(''));
/*  362 */     keysym2UCSHash.put(Long.valueOf(65408L), Character.valueOf(' '));
/*  363 */     keysym2UCSHash.put(Long.valueOf(65417L), Character.valueOf('\t'));
/*  364 */     keysym2UCSHash.put(Long.valueOf(65421L), Character.valueOf('\n'));
/*  365 */     keysym2UCSHash.put(Long.valueOf(65439L), Character.valueOf(''));
/*  366 */     keysym2UCSHash.put(Long.valueOf(65469L), Character.valueOf('='));
/*  367 */     keysym2UCSHash.put(Long.valueOf(65450L), Character.valueOf('*'));
/*  368 */     keysym2UCSHash.put(Long.valueOf(65451L), Character.valueOf('+'));
/*  369 */     keysym2UCSHash.put(Long.valueOf(65452L), Character.valueOf(','));
/*  370 */     keysym2UCSHash.put(Long.valueOf(65453L), Character.valueOf('-'));
/*  371 */     keysym2UCSHash.put(Long.valueOf(65454L), Character.valueOf('.'));
/*  372 */     keysym2UCSHash.put(Long.valueOf(65455L), Character.valueOf('/'));
/*  373 */     keysym2UCSHash.put(Long.valueOf(65456L), Character.valueOf('0'));
/*  374 */     keysym2UCSHash.put(Long.valueOf(65457L), Character.valueOf('1'));
/*  375 */     keysym2UCSHash.put(Long.valueOf(65458L), Character.valueOf('2'));
/*  376 */     keysym2UCSHash.put(Long.valueOf(65459L), Character.valueOf('3'));
/*  377 */     keysym2UCSHash.put(Long.valueOf(65460L), Character.valueOf('4'));
/*  378 */     keysym2UCSHash.put(Long.valueOf(65461L), Character.valueOf('5'));
/*  379 */     keysym2UCSHash.put(Long.valueOf(65462L), Character.valueOf('6'));
/*  380 */     keysym2UCSHash.put(Long.valueOf(65463L), Character.valueOf('7'));
/*  381 */     keysym2UCSHash.put(Long.valueOf(65464L), Character.valueOf('8'));
/*  382 */     keysym2UCSHash.put(Long.valueOf(65465L), Character.valueOf('9'));
/*  383 */     keysym2UCSHash.put(Long.valueOf(65056L), Character.valueOf('\t'));
/*  384 */     keysym2UCSHash.put(Long.valueOf(65104L), Character.valueOf('ˋ'));
/*  385 */     keysym2UCSHash.put(Long.valueOf(65105L), Character.valueOf('ˊ'));
/*  386 */     keysym2UCSHash.put(Long.valueOf(65106L), Character.valueOf('ˆ'));
/*  387 */     keysym2UCSHash.put(Long.valueOf(65107L), Character.valueOf('˜'));
/*  388 */     keysym2UCSHash.put(Long.valueOf(65108L), Character.valueOf('ˉ'));
/*  389 */     keysym2UCSHash.put(Long.valueOf(65109L), Character.valueOf('˘'));
/*  390 */     keysym2UCSHash.put(Long.valueOf(65110L), Character.valueOf('˙'));
/*  391 */     keysym2UCSHash.put(Long.valueOf(65111L), Character.valueOf('¨'));
/*  392 */     keysym2UCSHash.put(Long.valueOf(65112L), Character.valueOf('˚'));
/*  393 */     keysym2UCSHash.put(Long.valueOf(65113L), Character.valueOf('˝'));
/*  394 */     keysym2UCSHash.put(Long.valueOf(65114L), Character.valueOf('ˇ'));
/*  395 */     keysym2UCSHash.put(Long.valueOf(65115L), Character.valueOf('¸'));
/*  396 */     keysym2UCSHash.put(Long.valueOf(65116L), Character.valueOf('˛'));
/*  397 */     keysym2UCSHash.put(Long.valueOf(65117L), Character.valueOf('ɩ'));
/*  398 */     keysym2UCSHash.put(Long.valueOf(65118L), Character.valueOf('゙'));
/*  399 */     keysym2UCSHash.put(Long.valueOf(65119L), Character.valueOf('゚'));
/*  400 */     keysym2UCSHash.put(Long.valueOf(65120L), Character.valueOf('̣'));
/*  401 */     keysym2UCSHash.put(Long.valueOf(65121L), Character.valueOf('̡'));
/*  402 */     keysym2UCSHash.put(Long.valueOf(65122L), Character.valueOf('̛'));
/*  403 */     keysym2UCSHash.put(Long.valueOf(417L), Character.valueOf('Ą'));
/*  404 */     keysym2UCSHash.put(Long.valueOf(418L), Character.valueOf('˘'));
/*  405 */     keysym2UCSHash.put(Long.valueOf(419L), Character.valueOf('Ł'));
/*  406 */     keysym2UCSHash.put(Long.valueOf(421L), Character.valueOf('Ľ'));
/*  407 */     keysym2UCSHash.put(Long.valueOf(422L), Character.valueOf('Ś'));
/*  408 */     keysym2UCSHash.put(Long.valueOf(425L), Character.valueOf('Š'));
/*  409 */     keysym2UCSHash.put(Long.valueOf(426L), Character.valueOf('Ş'));
/*  410 */     keysym2UCSHash.put(Long.valueOf(427L), Character.valueOf('Ť'));
/*  411 */     keysym2UCSHash.put(Long.valueOf(428L), Character.valueOf('Ź'));
/*  412 */     keysym2UCSHash.put(Long.valueOf(430L), Character.valueOf('Ž'));
/*  413 */     keysym2UCSHash.put(Long.valueOf(431L), Character.valueOf('Ż'));
/*  414 */     keysym2UCSHash.put(Long.valueOf(433L), Character.valueOf('ą'));
/*  415 */     keysym2UCSHash.put(Long.valueOf(434L), Character.valueOf('˛'));
/*  416 */     keysym2UCSHash.put(Long.valueOf(435L), Character.valueOf('ł'));
/*  417 */     keysym2UCSHash.put(Long.valueOf(437L), Character.valueOf('ľ'));
/*  418 */     keysym2UCSHash.put(Long.valueOf(438L), Character.valueOf('ś'));
/*  419 */     keysym2UCSHash.put(Long.valueOf(439L), Character.valueOf('ˇ'));
/*  420 */     keysym2UCSHash.put(Long.valueOf(441L), Character.valueOf('š'));
/*  421 */     keysym2UCSHash.put(Long.valueOf(442L), Character.valueOf('ş'));
/*  422 */     keysym2UCSHash.put(Long.valueOf(443L), Character.valueOf('ť'));
/*  423 */     keysym2UCSHash.put(Long.valueOf(444L), Character.valueOf('ź'));
/*  424 */     keysym2UCSHash.put(Long.valueOf(445L), Character.valueOf('˝'));
/*  425 */     keysym2UCSHash.put(Long.valueOf(446L), Character.valueOf('ž'));
/*  426 */     keysym2UCSHash.put(Long.valueOf(447L), Character.valueOf('ż'));
/*  427 */     keysym2UCSHash.put(Long.valueOf(448L), Character.valueOf('Ŕ'));
/*  428 */     keysym2UCSHash.put(Long.valueOf(451L), Character.valueOf('Ă'));
/*  429 */     keysym2UCSHash.put(Long.valueOf(453L), Character.valueOf('Ĺ'));
/*  430 */     keysym2UCSHash.put(Long.valueOf(454L), Character.valueOf('Ć'));
/*  431 */     keysym2UCSHash.put(Long.valueOf(456L), Character.valueOf('Č'));
/*  432 */     keysym2UCSHash.put(Long.valueOf(458L), Character.valueOf('Ę'));
/*  433 */     keysym2UCSHash.put(Long.valueOf(460L), Character.valueOf('Ě'));
/*  434 */     keysym2UCSHash.put(Long.valueOf(463L), Character.valueOf('Ď'));
/*  435 */     keysym2UCSHash.put(Long.valueOf(464L), Character.valueOf('Đ'));
/*  436 */     keysym2UCSHash.put(Long.valueOf(465L), Character.valueOf('Ń'));
/*  437 */     keysym2UCSHash.put(Long.valueOf(466L), Character.valueOf('Ň'));
/*  438 */     keysym2UCSHash.put(Long.valueOf(469L), Character.valueOf('Ő'));
/*  439 */     keysym2UCSHash.put(Long.valueOf(472L), Character.valueOf('Ř'));
/*  440 */     keysym2UCSHash.put(Long.valueOf(473L), Character.valueOf('Ů'));
/*  441 */     keysym2UCSHash.put(Long.valueOf(475L), Character.valueOf('Ű'));
/*  442 */     keysym2UCSHash.put(Long.valueOf(478L), Character.valueOf('Ţ'));
/*  443 */     keysym2UCSHash.put(Long.valueOf(480L), Character.valueOf('ŕ'));
/*  444 */     keysym2UCSHash.put(Long.valueOf(483L), Character.valueOf('ă'));
/*  445 */     keysym2UCSHash.put(Long.valueOf(485L), Character.valueOf('ĺ'));
/*  446 */     keysym2UCSHash.put(Long.valueOf(486L), Character.valueOf('ć'));
/*  447 */     keysym2UCSHash.put(Long.valueOf(488L), Character.valueOf('č'));
/*  448 */     keysym2UCSHash.put(Long.valueOf(490L), Character.valueOf('ę'));
/*  449 */     keysym2UCSHash.put(Long.valueOf(492L), Character.valueOf('ě'));
/*  450 */     keysym2UCSHash.put(Long.valueOf(495L), Character.valueOf('ď'));
/*  451 */     keysym2UCSHash.put(Long.valueOf(496L), Character.valueOf('đ'));
/*  452 */     keysym2UCSHash.put(Long.valueOf(497L), Character.valueOf('ń'));
/*  453 */     keysym2UCSHash.put(Long.valueOf(498L), Character.valueOf('ň'));
/*  454 */     keysym2UCSHash.put(Long.valueOf(501L), Character.valueOf('ő'));
/*  455 */     keysym2UCSHash.put(Long.valueOf(507L), Character.valueOf('ű'));
/*  456 */     keysym2UCSHash.put(Long.valueOf(504L), Character.valueOf('ř'));
/*  457 */     keysym2UCSHash.put(Long.valueOf(505L), Character.valueOf('ů'));
/*  458 */     keysym2UCSHash.put(Long.valueOf(510L), Character.valueOf('ţ'));
/*  459 */     keysym2UCSHash.put(Long.valueOf(511L), Character.valueOf('˙'));
/*  460 */     keysym2UCSHash.put(Long.valueOf(673L), Character.valueOf('Ħ'));
/*  461 */     keysym2UCSHash.put(Long.valueOf(678L), Character.valueOf('Ĥ'));
/*  462 */     keysym2UCSHash.put(Long.valueOf(681L), Character.valueOf('İ'));
/*  463 */     keysym2UCSHash.put(Long.valueOf(683L), Character.valueOf('Ğ'));
/*  464 */     keysym2UCSHash.put(Long.valueOf(684L), Character.valueOf('Ĵ'));
/*  465 */     keysym2UCSHash.put(Long.valueOf(689L), Character.valueOf('ħ'));
/*  466 */     keysym2UCSHash.put(Long.valueOf(694L), Character.valueOf('ĥ'));
/*  467 */     keysym2UCSHash.put(Long.valueOf(697L), Character.valueOf('ı'));
/*  468 */     keysym2UCSHash.put(Long.valueOf(699L), Character.valueOf('ğ'));
/*  469 */     keysym2UCSHash.put(Long.valueOf(700L), Character.valueOf('ĵ'));
/*  470 */     keysym2UCSHash.put(Long.valueOf(709L), Character.valueOf('Ċ'));
/*  471 */     keysym2UCSHash.put(Long.valueOf(710L), Character.valueOf('Ĉ'));
/*  472 */     keysym2UCSHash.put(Long.valueOf(725L), Character.valueOf('Ġ'));
/*  473 */     keysym2UCSHash.put(Long.valueOf(728L), Character.valueOf('Ĝ'));
/*  474 */     keysym2UCSHash.put(Long.valueOf(733L), Character.valueOf('Ŭ'));
/*  475 */     keysym2UCSHash.put(Long.valueOf(734L), Character.valueOf('Ŝ'));
/*  476 */     keysym2UCSHash.put(Long.valueOf(741L), Character.valueOf('ċ'));
/*  477 */     keysym2UCSHash.put(Long.valueOf(742L), Character.valueOf('ĉ'));
/*  478 */     keysym2UCSHash.put(Long.valueOf(757L), Character.valueOf('ġ'));
/*  479 */     keysym2UCSHash.put(Long.valueOf(760L), Character.valueOf('ĝ'));
/*  480 */     keysym2UCSHash.put(Long.valueOf(765L), Character.valueOf('ŭ'));
/*  481 */     keysym2UCSHash.put(Long.valueOf(766L), Character.valueOf('ŝ'));
/*  482 */     keysym2UCSHash.put(Long.valueOf(930L), Character.valueOf('ĸ'));
/*  483 */     keysym2UCSHash.put(Long.valueOf(931L), Character.valueOf('Ŗ'));
/*  484 */     keysym2UCSHash.put(Long.valueOf(933L), Character.valueOf('Ĩ'));
/*  485 */     keysym2UCSHash.put(Long.valueOf(934L), Character.valueOf('Ļ'));
/*  486 */     keysym2UCSHash.put(Long.valueOf(938L), Character.valueOf('Ē'));
/*  487 */     keysym2UCSHash.put(Long.valueOf(939L), Character.valueOf('Ģ'));
/*  488 */     keysym2UCSHash.put(Long.valueOf(940L), Character.valueOf('Ŧ'));
/*  489 */     keysym2UCSHash.put(Long.valueOf(947L), Character.valueOf('ŗ'));
/*  490 */     keysym2UCSHash.put(Long.valueOf(949L), Character.valueOf('ĩ'));
/*  491 */     keysym2UCSHash.put(Long.valueOf(950L), Character.valueOf('ļ'));
/*  492 */     keysym2UCSHash.put(Long.valueOf(954L), Character.valueOf('ē'));
/*  493 */     keysym2UCSHash.put(Long.valueOf(955L), Character.valueOf('ģ'));
/*  494 */     keysym2UCSHash.put(Long.valueOf(956L), Character.valueOf('ŧ'));
/*  495 */     keysym2UCSHash.put(Long.valueOf(957L), Character.valueOf('Ŋ'));
/*  496 */     keysym2UCSHash.put(Long.valueOf(959L), Character.valueOf('ŋ'));
/*  497 */     keysym2UCSHash.put(Long.valueOf(960L), Character.valueOf('Ā'));
/*  498 */     keysym2UCSHash.put(Long.valueOf(967L), Character.valueOf('Į'));
/*  499 */     keysym2UCSHash.put(Long.valueOf(972L), Character.valueOf('Ė'));
/*  500 */     keysym2UCSHash.put(Long.valueOf(975L), Character.valueOf('Ī'));
/*  501 */     keysym2UCSHash.put(Long.valueOf(977L), Character.valueOf('Ņ'));
/*  502 */     keysym2UCSHash.put(Long.valueOf(978L), Character.valueOf('Ō'));
/*  503 */     keysym2UCSHash.put(Long.valueOf(979L), Character.valueOf('Ķ'));
/*  504 */     keysym2UCSHash.put(Long.valueOf(985L), Character.valueOf('Ų'));
/*  505 */     keysym2UCSHash.put(Long.valueOf(989L), Character.valueOf('Ũ'));
/*  506 */     keysym2UCSHash.put(Long.valueOf(990L), Character.valueOf('Ū'));
/*  507 */     keysym2UCSHash.put(Long.valueOf(992L), Character.valueOf('ā'));
/*  508 */     keysym2UCSHash.put(Long.valueOf(999L), Character.valueOf('į'));
/*  509 */     keysym2UCSHash.put(Long.valueOf(1004L), Character.valueOf('ė'));
/*  510 */     keysym2UCSHash.put(Long.valueOf(1007L), Character.valueOf('ī'));
/*  511 */     keysym2UCSHash.put(Long.valueOf(1009L), Character.valueOf('ņ'));
/*  512 */     keysym2UCSHash.put(Long.valueOf(1010L), Character.valueOf('ō'));
/*  513 */     keysym2UCSHash.put(Long.valueOf(1011L), Character.valueOf('ķ'));
/*  514 */     keysym2UCSHash.put(Long.valueOf(1017L), Character.valueOf('ų'));
/*  515 */     keysym2UCSHash.put(Long.valueOf(1021L), Character.valueOf('ũ'));
/*  516 */     keysym2UCSHash.put(Long.valueOf(1022L), Character.valueOf('ū'));
/*  517 */     keysym2UCSHash.put(Long.valueOf(4769L), Character.valueOf('Ḃ'));
/*  518 */     keysym2UCSHash.put(Long.valueOf(4770L), Character.valueOf('ḃ'));
/*  519 */     keysym2UCSHash.put(Long.valueOf(4774L), Character.valueOf('Ḋ'));
/*  520 */     keysym2UCSHash.put(Long.valueOf(4776L), Character.valueOf('Ẁ'));
/*  521 */     keysym2UCSHash.put(Long.valueOf(4778L), Character.valueOf('Ẃ'));
/*  522 */     keysym2UCSHash.put(Long.valueOf(4779L), Character.valueOf('ḋ'));
/*  523 */     keysym2UCSHash.put(Long.valueOf(4780L), Character.valueOf('Ỳ'));
/*  524 */     keysym2UCSHash.put(Long.valueOf(4784L), Character.valueOf('Ḟ'));
/*  525 */     keysym2UCSHash.put(Long.valueOf(4785L), Character.valueOf('ḟ'));
/*  526 */     keysym2UCSHash.put(Long.valueOf(4788L), Character.valueOf('Ṁ'));
/*  527 */     keysym2UCSHash.put(Long.valueOf(4789L), Character.valueOf('ṁ'));
/*  528 */     keysym2UCSHash.put(Long.valueOf(4791L), Character.valueOf('Ṗ'));
/*  529 */     keysym2UCSHash.put(Long.valueOf(4792L), Character.valueOf('ẁ'));
/*  530 */     keysym2UCSHash.put(Long.valueOf(4793L), Character.valueOf('ṗ'));
/*  531 */     keysym2UCSHash.put(Long.valueOf(4794L), Character.valueOf('ẃ'));
/*  532 */     keysym2UCSHash.put(Long.valueOf(4795L), Character.valueOf('Ṡ'));
/*  533 */     keysym2UCSHash.put(Long.valueOf(4796L), Character.valueOf('ỳ'));
/*  534 */     keysym2UCSHash.put(Long.valueOf(4797L), Character.valueOf('Ẅ'));
/*  535 */     keysym2UCSHash.put(Long.valueOf(4798L), Character.valueOf('ẅ'));
/*  536 */     keysym2UCSHash.put(Long.valueOf(4799L), Character.valueOf('ṡ'));
/*  537 */     keysym2UCSHash.put(Long.valueOf(4816L), Character.valueOf('\027'));
/*  538 */     keysym2UCSHash.put(Long.valueOf(4823L), Character.valueOf('Ṫ'));
/*  539 */     keysym2UCSHash.put(Long.valueOf(4830L), Character.valueOf('Ŷ'));
/*  540 */     keysym2UCSHash.put(Long.valueOf(4848L), Character.valueOf('ŵ'));
/*  541 */     keysym2UCSHash.put(Long.valueOf(4855L), Character.valueOf('ṫ'));
/*  542 */     keysym2UCSHash.put(Long.valueOf(4862L), Character.valueOf('ŷ'));
/*  543 */     keysym2UCSHash.put(Long.valueOf(5052L), Character.valueOf('Œ'));
/*  544 */     keysym2UCSHash.put(Long.valueOf(5053L), Character.valueOf('œ'));
/*  545 */     keysym2UCSHash.put(Long.valueOf(5054L), Character.valueOf('Ÿ'));
/*  546 */     keysym2UCSHash.put(Long.valueOf(1150L), Character.valueOf('‾'));
/*  547 */     keysym2UCSHash.put(Long.valueOf(1185L), Character.valueOf('。'));
/*  548 */     keysym2UCSHash.put(Long.valueOf(1186L), Character.valueOf('「'));
/*  549 */     keysym2UCSHash.put(Long.valueOf(1187L), Character.valueOf('」'));
/*  550 */     keysym2UCSHash.put(Long.valueOf(1188L), Character.valueOf('、'));
/*  551 */     keysym2UCSHash.put(Long.valueOf(1189L), Character.valueOf('・'));
/*  552 */     keysym2UCSHash.put(Long.valueOf(1190L), Character.valueOf('ヲ'));
/*  553 */     keysym2UCSHash.put(Long.valueOf(1191L), Character.valueOf('ァ'));
/*  554 */     keysym2UCSHash.put(Long.valueOf(1192L), Character.valueOf('ィ'));
/*  555 */     keysym2UCSHash.put(Long.valueOf(1193L), Character.valueOf('ゥ'));
/*  556 */     keysym2UCSHash.put(Long.valueOf(1194L), Character.valueOf('ェ'));
/*  557 */     keysym2UCSHash.put(Long.valueOf(1195L), Character.valueOf('ォ'));
/*  558 */     keysym2UCSHash.put(Long.valueOf(1196L), Character.valueOf('ャ'));
/*  559 */     keysym2UCSHash.put(Long.valueOf(1197L), Character.valueOf('ュ'));
/*  560 */     keysym2UCSHash.put(Long.valueOf(1198L), Character.valueOf('ョ'));
/*  561 */     keysym2UCSHash.put(Long.valueOf(1199L), Character.valueOf('ッ'));
/*  562 */     keysym2UCSHash.put(Long.valueOf(1200L), Character.valueOf('ー'));
/*  563 */     keysym2UCSHash.put(Long.valueOf(1201L), Character.valueOf('ア'));
/*  564 */     keysym2UCSHash.put(Long.valueOf(1202L), Character.valueOf('イ'));
/*  565 */     keysym2UCSHash.put(Long.valueOf(1203L), Character.valueOf('ウ'));
/*  566 */     keysym2UCSHash.put(Long.valueOf(1204L), Character.valueOf('エ'));
/*  567 */     keysym2UCSHash.put(Long.valueOf(1205L), Character.valueOf('オ'));
/*  568 */     keysym2UCSHash.put(Long.valueOf(1206L), Character.valueOf('カ'));
/*  569 */     keysym2UCSHash.put(Long.valueOf(1207L), Character.valueOf('キ'));
/*  570 */     keysym2UCSHash.put(Long.valueOf(1208L), Character.valueOf('ク'));
/*  571 */     keysym2UCSHash.put(Long.valueOf(1209L), Character.valueOf('ケ'));
/*  572 */     keysym2UCSHash.put(Long.valueOf(1210L), Character.valueOf('コ'));
/*  573 */     keysym2UCSHash.put(Long.valueOf(1211L), Character.valueOf('サ'));
/*  574 */     keysym2UCSHash.put(Long.valueOf(1212L), Character.valueOf('シ'));
/*  575 */     keysym2UCSHash.put(Long.valueOf(1213L), Character.valueOf('ス'));
/*  576 */     keysym2UCSHash.put(Long.valueOf(1214L), Character.valueOf('セ'));
/*  577 */     keysym2UCSHash.put(Long.valueOf(1215L), Character.valueOf('ソ'));
/*  578 */     keysym2UCSHash.put(Long.valueOf(1216L), Character.valueOf('タ'));
/*  579 */     keysym2UCSHash.put(Long.valueOf(1217L), Character.valueOf('チ'));
/*  580 */     keysym2UCSHash.put(Long.valueOf(1218L), Character.valueOf('ツ'));
/*  581 */     keysym2UCSHash.put(Long.valueOf(1219L), Character.valueOf('テ'));
/*  582 */     keysym2UCSHash.put(Long.valueOf(1220L), Character.valueOf('ト'));
/*  583 */     keysym2UCSHash.put(Long.valueOf(1221L), Character.valueOf('ナ'));
/*  584 */     keysym2UCSHash.put(Long.valueOf(1222L), Character.valueOf('ニ'));
/*  585 */     keysym2UCSHash.put(Long.valueOf(1223L), Character.valueOf('ヌ'));
/*  586 */     keysym2UCSHash.put(Long.valueOf(1224L), Character.valueOf('ネ'));
/*  587 */     keysym2UCSHash.put(Long.valueOf(1225L), Character.valueOf('ノ'));
/*  588 */     keysym2UCSHash.put(Long.valueOf(1226L), Character.valueOf('ハ'));
/*  589 */     keysym2UCSHash.put(Long.valueOf(1227L), Character.valueOf('ヒ'));
/*  590 */     keysym2UCSHash.put(Long.valueOf(1228L), Character.valueOf('フ'));
/*  591 */     keysym2UCSHash.put(Long.valueOf(1229L), Character.valueOf('ヘ'));
/*  592 */     keysym2UCSHash.put(Long.valueOf(1230L), Character.valueOf('ホ'));
/*  593 */     keysym2UCSHash.put(Long.valueOf(1231L), Character.valueOf('マ'));
/*  594 */     keysym2UCSHash.put(Long.valueOf(1232L), Character.valueOf('ミ'));
/*  595 */     keysym2UCSHash.put(Long.valueOf(1233L), Character.valueOf('ム'));
/*  596 */     keysym2UCSHash.put(Long.valueOf(1234L), Character.valueOf('メ'));
/*  597 */     keysym2UCSHash.put(Long.valueOf(1235L), Character.valueOf('モ'));
/*  598 */     keysym2UCSHash.put(Long.valueOf(1236L), Character.valueOf('ヤ'));
/*  599 */     keysym2UCSHash.put(Long.valueOf(1237L), Character.valueOf('ユ'));
/*  600 */     keysym2UCSHash.put(Long.valueOf(1238L), Character.valueOf('ヨ'));
/*  601 */     keysym2UCSHash.put(Long.valueOf(1239L), Character.valueOf('ラ'));
/*  602 */     keysym2UCSHash.put(Long.valueOf(1240L), Character.valueOf('リ'));
/*  603 */     keysym2UCSHash.put(Long.valueOf(1241L), Character.valueOf('ル'));
/*  604 */     keysym2UCSHash.put(Long.valueOf(1242L), Character.valueOf('レ'));
/*  605 */     keysym2UCSHash.put(Long.valueOf(1243L), Character.valueOf('ロ'));
/*  606 */     keysym2UCSHash.put(Long.valueOf(1244L), Character.valueOf('ワ'));
/*  607 */     keysym2UCSHash.put(Long.valueOf(1245L), Character.valueOf('ン'));
/*  608 */     keysym2UCSHash.put(Long.valueOf(1246L), Character.valueOf('゛'));
/*  609 */     keysym2UCSHash.put(Long.valueOf(1247L), Character.valueOf('゜'));
/*  610 */     keysym2UCSHash.put(Long.valueOf(1424L), Character.valueOf('ٰ'));
/*  611 */     keysym2UCSHash.put(Long.valueOf(1425L), Character.valueOf('۱'));
/*  612 */     keysym2UCSHash.put(Long.valueOf(1426L), Character.valueOf('۲'));
/*  613 */     keysym2UCSHash.put(Long.valueOf(1427L), Character.valueOf('۳'));
/*  614 */     keysym2UCSHash.put(Long.valueOf(1428L), Character.valueOf('۴'));
/*  615 */     keysym2UCSHash.put(Long.valueOf(1429L), Character.valueOf('۵'));
/*  616 */     keysym2UCSHash.put(Long.valueOf(1430L), Character.valueOf('۶'));
/*  617 */     keysym2UCSHash.put(Long.valueOf(1431L), Character.valueOf('۷'));
/*  618 */     keysym2UCSHash.put(Long.valueOf(1432L), Character.valueOf('۸'));
/*  619 */     keysym2UCSHash.put(Long.valueOf(1433L), Character.valueOf('۹'));
/*  620 */     keysym2UCSHash.put(Long.valueOf(1445L), Character.valueOf('٪'));
/*  621 */     keysym2UCSHash.put(Long.valueOf(1446L), Character.valueOf('ٰ'));
/*  622 */     keysym2UCSHash.put(Long.valueOf(1447L), Character.valueOf('ٹ'));
/*  623 */     keysym2UCSHash.put(Long.valueOf(1448L), Character.valueOf('پ'));
/*  624 */     keysym2UCSHash.put(Long.valueOf(1449L), Character.valueOf('چ'));
/*  625 */     keysym2UCSHash.put(Long.valueOf(1450L), Character.valueOf('ڈ'));
/*  626 */     keysym2UCSHash.put(Long.valueOf(1451L), Character.valueOf('ڑ'));
/*  627 */     keysym2UCSHash.put(Long.valueOf(1452L), Character.valueOf('،'));
/*  628 */     keysym2UCSHash.put(Long.valueOf(1454L), Character.valueOf('۔'));
/*  629 */     keysym2UCSHash.put(Long.valueOf(1456L), Character.valueOf('٠'));
/*  630 */     keysym2UCSHash.put(Long.valueOf(1457L), Character.valueOf('١'));
/*  631 */     keysym2UCSHash.put(Long.valueOf(1458L), Character.valueOf('٢'));
/*  632 */     keysym2UCSHash.put(Long.valueOf(1459L), Character.valueOf('٣'));
/*  633 */     keysym2UCSHash.put(Long.valueOf(1460L), Character.valueOf('٤'));
/*  634 */     keysym2UCSHash.put(Long.valueOf(1461L), Character.valueOf('٥'));
/*  635 */     keysym2UCSHash.put(Long.valueOf(1462L), Character.valueOf('٦'));
/*  636 */     keysym2UCSHash.put(Long.valueOf(1463L), Character.valueOf('٧'));
/*  637 */     keysym2UCSHash.put(Long.valueOf(1464L), Character.valueOf('٨'));
/*  638 */     keysym2UCSHash.put(Long.valueOf(1465L), Character.valueOf('٩'));
/*  639 */     keysym2UCSHash.put(Long.valueOf(1467L), Character.valueOf('؛'));
/*  640 */     keysym2UCSHash.put(Long.valueOf(1471L), Character.valueOf('؟'));
/*  641 */     keysym2UCSHash.put(Long.valueOf(1473L), Character.valueOf('ء'));
/*  642 */     keysym2UCSHash.put(Long.valueOf(1474L), Character.valueOf('آ'));
/*  643 */     keysym2UCSHash.put(Long.valueOf(1475L), Character.valueOf('أ'));
/*  644 */     keysym2UCSHash.put(Long.valueOf(1476L), Character.valueOf('ؤ'));
/*  645 */     keysym2UCSHash.put(Long.valueOf(1477L), Character.valueOf('إ'));
/*  646 */     keysym2UCSHash.put(Long.valueOf(1478L), Character.valueOf('ئ'));
/*  647 */     keysym2UCSHash.put(Long.valueOf(1479L), Character.valueOf('ا'));
/*  648 */     keysym2UCSHash.put(Long.valueOf(1480L), Character.valueOf('ب'));
/*  649 */     keysym2UCSHash.put(Long.valueOf(1481L), Character.valueOf('ة'));
/*  650 */     keysym2UCSHash.put(Long.valueOf(1482L), Character.valueOf('ت'));
/*  651 */     keysym2UCSHash.put(Long.valueOf(1483L), Character.valueOf('ث'));
/*  652 */     keysym2UCSHash.put(Long.valueOf(1484L), Character.valueOf('ج'));
/*  653 */     keysym2UCSHash.put(Long.valueOf(1485L), Character.valueOf('ح'));
/*  654 */     keysym2UCSHash.put(Long.valueOf(1486L), Character.valueOf('خ'));
/*  655 */     keysym2UCSHash.put(Long.valueOf(1487L), Character.valueOf('د'));
/*  656 */     keysym2UCSHash.put(Long.valueOf(1488L), Character.valueOf('ذ'));
/*  657 */     keysym2UCSHash.put(Long.valueOf(1489L), Character.valueOf('ر'));
/*  658 */     keysym2UCSHash.put(Long.valueOf(1490L), Character.valueOf('ز'));
/*  659 */     keysym2UCSHash.put(Long.valueOf(1491L), Character.valueOf('س'));
/*  660 */     keysym2UCSHash.put(Long.valueOf(1492L), Character.valueOf('ش'));
/*  661 */     keysym2UCSHash.put(Long.valueOf(1493L), Character.valueOf('ص'));
/*  662 */     keysym2UCSHash.put(Long.valueOf(1494L), Character.valueOf('ض'));
/*  663 */     keysym2UCSHash.put(Long.valueOf(1495L), Character.valueOf('ط'));
/*  664 */     keysym2UCSHash.put(Long.valueOf(1496L), Character.valueOf('ظ'));
/*  665 */     keysym2UCSHash.put(Long.valueOf(1497L), Character.valueOf('ع'));
/*  666 */     keysym2UCSHash.put(Long.valueOf(1498L), Character.valueOf('غ'));
/*  667 */     keysym2UCSHash.put(Long.valueOf(1504L), Character.valueOf('ـ'));
/*  668 */     keysym2UCSHash.put(Long.valueOf(1505L), Character.valueOf('ف'));
/*  669 */     keysym2UCSHash.put(Long.valueOf(1506L), Character.valueOf('ق'));
/*  670 */     keysym2UCSHash.put(Long.valueOf(1507L), Character.valueOf('ك'));
/*  671 */     keysym2UCSHash.put(Long.valueOf(1508L), Character.valueOf('ل'));
/*  672 */     keysym2UCSHash.put(Long.valueOf(1509L), Character.valueOf('م'));
/*  673 */     keysym2UCSHash.put(Long.valueOf(1510L), Character.valueOf('ن'));
/*  674 */     keysym2UCSHash.put(Long.valueOf(1511L), Character.valueOf('ه'));
/*  675 */     keysym2UCSHash.put(Long.valueOf(1512L), Character.valueOf('و'));
/*  676 */     keysym2UCSHash.put(Long.valueOf(1513L), Character.valueOf('ى'));
/*  677 */     keysym2UCSHash.put(Long.valueOf(1514L), Character.valueOf('ي'));
/*  678 */     keysym2UCSHash.put(Long.valueOf(1515L), Character.valueOf('ً'));
/*  679 */     keysym2UCSHash.put(Long.valueOf(1516L), Character.valueOf('ٌ'));
/*  680 */     keysym2UCSHash.put(Long.valueOf(1517L), Character.valueOf('ٍ'));
/*  681 */     keysym2UCSHash.put(Long.valueOf(1518L), Character.valueOf('َ'));
/*  682 */     keysym2UCSHash.put(Long.valueOf(1519L), Character.valueOf('ُ'));
/*  683 */     keysym2UCSHash.put(Long.valueOf(1520L), Character.valueOf('ِ'));
/*  684 */     keysym2UCSHash.put(Long.valueOf(1521L), Character.valueOf('ّ'));
/*  685 */     keysym2UCSHash.put(Long.valueOf(1522L), Character.valueOf('ْ'));
/*  686 */     keysym2UCSHash.put(Long.valueOf(1523L), Character.valueOf('ٓ'));
/*  687 */     keysym2UCSHash.put(Long.valueOf(1524L), Character.valueOf('ٔ'));
/*  688 */     keysym2UCSHash.put(Long.valueOf(1525L), Character.valueOf('ٕ'));
/*  689 */     keysym2UCSHash.put(Long.valueOf(1526L), Character.valueOf('ژ'));
/*  690 */     keysym2UCSHash.put(Long.valueOf(1527L), Character.valueOf('ڤ'));
/*  691 */     keysym2UCSHash.put(Long.valueOf(1528L), Character.valueOf('ک'));
/*  692 */     keysym2UCSHash.put(Long.valueOf(1529L), Character.valueOf('گ'));
/*  693 */     keysym2UCSHash.put(Long.valueOf(1530L), Character.valueOf('ں'));
/*  694 */     keysym2UCSHash.put(Long.valueOf(1531L), Character.valueOf('ھ'));
/*  695 */     keysym2UCSHash.put(Long.valueOf(1532L), Character.valueOf('ی'));
/*  696 */     keysym2UCSHash.put(Long.valueOf(1533L), Character.valueOf('ے'));
/*  697 */     keysym2UCSHash.put(Long.valueOf(1534L), Character.valueOf('ہ'));
/*  698 */     keysym2UCSHash.put(Long.valueOf(1664L), Character.valueOf('Ғ'));
/*  699 */     keysym2UCSHash.put(Long.valueOf(1680L), Character.valueOf('ғ'));
/*  700 */     keysym2UCSHash.put(Long.valueOf(1665L), Character.valueOf('Җ'));
/*  701 */     keysym2UCSHash.put(Long.valueOf(1681L), Character.valueOf('җ'));
/*  702 */     keysym2UCSHash.put(Long.valueOf(1666L), Character.valueOf('Қ'));
/*  703 */     keysym2UCSHash.put(Long.valueOf(1682L), Character.valueOf('қ'));
/*  704 */     keysym2UCSHash.put(Long.valueOf(1667L), Character.valueOf('Ҝ'));
/*  705 */     keysym2UCSHash.put(Long.valueOf(1683L), Character.valueOf('ҝ'));
/*  706 */     keysym2UCSHash.put(Long.valueOf(1668L), Character.valueOf('Ң'));
/*  707 */     keysym2UCSHash.put(Long.valueOf(1684L), Character.valueOf('ң'));
/*  708 */     keysym2UCSHash.put(Long.valueOf(1669L), Character.valueOf('Ү'));
/*  709 */     keysym2UCSHash.put(Long.valueOf(1685L), Character.valueOf('ү'));
/*  710 */     keysym2UCSHash.put(Long.valueOf(1670L), Character.valueOf('Ұ'));
/*  711 */     keysym2UCSHash.put(Long.valueOf(1686L), Character.valueOf('ұ'));
/*  712 */     keysym2UCSHash.put(Long.valueOf(1671L), Character.valueOf('Ҳ'));
/*  713 */     keysym2UCSHash.put(Long.valueOf(1687L), Character.valueOf('ҳ'));
/*  714 */     keysym2UCSHash.put(Long.valueOf(1672L), Character.valueOf('Ҷ'));
/*  715 */     keysym2UCSHash.put(Long.valueOf(1688L), Character.valueOf('ҷ'));
/*  716 */     keysym2UCSHash.put(Long.valueOf(1673L), Character.valueOf('Ҹ'));
/*  717 */     keysym2UCSHash.put(Long.valueOf(1689L), Character.valueOf('ҹ'));
/*  718 */     keysym2UCSHash.put(Long.valueOf(1674L), Character.valueOf('Һ'));
/*  719 */     keysym2UCSHash.put(Long.valueOf(1690L), Character.valueOf('һ'));
/*  720 */     keysym2UCSHash.put(Long.valueOf(1676L), Character.valueOf('Ә'));
/*  721 */     keysym2UCSHash.put(Long.valueOf(1692L), Character.valueOf('ә'));
/*  722 */     keysym2UCSHash.put(Long.valueOf(1677L), Character.valueOf('Ӣ'));
/*  723 */     keysym2UCSHash.put(Long.valueOf(1693L), Character.valueOf('ӣ'));
/*  724 */     keysym2UCSHash.put(Long.valueOf(1678L), Character.valueOf('Ө'));
/*  725 */     keysym2UCSHash.put(Long.valueOf(1694L), Character.valueOf('ө'));
/*  726 */     keysym2UCSHash.put(Long.valueOf(1679L), Character.valueOf('Ӯ'));
/*  727 */     keysym2UCSHash.put(Long.valueOf(1695L), Character.valueOf('ӯ'));
/*  728 */     keysym2UCSHash.put(Long.valueOf(1697L), Character.valueOf('ђ'));
/*  729 */     keysym2UCSHash.put(Long.valueOf(1698L), Character.valueOf('ѓ'));
/*  730 */     keysym2UCSHash.put(Long.valueOf(1699L), Character.valueOf('ё'));
/*  731 */     keysym2UCSHash.put(Long.valueOf(1700L), Character.valueOf('є'));
/*  732 */     keysym2UCSHash.put(Long.valueOf(1701L), Character.valueOf('ѕ'));
/*  733 */     keysym2UCSHash.put(Long.valueOf(1702L), Character.valueOf('і'));
/*  734 */     keysym2UCSHash.put(Long.valueOf(1703L), Character.valueOf('ї'));
/*  735 */     keysym2UCSHash.put(Long.valueOf(1704L), Character.valueOf('ј'));
/*  736 */     keysym2UCSHash.put(Long.valueOf(1705L), Character.valueOf('љ'));
/*  737 */     keysym2UCSHash.put(Long.valueOf(1706L), Character.valueOf('њ'));
/*  738 */     keysym2UCSHash.put(Long.valueOf(1707L), Character.valueOf('ћ'));
/*  739 */     keysym2UCSHash.put(Long.valueOf(1708L), Character.valueOf('ќ'));
/*  740 */     keysym2UCSHash.put(Long.valueOf(1709L), Character.valueOf('ґ'));
/*  741 */     keysym2UCSHash.put(Long.valueOf(1710L), Character.valueOf('ў'));
/*  742 */     keysym2UCSHash.put(Long.valueOf(1711L), Character.valueOf('џ'));
/*  743 */     keysym2UCSHash.put(Long.valueOf(1712L), Character.valueOf('№'));
/*  744 */     keysym2UCSHash.put(Long.valueOf(1713L), Character.valueOf('Ђ'));
/*  745 */     keysym2UCSHash.put(Long.valueOf(1714L), Character.valueOf('Ѓ'));
/*  746 */     keysym2UCSHash.put(Long.valueOf(1715L), Character.valueOf('Ё'));
/*  747 */     keysym2UCSHash.put(Long.valueOf(1716L), Character.valueOf('Є'));
/*  748 */     keysym2UCSHash.put(Long.valueOf(1717L), Character.valueOf('Ѕ'));
/*  749 */     keysym2UCSHash.put(Long.valueOf(1718L), Character.valueOf('І'));
/*  750 */     keysym2UCSHash.put(Long.valueOf(1719L), Character.valueOf('Ї'));
/*  751 */     keysym2UCSHash.put(Long.valueOf(1720L), Character.valueOf('Ј'));
/*  752 */     keysym2UCSHash.put(Long.valueOf(1721L), Character.valueOf('Љ'));
/*  753 */     keysym2UCSHash.put(Long.valueOf(1722L), Character.valueOf('Њ'));
/*  754 */     keysym2UCSHash.put(Long.valueOf(1723L), Character.valueOf('Ћ'));
/*  755 */     keysym2UCSHash.put(Long.valueOf(1724L), Character.valueOf('Ќ'));
/*  756 */     keysym2UCSHash.put(Long.valueOf(1725L), Character.valueOf('Ґ'));
/*  757 */     keysym2UCSHash.put(Long.valueOf(1726L), Character.valueOf('Ў'));
/*  758 */     keysym2UCSHash.put(Long.valueOf(1727L), Character.valueOf('Џ'));
/*  759 */     keysym2UCSHash.put(Long.valueOf(1728L), Character.valueOf('ю'));
/*  760 */     keysym2UCSHash.put(Long.valueOf(1729L), Character.valueOf('а'));
/*  761 */     keysym2UCSHash.put(Long.valueOf(1730L), Character.valueOf('б'));
/*  762 */     keysym2UCSHash.put(Long.valueOf(1731L), Character.valueOf('ц'));
/*  763 */     keysym2UCSHash.put(Long.valueOf(1732L), Character.valueOf('д'));
/*  764 */     keysym2UCSHash.put(Long.valueOf(1733L), Character.valueOf('е'));
/*  765 */     keysym2UCSHash.put(Long.valueOf(1734L), Character.valueOf('ф'));
/*  766 */     keysym2UCSHash.put(Long.valueOf(1735L), Character.valueOf('г'));
/*  767 */     keysym2UCSHash.put(Long.valueOf(1736L), Character.valueOf('х'));
/*  768 */     keysym2UCSHash.put(Long.valueOf(1737L), Character.valueOf('и'));
/*  769 */     keysym2UCSHash.put(Long.valueOf(1738L), Character.valueOf('й'));
/*  770 */     keysym2UCSHash.put(Long.valueOf(1739L), Character.valueOf('к'));
/*  771 */     keysym2UCSHash.put(Long.valueOf(1740L), Character.valueOf('л'));
/*  772 */     keysym2UCSHash.put(Long.valueOf(1741L), Character.valueOf('м'));
/*  773 */     keysym2UCSHash.put(Long.valueOf(1742L), Character.valueOf('н'));
/*  774 */     keysym2UCSHash.put(Long.valueOf(1743L), Character.valueOf('о'));
/*  775 */     keysym2UCSHash.put(Long.valueOf(1744L), Character.valueOf('п'));
/*  776 */     keysym2UCSHash.put(Long.valueOf(1745L), Character.valueOf('я'));
/*  777 */     keysym2UCSHash.put(Long.valueOf(1746L), Character.valueOf('р'));
/*  778 */     keysym2UCSHash.put(Long.valueOf(1747L), Character.valueOf('с'));
/*  779 */     keysym2UCSHash.put(Long.valueOf(1748L), Character.valueOf('т'));
/*  780 */     keysym2UCSHash.put(Long.valueOf(1749L), Character.valueOf('у'));
/*  781 */     keysym2UCSHash.put(Long.valueOf(1750L), Character.valueOf('ж'));
/*  782 */     keysym2UCSHash.put(Long.valueOf(1751L), Character.valueOf('в'));
/*  783 */     keysym2UCSHash.put(Long.valueOf(1752L), Character.valueOf('ь'));
/*  784 */     keysym2UCSHash.put(Long.valueOf(1753L), Character.valueOf('ы'));
/*  785 */     keysym2UCSHash.put(Long.valueOf(1754L), Character.valueOf('з'));
/*  786 */     keysym2UCSHash.put(Long.valueOf(1755L), Character.valueOf('ш'));
/*  787 */     keysym2UCSHash.put(Long.valueOf(1756L), Character.valueOf('э'));
/*  788 */     keysym2UCSHash.put(Long.valueOf(1757L), Character.valueOf('щ'));
/*  789 */     keysym2UCSHash.put(Long.valueOf(1758L), Character.valueOf('ч'));
/*  790 */     keysym2UCSHash.put(Long.valueOf(1759L), Character.valueOf('ъ'));
/*  791 */     keysym2UCSHash.put(Long.valueOf(1760L), Character.valueOf('Ю'));
/*  792 */     keysym2UCSHash.put(Long.valueOf(1761L), Character.valueOf('А'));
/*  793 */     keysym2UCSHash.put(Long.valueOf(1762L), Character.valueOf('Б'));
/*  794 */     keysym2UCSHash.put(Long.valueOf(1763L), Character.valueOf('Ц'));
/*  795 */     keysym2UCSHash.put(Long.valueOf(1764L), Character.valueOf('Д'));
/*  796 */     keysym2UCSHash.put(Long.valueOf(1765L), Character.valueOf('Е'));
/*  797 */     keysym2UCSHash.put(Long.valueOf(1766L), Character.valueOf('Ф'));
/*  798 */     keysym2UCSHash.put(Long.valueOf(1767L), Character.valueOf('Г'));
/*  799 */     keysym2UCSHash.put(Long.valueOf(1768L), Character.valueOf('Х'));
/*  800 */     keysym2UCSHash.put(Long.valueOf(1769L), Character.valueOf('И'));
/*  801 */     keysym2UCSHash.put(Long.valueOf(1770L), Character.valueOf('Й'));
/*  802 */     keysym2UCSHash.put(Long.valueOf(1771L), Character.valueOf('К'));
/*  803 */     keysym2UCSHash.put(Long.valueOf(1772L), Character.valueOf('Л'));
/*  804 */     keysym2UCSHash.put(Long.valueOf(1773L), Character.valueOf('М'));
/*  805 */     keysym2UCSHash.put(Long.valueOf(1774L), Character.valueOf('Н'));
/*  806 */     keysym2UCSHash.put(Long.valueOf(1775L), Character.valueOf('О'));
/*  807 */     keysym2UCSHash.put(Long.valueOf(1776L), Character.valueOf('П'));
/*  808 */     keysym2UCSHash.put(Long.valueOf(1777L), Character.valueOf('Я'));
/*  809 */     keysym2UCSHash.put(Long.valueOf(1778L), Character.valueOf('Р'));
/*  810 */     keysym2UCSHash.put(Long.valueOf(1779L), Character.valueOf('С'));
/*  811 */     keysym2UCSHash.put(Long.valueOf(1780L), Character.valueOf('Т'));
/*  812 */     keysym2UCSHash.put(Long.valueOf(1781L), Character.valueOf('У'));
/*  813 */     keysym2UCSHash.put(Long.valueOf(1782L), Character.valueOf('Ж'));
/*  814 */     keysym2UCSHash.put(Long.valueOf(1783L), Character.valueOf('В'));
/*  815 */     keysym2UCSHash.put(Long.valueOf(1784L), Character.valueOf('Ь'));
/*  816 */     keysym2UCSHash.put(Long.valueOf(1785L), Character.valueOf('Ы'));
/*  817 */     keysym2UCSHash.put(Long.valueOf(1786L), Character.valueOf('З'));
/*  818 */     keysym2UCSHash.put(Long.valueOf(1787L), Character.valueOf('Ш'));
/*  819 */     keysym2UCSHash.put(Long.valueOf(1788L), Character.valueOf('Э'));
/*  820 */     keysym2UCSHash.put(Long.valueOf(1789L), Character.valueOf('Щ'));
/*  821 */     keysym2UCSHash.put(Long.valueOf(1790L), Character.valueOf('Ч'));
/*  822 */     keysym2UCSHash.put(Long.valueOf(1791L), Character.valueOf('Ъ'));
/*  823 */     keysym2UCSHash.put(Long.valueOf(1953L), Character.valueOf('Ά'));
/*  824 */     keysym2UCSHash.put(Long.valueOf(1954L), Character.valueOf('Έ'));
/*  825 */     keysym2UCSHash.put(Long.valueOf(1955L), Character.valueOf('Ή'));
/*  826 */     keysym2UCSHash.put(Long.valueOf(1956L), Character.valueOf('Ί'));
/*  827 */     keysym2UCSHash.put(Long.valueOf(1957L), Character.valueOf('Ϊ'));
/*  828 */     keysym2UCSHash.put(Long.valueOf(1959L), Character.valueOf('Ό'));
/*  829 */     keysym2UCSHash.put(Long.valueOf(1960L), Character.valueOf('Ύ'));
/*  830 */     keysym2UCSHash.put(Long.valueOf(1961L), Character.valueOf('Ϋ'));
/*  831 */     keysym2UCSHash.put(Long.valueOf(1963L), Character.valueOf('Ώ'));
/*  832 */     keysym2UCSHash.put(Long.valueOf(1966L), Character.valueOf('΅'));
/*  833 */     keysym2UCSHash.put(Long.valueOf(1967L), Character.valueOf('―'));
/*  834 */     keysym2UCSHash.put(Long.valueOf(1969L), Character.valueOf('ά'));
/*  835 */     keysym2UCSHash.put(Long.valueOf(1970L), Character.valueOf('έ'));
/*  836 */     keysym2UCSHash.put(Long.valueOf(1971L), Character.valueOf('ή'));
/*  837 */     keysym2UCSHash.put(Long.valueOf(1972L), Character.valueOf('ί'));
/*  838 */     keysym2UCSHash.put(Long.valueOf(1973L), Character.valueOf('ϊ'));
/*  839 */     keysym2UCSHash.put(Long.valueOf(1974L), Character.valueOf('ΐ'));
/*  840 */     keysym2UCSHash.put(Long.valueOf(1975L), Character.valueOf('ό'));
/*  841 */     keysym2UCSHash.put(Long.valueOf(1976L), Character.valueOf('ύ'));
/*  842 */     keysym2UCSHash.put(Long.valueOf(1977L), Character.valueOf('ϋ'));
/*  843 */     keysym2UCSHash.put(Long.valueOf(1978L), Character.valueOf('ΰ'));
/*  844 */     keysym2UCSHash.put(Long.valueOf(1979L), Character.valueOf('ώ'));
/*  845 */     keysym2UCSHash.put(Long.valueOf(1985L), Character.valueOf('Α'));
/*  846 */     keysym2UCSHash.put(Long.valueOf(1986L), Character.valueOf('Β'));
/*  847 */     keysym2UCSHash.put(Long.valueOf(1987L), Character.valueOf('Γ'));
/*  848 */     keysym2UCSHash.put(Long.valueOf(1988L), Character.valueOf('Δ'));
/*  849 */     keysym2UCSHash.put(Long.valueOf(1989L), Character.valueOf('Ε'));
/*  850 */     keysym2UCSHash.put(Long.valueOf(1990L), Character.valueOf('Ζ'));
/*  851 */     keysym2UCSHash.put(Long.valueOf(1991L), Character.valueOf('Η'));
/*  852 */     keysym2UCSHash.put(Long.valueOf(1992L), Character.valueOf('Θ'));
/*  853 */     keysym2UCSHash.put(Long.valueOf(1993L), Character.valueOf('Ι'));
/*  854 */     keysym2UCSHash.put(Long.valueOf(1994L), Character.valueOf('Κ'));
/*  855 */     keysym2UCSHash.put(Long.valueOf(1995L), Character.valueOf('Λ'));
/*  856 */     keysym2UCSHash.put(Long.valueOf(1996L), Character.valueOf('Μ'));
/*  857 */     keysym2UCSHash.put(Long.valueOf(1997L), Character.valueOf('Ν'));
/*  858 */     keysym2UCSHash.put(Long.valueOf(1998L), Character.valueOf('Ξ'));
/*  859 */     keysym2UCSHash.put(Long.valueOf(1999L), Character.valueOf('Ο'));
/*  860 */     keysym2UCSHash.put(Long.valueOf(2000L), Character.valueOf('Π'));
/*  861 */     keysym2UCSHash.put(Long.valueOf(2001L), Character.valueOf('Ρ'));
/*  862 */     keysym2UCSHash.put(Long.valueOf(2002L), Character.valueOf('Σ'));
/*  863 */     keysym2UCSHash.put(Long.valueOf(2004L), Character.valueOf('Τ'));
/*  864 */     keysym2UCSHash.put(Long.valueOf(2005L), Character.valueOf('Υ'));
/*  865 */     keysym2UCSHash.put(Long.valueOf(2006L), Character.valueOf('Φ'));
/*  866 */     keysym2UCSHash.put(Long.valueOf(2007L), Character.valueOf('Χ'));
/*  867 */     keysym2UCSHash.put(Long.valueOf(2008L), Character.valueOf('Ψ'));
/*  868 */     keysym2UCSHash.put(Long.valueOf(2009L), Character.valueOf('Ω'));
/*  869 */     keysym2UCSHash.put(Long.valueOf(2017L), Character.valueOf('α'));
/*  870 */     keysym2UCSHash.put(Long.valueOf(2018L), Character.valueOf('β'));
/*  871 */     keysym2UCSHash.put(Long.valueOf(2019L), Character.valueOf('γ'));
/*  872 */     keysym2UCSHash.put(Long.valueOf(2020L), Character.valueOf('δ'));
/*  873 */     keysym2UCSHash.put(Long.valueOf(2021L), Character.valueOf('ε'));
/*  874 */     keysym2UCSHash.put(Long.valueOf(2022L), Character.valueOf('ζ'));
/*  875 */     keysym2UCSHash.put(Long.valueOf(2023L), Character.valueOf('η'));
/*  876 */     keysym2UCSHash.put(Long.valueOf(2024L), Character.valueOf('θ'));
/*  877 */     keysym2UCSHash.put(Long.valueOf(2025L), Character.valueOf('ι'));
/*  878 */     keysym2UCSHash.put(Long.valueOf(2026L), Character.valueOf('κ'));
/*  879 */     keysym2UCSHash.put(Long.valueOf(2027L), Character.valueOf('λ'));
/*  880 */     keysym2UCSHash.put(Long.valueOf(2028L), Character.valueOf('μ'));
/*  881 */     keysym2UCSHash.put(Long.valueOf(2029L), Character.valueOf('ν'));
/*  882 */     keysym2UCSHash.put(Long.valueOf(2030L), Character.valueOf('ξ'));
/*  883 */     keysym2UCSHash.put(Long.valueOf(2031L), Character.valueOf('ο'));
/*  884 */     keysym2UCSHash.put(Long.valueOf(2032L), Character.valueOf('π'));
/*  885 */     keysym2UCSHash.put(Long.valueOf(2033L), Character.valueOf('ρ'));
/*  886 */     keysym2UCSHash.put(Long.valueOf(2034L), Character.valueOf('σ'));
/*  887 */     keysym2UCSHash.put(Long.valueOf(2035L), Character.valueOf('ς'));
/*  888 */     keysym2UCSHash.put(Long.valueOf(2036L), Character.valueOf('τ'));
/*  889 */     keysym2UCSHash.put(Long.valueOf(2037L), Character.valueOf('υ'));
/*  890 */     keysym2UCSHash.put(Long.valueOf(2038L), Character.valueOf('φ'));
/*  891 */     keysym2UCSHash.put(Long.valueOf(2039L), Character.valueOf('χ'));
/*  892 */     keysym2UCSHash.put(Long.valueOf(2040L), Character.valueOf('ψ'));
/*  893 */     keysym2UCSHash.put(Long.valueOf(2041L), Character.valueOf('ω'));
/*  894 */     keysym2UCSHash.put(Long.valueOf(2209L), Character.valueOf('⎷'));
/*  895 */     keysym2UCSHash.put(Long.valueOf(2210L), Character.valueOf('┌'));
/*  896 */     keysym2UCSHash.put(Long.valueOf(2211L), Character.valueOf('─'));
/*  897 */     keysym2UCSHash.put(Long.valueOf(2212L), Character.valueOf('⌠'));
/*  898 */     keysym2UCSHash.put(Long.valueOf(2213L), Character.valueOf('⌡'));
/*  899 */     keysym2UCSHash.put(Long.valueOf(2214L), Character.valueOf('│'));
/*  900 */     keysym2UCSHash.put(Long.valueOf(2215L), Character.valueOf('⎡'));
/*  901 */     keysym2UCSHash.put(Long.valueOf(2216L), Character.valueOf('⎣'));
/*  902 */     keysym2UCSHash.put(Long.valueOf(2217L), Character.valueOf('⎤'));
/*  903 */     keysym2UCSHash.put(Long.valueOf(2218L), Character.valueOf('⎦'));
/*  904 */     keysym2UCSHash.put(Long.valueOf(2219L), Character.valueOf('⎛'));
/*  905 */     keysym2UCSHash.put(Long.valueOf(2220L), Character.valueOf('⎝'));
/*  906 */     keysym2UCSHash.put(Long.valueOf(2221L), Character.valueOf('⎞'));
/*  907 */     keysym2UCSHash.put(Long.valueOf(2222L), Character.valueOf('⎠'));
/*  908 */     keysym2UCSHash.put(Long.valueOf(2223L), Character.valueOf('⎨'));
/*  909 */     keysym2UCSHash.put(Long.valueOf(2224L), Character.valueOf('⎬'));
/*  910 */     keysym2UCSHash.put(Long.valueOf(2236L), Character.valueOf('≤'));
/*  911 */     keysym2UCSHash.put(Long.valueOf(2237L), Character.valueOf('≠'));
/*  912 */     keysym2UCSHash.put(Long.valueOf(2238L), Character.valueOf('≥'));
/*  913 */     keysym2UCSHash.put(Long.valueOf(2239L), Character.valueOf('∫'));
/*  914 */     keysym2UCSHash.put(Long.valueOf(2240L), Character.valueOf('∴'));
/*  915 */     keysym2UCSHash.put(Long.valueOf(2241L), Character.valueOf('∝'));
/*  916 */     keysym2UCSHash.put(Long.valueOf(2242L), Character.valueOf('∞'));
/*  917 */     keysym2UCSHash.put(Long.valueOf(2245L), Character.valueOf('∇'));
/*  918 */     keysym2UCSHash.put(Long.valueOf(2248L), Character.valueOf('∼'));
/*  919 */     keysym2UCSHash.put(Long.valueOf(2249L), Character.valueOf('≃'));
/*  920 */     keysym2UCSHash.put(Long.valueOf(2253L), Character.valueOf('℄'));
/*  921 */     keysym2UCSHash.put(Long.valueOf(2254L), Character.valueOf('⇒'));
/*  922 */     keysym2UCSHash.put(Long.valueOf(2255L), Character.valueOf('≡'));
/*  923 */     keysym2UCSHash.put(Long.valueOf(2262L), Character.valueOf('√'));
/*  924 */     keysym2UCSHash.put(Long.valueOf(2266L), Character.valueOf('⊂'));
/*  925 */     keysym2UCSHash.put(Long.valueOf(2267L), Character.valueOf('⊃'));
/*  926 */     keysym2UCSHash.put(Long.valueOf(2268L), Character.valueOf('∩'));
/*  927 */     keysym2UCSHash.put(Long.valueOf(2269L), Character.valueOf('∪'));
/*  928 */     keysym2UCSHash.put(Long.valueOf(2270L), Character.valueOf('∧'));
/*  929 */     keysym2UCSHash.put(Long.valueOf(2271L), Character.valueOf('∨'));
/*  930 */     keysym2UCSHash.put(Long.valueOf(2287L), Character.valueOf('∂'));
/*  931 */     keysym2UCSHash.put(Long.valueOf(2294L), Character.valueOf('ƒ'));
/*  932 */     keysym2UCSHash.put(Long.valueOf(2299L), Character.valueOf('←'));
/*  933 */     keysym2UCSHash.put(Long.valueOf(2300L), Character.valueOf('↑'));
/*  934 */     keysym2UCSHash.put(Long.valueOf(2301L), Character.valueOf('→'));
/*  935 */     keysym2UCSHash.put(Long.valueOf(2302L), Character.valueOf('↓'));
/*  936 */     keysym2UCSHash.put(Long.valueOf(2528L), Character.valueOf('◆'));
/*  937 */     keysym2UCSHash.put(Long.valueOf(2529L), Character.valueOf('▒'));
/*  938 */     keysym2UCSHash.put(Long.valueOf(2530L), Character.valueOf('␉'));
/*  939 */     keysym2UCSHash.put(Long.valueOf(2531L), Character.valueOf('␌'));
/*  940 */     keysym2UCSHash.put(Long.valueOf(2532L), Character.valueOf('␍'));
/*  941 */     keysym2UCSHash.put(Long.valueOf(2533L), Character.valueOf('␊'));
/*  942 */     keysym2UCSHash.put(Long.valueOf(2536L), Character.valueOf('␤'));
/*  943 */     keysym2UCSHash.put(Long.valueOf(2537L), Character.valueOf('␋'));
/*  944 */     keysym2UCSHash.put(Long.valueOf(2538L), Character.valueOf('┘'));
/*  945 */     keysym2UCSHash.put(Long.valueOf(2539L), Character.valueOf('┐'));
/*  946 */     keysym2UCSHash.put(Long.valueOf(2540L), Character.valueOf('┌'));
/*  947 */     keysym2UCSHash.put(Long.valueOf(2541L), Character.valueOf('└'));
/*  948 */     keysym2UCSHash.put(Long.valueOf(2542L), Character.valueOf('┼'));
/*  949 */     keysym2UCSHash.put(Long.valueOf(2543L), Character.valueOf('⎺'));
/*  950 */     keysym2UCSHash.put(Long.valueOf(2544L), Character.valueOf('⎻'));
/*  951 */     keysym2UCSHash.put(Long.valueOf(2545L), Character.valueOf('─'));
/*  952 */     keysym2UCSHash.put(Long.valueOf(2546L), Character.valueOf('⎼'));
/*  953 */     keysym2UCSHash.put(Long.valueOf(2547L), Character.valueOf('⎽'));
/*  954 */     keysym2UCSHash.put(Long.valueOf(2548L), Character.valueOf('├'));
/*  955 */     keysym2UCSHash.put(Long.valueOf(2549L), Character.valueOf('┤'));
/*  956 */     keysym2UCSHash.put(Long.valueOf(2550L), Character.valueOf('┴'));
/*  957 */     keysym2UCSHash.put(Long.valueOf(2551L), Character.valueOf('␬'));
/*  958 */     keysym2UCSHash.put(Long.valueOf(2552L), Character.valueOf('│'));
/*  959 */     keysym2UCSHash.put(Long.valueOf(2721L), Character.valueOf(' '));
/*  960 */     keysym2UCSHash.put(Long.valueOf(2722L), Character.valueOf(' '));
/*  961 */     keysym2UCSHash.put(Long.valueOf(2723L), Character.valueOf(' '));
/*  962 */     keysym2UCSHash.put(Long.valueOf(2724L), Character.valueOf(' '));
/*  963 */     keysym2UCSHash.put(Long.valueOf(2725L), Character.valueOf(' '));
/*  964 */     keysym2UCSHash.put(Long.valueOf(2726L), Character.valueOf(' '));
/*  965 */     keysym2UCSHash.put(Long.valueOf(2727L), Character.valueOf(' '));
/*  966 */     keysym2UCSHash.put(Long.valueOf(2728L), Character.valueOf(' '));
/*  967 */     keysym2UCSHash.put(Long.valueOf(2729L), Character.valueOf('—'));
/*  968 */     keysym2UCSHash.put(Long.valueOf(2730L), Character.valueOf('–'));
/*  969 */     keysym2UCSHash.put(Long.valueOf(2732L), Character.valueOf('␣'));
/*  970 */     keysym2UCSHash.put(Long.valueOf(2734L), Character.valueOf('…'));
/*  971 */     keysym2UCSHash.put(Long.valueOf(2735L), Character.valueOf('‥'));
/*  972 */     keysym2UCSHash.put(Long.valueOf(2736L), Character.valueOf('⅓'));
/*  973 */     keysym2UCSHash.put(Long.valueOf(2737L), Character.valueOf('⅔'));
/*  974 */     keysym2UCSHash.put(Long.valueOf(2738L), Character.valueOf('⅕'));
/*  975 */     keysym2UCSHash.put(Long.valueOf(2739L), Character.valueOf('⅖'));
/*  976 */     keysym2UCSHash.put(Long.valueOf(2740L), Character.valueOf('⅗'));
/*  977 */     keysym2UCSHash.put(Long.valueOf(2741L), Character.valueOf('⅘'));
/*  978 */     keysym2UCSHash.put(Long.valueOf(2742L), Character.valueOf('⅙'));
/*  979 */     keysym2UCSHash.put(Long.valueOf(2743L), Character.valueOf('⅚'));
/*  980 */     keysym2UCSHash.put(Long.valueOf(2744L), Character.valueOf('℅'));
/*  981 */     keysym2UCSHash.put(Long.valueOf(2747L), Character.valueOf('‒'));
/*  982 */     keysym2UCSHash.put(Long.valueOf(2748L), Character.valueOf('⟨'));
/*  983 */     keysym2UCSHash.put(Long.valueOf(2749L), Character.valueOf('.'));
/*  984 */     keysym2UCSHash.put(Long.valueOf(2750L), Character.valueOf('⟩'));
/*  985 */     keysym2UCSHash.put(Long.valueOf(2755L), Character.valueOf('⅛'));
/*  986 */     keysym2UCSHash.put(Long.valueOf(2756L), Character.valueOf('⅜'));
/*  987 */     keysym2UCSHash.put(Long.valueOf(2757L), Character.valueOf('⅝'));
/*  988 */     keysym2UCSHash.put(Long.valueOf(2758L), Character.valueOf('⅞'));
/*  989 */     keysym2UCSHash.put(Long.valueOf(2761L), Character.valueOf('™'));
/*  990 */     keysym2UCSHash.put(Long.valueOf(2762L), Character.valueOf('☓'));
/*  991 */     keysym2UCSHash.put(Long.valueOf(2764L), Character.valueOf('◁'));
/*  992 */     keysym2UCSHash.put(Long.valueOf(2765L), Character.valueOf('▷'));
/*  993 */     keysym2UCSHash.put(Long.valueOf(2766L), Character.valueOf('○'));
/*  994 */     keysym2UCSHash.put(Long.valueOf(2767L), Character.valueOf('▯'));
/*  995 */     keysym2UCSHash.put(Long.valueOf(2768L), Character.valueOf('‘'));
/*  996 */     keysym2UCSHash.put(Long.valueOf(2769L), Character.valueOf('’'));
/*  997 */     keysym2UCSHash.put(Long.valueOf(2770L), Character.valueOf('“'));
/*  998 */     keysym2UCSHash.put(Long.valueOf(2771L), Character.valueOf('”'));
/*  999 */     keysym2UCSHash.put(Long.valueOf(2772L), Character.valueOf('℞'));
/* 1000 */     keysym2UCSHash.put(Long.valueOf(2774L), Character.valueOf('′'));
/* 1001 */     keysym2UCSHash.put(Long.valueOf(2775L), Character.valueOf('″'));
/* 1002 */     keysym2UCSHash.put(Long.valueOf(2777L), Character.valueOf('✝'));
/* 1003 */     keysym2UCSHash.put(Long.valueOf(2779L), Character.valueOf('▬'));
/* 1004 */     keysym2UCSHash.put(Long.valueOf(2780L), Character.valueOf('◀'));
/* 1005 */     keysym2UCSHash.put(Long.valueOf(2781L), Character.valueOf('▶'));
/* 1006 */     keysym2UCSHash.put(Long.valueOf(2782L), Character.valueOf('●'));
/* 1007 */     keysym2UCSHash.put(Long.valueOf(2783L), Character.valueOf('▮'));
/* 1008 */     keysym2UCSHash.put(Long.valueOf(2784L), Character.valueOf('◦'));
/* 1009 */     keysym2UCSHash.put(Long.valueOf(2785L), Character.valueOf('▫'));
/* 1010 */     keysym2UCSHash.put(Long.valueOf(2786L), Character.valueOf('▭'));
/* 1011 */     keysym2UCSHash.put(Long.valueOf(2787L), Character.valueOf('△'));
/* 1012 */     keysym2UCSHash.put(Long.valueOf(2788L), Character.valueOf('▽'));
/* 1013 */     keysym2UCSHash.put(Long.valueOf(2789L), Character.valueOf('☆'));
/* 1014 */     keysym2UCSHash.put(Long.valueOf(2790L), Character.valueOf('•'));
/* 1015 */     keysym2UCSHash.put(Long.valueOf(2791L), Character.valueOf('▪'));
/* 1016 */     keysym2UCSHash.put(Long.valueOf(2792L), Character.valueOf('▲'));
/* 1017 */     keysym2UCSHash.put(Long.valueOf(2793L), Character.valueOf('▼'));
/* 1018 */     keysym2UCSHash.put(Long.valueOf(2794L), Character.valueOf('☜'));
/* 1019 */     keysym2UCSHash.put(Long.valueOf(2795L), Character.valueOf('☞'));
/* 1020 */     keysym2UCSHash.put(Long.valueOf(2796L), Character.valueOf('♣'));
/* 1021 */     keysym2UCSHash.put(Long.valueOf(2797L), Character.valueOf('♦'));
/* 1022 */     keysym2UCSHash.put(Long.valueOf(2798L), Character.valueOf('♥'));
/* 1023 */     keysym2UCSHash.put(Long.valueOf(2800L), Character.valueOf('✠'));
/* 1024 */     keysym2UCSHash.put(Long.valueOf(2801L), Character.valueOf('†'));
/* 1025 */     keysym2UCSHash.put(Long.valueOf(2802L), Character.valueOf('‡'));
/* 1026 */     keysym2UCSHash.put(Long.valueOf(2803L), Character.valueOf('✓'));
/* 1027 */     keysym2UCSHash.put(Long.valueOf(2804L), Character.valueOf('✗'));
/* 1028 */     keysym2UCSHash.put(Long.valueOf(2805L), Character.valueOf('♯'));
/* 1029 */     keysym2UCSHash.put(Long.valueOf(2806L), Character.valueOf('♭'));
/* 1030 */     keysym2UCSHash.put(Long.valueOf(2807L), Character.valueOf('♂'));
/* 1031 */     keysym2UCSHash.put(Long.valueOf(2808L), Character.valueOf('♀'));
/* 1032 */     keysym2UCSHash.put(Long.valueOf(2809L), Character.valueOf('☎'));
/* 1033 */     keysym2UCSHash.put(Long.valueOf(2810L), Character.valueOf('⌕'));
/* 1034 */     keysym2UCSHash.put(Long.valueOf(2811L), Character.valueOf('℗'));
/* 1035 */     keysym2UCSHash.put(Long.valueOf(2812L), Character.valueOf('‸'));
/* 1036 */     keysym2UCSHash.put(Long.valueOf(2813L), Character.valueOf('‚'));
/* 1037 */     keysym2UCSHash.put(Long.valueOf(2814L), Character.valueOf('„'));
/* 1038 */     keysym2UCSHash.put(Long.valueOf(2979L), Character.valueOf('<'));
/* 1039 */     keysym2UCSHash.put(Long.valueOf(2982L), Character.valueOf('>'));
/* 1040 */     keysym2UCSHash.put(Long.valueOf(2984L), Character.valueOf('∨'));
/* 1041 */     keysym2UCSHash.put(Long.valueOf(2985L), Character.valueOf('∧'));
/* 1042 */     keysym2UCSHash.put(Long.valueOf(3008L), Character.valueOf('¯'));
/* 1043 */     keysym2UCSHash.put(Long.valueOf(3010L), Character.valueOf('⊥'));
/* 1044 */     keysym2UCSHash.put(Long.valueOf(3011L), Character.valueOf('∩'));
/* 1045 */     keysym2UCSHash.put(Long.valueOf(3012L), Character.valueOf('⌊'));
/* 1046 */     keysym2UCSHash.put(Long.valueOf(3014L), Character.valueOf('_'));
/* 1047 */     keysym2UCSHash.put(Long.valueOf(3018L), Character.valueOf('∘'));
/* 1048 */     keysym2UCSHash.put(Long.valueOf(3020L), Character.valueOf('⎕'));
/* 1049 */     keysym2UCSHash.put(Long.valueOf(3022L), Character.valueOf('⊤'));
/* 1050 */     keysym2UCSHash.put(Long.valueOf(3023L), Character.valueOf('○'));
/* 1051 */     keysym2UCSHash.put(Long.valueOf(3027L), Character.valueOf('⌈'));
/* 1052 */     keysym2UCSHash.put(Long.valueOf(3030L), Character.valueOf('∪'));
/* 1053 */     keysym2UCSHash.put(Long.valueOf(3032L), Character.valueOf('⊃'));
/* 1054 */     keysym2UCSHash.put(Long.valueOf(3034L), Character.valueOf('⊂'));
/* 1055 */     keysym2UCSHash.put(Long.valueOf(3036L), Character.valueOf('⊢'));
/* 1056 */     keysym2UCSHash.put(Long.valueOf(3068L), Character.valueOf('⊣'));
/* 1057 */     keysym2UCSHash.put(Long.valueOf(3295L), Character.valueOf('‗'));
/* 1058 */     keysym2UCSHash.put(Long.valueOf(3296L), Character.valueOf('א'));
/* 1059 */     keysym2UCSHash.put(Long.valueOf(3297L), Character.valueOf('ב'));
/* 1060 */     keysym2UCSHash.put(Long.valueOf(3298L), Character.valueOf('ג'));
/* 1061 */     keysym2UCSHash.put(Long.valueOf(3299L), Character.valueOf('ד'));
/* 1062 */     keysym2UCSHash.put(Long.valueOf(3300L), Character.valueOf('ה'));
/* 1063 */     keysym2UCSHash.put(Long.valueOf(3301L), Character.valueOf('ו'));
/* 1064 */     keysym2UCSHash.put(Long.valueOf(3302L), Character.valueOf('ז'));
/* 1065 */     keysym2UCSHash.put(Long.valueOf(3303L), Character.valueOf('ח'));
/* 1066 */     keysym2UCSHash.put(Long.valueOf(3304L), Character.valueOf('ט'));
/* 1067 */     keysym2UCSHash.put(Long.valueOf(3305L), Character.valueOf('י'));
/* 1068 */     keysym2UCSHash.put(Long.valueOf(3306L), Character.valueOf('ך'));
/* 1069 */     keysym2UCSHash.put(Long.valueOf(3307L), Character.valueOf('כ'));
/* 1070 */     keysym2UCSHash.put(Long.valueOf(3308L), Character.valueOf('ל'));
/* 1071 */     keysym2UCSHash.put(Long.valueOf(3309L), Character.valueOf('ם'));
/* 1072 */     keysym2UCSHash.put(Long.valueOf(3310L), Character.valueOf('מ'));
/* 1073 */     keysym2UCSHash.put(Long.valueOf(3311L), Character.valueOf('ן'));
/* 1074 */     keysym2UCSHash.put(Long.valueOf(3312L), Character.valueOf('נ'));
/* 1075 */     keysym2UCSHash.put(Long.valueOf(3313L), Character.valueOf('ס'));
/* 1076 */     keysym2UCSHash.put(Long.valueOf(3314L), Character.valueOf('ע'));
/* 1077 */     keysym2UCSHash.put(Long.valueOf(3315L), Character.valueOf('ף'));
/* 1078 */     keysym2UCSHash.put(Long.valueOf(3316L), Character.valueOf('פ'));
/* 1079 */     keysym2UCSHash.put(Long.valueOf(3317L), Character.valueOf('ץ'));
/* 1080 */     keysym2UCSHash.put(Long.valueOf(3318L), Character.valueOf('צ'));
/* 1081 */     keysym2UCSHash.put(Long.valueOf(3319L), Character.valueOf('ק'));
/* 1082 */     keysym2UCSHash.put(Long.valueOf(3320L), Character.valueOf('ר'));
/* 1083 */     keysym2UCSHash.put(Long.valueOf(3321L), Character.valueOf('ש'));
/* 1084 */     keysym2UCSHash.put(Long.valueOf(3322L), Character.valueOf('ת'));
/* 1085 */     keysym2UCSHash.put(Long.valueOf(3489L), Character.valueOf('ก'));
/* 1086 */     keysym2UCSHash.put(Long.valueOf(3490L), Character.valueOf('ข'));
/* 1087 */     keysym2UCSHash.put(Long.valueOf(3491L), Character.valueOf('ฃ'));
/* 1088 */     keysym2UCSHash.put(Long.valueOf(3492L), Character.valueOf('ค'));
/* 1089 */     keysym2UCSHash.put(Long.valueOf(3493L), Character.valueOf('ฅ'));
/* 1090 */     keysym2UCSHash.put(Long.valueOf(3494L), Character.valueOf('ฆ'));
/* 1091 */     keysym2UCSHash.put(Long.valueOf(3495L), Character.valueOf('ง'));
/* 1092 */     keysym2UCSHash.put(Long.valueOf(3496L), Character.valueOf('จ'));
/* 1093 */     keysym2UCSHash.put(Long.valueOf(3497L), Character.valueOf('ฉ'));
/* 1094 */     keysym2UCSHash.put(Long.valueOf(3498L), Character.valueOf('ช'));
/* 1095 */     keysym2UCSHash.put(Long.valueOf(3499L), Character.valueOf('ซ'));
/* 1096 */     keysym2UCSHash.put(Long.valueOf(3500L), Character.valueOf('ฌ'));
/* 1097 */     keysym2UCSHash.put(Long.valueOf(3501L), Character.valueOf('ญ'));
/* 1098 */     keysym2UCSHash.put(Long.valueOf(3502L), Character.valueOf('ฎ'));
/* 1099 */     keysym2UCSHash.put(Long.valueOf(3503L), Character.valueOf('ฏ'));
/* 1100 */     keysym2UCSHash.put(Long.valueOf(3504L), Character.valueOf('ฐ'));
/* 1101 */     keysym2UCSHash.put(Long.valueOf(3505L), Character.valueOf('ฑ'));
/* 1102 */     keysym2UCSHash.put(Long.valueOf(3506L), Character.valueOf('ฒ'));
/* 1103 */     keysym2UCSHash.put(Long.valueOf(3507L), Character.valueOf('ณ'));
/* 1104 */     keysym2UCSHash.put(Long.valueOf(3508L), Character.valueOf('ด'));
/* 1105 */     keysym2UCSHash.put(Long.valueOf(3509L), Character.valueOf('ต'));
/* 1106 */     keysym2UCSHash.put(Long.valueOf(3510L), Character.valueOf('ถ'));
/* 1107 */     keysym2UCSHash.put(Long.valueOf(3511L), Character.valueOf('ท'));
/* 1108 */     keysym2UCSHash.put(Long.valueOf(3512L), Character.valueOf('ธ'));
/* 1109 */     keysym2UCSHash.put(Long.valueOf(3513L), Character.valueOf('น'));
/* 1110 */     keysym2UCSHash.put(Long.valueOf(3514L), Character.valueOf('บ'));
/* 1111 */     keysym2UCSHash.put(Long.valueOf(3515L), Character.valueOf('ป'));
/* 1112 */     keysym2UCSHash.put(Long.valueOf(3516L), Character.valueOf('ผ'));
/* 1113 */     keysym2UCSHash.put(Long.valueOf(3517L), Character.valueOf('ฝ'));
/* 1114 */     keysym2UCSHash.put(Long.valueOf(3518L), Character.valueOf('พ'));
/* 1115 */     keysym2UCSHash.put(Long.valueOf(3519L), Character.valueOf('ฟ'));
/* 1116 */     keysym2UCSHash.put(Long.valueOf(3520L), Character.valueOf('ภ'));
/* 1117 */     keysym2UCSHash.put(Long.valueOf(3521L), Character.valueOf('ม'));
/* 1118 */     keysym2UCSHash.put(Long.valueOf(3522L), Character.valueOf('ย'));
/* 1119 */     keysym2UCSHash.put(Long.valueOf(3523L), Character.valueOf('ร'));
/* 1120 */     keysym2UCSHash.put(Long.valueOf(3524L), Character.valueOf('ฤ'));
/* 1121 */     keysym2UCSHash.put(Long.valueOf(3525L), Character.valueOf('ล'));
/* 1122 */     keysym2UCSHash.put(Long.valueOf(3526L), Character.valueOf('ฦ'));
/* 1123 */     keysym2UCSHash.put(Long.valueOf(3527L), Character.valueOf('ว'));
/* 1124 */     keysym2UCSHash.put(Long.valueOf(3528L), Character.valueOf('ศ'));
/* 1125 */     keysym2UCSHash.put(Long.valueOf(3529L), Character.valueOf('ษ'));
/* 1126 */     keysym2UCSHash.put(Long.valueOf(3530L), Character.valueOf('ส'));
/* 1127 */     keysym2UCSHash.put(Long.valueOf(3531L), Character.valueOf('ห'));
/* 1128 */     keysym2UCSHash.put(Long.valueOf(3532L), Character.valueOf('ฬ'));
/* 1129 */     keysym2UCSHash.put(Long.valueOf(3533L), Character.valueOf('อ'));
/* 1130 */     keysym2UCSHash.put(Long.valueOf(3534L), Character.valueOf('ฮ'));
/* 1131 */     keysym2UCSHash.put(Long.valueOf(3535L), Character.valueOf('ฯ'));
/* 1132 */     keysym2UCSHash.put(Long.valueOf(3536L), Character.valueOf('ะ'));
/* 1133 */     keysym2UCSHash.put(Long.valueOf(3537L), Character.valueOf('ั'));
/* 1134 */     keysym2UCSHash.put(Long.valueOf(3538L), Character.valueOf('า'));
/* 1135 */     keysym2UCSHash.put(Long.valueOf(3539L), Character.valueOf('ำ'));
/* 1136 */     keysym2UCSHash.put(Long.valueOf(3540L), Character.valueOf('ิ'));
/* 1137 */     keysym2UCSHash.put(Long.valueOf(3541L), Character.valueOf('ี'));
/* 1138 */     keysym2UCSHash.put(Long.valueOf(3542L), Character.valueOf('ึ'));
/* 1139 */     keysym2UCSHash.put(Long.valueOf(3543L), Character.valueOf('ื'));
/* 1140 */     keysym2UCSHash.put(Long.valueOf(3544L), Character.valueOf('ุ'));
/* 1141 */     keysym2UCSHash.put(Long.valueOf(3545L), Character.valueOf('ู'));
/* 1142 */     keysym2UCSHash.put(Long.valueOf(3546L), Character.valueOf('ฺ'));
/* 1143 */     keysym2UCSHash.put(Long.valueOf(3551L), Character.valueOf('฿'));
/* 1144 */     keysym2UCSHash.put(Long.valueOf(3552L), Character.valueOf('เ'));
/* 1145 */     keysym2UCSHash.put(Long.valueOf(3553L), Character.valueOf('แ'));
/* 1146 */     keysym2UCSHash.put(Long.valueOf(3554L), Character.valueOf('โ'));
/* 1147 */     keysym2UCSHash.put(Long.valueOf(3555L), Character.valueOf('ใ'));
/* 1148 */     keysym2UCSHash.put(Long.valueOf(3556L), Character.valueOf('ไ'));
/* 1149 */     keysym2UCSHash.put(Long.valueOf(3557L), Character.valueOf('ๅ'));
/* 1150 */     keysym2UCSHash.put(Long.valueOf(3558L), Character.valueOf('ๆ'));
/* 1151 */     keysym2UCSHash.put(Long.valueOf(3559L), Character.valueOf('็'));
/* 1152 */     keysym2UCSHash.put(Long.valueOf(3560L), Character.valueOf('่'));
/* 1153 */     keysym2UCSHash.put(Long.valueOf(3561L), Character.valueOf('้'));
/* 1154 */     keysym2UCSHash.put(Long.valueOf(3562L), Character.valueOf('๊'));
/* 1155 */     keysym2UCSHash.put(Long.valueOf(3563L), Character.valueOf('๋'));
/* 1156 */     keysym2UCSHash.put(Long.valueOf(3564L), Character.valueOf('์'));
/* 1157 */     keysym2UCSHash.put(Long.valueOf(3565L), Character.valueOf('ํ'));
/* 1158 */     keysym2UCSHash.put(Long.valueOf(3568L), Character.valueOf('๐'));
/* 1159 */     keysym2UCSHash.put(Long.valueOf(3569L), Character.valueOf('๑'));
/* 1160 */     keysym2UCSHash.put(Long.valueOf(3570L), Character.valueOf('๒'));
/* 1161 */     keysym2UCSHash.put(Long.valueOf(3571L), Character.valueOf('๓'));
/* 1162 */     keysym2UCSHash.put(Long.valueOf(3572L), Character.valueOf('๔'));
/* 1163 */     keysym2UCSHash.put(Long.valueOf(3573L), Character.valueOf('๕'));
/* 1164 */     keysym2UCSHash.put(Long.valueOf(3574L), Character.valueOf('๖'));
/* 1165 */     keysym2UCSHash.put(Long.valueOf(3575L), Character.valueOf('๗'));
/* 1166 */     keysym2UCSHash.put(Long.valueOf(3576L), Character.valueOf('๘'));
/* 1167 */     keysym2UCSHash.put(Long.valueOf(3577L), Character.valueOf('๙'));
/* 1168 */     keysym2UCSHash.put(Long.valueOf(3745L), Character.valueOf('ㄱ'));
/* 1169 */     keysym2UCSHash.put(Long.valueOf(3746L), Character.valueOf('ㄲ'));
/* 1170 */     keysym2UCSHash.put(Long.valueOf(3747L), Character.valueOf('ㄳ'));
/* 1171 */     keysym2UCSHash.put(Long.valueOf(3748L), Character.valueOf('ㄴ'));
/* 1172 */     keysym2UCSHash.put(Long.valueOf(3749L), Character.valueOf('ㄵ'));
/* 1173 */     keysym2UCSHash.put(Long.valueOf(3750L), Character.valueOf('ㄶ'));
/* 1174 */     keysym2UCSHash.put(Long.valueOf(3751L), Character.valueOf('ㄷ'));
/* 1175 */     keysym2UCSHash.put(Long.valueOf(3752L), Character.valueOf('ㄸ'));
/* 1176 */     keysym2UCSHash.put(Long.valueOf(3753L), Character.valueOf('ㄹ'));
/* 1177 */     keysym2UCSHash.put(Long.valueOf(3754L), Character.valueOf('ㄺ'));
/* 1178 */     keysym2UCSHash.put(Long.valueOf(3755L), Character.valueOf('ㄻ'));
/* 1179 */     keysym2UCSHash.put(Long.valueOf(3756L), Character.valueOf('ㄼ'));
/* 1180 */     keysym2UCSHash.put(Long.valueOf(3757L), Character.valueOf('ㄽ'));
/* 1181 */     keysym2UCSHash.put(Long.valueOf(3758L), Character.valueOf('ㄾ'));
/* 1182 */     keysym2UCSHash.put(Long.valueOf(3759L), Character.valueOf('ㄿ'));
/* 1183 */     keysym2UCSHash.put(Long.valueOf(3760L), Character.valueOf('ㅀ'));
/* 1184 */     keysym2UCSHash.put(Long.valueOf(3761L), Character.valueOf('ㅁ'));
/* 1185 */     keysym2UCSHash.put(Long.valueOf(3762L), Character.valueOf('ㅂ'));
/* 1186 */     keysym2UCSHash.put(Long.valueOf(3763L), Character.valueOf('ㅃ'));
/* 1187 */     keysym2UCSHash.put(Long.valueOf(3764L), Character.valueOf('ㅄ'));
/* 1188 */     keysym2UCSHash.put(Long.valueOf(3765L), Character.valueOf('ㅅ'));
/* 1189 */     keysym2UCSHash.put(Long.valueOf(3766L), Character.valueOf('ㅆ'));
/* 1190 */     keysym2UCSHash.put(Long.valueOf(3767L), Character.valueOf('ㅇ'));
/* 1191 */     keysym2UCSHash.put(Long.valueOf(3768L), Character.valueOf('ㅈ'));
/* 1192 */     keysym2UCSHash.put(Long.valueOf(3769L), Character.valueOf('ㅉ'));
/* 1193 */     keysym2UCSHash.put(Long.valueOf(3770L), Character.valueOf('ㅊ'));
/* 1194 */     keysym2UCSHash.put(Long.valueOf(3771L), Character.valueOf('ㅋ'));
/* 1195 */     keysym2UCSHash.put(Long.valueOf(3772L), Character.valueOf('ㅌ'));
/* 1196 */     keysym2UCSHash.put(Long.valueOf(3773L), Character.valueOf('ㅍ'));
/* 1197 */     keysym2UCSHash.put(Long.valueOf(3774L), Character.valueOf('ㅎ'));
/* 1198 */     keysym2UCSHash.put(Long.valueOf(3775L), Character.valueOf('ㅏ'));
/* 1199 */     keysym2UCSHash.put(Long.valueOf(3776L), Character.valueOf('ㅐ'));
/* 1200 */     keysym2UCSHash.put(Long.valueOf(3777L), Character.valueOf('ㅑ'));
/* 1201 */     keysym2UCSHash.put(Long.valueOf(3778L), Character.valueOf('ㅒ'));
/* 1202 */     keysym2UCSHash.put(Long.valueOf(3779L), Character.valueOf('ㅓ'));
/* 1203 */     keysym2UCSHash.put(Long.valueOf(3780L), Character.valueOf('ㅔ'));
/* 1204 */     keysym2UCSHash.put(Long.valueOf(3781L), Character.valueOf('ㅕ'));
/* 1205 */     keysym2UCSHash.put(Long.valueOf(3782L), Character.valueOf('ㅖ'));
/* 1206 */     keysym2UCSHash.put(Long.valueOf(3783L), Character.valueOf('ㅗ'));
/* 1207 */     keysym2UCSHash.put(Long.valueOf(3784L), Character.valueOf('ㅘ'));
/* 1208 */     keysym2UCSHash.put(Long.valueOf(3785L), Character.valueOf('ㅙ'));
/* 1209 */     keysym2UCSHash.put(Long.valueOf(3786L), Character.valueOf('ㅚ'));
/* 1210 */     keysym2UCSHash.put(Long.valueOf(3787L), Character.valueOf('ㅛ'));
/* 1211 */     keysym2UCSHash.put(Long.valueOf(3788L), Character.valueOf('ㅜ'));
/* 1212 */     keysym2UCSHash.put(Long.valueOf(3789L), Character.valueOf('ㅝ'));
/* 1213 */     keysym2UCSHash.put(Long.valueOf(3790L), Character.valueOf('ㅞ'));
/* 1214 */     keysym2UCSHash.put(Long.valueOf(3791L), Character.valueOf('ㅟ'));
/* 1215 */     keysym2UCSHash.put(Long.valueOf(3792L), Character.valueOf('ㅠ'));
/* 1216 */     keysym2UCSHash.put(Long.valueOf(3793L), Character.valueOf('ㅡ'));
/* 1217 */     keysym2UCSHash.put(Long.valueOf(3794L), Character.valueOf('ㅢ'));
/* 1218 */     keysym2UCSHash.put(Long.valueOf(3795L), Character.valueOf('ㅣ'));
/* 1219 */     keysym2UCSHash.put(Long.valueOf(3796L), Character.valueOf('ᆨ'));
/* 1220 */     keysym2UCSHash.put(Long.valueOf(3797L), Character.valueOf('ᆩ'));
/* 1221 */     keysym2UCSHash.put(Long.valueOf(3798L), Character.valueOf('ᆪ'));
/* 1222 */     keysym2UCSHash.put(Long.valueOf(3799L), Character.valueOf('ᆫ'));
/* 1223 */     keysym2UCSHash.put(Long.valueOf(3800L), Character.valueOf('ᆬ'));
/* 1224 */     keysym2UCSHash.put(Long.valueOf(3801L), Character.valueOf('ᆭ'));
/* 1225 */     keysym2UCSHash.put(Long.valueOf(3802L), Character.valueOf('ᆮ'));
/* 1226 */     keysym2UCSHash.put(Long.valueOf(3803L), Character.valueOf('ᆯ'));
/* 1227 */     keysym2UCSHash.put(Long.valueOf(3804L), Character.valueOf('ᆰ'));
/* 1228 */     keysym2UCSHash.put(Long.valueOf(3805L), Character.valueOf('ᆱ'));
/* 1229 */     keysym2UCSHash.put(Long.valueOf(3806L), Character.valueOf('ᆲ'));
/* 1230 */     keysym2UCSHash.put(Long.valueOf(3807L), Character.valueOf('ᆳ'));
/* 1231 */     keysym2UCSHash.put(Long.valueOf(3808L), Character.valueOf('ᆴ'));
/* 1232 */     keysym2UCSHash.put(Long.valueOf(3809L), Character.valueOf('ᆵ'));
/* 1233 */     keysym2UCSHash.put(Long.valueOf(3810L), Character.valueOf('ᆶ'));
/* 1234 */     keysym2UCSHash.put(Long.valueOf(3811L), Character.valueOf('ᆷ'));
/* 1235 */     keysym2UCSHash.put(Long.valueOf(3812L), Character.valueOf('ᆸ'));
/* 1236 */     keysym2UCSHash.put(Long.valueOf(3813L), Character.valueOf('ᆹ'));
/* 1237 */     keysym2UCSHash.put(Long.valueOf(3814L), Character.valueOf('ᆺ'));
/* 1238 */     keysym2UCSHash.put(Long.valueOf(3815L), Character.valueOf('ᆻ'));
/* 1239 */     keysym2UCSHash.put(Long.valueOf(3816L), Character.valueOf('ᆼ'));
/* 1240 */     keysym2UCSHash.put(Long.valueOf(3817L), Character.valueOf('ᆽ'));
/* 1241 */     keysym2UCSHash.put(Long.valueOf(3818L), Character.valueOf('ᆾ'));
/* 1242 */     keysym2UCSHash.put(Long.valueOf(3819L), Character.valueOf('ᆿ'));
/* 1243 */     keysym2UCSHash.put(Long.valueOf(3820L), Character.valueOf('ᇀ'));
/* 1244 */     keysym2UCSHash.put(Long.valueOf(3821L), Character.valueOf('ᇁ'));
/* 1245 */     keysym2UCSHash.put(Long.valueOf(3822L), Character.valueOf('ᇂ'));
/* 1246 */     keysym2UCSHash.put(Long.valueOf(3823L), Character.valueOf('ㅭ'));
/* 1247 */     keysym2UCSHash.put(Long.valueOf(3824L), Character.valueOf('ㅱ'));
/* 1248 */     keysym2UCSHash.put(Long.valueOf(3825L), Character.valueOf('ㅸ'));
/* 1249 */     keysym2UCSHash.put(Long.valueOf(3826L), Character.valueOf('ㅿ'));
/* 1250 */     keysym2UCSHash.put(Long.valueOf(3827L), Character.valueOf('ㆁ'));
/* 1251 */     keysym2UCSHash.put(Long.valueOf(3828L), Character.valueOf('ㆄ'));
/* 1252 */     keysym2UCSHash.put(Long.valueOf(3829L), Character.valueOf('ㆆ'));
/* 1253 */     keysym2UCSHash.put(Long.valueOf(3830L), Character.valueOf('ㆍ'));
/* 1254 */     keysym2UCSHash.put(Long.valueOf(3831L), Character.valueOf('ㆎ'));
/* 1255 */     keysym2UCSHash.put(Long.valueOf(3832L), Character.valueOf('ᇫ'));
/* 1256 */     keysym2UCSHash.put(Long.valueOf(3833L), Character.valueOf('ᇰ'));
/* 1257 */     keysym2UCSHash.put(Long.valueOf(3834L), Character.valueOf('ᇹ'));
/* 1258 */     keysym2UCSHash.put(Long.valueOf(3839L), Character.valueOf('₩'));
/* 1259 */     keysym2UCSHash.put(Long.valueOf(5795L), Character.valueOf('Ẋ'));
/* 1260 */     keysym2UCSHash.put(Long.valueOf(5798L), Character.valueOf('Ĭ'));
/* 1261 */     keysym2UCSHash.put(Long.valueOf(5801L), Character.valueOf('Ƶ'));
/* 1262 */     keysym2UCSHash.put(Long.valueOf(5802L), Character.valueOf('Ǧ'));
/* 1263 */     keysym2UCSHash.put(Long.valueOf(5807L), Character.valueOf('Ɵ'));
/* 1264 */     keysym2UCSHash.put(Long.valueOf(5811L), Character.valueOf('ẋ'));
/* 1265 */     keysym2UCSHash.put(Long.valueOf(5814L), Character.valueOf('ĭ'));
/* 1266 */     keysym2UCSHash.put(Long.valueOf(5817L), Character.valueOf('ƶ'));
/* 1267 */     keysym2UCSHash.put(Long.valueOf(5818L), Character.valueOf('ǧ'));
/* 1268 */     keysym2UCSHash.put(Long.valueOf(5821L), Character.valueOf('ǒ'));
/* 1269 */     keysym2UCSHash.put(Long.valueOf(5823L), Character.valueOf('ɵ'));
/* 1270 */     keysym2UCSHash.put(Long.valueOf(5830L), Character.valueOf('Ə'));
/* 1271 */     keysym2UCSHash.put(Long.valueOf(5878L), Character.valueOf('ə'));
/* 1272 */     keysym2UCSHash.put(Long.valueOf(7840L), Character.valueOf('Ạ'));
/* 1273 */     keysym2UCSHash.put(Long.valueOf(7841L), Character.valueOf('ạ'));
/* 1274 */     keysym2UCSHash.put(Long.valueOf(7842L), Character.valueOf('Ả'));
/* 1275 */     keysym2UCSHash.put(Long.valueOf(7843L), Character.valueOf('ả'));
/* 1276 */     keysym2UCSHash.put(Long.valueOf(7844L), Character.valueOf('Ấ'));
/* 1277 */     keysym2UCSHash.put(Long.valueOf(7845L), Character.valueOf('ấ'));
/* 1278 */     keysym2UCSHash.put(Long.valueOf(7846L), Character.valueOf('Ầ'));
/* 1279 */     keysym2UCSHash.put(Long.valueOf(7847L), Character.valueOf('ầ'));
/* 1280 */     keysym2UCSHash.put(Long.valueOf(7848L), Character.valueOf('Ẩ'));
/* 1281 */     keysym2UCSHash.put(Long.valueOf(7849L), Character.valueOf('ẩ'));
/* 1282 */     keysym2UCSHash.put(Long.valueOf(7850L), Character.valueOf('Ẫ'));
/* 1283 */     keysym2UCSHash.put(Long.valueOf(7851L), Character.valueOf('ẫ'));
/* 1284 */     keysym2UCSHash.put(Long.valueOf(7852L), Character.valueOf('Ậ'));
/* 1285 */     keysym2UCSHash.put(Long.valueOf(7853L), Character.valueOf('ậ'));
/* 1286 */     keysym2UCSHash.put(Long.valueOf(7854L), Character.valueOf('Ắ'));
/* 1287 */     keysym2UCSHash.put(Long.valueOf(7855L), Character.valueOf('ắ'));
/* 1288 */     keysym2UCSHash.put(Long.valueOf(7856L), Character.valueOf('Ằ'));
/* 1289 */     keysym2UCSHash.put(Long.valueOf(7857L), Character.valueOf('ằ'));
/* 1290 */     keysym2UCSHash.put(Long.valueOf(7858L), Character.valueOf('Ẳ'));
/* 1291 */     keysym2UCSHash.put(Long.valueOf(7859L), Character.valueOf('ẳ'));
/* 1292 */     keysym2UCSHash.put(Long.valueOf(7860L), Character.valueOf('Ẵ'));
/* 1293 */     keysym2UCSHash.put(Long.valueOf(7861L), Character.valueOf('ẵ'));
/* 1294 */     keysym2UCSHash.put(Long.valueOf(7862L), Character.valueOf('Ặ'));
/* 1295 */     keysym2UCSHash.put(Long.valueOf(7863L), Character.valueOf('ặ'));
/* 1296 */     keysym2UCSHash.put(Long.valueOf(7864L), Character.valueOf('Ẹ'));
/* 1297 */     keysym2UCSHash.put(Long.valueOf(7865L), Character.valueOf('ẹ'));
/* 1298 */     keysym2UCSHash.put(Long.valueOf(7866L), Character.valueOf('Ẻ'));
/* 1299 */     keysym2UCSHash.put(Long.valueOf(7867L), Character.valueOf('ẻ'));
/* 1300 */     keysym2UCSHash.put(Long.valueOf(7868L), Character.valueOf('Ẽ'));
/* 1301 */     keysym2UCSHash.put(Long.valueOf(7869L), Character.valueOf('ẽ'));
/* 1302 */     keysym2UCSHash.put(Long.valueOf(7870L), Character.valueOf('Ế'));
/* 1303 */     keysym2UCSHash.put(Long.valueOf(7871L), Character.valueOf('ế'));
/* 1304 */     keysym2UCSHash.put(Long.valueOf(7872L), Character.valueOf('Ề'));
/* 1305 */     keysym2UCSHash.put(Long.valueOf(7873L), Character.valueOf('ề'));
/* 1306 */     keysym2UCSHash.put(Long.valueOf(7874L), Character.valueOf('Ể'));
/* 1307 */     keysym2UCSHash.put(Long.valueOf(7875L), Character.valueOf('ể'));
/* 1308 */     keysym2UCSHash.put(Long.valueOf(7876L), Character.valueOf('Ễ'));
/* 1309 */     keysym2UCSHash.put(Long.valueOf(7877L), Character.valueOf('ễ'));
/* 1310 */     keysym2UCSHash.put(Long.valueOf(7878L), Character.valueOf('Ệ'));
/* 1311 */     keysym2UCSHash.put(Long.valueOf(7879L), Character.valueOf('ệ'));
/* 1312 */     keysym2UCSHash.put(Long.valueOf(7880L), Character.valueOf('Ỉ'));
/* 1313 */     keysym2UCSHash.put(Long.valueOf(7881L), Character.valueOf('ỉ'));
/* 1314 */     keysym2UCSHash.put(Long.valueOf(7882L), Character.valueOf('Ị'));
/* 1315 */     keysym2UCSHash.put(Long.valueOf(7883L), Character.valueOf('ị'));
/* 1316 */     keysym2UCSHash.put(Long.valueOf(7884L), Character.valueOf('Ọ'));
/* 1317 */     keysym2UCSHash.put(Long.valueOf(7885L), Character.valueOf('ọ'));
/* 1318 */     keysym2UCSHash.put(Long.valueOf(7886L), Character.valueOf('Ỏ'));
/* 1319 */     keysym2UCSHash.put(Long.valueOf(7887L), Character.valueOf('ỏ'));
/* 1320 */     keysym2UCSHash.put(Long.valueOf(7888L), Character.valueOf('Ố'));
/* 1321 */     keysym2UCSHash.put(Long.valueOf(7889L), Character.valueOf('ố'));
/* 1322 */     keysym2UCSHash.put(Long.valueOf(7890L), Character.valueOf('Ồ'));
/* 1323 */     keysym2UCSHash.put(Long.valueOf(7891L), Character.valueOf('ồ'));
/* 1324 */     keysym2UCSHash.put(Long.valueOf(7892L), Character.valueOf('Ổ'));
/* 1325 */     keysym2UCSHash.put(Long.valueOf(7893L), Character.valueOf('ổ'));
/* 1326 */     keysym2UCSHash.put(Long.valueOf(7894L), Character.valueOf('Ỗ'));
/* 1327 */     keysym2UCSHash.put(Long.valueOf(7895L), Character.valueOf('ỗ'));
/* 1328 */     keysym2UCSHash.put(Long.valueOf(7896L), Character.valueOf('Ộ'));
/* 1329 */     keysym2UCSHash.put(Long.valueOf(7897L), Character.valueOf('ộ'));
/* 1330 */     keysym2UCSHash.put(Long.valueOf(7898L), Character.valueOf('Ớ'));
/* 1331 */     keysym2UCSHash.put(Long.valueOf(7899L), Character.valueOf('ớ'));
/* 1332 */     keysym2UCSHash.put(Long.valueOf(7900L), Character.valueOf('Ờ'));
/* 1333 */     keysym2UCSHash.put(Long.valueOf(7901L), Character.valueOf('ờ'));
/* 1334 */     keysym2UCSHash.put(Long.valueOf(7902L), Character.valueOf('Ở'));
/* 1335 */     keysym2UCSHash.put(Long.valueOf(7903L), Character.valueOf('ở'));
/* 1336 */     keysym2UCSHash.put(Long.valueOf(7904L), Character.valueOf('Ỡ'));
/* 1337 */     keysym2UCSHash.put(Long.valueOf(7905L), Character.valueOf('ỡ'));
/* 1338 */     keysym2UCSHash.put(Long.valueOf(7906L), Character.valueOf('Ợ'));
/* 1339 */     keysym2UCSHash.put(Long.valueOf(7907L), Character.valueOf('ợ'));
/* 1340 */     keysym2UCSHash.put(Long.valueOf(7908L), Character.valueOf('Ụ'));
/* 1341 */     keysym2UCSHash.put(Long.valueOf(7909L), Character.valueOf('ụ'));
/* 1342 */     keysym2UCSHash.put(Long.valueOf(7910L), Character.valueOf('Ủ'));
/* 1343 */     keysym2UCSHash.put(Long.valueOf(7911L), Character.valueOf('ủ'));
/* 1344 */     keysym2UCSHash.put(Long.valueOf(7912L), Character.valueOf('Ứ'));
/* 1345 */     keysym2UCSHash.put(Long.valueOf(7913L), Character.valueOf('ứ'));
/* 1346 */     keysym2UCSHash.put(Long.valueOf(7914L), Character.valueOf('Ừ'));
/* 1347 */     keysym2UCSHash.put(Long.valueOf(7915L), Character.valueOf('ừ'));
/* 1348 */     keysym2UCSHash.put(Long.valueOf(7916L), Character.valueOf('Ử'));
/* 1349 */     keysym2UCSHash.put(Long.valueOf(7917L), Character.valueOf('ử'));
/* 1350 */     keysym2UCSHash.put(Long.valueOf(7918L), Character.valueOf('Ữ'));
/* 1351 */     keysym2UCSHash.put(Long.valueOf(7919L), Character.valueOf('ữ'));
/* 1352 */     keysym2UCSHash.put(Long.valueOf(7920L), Character.valueOf('Ự'));
/* 1353 */     keysym2UCSHash.put(Long.valueOf(7921L), Character.valueOf('ự'));
/* 1354 */     keysym2UCSHash.put(Long.valueOf(7924L), Character.valueOf('Ỵ'));
/* 1355 */     keysym2UCSHash.put(Long.valueOf(7925L), Character.valueOf('ỵ'));
/* 1356 */     keysym2UCSHash.put(Long.valueOf(7926L), Character.valueOf('Ỷ'));
/* 1357 */     keysym2UCSHash.put(Long.valueOf(7927L), Character.valueOf('ỷ'));
/* 1358 */     keysym2UCSHash.put(Long.valueOf(7928L), Character.valueOf('Ỹ'));
/* 1359 */     keysym2UCSHash.put(Long.valueOf(7929L), Character.valueOf('ỹ'));
/* 1360 */     keysym2UCSHash.put(Long.valueOf(7930L), Character.valueOf('Ơ'));
/* 1361 */     keysym2UCSHash.put(Long.valueOf(7931L), Character.valueOf('ơ'));
/* 1362 */     keysym2UCSHash.put(Long.valueOf(7932L), Character.valueOf('Ư'));
/* 1363 */     keysym2UCSHash.put(Long.valueOf(7933L), Character.valueOf('ư'));
/* 1364 */     keysym2UCSHash.put(Long.valueOf(8352L), Character.valueOf('₠'));
/* 1365 */     keysym2UCSHash.put(Long.valueOf(8353L), Character.valueOf('₡'));
/* 1366 */     keysym2UCSHash.put(Long.valueOf(8354L), Character.valueOf('₢'));
/* 1367 */     keysym2UCSHash.put(Long.valueOf(8355L), Character.valueOf('₣'));
/* 1368 */     keysym2UCSHash.put(Long.valueOf(8356L), Character.valueOf('₤'));
/* 1369 */     keysym2UCSHash.put(Long.valueOf(8357L), Character.valueOf('₥'));
/* 1370 */     keysym2UCSHash.put(Long.valueOf(8358L), Character.valueOf('₦'));
/* 1371 */     keysym2UCSHash.put(Long.valueOf(8359L), Character.valueOf('₧'));
/* 1372 */     keysym2UCSHash.put(Long.valueOf(8360L), Character.valueOf('₨'));
/* 1373 */     keysym2UCSHash.put(Long.valueOf(8361L), Character.valueOf('₩'));
/* 1374 */     keysym2UCSHash.put(Long.valueOf(8362L), Character.valueOf('₪'));
/* 1375 */     keysym2UCSHash.put(Long.valueOf(8363L), Character.valueOf('₫'));
/* 1376 */     keysym2UCSHash.put(Long.valueOf(8364L), Character.valueOf('€'));
/* 1377 */     keysym2UCSHash.put(Long.valueOf(268762888L), Character.valueOf('\b'));
/* 1378 */     keysym2UCSHash.put(Long.valueOf(268762907L), Character.valueOf('\033'));
/* 1379 */     keysym2UCSHash.put(Long.valueOf(268763135L), Character.valueOf(''));
/*      */ 
/*      */ 
/*      */     
/* 1383 */     keysym2JavaKeycodeHash.put(Long.valueOf(97L), new Keysym2JavaKeycode(65, 1));
/* 1384 */     keysym2JavaKeycodeHash.put(Long.valueOf(98L), new Keysym2JavaKeycode(66, 1));
/* 1385 */     keysym2JavaKeycodeHash.put(Long.valueOf(99L), new Keysym2JavaKeycode(67, 1));
/* 1386 */     keysym2JavaKeycodeHash.put(Long.valueOf(100L), new Keysym2JavaKeycode(68, 1));
/* 1387 */     keysym2JavaKeycodeHash.put(Long.valueOf(101L), new Keysym2JavaKeycode(69, 1));
/* 1388 */     keysym2JavaKeycodeHash.put(Long.valueOf(102L), new Keysym2JavaKeycode(70, 1));
/* 1389 */     keysym2JavaKeycodeHash.put(Long.valueOf(103L), new Keysym2JavaKeycode(71, 1));
/* 1390 */     keysym2JavaKeycodeHash.put(Long.valueOf(104L), new Keysym2JavaKeycode(72, 1));
/* 1391 */     keysym2JavaKeycodeHash.put(Long.valueOf(105L), new Keysym2JavaKeycode(73, 1));
/* 1392 */     keysym2JavaKeycodeHash.put(Long.valueOf(106L), new Keysym2JavaKeycode(74, 1));
/* 1393 */     keysym2JavaKeycodeHash.put(Long.valueOf(107L), new Keysym2JavaKeycode(75, 1));
/* 1394 */     keysym2JavaKeycodeHash.put(Long.valueOf(108L), new Keysym2JavaKeycode(76, 1));
/* 1395 */     keysym2JavaKeycodeHash.put(Long.valueOf(109L), new Keysym2JavaKeycode(77, 1));
/* 1396 */     keysym2JavaKeycodeHash.put(Long.valueOf(110L), new Keysym2JavaKeycode(78, 1));
/* 1397 */     keysym2JavaKeycodeHash.put(Long.valueOf(111L), new Keysym2JavaKeycode(79, 1));
/* 1398 */     keysym2JavaKeycodeHash.put(Long.valueOf(112L), new Keysym2JavaKeycode(80, 1));
/* 1399 */     keysym2JavaKeycodeHash.put(Long.valueOf(113L), new Keysym2JavaKeycode(81, 1));
/* 1400 */     keysym2JavaKeycodeHash.put(Long.valueOf(114L), new Keysym2JavaKeycode(82, 1));
/* 1401 */     keysym2JavaKeycodeHash.put(Long.valueOf(115L), new Keysym2JavaKeycode(83, 1));
/* 1402 */     keysym2JavaKeycodeHash.put(Long.valueOf(116L), new Keysym2JavaKeycode(84, 1));
/* 1403 */     keysym2JavaKeycodeHash.put(Long.valueOf(117L), new Keysym2JavaKeycode(85, 1));
/* 1404 */     keysym2JavaKeycodeHash.put(Long.valueOf(118L), new Keysym2JavaKeycode(86, 1));
/* 1405 */     keysym2JavaKeycodeHash.put(Long.valueOf(119L), new Keysym2JavaKeycode(87, 1));
/* 1406 */     keysym2JavaKeycodeHash.put(Long.valueOf(120L), new Keysym2JavaKeycode(88, 1));
/* 1407 */     keysym2JavaKeycodeHash.put(Long.valueOf(121L), new Keysym2JavaKeycode(89, 1));
/* 1408 */     keysym2JavaKeycodeHash.put(Long.valueOf(122L), new Keysym2JavaKeycode(90, 1));
/*      */ 
/*      */     
/* 1411 */     keysym2JavaKeycodeHash.put(Long.valueOf(65288L), new Keysym2JavaKeycode(8, 1));
/* 1412 */     keysym2JavaKeycodeHash.put(Long.valueOf(65289L), new Keysym2JavaKeycode(9, 1));
/* 1413 */     keysym2JavaKeycodeHash.put(Long.valueOf(65056L), new Keysym2JavaKeycode(9, 1));
/* 1414 */     keysym2JavaKeycodeHash.put(Long.valueOf(65291L), new Keysym2JavaKeycode(12, 1));
/* 1415 */     keysym2JavaKeycodeHash.put(Long.valueOf(65293L), new Keysym2JavaKeycode(10, 1));
/* 1416 */     keysym2JavaKeycodeHash.put(Long.valueOf(65290L), new Keysym2JavaKeycode(10, 1));
/* 1417 */     keysym2JavaKeycodeHash.put(Long.valueOf(65299L), new Keysym2JavaKeycode(19, 1));
/* 1418 */     keysym2JavaKeycodeHash.put(Long.valueOf(65490L), new Keysym2JavaKeycode(19, 1));
/* 1419 */     keysym2JavaKeycodeHash.put(Long.valueOf(65490L), new Keysym2JavaKeycode(19, 1));
/* 1420 */     keysym2JavaKeycodeHash.put(Long.valueOf(65300L), new Keysym2JavaKeycode(145, 1));
/* 1421 */     keysym2JavaKeycodeHash.put(Long.valueOf(65492L), new Keysym2JavaKeycode(145, 1));
/* 1422 */     keysym2JavaKeycodeHash.put(Long.valueOf(65492L), new Keysym2JavaKeycode(145, 1));
/* 1423 */     keysym2JavaKeycodeHash.put(Long.valueOf(65307L), new Keysym2JavaKeycode(27, 1));
/*      */ 
/*      */     
/* 1426 */     keysym2JavaKeycodeHash.put(Long.valueOf(268762888L), new Keysym2JavaKeycode(8, 1));
/* 1427 */     keysym2JavaKeycodeHash.put(Long.valueOf(268762891L), new Keysym2JavaKeycode(12, 1));
/* 1428 */     keysym2JavaKeycodeHash.put(Long.valueOf(268762907L), new Keysym2JavaKeycode(27, 1));
/*      */ 
/*      */     
/* 1431 */     keysym2JavaKeycodeHash.put(Long.valueOf(65505L), new Keysym2JavaKeycode(16, 2));
/* 1432 */     keysym2JavaKeycodeHash.put(Long.valueOf(65506L), new Keysym2JavaKeycode(16, 3));
/* 1433 */     keysym2JavaKeycodeHash.put(Long.valueOf(65507L), new Keysym2JavaKeycode(17, 2));
/* 1434 */     keysym2JavaKeycodeHash.put(Long.valueOf(65508L), new Keysym2JavaKeycode(17, 3));
/* 1435 */     keysym2JavaKeycodeHash.put(Long.valueOf(65513L), new Keysym2JavaKeycode(18, 2));
/* 1436 */     keysym2JavaKeycodeHash.put(Long.valueOf(65514L), new Keysym2JavaKeycode(18, 3));
/* 1437 */     keysym2JavaKeycodeHash.put(Long.valueOf(65511L), new Keysym2JavaKeycode(157, 2));
/* 1438 */     keysym2JavaKeycodeHash.put(Long.valueOf(65512L), new Keysym2JavaKeycode(157, 3));
/* 1439 */     keysym2JavaKeycodeHash.put(Long.valueOf(65509L), new Keysym2JavaKeycode(20, 1));
/* 1440 */     keysym2JavaKeycodeHash.put(Long.valueOf(65510L), new Keysym2JavaKeycode(20, 1));
/*      */ 
/*      */     
/* 1443 */     keysym2JavaKeycodeHash.put(Long.valueOf(65377L), new Keysym2JavaKeycode(154, 1));
/* 1444 */     keysym2JavaKeycodeHash.put(Long.valueOf(65491L), new Keysym2JavaKeycode(154, 1));
/* 1445 */     keysym2JavaKeycodeHash.put(Long.valueOf(65491L), new Keysym2JavaKeycode(154, 1));
/* 1446 */     keysym2JavaKeycodeHash.put(Long.valueOf(65385L), new Keysym2JavaKeycode(3, 1));
/* 1447 */     keysym2JavaKeycodeHash.put(Long.valueOf(65386L), new Keysym2JavaKeycode(156, 1));
/* 1448 */     keysym2JavaKeycodeHash.put(Long.valueOf(65407L), new Keysym2JavaKeycode(144, 4));
/*      */ 
/*      */     
/* 1451 */     keysym2JavaKeycodeHash.put(Long.valueOf(268762985L), new Keysym2JavaKeycode(3, 1));
/* 1452 */     keysym2JavaKeycodeHash.put(Long.valueOf(268762986L), new Keysym2JavaKeycode(156, 1));
/*      */ 
/*      */     
/* 1455 */     keysym2JavaKeycodeHash.put(Long.valueOf(65360L), new Keysym2JavaKeycode(36, 1));
/* 1456 */     keysym2JavaKeycodeHash.put(Long.valueOf(65496L), new Keysym2JavaKeycode(36, 1));
/* 1457 */     keysym2JavaKeycodeHash.put(Long.valueOf(65365L), new Keysym2JavaKeycode(33, 1));
/* 1458 */     keysym2JavaKeycodeHash.put(Long.valueOf(65365L), new Keysym2JavaKeycode(33, 1));
/* 1459 */     keysym2JavaKeycodeHash.put(Long.valueOf(65498L), new Keysym2JavaKeycode(33, 1));
/* 1460 */     keysym2JavaKeycodeHash.put(Long.valueOf(65366L), new Keysym2JavaKeycode(34, 1));
/* 1461 */     keysym2JavaKeycodeHash.put(Long.valueOf(65366L), new Keysym2JavaKeycode(34, 1));
/* 1462 */     keysym2JavaKeycodeHash.put(Long.valueOf(65504L), new Keysym2JavaKeycode(34, 1));
/* 1463 */     keysym2JavaKeycodeHash.put(Long.valueOf(65367L), new Keysym2JavaKeycode(35, 1));
/* 1464 */     keysym2JavaKeycodeHash.put(Long.valueOf(65502L), new Keysym2JavaKeycode(35, 1));
/* 1465 */     keysym2JavaKeycodeHash.put(Long.valueOf(65379L), new Keysym2JavaKeycode(155, 1));
/* 1466 */     keysym2JavaKeycodeHash.put(Long.valueOf(65535L), new Keysym2JavaKeycode(127, 1));
/*      */ 
/*      */     
/* 1469 */     keysym2JavaKeycodeHash.put(Long.valueOf(65429L), new Keysym2JavaKeycode(36, 4));
/* 1470 */     keysym2JavaKeycodeHash.put(Long.valueOf(65434L), new Keysym2JavaKeycode(33, 4));
/* 1471 */     keysym2JavaKeycodeHash.put(Long.valueOf(65434L), new Keysym2JavaKeycode(33, 4));
/* 1472 */     keysym2JavaKeycodeHash.put(Long.valueOf(65435L), new Keysym2JavaKeycode(34, 4));
/* 1473 */     keysym2JavaKeycodeHash.put(Long.valueOf(65435L), new Keysym2JavaKeycode(34, 4));
/* 1474 */     keysym2JavaKeycodeHash.put(Long.valueOf(65436L), new Keysym2JavaKeycode(35, 4));
/* 1475 */     keysym2JavaKeycodeHash.put(Long.valueOf(65438L), new Keysym2JavaKeycode(155, 4));
/* 1476 */     keysym2JavaKeycodeHash.put(Long.valueOf(65439L), new Keysym2JavaKeycode(127, 4));
/*      */ 
/*      */     
/* 1479 */     keysym2JavaKeycodeHash.put(Long.valueOf(268762945L), new Keysym2JavaKeycode(33, 1));
/* 1480 */     keysym2JavaKeycodeHash.put(Long.valueOf(268762965L), new Keysym2JavaKeycode(33, 1));
/* 1481 */     keysym2JavaKeycodeHash.put(Long.valueOf(268762946L), new Keysym2JavaKeycode(34, 1));
/* 1482 */     keysym2JavaKeycodeHash.put(Long.valueOf(268762966L), new Keysym2JavaKeycode(34, 1));
/* 1483 */     keysym2JavaKeycodeHash.put(Long.valueOf(268762967L), new Keysym2JavaKeycode(35, 1));
/* 1484 */     keysym2JavaKeycodeHash.put(Long.valueOf(268762979L), new Keysym2JavaKeycode(155, 1));
/* 1485 */     keysym2JavaKeycodeHash.put(Long.valueOf(268763135L), new Keysym2JavaKeycode(127, 1));
/*      */ 
/*      */     
/* 1488 */     keysym2JavaKeycodeHash.put(Long.valueOf(65361L), new Keysym2JavaKeycode(37, 1));
/* 1489 */     keysym2JavaKeycodeHash.put(Long.valueOf(65362L), new Keysym2JavaKeycode(38, 1));
/* 1490 */     keysym2JavaKeycodeHash.put(Long.valueOf(65363L), new Keysym2JavaKeycode(39, 1));
/* 1491 */     keysym2JavaKeycodeHash.put(Long.valueOf(65364L), new Keysym2JavaKeycode(40, 1));
/*      */ 
/*      */     
/* 1494 */     keysym2JavaKeycodeHash.put(Long.valueOf(65430L), new Keysym2JavaKeycode(226, 4));
/* 1495 */     keysym2JavaKeycodeHash.put(Long.valueOf(65431L), new Keysym2JavaKeycode(224, 4));
/* 1496 */     keysym2JavaKeycodeHash.put(Long.valueOf(65432L), new Keysym2JavaKeycode(227, 4));
/* 1497 */     keysym2JavaKeycodeHash.put(Long.valueOf(65433L), new Keysym2JavaKeycode(225, 4));
/*      */ 
/*      */     
/* 1500 */     keysym2JavaKeycodeHash.put(Long.valueOf(268762961L), new Keysym2JavaKeycode(37, 1));
/* 1501 */     keysym2JavaKeycodeHash.put(Long.valueOf(268762962L), new Keysym2JavaKeycode(38, 1));
/* 1502 */     keysym2JavaKeycodeHash.put(Long.valueOf(268762963L), new Keysym2JavaKeycode(39, 1));
/* 1503 */     keysym2JavaKeycodeHash.put(Long.valueOf(268762964L), new Keysym2JavaKeycode(40, 1));
/*      */ 
/*      */     
/* 1506 */     keysym2JavaKeycodeHash.put(Long.valueOf(65368L), new Keysym2JavaKeycode(65368, 1));
/* 1507 */     keysym2JavaKeycodeHash.put(Long.valueOf(65437L), new Keysym2JavaKeycode(65368, 4));
/*      */     
/* 1509 */     keysym2JavaKeycodeHash.put(Long.valueOf(48L), new Keysym2JavaKeycode(48, 1));
/* 1510 */     keysym2JavaKeycodeHash.put(Long.valueOf(49L), new Keysym2JavaKeycode(49, 1));
/* 1511 */     keysym2JavaKeycodeHash.put(Long.valueOf(50L), new Keysym2JavaKeycode(50, 1));
/* 1512 */     keysym2JavaKeycodeHash.put(Long.valueOf(51L), new Keysym2JavaKeycode(51, 1));
/* 1513 */     keysym2JavaKeycodeHash.put(Long.valueOf(52L), new Keysym2JavaKeycode(52, 1));
/* 1514 */     keysym2JavaKeycodeHash.put(Long.valueOf(53L), new Keysym2JavaKeycode(53, 1));
/* 1515 */     keysym2JavaKeycodeHash.put(Long.valueOf(54L), new Keysym2JavaKeycode(54, 1));
/* 1516 */     keysym2JavaKeycodeHash.put(Long.valueOf(55L), new Keysym2JavaKeycode(55, 1));
/* 1517 */     keysym2JavaKeycodeHash.put(Long.valueOf(56L), new Keysym2JavaKeycode(56, 1));
/* 1518 */     keysym2JavaKeycodeHash.put(Long.valueOf(57L), new Keysym2JavaKeycode(57, 1));
/*      */     
/* 1520 */     keysym2JavaKeycodeHash.put(Long.valueOf(32L), new Keysym2JavaKeycode(32, 1));
/* 1521 */     keysym2JavaKeycodeHash.put(Long.valueOf(33L), new Keysym2JavaKeycode(517, 1));
/* 1522 */     keysym2JavaKeycodeHash.put(Long.valueOf(34L), new Keysym2JavaKeycode(152, 1));
/* 1523 */     keysym2JavaKeycodeHash.put(Long.valueOf(35L), new Keysym2JavaKeycode(520, 1));
/* 1524 */     keysym2JavaKeycodeHash.put(Long.valueOf(36L), new Keysym2JavaKeycode(515, 1));
/* 1525 */     keysym2JavaKeycodeHash.put(Long.valueOf(38L), new Keysym2JavaKeycode(150, 1));
/* 1526 */     keysym2JavaKeycodeHash.put(Long.valueOf(39L), new Keysym2JavaKeycode(222, 1));
/* 1527 */     keysym2JavaKeycodeHash.put(Long.valueOf(40L), new Keysym2JavaKeycode(519, 1));
/* 1528 */     keysym2JavaKeycodeHash.put(Long.valueOf(41L), new Keysym2JavaKeycode(522, 1));
/* 1529 */     keysym2JavaKeycodeHash.put(Long.valueOf(42L), new Keysym2JavaKeycode(151, 1));
/* 1530 */     keysym2JavaKeycodeHash.put(Long.valueOf(43L), new Keysym2JavaKeycode(521, 1));
/* 1531 */     keysym2JavaKeycodeHash.put(Long.valueOf(44L), new Keysym2JavaKeycode(44, 1));
/* 1532 */     keysym2JavaKeycodeHash.put(Long.valueOf(45L), new Keysym2JavaKeycode(45, 1));
/* 1533 */     keysym2JavaKeycodeHash.put(Long.valueOf(46L), new Keysym2JavaKeycode(46, 1));
/* 1534 */     keysym2JavaKeycodeHash.put(Long.valueOf(47L), new Keysym2JavaKeycode(47, 1));
/*      */     
/* 1536 */     keysym2JavaKeycodeHash.put(Long.valueOf(58L), new Keysym2JavaKeycode(513, 1));
/* 1537 */     keysym2JavaKeycodeHash.put(Long.valueOf(59L), new Keysym2JavaKeycode(59, 1));
/* 1538 */     keysym2JavaKeycodeHash.put(Long.valueOf(60L), new Keysym2JavaKeycode(153, 1));
/* 1539 */     keysym2JavaKeycodeHash.put(Long.valueOf(61L), new Keysym2JavaKeycode(61, 1));
/* 1540 */     keysym2JavaKeycodeHash.put(Long.valueOf(62L), new Keysym2JavaKeycode(160, 1));
/*      */     
/* 1542 */     keysym2JavaKeycodeHash.put(Long.valueOf(64L), new Keysym2JavaKeycode(512, 1));
/*      */     
/* 1544 */     keysym2JavaKeycodeHash.put(Long.valueOf(91L), new Keysym2JavaKeycode(91, 1));
/* 1545 */     keysym2JavaKeycodeHash.put(Long.valueOf(92L), new Keysym2JavaKeycode(92, 1));
/* 1546 */     keysym2JavaKeycodeHash.put(Long.valueOf(93L), new Keysym2JavaKeycode(93, 1));
/* 1547 */     keysym2JavaKeycodeHash.put(Long.valueOf(94L), new Keysym2JavaKeycode(514, 1));
/* 1548 */     keysym2JavaKeycodeHash.put(Long.valueOf(95L), new Keysym2JavaKeycode(523, 1));
/* 1549 */     keysym2JavaKeycodeHash.put(Long.valueOf(65515L), new Keysym2JavaKeycode(524, 1));
/* 1550 */     keysym2JavaKeycodeHash.put(Long.valueOf(65516L), new Keysym2JavaKeycode(524, 1));
/* 1551 */     keysym2JavaKeycodeHash.put(Long.valueOf(65383L), new Keysym2JavaKeycode(525, 1));
/* 1552 */     keysym2JavaKeycodeHash.put(Long.valueOf(96L), new Keysym2JavaKeycode(192, 1));
/*      */     
/* 1554 */     keysym2JavaKeycodeHash.put(Long.valueOf(123L), new Keysym2JavaKeycode(161, 1));
/* 1555 */     keysym2JavaKeycodeHash.put(Long.valueOf(125L), new Keysym2JavaKeycode(162, 1));
/*      */     
/* 1557 */     keysym2JavaKeycodeHash.put(Long.valueOf(161L), new Keysym2JavaKeycode(518, 1));
/*      */ 
/*      */     
/* 1560 */     keysym2JavaKeycodeHash.put(Long.valueOf(65456L), new Keysym2JavaKeycode(96, 4));
/* 1561 */     keysym2JavaKeycodeHash.put(Long.valueOf(65457L), new Keysym2JavaKeycode(97, 4));
/* 1562 */     keysym2JavaKeycodeHash.put(Long.valueOf(65458L), new Keysym2JavaKeycode(98, 4));
/* 1563 */     keysym2JavaKeycodeHash.put(Long.valueOf(65459L), new Keysym2JavaKeycode(99, 4));
/* 1564 */     keysym2JavaKeycodeHash.put(Long.valueOf(65460L), new Keysym2JavaKeycode(100, 4));
/* 1565 */     keysym2JavaKeycodeHash.put(Long.valueOf(65461L), new Keysym2JavaKeycode(101, 4));
/* 1566 */     keysym2JavaKeycodeHash.put(Long.valueOf(65462L), new Keysym2JavaKeycode(102, 4));
/* 1567 */     keysym2JavaKeycodeHash.put(Long.valueOf(65463L), new Keysym2JavaKeycode(103, 4));
/* 1568 */     keysym2JavaKeycodeHash.put(Long.valueOf(65464L), new Keysym2JavaKeycode(104, 4));
/* 1569 */     keysym2JavaKeycodeHash.put(Long.valueOf(65465L), new Keysym2JavaKeycode(105, 4));
/* 1570 */     keysym2JavaKeycodeHash.put(Long.valueOf(65408L), new Keysym2JavaKeycode(32, 4));
/* 1571 */     keysym2JavaKeycodeHash.put(Long.valueOf(65417L), new Keysym2JavaKeycode(9, 4));
/* 1572 */     keysym2JavaKeycodeHash.put(Long.valueOf(65421L), new Keysym2JavaKeycode(10, 4));
/* 1573 */     keysym2JavaKeycodeHash.put(Long.valueOf(65469L), new Keysym2JavaKeycode(61, 4));
/* 1574 */     keysym2JavaKeycodeHash.put(Long.valueOf(65493L), new Keysym2JavaKeycode(61, 4));
/* 1575 */     keysym2JavaKeycodeHash.put(Long.valueOf(65450L), new Keysym2JavaKeycode(106, 4));
/* 1576 */     keysym2JavaKeycodeHash.put(Long.valueOf(65495L), new Keysym2JavaKeycode(106, 4));
/* 1577 */     keysym2JavaKeycodeHash.put(Long.valueOf(65495L), new Keysym2JavaKeycode(106, 4));
/* 1578 */     keysym2JavaKeycodeHash.put(Long.valueOf(65451L), new Keysym2JavaKeycode(107, 4));
/* 1579 */     keysym2JavaKeycodeHash.put(Long.valueOf(65452L), new Keysym2JavaKeycode(108, 4));
/* 1580 */     keysym2JavaKeycodeHash.put(Long.valueOf(65453L), new Keysym2JavaKeycode(109, 4));
/* 1581 */     keysym2JavaKeycodeHash.put(Long.valueOf(65493L), new Keysym2JavaKeycode(109, 4));
/* 1582 */     keysym2JavaKeycodeHash.put(Long.valueOf(65454L), new Keysym2JavaKeycode(110, 4));
/* 1583 */     keysym2JavaKeycodeHash.put(Long.valueOf(65455L), new Keysym2JavaKeycode(111, 4));
/* 1584 */     keysym2JavaKeycodeHash.put(Long.valueOf(65494L), new Keysym2JavaKeycode(111, 4));
/* 1585 */     keysym2JavaKeycodeHash.put(Long.valueOf(65494L), new Keysym2JavaKeycode(111, 4));
/*      */ 
/*      */     
/* 1588 */     keysym2JavaKeycodeHash.put(Long.valueOf(65470L), new Keysym2JavaKeycode(112, 1));
/* 1589 */     keysym2JavaKeycodeHash.put(Long.valueOf(65471L), new Keysym2JavaKeycode(113, 1));
/* 1590 */     keysym2JavaKeycodeHash.put(Long.valueOf(65472L), new Keysym2JavaKeycode(114, 1));
/* 1591 */     keysym2JavaKeycodeHash.put(Long.valueOf(65473L), new Keysym2JavaKeycode(115, 1));
/* 1592 */     keysym2JavaKeycodeHash.put(Long.valueOf(65474L), new Keysym2JavaKeycode(116, 1));
/* 1593 */     keysym2JavaKeycodeHash.put(Long.valueOf(65475L), new Keysym2JavaKeycode(117, 1));
/* 1594 */     keysym2JavaKeycodeHash.put(Long.valueOf(65476L), new Keysym2JavaKeycode(118, 1));
/* 1595 */     keysym2JavaKeycodeHash.put(Long.valueOf(65477L), new Keysym2JavaKeycode(119, 1));
/* 1596 */     keysym2JavaKeycodeHash.put(Long.valueOf(65478L), new Keysym2JavaKeycode(120, 1));
/* 1597 */     keysym2JavaKeycodeHash.put(Long.valueOf(65479L), new Keysym2JavaKeycode(121, 1));
/* 1598 */     keysym2JavaKeycodeHash.put(Long.valueOf(65480L), new Keysym2JavaKeycode(122, 1));
/* 1599 */     keysym2JavaKeycodeHash.put(Long.valueOf(65481L), new Keysym2JavaKeycode(123, 1));
/*      */ 
/*      */     
/* 1602 */     keysym2JavaKeycodeHash.put(Long.valueOf(268828432L), new Keysym2JavaKeycode(122, 1));
/* 1603 */     keysym2JavaKeycodeHash.put(Long.valueOf(268828433L), new Keysym2JavaKeycode(123, 1));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1610 */     keysym2JavaKeycodeHash.put(Long.valueOf(65378L), new Keysym2JavaKeycode(30, 1));
/*      */     
/* 1612 */     keysym2JavaKeycodeHash.put(Long.valueOf(65313L), new Keysym2JavaKeycode(28, 1));
/*      */     
/* 1614 */     keysym2JavaKeycodeHash.put(Long.valueOf(65315L), new Keysym2JavaKeycode(263, 1));
/* 1615 */     keysym2JavaKeycodeHash.put(Long.valueOf(65327L), new Keysym2JavaKeycode(240, 1));
/* 1616 */     keysym2JavaKeycodeHash.put(Long.valueOf(65328L), new Keysym2JavaKeycode(240, 1));
/* 1617 */     keysym2JavaKeycodeHash.put(Long.valueOf(65320L), new Keysym2JavaKeycode(243, 1));
/* 1618 */     keysym2JavaKeycodeHash.put(Long.valueOf(65321L), new Keysym2JavaKeycode(244, 1));
/* 1619 */     keysym2JavaKeycodeHash.put(Long.valueOf(65317L), new Keysym2JavaKeycode(242, 1));
/* 1620 */     keysym2JavaKeycodeHash.put(Long.valueOf(65318L), new Keysym2JavaKeycode(241, 1));
/* 1621 */     keysym2JavaKeycodeHash.put(Long.valueOf(65316L), new Keysym2JavaKeycode(261, 1));
/* 1622 */     keysym2JavaKeycodeHash.put(Long.valueOf(65326L), new Keysym2JavaKeycode(21, 1));
/* 1623 */     keysym2JavaKeycodeHash.put(Long.valueOf(65325L), new Keysym2JavaKeycode(262, 1));
/* 1624 */     keysym2JavaKeycodeHash.put(Long.valueOf(65314L), new Keysym2JavaKeycode(29, 1));
/* 1625 */     keysym2JavaKeycodeHash.put(Long.valueOf(65341L), new Keysym2JavaKeycode(256, 1));
/* 1626 */     keysym2JavaKeycodeHash.put(Long.valueOf(65335L), new Keysym2JavaKeycode(258, 1));
/* 1627 */     keysym2JavaKeycodeHash.put(Long.valueOf(65342L), new Keysym2JavaKeycode(257, 1));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1634 */     keysym2JavaKeycodeHash.put(Long.valueOf(65312L), new Keysym2JavaKeycode(65312, 1));
/* 1635 */     keysym2JavaKeycodeHash.put(Long.valueOf(65406L), new Keysym2JavaKeycode(65406, 1));
/* 1636 */     keysym2JavaKeycodeHash.put(Long.valueOf(65027L), new Keysym2JavaKeycode(65406, 1));
/*      */ 
/*      */     
/* 1639 */     keysym2JavaKeycodeHash.put(Long.valueOf(65382L), new Keysym2JavaKeycode(65481, 1));
/*      */ 
/*      */ 
/*      */     
/* 1643 */     keysym2JavaKeycodeHash.put(Long.valueOf(65381L), new Keysym2JavaKeycode(65483, 1));
/* 1644 */     keysym2JavaKeycodeHash.put(Long.valueOf(65483L), new Keysym2JavaKeycode(65483, 1));
/* 1645 */     keysym2JavaKeycodeHash.put(Long.valueOf(65485L), new Keysym2JavaKeycode(65485, 1));
/* 1646 */     keysym2JavaKeycodeHash.put(Long.valueOf(65487L), new Keysym2JavaKeycode(65487, 1));
/* 1647 */     keysym2JavaKeycodeHash.put(Long.valueOf(65489L), new Keysym2JavaKeycode(65489, 1));
/* 1648 */     keysym2JavaKeycodeHash.put(Long.valueOf(65384L), new Keysym2JavaKeycode(65488, 1));
/* 1649 */     keysym2JavaKeycodeHash.put(Long.valueOf(65488L), new Keysym2JavaKeycode(65488, 1));
/* 1650 */     keysym2JavaKeycodeHash.put(Long.valueOf(65482L), new Keysym2JavaKeycode(65482, 1));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1656 */     keysym2JavaKeycodeHash.put(Long.valueOf(65382L), new Keysym2JavaKeycode(65481, 1));
/* 1657 */     keysym2JavaKeycodeHash.put(Long.valueOf(65381L), new Keysym2JavaKeycode(65483, 1));
/* 1658 */     keysym2JavaKeycodeHash.put(Long.valueOf(268828530L), new Keysym2JavaKeycode(65485, 1));
/* 1659 */     keysym2JavaKeycodeHash.put(Long.valueOf(268828532L), new Keysym2JavaKeycode(65487, 1));
/* 1660 */     keysym2JavaKeycodeHash.put(Long.valueOf(268828533L), new Keysym2JavaKeycode(65489, 1));
/* 1661 */     keysym2JavaKeycodeHash.put(Long.valueOf(65384L), new Keysym2JavaKeycode(65488, 1));
/* 1662 */     keysym2JavaKeycodeHash.put(Long.valueOf(268828528L), new Keysym2JavaKeycode(65482, 1));
/* 1663 */     keysym2JavaKeycodeHash.put(Long.valueOf(65385L), new Keysym2JavaKeycode(65480, 1));
/*      */ 
/*      */     
/* 1666 */     keysym2JavaKeycodeHash.put(Long.valueOf(268500738L), new Keysym2JavaKeycode(65485, 1));
/* 1667 */     keysym2JavaKeycodeHash.put(Long.valueOf(268500739L), new Keysym2JavaKeycode(65489, 1));
/* 1668 */     keysym2JavaKeycodeHash.put(Long.valueOf(268500740L), new Keysym2JavaKeycode(65487, 1));
/*      */ 
/*      */     
/* 1671 */     keysym2JavaKeycodeHash.put(Long.valueOf(268762882L), new Keysym2JavaKeycode(65485, 1));
/* 1672 */     keysym2JavaKeycodeHash.put(Long.valueOf(268762883L), new Keysym2JavaKeycode(65489, 1));
/* 1673 */     keysym2JavaKeycodeHash.put(Long.valueOf(268762884L), new Keysym2JavaKeycode(65487, 1));
/* 1674 */     keysym2JavaKeycodeHash.put(Long.valueOf(268762981L), new Keysym2JavaKeycode(65483, 1));
/*      */ 
/*      */     
/* 1677 */     keysym2JavaKeycodeHash.put(Long.valueOf(65104L), new Keysym2JavaKeycode(128, 1));
/* 1678 */     keysym2JavaKeycodeHash.put(Long.valueOf(65105L), new Keysym2JavaKeycode(129, 1));
/* 1679 */     keysym2JavaKeycodeHash.put(Long.valueOf(65106L), new Keysym2JavaKeycode(130, 1));
/* 1680 */     keysym2JavaKeycodeHash.put(Long.valueOf(65107L), new Keysym2JavaKeycode(131, 1));
/* 1681 */     keysym2JavaKeycodeHash.put(Long.valueOf(65108L), new Keysym2JavaKeycode(132, 1));
/* 1682 */     keysym2JavaKeycodeHash.put(Long.valueOf(65109L), new Keysym2JavaKeycode(133, 1));
/* 1683 */     keysym2JavaKeycodeHash.put(Long.valueOf(65110L), new Keysym2JavaKeycode(134, 1));
/* 1684 */     keysym2JavaKeycodeHash.put(Long.valueOf(65111L), new Keysym2JavaKeycode(135, 1));
/* 1685 */     keysym2JavaKeycodeHash.put(Long.valueOf(65112L), new Keysym2JavaKeycode(136, 1));
/* 1686 */     keysym2JavaKeycodeHash.put(Long.valueOf(65113L), new Keysym2JavaKeycode(137, 1));
/* 1687 */     keysym2JavaKeycodeHash.put(Long.valueOf(65114L), new Keysym2JavaKeycode(138, 1));
/* 1688 */     keysym2JavaKeycodeHash.put(Long.valueOf(65115L), new Keysym2JavaKeycode(139, 1));
/* 1689 */     keysym2JavaKeycodeHash.put(Long.valueOf(65116L), new Keysym2JavaKeycode(140, 1));
/* 1690 */     keysym2JavaKeycodeHash.put(Long.valueOf(65117L), new Keysym2JavaKeycode(141, 1));
/* 1691 */     keysym2JavaKeycodeHash.put(Long.valueOf(65118L), new Keysym2JavaKeycode(142, 1));
/* 1692 */     keysym2JavaKeycodeHash.put(Long.valueOf(65119L), new Keysym2JavaKeycode(143, 1));
/*      */ 
/*      */     
/* 1695 */     keysym2JavaKeycodeHash.put(Long.valueOf(268828416L), new Keysym2JavaKeycode(128, 1));
/* 1696 */     keysym2JavaKeycodeHash.put(Long.valueOf(268828417L), new Keysym2JavaKeycode(130, 1));
/* 1697 */     keysym2JavaKeycodeHash.put(Long.valueOf(268828418L), new Keysym2JavaKeycode(131, 1));
/* 1698 */     keysym2JavaKeycodeHash.put(Long.valueOf(268828419L), new Keysym2JavaKeycode(129, 1));
/* 1699 */     keysym2JavaKeycodeHash.put(Long.valueOf(268828420L), new Keysym2JavaKeycode(135, 1));
/* 1700 */     keysym2JavaKeycodeHash.put(Long.valueOf(268828421L), new Keysym2JavaKeycode(139, 1));
/*      */ 
/*      */     
/* 1703 */     keysym2JavaKeycodeHash.put(Long.valueOf(268500656L), new Keysym2JavaKeycode(136, 1));
/* 1704 */     keysym2JavaKeycodeHash.put(Long.valueOf(268500574L), new Keysym2JavaKeycode(130, 1));
/* 1705 */     keysym2JavaKeycodeHash.put(Long.valueOf(268500524L), new Keysym2JavaKeycode(139, 1));
/* 1706 */     keysym2JavaKeycodeHash.put(Long.valueOf(268500519L), new Keysym2JavaKeycode(129, 1));
/* 1707 */     keysym2JavaKeycodeHash.put(Long.valueOf(268500576L), new Keysym2JavaKeycode(128, 1));
/* 1708 */     keysym2JavaKeycodeHash.put(Long.valueOf(268500606L), new Keysym2JavaKeycode(131, 1));
/* 1709 */     keysym2JavaKeycodeHash.put(Long.valueOf(268500514L), new Keysym2JavaKeycode(135, 1));
/*      */ 
/*      */     
/* 1712 */     keysym2JavaKeycodeHash.put(Long.valueOf(268435624L), new Keysym2JavaKeycode(129, 1));
/* 1713 */     keysym2JavaKeycodeHash.put(Long.valueOf(268435625L), new Keysym2JavaKeycode(128, 1));
/* 1714 */     keysym2JavaKeycodeHash.put(Long.valueOf(268435626L), new Keysym2JavaKeycode(130, 1));
/* 1715 */     keysym2JavaKeycodeHash.put(Long.valueOf(268435627L), new Keysym2JavaKeycode(135, 1));
/* 1716 */     keysym2JavaKeycodeHash.put(Long.valueOf(268435628L), new Keysym2JavaKeycode(131, 1));
/*      */     
/* 1718 */     keysym2JavaKeycodeHash.put(Long.valueOf(0L), new Keysym2JavaKeycode(0, 0));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1723 */     javaKeycode2KeysymHash.put(Integer.valueOf(20), Long.valueOf(65509L));
/* 1724 */     javaKeycode2KeysymHash.put(Integer.valueOf(144), Long.valueOf(65407L));
/* 1725 */     javaKeycode2KeysymHash.put(Integer.valueOf(145), Long.valueOf(65300L));
/* 1726 */     javaKeycode2KeysymHash.put(Integer.valueOf(262), Long.valueOf(65325L));
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XKeysym.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */