/*     */ package javax.security.auth.callback;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChoiceCallback
/*     */   implements Callback, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3975664071579892167L;
/*     */   private String prompt;
/*     */   private String[] choices;
/*     */   private int defaultChoice;
/*     */   private boolean multipleSelectionsAllowed;
/*     */   private int[] selections;
/*     */   
/*     */   public ChoiceCallback(String paramString, String[] paramArrayOfString, int paramInt, boolean paramBoolean) {
/* 101 */     if (paramString == null || paramString.length() == 0 || paramArrayOfString == null || paramArrayOfString.length == 0 || paramInt < 0 || paramInt >= paramArrayOfString.length)
/*     */     {
/*     */       
/* 104 */       throw new IllegalArgumentException();
/*     */     }
/* 106 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 107 */       if (paramArrayOfString[b] == null || paramArrayOfString[b].length() == 0) {
/* 108 */         throw new IllegalArgumentException();
/*     */       }
/*     */     } 
/* 111 */     this.prompt = paramString;
/* 112 */     this.choices = paramArrayOfString;
/* 113 */     this.defaultChoice = paramInt;
/* 114 */     this.multipleSelectionsAllowed = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrompt() {
/* 125 */     return this.prompt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getChoices() {
/* 136 */     return this.choices;
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
/*     */   public int getDefaultChoice() {
/* 148 */     return this.defaultChoice;
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
/*     */   public boolean allowMultipleSelections() {
/* 160 */     return this.multipleSelectionsAllowed;
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
/*     */   public void setSelectedIndex(int paramInt) {
/* 174 */     this.selections = new int[1];
/* 175 */     this.selections[0] = paramInt;
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
/*     */   public void setSelectedIndexes(int[] paramArrayOfint) {
/* 193 */     if (!this.multipleSelectionsAllowed)
/* 194 */       throw new UnsupportedOperationException(); 
/* 195 */     this.selections = paramArrayOfint;
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
/*     */   public int[] getSelectedIndexes() {
/* 209 */     return this.selections;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/auth/callback/ChoiceCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */