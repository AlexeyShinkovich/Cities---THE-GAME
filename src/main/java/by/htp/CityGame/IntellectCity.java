package by.htp.CityGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class IntellectCity {
	private List<String> inBoxCity = new ArrayList<>();
	private Map<Character, List<String>> indexMapCities;

	public String getInitialName() {
		List<String> startCities = indexMapCities.get('В');
		int random = new Random().nextInt(startCities.size());
		String startCity = startCities.get(random);
		startCities.remove(random);
		indexMapCities.put('В', startCities);
		return startCity;
	}

	public String getInBoxCity(String city) {
		delDoubleCity(city);

		if (inBoxCity.isEmpty()) {
			return getAnswer(city);
		} else {
			if (inBoxCity.indexOf(city) != -1) {
				return "Repeat city";
			} else {
				return getAnswer(city);
			}
		}
	}

	public String getAnswer(String city) {
		char lastSymbol = getLastChar(city);
		List<String> cities = indexMapCities.get(lastSymbol);
		if (cities == null || cities.size() == 0) {
			return "I Lose";
		} else {
			int random = new Random().nextInt(cities.size());
			String answer = cities.get(Math.round(random));
			cities.remove(random);
			indexMapCities.put(lastSymbol, cities);
			inBoxCity.add(city);
			return answer;
		}
	}

	public void delDoubleCity(String city) {
		char firstSymbol = getFirstChar(city);
		List<String> citiesOfFirstSymbol = indexMapCities.get(firstSymbol);

		if (citiesOfFirstSymbol != null) {
			citiesOfFirstSymbol.remove(city);
			indexMapCities.put(firstSymbol, citiesOfFirstSymbol);
		}
	}

	public Character getFirstChar(String city) {
		return city.charAt(0);
	}

	public Character getLastChar(String city) {
		int pos = city.length() - 1;
		char lastChar = city.toUpperCase().charAt(pos);
		if (city.toUpperCase().charAt(pos) == 'Й') {
			return 'И';
		} else if (lastChar == 'Ь' || lastChar == 'Ы' || lastChar == 'Ъ') {
			pos--;
		}
		return city.toUpperCase().charAt(pos);
	}

	public void initCities(List<String> cities) {
		indexMapCities = new HashMap<Character, List<String>>();
		for (String city : cities) {
			Character firstChar = getFirstChar(city);
			List<String> cs = indexMapCities.get(firstChar);

			if (cs == null) {
				indexMapCities.put(firstChar, cs = new ArrayList<String>());
			}

			cs.add(city);
		}
	}
}