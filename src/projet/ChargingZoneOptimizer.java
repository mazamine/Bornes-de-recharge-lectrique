package projet;
import java.util.*;

public class ChargingZoneOptimizer {

    public static void optimizeChargingZones(List<Ville> cities, List<Route> roads) {
        Map<Ville, Ville> parentMap = new HashMap<>();
        Map<Ville, Integer> rankMap = new HashMap<>();

        for (Ville city : cities) {
            makeSet(city, parentMap, rankMap);
        }

        for (Route road : roads) {
            Ville city1 = road.getCity1();
            Ville city2 = road.getCity2();

            Ville parent1 = find(city1, parentMap);
            Ville parent2 = find(city2, parentMap);

            if (!parent1.equals(parent2)) {
                union(parent1, parent2, parentMap, rankMap);
            }
        }

        Map<Ville, List<Ville>> groups = new HashMap<>();
        for (Ville city : cities) {
            Ville parent = find(city, parentMap);
            groups.computeIfAbsent(parent, k -> new ArrayList<>()).add(city);
        }

        for (Map.Entry<Ville, List<Ville>> entry : groups.entrySet()) {
            List<Ville> group = entry.getValue();
            boolean hasChargingZone = false;

            for (Ville city : group) {
                if (city.hasZoneDeRecharge()) {
                    city.setZoneDeRecharge(true);;
                    break;
                }
            }

            if (!hasChargingZone) {
                group.get(0).setZoneDeRecharge(true);
            }
        }
    }

    private static void makeSet(Ville city, Map<Ville, Ville> parentMap, Map<Ville, Integer> rankMap) {
        parentMap.put(city, city);
        rankMap.put(city, 0);
    }

    private static Ville find(Ville city, Map<Ville, Ville> parentMap) {
        if (city != parentMap.get(city)) {
            parentMap.put(city, find(parentMap.get(city), parentMap));
        }
        return parentMap.get(city);
    }

    private static void union(Ville city1, Ville city2, Map<Ville, Ville> parentMap, Map<Ville, Integer> rankMap) {
        Ville parent1 = find(city1, parentMap);
        Ville parent2 = find(city2, parentMap);

        if (rankMap.get(parent1) > rankMap.get(parent2)) {
            parentMap.put(parent2, parent1);
        } else {
            parentMap.put(parent1, parent2);
            if (rankMap.get(parent1).equals(rankMap.get(parent2))) {
                rankMap.put(parent2, rankMap.get(parent2) + 1);
            }
        }
    }

}
