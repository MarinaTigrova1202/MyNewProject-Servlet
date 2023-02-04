package ru.marina.compas;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Controller {

    private static final DegreeMapModel DEGREE_MAP_MODEL = DegreeMapModel.getInstance();

    static {
        // for test
        DEGREE_MAP_MODEL.add("North", new DegreeRange(3, 59));
        DEGREE_MAP_MODEL.add("Northeas", new DegreeRange(60, 119));
        DEGREE_MAP_MODEL.add("East", new DegreeRange(359, 0));
        DEGREE_MAP_MODEL.add("Southeast", new DegreeRange(1, 2));
    }

    @GetMapping(value = "/calculateSide", produces = "application/json", consumes = "application/json")
    public String calculateSide(@RequestBody Map<String, Integer> map) {
        int degree = map.get("Degree");
        for (Map.Entry<String, DegreeRange> entry : DEGREE_MAP_MODEL.getAll().entrySet())
            if (isGoalSide(entry.getValue(), degree)) {
                return entry.getKey();
            }
        return null;
    }

    private boolean isGoalSide(DegreeRange degreeRange, int goalValue) {
        for (int i = degreeRange.getFrom(); i <= degreeRange.getTo(); i++) {
            if (goalValue == i) {
                return true;
            }
            if (i >= 359) {
                i = -1;
            }
        }
        return false;
    }

    @PostMapping(value = "/initSides", consumes = "application/json")
    public void initSides(@RequestBody Map<String, String> map) {
        DEGREE_MAP_MODEL.addAll(map);
    }

    @GetMapping(value = "/", produces = "application/json")
    public Map<String, DegreeRange> get() {
        return DEGREE_MAP_MODEL.getAll();
    }

}
