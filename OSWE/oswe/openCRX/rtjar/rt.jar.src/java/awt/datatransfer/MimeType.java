/*     */ package java.awt.datatransfer;
/*     */ 
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MimeType
/*     */   implements Externalizable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -6568722458793895906L;
/*     */   private String primaryType;
/*     */   private String subType;
/*     */   private MimeTypeParameterList parameters;
/*     */   private static final String TSPECIALS = "()<>@,;:\\\"/[]?=";
/*     */   
/*     */   public MimeType() {}
/*     */   
/*     */   public MimeType(String paramString) throws MimeTypeParseException {
/*  67 */     parse(paramString);
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
/*     */   public MimeType(String paramString1, String paramString2) throws MimeTypeParseException {
/*  80 */     this(paramString1, paramString2, new MimeTypeParameterList());
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
/*     */   public MimeType(String paramString1, String paramString2, MimeTypeParameterList paramMimeTypeParameterList) throws MimeTypeParseException {
/*  96 */     if (isValidToken(paramString1)) {
/*  97 */       this.primaryType = paramString1.toLowerCase(Locale.ENGLISH);
/*     */     } else {
/*  99 */       throw new MimeTypeParseException("Primary type is invalid.");
/*     */     } 
/*     */ 
/*     */     
/* 103 */     if (isValidToken(paramString2)) {
/* 104 */       this.subType = paramString2.toLowerCase(Locale.ENGLISH);
/*     */     } else {
/* 106 */       throw new MimeTypeParseException("Sub type is invalid.");
/*     */     } 
/*     */     
/* 109 */     this.parameters = (MimeTypeParameterList)paramMimeTypeParameterList.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 116 */     int i = 0;
/* 117 */     i += this.primaryType.hashCode();
/* 118 */     i += this.subType.hashCode();
/* 119 */     i += this.parameters.hashCode();
/* 120 */     return i;
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
/*     */   public boolean equals(Object paramObject) {
/* 133 */     if (!(paramObject instanceof MimeType)) {
/* 134 */       return false;
/*     */     }
/* 136 */     MimeType mimeType = (MimeType)paramObject;
/* 137 */     return (this.primaryType
/* 138 */       .equals(mimeType.primaryType) && this.subType
/* 139 */       .equals(mimeType.subType) && this.parameters
/* 140 */       .equals(mimeType.parameters));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parse(String paramString) throws MimeTypeParseException {
/* 150 */     int i = paramString.indexOf('/');
/* 151 */     int j = paramString.indexOf(';');
/* 152 */     if (i < 0 && j < 0)
/*     */     {
/*     */       
/* 155 */       throw new MimeTypeParseException("Unable to find a sub type."); } 
/* 156 */     if (i < 0 && j >= 0)
/*     */     {
/*     */       
/* 159 */       throw new MimeTypeParseException("Unable to find a sub type."); } 
/* 160 */     if (i >= 0 && j < 0) {
/*     */       
/* 162 */       this
/* 163 */         .primaryType = paramString.substring(0, i).trim().toLowerCase(Locale.ENGLISH);
/* 164 */       this
/* 165 */         .subType = paramString.substring(i + 1).trim().toLowerCase(Locale.ENGLISH);
/* 166 */       this.parameters = new MimeTypeParameterList();
/* 167 */     } else if (i < j) {
/*     */       
/* 169 */       this
/* 170 */         .primaryType = paramString.substring(0, i).trim().toLowerCase(Locale.ENGLISH);
/* 171 */       this
/* 172 */         .subType = paramString.substring(i + 1, j).trim().toLowerCase(Locale.ENGLISH);
/* 173 */       this
/* 174 */         .parameters = new MimeTypeParameterList(paramString.substring(j));
/*     */     }
/*     */     else {
/*     */       
/* 178 */       throw new MimeTypeParseException("Unable to find a sub type.");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 184 */     if (!isValidToken(this.primaryType)) {
/* 185 */       throw new MimeTypeParseException("Primary type is invalid.");
/*     */     }
/*     */ 
/*     */     
/* 189 */     if (!isValidToken(this.subType)) {
/* 190 */       throw new MimeTypeParseException("Sub type is invalid.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrimaryType() {
/* 198 */     return this.primaryType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSubType() {
/* 205 */     return this.subType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MimeTypeParameterList getParameters() {
/* 212 */     return (MimeTypeParameterList)this.parameters.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getParameter(String paramString) {
/* 220 */     return this.parameters.get(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParameter(String paramString1, String paramString2) {
/* 230 */     this.parameters.set(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeParameter(String paramString) {
/* 239 */     this.parameters.remove(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 246 */     return getBaseType() + this.parameters.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseType() {
/* 254 */     return this.primaryType + "/" + this.subType;
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
/*     */   public boolean match(MimeType paramMimeType) {
/* 269 */     if (paramMimeType == null)
/* 270 */       return false; 
/* 271 */     return (this.primaryType.equals(paramMimeType.getPrimaryType()) && (this.subType
/* 272 */       .equals("*") || paramMimeType
/* 273 */       .getSubType().equals("*") || this.subType
/* 274 */       .equals(paramMimeType.getSubType())));
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
/*     */   public boolean match(String paramString) throws MimeTypeParseException {
/* 291 */     if (paramString == null)
/* 292 */       return false; 
/* 293 */     return match(new MimeType(paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeExternal(ObjectOutput paramObjectOutput) throws IOException {
/* 304 */     String str = toString();
/*     */     
/* 306 */     if (str.length() <= 65535) {
/* 307 */       paramObjectOutput.writeUTF(str);
/*     */     } else {
/* 309 */       paramObjectOutput.writeByte(0);
/* 310 */       paramObjectOutput.writeByte(0);
/* 311 */       paramObjectOutput.writeInt(str.length());
/* 312 */       paramObjectOutput.write(str.getBytes());
/*     */     } 
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
/*     */   public void readExternal(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 327 */     String str = paramObjectInput.readUTF();
/* 328 */     if (str == null || str.length() == 0) {
/* 329 */       byte[] arrayOfByte = new byte[paramObjectInput.readInt()];
/* 330 */       paramObjectInput.readFully(arrayOfByte);
/* 331 */       str = new String(arrayOfByte);
/*     */     } 
/*     */     try {
/* 334 */       parse(str);
/* 335 */     } catch (MimeTypeParseException mimeTypeParseException) {
/* 336 */       throw new IOException(mimeTypeParseException.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 346 */     MimeType mimeType = null;
/*     */     try {
/* 348 */       mimeType = (MimeType)super.clone();
/* 349 */     } catch (CloneNotSupportedException cloneNotSupportedException) {}
/*     */     
/* 351 */     mimeType.parameters = (MimeTypeParameterList)this.parameters.clone();
/* 352 */     return mimeType;
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
/*     */   private static boolean isTokenChar(char paramChar) {
/* 365 */     return (paramChar > ' ' && paramChar < '' && "()<>@,;:\\\"/[]?=".indexOf(paramChar) < 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isValidToken(String paramString) {
/* 374 */     int i = paramString.length();
/* 375 */     if (i > 0) {
/* 376 */       for (byte b = 0; b < i; b++) {
/* 377 */         char c = paramString.charAt(b);
/* 378 */         if (!isTokenChar(c)) {
/* 379 */           return false;
/*     */         }
/*     */       } 
/* 382 */       return true;
/*     */     } 
/* 384 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/datatransfer/MimeType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */