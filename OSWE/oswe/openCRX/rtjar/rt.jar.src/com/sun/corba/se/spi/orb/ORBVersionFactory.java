/*     */ package com.sun.corba.se.spi.orb;
/*     */ 
/*     */ import com.sun.corba.se.impl.orb.ORBVersionImpl;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ORBVersionFactory
/*     */ {
/*     */   public static ORBVersion getFOREIGN() {
/*  38 */     return ORBVersionImpl.FOREIGN;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ORBVersion getOLD() {
/*  43 */     return ORBVersionImpl.OLD;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ORBVersion getNEW() {
/*  48 */     return ORBVersionImpl.NEW;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ORBVersion getJDK1_3_1_01() {
/*  53 */     return ORBVersionImpl.JDK1_3_1_01;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ORBVersion getNEWER() {
/*  58 */     return ORBVersionImpl.NEWER;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ORBVersion getPEORB() {
/*  63 */     return ORBVersionImpl.PEORB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ORBVersion getORBVersion() {
/*  70 */     return ORBVersionImpl.PEORB;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ORBVersion create(InputStream paramInputStream) {
/*  75 */     byte b = paramInputStream.read_octet();
/*  76 */     return byteToVersion(b);
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
/*     */   private static ORBVersion byteToVersion(byte paramByte) {
/* 103 */     switch (paramByte) { case 0:
/* 104 */         return ORBVersionImpl.FOREIGN;
/* 105 */       case 1: return ORBVersionImpl.OLD;
/* 106 */       case 2: return ORBVersionImpl.NEW;
/* 107 */       case 3: return ORBVersionImpl.JDK1_3_1_01;
/* 108 */       case 10: return ORBVersionImpl.NEWER;
/* 109 */       case 20: return ORBVersionImpl.PEORB; }
/* 110 */      return (ORBVersion)new ORBVersionImpl(paramByte);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/orb/ORBVersionFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */