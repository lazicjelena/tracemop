#!/bin/bash

TRACEMOP_VS_JAVAMOP_OUTPUT=$1
TRACEMOP_VS_PROTOTYPE_OUTPUT=$2

# Time
function get_time() {
  local output_directory=$1
  local output_file=$2
  pushd $output_directory &> /dev/null
  for i in $(ls); do
    times=$(cat $i/docker.log | grep "Duration" | cut -d ' ' -f 2);
    echo "$i,$(echo $times | tr ' ' ,)" >> $output_file
  done
  popd &> /dev/null
}

# Memory
function get_memory() {
  local output_directory=$1
  local output_file=$2
  pushd $output_directory &> /dev/null
  for i in $(ls); do
    new=0
    old=0
    if [[ -f $i/output/logs/memory.log ]]; then
      new=$(cat $i/output/logs/memory.log)
    fi
    if [[ -f $i/output/old-tracemop/logs/memory.log ]]; then
      old=$(cat $i/output/old-tracemop/logs/memory.log)
    fi
    echo "$i,$new,$old" >> $output_file
  done
  popd &> /dev/null
}

# Disk
function get_disk() {
  local output_directory=$1
  local output_file=$2
  pushd $output_directory &> /dev/null
  for i in $(ls); do
    total_new=0
    total_old=0
    if [[ -d $i/output ]]; then
      pushd $i/output &> /dev/null
      for j in $(ls | grep "all-traces"); do
        if [[ ! -f $j/locations.txt || ! -f $j/specs-frequency.csv || ! -f $j/unique-traces.txt ]]; then
          continue
        fi
        a=$(echo "$(stat --printf='%s' $j/locations.txt)+$(stat --printf='%s' $j/specs-frequency.csv)+$(stat --printf='%s' $j/unique-traces.txt)" | bc -l);
        total_new=$((total_new + a))
      done
      popd &> /dev/null
    fi
    
    if [[ -d $i/output/old-tracemop ]]; then
      pushd $i/output/old-tracemop &> /dev/null
      for j in $(ls | grep "all-traces"); do
        if [[ ! -f $j/locations.txt || ! -f $j/specs-frequency.csv || ! -f $j/unique-traces.txt ]]; then
          continue
        fi
        b=$(echo "$(stat --printf='%s' $j/locations.txt)+$(stat --printf='%s' $j/specs-frequency.csv)+$(stat --printf='%s' $j/unique-traces.txt)+$(stat --printf='%s' $j/unique-traces.txt)" | bc -l); # old prototype stores traces twice
        total_old=$((total_old + b))
      done
      popd &> /dev/null
    fi
    echo "$i,$total_new,$total_old" >> $output_file
  done
  popd &> /dev/null
}

echo "project,download,tracemop,	test,javamop" > "$(pwd)/tracemop-vs-javamop-time.csv"
get_time $TRACEMOP_VS_JAVAMOP_OUTPUT "$(pwd)/tracemop-vs-javamop-time.csv"

echo "project,tracemop,prototype" > "$(pwd)/tracemop-vs-prototype-time.csv"
get_time $TRACEMOP_VS_PROTOTYPE_OUTPUT "$(pwd)/tracemop-vs-prototype-time.csv"

echo "project,tracemop,prototype" > "$(pwd)/tracemop-vs-prototype-memory.csv"
get_memory $TRACEMOP_VS_PROTOTYPE_OUTPUT "$(pwd)/tracemop-vs-prototype-memory.csv"

echo "project,tracemop,prototype" > "$(pwd)/tracemop-vs-prototype-disk.csv"
get_disk $TRACEMOP_VS_PROTOTYPE_OUTPUT "$(pwd)/tracemop-vs-prototype-disk.csv"
