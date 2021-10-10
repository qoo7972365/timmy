/*    */ package com.sun.xml.internal.ws.client.dispatch;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*    */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*    */ import com.sun.xml.internal.ws.api.client.WSPortInfo;
/*    */ import com.sun.xml.internal.ws.api.message.Message;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*    */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*    */ import com.sun.xml.internal.ws.client.WSServiceDelegate;
/*    */ import com.sun.xml.internal.ws.encoding.xml.XMLMessage;
/*    */ import javax.activation.DataSource;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.ws.Service;
/*    */ import javax.xml.ws.WebServiceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DataSourceDispatch
/*    */   extends DispatchImpl<DataSource>
/*    */ {
/*    */   @Deprecated
/*    */   public DataSourceDispatch(QName port, Service.Mode mode, WSServiceDelegate service, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
/* 53 */     super(port, mode, service, pipe, binding, epr);
/*    */   }
/*    */   
/*    */   public DataSourceDispatch(WSPortInfo portInfo, Service.Mode mode, BindingImpl binding, WSEndpointReference epr) {
/* 57 */     super(portInfo, mode, binding, epr);
/*    */   }
/*    */ 
/*    */   
/*    */   Packet createPacket(DataSource arg) {
/* 62 */     switch (this.mode) {
/*    */       case PAYLOAD:
/* 64 */         throw new IllegalArgumentException("DataSource use is not allowed in Service.Mode.PAYLOAD\n");
/*    */       case MESSAGE:
/* 66 */         return new Packet(XMLMessage.create(arg, (WSFeatureList)this.binding.getFeatures()));
/*    */     } 
/* 68 */     throw new WebServiceException("Unrecognized message mode");
/*    */   }
/*    */ 
/*    */   
/*    */   DataSource toReturnValue(Packet response) {
/* 73 */     Message message = response.getInternalMessage();
/* 74 */     return (message instanceof XMLMessage.MessageDataSource) ? ((XMLMessage.MessageDataSource)message)
/* 75 */       .getDataSource() : 
/* 76 */       XMLMessage.getDataSource(message, (WSFeatureList)this.binding.getFeatures());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/dispatch/DataSourceDispatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */