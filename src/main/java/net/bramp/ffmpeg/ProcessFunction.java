package net.bramp.ffmpeg;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * This used to inherit from Function, but this interface allows us to throw a IOException
 * that keeps some code clean.
 *
 * @author bramp
 */
public interface ProcessFunction /*implements Function<List<String>, BufferedReader>*/ {
    BufferedReader run(List<String> args) throws IOException;
}
