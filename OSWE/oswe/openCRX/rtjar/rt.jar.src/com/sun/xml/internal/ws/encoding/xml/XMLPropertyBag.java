/*    */ package com.sun.xml.internal.ws.encoding.xml;
/*    */ 
/*    */ import com.oracle.webservices.internal.api.message.BasePropertySet;
/*    */ import com.oracle.webservices.internal.api.message.PropertySet.Property;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XMLPropertyBag
/*    */   extends BasePropertySet
/*    */ {
/*    */   private String contentType;
/*    */   
/*    */   protected BasePropertySet.PropertyMap getPropertyMap() {
/* 35 */     return model;
/*    */   }
/*    */   
/*    */   @Property({"com.sun.jaxws.rest.contenttype"})
/*    */   public String getXMLContentType() {
/* 40 */     return this.contentType;
/*    */   }
/*    */   
/*    */   public void setXMLContentType(String content) {
/* 44 */     this.contentType = content;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   private static final BasePropertySet.PropertyMap model = parse(XMLPropertyBag.class);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/xml/XMLPropertyBag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */