#!/bin/bash
for i in $(seq 10)
do
  curl -sw '%{time_total} - %{time_pretransfer}\n' -o /dev/null $1
  echo -n . >&2
done | bc | ministat -A
