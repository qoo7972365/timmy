/*     */ package com.sun.org.apache.xml.internal.serializer;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
/*     */ import com.sun.org.apache.xml.internal.serializer.utils.Utils;
/*     */ import com.sun.org.apache.xml.internal.serializer.utils.WrappedRuntimeException;
/*     */ import java.util.Properties;
/*     */ import org.xml.sax.ContentHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SerializerFactory
/*     */ {
/*     */   public static Serializer getSerializer(Properties format) {
/*     */     Serializer ser;
/*     */     try {
/*  92 */       String method = format.getProperty("method");
/*     */       
/*  94 */       if (method == null) {
/*  95 */         String msg = Utils.messages.createMessage("ER_FACTORY_PROPERTY_MISSING", new Object[] { "method" });
/*     */ 
/*     */         
/*  98 */         throw new IllegalArgumentException(msg);
/*     */       } 
/*     */ 
/*     */       
/* 102 */       String className = format.getProperty("{http://xml.apache.org/xalan}content-handler");
/*     */ 
/*     */       
/* 105 */       if (null == className) {
/*     */ 
/*     */ 
/*     */         
/* 109 */         Properties methodDefaults = OutputPropertiesFactory.getDefaultMethodProperties(method);
/*     */         
/* 111 */         className = methodDefaults.getProperty("{http://xml.apache.org/xalan}content-handler");
/* 112 */         if (null == className) {
/* 113 */           String msg = Utils.messages.createMessage("ER_FACTORY_PROPERTY_MISSING", new Object[] { "{http://xml.apache.org/xalan}content-handler" });
/*     */ 
/*     */           
/* 116 */           throw new IllegalArgumentException(msg);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 123 */       Class<?> cls = ObjectFactory.findProviderClass(className, true);
/*     */ 
/*     */ 
/*     */       
/* 127 */       Object obj = cls.newInstance();
/*     */       
/* 129 */       if (obj instanceof SerializationHandler)
/*     */       {
/*     */         
/* 132 */         ser = (Serializer)cls.newInstance();
/* 133 */         ser.setOutputFormat(format);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 141 */       else if (obj instanceof ContentHandler)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 150 */         className = "com.sun.org.apache.xml.internal.serializer.ToXMLSAXHandler";
/* 151 */         cls = ObjectFactory.findProviderClass(className, true);
/*     */         
/* 153 */         SerializationHandler sh = (SerializationHandler)cls.newInstance();
/* 154 */         sh.setContentHandler((ContentHandler)obj);
/* 155 */         sh.setOutputFormat(format);
/*     */         
/* 157 */         ser = sh;
/*     */       
/*     */       }
/*     */       else
/*     */       {
/*     */         
/* 163 */         throw new Exception(Utils.messages
/* 164 */             .createMessage("ER_SERIALIZER_NOT_CONTENTHANDLER", new Object[] { className }));
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 171 */     catch (Exception e) {
/*     */       
/* 173 */       throw new WrappedRuntimeException(e);
/*     */     } 
/*     */ 
/*     */     
/* 177 */     return ser;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/serializer/SerializerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */