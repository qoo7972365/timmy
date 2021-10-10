/*    */ package com.sun.org.apache.xml.internal.security.utils.resolver.implementations;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*    */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverContext;
/*    */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
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
/*    */ public class ResolverAnonymous
/*    */   extends ResourceResolverSpi
/*    */ {
/* 40 */   private InputStream inStream = null;
/*    */ 
/*    */   
/*    */   public boolean engineIsThreadSafe() {
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ResolverAnonymous(String paramString) throws FileNotFoundException, IOException {
/* 53 */     this.inStream = new FileInputStream(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ResolverAnonymous(InputStream paramInputStream) {
/* 60 */     this.inStream = paramInputStream;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLSignatureInput engineResolveURI(ResourceResolverContext paramResourceResolverContext) {
/* 66 */     return new XMLSignatureInput(this.inStream);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean engineCanResolveURI(ResourceResolverContext paramResourceResolverContext) {
/* 74 */     if (paramResourceResolverContext.uriToResolve == null) {
/* 75 */       return true;
/*    */     }
/* 77 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] engineGetPropertyKeys() {
/* 82 */     return new String[0];
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/resolver/implementations/ResolverAnonymous.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */