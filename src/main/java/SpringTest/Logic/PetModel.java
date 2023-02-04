package SpringTest.Logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PetModel implements Serializable {
    private static final PetModel instance = new PetModel();

    private final Map<Integer, Pet> model;

    public PetModel() {
        model = new HashMap<Integer, Pet>();
    }

    public static PetModel getInstance() {
        return instance;
    }

    public void add(Pet pet, int id) {
        model.put(id, pet);
    }

    public Pet getFromList(int id) {
        return model.get(id);
    }


    public Pet deleteFromList(int id) {
        return model.remove(id);
    }
    public Pet editFromList(Integer id, Pet pet) {
        return model.put(id,pet);
    }
    public Map<Integer, Pet> getAll() {
        return model;
    }
}
