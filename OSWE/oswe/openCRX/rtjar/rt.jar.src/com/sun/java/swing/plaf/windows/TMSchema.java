/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.util.EnumMap;
/*     */ import javax.swing.JComponent;
/*     */ import sun.awt.windows.ThemeReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TMSchema
/*     */ {
/*     */   public enum Control
/*     */   {
/*  65 */     BUTTON,
/*  66 */     COMBOBOX,
/*  67 */     EDIT,
/*  68 */     HEADER,
/*  69 */     LISTBOX,
/*  70 */     LISTVIEW,
/*  71 */     MENU,
/*  72 */     PROGRESS,
/*  73 */     REBAR,
/*  74 */     SCROLLBAR,
/*  75 */     SPIN,
/*  76 */     TAB,
/*  77 */     TOOLBAR,
/*  78 */     TRACKBAR,
/*  79 */     TREEVIEW,
/*  80 */     WINDOW;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Part
/*     */   {
/*  88 */     MENU((String)TMSchema.Control.MENU, 0),
/*  89 */     MP_BARBACKGROUND((String)TMSchema.Control.MENU, 7),
/*  90 */     MP_BARITEM((String)TMSchema.Control.MENU, 8),
/*  91 */     MP_POPUPBACKGROUND((String)TMSchema.Control.MENU, 9),
/*  92 */     MP_POPUPBORDERS((String)TMSchema.Control.MENU, 10),
/*  93 */     MP_POPUPCHECK((String)TMSchema.Control.MENU, 11),
/*  94 */     MP_POPUPCHECKBACKGROUND((String)TMSchema.Control.MENU, 12),
/*  95 */     MP_POPUPGUTTER((String)TMSchema.Control.MENU, 13),
/*  96 */     MP_POPUPITEM((String)TMSchema.Control.MENU, 14),
/*  97 */     MP_POPUPSEPARATOR((String)TMSchema.Control.MENU, 15),
/*  98 */     MP_POPUPSUBMENU((String)TMSchema.Control.MENU, 16),
/*     */     
/* 100 */     BP_PUSHBUTTON((String)TMSchema.Control.BUTTON, 1),
/* 101 */     BP_RADIOBUTTON((String)TMSchema.Control.BUTTON, 2),
/* 102 */     BP_CHECKBOX((String)TMSchema.Control.BUTTON, 3),
/* 103 */     BP_GROUPBOX((String)TMSchema.Control.BUTTON, 4),
/*     */     
/* 105 */     CP_COMBOBOX((String)TMSchema.Control.COMBOBOX, 0),
/* 106 */     CP_DROPDOWNBUTTON((String)TMSchema.Control.COMBOBOX, 1),
/* 107 */     CP_BACKGROUND((String)TMSchema.Control.COMBOBOX, 2),
/* 108 */     CP_TRANSPARENTBACKGROUND((String)TMSchema.Control.COMBOBOX, 3),
/* 109 */     CP_BORDER((String)TMSchema.Control.COMBOBOX, 4),
/* 110 */     CP_READONLY((String)TMSchema.Control.COMBOBOX, 5),
/* 111 */     CP_DROPDOWNBUTTONRIGHT((String)TMSchema.Control.COMBOBOX, 6),
/* 112 */     CP_DROPDOWNBUTTONLEFT((String)TMSchema.Control.COMBOBOX, 7),
/* 113 */     CP_CUEBANNER((String)TMSchema.Control.COMBOBOX, 8),
/*     */ 
/*     */     
/* 116 */     EP_EDIT((String)TMSchema.Control.EDIT, 0),
/* 117 */     EP_EDITTEXT((String)TMSchema.Control.EDIT, 1),
/*     */     
/* 119 */     HP_HEADERITEM((String)TMSchema.Control.HEADER, 1),
/* 120 */     HP_HEADERSORTARROW((String)TMSchema.Control.HEADER, 4),
/*     */     
/* 122 */     LBP_LISTBOX((String)TMSchema.Control.LISTBOX, 0),
/*     */     
/* 124 */     LVP_LISTVIEW((String)TMSchema.Control.LISTVIEW, 0),
/*     */     
/* 126 */     PP_PROGRESS((String)TMSchema.Control.PROGRESS, 0),
/* 127 */     PP_BAR((String)TMSchema.Control.PROGRESS, 1),
/* 128 */     PP_BARVERT((String)TMSchema.Control.PROGRESS, 2),
/* 129 */     PP_CHUNK((String)TMSchema.Control.PROGRESS, 3),
/* 130 */     PP_CHUNKVERT((String)TMSchema.Control.PROGRESS, 4),
/*     */     
/* 132 */     RP_GRIPPER((String)TMSchema.Control.REBAR, 1),
/* 133 */     RP_GRIPPERVERT((String)TMSchema.Control.REBAR, 2),
/*     */     
/* 135 */     SBP_SCROLLBAR((String)TMSchema.Control.SCROLLBAR, 0),
/* 136 */     SBP_ARROWBTN((String)TMSchema.Control.SCROLLBAR, 1),
/* 137 */     SBP_THUMBBTNHORZ((String)TMSchema.Control.SCROLLBAR, 2),
/* 138 */     SBP_THUMBBTNVERT((String)TMSchema.Control.SCROLLBAR, 3),
/* 139 */     SBP_LOWERTRACKHORZ((String)TMSchema.Control.SCROLLBAR, 4),
/* 140 */     SBP_UPPERTRACKHORZ((String)TMSchema.Control.SCROLLBAR, 5),
/* 141 */     SBP_LOWERTRACKVERT((String)TMSchema.Control.SCROLLBAR, 6),
/* 142 */     SBP_UPPERTRACKVERT((String)TMSchema.Control.SCROLLBAR, 7),
/* 143 */     SBP_GRIPPERHORZ((String)TMSchema.Control.SCROLLBAR, 8),
/* 144 */     SBP_GRIPPERVERT((String)TMSchema.Control.SCROLLBAR, 9),
/* 145 */     SBP_SIZEBOX((String)TMSchema.Control.SCROLLBAR, 10),
/*     */     
/* 147 */     SPNP_UP((String)TMSchema.Control.SPIN, 1),
/* 148 */     SPNP_DOWN((String)TMSchema.Control.SPIN, 2),
/*     */     
/* 150 */     TABP_TABITEM((String)TMSchema.Control.TAB, 1),
/* 151 */     TABP_TABITEMLEFTEDGE((String)TMSchema.Control.TAB, 2),
/* 152 */     TABP_TABITEMRIGHTEDGE((String)TMSchema.Control.TAB, 3),
/* 153 */     TABP_PANE((String)TMSchema.Control.TAB, 9),
/*     */     
/* 155 */     TP_TOOLBAR((String)TMSchema.Control.TOOLBAR, 0),
/* 156 */     TP_BUTTON((String)TMSchema.Control.TOOLBAR, 1),
/* 157 */     TP_SEPARATOR((String)TMSchema.Control.TOOLBAR, 5),
/* 158 */     TP_SEPARATORVERT((String)TMSchema.Control.TOOLBAR, 6),
/*     */     
/* 160 */     TKP_TRACK((String)TMSchema.Control.TRACKBAR, 1),
/* 161 */     TKP_TRACKVERT((String)TMSchema.Control.TRACKBAR, 2),
/* 162 */     TKP_THUMB((String)TMSchema.Control.TRACKBAR, 3),
/* 163 */     TKP_THUMBBOTTOM((String)TMSchema.Control.TRACKBAR, 4),
/* 164 */     TKP_THUMBTOP((String)TMSchema.Control.TRACKBAR, 5),
/* 165 */     TKP_THUMBVERT((String)TMSchema.Control.TRACKBAR, 6),
/* 166 */     TKP_THUMBLEFT((String)TMSchema.Control.TRACKBAR, 7),
/* 167 */     TKP_THUMBRIGHT((String)TMSchema.Control.TRACKBAR, 8),
/* 168 */     TKP_TICS((String)TMSchema.Control.TRACKBAR, 9),
/* 169 */     TKP_TICSVERT((String)TMSchema.Control.TRACKBAR, 10),
/*     */     
/* 171 */     TVP_TREEVIEW((String)TMSchema.Control.TREEVIEW, 0),
/* 172 */     TVP_GLYPH((String)TMSchema.Control.TREEVIEW, 2),
/*     */     
/* 174 */     WP_WINDOW((String)TMSchema.Control.WINDOW, 0),
/* 175 */     WP_CAPTION((String)TMSchema.Control.WINDOW, 1),
/* 176 */     WP_MINCAPTION((String)TMSchema.Control.WINDOW, 3),
/* 177 */     WP_MAXCAPTION((String)TMSchema.Control.WINDOW, 5),
/* 178 */     WP_FRAMELEFT((String)TMSchema.Control.WINDOW, 7),
/* 179 */     WP_FRAMERIGHT((String)TMSchema.Control.WINDOW, 8),
/* 180 */     WP_FRAMEBOTTOM((String)TMSchema.Control.WINDOW, 9),
/* 181 */     WP_SYSBUTTON((String)TMSchema.Control.WINDOW, 13),
/* 182 */     WP_MDISYSBUTTON((String)TMSchema.Control.WINDOW, 14),
/* 183 */     WP_MINBUTTON((String)TMSchema.Control.WINDOW, 15),
/* 184 */     WP_MDIMINBUTTON((String)TMSchema.Control.WINDOW, 16),
/* 185 */     WP_MAXBUTTON((String)TMSchema.Control.WINDOW, 17),
/* 186 */     WP_CLOSEBUTTON((String)TMSchema.Control.WINDOW, 18),
/* 187 */     WP_MDICLOSEBUTTON((String)TMSchema.Control.WINDOW, 20),
/* 188 */     WP_RESTOREBUTTON((String)TMSchema.Control.WINDOW, 21),
/* 189 */     WP_MDIRESTOREBUTTON((String)TMSchema.Control.WINDOW, 22);
/*     */     
/*     */     private final TMSchema.Control control;
/*     */     private final int value;
/*     */     
/*     */     Part(TMSchema.Control param1Control, int param1Int1) {
/* 195 */       this.control = param1Control;
/* 196 */       this.value = param1Int1;
/*     */     }
/*     */     
/*     */     public int getValue() {
/* 200 */       return this.value;
/*     */     }
/*     */     
/*     */     public String getControlName(Component param1Component) {
/* 204 */       String str = "";
/* 205 */       if (param1Component instanceof JComponent) {
/* 206 */         JComponent jComponent = (JComponent)param1Component;
/* 207 */         String str1 = (String)jComponent.getClientProperty("XPStyle.subAppName");
/* 208 */         if (str1 != null) {
/* 209 */           str = str1 + "::";
/*     */         }
/*     */       } 
/* 212 */       return str + this.control.toString();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 216 */       return this.control.toString() + "." + name();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum State
/*     */   {
/* 225 */     ACTIVE,
/* 226 */     ASSIST,
/* 227 */     BITMAP,
/* 228 */     CHECKED,
/* 229 */     CHECKEDDISABLED,
/* 230 */     CHECKEDHOT,
/* 231 */     CHECKEDNORMAL,
/* 232 */     CHECKEDPRESSED,
/* 233 */     CHECKMARKNORMAL,
/* 234 */     CHECKMARKDISABLED,
/* 235 */     BULLETNORMAL,
/* 236 */     BULLETDISABLED,
/* 237 */     CLOSED,
/* 238 */     DEFAULTED,
/* 239 */     DISABLED,
/* 240 */     DISABLEDHOT,
/* 241 */     DISABLEDPUSHED,
/* 242 */     DOWNDISABLED,
/* 243 */     DOWNHOT,
/* 244 */     DOWNNORMAL,
/* 245 */     DOWNPRESSED,
/* 246 */     FOCUSED,
/* 247 */     HOT,
/* 248 */     HOTCHECKED,
/* 249 */     ICONHOT,
/* 250 */     ICONNORMAL,
/* 251 */     ICONPRESSED,
/* 252 */     ICONSORTEDHOT,
/* 253 */     ICONSORTEDNORMAL,
/* 254 */     ICONSORTEDPRESSED,
/* 255 */     INACTIVE,
/* 256 */     INACTIVENORMAL,
/* 257 */     INACTIVEHOT,
/* 258 */     INACTIVEPUSHED,
/* 259 */     INACTIVEDISABLED,
/* 260 */     LEFTDISABLED,
/* 261 */     LEFTHOT,
/* 262 */     LEFTNORMAL,
/* 263 */     LEFTPRESSED,
/* 264 */     MIXEDDISABLED,
/* 265 */     MIXEDHOT,
/* 266 */     MIXEDNORMAL,
/* 267 */     MIXEDPRESSED,
/* 268 */     NORMAL,
/* 269 */     PRESSED,
/* 270 */     OPENED,
/* 271 */     PUSHED,
/* 272 */     READONLY,
/* 273 */     RIGHTDISABLED,
/* 274 */     RIGHTHOT,
/* 275 */     RIGHTNORMAL,
/* 276 */     RIGHTPRESSED,
/* 277 */     SELECTED,
/* 278 */     UNCHECKEDDISABLED,
/* 279 */     UNCHECKEDHOT,
/* 280 */     UNCHECKEDNORMAL,
/* 281 */     UNCHECKEDPRESSED,
/* 282 */     UPDISABLED,
/* 283 */     UPHOT,
/* 284 */     UPNORMAL,
/* 285 */     UPPRESSED,
/* 286 */     HOVER,
/* 287 */     UPHOVER,
/* 288 */     DOWNHOVER,
/* 289 */     LEFTHOVER,
/* 290 */     RIGHTHOVER,
/* 291 */     SORTEDDOWN,
/* 292 */     SORTEDHOT,
/* 293 */     SORTEDNORMAL,
/* 294 */     SORTEDPRESSED,
/* 295 */     SORTEDUP;
/*     */ 
/*     */ 
/*     */     
/*     */     private static EnumMap<TMSchema.Part, State[]> stateMap;
/*     */ 
/*     */ 
/*     */     
/*     */     private static synchronized void initStates() {
/* 304 */       stateMap = (EnumMap)new EnumMap<>(TMSchema.Part.class);
/*     */       
/* 306 */       stateMap.put(TMSchema.Part.EP_EDITTEXT, new State[] { NORMAL, HOT, SELECTED, DISABLED, FOCUSED, READONLY, ASSIST });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 311 */       stateMap.put(TMSchema.Part.BP_PUSHBUTTON, new State[] { NORMAL, HOT, PRESSED, DISABLED, DEFAULTED });
/*     */ 
/*     */       
/* 314 */       stateMap.put(TMSchema.Part.BP_RADIOBUTTON, new State[] { UNCHECKEDNORMAL, UNCHECKEDHOT, UNCHECKEDPRESSED, UNCHECKEDDISABLED, CHECKEDNORMAL, CHECKEDHOT, CHECKEDPRESSED, CHECKEDDISABLED });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 320 */       stateMap.put(TMSchema.Part.BP_CHECKBOX, new State[] { UNCHECKEDNORMAL, UNCHECKEDHOT, UNCHECKEDPRESSED, UNCHECKEDDISABLED, CHECKEDNORMAL, CHECKEDHOT, CHECKEDPRESSED, CHECKEDDISABLED, MIXEDNORMAL, MIXEDHOT, MIXEDPRESSED, MIXEDDISABLED });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 327 */       State[] arrayOfState1 = { NORMAL, HOT, PRESSED, DISABLED };
/* 328 */       stateMap.put(TMSchema.Part.CP_COMBOBOX, arrayOfState1);
/* 329 */       stateMap.put(TMSchema.Part.CP_DROPDOWNBUTTON, arrayOfState1);
/* 330 */       stateMap.put(TMSchema.Part.CP_BACKGROUND, arrayOfState1);
/* 331 */       stateMap.put(TMSchema.Part.CP_TRANSPARENTBACKGROUND, arrayOfState1);
/* 332 */       stateMap.put(TMSchema.Part.CP_BORDER, arrayOfState1);
/* 333 */       stateMap.put(TMSchema.Part.CP_READONLY, arrayOfState1);
/* 334 */       stateMap.put(TMSchema.Part.CP_DROPDOWNBUTTONRIGHT, arrayOfState1);
/* 335 */       stateMap.put(TMSchema.Part.CP_DROPDOWNBUTTONLEFT, arrayOfState1);
/* 336 */       stateMap.put(TMSchema.Part.CP_CUEBANNER, arrayOfState1);
/*     */       
/* 338 */       stateMap.put(TMSchema.Part.HP_HEADERITEM, new State[] { NORMAL, HOT, PRESSED, SORTEDNORMAL, SORTEDHOT, SORTEDPRESSED, ICONNORMAL, ICONHOT, ICONPRESSED, ICONSORTEDNORMAL, ICONSORTEDHOT, ICONSORTEDPRESSED });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 343 */       stateMap.put(TMSchema.Part.HP_HEADERSORTARROW, new State[] { SORTEDDOWN, SORTEDUP });
/*     */ 
/*     */       
/* 346 */       State[] arrayOfState2 = { NORMAL, HOT, PRESSED, DISABLED, HOVER };
/* 347 */       stateMap.put(TMSchema.Part.SBP_SCROLLBAR, arrayOfState2);
/* 348 */       stateMap.put(TMSchema.Part.SBP_THUMBBTNVERT, arrayOfState2);
/* 349 */       stateMap.put(TMSchema.Part.SBP_THUMBBTNHORZ, arrayOfState2);
/* 350 */       stateMap.put(TMSchema.Part.SBP_GRIPPERVERT, arrayOfState2);
/* 351 */       stateMap.put(TMSchema.Part.SBP_GRIPPERHORZ, arrayOfState2);
/*     */       
/* 353 */       stateMap.put(TMSchema.Part.SBP_ARROWBTN, new State[] { UPNORMAL, UPHOT, UPPRESSED, UPDISABLED, DOWNNORMAL, DOWNHOT, DOWNPRESSED, DOWNDISABLED, LEFTNORMAL, LEFTHOT, LEFTPRESSED, LEFTDISABLED, RIGHTNORMAL, RIGHTHOT, RIGHTPRESSED, RIGHTDISABLED, UPHOVER, DOWNHOVER, LEFTHOVER, RIGHTHOVER });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 363 */       State[] arrayOfState3 = { NORMAL, HOT, PRESSED, DISABLED };
/* 364 */       stateMap.put(TMSchema.Part.SPNP_UP, arrayOfState3);
/* 365 */       stateMap.put(TMSchema.Part.SPNP_DOWN, arrayOfState3);
/*     */       
/* 367 */       stateMap.put(TMSchema.Part.TVP_GLYPH, new State[] { CLOSED, OPENED });
/*     */       
/* 369 */       State[] arrayOfState4 = { NORMAL, HOT, PUSHED, DISABLED, INACTIVENORMAL, INACTIVEHOT, INACTIVEPUSHED, INACTIVEDISABLED };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 377 */       if (ThemeReader.getInt(TMSchema.Control.WINDOW.toString(), TMSchema.Part.WP_CLOSEBUTTON
/* 378 */           .getValue(), 1, TMSchema.Prop.IMAGECOUNT
/* 379 */           .getValue()) == 10) {
/* 380 */         arrayOfState4 = new State[] { NORMAL, HOT, PUSHED, DISABLED, null, INACTIVENORMAL, INACTIVEHOT, INACTIVEPUSHED, INACTIVEDISABLED, null };
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 386 */       stateMap.put(TMSchema.Part.WP_MINBUTTON, arrayOfState4);
/* 387 */       stateMap.put(TMSchema.Part.WP_MAXBUTTON, arrayOfState4);
/* 388 */       stateMap.put(TMSchema.Part.WP_RESTOREBUTTON, arrayOfState4);
/* 389 */       stateMap.put(TMSchema.Part.WP_CLOSEBUTTON, arrayOfState4);
/*     */ 
/*     */       
/* 392 */       stateMap.put(TMSchema.Part.TKP_TRACK, new State[] { NORMAL });
/* 393 */       stateMap.put(TMSchema.Part.TKP_TRACKVERT, new State[] { NORMAL });
/*     */       
/* 395 */       State[] arrayOfState5 = { NORMAL, HOT, PRESSED, FOCUSED, DISABLED };
/*     */       
/* 397 */       stateMap.put(TMSchema.Part.TKP_THUMB, arrayOfState5);
/* 398 */       stateMap.put(TMSchema.Part.TKP_THUMBBOTTOM, arrayOfState5);
/* 399 */       stateMap.put(TMSchema.Part.TKP_THUMBTOP, arrayOfState5);
/* 400 */       stateMap.put(TMSchema.Part.TKP_THUMBVERT, arrayOfState5);
/* 401 */       stateMap.put(TMSchema.Part.TKP_THUMBRIGHT, arrayOfState5);
/*     */ 
/*     */       
/* 404 */       State[] arrayOfState6 = { NORMAL, HOT, SELECTED, DISABLED, FOCUSED };
/* 405 */       stateMap.put(TMSchema.Part.TABP_TABITEM, arrayOfState6);
/* 406 */       stateMap.put(TMSchema.Part.TABP_TABITEMLEFTEDGE, arrayOfState6);
/* 407 */       stateMap.put(TMSchema.Part.TABP_TABITEMRIGHTEDGE, arrayOfState6);
/*     */ 
/*     */       
/* 410 */       stateMap.put(TMSchema.Part.TP_BUTTON, new State[] { NORMAL, HOT, PRESSED, DISABLED, CHECKED, HOTCHECKED });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 415 */       State[] arrayOfState7 = { ACTIVE, INACTIVE };
/* 416 */       stateMap.put(TMSchema.Part.WP_WINDOW, arrayOfState7);
/* 417 */       stateMap.put(TMSchema.Part.WP_FRAMELEFT, arrayOfState7);
/* 418 */       stateMap.put(TMSchema.Part.WP_FRAMERIGHT, arrayOfState7);
/* 419 */       stateMap.put(TMSchema.Part.WP_FRAMEBOTTOM, arrayOfState7);
/*     */       
/* 421 */       State[] arrayOfState8 = { ACTIVE, INACTIVE, DISABLED };
/* 422 */       stateMap.put(TMSchema.Part.WP_CAPTION, arrayOfState8);
/* 423 */       stateMap.put(TMSchema.Part.WP_MINCAPTION, arrayOfState8);
/* 424 */       stateMap.put(TMSchema.Part.WP_MAXCAPTION, arrayOfState8);
/*     */       
/* 426 */       stateMap.put(TMSchema.Part.MP_BARBACKGROUND, new State[] { ACTIVE, INACTIVE });
/*     */       
/* 428 */       stateMap.put(TMSchema.Part.MP_BARITEM, new State[] { NORMAL, HOT, PUSHED, DISABLED, DISABLEDHOT, DISABLEDPUSHED });
/*     */ 
/*     */       
/* 431 */       stateMap.put(TMSchema.Part.MP_POPUPCHECK, new State[] { CHECKMARKNORMAL, CHECKMARKDISABLED, BULLETNORMAL, BULLETDISABLED });
/*     */ 
/*     */       
/* 434 */       stateMap.put(TMSchema.Part.MP_POPUPCHECKBACKGROUND, new State[] { DISABLEDPUSHED, NORMAL, BITMAP });
/*     */       
/* 436 */       stateMap.put(TMSchema.Part.MP_POPUPITEM, new State[] { NORMAL, HOT, DISABLED, DISABLEDHOT });
/*     */       
/* 438 */       stateMap.put(TMSchema.Part.MP_POPUPSUBMENU, new State[] { NORMAL, DISABLED });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static synchronized int getValue(TMSchema.Part param1Part, State param1State) {
/* 445 */       if (stateMap == null) {
/* 446 */         initStates();
/*     */       }
/*     */       
/* 449 */       Enum[] arrayOfEnum = (Enum[])stateMap.get(param1Part);
/* 450 */       if (arrayOfEnum != null) {
/* 451 */         for (byte b = 0; b < arrayOfEnum.length; b++) {
/* 452 */           if (param1State == arrayOfEnum[b]) {
/* 453 */             return b + 1;
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 458 */       if (param1State == null || param1State == NORMAL) {
/* 459 */         return 1;
/*     */       }
/*     */       
/* 462 */       return 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Prop
/*     */   {
/* 473 */     COLOR((String)Color.class, 204),
/* 474 */     SIZE((String)Dimension.class, 207),
/*     */     
/* 476 */     FLATMENUS((String)Boolean.class, 1001),
/*     */     
/* 478 */     BORDERONLY((String)Boolean.class, 2203),
/*     */     
/* 480 */     IMAGECOUNT((String)Integer.class, 2401),
/* 481 */     BORDERSIZE((String)Integer.class, 2403),
/*     */     
/* 483 */     PROGRESSCHUNKSIZE((String)Integer.class, 2411),
/* 484 */     PROGRESSSPACESIZE((String)Integer.class, 2412),
/*     */     
/* 486 */     TEXTSHADOWOFFSET((String)Point.class, 3402),
/*     */     
/* 488 */     NORMALSIZE((String)Dimension.class, 3409),
/*     */ 
/*     */     
/* 491 */     SIZINGMARGINS((String)Insets.class, 3601),
/* 492 */     CONTENTMARGINS((String)Insets.class, 3602),
/* 493 */     CAPTIONMARGINS((String)Insets.class, 3603),
/*     */     
/* 495 */     BORDERCOLOR((String)Color.class, 3801),
/* 496 */     FILLCOLOR((String)Color.class, 3802),
/* 497 */     TEXTCOLOR((String)Color.class, 3803),
/*     */     
/* 499 */     TEXTSHADOWCOLOR((String)Color.class, 3818),
/*     */     
/* 501 */     BGTYPE((String)Integer.class, 4001),
/*     */     
/* 503 */     TEXTSHADOWTYPE((String)Integer.class, 4010),
/*     */     
/* 505 */     TRANSITIONDURATIONS((String)Integer.class, 6000);
/*     */     
/*     */     private final Class type;
/*     */     private final int value;
/*     */     
/*     */     Prop(Class param1Class, int param1Int1) {
/* 511 */       this.type = param1Class;
/* 512 */       this.value = param1Int1;
/*     */     }
/*     */     
/*     */     public int getValue() {
/* 516 */       return this.value;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 520 */       return name() + "[" + this.type.getName() + "] = " + this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum TypeEnum
/*     */   {
/* 529 */     BT_IMAGEFILE((String)TMSchema.Prop.BGTYPE, "imagefile", 0),
/* 530 */     BT_BORDERFILL((String)TMSchema.Prop.BGTYPE, "borderfill", 1),
/*     */     
/* 532 */     TST_NONE((String)TMSchema.Prop.TEXTSHADOWTYPE, "none", 0),
/* 533 */     TST_SINGLE((String)TMSchema.Prop.TEXTSHADOWTYPE, "single", 1),
/* 534 */     TST_CONTINUOUS((String)TMSchema.Prop.TEXTSHADOWTYPE, "continuous", 2);
/*     */     private final TMSchema.Prop prop;
/*     */     
/*     */     TypeEnum(TMSchema.Prop param1Prop, String param1String1, int param1Int1) {
/* 538 */       this.prop = param1Prop;
/* 539 */       this.enumName = param1String1;
/* 540 */       this.value = param1Int1;
/*     */     }
/*     */ 
/*     */     
/*     */     private final String enumName;
/*     */     private final int value;
/*     */     
/*     */     public String toString() {
/* 548 */       return this.prop + "=" + this.enumName + "=" + this.value;
/*     */     }
/*     */     
/*     */     String getName() {
/* 552 */       return this.enumName;
/*     */     }
/*     */ 
/*     */     
/*     */     static TypeEnum getTypeEnum(TMSchema.Prop param1Prop, int param1Int) {
/* 557 */       for (TypeEnum typeEnum : values()) {
/* 558 */         if (typeEnum.prop == param1Prop && typeEnum.value == param1Int) {
/* 559 */           return typeEnum;
/*     */         }
/*     */       } 
/* 562 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/TMSchema.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */