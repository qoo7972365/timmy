package com.sun.media.sound;

public interface ModelOscillator {
  int getChannels();
  
  float getAttenuation();
  
  ModelOscillatorStream open(float paramFloat);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/ModelOscillator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */