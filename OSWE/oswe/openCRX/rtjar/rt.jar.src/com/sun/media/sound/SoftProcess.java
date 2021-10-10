package com.sun.media.sound;

public interface SoftProcess extends SoftControl {
  void init(SoftSynthesizer paramSoftSynthesizer);
  
  double[] get(int paramInt, String paramString);
  
  void processControlLogic();
  
  void reset();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftProcess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */