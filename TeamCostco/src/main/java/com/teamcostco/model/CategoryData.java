package main.java.com.teamcostco.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

public class CategoryData {
    private Map<String, Set<String>> mainTomediumMap;
    private Map<String, Set<String>> mediumToSmallMap;

    public CategoryData(List<AllCategoryJoin> allCategories) {
        mainTomediumMap = new HashMap<>();
        mediumToSmallMap = new HashMap<>();
        for (AllCategoryJoin category : allCategories) {
            addCategory(category);
        }
    }

    private void addCategory(AllCategoryJoin category) {
        String mainName = category.getMain_name();
        String mediumName = category.getmedium_name();
        String smallName = category.getSmall_name();

        mainTomediumMap.putIfAbsent(mainName, new HashSet<>());
        mainTomediumMap.get(mainName).add(mediumName);

        mediumToSmallMap.putIfAbsent(mediumName, new HashSet<>());
        mediumToSmallMap.get(mediumName).add(smallName);
    }

    public Set<String> getmediumCategories(String mainName) {
        return mainTomediumMap.getOrDefault(mainName, new HashSet<>());
    }

    public Set<String> getSmallCategories(String mediumName) {
        return mediumToSmallMap.getOrDefault(mediumName, new HashSet<>());
    }

    public Set<String> getMainCategories() {
        return mainTomediumMap.keySet();
    }
}
