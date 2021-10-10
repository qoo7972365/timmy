/*     */ package com.sun.org.apache.xerces.internal.jaxp.validation;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import javax.xml.validation.Schema;
/*     */ import javax.xml.validation.Validator;
/*     */ import javax.xml.validation.ValidatorHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractXMLSchema
/*     */   extends Schema
/*     */   implements XSGrammarPoolContainer
/*     */ {
/*  51 */   private final HashMap fFeatures = new HashMap<>();
/*  52 */   private final HashMap fProperties = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Validator newValidator() {
/*  63 */     return new ValidatorImpl(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ValidatorHandler newValidatorHandler() {
/*  70 */     return new ValidatorHandlerImpl(this);
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
/*     */   public final Boolean getFeature(String featureId) {
/*  83 */     return (Boolean)this.fFeatures.get(featureId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setFeature(String featureId, boolean state) {
/*  90 */     this.fFeatures.put(featureId, state ? Boolean.TRUE : Boolean.FALSE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Object getProperty(String propertyId) {
/*  99 */     return this.fProperties.get(propertyId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setProperty(String propertyId, Object state) {
/* 106 */     this.fProperties.put(propertyId, state);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/validation/AbstractXMLSchema.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */