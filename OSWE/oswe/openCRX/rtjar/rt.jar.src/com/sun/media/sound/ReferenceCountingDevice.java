package com.sun.media.sound;

import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

public interface ReferenceCountingDevice {
  Receiver getReceiverReferenceCounting() throws MidiUnavailableException;
  
  Transmitter getTransmitterReferenceCounting() throws MidiUnavailableException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/ReferenceCountingDevice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */