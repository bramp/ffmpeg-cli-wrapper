package net.bramp.ffmpeg.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.JsonAdapter;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class BooleanTypeAdapterTest {
    static class Set {
        @JsonAdapter(BooleanTypeAdapter.class)
        public boolean a;
    }

    static Gson gson;

    @BeforeClass
    public static void setupGson() {
        gson = new GsonBuilder().create();
    }

    @Test
    public void testReadTrue() {
        Set s  = gson.fromJson("{\"a\":true}", Set.class);
        assertTrue(s.a);
    }

    @Test
    public void testReadFalse() {
        Set s  = gson.fromJson("{\"a\":false}", Set.class);
        assertFalse(s.a);
    }

    @Test
    public void testRead1() {
        Set s  = gson.fromJson("{\"a\":1}", Set.class);
        assertTrue(s.a);
    }

    @Test
    public void testRead0() {
        Set s  = gson.fromJson("{\"a\":0}", Set.class);
        assertFalse(s.a);
    }

    @Test
    public void testReadNull() {
        Set s = gson.fromJson("{\"a\":null}", Set.class);
        assertFalse(s.a);
    }
}
