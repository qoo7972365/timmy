/*     */ package com.sun.corba.se.spi.resolver;
/*     */ 
/*     */ import com.sun.corba.se.impl.resolver.BootstrapResolverImpl;
/*     */ import com.sun.corba.se.impl.resolver.CompositeResolverImpl;
/*     */ import com.sun.corba.se.impl.resolver.FileResolverImpl;
/*     */ import com.sun.corba.se.impl.resolver.INSURLOperationImpl;
/*     */ import com.sun.corba.se.impl.resolver.LocalResolverImpl;
/*     */ import com.sun.corba.se.impl.resolver.ORBDefaultInitRefResolverImpl;
/*     */ import com.sun.corba.se.impl.resolver.ORBInitRefResolverImpl;
/*     */ import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.orb.Operation;
/*     */ import com.sun.corba.se.spi.orb.StringPair;
/*     */ import java.io.File;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResolverDefault
/*     */ {
/*     */   public static LocalResolver makeLocalResolver() {
/*  51 */     return (LocalResolver)new LocalResolverImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Resolver makeORBInitRefResolver(Operation paramOperation, StringPair[] paramArrayOfStringPair) {
/*  59 */     return (Resolver)new ORBInitRefResolverImpl(paramOperation, paramArrayOfStringPair);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Resolver makeORBDefaultInitRefResolver(Operation paramOperation, String paramString) {
/*  65 */     return (Resolver)new ORBDefaultInitRefResolverImpl(paramOperation, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Resolver makeBootstrapResolver(ORB paramORB, String paramString, int paramInt) {
/*  75 */     return (Resolver)new BootstrapResolverImpl(paramORB, paramString, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Resolver makeCompositeResolver(Resolver paramResolver1, Resolver paramResolver2) {
/*  85 */     return (Resolver)new CompositeResolverImpl(paramResolver1, paramResolver2);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Operation makeINSURLOperation(ORB paramORB, Resolver paramResolver) {
/*  90 */     return (Operation)new INSURLOperationImpl(paramORB, paramResolver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LocalResolver makeSplitLocalResolver(Resolver paramResolver, LocalResolver paramLocalResolver) {
/*  97 */     return (LocalResolver)new SplitLocalResolverImpl(paramResolver, paramLocalResolver);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Resolver makeFileResolver(ORB paramORB, File paramFile) {
/* 102 */     return (Resolver)new FileResolverImpl(paramORB, paramFile);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/resolver/ResolverDefault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */