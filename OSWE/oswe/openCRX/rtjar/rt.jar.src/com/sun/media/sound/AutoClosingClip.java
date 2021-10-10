package com.sun.media.sound;

import javax.sound.sampled.Clip;

interface AutoClosingClip extends Clip {
  boolean isAutoClosing();
  
  void setAutoClosing(boolean paramBoolean);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/AutoClosingClip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */