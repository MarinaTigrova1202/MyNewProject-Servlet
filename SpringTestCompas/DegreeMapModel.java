package ru.appline;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DegreeMapModel implements Serializable {

    private static final DegreeMapModel instance = new DegreeMapModel();

    private final Map<Side, DegreeRange> repo;

    public DegreeMapModel() {
        repo = new HashMap<Side, DegreeRange>();
    }

    public static DegreeMapModel getInstance() {
        return instance;
    }

    public void add(Side side, DegreeRange degreeRange) {
        repo.put(side, degreeRange);
    }

    public Map<Side, DegreeRange> getAll() {
        return repo;
    }

    public void addAll(Map<Side, String> newMap) {
        newMap.forEach((key, line) -> {
            String[] degreeBounds = line.split("-");
            int from = Integer.parseInt(degreeBounds[0]);
            int to = Integer.parseInt(degreeBounds[1]);
            repo.put(key, new DegreeRange(from, to));
        });
    }

}
