/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ class XProtocol
/*     */ {
/*  33 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XProtocol");
/*     */   
/*  35 */   private Map<XAtom, XAtomList> atomToList = new HashMap<>();
/*  36 */   private Map<XAtom, Long> atomToAnchor = new HashMap<>();
/*     */ 
/*     */   
/*     */   volatile boolean firstCheck = true;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean checkProtocol(XAtom paramXAtom1, XAtom paramXAtom2) {
/*  44 */     XAtomList xAtomList = this.atomToList.get(paramXAtom1);
/*     */     
/*  46 */     if (xAtomList != null) {
/*  47 */       return xAtomList.contains(paramXAtom2);
/*     */     }
/*     */     
/*  50 */     xAtomList = paramXAtom1.getAtomListPropertyList(XToolkit.getDefaultRootWindow());
/*  51 */     this.atomToList.put(paramXAtom1, xAtomList);
/*     */     try {
/*  53 */       return xAtomList.contains(paramXAtom2);
/*     */     } finally {
/*  55 */       if (this.firstCheck) {
/*  56 */         this.firstCheck = false;
/*  57 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  58 */           log.fine("{0}:{1} supports {2}", new Object[] { this, paramXAtom1, xAtomList });
/*     */         }
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
/*     */   long checkAnchorImpl(XAtom paramXAtom, long paramLong) {
/*     */     long l1;
/*  75 */     XToolkit.awtLock();
/*     */     try {
/*  77 */       l1 = paramXAtom.get32Property(XToolkit.getDefaultRootWindow(), paramLong);
/*     */     } finally {
/*     */       
/*  80 */       XToolkit.awtUnlock();
/*     */     } 
/*  82 */     if (l1 == 0L) {
/*  83 */       return 0L;
/*     */     }
/*  85 */     long l2 = paramXAtom.get32Property(l1, paramLong);
/*  86 */     if (l2 != l1) {
/*  87 */       return 0L;
/*     */     }
/*  89 */     return l2;
/*     */   }
/*     */   public long checkAnchor(XAtom paramXAtom, long paramLong) {
/*  92 */     Long long_ = this.atomToAnchor.get(paramXAtom);
/*  93 */     if (long_ != null) {
/*  94 */       return long_.longValue();
/*     */     }
/*  96 */     long l = checkAnchorImpl(paramXAtom, paramLong);
/*  97 */     this.atomToAnchor.put(paramXAtom, Long.valueOf(l));
/*  98 */     return l;
/*     */   }
/*     */   public long checkAnchor(XAtom paramXAtom1, XAtom paramXAtom2) {
/* 101 */     return checkAnchor(paramXAtom1, paramXAtom2.getAtom());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XProtocol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */