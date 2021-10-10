/*    */ package javax.xml.bind.util;
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
/*    */ 
/*    */ class Messages
/*    */ {
/*    */   static final String UNRECOGNIZED_SEVERITY = "ValidationEventCollector.UnrecognizedSeverity";
/*    */   static final String RESULT_NULL_CONTEXT = "JAXBResult.NullContext";
/*    */   static final String RESULT_NULL_UNMARSHALLER = "JAXBResult.NullUnmarshaller";
/*    */   static final String SOURCE_NULL_CONTEXT = "JAXBSource.NullContext";
/*    */   static final String SOURCE_NULL_CONTENT = "JAXBSource.NullContent";
/*    */   static final String SOURCE_NULL_MARSHALLER = "JAXBSource.NullMarshaller";
/*    */   
/*    */   static String format(String property) {
/* 37 */     return format(property, (Object[])null);
/*    */   }
/*    */   
/*    */   static String format(String property, Object arg1) {
/* 41 */     return format(property, new Object[] { arg1 });
/*    */   }
/*    */   
/*    */   static String format(String property, Object arg1, Object arg2) {
/* 45 */     return format(property, new Object[] { arg1, arg2 });
/*    */   }
/*    */   
/*    */   static String format(String property, Object arg1, Object arg2, Object arg3) {
/* 49 */     return format(property, new Object[] { arg1, arg2, arg3 });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static String format(String property, Object[] args) {
/* 56 */     String text = ResourceBundle.getBundle(Messages.class.getName()).getString(property);
/* 57 */     return MessageFormat.format(text, args);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/bind/util/Messages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */