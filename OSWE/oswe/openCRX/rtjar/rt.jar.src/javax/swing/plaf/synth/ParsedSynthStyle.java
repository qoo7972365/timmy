/*      */ package javax.swing.plaf.synth;
/*      */ 
/*      */ import java.awt.Graphics;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import sun.swing.plaf.synth.DefaultSynthStyle;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class ParsedSynthStyle
/*      */   extends DefaultSynthStyle
/*      */ {
/*   38 */   private static SynthPainter DELEGATING_PAINTER_INSTANCE = new DelegatingPainter();
/*      */   
/*      */   private PainterInfo[] _painters;
/*      */ 
/*      */   
/*      */   private static PainterInfo[] mergePainterInfo(PainterInfo[] paramArrayOfPainterInfo1, PainterInfo[] paramArrayOfPainterInfo2) {
/*   44 */     if (paramArrayOfPainterInfo1 == null) {
/*   45 */       return paramArrayOfPainterInfo2;
/*      */     }
/*   47 */     if (paramArrayOfPainterInfo2 == null) {
/*   48 */       return paramArrayOfPainterInfo1;
/*      */     }
/*   50 */     int i = paramArrayOfPainterInfo1.length;
/*   51 */     int j = paramArrayOfPainterInfo2.length;
/*   52 */     byte b1 = 0;
/*   53 */     PainterInfo[] arrayOfPainterInfo = new PainterInfo[i + j];
/*   54 */     System.arraycopy(paramArrayOfPainterInfo1, 0, arrayOfPainterInfo, 0, i);
/*   55 */     for (byte b2 = 0; b2 < j; b2++) {
/*   56 */       boolean bool = false;
/*   57 */       for (byte b = 0; b < i - b1; 
/*   58 */         b++) {
/*   59 */         if (paramArrayOfPainterInfo2[b2].equalsPainter(paramArrayOfPainterInfo1[b])) {
/*   60 */           arrayOfPainterInfo[b] = paramArrayOfPainterInfo2[b2];
/*   61 */           b1++;
/*   62 */           bool = true;
/*      */           break;
/*      */         } 
/*      */       } 
/*   66 */       if (!bool) {
/*   67 */         arrayOfPainterInfo[i + b2 - b1] = paramArrayOfPainterInfo2[b2];
/*      */       }
/*      */     } 
/*   70 */     if (b1 > 0) {
/*   71 */       PainterInfo[] arrayOfPainterInfo1 = arrayOfPainterInfo;
/*   72 */       arrayOfPainterInfo = new PainterInfo[arrayOfPainterInfo.length - b1];
/*   73 */       System.arraycopy(arrayOfPainterInfo1, 0, arrayOfPainterInfo, 0, arrayOfPainterInfo.length);
/*      */     } 
/*   75 */     return arrayOfPainterInfo;
/*      */   }
/*      */ 
/*      */   
/*      */   public ParsedSynthStyle() {}
/*      */ 
/*      */   
/*      */   public ParsedSynthStyle(DefaultSynthStyle paramDefaultSynthStyle) {
/*   83 */     super(paramDefaultSynthStyle);
/*   84 */     if (paramDefaultSynthStyle instanceof ParsedSynthStyle) {
/*   85 */       ParsedSynthStyle parsedSynthStyle = (ParsedSynthStyle)paramDefaultSynthStyle;
/*      */       
/*   87 */       if (parsedSynthStyle._painters != null) {
/*   88 */         this._painters = parsedSynthStyle._painters;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public SynthPainter getPainter(SynthContext paramSynthContext) {
/*   94 */     return DELEGATING_PAINTER_INSTANCE;
/*      */   }
/*      */   
/*      */   public void setPainters(PainterInfo[] paramArrayOfPainterInfo) {
/*   98 */     this._painters = paramArrayOfPainterInfo;
/*      */   }
/*      */   
/*      */   public DefaultSynthStyle addTo(DefaultSynthStyle paramDefaultSynthStyle) {
/*  102 */     if (!(paramDefaultSynthStyle instanceof ParsedSynthStyle)) {
/*  103 */       paramDefaultSynthStyle = new ParsedSynthStyle(paramDefaultSynthStyle);
/*      */     }
/*  105 */     ParsedSynthStyle parsedSynthStyle = (ParsedSynthStyle)super.addTo(paramDefaultSynthStyle);
/*  106 */     parsedSynthStyle._painters = mergePainterInfo(parsedSynthStyle._painters, this._painters);
/*  107 */     return parsedSynthStyle;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private SynthPainter getBestPainter(SynthContext paramSynthContext, String paramString, int paramInt) {
/*  113 */     StateInfo stateInfo = (StateInfo)getStateInfo(paramSynthContext.getComponentState());
/*      */     SynthPainter synthPainter;
/*  115 */     if (stateInfo != null && (
/*  116 */       synthPainter = getBestPainter(stateInfo.getPainters(), paramString, paramInt)) != null)
/*      */     {
/*  118 */       return synthPainter;
/*      */     }
/*      */     
/*  121 */     if ((synthPainter = getBestPainter(this._painters, paramString, paramInt)) != null) {
/*  122 */       return synthPainter;
/*      */     }
/*  124 */     return SynthPainter.NULL_PAINTER;
/*      */   }
/*      */ 
/*      */   
/*      */   private SynthPainter getBestPainter(PainterInfo[] paramArrayOfPainterInfo, String paramString, int paramInt) {
/*  129 */     if (paramArrayOfPainterInfo != null) {
/*      */       
/*  131 */       SynthPainter synthPainter1 = null;
/*      */       
/*  133 */       SynthPainter synthPainter2 = null;
/*      */       
/*  135 */       for (int i = paramArrayOfPainterInfo.length - 1; i >= 0; i--) {
/*  136 */         PainterInfo painterInfo = paramArrayOfPainterInfo[i];
/*      */         
/*  138 */         if (painterInfo.getMethod() == paramString) {
/*  139 */           if (painterInfo.getDirection() == paramInt) {
/*  140 */             return painterInfo.getPainter();
/*      */           }
/*  142 */           if (synthPainter2 == null && painterInfo.getDirection() == -1) {
/*  143 */             synthPainter2 = painterInfo.getPainter();
/*      */           }
/*      */         }
/*  146 */         else if (synthPainter1 == null && painterInfo.getMethod() == null) {
/*  147 */           synthPainter1 = painterInfo.getPainter();
/*      */         } 
/*      */       } 
/*  150 */       if (synthPainter2 != null) {
/*  151 */         return synthPainter2;
/*      */       }
/*  153 */       return synthPainter1;
/*      */     } 
/*  155 */     return null;
/*      */   }
/*      */   
/*      */   public String toString() {
/*  159 */     StringBuffer stringBuffer = new StringBuffer(super.toString());
/*  160 */     if (this._painters != null) {
/*  161 */       stringBuffer.append(",painters=[");
/*  162 */       for (byte b = 0; b < this._painters.length; b++) {
/*  163 */         stringBuffer.append(this._painters[b].toString());
/*      */       }
/*  165 */       stringBuffer.append("]");
/*      */     } 
/*  167 */     return stringBuffer.toString();
/*      */   }
/*      */   
/*      */   static class StateInfo
/*      */     extends DefaultSynthStyle.StateInfo
/*      */   {
/*      */     private ParsedSynthStyle.PainterInfo[] _painterInfo;
/*      */     
/*      */     public StateInfo() {}
/*      */     
/*      */     public StateInfo(DefaultSynthStyle.StateInfo param1StateInfo) {
/*  178 */       super(param1StateInfo);
/*  179 */       if (param1StateInfo instanceof StateInfo) {
/*  180 */         this._painterInfo = ((StateInfo)param1StateInfo)._painterInfo;
/*      */       }
/*      */     }
/*      */     
/*      */     public void setPainters(ParsedSynthStyle.PainterInfo[] param1ArrayOfPainterInfo) {
/*  185 */       this._painterInfo = param1ArrayOfPainterInfo;
/*      */     }
/*      */     
/*      */     public ParsedSynthStyle.PainterInfo[] getPainters() {
/*  189 */       return this._painterInfo;
/*      */     }
/*      */     
/*      */     public Object clone() {
/*  193 */       return new StateInfo(this);
/*      */     }
/*      */ 
/*      */     
/*      */     public DefaultSynthStyle.StateInfo addTo(DefaultSynthStyle.StateInfo param1StateInfo) {
/*  198 */       if (!(param1StateInfo instanceof StateInfo)) {
/*  199 */         param1StateInfo = new StateInfo(param1StateInfo);
/*      */       } else {
/*      */         
/*  202 */         param1StateInfo = super.addTo(param1StateInfo);
/*  203 */         StateInfo stateInfo = (StateInfo)param1StateInfo;
/*  204 */         stateInfo._painterInfo = ParsedSynthStyle.mergePainterInfo(stateInfo._painterInfo, this._painterInfo);
/*      */       } 
/*      */       
/*  207 */       return param1StateInfo;
/*      */     }
/*      */     
/*      */     public String toString() {
/*  211 */       StringBuffer stringBuffer = new StringBuffer(super.toString());
/*  212 */       stringBuffer.append(",painters=[");
/*  213 */       if (this._painterInfo != null) {
/*  214 */         for (byte b = 0; b < this._painterInfo.length; b++) {
/*  215 */           stringBuffer.append("    ").append(this._painterInfo[b].toString());
/*      */         }
/*      */       }
/*  218 */       stringBuffer.append("]");
/*  219 */       return stringBuffer.toString();
/*      */     }
/*      */   }
/*      */   
/*      */   static class PainterInfo
/*      */   {
/*      */     private String _method;
/*      */     private SynthPainter _painter;
/*      */     private int _direction;
/*      */     
/*      */     PainterInfo(String param1String, SynthPainter param1SynthPainter, int param1Int) {
/*  230 */       if (param1String != null) {
/*  231 */         this._method = param1String.intern();
/*      */       }
/*  233 */       this._painter = param1SynthPainter;
/*  234 */       this._direction = param1Int;
/*      */     }
/*      */     
/*      */     void addPainter(SynthPainter param1SynthPainter) {
/*  238 */       if (!(this._painter instanceof ParsedSynthStyle.AggregatePainter)) {
/*  239 */         this._painter = new ParsedSynthStyle.AggregatePainter(this._painter);
/*      */       }
/*      */       
/*  242 */       ((ParsedSynthStyle.AggregatePainter)this._painter).addPainter(param1SynthPainter);
/*      */     }
/*      */     
/*      */     String getMethod() {
/*  246 */       return this._method;
/*      */     }
/*      */     
/*      */     SynthPainter getPainter() {
/*  250 */       return this._painter;
/*      */     }
/*      */     
/*      */     int getDirection() {
/*  254 */       return this._direction;
/*      */     }
/*      */     
/*      */     boolean equalsPainter(PainterInfo param1PainterInfo) {
/*  258 */       return (this._method == param1PainterInfo._method && this._direction == param1PainterInfo._direction);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  262 */       return "PainterInfo {method=" + this._method + ",direction=" + this._direction + ",painter=" + this._painter + "}";
/*      */     }
/*      */   }
/*      */   
/*      */   private static class AggregatePainter
/*      */     extends SynthPainter {
/*      */     private List<SynthPainter> painters;
/*      */     
/*      */     AggregatePainter(SynthPainter param1SynthPainter) {
/*  271 */       this.painters = new LinkedList<>();
/*  272 */       this.painters.add(param1SynthPainter);
/*      */     }
/*      */     
/*      */     void addPainter(SynthPainter param1SynthPainter) {
/*  276 */       if (param1SynthPainter != null) {
/*  277 */         this.painters.add(param1SynthPainter);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintArrowButtonBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  283 */       for (SynthPainter synthPainter : this.painters) {
/*  284 */         synthPainter.paintArrowButtonBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintArrowButtonBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  290 */       for (SynthPainter synthPainter : this.painters) {
/*  291 */         synthPainter.paintArrowButtonBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintArrowButtonForeground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  298 */       for (SynthPainter synthPainter : this.painters) {
/*  299 */         synthPainter.paintArrowButtonForeground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintButtonBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  306 */       for (SynthPainter synthPainter : this.painters) {
/*  307 */         synthPainter.paintButtonBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintButtonBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  313 */       for (SynthPainter synthPainter : this.painters) {
/*  314 */         synthPainter.paintButtonBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintCheckBoxMenuItemBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  321 */       for (SynthPainter synthPainter : this.painters) {
/*  322 */         synthPainter.paintCheckBoxMenuItemBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintCheckBoxMenuItemBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  329 */       for (SynthPainter synthPainter : this.painters) {
/*  330 */         synthPainter.paintCheckBoxMenuItemBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintCheckBoxBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  336 */       for (SynthPainter synthPainter : this.painters) {
/*  337 */         synthPainter.paintCheckBoxBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintCheckBoxBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  343 */       for (SynthPainter synthPainter : this.painters) {
/*  344 */         synthPainter.paintCheckBoxBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintColorChooserBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  351 */       for (SynthPainter synthPainter : this.painters) {
/*  352 */         synthPainter.paintColorChooserBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintColorChooserBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  359 */       for (SynthPainter synthPainter : this.painters) {
/*  360 */         synthPainter.paintColorChooserBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintComboBoxBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  367 */       for (SynthPainter synthPainter : this.painters) {
/*  368 */         synthPainter.paintComboBoxBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintComboBoxBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  375 */       for (SynthPainter synthPainter : this.painters) {
/*  376 */         synthPainter.paintComboBoxBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintDesktopIconBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  383 */       for (SynthPainter synthPainter : this.painters) {
/*  384 */         synthPainter.paintDesktopIconBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintDesktopIconBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  391 */       for (SynthPainter synthPainter : this.painters) {
/*  392 */         synthPainter.paintDesktopIconBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintDesktopPaneBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  399 */       for (SynthPainter synthPainter : this.painters) {
/*  400 */         synthPainter.paintDesktopPaneBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintDesktopPaneBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  407 */       for (SynthPainter synthPainter : this.painters) {
/*  408 */         synthPainter.paintDesktopPaneBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintEditorPaneBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  414 */       for (SynthPainter synthPainter : this.painters) {
/*  415 */         synthPainter.paintEditorPaneBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintEditorPaneBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  421 */       for (SynthPainter synthPainter : this.painters) {
/*  422 */         synthPainter.paintEditorPaneBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintFileChooserBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  428 */       for (SynthPainter synthPainter : this.painters) {
/*  429 */         synthPainter.paintFileChooserBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintFileChooserBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  435 */       for (SynthPainter synthPainter : this.painters) {
/*  436 */         synthPainter.paintFileChooserBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintFormattedTextFieldBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  443 */       for (SynthPainter synthPainter : this.painters) {
/*  444 */         synthPainter.paintFormattedTextFieldBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintFormattedTextFieldBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  451 */       for (SynthPainter synthPainter : this.painters) {
/*  452 */         synthPainter.paintFormattedTextFieldBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintInternalFrameTitlePaneBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  460 */       for (SynthPainter synthPainter : this.painters) {
/*  461 */         synthPainter.paintInternalFrameTitlePaneBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintInternalFrameTitlePaneBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  470 */       for (SynthPainter synthPainter : this.painters) {
/*  471 */         synthPainter.paintInternalFrameTitlePaneBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintInternalFrameBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  479 */       for (SynthPainter synthPainter : this.painters) {
/*  480 */         synthPainter.paintInternalFrameBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintInternalFrameBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  486 */       for (SynthPainter synthPainter : this.painters) {
/*  487 */         synthPainter.paintInternalFrameBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintLabelBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  493 */       for (SynthPainter synthPainter : this.painters) {
/*  494 */         synthPainter.paintLabelBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintLabelBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  500 */       for (SynthPainter synthPainter : this.painters) {
/*  501 */         synthPainter.paintLabelBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintListBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  507 */       for (SynthPainter synthPainter : this.painters) {
/*  508 */         synthPainter.paintListBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintListBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  514 */       for (SynthPainter synthPainter : this.painters) {
/*  515 */         synthPainter.paintListBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintMenuBarBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  521 */       for (SynthPainter synthPainter : this.painters) {
/*  522 */         synthPainter.paintMenuBarBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintMenuBarBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  528 */       for (SynthPainter synthPainter : this.painters) {
/*  529 */         synthPainter.paintMenuBarBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintMenuItemBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  535 */       for (SynthPainter synthPainter : this.painters) {
/*  536 */         synthPainter.paintMenuItemBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintMenuItemBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  542 */       for (SynthPainter synthPainter : this.painters) {
/*  543 */         synthPainter.paintMenuItemBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintMenuBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  549 */       for (SynthPainter synthPainter : this.painters) {
/*  550 */         synthPainter.paintMenuBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintMenuBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  556 */       for (SynthPainter synthPainter : this.painters) {
/*  557 */         synthPainter.paintMenuBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintOptionPaneBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  563 */       for (SynthPainter synthPainter : this.painters) {
/*  564 */         synthPainter.paintOptionPaneBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintOptionPaneBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  570 */       for (SynthPainter synthPainter : this.painters) {
/*  571 */         synthPainter.paintOptionPaneBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintPanelBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  577 */       for (SynthPainter synthPainter : this.painters) {
/*  578 */         synthPainter.paintPanelBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintPanelBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  584 */       for (SynthPainter synthPainter : this.painters) {
/*  585 */         synthPainter.paintPanelBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintPasswordFieldBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  592 */       for (SynthPainter synthPainter : this.painters) {
/*  593 */         synthPainter.paintPasswordFieldBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintPasswordFieldBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  599 */       for (SynthPainter synthPainter : this.painters) {
/*  600 */         synthPainter.paintPasswordFieldBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintPopupMenuBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  606 */       for (SynthPainter synthPainter : this.painters) {
/*  607 */         synthPainter.paintPopupMenuBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintPopupMenuBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  613 */       for (SynthPainter synthPainter : this.painters) {
/*  614 */         synthPainter.paintPopupMenuBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintProgressBarBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  620 */       for (SynthPainter synthPainter : this.painters) {
/*  621 */         synthPainter.paintProgressBarBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintProgressBarBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  628 */       for (SynthPainter synthPainter : this.painters) {
/*  629 */         synthPainter.paintProgressBarBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintProgressBarBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  636 */       for (SynthPainter synthPainter : this.painters) {
/*  637 */         synthPainter.paintProgressBarBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintProgressBarBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  644 */       for (SynthPainter synthPainter : this.painters) {
/*  645 */         synthPainter.paintProgressBarBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintProgressBarForeground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  653 */       for (SynthPainter synthPainter : this.painters) {
/*  654 */         synthPainter.paintProgressBarForeground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintRadioButtonMenuItemBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  663 */       for (SynthPainter synthPainter : this.painters) {
/*  664 */         synthPainter.paintRadioButtonMenuItemBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintRadioButtonMenuItemBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  672 */       for (SynthPainter synthPainter : this.painters) {
/*  673 */         synthPainter.paintRadioButtonMenuItemBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintRadioButtonBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  679 */       for (SynthPainter synthPainter : this.painters) {
/*  680 */         synthPainter.paintRadioButtonBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintRadioButtonBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  686 */       for (SynthPainter synthPainter : this.painters) {
/*  687 */         synthPainter.paintRadioButtonBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintRootPaneBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  693 */       for (SynthPainter synthPainter : this.painters) {
/*  694 */         synthPainter.paintRootPaneBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintRootPaneBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  700 */       for (SynthPainter synthPainter : this.painters) {
/*  701 */         synthPainter.paintRootPaneBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintScrollBarBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  707 */       for (SynthPainter synthPainter : this.painters) {
/*  708 */         synthPainter.paintScrollBarBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintScrollBarBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  715 */       for (SynthPainter synthPainter : this.painters) {
/*  716 */         synthPainter.paintScrollBarBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintScrollBarBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  723 */       for (SynthPainter synthPainter : this.painters) {
/*  724 */         synthPainter.paintScrollBarBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintScrollBarBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  731 */       for (SynthPainter synthPainter : this.painters) {
/*  732 */         synthPainter.paintScrollBarBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintScrollBarThumbBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  740 */       for (SynthPainter synthPainter : this.painters) {
/*  741 */         synthPainter.paintScrollBarThumbBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintScrollBarThumbBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  749 */       for (SynthPainter synthPainter : this.painters) {
/*  750 */         synthPainter.paintScrollBarThumbBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintScrollBarTrackBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  758 */       for (SynthPainter synthPainter : this.painters) {
/*  759 */         synthPainter.paintScrollBarTrackBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintScrollBarTrackBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  767 */       for (SynthPainter synthPainter : this.painters) {
/*  768 */         synthPainter.paintScrollBarTrackBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintScrollBarTrackBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  775 */       for (SynthPainter synthPainter : this.painters) {
/*  776 */         synthPainter.paintScrollBarTrackBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintScrollBarTrackBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  783 */       for (SynthPainter synthPainter : this.painters) {
/*  784 */         synthPainter.paintScrollBarTrackBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintScrollPaneBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  791 */       for (SynthPainter synthPainter : this.painters) {
/*  792 */         synthPainter.paintScrollPaneBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintScrollPaneBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  798 */       for (SynthPainter synthPainter : this.painters) {
/*  799 */         synthPainter.paintScrollPaneBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSeparatorBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  805 */       for (SynthPainter synthPainter : this.painters) {
/*  806 */         synthPainter.paintSeparatorBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintSeparatorBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  813 */       for (SynthPainter synthPainter : this.painters) {
/*  814 */         synthPainter.paintSeparatorBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSeparatorBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  820 */       for (SynthPainter synthPainter : this.painters) {
/*  821 */         synthPainter.paintSeparatorBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSeparatorBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  827 */       for (SynthPainter synthPainter : this.painters) {
/*  828 */         synthPainter.paintSeparatorBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintSeparatorForeground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  835 */       for (SynthPainter synthPainter : this.painters) {
/*  836 */         synthPainter.paintSeparatorForeground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintSliderBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  843 */       for (SynthPainter synthPainter : this.painters) {
/*  844 */         synthPainter.paintSliderBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintSliderBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  851 */       for (SynthPainter synthPainter : this.painters) {
/*  852 */         synthPainter.paintSliderBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSliderBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  858 */       for (SynthPainter synthPainter : this.painters) {
/*  859 */         synthPainter.paintSliderBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintSliderBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  866 */       for (SynthPainter synthPainter : this.painters) {
/*  867 */         synthPainter.paintSliderBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintSliderThumbBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  874 */       for (SynthPainter synthPainter : this.painters) {
/*  875 */         synthPainter.paintSliderThumbBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintSliderThumbBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  883 */       for (SynthPainter synthPainter : this.painters) {
/*  884 */         synthPainter.paintSliderThumbBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintSliderTrackBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  891 */       for (SynthPainter synthPainter : this.painters) {
/*  892 */         synthPainter.paintSliderTrackBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintSliderTrackBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  899 */       for (SynthPainter synthPainter : this.painters) {
/*  900 */         synthPainter.paintSliderTrackBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintSliderTrackBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  907 */       for (SynthPainter synthPainter : this.painters) {
/*  908 */         synthPainter.paintSliderTrackBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintSliderTrackBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  915 */       for (SynthPainter synthPainter : this.painters) {
/*  916 */         synthPainter.paintSliderTrackBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintSpinnerBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  923 */       for (SynthPainter synthPainter : this.painters) {
/*  924 */         synthPainter.paintSpinnerBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSpinnerBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  930 */       for (SynthPainter synthPainter : this.painters) {
/*  931 */         synthPainter.paintSpinnerBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintSplitPaneDividerBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  938 */       for (SynthPainter synthPainter : this.painters) {
/*  939 */         synthPainter.paintSplitPaneDividerBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintSplitPaneDividerBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  947 */       for (SynthPainter synthPainter : this.painters) {
/*  948 */         synthPainter.paintSplitPaneDividerBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintSplitPaneDividerForeground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  957 */       for (SynthPainter synthPainter : this.painters) {
/*  958 */         synthPainter.paintSplitPaneDividerForeground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintSplitPaneDragDivider(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  967 */       for (SynthPainter synthPainter : this.painters) {
/*  968 */         synthPainter.paintSplitPaneDragDivider(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintSplitPaneBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  975 */       for (SynthPainter synthPainter : this.painters) {
/*  976 */         synthPainter.paintSplitPaneBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSplitPaneBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  982 */       for (SynthPainter synthPainter : this.painters) {
/*  983 */         synthPainter.paintSplitPaneBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  989 */       for (SynthPainter synthPainter : this.painters) {
/*  990 */         synthPainter.paintTabbedPaneBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  996 */       for (SynthPainter synthPainter : this.painters) {
/*  997 */         synthPainter.paintTabbedPaneBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneTabAreaBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1004 */       for (SynthPainter synthPainter : this.painters) {
/* 1005 */         synthPainter.paintTabbedPaneTabAreaBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneTabAreaBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1013 */       for (SynthPainter synthPainter : this.painters) {
/* 1014 */         synthPainter.paintTabbedPaneTabAreaBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneTabAreaBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1022 */       for (SynthPainter synthPainter : this.painters) {
/* 1023 */         synthPainter.paintTabbedPaneTabAreaBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneTabAreaBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1030 */       for (SynthPainter synthPainter : this.painters) {
/* 1031 */         synthPainter.paintTabbedPaneTabAreaBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneTabBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1039 */       for (SynthPainter synthPainter : this.painters) {
/* 1040 */         synthPainter.paintTabbedPaneTabBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneTabBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
/* 1049 */       for (SynthPainter synthPainter : this.painters) {
/* 1050 */         synthPainter.paintTabbedPaneTabBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneTabBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1059 */       for (SynthPainter synthPainter : this.painters) {
/* 1060 */         synthPainter.paintTabbedPaneTabBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneTabBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
/* 1068 */       for (SynthPainter synthPainter : this.painters) {
/* 1069 */         synthPainter.paintTabbedPaneTabBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneContentBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1078 */       for (SynthPainter synthPainter : this.painters) {
/* 1079 */         synthPainter.paintTabbedPaneContentBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneContentBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1086 */       for (SynthPainter synthPainter : this.painters) {
/* 1087 */         synthPainter.paintTabbedPaneContentBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTableHeaderBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1093 */       for (SynthPainter synthPainter : this.painters) {
/* 1094 */         synthPainter.paintTableHeaderBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTableHeaderBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1100 */       for (SynthPainter synthPainter : this.painters) {
/* 1101 */         synthPainter.paintTableHeaderBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTableBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1107 */       for (SynthPainter synthPainter : this.painters) {
/* 1108 */         synthPainter.paintTableBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTableBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1114 */       for (SynthPainter synthPainter : this.painters) {
/* 1115 */         synthPainter.paintTableBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTextAreaBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1121 */       for (SynthPainter synthPainter : this.painters) {
/* 1122 */         synthPainter.paintTextAreaBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTextAreaBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1128 */       for (SynthPainter synthPainter : this.painters) {
/* 1129 */         synthPainter.paintTextAreaBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTextPaneBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1135 */       for (SynthPainter synthPainter : this.painters) {
/* 1136 */         synthPainter.paintTextPaneBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTextPaneBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1142 */       for (SynthPainter synthPainter : this.painters) {
/* 1143 */         synthPainter.paintTextPaneBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTextFieldBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1149 */       for (SynthPainter synthPainter : this.painters) {
/* 1150 */         synthPainter.paintTextFieldBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTextFieldBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1156 */       for (SynthPainter synthPainter : this.painters) {
/* 1157 */         synthPainter.paintTextFieldBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintToggleButtonBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1164 */       for (SynthPainter synthPainter : this.painters) {
/* 1165 */         synthPainter.paintToggleButtonBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintToggleButtonBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1172 */       for (SynthPainter synthPainter : this.painters) {
/* 1173 */         synthPainter.paintToggleButtonBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintToolBarBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1179 */       for (SynthPainter synthPainter : this.painters) {
/* 1180 */         synthPainter.paintToolBarBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintToolBarBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1187 */       for (SynthPainter synthPainter : this.painters) {
/* 1188 */         synthPainter.paintToolBarBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintToolBarBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1195 */       for (SynthPainter synthPainter : this.painters) {
/* 1196 */         synthPainter.paintToolBarBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintToolBarBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1203 */       for (SynthPainter synthPainter : this.painters) {
/* 1204 */         synthPainter.paintToolBarBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintToolBarContentBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1211 */       for (SynthPainter synthPainter : this.painters) {
/* 1212 */         synthPainter.paintToolBarContentBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintToolBarContentBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1220 */       for (SynthPainter synthPainter : this.painters) {
/* 1221 */         synthPainter.paintToolBarContentBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintToolBarContentBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1228 */       for (SynthPainter synthPainter : this.painters) {
/* 1229 */         synthPainter.paintToolBarContentBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintToolBarContentBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1236 */       for (SynthPainter synthPainter : this.painters) {
/* 1237 */         synthPainter.paintToolBarContentBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintToolBarDragWindowBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1245 */       for (SynthPainter synthPainter : this.painters) {
/* 1246 */         synthPainter.paintToolBarDragWindowBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintToolBarDragWindowBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1254 */       for (SynthPainter synthPainter : this.painters) {
/* 1255 */         synthPainter.paintToolBarDragWindowBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintToolBarDragWindowBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1263 */       for (SynthPainter synthPainter : this.painters) {
/* 1264 */         synthPainter.paintToolBarDragWindowBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintToolBarDragWindowBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1272 */       for (SynthPainter synthPainter : this.painters) {
/* 1273 */         synthPainter.paintToolBarDragWindowBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintToolTipBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1281 */       for (SynthPainter synthPainter : this.painters) {
/* 1282 */         synthPainter.paintToolTipBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintToolTipBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1288 */       for (SynthPainter synthPainter : this.painters) {
/* 1289 */         synthPainter.paintToolTipBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTreeBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1295 */       for (SynthPainter synthPainter : this.painters) {
/* 1296 */         synthPainter.paintTreeBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTreeBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1302 */       for (SynthPainter synthPainter : this.painters) {
/* 1303 */         synthPainter.paintTreeBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTreeCellBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1309 */       for (SynthPainter synthPainter : this.painters) {
/* 1310 */         synthPainter.paintTreeCellBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTreeCellBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1316 */       for (SynthPainter synthPainter : this.painters) {
/* 1317 */         synthPainter.paintTreeCellBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTreeCellFocus(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1323 */       for (SynthPainter synthPainter : this.painters) {
/* 1324 */         synthPainter.paintTreeCellFocus(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintViewportBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1330 */       for (SynthPainter synthPainter : this.painters) {
/* 1331 */         synthPainter.paintViewportBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintViewportBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1337 */       for (SynthPainter synthPainter : this.painters)
/* 1338 */         synthPainter.paintViewportBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4); 
/*      */     }
/*      */   }
/*      */   
/*      */   private static class DelegatingPainter extends SynthPainter {
/*      */     private DelegatingPainter() {}
/*      */     
/*      */     private static SynthPainter getPainter(SynthContext param1SynthContext, String param1String, int param1Int) {
/* 1346 */       return ((ParsedSynthStyle)param1SynthContext.getStyle()).getBestPainter(param1SynthContext, param1String, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintArrowButtonBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1352 */       getPainter(param1SynthContext, "arrowbuttonbackground", -1)
/* 1353 */         .paintArrowButtonBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintArrowButtonBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1358 */       getPainter(param1SynthContext, "arrowbuttonborder", -1)
/* 1359 */         .paintArrowButtonBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintArrowButtonForeground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1364 */       getPainter(param1SynthContext, "arrowbuttonforeground", param1Int5)
/* 1365 */         .paintArrowButtonForeground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintButtonBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1370 */       getPainter(param1SynthContext, "buttonbackground", -1)
/* 1371 */         .paintButtonBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintButtonBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1376 */       getPainter(param1SynthContext, "buttonborder", -1)
/* 1377 */         .paintButtonBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintCheckBoxMenuItemBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1382 */       getPainter(param1SynthContext, "checkboxmenuitembackground", -1)
/* 1383 */         .paintCheckBoxMenuItemBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintCheckBoxMenuItemBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1388 */       getPainter(param1SynthContext, "checkboxmenuitemborder", -1)
/* 1389 */         .paintCheckBoxMenuItemBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintCheckBoxBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1394 */       getPainter(param1SynthContext, "checkboxbackground", -1)
/* 1395 */         .paintCheckBoxBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintCheckBoxBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1400 */       getPainter(param1SynthContext, "checkboxborder", -1)
/* 1401 */         .paintCheckBoxBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintColorChooserBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1406 */       getPainter(param1SynthContext, "colorchooserbackground", -1)
/* 1407 */         .paintColorChooserBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintColorChooserBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1412 */       getPainter(param1SynthContext, "colorchooserborder", -1)
/* 1413 */         .paintColorChooserBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintComboBoxBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1418 */       getPainter(param1SynthContext, "comboboxbackground", -1)
/* 1419 */         .paintComboBoxBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintComboBoxBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1424 */       getPainter(param1SynthContext, "comboboxborder", -1)
/* 1425 */         .paintComboBoxBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintDesktopIconBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1430 */       getPainter(param1SynthContext, "desktopiconbackground", -1)
/* 1431 */         .paintDesktopIconBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintDesktopIconBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1436 */       getPainter(param1SynthContext, "desktopiconborder", -1)
/* 1437 */         .paintDesktopIconBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintDesktopPaneBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1442 */       getPainter(param1SynthContext, "desktoppanebackground", -1)
/* 1443 */         .paintDesktopPaneBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintDesktopPaneBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1448 */       getPainter(param1SynthContext, "desktoppaneborder", -1)
/* 1449 */         .paintDesktopPaneBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintEditorPaneBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1454 */       getPainter(param1SynthContext, "editorpanebackground", -1)
/* 1455 */         .paintEditorPaneBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintEditorPaneBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1460 */       getPainter(param1SynthContext, "editorpaneborder", -1)
/* 1461 */         .paintEditorPaneBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintFileChooserBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1466 */       getPainter(param1SynthContext, "filechooserbackground", -1)
/* 1467 */         .paintFileChooserBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintFileChooserBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1472 */       getPainter(param1SynthContext, "filechooserborder", -1)
/* 1473 */         .paintFileChooserBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintFormattedTextFieldBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1478 */       getPainter(param1SynthContext, "formattedtextfieldbackground", -1)
/* 1479 */         .paintFormattedTextFieldBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintFormattedTextFieldBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1484 */       getPainter(param1SynthContext, "formattedtextfieldborder", -1)
/* 1485 */         .paintFormattedTextFieldBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintInternalFrameTitlePaneBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1490 */       getPainter(param1SynthContext, "internalframetitlepanebackground", -1)
/* 1491 */         .paintInternalFrameTitlePaneBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintInternalFrameTitlePaneBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1496 */       getPainter(param1SynthContext, "internalframetitlepaneborder", -1)
/* 1497 */         .paintInternalFrameTitlePaneBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintInternalFrameBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1502 */       getPainter(param1SynthContext, "internalframebackground", -1)
/* 1503 */         .paintInternalFrameBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintInternalFrameBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1508 */       getPainter(param1SynthContext, "internalframeborder", -1)
/* 1509 */         .paintInternalFrameBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintLabelBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1514 */       getPainter(param1SynthContext, "labelbackground", -1)
/* 1515 */         .paintLabelBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintLabelBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1520 */       getPainter(param1SynthContext, "labelborder", -1)
/* 1521 */         .paintLabelBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintListBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1526 */       getPainter(param1SynthContext, "listbackground", -1)
/* 1527 */         .paintListBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintListBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1532 */       getPainter(param1SynthContext, "listborder", -1)
/* 1533 */         .paintListBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintMenuBarBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1538 */       getPainter(param1SynthContext, "menubarbackground", -1)
/* 1539 */         .paintMenuBarBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintMenuBarBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1544 */       getPainter(param1SynthContext, "menubarborder", -1)
/* 1545 */         .paintMenuBarBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintMenuItemBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1550 */       getPainter(param1SynthContext, "menuitembackground", -1)
/* 1551 */         .paintMenuItemBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintMenuItemBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1556 */       getPainter(param1SynthContext, "menuitemborder", -1)
/* 1557 */         .paintMenuItemBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintMenuBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1562 */       getPainter(param1SynthContext, "menubackground", -1)
/* 1563 */         .paintMenuBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintMenuBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1568 */       getPainter(param1SynthContext, "menuborder", -1)
/* 1569 */         .paintMenuBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintOptionPaneBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1574 */       getPainter(param1SynthContext, "optionpanebackground", -1)
/* 1575 */         .paintOptionPaneBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintOptionPaneBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1580 */       getPainter(param1SynthContext, "optionpaneborder", -1)
/* 1581 */         .paintOptionPaneBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintPanelBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1586 */       getPainter(param1SynthContext, "panelbackground", -1)
/* 1587 */         .paintPanelBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintPanelBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1592 */       getPainter(param1SynthContext, "panelborder", -1)
/* 1593 */         .paintPanelBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintPasswordFieldBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1598 */       getPainter(param1SynthContext, "passwordfieldbackground", -1)
/* 1599 */         .paintPasswordFieldBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintPasswordFieldBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1604 */       getPainter(param1SynthContext, "passwordfieldborder", -1)
/* 1605 */         .paintPasswordFieldBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintPopupMenuBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1610 */       getPainter(param1SynthContext, "popupmenubackground", -1)
/* 1611 */         .paintPopupMenuBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintPopupMenuBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1616 */       getPainter(param1SynthContext, "popupmenuborder", -1)
/* 1617 */         .paintPopupMenuBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintProgressBarBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1622 */       getPainter(param1SynthContext, "progressbarbackground", -1)
/* 1623 */         .paintProgressBarBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintProgressBarBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1628 */       getPainter(param1SynthContext, "progressbarbackground", param1Int5)
/* 1629 */         .paintProgressBarBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintProgressBarBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1634 */       getPainter(param1SynthContext, "progressbarborder", -1)
/* 1635 */         .paintProgressBarBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintProgressBarBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1640 */       getPainter(param1SynthContext, "progressbarborder", param1Int5)
/* 1641 */         .paintProgressBarBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintProgressBarForeground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1646 */       getPainter(param1SynthContext, "progressbarforeground", param1Int5)
/* 1647 */         .paintProgressBarForeground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintRadioButtonMenuItemBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1652 */       getPainter(param1SynthContext, "radiobuttonmenuitembackground", -1)
/* 1653 */         .paintRadioButtonMenuItemBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintRadioButtonMenuItemBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1658 */       getPainter(param1SynthContext, "radiobuttonmenuitemborder", -1)
/* 1659 */         .paintRadioButtonMenuItemBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintRadioButtonBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1664 */       getPainter(param1SynthContext, "radiobuttonbackground", -1)
/* 1665 */         .paintRadioButtonBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintRadioButtonBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1670 */       getPainter(param1SynthContext, "radiobuttonborder", -1)
/* 1671 */         .paintRadioButtonBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintRootPaneBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1676 */       getPainter(param1SynthContext, "rootpanebackground", -1)
/* 1677 */         .paintRootPaneBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintRootPaneBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1682 */       getPainter(param1SynthContext, "rootpaneborder", -1)
/* 1683 */         .paintRootPaneBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintScrollBarBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1688 */       getPainter(param1SynthContext, "scrollbarbackground", -1)
/* 1689 */         .paintScrollBarBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintScrollBarBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1694 */       getPainter(param1SynthContext, "scrollbarbackground", param1Int5)
/* 1695 */         .paintScrollBarBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintScrollBarBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1701 */       getPainter(param1SynthContext, "scrollbarborder", -1)
/* 1702 */         .paintScrollBarBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintScrollBarBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1708 */       getPainter(param1SynthContext, "scrollbarborder", param1Int5)
/* 1709 */         .paintScrollBarBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintScrollBarThumbBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1714 */       getPainter(param1SynthContext, "scrollbarthumbbackground", param1Int5)
/* 1715 */         .paintScrollBarThumbBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintScrollBarThumbBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1720 */       getPainter(param1SynthContext, "scrollbarthumbborder", param1Int5)
/* 1721 */         .paintScrollBarThumbBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintScrollBarTrackBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1726 */       getPainter(param1SynthContext, "scrollbartrackbackground", -1)
/* 1727 */         .paintScrollBarTrackBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintScrollBarTrackBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1732 */       getPainter(param1SynthContext, "scrollbartrackbackground", param1Int5)
/* 1733 */         .paintScrollBarTrackBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintScrollBarTrackBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1738 */       getPainter(param1SynthContext, "scrollbartrackborder", -1)
/* 1739 */         .paintScrollBarTrackBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintScrollBarTrackBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1744 */       getPainter(param1SynthContext, "scrollbartrackborder", param1Int5)
/* 1745 */         .paintScrollBarTrackBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintScrollPaneBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1750 */       getPainter(param1SynthContext, "scrollpanebackground", -1)
/* 1751 */         .paintScrollPaneBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintScrollPaneBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1756 */       getPainter(param1SynthContext, "scrollpaneborder", -1)
/* 1757 */         .paintScrollPaneBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSeparatorBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1762 */       getPainter(param1SynthContext, "separatorbackground", -1)
/* 1763 */         .paintSeparatorBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSeparatorBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1768 */       getPainter(param1SynthContext, "separatorbackground", param1Int5)
/* 1769 */         .paintSeparatorBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSeparatorBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1774 */       getPainter(param1SynthContext, "separatorborder", -1)
/* 1775 */         .paintSeparatorBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSeparatorBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1780 */       getPainter(param1SynthContext, "separatorborder", param1Int5)
/* 1781 */         .paintSeparatorBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSeparatorForeground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1786 */       getPainter(param1SynthContext, "separatorforeground", param1Int5)
/* 1787 */         .paintSeparatorForeground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSliderBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1792 */       getPainter(param1SynthContext, "sliderbackground", -1)
/* 1793 */         .paintSliderBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSliderBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1798 */       getPainter(param1SynthContext, "sliderbackground", param1Int5)
/* 1799 */         .paintSliderBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSliderBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1804 */       getPainter(param1SynthContext, "sliderborder", -1)
/* 1805 */         .paintSliderBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSliderBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1810 */       getPainter(param1SynthContext, "sliderborder", param1Int5)
/* 1811 */         .paintSliderBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSliderThumbBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1816 */       getPainter(param1SynthContext, "sliderthumbbackground", param1Int5)
/* 1817 */         .paintSliderThumbBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSliderThumbBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1822 */       getPainter(param1SynthContext, "sliderthumbborder", param1Int5)
/* 1823 */         .paintSliderThumbBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSliderTrackBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1828 */       getPainter(param1SynthContext, "slidertrackbackground", -1)
/* 1829 */         .paintSliderTrackBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSliderTrackBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1834 */       getPainter(param1SynthContext, "slidertrackbackground", param1Int5)
/* 1835 */         .paintSliderTrackBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSliderTrackBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1840 */       getPainter(param1SynthContext, "slidertrackborder", -1)
/* 1841 */         .paintSliderTrackBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSliderTrackBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1846 */       getPainter(param1SynthContext, "slidertrackborder", param1Int5)
/* 1847 */         .paintSliderTrackBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSpinnerBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1852 */       getPainter(param1SynthContext, "spinnerbackground", -1)
/* 1853 */         .paintSpinnerBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSpinnerBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1858 */       getPainter(param1SynthContext, "spinnerborder", -1)
/* 1859 */         .paintSpinnerBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSplitPaneDividerBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1864 */       getPainter(param1SynthContext, "splitpanedividerbackground", -1)
/* 1865 */         .paintSplitPaneDividerBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSplitPaneDividerBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1870 */       getPainter(param1SynthContext, "splitpanedividerbackground", param1Int5)
/* 1871 */         .paintSplitPaneDividerBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSplitPaneDividerForeground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1876 */       getPainter(param1SynthContext, "splitpanedividerforeground", param1Int5)
/* 1877 */         .paintSplitPaneDividerForeground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintSplitPaneDragDivider(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1883 */       getPainter(param1SynthContext, "splitpanedragdivider", param1Int5)
/* 1884 */         .paintSplitPaneDragDivider(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSplitPaneBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1889 */       getPainter(param1SynthContext, "splitpanebackground", -1)
/* 1890 */         .paintSplitPaneBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintSplitPaneBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1895 */       getPainter(param1SynthContext, "splitpaneborder", -1)
/* 1896 */         .paintSplitPaneBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1901 */       getPainter(param1SynthContext, "tabbedpanebackground", -1)
/* 1902 */         .paintTabbedPaneBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1907 */       getPainter(param1SynthContext, "tabbedpaneborder", -1)
/* 1908 */         .paintTabbedPaneBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneTabAreaBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1913 */       getPainter(param1SynthContext, "tabbedpanetabareabackground", -1)
/* 1914 */         .paintTabbedPaneTabAreaBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneTabAreaBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1919 */       getPainter(param1SynthContext, "tabbedpanetabareabackground", param1Int5)
/* 1920 */         .paintTabbedPaneTabAreaBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneTabAreaBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1926 */       getPainter(param1SynthContext, "tabbedpanetabareaborder", -1)
/* 1927 */         .paintTabbedPaneTabAreaBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneTabAreaBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1932 */       getPainter(param1SynthContext, "tabbedpanetabareaborder", param1Int5)
/* 1933 */         .paintTabbedPaneTabAreaBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneTabBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1939 */       getPainter(param1SynthContext, "tabbedpanetabbackground", -1)
/* 1940 */         .paintTabbedPaneTabBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneTabBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
/* 1946 */       getPainter(param1SynthContext, "tabbedpanetabbackground", param1Int6)
/* 1947 */         .paintTabbedPaneTabBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneTabBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1953 */       getPainter(param1SynthContext, "tabbedpanetabborder", -1)
/* 1954 */         .paintTabbedPaneTabBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneTabBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
/* 1960 */       getPainter(param1SynthContext, "tabbedpanetabborder", param1Int6)
/* 1961 */         .paintTabbedPaneTabBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneContentBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1967 */       getPainter(param1SynthContext, "tabbedpanecontentbackground", -1)
/* 1968 */         .paintTabbedPaneContentBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTabbedPaneContentBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1973 */       getPainter(param1SynthContext, "tabbedpanecontentborder", -1)
/* 1974 */         .paintTabbedPaneContentBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTableHeaderBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1979 */       getPainter(param1SynthContext, "tableheaderbackground", -1)
/* 1980 */         .paintTableHeaderBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTableHeaderBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1985 */       getPainter(param1SynthContext, "tableheaderborder", -1)
/* 1986 */         .paintTableHeaderBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTableBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1991 */       getPainter(param1SynthContext, "tablebackground", -1)
/* 1992 */         .paintTableBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTableBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1997 */       getPainter(param1SynthContext, "tableborder", -1)
/* 1998 */         .paintTableBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTextAreaBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2003 */       getPainter(param1SynthContext, "textareabackground", -1)
/* 2004 */         .paintTextAreaBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTextAreaBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2009 */       getPainter(param1SynthContext, "textareaborder", -1)
/* 2010 */         .paintTextAreaBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTextPaneBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2015 */       getPainter(param1SynthContext, "textpanebackground", -1)
/* 2016 */         .paintTextPaneBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTextPaneBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2021 */       getPainter(param1SynthContext, "textpaneborder", -1)
/* 2022 */         .paintTextPaneBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTextFieldBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2027 */       getPainter(param1SynthContext, "textfieldbackground", -1)
/* 2028 */         .paintTextFieldBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTextFieldBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2033 */       getPainter(param1SynthContext, "textfieldborder", -1)
/* 2034 */         .paintTextFieldBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintToggleButtonBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2039 */       getPainter(param1SynthContext, "togglebuttonbackground", -1)
/* 2040 */         .paintToggleButtonBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintToggleButtonBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2045 */       getPainter(param1SynthContext, "togglebuttonborder", -1)
/* 2046 */         .paintToggleButtonBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintToolBarBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2051 */       getPainter(param1SynthContext, "toolbarbackground", -1)
/* 2052 */         .paintToolBarBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintToolBarBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 2057 */       getPainter(param1SynthContext, "toolbarbackground", param1Int5)
/* 2058 */         .paintToolBarBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintToolBarBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2063 */       getPainter(param1SynthContext, "toolbarborder", -1)
/* 2064 */         .paintToolBarBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintToolBarBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 2069 */       getPainter(param1SynthContext, "toolbarborder", param1Int5)
/* 2070 */         .paintToolBarBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintToolBarContentBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2075 */       getPainter(param1SynthContext, "toolbarcontentbackground", -1)
/* 2076 */         .paintToolBarContentBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintToolBarContentBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 2081 */       getPainter(param1SynthContext, "toolbarcontentbackground", param1Int5)
/* 2082 */         .paintToolBarContentBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintToolBarContentBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2087 */       getPainter(param1SynthContext, "toolbarcontentborder", -1)
/* 2088 */         .paintToolBarContentBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintToolBarContentBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 2093 */       getPainter(param1SynthContext, "toolbarcontentborder", param1Int5)
/* 2094 */         .paintToolBarContentBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintToolBarDragWindowBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2099 */       getPainter(param1SynthContext, "toolbardragwindowbackground", -1)
/* 2100 */         .paintToolBarDragWindowBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintToolBarDragWindowBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 2105 */       getPainter(param1SynthContext, "toolbardragwindowbackground", param1Int5)
/* 2106 */         .paintToolBarDragWindowBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintToolBarDragWindowBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2111 */       getPainter(param1SynthContext, "toolbardragwindowborder", -1)
/* 2112 */         .paintToolBarDragWindowBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintToolBarDragWindowBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 2117 */       getPainter(param1SynthContext, "toolbardragwindowborder", param1Int5)
/* 2118 */         .paintToolBarDragWindowBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintToolTipBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2123 */       getPainter(param1SynthContext, "tooltipbackground", -1)
/* 2124 */         .paintToolTipBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintToolTipBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2129 */       getPainter(param1SynthContext, "tooltipborder", -1)
/* 2130 */         .paintToolTipBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTreeBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2135 */       getPainter(param1SynthContext, "treebackground", -1)
/* 2136 */         .paintTreeBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTreeBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2141 */       getPainter(param1SynthContext, "treeborder", -1)
/* 2142 */         .paintTreeBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTreeCellBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2147 */       getPainter(param1SynthContext, "treecellbackground", -1)
/* 2148 */         .paintTreeCellBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTreeCellBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2153 */       getPainter(param1SynthContext, "treecellborder", -1)
/* 2154 */         .paintTreeCellBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintTreeCellFocus(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2159 */       getPainter(param1SynthContext, "treecellfocus", -1)
/* 2160 */         .paintTreeCellFocus(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintViewportBackground(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2165 */       getPainter(param1SynthContext, "viewportbackground", -1)
/* 2166 */         .paintViewportBackground(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintViewportBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2171 */       getPainter(param1SynthContext, "viewportborder", -1)
/* 2172 */         .paintViewportBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/ParsedSynthStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */