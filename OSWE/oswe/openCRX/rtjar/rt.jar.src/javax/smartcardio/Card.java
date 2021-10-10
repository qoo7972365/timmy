package javax.smartcardio;

public abstract class Card {
  public abstract ATR getATR();
  
  public abstract String getProtocol();
  
  public abstract CardChannel getBasicChannel();
  
  public abstract CardChannel openLogicalChannel() throws CardException;
  
  public abstract void beginExclusive() throws CardException;
  
  public abstract void endExclusive() throws CardException;
  
  public abstract byte[] transmitControlCommand(int paramInt, byte[] paramArrayOfbyte) throws CardException;
  
  public abstract void disconnect(boolean paramBoolean) throws CardException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/smartcardio/Card.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */