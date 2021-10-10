/*    */ package jdk.internal.util.xml;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.InvalidPropertiesFormatException;
/*    */ import java.util.Properties;
/*    */ import sun.util.spi.XmlPropertiesProvider;
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
/*    */ public class BasicXmlPropertiesProvider
/*    */   extends XmlPropertiesProvider
/*    */ {
/*    */   public void load(Properties paramProperties, InputStream paramInputStream) throws IOException, InvalidPropertiesFormatException {
/* 47 */     PropertiesDefaultHandler propertiesDefaultHandler = new PropertiesDefaultHandler();
/* 48 */     propertiesDefaultHandler.load(paramProperties, paramInputStream);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void store(Properties paramProperties, OutputStream paramOutputStream, String paramString1, String paramString2) throws IOException {
/* 56 */     PropertiesDefaultHandler propertiesDefaultHandler = new PropertiesDefaultHandler();
/* 57 */     propertiesDefaultHandler.store(paramProperties, paramOutputStream, paramString1, paramString2);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/util/xml/BasicXmlPropertiesProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */