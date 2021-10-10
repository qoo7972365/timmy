/*    */ package sun.print;
/*    */ 
/*    */ import javax.print.AttributeException;
/*    */ import javax.print.PrintException;
/*    */ import javax.print.attribute.Attribute;
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
/*    */ class PrintJobAttributeException
/*    */   extends PrintException
/*    */   implements AttributeException
/*    */ {
/*    */   private Attribute attr;
/*    */   private Class category;
/*    */   
/*    */   PrintJobAttributeException(String paramString, Class paramClass, Attribute paramAttribute) {
/* 39 */     super(paramString);
/* 40 */     this.attr = paramAttribute;
/* 41 */     this.category = paramClass;
/*    */   }
/*    */   
/*    */   public Class[] getUnsupportedAttributes() {
/* 45 */     if (this.category == null) {
/* 46 */       return null;
/*    */     }
/* 48 */     return new Class[] { this.category };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Attribute[] getUnsupportedValues() {
/* 54 */     if (this.attr == null) {
/* 55 */       return null;
/*    */     }
/* 57 */     return new Attribute[] { this.attr };
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/PrintJobAttributeException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */