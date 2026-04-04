package net.bramp.ffmpeg.adapter;

import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.bramp.ffmpeg.probe.FFmpegStream;
import org.junit.BeforeClass;
import org.junit.Test;

public class FFmpegStreamSideDataAdapterTest {
  static Gson gson;

  @BeforeClass
  public static void setGson() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(FFmpegStream.SideData.class, new FFmpegStreamSideDataAdapter());
    gson = builder.create();
  }

  @Test
  public void testNumberFormatException() {
    FFmpegStream.SideData sideData =
        gson.fromJson(
            "{\"side_data_type\": \"Display Matrix\", \"displaymatrix\": \"\n00000000:            0           0           0\n00000001:            0           0           0\n00000002:            0           0  1073741824\n\", \"rotation\": -9223372036854775808}",
            FFmpegStream.SideData.class);
    assertEquals(sideData.side_data_type, "Display Matrix");
    assertEquals(
        sideData.displaymatrix,
        "\n00000000:            0           0           0\n00000001:            0           0           0\n00000002:            0           0  1073741824\n");
    assertEquals(sideData.rotation, 0);
  }

  @Test
  public void testNormalVideo() {
    FFmpegStream.SideData sideData =
        gson.fromJson(
            "{\"side_data_type\": \"Display Matrix\", \"displaymatrix\": \"\n00000000:            0      -65536           0\n00000001:        65536           0           0\n00000002:            0           0  1073741824\n\", \"rotation\": 90}",
            FFmpegStream.SideData.class);
    assertEquals(sideData.side_data_type, "Display Matrix");
    assertEquals(
        sideData.displaymatrix,
        "\n00000000:            0      -65536           0\n00000001:        65536           0           0\n00000002:            0           0  1073741824\n");
    assertEquals(sideData.rotation, 90);
  }
}
