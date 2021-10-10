package javax.sound.midi;

import java.util.EventListener;

public interface ControllerEventListener extends EventListener {
  void controlChange(ShortMessage paramShortMessage);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sound/midi/ControllerEventListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */