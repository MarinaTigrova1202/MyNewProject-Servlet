package SpringTest.Controller;

import SpringTest.Logic.Pet;
import SpringTest.Logic.PetModel;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController

public class Controller {
    private static final PetModel petModel = PetModel.getInstance();
    private static final AtomicInteger newId = new AtomicInteger(1);

    @PostMapping(value = "/createPet", consumes = "application/json")
    public String createPet(@RequestBody Pet pet) {
        petModel.add(pet, newId.getAndIncrement());
        return "Питомец успешно создан";
    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer,Pet> getAll(){
        return petModel.getAll();
    }



    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public Pet getPet(@RequestBody Map<String, Integer> id) {
    return petModel.getFromList(id.get("id"));
    }
    @DeleteMapping(value = "/deletePet", consumes = "application/json")
   public void deletePet(@RequestBody Map<String, Integer> id) {
        petModel.deleteFromList(id.get("id"));
    }

    @PutMapping(value = "/editPet", consumes = "application/json", produces = "application/json")
    public void editPet(@RequestBody Map<Integer, Pet> pet) {
        pet.forEach(petModel::editFromList);
    }
}
