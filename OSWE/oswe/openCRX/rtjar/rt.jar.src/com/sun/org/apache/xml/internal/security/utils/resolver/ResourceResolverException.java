/*     */ package com.sun.org.apache.xml.internal.security.utils.resolver;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import org.w3c.dom.Attr;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResourceResolverException
/*     */   extends XMLSecurityException
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  38 */   private Attr uri = null;
/*     */   
/*  40 */   private String baseURI = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceResolverException(String paramString1, Attr paramAttr, String paramString2) {
/*  50 */     super(paramString1);
/*     */     
/*  52 */     this.uri = paramAttr;
/*  53 */     this.baseURI = paramString2;
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
/*     */   public ResourceResolverException(String paramString1, Object[] paramArrayOfObject, Attr paramAttr, String paramString2) {
/*  66 */     super(paramString1, paramArrayOfObject);
/*     */     
/*  68 */     this.uri = paramAttr;
/*  69 */     this.baseURI = paramString2;
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
/*     */   public ResourceResolverException(String paramString1, Exception paramException, Attr paramAttr, String paramString2) {
/*  82 */     super(paramString1, paramException);
/*     */     
/*  84 */     this.uri = paramAttr;
/*  85 */     this.baseURI = paramString2;
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
/*     */   public ResourceResolverException(String paramString1, Object[] paramArrayOfObject, Exception paramException, Attr paramAttr, String paramString2) {
/* 100 */     super(paramString1, paramArrayOfObject, paramException);
/*     */     
/* 102 */     this.uri = paramAttr;
/* 103 */     this.baseURI = paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setURI(Attr paramAttr) {
/* 111 */     this.uri = paramAttr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attr getURI() {
/* 119 */     return this.uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setbaseURI(String paramString) {
/* 127 */     this.baseURI = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getbaseURI() {
/* 135 */     return this.baseURI;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/resolver/ResourceResolverException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */