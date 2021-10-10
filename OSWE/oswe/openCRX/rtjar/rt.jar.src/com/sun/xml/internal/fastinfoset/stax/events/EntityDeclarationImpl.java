/*     */ package com.sun.xml.internal.fastinfoset.stax.events;
/*     */ 
/*     */ import javax.xml.stream.events.EntityDeclaration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityDeclarationImpl
/*     */   extends EventBase
/*     */   implements EntityDeclaration
/*     */ {
/*     */   private String _publicId;
/*     */   private String _systemId;
/*     */   private String _baseURI;
/*     */   private String _entityName;
/*     */   private String _replacement;
/*     */   private String _notationName;
/*     */   
/*     */   public EntityDeclarationImpl() {
/*  43 */     init();
/*     */   }
/*     */   
/*     */   public EntityDeclarationImpl(String entityName, String replacement) {
/*  47 */     init();
/*  48 */     this._entityName = entityName;
/*  49 */     this._replacement = replacement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPublicId() {
/*  57 */     return this._publicId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSystemId() {
/*  65 */     return this._systemId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  73 */     return this._entityName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNotationName() {
/*  81 */     return this._notationName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReplacementText() {
/*  91 */     return this._replacement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseURI() {
/* 100 */     return this._baseURI;
/*     */   }
/*     */   
/*     */   public void setPublicId(String publicId) {
/* 104 */     this._publicId = publicId;
/*     */   }
/*     */   
/*     */   public void setSystemId(String systemId) {
/* 108 */     this._systemId = systemId;
/*     */   }
/*     */   
/*     */   public void setBaseURI(String baseURI) {
/* 112 */     this._baseURI = baseURI;
/*     */   }
/*     */   
/*     */   public void setName(String entityName) {
/* 116 */     this._entityName = entityName;
/*     */   }
/*     */   
/*     */   public void setReplacementText(String replacement) {
/* 120 */     this._replacement = replacement;
/*     */   }
/*     */   
/*     */   public void setNotationName(String notationName) {
/* 124 */     this._notationName = notationName;
/*     */   }
/*     */   
/*     */   protected void init() {
/* 128 */     setEventType(15);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/stax/events/EntityDeclarationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */