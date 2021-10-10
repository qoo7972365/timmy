/*    */ package com.sun.org.apache.xml.internal.security.utils.resolver;
/*    */ 
/*    */ import org.w3c.dom.Attr;
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
/*    */ public class ResourceResolverContext
/*    */ {
/*    */   public final String uriToResolve;
/*    */   public final boolean secureValidation;
/*    */   public final String baseUri;
/*    */   public final Attr attr;
/*    */   
/*    */   public ResourceResolverContext(Attr paramAttr, String paramString, boolean paramBoolean) {
/* 30 */     this.attr = paramAttr;
/* 31 */     this.baseUri = paramString;
/* 32 */     this.secureValidation = paramBoolean;
/* 33 */     this.uriToResolve = (paramAttr != null) ? paramAttr.getValue() : null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/resolver/ResourceResolverContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */