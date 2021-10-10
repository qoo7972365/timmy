/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.security.action.GetBooleanAction;
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
/*     */ public final class XErrorHandlerUtil
/*     */ {
/*  37 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XErrorHandlerUtil");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long display;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long saved_error_handler;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static volatile XErrorEvent saved_error;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static XErrorHandler current_error_handler;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private static boolean noisyAwtHandler = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.awt.noisyerrorhandler"))).booleanValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean initPassed;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void init(long paramLong) {
/*  83 */     SunToolkit.awtLock();
/*     */     try {
/*  85 */       if (!initPassed) {
/*  86 */         display = paramLong;
/*  87 */         saved_error_handler = XlibWrapper.SetToolkitErrorHandler();
/*  88 */         initPassed = true;
/*     */       } 
/*     */     } finally {
/*  91 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void WITH_XERROR_HANDLER(XErrorHandler paramXErrorHandler) {
/* 100 */     XSync();
/* 101 */     saved_error = null;
/* 102 */     current_error_handler = paramXErrorHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void RESTORE_XERROR_HANDLER() {
/* 111 */     XSync();
/* 112 */     current_error_handler = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int SAVED_XERROR_HANDLER(long paramLong, XErrorEvent paramXErrorEvent) {
/* 119 */     if (saved_error_handler != 0L);
/*     */ 
/*     */ 
/*     */     
/* 123 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 124 */       log.fine("Unhandled XErrorEvent: id=" + paramXErrorEvent
/* 125 */           .get_resourceid() + ", serial=" + paramXErrorEvent
/* 126 */           .get_serial() + ", ec=" + paramXErrorEvent
/* 127 */           .get_error_code() + ", rc=" + paramXErrorEvent
/* 128 */           .get_request_code() + ", mc=" + paramXErrorEvent
/* 129 */           .get_minor_code());
/*     */     }
/* 131 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int globalErrorHandler(long paramLong1, long paramLong2) {
/* 138 */     if (noisyAwtHandler) {
/* 139 */       XlibWrapper.PrintXErrorEvent(paramLong1, paramLong2);
/*     */     }
/* 141 */     XErrorEvent xErrorEvent = new XErrorEvent(paramLong2);
/* 142 */     saved_error = xErrorEvent;
/*     */     try {
/* 144 */       if (current_error_handler != null) {
/* 145 */         return current_error_handler.handleError(paramLong1, xErrorEvent);
/*     */       }
/* 147 */       return SAVED_XERROR_HANDLER(paramLong1, xErrorEvent);
/*     */     }
/* 149 */     catch (Throwable throwable) {
/* 150 */       log.fine("Error in GlobalErrorHandler", throwable);
/*     */       
/* 152 */       return 0;
/*     */     } 
/*     */   }
/*     */   private static void XSync() {
/* 156 */     SunToolkit.awtLock();
/*     */     try {
/* 158 */       XlibWrapper.XSync(display, 0);
/*     */     } finally {
/* 160 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XErrorHandlerUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */