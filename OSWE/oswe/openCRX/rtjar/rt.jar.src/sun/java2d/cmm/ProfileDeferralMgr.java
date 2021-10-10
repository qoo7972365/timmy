/*     */ package sun.java2d.cmm;
/*     */ 
/*     */ import java.awt.color.ProfileDataException;
/*     */ import java.util.Vector;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProfileDeferralMgr
/*     */ {
/*     */   public static boolean deferring = true;
/*     */   private static Vector<ProfileActivator> aVector;
/*     */   
/*     */   public static void registerDeferral(ProfileActivator paramProfileActivator) {
/*  51 */     if (!deferring) {
/*     */       return;
/*     */     }
/*  54 */     if (aVector == null) {
/*  55 */       aVector = new Vector<>(3, 3);
/*     */     }
/*  57 */     aVector.addElement(paramProfileActivator);
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
/*     */   public static void unregisterDeferral(ProfileActivator paramProfileActivator) {
/*  69 */     if (!deferring) {
/*     */       return;
/*     */     }
/*  72 */     if (aVector == null) {
/*     */       return;
/*     */     }
/*  75 */     aVector.removeElement(paramProfileActivator);
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
/*     */   public static void activateProfiles() {
/*  88 */     deferring = false;
/*  89 */     if (aVector == null) {
/*     */       return;
/*     */     }
/*  92 */     int i = aVector.size();
/*  93 */     for (ProfileActivator profileActivator : aVector) {
/*     */       try {
/*  95 */         profileActivator.activate();
/*  96 */       } catch (ProfileDataException profileDataException) {}
/*     */     } 
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
/* 114 */     aVector.removeAllElements();
/* 115 */     aVector = null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/cmm/ProfileDeferralMgr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */