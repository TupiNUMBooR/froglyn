#!/usr/bin/env bash
. vars.sh
sess=froglyn
if ! tmux has -t $sess 2> /dev/null; then
    tmux new-session -d -s $sess \
    java -jar "froglyn-0.0.2.jar"
fi
