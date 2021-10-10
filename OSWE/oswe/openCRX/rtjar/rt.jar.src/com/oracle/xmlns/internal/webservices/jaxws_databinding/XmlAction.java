/*     */ package com.oracle.xmlns.internal.webservices.jaxws_databinding;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.ws.Action;
/*     */ import javax.xml.ws.FaultAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "", propOrder = {"faultAction"})
/*     */ @XmlRootElement(name = "action")
/*     */ public class XmlAction
/*     */   implements Action
/*     */ {
/*     */   @XmlElement(name = "fault-action")
/*     */   protected List<XmlFaultAction> faultAction;
/*     */   @XmlAttribute(name = "input")
/*     */   protected String input;
/*     */   @XmlAttribute(name = "output")
/*     */   protected String output;
/*     */   
/*     */   public List<XmlFaultAction> getFaultAction() {
/* 100 */     if (this.faultAction == null) {
/* 101 */       this.faultAction = new ArrayList<>();
/*     */     }
/* 103 */     return this.faultAction;
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
/*     */   public String getInput() {
/* 115 */     return Util.nullSafe(this.input);
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
/*     */   public void setInput(String value) {
/* 127 */     this.input = value;
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
/*     */   public String getOutput() {
/* 139 */     return Util.nullSafe(this.output);
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
/*     */   public void setOutput(String value) {
/* 151 */     this.output = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public String input() {
/* 156 */     return Util.nullSafe(this.input);
/*     */   }
/*     */ 
/*     */   
/*     */   public String output() {
/* 161 */     return Util.nullSafe(this.output);
/*     */   }
/*     */ 
/*     */   
/*     */   public FaultAction[] fault() {
/* 166 */     return new FaultAction[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Annotation> annotationType() {
/* 171 */     return (Class)Action.class;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/xmlns/internal/webservices/jaxws_databinding/XmlAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */