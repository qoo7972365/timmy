/*     */ package javax.swing.text.html;
/*     */ 
/*     */ import java.util.StringTokenizer;
/*     */ import javax.swing.SizeRequirements;
/*     */ import javax.swing.text.AttributeSet;
/*     */ import javax.swing.text.BoxView;
/*     */ import javax.swing.text.Element;
/*     */ import javax.swing.text.View;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class FrameSetView
/*     */   extends BoxView
/*     */ {
/*     */   String[] children;
/*     */   int[] percentChildren;
/*     */   int[] absoluteChildren;
/*     */   int[] relativeChildren;
/*     */   int percentTotals;
/*     */   int absoluteTotals;
/*     */   int relativeTotals;
/*     */   
/*     */   public FrameSetView(Element paramElement, int paramInt) {
/*  62 */     super(paramElement, paramInt);
/*  63 */     this.children = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] parseRowColSpec(HTML.Attribute paramAttribute) {
/*  74 */     AttributeSet attributeSet = getElement().getAttributes();
/*  75 */     String str = "*";
/*  76 */     if (attributeSet != null && 
/*  77 */       attributeSet.getAttribute(paramAttribute) != null) {
/*  78 */       str = (String)attributeSet.getAttribute(paramAttribute);
/*     */     }
/*     */ 
/*     */     
/*  82 */     StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/*  83 */     int i = stringTokenizer.countTokens();
/*  84 */     int j = getViewCount();
/*  85 */     String[] arrayOfString = new String[Math.max(i, j)];
/*  86 */     byte b = 0;
/*  87 */     for (; b < i; b++) {
/*  88 */       arrayOfString[b] = stringTokenizer.nextToken().trim();
/*     */ 
/*     */ 
/*     */       
/*  92 */       if (arrayOfString[b].equals("100%")) {
/*  93 */         arrayOfString[b] = "*";
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  98 */     for (; b < arrayOfString.length; b++) {
/*  99 */       arrayOfString[b] = "*";
/*     */     }
/* 101 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init() {
/* 111 */     if (getAxis() == 1) {
/* 112 */       this.children = parseRowColSpec(HTML.Attribute.ROWS);
/*     */     } else {
/* 114 */       this.children = parseRowColSpec(HTML.Attribute.COLS);
/*     */     } 
/* 116 */     this.percentChildren = new int[this.children.length];
/* 117 */     this.relativeChildren = new int[this.children.length];
/* 118 */     this.absoluteChildren = new int[this.children.length];
/*     */     byte b;
/* 120 */     for (b = 0; b < this.children.length; b++) {
/* 121 */       this.percentChildren[b] = -1;
/* 122 */       this.relativeChildren[b] = -1;
/* 123 */       this.absoluteChildren[b] = -1;
/*     */       
/* 125 */       if (this.children[b].endsWith("*")) {
/* 126 */         if (this.children[b].length() > 1) {
/* 127 */           this.relativeChildren[b] = 
/* 128 */             Integer.parseInt(this.children[b].substring(0, this.children[b]
/* 129 */                 .length() - 1));
/* 130 */           this.relativeTotals += this.relativeChildren[b];
/*     */         } else {
/* 132 */           this.relativeChildren[b] = 1;
/* 133 */           this.relativeTotals++;
/*     */         } 
/* 135 */       } else if (this.children[b].indexOf('%') != -1) {
/* 136 */         this.percentChildren[b] = parseDigits(this.children[b]);
/* 137 */         this.percentTotals += this.percentChildren[b];
/*     */       } else {
/* 139 */         this.absoluteChildren[b] = Integer.parseInt(this.children[b]);
/*     */       } 
/*     */     } 
/* 142 */     if (this.percentTotals > 100) {
/* 143 */       for (b = 0; b < this.percentChildren.length; b++) {
/* 144 */         if (this.percentChildren[b] > 0) {
/* 145 */           this.percentChildren[b] = this.percentChildren[b] * 100 / this.percentTotals;
/*     */         }
/*     */       } 
/*     */       
/* 149 */       this.percentTotals = 100;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void layoutMajorAxis(int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/* 172 */     if (this.children == null) {
/* 173 */       init();
/*     */     }
/* 175 */     SizeRequirements.calculateTiledPositions(paramInt1, null, 
/* 176 */         getChildRequests(paramInt1, paramInt2), paramArrayOfint1, paramArrayOfint2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SizeRequirements[] getChildRequests(int paramInt1, int paramInt2) {
/* 183 */     int[] arrayOfInt = new int[this.children.length];
/*     */     
/* 185 */     spread(paramInt1, arrayOfInt);
/* 186 */     int i = getViewCount();
/* 187 */     SizeRequirements[] arrayOfSizeRequirements = new SizeRequirements[i];
/* 188 */     for (byte b1 = 0, b2 = 0; b1 < i; b1++) {
/* 189 */       View view = getView(b1);
/* 190 */       if (view instanceof FrameView || view instanceof FrameSetView) {
/* 191 */         arrayOfSizeRequirements[b1] = new SizeRequirements((int)view.getMinimumSpan(paramInt2), arrayOfInt[b2], 
/*     */             
/* 193 */             (int)view.getMaximumSpan(paramInt2), 0.5F);
/*     */         
/* 195 */         b2++;
/*     */       } else {
/* 197 */         int j = (int)view.getMinimumSpan(paramInt2);
/* 198 */         int k = (int)view.getPreferredSpan(paramInt2);
/* 199 */         int m = (int)view.getMaximumSpan(paramInt2);
/* 200 */         float f = view.getAlignment(paramInt2);
/* 201 */         arrayOfSizeRequirements[b1] = new SizeRequirements(j, k, m, f);
/*     */       } 
/*     */     } 
/* 204 */     return arrayOfSizeRequirements;
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
/*     */   private void spread(int paramInt, int[] paramArrayOfint) {
/* 216 */     if (paramInt == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 220 */     int i = 0;
/* 221 */     int j = paramInt;
/*     */ 
/*     */     
/*     */     byte b;
/*     */     
/* 226 */     for (b = 0; b < paramArrayOfint.length; b++) {
/* 227 */       if (this.absoluteChildren[b] > 0) {
/* 228 */         paramArrayOfint[b] = this.absoluteChildren[b];
/* 229 */         j -= paramArrayOfint[b];
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 235 */     i = j;
/* 236 */     for (b = 0; b < paramArrayOfint.length; b++) {
/* 237 */       if (this.percentChildren[b] > 0 && i > 0) {
/* 238 */         paramArrayOfint[b] = this.percentChildren[b] * i / 100;
/* 239 */         j -= paramArrayOfint[b];
/* 240 */       } else if (this.percentChildren[b] > 0 && i <= 0) {
/* 241 */         paramArrayOfint[b] = paramInt / paramArrayOfint.length;
/* 242 */         j -= paramArrayOfint[b];
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 247 */     if (j > 0 && this.relativeTotals > 0) {
/* 248 */       for (b = 0; b < paramArrayOfint.length; b++) {
/* 249 */         if (this.relativeChildren[b] > 0) {
/* 250 */           paramArrayOfint[b] = j * this.relativeChildren[b] / this.relativeTotals;
/*     */         }
/*     */       }
/*     */     
/* 254 */     } else if (j > 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 273 */       float f = (paramInt - j);
/* 274 */       float[] arrayOfFloat = new float[paramArrayOfint.length];
/* 275 */       j = paramInt; byte b1;
/* 276 */       for (b1 = 0; b1 < paramArrayOfint.length; b1++) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 281 */         arrayOfFloat[b1] = paramArrayOfint[b1] / f * 100.0F;
/* 282 */         paramArrayOfint[b1] = (int)(paramInt * arrayOfFloat[b1] / 100.0F);
/* 283 */         j -= paramArrayOfint[b1];
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 290 */       b1 = 0;
/* 291 */       while (j != 0) {
/* 292 */         if (j < 0) {
/* 293 */           paramArrayOfint[b1++] = paramArrayOfint[b1++] - 1;
/* 294 */           j++;
/*     */         } else {
/*     */           
/* 297 */           paramArrayOfint[b1++] = paramArrayOfint[b1++] + 1;
/* 298 */           j--;
/*     */         } 
/*     */ 
/*     */         
/* 302 */         if (b1 == paramArrayOfint.length) b1 = 0;
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int parseDigits(String paramString) {
/* 312 */     int i = 0;
/* 313 */     for (byte b = 0; b < paramString.length(); b++) {
/* 314 */       char c = paramString.charAt(b);
/* 315 */       if (Character.isDigit(c)) {
/* 316 */         i = i * 10 + Character.digit(c, 10);
/*     */       }
/*     */     } 
/* 319 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/FrameSetView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */