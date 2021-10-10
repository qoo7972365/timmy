/*     */ package com.sun.xml.internal.stream.events;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.List;
/*     */ import javax.xml.stream.events.DTD;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DTDEvent
/*     */   extends DummyEvent
/*     */   implements DTD
/*     */ {
/*     */   private String fDoctypeDeclaration;
/*     */   private List fNotations;
/*     */   private List fEntities;
/*     */   
/*     */   public DTDEvent() {
/*  44 */     init();
/*     */   }
/*     */   
/*     */   public DTDEvent(String doctypeDeclaration) {
/*  48 */     init();
/*  49 */     this.fDoctypeDeclaration = doctypeDeclaration;
/*     */   }
/*     */   
/*     */   public void setDocumentTypeDeclaration(String doctypeDeclaration) {
/*  53 */     this.fDoctypeDeclaration = doctypeDeclaration;
/*     */   }
/*     */   
/*     */   public String getDocumentTypeDeclaration() {
/*  57 */     return this.fDoctypeDeclaration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntities(List entites) {
/*  64 */     this.fEntities = entites;
/*     */   }
/*     */   
/*     */   public List getEntities() {
/*  68 */     return this.fEntities;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNotations(List notations) {
/*  75 */     this.fNotations = notations;
/*     */   }
/*     */   
/*     */   public List getNotations() {
/*  79 */     return this.fNotations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProcessedDTD() {
/*  88 */     return null;
/*     */   }
/*     */   
/*     */   protected void init() {
/*  92 */     setEventType(11);
/*     */   }
/*     */   
/*     */   public String toString() {
/*  96 */     return this.fDoctypeDeclaration;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeAsEncodedUnicodeEx(Writer writer) throws IOException {
/* 102 */     writer.write(this.fDoctypeDeclaration);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/stream/events/DTDEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */