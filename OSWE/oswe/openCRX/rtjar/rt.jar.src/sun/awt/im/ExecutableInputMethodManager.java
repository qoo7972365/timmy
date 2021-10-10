/*     */ package sun.awt.im;
/*     */ 
/*     */ import java.awt.AWTException;
/*     */ import java.awt.Component;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.InvocationEvent;
/*     */ import java.awt.im.spi.InputMethodDescriptor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Locale;
/*     */ import java.util.ServiceLoader;
/*     */ import java.util.Vector;
/*     */ import java.util.prefs.BackingStoreException;
/*     */ import java.util.prefs.Preferences;
/*     */ import sun.awt.AppContext;
/*     */ import sun.awt.InputMethodSupport;
/*     */ import sun.awt.SunToolkit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ExecutableInputMethodManager
/*     */   extends InputMethodManager
/*     */   implements Runnable
/*     */ {
/*     */   private InputContext currentInputContext;
/*     */   private String triggerMenuString;
/*     */   private InputMethodPopupMenu selectionMenu;
/*     */   private static String selectInputMethodMenuTitle;
/*     */   private InputMethodLocator hostAdapterLocator;
/*     */   private int javaInputMethodCount;
/*     */   private Vector<InputMethodLocator> javaInputMethodLocatorList;
/*     */   private Component requestComponent;
/*     */   private InputContext requestInputContext;
/*     */   private static final String preferredIMNode = "/sun/awt/im/preferredInputMethod";
/*     */   private static final String descriptorKey = "descriptor";
/*  99 */   private Hashtable<String, InputMethodLocator> preferredLocatorCache = new Hashtable<>();
/*     */   
/*     */   private Preferences userRoot;
/*     */ 
/*     */   
/*     */   ExecutableInputMethodManager() {
/* 105 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*     */     try {
/* 107 */       if (toolkit instanceof InputMethodSupport) {
/*     */ 
/*     */         
/* 110 */         InputMethodDescriptor inputMethodDescriptor = ((InputMethodSupport)toolkit).getInputMethodAdapterDescriptor();
/* 111 */         if (inputMethodDescriptor != null) {
/* 112 */           this.hostAdapterLocator = new InputMethodLocator(inputMethodDescriptor, null, null);
/*     */         }
/*     */       } 
/* 115 */     } catch (AWTException aWTException) {}
/*     */ 
/*     */ 
/*     */     
/* 119 */     this.javaInputMethodLocatorList = new Vector<>();
/* 120 */     initializeInputMethodLocatorList();
/*     */   }
/*     */   
/*     */   synchronized void initialize() {
/* 124 */     selectInputMethodMenuTitle = Toolkit.getProperty("AWT.InputMethodSelectionMenu", "Select Input Method");
/*     */     
/* 126 */     this.triggerMenuString = selectInputMethodMenuTitle;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/* 131 */     while (!hasMultipleInputMethods()) {
/*     */       try {
/* 133 */         synchronized (this) {
/* 134 */           wait();
/*     */         } 
/* 136 */       } catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 142 */       waitForChangeRequest();
/* 143 */       initializeInputMethodLocatorList();
/*     */       
/* 145 */       try { if (this.requestComponent != null) {
/* 146 */           showInputMethodMenuOnRequesterEDT(this.requestComponent);
/*     */           continue;
/*     */         } 
/* 149 */         EventQueue.invokeAndWait(new Runnable() {
/*     */               public void run() {
/* 151 */                 ExecutableInputMethodManager.this.showInputMethodMenu();
/*     */               }
/*     */             }); }
/*     */       
/* 155 */       catch (InterruptedException interruptedException) {  }
/* 156 */       catch (InvocationTargetException invocationTargetException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void showInputMethodMenuOnRequesterEDT(Component paramComponent) throws InterruptedException, InvocationTargetException {
/* 167 */     if (paramComponent == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     class AWTInvocationLock {};
/* 172 */     AWTInvocationLock aWTInvocationLock = new AWTInvocationLock();
/*     */     
/* 174 */     InvocationEvent invocationEvent = new InvocationEvent(paramComponent, new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 178 */             ExecutableInputMethodManager.this.showInputMethodMenu();
/*     */           }
/*     */         },  aWTInvocationLock, true);
/*     */ 
/*     */ 
/*     */     
/* 184 */     AppContext appContext = SunToolkit.targetToAppContext(paramComponent);
/* 185 */     synchronized (aWTInvocationLock) {
/* 186 */       SunToolkit.postEvent(appContext, invocationEvent);
/* 187 */       while (!invocationEvent.isDispatched()) {
/* 188 */         aWTInvocationLock.wait();
/*     */       }
/*     */     } 
/*     */     
/* 192 */     Throwable throwable = invocationEvent.getThrowable();
/* 193 */     if (throwable != null) {
/* 194 */       throw new InvocationTargetException(throwable);
/*     */     }
/*     */   }
/*     */   
/*     */   void setInputContext(InputContext paramInputContext) {
/* 199 */     if (this.currentInputContext == null || paramInputContext != null);
/*     */ 
/*     */ 
/*     */     
/* 203 */     this.currentInputContext = paramInputContext;
/*     */   }
/*     */   
/*     */   public synchronized void notifyChangeRequest(Component paramComponent) {
/* 207 */     if (!(paramComponent instanceof java.awt.Frame) && !(paramComponent instanceof java.awt.Dialog)) {
/*     */       return;
/*     */     }
/*     */     
/* 211 */     if (this.requestComponent != null) {
/*     */       return;
/*     */     }
/* 214 */     this.requestComponent = paramComponent;
/* 215 */     notify();
/*     */   }
/*     */   
/*     */   public synchronized void notifyChangeRequestByHotKey(Component paramComponent) {
/* 219 */     while (!(paramComponent instanceof java.awt.Frame) && !(paramComponent instanceof java.awt.Dialog)) {
/* 220 */       if (paramComponent == null) {
/*     */         return;
/*     */       }
/*     */       
/* 224 */       paramComponent = paramComponent.getParent();
/*     */     } 
/*     */     
/* 227 */     notifyChangeRequest(paramComponent);
/*     */   }
/*     */   
/*     */   public String getTriggerMenuString() {
/* 231 */     return this.triggerMenuString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hasMultipleInputMethods() {
/* 238 */     return ((this.hostAdapterLocator != null && this.javaInputMethodCount > 0) || this.javaInputMethodCount > 1);
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void waitForChangeRequest() {
/*     */     try {
/* 244 */       while (this.requestComponent == null) {
/* 245 */         wait();
/*     */       }
/* 247 */     } catch (InterruptedException interruptedException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializeInputMethodLocatorList() {
/* 256 */     synchronized (this.javaInputMethodLocatorList) {
/* 257 */       this.javaInputMethodLocatorList.clear();
/*     */       try {
/* 259 */         AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */             {
/*     */               public Object run() {
/* 262 */                 for (InputMethodDescriptor inputMethodDescriptor : ServiceLoader.<InputMethodDescriptor>loadInstalled(InputMethodDescriptor.class)) {
/* 263 */                   ClassLoader classLoader = inputMethodDescriptor.getClass().getClassLoader();
/* 264 */                   ExecutableInputMethodManager.this.javaInputMethodLocatorList.add(new InputMethodLocator(inputMethodDescriptor, classLoader, null));
/*     */                 } 
/* 266 */                 return null;
/*     */               }
/*     */             });
/* 269 */       } catch (PrivilegedActionException privilegedActionException) {
/* 270 */         privilegedActionException.printStackTrace();
/*     */       } 
/* 272 */       this.javaInputMethodCount = this.javaInputMethodLocatorList.size();
/*     */     } 
/*     */     
/* 275 */     if (hasMultipleInputMethods()) {
/*     */       
/* 277 */       if (this.userRoot == null) {
/* 278 */         this.userRoot = getUserRoot();
/*     */       }
/*     */     } else {
/*     */       
/* 282 */       this.triggerMenuString = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void showInputMethodMenu() {
/* 288 */     if (!hasMultipleInputMethods()) {
/* 289 */       this.requestComponent = null;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 294 */     this.selectionMenu = InputMethodPopupMenu.getInstance(this.requestComponent, selectInputMethodMenuTitle);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 299 */     this.selectionMenu.removeAll();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 304 */     String str = getCurrentSelection();
/*     */ 
/*     */     
/* 307 */     if (this.hostAdapterLocator != null) {
/* 308 */       this.selectionMenu.addOneInputMethodToMenu(this.hostAdapterLocator, str);
/* 309 */       this.selectionMenu.addSeparator();
/*     */     } 
/*     */ 
/*     */     
/* 313 */     for (byte b = 0; b < this.javaInputMethodLocatorList.size(); b++) {
/* 314 */       InputMethodLocator inputMethodLocator = this.javaInputMethodLocatorList.get(b);
/* 315 */       this.selectionMenu.addOneInputMethodToMenu(inputMethodLocator, str);
/*     */     } 
/*     */     
/* 318 */     synchronized (this) {
/* 319 */       this.selectionMenu.addToComponent(this.requestComponent);
/* 320 */       this.requestInputContext = this.currentInputContext;
/* 321 */       this.selectionMenu.show(this.requestComponent, 60, 80);
/* 322 */       this.requestComponent = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getCurrentSelection() {
/* 327 */     InputContext inputContext = this.currentInputContext;
/* 328 */     if (inputContext != null) {
/* 329 */       InputMethodLocator inputMethodLocator = inputContext.getInputMethodLocator();
/* 330 */       if (inputMethodLocator != null) {
/* 331 */         return inputMethodLocator.getActionCommandString();
/*     */       }
/*     */     } 
/* 334 */     return null;
/*     */   }
/*     */   
/*     */   synchronized void changeInputMethod(String paramString) {
/* 338 */     InputMethodLocator inputMethodLocator = null;
/*     */     
/* 340 */     String str1 = paramString;
/* 341 */     String str2 = null;
/* 342 */     int i = paramString.indexOf('\n');
/* 343 */     if (i != -1) {
/* 344 */       str2 = paramString.substring(i + 1);
/* 345 */       str1 = paramString.substring(0, i);
/*     */     } 
/* 347 */     if (this.hostAdapterLocator.getActionCommandString().equals(str1)) {
/* 348 */       inputMethodLocator = this.hostAdapterLocator;
/*     */     } else {
/* 350 */       for (byte b = 0; b < this.javaInputMethodLocatorList.size(); b++) {
/* 351 */         InputMethodLocator inputMethodLocator1 = this.javaInputMethodLocatorList.get(b);
/* 352 */         String str = inputMethodLocator1.getActionCommandString();
/* 353 */         if (str.equals(str1)) {
/* 354 */           inputMethodLocator = inputMethodLocator1;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 360 */     if (inputMethodLocator != null && str2 != null) {
/* 361 */       String str3 = "", str4 = "", str5 = "";
/* 362 */       int j = str2.indexOf('_');
/* 363 */       if (j == -1) {
/* 364 */         str3 = str2;
/*     */       } else {
/* 366 */         str3 = str2.substring(0, j);
/* 367 */         int k = j + 1;
/* 368 */         j = str2.indexOf('_', k);
/* 369 */         if (j == -1) {
/* 370 */           str4 = str2.substring(k);
/*     */         } else {
/* 372 */           str4 = str2.substring(k, j);
/* 373 */           str5 = str2.substring(j + 1);
/*     */         } 
/*     */       } 
/* 376 */       Locale locale = new Locale(str3, str4, str5);
/* 377 */       inputMethodLocator = inputMethodLocator.deriveLocator(locale);
/*     */     } 
/*     */     
/* 380 */     if (inputMethodLocator == null) {
/*     */       return;
/*     */     }
/*     */     
/* 384 */     if (this.requestInputContext != null) {
/* 385 */       this.requestInputContext.changeInputMethod(inputMethodLocator);
/* 386 */       this.requestInputContext = null;
/*     */ 
/*     */       
/* 389 */       putPreferredInputMethod(inputMethodLocator);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   InputMethodLocator findInputMethod(Locale paramLocale) {
/* 395 */     InputMethodLocator inputMethodLocator = getPreferredInputMethod(paramLocale);
/* 396 */     if (inputMethodLocator != null) {
/* 397 */       return inputMethodLocator;
/*     */     }
/*     */     
/* 400 */     if (this.hostAdapterLocator != null && this.hostAdapterLocator.isLocaleAvailable(paramLocale)) {
/* 401 */       return this.hostAdapterLocator.deriveLocator(paramLocale);
/*     */     }
/*     */ 
/*     */     
/* 405 */     initializeInputMethodLocatorList();
/*     */     
/* 407 */     for (byte b = 0; b < this.javaInputMethodLocatorList.size(); b++) {
/* 408 */       InputMethodLocator inputMethodLocator1 = this.javaInputMethodLocatorList.get(b);
/* 409 */       if (inputMethodLocator1.isLocaleAvailable(paramLocale)) {
/* 410 */         return inputMethodLocator1.deriveLocator(paramLocale);
/*     */       }
/*     */     } 
/* 413 */     return null;
/*     */   }
/*     */   
/*     */   Locale getDefaultKeyboardLocale() {
/* 417 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 418 */     if (toolkit instanceof InputMethodSupport) {
/* 419 */       return ((InputMethodSupport)toolkit).getDefaultKeyboardLocale();
/*     */     }
/* 421 */     return Locale.getDefault();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized InputMethodLocator getPreferredInputMethod(Locale paramLocale) {
/* 432 */     InputMethodLocator inputMethodLocator = null;
/*     */     
/* 434 */     if (!hasMultipleInputMethods())
/*     */     {
/* 436 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 440 */     inputMethodLocator = this.preferredLocatorCache.get(paramLocale.toString().intern());
/* 441 */     if (inputMethodLocator != null) {
/* 442 */       return inputMethodLocator;
/*     */     }
/*     */ 
/*     */     
/* 446 */     String str1 = findPreferredInputMethodNode(paramLocale);
/* 447 */     String str2 = readPreferredInputMethod(str1);
/*     */ 
/*     */ 
/*     */     
/* 451 */     if (str2 != null) {
/*     */       
/* 453 */       if (this.hostAdapterLocator != null && this.hostAdapterLocator
/* 454 */         .getDescriptor().getClass().getName().equals(str2)) {
/* 455 */         Locale locale = getAdvertisedLocale(this.hostAdapterLocator, paramLocale);
/* 456 */         if (locale != null) {
/* 457 */           inputMethodLocator = this.hostAdapterLocator.deriveLocator(locale);
/* 458 */           this.preferredLocatorCache.put(paramLocale.toString().intern(), inputMethodLocator);
/*     */         } 
/* 460 */         return inputMethodLocator;
/*     */       } 
/*     */       
/* 463 */       for (byte b = 0; b < this.javaInputMethodLocatorList.size(); b++) {
/* 464 */         InputMethodLocator inputMethodLocator1 = this.javaInputMethodLocatorList.get(b);
/* 465 */         InputMethodDescriptor inputMethodDescriptor = inputMethodLocator1.getDescriptor();
/* 466 */         if (inputMethodDescriptor.getClass().getName().equals(str2)) {
/* 467 */           Locale locale = getAdvertisedLocale(inputMethodLocator1, paramLocale);
/* 468 */           if (locale != null) {
/* 469 */             inputMethodLocator = inputMethodLocator1.deriveLocator(locale);
/* 470 */             this.preferredLocatorCache.put(paramLocale.toString().intern(), inputMethodLocator);
/*     */           } 
/* 472 */           return inputMethodLocator;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 477 */       writePreferredInputMethod(str1, null);
/*     */     } 
/*     */     
/* 480 */     return null;
/*     */   }
/*     */   
/*     */   private String findPreferredInputMethodNode(Locale paramLocale) {
/* 484 */     if (this.userRoot == null) {
/* 485 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 489 */     String str = "/sun/awt/im/preferredInputMethod/" + createLocalePath(paramLocale);
/*     */ 
/*     */     
/* 492 */     while (!str.equals("/sun/awt/im/preferredInputMethod")) {
/*     */       try {
/* 494 */         if (this.userRoot.nodeExists(str) && 
/* 495 */           readPreferredInputMethod(str) != null) {
/* 496 */           return str;
/*     */         }
/*     */       }
/* 499 */       catch (BackingStoreException backingStoreException) {}
/*     */ 
/*     */ 
/*     */       
/* 503 */       str = str.substring(0, str.lastIndexOf('/'));
/*     */     } 
/*     */     
/* 506 */     return null;
/*     */   }
/*     */   
/*     */   private String readPreferredInputMethod(String paramString) {
/* 510 */     if (this.userRoot == null || paramString == null) {
/* 511 */       return null;
/*     */     }
/*     */     
/* 514 */     return this.userRoot.node(paramString).get("descriptor", null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void putPreferredInputMethod(InputMethodLocator paramInputMethodLocator) {
/* 524 */     InputMethodDescriptor inputMethodDescriptor = paramInputMethodLocator.getDescriptor();
/* 525 */     Locale locale = paramInputMethodLocator.getLocale();
/*     */     
/* 527 */     if (locale == null) {
/*     */       
/*     */       try {
/* 530 */         Locale[] arrayOfLocale = inputMethodDescriptor.getAvailableLocales();
/* 531 */         if (arrayOfLocale.length == 1) {
/* 532 */           locale = arrayOfLocale[0];
/*     */         } else {
/*     */           
/*     */           return;
/*     */         } 
/* 537 */       } catch (AWTException aWTException) {
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 545 */     if (locale.equals(Locale.JAPAN)) {
/* 546 */       locale = Locale.JAPANESE;
/*     */     }
/* 548 */     if (locale.equals(Locale.KOREA)) {
/* 549 */       locale = Locale.KOREAN;
/*     */     }
/* 551 */     if (locale.equals(new Locale("th", "TH"))) {
/* 552 */       locale = new Locale("th");
/*     */     }
/*     */ 
/*     */     
/* 556 */     String str = "/sun/awt/im/preferredInputMethod/" + createLocalePath(locale);
/*     */ 
/*     */     
/* 559 */     writePreferredInputMethod(str, inputMethodDescriptor.getClass().getName());
/* 560 */     this.preferredLocatorCache.put(locale.toString().intern(), paramInputMethodLocator
/* 561 */         .deriveLocator(locale));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String createLocalePath(Locale paramLocale) {
/* 567 */     String str1 = paramLocale.getLanguage();
/* 568 */     String str2 = paramLocale.getCountry();
/* 569 */     String str3 = paramLocale.getVariant();
/* 570 */     String str4 = null;
/* 571 */     if (!str3.equals("")) {
/* 572 */       str4 = "_" + str1 + "/_" + str2 + "/_" + str3;
/* 573 */     } else if (!str2.equals("")) {
/* 574 */       str4 = "_" + str1 + "/_" + str2;
/*     */     } else {
/* 576 */       str4 = "_" + str1;
/*     */     } 
/*     */     
/* 579 */     return str4;
/*     */   }
/*     */   
/*     */   private void writePreferredInputMethod(String paramString1, String paramString2) {
/* 583 */     if (this.userRoot != null) {
/* 584 */       Preferences preferences = this.userRoot.node(paramString1);
/*     */ 
/*     */       
/* 587 */       if (paramString2 != null) {
/* 588 */         preferences.put("descriptor", paramString2);
/*     */       } else {
/* 590 */         preferences.remove("descriptor");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private Preferences getUserRoot() {
/* 596 */     return AccessController.<Preferences>doPrivileged(new PrivilegedAction<Preferences>() {
/*     */           public Preferences run() {
/* 598 */             return Preferences.userRoot();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private Locale getAdvertisedLocale(InputMethodLocator paramInputMethodLocator, Locale paramLocale) {
/* 604 */     Locale locale = null;
/*     */     
/* 606 */     if (paramInputMethodLocator.isLocaleAvailable(paramLocale)) {
/* 607 */       locale = paramLocale;
/* 608 */     } else if (paramLocale.getLanguage().equals("ja")) {
/*     */ 
/*     */       
/* 611 */       if (paramInputMethodLocator.isLocaleAvailable(Locale.JAPAN)) {
/* 612 */         locale = Locale.JAPAN;
/* 613 */       } else if (paramInputMethodLocator.isLocaleAvailable(Locale.JAPANESE)) {
/* 614 */         locale = Locale.JAPANESE;
/*     */       } 
/* 616 */     } else if (paramLocale.getLanguage().equals("ko")) {
/* 617 */       if (paramInputMethodLocator.isLocaleAvailable(Locale.KOREA)) {
/* 618 */         locale = Locale.KOREA;
/* 619 */       } else if (paramInputMethodLocator.isLocaleAvailable(Locale.KOREAN)) {
/* 620 */         locale = Locale.KOREAN;
/*     */       } 
/* 622 */     } else if (paramLocale.getLanguage().equals("th")) {
/* 623 */       if (paramInputMethodLocator.isLocaleAvailable(new Locale("th", "TH"))) {
/* 624 */         locale = new Locale("th", "TH");
/* 625 */       } else if (paramInputMethodLocator.isLocaleAvailable(new Locale("th"))) {
/* 626 */         locale = new Locale("th");
/*     */       } 
/*     */     } 
/*     */     
/* 630 */     return locale;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/im/ExecutableInputMethodManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */