/*     */ package com.sun.xml.internal.ws.fault;
/*     */ 
/*     */ import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
/*     */ import com.sun.xml.internal.ws.developer.ServerSideException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.PropertyException;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlElementWrapper;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRootElement(namespace = "http://jax-ws.dev.java.net/", name = "exception")
/*     */ final class ExceptionBean
/*     */ {
/*     */   @XmlAttribute(name = "class")
/*     */   public String className;
/*     */   @XmlElement
/*     */   public String message;
/*     */   
/*     */   public static void marshal(Throwable t, Node parent) throws JAXBException {
/*  60 */     Marshaller m = JAXB_CONTEXT.createMarshaller();
/*     */     try {
/*  62 */       m.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", nsp);
/*  63 */     } catch (PropertyException propertyException) {}
/*  64 */     m.marshal(new ExceptionBean(t), parent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ServerSideException unmarshal(Node xml) throws JAXBException {
/*  72 */     ExceptionBean e = (ExceptionBean)JAXB_CONTEXT.createUnmarshaller().unmarshal(xml);
/*  73 */     return e.toException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementWrapper(namespace = "http://jax-ws.dev.java.net/", name = "stackTrace")
/*     */   @XmlElement(namespace = "http://jax-ws.dev.java.net/", name = "frame")
/*  80 */   public List<StackFrame> stackTrace = new ArrayList<>();
/*     */ 
/*     */   
/*     */   @XmlElement(namespace = "http://jax-ws.dev.java.net/", name = "cause")
/*     */   public ExceptionBean cause;
/*     */   
/*     */   @XmlAttribute
/*  87 */   public String note = "To disable this feature, set " + SOAPFaultBuilder.CAPTURE_STACK_TRACE_PROPERTY + " system property to false";
/*     */   
/*     */   private static final JAXBContext JAXB_CONTEXT;
/*     */   
/*     */   static final String NS = "http://jax-ws.dev.java.net/";
/*     */   static final String LOCAL_NAME = "exception";
/*     */   
/*     */   ExceptionBean() {}
/*     */   
/*     */   private ExceptionBean(Throwable t) {
/*  97 */     this.className = t.getClass().getName();
/*  98 */     this.message = t.getMessage();
/*     */     
/* 100 */     for (StackTraceElement f : t.getStackTrace()) {
/* 101 */       this.stackTrace.add(new StackFrame(f));
/*     */     }
/*     */     
/* 104 */     Throwable cause = t.getCause();
/* 105 */     if (t != cause && cause != null)
/* 106 */       this.cause = new ExceptionBean(cause); 
/*     */   }
/*     */   
/*     */   private ServerSideException toException() {
/* 110 */     ServerSideException e = new ServerSideException(this.className, this.message);
/* 111 */     if (this.stackTrace != null) {
/* 112 */       StackTraceElement[] ste = new StackTraceElement[this.stackTrace.size()];
/* 113 */       for (int i = 0; i < this.stackTrace.size(); i++)
/* 114 */         ste[i] = ((StackFrame)this.stackTrace.get(i)).toStackTraceElement(); 
/* 115 */       e.setStackTrace(ste);
/*     */     } 
/* 117 */     if (this.cause != null)
/* 118 */       e.initCause((Throwable)this.cause.toException()); 
/* 119 */     return e;
/*     */   }
/*     */ 
/*     */   
/*     */   static final class StackFrame
/*     */   {
/*     */     @XmlAttribute(name = "class")
/*     */     public String declaringClass;
/*     */     
/*     */     @XmlAttribute(name = "method")
/*     */     public String methodName;
/*     */     
/*     */     @XmlAttribute(name = "file")
/*     */     public String fileName;
/*     */     @XmlAttribute(name = "line")
/*     */     public String lineNumber;
/*     */     
/*     */     StackFrame() {}
/*     */     
/*     */     public StackFrame(StackTraceElement ste) {
/* 139 */       this.declaringClass = ste.getClassName();
/* 140 */       this.methodName = ste.getMethodName();
/* 141 */       this.fileName = ste.getFileName();
/* 142 */       this.lineNumber = box(ste.getLineNumber());
/*     */     }
/*     */     
/*     */     private String box(int i) {
/* 146 */       if (i >= 0) return String.valueOf(i); 
/* 147 */       if (i == -2) return "native"; 
/* 148 */       return "unknown";
/*     */     }
/*     */     
/*     */     private int unbox(String v) {
/*     */       try {
/* 153 */         return Integer.parseInt(v);
/* 154 */       } catch (NumberFormatException e) {
/* 155 */         if ("native".equals(v)) {
/* 156 */           return -2;
/*     */         }
/* 158 */         return -1;
/*     */       } 
/*     */     }
/*     */     
/*     */     private StackTraceElement toStackTraceElement() {
/* 163 */       return new StackTraceElement(this.declaringClass, this.methodName, this.fileName, unbox(this.lineNumber));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isStackTraceXml(Element n) {
/* 171 */     return ("exception".equals(n.getLocalName()) && "http://jax-ws.dev.java.net/".equals(n.getNamespaceURI()));
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
/*     */   static {
/*     */     try {
/* 185 */       JAXB_CONTEXT = JAXBContext.newInstance(new Class[] { ExceptionBean.class });
/* 186 */     } catch (JAXBException e) {
/*     */       
/* 188 */       throw new Error(e);
/*     */     } 
/*     */   }
/*     */   
/* 192 */   private static final NamespacePrefixMapper nsp = new NamespacePrefixMapper() {
/*     */       public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
/* 194 */         if ("http://jax-ws.dev.java.net/".equals(namespaceUri)) {
/* 195 */           return "";
/*     */         }
/* 197 */         return suggestion;
/*     */       }
/*     */     };
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/fault/ExceptionBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */