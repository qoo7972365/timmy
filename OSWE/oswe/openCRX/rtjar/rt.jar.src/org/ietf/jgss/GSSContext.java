package org.ietf.jgss;

import java.io.InputStream;
import java.io.OutputStream;

public interface GSSContext {
  public static final int DEFAULT_LIFETIME = 0;
  
  public static final int INDEFINITE_LIFETIME = 2147483647;
  
  byte[] initSecContext(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws GSSException;
  
  int initSecContext(InputStream paramInputStream, OutputStream paramOutputStream) throws GSSException;
  
  byte[] acceptSecContext(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws GSSException;
  
  void acceptSecContext(InputStream paramInputStream, OutputStream paramOutputStream) throws GSSException;
  
  boolean isEstablished();
  
  void dispose() throws GSSException;
  
  int getWrapSizeLimit(int paramInt1, boolean paramBoolean, int paramInt2) throws GSSException;
  
  byte[] wrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, MessageProp paramMessageProp) throws GSSException;
  
  void wrap(InputStream paramInputStream, OutputStream paramOutputStream, MessageProp paramMessageProp) throws GSSException;
  
  byte[] unwrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, MessageProp paramMessageProp) throws GSSException;
  
  void unwrap(InputStream paramInputStream, OutputStream paramOutputStream, MessageProp paramMessageProp) throws GSSException;
  
  byte[] getMIC(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, MessageProp paramMessageProp) throws GSSException;
  
  void getMIC(InputStream paramInputStream, OutputStream paramOutputStream, MessageProp paramMessageProp) throws GSSException;
  
  void verifyMIC(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4, MessageProp paramMessageProp) throws GSSException;
  
  void verifyMIC(InputStream paramInputStream1, InputStream paramInputStream2, MessageProp paramMessageProp) throws GSSException;
  
  byte[] export() throws GSSException;
  
  void requestMutualAuth(boolean paramBoolean) throws GSSException;
  
  void requestReplayDet(boolean paramBoolean) throws GSSException;
  
  void requestSequenceDet(boolean paramBoolean) throws GSSException;
  
  void requestCredDeleg(boolean paramBoolean) throws GSSException;
  
  void requestAnonymity(boolean paramBoolean) throws GSSException;
  
  void requestConf(boolean paramBoolean) throws GSSException;
  
  void requestInteg(boolean paramBoolean) throws GSSException;
  
  void requestLifetime(int paramInt) throws GSSException;
  
  void setChannelBinding(ChannelBinding paramChannelBinding) throws GSSException;
  
  boolean getCredDelegState();
  
  boolean getMutualAuthState();
  
  boolean getReplayDetState();
  
  boolean getSequenceDetState();
  
  boolean getAnonymityState();
  
  boolean isTransferable() throws GSSException;
  
  boolean isProtReady();
  
  boolean getConfState();
  
  boolean getIntegState();
  
  int getLifetime();
  
  GSSName getSrcName() throws GSSException;
  
  GSSName getTargName() throws GSSException;
  
  Oid getMech() throws GSSException;
  
  GSSCredential getDelegCred() throws GSSException;
  
  boolean isInitiator() throws GSSException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/ietf/jgss/GSSContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */