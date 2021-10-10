/*     */ package javax.xml.stream;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.io.Writer;
/*     */ import javax.xml.transform.Result;
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
/*     */ public abstract class XMLOutputFactory
/*     */ {
/*     */   public static final String IS_REPAIRING_NAMESPACES = "javax.xml.stream.isRepairingNamespaces";
/*     */   static final String DEFAULIMPL = "com.sun.xml.internal.stream.XMLOutputFactoryImpl";
/*     */   
/*     */   public static XMLOutputFactory newInstance() throws FactoryConfigurationError {
/* 130 */     return FactoryFinder.<XMLOutputFactory>find(XMLOutputFactory.class, "com.sun.xml.internal.stream.XMLOutputFactoryImpl");
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
/*     */   public static XMLOutputFactory newFactory() throws FactoryConfigurationError {
/* 180 */     return FactoryFinder.<XMLOutputFactory>find(XMLOutputFactory.class, "com.sun.xml.internal.stream.XMLOutputFactoryImpl");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XMLInputFactory newInstance(String factoryId, ClassLoader classLoader) throws FactoryConfigurationError {
/* 201 */     return FactoryFinder.<XMLInputFactory>find(XMLInputFactory.class, factoryId, classLoader, null);
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
/*     */   public static XMLOutputFactory newFactory(String factoryId, ClassLoader classLoader) throws FactoryConfigurationError {
/* 263 */     return FactoryFinder.<XMLOutputFactory>find(XMLOutputFactory.class, factoryId, classLoader, null);
/*     */   }
/*     */   
/*     */   public abstract XMLStreamWriter createXMLStreamWriter(Writer paramWriter) throws XMLStreamException;
/*     */   
/*     */   public abstract XMLStreamWriter createXMLStreamWriter(OutputStream paramOutputStream) throws XMLStreamException;
/*     */   
/*     */   public abstract XMLStreamWriter createXMLStreamWriter(OutputStream paramOutputStream, String paramString) throws XMLStreamException;
/*     */   
/*     */   public abstract XMLStreamWriter createXMLStreamWriter(Result paramResult) throws XMLStreamException;
/*     */   
/*     */   public abstract XMLEventWriter createXMLEventWriter(Result paramResult) throws XMLStreamException;
/*     */   
/*     */   public abstract XMLEventWriter createXMLEventWriter(OutputStream paramOutputStream) throws XMLStreamException;
/*     */   
/*     */   public abstract XMLEventWriter createXMLEventWriter(OutputStream paramOutputStream, String paramString) throws XMLStreamException;
/*     */   
/*     */   public abstract XMLEventWriter createXMLEventWriter(Writer paramWriter) throws XMLStreamException;
/*     */   
/*     */   public abstract void setProperty(String paramString, Object paramObject) throws IllegalArgumentException;
/*     */   
/*     */   public abstract Object getProperty(String paramString) throws IllegalArgumentException;
/*     */   
/*     */   public abstract boolean isPropertySupported(String paramString);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/stream/XMLOutputFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */