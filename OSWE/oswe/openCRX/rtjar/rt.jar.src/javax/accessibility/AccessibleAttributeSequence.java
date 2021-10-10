/*    */ package javax.accessibility;
/*    */ 
/*    */ import javax.swing.text.AttributeSet;
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
/*    */ public class AccessibleAttributeSequence
/*    */ {
/*    */   public int startIndex;
/*    */   public int endIndex;
/*    */   public AttributeSet attributes;
/*    */   
/*    */   public AccessibleAttributeSequence(int paramInt1, int paramInt2, AttributeSet paramAttributeSet) {
/* 73 */     this.startIndex = paramInt1;
/* 74 */     this.endIndex = paramInt2;
/* 75 */     this.attributes = paramAttributeSet;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/accessibility/AccessibleAttributeSequence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */