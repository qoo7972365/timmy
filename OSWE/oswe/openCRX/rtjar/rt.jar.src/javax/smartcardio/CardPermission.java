/*     */ package javax.smartcardio;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.security.Permission;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CardPermission
/*     */   extends Permission
/*     */ {
/*     */   private static final long serialVersionUID = 7146787880530705613L;
/*     */   private static final int A_CONNECT = 1;
/*     */   private static final int A_EXCLUSIVE = 2;
/*     */   private static final int A_GET_BASIC_CHANNEL = 4;
/*     */   private static final int A_OPEN_LOGICAL_CHANNEL = 8;
/*     */   private static final int A_RESET = 16;
/*     */   private static final int A_TRANSMIT_CONTROL = 32;
/*     */   private static final int A_ALL = 63;
/*  87 */   private static final int[] ARRAY_MASKS = new int[] { 63, 1, 2, 4, 8, 16, 32 };
/*     */ 
/*     */   
/*     */   private static final String S_CONNECT = "connect";
/*     */ 
/*     */   
/*     */   private static final String S_EXCLUSIVE = "exclusive";
/*     */ 
/*     */   
/*     */   private static final String S_GET_BASIC_CHANNEL = "getBasicChannel";
/*     */   
/*     */   private static final String S_OPEN_LOGICAL_CHANNEL = "openLogicalChannel";
/*     */   
/*     */   private static final String S_RESET = "reset";
/*     */   
/*     */   private static final String S_TRANSMIT_CONTROL = "transmitControl";
/*     */   
/*     */   private static final String S_ALL = "*";
/*     */   
/* 106 */   private static final String[] ARRAY_STRINGS = new String[] { "*", "connect", "exclusive", "getBasicChannel", "openLogicalChannel", "reset", "transmitControl" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient int mask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile String actions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CardPermission(String paramString1, String paramString2) {
/* 141 */     super(paramString1);
/* 142 */     if (paramString1 == null) {
/* 143 */       throw new NullPointerException();
/*     */     }
/* 145 */     this.mask = getMask(paramString2);
/*     */   }
/*     */   
/*     */   private static int getMask(String paramString) {
/* 149 */     if (paramString == null || paramString.length() == 0) {
/* 150 */       throw new IllegalArgumentException("actions must not be empty");
/*     */     }
/*     */     
/*     */     int i;
/* 154 */     for (i = 0; i < ARRAY_STRINGS.length; i++) {
/* 155 */       if (paramString == ARRAY_STRINGS[i]) {
/* 156 */         return ARRAY_MASKS[i];
/*     */       }
/*     */     } 
/*     */     
/* 160 */     if (paramString.endsWith(",")) {
/* 161 */       throw new IllegalArgumentException("Invalid actions: '" + paramString + "'");
/*     */     }
/* 163 */     i = 0;
/* 164 */     String[] arrayOfString = paramString.split(",");
/*     */     
/* 166 */     for (String str : arrayOfString) {
/* 167 */       byte b = 0; while (true) { if (b < ARRAY_STRINGS.length) {
/* 168 */           if (ARRAY_STRINGS[b].equalsIgnoreCase(str)) {
/* 169 */             i |= ARRAY_MASKS[b]; break;
/*     */           }  b++;
/*     */           continue;
/*     */         } 
/* 173 */         throw new IllegalArgumentException("Invalid action: '" + str + "'"); }
/*     */     
/*     */     } 
/* 176 */     return i;
/*     */   }
/*     */   
/*     */   private static String getActions(int paramInt) {
/* 180 */     if (paramInt == 63) {
/* 181 */       return "*";
/*     */     }
/* 183 */     boolean bool = true;
/* 184 */     StringBuilder stringBuilder = new StringBuilder();
/* 185 */     for (byte b = 0; b < ARRAY_MASKS.length; b++) {
/* 186 */       int i = ARRAY_MASKS[b];
/* 187 */       if ((paramInt & i) == i) {
/* 188 */         if (!bool) {
/* 189 */           stringBuilder.append(",");
/*     */         } else {
/* 191 */           bool = false;
/*     */         } 
/* 193 */         stringBuilder.append(ARRAY_STRINGS[b]);
/*     */       } 
/*     */     } 
/* 196 */     return stringBuilder.toString();
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
/*     */   public String getActions() {
/* 209 */     if (this.actions == null) {
/* 210 */       this.actions = getActions(this.mask);
/*     */     }
/* 212 */     return this.actions;
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
/*     */   public boolean implies(Permission paramPermission) {
/* 232 */     if (!(paramPermission instanceof CardPermission)) {
/* 233 */       return false;
/*     */     }
/* 235 */     CardPermission cardPermission = (CardPermission)paramPermission;
/* 236 */     if ((this.mask & cardPermission.mask) != cardPermission.mask) {
/* 237 */       return false;
/*     */     }
/* 239 */     String str = getName();
/* 240 */     if (str.equals("*")) {
/* 241 */       return true;
/*     */     }
/* 243 */     if (str.equals(cardPermission.getName())) {
/* 244 */       return true;
/*     */     }
/* 246 */     return false;
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
/*     */   public boolean equals(Object paramObject) {
/* 266 */     if (this == paramObject) {
/* 267 */       return true;
/*     */     }
/* 269 */     if (!(paramObject instanceof CardPermission)) {
/* 270 */       return false;
/*     */     }
/* 272 */     CardPermission cardPermission = (CardPermission)paramObject;
/* 273 */     return (getName().equals(cardPermission.getName()) && this.mask == cardPermission.mask);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 282 */     return getName().hashCode() + 31 * this.mask;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 288 */     if (this.actions == null) {
/* 289 */       getActions();
/*     */     }
/* 291 */     paramObjectOutputStream.defaultWriteObject();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 297 */     paramObjectInputStream.defaultReadObject();
/* 298 */     this.mask = getMask(this.actions);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/smartcardio/CardPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */