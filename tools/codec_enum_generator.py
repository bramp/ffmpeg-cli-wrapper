import subprocess
import re

CODECS_REGEX = re.compile("^ ([.D][.E][VASD][.I][.L][.S]) (\S{2,})\s+(.*)$")

def removeLeadingNumbers(text):
    index = 0
    while index < len(text) and text[index].isdigit():
        index += 1
    return text[index:]

def writeCodec(m,codec):
    document = "\t"+"/**"+ m.group(3).rstrip()  +"*/\n"
    enumCode = "\t" +"public static final String " +removeLeadingNumbers(m.group(2).replace(".","_")).upper() +' = "'+  m.group(2) +'";' +'\n'
    codec.write(document)
    codec.write(enumCode)


output = subprocess.check_output("ffmpeg -codecs", shell=True).decode('utf-8')

print(output) 

output = output.split("\n")

videoCodec = open("VideoCodec.java", 'w')
audioCodec = open("AudioCodec.java", 'w')

# write header
videoCodec.write(
"""package net.bramp.ffmpeg.builder;
/**The available codecs may vary depending on the version of FFmpeg.
 * <br>
 *  you can get a list of available codecs through use {@link net.bramp.ffmpeg.FFmpeg#codecs()}.
 *
 *  @see net.bramp.ffmpeg.FFmpeg#codecs()
 *  @author van1164
 *  */
public class VideoCodec {

""")
audioCodec.write(
"""package net.bramp.ffmpeg.builder;
/**The available codecs may vary depending on the version of FFmpeg.
 * <br>
 *  you can get a list of available codecs through use {@link net.bramp.ffmpeg.FFmpeg#codecs()}.
 *
 *  @see net.bramp.ffmpeg.FFmpeg#codecs()
 *  @author van1164
 *  */
public class AudioCodec {

""")
for item in output:
    m = CODECS_REGEX.match(item)
    if not m : continue

    lst = item.split()
    if 'A' in m.group(1):
        writeCodec(m,audioCodec)

    if 'V' in m.group(1):
        writeCodec(m,videoCodec)


videoCodec.write("}")
audioCodec.write("}")

videoCodec.close()
audioCodec.close()
