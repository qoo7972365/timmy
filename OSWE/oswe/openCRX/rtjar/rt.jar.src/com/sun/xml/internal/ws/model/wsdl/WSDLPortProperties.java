/*    */ package com.sun.xml.internal.ws.model.wsdl;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.istack.internal.Nullable;
/*    */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class WSDLPortProperties
/*    */   extends WSDLProperties
/*    */ {
/*    */   @NotNull
/*    */   private final WSDLPort port;
/*    */   
/*    */   public WSDLPortProperties(@NotNull WSDLPort port) {
/* 47 */     this(port, null);
/*    */   }
/*    */   
/*    */   public WSDLPortProperties(@NotNull WSDLPort port, @Nullable SEIModel seiModel) {
/* 51 */     super(seiModel);
/* 52 */     this.port = port;
/*    */   }
/*    */   
/*    */   public QName getWSDLService() {
/* 56 */     return this.port.getOwner().getName();
/*    */   }
/*    */   
/*    */   public QName getWSDLPort() {
/* 60 */     return this.port.getName();
/*    */   }
/*    */   
/*    */   public QName getWSDLPortType() {
/* 64 */     return this.port.getBinding().getPortTypeName();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/wsdl/WSDLPortProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */