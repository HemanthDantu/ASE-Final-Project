package ase.smalloven;

import java.util.HashMap;
import java.util.List;

/**
 * Created by daras on 20-Oct-16.
 */
public class RecpieData {

    private String ID;
    private String Name;
    private String ImageURI;
    private Ingredient Ingredient;
    private String PreprationTime;
    private String Servings;
    private String isvegetarian;
    private String isvegan;
    private String isglutenFree;
    private String isdairyFree;
    private HashMap<String, String>Nutrition;
    private HashMap<String,String>CaloricBreakDown;
    private List<Ingredient> IngredientSet;
    private String Instructions;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageURI() {
        return ImageURI;
    }

    public void setImageURI(String imageURI) {
        ImageURI = imageURI;
    }

    public ase.smalloven.Ingredient getIngredient() {
        return Ingredient;
    }

    public void setIngredient(ase.smalloven.Ingredient ingredient) {
        Ingredient = ingredient;
    }

    public String getPreprationTime() {
        return PreprationTime;
    }

    public void setPreprationTime(String preprationTime) {
        PreprationTime = preprationTime;
    }

    public HashMap<String, String> getNutrition() {
        return Nutrition;
    }

    public void setNutrition(HashMap<String, String> nutrition) {
        Nutrition = nutrition;
    }

    public String getIsvegetarian() {
        return isvegetarian;
    }

    public void setIsvegetarian(String isvegetarian) {
        this.isvegetarian = isvegetarian;
    }

    public String getIsvegan() {
        return isvegan;
    }

    public void setIsvegan(String isvegan) {
        this.isvegan = isvegan;
    }

    public String getIsglutenFree() {
        return isglutenFree;
    }

    public void setIsglutenFree(String isglutenFree) {
        this.isglutenFree = isglutenFree;
    }

    public String getIsdairyFree() {
        return isdairyFree;
    }

    public void setIsdairyFree(String isdairyFree) {
        this.isdairyFree = isdairyFree;
    }

    public String getInstructions() {
        return Instructions;
    }

    public void setInstructions(String instructions) {
        Instructions = instructions;
    }

    public String getServings() {
        return Servings;
    }

    public void setServings(String servings) {
        Servings = servings;
    }

    public HashMap<String, String> getCaloricBreakDown() {
        return CaloricBreakDown;
    }

    public void setCaloricBreakDown(HashMap<String, String> caloricBreakDown) {
        CaloricBreakDown = caloricBreakDown;
    }

    public List<ase.smalloven.Ingredient> getIngredientSet() {
        return IngredientSet;
    }

    public void setIngredientSet(List<ase.smalloven.Ingredient> ingredientSet) {
        IngredientSet = ingredientSet;
    }
}
