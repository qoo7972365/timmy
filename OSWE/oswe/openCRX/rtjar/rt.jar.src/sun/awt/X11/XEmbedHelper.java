/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.AWTKeyStroke;
/*     */ import sun.misc.Unsafe;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XEmbedHelper
/*     */ {
/*  40 */   private static final PlatformLogger xembedLog = PlatformLogger.getLogger("sun.awt.X11.xembed");
/*  41 */   static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */   
/*     */   static final int XEMBED_VERSION = 0;
/*     */   
/*     */   static final int XEMBED_MAPPED = 1;
/*     */   
/*     */   static final int XEMBED_EMBEDDED_NOTIFY = 0;
/*     */   
/*     */   static final int XEMBED_WINDOW_ACTIVATE = 1;
/*     */   
/*     */   static final int XEMBED_WINDOW_DEACTIVATE = 2;
/*     */   
/*     */   static final int XEMBED_REQUEST_FOCUS = 3;
/*     */   
/*     */   static final int XEMBED_FOCUS_IN = 4;
/*     */   
/*     */   static final int XEMBED_FOCUS_OUT = 5;
/*     */   
/*     */   static final int XEMBED_FOCUS_NEXT = 6;
/*     */   
/*     */   static final int XEMBED_FOCUS_PREV = 7;
/*     */   static final int XEMBED_GRAB_KEY = 8;
/*     */   static final int XEMBED_UNGRAB_KEY = 9;
/*     */   static final int XEMBED_MODALITY_ON = 10;
/*     */   static final int XEMBED_MODALITY_OFF = 11;
/*     */   static final int XEMBED_REGISTER_ACCELERATOR = 12;
/*     */   static final int XEMBED_UNREGISTER_ACCELERATOR = 13;
/*     */   static final int XEMBED_ACTIVATE_ACCELERATOR = 14;
/*     */   static final int NON_STANDARD_XEMBED_GTK_GRAB_KEY = 108;
/*     */   static final int NON_STANDARD_XEMBED_GTK_UNGRAB_KEY = 109;
/*     */   static final int XEMBED_FOCUS_CURRENT = 0;
/*     */   static final int XEMBED_FOCUS_FIRST = 1;
/*     */   static final int XEMBED_FOCUS_LAST = 2;
/*     */   static final int XEMBED_MODIFIER_SHIFT = 1;
/*     */   static final int XEMBED_MODIFIER_CONTROL = 2;
/*     */   static final int XEMBED_MODIFIER_ALT = 4;
/*     */   static final int XEMBED_MODIFIER_SUPER = 8;
/*     */   static final int XEMBED_MODIFIER_HYPER = 16;
/*     */   static XAtom XEmbedInfo;
/*     */   static XAtom XEmbed;
/*     */   
/*     */   XEmbedHelper() {
/*  83 */     if (XEmbed == null) {
/*  84 */       XEmbed = XAtom.get("_XEMBED");
/*  85 */       if (xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  86 */         xembedLog.finer("Created atom " + XEmbed.toString());
/*     */       }
/*     */     } 
/*  89 */     if (XEmbedInfo == null) {
/*  90 */       XEmbedInfo = XAtom.get("_XEMBED_INFO");
/*  91 */       if (xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  92 */         xembedLog.finer("Created atom " + XEmbedInfo.toString());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void sendMessage(long paramLong, int paramInt) {
/*  98 */     sendMessage(paramLong, paramInt, 0L, 0L, 0L);
/*     */   }
/*     */   void sendMessage(long paramLong1, int paramInt, long paramLong2, long paramLong3, long paramLong4) {
/* 101 */     XClientMessageEvent xClientMessageEvent = new XClientMessageEvent();
/* 102 */     xClientMessageEvent.set_type(33);
/* 103 */     xClientMessageEvent.set_window(paramLong1);
/* 104 */     xClientMessageEvent.set_message_type(XEmbed.getAtom());
/* 105 */     xClientMessageEvent.set_format(32);
/* 106 */     xClientMessageEvent.set_data(0, XToolkit.getCurrentServerTime());
/* 107 */     xClientMessageEvent.set_data(1, paramInt);
/* 108 */     xClientMessageEvent.set_data(2, paramLong2);
/* 109 */     xClientMessageEvent.set_data(3, paramLong3);
/* 110 */     xClientMessageEvent.set_data(4, paramLong4);
/* 111 */     XToolkit.awtLock();
/*     */     try {
/* 113 */       if (xembedLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 114 */         xembedLog.fine("Sending " + XEmbedMessageToString(xClientMessageEvent));
/*     */       }
/* 116 */       XlibWrapper.XSendEvent(XToolkit.getDisplay(), paramLong1, false, 0L, xClientMessageEvent.pData);
/*     */     } finally {
/*     */       
/* 119 */       XToolkit.awtUnlock();
/*     */     } 
/* 121 */     xClientMessageEvent.dispose();
/*     */   }
/*     */   
/*     */   static String msgidToString(int paramInt) {
/* 125 */     switch (paramInt) {
/*     */       case 0:
/* 127 */         return "XEMBED_EMBEDDED_NOTIFY";
/*     */       case 1:
/* 129 */         return "XEMBED_WINDOW_ACTIVATE";
/*     */       case 2:
/* 131 */         return "XEMBED_WINDOW_DEACTIVATE";
/*     */       case 4:
/* 133 */         return "XEMBED_FOCUS_IN";
/*     */       case 5:
/* 135 */         return "XEMBED_FOCUS_OUT";
/*     */       case 3:
/* 137 */         return "XEMBED_REQUEST_FOCUS";
/*     */       case 6:
/* 139 */         return "XEMBED_FOCUS_NEXT";
/*     */       case 7:
/* 141 */         return "XEMBED_FOCUS_PREV";
/*     */       case 10:
/* 143 */         return "XEMBED_MODALITY_ON";
/*     */       case 11:
/* 145 */         return "XEMBED_MODALITY_OFF";
/*     */       case 12:
/* 147 */         return "XEMBED_REGISTER_ACCELERATOR";
/*     */       case 13:
/* 149 */         return "XEMBED_UNREGISTER_ACCELERATOR";
/*     */       case 14:
/* 151 */         return "XEMBED_ACTIVATE_ACCELERATOR";
/*     */       case 8:
/* 153 */         return "XEMBED_GRAB_KEY";
/*     */       case 9:
/* 155 */         return "XEMBED_UNGRAB_KEY";
/*     */       case 109:
/* 157 */         return "NON_STANDARD_XEMBED_GTK_UNGRAB_KEY";
/*     */       case 108:
/* 159 */         return "NON_STANDARD_XEMBED_GTK_GRAB_KEY";
/*     */       case 32770:
/* 161 */         return "KeyPress";
/*     */       case 32787:
/* 163 */         return "MapNotify";
/*     */       case 32796:
/* 165 */         return "PropertyNotify";
/*     */     } 
/* 167 */     return "unknown XEMBED id " + paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   static String focusIdToString(int paramInt) {
/* 172 */     switch (paramInt) {
/*     */       case 0:
/* 174 */         return "XEMBED_FOCUS_CURRENT";
/*     */       case 1:
/* 176 */         return "XEMBED_FOCUS_FIRST";
/*     */       case 2:
/* 178 */         return "XEMBED_FOCUS_LAST";
/*     */     } 
/* 180 */     return "unknown focus id " + paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   static String XEmbedMessageToString(XClientMessageEvent paramXClientMessageEvent) {
/* 185 */     return "XEmbed message to " + Long.toHexString(paramXClientMessageEvent.get_window()) + ": " + msgidToString((int)paramXClientMessageEvent.get_data(1)) + ", detail: " + paramXClientMessageEvent
/* 186 */       .get_data(2) + ", data:[" + paramXClientMessageEvent
/* 187 */       .get_data(3) + "," + paramXClientMessageEvent.get_data(4) + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getModifiers(int paramInt) {
/* 196 */     int i = 0;
/* 197 */     if ((paramInt & 0x1) != 0) {
/* 198 */       i |= 0x40;
/*     */     }
/* 200 */     if ((paramInt & 0x2) != 0) {
/* 201 */       i |= 0x80;
/*     */     }
/* 203 */     if ((paramInt & 0x4) != 0) {
/* 204 */       i |= 0x200;
/*     */     }
/*     */ 
/*     */     
/* 208 */     if ((paramInt & 0x8) != 0) {
/* 209 */       i |= 0x200;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 214 */     return i;
/*     */   }
/*     */   
/*     */   AWTKeyStroke getKeyStrokeForKeySym(long paramLong1, long paramLong2) {
/*     */     int i;
/* 219 */     XBaseWindow.checkSecurity();
/*     */ 
/*     */ 
/*     */     
/* 223 */     XToolkit.awtLock();
/*     */     try {
/* 225 */       XKeysym.Keysym2JavaKeycode keysym2JavaKeycode = XKeysym.getJavaKeycode(paramLong1);
/* 226 */       if (keysym2JavaKeycode == null) {
/* 227 */         i = 0;
/*     */       } else {
/* 229 */         i = keysym2JavaKeycode.getJavaKeycode();
/*     */       } 
/*     */     } finally {
/* 232 */       XToolkit.awtUnlock();
/*     */     } 
/*     */     
/* 235 */     int j = getModifiers((int)paramLong2);
/* 236 */     return AWTKeyStroke.getAWTKeyStroke(i, j);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XEmbedHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */