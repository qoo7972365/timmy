/*    */ package javax.print.event;
/*    */ 
/*    */ import javax.print.PrintService;
/*    */ import javax.print.attribute.AttributeSetUtilities;
/*    */ import javax.print.attribute.PrintServiceAttributeSet;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrintServiceAttributeEvent
/*    */   extends PrintEvent
/*    */ {
/*    */   private static final long serialVersionUID = -7565987018140326600L;
/*    */   private PrintServiceAttributeSet attributes;
/*    */   
/*    */   public PrintServiceAttributeEvent(PrintService paramPrintService, PrintServiceAttributeSet paramPrintServiceAttributeSet) {
/* 56 */     super(paramPrintService);
/* 57 */     this.attributes = AttributeSetUtilities.unmodifiableView(paramPrintServiceAttributeSet);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrintService getPrintService() {
/* 68 */     return (PrintService)getSource();
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
/*    */   
/*    */   public PrintServiceAttributeSet getAttributes() {
/* 81 */     return this.attributes;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/event/PrintServiceAttributeEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */