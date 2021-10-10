/*     */ package com.sun.org.apache.xml.internal.serialize;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.utils.ObjectFactory;
/*     */ import com.sun.org.apache.xerces.internal.utils.SecuritySupport;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.io.Writer;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
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
/*     */ public abstract class SerializerFactory
/*     */ {
/*     */   public static final String FactoriesProperty = "com.sun.org.apache.xml.internal.serialize.factories";
/*  48 */   private static final Map<String, SerializerFactory> _factories = Collections.synchronizedMap(new HashMap<>());
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
/*     */   static {
/*  61 */     SerializerFactory factory = new SerializerFactoryImpl("xml");
/*  62 */     registerSerializerFactory(factory);
/*  63 */     factory = new SerializerFactoryImpl("html");
/*  64 */     registerSerializerFactory(factory);
/*  65 */     factory = new SerializerFactoryImpl("xhtml");
/*  66 */     registerSerializerFactory(factory);
/*  67 */     factory = new SerializerFactoryImpl("text");
/*  68 */     registerSerializerFactory(factory);
/*     */     
/*  70 */     String list = SecuritySupport.getSystemProperty("com.sun.org.apache.xml.internal.serialize.factories");
/*  71 */     if (list != null) {
/*  72 */       StringTokenizer token = new StringTokenizer(list, " ;,:");
/*  73 */       while (token.hasMoreTokens()) {
/*  74 */         String className = token.nextToken();
/*     */         try {
/*  76 */           factory = (SerializerFactory)ObjectFactory.newInstance(className, true);
/*  77 */           if (_factories.containsKey(factory.getSupportedMethod()))
/*  78 */             _factories.put(factory.getSupportedMethod(), factory); 
/*  79 */         } catch (Exception exception) {}
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerSerializerFactory(SerializerFactory factory) {
/*  93 */     synchronized (_factories) {
/*  94 */       String method = factory.getSupportedMethod();
/*  95 */       _factories.put(method, factory);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SerializerFactory getSerializerFactory(String method) {
/* 106 */     return _factories.get(method);
/*     */   }
/*     */   
/*     */   protected abstract String getSupportedMethod();
/*     */   
/*     */   public abstract Serializer makeSerializer(OutputFormat paramOutputFormat);
/*     */   
/*     */   public abstract Serializer makeSerializer(Writer paramWriter, OutputFormat paramOutputFormat);
/*     */   
/*     */   public abstract Serializer makeSerializer(OutputStream paramOutputStream, OutputFormat paramOutputFormat) throws UnsupportedEncodingException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/serialize/SerializerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */