/*    */ package javax.lang.model.element;
/*    */ 
/*    */ import javax.lang.model.UnknownEntityException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnknownElementException
/*    */   extends UnknownEntityException
/*    */ {
/*    */   private static final long serialVersionUID = 269L;
/*    */   private transient Element element;
/*    */   private transient Object parameter;
/*    */   
/*    */   public UnknownElementException(Element paramElement, Object paramObject) {
/* 61 */     super("Unknown element: " + paramElement);
/* 62 */     this.element = paramElement;
/* 63 */     this.parameter = paramObject;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Element getUnknownElement() {
/* 74 */     return this.element;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getArgument() {
/* 83 */     return this.parameter;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/element/UnknownElementException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */