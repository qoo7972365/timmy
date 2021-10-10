/*     */ package sun.corba;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.EncapsInputStream;
/*     */ import com.sun.corba.se.impl.encoding.TypeCodeInputStream;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.org.omg.SendingContext.CodeBase;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import org.omg.CORBA.ORB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncapsInputStreamFactory
/*     */ {
/*     */   public static EncapsInputStream newEncapsInputStream(final ORB orb, final byte[] buf, final int size, final boolean littleEndian, final GIOPVersion version) {
/*  45 */     return 
/*  46 */       AccessController.<EncapsInputStream>doPrivileged(new PrivilegedAction<EncapsInputStream>()
/*     */         {
/*     */           public EncapsInputStream run() {
/*  49 */             return new EncapsInputStream(orb, buf, size, littleEndian, version);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EncapsInputStream newEncapsInputStream(final ORB orb, final ByteBuffer byteBuffer, final int size, final boolean littleEndian, final GIOPVersion version) {
/*  59 */     return 
/*  60 */       AccessController.<EncapsInputStream>doPrivileged(new PrivilegedAction<EncapsInputStream>()
/*     */         {
/*     */           public EncapsInputStream run() {
/*  63 */             return new EncapsInputStream(orb, byteBuffer, size, littleEndian, version);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EncapsInputStream newEncapsInputStream(final ORB orb, final byte[] data, final int size) {
/*  71 */     return 
/*  72 */       AccessController.<EncapsInputStream>doPrivileged(new PrivilegedAction<EncapsInputStream>()
/*     */         {
/*     */           public EncapsInputStream run() {
/*  75 */             return new EncapsInputStream(orb, data, size);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public static EncapsInputStream newEncapsInputStream(final EncapsInputStream eis) {
/*  82 */     return 
/*  83 */       AccessController.<EncapsInputStream>doPrivileged(new PrivilegedAction<EncapsInputStream>()
/*     */         {
/*     */           public EncapsInputStream run() {
/*  86 */             return new EncapsInputStream(eis);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EncapsInputStream newEncapsInputStream(final ORB orb, final byte[] data, final int size, final GIOPVersion version) {
/*  94 */     return 
/*  95 */       AccessController.<EncapsInputStream>doPrivileged(new PrivilegedAction<EncapsInputStream>()
/*     */         {
/*     */           public EncapsInputStream run() {
/*  98 */             return new EncapsInputStream(orb, data, size, version);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EncapsInputStream newEncapsInputStream(final ORB orb, final byte[] data, final int size, final GIOPVersion version, final CodeBase codeBase) {
/* 106 */     return 
/* 107 */       AccessController.<EncapsInputStream>doPrivileged(new PrivilegedAction<EncapsInputStream>()
/*     */         {
/*     */           public EncapsInputStream run() {
/* 110 */             return new EncapsInputStream(orb, data, size, version, codeBase);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeCodeInputStream newTypeCodeInputStream(final ORB orb, final byte[] buf, final int size, final boolean littleEndian, final GIOPVersion version) {
/* 119 */     return 
/* 120 */       AccessController.<TypeCodeInputStream>doPrivileged(new PrivilegedAction<TypeCodeInputStream>()
/*     */         {
/*     */           public TypeCodeInputStream run() {
/* 123 */             return new TypeCodeInputStream(orb, buf, size, littleEndian, version);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeCodeInputStream newTypeCodeInputStream(final ORB orb, final ByteBuffer byteBuffer, final int size, final boolean littleEndian, final GIOPVersion version) {
/* 133 */     return 
/* 134 */       AccessController.<TypeCodeInputStream>doPrivileged(new PrivilegedAction<TypeCodeInputStream>()
/*     */         {
/*     */           public TypeCodeInputStream run() {
/* 137 */             return new TypeCodeInputStream(orb, byteBuffer, size, littleEndian, version);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeCodeInputStream newTypeCodeInputStream(final ORB orb, final byte[] data, final int size) {
/* 145 */     return 
/* 146 */       AccessController.<TypeCodeInputStream>doPrivileged(new PrivilegedAction<TypeCodeInputStream>()
/*     */         {
/*     */           public TypeCodeInputStream run() {
/* 149 */             return new TypeCodeInputStream(orb, data, size);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/corba/EncapsInputStreamFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */