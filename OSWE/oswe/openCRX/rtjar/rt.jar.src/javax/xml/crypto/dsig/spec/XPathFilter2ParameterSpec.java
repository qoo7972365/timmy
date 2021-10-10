/*    */ package javax.xml.crypto.dsig.spec;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
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
/*    */ public final class XPathFilter2ParameterSpec
/*    */   implements TransformParameterSpec
/*    */ {
/*    */   private final List<XPathType> xPathList;
/*    */   
/*    */   public XPathFilter2ParameterSpec(List<?> paramList) {
/* 64 */     if (paramList == null) {
/* 65 */       throw new NullPointerException("xPathList cannot be null");
/*    */     }
/* 67 */     ArrayList<? extends XPathType> arrayList1 = new ArrayList(paramList);
/* 68 */     if (arrayList1.isEmpty()) {
/* 69 */       throw new IllegalArgumentException("xPathList cannot be empty");
/*    */     }
/* 71 */     int i = arrayList1.size();
/* 72 */     for (byte b = 0; b < i; b++) {
/* 73 */       if (!(arrayList1.get(b) instanceof XPathType)) {
/* 74 */         throw new ClassCastException("xPathList[" + b + "] is not a valid type");
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 80 */     ArrayList<? extends XPathType> arrayList2 = arrayList1;
/*    */     
/* 82 */     this.xPathList = Collections.unmodifiableList(arrayList2);
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
/*    */   public List getXPathList() {
/* 96 */     return this.xPathList;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/crypto/dsig/spec/XPathFilter2ParameterSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */