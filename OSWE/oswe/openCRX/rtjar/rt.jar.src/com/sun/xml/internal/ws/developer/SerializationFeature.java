/*    */ package com.sun.xml.internal.ws.developer;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.FeatureConstructor;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SerializationFeature
/*    */   extends WebServiceFeature
/*    */ {
/*    */   public static final String ID = "http://jax-ws.java.net/features/serialization";
/*    */   private final String encoding;
/*    */   
/*    */   public SerializationFeature() {
/* 47 */     this("");
/*    */   }
/*    */   
/*    */   @FeatureConstructor({"encoding"})
/*    */   public SerializationFeature(String encoding) {
/* 52 */     this.encoding = encoding;
/*    */   }
/*    */   
/*    */   public String getID() {
/* 56 */     return "http://jax-ws.java.net/features/serialization";
/*    */   }
/*    */   
/*    */   public String getEncoding() {
/* 60 */     return this.encoding;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/developer/SerializationFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */