/*    */ package com.sun.org.apache.xpath.internal.objects;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.utils.WrappedRuntimeException;
/*    */ import javax.xml.transform.TransformerException;
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
/*    */ public class XBooleanStatic
/*    */   extends XBoolean
/*    */ {
/*    */   static final long serialVersionUID = -8064147275772687409L;
/*    */   private final boolean m_val;
/*    */   
/*    */   public XBooleanStatic(boolean b) {
/* 46 */     super(b);
/*    */     
/* 48 */     this.m_val = b;
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
/*    */ 
/*    */   
/*    */   public boolean equals(XObject obj2) {
/*    */     try {
/* 64 */       return (this.m_val == obj2.bool());
/*    */     }
/* 66 */     catch (TransformerException te) {
/*    */       
/* 68 */       throw new WrappedRuntimeException(te);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/objects/XBooleanStatic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */