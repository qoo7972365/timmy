/*     */ package javax.print.attribute.standard;
/*     */ 
/*     */ import java.util.AbstractSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import javax.print.attribute.Attribute;
/*     */ import javax.print.attribute.PrintServiceAttribute;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PrinterStateReasons
/*     */   extends HashMap<PrinterStateReason, Severity>
/*     */   implements PrintServiceAttribute
/*     */ {
/*     */   private static final long serialVersionUID = -3731791085163619457L;
/*     */   
/*     */   public PrinterStateReasons() {}
/*     */   
/*     */   public PrinterStateReasons(int paramInt) {
/* 109 */     super(paramInt);
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
/*     */   public PrinterStateReasons(int paramInt, float paramFloat) {
/* 123 */     super(paramInt, paramFloat);
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
/*     */   public PrinterStateReasons(Map<PrinterStateReason, Severity> paramMap) {
/* 146 */     this();
/* 147 */     for (Map.Entry<PrinterStateReason, Severity> entry : paramMap.entrySet()) {
/* 148 */       put((PrinterStateReason)entry.getKey(), (Severity)entry.getValue());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Severity put(PrinterStateReason paramPrinterStateReason, Severity paramSeverity) {
/* 177 */     if (paramPrinterStateReason == null) {
/* 178 */       throw new NullPointerException("reason is null");
/*     */     }
/* 180 */     if (paramSeverity == null) {
/* 181 */       throw new NullPointerException("severity is null");
/*     */     }
/* 183 */     return super.put(paramPrinterStateReason, paramSeverity);
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
/*     */   public final Class<? extends Attribute> getCategory() {
/* 197 */     return (Class)PrinterStateReasons.class;
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
/*     */   public final String getName() {
/* 210 */     return "printer-state-reasons";
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
/*     */   public Set<PrinterStateReason> printerStateReasonSet(Severity paramSeverity) {
/* 235 */     if (paramSeverity == null) {
/* 236 */       throw new NullPointerException("severity is null");
/*     */     }
/* 238 */     return new PrinterStateReasonSet(paramSeverity, entrySet());
/*     */   }
/*     */   
/*     */   private class PrinterStateReasonSet
/*     */     extends AbstractSet<PrinterStateReason>
/*     */   {
/*     */     private Severity mySeverity;
/*     */     private Set myEntrySet;
/*     */     
/*     */     public PrinterStateReasonSet(Severity param1Severity, Set param1Set) {
/* 248 */       this.mySeverity = param1Severity;
/* 249 */       this.myEntrySet = param1Set;
/*     */     }
/*     */     
/*     */     public int size() {
/* 253 */       byte b = 0;
/* 254 */       Iterator iterator = iterator();
/* 255 */       while (iterator.hasNext()) {
/* 256 */         iterator.next();
/* 257 */         b++;
/*     */       } 
/* 259 */       return b;
/*     */     }
/*     */     
/*     */     public Iterator iterator() {
/* 263 */       return new PrinterStateReasons.PrinterStateReasonSetIterator(this.mySeverity, this.myEntrySet
/* 264 */           .iterator());
/*     */     }
/*     */   }
/*     */   
/*     */   private class PrinterStateReasonSetIterator
/*     */     implements Iterator {
/*     */     private Severity mySeverity;
/*     */     private Iterator myIterator;
/*     */     private Map.Entry myEntry;
/*     */     
/*     */     public PrinterStateReasonSetIterator(Severity param1Severity, Iterator param1Iterator) {
/* 275 */       this.mySeverity = param1Severity;
/* 276 */       this.myIterator = param1Iterator;
/* 277 */       goToNext();
/*     */     }
/*     */     
/*     */     private void goToNext() {
/* 281 */       this.myEntry = null;
/* 282 */       while (this.myEntry == null && this.myIterator.hasNext()) {
/* 283 */         this.myEntry = this.myIterator.next();
/* 284 */         if ((Severity)this.myEntry.getValue() != this.mySeverity) {
/* 285 */           this.myEntry = null;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 291 */       return (this.myEntry != null);
/*     */     }
/*     */     
/*     */     public Object next() {
/* 295 */       if (this.myEntry == null) {
/* 296 */         throw new NoSuchElementException();
/*     */       }
/* 298 */       Object object = this.myEntry.getKey();
/* 299 */       goToNext();
/* 300 */       return object;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 304 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/attribute/standard/PrinterStateReasons.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */