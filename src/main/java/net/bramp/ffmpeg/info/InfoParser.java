package net.bramp.ffmpeg.info;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public final class InfoParser {
    private InfoParser() {
        throw new AssertionError("No instances for you!");
    }

    public static List<ChannelLayout> parseLayouts(BufferedReader r) throws IOException {
        Map<String, IndividualChannel> individualChannelLookup = new HashMap<>();
        List<ChannelLayout> channelLayouts = new ArrayList<>();

        String line;
        boolean parsingIndividualChannels = false;
        boolean parsingChannelLayouts = false;

        while ((line = r.readLine()) != null) {
            if (line.startsWith("NAME") || line.isEmpty()) {
                // Skip header and empty lines
                continue;
            } else if (line.equals("Individual channels:")) {
                parsingIndividualChannels = true;
                parsingChannelLayouts = false;
            } else if (line.equals("Standard channel layouts:")) {
                parsingIndividualChannels = false;
                parsingChannelLayouts = true;
            } else if (parsingIndividualChannels) {
                String[] s = line.split(" ", 2);
                IndividualChannel individualChannel = new IndividualChannel(s[0], s[1].trim());
                channelLayouts.add(individualChannel);
                individualChannelLookup.put(individualChannel.getName(), individualChannel);
            } else if (parsingChannelLayouts) {
                String[] s = line.split(" ", 2);
                List<IndividualChannel> decomposition = new ArrayList<>();
                for (String channelName : s[1].trim().split("\\+")) {
                    decomposition.add(individualChannelLookup.get(channelName));
                }

                channelLayouts.add(new StandardChannelLayout(s[0], Collections.unmodifiableList(decomposition)));
            }
        }

        return channelLayouts;
    }
}
