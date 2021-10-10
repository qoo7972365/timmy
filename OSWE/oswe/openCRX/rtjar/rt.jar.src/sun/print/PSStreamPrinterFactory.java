/*    */ package sun.print;
/*    */ 
/*    */ import java.io.OutputStream;
/*    */ import javax.print.DocFlavor;
/*    */ import javax.print.StreamPrintService;
/*    */ import javax.print.StreamPrintServiceFactory;
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
/*    */ public class PSStreamPrinterFactory
/*    */   extends StreamPrintServiceFactory
/*    */ {
/*    */   static final String psMimeType = "application/postscript";
/* 38 */   static final DocFlavor[] supportedDocFlavors = new DocFlavor[] { DocFlavor.SERVICE_FORMATTED.PAGEABLE, DocFlavor.SERVICE_FORMATTED.PRINTABLE, DocFlavor.BYTE_ARRAY.GIF, DocFlavor.INPUT_STREAM.GIF, DocFlavor.URL.GIF, DocFlavor.BYTE_ARRAY.JPEG, DocFlavor.INPUT_STREAM.JPEG, DocFlavor.URL.JPEG, DocFlavor.BYTE_ARRAY.PNG, DocFlavor.INPUT_STREAM.PNG, DocFlavor.URL.PNG };
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
/*    */   public String getOutputFormat() {
/* 53 */     return "application/postscript";
/*    */   }
/*    */   
/*    */   public DocFlavor[] getSupportedDocFlavors() {
/* 57 */     return getFlavors();
/*    */   }
/*    */   
/*    */   static DocFlavor[] getFlavors() {
/* 61 */     DocFlavor[] arrayOfDocFlavor = new DocFlavor[supportedDocFlavors.length];
/* 62 */     System.arraycopy(supportedDocFlavors, 0, arrayOfDocFlavor, 0, arrayOfDocFlavor.length);
/* 63 */     return arrayOfDocFlavor;
/*    */   }
/*    */   
/*    */   public StreamPrintService getPrintService(OutputStream paramOutputStream) {
/* 67 */     return new PSStreamPrintService(paramOutputStream);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/PSStreamPrinterFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */