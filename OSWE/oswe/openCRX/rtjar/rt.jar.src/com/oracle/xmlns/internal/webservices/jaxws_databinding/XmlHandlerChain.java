/*    */ package com.oracle.xmlns.internal.webservices.jaxws_databinding;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.jws.HandlerChain;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ import javax.xml.bind.annotation.XmlType;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "")
/*    */ @XmlRootElement(name = "handler-chain")
/*    */ public class XmlHandlerChain
/*    */   implements HandlerChain
/*    */ {
/*    */   @XmlAttribute(name = "file")
/*    */   protected String file;
/*    */   
/*    */   public String getFile() {
/* 72 */     return this.file;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFile(String value) {
/* 84 */     this.file = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String file() {
/* 89 */     return Util.nullSafe(this.file);
/*    */   }
/*    */ 
/*    */   
/*    */   public String name() {
/* 94 */     return "";
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<? extends Annotation> annotationType() {
/* 99 */     return (Class)HandlerChain.class;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/xmlns/internal/webservices/jaxws_databinding/XmlHandlerChain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */