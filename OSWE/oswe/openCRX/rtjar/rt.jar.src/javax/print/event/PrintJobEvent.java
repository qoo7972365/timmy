/*     */ package javax.print.event;
/*     */ 
/*     */ import javax.print.DocPrintJob;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrintJobEvent
/*     */   extends PrintEvent
/*     */ {
/*     */   private static final long serialVersionUID = -1711656903622072997L;
/*     */   private int reason;
/*     */   public static final int JOB_CANCELED = 101;
/*     */   public static final int JOB_COMPLETE = 102;
/*     */   public static final int JOB_FAILED = 103;
/*     */   public static final int REQUIRES_ATTENTION = 104;
/*     */   public static final int NO_MORE_EVENTS = 105;
/*     */   public static final int DATA_TRANSFER_COMPLETE = 106;
/*     */   
/*     */   public PrintJobEvent(DocPrintJob paramDocPrintJob, int paramInt) {
/*  99 */     super(paramDocPrintJob);
/* 100 */     this.reason = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPrintEventType() {
/* 108 */     return this.reason;
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
/*     */   public DocPrintJob getPrintJob() {
/* 121 */     return (DocPrintJob)getSource();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/event/PrintJobEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */