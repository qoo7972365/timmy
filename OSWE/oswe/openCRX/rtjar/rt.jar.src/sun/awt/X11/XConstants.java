/*     */ package sun.awt.X11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XConstants
/*     */ {
/*     */   public static final int X_PROTOCOL = 11;
/*     */   public static final int X_PROTOCOL_REVISION = 0;
/*     */   public static final long None = 0L;
/*     */   public static final long ParentRelative = 1L;
/*     */   public static final long CopyFromParent = 0L;
/*     */   public static final long PointerWindow = 0L;
/*     */   public static final long InputFocus = 1L;
/*     */   public static final long PointerRoot = 1L;
/*     */   public static final long AnyPropertyType = 0L;
/*     */   public static final long AnyKey = 0L;
/*     */   public static final long AnyButton = 0L;
/*     */   public static final long AllTemporary = 0L;
/*     */   public static final long CurrentTime = 0L;
/*     */   public static final long NoSymbol = 0L;
/*     */   public static final long NoEventMask = 0L;
/*     */   public static final long KeyPressMask = 1L;
/*     */   public static final long KeyReleaseMask = 2L;
/*     */   public static final long ButtonPressMask = 4L;
/*     */   public static final long ButtonReleaseMask = 8L;
/*     */   public static final long EnterWindowMask = 16L;
/*     */   public static final long LeaveWindowMask = 32L;
/*     */   public static final long PointerMotionMask = 64L;
/*     */   public static final long PointerMotionHintMask = 128L;
/*     */   public static final long Button1MotionMask = 256L;
/*     */   public static final long Button2MotionMask = 512L;
/*     */   public static final long Button3MotionMask = 1024L;
/*     */   public static final long Button4MotionMask = 2048L;
/*     */   public static final long Button5MotionMask = 4096L;
/*     */   public static final long ButtonMotionMask = 8192L;
/*     */   public static final long KeymapStateMask = 16384L;
/*     */   public static final long ExposureMask = 32768L;
/*     */   public static final long VisibilityChangeMask = 65536L;
/*     */   public static final long StructureNotifyMask = 131072L;
/*     */   public static final long ResizeRedirectMask = 262144L;
/*     */   public static final long SubstructureNotifyMask = 524288L;
/*     */   public static final long SubstructureRedirectMask = 1048576L;
/*     */   public static final long FocusChangeMask = 2097152L;
/*     */   public static final long PropertyChangeMask = 4194304L;
/*     */   public static final long ColormapChangeMask = 8388608L;
/*     */   public static final long OwnerGrabButtonMask = 16777216L;
/*     */   public static final int MAX_BUTTONS = 5;
/*     */   public static final int ALL_BUTTONS_MASK = 7936;
/*     */   public static final int KeyPress = 2;
/*     */   public static final int KeyRelease = 3;
/*     */   public static final int ButtonPress = 4;
/*     */   public static final int ButtonRelease = 5;
/*     */   public static final int MotionNotify = 6;
/*     */   public static final int EnterNotify = 7;
/*     */   public static final int LeaveNotify = 8;
/*     */   public static final int FocusIn = 9;
/*     */   public static final int FocusOut = 10;
/*     */   public static final int KeymapNotify = 11;
/*     */   public static final int Expose = 12;
/*     */   public static final int GraphicsExpose = 13;
/*     */   public static final int NoExpose = 14;
/*     */   public static final int VisibilityNotify = 15;
/*     */   public static final int CreateNotify = 16;
/*     */   public static final int DestroyNotify = 17;
/*     */   public static final int UnmapNotify = 18;
/*     */   public static final int MapNotify = 19;
/*     */   public static final int MapRequest = 20;
/*     */   public static final int ReparentNotify = 21;
/*     */   public static final int ConfigureNotify = 22;
/*     */   public static final int ConfigureRequest = 23;
/*     */   public static final int GravityNotify = 24;
/*     */   public static final int ResizeRequest = 25;
/*     */   public static final int CirculateNotify = 26;
/*     */   public static final int CirculateRequest = 27;
/*     */   public static final int PropertyNotify = 28;
/*     */   public static final int SelectionClear = 29;
/*     */   public static final int SelectionRequest = 30;
/*     */   public static final int SelectionNotify = 31;
/*     */   public static final int ColormapNotify = 32;
/*     */   public static final int ClientMessage = 33;
/*     */   public static final int MappingNotify = 34;
/*     */   public static final int LASTEvent = 35;
/*     */   public static final int ShiftMask = 1;
/*     */   public static final int LockMask = 2;
/*     */   public static final int ControlMask = 4;
/*     */   public static final int Mod1Mask = 8;
/*     */   public static final int Mod2Mask = 16;
/*     */   public static final int Mod3Mask = 32;
/*     */   public static final int Mod4Mask = 64;
/*     */   public static final int Mod5Mask = 128;
/*     */   public static final int ShiftMapIndex = 0;
/*     */   public static final int LockMapIndex = 1;
/*     */   public static final int ControlMapIndex = 2;
/*     */   public static final int Mod1MapIndex = 3;
/*     */   public static final int Mod2MapIndex = 4;
/*     */   public static final int Mod3MapIndex = 5;
/*     */   public static final int Mod4MapIndex = 6;
/*     */   public static final int Mod5MapIndex = 7;
/*     */   public static final int AnyModifier = 32768;
/* 207 */   public static final int[] buttons = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24 };
/*     */   public static final int NotifyNormal = 0;
/*     */   public static final int NotifyGrab = 1;
/*     */   public static final int NotifyUngrab = 2;
/*     */   public static final int NotifyWhileGrabbed = 3;
/*     */   public static final int NotifyHint = 1;
/*     */   public static final int NotifyAncestor = 0;
/*     */   public static final int NotifyVirtual = 1;
/*     */   public static final int NotifyInferior = 2;
/*     */   public static final int NotifyNonlinear = 3;
/*     */   public static final int NotifyNonlinearVirtual = 4;
/*     */   public static final int NotifyPointer = 5;
/*     */   public static final int NotifyPointerRoot = 6;
/*     */   public static final int NotifyDetailNone = 7;
/*     */   public static final int VisibilityUnobscured = 0;
/*     */   public static final int VisibilityPartiallyObscured = 1;
/*     */   public static final int VisibilityFullyObscured = 2;
/*     */   public static final int PlaceOnTop = 0;
/*     */   public static final int PlaceOnBottom = 1;
/*     */   public static final int FamilyInternet = 0;
/*     */   public static final int FamilyDECnet = 1;
/*     */   public static final int FamilyChaos = 2;
/*     */   public static final int PropertyNewValue = 0;
/*     */   public static final int PropertyDelete = 1;
/*     */   public static final int ColormapUninstalled = 0;
/*     */   public static final int ColormapInstalled = 1;
/*     */   public static final int GrabModeSync = 0;
/*     */   public static final int GrabModeAsync = 1;
/*     */   public static final int GrabSuccess = 0;
/*     */   public static final int AlreadyGrabbed = 1;
/*     */   public static final int GrabInvalidTime = 2;
/*     */   public static final int GrabNotViewable = 3;
/*     */   public static final int GrabFrozen = 4;
/*     */   public static final int AsyncPointer = 0;
/*     */   public static final int SyncPointer = 1;
/*     */   public static final int ReplayPointer = 2;
/*     */   public static final int AsyncKeyboard = 3;
/*     */   public static final int SyncKeyboard = 4;
/*     */   public static final int ReplayKeyboard = 5;
/*     */   public static final int AsyncBoth = 6;
/*     */   public static final int SyncBoth = 7;
/*     */   public static final int RevertToNone = 0;
/*     */   public static final int RevertToPointerRoot = 1;
/*     */   public static final int RevertToParent = 2;
/*     */   public static final int QueuedAlready = 0;
/*     */   public static final int QueuedAfterReading = 1;
/*     */   public static final int QueuedAfterFlush = 2;
/*     */   public static final int Success = 0;
/*     */   public static final int BadRequest = 1;
/*     */   public static final int BadValue = 2;
/*     */   public static final int BadWindow = 3;
/*     */   public static final int BadPixmap = 4;
/*     */   public static final int BadAtom = 5;
/*     */   public static final int BadCursor = 6;
/*     */   public static final int BadFont = 7;
/*     */   public static final int BadMatch = 8;
/*     */   public static final int BadDrawable = 9;
/*     */   public static final int BadAccess = 10;
/*     */   public static final int BadAlloc = 11;
/*     */   public static final int BadColor = 12;
/*     */   public static final int BadGC = 13;
/*     */   public static final int BadIDChoice = 14;
/*     */   public static final int BadName = 15;
/*     */   public static final int BadLength = 16;
/*     */   public static final int BadImplementation = 17;
/*     */   public static final int FirstExtensionError = 128;
/*     */   public static final int LastExtensionError = 255;
/*     */   public static final int InputOutput = 1;
/*     */   public static final int InputOnly = 2;
/*     */   public static final long CWBackPixmap = 1L;
/*     */   public static final long CWBackPixel = 2L;
/*     */   public static final long CWBorderPixmap = 4L;
/*     */   public static final long CWBorderPixel = 8L;
/*     */   public static final long CWBitGravity = 16L;
/*     */   public static final long CWWinGravity = 32L;
/*     */   public static final long CWBackingStore = 64L;
/*     */   public static final long CWBackingPlanes = 128L;
/*     */   public static final long CWBackingPixel = 256L;
/*     */   public static final long CWOverrideRedirect = 512L;
/*     */   public static final long CWSaveUnder = 1024L;
/*     */   public static final long CWEventMask = 2048L;
/*     */   public static final long CWDontPropagate = 4096L;
/*     */   public static final long CWColormap = 8192L;
/*     */   public static final long CWCursor = 16384L;
/*     */   public static final int CWX = 1;
/*     */   public static final int CWY = 2;
/*     */   public static final int CWWidth = 4;
/*     */   public static final int CWHeight = 8;
/*     */   public static final int CWBorderWidth = 16;
/*     */   public static final int CWSibling = 32;
/*     */   public static final int CWStackMode = 64;
/*     */   public static final int ForgetGravity = 0;
/*     */   public static final int NorthWestGravity = 1;
/*     */   public static final int NorthGravity = 2;
/*     */   public static final int NorthEastGravity = 3;
/*     */   public static final int WestGravity = 4;
/*     */   public static final int CenterGravity = 5;
/*     */   public static final int EastGravity = 6;
/*     */   public static final int SouthWestGravity = 7;
/*     */   public static final int SouthGravity = 8;
/*     */   public static final int SouthEastGravity = 9;
/*     */   public static final int StaticGravity = 10;
/*     */   public static final int UnmapGravity = 0;
/*     */   public static final int NotUseful = 0;
/*     */   public static final int WhenMapped = 1;
/*     */   public static final int Always = 2;
/*     */   public static final int IsUnmapped = 0;
/*     */   public static final int IsUnviewable = 1;
/*     */   public static final int IsViewable = 2;
/*     */   public static final int SetModeInsert = 0;
/*     */   public static final int SetModeDelete = 1;
/*     */   public static final int DestroyAll = 0;
/*     */   public static final int RetainPermanent = 1;
/*     */   public static final int RetainTemporary = 2;
/*     */   public static final int Above = 0;
/*     */   public static final int Below = 1;
/*     */   public static final int TopIf = 2;
/*     */   public static final int BottomIf = 3;
/*     */   public static final int Opposite = 4;
/*     */   public static final int RaiseLowest = 0;
/*     */   public static final int LowerHighest = 1;
/*     */   public static final int PropModeReplace = 0;
/*     */   public static final int PropModePrepend = 1;
/*     */   public static final int PropModeAppend = 2;
/*     */   public static final int GXclear = 0;
/*     */   public static final int GXand = 1;
/*     */   public static final int GXandReverse = 2;
/*     */   public static final int GXcopy = 3;
/*     */   public static final int GXandInverted = 4;
/*     */   public static final int GXnoop = 5;
/*     */   public static final int GXxor = 6;
/*     */   public static final int GXor = 7;
/*     */   public static final int GXnor = 8;
/*     */   public static final int GXequiv = 9;
/*     */   public static final int GXinvert = 10;
/*     */   public static final int GXorReverse = 11;
/*     */   public static final int GXcopyInverted = 12;
/*     */   public static final int GXorInverted = 13;
/*     */   public static final int GXnand = 14;
/*     */   public static final int GXset = 15;
/*     */   public static final int LineSolid = 0;
/*     */   public static final int LineOnOffDash = 1;
/*     */   public static final int LineDoubleDash = 2;
/*     */   public static final int CapNotLast = 0;
/*     */   public static final int CapButt = 1;
/*     */   public static final int CapRound = 2;
/*     */   public static final int CapProjecting = 3;
/*     */   public static final int JoinMiter = 0;
/*     */   public static final int JoinRound = 1;
/*     */   public static final int JoinBevel = 2;
/*     */   public static final int FillSolid = 0;
/*     */   public static final int FillTiled = 1;
/*     */   public static final int FillStippled = 2;
/*     */   public static final int FillOpaqueStippled = 3;
/*     */   public static final int EvenOddRule = 0;
/*     */   public static final int WindingRule = 1;
/*     */   public static final int ClipByChildren = 0;
/*     */   public static final int IncludeInferiors = 1;
/*     */   public static final int Unsorted = 0;
/*     */   public static final int YSorted = 1;
/*     */   public static final int YXSorted = 2;
/*     */   public static final int YXBanded = 3;
/*     */   public static final int CoordModeOrigin = 0;
/*     */   public static final int CoordModePrevious = 1;
/*     */   public static final int Complex = 0;
/*     */   public static final int Nonconvex = 1;
/*     */   public static final int Convex = 2;
/*     */   public static final int ArcChord = 0;
/*     */   public static final int ArcPieSlice = 1;
/*     */   public static final long GCFunction = 1L;
/*     */   public static final long GCPlaneMask = 2L;
/*     */   public static final long GCForeground = 4L;
/*     */   public static final long GCBackground = 8L;
/*     */   public static final long GCLineWidth = 16L;
/*     */   public static final long GCLineStyle = 32L;
/*     */   public static final long GCCapStyle = 64L;
/*     */   public static final long GCJoinStyle = 128L;
/*     */   public static final long GCFillStyle = 256L;
/*     */   public static final long GCFillRule = 512L;
/*     */   public static final long GCTile = 1024L;
/*     */   public static final long GCStipple = 2048L;
/*     */   public static final long GCTileStipXOrigin = 4096L;
/*     */   public static final long GCTileStipYOrigin = 8192L;
/*     */   public static final long GCFont = 16384L;
/*     */   public static final long GCSubwindowMode = 32768L;
/*     */   public static final long GCGraphicsExposures = 65536L;
/*     */   public static final long GCClipXOrigin = 131072L;
/*     */   public static final long GCClipYOrigin = 262144L;
/*     */   public static final long GCClipMask = 524288L;
/*     */   public static final long GCDashOffset = 1048576L;
/*     */   public static final long GCDashList = 2097152L;
/*     */   public static final long GCArcMode = 4194304L;
/*     */   public static final int GCLastBit = 22;
/*     */   public static final int FontLeftToRight = 0;
/*     */   public static final int FontRightToLeft = 1;
/*     */   public static final int FontChange = 255;
/*     */   public static final int XYBitmap = 0;
/*     */   public static final int XYPixmap = 1;
/*     */   public static final int ZPixmap = 2;
/*     */   public static final int AllocNone = 0;
/*     */   public static final int AllocAll = 1;
/*     */   public static final int DoRed = 1;
/*     */   public static final int DoGreen = 2;
/*     */   public static final int DoBlue = 4;
/*     */   public static final int CursorShape = 0;
/*     */   public static final int TileShape = 1;
/*     */   public static final int StippleShape = 2;
/*     */   public static final int AutoRepeatModeOff = 0;
/*     */   public static final int AutoRepeatModeOn = 1;
/*     */   public static final int AutoRepeatModeDefault = 2;
/*     */   public static final int LedModeOff = 0;
/*     */   public static final int LedModeOn = 1;
/*     */   public static final long KBKeyClickPercent = 1L;
/*     */   public static final long KBBellPercent = 2L;
/*     */   public static final long KBBellPitch = 4L;
/*     */   public static final long KBBellDuration = 8L;
/*     */   public static final long KBLed = 16L;
/*     */   public static final long KBLedMode = 32L;
/*     */   public static final long KBKey = 64L;
/*     */   public static final long KBAutoRepeatMode = 128L;
/*     */   public static final int MappingSuccess = 0;
/*     */   public static final int MappingBusy = 1;
/*     */   public static final int MappingFailed = 2;
/*     */   public static final int MappingModifier = 0;
/*     */   public static final int MappingKeyboard = 1;
/*     */   public static final int MappingPointer = 2;
/*     */   public static final int DontPreferBlanking = 0;
/*     */   public static final int PreferBlanking = 1;
/*     */   public static final int DefaultBlanking = 2;
/*     */   public static final int DisableScreenSaver = 0;
/*     */   public static final int DisableScreenInterval = 0;
/*     */   public static final int DontAllowExposures = 0;
/*     */   public static final int AllowExposures = 1;
/*     */   public static final int DefaultExposures = 2;
/*     */   public static final int ScreenSaverReset = 0;
/*     */   public static final int ScreenSaverActive = 1;
/*     */   public static final int HostInsert = 0;
/*     */   public static final int HostDelete = 1;
/*     */   public static final int EnableAccess = 1;
/*     */   public static final int DisableAccess = 0;
/*     */   public static final int StaticGray = 0;
/*     */   public static final int GrayScale = 1;
/*     */   public static final int StaticColor = 2;
/*     */   public static final int PseudoColor = 3;
/*     */   public static final int TrueColor = 4;
/*     */   public static final int DirectColor = 5;
/*     */   public static final int LSBFirst = 0;
/*     */   public static final int MSBFirst = 1;
/*     */   public static final int XkbUseCoreKbd = 256;
/*     */   public static final int XkbNewKeyboardNotify = 0;
/*     */   public static final int XkbMapNotify = 1;
/*     */   public static final int XkbStateNotify = 2;
/*     */   public static final long XkbNewKeyboardNotifyMask = 1L;
/*     */   public static final long XkbMapNotifyMask = 2L;
/*     */   public static final long XkbStateNotifyMask = 4L;
/*     */   public static final long XkbGroupStateMask = 16L;
/*     */   public static final long XkbKeyTypesMask = 1L;
/*     */   public static final long XkbKeySymsMask = 2L;
/*     */   public static final long XkbModifierMapMask = 4L;
/*     */   public static final long XkbVirtualModsMask = 64L;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XConstants.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */