package com.airbnb.aerosolve.core.features;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

/*
  Features coming from differenct sources, and output as array[],
  FeatureMapping helps to save incoming feature in the right index of final output array[]
  it use incoming feature names array as key to locate the index.
  refer to ModelScorerTest.java as how to use FeatureMapping
 */
public class InputSchema {
  public final static int DEFAULT_SIZE = 100;
  @Getter
  private String[] names;
  private ArrayList<String> nameList;
  @Getter
  private final Map<Object, Entry> mapping;

  public static final class Entry {
    int start;
    int length;
  }

  public InputSchema() {
    this(DEFAULT_SIZE);
  }

  public InputSchema(int size) {
    nameList = new ArrayList<>(size);
    mapping = new HashMap<>(size);
  }

  // use name mapping array as key.
  public void add(String[] names) {
    add(names, names);
  }

  public void add(Object c, String[] names) {
    assert(names.length > 0);
    // should not add duplicated feature mapping
    assert(mapping.get(c) == null);
    Entry e = new Entry();
    e.start = nameList.size();
    e.length = names.length;
    Collections.addAll(nameList, names);
    mapping.put(c, e);
  }

  public void add(Class c, List<String> features) {
    assert (features.size() > 0);
    Entry e = new Entry();
    e.start = nameList.size();
    e.length = features.size();
    for (String name : features) {
      nameList.add(name);
    }
    mapping.put(c, e);
  }

  public void finish() {
    names = new String[nameList.size()];
    nameList.toArray(names);
    nameList = null;
  }
}