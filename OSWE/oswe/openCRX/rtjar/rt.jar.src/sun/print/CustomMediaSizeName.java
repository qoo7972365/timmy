/*     */ package sun.print;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import javax.print.attribute.EnumSyntax;
/*     */ import javax.print.attribute.standard.Media;
/*     */ import javax.print.attribute.standard.MediaSize;
/*     */ import javax.print.attribute.standard.MediaSizeName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CustomMediaSizeName
/*     */   extends MediaSizeName
/*     */ {
/*  36 */   private static ArrayList customStringTable = new ArrayList();
/*  37 */   private static ArrayList customEnumTable = new ArrayList();
/*     */   
/*     */   private String choiceName;
/*     */   
/*     */   private CustomMediaSizeName(int paramInt) {
/*  42 */     super(paramInt);
/*     */   }
/*     */   private MediaSizeName mediaName; private static final long serialVersionUID = 7412807582228043717L;
/*     */   
/*     */   private static synchronized int nextValue(String paramString) {
/*  47 */     customStringTable.add(paramString);
/*     */     
/*  49 */     return customStringTable.size() - 1;
/*     */   }
/*     */   
/*     */   public CustomMediaSizeName(String paramString) {
/*  53 */     super(nextValue(paramString));
/*  54 */     customEnumTable.add(this);
/*  55 */     this.choiceName = null;
/*  56 */     this.mediaName = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public CustomMediaSizeName(String paramString1, String paramString2, float paramFloat1, float paramFloat2) {
/*  61 */     super(nextValue(paramString1));
/*  62 */     this.choiceName = paramString2;
/*  63 */     customEnumTable.add(this);
/*  64 */     this.mediaName = null;
/*     */     try {
/*  66 */       this.mediaName = MediaSize.findMedia(paramFloat1, paramFloat2, 25400);
/*     */     }
/*  68 */     catch (IllegalArgumentException illegalArgumentException) {}
/*     */ 
/*     */ 
/*     */     
/*  72 */     if (this.mediaName != null) {
/*  73 */       MediaSize mediaSize = MediaSize.getMediaSizeForName(this.mediaName);
/*  74 */       if (mediaSize == null) {
/*  75 */         this.mediaName = null;
/*     */       } else {
/*  77 */         float f1 = mediaSize.getX(25400);
/*  78 */         float f2 = mediaSize.getY(25400);
/*  79 */         float f3 = Math.abs(f1 - paramFloat1);
/*  80 */         float f4 = Math.abs(f2 - paramFloat2);
/*  81 */         if (f3 > 0.1D || f4 > 0.1D) {
/*  82 */           this.mediaName = null;
/*     */         }
/*     */       } 
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
/*     */   public String getChoiceName() {
/*  97 */     return this.choiceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MediaSizeName getStandardMedia() {
/* 105 */     return this.mediaName;
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
/*     */   public static MediaSizeName findMedia(Media[] paramArrayOfMedia, float paramFloat1, float paramFloat2, int paramInt) {
/* 117 */     if (paramFloat1 <= 0.0F || paramFloat2 <= 0.0F || paramInt < 1) {
/* 118 */       throw new IllegalArgumentException("args must be +ve values");
/*     */     }
/*     */     
/* 121 */     if (paramArrayOfMedia == null || paramArrayOfMedia.length == 0) {
/* 122 */       throw new IllegalArgumentException("args must have valid array of media");
/*     */     }
/*     */     
/* 125 */     byte b1 = 0;
/* 126 */     MediaSizeName[] arrayOfMediaSizeName = new MediaSizeName[paramArrayOfMedia.length]; byte b2;
/* 127 */     for (b2 = 0; b2 < paramArrayOfMedia.length; b2++) {
/* 128 */       if (paramArrayOfMedia[b2] instanceof MediaSizeName) {
/* 129 */         arrayOfMediaSizeName[b1++] = (MediaSizeName)paramArrayOfMedia[b2];
/*     */       }
/*     */     } 
/*     */     
/* 133 */     if (b1 == 0) {
/* 134 */       return null;
/*     */     }
/*     */     
/* 137 */     b2 = 0;
/*     */     
/* 139 */     double d = (paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2);
/*     */ 
/*     */     
/* 142 */     float f1 = paramFloat1;
/* 143 */     float f2 = paramFloat2;
/*     */     
/* 145 */     for (byte b3 = 0; b3 < b1; b3++) {
/* 146 */       MediaSize mediaSize = MediaSize.getMediaSizeForName(arrayOfMediaSizeName[b3]);
/* 147 */       if (mediaSize != null) {
/*     */ 
/*     */         
/* 150 */         float[] arrayOfFloat = mediaSize.getSize(paramInt);
/* 151 */         if (paramFloat1 == arrayOfFloat[0] && paramFloat2 == arrayOfFloat[1]) {
/* 152 */           b2 = b3;
/*     */           break;
/*     */         } 
/* 155 */         f1 = paramFloat1 - arrayOfFloat[0];
/* 156 */         f2 = paramFloat2 - arrayOfFloat[1];
/* 157 */         double d1 = (f1 * f1 + f2 * f2);
/* 158 */         if (d1 < d) {
/* 159 */           d = d1;
/* 160 */           b2 = b3;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 165 */     return arrayOfMediaSizeName[b2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Media[] getSuperEnumTable() {
/* 172 */     return (Media[])super.getEnumValueTable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String[] getStringTable() {
/* 180 */     String[] arrayOfString = new String[customStringTable.size()];
/* 181 */     return (String[])customStringTable.toArray((Object[])arrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EnumSyntax[] getEnumValueTable() {
/* 188 */     MediaSizeName[] arrayOfMediaSizeName = new MediaSizeName[customEnumTable.size()];
/* 189 */     return (EnumSyntax[])customEnumTable.toArray((Object[])arrayOfMediaSizeName);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/CustomMediaSizeName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */