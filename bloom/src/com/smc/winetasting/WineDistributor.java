package com.smc.winetasting;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.smc.utils.BloomLogger;
import com.smc.utils.FileUtils;

public class WineDistributor {

  private Map<String, Set<String>> personToWinesPrefMap = Maps.newHashMap();
  private Map<String, Set<String>> wineToPersonsPrefMap = Maps.newHashMap();
  private String                   filePath;
  private Map<String, Set<String>> personToWinesDistMap = Maps.newHashMap();
  private Map<String, Set<String>> wineToPersonsDistMap = Maps.newHashMap();
  private int                      numSoldWines         = 0;

  public WineDistributor(String filePath) {
    this.filePath = filePath;
  }

  public void readFileSetupData() throws IOException {
    List<String> lines = FileUtils.readFileIntoLines(filePath);
    for (String line : lines) {
      updateMaps(personToWinesPrefMap, wineToPersonsPrefMap,
          personToWinesDistMap, wineToPersonsDistMap, line);
    }

    decideDistribution();

    BloomLogger.log("File read. Num lines: " + lines.size());
    BloomLogger.log("Num uniq users: " + personToWinesPrefMap.keySet().size());
    BloomLogger.log("Num uniq wines: " + wineToPersonsPrefMap.keySet().size());
  }

  public void distribute() throws IOException {
    BloomLogger.log("Reading data from file...");
    readFileSetupData();

    BloomLogger.log("\n\nDeciding distribution...");
    decideDistribution();

    BloomLogger.log("\n\n\nSaving results to file...");
    persistResults();
  }

  private void persistResults() throws IOException {
    String personDistFile = "person-to-wines-distribution.txt";
    FileUtils.writeMapToFile(personToWinesDistMap, personDistFile);

    String wineDistFile = "wine-to-persons-distribution.txt";
    FileUtils.writeMapToFile(wineToPersonsDistMap, wineDistFile);

    String bloomFile = "bloomFile.txt";
    FileUtils.writeContentToFile(toString(personToWinesDistMap), bloomFile);
    BloomLogger.log("See '" + personDistFile + "' & '" + wineDistFile + "'");
  }

  private String toString(Map<String, Set<String>> personToWinesDistMap) {
    StringBuffer sb = new StringBuffer();
    sb.append(numSoldWines + "");

    for (Entry<String, Set<String>> entry : personToWinesDistMap.entrySet()) {
      sb.append(distributionToString(entry.getKey(), entry.getValue()));
    }

    return sb.toString();
  }

  private String distributionToString(String personId, Set<String> wineIds) {
    StringBuffer sb = new StringBuffer();

    for (String wineId : wineIds) {
      sb.append("\n");
      sb.append(personId);
      sb.append("\t");
      sb.append(wineId);
    }

    return sb.toString();
  }

  private void decideDistribution() {
    for (String wineId : wineToPersonsPrefMap.keySet()) {
      Set<String> interestedPersons = wineToPersonsPrefMap.get(wineId);

      // If none is interested
      if (interestedPersons.isEmpty()) {
        continue;
      }

      // If all interested people are already full
      for (String person : interestedPersons) {
        if (isQuotaExceeded(person)) {
          continue;
        }

        wineToPersonsDistMap.get(wineId).add(person);
        personToWinesDistMap.get(person).add(wineId);
      }
    }

    for (Entry<String, Set<String>> entry : wineToPersonsDistMap.entrySet()) {
      if (!entry.getValue().isEmpty()) {
        numSoldWines++;
      }
    }

    int numPersonsWithNoWines = 0;
    for (Entry<String, Set<String>> entry : personToWinesDistMap.entrySet()) {
      if (entry.getValue().isEmpty()) {
        numPersonsWithNoWines++;
      }
    }

    BloomLogger.log("Num sold wines: " + numSoldWines);
    BloomLogger.log("Num people with no wines: " + numPersonsWithNoWines);
  }

  private boolean isQuotaExceeded(String personId) {
    return personToWinesDistMap.get(personId).size() >= 3;
  }

  @VisibleForTesting
  protected void updateMaps(Map<String, Set<String>> personToWinesPrefsMap,
      Map<String, Set<String>> wineToPersonsPrefsMap,
      Map<String, Set<String>> personToWinesDistMap,
      Map<String, Set<String>> wineToPersonsDistMap, String prefLine) {

    Preference preference = Preference.fromString(prefLine);
    if (preference == null) {
      return;
    }

    String personId = preference.getPersonId();
    String wineId = preference.getWineId();

    insertIfNotPresent(personToWinesPrefsMap, personId);
    insertIfNotPresent(wineToPersonsPrefsMap, wineId);

    insertIfNotPresent(personToWinesDistMap, personId);
    insertIfNotPresent(wineToPersonsDistMap, wineId);

    personToWinesPrefsMap.get(personId).add(wineId);
    wineToPersonsPrefsMap.get(wineId).add(personId);
  }

  public void insertIfNotPresent(Map<String, Set<String>> map, String key) {
    if (!map.containsKey(key)) {
      Set<String> value = Sets.newHashSet();
      map.put(key, value);
    }
  }

}
