/*     */ package sun.net.www.protocol.http.spnego;
/*     */ 
/*     */ import com.sun.security.jgss.ExtendedGSSContext;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import org.ietf.jgss.GSSContext;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.GSSName;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.net.www.protocol.http.HttpCallerInfo;
/*     */ import sun.net.www.protocol.http.Negotiator;
/*     */ import sun.security.action.GetBooleanAction;
/*     */ import sun.security.jgss.GSSManagerImpl;
/*     */ import sun.security.jgss.GSSUtil;
/*     */ import sun.security.jgss.HttpCaller;
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
/*     */ public class NegotiatorImpl
/*     */   extends Negotiator
/*     */ {
/*  53 */   private static final boolean DEBUG = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.security.krb5.debug"))).booleanValue();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GSSContext context;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] oneToken;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(HttpCallerInfo paramHttpCallerInfo) throws GSSException {
/*     */     Oid oid;
/*  70 */     if (paramHttpCallerInfo.scheme.equalsIgnoreCase("Kerberos")) {
/*     */       
/*  72 */       oid = GSSUtil.GSS_KRB5_MECH_OID;
/*     */     } else {
/*  74 */       String str1 = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */           {
/*     */             public String run() {
/*  77 */               return System.getProperty("http.auth.preference", "spnego");
/*     */             }
/*     */           });
/*     */ 
/*     */       
/*  82 */       if (str1.equalsIgnoreCase("kerberos")) {
/*  83 */         oid = GSSUtil.GSS_KRB5_MECH_OID;
/*     */       } else {
/*     */         
/*  86 */         oid = GSSUtil.GSS_SPNEGO_MECH_OID;
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     GSSManagerImpl gSSManagerImpl = new GSSManagerImpl(new HttpCaller(paramHttpCallerInfo));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     String str = "HTTP@" + paramHttpCallerInfo.host.toLowerCase();
/*     */     
/*  97 */     GSSName gSSName = gSSManagerImpl.createName(str, GSSName.NT_HOSTBASED_SERVICE);
/*     */     
/*  99 */     this.context = gSSManagerImpl.createContext(gSSName, oid, null, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     if (this.context instanceof ExtendedGSSContext) {
/* 106 */       ((ExtendedGSSContext)this.context).requestDelegPolicy(true);
/*     */     }
/* 108 */     this.oneToken = this.context.initSecContext(new byte[0], 0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NegotiatorImpl(HttpCallerInfo paramHttpCallerInfo) throws IOException {
/*     */     try {
/* 117 */       init(paramHttpCallerInfo);
/* 118 */     } catch (GSSException gSSException) {
/* 119 */       if (DEBUG) {
/* 120 */         System.out.println("Negotiate support not initiated, will fallback to other scheme if allowed. Reason:");
/*     */         
/* 122 */         gSSException.printStackTrace();
/*     */       } 
/* 124 */       IOException iOException = new IOException("Negotiate support not initiated");
/* 125 */       iOException.initCause(gSSException);
/* 126 */       throw iOException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] firstToken() {
/* 136 */     return this.oneToken;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] nextToken(byte[] paramArrayOfbyte) throws IOException {
/*     */     try {
/* 148 */       return this.context.initSecContext(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/* 149 */     } catch (GSSException gSSException) {
/* 150 */       if (DEBUG) {
/* 151 */         System.out.println("Negotiate support cannot continue. Reason:");
/* 152 */         gSSException.printStackTrace();
/*     */       } 
/* 154 */       IOException iOException = new IOException("Negotiate support cannot continue");
/* 155 */       iOException.initCause(gSSException);
/* 156 */       throw iOException;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/http/spnego/NegotiatorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */