/*    */ package javax.print.event;
/*    */ 
/*    */ import javax.print.DocPrintJob;
/*    */ import javax.print.attribute.AttributeSetUtilities;
/*    */ import javax.print.attribute.PrintJobAttributeSet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrintJobAttributeEvent
/*    */   extends PrintEvent
/*    */ {
/*    */   private static final long serialVersionUID = -6534469883874742101L;
/*    */   private PrintJobAttributeSet attributes;
/*    */   
/*    */   public PrintJobAttributeEvent(DocPrintJob paramDocPrintJob, PrintJobAttributeSet paramPrintJobAttributeSet) {
/* 53 */     super(paramDocPrintJob);
/*    */     
/* 55 */     this.attributes = AttributeSetUtilities.unmodifiableView(paramPrintJobAttributeSet);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DocPrintJob getPrintJob() {
/* 66 */     return (DocPrintJob)getSource();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrintJobAttributeSet getAttributes() {
/* 78 */     return this.attributes;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/event/PrintJobAttributeEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */