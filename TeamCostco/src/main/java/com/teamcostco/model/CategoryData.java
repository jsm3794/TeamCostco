package main.java.com.teamcostco.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

public class CategoryData {
    private Map<String, Set<String>> mainToMidiumMap;
    private Map<String, Set<String>> midiumToSmallMap;

    public CategoryData(List<AllCategoryJoin> allCategories) {
        mainToMidiumMap = new HashMap<>();
        midiumToSmallMap = new HashMap<>();
        for (AllCategoryJoin category : allCategories) {
            addCategory(category);
        }
    }

    private void addCategory(AllCategoryJoin category) {
        String mainName = category.getMain_name();
        String midiumName = category.getMidium_name();
        String smallName = category.getSmall_name();

        mainToMidiumMap.putIfAbsent(mainName, new HashSet<>());
        mainToMidiumMap.get(mainName).add(midiumName);

        midiumToSmallMap.putIfAbsent(midiumName, new HashSet<>());
        midiumToSmallMap.get(midiumName).add(smallName);
    }

    public Set<String> getMidiumCategories(String mainName) {
        return mainToMidiumMap.getOrDefault(mainName, new HashSet<>());
    }

    public Set<String> getSmallCategories(String midiumName) {
        return midiumToSmallMap.getOrDefault(midiumName, new HashSet<>());
    }

    public Set<String> getMainCategories() {
        return mainToMidiumMap.keySet();
    }
}
