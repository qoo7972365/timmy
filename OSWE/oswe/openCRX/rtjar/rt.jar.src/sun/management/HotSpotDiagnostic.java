/*     */ package sun.management;
/*     */ 
/*     */ import com.sun.management.HotSpotDiagnosticMXBean;
/*     */ import com.sun.management.VMOption;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.management.ObjectName;
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
/*     */ public class HotSpotDiagnostic
/*     */   implements HotSpotDiagnosticMXBean
/*     */ {
/*     */   public void dumpHeap(String paramString, boolean paramBoolean) throws IOException {
/*  47 */     String str = "jdk.management.heapdump.allowAnyFileSuffix";
/*  48 */     PrivilegedAction<Boolean> privilegedAction = () -> Boolean.valueOf(Boolean.parseBoolean(System.getProperty(paramString, "false")));
/*  49 */     boolean bool = ((Boolean)AccessController.<Boolean>doPrivileged(privilegedAction)).booleanValue();
/*  50 */     if (!bool && !paramString.endsWith(".hprof")) {
/*  51 */       throw new IllegalArgumentException("heapdump file must have .hprof extention");
/*     */     }
/*     */     
/*  54 */     SecurityManager securityManager = System.getSecurityManager();
/*  55 */     if (securityManager != null) {
/*  56 */       securityManager.checkWrite(paramString);
/*  57 */       Util.checkControlAccess();
/*     */     } 
/*     */     
/*  60 */     dumpHeap0(paramString, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<VMOption> getDiagnosticOptions() {
/*  66 */     List<Flag> list = Flag.getAllFlags();
/*  67 */     ArrayList<VMOption> arrayList = new ArrayList();
/*  68 */     for (Flag flag : list) {
/*  69 */       if (flag.isWriteable() && flag.isExternal()) {
/*  70 */         arrayList.add(flag.getVMOption());
/*     */       }
/*     */     } 
/*  73 */     return arrayList;
/*     */   }
/*     */   
/*     */   public VMOption getVMOption(String paramString) {
/*  77 */     if (paramString == null) {
/*  78 */       throw new NullPointerException("name cannot be null");
/*     */     }
/*     */     
/*  81 */     Flag flag = Flag.getFlag(paramString);
/*  82 */     if (flag == null) {
/*  83 */       throw new IllegalArgumentException("VM option \"" + paramString + "\" does not exist");
/*     */     }
/*     */     
/*  86 */     return flag.getVMOption();
/*     */   }
/*     */   
/*     */   public void setVMOption(String paramString1, String paramString2) {
/*  90 */     if (paramString1 == null) {
/*  91 */       throw new NullPointerException("name cannot be null");
/*     */     }
/*  93 */     if (paramString2 == null) {
/*  94 */       throw new NullPointerException("value cannot be null");
/*     */     }
/*     */     
/*  97 */     Util.checkControlAccess();
/*  98 */     Flag flag = Flag.getFlag(paramString1);
/*  99 */     if (flag == null) {
/* 100 */       throw new IllegalArgumentException("VM option \"" + paramString1 + "\" does not exist");
/*     */     }
/*     */     
/* 103 */     if (!flag.isWriteable()) {
/* 104 */       throw new IllegalArgumentException("VM Option \"" + paramString1 + "\" is not writeable");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 109 */     Object object = flag.getValue();
/* 110 */     if (object instanceof Long) {
/*     */       try {
/* 112 */         long l = Long.parseLong(paramString2);
/* 113 */         Flag.setLongValue(paramString1, l);
/* 114 */       } catch (NumberFormatException numberFormatException) {
/* 115 */         IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Invalid value: VM Option \"" + paramString1 + "\" expects numeric value");
/*     */ 
/*     */ 
/*     */         
/* 119 */         illegalArgumentException.initCause(numberFormatException);
/* 120 */         throw illegalArgumentException;
/*     */       } 
/* 122 */     } else if (object instanceof Boolean) {
/* 123 */       if (!paramString2.equalsIgnoreCase("true") && 
/* 124 */         !paramString2.equalsIgnoreCase("false")) {
/* 125 */         throw new IllegalArgumentException("Invalid value: VM Option \"" + paramString1 + "\" expects \"true\" or \"false\".");
/*     */       }
/*     */ 
/*     */       
/* 129 */       Flag.setBooleanValue(paramString1, Boolean.parseBoolean(paramString2));
/* 130 */     } else if (object instanceof String) {
/* 131 */       Flag.setStringValue(paramString1, paramString2);
/*     */     } else {
/* 133 */       throw new IllegalArgumentException("VM Option \"" + paramString1 + "\" is of an unsupported type: " + object
/*     */           
/* 135 */           .getClass().getName());
/*     */     } 
/*     */   }
/*     */   
/*     */   public ObjectName getObjectName() {
/* 140 */     return Util.newObjectName("com.sun.management:type=HotSpotDiagnostic");
/*     */   }
/*     */   
/*     */   private native void dumpHeap0(String paramString, boolean paramBoolean) throws IOException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/HotSpotDiagnostic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */