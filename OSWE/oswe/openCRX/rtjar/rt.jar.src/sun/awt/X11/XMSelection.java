/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMSelection
/*     */ {
/*  58 */   private static PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XMSelection");
/*     */ 
/*     */   
/*     */   String selectionName;
/*     */ 
/*     */   
/*     */   Vector listeners;
/*     */ 
/*     */   
/*     */   XAtom[] atoms;
/*     */ 
/*     */   
/*     */   long[] owners;
/*     */ 
/*     */   
/*     */   long eventMask;
/*     */ 
/*     */   
/*     */   static int numScreens;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  81 */     long l = XToolkit.getDisplay();
/*  82 */     XToolkit.awtLock();
/*     */     try {
/*  84 */       numScreens = XlibWrapper.ScreenCount(l);
/*     */     } finally {
/*  86 */       XToolkit.awtUnlock();
/*     */     } 
/*  88 */   } static XAtom XA_MANAGER = XAtom.get("MANAGER"); static {
/*  89 */     for (byte b = 0; b < numScreens; b++)
/*  90 */       initScreen(l, b); 
/*     */   }
/*     */   
/*  93 */   static HashMap selectionMap = new HashMap<>();
/*     */ 
/*     */   
/*     */   static void initScreen(long paramLong, final int screen) {
/*  97 */     XToolkit.awtLock();
/*     */     try {
/*  99 */       long l = XlibWrapper.RootWindow(paramLong, screen);
/* 100 */       XWindowAttributes xWindowAttributes = new XWindowAttributes();
/*     */       try {
/* 102 */         XlibWrapper.XGetWindowAttributes(paramLong, l, xWindowAttributes.pData);
/* 103 */         XlibWrapper.XSelectInput(paramLong, l, 0x20000L | xWindowAttributes
/*     */             
/* 105 */             .get_your_event_mask());
/*     */       } finally {
/* 107 */         xWindowAttributes.dispose();
/*     */       } 
/* 109 */       XToolkit.addEventDispatcher(l, new XEventDispatcher()
/*     */           {
/*     */             public void dispatchEvent(XEvent param1XEvent) {
/* 112 */               XMSelection.processRootEvent(param1XEvent, screen);
/*     */             }
/*     */           });
/*     */     } finally {
/*     */       
/* 117 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumberOfScreens() {
/* 123 */     return numScreens;
/*     */   }
/*     */   
/*     */   void select(long paramLong) {
/* 127 */     this.eventMask = paramLong;
/* 128 */     for (byte b = 0; b < numScreens; b++) {
/* 129 */       selectPerScreen(b, paramLong);
/*     */     }
/*     */   }
/*     */   
/*     */   void resetOwner(long paramLong, final int screen) {
/* 134 */     XToolkit.awtLock();
/*     */     try {
/* 136 */       long l = XToolkit.getDisplay();
/* 137 */       synchronized (this) {
/* 138 */         setOwner(paramLong, screen);
/* 139 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 140 */           log.fine("New Selection Owner for screen " + screen + " = " + paramLong);
/*     */         }
/* 142 */         XlibWrapper.XSelectInput(l, paramLong, 0x20000L | this.eventMask);
/* 143 */         XToolkit.addEventDispatcher(paramLong, new XEventDispatcher()
/*     */             {
/*     */               public void dispatchEvent(XEvent param1XEvent) {
/* 146 */                 XMSelection.this.dispatchSelectionEvent(param1XEvent, screen);
/*     */               }
/*     */             });
/*     */       } 
/*     */     } finally {
/*     */       
/* 152 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   void selectPerScreen(final int screen, long paramLong) {
/* 157 */     XToolkit.awtLock();
/*     */     try {
/*     */       try {
/* 160 */         long l = XToolkit.getDisplay();
/* 161 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 162 */           log.fine("Grabbing XServer");
/*     */         }
/* 164 */         XlibWrapper.XGrabServer(l);
/*     */         
/* 166 */         synchronized (this) {
/* 167 */           String str = getName() + "_S" + screen;
/* 168 */           if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 169 */             log.fine("Screen = " + screen + " selection name = " + str);
/*     */           }
/* 171 */           XAtom xAtom = XAtom.get(str);
/* 172 */           selectionMap.put(Long.valueOf(xAtom.getAtom()), this);
/* 173 */           setAtom(xAtom, screen);
/* 174 */           long l1 = XlibWrapper.XGetSelectionOwner(l, xAtom.getAtom());
/* 175 */           if (l1 != 0L) {
/* 176 */             setOwner(l1, screen);
/* 177 */             if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 178 */               log.fine("Selection Owner for screen " + screen + " = " + l1);
/*     */             }
/* 180 */             XlibWrapper.XSelectInput(l, l1, 0x20000L | paramLong);
/* 181 */             XToolkit.addEventDispatcher(l1, new XEventDispatcher()
/*     */                 {
/*     */                   public void dispatchEvent(XEvent param1XEvent) {
/* 184 */                     XMSelection.this.dispatchSelectionEvent(param1XEvent, screen);
/*     */                   }
/*     */                 });
/*     */           }
/*     */         
/*     */         } 
/* 190 */       } catch (Exception exception) {
/* 191 */         exception.printStackTrace();
/*     */       } finally {
/*     */         
/* 194 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 195 */           log.fine("UnGrabbing XServer");
/*     */         }
/* 197 */         XlibWrapper.XUngrabServer(XToolkit.getDisplay());
/*     */       } 
/*     */     } finally {
/* 200 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean processClientMessage(XEvent paramXEvent, int paramInt) {
/* 206 */     XClientMessageEvent xClientMessageEvent = paramXEvent.get_xclient();
/* 207 */     if (xClientMessageEvent.get_message_type() == XA_MANAGER.getAtom()) {
/* 208 */       if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 209 */         log.fine("client messags = " + xClientMessageEvent);
/*     */       }
/* 211 */       long l1 = xClientMessageEvent.get_data(0);
/* 212 */       long l2 = xClientMessageEvent.get_data(1);
/* 213 */       long l3 = xClientMessageEvent.get_data(2);
/* 214 */       long l4 = xClientMessageEvent.get_data(3);
/*     */       
/* 216 */       XMSelection xMSelection = getInstance(l2);
/* 217 */       if (xMSelection != null) {
/* 218 */         xMSelection.resetOwner(l3, paramInt);
/* 219 */         xMSelection.dispatchOwnerChangedEvent(paramXEvent, paramInt, l3, l4, l1);
/*     */       } 
/*     */     } 
/* 222 */     return false;
/*     */   }
/*     */   
/*     */   static boolean processRootEvent(XEvent paramXEvent, int paramInt) {
/* 226 */     switch (paramXEvent.get_type()) {
/*     */       case 33:
/* 228 */         return processClientMessage(paramXEvent, paramInt);
/*     */     } 
/*     */ 
/*     */     
/* 232 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static XMSelection getInstance(long paramLong) {
/* 238 */     return (XMSelection)selectionMap.get(Long.valueOf(paramLong));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMSelection(String paramString) {
/* 247 */     this(paramString, 4194304L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMSelection(String paramString, long paramLong) {
/* 258 */     synchronized (this) {
/* 259 */       this.selectionName = paramString;
/* 260 */       this.atoms = new XAtom[getNumberOfScreens()];
/* 261 */       this.owners = new long[getNumberOfScreens()];
/*     */     } 
/* 263 */     select(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addSelectionListener(XMSelectionListener paramXMSelectionListener) {
/* 269 */     if (this.listeners == null) {
/* 270 */       this.listeners = new Vector();
/*     */     }
/* 272 */     this.listeners.add(paramXMSelectionListener);
/*     */   }
/*     */   
/*     */   public synchronized void removeSelectionListener(XMSelectionListener paramXMSelectionListener) {
/* 276 */     if (this.listeners != null) {
/* 277 */       this.listeners.remove(paramXMSelectionListener);
/*     */     }
/*     */   }
/*     */   
/*     */   synchronized Collection getListeners() {
/* 282 */     return this.listeners;
/*     */   }
/*     */   
/*     */   synchronized XAtom getAtom(int paramInt) {
/* 286 */     if (this.atoms != null) {
/* 287 */       return this.atoms[paramInt];
/*     */     }
/* 289 */     return null;
/*     */   }
/*     */   
/*     */   synchronized void setAtom(XAtom paramXAtom, int paramInt) {
/* 293 */     if (this.atoms != null) {
/* 294 */       this.atoms[paramInt] = paramXAtom;
/*     */     }
/*     */   }
/*     */   
/*     */   synchronized long getOwner(int paramInt) {
/* 299 */     if (this.owners != null) {
/* 300 */       return this.owners[paramInt];
/*     */     }
/* 302 */     return 0L;
/*     */   }
/*     */   
/*     */   synchronized void setOwner(long paramLong, int paramInt) {
/* 306 */     if (this.owners != null) {
/* 307 */       this.owners[paramInt] = paramLong;
/*     */     }
/*     */   }
/*     */   
/*     */   synchronized String getName() {
/* 312 */     return this.selectionName;
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized void dispatchSelectionChanged(XPropertyEvent paramXPropertyEvent, int paramInt) {
/* 317 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 318 */       log.fine("Selection Changed : Screen = " + paramInt + "Event =" + paramXPropertyEvent);
/*     */     }
/* 320 */     if (this.listeners != null) {
/* 321 */       Iterator<XMSelectionListener> iterator = this.listeners.iterator();
/* 322 */       while (iterator.hasNext()) {
/* 323 */         XMSelectionListener xMSelectionListener = iterator.next();
/* 324 */         xMSelectionListener.selectionChanged(paramInt, this, paramXPropertyEvent.get_window(), paramXPropertyEvent);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   synchronized void dispatchOwnerDeath(XDestroyWindowEvent paramXDestroyWindowEvent, int paramInt) {
/* 330 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 331 */       log.fine("Owner dead : Screen = " + paramInt + "Event =" + paramXDestroyWindowEvent);
/*     */     }
/* 333 */     if (this.listeners != null) {
/* 334 */       Iterator<XMSelectionListener> iterator = this.listeners.iterator();
/* 335 */       while (iterator.hasNext()) {
/* 336 */         XMSelectionListener xMSelectionListener = iterator.next();
/* 337 */         xMSelectionListener.ownerDeath(paramInt, this, paramXDestroyWindowEvent.get_window());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void dispatchSelectionEvent(XEvent paramXEvent, int paramInt) {
/* 344 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 345 */       log.fine("Event =" + paramXEvent);
/*     */     }
/* 347 */     if (paramXEvent.get_type() == 17) {
/* 348 */       XDestroyWindowEvent xDestroyWindowEvent = paramXEvent.get_xdestroywindow();
/* 349 */       dispatchOwnerDeath(xDestroyWindowEvent, paramInt);
/*     */     }
/* 351 */     else if (paramXEvent.get_type() == 28) {
/* 352 */       XPropertyEvent xPropertyEvent = paramXEvent.get_xproperty();
/* 353 */       dispatchSelectionChanged(xPropertyEvent, paramInt);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized void dispatchOwnerChangedEvent(XEvent paramXEvent, int paramInt, long paramLong1, long paramLong2, long paramLong3) {
/* 359 */     if (this.listeners != null) {
/* 360 */       Iterator<XMSelectionListener> iterator = this.listeners.iterator();
/* 361 */       while (iterator.hasNext()) {
/* 362 */         XMSelectionListener xMSelectionListener = iterator.next();
/* 363 */         xMSelectionListener.ownerChanged(paramInt, this, paramLong1, paramLong2, paramLong3);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XMSelection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */