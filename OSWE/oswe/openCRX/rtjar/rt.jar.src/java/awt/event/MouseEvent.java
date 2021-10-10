/*      */ package java.awt.event;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.IllegalComponentStateException;
/*      */ import java.awt.Point;
/*      */ import java.awt.Toolkit;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.SunToolkit;
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
/*      */ public class MouseEvent
/*      */   extends InputEvent
/*      */ {
/*      */   public static final int MOUSE_FIRST = 500;
/*      */   public static final int MOUSE_LAST = 507;
/*      */   public static final int MOUSE_CLICKED = 500;
/*      */   public static final int MOUSE_PRESSED = 501;
/*      */   public static final int MOUSE_RELEASED = 502;
/*      */   public static final int MOUSE_MOVED = 503;
/*      */   public static final int MOUSE_ENTERED = 504;
/*      */   public static final int MOUSE_EXITED = 505;
/*      */   public static final int MOUSE_DRAGGED = 506;
/*      */   public static final int MOUSE_WHEEL = 507;
/*      */   public static final int NOBUTTON = 0;
/*      */   public static final int BUTTON1 = 1;
/*      */   public static final int BUTTON2 = 2;
/*      */   public static final int BUTTON3 = 3;
/*      */   int x;
/*      */   int y;
/*      */   private int xAbs;
/*      */   private int yAbs;
/*      */   int clickCount;
/*      */   private boolean causedByTouchEvent;
/*      */   int button;
/*      */   boolean popupTrigger = false;
/*      */   private static final long serialVersionUID = -991214153494842848L;
/*      */   private static int cachedNumberOfButtons;
/*      */   
/*      */   static {
/*  397 */     NativeLibLoader.loadLibraries();
/*  398 */     if (!GraphicsEnvironment.isHeadless()) {
/*  399 */       initIDs();
/*      */     }
/*  401 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  402 */     if (toolkit instanceof SunToolkit) {
/*  403 */       cachedNumberOfButtons = ((SunToolkit)toolkit).getNumberOfButtons();
/*      */     }
/*      */     else {
/*      */       
/*  407 */       cachedNumberOfButtons = 3;
/*      */     } 
/*  409 */     AWTAccessor.setMouseEventAccessor(new AWTAccessor.MouseEventAccessor()
/*      */         {
/*      */           public boolean isCausedByTouchEvent(MouseEvent param1MouseEvent) {
/*  412 */             return param1MouseEvent.causedByTouchEvent;
/*      */           }
/*      */ 
/*      */           
/*      */           public void setCausedByTouchEvent(MouseEvent param1MouseEvent, boolean param1Boolean) {
/*  417 */             param1MouseEvent.causedByTouchEvent = param1Boolean;
/*      */           }
/*      */         });
/*      */   }
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
/*      */   public Point getLocationOnScreen() {
/*  443 */     return new Point(this.xAbs, this.yAbs);
/*      */   }
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
/*      */   public int getXOnScreen() {
/*  460 */     return this.xAbs;
/*      */   }
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
/*      */   public int getYOnScreen() {
/*  477 */     return this.yAbs;
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MouseEvent(Component paramComponent, int paramInt1, long paramLong, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean, int paramInt6) {
/*  573 */     this(paramComponent, paramInt1, paramLong, paramInt2, paramInt3, paramInt4, 0, 0, paramInt5, paramBoolean, paramInt6);
/*  574 */     Point point = new Point(0, 0);
/*      */     try {
/*  576 */       point = paramComponent.getLocationOnScreen();
/*  577 */       this.xAbs = point.x + paramInt3;
/*  578 */       this.yAbs = point.y + paramInt4;
/*  579 */     } catch (IllegalComponentStateException illegalComponentStateException) {
/*  580 */       this.xAbs = 0;
/*  581 */       this.yAbs = 0;
/*      */     } 
/*      */   }
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
/*      */   public MouseEvent(Component paramComponent, int paramInt1, long paramLong, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean) {
/*  638 */     this(paramComponent, paramInt1, paramLong, paramInt2, paramInt3, paramInt4, paramInt5, paramBoolean, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean shouldExcludeButtonFromExtModifiers = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getModifiersEx() {
/*  650 */     int i = this.modifiers;
/*  651 */     if (this.shouldExcludeButtonFromExtModifiers) {
/*  652 */       i &= InputEvent.getMaskForButton(getButton()) ^ 0xFFFFFFFF;
/*      */     }
/*  654 */     return i & 0xFFFFFFC0;
/*      */   }
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
/*      */   public MouseEvent(Component paramComponent, int paramInt1, long paramLong, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean, int paramInt8) {
/*  754 */     super(paramComponent, paramInt1, paramLong, paramInt2);
/*  755 */     this.x = paramInt3;
/*  756 */     this.y = paramInt4;
/*  757 */     this.xAbs = paramInt5;
/*  758 */     this.yAbs = paramInt6;
/*  759 */     this.clickCount = paramInt7;
/*  760 */     this.popupTrigger = paramBoolean;
/*  761 */     if (paramInt8 < 0) {
/*  762 */       throw new IllegalArgumentException("Invalid button value :" + paramInt8);
/*      */     }
/*  764 */     if (paramInt8 > 3) {
/*  765 */       if (!Toolkit.getDefaultToolkit().areExtraMouseButtonsEnabled()) {
/*  766 */         throw new IllegalArgumentException("Extra mouse events are disabled " + paramInt8);
/*      */       }
/*  768 */       if (paramInt8 > cachedNumberOfButtons) {
/*  769 */         throw new IllegalArgumentException("Nonexistent button " + paramInt8);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  780 */       if (getModifiersEx() != 0 && (
/*  781 */         paramInt1 == 502 || paramInt1 == 500)) {
/*  782 */         this.shouldExcludeButtonFromExtModifiers = true;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  787 */     this.button = paramInt8;
/*      */     
/*  789 */     if (getModifiers() != 0 && getModifiersEx() == 0) {
/*  790 */       setNewModifiers();
/*  791 */     } else if (getModifiers() == 0 && (
/*  792 */       getModifiersEx() != 0 || paramInt8 != 0) && paramInt8 <= 3) {
/*      */ 
/*      */       
/*  795 */       setOldModifiers();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getX() {
/*  807 */     return this.x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getY() {
/*  818 */     return this.y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Point getPoint() {
/*      */     int i;
/*      */     int j;
/*  831 */     synchronized (this) {
/*  832 */       i = this.x;
/*  833 */       j = this.y;
/*      */     } 
/*  835 */     return new Point(i, j);
/*      */   }
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
/*      */   public synchronized void translatePoint(int paramInt1, int paramInt2) {
/*  849 */     this.x += paramInt1;
/*  850 */     this.y += paramInt2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getClickCount() {
/*  859 */     return this.clickCount;
/*      */   }
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
/*      */   public int getButton() {
/*  915 */     return this.button;
/*      */   }
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
/*      */   public boolean isPopupTrigger() {
/*  931 */     return this.popupTrigger;
/*      */   }
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
/*      */   public static String getMouseModifiersText(int paramInt) {
/*  960 */     StringBuilder stringBuilder = new StringBuilder();
/*  961 */     if ((paramInt & 0x8) != 0) {
/*  962 */       stringBuilder.append(Toolkit.getProperty("AWT.alt", "Alt"));
/*  963 */       stringBuilder.append("+");
/*      */     } 
/*  965 */     if ((paramInt & 0x4) != 0) {
/*  966 */       stringBuilder.append(Toolkit.getProperty("AWT.meta", "Meta"));
/*  967 */       stringBuilder.append("+");
/*      */     } 
/*  969 */     if ((paramInt & 0x2) != 0) {
/*  970 */       stringBuilder.append(Toolkit.getProperty("AWT.control", "Ctrl"));
/*  971 */       stringBuilder.append("+");
/*      */     } 
/*  973 */     if ((paramInt & 0x1) != 0) {
/*  974 */       stringBuilder.append(Toolkit.getProperty("AWT.shift", "Shift"));
/*  975 */       stringBuilder.append("+");
/*      */     } 
/*  977 */     if ((paramInt & 0x20) != 0) {
/*  978 */       stringBuilder.append(Toolkit.getProperty("AWT.altGraph", "Alt Graph"));
/*  979 */       stringBuilder.append("+");
/*      */     } 
/*  981 */     if ((paramInt & 0x10) != 0) {
/*  982 */       stringBuilder.append(Toolkit.getProperty("AWT.button1", "Button1"));
/*  983 */       stringBuilder.append("+");
/*      */     } 
/*  985 */     if ((paramInt & 0x8) != 0) {
/*  986 */       stringBuilder.append(Toolkit.getProperty("AWT.button2", "Button2"));
/*  987 */       stringBuilder.append("+");
/*      */     } 
/*  989 */     if ((paramInt & 0x4) != 0) {
/*  990 */       stringBuilder.append(Toolkit.getProperty("AWT.button3", "Button3"));
/*  991 */       stringBuilder.append("+");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1001 */     for (byte b = 1; b <= cachedNumberOfButtons; b++) {
/* 1002 */       int i = InputEvent.getMaskForButton(b);
/* 1003 */       if ((paramInt & i) != 0 && stringBuilder
/* 1004 */         .indexOf(Toolkit.getProperty("AWT.button" + b, "Button" + b)) == -1) {
/*      */         
/* 1006 */         stringBuilder.append(Toolkit.getProperty("AWT.button" + b, "Button" + b));
/* 1007 */         stringBuilder.append("+");
/*      */       } 
/*      */     } 
/*      */     
/* 1011 */     if (stringBuilder.length() > 0) {
/* 1012 */       stringBuilder.setLength(stringBuilder.length() - 1);
/*      */     }
/* 1014 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String paramString() {
/* 1024 */     StringBuilder stringBuilder = new StringBuilder(80);
/*      */     
/* 1026 */     switch (this.id) {
/*      */       case 501:
/* 1028 */         stringBuilder.append("MOUSE_PRESSED");
/*      */         break;
/*      */       case 502:
/* 1031 */         stringBuilder.append("MOUSE_RELEASED");
/*      */         break;
/*      */       case 500:
/* 1034 */         stringBuilder.append("MOUSE_CLICKED");
/*      */         break;
/*      */       case 504:
/* 1037 */         stringBuilder.append("MOUSE_ENTERED");
/*      */         break;
/*      */       case 505:
/* 1040 */         stringBuilder.append("MOUSE_EXITED");
/*      */         break;
/*      */       case 503:
/* 1043 */         stringBuilder.append("MOUSE_MOVED");
/*      */         break;
/*      */       case 506:
/* 1046 */         stringBuilder.append("MOUSE_DRAGGED");
/*      */         break;
/*      */       case 507:
/* 1049 */         stringBuilder.append("MOUSE_WHEEL");
/*      */         break;
/*      */       default:
/* 1052 */         stringBuilder.append("unknown type");
/*      */         break;
/*      */     } 
/*      */     
/* 1056 */     stringBuilder.append(",(").append(this.x).append(",").append(this.y).append(")");
/* 1057 */     stringBuilder.append(",absolute(").append(this.xAbs).append(",").append(this.yAbs).append(")");
/*      */     
/* 1059 */     if (this.id != 506 && this.id != 503) {
/* 1060 */       stringBuilder.append(",button=").append(getButton());
/*      */     }
/*      */     
/* 1063 */     if (getModifiers() != 0) {
/* 1064 */       stringBuilder.append(",modifiers=").append(getMouseModifiersText(this.modifiers));
/*      */     }
/*      */     
/* 1067 */     if (getModifiersEx() != 0)
/*      */     {
/*      */       
/* 1070 */       stringBuilder.append(",extModifiers=").append(getModifiersExText(getModifiersEx()));
/*      */     }
/*      */     
/* 1073 */     stringBuilder.append(",clickCount=").append(this.clickCount);
/*      */     
/* 1075 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setNewModifiers() {
/* 1083 */     if ((this.modifiers & 0x10) != 0) {
/* 1084 */       this.modifiers |= 0x400;
/*      */     }
/* 1086 */     if ((this.modifiers & 0x8) != 0) {
/* 1087 */       this.modifiers |= 0x800;
/*      */     }
/* 1089 */     if ((this.modifiers & 0x4) != 0) {
/* 1090 */       this.modifiers |= 0x1000;
/*      */     }
/* 1092 */     if (this.id == 501 || this.id == 502 || this.id == 500)
/*      */     {
/*      */ 
/*      */       
/* 1096 */       if ((this.modifiers & 0x10) != 0) {
/* 1097 */         this.button = 1;
/* 1098 */         this.modifiers &= 0xFFFFFFF3;
/* 1099 */         if (this.id != 501) {
/* 1100 */           this.modifiers &= 0xFFFFFBFF;
/*      */         }
/* 1102 */       } else if ((this.modifiers & 0x8) != 0) {
/* 1103 */         this.button = 2;
/* 1104 */         this.modifiers &= 0xFFFFFFEB;
/* 1105 */         if (this.id != 501) {
/* 1106 */           this.modifiers &= 0xFFFFF7FF;
/*      */         }
/* 1108 */       } else if ((this.modifiers & 0x4) != 0) {
/* 1109 */         this.button = 3;
/* 1110 */         this.modifiers &= 0xFFFFFFE7;
/* 1111 */         if (this.id != 501) {
/* 1112 */           this.modifiers &= 0xFFFFEFFF;
/*      */         }
/*      */       } 
/*      */     }
/* 1116 */     if ((this.modifiers & 0x8) != 0) {
/* 1117 */       this.modifiers |= 0x200;
/*      */     }
/* 1119 */     if ((this.modifiers & 0x4) != 0) {
/* 1120 */       this.modifiers |= 0x100;
/*      */     }
/* 1122 */     if ((this.modifiers & 0x1) != 0) {
/* 1123 */       this.modifiers |= 0x40;
/*      */     }
/* 1125 */     if ((this.modifiers & 0x2) != 0) {
/* 1126 */       this.modifiers |= 0x80;
/*      */     }
/* 1128 */     if ((this.modifiers & 0x20) != 0) {
/* 1129 */       this.modifiers |= 0x2000;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setOldModifiers() {
/* 1137 */     if (this.id == 501 || this.id == 502 || this.id == 500) {
/*      */ 
/*      */ 
/*      */       
/* 1141 */       switch (this.button) {
/*      */         case 1:
/* 1143 */           this.modifiers |= 0x10;
/*      */           break;
/*      */         case 2:
/* 1146 */           this.modifiers |= 0x8;
/*      */           break;
/*      */         case 3:
/* 1149 */           this.modifiers |= 0x4;
/*      */           break;
/*      */       } 
/*      */     } else {
/* 1153 */       if ((this.modifiers & 0x400) != 0) {
/* 1154 */         this.modifiers |= 0x10;
/*      */       }
/* 1156 */       if ((this.modifiers & 0x800) != 0) {
/* 1157 */         this.modifiers |= 0x8;
/*      */       }
/* 1159 */       if ((this.modifiers & 0x1000) != 0) {
/* 1160 */         this.modifiers |= 0x4;
/*      */       }
/*      */     } 
/* 1163 */     if ((this.modifiers & 0x200) != 0) {
/* 1164 */       this.modifiers |= 0x8;
/*      */     }
/* 1166 */     if ((this.modifiers & 0x100) != 0) {
/* 1167 */       this.modifiers |= 0x4;
/*      */     }
/* 1169 */     if ((this.modifiers & 0x40) != 0) {
/* 1170 */       this.modifiers |= 0x1;
/*      */     }
/* 1172 */     if ((this.modifiers & 0x80) != 0) {
/* 1173 */       this.modifiers |= 0x2;
/*      */     }
/* 1175 */     if ((this.modifiers & 0x2000) != 0) {
/* 1176 */       this.modifiers |= 0x20;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1186 */     paramObjectInputStream.defaultReadObject();
/* 1187 */     if (getModifiers() != 0 && getModifiersEx() == 0)
/* 1188 */       setNewModifiers(); 
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/event/MouseEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */