/*    */ package com.sun.xml.internal.ws.model.wsdl;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.model.ParameterBinding;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPartDescriptor;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPart;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class WSDLPartImpl
/*    */   extends AbstractObjectImpl
/*    */   implements EditableWSDLPart
/*    */ {
/*    */   private final String name;
/*    */   private ParameterBinding binding;
/*    */   private int index;
/*    */   private final WSDLPartDescriptor descriptor;
/*    */   
/*    */   public WSDLPartImpl(XMLStreamReader xsr, String partName, int index, WSDLPartDescriptor descriptor) {
/* 47 */     super(xsr);
/* 48 */     this.name = partName;
/* 49 */     this.binding = ParameterBinding.UNBOUND;
/* 50 */     this.index = index;
/* 51 */     this.descriptor = descriptor;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 56 */     return this.name;
/*    */   }
/*    */   
/*    */   public ParameterBinding getBinding() {
/* 60 */     return this.binding;
/*    */   }
/*    */   
/*    */   public void setBinding(ParameterBinding binding) {
/* 64 */     this.binding = binding;
/*    */   }
/*    */   
/*    */   public int getIndex() {
/* 68 */     return this.index;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setIndex(int index) {
/* 73 */     this.index = index;
/*    */   }
/*    */   
/*    */   public WSDLPartDescriptor getDescriptor() {
/* 77 */     return this.descriptor;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/wsdl/WSDLPartImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */