/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.PatternSyntaxException;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.plaf.FontUIResource;
/*     */ import sun.swing.BakedArrayList;
/*     */ import sun.swing.plaf.synth.DefaultSynthStyle;
/*     */ import sun.swing.plaf.synth.StyleAssociation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DefaultSynthStyleFactory
/*     */   extends SynthStyleFactory
/*     */ {
/*  75 */   private BakedArrayList _tmpList = new BakedArrayList(5);
/*  76 */   private List<StyleAssociation> _styles = new ArrayList<>();
/*  77 */   private Map<BakedArrayList, SynthStyle> _resolvedStyles = new HashMap<>();
/*     */   
/*     */   public static final int NAME = 0;
/*     */   
/*     */   public synchronized void addStyle(DefaultSynthStyle paramDefaultSynthStyle, String paramString, int paramInt) throws PatternSyntaxException {
/*  82 */     if (paramString == null)
/*     */     {
/*  84 */       paramString = ".*";
/*     */     }
/*  86 */     if (paramInt == 0) {
/*  87 */       this._styles.add(StyleAssociation.createStyleAssociation(paramString, paramDefaultSynthStyle, paramInt));
/*     */     
/*     */     }
/*  90 */     else if (paramInt == 1) {
/*  91 */       this._styles.add(StyleAssociation.createStyleAssociation(paramString
/*  92 */             .toLowerCase(), paramDefaultSynthStyle, paramInt));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int REGION = 1;
/*     */   
/*     */   private SynthStyle _defaultStyle;
/*     */ 
/*     */   
/*     */   public synchronized SynthStyle getStyle(JComponent paramJComponent, Region paramRegion) {
/* 103 */     BakedArrayList bakedArrayList = this._tmpList;
/*     */     
/* 105 */     bakedArrayList.clear();
/* 106 */     getMatchingStyles(bakedArrayList, paramJComponent, paramRegion);
/*     */     
/* 108 */     if (bakedArrayList.size() == 0) {
/* 109 */       return getDefaultStyle();
/*     */     }
/*     */     
/* 112 */     bakedArrayList.cacheHashCode();
/* 113 */     SynthStyle synthStyle = getCachedStyle(bakedArrayList);
/*     */     
/* 115 */     if (synthStyle == null) {
/* 116 */       synthStyle = mergeStyles(bakedArrayList);
/*     */       
/* 118 */       if (synthStyle != null) {
/* 119 */         cacheStyle(bakedArrayList, synthStyle);
/*     */       }
/*     */     } 
/* 122 */     return synthStyle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SynthStyle getDefaultStyle() {
/* 129 */     if (this._defaultStyle == null) {
/* 130 */       this._defaultStyle = new DefaultSynthStyle();
/* 131 */       ((DefaultSynthStyle)this._defaultStyle).setFont(new FontUIResource("Dialog", 0, 12));
/*     */     } 
/*     */     
/* 134 */     return this._defaultStyle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getMatchingStyles(List<SynthStyle> paramList, JComponent paramJComponent, Region paramRegion) {
/* 143 */     String str1 = paramRegion.getLowerCaseName();
/* 144 */     String str2 = paramJComponent.getName();
/*     */     
/* 146 */     if (str2 == null) {
/* 147 */       str2 = "";
/*     */     }
/* 149 */     for (int i = this._styles.size() - 1; i >= 0; i--) {
/* 150 */       String str; StyleAssociation styleAssociation = this._styles.get(i);
/*     */ 
/*     */       
/* 153 */       if (styleAssociation.getID() == 0) {
/* 154 */         str = str2;
/*     */       } else {
/*     */         
/* 157 */         str = str1;
/*     */       } 
/*     */       
/* 160 */       if (styleAssociation.matches(str) && paramList.indexOf(styleAssociation.getStyle()) == -1) {
/* 161 */         paramList.add(styleAssociation.getStyle());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cacheStyle(List<?> paramList, SynthStyle paramSynthStyle) {
/* 170 */     BakedArrayList bakedArrayList = new BakedArrayList(paramList);
/*     */     
/* 172 */     this._resolvedStyles.put(bakedArrayList, paramSynthStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SynthStyle getCachedStyle(List paramList) {
/* 179 */     if (paramList.size() == 0) {
/* 180 */       return null;
/*     */     }
/* 182 */     return this._resolvedStyles.get(paramList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SynthStyle mergeStyles(List<DefaultSynthStyle> paramList) {
/* 191 */     int i = paramList.size();
/*     */     
/* 193 */     if (i == 0) {
/* 194 */       return null;
/*     */     }
/* 196 */     if (i == 1) {
/* 197 */       return (SynthStyle)((DefaultSynthStyle)paramList.get(0)).clone();
/*     */     }
/*     */ 
/*     */     
/* 201 */     DefaultSynthStyle defaultSynthStyle = paramList.get(i - 1);
/*     */     
/* 203 */     defaultSynthStyle = (DefaultSynthStyle)defaultSynthStyle.clone();
/* 204 */     for (int j = i - 2; j >= 0; j--) {
/* 205 */       defaultSynthStyle = ((DefaultSynthStyle)paramList.get(j)).addTo(defaultSynthStyle);
/*     */     }
/* 207 */     return defaultSynthStyle;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/DefaultSynthStyleFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */