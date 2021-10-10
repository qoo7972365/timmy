/*     */ package com.sun.xml.internal.ws.runtime.config;
/*     */ 
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlElementDecl;
/*     */ import javax.xml.bind.annotation.XmlRegistry;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRegistry
/*     */ public class ObjectFactory
/*     */ {
/*  59 */   private static final QName _Tubelines_QNAME = new QName("http://java.sun.com/xml/ns/metro/config", "tubelines");
/*  60 */   private static final QName _TubelineMapping_QNAME = new QName("http://java.sun.com/xml/ns/metro/config", "tubeline-mapping");
/*  61 */   private static final QName _Tubeline_QNAME = new QName("http://java.sun.com/xml/ns/metro/config", "tubeline");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TubeFactoryConfig createTubeFactoryConfig() {
/*  75 */     return new TubeFactoryConfig();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TubeFactoryList createTubeFactoryList() {
/*  83 */     return new TubeFactoryList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TubelineDefinition createTubelineDefinition() {
/*  91 */     return new TubelineDefinition();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tubelines createTubelines() {
/*  99 */     return new Tubelines();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetroConfig createMetroConfig() {
/* 107 */     return new MetroConfig();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TubelineMapping createTubelineMapping() {
/* 115 */     return new TubelineMapping();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/metro/config", name = "tubelines")
/*     */   public JAXBElement<Tubelines> createTubelines(Tubelines value) {
/* 124 */     return new JAXBElement(_Tubelines_QNAME, Tubelines.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/metro/config", name = "tubeline-mapping")
/*     */   public JAXBElement<TubelineMapping> createTubelineMapping(TubelineMapping value) {
/* 133 */     return new JAXBElement(_TubelineMapping_QNAME, TubelineMapping.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/metro/config", name = "tubeline")
/*     */   public JAXBElement<TubelineDefinition> createTubeline(TubelineDefinition value) {
/* 142 */     return new JAXBElement(_Tubeline_QNAME, TubelineDefinition.class, null, value);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/runtime/config/ObjectFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */