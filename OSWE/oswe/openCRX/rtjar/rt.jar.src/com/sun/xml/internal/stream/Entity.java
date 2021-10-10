/*     */ package com.sun.xml.internal.stream;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
/*     */ import com.sun.xml.internal.stream.util.BufferAllocator;
/*     */ import com.sun.xml.internal.stream.util.ThreadLocalBufferAllocator;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Entity
/*     */ {
/*     */   public String name;
/*     */   public boolean inExternalSubset;
/*     */   
/*     */   public Entity() {
/*  57 */     clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity(String name, boolean inExternalSubset) {
/*  62 */     this.name = name;
/*  63 */     this.inExternalSubset = inExternalSubset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEntityDeclInExternalSubset() {
/*  72 */     return this.inExternalSubset;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract boolean isExternal();
/*     */ 
/*     */   
/*     */   public abstract boolean isUnparsed();
/*     */ 
/*     */   
/*     */   public void clear() {
/*  83 */     this.name = null;
/*  84 */     this.inExternalSubset = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValues(Entity entity) {
/*  89 */     this.name = entity.name;
/*  90 */     this.inExternalSubset = entity.inExternalSubset;
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
/*     */   public static class InternalEntity
/*     */     extends Entity
/*     */   {
/*     */     public String text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InternalEntity() {
/* 115 */       clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public InternalEntity(String name, String text, boolean inExternalSubset) {
/* 120 */       super(name, inExternalSubset);
/* 121 */       this.text = text;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean isExternal() {
/* 130 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isUnparsed() {
/* 135 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 140 */       super.clear();
/* 141 */       this.text = null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setValues(Entity entity) {
/* 146 */       super.setValues(entity);
/* 147 */       this.text = null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setValues(InternalEntity entity) {
/* 152 */       super.setValues(entity);
/* 153 */       this.text = entity.text;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ExternalEntity
/*     */     extends Entity
/*     */   {
/*     */     public XMLResourceIdentifier entityLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String notation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ExternalEntity() {
/* 182 */       clear();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ExternalEntity(String name, XMLResourceIdentifier entityLocation, String notation, boolean inExternalSubset) {
/* 188 */       super(name, inExternalSubset);
/* 189 */       this.entityLocation = entityLocation;
/* 190 */       this.notation = notation;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean isExternal() {
/* 199 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isUnparsed() {
/* 204 */       return (this.notation != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 209 */       super.clear();
/* 210 */       this.entityLocation = null;
/* 211 */       this.notation = null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setValues(Entity entity) {
/* 216 */       super.setValues(entity);
/* 217 */       this.entityLocation = null;
/* 218 */       this.notation = null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setValues(ExternalEntity entity) {
/* 223 */       super.setValues(entity);
/* 224 */       this.entityLocation = entity.entityLocation;
/* 225 */       this.notation = entity.notation;
/*     */     }
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
/*     */   public static class ScannedEntity
/*     */     extends Entity
/*     */   {
/*     */     public static final int DEFAULT_BUFFER_SIZE = 8192;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 248 */     public int fBufferSize = 8192;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static final int DEFAULT_XMLDECL_BUFFER_SIZE = 28;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static final int DEFAULT_INTERNAL_BUFFER_SIZE = 1024;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InputStream stream;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Reader reader;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public XMLResourceIdentifier entityLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String encoding;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean literal;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isExternal;
/*     */ 
/*     */ 
/*     */     
/*     */     public String version;
/*     */ 
/*     */ 
/*     */     
/* 294 */     public char[] ch = null;
/*     */ 
/*     */     
/*     */     public int position;
/*     */ 
/*     */     
/*     */     public int count;
/*     */ 
/*     */     
/* 303 */     public int lineNumber = 1;
/*     */ 
/*     */     
/* 306 */     public int columnNumber = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean declaredEncoding = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean externallySpecifiedEncoding = false;
/*     */ 
/*     */ 
/*     */     
/* 320 */     public String xmlVersion = "1.0";
/*     */ 
/*     */ 
/*     */     
/*     */     public int fTotalCountTillLastLoad;
/*     */ 
/*     */ 
/*     */     
/*     */     public int fLastCount;
/*     */ 
/*     */ 
/*     */     
/*     */     public int baseCharOffset;
/*     */ 
/*     */ 
/*     */     
/*     */     public int startPosition;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean mayReadChunks;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean xmlDeclChunkRead = false;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isGE = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getEncodingName() {
/* 354 */       return this.encoding;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getEntityVersion() {
/* 361 */       return this.version;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setEntityVersion(String version) {
/* 368 */       this.version = version;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Reader getEntityReader() {
/* 377 */       return this.reader;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InputStream getEntityInputStream() {
/* 386 */       return this.stream;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ScannedEntity(boolean isGE, String name, XMLResourceIdentifier entityLocation, InputStream stream, Reader reader, String encoding, boolean literal, boolean mayReadChunks, boolean isExternal) {
/* 398 */       this.isGE = isGE;
/* 399 */       this.name = name;
/* 400 */       this.entityLocation = entityLocation;
/* 401 */       this.stream = stream;
/* 402 */       this.reader = reader;
/* 403 */       this.encoding = encoding;
/* 404 */       this.literal = literal;
/* 405 */       this.mayReadChunks = mayReadChunks;
/* 406 */       this.isExternal = isExternal;
/* 407 */       int size = isExternal ? this.fBufferSize : 1024;
/* 408 */       BufferAllocator ba = ThreadLocalBufferAllocator.getBufferAllocator();
/* 409 */       this.ch = ba.getCharBuffer(size);
/* 410 */       if (this.ch == null) {
/* 411 */         this.ch = new char[size];
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 419 */       BufferAllocator ba = ThreadLocalBufferAllocator.getBufferAllocator();
/* 420 */       ba.returnCharBuffer(this.ch);
/* 421 */       this.ch = null;
/* 422 */       this.reader.close();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isEncodingExternallySpecified() {
/* 431 */       return this.externallySpecifiedEncoding;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setEncodingExternallySpecified(boolean value) {
/* 436 */       this.externallySpecifiedEncoding = value;
/*     */     }
/*     */     
/*     */     public boolean isDeclaredEncoding() {
/* 440 */       return this.declaredEncoding;
/*     */     }
/*     */     
/*     */     public void setDeclaredEncoding(boolean value) {
/* 444 */       this.declaredEncoding = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isExternal() {
/* 449 */       return this.isExternal;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isUnparsed() {
/* 454 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 464 */       StringBuffer str = new StringBuffer();
/* 465 */       str.append("name=\"" + this.name + '"');
/* 466 */       str.append(",ch=" + new String(this.ch));
/* 467 */       str.append(",position=" + this.position);
/* 468 */       str.append(",count=" + this.count);
/* 469 */       return str.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/stream/Entity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */