/*     */ package sun.corba;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.CDROutputObject;
/*     */ import com.sun.corba.se.impl.encoding.EncapsOutputStream;
/*     */ import com.sun.corba.se.impl.encoding.TypeCodeOutputStream;
/*     */ import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
/*     */ import com.sun.corba.se.pept.protocol.MessageMediator;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.protocol.CorbaMessageMediator;
/*     */ import com.sun.corba.se.spi.transport.CorbaConnection;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class OutputStreamFactory
/*     */ {
/*     */   public static TypeCodeOutputStream newTypeCodeOutputStream(final ORB orb) {
/*  52 */     return AccessController.<TypeCodeOutputStream>doPrivileged(new PrivilegedAction<TypeCodeOutputStream>()
/*     */         {
/*     */           public TypeCodeOutputStream run()
/*     */           {
/*  56 */             return new TypeCodeOutputStream(orb);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public static TypeCodeOutputStream newTypeCodeOutputStream(final ORB orb, final boolean littleEndian) {
/*  63 */     return AccessController.<TypeCodeOutputStream>doPrivileged(new PrivilegedAction<TypeCodeOutputStream>()
/*     */         {
/*     */           public TypeCodeOutputStream run()
/*     */           {
/*  67 */             return new TypeCodeOutputStream(orb, littleEndian);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public static EncapsOutputStream newEncapsOutputStream(final ORB orb) {
/*  74 */     return AccessController.<EncapsOutputStream>doPrivileged(new PrivilegedAction<EncapsOutputStream>()
/*     */         {
/*     */           public EncapsOutputStream run()
/*     */           {
/*  78 */             return new EncapsOutputStream(orb);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EncapsOutputStream newEncapsOutputStream(final ORB orb, final GIOPVersion giopVersion) {
/*  86 */     return AccessController.<EncapsOutputStream>doPrivileged(new PrivilegedAction<EncapsOutputStream>()
/*     */         {
/*     */           public EncapsOutputStream run()
/*     */           {
/*  90 */             return new EncapsOutputStream(orb, giopVersion);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EncapsOutputStream newEncapsOutputStream(final ORB orb, final boolean isLittleEndian) {
/*  98 */     return AccessController.<EncapsOutputStream>doPrivileged(new PrivilegedAction<EncapsOutputStream>()
/*     */         {
/*     */           public EncapsOutputStream run()
/*     */           {
/* 102 */             return new EncapsOutputStream(orb, isLittleEndian);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CDROutputObject newCDROutputObject(final ORB orb, final MessageMediator messageMediator, final Message header, final byte streamFormatVersion) {
/* 111 */     return AccessController.<CDROutputObject>doPrivileged(new PrivilegedAction<CDROutputObject>()
/*     */         {
/*     */           public CDROutputObject run()
/*     */           {
/* 115 */             return new CDROutputObject(orb, messageMediator, header, streamFormatVersion);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CDROutputObject newCDROutputObject(final ORB orb, final MessageMediator messageMediator, final Message header, final byte streamFormatVersion, final int strategy) {
/* 125 */     return AccessController.<CDROutputObject>doPrivileged(new PrivilegedAction<CDROutputObject>()
/*     */         {
/*     */           public CDROutputObject run()
/*     */           {
/* 129 */             return new CDROutputObject(orb, messageMediator, header, streamFormatVersion, strategy);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CDROutputObject newCDROutputObject(final ORB orb, final CorbaMessageMediator mediator, final GIOPVersion giopVersion, final CorbaConnection connection, final Message header, final byte streamFormatVersion) {
/* 139 */     return AccessController.<CDROutputObject>doPrivileged(new PrivilegedAction<CDROutputObject>()
/*     */         {
/*     */           public CDROutputObject run()
/*     */           {
/* 143 */             return new CDROutputObject(orb, mediator, giopVersion, connection, header, streamFormatVersion);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/corba/OutputStreamFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */