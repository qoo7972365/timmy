/*     */ package sun.java2d.cmm;
/*     */ 
/*     */ import java.awt.color.CMMException;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.color.ICC_Profile;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ServiceLoader;
/*     */ import sun.security.action.GetPropertyAction;
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
/*     */ public class CMSManager
/*     */ {
/*     */   public static ColorSpace GRAYspace;
/*     */   public static ColorSpace LINEAR_RGBspace;
/*  48 */   private static PCMM cmmImpl = null;
/*     */   
/*     */   public static synchronized PCMM getModule() {
/*  51 */     if (cmmImpl != null) {
/*  52 */       return cmmImpl;
/*     */     }
/*     */     
/*  55 */     CMMServiceProvider cMMServiceProvider = AccessController.<CMMServiceProvider>doPrivileged(new PrivilegedAction<CMMServiceProvider>()
/*     */         {
/*     */           public CMMServiceProvider run() {
/*  58 */             String str = System.getProperty("sun.java2d.cmm", "sun.java2d.cmm.lcms.LcmsServiceProvider");
/*     */ 
/*     */ 
/*     */             
/*  62 */             ServiceLoader<CMMServiceProvider> serviceLoader = ServiceLoader.loadInstalled(CMMServiceProvider.class);
/*     */             
/*  64 */             CMMServiceProvider cMMServiceProvider = null;
/*     */             
/*  66 */             for (CMMServiceProvider cMMServiceProvider1 : serviceLoader) {
/*  67 */               cMMServiceProvider = cMMServiceProvider1;
/*  68 */               if (cMMServiceProvider1.getClass().getName().equals(str)) {
/*     */                 break;
/*     */               }
/*     */             } 
/*  72 */             return cMMServiceProvider;
/*     */           }
/*     */         });
/*     */     
/*  76 */     cmmImpl = cMMServiceProvider.getColorManagementModule();
/*     */     
/*  78 */     if (cmmImpl == null) {
/*  79 */       throw new CMMException("Cannot initialize Color Management System.No CM module found");
/*     */     }
/*     */ 
/*     */     
/*  83 */     GetPropertyAction getPropertyAction = new GetPropertyAction("sun.java2d.cmm.trace");
/*  84 */     String str = AccessController.<String>doPrivileged(getPropertyAction);
/*  85 */     if (str != null) {
/*  86 */       cmmImpl = new CMMTracer(cmmImpl);
/*     */     }
/*     */     
/*  89 */     return cmmImpl;
/*     */   }
/*     */   
/*     */   static synchronized boolean canCreateModule() {
/*  93 */     return (cmmImpl == null);
/*     */   }
/*     */   
/*     */   public static class CMMTracer
/*     */     implements PCMM
/*     */   {
/*     */     PCMM tcmm;
/*     */     String cName;
/*     */     
/*     */     public CMMTracer(PCMM param1PCMM) {
/* 103 */       this.tcmm = param1PCMM;
/* 104 */       this.cName = param1PCMM.getClass().getName();
/*     */     }
/*     */     
/*     */     public Profile loadProfile(byte[] param1ArrayOfbyte) {
/* 108 */       System.err.print(this.cName + ".loadProfile");
/* 109 */       Profile profile = this.tcmm.loadProfile(param1ArrayOfbyte);
/* 110 */       System.err.printf("(ID=%s)\n", new Object[] { profile.toString() });
/* 111 */       return profile;
/*     */     }
/*     */     
/*     */     public void freeProfile(Profile param1Profile) {
/* 115 */       System.err.printf(this.cName + ".freeProfile(ID=%s)\n", new Object[] { param1Profile.toString() });
/* 116 */       this.tcmm.freeProfile(param1Profile);
/*     */     }
/*     */     
/*     */     public int getProfileSize(Profile param1Profile) {
/* 120 */       System.err.print(this.cName + ".getProfileSize(ID=" + param1Profile + ")");
/* 121 */       int i = this.tcmm.getProfileSize(param1Profile);
/* 122 */       System.err.println("=" + i);
/* 123 */       return i;
/*     */     }
/*     */     
/*     */     public void getProfileData(Profile param1Profile, byte[] param1ArrayOfbyte) {
/* 127 */       System.err.print(this.cName + ".getProfileData(ID=" + param1Profile + ") ");
/* 128 */       System.err.println("requested " + param1ArrayOfbyte.length + " byte(s)");
/* 129 */       this.tcmm.getProfileData(param1Profile, param1ArrayOfbyte);
/*     */     }
/*     */     
/*     */     public int getTagSize(Profile param1Profile, int param1Int) {
/* 133 */       System.err.printf(this.cName + ".getTagSize(ID=%x, TagSig=%s)", new Object[] { param1Profile, 
/* 134 */             signatureToString(param1Int) });
/* 135 */       int i = this.tcmm.getTagSize(param1Profile, param1Int);
/* 136 */       System.err.println("=" + i);
/* 137 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     public void getTagData(Profile param1Profile, int param1Int, byte[] param1ArrayOfbyte) {
/* 142 */       System.err.printf(this.cName + ".getTagData(ID=%x, TagSig=%s)", new Object[] { param1Profile, 
/* 143 */             signatureToString(param1Int) });
/* 144 */       System.err.println(" requested " + param1ArrayOfbyte.length + " byte(s)");
/* 145 */       this.tcmm.getTagData(param1Profile, param1Int, param1ArrayOfbyte);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setTagData(Profile param1Profile, int param1Int, byte[] param1ArrayOfbyte) {
/* 150 */       System.err.print(this.cName + ".setTagData(ID=" + param1Profile + ", TagSig=" + param1Int + ")");
/*     */       
/* 152 */       System.err.println(" sending " + param1ArrayOfbyte.length + " byte(s)");
/* 153 */       this.tcmm.setTagData(param1Profile, param1Int, param1ArrayOfbyte);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ColorTransform createTransform(ICC_Profile param1ICC_Profile, int param1Int1, int param1Int2) {
/* 160 */       System.err.println(this.cName + ".createTransform(ICC_Profile,int,int)");
/* 161 */       return this.tcmm.createTransform(param1ICC_Profile, param1Int1, param1Int2);
/*     */     }
/*     */     
/*     */     public ColorTransform createTransform(ColorTransform[] param1ArrayOfColorTransform) {
/* 165 */       System.err.println(this.cName + ".createTransform(ColorTransform[])");
/* 166 */       return this.tcmm.createTransform(param1ArrayOfColorTransform);
/*     */     }
/*     */     
/*     */     private static String signatureToString(int param1Int) {
/* 170 */       return String.format("%c%c%c%c", new Object[] {
/* 171 */             Character.valueOf((char)(0xFF & param1Int >> 24)), 
/* 172 */             Character.valueOf((char)(0xFF & param1Int >> 16)), 
/* 173 */             Character.valueOf((char)(0xFF & param1Int >> 8)), 
/* 174 */             Character.valueOf((char)(0xFF & param1Int))
/*     */           });
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/cmm/CMSManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */