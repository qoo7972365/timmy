/*     */ package javax.print.attribute;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Size2DSyntax
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 5584439964938660530L;
/*     */   private int x;
/*     */   private int y;
/*     */   public static final int INCH = 25400;
/*     */   public static final int MM = 1000;
/*     */   
/*     */   protected Size2DSyntax(float paramFloat1, float paramFloat2, int paramInt) {
/* 128 */     if (paramFloat1 < 0.0F) {
/* 129 */       throw new IllegalArgumentException("x < 0");
/*     */     }
/* 131 */     if (paramFloat2 < 0.0F) {
/* 132 */       throw new IllegalArgumentException("y < 0");
/*     */     }
/* 134 */     if (paramInt < 1) {
/* 135 */       throw new IllegalArgumentException("units < 1");
/*     */     }
/* 137 */     this.x = (int)(paramFloat1 * paramInt + 0.5F);
/* 138 */     this.y = (int)(paramFloat2 * paramInt + 0.5F);
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
/*     */   protected Size2DSyntax(int paramInt1, int paramInt2, int paramInt3) {
/* 156 */     if (paramInt1 < 0) {
/* 157 */       throw new IllegalArgumentException("x < 0");
/*     */     }
/* 159 */     if (paramInt2 < 0) {
/* 160 */       throw new IllegalArgumentException("y < 0");
/*     */     }
/* 162 */     if (paramInt3 < 1) {
/* 163 */       throw new IllegalArgumentException("units < 1");
/*     */     }
/* 165 */     this.x = paramInt1 * paramInt3;
/* 166 */     this.y = paramInt2 * paramInt3;
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
/*     */   private static float convertFromMicrometers(int paramInt1, int paramInt2) {
/* 185 */     if (paramInt2 < 1) {
/* 186 */       throw new IllegalArgumentException("units is < 1");
/*     */     }
/* 188 */     return paramInt1 / paramInt2;
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
/*     */   public float[] getSize(int paramInt) {
/* 205 */     return new float[] { getX(paramInt), getY(paramInt) };
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
/*     */   public float getX(int paramInt) {
/* 221 */     return convertFromMicrometers(this.x, paramInt);
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
/*     */   public float getY(int paramInt) {
/* 237 */     return convertFromMicrometers(this.y, paramInt);
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
/*     */   
/*     */   public String toString(int paramInt, String paramString) {
/* 260 */     StringBuffer stringBuffer = new StringBuffer();
/* 261 */     stringBuffer.append(getX(paramInt));
/* 262 */     stringBuffer.append('x');
/* 263 */     stringBuffer.append(getY(paramInt));
/* 264 */     if (paramString != null) {
/* 265 */       stringBuffer.append(' ');
/* 266 */       stringBuffer.append(paramString);
/*     */     } 
/* 268 */     return stringBuffer.toString();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 294 */     return (paramObject != null && paramObject instanceof Size2DSyntax && this.x == ((Size2DSyntax)paramObject).x && this.y == ((Size2DSyntax)paramObject).y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 304 */     return this.x & 0xFFFF | (this.y & 0xFFFF) << 16;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 315 */     StringBuffer stringBuffer = new StringBuffer();
/* 316 */     stringBuffer.append(this.x);
/* 317 */     stringBuffer.append('x');
/* 318 */     stringBuffer.append(this.y);
/* 319 */     stringBuffer.append(" um");
/* 320 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getXMicrometers() {
/* 330 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getYMicrometers() {
/* 340 */     return this.y;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/attribute/Size2DSyntax.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */