/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Point;
/*     */ import java.awt.Toolkit;
/*     */ import java.lang.ref.WeakReference;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.GlobalCursorManager;
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
/*     */ public final class XGlobalCursorManager
/*     */   extends GlobalCursorManager
/*     */ {
/*     */   private WeakReference<Component> nativeContainer;
/*     */   private static XGlobalCursorManager manager;
/*     */   
/*     */   static GlobalCursorManager getCursorManager() {
/*  49 */     if (manager == null) {
/*  50 */       manager = new XGlobalCursorManager();
/*     */     }
/*  52 */     return manager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void nativeUpdateCursor(Component paramComponent) {
/*  60 */     getCursorManager().updateCursorLater(paramComponent);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setCursor(Component paramComponent, Cursor paramCursor, boolean paramBoolean) {
/*  65 */     if (paramComponent == null) {
/*     */       return;
/*     */     }
/*     */     
/*  69 */     Cursor cursor = paramBoolean ? paramCursor : getCapableCursor(paramComponent);
/*     */     
/*  71 */     Component component = null;
/*  72 */     if (paramBoolean) {
/*  73 */       synchronized (this) {
/*  74 */         component = this.nativeContainer.get();
/*     */       } 
/*     */     } else {
/*  77 */       component = SunToolkit.getHeavyweightComponent(paramComponent);
/*     */     } 
/*     */     
/*  80 */     if (component != null) {
/*  81 */       XComponentPeer xComponentPeer = (XComponentPeer)AWTAccessor.getComponentAccessor().getPeer(component);
/*  82 */       if (xComponentPeer instanceof XComponentPeer) {
/*  83 */         synchronized (this) {
/*  84 */           this.nativeContainer = new WeakReference<>(component);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*  89 */         ((XComponentPeer)xComponentPeer).pSetCursor(cursor, false);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  94 */         updateGrabbedCursor(cursor);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void updateGrabbedCursor(Cursor paramCursor) {
/* 104 */     XBaseWindow xBaseWindow = XAwtState.getGrabWindow();
/* 105 */     if (xBaseWindow instanceof XWindowPeer) {
/* 106 */       XWindowPeer xWindowPeer = (XWindowPeer)xBaseWindow;
/* 107 */       xWindowPeer.pSetCursor(paramCursor);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateCursorOutOfJava() {
/* 115 */     updateGrabbedCursor(Cursor.getPredefinedCursor(0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getCursorPos(Point paramPoint) {
/* 120 */     if (!((XToolkit)Toolkit.getDefaultToolkit()).getLastCursorPos(paramPoint)) {
/* 121 */       XToolkit.awtLock();
/*     */       try {
/* 123 */         long l1 = XToolkit.getDisplay();
/* 124 */         long l2 = XlibWrapper.RootWindow(l1, 
/* 125 */             XlibWrapper.DefaultScreen(l1));
/*     */         
/* 127 */         XlibWrapper.XQueryPointer(l1, l2, XlibWrapper.larg1, XlibWrapper.larg2, XlibWrapper.larg3, XlibWrapper.larg4, XlibWrapper.larg5, XlibWrapper.larg6, XlibWrapper.larg7);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 136 */         paramPoint.x = XlibWrapper.unsafe.getInt(XlibWrapper.larg3);
/* 137 */         paramPoint.y = XlibWrapper.unsafe.getInt(XlibWrapper.larg4);
/*     */       } finally {
/* 139 */         XToolkit.awtUnlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   protected Component findHeavyweightUnderCursor() {
/* 144 */     return XAwtState.getComponentMouseEntered();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Point getLocationOnScreen(Component paramComponent) {
/* 151 */     return paramComponent.getLocationOnScreen();
/*     */   }
/*     */   
/*     */   protected Component findHeavyweightUnderCursor(boolean paramBoolean) {
/* 155 */     return findHeavyweightUnderCursor();
/*     */   }
/*     */   
/*     */   private Cursor getCapableCursor(Component paramComponent) {
/* 159 */     AWTAccessor.ComponentAccessor componentAccessor = AWTAccessor.getComponentAccessor();
/*     */     
/* 161 */     Component component = paramComponent;
/* 162 */     while (component != null && !(component instanceof java.awt.Window) && componentAccessor
/* 163 */       .isEnabled(component) && componentAccessor
/* 164 */       .isVisible(component) && componentAccessor
/* 165 */       .isDisplayable(component))
/*     */     {
/* 167 */       component = componentAccessor.getParent(component);
/*     */     }
/* 169 */     if (component instanceof java.awt.Window)
/* 170 */       return (componentAccessor.isEnabled(component) && componentAccessor
/* 171 */         .isVisible(component) && componentAccessor
/* 172 */         .isDisplayable(component) && componentAccessor
/* 173 */         .isEnabled(paramComponent)) ? componentAccessor
/*     */         
/* 175 */         .getCursor(paramComponent) : 
/*     */         
/* 177 */         Cursor.getPredefinedCursor(0); 
/* 178 */     if (component == null) {
/* 179 */       return null;
/*     */     }
/* 181 */     return getCapableCursor(componentAccessor.getParent(component));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long getCursor(Cursor paramCursor) {
/* 188 */     long l = 0L;
/* 189 */     int i = 0;
/*     */     try {
/* 191 */       l = AWTAccessor.getCursorAccessor().getPData(paramCursor);
/* 192 */       i = AWTAccessor.getCursorAccessor().getType(paramCursor);
/*     */     }
/* 194 */     catch (Exception exception) {
/*     */       
/* 196 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 199 */     if (l != 0L) return l;
/*     */     
/* 201 */     char c = Character.MIN_VALUE;
/* 202 */     switch (i) {
/*     */       case 0:
/* 204 */         c = 'D';
/*     */         break;
/*     */       case 1:
/* 207 */         c = '"';
/*     */         break;
/*     */       case 2:
/* 210 */         c = '';
/*     */         break;
/*     */       case 3:
/* 213 */         c = '';
/*     */         break;
/*     */       case 4:
/* 216 */         c = '\f';
/*     */         break;
/*     */       case 6:
/* 219 */         c = '';
/*     */         break;
/*     */       case 5:
/* 222 */         c = '\016';
/*     */         break;
/*     */       case 7:
/* 225 */         c = '';
/*     */         break;
/*     */       case 9:
/* 228 */         c = '\020';
/*     */         break;
/*     */       case 8:
/* 231 */         c = '';
/*     */         break;
/*     */       case 10:
/* 234 */         c = 'F';
/*     */         break;
/*     */       case 11:
/* 237 */         c = '`';
/*     */         break;
/*     */       case 12:
/* 240 */         c = '<';
/*     */         break;
/*     */       case 13:
/* 243 */         c = '4';
/*     */         break;
/*     */     } 
/*     */     
/* 247 */     XToolkit.awtLock();
/*     */     try {
/* 249 */       l = XlibWrapper.XCreateFontCursor(XToolkit.getDisplay(), c);
/*     */     } finally {
/*     */       
/* 252 */       XToolkit.awtUnlock();
/*     */     } 
/*     */     
/* 255 */     setPData(paramCursor, l);
/* 256 */     return l;
/*     */   }
/*     */ 
/*     */   
/*     */   static void setPData(Cursor paramCursor, long paramLong) {
/*     */     try {
/* 262 */       AWTAccessor.getCursorAccessor().setPData(paramCursor, paramLong);
/*     */     }
/* 264 */     catch (Exception exception) {
/*     */       
/* 266 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XGlobalCursorManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */