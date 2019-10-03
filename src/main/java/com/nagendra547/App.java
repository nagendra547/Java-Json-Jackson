package com.nagendra547;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author nagendra
 *
 */
public class App {
	private static final String RESPONSE = "response.json";

	public static void main(String[] args) {
		String activities = args[0];
		int budget = Integer.parseInt(args[1]);
		int days = Integer.parseInt(args[2]);
		buildSchedule(activities, budget, days);

	}

	private static void buildSchedule(String fileName, int budget, int days) {

		Map<String, String> errorMap = new HashMap<String, String>();
		errorMap.put("error", "Coud not return schedule");

		ObjectMapper mapper = new ObjectMapper();
		File output = new File(RESPONSE);
		List<Activity> activities = null;
		try {
			activities = mapper.readValue(new File(fileName), new TypeReference<List<Activity>>() {
			});

			List<Activity> activities2 = quickSolution(activities, budget, days);
			mapper.writeValue(output, activities2);
			System.out.println(activities2.size());

		} catch (Exception e) {
			System.out.println("Errors while executing");
			try {
				mapper.writeValue(output, errorMap);
			} catch (Exception e2) {
			}

		}

	}

	/**
	 * Quick solution to get the activities
	 * 
	 * @param activities
	 * @param budget
	 * @param days
	 * @return
	 */
	private static List<Activity> quickSolution(List<Activity> activities, int budget, int days) {
		Comparator<Activity> comp = new Comparator<Activity>() {
			public int compare(Activity o1, Activity o2) {
				int diff = o1.getPrice() - o2.getPrice();
				return diff == 0 ? 1 : diff;
			}
		};
		activities.sort(comp);
		List<Activity> solution = new ArrayList<Activity>();

		double average = getAverage(activities);
		int mid = averageItemIndex(activities, average);
		int counter = 0;
		int maxActivity = (3 * days + 1) / 2;
		int i = mid;
		while (counter < maxActivity) {
			solution.add(activities.get(i));
			counter++;
			i++;
		}

		counter = 0;
		i = mid;
		while (counter < maxActivity) {
			solution.add(activities.get(i));
			counter++;
			i--;
		}
		return solution;
	}

	private static double getAverage(List<Activity> activities) {
		int sum = 0;
		for (Activity a : activities) {
			sum += a.getPrice();
		}
		return sum / (double) activities.size();
	}

	private static int averageItemIndex(List<Activity> activities, double average) {
		int counter = 0;
		for (Activity a : activities) {
			if (a.getPrice() > average)
				break;
			counter++;
		}
		return counter;
	}
}
