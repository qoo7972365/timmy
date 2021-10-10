/*    */ package com.sun.xml.internal.ws.model.wsdl;
/*    */ 
/*    */ import com.oracle.webservices.internal.api.message.BasePropertySet;
/*    */ import com.oracle.webservices.internal.api.message.PropertySet.Property;
/*    */ import com.sun.istack.internal.Nullable;
/*    */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*    */ import javax.xml.namespace.QName;
/*    */ import org.xml.sax.InputSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WSDLProperties
/*    */   extends BasePropertySet
/*    */ {
/* 49 */   private static final BasePropertySet.PropertyMap model = parse(WSDLProperties.class);
/*    */   
/*    */   @Nullable
/*    */   private final SEIModel seiModel;
/*    */   
/*    */   protected WSDLProperties(@Nullable SEIModel seiModel) {
/* 55 */     this.seiModel = seiModel;
/*    */   }
/*    */   
/*    */   @Property({"javax.xml.ws.wsdl.service"})
/*    */   public abstract QName getWSDLService();
/*    */   
/*    */   @Property({"javax.xml.ws.wsdl.port"})
/*    */   public abstract QName getWSDLPort();
/*    */   
/*    */   @Property({"javax.xml.ws.wsdl.interface"})
/*    */   public abstract QName getWSDLPortType();
/*    */   
/*    */   @Property({"javax.xml.ws.wsdl.description"})
/*    */   public InputSource getWSDLDescription() {
/* 69 */     return (this.seiModel != null) ? new InputSource(this.seiModel.getWSDLLocation()) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BasePropertySet.PropertyMap getPropertyMap() {
/* 74 */     return model;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/wsdl/WSDLProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */