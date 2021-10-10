/*     */ package sun.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Insets;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.plaf.synth.ColorType;
/*     */ import javax.swing.plaf.synth.Region;
/*     */ import javax.swing.plaf.synth.SynthContext;
/*     */ import javax.swing.plaf.synth.SynthGraphicsUtils;
/*     */ import javax.swing.plaf.synth.SynthPainter;
/*     */ import javax.swing.plaf.synth.SynthStyle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultSynthStyle
/*     */   extends SynthStyle
/*     */   implements Cloneable
/*     */ {
/*  47 */   private static final Object PENDING = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean opaque;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Insets insets;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StateInfo[] states;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map data;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Font font;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SynthGraphicsUtils synthGraphics;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SynthPainter painter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultSynthStyle() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultSynthStyle(DefaultSynthStyle paramDefaultSynthStyle) {
/*  96 */     this.opaque = paramDefaultSynthStyle.opaque;
/*  97 */     if (paramDefaultSynthStyle.insets != null) {
/*  98 */       this.insets = new Insets(paramDefaultSynthStyle.insets.top, paramDefaultSynthStyle.insets.left, paramDefaultSynthStyle.insets.bottom, paramDefaultSynthStyle.insets.right);
/*     */     }
/*     */     
/* 101 */     if (paramDefaultSynthStyle.states != null) {
/* 102 */       this.states = new StateInfo[paramDefaultSynthStyle.states.length];
/* 103 */       for (int i = paramDefaultSynthStyle.states.length - 1; i >= 0; 
/* 104 */         i--) {
/* 105 */         this.states[i] = (StateInfo)paramDefaultSynthStyle.states[i].clone();
/*     */       }
/*     */     } 
/* 108 */     if (paramDefaultSynthStyle.data != null) {
/* 109 */       this.data = new HashMap<>();
/* 110 */       this.data.putAll(paramDefaultSynthStyle.data);
/*     */     } 
/* 112 */     this.font = paramDefaultSynthStyle.font;
/* 113 */     this.synthGraphics = paramDefaultSynthStyle.synthGraphics;
/* 114 */     this.painter = paramDefaultSynthStyle.painter;
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
/*     */   public DefaultSynthStyle(Insets paramInsets, boolean paramBoolean, StateInfo[] paramArrayOfStateInfo, Map paramMap) {
/* 128 */     this.insets = paramInsets;
/* 129 */     this.opaque = paramBoolean;
/* 130 */     this.states = paramArrayOfStateInfo;
/* 131 */     this.data = paramMap;
/*     */   }
/*     */   
/*     */   public Color getColor(SynthContext paramSynthContext, ColorType paramColorType) {
/* 135 */     return getColor(paramSynthContext.getComponent(), paramSynthContext.getRegion(), paramSynthContext
/* 136 */         .getComponentState(), paramColorType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor(JComponent paramJComponent, Region paramRegion, int paramInt, ColorType paramColorType) {
/* 142 */     if (!paramRegion.isSubregion() && paramInt == 1) {
/* 143 */       if (paramColorType == ColorType.BACKGROUND) {
/* 144 */         return paramJComponent.getBackground();
/*     */       }
/* 146 */       if (paramColorType == ColorType.FOREGROUND) {
/* 147 */         return paramJComponent.getForeground();
/*     */       }
/* 149 */       if (paramColorType == ColorType.TEXT_FOREGROUND) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 154 */         Color color1 = paramJComponent.getForeground();
/* 155 */         if (!(color1 instanceof javax.swing.plaf.UIResource)) {
/* 156 */           return color1;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 161 */     Color color = getColorForState(paramJComponent, paramRegion, paramInt, paramColorType);
/* 162 */     if (color == null) {
/*     */       
/* 164 */       if (paramColorType == ColorType.BACKGROUND || paramColorType == ColorType.TEXT_BACKGROUND)
/*     */       {
/* 166 */         return paramJComponent.getBackground();
/*     */       }
/* 168 */       if (paramColorType == ColorType.FOREGROUND || paramColorType == ColorType.TEXT_FOREGROUND)
/*     */       {
/* 170 */         return paramJComponent.getForeground();
/*     */       }
/*     */     } 
/* 173 */     return color;
/*     */   }
/*     */   
/*     */   protected Color getColorForState(SynthContext paramSynthContext, ColorType paramColorType) {
/* 177 */     return getColorForState(paramSynthContext.getComponent(), paramSynthContext.getRegion(), paramSynthContext
/* 178 */         .getComponentState(), paramColorType);
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
/*     */   protected Color getColorForState(JComponent paramJComponent, Region paramRegion, int paramInt, ColorType paramColorType) {
/* 193 */     StateInfo stateInfo = getStateInfo(paramInt);
/*     */     Color color;
/* 195 */     if (stateInfo != null && (color = stateInfo.getColor(paramColorType)) != null) {
/* 196 */       return color;
/*     */     }
/* 198 */     if (stateInfo == null || stateInfo.getComponentState() != 0) {
/* 199 */       stateInfo = getStateInfo(0);
/* 200 */       if (stateInfo != null) {
/* 201 */         return stateInfo.getColor(paramColorType);
/*     */       }
/*     */     } 
/* 204 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFont(Font paramFont) {
/* 214 */     this.font = paramFont;
/*     */   }
/*     */   
/*     */   public Font getFont(SynthContext paramSynthContext) {
/* 218 */     return getFont(paramSynthContext.getComponent(), paramSynthContext.getRegion(), paramSynthContext
/* 219 */         .getComponentState());
/*     */   }
/*     */   
/*     */   public Font getFont(JComponent paramJComponent, Region paramRegion, int paramInt) {
/* 223 */     if (!paramRegion.isSubregion() && paramInt == 1) {
/* 224 */       return paramJComponent.getFont();
/*     */     }
/* 226 */     Font font = paramJComponent.getFont();
/* 227 */     if (font != null && !(font instanceof javax.swing.plaf.UIResource)) {
/* 228 */       return font;
/*     */     }
/* 230 */     return getFontForState(paramJComponent, paramRegion, paramInt);
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
/*     */   protected Font getFontForState(JComponent paramJComponent, Region paramRegion, int paramInt) {
/* 243 */     if (paramJComponent == null) {
/* 244 */       return this.font;
/*     */     }
/*     */     
/* 247 */     StateInfo stateInfo = getStateInfo(paramInt);
/*     */     Font font;
/* 249 */     if (stateInfo != null && (font = stateInfo.getFont()) != null) {
/* 250 */       return font;
/*     */     }
/* 252 */     if (stateInfo == null || stateInfo.getComponentState() != 0) {
/* 253 */       stateInfo = getStateInfo(0);
/* 254 */       if (stateInfo != null && (font = stateInfo.getFont()) != null) {
/* 255 */         return font;
/*     */       }
/*     */     } 
/*     */     
/* 259 */     return this.font;
/*     */   }
/*     */   
/*     */   protected Font getFontForState(SynthContext paramSynthContext) {
/* 263 */     return getFontForState(paramSynthContext.getComponent(), paramSynthContext.getRegion(), paramSynthContext
/* 264 */         .getComponentState());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGraphicsUtils(SynthGraphicsUtils paramSynthGraphicsUtils) {
/* 273 */     this.synthGraphics = paramSynthGraphicsUtils;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthGraphicsUtils getGraphicsUtils(SynthContext paramSynthContext) {
/* 283 */     if (this.synthGraphics == null) {
/* 284 */       return super.getGraphicsUtils(paramSynthContext);
/*     */     }
/* 286 */     return this.synthGraphics;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInsets(Insets paramInsets) {
/* 295 */     this.insets = paramInsets;
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
/*     */   public Insets getInsets(SynthContext paramSynthContext, Insets paramInsets) {
/* 308 */     if (paramInsets == null) {
/* 309 */       paramInsets = new Insets(0, 0, 0, 0);
/*     */     }
/* 311 */     if (this.insets != null) {
/* 312 */       paramInsets.left = this.insets.left;
/* 313 */       paramInsets.right = this.insets.right;
/* 314 */       paramInsets.top = this.insets.top;
/* 315 */       paramInsets.bottom = this.insets.bottom;
/*     */     } else {
/*     */       
/* 318 */       paramInsets.left = paramInsets.right = paramInsets.top = paramInsets.bottom = 0;
/*     */     } 
/* 320 */     return paramInsets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPainter(SynthPainter paramSynthPainter) {
/* 329 */     this.painter = paramSynthPainter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthPainter getPainter(SynthContext paramSynthContext) {
/* 339 */     return this.painter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOpaque(boolean paramBoolean) {
/* 348 */     this.opaque = paramBoolean;
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
/*     */   public boolean isOpaque(SynthContext paramSynthContext) {
/* 360 */     return this.opaque;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(Map paramMap) {
/* 370 */     this.data = paramMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map getData() {
/* 379 */     return this.data;
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
/*     */   public Object get(SynthContext paramSynthContext, Object paramObject) {
/* 391 */     StateInfo stateInfo = getStateInfo(paramSynthContext.getComponentState());
/* 392 */     if (stateInfo != null && stateInfo.getData() != null && getKeyFromData(stateInfo.getData(), paramObject) != null) {
/* 393 */       return getKeyFromData(stateInfo.getData(), paramObject);
/*     */     }
/* 395 */     stateInfo = getStateInfo(0);
/* 396 */     if (stateInfo != null && stateInfo.getData() != null && getKeyFromData(stateInfo.getData(), paramObject) != null) {
/* 397 */       return getKeyFromData(stateInfo.getData(), paramObject);
/*     */     }
/* 399 */     if (getKeyFromData(this.data, paramObject) != null)
/* 400 */       return getKeyFromData(this.data, paramObject); 
/* 401 */     return getDefaultValue(paramSynthContext, paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   private Object getKeyFromData(Map<Object, Object> paramMap, Object paramObject) {
/* 406 */     Object object = null;
/* 407 */     if (paramMap != null) {
/*     */       
/* 409 */       synchronized (paramMap) {
/* 410 */         object = paramMap.get(paramObject);
/*     */       } 
/* 412 */       while (object == PENDING) {
/* 413 */         synchronized (paramMap) {
/*     */           try {
/* 415 */             paramMap.wait();
/* 416 */           } catch (InterruptedException interruptedException) {}
/* 417 */           object = paramMap.get(paramObject);
/*     */         } 
/*     */       } 
/* 420 */       if (object instanceof UIDefaults.LazyValue) {
/* 421 */         synchronized (paramMap) {
/* 422 */           paramMap.put(paramObject, PENDING);
/*     */         } 
/* 424 */         object = ((UIDefaults.LazyValue)object).createValue(null);
/* 425 */         synchronized (paramMap) {
/* 426 */           paramMap.put(paramObject, object);
/* 427 */           paramMap.notifyAll();
/*     */         } 
/*     */       } 
/*     */     } 
/* 431 */     return object;
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
/*     */   public Object getDefaultValue(SynthContext paramSynthContext, Object paramObject) {
/* 443 */     return super.get(paramSynthContext, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     DefaultSynthStyle defaultSynthStyle;
/*     */     try {
/* 454 */       defaultSynthStyle = (DefaultSynthStyle)super.clone();
/* 455 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 456 */       return null;
/*     */     } 
/* 458 */     if (this.states != null) {
/* 459 */       defaultSynthStyle.states = new StateInfo[this.states.length];
/* 460 */       for (int i = this.states.length - 1; i >= 0; i--) {
/* 461 */         defaultSynthStyle.states[i] = (StateInfo)this.states[i].clone();
/*     */       }
/*     */     } 
/* 464 */     if (this.data != null) {
/* 465 */       defaultSynthStyle.data = new HashMap<>();
/* 466 */       defaultSynthStyle.data.putAll(this.data);
/*     */     } 
/* 468 */     return defaultSynthStyle;
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
/*     */   public DefaultSynthStyle addTo(DefaultSynthStyle paramDefaultSynthStyle) {
/* 483 */     if (this.insets != null) {
/* 484 */       paramDefaultSynthStyle.insets = this.insets;
/*     */     }
/* 486 */     if (this.font != null) {
/* 487 */       paramDefaultSynthStyle.font = this.font;
/*     */     }
/* 489 */     if (this.painter != null) {
/* 490 */       paramDefaultSynthStyle.painter = this.painter;
/*     */     }
/* 492 */     if (this.synthGraphics != null) {
/* 493 */       paramDefaultSynthStyle.synthGraphics = this.synthGraphics;
/*     */     }
/* 495 */     paramDefaultSynthStyle.opaque = this.opaque;
/* 496 */     if (this.states != null) {
/* 497 */       if (paramDefaultSynthStyle.states == null) {
/* 498 */         paramDefaultSynthStyle.states = new StateInfo[this.states.length];
/* 499 */         for (int i = this.states.length - 1; i >= 0; i--) {
/* 500 */           if (this.states[i] != null) {
/* 501 */             paramDefaultSynthStyle.states[i] = (StateInfo)this.states[i]
/* 502 */               .clone();
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 510 */         byte b1 = 0;
/*     */         
/* 512 */         byte b2 = 0;
/* 513 */         int i = paramDefaultSynthStyle.states.length;
/* 514 */         for (int j = this.states.length - 1; j >= 0; 
/* 515 */           j--) {
/* 516 */           int k = this.states[j].getComponentState();
/* 517 */           boolean bool = false;
/*     */           
/* 519 */           int m = i - 1 - b2;
/* 520 */           for (; m >= 0; m--) {
/* 521 */             if (k == paramDefaultSynthStyle.states[m]
/* 522 */               .getComponentState()) {
/* 523 */               paramDefaultSynthStyle.states[m] = this.states[j]
/* 524 */                 .addTo(paramDefaultSynthStyle.states[m]);
/*     */               
/* 526 */               StateInfo stateInfo = paramDefaultSynthStyle.states[i - 1 - b2];
/*     */               
/* 528 */               paramDefaultSynthStyle.states[i - 1 - b2] = paramDefaultSynthStyle.states[m];
/*     */               
/* 530 */               paramDefaultSynthStyle.states[m] = stateInfo;
/* 531 */               b2++;
/* 532 */               bool = true;
/*     */               break;
/*     */             } 
/*     */           } 
/* 536 */           if (!bool) {
/* 537 */             b1++;
/*     */           }
/*     */         } 
/* 540 */         if (b1 != 0) {
/*     */ 
/*     */ 
/*     */           
/* 544 */           StateInfo[] arrayOfStateInfo = new StateInfo[b1 + i];
/*     */           
/* 546 */           int k = i;
/*     */           
/* 548 */           System.arraycopy(paramDefaultSynthStyle.states, 0, arrayOfStateInfo, 0, i);
/* 549 */           for (int m = this.states.length - 1; m >= 0; 
/* 550 */             m--) {
/* 551 */             int n = this.states[m].getComponentState();
/* 552 */             boolean bool = false;
/*     */             
/* 554 */             for (int i1 = i - 1; i1 >= 0; 
/* 555 */               i1--) {
/* 556 */               if (n == paramDefaultSynthStyle.states[i1]
/* 557 */                 .getComponentState()) {
/* 558 */                 bool = true;
/*     */                 break;
/*     */               } 
/*     */             } 
/* 562 */             if (!bool) {
/* 563 */               arrayOfStateInfo[k++] = (StateInfo)this.states[m]
/* 564 */                 .clone();
/*     */             }
/*     */           } 
/* 567 */           paramDefaultSynthStyle.states = arrayOfStateInfo;
/*     */         } 
/*     */       } 
/*     */     }
/* 571 */     if (this.data != null) {
/* 572 */       if (paramDefaultSynthStyle.data == null) {
/* 573 */         paramDefaultSynthStyle.data = new HashMap<>();
/*     */       }
/* 575 */       paramDefaultSynthStyle.data.putAll(this.data);
/*     */     } 
/* 577 */     return paramDefaultSynthStyle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStateInfo(StateInfo[] paramArrayOfStateInfo) {
/* 587 */     this.states = paramArrayOfStateInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StateInfo[] getStateInfo() {
/* 597 */     return this.states;
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
/*     */   public StateInfo getStateInfo(int paramInt) {
/* 622 */     if (this.states != null) {
/* 623 */       int i = 0;
/* 624 */       int j = -1;
/* 625 */       int k = -1;
/*     */       
/* 627 */       if (paramInt == 0) {
/* 628 */         for (int n = this.states.length - 1; n >= 0; n--) {
/* 629 */           if (this.states[n].getComponentState() == 0) {
/* 630 */             return this.states[n];
/*     */           }
/*     */         } 
/* 633 */         return null;
/*     */       } 
/* 635 */       for (int m = this.states.length - 1; m >= 0; m--) {
/* 636 */         int n = this.states[m].getComponentState();
/*     */         
/* 638 */         if (n == 0) {
/* 639 */           if (k == -1) {
/* 640 */             k = m;
/*     */           }
/*     */         }
/* 643 */         else if ((paramInt & n) == n) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 650 */           int i1 = n;
/* 651 */           i1 -= (0xAAAAAAAA & i1) >>> 1;
/* 652 */           i1 = (i1 & 0x33333333) + (i1 >>> 2 & 0x33333333);
/*     */           
/* 654 */           i1 = i1 + (i1 >>> 4) & 0xF0F0F0F;
/* 655 */           i1 += i1 >>> 8;
/* 656 */           i1 += i1 >>> 16;
/* 657 */           i1 &= 0xFF;
/* 658 */           if (i1 > i) {
/* 659 */             j = m;
/* 660 */             i = i1;
/*     */           } 
/*     */         } 
/*     */       } 
/* 664 */       if (j != -1) {
/* 665 */         return this.states[j];
/*     */       }
/* 667 */       if (k != -1) {
/* 668 */         return this.states[k];
/*     */       }
/*     */     } 
/* 671 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 676 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 678 */     stringBuffer.append(super.toString()).append(',');
/*     */     
/* 680 */     stringBuffer.append("data=").append(this.data).append(',');
/*     */     
/* 682 */     stringBuffer.append("font=").append(this.font).append(',');
/*     */     
/* 684 */     stringBuffer.append("insets=").append(this.insets).append(',');
/*     */     
/* 686 */     stringBuffer.append("synthGraphics=").append(this.synthGraphics).append(',');
/*     */     
/* 688 */     stringBuffer.append("painter=").append(this.painter).append(',');
/*     */     
/* 690 */     StateInfo[] arrayOfStateInfo = getStateInfo();
/* 691 */     if (arrayOfStateInfo != null) {
/* 692 */       stringBuffer.append("states[");
/* 693 */       for (StateInfo stateInfo : arrayOfStateInfo) {
/* 694 */         stringBuffer.append(stateInfo.toString()).append(',');
/*     */       }
/* 696 */       stringBuffer.append(']').append(',');
/*     */     } 
/*     */ 
/*     */     
/* 700 */     stringBuffer.deleteCharAt(stringBuffer.length() - 1);
/*     */     
/* 702 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class StateInfo
/*     */   {
/*     */     private Map data;
/*     */ 
/*     */ 
/*     */     
/*     */     private Font font;
/*     */ 
/*     */ 
/*     */     
/*     */     private Color[] colors;
/*     */ 
/*     */ 
/*     */     
/*     */     private int state;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public StateInfo() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public StateInfo(int param1Int, Font param1Font, Color[] param1ArrayOfColor) {
/* 733 */       this.state = param1Int;
/* 734 */       this.font = param1Font;
/* 735 */       this.colors = param1ArrayOfColor;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public StateInfo(StateInfo param1StateInfo) {
/* 745 */       this.state = param1StateInfo.state;
/* 746 */       this.font = param1StateInfo.font;
/* 747 */       if (param1StateInfo.data != null) {
/* 748 */         if (this.data == null) {
/* 749 */           this.data = new HashMap<>();
/*     */         }
/* 751 */         this.data.putAll(param1StateInfo.data);
/*     */       } 
/* 753 */       if (param1StateInfo.colors != null) {
/* 754 */         this.colors = new Color[param1StateInfo.colors.length];
/* 755 */         System.arraycopy(param1StateInfo.colors, 0, this.colors, 0, param1StateInfo.colors.length);
/*     */       } 
/*     */     }
/*     */     
/*     */     public Map getData() {
/* 760 */       return this.data;
/*     */     }
/*     */     
/*     */     public void setData(Map param1Map) {
/* 764 */       this.data = param1Map;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setFont(Font param1Font) {
/* 773 */       this.font = param1Font;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Font getFont() {
/* 782 */       return this.font;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setColors(Color[] param1ArrayOfColor) {
/* 792 */       this.colors = param1ArrayOfColor;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Color[] getColors() {
/* 802 */       return this.colors;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Color getColor(ColorType param1ColorType) {
/* 811 */       if (this.colors != null) {
/* 812 */         int i = param1ColorType.getID();
/*     */         
/* 814 */         if (i < this.colors.length) {
/* 815 */           return this.colors[i];
/*     */         }
/*     */       } 
/* 818 */       return null;
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
/*     */     public StateInfo addTo(StateInfo param1StateInfo) {
/* 834 */       if (this.font != null) {
/* 835 */         param1StateInfo.font = this.font;
/*     */       }
/* 837 */       if (this.data != null) {
/* 838 */         if (param1StateInfo.data == null) {
/* 839 */           param1StateInfo.data = new HashMap<>();
/*     */         }
/* 841 */         param1StateInfo.data.putAll(this.data);
/*     */       } 
/* 843 */       if (this.colors != null) {
/* 844 */         if (param1StateInfo.colors == null) {
/* 845 */           param1StateInfo.colors = new Color[this.colors.length];
/* 846 */           System.arraycopy(this.colors, 0, param1StateInfo.colors, 0, this.colors.length);
/*     */         }
/*     */         else {
/*     */           
/* 850 */           if (param1StateInfo.colors.length < this.colors.length) {
/* 851 */             Color[] arrayOfColor = param1StateInfo.colors;
/*     */             
/* 853 */             param1StateInfo.colors = new Color[this.colors.length];
/* 854 */             System.arraycopy(arrayOfColor, 0, param1StateInfo.colors, 0, arrayOfColor.length);
/*     */           } 
/* 856 */           for (int i = this.colors.length - 1; i >= 0; 
/* 857 */             i--) {
/* 858 */             if (this.colors[i] != null) {
/* 859 */               param1StateInfo.colors[i] = this.colors[i];
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/* 864 */       return param1StateInfo;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setComponentState(int param1Int) {
/* 874 */       this.state = param1Int;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getComponentState() {
/* 884 */       return this.state;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int getMatchCount(int param1Int) {
/* 893 */       param1Int &= this.state;
/* 894 */       param1Int -= (0xAAAAAAAA & param1Int) >>> 1;
/* 895 */       param1Int = (param1Int & 0x33333333) + (param1Int >>> 2 & 0x33333333);
/* 896 */       param1Int = param1Int + (param1Int >>> 4) & 0xF0F0F0F;
/* 897 */       param1Int += param1Int >>> 8;
/* 898 */       param1Int += param1Int >>> 16;
/* 899 */       return param1Int & 0xFF;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 908 */       return new StateInfo(this);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 912 */       StringBuffer stringBuffer = new StringBuffer();
/*     */       
/* 914 */       stringBuffer.append(super.toString()).append(',');
/*     */       
/* 916 */       stringBuffer.append("state=").append(Integer.toString(this.state)).append(',');
/*     */       
/* 918 */       stringBuffer.append("font=").append(this.font).append(',');
/*     */       
/* 920 */       if (this.colors != null) {
/* 921 */         stringBuffer.append("colors=").append(Arrays.asList(this.colors))
/* 922 */           .append(',');
/*     */       }
/* 924 */       return stringBuffer.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/swing/plaf/synth/DefaultSynthStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */