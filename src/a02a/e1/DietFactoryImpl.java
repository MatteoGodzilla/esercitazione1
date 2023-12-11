package a02a.e1;

import java.util.HashMap;
import java.util.Map;

public class DietFactoryImpl implements DietFactory {

    private abstract class AbstractDiet implements Diet {
        // These are for 100 grams of food
        private Map<String, Map<Nutrient, Integer>> reference = new HashMap<>();

        @Override
        public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
            reference.put(name,nutritionMap);
        }

        public int getTotalNutrient(Map<String, Double> dietMap, Nutrient n){
            int result = 0;
            for(var entry : dietMap.entrySet()){
                result += reference.get(entry.getKey()).get(n) * entry.getValue() / 100;
            }
            return result;
        }
    }

    @Override
    public Diet standard() {
        return new AbstractDiet() {
            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                int totalCalories = 0;

                totalCalories += getTotalNutrient(dietMap, Nutrient.CARBS);
                totalCalories += getTotalNutrient(dietMap, Nutrient.PROTEINS);
                totalCalories += getTotalNutrient(dietMap, Nutrient.FAT);

                return 1500 <= totalCalories && totalCalories <= 2000;  
            }
            
        };
    }

    @Override
    public Diet lowCarb() {
        return new AbstractDiet() {
            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                //i know these variables can be simplified to just function calls
                // i think separating them like this makes the code easier to read
                int totalCarbs = getTotalNutrient(dietMap, Nutrient.CARBS);
                int totalCalories = 0;
                totalCalories += totalCarbs;
                totalCalories += getTotalNutrient(dietMap, Nutrient.PROTEINS);
                totalCalories += getTotalNutrient(dietMap, Nutrient.FAT);

                return ( 1000 <= totalCalories && totalCalories <= 1500 ) && (totalCarbs <= 300);
            }
        };
    }

    @Override
    public Diet highProtein() {
        return new AbstractDiet() {
            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                int totalCarbs = getTotalNutrient(dietMap, Nutrient.CARBS);
                int totalProteins = getTotalNutrient(dietMap, Nutrient.PROTEINS);
                int totalCalories = totalCarbs + totalProteins + getTotalNutrient(dietMap, Nutrient.FAT);

                return (2000 <= totalCalories && totalCalories <= 2500)
                    && totalCarbs <= 300 && totalProteins >= 1300;

            }
        };
    }

    @Override
    public Diet balanced() {
        return new AbstractDiet() {
            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                int totalCarbs = getTotalNutrient(dietMap, Nutrient.CARBS);
                int totalProteins = getTotalNutrient(dietMap, Nutrient.PROTEINS);
                int totalFat = getTotalNutrient(dietMap, Nutrient.FAT);
                int totalCalories = totalCarbs + totalProteins + totalFat;

                return (1600 <= totalCalories && totalCalories <= 2000)
                    && totalCarbs >= 600 && totalProteins >= 600 && totalFat >= 400
                    && (totalFat + totalProteins <= 1100);
            }
        };
    }

}
