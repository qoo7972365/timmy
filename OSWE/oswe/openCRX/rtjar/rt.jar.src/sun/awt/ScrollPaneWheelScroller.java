/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.Adjustable;
/*     */ import java.awt.Insets;
/*     */ import java.awt.ScrollPane;
/*     */ import java.awt.event.MouseWheelEvent;
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
/*     */ public abstract class ScrollPaneWheelScroller
/*     */ {
/*  42 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.ScrollPaneWheelScroller");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void handleWheelScrolling(ScrollPane paramScrollPane, MouseWheelEvent paramMouseWheelEvent) {
/*  50 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/*  51 */       log.finer("x = " + paramMouseWheelEvent.getX() + ", y = " + paramMouseWheelEvent.getY() + ", src is " + paramMouseWheelEvent.getSource());
/*     */     }
/*  53 */     int i = 0;
/*     */     
/*  55 */     if (paramScrollPane != null && paramMouseWheelEvent.getScrollAmount() != 0) {
/*  56 */       Adjustable adjustable = getAdjustableToScroll(paramScrollPane);
/*  57 */       if (adjustable != null) {
/*  58 */         i = getIncrementFromAdjustable(adjustable, paramMouseWheelEvent);
/*  59 */         if (log.isLoggable(PlatformLogger.Level.FINER)) {
/*  60 */           log.finer("increment from adjustable(" + adjustable.getClass() + ") : " + i);
/*     */         }
/*  62 */         scrollAdjustable(adjustable, i);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Adjustable getAdjustableToScroll(ScrollPane paramScrollPane) {
/*  72 */     int i = paramScrollPane.getScrollbarDisplayPolicy();
/*     */ 
/*     */     
/*  75 */     if (i == 1 || i == 2) {
/*     */       
/*  77 */       if (log.isLoggable(PlatformLogger.Level.FINER)) {
/*  78 */         log.finer("using vertical scrolling due to scrollbar policy");
/*     */       }
/*  80 */       return paramScrollPane.getVAdjustable();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  85 */     Insets insets = paramScrollPane.getInsets();
/*  86 */     int j = paramScrollPane.getVScrollbarWidth();
/*     */     
/*  88 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/*  89 */       log.finer("insets: l = " + insets.left + ", r = " + insets.right + ", t = " + insets.top + ", b = " + insets.bottom);
/*     */       
/*  91 */       log.finer("vertScrollWidth = " + j);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  96 */     if (insets.right >= j) {
/*  97 */       if (log.isLoggable(PlatformLogger.Level.FINER)) {
/*  98 */         log.finer("using vertical scrolling because scrollbar is present");
/*     */       }
/* 100 */       return paramScrollPane.getVAdjustable();
/*     */     } 
/*     */     
/* 103 */     int k = paramScrollPane.getHScrollbarHeight();
/* 104 */     if (insets.bottom >= k) {
/* 105 */       if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 106 */         log.finer("using horiz scrolling because scrollbar is present");
/*     */       }
/* 108 */       return paramScrollPane.getHAdjustable();
/*     */     } 
/*     */     
/* 111 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 112 */       log.finer("using NO scrollbar becsause neither is present");
/*     */     }
/* 114 */     return null;
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
/*     */   public static int getIncrementFromAdjustable(Adjustable paramAdjustable, MouseWheelEvent paramMouseWheelEvent) {
/* 127 */     if (log.isLoggable(PlatformLogger.Level.FINE) && 
/* 128 */       paramAdjustable == null) {
/* 129 */       log.fine("Assertion (adj != null) failed");
/*     */     }
/*     */ 
/*     */     
/* 133 */     int i = 0;
/*     */     
/* 135 */     if (paramMouseWheelEvent.getScrollType() == 0) {
/* 136 */       i = paramMouseWheelEvent.getUnitsToScroll() * paramAdjustable.getUnitIncrement();
/*     */     }
/* 138 */     else if (paramMouseWheelEvent.getScrollType() == 1) {
/* 139 */       i = paramAdjustable.getBlockIncrement() * paramMouseWheelEvent.getWheelRotation();
/*     */     } 
/* 141 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void scrollAdjustable(Adjustable paramAdjustable, int paramInt) {
/* 149 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 150 */       if (paramAdjustable == null) {
/* 151 */         log.fine("Assertion (adj != null) failed");
/*     */       }
/* 153 */       if (paramInt == 0) {
/* 154 */         log.fine("Assertion (amount != 0) failed");
/*     */       }
/*     */     } 
/*     */     
/* 158 */     int i = paramAdjustable.getValue();
/* 159 */     int j = paramAdjustable.getMaximum() - paramAdjustable.getVisibleAmount();
/* 160 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 161 */       log.finer("doScrolling by " + paramInt);
/*     */     }
/*     */     
/* 164 */     if (paramInt > 0 && i < j) {
/*     */       
/* 166 */       if (i + paramInt < j) {
/* 167 */         paramAdjustable.setValue(i + paramInt);
/*     */         
/*     */         return;
/*     */       } 
/* 171 */       paramAdjustable.setValue(j);
/*     */       
/*     */       return;
/*     */     } 
/* 175 */     if (paramInt < 0 && i > paramAdjustable.getMinimum()) {
/*     */       
/* 177 */       if (i + paramInt > paramAdjustable.getMinimum()) {
/* 178 */         paramAdjustable.setValue(i + paramInt);
/*     */         
/*     */         return;
/*     */       } 
/* 182 */       paramAdjustable.setValue(paramAdjustable.getMinimum());
/*     */       return;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/ScrollPaneWheelScroller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */