#!/bin/bash
OUTPUT_DIR=$1
TRACEMOP_DIR=$2
TRACEDB_PATH=${OUTPUT_DIR}/all-traces

if [[ -f ${TRACEDB_PATH}/unique-traces.txt ]]; then
  mv ${TRACEDB_PATH}/unique-traces.txt ${TRACEDB_PATH}/traces-id.txt
  python3 ${TRACEMOP_DIR}/scripts/count-traces-frequency.py ${TRACEDB_PATH}
  rm ${TRACEDB_PATH}/traces-id.txt ${TRACEDB_PATH}/traces.txt
fi

num_db=0
last_db=""
for db in $(ls ${OUTPUT_DIR}/ | grep "all-traces-"); do
  # search directory starts with all-traces-*
  if [[ ! -f ${OUTPUT_DIR}/${db}/unique-traces.txt || ! -f ${OUTPUT_DIR}/${db}/specs-frequency.csv || ! -f ${OUTPUT_DIR}/${db}/locations.txt || ! -f ${OUTPUT_DIR}/${db}/traces.txt ]]; then
    continue
  fi

  mv ${OUTPUT_DIR}/${db}/unique-traces.txt ${OUTPUT_DIR}/${db}/traces-id.txt
  python3 ${TRACEMOP_DIR}/scripts/count-traces-frequency.py ${OUTPUT_DIR}/${db}
  rm ${OUTPUT_DIR}/${db}/traces-id.txt ${OUTPUT_DIR}/${db}/traces.txt
  num_db=$((num_db + 1))
  last_db=${db}
done

if [[ ! -d ${TRACEDB_PATH} || -z $(ls -A ${TRACEDB_PATH}) ]]; then
  rm -rf ${TRACEDB_PATH}
fi

if [[ ${num_db} -eq 1 ]]; then
  mv ${OUTPUT_DIR}/${db} ${TRACEDB_PATH}
fi

echo "Listing all violations:"
for violations in $(find -name violation-counts); do
  if [[ -f ${violations} ]]; then
    cat ${violations}
  fi
done
