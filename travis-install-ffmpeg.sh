#!/bin/bash
# Small script to fetch a static ffmpeg
set -ex

URL=https://johnvansickle.com/ffmpeg/releases/ffmpeg-release-amd64-static.tar.xz
FILE=$(basename ${URL})
DIST=$HOME/.dist
DISTFILE=${DIST}/${FILE}
DEST=${FILE%%.*}

[ -d ${DIST} ] || mkdir ${DIST}
[ -d ${DEST} ] || mkdir ${DEST}

if [[ -f ${DISTFILE} ]]; then
  # not first run
  curl -o ${DISTFILE} -z ${DISTFILE} -L ${URL}
else
  # first run
  curl -o ${DISTFILE} -L ${URL}
fi

tar xvJ --strip-components=1 -C ${DEST} -f ${DISTFILE}
