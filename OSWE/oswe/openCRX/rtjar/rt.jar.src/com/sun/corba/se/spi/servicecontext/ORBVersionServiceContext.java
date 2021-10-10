/*    */ package com.sun.corba.se.spi.servicecontext;
/*    */ 
/*    */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*    */ import com.sun.corba.se.spi.orb.ORBVersion;
/*    */ import com.sun.corba.se.spi.orb.ORBVersionFactory;
/*    */ import org.omg.CORBA.SystemException;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA_2_3.portable.InputStream;
/*    */ import org.omg.CORBA_2_3.portable.OutputStream;
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
/*    */ public class ORBVersionServiceContext
/*    */   extends ServiceContext
/*    */ {
/*    */   public static final int SERVICE_CONTEXT_ID = 1313165056;
/*    */   private ORBVersion version;
/*    */   
/*    */   public ORBVersionServiceContext() {
/* 77 */     this.version = ORBVersionFactory.getORBVersion(); this.version = ORBVersionFactory.getORBVersion(); } public ORBVersionServiceContext(ORBVersion paramORBVersion) { this.version = ORBVersionFactory.getORBVersion(); this.version = paramORBVersion; } public ORBVersionServiceContext(InputStream paramInputStream, GIOPVersion paramGIOPVersion) { super(paramInputStream, paramGIOPVersion); this.version = ORBVersionFactory.getORBVersion();
/*    */     this.version = ORBVersionFactory.create((InputStream)this.in); } public int getId() {
/*    */     return 1313165056;
/*    */   } public String toString() {
/* 81 */     return "ORBVersionServiceContext[ version=" + this.version + " ]";
/*    */   }
/*    */   
/*    */   public void writeData(OutputStream paramOutputStream) throws SystemException {
/*    */     this.version.write((OutputStream)paramOutputStream);
/*    */   }
/*    */   
/*    */   public ORBVersion getVersion() {
/*    */     return this.version;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/servicecontext/ORBVersionServiceContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */