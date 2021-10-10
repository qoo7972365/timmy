/*      */ package java.awt.color;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.StringTokenizer;
/*      */ import sun.java2d.cmm.CMSManager;
/*      */ import sun.java2d.cmm.PCMM;
/*      */ import sun.java2d.cmm.Profile;
/*      */ import sun.java2d.cmm.ProfileActivator;
/*      */ import sun.java2d.cmm.ProfileDataVerifier;
/*      */ import sun.java2d.cmm.ProfileDeferralInfo;
/*      */ import sun.java2d.cmm.ProfileDeferralMgr;
/*      */ import sun.misc.IOUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ICC_Profile
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -3938515861990936766L;
/*      */   private transient Profile cmmProfile;
/*      */   private transient ProfileDeferralInfo deferralInfo;
/*      */   private transient ProfileActivator profileActivator;
/*      */   private static ICC_Profile sRGBprofile;
/*      */   private static ICC_Profile XYZprofile;
/*      */   private static ICC_Profile PYCCprofile;
/*      */   private static ICC_Profile GRAYprofile;
/*      */   private static ICC_Profile LINEAR_RGBprofile;
/*      */   public static final int CLASS_INPUT = 0;
/*      */   public static final int CLASS_DISPLAY = 1;
/*      */   public static final int CLASS_OUTPUT = 2;
/*      */   public static final int CLASS_DEVICELINK = 3;
/*      */   public static final int CLASS_COLORSPACECONVERSION = 4;
/*      */   public static final int CLASS_ABSTRACT = 5;
/*      */   public static final int CLASS_NAMEDCOLOR = 6;
/*      */   public static final int icSigXYZData = 1482250784;
/*      */   public static final int icSigLabData = 1281450528;
/*      */   public static final int icSigLuvData = 1282766368;
/*      */   public static final int icSigYCbCrData = 1497588338;
/*      */   public static final int icSigYxyData = 1501067552;
/*      */   public static final int icSigRgbData = 1380401696;
/*      */   public static final int icSigGrayData = 1196573017;
/*      */   public static final int icSigHsvData = 1213421088;
/*      */   public static final int icSigHlsData = 1212961568;
/*      */   public static final int icSigCmykData = 1129142603;
/*      */   public static final int icSigCmyData = 1129142560;
/*      */   public static final int icSigSpace2CLR = 843271250;
/*      */   public static final int icSigSpace3CLR = 860048466;
/*      */   public static final int icSigSpace4CLR = 876825682;
/*      */   public static final int icSigSpace5CLR = 893602898;
/*      */   public static final int icSigSpace6CLR = 910380114;
/*      */   public static final int icSigSpace7CLR = 927157330;
/*      */   public static final int icSigSpace8CLR = 943934546;
/*      */   public static final int icSigSpace9CLR = 960711762;
/*      */   public static final int icSigSpaceACLR = 1094929490;
/*      */   public static final int icSigSpaceBCLR = 1111706706;
/*      */   public static final int icSigSpaceCCLR = 1128483922;
/*      */   public static final int icSigSpaceDCLR = 1145261138;
/*      */   public static final int icSigSpaceECLR = 1162038354;
/*      */   public static final int icSigSpaceFCLR = 1178815570;
/*      */   public static final int icSigInputClass = 1935896178;
/*      */   public static final int icSigDisplayClass = 1835955314;
/*      */   public static final int icSigOutputClass = 1886549106;
/*      */   public static final int icSigLinkClass = 1818848875;
/*      */   public static final int icSigAbstractClass = 1633842036;
/*      */   public static final int icSigColorSpaceClass = 1936744803;
/*      */   public static final int icSigNamedColorClass = 1852662636;
/*      */   public static final int icPerceptual = 0;
/*      */   public static final int icRelativeColorimetric = 1;
/*      */   public static final int icMediaRelativeColorimetric = 1;
/*      */   public static final int icSaturation = 2;
/*      */   public static final int icAbsoluteColorimetric = 3;
/*      */   public static final int icICCAbsoluteColorimetric = 3;
/*      */   public static final int icSigHead = 1751474532;
/*      */   public static final int icSigAToB0Tag = 1093812784;
/*      */   public static final int icSigAToB1Tag = 1093812785;
/*      */   public static final int icSigAToB2Tag = 1093812786;
/*      */   public static final int icSigBlueColorantTag = 1649957210;
/*      */   public static final int icSigBlueMatrixColumnTag = 1649957210;
/*      */   public static final int icSigBlueTRCTag = 1649693251;
/*      */   public static final int icSigBToA0Tag = 1110589744;
/*      */   public static final int icSigBToA1Tag = 1110589745;
/*      */   public static final int icSigBToA2Tag = 1110589746;
/*      */   public static final int icSigCalibrationDateTimeTag = 1667329140;
/*      */   public static final int icSigCharTargetTag = 1952543335;
/*      */   public static final int icSigCopyrightTag = 1668313716;
/*      */   public static final int icSigCrdInfoTag = 1668441193;
/*      */   public static final int icSigDeviceMfgDescTag = 1684893284;
/*      */   public static final int icSigDeviceModelDescTag = 1684890724;
/*      */   public static final int icSigDeviceSettingsTag = 1684371059;
/*      */   public static final int icSigGamutTag = 1734438260;
/*      */   public static final int icSigGrayTRCTag = 1800688195;
/*      */   public static final int icSigGreenColorantTag = 1733843290;
/*      */   public static final int icSigGreenMatrixColumnTag = 1733843290;
/*      */   public static final int icSigGreenTRCTag = 1733579331;
/*      */   public static final int icSigLuminanceTag = 1819635049;
/*      */   public static final int icSigMeasurementTag = 1835360627;
/*      */   public static final int icSigMediaBlackPointTag = 1651208308;
/*      */   public static final int icSigMediaWhitePointTag = 2004119668;
/*      */   public static final int icSigNamedColor2Tag = 1852009522;
/*      */   public static final int icSigOutputResponseTag = 1919251312;
/*      */   public static final int icSigPreview0Tag = 1886545200;
/*      */   public static final int icSigPreview1Tag = 1886545201;
/*      */   public static final int icSigPreview2Tag = 1886545202;
/*      */   public static final int icSigProfileDescriptionTag = 1684370275;
/*      */   public static final int icSigProfileSequenceDescTag = 1886610801;
/*      */   public static final int icSigPs2CRD0Tag = 1886610480;
/*      */   public static final int icSigPs2CRD1Tag = 1886610481;
/*      */   public static final int icSigPs2CRD2Tag = 1886610482;
/*      */   public static final int icSigPs2CRD3Tag = 1886610483;
/*      */   public static final int icSigPs2CSATag = 1886597747;
/*      */   public static final int icSigPs2RenderingIntentTag = 1886597737;
/*      */   public static final int icSigRedColorantTag = 1918392666;
/*      */   public static final int icSigRedMatrixColumnTag = 1918392666;
/*      */   public static final int icSigRedTRCTag = 1918128707;
/*      */   public static final int icSigScreeningDescTag = 1935897188;
/*      */   public static final int icSigScreeningTag = 1935897198;
/*      */   public static final int icSigTechnologyTag = 1952801640;
/*      */   public static final int icSigUcrBgTag = 1650877472;
/*      */   public static final int icSigViewingCondDescTag = 1987405156;
/*      */   public static final int icSigViewingConditionsTag = 1986618743;
/*      */   public static final int icSigChromaticityTag = 1667789421;
/*      */   public static final int icSigChromaticAdaptationTag = 1667785060;
/*      */   public static final int icSigColorantOrderTag = 1668051567;
/*      */   public static final int icSigColorantTableTag = 1668051572;
/*      */   public static final int icHdrSize = 0;
/*      */   public static final int icHdrCmmId = 4;
/*      */   public static final int icHdrVersion = 8;
/*      */   public static final int icHdrDeviceClass = 12;
/*      */   public static final int icHdrColorSpace = 16;
/*      */   public static final int icHdrPcs = 20;
/*      */   public static final int icHdrDate = 24;
/*      */   public static final int icHdrMagic = 36;
/*      */   public static final int icHdrPlatform = 40;
/*      */   public static final int icHdrFlags = 44;
/*      */   public static final int icHdrManufacturer = 48;
/*      */   public static final int icHdrModel = 52;
/*      */   public static final int icHdrAttributes = 56;
/*      */   public static final int icHdrRenderingIntent = 64;
/*      */   public static final int icHdrIlluminant = 68;
/*      */   public static final int icHdrCreator = 80;
/*      */   public static final int icHdrProfileID = 84;
/*      */   public static final int icTagType = 0;
/*      */   public static final int icTagReserved = 4;
/*      */   public static final int icCurveCount = 8;
/*      */   public static final int icCurveData = 12;
/*      */   public static final int icXYZNumberX = 8;
/*      */   private int iccProfileSerializedDataVersion;
/*      */   private transient ICC_Profile resolvedDeserializedProfile;
/*      */   
/*      */   ICC_Profile(Profile paramProfile) {
/* 1927 */     this.iccProfileSerializedDataVersion = 1; this.cmmProfile = paramProfile; } protected void finalize() { if (this.cmmProfile != null) { CMSManager.getModule().freeProfile(this.cmmProfile); } else if (this.profileActivator != null) { ProfileDeferralMgr.unregisterDeferral(this.profileActivator); }  } public static ICC_Profile getInstance(byte[] paramArrayOfbyte) { ICC_Profile iCC_Profile; Profile profile = null; if (ProfileDeferralMgr.deferring) ProfileDeferralMgr.activateProfiles();  ProfileDataVerifier.verify(paramArrayOfbyte); try { profile = CMSManager.getModule().loadProfile(paramArrayOfbyte); } catch (CMMException cMMException) { throw new IllegalArgumentException("Invalid ICC Profile Data"); }  try { if (getColorSpaceType(profile) == 6 && getData(profile, 2004119668) != null && getData(profile, 1800688195) != null) { iCC_Profile = new ICC_ProfileGray(profile); } else if (getColorSpaceType(profile) == 5 && getData(profile, 2004119668) != null && getData(profile, 1918392666) != null && getData(profile, 1733843290) != null && getData(profile, 1649957210) != null && getData(profile, 1918128707) != null && getData(profile, 1733579331) != null && getData(profile, 1649693251) != null) { iCC_Profile = new ICC_ProfileRGB(profile); } else { iCC_Profile = new ICC_Profile(profile); }  } catch (CMMException cMMException) { iCC_Profile = new ICC_Profile(profile); }  return iCC_Profile; } public static ICC_Profile getInstance(int paramInt) { ICC_Profile iCC_Profile = null; switch (paramInt) { case 1000: synchronized (ICC_Profile.class) { if (sRGBprofile == null) { ProfileDeferralInfo profileDeferralInfo = new ProfileDeferralInfo("sRGB.pf", 5, 3, 1); sRGBprofile = getDeferredInstance(profileDeferralInfo); }  iCC_Profile = sRGBprofile; }  return iCC_Profile;case 1001: synchronized (ICC_Profile.class) { if (XYZprofile == null) { ProfileDeferralInfo profileDeferralInfo = new ProfileDeferralInfo("CIEXYZ.pf", 0, 3, 1); XYZprofile = getDeferredInstance(profileDeferralInfo); }  iCC_Profile = XYZprofile; }  return iCC_Profile;case 1002: synchronized (ICC_Profile.class) { if (PYCCprofile == null) if (standardProfileExists("PYCC.pf")) { ProfileDeferralInfo profileDeferralInfo = new ProfileDeferralInfo("PYCC.pf", 13, 3, 1); PYCCprofile = getDeferredInstance(profileDeferralInfo); } else { throw new IllegalArgumentException("Can't load standard profile: PYCC.pf"); }   iCC_Profile = PYCCprofile; }  return iCC_Profile;case 1003: synchronized (ICC_Profile.class) { if (GRAYprofile == null) { ProfileDeferralInfo profileDeferralInfo = new ProfileDeferralInfo("GRAY.pf", 6, 1, 1); GRAYprofile = getDeferredInstance(profileDeferralInfo); }  iCC_Profile = GRAYprofile; }  return iCC_Profile;case 1004: synchronized (ICC_Profile.class) { if (LINEAR_RGBprofile == null) { ProfileDeferralInfo profileDeferralInfo = new ProfileDeferralInfo("LINEAR_RGB.pf", 5, 3, 1); LINEAR_RGBprofile = getDeferredInstance(profileDeferralInfo); }  iCC_Profile = LINEAR_RGBprofile; }  return iCC_Profile; }  throw new IllegalArgumentException("Unknown color space"); } private static ICC_Profile getStandardProfile(final String name) { return AccessController.<ICC_Profile>doPrivileged(new PrivilegedAction<ICC_Profile>() { public ICC_Profile run() { ICC_Profile iCC_Profile = null; try { iCC_Profile = ICC_Profile.getInstance(name); } catch (IOException iOException) { throw new IllegalArgumentException("Can't load standard profile: " + name); }  return iCC_Profile; } }); } public static ICC_Profile getInstance(String paramString) throws IOException { FileInputStream fileInputStream = null; File file = getProfileFile(paramString); if (file != null) fileInputStream = new FileInputStream(file);  if (fileInputStream == null) throw new IOException("Cannot open file " + paramString);  ICC_Profile iCC_Profile = getInstance(fileInputStream); fileInputStream.close(); return iCC_Profile; } public static ICC_Profile getInstance(InputStream paramInputStream) throws IOException { if (paramInputStream instanceof ProfileDeferralInfo) return getDeferredInstance((ProfileDeferralInfo)paramInputStream);  byte[] arrayOfByte; if ((arrayOfByte = getProfileDataFromStream(paramInputStream)) == null) throw new IllegalArgumentException("Invalid ICC Profile Data");  return getInstance(arrayOfByte); } static byte[] getProfileDataFromStream(InputStream paramInputStream) throws IOException { BufferedInputStream bufferedInputStream = new BufferedInputStream(paramInputStream); bufferedInputStream.mark(128); byte[] arrayOfByte = IOUtils.readNBytes(bufferedInputStream, 128); if (arrayOfByte[36] != 97 || arrayOfByte[37] != 99 || arrayOfByte[38] != 115 || arrayOfByte[39] != 112) return null;  int i = (arrayOfByte[0] & 0xFF) << 24 | (arrayOfByte[1] & 0xFF) << 16 | (arrayOfByte[2] & 0xFF) << 8 | arrayOfByte[3] & 0xFF; bufferedInputStream.reset(); try { return IOUtils.readNBytes(bufferedInputStream, i); } catch (OutOfMemoryError outOfMemoryError) { throw new IOException("Color profile is too big"); }  } static ICC_Profile getDeferredInstance(ProfileDeferralInfo paramProfileDeferralInfo) { if (!ProfileDeferralMgr.deferring) return getStandardProfile(paramProfileDeferralInfo.filename);  if (paramProfileDeferralInfo.colorSpaceType == 5) return new ICC_ProfileRGB(paramProfileDeferralInfo);  if (paramProfileDeferralInfo.colorSpaceType == 6) return new ICC_ProfileGray(paramProfileDeferralInfo);  return new ICC_Profile(paramProfileDeferralInfo); } void activateDeferredProfile() throws ProfileDataException { byte[] arrayOfByte; final String fileName = this.deferralInfo.filename; this.profileActivator = null; this.deferralInfo = null; PrivilegedAction<FileInputStream> privilegedAction = new PrivilegedAction<FileInputStream>() { public FileInputStream run() { File file = ICC_Profile.getStandardProfileFile(fileName); if (file != null) try { return new FileInputStream(file); } catch (FileNotFoundException fileNotFoundException) {}  return null; } }; FileInputStream fileInputStream; if ((fileInputStream = AccessController.doPrivileged(privilegedAction)) == null) throw new ProfileDataException("Cannot open file " + str);  try { arrayOfByte = getProfileDataFromStream(fileInputStream); fileInputStream.close(); } catch (IOException iOException) { ProfileDataException profileDataException = new ProfileDataException("Invalid ICC Profile Data" + str); profileDataException.initCause(iOException); throw profileDataException; }  if (arrayOfByte == null) throw new ProfileDataException("Invalid ICC Profile Data" + str);  try { this.cmmProfile = CMSManager.getModule().loadProfile(arrayOfByte); } catch (CMMException cMMException) { ProfileDataException profileDataException = new ProfileDataException("Invalid ICC Profile Data" + str); profileDataException.initCause(cMMException); throw profileDataException; }  } public int getMajorVersion() { byte[] arrayOfByte = getData(1751474532); return arrayOfByte[8]; } ICC_Profile(ProfileDeferralInfo paramProfileDeferralInfo) { this.iccProfileSerializedDataVersion = 1; this.deferralInfo = paramProfileDeferralInfo; this.profileActivator = new ProfileActivator() {
/*      */         public void activate() throws ProfileDataException { ICC_Profile.this.activateDeferredProfile(); }
/*      */       }; ProfileDeferralMgr.registerDeferral(this.profileActivator); }
/*      */   public int getMinorVersion() { byte[] arrayOfByte = getData(1751474532); return arrayOfByte[9]; }
/*      */   public int getProfileClass() { byte b; if (this.deferralInfo != null)
/*      */       return this.deferralInfo.profileClass;  byte[] arrayOfByte = getData(1751474532); int i = intFromBigEndian(arrayOfByte, 12); switch (i) {
/*      */       case 1935896178:
/*      */         b = 0; return b;
/*      */       case 1835955314:
/*      */         b = 1; return b;
/*      */       case 1886549106:
/*      */         b = 2; return b;
/*      */       case 1818848875:
/*      */         b = 3; return b;
/*      */       case 1936744803:
/*      */         b = 4; return b;
/*      */       case 1633842036:
/*      */         b = 5; return b;
/*      */       case 1852662636:
/*      */         b = 6; return b;
/*      */     }  throw new IllegalArgumentException("Unknown profile class"); }
/*      */   public int getColorSpaceType() { if (this.deferralInfo != null)
/*      */       return this.deferralInfo.colorSpaceType;  return getColorSpaceType(this.cmmProfile); }
/*      */   static int getColorSpaceType(Profile paramProfile) { byte[] arrayOfByte = getData(paramProfile, 1751474532); int i = intFromBigEndian(arrayOfByte, 16); return iccCStoJCS(i); }
/*      */   public int getPCSType() { if (ProfileDeferralMgr.deferring)
/*      */       ProfileDeferralMgr.activateProfiles();  return getPCSType(this.cmmProfile); }
/*      */   static int getPCSType(Profile paramProfile) { byte[] arrayOfByte = getData(paramProfile, 1751474532); int i = intFromBigEndian(arrayOfByte, 20); return iccCStoJCS(i); }
/*      */   public void write(String paramString) throws IOException { byte[] arrayOfByte = getData(); FileOutputStream fileOutputStream = new FileOutputStream(paramString); fileOutputStream.write(arrayOfByte); fileOutputStream.close(); }
/*      */   public void write(OutputStream paramOutputStream) throws IOException { byte[] arrayOfByte = getData(); paramOutputStream.write(arrayOfByte); } public byte[] getData() { if (ProfileDeferralMgr.deferring)
/*      */       ProfileDeferralMgr.activateProfiles();  PCMM pCMM = CMSManager.getModule(); int i = pCMM.getProfileSize(this.cmmProfile); byte[] arrayOfByte = new byte[i]; pCMM.getProfileData(this.cmmProfile, arrayOfByte);
/* 1957 */     return arrayOfByte; } private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException { paramObjectOutputStream.defaultWriteObject();
/*      */     
/* 1959 */     String str = null;
/* 1960 */     if (this == sRGBprofile) {
/* 1961 */       str = "CS_sRGB";
/* 1962 */     } else if (this == XYZprofile) {
/* 1963 */       str = "CS_CIEXYZ";
/* 1964 */     } else if (this == PYCCprofile) {
/* 1965 */       str = "CS_PYCC";
/* 1966 */     } else if (this == GRAYprofile) {
/* 1967 */       str = "CS_GRAY";
/* 1968 */     } else if (this == LINEAR_RGBprofile) {
/* 1969 */       str = "CS_LINEAR_RGB";
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1976 */     byte[] arrayOfByte = null;
/* 1977 */     if (str == null)
/*      */     {
/* 1979 */       arrayOfByte = getData();
/*      */     }
/*      */     
/* 1982 */     paramObjectOutputStream.writeObject(str);
/* 1983 */     paramObjectOutputStream.writeObject(arrayOfByte); } public byte[] getData(int paramInt) { if (ProfileDeferralMgr.deferring)
/*      */       ProfileDeferralMgr.activateProfiles();  return getData(this.cmmProfile, paramInt); }
/*      */   static byte[] getData(Profile paramProfile, int paramInt) { byte[] arrayOfByte; try { PCMM pCMM = CMSManager.getModule(); int i = pCMM.getTagSize(paramProfile, paramInt); arrayOfByte = new byte[i]; pCMM.getTagData(paramProfile, paramInt, arrayOfByte); } catch (CMMException cMMException) { arrayOfByte = null; }  return arrayOfByte; }
/*      */   public void setData(int paramInt, byte[] paramArrayOfbyte) { if (ProfileDeferralMgr.deferring)
/*      */       ProfileDeferralMgr.activateProfiles();  CMSManager.getModule().setTagData(this.cmmProfile, paramInt, paramArrayOfbyte); }
/*      */   void setRenderingIntent(int paramInt) { byte[] arrayOfByte = getData(1751474532); intToBigEndian(paramInt, arrayOfByte, 64); setData(1751474532, arrayOfByte); }
/*      */   int getRenderingIntent() { byte[] arrayOfByte = getData(1751474532); int i = intFromBigEndian(arrayOfByte, 64); return 0xFFFF & i; }
/*      */   public int getNumComponents() { byte b; if (this.deferralInfo != null)
/*      */       return this.deferralInfo.numComponents;  byte[] arrayOfByte = getData(1751474532); int i = intFromBigEndian(arrayOfByte, 16); switch (i) { case 1196573017: b = 1; return b;
/*      */       case 843271250: b = 2; return b;
/*      */       case 860048466: case 1129142560: case 1212961568: case 1213421088: case 1281450528: case 1282766368: case 1380401696: case 1482250784: case 1497588338:
/*      */       case 1501067552:
/*      */         b = 3; return b;
/*      */       case 876825682:
/*      */       case 1129142603:
/*      */         b = 4; return b;
/*      */       case 893602898:
/*      */         b = 5; return b;
/*      */       case 910380114:
/*      */         b = 6; return b;
/*      */       case 927157330:
/*      */         b = 7; return b;
/*      */       case 943934546:
/*      */         b = 8; return b;
/*      */       case 960711762:
/*      */         b = 9; return b;
/*      */       case 1094929490:
/*      */         b = 10; return b;
/*      */       case 1111706706:
/*      */         b = 11; return b;
/*      */       case 1128483922:
/*      */         b = 12; return b;
/*      */       case 1145261138:
/*      */         b = 13; return b;
/*      */       case 1162038354:
/*      */         b = 14; return b;
/*      */       case 1178815570:
/*      */         b = 15; return b; }  throw new ProfileDataException("invalid ICC color space"); }
/*      */   float[] getMediaWhitePoint() { return getXYZTag(2004119668); }
/*      */   float[] getXYZTag(int paramInt) { byte[] arrayOfByte = getData(paramInt); float[] arrayOfFloat = new float[3]; for (byte b1 = 0, b2 = 8; b1 < 3; b1++, b2 += 4) { int i = intFromBigEndian(arrayOfByte, b2); arrayOfFloat[b1] = i / 65536.0F; }  return arrayOfFloat; }
/*      */   float getGamma(int paramInt) { byte[] arrayOfByte = getData(paramInt); if (intFromBigEndian(arrayOfByte, 8) != 1)
/*      */       throw new ProfileDataException("TRC is not a gamma");  int i = shortFromBigEndian(arrayOfByte, 12) & 0xFFFF; return i / 256.0F; }
/* 2025 */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException { paramObjectInputStream.defaultReadObject();
/*      */     
/* 2027 */     String str = (String)paramObjectInputStream.readObject();
/* 2028 */     byte[] arrayOfByte = (byte[])paramObjectInputStream.readObject();
/*      */     
/* 2030 */     char c = Character.MIN_VALUE;
/* 2031 */     boolean bool = false;
/* 2032 */     if (str != null) {
/* 2033 */       bool = true;
/* 2034 */       if (str.equals("CS_sRGB")) {
/* 2035 */         c = 'Ϩ';
/* 2036 */       } else if (str.equals("CS_CIEXYZ")) {
/* 2037 */         c = 'ϩ';
/* 2038 */       } else if (str.equals("CS_PYCC")) {
/* 2039 */         c = 'Ϫ';
/* 2040 */       } else if (str.equals("CS_GRAY")) {
/* 2041 */         c = 'ϫ';
/* 2042 */       } else if (str.equals("CS_LINEAR_RGB")) {
/* 2043 */         c = 'Ϭ';
/*      */       } else {
/* 2045 */         bool = false;
/*      */       } 
/*      */     } 
/*      */     
/* 2049 */     if (bool)
/* 2050 */     { this.resolvedDeserializedProfile = getInstance(c); }
/*      */     else
/* 2052 */     { this.resolvedDeserializedProfile = getInstance(arrayOfByte); }  } short[] getTRC(int paramInt) { byte[] arrayOfByte = getData(paramInt); int i = intFromBigEndian(arrayOfByte, 8); if (i == 1) throw new ProfileDataException("TRC is not a table");  short[] arrayOfShort = new short[i]; for (byte b1 = 0, b2 = 12; b1 < i; b1++, b2 += 2) arrayOfShort[b1] = shortFromBigEndian(arrayOfByte, b2);  return arrayOfShort; }
/*      */   static int iccCStoJCS(int paramInt) { byte b; switch (paramInt) { case 1482250784: b = 0; return b;case 1281450528: b = 1; return b;case 1282766368: b = 2; return b;case 1497588338: b = 3; return b;case 1501067552: b = 4; return b;case 1380401696: b = 5; return b;case 1196573017: b = 6; return b;case 1213421088: b = 7; return b;case 1212961568: b = 8; return b;case 1129142603: b = 9; return b;case 1129142560: b = 11; return b;case 843271250: b = 12; return b;case 860048466: b = 13; return b;case 876825682: b = 14; return b;case 893602898: b = 15; return b;case 910380114: b = 16; return b;case 927157330: b = 17; return b;case 943934546: b = 18; return b;case 960711762: b = 19; return b;case 1094929490: b = 20; return b;case 1111706706: b = 21; return b;case 1128483922: b = 22; return b;case 1145261138: b = 23; return b;case 1162038354: b = 24; return b;case 1178815570: b = 25; return b; }  throw new IllegalArgumentException("Unknown color space"); }
/*      */   static int intFromBigEndian(byte[] paramArrayOfbyte, int paramInt) { return (paramArrayOfbyte[paramInt] & 0xFF) << 24 | (paramArrayOfbyte[paramInt + 1] & 0xFF) << 16 | (paramArrayOfbyte[paramInt + 2] & 0xFF) << 8 | paramArrayOfbyte[paramInt + 3] & 0xFF; }
/*      */   static void intToBigEndian(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) { paramArrayOfbyte[paramInt2] = (byte)(paramInt1 >> 24); paramArrayOfbyte[paramInt2 + 1] = (byte)(paramInt1 >> 16); paramArrayOfbyte[paramInt2 + 2] = (byte)(paramInt1 >> 8); paramArrayOfbyte[paramInt2 + 3] = (byte)paramInt1; }
/*      */   static short shortFromBigEndian(byte[] paramArrayOfbyte, int paramInt) { return (short)((paramArrayOfbyte[paramInt] & 0xFF) << 8 | paramArrayOfbyte[paramInt + 1] & 0xFF); }
/*      */   static void shortToBigEndian(short paramShort, byte[] paramArrayOfbyte, int paramInt) { paramArrayOfbyte[paramInt] = (byte)(paramShort >> 8); paramArrayOfbyte[paramInt + 1] = (byte)paramShort; }
/*      */   private static File getProfileFile(String paramString) { File file = new File(paramString); if (file.isAbsolute()) return file.isFile() ? file : null;  String str; if (!file.isFile() && (str = System.getProperty("java.iccprofile.path")) != null) { StringTokenizer stringTokenizer = new StringTokenizer(str, File.pathSeparator); while (stringTokenizer.hasMoreTokens() && (file == null || !file.isFile())) { String str1 = stringTokenizer.nextToken(); String str2 = str1 + File.separatorChar + paramString; file = new File(str2); }  }  if ((file == null || !file.isFile()) && (str = System.getProperty("java.class.path")) != null) { StringTokenizer stringTokenizer = new StringTokenizer(str, File.pathSeparator); while (stringTokenizer.hasMoreTokens() && (file == null || !file.isFile())) { String str1 = stringTokenizer.nextToken(); String str2 = str1 + File.separatorChar + paramString; file = new File(str2); }  }  if (file == null || !file.isFile()) file = getStandardProfileFile(paramString);  if (file != null && file.isFile())
/*      */       return file;  return null; }
/*      */   private static File getStandardProfileFile(String paramString) { String str1 = System.getProperty("java.home") + File.separatorChar + "lib" + File.separatorChar + "cmm"; String str2 = str1 + File.separatorChar + paramString; File file = new File(str2); return file.isFile() ? file : null; }
/*      */   private static boolean isChildOf(File paramFile, String paramString) { try { File file = new File(paramString); String str1 = file.getCanonicalPath(); if (!str1.endsWith(File.separator))
/*      */         str1 = str1 + File.separator;  String str2 = paramFile.getCanonicalPath(); return str2.startsWith(str1); } catch (IOException iOException) { return false; }  }
/*      */   private static boolean standardProfileExists(final String fileName) { return ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() { public Boolean run() { return Boolean.valueOf((ICC_Profile.getStandardProfileFile(fileName) != null)); } }
/*      */       )).booleanValue(); }
/* 2065 */   protected Object readResolve() throws ObjectStreamException { return this.resolvedDeserializedProfile; }
/*      */ 
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/color/ICC_Profile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */