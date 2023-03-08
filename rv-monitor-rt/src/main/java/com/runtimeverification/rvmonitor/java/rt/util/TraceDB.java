package com.runtimeverification.rvmonitor.java.rt.util;

import java.util.List;
import java.util.Map;

public interface TraceDB {

    void put(String monitorID, String trace, int length);

    void update(String monitorID, String trace, int length);

    void createTable();

    int uniqueTraces();

    int size();

    List<Integer> getTraceLengths();

    Map<String, Integer> getTraceFrequencies();

    void dump();

}
