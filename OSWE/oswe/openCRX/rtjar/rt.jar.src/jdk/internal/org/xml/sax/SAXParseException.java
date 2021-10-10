/*     */ package jdk.internal.org.xml.sax;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAXParseException
/*     */   extends SAXException
/*     */ {
/*     */   private String publicId;
/*     */   private String systemId;
/*     */   private int lineNumber;
/*     */   private int columnNumber;
/*     */   static final long serialVersionUID = -5651165872476709336L;
/*     */   
/*     */   public SAXParseException(String paramString, Locator paramLocator) {
/*  83 */     super(paramString);
/*  84 */     if (paramLocator != null) {
/*  85 */       init(paramLocator.getPublicId(), paramLocator.getSystemId(), paramLocator
/*  86 */           .getLineNumber(), paramLocator.getColumnNumber());
/*     */     } else {
/*  88 */       init(null, null, -1, -1);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAXParseException(String paramString, Locator paramLocator, Exception paramException) {
/* 110 */     super(paramString, paramException);
/* 111 */     if (paramLocator != null) {
/* 112 */       init(paramLocator.getPublicId(), paramLocator.getSystemId(), paramLocator
/* 113 */           .getLineNumber(), paramLocator.getColumnNumber());
/*     */     } else {
/* 115 */       init(null, null, -1, -1);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAXParseException(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2) {
/* 144 */     super(paramString1);
/* 145 */     init(paramString2, paramString3, paramInt1, paramInt2);
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
/*     */   public SAXParseException(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, Exception paramException) {
/* 176 */     super(paramString1, paramException);
/* 177 */     init(paramString2, paramString3, paramInt1, paramInt2);
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
/*     */   private void init(String paramString1, String paramString2, int paramInt1, int paramInt2) {
/* 194 */     this.publicId = paramString1;
/* 195 */     this.systemId = paramString2;
/* 196 */     this.lineNumber = paramInt1;
/* 197 */     this.columnNumber = paramInt2;
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
/*     */   public String getPublicId() {
/* 210 */     return this.publicId;
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
/*     */   public String getSystemId() {
/* 226 */     return this.systemId;
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
/*     */   public int getLineNumber() {
/* 241 */     return this.lineNumber;
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
/*     */   public int getColumnNumber() {
/* 256 */     return this.columnNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 265 */     StringBuilder stringBuilder = new StringBuilder(getClass().getName());
/* 266 */     String str = getLocalizedMessage();
/* 267 */     if (this.publicId != null) stringBuilder.append("publicId: ").append(this.publicId); 
/* 268 */     if (this.systemId != null) stringBuilder.append("; systemId: ").append(this.systemId); 
/* 269 */     if (this.lineNumber != -1) stringBuilder.append("; lineNumber: ").append(this.lineNumber); 
/* 270 */     if (this.columnNumber != -1) stringBuilder.append("; columnNumber: ").append(this.columnNumber);
/*     */ 
/*     */     
/* 273 */     if (str != null) stringBuilder.append("; ").append(str); 
/* 274 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/xml/sax/SAXParseException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */