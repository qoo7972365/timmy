/*    */ package com.sun.xml.internal.bind.marshaller;
/*    */ 
/*    */ import java.text.MessageFormat;
/*    */ import java.util.ResourceBundle;
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
/*    */ public class Messages
/*    */ {
/*    */   public static final String NOT_MARSHALLABLE = "MarshallerImpl.NotMarshallable";
/*    */   public static final String UNSUPPORTED_RESULT = "MarshallerImpl.UnsupportedResult";
/*    */   public static final String UNSUPPORTED_ENCODING = "MarshallerImpl.UnsupportedEncoding";
/*    */   public static final String NULL_WRITER = "MarshallerImpl.NullWriterParam";
/*    */   public static final String ASSERT_FAILED = "SAXMarshaller.AssertFailed";
/*    */   public static final String ERR_MISSING_OBJECT = "SAXMarshaller.MissingObject";
/*    */   public static final String ERR_DANGLING_IDREF = "SAXMarshaller.DanglingIDREF";
/*    */   public static final String ERR_NOT_IDENTIFIABLE = "SAXMarshaller.NotIdentifiable";
/*    */   public static final String DOM_IMPL_DOESNT_SUPPORT_CREATELEMENTNS = "SAX2DOMEx.DomImplDoesntSupportCreateElementNs";
/*    */   
/*    */   public static String format(String property) {
/* 39 */     return format(property, (Object[])null);
/*    */   }
/*    */   
/*    */   public static String format(String property, Object arg1) {
/* 43 */     return format(property, new Object[] { arg1 });
/*    */   }
/*    */   
/*    */   public static String format(String property, Object arg1, Object arg2) {
/* 47 */     return format(property, new Object[] { arg1, arg2 });
/*    */   }
/*    */   
/*    */   public static String format(String property, Object arg1, Object arg2, Object arg3) {
/* 51 */     return format(property, new Object[] { arg1, arg2, arg3 });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static String format(String property, Object[] args) {
/* 58 */     String text = ResourceBundle.getBundle(Messages.class.getName()).getString(property);
/* 59 */     return MessageFormat.format(text, args);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/marshaller/Messages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */