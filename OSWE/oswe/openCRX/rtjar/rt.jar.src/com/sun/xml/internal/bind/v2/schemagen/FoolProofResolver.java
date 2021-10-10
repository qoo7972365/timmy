/*    */ package com.sun.xml.internal.bind.v2.schemagen;
/*    */ 
/*    */ import com.sun.xml.internal.bind.Util;
/*    */ import java.io.IOException;
/*    */ import java.util.logging.Logger;
/*    */ import javax.xml.bind.SchemaOutputResolver;
/*    */ import javax.xml.transform.Result;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class FoolProofResolver
/*    */   extends SchemaOutputResolver
/*    */ {
/* 45 */   private static final Logger logger = Util.getClassLogger();
/*    */   private final SchemaOutputResolver resolver;
/*    */   
/*    */   public FoolProofResolver(SchemaOutputResolver resolver) {
/* 49 */     assert resolver != null;
/* 50 */     this.resolver = resolver;
/*    */   }
/*    */   
/*    */   public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
/* 54 */     logger.entering(getClass().getName(), "createOutput", new Object[] { namespaceUri, suggestedFileName });
/* 55 */     Result r = this.resolver.createOutput(namespaceUri, suggestedFileName);
/* 56 */     if (r != null) {
/* 57 */       String sysId = r.getSystemId();
/* 58 */       logger.finer("system ID = " + sysId);
/* 59 */       if (sysId == null)
/*    */       {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 68 */         throw new AssertionError("system ID cannot be null"); } 
/*    */     } 
/* 70 */     logger.exiting(getClass().getName(), "createOutput", r);
/* 71 */     return r;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/schemagen/FoolProofResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */