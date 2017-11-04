/**
 * Created by mizutahana on 10/15/17.
 */

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.filters.HighPass;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm;
import be.tarsos.dsp.effects.DelayEffect;
import be.tarsos.dsp.synthesis.NoiseGenerator;

public class FrequencyGetter {



    /**
     * Created by mizutahana on 10/2/17.
     */


    public static void main(String[] args) throws Exception {
        AudioDispatcher d = AudioDispatcherFactory.fromDefaultMicrophone(1024, 0);
        ;
//        final AudioFormat format = new AudioFormat(44100, 16, 1, true,
//                true);
//        TargetDataLine line;
//        line = (TargetDataLine) mixer.getLine(dataLineInfo);
//        line.open(format, 1024);
//        line.start();
//        final AudioInputStream stream = new AudioInputStream(line);
//
//        JVMAudioInputStream audioStream = new JVMAudioInputStream(stream);
//        try {
//            d = AudioDispatcherFactory.fromDefaultMicrophone(1024, 0);
//        } catch (LineUnavailableException e) {
//            System.out.println("nice");
//        }
        float sr = 44100.0f;

        AudioProcessor highPass = new HighPass(110, sr);
        d.addAudioProcessor(highPass);

//        class PitchDetectionHandler {
//            public void handlePitch (PitchDetectionResult pitchDetectionResult,
//                    AudioEvent audioEvent) {
//                System.out.println(pitchDetectionResult.getPitch());
//            }
//        }

        PitchDetectionHandler printPitch = new PitchDetectionHandler() {

            @Override
            public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
                System.out.println(pitchDetectionResult.getPitch());
            }
        };


//        PitchDetectionHandler printPitch = new PitchDetectionHandler() {
//            public void handlePitch (PitchDetectionResult pitchDetectionResult,
//                                     AudioEvent audioEvent) {
//                System.out.println(pitchDetectionResult.getPitch());
//            }
//        }

        PitchEstimationAlgorithm algo = PitchProcessor.PitchEstimationAlgorithm.YIN;
        AudioProcessor pitchEstimator = new PitchProcessor(algo, 44100, 1024,
                printPitch);
        d.addAudioProcessor(pitchEstimator);
        d.addAudioProcessor(new DelayEffect(0.5, 0.3, 44100));
        d.addAudioProcessor(new NoiseGenerator(.3));
//        d.addAudioProcessor(new AudioPlayer(new AudioFormat(sr, 16, 1, true,
//                true)));
        d.run();
    }


}
