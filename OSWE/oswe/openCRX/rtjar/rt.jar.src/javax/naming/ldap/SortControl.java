/*     */ package javax.naming.ldap;
/*     */ 
/*     */ import com.sun.jndi.ldap.BerEncoder;
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SortControl
/*     */   extends BasicControl
/*     */ {
/*     */   public static final String OID = "1.2.840.113556.1.4.473";
/*     */   private static final long serialVersionUID = -1965961680233330744L;
/*     */   
/*     */   public SortControl(String paramString, boolean paramBoolean) throws IOException {
/* 135 */     super("1.2.840.113556.1.4.473", paramBoolean, null);
/* 136 */     this.value = setEncodedValue(new SortKey[] { new SortKey(paramString) });
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
/*     */   public SortControl(String[] paramArrayOfString, boolean paramBoolean) throws IOException {
/* 158 */     super("1.2.840.113556.1.4.473", paramBoolean, null);
/* 159 */     SortKey[] arrayOfSortKey = new SortKey[paramArrayOfString.length];
/* 160 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 161 */       arrayOfSortKey[b] = new SortKey(paramArrayOfString[b]);
/*     */     }
/* 163 */     this.value = setEncodedValue(arrayOfSortKey);
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
/*     */   public SortControl(SortKey[] paramArrayOfSortKey, boolean paramBoolean) throws IOException {
/* 184 */     super("1.2.840.113556.1.4.473", paramBoolean, null);
/* 185 */     this.value = setEncodedValue(paramArrayOfSortKey);
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
/*     */   private byte[] setEncodedValue(SortKey[] paramArrayOfSortKey) throws IOException {
/* 201 */     BerEncoder berEncoder = new BerEncoder(30 * paramArrayOfSortKey.length + 10);
/*     */ 
/*     */     
/* 204 */     berEncoder.beginSeq(48);
/*     */     
/* 206 */     for (byte b = 0; b < paramArrayOfSortKey.length; b++) {
/* 207 */       berEncoder.beginSeq(48);
/* 208 */       berEncoder.encodeString(paramArrayOfSortKey[b].getAttributeID(), true);
/*     */       String str;
/* 210 */       if ((str = paramArrayOfSortKey[b].getMatchingRuleID()) != null) {
/* 211 */         berEncoder.encodeString(str, 128, true);
/*     */       }
/* 213 */       if (!paramArrayOfSortKey[b].isAscending()) {
/* 214 */         berEncoder.encodeBoolean(true, 129);
/*     */       }
/* 216 */       berEncoder.endSeq();
/*     */     } 
/* 218 */     berEncoder.endSeq();
/*     */     
/* 220 */     return berEncoder.getTrimmedBuf();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/ldap/SortControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */