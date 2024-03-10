package net.bramp.ffmpeg.fixtures;

import com.google.common.collect.ImmutableList;
import net.bramp.ffmpeg.info.IndividualChannel;
import net.bramp.ffmpeg.info.Layout;
import net.bramp.ffmpeg.info.StandardChannelLayout;

import java.util.*;

/**
 * Class that contains all layouts as defined in the unit tests This should not be used as a concise
 * list of available layouts, as every install of ffmpeg is different. Call ffmpeg.layouts() to
 * discover.
 *
 * @author euklios
 */
public final class Layouts {

  private Layouts() {
    throw new AssertionError("No instances for you!");
  }

  private static final IndividualChannel FL = new IndividualChannel("FL", "front left");
  private static final IndividualChannel FR = new IndividualChannel("FR", "front right");
  private static final IndividualChannel FC = new IndividualChannel("FC", "front center");
  private static final IndividualChannel LFE = new IndividualChannel("LFE", "low frequency");
  private static final IndividualChannel BL = new IndividualChannel("BL", "back left");
  private static final IndividualChannel BR = new IndividualChannel("BR", "back right");
  private static final IndividualChannel FLC = new IndividualChannel("FLC", "front left-of-center");
  private static final IndividualChannel FRC = new IndividualChannel("FRC", "front right-of-center");
  private static final IndividualChannel BC = new IndividualChannel("BC", "back center");
  private static final IndividualChannel SL = new IndividualChannel("SL", "side left");
  private static final IndividualChannel SR = new IndividualChannel("SR", "side right");
  private static final IndividualChannel TC = new IndividualChannel("TC", "top center");
  private static final IndividualChannel TFL = new IndividualChannel("TFL", "top front left");
  private static final IndividualChannel TFC = new IndividualChannel("TFC", "top front center");
  private static final IndividualChannel TFR = new IndividualChannel("TFR", "top front right");
  private static final IndividualChannel TBL = new IndividualChannel("TBL", "top back left");
  private static final IndividualChannel TBC = new IndividualChannel("TBC", "top back center");
  private static final IndividualChannel TBR = new IndividualChannel("TBR", "top back right");
  private static final IndividualChannel DL = new IndividualChannel("DL", "downmix left");
  private static final IndividualChannel DR = new IndividualChannel("DR", "downmix right");
  private static final IndividualChannel WL = new IndividualChannel("WL", "wide left");
  private static final IndividualChannel WR = new IndividualChannel("WR", "wide right");
  private static final IndividualChannel SDL = new IndividualChannel("SDL", "surround direct left");
  private static final IndividualChannel SDR = new IndividualChannel("SDR", "surround direct right");
  private static final IndividualChannel LFE2 = new IndividualChannel("LFE2", "low frequency 2");
  private static final IndividualChannel TSL = new IndividualChannel("TSL", "top side left");
  private static final IndividualChannel TSR = new IndividualChannel("TSR", "top side right");
  private static final IndividualChannel BFC = new IndividualChannel("BFC", "bottom front center");
  private static final IndividualChannel BFL = new IndividualChannel("BFL", "bottom front left");
  private static final IndividualChannel BFR = new IndividualChannel("BFR", "bottom front right");

  public static final ImmutableList<Layout> LAYOUTS =
      new ImmutableList.Builder<Layout>()
          .add(
                  FL,
                  FR,
                  FC,
                  LFE,
                  BL,
                  BR,
                  FLC,
                  FRC,
                  BC,
                  SL,
                  SR,
                  TC,
                  TFL,
                  TFC,
                  TFR,
                  TBL,
                  TBC,
                  TBR,
                  DL,
                  DR,
                  WL,
                  WR,
                  SDL,
                  SDR,
                  LFE2,
                  TSL,
                  TSR,
                  BFC,
                  BFL,
                  BFR,
new StandardChannelLayout("mono", decomposition(FC)),
new StandardChannelLayout("stereo", decomposition(FL, FR)),
new StandardChannelLayout("2.1", decomposition(FL, FR, LFE)),
new StandardChannelLayout("3.0", decomposition(FL, FR, FC)),
new StandardChannelLayout("3.0(back)", decomposition(FL, FR, BC)),
new StandardChannelLayout("4.0", decomposition(FL, FR, FC, BC)),
new StandardChannelLayout("quad", decomposition(FL, FR, BL, BR)),
new StandardChannelLayout("quad(side)", decomposition(FL, FR, SL, SR)),
new StandardChannelLayout("3.1", decomposition(FL, FR, FC, LFE)),
new StandardChannelLayout("5.0", decomposition(FL, FR, FC, BL, BR)),
new StandardChannelLayout("5.0(side)", decomposition(FL, FR, FC, SL, SR)),
new StandardChannelLayout("4.1", decomposition(FL, FR, FC, LFE, BC)),
new StandardChannelLayout("5.1", decomposition(FL, FR, FC, LFE, BL, BR)),
new StandardChannelLayout("5.1(side)", decomposition(FL, FR, FC, LFE, SL, SR)),
new StandardChannelLayout("6.0", decomposition(FL, FR, FC, BC, SL, SR)),
new StandardChannelLayout("6.0(front)", decomposition(FL, FR, FLC, FRC, SL, SR)),
new StandardChannelLayout("hexagonal", decomposition(FL, FR, FC, BL, BR, BC)),
new StandardChannelLayout("6.1", decomposition(FL, FR, FC, LFE, BC, SL, SR)),
new StandardChannelLayout("6.1(back)", decomposition(FL, FR, FC, LFE, BL, BR, BC)),
new StandardChannelLayout("6.1(front)", decomposition(FL, FR, LFE, FLC, FRC, SL, SR)),
new StandardChannelLayout("7.0", decomposition(FL, FR, FC, BL, BR, SL, SR)),
new StandardChannelLayout("7.0(front)", decomposition(FL, FR, FC, FLC, FRC, SL, SR)),
new StandardChannelLayout("7.1", decomposition(FL, FR, FC, LFE, BL, BR, SL, SR)),
new StandardChannelLayout("7.1(wide)", decomposition(FL, FR, FC, LFE, BL, BR, FLC, FRC)),
new StandardChannelLayout("7.1(wide-side)", decomposition(FL, FR, FC, LFE, FLC, FRC, SL, SR)),
new StandardChannelLayout("7.1(top)", decomposition(FL, FR, FC, LFE, BL, BR, TFL, TFR)),
new StandardChannelLayout("octagonal", decomposition(FL, FR, FC, BL, BR, BC, SL, SR)),
new StandardChannelLayout("cube", decomposition(FL, FR, BL, BR, TFL, TFR, TBL, TBR)),
new StandardChannelLayout("hexadecagonal", decomposition(FL, FR, FC, BL, BR, BC, SL, SR, TFL, TFC, TFR, TBL, TBC, TBR, WL, WR)),
new StandardChannelLayout("downmix", decomposition(DL, DR)),
new StandardChannelLayout("22.2", decomposition(FL, FR, FC, LFE, BL, BR, FLC, FRC, BC, SL, SR, TC, TFL, TFC, TFR, TBL, TBC, TBR, LFE2, TSL, TSR, BFC, BFL, BFR))
          )
          .build();

  private static List<IndividualChannel> decomposition(IndividualChannel... channels) {
    List<IndividualChannel> decomposition = new ArrayList<>();

    Collections.addAll(decomposition, channels);

    return Collections.unmodifiableList(decomposition);
  }
}
