/*    */ package sun.print;
/*    */ 
/*    */ import java.awt.Window;
/*    */ import java.awt.print.PrinterJob;
/*    */ import javax.print.PrintService;
/*    */ import javax.print.attribute.PrintRequestAttributeSet;
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
/*    */ public abstract class DocumentPropertiesUI
/*    */ {
/*    */   public static final int DOCUMENTPROPERTIES_ROLE = 199;
/* 46 */   public static final String DOCPROPERTIESCLASSNAME = DocumentPropertiesUI.class.getName();
/*    */   
/*    */   public abstract PrintRequestAttributeSet showDocumentProperties(PrinterJob paramPrinterJob, Window paramWindow, PrintService paramPrintService, PrintRequestAttributeSet paramPrintRequestAttributeSet);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/DocumentPropertiesUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */